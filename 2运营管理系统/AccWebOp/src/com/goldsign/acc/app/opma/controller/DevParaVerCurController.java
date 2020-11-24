/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.opma.controller;

import com.goldsign.acc.app.opma.entity.DevParaVerCur;
import com.goldsign.acc.app.opma.mapper.DevParaVerCurMapper;
import com.goldsign.acc.app.querysys.entity.OperatorLog;
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
 *
 * @author zhouyang
 * 设备参数版本查询结果
 * 20170622
 */
@Controller
public class DevParaVerCurController extends PrmBaseController{
    
    @Autowired
    private DevParaVerCurMapper devParaVerCurMapper;


    @RequestMapping("/DevParaVerCur")
    public ModelAndView service(HttpServletRequest request, HttpServletResponse response) {

        ModelAndView mv = new ModelAndView("/jsp/opma/dev_para_ver_cur.jsp");

        String command = request.getParameter("command");
        OperationResult opResult = new OperationResult();
        try {
            if (command != null) {
                command = command.trim();
                if (command.equals(CommandConstant.COMMAND_QUERY))//查询操作
                {
                    opResult = this.query(request, this.devParaVerCurMapper, this.operationLogMapper);
                }
                if (command.equals(CommandConstant.COMMAND_NEXT) || command.equals(CommandConstant.COMMAND_NEXTEND) || command.equals(CommandConstant.COMMAND_BACK)
                        || command.equals(CommandConstant.COMMAND_BACKEND)) {
                        this.saveQueryControlDefaultValues(request, mv);//保存查询条件
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        String[] attrNames = {OPERATORS,PARMTYPEIDS,DEVRECORDFLAGS,LINES,LINE_STATIONS,STATIONS,FIL_DEV_TYPES,LCC_LINES,ISEFFECT};
        
        this.setPageOptions(attrNames, mv, request, response);//设置页面操作员选项值
        this.getResultSetText((List<DevParaVerCur>) opResult.getReturnResultSet(), mv);
        this.baseHandler(request, response, mv);
        this.divideResultSet(request, mv, opResult);//结果集分页
        this.SaveOperationResult(mv, opResult);//返回结果集
        return mv;

    }
    
    private void getResultSetText(List<DevParaVerCur> resultSet, ModelAndView mv) {
        List<PubFlag> lines = (List<PubFlag>) mv.getModelMap().get(PrmBaseController.LINES);
        List<PubFlag> stations = (List<PubFlag>) mv.getModelMap().get(PrmBaseController.STATIONS);
        List<PubFlag> devTypeNames = (List<PubFlag>) mv.getModelMap().get(PrmBaseController.FIL_DEV_TYPES);
        List<PubFlag> recordFlags = (List<PubFlag>) mv.getModelMap().get(PrmBaseController.DEVRECORDFLAGS);
        List<PubFlag> parmTypeIDs = (List<PubFlag>) mv.getModelMap().get(PrmBaseController.PARMTYPEIDS);
        int code = 0;
        for (DevParaVerCur dv : resultSet) {
            dv.setCode(code);
            code++;
            if (lines != null && !lines.isEmpty()) {
                dv.setLine_name(DBUtil.getTextByCode(dv.getLine_id().trim(), lines));
            }
            if (stations != null && !stations.isEmpty()) {
                dv.setStation_name(DBUtil.getTextByCode(dv.getStation_id().trim(),dv.getLine_id().trim(), stations));
            }
            if (devTypeNames != null && !devTypeNames.isEmpty()) {
                dv.setDev_type_name(DBUtil.getTextByCode(dv.getDev_type_id().trim(), devTypeNames));
            }
            if (recordFlags != null && !recordFlags.isEmpty()) {
                dv.setRecord_flag_name(DBUtil.getTextByCode(dv.getRecord_flag(), recordFlags));
            }
            if (parmTypeIDs != null && !parmTypeIDs.isEmpty()) {
                dv.setParm_type_name(DBUtil.getTextByCode(dv.getParm_type_id(), parmTypeIDs));
            }
        }
    }

    public OperationResult query(HttpServletRequest request, DevParaVerCurMapper dvMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult or = new OperationResult();
        LogUtil logUtil = new LogUtil();
        Map<String,Object> devParaVerCur;
        List<DevParaVerCur> resultSet;
         

        try {
            devParaVerCur = this.getQueryCondition(request);
            dvMapper.getDevParaVerCurs(devParaVerCur);
            resultSet = (List<DevParaVerCur>) devParaVerCur.get("result");
            or.setReturnResultSet(resultSet);
            or.addMessage("成功查询" + resultSet.size() + "条记录");    
        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + resultSet.size() + "条记录", opLogMapper);
        return or;

    }
    
    private Map<String,Object> getQueryCondition(HttpServletRequest request) {
        Map<String,Object> devParaVerCur = new HashMap<String,Object>();
        devParaVerCur.put("record_flag",FormUtil.getParameter(request, "q_recordFlag"));
        devParaVerCur.put("is_effect",FormUtil.getParameter(request, "q_isEffect"));
        devParaVerCur.put("parm_type_id",FormUtil.getParameter(request, "q_parmTypeId"));
        if(FormUtil.getParameter(request, "q_beginDate") != null && !FormUtil.getParameter(request, "q_beginDate").equals("")){
            devParaVerCur.put("genTime",FormUtil.getParameter(request, "q_beginDate") + " 00:00:00");
        }
        if(FormUtil.getParameter(request, "q_endDate") != null && !FormUtil.getParameter(request, "q_endDate").equals("")){
            devParaVerCur.put("endTime",FormUtil.getParameter(request, "q_endDate") + " 23:59:59");
        }
        devParaVerCur.put("station_id",FormUtil.getParameter(request, "q_stationID"));
        devParaVerCur.put("dev_type_id",FormUtil.getParameter(request, "q_deviceType"));
        devParaVerCur.put("device_id",FormUtil.getParameter(request, "device_id"));
        //如果设备类型是LCC 则使用LCC_LINE_ID
//        if(FormUtil.getParameter(request, "q_deviceType") != null && "00".equals(FormUtil.getParameter(request, "q_deviceType"))){
//            devParaVerCur.put("line_id",FormUtil.getParameter(request, "q_lccLineId"));
//        }else{
            devParaVerCur.put("line_id",FormUtil.getParameter(request, "q_lineID"));
//        }
        return devParaVerCur;
    }
    
    @RequestMapping("/DevParaVerCurExportAll")
    public void exportExcel(HttpServletRequest request,HttpServletResponse response) throws Exception{
        List results = this.getBufferElementsForCurClass(request, "com.goldsign.acc.app.opma.entity.DevParaVerCur");
        List<Map<String,String>> queryResults = this.entityToMap(results);
        ExpUtil.renderExportExcelPath(response, ExpUtil.exportExcel(queryResults, request));
    }
    
    private List<Map<String,String>> entityToMap(List results){
        List<Map<String,String>> list =  new ArrayList<>();
        int row = results.size();
        for(int n=0;n<row ;n++){
            DevParaVerCur vo = (DevParaVerCur)results.get(n);
            Map<String,String> map = new HashMap<>();
            map.put("line_name", vo.getLine_name());
            map.put("station_name",vo.getStation_name());
            map.put("dev_type_name", vo.getDev_type_name());
            map.put("device_id",vo.getDevice_id());
            map.put("parm_type_name", vo.getParm_type_name());
            map.put("record_flag_name", vo.getRecord_flag_name());
            map.put("version_new", vo.getVersion_new());
            map.put("version_no", vo.getVersion_no());
            map.put("report_date", vo.getReport_date());
            map.put("remark",vo.getRemark());
            list.add(map);
        }
        return list;
    }
}
