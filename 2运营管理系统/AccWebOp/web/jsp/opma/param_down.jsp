<%-- 
    Document   : param_down
    Created on : 2017-6-19, 17:10:18
    Author     : liudezeng
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>参数下发</title>


        <script language="javascript" type="text/javascript" charset="utf-8" src="js/common_form.js"></script>
        <script language="javascript" type="text/javascript" charset="utf-8" src="js/Validator.js"></script>
        <script language="JavaScript" src="js/CalendarPop.js"></script>
        <link rel="stylesheet" type="text/css" href="css/frame/simple.css" title="Style"/>
    </head>
    <script type="text/javascript">
        function openLccLine(resluts, jspName, moduleID, width, height, id)
        {
            var obj = new Object();
            var parmTypeId = document.getElementById('parmTypeId').value;
            var verNum = document.getElementById('verNum').value;
            var verType = document.getElementById('verType').value;
            obj.value = resluts;
            var r = document.getElementsByName('rectNo');
            var path = jspName + "?ModuleID=" + moduleID + "&parmTypeId=" + parmTypeId + "&verNum=" + verNum + "&verType=" + verType;
            var rt = window.showModalDialog(path, obj, 'dialogWidth:' + width + 'px;dialogHeight:' + height + 'px;center:yes;resizable:no;status:no;scroll:yes');
            if (rt != null && rt != 'undefined'){
                for(var i = 0;i<r.length;i++){
                    if(r[i].checked){
                        
                        var a = r[i].value;
                        var b = a.replace("#","");
                        var c = "t"+b.replace("#","");
//                        if(document.getElementById(c).value == ""){
                document.getElementById(c).value = rt;
//            }
            } else{
            document.getElementById(id).value = rt;}
        
            }
        }

        }
    </script>

    <body onload="initDocument('queryOp', 'detail');
            initDocument('detailOp', 'detail');
            setControlsDefaultValue('queryOp');
            setListViewDefaultValue('detailOp', 'clearStart');
//                    setQueryControlsDefaultValue('queryOp', 'detailOp');
//                    setPageControl('detailOp'); 
            setTableRowBackgroundBlock('DataTable')">

        <table  class="table_title">
            <tr align="center" class="trTitle">
                <td colspan="4">参数 参数下发

                </td>
            </tr>
        </table>
        <!-- 表头 通用模板 -->

        <c:set var="pTitleName" scope="request" value="查询"/>
        <c:set var="pTitleWidth" scope="request" value="50"/>
        <c:import  url="/jsp/common/common_template.jsp?template_name=common_table_title" />

        <form method="post" name="queryOp" id="queryOp" action="ParamDown">
            <!-- 页面用到的变量 通用模板 -->

            <c:set var="divideShow" scope="request" value="1"/>
            <c:import  url="/jsp/common/common_template_web_variable.jsp?template_name=common_web_variable" />

            <table  class="table_edit">
                <tr class="table_edit_tr">
                    <td class="table_edit_tr_td_label">参数名称：</td>
                    <td class="table_edit_tr_td_input">
                        <input type="text" name="q_parm_type_name" id="q_parm_type_name" size="10"  />
                    </td>   
                    <td class="table_edit_tr_td_label">参数版本号:</td>
                    <td class="table_edit_tr_td_input">
                        <input type="text" name="q_version_no" id="q_version_no" size="10" />                     
                    </td>
                    
                    <td class="table_edit_tr_td_label" rowspan="2">

                        <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all_init" />
                        <c:set var="query" scope="request" value="1"/>
                        <c:set var="addClickMethod" scope="request" value="setControlNames('queryOp','q_parm_type_name#q_version_no');"/>
                        <c:set var="clickMethod" scope="request" value="btnClick('queryOp','clearStart','detail');"/>
                        <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all" />
                    </td>
                </tr>
                
            </table>

        </form>
                    <c:import url="/jsp/common/common_template.jsp?template_name=common_status_table" />
        <FORM method="post" name="detailOp" id="detailOp" action="ParamDown" > 
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
                            <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="1"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 120px">参数名称</td>
                            <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="2"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 120px">参数代码</td>
                            <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="3"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 120px">参数版本号</td>
                            <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="4"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 120px">版本说明</td>
                            <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=true index="5"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 150px">通知线路</td>
                        </tr>


                    </table>

                </div>
                <!-- <DIV id="clearStart" align="center" style="position:relative; left:20; width:95%; height:30%;min-height: 200px;max-height: 250px; layer-background-color:#99CC99; border:thin outset #ffffff; z-index:1; overflow: scroll;">-->
                <div id="clearStart"  class="divForTableBlockData">
                    <table class="table_list_block" id="DataTable" >

                        <c:forEach items="${ResultSet}" var="rs">
                            <!--class="listTableData" -->
                            <tr class="table_list_tr_data"  onMouseOver="overResultRow('detailOp', this);" 
                                onMouseOut="outResultRow('detailOp', this);" 
                                onclick="clickResultRow('detailOp', this, 'detail');" style="display:" id = "${rs.parm_type_id}${rs.version_no}${rs.record_flag}">

                                <td id="rectNo1" class="table_list_tr_col_data_block">
                                    <input type="checkbox" name="rectNo" onclick="unSelectAllRecord('detailOp', 'rectNoAll', 'rectNo');"
                                           value="${rs.parm_type_id}#${rs.version_no}#${rs.record_flag}">
                                    </input>
                                </td>

                                <td  id="parmTypeName" class="table_list_tr_col_data_block" style="width: 120px">
                                    ${rs.parm_type_name}
                                </td>
                                <td  id="parmTypeId" class="table_list_tr_col_data_block" style="width: 120px">
                                    ${rs.parm_type_id}
                                </td>
                                <td  id="verNum" class="table_list_tr_col_data_block" style="width: 120px">
                                    ${rs.version_no}
                                </td>
                                <td  id="verType" class="table_list_tr_col_data_block" style="width: 120px">
                                    ${rs.record_flag_name}
                                </td>
                                <!--                            <td   class="table_list_tr_col_data_block"  style="width: 150px" >
                                                            <select  require="true" multiple="true" size="2" id="t${rs.parm_type_id}${rs.version_no}${rs.record_flag}">
                                                                
                                                            </select>	 
                                                            </td>-->
                                <td class="table_list_tr_col_data_block" id="lcc_line_id"  style="width: 150px">
                                    <input type="text" readonly="true" id="t${rs.parm_type_id}${rs.version_no}${rs.record_flag}" name ="lcc_line_id${rs.parm_type_id}${rs.version_no}${rs.record_flag}"  require="true"
                                           onclick="openLccLine('detailOp', 'LccLineType?command=openwindow&parmTypeId=${rs.parm_type_id}&verNum=${rs.version_no}&verType=${rs.record_flag}&ModuleID=${ModuleID}', '0305', 600, 600, 't${rs.parm_type_id}${rs.version_no}${rs.record_flag}');" />

                                </td>

                            </tr>

                        </c:forEach>

                    </table>
                </div>
            </div>





            <c:set var="divideShow" scope="request" value="1"/>
            <c:import  url="/jsp/common/common_template_web_variable.jsp?template_name=common_web_variable" />
            <input type="hidden" name="allSelectedLccLines" id="allSelectedLccLines" value="" />
            <c:import url="/jsp/common/common_template.jsp?template_name=version_eval" />

            <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all_init" />
            <c:set var="download" scope="request" value="1"/>
            <c:set var="print" scope="request" value="1"/>
            <c:set var="export" scope="request" value="1"/>
            <c:set var="clickMethod" scope="request" value="btnClick('detailOp','clearStart','detail','','clearStartHead');"/>
            <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all" />
            <br/>
        </FORM>

        <!-- 状态栏 通用模板 -->

        
    </body>
</html>