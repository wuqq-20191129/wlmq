/*
 * 文件名：SignCardDao
 * 版权：Copyright: goldsign (c) 2013
 */

package com.goldsign.ecpmcs.dao.impl;

import com.goldsign.csfrm.dao.impl.BaseDao;
import com.goldsign.csfrm.util.DateHelper;
import com.goldsign.csfrm.util.StringUtil;
import com.goldsign.lib.db.util.DbHelper;
import com.goldsign.ecpmcs.dao.ISignCardDao;
import com.goldsign.ecpmcs.env.AppConstant;
import com.goldsign.ecpmcs.util.PhotoSearcher;
import com.goldsign.ecpmcs.util.PubUtil;
import com.goldsign.ecpmcs.vo.SignCardVo;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;


/*
 * 〈记名卡信息查询DAO〉
 * @author     lindaquan
 * @version    V1.0
 * @createTime 2014-4-11
 */

public class SignCardDao extends BaseDao implements ISignCardDao{
    
    private static Logger logger = Logger.getLogger(SignCardDao.class.getName());

    @Override
    public List<SignCardVo> getSignCardVo(SignCardVo signCardParam) {
        
        List<SignCardVo> signCardVoRets = new ArrayList<SignCardVo>();
        boolean result = false;
        DbHelper dbHelper = null;
    
        try {
            dbHelper = new DbHelper("", AppConstant.dbcpHelper.getConnection());
            
            String sqlStr = "select apply_name,apply_sex,identity_type,identity_id,expired_date,address,apply_datetime,card_sub_id,card_main_id"
                    + " from "+AppConstant.DATABASE_USER_ST+"w_st_list_sign_card where 1=1";
            if(!StringUtil.isEmpty(signCardParam.getBeginTime())){
                sqlStr += " and to_char(apply_datetime,'yyyyMMdd') >= '" + signCardParam.getBeginTime().trim() + "'";
            }
            if(!StringUtil.isEmpty(signCardParam.getEndTime())){
                sqlStr += " and to_char(apply_datetime,'yyyyMMdd') <= '" + signCardParam.getEndTime().trim() + "'";
            }
            if(!StringUtil.isEmpty(signCardParam.getApplyName())){
                sqlStr += " and apply_name like '%" + signCardParam.getApplyName().trim() + "%'";
            }
            if(!StringUtil.isEmpty(signCardParam.getIdentityId())){
                sqlStr += " and identity_id like '%" + signCardParam.getIdentityId().trim() + "%'";
            }
            if(!StringUtil.isEmpty(signCardParam.getIdentityType())){
                sqlStr += " and trim(identity_type) = '" + signCardParam.getIdentityType().trim() + "'";
            }
            if(!StringUtil.isEmpty(signCardParam.getCardMainId())){
                sqlStr += " and trim(card_main_id) = '" + signCardParam.getCardMainId().trim() + "'";
            }
            if(!StringUtil.isEmpty(signCardParam.getCardSubId())){
                sqlStr += " and trim(card_sub_id) = '" + signCardParam.getCardSubId().trim() + "'";
            }
            
            sqlStr += " order by req_no desc";

            logger.info("sqlStr:" + sqlStr);
            
            result = dbHelper.getFirstDocument(sqlStr);
            while (result) {
                
                SignCardVo vo = new SignCardVo();
                
                String identityType = dbHelper.getItemTrueValue("identity_type");
                String identityId = dbHelper.getItemTrueValue("identity_id");
                String returnDir = "";
                //搜索相片路径
                returnDir = PhotoSearcher.fileJPG(identityType, identityId);
                
                vo.setImgDir(returnDir);
                vo.setAddress(dbHelper.getItemTrueValue("address"));
                vo.setApplyDatetime(DateHelper.dateToStr19yyyy_MM_dd_HH_mm_ss(dbHelper.getItemTimestampValue("apply_datetime")));
                vo.setApplyName(dbHelper.getItemTrueValue("apply_name"));
                vo.setApplySex(dbHelper.getItemTrueValue("apply_sex"));
                vo.setApplySexTxt(PubUtil.getMapString(AppConstant.CERTIFICATE_SEX, dbHelper.getItemTrueValue("apply_sex")));
                String mainType = dbHelper.getItemTrueValue("card_main_id");
                String subType = dbHelper.getItemTrueValue("card_sub_id");
                vo.setCardType(mainType+subType);
                vo.setCardTypeTxt(PubUtil.getMapString(AppConstant.TICKET_TYPE, mainType+subType));
                vo.setCardSubId(dbHelper.getItemTrueValue("card_sub_id"));
                vo.setExpiredDate(DateHelper.dateToStr19yyyy_MM_dd_HH_mm_ss(dbHelper.getItemTimestampValue("expired_date")));
                vo.setIdentityId(dbHelper.getItemTrueValue("identity_id"));
                vo.setIdentityType(dbHelper.getItemTrueValue("identity_type"));
                vo.setIdentityTypeTxt(PubUtil.getMapString(AppConstant.CERTIFICATE_TYPE, dbHelper.getItemTrueValue("identity_type")));
                
                signCardVoRets.add(vo);
                
                result = dbHelper.getNextDocument();
            }
        } catch (Exception e) {
            PubUtil.handleExceptionNoThrow(e, logger);
        } finally {
            PubUtil.finalProcess(dbHelper);

        }

        return signCardVoRets;
    }

}
