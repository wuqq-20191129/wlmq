<%@ page language="java" contentType="text/html;charset=GBK" %>

<%@ page import="com.goldsign.frame.struts.BaseForwardAction,

         java.util.Vector,

         java.text.SimpleDateFormat,

         java.util.Date,

         java.util.Hashtable,

         com.goldsign.frame.util.FrameCharUtil,

         com.goldsign.frame.vo.Menu,


         com.goldsign.systemmonitor.dao.MenuDao"

         %>

<%





    MenuDao dao = new MenuDao();

    Vector list = new Vector();
    Vector userMenu = new Vector();









    try {
        //获得用户的顶菜单
        list = dao.getTopMenu();


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

        <title>::广州市轨道交通自动售检票系统--综合中央计算机系统::</title>

        <base target="contents">
        <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/jsp/css/css.css">
        <script language="JavaScript" charset="GBK" src="./js/topmenu.js"> </script>
        <script language="JavaScript" charset="GBK" src="./js/common_form_extend.js"> </script>
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
                <td width="174" height="83" background="./images/t05.jpg"><img src="./images//logo.jpg" width="174" height="83"></td>
                <td colspan="2">
                    <table width="100%"  border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td width="572" height="83" background="./images/t05.jpg"><img src="./images/top_tu.jpg" width="572" height="83"></td>
                            <td width="278" height="83" background="./images/t05.jpg"><img src="./images/top_tu3.jpg" width="278" height="83"></td>
                        </tr>
                    </table>
                </td>
                <td background="./images/t05.jpg">
                <td>
            </tr>
        </table>
        <table width="100% " border="0" cellspacing="0" cellpadding="0">
            <tr>
                <!--  background="./images//menu_bg.jpg"  -->
                <td background="./images/menu/menu_bg.jpg" align="left" valign="top" width="22%" height="33"><a class="simple" ><b>&nbsp;<%=sdf.format(new Date(System.currentTimeMillis()))%>
                            <font color="yellow"> &nbsp;欢迎您！</font> 


                        </b></a>
                </td>
                <!-- background="./images//menu_bg.jpg"-->
                <td  width="70%" height="33" background="./images/menu/menu_bg.jpg">
                    <table  border="0" id="menutableid" cellspacing="0" cellpadding="0" width="616" height="33">
                        <tr >

                            <!-- 	<td valign="bottom" height="20"> -->
                            <%
                                for (int i = 0; i < list.size(); i++) {
                                    menu = (Menu) list.elementAt(i);
                                    String url = "href=./leftMenu.do?TopMenuId=" + menu.getMenuId();



                                    if (menu.getLocked().equals("0")) {
                            %>
                            <%
                                topMenu_num++;
                                if (menu.getIcon().trim().length() == 0) {  //没有定义了图标
%>
                        <a <%=url%> class="top" title="<%=FrameCharUtil.IsoToGbk(menu.getMenuName())%>"><%=FrameCharUtil.IsoToGbk(menu.getMenuName())%>
                        </a>&nbsp;&nbsp;&nbsp;
                        <%
                        } else //定义了图标
                        {

                        %>

                        <td background="./images/menu/menu_bg.jpg" valign="top" height="20" >
                            <%
                                if (!menu.getPasswordFlag().equals("1")) {
                            %>
                            <a <%=url%> class="top" title="<%=FrameCharUtil.IsoToGbk(menu.getMenuName())%>" ><img   name="topMenuImage"  border="0" src="./images/menu/<%=menu.getIcon()%>"   onClick="menuClick();"/></a>
                                <%
                                } else {
                                %>
                            <a  class="top" title="<%=FrameCharUtil.IsoToGbk(menu.getMenuName())%>" ><img   name="topMenuImage"  border="0" src="./images/menu/<%=menu.getIcon()%>"   onClick="menuClick();showWindow('<%=menu.getMenuId()%>',200,200);"/></a>
                                <%

                                    }


                                %>









                        </td>

                        <%
                                    }
                                }
                            }
                        %>



            </tr>
        </table>
    </td>

<td valign="top"  background="./images/menu/menu_bg.jpg" width="10%" ><div align="left"><a href="./Logout.do" class="top">
            <!--  	退出系统  onMouseOver="menuMouseOver();" onMouseOut="menuMouseOut();"-->
            <image border="0" src="./images/menu/09_normal.gif"  onClick="menuClick();" />
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
<form name="formOp">
    <input type="hidden" name="validPassReturn" id="validPassReturn" value=""/>
    <input type="hidden" name="authenticated" id="authenticated" value=""/>
</form>

<input type="input" id="topMenuWidthOb" value="<%=topMenuWidth%>"/>
</body>

</html>

