/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.goldsign.esmcs.dao.impl;

import com.goldsign.csfrm.dao.impl.BaseDao;
import com.goldsign.esmcs.dao.IParamDao;
import com.goldsign.esmcs.env.AppConstant;
import com.goldsign.esmcs.env.ConfigConstant;
import com.goldsign.esmcs.env.LocalFileConstant;
import com.goldsign.esmcs.util.ConfigUtil;
import com.goldsign.publib.io.FileBufferReader;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import org.apache.log4j.Logger;

/**
 * 参数文件访问类
 * 
 * @author lenovo
 */
public class ParamDao extends BaseDao implements IParamDao{

    private static final Logger logger = Logger.getLogger(ParamDao.class.getName());

    /**
     * 取本地参数文件明细
     * 
     * @param file
     * @return
     * @throws IOException 
     */
    @Override
    public List<Object[]> getLocalParamFileDetails(String fileName, String filePath) 
            throws Exception{
        
        File file = new File(filePath + AppConstant.SLASH_LIX_SIGN + fileName);
        if (!file.exists()) {
            return new ArrayList<Object[]>();
        }
        
        List<Object[]> details = new ArrayList<Object[]>();
        BufferedReader br = null;
        try {
            br = new FileBufferReader(file);
            String line = br.readLine();
            while(line != null){
                details.add(new Object[]{line});
                line = br.readLine();
            }
        }finally{
            if(null != br){
                br.close();
            }
        }
        
        return details;
    }

    /**
     * 
     * @return
     * @throws IOException 
     */
    private File getAuditFilePath() throws IOException {
        
        Hashtable uploads = (Hashtable) AppConstant.configs.get(ConfigConstant.DownloadTag);
        String AUDIT_FILE_PATH = (String) uploads.get(ConfigConstant.FtpAuditLocalPathTag);
        
        File file = new File(AUDIT_FILE_PATH);
        
        return file;
    }
    
    /**
     * 取审计文件
     * 
     * @param startDate
     * @param endDate
     * @return
     * @throws Exception 
     */
    @Override
    public List<Object[]> getLocalAuditFiles(final String startDate, final String endDate) 
            throws Exception {
        
        File auditPath = getAuditFilePath();
        if(!auditPath.exists()){
            return new ArrayList<Object[]>();
        }
        final String deviceId = ConfigUtil.getDeviceNo();
        File[] files = auditPath.listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                if(!pathname.isFile()){
                    return false;
                }
                String fileName = pathname.getName();
                String filePre = LocalFileConstant.ES_FTP_AUDIT_FILE_PRE + deviceId + AppConstant.DOT_SIGN;
                if(!fileName.startsWith(filePre)){
                    return false;
                }
                if(fileName.length()<17){
                    return false;
                }
                String fileDate = fileName.substring(9, 17);
                if(fileDate.compareTo(startDate)>=0
                        && fileDate.compareTo(endDate)<=0){
                    return true;
                }
                return false;
            }
        });
        if(null == files){
            return new ArrayList<Object[]>();
        }
        List<Object[]> auditFiles = new ArrayList<Object[]>();
        for(File file: files){
            Object[] auditFile = new Object[3];
            auditFile[0] = AppConstant.AUDIT_PARAM_FILE_TYPE_AUDIT_NAME;
            auditFile[1] = file.getName();
            auditFile[2] = file.getParent();
            auditFiles.add(auditFile);
        }
        
        return auditFiles;
    }

    /**
     * 取错误文件
     * 
     * @param startDate
     * @param endDate
     * @return
     * @throws Exception 
     */
    @Override
    public List<Object[]> getLocalErrorFiles(final String startDate, final String endDate) 
            throws Exception {
        
        File auditPath = getAuditFilePath();
        if(!auditPath.exists()){
            return new ArrayList<Object[]>();
        }
        final String deviceId = ConfigUtil.getDeviceNo();
        File[] files = auditPath.listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                if(!pathname.isFile()){
                    return false;
                }
                String fileName = pathname.getName();
                String filePre = LocalFileConstant.ES_FTP_ERROR_FILE_PRE + deviceId + AppConstant.DOT_SIGN;
                if(!fileName.startsWith(filePre)){
                    return false;
                }
                if(fileName.length()<16){
                    return false;
                }
                String fileDate = fileName.substring(8, 16);
                if(fileDate.compareTo(startDate)>=0
                        && fileDate.compareTo(endDate)<=0){
                    return true;
                }
                return false;
            }
        });
        if(null == files){
            return new ArrayList<Object[]>();
        }
        List<Object[]> errorFiles = new ArrayList<Object[]>();
        for(File file: files){
            Object[] errorFile = new Object[3];
            errorFile[0] = AppConstant.AUDIT_PARAM_FILE_TYPE_ERROR_NAME;
            errorFile[1] = file.getName();
            errorFile[2] = file.getParent();
            errorFiles.add(errorFile);
        }
        
        return errorFiles;
    }

}
