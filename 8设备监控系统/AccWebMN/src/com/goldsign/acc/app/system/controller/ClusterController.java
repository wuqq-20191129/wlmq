/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.system.controller;

import com.goldsign.acc.app.system.entity.Cluster;
import com.goldsign.acc.app.system.mapper.ClusterMapper;
import com.goldsign.acc.frame.constant.CommandConstant;

import com.goldsign.acc.frame.controller.BaseController;
import com.goldsign.acc.frame.entity.PubFlag;

import com.goldsign.acc.frame.mapper.OperationLogMapper;
import com.goldsign.acc.frame.util.DBUtil;
import com.goldsign.acc.frame.util.ExpUtil;

import com.goldsign.acc.frame.util.LogUtil;
import com.goldsign.acc.frame.util.PageControlUtil;
import com.goldsign.acc.frame.util.PubUtil;

import com.goldsign.acc.frame.vo.OperationResult;
import java.util.ArrayList;

import java.util.List;
import java.util.Map;

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
 * 集群
 *
 * @author luck
 */
@Controller
public class ClusterController extends BaseController {

    @Autowired
    protected ClusterMapper cMapper;

    @RequestMapping(value = "/cluster")
    public ModelAndView service(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView("/jsp/system/cluster.jsp");
        OperationResult opResult = new OperationResult();
        try {
            opResult = this.queryPlan(request, this.cMapper, this.operationLogMapper);
        } catch (Exception e) {
            e.printStackTrace();
            opResult.addMessage(e.getMessage());
        }
        getResultSetText(opResult.getReturnResultSet(), mv);
         new PageControlUtil().putBuffer(request, opResult.getReturnResultSet());
        this.baseHandler(request, response, mv);
        this.divideResultSet(request, mv, opResult); //结果集分页
        this.SaveOperationResult(mv, opResult); //返回结果集

        return mv;
    }

    private void getResultSetText(List<Cluster> resultSet, ModelAndView mv) {
        List<PubFlag> status = pubFlagMapper.getCodeByType(2);
        for (Cluster sd : resultSet) {
            if (status != null && !status.isEmpty()) {
                sd.setStatusName(DBUtil.getTextByCode(sd.getStatus(), status));
            }
        }
    }

    public OperationResult queryPlan(HttpServletRequest request, ClusterMapper cMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult or = new OperationResult();
        LogUtil logUtil = new LogUtil();
        List<Cluster> resultSet;
        try {
            resultSet = cMapper.queryPlan();
            or.setReturnResultSet(resultSet);
            or.addMessage("成功查询" + resultSet.size() + "条记录");
        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + resultSet.size() + "条记录", opLogMapper);
        return or;

    }

    protected int addStatus(Cluster vo) throws Exception {
        TransactionStatus status = null;
        int n = 0;
        try {
            status = txMgr.getTransaction(this.def);
            n = cMapper.update(vo);
            if (n != 1) {
                int insertIntoCur = cMapper.insertIntoCur(vo);
            }
            int insertIntoHis = cMapper.insertIntoHis(vo);
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

    public void addStatuses(Vector vos) throws Exception {
        Cluster vo;
        try {
            for (int i = 0; i < vos.size(); i++) {
                vo = (Cluster) vos.get(i);
                if (!vo.getNodeName().equals("")) {
                    //System.out.println(vo.getNodeName());
                    this.addStatus(vo);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("/ClusterExportAll")
    public void exportExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {
        List results = this.getBufferElements(request);
        String expAllFields = request.getParameter("expAllFields");
        List<Map<String, Object>> queryResults
                = ExpUtil.entityToMap(expAllFields, results);
        ExpUtil.renderExportExcelPath(response,
                ExpUtil.exportExcel(queryResults, request));
    }

}
