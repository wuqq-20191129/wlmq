<%@ page language="java" contentType="text/html;charset=GBK"
 import="com.goldsign.frame.dao.UserDao" 
 import="org.apache.struts.action.ActionMapping"
 %>

<%
        Object ob = session.getAttribute("LoginMsg");
		String loginMsg ="";
		String version ="";
		UserDao dao = new UserDao();
				
        if(ob !=null)
          loginMsg = (String)ob;
        version = dao.getMaxVersion();         
%>
<html>
<head>
<style type="text/css">
td{ FONT-SIZE: 10pt; }
</style>

<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title>::长沙市轨道交通自动售检票系统--运营中心管理系统::</title>
<link href="css.css" rel="stylesheet" type="text/css">
<style type="text/css">
<!--
.style14 {color: #00FFFF}
.style16 {color: #006599}
-->
</style>

<script language="JavaScript">
   function setOperatorID(){

     var c1 = document.cookie;
     if(c1 ==null || c1 =="")
     		return;
  //   alert(c1);
     var idx = c1.indexOf("OperatorID");
     var idx1 = -1;
     var operatorID ="";
     if(idx == -1)
     	return;
     idx = c1.indexOf("=",idx+1);
     idx1 = c1.indexOf(";",idx+1);
     if(idx1 == -1)
     	 operatorID = c1.substring(idx+1);
     else
       operatorID = c1.substring(idx+1,idx1);
       
     document.forms["loginForm"].all["Account"].value = operatorID;
   }
  </script>

</head>

<!-- onLoad="setOperatorID();" -->
<body  leftMargin=0 topMargin=0 marginheight="0" onLoad="setOperatorID();" marginwidth="0"  scroll="no" background="images/frame_loginbg.jpg">
<div align="center" >
<table width="98%" height="98%" border="0"  cellspacing="0" cellpadding="0">

  <tr height="25%">
    <td>&nbsp;</td>
  </tr>
  
  <tr height="50%">
    <td>
	<FORM METHOD=POST ACTION="login.do" name="loginForm">
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
      	<tr>
        	<td width="70" height="250"><img src="images/frame_login_t01.jpg" width="70" height="250"></td>
       		<td width="337"  height="250"><img src="images/frame_login_t02.jpg" width="337" height="250"></td>
       		<td valign="top" background="images/frame_login_t05.jpg">
          		<table width="100%"  border="0" cellspacing="0" cellpadding="0">
          			<tr>
            				<td><img src="images/frame_login_t03.jpg" width="246" height="140"></td>
          			</tr>
          			<tr>
            				<td height="95">
                            <table width="90%"  border="0" cellspacing="3" cellpadding="0">
              					      <tr valign="bottom">
               								 <td width="10%" class="style14"><div align="left">用&nbsp;户</div></td>
                               				 <td width="13%"><span class="dl"><FONT color=#ffffff>
                               				 <input type="text" name="Account"  size="10" maxlength="16" style="border: 1 solid #FFCC00"; border-style: solid; border-color: #ffcc00">
                               				 </FONT></span>
                               				 </td>
               							    
               							    <td width="10%" class="style14"><div align="left">旧密码</div></td>
               							    <td width="75%"><span class="dl"><FONT color=#ffffff>
                 							<input type="password" name="Password"  size="10" maxlength="16" style="border: 1 solid #FFCC00"; border-style: solid; border-color: #ffcc00">
               								</FONT></span>
               								 </td>               								 
                               	 		</tr>
               						 		
              								<tr valign="bottom">
                									<td width="10%" class="style14"><div align="left">新密码</div></td>
                									<td width="13%"><span class="dl">
                									 <FONT color=#ffffff>
                  										<input type="password" name="NewPassword"  size="10" maxlength="16" style="border: 1 solid #FFCC00"; border-style: solid; border-color: #ffcc00">
                								   </FONT></span>
                								  </td>
                		
                								    <td width="10%" class="style14"><div align="left">确认密码</div></td>
                               						<td width="70%"><span class="dl"><FONT color=#ffffff>
                               						<input type="password" name="RePassword"  size="10" maxlength="16" style="border: 1 solid #FFCC00"; border-style: solid; border-color: #ffcc00">
                               						</FONT></span>
                							</tr>
											<tr valign="bottom">
												<td width="10%" class="style14"><div align="left">&nbsp;</div></td>
												<td width="13%" class="style14"><div align="left">&nbsp;</div></td>
												<td width="10%" class="style14"><div align="left">&nbsp;</div></td>
												<td width="70%" class="style14"><div align="left">&nbsp;</div></td>
											</tr>
											<tr valign="bottom">
                							<td width="10%" class="style14"><input type="hidden" name="Flag" value="1" visible=false size="1"><div align="left">&nbsp;</div></td>
                								<td width="13%"  valign="right" class="style14"><!-- class="style14" -->
                                                                                    <input type="hidden" name="sys_flag" value="03" />
                  							    <input type="submit" name="submit" value="修改" >
                  							    <input type="button" name="close" value="取消" onClick="window.close();">                							    
                							    </td>
		
                                 			<td width="10%" class="style14"><div align="left">&nbsp;</div></td>
                                 			<td width="70%" valign="bottom" class="style14">
                                  				<font color="red" >   								
                                     			<%=loginMsg%>                                                                                  
                                  				</font>
                                 			</td>
               						      </tr>
                             <tr valign="bottom">
                
                            </tr>
            					   </table>
					          </td>
          			</tr>
        		</table>
         </td>
        	<td background="images/frame_login_t05.jpg">&nbsp;</td>
       </tr>
       <tr height="30">
       	 <td colspan="2" valign="bottom" class="down">
         粤ICP备020236 最佳显示器分辨率1280x1024 支持IE 5.5以上版本
         </td>
       </tr>
       <tr height="30">
       	 <td colspan="2" valign="bottom" class="down">
         软件版本<%=version %>
         </td>
       </tr>
     	</table>
    </FORM>

    </td>
  </tr>

  <tr height="25%">
  	<td>
  	</td>
  </tr>
</table>
 </div>
</body>
</html>


<SCRIPT LANGUAGE="JavaScript">
<!--
function openFullWindow(){
	window.open("./login.do","","'fullscreen=yes,scrollbars=auto")
}
//-->
</SCRIPT>

