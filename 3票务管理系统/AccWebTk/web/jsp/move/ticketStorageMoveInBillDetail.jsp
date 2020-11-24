<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
        <html>
            <head>
                <!--add by mqf 20180609-->
                <meta http-equiv="X-UA-Compatible" content="IE=9,Chrome=1" />
                <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
                <title>迁移入库单明细</title>
                <script language="javascript" type="text/javascript" charset="utf-8" src="js/common_form.js"></script>
                <script language="javascript" type="text/javascript" charset="utf-8" src="js/common_form_extend.js"></script>
                <script language="javascript" type="text/javascript" charset="utf-8" src="js/Validator.js"></script>
                <script language="JavaScript" type="text/javascript" charset="utf-8" src="js/CalendarPop.js"></script>                
                <script language="JavaScript" type="text/javascript" charset="utf-8" src="js/icStorageProductBill.js"></script>
                <script language="JavaScript" type="text/javascript" charset="utf-8" src="js/icProduceOut.js"></script>
                
                <link rel="stylesheet" type="text/css" href="css/frame/simple.css" title="Style"/>
            </head>
            <body onload="initDocument('queryOp', 'detail');
            initDocument('detailOp', 'detail');
            setControlsDefaultValue('queryOp');
            setListViewDefaultValue('detailOp', 'clearStart');
            setQueryControlsDefaultValue('queryOp', 'detailOp');
            setPageControl('detailOp');
            setTableRowBackgroundBlock('DataTable')">
                <!-- 表头 通用模板 -->              
                <table  class="table_title">
                    <tr align="center" class="trTitle">
                        <td colspan="4">迁移入库单明细
                        </td>
                    </tr>
                </table>
                
                 <form method="post" name="queryOp" id="queryOp" action="tkqry">
                    <c:set var="divideShow" scope="request" value="1"/>
                    <c:import  url="/jsp/common/common_template_web_variable.jsp?template_name=common_web_variable" />
                 </form>

                
                  <c:set var="pTitleName" scope="request" value="列表"/>
                  <c:set var="pTitleWidth" scope="request" value="50"/>
                   <c:import  url="/jsp/common/common_template.jsp?template_name=common_table_title" />
                  <div id="clearStartBlock" class="divForTableBlock">
                      <div id="clearStartHead" class="divForTableBlockHead" style="width: 150%">
                        <table class="table_list_block" id="DataTableHead" >
                            <tr  class="table_list_tr_head_block" id="ignore">
                              
                                <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="1"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >流水号</td>
                                <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="2"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >入库原因 </td>
                                <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="3"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width:80px">仓库</td>
                                <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="4"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >票区</td>
                                <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="5"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width:100px">票卡主类型</td>
                                <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="6"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width:100px">票卡子类型</td>
                                <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=true index="7"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >数量</td>
                                <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="8"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width:100px">逻辑卡号起</td>
                                <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="9"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width:100px"> 逻辑卡号止</td>
                                <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="10"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >详细位置</td>
                               <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="11"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >面值</td>
                               <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="12"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >有效期</td>
                               <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="13"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width:100px">盒起号</td>
                               <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="14"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width:100px">盒止号</td>
                               <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="15"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >进站线路</td>
                               <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="16"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >进站车站</td>
                               <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="17"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width:100px">票卡有效天数</td>
                               <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="18"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >出站线路</td>
                               <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="19"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >出站车站</td>
                               <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="20"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width:100px">乘次票限制模式</td>
                               
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
                                      onclick=" controlsByFlag('detailOp', ['del', 'audit']);                      
                                    clickResultRow('detailOp', this, 'detail');
                                    setPageControl('detailOp');" 
                                      id="${rs.waterNo}" >

                                      <td id="waterNo" class="table_list_tr_col_data_block">
                                         ${rs.waterNo}
                                      </td>
                                        <td  id="reasonId" class="table_list_tr_col_data_block" >
                                          ${rs.reasonId}
                                      </td>
                                      <td  id="storageId" class="table_list_tr_col_data_block" style="width:80px">
                                        ${rs.storageId}

                                      </td>
                                        <td  id="areaId" class="table_list_tr_col_data_block" >
                                            ${rs.areaId}
                                        

                                      </td>
                                      <td  id="icMainType" class="table_list_tr_col_data_block" style="width:100px">
                                         ${rs.icMainType}
                                      </td>
                                      <td  id="icSubType" class="table_list_tr_col_data_block" style="width:100px">
                                         ${rs.icSubType}

                                      </td>
                                      <td  id="inNum" class="table_list_tr_col_data_block" >
                                         ${rs.inNum}
                                      </td>
                                      <td  id="startLogicalId" class="table_list_tr_col_data_block" style="width:100px">
                                          ${rs.startLogicalId}
                                      </td>
                                      <td  id="endLogicalId" class="table_list_tr_col_data_block" style="width:100px">
                                          ${rs.endLogicalId}
                                      </td>
                                      <td  id="detailPlace" class="table_list_tr_col_data_block" >
                                          ${rs.detailPlace}
                                      </td>
                                      <td  id="cardMoney" class="table_list_tr_col_data_block" >
                                          ${rs.cardMoney}
                                      </td>
                                      <td  id="validDate" class="table_list_tr_col_data_block" >
                                          ${rs.strValidDate}
                                      </td>
                                     <td  id="startBoxId" class="table_list_tr_col_data_block" style="width:100px">
                                          ${rs.startBoxId}
                                      </td>
                                      <td  id="endBoxId" class="table_list_tr_col_data_block" style="width:100px">
                                          ${rs.endBoxId}
                                      </td>
                                     
                                     <td  id="lineId" class="table_list_tr_col_data_block" >
                                          ${rs.lineId}
                                      </td>
                                      <td  id="stationId" class="table_list_tr_col_data_block" >
                                          ${rs.stationId}
                                      </td>
                                       <td  id="cardAvaDays" class="table_list_tr_col_data_block" style="width:100px">
                                          ${rs.cardAvaDays}
                                      </td>
                                      <td  id="exitLineId" class="table_list_tr_col_data_block" >
                                          ${rs.exitLineId}
                                      </td>
                                       <td  id="exitStationId" class="table_list_tr_col_data_block" >
                                          ${rs.exitStationId}
                                      </td>
                                     
                                     <td  id="model" class="table_list_tr_col_data_block" style="width:100px">
                                          ${rs.model}
                                      </td>
                                     
                                      
                                  </tr>

                              </c:forEach>

                          </table>
                      </div>
                  </div>
                     <c:set var="pTitleName" scope="request" value="明细"/>
        <c:set var="pTitleWidth" scope="request" value="50"/>
        <c:import url="/jsp/common/common_template.jsp?template_name=common_table_title" />

        <FORM method="post" name="detailOp" id="detailOp" action="ticketStorageMove" >

            <c:set var="divideShow" scope="request" value="1"/>
            <c:import  url="/jsp/common/common_template_web_variable.jsp?template_name=common_web_variable" />

            <!--<DIV id="detail" align="center" class="divForTableData" style="position:relative; left:20; width:95%; height:10; layer-background-color:#99CC99; border:thin outset #ffffff; z-index:1;">-->
            <div id="detail"  class="divForTableDataDetail" >
               
              
            </div>






            <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all_init" />
            <c:set var="add" scope="request" value="0"/>
            <c:set var="del" scope="request" value="0"/>
             <c:set var="modify" scope="request" value="0"/>
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
             <c:set var="addAfterMethod" scope="request" value="disableMoveControls('detailOp','d_quantity','d_startBoxId','d_endBoxId','d_select');"/>
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






