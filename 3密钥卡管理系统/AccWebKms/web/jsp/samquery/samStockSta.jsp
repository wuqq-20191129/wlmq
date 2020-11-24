<%-- 
    Document   : samStockSta
    Created on : 2017-8-25, 15:44:24
    Author     : chenzx
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>卡库存统计</title>
        <script language="javascript" type="text/javascript" charset="utf-8" src="js/common_form.js"></script>
        <script language="javascript" type="text/javascript" charset="utf-8" src="js/common_form_extend.js"></script>
        <script language="javascript" type="text/javascript" charset="utf-8" src="js/Validator.js"></script>
        <script language="JavaScript" type="text/javascript" charset="utf-8" src="js/CalendarPop.js"></script>
        <link rel="stylesheet" type="text/css" href="css/frame/simple.css" title="Style"/>
    </head>
    <body onload="initDocument('queryOp', 'detail');
            initDocument('detailOp', 'detail');
            setControlsDefaultValue('queryOp');
            setListViewDefaultValue('detailOp', 'clearStart');
            setQueryControlsDefaultValue('queryOp', 'detailOp');
            setPageControl('detailOp');
            setTableRowBackgroundBlock('DataTable')"> 
        <table  class="table_title">
            <tr align="center" class="trTitle">
                <td colspan="4">
                    卡库存统计
                </td>
            </tr>
        </table>

        <!-- 表头 通用模板 -->

        <c:set var="pTitleName" scope="request" value="查询"/>
        <c:set var="pTitleWidth" scope="request" value="50"/>
        <c:import  url="/jsp/common/common_template.jsp?template_name=common_table_title" />

        <form method="post" name="queryOp" id="queryOp" action="samStockStaAction">
            <!-- 页面用到的变量 通用模板 -->
            <c:set var="divideShow" scope="request" value="1"/>
            <c:import  url="/jsp/common/common_template_web_variable.jsp?template_name=common_web_variable" />
            <table  class="table_edit">
                <tr class="table_edit_tr">
                    <td class="table_edit_tr_td_label">SAM卡类型:</td>
                    <td class="table_edit_tr_td_input">
                        <select id = "q_samType" name = "q_samType">
                            <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_samtype" />
                        </select>
                    </td>    
                    <td class="table_edit_tr_td_label">起始逻辑卡号:</td>
                    <td class="table_edit_tr_td_input">
                        <input type="text" name="q_beginLogicNo" id="q_beginLogicNo" style="width:120px"
                               size="10"  maxLength="16" require="false" dataType="logicNo|LimitB" min="16" max="16" msg="起始逻辑卡号长度为16位，其中第9位为A或B，其它为数字！"/>
                    </td>    
                    <td class="table_edit_tr_td_label">结束逻辑卡号:</td>
                    <td class="table_edit_tr_td_input">
                        <input type="text" name="q_endLogicNo" id="q_endLogicNo" style="width:120px"
                               size="10"  maxLength="16" require="false" dataType="logicNo|LimitB" min="16" max="16" msg="结束逻辑卡号长度为16位，其中第9位为A或B，其它为数字！"/>
                    </td>
                                       
                    <td class="table_edit_tr_td_input" style="text-align:center">
                        <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all_init" />
                        <c:set var="query" scope="request" value="1"/>
                        <c:set var="addClickMethod" scope="request" value="setControlNames('queryOp','q_samType#q_beginLogicNo#q_endLogicNo#q_produceType#q_stockState#q_isInstock#q_isBad');"/>
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
            <div id="clearStartHead" class="divForTableBlockHead">
                <table class="table_list_block" id="DataTableHead" >
                    <!--说明：列的序号从0开始 isDigit：false 表示按字符串排序 ：true表示按数值排序 sortedby：asc表示按升序 dec表示按降序 -->
                    <tr  class="table_list_tr_head_block" id="ignore">
                        <td id="orderTd"  class="table_list_tr_col_head_block"  isDigit=false  index="0"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width:150px;">SAM卡类型</td>
                        <td id="orderTd"  class="table_list_tr_col_head_block"  isDigit=true index="1"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width:150px;">出库空白卡数量</td>
                        <td id="orderTd"  class="table_list_tr_col_head_block"  isDigit=true index="2"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width:150px;">在库空白卡数量</td>
                        <td id="orderTd"  class="table_list_tr_col_head_block"  isDigit=true index="3"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width:150px;">出库成品卡数量</td>
                        <td id="orderTd"  class="table_list_tr_col_head_block"  isDigit=true index="4"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width:150px;">出库成品锁卡数量</td>
			<td id="orderTd"  class="table_list_tr_col_head_block"  isDigit=true  index="5"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width:150px;">在库成品好卡数量</td>
			<td id="orderTd"  class="table_list_tr_col_head_block"  isDigit=true index="6"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width:150px;">在库成品坏卡数量</td>
                        <td id="orderTd"  class="table_list_tr_col_head_block"  isDigit=true index="7"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width:150px;">在库成品锁卡数量</td>
                    </tr>
                </table>

            </div>
            <div id="clearStart"  class="divForTableBlockData">
                <table class="table_list_block" id="DataTable" >
                    <c:forEach items="${ResultSet}" var="rs">
                        <tr class="table_list_tr_data"  onMouseOver="overResultRow('detailOp', this);" 
                            onMouseOut="outResultRow('detailOp', this);" 
                            onclick="clickResultRow('detailOp', this, '');
                                    setPageControl('detailOp');" 
                            id="${rs.sam_type}">
                            <td  id="samType" class="table_list_tr_col_data_block" style="width:150px;">
                                ${rs.sam_typeText}
                            </td>
                            <td  id="outStockEmptyQty" class="table_list_tr_col_data_block" style="width:150px;">
                                ${rs.outStockEmptyQty}
                            </td>
                            <td  id="stockEmptyQty" class="table_list_tr_col_data_block" style="width:150px;">
                                ${rs.stockEmptyQty}
                            </td>
                            <td  id="outStockProductQty" class="table_list_tr_col_data_block" style="width:150px;">
                                ${rs.outStockProductQty}
                            </td>
                             <td  id="outLockStock" class="table_list_tr_col_data_block" style="width:150px;">
                                ${rs.outLockStock}
                            </td>
                            <td  id="goodStockProductQty" class="table_list_tr_col_data_block" style="width:150px;">
                                ${rs.goodStockProductQty}
                            </td>
                            <td  id="badStockProductQty" class="table_list_tr_col_data_block" style="width:150px;">
                                ${rs.badStockProductQty}
                            </td>
                            <td  id="inLockStock" class="table_list_tr_col_data_block" style="width:150px;">
                                ${rs.inLockStock}
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </div>
        </div>
        <FORM method="post" name="detailOp" id="detailOp" action="samStockStaAction" >  
        <!-- 表头 通用模板 -->
        
            <c:set var="divideShow" scope="request" value="1"/>
            <c:import  url="/jsp/common/common_template_web_variable.jsp?template_name=common_web_variable" />
            <input type="hidden" name="expAllFields" id="d_expAllFields"
                   value="getSam_typeText,getOutStockEmptyQty,getStockEmptyQty,getOutStockProductQty,getOutLockStock,getGoodStockProductQty,getBadStockProductQty,getInLockStock" />
            <input type="hidden" name="expFileName" id="d_expFileName" value="卡库存统计.xlsx" />
            <input type="hidden" name="divId" id="d_divId" value="clearStartHead">
            <input type="hidden" name="methodUrl" id="d_methodUrl" value="/SamStockStaExportAll"/>
            <div id="detail"  class="divForTableDataDetail" ></div>
            <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all_init" />
            <c:set var="print" scope="request" value="1"/>
            <c:set var="export" scope="request" value="1"/>
            <c:set var="expAll" scope="request" value="1"/>
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