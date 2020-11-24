<%-- 
    Document   : inAdjustDetail
    Created on : 2017-8-10
    Author     : mqf
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <!--add by zhongzq 20180609-->
        <meta http-equiv="X-UA-Compatible" content="IE=9,Chrome=1" />
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>调账入库明细</title>


        <script language="javascript" type="text/javascript" charset="utf-8" src="js/common_form.js"></script>
        <script language="javascript" type="text/javascript" charset="utf-8" src="js/common_form_extend.js"></script>
        <script language="javascript" type="text/javascript" charset="utf-8" src="js/Validator.js"></script>
        <script language="JavaScript" type="text/javascript" charset="utf-8" src="js/CalendarPop.js"></script>
        <script language="JavaScript" type="text/javascript" charset="utf-8" src="js/icStorageIn.js"></script>
        <script language="JavaScript" type="text/javascript" charset="utf-8" src="js/icProduceOut.js"></script>
        
        <link rel="stylesheet" type="text/css" href="css/frame/simple.css" title="Style"/>
    </head>
    <body onload="
            initDocument('detailOp', 'detail');
            setListViewDefaultValue('detailOp', 'clearStart');
//            controlByRecordFlagForInit('detailOp', ['del'], true);

            setTableRowBackgroundBlock('DataTable')">

        <!--<body >-->
        <table  class="table_title">
            <tr align="center" class="trTitle">
                <td colspan="4">调账入库明细
                </td>
            </tr>
        </table>



        <!-- 表头 通用模板 -->
        <c:set var="pTitleName" scope="request" value="列表"/>
        <c:set var="pTitleWidth" scope="request" value="50"/>
        <c:import url="/jsp/common/common_template.jsp?template_name=common_table_title" />
        <div id="clearStartBlock" class="divForTableBlock">
            <div id="clearStartHead" class="divForTableBlockHead" style="width :1600px">
                <table class="table_list_block" id="DataTableHead" >
                    

                    <!--说明：列的序号从0开始 isDigit：false 表示按字符串排序 ：true表示按数值排序 sortedby：asc表示按升序 dec表示按降序 -->
                    <tr  class="table_list_tr_head_block" id="ignore">
                        <td   class="table_list_tr_col_head_block">
                            <input type="checkbox" name="rectNoAll" id="rectNoAll" onclick="selectAllRecord('detailOp', 'rectNoAll', 'rectNo', 'clearStart', 0);
                                controlByRecordFlagForInit('detailOp', ['del'], true);
//                                disableButtonByRecord('detailOp','del')
                                    "/>
                        </td>	
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="1"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >流水号</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="2"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >入库原因</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="3"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >仓库</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="4"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >票区</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false  index="5"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >票卡主类型</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="6"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >票卡子类型</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=true index="7"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >数量</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="8"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width:130px">逻辑卡号起</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="9"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width:130px">逻辑卡号止</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="10" sortedby="asc"  onclick="sortForTableBlock('clearStart');">详细位置</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="11" sortedby="asc"  onclick="sortForTableBlock('clearStart');">面值(分/次)</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="12" sortedby="asc"  onclick="sortForTableBlock('clearStart');">有效期</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="13" sortedby="asc"  onclick="sortForTableBlock('clearStart');" style="width:100px">盒起号</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="14" sortedby="asc"  onclick="sortForTableBlock('clearStart');" style="width:100px">盒止号</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="15" sortedby="asc"  onclick="sortForTableBlock('clearStart');">进站线路</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="16" sortedby="asc"  onclick="sortForTableBlock('clearStart');">进站车站</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="19" sortedby="asc"  onclick="sortForTableBlock('clearStart');">出站线路</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="20" sortedby="asc"  onclick="sortForTableBlock('clearStart');">出站车站</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="21" sortedby="asc"  onclick="sortForTableBlock('clearStart');" style="width:100px">进出站限制模式</td>


                    </tr>


                </table>

            </div>

            <div id="clearStart"  class="divForTableBlockData" style="width :1600px">
                <table class="table_list_block" id="DataTable" >

                    <c:forEach items="${ResultSet}" var="rs">

<!--                        onClick="
                setSelectValuesByRowPropertyName('query1','d_card_sub_code','commonVariable','cardMainCode');
                setSelectValuesByRowPropertyName('query1','d_area_id','commonVariable1','storageId');
                disableButtonByRecord('query1','del');
                setSelectValuesByRowPropertyName('query1','d_station_id','commonVariable2','lineId');
                clickResultRow('query1',this,'detail');
                setPageControl('query1');-->
                                    <!--disableButtonByRecord('detailOp','del');-->
                        <tr class="table_list_tr_data"  onMouseOver="overResultRow('detailOp', this);" 
                            onMouseOut="outResultRow('detailOp', this);" 
                            onclick="controlByRecordFlagForInit('detailOp', ['del'], true);" 
                                id="${rs.bill_no}#${rs.water_no}" 
                                >

                            <td id="rectNo1" class="table_list_tr_col_data_block" >
                                <input type="checkbox" name="rectNo" id="rectNo" onclick="unSelectAllRecord('detailOp', 'rectNoAll', 'rectNo');
                                        controlByRecordFlagForInit('detailOp', ['del'], true);
                                       "
                                       value="${rs.water_no}" >

                                </input>
                                        <!--controlsByFlag('detailOp', ['modify', 'del']);-->
                            </td>
                            <td  id="water_no" class="table_list_tr_col_data_block" >
                                ${rs.water_no}
                            </td>
                            <td  id="reason_id" class="table_list_tr_col_data_block" >
                                ${rs.reason_id_name}
                            </td>
                            <td  id="storage_id" class="table_list_tr_col_data_block">
                                ${rs.storage_id_name}
                            </td>
                            <td  id="area_id" class="table_list_tr_col_data_block">
                                ${rs.area_id_name}
                            </td>
                            <td  id="ic_main_type" class="table_list_tr_col_data_block">
                                ${rs.ic_main_type_name}
                            </td>
                            <td  id="ic_sub_type" class="table_list_tr_col_data_block">
                                ${rs.ic_sub_type_name}
                            </td>
                            <td  id="in_num" class="table_list_tr_col_data_block">
                                ${rs.in_num}
                            </td>
                            <td  id="start_logical_id" class="table_list_tr_col_data_block" style="width:130px">
                                ${rs.start_logical_id}
                            </td>
                            <td  id="end_logical_id" class="table_list_tr_col_data_block" style="width:130px">
                                ${rs.end_logical_id}
                            </td>
                            <td  id="detail_place" class="table_list_tr_col_data_block">
                                ${rs.detail_place}
                            </td>
                            <td  id="card_money" class="table_list_tr_col_data_block">
                                ${rs.card_money}
                            </td>

                            <td  id="valid_date" class="table_list_tr_col_data_block">
                                ${rs.valid_date}
                            </td>
                            <td  id="start_box_id" class="table_list_tr_col_data_block" style="width:100px">
                                ${rs.start_box_id}
                            </td>
                            <td  id="end_box_id" class="table_list_tr_col_data_block" style="width:100px">
                                ${rs.end_box_id}
                            </td>
                            <td  id="line_id" class="table_list_tr_col_data_block">
                                ${rs.line_id_name}
                            </td>
                            <td  id="station_id" class="table_list_tr_col_data_block">
                                ${rs.station_id_name}
                            </td>
                            <td  id="exit_line_id" class="table_list_tr_col_data_block">
                                ${rs.exit_line_id_name}
                            </td>
                            <td  id="exit_station_id" class="table_list_tr_col_data_block">
                                ${rs.exit_station_id_name}
                            </td>
                            <td  id="mode" class="table_list_tr_col_data_block" style="width:100px">
                                ${rs.model_name}
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

        <form method="post" name="detailOp" id="detailOp" action="ticketStorageInAdjustDetail" >

            <c:set var="divideShow" scope="request" value="0"/>
            <c:import  url="/jsp/common/common_template_web_variable.jsp?template_name=common_web_variable" />
            <c:import  url="/jsp/common/common_template_web_variable_ic.jsp?template_name=common_web_variable_ic" />
            

            <!--<DIV id="detail" align="center" class="divForTableData" style="position:relative; left:20; width:95%; height:10; layer-background-color:#99CC99; border:thin outset #ffffff; z-index:1;">-->
            <div id="detail"  class="divForTableDataDetail" >
                <table  class="table_edit_detail">
                </table>
            </div>
<!--            <input id="d_oper_type" type="hidden" name="ctype" size="15"  value="{/Service/Result/oper_type}" />
            <input id="d_bill_no" type="hidden" name="bill_no" size="15"  value="{/Service/Result/bill_no}" />
            <input id="record_flag" type="hidden" name="record_flag"  value="{/Service/Result/record_flag}" ></input>-->


            <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all_init" />
            <c:set var="del" scope="request" value="1"/>
            <c:set var="exportBill" scope="request" value="1"/>

           

            <c:set var="clickMethod" scope="request" value="btnClick('detailOp','clearStart','detail')"/>
            <c:set var="exportBillAfter" scope="request" value="exportBillDetail('detailOp','w_rpt_ic_rct_bl_in_adjust',['p_bill_no'],['queryCondition'],800,500)"/>


            <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all" />
            <br/>
        </FORM>


        <!-- 状态栏 通用模板 -->

        <c:import url="/jsp/common/common_template.jsp?template_name=common_status_table" />
    </body>
</html>
