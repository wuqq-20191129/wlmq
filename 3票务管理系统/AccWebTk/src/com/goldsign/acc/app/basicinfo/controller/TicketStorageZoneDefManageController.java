package com.goldsign.acc.app.basicinfo.controller;

import com.goldsign.acc.app.basicinfo.entity.TicketStorageStationContrastManage;
import com.goldsign.acc.app.basicinfo.mapper.TicketStorageZoneDefManageMapper;
import com.goldsign.acc.app.basicinfo.entity.TicketStorageZoneDefManage;
import com.goldsign.acc.app.basicinfo.mapper.TicketStorageStationContrastManageMapper;
import com.goldsign.acc.app.storageout.controller.TicketStorageOutProduceParentController;
import com.goldsign.acc.frame.constant.CommandConstant;
import com.goldsign.acc.frame.constant.LogConstant;
import com.goldsign.acc.frame.entity.PubFlag;
import com.goldsign.acc.frame.mapper.OperationLogMapper;
import com.goldsign.acc.frame.util.ExpUtil;
import com.goldsign.acc.frame.util.FormUtil;
import com.goldsign.acc.frame.util.LogUtil;
import com.goldsign.acc.frame.util.PageControlUtil;
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
 * @desc:票区定义
 * @author:xiaowu
 * @create date: 2017-08-01
 */
@Controller
public class TicketStorageZoneDefManageController  extends TicketStorageOutProduceParentController {

    @Autowired
    private TicketStorageZoneDefManageMapper mapper;
    
    @Autowired
    private TicketStorageStationContrastManageMapper ticketStorageStationContrastManageMapper;

    @RequestMapping("/ticketStorageZoneDefManage")
    public ModelAndView service(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView("/jsp/basicinfo/ticketStorageZoneDefManage.jsp");
        String command = request.getParameter("command");
        OperationResult opResult = new OperationResult();
        try {  
            if (command != null) {
                command = command.trim();
                if (command.equals(CommandConstant.COMMAND_MODIFY))//修改操作
                {
                    opResult = this.modify(request, this.mapper, this.operationLogMapper);
                }
                if (command.equals(CommandConstant.COMMAND_DELETE))//删除操作
                {
                    opResult = this.delete(request, this.mapper, this.operationLogMapper);
                }
                if (command.equals(CommandConstant.COMMAND_ADD))//增加操作
                {
                    opResult = this.add(request, this.mapper, this.operationLogMapper);
                }
                if (command.equals(CommandConstant.COMMAND_QUERY)) {
                    opResult = this.query(request, this.mapper, this.operationLogMapper);
                }
            } else {
                opResult = this.query(request, this.mapper, this.operationLogMapper);
            }

            if (command != null) {
                if (command.equals(CommandConstant.COMMAND_ADD) || command.equals(CommandConstant.COMMAND_DELETE) || command.equals(CommandConstant.COMMAND_MODIFY)) {
                    if (!command.equals(CommandConstant.COMMAND_ADD)) {
                        this.saveQueryControlDefaultValues(request, mv);//保存查询条件
                    }
                    this.queryForOp(request, this.mapper, this.operationLogMapper, opResult);
                }
            }
            
            //取操作员ID
            User user = (User) request.getSession().getAttribute("User");
            String sys_operator_id = user.getAccount();

            //仓库
            List<PubFlag> storageList = this.getStoreList(sys_operator_id);
            mv.addObject("storages", storageList);
            
            List<TicketStorageZoneDefManage> listTemp = new ArrayList<TicketStorageZoneDefManage>();
            List<TicketStorageZoneDefManage> list = opResult.getReturnResultSet();
            for(TicketStorageZoneDefManage tszdm: list){
                for(PubFlag pf : storageList){
                    if(tszdm.getStorage_id().trim().equals(pf.getCode())){
                        listTemp.add(tszdm);
                    }
                }
            }
            opResult.setReturnResultSet(listTemp);
        } catch (Exception e) {
            e.printStackTrace();
            opResult.addMessage(e.getMessage());
        }

        String[] attrNames = {};
        this.setPageOptions(attrNames, mv, request, response); //设置页面线路、车站、票卡、仓库等选项值、版本
        this.baseHandler(request, response, mv);
        this.SaveOperationResult(mv, opResult);
        new PageControlUtil().putBuffer(request,opResult.getReturnResultSet());

        return mv;
    }

    private OperationResult query(HttpServletRequest request, TicketStorageZoneDefManageMapper mapper, OperationLogMapper operationLogMapper) throws Exception {
        OperationResult or = new OperationResult();
        LogUtil logUtil = new LogUtil();
        TicketStorageZoneDefManage queryCondition;
        List<TicketStorageZoneDefManage> resultSet;
        try {
            queryCondition = this.getQueryCondition(request);
            request.getSession().setAttribute("queryCondition", queryCondition);
            resultSet = mapper.getTicketStorageZoneDefManage(queryCondition);
            or.setReturnResultSet(resultSet);
            or.addMessage("成功查询" + resultSet.size() + "条记录");
        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, operationLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + resultSet.size() + "条记录", operationLogMapper);
        return or;
    }
    
    @RequestMapping("/TicketStorageZoneDefExportAll")
    public void exportExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {
        List results = this.getBufferElementsForCurClass(request, "com.goldsign.acc.app.basicinfo.entity.TicketStorageZoneDefManage");
        String expAllFields = request.getParameter("expAllFields");
        List<Map<String, Object>> queryResults = ExpUtil.entityToMap(expAllFields,results);
        ExpUtil.renderExportExcelPath(response, ExpUtil.exportExcel(queryResults, request));
    }

    public OperationResult queryForOp(HttpServletRequest request, TicketStorageZoneDefManageMapper mapper, OperationLogMapper opLogMapper, OperationResult opResult) throws Exception {
        LogUtil logUtil = new LogUtil();
        TicketStorageZoneDefManage vo;
        List<TicketStorageZoneDefManage> resultSet;
        try {
            vo = this.getQueryConditionForOp(request);
            resultSet = mapper.getTicketStorageZoneDefManage(vo);
            opResult.setReturnResultSet(resultSet);
        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + resultSet.size() + "条记录", opLogMapper);
        return opResult;

    }

    private TicketStorageZoneDefManage getQueryCondition(HttpServletRequest request) {
        TicketStorageZoneDefManage qCon = new TicketStorageZoneDefManage();
        String areaId = FormUtil.getParameter(request, "q_area_id").replace(" ","");
        if(areaId != null && !areaId.equals("")){
            qCon.setArea_id("%" + areaId + "%");
        }
        qCon.setStorage_id(FormUtil.getParameter(request, "q_storage_id"));
        return qCon;
    }

    private TicketStorageZoneDefManage getReqAttribute(HttpServletRequest request, String type) {
        TicketStorageZoneDefManage po = new TicketStorageZoneDefManage();
        po.setArea_id(FormUtil.getParameter(request, "d_area_id"));
        po.setArea_name(FormUtil.getParameter(request, "d_area_name"));
        po.setStorage_id(FormUtil.getParameter(request, "d_storage_id"));
        po.setReal_num(FormUtil.getParameter(request, "d_real_num"));
        po.setUpper_num(FormUtil.getParameter(request, "d_upper_num"));
        return po;
    }

    public OperationResult modify(HttpServletRequest request, TicketStorageZoneDefManageMapper mapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        TicketStorageZoneDefManage po = this.getReqAttribute(request, CommandConstant.COMMAND_MODIFY);
        LogUtil logUtil = new LogUtil();
        int n = 0;
        try {
            String areaId = po.getArea_id();
            if (areaId.equals("02") || areaId.equals("03")) {
                rmsg.addMessage("该票区不允许修改");
                return rmsg;
            }

            if (!this.isExist(po)) {
                n = this.modifyByTrans(request, mapper, po);
            }else{
                rmsg.addMessage("已存在票卡数量不允许修改");
                return rmsg;
            }
        } catch (Exception e) {
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_MODIFY, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_MODIFY, request, LogConstant.modifySuccessMsg(n), opLogMapper);
        rmsg.addMessage(LogConstant.modifySuccessMsg(n));

        return rmsg;
    }

    private TicketStorageZoneDefManage getQueryConditionForOp(HttpServletRequest request) {

        TicketStorageZoneDefManage qCon = new TicketStorageZoneDefManage();
        HashMap vQueryControlDefaultValues = null;
        String queryControlDefaultValues = request.getParameter("queryControlDefaultValues");
        String command = request.getParameter("command");
        if (FormUtil.isAddOrModifyMode(request)) {//操作处于添加或修改模式
            qCon.setArea_id(FormUtil.getParameter(request, "d_area_id"));
            qCon.setArea_name(FormUtil.getParameter(request, "d_area_name"));
            qCon.setStorage_id(FormUtil.getParameter(request, "d_storage_id"));
            qCon.setReal_num(FormUtil.getParameter(request, "d_real_num"));
            qCon.setUpper_num(FormUtil.getParameter(request, "d_upper_num"));
        } else {//操作处于非添加模式
            vQueryControlDefaultValues = FormUtil.getQueryControlDefaultValues(queryControlDefaultValues);
        }
        return qCon;
    }

    private int modifyByTrans(HttpServletRequest request, TicketStorageZoneDefManageMapper mapper, TicketStorageZoneDefManage po) throws Exception {
        TransactionStatus status = null;
        int n = 0;
        try {
            status = txMgr.getTransaction(this.def);
            n = mapper.modifyTicketStorageZoneDefManage(po);
            txMgr.commit(status);
        } catch (Exception e) {
            if (txMgr != null) {
                txMgr.rollback(status);
            }
            throw e;
        }
        return n;
    }

    private OperationResult delete(HttpServletRequest request, TicketStorageZoneDefManageMapper mapper, OperationLogMapper operationLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        TicketStorageZoneDefManage prmVo = this.getReqAttribute(request, CommandConstant.COMMAND_DELETE);
        Vector<TicketStorageZoneDefManage> pos = this.getReqAttributeForDelete(request);
        LogUtil logUtil = new LogUtil();
        int n = 0;
        try {
            n = this.deleteByTrans(request, mapper, pos, prmVo);
            if(n == 0){
                rmsg.addMessage("票区不为空不能删除");
                return rmsg;
            }
        } catch (Exception e) {
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_DELETE, e, operationLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_DELETE, request, LogConstant.delSuccessMsg(n), operationLogMapper);
        rmsg.addMessage(LogConstant.delSuccessMsg(n));
        return rmsg;
    }

    private Vector<TicketStorageZoneDefManage> getReqAttributeForDelete(HttpServletRequest request) {
        TicketStorageZoneDefManage po = new TicketStorageZoneDefManage();
        String selectIds = request.getParameter("allSelectedIDs");
        Vector<TicketStorageZoneDefManage> selectedItems = this.getDeleteIDs(selectIds);
        return selectedItems;
    }

    private Vector<TicketStorageZoneDefManage> getDeleteIDs(String selectedIds) {
        StringTokenizer st = new StringTokenizer(selectedIds, ";");
        String strIds = null;
        Vector<TicketStorageZoneDefManage> list = new Vector();
        TicketStorageZoneDefManage po;
        while (st.hasMoreTokens()) {
            strIds = st.nextToken();
            po = this.getTicketStorageZoneDefManages(strIds, "#");
            list.add(po);
        }
        return list;
    }

    private TicketStorageZoneDefManage getTicketStorageZoneDefManages(String strIDs, String delim) {
        StringTokenizer st = new StringTokenizer(strIDs, delim);
        String tmp = null;
        int i = 0;
        TicketStorageZoneDefManage po = new TicketStorageZoneDefManage();

        while (st.hasMoreTokens()) {
            i++;
            tmp = st.nextToken();
            if (i == 1) {
                po.setArea_id(tmp);
                continue;
            }
            if (i == 2) {
                po.setStorage_id(tmp);
                continue;
            }
        }
        return po;
    }

    private int deleteByTrans(HttpServletRequest request, TicketStorageZoneDefManageMapper mapper, Vector<TicketStorageZoneDefManage> pos, TicketStorageZoneDefManage prmVo) {
        TransactionStatus status = null;
        int n = 0;
        try {
            status = txMgr.getTransaction(this.def);
            for (TicketStorageZoneDefManage po : pos) {
                n += mapper.deleteTicketStorageZoneDefManage(po);
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

    private OperationResult add(HttpServletRequest request, TicketStorageZoneDefManageMapper mapper, OperationLogMapper operationLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        TicketStorageZoneDefManage po = this.getReqAttribute(request, CommandConstant.COMMAND_ADD);
        LogUtil logUtil = new LogUtil();
        int n = 0;
        String preMsg = "";
        try {
            String areaName = po.getArea_name();
            if (po.getArea_name() != null || !po.getArea_name().equals("")) {
                po.setArea_id(this.getAreaId());
                
                if (areaName.length()>20) {
                    rmsg.addMessage("票区名称长度过长！");
                    return rmsg;
                }
            
                List<TicketStorageZoneDefManage>lists = mapper.isExistRecord(po);
                if (lists != null && lists.size() > 0) {
                    rmsg.addMessage("票库已存在此记录！");
                    return rmsg;
                }
                n = this.addByTrans(request, mapper, po);
            }
        } catch (Exception e) {
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_ADD, e, operationLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_ADD, request, preMsg + LogConstant.addSuccessMsg(n), operationLogMapper);
        rmsg.addMessage(LogConstant.addSuccessMsg(n));
        return rmsg;
    }
    
    /*
     * <method>retrieveAreaList</method>
     * <describe>获取票区最新ID</describe><p>
     * @return String
     * @throws Exception
     */
    public String getAreaId() throws Exception {
        String RcheckNo = "";
        String Rcount = mapper.getAreaIdCount();
        if (Rcount == null || Rcount.equals("") || Rcount.equals("0")) {
            RcheckNo = "1";
        }else{
            RcheckNo = mapper.getAreaIdCountNew();
        }
        if (RcheckNo.length() == 1) {
            RcheckNo = "0" + RcheckNo;
        }
        return RcheckNo;
    }

    private int addByTrans(HttpServletRequest request, TicketStorageZoneDefManageMapper mapper, TicketStorageZoneDefManage vo) {
        TransactionStatus status = null;
        int n = 0;
        try {
            status = txMgr.getTransaction(this.def);
            n = mapper.addTicketStorageZoneDefManage(vo);
            txMgr.commit(status);
        } catch (Exception e) {
            if (txMgr != null) {
                txMgr.rollback(status);
            }
            throw e;
        }
        return n;
    }

    private boolean isExist(TicketStorageZoneDefManage po) {
        boolean flag = false;
        List<TicketStorageZoneDefManage> realNums = mapper.getRealNum(po);
        String realNum = "";
        if (realNums != null && realNums.size() > 0) {
            realNum = realNums.get(0).getReal_num();
        }
//        strSql = "select real_num from ic_cod_area where area_id='" + name + "'";
//            dbHelper.getFirstDocument(strSql);
//            realNum = dbHelper.getItemValue("real_num");
        if (realNum.equals("0") || realNum == null) {
            flag = false;
        } else {
            flag = true;
        }
        return flag;
    }

    private List<PubFlag> getStoreList(String sys_operator_id) throws Exception {
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

    private Vector getStorageVector() {
        List<TicketStorageStationContrastManage> lists = ticketStorageStationContrastManageMapper.getStorageVector();
        Vector tableFlags = new Vector();
        for (TicketStorageStationContrastManage vo : lists) {
            PubFlag pv = new PubFlag();
            pv.setCode(vo.getLine_id());
            pv.setCode_text(vo.getLine_name());
            tableFlags.add(pv);
        }
        return tableFlags;
    }

    public Set getUserStoreSet(String operatorId) throws Exception {
        Vector paramList = new Vector();
        Set vectorSet = new TreeSet();
        TicketStorageStationContrastManage vo = new TicketStorageStationContrastManage();
        vo.setSys_operator_id(operatorId);
        List<TicketStorageStationContrastManage> lists = ticketStorageStationContrastManageMapper.getUserStoreSet(vo);
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
}
