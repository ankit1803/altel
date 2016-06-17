/*
 * Jitsi, the OpenSource Java VoIP and Instant Messaging client.
 *
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package net.java.sip.communicator.impl.gui.utils;

import net.java.sip.communicator.impl.gui.*;
import net.java.sip.communicator.plugin.desktoputil.*;
import net.java.sip.communicator.service.gui.*;
import net.java.sip.communicator.service.resources.*;
import net.java.sip.communicator.util.*;

import javax.imageio.*;
import java.awt.image.*;
import java.net.*;
import java.util.*;

/**
 * Service responsible for loading images and possibly cache them.
 *
 * @author Damian Minkov
 */
public class ImageLoaderServiceImpl
    implements ImageLoaderService<BufferedImage>
{
    /**
     * The <tt>Logger</tt> used by the <tt>ImageLoaderServiceImpl</tt> class
     * and its instances for logging output.
     */
    private static final Logger logger =
        Logger.getLogger(ImageLoaderServiceImpl.class);

    /**
     * Stores all already loaded images.
     */
    private static final Map<ImageID, BufferedImage> loadedImages =
        new Hashtable<ImageID, BufferedImage>();

    /**
     * Loads an image from a given image identifier.
     *
     * @param imageID The identifier of the image.
     * @return The image for the given identifier.
     */
    public BufferedImage getImage(ImageID imageID)
    {
        BufferedImage image = null;

        if (loadedImages.containsKey(imageID))
        {
            image = loadedImages.get(imageID);
        }
        else
        {
            URL path = GuiActivator.getResources().getImageURL(imageID.getId());

            if (path != null)
            {
                try
                {
                    image = ImageIO.read(path);

                    loadedImages.put(imageID, image);
                }
                catch (Exception ex)
                {
                    logger.error("Failed to load image: " + path, ex);
                }
            }
        }

        return image;
    }

    /**
     * Loads an image from a given image identifier and return
     * bytes of the image.
     *
     * @param imageID The identifier of the image.
     * @return The image bytes for the given identifier.
     */
    public byte[] getImageBytes(ImageID imageID)
    {
        BufferedImage image = getImage(imageID);

        if(image != null)
            return ImageUtils.toByteArray(image);
        else
            return null;
    }

    /**
     * Clears the images cache.
     */
    public void clearCache()
    {
        loadedImages.clear();
    }
}
