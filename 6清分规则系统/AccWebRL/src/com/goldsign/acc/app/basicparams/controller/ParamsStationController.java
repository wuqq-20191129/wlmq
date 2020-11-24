package com.goldsign.acc.app.basicparams.controller;

import com.goldsign.acc.app.basicparams.entity.ParamsStation;
import com.goldsign.acc.app.basicparams.mapper.ParamsStationMapper;
import com.goldsign.acc.frame.constant.CommandConstant;
import com.goldsign.acc.frame.constant.LogConstant;
import com.goldsign.acc.frame.constant.RuleConstant;
import com.goldsign.acc.frame.controller.RLBaseController;
import com.goldsign.acc.frame.entity.PubFlag;
import com.goldsign.acc.frame.mapper.OperationLogMapper;
import com.goldsign.acc.frame.util.DBUtil;
import com.goldsign.acc.frame.util.ExpUtil;
import com.goldsign.acc.frame.util.FormUtil;
import com.goldsign.acc.frame.util.LogUtil;
import com.goldsign.acc.frame.util.PageControlUtil;
import com.goldsign.acc.frame.util.PubUtil;
import com.goldsign.acc.frame.vo.OperationResult;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.StringTokenizer;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.TransactionStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author mh
 */
@Controller
public class ParamsStationController extends RLBaseController{
    @Autowired
    private ParamsStationMapper paramsStationMapper;
    
    @RequestMapping(value="/paramsStation")
    public ModelAndView service(HttpServletRequest request,HttpServletResponse response) {
    ModelAndView mv = new ModelAndView("/jsp/basicparams/paramsStation.jsp");
    String command = request.getParameter("command");
    String operType = request.getParameter("operType");
    OperationResult opResult = new OperationResult();
    try{
            if(command != null){
                command = command.trim();
                if(command.equals(CommandConstant.COMMAND_ADD)){
                    opResult = this.add(request, this.paramsStationMapper, this.operationLogMapper);
                }
                if(command.equals(CommandConstant.COMMAND_DELETE)){
                    opResult = this.delete(request, this.paramsStationMapper, this.operationLogMapper);
                }
                if (command.equals(CommandConstant.COMMAND_AUDIT)) {
                    opResult = this.audit(request, this.paramsStationMapper, this.operationLogMapper);
                }
                if(command.equals(CommandConstant.COMMAND_MODIFY)){
                    opResult = this.modify(request, this.paramsStationMapper, this.operationLogMapper);
                }
                if(command.equals(CommandConstant.COMMAND_QUERY)){
                    opResult = this.query(request, this.paramsStationMapper, this.operationLogMapper);
                } 
                if (this.isUpdateOp(command, operType))//更新操作，增、删、改、审核,查询更新结果或原查询结果
                {
                    this.queryUpdateResult(command, operType, request, paramsStationMapper, operationLogMapper, opResult, mv);
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
              
        String[] attrNames = this.getReqAttributeNames(request, command); //显示下拉内容
        this.setPageOptions(attrNames, mv, request, response);
        this.getResultSetText(opResult.getReturnResultSet(), mv, operType);
        this.baseHandler(request, response, mv);
        this.divideResultSet(request, mv, opResult);//结果集分页
        this.SaveOperationResult(mv, opResult); 
        return mv;
    }
    
    private OperationResult query(HttpServletRequest request, ParamsStationMapper paramsStationMapper, OperationLogMapper operationLogMapper) throws Exception {
        OperationResult op = new OperationResult();
        LogUtil logUtil = new LogUtil();
        ParamsStation queryConditon;
        List<ParamsStation> resultSet;
        try{
            queryConditon = this.getQueryConditon(request);
            resultSet = paramsStationMapper.queryParamsStation(queryConditon);
            op.setReturnResultSet(resultSet);
            op.addMessage("成功查询" + resultSet.size() + "条记录");
        }catch (Exception e){
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, operationLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + resultSet.size() + "条记录", operationLogMapper);
        return op;
    }
    
    private ParamsStation getQueryConditon(HttpServletRequest request) {
        ParamsStation paramsStation = new ParamsStation();
        paramsStation.setLineId(FormUtil.getParameter(request, "q_lineId"));
        paramsStation.setpStation(FormUtil.getParameter(request, "q_pStation"));
        paramsStation.setRecordFlag(FormUtil.getParameter(request, "q_recordFlag"));
        String beginOpTime = FormUtil.getParameter(request, "q_beginTime");
        String endOpTime = FormUtil.getParameter(request, "q_endTime");
        if (!beginOpTime.equals("")) {
            paramsStation.setBeginCreateTime(beginOpTime + " 00:00:00");
        }
        if (!beginOpTime.equals("")) {
            paramsStation.setEndCreateTime(endOpTime + " 23:59:59");
        }
        return paramsStation;
    }

    private OperationResult add(HttpServletRequest request, ParamsStationMapper paramsStationMapper, OperationLogMapper operationLogMapper) throws Exception {
        OperationResult op = new OperationResult();
        ParamsStation  paramsStation = this.getReqAttribute(request);
        LogUtil logUtil = new LogUtil();
        try {     
            int n = this.addByTrans(request, paramsStationMapper,paramsStation);
            if(n == 0){
                op.addMessage("添加失败！不能重复插入！请操作草稿版本！");
                return op;
            }
            logUtil.logOperation(CommandConstant.COMMAND_ADD, request, LogConstant.addSuccessMsg(n), operationLogMapper);
            op.addMessage("草稿版本，" + LogConstant.addSuccessMsg(n));
            return op;
        } catch  (Exception e) {
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_ADD, e, operationLogMapper);
        }
    }
        
    private ParamsStation getReqAttribute(HttpServletRequest request) {
        ParamsStation vo = new ParamsStation();
        vo.setLineId(FormUtil.getParameter(request, "d_lineId"));
        vo.setpStation(FormUtil.getParameter(request, "d_pStation"));
        vo.setnStation(FormUtil.getParameter(request, "d_nStation"));
        vo.setNtStation(FormUtil.getParameter(request, "d_ntStation"));
        vo.setMileage(FormUtil.getParameter(request, "d_mileage"));             
        vo.setRecordFlag(RuleConstant.RECORD_FLAG_DRAFT);
        vo.setCreateOperator(PageControlUtil.getOperatorFromSession(request));
        return vo;
    }

    private int addByTrans(HttpServletRequest request, ParamsStationMapper paramsStationMapper, ParamsStation paramsStation) throws Exception {
        TransactionStatus status = null;
        int n = 0;
        try {
            request.setAttribute("lineId", paramsStation.getLineId());
            request.setAttribute("pStation", paramsStation.getpStation());
            request.setAttribute("nStation", paramsStation.getNtStation());
            status = txMgr.getTransaction(this.def);
            n = paramsStationMapper.addParamsStation(paramsStation);
            txMgr.commit(status);
        } catch (Exception e) {
            PubUtil.handExceptionForTran(e, txMgr, status);
        }
        return n;
    }
    

    private OperationResult delete(HttpServletRequest request, ParamsStationMapper paramsStationMapper, OperationLogMapper opLogMapper) throws Exception{
        OperationResult rmsg = new OperationResult();
        Vector<ParamsStation> pos = this.getReqAttributeForDelete(request);
        LogUtil logUtil = new LogUtil();
        int n = 0;

        try {
            n = this.deleteByTrans(request, paramsStationMapper, pos);
        } catch (Exception e) {
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_DELETE, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_DELETE, request, LogConstant.delSuccessMsg(n), opLogMapper);
        rmsg.addMessage(LogConstant.delSuccessMsg(n));
        return rmsg;
    }
    
    private Vector<ParamsStation> getReqAttributeForDelete(HttpServletRequest request) {
        ParamsStation po = new ParamsStation();
        String selectIds = request.getParameter("allSelectedIDs");
        Vector<ParamsStation> selectedItems = this.getSelectIDs(selectIds, request);       
        return selectedItems;
    }
    
    private Vector<ParamsStation> getSelectIDs(String selectedIds, HttpServletRequest request) {
        StringTokenizer st = new StringTokenizer(selectedIds, ";");
        String strIds = null;
        Vector<ParamsStation> sds = new Vector();
        ParamsStation sd;
        while (st.hasMoreTokens()) {
            strIds = st.nextToken();
            sd = this.getStation(strIds, "#");
            sds.add(sd);
        }
        return sds;
    }
    
    private ParamsStation getStation(String strIDs, String delim) {
        StringTokenizer st = new StringTokenizer(strIDs, delim);
        String tmp = null;
        int i = 0;
        ParamsStation sd = new ParamsStation();
        Vector<ParamsStation> sds = new Vector();
        while (st.hasMoreTokens()) {
            i++;
            tmp = st.nextToken();
            if (i == 1) {
                sd.setLineId(tmp);
                continue;
            }
            if (i == 2) {
                sd.setpStation(tmp);
                continue;
            }
            if (i == 3) {
                sd.setnStation(tmp);
                continue;
            }
            if (i == 4) {
                sd.setVersion(tmp);
                continue;
            }
        }
        return sd;
    }

    private int deleteByTrans(HttpServletRequest request, ParamsStationMapper paramsStationMapper, Vector<ParamsStation> pos) throws Exception{
        TransactionStatus status = null;
        int n = 0;
        try {
            status = txMgr.getTransaction(this.def);
            for (ParamsStation po : pos) {
                po.setRecordFlag(RuleConstant.RECORD_FLAG_DRAFT);
                n += paramsStationMapper.deleteParamsStation(po);
            }
            txMgr.commit(status);
        } catch (Exception e) {
            PubUtil.handExceptionForTran(e, txMgr, status);
        }
        return n;
    }
    
    private OperationResult modify(HttpServletRequest request, ParamsStationMapper paramsStationMapper, OperationLogMapper  opLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        ParamsStation vo = this.getReqAttribute(request);
        LogUtil logUtil = new LogUtil();
        try {         
            int n = this.modifyByTrans(request, paramsStationMapper, vo, rmsg);
            logUtil.logOperation(CommandConstant.COMMAND_MODIFY, request, LogConstant.modifySuccessMsg(n), opLogMapper);
            rmsg.addMessage("草稿版本，" + LogConstant.modifySuccessMsg(n));
            return rmsg;
        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_MODIFY, e, opLogMapper);
        }
    }   

    private int modifyByTrans(HttpServletRequest request, ParamsStationMapper paramsStationMapper, ParamsStation vo, OperationResult rmsg) throws Exception {
        TransactionStatus status = null;
        int n = 0;
        try {  
            request.setAttribute("lineId", vo.getLineId());
            request.setAttribute("pStation", vo.getpStation());
            request.setAttribute("nStation", vo.getnStation());           
            status = txMgr.getTransaction(this.def);
            n = paramsStationMapper.modifyParamsStation(vo);
            txMgr.commit(status);
        } catch (Exception e) {            
            PubUtil.handExceptionForTran(e, txMgr, status);
        }
        return n;
    }

    
    private OperationResult audit(HttpServletRequest request, ParamsStationMapper paramsStationMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        Vector<ParamsStation> pos = this.getReqAttributeForAudit(request);
        LogUtil logUtil = new LogUtil();
        int n = 0;
        try {

//            if(pos.size() > 1){
//                rmsg.addMessage("一次仅能审核一条记录，请重新选择！");
//                return rmsg;
//            }
            n = this.auditByTrans(request, paramsStationMapper, pos, rmsg);
        } catch (Exception e) {
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_AUDIT, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_AUDIT, request, LogConstant.auditSuccessMsg(n), opLogMapper);

        rmsg.addMessage(LogConstant.auditSuccessMsg(n));
        return rmsg;
    }   
    
    private Vector<ParamsStation> getReqAttributeForAudit(HttpServletRequest request) {
        ParamsStation po = new ParamsStation();
        String selectIds = request.getParameter("allSelectedIDs");
        Vector<ParamsStation> selectedItems = this.getSelectIDs(selectIds, request);       
        return selectedItems;
    }

    private int auditByTrans(HttpServletRequest request, ParamsStationMapper paramsStationMapper, Vector<ParamsStation> pos, OperationResult opResult) {
        TransactionStatus status = null;
        int n = 0;
        try {
            status = txMgr.getTransaction(this.def);
            for (ParamsStation po : pos) {
               
                //更新当前版本为历史版本
                int updateCurrentToHistory = paramsStationMapper.updateCurrentToHistory(po);
                //更新草稿版本为当前版本
                int auditUpdate = paramsStationMapper.auditUpdate(po);
                n += auditUpdate;
            }
            txMgr.commit(status);
        } catch (Exception e) {
            PubUtil.handExceptionForTran(e, txMgr, status);
        } finally {
            return n;
        }
    }
    
    private void queryUpdateResult(String command, String operType, HttpServletRequest request, ParamsStationMapper paramsStationMapper, OperationLogMapper operationLogMapper, OperationResult opResult, ModelAndView mv) throws Exception {
        if (!command.equals(CommandConstant.COMMAND_ADD)) {
            this.saveQueryControlDefaultValues(request, mv); //保存查询条件
        }
        this.queryForOp(request, this.paramsStationMapper, this.operationLogMapper, opResult);
    }
    
    private OperationResult queryForOp(HttpServletRequest request, ParamsStationMapper paramsStationMapper, OperationLogMapper opLogMapper, OperationResult opResult) throws Exception {
        LogUtil logUtil = new LogUtil();
        ParamsStation queryCondition;
        List<ParamsStation> resultSet;
        try {
            queryCondition = this.getQueryConditionForOp(request);
            if (queryCondition == null) {
                return null;
            }
            resultSet = paramsStationMapper.queryParamsStation(queryCondition);
            opResult.setReturnResultSet(resultSet);
        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + resultSet.size() + "条记录", opLogMapper);
        return opResult;
    }
    
    private ParamsStation getQueryConditionForOp(HttpServletRequest request) {
        ParamsStation qCon = new ParamsStation();
        String queryControlDefaultValues = request.getParameter("queryControlDefaultValues");
        String command = request.getParameter("command");
        if (FormUtil.isAddOrModifyMode(request) || command.equals(CommandConstant.COMMAND_AUDIT)) {
            //操作处于添加或修改模式
            qCon.setLineId(FormUtil.getParameter(request, "d_lineId"));
            qCon.setpStation(FormUtil.getParameter(request, "d_pStation"));
            qCon.setnStation(FormUtil.getParameter(request, "d_nStation"));
//            qCon.setVersion(FormUtil.getParameter(request, "d_version"));
           qCon.setNtStation(FormUtil.getParameter(request, "d_ntStation"));
            
            qCon.setRecordFlag(RuleConstant.RECORD_FLAG_USE);
            if (command.equals(CommandConstant.COMMAND_DELETE)) {
                return null;
            }
            if (command.equals(CommandConstant.COMMAND_ADD) || command.equals(CommandConstant.COMMAND_MODIFY)) {
                //新增/修改的保存  回显草稿版本的记录
                qCon.setRecordFlag(RuleConstant.RECORD_FLAG_DRAFT);
            }
        } else {
            //操作处于非添加模式
            HashMap vQueryControlDefaultValues = FormUtil.getQueryControlDefaultValues(queryControlDefaultValues);
            if (!vQueryControlDefaultValues.isEmpty()) {
                qCon.setRecordFlag(FormUtil.getQueryControlDefaultValue(vQueryControlDefaultValues, "q_recorqFlag"));
                qCon.setLineId(FormUtil.getParameter(request, "d_lineId"));
                qCon.setpStation(FormUtil.getParameter(request, "d_pStation"));
                qCon.setnStation(FormUtil.getParameter(request, "d_nStation"));
                qCon.setVersion(FormUtil.getParameter(request, "d_version"));
                qCon.setRecordFlag(RuleConstant.RECORD_FLAG_USE);
            }
        }
        return qCon;
    }

    private String[] getReqAttributeNames(HttpServletRequest request, String command) {
        String[] attrNames = {LINE,LINE_STATIONS,RECORDFLAG,STATION};
        return attrNames;
    }    
       
    private void getResultSetText(List resultSet, ModelAndView mv, String operType) {
        this.getResultSetTextForStation(resultSet, mv);
    }

    private void getResultSetTextForStation(List<ParamsStation> resultSet, ModelAndView mv) {
        List<PubFlag> recordFlags = (List<PubFlag>) mv.getModelMap().get(RLBaseController.RECORDFLAG);
        List<PubFlag> lineIds = (List<PubFlag>) mv.getModelMap().get(RLBaseController.LINE);
        List<PubFlag> Stations = (List<PubFlag>) mv.getModelMap().get(RLBaseController.STATION);
        for (ParamsStation sd : resultSet) {
            if (recordFlags != null && !recordFlags.isEmpty()) {
                sd.setRecordFlagName(DBUtil.getTextByCode(sd.getRecordFlag(), recordFlags));
            }
            if (lineIds != null && !lineIds.isEmpty()) {
                sd.setLineName(DBUtil.getTextByCode(sd.getLineId(), lineIds));
            }
            if (Stations != null && !Stations.isEmpty()) {
                sd.setpStationsName(DBUtil.getTextByCode(sd.getpStation(),sd.getLineId(), Stations));
                sd.setnStationsName(DBUtil.getTextByCode(sd.getnStation(),sd.getLineId(), Stations));
                sd.setNtStationsName(DBUtil.getTextByCode(sd.getNtStation(),sd.getLineId(), Stations));
            }
        }
    }
    
    @RequestMapping("/ParamsStationExportAll")
    public void exportExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {

        List results = this.getBufferElementsForCurClass(request,"com.goldsign.acc.app.basicparams.entity.ParamsStation");
        List<Map<String,String>> queryResults = this.entityToMap(results);
        ExpUtil.renderExportExcelPath(response, ExpUtil.exportExcel(queryResults, request));
    }

    private List<Map<String, String>> entityToMap(List results) {
        List<Map<String,String>> list =  new ArrayList<>();
        int row = results.size();
        for(int n=0;n<row ;n++){
            ParamsStation vo = (ParamsStation)results.get(n);
            Map<String,String> map = new HashMap<>();
            map.put("lineName", vo.getLineName());
            map.put("pStationsName", vo.getpStationsName());
            map.put("nStationsName", vo.getnStationsName());
            map.put("ntStationsName", vo.getnStationsName());
            map.put("mileage", vo.getMileage());
            map.put("recordFlagName", vo.getRecordFlagName());
            map.put("version", vo.getVersion());
            map.put("createTime", vo.getCreateTime());
            map.put("createOperator", vo.getCreateOperator());
            list.add(map);
        }
        return list;
    }
    
 
}
