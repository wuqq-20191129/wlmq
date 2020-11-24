/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.opma.controller;

import com.goldsign.acc.app.opma.entity.DevParaVerHis;
import com.goldsign.acc.app.opma.mapper.DevParaVerHisMapper;
import com.goldsign.acc.app.prminfo.entity.DealAssignContc;
import com.goldsign.acc.frame.constant.CommandConstant;

import com.goldsign.acc.frame.controller.PrmBaseController;
import com.goldsign.acc.frame.entity.PubFlag;

import com.goldsign.acc.frame.mapper.OperationLogMapper;
import com.goldsign.acc.frame.util.DBUtil;
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
 * 设备参数查询历史
 * @author liudz
 */
@Controller
public class DevParaVerHisController extends PrmBaseController {

    @Autowired
    private DevParaVerHisMapper queryDevParaVerHisMapper;

    @RequestMapping("/DevParaVerHis")
    public ModelAndView service(HttpServletRequest request, HttpServletResponse response) {

        ModelAndView mv = new ModelAndView("/jsp/opma/dev_para_verhis.jsp");

        String command = request.getParameter("command");
        OperationResult opResult = new OperationResult();
        try {
            if (command != null) {
                command = command.trim();

                if (command.equals(CommandConstant.COMMAND_QUERY))//查询操作
                {
                    opResult = this.query(request, this.queryDevParaVerHisMapper, this.operationLogMapper);

                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        String[] attrNames = {LINES,STATIONS,LINE_STATIONS,DEV_TYPES,PARMTYPEIDS,DEVRECORDFLAGS};
        this.setPageOptions(attrNames, mv, request, response);//设置页面线路、车站等选项值、版本
        this.getResultSetText((List<DevParaVerHis>) opResult.getReturnResultSet(), mv);
        this.baseHandler(request, response, mv);
        this.divideResultSet(request, mv, opResult);//结果集分页
        this.SaveOperationResult(mv, opResult);//返回结果集
        this.SaveOperationResult(mv, opResult);
        return mv;

    }
    private void getResultSetText(List<DevParaVerHis> resultSet, ModelAndView mv) {
       //线路名
        List<PubFlag> lines = (List<PubFlag>) mv.getModelMap().get(PrmBaseController.LINES);
        //车站名
        List<PubFlag> stations = (List<PubFlag>) mv.getModelMap().get(PrmBaseController.STATIONS);
        //参数类型ID
        List<PubFlag> parmTypeIds = (List<PubFlag>) mv.getModelMap().get(PrmBaseController.PARMTYPEIDS);
        //设备版本标志
        List<PubFlag> devRecordFlags = (List<PubFlag>) mv.getModelMap().get(PrmBaseController.DEVRECORDFLAGS);
        //设备类型
        List<PubFlag> devTypeNames = (List<PubFlag>) mv.getModelMap().get(PrmBaseController.DEV_TYPES);
        for (DevParaVerHis dc : resultSet) {
            if (lines != null && !lines.isEmpty()) {
                if(dc.getLine_id()!= null && !dc.getLine_id().isEmpty()){
                    dc.setLine_name(DBUtil.getTextByCode(dc.getLine_id(), lines));
                }
            } 
             if (stations != null && !stations.isEmpty()) {
                if(dc.getLine_id()!= null && !dc.getLine_id().isEmpty()){
                   dc.setStation_name(DBUtil.getTextByCode(dc.getStation_id(),dc.getLine_id(),stations));
                }
            }
             if (parmTypeIds != null && !parmTypeIds.isEmpty()) {
                if(dc.getParm_type_id()!= null && !dc.getParm_type_id().isEmpty()){
                   dc.setParm_type_name(DBUtil.getTextByCode(dc.getParm_type_id(),parmTypeIds));
                }
            }
             if (devRecordFlags != null && !devRecordFlags.isEmpty()) {
                if(dc.getRecord_flag()!= null && !dc.getRecord_flag().isEmpty()){
                   dc.setRecord_flag_name(DBUtil.getTextByCode(dc.getRecord_flag(),devRecordFlags));
                }
            }
             if (devTypeNames != null && !devTypeNames.isEmpty()) {
                if(dc.getDev_type_id()!= null && !dc.getDev_type_id().isEmpty()){
                   dc.setDev_type_name(DBUtil.getTextByCode(dc.getDev_type_id(),devTypeNames));
                }
            }
        }
        }

    private DevParaVerHis getQueryCondition(HttpServletRequest request) {
        DevParaVerHis qCon = new DevParaVerHis();
        qCon.setLine_id(FormUtil.getParameter(request, "q_lineID"));
        qCon.setStation_id(FormUtil.getParameter(request, "q_stationID"));
        qCon.setDev_type_id(FormUtil.getParameter(request, "q_deviceType"));
        qCon.setDevice_id(FormUtil.getParameter(request, "device_id"));
         qCon.setParm_type_id(FormUtil.getParameter(request, "q_parmTypeId"));
        qCon.setRecord_flag(FormUtil.getParameter(request, "q_recordFlag"));
        qCon.setVersion_no(FormUtil.getParameter(request, "q_version_no"));
        qCon.setGenTime(FormUtil.getParameter(request, "q_b_time"));
        if(FormUtil.getParameter(request, "q_e_time") != null){
            qCon.setEndTime(FormUtil.getParameter(request, "q_e_time")+" 23:59:59");
        }else{
        qCon.setEndTime(FormUtil.getParameter(request, "q_e_time"));
        }
        return qCon;
    }

    public OperationResult query(HttpServletRequest request, DevParaVerHisMapper dpMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult or = new OperationResult();
        LogUtil logUtil = new LogUtil();
        DevParaVerHis queryCondition;
        List<DevParaVerHis> resultSet;

        try {
            queryCondition = this.getQueryCondition(request);
            resultSet = dpMapper.getDevParaVerHis(queryCondition);
            or.setReturnResultSet(resultSet);
            or.addMessage("成功查询" + resultSet.size() + "条记录");
        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + resultSet.size() + "条记录", opLogMapper);
        return or;

    }
    
    @RequestMapping("/DevParaVerHisExportAll")
    public void exportExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	
        List results = this.getBufferElementsForCurClass(request, "com.goldsign.acc.app.opma.entity.DevParaVerHis");
        List<Map<String,String>> queryResults = this.entityToMap(results);
        ExpUtil.renderExportExcelPath(response, ExpUtil.exportExcel(queryResults, request));
    }

    private List<Map<String, String>> entityToMap(List results) {
        List<Map<String,String>> list =  new ArrayList<>();
        int row = results.size();
        for(int n=0;n<row ;n++){
            DevParaVerHis vo = (DevParaVerHis)results.get(n);
            Map<String,String> map = new HashMap<>();
            map.put("line_name", vo.getLine_name());
            map.put("station_name", vo.getStation_name());
            map.put("dev_type_name", vo.getDev_type_name());
            map.put("device_id", vo.getDevice_id());
            map.put("parm_type_id", vo.getParm_type_id());
            map.put("record_flag_name", vo.getRecord_flag_name());
            map.put("version_no", vo.getVersion_no());
            map.put("report_date", vo.getReport_date());
            map.put("remark", vo.getRemark());
           
            list.add(map);
        }
        return list;
    }

}
