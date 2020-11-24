/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.login.result;

import com.goldsign.login.vo.ModuleDistrVo;
import com.goldsign.login.vo.User;
import java.util.List;

/**
 *
 * @author oywl
 */
public class AuthResult {

    private String returnCode;
    private String msg;
    private User user = new User();
    private List<ModuleDistrVo> modules;
    
    private String ip;
    private String flowId;

    /**
     * @return the returnCode
     */
    public String getReturnCode() {
        return returnCode;
    }

    /**
     * @param returnCode the returnCode to set
     */
    public void setReturnCode(String returnCode) {
        this.returnCode = returnCode;
    }

    /**
     * @return the msg
     */
    public String getMsg() {
        return msg;
    }

    /**
     * @param msg the msg to set
     */
    public void setMsg(String msg) {
        this.msg = msg;
    }

    /**

    /**
     * @return the modules
     */
    public List<ModuleDistrVo> getModules() {
        return modules;
    }

    /**
     * @param modules the modules to set
     */
    public void setModules(List<ModuleDistrVo> modules) {
        this.modules = modules;
    }

    /**
     * @return the user
     */
    public User getUser() {
        return user;
    }

    /**
     * @param user the user to set
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * @return the ip
     */
    public String getIp() {
        return ip;
    }

    /**
     * @param ip the ip to set
     */
    public void setIp(String ip) {
        this.ip = ip;
    }

    /**
     * @return the flowId
     */
    public String getFlowId() {
        return flowId;
    }

    /**
     * @param flowId the flowId to set
     */
    public void setFlowId(String flowId) {
        this.flowId = flowId;
    }

}
