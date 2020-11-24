/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.goldsign.esmcs.vo;

import com.goldsign.csfrm.util.StringUtil;
import com.goldsign.esmcs.env.AppConstant;
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JProgressBar;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import org.apache.log4j.Logger;

/**
 *
 * @author lenovo
 */
public class BoxStateVo {
    
    private static Logger logger = Logger.getLogger(BoxStateVo.class.getName());
    
    public int boxNo;
    
    public JRadioButton boxTypeNormalRB;
    
    public JRadioButton boxTypeInvalRB;
    
    public JTextField boxTotalNumTxt;
    
    public JTextField boxCurrentNumTxt;
    
    public JProgressBar boxStatusPB;
    
    public JButton boxResetBtn;
    
    public JButton boxUnloadBtn;
    
    public BoxStateVo(int boxNo, JRadioButton boxTypeNormalRB, JRadioButton boxTypeInvalRB,
            JTextField boxTotalNumTxt, JTextField boxCurrentNumTxt, JProgressBar boxStatusPB,
            JButton boxResetBtn, JButton boxUnloadBtn){
        this.boxNo = boxNo;
        this.boxTypeNormalRB = boxTypeNormalRB;
        this.boxTypeInvalRB = boxTypeInvalRB;
        this.boxTotalNumTxt = boxTotalNumTxt;
        this.boxCurrentNumTxt = boxCurrentNumTxt;
        this.boxStatusPB = boxStatusPB;
        this.boxResetBtn = boxResetBtn;
        this.boxUnloadBtn = boxUnloadBtn;
    }
    
    /**
     * 是否选择了本票箱
     * @return 
     */
    public boolean isStart(){
        if(boxTypeNormalRB.isSelected() || boxTypeInvalRB.isSelected()){
            return true;
        }else{
            return false;
        }
    }
    
    /**
     * 是否正常票箱
     * @return 
     */
    public boolean isNormal(){
        if(boxTypeNormalRB.isSelected()){
            return true;
        }else{
            return false;
        }
    }
    
    /**
     * 是否废票箱
     * @return 
     */
    public boolean isInval(){
        if(boxTypeInvalRB.isSelected()){
            return true;
        }else{
            return false;
        }
    }

    /**
     * 取得票箱类型， 正常、废票、未设置
     * @return the boxType
     */
    public int getBoxType() {
        if(isNormal()){
            return AppConstant.BOX_TYPE_NORMAL;
        }
        if(isInval()){
            return AppConstant.BOX_TYPE_INVAL;
        }
        return AppConstant.BOX_TYPE_NOSEL;
    }
    
    /**
     * 当前票箱是否已满
     * @return 
     */
    public boolean isFull(){
    
        logger.info("票箱："+boxNo+",当前数量:"+getBoxCurrentNum()+",总数量:"+getBoxTotalNum());
        return getBoxCurrentNum() >= getBoxTotalNum();
    }
    
    /**
     * 当前票箱是否将满
     * @return 
     */
    public boolean isFulling(){
        
        return getBoxTotalNum() - getBoxCurrentNum() <= AppConstant.BOX_FULLING_WARN_NUM;
    }

    /**
     * 取得票箱总数量
     * @return the boxTotalNum
     */
    public int getBoxTotalNum() {
        return StringUtil.getInt(boxTotalNumTxt.getText());
    }

    /**
     * 取得当前票箱数量
     * @return the boxCurrentNum
     */
    public int getBoxCurrentNum() {
        synchronized(this.boxCurrentNumTxt){
            return StringUtil.getInt(boxCurrentNumTxt.getText());
        }
    }

    /**
     * 设置当前票箱数量
     * @param boxCurrentNum 
     */
    public void setBoxCurrentNum(int boxCurrentNum){
        synchronized(this.boxCurrentNumTxt){
            boxCurrentNumTxt.setText(boxCurrentNum+"");
            setBoxStatus();
        }
    }
    
    /**
     * 设置当前票箱状态（进度条）
     */
    private void setBoxStatus(){
        int boxCurrentNum = getBoxCurrentNum();
        int boxTotalNum = getBoxTotalNum();
        double value = (boxCurrentNum*1.0/boxTotalNum)*100;
        this.boxStatusPB.setValue((int)value);
        if(isFull()){
            this.boxStatusPB.setForeground(Color.red);
        }else if(isFulling()){
            this.boxStatusPB.setForeground(Color.yellow);
        }else{
            this.boxStatusPB.setForeground(Color.green);
        }
        this.boxStatusPB.setString((int)value+"%");
    }

    /**
     * 取得当前票箱状态（进度条值）
     * @return the boxStatus
     */
    public int getBoxStatus() {
        return boxStatusPB.getValue();
    }

}
