/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.goldsign.esmcs.util;

import com.goldsign.esmcs.dll.structure.*;
import com.goldsign.esmcs.vo.*;

/**
 * 储值票应用-数据转换工具类
 * 
 * @author lenovo
 */
public class PKConverter extends Converter{

    /**
     * LineState转LineStateVo
     * 
     * @param lineState
     * @return 
     */
    public static LineStateVo structureToVo(LineState lineState) {

        LineStateVo lineStateVo = new LineStateVo();

        lineStateVo.setBox1(lineState.Box1);
        lineStateVo.setBox2(lineState.Box2);
        lineStateVo.setBox3(lineState.Box3);
        lineStateVo.setBox4(lineState.Box4);
        lineStateVo.setBox5(lineState.Box5);
        lineStateVo.setBox6(lineState.Box6);
        lineStateVo.setBox7(lineState.Box7);
        lineStateVo.setBox8(lineState.Box8);
        lineStateVo.setBox9(lineState.Box9);
        lineStateVo.setBox10(lineState.Box10);
        lineStateVo.setBox11(lineState.Box11);
        lineStateVo.setBox12(lineState.Box12);
        lineStateVo.setReader1(lineState.Reader1);
        lineStateVo.setReader2(lineState.Reader2);

        return lineStateVo;
    }
        
    /**
     * AlermInf转AlermInfoVo
     * 
     * @param alermInf
     * @return 
     */
    public static AlermInfoVo structureToVo(AlermInf alermInf) {

        AlermInfoVo alermInfoVo = new AlermInfoVo();

        alermInfoVo.setErrorCode(alermInf.ErrorCode);
        alermInfoVo.setBoxStateORSensorNo(alermInf.BoxStateORSensorNo);
        alermInfoVo.setErrorBoxNo(alermInf.ErrorBoxNo);

        return alermInfoVo;
    }
    
    /**
     * BoxInf[]转BoxInfoVo[]
     * 
     * @param boxInfArr
     * @return 
     */
    public static BoxInfoVo[] structureToVo(BoxInf[] boxInfArr){
    
        int len = boxInfArr.length;
        BoxInfoVo[] boxInfoVoArr = new BoxInfoVo[len];
        for(int i=0; i<len; i++){
            boxInfoVoArr[i] = new BoxInfoVo();
            boxInfoVoArr[i].setBoxStyle(boxInfArr[i].Box_Style);
            boxInfoVoArr[i].setBoxFullState(boxInfArr[i].Box_Full_State);
            boxInfoVoArr[i].setBoxRunState(boxInfArr[i].Box_Run_State);
            boxInfoVoArr[i].setBoxReserve1(boxInfArr[i].Box_Reserve1);
            boxInfoVoArr[i].setBoxReserve2(boxInfArr[i].Box_Reserve2);
            boxInfoVoArr[i].setBoxGateOpen(boxInfArr[i].Box_GateOpen);
            boxInfoVoArr[i].setBoxKeyOpen(boxInfArr[i].Box_KeyOpen);
            boxInfoVoArr[i].setBoxIsSpace(boxInfArr[i].Box_IsSpace);
            boxInfoVoArr[i].setBoxCheckDownUp(boxInfArr[i].Box_CheckDownUp);        
            boxInfoVoArr[i].setBoxCheckWillFull(boxInfArr[i].Box_CheckWillFull);
            boxInfoVoArr[i].setBoxCheckFull(boxInfArr[i].Box_CheckFull);
            boxInfoVoArr[i].getAlermInfVo().setErrorCode(boxInfArr[i].Inf.ErrorCode);
            boxInfoVoArr[i].getAlermInfVo().setBoxStateORSensorNo(boxInfArr[i].Inf.BoxStateORSensorNo);
            boxInfoVoArr[i].getAlermInfVo().setErrorBoxNo(boxInfArr[i].Inf.ErrorBoxNo); 
        }
        
        return boxInfoVoArr;
    }
 
    /**
     * BoxSensor[]BoxSensorVo[]
     *
     * @param boxSensorArr
     * @return
     */
    public static BoxSensorVo[] structureToVo(BoxSensor[] boxSensorArr){
    
        int len = boxSensorArr.length;
        BoxSensorVo[] boxSensorVoArr = new BoxSensorVo[len];
        for(int i=0; i<len; i++){
            boxSensorVoArr[i] = new BoxSensorVo();
            boxSensorVoArr[i].setS1(boxSensorArr[i].S1);
            boxSensorVoArr[i].setS2(boxSensorArr[i].S2);
            boxSensorVoArr[i].setS3(boxSensorArr[i].S3);
            boxSensorVoArr[i].setS4(boxSensorArr[i].S4);
            boxSensorVoArr[i].setS5(boxSensorArr[i].S5);
            boxSensorVoArr[i].setS6(boxSensorArr[i].S6);
        }
        
        return boxSensorVoArr;
    }
    
    /**
     * CardInf[]转CardInfoVo[]
     * 
     * @param cardInfArr
     * @return 
     */
    public static CardInfoVo[] structureToVo(CardInf[] cardInfArr){
    
        int len = cardInfArr.length;
        CardInfoVo[] cardInfoVoArr = new CardInfoVo[len];
        for(int i=0; i<len; i++){
            cardInfoVoArr[i] = new CardInfoVo();
            cardInfoVoArr[i].setCurrSite(cardInfArr[i].CurrSite);
            cardInfoVoArr[i].setTagSite(cardInfArr[i].TagSite);
            cardInfoVoArr[i].setCardNo(cardInfArr[i].CardNo);
            cardInfoVoArr[i].setState(cardInfArr[i].State);
            cardInfoVoArr[i].setRecyBox(cardInfArr[i].RecyBox);
            
        }
        
        return cardInfoVoArr;
    }
    
    /**
     * 取得通道错误代码描述
     * 
     * @param errCode
     * @return 
     */
    public static String getChannelErrorCodeDes(int errCode) {
        
        String errorCodeDes = "";
        switch (errCode) {
            case -1:
                errorCodeDes = "打开串口失败";
                break;
            case -2:
                errorCodeDes = "读串口数据出错";
                break;
            case -3:
                errorCodeDes = "写串口数据出错";
                break;
            case -4:
                errorCodeDes = "未与发卡机建立连接(串口没有打开)";
                break;
            case -5:
                errorCodeDes = "发送命令失败";
                break;
            case -6:
                errorCodeDes = "校验错误";
                break;
            case -7:
                errorCodeDes = "读串口数据超时";
                break;
            case -8:
                errorCodeDes = "与发卡机握手失败";
                break;
            case -9:
                errorCodeDes = "回收卡片失败";
                break;
            case -10:
                errorCodeDes = "未知错误的命令";
                break;
            case -11:
                errorCodeDes = "错误的回收票箱号";
                break;
            /*case -12:
                errorCodeDes = "发卡箱无卡";
                break;*/
            case -13:
                errorCodeDes = "线上无卡";
                break;
            case -14:
                errorCodeDes = "故障超时";
                break;
            case 1:
                errorCodeDes = "步进电机不在零位";
                break;
            case 2:
                errorCodeDes = "出卡位有卡";
                break;
            case 3:
                errorCodeDes = "步进电机不在零位且出卡位有卡";
                break;
            case 10:
                errorCodeDes = "机器处在暂停状态";
                break;
            case -15:
                errorCodeDes = "接收到错误的命令";
                break;
            case -16:
                errorCodeDes = "钩电机堵转";
                break;
            case -17:
                errorCodeDes = "发卡电机堵转";
                break;
            case -18:
                errorCodeDes = "步进电机堵转";
                break;
            case -19:
                errorCodeDes = "发卡箱空";
                break;
            case -20:
                errorCodeDes = "重复出卡";
                break;
            case -21:
                errorCodeDes = "步进电机运行中不能发卡";
                break;
            case -22:
                errorCodeDes = "卡运行错误";
                break;
            case -23:
                errorCodeDes = "无卡回收";
                break;
            case -24:
                errorCodeDes = "收卡超时";
                break;
            case -25:
                errorCodeDes = "发卡超时";
                break;
            case -26:
                errorCodeDes = "线上无卡";
                break;
            case -27:
                errorCodeDes = " 错误的当前位置";
                break;
            case -28:
                errorCodeDes = "错误的目标工位";
                break;
            case -31:
                errorCodeDes = "1号收卡箱收卡出错";
                break;
            case -32:
                errorCodeDes = "2号收卡箱收卡出错";
                break;
            case -33:
                errorCodeDes = "3号收卡箱收卡出错";
                break;
            case -34:
                errorCodeDes = "4号收卡箱收卡出错";
                break;
            case -35:
                errorCodeDes = "5号收卡箱收卡出错";
                break;
            case -36:
                errorCodeDes = "6号收卡箱收卡出错";
                break;
            case -37:
                errorCodeDes = "7号收卡箱收卡出错";
                break;
            case -38:
                errorCodeDes = "8号收卡箱收卡出错";
                break;
            case -39:
                errorCodeDes = "9号收卡箱收卡出错";
                break;
            case -40:
                errorCodeDes = "10号收卡箱收卡出错";
                break;
            case -41:
                errorCodeDes = "11号收卡箱收卡出错";
                break;
            case -42:
                errorCodeDes = "12号收卡箱收卡出错";
                break;
            case -100:
                errorCodeDes = "未知错误";
                break;
            default:
                errorCodeDes = "其它错误:"+errCode;
                break;
        }
        return errorCodeDes;
    }
    
    /**
     * 取得票箱错误代码描述
     * 
     * @param errCode
     * @return 
     */
    public static String getBoxErrorCodeDes(int errCode) {
        String errorCodeDes = "";
        switch (errCode) {
            case -1:
                errorCodeDes = "打开串口失败";
                break;
            case -2:
                errorCodeDes = "读串口数据出错";
                break;
            case -3:
                errorCodeDes = "写串口数据出错";
                break;
            case -4:
                errorCodeDes = "未与发卡机建立连接(串口没有打开)";
                break;
            case -5:
                errorCodeDes = "发送命令失败";
                break;
            case -6:
                errorCodeDes = "校验错误";
                break;
            case -7:
                errorCodeDes = "读串口数据超时";
                break;
            case -8:
                errorCodeDes = "与发卡机握手失败";
                break;
            case -9:
                errorCodeDes = "接收到错误的命令";
                break;
            case -10:
                errorCodeDes = "压卡超时";
                break;
            case -11:
                errorCodeDes = "压卡防反传感器";
                break;
            case -100:
                errorCodeDes = "未知错误";
                break;
            case 0:
                errorCodeDes = "运行模式";
                break;
            case 1:
                errorCodeDes = "复位模式";
                break;
            case 2:
                errorCodeDes = "报警模式";
                break;
            case 3:
                errorCodeDes = "测试模式";
                break;
            case 4:
                errorCodeDes = "打开串口出错";
                break;
            case 5:
                errorCodeDes = "错误的卡箱号";
                break;
            case 6:
                errorCodeDes = "发送命令出错或者未收到应答命令";
                break;
            case 7:
                errorCodeDes = "接收到错误命令";
                break;
            case 8:
                errorCodeDes = "接收到未知命令";
                break;
            default:
                errorCodeDes = "其它错误:"+errCode;
                break;

        }
        return errorCodeDes;
    }

    /**
     * 取得线上错误
     * 
     * @param lineErrState
     * @return 
     */
    public static String getLineStateError(LineStateVo lineErrState) {

        StringBuffer errinfo = new StringBuffer();
        ////0	000	正常
        //1	001	误收
        //2	010	越位
        //3	011	收脱钩
        //4	100	走卡脱钩
        //5	101	越位脱钩
        String[] vars = new String[]{"读写器1", "读写器2", "票箱1", "票箱2", "票箱3", "票箱4", 
            "票箱5", "票箱6", "票箱7", "票箱8", "票箱9", "票箱10", "票箱11", "票箱12"};
        int[] values = new int[]{lineErrState.getReader1(), lineErrState.getReader2(), 
            lineErrState.getBox1(),lineErrState.getBox2(), lineErrState.getBox3(), lineErrState.getBox4(), 
            lineErrState.getBox5(),lineErrState.getBox6(), lineErrState.getBox7(), lineErrState.getBox8(), 
            lineErrState.getBox9(),lineErrState.getBox10(), lineErrState.getBox11(), lineErrState.getBox12()};
        int len = vars.length;
        for(int i=0; i<len; i++){
            String var = vars[i];
            int value = values[i];
            if (value == 1) {
                errinfo.append(var+"位置卡片运行状态为误收;");
            } else if (value == 2) {
                errinfo.append("读写器1位置卡片运行状态为越位;");
            } else if (value == 3) {
                errinfo.append(var+"位置卡片运行状态为收脱钩;");
            }else if (value == 4) {
                errinfo.append(var+"位置卡片运行状态为走卡脱钩;");
            }else if (value == 5) {
                errinfo.append(var+"位置卡片运行状态为走越位脱钩;");
            }
        }
        
        return errinfo.toString();
    }
}
