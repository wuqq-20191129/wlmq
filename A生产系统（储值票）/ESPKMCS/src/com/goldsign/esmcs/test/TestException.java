/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.goldsign.esmcs.test;

import com.goldsign.esmcs.exception.MessageException;

/**
 *
 * @author lenovo
 */
public class TestException {

    public static void main(String[] args) throws Exception{
        test();
        System.out.println("ok");
    }
    
    public static void test() throws Exception{
    
        try{
            throw new MessageException("AAAAAAAAAAA");
        }catch(Exception e){
            System.out.println("Exception");
            throw new MessageException("BBBBBBBBBB");
        }finally{
            System.out.println("finally");
        }
    }
}
