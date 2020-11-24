package com.goldsign.systemmonitor.file;

import com.goldsign.frame.constant.FrameDBConstant;
import com.goldsign.systemmonitor.handler.HandlerCommandBase;
import com.goldsign.systemmonitor.util.FtpUtil;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
import java.util.Vector;
import org.apache.log4j.Logger;

public class FileAnalyzer {

    static Logger logger = Logger.getLogger(FileAnalyzer.class);
    public static boolean IS_FILE_HANDLED = true;

    public FileAnalyzer() {
        super();
    }

    public void parseFiles() throws Exception {
        //File dir = new File(FrameDBConstant.FtpLocalDir);
        File dir = new File(new FtpUtil().getLocalDirWithoutMakeDir());
        
        File[] files = dir.listFiles();
        if (files == null || files.length == 0) {
            logger.info("目录" + new FtpUtil().getLocalDir() + "没有需要分析的文件");
            return;
        }
        File f;
        for (int i = 0; i < files.length; i++) {
            f = files[i];
            if (f.isDirectory()) {
                continue;
            }
            this.parseFile(f.getName());
        }
    }

    private String getPathFile(String fileName) {

        return new FtpUtil().getLocalDir() + "/" + fileName;
        //return new FtpUtil().getLocalDirWithoutMakeDir() + "/" + fileName;
    }

    private String getCommand(String line) {
        String command = line.trim();
        command = line.substring(FrameDBConstant.FileLineCommandStart.length() + 1);

        return command.trim();
    }

    public String getHanderName(String command) {
        String start = command.substring(0, 1).toUpperCase();
        command = start + command.substring(1);
        return FrameDBConstant.FileHandlerClassPrix + command;


    }

    public void handleCommand(String command, Vector lines, String fileName) {
        HandlerCommandBase handler;
        try {
            String start = command.substring(0, 1).toUpperCase();
            command = start + command.substring(1);
            command = "com.goldsign.systemmonitor.handler.HandlerCommand" + command;
            // handler = (HandlerCommandBase) Class.forName(this.getHanderName(command)).newInstance();
            handler = (HandlerCommandBase) Class.forName(command).newInstance();
            handler.handleCommand(command, lines, fileName);
        } catch (InstantiationException e) {

            e.printStackTrace();
        } catch (IllegalAccessException e) {

            e.printStackTrace();
        } catch (ClassNotFoundException e) {

            e.printStackTrace();
        }

    }

    public void parseFile(String fileName) throws Exception {
        FileAnalyzer.IS_FILE_HANDLED = false;
        File f = new File(this.getPathFile(fileName));
        FileInputStream fis = new FileInputStream(f);

        String line = null;



        InputStreamReader isr = null;
        BufferedReader br = null;
        boolean isNeed = false;
        String command = "";
        Vector lines = null;
        try {
            isr = new InputStreamReader(fis, "GBK");
            br = new BufferedReader(isr);
            logger.info("分析文件：" + fileName);
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.length() == 0) {
                    continue;
                }
                if (fileName.equals("M_11_20150826.zhj")) {
                    logger.info(line);
                }
                if (line.indexOf(FrameDBConstant.FileLineCommandStart) != -1) {
                    isNeed = true;
                    command = this.getCommand(line);
                    lines = new Vector();
                    continue;
                }
                if (line.indexOf(FrameDBConstant.FileLineCommandEnd) != -1) {
                    isNeed = false;
                    this.handleCommand(command, lines, fileName);
                    if (FileAnalyzer.IS_FILE_HANDLED) {
                        logger.info("旧文件：" + fileName);
                        break;
                    }
                    continue;
                }
                if (isNeed) {
                    lines.add(line);
                }
            }
        } catch (Exception e) {
            throw e;
        } finally {
            this.closeFile(fis, isr, br);
        }
    }

    private void closeFile(FileInputStream fis, InputStreamReader isr, BufferedReader br) {
        try {
            if (fis != null) {
                fis.close();
            }
            if (isr != null);
            isr.close();
            if (br != null) {
                br.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
