<!DOCTYPE html>
<%@ page language="java" contentType="text/html;charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<html>
    <head>
        <title>权限组</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge,Chrome=1" />

        <script language="javascript" type="text/javascript" charset="utf-8" src="js/common_form.js"></script>
        <script language="javascript" type="text/javascript" charset="utf-8" src="js/Validator.js"></script>
        <script language="JavaScript" type="text/javascript" charset="utf-8" src="js/CalendarPop.js"></script>
        <link rel="stylesheet" type="text/css" href="css/frame/simple.css" title="Style"/>
        <script language="javascript">
        </script> 
    </head>


    <!--<body onload="disableAllInputWindow(true);"> -->
    <body onload="
            initDocument('groupOp', 'detail');
            setDisableAlwaysObs('groupOp', 'd_groupID');

            setPrimaryKeys('groupOp', 'd_groupID');
//                setEnableAlwaysObs('operatorOp','groupID_1');
            setControlsDefaultValue('queryOp');
            setListViewDefaultValue('groupOp', 'clearStart');
            setTableRowBackgroundBlock('DataTable');">
        <!-- 页面用到的变量 通用模板 -->
        <table class="table_title">
            <tr class="trTitle">
                <td>
                    权限 权限组
                </td>
            </tr>
        </table>

        <c:set var="pTitleName" scope="request" value="查询"/>
        <c:set var="pTitleWidth" scope="request" value="50"/>
        <c:import  url="/jsp/common/common_template.jsp?template_name=common_table_title" />
        <form method="post" name="queryOp" id="queryOp" action="groupList">
            <!-- 页面用到的变量 通用模板 -->

            <c:set var="divideShow" scope="request" value="1"/>
            <c:import  url="/jsp/common/common_template_web_variable.jsp?template_name=common_web_variable" />

            <table  class="table_edit">
                <tr class="table_edit_tr">
                <input type="hidden" name="type" value="020103">
                <td class="table_edit_tr_td_label">组别代号:</td>
                <td class="table_edit_tr_td_input">
                    <input type="text" name="q_sysGroupId" id="q_sysGroupId" size="10"  />
                </td>    

                <td class="table_edit_tr_td_label">组别名称:</td>
              
                 <td class="table_edit_tr_td_input">
                    <select id="q_sysGroupName" name ="q_sysGroupName"  require="false" dataType="LimitB" min="1"  msg="请选择">
                        <option value="">=请选择=</option>
                        <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_grps_1" />
                    </select>      
                </td>

                <td class="table_edit_tr_td_label">票务仓库:</td>
                <td class="table_edit_tr_td_input">
                    <select id="q_storageName" name = "q_storageName"  require="false" dataType="LimitB" min="1"  msg="请选择票务仓库">

                        <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_storage" />
                    </select>            
                </td>
                <td class="table_edit_tr_td_label" rowspan="2">

                    <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all_init" />
                    <c:set var="query" scope="request" value="1"/>
                    <c:set var="addClickMethod" scope="request" value="setControlNames('queryOp','q_sysGroupId#q_sysGroupName#q_storageName');"/>
                    <c:set var="clickMethod" scope="request" value="btnClick('queryOp','clearStart','detail');"/>
                    <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all" />
                </td>
                </tr>
            </table>
            <c:import url="/jsp/common/common_template.jsp?template_name=version_eval" />

        </form>

         <!-- 状态栏 通用模板 -->
        <c:import url="/jsp/common/common_template.jsp?template_name=common_status_table" />
            
        <!-- 表头 通用模板 -->
        <c:set var="pTitleName" scope="request" value="权限组信息"/>
        <c:set var="pTitleWidth" scope="request" value="60"/>
        <c:import  url="/jsp/common/common_template.jsp?template_name=common_table_title" />




        <DIV id="clearStartBlock"  class="divForTableBlock">
            <div id="clearStartHead" class="divForTableBlockHead">
                <table class="table_list_block" id="DataTableHead" >
                    <tr class="table_list_tr_head_block" id="ignore">

                        <td class="table_list_tr_col_head_block">
                            <input type="checkbox" name="rectNoAll" id="rectNoAll"  onclick="selectAllRecord('groupOp', 'rectNoAll', 'rectNo', 'clearStart', 0);"/>
                        </td>
                        <td id="orderTd" class="table_list_tr_col_head_block"  isDigit=true index="1"  sortedby="asc" onclick="sortForTableBlock('clearStart');">组别代号</td>
                        <td id="orderTd" class="table_list_tr_col_head_block"  isDigit=false index="2"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width:200px">组别名称</td>
                        <td id="orderTd" class="table_list_tr_col_head_block"  isDigit=false index="3"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width:200px">票务仓库</td>

                    </tr>
                </table>
            </div>
            <div id="clearStart"  class="divForTableBlockData">
                <table class="table_list_block" id="DataTable" >
                    <c:forEach items="${ResultSet}" var="rs">
                        <tr class="table_list_tr_data"  onMouseOver="overResultRow('groupOp', this);" 
                            onMouseOut="outResultRow('groupOp', this);" 
                            onclick="clickResultRow('groupOp', this, 'detail');
                            "
                            id="${rs.sysGroupId}">                               

                            <td id="rectNo1" class="table_list_tr_col_data_block">
                                <input type="checkbox" name="rectNo" onclick="unSelectAllRecord('groupOp', 'rectNoAll', 'rectNo');"
                                       value="${rs.sysGroupId}">

                                </input>
                            </td>                                
                            <td  id="groupID" class="table_list_tr_col_data_block">
                                ${rs.sysGroupId}
                            </td>

                            <td  id="groupName" class="table_list_tr_col_data_block" style="width:200px">
                                ${rs.sysGroupName}
                            </td>     
                            <td  id="storage_name" class="table_list_tr_col_data_block" style="width:200px">
                                ${rs.sysStorageName}
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

        <!--<FORM method="post" name="detailOp" id="detailOp" action="groupList">-->
        <FORM method="post" name="groupOp" id="groupOp" action="groupList" >  
            <c:set var="divideShow" scope="request" value="1"/>
            <c:import  url="/jsp/common/common_template_web_variable.jsp?template_name=common_web_variable" />
            <input type="hidden" name="type" value="020103">

            <DIV id="detail" class="divForTableDataDetail" >
                <table align="center" class="table_edit_detail" style="width:100%">
                    <tr class="table_edit_tr">

                        <td class="table_edit_tr_td_label">
                            组别代号:
                        </td>

                        <td class="table_edit_tr_td_input">
                            <input type="text" disabled="true"   name="d_groupID" id="d_groupID" size="10" require="false"  maxlength="4" dataType="integer|LimitB"  min="2" max="4" msg="组别代号应为4位数字字符!"/>

                        </td>
                        <td class="table_edit_tr_td_label">
                            组别名称:
                        </td>
                        <td class="table_edit_tr_td_input">
                            <input type="text"   name="d_groupName"  id="d_groupName" size="20" maxLength="16" dataType="LimitB" min="1" msg="组别名称不能为空"/>

                        </td>
                        <td class="table_edit_tr_td_label">
                            票务仓库:
                        </td>
                        <td class="table_edit_tr_td_input">
                            <select id="d_storage_name" name = "d_storage_id"  require="true" dataType="LimitB" min="1"  msg="请选择票务仓库">

                                <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_storage" />
                            </select>

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

            <c:set var="clickMethod" scope="request" value="btnClick('groupOp','clearStart','detail','','clearStartHead');"/>
            <c:set var="addAfterMethod" scope="request" value="disablePrimaryKeys('groupOp');"/>
            <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all" />


            <br/>
        </form>

       

    </body>
</html>

