<%-- 
    Document   : sys_operator
    Created on : 2018-1-18, 16:15:12
    Author     : luck
--%>

<!DOCTYPE html>
<%@ page language="java" contentType="text/html;charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<html>
    <head>
        <title>操作员</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge,Chrome=1" />

        <script language="javascript" type="text/javascript" charset="utf-8" src="js/common_form.js"></script>
        <script language="javascript" type="text/javascript" charset="utf-8" src="js/Validator.js"></script>
        <script language="JavaScript" type="text/javascript" charset="utf-8" src="js/CalendarPop.js"></script>
        <link rel="stylesheet" type="text/css" href="css/frame/simple.css" title="Style"/>
        <script language="javascript">
        </script> 
    </head>
    <script language="javascript">
        function preLoadVal(q_time) {
//            获取选择的时间
            var current = new Date();
//  		var year = current.getYear(); 
            var year = current.getFullYear() + 1;
            var month = current.getMonth() + 1;
            var day = current.getDate();
            if (month < 10)
                month = "0" + month;
            if (day < 10)
                day = "0" + day;
            var etime;
            etime = year + "-" + month + "-" + day;
            document.getElementById(q_time).value = etime;
        }
        function show(resluts, jspName, moduleID, width, height, id) {
            var obj = new Object();
            var d_groupName = document.getElementById('d_groupID_1').value;
            obj.value = resluts;

            var path = jspName + "?ModuleID=" + moduleID + "&d_groupName=" + d_groupName;
            //	alert(path);

            var rt = window.showModalDialog(path, obj, 'dialogWidth:' + width + 'px;dialogHeight:' + height + 'px;center:yes;resizable:no;status:no;scroll:yes');
            //	alert(rt);
            if (rt != null && rt != 'undefined') {
                var i = rt.split(",");
                var rt1 = "";
                var rt2 = "";
                for (var s = 0; s < i.length; s++) {
                    rt1 = rt1 + "," + i[s].split("#")[0];
                    rt2 = rt2 + "," + i[s].split("#")[1];
                }
                if (rt1.substr(0, 1) == ',')
                    rt1 = rt1.substr(1);
                 if (rt2.substr(0, 1) == ',')
                    rt2 = rt2.substr(1);
                document.getElementById(id).value = rt1;
                document.getElementById("d_groupID_2").value = rt2;
            }

        }
    </script>


    <!--<body onload="disableAllInputWindow(true);"> -->
    <body onload="
            initDocument('operatorOp', 'operatorDetail');
            setPrimaryKeys('operatorOp', 'd_operatorID');
//                setEnableAlwaysObs('operatorOp','groupID_1');
            setControlsDefaultValue('queryOp1');
            setAlwaysEnableForObj('operatorOp', false);
            setListViewDefaultValue('groupOp', 'clearStart');
            setListViewDefaultValue('operatorOp', 'opClearStart');
            preLoadVal('d_expiredDate');
            setTableRowBackgroundBlock('DataTable');">
        <!-- 页面用到的变量 通用模板 -->
        <table class="table_title">
            <tr class="trTitle">
                <td>
                    操作员
                </td>
            </tr>
        </table>

        <!-- 表头 通用模板 -->

        <c:set var="pTitleName" scope="request" value="查询"/>
        <c:set var="pTitleWidth" scope="request" value="50"/>
        <c:import  url="/jsp/common/common_template.jsp?template_name=common_table_title" />
        <form method="post" name="queryOp1" id="queryOp1" action="groupList">
            <!-- 页面用到的变量 通用模板 -->

            <c:set var="divideShow" scope="request" value="1"/>
            <c:import  url="/jsp/common/common_template_web_variable.jsp?template_name=common_web_variable" />

            <table  class="table_edit">
                <tr class="table_edit_tr">

                <input type="hidden" name="type1" value ="opr">
                <input type="hidden" name="type" value="020102">
                <td class="table_edit_tr_td_label">操作员ID:</td>
                <td class="table_edit_tr_td_input">
                    <input type="text" name="q_sysOperatorId" id="q_sysOperatorId" size="10" />
                </td>    

                <td class="table_edit_tr_td_label">姓名:</td>
                <td class="table_edit_tr_td_input">
                    <input type="text" name="q_sysOperatorName" id="q_sysOperatorName" size="10" />                     
                </td>
                <td class="table_edit_tr_td_label">工号:</td>
                <td class="table_edit_tr_td_input">
                    <input type="text" name="q_sysEmployeeId" id="q_sysEmployeeId" size="10" />                     
                </td>
                <td class="table_edit_tr_td_label">状态:</td>
                <td class="table_edit_tr_td_input">
                    <select id="q_status" name = "q_status" require="false" dataType="LimitB" min="1"  msg="请选择状态">
                        <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_locked" />
                    </select>         
                </td>
                <td class="table_edit_tr_td_label">组别:</td>
                <td class="table_edit_tr_td_input">
                    <select id="q_sysGroupName_1" name ="q_sysGroupName_1"  require="false" dataType="LimitB" min="1"  msg="请选择">
                        <option value="">=请选择=</option>
                        <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_grps" />
                    </select>      
                </td>
                <td class="table_edit_tr_td_label" rowspan="2">

                    <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all_init" />
                    <c:set var="query" scope="request" value="1"/>
                    <c:set var="cancle" scope="request" value="0"/>
                    <c:set var="addClickMethod" scope="request" value="setControlNames('queryOp1','q_sysOperatorId#q_sysOperatorName#q_sysEmployeeId#q_status#q_sysGroupName_1');"/>
                    <c:set var="clickMethod" scope="request" value="btnClick('queryOp1','clearStart','detail');"/>
                    <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all" />
                </td>
                </tr>
            </table>
            <c:import url="/jsp/common/common_template.jsp?template_name=version_eval" />

        </form>

        <!-- 状态栏 通用模板 -->
        <c:import url="/jsp/common/common_template.jsp?template_name=common_status_table" />    

        <c:set var="pTitleName" scope="request" value="操作员信息"/>
        <c:set var="pTitleWidth" scope="request" value="60"/>
        <c:import  url="/jsp/common/common_template.jsp?template_name=common_table_title" />                

        <DIV id="clearStartBlock"  class="divForTableBlock">
            <div id="clearStartHead1" class="divForTableBlockHead">
                <table class="table_list_block" id="DataTableHead" >
                    <tr class="table_list_tr_head_block" id="ignore">
                        <td class="table_list_tr_col_head_block">
                            <input type="checkbox" name="opRectNoAll" id="opRectNoAll"  onClick="selectAllRecord('operatorOp', 'opRectNoAll', 'opRectNo', 'opClearStart', 0);"/>
                        </td>
                        <td id="operatorTd" class="table_list_tr_col_head_block"  isDigit=false index="1"  sortedby="asc" onclick="sortForTableBlock('opClearStart');">操作员ID</td>
                        <td id="operatorTd" class="table_list_tr_col_head_block"  isDigit=false index="2"  sortedby="asc" onclick="sortForTableBlock('opClearStart');">姓名</td>
                        <td id="operatorTd" class="table_list_tr_col_head_block"  isDigit=false index="3"  sortedby="asc" onclick="sortForTableBlock('opClearStart');" >工号</td>
                        <td id="operatorTd" class="table_list_tr_col_head_block"  isDigit=false index="4"  sortedby="asc" onclick="sortForTableBlock('opClearStart');" style="width:250px">失效日期</td>
                        <td id="operatorTd" class="table_list_tr_col_head_block"  isDigit=false index="5"  sortedby="asc" onclick="sortForTableBlock('opClearStart');" >状态</td>
                        <td id="operatorTd" class="table_list_tr_col_head_block"  isDigit=false index="6"  sortedby="asc" onclick="sortForTableBlock('opClearStart');" style="width:350px">组别</td>
                    </tr>
                </table>
            </div>
            <div id="opClearStart"  class="divForTableBlockData">
                <table class="table_list_block" id="DataTable" >
                    <c:forEach items="${ResultSet1}" var="rsOp">
                        <tr class="table_list_tr_data"  onMouseOver="overResultRow('operatorOp', this);" 
                            onMouseOut="outResultRow('operatorOp', this);" 
                            onclick="clickResultRow('operatorOp', this, 'operatorDetail');"
                            id="${rsOp.sysOperatorId}" title="${rsOp.sysGroupId}">   

                            <td id="rectNo1" class="table_list_tr_col_data_block">
                                <input type="checkbox" name="opRectNo" onclick="unSelectAllRecord('operatorOp', 'rectNoAll', 'opRectNo');"
                                       value="${rsOp.sysOperatorId}">

                                </input>
                            </td>    

                            <td  id="operatorID" class="table_list_tr_col_data_block">
                                ${rsOp.sysOperatorId}
                            </td>                               
                            <td  id="name" class="table_list_tr_col_data_block">
                                ${rsOp.sysOperatorName}
                            </td>                               
                            <td  id="employeeID" class="table_list_tr_col_data_block">
                                ${rsOp.sysEmployeeId}
                            </td>                              
                            <td  id="expiredDate" class="table_list_tr_col_data_block" style="width:250px">
                                ${rsOp.sysExpiredDate}
                            </td>

                            <td  id="status" class="table_list_tr_col_data_block">
                                ${rsOp.sysStatus}
                            </td>

                            <td  id="groupID_2" class="table_list_tr_col_data_block" style="width:350px">
                                ${rsOp.sysGroupName}
                            </td>                               
                            <td  id="groupID_1" class="table_list_tr_col_data_block" style="width:100px; display: none " >
                                ${rsOp.sysGroupId}
                            </td>           
                        </tr>
                    </c:forEach>  


                </table>
            </div>
        </div>

        <FORM method="post" name="operatorOp" id="operatorOp" action="groupList">
            <input type="hidden" name="type" value="020102">
            <input type="hidden" name="type2" id="type2" value="opr"/>

            <c:set var="divideShow" scope="request" value="1"/>
            <c:import  url="/jsp/common/common_template_web_variable.jsp?template_name=common_web_variable" />
            <DIV id="operatorDetail" class="divForTableDataDetail">
                <table align="center" class="table_edit_detail" >
                    <!--		<tr><input type="hidden" name="allSelectedOpIDs"/></tr> -->
                    <tr class="table_edit_tr">
                        <td class="table_edit_tr_td_label">
                            操作员ID:
                        </td>
                        <td class="table_edit_tr_td_input">

                            <input type="text"   name="operatorID" id="d_operatorID" size="10" minlength="4" maxLength="8" dataType="NumAndEng|Limit" require="true" min="4" max="8" msg="操作员ID不为空且最多8个最少4个数字和字符"/>

                        </td>

                    <input type="hidden"   name="password"  id="d_password" value="888888"/>


                    <input type="hidden"   name="confirm"  id="d_confirm" value="888888"/>

                    <td class="table_edit_tr_td_label">
                        姓名:
                    </td>
                    <td class="table_edit_tr_td_input"> 

                        <input type="text"   name="name"  id="d_name" size="15" maxLength="45" dataType="LimitContainChinese" max="45"  min="1" msg="姓名不能为空且最多15个汉字,45个字符"/>

                    </td>

                    <td class="table_edit_tr_td_label">
                        工号:
                    </td>

                    <td class="table_edit_tr_td_input"> 

                        <input type="text"   name="employeeID"  id="d_employeeID" size="6" maxLength="6" dataType="NumAndEng|LimitB" min="1" max="6" msg="工号不为空且最多6个数字和字符"/>

                    </td>
                    </tr>
                    <tr class="table_edit_tr">
                        <td class="table_edit_tr_td_label">
                            失效日期:
                        </td>
                        <td class="table_edit_tr_td_input"> 

                            <input type="text" name="expiredDate" id="d_expiredDate" size="10"    require="true" dataType="LimitB" min="1"  msg="失效日期不能为空"/>
                            <a href="javascript:openCalenderDialogByID('d_expiredDate','false');">
                                <img src="./images/calendar.gif" width="15" height="15" border="0" style="block"/>
                            </a>

                        </td>
                        <td class="table_edit_tr_td_label">
                            状态:
                        </td>
                        <td class="table_edit_tr_td_input"> 
                            <select id="d_status" name = "status" require="true" dataType="LimitB" min="1"  msg="请选择状态">

                                <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_locked" />
                            </select>

                        </td>	
                        <td class="table_edit_tr_td_label">
                            权限组:
                        </td>
                        <td class="table_edit_tr_td_input">                         
                            <input type="text" name="groupID_2" id="d_groupID_2" size="30"  unselectable="on" readonly="true" dataType="Limit" min="1" msg="请选择权限组" onclick="show('operatorOp', 'SysGroupWindow?command=openwindow&ModuleID=${ModuleID}', '0305', 600, 600, 'd_groupID_1');" />

                        </td>                                           
                            <input type="hidden" name="groupID_1" id="d_groupID_1"  unselectable="on" readonly="true" dataType="Limit" min="1"   />
                    </tr>
                </table>
            </DIV>

            <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all_init" />
            <c:set var="add" scope="request" value="1"/>
            <c:set var="del" scope="request" value="1"/>
            <c:set var="modify" scope="request" value="1"/>
            <c:set var="btResetPass" scope="request" value="1"/>
            <c:set var="save" scope="request" value="1"/>
            <c:set var="cancle" scope="request" value="1"/>               
            <c:set var="print" scope="request" value="1"/>
            <c:set var="export" scope="request" value="1"/>    

            <c:set var="addClickMethod" scope="request" value="setUpdatePrimaryKey('operatorOp','operatorID');"/>
            <c:set var="clickMethod" scope="request" value="btnClick('operatorOp','opClearStart','operatorDetail','','clearStartHead1');"/>
            <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all" />
        </form>



    </body>
</html>


