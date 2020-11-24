/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.basicparams.mapper;
import com.goldsign.acc.app.basicparams.entity.LineStation;
import java.util.List;
/**
 *
 * @author chenzx
 */
public interface LineStationMapper {
    
    public List<LineStation> getLineStation(LineStation vo);
    
    public List<LineStation> getTransferStation(LineStation vo);
    
    public List<LineStation> getTransferLineStation(LineStation vo);
    
}
