/*
 * 文件名：PhyLogicCompDao
 * 版权：Copyright: goldsign (c) 2013
 */

package com.goldsign.escommu.dao;

import com.goldsign.escommu.dbutil.DbHelper;
import com.goldsign.escommu.env.AppConstant;
import com.goldsign.escommu.util.PubUtil;
import com.goldsign.escommu.util.StringUtil;
import com.goldsign.escommu.vo.PhyLogicVo;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;


/*
 * 物理卡号与逻辑卡号对照表
 * @author     lindaquan
 * @version    V1.0
 * @createTime 2013-9-11
 */

public class PhyLogicCompDao {
    
    private static Logger logger = Logger.getLogger(PhyLogicCompDao.class.getName());
    
    /**
     * 查询
     * 
     * @return
     * @throws Exception 
     */
    public List<PhyLogicVo> getPhyLogicList() throws Exception {
        boolean result = false;
        DbHelper dbHelper = null;
        Object[] values = {};
                    
        List<PhyLogicVo> orderTypeRspVos = new ArrayList<PhyLogicVo>();
        PhyLogicVo phyLogicVo = null;
        try {
            dbHelper = new DbHelper("", AppConstant.DATA_DBCPHELPER.getConnection());
            //********************************************
            String sqlStr = "SELECT t.physic_no,t.logic_no FROM "+AppConstant.COM_ST_P+"op_prm_phy_logic_list t";

            result = dbHelper.getFirstDocument(sqlStr, values);
            while (result) {
                phyLogicVo = getResultRecord(dbHelper);
                orderTypeRspVos.add(phyLogicVo);
                
                result = dbHelper.getNextDocument();
            }
        } catch (Exception e) {
            PubUtil.handleException(e, logger);
        } finally {
            PubUtil.finalProcess(dbHelper);
        }

        return orderTypeRspVos;
    }
    
    /**
     * 返回实体
     * 
     * @param dbHelper
     * @return
     * @throws Exception 
     */
    private PhyLogicVo getResultRecord(DbHelper dbHelper) throws Exception{
        
        PhyLogicVo vo = new PhyLogicVo();
        String logicNo = dbHelper.getItemValue("logic_no");
        String phyNo = dbHelper.getItemValue("physic_no");
        vo.setLogicNo(StringUtil.fmtStrLeftLen(logicNo, 20, " "));
        vo.setPhysicNo(StringUtil.fmtStrRightLen(phyNo, 20, " "));

        return vo;
    }
    
    /**
     * 删除
     * 
     * @return
     * @throws Exception 
     */
    public int deletePhyLogicList() throws Exception {
        int result = 0;
        DbHelper dbHelper = null;
                    
        try {
            dbHelper = new DbHelper("", AppConstant.DATA_DBCPHELPER.getConnection());
            String sqlStr = "delete FROM "+AppConstant.COM_ST_P+"op_prm_phy_logic_list t";

            result = dbHelper.executeUpdate(sqlStr);
            
        } catch (Exception e) {
            PubUtil.handleException(e, logger);
        } finally {
            PubUtil.finalProcess(dbHelper);
        }
        return result;
    }

}
