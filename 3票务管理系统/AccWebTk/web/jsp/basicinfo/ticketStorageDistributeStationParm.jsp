<%-- 
    Document   : TicketStorageDistributeStationParm
    Created on : 2017-7-29, 11:48:59
    Author     : Liudezeng
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>配票车站参数</title>


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
            setPrimaryKeys('detailOp', 'd_line_id#d_station_id#d_storage_id');
            setListViewDefaultValue('detailOp', 'clearStart');
            setQueryControlsDefaultValue('queryOp', 'detailOp');
            setPageControl('detailOp');
            setTableRowBackgroundBlock('DataTable')">

        <table  class="table_title">
            <tr align="center" class="trTitle">
                <td colspan="4">配票车站参数
                </td>
            </tr>
        </table>

        <!-- 表头 通用模板 -->

        <c:set var="pTitleName" scope="request" value="查询"/>
        <c:set var="pTitleWidth" scope="request" value="50"/>
        <c:import  url="/jsp/common/common_template.jsp?template_name=common_table_title" />

        <form method="post" name="queryOp" id="queryOp" action="ticketStorageDistributeStationParmManage">
            <!-- 页面用到的变量 通用模板 -->

            <c:set var="divideShow" scope="request" value="1"/>
            <c:import  url="/jsp/common/common_template_web_variable.jsp?template_name=common_web_variable" />

            <table  class="table_edit">

                <tr class="table_edit_tr">



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
                    <td class="table_edit_tr_td_label" >仓库:</td>
                    <td class="table_edit_tr_td_input">
                        <select id="q_storage_id" name="q_storage_id" >				    
                            <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_storage" />
                        </select>
                    </td>


                    <td class="table_edit_tr_td_label" rowspan="1">

                        <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all_init" />
                        <c:set var="query" scope="request" value="1"/>
                        <c:set var="addClickMethod" scope="request" value="setControlNames('queryOp','q_line_id#q_station_id#q_storage_id');setLineCardNames('queryOp','','','','q_line_id','q_station_id','commonVariable');"/>
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
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="1"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 80px">线路</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="2"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 120px">车站</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="3"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 100px">仓库</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="4"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 150px">TVM存备数</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="5"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 150px">TVM数量</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="6"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 150px">ITM存备数</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="7"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 150px">ITM数量</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="8"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 150px">BOM存备数</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="9"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 150px">BOM数量</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="10" sortedby="asc"  onclick="sortForTableBlock('clearStart');"style="width: 200px">备注</td>

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
                            onclick=" setSelectValuesByRowPropertyName('detailOp', 'd_station_id', 'commonVariable', 'idStartLine');
                                    clickResultRow('detailOp', this, 'detail');
                                    setPageControl('detailOp');" 
                            id="${rs.line_id}#${rs.station_id}#${rs.storage_id}#${rs.tvm_store_num}#${rs.tvm_num}#${rs.itm_store_num}#${rs.itm_num}#${rs.bom_store_num}#${rs.bom_num}#${rs.remark}"
                            idStartLine="${rs.line_id}">

                            <td id="rectNo1" class="table_list_tr_col_data_block">
                                <input type="checkbox" name="rectNo" id="rectNo" onclick="unSelectAllRecord('detailOp', 'rectNoAll', 'rectNo');"
                                       value="${rs.line_id}#${rs.station_id}#${rs.storage_id}"  >

                                </input>
                            </td>

                            <td  id="line_id" class="table_list_tr_col_data_block" style="width: 80px">
                                ${rs.line_id_name}
                            </td>
                            <td  id="station_id" class="table_list_tr_col_data_block" style="width: 120px">
                                ${rs.station_id_name}
                            </td>
                            <td  id="storage_id" class="table_list_tr_col_data_block" style="width: 120px">
                                ${rs.storage_id_name}
                            </td>
                            <td  id="tvm_store_num" class="table_list_tr_col_data_block" style="width: 150px">
                                ${rs.tvm_store_num}
                            </td>
                            <td  id="tvm_num" class="table_list_tr_col_data_block" style="width: 150px">
                                ${rs.tvm_num}
                            </td>
                            <td  id="itm_store_num" class="table_list_tr_col_data_block" style="width: 150px">
                                ${rs.itm_store_num}
                            </td>
                            <td  id="itm_num" class="table_list_tr_col_data_block" style="width: 150px">
                                ${rs.itm_num}
                            </td>
                            <td  id="bom_store_num" class="table_list_tr_col_data_block" style="width: 150px">
                                ${rs.bom_store_num}
                            </td>
                            <td  id="bom_num" class="table_list_tr_col_data_block" style="width: 150px">
                                ${rs.bom_num}
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

        <FORM method="post" name="detailOp" id="detailOp" action="ticketStorageDistributeStationParmManage" >

            <input type="hidden" name="expAllFields" id="d_expAllFields"
                   value="line_id_name,station_id_name,storage_id_name,tvm_store_num,tvm_num,itm_store_num,itm_num,bom_store_num,bom_num,remark" />
            <input type="hidden" name="expFileName" id="d_expFileName" value="配票车站参数.xlsx" />
            <input type="hidden" name="divId" id="d_divId" value="clearStartHead">
            <input type="hidden" name="methodUrl" id="d_methodUrl" value="/TicketStorageDistributeStationParmManageExportAll"/>

            <c:set var="divideShow" scope="request" value="1"/>
            <c:import  url="/jsp/common/common_template_web_variable.jsp?template_name=common_web_variable" />

            <!--<DIV id="detail" align="center" class="divForTableData" style="position:relative; left:20; width:95%; height:10; layer-background-color:#99CC99; border:thin outset #ffffff; z-index:1;">-->
            <div id="detail"  class="divForTableDataDetail" >
                <table  class="table_edit_detail">
                    <tr class="table_edit_tr">
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
                        <td class="table_edit_tr_td_label">仓库: </td>
                        <td class="table_edit_tr_td_input">
                            <select id="d_storage_id" name="d_storage_id"  require="true" dataType="NotEmpty" msg=仓库不能为空!">
                                <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_storage" />
                            </select>	
                        </td>


                    </tr>
                    <tr class="table_edit_tr">
                        <td class="table_edit_tr_td_label">TVM存备数:</td>
                        <td class="table_edit_tr_td_input">
                            <input type="text" name="d_tvm_store_num" id="d_tvm_store_num"  size="10" require="true"  maxlength="10"
                                   dataType="integer" msg="TVM的存备数应为非负整数"/>
                        </td>

                        <td class="table_edit_tr_td_label">TVM数:</td>
                        <td class="table_edit_tr_td_input">
                            <input type="text" name="d_tvm_num" id="d_tvm_num" size="10" require="true"  maxlength="10" 
                                   dataType="integer" msg="TVM数应为非负整数"/>
                        </td>
                        <td class="table_edit_tr_td_label">ITM存备数:</td>
                        <td class="table_edit_tr_td_input">
                            <input type="text" name="d_itm_store_num" id="d_itm_store_num"  size="10" require="true"  maxlength="10"
                                   dataType="integer" msg="ITM的存备数应为非负整数"/>
                        </td>

                    </tr>
                    <tr class="table_edit_tr">
                        <td class="table_edit_tr_td_label">ITM数:</td>
                        <td class="table_edit_tr_td_input">
                            <input type="text" name="d_itm_num" id="d_itm_num" size="10" require="true"  maxlength="10" 
                                   dataType="integer" msg="ITM数应为非负整数"/>
                        </td>

                        <td class="table_edit_tr_td_label">BOM存备数:</td>
                        <td class="table_edit_tr_td_input">
                            <input type="text" name="d_bom_store_num" id="d_bom_store_num"  size="10" require="true"  maxlength="10"
                                   dataType="integer" msg="BOM的存备数应为非负整数"/>
                        </td>

                        <td class="table_edit_tr_td_label">BOM数:</td>
                        <td class="table_edit_tr_td_input">
                            <input type="text" name="d_bom_num" id="d_bom_num" size="10" require="true"  maxlength="10" 
                                   dataType="integer" msg="BOM数应为非负整数"/>
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


            <c:set var="clickMethod" scope="request" value="btnClick('detailOp','clearStart','detail','','clearStartHead');"/>
            <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all" />
            <br/>
        </FORM>
        <!--
        <c:set var="addQueryMethod" scope="request" value="setLineCardNames('detailOp','d_line_id','d_station_id','commonVariable','','','');"/>
        <c:set var="addClickMethod" scope="request" value="setUpdatePrimaryKey('detailOp','d_line_id#d_station_id#d_storage_id#d_tvm_store_num#d_tvm_num#d_itm_store_num#d_itm_num#d_bom_store_num#d_bom_num#d_remark');"/>
        -->


    </body>
</html>
