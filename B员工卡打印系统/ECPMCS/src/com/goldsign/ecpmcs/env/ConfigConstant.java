/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.goldsign.ecpmcs.env;

/**
 * 配置文件常量
 * 
 * @author lenovo
 */
public class ConfigConstant extends AppConstant{

    //配置和日志文件
    public static final String CONFIG_FILE = "config/CommonConfig.xml";     //配置文件
    public static final String LOG_FILE = "config/Log4jConfig.xml";        //日志文件
    public static final String P_ERROR_FILE = "config/PrintErrorCode.txt";        //打印机错误代码文件
    public static final String PRINT_TEMPLATE_PATH = "template/templateTxt/";        //打印模板文件路径
    public static final String PRINT_BACKGROUND_PIC_PATH = "template/templatePics/";        //打印模板正反面背景图片路径
    
    //一级标签
    public static final String CommonTag = "Common";      //配置文件TAG
    public static final String DataConnectionPoolTag = "DataConnectionPool";
    public static final String RwDeviceTag = "RwDevice";
    public static final String UploadTag = "Upload";
    public static final String PhotoTag = "PhotoDir";
    public static final String PrinterTag = "Printer";
    
    //Common标签
    public static final String CommonStationIdTag = "StationId";
    public static final String CommonDeviceTypeTag = "DeviceType";
    public static final String CommonDeviceNoTag = "DeviceNo";
    public static final String CommonSystemFlagTag = "SystemFlag";
    
    //DataConnectionPool标签
    public static final String DataConnectionPoolDriverTag = "Driver";
    public static final String DataConnectionPoolURLTag = "URL";
    public static final String DataConnectionPoolUserTag = "User";
    public static final String DataConnectionPoolPasswordTag = "Password";
    public static final String DataConnectionPoolMaxActiveTag = "MaxActive";
    public static final String DataConnectionPoolMaxIdleTag = "MaxIdle";
    public static final String DataConnectionPoolMaxWaitTag = "MaxWait";

    //RwDevice标签
    public static final String RwDeviceRwPortTag = "RwPort";
    
    //Upload标签
    public static final String UploadLocalFileTag = "LocalFile";
    
    //Photo标签
    public static final String PhotoImgDirTag = "ImgDir";
    public static final String PhotoBmpDirTag = "BmpDir";
    public static final String PhotoSuffixTag = "Suffix";
    public static final String PhotoHeightTag = "Height";
    public static final String PhotoWidthTag = "Width";
    public static final String PhotoPoxTag = "Pox";
    public static final String PhotoPoyTag = "Poy";
    
    //Printer标签
    public static final String PrinterDriverTag = "Driver";
    public static final String PrinterTypeTag = "PrinterType";
    public static final String PrinterStepTag = "PrinterStep";
    
    //Font标签
    public static final String PrinterFontTag = "Font";
    public static final String PrinterFontSizeTag = "FontSize";
    public static final String PrinterFontStyleTag = "FontStyle";
    public static final String PrinterFontColorTag = "FontColor";
    public static final String PrinterFontXTag = "FontX";
    public static final String PrinterFontYTag = "FontY";
    public static final String PrinterFontHTag = "FontH";
    public static final String PrinterFontWTag = "FontW";
    public static final String PrinterFontAlignTag = "FontAlign";
    
    //模板标签
    public static final String TEMPLATECARDTYPE = "CardType";
    public static final String IMAGE = "Image";
    public static final String TEXT = "Text";
    public static final String BACKGROUND = "Background";
    
    public static final String IDPHOTO = "IDPhoto";
    public static final String NAME = "Name";
    public static final String DEPARTMENT = "Department";
    public static final String EMPLOYEEID = "EmployeeID";
    public static final String EMPLOYEECLASS = "EmployeeClass";
    public static final String BACKGROUNDPHOTO = "BackPhoto";
    
    
}
