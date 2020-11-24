/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.goldsign.acc.app.querysys.controller;

import com.goldsign.acc.app.config.mapper.ConfigMapper;
import com.goldsign.acc.app.querysys.entity.RpLogDetail;
import com.goldsign.acc.app.querysys.mapper.RpLogDetailMapper;
import com.goldsign.acc.frame.constant.CommandConstant;
import com.goldsign.acc.frame.entity.PubFlag;
import com.goldsign.acc.frame.mapper.PubFlagMapper;
import com.goldsign.acc.frame.util.ExpUtil;
import com.goldsign.acc.frame.vo.OperationResult;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author  刘粤湘
 * @date    2017-11-7 11:05:04
 * @version V1.0
 * @desc  报表生成日志
 */
@Controller
public class ReporBuildLogController extends CodePubFlagController{
    
    @Autowired
    RpLogDetailMapper rpLogDetailMapper;
    
    @Autowired
    PubFlagMapper pubFlagMapper;
    
    private Map mapRepModules;
    
     @Autowired
    private ConfigMapper configMapper;
    
    @RequestMapping("/ReporBuildLog")
    public Object service(HttpServletRequest request, HttpServletResponse response) {
         ModelAndView mv = new ModelAndView("/jsp/querysys/rp_log_detail.jsp");
         OperationResult opResult = new OperationResult();
         String command = request.getParameter("command");
         
         this.init(request,mv);
         
        try {
            if (command != null) {
                command = command.trim();
                if (command.equals(CommandConstant.COMMAND_QUERY))//查询操作
                {
                    RpLogDetail record = new RpLogDetail();
                    record = this.getQryCon(record, request);
                    request.getSession().setAttribute("qryRpLogDetailCon", record);

                    List records = rpLogDetailMapper.rpLogDetailQry(record);

                    this.fltRecords(records);

                    opResult.setReturnResultSet(records);
                }
                if (command != null && command.equals(CommandConstant.COMMAND_EXP_ALL)) {
                    /*从session中获取查询条件*/
                    RpLogDetail qryRpLogDetailCon = null;
                    if (request.getSession().getAttribute("qryRpLogDetailCon") != null) {
                        qryRpLogDetailCon = (RpLogDetail) request.getSession().getAttribute("qryRpLogDetailCon");
                        /*删除查询条件*/
                        request.getSession().removeAttribute("qryRpLogDetailCon");
                    }

                    /*获得数据集*/
                    List records = rpLogDetailMapper.rpLogDetailQryToMap(qryRpLogDetailCon);
                    this.fltMapRecords(records);

                    /*获得下载文件流*/
                    ResponseEntity<byte[]> re = ExpUtil.expAll(records, request, configMapper);
                    return re;
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

  

    private RpLogDetail getQryCon(RpLogDetail record, HttpServletRequest request) {
        if(record == null){
            return record;
        }
        String q_rpCode = request.getParameter("q_rpCode");
         if (q_rpCode != null && q_rpCode.trim().equals("")) {
            q_rpCode = null;
        }
        record.setReportCode(q_rpCode);
        
        
        
        String q_rpModule = request.getParameter("q_rpModule");
         if (q_rpModule != null && q_rpModule.trim().equals("")) {
            q_rpModule = null;
        }
        record.setReportModule(q_rpModule);
        
        String q_balanceWaterNo = request.getParameter("q_balanceWaterNo");
         if (q_balanceWaterNo != null && q_balanceWaterNo.trim().equals("")) {
            q_balanceWaterNo = null;
        }
        record.setBalanceWaterNo(q_balanceWaterNo);
        
        String q_beginDate = request.getParameter("q_beginDate");
         if (q_beginDate != null && q_beginDate.trim().equals("")) {
            q_beginDate = null;
        }
        record.setStrGenStartTime(q_beginDate);
        record.setStrGenEndTime(q_beginDate + " 23:59:59");
        
        String q_rpSize = request.getParameter("q_rpSize");
        if (q_rpSize != null && q_rpSize.trim().equals("")) {
            q_rpSize = null;
        }
        if (q_rpSize != null) {
            record.setReportSize(BigDecimal.valueOf(Long.parseLong(q_rpSize)));
        }
                    
        return record;
    }

    private void init(HttpServletRequest request,ModelAndView mv) {
        mapRepModules = new HashMap();
        List<PubFlag> repModules = pubFlagMapper.getReportModules();
        repModules.forEach((pubFlag) -> {
            mapRepModules.put(pubFlag.getCode(), pubFlag.getCode_text());
        });
        mv.addObject("reportModules", repModules);
        
        List<PubFlag> repCodes = pubFlagMapper.getALLReportCodes();
        mv.addObject("reportCodes", repCodes);
    }

    private void fltRecords(List records) {
        for (Object obj : records) {
            RpLogDetail rpLogDetail = (RpLogDetail) obj;
            rpLogDetail.setStrGenStartTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(rpLogDetail.getGenStartTime()));
            rpLogDetail.setReportModuleName((String) mapRepModules.get(rpLogDetail.getReportModule()));
        }
    }

    private void fltMapRecords(List records) {
        for (Object obj : records) {
            Map rpLogDetailMap = (Map) obj;
            rpLogDetailMap.put("GEN_START_TIME", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(rpLogDetailMap.get("GEN_START_TIME")));
//            System.out.println(rpLogDetailMap.get("REPORT_MODULE"));
            rpLogDetailMap.put("REPORT_MODULE_NAME", mapRepModules.get(rpLogDetailMap.get("REPORT_MODULE")));
           
        }
    }
    
//    @RequestMapping("/ReporBuildLogExportAll")
//	public void exportExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {
//        List results = this.getBufferElementsForCurClass(request,RpLogDetail.class.getName());
//        String expAllFields = request.getParameter("expAllFields");
//        List<Map<String, Object>> queryResults = ExpUtil.entityToMap(expAllFields,results);
//        ExpUtil.renderExportExcelPath(response, ExpUtil.exportExcel(queryResults, request));
//        }
    @RequestMapping("/ReporBuildLogExportAll")
    public void exportExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	
        List results = this.getBufferElementsForCurClass(request, "com.goldsign.acc.app.querysys.entity.RpLogDetail");
        List<Map<String,String>> queryResults = this.entityToMap(results);
        ExpUtil.renderExportExcelPath(response, ExpUtil.exportExcel(queryResults, request));
    }

    private List<Map<String, String>> entityToMap(List results) {
        List<Map<String,String>> list =  new ArrayList<>();
        int row = results.size();
        for(int n=0;n<row ;n++){
            RpLogDetail vo = (RpLogDetail)results.get(n);
            Map<String,String> map = new HashMap<>();
            map.put("REPORT_CODE", vo.getReportCode());
            map.put("REPORT_NAME", vo.getReportName());
            map.put("REPORT_MODULE", vo.getReportModule());
            map.put("REPORT_MODULE_NAME", vo.getReportModuleName());
            map.put("REPORT_SIZE", vo.getReportSize().toString());
            map.put("GEN_START_TIME", vo.getGenStartTime().toString());
            map.put("USE_TIME", vo.getUseTime().toString());
            map.put("BALANCE_WATER_NO", vo.getBalanceWaterNo());
            map.put("REPORT_WATER_NO", vo.getReportWaterNo());
            list.add(map);
        }
        return list;
    }
}
