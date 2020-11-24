<%@ page contentType = "text/html;charset=GBK"%>

<%@ page import="com.goldsign.frame.struts.BaseForwardAction"%>
<%@ page import="com.goldsign.frame.util.FrameDBUtil"%>

<%
    if (session != null && request != null) {
        FrameDBUtil dbUtil = new FrameDBUtil();
        String flowID = (String) session.getAttribute("flowID");
        dbUtil.logLogoutInfo(flowID);
        dbUtil.logoutHandle(request, response);
    }

%>

<script>

    window.parent.close();
</script>

