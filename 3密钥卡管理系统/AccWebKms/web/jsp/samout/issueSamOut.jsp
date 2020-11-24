<%-- 
    Document   : IssueSamOut
    Created on : 2017-8-29
    Author     : xiaowu
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>卡发行出库</title>

        <script language="javascript" type="text/javascript" charset="utf-8" src="js/common_form.js"></script>
        <script language="javascript" type="text/javascript" charset="utf-8" src="js/common_form_extend.js"></script>
        <script language="javascript" type="text/javascript" charset="utf-8" src="js/Validator.js"></script>
        <script language="JavaScript" type="text/javascript" charset="utf-8" src="js/CalendarPop.js"></script>
        <script language="JavaScript" type="text/javascript" charset="utf-8" src="js/icProduceOut.js"></script>
        <script language="JavaScript" type="text/javascript" charset="utf-8" src="js/icStorageProductBill.js"></script>
        <link rel="stylesheet" type="text/css" href="css/frame/simple.css" title="Style"/>
    </head>
    
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
                <td colspan="4">卡发行出库
                </td>
            </tr>
        </table>

        <!-- 表头 通用模板 -->

        <c:set var="pTitleName" scope="request" value="查询"/>
        <c:set var="pTitleWidth" scope="request" value="50"/>
        <c:import  url="/jsp/common/common_template.jsp?template_name=common_table_title" />

        <form method="post" name="queryOp" id="queryOp" action="issueSamOutAction" style="margin-bottom: 3px;">
            <!-- 页面用到的变量 通用模板 -->

            <c:set var="divideShow" scope="request" value="1"/>
            <c:import  url="/jsp/common/common_template_web_variable.jsp?template_name=common_web_variable" />

            <table  class="table_edit">
                <tr class="table_edit_tr">
                    <td class="table_edit_tr_td_label">出库单号:</td>
                    <td class="table_edit_tr_td_input">
                        <input type="text" id="q_order_no" name="q_order_no" value="" size="20" maxlength="20"
                            require="false" dataType="LimitB" min="1" max="20" msg="出库单号长度应为20位" />
                    </td>                 
                    <td class="table_edit_tr_td_label" >SAM卡类型:</td>
                    <td class="table_edit_tr_td_input">
                        <select id="q_sam_type" name="q_sam_type" >				    
                            <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_samtype" />
                        </select>
                    </td>
                    <td class="table_edit_tr_td_label">单据状态:</td>
                    <td class="table_edit_tr_td_input">
                        <select id="q_order_state" name="q_order_state" >
                            <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_billstatues" />
                        </select>
                    </td>
                    <td class="table_edit_tr_td_label">卡状态：</td>
                    <td class="table_edit_tr_td_input">
                        <select id = "q_is_Bad" name = "q_is_Bad">
                            <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_is_bad" />
                        </select>
                    </td>

                    <td class="table_edit_tr_td_label" rowspan="1">
                        <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all_init" />
                        <c:set var="query" scope="request" value="1"/>
                        <c:set var="addClickMethod" scope="request" value="setControlNames('queryOp','q_order_no#q_sam_type#q_order_state#q_is_Bad')"/>
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
            <div id="clearStartHead" class="divForTableBlockHead" style="width: 140%">
                <table class="table_list_block" id="DataTableHead" >

                    <!--说明：列的序号从0开始 isDigit：false 表示按字符串排序 ：true表示按数值排序 sortedby：asc表示按升序 dec表示按降序 -->
                    <tr  class="table_list_tr_head_block" id="ignore">
                        <td   class="table_list_tr_col_head_block">
                            <input type="checkbox" name="rectNoAll" id="rectNoAll" onclick="selectAllRecord('detailOp', 'rectNoAll', 'rectNo', 'clearStart', 0);controlsByFlagWithoutCk('detailOp', ['modify']);setPageControl('detailOp');controlsByFlag('detailOp', ['del', 'audit']);"/>
                        </td>	
                        <td id="orderTd" class="table_list_tr_col_head_block"  isDigit=false index="1"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 130px">出库单号</td>
                        <td id="orderTd" class="table_list_tr_col_head_block"  isDigit=false index="2"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 130px">生产单号</td>
                        <td id="orderTd" class="table_list_tr_col_head_block"  isDigit=false index="3"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 100px">sam卡类型</td>
                        <td id="orderTd" class="table_list_tr_col_head_block"  isDigit=false index="4"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >卡状态</td>
                        <td id="orderTd" class="table_list_tr_col_head_block"  isDigit=false index="5" sortedby="asc"  onclick="sortForTableBlock('clearStart');" style="width: 100px">起始逻辑卡号</td>
                        <td id="orderTd" class="table_list_tr_col_head_block"  isDigit=true index="6"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >发行卡数量</td>
                        <td id="orderTd" class="table_list_tr_col_head_block"  isDigit=false index="7"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >单据状态</td>
                        <td id="orderTd" class="table_list_tr_col_head_block"  isDigit=false index="8"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >领卡人员</td>
                        <td id="orderTd" class="table_list_tr_col_head_block"  isDigit=false index="9"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >出库人员</td>
                        <td id="orderTd" class="table_list_tr_col_head_block"  isDigit=false index="10"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 130px">出库时间</td>
                        <td id="orderTd" class="table_list_tr_col_head_block"  isDigit=false index="11"  sortedby="asc" onclick="sortForTableBlock('clearStart');" > 审单人</td>
                        <td id="orderTd" class="table_list_tr_col_head_block"  isDigit=false index="12"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 130px">审核时间</td>
                        <td id="orderTd" class="table_list_tr_col_head_block"  isDigit=false index="13"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >回库状态</td>
                        <td id="orderTd" class="table_list_tr_col_head_block"  isDigit=false index="14" sortedby="asc"  onclick="sortForTableBlock('clearStart');" style="width: 140px">备注</td>
                    </tr>
                </table>
            </div>
            <div id="clearStart"  class="divForTableBlockData" style="width: 140%">
                <table class="table_list_block" id="DataTable" >
                    <c:forEach items="${ResultSet}" var="rs">
                        <tr class="table_list_tr_data"  onMouseOver="overResultRow('detailOp', this);" 
                            onMouseOut="outResultRow('detailOp', this);" 
                            onclick="
                                    clickResultRow('detailOp', this, 'detail');
                                    controlsByFlagWithoutCk('detailOp', ['modify']);
                                    controlsByFlag('detailOp', ['del', 'audit']);
                                    setPageControl('detailOp');" 
                            id="${rs.order_no}" flag="${rs.order_state}">

                            <td id="rectNo1" class="table_list_tr_col_data_block">
                                <input type="checkbox" name="rectNo" id="rectNo" onclick="unSelectAllRecord('detailOp', 'rectNoAll', 'rectNo');controlsByFlagWithoutCk('detailOp', ['modify']);
                                    controlsByFlag('detailOp', ['del', 'audit']);"
                                       value="${rs.order_no}" flag="${rs.order_state}">
                                </input>
                            </td>
                            <td  id="order_no" name="order_no" class="table_list_tr_col_data_block" style="width: 130px">
                                <c:choose>
                                    <c:when test="${rs.order_state != '0'}">
                                        <a href='#'
                                           onClick="openwindow('produceOrderAction?orderNo=${rs.pdu_order_no}&command=detail&ModuleID=${ModuleID}', '', 1300, 600)">                  
                                            ${rs.order_no}
                                        </a>
                                    </c:when>
                                    <c:otherwise>
                                        ${rs.order_no}
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td  id="pdu_order_no" name="pdu_order_no" class="table_list_tr_col_data_block" style="width: 130px">
                                ${rs.pdu_order_no}                                
                            </td>
                            <td  id="sam_type" name="sam_type" class="table_list_tr_col_data_block" style="width: 100px">
                                ${rs.sam_type_text}                                
                            </td>
                            <td  id="isBad" class="table_list_tr_col_data_block" >
                                ${rs.isBadName}
                            </td>
                             <td  id="start_logic_no" class="table_list_tr_col_data_block" style="width: 100px">
                                ${rs.start_logic_no}
                            </td>
                            <td  id="order_num" class="table_list_tr_col_data_block">
                                ${rs.order_num}
                            </td>
                            <td  id="order_state" class="table_list_tr_col_data_block" >
                                ${rs.order_state_text}
                            </td>
                            <td  id="get_card_oper" class="table_list_tr_col_data_block">
                                ${rs.get_card_oper}
                            </td>
                            <td  id="out_stock_oper" class="table_list_tr_col_data_block">
                                ${rs.out_stock_oper}
                            </td>
                            <td  id="out_stock_time" class="table_list_tr_col_data_block" style="width: 130px">
                                ${rs.out_stock_time}
                            </td>
                            <td  id="audit_order_oper" class="table_list_tr_col_data_block" >
                                ${rs.audit_order_oper}
                            </td>
                            <td  id="audit_order_time" class="table_list_tr_col_data_block" style="width: 130px">
                                ${rs.audit_order_time}
                            </td>
                            <td  id="in_stock_state" class="table_list_tr_col_data_block" >
                                ${rs.in_stock_state_text}
                            </td>
                            <td  id="remark" class="table_list_tr_col_data_block" style="width: 140px">
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

        <FORM method="post" name="detailOp" id="detailOp" action="issueSamOutAction" >

            <c:set var="divideShow" scope="request" value="1"/>
            <c:import  url="/jsp/common/common_template_web_variable.jsp?template_name=common_web_variable" />
            <input type="hidden" name="expAllFields" id="d_expAllFields"
                value="getOrder_no,getPdu_order_no,getIsBadName,getSam_type_text,getStart_logic_no,getOrder_num,getOrder_state_text,getGet_card_oper,getOut_stock_oper,getOut_stock_time,getAudit_order_oper,getAudit_order_time,getIn_stock_state_text,getRemark" />
            <input type="hidden" name="expFileName" id="d_expFileName" value="卡发行出库.xlsx" />
            <input type="hidden" name="divId" id="d_divId" value="clearStartHead">
            <input type="hidden" name="methodUrl" id="d_methodUrl" value="/issueSamOutExportAll"/>
            
            <div id="detail"  class="divForTableDataDetail" >
                <table  class="table_edit_detail">
                    <tr class="table_edit_tr">
                        <td class="table_edit_tr_td_label">出库单号:</td>
                        <td class="table_edit_tr_td_input">
                            <input type="text" id="d_order_no" name="d_order_no" style="width:200px;"/>
                        </td>
                        <td class="table_edit_tr_td_label" >生产单号:</td>
                        <td class="table_edit_tr_td_input">
                            <select id="d_pdu_order_no" name="d_pdu_order_no" dataType="Require" msg="生产单号不能为空!" >				    
                                <!-- 生产单号 -->
                                <option value="" id="produceOrderSelf"></option>
                                <c:forEach items="${produceOrderList}" var="produceOrder"> 
                                    <option value="${produceOrder.code}" <c:if test="${produceOrder.code eq d_pdu_order_no}">selected = "selected"</c:if>>${produceOrder.code_text}</option>
                                </c:forEach>
                            </select>
                        </td>
                        <td class="table_edit_tr_td_label">领卡人员:</td>
                        <td class="table_edit_tr_td_input">
                            <input type="text" name="d_get_card_oper" id="d_get_card_oper" dataType="Limit" maxlength="45" 
                                    min="1" max="15" msg="领卡人员长度为45字符内" />
                        </td>
                    </tr>
                    <tr class="table_edit_tr">
                        <td class="table_edit_tr_td_label" >卡状态:</td>
                        <td class="table_edit_tr_td_input">
                            <select id="d_isBad" name="d_isBad" require="ture" dataType="NotEmpty" msg="卡状态不能为空!">				    
                                <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_is_bad" />
                            </select>
                        </td>
                        
                        <td class="table_edit_tr_td_label"  >备注:</td>
                        <td class="table_edit_tr_td_input">
                            <input type="text" id="d_remark" name="d_remark" dataType="Limit" maxlength="85" style="width:200px;"
                                                   require="false" min="1" max="85" msg="备注长度为256字符内" />
                        </td>
                    </tr>
                </table>
            </div>

            <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all_init" />
            <c:set var="add" scope="request" value="1"/>
            <c:set var="del" scope="request" value="1"/>
            <c:set var="modify" scope="request" value="1"/>
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

            <c:set var="clickMethod" scope="request" value="btnClick('detailOp','clearStart','detail','','clearStartHead');disableFormControls('detailOp',['d_order_no'],true);"/>
            <c:set var="addAfterClickModifyMethod" scope="request" value="displayOrderNoSelect('detailOp',['d_pdu_order_no'],true);"/>
            <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all" /> 
            <br/>
        </FORM> 

    </body>
</html>
