/*
 * Jitsi, the OpenSource Java VoIP and Instant Messaging client.
 *
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package net.java.sip.communicator.impl.protocol.jabber.extensions.carbon;

import org.jivesoftware.smack.packet.*;
import org.jivesoftware.smack.provider.*;
import org.jivesoftware.smack.util.*;
import org.xmlpull.v1.*;

import net.java.sip.communicator.impl.protocol.jabber.extensions.*;

/**
 * This class implements the forward extension. It is used by carbon extensions.
 * @author Hristo Terezov
 */
public class ForwardedPacketExtension
    extends AbstractPacketExtension
{
    /**
     * The namespace for the XML element.
     */
    public static final String NAMESPACE = "urn:xmpp:forward:0";

    /**
     * The name of the "forwarded" XML element.
     */
    public static final String ELEMENT_NAME = "forwarded";

    /**
     * The message instance included in the forwarded extension.
     */
    private Message message = null;

    /**
     * Constructs new <tt>ForwardedPacketExtension</tt> instance.
     */
    public ForwardedPacketExtension()
    {
        super(NAMESPACE, ELEMENT_NAME);
    }

    /**
     * Sets the <tt>Message</tt> instance to the forwarded extension.
     * @param message the messages
     */
    public void setMessage(Message message)
    {
        this.message = message;
        addPacket(message);
    }

    /**
     * Returns the <tt>Message</tt> instance.
     * @return the <tt>Message</tt> instance.
     */
    public Message getMessage()
    {
        return message;
    }

    /**
     * Parses the forwarded XML element.
     */
    public static class Provider
        implements PacketExtensionProvider
    {
        /**
         * Creates a <tt>ForwardedPacketExtension</tt> by parsing
         * an XML document.
         * @param parser the parser to use.
         * @return the created <tt>ForwardedPacketExtension</tt>.
         * @throws Exception
         */
        @Override
        public PacketExtension parseExtension(XmlPullParser parser)
            throws Exception
        {
            ForwardedPacketExtension packetExtension
                    = new ForwardedPacketExtension();

            //now parse the sub elements
            boolean done = false;
            String elementName;
            Message message = null;

            while (!done)
            {
                switch (parser.next())
                {
                case XmlPullParser.START_TAG:
                {
                    elementName = parser.getName();
                    if ("message".equals(elementName))
                    {
                        message
                            = (Message)PacketParserUtils.parseMessage(parser);
                        if (message != null)
                        {
                            packetExtension.setMessage(message);
                        }
                    }
                    break;
                }
                case XmlPullParser.END_TAG:
                {
                    elementName = parser.getName();
                    if (ELEMENT_NAME.equals(elementName))
                    {
                        done = true;

                    }
                    break;
                }
                }
            }
            return packetExtension;
        }
    }
}
