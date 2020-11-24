/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.prminfo.controller;

import com.goldsign.acc.app.prminfo.mapper.FareSubmitMapper;
import com.goldsign.acc.app.prminfo.entity.PrmVersion;
import com.goldsign.acc.app.prminfo.mapper.PrmVersionMapper;
import com.goldsign.acc.frame.constant.CommandConstant;
import com.goldsign.acc.frame.constant.LogConstant;
import com.goldsign.acc.frame.constant.ParameterConstant;

import com.goldsign.acc.frame.controller.PrmBaseController;
import com.goldsign.acc.frame.mapper.OperationLogMapper;

import com.goldsign.acc.frame.util.LogUtil;
import com.goldsign.acc.frame.vo.OperationResult;
import com.goldsign.login.vo.User;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.TransactionStatus;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 票价参数提交
 * @author mqf
 */
@Controller
public class FareSubmitController extends PrmBaseController {

    @Autowired
    private FareSubmitMapper fareSubmitMapper;


    @RequestMapping("/FareSubmit")
    public ModelAndView service(HttpServletRequest request, HttpServletResponse response) {

        ModelAndView mv = new ModelAndView("/jsp/prminfo/fare_submit.jsp");

        String command = request.getParameter("command");
        OperationResult opResult = new OperationResult();
        try {
            if (command != null) {
                command = command.trim();
                if (command.equals(CommandConstant.COMMAND_SUBMIT))//提交操作
                {
                    opResult = this.submit(request, this.fareSubmitMapper, this.prmVersionMapper, this.operationLogMapper);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        this.baseHandler(request, response, mv);
//        this.divideResultSet(request, mv, opResult);//结果集分页
        this.SaveOperationResult(mv, opResult);//返回结果集
        return mv;

    }


    private void getVersionNoForSubmit(PrmVersion po, String versionNoMax) {
        String max;
        int n;
        if (versionNoMax != null && versionNoMax.length() == 10) {
            max = versionNoMax.substring(8, 10);
            if (max.length() == 2) {
                n = new Integer(max).intValue();
                n++;
                max = Integer.toString(n);
                if (max.length() == 1) {
                    max = "0" + max;
                }
            }
        } else {
            max = "01";
        }
        String versionNoNew = po.getVersion_valid_date() + max;
        po.setVersion_no_new(versionNoNew);
        po.setRecord_flag_new(po.getRecord_flag_submit());

    }

    

    public int submitByTrans(HttpServletRequest request, FareSubmitMapper fsMapper, PrmVersionMapper pvMapper, PrmVersion po, PrmVersion prmVersion) throws Exception {

        TransactionStatus status = null;
        List<PrmVersion> prmVersions;
        String versionNoMax;
        int n = 0;
        int n1 = 0;
       // String test=null;
        try {

            status = txMgr.getTransaction(this.def);
            
            

            //旧的未来或当前参数数据做删除标志
            submitToOldFlag(fsMapper, po);
            //生成新的未来或当前参数的版本号
            versionNoMax = pvMapper.selectPrmVersionForSubmit(po);
            this.getVersionNoForSubmit(po, versionNoMax);

            //添加新的未来或当前参数的数据记录
            n = submitFromDraftToCurOrFur(fsMapper, po);
            
            PrmVersion pvForFareZone = this.getPrmVersionByParmTypeId(po,ParameterConstant.FARE_ZONE);
            PrmVersion pvForFareTable = this.getPrmVersionByParmTypeId(po,ParameterConstant.FARE_TABLE);
            PrmVersion pvForHolidayPara = this.getPrmVersionByParmTypeId(po,ParameterConstant.HOLIDAY_TABLE);
            PrmVersion pvForOffPeakhours = this.getPrmVersionByParmTypeId(po,ParameterConstant.OFF_PEAK_HOURS);
            PrmVersion pvForFareConf = this.getPrmVersionByParmTypeId(po,ParameterConstant.FARE_CONF);
            
            PrmVersion pvForFareTimeInterval = this.getPrmVersionByParmTypeId(po,ParameterConstant.FARE_TIME_INTERVAL);
            PrmVersion pvForFareDealTotal = this.getPrmVersionByParmTypeId(po,ParameterConstant.FARE_DEAL_TOTAL);
                    
            // 更新参数表中旧的未来或当前参数记录
            this.modifyPrmVersionForSubmit(pvMapper,po,pvForFareZone,pvForFareTable,
                    pvForHolidayPara,pvForOffPeakhours,pvForFareConf,
                    pvForFareTimeInterval,pvForFareDealTotal);
            // 重新生成参数表中的未来或当前参数记录
            this.addPrmVersion(pvMapper,po,pvForFareZone,pvForFareTable,
                    pvForHolidayPara,pvForOffPeakhours,pvForFareConf,
                    pvForFareTimeInterval,pvForFareDealTotal);
            
          

            txMgr.commit(status);
        } catch (Exception e) {
            if (txMgr != null) {
                txMgr.rollback(status);
            }
            throw e;
        }
        return n;
    }
    
    private void submitToOldFlag(FareSubmitMapper fsMapper, PrmVersion po) {
        //旧的未来或当前参数数据做删除标志
//        0401 收费区段
        fsMapper.submitToOldFlagForFareZone(po);
//        0403 票价表
        fsMapper.submitToOldFlagForFareTable(po);
//        0404 节假日定义
        fsMapper.submitToOldFlagForHolidayPara(po);
//        0405 非繁忙时间定义
        fsMapper.submitToOldFlagForOffPeakHours(po);
//        0402 收费配置表
        fsMapper.submitToOldFlagForFareConf(po);
//        0406 联乘时间间隔定义表
        fsMapper.submitToOldFlagForFareTimeInterval(po);
//        0407 累计消费额定义表 
        fsMapper.submitToOldFlagForFareDealToal(po);
    }
    
    private int submitFromDraftToCurOrFur(FareSubmitMapper fsMapper, PrmVersion po) {
        int n = 0;
        //添加新的未来或当前参数的数据记录
//        0401 收费区段
        n = fsMapper.submitFromDraftToCurOrFurForFareZone(po);
//        0403 票价表
        n += fsMapper.submitFromDraftToCurOrFurForFareTable(po);
//        0404 节假日定义
        n += fsMapper.submitFromDraftToCurOrFurForHolidayPara(po);
//        0405 非繁忙时间定义
        n += fsMapper.submitFromDraftToCurOrFurForOffPeakHours(po);
//        0402 收费配置表
        n += fsMapper.submitFromDraftToCurOrFurForFareConf(po);
//        0406 联乘时间间隔定义表
        n += fsMapper.submitFromDraftToCurOrFurForFareTimeInterval(po);
//        0407 累计消费额定义表
        n += fsMapper.submitFromDraftToCurOrFurForFareDealToal(po);
        
        return n;
    }
    
    private void modifyPrmVersionForSubmit(PrmVersionMapper pvMapper, PrmVersion po,
            PrmVersion pvForFareZone, PrmVersion pvForFareTable, PrmVersion pvForHolidayPara,
            PrmVersion pvForOffPeakhours, PrmVersion pvForFareConf,
            PrmVersion pvForFareTimeInterva, PrmVersion pvForFareDealTotal) {
        
        pvMapper.modifyPrmVersionForSubmit(pvForFareZone);
        pvMapper.modifyPrmVersionForSubmit(pvForFareTable);
        pvMapper.modifyPrmVersionForSubmit(pvForHolidayPara);
        pvMapper.modifyPrmVersionForSubmit(pvForOffPeakhours);
        pvMapper.modifyPrmVersionForSubmit(pvForFareConf);
        
        pvMapper.modifyPrmVersionForSubmit(pvForFareTimeInterva);
        pvMapper.modifyPrmVersionForSubmit(pvForFareDealTotal);
        
        pvMapper.modifyPrmVersionForSubmit(po);
        
    }
    
    
    private void addPrmVersion(PrmVersionMapper pvMapper, PrmVersion po,
            PrmVersion pvForFareZone, PrmVersion pvForFareTable, PrmVersion pvForHolidayPara,
            PrmVersion pvForOffPeakhours, PrmVersion pvForFareConf,
            PrmVersion pvForFareTimeInterva, PrmVersion pvForFareDealTotal) {
        
        pvMapper.addPrmVersion(pvForFareZone);
        pvMapper.addPrmVersion(pvForFareTable);
        pvMapper.addPrmVersion(pvForHolidayPara);
        pvMapper.addPrmVersion(pvForOffPeakhours);
        pvMapper.addPrmVersion(pvForFareConf);
        
        pvMapper.addPrmVersion(pvForFareTimeInterva);
        pvMapper.addPrmVersion(pvForFareDealTotal);
        
        pvMapper.addPrmVersion(po);
        
    }
    
    private PrmVersion getPrmVersionByParmTypeId(PrmVersion po, String parmTypeId) {
        PrmVersion prmVersion = new PrmVersion();
        
        prmVersion.setParm_type_id(parmTypeId);
        
        prmVersion.setVersion_no(po.getVersion_no());
        prmVersion.setRecord_flag(po.getRecord_flag());
        prmVersion.setOperator_id(po.getOperator_id());
        
        prmVersion.setBegin_time_submit(po.getBegin_time_submit());
        prmVersion.setEnd_time_submit(po.getEnd_time_submit());
        prmVersion.setRecord_flag_submit(po.getRecord_flag_submit());

        prmVersion.setRecord_flag_old(po.getRecord_flag_old());
        prmVersion.setVersion_valid_date(po.getVersion_valid_date());//版本的生效日期格式YYYMMDD
        prmVersion.setBegin_time_new(po.getBegin_time_new());//版本的生效的开始时间
        prmVersion.setEnd_time_new(po.getEnd_time_new());//版本的生效的结束时间
        prmVersion.setRecord_flag_new(po.getRecord_flag_new());
        
        prmVersion.setVersion_no_new(po.getVersion_no_new());
        
        prmVersion.setRemark(po.getRemark()); //版本备注
        prmVersion.setApp_line_name(po.getApp_line_name()); //应用线路： “未下发”
        
        return prmVersion;
    }

    
    public OperationResult submit(HttpServletRequest request, FareSubmitMapper fsMapper, PrmVersionMapper pvMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        PrmVersion po = this.getReqAttributeForSubmit(request);
        PrmVersion prmVersion = null;
        //User user = (User) request.getSession().getAttribute("User");
        // String operatorID = user.getAccount();
        LogUtil logUtil = new LogUtil();
        int n = 0;

        try {
            if (!this.existAllFarePara(po, fsMapper)) {
                rmsg.addMessage("提交失败，没有生成草稿参数！");
                return rmsg;
            }
            n = this.submitByTrans(request, fsMapper, pvMapper, po, prmVersion);

        } catch (Exception e) {
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_SUBMIT, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_SUBMIT, request, LogConstant.submitSuccessMsg(n, po.getRecord_flag_new()), opLogMapper);

        rmsg.addMessage(LogConstant.submitSuccessMsg(n, po.getRecord_flag_new()));

        return rmsg;
    }

    public PrmVersion getReqAttributeForSubmit(HttpServletRequest request) {
        PrmVersion po = new PrmVersion();
        //'0400' 草稿版本 提交
        po.setParm_type_id(ParameterConstant.FARE_ALL);
        po.setVersion_no(ParameterConstant.VERSION_NO_DRAFT);
        po.setRecord_flag(ParameterConstant.RECORD_FLAG_DRAFT);
        User user = (User) request.getSession().getAttribute("User");
        po.setOperator_id(user.getAccount());

        this.getBaseParametersForSubmit(request, po);

        return po;
    }
    
    private boolean existAllFarePara(PrmVersion po, FareSubmitMapper fcMapper) {
        boolean result = false;
        if (this.existFarePara(fcMapper, ParameterConstant.FARE_ZONE)
                && this.existFarePara(fcMapper, ParameterConstant.FARE_CONF)
                && this.existFarePara(fcMapper, ParameterConstant.FARE_TABLE)
                && this.existFarePara(fcMapper, ParameterConstant.HOLIDAY_TABLE)
                && this.existFarePara(fcMapper, ParameterConstant.OFF_PEAK_HOURS)
                //20180528 mqf 增加联乘时间间隔定义表 0406、累计消费额定义表 0407
                && this.existFarePara(fcMapper, ParameterConstant.FARE_TIME_INTERVAL)
                && this.existFarePara(fcMapper, ParameterConstant.FARE_DEAL_TOTAL)) {
            result = true;
        }
        return result;
    }
    
    private boolean existFarePara(FareSubmitMapper fcMapper, String parmTypeId) {
        
        List<PrmVersion> list = fcMapper.selectPrmVersionForDraft(parmTypeId);
        if (list.isEmpty()) {
            return false;
        }
        return true;
    }


}
