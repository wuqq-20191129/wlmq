/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.samquery.mapper;
import com.goldsign.acc.app.samquery.entity.SamStockSta;
import java.util.List;
/**
 *
 * @author chenzx
 */
public interface SamStockStaMapper {
    
    public List<SamStockSta> getSamStockSta(SamStockSta vo);
    
}
