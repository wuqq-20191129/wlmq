/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.frame.task;

import com.goldsign.acc.app.config.entity.Config;
import com.goldsign.acc.app.config.entity.ConfigKey;
import com.goldsign.acc.app.config.mapper.ConfigMapper;
import com.goldsign.acc.frame.constant.ConfigConstant;
import static com.goldsign.acc.frame.constant.ConfigConstant.FILTER_PROPERTIES;
import com.goldsign.acc.frame.constant.WebConstant;
import static com.goldsign.acc.frame.interceptor.AuthenticateInterceptor.IS_IN_CLUSTER_KEY;
import static com.goldsign.acc.frame.interceptor.AuthenticateInterceptor.NON_AUTHENTICATION_MIME_LIST_KEY;
import static com.goldsign.acc.frame.interceptor.AuthenticateInterceptor.NON_AUTHENTICATION_URL_LIST_KEY;
import static com.goldsign.acc.frame.interceptor.AuthenticateInterceptor.PHYLOGIC_UPLOAD_PATH;
import com.goldsign.acc.frame.mapper.VersionMapper;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 刷新系统配置
 * @author lind
 */

@Component
public class ConfigTask {

    @Autowired
    private ConfigMapper configMapper;
    @Autowired
    private VersionMapper versionMapper;

    /*
    心跳更新。启动时执行一次，之后每隔2小时执行一次
    */
    @Scheduled(fixedRate = 1000*60*60*2)
    public void authenRefresh(){
        System.out.println("com.goldsign.acc.frame.task.configTask.authenRefresh()");
        FILTER_PROPERTIES.put(NON_AUTHENTICATION_URL_LIST_KEY, "/SysVersion,/login,/login.html,/logon,/index.htm,/login.do,/jsp/showErrorMessage.jsp,/index_1.htm,/index_1.jsp,/jsp/index2.htm,/jsp/ParameterEncode.jsp,/jsp/logout.jsp,/Logout.do,/screenindex.htm,/screenindex_1.jsp,/screenBlank.htm,/ScreenShbglJk.do,/Action2XML.do,/introduce.jsp,/screenframe.htm,/jsp/ReportGenerator.jsp");
        FILTER_PROPERTIES.put(NON_AUTHENTICATION_MIME_LIST_KEY, "gif,jpg,css,js,pdf,swf,ppt,png");
        FILTER_PROPERTIES.put(IS_IN_CLUSTER_KEY, "false");
//        FILTER_PROPERTIES.put(PHYLOGIC_UPLOAD_PATH, "/home/wweb/op/phylogic");

        ConfigKey key = new ConfigKey();
        key.setType("1");
//        key.setTypeSub("4");
        List<Config> configs = new ArrayList<Config>();
//        configs = configMapper.selectConfigs(key);
        //获取所有type为'1'的配置
        configs = configMapper.selectAllConfigs(key);

        for(Config config:configs){
            String configName = config.getConfigName();

            if (configName.equals(NON_AUTHENTICATION_URL_LIST_KEY)
                    || configName.equals(NON_AUTHENTICATION_MIME_LIST_KEY)
                    || configName.equals(IS_IN_CLUSTER_KEY)) {

                FILTER_PROPERTIES.put(config.getConfigName(),config.getConfigValue());
                continue;
            }

            if (configName.equals("PHYLOGIC_UPLOAD_PATH")) {
                ConfigConstant.PHYLOGIC_UPLOAD_PATH = config.getConfigValue();
                continue;
            }

            if (configName.equals("PARA_UPLOAD_PATH")) {
                ConfigConstant.PARA_UPLOAD_PATH = config.getConfigValue();
                continue;
            }
            //add by zhongziqi 20180202
            if(configName.equals(ConfigConstant.EXPORT_EXCEL_PATH_KEY)) {
            	ConfigConstant.EXPORT_EXCEL_PATH = config.getConfigValue();
            	continue;
            }

            System.setProperty(config.getConfigName(),config.getConfigValue());
        }

        WebConstant.SYS_VERSION = versionMapper.getMaxVersion();

    }
}
