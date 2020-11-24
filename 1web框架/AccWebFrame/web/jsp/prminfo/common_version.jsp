<%-- 
    Document   : common_version
    Created on : 2017-5-16, 15:35:18
    Author     : hejj
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>参数版本</title>
        <script language="javascript"  src="js/common_form.js"></script>
        <link rel="stylesheet" type="text/css" href="css/frame/simple.css" title="Style"/>
    </head>
    <body onload="setTableRowBackgroundBlock('DataTable')">
    <xsl:call-template name="common_web_variable" />

    <c:set var="pTitleName" scope="request" value="版本选择"/>
    <c:set var="pTitleWidth" scope="request" value="50"/>
    <c:import  url="/jsp/common/common_template.jsp?template_name=common_table_title" />
    <div id="clearStartBlock" class="divForTableBlock_tall">
        <div id="clearStartHead" class="divForTableBlockHead">
            <table class="table_list_block" id="DataTableHead" >
                <!--说明：列的序号从0开始 isDigit：false 表示按字符串排序 ：true表示按数值排序 sortedby：asc表示按升序 dec表示按降序 -->
                <tr  class="table_list_tr_head" id="ignore">	
                    <td id="orderTd"    class="table_list_tr_col_head_block_w"  isDigit=false index="0"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >版本</td>
                    <td id="orderTd"    class="table_list_tr_col_head_block_w"  isDigit=false index="1"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >版本生成时间</td>
                    <td id="orderTd"    class="table_list_tr_col_head_block_w"  isDigit=false index="2"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >版本起始时间</td>
                    <td id="orderTd"    class="table_list_tr_col_head_block_w"  isDigit=false index="3"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >版本截止时间</td>
                    <td id="orderTd"    class="table_list_tr_col_head_block_w"  isDigit=false index="4"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >生成版本操作员</td>
                    <td id="orderTd"    style="width: 70px" class="table_list_tr_col_head_block_w"  isDigit=false index="5"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >版本说明</td>
                    <td id="orderTd"    class="table_list_tr_col_head_block_w"  isDigit=false index="6"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >应用线路</td>
                    <td id="orderTd"    style="width: 130px" class="table_list_tr_col_head_block_w"  isDigit=false index="7"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >版本备注</td>

                </tr>
            </table>
        </div>
        <div id="clearStart"  class="divForTableBlockData_tall">
            <table class="table_list_block" id="DataTable" >


                <c:forEach items="${versions}" var="version">
                    <tr class="table_list_tr_data" >

                        <td class="table_list_tr_col_data_block_w">
                            <a href="#" onclick="setVersion('versionForm', 'Version', 'Type', 'StartDate', 'EndDate', 'VersionType', '${version.version_no}', '${version.parm_type_id}', '${version.begin_time}', '${version.end_time}', '${version.record_flag}');">
                                ${version.version_no}
                            </a>
                        </td>
                        <td class="table_list_tr_col_data_block_w">
                            ${version.version_date}
                        </td>
                        <td class="table_list_tr_col_data_block_w">
                            ${version.begin_time}
                        </td>
                        <td class="table_list_tr_col_data_block_w">
                            ${version.end_time}
                        </td>
                        <td class="table_list_tr_col_data_block_w">
                            ${version.operator_id}
                        </td>
                        <td class="table_list_tr_col_data_block_w" style="width: 70px">
                            ${version.record_flag_name}
                        </td >
                        <td class="table_list_tr_col_data_block_w">
                            ${version.app_line_name}
                        </td>
                        <td class="table_list_tr_col_data_block_w" style="width: 130px">
                            ${version.remark}
                        </td>
                    </tr>

                </c:forEach>

            </table>
        </div>
    </div>

    <FORM method="post" name="versionForm" action="${action}">
        <c:import url="/jsp/common/common_template.jsp?template_name=version"/>

        <c:import url="/jsp/common/common_template_web_variable.jsp?template_name=common_web_variable_version"/>

        <c:set var="divideShow" scope="request" value="0"/>
        <c:import  url="/jsp/common/common_template_web_variable.jsp?template_name=common_web_variable" />


    </FORM>
</body>
</html>
