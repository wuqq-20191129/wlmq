/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.prminfo.mapper;

import com.goldsign.acc.app.prminfo.entity.PenaltyReason;
import java.util.List;
/**
 *
 * @author mh
 */
public interface PenaltyReasonMapper {
    
    public List<PenaltyReason> getPenaltyReason(PenaltyReason pr);

    public int addPenaltyReason(PenaltyReason pr);

    public int modifyPenaltyReason(PenaltyReason pr);

    public List<PenaltyReason> getPenaltyReasonById(PenaltyReason pr);
    
    public List<PenaltyReason> getPenaltyReasonByName(PenaltyReason pr);

    public int deletePenaltyReason(PenaltyReason pr);
}
