<?xml version="1.0" encoding="GBK"?>

<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:import href="common_template.xsl" />
    <xsl:import href="common_web_variable_template.xsl" />
    
    <xsl:template match="/">

        <html>
            <head>
                <title>下载权重数据</title>
                <link rel="stylesheet" type="text/css" href="./xsl/css/simple.css" title="Style" />
                <script language="JavaScript" src="./js/Validator.js"></script>
		<script language="javascript" type="text/javascript" charset="UTF-8" src="./js/common_form.js"></script>
                <script language="javascript" type="text/javascript" charset="UTF-8" src="./js/common_form_extend.js"></script>
            </head>

            <body onload="initDocument('formQuery','detail');
                                initDocument('formOp','detail');
				setControlsDefaultValue('formQuery');
				setQueryControlsDefaultValue('formQuery','formOp');
				setPageControl('formOp');">
                
                <table width="95%" align="center" class="tableStyle">
                    <tr align="center" class="trTitle">
                        <td colspan="2"><div id = "menuTitle">下载权重数据</div></td>
                    </tr>
                </table>
                
                <div>
                    <FORM method="post" id="formQuery" name="formQuery" action="generateDataAction.do">
                        <!-- 页面用到的变量 通用模板 -->
                        <xsl:call-template name="common_web_variable" />
                        
                        <table width="95%" align="center" class="tableStyle">
                            <tr>
                                <td colspan="9">
                                    <div align="left">
                                        <input type="button" id="btGenerate" name="btGenerate" value="生成权重数据" class="buttonStyle" onclick="btnClick('formQuery','clearStart','detail');"/>
                                        <input type="button" id="btGenerateOD" name="btGenerateOD" value="生成OD数据" class="buttonStyle" onclick="btnClick('formQuery','clearStart','detail');"/>
                                        <hr/>
                                    </div>
                                </td>
                            </tr>
                            <tr class="trContent">
                                <td width="8%">
                                    <div align="right">开始站点:</div>
                                </td>
                                <td width="10%">
                                    <div align="left">
                                        <select id="q_oline_id" NAME="q_oline_id" style="width:100%"
                                            onChange="setSelectValues('formQuery','q_oline_id','q_ostation_id','commonVariable');">
                                            <xsl:call-template name="option_list_pub">
                                                <xsl:with-param name="root" select="/Service/Result/lines/pubFlagVo"/>
                                            </xsl:call-template>
                                        </select>
                                    </div>
                                </td>
                                <td width="10%">
                                    <div align="left">
                                        <select id="q_ostation_id" NAME="q_ostation_id" style="width:100%">
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

                                <td width="8%">
                                    <div align="right">结束站点:</div>
                                </td>
                                <td width="10%">
                                    <div align="left">
                                        <select id="q_eline_id" NAME="q_eline_id" style="width:100%"
                                            onChange="setSelectValues('formQuery','q_eline_id','q_estation_id','ecommonVariable');">
                                            <xsl:call-template name="option_list_pub">
                                                <xsl:with-param name="root" select="/Service/Result/lines/pubFlagVo"/>
                                            </xsl:call-template>
                                        </select>
                                    </div>
                                </td>
                                <td width="10%">
                                    <div align="left">
                                        <select id="q_estation_id" NAME="q_estation_id" style="width:100%">
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
                                
                                <td width="8%">
                                    <div align="right">版本状态:</div>
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

                                <td align="right" width="10%">
                                    <!-- 页面用到的变量 通用模板 -->
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
                            <tr class="trContent" height="20">
                                <td width="8%">
                                    <div align="right">导出字段:</div>
                                </td>
                                <td colspan="7">
                                    <input type="hidden" id="c_checkbox" name="c_checkbox" value=""/>
                                    <input type="checkbox" value="c_oline" name="c" checked="checked" disabled="disabled">开始线路</input>
                                    <input type="checkbox" value="c_ostation" name="c" checked="checked" disabled="disabled">开始站点</input>
                                    <input type="checkbox" value="c_dline" name="c" checked="checked" disabled="disabled">结束线路</input>
                                    <input type="checkbox" value="c_dstation" name="c" checked="checked" disabled="disabled">结束站点</input>
                                    <input type="checkbox" value="c_dispart_line" name="c" checked="checked" disabled="disabled">权重线路</input>
                                    <input type="checkbox" value="c_proportion" name="c" checked="checked" disabled="disabled">权重比例</input>
                                    <input type="checkbox" value="c_version" name="c">版本</input>
                                </td>
                                <td align="left">
                                    <div align="left">
                                        <input type="button" id="btFileDown" name="btFileDown" value="参数下载" class="buttonStyle" onclick="saveFile('formQuery');" />
                                    </div>
                                </td>
                            </tr>
                        </table>
                    </FORM>
                </div>
                
                <DIV id="clearStart" align="center"
                        style="position:relative; left:20; width:95%; height:50%; layer-background-color:#99CC99; border:thin outset #ffffff; z-index:1; overflow: scroll;">

                    <table class="listDataTable" id="groupTable" align="left" width="100%">

                        <tr class="trDataHead" id="ignore" >
                            <td align="center" nowrap="1" class="listTableHead" onyes="head"
                                    onclick="JM_PowerListByIDs(this.parentElement.parentElement.parentElement.parentElement,this.parentElement.parentElement.parentElement,0)">
                                    开始线路
                            </td>
                            <td align="center" nowrap="1" class="listTableHead" onyes="head"
                                    onclick="JM_PowerListByIDs(this.parentElement.parentElement.parentElement.parentElement,this.parentElement.parentElement.parentElement,1)">
                                    开始站点
                            </td>
                            <td align="center" nowrap="1" class="listTableHead" onyes="head"
                                    onclick="JM_PowerListByIDs(this.parentElement.parentElement.parentElement.parentElement,this.parentElement.parentElement.parentElement,2)">
                                    结束线路
                            </td>
                            <td align="center" nowrap="1" class="listTableHead" onyes="head"
                                    onclick="JM_PowerListByIDs(this.parentElement.parentElement.parentElement.parentElement,this.parentElement.parentElement.parentElement,3)">
                                    结束站点
                            </td>
                            <td align="center" nowrap="1" class="listTableHead" onyes="head"
                                    onclick="JM_PowerListByIDs(this.parentElement.parentElement.parentElement.parentElement,this.parentElement.parentElement.parentElement,4)">
                                    权重线路
                            </td>
                            <td align="center" nowrap="1" class="listTableHead" onyes="head"
                                    onclick="JM_PowerListByIDs(this.parentElement.parentElement.parentElement.parentElement,this.parentElement.parentElement.parentElement,5)">
                                    权重比例
                            </td>
                            <td align="center" nowrap="1" class="listTableHead" onyes="head"
                                    onclick="JM_PowerListByIDs(this.parentElement.parentElement.parentElement.parentElement,this.parentElement.parentElement.parentElement,6)">
                                    版本
                            </td>
                            <td align="center" nowrap="1" class="listTableHead" onyes="head"
                                    onclick="JM_PowerListByIDs(this.parentElement.parentElement.parentElement.parentElement,this.parentElement.parentElement.parentElement,7)">
                                    版本状态
                            </td>
                        </tr>

                        <!-- 显示查询结果 -->
                        <xsl:for-each select="Service/Result/distanceProportions/distanceProportionVo">
                            <tr id="groupRow" class="trData" onMouseOver="overResultRow('formOp',this);" onMouseOut="outResultRow('formOp',this);">
                                <td align="center" nowrap="1" id="oLineId"> <xsl:value-of select="oLineIdText" /> </td>
                                <td align="center" nowrap="1" id="oStationId"> <xsl:value-of select="oStationIdText" /> </td>
                                <td align="center" nowrap="1" id="dLineId"> <xsl:value-of select="dLineIdText" /> </td>
                                <td align="center" nowrap="1" id="dStationId"> <xsl:value-of select="dStationIdText" /> </td>
                                <td align="center" nowrap="1" id="dispartLineId"> <xsl:value-of select="dispartLineIdText" /> </td>
                                <td align="center" nowrap="1" id="inPrecent"> <xsl:value-of select="inPrecent" /> </td>
                                <td align="center" nowrap="1" id="version"> <xsl:value-of select="version" /> </td>
                                <td align="center" nowrap="1" id="recordFlag"> <xsl:value-of select="recordFlagText" /> </td>
                            </tr>
                        </xsl:for-each>

                    </table>
                </DIV>
                
                <!-- 显示查询统计结果
                <table width="95%" align="center" class="tableStyle">
                        <tr class="trContent">
                            <td width="10%" class="listTableHead"  id="record">
                                <div align="center">总记录数 <xsl:value-of select="/Service/Result/record"/> 条</div>
                            </td>
                        </tr>
                </table>
                 -->
                <FORM method="post" id="formOp" name="formOp" action="generateDataAction.do">

                    <!-- 页面用到的变量 通用模板 -->
                    <xsl:call-template name="common_web_variable" >
                            <xsl:with-param name="divideShow" select="1"/>
                    </xsl:call-template>
                    <!-- 插入结果列表框 通用模板 -->
                    <xsl:call-template name="op_button_list_gen">
                            <xsl:with-param name="next" select="1" />
                            <xsl:with-param name="nextEnd" select="1" />
                            <xsl:with-param name="back" select="1" />
                            <xsl:with-param name="backEnd" select="1" />
                            <xsl:with-param name="print" select="1" />
                            <xsl:with-param name="quit" select="1" />
                            <xsl:with-param name="clickMethod"
                                    select="'btnClick(&quot;formOp&quot;,&quot;clearStart&quot;,&quot;detail&quot;);'" />
                    </xsl:call-template>
                </FORM>
                
                <!-- 状态栏 通用模板 -->
                <xsl:call-template name="common_status_table">
                </xsl:call-template>
                
            </body>
        </html>

    </xsl:template>
</xsl:stylesheet>