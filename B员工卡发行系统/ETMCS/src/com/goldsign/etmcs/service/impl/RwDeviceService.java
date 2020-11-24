/*
 * 文件名：RwDeviceService
 * 版权：Copyright: goldsign (c) 2013
 * 描述：RW读写器RwDeviceService服务实例实现类
 */

package com.goldsign.etmcs.service.impl;

import com.goldsign.csfrm.service.impl.BaseService;
import com.goldsign.csfrm.util.CharUtil;
import com.goldsign.csfrm.util.StringUtil;
import com.goldsign.csfrm.vo.CallResult;
import com.goldsign.esmcs.serial.RwDeviceCommu;
import com.goldsign.etmcs.dao.IMakeCardDao;
import com.goldsign.etmcs.dao.impl.MakeCardDao;
import com.goldsign.etmcs.env.AppConstant;
import com.goldsign.etmcs.service.IKmsService;
import com.goldsign.etmcs.service.IRwDeviceService;
import com.goldsign.etmcs.vo.AnalyzeVo;
import com.goldsign.etmcs.vo.CommonCfgParam;
import com.goldsign.etmcs.vo.KmsCardVo;
import com.goldsign.etmcs.vo.MakeCardVo;
import com.goldsign.etmcs.vo.SignCardParam;
import java.nio.ByteBuffer;
import org.apache.log4j.Logger;


public class RwDeviceService extends BaseService implements IRwDeviceService{
    
    private static final Logger logger = Logger.getLogger(RwDeviceService.class.getName());
              
    private RwDeviceCommu rwDeviceCommu;
    private IKmsService kmsService;
    private IMakeCardDao makeCardDao;
    
    public RwDeviceService(){
       
        rwDeviceCommu = new RwDeviceCommu();
        kmsService = KmsService.getInstance();
        makeCardDao = new MakeCardDao();
    }

    @Override
    public CallResult openConnection(String portNo) {
        
        CallResult callResult = new CallResult();
        
        try{
            rwDeviceCommu.open(portNo);
        } catch (Exception ex) {
            callResult.resetMsg(ex.getMessage());
            ex.printStackTrace();
            return callResult;
        }
        callResult.setObj(portNo);
        callResult.setResult(true);
        callResult.resetMsg("打开读写器串口成功！");
        
        return callResult;
    }

    @Override
    public CallResult esVersions() {
        
        CallResult callResult = new CallResult();
        byte[] byteRets = null;
        
        try {
            ByteBuffer bytes = ByteBuffer.allocate(5);
            
            byteRets = rwDeviceCommu.esVersions(bytes.array());
            
            String retCode = CharUtil.getByteString(byteRets, 0, 2);
            String relCode = CharUtil.getByteString(byteRets, 2, 1);
            
            if(AppConstant.RW_SUCCESS_CODE.equals(retCode)){
                callResult.setResult(true);
                callResult.resetMsg("获取设备版本号成功！");
            }else{
                callResult.resetMsg(relCode);
            }
            callResult.setCode(retCode);
        } catch (Exception ex) {
            callResult.resetMsg(ex.getMessage());
            return callResult;
        }

        return callResult;
    }
    
    @Override
    public CallResult initDevice(CommonCfgParam param) {
        
        CallResult callResult = new CallResult();
        byte[] byteRets = null;
        
        try {
            ByteBuffer bytes = ByteBuffer.allocate(5);
            
            String stationId = param.getStationId();
            bytes.put(CharUtil.bcdStringToByteArray(stationId));
            String deviceType = param.getDeviceType();
            bytes.put(CharUtil.bcdStringToByteArray(deviceType));
            String deviceId = param.getDeviceNo();
            bytes.put(CharUtil.bcdStringToByteArray(deviceId));
            
            byteRets = rwDeviceCommu.esInitDevice(bytes.array());
            
            String retCode = CharUtil.getByteString(byteRets, 0, 2);
            String relCode = CharUtil.getByteString(byteRets, 2, 1);
            
            if(AppConstant.RW_SUCCESS_CODE.equals(retCode)){
                callResult.setResult(true);
                callResult.resetMsg("初始化设备成功！");
            }else{
                callResult.resetMsg(relCode);
            }
            callResult.setCode(retCode);
        } catch (Exception ex) {
            callResult.resetMsg(ex.getMessage());
            return callResult;
        }

        return callResult;
    }

    //发卡
    @Override
    public CallResult signCard(SignCardParam param) {
        
        CallResult callResult = new CallResult();
        byte[] byteRets = null;
        
        try {
            callResult = analyze();//分析卡信息  ES(Encoder/Sorter:车票编码分拣机) 请求协议格式
            if (!callResult.isSuccess()) {
                return callResult;
            }
            AnalyzeVo analyzeVo = (AnalyzeVo) callResult.getObj();
            //判断是否满足写卡条件
            //未放卡
            if(StringUtil.isEmpty(analyzeVo.getcTicketType())){
                callResult.resetMsg("没有检测到任何票卡，请正确放置卡片！");
                callResult.setResult(false);
                return callResult;
            }
            //检测逻辑卡号是否已经写入发行员工卡
            MakeCardVo mvo = new MakeCardVo();
            mvo.setLogicId(analyzeVo.getcLogicalID());
            if(makeCardDao.isExistsMakeCard(mvo) > 0){
                callResult.resetMsg("该卡已经写入员工信息，不能重复发卡！");
                callResult.setResult(false);
                return callResult;
            }
            //是已发行状态
            if(!StringUtil.isEmpty(analyzeVo.getbIssueStatus())&&
                !(analyzeVo.getbIssueStatus().equals(AppConstant.ISSUE_STATUS_ISSUED))){
                callResult.resetMsg("请确认该卡是否已经发行状态！");
                callResult.setResult(false);
                return callResult;
            }
            //不是员工卡
            if(!(!StringUtil.isEmpty(analyzeVo.getcTicketType())&&
                    (analyzeVo.getcTicketType().substring(0,2)).equals(AppConstant.TICKET_TYPE_EMPLOYEE))){
                callResult.resetMsg("请确认，该卡不是员工卡！");
                callResult.setResult(false);
                return callResult;
            }
            
            ByteBuffer bytes = ByteBuffer.allocate(16+164);
            
            //加入密钥
            try {
                CallResult callResultKey = kmsService.getCardKey(analyzeVo.getcLogicalID());
                if(!callResultKey.isSuccess()){
                    callResultKey.setMsg("密钥错误,代码："+callResultKey.getCode());
                    return callResultKey;
                }
                KmsCardVo kmsCardVo = (KmsCardVo) callResultKey.getObj();
                bytes.put(CharUtil.hexStrToLenByteArr(kmsCardVo.getAppMendKey02(),16));
            } catch (Exception e) {
                callResult.resetMsg(e.getMessage());
                callResult.setResult(false);
                return callResult;
            }
            
            //个性化
            bytes.put(AppConstant.WRITE_CARD_HOLDER_TYPE_PERSON);//持卡类型
            bytes.put(AppConstant.WRITE_CARD_CARD_TYPE_COM);//本单位职工
            String employeeName = StringUtil.addEmptyAfterUTF(param.getEmployeeName(), 128);
            bytes.put(CharUtil.utfStrToBytes(employeeName));//姓名
            String employeeNo = StringUtil.addEmptyAfter(param.getEmployeeNo(), 32);
            bytes.put(CharUtil.utfStrToBytes(employeeNo));//工号
            bytes.put(AppConstant.WRITE_CARD_ID_TYPE_EMPLOYEE);//证件类型
            String sex = param.getSex();
            bytes.put(CharUtil.bcdStringToByteArray(sex));//性别
            
            byteRets = rwDeviceCommu.esSignCard(bytes.array());
            
            String retCode = CharUtil.byteToIntStr(byteRets, 0, 2);
            String relCode = CharUtil.getByteString(byteRets, 2, 1);
            if (AppConstant.RW_SUCCESS_CODE.equals(retCode)) {
                callResult.setResult(true);
                callResult.resetMsg("写员工卡信息成功！");
            } else {
                callResult.resetMsg(relCode);
                callResult.setResult(false);
            }
            callResult.setCode(retCode);
        } catch (Exception ex) {
            callResult.resetMsg(ex.getMessage());
            callResult.setResult(false);
            return callResult;
        }
        
        return callResult;
    }

    @Override
    public CallResult closeConnection() {
        
        CallResult callResult = new CallResult();
        
        rwDeviceCommu.close();
        
        callResult.setResult(true);
        callResult.resetMsg("关闭读写器串口成功！");
        
        return callResult;
    }

    @Override
    public CallResult destroy(MakeCardVo vo) {
        logger.info("进入退卡串口操作......");
        CallResult callResult = null;
        byte[] byteRets = null;

        try {
            callResult = analyze();
            if (!callResult.isSuccess()) {
                return callResult;
            }
            AnalyzeVo analyzeVo = (AnalyzeVo) callResult.getObj();
            analyzeVo.setcLogicalID(analyzeVo.getcLogicalID());
            //判断是否满足写卡条件
            //已经注销状态
            if(!StringUtil.isEmpty(analyzeVo.getbIssueStatus())&&
                    analyzeVo.getbIssueStatus().equals(AppConstant.ISSUE_STATUS_CANCEL)){
                callResult.resetMsg("请确认该卡是否已经注销！");
                logger.info("该卡已经注销！analyzeVo.getbIssueStatus() : "+analyzeVo.getbIssueStatus());
                callResult.setResult(false);
                return callResult;
            }
            //不是员工卡
            if(!(!StringUtil.isEmpty(analyzeVo.getcTicketType())&&
                    (analyzeVo.getcTicketType().substring(0,2)).equals(AppConstant.TICKET_TYPE_EMPLOYEE))){
                callResult.resetMsg("请确认，该卡不是员工卡！"); 
                logger.info("该卡不是员工卡！analyzeVo.getcTicketType() : " + analyzeVo.getcTicketType());
                callResult.setResult(false);
                return callResult;
            }
            //不是所属员工（注销逻辑卡号与卡逻辑卡号不一致）
            if(!(!StringUtil.isEmpty(analyzeVo.getcLogicalID())&&
                    analyzeVo.getcLogicalID().equals(vo.getLogicId()))){
                callResult.resetMsg("请确认，该卡用户信息与要注销用户信息不匹配！"); 
                logger.info("该卡用户信息与要注销用户信息不匹配！analyzeVo.getcLogicalID() : " + analyzeVo.getcLogicalID());
                callResult.setResult(false);
                return callResult;
            }
            logger.info("退卡串口检查完毕......");
            ByteBuffer bytes = ByteBuffer.allocate(39+16*5);
            
            String orderNo = "00000000000000";
            bytes.put(CharUtil.strToLenByteArr(orderNo,14));
            String reqNo = "0000000000";
            bytes.put(CharUtil.strToLenByteArr(reqNo,10));
            byte[] ticketType = CharUtil.hexStrToLenByteArr(analyzeVo.getcTicketType(), 2);
            bytes.put(ticketType);
            byte[] logicNo = CharUtil.hexStrToLenByteArr(analyzeVo.getcLogicalID(), 8);
            bytes.put(logicNo);
            byte deposite = CharUtil.strToByte(String.valueOf(analyzeVo.getlDeposite()));
            bytes.put(deposite);
            int balance = CharUtil.strToInt(String.valueOf(analyzeVo.getlBalance()));
            bytes.putInt(balance);
            
            //加入密钥
            CallResult callResultKey = kmsService.getCardKey(analyzeVo.getcLogicalID());
            if (!callResultKey.isSuccess()) {
                callResultKey.setMsg("密钥错误,代码：" + callResultKey.getCode());
                logger.info("密钥错误,代码：" + callResultKey.getCode());
                return callResultKey;
            }
            KmsCardVo kmsCardVo = (KmsCardVo) callResultKey.getObj();
            bytes.put(CharUtil.hexStrToLenByteArr(kmsCardVo.getConsumeKey(),16));
            bytes.put(CharUtil.hexStrToLenByteArr(kmsCardVo.getTranAuthenTacKey(),16));
            bytes.put(CharUtil.hexStrToLenByteArr(kmsCardVo.getAppMendKey(),16));
            bytes.put(CharUtil.hexStrToLenByteArr(kmsCardVo.getAppMendKey01(),16));
            bytes.put(CharUtil.hexStrToLenByteArr(kmsCardVo.getAppMendKey02(),16));
            
            //退卡
            logger.info("串口进行退卡..." );
            byteRets = rwDeviceCommu.esDestroy(bytes.array());

            String retCode = CharUtil.getByteString(byteRets, 0, 2);
            String relCode = CharUtil.getByteString(byteRets, 2, 1);
            
            if(AppConstant.RW_SUCCESS_CODE.equals(retCode)){
                callResult.setResult(true);
                callResult.resetMsg("卡注销成功！");
                logger.info("卡注销成功..." );
            }else{
                callResult.resetMsg(relCode);
                logger.info("卡注销失败：返回码 " + relCode);
            }
            
            callResult.setCode(retCode);
        } catch (Exception ex) {
            callResult.setResult(false);
            callResult.resetMsg(ex.getMessage());
            return callResult;
        }

        return callResult;
    }

    @Override
    public CallResult analyze() {
        
        CallResult callResult = new CallResult();
        byte[] byteRets = null;

        try {
            byteRets = rwDeviceCommu.esAnalyze();
            logger.debug("分析返回数据："+byteRets);
            String retCode = CharUtil.getByteString(byteRets, 0, 2);
            String relCode = CharUtil.getByteString(byteRets, 2, 1);

            if (AppConstant.RW_SUCCESS_CODE.equals(retCode)) {

                AnalyzeVo analyzeVo = getAnalyzeVo(byteRets);
                logger.info("票卡分析:"+analyzeVo);
                callResult.setObj(analyzeVo);
                callResult.setResult(true);
                callResult.resetMsg("票卡分析成功！");
                logger.info("票卡分析成功！");
            } else {
                callResult.resetMsg("票卡分析失败，错误码："+retCode+relCode+".");
                logger.warn("票卡分析失败，错误码："+retCode+relCode+".");
            }
            callResult.setCode(retCode+relCode);
        } catch (Exception ex) {
            callResult.resetMsg("票卡分析异常："+ex.getMessage());
            logger.error("票卡分析异常："+ex);
        }

        return callResult;
    }
    
    private AnalyzeVo getAnalyzeVo(byte[] bytesRet){
    
        AnalyzeVo analyzeVo = new AnalyzeVo();
        
        String bIssueStatus = CharUtil.getByteString(bytesRet, 7, 1);
        analyzeVo.setbIssueStatus(bIssueStatus);
        String bStatus = CharUtil.getByteString(bytesRet, 8, 1);
        analyzeVo.setbStatus(bStatus);
        String cTicketType = CharUtil.getByteCharString(bytesRet, 9, 4);
        analyzeVo.setcTicketType(cTicketType);
        String cLogicalID = CharUtil.getByteCharString(bytesRet, 13, 20);
        //analyzeVo.setcLogicalID(StringUtil.fmtStrLeftEmptyLen(cLogicalID,16));
        analyzeVo.setcLogicalID(cLogicalID.substring(0,16));
        String cPhysicalID = CharUtil.getByteCharString(bytesRet, 33, 20);
        analyzeVo.setcPhysicalID(cPhysicalID);
        String bCharacter = CharUtil.getByteString(bytesRet, 53, 1);
        analyzeVo.setbCharacter(bCharacter);
        String cIssueDate = CharUtil.bytesToAsciiStringNoSpace(CharUtil.getByteArr(bytesRet, 54, 14));
        analyzeVo.setcIssueDate(cIssueDate);
        String cExpire = CharUtil.bytesToAsciiStringNoSpace(CharUtil.getByteArr(bytesRet, 68, 8));
        analyzeVo.setcExpire(cExpire);
        
        String cEndExpire = CharUtil.bytesToAsciiStringNoSpace(CharUtil.getByteArr(bytesRet, 76, 8));
        analyzeVo.setcEndExpire(cEndExpire);
        String rfu = CharUtil.getByteCharString(bytesRet, 84, 16);
        analyzeVo.setRfu(rfu);
        long lBalance = CharUtil.byteToLong(CharUtil.getByteArr(bytesRet, 100, 4));
        analyzeVo.setlBalance(lBalance);
        long lDeposite = CharUtil.byteToLong(CharUtil.getByteArr(bytesRet, 104, 4));
        analyzeVo.setlDeposite(lDeposite);
        String cLine = CharUtil.getByteCharString(bytesRet, 108, 2);
        analyzeVo.setcLine(cLine);
        String cStationNo = CharUtil.getByteCharString(bytesRet, 110, 2);
        analyzeVo.setcStationNo(cStationNo);
        String cDateStart = CharUtil.bytesToAsciiStringNoSpace(CharUtil.getByteArr(bytesRet, 112, 8));
        analyzeVo.setcDateStart(cDateStart);
        String cDateEnd = CharUtil.bytesToAsciiStringNoSpace(CharUtil.getByteArr(bytesRet, 120, 8));
        analyzeVo.setcDateEnd(cDateEnd);
        //String dtDaliyActive = CharUtil.getByteString(bytesRet, 120, 7);
        String dtDaliyActive = CharUtil.bytesToHexStringNoSpace(CharUtil.getByteArr(bytesRet, 128, 7));
        analyzeVo.setDtDaliyActive(dtDaliyActive);
        //String bEffectDay = CharUtil.getByteString(bytesRet, 127, 1);
        String bEffectDay = CharUtil.byteToIntStr(CharUtil.getByteArr(bytesRet, 135, 2));
        analyzeVo.setbEffectDay(bEffectDay);
        String cLimitEntryLine = CharUtil.getByteCharString(bytesRet, 137, 2);
        analyzeVo.setcLimitEntryLine(cLimitEntryLine);
        String cLimitEntryStation = CharUtil.getByteCharString(bytesRet, 139, 2);
        analyzeVo.setcLimitEntryStation(cLimitEntryStation);
        String cLimitExitLine = CharUtil.getByteCharString(bytesRet, 141, 2);
        analyzeVo.setcLimitExitLine(cLimitExitLine);
        String cLimitExitStation = CharUtil.getByteCharString(bytesRet, 143, 2);
        analyzeVo.setcLimitExitStation(cLimitExitStation);
        String cLimitMode = CharUtil.getByteCharString(bytesRet, 145, 3);
        analyzeVo.setcLimitMode(cLimitMode);
        
        String certificateIscompany = CharUtil.getByteString(bytesRet, 148, 1);
        analyzeVo.setCertificateIscompany(certificateIscompany);
        String certificateIsmetro = CharUtil.getByteString(bytesRet, 149, 1);
        analyzeVo.setCertificateIsmetro(certificateIsmetro);
        String certificateName = CharUtil.bytesToUtfStr(CharUtil.getByteArr(bytesRet, 150, 128));
        analyzeVo.setCertificateName(certificateName);
        String certificateCode =  CharUtil.bytesToGbkStr(CharUtil.getByteArr(bytesRet, 278, 32));
        analyzeVo.setCertificateCode(certificateCode);
        String certificateType = CharUtil.getByteString(bytesRet, 310, 1);
        analyzeVo.setCertificateType(certificateType);
        String certificateSex = CharUtil.getByteString(bytesRet, 311, 1);
        analyzeVo.setCertificateSex(certificateSex);
        
        int tradeCount = CharUtil.byteToInt(CharUtil.getByteArr(bytesRet, 312, 2));
        analyzeVo.setTradeCount(tradeCount);
        
        //hwj add 20160107 增加卡商代码
        String cardProducerCode = CharUtil.getByteCharString(bytesRet, 314, 4);
        //String cardProducerCode = "0000";//暂屏蔽
         if(cardProducerCode.trim().equals("")){
            cardProducerCode = "0000";
         }
        analyzeVo.setCardProducerCode(cardProducerCode);
        
        return analyzeVo;
    }

}
