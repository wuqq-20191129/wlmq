/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.opma.controller;

import com.goldsign.acc.app.opma.entity.ParamDown;
import com.goldsign.acc.app.opma.mapper.ParamDownMapper;

import com.goldsign.acc.app.prminfo.entity.DealAssignLine;
import com.goldsign.acc.app.prminfo.entity.PrmVersion;
import com.goldsign.acc.app.prminfo.entity.SystemRateContc;
import com.goldsign.acc.app.prminfo.mapper.DealAssignLineMapper;
import com.goldsign.acc.app.prminfo.mapper.PrmVersionMapper;
import com.goldsign.acc.frame.constant.CommandConstant;
import com.goldsign.acc.frame.constant.LogConstant;
import com.goldsign.acc.frame.constant.OpCodeConstant;
import com.goldsign.acc.frame.constant.ParameterConstant;
import com.goldsign.acc.frame.controller.BaseController;
import com.goldsign.acc.frame.controller.PrmBaseController;
import com.goldsign.acc.frame.entity.PubFlag;
import com.goldsign.acc.frame.mapper.OperationLogMapper;
import com.goldsign.acc.frame.mapper.PubFlagMapper;
import com.goldsign.acc.frame.util.DBUtil;
import com.goldsign.acc.frame.util.FormUtil;
import com.goldsign.acc.frame.util.LogUtil;
import com.goldsign.acc.frame.vo.OperationResult;
import com.goldsign.login.vo.User;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.Vector;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.TransactionStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author liudz
 */
@Controller
public class ParamDownController extends PrmBaseController {

    @Autowired
    private ParamDownMapper paramDownMapper;

    @Autowired
    private PubFlagMapper pubFlagMapper;

    @RequestMapping("/ParamDown")
    public ModelAndView service(HttpServletRequest request, HttpServletResponse response) {

        ModelAndView mv = new ModelAndView("/jsp/opma/param_down.jsp");
        String command = request.getParameter("command");
        OperationResult opResult = new OperationResult();
        ParamDown pmb = new ParamDown();
        List<ParamDown> pd = new ArrayList<ParamDown>();
        try {
            
            if (command != null && command.equals(CommandConstant.COMMAND_DOWNLOAD)) {

                opResult = this.down(request, this.paramDownMapper, this.operationLogMapper);
            }
            else if (command != null &&command.equals(CommandConstant.COMMAND_QUERY)) {

                opResult = this.queryCondition(request, this.paramDownMapper, this.operationLogMapper);
            }
            else{
                opResult = this.query(request, this.paramDownMapper, this.operationLogMapper);
            }

            if (command != null) {
                if ( command.equals(CommandConstant.COMMAND_DOWNLOAD)) {
                    if (!command.equals(CommandConstant.COMMAND_ADD)) {
                        this.saveQueryControlDefaultValues(request, mv);//保存查询条件
                    }
//                    Thread.sleep(5000);
//                    this.queryForOp(request, this.paramDownMapper, this.operationLogMapper, opResult);
                }
            } 
        } catch (Exception e) {
            e.printStackTrace();
        }

        //初始化下拉框
        String[] attrNames = {LCC_LINES, RECORDFLAG};

        this.setPageOptions(attrNames, mv, request, response);//设置页面线路、车站等选项值、版本
        this.getResultSetText((List<ParamDown>) opResult.getReturnResultSet(), mv);
        this.baseHandler(request, response, mv);
//        this.divideResultSet(request, mv, opResult);//结果集分页
        this.SaveOperationResult(mv, opResult);//返回结果集

        return mv;

    }

    private void getResultSetText(List<ParamDown> resultSet, ModelAndView mv) {
        //LCC线路名
        List<PubFlag> lccLines = (List<PubFlag>) mv.getModelMap().get(PrmBaseController.LCC_LINES);

        List<PubFlag> recordFlags = (List<PubFlag>) mv.getModelMap().get(PrmBaseController.RECORDFLAG);
        for (ParamDown pd : resultSet) {
            if (pd != null) {
                PubFlag pv = new PubFlag();
                for (int i = 0; i < lccLines.size(); i++) {
                    pv = (PubFlag) lccLines.get(i);
                    if (pv.getCode().equals(pd.getSelectLccLine())) {
                        pd.setLccLineName(pv.getCode_text());
                    }
                }
            }
            if (recordFlags != null && !recordFlags.isEmpty()) {
                pd.setRecord_flag_name(DBUtil.getTextByCode(pd.getRecord_flag(), recordFlags));
            }
        }
    }

    private ParamDown getQueryConditionForOp(HttpServletRequest request) {
        ParamDown qCon = new ParamDown();
        HashMap vQueryControlDefaultValues = null;
        String queryControlDefaultValues = request.getParameter("queryControlDefaultValues");
        String command = request.getParameter("command");
        if (FormUtil.isAddOrModifyMode(request)) {//操作处于添加或修改模式
            if (command.equals(CommandConstant.COMMAND_DELETE)) {
                return null;
            }

        } else {//操作处于非添加模式
            vQueryControlDefaultValues = FormUtil.getQueryControlDefaultValues(queryControlDefaultValues);
            if (!vQueryControlDefaultValues.isEmpty()) {
            }
        }
//        this.getBaseParameters(request, qCon);

        return qCon;
    }

    private ParamDown getQueryCondition(HttpServletRequest request) {
        ParamDown qCon = new ParamDown();
        qCon.setParm_type_name(FormUtil.getParameter(request, "q_parm_type_name"));
        qCon.setVersion_no(FormUtil.getParameter(request, "q_version_no"));
        this.getBaseParameters(request, qCon);
        return qCon;
    }

    public OperationResult query(HttpServletRequest request, ParamDownMapper paramDownMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult or = new OperationResult();
        this.getQueryConditionForOp(request);
        LogUtil logUtil = new LogUtil();
        ParamDown queryCondition;
        List<ParamDown> resultSet = null;

        try {
            queryCondition = this.getQueryCondition(request);
            resultSet = this.getAllPriviledges(queryCondition);
//            String n = paramDownMapper.getDegradeModeRecd();
//            if(n != null)
//            {
//                ParamDown p1 = new ParamDown();
//                p1.setParm_type_name("降级模式");
//                p1.setVersion_no("0000000000");
//                p1.setParm_type_id(ParameterConstant.DEGRADE_MODE);
//                p1.setVersion_type("0");
//                resultSet.add(p1);
//            }
            or.setReturnResultSet(resultSet);
            or.addMessage("成功查询" + resultSet.size() + "条记录");
        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + resultSet.size() + "条记录", opLogMapper);

        return or;
    }

    public ParamDown getReqAttribute(HttpServletRequest request) {
        ParamDown pd = new ParamDown();

        pd.setParm_type_id(FormUtil.getParameter(request, "parm_type_id"));

        this.getBaseParameters(request, pd);

        return pd;
    }

    private DealAssignLine getDealAssignLine(String strIDs, String delim) {
        StringTokenizer st = new StringTokenizer(strIDs, delim);
        String tmp = null;
        int i = 0;
        DealAssignLine dealAssignLine = new DealAssignLine();
        while (st.hasMoreTokens()) {
            i++;
            tmp = st.nextToken();
            if (i == 1) {
                dealAssignLine.setB_line_id(tmp);
                continue;
            }
            if (i == 2) {
                dealAssignLine.setB_station_id(tmp);
                continue;
            }
            if (i == 3) {
                dealAssignLine.setE_line_id(tmp);
                continue;
            }
            if (i == 4) {
                dealAssignLine.setE_station_id(tmp);
                continue;
            }
            if (i == 5) {
                dealAssignLine.setLine_id_dispart(tmp);
                continue;
            }
            if (i == 6) {
                dealAssignLine.setIn_percent(tmp);
                continue;
            }
            if (i == 7) {
                dealAssignLine.setVersion_no(tmp);
                continue;
            }
            if (i == 8) {
                dealAssignLine.setRecord_flag(tmp);
                continue;
            }
        }
        return dealAssignLine;

    }

    private void getBaseParameters(HttpServletRequest request, ParamDown paramDown) {
        paramDown.setRecord_flag(FormUtil.getParameter(request, "VersionType"));
        paramDown.setParm_type_id(FormUtil.getParameter(request, "Type"));
        User user = (User) request.getSession().getAttribute("User");
        paramDown.setOperator_id(user.getAccount());
    }

//下发
    private OperationResult down(HttpServletRequest request, ParamDownMapper paramDownMapper, OperationLogMapper operationLogMapper) throws Exception {
        User user = (User) request.getSession().getAttribute("User");
        String operatorId = user.getAccount();
        OperationResult rmsg = new OperationResult();
        String strParmNum = request.getParameter("allSelectedIDs");
        //String strLccLines = request.getParameter("lcc_line_id"+strParmNum);
        LogUtil logUtil = new LogUtil();
        int n = 0;
        try {
            //n = this.downByParam(strParmNum, strLccLines, operatorId, paramDownMapper);
            n = this.downByParam(strParmNum, operatorId, paramDownMapper,request);
        } catch (Exception e) {

            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, operationLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, LogConstant.OPERATIION_SUCCESS_LOG_MESSAGE, operationLogMapper);

        rmsg.addMessage("第" + n + "次参数下发成功");

        return rmsg;
    }

//    private int downByParam(String strParmNum, String strLccLines, String operatorId, ParamDownMapper paramDownMapper) throws Exception {
//        List<String> paraNum = new ArrayList<>();
//        List<String> lccLines = new ArrayList<>();
//        this.getParms(strParmNum, paraNum, strLccLines, lccLines, operatorId, paramDownMapper);
//        return this.down1(paraNum, lccLines, operatorId);
//
//    }
    private int downByParam(String strParmNum,String operatorId, ParamDownMapper paramDownMapper,HttpServletRequest request) throws Exception {
        List<String> paraNum = new ArrayList<>();
        List<String> lccLines = new ArrayList<>();
//        this.getParms(strParmNum, paraNum, strLccLines, lccLines, operatorId, paramDownMapper);
        this.getParms(strParmNum, paraNum, request, lccLines, operatorId, paramDownMapper);
        return this.down1(paraNum, lccLines, operatorId);

    }

    private void getParms(String strParmNum, List<String> parms, HttpServletRequest request, List<String> lccLines, String operatorId, ParamDownMapper paramDownMapper) throws Exception {
        String[] parmArr = strParmNum.split(";");
        
//        String[] lccArr = strLccLines.split(";");
//        默认选择全部 如果选择了全部 其他不生效
//        if (parmArr.length != lccArr.length) {
//            throw new Exception("请选择参数下发的线路");
//        }
        String parm = null;
        String parmTypeId = null;
        String verNum = null;
        String msg = "";
        for (int i = 0; i < parmArr.length; i++) {
            parm = parmArr[i];
            if (parm.length() == 15) {
                String lccLineName = request.getParameter("lcc_line_id"+parmArr[i]);
                 if (lccLineName.trim() == null || lccLineName.trim().equals("")) {
                throw new Exception("请选择参数下发的线路");
            }
                lccLines.add(orderLccArrStr(lccLineName));
                parmTypeId = parm.substring(0, 4);//取出参数代码
                if (parmTypeId.equals("0501")) {
                    //降级模式下发

                    verNum = this.getDegradeVerNum(operatorId);
                    parms.add("0501" + verNum + "" + ParameterConstant.RECORD_FLAG_CURRENT);
                } else if (parmTypeId.equals("0305")) {

                    verNum = this.getLogicIccVerNum(operatorId);
                    parms.add("0305" + verNum + "" + ParameterConstant.RECORD_FLAG_CURRENT);
                } else {
                    parms.add(parm);
                }
            }
        }
    }

    private String orderLccArrStr(String lccStr) {
        String returnStr = "0";
//      默认取0 如果lccArr包含0 则只取0 其他不取
        if (lccStr != null && !lccStr.isEmpty()) {
            Set<String> lccSet = new HashSet();
            String[] lccArr = lccStr.split(",");
            for(String string : lccArr){
                String lccLinesid = paramDownMapper.getlccLineId(string);//线路中文转换ID
                
                lccSet.add(lccLinesid);
            }
//            for (String string : lccArr) {
//                lccSet.add(string);
//            }
            if (!lccSet.contains("0")) {
                returnStr = lccStr;
            }
        }
        return returnStr;
    }

    private int down1(List<String> parms, List<String> lccLines, String operatorId) throws Exception {
        int water_no = 0;
//        下发lccLines相同的为同一条消息
//        不同lccLines分开为其他信息
//        即按lccLines不同分组。
        Map<String, List<String>> groupParms = new HashMap<String, List<String>>();
        for (int i = 0; i < lccLines.size(); i++) {
            
            String linStr = lccLines.get(i);//取出下发的lcc线路id
            String[] lineArr = linStr.split(",");
            for (String string : lineArr) {
                String lccLinesid = paramDownMapper.getlccLineId(string);//线路中文转换ID
                if (groupParms.get(lccLinesid) == null) {
                    groupParms.put(lccLinesid, new ArrayList<String>());
                }
                groupParms.get(lccLinesid).add(parms.get(i));
            }
        }
        //20160512 add by mqf 获取参数对应应用线路中文列表

        Vector appLineNames = this.getAppLineNames(parms, lccLines);

        if (groupParms.size() > 0) {
            for (Map.Entry<String, List<String>> entry : groupParms.entrySet()) {
                water_no = this.downOne(entry.getValue(), entry.getKey(), operatorId);
            }
        }
//        //更新参数对应应用线路中文
        this.updateAppLineNames(appLineNames);

        return water_no;
    }

    private Vector getAppLineNames(List<String> parms, List<String> lccLines) throws Exception {
        Vector results = new Vector();
        //参数下发归类表

        List<ParamDown> parmDownTypesMap = paramDownMapper.getParmDownTypes();

        Map<String, String> pd = new HashMap<String, String>();
        for (ParamDown pdm : parmDownTypesMap) {
            pd.put(pdm.getParm_type_id(), pdm.getParm_type_down_type());
        }
        //查询线路对应的大线路中文信息
        List<ParamDown> lineTypesDes = paramDownMapper.findLineTypesDescription();

        //查询线路中文 
        Vector lineNames = this.getLineNames();
        // 获取 "轨道线/磁浮线"
        String allLineTypesDes = getAllLineTypesDes(lineTypesDes);

        Map<String, List<String>> groupParms = new HashMap<String, List<String>>();
        for (int i = 0; i < lccLines.size(); i++) {
            String parm = parms.get(i);
            String linStr = lccLines.get(i);
            String[] lineArr = linStr.split(",");
            
            String appLineName = "";
            for (String lineId : lineArr) {
                String parmTypeId = parm.substring(0, 4);
                String parmTypeDownType = pd.get(parmTypeId);
                if (null == parmTypeDownType) {
                    return results;
                }
                String lccLinesid = paramDownMapper.getlccLineId(lineId);//线路中文转换ID
//                if (lccLinesid.equals("0")) {
////                    1：线网 2：单线路
//                    if (parmTypeDownType.equals(OpCodeConstant.PARM_TYPE_DOWN_TYPE_LINE_NETWORK)) {
//                        appLineName = allLineTypesDes;
//                    } else if (parmTypeDownType.equals(OpCodeConstant.PARM_TYPE_DOWN_TYPE_SINGLE_LINE)) {
//                        appLineName = "全部线路";
//                    }
//                } else {
//                    1：线网 2：单线路
                    if (parmTypeDownType.equals(OpCodeConstant.PARM_TYPE_DOWN_TYPE_LINE_NETWORK)) {
                        appLineName = this.getLineTypeDes(lccLinesid, lineTypesDes, appLineName);//轨道线/虚拟线
                    } else if (parmTypeDownType.equals(OpCodeConstant.PARM_TYPE_DOWN_TYPE_SINGLE_LINE)) {
                        appLineName = this.getLineTypeDes1(lccLinesid, lineNames, appLineName);//一号线/二号线
                    }
//                }
            }
            Object[] objs = {parm, appLineName};
            results.add(objs);

        }
        return results;
    }

    private Vector getLineNames() throws Exception {
        //长沙磁浮快线 改为磁浮线 与页面数据一致（st_cod_lcc_line）
        Vector lineNames = paramDownMapper.findLineNames();
        for (Object obj : lineNames) {
            ParamDown lineNamesDesVo = (ParamDown) obj;
            if (lineNamesDesVo.getLine_id().equals("60")) {
                lineNamesDesVo.setLine_name("磁浮线");
            }
        }
        return lineNames;
    }

    // 获取 "轨道线/磁浮线"
    private String getAllLineTypesDes(List lineTypesDes) {
        // 获取 "轨道线/磁浮线"
        String allLineTypesDes = "";
        for (Object obj : lineTypesDes) {
            ParamDown lineTypesDesVo = (ParamDown) obj;
            String codeText = lineTypesDesVo.getDescription();
            if ("".equals(allLineTypesDes)) {
                allLineTypesDes = codeText;
            } else if (allLineTypesDes.indexOf(codeText) == -1) {
                allLineTypesDes = allLineTypesDes + "/" + codeText;
            }
        }
        return allLineTypesDes;
    }

    private String getLineTypeDes(String lineId, List lineTypesDes, String appLineName) {
        for (Object obj : lineTypesDes) {
            ParamDown lineTypesDesVo = (ParamDown) obj;
            if (lineId.equals(lineTypesDesVo.getLine_id())) {
                String codeText = lineTypesDesVo.getDescription();
                if ("".equals(appLineName)) {
                    appLineName = codeText;
                } else if (appLineName.indexOf(codeText) == -1) {
                    appLineName = appLineName + "/" + codeText;
                }
                break;
            }
        }
        return appLineName;
    }

    private String getLineTypeDes1(String lineId, Vector lineNames, String appLineName) {
        for (Object obj : lineNames) {
            ParamDown lineTypesDesVo = (ParamDown) obj;
            if (lineId.equals(lineTypesDesVo.getLine_id())) {
                String codeText = lineTypesDesVo.getLine_name();
                if ("".equals(appLineName)) {
                    appLineName = codeText;
                } else if (appLineName.indexOf(codeText) == -1) {
                    appLineName = appLineName + "/" + codeText;
                }
                break;
            }
        }
        return appLineName;
    }

    private int downOne(List<String> parms, String lccLines, String operatorId) {

        Vector<String> lineID = paramDownMapper.getLccLineID();
        paramDownMapper.updateWaterNo();
        String waterNo = paramDownMapper.getWaterNo();
        int n = 0;
        String degradeVerNum = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (waterNo != null) {
            ParamDown pd = new ParamDown();
            pd.setWater_no(waterNo);
            pd.setDistribute_datetime(sdf.format(new Date()));
            pd.setOperator_id(operatorId);
            pd.setDistribute_result("0");
            n = paramDownMapper.insterDistribute(pd);
            Iterator parmsItr = parms.iterator();
            while (parmsItr.hasNext()) {
                String verNumId = parmsItr.next().toString();
                String parmTypeId = verNumId.substring(0, 4);
                String verNum = verNumId.substring(4, 14);
                String verType = verNumId.substring(14, 15);
                //取0501参数的参数版本
                if (parmTypeId.equals("0501")) {
                    degradeVerNum = verNum;
                }
                ParamDown pd1 = new ParamDown();
                pd1.setWater_no(waterNo);
                pd1.setVersion_no(verNum);
                pd1.setParm_type_id(parmTypeId);
                pd1.setVersion_type(verType);
                pd1.setGen_result("0");
                n = paramDownMapper.insterGenDtl(pd1);

            }
            String lcclines = lccLines;
            if (lcclines.equals("0")) {
                Iterator lineItr = lineID.iterator();
                while (lineItr.hasNext()) {
                    //参数下发情况通知车站明细
                    String strLineId = lineItr.next().toString();
                    ParamDown pd2 = new ParamDown();
                    pd2.setWater_no(waterNo);
                    pd2.setStation_id("00");
                    pd2.setLine_id(strLineId);
                    pd2.setInform_result("0");
                    n = paramDownMapper.insterInformDtl(pd2);
                }
            } else {
                //参数下发情况通知车站明细
                String strLineId = lcclines;
                ParamDown pd3 = new ParamDown();
                pd3.setWater_no(waterNo);
                pd3.setStation_id("00");
                pd3.setLine_id(strLineId);
                pd3.setInform_result("0");
                n = paramDownMapper.insterInformDtl(pd3);
            }
        }
        return Integer.parseInt(waterNo);
    }

    private int updateAppLineNames(Vector appLineNames) {
        if (null == appLineNames || appLineNames.size() == 0) {
            return 0;
        }
        int n = 0;
        Vector devProgramValues = new Vector();
        for (Object obj : appLineNames) {
            Object[] objs = (Object[]) obj;
            String parm = (String) objs[0];
            String appLineName = (String) objs[1];

            String parmTypeId = parm.substring(0, 4);
            String verNum = parm.substring(4, 14);
            String verType = parm.substring(14, 15);
            ParamDown p = new ParamDown();
            p.setApp_line_name(appLineName);
            p.setVersion_no(verNum);
            p.setParm_type_id(parmTypeId);
            p.setVersion_type(verType);
            if (ParameterConstant.FARE_ALL.equals(parmTypeId)) {
                ParamDown p1 = new ParamDown();
                p1.setApp_line_name(appLineName);
                p1.setVersion_no(verNum);
                p1.setParm_type_id(ParameterConstant.FARE_ZONE);
                p1.setVersion_type(verType);
                try {
                    paramDownMapper.updateDevProgram(p1);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                ParamDown p2 = new ParamDown();
                p2.setApp_line_name(appLineName);
                p2.setVersion_no(verNum);
                p2.setParm_type_id(ParameterConstant.FARE_CONF);
                p2.setVersion_type(verType);
                paramDownMapper.updateDevProgram(p2);
                ParamDown p3 = new ParamDown();
                p3.setApp_line_name(appLineName);
                p3.setVersion_no(verNum);
                p3.setParm_type_id(ParameterConstant.FARE_TABLE);
                p3.setVersion_type(verType);
                paramDownMapper.updateDevProgram(p3);
                ParamDown p4 = new ParamDown();
                p4.setApp_line_name(appLineName);
                p4.setVersion_no(verNum);
                p4.setParm_type_id(ParameterConstant.HOLIDAY_TABLE);
                p4.setVersion_type(verType);
                paramDownMapper.updateDevProgram(p4);
                ParamDown p5 = new ParamDown();
                p5.setApp_line_name(appLineName);
                p5.setVersion_no(verNum);
                p5.setParm_type_id(ParameterConstant.OFF_PEAK_HOURS);
                p5.setVersion_type(verType);
                paramDownMapper.updateDevProgram(p5);
                
                //20181022 moqf 更新0406、0407的w_op_prm_para_ver
                ParamDown pFareTimeInterval = new ParamDown();
                pFareTimeInterval.setApp_line_name(appLineName);
                pFareTimeInterval.setVersion_no(verNum);
                pFareTimeInterval.setParm_type_id(ParameterConstant.FARE_TIME_INTERVAL);
                pFareTimeInterval.setVersion_type(verType);
                paramDownMapper.updateDevProgram(pFareTimeInterval);
                
                ParamDown pFareDealTotal = new ParamDown();
                pFareDealTotal.setApp_line_name(appLineName);
                pFareDealTotal.setVersion_no(verNum);
                pFareDealTotal.setParm_type_id(ParameterConstant.FARE_DEAL_TOTAL);
                pFareDealTotal.setVersion_type(verType);
                paramDownMapper.updateDevProgram(pFareDealTotal);

            } else if (ParameterConstant.TVM_MAP.equals(parmTypeId)
                    || ParameterConstant.DEV_PROGRAM_AGM.equals(parmTypeId)
                    || ParameterConstant.DEV_PROGRAM_TVM.equals(parmTypeId)
                    || ParameterConstant.DEV_PROGRAM_BOM.equals(parmTypeId)
                    || ParameterConstant.DEV_PROGRAM_AQM.equals(parmTypeId)
                    || ParameterConstant.DEV_PROGRAM_PCA.equals(parmTypeId)) {
                ParamDown p6 = new ParamDown();
                p6.setApp_line_name(appLineName);
                p6.setVersion_no(verNum);
                p6.setParm_type_id(parmTypeId);
                p6.setVersion_type(verType);
                paramDownMapper.updateDevProgram1(p6);

//                devProgramValues.add(new Object[]{appLineName, verNum, parmTypeId, verType});
            }
            n = paramDownMapper.updateDevProgram(p);
        }
//        if (devProgramValues.size() > 0) {
//
//            for (Object obj : devProgramValues) {
//                Object[] values = (Object[]) obj;
//
//                try {
//                    n += paramDownMapper.updateDevProgram1(values);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//                values = null;
//
//            }
//
//        }

        return n;

    }

    private String getDegradeVerNum(String operatorId) {
        String verNum = null;
        int n = 0;
        String max = null;
        String water_no;
        SimpleDateFormat formatMode = new SimpleDateFormat("yyyyMMdd");
        water_no = paramDownMapper.getWaterNoForDegrade();
        if (water_no != null) {
            verNum = formatMode.format(new Date());
            if (water_no.substring(0, 8).equals(verNum)) {
                max = water_no.substring(8, 10);
                n = new Integer(max).intValue();
                n++;
                max = Integer.toString(n);
                if (max.length() == 1) {
                    max = "0" + max;
                }
                verNum = verNum + max;

            } else {
                verNum = formatMode.format(new Date()) + "01";
            }
        } else {
            verNum = formatMode.format(new Date()) + "01";

        }
        paramDownMapper.updateDrgardeModRecd(verNum);
        if(water_no == null || water_no== ""){
            paramDownMapper.insertWaterNo(verNum);
        }
//        try {
//            paramDownMapper.insertWaterNo(verNum);
//        } catch (Exception e) {
//            e.printStackTrace(); 
//        }
        paramDownMapper.updateWaterNoForDegrade(verNum);
        return verNum;
    }

    private List<ParamDown> getAllPriviledges(ParamDown queryCondition) {
        Vector parmVector = new Vector();
        List<ParamDown> list = null;
        List<ParamDown> n = null;
        List<ParamDown> j;
        try {
            list = paramDownMapper.getAllPriviledges(queryCondition);
        } catch (Exception e) {
            e.printStackTrace();
        }
//                当前版本的版本号不能小于今天 未来版本的版本号不能大于今天
//                if (checkDownTime(list)) {
//                    parmVector.add(list);
//                }
        for (ParamDown pd : list) {
            parmVector.add(pd);
        }

        try {

            n = paramDownMapper.findDegradeModeRecd();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (n.size()!=0) {
            ParamDown pd = new ParamDown();
            pd.setParm_type_name("降级模式");
            pd.setVersion_no("0000000000");
            pd.setParm_type_id("0501");
            pd.setVersion_type("0");
            pd.setRecord_flag("0");
            pd.setSelectLccLine("1");
            parmVector.add(pd);
        }
        
        j = paramDownMapper.findLogicIccList();
        if (j.size()!= 0) {
             ParamDown pd = new ParamDown();
            pd.setParm_type_name("逻辑卡号刻印号对照表");
            pd.setVersion_no(this.getLogicIccVerNum());
            pd.setParm_type_id("0305");
            pd.setVersion_type("0");
            pd.setSelectLccLine("1");
            pd.setRecord_flag("0");
            parmVector.add(pd);
        }
        return parmVector;
    }

    /**
     * 获取逻辑卡号刻印号版本号
     *
     * @param operatorId
     * @return
     * @throws Exception
     */
    public String getLogicIccVerNum() {
        String verNum = null;
        int n = 0;
        String max = null;
        SimpleDateFormat formatMode = new SimpleDateFormat("yyyyMMdd");
        Date newDate = new Date();
        String versionDate = formatMode.format(newDate);
        try {
            String versionNo = paramDownMapper.getVersionNo();

            if (versionNo != null) {
                verNum = formatMode.format(newDate);
                if (versionNo.substring(0, 8).equals(verNum)) {
                    max = versionNo.substring(8, 10);
                    n = new Integer(max).intValue();
                    n++;
                    max = Integer.toString(n);
                    if (max.length() == 1) {
                        max = "0" + max;
                    }
                    verNum = verNum + max;

                } else {
                    verNum = formatMode.format(newDate) + "01";
                }

            } else {
                verNum = formatMode.format(newDate) + "01";
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return verNum;
    }

    private OperationResult queryForOp(HttpServletRequest request, ParamDownMapper paramDownMapper, OperationLogMapper operationLogMapper, OperationResult opResult) throws Exception {
       LogUtil logUtil = new LogUtil();
        ParamDown queryCondition;
        List<ParamDown> resultSet; 

        try {
            queryCondition = this.getQueryConditionForOp(request);
            if (queryCondition == null) {
                return null;
            }
            resultSet = paramDownMapper.getAllPriviledges(queryCondition);
            opResult.setReturnResultSet(resultSet);
            //or.addMessage("成功查询" + resultSet.size() + "条记录");
        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, operationLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + resultSet.size() + "条记录", operationLogMapper);
        return opResult;
    }

   public String getLogicIccVerNum(String operatorId) throws Exception {
        String verNum = null;
        int n = 0;
        String max = null;
        SimpleDateFormat formatMode = new SimpleDateFormat("yyyyMMdd");
        Date newDate = new Date();
        String versionDate = formatMode.format(newDate);
        String versionNo;
        try {
            versionNo = paramDownMapper.findVersionNo();
            if (versionNo!=null) {
                verNum = formatMode.format(newDate);
                if (versionNo.substring(0, 8).equals(verNum)) {
                    max = versionNo.substring(8, 10);
                    n = new Integer(max).intValue();
                    n++;
                    max = Integer.toString(n);
                    if (max.length() == 1) {
                        max = "0" + max;
                    }
                    verNum = verNum + max;

                } else {
                    verNum = formatMode.format(newDate) + "01";
                }

            } else {
                verNum = formatMode.format(newDate) + "01";
            }

            paramDownMapper.updateLogicIccList();

//
//            ParamDBUtil.updateParam(dbHelper, operatorId, ParamTypeConstant.LOGIC_ICC,
//                    ApplicationConstant.RECORD_FLAG_CURRENT, verNum, versionDate, versionDate);



        } catch (Exception e) {
            e.printStackTrace();
        } 
        return verNum;
    }

       private OperationResult queryCondition(HttpServletRequest request, ParamDownMapper paramDownMapper, OperationLogMapper operationLogMapper) throws Exception {
        OperationResult or = new OperationResult();
        LogUtil logUtil = new LogUtil();
        ParamDown queryCondition;
        List<ParamDown> resultSet;
        try {
            queryCondition = this.getQueryCondition(request);
            resultSet = paramDownMapper.getparamDown(queryCondition);
            or.setReturnResultSet(resultSet);
            or.addMessage("成功查询" + resultSet.size() + "条记录");
        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, operationLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + resultSet.size() + "条记录", operationLogMapper);
        return or;
    }

}
