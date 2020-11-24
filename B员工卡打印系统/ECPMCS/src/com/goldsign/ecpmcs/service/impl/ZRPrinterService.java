/*
 * 文件名：ZRPrinterService
 * 版权：Copyright: goldsign (c) 2013
 */

package com.goldsign.ecpmcs.service.impl;

import com.goldsign.csfrm.util.MessageShowUtil;
import com.goldsign.ecpmcs.dll.ZBRGraphicsDll;
import com.goldsign.ecpmcs.dll.ZBRPrinterDll;
import com.goldsign.ecpmcs.env.AppConstant;
import com.goldsign.ecpmcs.env.ConfigConstant;
import com.goldsign.ecpmcs.util.ConfigUtil;
import com.goldsign.ecpmcs.util.ImageUtil;
import com.goldsign.ecpmcs.util.PubUtil;
import com.goldsign.ecpmcs.vo.ResultVo;
import com.goldsign.ecpmcs.vo.SignCardPrintVo;
import java.awt.Image;
import java.awt.print.PageFormat;
import java.awt.print.PrinterJob;
import java.io.File;
import java.util.List;
import javax.swing.ImageIcon;
import org.apache.log4j.Logger;


/*
 * 〈打印机服务实现类〉
 * @author     lindaquan
 * @version    V1.0
 * @createTime 2014-4-16
 */

public class ZRPrinterService{
    
    private static final Logger logger = Logger.getLogger(ZRPrinterService.class.getName());
    
    public static String getPrinterVer() {
        ZBRPrinterDll.loadZBRPrinterDll();
        return ZBRPrinterDll.getSDKVer();
    }
    
    public static String getGraphicsVer() {
        ZBRPrinterDll.loadZBRPrinterDll();
        return ZBRGraphicsDll.getSDKVer();
    }

    /**
     * 打印状态
     * @return 
     */
    public static boolean isReady(){
        String driverName = ConfigUtil.getConfigValue(ConfigConstant.PrinterTag, ConfigConstant.PrinterDriverTag);
        ZBRGraphicsDll.loadZBRGraphicsDll();
        
        ResultVo vo = ZBRGraphicsDll.isPrinterReady(driverName);
        if(vo.getResultCode() == 0){
            logger.info("Printer is printing,Error code:" + vo.getErrCode() + "," + vo.getErrDescription());
//            ZBRGraphicsDll.closeGraphics(vo.getCodex());
            return false;
        }
        if(vo.getResultCode() == -2){
            logger.info(vo.getExceptionString());
//            ZBRGraphicsDll.closeGraphics(vo.getCodex());
            return false;
        }
        logger.info("Printer is ready!");
        return true;
    }
    
    /**
     * 储值卡打印
     * 打印
     * @param printVo
     * @return 
     */
    public static boolean print(SignCardPrintVo printVo) {
        if(printVo.getPhotoName().isEmpty() && printVo.getName().isEmpty()){
            logger.info("打印信息为空!");
            return false;
        }
        
        String driverName = ConfigUtil.getConfigValue(ConfigConstant.PrinterTag, ConfigConstant.PrinterDriverTag);
        int height = Integer.valueOf(ConfigUtil.getConfigValue(ConfigConstant.PhotoTag, ConfigConstant.PhotoHeightTag));
        int width = Integer.valueOf(ConfigUtil.getConfigValue(ConfigConstant.PhotoTag, ConfigConstant.PhotoWidthTag));
        int pox = Integer.valueOf(ConfigUtil.getConfigValue(ConfigConstant.PhotoTag, ConfigConstant.PhotoPoxTag));
        int poy = Integer.valueOf(ConfigUtil.getConfigValue(ConfigConstant.PhotoTag, ConfigConstant.PhotoPoyTag));
        String font = ConfigUtil.getConfigValue(ConfigConstant.PrinterTag, ConfigConstant.PrinterFontTag);
        int fontSize = Integer.valueOf(ConfigUtil.getConfigValue(ConfigConstant.PrinterTag, ConfigConstant.PrinterFontSizeTag));
        int fontStyle = Integer.parseInt(ConfigUtil.getConfigValue(ConfigConstant.PrinterTag, ConfigConstant.PrinterFontStyleTag),16);
        int fontColor = Integer.parseInt(ConfigUtil.getConfigValue(ConfigConstant.PrinterTag, ConfigConstant.PrinterFontColorTag),16);
        
        int poxT = Integer.parseInt(ConfigUtil.getConfigValue(ConfigConstant.PrinterTag, ConfigConstant.PrinterFontXTag));
        int poyT = Integer.parseInt(ConfigUtil.getConfigValue(ConfigConstant.PrinterTag, ConfigConstant.PrinterFontYTag));
        int widthT = Integer.parseInt(ConfigUtil.getConfigValue(ConfigConstant.PrinterTag, ConfigConstant.PrinterFontWTag));
        int heightT  = Integer.parseInt(ConfigUtil.getConfigValue(ConfigConstant.PrinterTag, ConfigConstant.PrinterFontHTag));
        int alignment = Integer.parseInt(ConfigUtil.getConfigValue(ConfigConstant.PrinterTag, ConfigConstant.PrinterFontAlignTag));
        
        ZBRGraphicsDll.loadZBRGraphicsDll();
        ZBRGraphicsDll.clearGraphics();
        
        ResultVo vo = ZBRGraphicsDll.initGraphics(driverName);
        if(vo.getResultCode() == 0){
            logger.info("initGraphics Error,Error code:" + vo.getErrCode() + "," + vo.getErrDescription());
            ZBRGraphicsDll.closeGraphics(vo.getCodex());
            return false;
        }
        if(vo.getResultCode() == -2){
            logger.info(vo.getExceptionString());
            return false;
        }
        logger.info("initGraphics successful!");
        
        String filename = printVo.getPhotoName();
        File file = new File(filename);
        if(file.exists() && !file.isDirectory()){
            String dir = ImageUtil.jpg2bmp(filename);
            ResultVo vodi = ZBRGraphicsDll.drawImageRect(dir, pox, poy, width, height);
            if(vodi.getResultCode() == 0){
                logger.info("drawImageRect Error,Error code:" + vodi.getErrCode() + "," + vodi.getErrDescription());
                ZBRGraphicsDll.closeGraphics(vo.getCodex());
                return false;
            }
            if(vodi.getResultCode() == -2){
                logger.info(vodi.getExceptionString());
                ZBRGraphicsDll.closeGraphics(vo.getCodex());
                return false;
            }
            logger.info("drawImageRect successful!");
        }

        if(!printVo.getName().isEmpty()){
            ResultVo vodt = ZBRGraphicsDll.drawTextRect(poxT, poyT, widthT, heightT, alignment, 
                    printVo.getName().trim(), font, fontSize, fontStyle, fontColor);
            if(vodt.getResultCode() == 0){
                logger.info("drawText Error,Error code:" + vodt.getErrCode() + "," + vodt.getErrDescription());
                ZBRGraphicsDll.closeGraphics(vo.getCodex());
                return false;
            }
            if(vodt.getResultCode() == -2){
                logger.info(vodt.getExceptionString());
                ZBRGraphicsDll.closeGraphics(vo.getCodex());
                return false;
            }
            logger.info("drawText successful!");
        }

        ResultVo vopg = ZBRGraphicsDll.printGraphics(vo.getCodex());
        if(vopg.getResultCode() == 0){
            logger.info("printGraphics Error,Error code:" + vopg.getErrCode() + "," + vopg.getErrDescription());
            ZBRGraphicsDll.closeGraphics(vo.getCodex());
            return false;
        }
        if(vopg.getResultCode() == -2){
            logger.info(vopg.getExceptionString());
            ZBRGraphicsDll.closeGraphics(vo.getCodex());
            return false;
        }
        logger.info("printGraphics successful!");

        ZBRGraphicsDll.closeGraphics(vo.getCodex());
        return true;
    }
    
    
    /**
     * 员工卡打印
     * 按模板打印
     * @param printVo
     * @return 
     */
    public static boolean printForTemplate(SignCardPrintVo printVo, List config) {
        String cardTypeLine = (String) config.get(0);
        String[] lineData = cardTypeLine.split(AppConstant.WELL_SIGN, -1);
        //判断打印类型
        String card_type = lineData[1].toString();
        boolean isbackFlag = "back".equals(card_type)?true:false;
        if(!isbackFlag){
            if(printVo.getPhotoName().isEmpty() && printVo.getIdentityId().isEmpty()){
                MessageShowUtil.errorOpMsg("打印信息为空!");
                MessageShowUtil.alertErrorMsg("打印信息为空!");
                logger.info("打印信息为空!");
                return false;
            }
        }
        
        String driverName = ConfigUtil.getConfigValue(ConfigConstant.PrinterTag, ConfigConstant.PrinterDriverTag);
        ZBRGraphicsDll.loadZBRGraphicsDll();
        ZBRGraphicsDll.clearGraphics();
        
        ResultVo vo = ZBRGraphicsDll.initGraphics(driverName);
        if(vo.getResultCode() == 0){
            logger.info("initGraphics Error,Error code:" + vo.getErrCode() + "," + vo.getErrDescription());
            ZBRGraphicsDll.closeGraphics(vo.getCodex());
            return false;
        }
        logger.debug("initGraphics successful!");
        for(int i=0; i<config.size(); i++){
            String line = (String) config.get(i);
            String[] data = line.split(AppConstant.WELL_SIGN, -1);
            
            String path = System.getProperty("user.dir");
            path += "/" + ConfigConstant.PRINT_BACKGROUND_PIC_PATH;
            //背景图片 - 正面A、B、C三种&&实习员工出入证正面&&背面
            String classFileName = "";
            if(!"0202".equals(card_type)){
                if("0501".equals(card_type)){
                    classFileName = path + AppConstant.CLASS_A_PHOYO_NAME;
                }else if("0502".equals(card_type)){
                    classFileName = path + AppConstant.CLASS_B_PHOYO_NAME;
                }else if("0503".equals(card_type)){
                    classFileName = path + AppConstant.CLASS_C_PHOYO_NAME;
                }else if("0504".equals(card_type)){
                    classFileName = path + AppConstant.CLASS_PASS_PHOYO_NAME;
                }else if("back".equals(card_type)){
                    classFileName = path + AppConstant.EMPLOYEE_BACK_PHOTO_NAME;
                }else if("0201".equals(card_type)){
                    classFileName = path + AppConstant.SINGCARD_PHOTO_NAME;
                }else{

                }
                //classFileName = classFileName.replace("\\", "/");
                File classFile = new File(classFileName);
                if(classFile.exists() && !classFile.isDirectory()
                        && data[0].equals(ConfigConstant.IMAGE) && data[1].equals(ConfigConstant.BACKGROUNDPHOTO)){

                    String dir = ImageUtil.jpg2bmp(classFileName);
                    ResultVo vodi = ZBRGraphicsDll.drawImageRect(dir, 
                            Integer.valueOf(data[4]), Integer.valueOf(data[5]), Integer.valueOf(data[6]), Integer.valueOf(data[7]));
                    if(vodi.getResultCode() == 0){
                        logger.info("drawImageRect Error,Error code:" + vodi.getErrCode() + "," + vodi.getErrDescription());
                        ZBRGraphicsDll.closeGraphics(vo.getCodex());
                        return false;
                    }
                    logger.info("drawImageRect successful!");
                }
            }else{
                
                classFileName = path + AppConstant.SINGCARD_PHOTO_NAME;
                classFileName = classFileName.replace(".jpg", "_landscape.jpg");
                File file = new File(classFileName);
                if(file.exists() && !file.isDirectory() 
                        && data[0].equals(ConfigConstant.IMAGE) && data[1].equals(ConfigConstant.BACKGROUNDPHOTO)){
                    String dir = ImageUtil.jpg2bmp(classFileName);
                    ResultVo vodi = ZBRGraphicsDll.drawImageRect(dir, 
                            Integer.valueOf(data[4]), Integer.valueOf(data[5]), Integer.valueOf(data[6]), Integer.valueOf(data[7]));
                    if(vodi.getResultCode() == 0){
                        logger.info("drawImageRect Error,Error code:" + vodi.getErrCode() + "," + vodi.getErrDescription());
                        ZBRGraphicsDll.closeGraphics(vo.getCodex());
                        return false;
                    }
                    logger.info("drawImageRect successful!");
                }
            }
            
            
            
            
            //图片 - 证件相
            String filename = printVo.getPhotoName();
            File file = new File(filename);
            if(file.exists() && !file.isDirectory() 
                    && data[0].equals(ConfigConstant.IMAGE) && data[1].equals(ConfigConstant.IDPHOTO)){
                String dir = ImageUtil.jpg2bmp(filename);
                ResultVo vodi = ZBRGraphicsDll.drawImageRect(dir, 
                        Integer.valueOf(data[4]), Integer.valueOf(data[5]), Integer.valueOf(data[6]), Integer.valueOf(data[7]));
                if(vodi.getResultCode() == 0){
                    logger.info("drawImageRect Error,Error code:" + vodi.getErrCode() + "," + vodi.getErrDescription());
                    ZBRGraphicsDll.closeGraphics(vo.getCodex());
                    return false;
                }
                logger.info("drawImageRect successful!");
            }
            

            //文字 -姓名
            if(!printVo.getName().isEmpty() &&
                    data[0].equals(ConfigConstant.TEXT) && data[1].equals(ConfigConstant.NAME)){
                String employee_name = printVo.getName().trim();
                employee_name = formatNameStr(employee_name);
                int lengthName = count(employee_name);
                int yLine = Integer.valueOf(data[8]);
                int xLine = Integer.valueOf(data[7]);
                int fontSize = Integer.valueOf(data[4]);
                int width = Integer.valueOf(data[9]);
                int fontAlign = Integer.valueOf(data[11]);
                if(lengthName>8){//名字长度大于8位，调整Y轴高度
                    fontSize -=1;
                    width += 180;
                    xLine += 20;
                    fontAlign = 5;//4:居中；5：居左；6：居右
                }else if(lengthName>4&&lengthName<=8){//正常显示

                }else{//名字长度小于等于4位，每位中间加空格
                    employee_name = appendSpace(employee_name);
                }
                ResultVo vodt = ZBRGraphicsDll.drawTextRect(
                        xLine, yLine, 
                        width, Integer.valueOf(data[10]),
                        fontAlign, employee_name, 
                        data[3], fontSize, 
                        Integer.valueOf(data[5]), Integer.valueOf(data[6]));
                if(vodt.getResultCode() == 0){
                    logger.info("文字 -姓名 drawText Error,Error code:" + vodt.getErrCode() + "," + vodt.getErrDescription());
                    ZBRGraphicsDll.closeGraphics(vo.getCodex());
                    return false;
                }
                logger.info("文字 -姓名 drawText successful!");
            }
            
            //文字 - 部门
            if(!printVo.getDepartment().isEmpty() &&
                    data[0].equals(ConfigConstant.TEXT) && data[1].equals(ConfigConstant.DEPARTMENT)){
                String department_temp = PubUtil.getMapString(AppConstant.EMPLOYEE_DEPARTMENT, printVo.getDepartment().trim());
                department_temp = formatNameStr(department_temp);
                int lengthName = count(department_temp);
                int yLine = Integer.valueOf(data[8]);
                int xLine = Integer.valueOf(data[7]);
                int fontSize = Integer.valueOf(data[4]);
                int width = Integer.valueOf(data[9]);
                int fontAlign = Integer.valueOf(data[11]);
                if(lengthName>8){//名字长度大于8位，调整Y轴高度
                    fontSize -=1;
                    width += 180;
                    xLine += 20;
                    fontAlign = 5;//4:居中；5：居左；6：居右
                }else if(lengthName>4&&lengthName<=8){//正常显示

                }else{//名字长度小于等于4位，每位中间加空格
                    department_temp = appendSpace(department_temp);
                }
                ResultVo vodt = ZBRGraphicsDll.drawTextRect(
                        xLine, yLine, 
                        width, Integer.valueOf(data[10]),
                        fontAlign, department_temp, 
                        data[3], fontSize, 
                        Integer.valueOf(data[5]), Integer.valueOf(data[6]));
                if(vodt.getResultCode() == 0){
                    logger.info("文字 -部门 drawText Error,Error code:" + vodt.getErrCode() + "," + vodt.getErrDescription());
                    ZBRGraphicsDll.closeGraphics(vo.getCodex());
                    return false;
                }
                logger.info("文字 -部门 drawText successful!");
            }
            
            //文字 - 员工号
            if(!printVo.getIdentityId().isEmpty() &&
                    data[0].equals(ConfigConstant.TEXT) && data[1].equals(ConfigConstant.EMPLOYEEID)){
                String id_temp = printVo.getIdentityId().trim();
                ResultVo vodt = ZBRGraphicsDll.drawTextRect(Integer.valueOf(data[7]),Integer.valueOf(data[8]), Integer.valueOf(data[9]), Integer.valueOf(data[10]),
                        Integer.valueOf(data[11]), appendSpace(id_temp), 
                        data[3], Integer.valueOf(data[4]), Integer.valueOf(data[5]), Integer.valueOf(data[6]));
                if(vodt.getResultCode() == 0){
                    logger.info("文字 -员工号 drawText Error,Error code:" + vodt.getErrCode() + "," + vodt.getErrDescription());
                    ZBRGraphicsDll.closeGraphics(vo.getCodex());
                    return false;
                }
                logger.info("文字 -员工号 drawText successful!");
            }
            
        
        }

        ResultVo vopg = ZBRGraphicsDll.printGraphics(vo.getCodex());
        if(vopg.getResultCode() == 0){
            logger.info("printGraphics Error,Error code:" + vopg.getErrCode() + "," + vopg.getErrDescription());
            ZBRGraphicsDll.closeGraphics(vo.getCodex());
            return false;
        }
        logger.info("printGraphics successful!");

        ZBRGraphicsDll.closeGraphics(vo.getCodex());
        return true;
    }
    
    
    /**
     * 检测打印机
     */
    public static String checkPrinter(){
        String driverName = ConfigUtil.getConfigValue(ConfigConstant.PrinterTag, ConfigConstant.PrinterDriverTag);
        ZBRPrinterDll.loadZBRPrinterDll();
        
        ResultVo vogh = ZBRPrinterDll.getHandle(driverName);
        if(vogh.getResultCode() == 0){
            logger.info("getHandle Error,Error code:" + vogh.getErrCode() + "," + vogh.getErrDescription());
        }
        int prType = vogh.getCodex();
        
        ResultVo vomp = ZBRPrinterDll.checkForErrors(Integer.valueOf(vogh.getContent()),prType);
        
        ZBRPrinterDll.closeHandle(Integer.valueOf(vogh.getContent()));
        
        return String.valueOf(vomp.getResultCode());
    }
    
    
    /**
     * 自校打印机
     */
    public static String selAdjustPrinter(){
        String driverName = ConfigUtil.getConfigValue(ConfigConstant.PrinterTag, ConfigConstant.PrinterDriverTag);
        ZBRPrinterDll.loadZBRPrinterDll();
        
        ResultVo vogh = ZBRPrinterDll.getHandle(driverName);
        if(vogh.getResultCode() == 0){
            logger.info("getHandle Error,Error code:" + vogh.getErrCode() + "," + vogh.getErrDescription());
        }
        int prType = vogh.getCodex();
        
        ResultVo vomp = ZBRPrinterDll.selfAdj(Integer.valueOf(vogh.getContent()),prType);
        
        ZBRPrinterDll.closeHandle(Integer.valueOf(vogh.getContent()));
        
        return String.valueOf(vomp.getResultCode());
    }

    
    /**
     * 重置打印机
     * @return 
     */
    public static boolean reset() {
        String driverName = ConfigUtil.getConfigValue(ConfigConstant.PrinterTag, ConfigConstant.PrinterDriverTag);
        
        ZBRPrinterDll.loadZBRPrinterDll();
        ResultVo vogh = ZBRPrinterDll.getHandle(driverName);
        if(vogh.getResultCode() == 0){
            logger.info("getHandle Error,Error code:" + vogh.getErrCode() + "," + vogh.getErrDescription());
            return false;
        }
        int prType = vogh.getCodex();
        
        ResultVo vomp = ZBRPrinterDll.resetPrinter(Integer.valueOf(vogh.getContent()),prType);
        if(vomp.getResultCode() == 0){
            logger.info("resetPrinter Error,Error code:" + vomp.getErrCode());
            ZBRPrinterDll.closeHandle(Integer.valueOf(vogh.getContent()));
            return false;
        }
        
        ZBRPrinterDll.closeHandle(Integer.valueOf(vogh.getContent()));
        return true;
    }
    
    
    /**
     * 查询色带剩余量
     * @return 
     */
    public static ResultVo getPanelsRemaining() {
        String driverName = ConfigUtil.getConfigValue(ConfigConstant.PrinterTag, ConfigConstant.PrinterDriverTag);
        
        ZBRPrinterDll.loadZBRPrinterDll();
        ResultVo vogh = ZBRPrinterDll.getHandle(driverName);
        if(vogh.getResultCode() == 0){
            logger.info("getHandle Error,Error code:" + vogh.getErrCode() + "," + vogh.getErrDescription());
            return vogh;
        }
        int prType = vogh.getCodex();
        
        ResultVo vomp = ZBRPrinterDll.getPanelsRemaining(Integer.valueOf(vogh.getContent()),prType);
        if(vomp.getResultCode() == 0){
            logger.info("getPanelsRemaining Error,Error code:" + vomp.getErrCode() + "," + vomp.getErrDescription());
            ZBRPrinterDll.closeHandle(Integer.valueOf(vogh.getContent()));
        }
        
        ZBRPrinterDll.closeHandle(Integer.valueOf(vogh.getContent()));
        return vomp;
    }
    
    /**
     * 走卡
     * @return 
     */
    public static boolean move() {
        String driverName = ConfigUtil.getConfigValue(ConfigConstant.PrinterTag, ConfigConstant.PrinterDriverTag);
        String printStep = ConfigUtil.getConfigValue(ConfigConstant.PrinterTag, ConfigConstant.PrinterStepTag);
        
        ZBRPrinterDll.loadZBRPrinterDll();
        ResultVo vogh = ZBRPrinterDll.getHandle(driverName);
        if(vogh.getResultCode() == 0){
            logger.info("getHandle Error,Error code:" + vogh.getErrCode() + "," + vogh.getErrDescription());
            return false;
        }
        int prType = vogh.getCodex();
        
        ResultVo vomp = ZBRPrinterDll.movePrintReady(Integer.valueOf(vogh.getContent()),prType);
        if(vomp.getResultCode() == 0){
            logger.info("movePrintReady Error,Error code:" + vomp.getErrCode() + "," + vomp.getErrDescription());
            ZBRPrinterDll.closeHandle(Integer.valueOf(vogh.getContent()));
            return false;
        }
        
        ResultVo vomc = ZBRPrinterDll.moveCard(Integer.valueOf(vogh.getContent()),prType,Integer.valueOf(printStep));
        if(vomc.getResultCode() == 0){
            logger.info("moveCard Error,Error code:" + vomc.getErrCode() + "," + vomc.getErrDescription());
            ZBRPrinterDll.closeHandle(Integer.valueOf(vogh.getContent()));
            return false;
        }
        
        ZBRPrinterDll.closeHandle(Integer.valueOf(vogh.getContent()));
        return true;
    }
    
    /**
     * 出卡
     * @return 
     */
    public static boolean out() {
        String driverName = ConfigUtil.getConfigValue(ConfigConstant.PrinterTag, ConfigConstant.PrinterDriverTag);
        
        ZBRPrinterDll.loadZBRPrinterDll();
        ResultVo vogh = ZBRPrinterDll.getHandle(driverName);
        if(vogh.getResultCode() == 0){
            logger.info("getHandle Error,Error code:" + vogh.getErrCode() + "," + vogh.getErrDescription());
            return false;
        }
        int prType = vogh.getCodex();
        
        ResultVo vomp = ZBRPrinterDll.movePrintReady(Integer.valueOf(vogh.getContent()),prType);
        if(vomp.getResultCode() == 0){
            logger.info("movePrintReady Error,Error code:" + vomp.getErrCode() + "," + vomp.getErrDescription());
            ZBRPrinterDll.closeHandle(Integer.valueOf(vogh.getContent()));
            return false;
        }
        
        ResultVo voec = ZBRPrinterDll.ejectCard(Integer.valueOf(vogh.getContent()),prType);
        if(voec.getResultCode() == 0){
            logger.info("ejectCard Error,Error code:" + voec.getErrCode() + "," + voec.getErrDescription());
            ZBRPrinterDll.closeHandle(Integer.valueOf(vogh.getContent()));
            return false;
        }
        
        ZBRPrinterDll.closeHandle(Integer.valueOf(vogh.getContent()));
        return true;
    }

    /**
     * 卡后退
     * @return 
     */
    public static boolean back() {
        String driverName = ConfigUtil.getConfigValue(ConfigConstant.PrinterTag, ConfigConstant.PrinterDriverTag);
        String printStep = ConfigUtil.getConfigValue(ConfigConstant.PrinterTag, ConfigConstant.PrinterStepTag);
        
        ZBRPrinterDll.loadZBRPrinterDll();
        ResultVo vogh = ZBRPrinterDll.getHandle(driverName);
        if(vogh.getResultCode() == 0){
            logger.info("getHandle Error,Error code:" + vogh.getErrCode() + "," + vogh.getErrDescription());
            return false;
        }
        int prType = vogh.getCodex();
        
        ResultVo vomp = ZBRPrinterDll.movePrintReady(Integer.valueOf(vogh.getContent()),prType);
        if(vomp.getResultCode() == 0){
            logger.info("movePrintReady Error,Error code:" + vomp.getErrCode() + "," + vomp.getErrDescription());
            ZBRPrinterDll.closeHandle(Integer.valueOf(vogh.getContent()));
            return false;
        }
        
        ResultVo vocb = ZBRPrinterDll.moveCardBkwd(Integer.valueOf(vogh.getContent()),prType,Integer.valueOf(printStep));
        if(vocb.getResultCode() == 0){
            logger.info("moveCardBkwd Error,Error code:" + vocb.getErrCode() + "," + vocb.getErrDescription());
            ZBRPrinterDll.closeHandle(Integer.valueOf(vogh.getContent()));
            return false;
        }
        
        ZBRPrinterDll.closeHandle(Integer.valueOf(vogh.getContent()));
        return true;
    }
    
    public static int getHandle(){
        String driverName = ConfigUtil.getConfigValue(ConfigConstant.PrinterTag, ConfigConstant.PrinterDriverTag);
        ResultVo vo = new ResultVo();
        
        ZBRPrinterDll.loadZBRPrinterDll();
        vo = ZBRPrinterDll.getHandle(driverName);
        if(vo.getResultCode() == 0){
            logger.info("打开句柄失败，错误代码" + vo.getErrCode() + "," + vo.getErrDescription());
        }
        return Integer.valueOf(vo.getContent());
    }
    
    public static ResultVo closeHandle(int handle){
        ResultVo vo = new ResultVo();
        
        ZBRPrinterDll.loadZBRPrinterDll();
        vo = ZBRPrinterDll.closeHandle(handle);
        return vo;
    }
    
    
    /********************************* 调试方法开始 ****************************************/
    
    public static void main(String[] args) throws Exception {
//        printTest();
        PrinterJob pj = PrinterJob.getPrinterJob();
        PageFormat pf = pj.defaultPage();
        pf.setOrientation(PageFormat.PORTRAIT);
        pf.getOrientation();
//        graphicsTest();
    }
    
    public static void printTest(){
        String driverName = "Zebra ZXP Series 3C USB Card Printer";
        ResultVo vo = ZBRPrinterDll.getHandle(driverName);
//        ResultVo vuoC = ZBRPrinterDll.getPanelsPrinted(Integer.valueOf(vo.getContent()), vo.getCodex());//2448
        ResultVo vu6 = ZBRPrinterDll.chkDueForCleaning(Integer.valueOf(vo.getContent()), vo.getCodex());//"2448.5000.5"
//        ResultVo vu7 = ZBRPrinterDll.setCleaningParam(Integer.valueOf(vo.getContent()), vo.getCodex() , 10000, 5);//92
//        ResultVo vu7 = ZBRPrinterDll.getPanelsRemaining(Integer.valueOf(vo.getContent()), vo.getCodex());//92
//        ResultVo vuo4 = ZBRPrinterDll.getPrintCount(Integer.valueOf(vo.getContent()), vo.getCodex());//"588"
//        ResultVo vob = ZBRPrinterDll.selfAdj(Integer.valueOf(vo.getContent()), vo.getCodex());
//        ResultVo voC = ZBRPrinterDll.resetPrinter(Integer.valueOf(vo.getContent()), vo.getCodex());
//        ResultVo vuX = ZBRPrinterDll.chkDueForCleaning(Integer.valueOf(vo.getContent()), vo.getCodex());//"2448.5000.5"
//        ResultVo voh = ZBRPrinterDll.movePrintReady(Integer.valueOf(vo.getContent()), vo.getCodex());
//        ResultVo vob = ZBRPrinterDll.clearErrStatusLn(Integer.valueOf(vo.getContent()), vo.getCodex());
//        ResultVo voy = ZBRPrinterDll.getSensorStatus(Integer.valueOf(vo.getContent()), vo.getCodex());
//        ZBRPrinterDll.moveCard(Integer.valueOf(vo.getContent()), vo.getCodex(),1500);
//        ZBRPrinterDll.moveCardBkwd(Integer.valueOf(vo.getContent()), vo.getCodex(),1800);
//        ResultVo voC = ZBRPrinterDll.movePrintReady(Integer.valueOf(vo.getContent()), vo.getCodex());
//        ZBRPrinterDll.ejectCard(Integer.valueOf(vo.getContent()), vo.getCodex());
        ZBRPrinterDll.closeHandle(Integer.valueOf(vo.getContent()));
//        System.out.println("------" + vu7.getCount());
    }
    
    public static boolean graphicsTest() {
        
        String driverName = "Zebra ZXP Series 3C USB Card Printer";
        
        ZBRGraphicsDll.loadZBRGraphicsDll();
        
        ZBRGraphicsDll.clearGraphics();
        
        ResultVo vo = ZBRGraphicsDll.initGraphics(driverName);
        if(vo.getResultCode() == 0){
            ZBRGraphicsDll.closeGraphics(vo.getCodex());
            return false;
        }
        
        ResultVo vov = ZBRGraphicsDll.isPrinterReady(driverName);
        
        String filename = "C:\\Users\\Administrator\\Pictures\\scp\\1\\201404099.jpg";
        File file = new File(filename);
        if(file.exists() && !file.isDirectory()){
            String dir = ImageUtil.jpg2bmp(filename);
            ResultVo vodi = ZBRGraphicsDll.drawImageRect(dir, 737, 59, 228, 315);
            if(vodi.getResultCode() == 0){
//                ZBRGraphicsDll.closeGraphics(vo.getCodex());
//                return false;
            }
        }
        
            ResultVo vodt = ZBRGraphicsDll.drawTextRect(735, 385, 220, 50, 4, "小明笔仍创誓记", "Arial", 8, 0x01, 0x000000);
            if(vodt.getResultCode() == 0){
                ZBRGraphicsDll.closeGraphics(vo.getCodex());
                return false;
            }
//            ResultVo vodt = ZBRGraphicsDll.drawTextRect(70, 140, 420, 50, 5, "430524195000000000", "Arial", 8, 0x01, 0x000000);
//            if(vodt.getResultCode() == 0){
//                ZBRGraphicsDll.closeGraphics(vo.getCodex());
//                return false;
//            }

        ResultVo vopg = ZBRGraphicsDll.printGraphics(vo.getCodex());
        if(vopg.getResultCode() == 0){
            ZBRGraphicsDll.closeGraphics(vo.getCodex());
            return false;
        }

        ZBRGraphicsDll.closeGraphics(vo.getCodex());
        return true;
    }
    
    /*
     * 打印机纵向或横向判断
     */
    private static ImageIcon printerSet(String[] data){
        
        String path = System.getProperty("user.dir");
        path += "/" + ConfigConstant.PRINT_BACKGROUND_PIC_PATH;
        path += data[1];
        
        PrinterJob pj = PrinterJob.getPrinterJob();
        PageFormat pf = pj.defaultPage();
        
        if(pf.getOrientation() == PageFormat.PORTRAIT){
            
        }else{
            path = path.replace(".jpg", "_landscape.jpg");
        }
        
        ImageIcon tempIcon = new ImageIcon(path); 
        if(pf.getOrientation() == PageFormat.PORTRAIT){
            tempIcon.setImage(tempIcon.getImage().getScaledInstance(Integer.valueOf(data[2]), Integer.valueOf(data[3]), Image.SCALE_DEFAULT));
        }else{
            tempIcon.setImage(tempIcon.getImage().getScaledInstance(Integer.valueOf(data[3]), Integer.valueOf(data[2]), Image.SCALE_DEFAULT));
        }
        
        return tempIcon;
    }
    
    public static String appendSpace(String  para){  
        int length = para.length();  
        char[] value = new char[length << 1];  
        for (int i=0, j=0; i<length; ++i, j = i << 1) {  
            value[j] = para.charAt(i);  
            value[1 + j] = ' ';  
        }  
        String s = new String(value);  
        return s.substring(0,s.length()-1);
    }
    /**
     * 将中文括号替换为英文括号
     * @param text
     * @return 
     */
    public static String formatNameStr(String text){
        String nameStr = text;
        nameStr = nameStr.replaceAll("（", "(");
        nameStr = nameStr.replaceAll("）", ")");
        return nameStr;
    }
    /**
     * 计算字符串的中文长度
     * @param text
     * @return 
     */
    public static int count(String text) {
     String Reg="^[\u4e00-\u9fa5]{1}$";//正则
     String dotReg = "\\.";
     String dotRegCN = "\\·";
     String bracketLeftReg = "\\(";
     String bracketRightReg = "\\)";
     int result=0;
     for(int i=0;i<text.length();i++){
        String b=Character.toString(text.charAt(i));
        if(b.matches(Reg))result++;
        if(b.matches(dotReg)) result++;
        if(b.matches(dotRegCN)) result++;
        if(b.matches(bracketLeftReg)) result++;
        if(b.matches(bracketRightReg)) result++;
     }
     return result;
   }
}
