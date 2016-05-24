/*
 * Jitsi, the OpenSource Java VoIP and Instant Messaging client.
 *
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package net.java.sip.communicator.impl.gui.main;

import net.java.sip.communicator.impl.gui.GuiActivator;
import net.java.sip.communicator.impl.gui.main.call.DTMFHandler;
import net.java.sip.communicator.impl.gui.utils.ImageLoader;
import net.java.sip.communicator.plugin.desktoputil.SIPCommButton;
import net.java.sip.communicator.plugin.desktoputil.TransparentPanel;
import net.java.sip.communicator.service.resources.ImageID;
import net.java.sip.communicator.util.skin.Skinnable;
import org.jitsi.service.resources.ResourceManagementService;
import org.jitsi.util.OSUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

/**
 * The <tt>DialPanel</tt> is the panel that contains the buttons to dial a
 * phone number.
 *
 * @author Yana Stamcheva
 * @author Adam Netocny
 * @author Lyubomir Marinov
 */
public class GeneralDialPanelNew
    extends TransparentPanel
    implements MouseListener,
               Skinnable
{
    /**
     * The parent dial pad dialog.
     */
    private final GeneralDialPadPanel dialPadDialog;

    /**
     * The dial panel.
     */
    private final JPanel dialPadPanel =
        new TransparentPanel(new GridLayout(4, 3,
            GuiActivator.getResources()
                .getSettingsInt("impl.gui.DIAL_PAD_HORIZONTAL_GAP"),
            GuiActivator.getResources()
                .getSettingsInt("impl.gui.DIAL_PAD_VERTICAL_GAP")));

    /**
     * Handles DTMFs.
     */
    private final DTMFHandler dtmfHandler;

    /**
     * Creates an instance of <tt>DialPanel</tt> for a specific call, by
     * specifying the parent <tt>CallManager</tt> and the
     * <tt>CallPeer</tt>.
     *
     * @param dtmfHandler handles DTMFs.
     */
    public GeneralDialPanelNew(GeneralDialPadPanel dialPadDialog,
                               DTMFHandler dtmfHandler)
    {
        this.dialPadDialog = dialPadDialog;
        this.dtmfHandler = dtmfHandler;

        this.init();
    }

    /**
     * Initializes a new dial button which is to be used on Mac OS X.
     *
     * @param imageID
     * @param rolloverImageID
     * @param pressedImageImageID
     * @param name
     * @return the newly-initialized dial button
     */
    private JButton createDialButton( ImageID imageID,
                                      ImageID rolloverImageID,
                                      ImageID pressedImageImageID,
                                      String name)
    {
        JButton button
            = new SIPCommButton(
                    ImageLoader.getImage(imageID),
                    ImageLoader.getImage(rolloverImageID),
                    ImageLoader.getImage(pressedImageImageID),
                    null,
                    null,
                    null);

        button.setAlignmentY(JButton.LEFT_ALIGNMENT);
        button.setOpaque(false);
        button.setName(name);
        button.addMouseListener(this);
        return button;
    }

    /**
     * Initializes this panel by adding all dial buttons to it.
     */
    public void init()
    {
        this.dialPadPanel.setOpaque(false);

        this.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        loadSkin();

        this.add(dialPadPanel, BorderLayout.CENTER);
    }

    /**
     * Reloads dial buttons.
     */
    @Override
    public void loadSkin()
    {
        dialPadPanel.removeAll();

        for (DTMFHandler.DTMFToneInfo info : DTMFHandler.AVAILABLE_TONES)
        {
            // We only add buttons with images.
            if (info.imageID == null)
                continue;

            JComponent c
                = OSUtils.IS_MAC
                    ? createDialButton(
                            info.macImageID,
                            info.macImageRolloverID,
                            info.macImageRolloverID,
                            info.tone.getValue())
                    : createDialButton(
                            info.imageID,
                            info.imageIDPressed,
                            info.imageIDRollover,
                            info.tone.getValue());

            dialPadPanel.add(c);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

    /**
     * Handles the <tt>MouseEvent</tt> triggered when user presses one of the
     * dial buttons.
     * @param e the event
     */
    @Override
    public void mousePressed(MouseEvent e)
    {
        JButton button = (JButton) e.getSource();

        dialPadDialog.dialButtonPressed(button.getName());
        dtmfHandler.startSendingDtmfTone(button.getName());
    }

    /**
     * Handles the <tt>MouseEvent</tt> triggered when user releases one of the
     * dial buttons.
     * @param e the event
     */
    @Override
    public void mouseReleased(MouseEvent e)
    {
        dtmfHandler.stopSendingDtmfTone();
    }

    /**
     * Paints the main background image to the background of this dial panel.
     *
     * @param g the <tt>Graphics</tt> object used for painting
     */
    @Override
    public void paintComponent(Graphics g)
    {
        // do the superclass behavior first
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        BufferedImage bgImage
            = ImageLoader.getImage(ImageLoader.MAIN_WINDOW_BACKGROUND);

        // paint the image
        if (bgImage != null)
        {
            ResourceManagementService r = GuiActivator.getResources();
            boolean isTextureBackground
                = Boolean.parseBoolean(
                        r.getSettingsString(
                                "impl.gui.IS_CONTACT_LIST_TEXTURE_BG_ENABLED"));
            int width = getWidth();
            int height = getHeight();

            if (isTextureBackground)
            {
                Rectangle rect
                    = new Rectangle(0, 0,
                            bgImage.getWidth(null),
                            bgImage.getHeight(null));
                TexturePaint texture = new TexturePaint(bgImage, rect);

                g2.setPaint(texture);
                g2.fillRect(0, 0, width, height);
            }
            else
            {
                g.setColor(new Color(r.getColor("contactListBackground")));
                // Paint the background with the chosen color.
                g.fillRect(0, 0, width, height);
                g2.drawImage(
                        bgImage,
                        width - bgImage.getWidth(),
                        height - bgImage.getHeight(),
                        this);
            }
        }
    }
}
