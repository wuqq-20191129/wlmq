<%-- 
    Document   : card_exp_date
    Created on : 2017-6-18
    Author     : luck
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>票卡有效期</title>


        <script language="javascript" type="text/javascript" charset="utf-8" src="js/common_form.js"></script>
        <script language="javascript" type="text/javascript" charset="utf-8" src="js/Validator.js"></script>
        <script language="JavaScript" src="js/CalendarPop.js"></script>
        <link rel="stylesheet" type="text/css" href="css/frame/simple.css" title="Style"/>
    </head>

    <body onload="initDocument('queryOp', 'detail');
            initDocument('detailOp', 'detail');
            setControlsDefaultValue('queryOp');
            setQueryControlsDefaultValue('queryOp', 'detailOp');
            setListViewDefaultValue('detailOp', 'clearStart');
            setPageControl('detailOp');
            setTableRowBackgroundBlock('DataTable')">
        <table  class="table_title">
            <tr align="center" class="trTitle">
                <td colspan="4">票卡有效期
                </td>
            </tr>
        </table>

        <!-- 表头 通用模板 -->
        <c:set var="pTitleName" scope="request" value="查询"/>
        <c:set var="pTitleWidth" scope="request" value="50"/>
        <c:import  url="/jsp/common/common_template.jsp?template_name=common_table_title" />

        <form method="post" name="queryOp" id="queryOp" action="CardExpDate">
            <!-- 页面用到的变量 通用模板 -->

            <c:set var="divideShow" scope="request" value="1"/>
            <c:import  url="/jsp/common/common_template_web_variable.jsp?template_name=common_web_variable" />

            <table  class="table_edit">
                <tr class="table_edit_tr">

                    <td class="table_edit_tr_td_label">票卡主类型:</td>
                    <td class="table_edit_tr_td_input">
                        <select id="q_cardMainID" name="q_cardMainID" onchange="setSelectValues('queryOp', 'q_cardMainID', 'q_cardSubID', 'commonVariable1');" >
                            <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_cardMain" />
                        </select>
                    </td>
                    <td class="table_edit_tr_td_label">票卡子类型:</td>
                    <td class="table_edit_tr_td_input">
                        <select id="q_cardSubID" name="q_cardSubID" >
                            <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_cardSub_none" />
                        </select>
                        <c:set var="pVarName" scope="request" value="commonVariable1"/>
                        <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_cardSub" />
                    </td>    
                    <td class="table_edit_tr_td_label">有效期(分钟):</td>
                    <td class="table_edit_tr_td_input">
                        <input name="exp_date" id="exp_date" type="text" size="15"  require="false" dataType="double" msg="有效期(天数)为大于等于零的数字！" />
                    </td>

                    <td class="table_edit_tr_td_label" rowspan="2"  >

                        <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all_init" />
                        <c:set var="query" scope="request" value="1"/>
                        <c:set var="addClickMethod" scope="request" value="setControlNames('queryOp','q_cardMainID#q_cardSubID#exp_date');setLineCardNames('queryOp','q_cardMainID','q_cardSubID','commonVariable1','','','');"/>
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

                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="0" style="width: 100px" sortedby="asc" onclick="sortForTableBlock('clearStart');" >票种主类型</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="1" style="width: 100px" sortedby="asc" onclick="sortForTableBlock('clearStart');" >票种子类型</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=true index="2" style="width: 150px" sortedby="asc" onclick="sortForTableBlock('clearStart');" >有效期(单位：分钟)</td>
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
                            onclick="clickResultRow('detailOp', this, 'detail');
                                    setPageControl('detailOp');" 
                            id="${rs.card_main_id}#${rs.card_sub_id}#${rs.exp_date}">


                            <td  id="card_main_id_text" class="table_list_tr_col_data_block" style="width: 100px">
                                ${rs.card_main_id_text}
                            </td>
                            <td  id="card_sub_id_text" class="table_list_tr_col_data_block" style="width: 100px">
                                ${rs.card_sub_id_text}
                            </td>
                            <td  id="exp_date" class="table_list_tr_col_data_block" style="width: 150px">
                                ${rs.exp_date}
                            </td>

                        </tr>

                    </c:forEach>

                </table>
            </div>
        </div>

        <FORM method="post" name="detailOp" id="detailOp" action="CardExpDate" >

            <c:set var="divideShow" scope="request" value="1"/>
            <c:import  url="/jsp/common/common_template_web_variable.jsp?template_name=common_web_variable" />

            <!--导出全部参数 -->
            <input type="hidden" name="expAllFields" id="d_expAllFields" value="CARD_MAIN_ID_TEXT,CARD_SUB_ID_TEXT,EXP_DATE" />
            <input type="hidden" name="expFileName" id="d_expFileName" value="票卡有效期.xlsx" />
            <input type="hidden" name="divId" id="d_divId" value="clearStartHead" />
            <input type="hidden" name="methodUrl" id="d_methodUrl" value="/QueryCardExpDateExportAll" />

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
