/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.goldsign.escommu.dao;
import com.goldsign.escommu.dbutil.DbHelper;
import com.goldsign.escommu.env.AppConstant;
import com.goldsign.escommu.util.PubUtil;
import com.goldsign.escommu.vo.CityTypeReqVo;
import com.goldsign.escommu.vo.CityTypeRspVo;
import org.apache.log4j.Logger;

/**
 *
 * @author Administrator
 */
public class CityFindDao {
    private static Logger logger = Logger.getLogger(CityFindDao.class.
			getName());
    
    public CityTypeRspVo cityFind(CityTypeReqVo cityTypeReqVo) throws Exception {
        boolean result = false;
        DbHelper dbHelper = null;
        Object[] values = {};
                    
        CityTypeRspVo cityTypeRspVo = null;
        try {
            dbHelper = new DbHelper("", AppConstant.DATA_DBCPHELPER.getConnection());
            //********************************************
            String sqlStr = "select sender_code,city_code city_no,busi_code call_no,key_version okm_no,card_version,app_version from "+AppConstant.COM_TK_P+"IC_ES_CFG_SYS";

            result = dbHelper.getFirstDocument(sqlStr, values);
            if (result) {
                cityTypeRspVo = getResultRecord(dbHelper);
            }
        } catch (Exception e) {
            PubUtil.handleException(e, logger);
        } finally {
            PubUtil.finalProcess(dbHelper);

        }

        return cityTypeRspVo;
    }
    
    private CityTypeRspVo  getResultRecord(DbHelper dbHelper) throws Exception{
        
        CityTypeRspVo cityTypeRspVo = new CityTypeRspVo();
        
        //********************************************
        String cityCode = dbHelper.getItemValue("city_no");
        String busiCode = dbHelper.getItemValue("call_no");
        String serKeyVer = dbHelper.getItemValue("okm_no");
        String senderCode = dbHelper.getItemValue("sender_code");
        String cardVersion = dbHelper.getItemValue("card_version");
        String appVersion = dbHelper.getItemValue("app_version");
        cityTypeRspVo.setCityCode(cityCode);
        cityTypeRspVo.setBusiCode(busiCode);
        cityTypeRspVo.setSerKeyVer(serKeyVer);
        cityTypeRspVo.setSenderCode(senderCode);
        cityTypeRspVo.setCardVersion(cardVersion);
        cityTypeRspVo.setAppVersion(appVersion);
        
        return cityTypeRspVo;
    }
}
