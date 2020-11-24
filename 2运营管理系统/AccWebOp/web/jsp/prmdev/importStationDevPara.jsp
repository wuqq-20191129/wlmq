<%-- 
    Document   : importStationDevPara
    Created on : 2018-3-5
    Author     : xiaowu
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
        <meta http-equiv="X-UA-Compatible" content="IE=9,Chrome=1" />
        <title>���복վ�豸��������</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <link rel="stylesheet" type="text/css" href="css/frame/simple.css" title="Style"/>
        <script language="javascript" type="text/javascript" charset="GBK" src="js/Validator.js"></script>
        <script language="javascript" type="text/javascript" charset="GBK" src="js/import.js"></script>
        <script language="javascript" type="text/javascript">
            function loading() {
                // alert('a');
                var frm = document.forms["formOp"];
                if(document.getElementById("makeFile").value===""){
                    alert("��ѡ��Ҫ������ļ�");
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
                tdob.innerHTML="������Ϣ:<font color='red'></font>";
            }
        </script>
    </head>

    <body onLoad="setDefaultValue();">
        
            <table  class="table_title">
            <tr align="center" class="trTitle">
            <td>
                <div align="center"><strong>��վ�豸�������ݵ��� </strong></div>
            </td>
        </tr>
    </table>
    <form name="formOp" method="post"
          action="import_StationDevice.do?command=importStationDevice"
          ENCTYPE="multipart/form-data"
          onSubmit="return Validator.Validate(this,2);" 
          >

        <table class="table_edit" style="margin-top: 15px;">
            <tr class="table_edit_tr">
            <td class="table_edit_tr_td_label" width="20%">
                <div align="right">�ֶηָ���:</div>
            </td>
            <td class="table_edit_tr_td_input" width="15%">
                <div align="left">
                    <select id="seperator"
                            name="seperator" dataType="Require" msg="�������Ͳ���Ϊ��!">
                        <option value="\t" selected="1">TAB�ָ���</option>
                        <option value="," >���ŷָ���</option>
                    </select>
                </div>
            </td>
            <td colspan="2">
            </td>

            </tr>
            <tr class="table_edit_tr" align="center" >
            <td class="table_edit_tr_td_label" width="20%">
                <div align="right">�ֶ�˳��:</div>
            </td>
            <td class="table_edit_tr_td_label" colspan="3">
                <div align="left"> 
                    <font color="red">
                        ��·ID,��վID,�豸����ID,�豸ID,�豸IP��ַ,�豸����,�豸���к�,բ��/TVM���б���,��վ��������
                    </font>
                </div>
            </td>
            </tr>
            <tr  class="table_edit_tr" align="center">
            <td width="20%">
                <div align="right">�����ļ�:</div>
            </td>
            <td colspan="3">
                <div align="left"> 
                    <input name="makeFile" type="file" id="makeFile" 
                           size="50" dataType="Filter" msg="��ѡ��(.txt)�ı��ļ���" accept="txt">
                </div>
            </td>
            </tr>
        </table>
        &nbsp;
        <table width="95%" align="center">

            <tr  class="table_edit_tr" align="center" >
            <td><div align="right">
                    <input name="confirm" type="button" value="ȷ��" onclick="clearMsg();loading();">
                </div>
            </td>
            <td><div align="left">
                    <input name="close" type="button" value="ȡ��" onclick="window.close();">
                </div>
            </td>
            </tr>
        </table>
        <input type="hidden" name="_terminator" value="<%=terminator%>"/>
        <input type="hidden" name="_fileName" value="<%=fileName%>"/>
        <input type="hidden" name="Type" value="${Type}" id="Type" name="Type"/>
    </form>
    <table class="table_status" width="95%"  >
        <tr class="table_edit_tr"  >
        <td width="20%" id="retMsg" class="status-font">������Ϣ:
            <font color="red">
                ${msg}
            </font>
        </td>
    </tr>
</table>

<div align="center" style="font-size:9pt;display:none;color:red" id="runPrompt">
    ���ڵ�������.............
</div>

</body>
</html>