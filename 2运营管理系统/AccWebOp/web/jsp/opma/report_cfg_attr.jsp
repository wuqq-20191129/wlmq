<%-- 
    Document   : report_gfg_attr
    Created on : 201711-06
    Author     : luck
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>报表属性配置</title>


        <script language="javascript" type="text/javascript" charset="utf-8" src="js/common_form.js"></script>
        <script language="javascript" type="text/javascript" charset="utf-8" src="js/Validator.js"></script>
        <script language="JavaScript" src="js/CalendarPop.js"></script>
        <link rel="stylesheet" type="text/css" href="css/frame/simple.css" title="Style"/>
    </head>

    <body onload="initDocument('queryOp', 'detail');
            initDocument('detailOp', 'detail');
            setControlsDefaultValue('queryOp');
            setPrimaryKeys('detailOp', 'd_reportName#d_reportModule');
            setListViewDefaultValue('detailOp', 'clearStart');
            setQueryControlsDefaultValue('queryOp', 'detailOp');
            setPageControl('detailOp');
            setTableRowBackgroundBlock('DataTable')">

        <!--<body >-->
        <table  class="table_title">
            <tr align="center" class="trTitle">
                <td colspan="4">报表属性配置    
                </td>
            </tr>
        </table>




        <!-- 表头 通用模板 -->

        <c:set var="pTitleName" scope="request" value="查询"/>
        <c:set var="pTitleWidth" scope="request" value="50"/>
        <c:import  url="/jsp/common/common_template.jsp?template_name=common_table_title" />

        <form method="post" name="queryOp" id="queryOp" action="ReportGfgAttr">
            <!-- 页面用到的变量 通用模板 -->

            <c:set var="divideShow" scope="request" value="1"/>
            <c:import  url="/jsp/common/common_template_web_variable.jsp?template_name=common_web_variable" />

            <table  class="table_edit">
                <tr class="table_edit_tr">

                    <td class="table_edit_tr_td_label">报表代码：</td>
                    <td class="table_edit_tr_td_input">
                        <select id="q_reportCode" name="q_reportCode" require="false" dataType="LimitB" min="1"   style="width:140px">				    
                            <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_reportCode" />
                        </select>
                    </td>
                    <td class="table_edit_tr_td_label">报表模板：</td>
                    <td class="table_edit_tr_td_input">
                        <select id="q_reportModule" name="q_reportModule" require="false" dataType="LimitB" min="1"  style="width:140px">				    
                            <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_reportModule" />
                        </select>
                    </td>
                    <td class="table_edit_tr_td_label">是否生成：</td>
                    <td class="table_edit_tr_td_input">
                        <select id ="q_reportLock" name="q_reportLock" >
                            <option value="">=请选择=</option>
                            <option value="0">是</option>
                            <option value="1">否</option>
                        </select>
                    </td>

                    <td class="table_edit_tr_td_label" rowspan="2">

                        <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all_init" />
                        <c:set var="query" scope="request" value="1"/>
                        <c:set var="addClickMethod" scope="request" value="setControlNames('queryOp','q_reportCode#q_reportModule#q_reportLock');"/>
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
        <div id="clearStartBlock" class="divForTableBlock" >
            <div id="clearStartHead" class="divForTableBlockHead" style="width: 133%">
                <table class="table_list_block" id="DataTableHead" >

                    <!--说明：列的序号从0开始 isDigit：false 表示按字符串排序 ：true表示按数值排序 sortedby：asc表示按升序 dec表示按降序 -->
                    <tr  class="table_list_tr_head_block" id="ignore">
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="0"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 80px" >报表代码</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="1"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 250px" >报表名称</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="2"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 80px">报表模板</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="3"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 100px" >报表类型</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="4"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 100px">时间类型</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="5"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 100px" >线路</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="6"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 100px">站点</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="7"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 100px" >票卡主类型</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="8"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 100px">票卡子类型</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="9"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 80px" >是否生成</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="10" sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 80px">输出类型</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="11" sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 120px">数据源</td>

                    </tr>

                </table>

            </div>
            <!-- <DIV id="clearStart" align="center" style="position:relative; left:20; width:95%; height:30%;min-height: 200px;max-height: 250px; layer-background-color:#99CC99; border:thin outset #ffffff; z-index:1; overflow: scroll;">-->
            <div id="clearStart"  class="divForTableBlockData" style="width: 133%">
                <table class="table_list_block" id="DataTable" >

                    <c:forEach items="${ResultSet}" var="rs">
                        <!--class="listTableData" -->
                        <tr class="table_list_tr_data"  onMouseOver="overResultRow('detailOp', this);" 
                            onMouseOut="outResultRow('detailOp', this);" 
                            onclick="clickResultRow('detailOp', this, 'detail');
                                    setPageControl('detailOp');" 
                            id="${rs.reportModule}#${rs.reportName}#${rs.outType}#${rs.cardMainId}#${rs.cardSubId}#${rs.lineId}">

                            <td  id="reportCode" class="table_list_tr_col_data_block" style="width: 80px">
                                ${rs.reportCode}
                            </td>
                            <td  id="reportName" class="table_list_tr_col_data_block" style="width: 250px">
                                ${rs.reportName}
                            </td>
                            <td  id="reportModule" class="table_list_tr_col_data_block" style="width: 80px">
                                ${rs.reportModule}
                            </td>
                            <td  id="periodType" class="table_list_tr_col_data_block" style="width: 100px">
                                ${rs.periodTypeName}
                            </td>
                            <td  id="reportType" class="table_list_tr_col_data_block" style="width: 100px">
                                ${rs.reportTypeName}
                            </td>
                        <font  id="lineId" class="table_list_tr_col_data_block" style="display: none">
                        ${rs.lineId}
                        </font>
                        <td  id="lineName" class="table_list_tr_col_data_block" style="width: 100px">
                            ${rs.lineName}
                        </td>
                        <td  id="stationId" class="table_list_tr_col_data_block" style="width: 100px">
                            ${rs.stationName}
                        </td>
                        <font  id="cardMainId" class="table_list_tr_col_data_block" style="display: none">
                        ${rs.cardMainId}
                        </font>
                        <td  id="cardMainName" class="table_list_tr_col_data_block" style="width: 100px">
                            ${rs.cardMainName}
                        </td>
                        <font  id="cardSubId" class="table_list_tr_col_data_block" style="display: none">
                        ${rs.cardSubId}
                        </font>
                        <td  id="cardSubName" class="table_list_tr_col_data_block" style="width: 100px">
                            ${rs.cardSubName}
                        </td>
                        <td  id="reportLock" class="table_list_tr_col_data_block" style="width: 80px">
                            ${rs.reportLockName}
                        </td>
                        <td  id="outType" class="table_list_tr_col_data_block" style="width: 80px">
                            ${rs.outType}
                        </td>
                        <td  id="dsId" class="table_list_tr_col_data_block" style="width: 120px">
                            ${rs.dsName}
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

        <FORM method="post" name="detailOp" id="detailOp" action="ReportGfgAttr" >

            <c:set var="divideShow" scope="request" value="1"/>
            <c:import  url="/jsp/common/common_template_web_variable.jsp?template_name=common_web_variable" />

            <!--导出全部参数 -->
            <input type="hidden" name="expAllFields" id="d_expAllFields" value="REPORT_CODE,REPORT_NAME,REPORT_MODULE,PERIODTYPENAME,REPORTTYPENAME,LINENAME,STATIONNAME,CARDMAINNAME,CARDSUBNAME,REPORTLOCKNAME,OUT_TYPE,DSNAME" />
            <input type="hidden" name="expFileName" id="d_expFileName" value="报表属性配置.xlsx" />
            <input type="hidden" name="divId" id="d_divId" value="clearStartHead" />
            <input type="hidden" name="methodUrl" id="d_methodUrl" value="/ReportGfgAttrExportAll" />

            <!--<DIV id="detail" align="center" class="divForTableData" style="position:relative; left:20; width:95%; height:10; layer-background-color:#99CC99; border:thin outset #ffffff; z-index:1;">-->
            <DIV id="detail"  class="divForTableDataDetail" >
                <table  class="table_edit_detail">


                    <tr class="table_edit_tr">
                        <td class="table_edit_tr_td_label">报表名称:</td>
                        <td class="table_edit_tr_td_input">
                            <input type="text" name="d_reportName" id="d_reportName" size="30"  require="true" dataType="NotEmpty" msg="报表名称不能为空"/>
                        </td>
                        <td class="table_edit_tr_td_label">报表模板:</td>
                        <td class="table_edit_tr_td_input">
                            <input type="text" name="d_reportModule" id="d_reportModule" size="10"  require="true" dataType="NotEmpty"  msg="报表模板不能为空" />
                        </td>               
                    <input type="hidden" name="d_lineId" id="d_lineId" size="10"  require="false"   msg="线路ID不能为空" />
                    <input type="hidden" name="d_cardMainId" id="d_cardMainId" size="10"  require="false"   msg="票卡主类型ID不能为空" />
                    <input type="hidden" name="d_cardSubId" id="d_cardSubId" size="10"  require="false"   msg="票卡子类型ID不能为空" />
                    <input type="hidden" name="d_outType" id="d_outType" size="10"  require="false"   msg="输出类型不能为空" />
                    <td class="table_edit_tr_td_label">是否生成:</td>
                    <td class="table_edit_tr_td_input">
                        <select id ="d_reportLock" name="d_reportLock" >
                            <option value="0">是</option>
                            <option value="1">否</option>
                        </select>
                    </td>         
                    </tr>



                </table>
            </DIV>

            <c:import url="/jsp/common/common_template.jsp?template_name=version_eval" />




            <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all_init" />
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


            <c:set var="addQueryMethod" scope="request" value="setLineCardNames('detailOp','d_cardMainId','d_cardMainName','commonVariable','','','');"/>
            <c:set var="addClickMethod" scope="request" value="setUpdatePrimaryKey('detailOp','d_cardMainId');"/>
            <c:set var="clickMethod" scope="request" value="btnClick('detailOp','clearStart','detail','','clearStartHead');"/>
            <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all" />
            <br/>
        </FORM>

      
    </body>
</html>
