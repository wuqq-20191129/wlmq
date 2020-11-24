<%-- 
    Document   : store
    Created on : 2017-6-12
    Author     : mh
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>商户定义</title>
        <script language="javascript" type="text/javascript" charset="utf-8" src="js/common_form.js"></script>
        <script language="javascript" type="text/javascript" charset="utf-8" src="js/Validator.js"></script>
        <script language="JavaScript" src="js/CalendarPop.js"></script>
        <link rel="stylesheet" type="text/css" href="css/frame/simple.css" title="Style"/>
    </head>
    <!--删除 hejj setInvisable('detailOp','clone');
    -->
    <body onload="initDocument('queryOp', 'detail');
            initDocument('detailOp', 'detail');
            setPrimaryKeys('detailOp', 'd_storeId');
            setControlsDefaultValue('queryOp');
            setQueryControlsDefaultValue('queryOp', 'detailOp');
            setListViewDefaultValue('detailOp', 'clearStart');
            setPageControl('detailOp');
            setTableRowBackgroundBlock('DataTable')"> 
        <table  class="table_title">
            <tr align="center" class="trTitle">
                <td colspan="4">商户定义</td>
            </tr>
        </table>

        <!-- 表头 通用模板 -->

        <c:set var="pTitleName" scope="request" value="查询"/>
        <c:set var="pTitleWidth" scope="request" value="50"/>
        <c:import  url="/jsp/common/common_template.jsp?template_name=common_table_title" />

        <form method="post" name="queryOp" id="queryOp" action="Store">
            <!-- 页面用到的变量 通用模板 -->
            <c:set var="divideShow" scope="request" value="1"/>
            <c:import  url="/jsp/common/common_template_web_variable.jsp?template_name=common_web_variable" />
            <table  class="table_edit">
                <tr class="table_edit_tr">
                    <td class="table_edit_tr_td_label">商户ID：</td>
                    <td class="table_edit_tr_td_input">
                        <input type="text" name="q_storeId" id="q_storeId" size="10"  maxLength="5" require="false" dataType="LimitB" min="5" max="5" msg="商户ID应为5位"/>
                    </td>   
                    <td class="table_edit_tr_td_label">商户名称:</td>
                    <td class="table_edit_tr_td_input">
                        <input type="text" name="q_storeName" id="q_storeName" size="10" maxLength="50" msg="商户名称应小于50位"/>                     
                    </td>

                    <td class="table_edit_tr_td_label" rowspan="2">
                        <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all_init" />
                        <c:set var="query" scope="request" value="1"/>
                        <c:set var="addClickMethod" scope="request" value="setControlNames('queryOp','q_storeId#q_storeName');"/>
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
                            <input type="checkbox" name="rectNoAll" id="rectNoAll" onclick="selectAllRecord('detailOp', 'rectNoAll', 'rectNo', 'clearStart', 0);"/>
                        </td>	
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="1"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >商户ID</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="2"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >商户名称</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="3"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >联系人</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="4"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width:100px;">电话</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=true index="5"   sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width:100px;">传真</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="6"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >终端编号</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="7"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width:150px;">地址</td>
                    </tr>
                </table>

            </div>
            <div id="clearStart"  class="divForTableBlockData">
                <table class="table_list_block" id="DataTable" >
                    <c:forEach items="${ResultSet}" var="rs">
                        <!--class="listTableData" -->
                        <!--删除 hejj setSelectValuesByRow('detailOp', 'd_cardLogicalId', 'commonVariable'); -->
                        <tr class="table_list_tr_data"  onMouseOver="overResultRow('detailOp', this);" 
                            onMouseOut="outResultRow('detailOp', this);" 
                            onclick="clickResultRow('detailOp', this, 'detail');
                                    setPageControl('detailOp');" 
                            id="${rs.store_id}#${rs.record_flag}">

                            <td id="rectNo1" class="table_list_tr_col_data_block">
                                <input type="checkbox" name="rectNo" onclick="unSelectAllRecord('detailOp', 'rectNoAll', 'rectNo');"
                                       value="${rs.store_id}">
                                </input>
                            </td>
                            <td  id="storeId" class="table_list_tr_col_data_block">
                                ${rs.store_id}
                            </td>
                            <td  id="storeName" class="table_list_tr_col_data_block">
                                ${rs.store_name}
                            </td>
                            <td  id="linkMan" class="table_list_tr_col_data_block">
                                ${rs.link_man}
                            </td>
                            <td  id="tel" class="table_list_tr_col_data_block" style="width:100px;">
                                ${rs.tel}
                            </td>
                            <td  id="fax" class="table_list_tr_col_data_block" style="width:100px;">
                                ${rs.fax}
                            </td>
                            <td  id="terminalNo" class="table_list_tr_col_data_block">
                                ${rs.terminal_no}
                            </td>
                            <td  id="address" class="table_list_tr_col_data_block" style="width:150px;">
                                ${rs.address}
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

        <FORM method="post" name="detailOp" id="detailOp" action="Store">
            <c:set var="divideShow" scope="request" value="1"/>
            <c:import  url="/jsp/common/common_template_web_variable.jsp?template_name=common_web_variable" />
            <input type="hidden" name="expAllFields" id="d_expAllFields" value="store_id,store_name,link_man,tel,fax,terminal_no,address" />
            <input type="hidden" name="expFileName" id="d_expFileName" value="商户定义.xlsx" />
            <input type="hidden" name="divId" id="d_divId" value="clearStartHead" />
            <input type="hidden" name="methodUrl" id="d_methodUrl" value="/StoreExportAll" />
            <DIV id="detail"  class="divForTableDataDetail" >
                <table  class="table_edit_detail">
                    <tr class="table_edit_tr">
                        <td class="table_edit_tr_td_label">商户ID:</td>
                        <td class="table_edit_tr_td_input">
                            <input type="text" name="d_storeId" id="d_storeId" size="10" maxLength="5" require="true" dataType="Number|LimitB" min="5" max="5" msg="商户ID应为5位数字"/>
                        </td>
                        <td class="table_edit_tr_td_label">商户名称:</td>
                        <td class="table_edit_tr_td_input">
                            <input type="text" name="d_storeName" id="d_storeName" size="10" maxLength="50" require="true" dataType="NotEmpty"  msg="商户名称不能为空" />
                        </td>
                        <td class="table_edit_tr_td_label">联系人:</td>
                        <td class="table_edit_tr_td_input">
                            <input type="text" name="d_linkMan" id="d_linkMan" size="10" maxLength="20" require="true" dataType="NotEmpty" msg="联系人不能为空！"/>
                        </td>
                    </tr>

                    <tr class="table_edit_tr">
                        <td class="table_edit_tr_td_label">电话:</td>
                        <td class="table_edit_tr_td_input">
                            <input type="text" name="d_tel" id="d_tel" size="10" require="false"  maxlength="15"   dataType="Phone" msg="电话号码输入错误！" />
                        </td>
                        <td class="table_edit_tr_td_label">传真:</td>
                        <td class="table_edit_tr_td_input">
                            <input type="text" name="d_fax" id="d_fax" size="10" require="false"  maxlength="15"   dataType="Phone" msg="传真号码输入错误！"/>
                        </td>
                        <td class="table_edit_tr_td_label">终端编号:</td>
                        <td class="table_edit_tr_td_input">
                            <input type="text" name="d_terminalNo" id="d_terminalNo" size="10" maxlength="4" require="false"  dataType="integer" msg="终端编号为1--4位数字！"/>
                        </td>
                    </tr>

                    <tr class="table_edit_tr">
                        <td class="table_edit_tr_td_label">地址:</td>
                        <td class="table_edit_tr_td_input">
                            <input type="text" name="d_address" id="d_address" size="15" />
                        </td>
                    </tr>
                </table>
            </DIV>

            <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all_init" />
            <c:set var="add" scope="request" value="1"/>
            <c:set var="del" scope="request" value="1"/>
            <c:set var="modify" scope="request" value="1"/>
            <c:set var="save" scope="request" value="1"/>
            <c:set var="cancle" scope="request" value="1"/>
            <c:set var="print" scope="request" value="1"/>
            <c:set var="export" scope="request" value="1"/>
            <c:set var="expAll" scope="request" value="1" />
            <c:set var="btNext" scope="request" value="1"/>
            <c:set var="btNextEnd" scope="request" value="1"/>
            <c:set var="btBack" scope="request" value="1"/>
            <c:set var="btBackEnd" scope="request" value="1"/>
            <c:set var="btGoPage" scope="request" value="1"/>
            <c:set var="addClickMethod" scope="request" value="setUpdatePrimaryKey('detailOp','d_storeId');"/>
            <c:set var="clickMethod" scope="request" value="btnClick('detailOp','clearStart','detail','','clearStartHead');"/>
            <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all" />
            <br/>
        </FORM>
        
    </body>
</html>
