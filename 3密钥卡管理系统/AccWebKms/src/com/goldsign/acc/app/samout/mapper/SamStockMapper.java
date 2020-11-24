/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.samout.mapper;

import com.goldsign.acc.app.samout.entity.SamStock;
import java.util.Vector;

/**
 *
 * @author ldz
 */
public interface SamStockMapper {

    public Vector checkLogicNo(SamStock checkVo);
    
}
