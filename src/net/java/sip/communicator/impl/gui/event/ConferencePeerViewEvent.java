/*
 * Jitsi, the OpenSource Java VoIP and Instant Messaging client.
 *
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package net.java.sip.communicator.impl.gui.event;

import java.util.EventObject;

import net.java.sip.communicator.impl.gui.main.call.ConferenceCallPeerRenderer;
import net.java.sip.communicator.service.protocol.CallPeer;

/**
 * Represents an event fired by a <tt>BasicConferenceCallPanel</tt> to notify
 * interested <tt>ConferencePeerPanelListener</tt>s about adding or removing
 * peer panel in conference call.
 *
 * @author Hristo Terezov.
 */
public class ConferencePeerViewEvent
    extends EventObject
{
    /**
     * Serial version UID.
     */
    private static final long serialVersionUID = 0L;

    /**
     * The ID of <tt>ConferencePeerPanelEvent</tt> which notifies
     * for adding of peer panels in conference call.
     */
    public static final int CONFERENCE_PEER_VIEW_ADDED = 1;

    /**
     * The ID of <tt>ConferencePeerPanelEvent</tt> which notifies
     * for removing of peer panels in conference call.
     */
    public static final int CONFERENCE_PEER_VIEW_REMOVED = 2;

    /**
     * The ID of the event.
     */
    private int eventID;

    /**
     * The call peer view associated with the event.
     */
    private ConferenceCallPeerRenderer callPeerView;

    /**
     * Initializes a new <tt>ConferencePeerPanelEvent</tt> which is to
     * be fired by a specific <tt>BasicConferenceCallPanel</tt> and notifies
     * about adding or removing of peer panels in conference call.
     *
     * @param eventID the ID of this event which may be
     *            {@link #CONFERENCE_PEER_VIEW_ADDED} or
     *            {@link #CONFERENCE_PEER_VIEW_REMOVED}
     * @param peer the call peer associated with the event.
     * @param callPeerView the peer view associated with the event.
     */
    public ConferencePeerViewEvent(int eventID, CallPeer peer,
        ConferenceCallPeerRenderer callPeerView)
    {
        super(peer);
        this.eventID = eventID;
        this.callPeerView = callPeerView;
    }

    /**
     * Gets the ID of this event which may be one of
     * {@link #CONFERENCE_PEER_VIEW_ADDED} and
     * {@link #CONFERENCE_PEER_VIEW_REMOVED}.
     *
     * @return the ID of this event which may be one of
     *         {@link #CONFERENCE_PEER_VIEW_ADDED},
     *         {@link #CONFERENCE_PEER_VIEW_REMOVED}
     */
    public int getEventID()
    {
        return eventID;
    }

    /**
     * Returns the call peer associated with the event.
     * @return the call peer.
     */
    public CallPeer getCallPeer()
    {
        return (CallPeer) getSource();
    }

    /**
     * Returns the call peer view associated with the event.
     * @return the call peer view.
     */
    public ConferenceCallPeerRenderer getCallPeerView()
    {
        return callPeerView;
    }

}
