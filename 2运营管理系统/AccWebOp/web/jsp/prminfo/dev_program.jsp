<!DOCTYPE html>
<%@ page language="java" contentType="text/html;charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<html>
    <head>
        <title>设备程序
            <c:choose>
                <c:when test="${Type=='9120'}">
                    AG读卡器程序
                </c:when>
                <c:when test="${Type=='9220'}">
                    TVM读卡器程序
                </c:when>
                <c:when test="${Type=='9320'}">
                    BOM读卡器程序
                </c:when>
                <c:when test="${Type=='9420'}">
                    AQM读卡器程序
                </c:when>
                <c:when test="${Type=='9520'}">
                    PCA读卡器程序
                </c:when>
                <c:when test="${Type=='8100'}">
                    TVM地图参数
                </c:when>
            </c:choose>                    
        </title>
        <meta http-equiv="X-UA-Compatible" content="IE=edge,Chrome=1" />

        <script language="javascript" type="text/javascript" charset="utf-8" src="js/common_form.js"></script>
        <script language="javascript" type="text/javascript" charset="utf-8" src="js/Validator.js"></script>
        <script language="javascript" type="text/javascript" charset="utf-8" src="js/common_form_extend.js"></script>
        <link rel="stylesheet" type="text/css" href="css/frame/simple.css" title="Style"/>

        <script language="javascript" >
            function OpenDialog()
            {
                var paramTypeId = document.getElementById("dev_program_type").value;
                var openUrl = 'devImp?paramTypeId=' + paramTypeId;
                openwindow(openUrl, '', 600, 250);

                //openwindow('import_phy_logic.do','',600,250);
            }
        </script>
    </head>
    <body onload="setTableRowBackgroundBlock('DataTable')">         
        <form>
            <table class="table_title">
                <tr align="center" class="trTitle">
                    <td colspan="4">设备程序- <c:choose>
                            <c:when test="${Type=='9120'}">
                                AG读卡器程序
                            </c:when>
                            <c:when test="${Type=='9220'}">
                                TVM读卡器程序
                            </c:when>
                            <c:when test="${Type=='9320'}">
                                BOM读卡器程序
                            </c:when>
                            <c:when test="${Type=='9420'}">
                                AQM读卡器程序
                            </c:when>
                            <c:when test="${Type=='9520'}">
                                PCA读卡器程序
                            </c:when>
                            <c:when test="${Type=='8100'}">
                                TVM地图参数
                            </c:when>
                        </c:choose>  </td>
                </tr>

            </table>
        </form>
        <c:import url="/jsp/common/common_template.jsp?template_name=common_status_table" />



        <c:set var="pTitleName" scope="request" value="列表"/>
        <c:set var="pTitleWidth" scope="request" value="50"/>
        <c:import  url="/jsp/common/common_template.jsp?template_name=common_table_title" />

        <DIV id="clearStartBlock" class="divForTableBlock">
            <div id="clearStartHead" class="divForTableBlockHead">
                <table class="table_list_block" id="DataTableHead">
                    <tr  class="table_list_tr_head_block" id="ignore">

                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="1"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width:250px">版本号</td>	
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="2"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >版本类型</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="3"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width:450px">文件路径</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="4"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >操作员</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="5"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width:150px">应用线路</td>

                    </tr>
                </table>
            </div>
            <div id="clearStart"  class="divForTableBlockData">
                <table class="table_list_block" id="DataTable" >
                    <c:forEach items="${ResultSet}" var="rs">
                        <!--class="listTableData" -->
                        <tr class="table_list_tr_data"  onMouseOver="overResultRow('detailOp', this);" 
                            onMouseOut="outResultRow('detailOp', this);" 
                            onclick="clickResultRow('detailOp', this, 'detail');" 
                            id="${rs.waterNo}">


                            <td  id="versionNo" class="table_list_tr_col_data_block" style="width:250px">
                                ${rs.versionNo}
                            </td>
                            <td  id="recordFlag" class="table_list_tr_col_data_block">
                                ${rs.recordFlag}
                            </td>
                            <td  id="filePath" class="table_list_tr_col_data_block" style="width:450px">
                                ${rs.filePath}
                            </td>
                            <td  id="operatorId" class="table_list_tr_col_data_block">
                                ${rs.operatorId}
                            </td>
                            <td  id="appLineName" class="table_list_tr_col_data_block" style="width:150px">
                                ${rs.appLineName}
                            </td>
                        </tr>

                    </c:forEach>

                </table>
            </div>
        </DIV>
        <FORM method="post" name="detailOp" id="detailOp" action="devProgram" >


            <c:import  url="/jsp/common/common_template_web_variable.jsp?template_name=common_web_variable" />
            <DIV id="detail"  class="divForTableDataDetail" >
                <table style="display:none" align="center"
                       class="listDataTable">
                    <tr style="display:none">
                        <td>
                            <input type="hidden" name="ignore" />
                        </td>
                    </tr>
                </table>
        </FORM>

        <FORM method="post" name="detailOp1" id="detailOp1" action="devProgram" >

            <c:set var="divideShow" scope="request" value="1"/>
            <c:import  url="/jsp/common/common_template_web_variable.jsp?template_name=common_web_variable" />
            <DIV id="detail"  class="divForTableDataDetail" >
                <table style="display:none" align="center"
                       class="listDataTable">
                    <tr style="display:none">
                        <td>
                            <input type="hidden" name="ignore" />
                        </td>
                    </tr>
                </table>
                <input type="hidden" id="dev_program_type"  name="Type" value="${Type}">

                </input>
                <input name="import" type="button" value="导入" onclick="OpenDialog();" class="buttonStyle">


            </div>
            <br/>
        </FORM>


        <!-- 状态栏 通用模板 -->


    </body>
</html>


