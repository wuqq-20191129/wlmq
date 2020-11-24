/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.goldsign.kmsfront.struct.vo;

/**
 *
 * @author lenovo
 */
public class QueryConVo {

    private int type;
    
    private String sign;
    
    private String value;

    public QueryConVo(){}
    
    public QueryConVo(int type, String sign, String value) {
        this.type = type;
        this.sign = sign;
        this.value = value;
    }

    @Override
    public String toString() {
        return "QueryConVo{" + "type=" + type + ", sign=" + sign + ", value=" + value + '}';
    }

    /**
     * @return the type
     */
    public int getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(int type) {
        this.type = type;
    }

    /**
     * @return the sign
     */
    public String getSign() {
        return sign;
    }

    /**
     * @param sign the sign to set
     */
    public void setSign(String sign) {
        this.sign = sign;
    }

    /**
     * @return the value
     */
    public String getValue() {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(String value) {
        this.value = value;
    }
    
}
