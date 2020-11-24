package com.goldsign.acc.frame.controller;

import java.io.File;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author zhongziqi
 * @date  2018-01-31
 * @desc  下载文件
 */
@Controller
public class DownLoadContriller {
	private static Logger logger = Logger.getLogger(DownLoadContriller.class.getName());


	@RequestMapping("/Download")
	public static ResponseEntity<byte[]> downLoadfile(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String filePath = request.getParameter("filePath");
		String expFileName = request.getParameter("expFileName");
		if (filePath == null || "".equals(filePath)) {
			logger.info("文件路径为空");
			throw new Exception("文件路径为空");
		}
		if (expFileName == null || "".equals(expFileName)) {
			logger.info("导出文件名为空");
			throw new Exception("导出文件名为空");
		}
		logger.info("下载文件:" + filePath);
		ResponseEntity<byte[]> re = null;
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Disposition", "attachment;filename=" + URLEncoder.encode(expFileName, "UTF-8"));
		logger.info("转成字节流开始：" + filePath);
		// byte[] by = FileUtils.readFileToByteArray(finalXlsxFile);
		byte[] by = FileUtils.readFileToByteArray(new File(filePath));
		logger.info("转成字节流结束：" + filePath);
		re = new ResponseEntity<byte[]>(by, headers, HttpStatus.OK);
		return re;

	}

}
