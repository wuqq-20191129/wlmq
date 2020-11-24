/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.prminfo.mapper;

import com.goldsign.acc.app.prminfo.entity.DegradeType;
import java.util.List;
/**
 *
 * @author mh
 */
public interface DegradeTypeMapper {
    
    public List<DegradeType> getDegradeType(DegradeType dt);

    public int addDegradeType(DegradeType dt);

    public int modifyDegradeType(DegradeType dt);

    public List<DegradeType> getDegradeTypeById(DegradeType dt);
    
    public List<DegradeType> getDegradeTypeByName(DegradeType dt);

    public int deleteDegradeType(DegradeType dt);
}
