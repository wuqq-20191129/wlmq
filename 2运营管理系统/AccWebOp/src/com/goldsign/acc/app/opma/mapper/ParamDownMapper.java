/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.opma.mapper;

import com.goldsign.acc.app.opma.entity.ParamDown;
import java.util.List;
import java.util.Map;
import java.util.Vector;

/**
 *
 * @author liudz
 */
public interface ParamDownMapper {
    public Vector getAllPriviledges(ParamDown queryCondition) ;

    public int down(String strParmNum, String strLccLines, String operatorId);

    public int down(List<String> paraNum, List<String> lccLines, String operatorId);

    public String getDegradeVerNum(String operatorId);

    public String getLogicIccVerNum(String operatorId);


    public List<ParamDown> getParmDownTypes();

    public List<ParamDown> findLineTypesDescription();

    public Vector findLineNames();

    public Vector<String> getLccLineID();

    public void updateWaterNo();

    public String getWaterNo();

    public int insterDistribute(ParamDown pd);

    public int insterGenDtl(ParamDown pd1);

    public int insterInformDtl(ParamDown pd2);


    public int updateDevProgram(ParamDown paramDown);
    
     public int updateDevProgram1(Object[] obj);

    public String getWaterNoForDegrade();


    public List findLogicIccList();

    public String getVersionNo();

    public List findDegradeModeRecd();

    public void updateDrgardeModRecd(String verNum);

    public void insertWaterNo(String verNum);

    public void updateWaterNoForDegrade(String verNum);

    public String findVersionNo();

    public void updateLogicIccList();

    public String getlccLineId(String lccLineName);

    public List<ParamDown> getparamDown(ParamDown queryCondition);

    public String getDegradeModeRecd();

    public void updateDevProgram1(ParamDown p6);

}
