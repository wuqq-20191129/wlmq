<%-- 
    Document   : ticketStorageLineStockWarnPara
    Created on : 2017-10-31, 17:15:43
    Author     : ysw
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>线路库存预警参数</title>


        <script language="javascript" type="text/javascript" charset="utf-8" src="js/common_form.js"></script>
        <script language="javascript" type="text/javascript" charset="utf-8" src="js/common_form_extend.js"></script>
        <script language="javascript" type="text/javascript" charset="utf-8" src="js/Validator.js"></script>
        <script language="JavaScript" type="text/javascript" charset="utf-8" src="js/CalendarPop.js"></script>
        <script language="JavaScript" type="text/javascript" charset="utf-8" src="js/icProduceOut.js"></script>
        <script language="JavaScript" type="text/javascript" charset="utf-8" src="js/icStorageProductBill.js"></script>
        <link rel="stylesheet" type="text/css" href="css/frame/simple.css" title="Style"/>
    </head>
    <body onload="initDocument('queryOp', 'detail');
            initDocument('detailOp', 'detail');
            setControlsDefaultValue('queryOp');
            setPrimaryKeys('detailOp', 'd_lineId#d_mainType#d_subType');
            setListViewDefaultValue('detailOp', 'clearStart');
            setQueryControlsDefaultValue('queryOp', 'detailOp');
            setPageControl('detailOp');
            setTableRowBackgroundBlock('DataTable')">

        <table  class="table_title">
            <tr align="center" class="trTitle">
                <td colspan="4">线路库存预警参数</td>
            </tr>
        </table>

        <!-- 表头 通用模板 -->

        <c:set var="pTitleName" scope="request" value="查询"/>
        <c:set var="pTitleWidth" scope="request" value="50"/>
        <c:import  url="/jsp/common/common_template.jsp?template_name=common_table_title" />

        <form method="post" name="queryOp" id="queryOp" action="ticketStorageLineStockParam">
            <!-- 页面用到的变量 通用模板 -->

            <c:set var="divideShow" scope="request" value="1"/>
            <c:import  url="/jsp/common/common_template_web_variable.jsp?template_name=common_web_variable" />

            <table  class="table_edit">
                <tr class="table_edit_tr">
                    <td class="table_edit_tr_td_label" >线路:</td>
                    <td class="table_edit_tr_td_input">
                        <select id="q_lineId" name="q_lineId" >				    
                            <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_afcLine" />
                        </select>
                    </td>
                    <td class="table_edit_tr_td_label" >票卡主类型:</td>
                    <td class="table_edit_tr_td_input">
                        <select id="q_mainType" name="q_mainType"
                                onChange="setSelectValues('queryOp', 'q_mainType', 'q_subType', 'commonVariable');"  >				    
                            <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_prm_maincard" />
                        </select>
                    </td>
                    <td class="table_edit_tr_td_label"><div align="right">票卡子类型:</div></td>
                    <td class="table_edit_tr_td_input">
                        <select id="q_subType" name="q_subType" >
                            <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_cardSub_none" />
                        </select>
                        <c:set var="pVarName" scope="request" value="commonVariable"/>
                        <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_prm_subcard" />
                    </td>
                    
                    <td class="table_edit_tr_td_label" rowspan="1">
                        <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all_init" />
                        <c:set var="query" scope="request" value="1"/>
                        <c:set var="addClickMethod" scope="request" value="setControlNames('queryOp','q_lineId#q_mainType#q_subType');setLineCardNames('queryOp','','','','q_mainType','q_subType','commonVariable');"/>
                        <c:set var="clickMethod" scope="request" value="btnClick('queryOp','clearStart','detail');"/>
                        <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all" />
                    </td>
                </tr>
            </table>
        </form>
                    
                    <!-- 状态栏 通用模板 -->

        <c:import url="/jsp/common/common_template.jsp?template_name=common_status_table" />

        <!-- 表头 通用模板 -->
        <c:set var="pTitleName" scope="request" value="列表"/>
        <c:set var="pTitleWidth" scope="request" value="50"/>
        <c:import url="/jsp/common/common_template.jsp?template_name=common_table_title" />
        <div id="clearStartBlock" class="divForTableBlock">
            <div id="clearStartHead" class="divForTableBlockHead">
                <table class="table_list_block" id="DataTableHead" >
                    <!--说明：列的序号从0开始 isDigit：false 表示按字符串排序 ：true表示按数值排序 sortedby：asc表示按升序 dec表示按降序 -->
                    <tr  class="table_list_tr_head_block" id="ignore">
                        <td   class="table_list_tr_col_head_block">
                            <input type="checkbox" name="rectNoAll" id="rectNoAll" onclick="selectAllRecord('detailOp', 'rectNoAll', 'rectNo', 'clearStart', 0);setPageControl('detailOp');"/>
                        </td>	
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="0"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 150px">线路</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="1"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 150px">票卡主类型</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="2"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 150px">票卡子类型</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="3"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 150px">安全库存上限</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="4"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 150px">安全库存下限</td>                      
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="5"  sortedby="asc"  onclick="sortForTableBlock('clearStart');"style="width: 200px">备注</td>
                    </tr>
                </table>
            </div>
            <!-- <DIV id="clearStart" align="center" style="position:relative; left:20; width:95%; height:30%;min-height: 200px;max-height: 250px; layer-background-color:#99CC99; border:thin outset #ffffff; z-index:1; overflow: scroll;">-->
            <div id="clearStart"  class="divForTableBlockData">
                <table class="table_list_block" id="DataTable" >

                    <c:forEach items="${ResultSet}" var="rs">
                        <!--class="listTableData" -->
                        <!--
                        setSelectValuesByRow('detailOp', 'd_stationID', 'commonVariable');
                        -->
                        <tr class="table_list_tr_data"  onMouseOver="overResultRow('detailOp', this);" 
                            onMouseOut="outResultRow('detailOp', this);" 
                            onclick=" setSelectValuesByRowPropertyName('detailOp', 'd_subType', 'commonVariable', 'idMainType');
                                    clickResultRow('detailOp', this, 'detail');
                                    setPageControl('detailOp');" 
                            id="${rs.lineId}#${rs.icMainType}#${rs.icSubType}"
                            idMainType="${rs.icMainType}">

                            <td id="rectNo1" class="table_list_tr_col_data_block">
                                <input type="checkbox" name="rectNo" id="rectNo" onclick="unSelectAllRecord('detailOp', 'rectNoAll', 'rectNo');"
                                       value="${rs.lineId}#${rs.icMainType}#${rs.icSubType}"  >
                                </input>
                            </td>

                            <td  id="lineId" class="table_list_tr_col_data_block" style="width: 150px">
                                ${rs.lineName}
                            </td>
                            <td  id="mainType" class="table_list_tr_col_data_block" style="width: 150px">
                                ${rs.mainTypeName}
                            </td>
                            <td  id="subType" class="table_list_tr_col_data_block" style="width: 150px">
                                ${rs.subTypeName}
                            </td>
                            <td  id="upperThresh" class="table_list_tr_col_data_block" style="width: 150px">
                                ${rs.upperThresh}
                            </td>
                            <td  id="lowerThresh" class="table_list_tr_col_data_block" style="width: 150px">
                                ${rs.lowerThresh}
                            </td>
                            <td  id="remark" class="table_list_tr_col_data_block" style="width: 200px">
                                ${rs.remark}
                            </td>

                        </tr>

                    </c:forEach>

                </table>
            </div>
        </div>

        <!-- 表头 通用模板 -->
        <c:set var="pTitleName" scope="request" value="明细"/>
        <c:set var="pTitleWidth" scope="request" value="50"/>
        <c:import url="/jsp/common/common_template.jsp?template_name=common_table_title" />

        <FORM method="post" name="detailOp" id="detailOp" action="ticketStorageLineStockParam" >
            
            <input type="hidden" name="expAllFields" id="d_expAllFields"
                   value="lineName,mainTypeName,subTypeName,upperThresh,lowerThresh,remark" />
            <input type="hidden" name="expFileName" id="d_expFileName" value="线路库存预警参数.xlsx" />
            <input type="hidden" name="divId" id="d_divId" value="clearStartHead">
            <input type="hidden" name="methodUrl" id="d_methodUrl" value="/TicketStorageLineStockParamExportAll"/>

            <c:set var="divideShow" scope="request" value="1"/>
            <c:import  url="/jsp/common/common_template_web_variable.jsp?template_name=common_web_variable" />

            <!--<DIV id="detail" align="center" class="divForTableData" style="position:relative; left:20; width:95%; height:10; layer-background-color:#99CC99; border:thin outset #ffffff; z-index:1;">-->
            <div id="detail"  class="divForTableDataDetail" >
                <table  class="table_edit_detail">
                    <tr class="table_edit_tr">
                        <td class="table_edit_tr_td_label">线路: </td>
                        <td class="table_edit_tr_td_input">
                            <select id="d_lineId" name="d_lineId" require="true" dataType="NotEmpty" msg="线路不能为空!">
                                <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_afcLine" />
                            </select>	
                        </td>
                        <td class="table_edit_tr_td_label">票卡主类型: </td>
                        <td class="table_edit_tr_td_input">
                            <select id="d_mainType" name="d_mainType" onChange="setSelectValues('detailOp', 'd_mainType', 'd_subType', 'commonVariable');"require="true" dataType="NotEmpty" msg="票卡主类型不能为空!">
                                <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_prm_maincard" />
                            </select>	
                        </td>
                        <td class="table_edit_tr_td_label">票卡子类型: </td>
                        <td class="table_edit_tr_td_input">
                            <select id="d_subType" name="d_subType" require="true" dataType="NotEmpty"  msg="票卡子类型不能为空！">
                                <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_cardSub_none" />
                            </select>
                            <c:set var="pVarName" scope="request" value="commonVariable"/>
                            <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_prm_subcard" />
                        </td>

                        <td class="table_edit_tr_td_label">安全库存上限:</td>
                        <td class="table_edit_tr_td_input">
                            <input type="text" name="d_upperThresh" id="d_upperThresh"  size="10" require="true"  maxlength="10"
                                   dataType="integer" msg="安全库存上限应为非负整数！"/>
                        </td>
                    </tr>
                    <tr class="table_edit_tr">
                        <td class="table_edit_tr_td_label">安全库存下限:</td>
                        <td class="table_edit_tr_td_input">
                            <input type="text" name="d_lowerThresh" id="d_lowerThresh" size="10" require="true"  maxlength="10" 
                                   dataType="integer|CompareNum" operator= "LessThan" to="d_upperThresh" msg="安全库存下限应为非负整数且小于安全库存上限！"/>
                        </td>
                        <td class="table_edit_tr_td_label">备注:</td>
                        <td class="table_edit_tr_td_input">
                            <input type="text" name="d_remark" id="d_remark" size="30" require="false" maxlength="256" dataType="LimitContainChinese" min="1" max="256" msg="备注最大录入长度为256个字节，一个中文字是3个字节"/>
                        </td>

                    </tr>

                </table>
            </div>

            <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all_init" />
            <c:set var="add" scope="request" value="1"/>
            <c:set var="modify" scope="request" value="1"/>
            <c:set var="del" scope="request" value="1"/>
            <c:set var="save" scope="request" value="1"/>
            <c:set var="cancle" scope="request" value="1"/>
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
        <c:set var="addQueryMethod" scope="request" value="setLineCardNames('detailOp','d_mainType','d_subType','commonVariable','','','');"/>
        <c:set var="addClickMethod" scope="request" value="setUpdatePrimaryKey('detailOp','d_lineId#d_mainType#d_subType');"/>
        -->

        
    </body>
</html>
