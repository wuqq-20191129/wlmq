/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.opma.mapper;
import com.goldsign.acc.app.opma.entity.ParamDownStatus;
import com.goldsign.acc.frame.entity.PubFlag;
import java.util.List;
import java.util.Map;

public interface ParamDownStatusMapper {
    
    public List<ParamDownStatus> getParamDownStatus(ParamDownStatus pds);
    
    //获取应用情况
    public List<PubFlag> getDownloadStatus();
    
}
