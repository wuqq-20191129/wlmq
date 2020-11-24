<%-- 
    Document   : samStockQuery
    Created on : 2017-8-23, 16:20:07
    Author     : chenzx
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>卡库存查询</title>
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
                    卡库存查询
                </td>
            </tr>
        </table>

        <!-- 表头 通用模板 -->

        <c:set var="pTitleName" scope="request" value="查询"/>
        <c:set var="pTitleWidth" scope="request" value="50"/>
        <c:import  url="/jsp/common/common_template.jsp?template_name=common_table_title" />

        <form method="post" name="queryOp" id="queryOp" action="samStockQueryAction">
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
                    <td class="table_edit_tr_td_label">产品类型：</td>
                    <td class="table_edit_tr_td_input">
                        <select id = "q_produceType" name = "q_produceType">
                            <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_produce_type" />
                        </select>
                    </td>
                </tr>
                
                <tr class="table_edit_tr">
                    <td class="table_edit_tr_td_label">库存状态：</td>
                    <td class="table_edit_tr_td_input">
                        <select id = "q_stockState" name = "q_stockState">
                            <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_stock_state" />
                        </select>
                    </td>
                    <td class="table_edit_tr_td_label">是否在库：</td>
                    <td class="table_edit_tr_td_input">
                        <select id = "q_isInstock" name = "q_isInstock">
                            <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_is_instock" />
                        </select>
                    </td>
                    <td class="table_edit_tr_td_label">卡状态：</td>
                    <td class="table_edit_tr_td_input">
                        <select id = "q_isBad" name = "q_isBad">
                            <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_is_bad" />
                        </select>
                    </td>
                    <td class="table_edit_tr_td_label"></td>
                    <td class="table_edit_tr_td_input">
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
                        <td id="orderTd"  class="table_list_tr_col_head_block"  isDigit=true  index="0"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width:160px;">SAM类型</td>
                        <td id="orderTd"  class="table_list_tr_col_head_block"  isDigit=false index="1"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width:160px;">逻辑卡号</td>
                        <td id="orderTd"  class="table_list_tr_col_head_block"  isDigit=false index="2"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width:160px;">物理卡号</td>
                        <td id="orderTd"  class="table_list_tr_col_head_block"  isDigit=false index="3"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width:100px;">产品类型</td>
			<td id="orderTd"  class="table_list_tr_col_head_block"  isDigit=true  index="4"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width:115px;">库存状态</td>
			<td id="orderTd"  class="table_list_tr_col_head_block"  isDigit=false index="5"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width:100px;">是否在库</td>
			<td id="orderTd"  class="table_list_tr_col_head_block"  isDigit=false index="6"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width:100px;">卡状态</td>
                        <td id="orderTd"  class="table_list_tr_col_head_block"  isDigit=false index="7"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width:120px;">备注</td>
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
                            id="${rs.logic_no}">
                            <td  id="samType" class="table_list_tr_col_data_block" style="width:160px;">
                                ${rs.sam_typeText}
                            </td>
                            <td  id="logicNo" class="table_list_tr_col_data_block" style="width:160px;">
                                ${rs.logic_no}
                            </td>
                            <td  id="phyNo" class="table_list_tr_col_data_block" style="width:160px;">
                                ${rs.phy_no}
                            </td>
                            <td  id="produceType" class="table_list_tr_col_data_block" style="width:100px;">
                                ${rs.produce_typeText}
                            </td>
                            <td  id="stockState" class="table_list_tr_col_data_block" style="width:115px;">
                                ${rs.stock_stateText}
                            </td>
                            <td  id="isInstock" class="table_list_tr_col_data_block" style="width:100px;">
                                ${rs.is_instockText}
                            </td>
                            <td  id="isBad" class="table_list_tr_col_data_block" style="width:100px;">
                                ${rs.is_badText}
                            </td>
                            <td  id="remark" class="table_list_tr_col_data_block" style="width:120px;">
                                ${rs.remark}
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </div>
        </div>
        <FORM method="post" name="detailOp" id="detailOp" action="samStockQueryAction" >  
        <!-- 表头 通用模板 -->
        
            <c:set var="divideShow" scope="request" value="1"/>
            <c:import  url="/jsp/common/common_template_web_variable.jsp?template_name=common_web_variable" />
            <input type="hidden" name="expAllFields" id="d_expAllFields"
                   value="getSam_typeText,getLogic_no,getPhy_no,getProduce_typeText,getStock_stateText,getIs_instockText,getIs_badText,getRemark" />
            <input type="hidden" name="expFileName" id="d_expFileName" value="卡库存查询.xlsx" />
            <input type="hidden" name="divId" id="d_divId" value="clearStartHead">
            <input type="hidden" name="methodUrl" id="d_methodUrl" value="/SamStockQueryExportAll"/>
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

