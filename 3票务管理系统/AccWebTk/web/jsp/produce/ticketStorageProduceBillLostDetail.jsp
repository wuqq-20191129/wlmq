<%-- 
    Document   : produce_bill_lost_detail
    Created on : 2017-8-24
    Author     : moqf
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <!--add by zhongzq 20180609-->
        <meta http-equiv="X-UA-Compatible" content="IE=9,Chrome=1" />
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>废票遗失票明细</title>


        <script language="javascript" type="text/javascript" charset="utf-8" src="js/common_form.js"></script>
        <script language="javascript" type="text/javascript" charset="utf-8" src="js/common_form_extend.js"></script>
        <script language="javascript" type="text/javascript" charset="utf-8" src="js/Validator.js"></script>
        <script language="JavaScript" type="text/javascript" charset="utf-8" src="js/CalendarPop.js"></script>
        <!--<script language="JavaScript" type="text/javascript" charset="utf-8" src="js/icProduceOut.js"></script>-->
        <script language="JavaScript" type="text/javascript" charset="utf-8" src="js/icStorageProductBill.js"></script>
        <link rel="stylesheet" type="text/css" href="css/frame/simple.css" title="Style"/>
    </head>
    <!-- preLoadIdVal('d_validDate', 0); setPageControl('detailOp'); -->
<!--    initDocument('formOpLost','detail');
    setPrimaryKeys('formOpLost','');
    setControlsDefaultValue('formOpLost');
    setListViewDefaultValue('formOpLost','clearStart');
    controlByRecordFlagForInit('formOpLost',['add'],true);-->
    <body onload="
        
            initDocument('detailOp', 'detail');
            setListViewDefaultValue('detailOp', 'clearStart');
            setPrimaryKeys('detailOp', '');
            setControlsDefaultValue('detailOp');
//            setDetailBillNo('detailOp', 'd_billNo', 'queryCondition');
//            controlByRecordFlagForInit('detailOp', ['add','del'], true);
            setTableRowBackgroundBlock('DataTable')">

        <!--<body >-->
        <table  class="table_title">
            <tr align="center" class="trTitle">
                <td colspan="4">废票遗失票明细
                </td>
            </tr>
        </table>



        <!-- 表头 通用模板 -->
        <c:set var="pTitleName" scope="request" value="列表"/>
        <c:set var="pTitleWidth" scope="request" value="50"/>
        <c:import url="/jsp/common/common_template.jsp?template_name=common_table_title" />
        <div id="clearStartBlock" class="divForTableBlock">
            <!--style="width :1800px"-->
            <div id="clearStartHead" class="divForTableBlockHead" >
                <table class="table_list_block" id="DataTableHead" >

                    <!--说明：列的序号从0开始 isDigit：false 表示按字符串排序 ：true表示按数值排序 sortedby：asc表示按升序 dec表示按降序 -->
                    <tr  class="table_list_tr_head_block" id="ignore">
                        <td   class="table_list_tr_col_head_block">
                            <input type="checkbox" name="rectNoAll" id="rectNoAll" onclick="selectAllRecord('detailOp', 'rectNoAll', 'rectNo', 'clearStart', 0);
//                                    controlsByFlag('detailOp', ['del']);
                                    controlByRecordFlagForInit('detailOp', ['add','del'], true);
                                        "/>
                        </td>	
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="1"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width:80px">工作单号</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="2"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width:100px" >订单号</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="3"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width:120px">票卡号</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="4"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >类型</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false  index="5"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >票卡主类型</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="6"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >票卡子类型</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="7"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >进站线路</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="8"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >进站车站</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="9"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >出站线路</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="10" sortedby="asc"  onclick="sortForTableBlock('clearStart');">出站车站</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="11" sortedby="asc"  onclick="sortForTableBlock('clearStart');" style="width:100px">乘次票限制模式</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=true index="12" sortedby="asc"  onclick="sortForTableBlock('clearStart');">面值(分/次)</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="13" sortedby="asc"  onclick="sortForTableBlock('clearStart');">有效期</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="14" sortedby="asc"  onclick="sortForTableBlock('clearStart');">机器号</td>


                    </tr>


                </table>

            </div>
<!--style="width :1800px"-->
            <div id="clearStart"  class="divForTableBlockData" >
                <table class="table_list_block" id="DataTable" >

                    <c:forEach items="${ResultSet}" var="rs">

                        <tr class="table_list_tr_data"  onMouseOver="overResultRow('detailOp', this);" 
                            onMouseOut="outResultRow('detailOp', this);" 
                            onclick="
                                    clickResultRow('detailOp', this, 'detail');

//                                    controlsByFlagWithoutCk('detailOp', ['modify']);
//                                    controlsByFlag('detailOp', ['del']);
                                    controlByRecordFlagForInit('detailOp', ['add','del'], true);
                                                                    " 
                            id="${rs.card_type}#${rs.bill_no}#${rs.card_no}" >

                            <td id="rectNo1" class="table_list_tr_col_data_block" >
                                <input type="checkbox" name="rectNo" id="rectNo" onclick="unSelectAllRecord('detailOp', 'rectNoAll', 'rectNo');
//                                        controlsByFlag('detailOp', ['del']);
//                                        controlByRecordFlagForInit('detailOp', ['add'], true);
                                       "
                                       value="${rs.card_type}#${rs.bill_no}#${rs.card_no}" 
                                       >

                                </input>
                            </td>
                            <td  id="bill_no" class="table_list_tr_col_data_block" style="width:80px">
                                ${rs.bill_no}
                            </td>
                            <td  id="order_no" class="table_list_tr_col_data_block" style="width:100px">
                                ${rs.order_no}
                            </td>
                            <td  id="card_no" class="table_list_tr_col_data_block" style="width:120px">
                                ${rs.card_no}
                            </td>
                            <td  id="card_type" class="table_list_tr_col_data_block">
                                ${rs.card_type_name}
                            </td>
                            <td  id="ic_main_type" class="table_list_tr_col_data_block">
                                ${rs.ic_main_type_name}
                            </td>
                            <td  id="ic_sub_type" class="table_list_tr_col_data_block">
                                ${rs.ic_sub_type_name}
                            </td>
                            <td  id="line_id" class="table_list_tr_col_data_block">
                                ${rs.line_id_name}
                            </td>
                            <td  id="station_id" class="table_list_tr_col_data_block">
                                ${rs.station_id_name}
                            </td>
                            <td  id="exit_line_id" class="table_list_tr_col_data_block">
                                ${rs.exit_line_id_name}
                            </td>
                            <td  id="exit_station_id" class="table_list_tr_col_data_block">
                                ${rs.exit_station_id_name}
                            </td>
                            <td  id="model" class="table_list_tr_col_data_block" style="width:100px">
                                ${rs.model_name}
                            </td>

                            <td  id="card_money" class="table_list_tr_col_data_block">
                                ${rs.card_money}
                            </td>
                            <td  id="valid_date" class="table_list_tr_col_data_block">
                                ${rs.valid_date}
                            </td>
                            <td  id="machine_no" class="table_list_tr_col_data_block">
                                ${rs.machine_no}
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
<!--onSubmit="return Validator.Validate(this,2);-->
        <form method="post" name="detailOp" id="detailOp" action="ticketStorageProduceBillLostDetail" >

            <c:set var="divideShow" scope="request" value="0"/>
            <c:import  url="/jsp/common/common_template_web_variable.jsp?template_name=common_web_variable" />
            
            <c:set var="QueryCondition" scope="request" value="${QueryCondition}"/>
            <c:import  url="/jsp/common/common_template_web_variable_ic.jsp?template_name=common_web_variable_ic" />

            <!--<DIV id="detail" align="center" class="divForTableData" style="position:relative; left:20; width:95%; height:10; layer-background-color:#99CC99; border:thin outset #ffffff; z-index:1;">-->
            <div id="detail"  class="divForTableDataDetail" >
                <table  class="table_edit_detail">
                    <tr class="table_edit_tr">
                        <td class="table_edit_tr_td_label">工作单号: </td>
                        <td class="table_edit_tr_td_input">
                            <input type="text" readonly="true"  name="d_bill_no" id="d_bill_no" maxlength="12" size="12" value="${bill_no}"/>
                        </td>
                        <td class="table_edit_tr_td_label">订单号: </td>
                        <td class="table_edit_tr_td_input">
                            <input type="text"  name="d_order_no" id="d_order_no" size="16" maxlength="14" require="true"/>
                        </td>
                        <td class="table_edit_tr_td_label">票卡号: </td>
                        <td class="table_edit_tr_td_input">
                            <input type="text"  name="d_card_no" id="d_card_no" size="22" maxlength="20" dataType="Number|LimitB" min="16" max="20" msg="票卡号应为16-20位数字字符" require="true"/>	
                        </td>

                        <td class="table_edit_tr_td_label">类型:</td>
                        <td class="table_edit_tr_td_input">
                            <select id="d_card_type" name="d_card_type" dataType="Require" msg="类型不能为空!" >
                                <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_uselessCardType" />
                            </select>
                        </td>
                    </tr>

                </table>
            </div>



            <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all_init" />
            <c:set var="add" scope="request" value="1"/>
            <c:set var="modify" scope="request" value="0"/>
            <c:set var="del" scope="request" value="1"/>
            <c:set var="save" scope="request" value="1"/>
            <c:set var="cancle" scope="request" value="1"/>
            <c:set var="print" scope="request" value="1"/>
            
            <%--<c:set var="exportBill" scope="request" value="1"/>--%>

           

            <c:set var="addClickMethod" scope="request" value="setUpdatePrimaryKey('detailOp','d_bill_no');"/>
            <c:set var="clickMethod" scope="request" value="btnClick('detailOp','clearStart','detail');"/>


            <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all" />
            <br/>
        </FORM>


        <!-- 状态栏 通用模板 -->

        <c:import url="/jsp/common/common_template.jsp?template_name=common_status_table" />
    </body>
</html>
