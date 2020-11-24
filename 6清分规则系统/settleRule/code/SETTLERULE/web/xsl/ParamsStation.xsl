<?xml version="1.0" encoding="GBK"?>

<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:import href="common_template.xsl" />
    <xsl:import href="common_web_variable_template.xsl" />
    
    <xsl:template match="/">

        <html>
            <head>
                <title>站点参数设置</title>
                <link rel="stylesheet" type="text/css" href="./xsl/css/simple.css" title="Style" />
                <script language="javascript" type="text/javascript" charset="UTF-8" src="./js/icProduceOut.js"></script>
                <script language="JavaScript" type="text/javascript"
                        charset="UTF-8" src="./js/ick.js">
                </script>
                <script language="javascript" type="text/javascript"
                        charset="UTF-8" src="./js/common_form.js">
                </script>
                <script language="javascript" type="text/javascript"
                        charset="UTF-8" src="./js/CalendarPop.js">
                </script>
                <script language="javascript" type="text/javascript"
                        charset="UTF-8" src="./js/Validator.js">
                </script>
                                
                
            </head>

           <body onload="initDocument('formQuery','detail');initDocument('formOp','detail');
                            setControlsDefaultValue('formQuery');
                            setListViewDefaultValue('formOp','clearStart');
                            setQueryControlsDefaultValue('formQuery','formOp');
                            setPrimaryKeys('formOp','d_line#d_presentStation#d_nextStation');
                            setSelectValues('formOp','d_line','d_presentStation','commonVariable1');
                            setSelectValues('formOp','d_line','d_nextStation','commonVariable1');
                            setSelectValues('formOp','d_line','d_nextTransferStation','commonVariable1');">
                
                <table width="95%" align="center" class="tableStyle">
                    <tr align="center" class="trTitle">
                        <td colspan="2">站点里程设置</td>
                    </tr>
                </table>
                <br/>
                
                <div style="height:1%;">
                    <FORM method="post" name="formQuery" action="paramsStationAction.do">
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
                                    <div align="right">当前站点:</div>
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
                                                    <td width="8%">
                                                            <div align="right">创建时间:</div>
                                                    </td>
                                                    <td colspan="3">
                                                            <div align="left">
                                                                    <input type="text"
                                                                            name="q_beginOpTime" id="q_beginOpTime" value="" style="width:40%"
                                                                            require="false" dataType="Date" format="ymd"
                                                                            msg="操作日期格式为(YYYY-MM-dd)!" />
                                                                    <a
                                                                            href="javascript:openCalenderDialog(document.all.q_beginOpTime);">
                                                                            <img
                                                                                    src="./images/calendar.gif" width="12" height="15"
                                                                                    border="0" style="block" />
                                                                    </a>
                                                                    --
                                                                    <input type="text"
                                                                            name="q_endOpTime" id="q_endOpTime" value="" style="width:40%"
                                                                            require="false" dataType="Date|ThanDate|dateDiff" format="ymd"  to="q_beginOpTime"
                                                                            msg="操作日期格式为(YYYY-MM-dd)且大于等于开始时间,查询范围90天!" />
                                                                    <a
                                                                            href="javascript:openCalenderDialog(document.all.q_endOpTime);">
                                                                            <img
                                                                                    src="./images/calendar.gif" width="12" height="15"
                                                                                    border="0" style="block" />
                                                                    </a>
                                                            </div>
                                                    </td>                                                  
                               
                              
                                <td align="center" width="8%" rowspan="2">
                                    <!-- 页面用到的变量 通用模板 -->
                                    <div align="center">
                                        <xsl:call-template name="op_button_list_gen">
                                            
                                              <xsl:with-param name="query" select="1" />
                                              <xsl:with-param name="export" select="0" />
                                           
                                            <xsl:with-param name="addClickMethod"
                                                select="'setControlNames(&quot;formQuery&quot;,&quot;q_oline_id#q_ostation_id#q_recorq_flag#q_beginOpTime#q_endOpTime&quot;);
                                                         checkDate(&quot;q_beginOpTime&quot;,&quot;q_endOpTime&quot;)'" />
                                            <xsl:with-param name="clickMethod"
                                                select="'btnClick(&quot;formQuery&quot;,&quot;clearStart&quot;,&quot;detail&quot;);'" />
                                        </xsl:call-template>
                                    </div>
                                </td>
                            </tr>
                        </table>
                    </FORM>
                </div>

                <DIV id="clearStart" align="center"
                        style="position:relative; left:20; width:95%; height:200%; layer-background-color:#99CC99; border:thin outset #ffffff; z-index:1; overflow: scroll;">

                    <table class="listDataTable" id="groupTable" align="left" width="100%">

                        <tr class="trDataHead" id="ignore" >
                            <td align="center" nowrap="1" width="5%">
                                <input type="checkbox" name="rectNoAll"
                                        onClick="selectAllRecord('formOp','rectNoAll','rectNo','clearStart',0);
                                        clearTextByFlag('formOp',['d_presentStation','d_nextStation']); controlsByFlag('formOp',['del','audit','modify']);" />
                            </td>
                            <td align="center" nowrap="1" width="10%" class="listTableHead" onyes="head"
                                    onclick="JM_PowerListByIDs(this.parentElement.parentElement.parentElement.parentElement,this.parentElement.parentElement.parentElement,1)">
                                    线路
                            </td>
                            <td align="center" nowrap="1" width="10%" class="listTableHead" onyes="head"
                                    onclick="JM_PowerListByIDs(this.parentElement.parentElement.parentElement.parentElement,this.parentElement.parentElement.parentElement,2)">
                                    当前站点
                            </td>
                            <td align="center" nowrap="1" width="10%" class="listTableHead" onyes="head"
                                    onclick="JM_PowerListByIDs(this.parentElement.parentElement.parentElement.parentElement,this.parentElement.parentElement.parentElement,3)">
                                    下一站点
                            </td>
                            <td align="center" nowrap="1" width="10%" class="listTableHead" onyes="head"
                                    onclick="JM_PowerListByIDs(this.parentElement.parentElement.parentElement.parentElement,this.parentElement.parentElement.parentElement,4)">
                                     下一换乘站
                            </td>
                         
                           <td align="center" nowrap="1" width="10%" class="listTableHead" onyes="head"
                                    onclick="JM_PowerListByIDs(this.parentElement.parentElement.parentElement.parentElement,this.parentElement.parentElement.parentElement,5)">
                                     里程数(米)
                            </td>
                             
                            <td align="center" nowrap="1" width="5%" class="listTableHead" onyes="head"
                                    onclick="JM_PowerListByIDs(this.parentElement.parentElement.parentElement.parentElement,this.parentElement.parentElement.parentElement,6)">
                                     版本状态
                            </td>
                            <td align="center" nowrap="1" width="15%" class="listTableHead" onyes="head"
                                    onclick="JM_PowerListByIDs(this.parentElement.parentElement.parentElement.parentElement,this.parentElement.parentElement.parentElement,7)">
                                     版本
                            </td>
                            <td align="center" nowrap="1" width="15%" class="listTableHead" onyes="head"
                                    onclick="JM_PowerListByIDs(this.parentElement.parentElement.parentElement.parentElement,this.parentElement.parentElement.parentElement,8)">
                                     创建时间
                            </td>
                            <td align="center" nowrap="1" width="10%" class="listTableHead" onyes="head"
                                    onclick="JM_PowerListByIDs(this.parentElement.parentElement.parentElement.parentElement,this.parentElement.parentElement.parentElement,9)">
                                     创建人
                            </td>
                        </tr>

                        <!-- 显示查询结果 -->
                      <xsl:for-each select="/Service/Result/paramsStation/paramsStationVo">
                         <tr optype="" modified="" onyes="detail" bgColor="#fdfdfd" style="display:" 
                            onMouseOver="overResultRow('formOp',this);" onMouseOut="outResultRow('formOp',this);" 
                            onclick="clickResultRow('formOp',this,'detail');setSelectValuesByRowPropertyName('formOp','d_presentStation','commonVariable1','stationP');
                                                                            setSelectValuesByRowPropertyName('formOp','d_nextStation','commonVariable1','stationN');
                                                                            setSelectValuesByRowPropertyName('formOp','d_nextTransferStation','commonVariable1','stationS');" >
                                <xsl:attribute name="stationP">
                                    <xsl:value-of select="concat(line,'#',presentStation)" />
                                </xsl:attribute>
                                <xsl:attribute name="stationN">
                                    <xsl:value-of select="concat(line,'#',nextStation)" />
                                </xsl:attribute>
                                <xsl:attribute name="stationS">
                                    <xsl:value-of select="concat(line,'#',nextTransferStation)" />
                                </xsl:attribute>
                                
                                <xsl:attribute name="id">
                                    <xsl:value-of select="line" />
                                    <xsl:value-of select="presentStation" />
                                    <xsl:value-of select="nextStation" />
                                    <xsl:value-of select="version" />
                                </xsl:attribute>

                                <td align="center" nowrap="1" id="rectNo1">
                                    <input type="checkbox" name="rectNo"
                                            onclick="unSelectAllRecord('formOp','rectNoAll','rectNo');controlsByFlag('formOp',['del','audit','modify']);">
                                            <xsl:attribute name="value">
                                                <xsl:value-of select="line" />
                                                <xsl:value-of select="presentStation" />
                                                <xsl:value-of select="nextStation" />
                                                <xsl:value-of select="version" />
                                            </xsl:attribute>
                                            <xsl:attribute name="flag">
                                                <xsl:value-of select="recordFlag" />
                                            </xsl:attribute>
                                    </input>
                                </td>
                                <td align="center" nowrap="1" id="line"> <xsl:value-of select="lineTxt" /> </td>
                                <td align="center" nowrap="1" id="presentStation"> <xsl:value-of select="presentStationTxt" /> </td>
                                <td align="center" nowrap="1" id="nextStation"> <xsl:value-of select="nextStationTxt" /> </td>
                                <td align="center" nowrap="1" id="nextTransferStation"> <xsl:value-of select="nextTransferStationTxt" /> </td>
                                <td align="center" nowrap="1" id="mileage"> <xsl:value-of select="mileage" /> </td>
                                <td align="center" nowrap="1" id="recordFlag"> <xsl:value-of select="recordFlagText" /> </td>
                                <td align="center" nowrap="1" id="version"> <xsl:value-of select="version" /> </td>
                                <td align="center" nowrap="1" id="createTime"> <xsl:value-of select="createTime" /> </td>
                                <td align="center" nowrap="1" id="operator"> <xsl:value-of select="operator" /> </td>
                            </tr>
                        </xsl:for-each>

                    </table>
                </DIV>
                
                 <!-- 明细表单 通用模板 -->
                <xsl:call-template name="common_table_head">
                    <xsl:with-param name="tbName" select="string('明细')" />
                </xsl:call-template>

                <div style="height:1%;">
                    <FORM method="post" name="formOp" action="paramsStationAction.do">
                        <!-- 页面用到的变量 通用模板 -->
			<xsl:call-template name="common_web_variable" />
                        <DIV id="detail" align="center"
                             style="position:relative; left:20; width:95%; height:5; layer-background-color:#99CC99; border:thin outset #ffffff; z-index:1;">
                            <table width="100%" align="left" class="listDataTable">
                                <tr align="center" bgColor="#EFEFEF">
                                    <td width="5%">
                                        <div align="right">线路：</div>
                                    </td>
                                    <td width="13%">
                                        <div align="left">
                                            <select id="d_line" NAME="d_line" style="width:100%" dataType="Require" msg="线路不能为空!"
                                                onChange="setSelectValues('formOp','d_line','d_presentStation','commonVariable1');
                                                          setSelectValues('formOp','d_line','d_nextStation','commonVariable1');
                                                          setSelectValues('formOp','d_line','d_nextTransferStation','commonVariable1');">
                                                <xsl:call-template name="option_list_pub">
                                                    <xsl:with-param name="isAll" select="''"/>
                                                    <xsl:with-param name="root" select="/Service/Result/lines/pubFlagVo"/>
                                                </xsl:call-template>
                                                <xsl:call-template name="option_values">
                                                    <xsl:with-param name="isAll" select="''"/>
                                                    <xsl:with-param name="root"  select="/Service/Result/stations/pubFlagVo"/>
                                                    <xsl:with-param name="inputName" select="'commonVariable1'"/>
                                                </xsl:call-template>
                                            </select>
                                            <!-- 
                                            <input type="text" name="d_line" style="width:100%" require="true" 
                                                   maxlength="2" dataType="LimitB" min="2" max="2" 
                                                   msg="线路ID不能为空，且是两位" />
                                                   -->
                                        </div>
                                    </td>                            
                                    <td width="8%">
                                        <div align="right">当前站点:</div>
                                    </td>
                                    <td width="12%">
                                        <div align="left">
                                            <select id="d_presentStation" NAME="d_presentStation" dataType="Require" msg="当前站点不能为空!" style="width:100%" >
                                                <xsl:call-template name="option_list_pub_none">
                                                    <xsl:with-param name="root" select="/Service/Result/stations/pubFlagVo"/>
                                                </xsl:call-template>
                                            </select>
                                            <!-- 
                                            <input type="text" name="d_presentStation" style="width:100%" require="true" 
                                                   maxlength="2" dataType="LimitB" regexp="(^\d\d$)" min="2" max="2" 
                                                   msg="当前站点ID不能为空，且是两位" />
                                                   -->
                                        </div>
                                    </td>
                                    <td width="8%">
                                        <div align="right">下一站点:</div>
                                    </td>
                                    <td width="12%">
                                        <div align="left">
                                            <select id="d_nextStation" NAME="d_nextStation" dataType="Require" msg="下一站点不能为空!" style="width:100%" >
                                                <xsl:call-template name="option_list_pub_none">
                                                    <xsl:with-param name="root" select="/Service/Result/stations/pubFlagVo"/>
                                                </xsl:call-template>
                                            </select>
                                            <!-- 
                                            <input type="text" name="d_nextStation" style="width:100%" require="true" 
                                                   maxlength="2" dataType="LimitB" regexp="(^\d\d$)" min="2" max="2" 
                                                   msg="下一站点ID不能为空，且是两位" />
                                                   -->
                                        </div>
                                    </td>
                                    
                                    <td width="10%">
                                        <div align="right">下一换乘站:</div>
                                    </td>
                                    <td width="12%">
                                        <div align="left">
                                            <select id="d_nextTransferStation" NAME="d_nextTransferStation" dataType="Require" msg="下一换乘站不能为空!" style="width:100%" >
                                                <xsl:call-template name="option_list_pub_none">
                                                    <xsl:with-param name="root" select="/Service/Result/stations/pubFlagVo"/>
                                                </xsl:call-template>
                                            </select>
                                            <!-- 
                                            <input type="text" name="d_nextTransferStation" style="width:100%" require="true" 
                                                   maxlength="2" dataType="LimitB" regexp="(^\d\d$)" min="2" max="2" 
                                                   msg="下一换乘站ID不能为空，且是两位;如果没有下一换乘站，请输入下一站点" />
                                                   -->
                                        </div>
                                    </td>
                                    <td width="8%">
                                        <div align="right">里程数:</div>
                                    </td>
                                    <td width="13%">
                                        <div align="left">
                                            <input type="text" name="d_mileage" style="width:100%" require="true" 
                                                   maxlength="13"  dataType="double|MaxMileage|LimitB" min="0" max="13" 
                                                   msg="里程数必须为小于100000,长度小于等于13位的正数"/>
                                        </div>
                                    </td>                                
                                </tr>
                          </table>
                        </DIV>
                

                         <xsl:call-template name="op_button_list_gen">
                                <xsl:with-param name="add" select="1" />
                                <xsl:with-param name="del" select="1" />
                                <xsl:with-param name="modify" select="1" />
                                <xsl:with-param name="audit" select="1" />
                                <xsl:with-param name="save" select="1" />
                                <xsl:with-param name="cancle" select="1" />
                                <xsl:with-param name="print" select="1" />
                                <xsl:with-param name="quit" select="1" />
                                <xsl:with-param name="addClickMethod"
                                        select="'setUpdatePrimaryKey(&quot;formOp&quot;,&quot;d_line#d_presentStation#d_nextStation&quot;);disablePrimaryKeys(&quot;formOp&quot;);'" />
                                <xsl:with-param name="clickMethod"
                                        select="'btnClick(&quot;formOp&quot;,&quot;clearStart&quot;,&quot;detail&quot;);'" />
                                <xsl:with-param name="addAfterMethod"
                                        select="'enablePrimaryKeys(&quot;formOp&quot;);'" />

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