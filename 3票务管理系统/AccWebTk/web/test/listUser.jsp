<%-- 
    Document   : listUser
    Created on : 2017-5-4, 11:22:19
    Author     : hejj
--%>


<%@page language="java"  contentType="text/html" pageEncoding="GB18030"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=GB18030">
        <title>操作员</title>

    </head>
    <body>
        <script language="javascript" type="text/javascript" charset="GBK" >
            function clickForQuery() {
                alert("operatorQry");
                document.getElementById("operatorQry").submit();

            }
            ;


            function setDetailFormContents(formName, detailForm) {
                 alert(detailForm);
                if (detailForm != "") {
                    var detail = document.getElementById(detailForm);
                    	alert(detail);
                    if (detail != null) {
                        var detailTable = detail.children[0];//table
                        while (detailTable.children[0].tagName != "TR") {
                            detailTable = detailTable.children[0];
                        }

                        var currentLine = getCurrentLine(formName);
                        //alert(currentLine);
                        if (currentLine != "") {
                            var currentObject = document.getElementById(currentLine);
                            var tmp = currentObject.children.length
                            if (tmp != 0) {
                                for (var i = 0; i < tmp; i++) {
                                    var child = currentObject.children[i];
                                    //alert("child.id=" + child.id + " detail=" + detail.id + " tmpId=" + "d_" + child.id);

                                    /******** 设置detailForm **********/
                                    if (child.id != "") {
                                        //alert(child.id + " to find " + "d_" + child.id);
                                        var tmpObj = document.getElementById("d_" + child.id);//明细输入框id加前缀 d_
                                        if (tmpObj != null && tmpObj != "undefined") {
                                            switch (tmpObj.tagName) {
                                                case "INPUT":
                                                {
                                                    switch (tmpObj.type) {
                                                        case "hidden":
                                                        {
                                                        }
                                                        case "text":
                                                        {
                                                            tmpObj.value = child.innerHTML;
                                                            if (child.children[0] != null && child.children[0].tagName == 'A') {
                                                                tmpObj.value = child.children[0].innerHTML;
                                                            }
                                                            if (child.children[0] != null && child.children[0].tagName == 'INPUT') {
                                                                tmpObj.value = child.children[0].value;
                                                            }
                                                            break;
                                                        }
                                                        case "radio":
                                                        {
                                                            var radioGroup = document.getElementsByName("d_" + child.id);
                                                            //												alert("name="+"d_" + child.id);
                                                            if (radioGroup == null)
                                                                break;
                                                            //												alert("radioGroup not empty");
                                                            for (var k = 0; k < radioGroup.length; k++) {
                                                                var radioGroupChild = radioGroup[k];
                                                                var cmpValue = -1;
                                                                //													alert("radioGroupChild.value="+radioGroupChild.value);
                                                                //													alert("child.innerHTML="+child.innerHTML);
                                                                if (child.innerHTML == "是")
                                                                    cmpValue = 1;
                                                                if (child.innerHTML == "否")
                                                                    cmpValue = 0;
                                                                //													alert("cmpValue="+cmpValue);
                                                                if (radioGroupChild.value == cmpValue)
                                                                {
                                                                    //radioGroupChild.defaultChecked = true;
                                                                    radioGroupChild.checked = true;
                                                                    break;
                                                                }
                                                            }

                                                            break;
                                                        }
                                                    }//switch
                                                    break;
                                                }
                                                case "SELECT":
                                                {
                                                    if (tmpObj.type == "select-one") {
                                                        for (j = 0; j < tmpObj.options.length; j++) {
                                                            // alert("="+tmpObj.options[j].value+"= =" + tmpObj.options[j].text+"= =" +child.innerHTML+"=" + (tmpObj.options[j].value == child.innerHTML || tmpObj.options[j].text ==child.innerHTML));
                                                            if (tmpObj.options[j].value == child.innerHTML || tmpObj.options[j].text == child.innerHTML) {
                                                                tmpObj.options[j].selected = "true";
                                                                break;
                                                            }
                                                        }
                                                    }
                                                    if (tmpObj.type == "select-multiple") {
                                                        for (j = 0; j < tmpObj.options.length; j++) {
                                                            var idx = child.innerHTML.indexOf(tmpObj.options[j].text);
                                                            var idx1 = child.innerHTML.indexOf(tmpObj.options[j].value);
                                                            tmpObj.options[j].selected = false;
                                                            //		alert("tmpObj.options[j].selected="+tmpObj.options[j].selected);
                                                            //		alert("tmpObj.options[j].defaultSelected="+tmpObj.options[j].defaultSelected);

                                                            if (idx != -1 || idx1 != -1) {
                                                                tmpObj.options[j].selected = true;
                                                            }
                                                        }

                                                    }
                                                    break;
                                                }
                                            }
                                        }

                                        tmpObj = document.getElementById("d1_" + child.id);//明细输入框id加前缀 d_
                                        if (tmpObj != null && tmpObj != "undefined") {
                                            switch (tmpObj.tagName) {
                                                case "INPUT":
                                                {
                                                    switch (tmpObj.type) {
                                                        case "text":
                                                        {
                                                            tmpObj.value = child.innerHTML;
                                                            break;
                                                        }
                                                    }//switch
                                                    break;
                                                }
                                                case "SELECT":
                                                {
                                                    tmpObj.selectedIndex = 1; /***********  不能使用 selectedIndex 属性 ****************/
                                                    break;
                                                }
                                            }
                                        }


                                    }

                                    /******** 设置detailForm **********/

                                }//for
                            }//if tmp
                        }//if currentLine
                    }//if detailObj
                }//if detailForm
            }
            ;

        </script>

        <table width="95%" align="center" class="tableStyle">
            <tr align="center" class="trTitle">
                <td>
                    操作员
                </td>
            </tr>
        </table>
        <FORM method="post"  name="operatorQry" id="operatorQry" action="list/selectUserByID" >

            <table width="95%" align="center" >
                <tr >
                    <td width="10%">
                        <div align="right">操作员ID:</div>
                    </td>
                    <td width="10%">
                        <div align="left">
                            <input type="text" name="q_sysOperatorId"  require="false" size="10" maxLength="6"  dataType="LimitB"  min="1" max="6" msg="操作员ID应不为空且最多6个字符"/>
                        </div>
                    </td>
                    <td width="10%">
                        <div align="right">姓名</div>
                    </td>
                    <td width="10%">
                        <div align="left">
                            <input type="text" name="q_sysOperatorName"  require="false"  size="10"  dataType="LimitB"  min="1" msg="姓名应不为空"/>
                        </div>
                    </td>
                    <td align="center" width="10%">
                        <input type="button" id="btQuery" name="query"  value="查询" onclick="clickForQuery();" />

                    </td>
                </tr>
            </table>

        </FORM>
        <DIV id="opClearStart" align="center" style="position:relative; left:20; width:95%; height:50%; layer-background-color:#99CC99; border:thin outset #ffffff; z-index:1; overflow: scroll;">
            <table class="listDataTable" id="DataTable" align="left">
                <tr class="trDataHead" id="ignore">
                    <td width="10%"  align="center" nowrap="1">
                        <input type="checkbox" name="opRectNoAll" onClick="selectAllRecord('operatorOp', 'opRectNoAll', 'opRectNo', 'opClearStart', 0);"/>
                    </td>
                    <td id="sysOperatorId" width="20%"  align="center" nowrap="1" class="listTableHead" onclick="JM_PowerListByIDs(this.parentElement.parentElement.parentElement.parentElement, this.parentElement.parentElement.parentElement, 1)" onyes="head">操作员ID</td>
                    <td id="sysOperatorName" width="20%"  align="center" nowrap="1" class="listTableHead" onclick="JM_PowerListByIDs(this.parentElement.parentElement.parentElement.parentElement, this.parentElement.parentElement.parentElement, 2)" onyes="head">姓名</td>
                    <td id="sysEmployeeId" width="20%"  align="center" nowrap="1" class="listTableHead" onclick="JM_PowerListByIDs(this.parentElement.parentElement.parentElement.parentElement, this.parentElement.parentElement.parentElement, 3)" onyes="head">职员ID</td>
                    <td id="sysExpiredDate" width="20%"  align="center" nowrap="1" class="listTableHead" onclick="JM_PowerListByIDs(this.parentElement.parentElement.parentElement.parentElement, this.parentElement.parentElement.parentElement, 4)" onyes="head">失效日期</td>
                    <td id="sysStatus" width="20%"  align="center" nowrap="1" class="listTableHead" onclick="JM_PowerListByIDs(this.parentElement.parentElement.parentElement.parentElement, this.parentElement.parentElement.parentElement, 5)" onyes="head">状态</td>
                </tr>
                <c:forEach items="${users}" var="user">  

                    <tr  id="opRow" class="trData" onClick="setDetailFormContents('opClearStart', 'operatorDetail');" onMouseOver="overResultRow('opClearStart', this);" onMouseOut="outResultRow('opClearStart', this);" >

                        <td width="10%"  align="center" nowrap="1">
                            <input type="checkbox" name="opRectNo" onClick="unSelectAllRecord('operatorOp', 'opRectNoAll', 'opRectNo');"/>
                        </td>
                        <td id="sysOperatorId" width="20%"  align="center" nowrap="1" >
                            ${user.sys_operator_id}
                        </td>
                        <td id="sysOperatorName" width="20%"  align="center" nowrap="1" >
                            ${user.sys_operator_name}
                        </td>
                        <td id="sysEmployeeId" width="20%"  align="center" nowrap="1" >
                            ${user.sys_employee_id}
                        </td>
                        <td id="sysExpiredDate" width="20%"  align="center" nowrap="1" >
                            ${user.sys_expired_date}
                        </td> 		
                        <td id="sysStatus" width="20%"  align="center" nowrap="1" >
                            ${user.sys_status}		
                        </td> 	
                    </tr>

                </c:forEach>

                <tr id="ignore">
                    <td align="right" colSpan="13"></td>
                </tr>
            </table>
        </DIV>
        <form name="operatorOp" method="post" action="list/modify" >


            <DIV id="operatorDetail" align="center" style="position:relative; left:20; width:95%; height:10; layer-background-color:#99CC99; border:thin outset #ffffff; z-index:1">
                <table id="oplistDataTableTB" align="center" class="listDataTable" >
                    <!--		<tr><input type="hidden" name="allSelectedOpIDs"/></tr> -->
                    <tr align="center" bgColor="#EFEFEF">
                        <td width="10%">
                            <div align="right">操作员ID:</div>
                        </td>
                        <td width="10%">
                            <div align="left">
                                <input type="text"   name="sysOperatorId" id="d_sysOperatorId" size="10" maxLength="6" require="true" dataType="LimitB"  min="1" max="8" msg="操作员ID应不为空且最多8个字符"/>
                            </div>
                        </td>
                        <td width="10%">
                            <div align="right">姓名</div>
                        </td>
                        <td width="10%">
                            <div align="left">
                                <input type="text"   name="sysOperatorName"  id="d_sysOperatorName" size="10" require="true" dataType="LimitB"  min="1" msg="姓名应不为空"/>
                            </div>
                        </td>
                        <td width="10%">
                            <div align="right">职员ID</div>
                        </td>
                        <td width="10%">
                            <div align="left">
                                <input type="text"   name="sysEmployeeId"  id="d_sysEmployeeId" size="10" require="true" dataType="LimitB"  min="1" msg="职员ID应不为空"/>
                            </div>
                        </td>
                        <td width="10%">
                            <div align="right">失效日期:</div>
                        </td>
                        <td width="10%">
                            <div align="left">
                                <input type="text" name="sysExpiredDate" id="d_sysExpiredDate" size="10"  require="true" dataType="ICCSDate"  msg="配置日期格式为[****-**-**]"/>
                                <a href="javascript:openCalenderDialogByID('d_sysExpiredDate','false');">
                                    <img src="./images/calendar.gif" width="15" height="15" border="0" style="block"/>
                                </a>
                            </div>
                        </td>
                        <!-- <td width="10%"><div align="left"><input type="text"   name="name"  id="d_name" size="10" require="true" dataType="Custom"  regexp="0.\d\d\d|1.000" msg="应小于或等于1.0,格式应是0.xx"/></div></td> -->
                    </tr>
                    <tr align="center" bgColor="#EFEFEF">
                        <!-- yangjihe 2006-10-30号新增 -->
                        <td width="10%">
                            <div align="right">状态:</div>
                        </td>		 

                        <td width="14%">
                            <div align="left">
                                <select id="d_sysStatus" name = "sysStatus" >        

                                    <option value="0">

                                        可用
                                    </option>

                                    <option value="1">

                                        禁用
                                    </option>

                                    <option value="2">

                                        已登陆
                                    </option>
                                </select>
                            </div>
                        </td>

                    </tr>
                </table>
            </DIV>

            <input type="button" id="btAdd" name="add" disabled="false" value="增加" class="buttonStyle" onclick="btClick();" />
            <input type="button" id="btModify" name="modify" disabled="false" value="修改" class="buttonStyle" onclick="btClick();" />
            <input type="button" id="btDelete" name="delete" disabled="false" value="删除" class="buttonStyle" onclick="btClick();" />
            <br/>
        </form>


    </body>
</html>
