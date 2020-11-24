<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
        <html>
            <head>
                <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
                <title>票库查询 未审核单据</title>
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
                        <td colspan="4">票库查询 未审核单据
                        </td>
                    </tr>
                </table>
                
                <!-- 状态栏 通用模板 -->
                <c:import url="/jsp/common/common_template.jsp?template_name=common_status_table" />

                  <c:set var="pTitleName" scope="request" value="列表"/>
                  <c:set var="pTitleWidth" scope="request" value="50"/>
                   <c:import  url="/jsp/common/common_template.jsp?template_name=common_table_title" />
                  <div id="clearStartBlock" class="divForTableBlock">
                    <div id="clearStartHead" class="divForTableBlockHead">
                        <table class="table_list_block" id="DataTableHead" >
                            <tr  class="table_list_tr_head_block" id="ignore">
                               
                                <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="0"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 150px">单号</td>
                                <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="1"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 150px">名称</td>
                                <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="2"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 150px">生成时间</td>
                                <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="3"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 150px">状态</td>
                                <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="4"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 150px">仓库</td>
                               
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
                                    setPageControl('detailOp');" 
                                    id="${rs.billNo}" >

                                      <td  id="billNo" class="table_list_tr_col_data_block" style="width: 150px">
                                          ${rs.billNo}

                                      </td>
                                      <td  id="billName" class="table_list_tr_col_data_block" style="width: 150px">
                                          ${rs.billName}
                                      </td>
                                      <td  id="strBillDate" class="table_list_tr_col_data_block" style="width: 150px">
                                          ${rs.strBillDate}

                                      </td>
                                      <td  id="recordFlag" class="table_list_tr_col_data_block" style="width: 150px">
                                          ${rs.recordFlag}
                                      </td>
                                      <td  id="storageId" class="table_list_tr_col_data_block" style="width: 150px">
                                          ${rs.storageId}
                                      </td>
                                    

                                  </tr>

                              </c:forEach>

                          </table>
                      </div>
                  </div>
                  

        <FORM method="post" name="detailOp" id="detailOp" action="tkqry">

            <c:set var="divideShow" scope="request" value="1"/>
            <c:import  url="/jsp/common/common_template_web_variable.jsp?template_name=common_web_variable" />
            
            <input type="hidden" name="expAllFields" id="d_expAllFields"
                   value="getBillNo,getBillName,getStrBillDate,getRecordFlag,getStorageId" />
            <input type="hidden" name="expFileName" id="d_expFileName" value="未审核单据.xlsx" />
            <input type="hidden" name="divId" id="d_divId" value="clearStartHead">
            <input type="hidden" name="methodUrl" id="d_methodUrl" value="/ticketStorageQueryUncheckedBillExportAll"/>

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
            <c:set var="btNext" scope="request" value="0"/>
            <c:set var="btNextEnd" scope="request" value="0"/>
            <c:set var="btBack" scope="request" value="0"/>
            <c:set var="btBackEnd" scope="request" value="0"/>
          
         <c:set var="clickMethod" scope="request" value="btnClick('detailOp','clearStart','detail','','clearStartHead');"/>
          <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all" />
            <br/>
        </FORM>
      
    <!--
        <c:set var="addQueryMethod" scope="request" value="setLineCardNames('detailOp','d_storage','d_zone','commonVariable','','','');"/>
        <c:set var="addClickMethod" scope="request" value="setUpdatePrimaryKey('detailOp','d_lineID#d_stationID#d_deviceType#d_deviceID#Version');"/>
        -->
            </body>
        </html>





