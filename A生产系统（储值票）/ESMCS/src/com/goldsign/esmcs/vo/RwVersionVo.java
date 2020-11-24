/*
 * 文件名：RwVersionVo
 * 版权：Copyright: goldsign (c) 2013
 */

package com.goldsign.esmcs.vo;


/*
 * 读写器版本实体类
 * @author     lindaquan
 * @version    V1.0
 * @createTime 2013-9-12
 */

public class RwVersionVo {
    
    public String verApi;			// API识别版本
    public String verApiFile;			// API文件版本，日期＋序号
    public String verRfDev;			// Rf驱动识别版本
    public String verRfFile;			// Rf文件版本，日期＋序号
    public String verSamDev;			// Sam驱动识别版本
    public String verSamFile;			// Sam驱动文件版本，日期＋序号

    public String getVerApi() {
        return verApi;
    }

    public void setVerApi(String verApi) {
        this.verApi = verApi;
    }

    public String getVerApiFile() {
        return verApiFile;
    }

    public void setVerApiFile(String verApiFile) {
        this.verApiFile = verApiFile;
    }

    public String getVerRfDev() {
        return verRfDev;
    }

    public void setVerRfDev(String verRfDev) {
        this.verRfDev = verRfDev;
    }

    public String getVerRfFile() {
        return verRfFile;
    }

    public void setVerRfFile(String verRfFile) {
        this.verRfFile = verRfFile;
    }

    public String getVerSamDev() {
        return verSamDev;
    }

    public void setVerSamDev(String verSamDev) {
        this.verSamDev = verSamDev;
    }

    public String getVerSamFile() {
        return verSamFile;
    }

    public void setVerSamFile(String verSamFile) {
        this.verSamFile = verSamFile;
    }

}
