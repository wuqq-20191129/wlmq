<%@ page language="java" contentType="text/html;charset=GBK"%>
<%@ page
    import="java.util.Vector,java.util.Hashtable,com.goldsign.frame.util.FrameCharUtil"%>
    <%@ page
        import="com.goldsign.frame.vo.Menu,com.goldsign.systemmonitor.dao.MenuDao"%>
        <%@ page import="com.goldsign.frame.constant.FrameDBConstant"%>
        <%
            //�ж��Ƿ�û�о���Struts,ֱ���������������ַ����
            // String forwardedByAction = (String) request.getAttribute(BaseForwardAction.FORWARDED_BY_ACTION_KEY);


            MenuDao dao = new MenuDao();
            Vector userMenu = new Vector();
            //ȡ��¼��Ϣ���õ��û���Ϣ
            //User user=(User)session.getAttribute("User");
            //ȡҪ�����Ķ��˵�
            String topMenuId = (String) request.getParameter("TopMenuId");
            String openDiv = request.getParameter("openDiv");
            String selectedTd = request.getParameter("selectedTd");

            String topMenuName = "";
            Vector topList = null;
            String isNeedRefresh = "0";
            String win = "main";


            //�û���½ʱ,Ĭ�϶��˵�ΪNULL,���û�ӵ�еĶ��˵���ȡ��һ��ΪĬ��
            if (topMenuId == null) {
                topList = dao.getTopMenu();
                if (!topList.isEmpty()) {
                    topMenuId = ((Menu) topList.get(1)).getMenuId();
                    topMenuName = ((Menu) topList.get(1)).getMenuName();
                }
                //Ĭ��ѡ��
                	topMenuId="02";

            }
            
            if (!topMenuId.equals("01")) {
                isNeedRefresh = "02";
            }
 
            Hashtable topMenuTable = new Hashtable();
            topMenuTable = (Hashtable) session.getAttribute("topMenuTable");
            try {
                if (topMenuName.length() == 0 && topMenuTable != null) {
                    topMenuName = (String) topMenuTable.get(topMenuId);
                }
                userMenu = dao.getLeftMenu(topMenuId);// ����û����˵���Ӧ��������˵�
                //System.out.println("userMenu="+userMenu.size());
            } catch (Exception e) {
                e.printStackTrace();
            }


            Menu menu = null;
            Vector scriptName = new Vector();
        %>
        <html>
            <head>
                <meta http-equiv="Content-Type" content="text/html; charset=gb2312">
                <title>::�����й����ͨ�Զ��ۼ�Ʊϵͳ--ϵͳ����ƽ̨::</title>
                <base target="main">
                <link rel="stylesheet" type="text/css"
                      href="<%=request.getContextPath()%>/jsp/css/css.css">

                <script language="javascript" src="./js/common_form.js"></script>
                <script language="JavaScript" charset="GBK" src="./js/leftmenu.js"> </script>

            </head>
            <!-- <body bgColor=#ffffff leftMargin=0 topMargin=0 marginheight=&quot;0&quot;marginwidth=&quot;0&quot onLoad="ob(),setNum(),setInterval('timer()',100);setInterval('runClock()',100)"> -->
            <body bgColor=#ffffff leftMargin=0 topMargin=0 marginheight="0"
                  marginwidth="0" background="images/menu/left_bg.jpg"
                  onLoad="freshLeftMenu('formOp','<%=isNeedRefresh%>');
                      openMenu('<%=openDiv%>');
                      setSubMenuColorForRefresh('<%=selectedTd%>');
                  ">

                <div id="selectedSubMenu" value="init"></div>
                <table width="100%" height="100%" border="0" cellpadding="0"
                       cellspacing="0">
                    <tr>
                        <td height="500" valign="top" background="images/menu/left_bg.jpg">
                            <table width="95%" border="0" align="center" cellpadding="0"
                                   cellspacing="5">
                                <tr>
                                        <!-- <td colspan=2 align="left">&nbsp;&nbsp;&nbsp;<font>==<%=FrameCharUtil.IsoToGbk(topMenuName)%>==</font></td> -->
                                </tr>
                                <tr>
                                    <td colspan=2><%
                                        String sMenuId = "";
                                        String pMenuId = "";
                                        int size = 0;
                                        String pMenuIds = "";
                                        while (size < userMenu.size()) {
                                            menu = (Menu) userMenu.elementAt(size);
                                            //���˵�(�����˵���
                                            if (menu.getTopMenuId().equals(menu.getParentId())) {
                                                size++;
                                                pMenuId = menu.getMenuId();
                                        %>
                                        <table width="100%" style="cursor:hand"
                                               onClick="openMenu('p<%=menu.getMenuId()%>');" border=0
                                               cellpadding=0 cellspacing=0>
                                            <tr>
                                                <%
                                                    if (menu.getIcon().trim().length() == 0) { //ģ��û�ж���ͼ��
                                                %>
                                                <td width="15"><img src="images/jian.gif" width="12" height="12"></td>
                                                <td width="115" nowrap height="18"
                                                    title="<%=FrameCharUtil.IsoToGbk(menu.getMenuName())%>"><%
                                                        //System.out.println(CharUtil.IsoToGbk(menu.getMenuName())+":"+menu.getStatus());
                                                        if (menu.getStatus().equals(FrameDBConstant.Status_normal)) {
                                                    %> <font style="font-size:12pt;color:white;text-decoration:"> <%                                                          } else {
                                                    %> <font style="font-size:12pt;color:red;text-decoration:"> <%                                                             }
                                                    %> <b><%=FrameCharUtil.IsoToGbk(menu.getMenuName())%> </b></font></td>

                                                <%
                                                } else //ģ�鶨����ͼ��
                                                {
                                                %>
                                                <td width="20"><img id="i<%=menu.getMenuId()%>"
                                                                    src="images/menu/node_close.gif"></td>
                                                <td nowrap height="30"
                                                    title="<%=FrameCharUtil.IsoToGbk(menu.getMenuName())%>"><img
                                                        src="images/menu/<%=menu.getMenuId()%>.gif" border="0" /></td>
                                                    <%
                                                        }
                                                    %>
                                            </tr>
                                        </table>
                                        <%
                                            scriptName.add(menu.getMenuId());
                                            pMenuIds += pMenuId + "#";
                                        }//if
                                        //�Ӳ˵��������˵���
                                        else {
                                            // scriptName.add(menu.getParentId());
                                        %>
                                        <div id="p<%=menu.getParentId()%>" style="display:none"><!--  bgcolor="#cccccc" -->
                                            <table width="100%" border="0" cellpadding=1 cellspacing=1>
                                                <%
                                                    int i = 0;
                                                    //�ҳ������˵���Ӧ�����������˵��򵥶��������˵�
                                                    while (!menu.getParentId().equals(menu.getTopMenuId())) {
                                                        String url = "";
                                                        if (menu.getNewWindowFlag().equals("1")) {
                                                            win = "_blank";
                                                        } else {
                                                            win = "main";
                                                        }
                                                        if (menu.getUrl().equals("")) {
                                                            url = "./jsp/welcome.jsp";
                                                        } else {
                                                            url = menu.getUrl();
                                                            if (url.indexOf("http") == -1) {
                                                                if (url.indexOf("?") == -1) {
                                                                    url = url + "?ModuleID=" + menu.getMenuId();
                                                                } else {
                                                                    url = url + "&ModuleID=" + menu.getMenuId();
                                                                }

                                                            }


                                                        }
                                                        //�жϴ��Ӳ˵��Ƿ�������LOCKED=1��
                                                        if (!menu.getLocked().equals("1")) {
                                                %>
                                                <tr>
                                                    <%
                                                        if (menu.getIcon().trim().length() == 0) { //ģ��û�ж�����ͼ��
                                                    %>
                                                    <td width="20">
                                                        <div align="right"><img src="images/d.gif" width="8" height="3"></div>
                                                    </td>
                                                    <td height="18" id="m<%=menu.getMenuId()%>"
                                                        title="<%=FrameCharUtil.IsoToGbk(menu.getMenuName())%>"><a
                                                            href="<%=url%>" class="left2" onclick="setSubMenuColor();" 
                                                            target="<%=win%>"
                                                            > 
                                                            <%
                                                                if (menu.getStatus().equals(FrameDBConstant.Status_normal)) {
                                                            %> <font style="font-size:11pt;color:yellow;text-decoration:">

                                                            <%                                                } else {
                                                            %> <font style="font-size:11pt;color:red;text-decoration:"> <%                                                             }
                                                            %> <b>
                                                                <div><%=FrameCharUtil.IsoToGbk(menu.getMenuName())%></div>
                                                            </b> </font></a></td>
                                                            <%
                                                            } else //ģ�鶨����ͼ��
                                                            {
                                                            %>

                                                    <td width="20"></td>
                                                    <td height="20" id="sub<%=menu.getParentId()%>"
                                                        title="<%=FrameCharUtil.IsoToGbk(menu.getMenuName())%>"><a
                                                            href="<%=url%>" class="left2"
                                                            target="<%=win%>"
                                                            onFocus="changTDBgColor('sub<%=menu.getParentId()%>');"> <img
                                                                src="images/menu/<%=menu.getIcon()%>" border="0" /> </a></td>

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
                                        %></td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                </table>
                <input type="hidden" id="pMenuIdsCtr" value="<%=pMenuIds%>" />
                <form name="formOp" method="post" id="formOp" target="contents"
                      action="./leftMenu.do?TopMenuId=<%=topMenuId%>">
                    <input type="hidden" name="openDiv" id="openDiv" value="" />
                    <input type="hidden" name="selectedTd" id="selectedTd" value="<%=selectedTd%>" />
                    <input type="hidden" name="topMenuName" id="topMenuName" value="<%=FrameCharUtil.IsoToGbk(topMenuName)%>"/>
                    <input type="hidden" name="topMenuId" id="topMenuId" value="<%=topMenuId%>" />


                </form>
            </body>

        </html>
        <script language="JavaScript">
            refreshMain();
            //parent.main.location.href="/sysmonitor/jsp/homepage.jsp?topMenuName=<%=FrameCharUtil.IsoToGbk(topMenuName)%>&topMenuId=<%=topMenuId%>";
	
        </script>


