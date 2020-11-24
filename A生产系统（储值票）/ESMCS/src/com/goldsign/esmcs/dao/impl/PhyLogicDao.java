/*
 * 文件名：PhyLogicDao
 * 版权：Copyright: goldsign (c) 2013
 */

package com.goldsign.esmcs.dao.impl;

import com.goldsign.csfrm.dao.impl.BaseDao;
import com.goldsign.csfrm.util.FileHelper;
import com.goldsign.esmcs.dao.IPhyLogicDao;
import com.goldsign.esmcs.env.AppConstant;
import com.goldsign.esmcs.env.ConfigConstant;
import com.goldsign.esmcs.env.FileConstant;
import com.goldsign.esmcs.util.ConfigUtil;
import com.goldsign.esmcs.vo.FtpFileParamVo;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;
import org.apache.log4j.Logger;


/*
 * 〈一句话功能简述〉
 * @author     lindaquan
 * @version    V1.0
 * @createTime 2013-9-16
 */

public class PhyLogicDao extends BaseDao implements IPhyLogicDao{

    private static final Logger logger = Logger.getLogger(PhyLogicDao.class.getName());
    
    /**
     * 取当天的FTP逻辑卡号对照表
     * 
     * @return 
     */
    private File getPhyLogicFile(){
        
        //文件信息获取
        FtpFileParamVo ftpFileVo = new FtpFileParamVo(ConfigUtil.getFtpLoginParam());
        ftpFileVo.setFile(FileHelper.getFileName(FileConstant.PHY_LOGIC_FILE_PRE));
        ftpFileVo.setLocalPath(ConfigUtil.getConfigValue(ConfigConstant.DownloadTag, ConfigConstant.FtpPhyLogicLocalPathTag));
        ftpFileVo.setRemotePath(ConfigUtil.getConfigValue(ConfigConstant.DownloadTag, ConfigConstant.FtpPhyLogicRemotePathTag));
        
        File file = new File(ftpFileVo.getLocalPath(),ftpFileVo.getFile());
        
        return file;
    }
    
    /**
     * 查询逻辑卡号
     * @param phyId 物理卡号
     * @return logicId 逻辑卡号
     */
    @Override
    public String findLogicId(String phyId) throws Exception{
        
        String logicId = null;
        //文件信息获取
        File file = getPhyLogicFile();
        if(!file.exists()){
            return null;
        }
        
        FileReader fr = null;
        BufferedReader br = null;
        try {
            fr = new FileReader(file);
            br = new BufferedReader(fr);
            String line = br.readLine();
            long st = System.currentTimeMillis();
            while (line != null){
                if (line.indexOf(phyId) > -1) {
                    long ed = System.currentTimeMillis();
                    logger.debug("搜索时间："+(ed-st));
                    String[] arr = line.split(AppConstant.WELL_SIGN);
                    if(arr.length>=2){
                        logicId = arr[1];
                        break;
                    }
                }
                line = br.readLine();
            }
        }finally{
            if(null != br){
                br.close();
            }
            if(null != fr){
                fr.close();
            }
        }
        
        return logicId;
    }
    
    /**
     * 取物理卡号对照表
     * 
     * @throws Exception 
     */
    @Override
    public Map<String, String> getPhyLogicMap() throws Exception{
        
        Map<String, String> phyLogicMap = new HashMap<String, String>();
        
        long st = System.currentTimeMillis();
        System.out.print("当前虚拟机最大可用内存为:");   
        System.out.println(Runtime.getRuntime().maxMemory()/1024/1024+"M");   
        System.out.print("当前，虚拟机已占用内存:");   
        System.out.println(Runtime.getRuntime().totalMemory()/1024/1024+"M"); 
        File file = getPhyLogicFile();
        if(!file.exists()){
            return phyLogicMap;
        }
        FileReader fr = null;
        BufferedReader br = null;
        try {
            fr = new FileReader(file);
            br = new BufferedReader(fr);
            String line = br.readLine();
            while (line != null){
                String[] arr = line.split(AppConstant.WELL_SIGN);
                if(arr.length > 1){
                    phyLogicMap.put(arr[0], arr[1]);
                }
                line = br.readLine();
            }
            long ed = System.currentTimeMillis();
            System.out.print("当前，虚拟机已占用内存:");   
            System.out.print(Runtime.getRuntime().totalMemory()/1024/1024+"M,"+"缓存完成时间：");  
            System.out.println(ed-st);
            
        }finally{
            if(null != br){
                br.close();
            }
            if(null != fr){
                fr.close();
            }
        }
        
        return phyLogicMap;
    }

}
