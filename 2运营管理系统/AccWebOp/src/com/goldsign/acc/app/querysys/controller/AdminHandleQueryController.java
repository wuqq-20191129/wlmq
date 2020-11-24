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

import com.goldsign.acc.app.querysys.entity.AdminHandleQuery;
import com.goldsign.acc.app.querysys.entity.CodePubFlag;
import com.goldsign.acc.app.querysys.mapper.AdminHandleQueryMapper;
import com.goldsign.acc.frame.constant.CommandConstant;
import com.goldsign.acc.frame.constant.WebConstant;
import com.goldsign.acc.frame.controller.PrmBaseController;
import com.goldsign.acc.frame.entity.PubFlag;
import com.goldsign.acc.frame.mapper.OperationLogMapper;
import com.goldsign.acc.frame.util.DBUtil;
import com.goldsign.acc.frame.util.ExpUtil;
import com.goldsign.acc.frame.util.LogUtil;
import com.goldsign.acc.frame.vo.OperationResult;

/**
 * @desc:行政处理查询
 * @author:zhongzqi
 * @create date: 2017-6-15
 */
@Controller
public class AdminHandleQueryController extends PrmBaseController{
    @Autowired
    private AdminHandleQueryMapper mapper;

    @RequestMapping("/AdminHandleQuery")
    public ModelAndView service(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView("/jsp/querysys/admin_handle_query.jsp");
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
        String[] attrNames = {LINES,STATIONS,CARDMAINS,CARDSUBS,CARDMAIN_SUBS,ADMINHANDLEREASONS,ADMIN_WAYS,DEV_TYPES};
        this.setPageOptions(attrNames, mv, request, response);
        this.baseHandler(request, response, mv);
        this.setResultText(mv,opResult,command);
        this.divideResultSet(request, mv, opResult);
        this.SaveOperationResult(mv, opResult);

        return mv;
    }
    private OperationResult query(HttpServletRequest request, AdminHandleQueryMapper mapper, OperationLogMapper operationLogMapper) throws Exception {
        OperationResult or = new OperationResult();
        LogUtil logUtil = new LogUtil();
        AdminHandleQuery queryCondition;
        List<AdminHandleQuery> resultSet;
        try {
            queryCondition = this.getQueryCondition(request);
            resultSet = mapper.getAdminHandleQuery(queryCondition);
            or.setReturnResultSet(resultSet);
            or.addMessage("成功查询" + resultSet.size() + "条记录");
        } catch (Exception e) {
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, operationLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + resultSet.size() + "条记录", operationLogMapper);
        return or;
    }
    private AdminHandleQuery getQueryCondition(HttpServletRequest request) {
        AdminHandleQuery vo = new AdminHandleQuery();
        String beginDateTime = request.getParameter("q_beginDatetime");
        String endDateTime = request.getParameter("q_endDatetime");
        String cardMainID = request.getParameter("q_cardMainID");
        String cardSubID = request.getParameter("q_cardSubID");
        String adminHandleReason = request.getParameter("q_adminHandleReason");
        if (!"".equals(beginDateTime)){
            beginDateTime = beginDateTime + " 00:00:00";
             vo.setBeginDatetime(beginDateTime);
        }
        if (!"".equals(beginDateTime)){
            endDateTime = endDateTime + " 23:59:59";
             vo.setEndDatetime(endDateTime);
        }
         if (!"".equals(cardMainID)){
            vo.setCardMainId(cardMainID);
         }
         if (!"".equals(cardSubID)){
            vo.setCardSubId(cardSubID);
         }
        if (!"".equals(adminHandleReason)){
            vo.setAdminReasonId(adminHandleReason);
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
         List<AdminHandleQuery> resultSet = (List<AdminHandleQuery>) result.getReturnResultSet();
         if(resultSet==null ||resultSet.isEmpty()){
             return ;
         }
         if (command == null || command.equals(CommandConstant.COMMAND_NEXT)
 				|| command.equals(CommandConstant.COMMAND_NEXTEND) || command.equals(CommandConstant.COMMAND_BACK)
 				|| command.equals(CommandConstant.COMMAND_BACKEND)||command.equals(CommandConstant.COMMAND_GOPAGE)) {
 			return;

 		}
         List<PubFlag> lines = (List<PubFlag>) mv.getModelMap().get(PrmBaseController.LINES);
         List<PubFlag> stations = (List<PubFlag>) mv.getModelMap().get(PrmBaseController.STATIONS);
         List<PubFlag> cardMains = (List<PubFlag>) mv.getModelMap().get(PrmBaseController.CARDMAINS);
         List<PubFlag> cardSubs = (List<PubFlag>) mv.getModelMap().get(PrmBaseController.CARDSUBS);
         List<PubFlag> adminHandleReasons = (List<PubFlag>) mv.getModelMap().get(PrmBaseController.ADMINHANDLEREASONS);
         List<PubFlag> adminWays = (List<PubFlag>) mv.getModelMap().get(PrmBaseController.ADMIN_WAYS);
         List<PubFlag> devTypes = (List<PubFlag>) mv.getModelMap().get(PrmBaseController.DEV_TYPES);
         for(AdminHandleQuery vo :resultSet){
             if (lines!=null&&!lines.isEmpty()){
                 vo.setLineText(DBUtil.getTextByCode(vo.getLineId(), lines));
             }
             if (stations!=null&&!stations.isEmpty()){
                 vo.setStationText(DBUtil.getTextByCode(vo.getStationId(), vo.getLineId(),stations));
             }
             if (cardMains!=null&&!cardMains.isEmpty()){
                 vo.setCardMainText(this.getTextByCodeAdaptNull(vo.getCardMainId(), cardMains));

             }
             if (cardSubs!=null&&!cardSubs.isEmpty()){
                  vo.setCardSubText(DBUtil.getTextByCode(vo.getCardSubId(),vo.getCardMainId(), cardSubs));
             }
             if (adminHandleReasons!=null&&!adminHandleReasons.isEmpty()){
                 vo.setAdminReasonText(this.getTextByCodeAdaptNull(vo.getAdminReasonId(), adminHandleReasons));
             }
             if (adminWays!=null&&!adminWays.isEmpty()){
                 vo.setAdminWayText(this.getTextByCodeAdaptNull(vo.getAdminWayId(), adminWays));
             }
             if (devTypes!=null&&!devTypes.isEmpty()){
                 vo.setDevTypeText(this.getTextByCodeAdaptNull(vo.getDevTypeId(), devTypes));
             }
         }
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

     @RequestMapping("/AdminHandleQueryExportAll")
     public void exportExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {
         List results = this.getBufferElementsForCurClass(request,AdminHandleQuery.class.getName());
         String expAllFields = request.getParameter("expAllFields");
         List<Map<String, Object>> queryResults = ExpUtil.entityToMap(expAllFields,results);
         ExpUtil.renderExportExcelPath(response, ExpUtil.exportExcel(queryResults, request));
     }
}
