/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.goldsign.acc.app.querysys.controller;

import com.goldsign.acc.app.querysys.entity.CodePubFlag;
import com.goldsign.acc.app.querysys.mapper.CodePubFlagMapper;
import com.goldsign.acc.frame.controller.PrmBaseController;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @desc:
 * @author:zhongzqi
 * @create date: 2017-6-9
 */
public class CodePubFlagController extends PrmBaseController{
    protected static final int TYPE_METRO_BLACK_RESON = 44;
    protected static final int TYPE_TICKET_CARD_DEAL_TYPE = 54;
    protected static final int TYPE_REPORT_DATA_SOURCE = 27 ;//报表数据源
//    protected static final int TYPE_SIGN_CARD_HANDLE_FLAG = 56;//记名卡退款退卡查询处理状态
    protected static final int TYPE_BUSINESS_TYPE = 51;//Bom记名卡挂失申请查询
    protected static final int TYPE_OPERATION_TYPE=60; //非即退日志 记名卡日志操作类型
    protected static final int TYPE_BROKEN_FLAG = 61;//非即退日志 记名卡日志折损标志
    protected static final int TYPE_HANDLE_FLAG = 56; //非即退日志 记名卡日志处理标志
    protected static final int TYPE_HANDLE_TYPE= 64; //非即时退款申请处理 处理状态
    protected static final int TYPE_QUERY_CONDITION = 65;//非即时退款查询条件
    protected static final int TYPE_AUDIT_FLAG = 66;//审核标志
    protected static final int TYPE_DEAL_TYPE = 67;//交易类型
    protected static final int TYPE_LIMIT_ROW_NUM = 68;//限制显示交易记录数量
    protected static final int TYPE_QUERY_CONDITION_SIGN = 69;//记名卡挂失申请处理查询条件
    @Autowired
    protected CodePubFlagMapper codePubFlagMapper ;
    private  CodePubFlag codePubFlge = new CodePubFlag();
    
    protected List<CodePubFlag> getBlackReson(){
        codePubFlge.setType(TYPE_METRO_BLACK_RESON);
        List<CodePubFlag> result = null;
        result = codePubFlagMapper.getListByType(codePubFlge);
        return result;
    }
    
    protected List<CodePubFlag> getTicketCardDealType(){
        codePubFlge.setType(TYPE_TICKET_CARD_DEAL_TYPE);
        List<CodePubFlag> result = null;
        result = codePubFlagMapper.getListByType(codePubFlge);
        return result;
    }
    
    protected List<CodePubFlag> getReportDataSource(){
        codePubFlge.setType(TYPE_REPORT_DATA_SOURCE);
        List<CodePubFlag> result = null;
        result = codePubFlagMapper.getListByType(codePubFlge);
        return result;
    }
    
//    protected List<CodePubFlag> getSignCardHandleFlag(){
////        codePubFlge.setType(TYPE_SIGN_CARD_HANDLE_FLAG);
////        List<CodePubFlag> result = null;
////        result = codePubFlagMapper.getListByType(codePubFlge);
////        return result;
////    }
    protected List<CodePubFlag> getBusinessType(){
        codePubFlge.setType(TYPE_BUSINESS_TYPE);
        List<CodePubFlag> result = null;
        result = codePubFlagMapper.getListByType(codePubFlge);
        return result;
    }
    protected List<CodePubFlag> getAllOperationType(){
        codePubFlge.setType(TYPE_OPERATION_TYPE);
        List<CodePubFlag> result = null;
        result = codePubFlagMapper.getListByType(codePubFlge);
        return result;
    }
    
    protected List<CodePubFlag> getBrokenFlag(){
        codePubFlge.setType(TYPE_BROKEN_FLAG);
        List<CodePubFlag> result = null;
        result = codePubFlagMapper.getListByType(codePubFlge);
        return result;
    }
    
    protected List<CodePubFlag> getHandleFlag(){
        codePubFlge.setType(TYPE_HANDLE_FLAG);
        List<CodePubFlag> result = null;
        result = codePubFlagMapper.getListByType(codePubFlge);
        return result;
    }
   
    
    protected List<CodePubFlag> getHandleType(){
        codePubFlge.setType(TYPE_HANDLE_TYPE);
        List<CodePubFlag> result = null;
        result = codePubFlagMapper.getListByType(codePubFlge);
        return result;
    }
    
    protected List<CodePubFlag> getQueryCondition(){
        codePubFlge.setType(TYPE_QUERY_CONDITION);
        List<CodePubFlag> result = null;
        result = codePubFlagMapper.getListByType(codePubFlge);
        return result;
    }
    protected List<CodePubFlag> getAuditFlag(){
        codePubFlge.setType(TYPE_AUDIT_FLAG);
        List<CodePubFlag> result = null;
        result = codePubFlagMapper.getListByType(codePubFlge);
        return result;
    }
    protected List<CodePubFlag> getDealType(){
        codePubFlge.setType(TYPE_DEAL_TYPE);
        List<CodePubFlag> result = null;
        result = codePubFlagMapper.getListByType(codePubFlge);
        return result;
    }
    protected List<CodePubFlag> getLimitRowNum(){
        codePubFlge.setType(TYPE_LIMIT_ROW_NUM);
        List<CodePubFlag> result = null;
        result = codePubFlagMapper.getListByType(codePubFlge);
        return result;
    }
    protected List<CodePubFlag> getQueryConditionForSignCard(){
        codePubFlge.setType(TYPE_QUERY_CONDITION_SIGN);
        List<CodePubFlag> result = null;
        result = codePubFlagMapper.getListByType(codePubFlge);
        return result;
    }

}

