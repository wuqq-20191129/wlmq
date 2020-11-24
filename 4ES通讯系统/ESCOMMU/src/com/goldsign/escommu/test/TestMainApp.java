/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.goldsign.escommu.test;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 *
 * @author lenovo
 */
public class TestMainApp {

    public static void main(String[] args) throws UnknownHostException{
        System.out.println(InetAddress.getLocalHost().getHostAddress());
    }
}
