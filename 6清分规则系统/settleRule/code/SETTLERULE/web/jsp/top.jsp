<%@ page language="java" contentType="text/html;charset=GBK" %>

<%@ page import="com.goldsign.frame.struts.BaseForwardAction,

         java.util.Vector,

         java.text.SimpleDateFormat,

         java.util.Date,

         java.util.Hashtable,

      

         com.goldsign.frame.vo.Menu,

         com.goldsign.frame.vo.User,

         com.goldsign.frame.bo.UserBo"

         %>

<%



    //判断是否没有经过Struts,直接在浏览器输入网址访问
    String forwardedByAction = (String) request.getAttribute(BaseForwardAction.FORWARDED_BY_ACTION_KEY);

    if (forwardedByAction == null) {
        response.sendError(HttpServletResponse.SC_NOT_FOUND);
        return;

    }

    UserBo newUserBo = new UserBo();
    Vector list = new Vector();
    Vector userMenu = new Vector();




    //取登录信息，得到用户信息	
    User user = (User) session.getAttribute("User");
    String operatorID = user.getAccount();

    //取得密码有效期	
    String strDays = newUserBo.getUserPassWordDays(user);

    try {
        //获得用户的顶菜单
        list = newUserBo.getTopMenu(operatorID);
    } catch (Exception e) {
        out.println("错误：" + e.getMessage());
        return;
    }


    //转换成HashTable
    Menu menu = null;

    Hashtable topMenuTable = new Hashtable();
    String topMenuId = null;
    int topMenu_num = 0;
    String topMenuWidth = "";
    //顶菜单内容放入topMenuTable
    for (int i = 0; i < list.size(); i++) {
        menu = (Menu) list.elementAt(i);
        topMenuTable.put(menu.getMenuId(), menu.getMenuName());
        if (i == 0) {
            topMenuId = menu.getMenuId();
        }

    }

    session.setAttribute("topMenuTable", topMenuTable);
    //session.setAttribute("TopMenuId",topMenuId);
    SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
%>

<html>

    <head>

        <meta http-equiv="Content-Type" content="text/html; charset=gb2312">

        <title>::长沙市轨道交通清分规则系统--综合中央计算机系统::</title>

        <base target="contents">
        <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/css.css">
        <script language="JavaScript" charset="GBK" src="./js/topmenu.js"> </script>
        <script language="JavaScript">
            function setMenuWidth(){
                var tbl = document.getElementById("menutableid");
                var menuWidthOb = document.getElementById("topMenuWidthOb");
                tbl.width =menuWidthOb.value;
            }
            function freshView(){
                alert("fresh top view");

            }
        </script>

    </head>


    <body  bgColor=#ffffff leftMargin=0 topMargin=0 marginheight="0" marginwidth="0" onLoad="setMenuWidth();">
        <table width="100% " border="0" cellspacing="0" cellpadding="0">
            <tr  >
                <td width="174" height="83" background="./images/frame_top_bg.jpg"><img src="./images//frame_logo.jpg" width="174" height="83"></td>
                <td colspan="2">
                    <table width="100%"  border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td width="572" height="83" background="./images/frame_top_bg.jpg"><img src="./images/frame_top_tu.jpg" width="572" height="83"></td>
                            <td width="278" height="83" background="./images/frame_top_bg.jpg"><img src="./images/frame_top_tu_3.jpg" width="278" height="83"></td>
                        </tr>
                    </table>
                </td>
                <td background="./images/frame_top_bg.jpg">
                <td>
            </tr>
        </table>
        <table width="100% " border="0" cellspacing="0" cellpadding="0">
            <tr>
                <!--  background="./images//frame_menu_bg.jpg"  -->
                <td background="./images/menu/frame_menu_bg.jpg" align="left" valign="top" width="22%" height="33"><a class="simple" ><b>&nbsp;<%=sdf.format(new Date(System.currentTimeMillis()))%>
                            <font color="yellow"> &nbsp;欢迎您！<%=user.getUsername()%></font> 
                            <!-- 判断是否到提醒日期 -->
                            <%
                                int iDays = new Integer(strDays).intValue();
                                if (iDays <= 7 && iDays >= 0) {
                            %>
                            <font color="red"> &nbsp;密码有效剩:<%=strDays%>天</font>
                            <%}%>								
                        </b></a>
                </td>
                <!-- background="./images//frame_menu_bg.jpg"-->
                <td  width="70%" height="33" background="./images/menu/frame_menu_bg.jpg">
                    <table  border="0" id="menutableid" cellspacing="0" cellpadding="0" width="616" height="33">
                        <tr >

                            <!-- 	<td valign="bottom" height="20"> -->
                            <%
                                for (int i = 0; i < list.size(); i++) {
                                    menu = (Menu) list.elementAt(i);
                                    String url = "";
                                    if (!(user == null)) {
                                        userMenu = newUserBo.getUserMenu(user.getAccount(), menu.getMenuId());
                                        if (userMenu.size() != 0) {
                                            url = "href=./leftMenu.do?TopMenuId=" + menu.getMenuId();
                                        } else {
                                            url = " class='simple'";
                                        }
                                    }
                                    if (menu.getLocked().equals("0")) {
                            %>
                            <%
                                topMenu_num++;
                                if (menu.getIcon().trim().length() == 0) {  //没有定义了图标
%>
                        <a <%=url%> class="top" title="<%=menu.getMenuName()%>"><%=menu.getMenuName()%>
                        </a>&nbsp;&nbsp;
                        <%
                        } else //定义了图标
                        {
                        %>
                        <!-- onMouseOver="menuMouseOver();" onMouseOut="menuMouseOut();" -->
                        <td background="./images/menu/frame_menu_bg.jpg" valign="top" height="20" >
                            <a <%=url%> class="top" title="<%=menu.getMenuName() %>" ><img   name="topMenuImage"  border="0" src="./images/menu/<%=menu.getIcon()%>"   onClick="menuClick();"/></a>
                        </td>

                        <%
                                    }
                                }
                            }
                        %>



            </tr>
        </table>
    </td>

<td valign="top"  background="./images/menu/frame_menu_bg.jpg" width="10%" ><div align="left"><a href="./logout.do" class="top">
            <!--  	退出系统  onMouseOver="menuMouseOver();" onMouseOut="menuMouseOut();"-->
            <image border="0" src="./images/menu/frame_exit_normal.gif"  onClick="menuClick();" />
        </a></div>
</td>
<%
    if (topMenu_num == 0) {
        topMenuWidth = new Integer(77).toString();
    } else {
        topMenuWidth = new Integer(topMenu_num * 77).toString();
    }

%>
</tr>
</table>
<input type="hidden" id="topMenuWidthOb" value="<%=topMenuWidth%>"/>
</body>

</html>

