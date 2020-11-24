
package com.goldsign.acc.app.querysys.controller;

import com.goldsign.acc.app.querysys.entity.DeviceStatus;
import com.goldsign.acc.app.querysys.mapper.DeviceStatusMapper;
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
 * 设备状态查询
 * 20171127
 */
@Controller
public class DeviceStatusController extends PrmBaseController{
    
    @Autowired
    private DeviceStatusMapper deviceStatusMapper;


    @RequestMapping("/deviceStatus")
    public ModelAndView service(HttpServletRequest request, HttpServletResponse response) {

        ModelAndView mv = new ModelAndView("/jsp/querysys/device_status.jsp");

        String command = request.getParameter("command");
        OperationResult opResult = new OperationResult();
        try {
            if (command != null) {
                command = command.trim();
                if (command.equals(CommandConstant.COMMAND_QUERY))//查询操作
                {
                    opResult = this.query(request, this.operationLogMapper);
                }
                if (command.equals(CommandConstant.COMMAND_NEXT) || command.equals(CommandConstant.COMMAND_NEXTEND) || command.equals(CommandConstant.COMMAND_BACK)
                        || command.equals(CommandConstant.COMMAND_BACKEND)) {
                        this.saveQueryControlDefaultValues(request, mv);//保存查询条件
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        String[] attrNames = {LINES,STATIONS,LINE_STATIONS,DEV_TYPES,ACC_STATUS_VALUE};
        
        this.setPageOptions(attrNames, mv, request, response);//设置页面操作员选项值
        this.getResultSetText((List<DeviceStatus>) opResult.getReturnResultSet(), mv);
        this.baseHandler(request, response, mv);
        this.divideResultSet(request, mv, opResult);//结果集分页
        this.SaveOperationResult(mv, opResult);//返回结果集
        return mv;

    }
    
    private void getResultSetText(List<DeviceStatus> resultSet, ModelAndView mv) {
        List<PubFlag> lines = (List<PubFlag>) mv.getModelMap().get(LINES);
        List<PubFlag> stations = (List<PubFlag>) mv.getModelMap().get(STATIONS);
        List<PubFlag> devType = (List<PubFlag>) mv.getModelMap().get(DEV_TYPES);
        List<PubFlag> accStatusValues = (List<PubFlag>) mv.getModelMap().get(ACC_STATUS_VALUE);
        int code = 0;
        for (DeviceStatus ds : resultSet) {
            ds.setCode(code);
            code++;
            if (lines != null && !lines.isEmpty()) {
                ds.setLineName(DBUtil.getTextByCode(ds.getLine_id(), lines));
            }
            if (stations != null && !stations.isEmpty()) {
                ds.setStationName(DBUtil.getTextByCode(ds.getStation_id(), ds.getLine_id(), stations));
            }
            if (devType != null && !devType.isEmpty()) {
                ds.setDev_type_name(DBUtil.getTextByCode(ds.getDev_type_id(), devType));
            }
            if (accStatusValues != null && !accStatusValues.isEmpty()) {
                ds.setAcc_status_name(DBUtil.getTextByCode(ds.getAcc_status_value(), accStatusValues));
            }
        }
    }

    public OperationResult query(HttpServletRequest request, OperationLogMapper opLogMapper) throws Exception {
        OperationResult or = new OperationResult();
        LogUtil logUtil = new LogUtil();
        DeviceStatus deviceStatus;
        List<DeviceStatus> resultSet;

        try {
            deviceStatus = this.getQueryCondition(request);
            resultSet = deviceStatusMapper.getDeviceStatus(deviceStatus);
            or.setReturnResultSet(resultSet);
            or.addMessage("成功查询" + resultSet.size() + "条记录");
        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + resultSet.size() + "条记录", opLogMapper);
        return or;

    }
    
     private DeviceStatus getQueryCondition(HttpServletRequest request) {
        DeviceStatus deviceStatus = new DeviceStatus();
        deviceStatus.setLine_id(FormUtil.getParameter(request, "q_lineID"));
        deviceStatus.setStation_id(FormUtil.getParameter(request, "q_stationID"));
        deviceStatus.setDev_type_id(FormUtil.getParameter(request, "q_devTypeId"));
        deviceStatus.setDevice_id(FormUtil.getParameter(request, "q_deviceId"));
        deviceStatus.setStatus_id(FormUtil.getParameter(request, "q_statusId"));
        deviceStatus.setAcc_status_value(FormUtil.getParameter(request, "q_ACCStatusValue"));
         if(FormUtil.getParameter(request, "q_beginDate")!=null && !FormUtil.getParameter(request, "q_beginDate").equals("")){
            deviceStatus.setBeginDate(FormUtil.getParameter(request, "q_beginDate") + " 00:00:00");
        }
        if(FormUtil.getParameter(request, "q_endDate")!=null && !FormUtil.getParameter(request, "q_endDate").equals("")){
            deviceStatus.setEndDate(FormUtil.getParameter(request, "q_endDate") + " 23:59:59");
        }
        
        return deviceStatus;
    }
     
    @RequestMapping("/DeviceStatusExportAll")
    public void exportExcel(HttpServletRequest request,HttpServletResponse response) throws Exception{
        List results = this.getBufferElementsForCurClass(request, "com.goldsign.acc.app.querysys.entity.DeviceStatus");
        List<Map<String,String>> queryResults = this.entityToMap(results);
        ExpUtil.renderExportExcelPath(response, ExpUtil.exportExcel(queryResults, request));
    }
    
    private List<Map<String,String>> entityToMap(List results){
        List<Map<String,String>> list =  new ArrayList<>();
        int row = results.size();
        for(int n=0;n<row ;n++){
            DeviceStatus vo = (DeviceStatus)results.get(n);
            Map<String,String> map = new HashMap<>();
            map.put("lineName", vo.getLineName());
            map.put("stationName", vo.getStationName());
            map.put("dev_type_name", vo.getDev_type_name());
            map.put("device_id", vo.getDevice_id());
            map.put("status_id", vo.getStatus_id());
            map.put("status_name", vo.getStatus_name());
            map.put("acc_status_name", vo.getAcc_status_name());
            map.put("status_datetime", vo.getStatus_datetime());
            list.add(map);
        }
        return list;
    }
}
