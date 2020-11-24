<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
        <html>
            <head>
                <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
                <title>票卡查询</title>
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
                        <td colspan="4">票卡查询
                        </td>
                    </tr>
                </table>

                <c:set var="pTitleName" scope="request" value="查询"/>
                <c:set var="pTitleWidth" scope="request" value="50"/>
                <c:import  url="/jsp/common/common_template.jsp?template_name=common_table_title" />

                <form method="post" name="queryOp" id="queryOp" action="ticketStorageQueryTicket">
                    <c:set var="divideShow" scope="request" value="1"/>
                    <c:import  url="/jsp/common/common_template_web_variable.jsp?template_name=common_web_variable" />
                
                    <table  class="table_edit">
                        <tr class="table_edit_tr">
                            <td class="table_edit_tr_td_label">
                                卡号
                            </td>
                            <td class="table_edit_tr_td_input">
                                <input type="text" id="q_logicalNo"
                                       name="q_logicalNo" value="" size="30" maxlength="20"
                                       require="true" dataType="LimitB|Number" min="16" max="20"
                                       msg="卡号应为16-20位数字" />                                
                            </td>
                             <td class="table_edit_tr_td_label" rowspan="1">

                                <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all_init" />
                                <c:set var="query" scope="request" value="1"/>
                                <c:set var="addClickMethod" scope="request" value="setControlNames('queryOp','q_logicalNo');"/>
                                <c:set var="clickMethod" scope="request" value="btnClick('queryOp','clearStart','detail');"/>
                                <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all" />
                            </td>
                                                 
                        </tr>
                        
                    </table>
                </FORM>
                            
                <!-- 状态栏 通用模板 -->
                <c:import url="/jsp/common/common_template.jsp?template_name=common_status_table" />
        
                  <c:set var="pTitleName" scope="request" value="列表"/>
                  <c:set var="pTitleWidth" scope="request" value="50"/>
                   <c:import  url="/jsp/common/common_template.jsp?template_name=common_table_title" />
                  <div id="clearStartBlock" class="divForTableBlock">
                    <div id="clearStartHead" class="divForTableBlockHead">
                        <table class="table_list_block" id="DataTableHead" >
                            <tr  class="table_list_tr_head_block" id="ignore">
                               
                                <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="0"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 150px">卡号</td>
                                <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="1"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 90px">出入库单或订单</td>
                                <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="2"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >状态</td>
                                <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="3"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 90px">仓库</td>
                                <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="4"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >库存票区</td>
                                <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="5"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >详细位置</td>
                                <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=true index="6"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >盒号</td>
                                <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="7"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >操作员</td>
                                <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="8"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 130px"> 审核员</td>
                                <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="9"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 130px">审核或生产时间</td>
                               
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
                                      onclick="controlsByFlag('detailOp', ['del', 'audit']);                                   
                                    clickResultRow('detailOp', this, 'detail');
                                    " 
                                      id="${rs.billNo}" >

                                    
                                      <td  id="logicalId" class="table_list_tr_col_data_block" style="width: 150px">
                                        
                                              ${rs.logicalId}
                                          

                                      </td>
                                        <td  id="billNo" class="table_list_tr_col_data_block" style="width: 90px">
                                         ${rs.billNo}

                                      </td>
                                      <td  id="reasonId" class="table_list_tr_col_data_block">
                                         ${rs.reasonIdText}
                                      </td>
                                      <td  id="storageId" class="table_list_tr_col_data_block" style="width: 90px">
                                         ${rs.storageIdText}

                                      </td>
                                      <td  id="areaId" class="table_list_tr_col_data_block">
                                         ${rs.areaIdText}
                                      </td>
                                      <td  id="detailPlace" class="table_list_tr_col_data_block">
                                          ${rs.detailPlace}
                                      </td>
                                      <td  id="boxRange" class="table_list_tr_col_data_block">
                                          ${rs.boxRange}
                                      </td>
                                      <td  id="formMaker" class="table_list_tr_col_data_block">
                                          ${rs.formMaker}
                                      </td>
                                      <td  id="verifyPerson" class="table_list_tr_col_data_block" style="width: 130px">
                                          ${rs.verifyPerson}
                                      </td>
                                      <td  id="verifyStrDate" class="table_list_tr_col_data_block" style="width: 130px">
                                          ${rs.verifyStrDate}
                                      </td>
                                    

                                  </tr>

                              </c:forEach>

                          </table>
                      </div>
                  </div>
                  

        <FORM method="post" name="detailOp" id="detailOp" action="tkqry">

            <c:set var="divideShow" scope="request" value="0"/>
            <c:import  url="/jsp/common/common_template_web_variable.jsp?template_name=common_web_variable" />
            
            <input type="hidden" name="expAllFields" id="d_expAllFields"
                   value="getLogicalId,getBillNo,getReasonIdText,getStorageIdText,getAreaIdText,getDetailPlace,getBoxRange,getFormMaker,getVerifyPerson,getVerifyStrDate" />
            <input type="hidden" name="expFileName" id="d_expFileName" value="票卡查询.xlsx" />
            <input type="hidden" name="divId" id="d_divId" value="clearStartHead">
            <input type="hidden" name="methodUrl" id="d_methodUrl" value="/ticketStorageQueryTicketExportAll"/>

            <div id="detail"  class="divForTableDataDetail" ></div>
            <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all_init" />
           
             <c:set var="add" scope="request" value="0"/>
            <c:set var="del" scope="request" value="0"/>
            <c:set var="save" scope="request" value="0"/>
            <c:set var="cancle" scope="request" value="0"/>
            <c:set var="audit" scope="request" value="0"/>
            <c:set var="print" scope="request" value="1"/>
            <c:set var="export" scope="request" value="1"/>
            <c:set var="expAll" scope="request" value="1"/>
            
          
         <c:set var="clickMethod" scope="request" value="btnClick('detailOp','clearStart','detail','','clearStartHead');"/>
          <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all" />
            <br/>
        </FORM>
      
    
        <c:set var="addQueryMethod" scope="request" value="setLineCardNames('detailOp','d_storage','d_zone','commonVariable','','','');"/>
        <c:set var="addClickMethod" scope="request" value="setUpdatePrimaryKey('detailOp','d_lineID#d_stationID#d_deviceType#d_deviceID#Version');"/>
        
        
            </body>
        </html>




