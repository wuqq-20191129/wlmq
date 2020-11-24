/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.goldsign.csfrm.vo;

/**
 *
 * @author lenovo
 */
public class ConfigParam extends CallParam {

    private String configFileName;
    
    private String logFileName;
    
    private XmlTagVo[] tagVos;

    public ConfigParam(){}
    
    public ConfigParam(String ConfigFileName, String logFileName, XmlTagVo[] tagVos) {
        this.configFileName = ConfigFileName;
        this.logFileName = logFileName;
        this.tagVos = tagVos;
    }

    /**
     * @return the ConfigFileName
     */
    public String getConfigFileName() {
        return configFileName;
    }

    /**
     * @param ConfigFileName the ConfigFileName to set
     */
    public void setConfigFileName(String ConfigFileName) {
        this.configFileName = ConfigFileName;
    }

    /**
     * @return the logFileName
     */
    public String getLogFileName() {
        return logFileName;
    }

    /**
     * @param logFileName the logFileName to set
     */
    public void setLogFileName(String logFileName) {
        this.logFileName = logFileName;
    }

    /**
     * @return the tagVos
     */
    public XmlTagVo[] getTagVos() {
        return tagVos;
    }

    /**
     * @param tagVos the tagVos to set
     */
    public void setTagVos(XmlTagVo[] tagVos) {
        this.tagVos = tagVos;
    }
    
}
