<%-- 
    Document   : inProduce
    Created on : 2017-7-29
    Author     : mqf
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>生产入库</title>


        <script language="javascript" type="text/javascript" charset="utf-8" src="js/common_form.js"></script>
        <script language="javascript" type="text/javascript" charset="utf-8" src="js/common_form_extend.js"></script>
        <script language="javascript" type="text/javascript" charset="utf-8" src="js/Validator.js"></script>
        <script language="JavaScript" type="text/javascript" charset="utf-8" src="js/CalendarPop.js"></script>
        <script language="JavaScript" type="text/javascript" charset="utf-8" src="js/icProduceOut.js"></script>
        <script language="JavaScript" type="text/javascript" charset="utf-8" src="js/icStorageIn.js"></script>
        <link rel="stylesheet" type="text/css" href="css/frame/simple.css" title="Style"/>
    </head>
    <!-- 
      setInvisable('detailOp', 'clone');
     setPrimaryKeys('detailOp', 'd_lineID#d_stationID#d_deviceID#d_deviceType');
    -->
    <!--
    initDocument('formQuery','detail');
    initDocument('formOp','detail');
    setControlsDefaultValue('formQuery');
    setListViewDefaultValue('formOp','clearStart');
    setQueryControlsDefaultValue('formQuery','formOp');
    setPrimaryKeys('formOp','d_billNo');
    
    <body onload="initDocument('formQuery','detail');
                            initDocument('formOp','detail');
                            setControlsDefaultValue('formQuery');
                            setListViewDefaultValue('formOp','clearStart');
                            setQueryControlsDefaultValue('formQuery','formOp');
                            setPrimaryKeys('formOp','bill_no');">
    -->
    <body onload="initDocument('queryOp', 'detail');
            initDocument('detailOp', 'detail');
            setControlsDefaultValue('queryOp');
            setListViewDefaultValue('detailOp', 'clearStart');
            setQueryControlsDefaultValue('queryOp', 'detailOp');
            setPageControl('detailOp');
            setTableRowBackgroundBlock('DataTable')
            setPrimaryKeys('detailOp','bill_no');">

        <!--<body >-->
        <table  class="table_title">
            <tr align="center" class="trTitle">
                <td colspan="4">生产入库
                </td>
            </tr>
        </table>

        <!-- 表头 通用模板 -->

        <c:set var="pTitleName" scope="request" value="查询"/>
        <c:set var="pTitleWidth" scope="request" value="50"/>
        <c:import  url="/jsp/common/common_template.jsp?template_name=common_table_title" />

        <form method="post" name="queryOp" id="queryOp" action="ticketStorageInProduceManage">
            <!-- 页面用到的变量 通用模板 -->

            <c:set var="divideShow" scope="request" value="1"/>
            <c:import  url="/jsp/common/common_template_web_variable.jsp?template_name=common_web_variable" />

            <table  class="table_edit">
                <tr class="table_edit_tr">
                    <input type="hidden" name="inType" value="SR"  />
                    
                    <td class="table_edit_tr_td_label">入库单号:</td>
                    <td class="table_edit_tr_td_input">
                        <input type="text" id="q_billNo" name="q_billNo" value="" size="12" maxlength="12"  require="false" dataType="LimitB" min="12" max="12"  msg="入库单号应为12位" />
                    </td>
                    <td class="table_edit_tr_td_label">生产单号:</td>
                    <td class="table_edit_tr_td_input">
                        <input type="text" id="q_relatedBillNo" name="q_relatedBillNo" value="" size="9" maxlength="12" require="false" dataType="LimitB" min="1" max="12" msg="生产单号最多为12位" />
                    </td>

                    <td class="table_edit_tr_td_label">开始时间: </td>
                    <td class="table_edit_tr_td_input">
                        <input type="text" name="q_beginTime" id="q_beginTime" value="" size="12" require="false" dataType="Date" format="ymd"  msg="生成时间格式为(yyyy-mm-dd)!" />
                        <a href="javascript:openCalenderDialogByID('q_beginTime','false');">
                            <img src="./images/calendar.gif" width="15" height="15" border="0" style="block"/>
                        </a>
                    </td>
                    <td class="table_edit_tr_td_label">结束时间: </td>
                    <td class="table_edit_tr_td_input">
                        <input type="text" name="q_endTime" id="q_endTime" value="" size="12" require="false"  dataType="Date|ThanDate" format="ymd" to="q_beginTime"
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
                    <td class="table_edit_tr_td_label">入库原因:</td>
                    <td class="table_edit_tr_td_input">
                        <select id="q_inReason" name="q_inReason" >
                            <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_inreason_in_produce" />
                        </select>
                    </td>
                </tr>
                 <tr class="table_edit_tr">
                    <td class="table_edit_tr_td_label">计划单状态:</td>
                    <td class="table_edit_tr_td_input">
                        <select id="q_recordFlag" name="q_recordFlag" >
                            <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_billstatues" />
                        </select>
                    </td>

                    <td class="table_edit_tr_td_label" rowspan="1">

                        <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all_init" />
                        <c:set var="query" scope="request" value="1"/>
                        <c:set var="addClickMethod" scope="request" value="setControlNames('queryOp','q_billNo#q_relatedBillNo#q_beginTime#q_endTime#q_storage#q_cardMainCode#q_cardSubCode#q_inReason#q_recordFlag');setLineCardNames('queryOp','','','','q_cardMainCode','q_cardSubCode','commonVariable');"/>
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
            <div id="clearStartHead" class="divForTableBlockHead" style="width :1200px">
                <table class="table_list_block" id="DataTableHead" >

                    <!--说明：列的序号从0开始 isDigit：false 表示按字符串排序 ：true表示按数值排序 sortedby：asc表示按升序 dec表示按降序 -->
                    <tr  class="table_list_tr_head_block" id="ignore">
                        <td   class="table_list_tr_col_head_block">
                            <input type="checkbox" name="rectNoAll" id="rectNoAll" onclick="selectAllRecord('detailOp', 'rectNoAll', 'rectNo', 'clearStart', 0);
//                                controlByFlag('detailOp',['audit']);
                                controlsByFlagAndMul('detailOp', ['audit']);
                                controlByFlagOper('detailOp',['del']);
                                setPageControl('detailOp');"/>
                        </td>	
                        <!--<td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="1"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 90px">入库明细</td>-->
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="2"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 100px">入库单号</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="3"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 140px">入库日期</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="4"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >制单人</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="5"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >交票人</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="6"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >车管员</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="7"  sortedby="asc" onclick="sortForTableBlock('clearStart');" > 记帐员</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="8"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 100px">生产工单</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="9"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 140px">审核时间</td>
                        
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="10"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >审核人</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="11"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >单据状态</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="12"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 150px">备注</td>
                        
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=true index="13"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >出入差额</td>
                        <!--<a href='#' onClick="openwindow('ticketStorageInProduceDetail?record_flag=0&amp;bill_no=SR2009000000&amp;in_type=DF','',1000,600)">出入差额</a>-->
                        

                    </tr>


                </table>

            </div>
            <!-- <DIV id="clearStart" align="center" style="position:relative; left:20; width:95%; height:30%;min-height: 200px;max-height: 250px; layer-background-color:#99CC99; border:thin outset #ffffff; z-index:1; overflow: scroll;">-->
            <div id="clearStart"  class="divForTableBlockData" style="width :1200px">
                <table class="table_list_block" id="DataTable" >

                    <c:forEach items="${ResultSet}" var="rs">
                        <!--class="listTableData" -->
                        <!--
                        setSelectValuesByRow('detailOp', 'd_stationID', 'commonVariable');
                        -->
                        <tr class="table_list_tr_data"  onMouseOver="overResultRow('detailOp', this);" 
                            onMouseOut="outResultRow('detailOp', this);" 
                            onclick="clickResultRow('detailOp', this, 'detail');
//                                controlByFlag('detailOp',['audit']);
                                controlsByFlagAndMul('detailOp', ['audit']);
                                controlByFlagOper('detailOp',['del']);
                                    setPageControl('detailOp');" 
                            id="${rs.bill_no}" flag="${rs.record_flag}">

                            <td id="rectNo1" class="table_list_tr_col_data_block">
                                <input type="checkbox" name="rectNo" id="rectNo" onclick="unSelectAllRecord('detailOp', 'rectNoAll', 'rectNo');"
                                       value="${rs.bill_no}"  flag="${rs.record_flag}" formaker="${rs.form_maker}">
                                </input>
                            </td>
                            
<!--                            <td  id="billNo" class="table_list_tr_col_data_block" style="width: 90px">
                                <a href='#'
                                    onClick="openwindow('ticketStorageInProduceDetail?billRecordFlag=${rs.record_flag}&queryCondition=${rs.bill_no}&command=query&ModuleID=${ModuleID}','',1000,600)">
                                    入库明细
                                </a>
                            </td>-->
                            
                            
                            <td  id="bill_no" class="table_list_tr_col_data_block" style="width: 100px">
                                <a href='#'
                                    onClick="openwindow('ticketStorageInProduceDetail?billRecordFlag=${rs.record_flag}&queryCondition=${rs.bill_no}&command=query&ModuleID=${ModuleID}','',1000,600)">
                                    ${rs.bill_no}
                                </a>
                            </td>
                            <td  id="bill_date" class="table_list_tr_col_data_block" style="width: 140px">
                                ${rs.bill_date}
                            </td>
                            <td  id="form_maker" class="table_list_tr_col_data_block">
                                ${rs.form_maker}
                            </td>
                            <td  id="hand_man" class="table_list_tr_col_data_block">
                                ${rs.hand_man}
                            </td>
                            <td  id="administer" class="table_list_tr_col_data_block">
                                ${rs.administer}
                            </td>
                            <td  id="accounter" class="table_list_tr_col_data_block">
                                ${rs.accounter}
                            </td>
                            <td  id="related_bill_no" class="table_list_tr_col_data_block" style="width: 100px">
                                ${rs.related_bill_no}
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
                            
                            <td  id="remark" class="table_list_tr_col_data_block" style="width: 150px">
                                ${rs.remark}
                            </td>
                            
                            <td  id="out_in_diff" name="out_in_diff" class="table_list_tr_col_data_block" >
                                <c:if test="${rs.out_in_diff !=0}">
                                    <a href='#' 
                                        onClick="openwindow('ticketStorageInProduceDetail?billRecordFlag=${rs.record_flag}&queryCondition=${rs.bill_no}&in_type=DF&ModuleID=${ModuleID}','',950,600)">
                                        ${rs.out_in_diff}
                                    </a>
                                </c:if>
                            </td>
                            
<!--                            <td  id="operator" class="table_list_tr_col_data_block">
                                
                            </td>-->

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
        <FORM method="post" name="detailOp" id="detailOp" action="ticketStorageInProduceManage" >

            <c:set var="divideShow" scope="request" value="1"/>
            <c:import  url="/jsp/common/common_template_web_variable.jsp?template_name=common_web_variable" />
            
            <input type="hidden" name="inType" value="SR"  />
            <input type="hidden" name="operator" value="${OperatorID}"  />
            
            <!--查询结果对应列，expAllFields的值为其实体类对应get方法，顺序一定要跟页面显示的顺序一致,不然导出列数据会混乱。-->
            <input type="hidden" name="expAllFields" id="d_expAllFields"
                value="getBill_no,getBill_date,getForm_maker,getHand_man,getAdminister,getAccounter,getRelated_bill_no,getVerify_date,getVerify_person,getRecord_flag_name,getRemark,getOut_in_diff" />
            <!--导出文件名字 生成的Excel sheet的名字也是这个-->
            <input type="hidden" name="expFileName" id="d_expFileName" value="生产入库.xlsx" />
            <!--需要导出页面的表数据  一个页面多个导出全部根据这里设置详情参看运营系统退款模块-->
            <input type="hidden" name="divId" id="d_divId" value="clearStartHead">
            <!--对应你controller写的方法  一个页面多个导出全部根据这里设置详情参看运营系统退款模块-->
            <input type="hidden" name="methodUrl" id="d_methodUrl" value="/ticketStorageInProduceManageExportAll"/>
            

            <!--<DIV id="detail" align="center" class="divForTableData" style="position:relative; left:20; width:95%; height:10; layer-background-color:#99CC99; border:thin outset #ffffff; z-index:1;">-->
            <div id="detail"  class="divForTableDataDetail" >
                <table  class="table_edit_detail">
                    <tr class="table_edit_tr">
                        <td class="table_edit_tr_td_label">生产单号: </td>
                        <td class="table_edit_tr_td_input">
                            <select id="d_related_bill_no" name="d_related_bill_no" dataType="Require" msg="生产单号不能为空!">
                                <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_pudProduceBill" />
                            </select>	
                        </td>
                        <td class="table_edit_tr_td_label">交 票 人:</td>
                        <td class="table_edit_tr_td_input">
                            <input type="text" id="d_hand_man" name="d_hand_man" size="8"   dataType="Required|LimitB" maxlength="8" max="8" msg="交票人不能为空,且长度不超过8个字节"/>
                        </td>
                        
                        <td class="table_edit_tr_td_label">备  注:</td>
                        <td class="table_edit_tr_td_input">
                            <input type="text" name="d_remark" size="58"  maxlength="400" dataType="LimitB" max="400" msg="备注长度不超过400个字节"/>
                        </td>
                        
                        <td class="table_edit_tr_td_label" nowrap="nowrap">车票管理员:</td>
                        <td class="table_edit_tr_td_input">
                            <input type="text" id="d_administer" name="d_administer" size="8"  dataType="Required|LimitB" maxlength="8" max="8" msg="车票管理员不能为空,且长度不超过8个字节"/>
                        </td>
                        

                    </tr>
                    
                    <tr class="table_edit_tr">
                        <td class="table_edit_tr_td_label" nowrap="nowrap">记 帐 员:</td>
                        <td class="table_edit_tr_td_input">
                            <input type="text" id="d_accounter" name="d_accounter" size="8"   dataType="Required|LimitB" maxlength="8" max="8" msg="记账员不能为空,且长度不超过8个字节"/>
                        </td>
                        
                        <td class="table_edit_tr_td_label" nowrap="nowrap">出入差额:</td>
                        <td class="table_edit_tr_td_input">
                            <input type="text" name="d_out_in_diff" size="3"  maxlength="10" readonly="true" />
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
            <c:set var="clickMethod" scope="request" value="auditOutInDiff('detailOp')"/>
            <c:set var="addAfterMethod" scope="request" value="disablePrimaryKeys('detailOp')"/>

            <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all" />
            <br/>
        </FORM>

        
    </body>
</html>
