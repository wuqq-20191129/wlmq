<%-- 
    Document   : ticketStorageOutDestroytoOutBillDetail
    Created on : 2017-8-10
    Author     : zhongziqi
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="X-UA-Compatible" content="IE=9,Chrome=1" />
        <title>出库单明细</title>
        <script language="javascript" type="text/javascript" charset="utf-8" src="js/common_form.js"></script>
        <script language="javascript" type="text/javascript" charset="utf-8" src="js/Validator.js"></script>
        <script language="JavaScript" type="text/javascript" charset="utf-8" src="js/CalendarPop.js"></script>
        <script language="JavaScript" type="text/javascript" charset="utf-8" src="js/icProduceOut.js"></script>
        <script language="JavaScript" type="text/javascript" charset="utf-8" src="js/icStorageProductBill.js"></script>
        <link rel="stylesheet" type="text/css" href="css/frame/simple.css" title="Style"/>
    </head>
    <body onload="
            initDocument('detailOp', 'detail');
            setListViewDefaultValue('detailOp', 'clearStart');
            setPrimaryKeys('detailOp', 'd_waterNo#d_billNo');
            setDetailBillNo('detailOp', 'd_billNo', 'queryCondition');
            controlByRecordFlagForInit('detailOp', ['add'], true);
            setTableRowBackgroundBlock('DataTable')">
        <table  class="table_title">
            <tr align="center" class="trTitle">
                <td colspan="4">出库单明细
                </td>
            </tr>
        </table>
        <!-- 表头 通用模板 -->
        <c:set var="pTitleName" scope="request" value="列表"/>
        <c:set var="pTitleWidth" scope="request" value="50"/>
        <c:import url="/jsp/common/common_template.jsp?template_name=common_table_title" />
        <div id="clearStartBlock" class="divForTableBlock">
            <div id="clearStartHead" class="divForTableBlockHead">
                <table class="table_list_block" id="DataTableHead" >
                    <!--说明：列的序号从0开始 isDigit：false 表示按字符串排序 ：true表示按数值排序 sortedby：asc表示按升序 dec表示按降序 -->
                    <tr  class="table_list_tr_head_block" id="ignore">
                        <td   class="table_list_tr_col_head_block">
                            <input type="checkbox" name="rectNoAll" id="rectNoAll" onclick="selectAllRecord('detailOp', 'rectNoAll', 'rectNo', 'clearStart', 0);
                                    controlsByFlag('detailOp', [ 'del']);
                                    controlsByFlagWithoutCk('detailOp', ['modify']);
                                    controlByRecordFlagForInit('detailOp', ['add'], true);
                                   "/>
                        </td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=true index="1"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >流水号</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="2"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 90px">出库单号</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="3"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >单据状态</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="4"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 90px">票卡主类型</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="5"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 90px">票卡子类型</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=true index="6"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >面值(分/次)</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=true index="7"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >出库数量</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="8"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 90px">仓库</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="9"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 90px">票区</td>
                    </tr>
                </table>
            </div>
            <div id="clearStart"  class="divForTableBlockData">
                <table class="table_list_block" id="DataTable" >
                    <c:forEach items="${ResultSet}" var="rs">
                        <tr class="table_list_tr_data"  onMouseOver="overResultRow('detailOp', this);" 
                            onMouseOut="outResultRow('detailOp', this);" 
                            onclick="
                                   setSelectValuesByRowPropertyName('detailOp', 'd_cardSubType', 'commonVariable', 'icMainType');
                                    setSelectValuesByRowPropertyName('detailOp','d_zone','commonVariable2','storageId');
                                    clickResultRow('detailOp', this, 'detail');
                                    controlsByFlag('detailOp', [ 'del']);
                                    controlsByFlagWithoutCk('detailOp', ['modify']);
                                    controlByRecordFlagForInit('detailOp', ['add'], true);
                            " 
                            id="${rs.billNo}#${rs.waterNo}" flag="${rs.recordFlag}" storageId = "${rs.storageId}" icMainType = "${rs.icMainType}">
                            <td id="rectNo1" class="table_list_tr_col_data_block">
                                <input type="checkbox" name="rectNo" id="rectNo" 
                                       onclick="unSelectAllRecord('detailOp', 'rectNoAll', 'rectNo');
                                               controlsByFlag('detailOp', ['del']);
                                               controlsByFlagWithoutCk('detailOp', ['modify']);
                                               controlByRecordFlagForInit('detailOp', ['add'], true);"
                                       value="${rs.billNo}"  flag="${rs.recordFlag}" >
                                </input>
                            </td>
                            <td  id="waterNo" class="table_list_tr_col_data_block" >
                                ${rs.waterNo}
                            </td>
                            <td  id="billNo" class="table_list_tr_col_data_block" style="width: 90px">
                                ${rs.billNo}
                            </td>
                            <td  id="recordFlag" class="table_list_tr_col_data_block">
                                ${rs.recordFlagName}
                            </td>
                            <td  id="cardMainType" class="table_list_tr_col_data_block" style="width: 90px">
                                ${rs.icMainName}
                            </td>
                            <td  id="cardSubType" class="table_list_tr_col_data_block" style="width: 90px">
                                ${rs.icSubName}
                            </td>
                            <td  id="cardMoney" class="table_list_tr_col_data_block">
                                ${rs.cardMoney}
                            </td>
                            <td  id="amount" class="table_list_tr_col_data_block">
                                ${rs.outNum}
                            </td>
                            <td  id="storage" class="table_list_tr_col_data_block" style="width: 90px">
                                ${rs.storageName}
                            </td>
                            <td  id="zone" class="table_list_tr_col_data_block" style="width: 90px">
                                ${rs.areaName}
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </div>
        </div>
        <!-- 表头 通用模板 -->
        <c:set var="pTitleName" scope="request" value="明细"/>
        <c:set var="pTitleWidth" scope="request" value="50"/>
        <c:import url="/jsp/common/common_template.jsp?template_name=common_table_title" />
        <FORM method="post" name="detailOp" id="detailOp" action="ticketStorageOutDestroyManage" >
            <c:set var="divideShow" scope="request" value="0"/>
            <c:set var="operTypeValue" scope="request" value="outBillDetail"/>
            <c:set var="queryConditionValue" scope="request" value="${QueryCondition}"/>
            <c:import  url="/jsp/common/common_template_web_variable.jsp?template_name=common_web_variable" />
            <c:import  url="/jsp/common/common_template_web_variable_ic.jsp?template_name=common_web_variable_ic" />

            <div id="detail"  class="divForTableDataDetail" >
                <table  class="table_edit_detail">
                    <tr class="table_edit_tr">
                        <td class="table_edit_tr_td_label">流水号: </td>
                        <td class="table_edit_tr_td_input">
                            <input type="text" name="d_waterNo" id="d_waterNo" size="10"
                                   require="false"  maxlength="18" />
                        </td>
                        <td class="table_edit_tr_td_label">出库单: </td>
                        <td class="table_edit_tr_td_input">
                            <input type="text" name="d_billNo" id="d_billNo" size="12"
                                   require="false"  maxlength="12" />
                        </td>
                        <td class="table_edit_tr_td_label" >票卡主类型:</td>
                        <td class="table_edit_tr_td_input">
                            <select id="d_cardMainType" name="d_cardMainType" require="true" dataType="Require" msg="请选择票卡主类型!" onChange="setSelectValues('detailOp', 'd_cardMainType', 'd_cardSubType', 'commonVariable');"  >				    
                                <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_maincard_limit" />
                            </select>
                        </td>
                        <td class="table_edit_tr_td_label"><div align="right">票卡子类型:</div></td>
                        <td class="table_edit_tr_td_input"> 
                            <select id="d_cardSubType" name="d_cardSubType"  require="true" dataType="Require"  msg="请选择票卡子类型!">
                                <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_none" />
                            </select>
                            <c:set var="pVarName" scope="request" value="commonVariable"/>
                            <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_mainsubcard" />
                        </td>
                    </tr>
                    <tr class="table_edit_tr">
                        <td class="table_edit_tr_td_label">出库数量: </td>
                        <td class="table_edit_tr_td_input">
                            <input type="text" name="d_amount" id="d_amount" size="10"
                                   require="true" maxlength="9"   dataType="Require|integer|Positive"  
                                   msg="出库数量为大于零的整数!"/>
                        </td>
                        <td class="table_edit_tr_td_label">仓库:</td>
                        <td class="table_edit_tr_td_input">
                            <select id="d_storage" name="d_storage" dataType="Require" msg="请选择仓库!"
                                    onChange="setSelectValues('detailOp', 'd_storage', 'd_zone', 'commonVariable2');
                                    "
                                    >
                                <c:set var="pVarName" scope="request" value="commonVariable1"/>
                                <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_storage" />
                            </select>
                        </td>
                        <td class="table_edit_tr_td_label">票区:</td>
                        <td class="table_edit_tr_td_input">
                            <select id="d_zone" name="d_zone" dataType="Require" msg="请选择库存票区!" 
                                    >
                                <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_none" />
                            </select>
                            <c:set var="pVarName" scope="request" value="commonVariable2"/>
                            <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_storagezone_destroy" />
                        </td>
                    </tr>
                </table>
            </div>
            <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all_init" />
            <c:set var="add" scope="request" value="1"/>
            <c:set var="modify" scope="request" value="1"/>
            <c:set var="del" scope="request" value="1"/>
            <c:set var="save" scope="request" value="1"/>
            <c:set var="cancle" scope="request" value="1"/>
            <c:set var="exportBill" scope="request" value="1"/>
            <c:set var="addformQueryMethod" scope="request" value="setLineCardNames('detailOp','d_storage','d_zone','commonVariable2','card_main_code','card_sub_code','commonVariable');"/>
            <c:set var="addClickMethod" scope="request" value="setUpdatePrimaryKey('detailOp','d_billNo#d_waterNo');enablePrimaryKeys('detailOp');"/>
            <c:set var="clickMethod" scope="request" value="btnClick('detailOp','clearStart','detail');disableFormControls('detailOp',['d_waterNo','d_billNo'],true);"/>
            <%--<c:set var="addAfterMethod" scope="request" value="disablePrimaryKeys('detailOp');disableFormControlsById(['d_mode','d_lineId','d_stationId','d_exitLineId','d_exitStationId'],'true');"/>--%>
            <c:set var="exportBillAfter" scope="request" value="exportBillDetail('detailOp','w_rpt_ic_rct_bl_out_xc',['p_bill_no'],['queryCondition'],800,500)"/>
            <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all" />
            <br/>
        </FORM>

        <!-- 状态栏 通用模板 -->

        <c:import url="/jsp/common/common_template.jsp?template_name=common_status_table" />
    </body>
</html>
