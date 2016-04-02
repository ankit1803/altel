/*
 * Jitsi, the OpenSource Java VoIP and Instant Messaging client.
 *
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package net.java.sip.communicator.impl.gui.main.call;

import java.awt.*;

import javax.swing.*;

import net.java.sip.communicator.impl.gui.*;
import net.java.sip.communicator.impl.gui.utils.*;
import net.java.sip.communicator.util.skin.*;

/**
 * The <tt>SecurityStatusLabel</tt> is meant to be used to visualize the audio
 * and video security details in a call.
 *
 * @author Yana Stamcheva
 * @author Adam Netocny
 */
public class SecurityStatusLabel
    extends JLabel
    implements  Skinnable
{
    /**
     * Serial version UID.
     */
    private static final long serialVersionUID = 0L;

    /**
     * The icon used for the not secured state.
     */
    private static Icon securityOffIcon;

    /**
     * The icon used for the secured state.
     */
    private static Icon securityOnIcon;

    /**
     * The icon used for the security pending state.
     */
    private static Icon securityPendingIcon;

    /**
     * Creates an instance of <tt>SecurityStatusLabel</tt> by specifying the
     * <tt>GuiCallPeer</tt>, the icon and the alignment to use for the label.
     */
    public SecurityStatusLabel()
    {
        loadSkin();

        setBorder(BorderFactory.createEmptyBorder(2, 8, 2, 3));
        setForeground(Color.WHITE);

        setHorizontalAlignment(JLabel.CENTER);
        setHorizontalTextPosition(JLabel.LEFT);
    }

    /**
     * Sets the audio security on or off.
     */
    public void setSecurityOn()
    {
        setIcon(securityOnIcon);
        setToolTipText(GuiActivator.getResources().getI18NString(
            "service.gui.security.CALL_SECURED_TOOLTIP"));
    }

    /**
     * Sets the audio security on or off.
     */
    public void setSecurityOff()
    {
        setIcon(securityOffIcon);
        this.setToolTipText(GuiActivator.getResources().getI18NString(
            "service.gui.security.CALL_NOT_SECURED_TOOLTIP"));
    }

    /**
     * Sets the audio security on or off.
     */
    public void setSecurityPending()
    {
        setIcon(securityPendingIcon);
        this.setToolTipText(GuiActivator.getResources().getI18NString(
            "service.gui.security.CALL_SECURED_COMPARE_TOOLTIP"));
    }

    /**
     * Indicates if the security status has been already set.
     *
     * @return <tt>true</tt> to indicate that security status is set,
     * <tt>false</tt> - otherwise
     */
    public boolean isSecurityStatusSet()
    {
        return (getIcon() != null) ? true : false;
    }

    /**
     * Reloads icons.
     */
    public void loadSkin()
    {
        securityOffIcon = new ImageIcon(ImageLoader
            .getImage(ImageLoader.SECURE_BUTTON_OFF));

        securityOnIcon = new ImageIcon(ImageLoader
            .getImage(ImageLoader.SECURE_BUTTON_ON));

        securityPendingIcon = new ImageIcon(ImageLoader
            .getImage(ImageLoader.SECURE_BUTTON_PENDING));
    }
}
