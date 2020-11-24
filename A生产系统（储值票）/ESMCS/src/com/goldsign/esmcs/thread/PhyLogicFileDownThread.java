/*
 * 文件名：PhyLogicFileDownThread
 * 版权：Copyright: goldsign (c) 2013
 */

package com.goldsign.esmcs.thread;

import com.goldsign.csfrm.util.FileHelper;
import com.goldsign.esmcs.application.Application;
import com.goldsign.esmcs.env.AppConstant;
import com.goldsign.esmcs.env.ConfigConstant;
import com.goldsign.esmcs.env.FileConstant;
import com.goldsign.esmcs.ftp.CommuFtp;
import com.goldsign.esmcs.service.IFileService;
import com.goldsign.esmcs.util.ConfigUtil;
import com.goldsign.esmcs.vo.FtpFileParamVo;
import java.io.File;
import org.apache.log4j.Logger;


/*
 * 物理卡号与逻辑卡号对照文件下载线程
 * @author     lindaquan
 * @version    V1.0
 * @createTime 2013-9-16
 */

public class PhyLogicFileDownThread extends Thread{
    
    private static final Logger logger = Logger.getLogger(PhyLogicFileDownThread.class.getName());
    
    //FTP锁
    public static final Object FTP_LOCK = new Object();
    
    //FTP
    private CommuFtp ftp = new CommuFtp();
    
    private IFileService fileService;
    
    public PhyLogicFileDownThread(){
        fileService = ((Application)AppConstant.application).getFileService();
    }
    
    /**
     * 运行
     * 
     */
    @Override
    public void run() {
        try {
            //文件信息获取
            FtpFileParamVo ftpFileVo = getFileParamVo();
            File file = new File(ftpFileVo.getLocalPath(), ftpFileVo.getFile());
            
            if(!file.exists()){
                synchronized(FTP_LOCK){
                    //下载文件
                    ftp.ftpGetFileSingle(ftpFileVo.getIp(), ftpFileVo.getUserCode(), ftpFileVo.getPwd(), 
                            ftpFileVo.getFile(), ftpFileVo.getLocalPath(), ftpFileVo.getRemotePath());
                }
            }
            //把结果放到缓存中
            fileService.setPhyLogicMap();
               
        } catch (Exception e) {
            logger.error(e);
        }
    }
    
    private FtpFileParamVo getFileParamVo(){
        
        FtpFileParamVo ftpFileVo = new FtpFileParamVo(ConfigUtil.getFtpLoginParam());
        ftpFileVo.setFile(FileHelper.getFileName(FileConstant.PHY_LOGIC_FILE_PRE));
        ftpFileVo.setLocalPath(ConfigUtil.getConfigValue(ConfigConstant.DownloadTag, ConfigConstant.FtpPhyLogicLocalPathTag));
        ftpFileVo.setRemotePath(ConfigUtil.getConfigValue(ConfigConstant.DownloadTag, ConfigConstant.FtpPhyLogicRemotePathTag));
    
        return ftpFileVo;
    }

}
