/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.fm.dao;

import com.goldsign.fm.common.AppConstant;
import com.goldsign.fm.common.PubUtil;
import com.goldsign.fm.vo.ViewVo;
import com.goldsign.lib.db.util.DbHelper;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import org.apache.log4j.Logger;

/**
 * 票卡类型客流
 * @author Administrator
 */
public class TrafficCardTypeDao {

    private Logger logger = Logger.getLogger(TrafficCardTypeDao.class.getName());

    public Map<String, Vector> getTrafficForCardType(String clickNode, String currentTrafficDate) throws Exception {
        boolean result = false;
        Map<String, Vector> map = new HashMap<String, Vector>();
        Vector inMainList = new Vector();
        Vector outMainList = new Vector();
        Vector inSubList = new Vector();
        Vector outSubList = new Vector();
        String strSql = "";
        DbHelper dbHelper = null;
       
        try {
            dbHelper = new DbHelper("",AppConstant.dbcpHelper2.getConnection());
            strSql = "{call UP_FM_FLOW_TYPE_TOTAL (?,?,?,?) }";
            int[] pInIndexes = {1,2,3};//存储过程输入参数索引列表
            Object[] pInStmtValues = {clickNode, currentTrafficDate,AppConstant.CARD_MAIN};//存储过程输入参数值

            int[] pOutIndexes = {4};//存储过程输出参数索引列表
            int[] pOutTypes = {DbHelper.PARAM_OUT_TYPE_CURSOR};//存储过程输出参数值类型

            dbHelper.runStoreProcForOracle(strSql, pInIndexes, pInStmtValues, pOutIndexes, pOutTypes);//执行存储过程
            result = dbHelper.getFirstDocumentForOracle();//判断结果集是否为空
            //主类型
            while (result) {
                ViewVo vo = this.getListItem(dbHelper, AppConstant.CARD_MAIN);
                if(vo.getTraffic_in().equals("")){
                    outMainList.add(vo);
                }else{
                    inMainList.add(vo);
                }
                result = dbHelper.getNextDocument();
            }
            dbHelper.close();
            
            //子类型
            Object[] pInStmtValuesSub = {clickNode, currentTrafficDate,AppConstant.CARD_SUB};//存储过程输入参数值
            dbHelper.runStoreProcForOracle(strSql, pInIndexes, pInStmtValuesSub, pOutIndexes, pOutTypes);//执行存储过程
            result = dbHelper.getFirstDocumentForOracle();//判断结果集是否为空
            
            while(result){
                ViewVo vo = this.getListItem(dbHelper, AppConstant.CARD_SUB);
                if(vo.getTraffic_in().equals("")){
                    outSubList.add(vo);
                }else{
                    inSubList.add(vo);
                }
                result = dbHelper.getNextDocument();
            }
           
        } catch (SQLException e) {
            PubUtil.handleException(e, logger);
        } finally {
            PubUtil.finalProcess(dbHelper);
        }

        map.put(AppConstant.CARD_IN_MAIN, inMainList);
        map.put(AppConstant.CARD_OUT_MAIN, outMainList);
        map.put(AppConstant.CARD_IN_SUB, inSubList);
        map.put(AppConstant.CARD_OUT_SUB, outSubList);
        return map;
    }

    private ViewVo getListItem(DbHelper dbHelper, String type) throws Exception {
        ViewVo vo = new ViewVo();
        String trafficIn = "";
        String trafficOut = "";
        String trafficInName = "";
        String trafficOutName = "";
        String mainCardID ="";
        String mainCardName ="";
        String subCardID = "";
        String subCardName= "";
        String flag= "";
        flag = dbHelper.getItemValue("flag");
        
        //主类型
        if(type.equals(AppConstant.CARD_MAIN)){
            mainCardID = dbHelper.getItemValue("card_main_type");
            mainCardName = dbHelper.getItemValue("card_main_name");
        }
        //子类型
        if(type.equals(AppConstant.CARD_SUB)){
            mainCardID = dbHelper.getItemValue("card_main_type");
            subCardID = dbHelper.getItemValue("card_sub_type");
            subCardName = dbHelper.getItemValue("card_sub_name");
        }
        //出站
        if(flag.equals(AppConstant.FLAG_OUT)){
            trafficOut = getTrafficValue(dbHelper,"traffic");
        }
        //入站
        if(flag.equals(AppConstant.FLAG_IN)){
            trafficIn = getTrafficValue(dbHelper,"traffic");
        }

        trafficInName = AppConstant.SCREEN_CAPTION_IN;
        trafficOutName = AppConstant.SCREEN_CAPTION_OUT;

        vo.setTraffic_in(trafficIn);
        vo.setTraffic_in_name(trafficInName);
        vo.setTraffic_out(trafficOut);
        vo.setTraffic_out_name(trafficOutName);
        vo.setMainCardID(mainCardID);
        vo.setMainCardName(mainCardName);
        vo.setSubCardID(subCardID);
        vo.setSubCardName(subCardName);
        
        return vo;

    }

    private String getTrafficValue(DbHelper dbHelper, String fieldName) throws
            SQLException {
        String result = dbHelper.getItemValue(fieldName);
        if (result == null || result.length() == 0) {
            result = "0";
        }
        return result;

    }
}
