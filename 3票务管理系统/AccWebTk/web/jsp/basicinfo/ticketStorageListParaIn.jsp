<%-- 
    Document   : ticketStorageListParaIn
    Created on : 2017-8-9, 8:45:03
    Author     : liudezeng
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>库存一览参数</title>


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
            setPrimaryKeys('detailOp', 'd_info_id');
            setListViewDefaultValue('detailOp', 'clearStart');
            setQueryControlsDefaultValue('queryOp', 'detailOp');
            setPageControl('detailOp');
            setTableRowBackgroundBlock('DataTable')">

        <table  class="table_title">
            <tr align="center" class="trTitle">
                <td colspan="4">库存一览站存录入参数
                </td>
            </tr>
        </table>

        <!-- 表头 通用模板 -->

        <c:set var="pTitleName" scope="request" value="查询"/>
        <c:set var="pTitleWidth" scope="request" value="50"/>
        <c:import  url="/jsp/common/common_template.jsp?template_name=common_table_title" />

        <form method="post" name="queryOp" id="queryOp" action="ticketStorageListParaIn">
            <!-- 页面用到的变量 通用模板 -->

            <c:set var="divideShow" scope="request" value="1"/>
            <c:import  url="/jsp/common/common_template_web_variable.jsp?template_name=common_web_variable" />

            <table  class="table_edit">

                <tr class="table_edit_tr">



                    <td class="table_edit_tr_td_label" >票卡主类型:</td>
                    <td class="table_edit_tr_td_input">
                        <select id="q_ic_main_type" name="q_ic_main_type"
                                onChange="setSelectValues('queryOp', 'q_ic_main_type', 'q_ic_sub_type', 'commonVariable');"  >				    
                            <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_maincard" />
                        </select>
                    </td>
                    <td class="table_edit_tr_td_label"><div align="right">票卡子类型:</div></td>
                    <td class="table_edit_tr_td_input">
                        <select id="q_ic_sub_type" name="q_ic_sub_type" >
                            <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_none" />
                        </select>
                        <c:set var="pVarName" scope="request" value="commonVariable"/>
                        <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_mainsubcard" />
                    </td>

                    <td class="table_edit_tr_td_label" width="15%">起始时间:</td>
                    <td class="table_edit_tr_td_input">
                        <input type="text" name="q_input_date_start" id="q_input_date_start" size="12" require="false"  
                               dataType="Date" format="ymd"  msg="开始时间格式不正确"/>
                        <a href="javascript:openCalenderDialog(document.all.q_input_date_start);">
                            <img src="./images/calendar.gif" width="15" height="15" border="0" style="block" />
                        </a>
                    </td>
                </tr>
                <tr class="table_edit_tr">
                    <td class="table_edit_tr_td_label" width="15%">结束时间:</td>
                    <td class="table_edit_tr_td_input" >
                        <input type="text" name="q_input_date_end" id="q_input_date_end" size="12" require="false"  
                               dataType="Date|ThanDate" format="ymd" to="q_input_date_start" msg="结束时间格式必须正确且大于开始时间"/>
                        <a href="javascript:openCalenderDialog(document.all.q_input_date_end);">
                            <img src="./images/calendar.gif" width="15" height="15" border="0" style="block" />
                        </a>
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
                        <c:set var="addClickMethod" scope="request" 
                               value="setControlNames('queryOp','q_ic_main_type#q_ic_sub_type#q_input_date_start#q_input_date_end#q_storage_id');
                               setLineCardNames('queryOp','','','','q_ic_main_type','q_ic_sub_type','commonVariable');"/>
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
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="2"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 120px">票卡主类型</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="3"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 120px">票卡子类型</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="4"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 80px">面值（分）</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="5" sortedby="asc"  onclick="sortForTableBlock('clearStart');" style="width: 200px">时间</td>

                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="6"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 100px">站存量</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="7"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 100px">物资部存量</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="8"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 100px">收益室存量</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="9"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 100px">其他部门</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="10"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 120px">仓库</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="11"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 250px">备注</td>

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
                            onclick=" setSelectValuesByRowPropertyName('detailOp', 'd_ic_sub_type', 'commonVariable', 'icMainType');
                                    clickResultRow('detailOp', this, 'detail');
                                    setPageControl('detailOp');" 
                            id="${rs.info_id}#${rs.ic_main_type}#${rs.ic_sub_type}#${rs.card_money}#${rs.input_date}#${rs.station_dept}
                            #${rs.store_dept}#${rs.income_dept}#${rs.other_dept}#${rs.storage_id}#${rs.remark}"
                            icMainType="${rs.ic_main_type}">

                            <td id="rectNo1" class="table_list_tr_col_data_block">
                                <input type="checkbox" name="rectNo" id="rectNo" onclick="unSelectAllRecord('detailOp', 'rectNoAll', 'rectNo');"
                                       value="${rs.info_id}"  >

                                </input>
                            </td>

                            <td  id="info_id" class="table_list_tr_col_data_block" style="width: 80px">
                                ${rs.info_id}
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
                            <td  id="input_date" class="table_list_tr_col_data_block" style="width: 200px">
                                ${rs.input_date}
                            </td>

                            <td  id="station_dept" class="table_list_tr_col_data_block" style="width: 100px">
                                ${rs.station_dept}
                            </td>
                            <td  id="store_dept" class="table_list_tr_col_data_block" style="width: 100px">
                                ${rs.store_dept}
                            </td>
                            <td  id="income_dept" class="table_list_tr_col_data_block" style="width: 100px">
                                ${rs.income_dept}
                            </td>
                            <td  id="other_dept" class="table_list_tr_col_data_block" style="width: 100px">
                                ${rs.other_dept}
                            </td>
                            <td  id="storage_id" class="table_list_tr_col_data_block" style="width: 120px">
                                ${rs.storage_id_name}
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

        <FORM method="post" name="detailOp" id="detailOp" action="ticketStorageListParaIn" >
            
            <input type="hidden" name="expAllFields" id="d_expAllFields"
                   value="info_id,ic_main_type_name,ic_sub_type_name,card_money,input_date,station_dept,store_dept,income_dept,other_dept,storage_id_name,remark" />
            <input type="hidden" name="expFileName" id="d_expFileName" value="库存一览参数.xlsx" />
            <input type="hidden" name="divId" id="d_divId" value="clearStartHead">
            <input type="hidden" name="methodUrl" id="d_methodUrl" value="/TicketStorageListParaInExportAll"/>

            <c:set var="divideShow" scope="request" value="1"/>
            <c:import  url="/jsp/common/common_template_web_variable.jsp?template_name=common_web_variable" />

            <!--<DIV id="detail" align="center" class="divForTableData" style="position:relative; left:20; width:95%; height:10; layer-background-color:#99CC99; border:thin outset #ffffff; z-index:1;">-->
            <div id="detail"  class="divForTableDataDetail" >
                <table  class="table_edit_detail">
                    <tr class="table_edit_tr">
                        <td class="table_edit_tr_td_label">序号:</td>
                        <td class="table_edit_tr_td_input">
                            <input type="text" name="d_info_id" id="d_info_id" size="10"  readonly="true"/>
                        </td>
                        <td class="table_edit_tr_td_label">票卡主类型: </td>
                        <td class="table_edit_tr_td_input">
                            <select id="d_ic_main_type" name="d_ic_main_type" onChange="setSelectValues('detailOp', 'd_ic_main_type', 'd_ic_sub_type', 'commonVariable');"require="true" dataType="NotEmpty" msg="票卡主类型不能为空!">
                                <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_maincard" />
                            </select>	
                        </td>
                        <td class="table_edit_tr_td_label">票卡子类型: </td>
                        <td class="table_edit_tr_td_input">
                            <select id="d_ic_sub_type" name="d_ic_sub_type" require="true" dataType="LimitB" min="1" msg="票卡子类型不能为空">
                                <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_none" />

                            </select>
                            <c:set var="pVarName" scope="request" value="commonVariable"/>
                            <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_mainsubcard" />
                        </td>
                        <td class="table_edit_tr_td_label">面值(分): </td>
                        <td class="table_edit_tr_td_input">
                            <select id="d_card_money" name="d_card_money" dataType="Integer" msg="面值不能为空!" >
                                <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_cardmoney" />
                            </select>	
                        </td>
                        
                    </tr>
                    <tr class="table_edit_tr">
                        <td class="table_edit_tr_td_label">站存量:</td>
                        <td class="table_edit_tr_td_input">
                            <input type="text" name="d_station_dept" id="d_station_dept" size="10" require="true"maxlength="10"
                                               dataType="integer" msg="站存必须为非负整数!"/>
                        </td>
                        <td class="table_edit_tr_td_label">物资部存量:</td>
                        <td class="table_edit_tr_td_input">
                            <input type="text" name="d_store_dept" id="d_store_dept"  size="10" require="true" maxlength="10"
                                               dataType="integer" msg="库存必须为非负整数!"/>
                        </td>
                        <td class="table_edit_tr_td_label">收益室存量:</td>
                        <td class="table_edit_tr_td_input">
                            <input type="text" name="d_income_dept" id="d_income_dept" size="10" name="income" require="true" maxlength="10"
                                               dataType="integer" msg="收益室存量必须为非负整数!"/>
                        </td>
                        <td class="table_edit_tr_td_label">其他部门:</td>
                        <td class="table_edit_tr_td_input">
                            <input type="text" name="d_other_dept" id="d_other_dept" size="10" name="other" require="true" maxlength="10"
                                               dataType="integer" msg="其它部门库存必须为非负整数!"/>
                        </td>
                    </tr>
                    <tr class="table_edit_tr">
                        <td class="table_edit_tr_td_label">时间:</td>
                        <td class="table_edit_tr_td_input">
                            <input type="text" name="d_input_date" id="d_input_date" size="10" require="true" datatype="Date" format="ymd" msg="时间格式为(yyyy-mm-dd)!"/>
                            <a href="javascript:openCalenderDialogByID('d_input_date','false');">
                                <img src="./images/calendar.gif" width="15" height="15" border="0" style="block"/>
                            </a>
                        </td>
                        <td class="table_edit_tr_td_label">仓库: </td>
                        <td class="table_edit_tr_td_input">
                            <select id="d_storage_id" name="d_storage_id"  require="true" dataType="NotEmpty" msg=仓库不能为空!">
                                <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_storage" />
                            </select>	
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


            <c:set var="clickMethod" scope="request" value="btnClick('detailOp','clearStart','detail','','clearStartHead');disableFormControls('detailOp',['d_info_id'],true);"/>
            <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all" />
            <br/>
        </FORM>
        <!--
        <c:set var="addQueryMethod" scope="request" value="setLineCardNames('detailOp','d_ic_main_type','d_ic_sub_type','commonVariable','','','');"/>
        <c:set var="addClickMethod" scope="request" value="setUpdatePrimaryKey('detailOp','d_info_id');"/>
        -->

        
    </body>
</html>
