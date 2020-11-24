<%-- 
    Document   : card_ticket_attr
    Created on : 2017-6-11
    Author     : luck
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>乘次票属性</title>


        <script language="javascript" type="text/javascript" charset="utf-8" src="js/common_form.js"></script>
        <script language="javascript" type="text/javascript" charset="utf-8" src="js/Validator.js"></script>
        <script language="JavaScript" src="js/CalendarPop.js"></script>
        <link rel="stylesheet" type="text/css" href="css/frame/simple.css" title="Style"/>
    </head>
    <c:choose>
        <c:when test="${version.version_no == '0000000000'}">
            <body onload="initDocument('queryOp', 'detail');
                    initDocument('detailOp', 'detail');
                    setInvisable('detailOp', 'clone');
                    setPrimaryKeys('detailOp', 'd_cardMainID#d_cardSubID#d_packageID');
                    setControlsDefaultValue('queryOp');
                    setListViewDefaultValue('detailOp', 'clearStart');
                    setQueryControlsDefaultValue('queryOp', 'detailOp');
                    setPageControl('detailOp');
                    setTableRowBackgroundBlock('DataTable')">
            </c:when>
            <c:otherwise>
            <body onload="initDocument('queryOp', 'detail');
                    initDocument('detailOp', 'detail');
                    setInvisable('detailOp', 'add;modify;del;submit1');
                    setPrimaryKeys('detailOp', 'd_cardMainID#d_cardSubID#d_packageID');
                    setControlsDefaultValue('queryOp');
                    setListViewDefaultValue('detailOp', 'clearStart');
                    setQueryControlsDefaultValue('queryOp', 'detailOp');
                    setPageControl('detailOp');
                    setTableRowBackgroundBlock('DataTable')">
            </c:otherwise>
        </c:choose>
        <!--<body >-->
        <table  class="table_title">
            <tr align="center" class="trTitle">
                <td colspan="4">乘次票属性（
                    ${version.record_flag_name}：${version.version_no}
                    ）
                </td>
            </tr>
        </table>

        <c:set var="pTitleName" scope="request" value="版本信息"/>
        <c:set var="pTitleWidth" scope="request" value="50"/>
        <c:import  url="/jsp/common/common_template.jsp?template_name=common_table_title" />
        <table class="table_edit" >
            <tr class="table_edit_tr">
                <td class="table_edit_tr_td_label">生效起始时间:</td>
                <td class="table_edit_tr_td_input">
                    <input type="text" name="startDate" id="startDate" size="20" value="${version.begin_time}" readonly="true">
                    </input>
                </td>
                <td class="table_edit_tr_td_label">生效截至时间:</td>
                <td class="table_edit_tr_td_input">
                    <input type="text" name="endDate" id="endDate" size="20" value="${version.end_time}" readonly="true">
                    </input>
                </td>
            </tr>
        </table>


        <!-- 表头 通用模板 -->

        <c:set var="pTitleName" scope="request" value="查询"/>
        <c:set var="pTitleWidth" scope="request" value="50"/>
        <c:import  url="/jsp/common/common_template.jsp?template_name=common_table_title" />

        <form method="post" name="queryOp" id="queryOp" action="CardTicketAttr">
            <!-- 页面用到的变量 通用模板 -->

            <c:set var="divideShow" scope="request" value="1"/>
            <c:import  url="/jsp/common/common_template_web_variable.jsp?template_name=common_web_variable" />

            <table  class="table_edit">
                <tr class="table_edit_tr">
                    <td class="table_edit_tr_td_label">票卡主类型:</td>
                    <td class="table_edit_tr_td_input">
                        <select id="q_cardMainID" name="q_cardMainID" onchange="setSelectValues('queryOp', 'q_cardMainID', 'q_cardSubID', 'commonVariable');" >
                            <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_cardMain_cardTickttr" />
                        </select>
                    </td>
                    <td class="table_edit_tr_td_label">票卡子类型:</td>
                    <td class="table_edit_tr_td_input">
                        <select id="q_cardSubID" name="q_cardSubID" >
                            <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_cardSub_none" />
                        </select>
                        <c:set var="pVarName" scope="request" value="commonVariable"/>
                        <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_cardSub" />
                    </td>    
                    <td class="table_edit_tr_td_label" rowspan="2">

                        <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all_init" />
                        <c:set var="query" scope="request" value="1"/>
                        <c:set var="addClickMethod" scope="request" value="setControlNames('queryOp','q_cardMainID#q_cardSubID');setLineCardNames('queryOp','q_cardMainID','q_cardSubID','commonVariable','','','');"/>
                        <c:set var="clickMethod" scope="request" value="btnClick('queryOp','clearStart','detail');"/>
                        <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all" />
                    </td>
                </tr>
            </table>
            <c:import url="/jsp/common/common_template.jsp?template_name=version_eval" />

        </form>

        <!-- 状态栏 通用模板 -->

        <c:import url="/jsp/common/common_template.jsp?template_name=common_status_table" />    
            
        <!-- 表头 通用模板 -->
        <c:set var="pTitleName" scope="request" value="列表"/>
        <c:set var="pTitleWidth" scope="request" value="50"/>
        <c:import url="/jsp/common/common_template.jsp?template_name=common_table_title" />
        <div id="clearStartBlock" class="divForTableBlock" >
            <div id="clearStartHead" class="divForTableBlockHead" >
                <table class="table_list_block" id="DataTableHead" >

                    <!--说明：列的序号从0开始 isDigit：false 表示按字符串排序 ：true表示按数值排序 sortedby：asc表示按升序 dec表示按降序 -->
                    <tr  class="table_list_tr_head_block" id="ignore">
                        <td   class="table_list_tr_col_head_block">
                            <input type="checkbox" name="rectNoAll" id="rectNoAll"  onclick="selectAllRecord('detailOp', 'rectNoAll', 'rectNo', 'clearStart', 0);"/>
                        </td>	
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="1"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >票卡主类型</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="2"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 100px">票卡子类型</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="3"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >套餐ID</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=true index="4"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 80px">每次充值金额(单位:分)</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=true index="5"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 80px">每次充值乘次(单位:次)</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=true index="6"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >下月充值起始日</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=true index="7"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >本月充值截止日</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=true index="8"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 80px">出站超时罚金（按次）</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=true index="9"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 80px">出站超时罚金（金额）</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=true index="10" sortedby="asc"  onclick="sortForTableBlock('clearStart');"style="width: 80px">进站超时罚金（按次）</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=true index="11" sortedby="asc"  onclick="sortForTableBlock('clearStart');"style="width: 80px">进站超时罚金（金额）</td>

                    </tr>


                </table>

            </div>
            <!-- <DIV id="clearStart" align="center" style="position:relative; left:20; width:95%; height:30%;min-height: 200px;max-height: 250px; layer-background-color:#99CC99; border:thin outset #ffffff; z-index:1; overflow: scroll;">-->
            <div id="clearStart"  class="divForTableBlockData" >
                <table class="table_list_block" id="DataTable">

                    <c:forEach items="${ResultSet}" var="rs">
                        <!--class="listTableData" -->
                        <tr class="table_list_tr_data"  onMouseOver="overResultRow('detailOp', this);" 
                            onMouseOut="outResultRow('detailOp', this);" 
                            onclick="setSelectValuesByRow('detailOp', 'd_cardSubID', 'commonVariable');clickResultRow('detailOp', this, 'detail');setPageControl('detailOp');" 
                            id="${rs.card_main_id}#${rs.card_sub_id}#${rs.package_id}#${rs.version_no}#${rs.record_flag}">

                            <td id="rectNo1" class="table_list_tr_col_data_block">
                                <input type="checkbox" name="rectNo" onclick="unSelectAllRecord('detailOp', 'rectNoAll', 'rectNo');"
                                       value="${rs.card_main_id}#${rs.card_sub_id}#${rs.package_id}">

                                </input>
                            </td>
                            <td  id="cardMainID" class="table_list_tr_col_data_block">
                                ${rs.card_main_name}
                            </td>
                            <td  id="cardSubID" class="table_list_tr_col_data_block" style="width: 100px">
                                ${rs.card_sub_name}
                            </td>
                            <td  id="packageID" class="table_list_tr_col_data_block">
                                ${rs.package_id}
                            </td>
                            <td  id="onceChargeMoney" class="table_list_tr_col_data_block" style="width: 80px">
                                ${rs.once_charge_money}
                            </td>
                            <td  id="onceChargeCount" class="table_list_tr_col_data_block" style="width: 80px">
                                ${rs.once_charge_count}
                            </td>
                            <td  id="addBeginDay" class="table_list_tr_col_data_block">
                                ${rs.add_begin_day}
                            </td>
                            <td  id="addValidDay" class="table_list_tr_col_data_block">
                                ${rs.add_valid_day}
                            </td>
                            <td  id="exitTimeoutCount" class="table_list_tr_col_data_block" style="width: 80px">
                                ${rs.exit_timeout_count}
                            </td>
                            <td  id="exitTimeoutMoney" class="table_list_tr_col_data_block" style="width: 80px">
                                ${rs.exit_timeout_money}
                            </td>
                            <td  id="entryTimeoutCount" class="table_list_tr_col_data_block" style="width: 80px">
                                ${rs.entry_timeout_count}
                            </td>
                            <td  id="entryTimeoutMoney" class="table_list_tr_col_data_block" style="width: 80px">
                                ${rs.entry_timeout_money}
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

        <FORM method="post" name="detailOp" id="detailOp" action="CardTicketAttr" >

            <c:set var="divideShow" scope="request" value="1"/>
            <c:import  url="/jsp/common/common_template_web_variable.jsp?template_name=common_web_variable" />

            <!--导出全部参数 -->
            <input type="hidden" name="expAllFields" id="d_expAllFields" value="CARD_MAIN_NAME,CARD_SUB_NAME,PACKAGE_ID,ONCE_CHARGE_MONEY,ONCE_CHARGE_COUNT,ADD_BEGIN_DAY,ADD_VALID_DAY,EXIT_TIMEOUT_COUNT,EXIT_TIMEOUT_MONEY,ENTRY_TIMEOUT_COUNT,ENTRY_TIMEOUT_MONEY" />
            <input type="hidden" name="expFileName" id="d_expFileName" value="票卡子类型.xlsx" />
            <input type="hidden" name="divId" id="d_divId" value="clearStartHead" />
            <input type="hidden" name="methodUrl" id="d_methodUrl" value="/CardTicketAttrExportAll" />

            <!--<DIV id="detail" align="center" class="divForTableData" style="position:relative; left:20; width:95%; height:10; layer-background-color:#99CC99; border:thin outset #ffffff; z-index:1;">-->
            <DIV id="detail"  class="divForTableDataDetail" >
                <table  class="table_edit_detail">
                    <tr class="table_edit_tr">
                        <td class="table_edit_tr_td_label">票卡主类型: </td>
                        <td class="table_edit_tr_td_input">
                            <select  name="d_cardMainID"  id="d_cardMainID" onChange="setSelectValues('detailOp', 'd_cardMainID', 'd_cardSubID', 'commonVariable');" require="true" dataType="LimitB" min="1" msg="没有选择票卡主类型">
                                <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_cardMain_cardTickttr" />
                            </select>	
                        </td>
                        <td class="table_edit_tr_td_label">票卡子类型:</td>
                        <td class="table_edit_tr_td_input">
                            <select id="d_cardSubID" name="d_cardSubID" require="true" dataType="LimitB" min="1" msg="没有选择票卡子类型">
                                <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_cardSub_none" />

                            </select>
                            <c:set var="pVarName" scope="request" value="commonVariable"/>
                            <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_cardSub" />
                        </td>
                        <td class="table_edit_tr_td_label">套餐ID:</td>
                        <td class="table_edit_tr_td_input">
                            <input type="text" name="d_packageID" id="d_packageID" size="10" maxLength="4" dataType="integer|LimitB|NotEmpty" min="4" max="4" msg="套餐ID应为4位数字！"/>
                        </td>
                    </tr>

                    <tr class="table_edit_tr">
                        <td class="table_edit_tr_td_label">每次充值金额(单位:分)</td>
                        <td class="table_edit_tr_td_input">
                            <input type="text" name="d_onceChargeMoney" id="d_onceChargeMoney" size="10" require="true" maxLength="6" dataType="integer" msg="每次充值金额(单位:分)应为正整数"/>
                        </td>
                        <td class="table_edit_tr_td_label">每次充值乘次(单位:次)</td>
                        <td class="table_edit_tr_td_input">
                            <input type="text" name="d_onceChargeCount" id="d_onceChargeCount" size="10" require="true" maxLength="4" dataType="integer"  msg="每次充值乘次(单位:次)应为正整数"/>
                        </td>
                        <td class="table_edit_tr_td_label">下月充值起始日</td>
                        <td class="table_edit_tr_td_input">
                            <input type="text" name="d_addBeginDay"  id="d_addBeginDay" size="10" require="true" maxlength="2" dataType="Range|integer|CompareNum" to="d_addValidDay" operator="GreaterThan" min="0" max="31"  msg="下月充值起始日应为0－31的整数且大于本月充值截止日"/>
                        </td>
                    </tr>
                    <tr class="table_edit_tr">
                        <td class="table_edit_tr_td_label">本月充值截止日:</td>
                        <td class="table_edit_tr_td_input">
                            <input type="text" name="d_addValidDay" id="d_addValidDay" size="10" maxLength="2" require="true" dataType="Range|integer" min="0" max="31" msg="本月充值截至日应为0－31的整数integer"/>
                        </td>
                        <td class="table_edit_tr_td_label">出站超时罚金（次）:</td>
                        <td class="table_edit_tr_td_input">
                            <input type="text" name="d_exitTimeoutCount" id="d_exitTimeoutCount" size="10" maxLength="2" require="true" dataType="integer"  msg="出站超时罚金（次）应为正整数" />
                        </td>
                        <td class="table_edit_tr_td_label">出站超时罚金（分）:</td>
                        <td class="table_edit_tr_td_input">
                            <input type="text" name="d_exitTimeoutMoney" id="d_exitTimeoutMoney" size="10" maxLength="4" require="true" dataType="integer"   msg="出站超时罚金（分）应为正整数" />
                        </td>
                    </tr>
                    <tr class="table_edit_tr">

                        <td class="table_edit_tr_td_label">进站超时罚金（次）:</td>
                        <td class="table_edit_tr_td_input">
                            <input type="text" name="d_entryTimeoutCount" id="d_entryTimeoutCount" size="10" require="true"  maxLength="2"  dataType="integer" value=""  msg="进站超时罚金（次）应为正整数" />
                        </td>

                        <td class="table_edit_tr_td_label">进站超时罚金（分）:</td>
                        <td class="table_edit_tr_td_input">
                            <input type="text" name="d_entryTimeoutMoney" id="d_entryTimeoutMoney" size="10" require="true"  maxLength="4"  dataType="integer" value=""  msg="进站超时罚金（分）应为正整数" />
                        </td>
                    </tr>



                </table>
            </DIV>

            <c:import url="/jsp/common/common_template.jsp?template_name=version_eval" />




            <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all_init" />
            <c:set var="add" scope="request" value="1"/>
            <c:set var="del" scope="request" value="1"/>
            <c:set var="modify" scope="request" value="1"/>
            <c:set var="save" scope="request" value="1"/>
            <c:set var="cancle" scope="request" value="1"/>
            <c:set var="clone" scope="request" value="1"/>
            <c:set var="submit" scope="request" value="1"/>
            <c:set var="print" scope="request" value="1"/>
            <c:set var="export" scope="request" value="1"/>
            <c:set var="expAll" scope="request" value="1" />
            <c:set var="btNext" scope="request" value="1"/>
            <c:set var="btNextEnd" scope="request" value="1"/>
            <c:set var="btBack" scope="request" value="1"/>
            <c:set var="btBackEnd" scope="request" value="1"/>
            <c:set var="btGoPage" scope="request" value="1"/>


            <c:set var="addQueryMethod" scope="request" value="setLineCardNames('detailOp','q_cardMainID','q_cardSubID','commonVariable','','','');"/>
            <c:set var="addClickMethod" scope="request" value="setUpdatePrimaryKey('detailOp','d_lineID#d_stationID#d_deviceType#d_deviceID#Version');"/>
            <c:set var="clickMethod" scope="request" value="btnClick('detailOp','clearStart','detail','','clearStartHead');"/>
            <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all" />
            <br/>
        </FORM>

       
    </body>
</html>
