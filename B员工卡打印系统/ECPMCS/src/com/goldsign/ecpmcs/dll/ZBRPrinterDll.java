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
 * 打印机DLL
 * @author lindq
 */
public class ZBRPrinterDll{
    private static final Logger logger = Logger.getLogger(ZBRPrinterDll.class.getName());
    
    private final static String dllPath = "\\ZBRPrinter.dll"; // 需要调用dll的路径
    private final static String dllName = "ZBRPrinter.dll";  // 需要调用的dll

    /**
     * 加载动态库
     */
    public static void loadZBRPrinterDll() {
        String readDllPath = System.getProperty("user.dir");
        readDllPath += dllPath;
        System.load(readDllPath);
        logger.debug(dllName + "DLL加载成功！");
    }
    
    /**
     * 检测打印机错误状态
     * @param handle 句柄
     * @param type 打印机类型
     * @return 
     */
    public static ResultVo checkForErrors(int handle, int type) {
        JNative n = null;
        ResultVo vo = new ResultVo();
        try {
            n = new JNative(dllName, "ZBRPRNCheckForErrors");
            n.setRetVal(Type.INT);

            int i = 0;
            n.setParameter(i++, handle);
            n.setParameter(i++, type);
            
            n.invoke();
            
            vo.setResultCode(Integer.valueOf(n.getRetVal()));
            vo.setErrCode(Integer.valueOf(n.getRetVal()));
            logger.debug("ZBRPRNCheckForErrors:" + n.getRetVal());
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
     * 查询传感器状态
     * @param handle 句柄
     * @param type 打印机类型
     * @return 
     */
    public static ResultVo getSensorStatus(int handle, int type) {
        JNative n = null;
        ResultVo vo = new ResultVo();
        try {
            n = new JNative(dllName, "ZBRPRNGetSensorStatus");
            n.setRetVal(Type.INT);

            Pointer err = new Pointer(MemoryBlockFactory.createMemoryBlock(10));
            Pointer status = new Pointer(MemoryBlockFactory.createMemoryBlock(8));
            
            int i = 0;
            n.setParameter(i++, handle);
            n.setParameter(i++, type);
            n.setParameter(i++, status);
            n.setParameter(i++, err);
            
            n.invoke();
            
            vo.setResultCode(Integer.valueOf(n.getRetVal()));
            vo.setErrCode(err.getAsInt(0));
            status.getAsByte(2);
            logger.debug("ZBRPRNGetSensorStatus:" + n.getRetVal() + "." + err.getAsInt(0)
                     + "." + status.getAsByte(0)
                     + "." + status.getAsByte(1)
                     + "." + status.getAsByte(2)
                     + "." + status.getAsByte(3)
                     + "." + status.getAsByte(4)
                     + "." + status.getAsByte(5)
                     + "." + status.getAsByte(6)
                     + "." + status.getAsByte(7));
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
     * 设置自适应顺序
     * @param handle 句柄
     * @param type 打印机类型
     * @return 
     */
    public static ResultVo selfAdj(int handle, int type) {
        JNative n = null;
        ResultVo vo = new ResultVo();
        try {
            n = new JNative(dllName, "ZBRPRNSelfAdj");
            n.setRetVal(Type.INT);

            Pointer err = new Pointer(MemoryBlockFactory.createMemoryBlock(10));
            
            int i = 0;
            n.setParameter(i++, handle);
            n.setParameter(i++, type);
            n.setParameter(i++, err);
            
            n.invoke();
            
            vo.setResultCode(Integer.valueOf(n.getRetVal()));
            vo.setErrCode(err.getAsInt(0));
            logger.debug("ZBRPRNSelfAdj:" + n.getRetVal() + "." + err.getAsInt(0));
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
     * 查询清洗值
     * @param handle 句柄
     * @param type 打印机类型
     * @return 
     */
    public static ResultVo chkDueForCleaning(int handle, int type) {
        JNative n = null;
        ResultVo vo = new ResultVo();
        try {
            n = new JNative(dllName, "ZBRPRNChkDueForCleaning");
            n.setRetVal(Type.INT);

            Pointer imgCounter = new Pointer(MemoryBlockFactory.createMemoryBlock(20));
            Pointer cleanCounter = new Pointer(MemoryBlockFactory.createMemoryBlock(20));
            Pointer cleanCardCounter = new Pointer(MemoryBlockFactory.createMemoryBlock(20));
            Pointer err = new Pointer(MemoryBlockFactory.createMemoryBlock(10));
            
            int i = 0;
            n.setParameter(i++, handle);
            n.setParameter(i++, type);
            n.setParameter(i++, imgCounter);
            n.setParameter(i++, cleanCounter);
            n.setParameter(i++, cleanCardCounter);
            n.setParameter(i++, err);
            
            n.invoke();
            
            vo.setResultCode(Integer.valueOf(n.getRetVal()));
            vo.setErrCode(err.getAsInt(0));
            vo.setContent(imgCounter.getAsInt(0) + "." + cleanCounter.getAsInt(0) + "." + cleanCardCounter.getAsInt(0));
            logger.debug("ZBRPRNChkDueForCleaning:" + n.getRetVal() + "." + err.getAsInt(0)
                    + "." + imgCounter.getAsInt(0) + "." + cleanCounter.getAsInt(0) + "." + cleanCardCounter.getAsInt(0));
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
     * 设置清洗值限值
     * @param handle 句柄
     * @param type 打印机类型
     * @param ribbonPanelCounter 打印色带面板清洗值（默认5000）
     * @param cleanCardPass 清洗次数穿过打印机 (默认5)
     * @return 
     */
    public static ResultVo setCleaningParam(int handle, int type, int ribbonPanelCounter, int cleanCardPass) {
        JNative n = null;
        ResultVo vo = new ResultVo();
        try {
            n = new JNative(dllName, "ZBRPRNSetCleaningParam");
            n.setRetVal(Type.INT);

            Pointer err = new Pointer(MemoryBlockFactory.createMemoryBlock(10));
            
            int i = 0;
            n.setParameter(i++, handle);
            n.setParameter(i++, type);
            n.setParameter(i++, ribbonPanelCounter);
            n.setParameter(i++, cleanCardPass);
            n.setParameter(i++, err);
            
            n.invoke();
            
            vo.setResultCode(Integer.valueOf(n.getRetVal()));
            vo.setErrCode(err.getAsInt(0));
            logger.debug("ZBRPRNSetCleaningParam:" + n.getRetVal() + "." + err.getAsInt(0));
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
     * 查询色带剩余量
     * @param handle 句柄
     * @param type 打印机类型
     * @return 
     */
    public static ResultVo getPanelsRemaining(int handle, int type) {
        JNative n = null;
        ResultVo vo = new ResultVo();
        try {
            n = new JNative(dllName, "ZBRPRNGetPanelsRemaining");
            n.setRetVal(Type.INT);

            Pointer panels = new Pointer(MemoryBlockFactory.createMemoryBlock(20));
            Pointer err = new Pointer(MemoryBlockFactory.createMemoryBlock(10));
            
            int i = 0;
            n.setParameter(i++, handle);
            n.setParameter(i++, type);
            n.setParameter(i++, panels);
            n.setParameter(i++, err);
            
            n.invoke();
            
            vo.setResultCode(Integer.valueOf(n.getRetVal()));
            vo.setErrCode(err.getAsInt(0));
            vo.setCount(panels.getAsInt(0));
            logger.debug("ZBRPRNGetPanelsRemaining:" + n.getRetVal() + "." + err.getAsInt(0) + "." + panels.getAsInt(0));
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
     * 查询色带打印量
     * @param handle 句柄
     * @param type 打印机类型
     * @return 
     */
    public static ResultVo getPanelsPrinted(int handle, int type) {
        JNative n = null;
        ResultVo vo = new ResultVo();
        try {
            n = new JNative(dllName, "ZBRPRNGetPanelsPrinted");
            n.setRetVal(Type.INT);

            Pointer panels = new Pointer(MemoryBlockFactory.createMemoryBlock(20));
            Pointer err = new Pointer(MemoryBlockFactory.createMemoryBlock(10));
            
            int i = 0;
            n.setParameter(i++, handle);
            n.setParameter(i++, type);
            n.setParameter(i++, panels);
            n.setParameter(i++, err);
            
            n.invoke();
            
            vo.setResultCode(Integer.valueOf(n.getRetVal()));
            vo.setErrCode(err.getAsInt(0));
            vo.setCodex(panels.getAsInt(0));
            logger.debug("ZBRPRNGetPanelsPrinted:" + n.getRetVal() + "." + err.getAsInt(0) + "." + panels.getAsInt(0));
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
     * 清除错误
     * @param handle 句柄
     * @param type 打印机类型
     * @return 
     */
    public static ResultVo clearErrStatusLn(int handle, int type) {
        JNative n = null;
        ResultVo vo = new ResultVo();
        try {
            n = new JNative(dllName, "ZBRPRNClrErrStatusLn");
            n.setRetVal(Type.INT);

            Pointer err = new Pointer(MemoryBlockFactory.createMemoryBlock(10));
            
            int i = 0;
            n.setParameter(i++, handle);
            n.setParameter(i++, type);
            n.setParameter(i++, err);
            
            n.invoke();
            
            vo.setResultCode(Integer.valueOf(n.getRetVal()));
            vo.setErrCode(err.getAsInt(0));
            logger.debug("ZBRPRNClrErrStatusLn:" + n.getRetVal() + "." + err.getAsInt(0));
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
     * 查询打印机打印次数
     * @param handle 句柄
     * @param type 打印机类型
     * @return 
     */
    public static ResultVo getPrintCount(int handle, int type) {
        JNative n = null;
        ResultVo vo = new ResultVo();
        try {
            n = new JNative(dllName, "ZBRPRNGetPrintCount");
            n.setRetVal(Type.INT);

            Pointer err = new Pointer(MemoryBlockFactory.createMemoryBlock(10));
            Pointer count = new Pointer(MemoryBlockFactory.createMemoryBlock(20));
            
            int i = 0;
            n.setParameter(i++, handle);
            n.setParameter(i++, type);
            n.setParameter(i++, count);
            n.setParameter(i++, err);
            
            n.invoke();
            
            vo.setResultCode(Integer.valueOf(n.getRetVal()));
            vo.setErrCode(err.getAsInt(0));
            vo.setContent(String.valueOf(count.getAsInt(0)));
            logger.debug("ZBRPRNGetPrintCount:" + n.getRetVal() + "." + err.getAsInt(0) + "." + count.getAsInt(0));
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
     * 取打印机驱动句柄
     * @param driverName 打印机驱动名称
     * @return 
     */
    public static ResultVo getHandle(String driverName) {
        JNative n = null;
        ResultVo vo = new ResultVo();
        try {
            n = new JNative(dllName, "ZBRGetHandle");
            n.setRetVal(Type.INT);

            Pointer handle = new Pointer(MemoryBlockFactory.createMemoryBlock(20));
            Pointer type = new Pointer(MemoryBlockFactory.createMemoryBlock(20));
            Pointer err = new Pointer(MemoryBlockFactory.createMemoryBlock(10));
            
            int i = 0;
            n.setParameter(i++, handle);
            n.setParameter(i++, Type.STRING, driverName);
            n.setParameter(i++, type);
            n.setParameter(i++, err);
            
            n.invoke();
            
            vo.setResultCode(Integer.valueOf(n.getRetVal()));
            vo.setContent(String.valueOf(handle.getAsInt(0)));
            vo.setCodex(type.getAsInt(0));
            vo.setErrCode(err.getAsInt(0));
            logger.debug("ZBRGetHandle:" + n.getRetVal() + "." + handle.getAsInt(0) + "." + type.getAsInt(0) + "." + err.getAsInt(0));
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
     * 取打印机状态
     * @param handle 句柄
     * @param type 打印机类型
     * @return 
     */
    public static ResultVo getPrinterStatus(int handle, int type) {
        JNative n = null;
        ResultVo vo = new ResultVo();
        try {
            n = new JNative(dllName, "ZBRPRNGetPrinterStatus");
            n.setRetVal(Type.INT);

            int i = 0;
            n.setParameter(i++, handle);
            n.setParameter(i++, type);
            
            n.invoke();
            
            vo.setResultCode(Integer.valueOf(n.getRetVal()));
            logger.debug("ZBRPRNGetPrinterStatus:" + n.getRetVal());
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
     * 取打印状态
     * @param handle 句柄
     * @param type 打印机类型
     * @return 
     */
    public static ResultVo getPrintStatus(int handle, int type) {
        JNative n = null;
        ResultVo vo = new ResultVo();
        try {
            n = new JNative(dllName, "ZBRPRNGetPrintStatus");
            n.setRetVal(Type.INT);

            Pointer err = new Pointer(MemoryBlockFactory.createMemoryBlock(10));
            
            int i = 0;
            n.setParameter(i++, handle);
            n.setParameter(i++, type);
            n.setParameter(i++, err);
            
            n.invoke();
            
            vo.setResultCode(Integer.valueOf(n.getRetVal()));
            vo.setErrCode(err.getAsInt(0));
            logger.debug("ZBRPRNGetPrintStatus:" + n.getRetVal() + "." + err.getAsInt(0));
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
     * 走卡准备
     * @param handle 句柄
     * @param type 打印机类型
     * @return 
     */
    public static ResultVo movePrintReady(int handle, int type) {
        JNative n = null;
        ResultVo vo = new ResultVo();
        try {
            n = new JNative(dllName, "ZBRPRNMovePrintReady");
            n.setRetVal(Type.INT);

            Pointer err = new Pointer(MemoryBlockFactory.createMemoryBlock(10));
            
            int i = 0;
            n.setParameter(i++, handle);
            n.setParameter(i++, type);
            n.setParameter(i++, err);
            
            n.invoke();
            
            vo.setResultCode(Integer.valueOf(n.getRetVal()));
            vo.setErrCode(err.getAsInt(0));
            logger.debug("ZBRPRNMovePrintReady:" + n.getRetVal() + "." + err.getAsInt(0));
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
     * 将卡片移动到打印机发卡位置
     * @param handle 句柄
     * @param type 打印机类型
     * @return 
     */
    public static ResultVo reversePrintReady(int handle, int type) {
        JNative n = null;
        ResultVo vo = new ResultVo();
        try {
            n = new JNative(dllName, "ZBRPRNReversePrintReady");
            n.setRetVal(Type.INT);

            Pointer err = new Pointer(MemoryBlockFactory.createMemoryBlock(10));
            
            int i = 0;
            n.setParameter(i++, handle);
            n.setParameter(i++, type);
            n.setParameter(i++, err);
            
            n.invoke();
            
            vo.setResultCode(Integer.valueOf(n.getRetVal()));
            vo.setErrCode(err.getAsInt(0));
            logger.debug("ZBRPRNReversePrintReady:" + n.getRetVal() + "." + err.getAsInt(0));
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
     * 重置打印机
     * @param handle 句柄
     * @param type 打印机类型
     * @return 
     */
    public static ResultVo resetPrinter(int handle, int type) {
        JNative n = null;
        ResultVo vo = new ResultVo();
        try {
            n = new JNative(dllName, "ZBRPRNResetPrinter");
            n.setRetVal(Type.INT);

            Pointer err = new Pointer(MemoryBlockFactory.createMemoryBlock(10));
            
            int i = 0;
            n.setParameter(i++, handle);
            n.setParameter(i++, type);
            n.setParameter(i++, err);
            
            n.invoke();
            
            vo.setResultCode(Integer.valueOf(n.getRetVal()));
            vo.setErrCode(err.getAsInt(0));
            logger.debug("ZBRPRNResetPrinter:" + n.getRetVal() + "." + err.getAsInt(0));
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
     * 将卡片向后移动steps   100steps=8mm
     * @param handle 句柄
     * @param type 打印机类型
     * @param steps 步数
     * @return 
     */
    public static ResultVo moveCardBkwd(int handle, int type, int steps) {
        JNative n = null;
        ResultVo vo = new ResultVo();
        try {
            n = new JNative(dllName, "ZBRPRNMoveCardBkwd");
            n.setRetVal(Type.INT);

            Pointer err = new Pointer(MemoryBlockFactory.createMemoryBlock(10));
            
            int i = 0;
            n.setParameter(i++, handle);
            n.setParameter(i++, type);
            n.setParameter(i++, steps);
            n.setParameter(i++, err);
            
            n.invoke();
            
            vo.setResultCode(Integer.valueOf(n.getRetVal()));
            vo.setErrCode(err.getAsInt(0));
            logger.debug("ZBRPRNMoveCardBkwd:" + n.getRetVal() + "." + err.getAsInt(0));
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
     * 将卡片向前移动steps   100steps=8mm
     * @param handle 句柄
     * @param type 打印机类型
     * @param steps 步数
     * @return 
     */
    public static ResultVo moveCard(int handle, int type, int steps) {
        JNative n = null;
        ResultVo vo = new ResultVo();
        try {
            n = new JNative(dllName, "ZBRPRNMoveCard");
            n.setRetVal(Type.INT);

            Pointer err = new Pointer(MemoryBlockFactory.createMemoryBlock(10));
            
            int i = 0;
            n.setParameter(i++, handle);
            n.setParameter(i++, type);
            n.setParameter(i++, steps);
            n.setParameter(i++, err);
            
            n.invoke();
            
            vo.setResultCode(Integer.valueOf(n.getRetVal()));
            vo.setErrCode(err.getAsInt(0));
            logger.debug("ZBRPRNMoveCard:" + n.getRetVal() + "." + err.getAsInt(0));
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
     * 
     * @param handle 句柄
     * @param type 打印机类型
     * @return 
     */
    public static ResultVo flipCard(int handle, int type) {
        JNative n = null;
        ResultVo vo = new ResultVo();
        try {
            n = new JNative(dllName, "ZBRPRNFlipCard");
            n.setRetVal(Type.INT);

            Pointer err = new Pointer(MemoryBlockFactory.createMemoryBlock(10));
            
            int i = 0;
            n.setParameter(i++, handle);
            n.setParameter(i++, type);
            n.setParameter(i++, err);
            
            n.invoke();
            
            vo.setResultCode(Integer.valueOf(n.getRetVal()));
            vo.setErrCode(err.getAsInt(0));
            logger.debug("ZBRPRNFlipCard:" + n.getRetVal() + "." + err.getAsInt(0));
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
     * 出卡
     * @param handle 句柄
     * @param type 打印机类型
     * @return 
     */
    public static ResultVo ejectCard(int handle, int type) {
        JNative n = null;
        ResultVo vo = new ResultVo();
        try {
            n = new JNative(dllName, "ZBRPRNEjectCard");
            n.setRetVal(Type.INT);

            Pointer err = new Pointer(MemoryBlockFactory.createMemoryBlock(10));
            
            int i = 0;
            n.setParameter(i++, handle);
            n.setParameter(i++, type);
            n.setParameter(i++, err);
            
            n.invoke();
            
            vo.setResultCode(Integer.valueOf(n.getRetVal()));
            vo.setErrCode(err.getAsInt(0));
            logger.debug("ZBRPRNEjectCard:" + n.getRetVal() + "." + err.getAsInt(0));
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
     * 关闭打印机驱动句柄
     * @param handle 句柄
     * @return 
     */
    public static ResultVo closeHandle(int handle) {
        JNative n = null;
        ResultVo vo = new ResultVo();
        try {
            n = new JNative(dllName, "ZBRCloseHandle");
            n.setRetVal(Type.INT);

            Pointer err = new Pointer(MemoryBlockFactory.createMemoryBlock(10));
            
            int i = 0;
            n.setParameter(i++, handle);
            n.setParameter(i++, err);
            
            n.invoke();
            
            vo.setResultCode(Integer.valueOf(n.getRetVal()));
            vo.setErrCode(err.getAsInt(0));
            logger.debug("ZBRCloseHandle:" + n.getRetVal() + "." + err.getAsInt(0));
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
     * 取SDK版本号
     * @return 
     */
    public static String getSDKVer() {
        JNative n = null;
        String vString = null;
        try {
            n = new JNative(dllName, "ZBRPRNGetSDKVer");
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
            logger.debug("ZBRPRNGetSDKVer:" + major.getAsInt(0) + "." + minor.getAsInt(0) + "." + engLevel.getAsInt(0));
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
