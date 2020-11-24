/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.frame.mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author luck
 */
public interface MtrConfigMapper {

    public String getConfigValue(String name);


    public void execProcedure(HashMap<String, Object> parmMap);

    public List<Map> getServerMsg();

    public List<Map> getModuleMsg();

    
}
