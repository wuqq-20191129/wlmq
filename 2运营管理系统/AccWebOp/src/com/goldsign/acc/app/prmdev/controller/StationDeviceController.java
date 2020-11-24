/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.prmdev.controller;

import com.goldsign.acc.app.prmdev.entity.StationDevice;
import com.goldsign.acc.app.prmdev.mapper.StationDeviceMapper;
import com.goldsign.acc.app.prminfo.entity.PrmVersion;
import com.goldsign.acc.app.prminfo.mapper.PrmVersionMapper;
import com.goldsign.acc.frame.constant.CommandConstant;
import com.goldsign.acc.frame.constant.ImportConstant;
import com.goldsign.acc.frame.constant.LogConstant;
import com.goldsign.acc.frame.constant.ParameterConstant;

import com.goldsign.acc.frame.controller.PrmBaseController;
import com.goldsign.acc.frame.entity.PubFlag;
import com.goldsign.acc.frame.mapper.OperationLogMapper;
import com.goldsign.acc.frame.util.CharUtil;
import com.goldsign.acc.frame.util.DBUtil;
import com.goldsign.acc.frame.util.ExpUtil;

import com.goldsign.acc.frame.util.FormUtil;
import com.goldsign.acc.frame.util.LogUtil;
import com.goldsign.acc.frame.vo.ImportResult;
import com.goldsign.acc.frame.vo.OperationResult;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.TransactionStatus;
import org.springframework.util.StringUtils;

import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 车站设备
 * @author hejj
 */
@Controller
public class StationDeviceController extends PrmBaseController {

    @Autowired
    private StationDeviceMapper stationDeviceMapper;

    Logger logger = Logger.getLogger(StationDeviceController.class);
    
    @RequestMapping("/StationDevice")
    public ModelAndView service(HttpServletRequest request, HttpServletResponse response) {

        ModelAndView mv = new ModelAndView("/jsp/prmdev/station_device.jsp");

        String command = request.getParameter("command");
        OperationResult opResult = new OperationResult();
        try {
            if (command != null) {
                command = command.trim();
                if (command.equals(CommandConstant.COMMAND_MODIFY))//修改操作
                {
                    opResult = this.modify(request, this.stationDeviceMapper, this.prmVersionMapper, this.operationLogMapper);
                }
                if (command.equals(CommandConstant.COMMAND_DELETE))//删除操作
                {
                    opResult = this.delete(request, this.stationDeviceMapper, this.prmVersionMapper, this.operationLogMapper);
                }
                if (command.equals(CommandConstant.COMMAND_ADD))//增加操作
                {
                    opResult = this.add(request, this.stationDeviceMapper, this.prmVersionMapper, this.operationLogMapper);
                }

                if (command.equals(CommandConstant.COMMAND_QUERY))//查询操作
                {
                    opResult = this.query(request, this.stationDeviceMapper, this.operationLogMapper);

                }
                if (command.equals(CommandConstant.COMMAND_SUBMIT))//提交操作
                {
                    opResult = this.submit(request, this.stationDeviceMapper, this.prmVersionMapper, this.operationLogMapper);
                }
                if (command.equals(CommandConstant.COMMAND_CLONE))//克隆操作
                {
                    opResult = this.clone(request, this.stationDeviceMapper, this.prmVersionMapper, this.operationLogMapper);

                }
//                if (command.equals(CommandConstant.COMMAND_IMPORT))//导入操作
//                {
//                    opResult = this.uploadImport(request);
//                    opResult = this.query(request, this.stationDeviceMapper, this.operationLogMapper);
//                }
                if (command.equals("importStationDevice"))//打开导入页面操作
                {
                    mv = new ModelAndView("/jsp/prmdev/importStationDevPara.jsp");
                    mv.addObject("Type", FormUtil.getParameter(request, "Type"));
                    return mv;
                }
            }

            if (command != null) {
                if (command.equals(CommandConstant.COMMAND_ADD) || command.equals(CommandConstant.COMMAND_DELETE) || command.equals(CommandConstant.COMMAND_MODIFY)
                        || command.equals(CommandConstant.COMMAND_CLONE) || command.equals(CommandConstant.COMMAND_SUBMIT)) {
                    this.queryForOp(request, this.stationDeviceMapper, this.operationLogMapper, opResult);
                }
                if (!command.equals(CommandConstant.COMMAND_ADD) || !command.equals(CommandConstant.COMMAND_QUERY)) {
                    this.saveQueryControlDefaultValues(request, mv);//保存查询条件
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        String[] attrNames = {LINES, STATIONS, LINE_STATIONS, DEV_TYPES, MERCHANTS, VERSION};

        this.setPageOptions(attrNames, mv, request, response);//设置页面线路、车站等选项值、版本
        this.getResultSetText((List<StationDevice>) opResult.getReturnResultSet(), mv);
        this.baseHandler(request, response, mv);
        this.divideResultSet(request, mv, opResult);//结果集分页
        this.SaveOperationResult(mv, opResult);//返回结果集
        return mv;

    }
    
    @RequestMapping("/StationDeviceExportAll")
    public void exportExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {
        List results = this.getBufferElementsForCurClass(request, "com.goldsign.acc.app.prmdev.entity.StationDevice");
        String expAllFields = request.getParameter("expAllFields");
        List<Map<String, Object>> queryResults = ExpUtil.entityToMap(expAllFields, results);
        ExpUtil.renderExportExcelPath(response, ExpUtil.exportExcel(queryResults, request));
    }

    private void getResultSetText(List<StationDevice> resultSet, ModelAndView mv) {
        List<PubFlag> lines = (List<PubFlag>) mv.getModelMap().get(PrmBaseController.LINES);
        List<PubFlag> stations = (List<PubFlag>) mv.getModelMap().get(PrmBaseController.STATIONS);
        List<PubFlag> devTypes = (List<PubFlag>) mv.getModelMap().get(PrmBaseController.DEV_TYPES);
        List<PubFlag> merchants = (List<PubFlag>) mv.getModelMap().get(PrmBaseController.MERCHANTS);

        for (StationDevice sd : resultSet) {
            if (lines != null && !lines.isEmpty()) {
                sd.setLine_id_name(DBUtil.getTextByCode(sd.getLine_id(), lines));
            }
            if (stations != null && !stations.isEmpty()) {
                sd.setStation_id_name(DBUtil.getTextByCode(sd.getStation_id(), sd.getLine_id(), stations));
            }
            if (devTypes != null && !devTypes.isEmpty()) {
                sd.setDev_type_id_name(DBUtil.getTextByCode(sd.getDev_type_id(), devTypes));
            }
            if (merchants != null && !merchants.isEmpty()) {
                sd.setStore_id_name(DBUtil.getTextByCode(sd.getStore_id(), merchants));
            }

        }

    }

    private StationDevice getQueryConditionForOp(HttpServletRequest request) {

        StationDevice qCon = new StationDevice();
        HashMap vQueryControlDefaultValues = null;
        String queryControlDefaultValues = request.getParameter("queryControlDefaultValues");
        String command = request.getParameter("command");
        if (FormUtil.isAddOrModifyMode(request)) {//操作处于添加或修改模式
            if (command.equals(CommandConstant.COMMAND_DELETE)) {
                return null;
            }

            qCon.setLine_id(FormUtil.getParameter(request, "d_lineID"));
            qCon.setStation_id(FormUtil.getParameter(request,"d_stationID"));
            qCon.setDev_type_id(FormUtil.getParameter(request,"d_deviceType"));
            qCon.setDevice_id(FormUtil.getParameter(request,"d_deviceID"));
        } else {//操作处于非添加模式
            vQueryControlDefaultValues = FormUtil.getQueryControlDefaultValues(queryControlDefaultValues);
            if (!vQueryControlDefaultValues.isEmpty()) {
                qCon.setLine_id(FormUtil.getQueryControlDefaultValue(vQueryControlDefaultValues, "q_lineID"));
                qCon.setStation_id(FormUtil.getQueryControlDefaultValue(vQueryControlDefaultValues, "q_stationID"));
                qCon.setDev_type_id(FormUtil.getQueryControlDefaultValue(vQueryControlDefaultValues, "q_deviceType"));
                qCon.setDevice_id(FormUtil.getQueryControlDefaultValue(vQueryControlDefaultValues, "q_deviceID"));

                qCon.setDev_serial(FormUtil.getQueryControlDefaultValue(vQueryControlDefaultValues, "q_deviceSerial"));
            }
        }
        this.getBaseParameters(request, qCon);
        //当操作为克隆时，查询克隆后的草稿版本 注释后改为按当前条件查询
//        if (command.equals(CommandConstant.COMMAND_CLONE)) {
//            qCon.setRecord_flag(ParameterConstant.RECORD_FLAG_DRAFT);
//        }

        return qCon;
    }
    
    @RequestMapping("/import_StationDevice")
    public ModelAndView import_StationDevice(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView("/jsp/prmdev/importStationDevPara.jsp");
        try {
            String command = request.getParameter("command");
            if (command != null) {
                if (command.equals("importStationDevice")) {
                    this.importFile(request, mv);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            mv.addObject("msg", e.getMessage());
        }
        return mv;
    }

    private OperationResult importFile(HttpServletRequest request, ModelAndView mv) throws Exception {
        int n = 0;
        OperationResult or = new OperationResult();
        String terminator = request.getParameter("seperator");
        try {
            MultipartHttpServletRequest multipartRequest;
            multipartRequest = (MultipartHttpServletRequest) request;
            CommonsMultipartFile file = (CommonsMultipartFile) multipartRequest
                    .getFile("makeFile");
            InputStream is = file.getInputStream();
            n = this.importStationDeviceBySql(request, is, terminator);
            mv.addObject("msg", "成功导入" + n + "条记录");
        } catch (Exception e) {
            e.printStackTrace();
            mv.addObject("msg", "导入失败:" + e.getMessage());

        }
        or.addMessage(LogConstant.importSuccessMsg(n));
        return or;
    }

    private int importStationDeviceBySql(HttpServletRequest request, InputStream stream, String terminator) throws Exception {
        int n = 0;
        //读取导入文件的记录
        InputStreamReader isr = null;
        BufferedReader br = null;
        String line;
        int i = 1;  //行标
        String errorMsg = "";

        isr = new InputStreamReader(stream);
        br = new BufferedReader(isr);
        List<PubFlag> optionLines = pubFlagMapper.getLines();
        List<PubFlag> optionStations = pubFlagMapper.getStations();
        List<PubFlag> optionDevTypes = pubFlagMapper.getDevTypes();
        List<String[]> listField = new ArrayList<String[]>();
        
        while ((line = br.readLine()) != null) {
            String[] fields = line.split(terminator);
            String[] fieldName = {"线路ID","车站ID","设备类型ID","设备ID","设备IP地址","设备名称","设备序列号","闸机/TVM阵列编码","车站内区域编号"};
            //                         0    1           2           3          4        5           6           7                   8              
            //判断字段数
            if(fields.length != 9){
                errorMsg += "第 " + i + " 行的参数数量不是9;";
                i++;
                continue;
            }
            int tempNo = 0;
            for(String field: fields){
                if(StringUtils.isEmpty(field)){
                    errorMsg += "第 " + i + " 行的"+ fieldName[tempNo] +"列为空;";
                }
                tempNo++;
            }
            
            //ID 要是数据库中存在的
            //线路ID,车站ID,设备类型ID    2位数字  应该在对应的字典表中
            if(!isIdExist(fields[0], optionLines)){
                errorMsg += "第 " + i + " 行的线路ID不存在;";
            }
            if(!isIdExist(fields[1], optionStations)){
                errorMsg += "第 " + i + " 行的车站ID不存在;";
            }
            if(!isIdExist(fields[2], optionDevTypes)){
                errorMsg += "第 " + i + " 行的设备类型ID不存在;";
            }
            //设备ID,
            if(!isNumNo(fields[3],3)){
                errorMsg += "第 " + i + " 行的设备ID应为3位数字;";
            }
            //判断设备IP地址是不格式正确
            if(!isIP(fields[4])){
                errorMsg += "第 " + i + " 行的设备IP地址格式不正确;";
            }
            //设备名称长度为1-30个字符
            if(fields[5].length()<1 || fields[5].length()>30){
                errorMsg += "第 " + i + " 行的设备名称长度为1-30个字符;";
            }
            //设备序列号应为数字、字母组合  最多为20个字符
            if(fields[6].length()>20){
                errorMsg += "第 " + i + " 行的设备序列号最多为20字符;";
            }
            if(!isNumAndCharacter(fields[6])){
                errorMsg += "第 " + i + " 行的设备序列号应为数字、字母组合;";
            }
            
            //闸机/TVM阵列编码,车站内区域编号     3位数字
            if(!isNumNo(fields[7],3)){
                errorMsg += "第 " + i + " 行的闸机/TVM阵列编码应为3位数字;";
            }
            if(!isNumNo(fields[8],3)){
                errorMsg += "第 " + i + " 行的车站内区域编号应为3位数字;";
            }
            String[] fieldsNew = insertElement(fields, String.valueOf(i), fields.length);
            listField.add(fieldsNew);
            i++;
        }
        if (i == 0) {
            throw new Exception("导入的文件不能为空!");
        }
        if(!errorMsg.equals("")){
            throw new Exception(errorMsg);
        }
       
        List<StationDevice> listStationDevices = new ArrayList<StationDevice>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String type = request.getParameter("Type");
        //如果异常信息为空,校验通过
        for(String[] strings : listField){
            StationDevice sd = new StationDevice();
            sd.setLine_id(strings[0]);
            sd.setStation_id(strings[1]);
            sd.setDev_type_id(strings[2]);
            sd.setDevice_id(strings[3]);
            sd.setIp_address(strings[4]);
            sd.setDev_name(strings[5]);
            sd.setDev_serial(strings[6]);
            sd.setArray_id(strings[7]);
            sd.setZone_id(strings[8]);
            sd.setConfig_date(sdf.format(new Date()));
            sd.setIndexTemp(strings[9]);  //在文件中的第几行
            this.getBaseParameters(request, sd);   //获取用户名和版本
            sd.setVersion_no(ParameterConstant.VERSION_NO_DRAFT);
            sd.setRecord_flag(ParameterConstant.RECORD_FLAG_DRAFT);
            sd.setParm_type_id(type);
            listStationDevices.add(sd);
        }
        //保存数据
        return importByTrans(listStationDevices);
    }
    
    private static String[] insertElement(String original[],
        String element, int index) {
           int length = original.length;
           String destination[] = new String[length + 1];
           System.arraycopy(original, 0, destination, 0, index);
           destination[index] = element;
           System.arraycopy(original, index, destination, index
           + 1, length - index);
           return destination;
    }
    
    //由于表中的数据量比较大，就不做全文件校验是否已经存在，做单行拦截校验
    private int importByTrans(List<StationDevice> poList) throws Exception {
        TransactionStatus status = null;
        int n = 0;
        StationDevice poTemp = new StationDevice();
        try {
            status = txMgr.getTransaction(this.def);
            for(StationDevice po : poList){
                poTemp = po;
                stationDeviceMapper.importStationDevice(po);
                prmVersionMapper.modifyPrmVersionForDraft(po);
                n++;
            }
            txMgr.commit(status);
        } catch (Exception e) {
            if (txMgr != null) {
                txMgr.rollback(status);
            }
            e.printStackTrace();
            throw new Exception("第"+ poTemp.getIndexTemp() +"行记录已经存在或保存数据库不成功，请确认");
        }
        return n;
    }
    
    //判断是否是正确的IP地址
    public static boolean isIP(String addr){  
        if(addr.length() < 7 || addr.length() > 15 || "".equals(addr)){  
            return false;  
        }  
        //排除前面带零的影响    10.04.01.108
        String strTemp = ""; 
        try {
            for(String temp : addr.split("\\.")){
                strTemp += Integer.parseInt(temp) + ".";
            }
        } catch (Exception e) {
            return false;
        }
        
        //判断IP格式和范围  
        String rexp = "([1-9]|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])(\\.(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])){3}";  
        Pattern pat = Pattern.compile(rexp);    
        Matcher mat = pat.matcher(strTemp.substring(0,strTemp.length()-1));    
        boolean ipAddress = mat.find();  
        return ipAddress;  
    }  
    
    //对应ID 是否存在
    public boolean isIdExist(String id , List<PubFlag> list){
        for(PubFlag pf : list){
            if(pf.getCode().equals(id)){
                return true;
            }
        }
        return false;
    }
    
    //数字和字母的组合
    public boolean isNumAndCharacter(String str){
        Pattern pattern = Pattern.compile("^[0-9A-Za-z]*$");
        Matcher isNum = pattern.matcher(str);
        if( isNum.matches() ){
            return true;
        }
        return false;
    }
    
    /**
     * 判断几位数字
     * @param str 字段值
     * @param numNo 数字位数
     * @return
     * @throws Exception 
     */
    public boolean isNumNo(String str , int numNo)throws Exception{
        Pattern pattern = Pattern.compile("^[0-9]{"+ numNo +"}$");
        Matcher isNum = pattern.matcher(str);
        if(isNum.matches() ){ 
            return true;
        }
        return false;
    }

    private StationDevice getQueryCondition(HttpServletRequest request) {
        StationDevice qCon = new StationDevice();
        qCon.setLine_id(FormUtil.getParameter(request, "q_lineID"));
        qCon.setStation_id(FormUtil.getParameter(request, "q_stationID"));
        qCon.setDev_type_id(FormUtil.getParameter(request, "q_deviceType"));
        qCon.setDevice_id(FormUtil.getParameter(request, "q_deviceID"));
        qCon.setDev_serial(FormUtil.getParameter(request, "q_deviceSerial"));

        this.getBaseParameters(request, qCon);
        return qCon;
    }

    public OperationResult query(HttpServletRequest request, StationDeviceMapper sdMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult or = new OperationResult();
        LogUtil logUtil = new LogUtil();
        StationDevice queryCondition;
        List<StationDevice> resultSet;

        try {
            queryCondition = this.getQueryCondition(request);
            resultSet = sdMapper.getStationDevices(queryCondition);
            or.setReturnResultSet(resultSet);
            or.addMessage("成功查询" + resultSet.size() + "条记录");
        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + resultSet.size() + "条记录", opLogMapper);
        return or;

    }

    public OperationResult queryForOp(HttpServletRequest request, StationDeviceMapper sdMapper, OperationLogMapper opLogMapper, OperationResult opResult) throws Exception {
        // OperationResult or = new OperationResult();
        LogUtil logUtil = new LogUtil();
        StationDevice queryCondition;
        List<StationDevice> resultSet;

        try {
            queryCondition = this.getQueryConditionForOp(request);
            if (queryCondition == null) {
                return null;
            }
            resultSet = sdMapper.getStationDevices(queryCondition);
            opResult.setReturnResultSet(resultSet);
            //or.addMessage("成功查询" + resultSet.size() + "条记录");
        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + resultSet.size() + "条记录", opLogMapper);
        return opResult;

    }

    public OperationResult modify(HttpServletRequest request, StationDeviceMapper sdMapper, PrmVersionMapper pvMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        StationDevice po = this.getReqAttribute(request);
        //User user = (User) request.getSession().getAttribute("User");
        // String operatorID = user.getAccount();
        LogUtil logUtil = new LogUtil();
        int n = 0;

        String preMsg = "车站设备：" + "主键：" + "线路" + po.getLine_id() + "车站" + po.getStation_id() + "设备类型"
                + po.getDev_type_id() + "设备ID" + po.getDevice_id() + ":";
        if (CharUtil.getDBLenth(po.getDev_name()) > 30) {
            rmsg.addMessage(preMsg + "车站设备名称最大值不能超过30位(中文字符为两位)。");
            return rmsg;
        } 
         if(po.getStore_id() != null && !po.getStore_id().trim().equals("")){
            if(!Pattern.matches("[0-9]{5}", po.getStore_id())){
                rmsg.addMessage(preMsg + "商户ID应为5位数字。");
                return rmsg;
            } 
        }
        try {
            n = this.modifyByTrans(request, sdMapper, pvMapper, po);
        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_MODIFY, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_MODIFY, request, preMsg + LogConstant.modifySuccessMsg(n), opLogMapper);
        
        rmsg.addMessage(LogConstant.modifySuccessMsg(n));

        return rmsg;
    }

    private boolean existRecord(StationDevice po, StationDeviceMapper sdMapper, OperationLogMapper opLogMapper) {
        List<StationDevice> list = sdMapper.getStationDeviceById(po);
        if (list.isEmpty()) {
            return false;
        }
        return true;

    }

    private int addByTrans(HttpServletRequest request, StationDeviceMapper sdMapper, PrmVersionMapper pvMapper, StationDevice po) throws Exception {
        TransactionStatus status = null;
        int n = 0;
        try {

            status = txMgr.getTransaction(this.def);
            n = sdMapper.addStationDevice(po);
            pvMapper.modifyPrmVersionForDraft(po);

            txMgr.commit(status);
        } catch (Exception e) {
            if (txMgr != null) {
                txMgr.rollback(status);
            }
            throw e;
        }
        return n;
    }

    private void getVersionNoForSubmit(StationDevice po, String versionNoMax) {
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

    

    public int submitByTrans(HttpServletRequest request, StationDeviceMapper sdMapper, PrmVersionMapper pvMapper, StationDevice po, PrmVersion prmVersion) throws Exception {

        TransactionStatus status = null;
        List<PrmVersion> prmVersions;
        String versionNoMax;
        int n = 0;
        int n1 = 0;
       // String test=null;
        try {

            status = txMgr.getTransaction(this.def);
            

            //旧的未来或当前参数数据做删除标志
            sdMapper.submitToOldFlag(po);
            //生成新的未来或当前参数的版本号
            versionNoMax = pvMapper.selectPrmVersionForSubmit(po);
            this.getVersionNoForSubmit(po, versionNoMax);

            //添加新的未来或当前参数的数据记录
            n = sdMapper.submitFromDraftToCurOrFur(po);
            // 重新生成参数表中的未来或当前参数记录
            pvMapper.modifyPrmVersionForSubmit(po);
            pvMapper.addPrmVersion(po);
          //  test.getBytes();

            txMgr.commit(status);
        } catch (Exception e) {
            if (txMgr != null) {
                txMgr.rollback(status);
            }
            throw e;
        }
        return n;
    }

    

    private int cloneByTrans(HttpServletRequest request, StationDeviceMapper sdMapper, PrmVersionMapper pvMapper, StationDevice po, PrmVersion prmVersion) throws Exception {
        //DataSourceTransactionManager txMgr = null;
        TransactionStatus status = null;
        List<PrmVersion> prmVersions;
       // String versionNoMax;
        int n = 0;
        int n1 = 0;
        try {

           // txMgr = DBUtil.getDataSourceTransactionManager(request);
            status = txMgr.getTransaction(this.def);

            //删除草稿版本
            sdMapper.deleteStationDevicesForClone(po);
            //未来或当前参数克隆成草稿版本
            n = sdMapper.cloneFromCurOrFurToDraft(po);
            //更新参数版本索引信息
            pvMapper.modifyPrmVersionForDraft(po);

            txMgr.commit(status);
        } catch (Exception e) {
            if (txMgr != null) {
                txMgr.rollback(status);
            }
            throw e;
        }
        return n;
    }

    public OperationResult submit(HttpServletRequest request, StationDeviceMapper sdMapper, PrmVersionMapper pvMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        StationDevice po = this.getReqAttributeForSubmit(request);
        StationDevice prmVersion = null;
        //User user = (User) request.getSession().getAttribute("User");
        // String operatorID = user.getAccount();
        LogUtil logUtil = new LogUtil();
        int n = 0;

        try {

            n = this.submitByTrans(request, sdMapper, pvMapper, po, prmVersion);

        } catch (Exception e) {
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_SUBMIT, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_SUBMIT, request, LogConstant.submitSuccessMsg(n, po.getRecord_flag_new()), opLogMapper);

        rmsg.addMessage(LogConstant.submitSuccessMsg(n, po.getRecord_flag_new()));

        return rmsg;
    }

    public OperationResult clone(HttpServletRequest request, StationDeviceMapper sdMapper, PrmVersionMapper pvMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        StationDevice po = this.getReqAttributeForClone(request);
        StationDevice prmVersion = null;
       // User user = (User) request.getSession().getAttribute("User");
        // String operatorID = user.getAccount();
        LogUtil logUtil = new LogUtil();
        int n = 0;

        try {

            n = this.cloneByTrans(request, sdMapper, pvMapper, po, prmVersion);

        } catch (Exception e) {
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_CLONE, e, opLogMapper);
        }
        //当前还是未来？
        logUtil.logOperation(CommandConstant.COMMAND_CLONE, request, LogConstant.cloneSuccessMsg(n), opLogMapper);

        rmsg.addMessage(LogConstant.cloneSuccessMsg(n));

        return rmsg;
    }

    private int modifyByTrans(HttpServletRequest request, StationDeviceMapper sdMapper, PrmVersionMapper pvMapper, StationDevice po) throws Exception {
        TransactionStatus status = null;
        int n = 0;
        try {

            status = txMgr.getTransaction(this.def);
            n = sdMapper.modifyStationDevice(po);
            pvMapper.modifyPrmVersionForDraft(po);

            txMgr.commit(status);
        } catch (Exception e) {
            if (txMgr != null) {
                txMgr.rollback(status);
            }
            throw e;
        }
        return n;
    }

    private int deleteByTrans(HttpServletRequest request, StationDeviceMapper sdMapper, PrmVersionMapper pvMapper, Vector<StationDevice> pos, StationDevice prmVo) throws Exception {

        TransactionStatus status = null;
        int n = 0;
        try {


            status = txMgr.getTransaction(this.def);
            for (StationDevice po : pos) {
                n += sdMapper.deleteStationDevice(po);
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

    public static void main(String[] args) {
        System.out.println(Pattern.matches("[0-9]{5}", "12345"));
    }
    
    public OperationResult add(HttpServletRequest request, StationDeviceMapper sdMapper, PrmVersionMapper pvMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        StationDevice po = this.getReqAttribute(request);
       // User user = (User) request.getSession().getAttribute("User");
        // String operatorID = user.getAccount();
        LogUtil logUtil = new LogUtil();
        int n = 0;

        String preMsg = "车站设备：" + "主键：" + "线路" + po.getLine_id() + "车站" + po.getStation_id() + "设备类型"
                + po.getDev_type_id() + "设备ID" + po.getDevice_id() + ":";
        if (CharUtil.getDBLenth(po.getDev_name()) > 30) {
            rmsg.addMessage(preMsg + "车站设备名称最大值不能超过30位(中文字符为两位)。");
            return rmsg;
        } 
        if(po.getStore_id() != null && !po.getStore_id().trim().equals("")){
            if(!Pattern.matches("[0-9]{5}", po.getStore_id())){
                rmsg.addMessage(preMsg + "商户ID应为5位数字。");
                return rmsg;
            } 
        }
        try {
            if (this.existRecord(po, sdMapper, opLogMapper)) {
                rmsg.addMessage("增加记录已存在！");
                return rmsg;
            }

            // n = sdMapper.addStationDevice(po);
            n = this.addByTrans(request, sdMapper, pvMapper, po);

        } catch (Exception e) {
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_ADD, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_ADD, request, preMsg + LogConstant.addSuccessMsg(n), opLogMapper);
        rmsg.addMessage(LogConstant.addSuccessMsg(n));

        return rmsg;
    }

    public OperationResult delete(HttpServletRequest request, StationDeviceMapper sdMapper, PrmVersionMapper pvMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        Vector<StationDevice> pos = this.getReqAttributeForDelete(request);
        StationDevice prmVo = new StationDevice();
        this.getBaseParameters(request, prmVo);
        //User user = (User) request.getSession().getAttribute("User");
        // String operatorID = user.getAccount();
        LogUtil logUtil = new LogUtil();
        int n = 0;

        try {
            n = this.deleteByTrans(request, sdMapper, pvMapper, pos, prmVo);
        } catch (Exception e) {
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_DELETE, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_DELETE, request, LogConstant.delSuccessMsg(n), opLogMapper);

        rmsg.addMessage(LogConstant.delSuccessMsg(n));

        return rmsg;
    }

    public StationDevice getReqAttribute(HttpServletRequest request) {
        StationDevice po = new StationDevice();

        // String cscNumber = ForUtil.getParameter("d_CSCNumber");
        po.setLine_id(FormUtil.getParameter(request, "d_lineID"));
        po.setStation_id(FormUtil.getParameter(request, "d_stationID"));
        po.setDev_type_id(FormUtil.getParameter(request, "d_deviceType"));
        po.setDevice_id(FormUtil.getParameter(request, "d_deviceID"));

        po.setCsc_num(FormUtil.getParameter(request, "d_CSCNumber"));

        po.setArray_id(FormUtil.getParameter(request, "d_arrayID"));
        po.setConcourse_id(FormUtil.getParameter(request, "d_zoneID"));
        po.setDev_serial(FormUtil.getParameter(request, "d_deviceSerial"));
        po.setIp_address(FormUtil.getParameter(request, "d_IPAddress"));
        po.setStore_id(FormUtil.getParameter(request, "d_venderID"));

        po.setConfig_date(FormUtil.getParameter(request, "d_configDate"));
        po.setDev_name(FormUtil.getParameter(request, "d_devName"));
        this.getBaseParameters(request, po);
        //--del po.setVersionNo(version);
        //--del po.setParamTypeID(type);
        return po;
    }

    public StationDevice getReqAttributeForSubmit(HttpServletRequest request) {
        StationDevice po = new StationDevice();

        this.getBaseParameters(request, po);
        this.getBaseParametersForSubmit(request, po);

        return po;
    }

    public StationDevice getReqAttributeForClone(HttpServletRequest request) {
        StationDevice po = new StationDevice();

        this.getBaseParameters(request, po);

        return po;
    }

    private Vector<StationDevice> getDeleteIDs(String selectedIds) {
        StringTokenizer st = new StringTokenizer(selectedIds, ";");
        String strIds = null;
        Vector<StationDevice> sds = new Vector();
        StationDevice sd;
        while (st.hasMoreTokens()) {
            strIds = st.nextToken();
            sd = this.getStationDevice(strIds, "#");
            sds.add(sd);
        }
        return sds;

    }

    private StationDevice getStationDevice(String strIDs, String delim) {
        StringTokenizer st = new StringTokenizer(strIDs, delim);
        String tmp = null;
        int i = 0;
        StationDevice sd = new StationDevice();;
        Vector<StationDevice> sds = new Vector();
        while (st.hasMoreTokens()) {
            i++;
            tmp = st.nextToken();
            if (i == 1) {
                sd.setLine_id(tmp);
                continue;
            }
            if (i == 2) {
                sd.setStation_id(tmp);
                continue;
            }
            if (i == 3) {
                sd.setDev_type_id(tmp);

                continue;
            }
            if (i == 4) {
                sd.setDevice_id(tmp);
                continue;
            }
            if (i == 5) {
                sd.setVersion_no(tmp);
                continue;
            }
            if (i == 6) {
                sd.setRecord_flag(tmp);
                continue;
            }

        }
        return sd;

    }

    private Vector<StationDevice> getReqAttributeForDelete(HttpServletRequest request) {
        StationDevice po = new StationDevice();
        String selectIds = request.getParameter("allSelectedIDs");
        Vector<StationDevice> selectedItems = this.getDeleteIDs(selectIds);

        //this.getBaseParameters(request, po);
        return selectedItems;
    }

//    /*
//    导入
//    */
//    private OperationResult uploadImport(HttpServletRequest request) {
//        int startNum = 3;
//        OperationResult rmsg = new OperationResult();
//        List<ArrayList<String>> list = null;
//        List<StationDevice> sdList = new ArrayList<StationDevice>();
//        //execl导出到list
//        FileImportExport fie = new FileImportExport();
//        list = fie.uploadImport(request);
//        if(list==null || list.size()<startNum){
//            rmsg.addMessage(LogConstant.addSuccessMsg(0));
//        }else{
//            //插入字段确认
//            List tabList = new ArrayList();
//            boolean checkTab = checkImportTab(tabList);
//            if(checkTab){
//                tabList.addAll(list.get(startNum));//模板第4行为数据库字段
//                //list转换bean
//                for(int i=startNum+1; i < list.size(); i++){
//                    List tmpList = list.get(i);
//                    if(tmpList.size() < 1){
//                        continue;
//                    }
//                    StationDevice sd = new StationDevice();
//                    setImportData(sd, tmpList);
//                    sdList.add(sd);
//                }
//                rmsg.addMessage(LogConstant.addSuccessMsg(list.size()-startNum));
//            }else{
//                rmsg.addMessage(LogConstant.OPERATIION_FAIL_LOG_MESSAGE+":匹配数据库字段有误！");
//            }
//        }
//        return rmsg;
//    }
//
//    /*
//    匹配数据库字段
//    */
//    private boolean checkImportTab(List tabList) {
//        return tabList.get(0).equals("line_id")
//                &&tabList.get(1).equals("station_id")
//                &&tabList.get(2).equals("dev_type_id")
//                &&tabList.get(3).equals("device_id")
//                &&tabList.get(4).equals("csc_num")
//                &&tabList.get(5).equals("array_id")
//                &&tabList.get(6).equals("concourse_id")
//                &&tabList.get(7).equals("ip_address")
//                &&tabList.get(8).equals("store_id")
//                &&tabList.get(9).equals("dev_serial")
//                &&tabList.get(10).equals("dev_name")
//                &&tabList.get(11).equals("config_date");
//    }
//
//    /*
//    list转换为bean
//    */
//    private void setImportData(StationDevice sd, List tmpList) {
//        sd.setLine_id((String) tmpList.get(0));
//        sd.setStation_id((String) tmpList.get(1));
//        sd.setDev_type_id((String) tmpList.get(2));
//        sd.setDevice_id((String) tmpList.get(3));
//        sd.setCsc_num((int) tmpList.get(4));
//        sd.setArray_id((String) tmpList.get(5));
//        sd.setConcourse_id((String) tmpList.get(6));
//        sd.setIp_address((String) tmpList.get(7));
//        sd.setStore_id((String) tmpList.get(8));
//        sd.setDev_serial((String) tmpList.get(9));
//        sd.setDev_name((String) tmpList.get(10));
//        sd.setConfig_date((String) tmpList.get(11));
//    }

}
