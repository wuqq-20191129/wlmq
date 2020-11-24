<%@ page contentType="text/html; charset=UTF-8" language="java"%>
<%
    String msg = (String) session.getAttribute("Message");
    if (msg == null) {
        msg = "";
    }
    String terminator = (String) session.getAttribute("Terminator");
    if (terminator == null) {
        terminator = "";
    }
    String fileName = (String) session.getAttribute("FileName");
    if (fileName == null) {
        fileName = "";
    }
    String paramTypeId = (String)request.getParameter("paramTypeId");
    if(paramTypeId==null){
        paramTypeId = (String) session.getAttribute("paramTypeId");
    }
    String paramTypeName="";
    String paramModule="";
    if(paramTypeId!=null){
        if(paramTypeId.equals("9120")){
            paramTypeName = "AGM读卡器程序";
            paramModule="011001";
        }else if(paramTypeId.equals("9220")){
            paramTypeName = "TVM读卡器程序";
            paramModule="011002";
        }else if(paramTypeId.equals("9320")){
            paramTypeName = "BOM读卡器程序";
            paramModule="011003";
        }else if(paramTypeId.equals("9420")){
            paramTypeName = "AQM读卡器程序";
            paramModule="011004";
        }else if(paramTypeId.equals("9520")){
            paramTypeName = "PCA读卡器程序";
            paramModule="011005";
        }else if(paramTypeId.equals("8100")){
            paramTypeName = "TVM地图参数";
            paramModule="011006";
        }
    }
%>

<html>
    <head>
        <title>导入<%=paramTypeName%></title>
        <meta http-equiv="X-UA-Compatible" content="IE=9,Chrome=1" />
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />

        <link rel="stylesheet" type="text/css" href="xsl/css/simple.css" title="Style" />
        <script language="javascript" type="text/javascript" charset="GBK" src="js/Validator.js"></script>
        <script language="javascript" type="text/javascript" charset="GBK" src="js/import.js"></script>
    </head>

    <body >
        <table width="90%" border="0" align="center">
            <tr>
            <td>
                <div align="center"><strong><%=paramTypeName%>导入 </strong></div>
            </td>
        </tr>
    </table>
    <form name="formOp" method="post"
          action="handleFileUpload?command=import&ModuleID=<%=paramModule%>"
          ENCTYPE="multipart/form-data"
          
          >

        <table width="95%" align="center"
               class="listDataTable">
            <input type="hidden" id="seperator"  name="seperator" value=","/>
            <tr align="center" bgColor="#EFEFEF">
            <td width="20%">
                <div align="right">导入文件:</div>
            </td>
            <td colspan="3">
                <div align="left"> 
                    <input name="makeFile" type="file" id="makeFile" 
                           size="50" dataType="Filter" >
                </div>
            </td>
            </tr>
        </table>
        &nbsp;
        <table width="95%" align="center">

            <tr align="center" >
            <td><div align="right">
                    <input name="confirm" type="button" value="确定" onclick="clearMsg();loading();">
                </div>
            </td>
            <td><div align="left">
                    <input name="close" type="button" value="取消" onclick="window.close();">
                </div>
            </td>



            </tr>
        </table>
        <input type="hidden" name="_terminator" value="<%=terminator%>"/>
        <input type="hidden" name="_fileName" value="<%=fileName%>"/>
        
        <input type="hidden" name="paramTypeId" value="<%=paramTypeId%>"/>
    </form>


    <table width="95%" align="center"  >
        <tr bgcolor="#D1D1D1">
        <td width="20%" id="retMsg" class="status-font">返回信息:
            <font color="red">
               
               
                ${msg}
            </font>
        </td>
    </tr>
</table>

<div align="center" style="font-size:9pt;display:none;color:red" id="runPrompt">
    正在导入数据.............
</div>

</body>
</html>