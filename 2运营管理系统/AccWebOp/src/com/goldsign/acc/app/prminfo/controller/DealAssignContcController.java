/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.prminfo.controller;

import com.goldsign.acc.app.prminfo.entity.DealAssignContc;
import com.goldsign.acc.app.prminfo.entity.DealAssignLine;
import com.goldsign.acc.app.prminfo.entity.HandleMessageBase;
import com.goldsign.acc.app.prminfo.entity.OpLogImport;
import com.goldsign.acc.app.prminfo.entity.PrmVersion;
import com.goldsign.acc.app.prminfo.mapper.DealAssignContcMapper;
import com.goldsign.acc.app.prminfo.mapper.PrmVersionMapper;
import com.goldsign.acc.frame.constant.CommandConstant;
import com.goldsign.acc.frame.constant.ImportConstant;
import com.goldsign.acc.frame.constant.LogConstant;
import com.goldsign.acc.frame.constant.ParameterConstant;
import com.goldsign.acc.frame.controller.PrmBaseController;
import com.goldsign.acc.frame.entity.PubFlag;
import com.goldsign.acc.frame.mapper.OperationLogMapper;
import com.goldsign.acc.frame.mapper.PubFlagMapper;
import com.goldsign.acc.frame.util.DBUtil;
import com.goldsign.acc.frame.util.ExpUtil;
import com.goldsign.acc.frame.util.FormUtil;
import com.goldsign.acc.frame.util.LogUtil;
import com.goldsign.acc.frame.vo.ImportResult;
import com.goldsign.acc.frame.vo.OperationResult;
import com.goldsign.lib.db.util.DbHelper;
import com.goldsign.login.vo.User;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.Vector;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.TransactionStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author liudezeng 运营商交易分配比例
 */
@Controller
public class DealAssignContcController extends PrmBaseController {

    Logger logger = Logger.getLogger(DealAssignContcController.class);
    @Autowired
    private DealAssignContcMapper dealAssignContcMapper;

    @Autowired
    private PubFlagMapper pubFlagMapper;

    @RequestMapping("/DealAssignContc")
    public ModelAndView service(HttpServletRequest request, HttpServletResponse response) {

        ModelAndView mv = new ModelAndView("/jsp/prminfo/deal_assign_contc.jsp");

        String command = request.getParameter("command");
        OperationResult opResult = new OperationResult();

        try {
            if (command != null) {
                command = command.trim();
                if (command.equals(CommandConstant.COMMAND_MODIFY))//修改操作
                {
                    opResult = this.modify(request, this.dealAssignContcMapper, this.prmVersionMapper, this.operationLogMapper);
                }
                if (command.equals(CommandConstant.COMMAND_DELETE))//删除操作
                {
                    opResult = this.delete(request, this.dealAssignContcMapper, this.prmVersionMapper, this.operationLogMapper);
                }
                if (command.equals(CommandConstant.COMMAND_ADD))//增加操作
                {
                    opResult = this.add(request, this.dealAssignContcMapper, this.prmVersionMapper, this.operationLogMapper);
                }

                if (command.equals(CommandConstant.COMMAND_QUERY))//查询操作
                {
                    opResult = this.query(request, this.dealAssignContcMapper, this.operationLogMapper);

                }
                if (command.equals(CommandConstant.COMMAND_SUBMIT))//提交操作
                {
                    opResult = this.submit(request, this.dealAssignContcMapper, this.prmVersionMapper, this.operationLogMapper);
                }
                if (command.equals("importContc"))//打开导入页面操作
                {
                    mv = new ModelAndView("/jsp/prminfo/importDealContcPara.jsp");
                    return mv;
                }
                if (command.equals(CommandConstant.COMMAND_CLONE))//克隆操作
                {
                    opResult = this.clone(request, this.dealAssignContcMapper, this.prmVersionMapper, this.operationLogMapper);
                }
            }

            if (command != null) {
                if (command.equals(CommandConstant.COMMAND_ADD) || command.equals(CommandConstant.COMMAND_DELETE) || command.equals(CommandConstant.COMMAND_MODIFY)
                        || command.equals(CommandConstant.COMMAND_CLONE) || command.equals(CommandConstant.COMMAND_SUBMIT)
                        ||command.equals(CommandConstant.COMMAND_NEXT) || command.equals(CommandConstant.COMMAND_NEXTEND) || command.equals(CommandConstant.COMMAND_BACK)
                        || command.equals(CommandConstant.COMMAND_BACKEND)) {
                    if (!command.equals(CommandConstant.COMMAND_ADD)) {
                        this.saveQueryControlDefaultValues(request, mv);//保存查询条件
                    }
                    this.queryForOp(request, this.dealAssignContcMapper, this.operationLogMapper, opResult);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        //初始化下拉框
        String[] attrNames = {LINES, STATIONS, CONTCS, LINE_STATIONS, VERSION};

        this.setPageOptions(attrNames, mv, request, response);//设置页面线路、车站等选项值、版本
        this.getResultSetText((List<DealAssignContc>) opResult.getReturnResultSet(), mv);
        this.baseHandler(request, response, mv);
        this.divideResultSet(request, mv, opResult);//结果集分页
        this.SaveOperationResult(mv, opResult);//返回结果集
        this.SaveOperationResult(mv, opResult);
        return mv;

    }

    private void getResultSetText(List<DealAssignContc> resultSet, ModelAndView mv) {
        //线路名
        List<PubFlag> lines = (List<PubFlag>) mv.getModelMap().get(PrmBaseController.LINES);
        //车站名
        List<PubFlag> stations = (List<PubFlag>) mv.getModelMap().get(PrmBaseController.STATIONS);
        //运营商
        List<PubFlag> contcs = (List<PubFlag>) mv.getModelMap().get(PrmBaseController.CONTCS);
        for (DealAssignContc dc : resultSet) {
            if (lines != null && !lines.isEmpty()) {
                if (dc.getB_line_id() != null && !dc.getB_line_id().isEmpty()) {
                    dc.setB_line_id_name(DBUtil.getTextByCode(dc.getB_line_id(), lines));
                }

                if (dc.getE_line_id() != null && !dc.getE_line_id().isEmpty()) {
                    dc.setE_line_id_name(DBUtil.getTextByCode(dc.getE_line_id(), lines));
                }
            }
            if (contcs != null && !contcs.isEmpty()) {
                if (dc.getContc_id() != null && !dc.getContc_id().isEmpty()) {
                    dc.setContc_id_name(DBUtil.getTextByCode(dc.getContc_id(), contcs));
                }
            }
            //分账比例为小于零的小数时加上前面的0,为1时加上后面的0
            if (".".equals(dc.getIn_percent().substring(0, 1))) {
                dc.setIn_percent("0" + dc.getIn_percent());
                if (dc.getIn_percent().length() < 8) {
                    DecimalFormat df = new DecimalFormat("0.000000");
//                    System.out.println(df.format(0.34)); 
                    double num1 ;  
                    num1 = Double.valueOf(dc.getIn_percent().toString());
                    dc.setIn_percent(df.format(num1));
                }
            } else if ("1".equals(dc.getIn_percent().substring(0, 1))) {
                dc.setIn_percent(dc.getIn_percent().substring(0, 1) + ".000000");
            }else if ("0".equals(dc.getIn_percent().substring(0, 1))&&dc.getIn_percent().length()==1) {
                dc.setIn_percent(dc.getIn_percent().substring(0, 1) + ".000000");
            }
            if (stations != null && !stations.isEmpty()) {
                if (dc.getB_station_id() != null && !dc.getB_station_id().isEmpty()) {
                    dc.setB_station_id_name(DBUtil.getTextByCode(dc.getB_station_id(), dc.getB_line_id(), stations));
                }
                if (dc.getE_station_id() != null && !dc.getE_station_id().isEmpty()) {
                    dc.setE_station_id_name(DBUtil.getTextByCode(dc.getE_station_id(), dc.getE_line_id(), stations));
                }
            }
        }
    }

    private DealAssignContc getQueryConditionForOp(HttpServletRequest request) {
        DealAssignContc qCon = new DealAssignContc();
        HashMap vQueryControlDefaultValues = null;
        String queryControlDefaultValues = request.getParameter("queryControlDefaultValues");
        String command = request.getParameter("command");
        if (FormUtil.isAddOrModifyMode(request)) {//操作处于添加或修改模式
            if (command.equals(CommandConstant.COMMAND_DELETE)) {
                return null;
            }
            qCon.setB_line_id(FormUtil.getParameter(request, "d_b_line_id"));
            qCon.setB_station_id(FormUtil.getParameter(request, "d_b_station_id"));
            qCon.setE_line_id(FormUtil.getParameter(request, "d_e_line_id"));
            qCon.setE_station_id(FormUtil.getParameter(request, "d_e_station_id"));
            qCon.setContc_id(FormUtil.getParameter(request, "d_line_id_dispart"));
            qCon.setIn_percent(FormUtil.getParameter(request, "d_in_percent"));

        } else {//操作处于非添加模式
            vQueryControlDefaultValues = FormUtil.getQueryControlDefaultValues(queryControlDefaultValues);
            if (!vQueryControlDefaultValues.isEmpty()) {
                qCon.setB_line_id(FormUtil.getQueryControlDefaultValue(vQueryControlDefaultValues, "q_b_line_id"));
                qCon.setB_station_id(FormUtil.getQueryControlDefaultValue(vQueryControlDefaultValues, "q_b_station_id"));
                qCon.setE_line_id(FormUtil.getQueryControlDefaultValue(vQueryControlDefaultValues, "q_e_line_id"));
                qCon.setE_station_id(FormUtil.getQueryControlDefaultValue(vQueryControlDefaultValues, "q_e_station_id"));
                qCon.setContc_id(FormUtil.getQueryControlDefaultValue(vQueryControlDefaultValues, "q_contc_id"));
            }
        }
        this.getBaseParameters(request, qCon);
        //当操作为克隆时，查询克隆后的草稿版本
        if (command.equals(CommandConstant.COMMAND_CLONE)) {
            qCon.setRecord_flag(ParameterConstant.RECORD_FLAG_DRAFT);
        }

        return qCon;
    }

    //获取查询参数
    private DealAssignContc getQueryCondition(HttpServletRequest request) {
        DealAssignContc dealAssignContc = new DealAssignContc();
        String q_b_line_id = FormUtil.getParameter(request, "q_b_line_id");
        String q_b_station_id = FormUtil.getParameter(request, "q_b_station_id");
        String q_e_line_id = FormUtil.getParameter(request, "q_e_line_id");
        String q_e_station_id = FormUtil.getParameter(request, "q_e_station_id");
        String q_contc_id = FormUtil.getParameter(request, "q_contc_id");
        dealAssignContc.setB_line_id(q_b_line_id);
        dealAssignContc.setB_station_id(q_b_station_id);
        dealAssignContc.setE_line_id(q_e_line_id);
        dealAssignContc.setE_station_id(q_e_station_id);
        dealAssignContc.setContc_id(q_contc_id);

//        //字段回显
//        request.setAttribute("q_line_id", q_line_id);
//        request.setAttribute("q_contc_id", q_contc_id);
        this.getBaseParameters(request, dealAssignContc);
        return dealAssignContc;
    }

    public OperationResult query(HttpServletRequest request, DealAssignContcMapper dealAssignContcMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult or = new OperationResult();
        LogUtil logUtil = new LogUtil();
        DealAssignContc queryCondition;
        List<DealAssignContc> resultSet;
        try {
            queryCondition = this.getQueryCondition(request);
            resultSet = dealAssignContcMapper.getDealAssignContc(queryCondition);
            or.setReturnResultSet(resultSet);
            or.addMessage("成功查询" + resultSet.size() + "条记录");
        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + resultSet.size() + "条记录", opLogMapper);
        return or;

    }

    /**
     * 带查询条件查询线路记录
     */
    public OperationResult queryForOp(HttpServletRequest request, DealAssignContcMapper dealAssignContcMapper, OperationLogMapper opLogMapper, OperationResult opResult) throws Exception {
        LogUtil logUtil = new LogUtil();
        DealAssignContc dealAssignContc;
        List<DealAssignContc> resultSet;

        try {
            dealAssignContc = this.getQueryConditionForOp(request);
            if (dealAssignContc == null) {
                return null;
            }
            resultSet = dealAssignContcMapper.getDealAssignContc(dealAssignContc);
            opResult.setReturnResultSet(resultSet);
        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + resultSet.size() + "条记录", opLogMapper);
        return opResult;
    }

    public OperationResult modify(HttpServletRequest request, DealAssignContcMapper dealAssignContcMapper, PrmVersionMapper pvMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        DealAssignContc dealAssignContc = this.getReqAttribute(request);
        LogUtil logUtil = new LogUtil();
        int n = 0;

        String preMsg = "交易分配比例：" + "主键：" + ":";
        try {
            n = this.modifyByTrans(request, dealAssignContcMapper, pvMapper, dealAssignContc);
        } catch (Exception e) {
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_MODIFY, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_MODIFY, request, LogConstant.modifySuccessMsg(n), opLogMapper);

        rmsg.addMessage(LogConstant.modifySuccessMsg(n));

        return rmsg;
    }

    private boolean existRecord(DealAssignContc po, DealAssignContcMapper dealAssignContcMapper, OperationLogMapper opLogMapper) {
        List<DealAssignContc> list = dealAssignContcMapper.getDealAssignContcById(po);
        if (list.isEmpty()) {
            return false;
        }
        return true;

    }

    private int addByTrans(HttpServletRequest request, DealAssignContcMapper dealAssignContcMapper, PrmVersionMapper pvMapper, DealAssignContc dealAssignContc) throws Exception {

        TransactionStatus status = null;
        int n = 0;
        try {
            status = txMgr.getTransaction(this.def);
            n = dealAssignContcMapper.addDealAssignContc(dealAssignContc);
            pvMapper.modifyPrmVersionForDraft(dealAssignContc);

            txMgr.commit(status);
        } catch (Exception e) {
            if (txMgr != null) {
                txMgr.rollback(status);
            }
            throw e;
        }
        return n;
    }

    private void getVersionNoForSubmit(DealAssignContc po, String versionNoMax) {
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

    public int submitByTrans(HttpServletRequest request, DealAssignContcMapper dealAssignContcMapper, PrmVersionMapper pvMapper, DealAssignContc dealAssignContc, PrmVersion prmVersion) throws Exception {

        TransactionStatus status = null;
        String versionNoMax;
        int n = 0;
        try {

            status = txMgr.getTransaction(def);

            //旧的未来或当前参数数据做删除标志
            dealAssignContcMapper.submitToOldFlag(dealAssignContc);
            //生成新的未来或当前参数的版本号
            versionNoMax = pvMapper.selectPrmVersionForSubmit(dealAssignContc);
            this.getVersionNoForSubmit(dealAssignContc, versionNoMax);

            //添加新的未来或当前参数的数据记录
            n = dealAssignContcMapper.submitFromDraftToCurOrFur(dealAssignContc);
            // 重新生成参数表中的未来或当前参数记录
            pvMapper.modifyPrmVersionForSubmit(dealAssignContc);
            pvMapper.addPrmVersion(dealAssignContc);

            txMgr.commit(status);
        } catch (Exception e) {
            if (txMgr != null) {
                txMgr.rollback(status);
            }
            throw e;
        }
        return n;
    }

    private int cloneByTrans(HttpServletRequest request, DealAssignContcMapper dealAssignContcMapper, PrmVersionMapper pvMapper, DealAssignContc dealAssignContc, PrmVersion prmVersion) throws Exception {
        TransactionStatus status = null;
        int n = 0;
        try {
            status = txMgr.getTransaction(this.def);

            //删除草稿版本
            dealAssignContcMapper.deleteDealAssignContcForClone(dealAssignContc);
            //未来或当前参数克隆成草稿版本
            n = dealAssignContcMapper.cloneFromCurOrFurToDraft(dealAssignContc);
            //更新参数版本索引信息
            pvMapper.modifyPrmVersionForDraft(dealAssignContc);

            txMgr.commit(status);
        } catch (Exception e) {
            if (txMgr != null) {
                txMgr.rollback(status);
            }
            throw e;
        }
        return n;
    }

    public OperationResult submit(HttpServletRequest request, DealAssignContcMapper dealAssignContcMapper, PrmVersionMapper pvMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        DealAssignContc po = this.getReqAttributeForSubmit(request);
        DealAssignContc prmVersion = null;
        LogUtil logUtil = new LogUtil();
        int n = 0;

        try {
            n = this.submitByTrans(request, dealAssignContcMapper, pvMapper, po, prmVersion);
        } catch (Exception e) {
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_SUBMIT, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_SUBMIT, request, LogConstant.submitSuccessMsg(n, po.getRecord_flag_new()), opLogMapper);

        rmsg.addMessage(LogConstant.submitSuccessMsg(n, po.getRecord_flag_new()));

        return rmsg;
    }

    public OperationResult clone(HttpServletRequest request, DealAssignContcMapper dealAssignContcMapper, PrmVersionMapper pvMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        DealAssignContc po = this.getReqAttributeForClone(request);
        PrmVersion prmVersion = null;
        LogUtil logUtil = new LogUtil();
        int n = 0;

        try {
            n = this.cloneByTrans(request, dealAssignContcMapper, pvMapper, po, prmVersion);
        } catch (Exception e) {
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_CLONE, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_CLONE, request, LogConstant.cloneSuccessMsg(n), opLogMapper);

        rmsg.addMessage(LogConstant.cloneSuccessMsg(n));

        return rmsg;
    }

    private int modifyByTrans(HttpServletRequest request, DealAssignContcMapper dealAssignContcMapper, PrmVersionMapper pvMapper, DealAssignContc dealAssignContc) throws Exception {

        TransactionStatus status = null;
        int n = 0;
        try {
            status = txMgr.getTransaction(this.def);
            n = dealAssignContcMapper.modifyDealAssignContc(dealAssignContc);
            pvMapper.modifyPrmVersionForDraft(dealAssignContc);

            txMgr.commit(status);
        } catch (Exception e) {
            if (txMgr != null) {
                txMgr.rollback(status);
            }
            throw e;
        }
        return n;
    }

    private int deleteByTrans(HttpServletRequest request, DealAssignContcMapper dealAssignContcMapper, PrmVersionMapper pvMapper, Vector<DealAssignContc> pos, DealAssignContc prmVo) throws Exception {

        TransactionStatus status = null;
        int n = 0;
        try {
            status = txMgr.getTransaction(this.def);
            for (DealAssignContc po : pos) {
                n += dealAssignContcMapper.deleteDealAssignContc(po);
            }
            pvMapper.modifyPrmVersionForDraft(prmVo);

            txMgr.commit(status);
        } catch (Exception e) {
            if (txMgr != null) {
                txMgr.rollback(status);
            }
            throw e;
        }
        return n;
    }

    public OperationResult add(HttpServletRequest request, DealAssignContcMapper dealAssignContcMapper, PrmVersionMapper pvMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        DealAssignContc po = this.getReqAttribute(request);
        LogUtil logUtil = new LogUtil();
        int n = 0;
        String preMsg = "交易分配比例：" + "主键：" + ":";
        try {
            if (this.existRecord(po, dealAssignContcMapper, opLogMapper)) {
                rmsg.addMessage("此交易分配比例记录已存在！");
                return rmsg;
            }

            n = this.addByTrans(request, dealAssignContcMapper, pvMapper, po);

        } catch (Exception e) {
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_ADD, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_ADD, request, preMsg + LogConstant.addSuccessMsg(n), opLogMapper);
//        }
        rmsg.addMessage(LogConstant.addSuccessMsg(n));

        return rmsg;
    }

    public OperationResult delete(HttpServletRequest request, DealAssignContcMapper dealAssignContcMapper, PrmVersionMapper pvMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        Vector<DealAssignContc> pos = this.getReqAttributeForDelete(request);
        DealAssignContc prmVo = new DealAssignContc();
        this.getBaseParameters(request, prmVo);
        LogUtil logUtil = new LogUtil();
        int n = 0;

        try {
            n = this.deleteByTrans(request, dealAssignContcMapper, pvMapper, pos, prmVo);
        } catch (Exception e) {
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_DELETE, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_DELETE, request, LogConstant.delSuccessMsg(n), opLogMapper);

        rmsg.addMessage(LogConstant.delSuccessMsg(n));

        return rmsg;
    }

    public DealAssignContc getReqAttribute(HttpServletRequest request) {
        DealAssignContc dealAssignContc = new DealAssignContc();

        dealAssignContc.setB_line_id(FormUtil.getParameter(request, "d_b_line_id"));
        dealAssignContc.setB_station_id(FormUtil.getParameter(request, "d_b_station_id"));
        dealAssignContc.setE_line_id(FormUtil.getParameter(request, "d_e_line_id"));
        dealAssignContc.setE_station_id(FormUtil.getParameter(request, "d_e_station_id"));
        dealAssignContc.setContc_id(FormUtil.getParameter(request, "d_contc_id"));
        dealAssignContc.setIn_percent(FormUtil.getParameter(request, "d_in_percent"));

        this.getBaseParameters(request, dealAssignContc);

        return dealAssignContc;
    }

    public DealAssignContc getReqAttributeForSubmit(HttpServletRequest request) {
        DealAssignContc dealAssignContc = new DealAssignContc();

        getBaseParameters(request, dealAssignContc);
        getBaseParametersForSubmit(request, dealAssignContc);

        return dealAssignContc;

    }

    public DealAssignContc getReqAttributeForClone(HttpServletRequest request) {
        DealAssignContc dealAssignContc = new DealAssignContc();

        this.getBaseParameters(request, dealAssignContc);

        return dealAssignContc;
    }

    private Vector<DealAssignContc> getDeleteIDs(String selectedIds) {
        StringTokenizer st = new StringTokenizer(selectedIds, ";");
        String strIds = null;
        Vector<DealAssignContc> sds = new Vector();
        DealAssignContc sd;
        while (st.hasMoreTokens()) {
            strIds = st.nextToken();
            sd = this.getDealAssignContc(strIds, "#");
            sds.add(sd);
        }
        return sds;

    }

    private DealAssignContc getDealAssignContc(String strIDs, String delim) {
        StringTokenizer st = new StringTokenizer(strIDs, delim);
        String tmp = null;
        int i = 0;
        DealAssignContc dealAssignContc = new DealAssignContc();
        while (st.hasMoreTokens()) {
            i++;
            tmp = st.nextToken();
            if (i == 1) {
                dealAssignContc.setB_line_id(tmp);
                continue;
            }
            if (i == 2) {
                dealAssignContc.setB_station_id(tmp);
                continue;
            }
            if (i == 3) {
                dealAssignContc.setE_line_id(tmp);
                continue;
            }
            if (i == 4) {
                dealAssignContc.setE_station_id(tmp);
                continue;
            }
            if (i == 5) {
                dealAssignContc.setContc_id(tmp);
                continue;
            }
            if (i == 6) {
                dealAssignContc.setIn_percent(tmp);
                continue;
            }
            if (i == 7) {
                dealAssignContc.setVersion_no(tmp);
                continue;
            }
            if (i == 8) {
                dealAssignContc.setRecord_flag(tmp);
                continue;
            }
        }
        return dealAssignContc;

    }

    private Vector<DealAssignContc> getReqAttributeForDelete(HttpServletRequest request) {
        String selectIds = request.getParameter("allSelectedIDs");
        Vector<DealAssignContc> selectedItems = this.getDeleteIDs(selectIds);

        return selectedItems;
    }

    private String getRecordFlagOld(DealAssignContc dealAssignContc) {
        String recordFlagSubmit = dealAssignContc.getRecord_flag_submit();
        String recordFlagOld = "";
        if (recordFlagSubmit.equals(ParameterConstant.RECORD_FLAG_CURRENT)) {
            recordFlagOld = ParameterConstant.RECORD_FLAG_HISTORY;
        }
        if (recordFlagSubmit.equals(ParameterConstant.RECORD_FLAG_FUTURE)) {
            recordFlagOld = ParameterConstant.RECORD_FLAG_DELETED;
        }
        return recordFlagOld;
    }

    private String getVersionValidDate(DealAssignContc dealAssignContc) {
        String verDateBegin = dealAssignContc.getBegin_time_submit();
        return this.convertValidDate(verDateBegin);
    }

    public String convertValidDate(String validDate) {
        if (validDate == null || validDate.trim().length() == 0 || validDate.trim().length() != 10) {
            return validDate;
        }
        int index = validDate.indexOf("-");
        int index1 = validDate.indexOf("-", index + 1);
        return validDate.substring(0, index) + validDate.substring(index + 1, index1) + validDate.substring(index1 + 1);
    }

    @RequestMapping("/import_DealContc")
    public ModelAndView import_DealLine(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView("/jsp/prminfo/importDealContcPara.jsp");
        OperationResult opResult = new OperationResult();

        try {

            String command = request.getParameter("command");

            if (command != null) {
                if (command.equals("importContc")) {
                    opResult = this.importFile(request, this.prmVersionMapper, this.operationLogMapper, mv);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            mv.addObject("msg", e.getMessage());
        }

//         this.SaveOperationResult(mv, opResult);
        return mv;
    }

    private OperationResult importFile(HttpServletRequest request, PrmVersionMapper pvMapper, OperationLogMapper opLogMapper, ModelAndView mv) throws Exception {

        LogUtil logUtil = new LogUtil();
        int n = 0;
        String preMsg = "线路交易参数导入：";
        OperationResult or = new OperationResult();
        HandleMessageBase messageBase = null;
        String terminator = request.getParameter("seperator");
        try {
            MultipartHttpServletRequest multipartRequest;
            multipartRequest = (MultipartHttpServletRequest) request;
            CommonsMultipartFile file = (CommonsMultipartFile) multipartRequest
                    .getFile("makeFile");
            String realFileName = file.getOriginalFilename();
            InputStream is = file.getInputStream();
            n = this.importDealAssginLineBySql(request, is, terminator);
            mv.addObject("msg", "成功导入" + n
                    + "条记录");
        } catch (Exception e) {
            e.printStackTrace();
            mv.addObject("msg", "导入失败，"+e.getMessage());

        }
//        logUtil.logOperation(CommandConstant.COMMAND_IMPORT, request,"成功导入" + n + "条记录", opLogMapper);
//        request.setAttribute("msg", "成功导入" + n
//                    + "条记录");
        or.addMessage(LogConstant.importSuccessMsg(n));
        return or;
    }

    private int importDealAssginLineBySql(HttpServletRequest request, InputStream stream, String terminator) throws Exception {
        int n = 0;
        Vector<String> linestations;
        Vector<String> contcs;
        linestations = dealAssignContcMapper.getLines();
        contcs = dealAssignContcMapper.getContcs();

        if (linestations.isEmpty()) {
            throw new Exception("车站表的当前版本记录数不能为空");
        }
        if (contcs.isEmpty()) {
            throw new Exception("线路运营商表的当前版本记录数不能为空");
        }
        ImportResult r4 = this.imporFileForDealAssignBySql(request, stream, ImportConstant.TABLE_NAME_DEAL_LINE,
                ImportConstant.FILE_NAME_DEAL_LINE_SQL, terminator, ImportConstant.FIELD_SIZE_DEAL_LINE,
                ImportConstant.FIELD_NAME_DEAL_LINE, ImportConstant.FIELD_TYPE_DEAL_LINE, linestations, contcs);
        n = r4.getRecordNum();
        return n;
    }

    public ImportResult imporFileForDealAssignBySql(HttpServletRequest request,
            InputStream in, String tableName, String fileName,
            String terminator, int fieldSize, String fieldNames,
            String[] fieldTypes, Vector<String> linestations, Vector<String> lines) throws Exception {
        DealAssignContc result = new DealAssignContc();
        ImportResult impResult = null;
        OpLogImport vo = new OpLogImport();
        long startTime = System.currentTimeMillis();
        long startTimeOrg = System.currentTimeMillis();
        long endTime = 0;
        int n;

        impResult = genImportVectorForCommonForDealAssign(request, in, fileName, terminator, fieldSize, linestations, lines);
        endTime = System.currentTimeMillis();
        logger.info("校验耗时：" + (endTime - startTime) / 1000 + "秒");
//            dbHelper = new DbHelper(FrameDBConstant.MAIN_DATASOURCE);
//            dbHelper.setAutoCommit(false);
        startTime = System.currentTimeMillis();
//            n = DealAssignContcMapper.deleteVersionRecordWithTran(tableName, impResult.getVersonNo());
//            vo = getVo(request, ImportConstant.OP_TYPE_DELETE, tableName, impResult.getVersonNo(), n, "\u5220\u9664");
//            opLogImportMapper.addOpLogImport(vo);
        logger.info("删除表" + tableName + "旧版本" + impResult.getVersonNo());
        endTime = System.currentTimeMillis();
        logger.info("删除旧版本耗时：" + (endTime - startTime) / 1000 + "秒");

        startTime = System.currentTimeMillis();
        result = insertRecordsByIndexWithTran(impResult, tableName, fieldNames, fieldTypes);
        endTime = System.currentTimeMillis();
        logger.info("插入数据库耗时：" + (endTime - startTime) / 1000 + "秒");
//            vo = this.getVo(request, ImportConstant.OP_TYPE_ADD, tableName, impResult.getVersonNo(), result.getTotal(), "增加");
//            opLogImportMapper.addOpLogImport(vo);
//            dbHelper.commitTran();
        impResult.setRecordNum(Integer.parseInt(result.getTotal()));
        logger.info("导入" + tableName + "记录数" + impResult.getRecordNum());
        endTime = System.currentTimeMillis();
        logger.info("导入" + tableName + "总耗时:" + (endTime - startTimeOrg)
                / 1000 + "秒");

        if (in != null) {
            in.close();
        }
//            PubUtil.finalProcessForTran(dbHelper, logger);

        return impResult;
    }

    /**
     * 校验并从文件取交易分配比例的所有记录
     *
     * @param request
     * @param in
     * @param title
     * @param delim
     * @param fieldSize
     * @param stations
     * @param conts
     * @return
     * @throws Exception
     */
    private ImportResult genImportVectorForCommonForDealAssign(
            HttpServletRequest request, InputStream in, String title,
            String delim, int fieldSize, Vector stations, Vector conts)
            throws Exception {
        InputStreamReader isr = null;
        BufferedReader br = null;

        String line;
        int i = 1;
        String[] fields;

        ImportResult result = new ImportResult();
        Vector records = new Vector(ImportConstant.SIZE_INIT,
                ImportConstant.SIZE_INCREMENT);

        TreeMap rates = new TreeMap();
        TreeSet keys = new TreeSet();
        try {
            isr = new InputStreamReader(in);
            br = new BufferedReader(isr);
            while ((line = br.readLine()) != null) {
                fields = this.getFieldsForSqlForDealAssignByTree(line, delim,
                        i, fieldSize, title, stations, conts, rates, keys);
                records.add(fields);
                i++;
            }
            if (i == 1) {
                throw new Exception(title + "文件为空");
            }
            this.checkRateByTree(rates);
        } catch (Exception e) {
            throw new Exception(e.getMessage());

        } finally {
            if (br != null) {
                br.close();
            }
            if (isr != null) {
                isr.close();
            }
        }
        result.setTile(title);
        result.setRecords(records);
        result.setVersonNo(ParameterConstant.VERSION_NO_DRAFT);
        return result;
    }

    /**
     * 获得日志对象
     *
     * @param request
     * @param opType
     * @param tableName
     * @param versionNo
     * @param recordCount
     * @param remark
     * @return
     */
    protected OpLogImport getVo(HttpServletRequest request, String opType,
            String tableName, String versionNo, String recordCount, String remark) {
        OpLogImport vo = new OpLogImport();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
        vo.setOperator_id(((User) request.getSession().getAttribute("User")).getAccount());
        vo.setOp_time(sdf.format(new Date()));
        vo.setOp_type("1");  // 1  表示增加
        vo.setTable_name("w_op_prm_phy_logic_list");
        vo.setRecord_count(recordCount);
        vo.setVersion_no("0000000000");   //草稿参数版本号
        vo.setRemark("导入");
        vo.setFile_name(vo.getFile_name());
        vo.setBegin_logical_id(vo.getBegin_logical_id());
        vo.setEnd_logical_id(vo.getEnd_logical_id());
        return vo;
    }

    public static String dateToString(Date date) {
        if (date == null) {
            return "";
        }
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return f.format(date);
    }

    /**
     * 插入数据
     *
     * @param impResult
     * @param tableName
     * @param fieldNames
     * @param fieldTypes
     * @param dbHelper
     * @return
     * @throws Exception
     */
    protected DealAssignContc insertRecordsByIndexWithTran(ImportResult impResult,
            String tableName, String fieldNames, String[] fieldTypes) throws Exception {
        DealAssignContc result = new DealAssignContc();
        OperationResult rmsg = new OperationResult();
        Vector records = impResult.getRecords();
        List indexs = this.getDivideIndex(records.size());
        int n = 0;
        List<DealAssignContc> listTemps = new ArrayList<DealAssignContc>();
        for (int a = 0; a < records.size(); a++) {
            try {
                String[] a1 = (String[]) records.get(a);
                DealAssignContc dl = new DealAssignContc();
                for (int b = 0; b < a1.length; b++) {
                    if (b == 0) {
                        String b0 = a1[b];
                        dl.setB_line_id(b0);
                    }
                    if (b == 1) {
                        String b1 = a1[b];
                        dl.setB_station_id(b1);
                    }
                    if (b == 2) {
                        String b2 = a1[b];
                        dl.setE_line_id(b2);
                    }
                    if (b == 3) {
                        String b3 = a1[b];
                        dl.setE_station_id(b3);
                    }
                    if (b == 4) {
                        String b4 = a1[b];
                        dl.setContc_id(b4);
                    }
                    if (b == 5) {
                        String b5 = a1[b];
                        dl.setIn_percent(b5);
                    }
                    if (b == 6) {
                        String b6 = a1[b];
                        dl.setVersion_no(b6);
                    }
                    if (b == 7) {
                        String b7 = a1[b];
                        dl.setRecord_flag(b7);
                    }

                }

                n += dealAssignContcMapper.addDealAssignContc(dl);
                listTemps.add(dl);
            } catch (Exception e) {
                DealAssignContc d2 = new DealAssignContc();
                for (int j = 0; j < listTemps.size(); j++) {
                    d2 = listTemps.get(j);
                    try {
                        dealAssignContcMapper.deleteDealAssignContc(d2);
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }
                throw new Exception("第" + (n + 1) + "行数据已存在");
            }

        }

        result.setTotal(new Integer(n).toString());
        return result;
    }

    public Vector getDivideIndex(int size) {
        int n = size / ImportConstant.TRAN_UNIT_COUNT;
        int r = size % ImportConstant.TRAN_UNIT_COUNT;
        if (r != 0) {
            n = n + 1;
        }
        int start = 0;
        int end;
        int[] indexs;
        Vector v = new Vector();
        for (int i = 0; i < n; i++) {
            start = i * ImportConstant.TRAN_UNIT_COUNT;
            end = (i + 1) * ImportConstant.TRAN_UNIT_COUNT;
            if (i == n - 1) {
                if (end > size) {
                    end = size;
                }
            }
            indexs = new int[2];
            indexs[0] = start;
            indexs[1] = end - 1;
            v.add(indexs);

            System.out.println("start=" + indexs[0] + " end=" + indexs[1]);
        }
        return v;
    }

    private int insertRecordsUnitByIndexWithTran(String tableName,
            String fieldNames, List fieldValuesV, String[] fieldTypes,
            int start, int end, DbHelper dbHelper) throws Exception {

        int n = 0;

        n = this.insertRecordByBatchByIndex(dbHelper, tableName, fieldNames,
                fieldValuesV, fieldTypes, start, end);

        return n;
    }

    public int insertRecordByBatchByIndex(DbHelper dbHelper, String tableName,
            String fieldNames, List fieldValuesV, String[] fieldTypes,
            int start, int end) throws Exception {

        String sql = this.getInsertSql(tableName, fieldNames);
        System.out.println("sql= " + sql);
        dbHelper.prepareStatement(sql);
        String[] fieldValues;
        for (int k = start; k <= end; k++) {
            fieldValues = (String[]) fieldValuesV.get(k);
            this.addValuesByBatch(dbHelper, fieldValues, fieldTypes);
        }
        int n = dbHelper.executeBatch();
        //TODO 返回值 有问题 是负数
        return Math.abs(n / 2);
    }

    public String getInsertSql(String tableName, String fields) {
        Vector fieldV = this.getVectorForStr(fields, ",");
        String paraForInsert = this.getParaSqlForInsert(fieldV.size());
        String sql = "insert into " + tableName + "(" + fields + ")"
                + " values(" + paraForInsert + ")";
        return sql;

    }

    public Vector getVectorForStr(String strIDs, String delim) {
        StringTokenizer st = new StringTokenizer(strIDs, delim);
        String ID = null;
        Vector v = new Vector();
        while (st.hasMoreTokens()) {
            ID = st.nextToken();
            v.add(ID);
        }
        return v;

    }

    public String getParaSqlForInsert(int size) {
        String para = "";
        for (int i = 0; i < size; i++) {
            para = para + "?" + ",";
        }

        return para.substring(0, para.length() - 1);
    }

    private void addValuesByBatch(DbHelper dbHelper, String[] fieldValues,
            String[] fieldTypes) throws Exception {

        Object[] values = this.getFieldValues(fieldValues, fieldTypes);
        dbHelper.addBatch(values);

        values = null;

    }

    public Object[] getFieldValues(String[] fieldValuesStr, String[] fieldTypes)
            throws Exception {

        Object[] fieldValues = new Object[fieldValuesStr.length];
        Object fieldValue;
        String fieldType;

        for (int i = 0; i < fieldValuesStr.length; i++) {
            fieldType = (String) fieldTypes[i];

            if (fieldType.equals("string") || fieldType.equals("date")) {
                fieldValue = fieldValuesStr[i];
                fieldValues[i] = fieldValue;
                continue;
            }
            if (fieldType.equals("decimal")) {
                fieldValue = new BigDecimal(fieldValuesStr[i]);
                fieldValues[i] = fieldValue;
                continue;
            }
            if (fieldType.equals("int")) {
                fieldValue = new Integer(fieldValuesStr[i]);
                fieldValues[i] = fieldValue;
                continue;
            }
        }
        return fieldValues;
    }

    private String[] getFieldsForSqlForDealAssignByTree(String line,
            String delim, int i, int fieldSize, String title, Vector stations,
            Vector concts, TreeMap rates, TreeSet keys) throws Exception {
        if (!this.isValidFieldLen(line, delim, fieldSize)) {
            throw new Exception(title + "文件" + "的第" + i + "行字段数不等于"
                    + fieldSize);
        }
        fieldSize = this.getLineFieldLen(line, delim);
        String[] fields = new String[fieldSize + 2];
        int k = 0;
        for (k = 0; k < fieldSize; k++) {
            fields[k] = this.getLineField(line, k, delim, i);
        }
        fields[k] = ParameterConstant.VERSION_NO_DRAFT;
        fields[k + 1] = ParameterConstant.RECORD_FLAG_DRAFT;

        String station = fields[0] + fields[1];
        String station_1 = fields[2] + fields[3];
        String cont = fields[4];
        String rate = fields[5];
        String od = station + station_1;
        String pk = od + cont;
        this.validFieldForDouble(rate, "分账比例", i);
        checkFieldsByBusinessForStations(station, stations, title, i);
        checkFieldsByBusinessForStations(station_1, stations, title, i);
        checkFieldsByBusinessForConcts(cont, concts, title, i);
        this.checkRepeatByTree(pk, keys, title, i);
        this.plusRateByTree(od, rate, rates);

        return fields;
    }

    protected String getLineField(String line, int i, String delim, int ii)
            throws Exception {
        if (delim.equals("\\t")) {
            delim = "\t";
        }
        StringTokenizer st = new StringTokenizer(line, delim);
        int k = -1;
        String token;

        while (st.hasMoreTokens()) {
            k++;
            token = st.nextToken();
            if (k == i) {
                return token.trim();
            }
        }
        throw new Exception("第" + ii + "行，" + this.getTerminatorName(delim)
                + "，字段的数量不够，不存在第" + (i + 1) + "字段");
    }

    /**
     * 检查文件每一行的字段数
     *
     * @param line
     * @param delim
     * @param fieldSize
     * @return
     */
    protected boolean isValidFieldLen(String line, String delim, int fieldSize) {
        if (delim.equals("\\t")) {
            delim = "\t";
        }
        StringTokenizer st = new StringTokenizer(line, delim);
        int n = st.countTokens();
        if (n != fieldSize) {
            return false;
        }
        return true;

    }

    /**
     * 获取行的信息
     *
     * @param line
     * @param delim
     * @return
     */
    protected int getLineFieldLen(String line, String delim) {
        if (delim.equals("\\t")) {
            delim = "\t";
        }
        StringTokenizer st = new StringTokenizer(line, delim);
        int n = st.countTokens();
        return n;

    }

    /**
     * 验证是否小数
     *
     * @param field
     * @param name
     * @param i
     * @throws Exception
     */
    protected void validFieldForDouble(String field, String name, int i)
            throws Exception {
        try {
            Double.parseDouble(field);
        } catch (Exception e) {
            throw new Exception("第" + i + "行，" + "字段" + name + "应是小数");
        }
    }

    private boolean checkFieldsByBusinessForStations(String station,
            Vector stations, String title, int i) throws Exception {
        if (!stations.contains(station)) {
            throw new Exception(title + "文件" + "的第" + i + "行车站不存在");
        }
        return true;

    }

    private boolean checkFieldsByBusinessForConcts(String cont, Vector concts,
            String title, int i) throws Exception {
        if (!concts.contains(cont)) {
            throw new Exception(title + "文件" + "的第" + i + "行运营商不存在");
        }
        return true;
    }

    private boolean checkRepeatByTree(String key, TreeSet keys, String title,
            int j) throws Exception {
        if (keys.contains(key)) {
            throw new Exception(title + "文件" + "的第" + j
                    + "行主键(起始线路站点终止线路站点运营商）主键重复");
        }
        keys.add(key);
        return true;
    }

    private void plusRateByTree(String od, String rate, TreeMap rates) {
        BigDecimal b = new BigDecimal(rate);
        if (!rates.containsKey(od)) {
            rates.put(od, b);
            return;
        }
        BigDecimal b1 = (BigDecimal) rates.get(od);
        b1 = b1.add(b);
        rates.put(od, b1);

    }

    private String getTerminatorName(String delim) {
        if (delim.equals("\t")) {
            return "TAB 分隔符";
        }
        if (delim.equals(",")) {
            return "逗号分隔符";
        }
        return "";
    }

    private boolean checkRateByTree(TreeMap rates) throws Exception {
        Set keys = rates.keySet();
        Iterator it = keys.iterator();
        String key;
        BigDecimal b;
        while (it.hasNext()) {
            key = (String) it.next();
            b = (BigDecimal) rates.get(key);
            if (b.compareTo(ImportConstant.RATE_STAND) != 0) {
                throw new Exception("站点OD" + key + "的分配比例和不等于"
                        + ImportConstant.RATE_STAND.toString());
            }
        }
        return true;
    }
    
    @RequestMapping("/DealAssignContcExportAll")
    public void exportExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	
        List results = this.getBufferElementsForCurClass(request, "com.goldsign.acc.app.prminfo.entity.DealAssignContc");
        List<Map<String,String>> queryResults = this.entityToMap(results);
        ExpUtil.renderExportExcelPath(response, ExpUtil.exportExcel(queryResults, request));
    }

    private List<Map<String, String>> entityToMap(List results) {
        List<Map<String,String>> list =  new ArrayList<>();
        int row = results.size();
        for(int n=0;n<row ;n++){
            DealAssignContc vo = (DealAssignContc)results.get(n);
            Map<String,String> map = new HashMap<>();
            map.put("b_line_id_name", vo.getB_line_id_name());
            map.put("b_station_id_name", vo.getB_station_id_name());
            map.put("e_line_id_name", vo.getE_line_id_name());
            map.put("e_station_id_name", vo.getE_station_id_name());
            map.put("contc_id_name", vo.getContc_id_name());
            map.put("in_percent", vo.getIn_percent());
           
            list.add(map);
        }
        return list;
    }

}
