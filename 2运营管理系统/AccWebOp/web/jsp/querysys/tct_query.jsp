<%-- 
    Document   : tct_query
    Created on : 2019-11-14
    Author     : luck
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>次票信息查询</title>


        <script language="javascript" type="text/javascript" charset="utf-8" src="js/common_form.js"></script>
        <script language="javascript" type="text/javascript" charset="utf-8" src="js/Validator.js"></script>
        <script language="JavaScript" src="js/CalendarPop.js"></script>
        <link rel="stylesheet" type="text/css" href="css/frame/simple.css" title="Style"/>
    </head>

    <body onload="preLoadVal('q_beginTime', 'q_endTime');
            initDocument('queryOp', 'detail');
            initDocument('detailOp', 'detail');
            setControlsDefaultValue('queryOp');
            setQueryControlsDefaultValue('queryOp', 'detailOp');
            setListViewDefaultValue('detailOp', 'clearStart');
            setPageControl('detailOp');
            setTableRowBackgroundBlock('DataTable')">
        <table  class="table_title">
            <tr align="center" class="trTitle">
                <td colspan="4">次票信息查询
                </td>
            </tr>
        </table>

        <!-- 表头 通用模板 -->
        <c:set var="pTitleName" scope="request" value="查询"/>
        <c:set var="pTitleWidth" scope="request" value="50"/>
        <c:import  url="/jsp/common/common_template.jsp?template_name=common_table_title" />

        <form method="post" name="queryOp" id="queryOp" action="TctQuery">
            <!-- 页面用到的变量 通用模板 -->

            <c:set var="divideShow" scope="request" value="1"/>
            <c:import  url="/jsp/common/common_template_web_variable.jsp?template_name=common_web_variable" />

            <table  class="table_edit">
                <tr class="table_edit_tr">


                    <td class="table_edit_tr_td_label">票卡号:</td>
                    <td class="table_edit_tr_td_input">
                        <input type="text" name="q_cardLogicalId" id="q_cardLogicalId" size="15" minlength="16" maxlength="16"  require="false" dataType="NumAndEng|LimitB" max="16" min="16" msg="票卡号为16位数字或英文" />
                    </td>

                    <td class="table_edit_tr_td_label" >发售开始日期:</td>
                    <td class="table_edit_tr_td_input">
                        <input type="text" name="q_beginTime" id="q_beginTime" size="10"  dataType="ICCSDateAndNull"  msg="开始日期格式为(yyyy-mm-dd)!" />
                        <a href="javascript:openCalenderDialogByID('q_beginTime','false');">
                            <img src="./images/calendar.gif" width="15" height="15" border="0" style="block"/>
                        </a>
                    </td>
                    <td class="table_edit_tr_td_label" >发售结束日期:</td>
                    <td class="table_edit_tr_td_input">
                        <input type="text" name="q_endTime" id="q_endTime" size="10"  dataType="ICCSDateAndNull|ThanDate" to="q_beginTime"  msg="开始日期不为空时结束日期不能为空且必须大于开始日期,格式为(yyyy-mm-dd)!" />
                        <a href="javascript:openCalenderDialogByID('q_endTime','false');">
                            <img src="./images/calendar.gif" width="15" height="15" border="0" style="block"/>
                        </a>
                    </td>
                    <td class="table_edit_tr_td_label"  >账户状态:</td>
                    <td class="table_edit_tr_td_input">
                        <select name="q_account_status" id="q_account_status">
                            <option value="">=请选择=</option>
                            <option value="0">已激活</option>
                            <option value="1">未激活</option>    
                            <option value="2">已过期</option>    
                        </select>
                    </td>
                </tr>
                <tr class="table_edit_tr">
                    <td class="table_edit_tr_td_label"  >清算标志:</td>
                    <td class="table_edit_tr_td_input">
                        <select name="q_status" id="q_status">
                            <option value="">=请选择=</option>
                            <option value="0">未清算</option>
                            <option value="1">已清算</option>                     
                        </select>
                    </td>
                    <td class="table_edit_tr_td_label">票卡主类型:</td>
                    <td class="table_edit_tr_td_input">
                        <select id="q_cardMainID" name="q_cardMainID" onchange="setSelectValues('queryOp', 'q_cardMainID', 'q_cardSubID', 'commonVariable');" >
                            <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_cardMain" />
                        </select>
                    </td>
                    <td class="table_edit_tr_td_label">票卡子类型:</td>
                    <td class="table_edit_tr_td_input">
                        <select id="q_cardSubID" name="q_cardSubID" >
                            <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_cardSub_none" />
                        </select>
                        <c:set var="pVarName" scope="request" value="commonVariable"/>
                        <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_cardSub" />
                    </td>    

                    <td class="table_edit_tr_td_label" rowspan="2"  >

                        <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all_init" />
                        <c:set var="query" scope="request" value="1"/>
                        <c:set var="addClickMethod" scope="request" value="setControlNames('queryOp','q_cardLogicalId#q_beginTime#q_endTime#q_status#q_cardMainID#q_cardSubID#q_account_status');setLineCardNames('queryOp','q_cardMainID','q_cardSubID','commonVariable','','','');"/>
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
            <div id="clearStartHead" class="divForTableBlockHead" style="width:160%;">
                <table class="table_list_block" id="DataTableHead" >

                    <!--说明：列的序号从0开始 isDigit：false 表示按字符串排序 ：true表示按数值排序 sortedby：asc表示按升序 dec表示按降序 -->
                    <tr  class="table_list_tr_head_block" id="ignore">
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="0" style="width: 160px" sortedby="asc" onclick="sortForTableBlock('clearStart');" >票卡号</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="1" style="width: 120px" sortedby="asc" onclick="sortForTableBlock('clearStart');" >票种</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="2" style="width: 120px" sortedby="asc" onclick="sortForTableBlock('clearStart');" >账号状态</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="3" style="width: 120px" sortedby="asc" onclick="sortForTableBlock('clearStart');" >发售时间</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="4" style="width: 120px" sortedby="asc" onclick="sortForTableBlock('clearStart');" >次票有效期开始时间</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="5" style="width: 120px" sortedby="asc" onclick="sortForTableBlock('clearStart');" >次票有效期结束时间</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=true index="6" style="width: 120px" sortedby="asc" onclick="sortForTableBlock('clearStart');" >次票当天消费次数</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=true index="7" style="width: 150px" sortedby="asc" onclick="sortForTableBlock('clearStart');" >次票当天消费金额(单位:元)</td>

                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=true index="8" style="width: 120px" sortedby="asc" onclick="sortForTableBlock('clearStart');" >次票累计消费次数</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=true index="9" style="width: 150px" sortedby="asc" onclick="sortForTableBlock('clearStart');" >次票累计消费金额(单位:元)</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=true index="10" style="width: 120px" sortedby="asc" onclick="sortForTableBlock('clearStart');" >剩余金额(单位:元)</td>

                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="11" style="width: 120px" sortedby="asc" onclick="sortForTableBlock('clearStart');" >清算标志</td>

                    </tr>


                </table>

            </div>
            <!-- <DIV id="clearStart" align="center" style="position:relative; left:20; width:95%; height:30%;min-height: 200px;max-height: 250px; layer-background-color:#99CC99; border:thin outset #ffffff; z-index:1; overflow: scroll;">-->
            <div id="clearStart"  class="divForTableBlockData" style="width: 160%;">
                <table class="table_list_block" id="DataTable" >

                    <c:forEach items="${ResultSet}" var="rs">
                        <!--class="listTableData" -->
                        <tr class="table_list_tr_data"  onMouseOver="overResultRow('detailOp', this);" 
                            onMouseOut="outResultRow('detailOp', this);" 
                            onclick="clickResultRow('detailOp', this, 'detail');
                                    setPageControl('detailOp');" 
                            id="${rs.cardLogicalId}#${rs.cardPhysicalId}">

                            <td  id="cardLogicalId" class="table_list_tr_col_data_block" style="width: 160px">
                                ${rs.cardLogicalId}
                            </td>
                            <td  id="cardSubName" class="table_list_tr_col_data_block" style="width: 120px">
                                ${rs.cardSubName}
                            </td>
                            <td  id="state" class="table_list_tr_col_data_block" style="width: 120px">
                                ${rs.state}
                            </td>
                            <td  id="saleDate" class="table_list_tr_col_data_block" style="width: 120px">
                                ${rs.saleDate}
                            </td>
                            <td  id="validDateStart" class="table_list_tr_col_data_block" style="width: 120px">
                                ${rs.validDateStart}
                            </td>
                            <td  id="validDateEnd" class="table_list_tr_col_data_block" style="width: 120px">
                                ${rs.validDateEnd}
                            </td>
                            <td  id="curConsumeCount" class="table_list_tr_col_data_block" style="width: 120px">
                                ${rs.curConsumeCount}
                            </td>
                            <td  id="curConsumeFee" class="table_list_tr_col_data_block" style="width: 150px">
                                ${rs.curConsumeFee}
                            </td>
                            <td  id="consumeCount" class="table_list_tr_col_data_block" style="width: 120px">
                                ${rs.consumeCount}
                            </td>
                            <td  id="consumeFee" class="table_list_tr_col_data_block" style="width: 150px">
                                ${rs.consumeFee}
                            </td>
                            <td  id="remainFee" class="table_list_tr_col_data_block" style="width: 120px">
                                ${rs.remainFee}
                            </td>
                            <td  id="balanceStatuesString" class="table_list_tr_col_data_block" style="width: 120px">
                                ${rs.balanceStatuesString}
                            </td>
                        </tr>

                    </c:forEach>

                </table>
            </div>
        </div>

        <FORM method="post" name="detailOp" id="detailOp" action="TctQuery" >

            <c:set var="divideShow" scope="request" value="1"/>
            <c:import  url="/jsp/common/common_template_web_variable.jsp?template_name=common_web_variable" />

            <!--导出全部参数 -->

            <input type="hidden" name="expAllFields" id="d_expAllFields" value="getCardLogicalId,getCardSubName,getState,getSaleDate,getValidDateStart,getValidDateEnd,getCurConsumeCount,getCurConsumeFee,getConsumeCount,getConsumeFee,getRemainFee,getBalanceStatuesString" />
            <input type="hidden" name="expFileName" id="d_expFileName" value="次票信息查询.xlsx" />
            <input type="hidden" name="divId" id="d_divId" value="clearStartHead" />
            <input type="hidden" name="methodUrl" id="d_methodUrl" value="/TctQueryExportAll" />

            <c:import url="/jsp/common/common_template.jsp?template_name=version_eval" />




            <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all_init" />

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
