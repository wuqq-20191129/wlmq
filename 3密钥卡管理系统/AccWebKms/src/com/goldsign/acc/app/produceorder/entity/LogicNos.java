package com.goldsign.acc.app.produceorder.entity;

import java.io.Serializable;

/**
 * 逻辑卡号段
 *
 * @author xiaowu 20170828
 */
public class LogicNos implements Serializable {

    private String order_no;            //单号
    private String start_logic_no;      //起始逻辑卡号
    private String end_logic_no;        //结束逻辑卡号

    public String getOrder_no() {
        return order_no;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no;
    }

    public String getStart_logic_no() {
        return start_logic_no;
    }

    public void setStart_logic_no(String start_logic_no) {
        this.start_logic_no = start_logic_no;
    }

    public String getEnd_logic_no() {
        return end_logic_no;
    }

    public void setEnd_logic_no(String end_logic_no) {
        this.end_logic_no = end_logic_no;
    }

}
