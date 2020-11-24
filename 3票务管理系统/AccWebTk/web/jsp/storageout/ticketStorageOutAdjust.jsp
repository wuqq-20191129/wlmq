<%-- 
    Document   : outAdjust
    Created on : 2017-9-8
    Author     : mqf
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>调账出库</title>


        <script language="javascript" type="text/javascript" charset="utf-8" src="js/common_form.js"></script>
        <script language="javascript" type="text/javascript" charset="utf-8" src="js/common_form_extend.js"></script>
        <script language="javascript" type="text/javascript" charset="utf-8" src="js/Validator.js"></script>
        <script language="JavaScript" type="text/javascript" charset="utf-8" src="js/CalendarPop.js"></script>
        <script language="JavaScript" type="text/javascript" charset="utf-8" src="js/icProduceOut.js"></script>
        <script language="JavaScript" type="text/javascript" charset="utf-8" src="js/icStorageIn.js"></script>
        
        <script language="JavaScript" type="text/javascript" charset="utf-8" src="js/icStorageProductBill.js"></script>
        <script language="javascript" type="text/javascript">
                function openWindowDialogByDisabled(formName) {
                    var administer = document.forms[formName].getElementsByTagName("input")['d_administer'];
                    if (administer.disabled == true) {
                        return; 
                    } else {
                        openWindowDialog('detailOp','d_related_bill_no','ticketStorageOutAdjustManage?queryCondition=${rs.related_bill_no}&command=query&operType=checkOutSelect&ModuleID=${ModuleID}',900,600);
                    }
                }
        </script>
        <link rel="stylesheet" type="text/css" href="css/frame/simple.css" title="Style"/>
    </head>

<!-- onload="initDocument('formQuery','detail');initDocument('formOp','detail');
                setControlsDefaultValue('formQuery');
                setListViewDefaultValue('formOp','clearStart');
                setQueryControlsDefaultValue('formQuery','formOp');
                setPrimaryKeys('formOp','d_checkBillNo#d_adjustId');">-->
    <body onload="initDocument('queryOp', 'detail');
            initDocument('detailOp', 'detail');
            setControlsDefaultValue('queryOp');
            setListViewDefaultValue('detailOp', 'clearStart');
            setQueryControlsDefaultValue('queryOp', 'detailOp');
            setPageControl('detailOp');
            setTableRowBackgroundBlock('DataTable')
//            setPrimaryKeys('detailOp','d_related_bill_no#d_reason_id');
            setPrimaryKeys('detailOp','d_checkBillNo#d_adjust_id');
                ">
        <!--<body >-->
        <table  class="table_title">
            <tr align="center" class="trTitle">
                <td colspan="4">调账出库
                </td>
            </tr>
        </table>

        <!-- 表头 通用模板 -->

        <c:set var="pTitleName" scope="request" value="查询"/>
        <c:set var="pTitleWidth" scope="request" value="50"/>
        <c:import  url="/jsp/common/common_template.jsp?template_name=common_table_title" />

        <form method="post" name="queryOp" id="queryOp" action="ticketStorageOutAdjustManage">
            <!-- 页面用到的变量 通用模板 -->

            <c:set var="divideShow" scope="request" value="1"/>
            <c:import  url="/jsp/common/common_template_web_variable.jsp?template_name=common_web_variable" />

            <table  class="table_edit">
                <tr class="table_edit_tr">
                    <input type="hidden" name="operTypeValue" value="outBill"  />
                    
                    <td class="table_edit_tr_td_label">出库单号:</td>
                    <td class="table_edit_tr_td_input">
                        <input type="text" id="q_billNo" name="q_billNo" value="" size="12" maxlength="12"  require="false" dataType="LimitB" min="12" max="12"  msg="出库单号应为12位" />
                    </td>
<!--                    <td class="table_edit_tr_td_label">盘点单号:</td>
                    <td class="table_edit_tr_td_input">
                        <input type="text" id="q_relatedBillNo" name="q_relatedBillNo" value="" size="9" maxlength="12" require="false" dataType="LimitB" min="1" max="12" msg="盘点单号最多为12位" />
                    </td>-->
                    
                    <td class="table_edit_tr_td_label">盘点人:</td>
                    <td class="table_edit_tr_td_input">
                        <input type="text" id="q_administer" name="q_administer" value="" size="12" maxlength="8" require="false" dataType="LimitB" min="1" max="8" msg="盘点人最大长度为8个字节" />
                    </td>

                    <td class="table_edit_tr_td_label">开始时间: </td>
                    <td class="table_edit_tr_td_input">
                        <input type="text" name="q_beginTime" id="q_beginTime" value="" size="10" require="false" dataType="Date" format="ymd"  msg="生成时间格式为(yyyy-mm-dd)!" />
                        <a href="javascript:openCalenderDialogByID('q_beginTime','false');">
                            <img src="./images/calendar.gif" width="15" height="15" border="0" style="block"/>
                        </a>
                    </td>
                    <td class="table_edit_tr_td_label">结束时间: </td>
                    <td class="table_edit_tr_td_input">
                        <input type="text" name="q_endTime" id="q_endTime" value="" size="10" require="false"  dataType="Date|ThanDate" format="ymd" to="q_beginTime"
                               msg="生成结束时间格式为(yyyy-mm-dd)且大于等于开始时间!" />
                        <a href="javascript:openCalenderDialogByID('q_endTime','false');">
                            <img src="./images/calendar.gif" width="15" height="15" border="0" style="block"/>
                        </a>
                    </td>
                </tr>
                 <tr class="table_edit_tr">
                    <td class="table_edit_tr_td_label">仓库:</td>
                    <td class="table_edit_tr_td_input">
                        <select id="q_storage" name="q_storage" >
                            <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_storage" />
                        </select>
                    </td>
               
               
                    <td class="table_edit_tr_td_label" >票卡主类型:</td>
                    <td class="table_edit_tr_td_input">
                        <select id="q_cardMainCode" name="q_cardMainCode" onChange="setSelectValues('queryOp', 'q_cardMainCode', 'q_cardSubCode', 'commonVariable');"  >				    
                            <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_maincard" />
                        </select>
                    </td>
                    <td class="table_edit_tr_td_label"><div align="right">票卡子类型:</div></td>
                    <td class="table_edit_tr_td_input">
                        <select id="q_cardSubCode" name="q_cardSubCode" >
                            <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_none" />
                        </select>
                        <c:set var="pVarName" scope="request" value="commonVariable"/>
                        <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_mainsubcard" />
                    </td>
<!--                    <td class="table_edit_tr_td_label">出库原因:</td>
                    <td class="table_edit_tr_td_input">
                        <select id="q_outReason" name="q_outReason" >
                            <%--<c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_outreason_produce" />--%>
                        </select>
                    </td>-->
<!--                </tr>
                 <tr class="table_edit_tr">-->
                    <td class="table_edit_tr_td_label">单据状态:</td>
                    <td class="table_edit_tr_td_input">
                        <select id="q_recordFlag" name="q_recordFlag" >
                            <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_billstatues" />
                        </select>
                    </td>

                    <td class="table_edit_tr_td_label" rowspan="1">

                        <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all_init" />
                        <c:set var="query" scope="request" value="1"/>
                        <c:set var="addClickMethod" scope="request" value="setControlNames('queryOp','q_billNo#q_administer#q_beginTime#q_endTime#q_storage#q_cardMainCode#q_cardSubCode#q_outReason#q_recordFlag');setLineCardNames('queryOp','','','','q_cardMainCode','q_cardSubCode','commonVariable');"/>
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
            <!--style="width :1300px"-->
            <div id="clearStartHead" class="divForTableBlockHead" >
                <table class="table_list_block" id="DataTableHead" >

                    <!--说明：列的序号从0开始 isDigit：false 表示按字符串排序 ：true表示按数值排序 sortedby：asc表示按升序 dec表示按降序 -->
                    <tr  class="table_list_tr_head_block" id="ignore">
                        <td   class="table_list_tr_col_head_block">
                            <input type="checkbox" name="rectNoAll" id="rectNoAll" onclick="selectAllRecord('detailOp', 'rectNoAll', 'rectNo', 'clearStart', 0);
                                controlsDisableForMulRecords('detailOp', ['del']);
                                controlsByFlagAndMul('detailOp', ['audit']);
                                setPageControl('detailOp');"/>
                        </td>	
<!--                                controlByFlagOper('detailOp',['del']);
                                disableButtonByRecord('query1','del');-->
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="1"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 90px">出库单号</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="2"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 90px">盘点单号</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="3"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 140px">出库日期</td>
                        <!--<td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="4"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >制单人</td>-->
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="5"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >盘点人</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="6"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 140px">审核时间</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="7"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >审核人</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="8"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >单据状态</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="9"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 180px">备注</td>

                    </tr>


                </table>

            </div>
            <!--style="width :1300px"-->
            <div id="clearStart"  class="divForTableBlockData" >
                <table class="table_list_block" id="DataTable" >

                    <c:forEach items="${ResultSet}" var="rs">
                        <!--class="listTableData" -->
                        <!--
                        setSelectValuesByRow('detailOp', 'd_stationID', 'commonVariable');
                        -->
                        <tr class="table_list_tr_data"  onMouseOver="overResultRow('detailOp', this);" 
                            onMouseOut="outResultRow('detailOp', this);" 
                            onclick="clickResultRow('detailOp', this, 'detail');
                                controlsDisableForMulRecords('detailOp', ['del']);
                                controlsByFlagAndMul('detailOp', ['audit']);
                                setPageControl('detailOp');" 
                            id="${rs.bill_no}" flag="${rs.record_flag}">

                            <td id="rectNo1" class="table_list_tr_col_data_block">
                                <input type="checkbox" name="rectNo" id="rectNo" onclick="unSelectAllRecord('detailOp', 'rectNoAll', 'rectNo');"
                                       value="${rs.bill_no}"  flag="${rs.record_flag}">
                                </input>
<!--                                            controlsByFlag('formOp',['del','audit']);-->
                            </td>
                            
                            <td  id="billNo" class="table_list_tr_col_data_block" style="width: 90px">
                                <a href='#'
                                    onClick="openwindow('ticketStorageOutAdjustManage?billRecordFlag=${rs.record_flag}&queryCondition=${rs.bill_no}&command=query&operType=outBillDetail&ModuleID=${ModuleID}','',1000,600)">
                                    ${rs.bill_no}
                                </a>
                            </td>
                            
                            
                            <td  id="related_bill_no" class="table_list_tr_col_data_block" style="width: 90px">
                                <a href='#'
                                    onClick="openwindow('ticketStorageOutAdjustManage?queryCondition=${rs.related_bill_no}&command=query&operType=checkOut&ModuleID=${ModuleID}','',1000,600)">
                                    ${rs.related_bill_no}
                                </a>
                            </td>
                            <td  id="bill_date" class="table_list_tr_col_data_block" style="width: 140px">
                                ${rs.bill_date}
                            </td>
                            <td  id="administer" class="table_list_tr_col_data_block">
                                ${rs.administer}
                            </td>
                            <td  id="verify_date" class="table_list_tr_col_data_block" style="width: 140px">
                                ${rs.verify_date}
                            </td>
                            
                            <td  id="verify_person" class="table_list_tr_col_data_block">
                                ${rs.verify_person}
                            </td>
                            <td  id="record_flag" class="table_list_tr_col_data_block">
                                ${rs.record_flag_name}
                            </td>
                            
                            <td  id="remark" class="table_list_tr_col_data_block" style="width: 180px">
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

        <!--onSubmit="return Validator.Validate(this,2);"-->
        <FORM method="post" name="detailOp" id="detailOp" action="ticketStorageOutAdjustManage" >

            <!--<input type="hidden" name="operTypeValue" value="outBill"  />-->
            <c:set var="OperType" scope="request" value="outBill"/>
            
            <c:set var="divideShow" scope="request" value="1"/>
            <c:import  url="/jsp/common/common_template_web_variable.jsp?template_name=common_web_variable" />
            
            <c:import  url="/jsp/common/common_template_web_variable_ic.jsp?template_name=common_web_variable_ic" />
            
            <!--查询结果对应列，expAllFields的值为其实体类对应get方法，顺序一定要跟页面显示的顺序一致,不然导出列数据会混乱。-->
            <input type="hidden" name="expAllFields" id="d_expAllFields"
                value="getBill_no,getRelated_bill_no,getBill_date,getAdminister,getVerify_date,getVerify_person,getRecord_flag_name,getRemark" />
            <!--导出文件名字 生成的Excel sheet的名字也是这个-->
            <input type="hidden" name="expFileName" id="d_expFileName" value="调账出库.xlsx" />
            <!--需要导出页面的表数据  一个页面多个导出全部根据这里设置详情参看运营系统退款模块-->
            <input type="hidden" name="divId" id="d_divId" value="clearStartHead">
            <!--对应你controller写的方法  一个页面多个导出全部根据这里设置详情参看运营系统退款模块-->
            <input type="hidden" name="methodUrl" id="d_methodUrl" value="/ticketStorageOutAdjustManageExportAll"/>
            

            <!--<DIV id="detail" align="center" class="divForTableData" style="position:relative; left:20; width:95%; height:10; layer-background-color:#99CC99; border:thin outset #ffffff; z-index:1;">-->
            <div id="detail"  class="divForTableDataDetail" >
                <table  class="table_edit_detail">
                    <tr class="table_edit_tr">
                        <td class="table_edit_tr_td_label">调账原因: </td>
                        <td class="table_edit_tr_td_input">
                            <select id="d_adjust_id" name="d_adjust_id" dataType="Require" msg="调账原因不能为空!" value="22">
                                <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_outreason_outAdjust" />
                            </select>	
                        </td>
                        <td class="table_edit_tr_td_label">盘点人:</td>
                        <td class="table_edit_tr_td_input">
                            <input type="text" id="d_administer" name="d_administer" size="15"   dataType="Required|LimitContainChinese" maxlength="45" min="1" max="45" msg="盘点人不能为空且最大长度为45个字节，一个中文是3个字节(最大15个中文)"/>
                        </td>
                        
                        <td class="table_edit_tr_td_label">备  注:</td>
                        <td class="table_edit_tr_td_input">
                            <input type="text" name="d_remark" require="false" dataType="NotEmpty|LimitContainChinese" size="58"  maxlength="256" min="1"  max="256" msg="备注最大长度为256个字节，一个中文是3个字节(最大85个中文)"/>
                        </td>
                        
                    </tr>  
                    <tr class="table_edit_tr">   
                        <td class="table_edit_tr_td_label">调帐内容:</td>
                        <td class="table_edit_tr_td_input" colspan="5">
                            <input type="text" id="d_related_bill_no" name="d_related_bill_no" size="104"  dataType="Required|NotEmpty" msg="调账内容不能为空！"/>
                            <img src="./images/calendar.gif" width="15" height="15" border="0" style="block" 
                                onClick="openWindowDialogByDisabled('detailOp');"/> 
                        </td>

                    </tr>
                    

                </table>
            </div>






            <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all_init" />
            <c:set var="add" scope="request" value="1"/>
            <c:set var="del" scope="request" value="1"/>
            <c:set var="save" scope="request" value="1"/>
            <c:set var="cancle" scope="request" value="1"/>
            <c:set var="audit" scope="request" value="1"/>
            <c:set var="print" scope="request" value="1"/>
            <c:set var="expAll" scope="request" value="1" />
            <c:set var="export" scope="request" value="1"/>
            <c:set var="quit" scope="request" value="1"/>
            <c:set var="btNext" scope="request" value="1"/>
            <c:set var="btNextEnd" scope="request" value="1"/>
            <c:set var="btBack" scope="request" value="1"/>
            <c:set var="btBackEnd" scope="request" value="1"/>
            
            <%--<c:set var="addformQueryMethod" scope="request" value="setLineCardNames('detailOp','d_storage','d_zone','commonVariable1','card_main_code','card_sub_code','commonVariable');"/>--%>
            <c:set var="addClickMethod" scope="request" value="setUpdatePrimaryKey('detailOp','d_billNo');enablePrimaryKeys('detailOp')"/>
            <c:set var="clickMethod" scope="request" value="btnClick('detailOp','clearStart','detail')"/>
            <c:set var="addAfterMethod" scope="request" value="disablePrimaryKeys('detailOp')"/>

            <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all" />
            <br/>
        </FORM>

        
    </body>
</html>
