/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.basicinfo.controller;

import com.goldsign.acc.app.basicinfo.entity.TicketStorageBorrowUnitDefManage;
import com.goldsign.acc.app.basicinfo.entity.TicketStorageDistributeStationParm;
import com.goldsign.acc.app.basicinfo.entity.TicketStorageParaIn;
import com.goldsign.acc.app.basicinfo.mapper.TicketStorageParaInMapper;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
 * @author liudezeng 流失量参数
 */
@Controller
public class TicketStorageParaInController extends StorageOutInBaseController {

    @Autowired
    private TicketStorageParaInMapper tspiMapper;

    @RequestMapping(value = "/ticketStorageParaIn")
    public ModelAndView service(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView("/jsp/basicinfo/ticketStorageParaIn.jsp");
        String command = request.getParameter("command");
        OperationResult opResult = new OperationResult(); 
         //取操作员ID
        User user = (User) request.getSession().getAttribute("User");
        String sys_operator_id = user.getAccount();
        
        try {
            if (command != null) {
                command = command.trim();
                if (command.equals(CommandConstant.COMMAND_QUERY)) {
                    opResult = this.queryParm(request, this.tspiMapper, this.operationLogMapper);
                }
                if (command.equals(CommandConstant.COMMAND_ADD)) {
                    opResult = this.addParm(request, this.tspiMapper, this.operationLogMapper);
                }
                if (command.equals(CommandConstant.COMMAND_DELETE)) {
                    opResult = this.deleteParm(request, this.tspiMapper, this.operationLogMapper);
                }
                if (command.equals(CommandConstant.COMMAND_MODIFY)) {
                    opResult = this.modifyParm(request, this.tspiMapper, this.operationLogMapper);
                }
                if (command != null) {
                    if (command.equals(CommandConstant.COMMAND_ADD) || command.equals(CommandConstant.COMMAND_DELETE) || command.equals(CommandConstant.COMMAND_MODIFY)) {
                        this.queryForOp(request, this.tspiMapper, this.operationLogMapper, opResult,command);

                    }
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
            opResult.addMessage(e.getMessage());
        }

        String[] attrNames = {PARA_FLAG, STORAGES, IC_CARD_MAIN, IC_CARD_SUB, IC_CARD_MAIN_SUB};
        this.setPageOptions(attrNames, mv, request, response); //设置页面线路、车站、票卡、仓库等选项值、版本
        this.getResultSetText((List<TicketStorageParaIn>) opResult.getReturnResultSet(), mv);
        this.baseHandler(request, response, mv);
        this.divideResultSet(request, mv, opResult); //结果集分页
        this.SaveOperationResult(mv, opResult); //返回结果集
        return mv;
    }

    private void getResultSetText(List<TicketStorageParaIn> resultSet, ModelAndView mv) {
        //票卡主类型

        List<PubFlag> icCardMains = (List<PubFlag>) mv.getModelMap().get(StorageOutInBaseController.IC_CARD_MAIN);
        //票卡子类型
        List<PubFlag> icCardSub = (List<PubFlag>) mv.getModelMap().get(StorageOutInBaseController.IC_CARD_SUB);
        //参数标志
        List<PubFlag> paraFlags = (List<PubFlag>) mv.getModelMap().get(StorageOutInBaseController.PARA_FLAG);

        //仓库
        List<PubFlag> storages = (List<PubFlag>) mv.getModelMap().get(StorageOutInBaseController.STORAGES);

        for (TicketStorageParaIn dl : resultSet) {
            if (icCardMains != null && !icCardMains.isEmpty()) {
                dl.setIc_main_type_name(DBUtil.getTextByCode(dl.getIc_main_type(), icCardMains));

            }
            if (icCardSub != null && !icCardSub.isEmpty()) {
                dl.setIc_sub_type_name(DBUtil.getTextByCode(dl.getIc_sub_type(), dl.getIc_main_type(), icCardSub));

            }
            if (paraFlags != null && !paraFlags.isEmpty()) {
                dl.setPara_flag_name(DBUtil.getTextByCode(dl.getPara_flag(), paraFlags));

            }
            if (storages != null && !storages.isEmpty()) {
                dl.setStorage_id_name(DBUtil.getTextByCode(dl.getStorage_id(), storages));

            }
            
            dl.setRpt_date(dl.getRpt_date().substring(0,10));
            dl.setSys_time(dl.getSys_time().substring(0,10));

        }
    }

    public OperationResult queryParm(HttpServletRequest request, TicketStorageParaInMapper tsdcpMapper, OperationLogMapper opLogMapper) throws Exception {
        LogUtil logUtil = new LogUtil();
        TicketStorageParaIn queryCondition;
        List<TicketStorageParaIn> resultSet;
        OperationResult or = new OperationResult();
        try {
            queryCondition = this.getQueryCondition(request, or);
            if (queryCondition == null) {
                return null;
            }
            resultSet = tsdcpMapper.queryParm(queryCondition);
            User user = (User) request.getSession().getAttribute("User");
        String operatorID = user.getAccount();
        List<TicketStorageParaIn> filterVector = findOperResult(operatorID, resultSet);
            or.setReturnResultSet(filterVector);

        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + resultSet.size() + "条记录", opLogMapper);
        return or;

    }

    public OperationResult addParm(HttpServletRequest request, TicketStorageParaInMapper tsdspMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        TicketStorageParaIn po = this.getReqAttribute(request);
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
    private TicketStorageParaIn getQueryCondition(HttpServletRequest request, OperationResult or) {
        TicketStorageParaIn tsdsp = new TicketStorageParaIn();
        String q_rpt_date_start = FormUtil.getParameter(request, "q_rpt_date_start");
        String q_rpt_date_end = FormUtil.getParameter(request, "q_rpt_date_end");
        String q_ic_main_type = FormUtil.getParameter(request, "q_ic_main_type");
        String q_ic_sub_type = FormUtil.getParameter(request, "q_ic_sub_type");
        String q_para_flag = FormUtil.getParameter(request, "q_para_flag");
        String q_storage_id = FormUtil.getParameter(request, "q_storage_id");

        tsdsp.setRpt_date_start(q_rpt_date_start);
        tsdsp.setRpt_date_end(q_rpt_date_end);
        tsdsp.setIc_main_type(q_ic_main_type);
        tsdsp.setIc_sub_type(q_ic_sub_type);
        tsdsp.setPara_flag(q_para_flag);
        tsdsp.setStorage_id(q_storage_id);

        return tsdsp;
    }

    public TicketStorageParaIn getReqAttribute(HttpServletRequest request) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        
        TicketStorageParaIn po = new TicketStorageParaIn();
        po.setWater_no(FormUtil.getParameter(request, "d_water_no"));
        po.setRpt_date(FormUtil.getParameter(request, "d_rpt_date"));
        po.setSys_time(sdf.format(new Date()));
        po.setIc_main_type(FormUtil.getParameter(request, "d_ic_main_type"));
        po.setIc_sub_type(FormUtil.getParameter(request, "d_ic_sub_type"));
        po.setStore_num("0"); 
        po.setPara_flag(FormUtil.getParameter(request, "d_para_flag"));
        po.setUseless_num(FormUtil.getParameter(request, "d_useless_num"));
        po.setNew_num(FormUtil.getParameter(request, "d_new_num"));
        po.setNot_in_num(FormUtil.getParameter(request, "d_not_in_num"));
        po.setStorage_id(FormUtil.getParameter(request, "d_storage_id"));
        po.setRemark(FormUtil.getParameter(request, "d_remark"));

        return po;
    }

    private boolean existRecord(TicketStorageParaIn po, TicketStorageParaInMapper tsdcpMapper, OperationLogMapper opLogMapper) {
        List<TicketStorageParaIn> list = tsdcpMapper.getListForAdd(po);
        if (list.isEmpty()) {
            return false;
        }
        return true;

    }

    private int addByTrans(HttpServletRequest request, TicketStorageParaInMapper tsdcpMapper, TicketStorageParaIn po) throws Exception {
        TransactionStatus status = null;
        int n = 0;
        try {

            status = txMgr.getTransaction(this.def);
            try {
                n = tsdcpMapper.addTicketStorageParaIn(po);
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

    public OperationResult modifyParm(HttpServletRequest request, TicketStorageParaInMapper tsdcpMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        TicketStorageParaIn po = this.getReqAttribute(request);
        LogUtil logUtil = new LogUtil();
        int n = 0;

        try {
            n = this.modifyByTrans(request, tsdcpMapper, po);
        } catch (Exception e) {
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_MODIFY, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_MODIFY, request, LogConstant.modifySuccessMsg(n), opLogMapper);

        rmsg.addMessage(LogConstant.modifySuccessMsg(n));

        return rmsg;
    }

    private int modifyByTrans(HttpServletRequest request, TicketStorageParaInMapper tsdcpMapper, TicketStorageParaIn po) throws Exception {
        TransactionStatus status = null;
        int n = 0;
        try {

            status = txMgr.getTransaction(this.def);
            n = tsdcpMapper.modifyTicketStorageParaIn(po);

            txMgr.commit(status);
        } catch (Exception e) {
            if (txMgr != null) {
                txMgr.rollback(status);
            }
            throw e;
        }
        return n;
    }

    public OperationResult deleteParm(HttpServletRequest request, TicketStorageParaInMapper tsdcpMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        Vector<TicketStorageParaIn> pos = this.getReqAttributeForDelete(request);
        TicketStorageParaIn prmVo = new TicketStorageParaIn();
        LogUtil logUtil = new LogUtil();
        int n = 0;

        try {
            n = this.deleteByTrans(request, tsdcpMapper, pos, prmVo);
        } catch (Exception e) {
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_DELETE, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_DELETE, request, LogConstant.delSuccessMsg(n), opLogMapper);

        rmsg.addMessage(LogConstant.delSuccessMsg(n));

        return rmsg;
    }

    private Vector<TicketStorageParaIn> getReqAttributeForDelete(HttpServletRequest request) {
        TicketStorageBorrowUnitDefManage po = new TicketStorageBorrowUnitDefManage();
        String selectIds = request.getParameter("allSelectedIDs");
        Vector<TicketStorageParaIn> selectedItems = this.getDeleteIDs(selectIds);
        return selectedItems;
    }

    private int deleteByTrans(HttpServletRequest request, TicketStorageParaInMapper tsdcpMapper, Vector<TicketStorageParaIn> pos, TicketStorageParaIn prmVo) throws Exception {

        TransactionStatus status = null;
        int n = 0;
        try {
            status = txMgr.getTransaction(this.def);
            for (TicketStorageParaIn po : pos) {
                n += tsdcpMapper.deleteTicketStorageParaIn(po);
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

    private Vector<TicketStorageParaIn> getDeleteIDs(String selectedIds) {
        StringTokenizer st = new StringTokenizer(selectedIds, ";");
        String strIds = null;
        Vector<TicketStorageParaIn> sds = new Vector();
        TicketStorageParaIn sd;
        while (st.hasMoreTokens()) {
            strIds = st.nextToken();
            sd = this.getTicketStorageParaIn(strIds, "#");
            sds.add(sd);
        }
        return sds;

    }

    private TicketStorageParaIn getTicketStorageParaIn(String strIDs, String delim) {
        StringTokenizer st = new StringTokenizer(strIDs, delim);
        String tmp = null;
        int i = 0;
        TicketStorageParaIn sd = new TicketStorageParaIn();
        Vector<TicketStorageParaIn> sds = new Vector();
        while (st.hasMoreTokens()) {
            i++;
            tmp = st.nextToken();
            if (i == 1) {
                sd.setWater_no(tmp);
                continue;
            }
            if (i == 2) {
                sd.setRpt_date(tmp);
                continue;
            }
            if (i == 3) {
                sd.setIc_main_type(tmp);
                continue;
            }
            if (i == 4) {
                sd.setIc_sub_type(tmp);
                continue;
            }
            if (i == 5) {
                sd.setPara_flag(tmp);
                continue;
            }
            if (i == 6) {
                sd.setUseless_num(tmp);
                continue;
            }
            if (i == 7) {
                sd.setNew_num(tmp);
                continue;
            }
            if (i == 8) {
                sd.setNot_in_num(tmp);
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

    public OperationResult queryForOp(HttpServletRequest request, TicketStorageParaInMapper tsdcpMapper, OperationLogMapper opLogMapper, OperationResult opResult,String command) throws Exception {
        LogUtil logUtil = new LogUtil();
        TicketStorageParaIn queryCondition;
        List<TicketStorageParaIn> resultSet;

        try {
            
            queryCondition = this.getQueryConditionForOp(request,tsdcpMapper);
            if(command.equals(CommandConstant.COMMAND_ADD)){
            resultSet = tsdcpMapper.getListForAdd(queryCondition);
            opResult.setReturnResultSet(resultSet);
            logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + resultSet.size() + "条记录", opLogMapper);
            }else if(command.equals(CommandConstant.COMMAND_MODIFY)){
                resultSet = tsdcpMapper.getListForModify(queryCondition);
               opResult.setReturnResultSet(resultSet);
               logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + resultSet.size() + "条记录", opLogMapper);
            }else {
            resultSet = tsdcpMapper.queryParm(queryCondition);
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

    private TicketStorageParaIn getQueryConditionForOp(HttpServletRequest request,TicketStorageParaInMapper tsdcpMapper) {
        TicketStorageParaIn qCon = new TicketStorageParaIn();
        
        HashMap vQueryControlDefaultValues = null;
        String queryControlDefaultValues = request.getParameter("queryControlDefaultValues");
        String command = request.getParameter("command");
        if (command.equals(CommandConstant.COMMAND_ADD)) {//操作处于添加模式
            if (command.equals(CommandConstant.COMMAND_DELETE)) {
                return null;
            }
            String water_no = tsdcpMapper.getWaterNo();
            qCon.setWater_no(water_no);
            qCon.setRpt_date(FormUtil.getParameter(request, "d_rpt_date"));
            qCon.setIc_main_type(FormUtil.getParameter(request, "d_ic_main_type"));
            qCon.setIc_sub_type(FormUtil.getParameter(request, "d_ic_sub_type"));
            qCon.setPara_flag(FormUtil.getParameter(request, "d_para_flag"));
            qCon.setUseless_num(FormUtil.getParameter(request, "d_useless_num"));
            qCon.setNew_num(FormUtil.getParameter(request, "d_new_num"));
            qCon.setNot_in_num(FormUtil.getParameter(request, "d_not_in_num"));
            qCon.setStorage_id(FormUtil.getParameter(request, "d_storage_id"));
            qCon.setRemark(FormUtil.getParameter(request, "d_remark"));

        }else if(command.equals(CommandConstant.COMMAND_MODIFY)) {//操作处于添加模式
            {
            qCon.setWater_no(FormUtil.getParameter(request, "d_water_no"));
            qCon.setRpt_date(FormUtil.getParameter(request, "d_rpt_date"));
            qCon.setIc_main_type(FormUtil.getParameter(request, "d_ic_main_type"));
            qCon.setIc_sub_type(FormUtil.getParameter(request, "d_ic_sub_type"));
            qCon.setPara_flag(FormUtil.getParameter(request, "d_para_flag"));
            qCon.setUseless_num(FormUtil.getParameter(request, "d_useless_num"));
            qCon.setNew_num(FormUtil.getParameter(request, "d_new_num"));
            qCon.setNot_in_num(FormUtil.getParameter(request, "d_not_in_num"));
            qCon.setStorage_id(FormUtil.getParameter(request, "d_storage_id"));
            qCon.setRemark(FormUtil.getParameter(request, "d_remark"));
        }
        } else {//操作处于非添加模式
            vQueryControlDefaultValues = FormUtil.getQueryControlDefaultValues(queryControlDefaultValues);
            if (!vQueryControlDefaultValues.isEmpty()) {
                qCon.setRpt_date_start(FormUtil.getQueryControlDefaultValue(vQueryControlDefaultValues, "q_rpt_date_start"));
                qCon.setRpt_date_end(FormUtil.getQueryControlDefaultValue(vQueryControlDefaultValues, "q_rpt_date_end"));
                qCon.setIc_main_type(FormUtil.getQueryControlDefaultValue(vQueryControlDefaultValues, "q_ic_main_type"));
                qCon.setIc_sub_type(FormUtil.getQueryControlDefaultValue(vQueryControlDefaultValues, "q_ic_sub_type"));
                qCon.setPara_flag(FormUtil.getQueryControlDefaultValue(vQueryControlDefaultValues, "q_para_flag"));
                qCon.setStorage_id(FormUtil.getQueryControlDefaultValue(vQueryControlDefaultValues, "q_storage_id"));

            }
        }

        return qCon;
    }


       private Vector findOperResult(String sys_operator_id, List<TicketStorageParaIn> resultSet) throws Exception{
        Set storeSet = getUserStoreSet(sys_operator_id);
        Vector returnVector = new Vector();
//        FrameDBUtil util = new FrameDBUtil();
        if(!storeSet.contains("9999")){
            for (Object object : resultSet) {
                TicketStorageParaIn infoVo = (TicketStorageParaIn) object;
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
        TicketStorageParaIn vo = new TicketStorageParaIn();
        vo.setSys_operator_id(operatorId);
       List<TicketStorageParaIn> lists = tspiMapper.getUserStoreSet(vo); 
        if (lists != null && lists.size() > 0) {
            String sysStorageId;
            for (TicketStorageParaIn tsscm : lists) {
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
          @RequestMapping("/TicketStorageParaInExportAll")
    public void exportExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	
        List results = this.getBufferElementsForCurClass(request,"com.goldsign.acc.app.basicinfo.entity.TicketStorageParaIn");
        List<Map<String,String>> queryResults = this.entityToMap(results);
        ExpUtil.renderExportExcelPath(response, ExpUtil.exportExcel(queryResults, request));
    }

    private List<Map<String, String>> entityToMap(List results) {
        List<Map<String,String>> list =  new ArrayList<>();
        int row = results.size();
        for(int n=0;n<row ;n++){
            TicketStorageParaIn vo = (TicketStorageParaIn)results.get(n);
            Map<String,String> map = new HashMap<>();
            map.put("water_no", vo.getWater_no());
            map.put("rpt_date", vo.getRpt_date());
            map.put("sys_time", vo.getSys_time());
            map.put("ic_main_type_name", vo.getIc_main_type_name());
            map.put("ic_sub_type_name", vo.getIc_sub_type_name());
            map.put("para_flag_name", vo.getPara_flag_name());
            map.put("useless_num", vo.getUseless_num());
            map.put("new_num", vo.getNew_num());
            map.put("not_in_num", vo.getNot_in_num());
            map.put("storage_id_name", vo.getStorage_id_name());
            map.put("remark", vo.getRemark());
           
            list.add(map);
        }
        return list;
    }


}
