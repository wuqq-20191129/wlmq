<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
        <html>
            <head>
                <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
                <title>票库迁移管理</title>
                <script language="javascript" type="text/javascript" charset="utf-8" src="js/common_form.js"></script>
                <script language="javascript" type="text/javascript" charset="utf-8" src="js/common_form_extend.js"></script>
                <script language="javascript" type="text/javascript" charset="utf-8" src="js/Validator.js"></script>
                <script language="JavaScript" type="text/javascript" charset="utf-8" src="js/CalendarPop.js"></script>                
                <script language="JavaScript" type="text/javascript" charset="utf-8" src="js/icStorageProductBill.js"></script>
                <script language="JavaScript" type="text/javascript" charset="utf-8" src="js/icProduceOut.js"></script>
                
                <link rel="stylesheet" type="text/css" href="css/frame/simple.css" title="Style"/>
                <script language="JavaScript" type="text/javascript">
                    function disableChkBillNo(){
                         var frm = document.forms['detailOp'];
                         var chkBillNo = frm.getElementsByTagName('input')['d_billNo'];
    
                        if(chkBillNo!=null){
                            chkBillNo.disabled = true;
                        }
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
                        <td colspan="4">票库迁移管理
                        </td>
                    </tr>
                </table>

                <c:set var="pTitleName" scope="request" value="查询"/>
                <c:set var="pTitleWidth" scope="request" value="50"/>
                <c:import  url="/jsp/common/common_template.jsp?template_name=common_table_title" />

                <form method="post" name="queryOp" id="queryOp" action="ticketStorageMove">
                    <c:set var="divideShow" scope="request" value="1"/>
                    <c:import  url="/jsp/common/common_template_web_variable.jsp?template_name=common_web_variable" />
                
                    <table  class="table_edit">
                        <tr class="table_edit_tr">
                            <td class="table_edit_tr_td_label">
                                迁移单号:
                            </td>
                            <td class="table_edit_tr_td_input">
                               <input type="text" id="q_billNo"
                                           name="q_billNo" value="" size="12" maxlength="12"
                                           require="false" dataType="LimitB" min="1" max="12"
                                           msg="迁移单号最多为12位" />                        
                            </td>
                            <td class="table_edit_tr_td_label">
                                制单人:
                            </td>
                            <td class="table_edit_tr_td_input">                                
                                <input type="text" id="q_operator"
                                           name="q_operator" value="" size="12" maxlength="8"
                                           require="false" dataType="LimitB" min="1" max="8"
                                           msg="姓名最多为8位" />                      
                            </td>
                             <td class="table_edit_tr_td_label">
                                起始时间:
                            </td>
                            <td class="table_edit_tr_td_input">                                
                                    <input type="text"
                                           name="q_beginTime" id="q_beginTime" value="" size="12"
                                           require="false" dataType="Date" format="ymd"
                                           msg="生成时间格式为(yyyy-mm-dd)!" />
                                    <a
                                        href="javascript:openCalenderDialog(document.all.q_beginTime);">
                                        <img src="./images/calendar.gif"
                                             width="12" height="15" border="0" style="block" />
                                    </a>
                                </div>
                            </td>
                            <td class="table_edit_tr_td_label">
                                结束时间:
                            </td>
                            <td class="table_edit_tr_td_input">                                
                                    <input type="text" name="q_endTime"
                                           id="q_endTime" value="" size="12" require="false"
                                           dataType="Date|ThanDate" format="ymd" to="q_beginTime"
                                           msg="生成结束时间格式为(yyyy-mm-dd)且大于等于开始时间!" />
                                    <a
                                        href="javascript:openCalenderDialog(document.all.q_endTime);">
                                        <img src="./images/calendar.gif"
                                             width="12" height="15" border="0" style="block" />
                                    </a>
                                </div>
                            </td>
                          
                            
                        </tr>
                        <tr class="table_edit_tr">
                            <td class="table_edit_tr_td_label" >票卡主类型:</td>
                            <td class="table_edit_tr_td_input">
                                <select id="q_cardMainCode" name="q_cardMainCode" require="false" dataType="NotEmpty"  msg="票卡主类型不能为空" onChange="setSelectValues('queryOp', 'q_cardMainCode', 'q_cardSubCode', 'commonVariable');" >				    
                                    <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_maincard" />
                                </select>
                            </td>
                             <td class="table_edit_tr_td_label">票卡子类型：</td>
                            <td class="table_edit_tr_td_input">
                                <select id="q_cardSubCode" name="q_cardSubCode" require="false" dataType="NotEmpty"  msg="票卡子类型不能为空" >				    
                                    <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_none" />
                                </select>
                                <c:set var="pVarName" scope="request" value="commonVariable"/>
                                <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_mainsubcard" />
                            </td>
                             <td class="table_edit_tr_td_label" >迁出仓库:</td>
                            <td class="table_edit_tr_td_input">
                                <select id="q_out_storage" name="q_out_storage"  >				    
                                    <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_storage" />
                                </select>
                            </td>
                            <td class="table_edit_tr_td_label"><div align="right">迁入仓库:</div></td>
                            <td class="table_edit_tr_td_input">
                                 <select id="q_in_storage" name="q_in_storage"  >				    
                                    <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_storage" />
                                </select>
                            </td>
                              
                        </tr>
                        <tr>
                              <td class="table_edit_tr_td_label">
                               单据状态:
                            </td>
                            <td  class="table_edit_tr_td_input">
                             <select id="q_recordFlag" name="q_recordFlag">
                                       <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_billstatues" />
                                    </select>
                            </td>   
                             <td class="table_edit_tr_td_label" rowspan="1">

                                <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all_init" />
                                <c:set var="query" scope="request" value="1"/>
                                <c:set var="addClickMethod" scope="request" value="setControlNames('queryOp','q_billNo#q_operator#q_recordFlag#q_beginTime#q_endTime#q_cardMainCode#q_cardSubCode#q_outReason#q_out_storage#q_in_storage');setLineCardNames('queryOp','','','','q_cardMainCode','q_cardSubCode','commonVariable');"/>
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
                    <div id="clearStartHead" class="divForTableBlockHead" style="width: 200%;">
                        <table class="table_list_block" id="DataTableHead" >
                            <tr  class="table_list_tr_head_block" id="ignore">
                                <td   class="table_list_tr_col_head_block">
                                    <input type="checkbox" name="rectNoAll" id="rectNoAll" onclick="selectAllRecord('detailOp', 'rectNoAll', 'rectNo', 'clearStart', 0);
                                        controlsByFlagAndMul('detailOp', ['audit']);
//                                        disableControlByFlag('detailOp',  'modify', '${rs.recordFlag}');
                                        controlsDisableForMulRecords('detailOp', ['del','modify']);
                                        setPageControl('detailOp');"/>
                                </td>	
                                <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="1"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width:100px">迁移单号</td>
                                <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="2"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width:100px">出库单号 </td>
                                <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="3"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width:100px">入库单号</td>
                                <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="4"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width:100px">单据状态</td>
                                <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="5"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width:100px">票卡主类型</td>
                                <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="6"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width:100px">票卡子类型</td>
                                <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=true index="7"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width:100px">迁出仓库</td>
                                <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="8"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width:100px">迁出票区</td>
                                <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="9"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width:100px"> 迁入仓库</td>
                                <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="10"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width:100px">迁入票区</td>
                               <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="11"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >面值</td>
                               <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="12"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width:100px">迁移数量</td>
                               <!--<td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="13"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width:100px">有效期</td>-->
                               <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="14"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width:100px">始盒号</td>
                               <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="15"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width:100px">止盒号</td>
                               <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="16"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >制单人</td>
                               <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="17"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width:100px">制单日期</td>
                               <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="18"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >审核人</td>
                               <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="19"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width:150px">审核时间</td>
                               <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="20"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width:100px">车票管理员</td>
                               <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="21"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >领票人</td>
                               <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="22"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width:100px">备注</td>
                            </tr>
                        </table>
                    </div>
                      <div id="clearStart"  class="divForTableBlockData" style="width: 200%;">
                          <table class="table_list_block" id="DataTable" >

                              <c:forEach items="${ResultSet}" var="rs">
                                  <!--class="listTableData" -->
                                  <!--
                                  setSelectValuesByRow('detailOp', 'd_stationID', 'commonVariable');
                                  controlsByFlag('detailOp', ['del', 'audit', 'modify']);  
                                  以下顺序不能改变：1.disableControlByFlag、2.controlsDisableForMulRecords
                                  以下顺序不能改变：1、setSelectValuesByRowPropertyName 2、clickResultRow 3、disableControlByFlag
                                  -->
                                  <tr class="table_list_tr_data"  onMouseOver="overResultRow('detailOp', this);" 
                                      onMouseOut="outResultRow('detailOp', this);" 
                                      onclick=" 
                                            setSelectValuesByRowPropertyName('detailOp', 'd_icSubType', 'commonVariable', 'cardMainCode');
                                            setSelectValuesByRowPropertyName('detailOp', 'd_outAreaId', 'commonVariable2', 'outStorageId');
                                            setSelectValuesByRowPropertyName('detailOp', 'd_inAreaId', 'commonVariable3', 'inStorageId');
                                            clickResultRow('detailOp', this, 'detail');
                                            controlsByFlagAndMul('detailOp', ['audit']);
                                            disableControlByFlag('detailOp',  'modify', '${rs.recordFlag}');
                                            controlsDisableForMulRecords('detailOp', ['del','modify']);
                                          
                                            setPageControl('detailOp');" 
                                      id="${rs.billNo}" flag="${rs.recordFlag}" cardMainCode="${rs.icMainType}" outStorageId="${rs.outStorageId}" inStorageId="${rs.inStorageId}">

                                      <td id="rectNo1" class="table_list_tr_col_data_block">
                                          <input type="checkbox" name="rectNo" id="rectNo" onclick="unSelectAllRecord('detailOp', 'rectNoAll', 'rectNo');"
                                                 value="${rs.billNo}"  flag="${rs.recordFlag}">

                                          </input>
                                      </td>
                                        <td  id="billNo" class="table_list_tr_col_data_block" style="width:100px">
                                          ${rs.billNo}
                                      </td>
                                      <td  id="outBillNo" class="table_list_tr_col_data_block" style="width:100px">
                                          <c:choose>
                                                <c:when test="${ rs.recordFlag=='0'}">
                                                    
                                                    <a href="#" onClick="openwindow('ticketStorageMove?queryCondition=${rs.outBillNo}&command=outBillDetail&ModuleID=1501','',1200,600)"> ${rs.outBillNo}</a>
                                                </c:when>
                                                <c:otherwise>
                                                    ${rs.outBillNo}
                                                </c:otherwise>
                                            </c:choose>
                                        

                                      </td>
                                        <td  id="inBillNo" class="table_list_tr_col_data_block" style="width:100px">
                                             <c:choose>
                                                <c:when test="${ rs.recordFlag=='0'}">
                                                    <a href="#" onClick="openwindow('ticketStorageMove?queryCondition=${rs.inBillNo}&command=inBillDetail&ModuleID=1501','',1200,600)"> ${rs.inBillNo}</a>

                                                </c:when>
                                                <c:otherwise>
                                                     ${rs.inBillNo}
                                                </c:otherwise>
                                            </c:choose>
                                        

                                      </td>
                                      <td  id="recordFlagText" class="table_list_tr_col_data_block" style="width:100px">
                                         ${rs.recordFlagText}
                                      </td>
                                      <td  id="icMainType" class="table_list_tr_col_data_block" style="width:100px">
                                         ${rs.icMainTypeText}

                                      </td>
                                      <td  id="icSubType" class="table_list_tr_col_data_block" style="width:100px">
                                         ${rs.icSubTypeText}
                                      </td>
                                      <td  id="outStorageId" class="table_list_tr_col_data_block" style="width:100px">
                                          ${rs.outStorageIdText}
                                      </td>
                                      <td  id="outAreaId" class="table_list_tr_col_data_block" style="width:100px">
                                          ${rs.outAreaIdText}
                                      </td>
                                      <td  id="inStorageId" class="table_list_tr_col_data_block" style="width:100px">
                                          ${rs.inStorageIdText}
                                      </td>
                                      <td  id="inAreaId" class="table_list_tr_col_data_block" style="width:100px">
                                          ${rs.inAreaIdText}
                                      </td>
                                      <td  id="cardMoney" class="table_list_tr_col_data_block" >
                                          ${rs.cardMoney}
                                      </td>
                                     <td  id="quantity" class="table_list_tr_col_data_block" style="width:100px">
                                          ${rs.quantity}
                                      </td>
<!--                                      <td  id="strValidDate" class="table_list_tr_col_data_block" style="width:100px">
                                          ${rs.strValidDate}
                                      </td>-->
                                     
                                     <td  id="startBoxId" class="table_list_tr_col_data_block" style="width:100px">
                                          ${rs.startBoxId}
                                      </td>
                                      <td  id="endBoxId" class="table_list_tr_col_data_block" style="width:100px">
                                          ${rs.endBoxId}
                                      </td>
                                       <td  id="formMaker" class="table_list_tr_col_data_block" >
                                          ${rs.formMaker}
                                      </td>
                                      <td  id="strBillDate" class="table_list_tr_col_data_block" style="width:100px">
                                          ${rs.strBillDate}
                                      </td>
                                       <td  id="verifyPerson" class="table_list_tr_col_data_block" >
                                          ${rs.verifyPerson}
                                      </td>
                                     
                                     <td  id="strVerifyDate" class="table_list_tr_col_data_block" style="width:150px">
                                          ${rs.strVerifyDate}
                                      </td>
                                      <td  id="distributeMan" class="table_list_tr_col_data_block" style="width:100px">
                                          ${rs.distributeMan}
                                      </td>
                                       <td  id="receiveMan" class="table_list_tr_col_data_block" >
                                          ${rs.receiveMan}
                                      </td>
                                      <td  id="remark" class="table_list_tr_col_data_block" style="width:100px">
                                          ${rs.remark}
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
            
            <!--查询结果对应列，expAllFields的值为其实体类对应get方法，顺序一定要跟页面显示的顺序一致,不然导出列数据会混乱。-->
            <!--getStrValidDate,-->
            <input type="hidden" name="expAllFields" id="d_expAllFields"
                value="getBillNo,getOutBillNo,getInBillNo,getRecordFlagText,getIcMainTypeText,getIcSubTypeText,getOutStorageIdText,getOutAreaIdText,getInStorageIdText,getInAreaIdText,getCardMoney,getQuantity,getStartBoxId,getEndBoxId,getFormMaker,getStrBillDate,getVerifyPerson,getStrVerifyDate,getDistributeMan,getReceiveMan,getRemark" />
            <!--导出文件名字 生成的Excel sheet的名字也是这个-->
            <input type="hidden" name="expFileName" id="d_expFileName" value="票库迁移管理.xlsx" />
            <!--需要导出页面的表数据  一个页面多个导出全部根据这里设置详情参看运营系统退款模块-->
            <input type="hidden" name="divId" id="d_divId" value="clearStartHead">
            <!--对应你controller写的方法  一个页面多个导出全部根据这里设置详情参看运营系统退款模块-->
            <input type="hidden" name="methodUrl" id="d_methodUrl" value="/ticketStorageMoveExportAll"/>

            <!--<DIV id="detail" align="center" class="divForTableData" style="position:relative; left:20; width:95%; height:10; layer-background-color:#99CC99; border:thin outset #ffffff; z-index:1;">-->
            <div id="detail"  class="divForTableDataDetail" >
               
                <table  class="table_edit_detail">
                    <tr class="table_edit_tr">
                      
                         <td class="table_edit_tr_td_label" >单号:</td>
                        <td class="table_edit_tr_td_input">
                            <input type="text" id="d_billNo" name="d_billNo" size="20" readOnly="true" disabled="disabled"/>
                        </td>
                        <td class="table_edit_tr_td_label" >票卡主类型:</td>
                            <td class="table_edit_tr_td_input">
                                <select id="d_icMainType" name="d_icMainType" require="yes" dataType="NotEmpty"  msg="票卡主类型不能为空" onChange="setSelectValues('detailOp', 'd_icMainType', 'd_icSubType', 'commonVariable');" >				    
                                    <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_maincard" />
                                </select>
                            </td>
                             <td class="table_edit_tr_td_label">票卡子类型：</td>
                            <td class="table_edit_tr_td_input">
                                <select id="d_icSubType" name="d_icSubType" require="yes" dataType="NotEmpty"  msg="票卡子类型不能为空" >				    
                                    <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_none" />
                                </select>
                                <c:set var="pVarName" scope="request" value="commonVariable"/>
                                <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_mainsubcard" />
                            </td>
                             <td class="table_edit_tr_td_label" >迁出仓库:</td>
                            <td class="table_edit_tr_td_input">
                                <select id="d_outStorageId" name="d_outStorageId" require="yes" dataType="NotEmpty"  msg="迁出仓库不能为空"  onChange="
                                            setSelectValues('detailOp','d_outStorageId','d_outAreaId','commonVariable2');
                                            controlMoveRadio('detailOp','d_icMainType','d_icSubType','d_outAreaId','d_quantity','d_startBoxId','d_endBoxId','d_select');
                                            controlMoveCardMoney('detailOp','d_outAreaId','d_cardMoney','d_cardMoney1');">
                                    <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_storage" />
                                </select>
                            </td>
                    </tr>
                    <tr class="table_edit_tr">
                        <td class="table_edit_tr_td_label">迁出票区:</td>
                        <td class="table_edit_tr_td_input">
                            <select id="d_outAreaId" name="d_outAreaId" require="yes" dataType="Integer" msg="迁出票区不能为空" onChange="
                                                          controlMoveRadio('detailOp','d_icMainType','d_icSubType','d_outAreaId','d_quantity','d_startBoxId','d_endBoxId','d_select');
                                                          controlMoveCardMoney('detailOp','d_outAreaId','d_cardMoney','d_cardMoney1');">
                                    <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_none" />
                                </select>   
                                <c:set var="pVarName" scope="request" value="commonVariable2"/>
                                <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_storagezone" />
                        </td>
                        <td class="table_edit_tr_td_label">
                            迁入仓库:
                        </td>
                        <td class="table_edit_tr_td_input">
                             <select id="d_inStorageId" name="d_inStorageId" require="yes" dataType="NotEmpty"  msg="迁入仓库不能为空" onChange="setSelectValues('detailOp','d_inStorageId','d_inAreaId','commonVariable3');">				    
                                    <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_storage" />
                                </select>
                        </td>
                        <td class="table_edit_tr_td_label" >迁入票区:</td>
                        <td class="table_edit_tr_td_input">
                              <select id="d_inAreaId" name="d_inAreaId" require="yes" dataType="Integer" msg="迁入票区不能为空" >
                                    <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_none" />
                                </select>   
                                <c:set var="pVarName" scope="request" value="commonVariable3"/>
                                <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_storagezone" />
                        </td>
                        <td class="table_edit_tr_td_label">面值(分/次):</td>
                        <td class="table_edit_tr_td_input">
                             <input type="text"
                                               name="d_cardMoney" id="d_cardMoney" size="3" value="0"
                                               require="yes" dataType="Integer" maxlength="5"
                                               msg="面值应是数字" />
                                        <select id="d_cardMoney1"
                                                name="d_cardMoney1" dataType="false"
                                                onChange="setCardMoney('detailOp','d_cardMoney','d_cardMoney1')">
                                           <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_bynum" />
                                        </select>
                        </td>
                    </tr>
                     <tr class="table_edit_tr">
<!--                        <td class="table_edit_tr_td_label">有效期 </td>
                        <td class="table_edit_tr_td_input">
                             <input type="text"
                                               name="d_strValidDate" id="d_strValidDate" value="" size="12"
                                               require="false" dataType="Date" format="ymd"
                                               msg="证件有效期时间格式为(yyyy-mm-dd)!" />
                                        <a
                                            href="javascript:openCalenderDialog(document.all.d_strValidDate);">
                                            <img src="./images/calendar.gif"
                                                 width="12" height="15" border="0" style="block" />
                                        </a>
                        </td>-->
                        <td class="table_edit_tr_td_label">
                              <input type="radio"
                                               name="d_select" id="d_select" size="1"
                                               onClick="controlByRadioForNumForMove('detailOp',['d_quantity','d_startBoxId','d_endBoxId']);
											"
                                               value="1" />
                            迁移数量
                        </td>
                        <td class="table_edit_tr_td_input">
                             <input type="text"
                                               name="d_quantity" id="d_quantity"
                                               size="10" require="false" maxlength="9" 
                                               dataType="integer|Positive" msg="迁移数量为大于零的整数!" />
                                      
                        </td>
                        <td class="table_edit_tr_td_label" >
                             <input type="radio"
                                               name="d_select" id="d_select" size="1" 
                                               onClick="controlByRadioForNumForMove('detailOp',['d_quantity','d_startBoxId','d_endBoxId']);
											"
                                               value="3" />
                            始盒号:</td>
                        <td class="table_edit_tr_td_input">
                            <input type="text"
                                               name="d_startBoxId" id="d_startBoxId" size="16" min="14" max="14"
                                               require="false" maxlength="14" dataType="Number|LimitB" 
                                               msg="始盒号为14位数字!" />
                        </td>
                        <td class="table_edit_tr_td_label">止盒号:</td>
                        <td class="table_edit_tr_td_input">
                            <input type="text"
                                               name="d_endBoxId" id="d_endBoxId" size="16" min="14" max="14"
                                               require="false" maxlength="14" 
                                               dataType="Number|CompareNum|Limit"
                                               operator="GreaterThanEqual" to="d_startBoxId"
                                               msg="止盒号为14位整数且大于等于始盒号!" />
                        </td>
                        <td class="table_edit_tr_td_label">车票管理员:</td>
                        <td class="table_edit_tr_td_input">
                               <input type="text"
                                               name="d_distributeMan" id="d_distributeMan" size="10"
                                               require="false" dataType="NotEmpty|LimitB" maxlength="8" min="1" max="8"
                                               msg="车票管理员不能为空且最大8个字节,一个中文字2个字节" />
                        </td>
                    </tr>
                     <tr class="table_edit_tr">
                        <td class="table_edit_tr_td_label">
                            领票人:
                        </td>
                        <td class="table_edit_tr_td_input">
                              <input type="text"
                                               name="d_receiveMan" id="d_receiveMan" size="10"
                                               require="false" dataType="NotEmpty|LimitB" maxlength="8" min="1" max="8"
                                               msg="领票人不能为空且最大8个字节,一个中文字2个字节" />
                                      
                        </td>
                         <td class="table_edit_tr_td_label">
                            备注:
                        </td>
                        <td class="table_edit_tr_td_input">
                            <input type="text"
                                               name="d_remark" id="d_remark" size="30"
                                               require="false" dataType="NotEmpty|LimitB" maxlength="400"
                                               min="1" max="400" msg="备注最大长度为400个字节，一个中文字是2个字节" />
                                      
                        </td>
                       
                    </tr>
                </table>
            </div>






            <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all_init" />
            <c:set var="add" scope="request" value="1"/>
            <c:set var="del" scope="request" value="1"/>
             <c:set var="modify" scope="request" value="1"/>
            <c:set var="save" scope="request" value="1"/>
            <c:set var="cancle" scope="request" value="1"/>
            <c:set var="audit" scope="request" value="1"/>
            <c:set var="print" scope="request" value="1"/>
            <c:set var="expAll" scope="request" value="1" />
            <c:set var="export" scope="request" value="1"/>
            <c:set var="btNext" scope="request" value="1"/>
            <c:set var="btNextEnd" scope="request" value="1"/>
            <c:set var="btBack" scope="request" value="1"/>
            <c:set var="btBackEnd" scope="request" value="1"/>


            <c:set var="clickMethod" scope="request" value="btnClick('detailOp','clearStart','detail','','clearStartHead');"/>
             <c:set var="addAfterMethod" scope="request" value=""/>
             <c:set var="addAfterClickModifyMethod" scope="request" value="disableMoveControls('detailOp','d_quantity','d_startBoxId','d_endBoxId','d_select');disableChkBillNo();"/>
              
            <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all" />
            <br/>
        </FORM>
        <!--
        <c:set var="addQueryMethod" scope="request" value="setLineCardNames('detailOp','d_storage','d_zone','commonVariable','','','');"/>
        <c:set var="addClickMethod" scope="request" value="setUpdatePrimaryKey('detailOp','d_lineID#d_stationID#d_deviceType#d_deviceID#Version');"/>
        -->

        
        
        
             
            </body>
        </html>





