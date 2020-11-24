/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.frame.util;

import java.io.UnsupportedEncodingException;

/**
 *
 * @author hejj
 */
public class CharUtil {
    public static String utf8ToGbk18030(String str) {
		if (str == null)
			return str;
		try {

			return new String(str.getBytes("UTF-8"), "GB18030");
		} catch (UnsupportedEncodingException e) {
			return str;
		}
	}
    public static String isoToGbk18030(String str) {
		if (str == null)
			return str;
		try {

			return new String(str.getBytes("ISO-8859-1"), "GB18030");
		} catch (UnsupportedEncodingException e) {
			return str;
		}
	}
    
}
