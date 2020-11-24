/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.goldsign.esmcs.vo;

/**
 *
 * @author lenovo
 */
public class BoxInfoVo {
    
    private byte boxStyle;			//票箱形式：0：开放式,1：封闭式							
    private byte boxFullState;		//票箱装票状态：(注：此状态只在运行状态下有效)0：正常,1：将满,2：满
    private byte boxRunState;		/*票箱运行状态：
                                                            0-无票箱
                                                            1-票箱复位
                                                            2-已有票箱未上锁(暂时未用得)
                                                            3-正在安装票箱
                                                            4-票箱已安装，处于运行状态(状态OK)
                                                            5-腾出接票空间(状态OK)
                                                            6-正在拆卸票箱				
                                                            7-票箱错误(有两种，a：复位时传感器条件(即：未在底但票箱锁未归零，b：腾出票箱空间时，超时出错))
                                                            8-拆卸票箱结束
                                                            9-封闭式票箱的票箱盖传感器异常
                                                            10-票箱发生报警
                                                            11-复位时所要执行的条件不满足
                                                    */
    //uchar Box_Run_Mode;			//当前运行模式
    //票箱传感器状态：	
    private byte boxReserve1;		//Bit7-保留，一般填充为0
    private byte boxReserve2;		//Bit6-保留，一般填充为0
    private byte boxGateOpen;		//Bit5-票箱盖打开/关闭(1/0)检测(开放式票箱常1)
    private byte boxKeyOpen;		//Bit4-票箱锁/开(0/1)检测
    private byte boxIsSpace;		//Bit3-票箱接票面空间有/无(0/1)检测
    private byte boxCheckDownUp;            //Bit2-票箱升降安全检测
    private byte boxCheckWillFull;          //Bit1-票箱将满(1)传感器检测
    private byte boxCheckFull;              //Bit0-票箱满(1)检测

    private AlermInfoVo alermInfVo = new AlermInfoVo();                    //票箱报警信息

    /**
     * @return the boxStyle
     */
    public byte getBoxStyle() {
        return boxStyle;
    }

    /**
     * @param boxStyle the boxStyle to set
     */
    public void setBoxStyle(byte boxStyle) {
        this.boxStyle = boxStyle;
    }

    /**
     * @return the boxFullState
     */
    public byte getBoxFullState() {
        return boxFullState;
    }

    /**
     * @param boxFullState the boxFullState to set
     */
    public void setBoxFullState(byte boxFullState) {
        this.boxFullState = boxFullState;
    }

    /**
     * @return the boxRunState
     */
    public byte getBoxRunState() {
        return boxRunState;
    }

    /**
     * @param boxRunState the boxRunState to set
     */
    public void setBoxRunState(byte boxRunState) {
        this.boxRunState = boxRunState;
    }

    /**
     * @return the boxReserve1
     */
    public byte getBoxReserve1() {
        return boxReserve1;
    }

    /**
     * @param boxReserve1 the boxReserve1 to set
     */
    public void setBoxReserve1(byte boxReserve1) {
        this.boxReserve1 = boxReserve1;
    }

    /**
     * @return the boxReserve2
     */
    public byte getBoxReserve2() {
        return boxReserve2;
    }

    /**
     * @param boxReserve2 the boxReserve2 to set
     */
    public void setBoxReserve2(byte boxReserve2) {
        this.boxReserve2 = boxReserve2;
    }

    /**
     * @return the boxGateOpen
     */
    public byte getBoxGateOpen() {
        return boxGateOpen;
    }

    /**
     * @param boxGateOpen the boxGateOpen to set
     */
    public void setBoxGateOpen(byte boxGateOpen) {
        this.boxGateOpen = boxGateOpen;
    }

    /**
     * @return the boxKeyOpen
     */
    public byte getBoxKeyOpen() {
        return boxKeyOpen;
    }

    /**
     * @param boxKeyOpen the boxKeyOpen to set
     */
    public void setBoxKeyOpen(byte boxKeyOpen) {
        this.boxKeyOpen = boxKeyOpen;
    }

    /**
     * @return the boxIsSpace
     */
    public byte getBoxIsSpace() {
        return boxIsSpace;
    }

    /**
     * @param boxIsSpace the boxIsSpace to set
     */
    public void setBoxIsSpace(byte boxIsSpace) {
        this.boxIsSpace = boxIsSpace;
    }

    /**
     * @return the boxCheckDownUp
     */
    public byte getBoxCheckDownUp() {
        return boxCheckDownUp;
    }

    /**
     * @param boxCheckDownUp the boxCheckDownUp to set
     */
    public void setBoxCheckDownUp(byte boxCheckDownUp) {
        this.boxCheckDownUp = boxCheckDownUp;
    }

    /**
     * @return the boxCheckWillFull
     */
    public byte getBoxCheckWillFull() {
        return boxCheckWillFull;
    }

    /**
     * @param boxCheckWillFull the boxCheckWillFull to set
     */
    public void setBoxCheckWillFull(byte boxCheckWillFull) {
        this.boxCheckWillFull = boxCheckWillFull;
    }

    /**
     * @return the boxCheckFull
     */
    public byte getBoxCheckFull() {
        return boxCheckFull;
    }

    /**
     * @param boxCheckFull the boxCheckFull to set
     */
    public void setBoxCheckFull(byte boxCheckFull) {
        this.boxCheckFull = boxCheckFull;
    }

    /**
     * @return the alermInfVo
     */
    public AlermInfoVo getAlermInfVo() {
        return alermInfVo;
    }

    /**
     * @param alermInfVo the alermInfVo to set
     */
    public void setAlermInfVo(AlermInfoVo alermInfVo) {
        this.alermInfVo = alermInfVo;
    }
    
}
