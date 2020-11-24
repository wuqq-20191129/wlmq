<%@ page language="java" contentType="text/html;charset=GBK" %>
<html>
    <head>
        <title>选择未来参数生效时间</title>
        <script language="JavaScript" src="../../js/CalendarPop.js"></script>
        <link rel="stylesheet" type="text/css" href="../../css/simple.css" title="Style"/>
        <script>
            function selectCondition() {
                var startDate = document.forms['versionForm'].getElementsByTagName("input")['d_ver_date_begin'].value;
                var endDate = document.forms['versionForm'].getElementsByTagName("input")['d_ver_date_end'].value;
                var current = new Date();
                var year = current.getFullYear();
                var month = current.getMonth() + 1;
                var day = current.getDate();
                var currentVersionStartDate = window.dialogArguments;
                //  		alert("currentVersionStartDate="+currentVersionStartDate);
                var remark = document.forms['versionForm'].getElementsByTagName("input")['d_ver_remark'].value;

                if (month < 10)
                    month = "0" + month;
                if (day < 10)
                    day = "0" + day;
                current = year + "-" + month + "-" + day
                //  		alert("current time:"+year+"-"+month+"-"+day);
                //  		alert("startDate="+startDate+" endDate"+endDate);
                var verRadiosOb = document.getElementsByName("generateversion");
                var selectedVer = -1;
                for (i = 0; i < verRadiosOb.length; i++)
                {
                    if (verRadiosOb[i].checked)
                        selectedVer = i;
                }
                if (startDate == "") {
                    alert("版本起始时间不能为空");
                    return;
                }
                if (endDate == "") {
                    alert("版本结束时间不能为空");
                    return;
                }
                if (selectedVer == -1) {
                    alert("请选择生成的版本");
                    return;
                }
                //                未来版本
                if (selectedVer == '1') {
                    if (startDate <= current)
                    {
                        alert("版本起始时间必须大于当前时间");
                        return;
                    }
                }
                //当前版本
                if (selectedVer == '0') {
                    if (startDate < current)
                    {
                        alert("版本起始时间不能小于当前时间");
                        return;
                    }
                    if (startDate > current)
                    {
                        alert("版本起始时间不能大于当前时间");
                        return;
                    }
                    if (currentVersionStartDate != null && currentVersionStartDate != "") {
                        if (startDate < currentVersionStartDate) {
                            alert("版本起始时间不能小于已存在的当前版本的起始时间" + currentVersionStartDate);
                            return;
                        }
                    }
                    if (endDate < current)
                    {
                        alert("版本结束时间不能小于当前时间");
                        return;
                    }
                }

                if (endDate <= startDate) {
                    alert("版本结束时间应大于版本起始时间");
                    return;
                }

                var retValues = startDate + ";" + endDate + ";" + selectedVer + ";" + remark;
                // 		alert("retValues="+retValues[0]+" endDate"+retValues[1]);
                window.returnValue = retValues;
                window.close();
            }
            function quit() {
                var retValues = "undefined"
                window.returnValue = retValues;
                window.close();
            }
            function preLoadVal() {
                //                    获取当前版本类型
                var verRadiosOb = document.getElementsByName("generateversion");
                var selectedVer = -1;
                for (i = 0; i < verRadiosOb.length; i++)
                {
                    if (verRadiosOb[i].checked)
                        selectedVer = i;
                }

                //            获取选择的时间
                var current = new Date();
                if (selectedVer == 1) {
                    current = current.valueOf();
                    current = current + 1 * 24 * 60 * 60 * 1000;
                    current = new Date(current);
                }
                var year = current.getFullYear();
                var month = current.getMonth() + 1;
                var day = current.getDate();

                if (month < 10)
                    month = "0" + month;
                if (day < 10)
                    day = "0" + day;

                var ver_begin_date;
                ver_begin_date = year + "-" + month + "-" + day;

                var ver_end_date;
                ver_end_date = (year + 100) + "-" + month + "-" + day;

                document.forms['versionForm'].getElementsByTagName("input")['d_ver_date_begin'].value = ver_begin_date;
                document.forms['versionForm'].getElementsByTagName("input")['d_ver_date_end'].value = ver_end_date;
            }
        </script>
    </head>
    <body bgColor="rgb(207,207,207)" background="" onload="preLoadVal();">
        <form name="versionForm" method="post" action="ShbglPzhiChzh.do">
            <table class="tableStyle">
                <tr>
                    <td width="150"><div align="right">版本起始时间:</div></td>
                    <td width="150"><div align="left">
                            <input type="text" name="d_ver_date_begin" size="10" readonly="1" onClick="openCalenderDialog(document.getElementsByTagName('input').d_ver_date_begin);" require="false"/>
                            <!--	<a href="javascript:openCalenderDialog(document.all.d_ver_date_begin);"> -->
                            <img src="../../images/calendar.gif" width="15" height="15" border="0" style="block" onclick="openCalenderDialog(document.getElementsByTagName('input').d_ver_date_begin, 'true');"/>
                            <!--	</a> -->
                        </div>
                    </td>
                </tr>
                <tr>
                    <td width="150"><div align="right">版本结束时间:</div></td>
                    <td width="150"><div align="left">
                            <input type="text" name="d_ver_date_end" size="10" readonly="1" onClick="openCalenderDialog(document.getElementsByTagName('input').d_ver_date_end, 'true');" require="false"/>
                            <img src="../../images/calendar.gif" width="15" height="15" border="0" style="block" onclick="openCalenderDialog(document.getElementsByTagName('input').d_ver_date_end, 'true');"/>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td width="150"><div align="right">版本备注:</div></td>
                    <td width="150" >
                        <div align="left">
                            <input type="text" name="d_ver_remark" id="d_ver_remark" size="18" maxlength="10"/></div>
                    </td>
                </tr>
                <tr>
                    <td width="150"><div align="right">
                            <input type="radio"  name="generateversion" id="generateversion"  onClick="preLoadVal();" />当前版本</div></td>
                    <td width="150"><div align="left">
                            <input type="radio"  checked name="generateversion" id="generateversion" onClick="preLoadVal();" />未来版本</div>
                    </td>
                </tr>
                <tr>
                    <td width="150"><div align="right">
                            <!-- <input type="button" name="submit" value="确定" onClick="selectCondition();" /><input type="button" name="cancel" value="取消" onClick="quit();"/> -->
                        </div></td>
                </tr>
            </table>

            <center>
                <input type="button" name="submit" value="确定" onClick="selectCondition();" />
                <input type="button" name="cancel" value="取消" onClick="quit();"/>
            </center>

        </form>
    </body>
</html>