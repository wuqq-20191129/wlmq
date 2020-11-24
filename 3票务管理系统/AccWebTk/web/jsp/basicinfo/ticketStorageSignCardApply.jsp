<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>记名卡申请</title>
        <script language="javascript" type="text/javascript" charset="utf-8" src="js/common_form.js"></script>
        <script language="javascript" type="text/javascript" charset="utf-8" src="js/common_form_extend.js"></script>
        <script language="javascript" type="text/javascript" charset="utf-8" src="js/Validator.js"></script>
        <script language="JavaScript" type="text/javascript" charset="utf-8" src="js/CalendarPop.js"></script>                
        <script language="JavaScript" type="text/javascript" charset="utf-8" src="js/icStorageProductBill.js"></script>
        <link rel="stylesheet" type="text/css" href="css/frame/simple.css" title="Style"/>
        <script language="javascript" >
            function OpenDialog()
            {

                //openwindow('ticketStorageQueryNamed.do','',600,250);
                openwindow('ticketStorageImpSignCard?command=import', '', 600, 400);
            }
            function selectedRowEx(thisObject)
            {
                var selectedRow;
                selectedRow = thisObject;
                document.getElementById('d_reqNo').value = selectedRow.id;
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
                <td colspan="4">记名卡申请
                </td>
            </tr>
        </table>

        <c:set var="pTitleName" scope="request" value="查询"/>
        <c:set var="pTitleWidth" scope="request" value="50"/>
        <c:import  url="/jsp/common/common_template.jsp?template_name=common_table_title" />

        <form method="post" name="queryOp" id="queryOp" action="ticketStorageSignCardApply">
            <c:set var="divideShow" scope="request" value="1"/>
            <c:import  url="/jsp/common/common_template_web_variable.jsp?template_name=common_web_variable" />

            <table  class="table_edit">
                <tr class="table_edit_tr">
                    <td class="table_edit_tr_td_label">
                        操作员
                    </td>
                    <td class="table_edit_tr_td_input">
                        <input type="text" id="q_operator"
                               name="q_operator" value="" size="12" maxlength="8"
                               require="false" dataType="LimitB" min="1" max="8"
                               msg="操作员最多为8位" />                        
                    </td>
                    <td class="table_edit_tr_td_label">
                        姓名:
                    </td>
                    <td class="table_edit_tr_td_input">                                
                        <input type="text" id="q_apply_name"
                               name="q_apply_name" value="" size="12" maxlength="8"
                               require="false" dataType="LimitB" min="1" max="8"
                               msg="姓名最多为8位" />                      
                    </td>
                    <td class="table_edit_tr_td_label">
                        证件号:
                    </td>
                    <td  class="table_edit_tr_td_input">
                        <input type="text" name="q_identityTd" id="q_identityTd" size="20" maxlength="30" require="false" dataType="LimitB" />
                    </td>   
                    <td class="table_edit_tr_td_label">
                        申请类型:
                    </td>
                    <td  class="table_edit_tr_td_input">
                        <select name="q_app_business_type" id="q_app_business_type">
                            <option value="">=请选择=</option>
                            <option value="1" >
                                正常申请
                            </option>
                            <option value="2">
                                补卡申请
                            </option>
                        </select>
                    </td> 
                </tr>
                <tr class="table_edit_tr">
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
                    <td class="table_edit_tr_td_label">
                        处理结果
                    </td>
                    <td  class="table_edit_tr_td_input">                                
                        <select name="q_hdl_flag" id="q_hdl_flag">
                            <option value="">=请选择=</option>
                            <option value="2" >
                                ES生产完毕
                            </option>
                            <option value="1" >
                                ES制作中
                            </option>
                            <option value="0"  >
                                未生产
                            </option>
                        </select>
                    </td>

                    <td class="table_edit_tr_td_label" >票卡主类型:</td>
                    <td class="table_edit_tr_td_input">
                        <select id="q_cardMainCode" name="q_cardMainCode" require="false" dataType="NotEmpty"  msg="票卡主类型不能为空" onChange="setSelectValues('queryOp', 'q_cardMainCode', 'q_cardSubCode', 'commonVariable');" >				    
                            <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_prm_maincard" />
                        </select>
                    </td>
                </tr>
                <td class="table_edit_tr_td_label">票卡子类型：</td>
                <td class="table_edit_tr_td_input">
                    <select id="q_cardSubCode" name="q_cardSubCode" require="false" dataType="NotEmpty"  msg="票卡子类型不能为空" >				    
                        <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_none" />
                    </select>
                    <c:set var="pVarName" scope="request" value="commonVariable"/>
                    <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_prm_subcard" />
                </td>


                <td class="table_edit_tr_td_label" >线路:</td>
                <td class="table_edit_tr_td_input">
                    <select id="q_line_id" name="q_line_id" onChange="setSelectValues('queryOp', 'q_line_id', 'q_station_id', 'commonVariable1');"  >				    
                        <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_line" />
                    </select>
                </td>
                <td class="table_edit_tr_td_label"><div align="right">车站:</div></td>
                <td class="table_edit_tr_td_input">
                    <select id="q_station_id" name="q_station_id" >
                        <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_none" />
                    </select>
                    <c:set var="pVarName" scope="request" value="commonVariable1"/>
                    <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_station" />
                </td>


                <td class="table_edit_tr_td_label" rowspan="1">

                    <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all_init" />
                    <c:set var="query" scope="request" value="1"/>
                    <c:set var="addClickMethod" scope="request" value="setControlNames('queryOp','q_operator#q_apply_name#q_identityTd#q_app_business_type#q_beginTime#q_endTime#q_hdl_flag#q_cardMainCode#q_cardSubCode#q_line_id#q_station_id');setLineCardNames('queryOp','q_line_id','q_station_id','commonVariable1','q_cardMainCode','q_cardSubCode','commonVariable');"/>
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
                            <input type="checkbox" name="rectNoAll" id="rectNoAll" onclick="selectAllRecord('detailOp', 'rectNoAll', 'rectNo', 'clearStart', 0);setPageControl('detailOp');"/>
                        </td>	
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="1"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >流水号 </td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="2"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width:180px">姓名 </td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="3"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >性别</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="4"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >证件类型</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="5"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width:150px">证件号</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="6"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width:100px">证件有效期</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=true index="7"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width:100px">电话</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="8"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >传真</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="9"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width:400px"> 地址</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="10"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width:150px">申请时间</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="11"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >操作员</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="12"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >线路</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="13"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >车站</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="14"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >票卡主类型</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="15"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width:100px">票卡子类型</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="16"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >测试标志</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="17"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >处理结果</td>
                    </tr>
                </table>
            </div>
            <div id="clearStart"  class="divForTableBlockData" style="width: 200%;">
                <table class="table_list_block" id="DataTable" >

                    <c:forEach items="${ResultSet}" var="rs">
                        <!--class="listTableData" -->
                        <!--
                        setSelectValuesByRow('detailOp', 'd_stationID', 'commonVariable');
                        -->
                        <tr class="table_list_tr_data"  onMouseOver="overResultRow('detailOp', this);" 
                            onMouseOut="outResultRow('detailOp', this);" 
                            onclick="

                                              setSelectValuesByRowPropertyName('detailOp', 'd_cardSubId', 'commonVariable2', 'cardMainId');
                                              setSelectValuesByRowPropertyName('detailOp', 'd_stationId', 'commonVariable3', 'lineId');
                                              clickResultRow('detailOp', this, 'detail');
                                              setPageControl('detailOp');" 
                            id="${rs.reqNo}" cardMainId="${rs.cardMainId}" lineId="${rs.lineId}">

                            <td id="rectNo1" class="table_list_tr_col_data_block">
                                <input type="checkbox" name="rectNo" id="rectNo" onclick="unSelectAllRecord('detailOp', 'rectNoAll', 'rectNo');"
                                       value="${rs.reqNo}"  >

                                </input>
                            </td>
                            <td  id="reqNo" class="table_list_tr_col_data_block" >
                                ${rs.waterNo}

                            </td>
                            <td  id="applyName" class="table_list_tr_col_data_block" style="width:180px">
                                ${rs.applyName}

                            </td>
                            <td  id="applySex" class="table_list_tr_col_data_block" >
                                ${rs.applySex}

                            </td>
                            <td  id="identityType" class="table_list_tr_col_data_block" >
                                ${rs.identityType}
                            </td>
                            <td  id="identityId" class="table_list_tr_col_data_block" style="width:150px">
                                ${rs.identityId}

                            </td>
                            <td  id="strExpDate" class="table_list_tr_col_data_block" style="width:100px">
                                ${rs.strExpDate}
                            </td>
                            <td  id="telNo" class="table_list_tr_col_data_block" style="width:100px">
                                ${rs.telNo}
                            </td>
                            <td  id="fax" class="table_list_tr_col_data_block">
                                ${rs.fax}
                            </td>
                            <td  id="address" class="table_list_tr_col_data_block" style="width:400px">
                                ${rs.address}
                            </td>
                            <td  id="strAppDate" class="table_list_tr_col_data_block" style="width:150px">
                                ${rs.strAppDate}
                            </td>
                            <td  id="operatorId" class="table_list_tr_col_data_block" >
                                ${rs.operatorId}
                            </td>
                            <td  id="lineId" class="table_list_tr_col_data_block" >
                                ${rs.lineIdText}
                            </td>
                            <td  id="stationId" class="table_list_tr_col_data_block" >
                                ${rs.stationIdText}
                            </td>

                            <td  id="cardMainId" class="table_list_tr_col_data_block" >
                                ${rs.cardMainIdText}
                            </td>
                            <td  id="cardSubId" class="table_list_tr_col_data_block" style="width:100px">
                                ${rs.cardSubIdText}
                            </td>
                            <td  id="cardAppFlag" class="table_list_tr_col_data_block" >
                                ${rs.cardAppFlag}
                            </td>
                            <td  id="hdlFlag" class="table_list_tr_col_data_block" >
                                ${rs.hdlFlag}
                            </td>

                        </tr>

                    </c:forEach>

                </table>
            </div>
        </div>
        <c:set var="pTitleName" scope="request" value="明细"/>
        <c:set var="pTitleWidth" scope="request" value="50"/>
        <c:import url="/jsp/common/common_template.jsp?template_name=common_table_title" />

        <FORM method="post" name="detailOp" id="detailOp" action="ticketStorageSignCardApply" >
            
            <input type="hidden" name="expAllFields" id="d_expAllFields"
                   value="waterNo,applyName,applySex,identityType,identityId,strExpDate,telNo,fax,address,strAppDate,operatorId,lineIdText,stationIdText,cardMainIdText,cardSubIdText,cardAppFlag,hdlFlag" />
            <input type="hidden" name="expFileName" id="d_expFileName" value="记名卡申请.xlsx" />
            <input type="hidden" name="divId" id="d_divId" value="clearStartHead">
            <input type="hidden" name="methodUrl" id="d_methodUrl" value="/TicketStorageSignCardApplyExportAll"/>

            <c:set var="divideShow" scope="request" value="1"/>
            <c:import  url="/jsp/common/common_template_web_variable.jsp?template_name=common_web_variable" />

            <!--<DIV id="detail" align="center" class="divForTableData" style="position:relative; left:20; width:95%; height:10; layer-background-color:#99CC99; border:thin outset #ffffff; z-index:1;">-->
            <div id="detail"  class="divForTableDataDetail" >

                <table  class="table_edit_detail">
                    <tr class="table_edit_tr">
                    <input type='hidden' name="d_reqNo" id ="d_reqNo" />
                    <td class="table_edit_tr_td_label" >票卡主类型:</td>
                    <td class="table_edit_tr_td_input">
                        <select id="d_cardMainId" name="d_cardMainId" require="true" dataType="NotEmpty"  msg="票卡主类型不能为空" onChange="setSelectValues('detailOp', 'd_cardMainId', 'd_cardSubId', 'commonVariable2');" >				    
                            <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_prm_maincard" />
                        </select>
                    </td>
                    <td class="table_edit_tr_td_label">票卡子类型：</td>
                    <td class="table_edit_tr_td_input">
                        <select id="d_cardSubId" name="d_cardSubId" require="true" dataType="NotEmpty"  msg="票卡子类型不能为空" >				    
                            <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_none" />
                        </select>
                        <c:set var="pVarName" scope="request" value="commonVariable2"/>
                        <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_prm_subcard" />
                    </td>
                    <td class="table_edit_tr_td_label" >线路:</td>
                    <td class="table_edit_tr_td_input">
                        <select id="d_lineId" name="d_line_id" onChange="setSelectValues('detailOp', 'd_lineId', 'd_stationId', 'commonVariable3');"  >				    
                            <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_line" />
                        </select>
                    </td>
                    <td class="table_edit_tr_td_label"><div align="right">车站:</div></td>
                    <td class="table_edit_tr_td_input">
                        <select id="d_stationId" name="d_station_id" >
                            <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_none" />
                        </select>
                        <c:set var="pVarName" scope="request" value="commonVariable3"/>
                        <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_station" />
                    </td>
                    </tr>
                    <tr class="table_edit_tr">
                        <td class="table_edit_tr_td_label">姓名 </td>
                        <td class="table_edit_tr_td_input">
                            <input type="text" name="d_applyName" id="d_applyName" size="20" maxlength="10" require="true" dataType="NotEmpty" msg="请输入姓名，最长为10位。" />
                        </td>
                        <td class="table_edit_tr_td_label">
                            性别
                        </td>
                        <td class="table_edit_tr_td_input">
                            <select id="d_applySex" name="d_applySex" dataType="Require" msg="性别不能为空!" >
                                <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_sex" />
                            </select>

                        </td>
                        <td class="table_edit_tr_td_label" >证件类型</td>
                        <td class="table_edit_tr_td_input">
                            <select id="d_identityType" name="d_identityType" dataType="Require" msg="证件类型不能为空!" >
                                <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_crdType" />
                            </select>
                        </td>
                        <td class="table_edit_tr_td_label"><div align="right">证件号</div></td>
                        <td class="table_edit_tr_td_input">
                            <input type="text" name="d_identityId" id="d_identityId" size="18" maxlength="18" require="true" dataType="NumAndEng" msg="请输入正确的证件号码（若有字母，字母须大写）" />
                        </td>
                    </tr>
                    <tr class="table_edit_tr">
                        <td class="table_edit_tr_td_label">证件有效期 </td>
                        <td class="table_edit_tr_td_input">
                            <input type="text"
                                   name="d_strExpDate" id="d_strExpDate" value="" size="12"
                                   require="true" dataType="Date" format="ymd"
                                   msg="证件有效期时间格式为(yyyy-mm-dd)!" />
                            <a
                                href="javascript:openCalenderDialog(document.all.d_strExpDate);">
                                <img src="./images/calendar.gif"
                                     width="12" height="15" border="0" style="block" />
                            </a>
                        </td>
                        <td class="table_edit_tr_td_label">
                            电话
                        </td>
                        <td class="table_edit_tr_td_input">
                            <input type="text" name="d_telNo" id="d_telNo" size="16" maxlength="16" require="true" dataType="Number" msg="请输入正确的电话号码。" />

                        </td>
                        <td class="table_edit_tr_td_label" >传真</td>
                        <td class="table_edit_tr_td_input">
                            <input type="text" name="d_fax" id="d_fax" size="16" maxlength="16" require="false" dataType="Number" msg="请输入正确的传真号码。"/>
                        </td>
                        <td class="table_edit_tr_td_label"><div align="right">测试标志</div></td>
                        <td class="table_edit_tr_td_input">
                            <select id="d_cardAppFlag" name="d_cardAppFlag" dataType="Require" msg="测试标志不能为空!">
                                <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_testFlag" />
                            </select>
                        </td>
                    </tr>
                    <tr class="table_edit_tr">
                        <td class="table_edit_tr_td_label">地址 </td>
                        <td class="table_edit_tr_td_input">
                            <input type="text" name="d_address" id="d_address"  size="30" maxlength="100" require="true" dataType="String" msg="最长为200位(一个汉字为两位)" />
                        </td>
                        <td class="table_edit_tr_td_label">
                            申请时间
                        </td>
                        <td class="table_edit_tr_td_input">
                            <input type="text"
                                   name="d_strAppDate" id="d_strAppDate" value="" size="12"
                                   require="true" dataType="Date" format="ymd"
                                   msg="申请时间格式为(yyyy-mm-dd)!" />
                            <a
                                href="javascript:openCalenderDialog(document.all.d_strAppDate);">
                                <img src="./images/calendar.gif"
                                     width="12" height="15" border="0" style="block" />
                            </a>

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
            <c:set var="audit" scope="request" value="0"/>
            <c:set var="print" scope="request" value="1"/>
            <c:set var="import_1" scope="request" value="1"/>
            <c:set var="export" scope="request" value="1"/>
            <c:set var="expAll" scope="request" value="1"/>
            <c:set var="btNext" scope="request" value="1"/>
            <c:set var="btNextEnd" scope="request" value="1"/>
            <c:set var="btBack" scope="request" value="1"/>
            <c:set var="btBackEnd" scope="request" value="1"/>
            <c:set var="btGoPage" scope="request" value="1"/>

            <c:set var="addClickMethod1" scope="request" value="OpenDialog();"/>
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




