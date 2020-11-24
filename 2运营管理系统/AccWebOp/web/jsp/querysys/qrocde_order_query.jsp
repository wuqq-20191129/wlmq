<%--
    Document   : QrcodeOrderQuery
    Created on : 2019-7-11
    Author     : wuqq
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>二维码取票订单查询</title>
        <script language="javascript" type="text/javascript" charset="utf-8" src="js/common_form.js"></script>
        <script language="javascript" type="text/javascript" charset="utf-8" src="js/common_form_extend.js"></script>
        <script language="javascript" type="text/javascript" charset="utf-8" src="js/Validator.js"></script>
        <script language="JavaScript" src="js/CalendarPop.js"></script>
        <link rel="stylesheet" type="text/css" href="css/frame/simple.css" title="Style"/>
    </head>

    <body onload="preLoadVal('q_beginTime', 'q_endTime');
            initDocument('queryOp', 'detail');
            initDocument('detailOp', 'detail');
            setControlsDefaultValue('queryOp');
            setPrimaryKeys('detailOp', 'd_orderNo#d_ticket_statusString#d_lock_dev#d_insert_date');
            setQueryControlsDefaultValue('queryOp', 'detailOp');
            setListViewDefaultValue('detailOp', 'clearStart');
            setPageControl('detailOp');
            setTableRowBackgroundBlock('DataTable')">
        <table  class="table_title">
            <tr align="center" class="trTitle">
                <td colspan="4">
                    二维码取票订单查询
                </td>
            </tr>
        </table>


        <!-- 表头 通用模板 -->

        <c:set var="pTitleName" scope="request" value="查询"/>
        <c:set var="pTitleWidth" scope="request" value="50"/>
        <c:import  url="/jsp/common/common_template.jsp?template_name=common_table_title" />

        <form method="post" name="queryOp" id="queryOp" action="QrcodeOrderQuery">
            <!-- 页面用到的变量 通用模板 -->
            <c:set var="divideShow" scope="request" value="1"/>
            <c:import  url="/jsp/common/common_template_web_variable.jsp?template_name=common_web_variable" />
            <table  class="table_edit">
                <tr class="table_edit_tr">
                    <td class="table_edit_tr_td_label">订单号:</td>
                    <td class="table_edit_tr_td_input">
                        <input type="text" name="q_orderNo" id="q_orderNo" size="16" minlength="14" maxlength="14"  require="false" dataType="Integer|LimitB" max="14" min="14" msg="订单号为14位数字" />
                    </td>
                    <td class="table_edit_tr_td_label" >订单生成开始日期:</td>
                    <td class="table_edit_tr_td_input">
                        <input type="text" name="q_beginTime" id="q_beginTime" size="10"  dataType="ICCSDate"  msg="开始日期不为空,格式为(yyyy-mm-dd)!" />
                        <a href="javascript:openCalenderDialogByID('q_beginTime','false');">
                            <img src="./images/calendar.gif" width="15" height="15" border="0" style="block"/>
                        </a>
                    </td>
                    <td class="table_edit_tr_td_label" >订单生成结束日期:</td>
                    <td class="table_edit_tr_td_input">
                        <input type="text" name="q_endTime" id="q_endTime" size="10"  dataType="ICCSDate|ThanDate" to="q_beginTime"  msg="结束日期不为空且必须大于开始日期,格式为(yyyy-mm-dd)!" />
                        <a href="javascript:openCalenderDialogByID('q_endTime','false');">
                            <img src="./images/calendar.gif" width="15" height="15" border="0" style="block"/>
                        </a>
                    </td>

                </tr>
                <tr class="table_edit_tr">
                    <td class="table_edit_tr_td_label">订单支付状态:</td>
                    <td class="table_edit_tr_td_input">
                        <select id = "q_orderStatus" name = "q_orderStatus">
                            <option value="">=请选择=</option>
                            <option value="0">未支付</option>
                            <option value="1">已支付</option>
                        </select>
                    </td>
                    <td class="table_edit_tr_td_label">订单状态:</td>
                    <td class="table_edit_tr_td_input">
                        <select id = "q_ticeketStatus" name = "q_ticeketStatus">
                            <option value="">=请选择=</option>
                            <option value="00">订单未执行</option>
                            <option value="01">订单部分完成</option>
                            <option value="02">订单取消</option>
                            <option value="80">未购票或不存在</option>
                            <option value="81">订单已完成(已取票)</option>
                            <option value="82">二维码已过有效期</option>
                            <option value="83">购票、取票始发站不一致</option>
                            <option value="84">订单锁定</option>
                        </select>
                    </td>

                    <td class="table_edit_tr_td_label" rowspan="2">
                        <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all_init" />
                        <c:set var="query" scope="request" value="1"/>
                        <c:set var="addClickMethod" scope="request" value="setControlNames('queryOp','q_orderNo#q_orderStatus#q_beginTime#q_endTime#q_ticeketStatus');"/>
                        <c:set var="clickMethod" scope="request" value="btnClick('queryOp','clearStart','detail');"/>
                        <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all" />
                    </td>
                </tr>
            </table>
        </form>
        <!-- 状态栏 通用模板 -->
        <c:import url="/jsp/common/common_template.jsp?template_name=common_status_table" />
        <!-- 表头 通用模板 -->
        <c:set var="pTitleName" scope="request" value="列表"/>
        <c:set var="pTitleWidth" scope="request" value="50"/>
        <c:import url="/jsp/common/common_template.jsp?template_name=common_table_title" />
        <div id="clearStartBlock" class="divForTableBlock" >
            <div id="clearStartHead" class="divForTableBlockHead" style="width: 3000px">
                <table class="table_list_block" id="DataTableHead" >
                    <!--说明：列的序号从0开始 isDigit：false 表示按字符串排序 ：true表示按数值排序 sortedby：asc表示按升序 dec表示按降序 -->
                    <tr  class="table_list_tr_head_block" id="ignore" >
                        <td   class="table_list_tr_col_head_block">
                            <input type="checkbox" name="rectNoAll" id="rectNoAll" 
                                   onclick="selectAllRecord('detailOp', 'rectNoAll', 'rectNo', 'clearStart', 0);
                                           controlsByFlag('detailOp', ['modify']);
                                           controlsDisableForMulRecords('detailOp', ['modify']);
                                           controlsByFlagAndMul('detailOp', ['audit']);
                                           setPageControl('detailOp');"/>
                        </td>	
                        <td id="orderTd"  style="width: 100px"  class="table_list_tr_col_head_block"  isDigit=true index="0"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >流水号</td>
                        <td id="orderTd"  style="width: 100px"  class="table_list_tr_col_head_block"  isDigit=true index="1"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >订单号</td>
                        <td id="orderTd"  style="width: 90px"  class="table_list_tr_col_head_block"  isDigit=false index="2"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >手机号</td>
                        <td id="orderTd"  style="width: 120px"  class="table_list_tr_col_head_block"  isDigit=true index="3"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >订单支付状态</td>
                        <td id="orderTd"  style="width: 120px"  class="table_list_tr_col_head_block"  isDigit=false index="4"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >订单状态</td>
                        <td id="orderTd"  style="width: 100px"  class="table_list_tr_col_head_block"  isDigit=true index="5"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >始发站</td>
                        <td id="orderTd"  style="width: 100px"  class="table_list_tr_col_head_block"  isDigit=false index="6"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >终点站</td>
                        <td id="orderTd"  style="width: 120px"  class="table_list_tr_col_head_block"  isDigit=false index="7"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >交易时间</td>
                        <td id="orderTd"  style="width: 120px"  class="table_list_tr_col_head_block"  isDigit=false index="8"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >更新时间</td>
                        <td id="orderTd"  style="width: 120px"  class="table_list_tr_col_head_block"  isDigit=true index="9"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >订单生成时间</td>
                        <td id="orderTd"  style="width: 160px"  class="table_list_tr_col_head_block"  isDigit=false index="10"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >已发售单程票单价(分)</td>
                        <td id="orderTd"  style="width: 140px"  class="table_list_tr_col_head_block"  isDigit=false index="11"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >已发售单程票数量</td>
                        <td id="orderTd"  style="width: 160px"  class="table_list_tr_col_head_block"  isDigit=true index="12"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >已发售单程票总价(分)</td>
                        <td id="orderTd"  style="width: 150px"  class="table_list_tr_col_head_block"  isDigit=true index="13"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >发售单程票单价(分)</td>
                        <td id="orderTd"  style="width: 120px"  class="table_list_tr_col_head_block"  isDigit=false index="14"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >发售单程票数量</td>
                        <td id="orderTd"  style="width: 150px"  class="table_list_tr_col_head_block"  isDigit=false index="15"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >发售单程票总价(分)</td>
                        <td id="orderTd"  style="width: 120px"  class="table_list_tr_col_head_block"  isDigit=false index="16"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >二维码有效时间</td>
                        <td id="orderTd"  style="width: 180px"  class="table_list_tr_col_head_block"  isDigit=false index="17"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >取票码(二维码加密前)</td>
                        <td id="orderTd"  style="width: 120px"  class="table_list_tr_col_head_block"  isDigit=false index="18"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >锁定终端编号</td>
                        <td id="orderTd"  style="width: 120px"  class="table_list_tr_col_head_block"  isDigit=false index="19"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >备注</td>

                    </tr>
                </table>
            </div>
            <div id="clearStart"  class="divForTableBlockData" style="width: 3000px">
                <table class="table_list_block" id="DataTable" >
                    <c:forEach items="${ResultSet}" var="rs">
                        <tr class="table_list_tr_data"  onMouseOver="overResultRow('detailOp', this);"
                            onMouseOut="outResultRow('detailOp', this);"
                            onclick="

                                    clickResultRow('detailOp', this, 'detail');
                                    controlsByFlagAndMul('detailOp', ['audit','modify']);
                                    setPageControl('detailOp');"
                            id="${rs.waterNo}#${rs.orderNo}#${rs.ticket_statusString}#${rs.lock_dev}#${rs.remark}"
                            flag="${rs.statusString}">

                            <td id="rectNo1" class="table_list_tr_col_data_block">
                                <input type="checkbox" name="rectNo" id="rectNo" 
                                       onclick="unSelectAllRecord('detailOp', 'rectNoAll', 'rectNo');
                                           controlsByFlagAndMul('detailOp', ['audit','modify']);
                                       "
                                       value="${rs.orderNo}" flag="${rs.statusString}">
                                </input>
                            </td>

                            <td  id="waterNo" class="table_list_tr_col_data_block" style="width: 100px">
                                ${rs.waterNo}
                            </td>
                            <td  id="orderNo" class="table_list_tr_col_data_block" style="width: 100px">
                                ${rs.orderNo}
                            </td>
                            <td  id="phoneNo" class="table_list_tr_col_data_block" style="width: 90px">
                                ${rs.phoneNo}
                            </td>
                            <td  id="status" class="table_list_tr_col_data_block" style="width: 120px">
                                ${rs.statusString}
                            </td>
                            <td  id="ticket_statusString" class="table_list_tr_col_data_block" style="width: 120px">
                                ${rs.ticket_statusString}
                            </td>
                            <td  id="start_station_text" class="table_list_tr_col_data_block" style="width: 100px">
                                ${rs.start_station_text}
                            </td>
                            <td  id="end_station_text" class="table_list_tr_col_data_block" style="width: 100px">
                                ${rs.end_station_text}
                            </td>
                            <td  id="deal_time" class="table_list_tr_col_data_block" style="width: 120px">
                                ${rs.deal_time}
                            </td>
                            <td  id="update_date" class="table_list_tr_col_data_block" style="width: 120px">
                                ${rs.update_date}
                            </td>
                            <td  id="insert_date" class="table_list_tr_col_data_block" style="width: 120px">
                                ${rs.insert_date}
                            </td>
                            <td  id="sale_fee" class="table_list_tr_col_data_block" style="width: 160px">
                                ${rs.sale_fee}
                            </td>
                            <td  id="sale_times" class="table_list_tr_col_data_block" style="width: 140px">
                                ${rs.sale_times}
                            </td>
                            <td  id="deal_fee" class="table_list_tr_col_data_block" style="width: 160px">
                                ${rs.deal_fee}
                            </td>
                            <td  id="sale_fee_total" class="table_list_tr_col_data_block" style="width: 150px">
                                ${rs.sale_fee_total}
                            </td>
                            <td  id="sale_times_total" class="table_list_tr_col_data_block" style="width: 120px">
                                ${rs.sale_times_total}
                            </td>
                            <td  id="deal_fee_total" class="table_list_tr_col_data_block" style="width: 150px">
                                ${rs.deal_fee_total}
                            </td>
                            <td  id="valid_time" class="table_list_tr_col_data_block" style="width: 120px">
                                ${rs.valid_time}
                            </td>
                            <td  id="tkcode" class="table_list_tr_col_data_block" style="width: 180px">
                                ${rs.tkcode}
                            </td>
                            <td  id="lock_dev" class="table_list_tr_col_data_block" style="width: 120px">
                                ${rs.lock_dev}
                            </td>
                            <td  id="remark" class="table_list_tr_col_data_block" style="width: 120px">
                                ${rs.remark}
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

        <FORM method="post" name="detailOp" id="detailOp" action="QrcodeOrderQuery" >

            <c:set var="divideShow" scope="request" value="1"/>
            <c:import  url="/jsp/common/common_template_web_variable.jsp?template_name=common_web_variable" />
            <!-- 表头 通用模板 -->
            <!--导出全部参数 -->
            <input type="hidden" name="expAllFields" id="d_expAllFields" value="getWaterNo,getOrderNo,getPhoneNo,getStatusString,getTicket_statusString,getStart_station_text,getEnd_station_text,getDeal_time,getUpdate_date,getInsert_date,getSale_fee,getSale_times,getDeal_fee,getSale_fee_total,getSale_times_total,getDeal_fee_total,getValid_time,getTkcode,getLock_dev,getRemark" />
            <input type="hidden" name="expFileName" id="d_expFileName" value="二维码取票订单.xlsx" />
            <input type="hidden" name="divId" id="d_divId" value="clearStartHead" />
            <input type="hidden" name="methodUrl" id="d_methodUrl" value="/QrcodeOrderQueryExportAll" />

            <div id="detail" name="detail" class="divForTableDataDetail" >
                <table  class="table_edit_detail">
                    <tr class="table_edit_tr">
                        <td class="table_edit_tr_td_label">单号:</td>
                        <td class="table_edit_tr_td_input">
                            <input type="text" name="d_orderNo" id="d_orderNo" size="20"  require="false" readOnly="true" />	
                        </td>

                        <td class="table_edit_tr_td_label">订单状态:</td>
                        <td class="table_edit_tr_td_input">

                            <select id = "d_ticket_statusString" name = "d_ticket_statusString">
                                <option value="">=请选择=</option>
                                <option value="00">订单未执行</option>
                                <option value="01">订单部分完成</option>
                                <option value="02">订单取消</option>
                                <option value="80">未购票或不存在</option>
                                <option value="81">订单已完成(已取票)</option>
                                <option value="82">二维码已过有效期</option>
                                <option value="83">购票、取票始发站不一致</option>
                                <option value="84">订单锁定</option>
                            </select>
                        </td>
                        
                        <td class="table_edit_tr_td_label">锁定终端编号:</td>
                        <td class="table_edit_tr_td_input">
                            <input type="text" name="d_lock_dev" id="d_lock_dev" size="20"  require="false" readOnly="true" />	
                        </td>
                    </tr>
                    <tr class="table_edit_tr">
                        <td class="table_edit_tr_td_label">订单生成时间:</td>
                        <td class="table_edit_tr_td_input">
                            <input type="text" name="d_insert_date" id="d_insert_date" size="20"  require="false" readOnly="true" />	
                        </td>

                        <td class="table_edit_tr_td_label">备注:</td>
                        <td class="table_edit_tr_td_input" colspan="3">

                            <select name="d_remark" id="d_remark">
                                <option value="">=请选择=</option>
                                <option value="0">手工处理为已出票</option>
                                <option value="1">手工处理为未出票</option>

                            </select>
                        </td>

                    </tr>




                </table>
            </div>


            <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all_init" />

            <c:set var="modify" scope="request" value="1"/>
            <c:set var="audit" scope="request" value="1"/>
            <c:set var="save" scope="request" value="1"/>
            <c:set var="cancle" scope="request" value="1"/>
            <c:set var="print" scope="request" value="1"/>
            <c:set var="export" scope="request" value="1"/>
            <c:set var="expAll" scope="request" value="1" />
            <c:set var="btNext" scope="request" value="1"/>
            <c:set var="btNextEnd" scope="request" value="1"/>
            <c:set var="btBack" scope="request" value="1"/>
            <c:set var="btBackEnd" scope="request" value="1"/>
            <c:set var="btGoPage" scope="request" value="1"/>

            <c:set var="clickMethod" scope="request" value="btnClick('detailOp','clearStart','detail','','clearStartHead');"/>
            <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all" />
            <br/>
        </FORM>

    </body>
</html>
