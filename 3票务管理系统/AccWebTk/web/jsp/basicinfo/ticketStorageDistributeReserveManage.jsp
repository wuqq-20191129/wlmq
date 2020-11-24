<%-- 
    Document   : ticketStorageDistributeReserveManage
    Created on : 2017-8-10, 19:01:17
    Author     : liudezeng
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>配票保有量参数</title>


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
            setPrimaryKeys('detailOp', 'd_line_id#d_station_id#d_storage_id#d_ic_main_type#d_ic_sub_type#d_card_money');
            setListViewDefaultValue('detailOp', 'clearStart');
            setQueryControlsDefaultValue('queryOp', 'detailOp');
            setPageControl('detailOp');
            setTableRowBackgroundBlock('DataTable')">

        <table  class="table_title">
            <tr align="center" class="trTitle">
                <td colspan="4">配票保有量参数
                </td>
            </tr>
        </table>

        <!-- 表头 通用模板 -->

        <c:set var="pTitleName" scope="request" value="查询"/>
        <c:set var="pTitleWidth" scope="request" value="50"/>
        <c:import  url="/jsp/common/common_template.jsp?template_name=common_table_title" />

        <form method="post" name="queryOp" id="queryOp" action="ticketStorageDistributeReserveManage">
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
                </tr>
                <tr calss="table_edit_tr">

                    <td class="table_edit_tr_td_label" >票卡主类型:</td>
                    <td class="table_edit_tr_td_input">
                        <select id="q_ic_main_type" name="q_ic_main_type"
                                onChange="setSelectValues('queryOp', 'q_ic_main_type', 'q_ic_sub_type', 'commonVariable1');"  >				    
                            <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_maincard" />
                        </select>
                    </td>
                    <td class="table_edit_tr_td_label"><div align="right">票卡子类型:</div></td>
                    <td class="table_edit_tr_td_input">
                        <select id="q_ic_sub_type" name="q_ic_sub_type" >
                            <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_none" />
                        </select>
                        <c:set var="pVarName" scope="request" value="commonVariable1"/>
                        <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_mainsubcard" />
                    </td>

                    <td class="table_edit_tr_td_label" rowspan="1">

                        <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all_init" />
                        <c:set var="query" scope="request" value="1"/>
                        <c:set var="addClickMethod" scope="request" 
                               value="setControlNames('queryOp','q_line_id#q_station_id#q_storage_id#q_ic_main_type#q_ic_sub_type');
                               setLineCardNames('queryOp','q_line_id','q_station_id','commonVariable','q_ic_main_type','q_ic_sub_type','commonVariable1');"/>
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
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="2"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 80px">车站</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="3"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 80px">仓库</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="4"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 120px">票卡主类型</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="5"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 120px">票卡子类型</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="6"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 80px">面值（分/次）</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="7"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 120px">预制票预留数</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="8"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 250px">备注</td>

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
                            onclick=" setSelectValuesByRowPropertyName('detailOp', 'd_ic_sub_type', 'commonVariable1', 'icMainType');
                                setSelectValuesByRowPropertyName('detailOp', 'd_station_id', 'commonVariable', 'icLineID');
                                    clickResultRow('detailOp', this, 'detail');
                                    setPageControl('detailOp');" 
                            id="${rs.line_id}#${rs.station_id}#${rs.storage_id}#${rs.ic_main_type}#${rs.ic_sub_type}#${rs.card_money}#${rs.reverve_num}#${rs.remark}"
                            icMainType="${rs.ic_main_type}" icLineID = "${rs.line_id}">

                            <td id="rectNo1" class="table_list_tr_col_data_block">
                                <input type="checkbox" name="rectNo" id="rectNo" onclick="unSelectAllRecord('detailOp', 'rectNoAll', 'rectNo');"
                                       value="${rs.line_id}#${rs.station_id}#${rs.storage_id}#${rs.ic_main_type}#${rs.ic_sub_type}#${rs.card_money}"  >

                                </input>
                            </td>

                            <td  id="line_id" class="table_list_tr_col_data_block" style="width: 80px">
                                ${rs.line_id_name}
                            </td>
                            <td  id="station_id" class="table_list_tr_col_data_block" style="width: 80px">
                                ${rs.station_id_name}
                            </td>
                            <td  id="storage_id" class="table_list_tr_col_data_block" style="width: 80px">
                                ${rs.storage_id_name}
                            </td>
                            <td  id="ic_main_type" class="table_list_tr_col_data_block" style="width: 120px">
                                ${rs.ic_main_type_name}
                            </td>
                            <td  id="ic_sub_type" class="table_list_tr_col_data_block" style="width: 120px">
                                ${rs.ic_sub_type_name}
                            </td>
                            <td  id="card_money" class="table_list_tr_col_data_block" style="width: 80px">
                                ${rs.card_money}
                            </td>
                            <td  id="reverve_num" class="table_list_tr_col_data_block" style="width: 120px">
                                ${rs.reverve_num}
                            </td>
                            <td  id="remark" class="table_list_tr_col_data_block" style="width: 250px">
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

        <FORM method="post" name="detailOp" id="detailOp" action="ticketStorageDistributeReserveManage" >
            
            <input type="hidden" name="expAllFields" id="d_expAllFields"
                   value="line_id_name,station_id_name,storage_id_name,ic_main_type_name,ic_sub_type_name,card_money,reverve_num,remark" />
            <input type="hidden" name="expFileName" id="d_expFileName" value="配票保有量参数.xlsx" />
            <input type="hidden" name="divId" id="d_divId" value="clearStartHead">
            <input type="hidden" name="methodUrl" id="d_methodUrl" value="/TicketStorageDistributeReserveManageExportAll"/>

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
                        <td class="table_edit_tr_td_label">票卡主类型: </td>
                        <td class="table_edit_tr_td_input">
                            <select id="d_ic_main_type" name="d_ic_main_type" onChange="setSelectValues('detailOp', 'd_ic_main_type', 'd_ic_sub_type', 'commonVariable1');"require="true" dataType="NotEmpty" msg="票卡主类型不能为空!">
                                <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_maincard" />
                            </select>	
                        </td>
                        <td class="table_edit_tr_td_label">票卡子类型: </td>
                        <td class="table_edit_tr_td_input">
                            <select id="d_ic_sub_type" name="d_ic_sub_type" require="true" dataType="LimitB" min="1" msg="票卡子类型不能为空">
                                <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_none" />

                            </select>
                            <c:set var="pVarName" scope="request" value="commonVariable1"/>
                            <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_mainsubcard" />
                        </td>
                    </tr>
                    <tr class="table_edit_tr">
                        <td class="table_edit_tr_td_label">仓库: </td>
                        <td class="table_edit_tr_td_input">
                            <select id="d_storage_id" name="d_storage_id"  require="true" dataType="NotEmpty" msg=仓库不能为空!">
                                <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_storage" />
                            </select>	
                        </td>
                        
                        <td class="table_edit_tr_td_label">面值: </td>
                        <td class="table_edit_tr_td_input">
                            <select id="d_card_money" name="d_card_money" dataType="Integer" msg="面值不能为空!" >
                                <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_cardmoney" />
                            </select>	
                        </td>
                        <td class="table_edit_tr_td_label">预制票保留数：</td>
                        <td class="table_edit_tr_td_input">
                            <input type="text" name="d_reverve_num" id="d_reverve_num" size="10" require="true" maxlength="10"
                                   dataType="integer"msg="预制票保留数应为非负整数"/>
                        </td>


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
        <c:set var="addQueryMethod" scope="request" value="setLineCardNames('detailOp','d_ic_main_type','d_ic_sub_type','commonVariable','','','');
               setLineCardNames('detailOp','d_line_id','d_station_id','commonVariable','','','');"/>
        <c:set var="addClickMethod" scope="request" value="setUpdatePrimaryKey('detailOp','d_line_id#d_station_id#d_ic_main_type#d_ic_sub_type#d_card_money');"/>
        -->

    </body>
</html>

