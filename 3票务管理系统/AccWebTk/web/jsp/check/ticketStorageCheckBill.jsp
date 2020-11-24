<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
        <html>
            <head>
                <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
                <title>票库盘点单</title>
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
            setPageControl('detailOp');
            setTableRowBackgroundBlock('DataTable')">
                <!-- 表头 通用模板 -->              
                <table  class="table_title">
                    <tr align="center" class="trTitle">
                        <td colspan="4">票库盘点单
                        </td>
                    </tr>
                </table>

                <c:set var="pTitleName" scope="request" value="查询"/>
                <c:set var="pTitleWidth" scope="request" value="50"/>
                <c:import  url="/jsp/common/common_template.jsp?template_name=common_table_title" />

                <form method="post" name="queryOp" id="queryOp" action="ticketStorageCheckBill">
                    <c:set var="divideShow" scope="request" value="1"/>
                    <c:import  url="/jsp/common/common_template_web_variable.jsp?template_name=common_web_variable" />
                
                    <table  class="table_edit">
                        <tr class="table_edit_tr">
                            <td class="table_edit_tr_td_label">
                                盘点单号
                            </td>
                            <td class="table_edit_tr_td_input">
                                <input type="text" id="q_billNo"
                                       name="q_billNo" size="12" require="false" maxlength="12" dataType="LimitB"
                                       min="12" max="12" msg="盘点单号应为12位" />                                
                            </td>
                            <td class="table_edit_tr_td_label">
                                仓库:
                            </td>
                            <td class="table_edit_tr_td_input">                                
                                <select id="q_storage" name="q_storage" onChange="setSelectValues('queryOp','q_storage','q_area_id','commonVariable1');">
                                    <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_storage" />
                                </select>                        
                            </td>
                            <td class="table_edit_tr_td_label">
                                库存票区:
                            </td>
                            <td  class="table_edit_tr_td_input">
                                <select id="q_area_id" name="q_area_id" >
                                     <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_none" />
                                    
                                </select>   
                                     <c:set var="pVarName" scope="request" value="commonVariable1"/>
                                     <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_storagezone" />
                            </td>                         
                        </tr>
                        <tr class="table_edit_tr">
                            <td class="table_edit_tr_td_label">
                                单据类型
                            </td>
                            <td  class="table_edit_tr_td_input">                                
                                <select id="q_recordFlag" name="q_recordFlag" >
                                    <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_billstatues" />
                                </select>
                                </div>
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


                            <td class="table_edit_tr_td_label" rowspan="1">

                                <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all_init" />
                                <c:set var="query" scope="request" value="1"/>
                                <c:set var="addClickMethod" scope="request" value="setControlNames('queryOp','q_billNo#q_operator#q_recordFlag#q_beginTime#q_endTime#q_cardMainCode#q_cardSubCode#q_outReason#q_storage#q_area_id');setLineCardNames('queryOp','','','','q_storage','q_area_id','commonVariable1');"/>
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
                    <div id="clearStartHead" class="divForTableBlockHead" >
                        <table class="table_list_block" id="DataTableHead" >
                            <tr  class="table_list_tr_head_block" id="ignore">
                                <td   class="table_list_tr_col_head_block">
                                    <input type="checkbox" name="rectNoAll" id="rectNoAll" onclick="selectAllRecord('detailOp', 'rectNoAll', 'rectNo', 'clearStart', 0);
                                        controlsByFlagAndMul('detailOp', ['audit']);
                                        controlsDisableForMulRecords('detailOp', ['del']);
                                        setPageControl('detailOp');"/>
                                </td>	
                                <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="1"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 90px">盘点单</td>
                                <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="2"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 90px">票卡主类型</td>
                                <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="3"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >票卡子类型</td>
                                <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="2"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 90px">仓库</td>
                                <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="3"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >库存票区</td>
                                <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="4"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >单据状态</td>
                                <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=true index="5"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 120px">盘点时间</td>
                                <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="6"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >盘点人</td>
                                <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="7"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 130px"> 审核时间</td>
                                <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="8"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 130px">审核人</td>
                               
                            </tr>
                        </table>
                    </div>
                      <div id="clearStart"  class="divForTableBlockData" >
                          <table class="table_list_block" id="DataTable" >

                              <c:forEach items="${ResultSet}" var="rs">
                                  <!--class="listTableData" -->
                                  <!--
                                  setSelectValuesByRow('detailOp', 'd_stationID', 'commonVariable');
                                  controlsByFlag('detailOp', ['del', 'audit']);
                                  -->
                                  <tr class="table_list_tr_data"  onMouseOver="overResultRow('detailOp', this);" 
                                      onMouseOut="outResultRow('detailOp', this);" 
                                      onclick="  
                                        controlsByFlagAndMul('detailOp', ['audit']);
                                        controlsDisableForMulRecords('detailOp', ['del']);
                                        
                                        setSelectValuesByRowPropertyName('detailOp', 'd_icSubType', 'commonVariable', 'icMainType');
                                        setSelectValuesByRowPropertyName('detailOp', 'd_areaId', 'commonVariable2', 'storageId');
                                        clickResultRow('detailOp', this, 'detail');
                                        setPageControl('detailOp');" 
                                      id="${rs.checkBillNo}" flag="${rs.recordFlag}" icMainType="${rs.icMainType}" storageId="${rs.storageId}">

                                      <td id="rectNo1" class="table_list_tr_col_data_block">
                                          <input type="checkbox" name="rectNo" id="rectNo" onclick="unSelectAllRecord('detailOp', 'rectNoAll', 'rectNo');"
                                                 value="${rs.checkBillNo}"  flag="${rs.recordFlag}">

                                          </input>
                                      </td>
                                      <td  id="checkBillNo" class="table_list_tr_col_data_block" style="width: 90px">
                                          <a href='#'
                                             onClick="openwindow('ticketStorageCheckBill?queryCondition=${rs.checkBillNo}&command=queryDetail&operType=billChkDetail&billRecordFlag=${rs.recordFlag}&ModuleID=${ModuleID}', '', 1200, 800)">
                                              <!--  <a href='#' onClick="window.showModelessDialog('ticketStorageOutProduceManage.do?queryCondition={billNo}&amp;command=queryDetail&amp;operType=planDetail&amp;ModuleID={/Service/Result/ModuleID}','0','dialogWidth:700px;dialogHeight:500px;center:yes;resizable:no;status:no;scroll:no')"> --> 
                                              ${rs.checkBillNo}
                                          </a>

                                      </td>
                                        <td  id="icMainType" class="table_list_tr_col_data_block" style="width: 90px">
                                         ${rs.icMainTypeText}

                                      </td>
                                      <td  id="icSubType" class="table_list_tr_col_data_block">
                                         ${rs.icSubTypeText}
                                      </td>
                                      <td  id="storageId" class="table_list_tr_col_data_block" style="width: 90px">
                                         ${rs.storageIdText}

                                      </td>
                                      <td  id="areaId" class="table_list_tr_col_data_block">
                                         ${rs.areaIdText}
                                      </td>
                                      <td  id="recordFlagText" class="table_list_tr_col_data_block">
                                          ${rs.recordFlagText}
                                      </td>
                                      <td  id="checkDate" class="table_list_tr_col_data_block" style="width: 120px">
                                          ${rs.startChkDate}
                                      </td>
                                      <td  id="checkPerson" class="table_list_tr_col_data_block">
                                          ${rs.checkPerson}
                                      </td>
                                      <td  id="verifyDate" class="table_list_tr_col_data_block" style="width: 130px">
                                          ${rs.endChkDate}
                                      </td>
                                      <td  id="verifyPerson" class="table_list_tr_col_data_block" style="width: 130px">
                                          ${rs.verifyPerson}
                                      </td>
                                    

                                  </tr>

                              </c:forEach>

                          </table>
                      </div>
                  </div>
                     <c:set var="pTitleName" scope="request" value="明细"/>
        <c:set var="pTitleWidth" scope="request" value="50"/>
        <c:import url="/jsp/common/common_template.jsp?template_name=common_table_title" />

        <FORM method="post" name="detailOp" id="detailOp" action="ticketStorageCheckBill" >

            <c:set var="divideShow" scope="request" value="1"/>
            <c:import  url="/jsp/common/common_template_web_variable.jsp?template_name=common_web_variable" />
            
            <!--查询结果对应列，expAllFields的值为其实体类对应get方法，顺序一定要跟页面显示的顺序一致,不然导出列数据会混乱。-->
            <input type="hidden" name="expAllFields" id="d_expAllFields"
                value="getCheckBillNo,getIcMainTypeText,getIcSubTypeText,getStorageIdText,getAreaIdText,getRecordFlagText,getStartChkDate,getCheckPerson,getEndChkDate,getVerifyPerson" />
            <!--导出文件名字 生成的Excel sheet的名字也是这个-->
            <input type="hidden" name="expFileName" id="d_expFileName" value="票库盘点单.xlsx" />
            <!--需要导出页面的表数据  一个页面多个导出全部根据这里设置详情参看运营系统退款模块-->
            <input type="hidden" name="divId" id="d_divId" value="clearStartHead">
            <!--对应你controller写的方法  一个页面多个导出全部根据这里设置详情参看运营系统退款模块-->
            <input type="hidden" name="methodUrl" id="d_methodUrl" value="/ticketStorageCheckBillExportAll"/>

            <!--<DIV id="detail" align="center" class="divForTableData" style="position:relative; left:20; width:95%; height:10; layer-background-color:#99CC99; border:thin outset #ffffff; z-index:1;">-->
            <div id="detail"  class="divForTableDataDetail" >
                <table  class="table_edit_detail">
                    <tr class="table_edit_tr">
                        <td class="table_edit_tr_td_label">票卡主类型 </td>
                        <td class="table_edit_tr_td_input">
                            <select id="d_icMainType"
                                    name="d_icMainType" require="true" dataType="NotEmpty" msg="票卡主类型不能为空!"
                                    onChange="setSelectValues('detailOp','d_icMainType','d_icSubType','commonVariable');">
                                <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_maincard" />
                            </select>
                        </td>
                        <td class="table_edit_tr_td_label">
                            票卡子类型
                        </td>
                        <td class="table_edit_tr_td_input">
                            <select id="d_icSubType" name="d_icSubType" >
                                <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_none" />
                            </select>
                            <c:set var="pVarName" scope="request" value="commonVariable"/>
                            <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_mainsubcard" />              
                        </td>
                        <td class="table_edit_tr_td_label">
                            仓库:
                        </td>
                        <td class="table_edit_tr_td_input">
                            <select id="d_storageId" name="d_storageId" require="true" dataType="NotEmpty" msg="仓库不能为空" onChange="setSelectValues('detailOp','d_storageId','d_areaId','commonVariable2');">
                                <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_storage" />
                            </select>   
                        </td>

                        <td class="table_edit_tr_td_label">
                            库存票区:
                        </td>
                        <td>
                            <select id="d_areaId" name="d_areaId" require="true" dataType="Integer" msg="票区不能为空" >
                                <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_none" />
                            </select>   
                            <c:set var="pVarName" scope="request" value="commonVariable2"/>
                            <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_storagezone" />
                        </td>
                    </tr>
                </table>
            </div>






            <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all_init" />
            <c:set var="add" scope="request" value="1"/>
            <c:set var="del" scope="request" value="1"/>
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
            <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all" />
            <br/>
        </FORM>
        <!--
        <c:set var="addQueryMethod" scope="request" value="setLineCardNames('detailOp','d_storage','d_zone','commonVariable','','','');"/>
        <c:set var="addClickMethod" scope="request" value="setUpdatePrimaryKey('detailOp','d_lineID#d_stationID#d_deviceType#d_deviceID#Version');"/>
        -->

        
        
        
             
            </body>
        </html>



