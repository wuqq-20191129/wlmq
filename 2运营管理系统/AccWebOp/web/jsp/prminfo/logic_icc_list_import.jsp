<%-- 
    Document   : logic_icc_list_import.jsp
    Created on : 2017-6-21
    Author     : xiaowu
--%>
<%@ page contentType="text/html; charset=UTF-8" language="java"%>
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
        <meta http-equiv="X-UA-Compatible" content="IE=9,Chrome=1" />
        <title>导入逻辑卡号刻印号对照表数据</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <link rel="stylesheet" type="text/css" href="css/frame/simple.css" title="Style"/>
        <script language="javascript" type="text/javascript" charset="GBK" src="js/Validator.js"></script>
        <script language="javascript" type="text/javascript" charset="GBK" src="js/import.js"></script>
        <script language="javascript" type="text/javascript">
            function loadingPhyNew() {
                // alert('a');
                var frm = document.forms["formOp"];
                if(document.getElementById("makeFile").value===""){
                    alert("请选择要导入的文件");
                    return ;
                }
                if (Validator.Validate(frm,2))  {
                    document.getElementById("confirm").disabled='true';
                    //alert( document.getElementById("makeFile").value);
                    document.getElementById("_fileName").value = document.getElementById("makeFile").value;
                    flashs();
                    document.formOp.submit();
                }
            }
            function clearMsgContent(){
                var tdob =document.getElementById('retMsg');
                tdob.innerHTML="返回信息:<font color='red'></font>";
            }
        </script>
    </head>

    <body >
        <table  class="table_title">
            <tr align="center" class="trTitle">
                <td colspan="4" style="font-weight: bold;font-size: 18px;">逻辑卡号刻印号对照表导入</td>
            </tr>
        </table>
        
    <form name="formOp" method="post"       
          action="LogicIccListUpload?command=import&&ModuleID=010511"
          ENCTYPE="multipart/form-data"
          onSubmit="return Validator.Validate(this,2);"
          >

        <table class="table_edit" style="margin-top: 15px;">
            <input type="hidden" id="seperator"  name="seperator" value=","/>
            <tr class="table_edit_tr">
                <td class="table_edit_tr_td_label">
                    导入文件:
                </td>
                <td class="table_edit_tr_td_input">
                    <input name="makeFile" type="file" id="makeFile" size="50" />
                </td>
            </tr>
        </table>   
        <table width="95%" align="center">
            <tr align="center" >
                <td><div align="right">
                        <input id="confirm" name="confirm" type="button" value="确定" onclick="clearMsgContent();loadingPhyNew();">
                    </div>
                </td>
                <td><div align="left">
                        <input id="close" name="close" type="button" value="取消" onclick="window.close();">
                    </div>
                </td>
            </tr>
        </table>
        <input type="hidden" id="_terminator" name="_terminator" value="<%=terminator%>"/>
        <input type="hidden" id="_fileName" name="_fileName" value="<%=fileName%>}"/>
    </form>


    <table class="table_status">
        <tr>
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