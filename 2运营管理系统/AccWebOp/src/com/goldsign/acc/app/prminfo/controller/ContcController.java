/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.prminfo.controller;

import com.goldsign.acc.app.prminfo.entity.Contc;
import com.goldsign.acc.app.prminfo.mapper.ContcMapper;
import com.goldsign.acc.frame.controller.BaseController;
import com.goldsign.acc.frame.constant.CommandConstant;
import com.goldsign.acc.frame.constant.LogConstant;
import com.goldsign.acc.frame.mapper.OperationLogMapper;
import com.goldsign.acc.frame.util.CharUtil;
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
 * @desc:运营商
 * @author:mh
 * @create date: 2017-6-16
 */
@Controller
public class ContcController extends BaseController {

    @Autowired
    private ContcMapper contcMapper;
    
    private int flag;

    @RequestMapping("/Contc")
    public ModelAndView service(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView("/jsp/prminfo/contc.jsp");
        String command = request.getParameter("command");
        OperationResult opResult = new OperationResult();
        flag = 0;
        try {
            if (command != null) {
                command = command.trim();
                if (command.equals(CommandConstant.COMMAND_MODIFY))//修改操作
                {
                    opResult = this.modify(request, this.contcMapper, this.operationLogMapper);
                }
                if (command.equals(CommandConstant.COMMAND_DELETE))//删除操作
                {
                    opResult = this.delete(request, this.contcMapper, this.operationLogMapper);
                }
                if (command.equals(CommandConstant.COMMAND_ADD))//增加操作
                {
                    opResult = this.add(request, this.contcMapper, this.operationLogMapper);
                }
                if (command.equals(CommandConstant.COMMAND_QUERY))//查询操作
                {
                    opResult = this.query(request, this.contcMapper, this.operationLogMapper);
                }
            }

            if (command != null) {
                if (command.equals(CommandConstant.COMMAND_ADD) || command.equals(CommandConstant.COMMAND_DELETE) || command.equals(CommandConstant.COMMAND_MODIFY)) 
                {
                    if (!command.equals(CommandConstant.COMMAND_ADD)) {
                        this.saveQueryControlDefaultValues(request, mv);//保存查询条件
                    }
                    this.queryForOp(request, this.contcMapper, this.operationLogMapper, opResult);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.baseHandler(request, response, mv);
        this.divideResultSet(request, mv, opResult);//结果集分页
        this.SaveOperationResult(mv, opResult);

        return mv;
    }

    private OperationResult query(HttpServletRequest request, ContcMapper contcMapper, OperationLogMapper operationLogMapper) throws Exception {
        OperationResult or = new OperationResult();
        LogUtil logUtil = new LogUtil();
        Contc queryCondition;
        List<Contc> resultSet;
        try {
            queryCondition = this.getQueryCondition(request);
            resultSet = contcMapper.getContc(queryCondition);
            or.setReturnResultSet(resultSet);
            or.addMessage("成功查询" + resultSet.size() + "条记录");
        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, operationLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + resultSet.size() + "条记录", operationLogMapper);
        return or;
    }

    public OperationResult queryForOp(HttpServletRequest request, ContcMapper contcMapper, OperationLogMapper opLogMapper, OperationResult opResult) throws Exception {
        LogUtil logUtil = new LogUtil();
        Contc vo;
        List<Contc> resultSet;
        try {
            vo = this.getQueryConditionForOp(request);
            if(flag==1){
                resultSet = contcMapper.getContcById(vo);
            }else if(flag==2){
                resultSet = contcMapper.getContcByName(vo);
            }else if(flag==3){
                resultSet = contcMapper.getContcBySequence(vo);
            }else{
                resultSet = contcMapper.getContc(vo);
            }
            
            opResult.setReturnResultSet(resultSet);
        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + resultSet.size() + "条记录", opLogMapper);
        return opResult;

    }

    private Contc getQueryCondition(HttpServletRequest request) {
        Contc qCon = new Contc();
        qCon.setRecord_flag("0");
        return qCon;
    }

    private Contc getReqAttribute(HttpServletRequest request, String type) {
        Contc po = new Contc();
        po.setContc_id(FormUtil.getParameter(request, "d_contcId"));
        po.setContc_name(FormUtil.getParameter(request, "d_contcName"));
        po.setLink_man(FormUtil.getParameter(request, "d_linkMan"));
        po.setTel(FormUtil.getParameter(request, "d_tel"));
        po.setFax(FormUtil.getParameter(request, "d_fax"));
        po.setSequence(FormUtil.getParameter(request, "d_sequence"));
        po.setRecord_flag("0");
        return po;
    }

    public OperationResult modify(HttpServletRequest request, ContcMapper contcMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        Contc po = this.getReqAttribute(request, CommandConstant.COMMAND_MODIFY);
        LogUtil logUtil = new LogUtil();
        int n = 0;

        String preMsg = "运营商ID为" + po.getContc_id() + "的";
        if (CharUtil.getDBLenth(po.getContc_name()) > 75) {
            rmsg.addMessage("运营商名称最大值不能超过75位(中文字符为两位)");
            return rmsg;
        }else if (this.existName(po, contcMapper)) {
            preMsg = "运营商名称为" + po.getContc_name() + "的";
            rmsg.addMessage(preMsg + "记录已存在！");
            flag=2;
            return rmsg;
        }else if(this.existSequence(po, contcMapper)){//校验序号是否已存在
            preMsg = "运营商序号为" + po.getSequence() + "的";
            rmsg.addMessage(preMsg+"记录已存在");
            flag=3;
            return rmsg;
        }else {
            try {
                n = this.modifyByTrans(request, contcMapper, po);
            } catch (Exception e) {
                return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_MODIFY, e, opLogMapper);
            }
            logUtil.logOperation(CommandConstant.COMMAND_MODIFY, request, preMsg + LogConstant.modifySuccessMsg(n), opLogMapper);
        }
        rmsg.addMessage(LogConstant.modifySuccessMsg(n));

        return rmsg;
    }

    private Contc getQueryConditionForOp(HttpServletRequest request) {

        Contc qCon = new Contc();
        HashMap vQueryControlDefaultValues = null;
        String queryControlDefaultValues = request.getParameter("queryControlDefaultValues");
        if (FormUtil.isAddOrModifyMode(request)) {//操作处于添加或修改模式
            qCon.setContc_id(FormUtil.getParameter(request, "d_contcId"));
            qCon.setContc_name(FormUtil.getParameter(request, "d_contcName"));
            qCon.setSequence(FormUtil.getParameter(request, "d_sequence"));
            qCon.setRecord_flag("0");
        } else {//操作处于非添加模式
            vQueryControlDefaultValues = FormUtil.getQueryControlDefaultValues(queryControlDefaultValues);
        }
        return qCon;
    }

    private int modifyByTrans(HttpServletRequest request, ContcMapper contcMapper, Contc po) throws Exception {
        TransactionStatus status = null;
        int n = 0;
        try {
            status = txMgr.getTransaction(this.def);
            n = contcMapper.modifyContc(po);
            txMgr.commit(status);
        } catch (Exception e) {
            if (txMgr != null) {
                txMgr.rollback(status);
            }
            throw e;
        }
        return n;
    }

    private OperationResult delete(HttpServletRequest request, ContcMapper contcMapper, OperationLogMapper operationLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        Contc prmVo = this.getReqAttribute(request, CommandConstant.COMMAND_DELETE);
        Vector<Contc> pos = this.getReqAttributeForDelete(request);
        LogUtil logUtil = new LogUtil();
        int n = 0;
        try {
            n = this.deleteByTrans(request, contcMapper, pos, prmVo);
        } catch (Exception e) {
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_DELETE, e, operationLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_DELETE, request, LogConstant.delSuccessMsg(n), operationLogMapper);

        rmsg.addMessage(LogConstant.delSuccessMsg(n));

        return rmsg;
    }

    private Vector<Contc> getReqAttributeForDelete(HttpServletRequest request) {
        Contc po = new Contc();
        String selectIds = request.getParameter("allSelectedIDs");
        Vector<Contc> selectedItems = this.getDeleteIDs(selectIds);
        return selectedItems;
    }

    private Vector<Contc> getDeleteIDs(String selectedIds) {
        StringTokenizer st = new StringTokenizer(selectedIds, ";");
        String strIds = null;
        Vector<Contc> list = new Vector();
        Contc po;
        while (st.hasMoreTokens()) {
            strIds = st.nextToken();
            po = this.getContcs(strIds, "#");
            list.add(po);
        }
        return list;
    }

    private Contc getContcs(String strIDs, String delim) {
        StringTokenizer st = new StringTokenizer(strIDs, delim);
        String tmp = null;
        int i = 0;
        Contc po = new Contc();

        while (st.hasMoreTokens()) {
            i++;
            tmp = st.nextToken();
            if (i == 1) {
                po.setContc_id(tmp);
                continue;
            }
            if (i == 2) {
                po.setRecord_flag(tmp);
                continue;
            }

        }
        return po;
    }

    private int deleteByTrans(HttpServletRequest request, ContcMapper contcMapper, Vector<Contc> pos, Contc prmVo) {
        TransactionStatus status = null;
        int n = 0;
        try {
            status = txMgr.getTransaction(this.def);
            for (Contc po : pos) {
                n += contcMapper.deleteContc(po);
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

    private OperationResult add(HttpServletRequest request, ContcMapper contcMapper, OperationLogMapper operationLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        Contc po = this.getReqAttribute(request, CommandConstant.COMMAND_ADD);
        LogUtil logUtil = new LogUtil();
        int n = 0;

        String preMsg = "运营商ID为" + po.getContc_id() + "的";
        if (CharUtil.getDBLenth(po.getContc_name()) > 75) {
            rmsg.addMessage("运营商名称最大值不能超过75位(中文字符为两位)");
            return rmsg;
        } else {
            try {
                if (this.existRecord(po, contcMapper)) {
                    rmsg.addMessage(preMsg + "记录已存在！");
                    flag=1;
                    return rmsg;
                } else {
                    preMsg = "运营商名称为" + po.getContc_name() + "的";
                    if (this.existName(po, contcMapper)) {
                        rmsg.addMessage(preMsg + "记录已存在！");
                        flag=2;
                        return rmsg;
                    }
                }
                if(this.existSequence(po, contcMapper)){//校验序号是否已存在
                    preMsg = "运营商序号为" + po.getSequence() + "的";
                    rmsg.addMessage(preMsg+"记录已存在");
                    flag=3;
                    return rmsg;
                }

                n = this.addByTrans(request, contcMapper, po);

            } catch (Exception e) {
                return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_ADD, e, operationLogMapper);
            }
            logUtil.logOperation(CommandConstant.COMMAND_ADD, request, preMsg + LogConstant.addSuccessMsg(n), operationLogMapper);
        }
        rmsg.addMessage(LogConstant.addSuccessMsg(n));

        return rmsg;
    }

    private boolean existRecord(Contc vo, ContcMapper contcMapper) {
        List<Contc> list = contcMapper.getContcById(vo);
        if (list.isEmpty()) {
            return false;
        }
        return true;
    }

    private boolean existName(Contc vo, ContcMapper contcMapper) {
        List<Contc> list = contcMapper.getContcByName(vo);
        if (list.isEmpty()) {
            return false;
        }
        return true;
    }
    
    private boolean existSequence(Contc vo, ContcMapper contcMapper) {
        List<Contc> list = contcMapper.getContcBySequence(vo);
        if (list.isEmpty()) {
            return false;
        }
        return true;
    }

    private int addByTrans(HttpServletRequest request, ContcMapper contcMapper, Contc vo) {
        TransactionStatus status = null;
        int n = 0;
        try {
            status = txMgr.getTransaction(this.def);
            n = contcMapper.addContc(vo);
            txMgr.commit(status);
        } catch (Exception e) {
            if (txMgr != null) {
                txMgr.rollback(status);
            }
            throw e;
        }
        return n;
    }
    
    @RequestMapping("/ContcExportAll")
    public void exportExcel(HttpServletRequest request,HttpServletResponse response) throws Exception{
        List results = this.getBufferElementsForCurClass(request, "com.goldsign.acc.app.prminfo.entity.Contc");
        List<Map<String,String>> queryResults = this.entityToMap(results);
        ExpUtil.renderExportExcelPath(response, ExpUtil.exportExcel(queryResults, request));
    }
    
    private List<Map<String,String>> entityToMap(List results){
        List<Map<String,String>> list =  new ArrayList<>();
        int row = results.size();
        for(int n=0;n<row ;n++){
            Contc vo = (Contc)results.get(n);
            Map<String,String> map = new HashMap<>();
            map.put("contc_id", vo.getContc_id());
            map.put("contc_name", vo.getContc_name());
            map.put("link_man", vo.getLink_man());
            map.put("tel", vo.getTel());
            map.put("fax", vo.getFax());
            map.put("sequence", vo.getSequence());
            list.add(map);
        }
        return list;
    }

}
