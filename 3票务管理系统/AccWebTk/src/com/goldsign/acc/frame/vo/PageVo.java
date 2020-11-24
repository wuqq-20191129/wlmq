/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.frame.vo;

/**
 *
 * @author hejj
 */
public class PageVo {
     private String back = "0";
    private String backEnd = "0";
    private String next = "0";
    private String nextEnd = "0";
    private String current = "0";
    private String total = "0";
    private String totalRecords = "0";
    private String currentRecords = "0";

    public String getCurrentRecords() {
        return currentRecords;
    }

    public void setCurrentRecords(String currentRecords) {
        this.currentRecords = currentRecords;
    }

    public String getTotalRecords() {
        return totalRecords;
    }

    public void setTotalRecords(String totalRecords) {
        this.totalRecords = totalRecords;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getCurrent() {
        return current;
    }

    public void setCurrent(String current) {
        this.current = current;
    }

    public PageVo() {
        super();
        // TODO Auto-generated constructor stub
    }

    public String getBack() {
        return back;
    }

    public void setBack(String back) {
        this.back = back;
    }

    public String getBackEnd() {
        return backEnd;
    }

    public void setBackEnd(String backEnd) {
        this.backEnd = backEnd;
    }

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }

    public String getNextEnd() {
        return nextEnd;
    }

    public void setNextEnd(String nextEnd) {
        this.nextEnd = nextEnd;
    }
    
}
