/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.goldsign.esmcs.vo;

/**
 *
 * @author lenovo
 */
public class AlermInfoVo {
    
    private byte errorCode;          //错误代码,02-步进电机运行超时报警,03-传感器信号异常
    
    private byte boxStateORSensorNo;	//ErrorCode=02 票箱运行状态, ErrorCode=03错误位置的传感器编号
    
    private byte errorBoxNo;		//发生错误的票箱固定模块号

    /**
     * @return the errorCode
     */
    public byte getErrorCode() {
        return errorCode;
    }

    /**
     * @param errorCode the errorCode to set
     */
    public void setErrorCode(byte errorCode) {
        this.errorCode = errorCode;
    }

    /**
     * @return the boxStateORSensorNo
     */
    public byte getBoxStateORSensorNo() {
        return boxStateORSensorNo;
    }

    /**
     * @param boxStateORSensorNo the boxStateORSensorNo to set
     */
    public void setBoxStateORSensorNo(byte boxStateORSensorNo) {
        this.boxStateORSensorNo = boxStateORSensorNo;
    }

    /**
     * @return the errorBoxNo
     */
    public byte getErrorBoxNo() {
        return errorBoxNo;
    }

    /**
     * @param errorBoxNo the errorBoxNo to set
     */
    public void setErrorBoxNo(byte errorBoxNo) {
        this.errorBoxNo = errorBoxNo;
    }
    
}
