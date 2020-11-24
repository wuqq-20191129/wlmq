<%@ page contentType = "text/html;charset=GBK"%>
<%
  Object ob = request.getAttribute("Error"); 
	String	message="";
	if(ob !=null)
		message = (String)ob;
	else
		message = request.getParameter("Error");
	
	out.println("<font color=\"red\" >");
	out.println("´íÎó£º"+message);
	out.println("</font>");
%>




