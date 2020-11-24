/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.basicinfo.controller;

import com.goldsign.acc.app.basicinfo.entity.TicketStorageBorrowUnitDefManage;
import com.goldsign.acc.app.basicinfo.entity.TicketStorageDistributeCardParm;
import com.goldsign.acc.app.basicinfo.entity.TicketStorageDistributeStationParm;
import com.goldsign.acc.app.basicinfo.mapper.TicketStorageDistributeCardParmMapper;
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
 * @author liudezeng
 */
@Controller
public class TicketStorageDistributeCardParmController extends StorageOutInBaseController{
     @Autowired
    private TicketStorageDistributeCardParmMapper tsdcpMapper;

    @RequestMapping(value = "/ticketStorageDistributeCardParmManage")
    public ModelAndView service(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView("/jsp/basicinfo/ticketStorageDistributeCardParm.jsp");
        String command = request.getParameter("command");
        OperationResult opResult = new OperationResult();
        try {
            if (command != null) {
                command = command.trim();
                if (command.equals(CommandConstant.COMMAND_QUERY)) {
                    opResult = this.queryParm(request, this.tsdcpMapper, this.operationLogMapper);
                }
                if (command.equals(CommandConstant.COMMAND_ADD)) {
                    opResult = this.addParm(request, this.tsdcpMapper,this.operationLogMapper);
                }
                    if (command.equals(CommandConstant.COMMAND_DELETE)) {
                        opResult = this.deleteParm(request, this.tsdcpMapper, this.operationLogMapper);
                    }
                    if (command.equals(CommandConstant.COMMAND_MODIFY)) {
                        opResult = this.modifyParm(request, this.tsdcpMapper, this.operationLogMapper);
                    }
                if (command != null) {
                if (command.equals(CommandConstant.COMMAND_ADD) || command.equals(CommandConstant.COMMAND_DELETE) || command.equals(CommandConstant.COMMAND_MODIFY)
                        ) {
                    this.queryForOp(request, this.tsdcpMapper, this.operationLogMapper, opResult);

                }
            }

            }
        } catch (Exception e) {
            e.printStackTrace();
            opResult.addMessage(e.getMessage());
        }

        String[] attrNames = {IC_CARD_MAIN, IC_CARD_SUB, IC_CARD_MAIN_SUB};
        this.setPageOptions(attrNames, mv, request, response); //设置页面线路、车站、票卡、仓库等选项值、版本
        this.getResultSetText((List<TicketStorageDistributeCardParm>) opResult.getReturnResultSet(), mv);
        this.baseHandler(request, response, mv);
        this.divideResultSet(request, mv, opResult); //结果集分页
        this.SaveOperationResult(mv, opResult); //返回结果集
        return mv;
    }

    private void getResultSetText(List<TicketStorageDistributeCardParm> resultSet, ModelAndView mv) {
        //票卡主类型

        List<PubFlag> icCardMains = (List<PubFlag>) mv.getModelMap().get(StorageOutInBaseController.IC_CARD_MAIN);
        //票卡子类型
        List<PubFlag> icCardSub = (List<PubFlag>) mv.getModelMap().get(StorageOutInBaseController.IC_CARD_SUB);

        for (TicketStorageDistributeCardParm dl : resultSet) {
            if (icCardMains != null && !icCardMains.isEmpty()) {
                dl.setIc_main_type_name(DBUtil.getTextByCode(dl.getIc_main_type(), icCardMains));

            }
            if (icCardSub != null && !icCardSub.isEmpty()) {
                dl.setIc_sub_type_name(DBUtil.getTextByCode(dl.getIc_sub_type(), dl.getIc_main_type(), icCardSub));

            }


        }
    }

    public OperationResult queryParm(HttpServletRequest request, TicketStorageDistributeCardParmMapper tsdcpMapper, OperationLogMapper opLogMapper) throws Exception {
        LogUtil logUtil = new LogUtil();
        TicketStorageDistributeCardParm queryCondition;
        List<TicketStorageDistributeCardParm> resultSet;
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

    public OperationResult addParm(HttpServletRequest request, TicketStorageDistributeCardParmMapper tsdspMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        TicketStorageDistributeCardParm po = this.getReqAttribute(request);
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
    private TicketStorageDistributeCardParm getQueryCondition(HttpServletRequest request, OperationResult or) {
        TicketStorageDistributeCardParm tsdsp = new TicketStorageDistributeCardParm();
        String q_ic_main_type = FormUtil.getParameter(request, "q_ic_main_type");
        String q_ic_sub_type = FormUtil.getParameter(request, "q_ic_sub_type");

        tsdsp.setIc_main_type(q_ic_main_type);
        tsdsp.setIc_sub_type(q_ic_sub_type);

        return tsdsp;
    }

       public TicketStorageDistributeCardParm getReqAttribute(HttpServletRequest request) {
        TicketStorageDistributeCardParm po = new TicketStorageDistributeCardParm();
        po.setIc_main_type(FormUtil.getParameter(request, "d_ic_main_type"));
        po.setIc_sub_type(FormUtil.getParameter(request, "d_ic_sub_type"));
        po.setCircle(FormUtil.getParameter(request, "d_circle"));
        po.setRemark(FormUtil.getParameter(request, "d_remark"));
        

        return po;
    }

      private boolean existRecord(TicketStorageDistributeCardParm po, TicketStorageDistributeCardParmMapper tsdcpMapper, OperationLogMapper opLogMapper) {
          
        List<TicketStorageDistributeCardParm> list = tsdcpMapper.getListForAdd(po);
        if (list.isEmpty()) {
            return false;
        }
        return true;

    }

       private int addByTrans(HttpServletRequest request, TicketStorageDistributeCardParmMapper tsdcpMapper, TicketStorageDistributeCardParm po) throws Exception {
        TransactionStatus status = null;
        int n = 0;
        try {

            status = txMgr.getTransaction(this.def);
            try {
                n = tsdcpMapper.addTicketStorageDistributeCardParm(po);
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


        public OperationResult modifyParm(HttpServletRequest request, TicketStorageDistributeCardParmMapper tsdcpMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        TicketStorageDistributeCardParm po = this.getReqAttribute(request);
        LogUtil logUtil = new LogUtil();
        int n = 0;

            try {
                n = this.modifyByTrans(request, tsdcpMapper,po);
            } catch (Exception e) {
                return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_MODIFY, e, opLogMapper);
            }
            logUtil.logOperation(CommandConstant.COMMAND_MODIFY, request, LogConstant.modifySuccessMsg(n), opLogMapper);

        rmsg.addMessage(LogConstant.modifySuccessMsg(n));

        return rmsg;
    }

        private int modifyByTrans(HttpServletRequest request, TicketStorageDistributeCardParmMapper tsdcpMapper,TicketStorageDistributeCardParm po) throws Exception {
        TransactionStatus status = null;
        int n = 0;
        try {

            status = txMgr.getTransaction(this.def);
            n = tsdcpMapper.modifyTicketStorageDistributeCardParm(po);

            txMgr.commit(status);
        } catch (Exception e) {
            if (txMgr != null) {
                txMgr.rollback(status);
            }
            throw e;
        }
        return n;
    }

        public OperationResult deleteParm(HttpServletRequest request, TicketStorageDistributeCardParmMapper tsdcpMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        Vector<TicketStorageDistributeCardParm> pos = this.getReqAttributeForDelete(request);
        TicketStorageDistributeCardParm prmVo = new TicketStorageDistributeCardParm();
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


       private Vector<TicketStorageDistributeCardParm> getReqAttributeForDelete(HttpServletRequest request) {
        TicketStorageBorrowUnitDefManage po = new TicketStorageBorrowUnitDefManage();
        String selectIds = request.getParameter("allSelectedIDs");
        Vector<TicketStorageDistributeCardParm> selectedItems = this.getDeleteIDs(selectIds);
        return selectedItems;
    }

        private int deleteByTrans(HttpServletRequest request, TicketStorageDistributeCardParmMapper tsdcpMapper, Vector<TicketStorageDistributeCardParm> pos, TicketStorageDistributeCardParm prmVo) throws Exception {

        TransactionStatus status = null;
        int n = 0;
        try {
            status = txMgr.getTransaction(this.def);
            for (TicketStorageDistributeCardParm po : pos) {
                n += tsdcpMapper.deleteTicketStorageDistributeCardParm(po);
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

         private Vector<TicketStorageDistributeCardParm> getDeleteIDs(String selectedIds) {
        StringTokenizer st = new StringTokenizer(selectedIds, ";");
        String strIds = null;
        Vector<TicketStorageDistributeCardParm> sds = new Vector();
        TicketStorageDistributeCardParm sd;
        while (st.hasMoreTokens()) {
            strIds = st.nextToken();
            sd = this.getTicketStorageDistributeCardParm(strIds, "#");
            sds.add(sd);
        }
        return sds;

    }

      private TicketStorageDistributeCardParm getTicketStorageDistributeCardParm(String strIDs, String delim) {
        StringTokenizer st = new StringTokenizer(strIDs, delim);
        String tmp = null;
        int i = 0;
        TicketStorageDistributeCardParm sd = new TicketStorageDistributeCardParm();
        Vector<TicketStorageDistributeCardParm> sds = new Vector();
        while (st.hasMoreTokens()) {
            i++;
            tmp = st.nextToken();
            if (i == 1) {
                sd.setIc_main_type(tmp);
                continue;
            }
            if (i == 2) {
                sd.setIc_sub_type(tmp);
                continue;
            }
            if (i == 3) {
                sd.setCircle(tmp);
                continue;
            }
            if (i == 4) {
                sd.setRemark(tmp);
                continue;
            }

        }
        return sd;

    }


         public OperationResult queryForOp(HttpServletRequest request, TicketStorageDistributeCardParmMapper tsdcpMapper, OperationLogMapper opLogMapper,OperationResult opResult) throws Exception {
        LogUtil logUtil = new LogUtil();
        TicketStorageDistributeCardParm queryCondition;
        List<TicketStorageDistributeCardParm> resultSet;

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

    private TicketStorageDistributeCardParm getQueryConditionForOp(HttpServletRequest request) {
        TicketStorageDistributeCardParm qCon = new TicketStorageDistributeCardParm();
        HashMap vQueryControlDefaultValues = null;
        String queryControlDefaultValues = request.getParameter("queryControlDefaultValues");
        String command = request.getParameter("command");
        if (FormUtil.isAddOrModifyMode(request)) {//操作处于添加或修改模式
            if (command.equals(CommandConstant.COMMAND_DELETE)) {
                return null;
            }
            qCon.setIc_main_type(FormUtil.getParameter(request, "d_ic_main_type"));
            qCon.setIc_sub_type(FormUtil.getParameter(request, "d_ic_sub_type"));
            qCon.setCircle(FormUtil.getParameter(request, "d_circle"));
            qCon.setRemark(FormUtil.getParameter(request, "d_remark"));

        } else {//操作处于非添加模式
            vQueryControlDefaultValues = FormUtil.getQueryControlDefaultValues(queryControlDefaultValues);
            if (!vQueryControlDefaultValues.isEmpty()) {
                qCon.setIc_main_type(FormUtil.getQueryControlDefaultValue(vQueryControlDefaultValues, "q_ic_main_type"));
                qCon.setIc_sub_type(FormUtil.getQueryControlDefaultValue(vQueryControlDefaultValues, "q_ic_sub_type"));

            }
        }


        return qCon;
    }
    
         @RequestMapping("/TicketStorageDistributeCardParmManageExportAll")
    public void exportExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	
        List results = this.getBufferElementsForCurClass(request,"com.goldsign.acc.app.basicinfo.entity.TicketStorageDistributeCardParm");
        List<Map<String,String>> queryResults = this.entityToMap(results);
        ExpUtil.renderExportExcelPath(response, ExpUtil.exportExcel(queryResults, request));
    }

    private List<Map<String, String>> entityToMap(List results) {
        List<Map<String,String>> list =  new ArrayList<>();
        int row = results.size();
        for(int n=0;n<row ;n++){
            TicketStorageDistributeCardParm vo = (TicketStorageDistributeCardParm)results.get(n);
            Map<String,String> map = new HashMap<>();
            map.put("ic_main_type_name", vo.getIc_main_type_name());
            map.put("ic_sub_type_name", vo.getIc_sub_type_name());
            map.put("circle", vo.getCircle());
            map.put("remark", vo.getRemark());

           
            list.add(map);
        }
        return list;
    }

}

