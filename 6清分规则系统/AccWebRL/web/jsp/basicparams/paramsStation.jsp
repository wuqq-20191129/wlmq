<%-- 
    Document   : paramsStation
    Created on : 2017-9-6, 11:11:07
    Author     : ysw
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="X-UA-Compatible" content="IE=9,Chrome=1" />
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>站点参数设置</title>


        <script language="javascript" type="text/javascript" charset="utf-8" src="js/common_form.js"></script>
        <script language="javascript" type="text/javascript" charset="utf-8" src="js/common_form_extend.js"></script>
        <script language="javascript" type="text/javascript" charset="utf-8" src="js/Validator.js"></script>
        <script language="JavaScript" type="text/javascript" charset="utf-8" src="js/CalendarPop.js"></script>
        <script language="JavaScript" type="text/javascript" charset="utf-8" src="js/icProduceOut.js"></script>
        <script language="JavaScript" type="text/javascript" charset="utf-8" src="js/icStorageProductBill.js"></script>
        <link rel="stylesheet" type="text/css" href="css/frame/simple.css" title="Style"/>
    </head>
    <!-- 
      setInvisable('detailOp', 'clone');
     setPrimaryKeys('detailOp', 'd_lineID#d_stationID#d_deviceID#d_deviceType');
    -->
    <!--
    initDocument('formQuery','detail');
    initDocument('formOp','detail');
    setControlsDefaultValue('formQuery');
    setListViewDefaultValue('formOp','clearStart');
    setQueryControlsDefaultValue('formQuery','formOp');
    setPrimaryKeys('formOp','d_billNo');
    -->
    <body onload="initDocument('queryOp', 'detail');
            initDocument('detailOp', 'detail');
            setPrimaryKeys('detailOp', 'd_lineId#d_pStation#d_nStation');
            setControlsDefaultValue('queryOp');
            setListViewDefaultValue('detailOp', 'clearStart');
            setQueryControlsDefaultValue('queryOp', 'detailOp');

            setPageControl('detailOp');
            setTableRowBackgroundBlock('DataTable')">

        <!--<body >-->
        <table  class="table_title">
            <tr align="center" class="trTitle">
                <td colspan="4">站点参数设置
                </td>
            </tr>
        </table>

        <!-- 表头 通用模板 -->

        <c:set var="pTitleName" scope="request" value="查询"/>
        <c:set var="pTitleWidth" scope="request" value="50"/>
        <c:import  url="/jsp/common/common_template.jsp?template_name=common_table_title" />

        <form method="post" name="queryOp" id="queryOp" action="paramsStation">
            <!-- 页面用到的变量 通用模板 -->

            <c:set var="divideShow" scope="request" value="1"/>
            <c:import  url="/jsp/common/common_template_web_variable.jsp?template_name=common_web_variable" />

            <table  class="table_edit">
                <tr class="table_edit_tr">                                      
                    <td class="table_edit_tr_td_label">线路:</td>
                    <td class="table_edit_tr_td_input">
                        <select id="q_lineId" name="q_lineId" onChange="setSelectValuesNoSelDefault('queryOp', 'q_lineId', 'q_pStation', 'commonVariable');">
                                <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_line" />
                        </select>                    
                    </td>
                    <td class="table_edit_tr_td_label">当前站点:</td>
                    <td class="table_edit_tr_td_input">
                        <select id="q_pStation" name="q_pStation" >
                                <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_none" />
                        </select>
                        <c:set var="pVarName" scope="request" value="commonVariable"/>
                        <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_station" />
                    </td>
                    <td class="table_edit_tr_td_label">版本状态:</td>
                    <td class="table_edit_tr_td_input">
                        <select id="q_recordFlag" name="q_recordFlag" >
                                <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_recordFlags" />
                        </select>                     
                    </td>
                    <td class="table_edit_tr_td_label">创建时间:</td>
                    <td class="table_edit_tr_td_input" style="width:10px">
                        <input type="text" name="q_beginTime" id="q_beginTime" require="false" dataType="Date" value="" size="12"   format="ymd"  
                               msg="创建时间格式为(YYYY-MM-dd)!" />
                        <a href="javascript:openCalenderDialogByID('q_beginTime','false');">
                            <img src="./images/calendar.gif" width="15" height="15" border="0" style="block"/>
                        </a>                        
                        --
                        <input type="text" name="q_endTime" id="q_endTime" require="false" dataType="Date|ThanDate|dateDiff" value="" size="12" to="q_beginTime"
                            msg="创建时间格式为(YYYY-MM-dd)且大于等于开始时间,查询范围90天！" />
                        <a href="javascript:openCalenderDialogByID('q_endTime','false');">
                            <img src="./images/calendar.gif" width="15" height="15" border="0" style="block"/>
                        </a>
                    </td> 

                    <td class="table_edit_tr_td_label" rowspan="2">
                        <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all_init" />
                        <c:set var="query" scope="request" value="1"/>
                        <c:set var="addClickMethod" scope="request" value="setControlNames('queryOp','q_lineId#q_pStation#q_recordFlag#q_beginTime#q_endTime');setLineCardNames('queryOp','q_lineId','q_pStation','commonVariable')"/>
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
            <div id="clearStartHead" class="divForTableBlockHead" >
                <table class="table_list_block" id="DataTableHead" >
                    <!--说明：列的序号从0开始 isDigit：false 表示按字符串排序 ：true表示按数值排序 sortedby：asc表示按升序 dec表示按降序 -->
                    <tr  class="table_list_tr_head_block" id="ignore">
                        <td   class="table_list_tr_col_head_block">
                            <input type="checkbox" name="rectNoAll" id="rectNoAll" onclick="selectAllRecord('detailOp', 'rectNoAll', 'rectNo', 'clearStart', 0);"/>
                        </td>	
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="0"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style ="width:80px">线路</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="1"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style ="width:80px">当前站点</td> 
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="2"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style ="width:100px">下一站点</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="3"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style ="width:100px">下一换乘站</td> 
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="4"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style ="width:100px">里程数（米）</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="5"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style ="width:120px">版本状态</td> 
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="6"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style ="width:120px">版本</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="7"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style ="width:120px">创建时间</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="8"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style ="width:120px">创建人</td>
                    </tr>
                </table>

            </div>
            <!-- <DIV id="clearStart" align="center" style="position:relative; left:20; width:95%; height:30%;min-height: 200px;max-height: 250px; layer-background-color:#99CC99; border:thin outset #ffffff; z-index:1; overflow: scroll;">-->
            <div id="clearStart"  class="divForTableBlockData">
                <table class="table_list_block" id="DataTable" >
                    <c:forEach items="${ResultSet}" var="rs">                 
                        <tr class="table_list_tr_data"  onMouseOver="overResultRow('detailOp', this);" 
                            onMouseOut="outResultRow('detailOp', this);" 
                            onclick="
                                setSelectValuesByRowPropertyName('detailOp','d_pStation','commonVariable1','stationP'); 
                                setSelectValuesByRowPropertyName('detailOp','d_nStation','commonVariable1','stationN'); 
                                setSelectValuesByRowPropertyName('detailOp','d_ntStation','commonVariable1','stationS');
                                clickResultRow('detailOp', this, 'detail');
                                controlsByFlagWithoutCk('detailOp', ['modify']);
                                controlsByFlag('detailOp', ['del', 'audit']); "
                                stationP="${rs.lineId}#${rs.pStation}" stationN="${rs.lineId}#${rs.nStation}" stationS="${rs.lineId}#${rs.ntStation}"
                            id="${rs.lineId}#${rs.pStation}#${rs.nStation}#${rs.version}" flag="${rs.recordFlag}">

                            <td id="rectNo1" class="table_list_tr_col_data_block">
                                <input type="checkbox" name="rectNo" id="rectNo" onclick="unSelectAllRecord('detailOp', 'rectNoAll', 'rectNo');"
                                       value="${rs.lineId}#${rs.pStation}#${rs.nStation}#${rs.version}" flag="${rs.recordFlag}">
                                </input>
                            </td>                       
                            <td  id="lineId" class="table_list_tr_col_data_block" style ="width:80px">
                                ${rs.lineName}
                            </td>
                            <td  id="pStation" class="table_list_tr_col_data_block" style ="width:80px">
                                ${rs.pStationsName}
                            </td>
                            <td  id="nStation" class="table_list_tr_col_data_block" style ="width:100px">
                                ${rs.nStationsName}
                            </td> 
                            <td  id="ntStation" class="table_list_tr_col_data_block" style ="width:100px">
                                ${rs.ntStationsName}
                            </td>
                            <td  id="mileage" class="table_list_tr_col_data_block" style ="width:100px">
                                ${rs.mileage}
                            </td>
                            <td  id="recordFlag" class="table_list_tr_col_data_block" style ="width:120px">
                                ${rs.recordFlagName}
                            </td>
                            <td  id="version" class="table_list_tr_col_data_block" style ="width:120px">
                                ${rs.version}
                            </td> 
                            <td  id="createTime" class="table_list_tr_col_data_block" style ="width:120px">
                                ${rs.createTime}
                            </td>
                            <td  id="createOperator" class="table_list_tr_col_data_block" style ="width:120px">
                                ${rs.createOperator}
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

        <FORM method="post" name="detailOp" id="detailOp" action="paramsStation" >
            
            <input type="hidden" name="expAllFields" id="d_expAllFields"
                   value="lineName,pStationsName,nStationsName,ntStationsName,mileage,recordFlagName,version,createTime,createOperator" />
            <input type="hidden" name="expFileName" id="d_expFileName" value="站点参数设置.xlsx" />
            <input type="hidden" name="divId" id="d_divId" value="clearStartHead">
            <input type="hidden" name="methodUrl" id="d_methodUrl" value="/ParamsStationExportAll"/>

            <c:set var="divideShow" scope="request" value="1"/>
            <c:import  url="/jsp/common/common_template_web_variable.jsp?template_name=common_web_variable" />

            <!--<DIV id="detail" align="center" class="divForTableData" style="position:relative; left:20; width:95%; height:10; layer-background-color:#99CC99; border:thin outset #ffffff; z-index:1;">-->
            <div id="detail"  class="divForTableDataDetail" >
                <table  class="table_edit_detail">
                    <tr class="table_edit_tr">
                        <td class="table_edit_tr_td_label">线路:</td>
                        <td class="table_edit_tr_td_input">
                            <select id="d_lineId" name="d_lineId"   msg="线路不能为空！"
                                    onChange="setSelectValues('detailOp', 'd_lineId', 'd_pStation', 'commonVariable1');
                                    setSelectValues('detailOp', 'd_lineId', 'd_nStation', 'commonVariable1');
                                    setSelectValues('detailOp', 'd_lineId', 'd_ntStation', 'commonVariable1');">
                                <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_line" />
                            </select>	
                        </td>
                        <td class="table_edit_tr_td_label">当前站点:</td>
                        <td class="table_edit_tr_td_input">
                           <select id="d_pStation" name="d_pStation" dataType="Require" msg="当前站点不能为空！">
                                <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_none" />
                            </select>
                            <c:set var="pVarName" scope="request" value="commonVariable1"/>
                            <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_station" />
                        </td>
                        <td class="table_edit_tr_td_label">下一站点:</td>
                        <td class="table_edit_tr_td_input">
                            <select id="d_nStation" name="d_nStation" dataType="Require" msg="下一站点不能为空！">
                                <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_none" />
                            </select>
                            <c:set var="pVarName" scope="request" value="commonVariable"/>
                            <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_station" />
                        </td>                       
                        <td class="table_edit_tr_td_label">下一换乘站:</td>
                        <td class="table_edit_tr_td_input">                         
                            <select id="d_ntStation" name="d_ntStation" dataType="Require" msg="下一站换乘不能为空！">
                                <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_none" />
                            </select>
                            <c:set var="pVarName" scope="request" value="commonVariable"/>
                            <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_station" />                  
                        </td>
                        <td class="table_edit_tr_td_label">里程数(米):</td>
                        <td class="table_edit_tr_td_input">                         
                            <input type="text" name="d_mileage" id="d_mileage" size="20" maxlength="13" require="true" dataType="integer|Positive|MaxMileage|LimitB" min="0" max="8" msg="里程数应为小于100000(米),长度小于等于7位的正数"/>                   
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
            <c:set var="audit" scope="request" value="1"/>
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
        <!--
        <c:set var="addQueryMethod" scope="request" value=""/>
        <c:set var="addClickMethod" scope="request" value=""/>
        -->

        
    </body>
</html>
