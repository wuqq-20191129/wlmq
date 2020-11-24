<?xml version="1.0" encoding="GBK"?>

<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <!--<xsl:import href="common_variable.xsl"/> -->
    <xsl:import href="common_template.xsl"/>
    <xsl:import href="common_web_variable_template.xsl"/>
    <xsl:template match="/">
        <html>

            <head>
                <title>�޸�����</title>
                <link rel="stylesheet" type="text/css" href="./xsl/css/simple.css" title="Style"/>
                <script language="javascript" type="text/javascript" charset="UTF-8" src="./js/common_form.js"></script>
                <script language="javascript" type="text/javascript" charset="UTF-8" src="./js/Validator.js"></script>
            </head>

            <body onload="initDocument('passwordOp','detail');">
                <form name="passwordOp" method="post" action="password.do">
                    <xsl:call-template name="common_web_variable" />
                    <table width="95%" align="center" class="tableStyle">
                        <tr align="center" class="trTitle">
                            <td>�޸�����
                            </td>
                        </tr>
                    </table>

                    <!-- ��ͷ ͨ��ģ�� -->
                    <DIV id="detail" align="center" style="position:relative; left:20; width:95%; height:10; layer-background-color:#99CC99; border:thin outset #ffffff; z-index:1;">

                        <xsl:call-template name="common_table_head">
                            <xsl:with-param name="tbName" select="string('������')"/>
                        </xsl:call-template>

                        <table width="95%" align="center" class="tableStyle">
                            <tr align="center" class="trContent" height="30">
                                <td width="11%">
                                    <div align="right">������:</div>
                                </td>
                                <td width="15%">
                                    <div align="left">
                                        <input type="password" name="password" maxLength="6" size="10"  />
                                    </div>
                                </td>
                            </tr>
                        </table>
                        <!-- ��ͷ ͨ��ģ�� -->

                        <xsl:call-template name="common_table_head">
                            <xsl:with-param name="tbName" select="string('������')"/>
                        </xsl:call-template>

                        <table width="95%" align="center" class="tableStyle">
                            <tr align="center" class="trContent" height="30">
                                <td width="11%">
                                    <div align="right">������:</div>
                                </td>
                                <td width="15%">
                                    <div align="left">
                                        <input type="password" name="passwordNew" dataType="SafeString|LimitB" size="10" min="6" max="6"  maxLength="6" msg="������Ӧ������ĸ�����ֺ�������ŵ�һ�����ϣ���������ֿո�����Ҫ6λ�ĳ���"/>
                                    </div>
                                </td>
                            </tr>
                            <tr align="center" class="trContent" height="30">
                                <td width="11%">
                                    <div align="right">������������:</div>
                                </td>
                                <td width="15%">
                                    <div align="left">
                                        <input type="password" name="passwordConfirm" size="10" dataType="Repeat|LimitB" min="6" max="6" maxLength="6" to="passwordNew" msg="��������������벻һ��"/>
                                    </div>
                                </td>
                            </tr>
                        </table>
	
                    </DIV>


                    <!-- �������б�� ͨ��ģ�� -->

                    <xsl:call-template name="op_button_list_gen">
                        <xsl:with-param name="modify1" select="1"/>
                        <xsl:with-param name="save" select="1"/>
                        <xsl:with-param name="cancle" select="1"/>
                        <xsl:with-param name="export" select="0"/>
                        <xsl:with-param name="clickMethod" select="'btnClick(&quot;passwordOp&quot;,&quot;&quot;,&quot;detail&quot;);'"/>	
                    </xsl:call-template>

                </form>

                <!-- ״̬�� ͨ��ģ�� -->
                <xsl:call-template name="common_status_table">
                </xsl:call-template>


            </body>
        </html>

    </xsl:template>
</xsl:stylesheet>

