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
        <title>生产订单</title>


        <script language="javascript" type="text/javascript" charset="utf-8" src="js/common_form.js"></script>
        <script language="javascript" type="text/javascript" charset="utf-8" src="js/common_form_extend.js"></script>
        <script language="javascript" type="text/javascript" charset="utf-8" src="js/Validator.js"></script>
        <script language="JavaScript" type="text/javascript" charset="utf-8" src="js/CalendarPop.js"></script>
        <script language="JavaScript" type="text/javascript" charset="utf-8" src="js/icProduceOut.js"></script>
        <script language="JavaScript" type="text/javascript" charset="utf-8" src="js/icStorageProductBill.js"></script>
        <link rel="stylesheet" type="text/css" href="css/frame/simple.css" title="Style"/>
    </head>

    <body onload="
            initDocument('detailOp', 'detail');
            setListViewDefaultValue('detailOp', 'clearStart');

            setTableRowBackgroundBlock('DataTable')">

        <!--<body >-->
        <table  class="table_title">
            <tr align="center" class="trTitle">
                <td colspan="4">生产订单
                </td>
            </tr>
        </table>



        <!-- 表头 通用模板 -->
        <c:set var="pTitleName" scope="request" value="列表"/>
        <c:set var="pTitleWidth" scope="request" value="50"/>
        <c:import url="/jsp/common/common_template.jsp?template_name=common_table_title" />
        <div id="clearStartBlock" class="divForTableBlock" >
            <div id="clearStartHead" class="divForTableBlockHead" style="width :2000px">
                <table class="table_list_block" id="DataTableHead" >

                    <!--说明：列的序号从0开始 isDigit：false 表示按字符串排序 ：true表示按数值排序 sortedby：asc表示按升序 dec表示按降序 -->
                    <tr  class="table_list_tr_head_block" id="ignore">
                        	
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="1"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 90px">订单编号</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="2"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >票卡主类型</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="3"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >票卡子类型</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="4"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >记名卡标志</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=true index="5"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >有效期</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="6"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >面值(分/次)</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="7"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >押金(分)</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="8"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >订单数量</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="9"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >开始序列号</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="10" sortedby="asc"  onclick="sortForTableBlock('clearStart');">终止序列号</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="11" sortedby="asc"  onclick="sortForTableBlock('clearStart');" style="width: 130px">订单生成时间</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="12" sortedby="asc"  onclick="sortForTableBlock('clearStart');">制单员</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="13" sortedby="asc"  onclick="sortForTableBlock('clearStart');">生产数量</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="14" sortedby="asc"  onclick="sortForTableBlock('clearStart');">ES操作员</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="15" sortedby="asc"  onclick="sortForTableBlock('clearStart');">执行标志</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="16" sortedby="asc"  onclick="sortForTableBlock('clearStart');" style="width:100px">乘次票限制模式</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="17" sortedby="asc"  onclick="sortForTableBlock('clearStart');">进站线路</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="18" sortedby="asc"  onclick="sortForTableBlock('clearStart');">进站车站</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="19" sortedby="asc"  onclick="sortForTableBlock('clearStart');">出站线路</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="20" sortedby="asc"  onclick="sortForTableBlock('clearStart');">出站车站</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="21" sortedby="asc"  onclick="sortForTableBlock('clearStart');">生效日期</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="22" sortedby="asc"  onclick="sortForTableBlock('clearStart');" style="width:100px">票卡有效天数</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="23" sortedby="asc"  onclick="sortForTableBlock('clearStart');" style="width:100px">充值上限(分/次)</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="24" sortedby="asc"  onclick="sortForTableBlock('clearStart');" style="width:100px">发售激活标志</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="25" sortedby="asc"  onclick="sortForTableBlock('clearStart');">测试标志</td>


                    </tr>


                </table>

            </div>
            
            <div id="clearStart"  class="divForTableBlockData" style="width :2000px">
                <table class="table_list_block" id="DataTable" >

                    <c:forEach items="${ResultSet}" var="rs">
                        <!--class="listTableData" -->
                        <!--
                        setSelectValuesByRow('detailOp', 'd_stationID', 'commonVariable');
                        setPageControl('detailOp');
                        -->
                        <tr class="table_list_tr_data"  onMouseOver="overResultRow('detailOp', this);" 
                            onMouseOut="outResultRow('detailOp', this);" 
                            onclick="clickResultRow('detailOp', this, 'detail');
                                    " 
                            id="${rs.bill_no}#${rs.order_no}" >

                           
                            <td  id="orderNo" class="table_list_tr_col_data_block" style="width: 90px">

                                 ${rs.order_no}
                            </td>
                            <td  id="cardMainCode" class="table_list_tr_col_data_block" >
                                ${rs.card_main_code_name}

                            </td>
                            <td  id="cardSubCode" class="table_list_tr_col_data_block">
                                ${rs.card_sub_code_name}
                            </td>
                            <td  id="cardType" class="table_list_tr_col_data_block">
                                ${rs.card_type_name}
                            </td>
                            <td  id="cardPerAva" class="table_list_tr_col_data_block">
                                ${rs.card_per_ava}
                            </td>
                            <td  id="cardMon" class="table_list_tr_col_data_block">
                                ${rs.card_mon}
                            </td>
                            <td  id="burseUplimit" class="table_list_tr_col_data_block">
                                ${rs.burse_uplimit}
                            </td>
                            <td  id="proNum" class="table_list_tr_col_data_block">
                                ${rs.pro_num}
                            </td>
                            <td  id="bSerialNo" class="table_list_tr_col_data_block">
                                ${rs.b_serial_no}
                            </td>
                            <td  id="eSerialNo" class="table_list_tr_col_data_block">
                                ${rs.e_serial_no}
                            </td>
                            <td  id="genTime" class="table_list_tr_col_data_block" style="width: 130px">
                                ${rs.gen_time}
                            </td>
                            <td  id="operId" class="table_list_tr_col_data_block">
                                ${rs.oper_id}
                            </td>
                            <td  id="finiPronum" class="table_list_tr_col_data_block">
                                ${rs.fini_pronum}
                            </td>
                            <td  id="esOperatorId" class="table_list_tr_col_data_block">
                                ${rs.es_operator_id}
                            </td>
                            <td  id="hdlFlag" class="table_list_tr_col_data_block">
                                ${rs.hdl_flag_name}
                            </td>
                            <td  id="mode" class="table_list_tr_col_data_block" style="width:100px">
                                ${rs.model_name}
                            </td>
                            <td  id="lineCode" class="table_list_tr_col_data_block">
                                ${rs.line_code_name}
                            </td>
                            <td  id="stationCode" class="table_list_tr_col_data_block">
                                ${rs.station_code_name}
                            </td>
                            <td  id="exitLineCode" class="table_list_tr_col_data_block">
                                ${rs.exit_line_code_name}
                            </td>
                            <td  id="exitStationCode" class="table_list_tr_col_data_block">
                                ${rs.exit_station_code_name}
                            </td>
                             <td  id="cardStartAva" class="table_list_tr_col_data_block">
                                ${rs.cardstartava}
                            </td>
                            <td  id="cardStartAva" class="table_list_tr_col_data_block" style="width:100px">
                                ${rs.card_ava_days}
                            </td>

                            <td  id="maxRechargeVal" class="table_list_tr_col_data_block" style="width:100px">
                                ${rs.max_recharge_val}
                            </td>
                            <td  id="saleFlag" class="table_list_tr_col_data_block" style="width:100px">
                                ${rs.sale_flag_name}
                            </td>
                            <td  id="testFlag" class="table_list_tr_col_data_block">
                                ${rs.test_flag_name}
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

        <FORM method="post" name="detailOp" id="detailOp" action="ticketStorageOutProduceManage" >

            <c:set var="divideShow" scope="request" value="0"/>
            <c:import  url="/jsp/common/common_template_web_variable.jsp?template_name=common_web_variable" />

            <div id="detail"  class="divForTableDataDetail" >
                <table  class="table_edit_detail">
                    <tr></tr>


                </table>
            </div>






            <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all_init" />

            
            <c:set var="print" scope="request" value="1"/>
            <c:set var="export" scope="request" value="1"/>
           


            <c:set var="clickMethod" scope="request" value="btnClick('detailOp','clearStart','detail','','clearStartHead');"/>
            <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all" />
            <br/>
        </FORM>
        <!--
        <c:set var="addQueryMethod" scope="request" value="setLineCardNames('detailOp','d_storage','d_zone','commonVariable','','','');"/>
        <c:set var="addClickMethod" scope="request" value="setUpdatePrimaryKey('detailOp','d_lineID#d_stationID#d_deviceType#d_deviceID#Version');"/>
        -->

        <!-- 状态栏 通用模板 -->

        <c:import url="/jsp/common/common_template.jsp?template_name=common_status_table" />
    </body>
</html>
