<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
        <html>
            <head>
                <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
                <title>空发卡查询</title>
                <script language="javascript" type="text/javascript" charset="utf-8" src="js/common_form.js"></script>
                <script language="javascript" type="text/javascript" charset="utf-8" src="js/common_form_extend.js"></script>
                <script language="javascript" type="text/javascript" charset="utf-8" src="js/Validator.js"></script>
                <script language="JavaScript" type="text/javascript" charset="utf-8" src="js/CalendarPop.js"></script>                
                <script language="JavaScript" type="text/javascript" charset="utf-8" src="js/icStorageProductBill.js"></script>
                <link rel="stylesheet" type="text/css" href="css/frame/simple.css" title="Style"/>
            </head>
            <body onload="initDocument('queryOp', 'detail');
            initDocument('detailOp', 'detail');
            setControlsDefaultValue('queryOp');
            setListViewDefaultValue('detailOp', 'clearStart');
            setQueryControlsDefaultValue('queryOp', 'detailOp');
            setTableRowBackgroundBlock('DataTable')">
                <!-- 表头 通用模板 -->              
                <table  class="table_title">
                    <tr align="center" class="trTitle">
                        <td colspan="4">空发卡查询
                        </td>
                    </tr>
                </table>

                <c:set var="pTitleName" scope="request" value="查询"/>
                <c:set var="pTitleWidth" scope="request" value="50"/>
                <c:import  url="/jsp/common/common_template.jsp?template_name=common_table_title" />

                <form method="post" name="queryOp" id="queryOp" action="ticketStorageQueryMbPduOrder">
                    <c:set var="divideShow" scope="request" value="1"/>
                    <c:import  url="/jsp/common/common_template_web_variable.jsp?template_name=common_web_variable" />
                
                    <table  class="table_edit">
                        <tr class="table_edit_tr">
                            <td class="table_edit_tr_td_label">
                                订单编号
                            </td>
                            <td class="table_edit_tr_td_input">
                                 <input type="text" id="q_order_no"
                                               name="q_order_no"  size="14" maxlength="14"
                                               require="false"   min="14" max="14"
                                               msg="订单编号应为14位"  />                              
                            </td>
                             <td class="table_edit_tr_td_label">
                                起始时间:
                            </td>
                            <td class="table_edit_tr_td_input">
                                 <input type="text"
                                               name="q_beginTime" id="q_beginTime"  size="12"
                                               require="false" dataType="Date" format="ymd"
                                               msg="生成时间格式为(yyyy-mm-dd)!" />
                                        <a href="javascript:openCalenderDialog(document.all.q_beginTime);">
                                            <img src="./images/calendar.gif"
                                                 width="12" height="15" border="0" style="block" />
                                        </a>                              
                            </td>
                             <td class="table_edit_tr_td_label">
                                结束时间:
                            </td>
                            <td class="table_edit_tr_td_input">
                                 <input type="text" name="q_endTime"
                                               id="q_endTime"  size="12" require="false"
                                               dataType="Date|ThanDate" format="ymd" to="q_beginTime"
                                               msg="生成结束时间格式为(yyyy-mm-dd)且大于等于开始时间!" />
                                        <a href="javascript:openCalenderDialog(document.all.q_endTime);">
                                            <img src="./images/calendar.gif"
                                                 width="12" height="15" border="0" style="block" />
                                        </a>                             
                            </td>
                             <td class="table_edit_tr_td_label" rowspan="1">

                                <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all_init" />
                                <c:set var="query" scope="request" value="1"/>
                                <c:set var="addClickMethod" scope="request" value="setControlNames('queryOp','q_billNo#q_operator#q_recordFlag#q_beginTime#q_endTime#q_cardMainCode#q_cardSubCode#q_outReason#q_storage');setLineCardNames('queryOp','','','','q_cardMainCode','q_cardSubCode','commonVariable');"/>
                                <c:set var="clickMethod" scope="request" value="btnClick('queryOp','clearStart','detail');"/>
                                <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all" />
                            </td>
                                                 
                        </tr>
                        
                    </table>
                </FORM>
                  <c:set var="pTitleName" scope="request" value="列表"/>
                  <c:set var="pTitleWidth" scope="request" value="50"/>
                   <c:import  url="/jsp/common/common_template.jsp?template_name=common_table_title" />
                  <div id="clearStartBlock" class="divForTableBlock">
                    <div id="clearStartHead" class="divForTableBlockHead">
                        <table class="table_list_block" id="DataTableHead" >
                            <tr  class="table_list_tr_head_block" id="ignore">
                               
                                <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="0"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 150px">订单编号</td>
                                <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="1"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 90px">领票数量</td>
                                <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="2"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >成品数量</td>
                                <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="3"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 90px">操作类型</td>
                                <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="4"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >结余票数量</td>
                                <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="5"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >废票数量</td>
                                <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=true index="6"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >完成标志</td>
                                <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="7"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 120px">完成时间</td>
                                <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="8"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 130px"> 备注</td>
                                
                               
                            </tr>
                        </table>
                    </div>
                      <div id="clearStart"  class="divForTableBlockData">
                          <table class="table_list_block" id="DataTable" >

                              <c:forEach items="${ResultSet}" var="rs">
                                  <!--class="listTableData" -->
                                  <!--
                                  setSelectValuesByRow('detailOp', 'd_stationID', 'commonVariable');
                                  -->
                                  <tr class="table_list_tr_data"  onMouseOver="overResultRow('detailOp', this);" 
                                      onMouseOut="outResultRow('detailOp', this);" 
                                      onclick=" controlsByFlag('detailOp', ['del', 'audit']);                                 
                                    clickResultRow('detailOp', this, 'detail');
                                    " 
                                      id="${rs.orderNo}" >

                                    
                                      <td  id="orderNo" class="table_list_tr_col_data_block" style="width: 150px">
                                         <a href='#'  onClick="openwindow('ticketStorageQueryMbPduInitInfo?q_order_no=${rs.orderNo}&q_beginTime=${rs.startAchieveTime}&command=qryInit&ModuleID=1502','',900,600)">
                                              ${rs.orderNo}
                                         </a>

                                      </td>
                                        <td  id="drawNum" class="table_list_tr_col_data_block" style="width: 90px">
                                         ${rs.drawNum}

                                      </td>
                                      <td  id="finiPronum" class="table_list_tr_col_data_block">
                                         ${rs.finiPronum}
                                      </td>
                                      <td  id="esWorktypeId" class="table_list_tr_col_data_block" style="width: 90px">
                                         ${rs.esWorktypeId}

                                      </td>
                                      <td  id="surplusNum" class="table_list_tr_col_data_block">
                                         ${rs.surplusNum}
                                      </td>
                                      <td  id="trashyNum" class="table_list_tr_col_data_block">
                                          ${rs.trashyNum}
                                      </td>
                                      <td  id="hdlFlag" class="table_list_tr_col_data_block">
                                          ${rs.hdlFlag}
                                      </td>
                                      <td  id="startAchieveTime" class="table_list_tr_col_data_block" style="width: 120px">
                                          ${rs.startAchieveTime}
                                      </td>
                                      <td  id="orderMemo" class="table_list_tr_col_data_block" style="width: 130px">
                                          ${rs.orderMemo}
                                      </td>
                                    

                                  </tr>

                              </c:forEach>

                          </table>
                      </div>
                  </div>
                  

                  <FORM method="post" name="detailOp" id="detailOp" action="ticketStorageQueryMbPduOrder">

            <c:set var="divideShow" scope="request" value="0"/>
            <c:import  url="/jsp/common/common_template_web_variable.jsp?template_name=common_web_variable" />

            <div id="detail"  class="divForTableDataDetail" ></div>
            <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all_init" />
           
             <c:set var="add" scope="request" value="0"/>
            <c:set var="del" scope="request" value="0"/>
            <c:set var="save" scope="request" value="0"/>
            <c:set var="cancle" scope="request" value="0"/>
            <c:set var="audit" scope="request" value="0"/>
            <c:set var="print" scope="request" value="1"/>
            <c:set var="export" scope="request" value="1"/>
          
          
         <c:set var="clickMethod" scope="request" value="btnClick('detailOp','clearStart','detail','','clearStartHead');"/>
          <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all" />
            <br/>
        </FORM>
      
    <!--
        <c:set var="addQueryMethod" scope="request" value="setLineCardNames('detailOp','d_storage','d_zone','commonVariable','','','');"/>
        <c:set var="addClickMethod" scope="request" value="setUpdatePrimaryKey('detailOp','d_lineID#d_stationID#d_deviceType#d_deviceID#Version');"/>
        -->
        <!-- 状态栏 通用模板 -->

        <c:import url="/jsp/common/common_template.jsp?template_name=common_status_table" />
        
        
             
            </body>
        </html>





