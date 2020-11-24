/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.goldsign.kmsfront.struct.vo;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author lenovo
 */
public class QueryRetVo {

   private int len;
    
   private List<String> values = new ArrayList<String>();
   
    /**
     * @return the len
     */
    public int getLen() {
        return len;
    }

    /**
     * @param len the len to set
     */
    public void setLen(int len) {
        this.len += len;
    }

    /**
     * @return the values
     */
    public List<String> getValues() {
        return values;
    }

    /**
     * @param values the values to set
     */
    public void setValue(String value) {
        this.values.add(value);
    }

 
}
