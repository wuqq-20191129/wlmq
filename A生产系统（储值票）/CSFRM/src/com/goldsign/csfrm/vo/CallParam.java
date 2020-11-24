/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.csfrm.vo;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author lenovo
 */
public class CallParam<T> {

    //参数
    private List<T> ts = new ArrayList<T>();

    /**
     * 添加多个参数
     * @param params 
     */
    public void setParams(List<T> params){
        this.ts.addAll(params);
    }
    
    /**
     * 先清空，再添加多个参数
     *
     * @param params
     */
    public void resetParams(List<T> params){
        this.ts.clear();
        this.ts.addAll(params);
    }
    
    /**
     * 添加单个参数
     * @param param 
     */
    public void setParam(T param){
        this.ts.add(param);
    }
    
    /**
     * 先清空，再添加单个参数
     * @param param 
     */
    public void resetParam(T param){
        this.ts.clear();
        this.ts.add(param);
    }
    
    /**
     * 获取单个参数
     * @return 
     */
    public T getParam(){
        return this.getParam(0);
    }
    
    /**
     * 获取单个参数
     *
     * @return
     */
    public T getParam(int index){
        if(this.ts.size()>index){
            return this.ts.get(index);
        }else{
            return null;
        }
    }
    
    /**
     * 获取多个参数
     * @return 
     */
    public List<T> getParams(){
        return this.ts;
    }
}
