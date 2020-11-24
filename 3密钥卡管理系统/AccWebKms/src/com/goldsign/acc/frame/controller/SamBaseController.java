/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.frame.controller;

import com.goldsign.acc.frame.constant.PubFlagConstant;
import com.goldsign.acc.frame.entity.PubFlag;
import com.goldsign.acc.frame.mapper.BillMapper;
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
public class SamBaseController extends BaseController {

    public static final String OPER_TYPE_PLAN = "plan";
    public static final String OPER_TYPE_PLAN_DETAIL = "planDetail";
    public static final String OPER_TYPE_OUT_BILL = "outBill";
    public static final String OPER_TYPE_OUT_BILL_DETAIL = "outBillDetail";
    public static final String OPER_TYPE_OUT_LINE_CONTENT = "lineContent";
    public static final String OPER_TYPE_ORDER = "order";
    public static final String IN_TYPE_XR = "XR";//新票入库标识
    protected static String SAM_TYPES = "samTypes";           //SAM类型
    protected static String PRODUCETYPES = "produceTypes";   //产品类型
    protected static String STOCKSTATES = "stockStates";     //库存状态
    protected static String ISINSTOCKS = "isInstocks";       //是否在库
    protected static String ISBADS = "isBads";               //是否损坏
    protected static String BILL_STATUES = "billStatues";   //单剧状态
    protected static String LINE_ES = "linees";   //ES线路
    protected static String RECORD_FLAG_IN_STOCK_STATE = "inStockStates";   //回库状态
    protected static String RECORD_FLAG_BILL_FINISH = "billFinishs";   //订单完成状态
    protected static String DISTRIBUTE_PLACE = "distributePlace";//分发目的地
    protected static String LineEsTypes = "lineEsTypes"; //线路es类型
    protected static String ISSUE_ORDER_NO = "issueOrderNo";//卡发行单号
    protected static String SAM_TYPE_DESCS = "samTypeDescs";           //卡类型定义名称
    protected static String OPERATORS = "operators";     //操作员

    public boolean isOperForPlan(String operType) {
        if (operType == null || operType.length() == 0) {
            return true;
        }
        if (operType.equals(this.OPER_TYPE_PLAN)) {
            return true;
        }
        return false;
    }

    public boolean isOperForPlanDetail(String operType) {
        if (operType == null || operType.length() == 0) {
            return false;
        }
        if (operType.equals(this.OPER_TYPE_PLAN_DETAIL)) {
            return true;
        }
        return false;
    }

    public boolean isOperForOutBill(String operType) {
        if (operType == null || operType.length() == 0) {
            return false;
        }
        if (operType.equals(this.OPER_TYPE_OUT_BILL)) {
            return true;
        }
        return false;
    }

    public boolean isOperForOutBillDetail(String operType) {
        if (operType == null || operType.length() == 0) {
            return false;
        }
        if (operType.equals(this.OPER_TYPE_OUT_BILL_DETAIL)) {
            return true;
        }
        return false;
    }

    public boolean isOperForOrder(String operType) {
        if (operType == null || operType.length() == 0) {
            return false;
        }
        if (operType.equals(this.OPER_TYPE_ORDER)) {
            return true;
        }
        return false;
    }

    public boolean isOperTypeForXR(String operType) {
        if (operType == null || operType.length() == 0) {
            return true;
        }
        if (operType.equals(this.IN_TYPE_XR)) {
            return true;
        }
        return false;
    }

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
        String optionsSerial;

        for (String attrName : attrNames) {
            if (attrName.equals(SAM_TYPES)) {//SAM类型
                options = pubFlagMapper.getSamType();
                mv.addObject(attrName, options);
                continue;
            }
            if (attrName.equals(PRODUCETYPES)) {//产品类型
                options = pubFlagMapper.getProduceType();
                mv.addObject(attrName, options);
                continue;
            }
            if (attrName.equals(STOCKSTATES)) {//库存状态
                options = pubFlagMapper.getStockState();
                mv.addObject(attrName, options);
                continue;
            }
            if (attrName.equals(ISINSTOCKS)) {//是否在库
                options = pubFlagMapper.getIsInstock();
                mv.addObject(attrName, options);
                continue;
            }
            if (attrName.equals(ISBADS)) {//是否损坏
                options = pubFlagMapper.getIsBad();
                mv.addObject(attrName, options);
                continue;
            }
            if (attrName.equals(BILL_STATUES)) {//单据状态
                options = pubFlagMapper.getCodesByType(PubFlagConstant.PUB_FLAG_TYPE_BILL_STATE);
                mv.addObject(attrName, options);
                continue;
            }
            if (attrName.equals(LINE_ES)) {//ES线路
                options = pubFlagMapper.getLineEs();
                mv.addObject(attrName, options);
                continue;
            }
            if (attrName.equals(RECORD_FLAG_IN_STOCK_STATE)) {//回库状态
                options = pubFlagMapper.getInStockStates();
                mv.addObject(attrName, options);
                continue;
            }
            if (attrName.equals(RECORD_FLAG_BILL_FINISH)) {//回库状态
                options = pubFlagMapper.getBillFinishs();
                mv.addObject(attrName, options);
                continue;
            }
            if (attrName.equals(DISTRIBUTE_PLACE)) {//分发目的地
                options = pubFlagMapper.getDistributePlace();
                mv.addObject(attrName, options);
                continue;
            }
            if (attrName.equals(LineEsTypes)) {//线路es类型ysw
                options = pubFlagMapper.getLineEsType();
                mv.addObject(attrName, options);
                continue;
            }
            if (attrName.equals(ISSUE_ORDER_NO)) {//卡发行单号
                options = pubFlagMapper.getIssueOrderNo();
                mv.addObject(attrName, options);
                continue;
            }
            if (attrName.equals(SAM_TYPE_DESCS)) {//卡类型定义名称
                options = pubFlagMapper.getSamTypeDescs();
                mv.addObject(attrName, options);
                continue;
            }
            if (attrName.equals(OPERATORS)) {//操作员
                options = pubFlagMapper.getOperators();
                mv.addObject(attrName, options);
                continue;
            }
        }

    }

    public String getBillNoTemp(BillMapper billMapper, String billType) {
        HashMap<String, String> parmMap = new HashMap();
        parmMap.put("piBillMainType", billType);
        billMapper.getBillNoTemp(parmMap);

        String billNo = parmMap.get("poBillNo");
        return billNo;
        // parmMap.put("piBillMainType", InOutConstant.TYPE_BILL_NO_PRODUCE_PLAN_TEMP);
    }

    protected void setOperatorId(ModelAndView mv, HttpServletRequest request) {
        String operatorId = PageControlUtil.getOperatorFromSession(request);
        mv.addObject("OperatorID", operatorId);
    }

    public boolean isOutByLogical(String startLogicalId, String endLogicalId) {
        if (startLogicalId != null && endLogicalId != null && startLogicalId.length() != 0 && endLogicalId.length() != 0) {
            return true;
        }
        return false;
    }

    /**
     * 取起始逻辑卡号后7位
     *
     * @param stLogicNo 起始逻辑卡号
     * @return
     */
    public String getStLogicNoFive(String stLogicNo) {
        return stLogicNo.substring(stLogicNo.length() - 7);
    }

    /**
     * 取起始逻辑卡号前9位
     *
     * @param stLogicNo 起始逻辑卡号
     * @return
     */
    public String getStLogicNoEle(String stLogicNo) {
        return stLogicNo.substring(0, 9);
    }

}
