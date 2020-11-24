/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.test.function;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author hejj
 */
public class ClassReflect {

    public String name;

    public static void main(String[] arg) {
        try {
            Class c = Class.forName("com.goldsign.settle.realtime.test.file.ClassReflect");
            Constructor ctt =c.getConstructor();
            Object obj =ctt.newInstance();
            
            Field[] fs = c.getFields();
            for (Field f : fs) {
                System.out.println(f.getName());
            };
            Method[] ms = c.getMethods();

            Method mg =c.getMethod("getName");
             Method mset =c.getMethod("setName",String.class);
             mset.invoke(obj, "hejj");
             System.out.println("dym="+mg.invoke(obj));
             



        } catch (Exception ex) {
            Logger.getLogger(ClassReflect.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * @return the name
     */
    public String getName() {
         System.out.println("invoke getName");
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        System.out.println("invoke setName");
        this.name = name;
    }
}
