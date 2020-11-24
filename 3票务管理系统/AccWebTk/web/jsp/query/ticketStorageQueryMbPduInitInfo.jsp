
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
        <html>
            <head>
                <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
                <title>空发制票明细查询</title>
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
                        <td colspan="4">空发制票明细查询
                        </td>
                    </tr>
                </table>

              
                  <c:set var="pTitleName" scope="request" value="列表"/>
                  <c:set var="pTitleWidth" scope="request" value="50"/>
                   <c:import  url="/jsp/common/common_template.jsp?template_name=common_table_title" />
                  <div id="clearStartBlock" class="divForTableBlock">
                    <div id="clearStartHead" class="divForTableBlockHead" style="width: 200%">
                        <table class="table_list_block" id="DataTableHead" >
                            <tr  class="table_list_tr_head_block" id="ignore">
                               
                                <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="0"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >票卡主类型</td>
                                <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="1"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >票卡子类型</td>
                                <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="2"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >操作类型</td>
                                <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="3"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 150px">订单编号</td>
                                <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="4"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >申请编号</td>
                                <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="5"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >逻辑卡号</td>
                                <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=true index="6"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >刻印卡号</td>
                                <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="7"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >物理卡号</td>
                                <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="8"  sortedby="asc" onclick="sortForTableBlock('clearStart');" > 制票时间</td>
                                <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="9"  sortedby="asc" onclick="sortForTableBlock('clearStart');" > 面值</td>
                                <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="10"  sortedby="asc" onclick="sortForTableBlock('clearStart');" > 有效期</td>
                                <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="11"  sortedby="asc" onclick="sortForTableBlock('clearStart');" > 限制模式</td>
                                <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="12"  sortedby="asc" onclick="sortForTableBlock('clearStart');" > 手机号码</td>
                                
                               
                            </tr>
                        </table>
                    </div>
                      <div id="clearStart"  class="divForTableBlockData" style="width: 200%">
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
                                      id="${rs.cardMainType}" >

                                    
                                      <td  id="cardMainType" class="table_list_tr_col_data_block" >
                                        
                                              ${rs.cardMainType}
                                    

                                      </td>
                                        <td  id="cardSubType" class="table_list_tr_col_data_block" >
                                         ${rs.cardSubType}

                                      </td>
                                      <td  id="" class="table_list_tr_col_data_block">
                                        
                                      </td>
                                      <td  id="orderNo" class="table_list_tr_col_data_block" style="width: 150px">
                                         ${rs.orderNo}

                                      </td>
                                      <td  id="reqNo" class="table_list_tr_col_data_block">
                                         ${rs.reqNo}
                                      </td>
                                      <td  id="logicalId" class="table_list_tr_col_data_block">
                                          ${rs.logicalId}
                                      </td>
                                      <td  id="printId" class="table_list_tr_col_data_block">
                                          ${rs.printId}
                                      </td>
                                      <td  id="phyId" class="table_list_tr_col_data_block">
                                          ${rs.phyId}
                                      </td>
                                      <td  id="manuTime" class="table_list_tr_col_data_block" >
                                          ${rs.manuTime}
                                      </td>
                                       <td  id="cardMoney" class="table_list_tr_col_data_block">
                                          ${rs.cardMoney}
                                      </td>
                                      <td  id="cardAvaDays" class="table_list_tr_col_data_block">
                                          ${rs.cardAvaDays}
                                      </td>
                                      <td  id="model" class="table_list_tr_col_data_block" >
                                          ${rs.model}
                                      </td>
                                       <td  id="phoneNo" class="table_list_tr_col_data_block" >
                                          ${rs.phoneNo}
                                      </td>

                                  </tr>

                              </c:forEach>

                          </table>
                      </div>
                  </div>
                  

                  <FORM method="post" name="detailOp" id="detailOp" action="pduOrder">
                     <div id="detail"  class="divForTableDataDetail" ><table  class="table_edit_detail"><tr></tr></table></div>
            <c:set var="divideShow" scope="request" value="0"/>
            <c:import  url="/jsp/common/common_template_web_variable.jsp?template_name=common_web_variable" />

            
            <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all_init" />
           
             <c:set var="add" scope="request" value="0"/>
            <c:set var="del" scope="request" value="0"/>
            <c:set var="save" scope="request" value="0"/>
            <c:set var="cancle" scope="request" value="0"/>
            <c:set var="audit" scope="request" value="0"/>
            <c:set var="print" scope="request" value="1"/>
            <c:set var="export" scope="request" value="1"/>
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
        <!-- 状态栏 通用模板 -->

        <c:import url="/jsp/common/common_template.jsp?template_name=common_status_table" />
        
        
             
            </body>
        </html>





