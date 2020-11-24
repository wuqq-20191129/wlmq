package com.goldsign.acc.app.system.controller;

import com.goldsign.acc.app.system.entity.Disk;
import com.goldsign.acc.app.system.mapper.DiskMapper;
import com.goldsign.acc.frame.constant.CommandConstant;
import com.goldsign.acc.frame.controller.BaseController;
import com.goldsign.acc.frame.entity.PubFlag;
import com.goldsign.acc.frame.mapper.OperationLogMapper;
import com.goldsign.acc.frame.util.DBUtil;
import com.goldsign.acc.frame.util.ExpUtil;
import com.goldsign.acc.frame.util.LogUtil;
import com.goldsign.acc.frame.vo.OperationResult;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.TransactionStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 *  服务器硬盘
 *
 * @author xiaowu
 */
@Controller
public class DiskController extends BaseController {

    @Autowired
    private DiskMapper mapper;

    @RequestMapping(value = "/disk")
    public ModelAndView service(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView("/jsp/system/disk.jsp");
        OperationResult opResult = new OperationResult();
        try {
            opResult = this.queryPlan(request, this.mapper, this.operationLogMapper);
        } catch (Exception e) {
            e.printStackTrace();
            opResult.addMessage(e.getMessage());
        }
        getResultSetText(opResult.getReturnResultSet(),mv);
        this.baseHandler(request, response, mv);
        this.divideResultSet(request, mv, opResult); //结果集分页
        this.SaveOperationResult(mv, opResult); //返回结果集
        return mv;
    }
    
    @RequestMapping("/diskExportAll")
    public void exportExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {
        List results = this.getBufferElements(request);
        String expAllFields = request.getParameter("expAllFields");
        List<Map<String, Object>> queryResults = ExpUtil.entityToMap(expAllFields, results);
        ExpUtil.renderExportExcelPath(response, ExpUtil.exportExcel(queryResults, request));
    }

    private void getResultSetText(List<Disk> resultSet, ModelAndView mv) {
        List<PubFlag> status = pubFlagMapper.getCodeByType(2);
        for (Disk sd : resultSet) {
           if (status != null && !status.isEmpty()) {
                sd.setStatus_text(DBUtil.getTextByCode(sd.getStatus(), status));
            }
        }
    }

    public OperationResult queryPlan(HttpServletRequest request, DiskMapper mapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult or = new OperationResult();
        LogUtil logUtil = new LogUtil();
        List<Disk> resultSet;
        try {
            resultSet = mapper.queryPlan();
            or.setReturnResultSet(resultSet);
            or.addMessage("成功查询" + resultSet.size() + "条记录");
        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + resultSet.size() + "条记录", opLogMapper);
        return or;
    }
    
    public void addStatus(Disk vo) {
        TransactionStatus status = null;
        try {
            status = txMgr.getTransaction(this.def);
            int n = mapper.updatePlan(vo);
            if (n != 1) {
                mapper.insertPlanOne(vo);
            }
            mapper.insertPlanTwo(vo);
            txMgr.commit(status);
        } catch (TransactionException e) {
            if (txMgr != null) {
                txMgr.rollback(status);
            }
            e.printStackTrace();
        }
    }
    
    public void updateMenuStatus(Map map){
        mapper.updateMenuStatus(map);
    }
}
