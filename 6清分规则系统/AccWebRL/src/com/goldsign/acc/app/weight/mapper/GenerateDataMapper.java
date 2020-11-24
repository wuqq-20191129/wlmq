/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.weight.mapper;

import com.goldsign.acc.app.weight.entity.DistanceChange;
import com.goldsign.acc.app.weight.entity.DistanceOd;
import com.goldsign.acc.app.weight.entity.ParamsStation;
import com.goldsign.acc.app.weight.entity.TransferStation;
import com.goldsign.acc.app.weight.entity.DistanceProportion;
import com.goldsign.acc.frame.entity.PubFlag;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;

/**
 *生成权重数据
 * @author liudz
 * 20170908
 */
public interface GenerateDataMapper {

    public List<DistanceProportion> getGenerateDataList(DistanceProportion queryCondition);
    
    public void getMapOdProportion(Map parmMap);
    
    public int getDistanceODMaxID();
    
    public List<TransferStation> getTransferStation();
    
    public List<ParamsStation> getParamsStatioin(String record_flag);
    
    public List<PubFlag> getStations();
    
    public int updateODVersion(@Param("old_record_flag") String oldRecordFlag,@Param("new_record_flag") String newRecordFlag);
    
    public int insertOD(DistanceOd od);
    
    public int insertChange(DistanceChange dc);

//    public void queryStore(Map queryCondition); 

    public List<DistanceProportion> queryStore(DistanceProportion queryCondition);

}
