<%--
    Document   : TicketStorageOutDistributePlanDetail
    Created on : 2017-8-2
    Author     : zhongziqi
--%>


<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="X-UA-Compatible" content="IE=9,Chrome=1" />
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>配票计划明细</title>


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

        <!--<body >-->
        <table  class="table_title">
            <tr align="center" class="trTitle">
                <td colspan="4">配票计划明细
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
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=true index="1"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >流水号</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="2"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width:80px" >计划单</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="3"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >单据状态</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="4"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width:80px">出库原因</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false  index="5"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >票卡主类型</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="6"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >票卡子类型</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=true index="7"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >面值(分/次)</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="8"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width:80px">有效期</td>
<!--                         <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="9"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width:100px">是否限制线路使用</td> -->
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="9" sortedby="asc"  onclick="sortForTableBlock('clearStart');" style="width:100px">进出站限制模式</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="10" sortedby="asc"  onclick="sortForTableBlock('clearStart');">卡进站线路</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="11" sortedby="asc"  onclick="sortForTableBlock('clearStart');">卡进站车站</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="12" sortedby="asc"  onclick="sortForTableBlock('clearStart');">卡出站线路</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="13" sortedby="asc"  onclick="sortForTableBlock('clearStart');">卡出站车站</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="14" sortedby="asc"  onclick="sortForTableBlock('clearStart');">配发线路</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=true index="15" sortedby="asc"  onclick="sortForTableBlock('clearStart');">配票数量</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="16" sortedby="asc"  onclick="sortForTableBlock('clearStart');">仓库</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="17" sortedby="asc"  onclick="sortForTableBlock('clearStart');">票区</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=true index="18" sortedby="asc"  onclick="sortForTableBlock('clearStart');" style="width:120px">盒号</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=true index="19" sortedby="asc"  onclick="sortForTableBlock('clearStart');" style="width:130px">逻辑卡号起</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=true index="20" sortedby="asc"  onclick="sortForTableBlock('clearStart');" style="width:130px">逻辑卡号止</td>


                    </tr>


                </table>

            </div>

            <div id="clearStart"  class="divForTableBlockData" style="width :1800px">
                <table class="table_list_block" id="DataTable" >
                    <c:forEach items="${ResultSet}" var="rs">
                        <tr class="table_list_tr_data"  onMouseOver="overResultRow('detailOp', this);"
                            onMouseOut="outResultRow('detailOp', this);"
                            onclick="
                                    setSelectValuesByRowPropertyName('detailOp', 'd_icSubType', 'commonVariable1', 'icMainType');
                                    setSelectValuesByRowPropertyName('detailOp', 'd_lineId', 'commonVariable4', 'storageId');
                                    setSelectValuesByRowPropertyName('detailOp', 'd_lineId', 'commonVariable4', 'storageId');
                                    setSelectValuesByRowPropertyName('detailOp', 'd_stationId', 'commonVariable5', 'lineId');
                                    setSelectValuesByRowPropertyName('detailOp', 'd_exitLineId', 'commonVariable6', 'storageId');
                                    setSelectValuesByRowPropertyName('detailOp', 'd_exitStationId', 'commonVariable7', 'exitLineId');
                                    setSelectValuesByRowPropertyName('detailOp', 'd_distributeLineId', 'commonVariable8', 'storageId');
                                    setSelectValueByRowValue('detailOp', 'd_reasonId', '${rs.reasonId}');
                                    setSelectValuesByMapping('detailOp', 'd_reasonId', 'd_icMainType', 'commonVariable', ['17', '18', '19'],
                                            [['12', '1', '2', '40', '7', '8', '10'], ['12', '1', '2', '40', '7', '8', '10'], ['2']]);
                                    setSelectValuesByMulMapping('detailOp', 'd_reasonId', 'd_icMainType',
                                    'd_areaId', 'commonVariable2',
                                    ['17#12', '17#1', '17#2', '17#40', '17#7', '17#8', '17#10', '18#12', '18#1', '18#2', '18#40', '18#7', '18#8', '18#10', '19#2'],
                                    [['02', '03','04','07'],
                                    ['02', '03'],
                                    ['02', '03', '04'],
                                    ['02', '03'],
                                    ['02', '03'],
                                    ['02', '03'],
                                    ['02', '03'],
                                    ['01', '02', '03', '04', '05', '06', '07'],
                                    ['01', '02', '03', '04', '05', '06', '07'],
                                    ['01', '02', '03', '04', '05', '06', '07'],
                                    ['01', '02', '03', '04', '05', '06', '07'],
                                    ['01', '02', '03', '04', '05', '06', '07'],
                                    ['01', '02', '03', '04', '05', '06', '07'],
                                    ['01', '02', '03', '04', '05', '06', '07'],
                                    ['02', '03', '04']
                                    ]);
                                    clickOneResultRow('detailOp', this, 'detail','rectNo');
                                    controlsByFlag('detailOp', ['del']);
                                    controlsByFlagWithoutCk('detailOp', ['modify']);
                                    controlByRecordFlagForInit('detailOp', ['add'], true);
                                    disableRadio('detailOp', 'd_reasonId', 'd_select');
                                    controlRadiosForDistribute('detailOp', '${rs.distributeQuantity}', '${rs.startLogicalId}', '${rs.endLogicalId}', '${rs.boxId}', ['d_select', 'd_distributeQuantity', 'd_startLogicalId', 'd_endLogicalId', 'd_boxId', 'd_endBoxId']);

                            "
                            id="${rs.billNo}#${rs.waterNo}" flag="${rs.recordFlag}" icMainType="${rs.icMainType}" storageId="${rs.storageId}"
                            reasonId="${rs.reasonId}" distributeLineId="${rs.distributeLineId}" lineId="${rs.lineId}" exitLineId="${rs.exitLineId}" >

                            <td id="rectNo1" class="table_list_tr_col_data_block" >
                                <input type="checkbox" name="rectNo" id="rectNo" onclick="unSelectAllRecord('detailOp', 'rectNoAll', 'rectNo');
                                        controlsByFlag('detailOp', ['modify', 'del']);
                                        controlByRecordFlagForInit('detailOp', ['add'], true);"
                                       value="${rs.billNo}"  flag="${rs.recordFlag}" >
                                </input>
                            </td>
                            <td  id="waterNo" class="table_list_tr_col_data_block" >
                                ${rs.waterNo}
                            </td>
                            <td  id="billNo" class="table_list_tr_col_data_block" style="width:80px">
                                ${rs.billNo}
                            </td>
                            <td  id="recordFlag" class="table_list_tr_col_data_block">
                                ${rs.recordFlagName}
                            </td>
                            <td  id="reasonId" class="table_list_tr_col_data_block" style="width:80px">
                                ${rs.reasonName}
                            </td>
                            <td  id="icMainType" class="table_list_tr_col_data_block">
                                ${rs.icMainTypeName}
                            </td>
                            <td  id="icSubType" class="table_list_tr_col_data_block">
                                ${rs.icSubTypeName}
                            </td>
                            <td  id="cardMoney" class="table_list_tr_col_data_block">
                                ${rs.cardMoney}
                            </td>
                            <td  id="validDate" class="table_list_tr_col_data_block" style="width:80px">
                                ${rs.validDate}
                            </td>
<!--                             <td  id="restrictFlag" class="table_list_tr_col_data_block" style="width:100px"> -->
<%--                                 ${rs.restrictName} --%>
<!--                             </td> -->
                            <td  id="mode" class="table_list_tr_col_data_block" style="width:100px">
                                ${rs.modelName}
                            </td>
                            <td  id="lineId" class="table_list_tr_col_data_block">
                                ${rs.lineName}
                            </td>
                            <td  id="stationId" class="table_list_tr_col_data_block">
                                ${rs.stationName}
                            </td>
                            <td  id="exitLineId" class="table_list_tr_col_data_block">
                                ${rs.exitLineName}
                            </td>
                            <td  id="exitStationId" class="table_list_tr_col_data_block">
                                ${rs.exitStationName}
                            </td>
                            <td  id="distributeLineId" class="table_list_tr_col_data_block">
                                ${rs.distributeLineName}
                            </td>
                            <td  id="distributeQuantity" class="table_list_tr_col_data_block">
                                ${rs.distributeQuantity}
                            </td>
                            <td  id="storageId" class="table_list_tr_col_data_block">
                                ${rs.storageName}
                            </td>
                            <td  id="areaId" class="table_list_tr_col_data_block">
                                ${rs.areaName}
                            </td>
                            <td  id="boxId" class="table_list_tr_col_data_block" style="width:120px">
                                ${rs.boxId}
                            </td>
                            <td  id="startLogicalId" class="table_list_tr_col_data_block" style="width:130px">
                                ${rs.startLogicalId}
                            </td>
                            <td  id="endLogicalId" class="table_list_tr_col_data_block" style="width:130px">
                                ${rs.endLogicalId}
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

        <form method="post" name="detailOp" id="detailOp" action="ticketStorageOutDistributeManage" >

            <c:set var="divideShow" scope="request" value="0"/>
            <c:set var="operTypeValue" scope="request" value="planDetail"/>
            <c:set var="queryConditionValue" scope="request" value="${QueryCondition}"/>
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
                        <td class="table_edit_tr_td_label">出库原因: </td>
                        <td class="table_edit_tr_td_input">
                            <select id="d_reasonId" name="d_reasonId" dataType="Require" msg="出库原因不能为空!"
                                    onChange="
                                            showControlByReasonId('d_reasonId',
                                                    ['d_distributeLineId', 'd_distributeQuantity', 'd_startLogicalId', 'd_endLogicalId', 'd_boxId', 'd_endBoxId'],
                                                    ['d_content', 'd_content_sel']);
                                            disableFormControlsById(['d_lineId', 'd_stationId'], true);
                                            setSelectValuesByMapping('detailOp', 'd_reasonId', 'd_icMainType', 'commonVariable', ['17', '18', '19'],
                                                    [['12', '1', '2', '40', '7', '8', '10'], ['12', '1', '2', '40', '7', '8', '10'], ['2']]);
                                            setSelectValues('detailOp', 'd_icMainType', 'd_icSubType', 'commonVariable1');
                                            setSelectValuesByMulMapping('detailOp', 'd_reasonId', 'd_icMainType',
                                                    'd_areaId', 'commonVariable2',
                                                    ['17#12', '17#1', '17#2', '17#40', '17#7', '17#8', '17#10', '18#12', '18#1', '18#2', '18#40', '18#7', '18#8', '18#10', '19#2'],
                                                    [['02', '03','04','07'],
                                                        ['02', '03'],
                                                        ['02', '03', '04'],
                                                        ['02', '03'],
                                                        ['02', '03'],
                                                        ['02', '03'],
                                                        ['02', '03'],
                                                        ['01', '02', '03', '04', '05', '06', '07'],
                                                        ['01', '02', '03', '04', '05', '06', '07'],
                                                        ['01', '02', '03', '04', '05', '06', '07'],
                                                        ['01', '02', '03', '04', '05', '06', '07'],
                                                        ['01', '02', '03', '04', '05', '06', '07'],
                                                        ['01', '02', '03', '04', '05', '06', '07'],
                                                        ['01', '02', '03', '04', '05', '06', '07'],
                                                        ['02', '03', '04']
                                                    ]);
                                            setFormCheckOptionBySelect('d_reasonId', ['17', '18', '19'],
                                                    ['d_content'], ['true', 'false', 'false']);
                                            setFormCheckOptionBySelect('d_reasonId', ['17', '18', '19'],
                                                    ['d_distributeLineId'], ['false', 'false', 'false']);
                                            disableRadio('detailOp', 'd_reasonId', 'd_select');
                                            controlByCardTypeAndAreaForMode();
                                            controlByCardTypeAndAreaForDistribute();
                                            controlToCardMoneyByDefualt();
                                            controlDistributeLineByReason('detailOp','d_reasonId','d_distributeLineId');
                                    ">
                                <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_outreason_distribute" />
                            </select>
                        </td>

                        <td class="table_edit_tr_td_label">票卡主类型:</td>
                        <td class="table_edit_tr_td_input">
                            <select id="d_icMainType"
                                    name="d_icMainType" dataType="Require" msg="票卡主类型不能为空!"
                                    onChange="setSelectValues('detailOp', 'd_icMainType', 'd_icSubType', 'commonVariable1');
                                            setSelectValuesByMulMapping('detailOp', 'd_reasonId', 'd_icMainType',
                                                    'd_areaId', 'commonVariable2',
                                                    ['17#12', '17#1', '17#2', '17#40', '17#7', '17#8', '17#10', '18#12', '18#1', '18#2', '18#40', '18#7', '18#8', '18#10', '19#2'],
                                                    [['02', '03','04','07'],
                                                        ['02', '03'],
                                                        ['02', '03', '04'],
                                                        ['02', '03'],
                                                        ['02', '03'],
                                                        ['02', '03'],
                                                        ['02', '03'],
                                                        ['01', '02', '03', '04', '05', '06', '07'],
                                                        ['01', '02', '03', '04', '05', '06', '07'],
                                                        ['01', '02', '03', '04', '05', '06', '07'],
                                                        ['01', '02', '03', '04', '05', '06', '07'],
                                                        ['01', '02', '03', '04', '05', '06', '07'],
                                                        ['01', '02', '03', '04', '05', '06', '07'],
                                                        ['01', '02', '03', '04', '05', '06', '07'],
                                                        ['02', '03', '04']
                                                    ]);
                                            controlByCardTypeAndAreaForDistribute();
                                            controlByCardTypeAndAreaForMode();
                                            controlToCardMoneyByDefualt();

                                    ">
                                <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_none" />
                            </select>
                            <c:set var="pVarName" scope="request" value="commonVariable"/>
                            <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_maincard_serial" />
                        </td>
                    </tr>
                    <tr class="table_edit_tr">
                        <td class="table_edit_tr_td_label"><div align="right">票卡子类型:</div></td>
                        <td class="table_edit_tr_td_input">
                            <select id="d_icSubType"
                                    name="d_icSubType" dataType="Require" msg="票卡子类型不能为空!"
                                    >
                                <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_none" />
                            </select>
                            <c:set var="pVarName" scope="request" value="commonVariable1"/>
                            <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_mainsubcard" />
                        </td>


                        <td class="table_edit_tr_td_label">仓库:</td>
                        <td class="table_edit_tr_td_input">
                            <select id="d_storageId" name="d_storageId" dataType="Require" msg="仓库不能为空!"
                                    onChange="clearFormControls('detailOp', ['d_content']);
                                            setSelectValues('detailOp', 'd_storageId', 'd_lineId', 'commonVariable4');
                                            setSelectValues('detailOp', 'd_lineId', 'd_stationId', 'commonVariable5');
                                            setSelectValues('detailOp', 'd_storageId', 'd_exitLineId', 'commonVariable6');
                                            setSelectValues('detailOp', 'd_exitLineId', 'd_exitStationId', 'commonVariable7');
                                            setSelectValues('detailOp', 'd_storageId', 'd_distributeLineId', 'commonVariable8');
                                            controlLineStationByModeById('detailOp', 'd_mode', 'd_lineId', 'd_stationId', 'd_exitLineId', 'd_exitStationId');
                                    ">
                                <!--                                <select id="d_storageId" name="d_storageId" dataType="Require" msg="仓库不能为空!"
                                                                    onChange="clearFormControls('detailOp', ['d_content']);
                                                                            setSelectValues('detailOp', 'd_storageId', 'd_lineId', 'commonVariable4');
                                                                            setSelectValues('detailOp', 'd_storageId', 'd_exitLineId', 'commonVariable6');
                                                                            setSelectValues('detailOp', 'd_storageId', 'd_distributeLineId', 'commonVariable8');
                                                                            controlLineStationByMode('detailOp', 'd_mode', 'd_lineId', 'd_stationId', 'd_exitLineId', 'd_exitStationId');
                                                                    ">-->
                                <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_storage" />
                            </select>
                        </td>
                        <td class="table_edit_tr_td_label">票区:</td>
                        <td class="table_edit_tr_td_input">
                            <select id="d_areaId" name="d_areaId" dataType="Require" msg="库存票区不能为空!"
                                    onChange="controlByCardTypeAndAreaForDistribute();
                                            controlByCardTypeAndAreaForMode();
                                            controlToCardMoneyByDefualt();
                                    ">
                                <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_none" />
                            </select>
                            <c:set var="pVarName" scope="request" value="commonVariable2"/>
                            <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_storagezone" />
                        </td>
                        <td class="table_edit_tr_td_label">面值(分/次):</td>
                        <td class="table_edit_tr_td_input" >
                            <input type="text"name="d_cardMoney" id="d_cardMoney" size="3" value="0"
                                   require="false" dataType="Integer" maxlength="5"
                                   msg="面值应是数字" />
                            <select id="d_cardMoney1"
                                    name="d_cardMoney1" dataType="false"
                                    onChange="setCardMoneyById('d_cardMoney', 'd_cardMoney1')">
                                <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_cardmoney" />
                            </select>
                        </td>
                    </tr>
                    <tr class="table_edit_tr">
<!--                         <td class="table_edit_tr_td_label">是否限线路使用:</td> -->
<!--                         <td class="table_edit_tr_td_input"> -->
<!--                             <select id="d_restrictFlag" -->
<!--                                     name="d_restrictFlag" dataType="Require" msg="是否限站使用不能为空!" -->
<!--                                     onChange="controlByRestrictFlag('detailOp', ['d_distributeLineId']); -->
<!--                                             controlByCardTypeAndAreaForMode();"> -->
<%--                                 <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_none" /> --%>
<!--                             </select> -->
<%--                             <c:set var="pVarName" scope="request" value="commonVariable3"/> --%>
<%--                             <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_limit_station_flag" /> --%>
<!--                         </td> -->
                        <td class="table_edit_tr_td_label"><div align="right">有效期:</div></td>
                        <td class="table_edit_tr_td_input">
                            <input type="text" name="d_validDate" id="d_validDate" value="" size="12" require="false"  dataType="Date" format="ymd"
                                   msg="有效期格式为(yyyy-mm-dd)!" />
                            <a href="javascript:openCalenderDialogByID('d_validDate','false');">
                                <img src="./images/calendar.gif" name="d_validDateSelect" id="d_validDateSelect"  readOnly=true width="15" height="15" border="0" style="block"/>
                            </a>
                        </td>

                        <td class="table_edit_tr_td_label">进出站限制模式:</td>
                        <td class="table_edit_tr_td_input">
                            <select id="d_mode" name="d_mode" require="false" msg="限制模式不能为空"
                                    onChange="controlLineStationByModeById('detailOp', 'd_mode', 'd_lineId', 'd_stationId', 'd_exitLineId', 'd_exitStationId');">
                                <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_mode" />
                            </select>
                        </td>

                        <td class="table_edit_tr_td_label">卡进站线路:</td>
                        <td class="table_edit_tr_td_input">
                            <select id="d_lineId" name="d_lineId" require="false" dataType="NotEmpty" msg="卡进站线路不能为空"
                                    onChange="setSelectValues('detailOp', 'd_lineId', 'd_stationId', 'commonVariable5');">
                                <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_none" />
                            </select>
                            <c:set var="pVarName" scope="request" value="commonVariable4"/>
                            <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_storage_line_serial" />
                        </td>
                        <td class="table_edit_tr_td_label"><div align="right">卡进站车站:</div></td>
                        <td class="table_edit_tr_td_input">
                            <select id="d_stationId" name="d_stationId" require="false"  dataType="NotEmpty" msg="卡进站车站不能为空" >
                                <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_none" />
                            </select>
                            <c:set var="pVarName" scope="request" value="commonVariable5"/>
                            <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_iclinestation" />
                        </td>
                    </tr>
                    <tr class="table_edit_tr">


                        <td class="table_edit_tr_td_label">卡出站线路:</td>
                        <td class="table_edit_tr_td_input">
                            <select id="d_exitLineId"
                                    name="d_exitLineId" require="false" dataType="NotEmpty" msg="卡出站线路不能为空"
                                    onChange="setSelectValues('detailOp', 'd_exitLineId', 'd_exitStationId', 'commonVariable7');">
                                <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_none" />
                            </select>
                            <c:set var="pVarName" scope="request" value="commonVariable6"/>
                            <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_storage_line_serial" />
                        </td>
                        </td>
                        <td class="table_edit_tr_td_label"><div align="right">卡出站车站:</div></td>
                        <td class="table_edit_tr_td_input">
                            <select id="d_exitStationId" name="d_exitStationId" require="false" dataType="NotEmpty" msg="卡出站车站不能为空">
                                <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_none" />
                            </select>
                            <c:set var="pVarName" scope="request" value="commonVariable7"/>
                            <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_iclinestation" />
                        </td>
                        <td class="table_edit_tr_td_label">
                            <input type="radio"
                                   name="d_select" id="d_select" size="1"
                                   onClick="controlByRadioForNumForDistribute('detailOp', ['d_distributeQuantity', 'd_startLogicalId', 'd_endLogicalId', 'd_boxId', 'd_endBoxId']);"
                                   value="0" />
                         	   配票数量:</td>
                        <td class="table_edit_tr_td_input">
                            <input type="text"
                                   name="d_distributeQuantity" id="d_distributeQuantity"
                                   size="10" require="false" maxlength="9"
                                   dataType="integer|Positive" msg="配票数量为大于零的整数!" />
                        </td>
                        <td class="table_edit_tr_td_label">配发线路:</td>
                        <td class="table_edit_tr_td_input">
                            <select id="d_distributeLineId"
                                    name="d_distributeLineId" require="false" dataType="NotEmpty" msg="配发线路不能为空">
                                <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_none" />
                            </select>
                            <c:set var="pVarName" scope="request" value="commonVariable8"/>
                            <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_storage_line_serial" />
                        </td>
                    </tr>
                    <tr class="table_edit_tr">
                        <td class="table_edit_tr_td_label">
                            <input type="radio"
                                   name="d_select" id="d_select" size="1"
                                   onClick="controlByRadioForNumForDistribute('detailOp', ['d_distributeQuantity', 'd_startLogicalId', 'd_endLogicalId', 'd_boxId', 'd_endBoxId']);"
                                   value="0" />
                            逻辑卡号起:</td>
                        <td class="table_edit_tr_td_input">
                            <input type="text"
                                   name="d_startLogicalId" id="d_startLogicalId" size="14"
                                   require="false" dataType="Number|Limit" min="1" max="20"
                                   maxlength="20" msg="起始逻辑卡号应为数字" /></td>
                        <td class="table_edit_tr_td_label">逻辑卡号止:</td>
                        <td class="table_edit_tr_td_input">
                            <input type="text"
                                   name="d_endLogicalId" id="d_endLogicalId" size="20"
                                   require="false" min="16" max="20"
                                   dataType="SameLen|Number|CompareBigNum|Limit"
                                   operator="GreaterThanEqual" to="d_startLogicalId" str1 ="d_startLogicalId" str2 ="d_endLogicalId"
                                   maxlength="20" msg="终止逻辑卡号应为16位-20位数字，输入位数与起始逻辑卡号相同,有效值大于或等于起始逻辑卡号" />
                        </td>
                        <td class="table_edit_tr_td_label">
                            <input type="radio"
                                   name="d_select" id="d_select" size="1"
                                   onClick="controlByRadioForNumForDistribute('detailOp', ['d_distributeQuantity', 'd_startLogicalId', 'd_endLogicalId', 'd_boxId', 'd_endBoxId']);"
                                   value="0" />
                            盒号起:
                        </td>
                        <td class="table_edit_tr_td_input">
                            <input type="text"
                                   name="d_boxId" id="d_boxId" size="12" min="14" max="14"
                                   require="false" maxlength="14" dataType="Number|LimitB"
                                   msg="始盒号为14位数字!" />
                        </td>
                        <td class="table_edit_tr_td_label">
                           盒号止:
                        </td>
                        <td class="table_edit_tr_td_input">
                            <input type="text"
                                   name="d_endBoxId" id="d_endBoxId" size="12" min="14" max="14"
                                   require="false" maxlength="14"
                                   dataType="Number|CompareNum|Limit"
                                   operator="GreaterThanEqual" to="d_boxId"
                                   msg="止盒号为14位整数且大于等于始盒号!" />
                        </td>
                    </tr>

                    <tr class="table_edit_tr">
                        <td class="table_edit_tr_td_label">
                            线路配票内容:
                        </td>
                        <td class="table_edit_tr_td_input" colspan="7">
                            <input type="text"
                                   name="d_content" id="d_content" size="95" dataType="NotEmpty"
                                   require="false" msg="线路配票内容不能为空！" />
                            <img src="./images/calendar.gif"
                                 name="d_content_sel" id="d_content_sel" width="15" height="15" border="0"
                                 style="block"  readOnly=true
                                 onClick="openWindowDialogForParamDis('detailOp', 'd_content', ['d_icMainType', 'd_icSubType', 'd_cardMoney', 'd_validDate', 'd_lineId', 'd_stationId', 'd_storageId'],
                                                 [true, true, true, false, false, false, false], ['票卡主类型', '票卡子类型', '面值', '', '', '', ''],
                                                 ['d_lineId', 'd_validDate', 'd_stationId'], ['卡线路', '有效期', '卡车站'],
                                                 'ticketStorageOutDistributeManage',${ModuleID}, 600, 600);" />
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
            <c:set var="addClickMethod" scope="request" value="setUpdatePrimaryKey('detailOp','d_billNo#d_waterNo');enablePrimaryKeys('detailOp');disableFormControlsById(['d_distributeLineId','d_reasonId'],false);"/>
            <c:set var="clickMethod" scope="request" value="btnClick('detailOp','clearStart','detail');disableFormControls('detailOp',['d_waterNo','d_billNo'],true);"/>
            <c:set var="addAfterMethod" scope="request" value="showControlByReasonId('d_reasonId',
                   ['d_distributeLineId', 'd_distributeQuantity', 'd_startLogicalId', 'd_endLogicalId', 'd_boxId', 'd_endBoxId'],
                   ['d_content', 'd_content_sel']);controlLineStationForDistribute('detailOp',['d_distributeLineId']);disableFormControlsById(['d_mode','d_lineId','d_stationId','d_exitLineId','d_exitStationId'],'true');controlByCardTypeAndAreaForDistributeModify();controlLineStationByModeById('detailOp', 'd_mode', 'd_lineId', 'd_stationId', 'd_exitLineId', 'd_exitStationId');controlByCardTypeAndAreaForMode();clearNumForDistributeLine('detailOp');controlDistributeLineByReason('detailOp','d_reasonId','d_distributeLineId');"/>
            <c:set var="addAfterClickModifyMethod" scope="request" value="disableFormControlsById(['d_content','d_content_sel','d_reasonId'],true);controlByCardTypeAndAreaForDistributeModify();disableFormControlsById(['d_mode','d_lineId','d_stationId','d_exitLineId','d_exitStationId'],'true');controlByCardTypeAndAreaForMode();controlDistributeLineByReason('detailOp','d_reasonId','d_distributeLineId');disableFormControls('detailOp',['d_endBoxId'],true);"/>
            <!--exportBillDetail('detailOp','rpt_ic_rct_bl_out_distribute_plan',['p_bill_no'],['queryCondition'],800,500);-->
            <c:set var="exportBillAfter" scope="request" value="exportBillDetail('detailOp','w_rpt_ic_rct_bl_out_distribute_plan',['p_bill_no'],['queryCondition'],800,500)"/>


            <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all" />
            <!-- <input type="hidden" name="validDays" id="validDays" value="{/Service/Result/validDays}" /> -->
            <input type="hidden" name="validDays" id="vaildDays" value="${vaildDays}" />
            <br/>
        </FORM>


        <!-- 状态栏 通用模板 -->

        <c:import url="/jsp/common/common_template.jsp?template_name=common_status_table" />
    </body>
</html>