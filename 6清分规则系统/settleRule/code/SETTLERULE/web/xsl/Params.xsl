<?xml version="1.0" encoding="GBK"?>

<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:import href="common_template.xsl" />
    <xsl:import href="common_web_variable_template.xsl" />
    
    <xsl:template match="/">

        <html>
            <head>
                <title>��������</title>
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
										setPrimaryKeys('formOp','d_typeCode#d_code');">
                
                <table width="95%" align="center" class="tableStyle">
                    <tr align="center" class="trTitle">
                        <td colspan="2">��������</td>
                    </tr>
                </table>
                <br/>
                
                 <!-- ��ѯ�� ͨ��ģ�� -->
                <xsl:call-template name="common_table_head">
                    <xsl:with-param name="tbName" select="string('��ѯ')" />
                </xsl:call-template>
                
                <div style="height:2%;">
                    <FORM method="post" name="formQuery" action="paramsAction.do">
                        <!-- ҳ���õ��ı��� ͨ��ģ�� -->
                        <xsl:call-template name="common_web_variable" />
                        <table width="95%" align="center" class="tableStyle">
                            <tr class="trContent">
                                <td width="10%">
                                    <div align="right">���ʹ���:</div>
                                </td>
                                <td width="8%">
                                    <div align="left">
                                        <input type="text" id="q_typeCode"
                                                name="q_typeCode" value="" style="width:100%" maxlength="2"
                                                require="false" dataType="LimitB" min="0" max="2"
                                                msg="���ʹ���ӦΪ1λ����2λ" />
                                    </div>
                                </td>

                               
                                <td width="10%">
                                    <div align="right">��������:</div>
                                </td>
                                <td width="8%">
                                    <div align="left">
                                        <input type="text" id="q_code"
                                                name="q_code" value="" style="width:100%" maxlength="2"
                                                require="false" dataType="LimitB" min="0" max="2"
                                                msg="����ӦΪ1λ����2λ" />
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
                                
                                                    <td width="10%">
                                                            <div align="right">����ʱ��:</div>
                                                    </td>
                                                    <td colspan="3">
                                                            <div align="left">
                                                                    <input type="text"
                                                                            name="q_beginOpTime" id="q_beginOpTime" value="" style="width:40%"
                                                                            require="false" dataType="Date" format="ymd"
                                                                            msg="�������ڸ�ʽΪ(YYYY-MM-dd)!" />
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
                                                                            msg="�������ڸ�ʽΪ(YYYY-MM-dd)�Ҵ��ڵ��ڿ�ʼʱ��,��ѯ��Χ90��!" />
                                                                    <a
                                                                            href="javascript:openCalenderDialog(document.all.q_endOpTime);">
                                                                            <img
                                                                                    src="./images/calendar.gif" width="12" height="15"
                                                                                    border="0" style="block" />
                                                                    </a>
                                                            </div>
                                                    </td>                                                  
                               
                              
                                <td align="center" width="10%" rowspan="2">
                                    <!-- ҳ���õ��ı��� ͨ��ģ�� -->
                                    <div align="center">
                                        <xsl:call-template name="op_button_list_gen">
                                            
                                              <xsl:with-param name="query" select="1" />
                                              <xsl:with-param name="export" select="0" />
                                           
                                            <xsl:with-param name="addClickMethod"
                                                select="'setControlNames(&quot;formQuery&quot;,&quot;q_typeCode#q_code#q_recorq_flag#q_beginOpTime#q_endOpTime&quot;);'" />
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
                        style="position:relative; left:20; width:95%; height:100%; layer-background-color:#99CC99; border:thin outset #ffffff; z-index:1; overflow: scroll;">

                    <table class="listDataTable" id="groupTable" align="left" width="100%">

                        <tr class="trDataHead" id="ignore" >
                            <td align="center" nowrap="1" width="5%">
                                <input type="checkbox" name="rectNoAll"
                                        onClick="selectAllRecord('formOp','rectNoAll','rectNo','clearStart',0);
                                        clearTextByFlag('formOp',['d_typeCode','d_code']); controlsByFlag('formOp',['del','audit','modify']);" />
                            </td>
                            <td align="center" nowrap="1" width="5%" class="listTableHead" onyes="head"
                                    onclick="JM_PowerListByIDs(this.parentElement.parentElement.parentElement.parentElement,this.parentElement.parentElement.parentElement,1)">
                                    ���ʹ���
                            </td>
                            <td align="center" nowrap="1" width="15%" class="listTableHead" onyes="head"
                                    onclick="JM_PowerListByIDs(this.parentElement.parentElement.parentElement.parentElement,this.parentElement.parentElement.parentElement,2)">
                                    ��������
                            </td>
                            <td align="center" nowrap="1" width="5%" class="listTableHead" onyes="head"
                                    onclick="JM_PowerListByIDs(this.parentElement.parentElement.parentElement.parentElement,this.parentElement.parentElement.parentElement,3)">
                                    ��������
                            </td>
                            <td align="center" nowrap="1" width="10%" class="listTableHead" onyes="head"
                                    onclick="JM_PowerListByIDs(this.parentElement.parentElement.parentElement.parentElement,this.parentElement.parentElement.parentElement,4)">
                                     ����ֵ
                            </td>
                         
                           <td align="center" nowrap="1" width="10%" class="listTableHead" onyes="head"
                                    onclick="JM_PowerListByIDs(this.parentElement.parentElement.parentElement.parentElement,this.parentElement.parentElement.parentElement,5)">
                                     ��������
                            </td>
                             
                            <td align="center" nowrap="1" width="5%" class="listTableHead" onyes="head"
                                    onclick="JM_PowerListByIDs(this.parentElement.parentElement.parentElement.parentElement,this.parentElement.parentElement.parentElement,6)">
                                     �汾״̬
                            </td>
                            <td align="center" nowrap="1" width="8%" class="listTableHead" onyes="head"
                                    onclick="JM_PowerListByIDs(this.parentElement.parentElement.parentElement.parentElement,this.parentElement.parentElement.parentElement,7)">
                                     �汾
                            </td>
                            <td align="center" nowrap="1" width="10%" class="listTableHead" onyes="head"
                                    onclick="JM_PowerListByIDs(this.parentElement.parentElement.parentElement.parentElement,this.parentElement.parentElement.parentElement,8)">
                                     ����ʱ��
                            </td>
                            <td align="center" nowrap="1" width="8%" class="listTableHead" onyes="head"
                                    onclick="JM_PowerListByIDs(this.parentElement.parentElement.parentElement.parentElement,this.parentElement.parentElement.parentElement,9)">
                                     ������
                            </td>
                        </tr>

                        <!-- ��ʾ��ѯ��� -->
                        <xsl:for-each select="Service/Result/params/paramsVo">
                             <tr optype="" modified="" onyes="detail" bgColor="#fdfdfd" style="display:" 
                            onMouseOver="overResultRow('formOp',this);" onMouseOut="outResultRow('formOp',this);" 
                            onclick="clickResultRow('formOp',this,'detail')" >
                                <xsl:attribute name="id">
                                    <xsl:value-of select="typeCode" />
                                    <xsl:value-of select="code" />
                                    <xsl:value-of select="version" />
                                </xsl:attribute>

                                <td align="center" nowrap="1" id="rectNo1">
                                    <input type="checkbox" name="rectNo"
                                            onclick="unSelectAllRecord('formOp','rectNoAll','rectNo');controlsByFlag('formOp',['del','audit','modify']);">
                                            <xsl:attribute name="value">
                                                <xsl:value-of select="typeCode" />
                                                <xsl:value-of select="code" />
                                                <xsl:value-of select="version" />
                                            </xsl:attribute>
                                            <xsl:attribute name="flag">
                                                <xsl:value-of select="recordFlag" />
                                            </xsl:attribute>
                                    </input>
                                </td>

                                <td align="center" nowrap="1" id="typeCode"> <xsl:value-of select="typeCode" /> </td>
                                <td align="center" nowrap="1" id="typeName"> <xsl:value-of select="typeName" /> </td>
                                <td align="center" nowrap="1" id="code"> <xsl:value-of select="code" /> </td>
                                <td align="center" nowrap="1" id="value"> <xsl:value-of select="value" /> </td>
                                <td align="center" nowrap="1" id="description"> <xsl:value-of select="description" /> </td>
                                <td align="center" nowrap="1" id="recordFlag"> <xsl:value-of select="recordFlagText" /> </td>
                                <td align="center" nowrap="1" id="version"> <xsl:value-of select="version" /> </td>
                                <td align="center" nowrap="1" id="createTime"> <xsl:value-of select="createTime" /> </td>
                                <td align="center" nowrap="1" id="operator"> <xsl:value-of select="operator" /> </td>
                            </tr>
                        </xsl:for-each>

                    </table>
                </DIV>
              <br/>
                 <!-- ��ϸ�� ͨ��ģ�� -->
                <xsl:call-template name="common_table_head">
                    <xsl:with-param name="tbName" select="string('��ϸ')" />
                </xsl:call-template>

                <div style="height:2%;">
                    <FORM method="post" name="formOp" action="paramsAction.do">
                        <!-- ҳ���õ��ı��� ͨ��ģ�� -->
			<xsl:call-template name="common_web_variable" />
                        <DIV id="detail" align="center"
                             style="position:relative; left:20; width:95%; height:5; layer-background-color:#99CC99; border:thin outset #ffffff; z-index:1;">
                            <table width="100%" align="center" class="listDataTable">
                                <tr align="center" bgColor="#EFEFEF">
                                    <td width="8%">
                                        <div align="right">���ʹ���:</div>
                                    </td>
                                    <td width="5%">
                                        <div align="left">
                                            <input type="text" name="d_typeCode" style="width:100%" require="true" 
                                                   maxlength="2" dataType="LimitB" regexp="(^\d\d$)" min="1" max="2" 
                                                   msg="���ʹ��벻��Ϊ�գ���С�ڵ��������ַ�����ĸ��������ɡ�" />
                                        </div>
                                    </td>
                                    <td width="8%">
                                        <div align="right">��������:</div>
                                    </td>
                                    <td width="10%">
                                        <div align="left">
                                            <input type="text" name="d_typeName" style="width:100%" require="true" 
                                                   maxlength="30" dataType="LimitB" min="1" max="30" 
                                                   msg="������������Ϊ�գ����Ȳ�����30���ַ�" />
                                        </div>
                                    </td>
                                    <td width="8%">
                                        <div align="right">��������:</div>
                                    </td>
                                    <td width="5%">
                                        <div align="left">
                                            <input type="text" name="d_code" style="width:100%" require="true" 
                                                   maxlength="2" dataType="LimitB" regexp="(^\d\d$)" min="1" max="2" 
                                                   msg="�������벻��Ϊ�գ��Ҳ�������λ" />
                                        </div>
                                    </td>                              
                                    <td width="8%">
                                        <div align="right">����ֵ:</div>
                                    </td>
                                    <td width="10%">
                                        <div align="left">
                                            <input type="text" name="d_value" style="width:100%" require="true" 
                                                   maxlength="30" dataType="LimitB" min="1" max="30" 
                                                   msg="����ֵ����Ϊ�գ�����ֵ������30���ַ�" />
                                        </div>
                                    </td>
                                    <td width="8%">
                                        <div align="right">��������:</div>
                                    </td>
                                    <td width="10%">
                                        <div align="left">
                                            <input type="text" name="d_description" style="width:100%" require="true"
                                                   maxlength="30"  dataType="LimitB" min="0" max="30" 
                                                   msg="��������������30���ַ�"/>
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
                                        select="'setUpdatePrimaryKey(&quot;formOp&quot;,&quot;d_typeCode#d_code&quot;);disablePrimaryKeys(&quot;formOp&quot;);'" />
                                <xsl:with-param name="clickMethod"
                                        select="'btnClick(&quot;formOp&quot;,&quot;clearStart&quot;,&quot;detail&quot;);'" />
                                <xsl:with-param name="addAfterMethod"
                                        select="'enablePrimaryKeys(&quot;formOp&quot;);'" />

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