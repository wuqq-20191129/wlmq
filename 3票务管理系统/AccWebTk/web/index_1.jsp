
<%@ page language="java" contentType="text/html;charset=GBK"
         import="com.goldsign.acc.frame.controller.VersionController"
         %>

<%
    Object ob = session.getAttribute("LoginMsg");
    String loginMsg = "";

    Object versionOb = request.getAttribute("version");
    String version = "";
    if (versionOb != null) {
        version = (String) versionOb;
    }
    //new VersionController().getMaxVersion();
    // VersionController verCtr =
    // verCtr.getMaxVersion();
    /*
    UserDao dao = new UserDao();

    if (ob != null) {
        loginMsg = (String) ob;
    }
    version = dao.getMaxVersion();
     */

%>

<html>
    <head>
        <style type="text/css">
            td{ FONT-SIZE: 10pt; }
        </style>

        <meta http-equiv="Content-Type" content="text/html; charset=GBK">
        <title>::���й����ͨ�Զ��ۼ�Ʊϵͳ--��Ӫ���Ĺ���ϵͳ::</title>
        <link href="css.css" rel="stylesheet" type="text/css">
        <style type="text/css">
            <!--
            .style14 {color: #00FFFF}
            .style16 {color: #006599}
            -->
        </style>
        <script language="JavaScript">
            function setOperatorID() {

                var c1 = document.cookie;
                if (c1 == null || c1 == "")
                    return;
                //   alert(c1);
                var idx = c1.indexOf("OperatorID");
                var idx1 = -1;
                var operatorID = "";
                if (idx == -1)
                    return;
                idx = c1.indexOf("=", idx + 1);
                idx1 = c1.indexOf(";", idx + 1);
                if (idx1 == -1)
                    operatorID = c1.substring(idx + 1);
                else
                    operatorID = c1.substring(idx + 1, idx1);
                document.forms["loginForm"].all["Account"].value = operatorID;
            }
        </script>
    </head>


    <body  leftMargin=0 topMargin=0 marginheight="0" marginwidth="0" onLoad="setOperatorID();" scroll="no" background="images/frame_loginbg.jpg">
        <div align="center" >
            <table width="98%" height="98%" border="0"  cellspacing="0" cellpadding="0">

                <tr height="25%">
                    <td>&nbsp;</td>
                </tr>

                <tr height="50%">
                    <td>
                        <FORM METHOD=POST ACTION="login" name="loginForm">
                            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                <tr>
                                    <td width="70" height="250"><img src="images/frame_login_t01.jpg" width="70" height="250"></td>
                                    <td width="337"  height="250"><img src="images/frame_login_t02.jpg" width="337" height="250"></td>
                                    <td valign="top" background="images/frame_top_bg.jpg">
                                        <table width="100%"  border="0" cellspacing="0" cellpadding="0">
                                            <tr>
                                                <td><img src="images/frame_login_t03.jpg" width="246" height="146"></td>
                                            </tr>
                                            <tr>
                                                <td height="95">
                                                    <table width="90%"  border="0" cellspacing="3" cellpadding="0">
                                                        <tr valign="bottom">
                                                            <td width="9%" class="style14"><div align="center">�� ��</div></td>
                                                            <td width="19%"><span class="dl"><FONT color=#ffffff>
                                                                    <input type="text" name="Account"  size="10" maxlength="16" style="border: 1 solid #FFCC00"; border-style: solid; border-color: #ffcc00">
                                                                           </FONT></span>
                                                            </td>
                                                            <td width="22%" class="style14">&nbsp;</td>
                                                            <td width="50%"><span class="dl"><FONT color=#ffffff></FONT></span>
                                                            </td>
                                                        </tr>
                                                        <tr valign="bottom">
                                                            <td class="style14"><div align="center">�� ��</div></td>
                                                            <td><span class="dl">
                                                                    <FONT color=#ffffff>
                                                                    <input type="password" name="Password"  size="10" maxlength="16" style="border: 1 solid #FFCC00"; border-style: solid; border-color: #ffcc00">
                                                                           </FONT></span>
                                                            </td>
                                                            <td class="style14"><input type="hidden" name="Flag" value="0" visible=false size="1"><span class="dl">
                                                                    <input type="hidden" name="sys_flag" value="03" />
                                                                    <input type="submit" name="Submit" value="��¼" >
                                                                    <input type="button" name="close" value="ȡ��" onClick="window.close();">
                                                                </span>
                                                            </td>
                                                            <td>&nbsp;</td>
                                                        </tr>
                                                        <tr valign="bottom">
                                                            <td colspan="4">
                                                                <font color="red" >   								
                                                                <%=loginMsg%>                                                                                  
                                                                </font>
                                                            </td>
                                                        </tr>
                                                    </table>
                                                </td>
                                            </tr>
                                        </table>
                                    </td>
                                    <td background="images/frame_top_bg.jpg">&nbsp;</td>
                                </tr>
                                <tr height="30">
                                    <td colspan="2" valign="bottom" class="down">
                                        �����ʾ���ֱ���1280x1024 ֧��IE x.x���ϰ汾
                                    </td>
                                </tr>
                                <tr height="30">
                                    <td colspan="2" valign="bottom" class="down">
                                        ����汾<%=version%>
                                    </td>
                                </tr>
                            </table>
                        </FORM>

                    </td>
                </tr>

                <tr height="25%">
                    <td>
                    </td>
                </tr>
            </table>
        </div>
    </body>
</html>


<SCRIPT LANGUAGE="JavaScript">
    <!--
    function openFullWindow() {
        window.open("./login.do", "", "'fullscreen=yes,scrollbars=auto")
    }
    //-->
</SCRIPT>
