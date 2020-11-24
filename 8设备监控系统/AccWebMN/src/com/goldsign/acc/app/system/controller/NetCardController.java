/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.system.controller;

import com.goldsign.acc.app.system.entity.NetCard;
import com.goldsign.acc.app.system.mapper.NetCardMapper;
import com.goldsign.acc.frame.constant.CommandConstant;

import com.goldsign.acc.frame.controller.BaseController;
import com.goldsign.acc.frame.entity.PubFlag;

import com.goldsign.acc.frame.mapper.OperationLogMapper;
import com.goldsign.acc.frame.util.DBUtil;
import com.goldsign.acc.frame.util.ExpUtil;


import com.goldsign.acc.frame.util.LogUtil;
import com.goldsign.acc.frame.util.PageControlUtil;

import com.goldsign.acc.frame.vo.OperationResult;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.TransactionStatus;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 *  服务器网卡 
 *
 * @author luck
 */
@Controller
public class NetCardController extends BaseController {

    @Autowired
    private NetCardMapper ncMapper;

    @RequestMapping(value = "/netCard")
    public ModelAndView service(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView("/jsp/system/netcard.jsp");
        OperationResult opResult = new OperationResult();
        try {
            opResult = this.queryPlan(request, this.ncMapper, this.operationLogMapper);
        } catch (Exception e) {
            e.printStackTrace();
            opResult.addMessage(e.getMessage());
        }
        getResultSetText(opResult.getReturnResultSet(),mv);
        new PageControlUtil().putBuffer(request, opResult.getReturnResultSet());
        this.baseHandler(request, response, mv);
        this.divideResultSet(request, mv, opResult); //结果集分页
        this.SaveOperationResult(mv, opResult); //返回结果集
        return mv;
    }

    private void getResultSetText(List<NetCard> resultSet, ModelAndView mv) {

        List<PubFlag> status = pubFlagMapper.getCodeByType(2);
        for (NetCard sd : resultSet) {
           if (status != null && !status.isEmpty()) {
                sd.setStatusName(DBUtil.getTextByCode(sd.getStatus(), status));
            }
        }
    }

    public OperationResult queryPlan(HttpServletRequest request, NetCardMapper ncMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult or = new OperationResult();
        LogUtil logUtil = new LogUtil();
        List<NetCard> resultSet;
        try {
            resultSet = ncMapper.queryPlan();
            or.setReturnResultSet(resultSet);
            or.addMessage("成功查询" + resultSet.size() + "条记录");
        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + resultSet.size() + "条记录", opLogMapper);
        return or;

    }
    
    public int addStatus(NetCard vo) throws Exception {
        TransactionStatus status = null;
        int n = 0;
        try {
            status = txMgr.getTransaction(this.def);
            n = ncMapper.update(vo);
            if (n != 1) {
                int insertIntoCur = ncMapper.insertIntoCur(vo);
            }
            int insertIntoHis = ncMapper.insertIntoHis(vo);
            txMgr.commit(status);
        } catch (Exception e) {
            if (txMgr != null) {
                txMgr.rollback(status);
            }
            e.printStackTrace();
//            throw e;
        } finally {
            return n;
        }
    }

    @RequestMapping("/NetCardExportAll")
    public void exportExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {
        List results = this.getBufferElements(request);
        String expAllFields = request.getParameter("expAllFields");
        List<Map<String, Object>> queryResults
                = ExpUtil.entityToMap(expAllFields, results);
        ExpUtil.renderExportExcelPath(response,
                ExpUtil.exportExcel(queryResults, request));
    }
}
