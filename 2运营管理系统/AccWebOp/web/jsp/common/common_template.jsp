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
                    
                  
    <c:when test="${templateName == 'option_list_pub_lineFlag'}">
        <!-- 线路标识-->
        <option value="">=请选择=</option>
        <c:forEach items="${lineFlags}" var="lineFlag">
            <c:choose>
                <c:when test="${lineFlag.code_text == null}">
                    <option value="${lineFlag.code}">${lineFlag.code}</option>
                </c:when>
                <c:otherwise>
                    <option value="${lineFlag.code}">${lineFlag.code_text}</option>
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

    <c:when test="${templateName == 'all_option_list_pub_line'}">
        <!-- 线路 -->
        <option value="00">&nbsp;&nbsp;&nbsp;全部&nbsp;&nbsp;&nbsp;</option>
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

    <c:when test="${templateName == 'all_option_list_pub_none'}">
        <!-- 车站缺省 -->
        <option value="00">&nbsp;&nbsp;&nbsp;全部&nbsp;&nbsp;&nbsp;</option>
    </c:when>

    <c:when test="${templateName == 'option_list_pub_fildevtype'}">
        <!-- 设备类型 -->
        <option value="">=请选择=</option>
        <c:forEach items="${filDevTypes}" var="filDevType">
            <c:choose>
                <c:when test="${filDevType.code_text == ''}">
                    <option value="${filDevType.code}">${filDevType.code}</option>
                </c:when>
                <c:otherwise>
                    <option value="${filDevType.code}">${filDevType.code_text}</option>
                </c:otherwise>
            </c:choose>
        </c:forEach>
    </c:when>

    <c:when test="${templateName == 'all_option_list_pub_filDevtype'}">
        <!-- 设备类型 -->
        <option value="00">&nbsp;&nbsp;&nbsp;全部&nbsp;&nbsp;&nbsp;</option>
        <c:forEach items="${filDevTypes}" var="filDevType">
            <c:choose>
                <c:when test="${filDevType.code_text == ''}">
                    <option value="${filDevType.code}">${filDevType.code}</option>
                </c:when>
                <c:otherwise>
                    <option value="${filDevType.code}">${filDevType.code_text}</option>
                </c:otherwise>
            </c:choose>
        </c:forEach>
    </c:when>

    <c:when test="${templateName == 'option_list_pub_merchant'}">
        <!-- 商户 -->
        <option value="">=请选择=</option>
        <c:forEach items="${merchants}" var="merchant">
            <c:choose>
                <c:when test="${merchant.code_text == ''}">
                    <option value="${merchant.code}">${merchant.code}</option>
                </c:when>
                <c:otherwise>
                    <option value="${merchant.code}">${merchant.code_text}</option>
                </c:otherwise>
            </c:choose>
        </c:forEach>
    </c:when>

    <c:when test="${templateName == 'option_list_pub_cardMain'}">
        <!-- 票卡主类型  zhouyang 20170607 -->
        <option value="">=请选择=</option>
        <c:forEach items="${cardMains}" var="cardMain">
            <c:choose>
                <c:when test="${cardMain.code_text == ''}">
                    <option value="${cardMain.code}">${cardMain.code}</option>
                </c:when>
                <c:otherwise>
                    <option value="${cardMain.code}">${cardMain.code_text}</option>
                </c:otherwise>
            </c:choose>
        </c:forEach>
    </c:when>

    <c:when test="${templateName == 'option_list_pub_cardMain_cardTickttr'}">
        <!-- 票卡主类型乘次票  luck 2017020 -->
        <option value="">=请选择=</option>
        <c:forEach items="${cardMains}" var="cardMain">
            <c:if test="${cardMain.code_text == '乘次票'}">
                <option value="${cardMain.code}">${cardMain.code_text}</option>
            </c:if>
        </c:forEach>
    </c:when>

    <c:when test="${templateName == 'option_list_pub_cardSub_none'}">
        <!-- 票卡子类型缺省 -->
        <option value="">=请选择=</option>
    </c:when>

    <c:when test="${templateName == 'option_list_pub_cardSub'}">
        <!-- 票卡子类型 zhouyang 20170607 -->
        <input type="hidden" id="${pVarName}" name="${pVarName}" value="${cardMainSubs}" />
    </c:when>
    <c:when test="${templateName == 'option_list_pub_blackReson'}">
        <!-- add by zhongziqi 20170612 黑名单原因-->
        <option value="">=请选择=</option>
        <c:forEach items="${blackResons}" var="blackReson">
            <c:choose>
                <c:when test="${blackReson.codeText == ''}">
                    <option value="${blackReson.code}">${blackReson.code}</option>
                </c:when>
                <c:otherwise>
                    <option value="${blackReson.code}">${blackReson.codeText}</option>
                </c:otherwise>
            </c:choose>
        </c:forEach>
    </c:when>
    <c:when test="${templateName == 'option_list_pub_reportDataSources'}">
        <!-- add by zhongziqi 20170612 报表数据源-->
        <option value="">=请选择=</option>
        <c:forEach items="${reportDataSources}" var="vo">
            <c:choose>
                <c:when test="${vo.codeText == ''}">
                    <option value="${vo.code}">${vo.code}</option>
                </c:when>
                <c:otherwise>
                    <option value="${vo.code}">${vo.codeText}</option>
                </c:otherwise>
            </c:choose>
        </c:forEach>
    </c:when>
    <c:when test="${templateName == 'option_list_pub_adminHandleReason'}">
        <!-- add by zhongziqi 20170616 行政处理原因-->
        <option value="">=请选择=</option>
        <c:forEach items="${adminHandleReasons}" var="vo">
            <c:choose>
                <c:when test="${vo.code_text == ''}">
                    <option value="${vo.code}">${vo.code}</option>
                </c:when>
                <c:otherwise>
                    <option value="${vo.code}">${vo.code_text}</option>
                </c:otherwise>
            </c:choose>
        </c:forEach>
    </c:when>

     <c:when test="${templateName == 'option_list_pub_adminHandleReason_name'}">
        <!-- add by luck 20180302 行政处理原因-->
        <option value="">=请选择=</option>
        <c:forEach items="${adminHandleReasons}" var="vo">
            <c:choose>
                <c:when test="${vo.code_text == ''}">
                    <option value="${vo.code}">${vo.code}</option>
                </c:when>
                <c:otherwise>
                    <option value="${vo.code_text}">${vo.code_text}</option>
                </c:otherwise>
            </c:choose>
        </c:forEach>
    </c:when>

    <c:when test="${templateName == 'option_list_pub_signCardHandleType'}">
        <!-- add by zhongziqi 20170618 记名卡退款退卡查询处理类型-->
        <option value="">=请选择=</option>
        <c:forEach items="${handleTypes}" var="vo">
            <c:choose>
                <c:when test="${vo.codeText == ''}">
                    <option value="${vo.code}">${vo.code}</option>
                </c:when>
                <c:otherwise>
                    <option value="${vo.code}">${vo.codeText}</option>
                </c:otherwise>
            </c:choose>
        </c:forEach>
    </c:when>
    <c:when test="${templateName == 'option_list_pub_nonRetunHandleType'}">
        <!-- add by zhongziqi 20170626 非即时退款申请处理类型-->
        <option value="">全部</option>
        <c:forEach items="${handleTypes}" var="vo">
            <c:choose>
                <c:when test="${vo.codeText == ''}">
                    <option value="${vo.code}">${vo.code}</option>
                </c:when>
                <c:otherwise>
                    <option value="${vo.code}">${vo.codeText}</option>
                </c:otherwise>
            </c:choose>
        </c:forEach>
    </c:when>
    <c:when test="${templateName == 'option_list_pub_nonRetunQueryCondition'}">
        <!-- add by zhongziqi 20170626 非即时退款申请处理查询类型-->
        <c:forEach items="${queryConditons}" var="vo">
            <c:choose>
                <c:when test="${vo.codeText == ''}">
                    <option value="${vo.code}">${vo.code}</option>
                </c:when>
                <c:otherwise>
                    <option value="${vo.code}">${vo.codeText}</option>
                </c:otherwise>
            </c:choose>
        </c:forEach>
    </c:when>

    <c:when test="${templateName == 'option_list_pub_operationChar'}">
        <!-- add by zhongziqi 20170618 操作符-->
        <option value="1">&gt;</option>
        <option value="2">&gt;=</option>
        <option value="3">=</option>
        <option value="4">&lt;=</option>
        <option value="5">&lt;</option>
    </c:when>
    <c:when test="${templateName == 'option_list_pub_penatlyReason'}">
        <!-- add by zhongziqi 20170629 罚款原因-->
        <option value="">==请选择==</option>
        <c:forEach items="${penatlyReasons}" var="vo">
            <c:choose>
                <c:when test="${vo.code_text == ''}">
                    <option value="${vo.code}">${vo.code}</option>
                </c:when>
                <c:otherwise>
                    <option value="${vo.code}">${vo.code_text}</option>
                </c:otherwise>
            </c:choose>
        </c:forEach>
    </c:when>
    <c:when test="${templateName == 'option_list_pub_handleFlag'}">
        <!-- add by zhongziqi 20170629 处理类型-->
        <option value="">=请选择=</option>
        <c:forEach items="${eidtHandleFlags}" var="vo">
            <c:choose>
                <c:when test="${vo.codeText == ''}">
                    <option value="${vo.code}">${vo.code}</option>
                </c:when>
                <c:otherwise>
                    <option value="${vo.code}">${vo.codeText}</option>
                </c:otherwise>
            </c:choose>
        </c:forEach>
    </c:when>
    <c:when test="${templateName == 'option_list_pub_identifyType'}">
        <!-- add by zhongziqi 20170620 Bom记名卡挂失申请查询-证件类型-->
        <option value="">=请选择=</option>
        <c:forEach items="${idTypes}" var="vo">
            <c:choose>
                <c:when test="${vo.codeText == ''}">
                    <option value="${vo.code}">${vo.code}</option>
                </c:when>
                <c:otherwise>
                    <option value="${vo.code}">${vo.codeText}</option>
                </c:otherwise>
            </c:choose>
        </c:forEach>
    </c:when>
    <c:when test="${templateName == 'option_list_pub_busnissType'}">
        <!-- add by zhongziqi 20170620 Bom记名卡挂失申请查询-业务类型-->
        <option value="">=请选择=</option>
        <c:forEach items="${businessTypes}" var="vo">
            <c:choose>
                <c:when test="${vo.codeText == ''}">
                    <option value="${vo.code}">${vo.code}</option>
                </c:when>
                <c:otherwise>
                    <option value="${vo.code}">${vo.codeText}</option>
                </c:otherwise>
            </c:choose>
        </c:forEach>
    </c:when>
    <c:when test="${templateName == 'option_list_pub_operatorName'}">
        <!-- add by zhongziqi 20170621 非即退 记名卡日志-操作员名称-->
        <option value="">=请选择=</option>
        <c:forEach items="${operatorNames}" var="vo">
            <c:choose>
                <c:when test="${vo.codeText == ''}">
                    <option value="${vo.code}">${vo.code}</option>
                </c:when>
                <c:otherwise>
                    <option value="${vo.code}">${vo.codeText}</option>
                </c:otherwise>
            </c:choose>
        </c:forEach>
    </c:when>
    <c:when test="${templateName == 'option_list_pub_operatorId'}">
        <!-- add by zhongziqi 201800302  黑名单系列-->
        <option value="">=请选择=</option>
        <c:forEach items="${operatorIds}" var="vo">
              <option value="${vo.operatorId}">${vo.operatorId}</option>
        </c:forEach>
    </c:when>
    <c:when test="${templateName == 'option_list_pub_operationType'}">
        <!-- add by zhongziqi 20170621 非即退 记名卡日志-操作类型-->
        <option value="">=请选择=</option>
        <c:forEach items="${operationTypes}" var="vo">
            <c:choose>
                <c:when test="${vo.codeText == ''}">
                    <option value="${vo.code}">${vo.code}</option>
                </c:when>
                <c:otherwise>
                    <option value="${vo.code}">${vo.codeText}</option>
                </c:otherwise>
            </c:choose>
        </c:forEach>
    </c:when>
    <c:when test="${templateName == 'option_list_pub_discount'}">
        <!-- 是否优惠票 zhouyang 20170607-->
        <option value="">=请选择=</option>
        <c:forEach items="${discounts}" var="discount">
            <c:choose>
                <c:when test="${discount.code_text == ''}">
                    <option value="${discount.code}">${discount.code}</option>
                </c:when>
                <c:otherwise>
                    <option value="${discount.code}">${discount.code_text}</option>
                </c:otherwise>
            </c:choose>
        </c:forEach>
    </c:when>

    <c:when test="${templateName == 'option_list_pub_sound'}">
        <!-- 是否启用语音提示 zhouyang 20170607-->
        <option value="">=请选择=</option>
        <c:forEach items="${sounds}" var="sound">
            <c:choose>
                <c:when test="${sound.code_text == ''}">
                    <option value="${sound.code}">${sound.code}</option>
                </c:when>
                <c:otherwise>
                    <option value="${sound.code}">${sound.code_text}</option>
                </c:otherwise>
            </c:choose>
        </c:forEach>
    </c:when>

    <c:when test="${templateName == 'option_list_pub_timeTableId'}">
        <!-- 节假日时间表类型 zhouyang 201706011-->
        <option value="">=请选择=</option>
        <c:forEach items="${timeTableIds}" var="timeTableId">
            <c:choose>
                <c:when test="${timeTableId.code_text == ''}">
                    <option value="${timeTableId.code}">${timeTableId.code}</option>
                </c:when>
                <c:otherwise>
                    <option value="${timeTableId.code}">${timeTableId.code_text}</option>
                </c:otherwise>
            </c:choose>
        </c:forEach>
    </c:when>

    <c:when test="${templateName == 'option_list_pub_fareName'}">
        <!-- 收费区段 mqf 20170612-->
        <option value="">=请选择=</option>
        <c:forEach items="${fareNames}" var="fareName">
            <c:choose>
                <c:when test="${fareName.code_text == ''}">
                    <option value="${fareName.code}">${fareName.code}</option>
                </c:when>
                <c:otherwise>
                    <option value="${fareName.code}">${fareName.code_text}</option>
                </c:otherwise>
            </c:choose>
        </c:forEach>
    </c:when>

    <c:when test="${templateName == 'option_list_pub_purseValueType'}">
        <!-- 钱包值类型 luck 20170613-->
        <option value="">=请选择=</option>
        <c:forEach items="${purseValueTypes}" var="purseValueType">
            <c:choose>
                <c:when test="${purseValueType.code_text == ''}">
                    <option value="${purseValueType.code}">${purseValueType.code}</option>
                </c:when>
                <c:otherwise>
                    <option value="${purseValueType.code}">${purseValueType.code_text}</option>
                </c:otherwise>
            </c:choose>
        </c:forEach>
    </c:when>

    <c:when test="${templateName == 'option_list_pub_feeType'}">
        <!-- 收费方式 luck 20170613-->
        <option value="">=请选择=</option>
        <c:forEach items="${feeTypes}" var="feeType">
            <c:choose>
                <c:when test="${feeType.code_text == ''}">
                    <option value="${feeType.code}">${feeType.code}</option>
                </c:when>
                <c:otherwise>
                    <option value="${feeType.code}">${feeType.code_text}</option>
                </c:otherwise>
            </c:choose>
        </c:forEach>
    </c:when>

    <c:when test="${templateName == 'option_list_pub_chkValidPhyLogic'}">
        <!-- 逻辑及物理有效期检查 luck 20170613-->
        <option value="">=请选择=</option>
        <c:forEach items="${chkValidPhyLogics}" var="chkValidPhyLogic">
            <c:choose>
                <c:when test="${chkValidPhyLogic.code_text == ''}">
                    <option value="${chkValidPhyLogic.code}">${chkValidPhyLogic.code}</option>
                </c:when>
                <c:otherwise>
                    <option value="${chkValidPhyLogic.code}">${chkValidPhyLogic.code_text}</option>
                </c:otherwise>
            </c:choose>
        </c:forEach>
    </c:when>

    <c:when test="${templateName == 'option_list_pub_checkInOutSequence'}">
        <!-- 进出站次序检查 luck 20170613-->
        <option value="">=请选择=</option>
        <c:forEach items="${checkInOutSequences}" var="checkInOutSequence">
            <c:choose>
                <c:when test="${checkInOutSequence.code_text == ''}">
                    <option value="${checkInOutSequence.code}">${checkInOutSequence.code}</option>
                </c:when>
                <c:otherwise>
                    <option value="${checkInOutSequence.code}">${checkInOutSequence.code_text}</option>
                </c:otherwise>
            </c:choose>
        </c:forEach>
    </c:when>

    <c:when test="${templateName == 'option_list_pub_deviceType'}">
        <!-- 票卡属性设备 luck 20170613-->
        <option value="" disabled="">=请选择=</option>
        <c:forEach items="${deviceTypes}" var="deviceType">
            <c:choose>
                <c:when test="${deviceType.code_text == ''}">
                    <option value="${deviceType.code}">${deviceType.code}</option>
                </c:when>
                <c:otherwise>
                    <option value="${deviceType.code}">${deviceType.code_text}</option>
                </c:otherwise>
            </c:choose>
        </c:forEach>
    </c:when>

    <c:when test="${templateName == 'option_list_pub_rechargeDeviceType'}">
        <!-- 票卡属性充值设备 luck 20170620-->
        <option value="" disabled="">=请选择=</option>
        <c:forEach items="${rechargeDevTypes}" var="rechargeDevType">
            <c:choose>
                <c:when test="${rechargeDevType.code_text == ''}">
                    <option value="${rechargeDevType.code}">${rechargeDevType.code}</option>
                </c:when>
                <c:otherwise>
                    <option value="${rechargeDevType.code}">${rechargeDevType.code_text}</option>
                </c:otherwise>
            </c:choose>
        </c:forEach>
    </c:when>

    <c:when test="${templateName == 'option_list_pub_saleDeviceType'}">
        <!-- 票卡属性充值设备 luck 20170620-->
        <option value="" disabled="">=请选择=</option>
        <c:forEach items="${saleDevTypes}" var="saleDevType">
            <c:choose>
                <c:when test="${saleDevType.code_text == ''}">
                    <option value="${saleDevType.code}">${saleDevType.code}</option>
                </c:when>
                <c:otherwise>
                    <option value="${saleDevType.code}">${saleDevType.code_text}</option>
                </c:otherwise>
            </c:choose>
        </c:forEach>
    </c:when>

    <c:when test="${templateName == 'option_list_pub_fareRideType'}">
        <!-- 票乘车费率类型 luck 20170616-->
        <option value="">=请选择=</option>
        <c:forEach items="${fareRideTypes}" var="fareRideType">
            <c:choose>
                <c:when test="${fareRideType.code_text == ''}">
                    <option value="${fareRideType.code}">${fareRideType.code}</option>
                </c:when>
                <c:otherwise>
                    <option value="${fareRideType.code}">${fareRideType.code_text}</option>
                </c:otherwise>
            </c:choose>
        </c:forEach>
    </c:when>

    <c:when test="${templateName == 'option_list_lcc_line'}">
        <!-- Lcc 线路 -->
        <option value="">=请选择=</option>
        <c:forEach items="${lccLines}" var="lccLine">
            <c:choose>
                <c:when test="${lccLine.code_text == ''}">
                    <option value="${lccLine.code}">${lccLine.code}</option>
                </c:when>
                <c:otherwise>
                    <option value="${lccLine.code}">${lccLine.code_text}</option>
                </c:otherwise>
            </c:choose>
        </c:forEach>
    </c:when>

    <c:when test="${templateName == 'option_list_pub_contc'}">
        <!-- 运营商 -->
        <option value="">=请选择=</option>
        <c:forEach items="${contcs}" var="contc">
            <c:choose>
                <c:when test="${contc.code_text == ''}">
                    <option value="${contc.code}">${contc.code}</option>
                </c:when>
                <c:otherwise>
                    <option value="${contc.code}">${contc.code_text}</option>
                </c:otherwise>
            </c:choose>
        </c:forEach>
    </c:when>
    <c:when test="${templateName == 'option_list_pub_agent'}">
        <!-- 代理商 -->
        <option value="">=请选择=</option>
        <c:forEach items="${agents}" var="agent">
            <c:choose>
                <c:when test="${agent.code_text == ''}">
                    <option value="${agent.code}">${agent.code}</option>
                </c:when>
                <c:otherwise>
                    <option value="${agent.code}">${agent.code_text}</option>
                </c:otherwise>
            </c:choose>
        </c:forEach>
    </c:when>
    <c:when test="${templateName == 'option_list_pub_operator'}">
        <!-- 操作员 -->
        <option value="">=请选择=</option>
        <c:forEach items="${operators}" var="operator">
            <c:choose>
                <c:when test="${operator.code_text == ''}">
                    <option value="${operator.code}">${operator.code}</option>
                </c:when>
                <c:otherwise>
                    <option value="${operator.code}">${operator.code_text}</option>
                </c:otherwise>
            </c:choose>
        </c:forEach>
    </c:when>
    <c:when test="${templateName == 'option_list_pub_chargeServerType'}">
        <!-- 设备优先级 -->
        <option value="">=请选择=</option>
        <c:forEach items="${chargeServerTypes}" var="chargeServerType">
            <c:choose>
                <c:when test="${chargeServerType.code_text == ''}">
                    <option value="${chargeServerType.code}">${chargeServerType.code}</option>
                </c:when>
                <c:otherwise>
                    <option value="${chargeServerType.code}">${chargeServerType.code_text}</option>
                </c:otherwise>
            </c:choose>
        </c:forEach>
    </c:when>

    <c:when test="${templateName == 'option_list_pub_errLevel'}">
        <!-- 清算日志错误级别 zhouyang 201706017-->
        <option value="">=请选择=</option>
        <c:forEach items="${errLevels}" var="errLevel">
            <c:choose>
                <c:when test="${errLevel.code_text == ''}">
                    <option value="${errLevel.code}">${errLevel.code}</option>
                </c:when>
                <c:otherwise>
                    <option value="${errLevel.code}">${errLevel.code_text}</option>
                </c:otherwise>
            </c:choose>
        </c:forEach>
    </c:when>

    <c:when test="${templateName == 'option_list_pub_samTypes'}">
        <!-- SAM卡类型 -->
        <option value="">=请选择=</option>
        <c:forEach items="${samTypes}" var="samType">
            <c:choose>
                <c:when test="${samType.code_text == ''}">
                    <option value="${samType.code}">${samType.code}</option>
                </c:when>
                <c:otherwise>
                    <option value="${samType.code}">${samType.code_text}</option>
                </c:otherwise>
            </c:choose>
        </c:forEach>
    </c:when>

    <c:when test="${templateName == 'option_list_pub_modeTypes'}">
        <!-- 模式类型 -->
        <option value="">=请选择=</option>
        <c:forEach items="${modeTypes}" var="modeType">
            <c:choose>
                <c:when test="${modeType.code_text == ''}">
                    <option value="${modeType.code}">${modeType.code}</option>
                </c:when>
                <c:otherwise>
                    <option value="${modeType.code}">${modeType.code_text}</option>
                </c:otherwise>
            </c:choose>
        </c:forEach>
    </c:when>

    <c:when test="${templateName == 'option_list_acc_status_value'}">
        <!-- ACC状态 -->
        <option value="">=请选择=</option>
        <c:forEach items="${accStatusValues}" var="accStatusValue">
            <c:choose>
                <c:when test="${accStatusValue.code_text == ''}">
                    <option value="${accStatusValue.code}">${accStatusValue.code}</option>
                </c:when>
                <c:otherwise>
                    <option value="${accStatusValue.code}">${accStatusValue.code_text}</option>
                </c:otherwise>
            </c:choose>
        </c:forEach>
    </c:when>
    <c:when test="${templateName == 'option_list_pub_parmTypeIds'}">
        <!-- 参数类型ID -->
        <option value="">=请选择=</option>
        <c:forEach items="${parmTypeIds}" var="parmTypeIds">
            <c:choose>
                <c:when test="${parmTypeIds.code_text == ''}">
                    <option value="${parmTypeIds.code}">${parmTypeIds.code}</option>
                </c:when>
                <c:otherwise>
                    <option value="${parmTypeIds.code}">${parmTypeIds.code_text}</option>
                </c:otherwise>
            </c:choose>
        </c:forEach>
    </c:when>

    <c:when test="${templateName == 'option_list_pub_devRecordFlags'}">
        <!-- 设备版本标志 -->
        <option value="">=请选择=</option>
        <c:forEach items="${devRecordFlags}" var="devRecordFlags">
            <c:choose>
                <c:when test="${devRecordFlags.code_text == ''}">
                    <option value="${devRecordFlags.code}">${devRecordFlags.code}</option>
                </c:when>
                <c:otherwise>
                    <option value="${devRecordFlags.code}">${devRecordFlags.code_text}</option>
                </c:otherwise>
            </c:choose>
        </c:forEach>
    </c:when>

    <c:when test="${templateName == 'option_list_parm_type'}">
        <!-- 参数类型 -->
        <option value="">=请选择=</option>
        <c:forEach items="${parmTypes}" var="parmType">
            <c:choose>
                <c:when test="${parmType.code_text == ''}">
                    <option value="${parmType.code}">${parmType.code}</option>
                </c:when>
                <c:otherwise>
                    <option value="${parmType.code}">${parmType.code_text}</option>
                </c:otherwise>
            </c:choose>
        </c:forEach>
    </c:when>

    <c:when test="${templateName == 'option_list_status'}">
        <!-- 请求状态 -->
        <option value="">=请选择=</option>
        <c:forEach items="${statuses}" var="status">
            <c:choose>
                <c:when test="${status.code_text == ''}">
                    <option value="${status.code}">${status.code}</option>
                </c:when>
                <c:otherwise>
                    <option value="${status.code}">${status.code_text}</option>
                </c:otherwise>
            </c:choose>
        </c:forEach>
    </c:when>

    <c:when test="${templateName == 'option_list_hour'}">
        <!--tvm停售模块的小时时间模板 zhouyang 20170613-->
        <option value="00">00</option>
        <option value="01">01</option>
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
        <option value="12">12</option>
        <option value="13">13</option>
        <option value="14">14</option>
        <option value="15">15</option>
        <option value="16">16</option>
        <option value="17">17</option>
        <option value="18">18</option>
        <option value="19">19</option>
        <option value="20">20</option>
        <option value="21">21</option>
        <option value="22">22</option>
        <option value="23">23</option>
    </c:when>

    <c:when test="${templateName == 'option_list_min'}">
        <!--tvm停售模块的分钟时间模板 zhouyang 20170613-->
        <option value="00">00</option>
        <option value="01">01</option>
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
        <option value="12">12</option>
        <option value="13">13</option>
        <option value="14">14</option>
        <option value="15">15</option>
        <option value="16">16</option>
        <option value="17">17</option>
        <option value="18">18</option>
        <option value="19">19</option>
        <option value="20">20</option>
        <option value="21">21</option>
        <option value="22">22</option>
        <option value="23">23</option>
        <option value="24">24</option>
        <option value="25">25</option>
        <option value="26">26</option>
        <option value="27">27</option>
        <option value="28">28</option>
        <option value="29">29</option>
        <option value="30">30</option>
        <option value="31">31</option>
        <option value="32">32</option>
        <option value="33">33</option>
        <option value="34">34</option>
        <option value="35">35</option>
        <option value="36">36</option>
        <option value="37">37</option>
        <option value="38">38</option>
        <option value="39">39</option>
        <option value="40">40</option>
        <option value="41">41</option>
        <option value="42">42</option>
        <option value="43">43</option>
        <option value="44">44</option>
        <option value="45">45</option>
        <option value="46">46</option>
        <option value="47">47</option>
        <option value="48">48</option>
        <option value="49">49</option>
        <option value="50">50</option>
        <option value="51">51</option>
        <option value="52">52</option>
        <option value="53">53</option>
        <option value="54">54</option>
        <option value="55">55</option>
        <option value="56">56</option>
        <option value="57">57</option>
        <option value="58">58</option>
        <option value="59">59</option>
    </c:when>

    <c:when test="${templateName == 'op_button_list_query'}">
        <!--查询按钮 -->
        <tr>
            <td>
                <input type="button" id="btQuery" name="query"  value="查询" class="buttonStyle" onclick="" />
            </td>
        </tr>

    </c:when>
    <c:when test="${templateName == 'option_list_pub_systype'}">
        <!-- 系统类型 -->
        <option value="">=请选择=</option>
        <c:forEach items="${codPubFlags}" var="codPubFlag">
            <c:choose>
                <c:when test="${codPubFlag.codeText == ''}">
                    <option value="${codPubFlag.code}">${codPubFlag.code}</option>
                </c:when>
                <c:otherwise>
                    <option value="${codPubFlag.code}">${codPubFlag.codeText}</option>
                </c:otherwise>
            </c:choose>
        </c:forEach>
    </c:when>
    <c:when test="${templateName == 'option_list_pub_locked'}">
        <!-- 锁定类型 -->
        <option value="">=请选择=</option>
        <c:forEach items="${lockeds}" var="locked">
            <c:choose>
                <c:when test="${locked.codeText == ''}">
                    <option value="${locked.code}">${locked.code}</option>
                </c:when>
                <c:otherwise>
                    <option value="${locked.code}">${locked.codeText}</option>
                </c:otherwise>
            </c:choose>
        </c:forEach>
    </c:when>
    <c:when test="${templateName == 'option_list_pub_modulePer'}">
        <!-- 模块权限类型 -->
        <option value="">=请选择=</option>
        <c:forEach items="${modulePers}" var="modulePer">
            <c:choose>
                <c:when test="${modulePer.codeText == ''}">
                    <option value="${modulePer.code}">${modulePer.code}</option>
                </c:when>
                <c:otherwise>
                    <option value="${modulePer.code}">${modulePer.codeText}</option>
                </c:otherwise>
            </c:choose>
        </c:forEach>
    </c:when>
    <c:when test="${templateName == 'option_list_pub_btn'}">
        <!-- 按钮权限类型 -->
        <option value="">=请选择=</option>
        <c:forEach items="${btns}" var="btn">
            <c:choose>
                <c:when test="${btn.btnName == ''}">
                    <option value="${btn.btnId}">${btn.btnId}</option>
                </c:when>
                <c:otherwise>
                    <option value="${btn.btnId}">${btn.btnName}</option>
                </c:otherwise>
            </c:choose>
        </c:forEach>
    </c:when>
    <c:when test="${templateName == 'option_list_pub_storage'}">
        <!-- 票务仓库类型 -->
        <option value="">=请选择=</option>
        <c:forEach items="${ticketStorages}" var="ticketStorage">
            <c:choose>
                <c:when test="${ticketStorage.codeText == ''}">
                    <option value="${ticketStorage.code}">${ticketStorage.code}</option>
                </c:when>
                <c:otherwise>
                    <option value="${ticketStorage.code}">${ticketStorage.codeText}</option>
                </c:otherwise>
            </c:choose>
        </c:forEach>
    </c:when>
    <c:when test="${templateName == 'option_list_pub_grps'}">
        <!-- 系统组
        <option value="">=请选择=</option> -->
        <c:forEach items="${sysGrps}" var="sysGrp">
            <c:choose>
                <c:when test="${sysGrp.sysGroupId == ''}">
                    <option value="${sysGrp.sysGroupId}">${sysGrp.sysGroupId}</option>
                </c:when>
                <c:otherwise>
                    <option value="${sysGrp.sysGroupId}">${sysGrp.sysGroupName}</option>
                </c:otherwise>
            </c:choose>
        </c:forEach>
    </c:when>
   
    <c:when test="${templateName == 'option_list_pub_grps_1'}">
        <!-- 系统组
        <option value="">=请选择=</option> -->
        <c:forEach items="${sysGrps1}" var="sysGrp">
            <c:choose>
                <c:when test="${sysGrp.sysGroupId == ''}">
                    <option value="${sysGrp.sysGroupId}">${sysGrp.sysGroupId}</option>
                </c:when>
                <c:otherwise>
                    <option value="${sysGrp.sysGroupName}">${sysGrp.sysGroupName}</option>
                </c:otherwise>
            </c:choose>
        </c:forEach>
    </c:when>
    <c:when test="${templateName == 'option_list_pub_holidayType'}">
        <!-- 节假日代码 -->
        <option value="">=请选择=</option>
        <c:forEach items="${holidayTypes}" var="holidayType">
            <c:choose>
                <c:when test="${holidayType.code_text == ''}">
                    <option value="${holidayType.code}">${holidayType.code}</option>
                </c:when>
                <c:otherwise>
                    <option value="${holidayType.code}">${holidayType.code_text}</option>
                </c:otherwise>
            </c:choose>
        </c:forEach>
    </c:when>
    <c:when test="${templateName == 'option_list_pub_dayOfWeek'}">
        <!-- 节假日代码 -->
        <option value="">=请选择=</option>
        <c:forEach items="${dayOfWeeks}" var="dayOfWeek">
            <c:choose>
                <c:when test="${dayOfWeek.code_text == ''}">
                    <option value="${dayOfWeek.code}">${dayOfWeek.code}</option>
                </c:when>
                <c:otherwise>
                    <option value="${dayOfWeek.code}">${dayOfWeek.code_text}</option>
                </c:otherwise>
            </c:choose>
        </c:forEach>
    </c:when>
    <c:when test="${templateName == 'option_list_pub_timeCode'}">
        <!-- 乘车时间类型 -->
        <option value="">=请选择=</option>
        <c:forEach items="${timeCodes}" var="timeCode">
            <c:choose>
                <c:when test="${timeCode.code_text == ''}">
                    <option value="${timeCode.code}">${timeCode.code}</option>
                </c:when>
                <c:otherwise>
                    <option value="${timeCode.code}">${timeCode.code_text}</option>
                </c:otherwise>
            </c:choose>
        </c:forEach>
    </c:when>
    <c:when test="${templateName == 'option_list_pub_fareTable'}">
        <!-- 票价表ID-->
        <option value="">=请选择=</option>
        <c:forEach items="${fareTables}" var="fareTable">
            <c:choose>
                <c:when test="${fareTable.code_text == null}">
                    <option value="${fareTable.code}">${fareTable.code}</option>
                </c:when>
                <c:otherwise>
                    <option value="${fareTable.code}">${fareTable.code_text}</option>
                </c:otherwise>
            </c:choose>
        </c:forEach>
    </c:when>

    <c:when test="${templateName == 'option_list_pub_directionFlag'}">
        <!-- 客流方向 -->
        <option value="">=请选择=</option>
        <c:forEach items="${directionFlags}" var="directionFlag">
            <c:choose>
                <c:when test="${directionFlag.code_text == null}">
                    <option value="${directionFlag.code}">${directionFlag.code}</option>
                </c:when>
                <c:otherwise>
                    <option value="${directionFlag.code}">${directionFlag.code_text}</option>
                </c:otherwise>
            </c:choose>
        </c:forEach>
    </c:when>

    <c:when test="${templateName == 'option_list_pub_paramLogParamType'}">
        <!-- 参数类型 zhouyang 20170622-->
        <option value="">=请选择=</option>
        <c:forEach items="${paramLogParamTypes}" var="paramLogParamType">
            <c:choose>
                <c:when test="${paramLogParamType.code_text == null}">
                    <option value="${paramLogParamType.code}">${paramLogParamType.code}</option>
                </c:when>
                <c:otherwise>
                    <option value="${paramLogParamType.code}">${paramLogParamType.code_text}</option>
                </c:otherwise>
            </c:choose>
        </c:forEach>
    </c:when>
    <c:when test="${templateName == 'option_list_pub_isEffect'}">
        <!-- 设备参数生效情况（是否生效） zhouyang 20170623-->
        <option value="">=请选择=</option>
        <c:forEach items="${isEffect}" var="isEffect">
            <c:choose>
                <c:when test="${isEffect.code_text == null}">
                    <option value="${isEffect.code}">${isEffect.code}</option>
                </c:when>
                <c:otherwise>
                    <option value="${isEffect.code}">${isEffect.code_text}</option>
                </c:otherwise>
            </c:choose>
        </c:forEach>
        
    </c:when>

    <c:when test="${templateName == 'option_list_pub_ACCStatusValue'}">
        <!-- ACC状态 zhouyang 20171128-->
        <option value="">=请选择=</option>
        <c:forEach items="${ACCStatusValue}" var="ACCStatusValue">
            <c:choose>
                <c:when test="${ACCStatusValue.code_text == null}">
                    <option value="${ACCStatusValue.code}">${ACCStatusValue.code}</option>
                </c:when>
                <c:otherwise>
                    <option value="${ACCStatusValue.code}">${ACCStatusValue.code_text}</option>
                </c:otherwise>
            </c:choose>
        </c:forEach>
    </c:when>

    <c:when test="${templateName == 'option_list_pub_reportModule'}">
        <!-- 报表模板 -->
        <option value="">=请选择=</option>
        <c:forEach items="${reportModules}" var="reportModule">
            <option value="${reportModule.code}">${reportModule.code_text}</option>
        </c:forEach>
    </c:when>
    <c:when test="${templateName == 'option_list_pub_reportModuleName'}">
        <!-- 报表模板名称 -->
        <input type="hidden" id="${pVarName}" name="${pVarName}" value="${reportModuleNames}" />
    </c:when>

    <c:when test="${templateName == 'option_list_pub_reportCode'}">
        <!-- 报表代码 -->
        <option value="">=请选择=</option>
        <c:forEach items="${reportCodes}" var="reportCode">
            <option value="${reportCode.code}">${reportCode.code}</option>
        </c:forEach>
    </c:when>

    <c:when test="${templateName == 'option_list_pub_downloadStatus'}">
        <!-- 应用情况 -->
        <option value="">=请选择=</option>
        <c:forEach items="${downloadStatus}" var="downloadStatus">
            <c:choose>
                <c:when test="${downloadStatus.code_text == ''}">
                    <option value="${downloadStatus.code}">${downloadStatus.code}</option>
                </c:when>
                <c:otherwise>
                    <option value="${downloadStatus.code}">${downloadStatus.code_text}</option>
                </c:otherwise>
            </c:choose>
        </c:forEach>
    </c:when>
                    
    <c:when test="${templateName == 'option_list_pub_fareTimeId'}">
        <!-- 联乘时间间隔代码-->
        <option value="">=请选择=</option>
        <c:forEach items="${fareTimeIds}" var="fareTimeId">
            <c:choose>
                <c:when test="${fareTimeId.code_text == null}">
                    <option value="${fareTimeId.code}">${fareTimeId.code}</option>
                </c:when>
                <c:otherwise>
                    <option value="${fareTimeId.code}">${fareTimeId.code_text}</option>
                </c:otherwise>
            </c:choose>
        </c:forEach>
    </c:when>
                    
    <c:when test="${templateName == 'option_list_pub_fareDealId'}">
        <!-- 累计消费额代码-->
        <option value="">=请选择=</option>
        <c:forEach items="${fareDealIds}" var="fareDealId">
            <c:choose>
                <c:when test="${fareDealId.code_text == null}">
                    <option value="${fareDealId.code}">${fareDealId.code}</option>
                </c:when>
                <c:otherwise>
                    <option value="${fareDealId.code}">${fareDealId.code_text}</option>
                </c:otherwise>
            </c:choose>
        </c:forEach>
    </c:when>
                    
    <c:when test="${templateName == 'option_list_pub_publisher'}">
        <!-- 卡发行商 -->
        <option value="">=请选择=</option>
        <c:forEach items="${publisherFlags}" var="publisherFlags">
            <c:choose>
                <c:when test="${publisherFlags.code_text == ''}">
                    <option value="${publisherFlags.code}">${publisherFlags.code}</option>
                </c:when>
                <c:otherwise>
                    <option value="${publisherFlags.code}">${publisherFlags.code_text}</option>
                </c:otherwise>
            </c:choose>
        </c:forEach>
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
                <input type="button" id="btResetPass" name="btResetPass" disabled="true" value="重置密码" class="buttonStyle" onclick="" />
                <input type="button" id="btGoPage" name="btGoPage" disabled="true" value="跳转" class="buttonStyle" onclick="" /><input type="text" value="${goPageNos}" name="goPageNos" size="1">页
            </td>
        </tr>

    </c:when>
    <c:when test="${templateName == 'op_button_list_all_init'}">
        <c:set var="clone" scope="request" value="0"/>
        <c:set var="submit" scope="request" value="0"/>

        <c:set var="query" scope="request" value="0"/>
        <!--add by zhongzq-->
        <c:set var="query1" scope="request" value="0"/>
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
        <c:set var="distribute" scope="request" value="0"/>
        <c:set var="inBlack" scope="request" value="0"/>

        <c:set var="genFile" scope="request" value="0"/>

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

        <c:set var="importUpload" scope="request" value="''"/>
        <c:set var="import_2" scope="request" value="0"/>
        <c:set var="export_right" scope="request" value="0"/>
        <c:set var="print_right" scope="request" value="0"/>

    </c:when>


    <c:when test="${templateName == 'op_button_list_all'}">

        <table width="95%" align="center">
            <tr>
                <td>

                    <c:if test="${query1 == 1}"><input type="button" id="btQuery" name="query"  value="查询" class="buttonStyle" onclick="${addClickMethod}; queryConfirm( & quot;${checkConditionMethod} & quot; , & quot;${clickMethod} & quot; );" /></c:if>
                    <c:if test="${query == 1}"><input type="button" id="btQuery" name="query"  value="查询" class="buttonStyle" onclick="${addClickMethod};${clickMethod};" /></c:if>
                    <c:if test="${add == 1}"><input type="button" id="btAdd" name="add" disabled="true" value="增加" class="buttonStyle" onclick="${addBeforeMethod};${clickMethod};${addAfterMethod}" /></c:if>
                    <c:if test="${del == 1}"><input type="button" id="btDelete" name="del" disabled="true" value="删除" class="buttonStyle" onclick="${addQueryMethod};${clickMethod}" /></c:if>
                    <c:if test="${modify == 1}"><input type="button" id="btModify" name="modify" disabled="true0" value="修改" class="buttonStyle" onclick="${clickMethod};${addAfterClickModifyMethod}" /></c:if>
                    <c:if test="${save == 1}"><input type="button" id="btSave" name="save" disabled="true" value="保存" class="buttonStyle" onclick="${addQueryMethod};${addClickMethod};${clickMethod}" /></c:if>
                    <c:if test="${cancle == 1}"><input type="button" id="btCancle" name="cancle" disabled="true" value="取消" class="buttonStyle" onclick="${clickMethod}" /></c:if>
                    <c:if test="${clone == 1}"><input type="button" id="btClone" name="clone" disabled="true" value="克隆" class="buttonStyle" onclick="${addQueryMethod};${clickMethod}" /></c:if>
                    <c:if test="${submit == 1}"><input type="button" id="btSubmit" name="submit1" disabled="true" value="提交" class="buttonStyle" onclick="${addQueryMethod};${clickMethod}" /></c:if>
                    <c:if test="${import_1 == 1}"><input type="button" id="btImport" name="import" disabled="true" value="导入" class="buttonStyle" onclick="${clickMethod}" /></c:if>
                    <c:if test="${import_2 == 1 && version.version_no == '0000000000'}"><input type="button" id="btImport" name="import" disabled="true" value="导入" class="buttonStyle" onclick="${importUpload}" /></c:if>
                    <c:if test="${distribute == 1}"><input type="button" id="btDistribute" name="distribute" disabled="true" value="分配" class="buttonStyle" onclick="${clickMethod}" /></c:if>
                    <c:if test="${inBlack == 1}"><input type="button" id="btInBlack" name="inBlack" disabled="true" value="加入黑名单" class="buttonStyle" onclick="${clickMethod}" /></c:if>

                    <c:if test="${genFile == 1}"><input type="button" id="btGenFile" name="genFile" disabled="true" value="生成文件" class="buttonStyle" onclick="${addQueryMethod};${clickMethod}" /></c:if>
                    <c:if test="${export_right == 1}"><input type="button" id="btExport" name="export" disabled="true" value="导出" class="buttonStyle" onclick="${expRight}" /></c:if>
                    <c:if test="${print_right == 1}"><input type="button" id="btPrint" name="print" disabled="true" value="打印" class="buttonStyle" onclick="${printRight}" /></c:if>

                    <c:if test="${download == 1}"><input type="button" id="btDownload" name="download"  value="下发" class="buttonStyle" onclick="${clickMethod}" /></c:if>
                    <c:if test="${statistic == 1}"><input type="button" id="btStatistic" name="statistic"  value="统计" class="buttonStyle" onclick="${clickMethod}" /></c:if>
                        <!--modify by zhongziqi 20170627-->
                    <%--<c:if test="${btRefundOk == 1}"><input type="button" id="btRefundOk" name="btRefundOk"  value="确认退款" class="buttonStyle" onclick="submitConfirm1All('${addClickMethod}', '${clickMethod}')" /></c:if>--%>
                    <%--<c:if test="${btRefundNo == 1}"><input type="button" id="btRefundNo" name="btRefundNo"  value="拒绝退款" class="buttonStyle" onclick="submitConfirm2All('${addClickMethod1}', '${clickMethod}')" /></c:if>--%>
                    <%--<c:if test="${btRefundMd == 1}"><input type="button" id="btRefundMd" name="btRefundMd"  value="确认修改" class="buttonStyle" onclick="submitConfirm3All('${addClickMethod2}', '${clickMethod}')" /></c:if>--%>
                    <%--<c:if test="${btRefundCk == 1}"><input type="button" id="btRefundCk" name="btRefundCk"  value="确认审核" class="buttonStyle" onclick="submitConfirm4All('${addClickMethod3}', '${clickMethod}')" /></c:if>--%>
                    <c:if test="${btRefundOk == 1}"><input type="button" id="btRefundOk" name="btRefundOk"  value="确认退款" class="buttonStyle" onclick="submitConfirm1All(&quot;${addClickMethod}&quot;, &quot;${clickMethod}&quot;)" /></c:if>
                    <c:if test="${btRefundNo == 1}"><input type="button" id="btRefundNo" name="btRefundNo"  value="拒绝退款" class="buttonStyle" onclick="submitConfirm2All(&quot;${addClickMethod1}&quot;, &quot;${clickMethod}&quot;)" /></c:if>
                    <c:if test="${btRefundMd == 1}"><input type="button" id="btRefundMd" name="btRefundMd"  value="确认修改" class="buttonStyle" onclick="submitConfirm3All(&quot;${addClickMethod2}&quot;, &quot;${clickMethod}&quot;)" /></c:if>
                    <c:if test="${btRefundCk == 1}"><input type="button" id="btRefundCk" name="btRefundCk"  value="确认审核" class="buttonStyle" onclick="submitConfirm4All(&quot;${addClickMethod3}&quot;, &quot;${clickMethod}&quot;)" /></c:if>
                    <c:if test="${exportBill == 1}"><input type="button" id="btExportBill" name="exportBill" disabled="true" value="单据导出" class="buttonStyle" onclick="${exportBillBefore};${clickMethod};${exportBillAfter}" /></c:if>
                    <c:if test="${audit == 1}"><input type="button" id="btAudit" name="audit"  value="审核" class="buttonStyle" onclick="${addClickMethod};${clickMethod};" /></c:if>
                    <c:if test="${expAll == 1}"><input type="button" id="btExpAll" name="expAll" disabled="true" value="导出全部" class="buttonStyle" onclick="${clickMethod}" /></c:if>
                    <c:if test="${export == 1}"><input type="button" id="btExport" name="export" disabled="true" value="导出" class="buttonStyle" onclick="${clickMethod}" /></c:if>
                    <c:if test="${print == 1}"><input type="button" id="btPrint" name="print" disabled="true" value="打印" class="buttonStyle" onclick="${clickMethod}" /></c:if>
                    <c:if test="${btBackEnd == 1}"><input type="button" id="btBackEnd" name="btBackEnd" disabled="true" value="始页" class="buttonStyle" onclick="${clickMethod};" /></c:if>
                    <c:if test="${btBack == 1}"><input type="button" id="btBack" name="btBack" disabled="true" value="前一页" class="buttonStyle" onclick="${clickMethod};" /></c:if>
                    <c:if test="${btNext == 1}"><input type="button" id="btNext" name="btNext" disabled="true" value="下一页" class="buttonStyle" onclick="${clickMethod};" /></c:if>
                    <c:if test="${btNextEnd == 1}"><input type="button" id="btNextEnd" name="btNextEnd" disabled="true" value="止页" class="buttonStyle" onclick="${clickMethod};" /></c:if>
                    <c:if test="${btResetPass == 1}"><input type="button" id="btResetPass" name="btResetPass" disabled="true" value="重置密码" class="buttonStyle" onclick="${clickMethod};" /></c:if>
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
