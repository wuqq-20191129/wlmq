<%-- 
    Document   : TicketStorageOutCleanOutBillDetial
    Created on : 2017-7-26
    Author     : luck
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="X-UA-Compatible" content="IE=9,Chrome=1" />
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>出库单明细</title>


        <script language="javascript" type="text/javascript" charset="utf-8" src="js/common_form.js"></script>
        <script language="javascript" type="text/javascript" charset="utf-8" src="js/common_form_extend.js"></script>
        <script language="javascript" type="text/javascript" charset="utf-8" src="js/Validator.js"></script>
        <script language="JavaScript" type="text/javascript" charset="utf-8" src="js/CalendarPop.js"></script>
        <script language="JavaScript" type="text/javascript" charset="utf-8" src="js/icProduceOut.js"></script>
        <script language="JavaScript" type="text/javascript" charset="utf-8" src="js/icStorageProductBill.js"></script>
        <link rel="stylesheet" type="text/css" href="css/frame/simple.css" title="Style"/>

    </head>
    <style type="text/css">
        .NewButtonStyle{
            font-size: 9pt;
            color:#3399FF;
            background-color:#FFFFFF;
            text-align:center;
        }
    </style>
    <script language="JavaScript" >
        function disableForm(controlObs, disabled) {
            for (i = 0; i < controlObs.length; i++) {
                document.getElementById(controlObs[i]).disabled = disabled;
            }
        }
        function set(formName) {
            var recordFlag = document.forms[formName].getElementsByTagName('input')['billRecordFlag'].value;
            if (recordFlag == '3') {
                document.getElementById('btModify').disabled = false;
                document.getElementById('btModify').className = 'NewButtonStyle';
            }
        }
    </script>

    <!-- preLoadIdVal('d_validDate', 0); setPageControl('detailOp'); -->
    <body onload="
            initDocument('detailOp', 'detail');
            setListViewDefaultValue('detailOp', 'clearStart');
            setPrimaryKeys('detailOp', 'd_waterNo#d_billNo#d_cardMainType#d_cardSubType#d_cardMoney#d_cardMoney1');
            setDetailBillNo('detailOp', 'd_billNo', 'queryCondition');
            controlByRecordFlagForInit('detailOp', ['add'], true);

            

            setTableRowBackgroundBlock('DataTable')">

        <!--<body >-->
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
            <div id="clearStartHead" class="divForTableBlockHead" >
                <table class="table_list_block" id="DataTableHead" >

                    <!--说明：列的序号从0开始 isDigit：false 表示按字符串排序 ：true表示按数值排序 sortedby：asc表示按升序 dec表示按降序 -->
                    <tr  class="table_list_tr_head_block" id="ignore">
                        <td   class="table_list_tr_col_head_block">
                            <input type="checkbox" name="rectNoAll" id="rectNoAll" onclick="selectAllRecord('detailOp', 'rectNoAll', 'rectNo', 'clearStart', 0);
                                    controlsByFlag('detailOp', [ 'del']);
                                    controlByRecordFlagForInit('detailOp', ['add'], true);"/>
                        </td>	
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="1"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >流水号</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="2"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width:80px" >出库单号</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="3"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >票卡主类型</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="4"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >票卡子类型</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=true index="5" sortedby="asc"  onclick="sortForTableBlock('clearStart');">出库数量</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="6" sortedby="asc"  onclick="sortForTableBlock('clearStart');">仓库</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="7" sortedby="asc"  onclick="sortForTableBlock('clearStart');">票区</td>


                    </tr>


                </table>

            </div>

            <div id="clearStart"  class="divForTableBlockData" >
                <table class="table_list_block" id="DataTable" >

                    <c:forEach items="${ResultSet}" var="rs">

                        <tr class="table_list_tr_data"  onMouseOver="overResultRow('detailOp', this);" 
                            onMouseOut="outResultRow('detailOp', this);" 
                            onclick="setSelectValuesByRowPropertyName('detailOp', 'd_cardSubType', 'commonVariable', 'cardMainCode');
                                    setSelectValuesByRowPropertyName('detailOp', 'd_zone', 'commonVariable1', 'storageId');


                                    clickResultRow('detailOp', this, 'detail');
                                    set('detailOp');
                                    controlsByFlagWithoutCk('detailOp', ['modify']);
                                    controlByRecordFlagForInit('detailOp', ['add'], true);" 
                            id="${rs.bill_no}#${rs.water_no}" flag="${rs.record_flag}" cardMainCode="${rs.ic_main_type}" 
                            storageId="${rs.storage_id}" lineId="${rs.line_id}" esWorkTypeId="${rs.es_worktype_id}" exitLineId="${rs.exit_line_id}">

                            <td id="rectNo1" class="table_list_tr_col_data_block" >
                                <input type="checkbox" name="rectNo" id="rectNo" onclick="unSelectAllRecord('detailOp', 'rectNoAll', 'rectNo');

                                        controlsByFlag('detailOp', ['modify', 'del']);
                                        controlByRecordFlagForInit('detailOp', ['add'], true);
                                       "
                                       value="${rs.bill_no}"  flag="${rs.record_flag}" >

                                </input>
                            </td>
                            <td  id="waterNo" class="table_list_tr_col_data_block" >
                                ${rs.water_no}
                            </td>
                            <td  id="billNo" class="table_list_tr_col_data_block" style="width:80px">
                                ${rs.bill_no}
                            </td>                          
                            <td  id="cardMainType" class="table_list_tr_col_data_block">
                                ${rs.ic_main_type_name}
                            </td>
                            <td  id="cardSubType" class="table_list_tr_col_data_block">
                                ${rs.ic_sub_type_name}
                            </td>
                            <td  id="amount" class="table_list_tr_col_data_block">
                                ${rs.out_num}
                            </td>
                            <td  id="storage" class="table_list_tr_col_data_block">
                                ${rs.storage_id_name}
                            </td>
                            <td  id="zone" class="table_list_tr_col_data_block">
                                ${rs.area_id_name}
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

        <form method="post" name="detailOp" id="detailOp" action="ticketStorageOutCleanManage" >

            <c:set var="divideShow" scope="request" value="0"/>
            <c:import  url="/jsp/common/common_template_web_variable.jsp?template_name=common_web_variable" />
            <c:import  url="/jsp/common/common_template_web_variable_ic.jsp?template_name=common_web_variable_ic" />

            <!--<DIV id="detail" align="center" class="divForTableData" style="position:relative; left:20; width:95%; height:10; layer-background-color:#99CC99; border:thin outset #ffffff; z-index:1;">-->
            <div id="detail"  class="divForTableDataDetail" >
                <table  class="table_edit_detail">
                    <tr class="table_edit_tr">
                        <td class="table_edit_tr_td_label">流水号: </td>
                        <td class="table_edit_tr_td_input">
                            <input type="text" name="d_waterNo" id="d_waterNo" size="10" maxlength="18"/>
                        </td>
                        <td class="table_edit_tr_td_label">出库单: </td>
                        <td class="table_edit_tr_td_input">
                            <input type="text" name="d_billNo" id="d_billNo" size="12" maxlength="12" />
                        </td>


                        <td class="table_edit_tr_td_label" >票卡主类型:</td>
                        <td class="table_edit_tr_td_input">
                            <select id="d_cardMainType" name="d_cardMainType" onChange="setSelectValues('detailOp', 'd_cardMainType', 'd_cardSubType', 'commonVariable');" dataType="Require" msg="票卡主类型不能为空!" >				    
                                <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_maincard_clean" />
                            </select>
                        </td>
                        <td class="table_edit_tr_td_label"><div align="right">票卡子类型:</div></td>
                        <td class="table_edit_tr_td_input">
                            <select id="d_cardSubType" name="d_cardSubType" dataType="Require" msg="票卡子类型不能为空!">
                                <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_none" />
                            </select>
                            <c:set var="pVarName" scope="request" value="commonVariable"/>
                            <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_mainsubcard" />
                        </td>
                    </tr>
                    <tr class="table_edit_tr">                      
                        <td class="table_edit_tr_td_label">出库数量:</td>
                        <td class="table_edit_tr_td_input">
                            <input type="text" name="d_amount" id="d_amount" size="10" require="true" maxlength="9" dataType="integer|Positive"  msg="出库数量为大于零的整数!" />
                        </td>
                        <td class="table_edit_tr_td_label">仓库:</td>
                        <td class="table_edit_tr_td_input">
                            <select id="d_storage" name="d_storage" dataType="Require" msg="仓库不能为空!" onChange="setSelectValues('detailOp', 'd_storage', 'd_zone', 'commonVariable1');">
                                <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_storage" />
                            </select>

                        </td>
                        <td class="table_edit_tr_td_label"><div align="right">票区:</div></td>
                        <td class="table_edit_tr_td_input">
                            <select id="d_zone" name="d_zone" dataType="Require" msg="库存票区不能为空!">
                                <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_none" />
                            </select>
                            <c:set var="pVarName" scope="request" value="commonVariable1"/>
                            <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_storagezone_recover" />
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
            <c:set var="addClickMethod" scope="request" value="setUpdatePrimaryKey('detailOp','d_waterNo#d_billNo#d_cardMainType#d_cardSubType#d_cardMoney#d_cardMoney1');"/>
            <c:set var="clickMethod" scope="request" value="btnClick('detailOp','clearStart','detail');disableForm(['d_waterNo','d_billNo'],true);"/>
            <c:set var="addAfterMethod" scope="request" value=""/>
            <c:set var="exportBillAfter" scope="request" value="exportBillDetail('detailOp','w_rpt_ic_rct_bl_out_qc',['p_bill_no'],['queryCondition'],800,500)"/>


            <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all" />
            <!-- <input type="hidden" name="validDays" id="validDays" value="{/Service/Result/validDays}" /> -->
            <input type="hidden" name="validDays" id="validDays" value="${validDays}" />
            <br/>
        </FORM>


        <!-- 状态栏 通用模板 -->

        <c:import url="/jsp/common/common_template.jsp?template_name=common_status_table" />
    </body>

</html>
