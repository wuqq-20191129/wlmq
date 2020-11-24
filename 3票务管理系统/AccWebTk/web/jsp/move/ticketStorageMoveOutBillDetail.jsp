
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
        <html>
            <head>
                <!--add by mqf 20180609-->
                <meta http-equiv="X-UA-Compatible" content="IE=9,Chrome=1" />
                <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
                <title>迁移出库单明细</title>
                <script language="javascript" type="text/javascript" charset="utf-8" src="js/common_form.js"></script>
                <script language="javascript" type="text/javascript" charset="utf-8" src="js/common_form_extend.js"></script>
                <script language="javascript" type="text/javascript" charset="utf-8" src="js/Validator.js"></script>
                <script language="JavaScript" type="text/javascript" charset="utf-8" src="js/CalendarPop.js"></script>                
                <script language="JavaScript" type="text/javascript" charset="utf-8" src="js/icStorageProductBill.js"></script>
                <link rel="stylesheet" type="text/css" href="css/frame/simple.css" title="Style"/>
            </head>
            <body onload="
            initDocument('detailOp', 'detail');
            setControlsDefaultValue('queryOp');
            setListViewDefaultValue('detailOp', 'clearStart');
            setQueryControlsDefaultValue('queryOp', 'detailOp');
            setPageControl('detailOp');
            setTableRowBackgroundBlock('DataTable')">
                <!-- 表头 通用模板 -->              
                <table  class="table_title">
                    <tr align="center" class="trTitle">
                        <td colspan="4">迁移出库单明细
                        </td>
                    </tr>
                </table>

              
                  <c:set var="pTitleName" scope="request" value="列表"/>
                  <c:set var="pTitleWidth" scope="request" value="50"/>
                   <c:import  url="/jsp/common/common_template.jsp?template_name=common_table_title" />
                  <div id="clearStartBlock" class="divForTableBlock">
                    <div id="clearStartHead" class="divForTableBlockHead" style="width: 150%">
                        <table class="table_list_block" id="DataTableHead" >
                            <tr  class="table_list_tr_head_block" id="ignore">
                               
                                <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="0"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width:100px">出库单号</td>
                                <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="1"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width:100px">票卡主类型</td>
                                <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="2"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width:100px">票卡子类型</td>
                                <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="3"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width:100px">出库原因</td>
                                <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="4"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width:100px">出库数量</td>
                                <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="5"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >仓库</td>
                                <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=true index="6"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >票区</td>
                                <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="7"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >详细位置</td>
                                <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="8"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width:100px"> 票盒起号</td>
                                <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="9"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width:100px"> 票盒止号</td>
                                <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="10"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width:100px"> 逻辑卡号起</td>
                                <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="11"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width:100px"> 逻辑卡号止</td>
                                <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="12"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width:100px"> 有效期</td>
                                <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="13"  sortedby="asc" onclick="sortForTableBlock('clearStart');"  style="width:100px"> 金额(分/次)</td>
                                <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="14"  sortedby="asc" onclick="sortForTableBlock('clearStart');"  style="width:100px"> 乘次票限制模式</td>
                                <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="15"  sortedby="asc" onclick="sortForTableBlock('clearStart');" > 进站线路</td>
                                <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="16"  sortedby="asc" onclick="sortForTableBlock('clearStart');" > 进站车站</td>
                                <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="17"  sortedby="asc" onclick="sortForTableBlock('clearStart');" > 出站线路</td>
                                <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="18"  sortedby="asc" onclick="sortForTableBlock('clearStart');" > 出站车站</td>
                                
                               
                            </tr>
                        </table>
                    </div>
                      <div id="clearStart"  class="divForTableBlockData" style="width: 150%">
                          <table class="table_list_block" id="DataTable" >

                              <c:forEach items="${ResultSet}" var="rs">
                                  <!--class="listTableData" -->
                                  <!--
                                  setSelectValuesByRow('detailOp', 'd_stationID', 'commonVariable');
                                  -->
                                  <tr class="table_list_tr_data"  onMouseOver="overResultRow('detailOp', this);" 
                                      onMouseOut="outResultRow('detailOp', this);" 
                                      onclick="controlsByFlag('detailOp', ['del', 'audit']);                                   
                                    clickResultRow('detailOp', this, 'detail');
                                    setPageControl('detailOp');" 
                                      id="${rs.bill_no}" >

                                    
                                      <td  id="bill_no" class="table_list_tr_col_data_block" style="width:100px">
                                        
                                              ${rs.BILL_NO}
                                    

                                      </td>
                                        <td  id="ic_main_type" class="table_list_tr_col_data_block" style="width:100px">
                                         ${rs.IC_MAIN_TYPE}

                                      </td>
                                      <td  id="ic_sub_type" class="table_list_tr_col_data_block" style="width:100px">
                                        ${rs.IC_SUB_TYPE}
                                      </td>
                                      <td  id="reason_id" class="table_list_tr_col_data_block" style="width:100px">
                                         ${rs.REASON_ID}

                                      </td>
                                      <td  id="section_num" class="table_list_tr_col_data_block" style="width:100px">
                                         ${rs.SECTION_NUM}
                                      </td>
                                      <td  id="storage_id" class="table_list_tr_col_data_block">
                                          ${rs.STORAGE_ID}
                                      </td>
                                      <td  id="area_id" class="table_list_tr_col_data_block">
                                          ${rs.AREA_ID}
                                      </td>
                                      <td  id="detail_place" class="table_list_tr_col_data_block">
                                          ${rs.DETAIL_PLACE}
                                      </td>
                                      <td  id="start_box_id" class="table_list_tr_col_data_block" style="width:100px">
                                          ${rs.START_BOX_ID}
                                      </td>
                                       <td  id="end_box_id" class="table_list_tr_col_data_block" style="width:100px">
                                          ${rs.END_BOX_ID}
                                      </td>
                                      <td  id="start_logical_id" class="table_list_tr_col_data_block" style="width:100px">
                                          ${rs.START_LOGICAL_ID}
                                      </td>
                                      <td  id="end_logical_id" class="table_list_tr_col_data_block" style="width:100px">
                                          ${rs.END_LOGICAL_ID}
                                      </td>
                                       <td  id="valid_date" class="table_list_tr_col_data_block" style="width:100px">
                                          ${rs.VALID_DATE}
                                      </td>
                                      <td  id="card_money" class="table_list_tr_col_data_block" style="width:100px">
                                          ${rs.CARD_MONEY}
                                      </td>
                                       <td  id="model" class="table_list_tr_col_data_block" style="width:100px">
                                          ${rs.MODEL}
                                      </td>
                                      <td  id="line_id" class="table_list_tr_col_data_block" >
                                          ${rs.LINE_ID}
                                      </td>
                                       <td  id="station_id" class="table_list_tr_col_data_block">
                                          ${rs.STATION_ID}
                                      </td>
                                      <td  id="exit_line_id" class="table_list_tr_col_data_block">
                                          ${rs.EXIT_LINE_ID}
                                      </td>
                                      <td  id="exit_station_id" class="table_list_tr_col_data_block" >
                                          ${rs.EXIT_STATION_ID}
                                      </td>
                                     

                                  </tr>

                              </c:forEach>

                          </table>
                      </div>
                  </div>
                  

                  <FORM method="post" name="detailOp" id="detailOp" action="pduOrder">

            <c:set var="divideShow" scope="request" value="1"/>
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
            <c:set var="btNext" scope="request" value="1"/>
            <c:set var="btNextEnd" scope="request" value="1"/>
            <c:set var="btBack" scope="request" value="1"/>
            <c:set var="btBackEnd" scope="request" value="1"/>
          
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






