<%-- 
    Document   : common_template_web_variable_ic
    Created on : 2017-7-11, 15:12:10
    Author     : hejj
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
    String templateName = request.getParameter("template_name");
    if (templateName != null && templateName.length() != 0) {
        request.setAttribute("templateName", templateName);
    }


%>
<c:choose>
    <c:when test="${templateName == 'common_web_variable_ic'}">
        <input type="hidden" name="operType" id="operType" value="${OperType}" />
        <input type="hidden" name="queryCondition" id="queryCondition" value="${QueryCondition}" />
        <input type="hidden" name="billRecordFlag" id="billRecordFlag" value="${BillRecordFlag}" />
    </c:when>
    <c:when test="${templateName == 'common_web_variable_ic_param'}">

    </c:when>
    <c:when test="${templateName == 'common_web_variable_report'}">
        <!-- 报表参数名 -->
        <input type="hidden" name="parameterNames" id="parameterNames" value="${ParameterNames}" />
        <input type="hidden" name="template" id="template" value="${Template}" />
        <input type="hidden" name="QueryDateNames" id="QueryDateNames" value="${QueryDateNames}" />
    </c:when>
</c:choose>
