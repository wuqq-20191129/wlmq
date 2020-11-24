<%-- 
    Document   : importDealLinePara
    Created on : 2017-6-22, 19:13:36
    Author     : liudz
--%>

<%@ page contentType="text/html; charset=GBK" language="java"%>
<%
    String msg = (String) session.getAttribute("Message");
    String terminator = (String) session.getAttribute("Terminator");
    if (terminator == null) {
        terminator = "";
    }
    String fileName = (String) session.getAttribute("FileName");
    if (fileName == null) {
        fileName = "";
    }
%>

<html>
    <head>
        <title>导入线路交易分配参数数据</title>
        <meta http-equiv="X-UA-Compatible" content="IE=9,Chrome=1" />
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <link rel="stylesheet" type="text/css" href="css/frame/simple.css" title="Style"/>
        <script language="javascript" type="text/javascript" charset="GBK" src="js/Validator.js"></script>
        <script language="javascript" type="text/javascript" charset="GBK" src="js/import.js"></script>
        <script language="javascript" type="text/javascript">
            function loading() {
                // alert('a');
                var frm = document.forms["formOp"];
                if(document.getElementById("makeFile").value===""){
                    alert("请选择要导入的文件");
                    return ;
                }
                if (Validator.Validate(frm,2))  {
                    frm.all['confirm'].disabled='true';
                    //alert( frm.all['makeFile'].value);
                    frm.all['_fileName'].value = frm.all['makeFile'].value;
                    flashs();
                    document.formOp.submit();
                }
            }
            
            function clearMsg(){
                var tdob =document.getElementById('retMsg');
                tdob.innerHTML="返回信息:<font color='red'></font>";
            }
        </script>
    </head>

    <body onLoad="setDefaultValue();">
        
            <table  class="table_title">
            <tr align="center" class="trTitle">
            <td>
                <div align="center"><strong>线路交易分配参数数据导入 </strong></div>
            </td>
        </tr>
    </table>
    <form name="formOp" method="post"
          action="import_DealLine.do?command=importLine"
          ENCTYPE="multipart/form-data"
          onSubmit="return Validator.Validate(this,2);" 
          >

        <table class="table_edit" style="margin-top: 15px;">
            <tr class="table_edit_tr">
            <td class="table_edit_tr_td_label" width="20%">
                <div align="right">字段分隔符:</div>
            </td>
            <td class="table_edit_tr_td_input" width="15%">
                <div align="left">
                    <select id="seperator"
                            name="seperator" dataType="Require" msg="工作类型不能为空!">
                        <option value="\t" selected="1">TAB分隔符</option>
                        <option value="," >逗号分隔符</option>
                    </select>
                </div>
            </td>
            <td colspan="2">
            </td>

            </tr>
            <tr class="table_edit_tr" align="center" >
            <td class="table_edit_tr_td_label" width="20%">
                <div align="right">字段顺序:</div>
            </td>
            <td class="table_edit_tr_td_label" colspan="3">
                <div align="left"> 
                    <font color="red">
                        起始线路,起始车站,目的线路,目的车站,线路,分账比例
                    </font>
                </div>
            </td>
            </tr>
            <tr  class="table_edit_tr" align="center">
            <td width="20%">
                <div align="right">导入文件:</div>
            </td>
            <td colspan="3">
                <div align="left"> 
                    <input name="makeFile" type="file" id="makeFile" 
                           size="50" dataType="Filter" msg="请选择(.txt)文本文件！" accept="txt">
                </div>
            </td>
            </tr>
        </table>
        &nbsp;
        <table width="95%" align="center">

            <tr  class="table_edit_tr" align="center" >
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
    </form>
    <table class="table_status" width="95%"  >
        <tr class="table_edit_tr"  >
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