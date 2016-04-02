/*
 * Jitsi, the OpenSource Java VoIP and Instant Messaging client.
 *
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package net.java.sip.communicator.impl.gui.main.call.conference;

import net.java.sip.communicator.impl.gui.main.call.*;
import net.java.sip.communicator.impl.gui.utils.*;

/**
 * Implements an AWT/Swing button which toggles the display of the participants
 * list in a conference call.
 *
 * @author Yana Stamcheva
 */
public class ShowHidePeersButton
    extends AbstractCallToggleButton
{
    private static final long serialVersionUID = 0L;

    /**
     * The parent call container.
     */
    private final CallPanel callPanel;

    /**
     * Initializes a new <tt>ShowHideVideoButton</tt> instance which is to
     * toggle the display of the visual <tt>Component</tt> which depicts the
     * video streaming from the local peer/user to the remote peer(s).
     *
     * @param callPanel the parent call container
     * @param selected <tt>true</tt> if the new toggle button is to be initially
     * selected; otherwise, <tt>false</tt>
     */
    public ShowHidePeersButton(CallPanel callPanel, boolean selected)
    {
        super(  null,
                true,
                selected,
                ImageLoader.SHOW_HIDE_PEERS_BUTTON,
                ImageLoader.SHOW_HIDE_PEERS_BUTTON_PRESSED,
                "service.gui.SHOW_HIDE_PEERS_TOOL_TIP");

        this.callPanel = callPanel;
    }

    /**
     * Toggles the display of the visual <tt>Component</tt> which depicts the
     * video streaming from the local peer/user to the remote peer(s).
     */
    @Override
    public void buttonPressed()
    {
        callPanel.showThumbnailsList(isSelected());
    }
}
