package com.goldsign.acc.app.weight.controller;

import com.goldsign.acc.app.weight.entity.DistanceChange;
import com.goldsign.acc.app.weight.entity.DistanceOd;
import com.goldsign.acc.app.weight.entity.ParamsStation;
import com.goldsign.acc.app.weight.entity.TransferStation;
import com.goldsign.acc.app.weight.entity.DistanceProportion;
import com.goldsign.acc.app.weight.mapper.GenerateDataMapper;
import com.goldsign.acc.app.weight.util.ProportionFileWrite;
import com.goldsign.acc.frame.constant.CommandConstant;
import com.goldsign.acc.frame.constant.RuleConstant;
import com.goldsign.acc.frame.controller.RLBaseController;
import com.goldsign.acc.frame.entity.PubFlag;
import com.goldsign.acc.frame.mapper.OperationLogMapper;
import com.goldsign.acc.frame.util.DBUtil;
import com.goldsign.acc.frame.util.ExpUtil;
import com.goldsign.acc.frame.util.FormUtil;
import com.goldsign.acc.frame.util.LogUtil;
import com.goldsign.acc.frame.util.PubUtil;
import com.goldsign.acc.frame.vo.OperationResult;
import com.goldsign.login.vo.User;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.TransactionStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 生成权重数据
 *
 * @author liudz 201709008
 */
@Controller
public class GenerateDataController extends RLBaseController {

    @Autowired
    private GenerateDataMapper gdMapper;

    private int maxId = 1;//数据库表最大ID;
    private List<TransferStation> transfers = new ArrayList<TransferStation>();//查出所有换乘站信息
    private List<ParamsStation> stationvs = new ArrayList<ParamsStation>();//所有站点参数
    private boolean changesOne = true;//首次换乘标志
    private String startStation = null;//开始站点（线路code + 站点code）临时变量
    private String defaultDistance = "0";//默认乘距
    private String defaultChangeTimes = "0";//默认换乘次数
    private List<DistanceOd> nods = new ArrayList<DistanceOd>();//未计算OD
    private Vector cods = new Vector();//已经计算OD总变量
    private Vector changes = new Vector();//换乘记录总变量
    private Vector codsO = new Vector();//单个车站循环已经计算OD
    private Vector nodsTmp = new Vector();//未计算OD临时变量
    private Vector changesO = new Vector();//单个车站循环换乘记录
    
    @RequestMapping(value = "/generateDataAction")
    public ModelAndView service(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView("/jsp/weight/generate_date.jsp");
        String command = request.getParameter("command");
        OperationResult opResult = new OperationResult();
        List<DistanceProportion> resultSet = new ArrayList<>();
        try {
            if (command != null) {
                command = command.trim();
                if (command.equals(CommandConstant.COMMAND_QUERY) || command.equals(CommandConstant.COMMAND_DOWNLOAD))//查询操作
                {
                    opResult = this.query(request, this.gdMapper, this.operationLogMapper,resultSet);
                }
                if (command.equals(CommandConstant.COMMAND_DOWNLOAD))//查询操作
                {
                    this.downFile(request, response, this.gdMapper, this.operationLogMapper,(List<DistanceProportion>) opResult.getReturnResultSet());
                    return null;
                } 
                 //生成权重数据
                if(command.equals(CommandConstant.COMMAND_GENERATE)){
                    opResult = this.generate(request, gdMapper);      
                }
                
                //生成OD数据
                if(command.equals(CommandConstant.COMMAND_GENERATE_OD)){
                    cleanVector(); 
                    opResult = this.generateOD(request, gdMapper);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //初始化下拉框
        String[] attrNames = {LINE, STATION, LINE_STATIONS, RECORDFLAG};

        this.setPageOptions(attrNames, mv, request, response);//设置页面线路、车站等选项值、版本
        this.getResultSetText((List<DistanceProportion>) opResult.getReturnResultSet(), mv);
        this.baseHandler(request, response, mv);
        if(!(command == null || command.equals(CommandConstant.COMMAND_GENERATE) || command.equals(CommandConstant.COMMAND_GENERATE_OD))){
            this.divideResultSet(request, mv, opResult);//结果集分页
        }
        this.SaveOperationResult(mv, opResult);
        return mv;
    }

    private OperationResult query(HttpServletRequest request, GenerateDataMapper gdMapper, OperationLogMapper operationLogMapper,List<DistanceProportion> resultSet) throws Exception {
        LogUtil logUtil = new LogUtil();
        DistanceProportion queryCondition;
        
        OperationResult or = new OperationResult();
        try {
            queryCondition = this.getQueryCondition(request);
            resultSet = gdMapper.getGenerateDataList(queryCondition);

            or.setReturnResultSet(resultSet);
            or.addMessage("成功查询" + resultSet.size() + "条记录");
        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, operationLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + resultSet.size() + "条记录", operationLogMapper);
        return or;
    }

    private void downFile(HttpServletRequest request, HttpServletResponse response, GenerateDataMapper gdMapper, OperationLogMapper operationLogMapper, List<DistanceProportion> resultSet) 
     throws Exception{
       //导出字段
        String params = request.getParameter("c_checkbox");
        String path = pathName(request);
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        OutputStream fos = null;
        InputStream fis = null;
         //如果是从服务器上取就用这个获得系统的绝对路径方法。 
        String filepath = request.getServletContext().getRealPath("/" + path);
        System.out.println("文件路径"+filepath);
        File uploadFile = new File(filepath);
        if(uploadFile.exists()){
            uploadFile.mkdir();
        }
        uploadFile.createNewFile();
        //将查询结果写入文件
        ProportionFileWrite.writeVectorModel(resultSet, uploadFile, params);
        
        fis = new FileInputStream(uploadFile);
        bis = new BufferedInputStream(fis);
        fos = response.getOutputStream();
        bos = new BufferedOutputStream(fos);
        
        //这个就就是弹出下载对话框的关键代码
        response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode(path, "utf-8"));
        int bytesRead = 0;
        
        //这个地方的同上传的一样。我就不多说了，都是用输入流进行先读，然后用输出流去写，唯一不同的是我用的是缓冲输入输出流
        byte[] buffer = new byte[8192];
        while ((bytesRead = bis.read(buffer, 0, 8192)) != -1) {
            bos.write(buffer, 0, bytesRead);
        }
        
        bos.flush();
        fis.close();
        bis.close();
        fos.close();
        bos.close();
        
//        af = this.getActionForward(mapping,FrameDBConstant.COMMAND_QUERY);//返回跳转页面
//        return null;
    }
    
    /**
     * 取文件名
     * @param request
     * @return 
     */
    private String pathName(HttpServletRequest request) {
        
        String path = "PROPORTION.";//临时文件名
        //查询条件
        DistanceProportion vo = new DistanceProportion();
        vo = this.getQueryCondition(request);
        
        if(stringIsNotEmpty(vo.getoLineId())){
             path += vo.getoLineId();
        }
        if(stringIsNotEmpty(vo.getoStationId())){
             path += vo.getoStationId();
        }
        if(stringIsNotEmpty(vo.getdLineId())){
             path += vo.getdLineId();
        }
        if(stringIsNotEmpty(vo.getdStationId())){
             path += vo.getdStationId();
        }
        if(path.equals("PROPORTION.")){
            path += "ALL";
        }
        
        return path + ".txt";
    }
    
     //判断字符串不为空时返回真
    public static boolean stringIsNotEmpty(String str){
        if(str == null || "".equals(str.trim())){
            return false;
        }
        return true;
    }

    private DistanceProportion getQueryCondition(HttpServletRequest request) {
        DistanceProportion dp = new DistanceProportion();
        String q_oLineId = FormUtil.getParameter(request, "q_oLineId");
        String q_oStationId = FormUtil.getParameter(request, "q_oStationId");
        String q_dLineId = FormUtil.getParameter(request, "q_dLineId");
        String q_dStationId = FormUtil.getParameter(request, "q_dStationId");
        String q_recordFlag = FormUtil.getParameter(request, "q_recordFlag");

        dp.setoLineId(q_oLineId);
        dp.setoStationId(q_oStationId);
        dp.setdLineId(q_dLineId);
        dp.setdStationId(q_dStationId);
        dp.setRecordFlag(q_recordFlag);

        return dp;

    }

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
            
//            //分账比例为小于零的小数时加上前面的0,为1时加上后面的0
//            if (".".equals(dl.getIn_percent().substring(0, 1))) {
//                dl.setIn_percent("0" + dl.getIn_percent());
//            } else if ("1".equals(dl.getIn_percent().substring(0, 1))) {
//                dl.setIn_percent(dl.getIn_percent().substring(0, 1) + ".0000");
//            }

//权重比例为小于零的小数时加上前面的0,为1时加上后面的0
            if (".".equals(dl.getInPrecent().substring(0, 1))) {
                dl.setInPrecent("0" + dl.getInPrecent());
                if (dl.getInPrecent().length() < 8) {
                    DecimalFormat df = new DecimalFormat("0.000000");
//                    System.out.println(df.format(0.34)); 
                    double num1 ;  
                    num1 = Double.valueOf(dl.getInPrecent().toString());
                    dl.setInPrecent(df.format(num1));
                }
            } else if ("1".equals(dl.getInPrecent().substring(0, 1))) {
                dl.setInPrecent(dl.getInPrecent().substring(0, 1) + ".000000");
            }else if ("0".equals(dl.getInPrecent().substring(0, 1))&&dl.getInPrecent().length()==1) {
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
    
    
    /**
    * 生成权重数据
    * @param request
    * @param bo 
    */
    private OperationResult generate(HttpServletRequest request,GenerateDataMapper mapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        String preMsg = "生成权重数据:";
        String returnUpdateNum;
        try {
            returnUpdateNum = this.generateByTrans(request,mapper);
        } catch (Exception e) {
            if(e.getMessage().contains("ORA-00001")){
                rmsg.addMessage("请勿1小时内重复生成数据！");
                return rmsg;
            }else{
                rmsg.addMessage(preMsg + "失败！" + e.getMessage());
                return rmsg;
            }
        }
        rmsg.addMessage(preMsg + "成功生成" + returnUpdateNum + "条记录！");
        return rmsg;
    }
    
    public String generateByTrans(HttpServletRequest request,GenerateDataMapper mapper) throws Exception {
        TransactionStatus status = null;
        String result = "0";
        Map parmMap = new HashMap();
        try {
            status = txMgr.getTransaction(this.def); 
            mapper.getMapOdProportion(parmMap);
            result = String.valueOf(parmMap.get("pResult"));
            String errMsg = String.valueOf(parmMap.get("pErrMsg"));
            txMgr.commit(status);
        } catch (Exception e) {
            e.printStackTrace();
            PubUtil.handExceptionForTran(e, txMgr, status);
        }
        return result;
    }
    
    /**
     * 生成OD数据
     * @param request
     * @param odbo
     * @return
     * @throws Exception 
     */
    private OperationResult generateOD(HttpServletRequest request,GenerateDataMapper mapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        String preMsg = "生成OD数据:";
        String returnUpdateNum;
        
        try {
            //初始化变量
            init();

            List<PubFlag> stations = mapper.getStations();
            //4.循环全部车站 找出全部路径
            for(int i = 0; i < stations.size(); i++){
    //            PubFlagVo pv = (PubFlagVo) RuleConstant.STATIONS.get(i);
                PubFlag pv = stations.get(i);
                changesOne = true;//每次站点循环，初始化首次换乘标志

                //4.1 当前站到当前站
                addFirstOD(pv);

                //4.2 查找 当前站下一站点，包括换乘
                findFirstNextStation();

                //4.3 清理OD变量值
                nods.addAll(nodsTmp);
                nodsTmp.clear();

                //4.4 开始循环未计算OD (主流程)
                while(nods.size() > 0){
                    DistanceOd dVo = (DistanceOd) nods.get(0);
                    String maxIdStr = String.valueOf(cods.size() + codsO.size() + maxId);//最大ID+
                    //更新临时开始站点信息
                    startStation = dVo.getO_line_id()+ dVo.getO_station_id();
                    //4.4.1 设置ID
                    dVo.setId(maxIdStr);
                    //是否有换乘
                    boolean isCopy = false;
                    // 插入换乘明细 （复制）
                    isCopy = copyChanges(dVo);

                    //4.4.2 加入已经计算OD
                    codsO.add(dVo);

                    //换乘时，路径完结，插入(换乘站-结束站)记录到换乘变量中
                    if(isCopy){
                        addChanges(dVo);
                    }

                    //4.4.3 同一线路时 ： 循环站点参数，计算下一站, //遇到开始站点时退出循环
                    addNextToNodsTmp(dVo);

                    //4.4.4 不同线路时： 检测是否为换乘站，并返回换乘站信息
                    addCodsForTransfer(dVo);

                    //4.4.5 清理OD变量值
                    nods.remove(0);
                    nods.addAll(nodsTmp);
                    nodsTmp.clear();
                }

                //将单个车站循环信息移动到总变量
                cods.addAll(codsO);
                codsO.clear();
                changes.addAll(changesO);
                changesO.clear(); 
            }
            
            returnUpdateNum = this.generateODByTrans(request,mapper);
        } catch (Exception e) {
            if(e.getMessage().contains("ORA-00001")){
                rmsg.addMessage("请勿1小时内重复生成数据！");
                return rmsg;
            }else{
                rmsg.addMessage(preMsg + "失败！" + e.getMessage());
                return rmsg;
            }
        }
        rmsg.addMessage(preMsg + "成功生成" + returnUpdateNum + "条记录！");
        return rmsg;
    }
    
   
    
    /**
     * 初始化
     *
     * @throws Exception
     */
    private void init() throws Exception {
        //查询OD表最大ID
        maxId = gdMapper.getDistanceODMaxID();

        //1.查出所有换乘站信息
        transfers = gdMapper.getTransferStation();

        //2.所有站点参数
        stationvs = gdMapper.getParamsStatioin(RuleConstant.RECORD_FLAG_USE);

    }

    /**
     * 添加第一个站为未计算OD (当前站-当前站)
     *
     * @param pv
     */
    private void addFirstOD(PubFlag pv) {
        DistanceOd firstDVo = new DistanceOd();
        startStation = pv.getCode_type() + pv.getCode();
        firstDVo.setO_line_id(pv.getCode_type());
        firstDVo.setO_station_id(pv.getCode());
        firstDVo.setE_line_id(pv.getCode_type());
        firstDVo.setE_station_id(pv.getCode());
        firstDVo.setPass_stations(startStation);
        firstDVo.setDistance(defaultDistance);//当前站到当前站 乘距设为0
        firstDVo.setChange_times(defaultChangeTimes);//当前站到当前站 换乘次数设为0
        nods.add(firstDVo);
    }
    
    protected String generateODByTrans(HttpServletRequest request,GenerateDataMapper mapper) throws Exception {
        TransactionStatus status = null;
        int n = 0;
        User user = (User)request.getSession().getAttribute("User");
        String operatorID = user.getAccount();
        try {
            status = txMgr.getTransaction(this.def); 
            mapper.updateODVersion(RuleConstant.RECORD_FLAG_USE, RuleConstant.RECORD_FLAG_HISTORY);
            
            for(int i=0; i<cods.size(); i++){
                DistanceOd vo = (DistanceOd) cods.get(i);
                vo.setRecord_flag(RuleConstant.RECORD_FLAG_USE);
                vo.setCreate_operator(operatorID);
                mapper.insertOD(vo);
                n++;
            }
            
            for(int i=0; i<changes.size(); i++){
                DistanceChange vo = (DistanceChange) changes.get(i);
                mapper.insertChange(vo);
            }
            txMgr.commit(status);
        } catch (Exception e) {
            e.printStackTrace();
            PubUtil.handExceptionForTran(e, txMgr, status);
        }
        return String.valueOf(n);
    }
    
    /**
     * 循环换乘站
     * @param dVo
     */
    private void addCodsForTransfer(DistanceOd dVo) {
        for(int k=0; k<transfers.size(); k++){
            TransferStation tsVo = (TransferStation) transfers.get(k);
            if(tsVo.getLine_id().equals(dVo.getE_line_id()) && tsVo.getStation_id().equals(dVo.getE_station_id())//换乘站等于结束站
                && !(tsVo.getTransfer_line_id().equals(dVo.getO_line_id()) && tsVo.getTransfer_station_id().equals(dVo.getO_station_id()))//换乘后车站不等于开始站
                    ){
                DistanceOd dvoTmp = new DistanceOd();
                dvoTmp.setO_line_id(dVo.getO_line_id());
                dvoTmp.setO_station_id(dVo.getO_station_id());
                dvoTmp.setE_line_id(tsVo.getTransfer_line_id());
                dvoTmp.setE_station_id(tsVo.getTransfer_station_id());
                dvoTmp.setDistance(dVo.getDistance());
                dvoTmp.setChange_times(String.valueOf(Integer.valueOf(dVo.getChange_times()) + 1));//换乘次数+1
                dvoTmp.setPass_stations(dVo.getPass_stations()+ tsVo.getTransfer_line_id()+ tsVo.getTransfer_station_id());//经过站点
                dvoTmp.setPreId(dVo.getId());
                //4.4.4.1 设置ID
                dvoTmp.setId(String.valueOf(cods.size() + codsO.size() + maxId));
                //4.4.4.2 加入已经计算OD
                codsO.add(dvoTmp);
                //复制换乘站
                copyChanges(dvoTmp);
                //4.4.4.3 插入换乘明细
                addChanges(dvoTmp);

                //4.4.4.4  循环站点参数，计算下一站 //遇到开始站点时退出循环
                addNextToNodsTmp(dvoTmp);
            }
        }
    }
    
    
    
    /**
     * 第一次查找下一车站
     */
    private void findFirstNextStation() {
        while(nods.size() > 0){
            DistanceOd dVo = (DistanceOd) nods.get(0);
            //设置ID
            dVo.setId(String.valueOf(cods.size() + codsO.size() + maxId));
            //加入已经计算OD
            codsO.add(dVo);

            //4.2.1 获得开始循环站点参数，即下一站（同一线路时）
            for(int j = 0; j < stationvs.size(); j++){
                ParamsStation psvoTmp = (ParamsStation) stationvs.get(j);
                if(psvoTmp.getLine_id().equals(dVo.getE_line_id()) && psvoTmp.getP_station_id().equals(dVo.getE_station_id())){
                    DistanceOd dvoTmp = new DistanceOd();
                    dvoTmp.setO_line_id(dVo.getO_line_id());
                    dvoTmp.setO_station_id(dVo.getO_station_id());
                    dvoTmp.setE_line_id(psvoTmp.getLine_id());
                    dvoTmp.setE_station_id(psvoTmp.getN_station_id());
                    dvoTmp.setDistance(psvoTmp.getMileage());
                    dvoTmp.setChange_times(dVo.getChange_times());
                    dvoTmp.setId(dVo.getId());
                    dvoTmp.setPreId(dVo.getId());
                    dvoTmp.setPass_stations(dVo.getPass_stations()+ psvoTmp.getLine_id()+ psvoTmp.getN_station_id());
                    nodsTmp.add(dvoTmp);
                }
            }

            //为换乘站时
            addCodsTransferFirst(dVo);

            nods.remove(0);
        }
    }
    
    /**
     * 第一次循环换乘站
     * @param dVo
     */
    private void addCodsTransferFirst(DistanceOd dVo) {
        for(int k=0; k<transfers.size(); k++){
            TransferStation tsVo = (TransferStation) transfers.get(k);
            if(tsVo.getLine_id().equals(dVo.getE_line_id()) && tsVo.getStation_id().equals(dVo.getE_station_id())){//换乘站等于结束站
                DistanceOd dvoTmp = new DistanceOd();
                dvoTmp.setO_line_id(dVo.getO_line_id());
                dvoTmp.setO_station_id(dVo.getO_station_id());
                dvoTmp.setE_line_id(tsVo.getTransfer_line_id());
                dvoTmp.setE_station_id(tsVo.getTransfer_station_id());
                dvoTmp.setDistance(dVo.getDistance());
                dvoTmp.setChange_times(String.valueOf(Integer.valueOf(dVo.getChange_times()) + 1));//换乘次数+1
                dvoTmp.setPass_stations(dVo.getPass_stations()+ tsVo.getTransfer_line_id()+ tsVo.getTransfer_station_id());//经过站点
                dvoTmp.setPreId(dVo.getId());
                //4.4.4.1 设置ID
                dvoTmp.setId(String.valueOf(cods.size() + codsO.size() + maxId));
                //4.4.4.2 加入已经计算OD
                codsO.add(dvoTmp);
                //4.4.4.3 插入换乘明细
                addChanges(dvoTmp);

                //4.4.4.4  循环站点参数，计算下一站 //遇到开始站点时退出循环
                addNextToNodsTmp(dvoTmp);
            }
        }
    }
    
     /**
     * 添加换乘站明细
     * @param dvoTmp
     */
    private void addChanges(DistanceOd dvoTmp) {
        DistanceChange dcvoTmp = new DistanceChange();
        dcvoTmp.setOd_id(dvoTmp.getId());
        dcvoTmp.setPass_distance(getChangeDistance(dvoTmp));
        dcvoTmp.setPass_line_in(dvoTmp.getE_line_id());//转入路线
        dcvoTmp.setNchange_station_id(dvoTmp.getE_station_id());//转入站点
        if(changesOne){//第一次换乘
            dcvoTmp.setPass_line_out(dvoTmp.getO_line_id());//转出线路
            dcvoTmp.setPchange_station_id(dvoTmp.getO_station_id());//转出站点
            changesOne = false;
        }else{
            dcvoTmp.setPass_line_out(startStation.substring(0, 2));
            dcvoTmp.setPchange_station_id(startStation.substring(2, 4));
        }
        //更新开始站点信息
//        startStation = dvoTmp.geteLineId() + dvoTmp.geteStationId();
        
        changesO.add(dcvoTmp);
    }
    
    /**
     * 取换乘乘距
     * @param dvoTmp
     * @return 
     */
    private String getChangeDistance(DistanceOd dvoTmp) {
        double changeDistance = Double.valueOf(dvoTmp.getDistance());
        for(int i=0; i<changesO.size(); i++){
            DistanceChange dcvoTmp = (DistanceChange) changesO.get(i);
            if(dcvoTmp.getOd_id().equals(dvoTmp.getId())){
                changeDistance -= Double.valueOf(dcvoTmp.getPass_distance());
            }
        }
        return String.valueOf(changeDistance);
    }

    /**
    * 添加下一站点到临时变量nodsTmp
    * @param dvoTmp 当前车站
    */
    private void addNextToNodsTmp(DistanceOd dvoTmp) {
        boolean isRepeat = false; //是否重复
        for(int j = 0; j < stationvs.size(); j++){
            ParamsStation psvoTmp = (ParamsStation) stationvs.get(j);
            //
            if(psvoTmp.getLine_id().equals(dvoTmp.getE_line_id()) 
                && psvoTmp.getP_station_id().equals(dvoTmp.getE_station_id())
                && !(psvoTmp.getN_station_id().equals(dvoTmp.getO_station_id()) && psvoTmp.getLine_id().equals(dvoTmp.getO_line_id()))//不等于开始站点，即不经过开始站点
                ){
                //4.4.4.4.1 判断不经过上一路径（dVo）经过的换乘站  的才插入nods(未计算OD)  --2014.01.13
                isRepeat = isRepeat(psvoTmp,dvoTmp);
                if(!isRepeat){
                    addNodsTmp(psvoTmp,dvoTmp);
                }
            }
        }
    }
    
    /**
     * 判断不经过上一路径（dVo）经过的站  的才插入nods(未计算OD)  --2014.01.13
     * @param psvoTmp 循环站点参数
     * @param dvoTmp 当前站点
     * @return 
     */
    private boolean isRepeat(ParamsStation psvoTmp, DistanceOd dvoTmp) {
        boolean isRepeat = false; 
        String passStation = dvoTmp.getPass_stations();
        int n = 4;
        
        String psvoTmpStr = psvoTmp.getLine_id()+ psvoTmp.getN_station_id();
        for(int j=0; j<passStation.length()/n; j++){//下一站为经过站点时，标志为重复路径
            if(psvoTmpStr.equals(passStation.substring(j*n, j*n+n))){
                isRepeat = true;
            }
        }
        
        if(!isRepeat){
            for(int k=0; k<transfers.size(); k++){
                TransferStation tsVo = (TransferStation) transfers.get(k);
                if(tsVo.getLine_id().equals(psvoTmp.getLine_id()) && tsVo.getStation_id().equals(psvoTmp.getN_station_id())){//循环站点参数的换乘站
                    String transferStr = tsVo.getTransfer_line_id()+ tsVo.getTransfer_station_id();
                    for(int j=0; j<passStation.length()/n; j++){//下一站为经过站点时，标志为重复路径
                        if(transferStr.equals(passStation.substring(j*n, j*n+n))){
                            isRepeat = true;
                            break;
                        }
                    }
                }
            }
        }
        
        return isRepeat;
    }
    
    /**
     * 添加OD到临时变量nodsTmp
     * @param psvoTmp 循环站点参数
     * @param dvoTmp 当前车站
     */
    private void addNodsTmp(ParamsStation psvoTmp, DistanceOd dvoTmp) {
        DistanceOd dvoTmpT = new DistanceOd();
        dvoTmpT.setO_line_id(dvoTmp.getO_line_id());
        dvoTmpT.setO_station_id(dvoTmp.getO_station_id());
        dvoTmpT.setE_line_id(psvoTmp.getLine_id());
        dvoTmpT.setE_station_id(psvoTmp.getN_station_id());
        dvoTmpT.setDistance(String.valueOf(Double.valueOf(psvoTmp.getMileage()) + Double.valueOf(dvoTmp.getDistance())));
        dvoTmpT.setPreId(dvoTmp.getId());
        dvoTmpT.setChange_times(dvoTmp.getChange_times());
        dvoTmpT.setPass_stations(dvoTmp.getPass_stations()+ psvoTmp.getLine_id()+ psvoTmp.getN_station_id());
        nodsTmp.add(dvoTmpT);
    }
    
    /**
     * 复制换乘站
     * @param dVo
     * @param maxIdStr 
     */
    private boolean copyChanges(DistanceOd dVo) {
        boolean isCopy = false;
        for(int y=0; y<changesO.size(); y++){
            DistanceChange dcvo = (DistanceChange) changesO.get(y);
            if(dVo.getPreId().equals(dcvo.getOd_id()) && !dcvo.getPass_line_in().equals(dcvo.getPass_line_out())//仅复制进出线路不相同的记录,结束站点不复制
                    ){
                DistanceChange dcvoTmp = new DistanceChange();
                dcvoTmp.setPass_distance(dcvo.getPass_distance());
                dcvoTmp.setPass_line_in(dcvo.getPass_line_in());
                dcvoTmp.setPass_line_out(dcvo.getPass_line_out());
                dcvoTmp.setNchange_station_id(dcvo.getNchange_station_id());
                dcvoTmp.setPchange_station_id(dcvo.getPchange_station_id());
                dcvoTmp.setOd_id(dVo.getId());
                changesO.add(dcvoTmp);
                isCopy = true;
                
                //更新临时开始站点信息
                startStation = dcvo.getPass_line_in()+ dcvo.getNchange_station_id();
            }
        }
        
        return isCopy;
    }
    
    private void cleanVector(){
        maxId = 1;//数据库表最大ID;
        transfers.clear();//查出所有换乘站信息
        stationvs.clear();//所有站点参数
        changesOne = true;//首次换乘标志
        startStation = null;//开始站点（线路code + 站点code）临时变量
        defaultDistance = "0";//默认乘距
        defaultChangeTimes  = "0";//默认换乘次数
        nods.clear();;//未计算OD
        cods.clear();//已经计算OD总变量
        changes.clear();//换乘记录总变量
        codsO.clear();//单个车站循环已经计算OD
        nodsTmp.clear();//未计算OD临时变量
        changesO.clear();//单个车站循环换乘记录
    }
    
      @RequestMapping("/GenerateDataActionExportAll")
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
            map.put("version", vo.getVersion());
            map.put("recordFlagText", vo.getRecordFlagText());
            list.add(map);
        }
        return list;
    }
    
}
