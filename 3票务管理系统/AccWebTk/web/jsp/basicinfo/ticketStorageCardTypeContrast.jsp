<%-- 
    Document   : TicketStorageCardTypeContrastManage
    Created on : 2017-7-27, 16:21:14
    Author     : chenzx
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>票卡类型对照表</title>

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
            setPrimaryKeys('detailOp', 'd_icMainType#d_icSubType#d_cardMainId#d_cardSubId#d_baseFlag');
            setListViewDefaultValue('detailOp', 'clearStart');
            setPageControl('detailOp');
            setTableRowBackgroundBlock('DataTable')">

        <!--<body >-->
        <table  class="table_title">
            <tr align="center" class="trTitle">
                <td colspan="4">票卡类型对照表</td>
            </tr>
        </table>
        
        <!-- 表头 通用模板 -->

        <c:set var="pTitleName" scope="request" value="查询"/>
        <c:set var="pTitleWidth" scope="request" value="50"/>
        <c:import  url="/jsp/common/common_template.jsp?template_name=common_table_title" />

        <form method="post" name="queryOp" id="queryOp" action="ticketStorageCardTypeContrastManage">
            <!-- 页面用到的变量 通用模板 -->
            <c:set var="divideShow" scope="request" value="1"/>
            <c:import  url="/jsp/common/common_template_web_variable.jsp?template_name=common_web_variable" />
            <table  class="table_edit">
                <tr class="table_edit_tr">
                    <td class="table_edit_tr_td_label" >票库票卡主类型：</td>
                        <td class="table_edit_tr_td_input">
                            <select id="q_icMainType" name="q_icMainType" onChange="setSelectValues('queryOp', 'q_icMainType', 'q_icSubType', 'commonVariable');" >				    
                                <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_maincard" />
                            </select>
                        </td>
                        <td class="table_edit_tr_td_label">票库票卡子类型：</td>
                        <td class="table_edit_tr_td_input">
                            <select id="q_icSubType" name="q_icSubType">				    
                                <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_none" />
                        </select>
                        <c:set var="pVarName" scope="request" value="commonVariable"/>
                        <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_mainsubcard" />
                        </td>

                    <td class="table_edit_tr_td_label" rowspan="2">
                        <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all_init" />
                        <c:set var="query" scope="request" value="1"/>
                        <c:set var="addClickMethod" scope="request" value="setControlNames('queryOp','q_icMainType#q_icSubType'); setLineCardNames('queryOp','','','','q_icMainType','q_icSubType','commonVariable');"/>
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
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="2"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width:150px;" >票库票卡主类型</td>                        
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="2"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width:150px;" >票库票卡子类型</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="2"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width:150px;" >票卡主类型</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="2"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width:150px;" >票卡子类型</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="2"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width:150px;" >单盒存入数量</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="2"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width:150px;" >基础标志</td>
                    </tr>

                </table>

            </div>

            <div id="clearStart"  class="divForTableBlockData">
                <table class="table_list_block" id="DataTable" >

                    <c:forEach items="${ResultSet}" var="rs">
                        <!--class="listTableData" -->
                        <!--
                        setSelectValuesByRow('detailOp', 'd_stationID', 'commonVariable');
                        setPageControl('detailOp');
                        -->
                        <tr class="table_list_tr_data"  onMouseOver="overResultRow('detailOp', this);" 
                            onMouseOut="outResultRow('detailOp', this);" 
                            onclick="setSelectValuesByRow('detailOp', 'd_icSubType', 'commonVariable');
                                setSelectValuesByRowPropertyName('detailOp', 'd_cardSubId', 'commonVariable1','typeIDs');
                                clickResultRow('detailOp', this, 'detail');" 
                            id="${rs.ic_main_type}#${rs.ic_sub_type}#${rs.ic_sub_typeText}" 
                            typeIDs="${rs.card_main_type}#${rs.card_sub_type}">

                            <td id="rectNo1" class="table_list_tr_col_data_block">
                                <input type="checkbox" name="rectNo" onclick="unSelectAllRecord('detailOp', 'rectNoAll', 'rectNo');"/>
                            </td>
                            <td  id="icMainType" class="table_list_tr_col_data_block" style="width:150px;" >
                                ${rs.ic_main_typeText} 
                            </td>
                            <td  id="icSubType" class="table_list_tr_col_data_block" style="width:150px;">
                                ${rs.ic_sub_typeText}
                            </td>
                            <td  id="cardMainId" class="table_list_tr_col_data_block" style="width:150px;" >
                                ${rs.card_main_idText}
                            </td>
                            <td  id="cardSubId" class="table_list_tr_col_data_block" style="width:150px;" >
                                ${rs.card_sub_idText} 
                            </td>
                            <td  id="boxUnit" class="table_list_tr_col_data_block" style="width:150px;">
                                ${rs.box_unit}
                            </td>
                            <td  id="baseFlag" class="table_list_tr_col_data_block" style="width:150px;">
                                ${rs.base_flag}
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

        <FORM method="post" name="detailOp" id="detailOp" action="ticketStorageCardTypeContrastManage" >

            <c:set var="divideShow" scope="request" value="1"/>
            <c:import  url="/jsp/common/common_template_web_variable.jsp?template_name=common_web_variable" />
            <input type="hidden" name="expAllFields" id="d_expAllFields"
                   value="getIc_main_typeText,getIc_sub_typeText,getCard_main_idText,getCard_sub_idText,getBox_unit,getBase_flag" />
            <input type="hidden" name="expFileName" id="d_expFileName" value="票卡类型对照表.xlsx" />
            <input type="hidden" name="divId" id="d_divId" value="clearStartHead">
            <input type="hidden" name="methodUrl" id="d_methodUrl" value="/TicketStorageCardTypeContrastManageExportAll"/>

            <div id="detail"  class="divForTableDataDetail" >
                <table  class="table_edit_detail">
                    <tr class="table_edit_tr">
                        <td class="table_edit_tr_td_label" >票库票卡主类型：</td>
                        <td class="table_edit_tr_td_input">
                            <select id="d_icMainType" name="d_icMainType" require="true" dataType="NotEmpty"  msg="票库票卡主类型不能为空" onChange="setSelectValues('detailOp', 'd_icMainType', 'd_icSubType', 'commonVariable');" >				    
                                <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_maincard" />
                            </select>
                        </td>
                        <td class="table_edit_tr_td_label">票库票卡子类型：</td>
                        <td class="table_edit_tr_td_input">
                            <select id="d_icSubType" name="d_icSubType" require="true" dataType="NotEmpty"  msg="票库票卡子类型不能为空" >				    
                                <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_none" />
                        </select>
                        <c:set var="pVarName" scope="request" value="commonVariable"/>
                        <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_mainsubcard" />
                        </td>
                        <td class="table_edit_tr_td_label">基础标志:</td>
                        <td class="table_edit_tr_td_input">
                            <select  id="d_baseFlag"  name="d_baseFlag"  require="false" msg="基础标志为1位数字">
                            <option value="1">1</option>
                            <option value="0">0</option> 
                            </select>
                        </td>
                    </tr>
                    <tr class="table_edit_tr">
                        <td class="table_edit_tr_td_label" >票卡主类型:</td>
                        <td class="table_edit_tr_td_input">
                            <select id="d_cardMainId" name="d_cardMainId" require="true" dataType="NotEmpty"  msg="票卡主类型不能为空" onChange="setSelectValues('detailOp', 'd_cardMainId', 'd_cardSubId', 'commonVariable1');" >				    
                                <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_prm_maincard" />
                            </select>
                        </td>
                        <td class="table_edit_tr_td_label">票卡子类型：</td>
                        <td class="table_edit_tr_td_input">
                            <select id="d_cardSubId" name="d_cardSubId" require="true" dataType="NotEmpty"  msg="票卡子类型不能为空" >				    
                                <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_none" />
                        </select>
                        <c:set var="pVarName" scope="request" value="commonVariable1"/>
                        <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_prm_subcard" />
                        </td>
                        <td class="table_edit_tr_td_label">单盒存入数量:</td>
                        <td class="table_edit_tr_td_input">
                            <input type="text" name="d_boxUnit" id="d_boxUnit" size="10" maxLength="30" require="true" dataType="Number|LimitB"  msg="单盒存入数量为正整数" />
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
            <c:set var="print" scope="request" value="1"/>
            <c:set var="export" scope="request" value="1"/>
            <c:set var="expAll" scope="request" value="1"/>
            <c:set var="btNext" scope="request" value="1"/>
            <c:set var="btNextEnd" scope="request" value="1"/>
            <c:set var="btBack" scope="request" value="1"/>
            <c:set var="btBackEnd" scope="request" value="1"/>
            <c:set var="btGoPage" scope="request" value="1"/>
            <c:set var="addClickMethod" scope="request" value="setUpdatePrimaryKey('detailOp','d_icMainType#d_icSubType#d_cardMainId#d_cardSubId#d_baseFlag');"/>
            <c:set var="clickMethod" scope="request" value="btnClick('detailOp','clearStart','detail','','clearStartHead');"/>
            <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all" />
            <br/>
        </FORM>
        
    </body>
</html>
