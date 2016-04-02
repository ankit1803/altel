/*
 * Jitsi, the OpenSource Java VoIP and Instant Messaging client.
 *
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package net.java.sip.communicator.impl.protocol.irc;

import net.java.sip.communicator.util.*;

import com.ircclouds.irc.api.*;
import com.ircclouds.irc.api.domain.messages.*;
import com.ircclouds.irc.api.listeners.*;
import com.ircclouds.irc.api.state.*;

/**
 * Abstract IRC message listener.
 *
 * This base implementation handles user quit events and error events signaling
 * user quits. The listener is then unregistered from the IRC client instance as
 * we have disconnected.
 *
 * @author Danny van Heumen
 */
public abstract class AbstractIrcMessageListener
    extends VariousMessageListenerAdapter
{
    /**
     * Logger.
     */
    private static final Logger LOGGER = Logger
        .getLogger(AbstractIrcMessageListener.class);

    /**
     * IRC client library instance.
     */
    protected final IRCApi irc;

    /**
     * IRC connection state.
     */
    protected final IIRCState connectionState;

    /**
     * Constructor.
     *
     * @param irc the irc instance
     * @param connectionState the connection state
     */
    protected AbstractIrcMessageListener(final IRCApi irc,
        final IIRCState connectionState)
    {
        if (irc == null)
        {
            throw new IllegalArgumentException(
                "A valid irc instance must be provided.");
        }
        this.irc = irc;
        if (connectionState == null)
        {
            throw new IllegalArgumentException(
                "A valid connection state instance must be provided.");
        }
        this.connectionState = connectionState;
    }

    /**
     * Handler for user quit events.
     *
     * @param msg the quit message
     */
    @Override
    public void onUserQuit(final QuitMessage msg)
    {
        final String nick = msg.getSource().getNick();
        if (!localUser(nick))
        {
            return;
        }
        // User is local user. Remove listener.
        LOGGER.debug("Local user's QUIT message received: removing "
            + this.getClass().getCanonicalName());
        this.irc.deleteListener(this);
    }

    /**
     * In case a (fatal) error occurs, remove the listener.
     *
     * @param msg the error message
     */
    @Override
    public void onError(final ErrorMessage msg)
    {
        // Errors signal fatal situation, so assume connection is lost and
        // unregister.
        LOGGER.debug("Local user received ERROR message: removing "
            + this.getClass().getCanonicalName());
        this.irc.deleteListener(this);
    }

    /**
     * Test if local user is disconnecting or it is some arbitrary other IRC
     * user.
     *
     * @param nick the nick name
     * @return returns <tt>true</tt> if local user is disconnecting, or
     *         <tt>false</tt> if some other user is disconnecting.
     */
    protected boolean localUser(final String nick)
    {
        return nick != null && nick.equals(this.connectionState.getNickname());
    }
}
