/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.frame.controller;

import com.goldsign.acc.frame.entity.PubFlag;
import com.goldsign.acc.frame.util.DateUtil;
import com.goldsign.acc.frame.util.FormUtil;
import com.goldsign.acc.frame.util.PageControlUtil;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author hejj
 */
public class RLBaseController extends BaseController {

    protected static String RECORDFLAG = "recordFlags";//版本状态
    protected static String LINE = "lines";//线路
    protected static String STATION = "stations";//车站
    protected static String LINE_STATIONS = "lineStations";//线路车站

    protected void baseHandlerForOutIn(HttpServletRequest request, HttpServletResponse response, ModelAndView modelView) {
        String operType = FormUtil.getParameter(request, "operType");
        String queryCondition = FormUtil.getParameter(request, "queryCondition");
        String billRecordFlag = FormUtil.getParameter(request, "billRecordFlag");
        if (operType != null && operType.length() != 0) {
            modelView.addObject("OperType", operType);
        }
        if (queryCondition != null && queryCondition.length() != 0) {
            modelView.addObject("QueryCondition", queryCondition);
        }
        if (billRecordFlag != null && billRecordFlag.length() != 0) {
            modelView.addObject("BillRecordFlag", billRecordFlag);
        }

    }

    protected void setPageOptions(String[] attrNames, ModelAndView mv, HttpServletRequest request, HttpServletResponse response) {
        List<PubFlag> options;
        for (String attrName : attrNames) {
            if (attrName.equals(RECORDFLAG)) {//版本状态  
                options = pubFlagMapper.getCode("30");
                mv.addObject(attrName, options);
                continue;
            }
            if (attrName.equals((LINE))) {
                options = pubFlagMapper.getLines();
                mv.addObject(attrName, options);
                continue;
            }
            if (attrName.equals((STATION))) {
                options = pubFlagMapper.getStations();
                mv.addObject(attrName, options);
                continue;
            }
            if (attrName.equals((LINE_STATIONS))) {
                options = pubFlagMapper.getStations();
                String lineStations = FormUtil.getLineStations(options);
                mv.addObject(attrName, lineStations);
                continue;
            }
        }
    }

    protected void setOperatorId(ModelAndView mv, HttpServletRequest request) {
        String operatorId = PageControlUtil.getOperatorFromSession(request);
        mv.addObject("OperatorID", operatorId);
    }

}
