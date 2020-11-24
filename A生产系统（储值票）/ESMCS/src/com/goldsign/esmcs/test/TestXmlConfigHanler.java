/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.goldsign.esmcs.test;

import com.goldsign.csfrm.util.XMLConfigHandler;
import com.goldsign.csfrm.vo.XmlConfigVo;
import java.util.Hashtable;
import java.util.Set;
import java.util.Vector;

/**
 *
 * @author lenovo
 */
public class TestXmlConfigHanler {

    public static void main(String[] args) throws Exception{
        XMLConfigHandler xch = new XMLConfigHandler();
        String fileName = "D:\\project\\csMetro\\程序开发\\ES票卡生产系统\\资料\\ESCommunication\\EsCommuConfig.xml";
        Vector configVos = new Vector();
        XmlConfigVo configVo = new XmlConfigVo();
        configVo.setTagName("Common");
        configVos.add(configVo);
        XmlConfigVo configVo1 = new XmlConfigVo();
        configVo1.setTagName("Messages");
        configVo1.setIsAttr(true);
        configVos.add(configVo1);
        XmlConfigVo configVo2 = new XmlConfigVo();
        configVo2.setTagName("FtpPaths");
        configVo2.setIsAttr(true);
        configVos.add(configVo2);
        Hashtable configs = xch.parseConfigFile(fileName, configVos);
        Set keys = configs.keySet();
        for(Object key: keys){
            Object value = configs.get(key);
            System.out.println("tag=" + key);
            Hashtable values = (Hashtable)value;
            Set keys2 = values.keySet();
            for(Object key2: keys2){
                Object value2 = values.get(key2);
                System.out.println("key="+key2 + ":value=" + value2);
            }
        }
    }
}
