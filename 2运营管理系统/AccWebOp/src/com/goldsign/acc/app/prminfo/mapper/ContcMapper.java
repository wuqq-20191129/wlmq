/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.prminfo.mapper;

import com.goldsign.acc.app.prminfo.entity.Contc;
import java.util.List;
/**
 *
 * @author mh
 */
public interface ContcMapper {
    
    public List<Contc> getContc(Contc contc);

    public int addContc(Contc contc);

    public int modifyContc(Contc contc);

    public List<Contc> getContcById(Contc contc);
    
    public List<Contc> getContcByName(Contc contc);
    
    public List<Contc> getContcBySequence(Contc contc);

    public int deleteContc(Contc contc);
}
