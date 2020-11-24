/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.escommu.test;

/**
 *
 * @author huangweijin
 */
public class TestSplitFileName {
    public static void main(String[] args){
        //String str = "MB09802.00201509010001";
        String str = "ES801.00201509010001";
        String[] arr = str.split("\\.");
        System.out.println(arr[0].substring(arr[0].length()-3));
        System.out.println(arr[1]);
     
    }
}
