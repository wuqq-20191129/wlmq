<%--
    Document   : balck_list_mtr
    Created on : 2017-6-7
    Author     : zhongziqi
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>地铁黑名单</title>
        <script language="javascript" type="text/javascript" charset="utf-8" src="js/common_form.js"></script>
        <script language="javascript" type="text/javascript" charset="utf-8" src="js/Validator.js"></script>
        <script language="javascript" type="text/javascript" charset="utf-8" src="js/BlackList.js"></script>
        <script language="JavaScript" src="js/CalendarPop.js"></script>
        <link rel="stylesheet" type="text/css" href="css/frame/simple.css" title="Style"/>

    </head>
    <!--删除 hejj setInvisable('detailOp','clone');  -->
    <body onload="initDocument('queryOp', 'detail');
            initDocument('detailOp', 'detail');
            setPrimaryKeys('detailOp', 'd_cardLogicalId');
            setControlsDefaultValue('queryOp');
            setQueryControlsDefaultValue('queryOp', 'detailOp');
            setListViewDefaultValue('detailOp', 'clearStart');
            setPageControl('detailOp');
            setCurDate('detailOp','curDate');
            setTableRowBackgroundBlock('DataTable');">

        <table  class="table_title">
            <tr align="center" class="trTitle">
                <td colspan="4">
                    黑名单管理 地铁黑名单
                </td>
            </tr>
        </table>

        <!-- 表头 通用模板 -->

        <c:set var="pTitleName" scope="request" value="查询"/>
        <c:set var="pTitleWidth" scope="request" value="50"/>
        <c:import  url="/jsp/common/common_template.jsp?template_name=common_table_title" />

        <form method="post" name="queryOp" id="queryOp" action="BlackListMtr">
            <!-- 页面用到的变量 通用模板 -->
            <c:set var="divideShow" scope="request" value="1"/>
            <c:import  url="/jsp/common/common_template_web_variable.jsp?template_name=common_web_variable" />
            <table  class="table_edit">
                <tr class="table_edit_tr">
                    <td class="table_edit_tr_td_label">逻辑卡号:</td>
                    <td class="table_edit_tr_td_input">
                        <input type="text" name="q_cardLogicalId" id="q_cardLogicalId" size="25"  maxLength="20" onpaste="removeBlankSpace('queryOp','q_cardLogicalId');" require="false" dataType="integer"  msg="逻辑卡号只能为数字，最大长度为20位"/>
                    </td>
                    <td class="table_edit_tr_td_label">生效开始日期: </td>
                    <td class="table_edit_tr_td_input">
                        <input type="text" id="q_beginDatetime" name="q_beginDatetime"   size="10" require="false" dataType="ICCSDate"  msg="开始日期格式为[yyyy-mm-dd]"/>
                        <a href="javascript:openCalenderDialogByID('q_beginDatetime','false');">
                            <img src="./images/calendar.gif" width="15" height="15" border="0" style="block"/>
                        </a>
                    </td>
                    <td class="table_edit_tr_td_label">生效结束日期: </td>
                    <td class="table_edit_tr_td_input">
                        <input type="text" id="q_endDatetime" name="q_endDatetime"   size="10" require="false" dataType="ICCSDate|ThanDate" to="q_beginDatetime" msg="结束日期格式为[yyyy-mm-dd]且大于等于开始日期"/>
                        <a href="javascript:openCalenderDialogByID('q_endDatetime','false');">
                            <img src="./images/calendar.gif" width="15" height="15" border="0" style="block"/>
                        </a>
                    </td>
                </tr>
                <tr class="table_edit_tr">
                    <td class="table_edit_tr_td_label">产生原因: </td>

                    <td class="table_edit_tr_td_input">
                        <select id="q_blackType" name="q_blackType"  >
                            <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_blackReson" />
                        </select>
                    </td>
                    <td class="table_edit_tr_td_label">操作员代码: </td>
                    <td class="table_edit_tr_td_input">
                        <select id="q_operatorId" name="q_operatorId"  >
                            <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_operatorId" />
                        </select>
                    </td>
                    <td class="table_edit_tr_td_label" rowspan="2">
                        <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all_init" />
                        <c:set var="query" scope="request" value="1"/>
                        <c:set var="addClickMethod" scope="request" value="removeBlankSpaceBeforeQuery('queryOp',['q_cardLogicalId']);setControlNames('queryOp','q_cardLogicalId#q_beginDatetime#q_endDatetime#q_blackType#q_operatorId');"/>
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
                            <input type="checkbox" name="rectNoAll" id ="rectNoAll" onclick="selectAllRecord('detailOp', 'rectNoAll', 'rectNo', 'clearStart', 0);"/>
                        </td>
                        <td id="orderTd"    style="width: 120px" class="table_list_tr_col_head_block"  isDigit=true index="1"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >逻辑卡号</td>
                        <td id="orderTd"    style="width: 120px" class="table_list_tr_col_head_block"  isDigit=false index="2"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >产生时间</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="3"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >产生原因</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="4"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >操作员代码</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="5"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >生效时间</td>
                        <td id="orderTd"    style="width: 120px" class="table_list_tr_col_head_block"  isDigit=false index="6"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >设备处理标志</td>
                        <td id="orderTd"    style="width: 250px" class="table_list_tr_col_head_block"  isDigit=false index="7"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >备注</td>

                    </tr>
                </table>

            </div>
            <div id="clearStart"  class="divForTableBlockData">
                <table class="table_list_block" id="DataTable" >
                    <c:forEach items="${ResultSet}" var="rs">
                        <!--class="listTableData" -->
                        <!--删除 hejj setSelectValuesByRow('detailOp', 'd_cardLogicalId', 'commonVariable'); -->
                        <tr class="table_list_tr_data"  onMouseOver="overResultRow('detailOp', this);"
                            onMouseOut="outResultRow('detailOp', this);"
                            onclick="clickOneResultRow('detailOp', this, 'detail','rectNo');
                                    setPageControl('detailOp');"
                            id="${rs.cardLogicalId}">

                            <td id="rectNo1" class="table_list_tr_col_data_block">
                                <input type="checkbox" name="rectNo" onclick="unSelectAllRecord('detailOp', 'rectNoAll', 'rectNo');"
                                       value="${rs.cardLogicalId}">
                                </input>
                            </td>
                            <td  id="cardLogicalId" class="table_list_tr_col_data_block" style="width: 120px">
                                ${rs.cardLogicalId}
                            </td>
                            <td  id="createDatetime" class="table_list_tr_col_data_block" style="width: 120px">
                                ${rs.createDatetime}
                            </td>
                            <td  id="blackType" class="table_list_tr_col_data_block">
                                ${rs.blackTypeText}
                            </td>
                            <td  id="operatorId" class="table_list_tr_col_data_block">
                                ${rs.operatorId}
                            </td>
                            <td  id="genDate" class="table_list_tr_col_data_block">
                                ${rs.genDatetime}
                            </td>

                            <td  id="actionType"  style="width: 120px" class="table_list_tr_col_data_block">
                                ${rs.actionType}
                            </td>
                            <td  id="remark" class="table_list_tr_col_data_block" style="width: 250px">
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

        <FORM method="post" name="detailOp" id="detailOp" action="BlackListMtr">
            <c:set var="divideShow" scope="request" value="1"/>
            <c:import  url="/jsp/common/common_template_web_variable.jsp?template_name=common_web_variable" />
			<!--导出全部参数 -->
<!--             <input type="hidden" name="expAllFields" id="d_expAllFields" value="CARD_LOGICAL_ID,CREATE_DATETIME,BLACK_TYPE,OPERATOR_ID,GEN_DATETIME,ACTION_TYPE,REMARK" /> -->
            <input type="hidden" name="expAllFields" id="d_expAllFields" value="getCardLogicalId,getCreateDatetime,getBlackTypeText,getOperatorId,getGenDatetime,getActionType,getRemark" />
			<input type="hidden" name="expFileName" id="d_expFileName" value="地铁黑名单.xlsx" />
			<input type="hidden" name="divId" id="d_divId" value="clearStartHead" />
			<input type="hidden" name="methodUrl" id="d_methodUrl" value="/BlackListMtrExportAll" />
            <DIV id="detail"  class="divForTableDataDetail" >
                <table  class="table_edit_detail">
                    <tr class="table_edit_tr">
                        <td class="table_edit_tr_td_label">逻辑卡号</td>
                        <td class="table_edit_tr_td_input">
                            <input type="text" name="d_cardLogicalId" id="d_cardLogicalId" size="25" maxLength="20" dataType="integer|NotEmpty"  msg="逻辑卡号只能为数字，最大长度为20位"/>
                        </td>
                        <td class="table_edit_tr_td_label">产生原因: </td>

                        <td class="table_edit_tr_td_input">
                            <select id="d_blackType" name="d_blackType" dataType="LimitContainChinese" min="1" msg="请选择产生原因" >
                                <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_blackReson" />
                            </select>
                        </td>
                        <td class="table_edit_tr_td_label">生效时间:</td>
                        <td class="table_edit_tr_td_input">
                            <input type="text" name="d_genDate" id="d_genDate" size="10"  dataType="ICCSDate|ThanDate"  to="curDate" msg="配置日期格式为[yyyy-mm-dd],且大于当前日期（产生时间）"/>
                            <a href="javascript:openCalenderDialogByID('d_genDate','false');">
                                <img src="./images/calendar.gif" width="15" height="15" border="0" style="block"/>
                            </a>
                        </td>
                        <input type="hidden" name="curDate" id="d_curDate" value="" />
                    </tr>

                    <tr class="table_edit_tr">
                        <td class="table_edit_tr_td_label">备注:</td>
                        <td class="table_edit_tr_td_input">
                            <input type="text" name="d_remark" id="d_remark" size="25" maxlength="150" dataType="LimitContainChinese" max="150" msg="备注最大为50个汉字或150个字符" />
                        </td>
                        <td class="table_edit_tr_td_label">设备处理标志:</td>
                        <td class="table_edit_tr_td_input">
                            <input type="text" name="d_actionType" id="d_actionType" size="15" value="000" maxlength="3" dataType="LimitB|NumAndEng"  msg="设备处理标志最大为3个英文或数字字符" />
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
            <c:set var="expAll" scope="request" value="1" />
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
