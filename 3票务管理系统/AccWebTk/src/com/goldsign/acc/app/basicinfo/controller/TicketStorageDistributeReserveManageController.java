/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.basicinfo.controller;

import com.goldsign.acc.app.basicinfo.entity.TicketStorageBorrowUnitDefManage;
import com.goldsign.acc.app.basicinfo.entity.TicketStorageDistributeReserveManage;
import com.goldsign.acc.app.basicinfo.entity.TicketStorageDistributeStationParm;
import com.goldsign.acc.app.basicinfo.mapper.TicketStorageDistributeReserveManageMapper;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
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
 * @author liudezeng 配票保有量参数
 */
@Controller
public class TicketStorageDistributeReserveManageController extends StorageOutInBaseController {

    @Autowired
    private TicketStorageDistributeReserveManageMapper tsdrmpMapper;

    @RequestMapping(value = "/ticketStorageDistributeReserveManage")
    public ModelAndView service(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView("/jsp/basicinfo/ticketStorageDistributeReserveManage.jsp");
        String command = request.getParameter("command");
        OperationResult opResult = new OperationResult();
        try {
            if (command != null) {
                command = command.trim();
                if (command.equals(CommandConstant.COMMAND_QUERY)) {
                    opResult = this.queryParm(request, this.tsdrmpMapper, this.operationLogMapper);
                }
                if (command.equals(CommandConstant.COMMAND_ADD)) {
                    opResult = this.addParm(request, this.tsdrmpMapper, this.operationLogMapper);
                }
                if (command.equals(CommandConstant.COMMAND_DELETE)) {
                    opResult = this.deleteParm(request, this.tsdrmpMapper, this.operationLogMapper);
                }
                if (command.equals(CommandConstant.COMMAND_MODIFY)) {
                    opResult = this.modifyParm(request, this.tsdrmpMapper, this.operationLogMapper);
                }
                if (command != null) {
                    if (command.equals(CommandConstant.COMMAND_ADD) || command.equals(CommandConstant.COMMAND_DELETE) || command.equals(CommandConstant.COMMAND_MODIFY)) {
                        this.queryForOp(request, this.tsdrmpMapper, this.operationLogMapper, opResult);

                    }
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
            opResult.addMessage(e.getMessage());
        }

        String[] attrNames = {CARD_MONEYS,IC_CARD_MAIN, IC_CARD_SUB, IC_CARD_MAIN_SUB, IC_LINES, IC_STATIONS, IC_LINE_STATIONS,STORAGES};
        this.setPageOptions(attrNames, mv, request, response); //设置页面线路、车站、票卡、仓库等选项值、版本
        this.getResultSetText((List<TicketStorageDistributeReserveManage>) opResult.getReturnResultSet(), mv);
        this.baseHandler(request, response, mv);
        this.divideResultSet(request, mv, opResult); //结果集分页
        this.SaveOperationResult(mv, opResult); //返回结果集 
        return mv;
    }

    private void getResultSetText(List<TicketStorageDistributeReserveManage> resultSet, ModelAndView mv) {
        //票卡主类型

        List<PubFlag> icCardMains = (List<PubFlag>) mv.getModelMap().get(StorageOutInBaseController.IC_CARD_MAIN);
        //票卡子类型
        List<PubFlag> icCardSub = (List<PubFlag>) mv.getModelMap().get(StorageOutInBaseController.IC_CARD_SUB);
        List<PubFlag> icLines = (List<PubFlag>) mv.getModelMap().get(StorageOutInBaseController.IC_LINES);
        List<PubFlag> icStations = (List<PubFlag>) mv.getModelMap().get(StorageOutInBaseController.IC_STATIONS);
        //仓库
        List<PubFlag> storages = (List<PubFlag>) mv.getModelMap().get(StorageOutInBaseController.STORAGES);

        for (TicketStorageDistributeReserveManage dl : resultSet) {
            if (icCardMains != null && !icCardMains.isEmpty()) {
                dl.setIc_main_type_name(DBUtil.getTextByCode(dl.getIc_main_type(), icCardMains));

            }
            if (icCardSub != null && !icCardSub.isEmpty()) {
                dl.setIc_sub_type_name(DBUtil.getTextByCode(dl.getIc_sub_type(), dl.getIc_main_type(), icCardSub));

            }
            if (icLines != null && !icLines.isEmpty()) {
                dl.setLine_id_name(DBUtil.getTextByCode(dl.getLine_id(), icLines));

            }
            if (icStations != null && !icStations.isEmpty()) {
                dl.setStation_id_name(DBUtil.getTextByCode(dl.getStation_id(), dl.getLine_id(), icStations));

            }
            if (storages != null && !storages.isEmpty()) {
                dl.setStorage_id_name(DBUtil.getTextByCode(dl.getStorage_id(), storages));

            }
            

        }
    }

    public OperationResult queryParm(HttpServletRequest request, TicketStorageDistributeReserveManageMapper tsdcpMapper, OperationLogMapper opLogMapper) throws Exception {
        LogUtil logUtil = new LogUtil();
        TicketStorageDistributeReserveManage queryCondition;
        List<TicketStorageDistributeReserveManage> resultSet;
        OperationResult or = new OperationResult();
        try {
            queryCondition = this.getQueryCondition(request, or);
            if (queryCondition == null) {
                return null;
            }
            resultSet = tsdcpMapper.queryParm(queryCondition);
            or.setReturnResultSet(resultSet);

        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + resultSet.size() + "条记录", opLogMapper);
        return or;

    }

    public OperationResult addParm(HttpServletRequest request, TicketStorageDistributeReserveManageMapper tsdspMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        TicketStorageDistributeReserveManage po = this.getReqAttribute(request);
        LogUtil logUtil = new LogUtil();
        int n = 0;

//        String preMsg = "服务费率：" + "主键：" + po.getAgent_id()+ ":";
        try {
            if (this.existRecord(po, tsdspMapper, opLogMapper)) {
                rmsg.addMessage("记录已存在！");
                return rmsg;
            }

            n = this.addByTrans(request, tsdspMapper, po);

        } catch (Exception e) {
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_ADD, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_ADD, request, LogConstant.addSuccessMsg(n), opLogMapper);

        rmsg.addMessage(LogConstant.addSuccessMsg(n));

        return rmsg;
    }

    //获取查询参数
    private TicketStorageDistributeReserveManage getQueryCondition(HttpServletRequest request, OperationResult or) {
        TicketStorageDistributeReserveManage tsdsp = new TicketStorageDistributeReserveManage();
        String q_ic_main_type = FormUtil.getParameter(request, "q_ic_main_type");
        String q_ic_sub_type = FormUtil.getParameter(request, "q_ic_sub_type");
        String q_storage_id = FormUtil.getParameter(request, "q_storage_id");
        String q_line_id = FormUtil.getParameter(request, "q_line_id");
        String q_station_id = FormUtil.getParameter(request, "q_station_id");

        tsdsp.setIc_main_type(q_ic_main_type);
        tsdsp.setIc_sub_type(q_ic_sub_type);
        tsdsp.setStorage_id(q_storage_id);
        tsdsp.setLine_id(q_line_id);
        tsdsp.setStation_id(q_station_id);

        return tsdsp;
    }

    public TicketStorageDistributeReserveManage getReqAttribute(HttpServletRequest request) {
        TicketStorageDistributeReserveManage po = new TicketStorageDistributeReserveManage();
        po.setLine_id(FormUtil.getParameter(request, "d_line_id"));
        po.setStation_id(FormUtil.getParameter(request, "d_station_id"));
        po.setStorage_id(FormUtil.getParameter(request, "d_storage_id"));
        po.setIc_main_type(FormUtil.getParameter(request, "d_ic_main_type"));
        po.setIc_sub_type(FormUtil.getParameter(request, "d_ic_sub_type"));
        po.setCard_money(FormUtil.getParameter(request, "d_card_money"));
        po.setReverve_num(FormUtil.getParameter(request, "d_reverve_num"));
        po.setRemark(FormUtil.getParameter(request, "d_remark"));

        return po;
    }

    private boolean existRecord(TicketStorageDistributeReserveManage po, TicketStorageDistributeReserveManageMapper tsdcpMapper, OperationLogMapper opLogMapper) {
        List<TicketStorageDistributeReserveManage> list = null;
        try {
            list = tsdcpMapper.getListForAdd(po);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (list.isEmpty()) {
            return false;
        }
        return true;

    }

    private int addByTrans(HttpServletRequest request, TicketStorageDistributeReserveManageMapper tsdcpMapper, TicketStorageDistributeReserveManage po) throws Exception {
        TransactionStatus status = null;
        int n = 0;
        try {

            status = txMgr.getTransaction(this.def);
            try {
                n = tsdcpMapper.addTicketStorageDistributeReserveManage(po);
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

    public OperationResult modifyParm(HttpServletRequest request, TicketStorageDistributeReserveManageMapper tsdcpMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        TicketStorageDistributeReserveManage po = this.getReqAttribute(request);
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

    private int modifyByTrans(HttpServletRequest request, TicketStorageDistributeReserveManageMapper tsdcpMapper, TicketStorageDistributeReserveManage po) throws Exception {
        TransactionStatus status = null;
        int n = 0;
        try {

            status = txMgr.getTransaction(this.def);
            n = tsdcpMapper.modifyTicketStorageDistributeReserveManage(po);

            txMgr.commit(status);
        } catch (Exception e) {
            if (txMgr != null) {
                txMgr.rollback(status);
            }
            throw e;
        }
        return n;
    }

    public OperationResult deleteParm(HttpServletRequest request, TicketStorageDistributeReserveManageMapper tsdcpMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        Vector<TicketStorageDistributeReserveManage> pos = this.getReqAttributeForDelete(request);
        TicketStorageDistributeReserveManage prmVo = new TicketStorageDistributeReserveManage();
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

    private Vector<TicketStorageDistributeReserveManage> getReqAttributeForDelete(HttpServletRequest request) {
        TicketStorageBorrowUnitDefManage po = new TicketStorageBorrowUnitDefManage();
        String selectIds = request.getParameter("allSelectedIDs");
        Vector<TicketStorageDistributeReserveManage> selectedItems = this.getDeleteIDs(selectIds);
        return selectedItems;
    }

    private int deleteByTrans(HttpServletRequest request, TicketStorageDistributeReserveManageMapper tsdcpMapper, Vector<TicketStorageDistributeReserveManage> pos, TicketStorageDistributeReserveManage prmVo) throws Exception {

        TransactionStatus status = null;
        int n = 0;
        try {
            status = txMgr.getTransaction(this.def);
            for (TicketStorageDistributeReserveManage po : pos) {
                n += tsdcpMapper.deleteTicketStorageDistributeReserveManage(po);
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

    private Vector<TicketStorageDistributeReserveManage> getDeleteIDs(String selectedIds) {
        StringTokenizer st = new StringTokenizer(selectedIds, ";");
        String strIds = null;
        Vector<TicketStorageDistributeReserveManage> sds = new Vector();
        TicketStorageDistributeReserveManage sd;
        while (st.hasMoreTokens()) {
            strIds = st.nextToken();
            sd = this.getTicketStorageDistributeReserveManage(strIds, "#");
            sds.add(sd);
        }
        return sds;

    }

    private TicketStorageDistributeReserveManage getTicketStorageDistributeReserveManage(String strIDs, String delim) {
        StringTokenizer st = new StringTokenizer(strIDs, delim);
        String tmp = null;
        int i = 0;
        TicketStorageDistributeReserveManage sd = new TicketStorageDistributeReserveManage();
        Vector<TicketStorageDistributeReserveManage> sds = new Vector();
        while (st.hasMoreTokens()) {
            i++;
            tmp = st.nextToken();
            if (i == 1) {
                sd.setLine_id(tmp);
                continue;
            }
            if (i == 2) {
                sd.setStation_id(tmp);
                continue;
            }
            if (i == 3) {
                sd.setStorage_id(tmp);
                continue;
            }
            if (i == 4) {
                sd.setIc_main_type(tmp);
                continue;
            }
            if (i == 5) {
                sd.setIc_sub_type(tmp);
                continue;
            }
            if (i == 6) {
                sd.setCard_money(tmp);
                continue;
            }
            if (i == 7) {
                sd.setReverve_num(tmp);
                continue;
            }
            if (i == 8) {
                sd.setRemark(tmp);
                continue;
            }

        }
        return sd;

    }

    public OperationResult queryForOp(HttpServletRequest request, TicketStorageDistributeReserveManageMapper tsdcpMapper, OperationLogMapper opLogMapper, OperationResult opResult) throws Exception {
        LogUtil logUtil = new LogUtil();
        TicketStorageDistributeReserveManage queryCondition;
        List<TicketStorageDistributeReserveManage> resultSet;

        try {
            queryCondition = this.getQueryConditionForOp(request);
            resultSet = tsdcpMapper.queryParm(queryCondition);
            opResult.setReturnResultSet(resultSet);
            //or.addMessage("成功查询" + resultSet.size() + "条记录");
        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + resultSet.size() + "条记录", opLogMapper);
        return opResult;

    }

    private TicketStorageDistributeReserveManage getQueryConditionForOp(HttpServletRequest request) {
        TicketStorageDistributeReserveManage qCon = new TicketStorageDistributeReserveManage();
        HashMap vQueryControlDefaultValues = null;
        String queryControlDefaultValues = request.getParameter("queryControlDefaultValues");
        String command = request.getParameter("command");
        if (FormUtil.isAddOrModifyMode(request)) {//操作处于添加或修改模式
            if (command.equals(CommandConstant.COMMAND_DELETE)) {
                return null;
            }
            qCon.setLine_id(FormUtil.getParameter(request, "d_line_id"));
            qCon.setStation_id(FormUtil.getParameter(request, "d_station_id"));
            qCon.setStorage_id(FormUtil.getParameter(request, "d_storage_id"));
            qCon.setIc_main_type(FormUtil.getParameter(request, "d_ic_main_type"));
            qCon.setIc_sub_type(FormUtil.getParameter(request, "d_ic_sub_type"));
            qCon.setCard_money(FormUtil.getParameter(request, "d_card_money"));
            qCon.setReverve_num(FormUtil.getParameter(request, "d_reverve_num"));
            qCon.setRemark(FormUtil.getParameter(request, "d_remark"));

        } else {//操作处于非添加模式
            vQueryControlDefaultValues = FormUtil.getQueryControlDefaultValues(queryControlDefaultValues);
            if (!vQueryControlDefaultValues.isEmpty()) {
                qCon.setLine_id(FormUtil.getQueryControlDefaultValue(vQueryControlDefaultValues, "q_line_id"));
                qCon.setStation_id(FormUtil.getQueryControlDefaultValue(vQueryControlDefaultValues, "q_station_id"));
                qCon.setStorage_id(FormUtil.getQueryControlDefaultValue(vQueryControlDefaultValues, "q_storage_id"));
                qCon.setIc_main_type(FormUtil.getQueryControlDefaultValue(vQueryControlDefaultValues, "q_ic_main_type"));
                qCon.setIc_sub_type(FormUtil.getQueryControlDefaultValue(vQueryControlDefaultValues, "q_ic_sub_type"));

            }
        }

        return qCon;
    }
    
         @RequestMapping("/TicketStorageDistributeReserveManageExportAll")
    public void exportExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	
        List results = this.getBufferElementsForCurClass(request,"com.goldsign.acc.app.basicinfo.entity.TicketStorageDistributeReserveManage");
        List<Map<String,String>> queryResults = this.entityToMap(results);
        ExpUtil.renderExportExcelPath(response, ExpUtil.exportExcel(queryResults, request));
    }

    private List<Map<String, String>> entityToMap(List results) {
        List<Map<String,String>> list =  new ArrayList<>();
        int row = results.size();
        for(int n=0;n<row ;n++){
            TicketStorageDistributeReserveManage vo = (TicketStorageDistributeReserveManage)results.get(n);
            Map<String,String> map = new HashMap<>();
            map.put("line_id_name", vo.getLine_id_name());
            map.put("station_id_name", vo.getStation_id_name());
            map.put("storage_id_name", vo.getStorage_id_name());
            map.put("ic_main_type_name", vo.getIc_main_type_name());
            map.put("ic_sub_type_name", vo.getIc_sub_type_name());
            map.put("card_money", vo.getCard_money());
            map.put("reverve_num", vo.getReverve_num());
            map.put("remark", vo.getRemark());
           
            list.add(map);
        }
        return list;
    }

}
