<%-- 
    Document   : TicketStorageChestDefManage
    Created on : 2017-7-26
    Author     : xiaowu
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>票柜定义</title>
        <script language="javascript" type="text/javascript" charset="utf-8" src="js/common_form.js"></script>
        <script language="javascript" type="text/javascript" charset="utf-8" src="js/common_form_extend.js"></script>
        <script language="javascript" type="text/javascript" charset="utf-8" src="js/Validator.js"></script>
        <script language="JavaScript" type="text/javascript" charset="utf-8" src="js/CalendarPop.js"></script>
        <script language="JavaScript" type="text/javascript" charset="utf-8" src="js/icProduceOut.js"></script>
        <script language="JavaScript" type="text/javascript" charset="utf-8" src="js/icStorageProductBill.js"></script>
        <link rel="stylesheet" type="text/css" href="css/frame/simple.css" title="Style"/>
        <script language="javascript" type="text/javascript">
            function setCardMoney(formName,element1,element2){
                var ob = document.forms[formName].elements[element1];
                var op = document.forms[formName].elements[element2];
                if (ob == null || ob=='') return;
                if (ob.value =='01') op.value=0;
                if (ob.value =='02') {
                    op.value=0;
                    document.getElementById("d_card_money").disabled = true;
//                    $("#d_card_money").
                }  //编码区   面值只能为 0
                if (ob.value =='03') {
//                    op.value=0;
                    document.getElementById("d_card_money").disabled = false;
//                    $("#d_card_money").
                }  //赋值区   面值为自然数
            }
        </script>
    </head>

    <body onload="initDocument('queryOp', 'detail');
            initDocument('detailOp', 'detail');
            setPrimaryKeys('detailOp','d_chest_name#d_chest_id#d_storage_id#d_area_id');
            setControlsDefaultValue('queryOp');
            setListViewDefaultValue('detailOp', 'clearStart');
            setQueryControlsDefaultValue('queryOp', 'detailOp');
            setPageControl('detailOp');
            setTableRowBackgroundBlock('DataTable')">

        <!--<body >-->
        <table  class="table_title">
            <tr align="center" class="trTitle">
                <td colspan="4">票柜定义
                </td>
            </tr>
        </table>

        <!-- 表头 通用模板 -->

        <c:set var="pTitleName" scope="request" value="查询"/>
        <c:set var="pTitleWidth" scope="request" value="50"/>
        <c:import  url="/jsp/common/common_template.jsp?template_name=common_table_title" />

        <form method="post" name="queryOp" id="queryOp" action="ticketStorageChestDefManage" style="margin-bottom: 3px;">
            <!-- 页面用到的变量 通用模板 -->

            <c:set var="divideShow" scope="request" value="1"/>
            <c:import  url="/jsp/common/common_template_web_variable.jsp?template_name=common_web_variable" />

            <table  class="table_edit">
                <tr class="table_edit_tr">
                    <td class="table_edit_tr_td_label">票柜代码:</td>
                        <td class="table_edit_tr_td_input">
                            <input type="text" name="q_chest_id" id="q_chest_id" size="3"  maxlength="3"  require="false" dataType="Integer" msg="票柜代码应为3位数字" />
                        </td>
                    <td class="table_edit_tr_td_label" >票卡主类型:</td>
                    <td class="table_edit_tr_td_input">
                        <select id="q_ic_main_type" name="q_ic_main_type" onChange="setSelectValuesNoSelDefault('queryOp', 'q_ic_main_type', 'q_ic_sub_type', 'commonVariable');"  >				    
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
                    <td class="table_edit_tr_td_label">仓库:</td>
                    <td class="table_edit_tr_td_input">
                        <select id="q_storage_id" name="q_storage_id" >
                            <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_storage" />
                        </select>
                    </td>
                    <td class="table_edit_tr_td_label">票区:</td>
                    <td class="table_edit_tr_td_input">
                        <select id="q_area_id" name ="q_area_id">
                            <option value="">请选择票区</option>
                            <option value="02">编码区</option>
                            <option value="03">赋值区</option>
                        </select>
                    </td>
                    
                    <td class="table_edit_tr_td_label" rowspan="1">
                        <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all_init" />
                        <c:set var="query" scope="request" value="1"/>
                        <c:set var="addClickMethod" scope="request" value="setControlNames('queryOp','q_ic_main_type#q_ic_sub_type#q_storage_id#q_area_id#q_chest_id');setLineCardNames('queryOp','','','','q_ic_main_type','q_ic_sub_type','commonVariable');"/>
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
            <div id="clearStartHead" class="divForTableBlockHead">
                <table class="table_list_block" id="DataTableHead" >

                    <!--说明：列的序号从0开始 isDigit：false 表示按字符串排序 ：true表示按数值排序 sortedby：asc表示按升序 dec表示按降序 -->
                    <tr  class="table_list_tr_head_block" id="ignore">

                        <td   class="table_list_tr_col_head_block">
                            <input type="checkbox" name="rectNoAll" id="rectNoAll" onclick="selectAllRecord('detailOp', 'rectNoAll', 'rectNo', 'clearStart', 0);"/>
                        </td>	
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="1"  sortedby="asc" onclick="sortForTableBlock('clearStart');">票柜代码</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="2"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width:140px;">票柜名称</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="3"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width:100px;">票卡主类型</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="4"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width:100px;">票卡子类型</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=true  index="5"  sortedby="asc" onclick="sortForTableBlock('clearStart');">仓库</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="6"  sortedby="asc" onclick="sortForTableBlock('clearStart');">票区</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="7"  sortedby="asc" onclick="sortForTableBlock('clearStart');">层数</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="8"  sortedby="asc" onclick="sortForTableBlock('clearStart');">层托数</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="9"  sortedby="asc" onclick="sortForTableBlock('clearStart');">托最大盒数</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="10" sortedby="asc" onclick="sortForTableBlock('clearStart');">面值(分/次)</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="11" sortedby="asc" onclick="sortForTableBlock('clearStart');">柜明细</td>
                    </tr>
                </table>
            </div>

            <div id="clearStart"  class="divForTableBlockData">
                <table class="table_list_block" id="DataTable" >

                    <c:forEach items="${ResultSet}" var="rs">
                        <tr class="table_list_tr_data"  onMouseOver="overResultRow('detailOp', this);" 
                            onMouseOut="outResultRow('detailOp', this);" 
                            onclick="setSelectValuesByRowPropertyName('detailOp', 'd_ic_sub_type','commonVariable','typeIDs1');clickResultRow('detailOp', this, 'detail');setPageControl('detailOp');" 
                            id="${rs.chest_name}#${rs.chest_id}#${rs.storage_id}#${rs.area_id}" typeIDs1="${rs.ic_main_type}">

                            <td id="rectNo1" class="table_list_tr_col_data_block">
                                <input type="checkbox" name="rectNo" onclick="unSelectAllRecord('detailOp', 'rectNoAll', 'rectNo');"
                                       value="${rs.chest_name}#${rs.chest_id}#${rs.storage_id}#${rs.area_id}">
                                </input>
                            </td>
                            <td  id="chest_id" class="table_list_tr_col_data_block">
                                ${rs.chest_id}
                            </td>
                            <td  id="chest_name" class="table_list_tr_col_data_block" style="width:140px;">
                                ${rs.chest_name}
                            </td>
                            <td  id="ic_main_type" class="table_list_tr_col_data_block" style="width:100px;">
                                ${rs.ic_main_desc}
                            </td>
                            <td  id="ic_sub_type" class="table_list_tr_col_data_block" style="width:100px;">
                                ${rs.ic_sub_desc}
                            </td>
                            <td  id="storage_id" class="table_list_tr_col_data_block">
                                ${rs.storage_name}
                            </td>
                            <td  id="area_id" class="table_list_tr_col_data_block">
                                ${rs.area_name}
                            </td>
                            <td  id="storey_num" class="table_list_tr_col_data_block">
                                ${rs.storey_num}
                            </td>
                            <td  id="base_num" class="table_list_tr_col_data_block">
                                ${rs.base_num}
                            </td>
                            <td  id="max_box_num" class="table_list_tr_col_data_block">
                                ${rs.max_box_num}
                            </td>
                            <td  id="card_money" class="table_list_tr_col_data_block">
                                ${rs.card_money}
                            </td>
                            <td  id="detailPage" class="table_list_tr_col_data_block">
                                <a href='#'
                                   onClick="openwindow('ticketStorageChestDefManage?chestId=${rs.chest_id}&storageId=${rs.storage_id}&areaId=${rs.area_id}&command=detail&ModuleID=${ModuleID}', '', 1300, 600)">
                                    明细
                                </a>
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

        <FORM method="post" name="detailOp" id="detailOp" action="ticketStorageChestDefManage" >

            <c:set var="divideShow" scope="request" value="1"/>
            <c:import  url="/jsp/common/common_template_web_variable.jsp?template_name=common_web_variable" />
            <input type="hidden" name="expAllFields" id="d_expAllFields"
                value="getChest_id,getChest_name,getIc_main_desc,getIc_sub_desc,getStorage_name,getArea_name,getStorey_num,getBase_num,getMax_box_num,getCard_money" />
            <input type="hidden" name="expFileName" id="d_expFileName" value="票柜定义.xlsx" />
            <input type="hidden" name="divId" id="d_divId" value="clearStartHead">
            <input type="hidden" name="methodUrl" id="d_methodUrl" value="/ticketStorageChestDefExportAll"/>

            <div id="detail"  class="divForTableDataDetail" >
                <table  class="table_edit_detail">
                    <tr class="table_edit_tr">
                        <td class="table_edit_tr_td_label">票柜代码:</td>
                        <td class="table_edit_tr_td_input">
                            <input type="text" name="d_chest_id" id="d_chest_id" maxlength="3"  size="3"  require="true" dataType="Custom" regexp="(^\d\d\d$)" msg="票柜代码必须为3位"/>
                        </td>
                        <td class="table_edit_tr_td_label">票柜名称:</td>
                        <td class="table_edit_tr_td_input">
                            <input type="text" name="d_chest_name" id="d_chest_name" size="10"  maxlength="10" />
                        </td>
                         <td class="table_edit_tr_td_label">票卡主类型:</td>
                        <td class="table_edit_tr_td_input">
                            <select id="d_ic_main_type" name="d_ic_main_type" dataType="Require" msg="票卡主类型不能为空!" onChange="setSelectValuesNoSelDefault('detailOp', 'd_ic_main_type', 'd_ic_sub_type', 'commonVariable');"  >				    
                                <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_maincard" />
                            </select>	
                        </td>
                         <td class="table_edit_tr_td_label">票卡子类型:</td>
                        <td class="table_edit_tr_td_input">
                            <select id="d_ic_sub_type" name="d_ic_sub_type" dataType="Require" msg="票卡子类型不能为空!">
                                <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_none" />
                            </select>
                            <c:set var="pVarName" scope="request" value="commonVariable"/>
                            <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_mainsubcard" />	
                        </td>
                    </tr>
                    <tr class="table_edit_tr">
                        <td class="table_edit_tr_td_label">仓库:</td>
                        <td class="table_edit_tr_td_input">
                            <select id="d_storage_id" name="d_storage_id" dataType="Require" msg="仓库不能为空!">
                                <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_storage" />
                            </select>	
                        </td>
                        <td class="table_edit_tr_td_label">票区:</td>
                        <td class="table_edit_tr_td_input">
                            <select id="d_area_id" name ="d_area_id" dataType="Require" msg="票区不能为空" require="true" onChange="setCardMoney('detailOp','d_area_id','d_card_money');"> 
                                <option value="">=请选择=</option>
                                <option value="02">编码区</option>
                                <option value="03">赋值区</option> 
                            </select>	
                        </td>
                        <td class="table_edit_tr_td_label">层数:</td>
                        <td class="table_edit_tr_td_input">
                            <input type="text" name="d_storey_num" id="d_storey_num" size="2"  maxlength="2" require="true" dataType="Integer|Positive" msg="层数必须为正整数"/>
                        </td>
                        <td class="table_edit_tr_td_label">层托数:</td>
                        <td class="table_edit_tr_td_input">
                            <input type="text" name="d_base_num" id="d_base_num" size="2" maxlength="2" require="true" dataType="Integer|Positive" msg="托数必须为正整数"/>
                        </td>
                    </tr>
                    <tr class="table_edit_tr">
                        <td class="table_edit_tr_td_label">托最大盒数:</td>
                        <td class="table_edit_tr_td_input">
                            <input type="text" name="d_max_box_num" id="d_max_box_num" size="4"  maxlength="4" require="true" dataType="Integer|Positive" msg="最大盒数必须为正整数"/>
                        </td>  
                        <td class="table_edit_tr_td_label">面值(分/次):</td>
                        <td class="table_edit_tr_td_input">
                            <input type="text" name="d_card_money" id="d_card_money" size="5"  maxlength="5" require="true" dataType="integer" msg="面值必须为自然数，编码区的面值只能为0" value="0"/>
                        </td>
                        <td class="table_edit_tr_td_label"></td>
                        <td class="table_edit_tr_td_input"></td>
                        <td class="table_edit_tr_td_label"></td>
                        <td class="table_edit_tr_td_input"> </td>
                    </tr>
                </table>
            </div>

            <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all_init" />
            <c:set var="add" scope="request" value="1"/>
            <c:set var="del" scope="request" value="1"/>
            <c:set var="modify" scope="request" value="1"/>
            <c:set var="save" scope="request" value="1"/>
            <c:set var="cancle" scope="request" value="1"/>
            <c:set var="print" scope="request" value="1"/>
            <c:set var="export" scope="request" value="1"/>
            <c:set var="btNext" scope="request" value="1"/>
            <c:set var="btNextEnd" scope="request" value="1"/>
            <c:set var="btBack" scope="request" value="1"/>
            <c:set var="btBackEnd" scope="request" value="1"/>
            <c:set var="btGoPage" scope="request" value="1"/>
            <c:set var="expAll" scope="request" value="1" />
            <c:set var="addClickMethod" scope="request" value="setUpdatePrimaryKey('detailOp','d_chest_name#d_chest_id#d_storage_id#d_area_id');"/>
            <c:set var="addAfterMethod" scope="request" value="setCardMoney('detailOp','d_area_id','d_card_money');"/>
            <c:set var="addAfterClickModifyMethod" scope="request" value="setCardMoney('detailOp','d_area_id','d_card_money');"/>
            <c:set var="clickMethod" scope="request" value="btnClick('detailOp','clearStart','detail','','clearStartHead');"/>
            <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all" />
            <br/>
        </FORM>
    </body>
</html>
