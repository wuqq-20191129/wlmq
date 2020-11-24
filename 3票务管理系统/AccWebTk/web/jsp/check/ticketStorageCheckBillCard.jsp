<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
        <html>
            <head>
                <!--add by zhongzq 20180609-->
                <meta http-equiv="X-UA-Compatible" content="IE=9,Chrome=1" />
                <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
                <title>盘点明细</title>
                <script language="javascript" type="text/javascript" charset="utf-8" src="js/common_form.js"></script>
                <script language="javascript" type="text/javascript" charset="utf-8" src="js/common_form_extend.js"></script>
                <script language="javascript" type="text/javascript" charset="utf-8" src="js/Validator.js"></script>
                <script language="JavaScript" type="text/javascript" charset="utf-8" src="js/CalendarPop.js"></script>                
                <script language="JavaScript" type="text/javascript" charset="utf-8" src="js/icStorageProductBill.js"></script>
                <link rel="stylesheet" type="text/css" href="css/frame/simple.css" title="Style"/>
            </head>
            <body onload="
            initDocument('detailOp', 'detail');
            
            setListViewDefaultValue('detailOp', 'clearStart');
            
            
            setTableRowBackgroundBlock('DataTable')">
                <!-- 表头 通用模板 -->              
                <table  class="table_title">
                    <tr align="center" class="trTitle">
                        <td colspan="4">盘点明细
                        </td>
                    </tr>
                </table>
                
                  <c:set var="pTitleName" scope="request" value="列表"/>
                  <c:set var="pTitleWidth" scope="request" value="50"/>
                   <c:import  url="/jsp/common/common_template.jsp?template_name=common_table_title" />
                  <div id="clearStartBlock" class="divForTableBlock">
                    <div id="clearStartHead" class="divForTableBlockHead" >
                        <table class="table_list_block" id="DataTableHead" >
                            <tr  class="table_list_tr_head_block" id="ignore">
                                  <td   class="table_list_tr_col_head_block">
                                    <input type="checkbox" name="rectNoAll" id="rectNoAll" onclick="selectAllRecord('detailOp', 'rectNoAll', 'rectNo', 'clearStart', 0);setPageControl('detailOp');"/>
                                </td>	
                                <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="1"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width:150px">盘点单</td>
                                <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="2"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >仓库</td>
                                <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="3"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >库存票区</td>
                                <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="2"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >票卡主类型</td>
                                <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="3"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >票卡子类型</td>
                                <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="4"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width:100px">盒号</td>
                                <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=true index="5"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width:150px">票卡起始号</td>
                                <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="6"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width:150px">票卡结束号</td>
                                
                            </tr>
                        </table>
                    </div>
                      <div id="clearStart"  class="divForTableBlockData" >
                          <table class="table_list_block" id="DataTable" >

                              <c:forEach items="${ResultSet}" var="rs">
                                  <!--class="listTableData" -->
                                  <!--
                                  setSelectValuesByRow('detailOp', 'd_stationID', 'commonVariable');
                                  -->
                                  <tr class="table_list_tr_data"  onMouseOver="overResultRow('detailOp', this);" 
                                      onMouseOut="outResultRow('detailOp', this); "   
                                          onclick="   
                                    clickResultRow('detailOp', this, 'detail');
                                    " 
                                      id="${rs.checkBillNo}#${rs.waterNo}" >
                                      <td id="rectNo1" class="table_list_tr_col_data_block">
                                          <input type="checkbox" name="rectNo" id="rectNo" onclick="unSelectAllRecord('detailOp', 'rectNoAll', 'rectNo');"
                                                 value="${rs.checkBillNo}#${rs.waterNo}"  >

                                          </input>
                                      </td>
                                 
                                      <td  id="checkBillNo" class="table_list_tr_col_data_block" style="width:150px">
                                         ${rs.checkBillNo}

                                      </td>
                                        <td  id="storageId" class="table_list_tr_col_data_block" >
                                         ${rs.storageName}

                                      </td>
                                      <td  id="areaId" class="table_list_tr_col_data_block">
                                         ${rs.areaName}
                                      </td>
                                      <td  id="icMainType" class="table_list_tr_col_data_block" >
                                         ${rs.icMainTypeName}

                                      </td>
                                      <td  id="icSubType" class="table_list_tr_col_data_block">
                                         ${rs.icSubTypeName}
                                      </td>
                                      <td  id="boxId" class="table_list_tr_col_data_block" style="width:100px">
                                          ${rs.boxId}
                                      </td>
                                      <td  id="startLogicalId" class="table_list_tr_col_data_block" style="width:150px">
                                          ${rs.startLogicalId}
                                      </td>
                                      <td  id="endLogicalId" class="table_list_tr_col_data_block" style="width:150px">
                                          ${rs.endLogicalId}
                                      </td>
                                  </tr>

                              </c:forEach>

                          </table>
                      </div>
                  </div>
                     <c:set var="pTitleName" scope="request" value="明细"/>
        <c:set var="pTitleWidth" scope="request" value="50"/>
        <c:import url="/jsp/common/common_template.jsp?template_name=common_table_title" />

        <FORM method="post" name="detailOp" id="detailOp" action="ticketStorageCheckBillCard" >

            <c:set var="divideShow" scope="request" value="1"/>
            <c:import  url="/jsp/common/common_template_web_variable.jsp?template_name=common_web_variable" />

            <!--<DIV id="detail" align="center" class="divForTableData" style="position:relative; left:20; width:95%; height:10; layer-background-color:#99CC99; border:thin outset #ffffff; z-index:1;">-->
            <div id="detail"  class="divForTableDataDetail" >
                <table  class="table_edit_detail">
                    <tr class="table_edit_tr">
                        <td class="table_edit_tr_td_label">
                            盘点单
                        </td>
                        <td class="table_edit_tr_td_input">                            
                            <input type="text" readonly="true" name="d_check_bill" id="d_check_bill" value="${checkBillNo}" />   
                            <input type="hidden" name="checkBillNo" value="${checkBillNo}" />
                        </td>
                         <td class="table_edit_tr_td_label">
                            仓库:
                        </td>
                        <td class="table_edit_tr_td_input">
                            <input type="text" readonly="true" name="d_storage_name" id="d_storage_id" size="10" value="${storageName}" /> 
                            <input type="hidden" name="storageId" value="${storageId}" />
                        </td>

                        <td class="table_edit_tr_td_label">
                            库存票区:
                        </td>
                   
                        <td>
                            <input type="text" readonly="true" name="d_area_name" id="d_area_name"  value="${areaName}" />
                            <input type="hidden" name="areaId" value="${areaId}" />
                        </td>
                         </tr>
                    <tr class="table_edit_tr">
                        <td class="table_edit_tr_td_label">票卡主类型 </td>
                        <td class="table_edit_tr_td_input">
                            <input type="text" readonly="true" name="d_main_type" id="d_main_type"  value="${icMainName}" />
                            <input type="hidden" name="icMainType" value="${icMainType}" />
                        </td>
                   
                        <td class="table_edit_tr_td_label">
                            票卡子类型
                        </td>
                        <td class="table_edit_tr_td_input">
                            <input type="text" readonly="true" name="d_sub_type" id="d_sub_type" value="${icSubName}" />    
                             <input type="hidden" name="icSubType" value="${icSubType}" />
                        </td>
                         <td class="table_edit_tr_td_label">
                            盒号
                        </td>
                        <td class="table_edit_tr_td_input">
                            <input type="text" readonly="true" name="d_box_id" id="d_box_id" value="${boxId}" />     
                            <input type="hidden" name="boxId" value="${boxId}" />
                        </td>
                        </tr>
                        <tr class="table_edit_tr">
                        <td class="table_edit_tr_td_label">
                           起始卡号
                        </td>
<!--                        <td class="table_edit_tr_td_input">                            
                                <input type="text"  require="false"
                                       name="d_startcardNo" id="d_startcardNo" size="16"
                                       dataType="Number|Limit" min="1" max="16"
                                       maxlength="16" msg="起始卡号应为数字" />                          
                        </td>-->
                        <td class="table_edit_tr_td_input">                            
                                <input type="text"  require="false"
                                       name="d_startLogicalId" id="d_startLogicalId" size="20"
                                       dataType="Number|Limit" min="16" max="20"
                                       maxlength="20" msg="起始卡号应为16-20位数字" />                          
                        </td>
                        
                        <td class="table_edit_tr_td_label">
                           结束卡号
                        </td>
<!--                        <td class="table_edit_tr_td_input">
                                <input type="text"  require="false"
                                       name="d_endcardNo" id="d_endcardNo" size="16" maxlength="16"											
                                       dataType="Number|CompareNum|Limit" operator="GreaterThanEqual"
                                       to="d_startcardNo" min="1" max="16" 
                                       msg="结束卡号应为数字且大于等于起始卡号" />
                        </td>-->
                        
                        <td class="table_edit_tr_td_input">
                                <input type="text"  require="false"
                                       name="d_endLogicalId" id="d_endLogicalId" size="20" maxlength="20"											
                                       dataType="Number|CompareNum|Limit" operator="GreaterThanEqual"
                                       to="d_startLogicalId" min="16" max="20" 
                                       msg="结束卡号应为16-20位数字且大于等于起始卡号" />
                        </td>
                       
                    </tr>
                </table>
            </div>



            <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all_init" />
             <c:choose>
            <c:when test="${billRecordFlag != 0 }">
            <c:set var="add" scope="request" value="1"/>
            <c:set var="del" scope="request" value="1"/>
            <c:set var="save" scope="request" value="1"/>
            <c:set var="cancle" scope="request" value="1"/>
             </c:when>
           </c:choose>
           
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




