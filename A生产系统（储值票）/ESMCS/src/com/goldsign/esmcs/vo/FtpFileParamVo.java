/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.goldsign.esmcs.vo;

/**
 *
 * @author lenovo
 */
public class FtpFileParamVo extends FtpLoginParamVo{

    private String remotePath;
    
    private String localPath;
    
    private String file;
    
    public FtpFileParamVo(){}
    
    public FtpFileParamVo(FtpLoginParamVo ftpLoginParamVo){
        this.setIp(ftpLoginParamVo.getIp());
        this.setUserCode(ftpLoginParamVo.getUserCode());
        this.setPwd(ftpLoginParamVo.getPwd());
    }

    /**
     * @return the remotePath
     */
    public String getRemotePath() {
        return remotePath;
    }

    /**
     * @param remotePath the remotePath to set
     */
    public void setRemotePath(String remotePath) {
        this.remotePath = remotePath;
    }

    /**
     * @return the localPath
     */
    public String getLocalPath() {
        return localPath;
    }

    /**
     * @param localPath the localPath to set
     */
    public void setLocalPath(String localPath) {
        this.localPath = localPath;
    }

    /**
     * @return the file
     */
    public String getFile() {
        return file;
    }

    /**
     * @param file the file to set
     */
    public void setFile(String file) {
        this.file = file;
    }

    @Override
    public String toString() {
        return "FtpFileParamVo{" + "remotePath=" + remotePath + ", localPath=" + localPath + ", file=" + file + '}';
    }
    
}
