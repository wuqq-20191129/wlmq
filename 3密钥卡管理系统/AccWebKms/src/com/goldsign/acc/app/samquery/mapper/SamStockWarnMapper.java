/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.samquery.mapper;

import com.goldsign.acc.app.samquery.entity.SamStockWarn;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 *
 * @author taidb
 */
public interface SamStockWarnMapper {
    
    public List<SamStockWarn> getSamStockWarn(SamStockWarn ssw);
}
