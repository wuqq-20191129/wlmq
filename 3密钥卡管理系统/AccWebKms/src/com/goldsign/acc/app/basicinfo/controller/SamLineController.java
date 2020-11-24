/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.basicinfo.controller;


import com.goldsign.acc.app.basicinfo.entity.SamLine;
import com.goldsign.acc.app.basicinfo.mapper.SamLineMapper;
import com.goldsign.acc.frame.constant.CommandConstant;
import com.goldsign.acc.frame.constant.LogConstant;
import com.goldsign.acc.frame.constant.SamCardConstant;
import com.goldsign.acc.frame.controller.SamBaseController;
import com.goldsign.acc.frame.mapper.OperationLogMapper;
import com.goldsign.acc.frame.util.CharUtil;
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
public class SamLineController  extends SamBaseController{
    @Autowired
    SamLineMapper samLineMapper;
    
    @RequestMapping(value="/samLineDefin")
    public  ModelAndView service(HttpServletRequest request,HttpServletResponse response){
        ModelAndView mv = new ModelAndView("/jsp/basicinfo/samLine.jsp");
        String command = request.getParameter("command");
        OperationResult opResult = new OperationResult();
        try{
            if(command != null){
                command = command.trim();
                if(command.equals(CommandConstant.COMMAND_ADD)){
                    opResult = this.add(request, this.samLineMapper, this.operationLogMapper);
                }
                if(command.equals(CommandConstant.COMMAND_DELETE)){
                    opResult = this.delete(request, this.samLineMapper, this.operationLogMapper);
                }
                if(command.equals(CommandConstant.COMMAND_MODIFY)){
                        opResult = this.modify(request, this.samLineMapper, this.operationLogMapper);
                }
                if(command.equals(CommandConstant.COMMAND_QUERY)){
                        opResult = this.query(request, this.samLineMapper, this.operationLogMapper);
                }               
            }
            if (command != null) {
                if (command.equals(CommandConstant.COMMAND_ADD) || command.equals(CommandConstant.COMMAND_DELETE) || command.equals(CommandConstant.COMMAND_MODIFY)) {
                    if (!command.equals(CommandConstant.COMMAND_ADD)) {
                        this.saveQueryControlDefaultValues(request, mv);//保存查询条件
                    }
                    this.queryForOp(request, this.samLineMapper, this.operationLogMapper, opResult);
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        this.SaveOperationResult(mv, opResult);
        //显示下拉内容
        String[] attrNames = this.getReqAttributeNames(request, command);
        this.setPageOptions(attrNames, mv, request, response);
        this.baseHandler(request, response, mv);
        this.divideResultSet(request, mv, opResult);//结果集分页
        return mv;
    }

    private OperationResult query(HttpServletRequest request, SamLineMapper samLineMapper, OperationLogMapper operationLogMapper) throws Exception{
        OperationResult op = new OperationResult();
        LogUtil logUtil = new LogUtil();
        SamLine queryConditon;
        List<SamLine> resultSet;
        try{
            queryConditon = this.getQueryConditon(request);
            resultSet = samLineMapper.querySamLine(queryConditon);
            op.setReturnResultSet(resultSet);
            op.addMessage("成功查询" + resultSet.size() + "条记录");
        }catch (Exception e){
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, operationLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + resultSet.size() + "条记录", operationLogMapper);
        return op;
    }
    
    private SamLine getQueryConditon(HttpServletRequest request) {
        SamLine samLineDefin = new SamLine();
        samLineDefin.setLine_es_code(FormUtil.getParameter(request, "q_lineCode"));
        samLineDefin.setLine_es_desc(FormUtil.getParameter(request, "q_lineDesc"));
        samLineDefin.setRecode_flag_es_line(SamCardConstant.RECORD_FLAG_ES_LINE);
        return samLineDefin;
    }
    
    private OperationResult add(HttpServletRequest request, SamLineMapper samLineMapper, OperationLogMapper operationLogMapper) throws Exception {
        OperationResult op = new OperationResult();
        SamLine  samLineDefin = this.getReqAttribute(request, CommandConstant.COMMAND_ADD);
        LogUtil logUtil = new LogUtil();
        int n = 0;
        String preMsg = "线路代码为" + samLineDefin.getLine_es_code()+ "的";
        if (CharUtil.getDBLenth(samLineDefin.getLine_es_desc()) > 50) {
            op.setMessage("线路名称最大值不能超过50位(中文字符为三位)");
            return op;
        }
        try {
            if(this.existReCord(samLineDefin, samLineMapper)){
                op.addMessage(preMsg + "记录已存在！");
                return op;
            }
            if(this.existReName(samLineDefin, samLineMapper)){
                op.addMessage("添加失败！线路名称" + "\""+ samLineDefin.getLine_es_desc()+ "\"" + "已经定义!");
                return op;
            }
            n = this.addByTrans(request, samLineMapper,samLineDefin);
        } catch  (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_ADD, e, operationLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_ADD, request, preMsg + LogConstant.addSuccessMsg(n), operationLogMapper);
        op.addMessage(LogConstant.addSuccessMsg(n));
        return op;
    }
    
    private SamLine getReqAttribute(HttpServletRequest request, String COMMAND_ADD) {
        SamLine po = new SamLine();
        po.setLine_es_code(FormUtil.getParameter(request, "d_lineCode"));
        po.setLine_es_desc(FormUtil.getParameter(request, "d_lineDesc"));
        //po.setType(FormUtil.getParameter(request, "d_codeText"));
        String codeText = FormUtil.getParameter(request, "d_codeText");
        String type = codeText.substring(0,2);
        String text = codeText.substring(3);
        po.setType(type);
        po.setCode_text(text);
        po.setRemark(FormUtil.getParameter(request, "d_remark"));
        po.setRecode_flag_es_line(SamCardConstant.RECORD_FLAG_ES_LINE);
        return po;
    }

    private boolean existReCord(SamLine samLineDefin, SamLineMapper samLineMapper) {
        List<SamLine> list = samLineMapper.querySamLineById(samLineDefin);
        if(list.isEmpty()) {
            return false;
        }
        return true;
    }
    
    private boolean existReName(SamLine samLineDefin, SamLineMapper samLineMapper) {
        List<SamLine> list = samLineMapper.querySamLineByName(samLineDefin);
        if(list.isEmpty()){
            return false;
        }
        return true;
    }

    private int addByTrans(HttpServletRequest request, SamLineMapper samLineMapper, SamLine samLineDefin) {
        TransactionStatus status = null;
        int n = 0;
        try {
            status = txMgr.getTransaction(this.def);
            n = samLineMapper.addSamLine(samLineDefin);
            txMgr.commit(status);
        } catch (Exception e) {
            if (txMgr != null) {
                txMgr.rollback(status);
            }
        }
        return n;
    }

    private OperationResult delete(HttpServletRequest request, SamLineMapper samLineMapper, OperationLogMapper operationLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        SamLine prmVo = this.getReqAttributes(request, CommandConstant.COMMAND_DELETE);
        Vector<SamLine> pos = this.getReqAttribute(request);
        LogUtil logUtil = new LogUtil();
        int n = 0;
        try {
            n = this.deleteByTrans(request, samLineMapper, pos, prmVo);
        }catch (Exception e) {
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_DELETE, e, operationLogMapper);
        }
        if (n == -1) {
            rmsg.addMessage("删除失败!该线路已被使用！");
            return rmsg;
        }
        logUtil.logOperation(CommandConstant.COMMAND_DELETE, request, LogConstant.delSuccessMsg(n), operationLogMapper);
        rmsg.addMessage(LogConstant.delSuccessMsg(n));
        return rmsg;
    }
    
    private SamLine getReqAttributes(HttpServletRequest request, String COMMAND_DELETE) {
        SamLine po = new SamLine();
        po.setLine_es_code(FormUtil.getParameter(request, "d_lineCode"));        
        return po;
    }
    
    private Vector<SamLine> getReqAttribute(HttpServletRequest request) {
        SamLine po = new SamLine();
        String selectIds = request.getParameter("allSelectedIDs");
        Vector<SamLine> selectedItems = this.getDeleteIDs(selectIds);
        return selectedItems;
    }
    
    private Vector<SamLine> getDeleteIDs(String selectedIds) {
        StringTokenizer st = new StringTokenizer(selectedIds, ";");
        String strIds = null;
        Vector<SamLine> list = new Vector();
        SamLine po;
        while (st.hasMoreTokens()) {
            strIds = st.nextToken();
            po = this.getSamType(strIds, "#");
            list.add(po);
        }
        return list;
    }
    
    private SamLine getSamType(String strIDs, String delim) {
        StringTokenizer st = new StringTokenizer(strIDs, delim);
        String tmp = null;
        int i = 0;
        SamLine po = new SamLine();
        while (st.hasMoreTokens()) {
            i++;
            tmp = st.nextToken();
            if (i == 1) {
                po.setLine_es_code(tmp);
                continue;
            }
        }
        return po;
    }
    
    private int deleteByTrans(HttpServletRequest request, SamLineMapper samLineMapper, Vector<SamLine> pos, SamLine prmVo) {
        TransactionStatus status = null;
        int n = 0;
        List<SamLine> temp = null;
        try {
            status = txMgr.getTransaction(this.def);
            for (SamLine po : pos) {
                temp = samLineMapper.queryIsUsed(po);
                if (temp.size() > 0) {
                    n = -1;
                    break;
                }
            }
            if (n == -1){
                return n;
            } else {
                for (SamLine po : pos) {
                    n += samLineMapper.deleteSamLine(po);
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

    private OperationResult modify(HttpServletRequest request, SamLineMapper samLineMapper, OperationLogMapper operationLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        SamLine po = this.getReqAttribute(request, CommandConstant.COMMAND_MODIFY);
         LogUtil logUtil = new LogUtil();
        int n = 0;
        String preMsg = "线路代码为" + po.getLine_es_code()+ "的";
        if (CharUtil.getDBLenth(po.getLine_es_desc()) > 50) {
            rmsg.addMessage("线路名称最大值不能超过50位(中文字符为三位)");
            return rmsg;
        } 
        try {  
            if(this.checkReName(po, samLineMapper)) {
                rmsg.addMessage("修改失败！线路名称" + "\""+ po.getLine_es_desc()+ "\"" + "已经定义！");
                return rmsg;
            }
            n = this.modifyByTrans(request, samLineMapper, po);
        } catch (Exception e) {
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_MODIFY, e, operationLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_MODIFY, request, preMsg + LogConstant.modifySuccessMsg(n), operationLogMapper);
    rmsg.addMessage(LogConstant.modifySuccessMsg(n));
    return rmsg;
    }
    
    private boolean checkReName(SamLine po, SamLineMapper samLineMapper) {
        List<SamLine> list = samLineMapper.checkIsDefin(po);
        if (list.isEmpty()) {
            return false;
        }
        return true;
    }
    
    private int modifyByTrans(HttpServletRequest request, SamLineMapper samLineMapper, SamLine po) {
        TransactionStatus status = null;
        int n = 0;
        try {
            status = txMgr.getTransaction(this.def);           
            n = samLineMapper.modifySamLine(po);
            txMgr.commit(status);
        } catch (Exception e) {
            if (txMgr != null) {
                txMgr.rollback(status);
            }
            throw e;
        }
        return n;
     }

    private OperationResult queryForOp(HttpServletRequest request, SamLineMapper samLineMapper, OperationLogMapper operationLogMapper, OperationResult opResult) throws Exception {
        LogUtil logUtil = new LogUtil();
        SamLine vo;
        List<SamLine> resultSet;
        try {
            vo = this.getQueryConditionForOp(request);          
            resultSet = samLineMapper.querySamLine(vo);
            opResult.setReturnResultSet(resultSet);
        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, operationLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + resultSet.size() + "条记录", operationLogMapper);
        return opResult;
    }   

    private SamLine getQueryConditionForOp(HttpServletRequest request) {
        SamLine qCon = new SamLine();
        HashMap vQueryControlDefaultValues = null;
        String queryControlDefaultValues = request.getParameter("queryControlDefaultValues");
        String command = request.getParameter("command");
        if (FormUtil.isAddOrModifyMode(request)) {//操作处于添加或修改模式
            qCon.setLine_es_code(FormUtil.getParameter(request, "d_lineCode"));
//            qCon.setLine_es_desc(FormUtil.getParameter(request, "d_lineDesc"));
//            String codeText = FormUtil.getParameter(request, "d_codeText");
//            String type = codeText.substring(0,2);
//            qCon.setType(type);       
//            qCon.setRemark(FormUtil.getParameter(request, "d_remark"));
            qCon.setRecode_flag_es_line(SamCardConstant.RECORD_FLAG_ES_LINE);
        }  else {//操作处于非添加模式
            if (command.equals(CommandConstant.COMMAND_DELETE)) {
                qCon.setRecode_flag_es_line(SamCardConstant.RECORD_FLAG_ES_LINE);
            }
            vQueryControlDefaultValues = FormUtil.getQueryControlDefaultValues(queryControlDefaultValues);            
        }
        return qCon;
    }
    
    private String[] getReqAttributeNames(HttpServletRequest request, String command) {        
        String[] attrNames = {LineEsTypes};
        return attrNames;
    }

    @RequestMapping("/SamLineExportAll")
    public void exportExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {
        List results = this.getBufferElementsForCurClass(request, "com.goldsign.acc.app.basicinfo.entity.SamLine");
        String expAllFields = request.getParameter("expAllFields");
        List<Map<String, Object>> queryResults
                = ExpUtil.entityToMap(expAllFields, results);
        ExpUtil.renderExportExcelPath(response,
                ExpUtil.exportExcel(queryResults, request));
    }
    
}
