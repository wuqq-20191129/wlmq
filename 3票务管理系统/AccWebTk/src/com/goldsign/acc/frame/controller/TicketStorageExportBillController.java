/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.frame.controller;

import com.goldsign.acc.frame.mapper.ReportUserMapper;
import com.goldsign.acc.frame.constant.ReportConstant;
import com.goldsign.acc.frame.entity.ReportUser;
import com.goldsign.acc.frame.util.PageControlUtil;
import com.goldsign.acc.frame.util.ReportUtil;
import com.goldsign.acc.frame.vo.OperationResult;
import com.goldsign.acc.frame.vo.ReportParameter;
import java.util.HashMap;
import java.util.StringTokenizer;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author hejj
 */
@Controller
public class TicketStorageExportBillController {
    @Autowired
    private ReportUserMapper reportUserMapper;
    
     @RequestMapping(value = "/ticketStorageExportBillManage")
    public ModelAndView service(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView();
        OperationResult opResult = new OperationResult();
        ReportParameter reportParameter;
        ReportUtil util = new ReportUtil();
        try {
             reportParameter = this.genReportParameter(request);// 获取报表参数
             ReportUser reportUser =this.getDbLoginInfo(reportUserMapper);
              //生成报表文件
            util.genReportFile(reportParameter.getDestinationName() + ReportUtil.REPORT_PDF_SUFFIX,
                    reportParameter.getTemplateName(), ReportUtil.PDF, reportParameter.getReportContext(),
                    reportParameter.getParameters(),reportUser.getDs_user(),reportUser.getDs_pass());
            mv = this.getModelAndView(request, reportParameter);
            
        }
        catch (Exception e) {
            e.printStackTrace();
            opResult.addMessage(e.getMessage());
        }
        return mv;
        
    }
    private ReportUser getDbLoginInfo(ReportUserMapper mapper){
        ReportUser user = mapper.getReportUser(ReportConstant.REPORT_DS_ID_TK);
        ReportUtil util = new ReportUtil();
        user.setDs_pass(util.getPass(user.getDs_pass()));
        return user;
    }
    private String RPGetTemplateName(HttpServletRequest request)
            throws Exception {
        String tempName = request.getParameter(ReportUtil.REPORT_TEMPLATE_NAME);
        ReportUtil util = new ReportUtil();
        if (tempName == null || tempName.trim().length() == 0) {
            throw new Exception("缺少生成报表的模板名称");
        }

        return tempName;
    }
    private ModelAndView getModelAndView(HttpServletRequest request, ReportParameter reportParameter) throws Exception {

        ReportUtil util = new ReportUtil();
        String path = ReportUtil.BASE_PATH_BILL + util.RPGetDestinationFileNameOnly(request, 
                                    reportParameter.getTemplateName()) + ReportUtil.REPORT_PDF_SUFFIX;
        ModelAndView mv = new ModelAndView(path);
         
        return mv;

    }
    private ReportParameter genReportParameter(HttpServletRequest request)
            throws Exception {
        ReportParameter rp = new ReportParameter();
        ReportUtil util = new ReportUtil();

        String templateName = this.RPGetTemplateName(request);// 获取报表模板名称
        HashMap params = this.RPGetReportParameters(request);// 获取报表参数
        String reportContext = util.RAGetReportUrlContext(request);


        rp.setDestinationName(util.RPGetDestinationFileName(request, templateName));// 生成报表存放路径及名称，名称=报表模板+查询条件，使用"."作为分隔符


        rp.setTemplateName(templateName);

        //this.processParameterDate(params);
        rp.setParameters(params);
        rp.setReportContext(reportContext);



        return rp;
    }
    private HashMap RPGetReportParameters(HttpServletRequest request) {
        String parameterNames = request.getParameter("paramNames");
        HashMap parameters = new HashMap();

        if (parameterNames == null || parameterNames.length() == 0) {
            return parameters;
        }
        StringTokenizer st = new StringTokenizer(parameterNames, "#");
        String name = "";
        String value = "";
       // User user = (User) request.getSession().getAttribute("User");
        String operator = PageControlUtil.getOperatorFromSession(request);
        parameters.put("operatorId", operator);
        while (st.hasMoreTokens()) {
            name = st.nextToken();
            value = request.getParameter(name);
            if (value == null || value.trim().length() == 0) {
                value = "";
            }
            parameters.put(name, value);
        }

        return parameters;
    }
    
}
