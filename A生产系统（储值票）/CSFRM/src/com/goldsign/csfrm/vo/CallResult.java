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
public class CallResult<T> {
    
    //返回代码
    private String code = "";
    
    //调用结果，默认为失败
    private boolean result = false;
    
    //返回信息
    private String msg = "";
    
    //返回结果集
    private List<T> retObjs = new ArrayList<T>();
    
    public CallResult(){}
    
    public CallResult(boolean result){
        this.result = result;
    }
    
    public CallResult(String msg){
        this.msg = msg;
    }
    
    public CallResult(boolean result, String msg){
        this.result = result;
        this.msg = msg;
    }
    /**
     * 设备调用结果
     * @param result 
     */
    public void setResult(boolean result){
        this.result = result;
    }
    
    /**
     * 调用是否成功
     * @return 
     */
    public boolean isSuccess(){
        return this.result;
    }
    
    /**
     * 添加返回结果集
     * @param objs 
     */
    public void setObjs(List<T> objs){
        this.retObjs.addAll(objs);
    }
    
    /**
     * 先删除，后添加返回结果集
     * @param objs 
     */
    public void resetObjs(List<T> objs){
        this.retObjs.clear();
        this.retObjs.addAll(objs);
    }
    
    /**
     * 添加单个返回结果
     * @param obj 
     */
    public void setObj(T obj){
        this.retObjs.add(obj);
    }
    
    /**
     * 先删除，后添加单个返回结果
     * @param obj 
     */
    public void resetObj(T obj){
        this.retObjs.clear();
        this.retObjs.add(obj);
    }
    
    /**
     * 取返回结果集
     * @return 
     */
    public List<T> getObjs(){
        return this.retObjs;
    }
    
    /**
     * 取单个返回结果
     * @return 
     */
    public T getObj(){
        if(this.retObjs.size() > 0){
            return this.retObjs.get(0);
        }else{
            return null;
        }
    }
    
    /**
     * 取单个指定返回结果
     * @return 
     */
    public T getObj(int index){
        if(this.retObjs.size() > index){
            return this.retObjs.get(index);
        }else{
            return null;
        }
    }

    /**
     * 取返回信息
     * @return the msg
     */
    public String getMsg() {
        return msg;
    }

    /**
     * 添回返回信息
     * @param msg the msg to set
     */
    public void setMsg(String msg) {
        this.msg = this.msg+"---"+msg;
    }
    
    /**
     * 设置返回信息
     *
     * @param msg the msg to set
     */
    public void resetMsg(String msg) {
        this.msg = msg;
    }

    /**
     * @return the code
     */
    public String getCode() {
        return code;
    }

    /**
     * @param code the code to set
     */
    public void setCode(String code) {
        this.code = code;
    }
    
}
