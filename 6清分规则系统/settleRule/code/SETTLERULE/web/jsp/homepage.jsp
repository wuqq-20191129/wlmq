<%@ page language="java" contentType="text/html;charset=GBK" %>
<%@ page import="com.goldsign.frame.util.FrameUtil,java.text.SimpleDateFormat,java.util.Date"%>

<%
String topMenuName="";
topMenuName=request.getParameter("topMenuName");
SimpleDateFormat sdf=new SimpleDateFormat("MMÔÂddÈÕ");
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

</body>
</html>

