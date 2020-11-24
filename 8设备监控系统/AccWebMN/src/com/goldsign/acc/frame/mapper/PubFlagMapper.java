/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.frame.mapper;

import com.goldsign.acc.frame.entity.PubFlag;
import java.util.List;

/**
 *
 * @author hejj
 */
public interface PubFlagMapper {

    public List<PubFlag> getLines();

    public List<PubFlag> getStations();

    public List<PubFlag> getDevTypes();

    public List<PubFlag> getButtons();

    public List<PubFlag> getMerchants();

    public List<PubFlag> getCode(String code);
    
    public List<PubFlag> getCodeByType(int type);

}
