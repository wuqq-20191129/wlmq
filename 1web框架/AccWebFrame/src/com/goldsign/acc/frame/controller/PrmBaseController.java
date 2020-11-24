/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.frame.controller;

import com.goldsign.acc.app.prmdev.entity.StationDevice;
import com.goldsign.acc.app.prminfo.entity.PrmVersion;
import com.goldsign.acc.frame.constant.ParameterConstant;
import com.goldsign.acc.frame.entity.PubFlag;
import com.goldsign.acc.frame.util.FormUtil;
import com.goldsign.login.vo.User;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author hejj
 */
public class PrmBaseController extends BaseController {

    protected static String LINES = "lines";
    protected static String STATIONS = "stations";
    protected static String LINE_STATIONS = "lineStations";
    protected static String DEV_TYPES = "devTypes";
    protected static String MERCHANTS = "merchants";
    protected static String VERSION = "version";

    protected void getRecordFlagName(PrmVersion version, PubFlag recordFlagName) {
        if (version.getRecord_flag().equals(recordFlagName.getCode())) {
            version.setRecord_flag_name(recordFlagName.getCode_text());
        }

    }

    protected PrmVersion getPrmVersion(HttpServletRequest request, HttpServletResponse response) {
        PrmVersion vo = new PrmVersion();

        vo.setParm_type_id(FormUtil.getParameter(request, "Type"));
        vo.setRecord_flag(FormUtil.getParameter(request, "VersionType"));
        vo.setVersion_no(FormUtil.getParameter(request, "Version"));
        vo.setBegin_time(FormUtil.getParameter(request, "StartDate"));
        vo.setEnd_time(FormUtil.getParameter(request, "EndDate"));

        return vo;

    }

    protected void setPageOptions(String[] attrNames, ModelAndView mv, HttpServletRequest request, HttpServletResponse response) {

        List<PubFlag> options;
        for (String attrName : attrNames) {
            if (attrName.equals(LINES)) {//线路
                options = pubFlagMapper.getLines();
                mv.addObject(attrName, options);
                continue;
            }
            if (attrName.equals(STATIONS)) {//车站
                options = pubFlagMapper.getStations();
                mv.addObject(attrName, options);
                continue;
            }
            if (attrName.equals(LINE_STATIONS)) {//线路车站
                options = pubFlagMapper.getStations();
                String lineStations = FormUtil.getLineStations(options);
                mv.addObject(attrName, lineStations);
                continue;
            }
            if (attrName.equals(DEV_TYPES)) {//设备类型
                options = pubFlagMapper.getDevTypes();
                mv.addObject(attrName, options);
                continue;
            }
            if (attrName.equals(MERCHANTS)) {//商户
                options = pubFlagMapper.getMerchants();
                mv.addObject(attrName, options);
                continue;
            }
            if (attrName.equals(VERSION)) {//参数版本
                PrmVersion version = this.getPrmVersion(request, response);
                PubFlag recordFlagName = pubFlagMapper.getRecordFlags(version.getRecord_flag());
                this.getRecordFlagName(version, recordFlagName);
                mv.addObject(attrName, version);
                continue;
            }

        }
    }
    private String getRecordFlagOld( PrmVersion prmVersion){
         String recordFlagSubmit = prmVersion.getRecord_flag_submit();
         String recordFlagOld ="";
        if (recordFlagSubmit.equals(ParameterConstant.RECORD_FLAG_CURRENT)) {
            recordFlagOld = ParameterConstant.RECORD_FLAG_HISTORY;
        }
        if (recordFlagSubmit.equals(ParameterConstant.RECORD_FLAG_FUTURE)) {
            recordFlagOld = ParameterConstant.RECORD_FLAG_DELETED;
        }
        return recordFlagOld;
        
    }
     private String convertValidDate(String validDate) {
        if (validDate == null || validDate.trim().length() == 0 || validDate.trim().length() != 10) {
            return validDate;
        }
        int index = validDate.indexOf("-");
        int index1 = validDate.indexOf("-", index + 1);
        return validDate.substring(0, index) + validDate.substring(index + 1, index1) + validDate.substring(index1 + 1);
    }
//    private String getVersionValidDate(StationDevice stationDevice){
//        String verDateBegin =stationDevice.getBegin_time_submit();
//        return this.convertValidDate(verDateBegin);
//        
//    }

    protected void getBaseParametersForSubmit(HttpServletRequest request, PrmVersion prmVersion) {
        prmVersion.setBegin_time_submit(FormUtil.getParameter(request, "ver_date_begin"));
        prmVersion.setEnd_time_submit(FormUtil.getParameter(request, "ver_date_end"));
        prmVersion.setRecord_flag_submit(FormUtil.getParameter(request, "ver_generate"));
        prmVersion.setRemark(FormUtil.getParameter(request, "ver_remark")); //版本备注
        
        
        prmVersion.setRecord_flag_old(this.getRecordFlagOld(prmVersion));
        prmVersion.setVersion_valid_date(this.convertValidDate(prmVersion.getBegin_time_submit()));//版本的生效日期格式YYYMMDD
        prmVersion.setBegin_time_new(prmVersion.getBegin_time_submit()+" 00:00:00");//版本的生效的开始时间
        prmVersion.setEnd_time_new(prmVersion.getEnd_time_submit()+" 23:59:59");//版本的生效的结束时间
        prmVersion.setRecord_flag_new(prmVersion.getRecord_flag_submit());
        
        
    }

    protected void getBaseParameters(HttpServletRequest request, PrmVersion prmVersion) {
        prmVersion.setVersion_no(FormUtil.getParameter(request, "Version"));
        prmVersion.setRecord_flag(FormUtil.getParameter(request, "VersionType"));
        prmVersion.setParm_type_id(FormUtil.getParameter(request, "Type"));
        User user = (User) request.getSession().getAttribute("User");
        prmVersion.setOperator_id(user.getAccount());

        /*
        version = request.getParameter("Version");
        type = request.getParameter("Type");
        verStartDate = request.getParameter("StartDate");
        verEndDate = request.getParameter("EndDate");
        versionType = request.getParameter("VersionType");

        startDate = request.getParameter("ver_date_begin");
        endDate = request.getParameter("ver_date_end");
        verFlag = request.getParameter("ver_generate");
         */
    }

}
