/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.query.controller;

import com.goldsign.acc.app.query.entity.BoxLogicalDetail;
import com.goldsign.acc.app.query.mapper.BoxLogicalDetailMapper;
import com.goldsign.acc.frame.constant.CommandConstant;
import com.goldsign.acc.frame.controller.StorageOutInBaseController;
import com.goldsign.acc.frame.util.FormUtil;
import com.goldsign.acc.frame.util.LogUtil;
import com.goldsign.acc.frame.vo.OperationResult;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author zhouy
 * 库存查询——盒内逻辑卡明细
 */
@Controller
public class BoxLogicalDetailController extends StorageOutInBaseController{
    
    @Autowired
    private BoxLogicalDetailMapper mapper;


    @RequestMapping("/boxLogicalDetail")
    public ModelAndView service(HttpServletRequest request, HttpServletResponse response) {

        ModelAndView mv = new ModelAndView("/jsp/query/boxLogicalDetail.jsp");
        OperationResult opResult = new OperationResult();
        try {
            opResult = this.query(request);
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.baseHandler(request, response, mv);
        this.divideResultSet(request, mv, opResult);
        this.SaveOperationResult(mv, opResult);//返回结果集
        return mv;

    }
    

    public OperationResult query(HttpServletRequest request) throws Exception {
        OperationResult or = new OperationResult();
        LogUtil logUtil = new LogUtil();
        BoxLogicalDetail vo;
        List<BoxLogicalDetail> resultSet = new ArrayList<>();

        try {
            vo = this.getQueryCondition(request);
            resultSet = mapper.query(vo);
            or.setReturnResultSet(resultSet);
            or.addMessage("成功查询" + resultSet.size() + "条记录");
        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, operationLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + resultSet.size() + "条记录", operationLogMapper);
        return or;

    }
    
     private BoxLogicalDetail getQueryCondition(HttpServletRequest request) {
        BoxLogicalDetail vo = new BoxLogicalDetail();
        vo.setBox_id(FormUtil.getParameter(request, "box_id"));
        return vo;
    }
}
