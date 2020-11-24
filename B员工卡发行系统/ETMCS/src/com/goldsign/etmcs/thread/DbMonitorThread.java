package com.goldsign.etmcs.thread;

import com.goldsign.csfrm.env.BaseConstant;
import com.goldsign.csfrm.util.StringUtil;
import com.goldsign.etmcs.env.AppConstant;
import com.goldsign.etmcs.env.ConfigConstant;
import com.goldsign.etmcs.service.IMakeCardService;
import com.goldsign.etmcs.service.impl.MakeCardService;
import com.goldsign.etmcs.util.ConfigUtil;
import com.goldsign.etmcs.util.DbcpHelper;
import java.io.File;
import java.io.IOException;
import org.apache.log4j.Logger;

/**
 * 通讯心跳监控线程
 * 
 * @author lenovo
 */
public class DbMonitorThread extends Thread{
    private static final Logger logger = Logger.getLogger(DbMonitorThread.class.getName());
    
    /**
     * Creates a new instance of DbMonitorThread
     */
    private IMakeCardService makeCardService;
    
    public DbMonitorThread() {
        makeCardService = new MakeCardService();
    }

    public void run() {
        while (true) {
            try {
                //检测数据库连接，并更新连接状态栏
                this.checkDBConnect();
                if(AppConstant.DATABASE_STATUS){
                    //读服务器离线文件，写入数据库
                    this.readMakeCardFiles();    
                }
                this.sleep(AppConstant.HEART_HEAT_TIME);
            } catch (Exception ex) {
                //this.interrupt();
                logger.error(ex);
                break;
            }
        }
    }
    /**
     * 检测数据库连接，并更新连接状态栏
     */
    private void checkDBConnect(){
        
        //检测数据库连接池
        if(AppConstant.dbcpHelper == null){
            loadDataConnectionPool();
        }
        //检测数据库连接
        AppConstant.DATABASE_STATUS = AppConstant.dbcpHelper.checkDBConnect();
        
        String[] vars = new String[]{AppConstant.STATUS_BAR_BATABASE_STATUS};
        boolean[] statuses = new boolean[]{AppConstant.DATABASE_STATUS};
        
        //更新连接状态栏
        setSBarStatus(vars,statuses);

    }
    
    /**
     * 加载数据库连接池
     *
     * @return
     */
    private void loadDataConnectionPool(){
        
        DbcpHelper dbcpHelper = null;
        String driverName = ConfigUtil.getConfigValue(ConfigConstant.DataConnectionPoolTag, 
                ConfigConstant.DataConnectionPoolDriverTag);
        String url = ConfigUtil.getConfigValue(ConfigConstant.DataConnectionPoolTag, 
                ConfigConstant.DataConnectionPoolURLTag);
        String userId =  ConfigUtil.getConfigValue(ConfigConstant.DataConnectionPoolTag, 
                ConfigConstant.DataConnectionPoolUserTag);
        String password = ConfigUtil.getConfigValue(ConfigConstant.DataConnectionPoolTag, 
                ConfigConstant.DataConnectionPoolPasswordTag);
        String maxActive = ConfigUtil.getConfigValue(ConfigConstant.DataConnectionPoolTag,
                ConfigConstant.DataConnectionPoolMaxActiveTag);
        String maxIdle = ConfigUtil.getConfigValue(ConfigConstant.DataConnectionPoolTag,
                ConfigConstant.DataConnectionPoolMaxIdleTag);
        String maxWait = ConfigUtil.getConfigValue(ConfigConstant.DataConnectionPoolTag,
                ConfigConstant.DataConnectionPoolMaxWaitTag);
        try {
            dbcpHelper = new DbcpHelper(driverName, url, userId, password,
                    StringUtil.getInt(maxActive), StringUtil.getInt(maxIdle), StringUtil.getInt(maxWait));
            
            AppConstant.dbcpHelper = dbcpHelper;
            
        } catch (ClassNotFoundException ex) {
            logger.error(ex);
        }
    }
    
    /**
     * 设置状态栏状态
     * 正常、警告
     * @param vars
     * @param statuses 
     */
    protected void setSBarStatus(String[] vars, boolean[] statuses) {
        int len = vars.length;
        for (int i = 0; i < len; i++) {
            if (statuses[i]) {
                BaseConstant.publicPanel.setOpLink(vars[i], BaseConstant.STATUS_BAR_STATUS_COLOR_ON);
            } else {
                BaseConstant.publicPanel.setOpLinkError(vars[i], BaseConstant.STATUS_BAR_STATUS_COLOR_OFF);
            }
        }
    }

    
    //读服务器离线文件，写入数据库
    private void readMakeCardFiles() {
        String dir = BaseConstant.appWorkDir+ConfigUtil.getConfigValue(ConfigConstant.UploadTag, ConfigConstant.UploadMakeCardTag);
        File file = new File(dir);
        if(file.isDirectory()){
            String[] fileList = file.list();
            if(fileList.length>0){
                for(int i=0;i<fileList.length;i++){
                    if(fileList[i].indexOf(AppConstant.MAKE_CARD_TO_FILE_SUFFIX)>-1){
                        File readFile = new File(dir+"/"+fileList[i]);
                        if(readFile.exists()){
                            try {
                                if(makeCardService.readLocalFile(readFile)){//成功读取文件后删除操作
                                    readFile.delete();
                                }else{//读取文件失败后备份操作
                                    readFile.renameTo(new File(dir+"/"+fileList[i].replaceAll(AppConstant.MAKE_CARD_TO_FILE_SUFFIX,AppConstant.MAKE_CARD_TO_FILE_SUFFIX_BACK)));
                                }
                            } catch (IOException ex) {
                                logger.error(ex);
                            }
                        }
                    }
                }
            }
        }
    }
}
