/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.basicinfo.controller;


import com.goldsign.acc.app.basicinfo.entity.SamType;
import com.goldsign.acc.app.basicinfo.mapper.SamTypeMapper;
import com.goldsign.acc.frame.constant.CommandConstant;
import com.goldsign.acc.frame.constant.LogConstant;
import com.goldsign.acc.frame.controller.BaseController;
import com.goldsign.acc.frame.controller.SamBaseController;
import com.goldsign.acc.frame.entity.PubFlag;
import com.goldsign.acc.frame.mapper.OperationLogMapper;
import com.goldsign.acc.frame.util.CharUtil;
import com.goldsign.acc.frame.util.DBUtil;
import com.goldsign.acc.frame.util.ExpUtil;
import com.goldsign.acc.frame.util.FormUtil;
import com.goldsign.acc.frame.util.LogUtil;
import com.goldsign.acc.frame.vo.OperationResult;
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
 * @author mh
 */
@Controller
public class SamTypeController  extends SamBaseController{
    @Autowired
    private SamTypeMapper  samTypeMapper;
    
    @RequestMapping(value="/samTypeDefin")
    public  ModelAndView service(HttpServletRequest request,HttpServletResponse response){
        ModelAndView mv = new ModelAndView("/jsp/basicinfo/samType.jsp");
        String command = request.getParameter("command");
        OperationResult opResult = new OperationResult();
        try{
            if(command != null){
                command = command.trim();
                if(command.equals(CommandConstant.COMMAND_ADD)){
                    opResult = this.add(request, this.samTypeMapper, this.operationLogMapper);
                }
                if(command.equals(CommandConstant.COMMAND_DELETE)){
                    opResult = this.delete(request, this.samTypeMapper, this.operationLogMapper);
                }
                if(command.equals(CommandConstant.COMMAND_MODIFY)){
                        opResult = this.modify(request, this.samTypeMapper, this.operationLogMapper);
                }
                if(command.equals(CommandConstant.COMMAND_QUERY)){
                        opResult = this.query(request, this.samTypeMapper, this.operationLogMapper);
                }               
            }
            if (command != null) {
                if (command.equals(CommandConstant.COMMAND_ADD) || command.equals(CommandConstant.COMMAND_DELETE) || command.equals(CommandConstant.COMMAND_MODIFY)) {
                    if (!command.equals(CommandConstant.COMMAND_ADD)) {
                        this.saveQueryControlDefaultValues(request, mv);//保存查询条件
                    }
                    this.queryForOp(request, this.samTypeMapper, this.operationLogMapper, opResult);
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        String[] attrNames = {SAM_TYPE_DESCS};
        this.setPageOptions(attrNames, mv, request, response); //设置页面线路、车站、票卡、仓库等选项值、版本
        this.getResultSetText((List<SamType>) opResult.getReturnResultSet(), mv);
        this.baseHandler(request, response, mv);
        this.divideResultSet(request, mv, opResult);//结果集分页
        this.SaveOperationResult(mv, opResult);
        return mv;
    }

    private OperationResult query(HttpServletRequest request, SamTypeMapper samTypeMapper, OperationLogMapper operationLogMapper) throws Exception{
        OperationResult op = new OperationResult();
        LogUtil logUtil = new LogUtil();
        SamType queryConditon;
        List<SamType> resultSet;
        try{
            queryConditon = this.getQueryConditon(request);
            resultSet = samTypeMapper.querySamType(queryConditon);
            op.setReturnResultSet(resultSet);
            op.addMessage("成功查询" + resultSet.size() + "条记录");
        }catch (Exception e){
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, operationLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + resultSet.size() + "条记录", operationLogMapper);
        return op;
    }
    
    private SamType getQueryConditon(HttpServletRequest request) {
        SamType samTypeDefin = new SamType();
        samTypeDefin.setSam_type_code(FormUtil.getParameter(request, "q_samTypeCode"));
        samTypeDefin.setSam_type_desc(FormUtil.getParameter(request, "q_samTypeDesc"));
        if(samTypeDefin.getSam_type_desc()!=null&&!samTypeDefin.getSam_type_desc().isEmpty())
        {
            samTypeDefin.setSam_type_desc(samTypeMapper.getSamTypeDesc(samTypeDefin.getSam_type_desc()));
        }
        return samTypeDefin;
    }
    
    private OperationResult add(HttpServletRequest request, SamTypeMapper samTypeMapper, OperationLogMapper operationLogMapper) throws Exception {
        OperationResult op = new OperationResult();
        SamType  samTypeDefin = this.getReqAttribute(request, CommandConstant.COMMAND_ADD);
        LogUtil logUtil = new LogUtil();
        int n = 0;
        String preMsg = "类型代码为" + samTypeDefin.getSam_type_code()+ "的";
        if (CharUtil.getDBLenth(samTypeDefin.getSam_type_desc()) > 30) {
            op.setMessage("卡类型名称最大值不能超过30位(中文字符为三位)");
            return op;
        } 
        try {
            if(this.existReCord(samTypeDefin, samTypeMapper)){
                op.addMessage(preMsg + "记录已存在！");
                return op;
            }
            if(this.existReName(samTypeDefin, samTypeMapper)) {
                op.addMessage("添加失败！卡类型名称" + "\""+samTypeDefin.getSam_type_desc() +"\""+ "已经定义!");
                return op;
            }
                n = this.addByTrans(request, samTypeMapper,samTypeDefin);
        } catch  (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_ADD, e, operationLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_ADD, request, preMsg + LogConstant.addSuccessMsg(n), operationLogMapper);
        op.addMessage(LogConstant.addSuccessMsg(n));
        return op;
    }
    
    private SamType getReqAttribute(HttpServletRequest request, String COMMAND_ADD) {
        SamType po = new SamType();
        po.setSam_type_code(FormUtil.getParameter(request, "d_samTypeCode"));
        po.setSam_type_desc(FormUtil.getParameter(request, "d_samTypeDesc"));
        po.setPdu_warn_threshhold(FormUtil.getParameterIntVal(request, "d_pduWarn"));
        po.setEty_warn_threshhold(FormUtil.getParameterIntVal(request, "d_etyWarn"));
        po.setRemark(FormUtil.getParameter(request, "d_remark"));
        return po;
    }

    private boolean existReCord(SamType samTypeDefin, SamTypeMapper samTypeMapper) {
        List<SamType> list = samTypeMapper.querySamTypeById(samTypeDefin);
        if(list.isEmpty()) {
            return false;
        }
        return true;
    }
    
    private boolean existReName(SamType samTypeDefin, SamTypeMapper samTypeMapper) {
        List<SamType> list = samTypeMapper.querySamTypeByName(samTypeDefin);
        if(list.isEmpty()) {
            return false;
        }
        return true;
    }

    private int addByTrans(HttpServletRequest request, SamTypeMapper samTypeMapper, SamType samTypeDefin) {
        TransactionStatus status = null;
        int n = 0;
        try {
            status = txMgr.getTransaction(this.def);
            n = samTypeMapper.addSamType(samTypeDefin);
            txMgr.commit(status);
        } catch (Exception e) {
            if (txMgr != null) {
                txMgr.rollback(status);
            }
        }
        return n;
    }

    private OperationResult delete(HttpServletRequest request, SamTypeMapper samTypeMapper, OperationLogMapper operationLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        SamType prmVo = this.getReqAttribute(request, CommandConstant.COMMAND_DELETE);
        Vector<SamType> pos = this.getReqAttribute(request);
        LogUtil logUtil = new LogUtil();
        int n = 0;
        try {
            n = this.deleteByTrans(request, samTypeMapper, pos, prmVo);
        }catch (Exception e) {
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_DELETE, e, operationLogMapper);
        }
        if (n == -1) {
            rmsg.addMessage("删除失败，该卡类型已被使用！");
            return rmsg;
        }
        logUtil.logOperation(CommandConstant.COMMAND_DELETE, request, LogConstant.delSuccessMsg(n), operationLogMapper);
        rmsg.addMessage(LogConstant.delSuccessMsg(n));
        return rmsg;
    }
    
    private Vector<SamType> getReqAttribute(HttpServletRequest request) {
        SamType po = new SamType();
        String selectIds = request.getParameter("allSelectedIDs");
        Vector<SamType> selectedItems = this.getDeleteIDs(selectIds);
        return selectedItems;
    }
    
    private Vector<SamType> getDeleteIDs(String selectedIds) {
        StringTokenizer st = new StringTokenizer(selectedIds, ";");
        String strIds = null;
        Vector<SamType> list = new Vector();
        SamType po;
        while (st.hasMoreTokens()) {
            strIds = st.nextToken();
            po = this.getSamType(strIds, "#");
            list.add(po);
        }
        return list;
    }
    
    private SamType getSamType(String strIDs, String delim) {
        StringTokenizer st = new StringTokenizer(strIDs, delim);
        String tmp = null;
        int i = 0;
        SamType po = new SamType();
        while (st.hasMoreTokens()) {
            i++;
            tmp = st.nextToken();
            if (i == 1) {
                po.setSam_type_code(tmp);
                continue;
            }
        }
        return po;
    }
    
    private int deleteByTrans(HttpServletRequest request, SamTypeMapper samTypeMapper, Vector<SamType> pos, SamType prmVo) {
        TransactionStatus status = null;
        int n = 0;
        List<SamType> temp = null;
        try {
            status = txMgr.getTransaction(this.def);
            for (SamType po : pos) {
                temp = samTypeMapper.queryIsUsed(po);
                if(temp.size() > 0) {
                    n = -1;
                    break;
                }
            }
            for (SamType po : pos) {
                temp = samTypeMapper.checkInNew(po);
                if(temp.size() > 0){
                    n = -1;
                    break;
                }
            }
            if (n == -1) {
                return n;
            } else {
                for (SamType po : pos) {
                    n += samTypeMapper.deleteSamType(po);
                }
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

    private OperationResult modify(HttpServletRequest request, SamTypeMapper samTypeMapper, OperationLogMapper operationLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        SamType po = this.getReqAttribute(request, CommandConstant.COMMAND_MODIFY);
         LogUtil logUtil = new LogUtil();
        int n = 0;
        String preMsg = "类型代码为" + po.getSam_type_code()+ "的";
        if (CharUtil.getDBLenth(po.getSam_type_desc()) > 30) {
            rmsg.addMessage("卡类型名称最大值不能超过30位(中文字符为三位)");
            return rmsg;
        } 
        try {
            if(this.checkReName(po, samTypeMapper)) {
                rmsg.addMessage("修改失败！卡类型名称" + "\"" + po.getSam_type_desc() + "\"" + "已经定义！");
                return rmsg;
            }
            n = this.modifyByTrans(request, samTypeMapper, po);
        } catch (Exception e) {
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_MODIFY, e, operationLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_MODIFY, request, preMsg + LogConstant.modifySuccessMsg(n), operationLogMapper);
    rmsg.addMessage(LogConstant.modifySuccessMsg(n));
    return rmsg;
    }
    
    private boolean checkReName(SamType po, SamTypeMapper samTypeMapper) {
        List<SamType> list = samTypeMapper.checkIsDefin(po);
        if (list.isEmpty()) {
            return false;
        }
        return true;
    }
    
    private int modifyByTrans(HttpServletRequest request, SamTypeMapper samTypeMapper, SamType po) {
        TransactionStatus status = null;
        int n = 0;
        try {
            status = txMgr.getTransaction(this.def);
            n = samTypeMapper.modifySamType(po);
            txMgr.commit(status);
        } catch (Exception e) {
            if (txMgr != null) {
                txMgr.rollback(status);
            }
            throw e;
        }
        return n;
     }

    private OperationResult queryForOp(HttpServletRequest request, SamTypeMapper samTypeMapper, OperationLogMapper operationLogMapper, OperationResult opResult) throws Exception {
        LogUtil logUtil = new LogUtil();
        SamType vo;
        List<SamType> resultSet;
        try {
            vo = this.getQueryConditionForOp(request);
            resultSet = samTypeMapper.querySamType(vo);
            opResult.setReturnResultSet(resultSet);
        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, operationLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + resultSet.size() + "条记录", operationLogMapper);
        return opResult;
    }   

    private SamType getQueryConditionForOp(HttpServletRequest request) {
        SamType qCon = new SamType();
        HashMap vQueryControlDefaultValues = null;
        String queryControlDefaultValues = request.getParameter("queryControlDefaultValues");
        String command = request.getParameter("command");
        if (FormUtil.isAddOrModifyMode(request)) {//操作处于添加或修改模式
            qCon.setSam_type_code(FormUtil.getParameter(request, "d_samTypeCode"));
        } else {//操作处于非添加模式
            vQueryControlDefaultValues = FormUtil.getQueryControlDefaultValues(queryControlDefaultValues);
        }
        return qCon;
    }

    private void getResultSetText(List<SamType> list, ModelAndView mv)  {
        //卡类型定义名称
        List<PubFlag> samTypeDescs = (List<PubFlag>)mv.getModelMap().get(SamBaseController.SAM_TYPE_DESCS);
        for(SamType st:list){
            if(samTypeDescs!=null && !samTypeDescs.isEmpty()){
                if(st.getSam_type_desc()!=null &&!st.getSam_type_desc().isEmpty())
                {
                    st.setSam_type_desc(DBUtil.getTextByCode(st.getSam_type_code(),samTypeDescs));
                }
            }
        }
    }
    
    @RequestMapping("/SamTypeExportAll")
    public void exportExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {
        List results = this.getBufferElementsForCurClass(request, "com.goldsign.acc.app.basicinfo.entity.SamType");
        String expAllFields = request.getParameter("expAllFields");
        List<Map<String, Object>> queryResults
                = ExpUtil.entityToMap(expAllFields, results);
        ExpUtil.renderExportExcelPath(response,
                ExpUtil.exportExcel(queryResults, request));
    }
}
