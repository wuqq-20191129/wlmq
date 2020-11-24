<%-- 
    Document   : query_logic_list
    Created on : 2017-6-18
    Author     : luck
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>物理逻辑卡号对照表查询</title>


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
                <td colspan="4">物理逻辑卡号对照表查询
                </td>
            </tr>
        </table>

        <!-- 表头 通用模板 -->
        <c:set var="pTitleName" scope="request" value="查询"/>
        <c:set var="pTitleWidth" scope="request" value="50"/>
        <c:import  url="/jsp/common/common_template.jsp?template_name=common_table_title" />

        <form method="post" name="queryOp" id="queryOp" action="QueryPhyLogicList">
            <!-- 页面用到的变量 通用模板 -->

            <c:set var="divideShow" scope="request" value="1"/>
            <c:import  url="/jsp/common/common_template_web_variable.jsp?template_name=common_web_variable" />

            <table  class="table_edit">
                <tr class="table_edit_tr">
                    <td class="table_edit_tr_td_label" >物理卡号:</td>
                    <td class="table_edit_tr_td_input">
                        <input type="text" name="q_physicNo" id="q_physicNo" maxlength="20" size="20" require="false"  dataType="NumAndEng"   msg="物理卡号应为英文或数字字符" />
                    </td>
                    <td class="table_edit_tr_td_label" >逻辑卡号:</td>
                    <td class="table_edit_tr_td_input">
                        <input type="text" name="q_logicNo" id="q_logicNo" maxlength="20" size="20" require="false"  dataType="NumAndEng"   msg="逻辑卡号应为英文或数字字符" />
                    </td>
                    <td class="table_edit_tr_td_label" >流水号:</td>
                    <td class="table_edit_tr_td_input">
                        <input type="text" name="q_waterNo" id="q_waterNo" maxlength="10" size="10" require="false"  dataType="NumAndEng"   msg="流水号应为英文或数字字符" />
                    </td>

                    <td class="table_edit_tr_td_label" rowspan="2"  >

                        <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all_init" />
                        <c:set var="query" scope="request" value="1"/>
                        <c:set var="addClickMethod" scope="request" value="setControlNames('queryOp','q_physicNo#q_logicNo#q_waterNo');"/>
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

                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="0" style="width: 100px" sortedby="asc" onclick="sortForTableBlock('clearStart');" >物理卡号</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="1" style="width: 100px" sortedby="asc" onclick="sortForTableBlock('clearStart');" >逻辑卡号</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="2" style="width: 100px" sortedby="asc" onclick="sortForTableBlock('clearStart');" >流水号</td>                        
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
                            id="${rs.physic_no}#${rs.logic_no}#${rs.water_no}">


                            <td  id="physic_no" class="table_list_tr_col_data_block" style="width: 100px">
                                ${rs.physic_no}
                            </td>
                            <td  id="login_no" class="table_list_tr_col_data_block" style="width: 100px">
                                ${rs.logic_no}
                            </td>
                            <td  id="water_no" class="table_list_tr_col_data_block" style="width: 100px">
                                ${rs.water_no}
                            </td>

                        </tr>

                    </c:forEach>

                </table>
            </div>
        </div>

        <FORM method="post" name="detailOp" id="detailOp" action="QueryPhyLogicList" >

            <c:set var="divideShow" scope="request" value="1"/>
            <c:import  url="/jsp/common/common_template_web_variable.jsp?template_name=common_web_variable" />
            <!--导出全部参数 -->
            <input type="hidden" name="expAllFields" id="d_expAllFields" value="PHYSIC_NO,LOGIC_NO,WATER_NO" />
            <input type="hidden" name="expFileName" id="d_expFileName" value="物理逻辑卡号对照表查询.xlsx" />
            <input type="hidden" name="divId" id="d_divId" value="clearStartHead" />
            <input type="hidden" name="methodUrl" id="d_methodUrl" value="/QueryPhyLogicListExportAll" />


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
