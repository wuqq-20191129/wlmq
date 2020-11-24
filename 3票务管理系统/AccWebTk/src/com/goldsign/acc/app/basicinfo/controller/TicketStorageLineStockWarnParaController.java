/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.basicinfo.controller;

import com.goldsign.acc.app.basicinfo.entity.TicketStorageDistributeStationParm;
import com.goldsign.acc.app.basicinfo.entity.TicketStorageLineStockWarnPara;
import com.goldsign.acc.app.basicinfo.mapper.TicketStorageLineStockWarnParaMapper;
import com.goldsign.acc.frame.constant.CommandConstant;
import com.goldsign.acc.frame.constant.LogConstant;
import com.goldsign.acc.frame.controller.StorageOutInBaseController;
import com.goldsign.acc.frame.entity.PubFlag;
import com.goldsign.acc.frame.mapper.OperationLogMapper;
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
 * @author ysw
 */
@Controller
public class TicketStorageLineStockWarnParaController extends StorageOutInBaseController{
    @Autowired
    private TicketStorageLineStockWarnParaMapper lineStockWarnMapper;

    @RequestMapping(value = "/ticketStorageLineStockParam")
    public ModelAndView service(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView("/jsp/basicinfo/ticketStorageLineStockWarnPara.jsp");
        String command = request.getParameter("command");
        OperationResult opResult = new OperationResult();
        try {
            if (command != null) {
                command = command.trim();
                if (command.equals(CommandConstant.COMMAND_QUERY)) {
                    opResult = this.queryParm(request, this.lineStockWarnMapper, this.operationLogMapper);
                }
                if (command.equals(CommandConstant.COMMAND_ADD)) {
                    opResult = this.addParm(request, this.lineStockWarnMapper,this.operationLogMapper);
                }
                    if (command.equals(CommandConstant.COMMAND_DELETE)) {
                        opResult = this.deleteParm(request, this.lineStockWarnMapper, this.operationLogMapper);
                    }
                    if (command.equals(CommandConstant.COMMAND_MODIFY)) {
                        opResult = this.modifyParm(request, this.lineStockWarnMapper, this.operationLogMapper);
                    }
                if (command != null) {
                if (command.equals(CommandConstant.COMMAND_ADD) || command.equals(CommandConstant.COMMAND_DELETE) || command.equals(CommandConstant.COMMAND_MODIFY)
                        ) {
                    this.queryForOp(request, this.lineStockWarnMapper, this.operationLogMapper, opResult);

                }
            }

            }
        } catch (Exception e) {
            e.printStackTrace();
            opResult.addMessage(e.getMessage());
        }

        String[] attrNames = {AFC_LINES,AFC_CARD_MAIN, AFC_CARD_SUB, CARD_MAIN_SUB};
        this.setPageOptions(attrNames, mv, request, response); //设置页面线路、车站、票卡、仓库等选项值、版本
        this.getResultSetText((List<TicketStorageLineStockWarnPara>) opResult.getReturnResultSet(), mv);
        this.baseHandler(request, response, mv);
        this.divideResultSet(request, mv, opResult); //结果集分页
        this.SaveOperationResult(mv, opResult); //返回结果集
        return mv;
    }

    private void getResultSetText(List<TicketStorageLineStockWarnPara> resultSet, ModelAndView mv) {
        List<PubFlag> lineId = (List<PubFlag>) mv.getModelMap().get(StorageOutInBaseController.AFC_LINES);
        List<PubFlag> cardMains = (List<PubFlag>) mv.getModelMap().get(StorageOutInBaseController.AFC_CARD_MAIN);
        List<PubFlag> cardSub = (List<PubFlag>) mv.getModelMap().get(StorageOutInBaseController.AFC_CARD_SUB);

        for (TicketStorageLineStockWarnPara dl : resultSet) {
            if (lineId != null && !lineId.isEmpty()) {
                dl.setLineName(DBUtil.getTextByCode(dl.getLineId(), lineId));
            }
            if (cardMains != null && !cardMains.isEmpty()) {
                dl.setMainTypeName(DBUtil.getTextByCode(dl.getIcMainType(), cardMains));
            }
            if (cardSub != null && !cardSub.isEmpty()) {
                dl.setSubTypeName(DBUtil.getTextByCode(dl.getIcSubType(), dl.getIcMainType(), cardSub));

            }
        }
    }

    public OperationResult queryParm(HttpServletRequest request, TicketStorageLineStockWarnParaMapper lineStockWarnMapper, OperationLogMapper opLogMapper) throws Exception {
        LogUtil logUtil = new LogUtil();
        TicketStorageLineStockWarnPara queryCondition;
        List<TicketStorageLineStockWarnPara> resultSet;
        OperationResult or = new OperationResult();
        try {
            queryCondition = this.getQueryCondition(request, or);
            if (queryCondition == null) {
                return null;
            }
            resultSet = lineStockWarnMapper.queryLineStockWarnPara(queryCondition);
            or.setReturnResultSet(resultSet);

        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + resultSet.size() + "条记录", opLogMapper);
        return or;

    }    
    
    private TicketStorageLineStockWarnPara getQueryCondition(HttpServletRequest request, OperationResult or) {
        TicketStorageLineStockWarnPara qCon = new TicketStorageLineStockWarnPara();
        qCon.setLineId(FormUtil.getParameter(request, "q_lineId")); 
        qCon.setIcMainType(FormUtil.getParameter(request, "q_mainType"));
        qCon.setIcSubType(FormUtil.getParameter(request, "q_subType"));
        return qCon;
    }

    public OperationResult addParm(HttpServletRequest request, TicketStorageLineStockWarnParaMapper tsdspMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        TicketStorageLineStockWarnPara po = this.getReqAttribute(request);
        LogUtil logUtil = new LogUtil();
        int n = 0;

        try {
            if (this.existRecord(po, tsdspMapper, opLogMapper)) {
                rmsg.addMessage("该条记录已存在！");
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

    public TicketStorageLineStockWarnPara getReqAttribute(HttpServletRequest request) {
        TicketStorageLineStockWarnPara po = new TicketStorageLineStockWarnPara();
        po.setLineId(FormUtil.getParameter(request, "d_lineId"));
        po.setIcMainType(FormUtil.getParameter(request, "d_mainType"));
        po.setIcSubType(FormUtil.getParameter(request, "d_subType"));
        po.setUpperThresh(FormUtil.getParameterIntVal(request, "d_upperThresh"));
        po.setLowerThresh(FormUtil.getParameterIntVal(request, "d_lowerThresh"));
        po.setRemark(FormUtil.getParameter(request, "d_remark"));       
        return po;
    }

    private int addByTrans(HttpServletRequest request, TicketStorageLineStockWarnParaMapper lineStockWarnMapper, TicketStorageLineStockWarnPara po) throws Exception {
        TransactionStatus status = null;
        int n = 0;
        try {

            status = txMgr.getTransaction(this.def);
            try {
                n = lineStockWarnMapper.addLineStockWarnPara(po);
            } catch (Exception e) {
                e.printStackTrace();
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
    
    private boolean existRecord(TicketStorageLineStockWarnPara po, TicketStorageLineStockWarnParaMapper lineStockWarnMapper, OperationLogMapper opLogMapper) {          
        List<TicketStorageLineStockWarnPara> list = lineStockWarnMapper.queryLineStockWarnParaById(po);
        if (list.isEmpty()) {
            return false;
        }
        return true;
    }
     
    public OperationResult modifyParm(HttpServletRequest request, TicketStorageLineStockWarnParaMapper lineStockWarnMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        TicketStorageLineStockWarnPara po = this.getReqAttribute(request);
        LogUtil logUtil = new LogUtil();
        int n = 0;

            try {
                n = this.modifyByTrans(request, lineStockWarnMapper,po);
            } catch (Exception e) {
                return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_MODIFY, e, opLogMapper);
            }
            logUtil.logOperation(CommandConstant.COMMAND_MODIFY, request, LogConstant.modifySuccessMsg(n), opLogMapper);

        rmsg.addMessage(LogConstant.modifySuccessMsg(n));

        return rmsg;
    }

    private int modifyByTrans(HttpServletRequest request, TicketStorageLineStockWarnParaMapper lineStockWarnMapper,TicketStorageLineStockWarnPara po) throws Exception {
        TransactionStatus status = null;
        int n = 0;
        try {
            status = txMgr.getTransaction(this.def);
            n = lineStockWarnMapper.modifyLineStockWarnPara(po);
            txMgr.commit(status);
        } catch (Exception e) {
            if (txMgr != null) {
                txMgr.rollback(status);
            }
            throw e;
        }
        return n;
    }

    public OperationResult deleteParm(HttpServletRequest request, TicketStorageLineStockWarnParaMapper lineStockWarnMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        Vector<TicketStorageLineStockWarnPara> pos = this.getReqAttributeForDelete(request);
        TicketStorageLineStockWarnPara prmVo = new TicketStorageLineStockWarnPara();
        LogUtil logUtil = new LogUtil();
        int n = 0;

        try {
            n = this.deleteByTrans(request, lineStockWarnMapper, pos, prmVo);
        } catch (Exception e) {
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_DELETE, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_DELETE, request, LogConstant.delSuccessMsg(n), opLogMapper);

        rmsg.addMessage(LogConstant.delSuccessMsg(n));

        return rmsg;
    }

    private Vector<TicketStorageLineStockWarnPara> getReqAttributeForDelete(HttpServletRequest request) {
        TicketStorageLineStockWarnPara po = new TicketStorageLineStockWarnPara();
        String selectIds = request.getParameter("allSelectedIDs");
        Vector<TicketStorageLineStockWarnPara> selectedItems = this.getDeleteIDs(selectIds);
        return selectedItems;
    }

    private int deleteByTrans(HttpServletRequest request, TicketStorageLineStockWarnParaMapper lineStockWarnMapper, Vector<TicketStorageLineStockWarnPara> pos, TicketStorageLineStockWarnPara prmVo) throws Exception {

        TransactionStatus status = null;
        int n = 0;
        try {
            status = txMgr.getTransaction(this.def);
            for (TicketStorageLineStockWarnPara po : pos) {
                n += lineStockWarnMapper.deleteLineStockWarnPara(po);
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

    private Vector<TicketStorageLineStockWarnPara> getDeleteIDs(String selectedIds) {
        StringTokenizer st = new StringTokenizer(selectedIds, ";");
        String strIds = null;
        Vector<TicketStorageLineStockWarnPara> sds = new Vector();
        TicketStorageLineStockWarnPara sd;
        while (st.hasMoreTokens()) {
            strIds = st.nextToken();
            sd = this.getTicketStorageLineStockWarnPara(strIds, "#");
            sds.add(sd);
        }
        return sds;

    }

    private TicketStorageLineStockWarnPara getTicketStorageLineStockWarnPara(String strIDs, String delim) {
        StringTokenizer st = new StringTokenizer(strIDs, delim);
        String tmp = null;
        int i = 0;
        TicketStorageLineStockWarnPara sd = new TicketStorageLineStockWarnPara();
        Vector<TicketStorageLineStockWarnPara> sds = new Vector();
        while (st.hasMoreTokens()) {
            i++;
            tmp = st.nextToken();
            if (i == 1) {
                sd.setLineId(tmp);
                continue;
            }
            if (i == 2) {
                sd.setIcMainType(tmp);
                continue;
            }
            if (i == 3) {
                sd.setIcSubType(tmp);
                continue;
            }            
        }
        return sd;
    }

    public OperationResult queryForOp(HttpServletRequest request, TicketStorageLineStockWarnParaMapper lineStockWarnMapper, OperationLogMapper opLogMapper,OperationResult opResult) throws Exception {
        LogUtil logUtil = new LogUtil();
        TicketStorageLineStockWarnPara queryCondition;
        List<TicketStorageLineStockWarnPara> resultSet;

        try {
            queryCondition = this.getQueryConditionForOp(request);
            resultSet = lineStockWarnMapper.queryLineStockWarnPara(queryCondition);
            opResult.setReturnResultSet(resultSet);
        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + resultSet.size() + "条记录", opLogMapper);
        return opResult;

    }

    private TicketStorageLineStockWarnPara getQueryConditionForOp(HttpServletRequest request) {
        TicketStorageLineStockWarnPara qCon = new TicketStorageLineStockWarnPara();
        HashMap vQueryControlDefaultValues = null;
        String queryControlDefaultValues = request.getParameter("queryControlDefaultValues");
        String command = request.getParameter("command");
        if (FormUtil.isAddOrModifyMode(request)) {//操作处于添加或修改模式
            if (command.equals(CommandConstant.COMMAND_DELETE)) {
                return null;
            }
            qCon.setLineId(FormUtil.getParameter(request, "d_lineId"));
            qCon.setIcMainType(FormUtil.getParameter(request, "d_mainType"));
            qCon.setIcSubType(FormUtil.getParameter(request, "d_subType"));           

        } else {//操作处于非添加模式
            vQueryControlDefaultValues = FormUtil.getQueryControlDefaultValues(queryControlDefaultValues);
            if (!vQueryControlDefaultValues.isEmpty()) {
                qCon.setLineId(FormUtil.getQueryControlDefaultValue(vQueryControlDefaultValues, "q_lineId"));
                qCon.setIcMainType(FormUtil.getQueryControlDefaultValue(vQueryControlDefaultValues, "q_mainType"));
                qCon.setIcSubType(FormUtil.getQueryControlDefaultValue(vQueryControlDefaultValues, "q_subType"));

            }
        }
        return qCon;
    }
    
         @RequestMapping("/TicketStorageLineStockParamExportAll")
    public void exportExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	
        List results = this.getBufferElementsForCurClass(request,"com.goldsign.acc.app.basicinfo.entity.TicketStorageLineStockWarnPara");
        List<Map<String,String>> queryResults = this.entityToMap(results);
        ExpUtil.renderExportExcelPath(response, ExpUtil.exportExcel(queryResults, request));
    }

    private List<Map<String, String>> entityToMap(List results) {
        List<Map<String,String>> list =  new ArrayList<>();
        int row = results.size();
        for(int n=0;n<row ;n++){
            TicketStorageLineStockWarnPara vo = (TicketStorageLineStockWarnPara)results.get(n);
            Map<String,String> map = new HashMap<>();
            map.put("lineName", vo.getLineName());
            map.put("mainTypeName", vo.getMainTypeName());
            map.put("subTypeName", vo.getSubTypeName());
            map.put("upperThresh", String.valueOf(vo.getUpperThresh()));
            map.put("lowerThresh", String.valueOf(vo.getLowerThresh()));
            map.put("remark", vo.getRemark());
           
            list.add(map);
        }
        return list;
    }
    
}
