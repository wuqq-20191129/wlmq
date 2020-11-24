/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.ecpmcs.dll;

import com.goldsign.ecpmcs.vo.ResultVo;
import org.apache.log4j.Logger;
import org.xvolks.jnative.JNative;
import org.xvolks.jnative.Type;
import org.xvolks.jnative.pointers.Pointer;
import org.xvolks.jnative.pointers.memory.MemoryBlockFactory;

/**
 * 图形DLL接口
 * @author lindq
 * 
 * Return Value: 1 successful
 *               0 failed
 */
public class ZBRGraphicsDll {
    
    private static final Logger logger = Logger.getLogger(ZBRPrinterDll.class.getName());
    
    private final static String dllPath = "\\ZBRGraphics.dll"; // 需要调用dll的路径
    private final static String dllName = "ZBRGraphics.dll";  // 需要调用的dll
    
    /**
     * 加载动态库
     */
    public static void loadZBRGraphicsDll() {
        String readDllPath = System.getProperty("user.dir");
        readDllPath += dllPath;
        System.load(readDllPath);
        logger.debug(dllName + "DLL加载成功！");
    }
    
    /**
     * 按坐标将图片写入Graphics缓存
     * @param img bmp图片路径
     * @param x 
     * @param y
     * @param width
     * @param height
     * @return 
     */
    public static ResultVo drawImageRect(String img,int x,int y,int width,int height) {
        
        JNative n = null;
        ResultVo vo = new ResultVo();
        try {
            n = new JNative(dllName, "ZBRGDIDrawImageRect");
            n.setRetVal(Type.INT);

            Pointer err = new Pointer(MemoryBlockFactory.createMemoryBlock(10));
            Pointer imgByte = new Pointer(MemoryBlockFactory.createMemoryBlock(512));
            
            int i = 0;
//            n.setParameter(i++, Type.STRING, img);
            imgByte.setMemory(img.getBytes("GB18030"));
            n.setParameter(i++, imgByte);
            n.setParameter(i++, x);
            n.setParameter(i++, y);
            n.setParameter(i++, width);
            n.setParameter(i++, height);
            n.setParameter(i++, err);
            
            n.invoke();
            
            vo.setResultCode(Integer.valueOf(n.getRetVal()));
            vo.setErrCode(err.getAsInt(0));
            logger.debug("ZBRGDIDrawImageRect:" + n.getRetVal() + "." + err.getAsInt(0));
        }catch (Exception ex) {
            ex.printStackTrace();
            vo.setResultCode(-2);
            vo.setExceptionString("Print DLL ZBRGDIDrawImageRect Exception!!");
            return vo;
        } finally {
            if (n != null) {
                try {
                    n.dispose();
                } catch (Exception ex) {
                    ex.printStackTrace();
                } 
            }
        }
        
        return vo;
    }

    /**
     * 将文字写入Graphics缓存
     */
    public static ResultVo drawTextRect(int x, int y, int width, int height, int alignment,
            String text, String font, int fontSize, int fontStyle, int color) {
        JNative n = null;
        ResultVo vo = new ResultVo();
        try {
            n = new JNative(dllName, "ZBRGDIDrawTextRect");
            n.setRetVal(Type.INT);

            Pointer err = new Pointer(MemoryBlockFactory.createMemoryBlock(10));
            Pointer textByte = new Pointer(MemoryBlockFactory.createMemoryBlock(512));
            
            int i = 0;
            n.setParameter(i++, x);
            n.setParameter(i++, y);
            n.setParameter(i++, width);
            n.setParameter(i++, height);
            n.setParameter(i++, alignment);
            textByte.setMemory(text.getBytes("GB18030"));
            n.setParameter(i++, textByte);
            n.setParameter(i++, Type.STRING, font);
            n.setParameter(i++, fontSize);
            n.setParameter(i++, fontStyle);
            n.setParameter(i++, color);
            n.setParameter(i++, err);
            
            n.invoke();
            
            vo.setResultCode(Integer.valueOf(n.getRetVal()));
            vo.setErrCode(err.getAsInt(0));
            logger.debug("ZBRGDIDrawTextRect:" + n.getRetVal() + "." + err.getAsInt(0));
        }catch (Exception ex) {
            ex.printStackTrace();
            vo.setResultCode(-2);
            vo.setExceptionString("Print DLL ZBRGDIDrawTextRect Exception!!");
            return vo;
        } finally {
            if (n != null) {
                try {
                    n.dispose();
                } catch (Exception ex) {
                    ex.printStackTrace();
                } 
            }
        }
        
        return vo;
    }
    
    /**
     * 打印Graphics缓存数据
     * @param hDC
     * @return 
     */
    public static ResultVo printGraphics(int hDC) {
        JNative n = null;
        ResultVo vo = new ResultVo();
        try {
            n = new JNative(dllName, "ZBRGDIPrintGraphics");
            n.setRetVal(Type.INT);

            Pointer err = new Pointer(MemoryBlockFactory.createMemoryBlock(10));
            
            int i = 0;
            n.setParameter(i++, hDC);
            n.setParameter(i++, err);
            
            n.invoke();
            
            vo.setResultCode(Integer.valueOf(n.getRetVal()));
            vo.setErrCode(err.getAsInt(0));
            logger.debug("ZBRGDIPrintGraphics:" + n.getRetVal() + "." + err.getAsInt(0));
        }catch (Exception ex) {
            ex.printStackTrace();
            vo.setResultCode(-2);
            vo.setExceptionString("Print DLL ZBRGDIPrintGraphics Exception!!");
            return vo;
        } finally {
            if (n != null) {
                try {
                    n.dispose();
                } catch (Exception ex) {
                    ex.printStackTrace();
                } 
            }
        }
        
        return vo;
    }
    
    /**
     * 清除Graphics缴存内容
     * @return 
     */
    public static ResultVo clearGraphics() {
        JNative n = null;
        ResultVo vo = new ResultVo();
        try {
            n = new JNative(dllName, "ZBRGDIClearGraphics");
            n.setRetVal(Type.INT);

            Pointer err = new Pointer(MemoryBlockFactory.createMemoryBlock(10));
            n.setParameter(0, err);
            
            n.invoke();
            
            vo.setResultCode(Integer.valueOf(n.getRetVal()));
            vo.setErrCode(err.getAsInt(0));
            logger.debug("ZBRGDIClearGraphics:" + n.getRetVal() + "." + err.getAsInt(0));
        }catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (n != null) {
                try {
                    n.dispose();
                } catch (Exception ex) {
                    ex.printStackTrace();
                } 
            }
        }
        
        return vo;
    }
    
    /**
     * 关闭Graphics
     * @param hDC
     * @return 
     */
    public static ResultVo closeGraphics(int hDC) {
        JNative n = null;
        ResultVo vo = new ResultVo();
        try {
            n = new JNative(dllName, "ZBRGDICloseGraphics");
            n.setRetVal(Type.INT);

            Pointer err = new Pointer(MemoryBlockFactory.createMemoryBlock(10));
            
            int i = 0;
            n.setParameter(i++, hDC);
            n.setParameter(i++, err);
            
            n.invoke();
            
            vo.setResultCode(Integer.valueOf(n.getRetVal()));
            vo.setErrCode(err.getAsInt(0));
            logger.debug("ZBRGDICloseGraphics:" + n.getRetVal() + "." + err.getAsInt(0));
        }catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (n != null) {
                try {
                    n.dispose();
                } catch (Exception ex) {
                    ex.printStackTrace();
                } 
            }
        }
        
        return vo;
    }
    
    /**
     * 初始化
     * @param driverName
     * @return 
     */
    public static ResultVo initGraphics(String driverName) {
        JNative n = null;
        ResultVo vo = new ResultVo();
        try {
            n = new JNative(dllName, "ZBRGDIInitGraphics");
            n.setRetVal(Type.INT);

            Pointer hDC = new Pointer(MemoryBlockFactory.createMemoryBlock(256));
            Pointer err = new Pointer(MemoryBlockFactory.createMemoryBlock(10));
            
            int i = 0;
            n.setParameter(i++, Type.STRING, driverName);
            n.setParameter(i++, hDC);
            n.setParameter(i++, err);
            
            n.invoke();
            
            vo.setResultCode(Integer.valueOf(n.getRetVal()));
            vo.setCodex(hDC.getAsInt(0));
            vo.setErrCode(err.getAsInt(0));
            logger.debug("ZBRGDIInitGraphics:" + n.getRetVal() + "." + hDC.getAsInt(0) + "." + err.getAsInt(0));
        }catch (Exception ex) {
            ex.printStackTrace();
            vo.setResultCode(-2);
            vo.setExceptionString("Print DLL ZBRGDIInitGraphics Exception!!");
            return vo;
        } finally {
            if (n != null) {
                try {
                    n.dispose();
                } catch (Exception ex) {
                    ex.printStackTrace();
                } 
            }
        }
        
        return vo;
    }
    
    /**
     * 是否正在打印
     * @param driverName 打印机驱动名称
     * @return 1 Printer is ready
     *          0 Printer is currently executing a print job
     */
    public static ResultVo isPrinterReady(String driverName) {
        JNative n = null;
        ResultVo vo = new ResultVo();
        try {
            n = new JNative(dllName, "ZBRGDIIsPrinterReady");
            n.setRetVal(Type.INT);

            Pointer err = new Pointer(MemoryBlockFactory.createMemoryBlock(10));
            
            int i = 0;
            n.setParameter(i++, Type.STRING, driverName);
            n.setParameter(i++, err);
            
            n.invoke();
            
            vo.setResultCode(Integer.valueOf(n.getRetVal()));
            vo.setErrCode(err.getAsInt(0));
            logger.debug("ZBRGDIIsPrinterReady:" + n.getRetVal() + "." + err.getAsInt(0));
        }catch (Exception ex) {
            ex.printStackTrace();
            vo.setResultCode(-2);
            vo.setExceptionString("Print DLL ZBRGDIIsPrinterReady Exception!!");
            return vo;
        } finally {
            if (n != null) {
                try {
                    n.dispose();
                } catch (Exception ex) {
                    ex.printStackTrace();
                } 
            }
        }
        
        return vo;
    }
    
    /**
     * 取SDK版本号
     * @return 
     */
    public static String getSDKVer() {
        JNative n = null;
        String vString = null;
        try {
            n = new JNative(dllName, "ZBRGDIGetSDKVer");
            n.setRetVal(Type.INT);

            Pointer major = new Pointer(MemoryBlockFactory.createMemoryBlock(10));
            Pointer minor = new Pointer(MemoryBlockFactory.createMemoryBlock(10));
            Pointer engLevel = new Pointer(MemoryBlockFactory.createMemoryBlock(10));
            
            int i = 0;
            n.setParameter(i++, major);
            n.setParameter(i++, minor);
            n.setParameter(i++, engLevel);
            
            n.invoke();
            
            vString = major.getAsInt(0) + "." + minor.getAsInt(0) + "." + engLevel.getAsInt(0);
            logger.debug("ZBRGDIGetSDKVer:" + major.getAsInt(0) + "." + minor.getAsInt(0) + "." + engLevel.getAsInt(0));
        }catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (n != null) {
                try {
                    n.dispose();
                } catch (Exception ex) {
                    ex.printStackTrace();
                } 
            }
        }
        return vString;
    }
    
}
