/*
 * Jitsi, the OpenSource Java VoIP and Instant Messaging client.
 *
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package net.java.sip.communicator.impl.muc;

import java.util.*;

import org.jitsi.service.resources.*;

import net.java.sip.communicator.service.contactsource.*;
import net.java.sip.communicator.service.customcontactactions.*;
import net.java.sip.communicator.service.protocol.*;

/**
 * Implements <tt>CustomContactActionsService</tt> for the groups of MUC contact 
 * source.
 * 
 * @author Hristo Terezov
 */
public class MUCGroupCustomContactActionService
    implements CustomContactActionsService<ContactSourceService>
{
    /**
     * List of custom menu items.
     */
    private final List<ContactActionMenuItem<ContactSourceService>> 
        actionMenuItems 
            = new LinkedList<ContactActionMenuItem<ContactSourceService>>();
    
    public MUCGroupCustomContactActionService()
    {
        actionMenuItems.add(new MUCActionMenuItems());
    }
    
    @Override
    public Class<ContactSourceService> getContactSourceClass()
    {
        return ContactSourceService.class;
    }

    @Override
    public Iterator<ContactAction<ContactSourceService>> 
        getCustomContactActions()
    {
        return null;
    }

    @Override
    public Iterator<ContactActionMenuItem<ContactSourceService>> 
        getCustomContactActionsMenuItems()
    {
        return actionMenuItems.iterator(); 
    }

    /**
     * Implements the MUC custom menu items.
     */
    private class MUCActionMenuItems
        implements ContactActionMenuItem<ContactSourceService>
    {
        /**
         * The resource management service.
         */
        private ResourceManagementService resources 
            = MUCActivator.getResources();

        @Override
        public void actionPerformed(ContactSourceService actionSource)
            throws OperationFailedException
        {
            MUCActivator.getUIService().showAddChatRoomDialog();
        }

        @Override
        public byte[] getIcon()
        {
            return resources.getImageInBytes(
                "service.gui.icons.CHAT_ROOM_16x16_ICON");
        }

        @Override
        public String getText(ContactSourceService contactSource)
        {
            return resources.getI18NString("service.gui.MY_CHAT_ROOMS");
        }

        @Override
        public boolean isVisible(ContactSourceService actionSource)
        {
            return actionSource instanceof ChatRoomContactSourceService;
        }

        @Override
        public char getMnemonics()
        {
            return resources.getI18nMnemonic("service.gui.MY_CHAT_ROOMS");
        }

        @Override
        public boolean isEnabled(ContactSourceService actionSource)
        {
            return true;
        }

        @Override
        public boolean isCheckBox()
        {
            return false;
        }

        @Override
        public boolean isSelected(ContactSourceService actionSource)
        {
            return false;
        }
        
    }
}
