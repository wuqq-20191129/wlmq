<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
        <html>
            <head>
                <!--add by zhongzq 20180609-->
                <meta http-equiv="X-UA-Compatible" content="IE=9,Chrome=1" />
                <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
                <title>票库盘点</title>
                <script language="javascript" type="text/javascript" charset="utf-8" src="js/common_form.js"></script>
                <script language="javascript" type="text/javascript" charset="utf-8" src="js/common_form_extend.js"></script>
                <script language="javascript" type="text/javascript" charset="utf-8" src="js/Validator.js"></script>
                <script language="JavaScript" type="text/javascript" charset="utf-8" src="js/CalendarPop.js"></script>                
                <script language="JavaScript" type="text/javascript" charset="utf-8" src="js/icStorageProductBill.js"></script>
                 <script language="JavaScript" type="text/javascript" charset="utf-8" src="js/icProduceOut.js"></script>
                <link rel="stylesheet" type="text/css" href="css/frame/simple.css" title="Style"/>
                <SCRIPT LANGUAGE="JavaScript" type="text/javascript">
					function check() {		
						if(!Validator.Validate(formOp,2))
						   return;
						var rst =confirm("确认执行该操作吗？");
						if (rst)
							document.formOp.submit();
					 
					
					}
 function formInit(){
		
   document.getElementById('btExportBill').disabled = false;
            }
      
					
				</SCRIPT>
            </head>
            
            <body   onload="formInit()">
                <!-- 表头 通用模板 -->              
                <table  class="table_title">
                    <tr align="center" class="trTitle">
                        <td colspan="4">票库盘点
                        </td>
                    </tr>
                </table>

            <FORM method="post" name="formOp" id="formOp" action="ticketStorageCheckBill" >
                  <c:set var="pTitleName" scope="request" value="列表"/>
                  <c:set var="pTitleWidth" scope="request" value="50"/>
                   <c:import  url="/jsp/common/common_template.jsp?template_name=common_table_title" />
                  <div id="clearStartBlock" class="divForTableBlock">
                    <div id="clearStartHead" class="divForTableBlockHead" >
                        <table class="table_list_block" id="DataTableHead" >
                            <tr  class="table_list_tr_head_block" id="ignore">
                            
                                <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="1"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >仓库</td>
                                <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="2"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >库存票区</td>
                                <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="3"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width:120px">详细位置</td>
                                <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="2"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width:110px">盒</td>
                                <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="3"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >票卡主类型</td>
                                <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="4"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >票卡子类型</td>
                                <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=true index="5"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >库存数量</td>
                                <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="6"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >实际数量</td>
                                <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="7"  sortedby="asc" onclick="sortForTableBlock('clearStart');" > 面值(分/次)</td>
                                <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="8"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >进站线路</td>
                                <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="9"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >进站车站</td>
                                <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="10"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >出站线路</td>
                                <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="11"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >出站车站</td>
                                <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="12"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >限制模式</td>
                                <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="13"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >差额</td>
                                <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="14"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width:100px">盘点单</td>
                                <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="15"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 130px">入库时间</td>
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
                                      onMouseOut="outResultRow('detailOp', this);"
                                     onclick=" controlsByFlag('detailOp', ['del', 'audit']);                                 
                                    clickResultRow('detailOp', this, 'detail');
                                    " 
                                      id="${rs.checkBillNo}#${rs.boxId}" >
                                      <input type="hidden" name="waterNo" value="${rs.waterNo}" />
                                      <input type="hidden" id="checkBillNo" name="checkBillNo" value="${rs.checkBillNo}" />
                                    
                                      <td  id="storageName" class="table_list_tr_col_data_block" >
                                        ${rs.storageName}
                                        <input type="hidden" name="storageId" value="${rs.storageId}" />
                                      </td>
                                        <td  id="areaName" class="table_list_tr_col_data_block" >
                                         ${rs.areaName}
                                         <input type="hidden" name="areaId" value="${rs.areaId}" />
                                      </td>
                                      <td  id="chestId" class="table_list_tr_col_data_block" style="width:120px">
                                         ${rs.chestId}
                                          <input type="hidden" name="chestId" value="${rs.chestId}" />
                                      </td>
                                      <td  id="boxId" class="table_list_tr_col_data_block" style="width:110px">
                                         ${rs.boxId}
                                         <input type="hidden" name="boxId" value="${rs.boxId}" />
                                      </td>
                                      <td  id="icMainName" class="table_list_tr_col_data_block">
                                         ${rs.icMainName}
                                         <input type="hidden" name="icMainType" value="${rs.icMainType}" />
                                      </td>
                                      <td  id="icSubName" class="table_list_tr_col_data_block">
                                          ${rs.icSubName}
                                          <input type="hidden" name="icSubType" value="${rs.icSubType}" />
                                      </td>
                                      <td  id="sysAmount" class="table_list_tr_col_data_block">
                                          ${rs.sysAmount}
                                      </td>
                                      <td  id="realAmount" class="table_list_tr_col_data_block" >
                                          
                                          <INPUT TYPE="text"
											NAME="realAmount" size="5" value="${rs.realAmount}"
											require="true" dataType="integer" msg="请输入大于等于零的整数!" />
                                      </td>
                                      <td  id="cardMoney" class="table_list_tr_col_data_block" >
                                          <INPUT TYPE="text"
											NAME="cardMoney" size="5" value="${rs.cardMoney}"  readonly="true"  />
                                          
                                      </td>
                                      <td  id="lineId" class="table_list_tr_col_data_block" >
                                          ${rs.lineId}
                                      </td>
                                      <td  id="stationId" class="table_list_tr_col_data_block" >
                                          ${rs.stationId}
                                      </td>
                                       <td  id="exitLineId" class="table_list_tr_col_data_block" >
                                          ${rs.exitLineId}
                                      </td>
                                       <td  id="exitStationId" class="table_list_tr_col_data_block" >
                                          ${rs.exitStationId}
                                      </td>
                                       <td  id="model" class="table_list_tr_col_data_block" >
                                          ${rs.model}
                                      </td>
                                       <td  id="diffAmount" class="table_list_tr_col_data_block" >
                                           <c:if test="${rs.diffAmount != 0}">
                                               <font color="red">${rs.diffAmount}</font>
                                           </c:if>
                                           <c:if test="${rs.diffAmount == 0}">
                                               ${rs.diffAmount}
                                           </c:if>
                                          
                                      </td>
                                       <td  id="checkBillNo" class="table_list_tr_col_data_block" style="width:100px">
                                           <!--赋值区--><!--编码区不为单程票-->
                                               <c:choose>
                                                   
                                                   <c:when test="${rs.diffAmount != 0 && rs.areaId=='03'}">
                                                       <a href='#' onClick="openwindow('ticketStorageCheckBill?waterNo=${rs.waterNo}&checkBillNo=${rs.checkBillNo}&storageId=${rs.storageId}&areaId=${rs.areaId}&icMainType=${rs.icMainType}&icSubType=${rs.icSubType}&boxId=${rs.boxId}&ModuleID=${ModuleID}&command=queryCard&billRecordFlag=${billRecordFlag}','',800,600)">
                                                          ${rs.checkBillNo}
                                                       </a>
                                                   </c:when>
                                                   
                                                   <c:when test="${rs.diffAmount != 0 && rs.icMainType!='12' && rs.areaId=='02'}">
                                                        <a href='#' onClick="openwindow('ticketStorageCheckBill?waterNo=${rs.waterNo}&checkBillNo=${rs.checkBillNo}&storageId=${rs.storageId}&areaId=${rs.areaId}&icMainType=${rs.icMainType}&icSubType=${rs.icSubType}&boxId=${rs.boxId}&ModuleID=${ModuleID}&command=queryCard&billRecordFlag=${billRecordFlag}','',800,600)">
                                                          ${rs.checkBillNo}
                                                       </a>
                                                   </c:when>
                                                   <c:otherwise>
                                                       ${rs.checkBillNo}
                                                   </c:otherwise>
                                               </c:choose>
                                          
                                      </td>
                                       <td  id="strProDate" class="table_list_tr_col_data_block" style="width: 130px">
                                          ${rs.strProDate}
                                      </td>
                                  </tr>

                              </c:forEach>

                          </table>
                      </div>
                  </div>
                  
                 
           <input type="hidden" name="d_billNo" id="d_billNo" size="12" maxlength="12" />
           <input type="hidden" name="queryCondition" id="queryCondition" value="${QueryCondition}" />
             <input type="hidden" name="billRecordFlag" id="billRecordFlag" value="${billRecordFlag}" />
            <c:set var="divideShow" scope="request" value="1"/>
            <c:import  url="/jsp/common/common_template_web_variable.jsp?template_name=common_web_variable" />

            <!--<DIV id="detail" align="center" class="divForTableData" style="position:relative; left:20; width:95%; height:10; layer-background-color:#99CC99; border:thin outset #ffffff; z-index:1;">-->
            
            <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all_init" />
           <c:choose>
            <c:when test="${billRecordFlag != 0 }">
            <c:set var="check1" scope="request" value="1"/>
            </c:when>
           </c:choose>
            
            <c:set var="exportBill" scope="request" value="1" />
          
           
            <c:set var="addClickMethod" scope="request" value="check()"/>
            <c:set var="clickMethod" scope="request" value="btnClick('formOp','clearStart','detail','','clearStartHead');"/>
             <c:set var="exportBillAfter" scope="request" value="exportBillDetail('formOp','w_rpt_ic_rct_bl_check',['p_bill_no'],['queryCondition'],800,500);"/>
           
            <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all" />
            
            <br/>
        </FORM>
      

        <!-- 状态栏 通用模板 -->

        <c:import url="/jsp/common/common_template.jsp?template_name=common_status_table" />
        
        
             
            </body>
        </html>




