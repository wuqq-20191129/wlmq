<%-- 
    Document   : degrade_mode_history
    Created on : 2017-6-16, 14:35:15
    Author     : lind
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="X-UA-Compatible" content="IE=9,Chrome=1" />
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>降级模式使用记录</title>
        <script language="javascript" type="text/javascript" charset="utf-8" src="js/common_form.js"></script>
        <script language="javascript" type="text/javascript" charset="utf-8" src="js/Validator.js"></script>
        <script language="javascript" type="text/javascript" charset="utf-8" src="js/common_form_extend.js"></script>
        <script language="JavaScript" src="js/CalendarPop.js"></script>
        <link rel="stylesheet" type="text/css" href="css/frame/simple.css" title="Style"/>
    </head>
    <style type="text/css">
        .NewButtonStyle{
            font-size: 9pt;
            color:#3399FF;
            background-color:#FFFFFF;
            text-align:center;
        }
    </style>



    <body onload="initDocument('queryOp', 'detail');
            initDocument('detailOp', 'detail');
            setPrimaryKeys('detailOp', 'd_waterNo');
            setControlsDefaultValue('queryOp');
            setListViewDefaultValue('detailOp', 'clearStart');
            setQueryControlsDefaultValue('queryOp', 'detailOp');
            setPageControl('detailOp');
            setTableRowBackgroundBlock('DataTable');
          ">

        <table  class="table_title">
            <tr align="center" class="trTitle">
                <td colspan="4">降级模式使用记录</td>
            </tr>
        </table>

        <!-- 表头 通用模板 -->
        <c:set var="pTitleName" scope="request" value="查询"/>
        <c:set var="pTitleWidth" scope="request" value="50"/>
        <c:import  url="/jsp/common/common_template.jsp?template_name=common_table_title" />

        <form method="post" name="queryOp" id="queryOp" action="DegradeModeRecd">
            <!-- 页面用到的变量 通用模板 -->
            <c:set var="divideShow" scope="request" value="1"/>
            <c:import  url="/jsp/common/common_template_web_variable.jsp?template_name=common_web_variable" />

            <table class="table_edit">
                <tr class="table_edit_tr">
                    <td class="table_edit_tr_td_label">线路:</td>
                    <td class="table_edit_tr_td_input">
                        <select id="q_lineID" name="q_lineID" onChange="setSelectValues('queryOp', 'q_lineID', 'q_stationID', 'commonVariable');" style="width:140px"">

                            <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_line" />

                        </select>
                    </td>
                    <td class="table_edit_tr_td_label"><div align="right">车站:</div></td>
                    <td class="table_edit_tr_td_input">
                        <select id="q_stationID" name="q_stationID" style="width:140px">
                            <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_none" />
                        </select>

                        <c:set var="pVarName" scope="request" value="commonVariable"/>
                        <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_station" />

                    </td>
                    <td class="table_edit_tr_td_label">模式类型:</td>
                    <td class="table_edit_tr_td_input">
                        <select id="q_degradeModeID" name="q_degradeModeID"style="width:140px">
                            <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_modeTypes" />
                        </select>
                    </td>
                    <td class="table_edit_tr_td_label">处理标志:</td>
                    <td class="table_edit_tr_td_input">
                        <select id="q_hdlFlag" name="q_hdlFlag" style="width:140px">
                            <option value="">=请选择=</option>
                            <option value="1">已下发</option>
                            <option value="2">未下发</option>
                        </select>	
                    </td>
                    <td class="table_edit_tr_td_label" rowspan="2">

                        <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all_init" />
                        <c:set var="query" scope="request" value="1"/>
                        <c:set var="addClickMethod" scope="request" value="setControlNames('queryOp','q_hdlFlag#q_degradeModeID#q_lineID#q_stationID');setLineCardNames('queryOp','q_lineID','q_stationID','commonVariable','','','');"/>
                        <c:set var="clickMethod" scope="request" value="btnClick('queryOp','clearStart','detail');"/>
                        <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all" />

                    </td>
                </tr>
            </table>

            <c:import url="/jsp/common/common_template.jsp?template_name=version_eval" />

        </form>
        <c:import url="/jsp/common/common_template.jsp?template_name=common_status_table" />
        <!-- 表头 通用模板 -->
        <c:set var="pTitleName" scope="request" value="列表"/>
        <c:set var="pTitleWidth" scope="request" value="50"/>
        <c:import url="/jsp/common/common_template.jsp?template_name=common_table_title" />

        <div id="clearStartBlock" class="divForTableBlock">
            <div id="clearStartHead" class="divForTableBlockHead">
                <table id="DataTableHead" class="table_list_block">
                    <!--说明：列的序号从0开始 isDigit：false 表示按字符串排序 ：true表示按数值排序 sortedby：asc表示按升序 dec表示按降序 -->
                    <tr  class="table_list_tr_head_block" id="ignore">
                        <td style="width:50px" class="table_list_tr_col_head_block">
                            <input type="checkbox" name="rectNoAll" id="rectNoAll" onclick="selectAllRecord('detailOp', 'rectNoAll', 'rectNo', 'clearStart', 0); controlsByFlagWithoutCk('detailOp', ['modify']);controlsByFlag('detailOp', ['del', 'audit']);"/>
                        </td>	
                        <td style="width:100px" id="orderTd" class="table_list_tr_col_head_block" isDigit=false index="1" sortedby="asc" onclick="sortForTableBlock('clearStart');" >线路</td>
                        <td style="width:100px" id="orderTd" class="table_list_tr_col_head_block" isDigit=false index="2" sortedby="asc" onclick="sortForTableBlock('clearStart');" >车站</td>
                        <td style="width:100px" id="orderTd" class="table_list_tr_col_head_block" isDigit=false index="3" sortedby="asc" onclick="sortForTableBlock('clearStart');" >模式类型</td>
                        <td style="width:100px" id="orderTd" class="table_list_tr_col_head_block" isDigit=false index="4" sortedby="asc" onclick="sortForTableBlock('clearStart');" >开始时间</td>
                        <td style="width:100px" id="orderTd" class="table_list_tr_col_head_block" isDigit=false index="5" sortedby="asc" onclick="sortForTableBlock('clearStart');" >结束时间</td>
                        <td style="width:100px" id="orderTd" class="table_list_tr_col_head_block" isDigit=false index="6" sortedby="asc" onclick="sortForTableBlock('clearStart');" >设置操作员</td>
                        <td style="width:100px" id="orderTd" class="table_list_tr_col_head_block" isDigit=false index="7" sortedby="asc" onclick="sortForTableBlock('clearStart');" >取消操作员</td>
                        <td style="width:100px" id="orderTd" class="table_list_tr_col_head_block" isDigit=false index="8" sortedby="asc" onclick="sortForTableBlock('clearStart');" >处理标志</td>
                    </tr>
                </table>
            </div>

            <div id="clearStart" class="divForTableBlockData">
                <table class="table_list_block" id="DataTable" >

                    <c:forEach items="${ResultSet}" var="rs">
                        <tr class="table_list_tr_data"  onMouseOver="overResultRow('detailOp', this);" onMouseOut="outResultRow('detailOp', this);" 
                            onclick="document.getElementById('d_waterNo').value =${rs.waterNo};
                                    setSelectValuesByRow('detailOp', 'd_stationID', 'commonVariable');
                                    clickResultRow('detailOp', this, 'detail');
                                    set('detailOp','${rs.hdlFlag}');
                                    controlsByFlag('detailOp', ['del', 'modify']);
                                    controlsByFlagWithoutCk('detailOp', ['modify']);
                                    setPageControl('detailOp');" 
                            id="${rs.lineId}#${rs.stationId}#${rs.waterNo}#${rs.versionNo}#${rs.hdlFlag}"
                            flag="${rs.hdlFlag}">

                            <td style="width:50px" id="rectNo1" class="table_list_tr_col_data_block">
                                <input type="checkbox" name="rectNo" onclick="unSelectAllRecord('detailOp', 'rectNoAll', 'rectNo');
                                        controlsByFlag('detailOp', ['modify','del']);
                                        controlsByFlag('detailOp', ['del']);
                                        disableControlByFlag('detailOp', 'modify', '${rs.hdlFlag}');
                                        disableControlByFlag('detailOp', 'del', '${rs.hdlFlag}');"  
                                       value="${rs.waterNo}#${rs.versionNo}#${rs.hdlFlag}"
                                       flag="${rs.hdlFlag}">
                                </input>
                            </td>
                            <td style="width:100px" id="lineID" class="table_list_tr_col_data_block">
                                ${rs.lineIdText}
                            </td>
                            <td style="width:100px" id="stationID" class="table_list_tr_col_data_block">
                                ${rs.stationIdText}
                            </td>
                            <td style="width:100px" id="degradeModeID" class="table_list_tr_col_data_block">
                                ${rs.degradeModeIdText}
                            </td>
                            <td style="width:100px" id="startTime" class="table_list_tr_col_data_block">
                                ${rs.startTime}
                            </td>
                            <td style="width:100px" id="endTime" class="table_list_tr_col_data_block">
                                ${rs.endTime}
                            </td>
                            <td style="width:100px" id="setOperID" class="table_list_tr_col_data_block">
                                ${rs.setOperId}
                            </td>
                            <td style="width:100px" id="cancelOperID" class="table_list_tr_col_data_block">
                                ${rs.cancelOperId}
                            </td>
                            <td style="width:100px" id="hdlFlag" class="table_list_tr_col_data_block">
                                ${rs.hdlFlagText}
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

        <FORM method="post" name="detailOp" id="detailOp" action="DegradeModeRecd" >
            <input type="hidden" name="expAllFields" id="d_expAllFields" value="lineIdText,stationIdText,degradeModeIdText,startTime,endTime,setOperId,cancelOperId,hdlFlagText" />
            <input type="hidden" name="expFileName" id="d_expFileName" value="降级模式使用记录.xlsx" />
            <input type="hidden" name="divId" id="d_divId" value="clearStartHead" />
            <input type="hidden" name="methodUrl" id="d_methodUrl" value="/DegradeModeRecdExportAll" />

            <c:set var="divideShow" scope="request" value="1"/>
            <c:import  url="/jsp/common/common_template_web_variable.jsp?template_name=common_web_variable" />

            <DIV id="detail" class="divForTableDataDetail" >
                <table  class="table_edit_detail">
                    <tr class="table_edit_tr">
                        <td class="table_edit_tr_td_label">线路:</td>
                        <td class="table_edit_tr_td_input">
                            <select  name="d_lineID" id="d_lineID" onChange="setSelectValues('detailOp', 'd_lineID', 'd_stationID', 'commonVariable');" 
                                     require="true" dataType="LimitB" min="1" msg="没有选择线路" style="width:120px">
                                <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_line" />
                            </select>	
                        </td>
                        <td class="table_edit_tr_td_label">车站:</td>
                        <td class="table_edit_tr_td_input">
                            <select id="d_stationID" name="d_stationID" require="true" dataType="LimitB" min="1" msg="没有选择车站" style="width:120px">
                                <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_none" />

                            </select>
                            <c:set var="pVarName" scope="request" value="commonVariable"/>
                            <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_station" />
                        </td>
                        <td class="table_edit_tr_td_label">开始时间:</td>
                        <td class="table_edit_tr_td_input">
                            <input type="text" name="d_startTime" id="d_startTime" style="width:110px" dataType="ICCSDateTime1"  msg="配置日期格式为[yyyyMMddHHmmss]"/>
                            <a href="javascript:openDetailCalenderDialogByIDx('d_startTime','false');">
                                <img src="./images/calendar.gif" width="15" height="15" border="0" style="block"/>
                            </a>
                        </td>
                        <td class="table_edit_tr_td_label">结束时间:</td>
                        <td class="table_edit_tr_td_input">
                            <input type="text" name="d_endTime" id="d_endTime" style="width:110px" dataType="ICCSDateTime1"  msg="配置日期格式为[yyyyMMddHHmmss]"/>
                            <a href="javascript:openDetailCalenderDialogByIDx('d_endTime','false');">
                                <img src="./images/calendar.gif" width="15" height="15" border="0" style="block"/>
                            </a>
                        </td>
                    </tr>
                    <tr class="table_edit_tr">
                        <td class="table_edit_tr_td_label">模式类型:</td>
                        <td class="table_edit_tr_td_input">
                            <select id="d_degradeModeID" name="d_degradeModeID" require="true" dataType="LimitB" min="1" msg="没有模式类型" style="width:120px">
                                <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_modeTypes" />
                            </select>
                        </td>
                        <td class="table_edit_tr_td_label">设置操作员:</td>
                        <td class="table_edit_tr_td_input">
                            <!--                            <input type="text" name="d_setOperID" id="d_setOperID" require="false" style="width:120px" maxlength="6" dataType="NotEmpty"  msg="操作员为6位英文数字字符且不为空！"/>-->
                            <input type="text"   name="d_setOperID"  id="d_setOperID" style="width:120px" maxLength="6"  dataType="NotEmpty|LimitContainChinese" min="6" max="6" msg="设置操作员为6位英文数字字符且不为空！"/>

                        </td>
                        <td class="table_edit_tr_td_label">取消操作员:</td>
                        <td class="table_edit_tr_td_input">
                            <!--                            <input type="text" name="d_cancelOperID" id="d_cancelOperID" require="false" style="width:110px" maxlength="6" dataType="NumAndEng|LimitB|NotEmpty" min="0" max="6" msg="操作员为6位英文数字字符且不为空!"/>-->
                            <input type="text"   name="d_cancelOperID"  id="d_cancelOperID" style="width:120px" maxLength="6"  dataType="NotEmpty|LimitContainChinese" min="6" max="6" msg="取消操作员为6位英文数字字符且不为空！"/>
                        </td>
                    </tr>
                </table>
            </DIV>
            <input type="hidden" id="d_waterNo" name="d_waterNo" value=""/>

            <c:import url="/jsp/common/common_template.jsp?template_name=version_eval" />

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

            <c:set var="addClickMethod" scope="request" value="setUpdatePrimaryKey('detailOp','d_waterNo');"/>
            <c:set var="clickMethod" scope="request" value="btnClick('detailOp','clearStart','detail','','clearStartHead');"/>
            <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all" />
            <br/>
        </FORM>

        <!-- 状态栏 通用模板 -->

    </body>
</html>