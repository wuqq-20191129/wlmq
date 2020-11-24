package com.goldsign.acc;

import java.io.File;
import java.io.FilenameFilter;
import java.util.StringTokenizer;
import java.util.Iterator;

public class ReportFileFilterForBalance implements FilenameFilter {
	String filter ="";
    public ReportFileFilterForBalance(){
    	
    }
public ReportFileFilterForBalance(String filter){
	this.filter = filter;
    	
    }
	public boolean accept(File dir, String name) {
		// TODO Auto-generated method stub
		if(new ReportFileFilter().isErrFile(name))
			return false;
		if(filter.length() ==0)
			return true;
	    return this.isIncludeInPosition(name,this.filter);	    

	}
	public boolean isIncludeInPosition(String name,String nameFilter){
		int index=0;
		String balanceDate ="";
		String reportCode ="";
		index = nameFilter.indexOf(".");
		if(index ==-1)
			return false;
		balanceDate = nameFilter.substring(0,index);
		reportCode = nameFilter.substring(index+1);
		index = name.indexOf(reportCode+"."+balanceDate);
		if(index ==-1)
			return false;
		else
			return true;

	}

}
