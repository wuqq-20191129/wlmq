<%-- 
    Document   : common_template
    Created on : 2017-5-17, 11:19:39
    Author     : hejj
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
    String templateName = request.getParameter("template_name");
    if (templateName != null && templateName.length() != 0) {
        request.setAttribute("templateName", templateName);
    }


%>

<c:choose>
    <c:when test="${templateName == 'version'}">
        <!-- 参数版本 -->
        <input type="hidden" name="Version" value="${version.version_no}"/>
        <input type="hidden" name="VersionType" value="${version.record_flag}"/>
        <input type="hidden" name="Type" value="${version.parm_type_id}"/>
        <input type="hidden" name="StartDate" value="${version.begin_time}"/>
        <input type="hidden" name="EndDate" value="${version.end_time}" />
    </c:when>
    <c:when test="${templateName == 'version_eval'}">
        <!-- 赋值参数版本 -->
        <input type="hidden" name="Version" value="${version.version_no}"/>
        <input type="hidden" name="VersionType" value="${version.record_flag}"/>
        <input type="hidden" name="Type" value="${version.parm_type_id}"/>
        <input type="hidden" name="StartDate" value="${version.begin_time}"/>
        <input type="hidden" name="EndDate" value="${version.end_time}"/>
    </c:when>

    <c:when test="${templateName == 'option_list_pub_line'}">
        <!-- 线路 -->
        <option value="">=请选择=</option>
        <c:forEach items="${lines}" var="line"> 
            <c:choose>
                <c:when test="${line.code_text == ''}">
                    <option value="${line.code}">${line.code}</option>
                </c:when>
                <c:otherwise>
                    <option value="${line.code}">${line.code_text}</option>
                </c:otherwise>
            </c:choose>
        </c:forEach>
    </c:when>

    <c:when test="${templateName == 'option_list_pub_none'}">
        <!-- 车站缺省 -->
        <option value="">=请选择=</option>
    </c:when>

    <c:when test="${templateName == 'option_list_pub_station'}">
        <!-- 车站 -->
        <input type="hidden" id="${pVarName}" name="${pVarName}" value="${lineStations}" />
    </c:when>

    <c:when test="${templateName == 'option_list_pub_devtype'}">
        <!-- 设备类型 -->
        <option value="">=请选择=</option>
        <c:forEach items="${devTypes}" var="devType"> 
            <c:choose>
                <c:when test="${devType.code_text == ''}">
                    <option value="${devType.code}">${devType.code}</option>
                </c:when>
                <c:otherwise>
                    <option value="${devType.code}">${devType.code_text}</option>
                </c:otherwise>
            </c:choose>
        </c:forEach>
    </c:when>

    <c:when test="${templateName == 'op_button_list_query'}">
        <!--查询按钮 -->
        <tr>
            <td>
                <input type="button" id="btQuery" name="query"  value="查询" class="buttonStyle" onclick="" />
            </td>
        </tr>

    </c:when>
    <c:when test="${templateName == 'op_button_list_prm'}">
        <!--参数操作按钮列表 -->
        <tr>
            <td>
                <input type="button" id="btAdd" name="add" disabled="true" value="增加" class="buttonStyle" onclick="" />
                <input type="button" id="btDelete" name="del" disabled="true" value="删除" class="buttonStyle" onclick="" />
                <input type="button" id="btModify" name="modify" disabled="true0" value="修改" class="buttonStyle" onclick="" />
                <input type="button" id="btSave" name="save" disabled="true" value="保存" class="buttonStyle" onclick="" />
                <input type="button" id="btCancle" name="cancle" disabled="true" value="取消" class="buttonStyle" onclick="" />
                <input type="button" id="btClone" name="clone" disabled="true" value="克隆" class="buttonStyle" onclick="" />
                <input type="button" id="btSubmit" name="submit1" disabled="true" value="提交" class="buttonStyle" onclick="" />
                <input type="button" id="btExport1" name="export1" disabled="true" value="导出" class="buttonStyle" onclick="" />
                <input type="button" id="btBackEnd" name="btBackEnd" disabled="true" value="始页" class="buttonStyle" onclick="" />
                <input type="button" id="btBack" name="btBack" disabled="true" value="前一页" class="buttonStyle" onclick="" />
                <input type="button" id="btNext" name="btNext" disabled="true" value="下一页" class="buttonStyle" onclick="" />
                <input type="button" id="btNextEnd" name="btNextEnd" disabled="true" value="止页" class="buttonStyle" onclick="" />
                <input type="button" id="btGoPage" name="btGoPage" disabled="true" value="跳转" class="buttonStyle" onclick="" /><input type="text" value="${goPageNos}" name="goPageNos" size="1">页
            </td>
        </tr>

    </c:when>
    <c:when test="${templateName == 'op_button_list_all_init'}">
        <c:set var="clone" scope="request" value="0"/>
        <c:set var="submit" scope="request" value="0"/>

        <c:set var="query" scope="request" value="0"/>
        <c:set var="add" scope="request" value="0"/>
        <c:set var="del" scope="request" value="0"/>
        <c:set var="modify" scope="request" value="0"/>
        <c:set var="save" scope="request" value="0"/>
        <c:set var="cancel" scope="request" value="0"/>

        <c:set var="import" scope="request" value="0"/>
        <c:set var="expAll" scope="request" value="0"/>
        <c:set var="export" scope="request" value="0"/>
        <c:set var="print" scope="request" value="0"/>

        <c:set var="download" scope="request" value="0"/>
        <c:set var="statistic" scope="request" value="0"/>

        <c:set var="refundOk" scope="request" value="0"/>
        <c:set var="refundNo" scope="request" value="0"/>
        <c:set var="refundMd" scope="request" value="0"/>
        <c:set var="refundCk" scope="request" value="0"/>

        <c:set var="exportBill" scope="request" value="0"/>
        <c:set var="audit" scope="request" value="0"/>

        <c:set var="next" scope="request" value="0"/>
        <c:set var="nextEnd" scope="request" value="0"/>
        <c:set var="back" scope="request" value="0"/>
        <c:set var="backEnd" scope="request" value="0"/>
        <c:set var="clickMethodVisable" scope="request" value="''"/>
        <c:set var="addClickMethod" scope="request" value="''"/>
        <c:set var="addAfterClickModifyMethod" scope="request" value="''"/>
        <c:set var="addClickMethod1" scope="request" value="''"/>
        <c:set var="addClickMethod2" scope="request" value="''"/>
        <c:set var="addClickMethod3" scope="request" value="''"/>
        <c:set var="addQueryMethod" scope="request" value="''"/>
        <c:set var="addAfterMethod" scope="request" value="''"/>
        <c:set var="addBeforeMethod" scope="request" value="''"/>
        <c:set var="exportBillBefore" scope="request" value="''"/>
        <c:set var="exportBillAfter" scope="request" value="''"/>

    </c:when>


    <c:when test="${templateName == 'op_button_list_all'}">

        <table width="95%" align="center">
            <tr>
                <td>


                    <c:if test="${query == 1}"><input type="button" id="btQuery" name="query"  value="查询" class="buttonStyle" onclick="${addClickMethod};${clickMethod};" /></c:if>
                    <c:if test="${add == 1}"><input type="button" id="btAdd" name="add" disabled="true" value="增加" class="buttonStyle" onclick="${addBeforeMethod};${clickMethod};${addAfterMethod}" /></c:if>
                    <c:if test="${del == 1}"><input type="button" id="btDelete" name="del" disabled="true" value="删除" class="buttonStyle" onclick="${addQueryMethod};${clickMethod}" /></c:if>
                    <c:if test="${modify == 1}"><input type="button" id="btModify" name="modify" disabled="true0" value="修改" class="buttonStyle" onclick="${clickMethod};${addAfterClickModifyMethod}" /></c:if>
                    <c:if test="${save == 1}"><input type="button" id="btSave" name="save" disabled="true" value="保存" class="buttonStyle" onclick="${addQueryMethod};${addClickMethod};${clickMethod}" /></c:if>
                    <c:if test="${cancle == 1}"><input type="button" id="btCancle" name="cancle" disabled="true" value="取消" class="buttonStyle" onclick="${clickMethod}" /></c:if>
                    <c:if test="${clone == 1}"><input type="button" id="btClone" name="clone" disabled="true" value="克隆" class="buttonStyle" onclick="${addQueryMethod};${clickMethod}" /></c:if>
                    <c:if test="${submit == 1}"><input type="button" id="btSubmit" name="submit1" disabled="true" value="提交" class="buttonStyle" onclick="${addQueryMethod};${clickMethod}" /></c:if>
                    <c:if test="${import_1 == 1}"><input type="button" id="btImport" name="import" disabled="true" value="导入" class="buttonStyle" onclick="${clickMethod}" /></c:if>


                    <c:if test="${download == 1}"><input type="button" id="btDownload" name="download"  value="下发" class="buttonStyle" onclick="${clickMethod}" /></c:if>
                    <c:if test="${statistic == 1}"><input type="button" id="btStatistic" name="statistic"  value="统计" class="buttonStyle" onclick="${clickMethod}" /></c:if>

                    <c:if test="${btRefundOk == 1}"><input type="button" id="btRefundOk" name="btRefundOk"  value="确认退款" class="buttonStyle" onclick="submitConfirm1All('${addClickMethod}', '${clickMethod}')" /></c:if>
                    <c:if test="${btRefundNo == 1}"><input type="button" id="btRefundNo" name="btRefundNo"  value="拒绝退款" class="buttonStyle" onclick="submitConfirm2All('${addClickMethod1}', '${clickMethod}')" /></c:if>
                    <c:if test="${btRefundMd == 1}"><input type="button" id="btRefundMd" name="btRefundMd"  value="确认修改" class="buttonStyle" onclick="submitConfirm3All('${addClickMethod2}', '${clickMethod}')" /></c:if>
                    <c:if test="${btRefundCk == 1}"><input type="button" id="btRefundCk" name="btRefundCk"  value="确认审核" class="buttonStyle" onclick="submitConfirm4All('${addClickMethod3}', '${clickMethod}')" /></c:if>
                    <c:if test="${exportBill == 1}"><input type="button" id="btExportBill" name="exportBill" disabled="true" value="单据导出" class="buttonStyle" onclick="${exportBillBefore};${clickMethod};${exportBillAfter}" /></c:if>
                    <c:if test="${audit == 1}"><input type="button" id="btAudit" name="audit"  value="审核" class="buttonStyle" onclick="${addClickMethod};${clickMethod};" /></c:if>
                    <c:if test="${expAll == 1}"><input type="button" id="btExpAll" name="expAll" disabled="true" value="导出全部" class="buttonStyle" onclick="${clickMethod}" /></c:if>
                    <c:if test="${export == 1}"><input type="button" id="btExport" name="export" disabled="true" value="导出" class="buttonStyle" onclick="${clickMethod}" /></c:if>
                    <c:if test="${print == 1}"><input type="button" id="btPrint" name="print" disabled="true" value="打印" class="buttonStyle" onclick="${clickMethod}" /></c:if>
                    <c:if test="${btBackEnd == 1}"><input type="button" id="btBackEnd" name="btBackEnd" disabled="true" value="始页" class="buttonStyle" onclick="${clickMethod};" /></c:if>
                    <c:if test="${btBack == 1}"><input type="button" id="btBack" name="btBack" disabled="true" value="前一页" class="buttonStyle" onclick="${clickMethod};" /></c:if>
                    <c:if test="${btNext == 1}"><input type="button" id="btNext" name="btNext" disabled="true" value="下一页" class="buttonStyle" onclick="${clickMethod};" /></c:if>
                    <c:if test="${btNextEnd == 1}"><input type="button" id="btNextEnd" name="btNextEnd" disabled="true" value="止页" class="buttonStyle" onclick="${clickMethod};" /></c:if>
                    <c:if test="${btGoPage == 1}"><input type="button" id="btGoPage" name="btGoPage" disabled="true" value="跳转" class="buttonStyle" onclick="${clickMethod};" /><input type="text" value="${goPageNos}" name="goPageNos" size="1">页</c:if>



                    </td>
                </tr>
            </table>

    </c:when>

    <c:when test="${templateName == 'common_status_table'}">
        <!--状态栏 -->
        <table class="table_status" >
            <tr >
                <td  id="promptMessageId" >返回信息:<strong><font color="red">
                        ${Message}

                        </font>
                    </strong>
                </td>
            </tr>
        </table>

    </c:when>

    <c:when test="${templateName == 'common_table_title'}">
        <!--查询、数据、操作的标题 -->
        <table  class="table_title_label" >
            <tr >
                <td  width="${pTitleWidth}">
                    ${pTitleName}
                </td>
                <td>
                </td>

            </tr>
        </table>

    </c:when>

    <c:otherwise>

    </c:otherwise>
</c:choose>
