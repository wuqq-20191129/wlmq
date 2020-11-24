/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.goldsign.escommu.dao;
import com.goldsign.escommu.env.AppConstant;
import com.goldsign.escommu.dbutil.DbHelper;
import com.goldsign.escommu.util.PubUtil;
import com.goldsign.escommu.vo.CardTypeReqVo;
import com.goldsign.escommu.vo.CardTypeRspVo;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

/**
 *
 * @author Administrator
 */
public class CardTypeFindDao {
    private static Logger logger = Logger.getLogger(CardTypeFindDao.class.
			getName());
    
    public List<CardTypeRspVo> cardTypeFind(CardTypeReqVo cardTypeReqVo) throws Exception {
        boolean result = false;
        DbHelper dbHelper = null;
        Object[] values = {};
                    
        List<CardTypeRspVo> cardTypeRspVos = new ArrayList<CardTypeRspVo>();
        CardTypeRspVo cardTypeRspVo = null;
        try {
            dbHelper = new DbHelper("", AppConstant.DATA_DBCPHELPER.getConnection());
            //********************************************
            String sqlStr = "select distinct CARD_MAIN_ID||CARD_SUB_ID card_type,CARD_SUB_NAME card_sub_desc from "+AppConstant.COM_ST_P+"OP_PRM_CARD_SUB where record_flag='0'";

            result = dbHelper.getFirstDocument(sqlStr, values);
            while (result) {
                cardTypeRspVo = getResultRecord(dbHelper);
                cardTypeRspVos.add(cardTypeRspVo);
                
                result = dbHelper.getNextDocument();
            }
        } catch (Exception e) {
            PubUtil.handleException(e, logger);
        } finally {
            PubUtil.finalProcess(dbHelper);

        }

        return cardTypeRspVos;
    }
    
    private CardTypeRspVo  getResultRecord(DbHelper dbHelper) throws Exception{
        
        CardTypeRspVo cardTypeRspVo = new CardTypeRspVo();
        
        //********************************************
        String cardType = dbHelper.getItemValue("card_type");
        String cardSubDesc = dbHelper.getItemValue("card_sub_desc");
        cardTypeRspVo.setCardType(cardType);
        cardTypeRspVo.setCardSubDesc(cardSubDesc);
        
        return cardTypeRspVo;
    }
}
