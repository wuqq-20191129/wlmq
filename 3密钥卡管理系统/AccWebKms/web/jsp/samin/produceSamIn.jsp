<%-- 
    Document   : produceSamIn
    Created on : 2017-9-04
    Author     : luck
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>卡制作入库</title>


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
    <body onload="initDocument('queryOp', 'detail');
            initDocument('detailOp', 'detail');
            setControlsDefaultValue('queryOp');
            setListViewDefaultValue('detailOp', 'clearStart');
            setQueryControlsDefaultValue('queryOp', 'detailOp');
            setPrimaryKeys('detailOp', 'd_issueOrderNo');
            setPageControl('detailOp');
            setTableRowBackgroundBlock('DataTable')">

        <!--<body >-->
        <table  class="table_title">
            <tr align="center" class="trTitle">
                <td colspan="4">卡制作入库
                </td>
            </tr>
        </table>

        <!-- 表头 通用模板 -->

        <c:set var="pTitleName" scope="request" value="查询"/>
        <c:set var="pTitleWidth" scope="request" value="50"/>
        <c:import  url="/jsp/common/common_template.jsp?template_name=common_table_title" />

        <form method="post" name="queryOp" id="queryOp" action="produceSamInAction">
            <!-- 页面用到的变量 通用模板 -->

            <c:set var="divideShow" scope="request" value="1"/>
            <c:import  url="/jsp/common/common_template_web_variable.jsp?template_name=common_web_variable" />

            <table  class="table_edit">
                <tr class="table_edit_tr">
                    <td class="table_edit_tr_td_label">入库单号:</td>
                    <td class="table_edit_tr_td_input">
                        <input type="text" id="q_orderNo" name="q_orderNo" value="" size="25" maxlength="20" require="false" dataType="NumAndEng|LimitContainChinese" min="20" max="20"msg="入库单号长度应为20位数字或字母组合" />
                    </td>                 
                    <td class="table_edit_tr_td_label" >SAM卡类型:</td>
                    <td class="table_edit_tr_td_input">
                        <select id="q_samType" name="q_samType" >				    
                            <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_samtype" />
                        </select>
                    </td>
                    <td class="table_edit_tr_td_label">单据状态:</td>
                    <td class="table_edit_tr_td_input">
                        <select id="q_orderState" name="q_orderState" >
                            <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_billstatues" />
                        </select>
                    </td>

                    <td class="table_edit_tr_td_label" rowspan="1">

                        <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all_init" />
                        <c:set var="query" scope="request" value="1"/>
                        <c:set var="addClickMethod" scope="request" value="setControlNames('queryOp','q_orderNo#q_samType#q_orderState')"/>
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
                            <input type="checkbox" name="rectNoAll" id="rectNoAll" onclick="selectAllRecord('detailOp', 'rectNoAll', 'rectNo', 'clearStart', 0);controlsByFlag('detailOp', ['del', 'audit']);controlsByFlagWithoutCk('detailOp', ['modify']);setPageControl('detailOp');"/>
                        </td>	
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="1"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 150px">入库单号</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="2"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 150px">卡发行出库单号</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="3"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 100px">sam卡类型</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="4"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 120px">起始逻辑卡号</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=true index="5"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >发行卡数量</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="6"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >单据状态</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="7"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 100px">领卡人员</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="8"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >入库人员</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="9"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 120px">入库时间</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="10" sortedby="asc"  onclick="sortForTableBlock('clearStart');" >审单人</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="11" sortedby="asc"  onclick="sortForTableBlock('clearStart');" style="width: 120px">审核时间</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="12" sortedby="asc"  onclick="sortForTableBlock('clearStart');" style="width: 200px">备注</td>
                    </tr>


                </table>

            </div>
            <!-- <DIV id="clearStart" align="center" style="position:relative; left:20; width:95%; height:30%;min-height: 200px;max-height: 250px; layer-background-color:#99CC99; border:thin outset #ffffff; z-index:1; overflow: scroll;">-->
            <div id="clearStart"  class="divForTableBlockData" style="width: 140%">
                <table class="table_list_block" id="DataTable" >

                    <c:forEach items="${ResultSet}" var="rs">
                        <!--class="listTableData" -->
                        <!--
                        setSelectValuesByRow('detailOp', 'd_stationID', 'commonVariable');
                        -->
                        <tr class="table_list_tr_data"  onMouseOver="overResultRow('detailOp', this);" 
                            onMouseOut="outResultRow('detailOp', this);" 
                            onclick="
                                    clickResultRow('detailOp', this, 'detail');
                                    controlsByFlagWithoutCk('detailOp', ['modify']);
                                    controlsByFlag('detailOp', ['del', 'audit']);
                                    setPageControl('detailOp');" 
                            id="${rs.orderNo}" flag="${rs.orderState}">

                            <td id="rectNo1" class="table_list_tr_col_data_block">
                                <input type="checkbox" name="rectNo" id="rectNo" onclick="unSelectAllRecord('detailOp', 'rectNoAll', 'rectNo');"
                                       value="${rs.orderNo}"  flag="${rs.orderState}">

                                </input>
                            </td>
                            <td  id="orderNo" class="table_list_tr_col_data_block" style="width: 150px">
                                <c:choose>
                                    <c:when test="${rs.orderState == '1'}">
                                        <a href='#'
                                           onClick="openwindow('recycleSamInAction?queryCondition=${rs.orderNo}&command=query&operType=planDetail&billRecordFlag=${rs.orderState}&ModuleID=${ModuleID}', '', 1200, 800)">                  
                                            ${rs.orderNo}
                                        </a>
                                    </c:when>
                                    <c:otherwise>
                                        ${rs.orderNo}
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td  id="issueOrderNo" class="table_list_tr_col_data_block" style="width: 150px">
                                ${rs.issueOrderNo}                                
                            </td>
                            <td  id="samType" class="table_list_tr_col_data_block" style="width: 100px">
                                ${rs.samTypeName}
                            </td>
                            <td  id="startLogicNo" class="table_list_tr_col_data_block" style="width: 120px">
                                ${rs.startLogicNo}
                            </td>
                            <td  id="orderNum" class="table_list_tr_col_data_block">
                                ${rs.orderNum}
                            </td>
                            <td  id="orderState" class="table_list_tr_col_data_block" >
                                ${rs.orderStateName}
                            </td>
                            <td  id="getCardOper" class="table_list_tr_col_data_block" style="width: 100px">
                                ${rs.getCardOper}
                            </td>
                            <td  id="inStockOper" class="table_list_tr_col_data_block" >
                                ${rs.inStockOper}
                            </td>
                            <td  id="inStockTime" class="table_list_tr_col_data_block" style="width: 120px">
                                ${rs.inStockTime}
                            </td>
                            <td  id="auditOrderOper" class="table_list_tr_col_data_block">
                                ${rs.auditOrderOper}
                            </td>
                            <td  id="auditOrderTime" class="table_list_tr_col_data_block" style="width: 120px">
                                ${rs.auditOrderTime}
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

        <FORM method="post" name="detailOp" id="detailOp" action="produceSamInAction" >

            <c:set var="divideShow" scope="request" value="1"/>
            <c:import  url="/jsp/common/common_template_web_variable.jsp?template_name=common_web_variable" />
            
            <input type="hidden" name="expAllFields" id="d_expAllFields"
                   value="getOrderNo,getIssueOrderNo,getSamTypeName,getStartLogicNo,getOrderNum,getOrderStateName,getGetCardOper,getInStockOper,getInStockTime,getAuditOrderOper,getAuditOrderTime,getRemark" />
            <input type="hidden" name="expFileName" id="d_expFileName" value="卡制作入库.xlsx" />
            <input type="hidden" name="divId" id="d_divId" value="clearStartHead">
            <input type="hidden" name="methodUrl" id="d_methodUrl" value="/ProduceSamInExportAll"/>

            <!--<DIV id="detail" align="center" class="divForTableData" style="position:relative; left:20; width:95%; height:10; layer-background-color:#99CC99; border:thin outset #ffffff; z-index:1;">-->
            <div id="detail"  class="divForTableDataDetail" >
                <table  class="table_edit_detail">
                    <tr class="table_edit_tr">
                        <td class="table_edit_tr_td_label">入库单号: </td>
                        <td class="table_edit_tr_td_input">
                            <input type="text" id="d_orderNo" name="d_orderNo" />
                        </td>
                        <td class="table_edit_tr_td_label" >卡发行单号:</td>
                        <td class="table_edit_tr_td_input">
                            <select id="d_issueOrderNo" name="d_issueOrderNo" require="ture" dataType="NotEmpty" msg="卡发行单号不能为空!">				    
                                <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_issue_OrderNO" />
                            </select>
                        </td>
                        <td class="table_edit_tr_td_label" >领卡人员:</td>
                        <td class="table_edit_tr_td_input">
                            <input type="text" name="d_getCardOper" id="d_getCardOper" dataType="LimitContainChinese" maxlength="45" min="1" max="45" msg="领卡人员长度为45字符内,中文为3个字符（最多15个中文）" />
                        </td>

                    </tr>
                    <tr class="table_edit_tr">                 
                        <td class="table_edit_tr_td_label"  >备注:</td>
                        <td class="table_edit_tr_td_input">
                            <input type="text" id="d_remark" name="d_remark" dataType="LimitContainChinese" require="false" maxlength="250" min="1" max="250" msg="备注长度为250字符内,中文为3个字符（最多85个中文）" />
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
            <c:set var="expAll" scope="request" value="1"/>
            <c:set var="btNext" scope="request" value="1"/>
            <c:set var="btNextEnd" scope="request" value="1"/>
            <c:set var="btBack" scope="request" value="1"/>
            <c:set var="btBackEnd" scope="request" value="1"/>
            <c:set var="btGoPage" scope="request" value="1"/>



            <c:set var="clickMethod" scope="request" value="btnClick('detailOp','clearStart','detail','','clearStartHead');disableFormControls('detailOp',['d_orderNo'],true);"/>
            <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all" />
            <br/>
        </FORM>
        <!--
        <c:set var="addQueryMethod" scope="request" value=""/>
        <c:set var="addClickMethod" scope="request" value=""/>
        -->

        
    </body>
</html>
