<%-- 
    Document   : stRptIndex
    Created on : 2017-9-19, 14:58:14
    Author     : liudz
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>中间表分表查询</title>


        <script language="javascript" type="text/javascript" charset="utf-8" src="js/common_form.js"></script>
        <script language="javascript" type="text/javascript" charset="utf-8" src="js/common_form_extend.js"></script>
        <script language="javascript" type="text/javascript" charset="utf-8" src="js/Validator.js"></script>
        <script language="JavaScript" type="text/javascript" charset="utf-8" src="js/CalendarPop.js"></script>  
        <link rel="stylesheet" type="text/css" href="css/frame/simple.css" title="Style"/>
    </head>

    <body onload="
            initDocument('queryOp', 'detail');
            initDocument('detailOp', 'detail');
            setControlsDefaultValue('queryOp');
            setListViewDefaultValue('detailOp', 'clearStart');
            setQueryControlsDefaultValue('queryOp', 'detailOp');

            setPageControl('detailOp');
            setTableRowBackgroundBlock('DataTable')">

        <!--<body >-->
        <table  class="table_title">
            <tr align="center" class="trTitle">
                <td colspan="4">清算系统  中间表分表查询
                </td>
            </tr>
        </table>

        <!-- 表头 通用模板 -->
        <c:set var="pTitleName" scope="request" value="查询"/>
        <c:set var="pTitleWidth" scope="request" value="50"/>
        <c:import  url="/jsp/common/common_template.jsp?template_name=common_table_title" />

        <form method="post" name="queryOp" id="queryOp" action="stRptIndex">
            <!-- 页面用到的变量 通用模板 -->

            <c:set var="divideShow" scope="request" value="1"/>
            <c:import  url="/jsp/common/common_template_web_variable.jsp?template_name=common_web_variable" />

            <table  class="table_edit">
                <tr class="table_edit_tr">
                    <td class="table_edit_tr_td_label">原表表名:</td>
                    <td class="table_edit_tr_td_input">
                        <input type="text" name="q_origin_table_name" id="q_origin_table_name" type="text" require="false" dataType="string"  />
                    </td>

                    <td class="table_edit_tr_td_label">目的表名:</td>
                    <td class="table_edit_tr_td_input">
                        <input type="text" name="q_table_name" id="q_table_name" type="text" require="false" dataType="string"  />
                    </td>
                    <td class="table_edit_tr_td_label">表类别:</td>
                        <td class="table_edit_tr_td_input">
                            <select id="q_table_type" name="q_table_type" style="width:140px">    
                                <option value="">=请选择=</option>
                                <option value="1">按运营日</option>
                                <option value="2">按清算日</option>
                                <option value="3">按主从表</option>
                                
                            </select>
                        </td>

                    <td class="table_edit_tr_td_label" rowspan="1">

                        <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all_init" />
                        <c:set var="query" scope="request" value="1"/>
                        <c:set var="addClickMethod" scope="request" value="setControlNames('queryOp','q_table_type#q_origin_table_name#q_table_name')"/>
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
        <div id="clearStartBlock" class="divForTableBlock" >
            <div id="clearStartHead" class="divForTableBlockHead" >
                <table class="table_list_block" id="DataTableHead" >

                    <!--说明：列的序号从0开始 isDigit：false 表示按字符串排序 ：true表示按数值排序 sortedby：asc表示按升序 dec表示按降序 -->
                    <tr  class="table_list_tr_head_block" id="ignore">
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="1"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 200px" >源表表名</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="2"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 200px">目的表名</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="3"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 120px">最小清算号</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="4"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 120px">最大清算号</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="5"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 150px">最小交易时间</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="6"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 150px">最大交易时间</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="7"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 150px">主表最小交易时间</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="8"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 150px">主表最大交易时间</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=true  index="9"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 100px">迁移数量</td>

                    </tr>


                </table>

            </div>
            <!-- <DIV id="clearStart" align="center" style="position:relative; left:20; width:95%; height:30%;min-height: 200px;max-height: 250px; layer-background-color:#99CC99; border:thin outset #ffffff; z-index:1; overflow: scroll;">-->
            <div id="clearStart"  class="divForTableBlockData" >
                <table class="table_list_block" id="DataTable" >

                    <c:forEach items="${ResultSet}" var="rs">
                        <!--class="listTableData" -->
                        <!--
                        setSelectValuesByRow('detailOp', 'd_stationID', 'commonVariable');
                        -->
                        <tr class="table_list_tr_data"  onMouseOver="overResultRow('detailOp', this);" 
                            onMouseOut="outResultRow('detailOp', this);" 
                            onclick="
                                    clickResultRow('detailOp', this, '');

                                    setPageControl('detailOp');" 
                            id="${rs.origin_table_name}#${rs.table_name}#${rs.begin_balance_no}#${rs.end_balance_no}#${rs.min_squad_day}#${max_squad_day}
                            #${begin_date} #${end_date} #${recd_count}" >


                            <td  id="origin_table_name" class="table_list_tr_col_data_block" style="width: 200px">
                                ${rs.origin_table_name}
                            </td>
                            <td  id="table_name" class="table_list_tr_col_data_block" style="width: 200px">
                                ${rs.table_name}                                
                            </td>
                            <td  id="begin_balance_no" class="table_list_tr_col_data_block" style="width: 120px">
                                ${rs.begin_balance_no}
                            </td>
                            <td  id="end_balance_no" class="table_list_tr_col_data_block" style="width: 120px">
                                ${rs.end_balance_no}
                            </td>
                            <td  id="min_squad_day" class="table_list_tr_col_data_block" style="width: 150px">
                                ${rs.min_squad_day}
                            </td>
                            <td  id="max_squad_day" class="table_list_tr_col_data_block" style="width: 150px">
                                ${rs.max_squad_day}
                            </td>
                            <td  id="begin_date" class="table_list_tr_col_data_block" style="width: 150px">
                                ${rs.begin_date}
                            </td>
                            <td  id="end_date" class="table_list_tr_col_data_block" style="width: 150px">
                                ${rs.end_date}
                            </td>
                            <td  id="recd_count" class="table_list_tr_col_data_block" style="width: 100px">
                                ${rs.recd_count}
                            </td>



                        </tr>

                    </c:forEach>

                </table>
            </div>
        </div>

        <!-- 表头 通用模板 -->
        <!-- 表头 通用模板 -->
        <c:set var="pTitleName" scope="request" value="明细"/>
        <c:set var="pTitleWidth" scope="request" value="50"/>   
        <c:import url="/jsp/common/common_template.jsp?template_name=common_table_title" />

        <FORM method="post" name="detailOp" id="detailOp" action="stRptIndex" >
            
            <input type="hidden" name="expAllFields" id="d_expAllFields"
                value="origin_table_name,table_name,begin_balance_no,end_balance_no,min_squad_day,max_squad_day,begin_date,end_date,recd_count" />
            <input type="hidden" name="expFileName" id="d_expFileName" value="中间表分表查询.xlsx" />
            <input type="hidden" name="divId" id="d_divId" value="clearStartHead">
            <input type="hidden" name="methodUrl" id="d_methodUrl" value="/StRptIndexExportAll"/>

            <c:set var="divideShow" scope="request" value="1"/>
            <c:import  url="/jsp/common/common_template_web_variable.jsp?template_name=common_web_variable" />

            <!--<DIV id="detail" align="center" class="divForTableData" style="position:relative; left:20; width:95%; height:10; layer-background-color:#99CC99; border:thin outset #ffffff; z-index:1;">-->
            <div id="detail"  class="divForTableDataDetail" >
                <table  class="table_edit_detail">                  
                </table>
            </div>






            <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all_init" />




            <c:set var="print" scope="request" value="1"/>
            <c:set var="export" scope="request" value="1"/>
            <c:set var="expAll" scope="request" value="1" />
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
        <c:set var="addQueryMethod" scope="request" value=""/>
        <c:set var="addClickMethod" scope="request" value=""/>
        -->

        
    </body>
</html>