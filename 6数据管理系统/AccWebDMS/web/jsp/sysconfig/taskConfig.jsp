<%-- 
    Document   : taskConfig
    Created on : 2017-9-14, 15:34:06
    Author     : ysw
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>系统管理 任务管理</title>

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
            setPrimaryKeys('detailOp', 'd_taskName');
            setControlsDefaultValue('queryOp');
            setListViewDefaultValue('detailOp', 'clearStart');
            setQueryControlsDefaultValue('queryOp', 'detailOp');
            setTableRowBackgroundBlock('DataTable');">

        <!--<body >-->
        <table  class="table_title">
            <tr align="center" class="trTitle">
                <td colspan="4">系统管理 任务管理</td>
            </tr>
        </table>

        <c:set var="pTitleName" scope="request" value="查询"/>
        <c:set var="pTitleWidth" scope="request" value="50"/>
        <c:import  url="/jsp/common/common_template.jsp?template_name=common_table_title" />

        <form method="post" name="queryOp" id="queryOp" action="taskConfig">
            <!-- 页面用到的变量 通用模板 -->
            <c:set var="divideShow" scope="request" value="1"/>
            <c:import  url="/jsp/common/common_template_web_variable.jsp?template_name=common_web_variable" />
            <table  class="table_edit">
                <tr class="table_edit_tr">                                      
                    <td class="table_edit_tr_td_label">任务名:</td>
                    <td class="table_edit_tr_td_input">
                        <input type="text" name="q_taskName" id="q_taskName" size="20" maxlength="50" require="false" dataType="string" msg="任务名应为字母或数字"/>                                                          
                    </td>
                    <td class="table_edit_tr_td_label">任务状态:</td>
                    <td class="table_edit_tr_td_input">
                        <select id="q_status" name="q_status" >
                            <option value="">=请选择=</option>                            
                            <option value ="0">待执行</option>
                            <option value ="1">执行中</option>                                              
                        </select>                        
                    </td>

                    <td class="table_edit_tr_td_label" rowspan="2">
                        <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all_init" />
                        <c:set var="query" scope="request" value="1"/>
                        <c:set var="addClickMethod" scope="request" value="removeBlankSpaceBeforeQuery('queryOp',['q_taskName']);setControlNames('queryOp','q_taskName#q_status');"/>
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
            <div id="clearStartHead" class="divForTableBlockHead" style="width: 1120px" >
                <table class="table_list_block" id="DataTableHead" >
                    <!--说明：列的序号从0开始 isDigit：false 表示按字符串排序 ：true表示按数值排序 sortedby：asc表示按升序 dec表示按降序 -->
                    <tr  class="table_list_tr_head_block" id="ignore">
                        <td   class="table_list_tr_col_head_block">
                            <input type="checkbox" name="rectNoAll" id="rectNoAll" onclick="selectAll('detailOp', 'rectNoAll', 'rectNo', 'clearStart', 0);"/>
                        </td>	
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="0"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 200px">任务名</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="1"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 200px">启动时间（HHmm）</td> 
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="2"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 200px">任务状态</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="3"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 400px">任务描述</td>                         
                    </tr>
                </table>
            </div>
            <!-- <DIV id="clearStart" align="center" style="position:relative; left:20; width:95%; height:30%;min-height: 200px;max-height: 250px; layer-background-color:#99CC99; border:thin outset #ffffff; z-index:1; overflow: scroll;">-->
            <div id="clearStart"  class="divForTableBlockData" style="width: 1120px">
                <table class="table_list_block" id="DataTable" >
                    <c:forEach items="${ResultSet}" var="rs">                  
                        <tr class="table_list_tr_data"  onMouseOver="overResultRow('detailOp', this);" 
                            onMouseOut="outResultRow('detailOp', this);" 
                            onclick="clickOneResultRow('detailOp', this, 'detail','rectNo');"
                            id="${rs.taskName}" >

                            <td id="rectNo1" class="table_list_tr_col_data_block">
                                <input type="checkbox" name="rectNo" id="rectNo" 
                                       value="${rs.taskName}">
                                </input>
                            </td>                       
                            <td  id="taskName" class="table_list_tr_col_data_block" style="width: 200px">
                                ${rs.taskName}
                            </td>
                            <td  id="startTime" class="table_list_tr_col_data_block" style="width: 200px">
                                ${rs.startTime}
                            </td>
                            <td  id="status" class="table_list_tr_col_data_block" style="width: 200px">
                                ${rs.statusName}
                            </td> 
                            <td  id="remark" class="table_list_tr_col_data_block" style="width: 400px">
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

        <FORM method="post" name="detailOp" id="detailOp" action="taskConfig" >

            <c:set var="divideShow" scope="request" value="1"/>
            <c:import  url="/jsp/common/common_template_web_variable.jsp?template_name=common_web_variable" />
            <!--查询结果对应列，expAllFields的值为其实体类对应get方法，顺序一定要跟页面显示的顺序一致,不然导出列数据会混乱。-->
            <input type="hidden" name="expAllFields" id="d_expAllFields"
                   value="getTaskName,getStartTime,getStatusName,getRemark" />
            <!--导出文件名字 生成的Excel sheet的名字也是这个-->
            <input type="hidden" name="expFileName" id="d_expFileName" value="任务管理.xlsx" />
            <!--需要导出页面的表数据  一个页面多个导出全部根据这里设置详情参看运营系统退款模块-->
            <input type="hidden" name="divId" id="d_divId" value="clearStartHead">
            <!--对应你controller写的方法  一个页面多个导出全部根据这里设置详情参看运营系统退款模块-->
            <input type="hidden" name="methodUrl" id="d_methodUrl" value="/TaskConfigExportAll"/>
            <!--<DIV id="detail" align="center" class="divForTableData" style="position:relative; left:20; width:95%; height:10; layer-background-color:#99CC99; border:thin outset #ffffff; z-index:1;">-->
            <div id="detail"  class="divForTableDataDetail" >
                <table  class="table_edit_detail">
                    <tr class="table_edit_tr">
                        <td class="table_edit_tr_td_label">任务名:</td>
                        <td class="table_edit_tr_td_input">                         
                            <input type="text" name="d_taskName" id="d_taskName" size="20" maxlength="50" require="true" dataType="NotEmpty"  msg="任务名不能为空!"/>                   
                        </td> 
                        <td class="table_edit_tr_td_label" style="width:30px">启动时间（HHmm）:</td>
                        <td class="table_edit_tr_td_input" style="width:30px">                         
                            <input type="text" name="d_startTime" id="d_startTime" size="20" maxlength="25" require="true" dataType="DMSTaskTime|CheckValidTime"  msg="请输入有效时间设置信息（HHmm）：0805,小时位小于24，分钟位小于60，多个时间段为递增关系"/>                  
                            格式如：0330,1020,2015(使用英文半角,分割)
                        </td> 
                    <input type="hidden" name="d_status" id="d_status"/>
                    </tr>
                </table>
            </div>

            <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all_init" />           
            <c:set var="modify" scope="request" value="1"/>
            <c:set var="save" scope="request" value="1"/>
            <c:set var="cancle" scope="request" value="1"/>           
            <c:set var="print" scope="request" value="1"/>
            <c:set var="export" scope="request" value="1"/>  
            <c:set var="expAll" scope="request" value="1" />
            <c:set var="clickMethod" scope="request" value="btnClick('detailOp','clearStart','detail','','clearStartHead');"/>
            <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all" />
            <br/>
        </FORM>


    </body>
</html>

