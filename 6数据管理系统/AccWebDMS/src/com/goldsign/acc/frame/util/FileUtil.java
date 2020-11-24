/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.frame.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author hejj
 */
public class FileUtil {
    public HashMap getConfigPropertiesByAppPath(HttpServletRequest req, String configFile) throws Exception {
        String appRoot = req.getSession().getServletContext().getRealPath("/");
        String fileName = appRoot + "/properties/" + configFile;
        //      InputStream in =this.getClass().getResourceAsStream(configFile);
        FileInputStream fis = new FileInputStream(fileName);
        String line = null;
        int index = -1;
        String key = null;
        String value = null;
        HashMap properties = new HashMap();
        InputStreamReader isr = null;
        BufferedReader br = null;
        try {
            isr = new InputStreamReader(fis, "GBK");
            br = new BufferedReader(isr);
            while ((line = br.readLine()) != null) {
                if (line.startsWith("#")) {
                    continue;
                }
                index = line.indexOf("=");
                if (index == -1) {
                    continue;
                }
                key = line.substring(0, index);
                value = line.substring(index + 1);
                if (value.startsWith("${ROOT}")) {
                    value = req.getSession().getServletContext().getRealPath("/") + value.substring(7);
                }
                value = value.trim();
                properties.put(key, value);

            }

        } catch (Exception e) {
            //     e.printStackTrace();
            throw e;
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                }
                if (isr != null);
                isr.close();
                if (br != null) {
                    br.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return properties;
    }
    
}
