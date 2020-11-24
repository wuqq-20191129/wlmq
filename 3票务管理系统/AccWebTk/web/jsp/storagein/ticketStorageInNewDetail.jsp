<%-- 
    Document   : TicketStorageInNewDetial
    Created on : 2017-08-03
    Author     : zhouyang
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="X-UA-Compatible" content="IE=9,Chrome=1" />
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>新票入库明细</title>


        <script language="javascript" type="text/javascript" charset="utf-8" src="js/common_form.js"></script>
        <script language="javascript" type="text/javascript" charset="utf-8" src="js/common_form_extend.js"></script>
        <script language="javascript" type="text/javascript" charset="utf-8" src="js/Validator.js"></script>
        <script language="JavaScript" type="text/javascript" charset="utf-8" src="js/CalendarPop.js"></script>
        <script language="JavaScript" type="text/javascript" charset="utf-8" src="js/icProduceOut.js"></script>
        <script language="JavaScript" type="text/javascript" charset="utf-8" src="js/icStorageProductBill.js"></script>
        <link rel="stylesheet" type="text/css" href="css/frame/simple.css" title="Style"/>
    </head>

    <!-- preLoadIdVal('d_validDate', 0); setPageControl('detailOp'); -->
    <body onload="
            initDocument('detailOp', 'detail');
            setListViewDefaultValue('detailOp', 'clearStart');
            setPrimaryKeys('detailOp', 'd_waterNo#d_billNo');
            setDetailBillNo('detailOp', 'd_billNo', 'queryCondition');
            controlByRecordFlagForInit('detailOp', ['add'], true);
            setTableRowBackgroundBlock('DataTable')">
        <table  class="table_title">
            <tr align="center" class="trTitle">
                <td colspan="4">新票入库明细
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
                                    controlByRecordFlagForInit('detailOp', ['add'], true);"/>
                            
                        </td>	
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="1"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width :100px">流水号</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="2"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width :100px" >入库原因</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="3"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width :100px">仓库</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="4"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width :100px">票区</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false  index="5" sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width :100px">票卡主类型</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="6"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width :100px">票卡子类型</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="7"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width :70px">数量</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="8" sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width :100px">面值(分/次)</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="9" sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width :140px">乘次票限制模式</td>
                    </tr>
                </table>

            </div>

            <div id="clearStart"  class="divForTableBlockData" >
                <table class="table_list_block" id="DataTable" >
                    <c:forEach items="${ResultSet}" var="rs">
                        <tr class="table_list_tr_data"
                            onMouseOut="outResultRow('detailOp', this);" 
                            onclick="setSelectValuesByRowPropertyName('detailOp','d_cardSubType','commonVariable','cardMainType');   
                                        clickResultRow('detailOp', this, 'detail');
                                        controlsByFlag('detailOp', ['modify', 'del']);
                                        controlsByFlagWithoutCk('detailOp', ['modify']);
                                        controlByRecordFlagForInit('detailOp', ['add'], true);" 
                            id="${rs.water_no}" flag="${rs.record_flag}" cardMainType="${rs.ic_main_type}">
                            <td id="rectNo1" class="table_list_tr_col_data_block">
                                <input type="checkbox" name="rectNo" id="rectNo" onclick="unSelectAllRecord('detailOp', 'rectNoAll', 'rectNo');
                                                controlsByFlag('detailOp', ['modify', 'del']);
                                                controlsByFlagWithoutCk('detailOp', ['modify']);
                                                controlByRecordFlagForInit('detailOp', ['add'], true);"
                                       value="${rs.water_no}"  flag="${rs.record_flag}">
                                </input>
                            </td>

                            <td  id="waterNo" class="table_list_tr_col_data_block" style="width :100px" >
                                ${rs.water_no}
                            </td>
                            <td  id="reasonId" class="table_list_tr_col_data_block" style="width :100px">
                                ${rs.reason_name}
                            </td>
                            <td  id="storageId" class="table_list_tr_col_data_block" style="width :100px">
                                ${rs.storage_name}
                            </td>
                            <td  id="areaId" class="table_list_tr_col_data_block" style="width :100px">
                                ${rs.area_name}
                            </td>
                            <td  id="cardMainType" class="table_list_tr_col_data_block" style="width :100px">
                                ${rs.ic_main_type_name}
                            </td>
                            <td  id="cardSubType" class="table_list_tr_col_data_block" style="width :100px">
                                ${rs.ic_sub_type_name}
                            </td>
                            <td  id="inNum" class="table_list_tr_col_data_block" style="width :70px">
                                ${rs.in_num}
                            </td>
                            <td  id="cardMoney" class="table_list_tr_col_data_block" style="width :100px">
                                ${rs.card_money}
                            </td>
                            <td  id="model" class="table_list_tr_col_data_block" style="width :140px">
                                ${rs.model_name}
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

        <form method="post" name="detailOp" id="detailOp" action="ticketStorageInNewDetailController" >

            <c:set var="divideShow" scope="request" value="0"/>
            <c:import  url="/jsp/common/common_template_web_variable.jsp?template_name=common_web_variable" />
            <c:import  url="/jsp/common/common_template_web_variable_ic.jsp?template_name=common_web_variable_ic" />

            <!--<DIV id="detail" align="center" class="divForTableData" style="position:relative; left:20; width:95%; height:10; layer-background-color:#99CC99; border:thin outset #ffffff; z-index:1;">-->
            <div id="detail"  class="divForTableDataDetail" >
                <table  class="table_edit_detail">
                    <tr class="table_edit_tr">
                            <input type="hidden" name="d_billNo" id="d_billNo" size="10" maxlength="12" />
                        <td class="table_edit_tr_td_label">流水号: </td>
                        <td class="table_edit_tr_td_input">
                            <input type="disable" name="d_waterNo" id="d_waterNo" size="18" disable />
                        </td>
                        <td class="table_edit_tr_td_label" >票卡主类型:</td>
                        <td class="table_edit_tr_td_input">
                            <select id="d_cardMainType" name="d_cardMainType" onChange="setSelectValues('detailOp', 'd_cardMainType', 'd_cardSubType', 'commonVariable');"  >				    
                                <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_maincard" />
                            </select>
                        </td>
                        <td class="table_edit_tr_td_label"><div align="right">票卡子类型:</div></td>
                        <td class="table_edit_tr_td_input">
                            <select id="d_cardSubType" name="d_cardSubType" dataType="Require" msg="票卡子类型不能为空!" >
                                <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_none" />
                            </select>
                            <c:set var="pVarName" scope="request" value="commonVariable"/>
                            <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_mainsubcard" />
                        </td>
                        <td class="table_edit_tr_td_label">仓库:</td>
                        <td class="table_edit_tr_td_input">
                            <select id="d_storageId" name="d_storageId" dataType="Require" msg="仓库不能为空!">
                                <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_storage" />
                            </select>
                        </td>
                        <td class="table_edit_tr_td_label">票区:</td>
                        <td class="table_edit_tr_td_input">
                            <select id="d_area" name="d_area">
                                <option value="01">新票区</option>
                            </select>
                        </td>
                    </tr>
                    <tr class="table_edit_tr">
                        
                        <td class="table_edit_tr_td_label">数量: </td>
                        <td class="table_edit_tr_td_input">
                            <input type="text" name="d_inNum" id="d_inNum" size="8"  maxlength="9" dataType="integer|Positive" required="true"  msg="数量为大于0的整数"/>
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



            <c:set var="addformQueryMethod" scope="request" value="setLineCardNames('detailOp','','','','card_main_code','card_sub_code','commonVariable');"/>
            <c:set var="addClickMethod" scope="request" value="setUpdatePrimaryKey('detailOp','d_waterNo#d_cardMainType#d_cardSubType#d_storageId#d_area#d_inNum');"/>
            <c:set var="clickMethod" scope="request" value="btnClick('detailOp','clearStart','detail','','clearStartHead');disableFormControls('detailOp',['d_waterNo'],true);"/>
            <c:set var="exportBillAfter" scope="request" value="exportBillDetail('detailOp','w_rpt_ic_rct_bl_in_new',['p_bill_no'],['queryCondition'],800,500)"/>
            <c:set var="addAfterMethod" scope="request" value="disableFormControls('detailOp',['d_waterNo'],true);"/>

            <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all" />
            <br/>
        </FORM>


        <!-- 状态栏 通用模板 -->

        <c:import url="/jsp/common/common_template.jsp?template_name=common_status_table" />
    </body>
</html>
