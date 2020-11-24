<%-- 
    Document   : traffic_fare_param
    Created on : 2017-6-16
    Author     : luck
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>乘车费率参数</title>


        <script language="javascript" type="text/javascript" charset="utf-8" src="js/common_form.js"></script>
        <script language="javascript" type="text/javascript" charset="utf-8" src="js/Validator.js"></script>
        <script language="JavaScript" src="js/CalendarPop.js"></script>
        <link rel="stylesheet" type="text/css" href="css/frame/simple.css" title="Style"/>
    </head>

    <script language="javascript">

        function selCheck(checkboxId, rtnId,inputId) {
            var checkBtn = document.getElementById(checkboxId);
            if (checkBtn.checked === true) {
                document.getElementById(rtnId).disabled = false;
                document.getElementById(inputId).disabled = false;
            } else {
                document.getElementById(rtnId).disabled = true;
                document.getElementById(inputId).disabled = true;
                document.getElementById(inputId).value = "0";
            }
        }

        function formInit() {

            if (document.getElementById('balaCheckFlag').value === "true") {
                document.getElementById('balaCheck').value = "1";
                document.getElementById('balaCheck').checked = true;
            }
            if (document.getElementById('balaCheck').checked) {
                document.getElementById('q_rtnOper').disabled = false;
                document.getElementById('distince').disabled = false;
            }
            if (document.getElementById('distince').value === 'null')
                document.getElementById('distince').value = "0";

            if (document.getElementById('balaCheckFlag1').value === "true") {
                document.getElementById('balaCheck1').value = "1";
                document.getElementById('balaCheck1').checked = true;
            }
            if (document.getElementById('balaCheck1').checked) {
                document.getElementById('q_rtnOper1').disabled = false;
                document.getElementById('fare').disabled = false;
            }
            if (document.getElementById('fare').value === 'null')
                document.getElementById('fare').value = "0";
        }
    </script>

    <body onload="initDocument('queryOp', 'detail');
            initDocument('detailOp', 'detail');
            setControlsDefaultValue('queryOp');
            setQueryControlsDefaultValue('queryOp', 'detailOp');
            setListViewDefaultValue('detailOp', 'clearStart');
            setPageControl('detailOp');
            formInit();
            setTableRowBackgroundBlock('DataTable')">
        <table  class="table_title">
            <tr align="center" class="trTitle">
                <td colspan="4">乘车费率参数
                </td>
            </tr>
        </table>

        <!-- 表头 通用模板 -->
        <c:set var="pTitleName" scope="request" value="查询"/>
        <c:set var="pTitleWidth" scope="request" value="50"/>
        <c:import  url="/jsp/common/common_template.jsp?template_name=common_table_title" />

        <form method="post" name="queryOp" id="queryOp" action="TrafficFareParam">
            <!-- 页面用到的变量 通用模板 -->

            <c:set var="divideShow" scope="request" value="1"/>
            <c:import  url="/jsp/common/common_template_web_variable.jsp?template_name=common_web_variable" />

            <table  class="table_edit">
                <tr class="table_edit_tr">
                    <td class="table_edit_tr_td_label" >站点距离:</td>
                    <td class="table_edit_tr_td_input">
                        <input type="hidden" id="balaCheckFlag" name="balaCheckFlag" value="${checkboxflg}" />
                        <input type="checkbox" id="balaCheck" name="balaCheck" value="0" onclick="selCheck('balaCheck', 'q_rtnOper','distince');"/>
                        <select id="q_rtnOper" name="q_rtnOper"  disabled="true" style="width: 20%">
                            <option value="1">&gt;</option>
                            <option value="2">&gt;=</option>
                            <option value="3">=</option>
                            <option value="4">&lt;=</option>
                            <option value="5">&lt;</option>
                        </select>
                        <input name="distince" id="distince" disabled="true" type="text" size="15" require="false" dataType="double" msg="站点距离为大于零的数字！" value="0"/>
                    </td>
                    <td class="table_edit_tr_td_label" >乘车资费:</td>
                    <td class="table_edit_tr_td_input">
                        <input type="hidden" id="balaCheckFlag1" name="balaCheckFlag1" value="${checkboxflg1}" />
                        <input type="checkbox" id="balaCheck1" name="balaCheck1" value="0" onclick="selCheck('balaCheck1', 'q_rtnOper1','fare');"/>
                        <select id="q_rtnOper1" name="q_rtnOper1"  disabled="true" style="width: 20%">
                            <option value="1">&gt;</option>
                            <option value="2">&gt;=</option>
                            <option value="3">=</option>
                            <option value="4">&lt;=</option>
                            <option value="5">&lt;</option>
                        </select>
                        <input name="fare" id="fare" type="text" disabled="true" size="15" require="false" dataType="double" msg="乘车资费为大于零的数字！" value="0"/>
                    </td>
                    <td class="table_edit_tr_td_label">票卡主类型:</td>
                    <td class="table_edit_tr_td_input">
                        <select id="q_cardMainID" name="q_cardMainID" onchange="setSelectValues('queryOp', 'q_cardMainID', 'q_cardSubID', 'commonVariable1');" >
                            <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_cardMain" />
                        </select>
                    </td>

                </tr>
                <tr class="table_edit_tr">
                    <td class="table_edit_tr_td_label">票卡子类型:</td>
                    <td class="table_edit_tr_td_input">
                        <select id="q_cardSubID" name="q_cardSubID" >
                            <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_cardSub_none" />
                        </select>
                        <c:set var="pVarName" scope="request" value="commonVariable1"/>
                        <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_cardSub" />
                    </td>    
                    <td class="table_edit_tr_td_label"><div align="right">乘车费率类型:</div></td>
                    <td class="table_edit_tr_td_input">
                        <select id="q_fareRideType" name="q_fareRideType" dataType="Require" msg="乘车费率不能为空">
                            <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_fareRideType" />
                        </select>
                        <c:set var="pVarName" scope="request" value="commonVariable"/>
                        <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_station" />
                    </td>
                    <td class="table_edit_tr_td_label" rowspan="2"  >

                        <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all_init" />
                        <c:set var="query" scope="request" value="1"/>
                        <c:set var="addClickMethod" scope="request" value="setControlNames('queryOp','distince#fare#q_cardMainID#q_cardSubID#q_fareRideType#q_rtnOper#q_rtnOper1');setLineCardNames('queryOp','q_cardMainID','q_cardSubID','commonVariable1','','','');"/>
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

                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=true index="0" style="width: 100px" sortedby="asc" onclick="sortForTableBlock('clearStart');" >站点距离（公里）</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=true index="1" style="width: 100px" sortedby="asc" onclick="sortForTableBlock('clearStart');" >乘车资费（元）</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="2" style="width: 100px" sortedby="asc" onclick="sortForTableBlock('clearStart');" >票卡主类型</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="3" style="width: 100px" sortedby="asc" onclick="sortForTableBlock('clearStart');" >票卡子类型</td>
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
                            id="${rs.distince}#${rs.fare}#${rs.card_main_id}#${rs.card_sub_id}}">


                            <td  id="distince" class="table_list_tr_col_data_block" style="width: 100px">
                                ${rs.distince}
                            </td>
                            <td  id="fare" class="table_list_tr_col_data_block" style="width: 100px">
                                ${rs.fare}
                            </td>
                            <td  id="card_main_id_text" class="table_list_tr_col_data_block" style="width: 100px">
                                ${rs.card_main_id_text}
                            </td>
                            <td  id="card_sub_id_text" class="table_list_tr_col_data_block" style="width: 100px">
                                ${rs.card_sub_id_text}
                            </td>
                        </tr>

                    </c:forEach>

                </table>
            </div>
        </div>

        <FORM method="post" name="detailOp" id="detailOp" action="TrafficFareParam" >

            <c:set var="divideShow" scope="request" value="1"/>
            <c:import  url="/jsp/common/common_template_web_variable.jsp?template_name=common_web_variable" />
            <!--导出全部参数 -->
            <input type="hidden" name="expAllFields" id="d_expAllFields" value="DISTINCE,FARE,CARD_MAIN_ID_TEXT,CARD_SUB_ID_TEXT" />
            <input type="hidden" name="expFileName" id="d_expFileName" value="乘车费率参数.xlsx" />
            <input type="hidden" name="divId" id="d_divId" value="clearStartHead" />
            <input type="hidden" name="methodUrl" id="d_methodUrl" value="/QueryTrafficFareParamExportAll" />


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
