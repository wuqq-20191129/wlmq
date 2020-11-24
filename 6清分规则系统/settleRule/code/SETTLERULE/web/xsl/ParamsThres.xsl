<?xml version="1.0" encoding="GBK"?>

<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:import href="common_template.xsl" />
    <xsl:import href="common_web_variable_template.xsl" />
    
    <xsl:template match="/">

        <html>
            <head>
                <title>阀值参数设置</title>
                <link rel="stylesheet" type="text/css" href="./xsl/css/simple.css" title="Style" />
                <script language="javascript" type="text/javascript" charset="UTF-8" src="./js/icProduceOut.js"></script>
                <script language="JavaScript" type="text/javascript" charset="UTF-8" src="./js/ick.js"></script>
                <script language="javascript" type="text/javascript" charset="UTF-8" src="./js/common_form.js"></script>
                <script language="javascript" type="text/javascript" charset="UTF-8" src="./js/CalendarPop.js"></script>
                <script language="javascript" type="text/javascript" charset="UTF-8" src="./js/Validator.js"></script>
            </head>

           <body onload="initDocument('formQuery','detail');
                            initDocument('formOp','detail');
                            setControlsDefaultValue('formQuery');
                            setListViewDefaultValue('formOp','clearStart');
                            setQueryControlsDefaultValue('formQuery','formOp');
                            setPrimaryKeys('formOp','d_id');">
                
                <table width="95%" align="center" class="tableStyle">
                    <tr align="center" class="trTitle">
                        <td colspan="2"><div id = "menuTitle">阀值参数设置</div></td>
                    </tr>
                </table>
                
                
                 <!-- 查询表单 通用模板 -->
                <xsl:call-template name="common_table_head">
                    <xsl:with-param name="tbName" select="string('查询')" />
                </xsl:call-template>
                
                <div style="height:2%;">
                    <FORM method="post" name="formQuery" action="paramsThresAction.do">
                        <!-- 页面用到的变量 通用模板 -->
                        <xsl:call-template name="common_web_variable" />
                        <table width="95%" align="center" class="tableStyle">
                            <tr class="trContent">
                                <td width="50%">
                                    <div align="right"></div>
                                </td>
                                <td width="10%">
                                    <div align="right">版本状态:</div>
                                </td>
                                <td width="10%">
                                    <div align="left">
                                        <select id="q_recorqFlag" NAME="q_recorqFlag">
                                            <xsl:call-template name="option_list_pub">
                                                <xsl:with-param name="root" select="/Service/Result/recordFlags/pubFlagVo"/>
                                            </xsl:call-template>
                                        </select>
                                    </div>
                                </td>
                                
                                <td align="center" width="10%">
                                    <!-- 页面用到的变量 通用模板 -->
                                    <div align="center">
                                        <xsl:call-template name="op_button_list_gen">
                                              <xsl:with-param name="query" select="1" />
                                              <xsl:with-param name="export" select="0" />
                                            <xsl:with-param name="addClickMethod"
                                                select="'setControlNames(&quot;formQuery&quot;,&quot;q_recorqFlag&quot;);'" />
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
                        style="position:relative; left:20; width:95%; height:80%; layer-background-color:#99CC99; border:thin outset #ffffff; z-index:1; overflow: scroll;">

                    <table class="listDataTable" id="groupTable" align="left" width="100%">

                        <tr class="trDataHead" id="ignore" >
                            <td align="center" nowrap="1" width="5%">
                                <input type="checkbox" name="rectNoAll"
                                        onClick="selectAllRecord('formOp','rectNoAll','rectNo','clearStart',0);
                                        clearTextByFlag('formOp',['id']); controlsByFlag('formOp',['del','audit','modify']);" />
                            </td>
                            <td align="center" nowrap="1" width="5%" class="listTableHead" onyes="head"
                                    onclick="JM_PowerListByIDs(this.parentElement.parentElement.parentElement.parentElement,this.parentElement.parentElement.parentElement,1)">
                                    ID
                            </td>
                            <td align="center" nowrap="1" width="10%" class="listTableHead" onyes="head"
                                    onclick="JM_PowerListByIDs(this.parentElement.parentElement.parentElement.parentElement,this.parentElement.parentElement.parentElement,2)">
                                    里程差比例阀值
                            </td>
                            <td align="center" nowrap="1" width="10%" class="listTableHead" onyes="head"
                                    onclick="JM_PowerListByIDs(this.parentElement.parentElement.parentElement.parentElement,this.parentElement.parentElement.parentElement,3)">
                                    站点差阀值
                            </td>
                            <td align="center" nowrap="1" width="10%" class="listTableHead" onyes="head"
                                    onclick="JM_PowerListByIDs(this.parentElement.parentElement.parentElement.parentElement,this.parentElement.parentElement.parentElement,4)">
                                    换乘次数阀值
                            </td>
                            <td align="center" nowrap="1" width="10%" class="listTableHead" onyes="head"
                                    onclick="JM_PowerListByIDs(this.parentElement.parentElement.parentElement.parentElement,this.parentElement.parentElement.parentElement,5)">
                                    乘车时间（s）阀值
                            </td>
                            <td align="center" nowrap="1" width="10%" class="listTableHead" onyes="head"
                                    onclick="JM_PowerListByIDs(this.parentElement.parentElement.parentElement.parentElement,this.parentElement.parentElement.parentElement,6)">
                                    描述
                            </td>
                            <td align="center" nowrap="1" width="10%" class="listTableHead" onyes="head"
                                    onclick="JM_PowerListByIDs(this.parentElement.parentElement.parentElement.parentElement,this.parentElement.parentElement.parentElement,7)">
                                    版本状态
                            </td>
                            <td align="center" nowrap="1" width="10%" class="listTableHead" onyes="head"
                                    onclick="JM_PowerListByIDs(this.parentElement.parentElement.parentElement.parentElement,this.parentElement.parentElement.parentElement,8)">
                                    版本
                            </td>
                            <td align="center" nowrap="1" width="10%" class="listTableHead" onyes="head"
                                    onclick="JM_PowerListByIDs(this.parentElement.parentElement.parentElement.parentElement,this.parentElement.parentElement.parentElement,9)">
                                    更新时间
                            </td>
                            <td align="center" nowrap="1" width="10%" class="listTableHead" onyes="head"
                                    onclick="JM_PowerListByIDs(this.parentElement.parentElement.parentElement.parentElement,this.parentElement.parentElement.parentElement,10)">
                                    更新人
                            </td>
                        </tr>

                        <!-- 显示查询结果 -->
                        <xsl:for-each select="Service/Result/paramsThres/paramsThresVo">
                             <tr optype="" modified="" onyes="detail" bgColor="#fdfdfd" style="display:" 
                            onMouseOver="overResultRow('formOp',this);" onMouseOut="outResultRow('formOp',this);" 
                            onclick="clickResultRow('formOp',this,'detail')" >
                                <xsl:attribute name="id">
                                    <xsl:value-of select="id" />
                                </xsl:attribute>

                                <td align="center" nowrap="1" id="rectNo1">
                                    <input type="checkbox" name="rectNo"
                                            onclick="unSelectAllRecord('formOp','rectNoAll','rectNo');controlsByFlag('formOp',['del','audit','modify']);">
                                            <xsl:attribute name="value">
                                                <xsl:value-of select="id" />
                                            </xsl:attribute>
                                            <xsl:attribute name="flag">
                                                <xsl:value-of select="recordFlag" />
                                            </xsl:attribute>
                                    </input>
                                </td>
                                <td align="center" nowrap="1" id="id"> <xsl:value-of select="id" /> </td>
                                <td align="center" nowrap="1" id="distanceThres"> <xsl:value-of select="distanceThres" /> </td>
                                <td align="center" nowrap="1" id="stationThres"> <xsl:value-of select="stationThres" /> </td>
                                <td align="center" nowrap="1" id="changeThres"> <xsl:value-of select="changeThres" /> </td>
                                <td align="center" nowrap="1" id="timeThres"> <xsl:value-of select="timeThres" /> </td>
                                <td align="center" nowrap="1" id="description"> <xsl:value-of select="description" /> </td>
                                <td align="center" nowrap="1" id="recordFlag"> <xsl:value-of select="recordFlagText" /> </td>
                                <td align="center" nowrap="1" id="version"> <xsl:value-of select="version" /> </td>
                                <td align="center" nowrap="1" id="updateTime"> <xsl:value-of select="updateTime" /> </td>
                                <td align="center" nowrap="1" id="updateOperator"> <xsl:value-of select="updateOperator" /> </td>
                            </tr>
                        </xsl:for-each>

                    </table>
                </DIV>
                
                 <!-- 明细表单 通用模板 -->
                <xsl:call-template name="common_table_head">
                    <xsl:with-param name="tbName" select="string('明细')" />
                </xsl:call-template>

                <div>
                    <FORM method="post" name="formOp" action="paramsThresAction.do">
                        <!-- 页面用到的变量 通用模板 -->
			<xsl:call-template name="common_web_variable" />
                        <DIV id="detail" align="center"
                             style="position:relative; left:20; width:95%; height:5; layer-background-color:#99CC99; border:thin outset #ffffff; z-index:1;">
                            <table width="100%" align="center" class="listDataTable">
                                <tr align="center" bgColor="#EFEFEF">
                                    <td width="12%">
                                        <div align="right">里程差比例阀值:</div>
                                    </td>
                                    <td width="10%">
                                        <div align="left">
                                            <input type="text" name="d_distanceThres" require="true" dataType="LimitB|double"
                                                  maxlength="8" style="width:100%" msg="里程差比例阀值必须输入非负数" />
                                        </div>
                                    </td>
                                    <td width="10%">
                                        <div align="right">站点差阀值:</div>
                                    </td>
                                    <td width="10%">
                                        <div align="left">
                                            <input type="text" name="d_stationThres" require="true" dataType="LimitB|Number"
                                                  maxlength="8" style="width:100%" msg="站点差阀值必须输入整数" />
                                        </div>
                                    </td>
                                    <td width="12%">
                                        <div align="right">换乘次数阀值:</div>
                                    </td>
                                    <td width="10%">
                                        <div align="left">
                                            <input type="text" name="d_changeThres" require="true" dataType="LimitB|Number"
                                                  maxlength="8" style="width:100%" msg="换乘次数阀值必须输入整数" />
                                        </div>
                                    </td>                              
                                    <td width="12%">
                                        <div align="right">乘车时间(s)阀值:</div>
                                    </td>
                                    <td width="10%">
                                        <div align="left">
                                            <input type="text" name="d_timeThres" require="true" dataType="LimitB|Number"
                                                  maxlength="8" style="width:100%" msg="换乘次数阀值必须输入整数" />
                                        </div>
                                    </td>
                                </tr>
                                <tr align="center" bgColor="#EFEFEF">
                                    <td width="10%">
                                        <div align="right">描述:</div>
                                    </td>
                                    <td  width="40%" colspan="5">
                                        <div align="left">
                                            <input type="text" name="d_description" require="true" style="width:100%"
                                                   maxlength="50"  dataType="LimitB" min="0" max="50" msg="参数描述不超过50字符"/>
                                        </div>
                                    </td>
                                    <td>
                                        <div align="right">ID:</div>
                                    </td>
                                    <td>
                                        <div align="left">
                                            <input type="text" name="d_id" disable="true"/>
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
                                        select="'setUpdatePrimaryKey(&quot;formOp&quot;,&quot;d_id&quot;);disablePrimaryKeys(&quot;formOp&quot;);'" />
                                <xsl:with-param name="clickMethod"
                                        select="'btnClick(&quot;formOp&quot;,&quot;clearStart&quot;,&quot;detail&quot;);'" />
                                <xsl:with-param name="addAfterMethod"
                                        select="'disablePrimaryKeys(&quot;formOp&quot;);'" />
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