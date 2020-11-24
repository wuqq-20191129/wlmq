<?xml version="1.0" encoding="GBK"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:import href="common_variable.xsl"/>
<xsl:import href="common_template.xsl"/>
<xsl:import href="common_web_variable_template.xsl"/>

<xsl:template match="/">

<html>
<head>
	<title>ϵͳģ��</title>
	
	
	<script language="javascript" type="text/javascript" charset="UTF-8" src="./js/common_form.js"></script>
	<script language="javascript" type="text/javascript" charset="UTF-8" src="./js/Validator.js"></script>
  <link rel="stylesheet" type="text/css" href="./xsl/css/simple.css" title="Style"/>

<script language="javascript">
</script> 

</head>

<body onload="initDocument('moduleQry','detail');initDocument('moduleOp','detail');setPrimaryKeys('moduleOp','d_moduleID');setControlsDefaultValue('moduleQry');setListViewDefaultValue('moduleOp','clearStart');setQueryControlsDefaultValue('moduleQry','moduleOp');">


<table width="95%" align="center" class="tableStyle">
	<tr align="center" class="trTitle">
	  <td>
	    ϵͳģ��
	  </td>
  </tr>
</table>

<xsl:call-template name="common_table_head">
	<xsl:with-param name="tbName" select="string('��ѯ')"/>
</xsl:call-template>
<FORM method="post" name="moduleQry" id="moduleQry" action="menuManage.do" >
		<xsl:call-template name="common_web_variable" />
		<table width="95%" align="center" class="tableStyle">
			<tr class="trContent">
					<td width="10%"><div align="right">ģ�����:</div></td>
    			<td width="10%"><div align="left"><input type="text" name="q_moduleID" id="q_moduleID" size="10" require="false" maxLength="6" dataType="Number|LimitB" min="1" max="6" msg="ģ����벻��Ϊ�������Ϊ6�������ַ�"/></div></td>
    			<td width="10%"><div align="right">ģ������:</div></td>
    			<td width="10%"><div align="left"><input type="text" name="q_name" id="q_name" size="10" require="false" dataType="LimitB" min="1" msg="ģ�����Ʋ���Ϊ��"/></div></td>
    			<td width="10%"><div align="right">һ��ģ��ID:</div></td>
    			<td width="10%"><div align="left"><input type="text" name="q_topID" id="q_topID" size="10" maxLength="2" require="false" dataType="Number|LimitB"  msg="һ��ģ��IDӦΪ�����ַ�����󳤶�Ϊ2"/></div></td>
    	</tr>
    	<tr class="trContent">
    			<td width="10%"><div align="right">�ϼ�ģ��ID:</div></td>
    			<td width="10%"><div align="left"><input type="text" name="q_parentID" id="q_parentID" size="10" maxLength="4" require="false" dataType="Number|LimitB"   min="1" max="4" msg="�ϼ�ģ��IDӦΪ�����ַ�����󳤶�Ϊ4"/></div></td>
    			<td align="center" width="10%">
						<xsl:call-template name="op_button_list_gen">
						<xsl:with-param name="query" select="1"/>
						<xsl:with-param name="export" select="0"/>
						<xsl:with-param name="addClickMethod" select="'setControlNames(&quot;moduleQry&quot;,&quot;q_moduleID#q_name#q_topID#q_parentID&quot;)'"/> 
						<xsl:with-param name="clickMethod" select="'btnClick(&quot;moduleQry&quot;,&quot;clearStart&quot;,&quot;detail&quot;);'"/>	
						</xsl:call-template>
			 		</td>
			 		<td colspan="3">
			 		</td>
			 		
			</tr>
		</table>
</FORM>


<!-- ��ͷ ͨ��ģ�� -->
<xsl:call-template name="common_table_head">
	<xsl:with-param name="tbName" select="string('�б�')"/>
</xsl:call-template>

<DIV id="clearStart" align="center" style="position:relative; left:20; width:95%; height:40%; layer-background-color:#99CC99; border:thin outset #ffffff; z-index:1; overflow: scroll;">
	<table class="listDataTable" id="DataTable" align="left">
	  <tr class="trDataHead" id="ignore">
    <td width="5%"  align="center" nowrap="1" ><input type="checkbox" name="rectNoAll" onclick="selectAllRecord('moduleOp','rectNoAll','rectNo','clearStart',0);"/></td>
    <td id="moduleTd" width="10%"  align="center" nowrap="1" class="listTableHead" onclick="JM_PowerListByIDs(this.parentElement.parentElement.parentElement.parentElement,this.parentElement.parentElement.parentElement,1)" onyes="head">ģ�����</td>
    <td id="moduleTd" width="10%"  align="center" nowrap="1" class="listTableHead" onclick="JM_PowerListByIDs(this.parentElement.parentElement.parentElement.parentElement,this.parentElement.parentElement.parentElement,2)" onyes="head">ģ������</td>
    <td id="moduleTd" width="10%"  align="center" nowrap="1" class="listTableHead" onclick="JM_PowerListByIDs(this.parentElement.parentElement.parentElement.parentElement,this.parentElement.parentElement.parentElement,3)" onyes="head">һ��ģ��ID</td>
    <td id="moduleTd" width="10%"  align="center" nowrap="1" class="listTableHead" onclick="JM_PowerListByIDs(this.parentElement.parentElement.parentElement.parentElement,this.parentElement.parentElement.parentElement,4)" onyes="head">�ϼ�ģ��ID</td>
    <td id="moduleTd" width="10%"  align="center" nowrap="1" class="listTableHead" onclick="JM_PowerListByIDs(this.parentElement.parentElement.parentElement.parentElement,this.parentElement.parentElement.parentElement,5)" onyes="head">����·��</td>
    <td id="moduleTd" width="10%"  align="center" nowrap="1" class="listTableHead" onclick="JM_PowerListByIDs(this.parentElement.parentElement.parentElement.parentElement,this.parentElement.parentElement.parentElement,6)" onyes="head">���ͼ��</td>
    <td id="moduleTd" width="10%"  align="center" nowrap="1" class="listTableHead" onclick="JM_PowerListByIDs(this.parentElement.parentElement.parentElement.parentElement,this.parentElement.parentElement.parentElement,7)" onyes="head">����״̬</td>
    <td id="moduleTd" width="10%"  align="center" nowrap="1" class="listTableHead" onclick="JM_PowerListByIDs(this.parentElement.parentElement.parentElement.parentElement,this.parentElement.parentElement.parentElement,8)" onyes="head">ʹ���´���</td>
  </tr>
  <xsl:for-each select="/Service/Result/Modules/menu">
		<tr  id="moduleRow" class="trData" onClick="clickResultRow('moduleOp',this,'detail');" onMouseOver="overResultRow('moduleOp',this);" onMouseOut="outResultRow('moduleOp',this);" >
 			<xsl:attribute name="id">
 				<xsl:value-of select="menuId"/>
 			</xsl:attribute>
	 	 	<td width="30"  align="center" nowrap="1"><input type="checkbox" name="rectNo" onClick="unSelectAllRecord('moduleOp','rectNoAll','rectNo');"/></td>
    	<td id="moduleID" width="150"  align="center" nowrap="1" ><xsl:value-of select="menuId"/></td>
    	<td id="name" width="150"  align="center" nowrap="1" ><xsl:value-of select="menuName"/></td>
    	<td id="topID" width="150"  align="center" nowrap="1" ><xsl:value-of select="topMenuId"/></td>
    	<td id="parentID" width="150"  align="center" nowrap="1" ><xsl:value-of select="parentId"/></td>
    	<td id="url" width="150"  align="center" nowrap="1" ><xsl:value-of select="url"/></td>
    	<td id="icon" width="150"  align="center" nowrap="1" ><xsl:value-of select="icon"/></td>   	
    	<td id="locked" width="150"  align="center" nowrap="1" ><xsl:value-of select="lockedText"/></td>
    	<td id="newWindowFlag" width="150"  align="center" nowrap="1" ><xsl:value-of select="newWindowFlagText"/></td>
  	</tr>	
  </xsl:for-each>

		<tr id="ignore">
			<td align="right" colSpan="13"></td>
		</tr>

	</table>
</DIV>


<!-- ��ͷ ͨ��ģ�� -->
<xsl:call-template name="common_table_head">
	<xsl:with-param name="tbName" select="string('��ϸ')"/>
</xsl:call-template>

<FORM method="post" name="moduleOp" id="moduleOp" action="menuManage.do">
	<!-- ҳ���õ��ı��� ͨ��ģ�� -->
	<xsl:call-template name="common_web_variable" />
	<DIV id="detail" align="center" style="position:relative; left:20; width:95%; height:10; layer-background-color:#99CC99; border:thin outset #ffffff; z-index:1;">
	<table align="center" class="listDataTable" >
  	<tr align="center" bgColor="#EFEFEF">
    	<td width="10%"><div align="right">ģ�����:</div></td>
    	<td width="10%"><div align="left"><input type="text" name="moduleID" id="d_moduleID" size="10" maxLength="6" dataType="Number|LimitB" min="1" max="6" msg="ģ����벻��Ϊ�������Ϊ6�������ַ�"/></div></td>
    	<td width="10%"><div align="right">ģ������:</div></td>
    	<td width="10%"><div align="left"><input type="text" name="name" id="d_name" size="10" require="true" dataType="LimitB" min="1" msg="ģ�����Ʋ���Ϊ��"/></div></td>
    	<td width="10%"><div align="right">һ��ģ��ID:</div></td>
    	<td width="10%"><div align="left"><input type="text" name="topID" id="d_topID" size="10" maxLength="2" require="false" dataType="Number|LimitB"  msg="һ��ģ��IDӦΪ�����ַ�����󳤶�Ϊ2"/></div></td>
    	<td width="10%"><div align="right">�ϼ�ģ��ID:</div></td>
    	<td width="10%"><div align="left"><input type="text" name="parentID" id="d_parentID" size="10" maxLength="4" require="false" dataType="Number|LimitB"   min="1" max="4" msg="�ϼ�ģ��IDӦΪ�����ַ�����󳤶�Ϊ4"/></div></td>
		</tr>
		<tr align="center" bgColor="#EFEFEF">
    	<td width="10%"><div align="right">����·��:</div></td>
    	<td width="10%"><div align="left"><input type="text" name="url" id="d_url" size="10" require="false" dataType="LimitB" min="1" /></div></td>
    	<td width="10%"><div align="right">���ͼ��:</div></td>
    	<td width="10%"><div align="left"><input type="text" name="icon" id="d_icon" size="10" require="false" dataType="LimitB" min="1"/></div></td>
    	<td width="10%"><div align="right">����״̬:</div></td>
    	<td width="10%"><div align="left">
    		<select id="d_locked" name = "locked" require="true" dataType="LimitB" min="1" msg="û��ѡ������״̬">
        	<xsl:call-template name="option_list_pub">
          	<xsl:with-param name="root" select="/Service/Result/Locks/pubFlagVo"/>
          </xsl:call-template>
				</select></div>
    	</td>
		</tr>
		<tr align="center" bgColor="#EFEFEF">
		<td width="10%"><div align="right">ʹ���´���:</div></td>
    	<td width="10%"><div align="left">
    		<select id="d_newWindowFlag" name = "newWindowFlag" require="true" dataType="LimitB" min="1" msg="û��ѡ���Ƿ�ʹ���´���">
        	<xsl:call-template name="option_list_pub">
        	
        	<xsl:with-param name="selectvalue" select="0"/>
          	<xsl:with-param name="root" select="/Service/Result/NewWindowFlags/pubFlagVo"/>
          </xsl:call-template>
				</select></div>
    	</td>
		<td width="10%"><div align="right">url����:</div></td>
		<td width="10%" colspan="5"><div align="left"><input type="text" name="d_postParameter" id="d_postParameter" size="80" require="false" dataType="LimitB" min="1" max="255"/></div></td>
		</tr>
	</table>
</DIV>

<xsl:call-template name="op_button_list_gen">
	<xsl:with-param name="add" select="1"/>
	<xsl:with-param name="del" select="1"/>
	<xsl:with-param name="modify" select="1"/>
	<xsl:with-param name="save" select="1"/>
	<xsl:with-param name="cancle" select="1"/>
	<xsl:with-param name="print" select="1"/>
	<xsl:with-param name="quit" select="1"/>
	<xsl:with-param name="addClickMethod" select="'setUpdatePrimaryKey(&quot;moduleOp&quot;,&quot;moduleID&quot;);'"/>
	<xsl:with-param name="clickMethod" select="'btnClick(&quot;moduleOp&quot;,&quot;clearStart&quot;,&quot;detail&quot;);'"/>	
</xsl:call-template>

<br/>

</FORM>

<!-- ״̬�� ͨ��ģ�� -->
<xsl:call-template name="common_status_table">
</xsl:call-template>


</body>
</html>

</xsl:template>
</xsl:stylesheet>
