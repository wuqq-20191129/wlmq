<?xml version="1.0" encoding="GBK"?>

<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:import href="common_template.xsl" />
    <xsl:import href="common_web_variable_template.xsl" />
    
    <xsl:template match="/">

        <html>
            <head>
                <title>OD·��������ϸ</title>
                <link rel="stylesheet" type="text/css" href="./xsl/css/simple.css" title="Style" />
		<script language="javascript" type="text/javascript" charset="UTF-8" src="./js/common_form.js"></script>
                <script language="javascript" type="text/javascript" charset="UTF-8" src="./js/common_form_extend.js"></script>
            </head>

            <body onload="initDocument('formOp','detail');">
                
                <table width="95%" align="center" class="tableStyle">
                    <tr align="center" class="trTitle">
                        <td colspan="2"><div id = "menuTitle">OD·��������ϸ</div></td>
                    </tr>
                </table>
                <br/>
                
                <!-- ��ͷ ͨ��ģ�� -->
                <xsl:call-template name="common_table_head">
                    <xsl:with-param name="tbName" select="string('�б�')" />
                </xsl:call-template>
                
                <!-- �б����� -->
                <DIV id="clearStart" align="center" 
                     style="position:relative; left:20; width:95%; height:50%; layer-background-color:#99CC99; border:thin outset #ffffff; z-index:1; overflow: scroll;">
                    
                    <table class="listDataTable" id="groupTable" width="100%" align="left">
                        
                        <tr class="trDataHead" id="ignore">
                            <td align="center" nowrap="1" width="15%" class="listTableHead"
                                    onclick="JM_PowerListByIDs(this.parentElement.parentElement.parentElement.parentElement,this.parentElement.parentElement.parentElement,0)"
                                    onyes="head">·��ID
                            </td>
                            <td align="center" nowrap="1" width="15%" class="listTableHead"
                                    onclick="JM_PowerListByIDs(this.parentElement.parentElement.parentElement.parentElement,this.parentElement.parentElement.parentElement,1)"
                                    onyes="head">��ʼվ��
                            </td>
                            <td align="center" nowrap="1" width="15%" class="listTableHead"
                                    onclick="JM_PowerListByIDs(this.parentElement.parentElement.parentElement.parentElement,this.parentElement.parentElement.parentElement,2)"
                                    onyes="head">ת����·
                            </td>
                            <td align="center" nowrap="1" width="15%" class="listTableHead"
                                    onclick="JM_PowerListByIDs(this.parentElement.parentElement.parentElement.parentElement,this.parentElement.parentElement.parentElement,3)"
                                    onyes="head">����վ��
                            </td>
                            <td align="center" nowrap="1" width="15%" class="listTableHead"
                                    onclick="JM_PowerListByIDs(this.parentElement.parentElement.parentElement.parentElement,this.parentElement.parentElement.parentElement,4)"
                                    onyes="head">ת����·
                            </td>
                            <td align="center" nowrap="1" width="15%" class="listTableHead"
                                    onclick="JM_PowerListByIDs(this.parentElement.parentElement.parentElement.parentElement,this.parentElement.parentElement.parentElement,5)"
                                    onyes="head">ת����·�˾�
                            </td>
                        </tr>
                        
                        <xsl:for-each select="/Service/Result/distanceDetail/distanceChangeVo">
                        <tr optype="" modified="" onyes="detail" bgColor="#fdfdfd" style="display:" 
                            onMouseOver="overResultRow('formOp',this);" onMouseOut="outResultRow('formOp',this);" >
                            <td align="center" id="id" nowrap="1"> <xsl:value-of select="id" /> </td>
                            <td align="center" id="pChangeStationId" nowrap="1"> <xsl:value-of select="pChangeStationIdText" /> </td>
                            <td align="center" id="passLineOut" nowrap="1"> <xsl:value-of select="passLineOutText" /> </td>
                            <td align="center" id="nChangeStationId" nowrap="1"> <xsl:value-of select="nChangeStationIdText" /> </td>
                            <td align="center" id="passLineIn" nowrap="1"> <xsl:value-of select="passLineInText" /> </td>
                            <td align="center" id="passDistance" nowrap="1"> <xsl:value-of select="passDistance" /> </td>
                        </tr>
                        </xsl:for-each>
                        
                    </table>
                </DIV>
                <br/>

                <div style="height:2%;">
                    <FORM method="post" name="formOp" action="">
                        <!-- ҳ���õ��ı��� ͨ��ģ�� -->
			<xsl:call-template name="common_web_variable" />
                        <DIV id="detail"></DIV>
                        <!-- �������б�� ͨ��ģ�� -->
                        <xsl:call-template name="op_button_list_gen">
                                <xsl:with-param name="print" select="1" />
                                <xsl:with-param name="clickMethod" select="'btnClick(&quot;formOp&quot;,&quot;clearStart&quot;);'"/>
                        </xsl:call-template>
                    </FORM>
                </div>
                
                <!-- ״̬�� ͨ��ģ�� -->
                <xsl:call-template name="common_status_table">
                </xsl:call-template>
                
            </body>
        </html>

    </xsl:template>
</xsl:stylesheet>