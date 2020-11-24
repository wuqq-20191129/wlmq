/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.prminfo.mapper;

import com.goldsign.acc.app.prminfo.entity.PrmVersion;
import java.util.List;

/**
 *
 * @author hejj
 */
public interface PrmVersionMapper {

    public List<PrmVersion> selectPrmVersionByID(String parmTypeId);

    public List<PrmVersion> selectPrmVersionForCurrent(String parmTypeId);

    public String selectPrmVersionForSubmit(PrmVersion po);

    public int modifyPrmVersionForDraft(PrmVersion prmVersion);

    public int modifyPrmVersionForSubmit(PrmVersion prmVersion);

    public int addPrmVersion(PrmVersion po);

}
