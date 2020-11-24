/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.goldsign.escommu.dao;
import com.goldsign.escommu.dbutil.DbHelper;
import com.goldsign.escommu.env.AppConstant;
import com.goldsign.escommu.util.PubUtil;
import com.goldsign.escommu.vo.SignCardReqVo;
import com.goldsign.escommu.vo.SignCardRspVo;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

/**
 *
 * @author Administrator
 */
public class SignCardFindDao {
    private static Logger logger = Logger.getLogger(SignCardFindDao.class.
			getName());
    
    public List<SignCardRspVo> signCardFind(SignCardReqVo signCardReqVo) throws Exception {
        boolean result = false;
        DbHelper dbHelper = null;
        //Object[] values = {signCardReqVo.getOrderNo(), signCardReqVo.getStartReqNo(), signCardReqVo.getEndReqNo()};
        Object[] values = {signCardReqVo.getOrderNo()};
                    
        List<SignCardRspVo> signCardRspVos = new ArrayList<SignCardRspVo>();
        SignCardRspVo signCardRspVo = null;
        try {
            dbHelper = new DbHelper("", AppConstant.DATA_DBCPHELPER.getConnection());
            //********************************************
            /*String sqlStr = "select req_no,b.proposer_name,b.proposer_sex,b.ind_type,b.national_id, "+
                    "to_char(b.expired_date,'yyyyMMdd') expired_date "+
                 "from ACC_TK.IC_PDU_ORDER_FORM a,"+AppConstant.COM_ST_P+"st_list_sign_card b "+
                 "where "+
                    "a.order_no=b.order_no "+
                    "and a.order_no=? "+
                    "and TRUNC(a.b_req_no)>=? "+
                    "and TRUNC(a.e_req_no)<=?";*/
            String sqlStr = "select b.req_no,b.APPLY_NAME proposer_name,b.APPLY_SEX proposer_sex,b.IDENTITY_TYPE ind_type,b.IDENTITY_ID national_id, "+
                    "to_char(b.expired_date,'yyyyMMdd') expired_date "+
                 "from "+AppConstant.COM_ST_P+"st_list_sign_card b "+
                    "where b.order_no=? order by b.req_no asc ";

            result = dbHelper.getFirstDocument(sqlStr, values);
            while (result) {
                signCardRspVo = getResultRecord(dbHelper);
                signCardRspVos.add(signCardRspVo);
                
                result = dbHelper.getNextDocument();
            }
        } catch (Exception e) {
            PubUtil.handleException(e, logger);
        } finally {
            PubUtil.finalProcess(dbHelper);

        }

        return signCardRspVos;
    }
    
    private SignCardRspVo getResultRecord(DbHelper dbHelper) throws Exception{
        
        SignCardRspVo signCardRspVo = new SignCardRspVo();

        //********************************************
        String reqNo = dbHelper.getItemValue("req_no");
        String name = dbHelper.getItemValue("proposer_name");
        String gender = dbHelper.getItemValue("proposer_sex");
        String cerType = dbHelper.getItemValue("ind_type");
        String cerNo = dbHelper.getItemValue("national_id");
        String cerEffTime = dbHelper.getItemValue("expired_date");
        
        signCardRspVo.setReqNo(reqNo);
        signCardRspVo.setName(name);
        signCardRspVo.setGender(gender);
        signCardRspVo.setCerType(cerType);
        signCardRspVo.setCerNo(cerNo);
        signCardRspVo.setCerEffTime(cerEffTime);

        return signCardRspVo;
    }
}
