<%-- 
    Document   : report_qry_template
    Created on : 2017-7-9
    Author     : mqf
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
    
        
    <c:when test="${templateName == 'op_report_button_list_all'}">
        <%--<c:set var="formName" scope="request" value="''"/>--%>
        <%--<c:set var="reportType" scope="request" value="''"/>--%>
        <%--<c:set var="controlNames" scope="request" value="''"/>--%>
        
        <input type="button" id="btReportQuery" name="ReportQuery"  value="查询" class="buttonStyle" onclick="btnClickForReport('${formName}','${reportType}','${controlNames}')">

    </c:when>
        
    <c:when test="${templateName == 'op_report_saveReportCodeMapping'}">
        <input type="hidden" id="reportCodeMapping" name="reportCodeMapping" value="${ReportCodeMappings}" />
<!--	  <xsl:attribute name="value">
			 <xsl:for-each select="/Service/Result/ReportCodeMappings/pubFlagVO">
			 		<xsl:value-of select="concat(code,'#',codeText,';')"/>
	  	 </xsl:for-each>
		</xsl:attribute>-->
        

    </c:when>
        
    <c:when test="${templateName == 'op_report_table'}">
        <%--<c:set var="note" scope="request" value="''"/>--%>
        <div id="clearStartBlock" class="divForTableBlockReport">
        <div id="clearStartHead" class="divForTableBlockHead">
        <table id="reportList" class="table_list_block" style="display:1">
            <tr align="center" class="table_list_tr_head_block">
                
                                    <td class="table_list_tr_col_head_block" style="width:100px" height="18"><div align="center" >序号</div></td>
                                    <td class="table_list_tr_col_head_block" style="width:400px"><div align="center" >报表连接</div></td>
                                    <td class="table_list_tr_col_head_block"  style="width:200px"><div align="center" >清算日期</div></td>
                                    <td class="table_list_tr_col_head_block" style="width:400px"><div align="center" >备注</div></td>
                                    
            </tr>
            

            <!--<xsl:for-each select="/Service/Result/ReportNames/reportAttribute">-->
            <!--<a href="${rs.reportURL}"  target="_blank"> 外使用<div align="center" >，显示多了2018-07-04 .PDF (;;) -->
            <c:forEach items="${ResultSet}" var="rs" varStatus="status">
               <tr class="trContent">
                                    <td style="width:100px" align="center" height="18">
                                            ${status.index+1}
                                    </td>
                                    <td style="width:400px" align="center">
                                            <a href="${rs.reportURL}"  target="_blank">
                                                ${rs.operationDate}
                                                ${rs.fileTypeText}
                                            </a>
                                    </td>
                                    <td style="width:200px" align="center">
                                            ${rs.balanceDate}
                                    </td>
                                    <td style="width:400px" align="center">
                                            ${note}
                                    </td>
                     </tr>	
            </c:forEach>
                     
            <!--</xsl:for-each>-->
        
        </table>
        </div>
        </div>

    </c:when>
    
    <c:when test="${templateName == 'op_report_qry_con_date'}">
        <td class="table_edit_tr_td_label" >统计日期:</td>
        <td class="table_edit_tr_td_input" >
            <input type="text" size="9" id="date" name="date"   require="true"  value= "${BalanceDate}" dataType="Custom" regexp="\d\d\d\d-\d\d-\d\d" msg="日期格式为：年月日如1900-01-02"> 
            </input>
            <a href="javascript:openCalenderDialogByID('date','false');">
                <img src="./images/calendar.gif" width="15" height="15" border="0" style="block"/>
            </a>
        </td>

    </c:when>
        
    <c:when test="${templateName == 'op_report_qry_con_date_cur'}">
        <td class="table_edit_tr_td_label" >统计日期:</td>
        <td class="table_edit_tr_td_input" >
            <input type="text" size="9" id="date" name="date"   require="true"  value= "${BalanceDateCur}" dataType="Custom" regexp="\d\d\d\d-\d\d-\d\d" msg="日期格式为：年月日如1900-01-02"> 
            </input>
            <a href="javascript:openCalenderDialogByID('date','false');">
                <img src="./images/calendar.gif" width="15" height="15" border="0" style="block"/>
            </a>
        </td>

    </c:when>
    
    
    <c:when test="${templateName == 'op_report_qry_con_isBalanceDate'}">
        <c:choose>
        <c:when test="${isBalanceDate == '1'}">
            <td class="table_edit_tr_td_label">
<!--                <input type="checkbox" id="isBalanceDate" name="isBalanceDate" value="0" checked = "true" >
                </input>-->
                <label for="isBalanceDate"><input id="isBalanceDate" type="radio" value="1" name="isBalanceDate" checked>按清算日</label>
            </td>
            <td class="table_edit_tr_td_input">
                <label for="isSquadDate"><input id="isSquadDate" type="radio" value="0" name="isBalanceDate">按运营日</label>
            </td>    
            
        </c:when>
        <c:otherwise>
            <td  class="table_edit_tr_td_label">
                <label for="isBalanceDate"><input id="isBalanceDate" type="radio" value="1" name="isBalanceDate" >按清算日</label>
            </td>
            <td  class="table_edit_tr_td_input">
                <label for="isSquadDate"><input id="isSquadDate" type="radio" value="0" name="isBalanceDate" checked>按运营日</label>
            </td>
       </c:otherwise>
       </c:choose>
	
    </c:when>
    
        
    <c:when test="${templateName == 'op_report_qry_con_year'}">
<!--        <select name="year">

			<xsl:call-template name="option_list_pub">
					   <xsl:with-param name="root" select="/Service/Result/Years/pubFlagVO"/>
					   <xsl:with-param name="isAll" select="0"/>
					   <xsl:with-param name="selectvalue" select="$selvalue"/>  
			</xsl:call-template>
	</select>
        年-->
        <td class="table_edit_tr_td_label">年:</td>
        <td class="table_edit_tr_td_input">
            <select id="year" name="year" >
                <c:set var="varReportQryCons" scope="request" value="${Years}"/>
                <c:import url="/jsp/report/report_qry_template.jsp?template_name=option_list_pub_report_qry_con_no_select" />
            </select>
            年
        </td>
    </c:when>
        
    <c:when test="${templateName == 'op_report_qry_con_month'}">
<!--        <select name="month">

			 <xsl:call-template name="option_list_pub">
					   <xsl:with-param name="root" select="/Service/Result/Months/pubFlagVO"/>
					   <xsl:with-param name="isAll" select="0"/>
			</xsl:call-template>
	 </select>-->
        
        <td class="table_edit_tr_td_input">
            <select id="month" name="month" >
                <c:set var="varReportQryCons" scope="request" value="${Months}"/>
                <c:import url="/jsp/report/report_qry_template.jsp?template_name=option_list_pub_report_qry_con_no_select" />
            </select>
            月
        </td>
    </c:when>
        
    <c:when test="${templateName == 'op_report_qry_con_year_month'}">
        <td class="table_edit_tr_td_label" >年月:</td>
        <td class="table_edit_tr_td_input" >
            <select id="year" name="year" style="width: 60px">
                <c:set var="varReportQryCons" scope="request" value="${Years}"/>
                <c:import url="/jsp/report/report_qry_template.jsp?template_name=option_list_pub_report_qry_con_no_select" />
            </select>
            
            <select id="month" name="month" style="width: 40px">
                <c:set var="varReportQryCons" scope="request" value="${Months}"/>
                <c:import url="/jsp/report/report_qry_template.jsp?template_name=option_list_pub_report_qry_con_no_select" />
                
<!--                    <option value="01">01</option>
                    <option value="02">02</option>
                    <option value="03">03</option>
                    <option value="04">04</option>
                    <option value="05">05</option>
                    <option value="06">06</option>
                    <option value="07">07</option>
                    <option value="08">08</option>
                    <option value="09">09</option>
                    <option value="10">10</option>
                    <option value="11">11</option>
                    <option value="12">12</option>-->
            </select>
            
        </td>
    </c:when>
        
    <c:when test="${templateName == 'op_report_qry_con_lineID'}">
<!--        <select id="lineID" name="lineID" require="true"  dataType="LimitB" min="1" msg="请选择线路">
                    <xsl:if test="$isOnChange=1">
                            <xsl:attribute name="onChange" >
                                    setSelectValues('<xsl:value-of select="$formName"/>','lineID','stationID','commonVariable1');
                            </xsl:attribute>		
                    </xsl:if>
                            <xsl:call-template name="option_list_pub">
                                            <xsl:with-param name="root" select="/Service/Result/Lines/pubFlagVO"/>
                       </xsl:call-template>
      </select>-->
        <c:choose>
        <c:when test="${isOnChange == '1'}">
            <td class="table_edit_tr_td_label">线路:</td>
            <td class="table_edit_tr_td_input">
                <select id="lineID" name="lineID" require="true"  dataType="LimitB" min="1" msg="请选择线路"
                        onChange="setSelectValues('queryOp','lineID','stationID','commonVariable1');">
                    <c:set var="varReportQryCons" scope="request" value="${Lines}"/>
                    <c:import url="/jsp/report/report_qry_template.jsp?template_name=option_list_pub_report_qry_con" />
            </select>
            </td>
        </c:when>
        <c:otherwise>
            <td class="table_edit_tr_td_label">线路:</td>
            <td class="table_edit_tr_td_input">
                <select id="lineID" name="lineID" require="true"  dataType="LimitB" min="1" msg="请选择线路">
                    <c:set var="varReportQryCons" scope="request" value="${Lines}"/>
                    <c:import url="/jsp/report/report_qry_template.jsp?template_name=option_list_pub_report_qry_con" />
                </select>
            </td>
        </c:otherwise>
        </c:choose>

    </c:when>
        
    <c:when test="${templateName == 'op_report_qry_con_lineIDLarge'}">
        <td class="table_edit_tr_td_label">大线路:</td>
        <td class="table_edit_tr_td_input">
            <select id="lineID" name="lineID" require="true"  dataType="LimitB" min="1" msg="请选择大线路">
                    <c:set var="varReportQryCons" scope="request" value="${LineTypes}"/>
                    <c:import url="/jsp/report/report_qry_template.jsp?template_name=option_list_pub_report_qry_con" />
            </select>
        </td>    
    </c:when>
        
    <c:when test="${templateName == 'op_report_qry_con_mainType'}">
        <td class="table_edit_tr_td_label">票卡主类型:</td>
        <td class="table_edit_tr_td_input">
            <select id="mainType" name="mainType" onChange="setSelectValues('queryOp','mainType','subType','commonVariable');" require="true"  dataType="LimitB" min="1" msg="请选择票卡主类型">	
                    <c:set var="varReportQryCons" scope="request" value="${CardMainTypes}"/>
                    <c:import url="/jsp/report/report_qry_template.jsp?template_name=option_list_pub_report_qry_con" />
                    <%--<c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_cardMain" />--%>
            </select>
        </td>  
    </c:when>
        
    <c:when test="${templateName == 'op_report_qry_con_subType'}">
        <td class="table_edit_tr_td_label">票卡子类型:</td>
        <td class="table_edit_tr_td_input">
            <select id="subType" name="subType" require="true"  dataType="LimitB" min="1" msg="请选择票卡子类型">	
                    
                    <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_cardSub_none" />
            </select>
            <c:set var="pVarName" scope="request" value="commonVariable"/>
            <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_cardSub" />
            <%--<c:set var="varReportQryCons" scope="request" value="commonVariable"/>--%>
            <%--<c:import url="/jsp/report/report_qry_template.jsp?template_name=option_list_pub_report_qry_con" />--%>
        </td>

    </c:when>
        
    <c:when test="${templateName == 'option_list_pub_report_qry_con'}">
        <option value="">=请选择=</option>
        <c:forEach items="${varReportQryCons}" var="varReportQryCon"> 
            <c:choose>
                <c:when test="${varReportQryCon.isDefaultValue == 'true'}">
                    <!--<option value="${varReportQryCon.code}" selected="true">-->
                        <c:choose>
                            <c:when test="${varReportQryCon.code_text == null}">
                                <option value="${varReportQryCon.code}" selected="true">${varReportQryCon.code}</option>
                            </c:when>
                            <c:otherwise>
                                <option value="${varReportQryCon.code}" selected="true">${varReportQryCon.code_text}</option>
                            </c:otherwise>
                        </c:choose>  
                        
                    <!--</option>-->
                </c:when>
                <c:otherwise>
                    <c:choose>
                        <c:when test="${varReportQryCon.code_text == null}">
                            <option value="${varReportQryCon.code}">${varReportQryCon.code}</option>
                        </c:when>
                        <c:otherwise>
                            <option value="${varReportQryCon.code}">${varReportQryCon.code_text}</option>
                        </c:otherwise>
                    </c:choose>
                </c:otherwise>
            
                    
                    
            </c:choose>
        </c:forEach>
    </c:when>
                            
    <c:when test="${templateName == 'option_list_pub_report_qry_con_no_select'}">
        <!-- 年-->
        <!--<option value="">=请选择=</option>-->
        <c:forEach items="${varReportQryCons}" var="varReportQryCon"> 
            <c:choose>
                <c:when test="${varReportQryCon.isDefaultValue == 'true'}">
                    <!--<option value="${varReportQryCon.code}" selected="true">-->
                        <c:choose>
                            <c:when test="${varReportQryCon.code_text == null}">
                                <option value="${varReportQryCon.code}" selected="true">${varReportQryCon.code}</option>
                            </c:when>
                            <c:otherwise>
                                <option value="${varReportQryCon.code}" selected="true">${varReportQryCon.code_text}</option>
                            </c:otherwise>
                        </c:choose>  
                        
                    <!--</option>-->
                </c:when>
                <c:otherwise>
                    <c:choose>
                        <c:when test="${varReportQryCon.code_text == null}">
                            <option value="${varReportQryCon.code}">${varReportQryCon.code}</option>
                        </c:when>
                        <c:otherwise>
                            <option value="${varReportQryCon.code}">${varReportQryCon.code_text}</option>
                        </c:otherwise>
                    </c:choose>
                </c:otherwise>
            
                    
                    
            </c:choose>
        </c:forEach>
    </c:when>

    <c:otherwise>

    </c:otherwise>
</c:choose>
