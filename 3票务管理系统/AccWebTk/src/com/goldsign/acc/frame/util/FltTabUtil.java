/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.goldsign.acc.frame.util;

import com.goldsign.acc.frame.constant.PubFlagConstant;
import com.goldsign.acc.frame.entity.PubFlag;
import com.goldsign.acc.frame.mapper.PubFlagMapper;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

/**
 * @author  刘粤湘
 * @date    2017-9-4 9:51:58
 * @version V1.0
 * @desc  
 */
public class FltTabUtil {
    
       
    public Map<String, String> mapMainTypes = null;
    public Map<String, String> mapMainSubs = null;
    public Map<String, String> mapStorages = null;
    public Map<String, String> mapStoragesByOpr = null;
    public Map<String, String> mapAreas = null;
    public Map<String, String> mapLines = null;
    public Map<String, String> mapLinesIc = null;
    public Map<String, String> mapStations = null;
    public Map<String, String> mapStationsIc = null;
    public Map<String, String> mapLimitModels = null;
    public Map<String, String> mapBillStatus = null;
    public Map<String, String> mapAfcMainTypes = null;
    public Map<String, String> mapAfcMainSubs = null;

    public Map<String, String> mapSexs = null;
    public Map<String, String> mapCrdTypes = null;
    public Map<String, String> mapTestFlags = null;
    public Map<String, String> mapDealResults = null;   

    public List<PubFlag> sexsTmp = null;
    public List<PubFlag> crdTypesTmp = null;
    public List<PubFlag> testFlagsTmp = null;
    
    private int SEX_TYPE = 40;
    private int CRD_TYPE = 41;
    private int FLAG_TYPE = 4;//modify by zhouy 20180118 原先42
    private int RES_TYPE = 43;
    
    public String TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public String DATE_FORMAT = "yyyy-MM-dd ";
    
     /**
     * 初始化表格过滤时需要用到的数据
     */
    public void initFilterNeed(PubFlagMapper pubFlagMapper){
         //票卡主类型
        List<PubFlag> mainTypes = pubFlagMapper.getCardMainTypesForIc();
        mapMainTypes = new HashMap();
        for (PubFlag mainType : mainTypes) {
            mapMainTypes.put(mainType.getCode(), mainType.getCode_text());
        }
        //票卡子类型
        List<PubFlag> mainSubs = pubFlagMapper.getCardSubTypesForIc();
        mapMainSubs = new HashMap();
        for (PubFlag mainSub : mainSubs) {
//            System.out.println(mainSub.getCode_type() + mainSub.getCode());
            mapMainSubs.put(mainSub.getCode_type() + mainSub.getCode(), mainSub.getCode_text());
        }
        
         List<PubFlag> afcMainTypes = pubFlagMapper.getCardMainTypesForAfc();
        mapAfcMainTypes = new HashMap();
        for (PubFlag afcMainType : afcMainTypes) {
            mapAfcMainTypes.put(afcMainType.getCode(), afcMainType.getCode_text());
        }
        
         List<PubFlag> afcMainSubs = pubFlagMapper.getCardSubTypesForAfc();
        mapAfcMainSubs = new HashMap();
        for (PubFlag afcMainSub : afcMainSubs) {
            mapAfcMainSubs.put(afcMainSub.getCode_type()+ afcMainSub.getCode(), afcMainSub.getCode_text());
        }
        
        
        //仓库
        List<PubFlag> storages = pubFlagMapper.getStoragesAll();
        mapStorages = new HashMap();
        for (PubFlag storage : storages) {
            mapStorages.put(storage.getCode(), storage.getCode_text());
        }
        //票区
        List<PubFlag> areas = pubFlagMapper.getStoragesAreas();
        mapAreas = new HashMap();
        for (PubFlag area : areas) {
            mapAreas.put(area.getCode(), area.getCode_text());
        }
        //线路 车站        
        List<PubFlag> lines = pubFlagMapper.getLinesForAfc();
        mapLines = new HashMap();
        for (PubFlag line : lines) {
            mapLines.put(line.getCode(), line.getCode_text());
        }
                
        List<PubFlag> stations = pubFlagMapper.getStationsForAfc();
        mapStations = new HashMap();
        for (PubFlag station : stations) {
            mapStations.put(station.getCode_type() + station.getCode(), station.getCode_text());
        }
        
        //线路 车站        
        List<PubFlag> linesIc = pubFlagMapper.getLinesForIc();
        mapLinesIc = new HashMap();
        for (PubFlag line : linesIc) {
            mapLinesIc.put(line.getCode(), line.getCode_text());
        }
                
        List<PubFlag> stationsIc = pubFlagMapper.getStationsForIc();
        mapStationsIc = new HashMap();
        for (PubFlag station : stationsIc) {
            mapStationsIc.put(station.getCode_type() + station.getCode(), station.getCode_text());
        }
        
        //乘次票限制模式
        List<PubFlag> limitModels = pubFlagMapper.getCodesByType(PubFlagConstant.PUB_FLAG_TYPE_MODE);
        mapLimitModels = new HashMap();
         for (PubFlag limitModel : limitModels) {
            mapLimitModels.put(limitModel.getCode(), limitModel.getCode_text());
        }
         
         //单据状态
        List<PubFlag> billStatus = pubFlagMapper.getCodesByType(PubFlagConstant.PUB_FLAG_TYPE_BILL_STATE);
        mapBillStatus = new HashMap();
        for (PubFlag billStatu : billStatus) {
            mapBillStatus.put(billStatu.getCode(), billStatu.getCode_text());
        }
        
        List<PubFlag> sexs = pubFlagMapper.getCodesByType(SEX_TYPE);
        mapSexs = new HashMap();
        for (PubFlag sex : sexs) {
            mapSexs.put(sex.getCode(), sex.getCode_text());
        }
        
         List<PubFlag> crdTypes = pubFlagMapper.getCodesByType(CRD_TYPE);
        mapCrdTypes = new HashMap();
        for (PubFlag crdType : crdTypes) {
            mapCrdTypes.put(crdType.getCode(), crdType.getCode_text());
        }
        
         List<PubFlag> testFlags = pubFlagMapper.getCodesByType(FLAG_TYPE);
        mapTestFlags = new HashMap();
        for (PubFlag testFlag : testFlags) {
            mapTestFlags.put(testFlag.getCode(), testFlag.getCode_text());
        }
        
         List<PubFlag> resTypes = pubFlagMapper.getCodesByType(RES_TYPE);
        mapDealResults = new HashMap();
        for (PubFlag resType : resTypes) {
            mapDealResults.put(resType.getCode(), resType.getCode_text());
        }
        
        sexsTmp = sexs;
        crdTypesTmp = crdTypes;
        testFlagsTmp = testFlags;
    }
    
    public void initFilterNeed(PubFlagMapper pubFlagMapper,HttpServletRequest request){
         //根据操作员ID取得对应仓库权限
        String operatorId = PageControlUtil.getOperatorFromSession(request);
        List<PubFlag> pubFlags = pubFlagMapper.getStoragesForOperator(operatorId);
//        System.out.println(operatorId);
        mapStoragesByOpr = new HashMap();
        for (PubFlag pubFlag : pubFlags) {
            mapStoragesByOpr.put(pubFlag.getCode(), pubFlag.getCode_text());
        }
    }
}
