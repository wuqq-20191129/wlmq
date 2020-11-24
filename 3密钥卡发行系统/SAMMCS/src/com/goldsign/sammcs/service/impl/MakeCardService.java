/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.sammcs.service.impl;

import com.goldsign.csfrm.service.impl.BaseService;
import com.goldsign.sammcs.dao.IMakeCardDao;
import com.goldsign.sammcs.dao.impl.MakeCardDao;
import com.goldsign.sammcs.service.IMakeCardService;
import com.goldsign.sammcs.vo.MakeCardQueryVo;
import com.goldsign.sammcs.vo.MakeCardVo;
import com.goldsign.sammcs.vo.SamIssueDetailVo;
import com.goldsign.sammcs.vo.SamOrderPlanVo;
import java.io.File;
import java.io.IOException;
import java.lang.Boolean;
import java.lang.Object;
import java.lang.Override;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Administrator
 */
public class MakeCardService extends BaseService implements IMakeCardService {
    
    private IMakeCardDao makeCardDao = new MakeCardDao();
    
    public  List<Object[]> getMakeCards(MakeCardQueryVo queryVo) {
         List<SamOrderPlanVo> markCardList = makeCardDao.getMakeCards(queryVo);

         List<Object[]> objects = new ArrayList<Object[]>();
         for(SamOrderPlanVo vo : markCardList) {
             //列表顺序
             Object[] obj = new Object[11];
                obj[0] = vo.getOrderNo();
                obj[1] = vo.getSamTypeDesc();
                obj[2] = vo.getStartLogicNo();
                obj[3] = vo.getOrderNum();
                obj[4] = vo.getFinishNum();
                obj[5] = vo.getFinishFlagName();
                obj[6] = vo.getMakeCardOper();
                obj[7] = vo.getMakeCardTime();
                obj[8] = vo.getRemark(); 
                obj[9] = vo.getAuditOrderOper();
                obj[10] = vo.getAuditOrderTime();
                
                objects.add(obj);
         }
         return objects;
        
    }
    
//    public File writeLocalLogFile(OperateLogVo vo) throws IOException {
//        return makeCardDao.writeLocalLogFile(vo);
//    }
    
    @Override
    public File writeLocalFile(MakeCardVo vo, String phyNo, String cardProducerCode) throws IOException {
        return makeCardDao.writeLocalFile(vo, phyNo, cardProducerCode);
    }
    
    
    public Boolean writeMakeCard(MakeCardVo vo, String phyNo, String cardProducerCode, String userId) throws SQLException {
        return makeCardDao.writeMakeCard(vo, phyNo, cardProducerCode, userId);
    }
    
    public boolean checkOrderPlan(String orderNo) {
        return makeCardDao.checkOrderPlan(orderNo);
    }
    
//    public Object[] checkOrderPlanForFinishNum(String orderNo){
//        return makeCardDao.checkOrderPlanForFinishNum(orderNo);
//    }
    
    public String getCurLogicNo(String orderNo) {
        return makeCardDao.getCurLogicNo(orderNo);
    }
    
    public Boolean readLocalFile(File file, String userId) throws IOException,SQLException {
        return makeCardDao.readLocalFile(file, userId);
    }
    
    public List<Object[]> queryIssueDetails(MakeCardQueryVo queryVo) {
        List<SamIssueDetailVo> issueDetailList = makeCardDao.queryIssueDetails(queryVo);

         List<Object[]> objects = new ArrayList<Object[]>();
         for(SamIssueDetailVo vo : issueDetailList) {
             //列表顺序
             Object[] obj = new Object[7];
                obj[0] = vo.getOrderNo();
                obj[1] = vo.getSamTypeDesc();
                obj[2] = vo.getLogicNo();
                obj[3] = vo.getMakeCardOper();
                obj[4] = vo.getMakeCardTime();
                obj[5] = vo.getMakeResultDesc();
                obj[6] = vo.getRemark();
                
                objects.add(obj);
         }
         return objects;
    }
    
    public Boolean issueDetailIsExist(String logicNo) {
        return makeCardDao.issueDetailIsExist(logicNo);
    }
    
    public Object[] getOrderPlanData (String orderNo) {
        
        boolean notAllComplete = false;
        String finishFlag = "";
        String finishNum = "0";
        String samType ="";
        String makeCardOper ="";
        String makeCardTime = "";
        Object[] results = new Object[5]; 
        
        Object[] orderPlans = makeCardDao.getOrderPlanData(orderNo);
        if (orderPlans != null) {
            finishFlag = (String)orderPlans[0]; 
            finishNum = (String)orderPlans[1];
            samType = (String)orderPlans[2];
            makeCardOper = (String)orderPlans[3];
            makeCardTime = (String)orderPlans[4];
            
//            if (!AppConstant.FINISH_FLAG_ALL_COMPLETE.equals(finishFlag)) {
//                notAllComplete = true;
//            } else {
//                notAllComplete = false;
//            }  
        }
        
//        results[0] = notAllComplete;
        results[0] = finishFlag;
        results[1] = finishNum;
        results[2] = samType;
        results[3] = makeCardOper;
        results[4] = makeCardTime;
        
        return results;
        
    }
    
    public Boolean cancelOrder(String orderNo,String userId) throws SQLException {
        return makeCardDao.cancelOrder(orderNo, userId);
    }

    
    
}
