/*
 * Jitsi, the OpenSource Java VoIP and Instant Messaging client.
 *
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package net.java.sip.communicator.impl.gui.main.call;

import java.awt.*;

import net.java.sip.communicator.plugin.desktoputil.*;

/**
 * The <tt>CallBarButton</tt> is a button shown in the call window tool bar.
 *
 * @author Yana Stamcheva
 */
public class CallToolBarButton
    extends SIPCommButton
{
    /**
     * The default width of a button in the call tool bar.
     */
    public static final int DEFAULT_WIDTH = 44;

    /**
     * The default height of a button in the call tool bar.
     */
    public static final int DEFAULT_HEIGHT = 38;

    /**
     * Creates an instance of <tt>CallToolBarButton</tt>.
     */
    public CallToolBarButton()
    {
        this(null, null);
    }

    /**
     * Creates an instance of <tt>CallToolBarButton</tt> by specifying the icon
     * image and the tool tip text.
     *
     * @param iconImage the icon of this button
     * @param tooltipText the text to show in the button tooltip
     */
    public CallToolBarButton(   Image iconImage,
                                String tooltipText)
    {
        this(iconImage, null, tooltipText);
    }

    /**
     * Creates an instance of <tt>CallToolBarButton</tt> by specifying the icon
     * image, the name of the button and the tool tip text.
     *
     * @param iconImage the icon of this button
     * @param buttonName the name of this button
     * @param tooltipText the text to show in the button tooltip
     */
    public CallToolBarButton(   Image iconImage,
                                String buttonName,
                                String tooltipText)
    {
        super(null, iconImage);

        setIconImage(iconImage);

        setPreferredSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));
        setName(buttonName);
        setToolTipText(tooltipText);
    }
}
