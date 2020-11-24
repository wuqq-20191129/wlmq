<%-- 
    Document   : report_qry_cfg
    Created on : 2017-7-6
    Author     : mqf
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>报表查询配置</title>


        <script language="javascript" type="text/javascript" charset="utf-8" src="js/common_form.js"></script>
        <script language="javascript" type="text/javascript" charset="utf-8" src="js/Validator.js"></script>
        <script language="JavaScript" src="js/CalendarPop.js"></script>
        <script language="javascript">
            function setDetailFormQryConText(formName){
//                var qryConCodeString =document.getElementById('d_qryConCodeString').value;
//                var qryConPosString =document.getElementById('d_qryConPosString').value;
                var objQryConCodeString = document.forms[formName].getElementsByTagName("input")["d_qryConCodeString"];
                var objQryConPosString = document.forms[formName].getElementsByTagName("input")["d_qryConPosString"];
                var qryConCodeString = objQryConCodeString.value;
                var qryConPosString = objQryConPosString.value;
                
                var qryConCodeArray = [];
                var idxCode = qryConCodeString.indexOf(";");
                if (idxCode == -1)
                    qryConCodeArray[0] = qryConCodeString;
                else
                    qryConCodeArray = qryConCodeString.split(";");
                
                var qryConPosArray = [];
                var idxPos = qryConPosString.indexOf(";");
                if (idxPos == -1)
                    qryConPosArray[0] = qryConPosString;
                else
                    qryConPosArray = qryConPosString.split(";");
                
                var code = "";
                var pos = "";
                var controlText = "";
                for (i = 0; i < qryConCodeArray.length; i++) {
                    code = qryConCodeArray[i];
                    //
//                    if (qryConPosArray[i])
                    pos = qryConPosArray[i];
                    setDetailFormQryConValue(formName,code,pos);
                }
            }
            
            function setDetailFormQryConValue(formName,code,pos) {
//                var objCode = document.getElementById("d_" + code);//明细checkbox d_
//                var objPos = document.getElementById("d_" + code +"_pos");//明细输入框
                var objCode = document.forms[formName].getElementsByTagName("input")["d_" + code];
                var objPos = document.forms[formName].getElementsByTagName("input")["d_" + code +"_pos"];
                    if (objCode != null && objCode != "undefined" && objCode.type == "checkbox") {
                       objCode.checked = "true";
                    }
                        
                    if (objPos != null && objPos != "undefined" && objPos.type == "text") {
                        objPos.value = pos;
                    }
            }
            
            function setSelectValuesForText(formName, selectedName, selectName, varName) {
//	     alert("formName="+formName);
//	      alert("varName="+varName);
//	      alert("varValues="+document.forms[formName].children[varName].value);

                var cmdV = document.forms[formName].getElementsByTagName("input")[varName];
                var sltd = document.forms[formName].getElementsByTagName("select")[selectedName];
                var sltText = document.forms[formName].getElementsByTagName("input")[selectName];
                var code = sltd.value;
                
                if (cmdV.value == null || cmdV.value == '')
                    return;
                if (cmdV.value.indexOf(":") == -1)
                    return;
                //        alert("cmdV.value="+cmdV.value);
                //        alert("slt.options.length="+slt.options.length);
                var values = cmdV.value.split(":");
                var count = 0;
                var i = -1;
                sltText.value = "";
                for (i = 0; i < values.length; i++) {
                    if (values[i].indexOf(",") == -1)
                        continue;
                    var objs = values[i].split(",");
                    if (objs[0] == code) {
                        sltText.value = objs[1];
                        count++;
                    }

                }

                if (count == 0 && sltText.value.length > 1) {
                    sltText.value = "";
                }

            }
            
            
          
        </script>
        <link rel="stylesheet" type="text/css" href="css/frame/simple.css" title="Style"/>
    </head>
        <body onload="initDocument('queryOp', 'detail');
                initDocument('detailOp', 'detail');
//                setInvisable('detailOp', 'add;modify;del;submit1');
                setPrimaryKeys('detailOp', 'd_report_module');
                setControlsDefaultValue('queryOp');
                setListViewDefaultValue('detailOp', 'clearStart');
                setQueryControlsDefaultValue('queryOp', 'detailOp');
                setPageControl('detailOp');
                setTableRowBackgroundBlock('DataTable')">
        <!--<body >-->
        <table  class="table_title">
            <tr align="center" class="trTitle">
                <td colspan="4">报表查询配置
                </td>
            </tr>
        </table>


        <!-- 表头 通用模板 -->

        <c:set var="pTitleName" scope="request" value="查询"/>
        <c:set var="pTitleWidth" scope="request" value="50"/>
        <c:import  url="/jsp/common/common_template.jsp?template_name=common_table_title" />

        <form method="post" name="queryOp" id="queryOp" action="ReportQryCfg">
            <!-- 页面用到的变量 通用模板 -->

            <c:set var="divideShow" scope="request" value="1"/>
            <c:import  url="/jsp/common/common_template_web_variable.jsp?template_name=common_web_variable" />

            <table  class="table_edit">
                <tr class="table_edit_tr">
                    <td class="table_edit_tr_td_label">报表模板号:</td>
                    <td class="table_edit_tr_td_input">
                        <input type="text" name="q_report_module" id="q_report_module" style="width:100px" maxLength="6" require="false" />
                    </td>    

                    <td class="table_edit_tr_td_label">报表名称:</td>
                    <td class="table_edit_tr_td_input">
                        <input type="text" name="q_report_name" id="q_report_name" require="false" maxLength="20" style="width:300px" />                     
                    </td>
                    <td class="table_edit_tr_td_label" rowspan="2">

                        <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all_init" />
                        <c:set var="query" scope="request" value="1"/>
                        <c:set var="addClickMethod" scope="request" value="setControlNames('queryOp','q_report_module#q_report_name');"/>
                        <c:set var="clickMethod" scope="request" value="btnClick('queryOp','clearStart','detail');"/>
                        <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all" />
                    </td>
                </tr>
            </table>

        </form>

        <!-- 表头 通用模板 -->
        <c:set var="pTitleName" scope="request" value="列表"/>
        <c:set var="pTitleWidth" scope="request" value="50"/>
        <c:import url="/jsp/common/common_template.jsp?template_name=common_table_title" />
        <div id="clearStartBlock" class="divForTableBlock">
            <div id="clearStartHead" class="divForTableBlockHead">
                <table id="DataTableHead" class="table_list_block">

                    <!--说明：列的序号从0开始 isDigit：false 表示按字符串排序 ：true表示按数值排序 sortedby：asc表示按升序 dec表示按降序 -->
                    <tr  class="table_list_tr_head_block" id="ignore">
                        <td   class="table_list_tr_col_head_block">
                            <input type="checkbox" name="rectNoAll" id="rectNoAll" onclick="selectAllRecord('detailOp', 'rectNoAll', 'rectNo', 'clearStart', 0);"/>
                        </td>	
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="1"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >报表模板号</td>
                        <td id="orderTd"    style="width:300px" class="table_list_tr_col_head_block"  isDigit=false index="2"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >报表名称</td>
                        <td id="orderTd"    style="width:200px" class="table_list_tr_col_head_block"  isDigit=false index="3"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >查询条件代码</td>
                        <td id="orderTd"    style="width:200px" class="table_list_tr_col_head_block"  isDigit=false index="4"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >查询条件位置</td>
                        
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
                            onclick="clickResultRow('detailOp', this, 'detail');setDetailFormQryConText('detailOp');setPageControl('detailOp');" 
                            id="${rs.report_module}">

                            <td id="rectNo" class="table_list_tr_col_data_block">
                                <input type="checkbox" name="rectNo" onclick="unSelectAllRecord('detailOp', 'rectNoAll', 'rectNo');"
                                       value="${rs.report_module}">

                                </input>
                            </td>
                            <td  id="report_module" class="table_list_tr_col_data_block">
                                ${rs.report_module}
                            </td>
                            <td  id="report_name" style="width:300px" class="table_list_tr_col_data_block">
                                ${rs.report_name}
                            </td>
                            <td  id="qryConCodeString" style="width:200px" class="table_list_tr_col_data_block">
                                ${rs.qryConCodeString}
                            </td>
                            <td  id="qryConPosString" style="width:200px" class="table_list_tr_col_data_block">
                                ${rs.qryConPosString}
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

        <FORM method="post" name="detailOp" id="detailOp" action="ReportQryCfg" >

            <c:set var="divideShow" scope="request" value="1"/>
            <c:import  url="/jsp/common/common_template_web_variable.jsp?template_name=common_web_variable" />

            <DIV id="detail"  class="divForTableDataDetail" >
                <table  class="table_edit_detail">
                    <tr class="table_edit_tr">
                        
                        <input type="hidden" id="d_qryConCodeString" name="d_qryConCodeString"/>
                        
                        <input type="hidden" id="d_qryConPosString" name="d_qryConPosString"/>
                        
                        <td class="table_edit_tr_td_label">查询条件代码:</td>
                        <td class="table_edit_tr_td_input">(位置格式[x,y],x:行,y:列)</td>
                        
                        
                        <td class="table_edit_tr_td_label">报表模板号: </td>
                        <td class="table_edit_tr_td_input">
                            <select id="d_report_module" name="d_report_module" onChange="setSelectValuesForText('detailOp','d_report_module','d_report_name','commonVariable');"
                                    require="true" dataType="LimitB" min="1"  msg="没有选择报表模板号" style="width:140px">				    
                                <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_reportModule" />
                            </select>
                        </td>
                        
                        <td class="table_edit_tr_td_label">报表名称: </td>
                        <td class="table_edit_tr_td_input">
                            <input type="text" name="d_report_name" id="d_report_name" disabled= "true" maxLength="20" style="width:300px" />
                            
                            <c:set var="pVarName" scope="request" value="commonVariable"/>
                            <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_reportModuleName" />
                        </td>
                        
                    </tr>
                    
                    <tr class="table_edit_tr">
                        
                        
                        
                        <td class="table_edit_tr_td_label">
                            年<input type="checkbox" name="d_year" id="d_year"  value="0" ></input>
                        </td>
                        <td class="table_edit_tr_td_input">
                            位置:
                            <input type="text" name="d_year_pos" id="d_year_pos" size="3" maxLength="3" dataType="rowAndCol" require="false" msg="请输入正确的格式[x,y]"/>
                            <!--<input type="text" name="d_year_pos" id="d_year_pos" maxLength="3" require="false" dataType="rowAndCol" min="3" max="3" msg="请输入正确的格式[x,y]"/>-->
                            <!--<input type="text" name="d_year_pos" id="d_year_pos" size="3" maxLength="5"  require="false" dataType="rowAndCol"  msg="请输入正确的格式[x,y]"/>-->
                        </td>
                        
                        <td class="table_edit_tr_td_label">
                            月<input type="checkbox" name="d_month" id="d_month" value="0" ></input>
                        </td>
                         <td class="table_edit_tr_td_input">
                            位置:
                            <input type="text" name="d_month_pos" id="d_month_pos" size="3" maxLength="3" dataType="rowAndCol" require="false" msg="请输入正确的格式[x,y]" />
                        </td>
                        
                        <td class="table_edit_tr_td_label">
                            统计日期(前一天)<input type="checkbox" name="d_date" id="d_date" value="0" ></input>
                        </td>
                        <td class="table_edit_tr_td_input">
                            位置:
                            <input type="text" name="d_date_pos" id="d_date_pos" size="3" maxLength="3" dataType="rowAndCol" require="false" msg="请输入正确的格式[x,y]" />
                        </td>
                    </tr>
                    
                    <tr class="table_edit_tr">
                        <td class="table_edit_tr_td_label">
                            统计日期(当天)<input type="checkbox" name="d_dateCur" id="d_dateCur" value="0" ></input>
                        </td>
                        <td class="table_edit_tr_td_input">
                            位置:
                            <input type="text" name="d_dateCur_pos" id="d_dateCur_pos" size="3" maxLength="3" dataType="rowAndCol" require="false" msg="请输入正确的格式[x,y]" />
                        </td>
                            
                        <td class="table_edit_tr_td_label">
                            线路<input type="checkbox" name="d_lineID" id="d_lineID" value="0" ></input>
                        </td>
                        <td class="table_edit_tr_td_input">
                            位置:
                            <input type="text" name="d_lineID_pos" id="d_lineID_pos" size="3" maxLength="3" dataType="rowAndCol" require="false" msg="请输入正确的格式[x,y]" />
                        </td>
                        
                        <td class="table_edit_tr_td_label">
                            大线路<input type="checkbox" name="d_lineIDLarge" id="d_lineIDLarge" value="0" ></input>
                        </td>
                        <td class="table_edit_tr_td_input">
                            位置:
                            <input type="text" name="d_lineIDLarge_pos" id="d_lineIDLarge_pos" size="3" maxLength="3" dataType="rowAndCol" require="false" msg="请输入正确的格式[x,y]" />
                        </td>
                    </tr>
                    <tr class="table_edit_tr">
                        <td class="table_edit_tr_td_label">
                            票卡主类型<input type="checkbox" name="d_mainType" id="d_mainType" value="0" ></input>
                        </td>
                        <td class="table_edit_tr_td_input">
                            位置:
                            <input type="text" name="d_mainType_pos" id="d_mainType_pos" size="3" maxLength="3" dataType="rowAndCol" require="false" msg="请输入正确的格式[x,y]" />
                        </td>
                        <td class="table_edit_tr_td_label">
                            票卡子类型<input type="checkbox" name="d_subType" id="d_subType" value="0" ></input>
                        </td>
                        <td class="table_edit_tr_td_input">
                            位置:
                            <input type="text" name="d_subType_pos" id="d_subType_pos" size="3" maxLength="3" dataType="rowAndCol" require="false" msg="请输入正确的格式[x,y]" />
                        </td>
                        
                        <td class="table_edit_tr_td_label">
                            按清算日<input type="checkbox" name="d_isBalanceDate" id="d_isBalanceDate" value="0" checked="true" require="true" msg="查询条件[按清算日]必须选择" ></input>
                        </td>
                        <td class="table_edit_tr_td_input">
                            位置:
                            <input type="text" name="d_isBalanceDate_pos" id="d_isBalanceDate_pos" size="3" maxLength="3" dataType="rowAndCol" require="true" msg="[按清算日]的[位置]不能为空，请输入正确的格式[x,y]" />
                        </td>
                        
                    </tr>

                </table>
            </DIV>

            <c:import url="/jsp/common/common_template.jsp?template_name=version_eval" />

            <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all_init" />
            <c:set var="add" scope="request" value="1"/>
            <c:set var="del" scope="request" value="1"/>
            <c:set var="modify" scope="request" value="1"/>
            <c:set var="save" scope="request" value="1"/>
            <c:set var="cancle" scope="request" value="1"/>
            <c:set var="genFile" scope="request" value="1"/>
            <c:set var="print" scope="request" value="1"/>
            <c:set var="export" scope="request" value="1"/>
            <c:set var="btNext" scope="request" value="1"/>
            <c:set var="btNextEnd" scope="request" value="1"/>
            <c:set var="btBack" scope="request" value="1"/>
            <c:set var="btBackEnd" scope="request" value="1"/>

            <c:set var="addQueryMethod" scope="request" value="setLineCardNames('detailOp','q_lineID','q_stationID','commonVariable','','','');"/>
            <c:set var="addClickMethod" scope="request" value="setUpdatePrimaryKey('detailOp','d_lineID#d_stationID#d_deviceType#d_deviceID#Version');"/>
            <c:set var="clickMethod" scope="request" value="btnClick('detailOp','clearStart','detail','','clearStartHead');"/>
            <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all" />
            <br/>
        </FORM>

        <!-- 状态栏 通用模板 -->
        <c:import url="/jsp/common/common_template.jsp?template_name=common_status_table" />
    </body>
</html>
