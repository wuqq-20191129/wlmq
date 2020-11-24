<%-- 
    Document   : TicketStorageInCleanInBillDetial
    Created on : 2017-7-31
    Author     : luck
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="X-UA-Compatible" content="IE=9,Chrome=1" />
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>清洗入库明细</title>


        <script language="javascript" type="text/javascript" charset="utf-8" src="js/common_form.js"></script>
        <script language="javascript" type="text/javascript" charset="utf-8" src="js/common_form_extend.js"></script>
        <script language="javascript" type="text/javascript" charset="utf-8" src="js/Validator.js"></script>
        <script language="JavaScript" type="text/javascript" charset="utf-8" src="js/CalendarPop.js"></script>
        <script language="JavaScript" type="text/javascript" charset="utf-8" src="js/icProduceOut.js"></script>
        <script language="JavaScript" type="text/javascript" charset="utf-8" src="js/icStorageProductBill.js"></script>
        <script language="JavaScript" type="text/javascript" charset="utf-8" src="js/icStorageIn.js"></script>
        <link rel="stylesheet" type="text/css" href="css/frame/simple.css" title="Style"/>
    </head>

    <style type="text/css">
        .NewButtonStyle{
            font-size: 9pt;
            color:#3399FF;
            background-color:#FFFFFF;
            text-align:center;
        }
        .DefaultButtonStyle{
            font-size: 9pt;
            color:#ACA899;
            text-align:center;
        }
    </style>
    <script  type="text/javascript">
        function initAddAndDel() {
            var flag = document.getElementById('flg').value;
            if (flag == 'true') {
                var recordFlag = document.getElementById('recordFlag').value;
                if (recordFlag != 0) {
                    document.getElementById('btAdd').disabled = true;
                    document.getElementById('btDelete').disabled = false;
                    document.getElementById('btDelete').className = 'NewButtonStyle';
                    document.getElementById('btAdd').className = 'DefaultButtonStyle';
                }
            }
        }
    </script>

    <!-- preLoadIdVal('d_validDate', 0); setPageControl('detailOp'); -->
    <body onload="
            initDocument('detailOp', 'detail');
            setListViewDefaultValue('detailOp', 'clearStart');

            setDetailBillNo('detailOp', 'd_billNo', 'queryCondition');
            controlByRecordFlagForInit('detailOp', ['add'], true);
            initAddAndDel();

            setTableRowBackgroundBlock('DataTable')">

        <!--<body >-->
        <table  class="table_title">
            <tr align="center" class="trTitle">
                <td colspan="4">清洗入库明细
                </td>
            </tr>
        </table>



        <!-- 表头 通用模板 -->
        <c:set var="pTitleName" scope="request" value="列表"/>
        <c:set var="pTitleWidth" scope="request" value="50"/>
        <c:import url="/jsp/common/common_template.jsp?template_name=common_table_title" />
        <div id="clearStartBlock" class="divForTableBlock">
            <div id="clearStartHead" class="divForTableBlockHead" >
                <table class="table_list_block" id="DataTableHead" >

                    <!--说明：列的序号从0开始 isDigit：false 表示按字符串排序 ：true表示按数值排序 sortedby：asc表示按升序 dec表示按降序 -->
                    <tr  class="table_list_tr_head_block" id="ignore">
                        <td   class="table_list_tr_col_head_block"  style=" display: none">
                            <input type="checkbox" name="rectNoAll" id="rectNoAll" checked="checked" style=" display: none"/>
                        </td>	

                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="1"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >仓库</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="2"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width:80px" >票区</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="3"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >票卡主类型</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="4"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >票卡子类型</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=true  index="5"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >数量</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="6"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >票卡类型</td>

                    </tr>


                </table>

            </div>
            <input id="flg" value="${delFlag}" type="hidden"/>
            <div id="clearStart"  class="divForTableBlockData" >
                <table class="table_list_block" id="DataTable" >

                    <c:forEach items="${ResultSet}" var="rs">

                        <tr class="table_list_tr_data"  onMouseOver="overResultRow('detailOp', this);" 
                            onMouseOut="outResultRow('detailOp', this);"                        
                            id="${rs.billNo}" flag="${rs.record_flag}" cardMainCode="${rs.icMainType}" 
                            storageId="${rs.storageId}" >
                            <td id="rectNo1" class="table_list_tr_col_data_block"  style=" display: none">
                                <input type="checkbox" name="rectNo" id="rectNo" 
                                       value="${rs.billNo}"  flag="${rs.record_flag}" checked="checked" style=" display: none">

                                </input>
                            </td>
                        <input type="hidden" value="${rs.record_flag}" id="recordFlag"/>
                        <td  id="storageId" class="table_list_tr_col_data_block" >
                            ${rs.storageName}
                        </td>
                        <td  id="areaId" class="table_list_tr_col_data_block" style="width:80px">
                            ${rs.areaName}
                        </td>
                        <td  id="icMainType" class="table_list_tr_col_data_block">
                            ${rs.icMainTypeName}
                        </td>
                        <td  id="icSubType" class="table_list_tr_col_data_block">
                            ${rs.icSubTypeName}
                        </td>
                        <td  id="inNum" class="table_list_tr_col_data_block">
                            ${rs.inNum}
                        </td>
                        <td  id="cardType" class="table_list_tr_col_data_block">
                            ${rs.cardType}
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

        <form method="post" name="detailOp" id="detailOp" action="ticketStorageInCleanManage" >

            <c:set var="divideShow" scope="request" value="0"/>
            <c:import  url="/jsp/common/common_template_web_variable.jsp?template_name=common_web_variable" />
            <c:import  url="/jsp/common/common_template_web_variable_ic.jsp?template_name=common_web_variable_ic" />

            <!--<DIV id="detail" align="center" class="divForTableData" style="position:relative; left:20; width:95%; height:10; layer-background-color:#99CC99; border:thin outset #ffffff; z-index:1;">-->
            <div id="detail"  class="divForTableDataDetail" >
                <table  class="table_edit_detail">
                    <tr class="table_edit_tr">

                    <input type="hidden" name="d_billNo" id="d_billNo" size="12" maxlength="12" />

                    <td class="table_edit_tr_td_label">有效票: </td>
                    <td class="table_edit_tr_td_input">
                        <input type="text" name="d_valid_num" id="d_valid_num" size="18"  maxlength="18" dataType="integer|Limit" required="true" value="0"  msg="有效票数量为正整数"/>
                    </td>
                    <td class="table_edit_tr_td_label">废  票: </td>
                    <td class="table_edit_tr_td_input">
                        <input type="text" name="d_man_useless_num" id="d_man_useless_num" size="8"  maxlength="9" dataType="integer|Limit" required="true" value="0" msg="人工废票数量为正整数"/>
                    </td>
                    <td class="table_edit_tr_td_label">未完成: </td>
                    <td class="table_edit_tr_td_input">
                        <input type="text" name="d_real_balance" id="d_real_balance" size="8"  maxlength="9" dataType="integer|Limit" required="true" value="0"   msg="注销少票数量为正整数"/>
                    </td>

                    <td class="table_edit_tr_td_label">全部入库标志:</td>
                    <td class="table_edit_tr_td_input">
                        <select id="d_inFlag" name="d_inFlag" id="d_inFlag">                              
                            <option value="0">未完成</option>
                            <option value="1">已完成</option>
                        </select>
                        <c:set var="pVarName" scope="request" value="commonVariable4"/>
                        <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_maincard_serial" />
                    </td>
                    </tr>

                </table>
            </div>






            <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all_init" />
            <c:set var="add" scope="request" value="1"/>
            <c:set var="del" scope="request" value="1"/>
            <c:set var="save" scope="request" value="1"/>
            <c:set var="cancle" scope="request" value="1"/>
            <c:set var="exportBill" scope="request" value="1"/>



            <c:set var="addformQueryMethod" scope="request" value="setLineCardNames('detailOp','','','','card_main_code','card_sub_code','commonVariable');"/>
            <c:set var="addClickMethod" scope="request" value="setUpdatePrimaryKey('detailOp','d_billNo');"/>
            <c:set var="clickMethod" scope="request" value="addCheckNum('detailOp',['d_valid_num','d_man_useless_num','d_real_balance'])"/>
            <c:set var="addAfterMethod" scope="request" value="disableFormControls('detailOp',['d_billNo'],true);"/>
            <c:set var="exportBillAfter" scope="request" value="exportBillDetail('detailOp','w_rpt_ic_rct_bl_in_qr',['p_bill_no'],['queryCondition'],800,500)"/>


            <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all" />
            <!-- <input type="hidden" name="validDays" id="validDays" value="{/Service/Result/validDays}" /> -->
            <input type="hidden" name="validDays" id="validDays" value="${validDays}" />
            <br/>
        </FORM>


        <!-- 状态栏 通用模板 -->

        <c:import url="/jsp/common/common_template.jsp?template_name=common_status_table" />
    </body>
</html>

