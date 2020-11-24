<%-- 
    Document   : produce_bill
    Created on : 2017-8-17
    Author     : moqf
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>生产工作单</title>


        <script language="javascript" type="text/javascript" charset="utf-8" src="js/common_form.js"></script>
        <script language="javascript" type="text/javascript" charset="utf-8" src="js/common_form_extend.js"></script>
        <script language="javascript" type="text/javascript" charset="utf-8" src="js/Validator.js"></script>
        <script language="JavaScript" type="text/javascript" charset="utf-8" src="js/CalendarPop.js"></script>
        <!--<script language="JavaScript" type="text/javascript" charset="utf-8" src="js/icProduceOut.js"></script>-->
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
    setPrimaryKeys('formOp','d_bill_id#d_out_bill#d_drawqulity#d_es_work#d_billdate#d_flag#d_sys_num#d_es_oper#d_valid');
    setControlsDefaultValue('formQuery');
    setControlsDefaultValue('formOp');
    setListViewDefaultValue('formQuery','clearStart');
    setListViewDefaultValue('formOp','clearStart');
    setQueryControlsDefaultValue('formQuery','formOp');
    -->
    <body onload="
        initDocument('queryOp', 'detail');
            initDocument('detailOp', 'detail');
            setControlsDefaultValue('queryOp');
            setListViewDefaultValue('detailOp', 'clearStart');
            setQueryControlsDefaultValue('queryOp', 'detailOp');
            setPageControl('detailOp');
            setTableRowBackgroundBlock('DataTable');
//            setPrimaryKeys('detailOp','d_bill_no#d_out_bill_no#d_draw_total#d_es_workType_id#d_bill_date#d_record_flag#d_system_balance#d_es_operator#d_valid_num');
            setPrimaryKeys('detailOp','d_bill_no#d_out_bill_no#d_draw_total#d_es_workType_id#d_bill_date#d_record_flag#d_system_balance#d_es_operator#d_valid_num#d_es_useless_num');
                "
            >

        <!--<body >-->
        <table  class="table_title">
            <tr align="center" class="trTitle">
                <td colspan="4">生产工作单
                </td>
            </tr>
        </table>

        <!-- 表头 通用模板 -->

        <c:set var="pTitleName" scope="request" value="查询"/>
        <c:set var="pTitleWidth" scope="request" value="50"/>
        <c:import  url="/jsp/common/common_template.jsp?template_name=common_table_title" />

        <form method="post" name="queryOp" id="queryOp" action="ticketStorageProduceBill">
            <!-- 页面用到的变量 通用模板 -->

            <c:set var="divideShow" scope="request" value="1"/>
            <c:import  url="/jsp/common/common_template_web_variable.jsp?template_name=common_web_variable" />

            <table  class="table_edit">
                <tr class="table_edit_tr">
                    <td class="table_edit_tr_td_label">生产单号:</td>
                    <td class="table_edit_tr_td_input">
                        <input type="text" id="q_billNo" name="q_billNo" value="" size="12" maxlength="12"  require="false" dataType="LimitB" min="12" max="12"  msg="生产单号应为12位" />
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
                    <td class="table_edit_tr_td_label">单据状态:</td>
                    <td class="table_edit_tr_td_input">
                        <select id="q_recordFlag" name="q_recordFlag" >
                            <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_billstatues" />
                        </select>
                    </td>
                    
                    <td class="table_edit_tr_td_label">工作类型:</td>
                    <td class="table_edit_tr_td_input">
                        <select id="q_recordFlag" name="q_esWorkType" >
                            <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_worktype" />
                        </select>
                    </td>
                    

                    <td class="table_edit_tr_td_label" rowspan="1">

                        <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all_init" />
                        <c:set var="query" scope="request" value="1"/>
                        <!--setLineCardNames('queryOp','','','','q_cardMainCode','q_cardSubCode','commonVariable');-->
                        <c:set var="addClickMethod" scope="request" value="setControlNames('queryOp','q_billNo#q_operator#q_recordFlag#q_beginTime#q_endTime#q_esWorkType');"/>
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
            <div id="clearStartHead" class="divForTableBlockHead" style="width: 180%;">
                <table class="table_list_block" id="DataTableHead" >

                    <!--说明：列的序号从0开始 isDigit：false 表示按字符串排序 ：true表示按数值排序 sortedby：asc表示按升序 dec表示按降序 -->
                    <tr  class="table_list_tr_head_block" id="ignore">
                        <td   class="table_list_tr_col_head_block">
                            <input type="checkbox" name="rectNoAll" id="rectNoAll" onclick="selectAllRecord('detailOp', 'rectNoAll', 'rectNo', 'clearStart', 0);
//                                controlsByFlagAndMul('detailOp', ['audit']);
                                userPrivilegeWithCheckBoxByMul('detailOp',['modify','audit'],'${OperatorID}');
                                setPageControl('detailOp');"/>
                        </td>	
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="1"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 90px">遗失明细</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="2"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 120px">生产工作单</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="3"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 90px">出库单号</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=true index="4"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 120px">总领票数量</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=true index="5"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 120px" >有效票数量</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="6"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 120px" >E/S工作类型</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=true index="7"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 120px" > E/S废票数量</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=true index="8"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 120px" >人工废票</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=true index="9"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >遗失票</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=true index="10" sortedby="asc"  onclick="sortForTableBlock('clearStart');" style="width: 90px">系统结余</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=true index="11" sortedby="asc"  onclick="sortForTableBlock('clearStart');" style="width: 90px">实际结余</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="12" sortedby="asc"  onclick="sortForTableBlock('clearStart');" style="width: 90px">单据状态</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="13" sortedby="asc"  onclick="sortForTableBlock('clearStart');" style="width: 130px">制单时间</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="14" sortedby="asc"  onclick="sortForTableBlock('clearStart');">交票人</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="15" sortedby="asc"  onclick="sortForTableBlock('clearStart');">接票人</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="16" sortedby="asc"  onclick="sortForTableBlock('clearStart');" style="width: 90px">ES操作员</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="17" sortedby="asc"  onclick="sortForTableBlock('clearStart');" style="width: 90px">差额原因</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="18" sortedby="asc"  onclick="sortForTableBlock('clearStart');" style="width: 200px">备注</td>

                    </tr>


                </table>

            </div>
            <div id="clearStart"  class="divForTableBlockData" style="width: 180%;">
                <table class="table_list_block" id="DataTable" >

                    <c:forEach items="${ResultSet}" var="rs">
                        <!--class="listTableData" -->
                        <!--
                        setSelectValuesByRow('detailOp', 'd_stationID', 'commonVariable');
                        -->
                        <!--controlsByFlag('detailOp', ['audit']);-->
                                    <!--userPrivilegeByClickCurrentRow('detailOp','${OperatorID}','${rs.es_operator}','${rs.record_flag}');-->
                        <tr class="table_list_tr_data"  onMouseOver="overResultRow('detailOp', this);" 
                            onMouseOut="
                                outResultRow('detailOp', this);" 
                            onclick="
                                clickResultRow('detailOp', this, 'detail');
//                                    userPrivilegeWithCheckBox('detailOp','${OperatorID}','${rs.es_operator}','${rs.record_flag}');
                                    userPrivilegeWithCheckBoxByMul('detailOp',['modify','audit'],'${OperatorID}');
                                    disableControlByFlag('detailOp',  'modify', '${rs.record_flag}');
                                    controlsDisableForMulRecords('detailOp', ['modify']);
                                    setPageControl('detailOp');
                                        " 
                            id="${rs.bill_no}" >

<!--                                       userPrivilegeByClickCurrentRow('detailOp','${OperatorID}','${rs.es_operator}','${rs.record_flag}');-->
                            <td id="rectNo1" class="table_list_tr_col_data_block">
                                <input type="checkbox" name="rectNo" id="rectNo" onclick="
                                    unSelectAllRecord('detailOp', 'rectNoAll', 'rectNo');
//                                    controlsByFlag('detailOp', ['audit']);
//                                    userPrivilegeWithCheckBox('detailOp','${OperatorID}','${rs.es_operator}','${rs.record_flag}');
                                          "
                                       value="${rs.bill_no}" flag="${rs.record_flag}" es_operator="${rs.es_operator}" >

                                </input>
                            </td>
                            <td  id="lostdetail" class="table_list_tr_col_data_block" style="width: 90px">
                                <c:if test="${rs.man_useless_num>0 or rs.es_useless_num>0 or rs.lost_num>0}">
                                <a href='#'
                                    onClick="openwindow('ticketStorageProduceBillLostDetail?queryCondition=${rs.bill_no}&billRecordFlag=${rs.record_flag}&ModuleID=${ModuleID}', '', 1200, 800)">
                                    废票遗失明细
                                </a>
                                </c:if>
                                <c:if test="${rs.man_useless_num ==0 and rs.es_useless_num==0 and rs.lost_num==0}">
                                    废票遗失明细
                                </c:if>

                            </td>
                            <td  id="bill_no" class="table_list_tr_col_data_block" style="width: 120px">
                                <a href='#'
                                   onClick="openwindow('ticketStorageProduceBillDetail?d_bill_no=${rs.bill_no}&ModuleID=${ModuleID}', '', 1300, 600)">
                                    ${rs.bill_no}
                                </a>

                            </td>
                            <td  id="out_bill_no" class="table_list_tr_col_data_block" style="width: 90px">
                                ${rs.out_bill_no}
                            </td>
                            <td  id="draw_total" class="table_list_tr_col_data_block" style="width: 120px">
                                ${rs.draw_total}
                            </td>
                            <td  id="valid_num" class="table_list_tr_col_data_block" style="width: 120px">
                                ${rs.valid_num}
                            </td>
                            <td  id="es_workType_id" class="table_list_tr_col_data_block" style="width: 120px">
                                ${rs.es_worktype_id_name}
                            </td>
                            <td  id="es_useless_num" class="table_list_tr_col_data_block" style="width: 120px">
                                ${rs.es_useless_num}
                            </td>
                            <td  id="man_useless_num" class="table_list_tr_col_data_block" style="width: 120px">
                                ${rs.man_useless_num}
                            </td>
                            <td  id="lost_num" class="table_list_tr_col_data_block" >
                                ${rs.lost_num}
                            </td>
                            <td  id="system_balance" class="table_list_tr_col_data_block" style="width: 90px">
                                ${rs.system_balance}
                            </td>
                            <td  id="real_balance" class="table_list_tr_col_data_block" style="width: 90px">
                                ${rs.real_balance}
                            </td>
                            <td  id="record_flag_name" class="table_list_tr_col_data_block" style="width: 90px">
                                ${rs.record_flag_name}
                            </td>
                            <td  id="bill_date" class="table_list_tr_col_data_block" style="width: 130px">
                                ${rs.bill_date}
                            </td>
                            <td  id="hand_man" class="table_list_tr_col_data_block">
                                ${rs.hand_man}
                            </td>
                            <td  id="receive_man" class="table_list_tr_col_data_block">
                                ${rs.receive_man}
                            </td>
                            <td  id="es_operator" class="table_list_tr_col_data_block" style="width: 90px">
                                ${rs.es_operator}
                            </td>
                            <td  id="diff_id" class="table_list_tr_col_data_block" style="width: 90px">
                                ${rs.diff_id_name}
                            </td>
                            <td  id="remarks" class="table_list_tr_col_data_block" style="width: 200px">
                                ${rs.remarks}
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

        <FORM method="post" name="detailOp" id="detailOp" action="ticketStorageProduceBill" >

            <c:set var="divideShow" scope="request" value="1"/>
            <c:import  url="/jsp/common/common_template_web_variable.jsp?template_name=common_web_variable" />
            
            <!--查询结果对应列，expAllFields的值为其实体类对应get方法，顺序一定要跟页面显示的顺序一致,不然导出列数据会混乱。-->
            <input type="hidden" name="expAllFields" id="d_expAllFields"
                value="getLost_detail_name,getBill_no,getOut_bill_no,getDraw_total,getValid_num,getEs_worktype_id_name,getEs_useless_num,getMan_useless_num,getLost_num,getSystem_balance,getReal_balance,getRecord_flag_name,getBill_date,getHand_man,getReceive_man,getEs_operator,getDiff_id_name,getRemarks" />
            <!--导出文件名字 生成的Excel sheet的名字也是这个-->
            <input type="hidden" name="expFileName" id="d_expFileName" value="生产工作单.xlsx" />
            <!--需要导出页面的表数据  一个页面多个导出全部根据这里设置详情参看运营系统退款模块-->
            <input type="hidden" name="divId" id="d_divId" value="clearStartHead">
            <!--对应你controller写的方法  一个页面多个导出全部根据这里设置详情参看运营系统退款模块-->
            <input type="hidden" name="methodUrl" id="d_methodUrl" value="/ticketStorageProduceBillExportAll"/>

            <!--<DIV id="detail" align="center" class="divForTableData" style="position:relative; left:20; width:95%; height:10; layer-background-color:#99CC99; border:thin outset #ffffff; z-index:1;">-->
            <div id="detail"  class="divForTableDataDetail" >
                <table  class="table_edit_detail">
                    <tr class="table_edit_tr">
                        <td class="table_edit_tr_td_label">生产工作单: </td>
                        <td class="table_edit_tr_td_input">
                            <input type="text"   name="d_bill_no" id="d_bill_no"  readonly="true"  require="true" dataType="String"/>
                        </td>
                        <td class="table_edit_tr_td_label">出库单号:</td>
                        <td class="table_edit_tr_td_input">
                            <input type="text" name="d_out_bill_no" id="d_out_bill_no" readonly="true" require="true"/>
                        </td>
                        <td class="table_edit_tr_td_label">领票数量:</td>
                        <td class="table_edit_tr_td_input">
                            <input type="text" id="d_draw_total" name="d_draw_total" readonly="true"  dataType="Require" msg="票区不能为空" />
                        </td>
                        <td class="table_edit_tr_td_label">有效票数:</td>
                        <td class="table_edit_tr_td_input">
                            <input type="text" id="d_valid_num" name="d_valid_num" require="true" readonly="true" maxlength="9" dataType="Integer" msg="票卡数量必须为整数"/>
                        </td>
                    </tr>
                    <tr class="table_edit_tr">
                        <td class="table_edit_tr_td_label">E/S工作类型:</td>
                        <td class="table_edit_tr_td_input">
                            <input type="text" id="d_es_workType_id" name = "d_es_workType_id" readonly="true" />
                        </td>
                        <td class="table_edit_tr_td_label">E/S废票数:</td>
                        <td class="table_edit_tr_td_input">
                            <!--<input type="text" id="d_es_useless_num" name="d_es_useless_num"  maxlength="9" dataType="Integer" require="true" msg="票卡数量必须为整数"/>-->
                            <input type="text" id="d_es_useless_num" name="d_es_useless_num"  readonly="true"/>
                        </td>
                        <td class="table_edit_tr_td_label">人工废票数:</td>
                        <td class="table_edit_tr_td_input">
                            <input type="text" id="d_man_useless_num" name="d_man_useless_num" require="true" maxlength="9" dataType="Integer" msg="票卡数量必须为整数"/>
                        </td>
                        <td class="table_edit_tr_td_label">遗失票数:</td>
                        <td class="table_edit_tr_td_input">
                            <input type="text" id="d_lost_num" name="d_lost_num" require="true" maxlength="9" dataType="Integer" msg="票卡数量必须为整数"/>
                        </td>

                    </tr>
                    <tr class="table_edit_tr">
                        <td class="table_edit_tr_td_label">系统结余</td>
                        <td class="table_edit_tr_td_input">
                            <input type="text" id="d_system_balance" name="d_system_balance"  readonly="true"/>
                        </td>
                        <td class="table_edit_tr_td_label">实际结余:</td>
                        <td class="table_edit_tr_td_input">
                            <input type="text" id="d_real_balance" name="d_real_balance" require="true" maxlength="9" dataType="Integer" msg="票卡数量必须为整数"/>
                        </td>
                        <td class="table_edit_tr_td_label">制单时间</td>
                        <td class="table_edit_tr_td_input">
                            <input type="text" id="d_bill_date" name="d_bill_date" readonly="true"/>
                        </td>
                        <td class="table_edit_tr_td_label">单据状态</td>
                        <td class="table_edit_tr_td_input">
                            <!--<input type="text" id="d_record_flag" name="d_record_flag" readonly="true"/>-->
                            <select id="d_record_flag" name="d_record_flag" readonly="true">
                                <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_billstatues" />
                            </select>
                        </td>
                    </tr>
                    <tr class="table_edit_tr">
                        <td class="table_edit_tr_td_label">交票人</td>
                        <td class="table_edit_tr_td_input">
                            <input type="text" id="d_hand_man" name="d_hand_man" dataType="LimitB" maxlength="15" min="1" max="15" msg="交票人长度不超过15个字符"/>
                        </td>
                        <td class="table_edit_tr_td_label">接票人</td>
                        <td class="table_edit_tr_td_input">
                            <input type="text" id="d_receive_man" name="d_receive_man" dataType="LimitB" maxlength="15" min="1" max="15" msg="交票人长度不超过15个字符"/>
                        </td>
                        <td class="table_edit_tr_td_label">ES操作员</td>
                        <td class="table_edit_tr_td_input">
                            <input type="text" id="d_es_operator" name="d_es_operator" readonly="true" />
                        </td>
                        <td class="table_edit_tr_td_label">差额原因</td>
                        <td class="table_edit_tr_td_input">
                            <!--<input type="text" id="d_diff_id" name="d_diff_id" readonly="true" />-->
                            <select id="d_diff_id" name="d_diff_id" >
                                <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_diffReason" />
                            </select>
                        </td>
                    </tr>
                    <tr class="table_edit_tr" colspan="4">
                        <td class="table_edit_tr_td_label">备注</td>
                        <td class="table_edit_tr_td_input" colspan="7">
                            <input type="text" id="d_remarks" name="d_remarks" size="108" dataType="LimitB" maxlength="300" min="1" max="300" msg="备注长度不超过300个字符"/>
                        </td>
                    </tr>

                </table>
            </div>






            <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all_init" />
            <c:set var="add" scope="request" value="0"/>
            <c:set var="modify" scope="request" value="1"/>
            <c:set var="save" scope="request" value="1"/>
            <c:set var="cancle" scope="request" value="1"/>
            <c:set var="audit" scope="request" value="1"/>
            <c:set var="print" scope="request" value="1"/>
            <c:set var="expAll" scope="request" value="1" />
            <c:set var="export" scope="request" value="1"/>
            <c:set var="btNext" scope="request" value="1"/>
            <c:set var="btNextEnd" scope="request" value="1"/>
            <c:set var="btBack" scope="request" value="1"/>
            <c:set var="btBackEnd" scope="request" value="1"/>


            <c:set var="addClickMethod" scope="request" value="setUpdatePrimaryKey('detailOp','d_bill_no');"/>
            <c:set var="clickMethod" scope="request" value="btnClick('detailOp','clearStart','detail','','clearStartHead');"/>
            <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all" />
            <br/>
        </FORM>

        
    </body>
</html>
