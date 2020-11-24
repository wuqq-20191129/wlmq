<%-- 
    Document   : station_device
    Created on : 2017-5-16, 20:48:42
    Author     : hejj
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <!--add by zhongzq 20180609-->
        <meta http-equiv="X-UA-Compatible" content="IE=9,Chrome=1" />
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>生产计划明细</title>


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
//            setPrimaryKeys('detailOp', 'd_waterNo#d_billNo#d_workType#d_cardMainType#d_cardSubType#d_cardMoney#d_cardMoney1');
            setPrimaryKeys('detailOp', 'd_waterNo#d_billNo#d_workType#d_cardMainType#d_cardSubType');
            setDetailBillNo('detailOp', 'd_billNo', 'queryCondition');
            controlByRecordFlagForInit('detailOp', ['add'], true);

            setSelectValuesByFixed('detailOp', 'd_workType', ['00', '01', '02', '03']);

           
            setTableRowBackgroundBlock('DataTable')">

        <!--<body >-->
        <table  class="table_title">
            <tr align="center" class="trTitle">
                <td colspan="4">生产计划明细
                </td>
            </tr>
        </table>



        <!-- 表头 通用模板 -->
        <c:set var="pTitleName" scope="request" value="列表"/>
        <c:set var="pTitleWidth" scope="request" value="50"/>
        <c:import url="/jsp/common/common_template.jsp?template_name=common_table_title" />
        <div id="clearStartBlock" class="divForTableBlock">
            <div id="clearStartHead" class="divForTableBlockHead" style="width :1800px">
                <table class="table_list_block" id="DataTableHead" >

                    <!--说明：列的序号从0开始 isDigit：false 表示按字符串排序 ：true表示按数值排序 sortedby：asc表示按升序 dec表示按降序 -->
                    <tr  class="table_list_tr_head_block" id="ignore">
                        <td   class="table_list_tr_col_head_block">
                            <input type="checkbox" name="rectNoAll" id="rectNoAll" onclick="selectAllRecord('detailOp', 'rectNoAll', 'rectNo', 'clearStart', 0);
                                    controlsByFlag('detailOp', ['del']);
                                    controlsByFlagWithoutCk('detailOp', ['modify']);
                                    controlByRecordFlagForInit('detailOp', ['add'], true);"/>
                        </td>	
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="1"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >流水号</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="2"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width:80px" >计划单</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="3"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >工作类型</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="4"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >票卡主类型</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false  index="5"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >票卡子类型</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="6"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >记名卡标志</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="7"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >仓库</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="8"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >票区</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="9"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >出库原因</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=true index="10" sortedby="asc"  onclick="sortForTableBlock('clearStart');">领票数量</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=true index="11" sortedby="asc"  onclick="sortForTableBlock('clearStart');">生产数量</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="12" sortedby="asc"  onclick="sortForTableBlock('clearStart');">出库卡面值</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="13" sortedby="asc"  onclick="sortForTableBlock('clearStart');">生产卡面值</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="14" sortedby="asc"  onclick="sortForTableBlock('clearStart');">有效期</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="15" sortedby="asc"  onclick="sortForTableBlock('clearStart');" style="width:100px">进出站限制模式</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="16" sortedby="asc"  onclick="sortForTableBlock('clearStart');">进站线路</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="17" sortedby="asc"  onclick="sortForTableBlock('clearStart');">进站车站</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="18" sortedby="asc"  onclick="sortForTableBlock('clearStart');">出站线路</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="19" sortedby="asc"  onclick="sortForTableBlock('clearStart');">出站车站</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="20" sortedby="asc"  onclick="sortForTableBlock('clearStart');">机器号</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="21" sortedby="asc"  onclick="sortForTableBlock('clearStart');" style="width:100px">乘次票有效天数</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="22" sortedby="asc"  onclick="sortForTableBlock('clearStart');">测试标志</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="23" sortedby="asc"  onclick="sortForTableBlock('clearStart');">订单类型</td>


                    </tr>


                </table>

            </div>

            <div id="clearStart"  class="divForTableBlockData" style="width :1800px">
                <table class="table_list_block" id="DataTable" >

                    <c:forEach items="${ResultSet}" var="rs">

                        <tr class="table_list_tr_data"  onMouseOver="overResultRow('detailOp', this);" 
                            onMouseOut="outResultRow('detailOp', this);" 
                            onclick="setSelectValuesByRowPropertyName('detailOp', 'd_cardSubType', 'commonVariable', 'cardMainCode');
                                    setSelectValuesByRowPropertyName('detailOp', 'd_zone', 'commonVariable1', 'storageId');
                                    setSelectValuesByRowPropertyName('detailOp', 'd_stationId', 'commonVariable2', 'lineId');
                                    setSelectValuesByRowPropertyName('detailOp', 'd_outReason', 'commonVariable3', 'esWorkTypeId');
                                    setSelectValuesByRowPropertyName('detailOp', 'd_exitStationId', 'commonVariable5', 'exitLineId');
                                    clickResultRow('detailOp', this, 'detail');
                                    setSelectValuesByMapping('detailOp', 'd_workType', 'd_zone', 'commonVariable1',
                                            ['00', '01', '02', '03', '05'], [['01'], ['01', '02', '04'], ['04'], ['05'], ['02', '05', '07']]);
                                    setSelectValuesByMapping('detailOp', 'd_workType', 'd_cardMainType', 'commonVariable4',
                                            ['00', '01', '02', '03', '05'], [['1', '12', '2', '40', '7', '8', '10'], ['1', '12', '2', '40', '7', '8', '10'], ['1', '12', '2', '40', '7', '8', '10'], ['1', '12', '9', '2', '40', '7', '8', '10'], ['12', '7', '8']]);
                                    clickResultRow('detailOp', this, 'detail');

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
                            <td  id="workType" class="table_list_tr_col_data_block">
                                ${rs.es_worktype_id_name}
                            </td>
                            <td  id="cardMainType" class="table_list_tr_col_data_block">
                                ${rs.ic_main_type_name}
                            </td>
                            <td  id="cardSubType" class="table_list_tr_col_data_block">
                                ${rs.ic_sub_type_name}
                            </td>
                            <td  id="newOldFlag" class="table_list_tr_col_data_block">
                                ${rs.card_type_name}
                            </td>
                            <td  id="storage" class="table_list_tr_col_data_block">
                                ${rs.storage_id_name}
                            </td>
                            <td  id="zone" class="table_list_tr_col_data_block">
                                ${rs.area_id_name}
                            </td>
                            <td  id="outReason" class="table_list_tr_col_data_block">
                                ${rs.reason_id_name}
                            </td>
                            <td  id="drawAmount" class="table_list_tr_col_data_block">
                                ${rs.draw_quantity}
                            </td>
                            <td  id="amount" class="table_list_tr_col_data_block">
                                ${rs.make_quantity}
                            </td>

                            <td  id="cardMoney" class="table_list_tr_col_data_block">
                                ${rs.card_money}
                            </td>
                            <td  id="cardMoneyProduce" class="table_list_tr_col_data_block">
                                ${rs.card_money_produce}
                            </td>
                            <td  id="validDate" class="table_list_tr_col_data_block">
                                ${rs.valid_date}
                            </td>
                            <td  id="mode" class="table_list_tr_col_data_block" style="width:100px">
                                ${rs.model_name}
                            </td>
                            <td  id="lineId" class="table_list_tr_col_data_block">
                                ${rs.line_id_name}
                            </td>
                            <td  id="stationId" class="table_list_tr_col_data_block">
                                ${rs.station_id_name}
                            </td>
                            <td  id="exitLineId" class="table_list_tr_col_data_block">
                                ${rs.exit_line_id_name}
                            </td>
                            <td  id="exitStationId" class="table_list_tr_col_data_block">
                                ${rs.exit_station_id_name}
                            </td>
                            <td  id="machineNo" class="table_list_tr_col_data_block">
                                ${rs.machine_no}
                            </td>
                            <td  id="cardAvaDays" class="table_list_tr_col_data_block" style="width:100px">
                                ${rs.card_ava_days}
                            </td>
                            <td  id="testFlag" class="table_list_tr_col_data_block">
                                ${rs.test_flag_name}
                            </td>
                            <td  id="orderType" class="table_list_tr_col_data_block">
                                ${rs.order_type_name}
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

        <form method="post" name="detailOp" id="detailOp" action="ticketStorageOutProduceManage" >

            <c:set var="divideShow" scope="request" value="0"/>
            <c:import  url="/jsp/common/common_template_web_variable.jsp?template_name=common_web_variable" />
            <c:import  url="/jsp/common/common_template_web_variable_ic.jsp?template_name=common_web_variable_ic" />

            <!--<DIV id="detail" align="center" class="divForTableData" style="position:relative; left:20; width:95%; height:10; layer-background-color:#99CC99; border:thin outset #ffffff; z-index:1;">-->
            <div id="detail"  class="divForTableDataDetail" >
                <table  class="table_edit_detail">
                    <tr class="table_edit_tr">
                        <td class="table_edit_tr_td_label">流水号: </td>
                        <td class="table_edit_tr_td_input">
                            <input type="text" name="d_waterNo" id="d_waterNo" size="10" maxlength="18" />
                        </td>
                        <td class="table_edit_tr_td_label">计划单: </td>
                        <td class="table_edit_tr_td_input">
                            <input type="text" name="d_billNo" id="d_billNo" size="10" maxlength="12" />
                        </td>
                        <td class="table_edit_tr_td_label">工作类型: </td>
                        <td class="table_edit_tr_td_input">
                            <select id="d_workType" name="d_workType" dataType="Require" msg="工作类型不能为空!"
                                    onChange="
                                            setSelectValues('detailOp', 'd_workType', 'd_outReason', 'commonVariable3');
                                            setSelectValuesByMapping('detailOp', 'd_workType', 'd_zone', 'commonVariable1',
                                                    ['00', '01', '02', '03', '05'], [['01'], ['02', '04'], ['04'], ['05'], ['02', '05', '07']]);

                                            setSelectValuesByMulMapping('detailOp', 'd_workType', 'd_zone', 'd_outReason', 'commonVariable3',
                                                    ['01#02', '01#04'],
                                                    [['04'],
                                                        ['05']]
                                                    );


                                            setSelectValuesByMapping('detailOp', 'd_workType', 'd_cardMainType', 'commonVariable4',
                                                    ['00', '01', '02', '03', '05'], [['1', '12', '2', '40', '7', '8', '10'], ['1', '12', '2', '40', '7', '8', '10'], ['1', '12', '2', '40', '7', '8', '10'], ['1', '12', '9', '2', '40', '7', '8', '10'], ['12', '7', '8']]);
                                            setSelectValues('detailOp', 'd_cardMainType', 'd_cardSubType', 'commonVariable');
                                            controlDisableForTctAtt('detailOp', 'd_workType', 'd_cardMainType', ['d_cardAvaDays']);
                                            controlDisableForMode('detailOp', 'd_workType', [ 'd_mode']);
                                            controlEnableForTctAtt('detailOp', 'd_workType', 'd_cardMainType', ['d_cardMoneyProduce', 'd_cardMoneyProduce1']);
                                            controlLineStationByCardTypeAndWorkType('detailOp', 'd_workType', 'd_cardMainType', 'd_lineId', 'd_stationId');
                                            controlCardMoneyWorkType('detailOp', 'd_workType', 'd_cardMoneyProduce', 'd_cardMoneyProduce1');
                                            controlOldNewFlagWorkType('detailOp', 'd_workType', 'd_newOldFlag');
                                            controlValidDateByWorkType('detailOp', 'd_workType', 'd_validDate', 'lbl_validDate');
                                    ">
                                <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_none" />
                                <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_worktype" />
                            </select>	
                        </td>

                        <td class="table_edit_tr_td_label">票卡主类型:</td>
                        <td class="table_edit_tr_td_input">
                            <select id="d_cardMainType"
                                    name="d_cardMainType" dataType="Require" msg="票卡主类型不能为空!"
                                    onChange="setSelectValues('detailOp', 'd_cardMainType', 'd_cardSubType', 'commonVariable');
                                            controlDisableForTctAtt('detailOp', 'd_workType', 'd_cardMainType', ['d_cardAvaDays']);


                                            controlLineStationByCardTypeAndWorkType('detailOp', 'd_workType', 'd_cardMainType', 'd_lineId', 'd_stationId');
                                            controlCradAvaDaysByCardType('detailOp', 'd_cardMainType', 'd_cardAvaDays');
                                    ">
                                <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_none" />
                            </select>
                            <c:set var="pVarName" scope="request" value="commonVariable4"/>
                            <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_maincard_serial" />
                        </td>
                    </tr>
                    <tr class="table_edit_tr">
                        <td class="table_edit_tr_td_label"><div align="right">票卡子类型:</div></td>
                        <td class="table_edit_tr_td_input">
                            <select id="d_cardSubType"
                                    name="d_cardSubType" dataType="Require" msg="票卡子类型不能为空!"
                                    onChange="

                                            controlDisableForTctAtt('detailOp', 'd_workType', 'd_cardMainType', ['d_cardAvaDays']);
                                            setValidDefaultValue('detailOp', 'd_workType', 'd_cardMainType', 'd_cardSubType', 'd_validDate');
                                    ">
                                <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_none" />
                            </select>
                            <c:set var="pVarName" scope="request" value="commonVariable"/>
                            <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_mainsubcard" />
                        </td>

                   
                        <td class="table_edit_tr_td_label">记名卡标志:</td>
                        <td class="table_edit_tr_td_input">
                            <select id="d_newOldFlag" name="d_newOldFlag" dataType="Require" msg="记名卡标志不能为空!" >
                                <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_signcardflag" />
                            </select>
                        </td>
                        <td class="table_edit_tr_td_label">生产数量:</td>
                        <td class="table_edit_tr_td_input">
                            <input type="text" name="d_amount" id="d_amount" size="10" require="true" maxlength="9" dataType="integer|Positive" msg="生产数量为大于零的整数!" />
                        </td>
                        <td class="table_edit_tr_td_label">领票数量:</td>
                        <td class="table_edit_tr_td_input">
                            <input type="text" name="d_drawAmount"  id="d_drawAmount" size="10" require="true" maxlength="9" operator="GreaterThanEqual" to="d_amount" dataType="integer|Positive|CompareNum"
                                   msg="领票数量为大于零的整数且大于等于生产数量!" />
                        </td>
                    </tr>
                    <tr class="table_edit_tr">


                        <!--
                        del
                        setSelectValues('detailOp', 'd_storage', 'd_lineId', 'commonVariable8');
                         setSelectValues('detailOp', 'd_storage', 'd_exitLineId', 'commonVariable9');
                        -->
                        <td class="table_edit_tr_td_label">仓库:</td>
                        <td class="table_edit_tr_td_input">
                            <select id="d_storage"
                                    name="d_storage" dataType="Require" msg="仓库不能为空!"
                                    onChange="

                                            setSelectValues('detailOp', 'd_storage', 'd_zone', 'commonVariable1');
                                            setSelectValues('detailOp', 'd_lineId', 'd_stationId', 'commonVariable2');

                                            setSelectValues('detailOp', 'd_exitLineId', 'd_exitStationId', 'commonVariable5');
                                            controlLineStationByMode('detailOp', 'd_mode', 'd_lineId', 'd_stationId', 'd_exitLineId', 'd_exitStationId');
                                            
                                            
                                            setSelectValuesByMapping('detailOp', 'd_workType', 'd_zone', 'commonVariable1',
                                                    ['00', '01', '02', '03', '05'], [['01'], ['02', '04'], ['04'], ['05'], ['02', '05', '07']]);

                                            setSelectValuesByMulMapping('detailOp', 'd_workType', 'd_zone', 'd_outReason', 'commonVariable3',
                                                    ['01#02', '01#04'],
                                                    [['04'],
                                                        ['05']]
                                                    );

                                    ">


                                <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_storage" />
                            </select>

                        </td>


                        <td class="table_edit_tr_td_label"><div align="right">票区:</div></td>
                        <td class="table_edit_tr_td_input">
                            <select id="d_zone" name="d_zone" dataType="Require" msg="库存票区不能为空!"
                                    onChange=" setSelectValuesByMapping('detailOp', 'd_zone', 'd_outReason', 'commonVariable3',
                                                    ['02', '04'], [['04'], ['05']]);
                                    ">
                                <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_none" />

                            </select>
                            <c:set var="pVarName" scope="request" value="commonVariable1"/>
                            <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_storagezone" />
                        </td>

                    
                        <td class="table_edit_tr_td_label">出库原因:</td>
                        <td class="table_edit_tr_td_input">
                            <select id="d_outReason" name="d_outReason" dataType="Require" msg="出库原因不能为空!">
                                <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_none" />

                            </select>
                            <c:set var="pVarName" scope="request" value="commonVariable3"/>
                            <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_outreason_produce_serial" />
                        </td>

                        <!--<td class="table_edit_tr_td_label">出库卡面值(分/次):</td>-->
                        <!--<td class="table_edit_tr_td_input">-->
                            <input type="hidden"
                                   name="d_cardMoney" id="d_cardMoney" size="3" require="true"
                                   dataType="Integer|Limit" maxlength="5" value="0"
                                   msg="出库卡面值应是数字,0值表示卡无面值" />

<!--                            <select id="d_cardMoney1"
                                    name="d_cardMoney1" dataType="false"
                                    onChange="setCardMoney('detailOp', 'd_cardMoney', 'd_cardMoney1')">

                                <%--<c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_cardmoney" />--%>
                            </select>-->
                        <!--</td>-->
                        <td class="table_edit_tr_td_label">生产卡面值(分/次):</td>
                        <td class="table_edit_tr_td_input">
                            <input type="text"
                                   name="d_cardMoneyProduce" id="d_cardMoneyProduce" size="5"
                                   require="true" dataType="Integer|Limit" maxlength="5" 
                                   value="0" msg="生产卡面值应是数字,0值表示卡无面值" />

                            <select id="d_cardMoneyProduce1"
                                    name="d_cardMoneyProduce1" dataType="false"
                                    onChange="setCardMoney('detailOp', 'd_cardMoneyProduce', 'd_cardMoneyProduce1')">
                                <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_cardmoney" />
                            </select>

                        </td>
                    </tr>
                    <tr class="table_edit_tr">

                        <td class="table_edit_tr_td_label" id="lbl_validDate">有效期:</td>
                        <td class="table_edit_tr_td_input">
                            <input type="text" name="d_validDate" id="d_validDate" value="" size="12" require="false" dataType="Date" format="ymd"
                                   msg="有效期格式为(yyyy-mm-dd)!" />
                            <a href="javascript:openCalenderDialogByID('d_validDate','false');">
                                <img src="./images/calendar.gif" width="15" height="15" border="0" style="block"/>
                            </a>
                            <!--
                            <a href="javascript:openCalenderDialog(document.all.d_validDate);">
                                <img src="./images/calendar.gif" width="12" height="15" border="0" style="block" />
                            </a>
                            -->
                        </td>
                        <td class="table_edit_tr_td_label">进出站限制模式:</td>
                        <td class="table_edit_tr_td_input">
                            <select id="d_mode" name="d_mode" require="false" msg="限制模式不能为空"
                                    onChange="controlLineStationByMode('detailOp', 'd_mode', 'd_lineId', 'd_stationId', 'd_exitLineId', 'd_exitStationId');">
                                <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_mode" />
                            </select>
                        </td>
                    
                        <td class="table_edit_tr_td_label">进站线路:</td>
                        <td class="table_edit_tr_td_input">
                            <select id="d_lineId" name="d_lineId" require="false" dataType="NotEmpty" msg="进站线路不能为空"
                                    onChange="setSelectValues('detailOp', 'd_lineId', 'd_stationId', 'commonVariable2');">
                                <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_icline" />
                            </select>
                        </td>
                        
                        <td class="table_edit_tr_td_label"><div align="right">进站车站:</div></td>
                        <td class="table_edit_tr_td_input">
                            <select id="d_stationId" name="d_stationId" require="false"  dataType="NotEmpty" msg="进站车站不能为空" >
                                <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_none" />
                            </select>
                            <c:set var="pVarName" scope="request" value="commonVariable2"/>
                            <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_iclinestation" />
                        </td>
                    </tr>
                     <tr class="table_edit_tr">

                        <td class="table_edit_tr_td_label">出站线路:</td>
                        <td class="table_edit_tr_td_input">
                            <select id="d_exitLineId"
                                    name="d_exitLineId" require="false" dataType="NotEmpty" msg="出站线路不能为空"
                                    onChange="setSelectValues('detailOp', 'd_exitLineId', 'd_exitStationId', 'commonVariable5');">
                                <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_icline" />
                            </select>
                        </td>
                        <td class="table_edit_tr_td_label"><div align="right">出站车站:</div></td>
                        <td class="table_edit_tr_td_input">
                            <select id="d_exitStationId" name="d_exitStationId" require="false" dataType="NotEmpty" msg="出站车站不能为空">
                                <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_none" />
                            </select>
                            <c:set var="pVarName" scope="request" value="commonVariable5"/>
                            <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_iclinestation" />
                        </td>
                        <td class="table_edit_tr_td_label">机器号:</td>
                        <td class="table_edit_tr_td_input">
                            <!--<input type="text" name="d_machineNo" id="d_machineNo" size="10" require="false" maxlength="4" dataType="Number" msg="机器号应为数字" />-->
                            <select id="d_machineNo" name="d_machineNo" dataType="false">
                                <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_devCodeES" />
                            </select>
                        </td>
                        
                        <td class="table_edit_tr_td_label">乘次票有效天数:</td>
                        <td class="table_edit_tr_td_input">
                            <input type="text" id="d_cardAvaDays" name="d_cardAvaDays" size="10" require="true" maxlength="3" min="3" max="3" 
                                   dataType="Number|NotNegative|Limit|MaxValue" msg="乘次票有效天数应为3位正整数且小于等于255" value="000" />
                        </td>
                    </tr>
                    <tr class="table_edit_tr">
                        <td class="table_edit_tr_td_label">测试标志:</td>
                        <td class="table_edit_tr_td_input">
                            <select id="d_testFlag" name="d_testFlag" dataType="Require" msg="测试标志不能为空!">
                                <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_testflag" />
                            </select>
                        </td>
                        <td class="table_edit_tr_td_label">订单类型:</td>
                        <td class="table_edit_tr_td_input">
                            <select id="d_orderType" name="d_orderType" dataType="Require" msg="订单类型不能为空!">
                                <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_ordertype" />
                            </select>
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
            <%--<c:set var="addClickMethod" scope="request" value="setUpdatePrimaryKey('detailOp','d_waterNo#d_billNo#d_workType#d_cardMainType#d_cardSubType#d_cardMoney#d_cardMoney1');controlByCardTypeAndAreaForProduce();"/>--%>
            <c:set var="addClickMethod" scope="request" value="setUpdatePrimaryKey('detailOp','d_waterNo#d_billNo#d_workType#d_cardMainType#d_cardSubType');controlByCardTypeAndAreaForProduce();"/>
            <c:set var="clickMethod" scope="request" value="btnClick('detailOp','clearStart','detail');controlByAreaForProduce();"/>
            <%--<c:set var="addAfterMethod" scope="request" value="disableFormControls('detailOp',['d_waterNo','d_billNo','d_cardMoney','d_lineId','d_stationId','d_exitLineId','d_exitStationId','d_mode','d_cardMoney1'],true);"/>--%>
            <c:set var="addAfterMethod" scope="request" value="disableFormControls('detailOp',['d_waterNo','d_billNo','d_lineId','d_stationId','d_exitLineId','d_exitStationId','d_mode'],true);"/>
            <c:set var="exportBillAfter" scope="request" value="exportBillDetail('detailOp','w_rpt_ic_rct_bl_out_plan',['p_bill_no'],['queryCondition'],800,500)"/>
            

            <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all" />
            <!-- <input type="hidden" name="validDays" id="validDays" value="{/Service/Result/validDays}" /> -->
            <input type="hidden" name="validDays" id="validDays" value="${validDays}" />
            <br/>
        </FORM>


        <!-- 状态栏 通用模板 -->

        <c:import url="/jsp/common/common_template.jsp?template_name=common_status_table" />
    </body>
</html>
