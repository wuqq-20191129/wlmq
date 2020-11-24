<%--
    Document   : tickertStorageInBorrow
    Created on : 2017-9-2
    Author     : zhongziqi
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>借票归还</title>
        <script language="javascript" type="text/javascript" charset="utf-8" src="js/common_form.js"></script>
        <script language="javascript" type="text/javascript" charset="utf-8" src="js/common_form_extend.js"></script>
        <script language="javascript" type="text/javascript" charset="utf-8" src="js/Validator.js"></script>
        <script language="JavaScript" type="text/javascript" charset="utf-8" src="js/CalendarPop.js"></script>
        <script language="JavaScript" type="text/javascript" charset="utf-8" src="js/icProduceOut.js"></script>
        <script language="JavaScript" type="text/javascript" charset="utf-8" src="js/icStorageProductBill.js"></script>
        <script language="JavaScript" type="text/javascript" charset="utf-8" src="js/icStorageIn.js"></script>
        <link rel="stylesheet" type="text/css" href="css/frame/simple.css" title="Style"/>
        <SCRIPT LANGUAGE="JavaScript">
            function isFullReturn(formName)
            {
                var hdlAudit = document.forms['detailOp'].all['audit'];
                if (hdlAudit.disabled) {

                } else
                {
                    alert('请确认是否完全归还');
                }
            }
            function queryByCondition() {
                setControlNames('queryOp', 'q_billNo#q_lendBillNo#q_inBillNo#q_beginTime#q_endTime#q_storage#q_cardMainCode#q_cardSubCode#q_recordFlag');
                setLineCardNames('queryOp', '', '', '', 'q_cardMainCode', 'q_cardSubCode', 'commonVariable');
                setCommand('queryOp', "query");
                setSubmitForm('queryOp');
                queryAction('queryOp');
            }
        </SCRIPT>
    </head>

    <body onload="initDocument('queryOp', 'detail');
            initDocument('detailOp', 'detail');
            setControlsDefaultValue('queryOp');
            setListViewDefaultValue('detailOp', 'clearStart');
            setQueryControlsDefaultValue('queryOp', 'detailOp');
            setPageControl('detailOp');
            setTableRowBackgroundBlock('DataTable')
            setPrimaryKeys('detailOp', 'bill_no');">

        <!--<body >-->
        <table  class="table_title">
            <tr align="center" class="trTitle">
                <td colspan="4">借票归还
                </td>
            </tr>
        </table>

        <!-- 表头 通用模板 -->

        <c:set var="pTitleName" scope="request" value="查询"/>
        <c:set var="pTitleWidth" scope="request" value="50"/>
        <c:import  url="/jsp/common/common_template.jsp?template_name=common_table_title" />
        <form method="post" name="queryOp" id="queryOp" action="ticketStorageInBorrowManage">
            <!-- 页面用到的变量 通用模板 -->
            <c:set var="divideShow" scope="request" value="1"/>
            <c:import  url="/jsp/common/common_template_web_variable.jsp?template_name=common_web_variable" />

            <table  class="table_edit">
                <tr class="table_edit_tr">
                <input type="hidden" name="inType" value="GH"  />
                <td class="table_edit_tr_td_label">归还单号:</td>
                <td class="table_edit_tr_td_input">
                    <input type="text" id="q_billNo" name="q_billNo" value="" size="9" maxlength="12" require="false" dataType="NumAndEng|LimitContainChinese" min="1" max="12" msg="归还单号最多为12位，由字母和数字组成" />
                </td>
                <td class="table_edit_tr_td_label">借出单号:</td>
                <td class="table_edit_tr_td_input">
                    <input type="text" id="q_lendBillNo" name="q_lendBillNo" value="" size="12" maxlength="12"  require="false" dataType="NumAndEng|LimitContainChinese" min="1" max="12"  msg="借出单号最多为12位，由字母和数字组成" />
                </td>
                <td class="table_edit_tr_td_label">入库单号:</td>
                <td class="table_edit_tr_td_input">
                    <input type="text" id="q_inBillNo" name="q_inBillNo" value="" size="9" maxlength="12" require="false" dataType="NumAndEng|LimitContainChinese" min="1" max="12" msg="入库单号最多为12位，由字母和数字组成" />
                </td>
                <td class="table_edit_tr_td_label">仓库:</td>
                <td class="table_edit_tr_td_input">
                    <select id="q_storage" name="q_storage" >
                        <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_storage" />
                    </select>
                </td>

                </tr>
                <tr class="table_edit_tr">
                    <td class="table_edit_tr_td_label">归还时间始: </td>
                    <td class="table_edit_tr_td_input">
                        <input type="text" name="q_beginTime" id="q_beginTime" value="" size="12" require="false" dataType="Date" format="ymd"  msg="生成时间格式为(yyyy-mm-dd)!" />
                        <a href="javascript:openCalenderDialogByID('q_beginTime','false');">
                            <img src="./images/calendar.gif" width="15" height="15" border="0" style="block"/>
                        </a>
                    </td>
                    <td class="table_edit_tr_td_label">归还时间末: </td>
                    <td class="table_edit_tr_td_input">
                        <input type="text" name="q_endTime" id="q_endTime" value="" size="12" require="false"  dataType="Date|ThanDate" format="ymd" to="q_beginTime"
                               msg="生成结束时间格式为(yyyy-mm-dd)且大于等于开始时间!" />
                        <a href="javascript:openCalenderDialogByID('q_endTime','false');">
                            <img src="./images/calendar.gif" width="15" height="15" border="0" style="block"/>
                        </a>
                    </td>
                    <td class="table_edit_tr_td_label" >票卡主类型:</td>
                    <td class="table_edit_tr_td_input">
                        <select id="q_cardMainCode" name="q_cardMainCode" onChange="setSelectValues('queryOp', 'q_cardMainCode', 'q_cardSubCode', 'commonVariable');"  >
                            <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_maincard" />
                        </select>
                    </td>
                    <td class="table_edit_tr_td_label"><div align="right">票卡子类型:</div></td>
                    <td class="table_edit_tr_td_input">
                        <select id="q_cardSubCode" name="q_cardSubCode" >
                            <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_none" />
                        </select>
                        <c:set var="pVarName" scope="request" value="commonVariable"/>
                        <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_mainsubcard" />
                    </td>
                </tr>
                <tr class="table_edit_tr">
                    <td class="table_edit_tr_td_label">计划单状态:</td>
                    <td class="table_edit_tr_td_input">
                        <select id="q_recordFlag" name="q_recordFlag" >
                            <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_billstatues" />
                        </select>
                    </td>
                    <td class="table_edit_tr_td_label" rowspan="1">
                        <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all_init" />
                        <c:set var="query" scope="request" value="1"/>
                        <c:set var="addClickMethod" scope="request" value="setControlNames('queryOp','q_billNo#q_lendBillNo#q_inBillNo#q_beginTime#q_endTime#q_storage#q_cardMainCode#q_cardSubCode#q_recordFlag');setLineCardNames('queryOp','','','','q_cardMainCode','q_cardSubCode','commonVariable');"/>
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
        <div id="clearStartBlock" class="divForTableBlock">
            <div id="clearStartHead" class="divForTableBlockHead" style="width :1500px" >
                <table class="table_list_block" id="DataTableHead" >
                    <!--说明：列的序号从0开始 isDigit：false 表示按字符串排序 ：true表示按数值排序 sortedby：asc表示按升序 dec表示按降序 -->
                    <tr  class="table_list_tr_head_block" id="ignore">
                        <td   class="table_list_tr_col_head_block">
                            <input type="checkbox" name="rectNoAll" id="rectNoAll" onclick="selectAllRecord('detailOp', 'rectNoAll', 'rectNo', 'clearStart', 0);
                                    controlsDisableForMulRecords('detailOp', ['del']);
                                    controlsByFlagAndMul('detailOp', ['audit']);
                                    setPageControl('detailOp');"/>
                        </td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="1"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 90px">归还单号</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="2"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 90px">入库单号</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="3"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 90px">借出单号</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="4"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 120px">归还日期</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="5"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 90px">单据状态</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="6"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 90px">入库单据状态</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="7"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 120px">归还人</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="8"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 120px">接收人</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="9"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 120px"> 借票单位</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="10"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 90px">审核人</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="11"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 120px">审核时间</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="12"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 200px">备注</td>
                    </tr>
                </table>

            </div>
            <div id="clearStart"  class="divForTableBlockData" style="width :1500px">
                <table class="table_list_block" id="DataTable" >
                    <c:forEach items="${ResultSet}" var="rs">
                        <tr class="table_list_tr_data"  onMouseOver="overResultRow('detailOp', this);"
                            onMouseOut="outResultRow('detailOp', this);"
                            onclick="clickOneResultRow('detailOp', this, 'detail','rectNo');
                                    controlsDisableForMulRecords('detailOp', ['del']);
                                    controlsByFlagAndMul('detailOp', ['audit']);
                                    setPageControl('detailOp');"
                            id="${rs.bill_no}" flag="${rs.record_flag}">

                            <td id="rectNo1" class="table_list_tr_col_data_block">
                                <input type="checkbox" name="rectNo" id="rectNo" onclick="unSelectAllRecord('detailOp', 'rectNoAll', 'rectNo');"
                                       value="${rs.bill_no}"  flag="${rs.record_flag}" >
                                </input>
                            </td>
                            <td  id="bill_no" class="table_list_tr_col_data_block" style="width: 90px">
                                <a href='#'
                                   onClick="openwindow('ticketStorageInBorrowManage?billRecordFlag=${rs.record_flag}&queryCondition=${rs.bill_no}&command=queryDetail&operType=planDetail&ModuleID=${ModuleID}&_divideShow=0', '', 1000, 600)">
                                    ${rs.bill_no}
                                </a>
                            </td>
                            <td  id="in_bill_no" class="table_list_tr_col_data_block" style="width: 90px">
                                <a href='#'
                                   onClick="openwindow('ticketStorageInBorrowManage?billRecordFlag=${rs.record_in_flag}&queryCondition=${rs.in_bill_no}&command=query&operType=inBillDetail&ModuleID=${ModuleID}&_divideShow=0', '', 1000, 600)">
                                    ${rs.in_bill_no}
                                </a>
                            </td>
                            <td  id="lend_bill_no" class="table_list_tr_col_data_block" style="width: 90px">
                                ${rs.lend_bill_no}
                            </td>
                            <td  id="bill_date" class="table_list_tr_col_data_block" style="width: 120px">
                                ${rs.bill_date}
                            </td>
                            <td  id="record_flag" class="table_list_tr_col_data_block" style="width: 90px">
                                    ${rs.record_flag_name}
                            </td>
                            <td  id="record_in_flag" class="table_list_tr_col_data_block" style="width: 90px">
                                    ${rs.record_in_flag_name}
                            </td>
                            <td  id="return_man" class="table_list_tr_col_data_block" style="width: 120px">
                                ${rs.return_man}
                            </td>
                            <td  id="receive_man" class="table_list_tr_col_data_block" style="width: 120px">
                                ${rs.receive_man}
                            </td>
                            <td  id="unit_id" class="table_list_tr_col_data_block" style="width: 120px">
                                ${rs.unit_name}
                            </td>
                            <td  id="verify_person" class="table_list_tr_col_data_block" style="width: 90px">
                                ${rs.verify_person}
                            </td>
                            <td  id="verify_date" class="table_list_tr_col_data_block" style="width: 120px">
                                ${rs.verify_date}
                            </td>
                            <td  id="remark" class="table_list_tr_col_data_block" style="width: 200px">
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

        <!--onSubmit="return Validator.Validate(this,2);"-->
        <FORM method="post" name="detailOp" id="detailOp" action="ticketStorageInBorrowManage" >
            <c:set var="divideShow" scope="request" value="1"/>
            <c:import  url="/jsp/common/common_template_web_variable.jsp?template_name=common_web_variable" />
            <c:set var="divideShow" scope="request" value="1"/>
            <!--查询结果对应列，expAllFields的值为其实体类对应get方法，顺序一定要跟页面显示的顺序一致,不然导出列数据会混乱。-->
			<input type="hidden" name="expAllFields" id="d_expAllFields"
			value="getBill_no,getIn_bill_no,getLend_bill_no,getBill_date,getRecord_flag_name,getRecord_in_flag_name,getReturn_man,getReceive_man,getUnit_name,getVerify_person,getVerify_date,getRemark" />
			<!--导出文件名字 生成的Excel sheet的名字也是这个-->
			<input type="hidden" name="expFileName" id="d_expFileName" value="借票归还.xlsx" />
			<!--需要导出页面的表数据  一个页面多个导出全部根据这里设置详情参看运营系统退款模块-->
			<input type="hidden" name="divId" id="d_divId" value="clearStartHead">
			<!--对应你controller写的方法  一个页面多个导出全部根据这里设置详情参看运营系统退款模块-->
			<input type="hidden" name="methodUrl" id="d_methodUrl" value="/TicketStorageInBorrowExportAll"/>
            <input type="hidden" name="inType" value="GH"  />
            <input type="hidden" name="isReturn" value = "0"  />
            <div id="detail"  class="divForTableDataDetail" >
                <table  class="table_edit_detail">
                    <tr class="table_edit_tr">
                        <td class="table_edit_tr_td_label">借出单号: </td>
                        <td class="table_edit_tr_td_input">
                            <select id="d_lend_bill_no" name="d_lend_bill_no" require="true" dataType="Require" msg="借出单号不能为空!">
                                <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_lendBillNos" />
                            </select>
                        </td>
                        <td class="table_edit_tr_td_label">归还人:</td>
                        <td class="table_edit_tr_td_input">
                            <input type="text" id="d_return_man" name="d_return_man" size="8"  require="true" dataType="Require|LimitContainChinese" maxlength="45" max="45" msg="归还人不能空且不超过45个字节,一个中文3个字节(最大15个中文)"/>
                        </td>

                        <td class="table_edit_tr_td_label">接收人:</td>
                        <td class="table_edit_tr_td_input">
                            <input type="text" id="d_receive_man" name="d_receive_man" size="8"  require="true" dataType="Require|LimitContainChinese" maxlength="45" max="45" msg="接收人不能空且不超过45个字节,一个中文3个字节(最大15个中文)"/>
                        </td>

                        <td class="table_edit_tr_td_label">备  注:</td>
                        <td class="table_edit_tr_td_input">
                            <input type="text" id="d_remark"  name="d_remark" size="58"  maxlength="256" dataType="LimitContainChinese" max="256" msg="备注最大长度为256个字节，一个中文是3个字节（最大85个中文）"/>
                        </td>
                    </tr>
                </table>
            </div>
            <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all_init" />
            <c:set var="add" scope="request" value="1"/>
            <c:set var="del" scope="request" value="1"/>
            <c:set var="save" scope="request" value="1"/>
            <c:set var="cancle" scope="request" value="1"/>
            <c:set var="audit" scope="request" value="1"/>
            <c:set var="print" scope="request" value="1"/>
            <c:set var="export" scope="request" value="1"/>
            <c:set var="expAll" scope="request" value="1" />
            <c:set var="btNext" scope="request" value="1"/>
            <c:set var="btNextEnd" scope="request" value="1"/>
            <c:set var="btBack" scope="request" value="1"/>
            <c:set var="btBackEnd" scope="request" value="1"/>
            <c:set var="btGoPage" scope="request" value="1"/>
            <c:set var="addformQueryMethod" scope="request" value="setLineCardNames('detailOp','d_storage','d_zone','commonVariable1','card_main_code','card_sub_code','commonVariable');"/>
            <c:set var="addClickMethod" scope="request" value="setUpdatePrimaryKey('detailOp','d_billNo');enablePrimaryKeys('detailOp')"/>
            <c:set var="clickMethod" scope="request" value="btnClick('detailOp','clearStart','detail','','clearStartHead');"/>
            <c:set var="addAfterMethod" scope="request" value="disablePrimaryKeys('detailOp')"/>
            <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all" />
            <br/>
        </FORM>

    </body>
</html>