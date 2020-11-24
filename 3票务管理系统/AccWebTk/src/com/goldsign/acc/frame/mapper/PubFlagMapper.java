/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.frame.mapper;

import com.goldsign.acc.frame.entity.PubFlag;
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.Type;
import java.util.List;
import java.util.Vector;

/**
 *
 * @author hejj
 */
public interface PubFlagMapper {

    public List<PubFlag> getLinesForIc();

    public List<PubFlag> getStationsForIc();

    public List<PubFlag> getCardMainTypesForIc();//票库票卡主类型

    public List<PubFlag> getButtons();

    public List<PubFlag> getCardSubTypesForIc();//票库票卡子类型

    public List<PubFlag> getCodesByType(int type);

    public List<PubFlag> getInOutReasonByFlag(String flag);

    public List<PubFlag> getInOutReasonForDistribute();

    public List<PubFlag> getEsOperators();

    public List<PubFlag> getEsWorkTypes();

    public List<PubFlag> getStoragesAll();

    public List<PubFlag> getStoragesForOperator(String operatorId);

    public List<PubFlag> getStoragesAreas();

    public List<PubFlag> getStoragesAreasRecover();

    public List<PubFlag> getStoragesAreasCancel();

    public List<PubFlag> getLinesForAfc();

    public List<PubFlag> getStationsForAfc();

    public List<PubFlag> getCardMainTypesForAfc();//（运营）票卡主类型

    public List<PubFlag> getCardSubTypesForAfc();//（运营）票卡子类型
    
    public List<PubFlag> getCleanOutBills(List<String> storageIds);

    public List<PubFlag> getCancelOutBills(List<String> storageIds);

    public List<PubFlag> getPduProduceBills(String operatorId);

    public List<PubFlag> getInOutReasonForInProduces();

    public List<PubFlag> getBorrowUnits();// 借票单位，用于查询下拉框

    public List<PubFlag> getLines(String paramString);   //线路

    public List<PubFlag> getStations(String paramString);   //线路车站

    public List<PubFlag> getStorageLines();//仓库线路

    public List<PubFlag> getProduceFactory();   //生产厂商

    public List<PubFlag> getLinesByStorageId(String storageId);

    public List<PubFlag> getInOutTypes();//出入库类型

    public List<PubFlag> getStoragesAreasDestroy();//c

    public List<PubFlag> getCardMainTypesForIcLimit();

    public List<PubFlag> getDiffReasons(); //出入差额原因

    public List<PubFlag> getInOutReasonForBorrow();
    
    public List<PubFlag> getCardMainTypesForReturn();

    public List<PubFlag> getLendBillNos();

    public List<PubFlag> getStoragesAreasBorrowIn();

    public List<PubFlag> getStoragesAreasEncodeAndValue();
    
    public List<PubFlag> getDevCodeES();//ES机器号

    public List<PubFlag> getBillName();//单据名称

    public List<PubFlag> getInOutSubTypes();

    
}
