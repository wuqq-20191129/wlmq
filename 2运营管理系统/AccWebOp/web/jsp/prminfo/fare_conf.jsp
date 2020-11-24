<%-- 
    Document   : fare_conf
    Created on : 2017-6-18
    Author     : mqf
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>收费配置表</title>


        <script language="javascript" type="text/javascript" charset="utf-8" src="js/common_form.js"></script>
        <script language="javascript" type="text/javascript" charset="utf-8" src="js/Validator.js"></script>
        <script language="JavaScript" src="js/CalendarPop.js"></script>
        <link rel="stylesheet" type="text/css" href="css/frame/simple.css" title="Style"/>
    </head>
    <c:choose>
        <c:when test="${version.version_no == '0000000000'}">
            <body onload="initDocument('queryOp', 'detail');
                    initDocument('detailOp', 'detail');
                    setInvisable('detailOp', 'clone');
                    setPrimaryKeys('detailOp', 'd_card_main_id#d_card_sub_id#d_time_code#d_fare_table_id');
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
                    setPrimaryKeys('detailOp', 'd_card_main_id#d_card_sub_id#d_time_code#d_fare_table_id');
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
                <td colspan="4">收费配置表（
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

        <form method="post" name="queryOp" id="queryOp" action="FareConf">
            <!-- 页面用到的变量 通用模板 -->

            <c:set var="divideShow" scope="request" value="1"/>
            <c:import  url="/jsp/common/common_template_web_variable.jsp?template_name=common_web_variable" />

            <table  class="table_edit">
                <tr class="table_edit_tr">
                    <td class="table_edit_tr_td_label" >票卡类型:</td>
                    <td class="table_edit_tr_td_input" style="width:15%">
                        <select id="q_card_main_id" name="q_card_main_id" onChange="setSelectValues('queryOp', 'q_card_main_id', 'q_card_sub_id', 'commonVariable');"  >				    
                            <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_cardMain" />
                        </select>
                        
                        <select id="q_card_sub_id" name="q_card_sub_id" >
                            <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_none" />
                        </select>
                        <c:set var="pVarName" scope="request" value="commonVariable"/>
                        <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_cardSub" />
                    </td>
                    
                
                    <td class="table_edit_tr_td_label">乘车时间类型:</td>
                    <td class="table_edit_tr_td_input">
                        <select id="q_time_code" name="q_time_code" >
                            <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_timeCode" />
                        </select>
                    </td>
                    
                    <td class="table_edit_tr_td_label">票价表ID:</td>
                    <td class="table_edit_tr_td_input">
                        <select id="q_fare_table_id" name="q_fare_table_id" >
                            <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_fareTable" />
                        </select>
                    </td>
                </tr>
                <tr class="table_edit_tr">
                    <td class="table_edit_tr_td_label">联乘时间间隔代码:</td>
                    <td class="table_edit_tr_td_input">
                        <select id="q_fare_time_id" name="q_fare_time_id" >
                            <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_fareTimeId" />
                        </select>
                    </td>
                    <td class="table_edit_tr_td_label">累计消费额代码:</td>
                    <td class="table_edit_tr_td_input">
                        <select id="q_fare_deal_id" name="q_fare_deal_id" >
                            <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_fareDealId" />
                        </select>
                    </td>
                    <td class="table_edit_tr_td_label" rowspan="2">

                        <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all_init" />
                        <c:set var="query" scope="request" value="1"/>
                        <c:set var="addClickMethod" scope="request" value="setControlNames('queryOp','q_card_main_id#q_card_sub_id#q_time_code#q_fare_table_id#q_fare_time_id#q_fare_deal_id');setLineCardNames('queryOp','q_card_main_id','q_card_sub_id','commonVariable','','','');"/>
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
                            <input type="checkbox" name="rectNoAll" id="rectNoAll" onclick="selectAllRecord('detailOp', 'rectNoAll', 'rectNo', 'clearStart', 0);"/>
                        </td>	
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="1"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 100px">票卡主类型</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="2"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 100px">票卡子类型</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="3"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 100px">乘车时间类型</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="4"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >票价表ID</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="5"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 130px" >联乘时间间隔代码</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="6"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 130px" >累计消费额代码</td>
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
                            onclick="setSelectValuesByRowPropertyName('detailOp','d_card_sub_id','commonVariable','typeIDs');clickResultRow('detailOp', this, 'detail');setPageControl('detailOp');" 
                            id="${rs.time_code}#${rs.card_main_id}#${rs.card_sub_id}#${rs.fare_table_id}#${rs.version_no}#${rs.record_flag}"
                            typeIDs="${rs.card_main_id}#${rs.card_sub_id}">

                            <td id="rectNo1" class="table_list_tr_col_data_block">
                                <input type="checkbox" name="rectNo" onclick="unSelectAllRecord('detailOp', 'rectNoAll', 'rectNo');"
                                       value="${rs.time_code}#${rs.card_main_id}#${rs.card_sub_id}#${rs.fare_table_id}">

                                </input>
                            </td>
                            <td  id="card_main_id" class="table_list_tr_col_data_block" style="width: 100px">
                                ${rs.card_main_id_text}
                            </td>
                            <td  id="card_sub_id" class="table_list_tr_col_data_block" style="width: 100px">
                                ${rs.card_sub_id_text}
                            </td>
                            <td  id="time_code" class="table_list_tr_col_data_block" style="width: 100px">
                                ${rs.time_code_text}
                            </td>
                            <td  id="fare_table_id" class="table_list_tr_col_data_block">
                                ${rs.fare_table_id}
                            </td>
                            <td  id="fare_time_id" class="table_list_tr_col_data_block" style="width: 130px">
                                ${rs.fare_time_id}
                            </td>
                            <td  id="fare_deal_id" class="table_list_tr_col_data_block" style="width: 130px">
                                ${rs.fare_deal_id}
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

        <FORM method="post" name="detailOp" id="detailOp" action="FareConf" >

            <c:set var="divideShow" scope="request" value="1"/>
            <c:import  url="/jsp/common/common_template_web_variable.jsp?template_name=common_web_variable" />
            
            <!--导出全部参数 -->
            <input type="hidden" name="expAllFields" id="d_expAllFields" value="getCard_main_id_text,getCard_sub_id_text,getTime_code_text,getFare_table_id,getFare_time_id,getFare_deal_id" />
            <input type="hidden" name="expFileName" id="d_expFileName" value="收费配置表.xlsx" />
            <input type="hidden" name="divId" id="d_divId" value="clearStartHead" />
            <input type="hidden" name="methodUrl" id="d_methodUrl" value="/FareConfExportAll" />

            <!--<DIV id="detail" align="center" class="divForTableData" style="position:relative; left:20; width:95%; height:10; layer-background-color:#99CC99; border:thin outset #ffffff; z-index:1;">-->
            <DIV id="detail"  class="divForTableDataDetail" >
                <table  class="table_edit_detail">
                    <tr class="table_edit_tr" >
                        <td class="table_edit_tr_td_label">票卡类型: </td>
                        <td class="table_edit_tr_td_input" style="width:15%">
                            <select  id="d_card_main_id" name="d_card_main_id"  onChange="setSelectValues('detailOp', 'd_card_main_id', 'd_card_sub_id', 'commonVariable');" dataType="Require" msg="票卡类型不能为空!">
                                <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_cardMain" />
                            </select>	
                            
                            <select id="d_card_sub_id" name="d_card_sub_id" dataType="Require"  msg="票种子类型不能为空!">
                                <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_none" />

                            </select>
                            <c:set var="pVarName" scope="request" value="commonVariable"/>
                            <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_cardSub" />
                            
                        </td>
                        
                        <td class="table_edit_tr_td_label">乘车时间类型: </td>
                        <td class="table_edit_tr_td_input">
                            <select id="d_time_code" name="d_time_code" dataType="NotNull" msg="乘车时间类型不能为空!">
                                <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_timeCode" />
                            </select>	
                        </td>
                        
                        <td class="table_edit_tr_td_label">票价表ID: </td>
                        <td class="table_edit_tr_td_input">
                            <select id="d_fare_table_id" name="d_fare_table_id" dataType="Require" msg="票价表ID不能为空!">
                                <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_fareTable" />
                            </select>	
                        </td>
                    </tr>

                    <tr class="table_edit_tr">
                        <td class="table_edit_tr_td_label">联乘时间间隔代码:</td>
                        <td class="table_edit_tr_td_input">
                            <select id="d_fare_time_id" name="d_fare_time_id" dataType="Require" msg="联乘时间间隔代码不能为空!">
                                <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_fareTimeId" />
                            </select>
                        </td>
                        <td class="table_edit_tr_td_label">累计消费额代码:</td>
                        <td class="table_edit_tr_td_input">
                            <select id="d_fare_deal_id" name="d_fare_deal_id" dataType="Require" msg="累计消费额代码不能为空!">
                                <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_fareDealId" />
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
            <c:set var="expAll" scope="request" value="1" />
            <c:set var="btNext" scope="request" value="1"/>
            <c:set var="btNextEnd" scope="request" value="1"/>
            <c:set var="btBack" scope="request" value="1"/>
            <c:set var="btBackEnd" scope="request" value="1"/>
            <c:set var="btGoPage" scope="request" value="1"/>

            <%--<c:set var="addQueryMethod" scope="request" value="setLineCardNames('detailOp','q_lineID','q_stationID','commonVariable','','','');"/>--%>
            <c:set var="addClickMethod" scope="request" value="setUpdatePrimaryKey('detailOp','d_time_code#d_card_main_id#d_card_sub_id#d_fare_table_id');"/>
            <c:set var="clickMethod" scope="request" value="btnClick('detailOp','clearStart','detail','','clearStartHead');"/>
            <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all" />
            <br/>
        </FORM>

        <!-- 状态栏 通用模板 -->

        <%--<c:import url="/jsp/common/common_template.jsp?template_name=common_status_table" />--%>
    </body>
</html>
