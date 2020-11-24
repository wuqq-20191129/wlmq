/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.goldsign.acc.app.querysys.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.goldsign.acc.app.querysys.entity.CodePubFlag;
import com.goldsign.acc.app.querysys.entity.NonReturnOpLog;
import com.goldsign.acc.app.querysys.mapper.NonReturnOpLogMapper;
import com.goldsign.acc.frame.constant.CommandConstant;
import com.goldsign.acc.frame.constant.WebConstant;
import com.goldsign.acc.frame.controller.PrmBaseController;
import com.goldsign.acc.frame.entity.PubFlag;
import com.goldsign.acc.frame.mapper.OperationLogMapper;
import com.goldsign.acc.frame.util.ExpUtil;
import com.goldsign.acc.frame.util.LogUtil;
import com.goldsign.acc.frame.vo.OperationResult;

/**
 * @desc:非即时退款操作日志
 * @author:zhongzqi
 * @create date: 2017-6-21
 */
@Controller
public class NonReturnOpLogController extends CodePubFlagController{
    @Autowired
    private NonReturnOpLogMapper mapper;

    @RequestMapping("/NonReturnOpLog")
    public ModelAndView service(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView("/jsp/querysys/non_return_op_log.jsp");
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
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        String[] attrNames = {PENATLYRESONS};
        this.setPageOptions(attrNames, mv, request, response);
        this.baseHandler(request, response, mv);
        this.setResultText(mv,opResult,command);
        this.divideResultSet(request, mv, opResult);
        this.SaveOperationResult(mv, opResult);
        return mv;
    }
    private OperationResult query(HttpServletRequest request, NonReturnOpLogMapper mapper, OperationLogMapper operationLogMapper) throws Exception {
        OperationResult or = new OperationResult();
        LogUtil logUtil = new LogUtil();
        NonReturnOpLog queryCondition;
        List<NonReturnOpLog> resultSet;
        try {
            queryCondition = this.getQueryCondition(request);
            resultSet = mapper.getNonReturnOpLog(queryCondition);
            or.setReturnResultSet(resultSet);
            or.addMessage("成功查询" + resultSet.size() + "条记录");
        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, operationLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + resultSet.size() + "条记录", operationLogMapper);
        return or;
    }
    private NonReturnOpLog getQueryCondition(HttpServletRequest request) {
        NonReturnOpLog vo = new NonReturnOpLog();
        String businessReceiptId = request.getParameter("q_businessReceiptId");
        String cardPrintId = request.getParameter("q_cardPrintId");
         String operatorId = request.getParameter("q_operatorId");
        String beginDateTime = request.getParameter("q_beginDatetime");
        String endDateTime = request.getParameter("q_endDatetime");
        String oprationType = request.getParameter("q_oprationType");
         if(!"".equals(businessReceiptId)){
            vo.setReceiptId(businessReceiptId);
        }
        if (!"".equals(beginDateTime)){
            beginDateTime = beginDateTime + " 00:00:00";
             vo.setBeginDatetime(beginDateTime);
        }
        if (!"".equals(endDateTime)){
            endDateTime = endDateTime + " 23:59:59";
             vo.setEndDatetime(endDateTime);
        }
        if(!"".equals(operatorId)){
            vo.setOperatorId(operatorId);
        }
        if(!"".equals(cardPrintId)){
            vo.setCardPrintId(cardPrintId);
        }

        if(!"".equals(oprationType)){
            vo.setOperateType(oprationType);
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
         List<NonReturnOpLog> resultSet = (List<NonReturnOpLog>) result.getReturnResultSet();
        this.setOperatorName(mv,mapper);
        this.setOperationType(mv,mapper);
        this.setBrokenFlag(mv,mapper);
        this.setHandleFlgg(mv,mapper);
         if(resultSet==null ||resultSet.isEmpty()){
             return ;
         }
         if (command == null || command.equals(CommandConstant.COMMAND_NEXT)
  				|| command.equals(CommandConstant.COMMAND_NEXTEND) || command.equals(CommandConstant.COMMAND_BACK)
  				|| command.equals(CommandConstant.COMMAND_BACKEND)||command.equals(CommandConstant.COMMAND_GOPAGE)) {
  			return;

  		}

         List<CodePubFlag> operatorNames = (List<CodePubFlag>) mv.getModelMap().get("operatorNames");
         List<CodePubFlag> operationTypes = (List<CodePubFlag>) mv.getModelMap().get("operationTypes");
         List<CodePubFlag> brokenFlags = (List<CodePubFlag>) mv.getModelMap().get("brokenFlags");
         List<CodePubFlag> handleFlags = (List<CodePubFlag>) mv.getModelMap().get("handleFlags");
          List<PubFlag> penatlyReasons = (List<PubFlag>) mv.getModelMap().get(PrmBaseController.PENATLYRESONS);
         for(NonReturnOpLog vo :resultSet){
             if(operatorNames!=null&&!operatorNames.isEmpty()){
                 vo.setOperatorName(getTextByCode(vo.getOperatorId(), operatorNames));
             }
             if(operationTypes!=null&&!operationTypes.isEmpty()){
                 vo.setOperateTypeText(getTextByCode(vo.getOperateType(), operationTypes));
             }
             if(brokenFlags!=null&&!brokenFlags.isEmpty()){
                 vo.setBrokenText(getTextByCode(vo.getIsBroken(), brokenFlags));
             }
             if(handleFlags!=null&&!handleFlags.isEmpty()){
                 vo.setHdlFlagText(getTextByCode(vo.getHdlFlag(), handleFlags));
             }
             if(penatlyReasons!=null&&!penatlyReasons.isEmpty()){
                 vo.setPenaltyReasonText(this.getTextByCodeAdaptNull(vo.getPenaltyReason(), penatlyReasons));
             }
         }
    }

    private void setOperatorName(ModelAndView mv, NonReturnOpLogMapper mapper) {
         List<CodePubFlag> operatorNames =mapper.getOperatorName();
         mv.addObject("operatorNames",operatorNames);
    }

    private void setOperationType(ModelAndView mv, NonReturnOpLogMapper mapper) {
    	List<CodePubFlag> operationTypes = this.getOperationTypes();

            mv.addObject("operationTypes",operationTypes);
         }

    private List<CodePubFlag> getOperationTypes() {
    	List<CodePubFlag> operationTypes = mapper.getOperationType();
        List<CodePubFlag> allOperationTypes = this.getAllOperationType();
        if(operationTypes!=null&&!operationTypes.isEmpty()){
           for(CodePubFlag vo:operationTypes){
               for(CodePubFlag po:allOperationTypes){
                   if(vo.getCode().equals(po.getCode())){
                       vo.setCodeText(po.getCodeText());
                       break;
                   }
               }
			}
		}
        return operationTypes;
	}

    private void setBrokenFlag(ModelAndView mv, NonReturnOpLogMapper mapper) {
         List<CodePubFlag> brokenFlags =this.getBrokenFlag();
         mv.addObject("brokenFlags",brokenFlags);
    }

    private void setHandleFlgg(ModelAndView mv, NonReturnOpLogMapper mapper) {
         List<CodePubFlag> handleFlags =this.getHandleFlag();
         mv.addObject("handleFlags",handleFlags);
    }
    public  String getTextByCodeAdaptNull(String code,  List tableFlags)  {
        PubFlag pv = null;
        for (int i = 0; i < tableFlags.size(); i++) {
            pv = (PubFlag) tableFlags.get(i);
            if ((pv.getCode().trim()).equals(code) ) {
                return pv.getCode_text();
            }
        }
        return code;

    }

    @RequestMapping("/NonReturnOpLogExportAll")
    public void exportExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {
        List results = this.getBufferElementsForCurClass(request,NonReturnOpLog.class.getName());
        String expAllFields = request.getParameter("expAllFields");
        List<Map<String, Object>> queryResults = ExpUtil.entityToMap(expAllFields,results);
        ExpUtil.renderExportExcelPath(response, ExpUtil.exportExcel(queryResults, request));
    }

}
