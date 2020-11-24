/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.opma.controller;

import com.goldsign.acc.app.opma.entity.ParamDownStatus;
import com.goldsign.acc.app.opma.mapper.ParamDownStatusMapper;
import com.goldsign.acc.frame.constant.CommandConstant;
import com.goldsign.acc.frame.controller.PrmBaseController;
import com.goldsign.acc.frame.entity.PubFlag;
import com.goldsign.acc.frame.mapper.OperationLogMapper;
import com.goldsign.acc.frame.util.DBUtil;
import com.goldsign.acc.frame.util.ExpUtil;
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
 * @desc:参数下发状态
 * @author:chenzx
 * @create date: 2017-6-22
 */
@Controller
public class ParamDownStatusController extends PrmBaseController {

    @Autowired
    private ParamDownStatusMapper mapper;

    @RequestMapping("/ParamDownStatus")
    public ModelAndView service(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView mv = new ModelAndView("/jsp/opma/param_down_status.jsp");
        String command = request.getParameter("command");
        OperationResult opResult = new OperationResult();
        try {
            if (command != null) {
                command = command.trim();
                if (command.equals(CommandConstant.COMMAND_QUERY))//查询操作
                {
                    opResult = this.query(request, this.mapper, this.operationLogMapper);
                }
                if (command.equals(CommandConstant.COMMAND_NEXT) || command.equals(CommandConstant.COMMAND_NEXTEND) || command.equals(CommandConstant.COMMAND_BACK)
                        || command.equals(CommandConstant.COMMAND_BACKEND)) {
                        this.saveQueryControlDefaultValues(request, mv);//保存查询条件
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        String[] attrNames = {LINES, PARM_TYPES, OPERATORS};
        this.setPageOptions(attrNames, mv, request, response);
        this.setDownloadStatus(mv);//设置查询条件“应用情况”
        this.getResultSetText((List<ParamDownStatus>) opResult.getReturnResultSet(), mv);
        this.baseHandler(request, response, mv);
        this.divideResultSet(request, mv, opResult);//结果集分页
        this.SaveOperationResult(mv, opResult);
        return mv;
    }
    
    private void setDownloadStatus(ModelAndView mv){
        List<PubFlag> options;
        options = mapper.getDownloadStatus();
        mv.addObject("downloadStatus", options);
    }

    private OperationResult query(HttpServletRequest request, ParamDownStatusMapper mapper, OperationLogMapper operationLogMapper) throws Exception {
        OperationResult or = new OperationResult();
        LogUtil logUtil = new LogUtil();
        ParamDownStatus queryCondition;
        List<ParamDownStatus> resultSet;
        List<ParamDownStatus> resultSet1;
        try {
            queryCondition = this.getQueryCondition(request);
            resultSet = mapper.getParamDownStatus(queryCondition);
            resultSet1 = this.getParamDownStatus1(resultSet);//修改非运营时间段下发黑名单的通知情况
            or.setReturnResultSet(resultSet1);
            or.addMessage("成功查询" + resultSet1.size() + "条记录");
        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, operationLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + resultSet.size() + "条记录", operationLogMapper);
        return or;
    }

    private ParamDownStatus getQueryCondition(HttpServletRequest request) {
        ParamDownStatus vo = new ParamDownStatus();
        String beginDateTime = request.getParameter("q_beginDate");
        String endDateTime = request.getParameter("q_endDate");
        if (beginDateTime!=null && !beginDateTime.equals("")) {
            beginDateTime = beginDateTime + " 00:00:00";
            vo.setBeginDatetime(beginDateTime);
        }
        if (endDateTime!=null && !endDateTime.equals("")) {
            endDateTime = endDateTime + " 23:59:59";
            vo.setEndDatetime(endDateTime);
        }
        vo.setLine_id(request.getParameter("q_lineId"));
        vo.setParm_type_id(request.getParameter("q_paramTypeId"));
        vo.setOperator_id(request.getParameter("q_operatorId"));
        vo.setDownload_status(request.getParameter("q_downloadStatus"));
        return vo;
    }

    private void getResultSetText(List<ParamDownStatus> resultSet, ModelAndView mv) throws Exception {
        List<PubFlag> lines = (List<PubFlag>) mv.getModelMap().get(PrmBaseController.LINES);
        List<PubFlag> parmTypes = (List<PubFlag>) mv.getModelMap().get(PrmBaseController.PARM_TYPES);
        List<PubFlag> operators = (List<PubFlag>) mv.getModelMap().get(PrmBaseController.OPERATORS);
        String lineInfor, fileInfor ,download;
        int code=0;

        for (ParamDownStatus vo : resultSet) {
            vo.setCode(code);
            code++;
            if (lines != null && !lines.isEmpty()) {
                vo.setLineText(DBUtil.getTextByCode(vo.getLine_id(), lines));
            }
            if (parmTypes != null && !parmTypes.isEmpty()) {
                vo.setParmTypeText(DBUtil.getTextByCode(vo.getParm_type_id(), parmTypes));
            }
            if (operators != null && !operators.isEmpty()) {
                vo.setOperatorText(DBUtil.getTextByCode(vo.getOperator_id(), operators));
            }
            lineInfor = vo.getInform_result();
            if (lineInfor.equals("0")) {
                vo.setLineInforText("未通知");
            } else if (lineInfor.equals("1")) {
                vo.setLineInforText("通知成功");
            } else if (lineInfor.equals("2")) {
                vo.setLineInforText("通知失败");
            }
            
            fileInfor = vo.getDistribute_result();
            if (fileInfor.equals("0")) {
                vo.setFileInforText("未生成");
            } else if (fileInfor.equals("2")) {
                vo.setFileInforText("全部生成成功");
            } else if (fileInfor.equals("1")) {
                vo.setFileInforText("出错文件： " + vo.getDistribute_memo());
            } else if (fileInfor.equals("9")) {
                vo.setFileInforText("未知错误");
            }else if (fileInfor.equals("3")) {
                vo.setFileInforText("(除黑名单)部分生成");
            }
            download = vo.getDownload_status();
            if(download!=null && download!=""){
                String downLoadText = "";
                String[]  downloadIds= download.split(",");
                int i = downloadIds.length;
                String downloadId = "";
                boolean bl = true;
                for(int n=0;n<i;n++){
                    downloadId = downloadIds[n];
                    if ("Y".equals(downloadId)) {
                        downLoadText+= "应用成功,";
                    }else if ("N".equals(downloadId)) {
                        downLoadText+= "应用失败,";
                    }else{
                        if(bl){
                            downLoadText+= "未知,";
                            bl=false;
                        }
                    }
                };
                vo.setDownloadText(downLoadText.substring(0,downLoadText.length()-1));
            }
            
        }
    }
    
    @RequestMapping("/ParamDownStatusExportAll")
    public void exportExcel(HttpServletRequest request,HttpServletResponse response) throws Exception{
        List results = this.getBufferElementsForCurClass(request, "com.goldsign.acc.app.opma.entity.ParamDownStatus");
        List<Map<String,String>> queryResults = this.entityToMap(results);
        ExpUtil.renderExportExcelPath(response, ExpUtil.exportExcel(queryResults, request));
    }
    
    private List<Map<String,String>> entityToMap(List results){
        List<Map<String,String>> list =  new ArrayList<>();
        int row = results.size();
        for(int n=0;n<row ;n++){
            ParamDownStatus vo = (ParamDownStatus)results.get(n);
            Map<String,String> map = new HashMap<>();
            map.put("parmTypeText", vo.getParmTypeText());
            map.put("versionText", vo.getParm_type_id() + "-" + vo.getVersion_no());
            map.put("distribute_datetime", vo.getDistribute_datetime());
            map.put("lineText", vo.getLineText());
            map.put("fileInforText", vo.getFileInforText());
            map.put("lineInforText", vo.getLineInforText());
            map.put("downloadText", vo.getDownloadText());
            map.put("operatorText", vo.getOperatorText());
            list.add(map);
        }
        return list;
    }

    private List<ParamDownStatus> getParamDownStatus1(List<ParamDownStatus> resultSet) {
        for(ParamDownStatus r1: resultSet){
            String PramBlcak = r1.getParm_type_id().substring(0, 2);
            if((PramBlcak.equals("06"))&&(r1.getDistribute_result().equals("3"))){
                r1.setInform_result("0");
            }
            
        }
        return resultSet;
    }
}
