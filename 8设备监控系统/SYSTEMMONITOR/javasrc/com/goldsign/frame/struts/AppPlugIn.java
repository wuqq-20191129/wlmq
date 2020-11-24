/*
 * Amendment History:
 * 
 * Date          By             Description
 * ----------    -----------    -------------------------------------------
 * 2004-06-01    Rong Weitao    Create the class
 */
package com.goldsign.frame.struts;

import org.apache.struts.action.PlugIn;
import org.apache.struts.action.ActionServlet;
import org.apache.struts.config.ModuleConfig;
import org.apache.log4j.xml.DOMConfigurator;
import java.io.File;
import java.io.FileNotFoundException;

public class AppPlugIn implements PlugIn {

    /**
     * XML config file location for logger service
     */
    private String loggerServiceConfig = "";
    /**
     * The current application ActionServlet reference.
     */
    private ActionServlet actionServlet;

    public void init(ActionServlet servlet, ModuleConfig config) {
        setServlet(servlet);

        // start the logger service
        try {
            System.out.println("[AppPlugIn] Starting logger servie...");
            startLoggerService();
            System.out.println("[AppPlugIn] Logger service is started.");
        } catch (Exception e) {
            System.out.println("[AppPlugIn] Error : Fail to start the logger service.");
            System.err.println("[AppPlugIn] Error : Fail to start the logger service.");
            e.printStackTrace();
        }

    }

    /**
     * The clear-up jobs for the services
     */
    public void destroy() {
        if (loggerServiceConfig != null) {
            loggerServiceConfig = null;
        }
        return;
    }

    /**
     * Set the logger service config file location
     *
     * @param configLocation - The config file location
     *
     */
    public void setLoggerServiceConfig(String configLocation) {

        this.loggerServiceConfig = configLocation;

    }

    /**
     * Get the logger service config file location
     *
     * @return String - Location of the logger service config file
     *
     */
    public String getLoggerServiceConfig() {

        return this.loggerServiceConfig;

    }

    /**
     * Get the ActionServlet instance that is managing all the modules in this
     * web application
     *
     * @return ActionServlet - ActionServlet that is managing all the modules in
     * this web application
     *
     */
    protected final ActionServlet getServlet() {

        return this.actionServlet;

    }

    /**
     * Set the ActionServlet instance that is managing all the modules in this
     * web application
     *
     * @param servlet - ActionServlet that is managing all the modules in this
     * web application
     *
     */
    protected final void setServlet(ActionServlet servlet) {

        this.actionServlet = servlet;

    }

    /**
     * Start the logger service
     */
    protected void startLoggerService() throws FileNotFoundException {

        String prefix = getServlet().getServletContext().getRealPath("/");

        // The Logger Configuration Location is passed as a PlugIn property in the struts-config.xml file
        String file = getLoggerServiceConfig();

        System.out.println("[AppPlugIn] Logger Service: Use XML config file: " + prefix + file);

        if (file == null || file.length() == 0) {
            throw new IllegalArgumentException("Logger service config file is required.");
        } else {
            File configFile = new File(prefix + file);
            if (!configFile.exists()) {
                throw new FileNotFoundException("Can not find the file " + prefix + file + ".");
            }
            DOMConfigurator.configureAndWatch(prefix + file);
        }

    }
}