/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.goldsign.esmcs.dao;

import com.goldsign.csfrm.dao.IBaseDao;
import java.io.IOException;
import java.util.List;

/**
 * 参数文件访问接口
 * 
 * @author lenovo
 */
public interface IParamDao extends IBaseDao{

    /**
     * 取本地参数文件明细
     * 
     * @param file
     * @return
     * @throws IOException 
     */
    List<Object[]> getLocalParamFileDetails(String fileName, String filePath)
            throws Exception;
  
    /**
     * 取本地审计文件
     * 
     * @return
     * @throws Exception 
     */
    List<Object[]> getLocalAuditFiles(String startDate, String endDate)
            throws Exception;
    
    /**
     * 取本地错误文件
     *
     * @return
     * @throws Exception
     */
    List<Object[]> getLocalErrorFiles(String startDate, String endDate)
            throws Exception;
}
