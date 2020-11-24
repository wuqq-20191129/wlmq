package com.goldsign.acc.app.basicinfo.controller;

import com.goldsign.acc.app.basicinfo.entity.TicketStorageChestDefManage;
import com.goldsign.acc.app.basicinfo.entity.TicketStorageStationContrastManage;
import com.goldsign.acc.app.basicinfo.mapper.TicketStorageChestDefManageMapper;
import com.goldsign.acc.app.basicinfo.mapper.TicketStorageStationContrastManageMapper;
import com.goldsign.acc.frame.constant.CommandConstant;
import com.goldsign.acc.frame.controller.StorageOutInBaseController;
import com.goldsign.acc.frame.entity.PubFlag;
import com.goldsign.acc.frame.mapper.OperationLogMapper;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 票柜定义
 * @author xiaowu
 * 20170726
 */
@Controller
public class TicketStorageChestDefManageController extends StorageOutInBaseController {
    
    private final Logger logger =LoggerFactory.getLogger(TicketStorageChestDefManageController.class);
    
    @Autowired
    private TicketStorageChestDefManageMapper infoMapper;
    
    @Autowired
    private TicketStorageStationContrastManageMapper ticketStorageStationContrastManageMapper;
    
    @RequestMapping(value = "/ticketStorageChestDefManage")
    public ModelAndView service(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView("/jsp/basicinfo/ticketStorageChestDefManage.jsp");
        String command = request.getParameter("command");
        OperationResult opResult = new OperationResult();
        try {
            if (command != null) {
                command = command.trim();
                if (command.equals(CommandConstant.COMMAND_QUERY)) {
                    opResult = this.queryInfo(request, this.infoMapper, this.operationLogMapper);
                }
                if (command.equals(CommandConstant.COMMAND_ADD)) {
                    opResult = this.addInfo(request, this.infoMapper, this.operationLogMapper);
                }
                if (command.equals(CommandConstant.COMMAND_DELETE)) {
                    opResult = this.deleteInfo(request, this.infoMapper, this.operationLogMapper);
                }
                if (command.equals(CommandConstant.COMMAND_MODIFY)) {
                    opResult = this.modifyInfo(request, this.infoMapper, this.operationLogMapper);
                }
                if (command.equals("detail")) {
                    mv = new ModelAndView("/jsp/basicinfo/ticketStorageChestDefManageDetail.jsp");
                    opResult = this.queryInfoDetail(request, this.infoMapper, this.operationLogMapper);
                }
               
                if (command.equals(CommandConstant.COMMAND_ADD) || command.equals(CommandConstant.COMMAND_DELETE) || command.equals(CommandConstant.COMMAND_MODIFY)
                       || command.equals(CommandConstant.COMMAND_SUBMIT)) {//更新操作，增、删、改、审核,查询更新结果或原查询结果
                    this.queryUpdateResult(command, request, infoMapper, operationLogMapper, opResult, mv);
                }  
            }
            //取操作员ID
            User user = (User) request.getSession().getAttribute("User");
            String sys_operator_id = user.getAccount();

            //仓库
            List<PubFlag> storageList = this.getStoreList(sys_operator_id);
            mv.addObject("storages", storageList);
        } catch (Exception e) {
            e.printStackTrace();
            opResult.addMessage(e.getMessage());
        }

        
        //票卡主类型/票卡子类型员
        String[] attrNames = {IC_CARD_MAIN, IC_CARD_MAIN_SERIAL, IC_CARD_SUB, IC_CARD_MAIN_SUB};
        this.setPageOptions(attrNames, mv, request, response); //设置页面线路、车站、票卡、仓库等选项值、版本
        this.baseHandlerForOutIn(request, response, mv); //出入库基本回传参数
        this.baseHandler(request, response, mv);
        this.divideResultSet(request, mv, opResult); //结果集分页
        this.SaveOperationResult(mv, opResult); //返回结果集
        return mv;
    }
    
    @RequestMapping("/ticketStorageChestDefExportAll")
    public void exportExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {
            List results = this.getBufferElementsForCurClass(request, "com.goldsign.acc.app.basicinfo.entity.TicketStorageChestDefManage");
            String expAllFields = request.getParameter("expAllFields");
            List<Map<String, Object>> queryResults = ExpUtil.entityToMap(expAllFields, results);
            ExpUtil.renderExportExcelPath(response, ExpUtil.exportExcel(queryResults, request));
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

    private void queryUpdateResult(String command, HttpServletRequest request, TicketStorageChestDefManageMapper infoMapper,
            OperationLogMapper opLogMapper, OperationResult opResult, ModelAndView mv) throws Exception {
        if (!command.equals(CommandConstant.COMMAND_ADD)) {
            this.saveQueryControlDefaultValues(request, mv); //保存查询条件
        } 
        this.queryForOpInfo(request, infoMapper, opLogMapper, opResult);
    }


    public OperationResult queryForOpInfo(HttpServletRequest request, TicketStorageChestDefManageMapper infoMapper, OperationLogMapper opLogMapper, OperationResult opResult) throws Exception {
        LogUtil logUtil = new LogUtil();
        TicketStorageChestDefManage queryCondition;
        List<TicketStorageChestDefManage> resultSet;
        List<TicketStorageChestDefManage> resultSetTemp = new ArrayList<TicketStorageChestDefManage>();

        try {
            queryCondition = this.getReqAttribute(request);
            if (queryCondition == null) {
                return null;
            }
            resultSet = infoMapper.getTicketStorageChestDefManages(queryCondition);
            if(queryCondition.getStorage_id() == null || queryCondition.getStorage_id().equalsIgnoreCase("")){
                List<PubFlag> storageIdList = getStoreList(((User) request.getSession().getAttribute("User")).getAccount());
                for(TicketStorageChestDefManage tscdf : resultSet){
                    for(PubFlag pf : storageIdList){
                        if(pf.getCode().equals(tscdf.getStorage_id())){
                            resultSetTemp.add(tscdf);
                        }
                    }
                }
                resultSet = resultSetTemp;
            }
            opResult.setReturnResultSet(resultSet);
        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + resultSet.size() + "条记录", opLogMapper);
        return opResult;

    }

    public OperationResult queryInfo(HttpServletRequest request, TicketStorageChestDefManageMapper infoMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult or = new OperationResult();
        LogUtil logUtil = new LogUtil();
        TicketStorageChestDefManage queryCondition;
        List<TicketStorageChestDefManage> resultSet;
        List<TicketStorageChestDefManage> resultSetTemp = new ArrayList<TicketStorageChestDefManage>();
        try {
            queryCondition = this.getQueryCondition(request);
            resultSet = infoMapper.getTicketStorageChestDefManages(queryCondition);
            if(queryCondition == null || queryCondition.getStorage_id() == null || queryCondition.getStorage_id().equalsIgnoreCase("")){
                List<PubFlag> storageIdList = getStoreList(((User) request.getSession().getAttribute("User")).getAccount());
                for(TicketStorageChestDefManage tscdf : resultSet){
                    for(PubFlag pf : storageIdList){
                        if(pf.getCode().equals(tscdf.getStorage_id())){
                            resultSetTemp.add(tscdf);
                        }
                    }
                }
                resultSet = resultSetTemp;
            }
            or.setReturnResultSet(resultSet);
            or.addMessage("成功查询" + resultSet.size() + "条记录");
        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + resultSet.size() + "条记录", opLogMapper);
        return or;
    }
    
    public OperationResult queryInfoDetail(HttpServletRequest request, TicketStorageChestDefManageMapper infoMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult or = new OperationResult();
        LogUtil logUtil = new LogUtil();
        TicketStorageChestDefManage queryCondition = new TicketStorageChestDefManage();
        List<TicketStorageChestDefManage> resultSet;
        String chestId = (String) request.getParameter("chestId");
        String areaId = (String) request.getParameter("areaId");
        String storageId = (String) request.getParameter("storageId");
        
        try {
            if(chestId!=null && areaId != null && storageId != null){
                queryCondition.setArea_id(areaId);
                queryCondition.setChest_id(chestId);
                queryCondition.setStorey_id(storageId);
                resultSet = infoMapper.getTicketStorageChestDefManageDetails(queryCondition);
            }else{
                resultSet = new ArrayList<TicketStorageChestDefManage>();
            }
            or.setReturnResultSet(resultSet);
            or.addMessage("成功查询" + resultSet.size() + "条记录");
        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + resultSet.size() + "条记录", opLogMapper);
        return or;
    }

    public OperationResult addInfo(HttpServletRequest request, TicketStorageChestDefManageMapper infoMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        LogUtil logUtil = new LogUtil();
        try {
            TicketStorageChestDefManage vo = this.getReqAttribute(request);
            rmsg = this.addInfoByTrans(request,vo,rmsg);
        } catch (Exception e) {
            rmsg.addMessage("增加票柜失败");
            return rmsg;
        }
        return rmsg;
    }
    
    

    public OperationResult deleteInfo(HttpServletRequest request, TicketStorageChestDefManageMapper infoMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        Vector<TicketStorageChestDefManage> pos = this.getReqAttributeForDelete(request);
        return this.deleteInfoByTrans(request, infoMapper, pos);
    }
    
    private Vector<TicketStorageChestDefManage> getReqAttributeForDelete(HttpServletRequest request) {
        String selectIds = request.getParameter("allSelectedIDs");
        Vector<TicketStorageChestDefManage> selectedItems = this.getDeleteInfos(selectIds);
        return selectedItems;
    }
    
    private OperationResult deleteInfoByTrans(HttpServletRequest request, TicketStorageChestDefManageMapper infoMapper, Vector<TicketStorageChestDefManage> infos) throws Exception {
        OperationResult rmsg = new OperationResult();
        TransactionStatus status = null;
        int n = 0;
        try {
            boolean flag = false;
            for (TicketStorageChestDefManage info : infos) {
                //验证票柜是否为空
                flag = this.isEmptyForChest(infoMapper,info);
                if (!flag) {
                    //票柜不为空不能删除
                    rmsg.addMessage("删除失败，票柜不为空不能删除！");
                    return rmsg;
                }
            }
            status = txMgr.getTransaction(this.def);
            rmsg = this.delete(infos);
            txMgr.commit(status);
        } catch (Exception e) {
            if (txMgr != null) {
                txMgr.rollback(status);
            }
            rmsg.addMessage("删除失败!");
            e.printStackTrace();
            return rmsg;
        }
        return rmsg;
    }
    
    private Vector<TicketStorageChestDefManage> getDeleteInfos(String selectedIds) {
        StringTokenizer st = new StringTokenizer(selectedIds, ";");
        String strIds = null;
        Vector<TicketStorageChestDefManage> infos = new Vector();
        TicketStorageChestDefManage info;
        while (st.hasMoreTokens()) {
            strIds = st.nextToken();
            info = this.getDeleteInfo(strIds, "#");
            infos.add(info);
        }
        return infos;
    }
    
    private TicketStorageChestDefManage getDeleteInfo(String strIDs, String delim) {
        StringTokenizer st = new StringTokenizer(strIDs, delim);
        String tmp = null;
        int i = 0;
        TicketStorageChestDefManage info = new TicketStorageChestDefManage();
        while (st.hasMoreTokens()) {
            i++;
            tmp = st.nextToken();
            if (i == 1) {
                info.setChest_name(tmp);
                continue;
            }
            if (i == 2) {
                info.setChest_id(tmp);
                continue;
            }
            if (i == 3) {
                info.setStorage_id(tmp);
                continue;
            }
            if (i == 4) {
                info.setArea_id(tmp);
                continue;
            }
        }
        return info;
    }

    /**
     * 修改记录
     */
    public OperationResult modifyInfo(HttpServletRequest request, TicketStorageChestDefManageMapper infoMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        TicketStorageChestDefManage vo = this.getReqAttribute(request);
        LogUtil logUtil = new LogUtil();
        try {
            rmsg = this.modifyInfoByTrans(request, vo, rmsg);
        } catch (Exception e) {
            rmsg.addMessage("修改失败！！");
            return rmsg;
        }
        return rmsg;
    }
    
    public TicketStorageChestDefManage getReqAttribute(HttpServletRequest request) {
        TicketStorageChestDefManage vo = new TicketStorageChestDefManage();
        HashMap vQueryControlDefaultValues = null;
        String queryControlDefaultValues = request.getParameter("queryControlDefaultValues");
        String command = request.getParameter("command");
        if (FormUtil.isAddOrModifyMode(request)) {
            //操作处于添加或修改模式
            if (command.equals(CommandConstant.COMMAND_DELETE)) {
                return null;
            }
            vo.setChest_id(FormUtil.getParameter(request, "d_chest_id"));                   //票柜代码
            vo.setChest_name(FormUtil.getParameter(request, "d_chest_name"));               //票柜名称
            vo.setIc_main_type(FormUtil.getParameter(request, "d_ic_main_type"));           //票卡主类型
            vo.setIc_sub_type(FormUtil.getParameter(request, "d_ic_sub_type"));             //票卡子类型
            vo.setStorage_id(FormUtil.getParameter(request, "d_storage_id"));               //仓库 
            vo.setArea_id(FormUtil.getParameter(request, "d_area_id"));                     //票区
            vo.setStorey_num(FormUtil.getParameter(request, "d_storey_num"));               //层数
            vo.setBase_num(FormUtil.getParameter(request, "d_base_num"));                   //层托数
            vo.setMax_box_num(FormUtil.getParameter(request, "d_max_box_num"));             //托最大盒数
            vo.setCard_money(FormUtil.getParameter(request, "d_card_money"));               //面值(分/次) 
        } else { 
            //操作处于非添加模式
            vQueryControlDefaultValues = FormUtil.getQueryControlDefaultValues(queryControlDefaultValues);
            if (!vQueryControlDefaultValues.isEmpty()) {
                vo.setIc_main_type(FormUtil.getParameter(request, "q_ic_main_type"));
                vo.setIc_sub_type(FormUtil.getParameter(request, "q_ic_sub_type"));
                vo.setStorage_id(FormUtil.getParameter(request, "q_storage_id"));
                vo.setArea_id(FormUtil.getParameter(request, "q_area_id"));
                vo.setChest_id(FormUtil.getParameter(request, "q_chest_id"));
            }
        } 
        return vo;
    }
    
    public TicketStorageChestDefManage getQueryCondition(HttpServletRequest request){
        TicketStorageChestDefManage info = new TicketStorageChestDefManage();
        
        info.setIc_main_type(FormUtil.getParameter(request, "q_ic_main_type"));
        info.setIc_sub_type(FormUtil.getParameter(request, "q_ic_sub_type"));
        info.setStorage_id(FormUtil.getParameter(request, "q_storage_id"));
        info.setArea_id(FormUtil.getParameter(request, "q_area_id"));
        info.setChest_id(FormUtil.getParameter(request, "q_chest_id"));
        
        return info;
    }
    
//    @Transactional
    public OperationResult addInfoByTrans(HttpServletRequest request,TicketStorageChestDefManage vo, OperationResult opResult) throws Exception {
        OperationResult rmsg = new OperationResult();
        LogUtil logUtil = new LogUtil();
        TransactionStatus status = null;
        int n = 0;
        try {
            if (vo.getArea_id() != null){
                List<TicketStorageChestDefManage> temp = new ArrayList<TicketStorageChestDefManage>();
                if(vo.getArea_id().equals("01")) {
                    TicketStorageChestDefManage voTemp = new TicketStorageChestDefManage();
                    voTemp.setStorage_id(vo.getStorage_id());
                    voTemp.setArea_name("赋值区");
                    temp = infoMapper.getAreaId(voTemp);
                    if (temp == null || temp.size() < 1) {
                        rmsg.addMessage("票区未创建");
                        return rmsg;
                    }
                }
                if(vo.getArea_id().equals("02")) {
                    TicketStorageChestDefManage voTemp = new TicketStorageChestDefManage();
                    voTemp.setStorage_id(vo.getStorage_id());
                    voTemp.setArea_name("编码区");
                    temp = infoMapper.getAreaId(voTemp);
                    if (temp == null || temp.size() < 1) {
                        rmsg.addMessage("票区未创建");
                        return rmsg;
                    }
                }
            }
            
            String chestId = vo.getChest_id();
            if(chestId != null){
                if (chestId.length() < 3) {
                    rmsg.addMessage("柜ID长度为3位数字");
                    return rmsg;
                }
                List<TicketStorageChestDefManage> tscds = infoMapper.checkChestId(vo);
                if(tscds != null && tscds.size() > 0){
                    rmsg.addMessage("票柜代码已被使用"); 
                    return rmsg;
                }
            }
            //保存数据 
            vo.setFull_flag("0");
            String chestName = this.getAreaNameAndSubName(vo);
            vo.setChest_name(chestName);
            boolean areaFlag = this.getCurrentAreaNum(vo,infoMapper);
            status = txMgr.getTransaction(this.def);
            int addResult = infoMapper.addTicketStorageChestDefManage(vo);
            if (addResult == 1) { 
                //票柜里自动增加层代码
                addResult = this.addStoreyVector(vo);
                if (addResult > 0) {
                    //更新票区上限值
                    if (areaFlag) {
                        txMgr.commit(status);
                        rmsg.addMessage("增加票柜成功");
                        return rmsg;
                    } else {
                        //回滚数据
                        rmsg.addMessage("增加票柜失败");
                        throw new RuntimeException("增加票柜失败");
                    }
                } else {
                    //回滚数据
                    rmsg.addMessage("增加票柜失败");
                    throw new RuntimeException("增加票柜失败");
                }
            }
        } catch (Exception e) {
            if (txMgr != null) {
                txMgr.rollback(status);
            }
            e.printStackTrace();
            throw e;
        }
        return rmsg;
    }
    
    private int addStoreyVector(TicketStorageChestDefManage vo) {
        int result = 0;
        int circleTime = Integer.parseInt((String) vo.getStorey_num());
        if (circleTime >= 1) {
            for (int i = 1; i <= circleTime; i++) {
                try {
                    Vector vectorStorey = new Vector();
                    String s = new Integer(i).toString();
                    if (s.length() == 1) {
                        s = "0" + s;
                    }
                    vo.setStorey_id(s);
                    TicketStorageChestDefManage temp = new TicketStorageChestDefManage();
                    temp.setStorage_id(vo.getStorage_id());
                    temp.setArea_id(vo.getArea_id());
                    temp.setChest_id(vo.getChest_id());
                    temp.setStorey_id(vo.getStorey_id());
                    temp.setStorey_name(vo.getStorey_id() + "层");
                    temp.setBase_num(vo.getBase_num());
                    temp.setFull_flag("0"); 
                    
                    result  = infoMapper.addStorey(temp);
                    if (result == 1) { 
                        int baseFlag = this.addBase(vo,infoMapper); 
                        if (baseFlag != 1) {
                            return 0;
                        }
                    } else {
                        return 0;
                    }
                } catch (Exception e) {
                    System.out.println("票柜定义,票柜创建层代码异常");
                    throw e;
                } finally {
                }
            }
            return 1;
        } else {
            return 0;
        }
    }

    private int addBase(TicketStorageChestDefManage vo, TicketStorageChestDefManageMapper infoMapper) {
        int result = 0;
        int circleTime = Integer.parseInt(vo.getBase_num());
        if (circleTime >= 1) {
            for (int i = 1; i <= circleTime; i++) {
                try {
                    Vector vectorBase = new Vector();
                    String s = new Integer(i).toString();
                    if (s.length() == 1) {
                        s = "0" + s;
                    }
                    vo.setBase_id(s);
                    TicketStorageChestDefManage temp = new TicketStorageChestDefManage();
                    temp.setStorage_id(vo.getStorage_id());
                    temp.setArea_id(vo.getArea_id());
                    temp.setChest_id(vo.getChest_id());
                    temp.setStorey_id(vo.getStorey_id());
                    temp.setBase_id(vo.getBase_id());
                    temp.setBase_name(vo.getBase_id() + "托");
                    temp.setReal_num("0");
                    temp.setMax_box_num(vo.getMax_box_num());
                    
                    result  = infoMapper.addBase(temp);
                    
                    if (result != 1) {
                        return 0;
                    }
                } catch (Exception e) {
                    System.out.println("票柜定义,新增票柜创建托代码异常");
                    throw new RuntimeException("票柜定义,新增票柜创建托代码异常");
                } finally {
                }
            }
            return 1;
        } else {
            return 0;
        }
    }

    private boolean getCurrentAreaNum(TicketStorageChestDefManage vo, TicketStorageChestDefManageMapper infoMapper) {
        int storey = Integer.parseInt(vo.getStorey_num());//层数 
        boolean i = false;
        int boxUnit = 0;
        int base = Integer.parseInt(vo.getBase_num());  // 托数
        int maxBox = Integer.parseInt(vo.getMax_box_num());
        int currentNum = storey * base * maxBox;
        try {
            List<TicketStorageChestDefManage> tscdms = infoMapper.getTicketUnit(vo);
            if (tscdms != null && tscdms.size()>0) {
                boxUnit = Integer.parseInt(tscdms.get(0).getBox_unit());
            }
            currentNum *= boxUnit;
            vo.setUpper_num(String.valueOf(currentNum));
            int re = infoMapper.upperAreaNum(vo);
            if (re == 1) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            System.out.println("票柜定义,更新票区上限值(向上更新)异常");
            throw e; 
        }
    }

    public boolean isEmptyForChest(TicketStorageChestDefManageMapper infoMapper,TicketStorageChestDefManage vo) {
        List<TicketStorageChestDefManage> tscdms = infoMapper.isEmptyForChest(vo);
        String cardNum = "";
        if (tscdms != null && tscdms.size() > 0) {
            cardNum = tscdms.get(0).getCard_num();
        }
        if (cardNum.equals("0")) {
            return true;
        }
        return false;
    }
    
    public OperationResult delete(Vector<TicketStorageChestDefManage> infos)throws Exception{
        OperationResult rmsg = new OperationResult();
        int delNum = 0;
        if(infos != null && infos.size() > 0 ){
            for(TicketStorageChestDefManage info : infos){
                //删除柜包含数量为0的盒信息，库存信息
                this.deleteRecordCardNumZero(info);
                //删除票柜前，同步更新票区上限
                boolean areaFlag = this.updateAreaUpperNum(info);
                if (areaFlag) {
                    int n = infoMapper.deleteBase(info);
                    if (n != 0) {
                        //删除票层
                        int del = this.deleteStorey(info,0);
                        delNum ++;
                        if(del == 0){
                            throw new RuntimeException("删除数据失败,回滚数据！");
                        }
                    }
                }else{
                    throw new RuntimeException("删除失败,回滚数据！");
                } 
            }
            rmsg.addMessage("成功删除" + delNum + "条记录");
        }
        return rmsg;
    }
    

    private void deleteRecordCardNumZero(TicketStorageChestDefManage info) {
//        String sql = "delete from ic_cod_box_info where box_id in(select box_id from ic_sts_storage where storage_id=? and area_id=? and chest_id=? and card_num=0)";
        int n = infoMapper.deleteCodBoxInfo(info);
        //concat(storageCode,'#',areaCode,'#',chestCode)
        logger.info("清除盒信息中仓库:" + info.getStorage_id() + "票区:" + info.getArea_id() + "柜" + info.getChest_id() + "卡数量为0记录数" + n);
//        sql = "delete  from ic_sts_storage where storage_id=? and area_id=? and chest_id=? and card_num=0";
//        n = dbHelper.executeUpdate(sql, values);
        n = infoMapper.deleteStsStorage(info);
        logger.info("清除库存信息表中仓库:" + info.getStorage_id() + "票区:" + info.getArea_id() + "柜" + info.getChest_id() + "卡数量为0记录数" + n);
    }

    private boolean updateAreaUpperNum(TicketStorageChestDefManage info) {
        int boxUnit = 0;
        boolean flag = false;
        int storey = 0;
        int base = 0;
        int maxBox = 0;
        try {
            List<TicketStorageChestDefManage> lists = infoMapper.getStoreyNum(info);
            if (lists != null && lists.size() > 0) {
                storey = Integer.parseInt(lists.get(0).getStorey_num());
                lists = infoMapper.getBaseNum(info);
                if (lists != null && lists.size() > 0) {
                    base = Integer.parseInt(lists.get(0).getBase_num());
                    lists = infoMapper.getMaxBoxNum(info);
                    if (lists != null && lists.size() > 0) {
                        maxBox = Integer.parseInt(lists.get(0).getMax_box_num());
                        int uppNum = storey * base * maxBox;
                        lists = infoMapper.getBoxUnit(info);
                        if (lists != null && lists.size() > 0) {
                            boxUnit = Integer.parseInt(lists.get(0).getBox_unit());
                        }
                        uppNum *= boxUnit;
                        String upperNumTemp = info.getUpper_num();
                        info.setUpper_num(String.valueOf(uppNum));
                        int i = infoMapper.lowerAreaNum(info);
                        
                        // 还原 upperNum
                        info.setUpper_num(upperNumTemp);
                        if (i != 0) {
                            return true;
                        } else {
                            return false;
                        }
                    } else {
                        return false;
                    }
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } catch (Exception e) {
            logger.error("票柜定义,删除票柜前，同步更新票区上限:", e);
            throw new RuntimeException("票柜定义,删除票柜前，同步更新票区上限");
        }
    }
    
//    @Transactional
    public OperationResult modifyInfoByTrans(HttpServletRequest request, TicketStorageChestDefManage vo, OperationResult rmsg)throws Exception{
        try {
            if (vo != null && vo.getArea_id() != null){
                List<TicketStorageChestDefManage> temp = new ArrayList<TicketStorageChestDefManage>();
                if(vo.getArea_id().equals("01")) {
                    TicketStorageChestDefManage voTemp = new TicketStorageChestDefManage();
                    voTemp.setStorage_id(vo.getStorage_id());
                    voTemp.setArea_name("赋值区");
                    temp = infoMapper.getAreaId(voTemp);
                    if (temp == null || temp.size() < 1) {
                        rmsg.addMessage("票区未创建");
                        return rmsg;
                    }
                }
                if(vo.getArea_id().equals("02")) {
                    TicketStorageChestDefManage voTemp = new TicketStorageChestDefManage();
                    voTemp.setStorage_id(vo.getStorage_id());
                    voTemp.setArea_name("编码区");
                    temp = infoMapper.getAreaId(voTemp);
                    if (temp == null || temp.size() < 1) {
                        rmsg.addMessage("票区未创建");
                        return rmsg;
                    }
                }
            }
            
            List<TicketStorageChestDefManage> tsTemp = infoMapper.checkBase(vo);
            //验证票柜是否为空
            if(tsTemp != null && tsTemp.size() > 0 ){
                //票柜不为空不能修改
                rmsg.addMessage("修改失败，票柜不为空,不能修改！");
                return rmsg;
            }
//        TransactionStatus status = null;
        
//            status = txMgr.getTransaction(this.def);
            rmsg = this.modifyChest(vo);
//            txMgr.commit(status);
        } catch (Exception e){
//            if (txMgr != null) {
//                txMgr.rollback(status);
//            }
            rmsg.addMessage("修改出错");
            return rmsg;
        }
        return rmsg;
    }

    private OperationResult modifyChest(TicketStorageChestDefManage vo)throws Exception{
        TransactionStatus status = null;
        OperationResult rsmg = new OperationResult();
        int result = 0;
        int money = 0;
        String inplace = null;
        try {
            String chestName = this.getAreaNameAndSubName(vo);
            vo.setChest_name(chestName);
            List<TicketStorageChestDefManage> lists = infoMapper.getCardMoney(vo);
            if (lists !=null && lists.size()>0) {
                money = Integer.parseInt(lists.get(0).getCard_money());
            }
            inplace = vo.getStorage_id() + vo.getArea_id() + vo.getChest_id();
            status = txMgr.getTransaction(this.def);
            //更新票区上限 --向下更新
            if (this.updateAreaNum(vo,0)) {
                result = infoMapper.updateCodeChest(vo);
                if (result > 0) {
                    //删除票层
                    int s = this.deleteStorey( vo, 1);
                    if (s > 0) {
                        System.out.println("删除票层成功");
                        //票柜修改--删除票托
                        int k = this.deleteBase(vo);
                        if (k > 0) {
                            System.out.println("删除票托成功");
                            int l = this.addStoreyVector(vo);
                            if (l > 0) {
                                System.out.println("增加票层托成功");
                                //更新票区上限 --向下更新
                                if (this.updateAreaNum(vo, 1)) {
                                    //清除 生产入库表ic_access_place 中入库最后的位置
                                    this.clearProductInPlaceStatus(vo , inplace, money);
                                    rsmg.addMessage("修改票柜成功");
                                    txMgr.commit(status);
                                    return rsmg;
                                } else {
                                    throw new RuntimeException("修改票柜失败");
                                }
                            } else {
                                throw new RuntimeException("修改票柜失败");
                            }
                        } else {
                            throw new RuntimeException("修改票柜失败");
                        }
                    } else {
                        throw new RuntimeException("修改票柜失败");
                    }
                } else {
                    throw new RuntimeException("修改票柜失败");
                }
            } else {
                throw new RuntimeException("修改票柜失败");
            }
        } catch (Exception e) {
            if (txMgr != null) {
                txMgr.rollback(status);
            }
            rsmg.addMessage("修改出错,修改票柜失败");
            return rsmg;
        }
    }
    
    public int deleteBase(TicketStorageChestDefManage vo) throws Exception {
        int i = 0;
        try {
            i = infoMapper.deleteBase(vo);
            if (i != 0) {
                System.out.println("票层删除成功！删除个数：" + i);
                System.out.println("开始执行删除票柜操作");
                return i;
            } else {
                return i;
            }
        } catch (Exception e) {
            logger.error("票柜定义,票柜修改--删除票托异常:", e);
            throw e;
        }
    }
    
    public String getAreaNameAndSubName(TicketStorageChestDefManage vo) throws Exception{
        String result = "";
        List<TicketStorageChestDefManage> lists = infoMapper.getChestName(vo);
        for(TicketStorageChestDefManage tempChestDefManage: lists){
            if (tempChestDefManage != null) {
                result = tempChestDefManage.getChest_name()+vo.getChest_id();
                break;
            }
        }
        return result;
    }

    private boolean updateAreaNum(TicketStorageChestDefManage vo,int status) throws Exception{
        boolean flag = false;
        Vector ve = new Vector();
        int updateNum = 0;
        int boxUnit = 0;
        boolean k = false;
        try {
            if (status == 0) {
                List<TicketStorageChestDefManage> lists = infoMapper.getChestMaxNum(vo);
                System.out.println(flag);
                if (lists !=null  && lists.size() > 0) {
                    updateNum = Integer.parseInt(lists.get(0).getMax_box_num());
                    lists = infoMapper.getBoxUnit(vo);
                    if (lists !=null  && lists.size() > 0) {
                        boxUnit = Integer.parseInt(lists.get(0).getBox_unit());
                    }
                    updateNum *= boxUnit;
                    vo.setUpper_num(String.valueOf(updateNum));
                    int i = infoMapper.lowerAreaNum(vo);
                    if (i > 0) {
                        System.out.println("票区更新成功");
                        return true;
                    } else {
                        return false;
                    }
                } else {
                    return false;
                }
            } else {
                List<TicketStorageChestDefManage> lists = infoMapper.getChestMaxNum(vo);
                System.out.println(flag);
                if (lists !=null  && lists.size() > 0) {
                    updateNum = Integer.parseInt(lists.get(0).getMax_box_num());
                    lists = infoMapper.getBoxUnit(vo);
                    if (lists !=null  && lists.size() > 0) {
                        boxUnit = Integer.parseInt(lists.get(0).getBox_unit());
                    }
                    updateNum *= boxUnit;
                    vo.setUpper_num(String.valueOf(updateNum));
                    int i = infoMapper.upperAreaNum(vo);
                    if (i > 0) {
                        System.out.println("票区更新成功");
                        return true;
                    } else {
                        return false;
                    }
                } else {
                    return false;
                }
            }
        } catch (Exception e) {
            logger.error("票柜定义,票柜删除，修改票区上限(向下更新)异常:", e);
            throw new RuntimeException("票柜定义,票柜删除，修改票区上限(向下更新)异常");
        }
    }

    private int deleteStorey(TicketStorageChestDefManage info, int status) throws Exception{
        int n = 0 ;
        n = infoMapper.deleteCodeStorey(info);
        if (n != 0) {
            if (status == 0) {
                return this.deleteChest(info);
            }
            return n;
        } else {
            return n;
        }
    }

    private int deleteChest(TicketStorageChestDefManage info) throws Exception{
        return infoMapper.deleteCodeChest(info);
    }

    private void clearProductInPlaceStatus(TicketStorageChestDefManage vo, String inplace, int cardmoney) throws Exception{
        boolean result = false;
        int count = 0;
        String nextPlace = null;
        Vector vectorList = new Vector();
        try {
            vo.setPut_place(inplace);
            List<TicketStorageChestDefManage> lists = infoMapper.getCheckPlace(vo);
            if (lists != null && lists.size() >0) {
                count = Integer.parseInt(lists.get(0).getPut_place());
                if (count > 0) {
                    String state = vo.getIc_main_type().trim() + vo.getIc_sub_type().trim() + vo.getCard_money().trim();
                    lists = infoMapper.getStatus(vo);
                    String change = "";
                    if (lists != null && lists.size() >0) {
                        change = lists.get(0).getIc_main_type();
                    }
                    System.out.println("当前状态值" + change + "修改票柜后的值" + state);
                    if (state.equals(change)) {
                        return;
                    }
                    vo.setPut_place(inplace);
                    vo.setCard_money(String.valueOf(cardmoney));
                    lists = infoMapper.getPutPlace(vo);
                    if (lists != null && lists.size() >0) {
                        nextPlace = lists.get(0).getPut_place();
                        if (nextPlace != null && !nextPlace.equals("")) {
                            System.out.println("更新入库最后存放位置为:" + inplace);
                            nextPlace = vo.getStorage_id() + vo.getArea_id() + nextPlace;
                            vo.setNext_place(nextPlace);
                            infoMapper.updateNextPlace(vo);
                        } else {
                            infoMapper.deletePutPlace(vo);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
        }
        
    }
}
