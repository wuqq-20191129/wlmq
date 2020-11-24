<%-- 
    Document   : user
    Created on : 2017-4-21, 14:31:19
    Author     : hejj
--%>

<%@page language="java"  contentType="text/html" pageEncoding="GB18030"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=GB18030">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Hello World hejj!</h1>
        <h1>welcome 1!</h1>
        
        ${company}<br/>
        <c:forEach items="${users}" var="item">  
            1
            ${item.sys_operator_id}--${item.sys_operator_name}--${item.sys_employee_id}--${item.sys_status}--${item.login_num}
              --${item.edit_past_days}<br />
        </c:forEach>
    </body>
</html>
