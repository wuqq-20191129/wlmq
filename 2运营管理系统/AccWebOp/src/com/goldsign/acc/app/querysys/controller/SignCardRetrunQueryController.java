/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.goldsign.acc.app.querysys.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.goldsign.acc.app.querysys.entity.CodePubFlag;
import com.goldsign.acc.app.querysys.entity.SignCardReturnQuery;
import com.goldsign.acc.app.querysys.mapper.SignCardReturnQueryMapper;
import com.goldsign.acc.frame.constant.CommandConstant;
import com.goldsign.acc.frame.constant.WebConstant;
import com.goldsign.acc.frame.entity.PubFlag;
import com.goldsign.acc.frame.mapper.OperationLogMapper;
import com.goldsign.acc.frame.util.DBUtil;
import com.goldsign.acc.frame.util.ExpUtil;
import com.goldsign.acc.frame.util.LogUtil;
import com.goldsign.acc.frame.vo.OperationResult;

/**
 * @desc:记名卡退卡退款查询
 * @author:zhongzqi
 * @create date: 2017-6-16
 */
@Controller
public class SignCardRetrunQueryController extends CodePubFlagController{
    @Autowired
    private SignCardReturnQueryMapper mapper;

    @RequestMapping("/SignCardReturnQuery")
    public ModelAndView service(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView("/jsp/querysys/sign_card_return_query.jsp");
         String command = request.getParameter("command");
        OperationResult opResult = new OperationResult();
        try {
            if (command != null) {
                command = command.trim();
                if (command.equals(CommandConstant.COMMAND_QUERY))
                {
                    opResult = this.query(request, this.mapper, this.operationLogMapper);
                }
                if (command.equals(CommandConstant.COMMAND_NEXT) || command.equals(CommandConstant.COMMAND_NEXTEND) || command.equals(CommandConstant.COMMAND_BACK)
                        || command.equals(CommandConstant.COMMAND_BACKEND)) {
                        //保存查询条件
                        this.saveQueryControlDefaultValues(request, mv);
                        this.saveCheckFlag(request,mv);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        String[] attrNames = {LINES,STATIONS,LINE_STATIONS,CARDMAINS,CARDSUBS,CARDMAIN_SUBS};
        this.setPageOptions(attrNames, mv, request, response);
        this.baseHandler(request, response, mv);
        this.setResultText(mv,opResult,command);
        this.divideResultSet(request, mv, opResult);
        this.SaveOperationResult(mv, opResult);
        return mv;
    }
    private OperationResult query(HttpServletRequest request, SignCardReturnQueryMapper mapper, OperationLogMapper operationLogMapper) throws Exception {
        OperationResult or = new OperationResult();
        LogUtil logUtil = new LogUtil();
        SignCardReturnQuery queryCondition;
        List<SignCardReturnQuery> resultSet;
        try {
            queryCondition = this.getQueryCondition(request);
            resultSet = mapper.getSignCardReturnQuery(queryCondition);
            or.setReturnResultSet(resultSet);
            or.addMessage("成功查询" + resultSet.size() + "条记录");
        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, operationLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + resultSet.size() + "条记录", operationLogMapper);
        return or;
    }
    private SignCardReturnQuery getQueryCondition(HttpServletRequest request) {
        SignCardReturnQuery vo = new SignCardReturnQuery();
        String cardLogicId = request.getParameter("q_cardLogicalId");
        String cardMainId = request.getParameter("q_cardMainId");
        String cardSubId = request.getParameter("q_cardSubId");
        String receiptId  = request.getParameter("q_receiptId");
        String returnLineId = request.getParameter("q_returnLineId");
        String returnStationId = request.getParameter("q_returnStationId");
        String identityId = request.getParameter("q_identityId");
        String beginDateTime = request.getParameter("q_beginDatetime");
        String endDateTime = request.getParameter("q_endDatetime");
        String hdlFlag = request.getParameter("q_hdlFlag");
        boolean balaCheckFlag = request.getParameterMap().containsKey("q_balaCheck");
        String idType = request.getParameter("q_identifyType");

        if(!"".equals(cardLogicId)){
            vo.setCardLogicalId(cardLogicId);
        }
        if (!"".equals(beginDateTime)){
            beginDateTime = beginDateTime + " 00:00:00";
             vo.setBeginDatetime(beginDateTime);
        }
          if (!"".equals(cardMainId)){
            vo.setCardMainId(cardMainId);
         }
         if (!"".equals(cardSubId)){
            vo.setCardSubId(cardSubId);
         }
         if (!"".equals(receiptId)){
             vo.setBusinessReceiptId(receiptId);
         }
         if (!"".equals(returnLineId)){
             vo.setReturnLineId(returnLineId);
         }
         if (!"".equals(returnStationId)){
             vo.setReturnStationId(returnStationId);
         }
         if (!"".equals(identityId)){
             vo.setIdentityId(identityId);
         }
        if (!"".equals(endDateTime)){
            endDateTime = endDateTime + " 23:59:59";
             vo.setEndDatetime(endDateTime);
        }
        if (!"".equals(hdlFlag)){
            vo.setHdlFlag(hdlFlag);
        }
        if(balaCheckFlag){
            request.setAttribute("balaCheckFlag", balaCheckFlag);
            String operator = request.getParameter("q_rtnOper");
            String rtnBala = request.getParameter("q_rtnBala");
            if(!"".equals(operator)){
             vo.setOperator(operator);
            }
            if(!"".equals(rtnBala)){
                vo.setReturnBala(new BigDecimal(rtnBala));
            }
        }
        if(!"".equals(idType)) {
        	vo.setIdentityType(idType);
        }
        return vo;
    }

    public static String getTextByCode(String code, List tableFlags) {
        CodePubFlag pv = null;
        for (int i = 0; i < tableFlags.size(); i++) {
            pv = (CodePubFlag) tableFlags.get(i);
            if (pv.getCode().equals(code)) {
                return pv.getCodeText();
            }
        }
        return code;
    }


    private void setResultText(ModelAndView mv,OperationResult result, String command) {
         List<SignCardReturnQuery> resultSet = (List<SignCardReturnQuery>) result.getReturnResultSet();
        this.setHandleFlag(mv);
        this.setIdType(mv,mapper);
         if(resultSet==null ||resultSet.isEmpty()){
             return ;
         }
         if (command == null || command.equals(CommandConstant.COMMAND_NEXT)
   				|| command.equals(CommandConstant.COMMAND_NEXTEND) || command.equals(CommandConstant.COMMAND_BACK)
   				|| command.equals(CommandConstant.COMMAND_BACKEND)||command.equals(CommandConstant.COMMAND_GOPAGE)) {
   			return;

   		}

         List<PubFlag> lines = (List<PubFlag>) mv.getModelMap().get(SignCardRetrunQueryController.LINES);
         List<PubFlag> stations = (List<PubFlag>) mv.getModelMap().get(SignCardRetrunQueryController.STATIONS);
         List<PubFlag> cardMains = (List<PubFlag>) mv.getModelMap().get(SignCardRetrunQueryController.CARDMAINS);
         List<PubFlag> cardSubs = (List<PubFlag>) mv.getModelMap().get(SignCardRetrunQueryController.CARDSUBS);
         List<CodePubFlag> hadleTypes = (List<CodePubFlag>) mv.getModelMap().get("handleTypes");
         List<CodePubFlag> idTypes = (List<CodePubFlag>) mv.getModelMap().get("idTypes");
         for(SignCardReturnQuery vo :resultSet){
             if (lines!=null&&!lines.isEmpty()){
                 vo.setLineText(this.getTextByCodeAdaptNull(vo.getLineId(), lines));
                 vo.setReturnLineText(this.getTextByCodeAdaptNull(vo.getReturnLineId(), lines));
             }
             if (stations!=null&&!stations.isEmpty()){
                 vo.setStationText(DBUtil.getTextByCode(vo.getStationId(),vo.getLineId(), stations));
                 vo.setReturnStationText(DBUtil.getTextByCode(vo.getReturnStationId(),vo.getReturnLineId(), stations));
             }
             if (cardMains!=null&&!cardMains.isEmpty()){
                 vo.setCardMainText(this.getTextByCodeAdaptNull(vo.getCardMainId(), cardMains));
             }
             if (cardSubs!=null&&!cardSubs.isEmpty()){
                  vo.setCardSubText(DBUtil.getTextByCode(vo.getCardSubId(), vo.getCardMainId(),cardSubs));
             }
             if (hadleTypes!=null&&!hadleTypes.isEmpty()){
                 vo.setHdlText(this.getTextByFlagCode(vo.getHdlFlag(), hadleTypes));
             }
             if(idTypes!=null&&!idTypes.isEmpty()){
                 vo.setIdentityTypeText(this.getTextByFlagCode(vo.getIdentityType(), idTypes));
             }
         }
    }

    private void setHandleFlag(ModelAndView mv) {
        List<CodePubFlag> handleTypes =this.getHandleFlag();
        mv.addObject("handleTypes",handleTypes);
    }
    public  String getTextByFlagCode(String code, List tableFlags) {
        CodePubFlag pv = null;
        for (int i = 0; i < tableFlags.size(); i++) {
            pv = (CodePubFlag) tableFlags.get(i);
            if (pv.getCode().equals(code)) {
                return pv.getCodeText();
            }
        }
        return code;
    }

   public String getTextByCodeAdaptNull(String code, List tableFlags) {
        PubFlag pv = null;
        for (int i = 0; i < tableFlags.size(); i++) {
            pv = (PubFlag) tableFlags.get(i);
            if ((pv.getCode().trim()).equals(code)) {
                return pv.getCode_text();
            }
        }
        return code;

    }
    private void setIdType(ModelAndView mv,SignCardReturnQueryMapper mapper) {
         List<CodePubFlag> idTypes =mapper.getIdTypes();
         mv.addObject("idTypes",idTypes);
    }

    private void saveCheckFlag(HttpServletRequest request, ModelAndView mv) {
        String checkFlag = request.getParameter("d_balaCheckFlag");
        if("true".equals(checkFlag)){
            mv.addObject("balaCheckFlag",checkFlag);
        }

    }
    @RequestMapping("/SignCardReturnQueryExportAll")
    public void exportExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {
        List results = this.getBufferElementsForCurClass(request,SignCardReturnQuery.class.getName());
        String expAllFields = request.getParameter("expAllFields");
        List<Map<String, Object>> queryResults = ExpUtil.entityToMap(expAllFields,results);
        ExpUtil.renderExportExcelPath(response, ExpUtil.exportExcel(queryResults, request));
    }
}
