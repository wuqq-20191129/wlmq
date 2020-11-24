<%-- 
    Document   : fare_table
    Created on : 2017-6-16
    Author     : mqf
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>票价表</title>


        <script language="javascript" type="text/javascript" charset="utf-8" src="js/common_form.js"></script>
        <script language="javascript" type="text/javascript" charset="utf-8" src="js/Validator.js"></script>
        <script language="JavaScript" src="js/CalendarPop.js"></script>
        <SCRIPT LANGUAGE="JavaScript">
//            function selectedRow(thisObject)
//            {
//               var selectedRow;
//               if(selectedRow != null)
//               {
//                  selectedRow.value = "unselected";
//                  selectedRow.style.backgroundColor = "";
//               }
//               thisObject.value = "selected";
//               thisObject.style.backgroundColor = "#94CBFF";
//               selectedRow = thisObject;
//               document.getElementById('d_flowId').value=selectedRow.childNodes.item(0).childNodes.item(0).value;
//               document.getElementById('d_water_no').value=thisObject.id;
//            }
        </SCRIPT>
        <link rel="stylesheet" type="text/css" href="css/frame/simple.css" title="Style"/>
    </head>
    <c:choose>
        <c:when test="${version.version_no == '0000000000'}">
            <body onload="initDocument('queryOp', 'detail');
                    initDocument('detailOp', 'detail');
                    setInvisable('detailOp', 'clone');
                    setPrimaryKeys('detailOp', 'd_fare_table_id#d_fare_zone');
                    setControlsDefaultValue('queryOp');
                    setListViewDefaultValue('detailOp', 'clearStart');
                    setQueryControlsDefaultValue('queryOp', 'detailOp');
                    setPageControl('detailOp');
                    setTableRowBackgroundBlock('DataTable')">
            </c:when>
            <c:otherwise>
            <body onload="initDocument('queryOp', 'detail');
                    initDocument('detailOp', 'detail');
                    setInvisable('detailOp', 'add;modify;del');
                    setPrimaryKeys('detailOp', 'd_fare_table_id#d_fare_zone');
                    setControlsDefaultValue('queryOp');
                    setListViewDefaultValue('detailOp', 'clearStart');
                    setQueryControlsDefaultValue('queryOp', 'detailOp');
                    setPageControl('detailOp');
                    setTableRowBackgroundBlock('DataTable')">
            </c:otherwise>
        </c:choose>
        <!--<body >-->
        <table  class="table_title">
            <tr align="center" class="trTitle">
                <td colspan="4">票价表（
                    ${version.record_flag_name}：${version.version_no}
                    ）
                </td>
            </tr>
        </table>

        <c:set var="pTitleName" scope="request" value="版本信息"/>
        <c:set var="pTitleWidth" scope="request" value="50"/>
        <c:import  url="/jsp/common/common_template.jsp?template_name=common_table_title" />
        <table class="table_edit" >
            <tr class="table_edit_tr">
                <td class="table_edit_tr_td_label">生效起始时间:</td>
                <td class="table_edit_tr_td_input">
                    <input type="text" name="startDate" id="startDate" size="20" value="${version.begin_time}" readonly="true">
                    </input>
                </td>
                <td class="table_edit_tr_td_label">生效截至时间:</td>
                <td class="table_edit_tr_td_input">
                    <input type="text" name="endDate" id="endDate" size="20" value="${version.end_time}" readonly="true">
                    </input>
                </td>
            </tr>
        </table>


        <!-- 表头 通用模板 -->

        <c:set var="pTitleName" scope="request" value="查询"/>
        <c:set var="pTitleWidth" scope="request" value="50"/>
        <c:import  url="/jsp/common/common_template.jsp?template_name=common_table_title" />

        <form method="post" name="queryOp" id="queryOp" action="FareTable">
            <!-- 页面用到的变量 通用模板 -->

            <c:set var="divideShow" scope="request" value="1"/>
            <c:import  url="/jsp/common/common_template_web_variable.jsp?template_name=common_web_variable" />

            <table  class="table_edit">
                <tr class="table_edit_tr">
                    <td class="table_edit_tr_td_label" >票价表ID:</td>
                    <td class="table_edit_tr_td_input">
                        <input type="text" name="q_fareId" id="q_fareId" size="10"  require="false" dataType="integer" maxLength="3"  msg="票价表ID只能为数字，最大长度为3位"/>
                    </td>
                    <td class="table_edit_tr_td_label">收费(分):</td>
                    <td class="table_edit_tr_td_input">
                        <input type="text" name="q_fare" id="q_fare" size="10" require="false" dataType="integer"  maxLength="10" msg="收费(分)只能为数字，最大长度为10位"/>
                    </td>
                    <td class="table_edit_tr_td_label">收费区段:</td>
                    <td class="table_edit_tr_td_input">
                        <select id="q_fareZone" name="q_fareZone" >
                            <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_fareName" />
                        </select>
                    </td>
                    
                    <td class="table_edit_tr_td_label" rowspan="2">
                        
                        <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all_init" />
                        <c:set var="query" scope="request" value="1"/>
                        <c:set var="addClickMethod" scope="request" value="setControlNames('queryOp','q_fareId#q_fare#q_fareZone');"/>
                        <c:set var="clickMethod" scope="request" value="btnClick('queryOp','clearStart','detail');"/>
                        <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all" /> 
                    </td>

                </tr>
            </table>
            <c:import url="/jsp/common/common_template.jsp?template_name=version_eval" />

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
                        <td   class="table_list_tr_col_head_block">
                            <input type="checkbox" name="rectNoAll" id="rectNoAll"  onclick="selectAllRecord('detailOp', 'rectNoAll', 'rectNo', 'clearStart', 0);"/>
                        </td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="1"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >票价表ID</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=true index="2"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >收费(分)</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="3"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >收费区段</td>
                        <td id="orderTd"    style="width: 120px" class="table_list_tr_col_head_block"  isDigit=false index="4"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >区段中文名</td>
                    </tr>


                </table>

            </div>
            <!-- <DIV id="clearStart" align="center" style="position:relative; left:20; width:95%; height:30%;min-height: 200px;max-height: 250px; layer-background-color:#99CC99; border:thin outset #ffffff; z-index:1; overflow: scroll;">-->
            <div id="clearStart"  class="divForTableBlockData">
                <table class="table_list_block" id="DataTable" >

                    <c:forEach items="${ResultSet}" var="rs">
                        <!--class="listTableData" -->
                        <!--selectedRow(this);-->
                        <tr class="table_list_tr_data"  onMouseOver="overResultRow('detailOp', this);" onMouseOut="outResultRow('detailOp', this);" 
                            onclick="clickResultRow('detailOp',this,'detail');setPageControl('detailOp');" 
                            id="${rs.water_no}#${rs.fare_table_id}">

                            <td id="rectNo" class="table_list_tr_col_data_block">
                                <input type="checkbox" name="rectNo" onclick="unSelectAllRecord('detailOp', 'rectNoAll', 'rectNo');"
                                       value="${rs.water_no}">
                                
                                </input>
                            </td>
                            <td  id="fare_table_id" class="table_list_tr_col_data_block">
                                ${rs.fare_table_id}
                            </td>
                            <td  id="fare" class="table_list_tr_col_data_block">
                                ${rs.fare}
                            </td>
                            <td  id="fare_zone" class="table_list_tr_col_data_block">
                                ${rs.fare_zone}
                            </td>
                            <td  id="fare_name" style="width: 120px" class="table_list_tr_col_data_block">
                                ${rs.fare_name}
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

        <FORM method="post" name="detailOp" id="detailOp" action="FareTable" >

            <c:set var="divideShow" scope="request" value="1"/>
            <c:import  url="/jsp/common/common_template_web_variable.jsp?template_name=common_web_variable" />
            
            <!--导出全部参数 -->
            <input type="hidden" name="expAllFields" id="d_expAllFields" value="getFare_table_id,getFare,getFare_zone,getFare_name" />
            <input type="hidden" name="expFileName" id="d_expFileName" value="票价表.xlsx" />
            <input type="hidden" name="divId" id="d_divId" value="clearStartHead" />
            <input type="hidden" name="methodUrl" id="d_methodUrl" value="/FareTableExportAll" />

            <!--<DIV id="detail" align="center" class="divForTableData" style="position:relative; left:20; width:95%; height:10; layer-background-color:#99CC99; border:thin outset #ffffff; z-index:1;">-->
            <DIV id="detail"  class="divForTableDataDetail" >
                <table  class="table_edit_detail">
                    <tr class="table_edit_tr">
                        
                        <input type="hidden" id="d_rectNo" name="d_water_no"/>
                        
                        <td class="table_edit_tr_td_label">票价表ID: </td>
                        <td class="table_edit_tr_td_input">
                            <input type="text" name="d_fare_table_id" id="d_fare_table_id" size="10" maxlength="3" require="true" dataType="Integer|Compare|LimitB"  operator="GreaterThanEqual" to="1" min="1" max="3" msg="票价表ID为(1-999)的整数!"/>
                        </td>
                        <td class="table_edit_tr_td_label">收费(分):</td>
                        <td class="table_edit_tr_td_input">
                            <input type="text" name="d_fare" id="d_fare" size="10" maxlength="10" require="true" dataType="integer|Compare"  operator="GreaterThanEqual" to="0" msg="收费值为正整数"/>
                        </td>
                        <td class="table_edit_tr_td_label">收费区段: </td>
                        <td class="table_edit_tr_td_input">
                            <select id="d_fare_zone" name="d_fare_zone" require="true" dataType="Require" msg="收费区段不能为空!">
                                <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_fareName" />
                            </select>
                        </td>
                    </tr>


                </table>
            </DIV>

            <c:import url="/jsp/common/common_template.jsp?template_name=version_eval" />




            <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all_init" />
            <c:set var="add" scope="request" value="1"/>
            <c:set var="del" scope="request" value="1"/>
            <c:set var="modify" scope="request" value="1"/>
            <c:set var="save" scope="request" value="1"/>
            <c:set var="cancle" scope="request" value="1"/>
            <c:set var="clone" scope="request" value="1"/>
            <c:set var="submit" scope="request" value="0"/>
            <c:set var="print" scope="request" value="1"/>
            <c:set var="export" scope="request" value="1"/>
            <%--<c:set var="expAll" scope="request" value="1" />--%>
            <c:set var="btNext" scope="request" value="1"/>
            <c:set var="btNextEnd" scope="request" value="1"/>
            <c:set var="btBack" scope="request" value="1"/>
            <c:set var="btBackEnd" scope="request" value="1"/>
            <c:set var="btGoPage" scope="request" value="1"/>

            <c:set var="addClickMethod" scope="request" value="setUpdatePrimaryKey('detailOp','d_water_no');"/>
            <c:set var="clickMethod" scope="request" value="btnClick('detailOp','clearStart','detail','','clearStartHead');"/>
            <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all" />
            <br/>
        </FORM>

        <!-- 状态栏 通用模板 -->

        <%--<c:import url="/jsp/common/common_template.jsp?template_name=common_status_table" />--%>
    </body>
</html>
