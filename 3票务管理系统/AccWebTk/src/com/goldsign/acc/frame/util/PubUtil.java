/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.frame.util;

import com.goldsign.acc.app.storagein.entity.TicketStorageInBaseBill;
import com.goldsign.acc.app.storagein.entity.TicketStorageInBill;
import com.goldsign.acc.frame.constant.InOutConstant;
import com.goldsign.acc.frame.entity.PubFlag;
import com.goldsign.lib.db.util.DbHelper;
import java.math.BigInteger;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionStatus;

/**
 *
 * @author hejj
 */
public class PubUtil {

    public static void handExceptionForTran(Exception e, DataSourceTransactionManager txMgr, TransactionStatus status) throws Exception {
        if (txMgr != null) {
            txMgr.rollback(status);
        }
        throw e;

    }

    public static void finalProcess(DbHelper dbHelper, Logger logger) {
        try {
            if (dbHelper != null) {
                dbHelper.closeConnection();
            }
        } catch (SQLException e) {
            logger.error("关闭连接错误", e);
        }
    }

    public static void finalProcessForTran(DbHelper dbHelper, Logger logger) {
        try {

            if (dbHelper != null) {
                dbHelper.closeConnection();
            }

        } catch (SQLException e) {
            logger.error("\"关闭连接错误", e);
        }
    }
    
    public static int calculateSection(String startLogicalId,
            String endLogicalId, String icMainType, String icSubType)
            throws Exception {
        String startLogicalId1 = PubUtil.getLogicalIdEffective(startLogicalId,
                icMainType, icSubType);
        String endLogicalId1 = PubUtil.getLogicalIdEffective(endLogicalId,
                icMainType, icSubType);
        BigInteger n = new BigInteger(startLogicalId1);
        BigInteger n1 = new BigInteger(endLogicalId1);
        BigInteger n2 = n1.subtract(n);
        return n2.intValue() + 1;

    }
    
    public static String getLogicalIdEffective(String logicalId,
            String icMainType, String icSubType) throws Exception {
        if (logicalId == null || logicalId.length() == 0) {
            throw new Exception("逻辑卡号为空不合法");
        }
        // 单乘票、储值票、员工票有效位为20
		/*
         * if (PubUtil.isCardForSJT(icMainType) ||
         * PubUtil.isCardForSVT(icMainType) )
         */
        if (PubUtil.isCardForLogicalEffect(icMainType)) {
            if (logicalId.length() != 20) {
                throw new Exception(logicalId + "长度不为20位");
            }
            return logicalId.substring(0, InOutConstant.LEN_LOGICAL_EFFECTIVE);

        }
        // 发票
        // if (PubUtil.isCardForBill(icMainType)||
        // PubUtil.isCardForOCT(icMainType))
        // return logicalId;
        return logicalId;

    }
    
     /*
     * public static String getLogicalIdEffective(String logicalId) throws
     * Exception{ if(logicalId ==null || logicalId.length()==0) throw new
     * Exception("逻辑卡号为空不合法"); if(logicalId.length() !=16) throw new
     * Exception(logicalId+"长度不为16位"); return logicalId.substring(0,15); }
     */
    /*
     * public static boolean isCardForSJT(String icMainType) { if (icMainType ==
     * null || icMainType.length() == 0) return false; if
     * (icMainType.equals(InOutConstant.IC_CARD_TYPE_SJT)) return true; return
     * false; }
     */
    public static boolean isCardForLogicalEffect(String icMainType) {
        if (icMainType == null || icMainType.length() == 0) {
            return false;
        }
        for (int i = 0; i < InOutConstant.IC_CARD_TYPE_LOGICAL_EFFECT.length; i++) {
            if (icMainType.equals(InOutConstant.IC_CARD_TYPE_LOGICAL_EFFECT[i])) {
                return true;
            }
        }

        return false;

    }
    
    public static boolean isNeedDetailPlace(String areaId, String icMainType) {
        if (areaId == null || areaId.length() == 0) {
            return false;
        }
        if (!areaId.equals(InOutConstant.AREA_ENCODE)
                && !areaId.equals(InOutConstant.AREA_VALUE)) {
            return false;
        }
        if (icMainType.equals(InOutConstant.IC_MAIN_TYPE_SJT)
                && areaId.equals(InOutConstant.AREA_ENCODE))// 单程票从编码区

        {
            return false;
        }
        // 单程票付值编码区、储值票从编码区及赋值区
        return true;

    }
    public static String addLogicalIdEffective(String logicalId, String addNum)
            throws Exception {

        int len = logicalId.length();
        BigInteger n1 = new BigInteger(logicalId);
        BigInteger n2 = new BigInteger(addNum);
        BigInteger n3 = n1.add(n2);
        String sn3 = NumberUtil.formatNumber(n3, len);
        return sn3;

    }
    
    public static List sortPubFlagList(List pfList){
//	List<PubFlag> pfList = new ArrayList<PubFlag>();
	Collections.sort(pfList,new Comparator<PubFlag>(){  
		public int compare(PubFlag o1, PubFlag o2){ 
			if(Integer.parseInt(o1.getCode())> Integer.parseInt(o2.getCode())){  
				return 1;        
			}  
			if(Integer.parseInt(o1.getCode()) == Integer.parseInt(o2.getCode())){  
				return 0;  
			}  
				return -1;  
			}  
		});  
	return pfList;
    }
    
    /**
     * 按单据状态（未审核、审核）、单号（倒序）排序
     * @param sortList
     * @return 
     */
    public static List sortInBillList(List inBaseBillList){
	Collections.sort(inBaseBillList,new Comparator<TicketStorageInBaseBill>(){  
		public int compare(TicketStorageInBaseBill o1, TicketStorageInBaseBill o2){ 
                        int sortRecordFlag = o1.getRecord_flag().compareTo(o2.getRecord_flag());
                        if (sortRecordFlag == 0) {
                            return (-1)*o1.getBill_no().compareTo(o2.getBill_no());
                        } else {
                            return (-1)*sortRecordFlag;
                        }
                }  
		
        });  
	return inBaseBillList;
    }

}
