/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.querysys.controller;

import com.goldsign.acc.app.querysys.entity.ViewOpertion;
import com.goldsign.acc.app.querysys.mapper.ViewOpertionMapper;
import com.goldsign.acc.frame.constant.CommandConstant;
import com.goldsign.acc.frame.controller.PrmBaseController;
import com.goldsign.acc.frame.entity.PubFlag;
import com.goldsign.acc.frame.mapper.OperationLogMapper;
import com.goldsign.acc.frame.util.DBUtil;
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
 * @author zhouyang
 * 20170619
 * 安全日志——查看操作
 */
@Controller
public class ViewOpertionController extends PrmBaseController{
    
    @Autowired
    private ViewOpertionMapper viewOpertionMapper;


    @RequestMapping("/ViewOpertion")
    public ModelAndView service(HttpServletRequest request, HttpServletResponse response) {

        ModelAndView mv = new ModelAndView("/jsp/querysys/view_opertion.jsp");
        OperationResult opResult = new OperationResult();
        try {
            opResult = this.query(request, this.viewOpertionMapper, this.operationLogMapper);
        } catch (Exception e) {
            e.printStackTrace();
        }

        String[] attrNames = {OPERATORS,OP_TYPE};
        
        this.setPageOptions(attrNames, mv, request, response);//设置页面操作员选项值
        this.getResultSetText((List<ViewOpertion>) opResult.getReturnResultSet(), mv);
        this.baseHandler(request, response, mv);
        this.SaveOperationResult(mv, opResult);//返回结果集
        return mv;

    }
    
    private void getResultSetText(List<ViewOpertion> resultSet, ModelAndView mv) {
        List<PubFlag> operators = (List<PubFlag>) mv.getModelMap().get(PrmBaseController.OPERATORS);
        List<PubFlag> op_type = (List<PubFlag>) mv.getModelMap().get(PrmBaseController.OP_TYPE);
        for (ViewOpertion vo : resultSet) {
            if (operators != null && !operators.isEmpty() && vo.getOperator_id()!=null && !vo.getOperator_id().equals("")) {
                vo.setOperator_name(DBUtil.getTextByCode(vo.getOperator_id(), operators));
            }
            if(vo.getModule() != null && !vo.getModule().isEmpty()){
                vo.setModule_name(viewOpertionMapper.getModuleName(vo));
            }
            if(op_type != null && !op_type.isEmpty() && vo.getOp_type()!=null && !vo.getOp_type().equals("")){
                vo.setOp_type_name(DBUtil.getTextByCode(vo.getOp_type(), op_type));
            }
        }
    }

    public OperationResult query(HttpServletRequest request, ViewOpertionMapper voMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult or = new OperationResult();
        LogUtil logUtil = new LogUtil();
        ViewOpertion viewOpertion;
        List<ViewOpertion> resultSet = new ArrayList<ViewOpertion>();

        try {
            viewOpertion = this.getQueryCondition(request);
            resultSet.addAll(voMapper.getOPViewOpertions(viewOpertion)) ;//运营管理系统
            resultSet.addAll(voMapper.getTKViewOpertions(viewOpertion));//票务管理系统
            resultSet.addAll(voMapper.getSAMViewOpertions(viewOpertion));//密钥卡管理系统
            resultSet.addAll(voMapper.getDMViewOpertions(viewOpertion));//数据管理管理系统
            resultSet.addAll(voMapper.getMNViewOpertions(viewOpertion));//设备监控系统
            resultSet.addAll(voMapper.getRLViewOpertions(viewOpertion));//清分规则系统
            
            or.setReturnResultSet(resultSet);
            or.addMessage("成功查询" + resultSet.size() + "条记录");
        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + resultSet.size() + "条记录", opLogMapper);
        return or;

    }
    
     private ViewOpertion getQueryCondition(HttpServletRequest request) {
        ViewOpertion viewOpertion = new ViewOpertion();
        if(FormUtil.getParameter(request, "login_time")!=null && !FormUtil.getParameter(request, "login_time").isEmpty()){
            viewOpertion.setBegin_date(FormUtil.getParameter(request, "login_time").substring(0,10)+" 00:00:01");
            viewOpertion.setEnd_date(FormUtil.getParameter(request, "login_time").substring(0,10)+" 23:59:59");
        }
        viewOpertion.setOperator_id(FormUtil.getParameter(request, "sysOperatorId").trim());
        return viewOpertion;
    }
}
