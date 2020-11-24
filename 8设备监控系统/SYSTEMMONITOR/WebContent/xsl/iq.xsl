<?xml version="1.0" encoding="GBK"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:import href="common_variable.xsl"/>
<xsl:import href="common_template.xsl"/>
<xsl:import href="common_web_variable_template.xsl"/>

<xsl:template match="/">

<html>
<head>
	<title>SYBASE IQ状态</title>
	
	
	<script language="javascript" type="text/javascript" charset="GBK" src="./js/common_form.js"></script>
	
  <link rel="stylesheet" type="text/css" href="./xsl/css/simple.css" title="Style"/>

<script language="javascript">
</script> 

</head>

<body onload="initDocument('formOp','detail');setListViewDefaultValue('formOp','clearStart');">


<table width="95%" align="center" class="tableStyle">
	<tr align="center" class="trTitle">
	  <td>
	    SYBASE IQ状态
	  </td>
  </tr>
</table>




<!-- 表头 通用模板 -->
<xsl:call-template name="common_table_head">
	<xsl:with-param name="tbName" select="string('列表')"/>
</xsl:call-template>

<DIV id="clearStart" align="center" style="position:relative; left:20; width:95%; height:70%; layer-background-color:#99CC99; border:thin outset #ffffff; z-index:1; overflow: scroll;">
	<table class="listDataTable" id="DataTable" style="table-layout:fixed;word-break:break-all" align="left" width="100%">
	<tr class="trDataHead" id="ignore" height="0">   
    <td id=""   rowspan="3" align="center"  class="listTableHead"  onyes="head">数据库名称</td>
    <td id=""   rowspan="3" align="center"  class="listTableHead"  onyes="head">服务器IP</td>
    <td></td>
    <td></td>

    <td id=""   rowspan="3" align="center"  class="listTableHead"  onyes="head">全备份文件大小(G)</td>
    <td></td>
    <td></td>
     <td id=""  rowspan="3"   align="center"  class="listTableHead"  onyes="head">备份时长(min)</td>
     <td id=""  rowspan="3"   align="center"  class="listTableHead"  onyes="head">增量备份文件大小(G)</td>
     <td id=""  rowspan="3"   align="center"  class="listTableHead"  onyes="head">增量备份时长(min)</td>
     <td id=""  rowspan="3"   align="center"  class="listTableHead"  onyes="head">状态</td>
     <td id=""  rowspan="3"   align="center"  class="listTableHead"  onyes="head">状态时间</td>
   
  </tr>
  <tr class="trDataHead" id="ignore">
    <td id=""   colspan="2" align="center"  class="listTableHead"  onyes="head">数据空间</td>
    
    
    <td id=""  colspan="2" align="center"  class="listTableHead"  onyes="head">备份起止时间</td>
  </tr>

  <tr class="trDataHead" id="ignore">
    <td id=""   align="center"  class="listTableHead"  onyes="head">使用(G)</td>
    <td id=""     align="center"  class="listTableHead"  onyes="head">使用率(%)</td>

    <td id=""   align="center"  class="listTableHead"  onyes="head">开始</td>
    <td id=""  align="center"  class="listTableHead"  onyes="head">完成</td>
    
    
  </tr>

  


  
  
  
  <xsl:for-each select="/Service/Result/Statues/iqVo">
		<tr  id="moduleRow" class="trData" onClick="clickResultRow('formOp',this,'detail');" onMouseOver="overResultRow('formOp',this);" onMouseOut="outResultRow('formOp',this);" >
 			<xsl:attribute name="id">
 				<xsl:value-of select="ip"/>
 			</xsl:attribute>
	 	 	
    	<td id="name"   align="left"  ><xsl:value-of select="name"/></td>
    	<td id="ip"  align="left"  ><xsl:value-of select="ip"/></td>
    	<td id="freeData"  align="left"  ><xsl:value-of select="freeData"/></td>
    	<td id="usedDataRate"  align="left"  ><xsl:value-of select="usedDataRate"/></td>
    	
    	<td id="backupFulSize"  align="left"  ><xsl:value-of select="backupFulSize"/></td>
    	<td id="backupFulStartTime"  align="left"  ><xsl:value-of select="backupFulStartTime"/></td>
    	<td id="backupFulEndTime"  align="left"  ><xsl:value-of select="backupFulEndTime"/></td>
    	<td id="backupFulInterval"  align="left"  ><xsl:value-of select="backupFulInterval"/></td>  
    	<td id="backupIncSize"  align="left"  ><xsl:value-of select="backupIncSize"/></td>	
    	<td id="backupIncInterval"  align="left"  ><xsl:value-of select="backupIncInterval"/></td> 
    	<td id="status"   align="left"  >
    	  <xsl:choose>
    	     <xsl:when test="not(status='0')">
    	        <font color="red">
    	        <b>
    	          <xsl:value-of select="statusText"/>
    	        </b>
    	        </font>
    	     </xsl:when>
    	     <xsl:otherwise>
    	        <xsl:value-of select="statusText"/>
    	     </xsl:otherwise>
    	  </xsl:choose>
    	   
    	</td> 
    	<td id="statusDate"   align="left"  ><xsl:value-of select="statusDate"/></td>
    	
    	
  	</tr>	
  </xsl:for-each>


	</table>
</DIV>




<FORM method="post" name="formOp" id="formOp" action="menuManage.do">
	<!-- 页面用到的变量 通用模板 -->
	<xsl:call-template name="common_web_variable" />
	

<xsl:call-template name="op_button_list_gen">
	
	<xsl:with-param name="print" select="1"/>
	<xsl:with-param name="quit" select="1"/>
	
	<xsl:with-param name="clickMethod" select="'btnClick(&quot;formOp&quot;,&quot;clearStart&quot;,&quot;detail&quot;);'"/>	
</xsl:call-template>

<br/>

</FORM>

<!-- 状态栏 通用模板 -->
<xsl:call-template name="common_status_table">
</xsl:call-template>


</body>
</html>

</xsl:template>
</xsl:stylesheet>
