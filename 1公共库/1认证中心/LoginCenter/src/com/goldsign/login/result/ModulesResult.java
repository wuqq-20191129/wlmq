/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.login.result;

import com.goldsign.login.vo.ModuleDistrVo;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Administrator
 */
public class ModulesResult {

    private boolean result = true;
    private String msg = "";
    private List<ModuleDistrVo> modulePrilivedges = new ArrayList<ModuleDistrVo>();

    /**
     * @return the result
     */
    public boolean isResult() {
        return result;
    }

    /**
     * @param result the result to set
     */
    public void setResult(boolean result) {
        this.result = result;
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
     * @return the modulePrilivedges
     */
    public List<ModuleDistrVo> getModulePrilivedges() {
        return modulePrilivedges;
    }

    /**
     * @param modulePrilivedges the modulePrilivedges to set
     */
    public void setModulePrilivedges(List<ModuleDistrVo> modulePrilivedges) {
        this.modulePrilivedges = modulePrilivedges;
    }
}
