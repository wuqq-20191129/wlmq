/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.escommu.thread;

import com.goldsign.escommu.env.AppConstant;
import com.goldsign.escommu.dao.FindFileDao;
import com.goldsign.escommu.message.ConstructMessage50;
import com.goldsign.escommu.util.FileUtil;
import com.goldsign.escommu.vo.FindFileVo;
import java.util.Vector;
import org.apache.log4j.Logger;

/**
 *
 * @author Administrator
 */
public class ComFindFileThread extends Thread {

    private static Logger logger = Logger.getLogger(ComFindFileThread.class.getName());

    /**
     * 运行
     * 
     */
    public void run() {

        while (true) {
            try {
                this.ftpGetFiles();
                this.threadSleep();
            } catch (Exception e) {
                logger.error("文件获取线程错误-" + e);
            }
        }

    }

    /**
     * FTP取文件
     * 
     * @throws Exception 
     */
    private void ftpGetFiles() throws Exception {
        Vector files = this.getFiles();
        if (files.isEmpty()) {
            return;
        }
        ConstructMessage50 cm = new ConstructMessage50();
        FileUtil fileUtil = new FileUtil();
        FindFileVo vo;
        for (int i = 0; i < files.size(); i++) {
            vo = (FindFileVo) files.get(i);
            fileUtil.checkFile(vo);//文件状态是否非法
            this.isLeagalDev(vo);//是否合法设备
            if (vo.getStatus()==AppConstant.FTP_STATUS_ILLEGAL) {
                this.updateFileStatus(vo);//更新文件状态为非法文件
                continue;
            }
            this.setIp(vo);
            cm.constructAndSend(vo.getIp(), vo.getFileName());//构造消息，发送到消息处理队列
        }

    }

    /**
     * 是否合法设备
     * @param vo
     * @return 
     */
    private void isLeagalDev(FindFileVo vo) {
        if (!AppConstant.devIps.containsKey(vo.getDeviceId())) {
            vo.setRemark("设备ID:" + vo.getDeviceId() + "非法");
            vo.setStatus(AppConstant.FTP_STATUS_ILLEGAL);
        }
    }

    /**
     * 设置IP
     * 
     * @param vo
     * @throws Exception 
     */
    private void setIp(FindFileVo vo) throws Exception {
        String devId = vo.getDeviceId();
        String ip = (String) AppConstant.devIps.get(devId);
        vo.setIp(ip);
    }

    /**
     * 更新文件状态
     * 
     * @param vo 
     */
    private void updateFileStatus(FindFileVo vo) throws Exception {
        FindFileDao dao = new FindFileDao();
        dao.updateFileStatus(vo);
    }

    /**
     * 取得文件
     * 
     * @return 
     */
    private Vector getFiles() throws Exception {
        FindFileDao dao = new FindFileDao();
        Vector files = dao.getFiles();
        return files;

    }

    /**
     * 休息
     * 
     */
    private void threadSleep() {

        try {
            this.sleep(AppConstant.IntervalThreadFindFile);
        } catch (NumberFormatException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}
