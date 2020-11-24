/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.prminfo.mapper;

import com.goldsign.acc.app.prminfo.entity.StatusMapping;
import java.util.List;
/**
 *
 * @author mh
 */
public interface StatusMappingMapper {
    
    public List<StatusMapping> getStatusMapping(StatusMapping sm);

    public int addStatusMapping(StatusMapping sm);

    public int modifyStatusMapping(StatusMapping sm);

    public List<StatusMapping> getStatusMappingById(StatusMapping sm);

    public int deleteStatusMapping(StatusMapping sm);
}
