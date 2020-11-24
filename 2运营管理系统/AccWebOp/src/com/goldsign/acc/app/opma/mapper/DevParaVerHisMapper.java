/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.opma.mapper;

import com.goldsign.acc.app.opma.entity.DevParaVerCur;
import com.goldsign.acc.app.opma.entity.DevParaVerHis;
import java.util.List;

/**
 *
 * @author liudz
 */
public interface DevParaVerHisMapper {
    
    public List<DevParaVerHis> getDevParaVerHis(DevParaVerHis queryDevParaVerHis);


    public List<DevParaVerHis> getDevIdList(DevParaVerHis devParaVerHis);
}
