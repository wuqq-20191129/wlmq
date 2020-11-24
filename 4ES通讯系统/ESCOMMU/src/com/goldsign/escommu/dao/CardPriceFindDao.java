/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.goldsign.escommu.dao;
import com.goldsign.escommu.env.AppConstant;
import com.goldsign.escommu.dbutil.DbHelper;
import com.goldsign.escommu.util.PubUtil;
import com.goldsign.escommu.vo.CardPriceReqVo;
import com.goldsign.escommu.vo.CardPriceRspVo;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

/**
 *
 * @author Administrator
 */
public class CardPriceFindDao {
    private static Logger logger = Logger.getLogger(CardPriceFindDao.class.
			getName());
    
    public List<CardPriceRspVo> cardPriceFind(CardPriceReqVo cardPriceReqVo) throws Exception {
        boolean result = false;
        DbHelper dbHelper = null;
        Object[] values = {};
                    
        List<CardPriceRspVo> cardPriceRspVos = new ArrayList<CardPriceRspVo>();
        CardPriceRspVo cardPriceRspVo = null;
        try {
            dbHelper = new DbHelper("", AppConstant.DATA_DBCPHELPER.getConnection());
            //********************************************
            String sqlStr = "select distinct(fare) fare from "+AppConstant.COM_ST_P+"OP_PRM_FARE_TABLE where record_flag='0' order by fare";

            result = dbHelper.getFirstDocument(sqlStr, values);
            while (result) {
                cardPriceRspVo = getResultRecord(dbHelper);
                cardPriceRspVos.add(cardPriceRspVo);
                
                result = dbHelper.getNextDocument();
            }
        } catch (Exception e) {
            PubUtil.handleException(e, logger);
        } finally {
            PubUtil.finalProcess(dbHelper);

        }

        return cardPriceRspVos;
    }
    
    private CardPriceRspVo  getResultRecord(DbHelper dbHelper) throws Exception{
        
        CardPriceRspVo cardPriceRspVo = new CardPriceRspVo();
        
        //********************************************
        String price = dbHelper.getItemValue("fare");
        cardPriceRspVo.setPrice(price);
        
        return cardPriceRspVo;
    }
}
