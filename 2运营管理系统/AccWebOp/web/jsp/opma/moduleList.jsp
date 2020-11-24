<!DOCTYPE html>
<%@ page language="java" contentType="text/html;charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
    <head>
        <title>模块管理</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge,Chrome=1" />

        <script language="javascript" type="text/javascript" charset="utf-8" src="js/common_form.js"></script>
        <script language="javascript" type="text/javascript" charset="utf-8" src="js/Validator.js"></script>
        <script language="JavaScript" type="text/javascript" charset="utf-8" src="js/CalendarPop.js"></script>
        <link rel="stylesheet" type="text/css" href="css/frame/simple.css" title="Style"/>
        <script language="javascript">
            function selectModuleTypeChange(v) {              
                if (v != 'B') {
                    document.getElementById('d_btnId').disabled = true;
                } else {
                    document.getElementById('d_btnId').disabled = false;
                }
            }
            function changeModuleType(){
                  var i = document.getElementById("d_moduleType");
                  if(i.disabled == false && i.value  == 'B'){
                      document.getElementById('d_btnId').disabled = false;
                  }else{
                     document.getElementById('d_btnId').disabled = true;
                  }
            }
        </script> 

    </head>
    <body onload="initDocument('queryOp', 'detail');
            initDocument('detailOp', 'detail');
            setPrimaryKeys('detailOp', 'moduleId#topMenuId#parentId');
            setControlsDefaultValue('queryOp');
            setListViewDefaultValue('detailOp', 'clearStart');
            setQueryControlsDefaultValue('queryOp', 'detailOp');
            setPageControl('detailOp');
            setTableRowBackgroundBlock('DataTable');
            setDisableAlwaysObs('detailOp', 'd_btnId')">
        <!--onload="initDocument('moduleQry','detail');initDocument('moduleQry','detail');setPrimaryKeys('queryOp','d_moduleID');setControlsDefaultValue('moduleQry');setListViewDefaultValue('queryOp','clearStart');setQueryControlsDefaultValue('moduleQry','queryOp');"-->

        <table  class="table_title">
            <tr align="center" class="trTitle">
                <td>
                    权限 模块
                </td>
            </tr>
        </table>

        <c:set var="pTitleName" scope="request" value="模块信息"/>
        <c:set var="pTitleWidth" scope="request" value="50"/>
        <c:import  url="/jsp/common/common_template.jsp?template_name=common_table_title" />
        <FORM method="post" name="queryOp" id="queryOp" action="moduleList" >
            <c:set var="divideShow" scope="request" value="1"/>
            <c:import  url="/jsp/common/common_template_web_variable.jsp?template_name=common_web_variable" />
            <table class="table_edit">
                <tr class="table_edit_tr">
                    <td class="table_edit_tr_td_label">
                        模块代码:
                    </td>
                    <td class="table_edit_tr_td_input">                                
                        <input type="text" name="q_moduleID" id="q_moduleID" size="10" require="false" maxLength="8" dataType="Number|LimitB" min="1" max="8" msg="模块代码不能为空且最多为8个数字字符"/>
                    </td>
                    <td class="table_edit_tr_td_label">
                        模块名称:
                    </td>
                    <td class="table_edit_tr_td_input">                                
                        <input type="text" name="q_name" id="q_name" size="10" require="false" maxLength="33" dataType="LimitB" min="1" msg="模块名称不能为空"/>                               
                    </td>
                    <td class="table_edit_tr_td_label">
                        一级模块ID:
                    </td>
                    <td class="table_edit_tr_td_input">                                
                        <input type="text" name="q_topID" id="q_topID" size="10" maxLength="2" require="false" dataType="Number|LimitB"  msg="一级模块ID应为数字字符且最大长度为2"/>

                    </td>
                </tr>
                <tr class="table_edit_tr">
                    <td class="table_edit_tr_td_label">
                        上级模块ID:
                    </td>
                    <td class="table_edit_tr_td_input">

                        <input type="text" name="q_parentID" id="q_parentID" size="10" maxLength="6" require="false" dataType="Number|LimitB"   min="1" max="6" msg="上级模块ID应为数字字符且最大长度为6"/>

                    </td>
                    <td class="table_edit_tr_td_label">
                        系统类型
                    </td>
                    <td class="table_edit_tr_td_input">

                        <select id="q_sysFlag" name="q_sysFlag" require="false" dataType="NotEmpty" msg="应选择系统类型">	
                            <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_systype" />

                        </select>

                    </td>
                    <td class="table_edit_tr_td_label" rowspan="2">

                        <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all_init" />
                        <c:set var="query" scope="request" value="1"/>
                        <c:set var="addClickMethod" scope="request" value="setControlNames('queryOp','q_moduleID#q_name#q_topID#q_parentID#q_sysFlag');"/>
                        <c:set var="clickMethod" scope="request" value="btnClick('queryOp','clearStart','detail');"/>
                        <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all" />
                    </td>

                </tr>
            </table>
        </FORM>

        <!-- 状态栏 通用模板 -->
        <c:import url="/jsp/common/common_template.jsp?template_name=common_status_table" />            

        <!-- 表头 通用模板 -->

        <c:set var="pTitleName" scope="request" value="列表"/>
        <c:set var="pTitleWidth" scope="request" value="50"/>
        <c:import url="/jsp/common/common_template.jsp?template_name=common_table_title" />

        <DIV id="clearStartBlock"  class="divForTableBlock">
            <div id="clearStartHead" class="divForTableBlockHead" style="width: 130%;">
                <table class="table_list_block" id="DataTableHead" >
                    <tr class="table_list_tr_head_block" id="ignore">
                        <td class="table_list_tr_col_head_block">
                            <input type="checkbox" name="rectNoAll" id="rectNoAll"  onclick="selectAllRecord('detailOp', 'rectNoAll', 'rectNo', 'clearStart', 0);"/>
                        </td>
                        <td id="moduleTd" class="table_list_tr_col_head_block"  isDigit=false index="1"  sortedby="asc" onclick="sortForTableBlock('clearStart');">模块代码</td>
                        <td id="moduleTd" class="table_list_tr_col_head_block"  isDigit=false index="2"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width:200px">模块名称</td>
                        <td id="moduleTd" class="table_list_tr_col_head_block"  isDigit=false index="3"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width:100px">一级模块ID</td>
                        <td id="moduleTd" class="table_list_tr_col_head_block"  isDigit=false index="4"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width:100px">上级模块ID</td>
                        <td id="moduleTd" class="table_list_tr_col_head_block"  isDigit=false index="5"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width:280px">链接路径</td>
                        <td id="moduleTd" class="table_list_tr_col_head_block"  isDigit=false index="6"  sortedby="asc" onclick="sortForTableBlock('clearStart');">标记图标</td>
                        <td id="moduleTd" class="table_list_tr_col_head_block"  isDigit=false index="7"  sortedby="asc" onclick="sortForTableBlock('clearStart');">锁定状态</td>
                        <td id="moduleTd" class="table_list_tr_col_head_block"  isDigit=false index="8"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width:120px">系统类型</td>
                        <td id="moduleTd" class="table_list_tr_col_head_block"  isDigit=false index="9"  sortedby="asc" onclick="sortForTableBlock('clearStart');">模块权限</td>
                        <td id="moduleTd" class="table_list_tr_col_head_block"  isDigit=false index="10"  sortedby="asc" onclick="sortForTableBlock('clearStart');">按钮权限</td>
                    </tr>
                </table>
            </div>
            <div id="clearStart"  class="divForTableBlockData" style="width: 130%;">
                <table class="table_list_block" id="DataTable" >
                    <c:forEach items="${ResultSet}" var="rs">
                        <!--class="listTableData" -->
                        <tr class="table_list_tr_data"  onMouseOver="overResultRow('detailOp', this);" 
                            onMouseOut="outResultRow('detailOp', this);" 
                            onclick="clickResultRow('detailOp', this, 'detail');
                                    setPageControl('detailOp');" 
                            id="${rs.moduleId}">

                            <td id="rectNo1" class="table_list_tr_col_data_block">
                                <input type="checkbox" name="rectNo" onclick="unSelectAllRecord('detailOp', 'rectNoAll', 'rectNo');"
                                       value="${rs.moduleId}">

                                </input>
                            </td>
                            <td  id="moduleId" class="table_list_tr_col_data_block">
                                ${rs.moduleId}
                            </td>
                            <td  id="moduleName" class="table_list_tr_col_data_block" style="width:200px">
                                ${rs.moduleName}
                            </td>                                
                            <td  id="topMenuId" class="table_list_tr_col_data_block" style="width:100px">
                                ${rs.topMenuId}
                            </td>
                            <td  id="parentId" class="table_list_tr_col_data_block" style="width:100px">
                                ${rs.parentId}
                            </td>
                            <td  id="menuUrl" class="table_list_tr_col_data_block" style="width:280px">
                                ${rs.menuUrl}
                            </td>
                            <td  id="menuIcon" class="table_list_tr_col_data_block">
                                ${rs.menuIcon}
                            </td>
                            <td  id="locked" class="table_list_tr_col_data_block">
                                ${rs.locked}
                            </td>
                            <td  id="sysFlag" class="table_list_tr_col_data_block" style="width:120px">
                                ${rs.sysFlag}
                            </td>
                            <td  id="moduleType" class="table_list_tr_col_data_block">
                                ${rs.moduleType}
                            </td>
                            <td  id="btnId" class="table_list_tr_col_data_block">
                                ${rs.btnId}
                            </td>



                        </tr>

                    </c:forEach>

                </table>
            </div>
        </DIV>


        <!-- 表头 通用模板 -->
        <c:set var="pTitleName" scope="request" value="明细"/>
        <c:set var="pTitleWidth" scope="request" value="50"/>
        <c:import url="/jsp/common/common_template.jsp?template_name=common_table_title" />



        <FORM method="post" name="detailOp" id="detailOp" action="moduleList">
            <!-- 页面用到的变量 通用模板 -->
            <c:set var="divideShow" scope="request" value="1"/>
            <c:import  url="/jsp/common/common_template_web_variable.jsp?template_name=common_web_variable" />
            <!--导出全部参数 -->
            <input type="hidden" name="expAllFields" id="d_expAllFields" value="MODULE_ID,MODULE_NAME,TOP_MENU_ID,PARENT_ID,MENU_URL,MENU_ICON,LOCKED_TEXT,SYS_FLAG_TEXT,MODULE_TYPE_TEXT,BTN_ID_TEXT" />
            <input type="hidden" name="expFileName" id="d_expFileName" value="权限模块.xlsx" />
            <input type="hidden" name="divId" id="d_divId" value="clearStartHead" />
            <input type="hidden" name="methodUrl" id="d_methodUrl" value="/SysModuleExportAll" />
            <DIV id="detail" class="divForTableDataDetail">
                <table align="center" class="table_edit_detail" >
                    <tr class="table_edit_tr">
                        <td class="table_edit_tr_td_label">
                            模块代码:
                        </td>
                        <td class="table_edit_tr_td_input">

                            <input type="text" name="d_moduleId" id="d_moduleId" size="10" maxLength="8" dataType="Number|LimitB" min="1" max="8" msg="模块代码不能为空且最多为8个数字字符"/>

                        </td>
                        <td class="table_edit_tr_td_label" >
                            模块名称:
                        </td>
                        <td class="table_edit_tr_td_input">
                            <input type="text" name="d_moduleName" id="d_moduleName" size="10" maxLength="33" require="true" dataType="LimitB" min="1" msg="模块名称不能为空"/>

                        </td>
                        <td class="table_edit_tr_td_label">
                            一级模块ID:
                        </td>
                        <td class="table_edit_tr_td_input">

                            <input type="text" name="d_topMenuId" id="d_topMenuId" size="10" maxLength="2" require="false" dataType="Number|LimitB"  msg="一级模块ID应为数字字符且最大长度为2"/>

                        </td>
                        <td class="table_edit_tr_td_label">
                            上级模块ID:
                        </td>
                        <td class="table_edit_tr_td_input">
                            <input type="text" name="d_parentID" id="d_parentId" size="10" maxLength="6" require="false" dataType="Number|LimitB"   min="1" max="6" msg="上级模块ID应为数字字符且最大长度为6" style="width:140px"/>

                        </td>
                    </tr>
                    <tr class="table_edit_tr">
                        <td class="table_edit_tr_td_label" >
                            链接路径:
                        </td>
                        <td class="table_edit_tr_td_input">

                            <input type="text" name="d_menuUrl" id="d_menuUrl" size="10" require="false" dataType="LimitB" min="1" />

                        </td>
                        <td class="table_edit_tr_td_label">
                            标记图标:
                        </td>
                        <td class="table_edit_tr_td_input">

                            <input type="text" name="d_menuIcon" id="d_menuIcon" size="10" require="false" dataType="LimitB" min="1"/>

                        </td>
                        <td class="table_edit_tr_td_label">
                            锁定状态:
                        </td>
                        <td class="table_edit_tr_td_input">

                            <select id="d_locked" name = "d_locked" require="true" dataType="LimitB" min="1" msg="没有选择锁定状态" style="width:100px">                                           
                                <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_locked" />
                            </select>

                        </td>
                        <td class="table_edit_tr_td_label">
                            <div align="right">系统:</div>
                        </td>
                        <td class="table_edit_tr_td_input">
                            <select id="d_sysFlag" name = "d_sysFlag" require="true" dataType="LimitB" min="1" msg="没有选择系统">
                                <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_systype" />
                            </select>

                        </td>
                    </tr>
                    <tr class="table_edit_tr">
                        <td class="table_edit_tr_td_label">
                            模块权限类型:
                        </td>
                        <td class="table_edit_tr_td_input">

                            <select id="d_moduleType" name = "d_moduleType" require="true" dataType="LimitB" min="1" msg="没有选择模块权限类型" onchange="selectModuleTypeChange(this.value)" style="width:100px">
                                <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_modulePer" />
                            </select>

                        </td>
                        <td class="table_edit_tr_td_label">
                            按钮权限类型:
                        </td>
                        <td class="table_edit_tr_td_input">

                            <select id="d_btnId" name = "d_btnId" require="false" disabled="true"  dataType="LimitB" min="1" msg="没有选择按钮权限类型" style="width:100px">
                                <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_btn" />
                            </select>

                        </td>
                        <td width="10%">
                        </td>
                        <td width="10%">
                        </td>
                        <td width="10%">
                        </td>
                        <td width="10%">
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

            <c:set var="print" scope="request" value="0"/>
            <c:set var="export" scope="request" value="0"/>
            <c:set var="expAll" scope="request" value="1" />
            <c:set var="btNext" scope="request" value="1"/>
            <c:set var="btNextEnd" scope="request" value="1"/>
            <c:set var="btBack" scope="request" value="1"/>
            <c:set var="btBackEnd" scope="request" value="1"/>
            <c:set var="btGoPage" scope="request" value="1"/>



            <c:set var="addClickMethod" scope="request" value="setUpdatePrimaryKey('detailOp','d_moduleId');"/>
            <c:set var="clickMethod" scope="request" value="btnClick('detailOp','clearStart','detail');changeModuleType()"/>
            <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all" />

            <br/>

        </FORM>




    </body>
</html>


