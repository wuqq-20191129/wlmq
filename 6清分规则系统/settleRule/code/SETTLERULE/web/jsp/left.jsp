<%@ page language="java" contentType="text/html;charset=GBK" %>
<%@ page import="com.goldsign.frame.struts.BaseForwardAction ,java.util.Vector,java.util.Hashtable"%>
<%@ page import="com.goldsign.frame.vo.Menu,com.goldsign.frame.vo.User,com.goldsign.frame.bo.UserBo" %>
<%
//�ж��Ƿ�û�о���Struts,ֱ���������������ַ����
// String forwardedByAction = (String) request.getAttribute(BaseForwardAction.FORWARDED_BY_ACTION_KEY);
    String leftDays = (String) session.getAttribute("LeftDays");
    //if (forwardedByAction == null) {
    //    response.sendError(HttpServletResponse.SC_NOT_FOUND);
//     return;
// }
    UserBo newUserBo = new UserBo();
    Vector userMenu = new Vector();
//ȡ��¼��Ϣ���õ��û���Ϣ
    User user = (User) session.getAttribute("User");
//ȡҪ�����Ķ��˵�
    String topMenuId = (String) request.getParameter("TopMenuId");
    //System.out.println(topMenuId);
    String topMenuName = "";
    Vector topList = null;


//�û���½ʱ,Ĭ�϶��˵�ΪNULL,���û�ӵ�еĶ��˵���ȡ��һ��ΪĬ��
    if (topMenuId == null) {
        topList = newUserBo.getTopMenu(user.getAccount());
        if (!topList.isEmpty()) {
            topMenuId = ((Menu) topList.get(0)).getMenuId();
            topMenuName = ((Menu) topList.get(0)).getMenuName();
        }
        //Ĭ��ѡ��
//	topMenuId="01";

    }
    Hashtable topMenuTable = new Hashtable();
    topMenuTable = (Hashtable) session.getAttribute("topMenuTable");
    try {
        if (topMenuName.length() == 0) {
            topMenuName = (String) topMenuTable.get(topMenuId);
        }
    } catch (Exception e) {
    }
//out.println("topMenuName=" + topMenuName);
    if (!(user == null)) {
        userMenu = newUserBo.getUserMenu(user.getAccount(), topMenuId);// ����û����˵���Ӧ��������˵�
    }
    Menu menu = null;
    Vector scriptName = new Vector();
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=gb2312">
        <title>::��ɳ�й����ͨ��ֹ���ϵͳ--�ۺ���������ϵͳ::</title>
        <base target="main">
        <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/css.css">
        <!--
        <script language="javascript" src="./js/clock.js"></script>
        <script language="javascript" src="./js/common.js"></script>
        -->
        <script language="JavaScript" charset="UTF-8" src="./js/ick.js"> </script>
        <script language="JavaScript" charset="GBK" src="./js/leftmenu.js"> </script>
        <SCRIPT language=javascript>
            function showLeftDays(){
                var leftDays = document.getElementById("leftDays");
                if(leftDays.value =="-1")
                    return;
                else{
                    if(leftDays.value !=null)
                        alert("�ʻ���Ч�ڻ���"+leftDays.value+"��");
                    leftDays.value ="-1";
                }
            }
            function freshView(){
                alert("fresh left view");

            }

        </SCRIPT>
    </head>
    <!-- <body bgColor=#ffffff leftMargin=0 topMargin=0 marginheight=&quot;0&quot;marginwidth=&quot;0&quot onLoad="ob(),setNum(),setInterval('timer()',100);setInterval('runClock()',100)"> -->
    <body bgColor=#ffffff leftMargin=0 topMargin=0 marginheight="0" marginwidth="0"   background="images/menu/frame_left_bg.jpg" onLoad="showLeftDays();">
        <input type="hidden" name="leftDays" id="leftDays"  value=<%=leftDays%> />
        <%
            session.setAttribute("LeftDays", "-1");
        %>
        <div id="selectedSubMenu" value="init"></div>
        <table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0">
            <tr>
                <td height="100%" valign="top" background="images/menu/frame_left_bg.jpg">
                    <table width="95%"  border="0" align="center" cellpadding="0" cellspacing="5">
                        <tr>
                         <!-- <td colspan=2 align="left">&nbsp;&nbsp;&nbsp;<font>==<%=topMenuName %>==</font></td> -->
                        </tr>
                        <tr>
                            <td colspan=2>
                                <%
                                    String sMenuId = "";
                                    String pMenuId = "";
                                    int size = 0;
                                    String pMenuIds = "";
                                   
                                    
                                    while (size < userMenu.size()) {
                                        menu = (Menu) userMenu.elementAt(size);
                                        if(menu.getMenuId().length()==2){
                                            menu = (Menu) userMenu.elementAt(++size);
                                           }
                                        
                                        //���˵�(�����˵���
                                        if (menu.getTopMenuId().equals(menu.getParentId())  ) {
                                            
                                            if(menu.getUrl().equals("")){
                                            size++;
                                            pMenuId = menu.getMenuId();
                                %>
                                <table width="100%" style="cursor:hand" onClick="openMenu('p<%=menu.getMenuId()%>');"  border=0 cellpadding=0 cellspacing=0 >
                                    <tr>
                                        <%
                                                    if (menu.getIcon().trim().length() == 0) { //ģ��û�ж���ͼ��
%>
                                        <td width="15"><img src="images/frame_left_node.gif" width="12" height="12"></td>
                                        <td width="115" nowrap height="18" title="<%=menu.getMenuName()%>">
                                            <font style="font-size:10pt;color:#CC0000;text-decoration:"><%=menu.getMenuName()%></font>
                                        </td>

                                        <%
                                        } else //ģ�鶨����ͼ��
                                        {
                                        %>
                                        <td width="20"><img id="i<%=menu.getMenuId()%>" src="images/menu/frame_node.gif" ></td>
                                        <td  nowrap height="30" title="<%=menu.getMenuName()%>">
                                            <img src="images/menu/<%=menu.getMenuId()%>.gif" border="0"/>
                                        </td>
                                        <%
                                            }
                                        %>
                                    </tr>
                                </table>
                                <%
                                    scriptName.add(menu.getMenuId());
                                    pMenuIds += pMenuId + "#";
                                    
                                    }//����if
                                     else{
                                         %>
                                         <div id="p<%=menu.getParentId()%>"  >
                                    <!--  bgcolor="#cccccc" -->
                                    <table width="100%" border="0" cellpadding=1 cellspacing=1 >
                                        <%
                                            int i = 0;
                                            //�ҳ������˵���Ӧ�����������˵��򵥶��������˵�
                                            while (menu.getParentId().equals(menu.getTopMenuId())) {
                                                String url = "";
                                                if (menu.getUrl().equals("")) {
                                                    url = "./jsp/welcome.jsp";
                                                } else {
                                                    url = menu.getUrl();
                                                    if (url.indexOf("?") == -1) {
                                                        url = url + "?ModuleID=" + menu.getMenuId();
                                                    } else {
                                                        url = url + "&ModuleID=" + menu.getMenuId();
                                                    }
                                                }
                                                //�жϴ��Ӳ˵��Ƿ�������LOCKED=1��
                                                if (!menu.getLocked().equals("1")) {
                                        %>
                                        <tr>
                                            <%
                                          if (menu.getIcon().trim().length() == 0) { //ģ��û�ж�����ͼ��
%>
                                            <td width="20"><div align="right"><img src="images/d.gif" width="8" height="3"></div></td>
                                            <td   height="18"  id="m<%=menu.getMenuId()%>" title="<%=menu.getMenuName()%>">
                                                <a href="<%=url%>"  class="left2" onclick="setSubMenuColor();">
                                                    <font color="rgb(128,0,255)">
                                                    <%=menu.getMenuName()%>
                                                    </font>
                                                </a>
                                            </td>
                                            <%
                                            } else //ģ�鶨����ͼ��
                                            {
                                            %>

                                          
                                            <td width="20"><img id="i<%=menu.getMenuId()%>" src="images/menu/frame_node.gif" ></td>
                                            <td   height="20"   id="sub<%=menu.getParentId()%>" title="<%=menu.getMenuName()%>"  >
                                                <a href="<%=url%>"  class="left2" onFocus="changTDBgColor('sub<%=menu.getParentId()%>');" >
                                                    <img src="images/menu/<%=menu.getIcon()%>"  border="0" />
                                                </a>
                                            </td>

                                            <%
                                                }
                                            %>
                                        </tr>
                                        <%
                                                }
                                                size++;
                                                if (size >= userMenu.size()) {
                                                    break;
                                                }
                                                if (size < userMenu.size()) {
                                                    menu = (Menu) userMenu.elementAt(size);
                                                }
                                            }//����while �Ӳ˵�
%>
                                    </table>
                                </div>
                                         
                                         
                                         <%
                                   }//����else
                                    
                                }//if
                                //�Ӳ˵��������˵���
                                else {
                                    // scriptName.add(menu.getParentId());
%>
                                <div id="p<%=menu.getParentId()%>" style="display:none" >
                                    <!--  bgcolor="#cccccc" -->
                                    <table width="100%" border="0" cellpadding=1 cellspacing=1 >
                                        <%
                                            int i = 0;
                                            
                                            
                                            //�ҳ������˵���Ӧ�����������˵��򵥶��������˵�
                                            while (!menu.getParentId().equals(menu.getTopMenuId())) {
                                                String url = "";
                                                if (menu.getUrl().equals("")) {
                                                    url = "./jsp/welcome.jsp";
                                                } else {
                                                    url = menu.getUrl();
                                                    if (url.indexOf("?") == -1) {
                                                        url = url + "?ModuleID=" + menu.getMenuId();
                                                    } else {
                                                        url = url + "&ModuleID=" + menu.getMenuId();
                                                    }
                                                }
                                                //�жϴ��Ӳ˵��Ƿ�������LOCKED=1��
                                                if (!menu.getLocked().equals("1")) {
                                        %>
                                        <tr>
                                            <%
                                          if (menu.getIcon().trim().length() == 0) { //ģ��û�ж�����ͼ��
%>
                                            <td width="20"><div align="right"><img src="images/d.gif" width="8" height="3"></div></td>
                                            <td   height="18"  id="m<%=menu.getMenuId()%>" title="<%=menu.getMenuName()%>">
                                                <a href="<%=url%>"  class="left2" onclick="setSubMenuColor();">
                                                    <font color="rgb(128,0,255)">
                                                    <%=menu.getMenuName()%>
                                                    </font>
                                                </a>
                                            </td>
                                            <%
                                            } else //ģ�鶨����ͼ��
                                            {
                                            %>

                                            <td width="20"></td>
                                            <td   height="20"   id="sub<%=menu.getParentId()%>" title="<%=menu.getMenuName()%>"  >
                                                <a href="<%=url%>"  class="left2" onFocus="changTDBgColor('sub<%=menu.getParentId()%>');" >
                                                    <img src="images/menu/<%=menu.getIcon()%>"  border="0" />
                                                </a>
                                            </td>

                                            <%
                                                }
                                            %>
                                        </tr>
                                        <%
                                                }
                                                size++;
                                                if (size >= userMenu.size()) {
                                                    break;
                                                }
                                                if (size < userMenu.size()) {
                                                    menu = (Menu) userMenu.elementAt(size);
                                                }
                                            }//while �Ӳ˵�
%>
                                    </table>
                                </div>

                                <%
                                        }//else �Ӳ˵�
                                                                                
                                    }//while
%>
                            </td>
                        </tr>
                    </table>
                </td>
            </tr>
        </table>
        <input type="hidden" id="pMenuIdsCtr" value="<%=pMenuIds%>"/>
    </body>

</html>
<script language="JavaScript">
    parent.main.location.href="<%=request.getContextPath()%>/jsp/homepage.jsp?topMenuName=<%=topMenuName%>&topMenuId=<%=topMenuId%>";
</script>


