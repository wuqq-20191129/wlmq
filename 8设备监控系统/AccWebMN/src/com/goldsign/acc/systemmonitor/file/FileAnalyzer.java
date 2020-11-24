package com.goldsign.acc.systemmonitor.file;

import com.goldsign.acc.frame.constant.FrameDBConstant;
import com.goldsign.acc.systemmonitor.handler.HandlerCommandBase;
import com.goldsign.acc.systemmonitor.handler.HandlerCommandCommlcc;
import com.goldsign.acc.systemmonitor.util.FtpUtil;
import org.apache.log4j.Logger;

import java.io.*;
import java.nio.channels.FileChannel;
import java.sql.SQLOutput;
import java.text.ParseException;
import java.util.Vector;


public class FileAnalyzer {

    static Logger logger = Logger.getLogger(FileAnalyzer.class);
    public static boolean IS_FILE_HANDLED = true;


    public FileAnalyzer() {
        super();
    }

    public void parseFiles() throws Exception {
        //File dir = new File(FrameDBConstant.FTP_LOCAL_DIRECTORY);
        System.out.println("*********come into parseFiles***********");
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

        return new FtpUtil().getLocalDir() + FrameDBConstant.COMMAND_SEPARATOR_SLASH + fileName;
        //return new FtpUtil().getLocalDirWithoutMakeDir() + "/" + fileName;
    }

    private String getCommand(String line) {
        String command = line.trim();
        command = line.substring(FrameDBConstant.FILE_LINE_COMMAND_START.length() + 1);

        return command.trim();
    }

    public String getHanderName(String command) {
        String start = command.substring(0, 1).toUpperCase();
        command = start + command.substring(1);
        return FrameDBConstant.FILE_HANDLER_CLASS_PREFIX + command;


    }

    public void handleCommand(String command, Vector lines, String fileName) {
        HandlerCommandBase handler;
        try {
            //lv_mirror_state为disk state更加详细的描述，弃用文件中disk state信息
//            if(IGNORE_COMMAND_DISK_STATE.equals(command)){
//                return ;
//            }
            String start = command.substring(0, 1).toUpperCase();
            command = start + command.substring(1);
            command = FrameDBConstant.FILE_HANDLER_CLASS_PREFIX + command;
            handler = (HandlerCommandBase) Class.forName(command).newInstance();
            handler.handleCommand(command, lines, fileName);
        } catch (InstantiationException e) {

            e.printStackTrace();
        } catch (IllegalAccessException e) {

            e.printStackTrace();
        } catch (ClassNotFoundException e) {

            e.printStackTrace();
        } catch (ParseException e) {
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
            isr = new InputStreamReader(fis, "UTF-8");
            br = new BufferedReader(isr);
            logger.info("分析文件：" + fileName);
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.length() == 0) {
                    continue;
                }
                if (line.indexOf(FrameDBConstant.FILE_LINE_COMMAND_START) != -1) {
                    isNeed = true;
                    command = this.getCommand(line);
                    lines = new Vector();
                    continue;
                }
                if (line.indexOf(FrameDBConstant.FILE_LINE_COMMAND_END) != -1) {
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
            if (isr != null) {
                isr.close();
            }
            if (br != null) {
                br.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @throws Exception
     */
    public void archivingFiles() throws Exception {
        System.out.println("*********come into archivingFiles***********");
        File archivingRoot = new File(FrameDBConstant.ARCHIVING_DIR_PATH);
        if (!archivingRoot.exists()) {
            archivingRoot.mkdir();
        }
        String archivingDir = FrameDBConstant.ARCHIVING_DIR_PATH + FrameDBConstant.COMMAND_SEPARATOR_SLASH + new FtpUtil().getArchivingDir();
        System.out.println("archivingDir = " + archivingDir);
        File dir = new File(archivingDir);
        String localDirPath = new FtpUtil().getLocalDir();
        if (!dir.exists()) {
            dir.mkdir();
            File localDir = new File(localDirPath);
            if (localDir.exists() && localDir.isDirectory()) {
                File[] files = localDir.listFiles();
                Vector fileNames = new Vector();
                File tmp;
                if (files != null && files.length != 0) {
                    for (int i = 0; i < files.length; i++) {
                        tmp = files[i];
                        if (tmp.isDirectory()) {
                            continue;
                        }
                        fileNames.add(tmp.getName());
                    }
                    for (int i = 0; i < fileNames.size(); i++) {
                        File sourceFile = new File(localDirPath + FrameDBConstant.COMMAND_SEPARATOR_SLASH + fileNames.get(i));
                        File targetFile = new File(archivingDir + FrameDBConstant.COMMAND_SEPARATOR_SLASH + fileNames.get(i));
                        this.copyFile(sourceFile, targetFile);
                    }
                }
            }
        } else {
            logger.info("目录存在，已归过档");
        }


    }

    private void copyFile(File sourceFile, File targetFile) throws IOException {
        FileChannel inputChannel = null;
        FileChannel outputChannel = null;
        try {
            inputChannel = new FileInputStream(sourceFile).getChannel();
            outputChannel = new FileOutputStream(targetFile).getChannel();
            outputChannel.transferFrom(inputChannel, 0, inputChannel.size());
        } catch (Exception e) {
            logger.info("=====归档失败");
            logger.info("源文件:" + sourceFile.getAbsolutePath());
            logger.info("目标文件：" + targetFile.getAbsolutePath());
            logger.info("异常信息：" + e.getMessage());
        } finally {
            inputChannel.close();
            outputChannel.close();
        }
    }
}
