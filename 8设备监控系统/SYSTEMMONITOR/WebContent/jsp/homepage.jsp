<%@ page language="java" contentType="text/html;charset=GBK" %>
<%@ page import="com.goldsign.frame.util.FrameCharUtil,java.text.SimpleDateFormat,java.util.Date"%>

<%
String topMenuName="";
topMenuName=request.getParameter("topMenuName");
SimpleDateFormat sdf=new SimpleDateFormat("MM��dd��");
String topMenuId = request.getParameter("topMenuId");
%>
<html>
<head>
<!-- background-image: url(images/menu_image<%=topMenuId %>.jpg); -->
<style type="text/css">
	.bgd { 
 				background-repeat: no-repeat; background-position: left top;}
</style>
<script language="JavaScript">
		function changBackground(){			
			var screenWidth=screen.width;
			var screenHeight = screen.height;
//			alert("screenHeight="+screenHeight+" screenWidth"+screenWidth);
      var imgUrl = document.body.background;
      var idx = imgUrl.indexOf(".jpg");
      if((screenWidth==1152&&screenHeight==864)||(screenWidth==1280&&screenHeight==768)||(screenWidth==1280&&screenHeight==1024))
      	document.body.background = imgUrl.substring(0,idx)+"_"+screenWidth+"_"+screenHeight+".jpg";
 //     alert(document.body.background);
		}
</script>

</head>

<body  class="bgd" background="images/menu_image<%=topMenuId %>.jpg" onload="changBackground();">



<%
if (FrameCharUtil.IsoToGbk(topMenuName).equals("����ϵͳ")){
%>

<br/>
<!--
<font size="5">
	<center><b><%=sdf.format(new Date(System.currentTimeMillis()-3600000*24))%>����</b></center>
</font>
-->
<font size="2">
   <center>
	<table align="center" width="100��" class="tableStyle">
		<tr>
			<td><a href="../Bbxtjysjjsehzb.do?ModuleID=050102"><font size="2">�������ݽ����ջ��ܱ�</font></a></td>
		</tr>
		<tr>
			<td><a href="../BbxtSyExceptBalance.do?ModuleID=050502"><font size="2">�쳣�������ݽ����ջ��ܱ�</font></a></td>
		</tr>

		<tr>
			<td><a href="../Bbxtxtyyrb.do?ModuleID=050716"><font size="2">ϵͳ��Ӫ�ձ�</font></a></td>
		</tr>

		<tr>
			<td><a href="../BbxtFxbb_002.do?ModuleID=050202"><font size="2">ϵͳOD�����ձ�</font></a></td>
		</tr>
		<tr>
			<td><a href="../BbxtFxbb_003.do?ModuleID=050203"><font size="2">վ��OD�����ձ�</font></a></td>
		</tr>

		<tr>
			<td><a href="../BbxtKlbb_lineFlowDaily.do?ModuleID=050604"><font size="2">��·����վ�����ձ�</font></a></td>
		</tr>
		<tr>
			<td><a href="../BbxtKlbb_stationFlowDaily.do?ModuleID=050602"><font size="2">��վ����վ�����ձ�</font></a></td>
		</tr>
		<tr>
			<td><a href="../ticketSaleReport.do?ModuleID=050703"><font size="2">��Ʊ����ͳ���ձ�</font></a></td>
		</tr>
		<tr>
			<td><a href="../ticketUpdateDayReport.do?ModuleID=050704"><font size="2">Ʊ������ͳ���ձ�</font></a></td>
		</tr>
		<tr>
			<td><a href="../ReturnDayReport.do?ModuleID=050706"><font size="2">ϵͳ�˿�ͳ���ձ�</font></a></td>
		</tr>

	</table>
  </center>
</font>

<%
	}
%>
</body>
</html>

