<?xml version="1.0" encoding="GBK"?>

<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:import href="common_template.xsl" />
    <xsl:import href="common_web_variable_template.xsl" />
    
    <xsl:template match="/">
<html>
            <head>
                <title>线路站点查询</title>
                <link rel="stylesheet" type="text/css" href="./xsl/css/simple.css" title="Style" />
                <script language="javascript" type="text/javascript" charset="UTF-8" src="./js/icProduceOut.js"></script>
                <script language="JavaScript" type="text/javascript"
                        charset="UTF-8" src="./js/ick.js">
                </script>
                <script language="javascript" type="text/javascript"
                        charset="UTF-8" src="./js/common_form.js">
                </script>
                <script language="javascript" type="text/javascript"
                        charset="UTF-8" src="./js/Validator.js">
                </script>
                                
                
            </head>

           <body onload="initDocument('formQuery','detail');initDocument('formOp','detail');
										setControlsDefaultValue('formQuery');
                                                                                setListViewDefaultValue('formOp','clearStart');
										setQueryControlsDefaultValue('formQuery','formOp');">
                
                <table width="95%" align="center" class="tableStyle">
                    <tr align="center" class="trTitle">
                        <td colspan="2">线路站点查询</td>
                    </tr>
                </table>
                <br/>
                
                 <!-- 查询表单 通用模板 -->
                <xsl:call-template name="common_table_head">
                    <xsl:with-param name="tbName" select="string('查询')" />
                </xsl:call-template>
                
                <div style="height:2%;">
                    <FORM method="post" name="formQuery" action="lineStationAction.do">
                        <!-- 页面用到的变量 通用模板 -->
                        <xsl:call-template name="common_web_variable" />
                        <table width="95%" align="center" class="tableStyle">
                            <tr class="trContent">
                             <td width="8%">
                                    <div align="right">线路:</div>
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
                                <td width="8%">
                                    <div align="right">站点:</div>
                                </td>
                                <td width="10%">
                                    <div align="left">
                                        <select id="q_ostation_id" NAME="q_ostation_id" style="width:100%" >
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
                              
                              
                                <td align="center" width="10%" rowspan="2">
                                    <!-- 页面用到的变量 通用模板 -->
                                    <div align="center">
                                <xsl:call-template name="op_button_list_gen">
                                            
                                              <xsl:with-param name="query" select="1" />
                                              <xsl:with-param name="export" select="0" />
                                           
                                            <xsl:with-param name="addClickMethod"
                                                select="'setControlNames(&quot;formQuery&quot;,&quot;q_oline_id#q_ostation_id#q_recorq_flag&quot;);'" />
                                            <xsl:with-param name="clickMethod"
                                                select="'btnClick(&quot;formQuery&quot;,&quot;clearStart&quot;,&quot;detail&quot;);'" />
                                </xsl:call-template>
                                    </div>
                                </td>
                            </tr>
                        </table>
                    </FORM>
                </div>
                <!-- 表头 通用模板 -->
                <xsl:call-template name="common_table_head">
                        <xsl:with-param name="tbName" select="string('列表')" />
                </xsl:call-template>

                <DIV id="clearStart" align="center"
                        style="position:relative; left:20; width:95%; height:250; layer-background-color:#99CC99; border:thin outset #ffffff; z-index:1; overflow: scroll;">

                    <table class="listDataTable" id="groupTable" align="left" width="100%">

                        <tr class="trDataHead" id="ignore" >
                            <td align="center" nowrap="1" width="10%" class="listTableHead" onyes="head"
                                    onclick="JM_PowerListByIDs(this.parentElement.parentElement.parentElement.parentElement,this.parentElement.parentElement.parentElement,0)">
                                    所属线路
                            </td>
                            <td align="center" nowrap="1" width="15%" class="listTableHead" onyes="head"
                                    onclick="JM_PowerListByIDs(this.parentElement.parentElement.parentElement.parentElement,this.parentElement.parentElement.parentElement,1)">
                                    站点中文名
                            </td>
                            <td align="center" nowrap="1" width="15%" class="listTableHead" onyes="head"
                                    onclick="JM_PowerListByIDs(this.parentElement.parentElement.parentElement.parentElement,this.parentElement.parentElement.parentElement,2)">
                                    站点英文名
                            </td>
                            <td align="center" nowrap="1" width="10%" class="listTableHead" onyes="head"
                                    onclick="JM_PowerListByIDs(this.parentElement.parentElement.parentElement.parentElement,this.parentElement.parentElement.parentElement,3)">
                                    版本状态
                            </td>
                        </tr>

                        <!-- 显示查询结果 -->
                        <xsl:for-each select="Service/Result/lineStation/lineStationVo">
                             <tr id="groupRow" class="trData" onMouseOver="overResultRow('formOp',this);" onMouseOut="outResultRow('formOp',this);">                               
                                <td align="center" nowrap="1" id="line"> <xsl:value-of select="line" /> </td>
                                <td align="center" nowrap="1" id="chineseStation"> <xsl:value-of select="chineseStation" /> </td>
                                <td align="center" nowrap="1" id="englishStation"> <xsl:value-of select="englishStation" /> </td>
                                <td align="center" nowrap="1" id="recordFlag"> <xsl:value-of select="recordFlagText" /> </td>
                            </tr>
                        </xsl:for-each>

                    </table>
                </DIV>
              <br/>
                <div style="height:2%;">
                    <FORM method="post" name="formOp" action="">
                        <!-- 页面用到的变量 通用模板 -->
			<xsl:call-template name="common_web_variable" />
                        <DIV id="detail"></DIV>
                        <!-- 插入结果列表框 通用模板 -->
                        <xsl:call-template name="op_button_list_gen">
                                <xsl:with-param name="print" select="1" />
                                <xsl:with-param name="clickMethod" select="'btnClick(&quot;formOp&quot;,&quot;clearStart&quot;);'"/>
                        </xsl:call-template>
                    </FORM>
                </div>
                
                  
                  <!-- 状态栏 通用模板 -->
                  <xsl:call-template name="common_status_table">
                  </xsl:call-template>      
                        
               
            </body>
        </html>

    </xsl:template>
</xsl:stylesheet>