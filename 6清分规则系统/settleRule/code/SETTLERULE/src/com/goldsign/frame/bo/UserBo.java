/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.frame.bo;

import com.goldsign.frame.dao.UserDao;
import com.goldsign.frame.filter.AuthenticateFilter;
import com.goldsign.frame.util.FrameDBUtil;
import com.goldsign.frame.util.FrameUtil;
import com.goldsign.frame.vo.AuditResult;
import com.goldsign.frame.vo.Menu;
import com.goldsign.frame.vo.User;
import com.goldsign.login.util.Encryption;
import org.apache.log4j.Logger;

import java.util.Vector;
import java.util.Hashtable;

import java.sql.Timestamp;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.StringTokenizer;
import java.util.GregorianCalendar;


/**
 *
 * @author hejj
 */
public class UserBo {
    private static Logger logger = Logger.getLogger(UserBo.class.getName());
    private String loginFile ="loginAudit.properties";
    private static String LOCKED ="1";
    private static String UNLOCKED ="0";
    private static long DAYS = 7;

	public	AuditResult	login(String account,String password,HttpServletRequest req,HttpServletResponse resp) throws Exception{
    	UserDao UserDao = new UserDao();
    	User user = UserDao.findByAccount(account);
        AuditResult auditResult = null;
        if(user !=null)
          return this.AuditUser(user,password,req,resp);
        else
        {
            auditResult = new AuditResult();
            auditResult.setResult(false);
            auditResult.setMsg("账号不存在");
            return auditResult;
        }
/*
		if (user!=null){

			if(user.getPassword().equals(password)){
				return	true;
			}else{
				return	false;
			}

		}else{
			return false;
		}
                */

	}
    public AuditResult AuditUser(User user,String password,HttpServletRequest req,HttpServletResponse resp) throws Exception{
      AuditResult auditResult = new AuditResult();
      FrameUtil util = new FrameUtil();
      if(this.isInDenyIps(user,req)){
        auditResult.setResult(false);
        auditResult.setMsg("用户IP不能访问应用");
        return auditResult;
      }

      if(user.getUserStatus().equals(this.LOCKED)){
        auditResult.setResult(false);
        auditResult.setMsg("帐户被锁住");
        return auditResult;
      }
 
      if(!this.isInValidDate(user,req)){
        auditResult.setResult(false);
        auditResult.setMsg("帐户已过有效期");
        return auditResult;
      }
      
      //System.out.println("旧密码："+user.getPassword());
      //System.out.println("新录入密码："+password);
      //Encryption.biEncrypt
//      if(!user.getPassword().equals(util.encode(password))){
      if(!user.getPassword().equals(Encryption.biEncrypt(password))){
        auditResult.setResult(false);
        auditResult.setMsg("密码错误");
        if(this.isOverFailedNum(user))
        {
          new UserDao().modifyUser(user.getAccount(),"sys_status",this.LOCKED);
          user.setUserStatus(this.LOCKED);
          auditResult.setMsg("密码输入错误超过3次，帐户:"+user.getAccount()+"被锁住");
        }
        return auditResult;
      }
      
      //2007-07-18 yjh  添加修订密码提示
      if(!this.isEditPassWord(user,req)){    	  
          //判断提交的新旧密码是否为空

    	  
          auditResult.setResult(false);          
          auditResult.setMsg("密码已过期，请修改");          
          return auditResult;
      }

      

      

      
      //启动消息接收线程,发送用户登陆消息给群集服务器 
      //暂时不考虑集群应用 add by hejj 2012-04-07
      // new Util().handleClusterForUserLogin(req,resp,user);
       if(!this.isInUsed(user,req,resp)){
      //   auditResult.setResult(false);
         auditResult.setResult(true);
     //    auditResult.setMsg("帐户："+user.getAccount()+"正在使用");
         return auditResult;
       }


      return auditResult;

    }
    
    //2007-07-18  yjh  新增，提示密码修改

    
    public boolean isEditPassWord(User user,HttpServletRequest req){
        Timestamp time = new Timestamp(System.currentTimeMillis());
        String date = time.toString().substring(0,10);
        String dbDate =new FrameUtil().convertDateToDBFormat(date);
                       
        if(this.isNeedEdit(user,date,req)){
        	return false;        
        }else{
        	return true;
        }
      }
    
    
    public boolean isInValidDate(User user,HttpServletRequest req){
      Timestamp time = new Timestamp(System.currentTimeMillis());
      String date = time.toString().substring(0,10);
      String dbDate =new FrameUtil().convertDateToDBFormat(date);
      this.isNeedPrompt(user,date,req);
      if(dbDate.compareTo(user.getExpireDate())>0)
         return false;
      return true;

    }
    
    public boolean isNeedPrompt(User user,String currentDate,HttpServletRequest req){
        String expireDate = user.getExpireDate().trim();
        if(expireDate.length()<8)
          return false;

        String year = expireDate.substring(0,4);
        String month = expireDate.substring(4,6);
        String day  = expireDate.substring(6,8);

        String curYear = currentDate.substring(0,4);
        String curMonth = currentDate.substring(5,7);
        String curDay = currentDate.substring(8,10);

        int iYear = new Integer(year).intValue();
        int iMonth = new Integer(month).intValue();
        int iDay = new Integer(day).intValue();

        int iCurYear = new Integer(curYear).intValue();
        int iCurMonth = new Integer(curMonth).intValue();
        int iCurDay = new Integer(curDay).intValue();



        GregorianCalendar gExpired = new GregorianCalendar(iYear,iMonth,iDay);
        GregorianCalendar gCur = new GregorianCalendar(iCurYear,iCurMonth,iCurDay);
        long days = -1;
        days =(gExpired.getTimeInMillis() -gCur.getTimeInMillis())/(24*60*60*10*10*10);

      //  System.out.println("days="+days);
        if(days >=0 &&days <= this.DAYS){
          req.getSession().setAttribute("LeftDays",Long.toString(days));
          return true;
        }
        else{
          req.getSession().setAttribute("LeftDays",Long.toString(-1));
          return false;
        }
    }
    
    //2007-07-18   yjh  新增  判断当前日期是否超过需要提示日期

    public boolean isNeedEdit(User user,String currentDate,HttpServletRequest req){
        String editDate = user.getEditPassWordDate().trim();
        if(editDate.length()<8)
          return false;

        int    iDays=user.getEditPassWordDays();
        
        String year = editDate.substring(0,4);
        String month = editDate.substring(4,6);
        String day  = editDate.substring(6,8);

        String curYear = currentDate.substring(0,4);
        String curMonth = currentDate.substring(5,7);
        String curDay = currentDate.substring(8,10);

        int iYear = new Integer(year).intValue();
        int iMonth = new Integer(month).intValue();
        int iDay = new Integer(day).intValue();

        int iCurYear = new Integer(curYear).intValue();
        int iCurMonth = new Integer(curMonth).intValue();
        int iCurDay = new Integer(curDay).intValue();


        GregorianCalendar gExpired = new GregorianCalendar(iYear,iMonth,iDay);
        GregorianCalendar gCur = new GregorianCalendar(iCurYear,iCurMonth,iCurDay);
        
        long days = -1;
        
        days =(gCur.getTimeInMillis()-gExpired.getTimeInMillis())/(24*60*60*10*10*10);

        //System.out.println("days:"+String.valueOf(days));       
        //System.out.println("idays:"+String.valueOf(iDays));        
                
        if( days<=iDays){
          //req.getSession().setAttribute("LeftDays",Long.toString(days));
          //不提示修改密码          
          //System.out.println("不提示修改密码");        	
          //req.getSession().setAttribute("LeftDays",Long.toString(-1));
            return false;                    
        }
        else{
          //提示修改密码
            //System.out.println("提示修改密码");        	            
            return true;
        }
    }
    
     
    public boolean isInUsed(User user,HttpServletRequest req,HttpServletResponse resp) throws Exception{
      AuthenticateFilter filter = new AuthenticateFilter();
      String oldSessionID = user.getSessionID();
      String newSessionID = req.getSession().getId();
      FrameDBUtil dbUtil = new FrameDBUtil();
//      if(this.isInRefresh(req))
     if(filter.AuthenticateTokenInHeader(req))
       return false;
      if(user.getLoginNum()>0){//帐户已在使用
        if(oldSessionID.length() !=0){
 //         if(req.getSession().getId().equals(oldSessionID))
 //           return true;
          if(!oldSessionID.equals(newSessionID))
            dbUtil.removeUserSession(oldSessionID, req, resp);
        }
       }
 //     new UserDao().modifyUser(user.getAccount(),"login_num",1);
     //设置帐户已登陆信息

     /*
     String newSessionID = req.getSession().getId();
      new UserDao().modifyUser(user.getAccount(),"session_id",newSessionID,"login_num",1);
      user.setLoginNum(1);
      user.setSessionID(newSessionID);
      dbUtil.addUserSession(req.getSession());
      */
     this.setUserLoginInfo(user,req);
      return false;


    }
    public void setUserLoginInfo(User user,HttpServletRequest req) throws Exception{
      String newSessionID = req.getSession().getId();

      new UserDao().modifyUser(user.getAccount(),"session_id",newSessionID,"login_num",1);
      user.setLoginNum(1);
      user.setSessionID(newSessionID);
      new FrameDBUtil().addUserSession(req.getSession());
    }
    public boolean isInRefresh(HttpServletRequest req){
      String referer = req.getHeader("Referer");
      if(referer == null || referer.length() ==0)
        return false;
      int index = referer.indexOf("index_1.jsp");
      if(index != -1)
        return true;
      return false;
    }
    public boolean isInDenyIps(User user,HttpServletRequest req) throws Exception{
  //    HashMap loginProp = new Util().getConfigProperties(this.loginFile);
      HashMap loginProp = new FrameUtil().getConfigPropertiesByAppPath(req,this.loginFile);
      if(loginProp.isEmpty())
        return false;
      String denyIps = (String)loginProp.get("denyIPs");
      StringTokenizer st = new StringTokenizer(denyIps,";");
      String ip =req.getRemoteAddr();
      String token = "";
      while(st.hasMoreTokens()){
        token = st.nextToken().trim();
        if(ip.equals(token))
          return true;

      }
      return false;

    }
    public boolean isOverFailedNum(User user) throws Exception{
      new UserDao().modifyUser(user.getAccount(),"failed_num",user.getFailedNum()+1);
      user.setFailedNum(user.getFailedNum()+1);
      if(user.getFailedNum()>3)
        return true;
      return false;

    }
    public User getUserByAccount(String account) throws Exception{
    	UserDao UserDao = new UserDao();
     	User user = UserDao.findByAccount(account);
	    return user;
    }

    public String getUserNameByAccount(String account) throws Exception{
    	UserDao UserDao = new UserDao();
     	User user = UserDao.findByAccount(account);
	    return user.getUsername();
    }

    public Hashtable getUserRoles(String account) throws Exception{
    	UserDao UserDao = new UserDao();
	    return UserDao.getUserRoles(account);
    }

	public	Vector	getTopMenu(String operatorID) throws Exception{
    	UserDao UserDao = new UserDao();
		return UserDao.getTopMenu(operatorID);// getTopMenu(operatorID);
	}

	private Hashtable getUserAccessMenu(String account) throws Exception{
    	UserDao ud = new UserDao();
	    return ud.getUserMenu(account);
    }

	private Vector getLeftMenu() throws Exception{
    	UserDao UserDao = new UserDao();
		return UserDao.getLeftMenu();
	}
//确定顶菜单项有没有对应的有权限访问的左菜单，如没有该顶菜单没有链接，如有，显示该菜单且有链接
	private Vector getTopMenuItem(String topMenuId) throws Exception{
    	UserDao UserDao = new UserDao();
		Vector allLeftMenu=UserDao.getLeftMenu();
		Vector resultList=new Vector();
		Menu menu=null;

		for (int i=0;i<allLeftMenu.size();i++ ){
			menu=(Menu)allLeftMenu.elementAt(i);
			if (menu.getTopMenuId().equals(topMenuId)){
				resultList.add(menu);
			}
		}
		return resultList;
	}

	public synchronized Vector getUserMenu(String account,String topMenuId) throws Exception,Exception{
		Hashtable userAccessMenu=new Hashtable();
		Vector topMenuItem=new Vector();
		Vector tmpList=new Vector();
		Vector resultList=new Vector();
		Menu menu=null;

		userAccessMenu=getUserAccessMenu(account);
		topMenuItem=getTopMenuItem(topMenuId);

		for (int i=0;i<topMenuItem.size();i++ ){
			menu=(Menu)topMenuItem.elementAt(i);
			if (!(userAccessMenu.get(menu.getMenuId())==null)){
				tmpList.add(menu);
			}
		}

		for (int i=0;i<tmpList.size();i++ ){
			menu=(Menu)tmpList.elementAt(i);

   //                     System.out.println(menu.getMenuName());

			resultList.add(menu);
		}

		return resultList;
	}
	
	//  新增 提示用户修改密码，有效期剩7天开始提醒

	public  String getUserPassWordDays(User user){
		String strDays;
		
	    Timestamp time = new Timestamp(System.currentTimeMillis());
	    String date = time.toString().substring(0,10);
	    String dbDate =new FrameUtil().convertDateToDBFormat(date);
	      
				
        String editDate = user.getEditPassWordDate().trim();
        if(editDate.length()<8){           
           return "1";
        }
        
        long    iDays=user.getEditPassWordDays();
        
        String year = editDate.substring(0,4);
        String month = editDate.substring(4,6);
        String day  = editDate.substring(6,8);

        String curYear = date.substring(0,4);
        String curMonth = date.substring(5,7);
        String curDay = date.substring(8,10);

        int iYear = new Integer(year).intValue();
        int iMonth = new Integer(month).intValue();
        int iDay = new Integer(day).intValue();

        int iCurYear = new Integer(curYear).intValue();
        int iCurMonth = new Integer(curMonth).intValue();
        int iCurDay = new Integer(curDay).intValue();


        GregorianCalendar gExpired = new GregorianCalendar(iYear,iMonth,iDay);
        GregorianCalendar gCur = new GregorianCalendar(iCurYear,iCurMonth,iCurDay);
        
        long days = -1;        
        days =(gCur.getTimeInMillis()-gExpired.getTimeInMillis())/(24*60*60*10*10*10);
        long iRemainDays=iDays-days;        
        strDays=String.valueOf(iRemainDays);    
		return strDays;
	}


    
}
