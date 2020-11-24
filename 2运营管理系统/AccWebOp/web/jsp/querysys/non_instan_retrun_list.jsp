<%-- 
    Document   : non_instan_retrun_list
    Created on : 2017-6-15
    Author     : luck
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>非即时退款查询</title>


        <script language="javascript" type="text/javascript" charset="utf-8" src="js/common_form.js"></script>
        <script language="javascript" type="text/javascript" charset="utf-8" src="js/Validator.js"></script>
        <script language="JavaScript" src="js/CalendarPop.js"></script>
        <link rel="stylesheet" type="text/css" href="css/frame/simple.css" title="Style"/>
    </head>

    <script language="javascript">

        function selCheck() {
            var checkBtn = document.getElementById('balaCheck');
            if (checkBtn.checked === true) {
                document.getElementById('q_rtnOper').disabled = false;
                document.getElementById('q_rtnBala').disabled = false;
            } else {
                document.getElementById('q_rtnOper').disabled = true;
                document.getElementById('q_rtnBala').disabled = true;
                document.getElementById('q_rtnBala').value = "0";
            }
        }

        function formInit() {

            if (document.getElementById('balaCheckFlag').value === "true") {
                document.getElementById('balaCheck').value = "1";
                document.getElementById('balaCheck').checked = true;
            }
            if (document.getElementById('balaCheck').checked) {
                document.getElementById('q_rtnOper').disabled = false;
                document.getElementById('q_rtnBala').disabled = false;
            }
            if (document.getElementById('q_rtnBala').value === 'null')
                document.getElementById('q_rtnBala').value = "0";
        }
    </script>


    <body onload="preLoadVal('q_beginTime', 'q_endTime');
            initDocument('queryOp', 'detail');
            initDocument('detailOp', 'detail');
            setControlsDefaultValue('queryOp');
            setQueryControlsDefaultValue('queryOp', 'detailOp');
            setListViewDefaultValue('detailOp', 'clearStart');
            setPageControl('detailOp');
            setTableRowBackgroundBlock('DataTable');
            formInit()">
        <table  class="table_title">
            <tr align="center" class="trTitle">
                <td colspan="4">非即时退款查询
                </td>
            </tr>
        </table>

        <!-- 表头 通用模板 -->
        <c:set var="pTitleName" scope="request" value="查询"/>
        <c:set var="pTitleWidth" scope="request" value="50"/>
        <c:import  url="/jsp/common/common_template.jsp?template_name=common_table_title" />

        <form method="post" name="queryOp" id="queryOp" action="NonInstanReturnList">
            <!-- 页面用到的变量 通用模板 -->

            <c:set var="divideShow" scope="request" value="1"/>
            <c:import  url="/jsp/common/common_template_web_variable.jsp?template_name=common_web_variable" />

            <table  class="table_edit">
                <tr class="table_edit_tr">
                    <td class="table_edit_tr_td_label" >逻辑卡号:</td>
                    <td class="table_edit_tr_td_input">
                        <input type="text" name="q_tkLogicNo" id="q_tkLogicNo" size="26" maxlength="20" require="false" dataType="Integer|LimitB" max="20" msg="逻辑卡号为数字，最大20位" />
                    </td>
                    <td class="table_edit_tr_td_label" >凭证号:</td>
                    <td class="table_edit_tr_td_input">
                        <input type="text" name="q_receiptId" id="q_receiptId" size="25" maxlength="25" require="false" dataType="proofID|LimitB" max="25" msg="凭证号为数字与'-'，最大25位" />
                    </td>
                    <td class="table_edit_tr_td_label">票卡主类型:</td>
                    <td class="table_edit_tr_td_input">
                        <select id="q_cardMainID" name="q_cardMainID" onchange="setSelectValues('queryOp', 'q_cardMainID', 'q_cardSubID', 'commonVariable1');" >
                            <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_cardMain" />
                        </select>
                    </td>
                    <td class="table_edit_tr_td_label">票卡子类型:</td>
                    <td class="table_edit_tr_td_input">
                        <select id="q_cardSubID" name="q_cardSubID" >
                            <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_cardSub_none" />
                        </select>
                        <c:set var="pVarName" scope="request" value="commonVariable1"/>
                        <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_cardSub" />
                    </td>    
                </tr>
                <tr class="table_edit_tr">
                    <td class="table_edit_tr_td_label" >线路:</td>
                    <td class="table_edit_tr_td_input">
                        <select id="q_lineID" name="q_lineID" onChange="setSelectValues('queryOp', 'q_lineID', 'q_stationID', 'commonVariable');"  >				    
                            <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_line" />
                        </select>
                    </td>
                    <td class="table_edit_tr_td_label"><div align="right">车站:</div></td>
                    <td class="table_edit_tr_td_input">
                        <select id="q_stationID" name="q_stationID" >
                            <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_none" />
                        </select>
                        <c:set var="pVarName" scope="request" value="commonVariable"/>
                        <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_station" />
                    </td>
                    <td class="table_edit_tr_td_label" >开始日期:</td>
                    <td class="table_edit_tr_td_input">
                        <input type="text" name="q_beginTime" id="q_beginTime" size="10"  dataType="ICCSDate"  msg="开始日期不为空,格式为(yyyy-mm-dd)!" />
                        <a href="javascript:openCalenderDialogByID('q_beginTime','false');">
                            <img src="./images/calendar.gif" width="15" height="15" border="0" style="block"/>
                        </a>
                    </td>
                    <td class="table_edit_tr_td_label" >结束日期:</td>
                    <td class="table_edit_tr_td_input">
                        <input type="text" name="q_endTime" id="q_endTime" size="10"  dataType="ICCSDate|ThanDate" to="q_beginTime"  msg="结束日期不为空且必须大于开始日期,格式为(yyyy-mm-dd)!" />
                        <a href="javascript:openCalenderDialogByID('q_endTime','false');">
                            <img src="./images/calendar.gif" width="15" height="15" border="0" style="block"/>
                        </a>
                    </td>
                </tr>
                <tr class="table_edit_tr">
                    <td class="table_edit_tr_td_label"  >处理状态:</td>
                    <td class="table_edit_tr_td_input">
                        <select name="q_hdlFlag" id="q_hdlFlag">
                            <option value="">=请选择=</option>
                            <option value="1">已办理退款</option>
                            <option value="2">车票未处理</option>
                            <option value="3">车票已有退款结果</option>
                            <option value="4">黑名单车票，不能办理退款</option>
                            <option value="5">凭证号或卡号输入错误，重新输入</option>
                            <option value="6">退款申请已撤消</option>
                            <option value="7">退款申请得到许可</option>
                            <option value="8">卡号非法</option>
                        </select>
                    </td>
                    <td class="table_edit_tr_td_label" style="width: 10%"><font >理论应退金额(元):</font></td>
                    <td class="table_edit_tr_td_input" >
                        <input type="hidden" id="balaCheckFlag" name="balaCheckFlag" value="${checkboxflg}" />
                        <input type="checkbox" id="balaCheck" name="balaCheck" value="0" onclick="selCheck();"/>
                        <select id="q_rtnOper" name="q_rtnOper"  disabled="true">
                            <option value="1">&gt;</option>
                            <option value="2">&gt;=</option>
                            <option value="3">=</option>
                            <option value="4">&lt;=</option>
                            <option value="5">&lt;</option>
                        </select>
                        <input type="text" id="q_rtnBala" style="width: 50px" name="q_rtnBala" id="q_rtnBala"  disabled="true" dataType="Double" msg="理论应退金额必须为实数" value="0"/>
                    </td>

                    <td class="table_edit_tr_td_label" rowspan="2"  >

                        <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all_init" />
                        <c:set var="query" scope="request" value="1"/>
                        <c:set var="addClickMethod" scope="request" value="setControlNames('queryOp','q_tkLogicNo#q_receiptId#q_cardMainID#q_cardSubID#q_lineID#q_stationID#q_beginTime#q_endTime#q_hdlFlag#balaCheck#q_rtnOper#q_rtnBala');setLineCardNames('queryOp','q_lineID','q_stationID','commonVariable','q_cardMainID','q_cardSubID','commonVariable1');"/>
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
            <div id="clearStartHead" class="divForTableBlockHead" style="width: 138%">
                <table class="table_list_block" id="DataTableHead" >

                    <!--说明：列的序号从0开始 isDigit：false 表示按字符串排序 ：true表示按数值排序 sortedby：asc表示按升序 dec表示按降序 -->
                    <tr  class="table_list_tr_head_block" id="ignore">

                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false  index="0" sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 120px" >逻辑卡号</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false  index="1" sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 150px" >凭证号</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="2"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >票卡主类型</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="3"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 85px" >票卡子类型</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false  index="4"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 85px">乘客姓名</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=true index="5"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 105px">理论应退金额(元)</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=true index="6"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 105px">实际退款金额(元)</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false  index="7" sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 120px" >申请日期</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="8"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 150px">处理状态</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="9" sortedby="asc" onclick="sortForTableBlock('clearStart');">线路代码</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="10" sortedby="asc" onclick="sortForTableBlock('clearStart');">车站代码</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="11" sortedby="asc" onclick="sortForTableBlock('clearStart');">退款线路</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="12" sortedby="asc" onclick="sortForTableBlock('clearStart');">退款车站</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="13" sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 120px">备注</td>
                    </tr>


                </table>

            </div>
            <!-- <DIV id="clearStart" align="center" style="position:relative; left:20; width:95%; height:30%;min-height: 200px;max-height: 250px; layer-background-color:#99CC99; border:thin outset #ffffff; z-index:1; overflow: scroll;">-->
            <div id="clearStart"  class="divForTableBlockData" style="width: 138%">
                <table class="table_list_block" id="DataTable" >

                    <c:forEach items="${ResultSet}" var="rs">
                        <!--class="listTableData" -->
                        <tr class="table_list_tr_data"  onMouseOver="overResultRow('detailOp', this);" 
                            onMouseOut="outResultRow('detailOp', this);" 
                            onclick="clickResultRow('detailOp', this, 'detail');
                                    setPageControl('detailOp');" 
                            id="${rs.card_logical_id}#${rs.receipt_id}#${rs.card_main_id}#${rs.card_sub_id}#${rs.line_id}#${rs.station_id}">
                            <c:choose>
                                <c:when test="${rs.isOverTime == '1' }">
                                    <td  id="cardLogicalId" class="table_list_tr_col_data_block" style="width: 120px" >
                                        <font color="red"> ${rs.card_logical_id} </font>
                                    </td>
                                    <td  id="receipt_id" class="table_list_tr_col_data_block" style="width: 150px" >
                                        <font color="red"> ${rs.business_receipt_id} </font>
                                    </td>
                                    <td  id="card_main_id_text" class="table_list_tr_col_data_block">
                                        <font color="red"> ${rs.card_main_id_text} </font>
                                    </td>
                                    <td  id="card_sub_id_text" class="table_list_tr_col_data_block" style="width: 85px">
                                        <font color="red"> ${rs.card_sub_id_text} </font>
                                    </td>
                                    <td  id="apply_name" class="table_list_tr_col_data_block" style="width: 85px">
                                        <font color="red">  ${rs.apply_name} </font>
                                    </td>
                                    <td  id="return_bala" class="table_list_tr_col_data_block" style="width: 105px">
                                        <font color="red"> ${rs.return_bala} </font>
                                    </td>
                                    <td  id="actual_return_bala" class="table_list_tr_col_data_block" style="width: 105px">
                                        <font color="red"> ${rs.actual_return_bala}  </font>
                                    </td>
                                    <td  id="apply_datetime" class="table_list_tr_col_data_block" style="width: 120px">
                                        <font color="red">  ${rs.apply_datetime} </font>
                                    </td>
                                    <td  id="hdl_flag_text" class="table_list_tr_col_data_block" style="width: 150px">
                                        <font color="red"> ${rs.hdl_flag_text} </font>
                                    </td>
                                    <td  id="line_id_text" class="table_list_tr_col_data_block">
                                        <font color="red">  ${rs.line_id_text} </font>
                                    </td>
                                    <td  id="station_id_text" class="table_list_tr_col_data_block">
                                        <font color="red"> ${rs.station_id_text} </font>
                                    </td>
                                    <td  id="return_line_id_text" class="table_list_tr_col_data_block">
                                        <font color="red"> ${rs.return_line_id_text} </font>
                                    </td>
                                    <td  id="return_station_id_text" class="table_list_tr_col_data_block">
                                        <font color="red"> ${rs.return_station_id_text} </font>
                                    </td>
                                    <td  id="remark" class="table_list_tr_col_data_block" style="width: 120px">
                                        <font color="red"> ${rs.remark} </font>
                                    </td>

                                </c:when>
                                    <c:when test="${rs.isOverNoAduit == '1' }">
                                    <td  id="cardLogicalId" class="table_list_tr_col_data_block" style="width: 120px" >
                                        <font color="fuchsia"> ${rs.card_logical_id} </font>
                                    </td>
                                    <td  id="receipt_id" class="table_list_tr_col_data_block" style="width: 150px" >
                                        <font color="fuchsia"> ${rs.business_receipt_id} </font>
                                    </td>
                                    <td  id="card_main_id_text" class="table_list_tr_col_data_block">
                                        <font color="fuchsia"> ${rs.card_main_id_text} </font>
                                    </td>
                                    <td  id="card_sub_id_text" class="table_list_tr_col_data_block" style="width: 85px">
                                        <font color="fuchsia"> ${rs.card_sub_id_text} </font>
                                    </td>
                                    <td  id="apply_name" class="table_list_tr_col_data_block" style="width: 85px">
                                        <font color="fuchsia">  ${rs.apply_name} </font>
                                    </td>
                                    <td  id="return_bala" class="table_list_tr_col_data_block" style="width: 105px">
                                        <font color="fuchsia"></font>
                                    </td>
                                    <td  id="actual_return_bala" class="table_list_tr_col_data_block" style="width: 105px">
                                        <font color="fuchsia"></font>
                                    </td>
                                    <td  id="apply_datetime" class="table_list_tr_col_data_block" style="width: 120px">
                                        <font color="fuchsia">  ${rs.apply_datetime} </font>
                                    </td>
                                    <td  id="hdl_flag_text" class="table_list_tr_col_data_block" style="width: 150px">
                                        <font color="fuchsia"> ${rs.hdl_flag_text} </font>
                                    </td>
                                    <td  id="line_id_text" class="table_list_tr_col_data_block">
                                        <font color="fuchsia">  ${rs.line_id_text} </font>
                                    </td>
                                    <td  id="station_id_text" class="table_list_tr_col_data_block">
                                        <font color="fuchsia"> ${rs.station_id_text} </font>
                                    </td>
                                    <td  id="return_line_id_text" class="table_list_tr_col_data_block">
                                        <font color="fuchsia"> ${rs.return_line_id_text} </font>
                                    </td>
                                    <td  id="return_station_id_text" class="table_list_tr_col_data_block">
                                        <font color="fuchsia"> ${rs.return_station_id_text} </font>
                                    </td>
                                    <td  id="remark" class="table_list_tr_col_data_block" style="width: 120px">
                                        <font color="fuchsia"> ${rs.remark} </font>
                                    </td>

                                </c:when>
                                <c:otherwise>
                                    <td  id="cardLogicalId" class="table_list_tr_col_data_block" style="width: 120px" >
                                        ${rs.card_logical_id}
                                    </td>
                                    <td  id="receipt_id" class="table_list_tr_col_data_block" style="width: 150px" >
                                        ${rs.business_receipt_id}
                                    </td>
                                    <td  id="card_main_id_text" class="table_list_tr_col_data_block">
                                        ${rs.card_main_id_text}
                                    </td>
                                    <td  id="card_sub_id_text" class="table_list_tr_col_data_block" style="width: 85px">
                                        ${rs.card_sub_id_text}
                                    </td>
                                    <td  id="apply_name" class="table_list_tr_col_data_block" style="width: 85px">
                                        ${rs.apply_name}
                                    </td>
                                    <td  id="return_bala" class="table_list_tr_col_data_block" style="width: 105px">
                                        ${rs.return_bala}
                                    </td>
                                    <td  id="actual_return_bala" class="table_list_tr_col_data_block" style="width: 105px">
                                        ${rs.actual_return_bala}
                                    </td>
                                    <td  id="apply_datetime" class="table_list_tr_col_data_block" style="width: 120px">
                                        ${rs.apply_datetime}
                                    </td>
                                    <td  id="hdl_flag_text" class="table_list_tr_col_data_block" style="width: 150px">
                                        ${rs.hdl_flag_text}
                                    </td>
                                    <td  id="line_id_text" class="table_list_tr_col_data_block">
                                        ${rs.line_id_text}
                                    </td>
                                    <td  id="station_id_text" class="table_list_tr_col_data_block">
                                        ${rs.station_id_text}
                                    </td>
                                    <td  id="return_line_id_text" class="table_list_tr_col_data_block">
                                        ${rs.return_line_id_text} 
                                    </td>
                                    <td  id="return_station_id_text" class="table_list_tr_col_data_block">
                                        ${rs.return_station_id_text} 
                                    </td>
                                    <td  id="remark" class="table_list_tr_col_data_block" style="width: 120px">
                                        ${rs.remark}
                                    </td>

                                </c:otherwise>
                            </c:choose>

                        </tr>

                    </c:forEach>

                </table>
            </div>
        </div>

        <FORM method="post" name="detailOp" id="detailOp" action="NonInstanReturnList" >

            <c:set var="divideShow" scope="request" value="1"/>
            <c:import  url="/jsp/common/common_template_web_variable.jsp?template_name=common_web_variable" />

            <!--导出全部参数 -->
            <input type="hidden" name="expAllFields" id="d_expAllFields" value="CARD_LOGICAL_ID,BUSINESS_RECEIPT_ID,CARD_MAIN_ID_TEXT,CARD_SUB_ID_TEXT,APPLY_NAME,RETURN_BALA,ACTUAL_RETURN_BALA,TIME,HDL_FLAG_TEXT,LINE_ID_TEXT,STATION_ID_TEXT,RETURN_LINE_ID_TEXT,RETURN_STATION_ID_TEXT,REMARK" />
            <input type="hidden" name="expFileName" id="d_expFileName" value="非即时退款查询.xlsx" />
            <input type="hidden" name="divId" id="d_divId" value="clearStartHead" />
            <input type="hidden" name="methodUrl" id="d_methodUrl" value="/NonInstanReturnListExportAll" />


            <c:import url="/jsp/common/common_template.jsp?template_name=version_eval" />




            <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all_init" />

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
