<?xml version="1.0" encoding="GBK"?>

<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <!--<xsl:import href="common_variable.xsl"/> -->
    <xsl:import href="common_template.xsl"/>
    <xsl:import href="common_web_variable_template.xsl"/>
    <xsl:template match="/">
        <html>

            <head>
                <title>修改密码</title>
                <link rel="stylesheet" type="text/css" href="./xsl/css/simple.css" title="Style"/>
                <script language="javascript" type="text/javascript" charset="UTF-8" src="./js/common_form.js"></script>
                <script language="javascript" type="text/javascript" charset="UTF-8" src="./js/Validator.js"></script>
            </head>

            <body onload="initDocument('passwordOp','detail');">
                <form name="passwordOp" method="post" action="password.do">
                    <xsl:call-template name="common_web_variable" />
                    <table width="95%" align="center" class="tableStyle">
                        <tr align="center" class="trTitle">
                            <td>修改密码
                            </td>
                        </tr>
                    </table>

                    <!-- 表头 通用模板 -->
                    <DIV id="detail" align="center" style="position:relative; left:20; width:95%; height:10; layer-background-color:#99CC99; border:thin outset #ffffff; z-index:1;">

                        <xsl:call-template name="common_table_head">
                            <xsl:with-param name="tbName" select="string('旧密码')"/>
                        </xsl:call-template>

                        <table width="95%" align="center" class="tableStyle">
                            <tr align="center" class="trContent" height="30">
                                <td width="11%">
                                    <div align="right">旧密码:</div>
                                </td>
                                <td width="15%">
                                    <div align="left">
                                        <input type="password" name="password" maxLength="6" size="10"  />
                                    </div>
                                </td>
                            </tr>
                        </table>
                        <!-- 表头 通用模板 -->

                        <xsl:call-template name="common_table_head">
                            <xsl:with-param name="tbName" select="string('新密码')"/>
                        </xsl:call-template>

                        <table width="95%" align="center" class="tableStyle">
                            <tr align="center" class="trContent" height="30">
                                <td width="11%">
                                    <div align="right">新密码:</div>
                                </td>
                                <td width="15%">
                                    <div align="left">
                                        <input type="password" name="passwordNew" dataType="SafeString|LimitB" size="10" min="6" max="6"  maxLength="6" msg="新密码应包含字母、数字和特殊符号的一种以上，不允许出现空隔，需要6位的长度"/>
                                    </div>
                                </td>
                            </tr>
                            <tr align="center" class="trContent" height="30">
                                <td width="11%">
                                    <div align="right">再输入新密码:</div>
                                </td>
                                <td width="15%">
                                    <div align="left">
                                        <input type="password" name="passwordConfirm" size="10" dataType="Repeat|LimitB" min="6" max="6" maxLength="6" to="passwordNew" msg="两次输入的新密码不一致"/>
                                    </div>
                                </td>
                            </tr>
                        </table>
	
                    </DIV>


                    <!-- 插入结果列表框 通用模板 -->

                    <xsl:call-template name="op_button_list_gen">
                        <xsl:with-param name="modify1" select="1"/>
                        <xsl:with-param name="save" select="1"/>
                        <xsl:with-param name="cancle" select="1"/>
                        <xsl:with-param name="export" select="0"/>
                        <xsl:with-param name="clickMethod" select="'btnClick(&quot;passwordOp&quot;,&quot;&quot;,&quot;detail&quot;);'"/>	
                    </xsl:call-template>

                </form>

                <!-- 状态栏 通用模板 -->
                <xsl:call-template name="common_status_table">
                </xsl:call-template>


            </body>
        </html>

    </xsl:template>
</xsl:stylesheet>

