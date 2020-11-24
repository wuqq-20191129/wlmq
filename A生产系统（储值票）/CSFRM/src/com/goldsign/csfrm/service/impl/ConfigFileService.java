package com.goldsign.csfrm.service.impl;

import com.goldsign.csfrm.exception.LoadException;
import com.goldsign.csfrm.util.XMLConfigHandler;
import com.goldsign.csfrm.vo.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Vector;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import org.xml.sax.SAXException;

/**
 *
 * @author lenovo
 * 
 * 配置文件服务类
 * 加载配置文件和日志文件
 * 
 */
public class ConfigFileService extends BaseService{

    private static final Logger logger = Logger.getLogger(ConfigFileService.class.getName());
    
    /**
     * 加载配置文件和日志文件
     * 
     * @param callParam
     * @return
     * @throws LoadException 
     */
    public CallResult loadConfigAndLogFile(CallParam callParam) throws LoadException {
        
        CallResult callResult = new CallResult();
        ConfigParam configParam = (ConfigParam)callParam;
        
        //加载日志文件
        loadLogFile(configParam.getLogFileName());
        
        Hashtable configs = null;
        try {
            //加载配置文件
            configs = loadConfigFile(configParam.getConfigFileName(), configParam.getTagVos());
            callResult.setObj(configs);
            
            logger.info("加载配置文件成功！");
        } catch (SAXException ex) {
            logger.error(ex);
            throw new LoadException("加载配置文件失败！");
        } catch (FileNotFoundException ex) {
            logger.error(ex);
            throw new LoadException("加载配置文件失败！");
        } catch (IOException ex) {
            logger.error(ex);
            throw new LoadException("加载配置文件失败！");
        }

        callResult.setResult(true);

        return callResult;
    }
    
    /**
     * 加配配置文件
     * 
     * @param fileName
     * @param tagVos
     * @return
     * @throws SAXException
     * @throws FileNotFoundException
     * @throws IOException 
     */
    public Hashtable loadConfigFile(String fileName, XmlTagVo[] tagVos) 
            throws SAXException, FileNotFoundException, IOException{
        
        XMLConfigHandler configHandler = new XMLConfigHandler();
        
        Vector<XmlConfigVo> configVos = new Vector<XmlConfigVo>();
        
        for(XmlTagVo tagVo: tagVos){
            configVos.add(new XmlConfigVo(tagVo));
        }
        //分析配置文件
        Hashtable configs = configHandler.parseConfigFile(fileName, configVos);
        
        return configs;
        
    }
        
    /**
     * 加载日志文件
     * 
     * @param fileName 
     */
    public void loadLogFile(String fileName){
        
         DOMConfigurator.configureAndWatch(fileName);
         
         logger.info("加载日志文件成功！");
    }
}
