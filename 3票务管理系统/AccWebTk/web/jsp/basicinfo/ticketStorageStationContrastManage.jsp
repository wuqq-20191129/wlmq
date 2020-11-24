<%-- 
    Document   : TicketStorageStationContrastManage
    Created on : 2017-8-03
    Author     : xiaowu
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>车站对照表</title>

        <script language="javascript" type="text/javascript" charset="utf-8" src="js/common_form.js"></script>
        <script language="javascript" type="text/javascript" charset="utf-8" src="js/common_form_extend.js"></script>
        <script language="javascript" type="text/javascript" charset="utf-8" src="js/Validator.js"></script>
        <script language="JavaScript" type="text/javascript" charset="utf-8" src="js/CalendarPop.js"></script>
        <script language="JavaScript" type="text/javascript" charset="utf-8" src="js/icProduceOut.js"></script>
        <script language="JavaScript" type="text/javascript" charset="utf-8" src="js/icStorageProductBill.js"></script>
        <link rel="stylesheet" type="text/css" href="css/frame/simple.css" title="Style"/>
    </head>

    <body onload="initDocument('queryOp', 'detail');
            initDocument('detailOp', 'detail');
            setControlsDefaultValue('queryOp');
            setPrimaryKeys('detailOp', 'd_line_id#d_station_id');
            setListViewDefaultValue('detailOp', 'clearStart');
            setQueryControlsDefaultValue('queryOp', 'detailOp');
            setPageControl('detailOp');
            setTableRowBackgroundBlock('DataTable')">
        <!--<body >-->
        <table  class="table_title">
            <tr align="center" class="trTitle">
                <td colspan="4">车站对照表</td>
            </tr>
        </table>

        <!-- 表头 通用模板 -->
        <c:set var="pTitleName" scope="request" value="查询"/>
        <c:set var="pTitleWidth" scope="request" value="50"/>
        <c:import  url="/jsp/common/common_template.jsp?template_name=common_table_title" />

        <form method="post" name="queryOp" id="queryOp" action="ticketStorageStationContrastManage" style="margin-bottom: 3px;">
            <!-- 页面用到的变量 通用模板 -->
            <c:set var="divideShow" scope="request" value="1"/>
            <c:import  url="/jsp/common/common_template_web_variable.jsp?template_name=common_web_variable" />
            <table  class="table_edit">
                <tr class="table_edit_tr">
                    <td class="table_edit_tr_td_label" >线路:</td>
                        <td class="table_edit_tr_td_input">
                            <select id="q_line_id" name="q_line_id" onChange="setSelectValues('queryOp', 'q_line_id', 'q_station_id', 'commonVariable');" >				    
                                <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_line" />
                            </select>
                        </td>
                        <td class="table_edit_tr_td_label"><div align="right">车站:</div></td>
                        <td class="table_edit_tr_td_input">
                            <select id="q_station_id" name="q_station_id">				    
                                <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_none" />
                        </select>
                        <c:set var="pVarName" scope="request" value="commonVariable"/>
                        <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_station" />
                        </td>

                    <td class="table_edit_tr_td_label" rowspan="1">
                        <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all_init" />
                        <c:set var="query" scope="request" value="1"/>
                        <c:set var="addClickMethod" scope="request" value="setControlNames('queryOp','q_line_id#q_station_id'); setLineCardNames('queryOp','','','','q_line_id','q_station_id','commonVariable');"/>
                        <c:set var="clickMethod" scope="request" value="btnClick('queryOp','clearStart','detail');"/>
                        <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all" />
                    </td>
                </tr>
            </table>

        </form>
        <!-- 状态栏 通用模板 -->
        <c:import url="/jsp/common/common_template.jsp?template_name=common_status_table" />
        
        <!-- 表头 通用模板 -->
        <c:set var="pTitleName" scope="request" value="列表"/>
        <c:set var="pTitleWidth" scope="request" value="50"/>
        <c:import url="/jsp/common/common_template.jsp?template_name=common_table_title" />
        <div id="clearStartBlock" class="divForTableBlock" >
            <div id="clearStartHead" class="divForTableBlockHead">
                <table class="table_list_block" id="DataTableHead" >

                    <!--说明：列的序号从0开始 isDigit：false 表示按字符串排序 ：true表示按数值排序 sortedby：asc表示按升序 dec表示按降序 -->
                    <tr  class="table_list_tr_head_block" id="ignore">

                        <td   class="table_list_tr_col_head_block">
                            <input type="checkbox" name="rectNoAll" id="rectNoAll" onclick="selectAllRecord('detailOp', 'rectNoAll', 'rectNo', 'clearStart', 0);setPageControl('detailOp');"/>
                        </td>	
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="1"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width:150px;" >线路</td>                        
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="2"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width:150px;" >车站</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="3"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width:150px;" >票务线路代码</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="4"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width:150px;" >票务车站代码</td>
                    </tr>
                </table>
            </div>

            <div id="clearStart"  class="divForTableBlockData">
                <table class="table_list_block" id="DataTable" >
                    <c:forEach items="${ResultSet}" var="rs">
                        <!--class="listTableData" -->
                        <!--
                        setSelectValuesByRow('detailOp', 'd_stationID', 'commonVariable');
                        setPageControl('detailOp');
                        -->
                        <tr class="table_list_tr_data"  onMouseOver="overResultRow('detailOp', this);" 
                            onMouseOut="outResultRow('detailOp', this);" 
                            onclick="setSelectValuesByRowPropertyName('detailOp', 'd_station_id', 'commonVariable','typeIDs');
                                setSelectValuesByRowPropertyName('detailOp', 'd_ic_station_id', 'commonVariable1','typeIDs1');
                                clickResultRow('detailOp', this, 'detail');
                                setPageControl('detailOp');" 
                            id="${rs.line_id}#${rs.station_id}" 
                            typeIDs="${rs.line_id}" typeIDs1="${rs.ic_line_id}" >

                            <td id="rectNo1" class="table_list_tr_col_data_block">
                                <input type="checkbox" name="rectNo" onclick="unSelectAllRecord('detailOp', 'rectNoAll', 'rectNo');"/>
                            </td>
                            <td  id="line_id" class="table_list_tr_col_data_block" style="width:150px;" >
                                ${rs.line_name} 
                            </td>
                            <td  id="station_id" class="table_list_tr_col_data_block" style="width:150px;">
                                ${rs.station_name}
                            </td>
                            <td  id="ic_line_id" class="table_list_tr_col_data_block" style="width:150px;" >
                                ${rs.ic_line_name}
                            </td>
                            <td  id="ic_station_id" class="table_list_tr_col_data_block" style="width:150px;" >
                                ${rs.ic_station_name} 
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

        <FORM method="post" name="detailOp" id="detailOp" action="ticketStorageStationContrastManage" >

            <c:set var="divideShow" scope="request" value="1"/>
            <c:import  url="/jsp/common/common_template_web_variable.jsp?template_name=common_web_variable" />
            <input type="hidden" name="expAllFields" id="d_expAllFields"
                   value="getLine_name,getStation_name,getIc_line_name,getIc_station_name" />
            <input type="hidden" name="expFileName" id="d_expFileName" value="票务车站.xlsx" />
            <input type="hidden" name="divId" id="d_divId" value="clearStartHead">
            <input type="hidden" name="methodUrl" id="d_methodUrl" value="/TicketStorageStationContrastManageExportAll"/>

            <div id="detail"  class="divForTableDataDetail" >
                <table  class="table_edit_detail">
                    <tr class="table_edit_tr">
                        <td class="table_edit_tr_td_label" >线路:</td>
                        <td class="table_edit_tr_td_input">
                            <select id="d_line_id" name="d_line_id" dataType="Require" msg="线路不能为空!" onChange="setSelectValues('detailOp', 'd_line_id', 'd_station_id', 'commonVariable');" >				    
                                <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_line" />
                            </select>
                        </td>
                        <td class="table_edit_tr_td_label">车站:</td>
                        <td class="table_edit_tr_td_input">
                            <select id="d_station_id" name="d_station_id" dataType="Require" msg="车站不能为空!">			    
                                <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_none" />
                            </select>
                        <c:set var="pVarName" scope="request" value="commonVariable"/>
                        <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_station" />
                        </td>
                    </tr>
                    <tr class="table_edit_tr">
                        <td class="table_edit_tr_td_label" >票务线路代码:</td>
                        <td class="table_edit_tr_td_input">
                            <select id="d_ic_line_id" name="d_ic_line_id" dataType="Require" msg="票务线路代码不能为空!" onChange="setSelectValues('detailOp', 'd_ic_line_id', 'd_ic_station_id', 'commonVariable1');" >				    
                                <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_icline" />
                            </select>
                        </td>
                        <td class="table_edit_tr_td_label">票务车站代码:</td>
                        <td class="table_edit_tr_td_input">
                            <select id="d_ic_station_id" name="d_ic_station_id" dataType="Require" msg="票务车站代码不能为空!">				    
                                <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_none" />
                            </select>
                            <c:set var="pVarName" scope="request" value="commonVariable1"/>
                            <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_iclinestation" />
                        </td>
                    </tr>
                </table>
            </div>

            <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all_init" />
            <c:set var="add" scope="request" value="1"/>
            <c:set var="del" scope="request" value="1"/>
            <c:set var="modify" scope="request" value="1"/>
            <c:set var="save" scope="request" value="1"/>
            <c:set var="cancle" scope="request" value="1"/>
            <c:set var="print" scope="request" value="1"/>
            <c:set var="export" scope="request" value="1"/>
            <c:set var="expAll" scope="request" value="1"/>
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
