<%-- 
    Document   : gateApproEnterPara
    Created on : 2017-6-07, 20:48:42
    Author     : zhouyang
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>闸机专用通道参数</title>


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
                    setPrimaryKeys('detailOp', 'd_cardMainID#d_cardSubID');
                    setControlsDefaultValue('queryOp');
                    setListViewDefaultValue('detailOp', 'clearStart');
                    setQueryControlsDefaultValue('queryOp', 'detailOp');
                    setPageControl('detailOp');
                    setTableRowBackgroundBlock('DataTable')">
            </c:when>
            <c:otherwise>
            <body onload="initDocument('queryOp', 'detail');
                    initDocument('detailOp', 'detail');
                    setInvisable('detailOp', 'add;modify;del;submit1');
                    setPrimaryKeys('detailOp', 'd_cardMainID#d_cardSubID');
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
                <td colspan="4">闸机专用通道参数（
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

        <form method="post" name="queryOp" id="queryOp" action="GateApproEnterPara">
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

                    <td class="table_edit_tr_td_label" rowspan="2">

                        <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all_init" />
                        <c:set var="query" scope="request" value="1"/>
                        <c:set var="addClickMethod" scope="request" value="setControlNames('queryOp','q_cardMainID#q_cardSubID');setLineCardNames('queryOp','','','','q_cardMainID','q_cardSubID','commonVariable1');"/>
                        <c:set var="clickMethod" scope="request" value="btnClick('queryOp','clearStart','detail');"/>
                        <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all" />
                    </td>
                </tr>
            </table>
            <c:import url="/jsp/common/common_template.jsp?template_name=version_eval" />

        </form>
            
        <!-- 返回信息栏 通用模板 -->
        <c:import url="/jsp/common/common_template.jsp?template_name=common_status_table" />

        <!-- 表头 通用模板 -->
        <c:set var="pTitleName" scope="request" value="列表"/>
        <c:set var="pTitleWidth" scope="request" value="50"/>
        <c:import url="/jsp/common/common_template.jsp?template_name=common_table_title" />
        <div id="clearStartBlock" class="divForTableBlock">
            <DIV id="clearStartHead"  class="divForTableBlockHead">
                <table class="table_list_block" id="DataTableHead" >

                    <!--说明：列的序号从0开始 isDigit：false 表示按字符串排序 ：true表示按数值排序 sortedby：asc表示按升序 dec表示按降序 -->
                    <tr  class="table_list_tr_head_block" id="ignore">
                        <td  align="center" class="table_list_tr_col_head_block">
                            <input type="checkbox" name="rectNoAll" id="rectNoAll" onclick="selectAllRecord('detailOp', 'rectNoAll', 'rectNo', 'clearStart', 0);"/>
                        </td>
                        <td id="orderTd"    class="table_list_tr_col_head"  isDigit=false index="1"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 100px">票卡主类型</td>
                        <td id="orderTd"    class="table_list_tr_col_head"  isDigit=false index="2"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 100px">票卡子类型</td>
                        <td id="orderTd"    class="table_list_tr_col_head"  isDigit=false index="3"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 100px">是否优惠票</td>
                        <td id="orderTd"    class="table_list_tr_col_head"  isDigit=false index="4"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 130px">是否启用语音提示</td>
                        <td id="orderTd"    class="table_list_tr_col_head"  isDigit=false index="5"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 100px">版本标志</td>
                        <td id="orderTd"    class="table_list_tr_col_head"  isDigit=false index="6"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 100px">版本号</td>
                    </tr>
                </table>
            </div>
            
            <div id="clearStart"  class="divForTableBlockData">
                <table class="table_list_block" id="DataTable" >
                    <c:forEach items="${ResultSet}" var="rs">
                        <!--class="listTableData" -->
                        <tr class="table_list_tr_data"  onMouseOver="overResultRow('detailOp', this);" 
                            onMouseOut="outResultRow('detailOp', this);" 
                            onclick="setSelectValuesByRowPropertyName('detailOp','d_cardSubID','commonVariable1','idCard');
                                     clickResultRow('detailOp',this,'detail');setPageControl('detailOp');" 
                            id="${rs.card_main_id}#${rs.card_sub_id}#${rs.version_no}#${rs.record_flag}#${rs.discount}#${rs.sound}"
                            idCard="${rs.card_main_id}">

                            <td id="rectNo1" class="table_list_tr_col_data_block">
                                <input type="checkbox" name="rectNo" onclick="unSelectAllRecord('detailOp', 'rectNoAll', 'rectNo');"
                                       value="${rs.card_main_id}#${rs.card_sub_id}">

                                </input>
                            </td>
                            <td  id="cardMainID"  class="table_list_tr_col_data_block" style="width: 100px">
                                ${rs.card_main_id_name}
                            </td>
                            <td  id="cardSubID"  class="table_list_tr_col_data_block" style="width: 100px">
                                ${rs.card_sub_id_name}
                            </td>
                            <td  id="discount"  class="table_list_tr_col_data_block" style="width: 100px">
                                ${rs.discount_name}
                            </td>
                            <td  id="sound"  class="table_list_tr_col_data_block" style="width: 130px">
                                ${rs.sound_name}
                            </td>
                            <td  id="recordFlag"  class="table_list_tr_col_data_block" style="width: 100px">
                                ${rs.record_flag_name}
                            </td>
                            <td  id="versionNo"  class="table_list_tr_col_data_block" style="width: 100px">
                                ${rs.version_no}
                            </td>

                        </tr>

                    </c:forEach>

                </table>
            </DIV>
        </div>

        <!-- 表头 通用模板 -->
        <c:set var="pTitleName" scope="request" value="明细"/>
        <c:set var="pTitleWidth" scope="request" value="50"/>
        <c:import url="/jsp/common/common_template.jsp?template_name=common_table_title" />

        <FORM method="post" name="detailOp" id="detailOp" action="GateApproEnterPara" >

            <c:set var="divideShow" scope="request" value="1"/>
            <c:import  url="/jsp/common/common_template_web_variable.jsp?template_name=common_web_variable" />

            <!--<DIV id="detail" align="center" class="divForTableData" style="position:relative; left:20; width:95%; height:10; layer-background-color:#99CC99; border:thin outset #ffffff; z-index:1;">-->
            <DIV id="detail"  class="divForTableDataDetail" >
                <table  class="table_edit_detail">
                    <tr class="table_edit_tr">
                        <td class="table_edit_tr_td_label" style="width:90px">票卡主类型: </td>
                        <td class="table_edit_tr_td_input" style="width:90px">
                            <select  name="d_cardMainID"  id="d_cardMainID" onChange="setSelectValues('detailOp', 'd_cardMainID', 'd_cardSubID', 'commonVariable1');" require="true" dataType="LimitB" min="1" msg="没有选择票卡主类型!">
                                <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_cardMain" />
                            </select>	
                        </td>
                        <td class="table_edit_tr_td_label" style="width:90px">票卡子类型:</td>
                        <td class="table_edit_tr_td_input" style="width:90px">
                            <select id="d_cardSubID" name="d_cardSubID" require="true" dataType="LimitB" min="1" msg="没有选择票卡子类型!">
                                <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_cardSub_none" />

                            </select>
                            <c:set var="pVarName" scope="request" value="commonVariable1"/>
                            <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_cardSub" />
                        </td>
                        <td class="table_edit_tr_td_label" style="width:90px">是否优惠票: </td>
                        <td class="table_edit_tr_td_input" style="width:90px">
                            <select id="d_discount" name="d_discount" require="true" dataType="LimitB" min="1" msg="没有选择是否优惠票!">
                                <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_discount" />
                            </select>	
                        </td>
                        <td class="table_edit_tr_td_label" style="width:90px">是否启用语音提示: </td>
                        <td class="table_edit_tr_td_input" style="width:90px">
                            <select id="d_sound" name="d_sound" require="true" dataType="LimitB" min="1" msg="没有选择是否启用语音提示!">
                                <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_sound" />
                            </select>	
                        </td>
                    </tr>
                    
                </table>
            </DIV>

            <input type="hidden" name="expAllFields" id="d_expAllFields"
                   value="getCard_main_id_name,getCard_sub_id_name,getDiscount_name,getSound_name,getRecord_flag_name,getVersion_no" />
            <input type="hidden" name="expFileName" id="d_expFileName" value="闸机专用通道参数.xlsx" />
            <input type="hidden" name="divId" id="d_divId" value="clearStartHead">
            <input type="hidden" name="methodUrl" id="d_methodUrl" value="/GateApproEnterParaExportAll"/>
            <c:import url="/jsp/common/common_template.jsp?template_name=version_eval" />
            <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all_init" />
            <c:set var="add" scope="request" value="1"/>
            <c:set var="del" scope="request" value="1"/>
            <c:set var="modify" scope="request" value="1"/>
            <c:set var="save" scope="request" value="1"/>
            <c:set var="cancle" scope="request" value="1"/>
            <c:set var="clone" scope="request" value="1"/>
            <c:set var="submit" scope="request" value="1"/>
            <c:set var="print" scope="request" value="1"/>
            <c:set var="export" scope="request" value="1"/>
            <c:set var="expAll" scope="request" value="1" />
            <c:set var="btNext" scope="request" value="1"/>
            <c:set var="btNextEnd" scope="request" value="1"/>
            <c:set var="btBack" scope="request" value="1"/>
            <c:set var="btBackEnd" scope="request" value="1"/>
            <c:set var="btGoPage" scope="request" value="1"/>

            <c:set var="addClickMethod" scope="request" value="setUpdatePrimaryKey('detailOp','d_cardMainID#d_cardSubID#Version');"/>
            <c:set var="clickMethod" scope="request" value="btnClick('detailOp','clearStart','detail','','clearStartHead');"/>
            <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all" />
            <br/>
        </FORM>

        
    </body>
</html>
