/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.frame.util;

import org.apache.log4j.Logger;

/**
 *
 * @author hejj
 */
public class LoggerUtil {

    public static void loggerLineForSectFirst(Logger logger, String info) {
        loggerLineSep(logger);
        logger.info(info);

    }
     public static void loggerLineForSectFirstEmp(Logger logger, String info) {
        loggerLineSepEmp(logger);
        logger.info(info);

    }
      public static void loggerLineForSectLastEmp(Logger logger, String info) {
        logger.info(info);
        loggerLineSepEmp(logger);
        logger.info("\n");


    }

    public static void loggerLineForSectLast(Logger logger, String info) {
        logger.info(info);
        loggerLineSep(logger);
        logger.info("\n");


    }

    public static void loggerLineForSectAll(Logger logger, String info) {
        loggerLineSep(logger);
        logger.info(info);
        loggerLineSep(logger);
        logger.info("\n");


    }
    public static void loggerLineForSectAllEmp(Logger logger, String info) {
        loggerLineSepEmp(logger);
        logger.info(info);
        loggerLineSepEmp(logger);
        logger.info("\n");


    }

    public static void loggerLineForSectMid(Logger logger, String info) {
        loggerLine(logger, info);
    }

    public static void loggerLine(Logger logger, String info) {
        logger.info(info);
    }

    public static void loggerLineSep(Logger logger) {
        logger.info("======================================================================");
    }
    public static void loggerLineSepEmp(Logger logger) {
        logger.info("**********************************************************************");
    }
     public static String getBalanceWaterInfo(String balanceWaterNo,int balanceWaterNoSub) {
         return "清算流水号：" + balanceWaterNo +" 子流水："+balanceWaterNoSub+" ";
         
     }
}
