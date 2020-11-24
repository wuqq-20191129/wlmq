<%-- 
    Document   : ticketStorageLineQry
    Created on : 2017-10-31
    Author     : luck
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>线路库存预警查询</title>


        <script language="javascript" type="text/javascript" charset="utf-8" src="js/common_form.js"></script>
        <script language="javascript" type="text/javascript" charset="utf-8" src="js/common_form_extend.js"></script>
        <script language="javascript" type="text/javascript" charset="utf-8" src="js/Validator.js"></script>
        <script language="JavaScript" type="text/javascript" charset="utf-8" src="js/CalendarPop.js"></script>  
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
    <body onload="
            initDocument('queryOp', 'detail');
            initDocument('detailOp', 'detail');
            setControlsDefaultValue('queryOp');
            setListViewDefaultValue('detailOp', 'clearStart');
            setQueryControlsDefaultValue('queryOp', 'detailOp');

            setPageControl('detailOp');
            setTableRowBackgroundBlock('DataTable')">

        <!--<body >-->
        <table  class="table_title">
            <tr align="center" class="trTitle">
                <td colspan="4">线路库存预警查询
                </td>
            </tr>
        </table>

        <!-- 表头 通用模板 -->

        <c:set var="pTitleName" scope="request" value="查询"/>
        <c:set var="pTitleWidth" scope="request" value="50"/>
        <c:import  url="/jsp/common/common_template.jsp?template_name=common_table_title" />

        <form method="post" name="queryOp" id="queryOp" action="ticketLineStorageQry">
            <!-- 页面用到的变量 通用模板 -->

            <c:set var="divideShow" scope="request" value="1"/>
            <c:import  url="/jsp/common/common_template_web_variable.jsp?template_name=common_web_variable" />

            <table  class="table_edit">
                <tr class="table_edit_tr">

                    <td class="table_edit_tr_td_label">线路:</td>
                    <td class="table_edit_tr_td_input">
                        <select id="q_lineId" name="q_lineId" >
                            <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_line" />
                        </select>
                    </td>
                    <td class="table_edit_tr_td_label" >票卡主类型:</td>
                    <td class="table_edit_tr_td_input">
                        <select id="q_cardMainCode" name="q_cardMainCode" onChange="setSelectValues('queryOp', 'q_cardMainCode', 'q_cardSubCode', 'commonVariable');"  >				    
                            <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_prm_maincard" />
                        </select>
                    </td>
                    <td class="table_edit_tr_td_label"><div align="right">票卡子类型:</div></td>
                    <td class="table_edit_tr_td_input">
                        <select id="q_cardSubCode" name="q_cardSubCode" >
                            <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_none" />
                        </select>
                        <c:set var="pVarName" scope="request" value="commonVariable"/>
                        <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_prm_subcard" />
                    </td>
                    <td class="table_edit_tr_td_label" rowspan="1">

                        <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all_init" />
                        <c:set var="query" scope="request" value="1"/>
                        <c:set var="addClickMethod" scope="request" value="setControlNames('queryOp','q_lineId#q_cardMainCode#q_cardSubCode');setLineCardNames('queryOp','','','','q_cardMainCode','q_cardSubCode','commonVariable');"/>
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
            <div id="clearStartHead" class="divForTableBlockHead" >
                <table class="table_list_block" id="DataTableHead" >

                    <!--说明：列的序号从0开始 isDigit：false 表示按字符串排序 ：true表示按数值排序 sortedby：asc表示按升序 dec表示按降序 -->
                    <tr  class="table_list_tr_head_block" id="ignore">
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="0"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 120px">线路</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="1"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 120px">票卡主类型</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="2"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 120px">票卡子类型</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=true index="3"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 90px">库存</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="4"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 150px">预警结果</td>

                    </tr>


                </table>

            </div>
            <!-- <DIV id="clearStart" align="center" style="position:relative; left:20; width:95%; height:30%;min-height: 200px;max-height: 250px; layer-background-color:#99CC99; border:thin outset #ffffff; z-index:1; overflow: scroll;">-->
            <div id="clearStart"  class="divForTableBlockData">
                <table class="table_list_block" id="DataTable" >

                    <c:forEach items="${ResultSet}" var="rs">
                        <!--class="listTableData" -->
                        <!--
                        setSelectValuesByRow('detailOp', 'd_stationID', 'commonVariable');
                        -->
                        <tr class="table_list_tr_data"  onMouseOver="overResultRow('detailOp', this);" 
                            onMouseOut="outResultRow('detailOp', this);" 
                            onclick="
                                    clickResultRow('detailOp', this, '');

                                    setPageControl('detailOp');" 
                            id="${rs.deptId}#${rs.tickettypeId}" >

                            <td  id="lineName" class="table_list_tr_col_data_block" style="width: 120px">
                                ${rs.lineName}

                            </td>
                            <td  id="cardMainName" class="table_list_tr_col_data_block" style="width: 120px">
                                ${rs.cardMainName}

                            </td>
                            <td  id="cardSubName" class="table_list_tr_col_data_block" style="width: 120px">
                                ${rs.cardSubName}
                            </td>
                            <td  id="currenttotal" class="table_list_tr_col_data_block" style="width: 90px">
                                ${rs.currenttotal}

                            </td>
                            <td id="flag" class="table_list_tr_col_data_block" style="width: 150px">
                                <c:choose>
                                    <c:when test="${rs.flag == 1}">
                                        <b style="color: red">超过库存安全上限</b>
                                    </c:when>
                                    <c:when test="${rs.flag == 2}">
                                        <b style="color: red">低于库存安全下限</b>
                                    </c:when>
                                    <c:otherwise>
                                         <b>库存正常</b>
                                    </c:otherwise>
                                </c:choose>
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
        
       

        <FORM method="post" name="detailOp" id="detailOp" action="ticketLineStorageQry" >

            <c:set var="divideShow" scope="request" value="1"/>
            <c:import  url="/jsp/common/common_template_web_variable.jsp?template_name=common_web_variable" />
            
              <input type="hidden" name="expAllFields" id="d_expAllFields"
                   value="getLineName,getCardMainName,getCardSubName,getCurrenttotal,getFlagName" />
            <input type="hidden" name="expFileName" id="d_expFileName" value="线路库存预警查询.xlsx" />
            <input type="hidden" name="divId" id="d_divId" value="clearStartHead">
            <input type="hidden" name="methodUrl" id="d_methodUrl" value="/TicketStorageLineQryExportAll"/>

            <!--<DIV id="detail" align="center" class="divForTableData" style="position:relative; left:20; width:95%; height:10; layer-background-color:#99CC99; border:thin outset #ffffff; z-index:1;">-->
            <div id="detail"  class="divForTableDataDetail" >
                <table  class="table_edit_detail">                  
                </table>
            </div>






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
        <!--
        <c:set var="addQueryMethod" scope="request" value=""/>
        <c:set var="addClickMethod" scope="request" value=""/>
        -->

        
    </body>
</html>
