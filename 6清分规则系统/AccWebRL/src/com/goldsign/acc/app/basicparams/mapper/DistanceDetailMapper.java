/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.basicparams.mapper;

import com.goldsign.acc.app.basicparams.entity.DistanceDetail;
import java.util.List;
/**
 *
 * @author chenzx
 */
public interface DistanceDetailMapper {
    
    public List<DistanceDetail> getDistanceDetail(DistanceDetail vo);
    
}
