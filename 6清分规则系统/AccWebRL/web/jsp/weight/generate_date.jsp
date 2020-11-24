<%-- 
    Document   : generate_date
    Created on : 2017-9-7, 10:07:31
    Author     : liudz
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="X-UA-Compatible" content="IE=9,Chrome=1" />
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>下载权重数据</title>

        <script language="javascript" type="text/javascript" charset="utf-8" src="js/common_form_extend.js"></script>
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
            setListViewDefaultValue('detailOp', 'clearStart');
            setQueryControlsDefaultValue('queryOp', 'detailOp');
            setPageControl('detailOp');
            setTableRowBackgroundBlock('DataTable')">

        <!--<body >-->
        <table  class="table_title">
            <tr align="center" class="trTitle">
                <td colspan="4">下载权重数据
                </td>
            </tr>
        </table>
        
        
        <!-- 生成数据 -->
        <c:set var="pTitleName" scope="request" value="生成数据"/>
        <c:set var="pTitleWidth" scope="request" value="50"/>
        <c:import  url="/jsp/common/common_template.jsp?template_name=common_table_title" />

        <form method="post" name="generateData" id="generateData" action="generateDataAction">
            <table  class="table_edit">
                <tr class="table_edit_tr">
                    <input type="button" id="generate" name="generate" value="生成权重数据" class="buttonStyle" onclick="generateFunction('queryOp');" />
                    <input type="button" id="generateOD" name="generateOD" value="生成OD数据" class="buttonStyle" onclick="generateODFunction('queryOp');" />
                </tr>
            </table>
        </form>
        <!-- 表头 通用模板 -->
        <c:set var="pTitleName" scope="request" value="查询"/>
        <c:set var="pTitleWidth" scope="request" value="50"/>
        <c:import  url="/jsp/common/common_template.jsp?template_name=common_table_title" />

        <form method="post" name="queryOp" id="queryOp" action="generateDataAction">
            <!-- 页面用到的变量 通用模板 -->

            <c:set var="divideShow" scope="request" value="1"/>
            <c:import  url="/jsp/common/common_template_web_variable.jsp?template_name=common_web_variable" />

            <table  class="table_edit">
                <tr class="table_edit_tr">
                    <td class="table_edit_tr_td_label" >开始线路:</td>
                    <td class="table_edit_tr_td_input">
                        <select id="q_oLineId" name="q_oLineId" onChange="setSelectValues('queryOp', 'q_oLineId', 'q_oStationId', 'commonVariable');"  >				    
                            <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_line" />
                        </select>
                    </td>
                    <td class="table_edit_tr_td_label" >开始站点:</td>
                    <td class="table_edit_tr_td_input">
                        <select id="q_oStationId" name="q_oStationId" >
                            <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_none" />
                        </select>
                        <c:set var="pVarName" scope="request" value="commonVariable"/>
                        <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_station" />
                    </td>
                    <td class="table_edit_tr_td_label" >结束线路:</td>
                    <td class="table_edit_tr_td_input">
                        <select id="q_dLineId" name="q_dLineId" onChange="setSelectValues('queryOp', 'q_dLineId', 'q_dStationId', 'commonVariable1');"  >				    
                            <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_line" />
                        </select>
                    </td>
                    <td class="table_edit_tr_td_label" >结束站点:</td>
                    <td class="table_edit_tr_td_input">
                        <select id="q_dStationId" name="q_dStationId" >
                            <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_none" />
                        </select>
                        <c:set var="pVarName" scope="request" value="commonVariable1"/>
                        <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_station" />
                    </td>
                    <td class="table_edit_tr_td_label">版本状态:</td>
                    <td class="table_edit_tr_td_input">
                        <select id="q_recordFlag" name="q_recordFlag" >				    
                            <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_recordFlags" />
                        </select>
                    </td>


                    <td class="table_edit_tr_td_label" rowspan="1">

                        <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all_init" />
                        <c:set var="query" scope="request" value="1"/>
                        <c:set var="addClickMethod" scope="request" value="setControlNames('queryOp','q_oLineId#q_oStationId#q_dLineId#q_dStationId#q_recordFlag');setLineCardNames('queryOp','q_oLineId','q_oStationId','commonVariable','q_dLineId','q_dStationId','commonVariable1');"/>
                        <c:set var="clickMethod" scope="request" value="btnClick('queryOp','clearStart','detail');"/>
                        <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all" />
                    </td>

                </tr>
                <tr class="table_edit_tr">
                    <td class="table_edit_tr_td_label" >导出字段:</td>
                    <td colspan="7">
                        <input type="hidden" id="c_checkbox" name="c_checkbox" value=""/>
                        <input type="checkbox" value="c_oline" name="c" checked="checked" disabled="disabled">开始线路</input>
                        <input type="checkbox" value="c_ostation" name="c" checked="checked" disabled="disabled">开始站点</input>
                        <input type="checkbox" value="c_dline" name="c" checked="checked" disabled="disabled">结束线路</input>
                        <input type="checkbox" value="c_dstation" name="c" checked="checked" disabled="disabled">结束站点</input>
                        <input type="checkbox" value="c_dispart_line" name="c" checked="checked" disabled="disabled">权重线路</input>
                        <input type="checkbox" value="c_proportion" name="c" checked="checked" disabled="disabled">权重比例</input>
                        <input type="checkbox" value="c_version" name="c">版本</input>
                    </td>
                    <td class="table_edit_tr_td_label" rowspan="1">
                        <input type="button" id="btFileDown" name="btFileDown" value="参数下载" class="buttonStyle" onclick="saveFile('queryOp');" />
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

                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="1"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 130px">开始线路</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="2"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 130px">开始站点</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="3"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 130px">结束线路</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="4"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 130px">结束站点</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=true index="5"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 130px">权重线路</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="6"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 130px">权重比例</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="7"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 130px">版本</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="8"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 130px">版本状态</td>
                    </tr>


                </table>

            </div>
            <!-- <DIV id="clearStart" align="center" style="position:relative; left:20; width:95%; height:30%;min-height: 200px;max-height: 250px; layer-background-color:#99CC99; border:thin outset #ffffff; z-index:1; overflow: scroll;">-->
            <div id="clearStart"  class="divForTableBlockData">
                <table class="table_list_block" id="DataTable" >

                    <c:forEach items="${ResultSet}" var="rs">
                        <!--class="listTableData" -->
                        <tr class="table_list_tr_data"  onMouseOver="overResultRow('detailOp', this);" 
                            onMouseOut="outResultRow('detailOp', this);" >

                            <td  id="oLineId" class="table_list_tr_col_data_block" style="width: 130px">
                                ${rs.oLineIdText}
                            </td>
                            <td  id="oStationId" class="table_list_tr_col_data_block" style="width: 130px">
                                ${rs.oStationIdText}
                            </td>
                            <td  id="dLineId" class="table_list_tr_col_data_block" style="width: 130px">
                                ${rs.dLineIdText}
                            </td>
                            <td  id="dStationId" class="table_list_tr_col_data_block" style="width: 130px">
                                ${rs.dStationIdText}
                            </td>
                            <td  id="dispartLineId" class="table_list_tr_col_data_block" style="width: 130px">
                                ${rs.dispartLineIdText}
                            </td>
                            <td  id="inPrecent" class="table_list_tr_col_data_block" style="width: 130px">
                                ${rs.inPrecent}
                            </td>
                            <td  id="version" class="table_list_tr_col_data_block" style="width: 130px">
                                ${rs.version}
                            </td>
                            <td  id="recordFlag" class="table_list_tr_col_data_block" style="width: 130px">
                                ${rs.recordFlagText}
                            </td>
                        </tr>

                    </c:forEach>

                </table>
            </div>
        </div>

        <!-- 表头 通用模板 -->

        <FORM method="post" name="detailOp" id="detailOp" action="generateDataAction" >
            
            <input type="hidden" name="expAllFields" id="d_expAllFields"
                   value="oLineIdText,oStationIdText,dLineIdText,dStationIdText,dispartLineIdText,inPrecent,version,recordFlagText" />
            <input type="hidden" name="expFileName" id="d_expFileName" value="下载权重.xlsx" />
            <input type="hidden" name="divId" id="d_divId" value="clearStartHead">
            <input type="hidden" name="methodUrl" id="d_methodUrl" value="/GenerateDataActionExportAll"/>
            
            <c:set var="divideShow" scope="request" value="1"/>
            <c:import  url="/jsp/common/common_template_web_variable.jsp?template_name=common_web_variable" />
            <div id="detail"  class="divForTableDataDetail" ></div>
            <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all_init" />

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
