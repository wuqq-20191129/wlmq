/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.prminfo.mapper;

import com.goldsign.acc.app.prminfo.entity.AgentRate;
import java.util.List;

/**
 *
 * @author ldz
 */
public interface AgentRateMapper {
    public List<AgentRate> getAgentRate(AgentRate agent);

    public int addAgentRate(AgentRate agent);

    public int modifyAgentRate(AgentRate agent);

    public List<AgentRate> getAgentRateById(AgentRate agent);

    public int deleteAgentRate(AgentRate agent);

    public int submitToOldFlag(AgentRate agent);

    public int submitFromDraftToCurOrFur(AgentRate agent);

    public int deleteAgentRateForClone(AgentRate agent);
    
    public int cloneFromCurOrFurToDraft(AgentRate agent);
}
