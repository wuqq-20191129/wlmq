<%-- 
    Document   : fare_name
    Created on : 2017-6-21
    Author     : mqf
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>区段定义</title>


        <script language="javascript" type="text/javascript" charset="utf-8" src="js/common_form.js"></script>
        <script language="javascript" type="text/javascript" charset="utf-8" src="js/Validator.js"></script>
        <script language="JavaScript" src="js/CalendarPop.js"></script>
        <link rel="stylesheet" type="text/css" href="css/frame/simple.css" title="Style"/>
    </head>
        <body onload="initDocument('detailOp', 'detail');
            setPrimaryKeys('detailOp', 'd_fare_id');
            setListViewDefaultValue('detailOp', 'clearStart');
            setPageControl('detailOp');
            setTableRowBackgroundBlock('DataTable')">
            
        <table  class="table_title">
            <tr align="center" class="trTitle">
                <td colspan="4">收费区段定义
                </td>
            </tr>
        </table>

         <!-- 状态栏 通用模板 -->
        <c:import url="/jsp/common/common_template.jsp?template_name=common_status_table" />

        <!-- 表头 通用模板 -->
        <c:set var="pTitleName" scope="request" value="列表"/>
        <c:set var="pTitleWidth" scope="request" value="50"/>
        <c:import url="/jsp/common/common_template.jsp?template_name=common_table_title" />
        <div id="clearStartBlock" class="divForTableBlock">
            <div id="clearStartHead" class="divForTableBlockHead">
                <table id="DataTableHead" class="table_list_block">

                    <!--说明：列的序号从0开始 isDigit：false 表示按字符串排序 ：true表示按数值排序 sortedby：asc表示按升序 dec表示按降序 -->
                    <tr  class="table_list_tr_head_block" id="ignore">
                        <td   class="table_list_tr_col_head_block">
                            <input type="checkbox" name="rectNoAll" id="rectNoAll" onclick="selectAllRecord('detailOp', 'rectNoAll', 'rectNo', 'clearStart', 0);"/>
                        </td>	
                        <td id="orderTd"    style="width: 150px" class="table_list_tr_col_head_block"  isDigit=false index="1"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >收费区段ID</td>
                        <td id="orderTd"    style="width: 150px" class="table_list_tr_col_head_block"  isDigit=false index="2"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >区段名称</td>
                    </tr>

                </table>

            </div>
            <!-- <DIV id="clearStart" align="center" style="position:relative; left:20; width:95%; height:30%;min-height: 200px;max-height: 250px; layer-background-color:#99CC99; border:thin outset #ffffff; z-index:1; overflow: scroll;">-->
            <div id="clearStart"  class="divForTableBlockData">
                <table class="table_list_block" id="DataTable" >

                    <c:forEach items="${ResultSet}" var="rs">
                        <!--class="listTableData" -->
                        <tr class="table_list_tr_data"  onMouseOver="overResultRow('detailOp', this);" 
                            onMouseOut="outResultRow('detailOp', this);" 
                            onclick="clickResultRow('detailOp', this, 'detail');setPageControl('detailOp');" 
                            id="${rs.fare_id}">

                            <td id="rectNo1" class="table_list_tr_col_data_block">
                                <input type="checkbox" name="rectNo" onclick="unSelectAllRecord('detailOp', 'rectNoAll', 'rectNo');"
                                       value="${rs.fare_id}">

                                </input>
                            </td>
                            <td  id="fare_id" style="width: 150px" class="table_list_tr_col_data_block">
                                ${rs.fare_id}
                            </td>
                            <td  id="fare_name" style="width: 150px" class="table_list_tr_col_data_block">
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

        <FORM method="post" name="detailOp" id="detailOp" action="FareName" >

            <c:set var="divideShow" scope="request" value="1"/>
            <c:import  url="/jsp/common/common_template_web_variable.jsp?template_name=common_web_variable" />

            <!--<DIV id="detail" align="center" class="divForTableData" style="position:relative; left:20; width:95%; height:10; layer-background-color:#99CC99; border:thin outset #ffffff; z-index:1;">-->
            <DIV id="detail"  class="divForTableDataDetail" >
                <table  class="table_edit_detail">

                    <tr class="table_edit_tr">
                        <td class="table_edit_tr_td_label">收费区段ID:</td>
                        <td class="table_edit_tr_td_input">
                            <input type="text" name="d_fare_id" id="d_fare_id" size="15"  maxlength="2" require="true" dataType="Integer|Compare|LimitB"  operator="GreaterThanEqual" to="1" min="1" max="2"  msg="请输入1-99的数字!" />
                        </td>
                        <td class="table_edit_tr_td_label">区段名称:</td>
                        <td class="table_edit_tr_td_input">
                            <input type="text" name="d_fare_name" id="d_fare_name" size="10"  maxlength="10"   dataType="NotEmpty"    msg="区段名称不能为空!" />
                        </td>
                    </tr>
                </table>
            </DIV>


            <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all_init" />
            <c:set var="add" scope="request" value="1"/>
            <c:set var="del" scope="request" value="1"/>
            <c:set var="modify" scope="request" value="1"/>
            <c:set var="save" scope="request" value="1"/>
            <c:set var="cancle" scope="request" value="1"/>
            <c:set var="print" scope="request" value="1"/>
            <c:set var="export" scope="request" value="1"/>
            <c:set var="btNext" scope="request" value="1"/>
            <c:set var="btNextEnd" scope="request" value="1"/>
            <c:set var="btBack" scope="request" value="1"/>
            <c:set var="btBackEnd" scope="request" value="1"/>
            <c:set var="btGoPage" scope="request" value="1"/>

            <c:set var="addClickMethod" scope="request" value="setUpdatePrimaryKey('detailOp','d_fare_id');"/>
            <c:set var="clickMethod" scope="request" value="btnClick('detailOp','clearStart','detail','','clearStartHead');"/>
            <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all" />
            <br/>
        </FORM>

        <!-- 状态栏 通用模板 -->
        <%--<c:import url="/jsp/common/common_template.jsp?template_name=common_status_table" />--%>
    </body>
</html>
