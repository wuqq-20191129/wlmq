<%-- 
    Document   : sys_group_window
    Created on : 2018-1-19
    Author     : luck
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="X-UA-Compatible" content="IE=9,Chrome=1" />
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>权限组</title>


        <script language="javascript" type="text/javascript" charset="utf-8" src="js/common_form.js"></script>
        <script language="javascript" type="text/javascript" charset="utf-8" src="js/Validator.js"></script>
        <script language="JavaScript" src="js/CalendarPop.js"></script>
        <link rel="stylesheet" type="text/css" href="css/frame/simple.css" title="Style"/>
    </head>

    <script  type="text/javascript">
        function checkVal() {
            var hiddenVal = document.getElementById('hiddenValue').value;
            var val = hiddenVal.split(',');
            var r = document.getElementsByName('rectNo');
        
            for (var i = 0; i < r.length; i++) {
                if (val.indexOf(r[i].value.split("#")[0]) > -1) {
                    r[i].checked = true;
                }
            }
        }

        function selectItemsForDialogDevID1(divName, formName) {
            var btId = event.srcElement.id;
            if (btId == 'cancel') {
                window.close();
                return;
            }

            var rowSelected = '';
            var r = document.getElementsByName('rectNo');
            for (var i = 0; i < r.length; i++) {
                if (r[i].checked) {
                    rowSelected = rowSelected + ',' + r[i].value;
                }
            }
            if (rowSelected.substr(0, 1) == ',')
                rowSelected = rowSelected.substr(1);
            window.returnValue = rowSelected;
            window.close();
            return;
        }


    </script>

    <body onload="
            checkVal();
            setTableRowBackgroundBlock('DataTable')">

        <!--<body >-->





        <!-- 表头 通用模板 -->

        <div id="clearStartBlock" style="height: 85%;margin-left: 58px;" >
            <div id="clearStartHead" class="divForTableBlockHead" style="width: 89%">
                <table class="table_list_block" id="DataTableHead" >

                    <!--说明：列的序号从0开始 isDigit：false 表示按字符串排序 ：true表示按数值排序 sortedby：asc表示按升序 dec表示按降序 -->
                    <tr  class="table_list_tr_head_block" id="ignore">
                        <td   class="table_list_tr_col_head_block" >
                            <input type="checkbox" name="rectNoAll" id="rectNoAll"  onclick="selectAllRecord('detailOp', 'rectNoAll', 'rectNo', 'clearStart', 0);"/>
                        </td>	
                        <td id="orderTd" class="table_list_tr_col_head_block"  style="width: 87%">权限组</td>
                    </tr>


                </table>

            </div>
            <!-- <DIV id="clearStart" align="center" style="position:relative; left:20; width:95%; height:30%;min-height: 200px;max-height: 250px; layer-background-color:#99CC99; border:thin outset #ffffff; z-index:1; overflow: scroll;">-->
            <div id="clearStart"  class="divForTableBlockData" style=" height: 80%; width: 89%">
                <table class="table_list_block" id="DataTable"  >

                    <c:forEach items="${ResultSet}" var="rs">
                        <!--class="listTableData" -->
                        <tr class="table_list_tr_data"  onMouseOver="overResultRow('detailOp', this);" 
                            onMouseOut="outResultRow('detailOp', this);" 
                            id="${rs.sysGroupId}">

                            <td id="rectNo1" class="table_list_tr_col_head_block">
                                <input type="checkbox" name="rectNo" onclick="unSelectAllRecord('detailOp', 'rectNoAll', 'rectNo');"
                                       value="${rs.sysGroupId}#${rs.sysGroupName}">

                                </input>
                            </td>
                            <td  id="sysGroupName" class="table_list_tr_col_data_block" style="width:87%">
                                ${rs.sysGroupName}
                            </td>
                        </tr>

                    </c:forEach>

                </table>
            </div>
        </div>

        <!-- 表头 通用模板 -->


        <FORM method="post" name="detailOp" id="detailOp" action="" >

            <c:set var="divideShow" scope="request" value="0"/>
            <c:import  url="/jsp/common/common_template_web_variable.jsp?template_name=common_web_variable" />

            <!--<DIV id="detail" align="center" class="divForTableData" style="position:relative; left:20; width:95%; height:10; layer-background-color:#99CC99; border:thin outset #ffffff; z-index:1;">-->
            <DIV id="detail"  class="" >

            </DIV>
            <center>
                <input type="hidden" id="allSelectedIDs" name="allSelectedIDs" value=""/>
                <input type="hidden" id="hiddenValue" name = "hiddenValue" value="${d_groupName}" />
                <input type="button" id="submit" name="submit" value="确定"  onClick="selectItemsForDialogDevID1('clearStart', 'detailOp')"/>
                <input type="button" id="cancel" name="cancel" value="取消"  onClick="selectItemsForDialogDevID1('clearStart', 'detailOp')"/>
            </center>   
            <c:import url="/jsp/common/common_template.jsp?template_name=version_eval" />
            <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all_init" />

        </FORM>

        <!-- 状态栏 通用模板 -->


    </body>
</html>
