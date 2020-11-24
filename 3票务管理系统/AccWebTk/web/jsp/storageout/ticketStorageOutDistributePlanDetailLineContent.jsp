<%--
    Document   : TicketStorageOutDistributePlanDetailLineContent
    Created on : 2017-8-7
    Author     : zhongziqi
--%>
<%@page  contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <title>配票内容</title>
        <meta http-equiv="X-UA-Compatible" content="IE=9">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="css/frame/simple.css" title="Style"/>
        <script language="JavaScript" type="text/javascript" charset="utf-8" src="js/icProduceOut.js"></script>
        <script language="javascript" type="text/javascript" charset="utf-8" src="js/common_form.js"></script>
        <script language="javascript" type="text/javascript" charset="utf-8" src="js/common_form_extend.js"></script>
    </head>
    <body>
        <c:import  url="/jsp/common/common_template_web_variable.jsp?template_name=common_web_variable" />
        <table  class="table_title">
            <tr align="center" class="trTitle">
                <td colspan="4">配票出库 配票内容
                </td>
            </tr>
        </table>
        <table  class="table_edit">
            <tr class="table_edit_tr">
                <td class="table_edit_tr_td_label">线路:</td>
                <td class="table_edit_tr_td_input">
                    <select id="q_icline" name="q_icline" onChange="setItemByLine('DataTable','q_icline');">

                        <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_icline_for_content" />
                    </select>
                </td>
                <td class="table_edit_tr_td_input">

                </td>
            </tr>
        </table>
        <c:set var="pTitleName" scope="request" value="列表"/>
        <c:set var="pTitleWidth" scope="request" value="50"/>
        <c:import url="/jsp/common/common_template.jsp?template_name=common_table_title" />
        <div id="clearStartBlock" class="divForTableBlock">
            <div id="clearStartHead" class="divForTableBlockHead" >
                <table class="table_list_block" id="DataTableHead" >
                    <!--说明：列的序号从0开始 isDigit：false 表示按字符串排序 ：true表示按数值排序 sortedby：asc表示按升序 dec表示按降序 -->
                    <tr  class="table_list_tr_head_block" id="ignore">
                        <td   class="table_list_tr_col_head_block">
                            <input type="checkbox" name="rectNoAll"  id="rectNoAll" onclick="selectAllRecordForDialog('detailOp', 'rectNoAll', 'rectNo', 'clearStart', 0);"/>
                        </td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=true index="1"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 100px">流水号</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="2"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 100px">线路</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=true index="3"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 100px">配票数量</td>
                    </tr>
                </table>
            </div>
            <div id="clearStart"  class="divForTableBlockData" >
                <table class="table_list_block" id="DataTable" >
                    <c:forEach items="${result}" var="rs">
                        <tr class="table_list_tr_data"  onMouseOver="overResultRow('detailOp', this);"
                            onMouseOut="outResultRow('detailOp', this);"
                            onclick="clickResultRowForDialog('detailOp', this);"
                            id="${rs.distributeLineId}" >
                            <td id="rectNo1" class="table_list_tr_col_data_block">
                                <input type="checkbox" name="rectNo" onclick="unSelectAllRecordForDialog('rectNoAll', 'rectNo');"
                                       value="${rs.waterNo}">
                                </input>
                            </td>
                            <td  id="waterNo" class="table_list_tr_col_data_block" style="width: 100px">
                                ${rs.waterNo}
                            </td>
                            <td  id="lineId" class="table_list_tr_col_data_block" style="width: 100px">
                                ${rs.distributeLineName}
                            </td>
                            <td  id="distributeQuantity" class="table_list_tr_col_data_block" style="width: 100px">
                                <!--${rs.distributeQuantity}-->
                                <input
                                type="text" name="${rs.distributeLineId}:${rs.distributeQuantity}"
                                id="${rs.distributeLineId}distributeQuantity" size="12" value="${rs.distributeQuantity}" /></td>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </div>
        </div>
        <form name="detailOp" method="post" action="">
            <input type="hidden" id="rowSelected" value="init" />
                <input type="hidden" id="allSelectedIDs" name="allSelectedIDs"
                       value="" />
                <input type="button" id="submit" name="submit" value="确定"
                       onClick=" selectItemsForDialogStation('clearStart', 'detailOp')" />
                <input type="button" id="cancel" name="cancel" value="取消"
                       onClick="selectItemsForDialog('clearStart', 'detailOp')" />
        </form>
    </body>
</html>
