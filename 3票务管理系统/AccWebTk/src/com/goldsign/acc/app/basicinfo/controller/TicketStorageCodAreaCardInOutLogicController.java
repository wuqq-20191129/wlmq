/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.basicinfo.controller;

import com.goldsign.acc.app.basicinfo.entity.TicketStorageCodAreaCardInOutLogic;
import com.goldsign.acc.app.basicinfo.mapper.TicketStorageCodAreaCardInOutLogicMapper;
import com.goldsign.acc.frame.constant.CommandConstant;
import com.goldsign.acc.frame.constant.LogConstant;
import com.goldsign.acc.frame.controller.StorageOutInBaseController;
import com.goldsign.acc.frame.entity.PubFlag;
import com.goldsign.acc.frame.mapper.OperationLogMapper;
import com.goldsign.acc.frame.util.DBUtil;
import com.goldsign.acc.frame.util.FormUtil;
import com.goldsign.acc.frame.util.LogUtil;
import com.goldsign.acc.frame.vo.OperationResult;
import com.goldsign.login.vo.User;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeSet;
import java.util.Vector;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.TransactionStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author liudz
 * 票卡出入库管理配置
 */
@Controller
public class TicketStorageCodAreaCardInOutLogicController extends StorageOutInBaseController{
  @Autowired
    private TicketStorageCodAreaCardInOutLogicMapper tslpiMapper;

    @RequestMapping(value = "/ticketStorageCodAreaCardInOutLogic")
    public ModelAndView service(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView("/jsp/basicinfo/ticketStorageCodAreaCardInOutLogic.jsp");
        String command = request.getParameter("command");
        OperationResult opResult = new OperationResult(); 
         //取操作员ID
        User user = (User) request.getSession().getAttribute("User");
        String sys_operator_id = user.getAccount();
        
        try {
            if (command != null) {
                command = command.trim();
                if (command.equals(CommandConstant.COMMAND_QUERY)) {
                    opResult = this.queryParm(request, this.tslpiMapper, this.operationLogMapper);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
            opResult.addMessage(e.getMessage());
        }

        String[] attrNames = {STORAGES,AREAS,STORAGE_AREAS_ENCODE_AND_VALUE,IC_CARD_MAIN, IC_CARD_SUB, IC_CARD_MAIN_SUB};
        this.setPageOptions(attrNames, mv, request, response); //设置页面线路、车站、票卡、仓库等选项值、版本
        this.getResultSetText((List<TicketStorageCodAreaCardInOutLogic>) opResult.getReturnResultSet(), mv);
        this.baseHandler(request, response, mv);
//        this.divideResultSet(request, mv, opResult); //结果集分页
        this.SaveOperationResult(mv, opResult); //返回结果集
        return mv;
    }

    private void getResultSetText(List<TicketStorageCodAreaCardInOutLogic> resultSet, ModelAndView mv) {
        //票卡主类型

        List<PubFlag> icCardMains = (List<PubFlag>) mv.getModelMap().get(StorageOutInBaseController.IC_CARD_MAIN);
        //票卡子类型
        List<PubFlag> icCardSub = (List<PubFlag>) mv.getModelMap().get(StorageOutInBaseController.IC_CARD_SUB);

        //仓库
        List<PubFlag> storages = (List<PubFlag>) mv.getModelMap().get(StorageOutInBaseController.STORAGES);
        //票区
        List<PubFlag> area = (List<PubFlag>) mv.getModelMap().get(StorageOutInBaseController.AREAS);

        for (TicketStorageCodAreaCardInOutLogic dl : resultSet) {
            if (icCardMains != null && !icCardMains.isEmpty()) {
                dl.setIc_main_type_name(DBUtil.getTextByCode(dl.getIc_main_type(), icCardMains));

            }
            if (icCardSub != null && !icCardSub.isEmpty()) {
                dl.setIc_sub_type_name(DBUtil.getTextByCode(dl.getIc_sub_type(), dl.getIc_main_type(), icCardSub));

            }
            if (storages != null && !storages.isEmpty()) {
                dl.setStorage_id_name(DBUtil.getTextByCode(dl.getStorage_id(), storages));

            }
            if (area != null && !area.isEmpty()) {
                dl.setArea_id_name(DBUtil.getTextByCode(dl.getArea_id(), dl.getStorage_id(), area));

            }
           if (dl.getOut_logic_flag().equals("1")) {
                dl.setOut_logic_flag_name("按盒管理");

            }

        }
    }

    public OperationResult queryParm(HttpServletRequest request, TicketStorageCodAreaCardInOutLogicMapper tslpiMapper, OperationLogMapper opLogMapper) throws Exception {
        LogUtil logUtil = new LogUtil();
        TicketStorageCodAreaCardInOutLogic queryCondition;
        List<TicketStorageCodAreaCardInOutLogic> resultSet;
        OperationResult or = new OperationResult();
        try {
            queryCondition = this.getQueryCondition(request, or);
            if (queryCondition == null) {
                return null;
            }
            resultSet = tslpiMapper.queryParm(queryCondition);
            or.setReturnResultSet(resultSet);
            or.addMessage("成功查询" + resultSet.size() + "条记录");

        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + resultSet.size() + "条记录", opLogMapper);
        return or;

    }

   

    //获取查询参数
    private TicketStorageCodAreaCardInOutLogic getQueryCondition(HttpServletRequest request, OperationResult or) {
        TicketStorageCodAreaCardInOutLogic tslpi = new TicketStorageCodAreaCardInOutLogic();
        
        String q_ic_main_type = FormUtil.getParameter(request, "q_ic_main_type");
        String q_ic_sub_type = FormUtil.getParameter(request, "q_ic_sub_type");
        String q_storage_id = FormUtil.getParameter(request, "q_storage_id");
        String q_area_id = FormUtil.getParameter(request, "q_area_id");
        

        
        tslpi.setIc_main_type(q_ic_main_type);
        tslpi.setIc_sub_type(q_ic_sub_type);
        tslpi.setStorage_id(q_storage_id);
        tslpi.setArea_id(q_area_id);
    

        return tslpi;
    }




}


