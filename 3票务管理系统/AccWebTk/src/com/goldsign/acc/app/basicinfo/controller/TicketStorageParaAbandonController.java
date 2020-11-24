/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.basicinfo.controller;

import com.goldsign.acc.app.basicinfo.entity.TicketStorageBorrowUnitDefManage;
import com.goldsign.acc.app.basicinfo.entity.TicketStorageListParaIn;
import com.goldsign.acc.app.basicinfo.entity.TicketStorageParaAbandon;
import com.goldsign.acc.app.basicinfo.mapper.TicketStorageParaAbandonMapper;
import com.goldsign.acc.app.basicinfo.mapper.TicketStorageParaAbandonMapper;
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
 * @author liudezeng 弃票回收箱参数
 */
@Controller
public class TicketStorageParaAbandonController extends StorageOutInBaseController {

    @Autowired
    private TicketStorageParaAbandonMapper tspsMapper;

    @RequestMapping(value = "/ticketStorageParaAbandon")
    public ModelAndView service(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView("/jsp/basicinfo/ticketStorageParaAbandon.jsp");
        String command = request.getParameter("command");
        OperationResult opResult = new OperationResult();
        //取操作员ID
        User user = (User) request.getSession().getAttribute("User");
        String sys_operator_id = user.getAccount();

        try {
            if (command != null) {
                command = command.trim();
                if (command.equals(CommandConstant.COMMAND_QUERY)) {
                    opResult = this.queryParm(request, this.tspsMapper, this.operationLogMapper);
                }
                if (command.equals(CommandConstant.COMMAND_ADD)) {
                    opResult = this.addParm(request, this.tspsMapper, this.operationLogMapper);
                }
                if (command.equals(CommandConstant.COMMAND_DELETE)) {
                    opResult = this.deleteParm(request, this.tspsMapper, this.operationLogMapper);
                }
                if (command.equals(CommandConstant.COMMAND_MODIFY)) {
                    opResult = this.modifyParm(request, this.tspsMapper, this.operationLogMapper);
                }
                if (command != null) {
                    if (command.equals(CommandConstant.COMMAND_ADD) || command.equals(CommandConstant.COMMAND_DELETE) || command.equals(CommandConstant.COMMAND_MODIFY)) {
                        this.queryForOp(request, this.tspsMapper, this.operationLogMapper, opResult, command);

                    }
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
            opResult.addMessage(e.getMessage());
        }

        String[] attrNames = {IC_LINES, IC_STATIONS, IC_LINE_STATIONS};
        this.setPageOptions(attrNames, mv, request, response); //设置页面线路、车站、票卡、仓库等选项值、版本
        this.getResultSetText((List<TicketStorageParaAbandon>) opResult.getReturnResultSet(), mv);
        this.baseHandler(request, response, mv);
        this.divideResultSet(request, mv, opResult); //结果集分页
        this.SaveOperationResult(mv, opResult); //返回结果集
        return mv;
    }

    private void getResultSetText(List<TicketStorageParaAbandon> resultSet, ModelAndView mv) {
        //线路名

        List<PubFlag> icLines = (List<PubFlag>) mv.getModelMap().get(StorageOutInBaseController.IC_LINES);
        List<PubFlag> icStations = (List<PubFlag>) mv.getModelMap().get(StorageOutInBaseController.IC_STATIONS);

        for (TicketStorageParaAbandon dl : resultSet) {
            if (icLines != null && !icLines.isEmpty()) {
                dl.setLine_id_name(DBUtil.getTextByCode(dl.getLine_id(), icLines));

            }
            if (icStations != null && !icStations.isEmpty()) {
                dl.setStation_id_name(DBUtil.getTextByCode(dl.getStation_id(), dl.getLine_id(), icStations));

            }
            dl.setReport_date(dl.getReport_date().substring(0, 10));

        }
    }

    public OperationResult queryParm(HttpServletRequest request, TicketStorageParaAbandonMapper tslpiMapper, OperationLogMapper opLogMapper) throws Exception {
        LogUtil logUtil = new LogUtil();
        TicketStorageParaAbandon queryCondition;
        List<TicketStorageParaAbandon> resultSet;
        OperationResult or = new OperationResult();
        try {
            queryCondition = this.getQueryCondition(request, or);
            if (queryCondition == null) {
                return null;
            }
            resultSet = tslpiMapper.queryParm(queryCondition);
            User user = (User) request.getSession().getAttribute("User");
            String operatorID = user.getAccount();
            List<TicketStorageParaAbandon> filterVector = findOperResult(operatorID, resultSet);
            or.setReturnResultSet(filterVector);

        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + resultSet.size() + "条记录", opLogMapper);
        return or;

    }

    public OperationResult addParm(HttpServletRequest request, TicketStorageParaAbandonMapper tsdspMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        TicketStorageParaAbandon po = this.getReqAttribute(request);
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
    private TicketStorageParaAbandon getQueryCondition(HttpServletRequest request, OperationResult or) {
        TicketStorageParaAbandon tslpi = new TicketStorageParaAbandon();

        String q_report_date_start = FormUtil.getParameter(request, "q_report_date_start");
        String q_report_date_end = FormUtil.getParameter(request, "q_report_date_end");
        String q_line_id = FormUtil.getParameter(request, "q_line_id");
        String q_station_id = FormUtil.getParameter(request, "q_station_id");

        tslpi.setReport_date_start(q_report_date_start);
        tslpi.setReport_date_end(q_report_date_end);
        tslpi.setLine_id(q_line_id);
        tslpi.setStation_id(q_station_id);
        return tslpi;
    }

    public TicketStorageParaAbandon getReqAttribute(HttpServletRequest request) {
        TicketStorageParaAbandon po = new TicketStorageParaAbandon();
        po.setWater_no(FormUtil.getParameter(request, "d_water_no"));
        po.setReport_date(FormUtil.getParameter(request, "d_report_date"));
        po.setLine_id(FormUtil.getParameter(request, "d_line_id"));
        po.setStation_id(FormUtil.getParameter(request, "d_station_id"));
        po.setRemark(FormUtil.getParameter(request, "d_remark"));

        return po;
    }

    private boolean existRecord(TicketStorageParaAbandon po, TicketStorageParaAbandonMapper tslpiMapper, OperationLogMapper opLogMapper) {
        List<TicketStorageParaAbandon> list = tslpiMapper.getListForAdd(po);
        if (list.isEmpty()) {
            return false;
        }
        return true;

    }

    private int addByTrans(HttpServletRequest request, TicketStorageParaAbandonMapper tslpiMapper, TicketStorageParaAbandon po) throws Exception {
        TransactionStatus status = null;
        int n = 0;
        try {

            status = txMgr.getTransaction(this.def);
            try {
                n = tslpiMapper.addTicketStorageParaAbandon(po);
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

    public OperationResult modifyParm(HttpServletRequest request, TicketStorageParaAbandonMapper tslpiMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        TicketStorageParaAbandon po = this.getReqAttribute(request);
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

    private int modifyByTrans(HttpServletRequest request, TicketStorageParaAbandonMapper tslpiMapper, TicketStorageParaAbandon po) throws Exception {
        TransactionStatus status = null;
        int n = 0;
        try {

            status = txMgr.getTransaction(this.def);
            n = tslpiMapper.modifyTicketStorageParaAbandon(po);

            txMgr.commit(status);
        } catch (Exception e) {
            if (txMgr != null) {
                txMgr.rollback(status);
            }
            throw e;
        }
        return n;
    }

    public OperationResult deleteParm(HttpServletRequest request, TicketStorageParaAbandonMapper tslpiMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        Vector<TicketStorageParaAbandon> pos = this.getReqAttributeForDelete(request);
        TicketStorageParaAbandon prmVo = new TicketStorageParaAbandon();
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

    private Vector<TicketStorageParaAbandon> getReqAttributeForDelete(HttpServletRequest request) {
        TicketStorageBorrowUnitDefManage po = new TicketStorageBorrowUnitDefManage();
        String selectIds = request.getParameter("allSelectedIDs");
        Vector<TicketStorageParaAbandon> selectedItems = this.getDeleteIDs(selectIds);
        return selectedItems;
    }

    private int deleteByTrans(HttpServletRequest request, TicketStorageParaAbandonMapper tslpiMapper, Vector<TicketStorageParaAbandon> pos, TicketStorageParaAbandon prmVo) throws Exception {

        TransactionStatus status = null;
        int n = 0;
        try {
            status = txMgr.getTransaction(this.def);
            for (TicketStorageParaAbandon po : pos) {
                n += tslpiMapper.deleteTicketStorageParaAbandon(po);
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

    private Vector<TicketStorageParaAbandon> getDeleteIDs(String selectedIds) {
        StringTokenizer st = new StringTokenizer(selectedIds, ";");
        String strIds = null;
        Vector<TicketStorageParaAbandon> sds = new Vector();
        TicketStorageParaAbandon sd;
        while (st.hasMoreTokens()) {
            strIds = st.nextToken();
            sd = this.getTicketStorageParaAbandon(strIds, "#");
            sds.add(sd);
        }
        return sds;

    }

    private TicketStorageParaAbandon getTicketStorageParaAbandon(String strIDs, String delim) {
        StringTokenizer st = new StringTokenizer(strIDs, delim);
        String tmp = null;
        int i = 0;
        TicketStorageParaAbandon sd = new TicketStorageParaAbandon();
        Vector<TicketStorageParaAbandon> sds = new Vector();
        while (st.hasMoreTokens()) {
            i++;
            tmp = st.nextToken();
            if (i == 1) {
                sd.setWater_no(tmp);
                continue;
            }
            if (i == 2) {
                sd.setReport_date(tmp);
                continue;
            }
            if (i == 3) {
                sd.setLine_id(tmp);
                continue;
            }
            if (i == 4) {
                sd.setStation_id(tmp);
                continue;
            }
            if (i == 5) {
                sd.setRemark(tmp);
                continue;
            }

        }
        return sd;

    }

    public OperationResult queryForOp(HttpServletRequest request, TicketStorageParaAbandonMapper tslpiMapper, OperationLogMapper opLogMapper, OperationResult opResult, String command) throws Exception {
        LogUtil logUtil = new LogUtil();
        TicketStorageParaAbandon queryCondition;
        List<TicketStorageParaAbandon> resultSet;

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

    private TicketStorageParaAbandon getQueryConditionForOp(HttpServletRequest request,TicketStorageParaAbandonMapper tslpiMapper) {
        TicketStorageParaAbandon qCon = new TicketStorageParaAbandon();
        HashMap vQueryControlDefaultValues = null;
        String queryControlDefaultValues = request.getParameter("queryControlDefaultValues");
        String command = request.getParameter("command");
        if (command.equals(CommandConstant.COMMAND_ADD)) {//操作处于添加模式
            if (command.equals(CommandConstant.COMMAND_DELETE)) {
                return null;
            }
            String water_no = tslpiMapper.getWaterNo();
            qCon.setWater_no(water_no); 
            qCon.setReport_date(FormUtil.getParameter(request, "d_report_date"));
            qCon.setLine_id(FormUtil.getParameter(request, "d_line_id"));
            qCon.setStation_id(FormUtil.getParameter(request, "d_station_id"));
            qCon.setRemark(FormUtil.getParameter(request, "d_remark"));

        } else if (command.equals(CommandConstant.COMMAND_MODIFY)) {//操作处于修改模式
            {
                qCon.setWater_no(FormUtil.getParameter(request, "d_water_no"));
                qCon.setReport_date(FormUtil.getParameter(request, "d_report_date"));
                qCon.setLine_id(FormUtil.getParameter(request, "d_line_id"));
                qCon.setStation_id(FormUtil.getParameter(request, "d_station_id"));
                qCon.setRemark(FormUtil.getParameter(request, "d_remark"));

            }
        } else {//操作处于非添加模式
            vQueryControlDefaultValues = FormUtil.getQueryControlDefaultValues(queryControlDefaultValues);
            if (!vQueryControlDefaultValues.isEmpty()) {
                qCon.setReport_date_start(FormUtil.getQueryControlDefaultValue(vQueryControlDefaultValues, "q_report_date_start"));
                qCon.setReport_date_end(FormUtil.getQueryControlDefaultValue(vQueryControlDefaultValues, "q_report_date_end"));
                qCon.setLine_id(FormUtil.getQueryControlDefaultValue(vQueryControlDefaultValues, "q_line_id"));
                qCon.setStation_id(FormUtil.getQueryControlDefaultValue(vQueryControlDefaultValues, "q_station_id"));

            }
        }

        return qCon;
    }

    private Vector findOperResult(String sys_operator_id, List<TicketStorageParaAbandon> resultSet) throws Exception {
        Set storeSet = getUserStoreSet(sys_operator_id);
        Vector returnVector = new Vector();
//        FrameDBUtil util = new FrameDBUtil();
        if (!storeSet.contains("9999")) {
            for (Object object : resultSet) {
                TicketStorageParaAbandon infoVo = (TicketStorageParaAbandon) object;
                if (storeSet.contains(infoVo.getStorage_id())) {
                    returnVector.add(object);
                }
            }
        } else {
            returnVector.addAll(resultSet);
        }
        return returnVector;
    }

    public Set getUserStoreSet(String operatorId) throws Exception {
        Vector paramList = new Vector();
        Set vectorSet = new TreeSet();
        boolean result = false;
        TicketStorageParaAbandon vo = new TicketStorageParaAbandon();
        vo.setSys_operator_id(operatorId);
        List<TicketStorageParaAbandon> lists = tspsMapper.getUserStoreSet(vo);
        if (lists != null && lists.size() > 0) {
            String sysStorageId;
            for (TicketStorageParaAbandon tsscm : lists) {
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
    
   @RequestMapping("/TicketStorageParaAbandonExportAll")
    public void exportExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	
        List results = this.getBufferElementsForCurClass(request,"com.goldsign.acc.app.basicinfo.entity.TicketStorageParaAbandon");
        List<Map<String,String>> queryResults = this.entityToMap(results);
        ExpUtil.renderExportExcelPath(response, ExpUtil.exportExcel(queryResults, request));
    }

    private List<Map<String, String>> entityToMap(List results) {
        List<Map<String,String>> list =  new ArrayList<>();
        int row = results.size();
        for(int n=0;n<row ;n++){
            TicketStorageParaAbandon vo = (TicketStorageParaAbandon)results.get(n);
            Map<String,String> map = new HashMap<>();
            map.put("water_no", vo.getWater_no());
            map.put("report_date", vo.getReport_date());
            map.put("line_id_name", vo.getLine_id_name());
            map.put("station_id_name", vo.getStation_id_name());
            map.put("remark", vo.getRemark());
           
            list.add(map);
        }
        return list;
    }

}
