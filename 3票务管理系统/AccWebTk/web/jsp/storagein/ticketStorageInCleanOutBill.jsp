<%-- 
    Document   : TicketStorageInCleanOutBill
    Created on : 2017-7-29
    Author     : luck
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>清洗入库</title>


        <script language="javascript" type="text/javascript" charset="utf-8" src="js/common_form.js"></script>
        <script language="javascript" type="text/javascript" charset="utf-8" src="js/common_form_extend.js"></script>
        <script language="javascript" type="text/javascript" charset="utf-8" src="js/Validator.js"></script>
        <script language="JavaScript" type="text/javascript" charset="utf-8" src="js/CalendarPop.js"></script>
        <script language="JavaScript" type="text/javascript" charset="utf-8" src="js/icProduceOut.js"></script>
        <script language="JavaScript" type="text/javascript" charset="utf-8" src="js/icStorageProductBill.js"></script>
        <script language="JavaScript" type="text/javascript" charset="utf-8" src="js/icStorageIn.js"></script>
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
    <body onload="initDocument('queryOp', 'detail');
            initDocument('detailOp', 'detail');
            setControlsDefaultValue('queryOp');
            setListViewDefaultValue('detailOp', 'clearStart');
            setQueryControlsDefaultValue('queryOp', 'detailOp');

            setPageControl('detailOp');
            setTableRowBackgroundBlock('DataTable')">

        <!--<body >-->
        <table  class="table_title">
            <tr align="center" class="trTitle">
                <td colspan="4">清洗入库
                </td>
            </tr>
        </table>

        <!-- 表头 通用模板 -->

        <c:set var="pTitleName" scope="request" value="查询"/>
        <c:set var="pTitleWidth" scope="request" value="50"/>
        <c:import  url="/jsp/common/common_template.jsp?template_name=common_table_title" />

        <form method="post" name="queryOp" id="queryOp" action="ticketStorageInCleanManage">
            <!-- 页面用到的变量 通用模板 -->

            <c:set var="divideShow" scope="request" value="1"/>
            <c:import  url="/jsp/common/common_template_web_variable.jsp?template_name=common_web_variable" />

            <table  class="table_edit">
                <tr class="table_edit_tr">
                    <td class="table_edit_tr_td_label">入库单号:</td>
                    <td class="table_edit_tr_td_input">
                        <input type="text" id="q_billNo" name="q_billNo" value="" size="12" maxlength="12"  require="false" dataType="NumAndEng|LimitB" min="12" max="12"  msg="入库单号应为12位数字或字母组合" />
                    </td>
                    <td class="table_edit_tr_td_label">出库单号:</td>
                    <td class="table_edit_tr_td_input">
                        <input type="text" id="q_relatedBillno" name="q_relatedBillno" value="" size="12" maxlength="12"  require="false" dataType="NumAndEng|LimitB" min="12" max="12"  msg="出库单号应为12位数字或字母组合" />
                    </td>

                    <td class="table_edit_tr_td_label">起始时间: </td>
                    <td class="table_edit_tr_td_input">
                        <input type="text" name="q_beginTime" id="q_beginTime" value="" size="12" require="false" dataType="Date" format="ymd"  msg="生成时间格式为(yyyy-mm-dd)!" />
                        <a href="javascript:openCalenderDialogByID('q_beginTime','false');">
                            <img src="./images/calendar.gif" width="15" height="15" border="0" style="block"/>
                        </a>
                    </td>
                </tr>
                <tr class="table_edit_tr">
                    <td class="table_edit_tr_td_label">结束时间: </td>
                    <td class="table_edit_tr_td_input" >
                        <input type="text" name="q_endTime" id="q_endTime" value="" size="12" require="false"  dataType="Date|ThanDate" format="ymd" to="q_beginTime"
                               msg="生成结束时间格式为(yyyy-mm-dd)且大于等于开始时间!" />
                        <a href="javascript:openCalenderDialogByID('q_endTime','false');">
                            <img src="./images/calendar.gif" width="15" height="15" border="0" style="block"/>
                        </a>
                    </td>
                    <td class="table_edit_tr_td_label">仓库:</td>
                    <td class="table_edit_tr_td_input">
                        <select id="q_storage" name="q_storage" >
                            <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_storage" />
                        </select>
                    </td>
                    <td class="table_edit_tr_td_label" >票卡主类型:</td>
                    <td class="table_edit_tr_td_input">
                        <select id="q_cardMainCode" name="q_cardMainCode" onChange="setSelectValues('queryOp', 'q_cardMainCode', 'q_cardSubCode', 'commonVariable');"  >				    
                            <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_maincard_clean" />
                        </select>
                    </td>
                </tr>
                <tr class="table_edit_tr">
                    <td class="table_edit_tr_td_label"><div align="right">票卡子类型:</div></td>
                    <td class="table_edit_tr_td_input">
                        <select id="q_cardSubCode" name="q_cardSubCode" >
                            <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_none" />
                        </select>
                        <c:set var="pVarName" scope="request" value="commonVariable"/>
                        <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_mainsubcard" />
                    </td>

                    <td class="table_edit_tr_td_label">出库单状态:</td>
                    <td class="table_edit_tr_td_input">
                        <select id="q_recordFlag" name="q_recordFlag" >
                            <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_billstatues" />
                        </select>
                    </td>

                    <td class="table_edit_tr_td_label" rowspan="1">

                        <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all_init" />
                        <c:set var="query" scope="request" value="1"/>
                        <c:set var="addClickMethod" scope="request" value="setControlNames('queryOp','q_billNo#q_relatedBillno#q_operator#q_recordFlag#q_beginTime#q_endTime#q_cardMainCode#q_cardSubCode#q_outReason#q_storage');setLineCardNames('queryOp','','','','q_cardMainCode','q_cardSubCode','commonVariable');"/>
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
            <div id="clearStartHead" class="divForTableBlockHead" style="width: 121%">
                <table class="table_list_block" id="DataTableHead" >

                    <!--说明：列的序号从0开始 isDigit：false 表示按字符串排序 ：true表示按数值排序 sortedby：asc表示按升序 dec表示按降序 -->
                    <tr  class="table_list_tr_head_block" id="ignore">
                        <td   class="table_list_tr_col_head_block">
                            <input type="checkbox" name="rectNoAll" id="rectNoAll" onclick="selectAllRecord('detailOp', 'rectNoAll', 'rectNo', 'clearStart', 0);
                                    controlsByFlagAndMul('detailOp', ['audit']);
                                    controlsDisableForMulRecords('detailOp', ['del']);
                                    setPageControl('detailOp');"/>
                        </td>	
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="1"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 90px">入库单号</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="2"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 130px">入库日期</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="3"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >制单人</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="4"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >交票人</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="5"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >车管员</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="6"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >记帐员</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="7"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 90px"> 出库单号</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="8"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 130px">审核时间</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="9"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >审核人</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="10" sortedby="asc"  onclick="sortForTableBlock('clearStart');">单据状态</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="11"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 200px">备注</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=true index="12" sortedby="asc"  onclick="sortForTableBlock('clearStart');">入出差额</td>
                    </tr>


                </table>

            </div>
            <!-- <DIV id="clearStart" align="center" style="position:relative; left:20; width:95%; height:30%;min-height: 200px;max-height: 250px; layer-background-color:#99CC99; border:thin outset #ffffff; z-index:1; overflow: scroll;">-->
            <div id="clearStart"  class="divForTableBlockData" style="width: 121%">
                <table class="table_list_block" id="DataTable" >

                    <c:forEach items="${ResultSet}" var="rs">
                        <!--class="listTableData" -->
                        <!--
                        setSelectValuesByRow('detailOp', 'd_stationID', 'commonVariable');
                        -->
                        <tr class="table_list_tr_data"  onMouseOver="overResultRow('detailOp', this);" 
                            onMouseOut="outResultRow('detailOp', this);" 
                            onclick="controlsByFlagAndMul('detailOp', ['audit']);
                                    clickResultRow('detailOp', this, 'detail');
                                    controlsDisableForMulRecords('detailOp', ['del']);
                                    setPageControl('detailOp');" 
                            id="${rs.bill_no}" flag="${rs.record_flag}">

                            <td id="rectNo1" class="table_list_tr_col_data_block">
                                <input type="checkbox" name="rectNo" id="rectNo" onclick="unSelectAllRecord('detailOp', 'rectNoAll', 'rectNo');"
                                       value="${rs.bill_no}"  flag="${rs.record_flag}">

                                </input>
                            </td>
                            <td  id="billNo" class="table_list_tr_col_data_block" style="width: 90px">
                                <a href='#'
                                   onClick="openwindow('ticketStorageInCleanManage?queryCondition=${rs.bill_no}&command=queryDetail&operType=planDetail&billRecordFlag=${rs.record_flag}&ModuleID=${ModuleID}', '', 1200, 800)">
                                    <!--  <a href='#' onClick="window.showModelessDialog('ticketStorageOutProduceManage.do?queryCondition={billNo}&amp;command=queryDetail&amp;operType=planDetail&amp;ModuleID={/Service/Result/ModuleID}','0','dialogWidth:700px;dialogHeight:500px;center:yes;resizable:no;status:no;scroll:no')"> --> 
                                    ${rs.bill_no}
                                </a>

                            </td>
                            <td  id="bill_date" class="table_list_tr_col_data_block" style="width: 130px">

                                ${rs.bill_date}

                            </td>
                            <td  id="form_maker" class="table_list_tr_col_data_block" >
                                ${rs.form_maker}
                            </td>
                            <td  id="hand_man" class="table_list_tr_col_data_block">
                                ${rs.hand_man}
                            </td>
                            <td  id="administer" class="table_list_tr_col_data_block">
                                ${rs.administer}
                            </td>
                            <td  id="accounter" class="table_list_tr_col_data_block">
                                ${rs.accounter}
                            </td>
                            <td  id="related_bill_no" class="table_list_tr_col_data_block" style="width: 90px">
                                ${rs.related_bill_no}
                            </td>
                            <td  id="verify_date" class="table_list_tr_col_data_block" style="width: 130px">
                                ${rs.verify_date}
                            </td>
                            <td  id="verify_person" class="table_list_tr_col_data_block" >
                                ${rs.verify_person}
                            </td>

                            <td  id="record_flag" class="table_list_tr_col_data_block" >
                                ${rs.record_flag_name}
                            </td>
                            <td  id="remarks" class="table_list_tr_col_data_block" style="width: 200px">
                                ${rs.remark}
                            </td>
                            <td  id="out_in_diff" class="table_list_tr_col_data_block">

                                <c:if test = "${rs.out_in_diff != 0}">
                                    <a href='#'
                                       onClick="openwindow('ticketStorageInCleanManage?queryCondition_in=${rs.bill_no}&queryCondition_out=${rs.related_bill_no}&command=queryDiff&operType=diff&billRecordFlag=${rs.record_flag}&ModuleID=${ModuleID}', '', 1200, 800)">
                                        <!--  <a href='#' onClick="window.showModelessDialog('ticketStorageOutProduceManage.do?queryCondition={billNo}&amp;command=queryDetail&amp;operType=planDetail&amp;ModuleID={/Service/Result/ModuleID}','0','dialogWidth:700px;dialogHeight:500px;center:yes;resizable:no;status:no;scroll:no')"> --> 
                                        ${rs.out_in_diff}
                                    </a>                                
                                </c:if>         

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

        <FORM method="post" name="detailOp" id="detailOp" action="ticketStorageInCleanManage" >

            <c:set var="divideShow" scope="request" value="1"/>
            <c:import  url="/jsp/common/common_template_web_variable.jsp?template_name=common_web_variable" />
            
            <input type="hidden" name="expAllFields" id="d_expAllFields"
                   value="getBill_no,getBill_date,getForm_maker,getHand_man,getAdminister,getAccounter,getRelated_bill_no,getVerify_date,getVerify_person,getRecord_flag_name,getRemark,getOut_in_diff" />
            <input type="hidden" name="expFileName" id="d_expFileName" value="清洗入库.xlsx" />
            <input type="hidden" name="divId" id="d_divId" value="clearStartHead">
            <input type="hidden" name="methodUrl" id="d_methodUrl" value="/TicketStorageInCleanExportAll"/>

            <!--<DIV id="detail" align="center" class="divForTableData" style="position:relative; left:20; width:95%; height:10; layer-background-color:#99CC99; border:thin outset #ffffff; z-index:1;">-->
            <div id="detail"  class="divForTableDataDetail" >
                <table  class="table_edit_detail">
                    <tr class="table_edit_tr">
                        <td class="table_edit_tr_td_label">出库单号: </td>
                        <td class="table_edit_tr_td_input">
                            <select id="d_related_bill_no" name="d_related_bill_no" dataType="Require" msg="出库单号不能为空!" >
                                <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_cleanOutBill" />
                            </select>
                        </td>
                        <td class="table_edit_tr_td_label">交票人: </td>
                        <td class="table_edit_tr_td_input">
                            <input type="text" id="d_hand_man" name="d_hand_man" size="8"   dataType="Required|LimitContainChinese" maxlength="45" max="45" msg="交票人不能为空,且长度不超过45个字节，或15个汉字"/>
                        </td>
                        <td class="table_edit_tr_td_label">车票管理员: </td>
                        <td class="table_edit_tr_td_input">
                            <input type="text" name="d_administer" id="d_administer" size="10" require="true" maxlength="45"dataType="NotEmpty|LimitContainChinese" min="1" max="45" msg="车票管理员不能为空,且长度不超过45个字节，或15个汉字"/>
                        </td>

                    </tr>
                    <tr class="table_edit_tr">
                        <td class="table_edit_tr_td_label">记帐人: </td>
                        <td class="table_edit_tr_td_input">
                            <input type="text" name="d_accounter" id="d_accounter" size="10" require="true" maxlength="45"dataType="NotEmpty|LimitContainChinese" min="1" max="45" msg="记账员不能为空,且长度不超过45个字节,或15个汉字"/>
                        </td>
                        <td class="table_edit_tr_td_label">备注:</td>
                        <td class="table_edit_tr_td_input">
                            <input type="text" name="d_remarks" id="d_remarks" size="50" require="false" maxlength="256" dataType="LimitContainChinese" min="1" max="256" msg="备注最大长度为256个字节,或85个汉字"/>
                        </td>

                    <input type="hidden" name="d_out_in_diff" id="d_out_in_diff" size="1"  maxlength="10" readonly="true" />

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
             <c:set var="expAll" scope="request" value="1"/>
            <c:set var="btNext" scope="request" value="1"/>
            <c:set var="btNextEnd" scope="request" value="1"/>
            <c:set var="btBack" scope="request" value="1"/>
            <c:set var="btBackEnd" scope="request" value="1"/>
            <c:set var="btGoPage" scope="request" value="1"/>



            <c:set var="clickMethod" scope="request" value="submitForm('detailOp')"/>
            <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all" />
            <br/>
        </FORM>
        <!--
        <c:set var="addQueryMethod" scope="request" value="setLineCardNames('detailOp','d_storage','d_zone','commonVariable','','','');"/>
        <c:set var="addClickMethod" scope="request" value="setUpdatePrimaryKey('detailOp','d_lineID#d_stationID#d_deviceType#d_deviceID#Version');"/>
        -->

        
    </body>

    <script language="JavaScript">

        function submitForm(formName) {
            if (event.srcElement.name == "audit") {
                var rst = confirm("请确认全部入库已经完成！");
                if (!rst)
                    return;
            }
            auditOutInDiff(formName);
        }


    </script>
</html>
