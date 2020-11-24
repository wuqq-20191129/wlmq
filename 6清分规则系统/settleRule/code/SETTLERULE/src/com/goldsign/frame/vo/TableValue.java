/*
 * 文件名：TableValue
 * 版权：Copyright: goldsign (c) 2013
 */

package com.goldsign.frame.vo;


/*
 * 表 
 * @author     lindaquan
 * @version    V1.0
 * @createTime 2013-8-9
 */

public class TableValue {
    private String value ="";

    public TableValue() {
            super();
            // TODO Auto-generated constructor stub
    }
    public TableValue(String value){
            this.value=value;
    }
    public String getValue() {
            return value;
    }
    public void setValue(String value) {
            this.value = value;
    }
}
