/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.goldsign.commu.commu.test;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author lenovo
 */
public class TestList {

    public static void main(String[] args){
       String str = "{02#4#0#0#0#0#0#0#0#0#0#0}{02#5#0#0#0#0#0#0#0#0#0#0}{02#5#0#0#0#0#0#0#0#0#0#0}";
       String[] arr = str.substring(1, str.length()-1).split("\\}\\{");
       for(String a: arr){
           System.out.println(a);
       }
    }
}
