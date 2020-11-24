/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.frame.util;

import com.goldsign.acc.frame.constant.CommandConstant;
import com.goldsign.acc.frame.entity.PubFlag;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author hejj
 */
public class FormUtil {
    public static String getParameter(HttpServletRequest req,String fieldName){
        String value = req.getParameter(fieldName);
        if(value ==null)
            return "";
        return value;
    }
    
    
     public static int getParameterIntVal(HttpServletRequest req,String fieldName){
        String value = req.getParameter(fieldName);
        if(value ==null || value.length()==0)
            return 0;
        return Integer.parseInt(value);
    }
    public static String getParameterInt(HttpServletRequest req,String fieldName){
        String value = req.getParameter(fieldName);
        if(value ==null)
            return "0";
        return value;
    }
    public static String getLineStations(List<PubFlag> stations){
        String lineStations ="";
        for(PubFlag station:stations){
            lineStations =lineStations+station.getCode_type()+","+station.getCode()+","+station.getCode_text()+":";
        }
        return lineStations;
        
    }
     public static String getMainSubs(List<PubFlag> mainSubs){
        String strMainSub ="";
        for(PubFlag mainSub:mainSubs){
            strMainSub =strMainSub+mainSub.getCode_type()+","+mainSub.getCode()+","+mainSub.getCode_text()+":";
        }
        return strMainSub;
        
    }
    public static boolean isAddOrModifyMode(HttpServletRequest request) {
        String precommand = request.getParameter("precommand");
        String preTwoCommand = request.getParameter("preTwoCommand");
        String command = request.getParameter("command");
        //System.out.println("command="+command+" prcommand="+precommand+" preTwoCommand="+preTwoCommand);
        if (command == null) {
            return false;
        }
        if (command.equals(CommandConstant.COMMAND_ADD)||command.equals(CommandConstant.COMMAND_MODIFY)) {
            return true;
        }
        if (precommand != null && precommand.equals(CommandConstant.COMMAND_ADD) 
                && command.equals(CommandConstant.COMMAND_MODIFY)) {
            return true;
        }
        if (precommand != null && precommand.equals(CommandConstant.COMMAND_ADD) && command.equals(CommandConstant.COMMAND_DELETE)) {
            return true;
        }
        if (preTwoCommand != null && preTwoCommand.equals(CommandConstant.COMMAND_ADD)
                && precommand.equals(CommandConstant.COMMAND_MODIFY) && command.equals(CommandConstant.COMMAND_DELETE)) {
            return true;
        }
        return false;


    }
    public static HashMap  getQueryControlDefaultValues(String queryControlDefaultValues) {
        HashMap qv = new HashMap();
        if (queryControlDefaultValues == null || queryControlDefaultValues.trim().length() == 0) {
            return qv;
        }
        StringTokenizer st = new StringTokenizer(queryControlDefaultValues, ";");
        String token = "";
        String name = "";
        String value = "";
        int index = -1;
        while (st.hasMoreTokens()) {
            token = st.nextToken();
            index = token.indexOf("#");
            if (index == -1) {
                continue;
            }
            name = token.substring(0, index);
            value = token.substring(index + 1);
            qv.put(name, value);

        }
        return qv;
    }
     public static String getQueryControlDefaultValue(HashMap vQueryControlDefaultValues, String name) {
        String value = (String) vQueryControlDefaultValues.get(name);
        if (value == null) {
            value = "";
        }
        return value;
    }
    
}
