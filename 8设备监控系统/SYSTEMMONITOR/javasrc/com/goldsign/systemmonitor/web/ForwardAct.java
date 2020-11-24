package com.goldsign.systemmonitor.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.goldsign.systemmonitor.dao.MenuDao;
import com.goldsign.systemmonitor.util.Encryption;

public class ForwardAct extends Action{
	
	public ForwardAct() {
		super();
		// TODO Auto-generated constructor stub
	}
	private static Logger logger = Logger.getLogger(ForwardAct.class.getName());
	private boolean isValidPostParameter(String postParameter){
		if(postParameter ==null || postParameter.length()==0)
			return false;
		return true;
	}
	private String getForwardUrl(String url,String postParameter){
		if(url==null || url.length()==0)
			return "";
		String con="&";
		if(url.indexOf("?")==-1)
			con="?";
		if(this.isValidPostParameter(postParameter))
			url =url+con+postParameter;
		return url;
	}
	public ActionForward execute(ActionMapping mapping,ActionForm baseForm,HttpServletRequest request,HttpServletResponse response)
	throws	Exception{
		String url= request.getParameter("url");
		String moduleId = request.getParameter("ModuleID");
		String postParameter= new MenuDao().getMenuPostParameter(moduleId);
		if(this.isValidPostParameter(postParameter))
			postParameter=new Encryption().biDecrypt(Encryption.ENC_KEY,postParameter);
		logger.info("url="+url+" postParameter="+postParameter);
		url = this.getForwardUrl(url,postParameter);
		logger.info("complete url="+url);
		response.sendRedirect(url);
		
		
		return	null;			
	}
	
}
