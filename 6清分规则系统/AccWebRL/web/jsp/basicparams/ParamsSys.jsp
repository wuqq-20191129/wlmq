<%-- 
    Document   : ParamsSys
    Created on : 2017-9-7
    Author     : luck
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>系统参数设置</title>


        <script language="javascript" type="text/javascript" charset="utf-8" src="js/common_form.js"></script>
        <script language="javascript" type="text/javascript" charset="utf-8" src="js/common_form_extend.js"></script>
        <script language="javascript" type="text/javascript" charset="utf-8" src="js/Validator.js"></script>
        <script language="JavaScript" type="text/javascript" charset="utf-8" src="js/CalendarPop.js"></script>
        <script language="JavaScript" type="text/javascript" charset="utf-8" src="js/icProduceOut.js"></script>
        <script language="JavaScript" type="text/javascript" charset="utf-8" src="js/icStorageProductBill.js"></script>
        <link rel="stylesheet" type="text/css" href="css/frame/simple.css" title="Style"/>
    </head>
    <!-- 
      setInvisable('detailOp', 'clone');
     setPrimaryKeys('detailOp', 'd_lineID#d_stationID#d_deviceID#d_deviceType');
    -->
    <!--
    initDocument('formQuery','detail');
    initDocument('formOp','detail');
    setControlsDefaultValue('formQuery');
    setListViewDefaultValue('formOp','clearStart');
    setQueryControlsDefaultValue('formQuery','formOp');
    setPrimaryKeys('formOp','d_billNo');
    -->
    <body onload="initDocument('queryOp', 'detail');
            initDocument('detailOp', 'detail');
            setControlsDefaultValue('queryOp');
            setListViewDefaultValue('detailOp', 'clearStart');
            setQueryControlsDefaultValue('queryOp', 'detailOp');
            setPrimaryKeys('detailOp', 'd_code#d_typeCode');
            setPageControl('detailOp');
            setTableRowBackgroundBlock('DataTable')">

        <!--<body >-->
        <table  class="table_title">
            <tr align="center" class="trTitle">
                <td colspan="4">系统参数设置
                </td>
            </tr>
        </table>

        <!-- 表头 通用模板 -->

        <c:set var="pTitleName" scope="request" value="查询"/>
        <c:set var="pTitleWidth" scope="request" value="50"/>
        <c:import  url="/jsp/common/common_template.jsp?template_name=common_table_title" />

        <form method="post" name="queryOp" id="queryOp" action="paramsAction">
            <!-- 页面用到的变量 通用模板 -->

            <c:set var="divideShow" scope="request" value="1"/>
            <c:import  url="/jsp/common/common_template_web_variable.jsp?template_name=common_web_variable" />

            <table  class="table_edit">
                <tr class="table_edit_tr">

                    <td class="table_edit_tr_td_label">类型代码:</td>
                    <td class="table_edit_tr_td_input">
                        <input type="text" id="q_typeCode" name="q_typeCode"  maxlength="2" require="false" dataType="NumAndEng" min="0" max="2" msg="类型代码应为1位或者2位的字母或数字" />
                    </td>
                    <td class="table_edit_tr_td_label">参数代码:</td>
                    <td class="table_edit_tr_td_input">                   
                        <input type="text" id="q_code"  name="q_code"  maxlength="2" require="false" dataType="NumAndEng" min="0" max="2" msg="代码应为1位或者2位的字母或数字" />                    
                    </td>
                    <td class="table_edit_tr_td_label">版本状态:</td>
                    <td class="table_edit_tr_td_input">
                        <select id="q_recorqFlag" name="q_recorqFlag" >
                            <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_recordFlags" />
                        </select>
                    </td>
                </tr>
                <tr class="table_edit_tr">
                    <td class="table_edit_tr_td_label" >创建时间:</td>
                    <td class="table_edit_tr_td_input" colspan="3">
                        <input type="text" name="q_beginTime" id="q_beginTime" value="" size="12" require="false" dataType="Date" format="ymd"  msg="生成时间格式为(yyyy-mm-dd)!" />
                        <a href="javascript:openCalenderDialogByID('q_beginTime','false');">
                            <img src="./images/calendar.gif" width="15" height="15" border="0" style="block"/>
                        </a>
                        --
                        <input type="text" name="q_endTime" id="q_endTime" value="" size="12" require="false"  dataType="Date|ThanDate|dateDiff" format="ymd" to="q_beginTime"
                               msg="生成结束时间格式为(yyyy-mm-dd)且大于等于开始时间,查询范围90天!" />
                        <a href="javascript:openCalenderDialogByID('q_endTime','false');">
                            <img src="./images/calendar.gif" width="15" height="15" border="0" style="block"/>
                        </a>
                    </td>



                    <td class="table_edit_tr_td_label" rowspan="1">

                        <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all_init" />
                        <c:set var="query" scope="request" value="1"/>
                        <c:set var="addClickMethod" scope="request" value="setControlNames('queryOp','q_recorqFlag#q_typeCode#q_code#q_beginTime#q_endTime')"/>
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
                        <td   class="table_list_tr_col_head_block">
                            <input type="checkbox" name="rectNoAll" id="rectNoAll" onclick="selectAllRecord('detailOp', 'rectNoAll', 'rectNo', 'clearStart', 0);setPageControl('detailOp');controlsByFlag('detailOp', ['del', 'audit'])"/>
                        </td>	
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="1"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >类型代码</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="2"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 110px">类型值</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="3"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >参数代码</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="4"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 110px">参数值</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="5"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 110px">参数描述</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="6"  sortedby="asc" onclick="sortForTableBlock('clearStart');" > 版本状态</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="7"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 120px">创建时间</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="8" sortedby="asc"  onclick="sortForTableBlock('clearStart');" >创建人</td>
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
                            onclick="
                                    clickResultRow('detailOp', this, 'detail');
                                    controlsByFlagWithoutCk('detailOp', ['modify']);
                                    controlsByFlag('detailOp', ['del', 'audit']);
                                    setPageControl('detailOp');" 
                            id="${rs.typeCode}#${rs.code}#${rs.version}" flag="${rs.recordFlag}">

                            <td id="rectNo1" class="table_list_tr_col_data_block">
                                <input type="checkbox" name="rectNo" id="rectNo" onclick="unSelectAllRecord('detailOp', 'rectNoAll', 'rectNo');"
                                       value="${rs.typeCode}#${rs.code}#${rs.version}"  flag="${rs.recordFlag}">

                                </input>
                            </td>
                            <td  id="typeCode" class="table_list_tr_col_data_block" >
                                ${rs.typeCode}
                            </td>
                            <td  id="typeDescription" class="table_list_tr_col_data_block" style="width: 110px">
                                ${rs.typeDescription}                                
                            </td>
                            <td  id="code" class="table_list_tr_col_data_block">
                                ${rs.code}
                            </td>
                            <td  id="value" class="table_list_tr_col_data_block" style="width: 110px">
                                ${rs.value}
                            </td>
                            <td  id="description" class="table_list_tr_col_data_block" style="width: 110px">
                                ${rs.description}
                            </td>
                            <td  id="recordFlag" class="table_list_tr_col_data_block" >
                                ${rs.recordFlagName}
                            </td>
                           
                            <td  id="createTime" class="table_list_tr_col_data_block" style="width: 120px">
                                ${rs.createTime}
                            </td>
                            <td  id="createOperator" class="table_list_tr_col_data_block" >
                                ${rs.createOperator}
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

        <FORM method="post" name="detailOp" id="detailOp" action="paramsAction" >

            <c:set var="divideShow" scope="request" value="1"/>
            <c:import  url="/jsp/common/common_template_web_variable.jsp?template_name=common_web_variable" />
            
             <input type="hidden" name="expAllFields" id="d_expAllFields"
                   value="getTypeCode,getTypeDescription,getCode,getValue,getDescription,getRecordFlagName,getCreateTime,getCreateOperator" />
            <input type="hidden" name="expFileName" id="d_expFileName" value="系统参数设置.xlsx" />
            <input type="hidden" name="divId" id="d_divId" value="clearStartHead">
            <input type="hidden" name="methodUrl" id="d_methodUrl" value="/ParamsSysExportAll"/>

            <!--<DIV id="detail" align="center" class="divForTableData" style="position:relative; left:20; width:95%; height:10; layer-background-color:#99CC99; border:thin outset #ffffff; z-index:1;">-->
            <div id="detail"  class="divForTableDataDetail" >
                <table  class="table_edit_detail">
                    <tr class="table_edit_tr">
                        <td class="table_edit_tr_td_label">类型代码: </td>
                        <td class="table_edit_tr_td_input">
                            <input type="text" name="d_typeCode" id="d_typeCode"   require="true" maxlength="2" dataType="NumAndEng"  min="1" max="2"  msg="类型代码不能为空，由小于等于两个字符的字母或数字组成" />
                        </td>
                        <td class="table_edit_tr_td_label" >类型值:</td>
                        <td class="table_edit_tr_td_input">
                            <input type="text" name="d_typeDescription" id="d_typeDescription"  require="true" maxlength="60" dataType="LimitContainChinese" min="1" max="60"  msg="类型值不能为空，长度不超过60个字符,中文占用3个字符（最多20个汉字）" />
                        </td>
                        <td class="table_edit_tr_td_label" >参数代码:</td>
                        <td class="table_edit_tr_td_input">
                            <input type="text" name="d_code" id="d_code" require="true"  maxlength="2" dataType="NumAndEng"  min="1" max="2"  msg="参数代码不能为空，由小于等于两个字符的字母或数字组成" />
                        </td>

                    </tr>
                    <tr class="table_edit_tr">
                        <td class="table_edit_tr_td_label" >参数值:</td>
                        <td class="table_edit_tr_td_input">
                            <input type="text" name="d_value" id="d_value" require="true"  maxlength="60" dataType="LimitContainChinese" min="1" max="60"  msg="参数值不能为空，长度不超过60个字符,中文占用3个字符（最多20个汉字）" />
                        </td>
                        <td class="table_edit_tr_td_label" >参数描述:</td>
                        <td class="table_edit_tr_td_input">
                            <input type="text" name="d_description" id="d_description" require="true"  maxlength="60"  dataType="LimitContainChinese" min="0" max="60" msg="参数描述不超过60个字符,中文为3个字符（最多20个中文）"/>
                        </td>

                    <input type="hidden" name="d_id" id="d_id" />


                    </tr>

                </table>
            </div>






            <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all_init" />
            <c:set var="add" scope="request" value="1"/>
            <c:set var="del" scope="request" value="1"/>
            <c:set var="modify" scope="request" value="1"/>
            <c:set var="save" scope="request" value="1"/>
            <c:set var="cancle" scope="request" value="1"/>
            <c:set var="audit" scope="request" value="1"/>
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
        <c:set var="addQueryMethod" scope="request" value=""/>
        <c:set var="addClickMethod" scope="request" value=""/>
        -->

       
    </body>
</html>
