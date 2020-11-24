/*
 * InputUtil.java
 *
 * Created on 2008年10月26日, 下午9:53
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package com.goldsign.csfrm.ui.common;

/**
 *
 * @author david.wang
 */
public class InputUtil {

    public static final String MSG_INPUT_DIGIT_MAXVLAUE = "输入必须是数字且最大为";

    /**
     * Creates a new instance of InputUtil
     */
    public InputUtil() {
    }

    public static boolean isCharacter(char c) {
        if ((c >= 'a' && c <= 'z') || c >= 'A' && c <= 'Z') {
            return true;
        }
        return false;
    }

    public static boolean isDigit(char c) {
        if ((c >= '0' && c <= '9')) {
            return true;
        }
        return false;
    }
}
