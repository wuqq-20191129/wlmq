/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.samquery.mapper;

import com.goldsign.acc.app.samquery.entity.SamOperLogging;
import java.util.List;

/**
 *  操作日志
 * @author taidb
 */
public interface SamOperLoggingMapper {
    
    public List<SamOperLogging>  getSamOperLogging(SamOperLogging sol);
    
    public List<SamOperLogging>  getSamOperLoggingOperType();
    
    public List<SamOperLogging>  getSamOperLoggingSysType();
    
    public String  getModuleName(SamOperLogging vo);
}
