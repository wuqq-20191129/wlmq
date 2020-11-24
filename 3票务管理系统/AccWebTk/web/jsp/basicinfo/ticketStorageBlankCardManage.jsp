<%-- 
    Document   : TicketStorageBlankCardManage
    Created on : 2017-8-3, 9:03:36
    Author     : xiaowu
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="X-UA-Compatible" content="IE=9,Chrome=1" />
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>空白卡订单</title>
        <script language="javascript" type="text/javascript" charset="utf-8" src="js/common_form.js"></script>
        <script language="javascript" type="text/javascript" charset="utf-8" src="js/common_form_extend.js"></script>
        <script language="javascript" type="text/javascript" charset="utf-8" src="js/Validator.js"></script>
        <script language="JavaScript" type="text/javascript" charset="utf-8" src="js/CalendarPop.js"></script>
        <script language="JavaScript" type="text/javascript" charset="utf-8" src="js/icProduceOut.js"></script>
        <script language="JavaScript" type="text/javascript" charset="utf-8" src="js/icStorageProductBill.js"></script>
        <link rel="stylesheet" type="text/css" href="css/frame/simple.css" title="Style"/>
        <script language="JavaScript" type="text/javascript">
            function disableInputByBlankCardTypeNew(formName) {
                var blankCardType = document.getElementById("d_blank_card_type").value;
                //普通单程值 00、往返票 01 未选择
                if (blankCardType == '00' || blankCardType == '01' || blankCardType == '03' || blankCardType == "") {
                    document.forms[formName].elements["d_start_logicno"].disabled = true;
                    document.forms[formName].elements["d_end_logicno"].disabled = true;
                    //document.forms[formName].elements["d_settCurrentStartLogicNo"].disabled=true;

//                            document.forms[formName].elements["d_start_logicno"].require="false";
//                            document.forms[formName].elements["d_start_logicno"].dataType="";
                } else {
                    document.forms[formName].elements["d_start_logicno"].disabled = false;
                    document.forms[formName].elements["d_end_logicno"].disabled = true;
                    //document.forms[formName].elements["d_settCurrentStartLogicNo"].disabled=false;

//                            document.forms[formName].elements["d_start_logicno"].require="true";
//                            document.forms[formName].elements["d_start_logicno"].dataType="Integer|Limit";
                }
            }

            function disableInputByBlankCardbtnClickNew(formName) {
                if (event.srcElement.name == "add" || event.srcElement.name == "modify" || event.srcElement.name == "save") {
                    var blankCardType = document.getElementById("d_blank_card_type").value;
                    //普通单程值 00、往返票 01 未选择
                    if (blankCardType == "" || blankCardType === undefined || blankCardType == '00' || blankCardType == '01' || blankCardType == '03') {
                         document.forms[formName].elements["d_bill_no"].disabled = true;
                        document.forms[formName].elements["d_start_logicno"].disabled = true;
                        document.forms[formName].elements["d_end_logicno"].disabled = true;
                        //document.forms[formName].elements["d_settCurrentStartLogicNo"].disabled=true;

//                            document.forms[formName].elements["d_start_logicno"].require="false";
                    } else {
                        document.forms[formName].elements["d_bill_no"].disabled = true;
                        document.getElementById("d_start_logicno").disabled = false;
                        document.getElementById("d_end_logicno").disabled = true;
                        //document.forms[formName].elements["d_settCurrentStartLogicNo"].disabled=false;
//                            document.getElementById("d_start_logicno").require="true";
                    }
                }
            }
        </script>
    </head>
    <body onload="initDocument('queryOp', 'detail');
            initDocument('detailOp', 'detail');
            setControlsDefaultValue('queryOp');
            setPrimaryKeys('detailOp', 'd_bill_no#d_end_logicno');
            setListViewDefaultValue('detailOp', 'clearStart');
            setQueryControlsDefaultValue('queryOp', 'detailOp');
            setPageControl('detailOp');
            setTableRowBackgroundBlock('DataTable');">

        <table  class="table_title">
            <tr align="center" class="trTitle">
                <td colspan="4">空白卡订单
                </td>
            </tr>
        </table>

        <!-- 表头 通用模板 -->
        <c:set var="pTitleName" scope="request" value="查询"/>
        <c:set var="pTitleWidth" scope="request" value="50"/>
        <c:import  url="/jsp/common/common_template.jsp?template_name=common_table_title" />

        <form method="post" name="queryOp" id="queryOp" action="ticketStorageBlankCardManage"  style="margin-bottom: 3px;">
            <!-- 页面用到的变量 通用模板 -->

            <c:set var="divideShow" scope="request" value="1"/>
            <c:import  url="/jsp/common/common_template_web_variable.jsp?template_name=common_web_variable" />

            <table  class="table_edit">
                <tr class="table_edit_tr">
                    <td class="table_edit_tr_td_label" >单号:</td>
                    <td class="table_edit_tr_td_input">
                        <input type="text" name="q_bill_no" id="q_bill_no" size="12" maxlength="12" require="false" dataType="NumAndEng" msg="单号应为数字和英文组合" />
                    </td>
                    <td class="table_edit_tr_td_label" >年份:</td>
                    <td class="table_edit_tr_td_input">
                        <input type="text" name="q_year" id="q_year" size="4"  require="false" min="4" max="4" 
                               minlength="4" maxlength="4" dataType="Integer|Limit" msg="年份应为4位数字" />
                    </td>
                    <td class="table_edit_tr_td_label" >批次:</td>
                    <td class="table_edit_tr_td_input">
                        <input type="text" name="q_batch_no" id="q_batch_no" size="2" maxlength="2" require="false" dataType="Integer" msg="批次应为2位数字" />
                    </td>
                    <td class="table_edit_tr_td_label" >票卡类型:</td>
                    <td class="table_edit_tr_td_input">
                        <select id="q_blank_card_type" name="q_blank_card_type">				    			    
                            <!-- 空白卡订单 票卡类型 -->
                            <option value="">=请选择=</option>
                            <c:forEach items="${logicCardTypes}" var="logicCardTypes"> 
                                <c:choose>
                                    <c:when test="${logicCardTypes.remark == ''}">
                                        <option value="${logicCardTypes.blank_card_type}">${logicCardTypes.blank_card_type}</option>
                                    </c:when>
                                    <c:otherwise>
                                        <option value="${logicCardTypes.blank_card_type}" <c:if test="${logicCardTypes.blank_card_type eq d_blank_card_type}">selected = "selected"</c:if>>${logicCardTypes.remark}</option>
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>
                        </select>
                    </td>
                </tr>
                <tr class="table_edit_tr">
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
                    <td class="table_edit_tr_td_label" >单据状态:</td>
                    <td class="table_edit_tr_td_input">
                        <select id="q_record_flag" name="q_record_flag" >				    
                            <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_record_flag"/>
                        </select>
                    </td>
                    <td class="table_edit_tr_td_label" >使用状态:</td>
                    <td class="table_edit_tr_td_input">
                        <select id="q_is_used" name="q_is_used">				    
                            <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_is_used_flag" />
                        </select>
                    </td>
                </tr>
                <tr class="table_edit_tr">       
                    <td class="table_edit_tr_td_label" >生产厂商:</td>
                    <td class="table_edit_tr_td_input">
                        <select id="q_produce_factory_id" name="q_produce_factory_id" style="width: 150px;">				    
                            <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_produce_factory" />
                        </select>
                    </td>
                    <td class="table_edit_tr_td_label" >应用标识:</td>
                    <td class="table_edit_tr_td_input">
                        <select id="q_app_identifier" name="q_app_identifier">			    
                            <!-- 空白卡订单 应用标识 -->
                            <option value="">=请选择=</option>
                            <c:forEach items="${appIdentifiers}" var="appIdentifiers"> 
                                <c:choose>
                                    <c:when test="${appIdentifiers.code_text == ''}">
                                        <option value="${appIdentifiers.code}">${appIdentifiers.code}</option>
                                    </c:when>
                                    <c:otherwise>
                                        <option value="${appIdentifiers.code}" <c:if test="${appIdentifiers.code eq d_app_identifier}">selected = "selected"</c:if>>${appIdentifiers.code_text}</option>
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>
                        </select>
                    </td>

                    <td class="table_edit_tr_td_label" rowspan="4">
                        <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all_init" />
                        <c:set var="query" scope="request" value="1"/>
                        <c:set var="addClickMethod" scope="request" value="setControlNames('queryOp','q_bill_no#q_year#q_batch_no#q_produce_factory_id#q_blank_card_type#q_beginTime#q_endTime#q_record_flag#q_is_used#q_app_identifier');"/>
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
            <div id="clearStartHead" class="divForTableBlockHead" style="width: 198%;">
                <table class="table_list_block" id="DataTableHead" >

                    <!--说明：列的序号从0开始 isDigit：false 表示按字符串排序 ：true表示按数值排序 sortedby：asc表示按升序 dec表示按降序 -->
                    <tr  class="table_list_tr_head_block" id="ignore">
                        <td   class="table_list_tr_col_head_block">
                            <input type="checkbox" name="rectNoAll" id="rectNoAll" 
                                   onclick="selectAllRecord('detailOp', 'rectNoAll', 'rectNo', 'clearStart', 0);
                                           controlsByFlag('detailOp', ['del', 'modify']);
                                           controlsDisableForMulRecords('detailOp', ['del', 'modify']);
                                           controlsByFlagAndMul('detailOp', ['audit']);
                                           setPageControl('detailOp');"/>
                        </td>	
                        <td id="orderTd" class="table_list_tr_col_head_block"  isDigit=false index="1"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 100px;">单号</td>
                        <td id="orderTd" class="table_list_tr_col_head_block"  isDigit=false index="2"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 80px;">单据状态</td>
                        <td id="orderTd" class="table_list_tr_col_head_block"  isDigit=false index="3"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 130px;">制单时间</td>
                        <td id="orderTd" class="table_list_tr_col_head_block"  isDigit=false index="4" sortedby="asc"  onclick="sortForTableBlock('clearStart');" style="width: 50px;">年份</td>
                        <td id="orderTd" class="table_list_tr_col_head_block"  isDigit=false index="5" sortedby="asc"  onclick="sortForTableBlock('clearStart');" style="width: 50px;">批次</td>
                        <td id="orderTd" class="table_list_tr_col_head_block"  isDigit=false index="6" sortedby="asc"  onclick="sortForTableBlock('clearStart');" style="width: 60px;">生产厂商</td>
                        <td id="orderTd" class="table_list_tr_col_head_block"  isDigit=false index="7" sortedby="asc"  onclick="sortForTableBlock('clearStart');" style="width: 100px;">票卡类型</td>
                        <td id="orderTd" class="table_list_tr_col_head_block"  isDigit=false index="8" sortedby="asc"  onclick="sortForTableBlock('clearStart');" style="width: 50px;">数量</td>
                        <td id="orderTd" class="table_list_tr_col_head_block"  isDigit=false index="8" sortedby="asc"  onclick="sortForTableBlock('clearStart');" style="width: 80px;">城市代码</td>
                        <td id="orderTd" class="table_list_tr_col_head_block"  isDigit=false index="8" sortedby="asc"  onclick="sortForTableBlock('clearStart');" style="width: 80px;">行业代码</td>
                        <td id="orderTd" class="table_list_tr_col_head_block"  isDigit=false index="8" sortedby="asc"  onclick="sortForTableBlock('clearStart');" style="width: 70px;">应用标识</td>
                        <td id="orderTd" class="table_list_tr_col_head_block"  isDigit=false index="9" sortedby="asc"  onclick="sortForTableBlock('clearStart');" style="width: 80px;">开始序列号</td>
                        <td id="orderTd" class="table_list_tr_col_head_block"  isDigit=false index="10" sortedby="asc"  onclick="sortForTableBlock('clearStart');" style="width: 80px;">结束序列号</td>
                        <td id="orderTd" class="table_list_tr_col_head_block"  isDigit=false index="9" sortedby="asc"  onclick="sortForTableBlock('clearStart');" style="width: 130px;">开始逻辑卡号</td>
                        <td id="orderTd" class="table_list_tr_col_head_block"  isDigit=false index="10" sortedby="asc"  onclick="sortForTableBlock('clearStart');" style="width: 130px;">结束逻辑卡号</td>
                        <td id="orderTd" class="table_list_tr_col_head_block"  isDigit=false index="11" sortedby="asc"  onclick="sortForTableBlock('clearStart');" style="width: 60px;">制单人</td>
                        <td id="orderTd" class="table_list_tr_col_head_block"  isDigit=false index="12" sortedby="asc"  onclick="sortForTableBlock('clearStart');" style="width: 130px;">审核时间</td>
                        <td id="orderTd" class="table_list_tr_col_head_block"  isDigit=false index="13" sortedby="asc"  onclick="sortForTableBlock('clearStart');" style="width: 60px;">审核人</td>
                        <td id="orderTd" class="table_list_tr_col_head_block"  isDigit=false index="14" sortedby="asc"  onclick="sortForTableBlock('clearStart');" style="width: 70px;">使用状态</td>
                        <td id="orderTd" class="table_list_tr_col_head_block"  isDigit=false index="15" sortedby="asc"  onclick="sortForTableBlock('clearStart');" style="width: 70px;">文件前缀</td>
                        <td id="orderTd" class="table_list_tr_col_head_block"  isDigit=false index="16" sortedby="asc"  onclick="sortForTableBlock('clearStart');" style="width: 160px;">备注</td>
                    </tr>
                </table>
            </div> 
            <!-- <DIV id="clearStart" align="center" style="position:relative; left:20; width:95%; height:30%;min-height: 200px;max-height: 250px; layer-background-color:#99CC99; border:thin outset #ffffff; z-index:1; overflow: scroll;">-->
            <div id="clearStart"  class="divForTableBlockData" style="width: 198%;">
                <table class="table_list_block" id="DataTable" >

                    <c:forEach items="${ResultSet}" var="rs">
                        <tr class="table_list_tr_data"  onMouseOver="overResultRow('detailOp', this);" 
                            onMouseOut="outResultRow('detailOp', this);" 
                            onclick="clickResultRow('detailOp', this, 'detail');
                                    
                                    
                                    controlsDisableForMulRecords('detailOp', ['del', 'modify']);
                                    controlsByFlagAndMul('detailOp', ['audit']);
                                    setPageControl('detailOp');" 
                            id="${rs.bill_no}#${rs.produce_factory_id}#${batch_no}"
                            flag="${rs.record_flag}">

                            <td id="rectNo1" class="table_list_tr_col_data_block">
                                <input type="checkbox" name="rectNo" id="rectNo" 
                                       onclick="unSelectAllRecord('detailOp', 'rectNoAll', 'rectNo');
                                               
                                               controlsDisableForMulRecords('detailOp', ['del', 'modify']);
                                               controlsByFlagAndMul('detailOp', ['audit']);"
                                       value="${rs.bill_no}" flag="${rs.record_flag}">
                                </input>
                            </td>

                            <td  id="bill_no" class="table_list_tr_col_data_block" style="width: 100px;">
                                ${rs.bill_no}
                            </td>
                            <td  id="record_flag" class="table_list_tr_col_data_block" style="width: 80px;">
                                ${rs.record_flag_text}
                            </td>
                            <td  id="bill_date" class="table_list_tr_col_data_block" style="width: 130px;">
                                ${rs.bill_date}
                            </td>
                            <td  id="year" class="table_list_tr_col_data_block" style="width: 50px;">
                                ${rs.year}
                            </td>
                            <td  id="batch_no" class="table_list_tr_col_data_block" style="width: 50px;">
                                ${rs.batch_no}
                            </td>
                            <td  id="produce_factory_id" class="table_list_tr_col_data_block" style="width: 60px;">
                                ${rs.produce_factory_text}
                            </td>
                            <td  id="blank_card_type" class="table_list_tr_col_data_block" style="width: 100px;">
                                ${rs.blank_card_type_text}
                            </td>
                            <td  id="qty" class="table_list_tr_col_data_block" style="width: 50px;">
                                ${rs.qty}
                            </td>
                            <td  id="city_code" class="table_list_tr_col_data_block" style="width: 80px;">
                                ${rs.city_code_text}
                            </td>
                            <td  id="industry_code" class="table_list_tr_col_data_block" style="width: 80px;">
                                ${rs.industry_code_text}
                            </td>
                            <td  id="app_identifier" class="table_list_tr_col_data_block" style="width: 70px;">
                                ${rs.app_identifier_text}
                            </td>
                            <td  id="start_logicno" class="table_list_tr_col_data_block" style="width: 80px;">
                                ${rs.start_logicno}
                            </td>
                            <td  id="end_logicno" class="table_list_tr_col_data_block" style="width: 80px;">
                                ${rs.end_logicno}
                            </td>
                            <td  id="start_logic_all" class="table_list_tr_col_data_block" style="width: 130px;">
                                ${rs.start_logic_all}
                            </td>
                            <td  id="end_logic_all" class="table_list_tr_col_data_block" style="width: 130px;">
                                ${rs.end_logic_all}
                            </td>
                            <td  id="form_maker" class="table_list_tr_col_data_block" style="width: 60px;">
                                ${rs.form_maker}
                            </td>
                            <td  id="verify_date" class="table_list_tr_col_data_block" style="width: 130px;">
                                ${rs.verify_date}
                            </td>
                            <td  id="verify_person" class="table_list_tr_col_data_block" style="width: 60px;">
                                ${rs.verify_person}
                            </td>
                            <td  id="is_used" class="table_list_tr_col_data_block" style="width: 70px;">
                                ${rs.is_used_text}
                            </td>
                            <td  id="file_prefix" class="table_list_tr_col_data_block" style="width: 70px;">
                                ${rs.file_prefix}
                            </td>
                            <td  id="remark" class="table_list_tr_col_data_block" style="width: 160px;">
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

        <FORM method="post" name="detailOp" id="detailOp" action="ticketStorageBlankCardManage" >

            <c:set var="divideShow" scope="request" value="1"/>
            <c:import  url="/jsp/common/common_template_web_variable.jsp?template_name=common_web_variable" />
            <input type="hidden" name="expAllFields" id="d_expAllFields"
                   value="getBill_no,getRecord_flag_text,getBill_date,getYear,getBatch_no,getProduce_factory_text,getBlank_card_type_text,getQty,getCity_code_text,getIndustry_code_text,getApp_identifier_text,getStart_logicno,getEnd_logicno,getStart_logic_all,getEnd_logic_all,getForm_maker,getVerify_date,getVerify_person,getIs_used_text,getRemark,getFile_prefix" />
            <input type="hidden" name="expFileName" id="d_expFileName" value="空白卡订单.xlsx" />
            <input type="hidden" name="divId" id="d_divId" value="clearStartHead">
            <input type="hidden" name="methodUrl" id="d_methodUrl" value="/ticketStorageBlankCardManageExportAll"/>

            <div id="detail" name="detail" class="divForTableDataDetail" >
                <table  class="table_edit_detail">
                    <tr class="table_edit_tr">
                        <td class="table_edit_tr_td_label">单号:</td>
                        <td class="table_edit_tr_td_input">
                            <input type="text" name="d_bill_no" id="d_bill_no" size="20"  require="false" readOnly="true"  value="${d_bill_no}"/>	
                        </td>
                        <td class="table_edit_tr_td_label">年份:</td>
                        <td class="table_edit_tr_td_input">
                            <input type="text" name="d_year" id="d_year" size="4"  require="true" min="4" max="4" 
                                   minlength="4" maxlength="4" dataType="Integer|Limit" msg="年份应为4位数字" value="${d_year}"/>
                        </td>

                        <td class="table_edit_tr_td_label">生产厂商:</td>
                        <td class="table_edit_tr_td_input">
                            <select id="d_produce_factory_id" name="d_produce_factory_id" require="true" dataType="NotEmpty" msg="生产厂商不能为空">				    
                                <!-- 生产厂商 -->
                                <option value="">=请选择=</option>
                                <c:forEach items="${produceFactorys}" var="produceFactorys"> 
                                    <c:choose>
                                        <c:when test="${produceFactorys.code_text == ''}">
                                            <option value="${produceFactorys.code}">${produceFactorys.code}</option>
                                        </c:when>
                                        <c:otherwise>
                                            <option value="${produceFactorys.code}" <c:if test="${produceFactorys.code eq d_produce_factory_id}">selected = "selected"</c:if>>${produceFactorys.code_text}</option>
                                        </c:otherwise>
                                    </c:choose>
                                </c:forEach>
                            </select>
                        </td>
                    </tr>
                    <tr class="table_edit_tr">
                        <td class="table_edit_tr_td_label">票卡类型:</td>
                        <td class="table_edit_tr_td_input">
                            <select id="d_blank_card_type" name="d_blank_card_type"  require="true" dataType="NotEmpty"
                                    msg="票卡类型不能为空" onChange="disableInputByBlankCardTypeNew('detailOp');">				    
                                <!-- 空白卡订单 票卡类型 -->
                                <option value="">=请选择=</option>
                                <c:forEach items="${logicCardTypes}" var="logicCardTypes"> 
                                    <c:choose>
                                        <c:when test="${logicCardTypes.remark == ''}">
                                            <option value="${logicCardTypes.blank_card_type}">${logicCardTypes.blank_card_type}</option>
                                        </c:when>
                                        <c:otherwise>
                                            <option value="${logicCardTypes.blank_card_type}" <c:if test="${logicCardTypes.blank_card_type eq d_blank_card_type}">selected = "selected"</c:if>>${logicCardTypes.remark}</option>
                                        </c:otherwise>
                                    </c:choose>
                                </c:forEach>
                            </select>
                        </td>
                        <td class="table_edit_tr_td_label">开始序列号:</td>
                        <td class="table_edit_tr_td_input">
                            <input type="text" name="d_start_logicno" id="d_start_logicno"  size ="9" dataType="NotEmptyForBlank|LimitContainForBlank" min="9" max="9"  maxlength="9" value="${d_start_logicno}" msg="开始序列号应为9位数字"/>
                        </td>
                        <td class="table_edit_tr_td_label">结束序列号:</td>
                        <td class="table_edit_tr_td_input">
                            <input type="text" name="d_end_logicno" id="d_end_logicno" size="8" disabled="disabled" value="${d_end_logicno}"/>
                        </td>
                    </tr>
                    <tr class="table_edit_tr">
                        <td class="table_edit_tr_td_label">城市代码:</td> 
                        <td class="table_edit_tr_td_input">
                            <select id="d_city_code" name="d_city_code"  require="true" dataType="NotEmpty"
                                    msg="城市代码不能为空">				    
                                <option value="">=请选择=</option>
                                <c:forEach items="${cityCodes}" var="cityCodes"> 
                                    <c:choose>
                                        <c:when test="${cityCodes.code_text == ''}">
                                            <option value="${cityCodes.code}">${cityCodes.code}</option>
                                        </c:when>
                                        <c:otherwise>
                                            <option value="${cityCodes.code}" <c:if test="${cityCodes.code eq d_city_code}">selected = "selected"</c:if>>${cityCodes.code_text}</option>
                                        </c:otherwise>
                                    </c:choose>
                                </c:forEach>
                            </select>
                        </td>
                        <td class="table_edit_tr_td_label">行业代码:</td>
                        <td class="table_edit_tr_td_input">
                            <select id="d_industry_code" name="d_industry_code"  require="true" dataType="NotEmpty"
                                    msg="行业代码不能为空">				    
                                <!-- 空白卡订单 行业代码 -->
                                <option value="">=请选择=</option>
                                <c:forEach items="${industryCodes}" var="industryCodes"> 
                                    <c:choose>
                                        <c:when test="${industryCodes.code_text == ''}">
                                            <option value="${industryCodes.code}">${industryCodes.code}</option>
                                        </c:when>
                                        <c:otherwise>
                                            <option value="${industryCodes.code}" <c:if test="${industryCodes.code eq d_industry_code}">selected = "selected"</c:if>>${industryCodes.code_text}</option>
                                        </c:otherwise>
                                    </c:choose>
                                </c:forEach>
                            </select>
                        </td>
                        <td class="table_edit_tr_td_label">应用标识:</td>
                        <td class="table_edit_tr_td_input">
                            <select id="d_app_identifier" name="d_app_identifier"  require="true" dataType="NotEmpty"
                                    msg="应用标识不能为空">				    
                                <!-- 空白卡订单 应用标识 -->
                                <option value="">=请选择=</option>
                                <c:forEach items="${appIdentifiers}" var="appIdentifiers"> 
                                    <c:choose>
                                        <c:when test="${appIdentifiers.code_text == ''}">
                                            <option value="${appIdentifiers.code}">${appIdentifiers.code}</option>
                                        </c:when>
                                        <c:otherwise>
                                            <option value="${appIdentifiers.code}" <c:if test="${appIdentifiers.code eq d_app_identifier}">selected = "selected"</c:if>>${appIdentifiers.code_text}</option>
                                        </c:otherwise>
                                    </c:choose>
                                </c:forEach>
                            </select>
                        </td>
                    </tr>
                    <tr class="table_edit_tr">
                        <td class="table_edit_tr_td_label">数量:</td> 
                        <td class="table_edit_tr_td_input">
                            <input type="text" name="d_qty" id="d_qty" size="6" require="true" minlength="1" maxlength="6" min="1" 
                                   max="999999" dataType="Integer|Range" msg="数量应为正整数类型" value="${d_qty}"/>
                        </td>
                        <td class="table_edit_tr_td_label">备注:</td>
                        <td class="table_edit_tr_td_input" colspan="3">
                            <input type="text" name="d_remark" id="d_remark" size="80"  maxlength="400" dataType="LimitB" max="400" msg="备注长度不超过200个字节" value="${d_remark}"/>
                        </td>
                    </tr>
                </table>
            </div>

            <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all_init" />
            <c:set var="add" scope="request" value="1"/>
            <c:set var="modify" scope="request" value="1"/>
            <c:set var="del" scope="request" value="1"/>
            <c:set var="audit" scope="request" value="1"/>
            <c:set var="save" scope="request" value="1"/>
            <c:set var="cancle" scope="request" value="1"/>
            <c:set var="print" scope="request" value="1"/>
            <c:set var="export" scope="request" value="1"/>
            <c:set var="expAll" scope="request" value="1"/>
            <c:set var="btNext" scope="request" value="1"/>
            <c:set var="btNextEnd" scope="request" value="1"/>
            <c:set var="btBack" scope="request" value="1"/>
            <c:set var="btBackEnd" scope="request" value="1"/>
            <c:set var="btGoPage" scope="request" value="1"/>

            <c:set var="clickMethod" scope="request" value="btnClick('detailOp','clearStart','detail','','clearStartHead');disableInputByBlankCardbtnClickNew('detailOp');"/>
            <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all" />
            <br/>
        </FORM>
    </body>
</html>
