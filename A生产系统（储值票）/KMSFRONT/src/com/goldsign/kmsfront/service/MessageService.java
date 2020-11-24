/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.goldsign.kmsfront.struct.service;

import com.goldsign.commu.commu.service.BaseService;
import com.goldsign.kmsfront.struct.dao.IMessageDao;
import com.goldsign.kmsfront.struct.dao.MessageDao;
import com.goldsign.kmsfront.struct.vo.QueryConVo;
import com.goldsign.kmsfront.struct.vo.QueryRetVo;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

/**
 *
 * @author lenovo
 */
public class MessageService extends BaseService implements IMessageService{

    private static Logger logger = Logger.getLogger(MessageService.class.getName());
    
    private IMessageDao messageDao;
    
    public MessageService(){
        messageDao = new MessageDao();
    }

    @Override
    public QueryRetVo queryStructDatas(List<QueryConVo> queryCons) throws Exception{
        
        List<String> accountNos = null;
        if(isAccountQuery(queryCons)){
            accountNos = getAccountByQueryCon(queryCons);
        }else if(isCardQuery(queryCons)){
            accountNos = getAccountsByCard(queryCons);
        }else if(isIdQuery(queryCons)){
            accountNos = getAccountsById(queryCons);
        }else{
            accountNos = new ArrayList<String>();
        }
        
        QueryRetVo queryRetVo = messageDao.queryStructDatas(accountNos, queryCons);
        
        return queryRetVo;
    }
    
    private boolean isAccountQuery(List<QueryConVo> queryCons){
        for(QueryConVo queryConVo: queryCons){
            int type = queryConVo.getType();
            if(type == 0){
                return true;
            }
        }
        return false;
    }
    
    private boolean isCardQuery(List<QueryConVo> queryCons){
        
        for(QueryConVo queryConVo: queryCons){
            int type = queryConVo.getType();
            if(type == 101){
                return true;
            }
        }
        
        return false;
    }
    
    private boolean isIdQuery(List<QueryConVo> queryCons){
    
        for(QueryConVo queryConVo: queryCons){
            int type = queryConVo.getType();
            if(type == 102){
                return true;
            }
        }
        
        return false;
    }

    private List<String> getAccountByQueryCon(List<QueryConVo> queryCons){
    
        List<String> accountNos = new ArrayList<String>();
        for(QueryConVo queryConVo: queryCons){
            int type = queryConVo.getType();
            String accountNo = queryConVo.getValue();
            if(type == 0){
                accountNos.add(accountNo);
                break;
            }
        }
        return accountNos;
    }
  
    private List<String> getAccountsByCard(List<QueryConVo> queryCons) throws Exception{
    
        List<String> accountNos = new ArrayList<String>();
        for(QueryConVo queryConVo: queryCons){
            int type = queryConVo.getType();
            String cardNo = queryConVo.getValue();
            if(type == 101){
                accountNos.addAll(messageDao.getAccountsByCard(cardNo));
                break;
            }
        }
        return accountNos;
    }
    
    private List<String> getAccountsById(List<QueryConVo> queryCons) throws Exception{
    
        List<String> accountNos = new ArrayList<String>();
        for(QueryConVo queryConVo: queryCons){
            int type = queryConVo.getType();
            String idNo = queryConVo.getValue();
            if(type == 102){
                accountNos.addAll(messageDao.getAccountsById(idNo));
                break;
            }
        }
        return accountNos;
    }

}
