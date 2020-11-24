<!DOCTYPE html>
<%@ page language="java" contentType="text/html;charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <!--<xsl:import href="common_variable.xsl"/>
    <xsl:import href="../../xsl/common_template.xsl"/>
    <xsl:import href="../../xsl/common_web_variable_template.xsl"/> -->
    <xsl:template match="/">
        <html>
            <script type="text/javascript">
                window.name = "curWindow";
            </script>
            <head>
                <title>修改密码</title>
                <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
                <meta http-equiv="X-UA-Compatible" content="IE=edge,Chrome=1" />
                <link href="././css/frame/style.css" rel="stylesheet" type="text/css"/>
                <script language="javascript" type="text/javascript" charset="utf-8" src="js/common_form.js"></script>
                <script language="javascript" type="text/javascript" charset="utf-8" src="js/Validator.js"></script>
            </head>

            <body onload="initDocument('passwordForm', 'detail');">



                <table width="95%" align="center" class="tableStyle">
                    <tr align="center" class="trTitle">
                        <td>ACC操作员修改密码
                        </td>
                    </tr>
                </table>


                <form id="passwordForm" action="StartChangePassword" method="post" name="passwordForm" target="curWindow">
                    <c:import  url="/jsp/common/common_template_web_variable.jsp?template_name=common_web_variable" />
                    <!-- 表头 通用模板 -->
                    <DIV id="detail" align="center" style="position:relative; left:20; width:95%; height:10; layer-background-color:#99CC99; border:thin outset #ffffff; z-index:1;">
                        <table width="95%" align="center" class="tableStyle">
                            <tr align="center" class="trContent" height="30">
                                <td width="11%"><div align="right">旧密码:</div></td>
                                <td width="15%"><div align="left"><input type="password" name="oldPassword" size="10"  /></div></td>
                            </tr>
                        </table>                    

                        <table width="95%" align="center" class="tableStyle">
                            <tr align="center" class="trContent" height="30">
                                <td width="11%"><div align="right">新密码:</div></td>
                                <td width="15%"><div align="left"><input type="password" name="password"  dataType="SafeString" maxLength="6" size="10" msg="密码应包含字母、数字和特殊符号的一种以上，不允许出现空间，6位的长度"/></div></td>
                            </tr>
                            <tr align="center" class="trContent" height="30">
                                <td width="11%"><div align="right">再输入新密码:</div></td>
                                <td width="15%"><div align="left"><input type="password" name="againPassword" maxLength="6" size="10" dataType="Repeat" to="password" msg="两次输入的密码不一致"/></div></td>
                            </tr>
                            <tr align="center" class="trContent" height="30">
                                <td width="11%">
                                    <div align="right">
                                        <!--
                                        <input class="button" type="submit" name="Submit" value="保存"/>-->
                                    </div>
                                </td>
                                <td width="15%">
                                    <%
                                        Object ob = request.getAttribute("Error");
                                        String message = "";
                                        if (ob != null) {
                                            message = (String) ob;
                                            out.println("<font color=\"red\" >");
                                            out.println("信息：" + message);
                                            out.println("</font>");
                                        } else {

                                        }
                                    %>                                        
                                </td>
                            </tr>
                        </table>
                                </DIV>
                       
                        <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all_init" />
                        <c:set var="modify1" scope="request" value="1"/>
                        <c:set var="save" scope="request" value="1"/>
                        <c:set var="cancle" scope="request" value="1"/>
                        <c:set var="export" scope="request" value="0"/>
                        <c:set var="clickMethod" scope="request" value="btnClick('passwordForm','','detail','');"/>
                        <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all" />

                      
                </form>
                       <c:import url="/jsp/common/common_template.jsp?template_name=common_status_table" />
            </body>
        </html>

    </xsl:template>
</xsl:stylesheet>

