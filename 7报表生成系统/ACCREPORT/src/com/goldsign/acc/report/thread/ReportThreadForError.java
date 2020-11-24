package com.goldsign.acc.report.thread;
import java.util.Vector;

import com.goldsign.acc.report.handler.ReportGenerator;
import com.goldsign.acc.report.vo.ThreadStatus;
public class ReportThreadForError extends Thread{
  private Vector reports = null;
  private String balanceWaterNo = null;
  private int threadNum = 0;
  private ThreadStatus st = new ThreadStatus();
  private String bufferFlag ;


  public ReportThreadForError(Vector reports,String balanceNo,int num,String bufferFlag) {
    this.reports = reports;
    this.balanceWaterNo = balanceNo;
    this.threadNum =num;
    this.bufferFlag = bufferFlag;

  }


  public ReportThreadForError() {
  }
  public void run(){
    ReportGenerator gen = new ReportGenerator();
    gen.generateReportForError(this.reports,balanceWaterNo,threadNum,bufferFlag);
    this.setThreadStatus(true);
  }
  public void setThreadStatus(boolean status){
    synchronized(this.st){
      st.setIsFinishForThread(status);
    }

  }
  public boolean getThreadStatus(){
    synchronized(this.st){
      return st.getIsFinishForThread();
    }

  }

}
