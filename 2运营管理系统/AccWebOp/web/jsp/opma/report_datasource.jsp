<%--
    Document   : report_datasource
    Created on : 2017-6-14
    Author     : zhongziqi
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>报表数据源帐户管理</title>
        <script language="javascript" type="text/javascript" charset="utf-8" src="js/common_form.js"></script>
        <script language="javascript" type="text/javascript" charset="utf-8" src="js/Validator.js"></script>
        <script language="JavaScript" src="js/CalendarPop.js"></script>
        <link rel="stylesheet" type="text/css" href="css/frame/simple.css" title="Style"/>
        <script language="javascript" type="text/javascript">
            function setMinNum(setId){
                document.getElementById(setId).setAttribute("min","0");
                document.getElementById(setId).setAttribute("require","false");
            }
        </script>
    </head>
    <!--删除 hejj setInvisable('detailOp','clone');  -->
    <body onload="initDocument('queryOp', 'detail');
            initDocument('detailOp', 'detail');
            setPrimaryKeys('detailOp', 'd_dsId');
            setControlsDefaultValue('queryOp');
            setQueryControlsDefaultValue('queryOp', 'detailOp');
            setListViewDefaultValue('detailOp', 'clearStart');
            setTableRowBackgroundBlock('DataTable')"
                >
        <table  class="table_title">
            <tr align="center" class="trTitle">
                <td colspan="4">
                    修改密码 报表数据源帐户管理
                </td>
            </tr>
        </table>

        <!-- 表头 通用模板 -->

        <c:set var="pTitleName" scope="request" value="查询"/>
        <c:set var="pTitleWidth" scope="request" value="50"/>
        <c:import  url="/jsp/common/common_template.jsp?template_name=common_table_title" />

        <form method="post" name="queryOp" id="queryOp" action="ReportDataSource">
            <!-- 页面用到的变量 通用模板 -->
            <c:set var="divideShow" scope="request" value="1"/>
            <c:import  url="/jsp/common/common_template_web_variable.jsp?template_name=common_web_variable" />
            <table  class="table_edit">
                <tr class="table_edit_tr">
                    <td class="table_edit_tr_td_label">报表数据源:</td>
                    <td class="table_edit_tr_td_input">
                        <select id="q_dsId" name = "q_dsId" require="true" dataType="LimitB">
                            <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_reportDataSources" />
                        </select>
                    </td>
                    <td class="table_edit_tr_td_label" rowspan="2">
                        <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all_init" />
                        <c:set var="query" scope="request" value="1"/>
                        <c:set var="addClickMethod" scope="request" value="setControlNames('queryOp','q_dsId');"/>
                        <c:set var="clickMethod" scope="request" value="btnClick('queryOp','clearStart','detail');"/>
                        <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all" />
                    </td>
                </tr>
            </table>

        </form>

        <!-- 表头 通用模板 -->
        <!-- 状态栏 通用模板 -->
        <c:import url="/jsp/common/common_template.jsp?template_name=common_status_table" />
        <c:set var="pTitleName" scope="request" value="列表"/>
        <c:set var="pTitleWidth" scope="request" value="50"/>
        <c:import url="/jsp/common/common_template.jsp?template_name=common_table_title" />
        <div id="clearStartBlock" class="divForTableBlock">
            <div id="clearStartHead" class="divForTableBlockHead">
                <table class="table_list_block" id="DataTableHead" >
                    <!--说明：列的序号从0开始 isDigit：false 表示按字符串排序 ：true表示按数值排序 sortedby：asc表示按升序 dec表示按降序 -->
                    <tr  class="table_list_tr_head_block" id="ignore">
                        <td   class="table_list_tr_col_head_block">
                            <input type="checkbox" name="rectNoAll"  id="rectNoAll" onclick="selectAllRecord('detailOp', 'rectNoAll', 'rectNo', 'clearStart', 0);"/>
                        </td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="1"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >ID</td>
                        <td id="orderTd"   style="width: 150px" class="table_list_tr_col_head_block"  isDigit=false index="2"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >报表数据源</td>
                        <td id="orderTd"   style="width: 100px"  class="table_list_tr_col_head_block"  isDigit=false index="3"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >用户名</td>
                        <td id="orderTd"   style="width: 400px" class="table_list_tr_col_head_block"  isDigit=false index="4"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >备注</td>
                    </tr>
                </table>

            </div>
            <div id="clearStart"  class="divForTableBlockData">
                <table class="table_list_block" id="DataTable" >
                    <c:forEach items="${ResultSet}" var="rs">
                        <!--class="listTableData" -->
                        <tr class="table_list_tr_data"  onMouseOver="overResultRow('detailOp', this);"
                            onMouseOut="outResultRow('detailOp', this);"
                            onclick="clickOneResultRow('detailOp', this, 'detail','rectNo');"
                            id="${rs.dsId}">

                            <td id="rectNo1" class="table_list_tr_col_data_block">
                                <input type="checkbox" name="rectNo" onclick="unSelectAllRecord('detailOp', 'rectNoAll', 'rectNo');"
                                       value="${rs.dsId}">
                                </input>
                            </td>
                            <td  id="dsId" class="table_list_tr_col_data_block" >
                                ${rs.dsId}
                            </td>
                            <td  id="dsName" class="table_list_tr_col_data_block" style="width: 150px">
                                ${rs.dsName}
                            </td>
                            <td  id="dsUser" class="table_list_tr_col_data_block" style="width: 100px">
                                ${rs.dsUser}
                            </td>
                            <td  id="remark" class="table_list_tr_col_data_block" style="width: 400px">
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

        <FORM method="post" name="detailOp" id="detailOp" action="ReportDataSource">
            <c:import  url="/jsp/common/common_template_web_variable.jsp?template_name=common_web_variable" />
            <DIV id="detail"  class="divForTableDataDetail" >
                <table  class="table_edit_detail">
                    <tr class="table_edit_tr">
                        <td class="table_edit_tr_td_label">报表数据源</td>
                        <td class="table_edit_tr_td_input">
                            <select name="d_dsId" id="d_dsId" require="true" dataType="LimitB" min="1"  msg="请选择报表数据源"/>
                            <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_reportDataSources" />
                            </select>
                        </td>
                        <td class="table_edit_tr_td_label">用户名: </td>
                        <td class="table_edit_tr_td_input">
                            <input type="text" name="d_dsUser" id="d_dsUser" size="20" require="true"  maxlength="20" dataType="LimitContainChinese" min="1" max = "20" msg="用户名不能为空，最长为20个字符或6个中文"/>
                        </td>
                        <td class="table_edit_tr_td_label">密码:</td>
                        <td class="table_edit_tr_td_input">
                            <input type="password"   name="d_dsPass"  id="d_dsPass" size="20"  require="true" dataType="LimitB"  min="1" max = "20" msg="新增密码不能为空,最长为20个字符"/>
                        </td>
                    </tr>
                    <tr class="table_edit_tr">
                        <td class="table_edit_tr_td_label">备注:</td>
                        <td class="table_edit_tr_td_input">
                            <input type="text" name="d_remark" id="d_remark" size="39" maxlength="150" dataType="LimitContainChinese" min="1" max="150" msg="备注最大为50个汉字或150个字符，最小为1个字符" />
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
            <%--<c:set var="print" scope="request" value="1"/>--%>
            <%--<c:set var="export" scope="request" value="1"/>--%>
            <c:set var="addClickMethod" scope="request" value="setUpdatePrimaryKey('detailOp','d_dsId');"/>
            <c:set var="addAfterClickModifyMethod" scope="request" value="setMinNum('d_dsPass');"/>
            <c:set var="clickMethod" scope="request" value="btnClick('detailOp','clearStart','detail','','clearStartHead');"/>
            <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all" />
            <br/>
        </FORM>
    </body>
</html>
