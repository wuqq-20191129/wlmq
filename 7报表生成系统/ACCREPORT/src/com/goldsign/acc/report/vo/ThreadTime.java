package com.goldsign.acc.report.vo;

public class ThreadTime {
  private long startTime =0;
  private long endTime =0;
  public ThreadTime() {
  }
  public void setStartTime(long startTime){
    this.startTime = startTime;
  }
  public long getStartTime(){
    return this.startTime;
  }
  public void setEndTime(long endTime){
    this.endTime = endTime;
  }
  public long getEndTime(){
    return this.endTime;
  }

}
