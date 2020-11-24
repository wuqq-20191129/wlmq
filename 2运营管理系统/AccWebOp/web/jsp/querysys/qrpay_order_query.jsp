<%-- 
    Document   : qrpay_order_query
    Created on : 2019-7-10
    Author     : luck
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>二维码支付订单查询</title>


        <script language="javascript" type="text/javascript" charset="utf-8" src="js/common_form.js"></script>
        <script language="javascript" type="text/javascript" charset="utf-8" src="js/common_form_extend.js"></script>
        <script language="javascript" type="text/javascript" charset="utf-8" src="js/Validator.js"></script>
        <script language="JavaScript" src="js/CalendarPop.js"></script>
        <link rel="stylesheet" type="text/css" href="css/frame/simple.css" title="Style"/>
    </head>

    <body onload="preLoadVal('q_beginTime', 'q_endTime');
            initDocument('queryOp', 'detail');
            initDocument('detailOp', 'detail');
            setControlsDefaultValue('queryOp');
            setPrimaryKeys('detailOp', 'd_orderNo#d_saleTimes#d_orderIp#d_statusString');
            setQueryControlsDefaultValue('queryOp', 'detailOp');
            setListViewDefaultValue('detailOp', 'clearStart');
            setPageControl('detailOp');
            setTableRowBackgroundBlock('DataTable')">
        <table  class="table_title">
            <tr align="center" class="trTitle">
                <td colspan="4">二维码支付订单查询
                </td>
            </tr>
        </table>

        <!-- 表头 通用模板 -->
        <c:set var="pTitleName" scope="request" value="查询"/>
        <c:set var="pTitleWidth" scope="request" value="50"/>
        <c:import  url="/jsp/common/common_template.jsp?template_name=common_table_title" />

        <form method="post" name="queryOp" id="queryOp" action="QrpayQrderQuery">
            <!-- 页面用到的变量 通用模板 -->

            <c:set var="divideShow" scope="request" value="1"/>
            <c:import  url="/jsp/common/common_template_web_variable.jsp?template_name=common_web_variable" />

            <table  class="table_edit">
                <tr class="table_edit_tr">


                    <td class="table_edit_tr_td_label">订单号:</td>
                    <td class="table_edit_tr_td_input">
                        <input type="text" name="q_orderNo" id="q_orderNo" size="16" minlength="14" maxlength="14"  require="false" dataType="Integer|LimitB" max="14" min="14" msg="订单号为14位数字" />
                    </td>

                    <td class="table_edit_tr_td_label" >订单生成开始日期:</td>
                    <td class="table_edit_tr_td_input">
                        <input type="text" name="q_beginTime" id="q_beginTime" size="10"  dataType="ICCSDate"  msg="开始日期不为空,格式为(yyyy-mm-dd)!" />
                        <a href="javascript:openCalenderDialogByID('q_beginTime','false');">
                            <img src="./images/calendar.gif" width="15" height="15" border="0" style="block"/>
                        </a>
                    </td>
                    <td class="table_edit_tr_td_label" >订单生成结束日期:</td>
                    <td class="table_edit_tr_td_input">
                        <input type="text" name="q_endTime" id="q_endTime" size="10"  dataType="ICCSDate|ThanDate" to="q_beginTime"  msg="结束日期不为空且必须大于开始日期,格式为(yyyy-mm-dd)!" />
                        <a href="javascript:openCalenderDialogByID('q_endTime','false');">
                            <img src="./images/calendar.gif" width="15" height="15" border="0" style="block"/>
                        </a>
                    </td>
                </tr>
                <tr class="table_edit_tr">
                    <td class="table_edit_tr_td_label"  >订单支付状态:</td>
                    <td class="table_edit_tr_td_input">
                        <select name="q_status" id="q_status">
                            <option value="">=请选择=</option>
                            <option value="0">未支付</option>
                            <option value="1">已支付</option>
                            <option value="4">订单取消</option>
                            <option value="5">订单支付失败</option>
                            <option value="6">订单已退款</option>                        
                        </select>
                    </td>
                    <td class="table_edit_tr_td_label" >二维码信息:</td>
                    <td class="table_edit_tr_td_input">
                        <input type="text" name="q_qrpayData" id="q_qrpayData" size="34" maxlength="34" require="false" dataType="proofID|LimitB" max="34" msg="二维码信息为数字与'-'，最大34位" />
                    </td>
                    
                    <td class="table_edit_tr_td_label"  >是否为异常订单:</td>
                    <td class="table_edit_tr_td_input">
                        <select name="q_unusual" id="q_unusual">
                            <option value="">=请选择=</option>
                            <option value="0">异常订单</option>
                          
                                                    
                        </select>
                    </td>

                    <td class="table_edit_tr_td_label" rowspan="2"  >

                        <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all_init" />
                        <c:set var="query" scope="request" value="1"/>
                        <c:set var="addClickMethod" scope="request" value="setControlNames('queryOp','q_orderNo#q_beginTime#q_endTime#q_status#q_qrpayData#q_unusual');"/>
                        <c:set var="clickMethod" scope="request" value="btnClick('queryOp','clearStart','detail');"/>
                        <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all" />
                    </td>
                </tr>
            </table>
            <c:import url="/jsp/common/common_template.jsp?template_name=version_eval" />

        </form>

        <!-- 状态栏 通用模板 -->

        <c:import url="/jsp/common/common_template.jsp?template_name=common_status_table" />    

        <!-- 表头 通用模板 -->
        <c:set var="pTitleName" scope="request" value="列表"/>
        <c:set var="pTitleWidth" scope="request" value="50"/>
        <c:import url="/jsp/common/common_template.jsp?template_name=common_table_title" />
        <div id="clearStartBlock" class="divForTableBlock">
            <div id="clearStartHead" class="divForTableBlockHead" style="width:260%;">
                <table class="table_list_block" id="DataTableHead" >

                    <!--说明：列的序号从0开始 isDigit：false 表示按字符串排序 ：true表示按数值排序 sortedby：asc表示按升序 dec表示按降序 -->
                    <tr  class="table_list_tr_head_block" id="ignore">
                        <td   class="table_list_tr_col_head_block">
                            <input type="checkbox" name="rectNoAll" id="rectNoAll" 
                                   onclick="selectAllRecord('detailOp', 'rectNoAll', 'rectNo', 'clearStart', 0);
                                           controlsByFlag('detailOp', ['modify']);
                                           controlsDisableForMulRecords('detailOp', ['modify']);
                                           controlsByFlagAndMul('detailOp', ['audit']); 
                                           setPageControl('detailOp');"/>
                        </td>	
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=true index="0" style="width: 100px" sortedby="asc" onclick="sortForTableBlock('clearStart');" >流水号</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=true index="1" style="width: 100px" sortedby="asc" onclick="sortForTableBlock('clearStart');" >订单号</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=true index="2" style="width: 120px" sortedby="asc" onclick="sortForTableBlock('clearStart');" >出票单程票票价(分)</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=true index="3" style="width: 120px" sortedby="asc" onclick="sortForTableBlock('clearStart');" >出票售单程票数量</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=true index="4" style="width: 120px" sortedby="asc" onclick="sortForTableBlock('clearStart');" >出票售单程票总价(分)</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="5" style="width: 120px" sortedby="asc" onclick="sortForTableBlock('clearStart');" >订单支付状态</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="6" style="width: 120px" sortedby="asc" onclick="sortForTableBlock('clearStart');" >订单生成时间</td>

                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="7" style="width: 220px" sortedby="asc" onclick="sortForTableBlock('clearStart');" >支付二维码信息</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=true index="8" style="width: 120px" sortedby="asc" onclick="sortForTableBlock('clearStart');" >支付手机号</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="9" style="width: 120px" sortedby="asc" onclick="sortForTableBlock('clearStart');" >支付时间</td>

                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=true index="10" style="width: 120px" sortedby="asc" onclick="sortForTableBlock('clearStart');" >发售单程票单价(分)</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=true index="11" style="width: 120px" sortedby="asc" onclick="sortForTableBlock('clearStart');" >发售单程票数量</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=true index="12" style="width: 120px" sortedby="asc" onclick="sortForTableBlock('clearStart');" >发售单程票总价(分)</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=true index="13" style="width: 120px" sortedby="asc" onclick="sortForTableBlock('clearStart');" >中心处理流水</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="14" style="width: 120px" sortedby="asc" onclick="sortForTableBlock('clearStart');" >生成订单终端IP</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="15" style="width: 120px" sortedby="asc" onclick="sortForTableBlock('clearStart');" >二维码有效期</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=true index="16" style="width: 120px" sortedby="asc" onclick="sortForTableBlock('clearStart');" >退款金额</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="17" style="width: 120px" sortedby="asc" onclick="sortForTableBlock('clearStart');" >更新时间</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="18" style="width: 180px" sortedby="asc" onclick="sortForTableBlock('clearStart');" >备注</td>
                    </tr>


                </table>

            </div>
            <!-- <DIV id="clearStart" align="center" style="position:relative; left:20; width:95%; height:30%;min-height: 200px;max-height: 250px; layer-background-color:#99CC99; border:thin outset #ffffff; z-index:1; overflow: scroll;">-->
            <div id="clearStart"  class="divForTableBlockData" style="width: 260%;">
                <table class="table_list_block" id="DataTable" >

                    <c:forEach items="${ResultSet}" var="rs">
                        <!--class="listTableData" -->
                        <tr class="table_list_tr_data"  onMouseOver="overResultRow('detailOp', this);" 
                            onMouseOut="outResultRow('detailOp', this);" 
                            onclick="clickResultRow('detailOp', this, 'detail');
                                    controlsByFlagAndMul('detailOp', ['audit','modify']);
                                    setPageControl('detailOp');" 
                            id="${rs.waterNo}#${rs.orderNo}#${rs.saleTimes}#${rs.remark}"
                            flag="${rs.statusString}">
                            
                            <td id="rectNo1" class="table_list_tr_col_data_block">
                                <input type="checkbox" name="rectNo" id="rectNo" 
                                       onclick="unSelectAllRecord('detailOp', 'rectNoAll', 'rectNo');
                                               controlsByFlagAndMul('detailOp', ['audit','modify']);
                                               "
                                       value="${rs.orderNo}" flag="${rs.statusString}">
                                </input>
                            </td>
                            
                            <td  id="waterNo" class="table_list_tr_col_data_block" style="width: 100px">
                                ${rs.waterNo}
                            </td>

                            <td  id="orderNo" class="table_list_tr_col_data_block" style="width: 100px">
                                ${rs.orderNo}
                            </td>
                            <td  id="saleFee" class="table_list_tr_col_data_block" style="width: 120px">
                                ${rs.saleFee}
                            </td>
                            <td  id="saleTimes" class="table_list_tr_col_data_block" style="width: 120px">
                                ${rs.saleTimes}
                            </td>
                            <td  id="dealFee" class="table_list_tr_col_data_block" style="width: 120px">
                                ${rs.dealFee}
                            </td>
                            <td  id="statusString" class="table_list_tr_col_data_block" style="width: 120px">
                                ${rs.statusString}
                            </td>
                            <td  id="orderDate" class="table_list_tr_col_data_block" style="width: 120px">
                                ${rs.orderDate}
                            </td>
                          
                            <td  id="qrpayData" class="table_list_tr_col_data_block" style="width: 220px">
                                ${rs.qrpayData}
                            </td>
                            <td  id="phoneNo" class="table_list_tr_col_data_block" style="width: 120px">
                                ${rs.phoneNo}
                            </td>
                            <td  id="payDate" class="table_list_tr_col_data_block" style="width: 120px">
                                ${rs.payDate}
                            </td>
                            
                           
                            <td  id="saleFeeTotal" class="table_list_tr_col_data_block" style="width: 120px">
                                ${rs.saleFeeTotal}
                            </td>
                            <td  id="saleTimesTotal" class="table_list_tr_col_data_block" style="width: 120px">
                                ${rs.saleTimesTotal}
                            </td>
                            <td  id="dealFeeTotal" class="table_list_tr_col_data_block" style="width: 120px">
                                ${rs.dealFeeTotal}
                            </td>
                            <td  id="accSeq" class="table_list_tr_col_data_block" style="width: 120px">
                                ${rs.accSeq}
                            </td>
                            <td  id="orderIp" class="table_list_tr_col_data_block" style="width: 120px">
                                ${rs.orderIp}
                            </td>
                         
                            <td  id="validTime" class="table_list_tr_col_data_block" style="width: 120px">
                                ${rs.validTime}
                            </td>
                            <td  id="refundFee" class="table_list_tr_col_data_block" style="width: 120px">
                                ${rs.refundFee}
                            </td>
                            <td  id="updateTime" class="table_list_tr_col_data_block" style="width: 120px">
                                ${rs.updateTime}
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


        <FORM method="post" name="detailOp" id="detailOp" action="QrpayQrderQuery" >

            <c:set var="divideShow" scope="request" value="1"/>
            <c:import  url="/jsp/common/common_template_web_variable.jsp?template_name=common_web_variable" />

            <!--导出全部参数 -->

            <input type="hidden" name="expAllFields" id="d_expAllFields" value="getWaterNo,getOrderNo,getSaleFee,getSaleTimes,getDealFee,getStatusString,getOrderDate,getQrpayData,getPhoneNo,getPayDate,getSaleFeeTotal,getSaleTimesTotal,getDealFeeTotal,getAccSeq,getOrderIp,getValidTime,getRefundFee,getUpdateTime,getRemark" />
            <input type="hidden" name="expFileName" id="d_expFileName" value="二维码支付订单查询.xlsx" />
            <input type="hidden" name="divId" id="d_divId" value="clearStartHead" />
            <input type="hidden" name="methodUrl" id="d_methodUrl" value="/QrpayOrderQueryExportAll" />
            
            <div id="detail" name="detail" class="divForTableDataDetail" >
                <table  class="table_edit_detail">
                    <tr class="table_edit_tr">
                        <td class="table_edit_tr_td_label">单号:</td>
                        <td class="table_edit_tr_td_input">
                            <input type="text" name="d_orderNo" id="d_orderNo" size="20"  require="false" readOnly="true" />	
                        </td>
                        <td class="table_edit_tr_td_label">生成订单终端IP:</td>
                        <td class="table_edit_tr_td_input">
                            <input type="text" name="d_orderIp" id="d_orderIp" size="10" maxlength="6" require="false" readOnly="true"/>
                        </td>
                        
                        <td class="table_edit_tr_td_label">出票售单程票数量:</td>
                        <td class="table_edit_tr_td_input">
                            <input type="text" name="d_saleTimes" id="d_saleTimes" size="10" maxlength="6" require="false" readOnly="true"/>
                        </td>
  
                    </tr>
                    <tr class="table_edit_tr">
                      
                        <td class="table_edit_tr_td_label">订单支付状态:</td>
                       <td class="table_edit_tr_td_input">
                        
                        <select name="d_statusString" id="d_statusString">
                            <option value="">=请选择=</option>
                            <option value="0">未支付</option>
                            <option value="1">已支付</option>
                            <option value="4">订单取消</option>
                            <option value="5">订单支付失败</option>
                            <option value="6">订单已退款</option>                        
                        </select>
                </td>
                        
                        <td class="table_edit_tr_td_label">备注:</td>
                        <td class="table_edit_tr_td_input" colspan="3">
                            
                            <select name="d_remark" id="d_remark">
                            <option value="">=请选择=</option>
                            <option value="0">itm未出票，手工处理为取消订单</option>
                                                   
                        </select>
                        </td>
                       
                    </tr>
                    
                    
                        
                   
                </table>
            </div>

            <c:import url="/jsp/common/common_template.jsp?template_name=version_eval" />

            <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all_init" />

            <c:set var="modify" scope="request" value="1"/>
            <c:set var="audit" scope="request" value="1"/>
            <c:set var="save" scope="request" value="1"/>
            <c:set var="cancle" scope="request" value="1"/>
            <c:set var="print" scope="request" value="1"/>
            <c:set var="export" scope="request" value="1"/>
            <c:set var="expAll" scope="request" value="1" />
            <c:set var="btNext" scope="request" value="1"/>
            <c:set var="btNextEnd" scope="request" value="1"/>
            <c:set var="btBack" scope="request" value="1"/>
            <c:set var="btBackEnd" scope="request" value="1"/>
            <c:set var="btGoPage" scope="request" value="1"/>


            <c:set var="clickMethod" scope="request" value="btnClick('detailOp','clearStart','detail','','clearStartHead');"/>
            <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all" />
            <br/>
        </FORM>


    </body>



</html>
