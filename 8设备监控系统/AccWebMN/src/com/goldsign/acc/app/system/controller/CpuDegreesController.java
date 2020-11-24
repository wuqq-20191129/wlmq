package com.goldsign.acc.app.system.controller;

import com.goldsign.acc.app.system.entity.CpuDegrees;
import com.goldsign.acc.app.system.mapper.CpuDegreesMapper;
import com.goldsign.acc.frame.constant.CommandConstant;
import com.goldsign.acc.frame.constant.FrameDBConstant;
import com.goldsign.acc.frame.constant.WebConstant;
import com.goldsign.acc.frame.controller.BaseController;
import com.goldsign.acc.frame.entity.PubFlag;
import com.goldsign.acc.frame.mapper.OperationLogMapper;
import com.goldsign.acc.frame.util.DBUtil;
import com.goldsign.acc.frame.util.LogUtil;
import com.goldsign.acc.frame.vo.OperationResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.TransactionStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Description:
 *
 * @author: zhongziqi
 * @Date: 2018-08-14
 * @Time: 15:34
 */
@Controller
public class CpuDegreesController extends BaseController {
    @Autowired
    protected CpuDegreesMapper cpuDegreesMapper;

    @RequestMapping(value = "/cpuDegrees")
    public ModelAndView service(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView("/jsp/system/cpu_degrees.jsp");
        OperationResult opResult = new OperationResult();
        try {
            opResult = this.queryPlan(request, this.cpuDegreesMapper, this.operationLogMapper);
        } catch (Exception e) {
            e.printStackTrace();
            opResult.addMessage(e.getMessage());
        }
        this.baseHandler(request, response, mv);
        this.divideResultSet(request, mv, opResult); //结果集分页
        this.SaveOperationResult(mv, opResult); //返回结果集
        this.setResultText(mv);
        return mv;
    }

    private void setResultText(ModelAndView mv) {
        List<CpuDegrees> resultSet = (List<CpuDegrees>) mv.getModelMap().get(WebConstant.ATT_ResultSet);
        if (resultSet == null || resultSet.isEmpty()) {
            return;
        }
        List<PubFlag> statuesFlags = pubFlagMapper.getCodeByType(FrameDBConstant.TYPE_DEVICE_STATUS_NAME);
        for (CpuDegrees vo : resultSet) {
            if (statuesFlags != null && !statuesFlags.isEmpty()) {
                vo.setStatusName(DBUtil.getTextByCode(vo.getStatus(), statuesFlags));
            }

        }
    }

    private OperationResult queryPlan(HttpServletRequest request, CpuDegreesMapper cpuDegreesMapper, OperationLogMapper operationLogMapper) throws Exception {
        OperationResult or = new OperationResult();
        LogUtil logUtil = new LogUtil();
        List<CpuDegrees> resultSet;
        try {
            resultSet = cpuDegreesMapper.queryPlan();
            or.setReturnResultSet(resultSet);
            or.addMessage("成功查询" + resultSet.size() + "条记录");
        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, operationLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + resultSet.size() + "条记录", operationLogMapper);
        return or;
    }

    public int addStatus(CpuDegrees vo) throws Exception {
        TransactionStatus status = null;
        int n = 0;
        try {
            status = txMgr.getTransaction(this.def);
            n = cpuDegreesMapper.update(vo);
            if (n != 1) {
                int insertIntoCur = cpuDegreesMapper.insertIntoCur(vo);
            }
            int insertIntoHis = cpuDegreesMapper.insertIntoHis(vo);
            txMgr.commit(status);
        } catch (Exception e) {
            if (txMgr != null) {
                txMgr.rollback(status);
            }
            e.printStackTrace();
        } finally {
            return n;
        }
    }
}
