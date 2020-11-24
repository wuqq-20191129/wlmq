<%-- 
    Document   : ticketStorageInReturnDetail
    Created on : 2017-8-15, 16:06:19
    Author     : zhouyang
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="X-UA-Compatible" content="IE=9,Chrome=1" />
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>回收入库明细</title>


        <script language="javascript" type="text/javascript" charset="utf-8" src="js/common_form.js"></script>
        <script language="javascript" type="text/javascript" charset="utf-8" src="js/common_form_extend.js"></script>
        <script language="javascript" type="text/javascript" charset="utf-8" src="js/Validator.js"></script>
        <script language="JavaScript" type="text/javascript" charset="utf-8" src="js/CalendarPop.js"></script>
        <script language="JavaScript" type="text/javascript" charset="utf-8" src="js/icProduceOut.js"></script>
        <script language="JavaScript" type="text/javascript" charset="utf-8" src="js/icStorageIn.js"></script>
        <script language="JavaScript" type="text/javascript" charset="utf-8" src="js/icStorageProductBill.js"></script>
        <link rel="stylesheet" type="text/css" href="css/frame/simple.css" title="Style"/>
        <script LANGUAGE="JavaScript">
            function disableFormControlsByIdForInReturn(contro1,contro2,contro3,disabled) {
                var ob;
                var cont;
                ob=document.getElementById("d_reason_id").value;
                if(ob==null || ob==""){
                    for (i = 0; i < contro1.length; i++) {
                        cont=document.getElementById(contro1[i]);
                        if (cont === null)
                            continue;
                        cont.disabled = disabled;
                    }
                }else if(ob==10 || ob==12){
                    for (i = 0; i < contro2.length; i++) {
                        cont=document.getElementById(contro2[i]);
                        if (cont === null)
                            continue;
                        cont.disabled = disabled;
                    }
                }else{
                    for (i = 0; i < contro3.length; i++) {
                        cont=document.getElementById(contro3[i]);
                        if (cont === null)
                            continue;
                        cont.disabled = disabled;
                    }
                }
            }
            function controlsByReasonId(idName,formName,btn) {
                var ob;
                var i;
                ob=document.getElementById("d_reason_id").value;
                if(ob==10 || ob==12){
                    for (i = 0; i < btn.length; i++) {
                        document.forms[formName].getElementsByTagName('input')[btn[i]].disabled = true;
                    }
                }
            }
        </script>
    </head>
    <!-- preLoadIdVal('d_validDate', 0); setPageControl('detailOp'); -->
    <body onload="
            initDocument('detailOp','detail');
            setListViewDefaultValue('detailOp','clearStart');
            setPrimaryKeys('detailOp', 'd_billNo');
            setDetailBillNo('detailOp', 'd_billNo', 'queryCondition');
            controlByRecordFlagForInit('detailOp', ['add'], true);
            setTableRowBackgroundBlock('DataTable');">

        <!--<body >-->
        <table  class="table_title">
            <tr align="center" class="trTitle">
                <td colspan="4">回收入库明细
                </td>
            </tr>
        </table>



        <!-- 表头 通用模板 -->
        <c:set var="pTitleName" scope="request" value="列表"/>
        <c:set var="pTitleWidth" scope="request" value="50"/>
        <c:import url="/jsp/common/common_template.jsp?template_name=common_table_title" />
        <div id="clearStartBlock" class="divForTableBlock">
            <div id="clearStartHead" class="divForTableBlockHead" style="width :1700px">
                <table class="table_list_block" id="DataTableHead" >

                    <!--说明：列的序号从0开始 isDigit：false 表示按字符串排序 ：true表示按数值排序 sortedby：asc表示按升序 dec表示按降序 -->
                    <tr  class="table_list_tr_head_block" id="ignore">
                        <td   class="table_list_tr_col_head_block">
                            <input type="checkbox" name="rectNoAll" id="rectNoAll" onclick="selectAllRecord('detailOp', 'rectNoAll', 'rectNo', 'clearStart', 0);
                                    controlsByFlag('detailOp', ['del']);
                                    controlsByFlagWithoutCk('detailOp', ['modify']);
                                    controlByRecordFlagForInit('detailOp', ['add'], true);"
                                     />
                        </td>	
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="1"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width:100px" >流水号</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="2"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width:100px" >入库原因</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="3"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width:80px" >仓库</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="4"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width:80px" >票区</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false  index="5"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width:100px" >票卡主类型</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="6"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width:100px" >票卡子类型</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="7"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width:70px" >数量</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="8"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width:70px" >面值(分/次)</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="9"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width:110px" >报表日期</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=true index="10" sortedby="asc"  onclick="sortForTableBlock('clearStart');"style="width:100px" >回收线路</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=true index="11" sortedby="asc"  onclick="sortForTableBlock('clearStart');"style="width:100px" >回收车站</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="12" sortedby="asc"  onclick="sortForTableBlock('clearStart');"style="width:100px" >乘次票限制模式</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="13" sortedby="asc"  onclick="sortForTableBlock('clearStart');"style="width:110px" >有效期</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="14" sortedby="asc"  onclick="sortForTableBlock('clearStart');"style="width:150px" >车票起始逻辑卡号</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="15" sortedby="asc"  onclick="sortForTableBlock('clearStart');"style="width:150px" >车票结束逻辑卡号</td>


                    </tr>


                </table>

            </div>

            <div id="clearStart"  class="divForTableBlockData" style="width :1700px">
                <table class="table_list_block" id="DataTable" >

                    <c:forEach items="${ResultSet}" var="rs">

                        <tr class="table_list_tr_data"  onMouseOver="overResultRow('detailOp', this);" 
                            onMouseOver="overResultRow('detailOp',this);"
                                onMouseOut="outResultRow('detailOp',this);"
                                onClick="setSelectValuesByRowPropertyName('detailOp','d_card_sub_code','commonVariable','cardMainCode');
                                setSelectValuesByRowPropertyName('detailOp','d_area_id','commonVariable1','storageId');
                                setSelectValuesByRowPropertyName('detailOp','d_line_id_reclaim','commonVariable4','storageId');
                                setSelectValuesByRowPropertyName('detailOp','d_station_id_reclaim','commonVariable2','line_id_reclaim');
                                clickResultRow('detailOp',this,'detail');
                                clickResultRow('detailOp', this, 'detail');
                                controlsByFlag('detailOp', ['modify', 'del']);
                                controlsByFlagWithoutCk('detailOp', ['modify']);
                                controlsByReasonId('reason_id','detailOp', ['modify']);
                                controlByRecordFlagForInit('detailOp', ['add'], true);"
                                style="display:"
                            id="${rs.water_no}" flag="${rs.record_flag}" cardMainCode="${rs.ic_main_type}" line_id_reclaim="${rs.line_id_reclaim}"
                            storageId="${rs.storage_id}" line_id_reclaim="${rs.line_id}">

                            <td id="rectNo1" class="table_list_tr_col_data_block" >
                                <input type="checkbox" name="rectNo" id="rectNo" onclick="unSelectAllRecord('detailOp', 'rectNoAll', 'rectNo');
                                        controlsByFlag('detailOp', ['modify', 'del']);
                                        controlsByFlagWithoutCk('detailOp', ['modify']);
                                        controlByRecordFlagForInit('detailOp', ['add'], true);
                                       "
                                       value="${rs.bill_no}"  flag="${rs.record_flag}" >

                                </input>
                            </td>
                            <td  id="waterNo" class="table_list_tr_col_data_block"  style="width:100px" >
                                ${rs.water_no}
                            </td>
                            <td  id="reason_id" class="table_list_tr_col_data_block" style="width:100px">
                                ${rs.reason_name}
                            </td>
                            <td  id="storage_id" class="table_list_tr_col_data_block" style="width:80px" >
                                ${rs.storage_name}
                            </td>
                            <td  id="area_id" class="table_list_tr_col_data_block" style="width:80px" >
                                ${rs.area_name}
                            </td>
                            <td  id="card_main_code" class="table_list_tr_col_data_block" style="width:100px" >
                                ${rs.ic_main_type_name}
                            </td>
                            <td  id="card_sub_code" class="table_list_tr_col_data_block" style="width:100px" >
                                ${rs.ic_sub_type_name}
                            </td>
                            <td  id="in_num" class="table_list_tr_col_data_block" style="width:70px" >
                                ${rs.in_num}
                            </td>
                            <td  id="card_money" class="table_list_tr_col_data_block" style="width:70px" >
                                ${rs.card_money}
                            </td>
                            <td  id="report_date" class="table_list_tr_col_data_block" style="width:110px" >
                                ${rs.report_date}
                            </td>
                            <td  id="line_id_reclaim" class="table_list_tr_col_data_block" style="width:100px" >
                                ${rs.line_name}
                            </td>
                            <td  id="station_id_reclaim" class="table_list_tr_col_data_block" style="width:100px" >
                                ${rs.station_name}
                            </td>
                            <td  id="mode" class="table_list_tr_col_data_block" style="width:100px" >
                                ${rs.model_name}
                            </td>
                            <td  id="valid_date" class="table_list_tr_col_data_block" style="width:110px" >
                                ${rs.valid_date}
                            </td>
                            <td  id="start_logical_id" class="table_list_tr_col_data_block" style="width:150px" >
                                ${rs.start_logical_id}
                            </td>
                            <td  id="end_logical_id" class="table_list_tr_col_data_block" style="width:150px" >
                                ${rs.end_logical_id}
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

        <form method="post" name="detailOp" id="detailOp" action="ticketStorageInReturnDetailController" >

            <c:set var="divideShow" scope="request" value="0"/>
            <c:import  url="/jsp/common/common_template_web_variable.jsp?template_name=common_web_variable" />
            <c:import  url="/jsp/common/common_template_web_variable_ic.jsp?template_name=common_web_variable_ic" />
            <input id="d_oper_type" type="hidden" name="ctype"
                    size="15" value="${oper_type}" />
            <input id="d_bill_no" type="hidden" name="bill_no"
                    size="15" value="${bill_no}" />
            <input id="in_type" type="hidden" name="in_type"
                    value="${in_type}" />
            <input id="record_flag" type="hidden" name="record_flag" 
                   value="${record_flag}" />

            <!--<DIV id="detail" align="center" class="divForTableData" style="position:relative; left:20; width:95%; height:10; layer-background-color:#99CC99; border:thin outset #ffffff; z-index:1;">-->
            <div id="detail"  class="divForTableDataDetail" >
                <table  class="table_edit_detail">
                    <tr class="table_edit_tr">
                        <input type="hidden" name="d_billNo" id="d_billNo" size="10" maxlength="12" />
                        <td class="table_edit_tr_td_label">流水号: </td>
                        <td class="table_edit_tr_td_input">
                            <input type="text" name="d_waterNo" id="d_waterNo" size="10" maxlength="18" />
                        </td>
                        <td class="table_edit_tr_td_label">入库原因: </td>
                        <td class="table_edit_tr_td_input">
                            <select id="d_reason_id" name="d_reason_id" dataType="Require" msg="入库原因不能为空!"
                                    onChange="returnUpdateRequired();
			
			setSelectValuesByMapping('detailOp','d_reason_id','d_area_id','commonVariable1',['10','11','13','14','24'],[['07','05'],['07'],['01','04','05','06','07'],['04','05'],['01','04','05','06','07']]);
                        setSelectValuesByMapping('detailOp','d_reason_id','d_card_main_code','commonVariable3',['11','13','14','24'],[['12'],['1','2','7','8','10','12','40'],['2'],['1','2','7','8','10','12','40']]);
			setSelectValues('detailOp','d_card_main_code','d_card_sub_code','commonVariable');
			">
                                <c:set var="filter_1" scope="request" value="09"/>
                                <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_inReason" />
                            </select>
                        </td>

                        <td class="table_edit_tr_td_label">票卡主类型:</td>
                        <td class="table_edit_tr_td_input">
                            <select id="d_card_main_code"
                                name="d_card_main_code" required="true"  msg="票卡主类型不能为空!"
                                onChange="setSelectValues('detailOp','d_card_main_code','d_card_sub_code','commonVariable');
                                 setSelectValuesByMulMapping('detailOp','d_reason_id','d_card_main_code','d_area_id','commonVariable1',
                                ['11#12',
                                 '13#1','13#2','13#7','13#8','13#10','13#12','13#40',
                                 '14#2',
                                 '24#1','24#2','24#7','24#8','24#10','24#12','24#40'
                                ],
                                [['07'],
                                ['01','04','05','06','07'],
                                ['01','04','05','06','07'],
                                ['01','04','05','06','07'],
                                ['01','04','05','06','07'],
                                ['01','04','05','06','07'],
                                ['01','04','05','06','07'],
                                ['01','04','05','06','07'],
                                ['04','05'],
                                ['01','04','05','06','07'],
                                ['01','04','05','06','07'],
                                ['01','04','05','06','07'],
                                ['01','04','05','06','07'],
                                ['01','04','05','06','07'],
                                ['01','04','05','06','07'],
                                ['01','04','05','06','07']]);
                            ">
                                <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_icCardMainType" />
                                <c:import url="/jsp/common/common_template.jsp?template_name=option_values" />
                                <c:set var="pVarName" scope="request" value="commonVariable3"/>
                                <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_icCardMainTypeHidden" />
                            </select>
                            
                        </td>
                        
                        <td class="table_edit_tr_td_label"><div align="right">票卡子类型:</div></td>
                        <td class="table_edit_tr_td_input">
                            <select id="d_card_sub_code"
                                    name="d_card_sub_code" required="false" dataType="" msg="票卡子类型不能为空!">
                                <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_none" />
                            </select>
                            <c:set var="pVarName" scope="request" value="commonVariable"/>
                            <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_mainsubcard" />
                        </td>

                    </tr>
                    <tr class="table_edit_tr">
                        
                   
                        <td class="table_edit_tr_td_label">仓 库:</td>
                        <td class="table_edit_tr_td_input">
                            <select id="d_storage_id" name="d_storage_id" dataType="Require" msg="仓库不能为空!" 
                                    onchange="setSelectValues('detailOp','d_storage_id','d_line_id_reclaim','commonVariable4');
                                              setSelectValues('detailOp','d_line_id_reclaim','d_station_id_reclaim','commonVariable2');">
                                <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_storage" />
                            </select>
                        </td>
                        <td class="table_edit_tr_td_label">票 区:</td>
                        <td class="table_edit_tr_td_input">
                             <select id="d_area_id" name="d_area_id" required="false" >
                                <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub" />
                                <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_none" />
                            </select>
                            <c:set var="pVarName" scope="request" value="commonVariable1"/>
                            <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_storagezone" />
                        </td>
                        <td class="table_edit_tr_td_label">数量:</td>
                        <td class="table_edit_tr_td_input">
                            <input type="text" name="d_in_num"  id="d_in_num" size="10" require="true" maxlength="9" operator="GreaterThanEqual" dataType="integer"
                                   value="0" msg="数量为大于或等于零的整数" />
                        </td>
                        
                        
                        <td class="table_edit_tr_td_label">面值(分/次):</td>
                        <td class="table_edit_tr_td_input">
                            <input type="text"
                                name="d_card_money" id="d_card_money" size="3"
                                require="false" dataType="Integer|Limit" maxlength="5"
                                value="0" msg="面值为大于或等于零的数字" />
                            <select id="d_cardMoney1"
                                    name="d_cardMoney1" dataType="false"
                                    onChange="document.getElementById('d_card_money').value = this.value">
                                <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_cardmoney" />
                            </select>
                        </td>

                    </tr>
                    <tr class="table_edit_tr">


                        <td class="table_edit_tr_td_label">报表日期</td>
                        <td class="table_edit_tr_td_input">
                           <input type="text" name="d_report_date" id="d_report_date" value="" size="12" require="false" dataType="Date" format="ymd"
                               msg="有效期格式为(yyyy-mm-dd)!" />
                            <a  href="javascript:openCalenderDialogByID('d_report_date','false');">
                                <img  src="./images/calendar.gif" width="15" height="15" border="0" style="block"/>
                            </a>
                        </td>

                        <td class="table_edit_tr_td_label">回收线路:</td>
                        <td class="table_edit_tr_td_input">
                            <select id="d_line_id_reclaim"
                                        name="d_line_id_reclaim" required="false"
                                        onChange="setSelectValues('detailOp','d_line_id_reclaim','d_station_id_reclaim','commonVariable2');">
                                <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_icline" />
                            </select>
                            <c:set var="pVarName" scope="request" value="commonVariable4"/>
                            <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_icCodStorageLineHidden" />
                        </td>
                        
                        
                        <td class="table_edit_tr_td_label">回收车站:</td>
                        <td class="table_edit_tr_td_input">
                            <select id="d_station_id_reclaim"
                                        name="d_station_id_reclaim" required="false"
                                <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_none" />
                                <c:set var="pVarName" scope="request" value="commonVariable2"/>
                            <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_iclinestation" />
                            </select>
                        </td>
                    </tr>
                     <tr class="table_edit_tr">

                        <td class="table_edit_tr_td_label">车票起始逻辑卡号</td>
                        <td class="table_edit_tr_td_input">
                            <input type="text" name="d_start_logical_id" id="d_start_logical_id" size="10" require="false" maxlength="4" dataType="Number" msg="车票起始ID应为数字" />
                        </td>
                        <td class="table_edit_tr_td_label">车票结束逻辑卡号</td>
                        <td class="table_edit_tr_td_input">
                            <input type="text" name="d_end_logical_id" id="d_end_logical_id" size="10" require="false" maxlength="4" dataType="Number" msg="车票结束ID应为数字" />
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
            <c:set var="exportBill" scope="request" value="1"/>

           

            <c:set var="addformQueryMethod" scope="request" value="setLineCardNames('detailOp','','','','card_main_code','card_sub_code','commonVariable');"/>
            <c:set var="addClickMethod" scope="request" value="setUpdatePrimaryKey('detailOp','d_waterNo#d_start_logical_id#d_end_logical_id#d_billNo#d_workType#d_cardMainType#d_cardSubType#d_cardMoney#d_cardMoney1');"/>
            <c:set var="clickMethod" scope="request" value="btnClick('detailOp','clearStart','detail');
                         disableFormControlsByIdForInReturn(['d_waterNo','d_card_main_code','d_card_sub_code','d_storage_id','d_area_id','d_in_num','d_card_money',
                         'd_cardMoney1','d_report_date','d_line_id_reclaim','d_station_id_reclaim','d_start_logical_id','d_end_logical_id'],
                         ['d_waterNo','d_card_main_code','d_card_sub_code','d_area_id','d_in_num','d_card_money','d_cardMoney1',
                         'd_line_id_reclaim','d_station_id_reclaim','d_start_logical_id','d_end_logical_id'],
                         ['d_waterNo','d_report_date','d_start_logical_id','d_end_logical_id'],true);"/>
            <c:set var="addAfterMethod" scope="request" value="disableFormControls('detailOp',['d_waterNo','d_start_logical_id','d_end_logical_id','d_billNo','d_cardMoney','d_lineId','d_stationId','d_exitLineId','d_exitStationId','d_mode','d_cardMoney1'],true);"/>
            <c:set var="exportBillAfter" scope="request" value="exportBillDetail('detailOp','w_rpt_ic_rct_bl_in_hr',['p_bill_no'],['queryCondition'],800,500)"/>


            <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all" />
            <!-- <input type="hidden" name="validDays" id="validDays" value="{/Service/Result/validDays}" /> -->
            <input type="hidden" name="validDays" id="validDays" value="${validDays}" />
            <br/>
        </FORM>


        <!-- 状态栏 通用模板 -->

        <c:import url="/jsp/common/common_template.jsp?template_name=common_status_table" />
    </body>
</html>
