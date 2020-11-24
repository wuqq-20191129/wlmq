/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.basicinfo.controller;

import com.goldsign.acc.app.basicinfo.entity.TicketStorageBorrowUnitDefManage;
import com.goldsign.acc.app.basicinfo.entity.TicketStorageStationContrastManage;

import com.goldsign.acc.app.basicinfo.mapper.TicketStorageStationContrastManageMapper;

import com.goldsign.acc.frame.constant.CommandConstant;
import com.goldsign.acc.frame.constant.LogConstant;
import com.goldsign.acc.frame.constant.PubFlagConstant;
import com.goldsign.acc.frame.controller.StorageOutInBaseController;
import com.goldsign.acc.frame.entity.PubFlag;
import com.goldsign.acc.frame.mapper.BillMapper;
import com.goldsign.acc.frame.mapper.OperationLogMapper;
import com.goldsign.acc.frame.util.CharUtil;
import com.goldsign.acc.frame.util.DBUtil;
import com.goldsign.acc.frame.util.ExpUtil;
import com.goldsign.acc.frame.util.FormUtil;
import com.goldsign.acc.frame.util.LogUtil;
import com.goldsign.acc.frame.util.PubUtil;
import com.goldsign.acc.frame.vo.OperationResult;
import com.goldsign.login.vo.User;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
 * 车站对照表
 * @author xiaowu
 */
@Controller
public class TicketStorageStationContrastManageController extends StorageOutInBaseController {

    @Autowired
    private TicketStorageStationContrastManageMapper mapper;

    @RequestMapping(value = "/ticketStorageStationContrastManage")
    public ModelAndView serviceNew(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView mv = new ModelAndView("/jsp/basicinfo/ticketStorageStationContrastManage.jsp");
        String command = request.getParameter("command");
        OperationResult opResult = new OperationResult();
        
         //取操作员ID
        User user = (User) request.getSession().getAttribute("User");
        String sys_operator_id = user.getAccount();
        
        List<PubFlag> storageList = this.getStoreList(sys_operator_id);
        //过滤下拉框的内容
        List<PubFlag> icLines = pubFlagMapper.getLinesForIc();
        List icLinesFilted = this.getFilterResult(icLines, storageList, "LINE");
        PubUtil.sortPubFlagList(icLinesFilted);
        mv.addObject(StorageOutInBaseController.IC_LINES, icLinesFilted);
        
        List<PubFlag> icStations = pubFlagMapper.getStationsForIc();
        List icStationsFilted = this.getFilterResult(icStations, storageList, "STATION");
        PubUtil.sortPubFlagList(icStationsFilted);
        String idLineStations = FormUtil.getLineStations(icStationsFilted);
        mv.addObject(StorageOutInBaseController.IC_LINE_STATIONS, idLineStations);
        
        List<PubFlag> icLineStations = pubFlagMapper.getStationsForIc();
        List icLineStationsFilted = this.getFilterResult(icLineStations, storageList, "STATION");
        PubUtil.sortPubFlagList(icLineStationsFilted);
        mv.addObject(StorageOutInBaseController.IC_STATIONS, icLineStationsFilted);
        
        List<PubFlag> lines = pubFlagMapper.getLines(PubFlagConstant.PARAM_TYPE_ID_LINE);
        List linesFilted = this.getFilterResult(lines, storageList, "LINE");
        PubUtil.sortPubFlagList(linesFilted);
        mv.addObject(StorageOutInBaseController.LINES, linesFilted);
        
        List<PubFlag> stations = pubFlagMapper.getStations(PubFlagConstant.PARAM_TYPE_ID_STATION);
        List stationsFilted = this.getFilterResult(stations, storageList, "STATION");
        PubUtil.sortPubFlagList(stationsFilted);
        mv.addObject(StorageOutInBaseController.STATIONS, stationsFilted);
        
        List<PubFlag> lineStations = pubFlagMapper.getStations(PubFlagConstant.PARAM_TYPE_ID_STATION);
        List lineStationsFilted = this.getFilterResult(lineStations, storageList, "STATION");
        PubUtil.sortPubFlagList(lineStationsFilted);
        String lineStationSts = FormUtil.getLineStations(lineStationsFilted);
        mv.addObject(StorageOutInBaseController.LINE_STATIONS, lineStationSts); 
        
        try {
            if (command != null) {
                command = command.trim();
                if (command.equals(CommandConstant.COMMAND_QUERY)) {
                    opResult = this.queryParm(request, this.mapper, this.operationLogMapper,storageList);
                }
                if (command.equals(CommandConstant.COMMAND_ADD)) {
                    opResult = this.addParm(request, this.mapper,this.operationLogMapper);
                }
                if (command.equals(CommandConstant.COMMAND_DELETE)) {
                    opResult = this.deleteParm(request, this.mapper, this.operationLogMapper);
                }
                if (command.equals(CommandConstant.COMMAND_MODIFY)) {
                    opResult = this.modifyParm(request, this.mapper, this.operationLogMapper);
                }
                if (command != null) {
                    if (command.equals(CommandConstant.COMMAND_ADD) || command.equals(CommandConstant.COMMAND_DELETE) || command.equals(CommandConstant.COMMAND_MODIFY)
                            ) {
                        this.queryForOp(request, this.mapper, this.operationLogMapper, opResult,storageList);
                    }
                }
            }
//            else{
//                this.queryForOp(request, this.mapper, this.operationLogMapper, opResult,storageList);
//            }
        } catch (Exception e) {
            opResult.addMessage(e.getMessage());
        }

        String[] attrNames = {};
        this.setPageOptions(attrNames, mv, request, response); //设置页面线路、车站、票卡、仓库等选项值、版本
        this.getResultSetText((List<TicketStorageStationContrastManage>) opResult.getReturnResultSet(), mv);
        this.baseHandler(request, response, mv);
        this.divideResultSet(request, mv, opResult); //结果集分页
        this.SaveOperationResult(mv, opResult); //返回结果集
        return mv;
    }

    private void getResultSetText(List<TicketStorageStationContrastManage> resultSet, ModelAndView mv) {
        //线路名

//        List<PubFlag> icLines = (List<PubFlag>) mv.getModelMap().get(StorageOutInBaseController.IC_LINES);
//        List<PubFlag> icStations = (List<PubFlag>) mv.getModelMap().get(StorageOutInBaseController.IC_STATIONS);
        //车站名
//        List<PubFlag> stations = (List<PubFlag>) mv.getModelMap().get(StorageOutInBaseController.IC_LINE_STATIONS);
//        for (TicketStorageStationContrastManage dl : resultSet) {
//            if (icLines != null && !icLines.isEmpty()) {
//                dl.setLine_id_name(DBUtil.getTextByCode(dl.getLine_id(), icLines));
//
//            }
//            if (icStations != null && !icStations.isEmpty()) {
//                dl.setStation_id_name(DBUtil.getTextByCode(dl.getStation_id(), dl.getLine_id(), icStations));
//
//            }
//            if (dl.getStation_id()!= null && !dl.getStation_id().isEmpty()) {
//                    dl.setStation_id_name(DBUtil.getTextByCode(dl.getStation_id(), dl.getLine_id(), stations));
//                }

//        }
    }

    public OperationResult queryParm(HttpServletRequest request, TicketStorageStationContrastManageMapper mapper, OperationLogMapper opLogMapper,List<PubFlag> storageList) throws Exception {
        LogUtil logUtil = new LogUtil();
        TicketStorageStationContrastManage queryCondition;
        List<TicketStorageStationContrastManage> resultSet;
        OperationResult or = new OperationResult();
        try {
            queryCondition = this.getQueryCondition(request, or);
            if (queryCondition == null) {
                return null;
            }
            resultSet = mapper.getTicketStorageStationContrastManage(queryCondition);
            resultSet = this.getFilterResult(resultSet, storageList, "STATIONCONTRASTLIST");
            or.setReturnResultSet(resultSet);

        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + resultSet.size() + "条记录", opLogMapper);
        return or;

    }

    public OperationResult addParm(HttpServletRequest request, TicketStorageStationContrastManageMapper mapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        TicketStorageStationContrastManage po = this.getReqAttribute(request);
        LogUtil logUtil = new LogUtil();
        int n = 0;

        try {
            if(mapper.ifExistCardType(po)> 0 || mapper.ifExistIcCardType(po)>0){
                rmsg.addMessage("该车站的对照记录已存在，添加失败！");
                return rmsg;
            }
            n = this.addByTrans(request, mapper, po);
            if(n < 1){
                rmsg.addMessage("写数据库失败！");
                return rmsg;
            }
        } catch (Exception e) {
            e.printStackTrace(); 
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_ADD, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_ADD, request, LogConstant.addSuccessMsg(n), opLogMapper);

        rmsg.addMessage(LogConstant.addSuccessMsg(n));

        return rmsg;
    }

    //获取查询参数
    private TicketStorageStationContrastManage getQueryCondition(HttpServletRequest request, OperationResult or) {
        TicketStorageStationContrastManage tsdsp = new TicketStorageStationContrastManage();
        String q_line_id = FormUtil.getParameter(request, "q_line_id");
        String q_station_id = FormUtil.getParameter(request, "q_station_id");

        tsdsp.setLine_id(q_line_id);
        tsdsp.setStation_id(q_station_id);

        return tsdsp;
    }

       public TicketStorageStationContrastManage getReqAttribute(HttpServletRequest request) {
        TicketStorageStationContrastManage po = new TicketStorageStationContrastManage();
        
        po.setLine_id(FormUtil.getParameter(request, "d_line_id"));
        po.setStation_id(FormUtil.getParameter(request, "d_station_id"));
        po.setIc_line_id(FormUtil.getParameter(request, "d_ic_line_id"));
        po.setIc_station_id(FormUtil.getParameter(request, "d_ic_station_id"));
        return po;
    }

      private boolean existRecord(TicketStorageStationContrastManage po, TicketStorageStationContrastManageMapper arMapper, OperationLogMapper opLogMapper) {
//        List<TicketStorageStationContrastManage> list = arMapper.getListForAdd(po);
//        if (list.isEmpty()) {
//            return false;
//        }
        return true;

    }

       private int addByTrans(HttpServletRequest request, TicketStorageStationContrastManageMapper mapper, TicketStorageStationContrastManage po) throws Exception {
        TransactionStatus status = null;
        int n = 0;
        try {

            status = txMgr.getTransaction(this.def);
            try {
                n = mapper.addTicketStorageStationContrastManage(po);
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


        public OperationResult modifyParm(HttpServletRequest request, TicketStorageStationContrastManageMapper mapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        TicketStorageStationContrastManage po = this.getReqAttribute(request);
        LogUtil logUtil = new LogUtil();
        int n = 0;

            try {
                n = this.modifyByTrans(request, mapper,po);
            } catch (Exception e) {
                return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_MODIFY, e, opLogMapper);
            }
            logUtil.logOperation(CommandConstant.COMMAND_MODIFY, request, LogConstant.modifySuccessMsg(n), opLogMapper);

        rmsg.addMessage(LogConstant.modifySuccessMsg(n));

        return rmsg;
    }

        private int modifyByTrans(HttpServletRequest request, TicketStorageStationContrastManageMapper mapper,  TicketStorageStationContrastManage po) throws Exception {
        TransactionStatus status = null;
        int n = 0;
        try {

            status = txMgr.getTransaction(this.def);
            n = mapper.modifyTicketStorageStationContrastManage(po);

            txMgr.commit(status);
        } catch (Exception e) {
            if (txMgr != null) {
                txMgr.rollback(status);
            }
            throw e;
        }
        return n;
    }

        public OperationResult deleteParm(HttpServletRequest request, TicketStorageStationContrastManageMapper mapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        Vector<TicketStorageStationContrastManage> pos = this.getReqAttributeForDelete(request);
        TicketStorageStationContrastManage prmVo = new TicketStorageStationContrastManage();
        LogUtil logUtil = new LogUtil();
        int n = 0;

        try {
            n = this.deleteByTrans(request, mapper, pos, prmVo);
        } catch (Exception e) {
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_DELETE, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_DELETE, request, LogConstant.delSuccessMsg(n), opLogMapper);

        rmsg.addMessage(LogConstant.delSuccessMsg(n));

        return rmsg;
    }


       private Vector<TicketStorageStationContrastManage> getReqAttributeForDelete(HttpServletRequest request) {
        TicketStorageBorrowUnitDefManage po = new TicketStorageBorrowUnitDefManage();
        String selectIds = request.getParameter("allSelectedIDs");
        Vector<TicketStorageStationContrastManage> selectedItems = this.getDeleteIDs(selectIds);
        return selectedItems;
    }

        private int deleteByTrans(HttpServletRequest request, TicketStorageStationContrastManageMapper tsbudmMapper, Vector<TicketStorageStationContrastManage> pos, TicketStorageStationContrastManage prmVo) throws Exception {

        TransactionStatus status = null;
        int n = 0;
        try {
            status = txMgr.getTransaction(this.def);
            for (TicketStorageStationContrastManage po : pos) {
                n += tsbudmMapper.deleteTicketStorageStationContrastManage(po);
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

         private Vector<TicketStorageStationContrastManage> getDeleteIDs(String selectedIds) {
        StringTokenizer st = new StringTokenizer(selectedIds, ";");
        String strIds = null;
        Vector<TicketStorageStationContrastManage> sds = new Vector();
        TicketStorageStationContrastManage sd;
        while (st.hasMoreTokens()) {
            strIds = st.nextToken();
            sd = this.getTicketStorageStationContrastManage(strIds, "#");
            sds.add(sd);
        }
        return sds;

    }

      private TicketStorageStationContrastManage getTicketStorageStationContrastManage(String strIDs, String delim) {
        StringTokenizer st = new StringTokenizer(strIDs, delim);
        String tmp = null;
        int i = 0;
        TicketStorageStationContrastManage sd = new TicketStorageStationContrastManage();
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
        }
        return sd;

    }


        public OperationResult queryForOp(HttpServletRequest request, TicketStorageStationContrastManageMapper mapper, OperationLogMapper opLogMapper,OperationResult opResult,List<PubFlag> storageList) throws Exception {
        LogUtil logUtil = new LogUtil();
        TicketStorageStationContrastManage queryCondition;
        List<TicketStorageStationContrastManage> resultSet;

        try {
            queryCondition = this.getQueryConditionForOp(request);
            resultSet = mapper.getTicketStorageStationContrastManage(queryCondition);
            resultSet = this.getFilterResult(resultSet, storageList, "STATIONCONTRASTLIST");
            opResult.setReturnResultSet(resultSet);
//            opResult.addMessage("成功查询" + resultSet.size() + "条记录");
        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + resultSet.size() + "条记录", opLogMapper);
        return opResult;

    }

    private TicketStorageStationContrastManage getQueryConditionForOp(HttpServletRequest request) {
        TicketStorageStationContrastManage qCon = new TicketStorageStationContrastManage();
        HashMap vQueryControlDefaultValues = null;
        String queryControlDefaultValues = request.getParameter("queryControlDefaultValues");
        String command = request.getParameter("command");
        if (FormUtil.isAddOrModifyMode(request)) {//操作处于添加或修改模式
            if (command.equals(CommandConstant.COMMAND_DELETE)) {
                return null;
            }
            qCon.setLine_id(FormUtil.getParameter(request, "d_line_id"));
            qCon.setStation_id(FormUtil.getParameter(request, "d_station_id"));
            qCon.setIc_line_id(FormUtil.getParameter(request, "d_ic_line_id"));
            qCon.setIc_station_id(FormUtil.getParameter(request, "d_ic_station_id"));

        } else {//操作处于非添加模式
            vQueryControlDefaultValues = FormUtil.getQueryControlDefaultValues(queryControlDefaultValues);
            if (!vQueryControlDefaultValues.isEmpty()) {
                qCon.setLine_id(FormUtil.getQueryControlDefaultValue(vQueryControlDefaultValues, "q_line_id"));
                qCon.setStation_id(FormUtil.getQueryControlDefaultValue(vQueryControlDefaultValues, "q_station_id"));

            }
        }
        return qCon;
    }
    
    
    private List<PubFlag> getStoreList(String sys_operator_id) throws Exception{
        Set storeSet = getUserStoreSet(sys_operator_id);
        List<PubFlag> vectorList = new ArrayList<PubFlag>();
//        FrameDBUtil util = new FrameDBUtil();
        Vector storages = this.getStorageVector();
//                util.getTableFlags("ic_cod_storage", "storage_id", "storage_name");
//        "9999"代表全部
        if (storeSet.contains("9999")) {
            return storages;
        } else {
            for (Object obj : storages) {
                PubFlag pubFlagVo = (PubFlag) obj;
                if (storeSet.contains(pubFlagVo.getCode())) {
                    vectorList.add(pubFlagVo);
                }
            }
        }
        return vectorList;
    }
    
    public Set getUserStoreSet(String operatorId) throws Exception {
        Set vectorSet = new TreeSet();
        TicketStorageStationContrastManage vo = new TicketStorageStationContrastManage();
        vo.setSys_operator_id(operatorId);
        List<TicketStorageStationContrastManage> lists = mapper.getUserStoreSet(vo);
        if (lists != null && lists.size() > 0) {
            String sysStorageId;
            for (TicketStorageStationContrastManage tsscm : lists) {
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

    private Vector getStorageVector() {
        List<TicketStorageStationContrastManage> lists = mapper.getStorageVector();
        Vector tableFlags = new Vector();
        for(TicketStorageStationContrastManage vo:lists){
            PubFlag pv = new PubFlag();
            pv.setCode(vo.getLine_id());
            pv.setCode_text(vo.getLine_name());
            tableFlags.add(pv);
        }
        return tableFlags;
     }
    
     public List getFilterResult(List results,List<PubFlag> storageList,String type) throws  Exception
    {
//        if (storageList.size() > 0) {
//            //权限包含超级管理员
//            for (int i = 0; i < storageList.size(); i++) {
//                if (storageList.get(i).getCode().equals("9999")) {
//                    return results;
//                }
//            }
//        }
        Vector lines = this.getLinesID(storageList); 
        return this.getFilterResultSecond(results, lines,type);
    }
     
    //过滤结果
    public Vector getFilterResultSecond(List result,Vector lines,String type)
    {
        Vector resultReal = new Vector();
        if(lines.size()>0){
                int index = 0;
                if(type.equals("LINE")){
                    for(int i = 0;i < lines.size(); i++) {
                         PubFlag pvLine = (PubFlag)lines.get(i);
                        for (int j = index;j < result.size();j++){
                           PubFlag pv=(PubFlag)result.get(j);
                            if (Integer.parseInt(pv.getCode())==Integer.parseInt(pvLine.getCode())){
                                resultReal.add(pv);
                            }
//                            else if(pv.getCode().equals("0"+pvLine.getCode())&&pvLine.getCode().length()==1) {
//                                resultReal.add(pv);
//                            }
                        }
                    }
                }
                if (type.equals("STATION")){
                    for(int i = 0;i < lines.size(); i++) {
                         PubFlag pvLine = (PubFlag)lines.get(i);
                        for (int j = index;j < result.size();j++){
                           PubFlag pv=(PubFlag)result.get(j);
                            if (Integer.parseInt(pv.getCode_type())==Integer.parseInt(pvLine.getCode())){
                                resultReal.add(pv);
                            }
//                            else if(pv.getCode_type().equals("0"+pvLine.getCode())&&pvLine.getCode().length()==1) {
//                                resultReal.add(pv);
//                            }
                        }
                    }
                }
                if (type.equals("STATIONCONTRASTLIST")){
                    for(int i = 0;i < lines.size(); i++) {
                         PubFlag pvLine = (PubFlag)lines.get(i);
                        for (int j = index;j < result.size();j++){
                           TicketStorageStationContrastManage vo=(TicketStorageStationContrastManage)result.get(j);
                            if (Integer.parseInt(vo.getLine_id())==Integer.parseInt(pvLine.getCode())){
                                resultReal.add(vo);
                            }
//                            else if(vo.getLine_id().equals("0"+pvLine.getCode())&&pvLine.getCode().length()==1) {
//                                resultReal.add(vo);
//                            }
                        }
                    }
                }
               
        }
         return resultReal;
    }
     
      //获取用户能管理的线路组
    public Vector getLinesID(List<PubFlag> storageList) throws  Exception{
        List<String> storageIdList = new ArrayList<String>();
        for(Object obj:storageList){
            PubFlag pf = (PubFlag)obj;
            storageIdList.add(pf.getCode());
        }
        List<String>lineIdList = this.getLincodeCondition(storageIdList);
        Vector linesId = new Vector();
        if (lineIdList != null && lineIdList.size() > 0) {
            List<TicketStorageStationContrastManage> lists = mapper.getLinesID(lineIdList);
            for(TicketStorageStationContrastManage psContrastManage:lists){
                PubFlag pf = new PubFlag();
                pf.setCode(psContrastManage.getLine_id());
                pf.setCode_text(psContrastManage.getLine_name());
                linesId.add(pf);
            }
        }
//        linesId = this.getICParamTable("ic_cod_line", "line_id",
//                    "line_name",condition);
        return linesId;
    }

    private List<String> getLincodeCondition(List<String> storageIdList) {
        List<TicketStorageStationContrastManage> lists = mapper.getLincodeCondition(storageIdList);
        List<String> lineIdList = new ArrayList<String>();
        for(TicketStorageStationContrastManage vo:lists){
            lineIdList.add(vo.getLine_id());
        }
        return lineIdList;
    }
    
    @RequestMapping("/TicketStorageStationContrastManageExportAll")
    public void exportExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {
        List results = this.getBufferElementsForCurClass(request, "com.goldsign.acc.app.basicinfo.entity.TicketStorageStationContrastManage");
        String expAllFields = request.getParameter("expAllFields");
        List<Map<String, Object>> queryResults
                = ExpUtil.entityToMap(expAllFields, results);
        ExpUtil.renderExportExcelPath(response,
                ExpUtil.exportExcel(queryResults, request));
    }

}
