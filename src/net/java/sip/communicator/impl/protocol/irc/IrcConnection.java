/*
 * Jitsi, the OpenSource Java VoIP and Instant Messaging client.
 *
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package net.java.sip.communicator.impl.protocol.irc;

import java.io.*;
import java.util.*;

import net.java.sip.communicator.service.protocol.*;
import net.java.sip.communicator.service.protocol.event.*;
import net.java.sip.communicator.util.*;

import com.ircclouds.irc.api.*;
import com.ircclouds.irc.api.domain.messages.*;
import com.ircclouds.irc.api.listeners.*;
import com.ircclouds.irc.api.state.*;

/**
 * IRC Connection.
 *
 * TODO Show MOTD in Jitsi "System Room" or something similar, since the MOTD is
 * aimed directly at the local user.
 *
 * TODO Find out how irc-api responds to losing a connection (no response). Does
 * it use ping/pong messages to determine connectivity?
 * @author Danny van Heumen
 */
public class IrcConnection
{
    /**
     * Logger.
     */
    private static final Logger LOGGER = Logger.getLogger(IrcConnection.class);

    /**
     * Set of characters with special meanings for IRC, such as: ',' used as
     * separator of list of items (channels, nicks, etc.), ' ' (space) separator
     * of command parameters, etc.
     */
    public static final Set<Character> SPECIAL_CHARACTERS;

    /**
     * Initialize set of special characters.
     */
    static
    {
        HashSet<Character> specials = new HashSet<Character>();
        specials.add('\0');
        specials.add('\n');
        specials.add('\r');
        specials.add(' ');
        specials.add(',');
        SPECIAL_CHARACTERS = Collections.unmodifiableSet(specials);
    }

    /**
     * Context.
     */
    private final IrcStack.PersistentContext context;

    /**
     * IRC client configuration for managing (more advanced) client behaviour
     * such as the use of periodic tasks for querying presence of contacts and
     * channel members.
     */
    private final ClientConfig config;

    /**
     * IRC Api instance.
     */
    private final IRCApi irc;

    /**
     * Connection state of a successful IRC connection.
     */
    private final IIRCState connectionState;

    /**
     * Callback to inform on connection interruptions.
     */
    private final IrcConnectionListener connectionListener;

    /**
     * Manager component that manages current IRC presence.
     */
    private final PresenceManager presence;

    /**
     * Manager component for server channel listing.
     */
    private final ServerChannelLister channelLister;

    /**
     * The local user's identity as it will be used in server-client
     * communication for sent messages.
     */
    private final IdentityManager identity;

    /**
     * The channel manager instance.
     */
    private final ChannelManager channel;

    /**
     * The message manager instance.
     */
    private final MessageManager message;

    /**
     * Constructor.
     *
     * @param context persistent context that crosses connections
     * @param config client configuration
     * @param irc the irc instance
     * @param params connection parameters
     * @param connectionListener listener for callback upon connection
     *            interruption
     * @throws Exception Throws IOException in case of connection problems.
     */
    IrcConnection(final IrcStack.PersistentContext context,
        final ClientConfig config, final IRCApi irc,
        final IServerParameters params,
        final IrcConnectionListener connectionListener)
        throws Exception
    {
        if (context == null)
        {
            throw new IllegalArgumentException("context cannot be null");
        }
        this.context = context;
        if (config == null)
        {
            throw new IllegalArgumentException("client config cannot be null");
        }
        this.config = config;
        if (irc == null)
        {
            throw new IllegalArgumentException("irc instance cannot be null");
        }
        this.irc = irc;
        this.connectionListener = connectionListener;

        // Install a listener for everything that is not directly related to a
        // specific chat room or operation.
        this.irc.addListener(new ServerListener());

        // Now actually connect to the IRC server.
        this.connectionState =
            connectSynchronized(this.context.provider, params, this.irc);

        // instantiate identity manager for the connection
        this.identity =
            new IdentityManager(this.irc, this.connectionState,
                this.context.provider);

        // instantiate message manager for the connection
        this.message =
            new MessageManager(this, this.irc, this.connectionState,
                this.context.provider, this.identity);

        // instantiate channel manager for the connection
        this.channel =
            new ChannelManager(this.irc, this.connectionState,
                this.context.provider, this.config);

        // instantiate presence manager for the connection
        this.presence =
            new PresenceManager(this.irc, this.connectionState,
                this.context.provider.getPersistentPresence(),
                this.config, this.context.nickWatchList);

        // instantiate server channel lister
        this.channelLister =
            new ServerChannelLister(this.irc, this.connectionState);
    }

    /**
     * Perform synchronized connect operation.
     *
     * @param provider Parent protocol provider
     * @param params Server connection parameters
     * @param irc IRC Api instance
     * @throws Exception exception thrown when connect fails
     */
    private static IIRCState connectSynchronized(
        final ProtocolProviderServiceIrcImpl provider,
        final IServerParameters params, final IRCApi irc) throws Exception
    {
        final Result<IIRCState, Exception> result =
            new Result<IIRCState, Exception>();
        synchronized (result)
        {
            // start connecting to the specified server ...
            irc.connect(params, new Callback<IIRCState>()
            {

                @Override
                public void onSuccess(final IIRCState state)
                {
                    synchronized (result)
                    {
                        LOGGER.trace("IRC connected successfully!");
                        result.setDone(state);
                        result.notifyAll();
                    }
                }

                @Override
                public void onFailure(final Exception e)
                {
                    synchronized (result)
                    {
                        LOGGER.trace("IRC connection FAILED!", e);
                        result.setDone(e);
                        result.notifyAll();
                    }
                }
            });

            provider.setCurrentRegistrationState(RegistrationState.REGISTERING,
                RegistrationStateChangeEvent.REASON_USER_REQUEST);

            while (!result.isDone())
            {
                LOGGER.trace("Waiting for the connection to be "
                    + "established ...");
                result.wait();
            }
        }

        // TODO Implement connection timeout and a way to recognize that
        // the timeout occurred.

        final Exception e = result.getException();
        if (e != null)
        {
            throw new IOException(e);
        }

        final IIRCState state = result.getValue();
        if (state == null)
        {
            throw new IOException(
                "Failed to connect to IRC server: connection state is null");
        }

        return state;
    }

    /**
     * Check whether or not a connection is established.
     *
     * @return true if connected, false otherwise.
     */
    public boolean isConnected()
    {
        return this.connectionState != null
            && this.connectionState.isConnected();
    }

    /**
     * Check whether the connection is a secure connection (TLS).
     *
     * @return true if connection is secure, false otherwise.
     */
    public boolean isSecureConnection()
    {
        return isConnected() && this.connectionState.getServer().isSSL();
    }

    /**
     * Disconnect.
     */
    void disconnect()
    {
        try
        {
            this.irc.disconnect();
        }
        catch (RuntimeException e)
        {
            // Disconnect might throw ChannelClosedException. Shouldn't be a
            // problem, but for now lets log it just to be sure.
            LOGGER.debug("exception occurred while disconnecting", e);
        }
    }

    /**
     * Get the IRC client library instance.
     *
     * @return returns the client instance
     */
    public IRCApi getClient()
    {
        return this.irc;
    }

    /**
     * Get the presence manager. (Guaranteed to be non-null.)
     *
     * @return returns the presence manager instance
     */
    public PresenceManager getPresenceManager()
    {
        return this.presence;
    }

    /**
     * Get the channel lister that facilitates server channel queries.
     * (Guaranteed to be non-null.)
     *
     * @return returns the channel lister instance
     */
    public ServerChannelLister getServerChannelLister()
    {
        return this.channelLister;
    }

    /**
     * Get the identity manager instance. (Guaranteed to be non-null.)
     *
     * @return returns the identity manager instance
     */
    public IdentityManager getIdentityManager()
    {
        return this.identity;
    }

    /**
     * Get the channel manager instance. (Guaranteed to be non-null.)
     *
     * @return returns the channel manager instance
     */
    public ChannelManager getChannelManager()
    {
        return this.channel;
    }

    /**
     * Get the message manager instance. (Guaranteed to be non-null.)
     *
     * @return returns the message manager instance
     */
    public MessageManager getMessageManager()
    {
        return this.message;
    }

    /**
     * A listener for server-level messages (any messages that are related to
     * the server, the connection, that are not related to any chatroom in
     * particular) or that are personal message from user to local user.
     */
    private final class ServerListener
        extends VariousMessageListenerAdapter
    {

        /**
         * Print out server notices for debugging purposes and for simply
         * keeping track of the connections.
         *
         * @param msg the server notice
         */
        @Override
        public void onServerNotice(final ServerNotice msg)
        {
            LOGGER.debug("NOTICE: " + msg.getText());
        }

        /**
         * Print out received errors for debugging purposes and may be for
         * expected errors that can be acted upon.
         *
         * @param msg the error message
         */
        @Override
        public void onError(final ErrorMessage msg)
        {
            if (LOGGER.isDebugEnabled())
            {
                LOGGER
                    .debug("ERROR: " + msg.getSource() + ": " + msg.getText());
            }

            // Errors signal fatal situation, so unregister and assume
            // connection lost.
            LOGGER.debug("Local user received ERROR message: removing server "
                + "listener.");
            IrcConnection.this.irc.deleteListener(this);

            // If listener is available, inform of connection interrupt.
            if (IrcConnection.this.connectionListener != null)
            {
                IrcConnection.this.connectionListener
                    .connectionInterrupted(IrcConnection.this);
            }
        }

        /**
         * User quit messages.
         *
         * User quit messages only need to be handled in case quitting users,
         * since that is the only clear signal of presence change we have.
         *
         * @param msg Quit message
         */
        @Override
        public void onUserQuit(final QuitMessage msg)
        {
            final String user = msg.getSource().getNick();
            if (!IrcConnection.this.connectionState.getNickname().equals(user))
            {
                return;
            }

            LOGGER.debug("Local user's QUIT message received: removing "
                + "server listener.");
            IrcConnection.this.irc.deleteListener(this);

            // If listener is available, inform of connection interrupt.
            if (IrcConnection.this.connectionListener != null)
            {
                IrcConnection.this.connectionListener
                    .connectionInterrupted(IrcConnection.this);
            }
        }
    }
}
