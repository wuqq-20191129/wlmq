<%-- 
    Document   : distributeSamOutAction
    Created on : 2017-8-24, 9:19:46
    Author     : liudz
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>卡分发出库</title>


        <script language="javascript" type="text/javascript" charset="utf-8" src="js/common_form.js"></script>
        <script language="javascript" type="text/javascript" charset="utf-8" src="js/common_form_extend.js"></script>
        <script language="javascript" type="text/javascript" charset="utf-8" src="js/Validator.js"></script>
        <script language="JavaScript" type="text/javascript" charset="utf-8" src="js/CalendarPop.js"></script>
        <script language="JavaScript" type="text/javascript" charset="utf-8" src="js/icProduceOut.js"></script>
        <script language="JavaScript" type="text/javascript" charset="utf-8" src="js/icStorageProductBill.js"></script>
        <link rel="stylesheet" type="text/css" href="css/frame/simple.css" title="Style"/>
    </head>

    <body onload="
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
                <td colspan="4">卡分发出库
                </td>
            </tr>
        </table>

        <!-- 表头 通用模板 -->

        <c:set var="pTitleName" scope="request" value="查询"/>
        <c:set var="pTitleWidth" scope="request" value="50"/>
        <c:import  url="/jsp/common/common_template.jsp?template_name=common_table_title" />

        <form method="post" name="queryOp" id="queryOp" action="distributeSamOutAction">
            <!-- 页面用到的变量 通用模板 -->

            <c:set var="divideShow" scope="request" value="1"/>
            <c:import  url="/jsp/common/common_template_web_variable.jsp?template_name=common_web_variable" />

            <table  class="table_edit">
                <tr class="table_edit_tr">
                    <td class="table_edit_tr_td_label">出库单号:</td>
                    <td class="table_edit_tr_td_input">
                        <input type="text" id="q_order_no" name="q_order_no" value="" size="30" maxlength="20"  require="false" dataType="NumAndEng" min="20" max="20"  msg="出库单号应为20位" />
                    </td>

                    <td class="table_edit_tr_td_label">SAM卡类型:</td>
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

                    <td class="table_edit_tr_td_label" rowspan="1">

                        <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all_init" />
                        <c:set var="query" scope="request" value="1"/>
                        <c:set var="addClickMethod" scope="request" value="setControlNames('queryOp','q_order_no#q_sam_type#q_order_state');"/>
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
                            <input type="checkbox" name="rectNoAll" id="rectNoAll" 
                                   onclick="selectAllRecord('detailOp', 'rectNoAll', 'rectNo', 'clearStart', 0);setPageControl('detailOp');controlsByFlagWithoutCk('detailOp', ['modify']);controlsByFlag('detailOp', ['del', 'audit']);controlsByFlag('detailOp', ['del', 'audit']);"/>
                    </td>		
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="1"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 150px">出库单号</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="2"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 70px">线路（ES）</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="3"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 100px">sam卡类型</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="4"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 120px">起始逻辑卡号</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="5"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 80px">卡数量</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="6"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 120px">单据状态</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="7"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 80px">出库人员</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="8"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 150px">出库时间</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="9"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 80px">审单人</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="10" sortedby="asc"  onclick="sortForTableBlock('clearStart');" style="width: 150px">审核时间</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="11" sortedby="asc"  onclick="sortForTableBlock('clearStart');" style="width: 180px">备注</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="12"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 80px">领卡人</td>
                    </tr>


                </table>

            </div>
            <!-- <DIV id="clearStart" align="center" style="position:relative; left:20; width:95%; height:30%;min-height: 200px;max-height: 250px; layer-background-color:#99CC99; border:thin outset #ffffff; z-index:1; overflow: scroll;">-->
            <div id="clearStart"  class="divForTableBlockData" style="width: 130%;">
                <table class="table_list_block" id="DataTable" >

                    <c:forEach items="${ResultSet}" var="rs">

                        <tr class="table_list_tr_data"  onMouseOver="overResultRow('detailOp', this);" 
                            onMouseOut="outResultRow('detailOp', this);" 
                            onclick="
                                    
                                    clickResultRow('detailOp', this, 'detail');
                                    controlsByFlag('detailOp', ['del', 'audit']);
                                    controlsByFlagWithoutCk('detailOp', ['modify']);
                                    setPageControl('detailOp');"  
                            id="${rs.order_no}" flag="${rs.order_state}">

                            <td id="rectNo1" class="table_list_tr_col_data_block">

                                <input type="checkbox" name="rectNo" id="rectNo" onclick="unSelectAllRecord('detailOp', 'rectNoAll', 'rectNo');"
                                       value="${rs.order_no}"  flag="${rs.order_state}">
                                </input>
                            </td>
                            <c:choose> 
                                <c:when test="${rs.order_state=='0'}" >
                                    <td  id="order_no" name="order_no" class="table_list_tr_col_data_block" style="width: 150px;">
                                        ${rs.order_no} 
                                    </td>

                                </c:when>
                                <c:otherwise>
                                    <td  id="order_no" name="order_no" class="table_list_tr_col_data_block" style="width: 150px">
                                <a href='#'onClick="openwindow('distributeSamOutAction?queryCondition=${rs.order_no}&command=query&operType=planDetail&billRecordFlag=${rs.order_state}&ModuleID=${ModuleID}', '', 1200, 800)">
                                    ${rs.order_no}
                                </a>
                            </td>
                                    
                                </c:otherwise>
                            </c:choose>



                            <td  id="line_es" class="table_list_tr_col_data_block" style="width: 70px">
                                ${rs.line_es_name}
                            </td>
                            <td  id="sam_type" class="table_list_tr_col_data_block" style="width: 100px">
                                ${rs.sam_type_name}
                            </td>
                            <td  id="start_logic_no" class="table_list_tr_col_data_block" style="width: 120px">
                                ${rs.start_logic_no}
                            </td>
                            <td  id="order_num" class="table_list_tr_col_data_block" style="width: 80px">
                                ${rs.order_num}
                            </td>
                            <td  id="order_state" class="table_list_tr_col_data_block" style="width: 120px">
                                ${rs.order_state_name}
                            </td>

                            <td  id="out_stock_oper" class="table_list_tr_col_data_block" style="width: 80px"">
                                ${rs.out_stock_oper}
                            </td>
                            <td  id="out_stock_time" class="table_list_tr_col_data_block" style="width: 150px">
                                ${rs.out_stock_time}
                            </td>
                            <td  id="audit_order_oper" class="table_list_tr_col_data_block" style="width: 80px">
                                ${rs.audit_order_oper}
                            </td>
                            <td  id="audit_order_time" class="table_list_tr_col_data_block" style="width: 150px">
                                ${rs.audit_order_time}
                            </td>
                            <td  id="remark" class="table_list_tr_col_data_block" style="width: 180px">
                                ${rs.remark}
                            </td>
                            <td  id="get_card_oper" class="table_list_tr_col_data_block" style="width: 80px">
                                ${rs.get_card_oper}
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

        <FORM method="post" name="detailOp" id="detailOp" action="distributeSamOutAction" >
            
            <input type="hidden" name="expAllFields" id="d_expAllFields"
                value="order_no,line_es_name,sam_type_name,start_logic_no,order_num,order_state_name,out_stock_oper,out_stock_time,
                audit_order_oper,audit_order_time,remark,get_card_oper" />
            <input type="hidden" name="expFileName" id="d_expFileName" value="卡分发出库.xlsx" />
            <input type="hidden" name="divId" id="d_divId" value="clearStartHead">
            <input type="hidden" name="methodUrl" id="d_methodUrl" value="/DistributeSamOutActionExportAll"/>

            <c:set var="divideShow" scope="request" value="1"/>
            <c:import  url="/jsp/common/common_template_web_variable.jsp?template_name=common_web_variable" />

            <!--<DIV id="detail" align="center" class="divForTableData" style="position:relative; left:20; width:95%; height:10; layer-background-color:#99CC99; border:thin outset #ffffff; z-index:1;">-->
            <div id="detail"  class="divForTableDataDetail" >
                <table  class="table_edit_detail">
                    <tr class="table_edit_tr">
                        <td class="table_edit_tr_td_label">出库单号: </td>
                        <td class="table_edit_tr_td_input">
                            <input type="text" name="d_order_no" id="d_order_no"/>
                        </td>
                        <td class="table_edit_tr_td_label">起始逻辑卡号: </td>
                        <td class="table_edit_tr_td_input">
                            <input type="text" name="d_start_logic_no" id="d_start_logic_no" dataType="logicNo|LimitB" maxlength="16" min="16" 
                                   max="16" msg="起始逻辑卡号长度为16位，其中第9位为A或B，其它为数字！"/>
                        </td>
                        <td class="table_edit_tr_td_label">连续卡数量: </td>
                        <td class="table_edit_tr_td_input">
                            <input type="text" name="d_order_num" id="d_order_num" dataType="positiveInt|Limit" value="1" 
                                   maxlength="5" min="1" max="5" msg="连续卡数量长度为小于等于5位的正数字！"/>
                        </td>
                        <td class="table_edit_tr_td_label">领卡人员: </td>
                        <td class="table_edit_tr_td_input">
                            <input type="text" name="d_get_card_oper" id="d_get_card_oper" dataType="LimitB" maxlength="20" 
                                   min="1" max="20" msg="领卡人员长度为20字符内"/>
                        </td>
                    </tr>
                    <tr class="table_edit_tr">
                        <td class="table_edit_tr_td_label">线路（ES）:</td>
                        <td class="table_edit_tr_td_input">
                            <select id="d_line_es" name="d_line_es"dataType="Require" msg="线路(ES)不能为空!" >				    
                                <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_line_es" />
                            </select>	
                        </td>
                        <td class="table_edit_tr_td_label">SAM卡类型:</td>
                        <td class="table_edit_tr_td_input">
                            <select id="d_sam_type" name="d_sam_type" dataType="Require" msg="sam卡类型不能为空!"   >				    
                                <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_samtype" />
                            </select>	
                        </td>
                        <td class="table_edit_tr_td_label">备注:</td>
                        <td class="table_edit_tr_td_input">
                            <input type="text" name="d_remark" id="d_remark" size="30" require="false" maxlength="256" dataType="LimitContainChinese" min="1" max="256" msg="备注最大长度为256个字节，85个中文字"/>
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
            <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all" />
            <br/>
        </FORM
        <!--
        
        <c:set var="addClickMethod" scope="request" value="setUpdatePrimaryKey('detailOp','d_order_no#d_start_logic_no#d_order_num#d_get_card_oper#d_line_es#d_sam_type#d_remark');"/>
        -->

        
    </body>
</html>