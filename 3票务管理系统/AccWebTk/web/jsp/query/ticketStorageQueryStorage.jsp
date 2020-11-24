<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
        <html>
            <head>
                <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
                <title>库存查询</title>
                <script language="javascript" type="text/javascript" charset="utf-8" src="js/common_form.js"></script>
                <script language="javascript" type="text/javascript" charset="utf-8" src="js/common_form_extend.js"></script>
                <script language="javascript" type="text/javascript" charset="utf-8" src="js/Validator.js"></script>
                <script language="JavaScript" type="text/javascript" charset="utf-8" src="js/CalendarPop.js"></script>                
                <script language="JavaScript" type="text/javascript" charset="utf-8" src="js/icStorageProductBill.js"></script>
                <link rel="stylesheet" type="text/css" href="css/frame/simple.css" title="Style"/>
                <script language="JavaScript">
                    function MM_openBrWindow(theURL,winName,features) {
                    window.open(theURL,winName,features);
                    }
                </script>
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
                        <td colspan="4">库存查询
                        </td>
                    </tr>
                </table>

                <c:set var="pTitleName" scope="request" value="查询"/>
                <c:set var="pTitleWidth" scope="request" value="50"/>
                <c:import  url="/jsp/common/common_template.jsp?template_name=common_table_title" />

                <form method="post" name="queryOp" id="queryOp" action="ticketStorageQueryStorage">
                    <c:set var="divideShow" scope="request" value="1"/>
                    <c:import  url="/jsp/common/common_template_web_variable.jsp?template_name=common_web_variable" />
                
                    <table  class="table_edit">
                        <tr class="table_edit_tr">
                            <td class="table_edit_tr_td_label">
                                票卡主类型
                            </td>
                            <td class="table_edit_tr_td_input">
                                <select id="q_cardMainCode"
                                        name="q_cardMainCode" require="false" dataType="NotEmpty" msg="票卡主类型不能为空!"
                                        onChange="setSelectValues('queryOp','q_cardMainCode','q_cardSubCode','commonVariable');">
                                    <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_maincard" />
                                </select>                              
                            </td>
                            <td class="table_edit_tr_td_label">
                                票卡子类型
                            </td>
                            <td class="table_edit_tr_td_input">                                
                                <select id="q_cardSubCode" name="q_cardSubCode" >
                                    <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_none" />
                                </select>
                                <c:set var="pVarName" scope="request" value="commonVariable"/>
                                <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_mainsubcard" />                       
                            </td>
                            <td class="table_edit_tr_td_label">
                                面值(分/次)
                            </td>
                            <td  class="table_edit_tr_td_input">
                                <input type="text" name="q_cardMoney" id="q_cardMoney"
                                       size="5" maxlength="5"  require="false" dataType="LimitB|Number" min="0" max="5" msg="面值为1-5位数字"/>
                            </td>     
                        </tr>
                        <tr class="table_edit_tr">
                            
                            <td class="table_edit_tr_td_label">
                                仓库
                            </td>
                            <td  class="table_edit_tr_td_input">                                
                                <select id="q_storage" name="q_storage" require="false" dataType="NotEmpty" msg="仓库不能为空" onChange="setSelectValues('queryOp','q_storage','q_area_id','commonVariable2');">
                                    <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_storage" />
                                </select> 
                               
                            </td>

                            <td class="table_edit_tr_td_label">
                                票区
                            </td>
                            <td class="table_edit_tr_td_input">                                
                                <select id="q_area_id" name="q_area_id" require="false" dataType="Integer" msg="票区不能为空" >
                                    <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_none" />
                                </select>   
                                <c:set var="pVarName" scope="request" value="commonVariable2"/>
                                <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_storagezone" />
                            </td>
                            <td class="table_edit_tr_td_label">
                                有效期
                            </td>
                            <td  class="table_edit_tr_td_input">
                                <input type="text"
                                       name="q_validDate" id="q_validDate" value="" size="12"
                                       require="false" dataType="Date" format="ymd"
                                       msg="有效期格式为(yyyy-mm-dd)!" />
                                <a
                                    href="javascript:openCalenderDialog(document.all.q_validDate);">
                                    <img
                                        src="./images/calendar.gif" width="12" height="15"
                                        border="0" style="block" />
                                </a>
                            </td>
                            
                        </tr>
                        <tr class="table_edit_tr">
                            <td class="table_edit_tr_td_label">
                                盒号
                            </td>
                            <td class="table_edit_tr_td_input">                                
                                <input type="text" name="q_boxId" id="q_boxId"
                                       size="14" maxlength="14"  require="false" dataType="LimitB|Number" min="14" max="14" msg="盒号为14位数字"/>
                            </td>
                             <td class="table_edit_tr_td_label">
                                入库起始时间
                            </td>
                            <td class="table_edit_tr_td_input">                                
                                <input type="text"
                                       name="q_beginTime" id="q_beginTime" value="" size="12"
                                       require="false" dataType="Date" format="ymd"
                                       msg="入库起始时间格式为(yyyy-mm-dd)!" />
                                <a
                                    href="javascript:openCalenderDialog(document.all.q_beginTime);">
                                    <img src="./images/calendar.gif"
                                         width="12" height="15" border="0" style="block" />
                                </a>
                            </td>
                            <td class="table_edit_tr_td_label">
                                入库结束时间
                            </td>
                            <td class="table_edit_tr_td_input">                                
                                <input type="text" name="q_endTime"
                                       id="q_endTime" value="" size="12" require="false"
                                       dataType="Date|ThanDate" format="ymd" to="q_beginTime"
                                       msg="入库结束时间格式为(yyyy-mm-dd)且大于等于开始时间!" />
                                <a
                                    href="javascript:openCalenderDialog(document.all.q_endTime);">
                                    <img src="./images/calendar.gif"
                                         width="12" height="15" border="0" style="block" />
                                </a>
                            </td>
                            <td class="table_edit_tr_td_label" rowspan="1">

                                <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all_init" />
                                <c:set var="query" scope="request" value="1"/>
                                <c:set var="addClickMethod" scope="request" value="setControlNames('queryOp','q_cardMoney#q_validDate#q_boxId#q_beginTime#q_endTime#q_cardMainCode#q_cardSubCode#q_outReason#q_storage#q_area_id');setLineCardNames('queryOp','q_cardMainCode','q_cardSubCode','commonVariable','q_storage','q_area_id','commonVariable2');"/>
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
                    <div id="clearStartHead" class="divForTableBlockHead" style="width: 130%;">
                        <table class="table_list_block" id="DataTableHead" >
                            <tr  class="table_list_tr_head_block" id="ignore">
                              
                                <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="0"  sortedby="asc" onclick="sortForTableBlock('clearStart');"  style="width:90px" >票卡主类型</td>
                                <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="1"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width:90px"  >票卡子类型</td>
                                <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="2"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >面值(分/次)</td>
                                <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="3"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >数量</td>
                                <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="4"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >仓库</td>
                                <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="5"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 130px">库存票区</td>
                                <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=true index="6"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >详细位置</td>
                                <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="7"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 130px">盒号</td>
                                <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="8"  sortedby="asc" onclick="sortForTableBlock('clearStart');" > 进站线路</td>
                                <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="9"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >进站车站</td>
                                <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="10"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >出站线路</td>
                                <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="11"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >出站车站</td>
                                <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="12"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 130px">乘次票限制模式</td>
                                <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="13"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >有效期</td>
                                <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="14"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >入库时间</td>
                               
                            </tr>
                        </table>
                    </div>
                      <div id="clearStart"  class="divForTableBlockData" style="width: 130%;">
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
                                      id="${rs.icMainType}${rs.icSubType} ${rs.cardMoney} ${rs.cardNum}${rs.boxId}" >

                                    
                                        <td  id="icMainType" class="table_list_tr_col_data_block"  style="width:90px" >
                                         ${rs.icMainType}

                                      </td>
                                      <td  id="icSubType" class="table_list_tr_col_data_block" style="width:90px" >
                                         ${rs.icSubType}
                                      </td>
                                        <td  id="cardMoney" class="table_list_tr_col_data_block">
                                         ${rs.cardMoney}
                                      </td>
                                       <td  id="cardNum" class="table_list_tr_col_data_block">
                                         ${rs.cardNum}
                                      </td>
                                      <td  id="storageId" class="table_list_tr_col_data_block" >
                                         ${rs.storageId}

                                      </td>
                                      <td  id="areaId" class="table_list_tr_col_data_block" style="width: 130px">
                                         ${rs.areaId}
                                      </td>
                                      <td  id="place" class="table_list_tr_col_data_block">
                                         ${rs.place}
                                      </td>
                                      <td  id="boxId" class="table_list_tr_col_data_block" style="width: 130px">
                                          <a href="#" onClick="MM_openBrWindow('boxLogicalDetail?box_id=${rs.boxId}&ModuleID=1602','','scrollbars=yes,resizable=yes,width=800,height=700')">${rs.boxId}</a>
                                      </td>
                                       <td  id="lineId" class="table_list_tr_col_data_block">
                                         ${rs.lineId}
                                      </td>
                                      <td  id="stationId" class="table_list_tr_col_data_block">
                                         ${rs.stationId}
                                      </td>                                      
                                      <td  id="exitLineId" class="table_list_tr_col_data_block">
                                          ${rs.exitLineId}
                                      </td>
                                       <td  id="exitStationId" class="table_list_tr_col_data_block">
                                          ${rs.exitStationId}
                                      </td>
                                      <td  id="model" class="table_list_tr_col_data_block" style="width: 130px">
                                          ${rs.model}
                                      </td>
                                      <td  id="validDate" class="table_list_tr_col_data_block">
                                          ${rs.validDate}
                                      </td>
                                      <td  id="productDate" class="table_list_tr_col_data_block" >
                                          ${rs.strProductDate}
                                      </td>
                                 
                                    

                                  </tr>

                              </c:forEach>

                          </table>
                      </div>
                  </div>
                  <c:set var="pTitleName" scope="request" value="统计"/>
        <c:set var="pTitleWidth" scope="request" value="50"/>
        <c:import url="/jsp/common/common_template.jsp?template_name=common_table_title" />
          <table class="tableStyle" width="95%" align="center" >
                            <tr  class="trContent" id="ignore">
                                <td id="orderTd" width="10%"    class="listTableHead"  ><div align="center">票卡总数(单位：张)</div></td>
                                <td id="orderTd" width="10%"    class="listTableHead"  ><div align="center">总记录数(单位：条)</div></td>
						
						</tr>
						 <tr class="trContent"  >

                                    
                                        <td width="15%" id="ticketTotals" class="listTableHead" style="width: 90px">
                                            <div align="center">${ticketTotals}</div>
                                            <input type="hidden" name="ticketTotals" value="${ticketTotals}" />

                                      </td>
                                       <td width="15%" id="allRecords" class="listTableHead" style="width: 90px">
                                           <div align="center"> ${allRecords}</div>
                                           <input type="hidden" name="allRecords" value="${allRecords}" />
                                      </td>
                                                 </tr>
                                                 
					</table>
        <FORM method="post" name="detailOp" id="detailOp" action="ticketStorageQueryStorage">

            <c:set var="divideShow" scope="request" value="1"/>
            <c:import  url="/jsp/common/common_template_web_variable.jsp?template_name=common_web_variable" />

            <div id="detail"  class="divForTableDataDetail" ></div>
            <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all_init" />
            
            <input type="hidden" name="expAllFields" id="d_expAllFields"
                   value="getIcMainType,getIcSubType,getCardMoney,getCardNum,getStorageId,getAreaId,getPlace,getBoxId,getLineId,getStationId,getExitLineId,getExitStationId,getModel,getValidDate,getStrProductDate" />
            <input type="hidden" name="expFileName" id="d_expFileName" value="库存查询.xlsx" />
            <input type="hidden" name="divId" id="d_divId" value="clearStartHead">
            <input type="hidden" name="methodUrl" id="d_methodUrl" value="/ticketStorageQueryStorageExportAll"/>
           
             <c:set var="add" scope="request" value="0"/>
            <c:set var="del" scope="request" value="0"/>
            <c:set var="save" scope="request" value="0"/>
            <c:set var="cancle" scope="request" value="0"/>
            <c:set var="audit" scope="request" value="0"/>
            <c:set var="print" scope="request" value="1"/>
            <c:set var="export" scope="request" value="1"/>
            <c:set var="expAll" scope="request" value="1"/>
            <c:set var="btNext" scope="request" value="1"/>
            <c:set var="btNextEnd" scope="request" value="1"/>
            <c:set var="btBack" scope="request" value="1"/>
            <c:set var="btBackEnd" scope="request" value="1"/>
            <c:set var="btGoPage" scope="request" value="1"/>
          
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




