<%-- 
    Document   : DevParaVerCur
    Created on : 2017-6-22, 19:01:09
    Author     : zhouyang
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>参数 设备参数状态查询</title>
        <script language="javascript" type="text/javascript" charset="utf-8" src="js/common_form.js"></script>
        <script language="javascript" type="text/javascript" charset="utf-8" src="js/common_form_extend.js"></script>
        <script language="javascript" type="text/javascript" charset="utf-8" src="js/Validator.js"></script>
        <script language="JavaScript" src="js/CalendarPop.js"></script>
        <link rel="stylesheet" type="text/css" href="css/frame/simple.css" title="Style"/>
        
    </head>
    
    <body onload="
            setTableRowBackgroundBlock('DataTable')"> 
        <c:set var="pTitleName" scope="request" value="列表"/>
        <c:set var="pTitleWidth" scope="request" value="50"/>
        <c:import url="/jsp/common/common_template.jsp?template_name=common_table_title" />
        <div id="clearStartBlock" class="divForTableBlock_tall">
            <div id="clearStart" class="divForTableBlockData_tall" >
                <table class="listDataTable" id="DataTable" >
                    <tr class="trDataHead" id="ignore">
                    <td  width="550"   align="center" nowrap="1" class="trTitle" onyes="head">设备ID</td>
                    </tr>
                    <c:forEach items="${ResultSet}" var="rs">
                        
                        <tr class="table_list_block" id="${rs.device_id}" 
                		onClick="clickResultRowForDialog('formOp',this);" 
                		onMouseOver="overResultRow('formOp',this);" 
                		onMouseOut="outResultRow('formOp',this);" >
                            <td  id="checkBillNo" width="550"  align="center" nowrap="1">
                                ${rs.device_id}
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            
            </div>
        </div>
        
        <form name="formOp" method="post" action="">
    
             
            <center>
                <input type="hidden" id="allSelectedIDs" name="allSelectedIDs" value=""/>
                <input type="hidden" id="rowSelected" name = "rowSelected" value="" />
                <input type="button" id="submit" name="submit" value="确定" onClick="selectItemsForDialogDevID('clearStart','formOp')" />
                <input type="button" id="cancel" name="cancel" value="取消" onClick="selectItemsForDialog('clearStart','formOp')"/>
            </center>           
        </form>
        <c:import url="/jsp/common/common_template.jsp?template_name=common_status_table" />
    </body>
</html>
