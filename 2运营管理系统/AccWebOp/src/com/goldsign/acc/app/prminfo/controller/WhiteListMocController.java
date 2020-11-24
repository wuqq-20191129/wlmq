/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.prminfo.controller;

import com.goldsign.acc.app.prminfo.entity.WhiteListMoc;
import com.goldsign.acc.app.prminfo.mapper.WhiteListMocMapper;
import com.goldsign.acc.app.querysys.controller.CodePubFlagController;
import com.goldsign.acc.app.querysys.entity.OperatorLog;
import com.goldsign.acc.frame.constant.CommandConstant;
import com.goldsign.acc.frame.mapper.OperationLogMapper;
import com.goldsign.acc.frame.util.ExpUtil;
import com.goldsign.acc.frame.util.FormUtil;
import com.goldsign.acc.frame.util.LogUtil;
import com.goldsign.acc.frame.vo.OperationResult;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author zhouy
 * 交通部一卡通白名单
 * 20180208
 */
@Controller
public class WhiteListMocController extends CodePubFlagController {

    @Autowired
    private WhiteListMocMapper mapper;

    @RequestMapping("/WhiteListMoc")
    public ModelAndView service(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView("/jsp/prminfo/white_list_moc.jsp");
        String command = request.getParameter("command");
        OperationResult opResult = new OperationResult();
        try {
            if (command != null) {
                command = command.trim();
                if (command.equals(CommandConstant.COMMAND_QUERY))//查询操作
                {
                    opResult = this.query(request, this.mapper, this.operationLogMapper);
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

    private OperationResult query(HttpServletRequest request, WhiteListMocMapper mapper, OperationLogMapper operationLogMapper) throws Exception {
        OperationResult or = new OperationResult();
        LogUtil logUtil = new LogUtil();
        WhiteListMoc queryCondition;
        List<WhiteListMoc> resultSet;
        try {
            queryCondition = this.getQueryCondition(request);
            resultSet = mapper.getWhiteListMocs(queryCondition);
            or.setReturnResultSet(resultSet);
            or.addMessage("成功查询" + resultSet.size() + "条记录");
        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, operationLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + resultSet.size() + "条记录", operationLogMapper);
        return or;
    }

    private WhiteListMoc getQueryCondition(HttpServletRequest request) {
        WhiteListMoc whiteListMoc = new WhiteListMoc();
        whiteListMoc.setIss_identify_id(FormUtil.getParameter(request, "q_iss_identify_id"));
        whiteListMoc.setIss_identify_num(FormUtil.getParameter(request, "q_iss_identify_num"));
        return whiteListMoc;
    }
    
    @RequestMapping("/WhiteListMocExportAll")
    public void exportExcel(HttpServletRequest request,HttpServletResponse response) throws Exception{
        List results = this.getBufferElements(request);
        List<Map<String,String>> queryResults = this.entityToMap(results);
        ExpUtil.renderExportExcelPath(response, ExpUtil.exportExcel(queryResults, request));
    }
    
    private List<Map<String,String>> entityToMap(List results){
        List<Map<String,String>> list =  new ArrayList<>();
        int row = results.size();
        for(int n=0;n<row ;n++){
            WhiteListMoc vo = (WhiteListMoc)results.get(n);
            Map<String,String> map = new HashMap<>();
            map.put("iss_identify_id", vo.getIss_identify_id());
            map.put("iss_identify_num", vo.getIss_identify_num());
            list.add(map);
        }
        return list;
    }
}
