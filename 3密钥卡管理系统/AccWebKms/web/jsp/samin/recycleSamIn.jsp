<%-- 
    Document   : recycleSamIn
    Created on : 2017-8-28
    Author     : luck
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>卡回收入库</title>


        <script language="javascript" type="text/javascript" charset="utf-8" src="js/common_form.js"></script>
        <script language="javascript" type="text/javascript" charset="utf-8" src="js/common_form_extend.js"></script>
        <script language="javascript" type="text/javascript" charset="utf-8" src="js/Validator.js"></script>
        <script language="JavaScript" type="text/javascript" charset="utf-8" src="js/CalendarPop.js"></script>
        <script language="JavaScript" type="text/javascript" charset="utf-8" src="js/icProduceOut.js"></script>
        <script language="JavaScript" type="text/javascript" charset="utf-8" src="js/icStorageProductBill.js"></script>
        <link rel="stylesheet" type="text/css" href="css/frame/simple.css" title="Style"/>
    </head>

    <script language="JavaScript">

        function check() {
            var flag = <%=request.getAttribute("flag")%>;
            var flag1 = <%=request.getAttribute("flag1")%>;
            var ModuleID = <%=request.getAttribute("ModuleID")%>;
            var orderNo = document.getElementById("No").value;
            if (flag == "1") {
                var rst = confirm("单号:" + orderNo + "卡号段不连续，是否确定审核?");
                if (!rst)
                    return;
                else {
                    if (flag1 == "1") {
                        var rst1 = confirm("单号:" + orderNo + "起始逻辑卡号已在库存，审核将自动更新起始逻辑卡号，是否确定审核?");
                        if (!rst1)
                            return;
                        else
                            window.location.href = 'recycleSamInAction.do?command=audit&check=checked&ModuleID=' + ModuleID;
                    } else
                        window.location.href = 'recycleSamInAction.do?command=audit&check=checked&ModuleID=' + ModuleID;

                }

            } else {
                if (flag1 == "1") {
                    var rst1 = confirm("单号:" + orderNo + "起始逻辑卡号已在库存，审核将自动更新起始逻辑卡号，是否确定审核?");
                    if (!rst1)
                        return;
                    else
                        window.location.href = 'recycleSamInAction.do?command=audit&check=checked&ModuleID=' + ModuleID;
                }

            }
        }

    </script>

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
            check();
            setPageControl('detailOp');
            setTableRowBackgroundBlock('DataTable')">

        <!--<body >-->
        <table  class="table_title">
            <tr align="center" class="trTitle">
                <td colspan="4">卡回收入库
                </td>
            </tr>
        </table>

        <!-- 表头 通用模板 -->

        <c:set var="pTitleName" scope="request" value="查询"/>
        <c:set var="pTitleWidth" scope="request" value="50"/>
        <c:import  url="/jsp/common/common_template.jsp?template_name=common_table_title" />

        <form method="post" name="queryOp" id="queryOp" action="recycleSamInAction">
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
                    <td class="table_edit_tr_td_label">卡状态:</td>
                    <td class="table_edit_tr_td_input">
                        <select id="q_isBad" name="q_isBad" >				    
                                <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_is_bad" />
                            </select>
                    </td>

                    <td class="table_edit_tr_td_label" rowspan="1">

                        <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all_init" />
                        <c:set var="query" scope="request" value="1"/>
                        <c:set var="addClickMethod" scope="request" value="setControlNames('queryOp','q_orderNo#q_samType#q_orderState#q_isBad')"/>
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
                    <input type="hidden" name="No" id="No" value="${No}"/>
                    <td   class="table_list_tr_col_head_block">
                        <input type="checkbox" name="rectNoAll" id="rectNoAll" onclick="selectAllRecord('detailOp', 'rectNoAll', 'rectNo', 'clearStart', 0);setPageControl('detailOp');controlsByFlag('detailOp', ['del', 'audit']);controlsByFlag('detailOp', ['del', 'audit']);controlsByFlagWithoutCk('detailOp', ['modify']);"/>
                    </td>	
                    <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="1"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 150px">入库单号</td>
                    <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="2"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >线路(ES)</td>
                    <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="3"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 100px">sam卡类型</td>
                    <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="4"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 120px">起始逻辑卡号</td>
                    <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=true index="5"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >卡数量</td>
                    <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="6"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >单据状态</td>
                    <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="7"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 100px"> 领卡人员</td>
                    <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="8"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >卡状态</td>
                    <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=true index="9"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >入库人员</td>
                    <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="10" sortedby="asc"  onclick="sortForTableBlock('clearStart');" style="width: 130px">入库时间</td>
                    <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="11" sortedby="asc"  onclick="sortForTableBlock('clearStart');">审单人</td>
                    <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="12" sortedby="asc"  onclick="sortForTableBlock('clearStart');" style="width: 130px">审核时间</td>
                    <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="13" sortedby="asc"  onclick="sortForTableBlock('clearStart');" style="width: 200px">备注</td>
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
                            <td  id="lineEs" class="table_list_tr_col_data_block" >
                                ${rs.lineEsName}                                
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
                            <td  id="isBad" class="table_list_tr_col_data_block" >
                                ${rs.isBadName}
                            </td>
                            <td  id="inStockOper" class="table_list_tr_col_data_block" >
                                ${rs.inStockOper}
                            </td>
                            <td  id="inStockTime" class="table_list_tr_col_data_block" style="width: 130px">
                                ${rs.inStockTime}
                            </td>
                            <td  id="auditOrderOper" class="table_list_tr_col_data_block">
                                ${rs.auditOrderOper}
                            </td>
                            <td  id="auditOrderTime" class="table_list_tr_col_data_block" style="width: 130px">
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

        <FORM method="post" name="detailOp" id="detailOp" action="recycleSamInAction" >

            <c:set var="divideShow" scope="request" value="1"/>
            <c:import  url="/jsp/common/common_template_web_variable.jsp?template_name=common_web_variable" />
            
            <input type="hidden" name="expAllFields" id="d_expAllFields"
                   value="getOrderNo,getLineEsName,getSamTypeName,getStartLogicNo,getOrderNum,getOrderStateName,getGetCardOper,getIsBadName,getInStockOper,getInStockTime,getAuditOrderOper,getAuditOrderTime,getRemark" />
            <input type="hidden" name="expFileName" id="d_expFileName" value="卡回收入库.xlsx" />
            <input type="hidden" name="divId" id="d_divId" value="clearStartHead">
            <input type="hidden" name="methodUrl" id="d_methodUrl" value="/RecycleSamInExportAll"/>

            <!--<DIV id="detail" align="center" class="divForTableData" style="position:relative; left:20; width:95%; height:10; layer-background-color:#99CC99; border:thin outset #ffffff; z-index:1;">-->
            <div id="detail"  class="divForTableDataDetail" >
                <table  class="table_edit_detail">
                    <tr class="table_edit_tr">
                        <td class="table_edit_tr_td_label">入库单号: </td>
                        <td class="table_edit_tr_td_input">
                            <input type="text" id="d_orderNo" name="d_orderNo" />
                        </td>
                        <td class="table_edit_tr_td_label" >线路(ES):</td>
                        <td class="table_edit_tr_td_input">
                            <select id="d_lineEs" name="d_lineEs" require="ture" dataType="NotEmpty" msg="线路(ES)不能为空!">				    
                                <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_line_es" />
                            </select>
                        </td>
                        <td class="table_edit_tr_td_label" >SAM卡类型:</td>
                        <td class="table_edit_tr_td_input">
                            <select id="d_samType" name="d_samType" require="ture" dataType="NotEmpty" msg="SAM卡类型不能为空">				    
                                <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_samtype" />
                            </select>
                        </td>
                        <td class="table_edit_tr_td_label" >起始逻辑卡号:</td>
                        <td class="table_edit_tr_td_input">
                            <input type="text" name="d_startLogicNo" id="d_startLogicNo" dataType="logicNo|LimitB" maxlength="16"  min="16" max="16" msg="起始逻辑卡号长度为16位，其中第9位为A或B，其它为数字！" />
                        </td>


                    </tr>
                    <tr class="table_edit_tr">        
                        <td class="table_edit_tr_td_label" >连续卡数量:</td>
                        <td class="table_edit_tr_td_input">
                            <input type="text" name="d_orderNum" id="d_orderNum"  require="true" maxlength="5" dataType="positiveInt|Limit" min="1" max="5" msg="连续卡数量为大于等于1的整数"  value="1" />
                        </td>
                        <td class="table_edit_tr_td_label" >领卡人员:</td>
                        <td class="table_edit_tr_td_input">
                            <input type="text" name="d_getCardOper" id="d_getCardOper" dataType="LimitContainChinese" maxlength="45" min="1" max="45" msg="领卡人员长度为45字符内,中文为3个字符(最多15个中文)" />
                        </td>
                        <td class="table_edit_tr_td_label" >卡状态:</td>
                        <td class="table_edit_tr_td_input">
                            <select id="d_isBad" name="d_isBad" require="ture" dataType="NotEmpty" msg="卡状态不能为空!">				    
                                <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_is_bad" />
                            </select>
                        </td>
                        <td class="table_edit_tr_td_label"  >备注:</td>
                        <td class="table_edit_tr_td_input">
                            <input type="text" id="d_remark" name="d_remark" dataType="LimitContainChinese" require="false" maxlength="250" min="1" max="250" msg="备注长度为250字符内,中文为3个字符(最多85个中文)" />
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
        <c:set var="addQueryMethod" scope="request" value="setLineCardNames('detailOp','d_storage','d_zone','commonVariable','','','');"/>
        <c:set var="addClickMethod" scope="request" value="setUpdatePrimaryKey('detailOp','d_lineID#d_stationID#d_deviceType#d_deviceID#Version');"/>
        -->

        
    </body>
</html>
