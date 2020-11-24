/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.basicinfo.controller;

import com.goldsign.acc.app.basicinfo.entity.TicketStorageBorrowUnitDefManage;
import com.goldsign.acc.app.basicinfo.entity.TicketStorageDistributeStationParm;

import com.goldsign.acc.app.basicinfo.mapper.TicketStorageDistributeStationParmMapper;

import com.goldsign.acc.frame.constant.CommandConstant;
import com.goldsign.acc.frame.constant.LogConstant;
import com.goldsign.acc.frame.controller.StorageOutInBaseController;
import com.goldsign.acc.frame.entity.PubFlag;
import com.goldsign.acc.frame.mapper.BillMapper;
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
 * @author liudezeng
 */
@Controller
public class TicketStorageDistributeStationParmController extends StorageOutInBaseController {

    @Autowired
    private TicketStorageDistributeStationParmMapper tsdspMapper;

    @RequestMapping(value = "/ticketStorageDistributeStationParmManage")
    public ModelAndView service(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView("/jsp/basicinfo/ticketStorageDistributeStationParm.jsp");
        String command = request.getParameter("command");
        String operType = request.getParameter("operType");
        OperationResult opResult = new OperationResult();
        try {
            if (command != null) {
                command = command.trim();
                if (command.equals(CommandConstant.COMMAND_QUERY)) {
                    opResult = this.queryParm(request, this.tsdspMapper, this.operationLogMapper);
                }
                if (command.equals(CommandConstant.COMMAND_ADD)) {
                    opResult = this.addParm(request, this.tsdspMapper, this.operationLogMapper);
                }
                if (command.equals(CommandConstant.COMMAND_DELETE)) {
                    opResult = this.deleteParm(request, this.tsdspMapper, this.operationLogMapper);
                }
                if (command.equals(CommandConstant.COMMAND_MODIFY)) {
                    opResult = this.modifyParm(request, this.tsdspMapper, this.operationLogMapper);
                }
                if (command != null) {
                    if (command.equals(CommandConstant.COMMAND_ADD) || command.equals(CommandConstant.COMMAND_DELETE) || command.equals(CommandConstant.COMMAND_MODIFY)) {
                        this.queryForOp(request, this.tsdspMapper, this.operationLogMapper, opResult);

                    }
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
            opResult.addMessage(e.getMessage());
        }

        String[] attrNames = {IC_LINES, IC_STATIONS, IC_LINE_STATIONS, STORAGES};
        this.setPageOptions(attrNames, mv, request, response); //设置页面线路、车站、票卡、仓库等选项值、版本
        this.getResultSetText((List<TicketStorageDistributeStationParm>) opResult.getReturnResultSet(), mv);
        this.baseHandler(request, response, mv);
        this.divideResultSet(request, mv, opResult); //结果集分页
        this.SaveOperationResult(mv, opResult); //返回结果集
        return mv;
    }

    private void getResultSetText(List<TicketStorageDistributeStationParm> resultSet, ModelAndView mv) {
        //线路名

        List<PubFlag> icLines = (List<PubFlag>) mv.getModelMap().get(StorageOutInBaseController.IC_LINES);
        //车站名
        List<PubFlag> icStations = (List<PubFlag>) mv.getModelMap().get(StorageOutInBaseController.IC_STATIONS);
        //仓库
        List<PubFlag> storages = (List<PubFlag>) mv.getModelMap().get(StorageOutInBaseController.STORAGES);

//        List<PubFlag> stations = (List<PubFlag>) mv.getModelMap().get(StorageOutInBaseController.IC_LINE_STATIONS);
        for (TicketStorageDistributeStationParm dl : resultSet) {
            if (icLines != null && !icLines.isEmpty()) {
                dl.setLine_id_name(DBUtil.getTextByCode(dl.getLine_id(), icLines));

            }
            if (icStations != null && !icStations.isEmpty()) {
                dl.setStation_id_name(DBUtil.getTextByCode(dl.getStation_id(), dl.getLine_id(), icStations));

            }
            if (storages != null && !storages.isEmpty()) {
                dl.setStorage_id_name(DBUtil.getTextByCode(dl.getStorage_id(), storages));

            }
//            if (dl.getStation_id()!= null && !dl.getStation_id().isEmpty()) {
//                    dl.setStation_id_name(DBUtil.getTextByCode(dl.getStation_id(), dl.getLine_id(), stations));
//                }

        }
    }

    public OperationResult queryParm(HttpServletRequest request, TicketStorageDistributeStationParmMapper tsdspMapper, OperationLogMapper opLogMapper) throws Exception {
        LogUtil logUtil = new LogUtil();
        TicketStorageDistributeStationParm queryCondition;
        List<TicketStorageDistributeStationParm> resultSet;
        OperationResult or = new OperationResult();
        try {
            queryCondition = this.getQueryCondition(request, or);
            if (queryCondition == null) {
                return null;
            }
            resultSet = tsdspMapper.queryParm(queryCondition);
            or.setReturnResultSet(resultSet);

        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + resultSet.size() + "条记录", opLogMapper);
        return or;

    }

    public OperationResult addParm(HttpServletRequest request, TicketStorageDistributeStationParmMapper tsdspMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        TicketStorageDistributeStationParm po = this.getReqAttribute(request);
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
    private TicketStorageDistributeStationParm getQueryCondition(HttpServletRequest request, OperationResult or) {
        TicketStorageDistributeStationParm tsdsp = new TicketStorageDistributeStationParm();
        String q_line_id = FormUtil.getParameter(request, "q_line_id");
        String q_station_id = FormUtil.getParameter(request, "q_station_id");
        String q_storage_id = FormUtil.getParameter(request, "q_storage_id");
        tsdsp.setLine_id(q_line_id);
        tsdsp.setStation_id(q_station_id);
        tsdsp.setStorage_id(q_storage_id);
        return tsdsp;
    }

    public TicketStorageDistributeStationParm getReqAttribute(HttpServletRequest request) {
        TicketStorageDistributeStationParm po = new TicketStorageDistributeStationParm();
        po.setLine_id(FormUtil.getParameter(request, "d_line_id"));
        po.setStation_id(FormUtil.getParameter(request, "d_station_id"));
        po.setStorage_id(FormUtil.getParameter(request, "d_storage_id"));
        po.setTvm_store_num(FormUtil.getParameter(request, "d_tvm_store_num"));
        po.setTvm_num(FormUtil.getParameter(request, "d_tvm_num"));
        po.setItm_store_num(FormUtil.getParameter(request, "d_itm_store_num"));
        po.setItm_num(FormUtil.getParameter(request, "d_itm_num"));
        po.setBom_store_num(FormUtil.getParameter(request, "d_bom_store_num"));
        po.setBom_num(FormUtil.getParameter(request, "d_bom_num"));
        po.setRemark(FormUtil.getParameter(request, "d_remark"));

        return po;
    }

    private boolean existRecord(TicketStorageDistributeStationParm po, TicketStorageDistributeStationParmMapper arMapper, OperationLogMapper opLogMapper) {
        List<TicketStorageDistributeStationParm> list = arMapper.getListForAdd(po);
        if (list.isEmpty()) {
            return false;
        }
        return true;

    }

    private int addByTrans(HttpServletRequest request, TicketStorageDistributeStationParmMapper tsdspMapper, TicketStorageDistributeStationParm po) throws Exception {
        TransactionStatus status = null;
        int n = 0;
        try {

            status = txMgr.getTransaction(this.def);
            try {
                n = tsdspMapper.addTicketStorageDistributeStationParm(po);
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

    public OperationResult modifyParm(HttpServletRequest request, TicketStorageDistributeStationParmMapper tsdspMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        TicketStorageDistributeStationParm po = this.getReqAttribute(request);
        LogUtil logUtil = new LogUtil();
        int n = 0;

        try {
            n = this.modifyByTrans(request, tsdspMapper, po);
        } catch (Exception e) {
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_MODIFY, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_MODIFY, request, LogConstant.modifySuccessMsg(n), opLogMapper);

        rmsg.addMessage(LogConstant.modifySuccessMsg(n));

        return rmsg;
    }

    private int modifyByTrans(HttpServletRequest request, TicketStorageDistributeStationParmMapper tsdspMapper, TicketStorageDistributeStationParm po) throws Exception {
        TransactionStatus status = null;
        int n = 0;
        try {

            status = txMgr.getTransaction(this.def);
            n = tsdspMapper.modifyTicketStorageDistributeStationParm(po);

            txMgr.commit(status);
        } catch (Exception e) {
            if (txMgr != null) {
                txMgr.rollback(status);
            }
            throw e;
        }
        return n;
    }

    public OperationResult deleteParm(HttpServletRequest request, TicketStorageDistributeStationParmMapper tsdspMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        Vector<TicketStorageDistributeStationParm> pos = this.getReqAttributeForDelete(request);
        TicketStorageDistributeStationParm prmVo = new TicketStorageDistributeStationParm();
        LogUtil logUtil = new LogUtil();
        int n = 0;

        try {
            n = this.deleteByTrans(request, tsdspMapper, pos, prmVo);
        } catch (Exception e) {
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_DELETE, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_DELETE, request, LogConstant.delSuccessMsg(n), opLogMapper);

        rmsg.addMessage(LogConstant.delSuccessMsg(n));

        return rmsg;
    }

    private Vector<TicketStorageDistributeStationParm> getReqAttributeForDelete(HttpServletRequest request) {
        TicketStorageBorrowUnitDefManage po = new TicketStorageBorrowUnitDefManage();
        String selectIds = request.getParameter("allSelectedIDs");
        Vector<TicketStorageDistributeStationParm> selectedItems = this.getDeleteIDs(selectIds);
        return selectedItems;
    }

    private int deleteByTrans(HttpServletRequest request, TicketStorageDistributeStationParmMapper tsbudmMapper, Vector<TicketStorageDistributeStationParm> pos, TicketStorageDistributeStationParm prmVo) throws Exception {

        TransactionStatus status = null;
        int n = 0;
        try {
            status = txMgr.getTransaction(this.def);
            for (TicketStorageDistributeStationParm po : pos) {
                n += tsbudmMapper.deleteTicketStorageDistributeStationParm(po);
            }

            txMgr.commit(status); 
        } catch (Exception e) {
            if (txMgr != null) {
                txMgr.rollback(status);
            }
            throw e;
        }
        return n;
    }

    private Vector<TicketStorageDistributeStationParm> getDeleteIDs(String selectedIds) {
        StringTokenizer st = new StringTokenizer(selectedIds, ";");
        String strIds = null;
        Vector<TicketStorageDistributeStationParm> sds = new Vector();
        TicketStorageDistributeStationParm sd;
        while (st.hasMoreTokens()) {
            strIds = st.nextToken();
            sd = this.getTicketStorageDistributeStationParm(strIds, "#");
            sds.add(sd);
        }
        return sds;

    }

    private TicketStorageDistributeStationParm getTicketStorageDistributeStationParm(String strIDs, String delim) {
        StringTokenizer st = new StringTokenizer(strIDs, delim);
        String tmp = null;
        int i = 0;
        TicketStorageDistributeStationParm sd = new TicketStorageDistributeStationParm();
        Vector<TicketStorageDistributeStationParm> sds = new Vector();
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
                sd.setTvm_store_num(tmp);
                continue;
            }
            if (i == 5) {
                sd.setTvm_num(tmp);
                continue;
            }
            if (i == 6) {
                sd.setItm_store_num(tmp);
                continue;
            }
            if (i == 7) {
                sd.setItm_num(tmp);
                continue;
            }
            if (i == 8) {
                sd.setBom_store_num(tmp);
                continue;
            }
            if (i == 9) {
                sd.setBom_num(tmp); 
                continue;
            }
            if (i == 10) {
                sd.setRemark(tmp);
                continue;
            }

        }
        return sd;

    }

    public OperationResult queryForOp(HttpServletRequest request, TicketStorageDistributeStationParmMapper tsdspMapper, OperationLogMapper opLogMapper, OperationResult opResult) throws Exception {
        LogUtil logUtil = new LogUtil();
        TicketStorageDistributeStationParm queryCondition;
        List<TicketStorageDistributeStationParm> resultSet;

        try {
            queryCondition = this.getQueryConditionForOp(request);
            resultSet = tsdspMapper.queryParm(queryCondition);
            opResult.setReturnResultSet(resultSet);
            //or.addMessage("成功查询" + resultSet.size() + "条记录");
        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + resultSet.size() + "条记录", opLogMapper);
        return opResult;

    }

    private TicketStorageDistributeStationParm getQueryConditionForOp(HttpServletRequest request) {
        TicketStorageDistributeStationParm qCon = new TicketStorageDistributeStationParm();
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
            qCon.setTvm_store_num(FormUtil.getParameter(request, "d_tvm_store_num"));
            qCon.setTvm_num(FormUtil.getParameter(request, "d_tvm_num"));
            qCon.setItm_store_num(FormUtil.getParameter(request, "d_itm_store_num"));
            qCon.setItm_num(FormUtil.getParameter(request, "d_itm_num"));
            qCon.setBom_store_num(FormUtil.getParameter(request, "d_bom_store_num"));
            qCon.setBom_num(FormUtil.getParameter(request, "d_bom_num"));
            qCon.setRemark(FormUtil.getParameter(request, "d_remark"));

        } else {//操作处于非添加模式
            vQueryControlDefaultValues = FormUtil.getQueryControlDefaultValues(queryControlDefaultValues);
            if (!vQueryControlDefaultValues.isEmpty()) {
                qCon.setLine_id(FormUtil.getQueryControlDefaultValue(vQueryControlDefaultValues, "q_line_id"));
                qCon.setStation_id(FormUtil.getQueryControlDefaultValue(vQueryControlDefaultValues, "q_station_id"));
                qCon.setStorage_id(FormUtil.getQueryControlDefaultValue(vQueryControlDefaultValues, "q_storage_id"));

            }
        }

        return qCon;
    }
    
     @RequestMapping("/TicketStorageDistributeStationParmManageExportAll")
    public void exportExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	
         List results = this.getBufferElementsForCurClass(request,"com.goldsign.acc.app.basicinfo.entity.TicketStorageDistributeStationParm");
        List<Map<String,String>> queryResults = this.entityToMap(results);
        ExpUtil.renderExportExcelPath(response, ExpUtil.exportExcel(queryResults, request));
    }

    private List<Map<String, String>> entityToMap(List results) {
        List<Map<String,String>> list =  new ArrayList<>();
        int row = results.size();
        for(int n=0;n<row ;n++){
            TicketStorageDistributeStationParm vo = (TicketStorageDistributeStationParm)results.get(n);
            Map<String,String> map = new HashMap<>();
            map.put("line_id_name", vo.getLine_id_name());
            map.put("station_id_name", vo.getStation_id_name());
            map.put("storage_id_name", vo.getStorage_id_name());
            map.put("tvm_store_num", vo.getTvm_store_num());
            map.put("tvm_num", vo.getTvm_num());
            map.put("itm_store_num", vo.getItm_store_num());
            map.put("itm_num", vo.getItm_num());
            map.put("bom_store_num", vo.getBom_store_num());
            map.put("bom_num", vo.getBom_num());
            map.put("remark", vo.getRemark());
           
            list.add(map);
        }
        return list;
    }

}
