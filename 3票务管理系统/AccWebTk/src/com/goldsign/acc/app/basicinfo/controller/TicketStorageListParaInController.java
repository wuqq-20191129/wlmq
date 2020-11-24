/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.basicinfo.controller;

import com.goldsign.acc.app.basicinfo.entity.TicketStorageBorrowUnitDefManage;
import com.goldsign.acc.app.basicinfo.entity.TicketStorageListParaIn;
import com.goldsign.acc.app.basicinfo.mapper.TicketStorageListParaInMapper;
import com.goldsign.acc.frame.constant.CommandConstant;
import com.goldsign.acc.frame.constant.LogConstant;
import com.goldsign.acc.frame.controller.StorageOutInBaseController;
import com.goldsign.acc.frame.entity.PubFlag;
import com.goldsign.acc.frame.mapper.OperationLogMapper;
import com.goldsign.acc.frame.util.CharUtil;
import com.goldsign.acc.frame.util.DBUtil;
import com.goldsign.acc.frame.util.ExpUtil;
import com.goldsign.acc.frame.util.FormUtil;
import com.goldsign.acc.frame.util.LogUtil;
import com.goldsign.acc.frame.vo.OperationResult;
import com.goldsign.login.vo.User;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
 * @author liudezeng
 * 库存一览参数
 */
@Controller
public class TicketStorageListParaInController extends StorageOutInBaseController{
  @Autowired
    private TicketStorageListParaInMapper tslpiMapper;

    @RequestMapping(value = "/ticketStorageListParaIn")
    public ModelAndView service(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView("/jsp/basicinfo/ticketStorageListParaIn.jsp");
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
                if (command.equals(CommandConstant.COMMAND_ADD)) {
                    opResult = this.addParm(request, this.tslpiMapper, this.operationLogMapper);
                }
                if (command.equals(CommandConstant.COMMAND_DELETE)) {
                    opResult = this.deleteParm(request, this.tslpiMapper, this.operationLogMapper);
                }
                if (command.equals(CommandConstant.COMMAND_MODIFY)) {
                    opResult = this.modifyParm(request, this.tslpiMapper, this.operationLogMapper);
                }
                if (command != null) {
                    if (command.equals(CommandConstant.COMMAND_ADD) || command.equals(CommandConstant.COMMAND_DELETE) || command.equals(CommandConstant.COMMAND_MODIFY)) {
                        this.queryForOp(request, this.tslpiMapper, this.operationLogMapper, opResult,command);

                    }
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
            opResult.addMessage(e.getMessage());
        }

        String[] attrNames = {CARD_MONEYS,STORAGES, IC_CARD_MAIN, IC_CARD_SUB, IC_CARD_MAIN_SUB};
        this.setPageOptions(attrNames, mv, request, response); //设置页面线路、车站、票卡、仓库等选项值、版本
        this.getResultSetText((List<TicketStorageListParaIn>) opResult.getReturnResultSet(), mv);
        this.baseHandler(request, response, mv);
        this.divideResultSet(request, mv, opResult); //结果集分页
        this.SaveOperationResult(mv, opResult); //返回结果集
        return mv;
    }

    private void getResultSetText(List<TicketStorageListParaIn> resultSet, ModelAndView mv) {
        //票卡主类型

        List<PubFlag> icCardMains = (List<PubFlag>) mv.getModelMap().get(StorageOutInBaseController.IC_CARD_MAIN);
        //票卡子类型
        List<PubFlag> icCardSub = (List<PubFlag>) mv.getModelMap().get(StorageOutInBaseController.IC_CARD_SUB);

        //仓库
        List<PubFlag> storages = (List<PubFlag>) mv.getModelMap().get(StorageOutInBaseController.STORAGES);

        for (TicketStorageListParaIn dl : resultSet) {
            if (icCardMains != null && !icCardMains.isEmpty()) {
                dl.setIc_main_type_name(DBUtil.getTextByCode(dl.getIc_main_type(), icCardMains));

            }
            if (icCardSub != null && !icCardSub.isEmpty()) {
                dl.setIc_sub_type_name(DBUtil.getTextByCode(dl.getIc_sub_type(), dl.getIc_main_type(), icCardSub));

            }
            if (storages != null && !storages.isEmpty()) {
                dl.setStorage_id_name(DBUtil.getTextByCode(dl.getStorage_id(), storages));

            }
            
            dl.setInput_date(dl.getInput_date().substring(0,10));

        }
    }

    public OperationResult queryParm(HttpServletRequest request, TicketStorageListParaInMapper tslpiMapper, OperationLogMapper opLogMapper) throws Exception {
        LogUtil logUtil = new LogUtil();
        TicketStorageListParaIn queryCondition;
        List<TicketStorageListParaIn> resultSet;
        OperationResult or = new OperationResult();
        try {
            queryCondition = this.getQueryCondition(request, or);
            if (queryCondition == null) {
                return null;
            }
            resultSet = tslpiMapper.queryParm(queryCondition);
            User user = (User) request.getSession().getAttribute("User");
        String operatorID = user.getAccount();
        List<TicketStorageListParaIn> filterVector = findOperResult(operatorID, resultSet);
            or.setReturnResultSet(filterVector);

        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + resultSet.size() + "条记录", opLogMapper);
        return or;

    }

    public OperationResult addParm(HttpServletRequest request, TicketStorageListParaInMapper tsdspMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        TicketStorageListParaIn po = this.getReqAttribute(request);
        LogUtil logUtil = new LogUtil();
        int n = 0;

//        String preMsg = "服务费率：" + "主键：" + po.getAgent_id()+ ":";
        try {
//            if (this.existRecord(po, tsdspMapper, opLogMapper)) {
//                rmsg.addMessage("记录已存在！");
//                return rmsg;
//            }

            n = this.addByTrans(request, tsdspMapper, po);

        } catch (Exception e) {
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_ADD, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_ADD, request, LogConstant.addSuccessMsg(n), opLogMapper);

        rmsg.addMessage(LogConstant.addSuccessMsg(n));

        return rmsg;
    }

    //获取查询参数
    private TicketStorageListParaIn getQueryCondition(HttpServletRequest request, OperationResult or) {
        TicketStorageListParaIn tslpi = new TicketStorageListParaIn();
        
        String q_ic_main_type = FormUtil.getParameter(request, "q_ic_main_type");
        String q_ic_sub_type = FormUtil.getParameter(request, "q_ic_sub_type");
        String q_input_date_start = FormUtil.getParameter(request, "q_input_date_start");
        String q_input_date_end = FormUtil.getParameter(request, "q_input_date_end");
        String q_storage_id = FormUtil.getParameter(request, "q_storage_id");

        
        tslpi.setIc_main_type(q_ic_main_type);
        tslpi.setIc_sub_type(q_ic_sub_type);
        tslpi.setInput_date_start(q_input_date_start);
        tslpi.setInput_date_end(q_input_date_end);
        tslpi.setStorage_id(q_storage_id);

        return tslpi;
    }

    public TicketStorageListParaIn getReqAttribute(HttpServletRequest request) {
        TicketStorageListParaIn po = new TicketStorageListParaIn();
        po.setInfo_id(FormUtil.getParameter(request, "d_info_id"));
        po.setIc_main_type(FormUtil.getParameter(request, "d_ic_main_type"));
        po.setIc_sub_type(FormUtil.getParameter(request, "d_ic_sub_type"));
        po.setCard_money(FormUtil.getParameter(request, "d_card_money"));
        po.setStation_dept(FormUtil.getParameter(request, "d_station_dept"));
        po.setStore_dept(FormUtil.getParameter(request, "d_store_dept"));
        po.setIncome_dept(FormUtil.getParameter(request, "d_income_dept"));
        po.setOther_dept(FormUtil.getParameter(request, "d_other_dept"));
        po.setInput_date(FormUtil.getParameter(request, "d_input_date"));
        po.setStorage_id(FormUtil.getParameter(request, "d_storage_id"));
        po.setRemark(FormUtil.getParameter(request, "d_remark"));

        return po;
    }

    private boolean existRecord(TicketStorageListParaIn po, TicketStorageListParaInMapper tslpiMapper, OperationLogMapper opLogMapper) {
        List<TicketStorageListParaIn> list = tslpiMapper.getListForAdd(po);
        if (list.isEmpty()) {
            return false;
        }
        return true;

    }

    private int addByTrans(HttpServletRequest request, TicketStorageListParaInMapper tslpiMapper, TicketStorageListParaIn po) throws Exception {
        TransactionStatus status = null;
        int n = 0;
        try {

            status = txMgr.getTransaction(this.def);
            try {
                n = tslpiMapper.addTicketStorageListParaIn(po);
            } catch (Exception e) {
                e.printStackTrace();
            }
//            pvMapper.modifyPrmVersionForDraft(po);

            txMgr.commit(status);  
        } catch (Exception e) {
            if (txMgr != null) {
                txMgr.rollback(status);
            }
            throw e;
        }
        return n;
    }

    public OperationResult modifyParm(HttpServletRequest request, TicketStorageListParaInMapper tslpiMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        TicketStorageListParaIn po = this.getReqAttribute(request);
        LogUtil logUtil = new LogUtil();
        int n = 0;

        try {
            n = this.modifyByTrans(request, tslpiMapper, po);
        } catch (Exception e) {
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_MODIFY, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_MODIFY, request, LogConstant.modifySuccessMsg(n), opLogMapper);

        rmsg.addMessage(LogConstant.modifySuccessMsg(n));

        return rmsg;
    }

    private int modifyByTrans(HttpServletRequest request, TicketStorageListParaInMapper tslpiMapper, TicketStorageListParaIn po) throws Exception {
        TransactionStatus status = null;
        int n = 0;
        try {

            status = txMgr.getTransaction(this.def);
            n = tslpiMapper.modifyTicketStorageListParaIn(po);

            txMgr.commit(status);
        } catch (Exception e) {
            if (txMgr != null) {
                txMgr.rollback(status);
            }
            throw e;
        }
        return n;
    }

    public OperationResult deleteParm(HttpServletRequest request, TicketStorageListParaInMapper tslpiMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        Vector<TicketStorageListParaIn> pos = this.getReqAttributeForDelete(request);
        TicketStorageListParaIn prmVo = new TicketStorageListParaIn();
        LogUtil logUtil = new LogUtil();
        int n = 0;

        try {
            n = this.deleteByTrans(request, tslpiMapper, pos, prmVo);
        } catch (Exception e) {
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_DELETE, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_DELETE, request, LogConstant.delSuccessMsg(n), opLogMapper);

        rmsg.addMessage(LogConstant.delSuccessMsg(n));

        return rmsg;
    }

    private Vector<TicketStorageListParaIn> getReqAttributeForDelete(HttpServletRequest request) {
        TicketStorageBorrowUnitDefManage po = new TicketStorageBorrowUnitDefManage();
        String selectIds = request.getParameter("allSelectedIDs");
        Vector<TicketStorageListParaIn> selectedItems = this.getDeleteIDs(selectIds);
        return selectedItems;
    }

    private int deleteByTrans(HttpServletRequest request, TicketStorageListParaInMapper tslpiMapper, Vector<TicketStorageListParaIn> pos, TicketStorageListParaIn prmVo) throws Exception {

        TransactionStatus status = null;
        int n = 0;
        try {
            status = txMgr.getTransaction(this.def);
            for (TicketStorageListParaIn po : pos) {
                n += tslpiMapper.deleteTicketStorageListParaIn(po);
            }

            txMgr.commit(status);
        } catch (Exception e) {
            e.printStackTrace();
            if (txMgr != null) {
                txMgr.rollback(status);
            }
            throw e;
        }
        return n;
    }

    private Vector<TicketStorageListParaIn> getDeleteIDs(String selectedIds) {
        StringTokenizer st = new StringTokenizer(selectedIds, ";");
        String strIds = null;
        Vector<TicketStorageListParaIn> sds = new Vector();
        TicketStorageListParaIn sd;
        while (st.hasMoreTokens()) {
            strIds = st.nextToken();
            sd = this.getTicketStorageListParaIn(strIds, "#");
            sds.add(sd);
        }
        return sds;

    }

    private TicketStorageListParaIn getTicketStorageListParaIn(String strIDs, String delim) {
        StringTokenizer st = new StringTokenizer(strIDs, delim);
        String tmp = null;
        int i = 0;
        TicketStorageListParaIn sd = new TicketStorageListParaIn();
        Vector<TicketStorageListParaIn> sds = new Vector();
        while (st.hasMoreTokens()) {
            i++;
            tmp = st.nextToken();
            if (i == 1) {
                sd.setInfo_id(tmp);
                continue;
            }
            if (i == 2) {
                sd.setIc_main_type(tmp);
                continue;
            }
            if (i == 3) {
                sd.setIc_sub_type(tmp);
                continue;
            }
            if (i == 4) {
                sd.setCard_money(tmp);
                continue;
            }
            if (i == 5) {
                sd.setStation_dept(tmp);
                continue;
            }
            if (i == 6) {
                sd.setStore_dept(tmp);
                continue;
            }
            if (i == 7) {
                sd.setIncome_dept(tmp);
                continue;
            }
            if (i == 8) {
                sd.setOther_dept(tmp);
                continue;
            }
            if (i == 9) {
                sd.setStorage_id(tmp);
                continue;
            }
            if (i == 10) {
                sd.setRemark(tmp);
                continue;
            }

        }
        return sd;

    }

    public OperationResult queryForOp(HttpServletRequest request, TicketStorageListParaInMapper tslpiMapper, OperationLogMapper opLogMapper, OperationResult opResult,String command) throws Exception {
        LogUtil logUtil = new LogUtil();
        TicketStorageListParaIn queryCondition;
        List<TicketStorageListParaIn> resultSet;

        try {
            
            queryCondition = this.getQueryConditionForOp(request,tslpiMapper);
            
            if(command.equals(CommandConstant.COMMAND_ADD)||command.equals(CommandConstant.COMMAND_MODIFY)){
            resultSet = tslpiMapper.getListForAdd(queryCondition);
            opResult.setReturnResultSet(resultSet);
            logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + resultSet.size() + "条记录", opLogMapper);
            }else{
            resultSet = tslpiMapper.queryParm(queryCondition);
            opResult.setReturnResultSet(resultSet);
            }
            //or.addMessage("成功查询" + resultSet.size() + "条记录");
        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + resultSet.size() + "条记录", opLogMapper);
        return opResult;

    }

    private TicketStorageListParaIn getQueryConditionForOp(HttpServletRequest request,TicketStorageListParaInMapper tslpiMapper) {
        TicketStorageListParaIn qCon = new TicketStorageListParaIn();
        HashMap vQueryControlDefaultValues = null;
        String queryControlDefaultValues = request.getParameter("queryControlDefaultValues");
        String command = request.getParameter("command");
        if (command.equals(CommandConstant.COMMAND_ADD)) {//操作处于添加模式
            if (command.equals(CommandConstant.COMMAND_DELETE)) {
                return null;
            }
            String info_id = tslpiMapper.getInfoId();
            qCon.setInfo_id(info_id);            
            qCon.setIc_main_type(FormUtil.getParameter(request, "d_ic_main_type"));
            qCon.setIc_sub_type(FormUtil.getParameter(request, "d_ic_sub_type"));
            qCon.setCard_money(FormUtil.getParameter(request, "d_card_money"));
            qCon.setStation_dept(FormUtil.getParameter(request, "d_station_dept"));
            qCon.setStore_dept(FormUtil.getParameter(request, "d_store_dept"));
            qCon.setIncome_dept(FormUtil.getParameter(request, "d_income_dept"));
            qCon.setOther_dept(FormUtil.getParameter(request, "d_other_dept"));
            qCon.setStorage_id(FormUtil.getParameter(request, "d_storage_id"));            
            qCon.setRemark(FormUtil.getParameter(request, "d_remark"));

        }else if(command.equals(CommandConstant.COMMAND_MODIFY)) {//操作处于修改模式
            {
            qCon.setInfo_id(FormUtil.getParameter(request, "d_info_id"));          
            qCon.setIc_main_type(FormUtil.getParameter(request, "d_ic_main_type"));
            qCon.setIc_sub_type(FormUtil.getParameter(request, "d_ic_sub_type"));
            qCon.setCard_money(FormUtil.getParameter(request, "d_card_money"));
            qCon.setStation_dept(FormUtil.getParameter(request, "d_station_dept"));
            qCon.setStore_dept(FormUtil.getParameter(request, "d_store_dept"));
            qCon.setIncome_dept(FormUtil.getParameter(request, "d_income_dept"));
            qCon.setOther_dept(FormUtil.getParameter(request, "d_other_dept"));
            qCon.setStorage_id(FormUtil.getParameter(request, "d_storage_id"));            
            qCon.setRemark(FormUtil.getParameter(request, "d_remark"));
        }
        } else {//操作处于非添加模式
            vQueryControlDefaultValues = FormUtil.getQueryControlDefaultValues(queryControlDefaultValues);
            if (!vQueryControlDefaultValues.isEmpty()) {
                qCon.setInput_date_start(FormUtil.getQueryControlDefaultValue(vQueryControlDefaultValues, "q_input_date_start"));
                qCon.setInput_date_end(FormUtil.getQueryControlDefaultValue(vQueryControlDefaultValues, "q_input_date_end"));
                qCon.setIc_main_type(FormUtil.getQueryControlDefaultValue(vQueryControlDefaultValues, "q_ic_main_type"));
                qCon.setIc_sub_type(FormUtil.getQueryControlDefaultValue(vQueryControlDefaultValues, "q_ic_sub_type"));
                qCon.setStorage_id(FormUtil.getQueryControlDefaultValue(vQueryControlDefaultValues, "q_storage_id"));

            }
        }

        return qCon;
    }


       private Vector findOperResult(String sys_operator_id, List<TicketStorageListParaIn> resultSet) throws Exception{
        Set storeSet = getUserStoreSet(sys_operator_id);
        Vector returnVector = new Vector();
//        FrameDBUtil util = new FrameDBUtil();
        if(!storeSet.contains("9999")){
            for (Object object : resultSet) {
                TicketStorageListParaIn infoVo = (TicketStorageListParaIn) object;
                if(storeSet.contains(infoVo.getStorage_id())){
                    returnVector.add(object);
                }
            }
        }else{
            returnVector.addAll(resultSet);
        }
        return returnVector;
    }

     public Set getUserStoreSet(String operatorId) throws Exception {
        Vector paramList = new Vector();
        Set vectorSet = new TreeSet();
        boolean result = false;
        TicketStorageListParaIn vo = new TicketStorageListParaIn();
        vo.setSys_operator_id(operatorId);
       List<TicketStorageListParaIn> lists = tslpiMapper.getUserStoreSet(vo); 
        if (lists != null && lists.size() > 0) {
            String sysStorageId;
            for (TicketStorageListParaIn tsscm : lists) {
                sysStorageId = tsscm.getSys_storage_id();
                if (sysStorageId != null && !(sysStorageId.trim().isEmpty())) {
//                  "0000"代表 无 
                    if (!sysStorageId.equals("0000")) {
                        //                        "9999"代表 全部
                        if (sysStorageId.equals("9999")) {
                            vectorSet.add(sysStorageId);
                            break;
                        } else {
                            vectorSet.add(sysStorageId);
                        }
                    }
                }
            }
        }
        return vectorSet;
    }
     
          @RequestMapping("/TicketStorageListParaInExportAll")
    public void exportExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	
        List results = this.getBufferElementsForCurClass(request,"com.goldsign.acc.app.basicinfo.entity.TicketStorageListParaIn");
        List<Map<String,String>> queryResults = this.entityToMap(results);
        ExpUtil.renderExportExcelPath(response, ExpUtil.exportExcel(queryResults, request));
    }

    private List<Map<String, String>> entityToMap(List results) {
        List<Map<String,String>> list =  new ArrayList<>();
        int row = results.size();
        for(int n=0;n<row ;n++){
            TicketStorageListParaIn vo = (TicketStorageListParaIn)results.get(n);
            Map<String,String> map = new HashMap<>();
            map.put("info_id", vo.getInfo_id());
            map.put("ic_main_type_name", vo.getIc_main_type_name());
            map.put("ic_sub_type_name", vo.getIc_sub_type_name());
            map.put("card_money", vo.getCard_money());
            map.put("input_date", vo.getInput_date());
            map.put("station_dept", vo.getStation_dept());
            map.put("store_dept", vo.getStore_dept());
            map.put("income_dept", vo.getIncome_dept());
            map.put("other_dept", vo.getOther_dept());
            map.put("storage_id_name", vo.getStorage_id_name());
            map.put("remark", vo.getRemark());
           
            list.add(map);
        }
        return list;
    }


}

