/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.system.mapper;

import com.goldsign.acc.app.system.entity.Cluster;
import java.util.List;

/**
 * 集群
 * @author luck
 */
public interface ClusterMapper {

    public List<Cluster> queryPlan();

    public int update(Cluster vo);

    public int insertIntoCur(Cluster vo);

    public int insertIntoHis(Cluster vo);
    
}
