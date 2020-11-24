/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.commu.mapper;

import com.goldsign.acc.app.commu.entity.CmClearLog;
import java.util.List;
/**
 *
 * @author mh
 */
public interface CmClearLogMapper {
    
    public List<CmClearLog> getCmClearLog(CmClearLog vo);
    
}
