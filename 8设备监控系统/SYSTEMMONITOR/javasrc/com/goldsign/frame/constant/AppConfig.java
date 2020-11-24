/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.frame.constant;

/**
 *
 * @author hejj
 */
public class AppConfig {
    
	/**
	 * JNDI Prefix
	 */
	private static String jndiPrefix = null;

	/**
	 * Get the JNDI Prefix
	 * 
	 * @return  String - The JNDI Prefix
	 */
	public static String getJndiPrefix()
	{
		return jndiPrefix;
	}

	/**
	 * Set the JNDI Prefix
	 * 
	 * @param   aJndiPrefix - The JNDI Prefix
	 */
	public static void setJndiPrefix(String aJndiPrefix)
	{
		jndiPrefix = aJndiPrefix.trim();
	}
    
}
