/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.frame.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author hejj
 */
public class PrintHelper {

    public static void screenPrintForEx(String str) {
        System.out.println(datetimeToString(new Date()) + "  " + str);
    }

    public static void screenPrintForEx(Map m) {
        String date = datetimeToString(new Date());
        String key;
        Long value;
        Set set = m.keySet();
        Iterator it = set.iterator();
        while (it.hasNext()) {
            key = (String) it.next();
            value = (Long) m.get(key);
            System.out.println(date + "  " + key + "=" + value);
        }
    }

    private static String datetimeToString(java.util.Date d) {
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return f.format(d);
    }
}
