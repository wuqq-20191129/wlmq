<%-- 
    Document   : fare_submit
    Created on : 2017-6-23
    Author     : mqf
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>票价资料</title>


        <script language="javascript" type="text/javascript" charset="utf-8" src="js/common_form.js"></script>
        <script language="javascript" type="text/javascript" charset="utf-8" src="js/Validator.js"></script>
        <script language="JavaScript" src="js/CalendarPop.js"></script>
        <link rel="stylesheet" type="text/css" href="css/frame/simple.css" title="Style"/>
    </head>
        <body onload="initDocument('detailOp', 'detail');">
            
        <table  class="table_title">
            <tr align="center" class="trTitle">
                <td colspan="4">票价参数提交
                </td>
            </tr>
        </table>


        <!-- 表头 通用模板 -->
        <c:set var="pTitleName" scope="request" value="操作"/>
        <c:set var="pTitleWidth" scope="request" value="50"/>
        <c:import url="/jsp/common/common_template.jsp?template_name=common_table_title" />

        <FORM method="post" name="detailOp" id="detailOp" action="FareSubmit" >

            <c:set var="divideShow" scope="request" value="1"/>
            <c:import  url="/jsp/common/common_template_web_variable.jsp?template_name=common_web_variable" />


            <c:import url="/jsp/common/common_template.jsp?template_name=version_eval" />

            <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all_init" />
            <c:set var="add" scope="request" value="0"/>
            <c:set var="del" scope="request" value="0"/>
            <c:set var="modify" scope="request" value="0"/>
            <c:set var="save" scope="request" value="0"/>
            <c:set var="cancle" scope="request" value="0"/>
            <c:set var="clone" scope="request" value="0"/>
            <c:set var="submit" scope="request" value="1"/>
            <c:set var="print" scope="request" value="0"/>
            <c:set var="export" scope="request" value="0"/>
            <c:set var="btNext" scope="request" value="0"/>
            <c:set var="btNextEnd" scope="request" value="0"/>
            <c:set var="btBack" scope="request" value="0"/>
            <c:set var="btBackEnd" scope="request" value="0"/>

            <c:set var="clickMethod" scope="request" value="btnClick('detailOp','','');"/>
            <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all" />
            <br/>
        </FORM>

        <!-- 状态栏 通用模板 -->
        <c:import url="/jsp/common/common_template.jsp?template_name=common_status_table" />
    </body>
</html>
