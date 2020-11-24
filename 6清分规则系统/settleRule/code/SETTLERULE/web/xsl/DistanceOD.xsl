<?xml version="1.0" encoding="GBK"?>

<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:import href="common_template.xsl" />
    <xsl:import href="common_web_variable_template.xsl" />
    
    <xsl:template match="/">

        <html>
            <head>
                <title>���·����ѯ</title>
                <link rel="stylesheet" type="text/css" href="./xsl/css/simple.css" title="Style" />
                <script language="JavaScript" src="./js/Validator.js"></script>
		<script language="javascript" type="text/javascript" charset="UTF-8" src="./js/common_form.js"></script>
                <script language="javascript" type="text/javascript" charset="UTF-8" src="./js/common_form_extend.js"></script>
            </head>

            <body onload="initDocument('formQuery','detail');
                                initDocument('formOp','detail');
				setControlsDefaultValue('formQuery');
				setQueryControlsDefaultValue('formQuery','formOp');">
                
                <table width="95%" align="center" class="tableStyle">
                    <tr align="center" class="trTitle">
                        <td colspan="2"><div id = "menuTitle">���·����ѯ</div></td>
                    </tr>
                </table>
                <br/>
                
                <!-- ��ѯ�� ͨ��ģ�� -->
                <xsl:call-template name="common_table_head">
                    <xsl:with-param name="tbName" select="string('��ѯ')" />
                </xsl:call-template>
                
                <div style="height:2%;">
                    <FORM method="post" id="formQuery" name="formQuery" action="distanceODAction.do">
                        <!-- ҳ���õ��ı��� ͨ��ģ�� -->
                        <xsl:call-template name="common_web_variable" />
                        <table width="95%" align="center" class="tableStyle">
                            <tr class="trContent">
                                <td width="10%">
                                    <div align="right">��ʼվ��:</div>
                                </td>
                                <td width="10%">
                                    <div align="left">
                                        <select id="q_oline_id" NAME="q_oline_id" style="width:100%" dataType="Require" msg="��ѡ��ʼ��·"
                                            onChange="setSelectValues('formQuery','q_oline_id','q_ostation_id','commonVariable');">
                                            <xsl:call-template name="option_list_pub">
                                                <xsl:with-param name="root" select="/Service/Result/lines/pubFlagVo"/>
                                            </xsl:call-template>
                                        </select>
                                    </div>
                                </td>
                                <td width="10%">
                                    <div align="left">
                                        <select id="q_ostation_id" NAME="q_ostation_id" style="width:100%" msg="��ѡ��ʼվ��" dataType="Require">
                                            <xsl:call-template name="option_list_pub_none">
                                                <xsl:with-param name="root" select="/Service/Result/stations/pubFlagVo"/>
                                            </xsl:call-template>
                                            <xsl:call-template name="option_values">
                                                <xsl:with-param name="root"  select="/Service/Result/stations/pubFlagVo"/>
                                                <xsl:with-param name="inputName" select="'commonVariable'"/>
                                            </xsl:call-template>
                                        </select>
                                    </div>
                                </td>

                                <td width="10%">
                                    <div align="right">����վ��:</div>
                                </td>
                                <td width="10%">
                                    <div align="left">
                                        <select id="q_eline_id" NAME="q_eline_id" style="width:100%" msg="��ѡ�������·" dataType="Require"
                                            onChange="setSelectValues('formQuery','q_eline_id','q_estation_id','ecommonVariable');">
                                            <xsl:call-template name="option_list_pub">
                                                <xsl:with-param name="root" select="/Service/Result/lines/pubFlagVo"/>
                                            </xsl:call-template>
                                        </select>
                                    </div>
                                </td>
                                <td width="10%">
                                    <div align="left">
                                        <select id="q_estation_id" NAME="q_estation_id" style="width:100%" msg="��ѡ�����վ��" dataType="Require">
                                            <xsl:call-template name="option_list_pub_none">
                                                <xsl:with-param name="root" select="/Service/Result/stations/pubFlagVo"/>
                                            </xsl:call-template>
                                            <xsl:call-template name="option_values">
                                                <xsl:with-param name="root"  select="/Service/Result/stations/pubFlagVo"/>
                                                <xsl:with-param name="inputName" select="'ecommonVariable'"/>
                                            </xsl:call-template>
                                        </select>
                                    </div>
                                </td>
                                
                                <td width="10%">
                                    <div align="right">�汾״̬:</div>
                                </td>
                                <td width="10%">
                                    <div align="left">
                                        <select id="q_recorq_flag" NAME="q_recorq_flag" style="width:100%">
                                            <xsl:call-template name="option_list_pub">
                                                <xsl:with-param name="root" select="/Service/Result/recordFlags/pubFlagVo"/>
                                            </xsl:call-template>
                                        </select>
                                    </div>
                                </td>

                                <td align="right" width="10%" rowspan="2">
                                    <!-- ҳ���õ��ı��� ͨ��ģ�� -->
                                    <div align="right">
                                        <xsl:call-template name="op_button_list_gen">
                                            <xsl:with-param name="query" select="1" />
                                            <xsl:with-param name="export" select="0" />
                                            <xsl:with-param name="addClickMethod"
                                                select="'setControlNames(&quot;formQuery&quot;,&quot;q_oline_id#q_ostation_id#q_eline_id#q_estation_id#q_recorq_flag&quot;);'" />
                                            <xsl:with-param name="clickMethod"
                                                select="'btnClick(&quot;formQuery&quot;,&quot;clearStart&quot;,&quot;detail&quot;);'" />
                                        </xsl:call-template>
                                    </div>
                                </td>
                            </tr>
                        </table>
                    </FORM>
                </div>
                
                <!-- ��ͷ ͨ��ģ�� -->
                <xsl:call-template name="common_table_head">
                        <xsl:with-param name="tbName" select="string('�б�')" />
                </xsl:call-template>

                <DIV id="clearStart" align="center"
                        style="position:relative; left:20; width:95%; height:250; layer-background-color:#99CC99; border:thin outset #ffffff; z-index:1; overflow: scroll;">

                    <table class="listDataTable" id="groupTable" align="left" width="100%">

                        <tr class="trDataHead" id="ignore" >
                            <td align="center" nowrap="1" class="listTableHead" onyes="head"
                                    onclick="JM_PowerListByIDs(this.parentElement.parentElement.parentElement.parentElement,this.parentElement.parentElement.parentElement,0)">
                                    ID
                            </td>
                            <td align="center" nowrap="1" class="listTableHead" onyes="head"
                                    onclick="JM_PowerListByIDs(this.parentElement.parentElement.parentElement.parentElement,this.parentElement.parentElement.parentElement,1)">
                                    ��ʼ��·
                            </td>
                            <td align="center" nowrap="1" class="listTableHead" onyes="head"
                                    onclick="JM_PowerListByIDs(this.parentElement.parentElement.parentElement.parentElement,this.parentElement.parentElement.parentElement,2)">
                                    ��ʼվ��
                            </td>
                            <td align="center" nowrap="1" class="listTableHead" onyes="head"
                                    onclick="JM_PowerListByIDs(this.parentElement.parentElement.parentElement.parentElement,this.parentElement.parentElement.parentElement,3)">
                                    ������·
                            </td>
                            <td align="center" nowrap="1" class="listTableHead" onyes="head"
                                    onclick="JM_PowerListByIDs(this.parentElement.parentElement.parentElement.parentElement,this.parentElement.parentElement.parentElement,4)">
                                    ����վ��
                            </td>
                            <td align="center" nowrap="1" class="listTableHead" onyes="head"
                                    onclick="JM_PowerListByIDs(this.parentElement.parentElement.parentElement.parentElement,this.parentElement.parentElement.parentElement,5)">
                                    ���˴���
                            </td>
                            <td align="center" nowrap="1" class="listTableHead" onyes="head"
                                    onclick="JM_PowerListByIDs(this.parentElement.parentElement.parentElement.parentElement,this.parentElement.parentElement.parentElement,6)">
                                    �������
                            </td>
                            <td align="center" nowrap="1" class="listTableHead" onyes="head"
                                    onclick="JM_PowerListByIDs(this.parentElement.parentElement.parentElement.parentElement,this.parentElement.parentElement.parentElement,7)">
                                    ������
                            </td>
                            <td align="center" nowrap="1" class="listTableHead" onyes="head"
                                    onclick="JM_PowerListByIDs(this.parentElement.parentElement.parentElement.parentElement,this.parentElement.parentElement.parentElement,8)">
                                    �Ƿ���Ч
                            </td>
                            <td align="center" nowrap="1" class="listTableHead" onyes="head"
                                    onclick="JM_PowerListByIDs(this.parentElement.parentElement.parentElement.parentElement,this.parentElement.parentElement.parentElement,9)">
                                    �汾
                            </td>
                            <td align="center" nowrap="1" class="listTableHead" onyes="head"
                                    onclick="JM_PowerListByIDs(this.parentElement.parentElement.parentElement.parentElement,this.parentElement.parentElement.parentElement,10)">
                                    �汾״̬
                            </td>
                        </tr>

                        <!-- ��ʾ��ѯ��� -->
                        <xsl:for-each select="Service/Result/odDistances/distanceODVo">
                            <tr id="groupRow" class="trData" onMouseOver="overResultRow('formOp',this);" onMouseOut="outResultRow('formOp',this);">
                                <td align="center" nowrap="1" id="id">
                                    <xsl:choose>
                                        <xsl:when test="changeTimes='0'">
                                            <xsl:value-of select="id" />
                                        </xsl:when>
                                        <xsl:otherwise>								
                                            <a href='#' onClick="openwindow('distanceDetailAction.do?q_od_id={id}&amp;command=query&amp;ModuleID={/Service/Result/ModuleID}','',800,600)">
                                                <xsl:value-of select="id" />
                                            </a>
                                        </xsl:otherwise>
                                    </xsl:choose>
                                </td>
                                <td align="center" nowrap="1" id="oLineId"> <xsl:value-of select="oLineIdText" /> </td>
                                <td align="center" nowrap="1" id="oStationId"> <xsl:value-of select="oStationIdText" /> </td>
                                <td align="center" nowrap="1" id="eLineId"> <xsl:value-of select="eLineIdText" /> </td>
                                <td align="center" nowrap="1" id="eStationId"> <xsl:value-of select="eStationIdText" /> </td>
                                <td align="center" nowrap="1" id="changeTimes"> <xsl:value-of select="changeTimes" /> </td>
                                <td align="center" nowrap="1" id="distance"> <xsl:value-of select="distance" /> </td>
                                <td align="center" nowrap="1" id="minDistance"> <xsl:value-of select="minDistance" /> </td>
                                <td align="center" nowrap="1" id="isValid"> <xsl:value-of select="isValidText" /> </td>
                                <td align="center" nowrap="1" id="version">
                                    <xsl:choose>
                                        <xsl:when test="changeTimes='0'">
                                            <xsl:value-of select="version" />
                                        </xsl:when>
                                        <xsl:otherwise>								
                                            <a href='#' onClick="openwindow('distanceDetailAction.do?q_od_id={id}&amp;command=query&amp;ModuleID={/Service/Result/ModuleID}','',800,600)">
                                                <xsl:value-of select="version" />
                                            </a>
                                        </xsl:otherwise>
                                    </xsl:choose>
                                </td>
                                <td align="center" nowrap="1" id="recordFlag"> <xsl:value-of select="recordFlagText" /> </td>
                            </tr>
                        </xsl:for-each>

                    </table>
                </DIV>
                
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