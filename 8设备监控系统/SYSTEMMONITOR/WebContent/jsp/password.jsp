<%@ page language="java" contentType="text/html;charset=GBK"
   
 %>
 <%@ page import="com.goldsign.systemmonitor.dao.PasswordDao"
 %>
 <%
 String password="";
 PasswordDao passwordDao=new PasswordDao();
 try
	{
	
	password=passwordDao.getPassword();
	
	}catch(Exception e)
	{
		out.println("����"+e.getMessage());
		return;
	}
 %>



<html>
<head>
<style type="text/css">
td{ FONT-SIZE: 10pt; }
</style>

<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>����</title>
<link rel="stylesheet" type="text/css" href="xsl/css/simple.css" title="Style" />


<script language="JavaScript">

  </script>
  <script language="JavaScript" charset="GBK" src="../js/topmenu.js"> </script>

</head>

<!-- onLoad="setOperatorID();" -->
<body  leftMargin=0 topMargin=0 marginheight="0" onLoad="" marginwidth="0"  scroll="no" bgColor="#EFEFEF">
<div align="center" valign="center" >

	<FORM METHOD=POST ACTION="" >
	<br />
	<table width="100%" border="0" class="listDataTable" cellspacing="0" cellpadding="0">
	    
      	<tr bgColor="#EFEFEF">
      	  <td width="50%" ><div align="right">��������:</div></td>
      	  <td width="50%"><div align="left">    	                 							
             <input type="password" name="Password"  id="Password" size="10" maxlength="6" >
          </div>
          </td>
          </tr>
          <tr >
             <td>&nbsp;</td><td>&nbsp;</td>
          </tr>
         
          <tr bgColor="#EFEFEF">
          
          <td width="50%" ><div align="right">
           <input type="button" name="confirm" value="ȷ��" onclick="validPass();">
           </div>
           </td>
           <td width="50%" ><div align="left">
          <input type="button" name="close" value="ȡ��" onClick="window.close();">
           </div>
          </td>
        </tr>
    
   
     	</table>
    <input type="hidden" name="DbPassword"  id="DbPassword" value="<%=password %>">
    </FORM>

 </div>
</body>
</html>




