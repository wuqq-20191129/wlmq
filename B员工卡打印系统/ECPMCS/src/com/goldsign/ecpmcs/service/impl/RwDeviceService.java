/*
 * 文件名：RwDeviceService
 * 版权：Copyright: goldsign (c) 2013
 * 描述：RW读写器RwDeviceService服务实例实现类
 */

package com.goldsign.ecpmcs.service.impl;

import com.goldsign.csfrm.service.impl.BaseService;
import com.goldsign.csfrm.util.CharUtil;
import com.goldsign.csfrm.util.StringUtil;
import com.goldsign.csfrm.vo.CallResult;
import com.goldsign.esmcs.serial.RwDeviceCommu;
import com.goldsign.ecpmcs.env.AppConstant;
import com.goldsign.ecpmcs.service.IRwDeviceService;
import com.goldsign.ecpmcs.vo.AnalyzeVo;
import com.goldsign.ecpmcs.vo.CommonCfgParam;
import java.nio.ByteBuffer;
import org.apache.log4j.Logger;


public class RwDeviceService extends BaseService implements IRwDeviceService{
    
    private static final Logger logger = Logger.getLogger(RwDeviceService.class.getName());
              
    private RwDeviceCommu rwDeviceCommu;
    
    public RwDeviceService(){
       
        rwDeviceCommu = new RwDeviceCommu();
    }

    @Override
    public CallResult openConnection(String portNo) {
        
        CallResult callResult = new CallResult();
        
        try{
            rwDeviceCommu.open(portNo);
        } catch (Exception ex) {
            callResult.resetMsg(ex.getMessage());
            ex.printStackTrace();
            System.out.println("ex.getMessage() : " + ex.getMessage());
            return callResult;
        }
        callResult.setObj(portNo);
        callResult.setResult(true);
        callResult.resetMsg("打开读写器串口成功！");
        
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
}
