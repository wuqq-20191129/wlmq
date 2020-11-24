/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.weight.controller;

import com.goldsign.acc.app.weight.entity.DistanceProportion;
import com.goldsign.acc.app.weight.entity.GenerateData;
import com.goldsign.acc.app.weight.mapper.GenerateDataMapper;
import com.goldsign.acc.frame.constant.CommandConstant;
import com.goldsign.acc.frame.controller.RLBaseController;
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
 *里程权重比例查询
 * @author liudz
 */
@Controller
public class DistanceProportionController extends RLBaseController{
     @Autowired
    private GenerateDataMapper gdMapper;

    @RequestMapping(value = "/distanceProportionAction")
    public ModelAndView service(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView("/jsp/weight/distance_proportion.jsp");
        String command = request.getParameter("command");
        OperationResult opResult = new OperationResult();
        try {
            if (command != null) {
                command = command.trim();
                if (command.equals(CommandConstant.COMMAND_QUERY))//查询操作
                {
                    opResult = this.query(request, this.gdMapper, this.operationLogMapper);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //初始化下拉框
        String[] attrNames = {LINE, STATION, LINE_STATIONS};

        this.setPageOptions(attrNames, mv, request, response);//设置页面线路、车站等选项值、版本
        this.getResultSetText((List<DistanceProportion>) opResult.getReturnResultSet(), mv);
        this.baseHandler(request, response, mv);
        this.divideResultSet(request, mv, opResult);//结果集分页
        this.SaveOperationResult(mv, opResult);

        return mv;
    }

    private OperationResult query(HttpServletRequest request, GenerateDataMapper gdMapper, OperationLogMapper operationLogMapper) throws Exception {
        LogUtil logUtil = new LogUtil();       
        OperationResult or = new OperationResult();
        DistanceProportion queryCondition;
//        Map<String,Object> queryCondition;
        List<DistanceProportion> resultSet;
        try {
            queryCondition = this.getQueryCondition(request);
           resultSet= gdMapper.queryStore(queryCondition);
//            resultSet = (List<DistanceProportion>) queryCondition.get("result");
            or.setReturnResultSet(resultSet);
            or.addMessage("成功查询" + resultSet.size() + "条记录");
        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, operationLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + resultSet.size() + "条记录", operationLogMapper);
        return or;
    }

    
public DistanceProportion getQueryCondition(HttpServletRequest request){
        DistanceProportion info = new DistanceProportion();
        
        info.setoLineId(FormUtil.getParameter(request, "q_oLineId"));
        info.setoStationId(FormUtil.getParameter(request, "q_oStationId"));
        info.setdLineId(FormUtil.getParameter(request, "q_dLineId"));
        info.setdStationId(FormUtil.getParameter(request, "q_dStationId"));
        
        return info;
    }
//    private Map<String,Object> getQueryCondition(HttpServletRequest request) {
//        Map<String,Object> dp = new HashMap<String,Object>();
//        dp.put("o_line_id",FormUtil.getParameter(request, "q_oLineId"));
//        dp.put("o_station_id",FormUtil.getParameter(request, "q_oStationId"));
//        dp.put("d_line_id",FormUtil.getParameter(request, "q_dLineId"));
//        dp.put("d_station_id",FormUtil.getParameter(request, "q_dStationId"));
//
//        return dp;
//
//    }

    private void getResultSetText(List<DistanceProportion> resultSet, ModelAndView mv) {
        //线路名
        List<PubFlag> lines = (List<PubFlag>) mv.getModelMap().get(RLBaseController.LINE);
        //车站名
        List<PubFlag> stations = (List<PubFlag>) mv.getModelMap().get(RLBaseController.STATION);
        //版本状态
        List<PubFlag> recordFlags = (List<PubFlag>) mv.getModelMap().get(RLBaseController.RECORDFLAG);
        for (DistanceProportion dl : resultSet) {
            if (dl.getoLineId()!= null && !dl.getoLineId().isEmpty()) {
                dl.setoLineIdText(DBUtil.getTextByCode(dl.getoLineId(), lines));
            }

            if (dl.getdLineId()!= null && !dl.getdLineId().isEmpty()) {
                dl.setdLineIdText(DBUtil.getTextByCode(dl.getdLineId(), lines));
            }
            if (dl.getDispartLineId()!= null && !dl.getDispartLineId().isEmpty()) {
                dl.setDispartLineIdText(DBUtil.getTextByCode(dl.getDispartLineId(), lines));
            }
            
            //分账比例为小于零的小数时加上前面的0,为1时加上后面的0
            if (".".equals(dl.getInPrecent().substring(0, 1))) {
                dl.setInPrecent("0" + dl.getInPrecent());
            } else if ("1".equals(dl.getInPrecent().substring(0, 1))) {
                dl.setInPrecent(dl.getInPrecent().substring(0, 1) + ".000000");
            }

            if (stations != null && !stations.isEmpty()) {
                if (dl.getoStationId()!= null && !dl.getoStationId().isEmpty()) {
                    dl.setoStationIdText(DBUtil.getTextByCode(dl.getoStationId(), dl.getoLineId(), stations));
                }
                if (dl.getdStationId()!= null && !dl.getdStationId().isEmpty()) {
                    dl.setdStationIdText(DBUtil.getTextByCode(dl.getdStationId(), dl.getdLineId(), stations));
                }
            }
              if (dl.getRecordFlag()!= null && !dl.getRecordFlag().isEmpty()) {
                dl.setRecordFlagText(DBUtil.getTextByCode(dl.getRecordFlag(), recordFlags));
            }

            
        }
    }
    
     @RequestMapping("/DistanceProportionActionExportAll")
    public void exportExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {

        List results = this.getBufferElementsForCurClass(request,"com.goldsign.acc.app.weight.entity.DistanceProportion");
        List<Map<String,String>> queryResults = this.entityToMap(results);
        ExpUtil.renderExportExcelPath(response, ExpUtil.exportExcel(queryResults, request));
    }

    private List<Map<String, String>> entityToMap(List results) {
        List<Map<String,String>> list =  new ArrayList<>();
        int row = results.size();
        for(int n=0;n<row ;n++){
            DistanceProportion vo = (DistanceProportion)results.get(n);
            Map<String,String> map = new HashMap<>();
            map.put("oLineIdText", vo.getoLineIdText());
            map.put("oStationIdText", vo.getoStationIdText());
            map.put("dLineIdText", vo.getdLineIdText());
            map.put("dStationIdText", vo.getdStationIdText());
            map.put("dispartLineIdText", vo.getDispartLineIdText());
            map.put("inPrecent", vo.getInPrecent());
            list.add(map);
        }
        return list;
    }

    
}

