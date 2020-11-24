/*
 * 文件名：ParamDao
 * 版权：Copyright: goldsign (c) 2013
 */

package com.goldsign.ecpmcs.dao.impl;

import com.goldsign.csfrm.dao.impl.BaseDao;
import com.goldsign.csfrm.util.StringUtil;
import com.goldsign.lib.db.util.DbHelper;
import com.goldsign.ecpmcs.dao.IParamDao;
import com.goldsign.ecpmcs.env.AppConstant;
import com.goldsign.ecpmcs.util.PubUtil;
import com.goldsign.ecpmcs.vo.CardTypeVo;
import com.goldsign.ecpmcs.vo.PubFlagVo;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;


/*
 * 〈参数查询实现类〉
 * @author     lindaquan
 * @version    V1.0
 * @createTime 2014-4-10
 */

public class ParamDao extends BaseDao implements IParamDao{
    
    private static Logger logger = Logger.getLogger(ParamDao.class.getName());

    @Override
    public List<CardTypeVo> getCardTypeVos() {
        
        List<CardTypeVo> CardTypeVoRets = new ArrayList<CardTypeVo>();
        boolean result = false;
        DbHelper dbHelper = null;
    
        try {
            dbHelper = new DbHelper("", AppConstant.dbcpHelper.getConnection());
            
            String sqlStr = "select a.card_main_id,b.card_main_name,a.card_sub_id,a.card_sub_name"
                    + " from "+AppConstant.DATABASE_USER_ST+"w_op_prm_card_sub a,"+AppConstant.DATABASE_USER_ST+"w_op_prm_card_main b"
                    + " where a.card_main_id=b.card_main_id(+) and a.record_flag='"+AppConstant.RECORD_CURRENT+"' and b.record_flag='"+AppConstant.RECORD_CURRENT+"'";

            logger.info("sqlStr:" + sqlStr);
            
            result = dbHelper.getFirstDocument(sqlStr);
            while (result) {
                
                CardTypeVo CardTypeVoRet = new CardTypeVo();
                CardTypeVoRet.setCardMainId(dbHelper.getItemTrueValue("card_main_id"));
                CardTypeVoRet.setCardMainName(dbHelper.getItemTrueValue("card_main_name"));
                CardTypeVoRet.setCardSubId(dbHelper.getItemTrueValue("card_sub_id"));
                CardTypeVoRet.setCardSubName(dbHelper.getItemTrueValue("card_sub_name"));
                
                CardTypeVoRets.add(CardTypeVoRet);
                
                result = dbHelper.getNextDocument();
            }
        } catch (Exception e) {
            PubUtil.handleExceptionNoThrow(e, logger);
        } finally {
            PubUtil.finalProcess(dbHelper);

        }

        return CardTypeVoRets;
    }

    @Override
    public List<PubFlagVo> getIdentifyTypeVos() {
        List<PubFlagVo> PubFlagVoRets = new ArrayList<PubFlagVo>();
        boolean result = false;
        DbHelper dbHelper = null;
    
        try {
            dbHelper = new DbHelper("", AppConstant.dbcpHelper.getConnection());
            
            String sqlStr = "select type,code,code_text from "+AppConstant.DATABASE_USER_TK+"w_ic_ecp_pub_flag where type='"+AppConstant.STR_CERTIFICATE_TYPE+"' order by code";

            logger.info("sqlStr:" + sqlStr);
            
            result = dbHelper.getFirstDocument(sqlStr);
            while (result) {
                
                PubFlagVo PubFlagVoRet = new PubFlagVo();
                PubFlagVoRet.setCode(dbHelper.getItemTrueValue("code"));
                PubFlagVoRet.setCodeText(dbHelper.getItemTrueValue("code_text"));
                PubFlagVoRet.setType(dbHelper.getItemTrueValue("type"));
                
                PubFlagVoRets.add(PubFlagVoRet);
                
                result = dbHelper.getNextDocument();
            }
        } catch (Exception e) {
            PubUtil.handleExceptionNoThrow(e, logger);
        } finally {
            PubUtil.finalProcess(dbHelper);

        }

        return PubFlagVoRets;
    }

    //检测设备配置是否正确
    @Override
    public Boolean isDeviceConfigureRight(String ip, String deviceType, String deviceNo) {
        boolean result = false;
        DbHelper dbHelper = null;
        try {
            dbHelper = new DbHelper("", AppConstant.dbcpHelper.getConnection());
            dbHelper.setAutoCommit(false);
            
            Object[] values = {
                    StringUtil.nullToString(ip) + "",
                    StringUtil.nullToString(deviceType) + "",
                    StringUtil.nullToString(deviceNo) + ""
                };
            
            //当数据库存在员工信息，并为发卡状态时use_state='0'，更新该条记录
            String sqlStr = "select count(*) as total from w_acc_st.w_op_prm_dev_code_acc "
                    + " where record_flag ='0' "
                    + " and IP_ADDRESS = '" + values[0] + "' "
                    + " and DEV_TYPE_ID = '" + values[1] + "' "
                    + " and DEVICE_ID = '" + values[2] + "' ";
            
            logger.info("sqlStr:" + sqlStr);
            result = dbHelper.getFirstDocument(sqlStr);
            if(result){
                if(Integer.valueOf(dbHelper.getItemValue("total"))>0){
                    result = true;
                }else{
                    result = false;
                }
            }
            
        } catch (Exception e) {
            return false;
        } finally {
            PubUtil.finalProcess(dbHelper);
        }
        logger.info("测试sqlStr:" + result);
        return result;
    }
}
