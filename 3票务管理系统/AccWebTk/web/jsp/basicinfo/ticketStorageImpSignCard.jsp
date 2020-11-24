<%@ page contentType="text/html; charset=UTF-8" language="java"%>
<%
    String msg = (String) session.getAttribute("Message");
    String terminator = (String) session.getAttribute("Terminator");
    if (terminator == null) {
        terminator = "";
    }
    String fileName = (String) session.getAttribute("FileName");
    if (fileName == null) {
        fileName = "";
    }
%>

<html>
    <head>
        <title>导入记名卡申请信息</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />

        <link rel="stylesheet" type="text/css" href="xsl/css/simple.css" title="Style" />
        <script language="JavaScript" src="js/CalendarPop.js"></script>
        <script language="javascript" type="text/javascript" charset="UTF-8" src="js/Validator.js"></script>

        <script language="javascript" type="text/javascript" charset="UTF-8" src="js/import.js"></script>
    </head>

    <body >
        <table width="90%" border="0" align="center">
            <tr>
                <td>
                    <div align="center"><strong>记名卡申请数据导入 </strong></div>
                </td>
            </tr>
        </table>
        <form name="formOp" method="post"
              action="ticketStorageImpSignCard1?command=stimp"
              ENCTYPE="multipart/form-data"

              >
            <!-- onSubmit="return Validator.Validate(this,2);" -->
            <table width="95%" align="center" class="listDataTable">
                <tr align="center" bgColor="#EFEFEF">
                    <td width="10%" colspan="1">
                        <div align="right">申请站点:</div>
                    </td>
                    <td width="15%">
                        <div align="left">
                            <select id="d_station_id"
                                    name="d_station_id" dataType="Require" msg="申请站点不能为空!">
                                <option value="0101">三屯碑</option>
                                <option value="0102">新疆大学</option>
                                 <option value="0103">三道桥</option>
                            </select>
                        </div>
                    </td>
                    <td width="10%" colspan="1">
                        <div align="right">申请时间:</div>
                    </td>
                    <td width="15%">
                        <div align="left">
                            <input type="text"
                                   name="d_applyDate" id="d_applyDate" value="" size="12"
                                   require="true" dataType="Date" format="ymd"
                                   msg="申请时间格式为(YYYY-MM-dd)!" />
                            <a
                                href="javascript:openCalenderDialog(document.all.d_applyDate);">
                                <img src="./images/calendar.gif"
                                     width="12" height="15" border="0" style="block" />
                            </a>
                        </div>
                    </td>
                </tr>
                <tr align="center" bgColor="#EFEFEF">
                    <td width="20%">
                        <div align="right">导入文件(xls):</div>
                    </td>
                    <td colspan="3">
                        <div align="left"> 
                            <input name="makeFile" type="file" id="makeFile" 
                                   size="50" dataType="Filter" msg="请选择(.xls)文本文件！" accept="xls">
                        </div>
                    </td>
                </tr>
            </table>
            &nbsp;
            <table width="95%" align="center">

                <tr align="center" >
                    <td><div align="right">
                            <input name="confirm" type="button" value="确定" onclick="clearMsg();loading();">
                        </div>
                    </td>
                    <td><div align="left">
                            <input name="close" type="button" value="取消" onclick="window.close();">
                        </div>
                    </td>



                </tr>
            </table>
            <input type="hidden" name="_terminator" value="<%=terminator%>"/>
            <input type="hidden" name="_fileName" value="<%=fileName%>"/>

        </form>


        <table width="85%" align="center" >
            <tr bgcolor="#D1D1D1">
                <td width="20%" id="retMsg" class="status-font" >返回信息:
                    <font color="red">
                    <div style="height:145px;overflow:auto;">
                    ${msg}
                    </div>
                    </font>
                </td>
            </tr>
        </table>

        <div align="center" style="font-size:9pt;display:none;color:red" id="runPrompt">
            正在导入数据.............
        </div>




    </body>
</html>
