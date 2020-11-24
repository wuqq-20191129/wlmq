<!DOCTYPE html>
<%@ page language="java" contentType="text/html;charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<xsl:template match="/">
    <html>
        <head>
            <title>模块权限分配</title>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
            <meta http-equiv="X-UA-Compatible" content="IE=edge,Chrome=1" />

            <script language="javascript" type="text/javascript" charset="utf-8" src="js/common_form.js"></script>
            <script language="javascript" type="text/javascript" charset="utf-8" src="js/moduleDistr.js"></script>
            <script language="javascript" type="text/javascript" charset="utf-8" src="js/dtree/dtree_test.js"></script>
            <link rel="stylesheet" type="text/css" href="css/frame/simple.css" title="Style"/>
            <link rel="stylesheet" type="text/css" href="js/dtree/dtree.css" title="Style"/>
            <script language="javascript">
                //权限集合，从权限树遍历获取选取的权限
                var ras;
                //权限表格表头
                var h1 = '', h2 = '', h3 = '权限分配';
                //权限表格第一列和第二列内容
                var c1 = '', c2 = '';
                //id为3位长的权限，这里为系统
                var r3 = '';
                function initTableData() {
                    ras = new Array();
                    //取得选择的权限，获得模块ID
                    var ds = document.getElementsByName(cbName);
                    var elements = new Array();
                    for (var i = 0; i < ds.length; i++) {
                        var element = ds[i];
                        if (element.checked == true) {
                            elements.push(element);
                        }
                    }

                    //根据ID获得名称
                    var nodes = new Array();
                    for (var j = 0; j < dc.aNodes.length; j++) {
                        for (var k = 0; k < elements.length; k++) {
                            if (elements[k].value == dc.aNodes[j].id && typeof (dc.aNodes[j].name) != 'undefined') {
                                nodes.push(dc.aNodes[j]);
                                break;
                            }
                        }
                    }

                    //遍历节点集合，使用迭代算法(以ID长度为迭代循环终止判断依据)，系统权限最多5级，其中第一级权限系统ID为3位长
                    var r2 = '', r4 = '', r6 = '', r8 = '', ra = '';
                    for (var i = 0; i < nodes.length; i++) {
                        if (nodes[i].id.length == 3) {
                            r3 = '';
                            r3 += nodes[i].name;
                            r3 += ',';
                        }
                        if (nodes[i].id.length == 2) {
                            r2 = '';
                            r2 += nodes[i].name;
                            r2 += ',';
                        }
                        if (nodes[i].id.length == 4) {
                            r4 = '';
                            r4 += r2;
                            r4 += nodes[i].name;
//                                r4+= ',';
                            if (!nodes[i]._hc) {
                                ra += r4;
                                ras.push(ra);
                                ra = '';
                            } else {
                                r4 += ',';
                            }
                        }
                        if (nodes[i].id.length == 6) {
                            r6 = '';
                            r6 += r4;
                            r6 += nodes[i].name;
//                                 r6+= ',';
                            if (!nodes[i]._hc) {
                                ra += r6;
                                ras.push(ra);
                                ra = '';
                            } else {
                                r6 += ',';
                            }

                        }
                        if (nodes[i].id.length == 8) {
                            r8 = '';
                            r8 += r6;
                            r8 += nodes[i].name;
                            ra += r8;
                            ras.push(ra);
                            ra = '';
                            //break;
                        }


                    }

                    //从组别名称表格取得选择的组别
                    var divOb = document.getElementById("clearStart");
                    var table = divOb.children[0];
//                        var h1='',h2='',h3='权限分配';
//                        var  c1='',c2='';
                    for (var i = 1; i < table.rows.length; i++) {
                        if (table.rows[i].cells[0].children[0].type == "checkbox" || table.rows[i].cells[0].children[0].type == "radio") {
                            if (table.rows[i].cells[0].children[0].checked) {
                                h1 = table.rows[0].cells[1].innerText;
                                c1 = table.rows[i].cells[1].innerText;

                            }
                        }
                    }

                    //从系统名称表格取得选择的系统
                    divOb = document.getElementById("clearStart1");
                    table = divOb.children[0];
//                        alert(table.rows[0].cells[1].innerText);
                    for (var i = 1; i < table.rows.length; i++) {
                        if (table.rows[i].cells[0].children[0].type == "checkbox" || table.rows[i].cells[0].children[0].type == "radio") {
                            if (table.rows[i].cells[0].children[0].checked) {
                                h2 = table.rows[0].cells[1].innerText;
                                c2 = table.rows[i].cells[1].innerText;
                            }
                        }
                    }
                }

                function expRight() {
                    initTableData();
//                        alert(h1 + '选择了：' + c1);
//                        alert(h2 + '选择了：' + c2);
//                        alert(h3 + '集合大小:' + ras.length);
//                        alert('权限分配系统:' + r3);
                    //创建excel表格
                    var oXL = new ActiveXObject("Excel.Application");
                    var oWB = oXL.Workbooks.Add();
                    var oSheet = oWB.ActiveSheet;
                    oSheet.Cells(1, 1).value = h1;
                    oSheet.Cells(1, 1).Font.Bold = true;
                    oSheet.Cells(1, 1).HorizontalAlignment = -4108;
                    oSheet.Cells(1, 2).value = h2;
                    oSheet.Cells(1, 2).Font.Bold = true;
                    oSheet.Cells(1, 2).HorizontalAlignment = -4108;
                    oSheet.Cells(1, 3).value = h3;
                    oSheet.Cells(1, 3).Font.Bold = true;
                    oSheet.Cells(1, 3).HorizontalAlignment = -4108;
//                        oSheet.Columns.AutoFit();

                    oSheet.Columns(1).columnwidth = 10;
                    oSheet.Columns(2).columnwidth = 15;
                    oSheet.Columns(3).columnwidth = 85;
                    for (var i = 0; i < ras.length; i++) {
                        oSheet.Cells(i + 2, 1).value = c1;
                        oSheet.Cells(i + 2, 2).value = c2;
                        oSheet.Cells(i + 2, 3).value = r3 + ras[i];
                    }
                    oXL.Visible = true;
//                        alert(document.getElementById("clearStart1"));
                }
                function printRight() {
                    initTableData();
                    var oXL = new ActiveXObject("Excel.Application");
                    var oWB = oXL.Workbooks.Add();
                    var oSheet = oWB.ActiveSheet;
                    oSheet.PageSetup.Orientation = 2;
                    oSheet.PageSetup.CenterFooter = "第&P页";
                    oSheet.Cells(1, 1).value = h1;
                    oSheet.Cells(1, 1).Font.Bold = true;
                    oSheet.Cells(1, 1).HorizontalAlignment = -4108;
                    oSheet.Cells(1, 2).value = h2;
                    oSheet.Cells(1, 2).Font.Bold = true;
                    oSheet.Cells(1, 2).HorizontalAlignment = -4108;
                    oSheet.Cells(1, 3).value = h3;
                    oSheet.Cells(1, 3).Font.Bold = true;
                    oSheet.Cells(1, 3).HorizontalAlignment = -4108;
//                        oSheet.Columns.AutoFit();

                    oSheet.Columns(1).columnwidth = 10;
                    oSheet.Columns(2).columnwidth = 15;
                    oSheet.Columns(3).columnwidth = 85;
                    for (var i = 0; i < ras.length; i++) {
                        oSheet.Cells(i + 2, 1).value = c1;
                        oSheet.Cells(i + 2, 2).value = c2;
                        oSheet.Cells(i + 2, 3).value = r3 + ras[i];
                        oSheet.Cells(i + 1, 1).Borders.Weight = 2;
                        oSheet.Cells(i + 1, 2).Borders.Weight = 2;
                        oSheet.Cells(i + 1, 3).Borders.Weight = 2;
                        if (i > 0 && i % 30 == 0) {
//                                alert(i);
                            oSheet.Rows(i).PageBreak = 1;
                        }
                    }
                    oSheet.Cells(ras.length + 1, 1).Borders.Weight = 2;
                    oSheet.Cells(ras.length + 1, 2).Borders.Weight = 2;
                    oSheet.Cells(ras.length + 1, 3).Borders.Weight = 2;
                    oXL.Visible = true;
                    oXL.Caption = "列表数据打印";
//                        oSheet.Cells.HorizontalAlignment = -4108;
//                        oSheet.Cells.WrapText = true;
//                        oSheet.Cells.NumberFormat = "\@";

//                        oSheet.PageSetup.PrintGridLines = true;

                    oXL.DisplayAlerts = false;
                    oXL.ActiveSheet.PrintPreview();
                }
            </script> 
        </head>
        <!--
         setInitEnabledButtons('moduleDistrOp','clearStart','distribute');enableModule('clearStart','moduleClearStart')
        -->
        <body onload="initDocument('moduleDistrOp', 'detail');initBack();">

            <table class="table_title">
                <tr align="center" class="trTitle">
                    <td>权限 权限分配</td>
                </tr>

            </table>
            <!-- 状态栏 通用模板 -->
            <c:import url="/jsp/common/common_template.jsp?template_name=common_status_table" />
            <form name="moduleDistrOp" method="post"
                  action="distr">
                <input type="hidden" id="currentGroupID" name="currentGroupID" value="${currentGroupID}"/>
                <input type="hidden" id="sysFlagID" name="sysFlagID" value="${sysFlagID}"/>
                <c:import  url="/jsp/common/common_template_web_variable.jsp?template_name=common_web_variable" />

                <div style="float: left;height: 400px;width: auto;">
                    <!--显示组别-->
                    <div  style="float: left;height: 400px;width: 200px;">
                        <div id="clearStart"  style="border: solid 1px silver;width: 15%;height: 375px;overflow: auto;position: absolute;">

                            <table  style="width: 100% ;border: solid 1px silver;" id="DataTable"
                                    align="left">
                                <tr class="table_list_tr_head" id="ignore">
                                    <td class="table_list_tr_col_head">
                                        <input type="checkbox" name="rectNoAll"
                                               style="display:none" />
                                    </td>
                                    <td id="groupTd" class="table_list_tr_col_head"  isDigit=false index="1"  sortedby="asc" onclick="sortForTable();">
                                        组别名称
                                    </td>
                                </tr>
                                <c:forEach items="${ResultSet}" var="rs">
                                    <tr class="table_list_tr_data"  onMouseOver="overResultRow('moduleDistrOp', this);" 
                                        onMouseOut="outResultRow('moduleDistrOp', this);" 
                                        onclick="clickResultRow('moduleDistrOp', this, 'detail');"
                                        id="${rs.sysGroupId}">                               

                                        <td id="rectNo1">
                                            <c:choose>
                                                <c:when test="${rs.sysGroupId == currentGroupID}">
                                                    <input type="radio" name="rectNo" onclick="unSelectAllRecord('moduleDistrOp', 'rectNoAll', 'rectNo');listGroupModules('moduleDistrOp', 'moduleClearStart');"
                                                           value="${rs.sysGroupId}" checked="checked">
                                                    </input>
                                                </c:when>
                                                <c:otherwise>
                                                    <input type="radio" name="rectNo" onclick="unSelectAllRecord('moduleDistrOp', 'rectNoAll', 'rectNo');
                                                                listGroupModules('moduleDistrOp', 'moduleClearStart');"
                                                           value="${rs.sysGroupId}" >
                                                    </input
                                                </c:otherwise>
                                            </c:choose>


                                        </td>                                


                                        <td  id="groupName">
                                            ${rs.sysGroupName}
                                        </td>     

                                    </tr>
                                </c:forEach>       


                            </table>

                        </div>
                    </div>

                    <!--显示子系统-->
                    <div  style="float: left;height: 400px;width: 170px;">
                        <div id="clearStart1"  style="width: 100%;height: 396;overflow: auto;position: absolute;">
                            <table style="width: 12% ;border: solid 1px silver;" id="DataTable1"  align="left">
                                <tr class="table_list_tr_head" id="ignore">
                                    <td width="20" align="center" nowrap="1">
                                        <input type="checkbox" name="rectNoAll2"
                                               style="display:none" />
                                    </td>
                                    <td id="groupTd" class="table_list_tr_col_head"  isDigit=false index="1"  sortedby="asc" onclick="sortForTable();">系统名称
                                    </td>
                                </tr>
                                <c:forEach items="${ResultSet1}" var="rs">
                                    <tr class="table_list_tr_data"  onMouseOver="overResultRow('moduleDistrOp', this);" 
                                        onMouseOut="outResultRow('moduleDistrOp', this);" 
                                        onclick="clickResultRow('moduleDistrOp', this, 'detail');"
                                        id="${rs.code}">                               

                                        <td id="rectNo1">
                                            <c:choose>
                                                <c:when test="${rs.code == sysFlagID}">
                                                    <input type="radio" name="rectNo2" onclick="unSelectAllRecord('moduleDistrOp', 'rectNoAll', 'rectNo');listGroupModules2('moduleDistrOp', 'moduleClearStart');"
                                                           value="${rs.code}" checked="checked">
                                                    </input>
                                                </c:when>
                                                <c:otherwise>
                                                    <input type="radio" name="rectNo2" onclick="unSelectAllRecord('moduleDistrOp', 'rectNoAll', 'rectNo');
                                                                listGroupModules2('moduleDistrOp', 'moduleClearStart');"
                                                           value="${rs.code}" >
                                                    </input
                                                </c:otherwise>
                                            </c:choose>
                                        </td>                                


                                        <td  id="groupName">
                                            ${rs.codeText}
                                        </td>     

                                    </tr>
                                </c:forEach>  


                            </table>
                        </div>
                    </div>

                    <!--显示勾选权限界面-->
                    <div style="float: left; height: 400;width: 500px; ">
                        <div id="moduleClearStart" style="width: 50%;height: 375px;position: absolute;">
                            <div  class="divForTableBlockData_tall">
                                <script type="text/javascript">
                                    var dc = new dTree('dc', true);
                                    dc.add(0, -1, '');
                                </script>
                                <c:forEach items="${ResultSet2}" var="rs">                                       
                                    <script type="text/javascript">
                                             dc.add('${rs.moduleId}', '${rs.parentId}', '${rs.moduleName}', '${rs.checked}');
                                    </script>
                                </c:forEach>  

                                <script type="text/javascript">
                                    document.write(dc);
                                    try {
                                        setBroTreeV();
                                    } catch (e) {
                                    }
                                    ;
                                </script>
                            </div>
                        </div>
                    </div>
                    <!--                        显示权限预览-->
                    <!--                        <div style="float: left;height: 400px;width: 330px;">
                        <div style="border: solid 1px silver;width: 100%;height: 396;overflow: auto;position: absolute;">
                            <div class="dtree">
                                <script type="text/javascript">
                                    var d = new dTree('d',false);
                                    d.add(0,-1,'');
                                </script>
                                <xsl:for-each
                                    select="/Service/Result/Modules/sysModuleVo">
                                    <script type="text/javascript">
                                        d.add('<xsl:value-of select="moduleID"/>','<xsl:value-of select="parentID"/>','<xsl:value-of select="name"/>','<xsl:value-of select="checked"/>');
                                    </script>
                                </xsl:for-each>
                                <script type="text/javascript">
                                    document.write(d);
                                    try{
                                    setBroTreeV();
                                    }
                                    catch(e){
                                    };
                                </script>
                            </div>
                            </div>
                    </div>-->
                </div>

                <DIV id="detail" align="center"
                     style="display:none;position:relative; left:20; width:95%; height:10px; layer-background-color:#99CC99; border:thin outset #ffffff; ">
                    <table style="display:none" align="center"
                           class="listDataTable">
                        <tr style="display:none">
                            <td>
                                <input type="hidden" name="ignore" />
                            </td>
                        </tr>
                    </table>
                </DIV>

                <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all_init" />
                <c:set var="distribute" scope="request" value="1"/>                  
                <c:set var="export_right" scope="request" value="1"/>       
                <c:set var="print_right" scope="request" value="1"/>
                <c:set var="clickMethod" scope="request" value="btnClickEx('moduleDistrOp','clearStart','detail','moduleClearStart','');"/>
                <c:set var="expRight" scope="request" value="expRight();"/>
                <c:set var="printRight" scope="request" value="printRight();"/>

                <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all" />


                <br />

            </form>


        </body>
    </html>
