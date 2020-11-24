package com.goldsign.systemmonitor.web;

import com.goldsign.frame.constant.AppConfig;
import org.apache.struts.action.PlugIn;
import org.apache.struts.action.ActionServlet;
import org.apache.struts.config.ModuleConfig;

public class ApplicationPlugIn implements PlugIn
{ 

    /**
     *  JNDI Prefix
     */
    private String jndiPrefix = "";
	
    public void init(ActionServlet servlet, ModuleConfig config) 
	{
		if (jndiPrefix == null)
			System.out.println("[ApplicationPlugIn] Failed to get the JNDI Prefix information!");
		else
			// Set the JNDI Prefix to AppConfig
			AppConfig.setJndiPrefix(this.getJndiPrefix());
			System.out.println("[ApplicationPlugIn] JNDI Prefix : " + AppConfig.getJndiPrefix());
	}
	
	
	/**
     * The clear-up jobs for the services
     */
	public void destroy()
	{
		if (jndiPrefix != null) jndiPrefix = null;
        return;
    }
    
	/**
	 * Get the JNDI Prefix
	 * 
	 * @return  String - The JNDI Prefix
	 */
	public String getJndiPrefix()
	{
		return jndiPrefix;
	}

	/**
	 * Set the JNDI Prefix
	 * 
	 * @param   aJndiPrefix - The JNDI Prefix
	 */
	public void setJndiPrefix(String aJndiPrefix)
	{
		jndiPrefix = aJndiPrefix.trim();
	}
    
}