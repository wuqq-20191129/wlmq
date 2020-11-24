/*
 * Amendment History:
 *
 * Date          By             Description
 * ----------    -----------    -------------------------------------------
 * 
 */

package com.goldsign.acc.report.util;

import org.apache.log4j.Logger;

import com.goldsign.acc.report.constant.AppConstant;
import com.goldsign.acc.report.common.ReportBase;
import com.goldsign.acc.report.vo.ReportAttribute;
import com.goldsign.acc.report.vo.ReportGenerateResult;

public class ReportWithBalanceOrSquadDay extends ReportBase{
	
	private static Logger logger = Logger.getLogger(ReportWithBalanceOrSquadDay.class.getName());
	
	public ReportGenerateResult generate(String balanceWaterNo,ReportAttribute report,String bufferFlag){

		ReportGenerateResult result = new ReportGenerateResult(false);

		//获取运营日或月
		String reportDay;
//		if(report.getPeriodType().equals("1")){		//day report
                //20170428 modify by mqf 分两种情况，1 普通日报， 2 定制报表 每天生成
                if (ReportBase.isDayReport(report) || ReportBase.isDayReportByCustomed(report)) {    
			reportDay = balanceWaterNo.substring(0,8);
		}
		else{											//month report
			reportDay = balanceWaterNo.substring(0,6)+"00";
		}
		String out = report.getOutType();

//		文件类型使用+号连接，如PDF+XLS+TXT如果有一个生成错误所有都需重新生成
		result = this.saveReportByOutType(balanceWaterNo, reportDay, report,out,bufferFlag);
		
        this.threadSleep();
        return result;

	}
	private void threadSleep(){
		try {
			Thread.sleep(AppConstant.threadSleepTime);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
