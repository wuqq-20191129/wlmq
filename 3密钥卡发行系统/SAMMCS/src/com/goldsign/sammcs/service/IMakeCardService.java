/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.sammcs.service;

import com.goldsign.csfrm.service.IBaseService;
import com.goldsign.sammcs.vo.MakeCardQueryVo;
import com.goldsign.sammcs.vo.MakeCardVo;
import com.goldsign.sammcs.vo.OperateLogVo;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Administrator
 */
public interface IMakeCardService extends IBaseService{
    
    public  List<Object[]> getMakeCards(MakeCardQueryVo queryVo);
    
    //写日志文件
//    public File writeLocalLogFile(OperateLogVo vo) throws IOException;
    
    //插入数据库异常时，写到本地文件
    public File writeLocalFile(MakeCardVo vo,String phyNo, String cardProducerCode) throws IOException;
    
    public Boolean writeMakeCard(MakeCardVo vo,String phyNo, String cardProducerCode, String userId) throws SQLException;
    
    public boolean checkOrderPlan(String orderNo);
    
//    public Object[] checkOrderPlanForFinishNum(String orderNo);
    
    public String getCurLogicNo(String orderNo);
    
    //读本地文件
    public Boolean readLocalFile(File file, String userId) throws IOException,SQLException;
    
    public List<Object[]> queryIssueDetails(MakeCardQueryVo queryVo);
    
    public Boolean issueDetailIsExist(String logicNo);
    
    public Object[] getOrderPlanData (String orderNo);
    
    public Boolean cancelOrder(String orderNo, String userId) throws SQLException;
    
}
