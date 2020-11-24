/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.system.mapper;

import com.goldsign.acc.app.system.entity.Lcc;
import java.util.List;
/**
 *
 * @author mh
 */
public interface LccMapper {
    
    public List<Lcc> getLcc();
    
    public int updateLcc(Lcc vo);

    public int insertIntoCur(Lcc vo);

    public int insertIntoHis(Lcc vo);
    
}
