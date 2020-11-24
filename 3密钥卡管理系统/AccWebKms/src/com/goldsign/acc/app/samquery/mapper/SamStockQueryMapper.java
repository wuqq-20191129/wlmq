/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.samquery.mapper;
import com.goldsign.acc.app.samquery.entity.SamStockQuery;
import java.util.List;
/**
 *
 * @author chenzx
 */
public interface SamStockQueryMapper {
    
    public List<SamStockQuery> getSamStockQuery(SamStockQuery vo);
    
} 