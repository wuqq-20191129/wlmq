package com.goldsign.acc.frame.task;

import java.io.DataInputStream;
import java.io.File;

import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.goldsign.acc.frame.constant.ConfigConstant;

/**
 * @desc:
 * @author:zhongziqi
 * @create 2018年3月14日
 */
@Component
public class ExcelClearingTask {
	private static Logger logger = Logger.getLogger(ExcelClearingTask.class);

	 @Scheduled(cron = "0 30 23 * * ?")
//	@Scheduled(fixedRate = 1000 * 60)
	public void autoClearing() {
		if (ConfigConstant.EXPORT_EXCEL_PATH == null || "".equals(ConfigConstant.EXPORT_EXCEL_PATH)) {
			logger.error("Excel临时文件路径未初始化");
			return;
		}
		File dir = new File(ConfigConstant.EXPORT_EXCEL_PATH);
		if (dir.exists() && dir.isDirectory()) {
			logger.info("======自动清理导出Excel临时文件开始=======");
			logger.info("清理路径:" + ConfigConstant.EXPORT_EXCEL_PATH);
			try {
				String[] flieName = dir.list();
				logger.info("文件数量:"+flieName.length);
				if (flieName.length > 0) {
					for (int i = 0; i < flieName.length; i++) {
						if (flieName[i].contains(".xlsx")) {
							File file = new File(ConfigConstant.EXPORT_EXCEL_PATH + "/" + flieName[i]);
							if(file.exists()) {
								logger.info("删除文件"+flieName[i]);
								file.delete();
							}
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			logger.info("======自动清理导出Excel临时文件结束=======");
		} else {
			logger.info("清理路径:" + ConfigConstant.EXPORT_EXCEL_PATH + " 不存在");
		}

	}
}
