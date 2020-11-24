package com.goldsign.acc.report.thread;


import org.apache.log4j.Logger;

import com.goldsign.acc.report.constant.AppConstant;
import com.goldsign.acc.report.dao.LogLevelDao;
import com.goldsign.acc.report.util.ReportLogUtil;
import com.goldsign.acc.report.vo.SysLogLevelVo;


public class ReportLogLevelThread extends Thread{
	private static Logger logger = Logger.getLogger(ReportLogLevelThread.class.
			getName());
	public ReportLogLevelThread() {
		super();
		// TODO Auto-generated constructor stub
	}
	public void run(){
	
		while(true){
			try{
				this.setCurrentLogLevel();
				this.threadSleep();
			}catch(Exception e){
				
			}
			
			
		}
		
	}
	public void runOne(){
		this.setCurrentLogLevel();
			
	}
	private void setCurrentLogLevel(){
		LogLevelDao dao = new LogLevelDao();
		SysLogLevelVo level =dao.getLogLevel();
		if(level ==null){
			AppConstant.LOG_LEVEL_CURRENT =AppConstant.LOG_LEVEL_INFO;
			AppConstant.LOG_LEVEL_TEXT_CURRENT=AppConstant.LOG_LEVEL_INFO_TEXT;
			
		}
		else{
			AppConstant.LOG_LEVEL_CURRENT = level.getSysLevel();
			AppConstant.LOG_LEVEL_TEXT_CURRENT=ReportLogUtil.getLevelText(level.getSysLevel());
		}
		logger.info("设置日志级别为："+AppConstant.LOG_LEVEL_CURRENT);
		
	}
	private void threadSleep(){
		
		try {
			this.sleep(AppConstant.threadLogLevel);
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
