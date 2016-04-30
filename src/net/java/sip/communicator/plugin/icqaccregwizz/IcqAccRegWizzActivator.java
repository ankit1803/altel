/*
 * Jitsi, the OpenSource Java VoIP and Instant Messaging client.
 *
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package net.java.sip.communicator.plugin.icqaccregwizz;

import java.util.*;

import net.java.sip.communicator.service.browserlauncher.*;
import net.java.sip.communicator.service.gui.*;
import net.java.sip.communicator.service.protocol.*;
import net.java.sip.communicator.util.*;

import org.osgi.framework.*;

/**
 * Registers the <tt>IcqAccountRegistrationWizard</tt> in the UI Service.
 *
 * @author Yana Stamcheva
 */
public class IcqAccRegWizzActivator
    extends AbstractServiceDependentActivator
{
    /**
     * OSGi bundle context.
     */
    public static BundleContext bundleContext;

    private static Logger logger = Logger.getLogger(
        IcqAccRegWizzActivator.class);

    private static BrowserLauncherService browserLauncherService;

    private static WizardContainer wizardContainer;

    private static IcqAccountRegistrationWizard icqWizard;

    private static UIService uiService;

    /**
     * Starts this bundle.
     */
    @Override
    public void start(Object dependentService)
    {
        uiService = (UIService)dependentService;

        wizardContainer = uiService.getAccountRegWizardContainer();

        icqWizard = new IcqAccountRegistrationWizard(wizardContainer);

        Hashtable<String, String> containerFilter
            = new Hashtable<String, String>();

        containerFilter.put(
                ProtocolProviderFactory.PROTOCOL,
                ProtocolNames.ICQ);

//        bundleContext.registerService(
//            AccountRegistrationWizard.class.getName(),
//            icqWizard,
//            containerFilter);
    }

    /**
     * The dependent class. We are waiting for the ui service.
     * @return the ui service class.
     */
    @Override
    public Class<?> getDependentServiceClass()
    {
        return UIService.class;
    }

    /**
     * The bundle context to use.
     * @param context the context to set.
     */
    @Override
    public void setBundleContext(BundleContext context)
    {
        bundleContext = context;
    }

    public void stop(BundleContext bundleContext) throws Exception
    {
    }

    /**
     * Returns the <tt>ProtocolProviderFactory</tt> for the ICQ protocol.
     * @return the <tt>ProtocolProviderFactory</tt> for the ICQ protocol
     */
    public static ProtocolProviderFactory getIcqProtocolProviderFactory() {

        ServiceReference[] serRefs = null;

        String osgiFilter = "("
            + ProtocolProviderFactory.PROTOCOL
            + "="+ProtocolNames.ICQ+")";

        try {
            serRefs = bundleContext.getServiceReferences(
                ProtocolProviderFactory.class.getName(), osgiFilter);
        }
        catch (InvalidSyntaxException ex){
            logger.error("IcqAccRegWizzActivator : " + ex);
        }

        return (ProtocolProviderFactory) bundleContext.getService(serRefs[0]);
    }

    /**
     * Returns the <tt>BrowserLauncherService</tt> obtained from the bundle
     * context.
     * @return the <tt>BrowserLauncherService</tt> obtained from the bundle
     * context
     */
    public static BrowserLauncherService getBrowserLauncher() {
        if (browserLauncherService == null) {
            ServiceReference serviceReference = bundleContext
                .getServiceReference(BrowserLauncherService.class.getName());

            browserLauncherService = (BrowserLauncherService) bundleContext
                .getService(serviceReference);
        }

        return browserLauncherService;
    }

    /**
     * Returns the <tt>UIService</tt>.
     *
     * @return the <tt>UIService</tt>
     */
    public static UIService getUIService()
    {
        return uiService;
    }
}
