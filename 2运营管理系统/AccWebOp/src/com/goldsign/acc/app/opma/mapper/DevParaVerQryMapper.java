/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.opma.mapper;
import com.goldsign.acc.app.opma.entity.DevParaVerQry;
import java.util.List;
import java.util.Map;

public interface DevParaVerQryMapper {
    
    public List<DevParaVerQry> getDevParaVerQry(DevParaVerQry dpvq);
    
    public int addDevParaVerQry(DevParaVerQry dpvq);
    
}
