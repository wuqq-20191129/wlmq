/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.sammcs.dao;

import com.goldsign.csfrm.dao.IBaseDao;
import com.goldsign.sammcs.vo.MakeCardQueryVo;
import com.goldsign.sammcs.vo.MakeCardVo;
import com.goldsign.sammcs.vo.OperateLogVo;
import com.goldsign.sammcs.vo.SamIssueDetailVo;
import com.goldsign.sammcs.vo.SamOrderPlanVo;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Administrator
 */
public interface IMakeCardDao  extends IBaseDao{
    
    public List<SamOrderPlanVo> getMakeCards(MakeCardQueryVo queryVo);
    
    //写日志文件
//    public File writeLocalLogFile(OperateLogVo vo) throws IOException;
    
    //写本地文件
    public File writeLocalFile(MakeCardVo vo, String phyNo, String cardProducerCode) throws IOException;
    
    public Boolean writeMakeCard(MakeCardVo vo, String phyNo, String cardProducerCode, String userId) throws SQLException;
    
    public boolean checkOrderPlan(String orderNo);
    
    public String getCurLogicNo(String orderNo);
    
    //读本地文件
    public Boolean readLocalFile(File file, String userId) throws IOException,SQLException;
    
    public List<SamIssueDetailVo> queryIssueDetails(MakeCardQueryVo queryVo);
    
    public Boolean issueDetailIsExist(String logicNo);
    
    public Object[] getOrderPlanData(String orderNo);
    
    public Boolean cancelOrder(String orderNo, String userId) throws SQLException;
    
    //检测设备配置是否正确
    public Boolean isDeviceConfigureRight(String ip, String deviceType, String deviceNo);
}
