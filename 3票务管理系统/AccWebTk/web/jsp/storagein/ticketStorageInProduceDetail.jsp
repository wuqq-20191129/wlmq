<%-- 
    Document   : inProduceDetail
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
        <title>生产入库明细</title>


        <script language="javascript" type="text/javascript" charset="utf-8" src="js/common_form.js"></script>
        <script language="javascript" type="text/javascript" charset="utf-8" src="js/common_form_extend.js"></script>
        <script language="javascript" type="text/javascript" charset="utf-8" src="js/Validator.js"></script>
        <script language="JavaScript" type="text/javascript" charset="utf-8" src="js/CalendarPop.js"></script>
        <script language="JavaScript" type="text/javascript" charset="utf-8" src="js/icProduceOut.js"></script>
        <!--<script language="JavaScript" type="text/javascript" charset="utf-8" src="js/icStorageProductBill.js"></script>-->
        
        <script language="JavaScript" type="text/javascript" charset="utf-8" src="js/icStorageIn.js"></script>
        
        <link rel="stylesheet" type="text/css" href="css/frame/simple.css" title="Style"/>
    </head>
    <!-- preLoadIdVal('d_card_ava_days', 0); setPageControl('detailOp'); -->
<!--    <body onload="initDocument('query1','detail');
              setListViewDefaultValue('query1','clearStart');
              setPageControl('query1');">-->
    <body onload="
            initDocument('detailOp', 'detail');
            setListViewDefaultValue('detailOp', 'clearStart');
//            setPrimaryKeys('detailOp', 'd_water_no#d_bill_no#d_workType#d_ic_main_type#d_ic_sub_type#d_card_money#d_card_money1');
//            setDetailBillNo('detailOp', 'd_bill_no', 'queryCondition');
            controlByRecordFlagForInit('detailOp', ['del'], true);

//            setSelectValuesByFixed('detailOp', 'd_workType', ['00', '01', '02', '03']);
//            setPageControl('detailOp');
            setTableRowBackgroundBlock('DataTable')">

        <!--<body >-->
        <table  class="table_title">
            <tr align="center" class="trTitle">
                <td colspan="4">生产入库明细
                </td>
            </tr>
        </table>



        <!-- 表头 通用模板 -->
        <c:set var="pTitleName" scope="request" value="列表"/>
        <c:set var="pTitleWidth" scope="request" value="50"/>
        <c:import url="/jsp/common/common_template.jsp?template_name=common_table_title" />
        <div id="clearStartBlock" class="divForTableBlock">
            <div id="clearStartHead" class="divForTableBlockHead" style="width :1800px">
                <table class="table_list_block" id="DataTableHead" >

                    <!--说明：列的序号从0开始 isDigit：false 表示按字符串排序 ：true表示按数值排序 sortedby：asc表示按升序 dec表示按降序 -->
                    <tr  class="table_list_tr_head_block" id="ignore">
                        <td   class="table_list_tr_col_head_block">
                            <input type="checkbox" name="rectNoAll" id="rectNoAll" onclick="selectAllRecord('detailOp', 'rectNoAll', 'rectNo', 'clearStart', 0);
                                controlByRecordFlagForInit('detailOp', ['del'], true);
                                    "/>
                                    <!--controlsByFlag('detailOp', ['del']);-->
                                    <!--setPageControl('detailOp');-->
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
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="17" sortedby="asc"  onclick="sortForTableBlock('clearStart');">生产类别</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="18" sortedby="asc"  onclick="sortForTableBlock('clearStart');" style="width:100px">票卡有效天数</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="19" sortedby="asc"  onclick="sortForTableBlock('clearStart');">出站线路</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="20" sortedby="asc"  onclick="sortForTableBlock('clearStart');">出站车站</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="21" sortedby="asc"  onclick="sortForTableBlock('clearStart');" style="width:100px">进出站限制模式</td>


                    </tr>


                </table>

            </div>

            <div id="clearStart"  class="divForTableBlockData" style="width :1800px">
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
                            onclick="setSelectValuesByRowPropertyName('detailOp', 'd_ic_sub_type', 'commonVariable', 'cardMainCode');
                                    setSelectValuesByRowPropertyName('detailOp', 'd_area_id', 'commonVariable1', 'storageId');
                                    controlByRecordFlagForInit('detailOp', ['del'], true); 
                                    setSelectValuesByRowPropertyName('detailOp', 'd_station_id', 'commonVariable2', 'lineId');
//                                    setSelectValuesByRowPropertyName('detailOp', 'd_reason_id', 'commonVariable3', 'esWorkTypeId');
//                                    setSelectValuesByRowPropertyName('detailOp', 'd_exitStationId', 'commonVariable5', 'exitLineId');
                                    clickResultRow('detailOp', this, 'detail');

//                                    controlsByFlagWithoutCk('detailOp', ['modify']);
//                                    setPageControl('detailOp');
                                        " 
                                id="${rs.bill_no}#${rs.water_no}" cardMainCode="${rs.ic_main_type}" 
                                storageId="${rs.storage_id}" lineId="${rs.line_id}" >

                            <td id="rectNo1" class="table_list_tr_col_data_block" >
                                <input type="checkbox" name="rectNo" id="rectNo" onclick="unSelectAllRecord('detailOp', 'rectNoAll', 'rectNo');
                                        controlByRecordFlagForInit('detailOp', ['del'], true);
                                       "
                                       value="${rs.water_no}" >
                                    <!--setPageControl('detailOp');-->

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
                            <td  id="use_flag" class="table_list_tr_col_data_block">
                                ${rs.use_flag_name}
                            </td>
                            <td  id="card_ava_days" class="table_list_tr_col_data_block" style="width:100px">
                                ${rs.card_ava_days}
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

        <form method="post" name="detailOp" id="detailOp" action="ticketStorageInProduceDetail" >

            <c:set var="divideShow" scope="request" value="0"/>
            <c:import  url="/jsp/common/common_template_web_variable.jsp?template_name=common_web_variable" />
            <c:import  url="/jsp/common/common_template_web_variable_ic.jsp?template_name=common_web_variable_ic" />
            

            <!--<DIV id="detail" align="center" class="divForTableData" style="position:relative; left:20; width:95%; height:10; layer-background-color:#99CC99; border:thin outset #ffffff; z-index:1;">-->
            <div id="detail"  class="divForTableDataDetail" >
                <table  class="table_edit_detail">
                    <tr class="table_edit_tr">
                        <td class="table_edit_tr_td_label">流水号: </td>
                        <td class="table_edit_tr_td_input">
                            <input type="text" name="d_water_no" id="d_water_no" size="12" maxlength="12" readonly="true"/>
                        </td>
                        
                        <td class="table_edit_tr_td_label">票卡主类型:</td>
                        <td class="table_edit_tr_td_input">
                            <select id="d_ic_main_type" name="d_ic_main_type" dataType="Require" msg="票卡主类型不能为空!"
                                    onChange="setSelectValues('detailOp', 'd_ic_main_type', 'd_ic_sub_type', 'commonVariable');
                                    ">
                                <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_maincard" />
                            </select>
                                <!--select="'09'-->
                        </td>
                        
                        <td class="table_edit_tr_td_label"><div align="right">票卡子类型:</div></td>
                        <td class="table_edit_tr_td_input">
                            <select id="d_ic_sub_type" name="d_ic_sub_type" dataType="Require" msg="票卡子类型不能为空!">
                                <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_none" />
                            </select>
                            <c:set var="pVarName" scope="request" value="commonVariable"/>
                            <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_mainsubcard" />
                        </td>
                        
                        <td class="table_edit_tr_td_label">入库原因:</td>
                        <td class="table_edit_tr_td_input">
                            <select id="d_reason_id" name="d_reason_id" dataType="Require" msg="入库原因不能为空!">
                                <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_inreason_in_produce" />

                            </select>
                        </td>
                        
                        
                    </tr>
                    <tr class="table_edit_tr">
                        
                        <td class="table_edit_tr_td_label">仓库:</td>
                        <td class="table_edit_tr_td_input">
                            <select id="d_storage_id" name="d_storage_id" dataType="Require" msg="仓库不能为空!"
                                    onChange="
                                            setSelectValues('detailOp', 'd_storage_id', 'd_area_id', 'commonVariable1');
                                            setSelectValues('detailOp', 'd_storage_id', 'd_line_id', 'commonVariable4');
                                            setSelectValues('detailOp', 'd_line_id', 'd_station_id', 'commonVariable2');
                                    ">

                                <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_storage" />
                            </select>

                        </td>
                        
                        <td class="table_edit_tr_td_label"><div align="right">票区:</div></td>
                        <td class="table_edit_tr_td_input">
                            <select id="d_area_id" name="d_area_id" dataType="Require" msg="库存票区不能为空!" >
                                <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_none" />

                            </select>
                            <c:set var="pVarName" scope="request" value="commonVariable1"/>
                            <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_storagezone" />
                        </td>
                        
                        <td class="table_edit_tr_td_label">数量:</td>
                        <td class="table_edit_tr_td_input">
                            <input type="text" name="d_in_num" id="d_in_num" size="8" maxlength="9" require="true"  msg="数量为大于0的整数"/>
                        </td>
                        
                        <td class="table_edit_tr_td_label">位置:</td>
                        <td class="table_edit_tr_td_input">
                            <input type="text" name="d_detail_place" id="d_detail_place" size="20" maxlength="20" require="true" />
                        </td>
                    </tr>    
                    <tr class="table_edit_tr">
                        
                        <td class="table_edit_tr_td_label">票 盒:</td>
                        <td class="table_edit_tr_td_input">
                            <input type="text" name="d_start_box_id" id="d_start_box_id" size="14" maxlength="14" require="false"  dataType="integer" msg="票盒起号为大于0的整数!" />
                        </td>
                        <td class="table_edit_tr_td_label">至:</td>
                        <td class="table_edit_tr_td_input">
                            <input type="text" name="d_end_box_id" id="d_end_box_id" size="14" maxlength="14" require="false"  dataType="integer" msg="票盒止号为大于0的整数!" />
                        </td>
                        
                        <td class="table_edit_tr_td_label">进站线路:</td>
                        <td class="table_edit_tr_td_input">
                            <select id="d_line_id" name="d_line_id" require="false" 
                                    onChange="setSelectValues('detailOp', 'd_line_id', 'd_station_id', 'commonVariable2');">
                                <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_none" />
                            </select>
                            <c:set var="pVarName" scope="request" value="commonVariable4"/>
                            <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_icline_serial" />
                        </td>
                        
                        <td class="table_edit_tr_td_label">进站车站:</td>
                        <td class="table_edit_tr_td_input">
                            <select id="d_station_id" name="d_station_id" >
                                <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_none" />
                            </select>
                            <c:set var="pVarName" scope="request" value="commonVariable2"/>
                            <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_iclinestation" />
                        </td>
                        

                   
                    </tr>
                    <tr class="table_edit_tr">


                        <td class="table_edit_tr_td_label">逻辑卡号:</td>
                        <td class="table_edit_tr_td_input">
                            <input type="text" name="d_start_logical_id" id="d_start_logical_id" size="20" maxlength="20"  require="false"
                                   dataType="integer" msg="逻辑卡号起为全数字" onblur="checkInputNum(this,20)"/>
                        </td>
                        
                        <td class="table_edit_tr_td_label">至:</td>
                        <td class="table_edit_tr_td_input">
                            <input type="text" name="d_end_logical_id" id="d_end_logical_id" size="20" maxlength="20"  require="false"
                                   dataType="integer" msg="逻辑卡号止为全数字" onblur="checkInputNum(this,20)"/>
                        </td>
                    </tr>
                    <tr class="table_edit_tr">
                         
                        <td class="table_edit_tr_td_label">面 值(分):</td>
                        <td class="table_edit_tr_td_input">
                            <input type="text" name="d_card_money" id="d_card_money" size="8" maxlength="9" require="true"  dataType="integer" msg="面值为大于0的整数!" />
                        </td>

                        <td class="table_edit_tr_td_label">有效期:</td>
                        <td class="table_edit_tr_td_input">
                            <input type="text" name="d_card_ava_days" id="d_card_ava_days" value="" size="12" require="false" dataType="Date" format="ymd"
                                   msg="有效期格式为(yyyy-mm-dd)!" />
                            <a href="javascript:openCalenderDialogByID('d_card_ava_days','false');">
                                <img src="./images/calendar.gif" width="15" height="15" border="0" style="block"/>
                            </a>
                        </td>
                    
                        
                    </tr>

                </table>
            </div>






            <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all_init" />
            <c:set var="add" scope="request" value="0"/>
            <c:set var="modify" scope="request" value="0"/>
            <c:set var="del" scope="request" value="1"/>
            <c:set var="save" scope="request" value="0"/>
            <c:set var="cancle" scope="request" value="0"/>
            <c:set var="exportBill" scope="request" value="1"/>

           

            <%--<c:set var="addClickMethod" scope="request" value="setUpdatePrimaryKey('detailOp','d_water_no#d_bill_no#d_workType#d_ic_main_type#d_ic_sub_type#d_card_money#d_card_money1');controlByCardTypeAndAreaForProduce();"/>--%>
            <c:set var="clickMethod" scope="request" value="btnClick('detailOp','clearStart','detail')"/>
            <c:set var="exportBillAfter" scope="request" value="exportBillDetail('detailOp','w_rpt_ic_rct_bl_in_produce',['p_bill_no'],['queryCondition'],800,500)"/>


            <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all" />
            <br/>
        </FORM>


        <!-- 状态栏 通用模板 -->

        <c:import url="/jsp/common/common_template.jsp?template_name=common_status_table" />
    </body>
</html>
