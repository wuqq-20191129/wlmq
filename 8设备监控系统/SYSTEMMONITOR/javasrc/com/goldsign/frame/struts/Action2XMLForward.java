/*
 * Amendment History:
 * 
 * Date          By             Description
 * ----------    -----------    -------------------------------------------
 * 2004-06-01    Rong Weitao    Create the class
*/

package com.goldsign.frame.struts;

import org.apache.struts.action.ActionForward;
import org.apache.log4j.Logger;

/** 
 * ActionForward class for all the forwards to "Action2XML" action
 */
public class Action2XMLForward extends ActionForward{

    /**
     *  The "true" value for the "includeParm" argument
     */
    public static String INCLUDE_PARM_TRUE = "true";
    
    /**
     *  The "false" value for the "includeParm" argument
     */
    public static String INCLUDE_PARM_FALSE = "false";
    
    /**
     *  The "skip value" value for the "includeParm" argument
     */
    public static String INCLUDE_PARM_SKIPVALUE = "skipvalue";

    /**
     * The xsl file name
     */
    private String XSLFile;
    
    
    /**
     * This flag indicates if the request parameters are needed in the result XML
     */
    private String includeParm;
    
    
    /**
     * The message logger
     */
    private static Logger log = Logger.getLogger(Action2XMLForward.class.getName());

    
    /**
     * This member function is used to set the xsl file name
     * 
     * @param   xslFile - The xsl file name
     *
     */
    public void setXSLFile(String xslFile){
        this.XSLFile = xslFile;
    }

    /**
     * This member function is used to get the xsl file name
     *
     * @return  NULL or the xsl file name
     *
     */    
    public String getXSLFile(){
        return this.XSLFile;
    }
    

    /**
     * This member function is used to set the flag that if the request parameters
     * are included in the result XML.
     * 
     * @param   includeParm     -   The flag in String.
     *                              "true" : include all parameters;
     *                              "false"(default) : do not include any parameters;
     *                              "skipValue" : include all parameters except "value(XXX)" parameters
     *
     */
    public void setIncludeParm(String includeParm) {

        if (log.isDebugEnabled())
                log.debug("setIncludeParm() : " + includeParm);

        if (includeParm.toLowerCase().equals(INCLUDE_PARM_TRUE)){
            this.includeParm = INCLUDE_PARM_TRUE;
        }
        else if (includeParm.toLowerCase().equals(INCLUDE_PARM_SKIPVALUE)) {
            this.includeParm = INCLUDE_PARM_SKIPVALUE;
        }
        else {
            this.includeParm = INCLUDE_PARM_FALSE;
        }

    }
    

    /**
     * This member function is used to get the flag that if the request parameters
     * are included.
     * 
     * @return  -   The flag in String.
     *
     */
    public String getIncludeParm() {

        return this.includeParm;

    }


}