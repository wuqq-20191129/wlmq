package com.goldsign.acc.app.st.controller;

import com.goldsign.acc.app.st.entity.TkIndex;
import com.goldsign.acc.app.st.mapper.TkIndexMapper;
import com.goldsign.acc.frame.constant.CommandConstant;
import com.goldsign.acc.frame.mapper.OperationLogMapper;
import com.goldsign.acc.frame.util.ExpUtil;
import com.goldsign.acc.frame.util.LogUtil;
import com.goldsign.acc.frame.vo.OperationResult;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 票务管理系统 - 分表记录
 *
 * @author xiaowu
 */
@Controller
public class TkIndexController extends TkIndexParentController {

    @Autowired
    private TkIndexMapper mapper;

    @RequestMapping(value = "/tkIndex")
    public ModelAndView service(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView("/jsp/st/tkIndex.jsp");
        String command = request.getParameter("command");
        String operType = request.getParameter("operType");
        OperationResult opResult = new OperationResult();
        try {
            if (command != null) {
                command = command.trim();
                if (command.equals(CommandConstant.COMMAND_QUERY)) {
                    opResult = this.queryPlan(request, this.mapper, this.operationLogMapper);
                }              
                if (this.isUpdateOp(command, operType))//更新操作，增、删、改、审核,查询更新结果或原查询结果
                {
                    this.queryUpdateResult(command, operType, request, mapper, opResult, mv);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            opResult.addMessage(e.getMessage());
        }

        this.baseHandler(request, response, mv);
        this.divideResultSet(request, mv, opResult); //结果集分页
        this.SaveOperationResult(mv, opResult); //返回结果集
        return mv;
    }
    
    @RequestMapping("/tkIndexExportAll")
    public void exportExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {
        List results = this.getBufferElements(request);
        String expAllFields = request.getParameter("expAllFields");
        List<Map<String, Object>> queryResults
                = ExpUtil.entityToMap(expAllFields, results);
        ExpUtil.renderExportExcelPath(response,
                ExpUtil.exportExcel(queryResults, request));
    }

    

    private void queryUpdateResult(String command, String operType, HttpServletRequest request, TkIndexMapper mapper,  OperationResult opResult, ModelAndView mv) throws Exception {
        if (!command.equals(CommandConstant.COMMAND_ADD)) {
            this.saveQueryControlDefaultValues(request, mv); //保存查询条件
        }
        this.queryForOpPlan(request, this.mapper, this.operationLogMapper, opResult);
    }

   

    public OperationResult queryForOpPlan(HttpServletRequest request, TkIndexMapper mapper, OperationLogMapper opLogMapper, OperationResult opResult) throws Exception {
        LogUtil logUtil = new LogUtil();
        TkIndex queryCondition;
        List<TkIndex> resultSet;

        try {
            queryCondition = this.getQueryConditionForOpPlan(request, opResult);
            if (queryCondition == null) {
                return null;
            }
            resultSet = mapper.queryPlan(queryCondition);
            opResult.setReturnResultSet(resultSet);
        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + resultSet.size() + "条记录", opLogMapper);
        return opResult;
    }

    public OperationResult queryPlan(HttpServletRequest request, TkIndexMapper stcMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult or = new OperationResult();
        LogUtil logUtil = new LogUtil();
        TkIndex queryCondition;
        List<TkIndex> resultSet;

        try {
            queryCondition = this.getQueryConditionIn(request);
            resultSet = stcMapper.queryPlan(queryCondition);
            or.setReturnResultSet(resultSet);
            or.addMessage("成功查询" + resultSet.size() + "条记录");
        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + resultSet.size() + "条记录", opLogMapper);
        return or;

    }

}
