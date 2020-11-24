<%--
    Document   : Ticket StorageOut Distribute Plan
    Created on : 2017-7-27, 20:48:42
    Author     : zhongzq
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>配票计划</title>


        <script language="javascript" type="text/javascript" charset="utf-8" src="js/common_form.js"></script>
        <script language="javascript" type="text/javascript" charset="utf-8" src="js/common_form_extend.js"></script>
        <script language="javascript" type="text/javascript" charset="utf-8" src="js/Validator.js"></script>
        <script language="JavaScript" type="text/javascript" charset="utf-8" src="js/CalendarPop.js"></script>
        <script language="JavaScript" type="text/javascript" charset="utf-8" src="js/icProduceOut.js"></script>
        <script language="JavaScript" type="text/javascript" charset="utf-8" src="js/icStorageProductBill.js"></script>
        <link rel="stylesheet" type="text/css" href="css/frame/simple.css" title="Style"/>
    </head>
    <!--
      setInvisable('detailOp', 'clone');
     setPrimaryKeys('detailOp', 'd_lineID#d_stationID#d_deviceID#d_deviceType');
    -->
    <!--
    initDocument('formQuery','detail');
    initDocument('formOp','detail');
    setControlsDefaultValue('formQuery');
    setListViewDefaultValue('formOp','clearStart');
    setQueryControlsDefaultValue('formQuery','formOp');
    setPrimaryKeys('formOp','d_billNo');
    -->
    <body onload="preLoadVal('q_beginTime', 'q_endTime',90);
            initDocument('queryOp', 'detail');
            initDocument('detailOp', 'detail');
            setControlsDefaultValue('queryOp');
            setListViewDefaultValue('detailOp', 'clearStart');
            setQueryControlsDefaultValue('queryOp', 'detailOp');
            setPageControl('detailOp');
            setTableRowBackgroundBlock('DataTable');
            "
                >

        <!--<body >-->
        <table  class="table_title">
            <tr align="center" class="trTitle">
                <td colspan="4">配票计划
                </td>
            </tr>
        </table>

        <!-- 表头 通用模板 -->

        <c:set var="pTitleName" scope="request" value="查询"/>
        <c:set var="pTitleWidth" scope="request" value="50"/>
        <c:import  url="/jsp/common/common_template.jsp?template_name=common_table_title" />

        <form method="post" name="queryOp" id="queryOp" action="ticketStorageOutDistributeManage">
            <!-- 页面用到的变量 通用模板 -->

            <c:set var="divideShow" scope="request" value="1"/>
            <c:import  url="/jsp/common/common_template_web_variable.jsp?template_name=common_web_variable" />

            <table  class="table_edit">
                <tr class="table_edit_tr">
                    <td class="table_edit_tr_td_label">计划单号:</td>
                    <td class="table_edit_tr_td_input">
                        <input type="text" id="q_billNo" name="q_billNo" value="" size="12" maxlength="12"  require="false" dataType="NumAndEng" min="12" max="12"  msg="计划单号应为12位" />
                    </td>
                    <td class="table_edit_tr_td_label">制单人:</td>
                    <td class="table_edit_tr_td_input">
                        <input type="text" id="q_formMaker" name="q_formMaker" value="" size="12" maxlength="8" require="false" dataType="LimitContainChinese" min="1" max="8" msg="制单人最多为8位" />
                    </td>

                    <td class="table_edit_tr_td_label">起始时间: </td>
                    <td class="table_edit_tr_td_input">
                        <input type="text" name="q_beginTime" id="q_beginTime" value="" size="12" require="false" dataType="Date" format="ymd"  msg="生成时间格式为(yyyy-mm-dd)!" />
                        <a href="javascript:openCalenderDialogByID('q_beginTime','false');">
                            <img src="./images/calendar.gif" width="15" height="15" border="0" style="block"/>
                        </a>
                    </td>
                    <td class="table_edit_tr_td_label">结束时间: </td>
                    <td class="table_edit_tr_td_input">
                        <input type="text" name="q_endTime" id="q_endTime" value="" size="12" require="false"  dataType="Date|ThanDate" format="ymd" to="q_beginTime"
                               msg="生成结束时间格式为(yyyy-mm-dd)且大于等于开始时间!" />
                        <a href="javascript:openCalenderDialogByID('q_endTime','false');">
                            <img src="./images/calendar.gif" width="15" height="15" border="0" style="block"/>
                        </a>
                    </td>
                </tr>
                <tr class="table_edit_tr">
                    <td class="table_edit_tr_td_label">仓库:</td>
                    <td class="table_edit_tr_td_input">
                        <select id="q_storage" name="q_storage" >
                            <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_storage" />
                        </select>
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
                    <td class="table_edit_tr_td_label">出库原因:</td>
                    <td class="table_edit_tr_td_input">
                        <select id="q_outReason" name="q_outReason" >
                            <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_outreason_distribute" />
                        </select>
                    </td>
                </tr>
                <tr class="table_edit_tr">
                    <td class="table_edit_tr_td_label">计划单状态:</td>
                    <td class="table_edit_tr_td_input">
                        <select id="q_planStatu" name="q_planStatu" >
                            <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_billstatues" />
                        </select>
                    </td>

                    <td class="table_edit_tr_td_label" rowspan="1">

                        <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all_init" />
                        <c:set var="query" scope="request" value="1"/>
                        <c:set var="addClickMethod" scope="request" value="setControlNames('queryOp','q_billNo#q_formMaker#q_planStatu#q_beginTime#q_endTime#q_cardMainCode#q_cardSubCode#q_outReason#q_storage');setLineCardNames('queryOp','','','','q_cardMainCode','q_cardSubCode','commonVariable');"/>
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
            <div id="clearStartHead" class="divForTableBlockHead" style="width: 130%;">
                <table class="table_list_block" id="DataTableHead" >

                    <!--说明：列的序号从0开始 isDigit：false 表示按字符串排序 ：true表示按数值排序 sortedby：asc表示按升序 dec表示按降序 -->
                    <tr  class="table_list_tr_head_block" id="ignore">
                        <td   class="table_list_tr_col_head_block">
                            <input type="checkbox" name="rectNoAll" id="rectNoAll" onclick="selectAllRecord('detailOp', 'rectNoAll', 'rectNo', 'clearStart', 0);setPageControl('detailOp');controlsByFlagAndMul('detailOp', ['audit']);controlsDisableForMulRecords('detailOp', ['del']);"/>
                        </td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="1"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 100px">计划单号</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="2"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 100px">出库单号</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="3"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 100px">配票单号</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="4"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 90px">单据状态</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="5"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 80px">制单人</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="6"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 120px">制单时间</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="7"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 80px">审核人</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="8"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 120px">审核时间</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="9"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 80px">领票人</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="10" sortedby="asc"  onclick="sortForTableBlock('clearStart');" style="width: 80px">车票管理员</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="11" sortedby="asc"  onclick="sortForTableBlock('clearStart');" style="width: 180px">备注</td>
                    </tr>


                </table>

            </div>
            <!-- <DIV id="clearStart" align="center" style="position:relative; left:20; width:95%; height:30%;min-height: 200px;max-height: 250px; layer-background-color:#99CC99; border:thin outset #ffffff; z-index:1; overflow: scroll;">-->
            <div id="clearStart"  class="divForTableBlockData" style="width: 130%;">
                <table class="table_list_block" id="DataTable" >

                    <c:forEach items="${ResultSet}" var="rs">
                        <!--class="listTableData" -->
                        <!--
                        setSelectValuesByRow('detailOp', 'd_stationID', 'commonVariable');
                        -->
                        <tr class="table_list_tr_data"  onMouseOver="overResultRow('detailOp', this);"
                            onMouseOut="outResultRow('detailOp', this);"
                            onclick="clickOneResultRow('detailOp', this, 'detail','rectNo');
                                    controlsDisableForMulRecords('detailOp', ['del']);
                                    controlsByFlagAndMul('detailOp', ['audit']);
                                    setPageControl('detailOp');"
                            id="${rs.billNo}" flag="${rs.recordFlag}">

                            <td id="rectNo1" class="table_list_tr_col_data_block">

                                <input type="checkbox" name="rectNo" id="rectNo" onclick="unSelectAllRecord('detailOp', 'rectNoAll', 'rectNo');"
                                       value="${rs.billNo}"  flag="${rs.recordFlag}">
                                </input>
                            </td>
                            <td  id="billNo" class="table_list_tr_col_data_block" style="width: 100px">
                                <a href='#'
                                   onClick="openwindow('ticketStorageOutDistributeManage?queryCondition=${rs.billNo}&command=queryDetail&operType=planDetail&billRecordFlag=${rs.recordFlag}&ModuleID=${ModuleID}&_divideShow=0', '', 1200, 800)">
                                    <!--  <a href='#' onClick="window.showModelessDialog('ticketStorageOutProduceManage.do?queryCondition={billNo}&amp;command=queryDetail&amp;operType=planDetail&amp;ModuleID={/Service/Result/ModuleID}','0','dialogWidth:700px;dialogHeight:500px;center:yes;resizable:no;status:no;scroll:no')"> -->
                                    ${rs.billNo}
                                </a>
                            </td>
                            <c:choose>
                                <c:when test="${rs.recordFlag=='3'}" >
                                    <td  id="outBillNo" class="table_list_tr_col_data_block" style="width: 100px">
                                        ${rs.outBillNo}
                                    </td>
                                    <td  id="distributeBillNo" class="table_list_tr_col_data_block" style="width: 100px">
                                        ${rs.distributeBillNo}
                                    </td>
                                </c:when>
                                <c:otherwise>
                                    <td  id="outBillNo" class="table_list_tr_col_data_block" style="width: 100px">
                                        <a href='#'
                                           onClick="openwindow('ticketStorageOutDistributeManage?queryCondition=${rs.outBillNo}&command=query&operType=outBill&billRecordFlag=${rs.recordFlag}&ModuleID=${ModuleID}', '', 1300, 600)">
                                            <!--  <a href='#' onClick="window.showModelessDialog('ticketStorageOutProduceManage.do?queryCondition={billNo}&amp;command=queryDetail&amp;operType=planDetail&amp;ModuleID={/Service/Result/ModuleID}','0','dialogWidth:700px;dialogHeight:500px;center:yes;resizable:no;status:no;scroll:no')"> -->
                                            ${rs.outBillNo}
                                        </a>
                                    </td>
                                    <td  id="distributeBillNo" class="table_list_tr_col_data_block" style="width: 100px">
                                        <a href='#'
                                           onClick="openwindow('ticketStorageOutDistributeManage?queryCondition=${rs.distributeBillNo}&command=query&operType=distributeDetail&billRecordFlag=${rs.recordFlag}&ModuleID=${ModuleID}', '', 1300, 600)">
                                            <!--  <a href='#' onClick="window.showModelessDialog('ticketStorageOutProduceManage.do?queryCondition={billNo}&amp;command=queryDetail&amp;operType=planDetail&amp;ModuleID={/Service/Result/ModuleID}','0','dialogWidth:700px;dialogHeight:500px;center:yes;resizable:no;status:no;scroll:no')"> -->
                                            ${rs.distributeBillNo}
                                        </a>
                                    </td>
                                </c:otherwise>
                            </c:choose>
                            <td  id="recordFlag" class="table_list_tr_col_data_block" style="width: 90px">
                                ${rs.recordFlagName}
                            </td>
                            <td  id="formMaker" class="table_list_tr_col_data_block" style="width: 80px">
                                ${rs.formMaker}
                            </td>
                            <td  id="billDate" class="table_list_tr_col_data_block" style="width: 120px">
                                ${rs.billDate}
                            </td>
                            <td  id="verifyPerson" class="table_list_tr_col_data_block" style="width: 80px">
                                ${rs.verifyPerson}
                            </td>
                            <td  id="verifyDate" class="table_list_tr_col_data_block" style="width: 120px">
                                ${rs.verifyDate}
                            </td>

                            <td  id="receiveMan" class="table_list_tr_col_data_block" style="width: 80px"">
                                ${rs.receiveMan}
                            </td>
                            <td  id="distributeMan" class="table_list_tr_col_data_block" style="width: 80px">
                                ${rs.distributeMan}
                            </td>
                            <td  id="remarks" class="table_list_tr_col_data_block" style="width: 180px">
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

        <FORM method="post" name="detailOp" id="detailOp" action="ticketStorageOutDistributeManage" >

            <c:set var="divideShow" scope="request" value="1"/>
            <c:import  url="/jsp/common/common_template_web_variable.jsp?template_name=common_web_variable" />
			<!--查询结果对应列，expAllFields的值为其实体类对应get方法，顺序一定要跟页面显示的顺序一致,不然导出列数据会混乱。-->
			<input type="hidden" name="expAllFields" id="d_expAllFields"
			value="getBillNo,getOutBillNo,getDistributeBillNo,getRecordFlagName,getFormMaker,getBillDate,getVerifyPerson,getVerifyDate,getReceiveMan,getDistributeMan,getRemark" />
			<!--导出文件名字 生成的Excel sheet的名字也是这个-->
			<input type="hidden" name="expFileName" id="d_expFileName" value="配票出库.xlsx" />
			<!--需要导出页面的表数据  一个页面多个导出全部根据这里设置详情参看运营系统退款模块-->
			<input type="hidden" name="divId" id="d_divId" value="clearStartHead">
			<!--对应你controller写的方法  一个页面多个导出全部根据这里设置详情参看运营系统退款模块-->
			<input type="hidden" name="methodUrl" id="d_methodUrl" value="/TicketStorageOutDistributeExportAll"/>
            <!--<DIV id="detail" align="center" class="divForTableData" style="position:relative; left:20; width:95%; height:10; layer-background-color:#99CC99; border:thin outset #ffffff; z-index:1;">-->
            <div id="detail"  class="divForTableDataDetail" >
                <table  class="table_edit_detail">
                    <tr class="table_edit_tr">
                        <td class="table_edit_tr_td_label">车票管理员: </td>
                        <td class="table_edit_tr_td_input">
                            <input type="text" name="d_distributeMan" id="d_distributeMan" size="15"
                                   require="true" dataType="NotEmpty|LimitContainChinese" maxlength="15" min="1" max="45" msg="车票管理员不能为空且最大15个字"/>
                        </td>
                        <td class="table_edit_tr_td_label">领票人: </td>
                        <td class="table_edit_tr_td_input">
                            <input type="text" name="d_receiveMan" id="d_receiveMan" size="15"
                                   require="true" dataType="NotEmpty|LimitContainChinese" maxlength="15" min="1" max="45" msg="领票人不能为空且最大15个字"/>
                        </td>
                    </tr>
                    <tr class="table_edit_tr">
                        <td class="table_edit_tr_td_label">备注:</td>
                        <td class="table_edit_tr_td_input">
                            <input type="text" name="d_remarks" id="d_remarks" size="80" require="false" maxlength="256" dataType="LimitContainChinese" min="1" max="256" msg="备注最大长度为256个字节，一个中文字是3个字节(最大85个中文)"/>
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
            <c:set var="btNext" scope="request" value="1"/>
            <c:set var="btNextEnd" scope="request" value="1"/>
            <c:set var="btBack" scope="request" value="1"/>
            <c:set var="btBackEnd" scope="request" value="1"/>
			<c:set var="btGoPage" scope="request" value="1"/>
			<c:set var="expAll" scope="request" value="1" />
            <c:set var="clickMethod" scope="request" value="btnClick('detailOp','clearStart','detail','','clearStartHead');"/>
            <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all" />
            <br/>
        </FORM>
        <!--
        <c:set var="addQueryMethod" scope="request" value="setLineCardNames('detailOp','d_storage','d_zone','commonVariable','','','');"/>
        <c:set var="addClickMethod" scope="request" value="setUpdatePrimaryKey('detailOp','d_lineID#d_stationID#d_deviceType#d_deviceID#Version');"/>
        -->


    </body>
</html>
