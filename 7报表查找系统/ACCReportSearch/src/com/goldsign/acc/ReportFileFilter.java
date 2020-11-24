package com.goldsign.acc;
import java.io.FilenameFilter;
import java.io.File;


public class ReportFileFilter implements FilenameFilter{
  private String filter ="";
  public ReportFileFilter() {
  }
  public ReportFileFilter(String filter) {
    this.filter = filter;
  }
  public boolean accept(File dir,String name){
	if(this.isErrFile(name))
		return false;
    if(filter.length()==0)
      return true;
    if(name.startsWith(filter))
      return true;
    return false;

  }
  public boolean isErrFile(String name){
	  if(name == null )
		  return false;
	  int index = name.indexOf("ERR");
	  int index1 = name.indexOf("err");
	  if(index !=-1 || index1 !=-1)
		  return true;
	  return false;
  }

}
