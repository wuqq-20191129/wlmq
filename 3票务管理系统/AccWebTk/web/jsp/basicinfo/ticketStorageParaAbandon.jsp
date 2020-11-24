<%-- 
    Document   : ticketStorageParaAbandon
    Created on : 2017-8-10, 14:43:34
    Author     : liudezeng
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>弃票回收箱参数</title>


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
            setPrimaryKeys('detailOp', 'd_water_no');
            setListViewDefaultValue('detailOp', 'clearStart');
            setQueryControlsDefaultValue('queryOp', 'detailOp');
            setPageControl('detailOp');
            setTableRowBackgroundBlock('DataTable')">

        <table  class="table_title">
            <tr align="center" class="trTitle">
                <td colspan="4">弃票回收箱参数
                </td>
            </tr>
        </table>

        <!-- 表头 通用模板 -->

        <c:set var="pTitleName" scope="request" value="查询"/>
        <c:set var="pTitleWidth" scope="request" value="50"/>
        <c:import  url="/jsp/common/common_template.jsp?template_name=common_table_title" />

        <form method="post" name="queryOp" id="queryOp" action="ticketStorageParaAbandon">
            <!-- 页面用到的变量 通用模板 -->

            <c:set var="divideShow" scope="request" value="1"/>
            <c:import  url="/jsp/common/common_template_web_variable.jsp?template_name=common_web_variable" />

            <table  class="table_edit">

                <tr class="table_edit_tr">

                    <td class="table_edit_tr_td_label" width="15%">起始时间:</td>
                    <td class="table_edit_tr_td_input">
                        <input type="text" name="q_report_date_start" id="q_report_date_start" size="10" require="false"  
                               dataType="Date" format="ymd"  msg="开始时间格式不正确!"/>
                        <a href="javascript:openCalenderDialog(document.all.q_report_date_start);">
                            <img src="./images/calendar.gif" width="15" height="15" border="0" style="block" />
                        </a>
                    </td>
                    <td class="table_edit_tr_td_label" width="15%">结束时间:</td>
                    <td class="table_edit_tr_td_input" >
                        <input type="text" name="q_report_date_end" id="q_report_date_end" size="10" require="false"  
                               dataType="Date|ThanDate" format="ymd" to="q_report_date_start" msg="结束时间格式必须正确且大于开始时间!"/>
                        <a href="javascript:openCalenderDialog(document.all.q_report_date_end);">
                            <img src="./images/calendar.gif" width="15" height="15" border="0" style="block" />
                        </a>
                    </td>

                   <td class="table_edit_tr_td_label" >线路:</td>
                    <td class="table_edit_tr_td_input">
                        <select id="q_line_id" name="q_line_id" onChange="setSelectValues('queryOp', 'q_line_id', 'q_station_id', 'commonVariable');"  >				    
                            <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_icline" />
                        </select>
                    </td>
                    <td class="table_edit_tr_td_label"><div align="right">车站:</div></td>
                    <td class="table_edit_tr_td_input">
                        <select id="q_station_id" name="q_station_id" >
                            <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_none" />
                        </select>
                        <c:set var="pVarName" scope="request" value="commonVariable"/>
                        <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_iclinestation" />
                    </td>


                    <td class="table_edit_tr_td_label" rowspan="1">

                        <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all_init" />
                        <c:set var="query" scope="request" value="1"/>
                        <c:set var="addClickMethod" scope="request" 
                               value="setControlNames('queryOp','q_report_date_start#q_report_date_end#q_line_id#q_station_id');
                               setLineCardNames('queryOp','','','','q_line_id','q_station_id','commonVariable');"/>
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
        <div id="clearStartBlock" class="divForTableBlock">
            <div id="clearStartHead" class="divForTableBlockHead">
                <table class="table_list_block" id="DataTableHead" >

                    <!--说明：列的序号从0开始 isDigit：false 表示按字符串排序 ：true表示按数值排序 sortedby：asc表示按升序 dec表示按降序 -->
                    <tr  class="table_list_tr_head_block" id="ignore">
                        <td   class="table_list_tr_col_head_block">
                            <input type="checkbox" name="rectNoAll" id="rectNoAll" onclick="selectAllRecord('detailOp', 'rectNoAll', 'rectNo', 'clearStart', 0);setPageControl('detailOp');"/>
                        </td>	
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="1"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 80px">序号</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="2"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 200px">时间</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="3"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 120px">线路</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="4"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 120px">车站</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="5" sortedby="asc"  onclick="sortForTableBlock('clearStart');" style="width: 200px">备注</td>


                    </tr>


                </table>

            </div>
            <!-- <DIV id="clearStart" align="center" style="position:relative; left:20; width:95%; height:30%;min-height: 200px;max-height: 250px; layer-background-color:#99CC99; border:thin outset #ffffff; z-index:1; overflow: scroll;">-->
            <div id="clearStart"  class="divForTableBlockData">
                <table class="table_list_block" id="DataTable" >

                    <c:forEach items="${ResultSet}" var="rs">
                        <!--class="listTableData" -->
                        <!--
                        setSelectValuesByRow('detailOp', 'd_stationID', 'commonVariable');
                        -->
                        <tr class="table_list_tr_data"  onMouseOver="overResultRow('detailOp', this);" 
                            onMouseOut="outResultRow('detailOp', this);" 
                            onclick=" setSelectValuesByRowPropertyName('detailOp', 'd_station_id', 'commonVariable', 'icMainType');
                                    clickResultRow('detailOp', this, 'detail');
                                    setPageControl('detailOp');" 
                            id="${rs.water_no}#${rs.report_date}#${rs.line_id}#${rs.station_id}#${rs.remark}"
                            icMainType="${rs.line_id}">

                            <td id="rectNo1" class="table_list_tr_col_data_block">
                                <input type="checkbox" name="rectNo" id="rectNo" onclick="unSelectAllRecord('detailOp', 'rectNoAll', 'rectNo');"
                                       value="${rs.water_no}"  >

                                </input>
                            </td>

                            <td  id="water_no" class="table_list_tr_col_data_block" style="width: 80px">
                                ${rs.water_no}
                            </td>
                            <td  id="report_date" class="table_list_tr_col_data_block" style="width: 200px">
                                ${rs.report_date}
                            </td>
                            <td  id="line_id" class="table_list_tr_col_data_block" style="width: 120px">
                                ${rs.line_id_name}
                            </td>
                            <td  id="station_id" class="table_list_tr_col_data_block" style="width: 120px">
                                ${rs.station_id_name}
                            </td>
                            <td  id="remark" class="table_list_tr_col_data_block" style="width: 200px">
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

        <FORM method="post" name="detailOp" id="detailOp" action="ticketStorageParaAbandon" >
            
            <input type="hidden" name="expAllFields" id="d_expAllFields"
                   value="water_no,report_date,line_id_name,station_id_name,remark" />
            <input type="hidden" name="expFileName" id="d_expFileName" value="弃票回收箱参数.xlsx" />
            <input type="hidden" name="divId" id="d_divId" value="clearStartHead">
            <input type="hidden" name="methodUrl" id="d_methodUrl" value="/TicketStorageParaAbandonExportAll"/>

            <c:set var="divideShow" scope="request" value="1"/>
            <c:import  url="/jsp/common/common_template_web_variable.jsp?template_name=common_web_variable" />

            <!--<DIV id="detail" align="center" class="divForTableData" style="position:relative; left:20; width:95%; height:10; layer-background-color:#99CC99; border:thin outset #ffffff; z-index:1;">-->
            <div id="detail"  class="divForTableDataDetail" >
                <table  class="table_edit_detail">
                    <tr class="table_edit_tr">
                        <td class="table_edit_tr_td_label">序号:</td>
                        <td class="table_edit_tr_td_input">
                            <input type="text" name="d_water_no" id="d_water_no" size="10"  readonly="true"/>
                        </td>
                        <td class="table_edit_tr_td_label">时间:</td>
                        <td class="table_edit_tr_td_input">
                            <input type="text" name="d_report_date" id="d_report_date" size="10" require="true" datatype="Date" format="ymd" msg="时间格式为(yyyy-mm-dd)!"/>
                            <a href="javascript:openCalenderDialogByID('d_report_date','false');">
                                <img src="./images/calendar.gif" width="15" height="15" border="0" style="block"/>
                            </a>
                        </td>
                        <td class="table_edit_tr_td_label">线路: </td>
                        <td class="table_edit_tr_td_input">
                            <select id="d_line_id" name="d_line_id" onChange="setSelectValues('detailOp', 'd_line_id', 'd_station_id', 'commonVariable');"require="true" dataType="NotEmpty" msg="线路不能为空!">
                                <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_icline" />
                            </select>	
                        </td>
                        <td class="table_edit_tr_td_label">车站: </td>
                        <td class="table_edit_tr_td_input">
                            <select id="d_station_id" name="d_station_id" require="true" dataType="LimitB" min="1" msg="车站代码不能为空">
                                <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_none" />

                            </select>
                            <c:set var="pVarName" scope="request" value="commonVariable"/>
                            <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_iclinestation" />
                        </td>
                        
                    </tr>
                    
                    <tr class="table_edit_tr">
                        
                    
                        <td class="table_edit_tr_td_label">备注:</td>
                        <td class="table_edit_tr_td_input">
                             <input type="text" name="d_remark" id="d_remark" size="30" require="false" maxlength="256" dataType="LimitContainChinese" min="1" max="256" msg="备注最大录入长度为256个字节，一个中文字是3个字节"/>
                        </td>

                    </tr>

                </table>
            </div>






            <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all_init" />
            <c:set var="add" scope="request" value="1"/>
            <c:set var="modify" scope="request" value="1"/>
            <c:set var="del" scope="request" value="1"/>
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


            <c:set var="clickMethod" scope="request" value="btnClick('detailOp','clearStart','detail','','clearStartHead');disableFormControls('detailOp',['d_water_no'],true);"/>
            <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all" />
            <br/>
        </FORM>
        <!--
        <c:set var="addQueryMethod" scope="request" value="setLineCardNames('detailOp','d_line_id','d_station_id','commonVariable','','','');"/>
        <c:set var="addClickMethod" scope="request" value="setUpdatePrimaryKey('detailOp','d_water_no');"/>
        -->

        
    </body>
</html>
