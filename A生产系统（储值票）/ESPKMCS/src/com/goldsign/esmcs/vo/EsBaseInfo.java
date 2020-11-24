package com.goldsign.esmcs.vo;

import com.goldsign.esmcs.dll.structure.*;
import com.goldsign.esmcs.env.PKAppConstant;

/**
 *
 * @author lenovo
 */
public class EsBaseInfo {

    private byte nextNormalBoxNo=1;
    
    private byte nextInvalBoxNo=1;

    public CardInf[] cardInfs;
    
    public BoxInf[] boxInfs;
    
    public BoxSensor[] boxSensors;
    
    public short[] boxIsCards = new short[10];
    
    public AlermInf.ByReference alermInf = new AlermInf.ByReference();
    
    public LineState.ByReference lineState = new LineState.ByReference();
    
    /**
     * 设置下一个回收正常票箱号
     * @param boxNo 
     */
    private void setNextNormalBoxNo(byte boxNo){
        if(boxNo > PKAppConstant.ES_BOX_NUM){
            nextNormalBoxNo = 1;
        }else{
            nextNormalBoxNo = boxNo;
        }
    }
    
    /**
     * 设置下一个回收废票箱号
     * @param boxNo 
     */
    private void setNextInvalBoxNo(byte boxNo){
        if(boxNo > PKAppConstant.ES_BOX_NUM){
            nextInvalBoxNo = 1;
        }else{
            nextInvalBoxNo = boxNo;
        }
    }
    
    /**
     * 取得下一个回收正常票箱号
     * @param curBoxStateVos
     * @return 
     */
    public byte getNextNormalBoxNo(BoxStateVo[] curBoxStateVos) {
        
        int len = curBoxStateVos.length;
        for(byte i=nextNormalBoxNo; i<=len; i++){
            BoxStateVo boxStateVo = curBoxStateVos[i-1];
            if(!boxStateVo.isNormal()){
                continue;
            }
            if(boxStateVo.isFull()){
                continue;
            }
            this.setNextNormalBoxNo((byte)(i+1));
            return i;
        }

        for(byte i=1; i<=nextNormalBoxNo; i++){
            BoxStateVo boxStateVo = curBoxStateVos[i-1];
            if(!boxStateVo.isNormal()){
                continue;
            }
            if(boxStateVo.isFull()){
                continue;
            }
            this.setNextNormalBoxNo((byte)(i+1));
            return i;
        }
        
        //return PKAppConstant.BOX_NORMAL_DEFAULT_NO;
        return PKAppConstant.BOX_INVAL_DEFAULT_NO;
    }
    
    /**
     * 取得下一个回收废票箱号
     * @param curBoxStateVos
     * @return 
     */
    public byte getNextInvalBoxNo(BoxStateVo[] curBoxStateVos) {
        
        int len = curBoxStateVos.length;
        for(byte i=nextInvalBoxNo; i<=len; i++){
            BoxStateVo boxStateVo = curBoxStateVos[i-1];
            if(!boxStateVo.isInval()){
                continue;
            }
            if(boxStateVo.isFull()){
                continue;
            }
            this.setNextInvalBoxNo((byte)(i+1));
            return i;
        }

        for(byte i=1; i<=nextInvalBoxNo; i++){
            BoxStateVo boxStateVo = curBoxStateVos[i-1];
            if(!boxStateVo.isInval()){
                continue;
            }
            if(boxStateVo.isFull()){
                continue;
            }
            this.setNextInvalBoxNo((byte)(i+1));
            return i;
        }
        
        return PKAppConstant.BOX_INVAL_DEFAULT_NO;
    }

}
