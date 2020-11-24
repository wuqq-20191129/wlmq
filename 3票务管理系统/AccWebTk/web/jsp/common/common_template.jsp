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
    <c:when test="${templateName == 'option_list_pub_storage'}">
        <!-- 仓库 -->
        <option value="">=请选择=</option>
        <c:forEach items="${storages}" var="storage">
            <c:choose>
                <c:when test="${storage.code_text == ''}">
                    <option value="${storage.code}">${storage.code}</option>
                </c:when>
                <c:otherwise>
                    <option value="${storage.code}">${storage.code_text}</option>
                </c:otherwise>
            </c:choose>
        </c:forEach>
    </c:when>
    <c:when test="${templateName == 'option_list_pub_storagezone'}">

        <!-- 票区 -->

        <input type="hidden" id="${pVarName}" name="${pVarName}" value="${storageZones}" />
    </c:when>
    <c:when test="${templateName == 'option_list_pub_storagezone_destroy'}">

        <!-- 票区 -->

        <input type="hidden" id="${pVarName}" name="${pVarName}" value="${storageZonesDestroy}" />
    </c:when>
    <c:when test="${templateName == 'option_list_pub_storagezone_borrow_in'}">

        <!-- 票区 -->

        <input type="hidden" id="${pVarName}" name="${pVarName}" value="${storageZonesBorrowIn}" />
    </c:when>
    <c:when test="${templateName == 'option_list_pub_storagezone_recover'}">

        <!-- 回收票区 add by luck-->

        <input type="hidden" id="${pVarName}" name="${pVarName}" value="${storageZones_recover}" />
    </c:when>
    <c:when test="${templateName == 'option_list_pub_storagezone_cancel'}">

        <!-- 待注销票区 add by luck-->

        <input type="hidden" id="${pVarName}" name="${pVarName}" value="${storageZones_cancel}" />
    </c:when>
    <c:when test="${templateName == 'option_list_pub_storagezone_cod_and_value'}">

        <!-- 赋值区编码区 add by ldz-->

        <input type="hidden" id="${pVarName}" name="${pVarName}" value="${storageZones_cod_and_value}" />
    </c:when>
    <c:when test="${templateName == 'option_list_pub_maincard'}">
        <!-- 票库票卡主类型 -->
        <option value="">=请选择=</option>
        <c:forEach items="${icCardMainTypes}" var="icCardMainType">
            <c:choose>
                <c:when test="${icCardMainType.code_text == ''}">
                    <option value="${icCardMainType.code}">${icCardMainType.code}</option>
                </c:when>
                <c:otherwise>
                    <option value="${icCardMainType.code}">${icCardMainType.code_text}</option>
                </c:otherwise>
            </c:choose>
        </c:forEach>
    </c:when>
    <c:when test="${templateName == 'option_list_pub_maincard_default'}">
        <!-- 票库票卡主类型 -->
        <c:forEach items="${icCardMainTypes}" var="icCardMainType">
            <c:choose>
                <c:when test="${icCardMainType.code_text == ''}">
                    <option value="${icCardMainType.code}">${icCardMainType.code}</option>
                </c:when>
                <c:otherwise>
                    <option value="${icCardMainType.code}">${icCardMainType.code_text}</option>
                </c:otherwise>
            </c:choose>
        </c:forEach>
    </c:when>
    <c:when test="${templateName == 'option_list_pub_maincard_limit'}">
        <!-- 票库票卡主类型 -->
        <option value="">=请选择=</option>
        <c:forEach items="${icCardMainTypesLimit}" var="icCardMainType">
            <c:choose>
                <c:when test="${icCardMainType.code_text == ''}">
                    <option value="${icCardMainType.code}">${icCardMainType.code}</option>
                </c:when>
                <c:otherwise>
                    <option value="${icCardMainType.code}">${icCardMainType.code_text}</option>
                </c:otherwise>
            </c:choose>
        </c:forEach>
    </c:when>
    <c:when test="${templateName == 'option_list_pub_maincard_clean'}">
        <!-- 票库票卡主类型清洗 add by luck 20170725-->
        <option value="">=请选择=</option>
        <c:forEach items="${icCardMainTypes}" var="icCardMainType">
            <c:if test="${icCardMainType.code == '12'}">
                <c:choose>
                    <c:when test="${icCardMainType.code_text == ''}">
                        <option value="${icCardMainType.code}">${icCardMainType.code}</option>
                    </c:when>
                    <c:otherwise>
                        <option value="${icCardMainType.code}">${icCardMainType.code_text}</option>
                    </c:otherwise>
                </c:choose>
            </c:if>
        </c:forEach>
    </c:when>

    <c:when test="${templateName == 'option_list_pub_maincard_cancel'}">
        <!-- 票库票卡主类型核查 add by luck 2017804-->
        <option value="">=请选择=</option>
        <c:forEach items="${icCardMainTypes}" var="icCardMainType">
            <c:if test="${icCardMainType.code == '12' or icCardMainType.code == '1' or icCardMainType.code == '2' or icCardMainType.code == '40' }">
                <c:choose>
                    <c:when test="${icCardMainType.code_text == ''}">
                        <option value="${icCardMainType.code}">${icCardMainType.code}</option>
                    </c:when>
                    <c:otherwise>
                        <option value="${icCardMainType.code}">${icCardMainType.code_text}</option>
                    </c:otherwise>
                </c:choose>
            </c:if>
        </c:forEach>
    </c:when>
    <c:when test="${templateName == 'option_list_pub_maincard_serial'}">
        <!-- 票库票卡主类型串 -->
        <input type="hidden" id="${pVarName}" name="${pVarName}" value="${icCardMainTypesSerial}" />
    </c:when>

    <c:when test="${templateName == 'option_list_pub_mainsubcard'}">
        <!-- 票库票卡子类型 -->
        <input type="hidden" id="${pVarName}" name="${pVarName}" value="${icCardMainSubTypes}" />
    </c:when>

    <c:when test="${templateName == 'option_list_pub_prm_maincard'}">
        <!-- （运营）票卡主类型 -->
        <option value="">=请选择=</option>
        <c:forEach items="${afcCardMainTypes}" var="afcCardMainType">
            <c:choose>
                <c:when test="${afcCardMainType.code_text == ''}">
                    <option value="${afcCardMainType.code}">${afcCardMainType.code}</option>
                </c:when>
                <c:otherwise>
                    <option value="${afcCardMainType.code}">${afcCardMainType.code_text}</option>
                </c:otherwise>
            </c:choose>
        </c:forEach>
    </c:when>
    <c:when test="${templateName == 'option_list_pub_cardSub_none'}">
        <!-- 票卡子类型缺省 -->
        <option value="">=请选择=</option>
    </c:when>
    <c:when test="${templateName == 'option_list_pub_prm_subcard'}">
        <!--（运营）票卡子类型 -->
        <input type="hidden" id="${pVarName}" name="${pVarName}" value="${cardMainSubs}" />
    </c:when>

    <c:when test="${templateName == 'option_list_pub_outreason_produce'}">
        <!-- 生产出库原因 -->
        <option value="">=请选择=</option>
        <c:forEach items="${inOutReasonProduces}" var="inOutReasonProduce">
            <c:choose>
                <c:when test="${inOutReasonProduce.code_text == ''}">
                    <option value="${inOutReasonProduce.code}">${inOutReasonProduce.code}</option>
                </c:when>
                <c:otherwise>
                    <option value="${inOutReasonProduce.code}">${inOutReasonProduce.code_text}</option>
                </c:otherwise>
            </c:choose>
        </c:forEach>
    </c:when>

    <c:when test="${templateName == 'option_list_pub_outreason_cancel'}">
        <!-- 核查出库原因 -->
        <c:forEach items="${inOutReasonProduces}" var="inOutReasonProduce">
            <c:if test="${inOutReasonProduce.code == '06'}">
                <c:choose>
                    <c:when test="${inOutReasonProduce.code_text == ''}">
                        <option value="${inOutReasonProduce.code}">${inOutReasonProduce.code}</option>
                    </c:when>
                    <c:otherwise>
                        <option value="${inOutReasonProduce.code}">${inOutReasonProduce.code_text}</option>
                    </c:otherwise>
                </c:choose>
            </c:if>
        </c:forEach>

    </c:when>

    <c:when test="${templateName == 'option_list_pub_outreason_distribute'}">
        <!-- 配票出库原因 -->
        <option value="">=请选择=</option>
        <c:forEach items="${inOutReasonDistributes}" var="inOutReasonDistribute">
            <c:choose>
                <c:when test="${inOutReasonDistribute.code_text == ''}">
                    <option value="${inOutReasonDistribute.code}">${inOutReasonDistribute.code}</option>
                </c:when>
                <c:otherwise>
                    <option value="${inOutReasonDistribute.code}">${inOutReasonDistribute.code_text}</option>
                </c:otherwise>
            </c:choose>
        </c:forEach>
    </c:when>
    <c:when test="${templateName == 'option_list_pub_outreason_outAdjust'}">
        <!-- 调账出库原因 -->
        <c:forEach items="${inOutReasonProduces}" var="inOutReasonProduce">
            <c:if test="${inOutReasonProduce.code == '22'}">
                <c:choose>
                    <c:when test="${inOutReasonProduce.code_text == ''}">
                        <option value="${inOutReasonProduce.code}">${inOutReasonProduce.code}</option>
                    </c:when>
                    <c:otherwise>
                        <option value="${inOutReasonProduce.code}">${inOutReasonProduce.code_text}</option>
                    </c:otherwise>
                </c:choose>
            </c:if>
        </c:forEach>
    </c:when>
    <c:when test="${templateName == 'option_list_pub_outreason_borrow_until'}">
        <!-- 借票单位 -->
        <option value="">=请选择=</option>
        <c:forEach items="${borrowUnits}" var="borrowUnit">
            <c:choose>
                <c:when test="${borrowUnit.code_text == ''}">
                    <option value="${borrowUnit.code}">${borrowUnit.code}</option>
                </c:when>
                <c:otherwise>
                    <option value="${borrowUnit.code}">${borrowUnit.code_text}</option>
                </c:otherwise>
            </c:choose>
        </c:forEach>
    </c:when>


    <c:when test="${templateName == 'option_list_pub_in_out_type'}">
        <!-- 出入库主类型 -->
        <option value="">=请选择=</option>
        <c:forEach items="${inOutTypes}" var="inOutTypes">
            <c:choose>
                <c:when test="${inOutTypes.code_text == ''}">
                    <option value="${inOutTypes.code}">${inOutTypes.code}</option>
                </c:when>
                <c:otherwise>
                    <option value="${inOutTypes.code}">${inOutTypes.code_text}</option>
                </c:otherwise>
            </c:choose>
        </c:forEach>
    </c:when>
    <c:when test="${templateName == 'option_list_pub_none'}">
        <!-- 出入库子类型缺省 -->
        <option value="">=请选择=</option>
    </c:when>


    <c:when test="${templateName == 'option_list_pub_in_out_sub_type'}">
        <!--出入库子类型 -->
        <input type="hidden" id="${pVarName}" name="${pVarName}" value="${inOutMainSubTypes}" />
    </c:when>






    <c:when test="${templateName == 'option_list_pub_outreason_produce_serial'}">
        <!-- 生产出库原因串 -->
        <input type="hidden" id="${pVarName}" name="${pVarName}" value="${inOutReasonProducesSerial}" />
    </c:when>

    <c:when test="${templateName == 'option_list_pub_billstatues'}">
        <!-- 单据状态 -->
        <option value="">=请选择=</option>
        <c:forEach items="${billStatues}" var="billState">
            <c:choose>
                <c:when test="${billState.code_text == ''}">
                    <option value="${billState.code}">${billState.code}</option>
                </c:when>
                <c:otherwise>
                    <option value="${billState.code}">${billState.code_text}</option>
                </c:otherwise>
            </c:choose>
        </c:forEach>
    </c:when>

    <c:when test="${templateName == 'option_list_pub_esoperator'}">
        <!-- es操作员 -->
        <option value="">=请选择=</option>
        <c:forEach items="${esOperators}" var="esOperator">
            <c:choose>
                <c:when test="${esOperator.code_text == ''}">
                    <option value="${esOperator.code}">${esOperator.code}</option>
                </c:when>
                <c:otherwise>
                    <option value="${esOperator.code}">${esOperator.code_text}</option>
                </c:otherwise>
            </c:choose>
        </c:forEach>
    </c:when>

    <c:when test="${templateName == 'option_list_pub_signcardflag'}">
        <!-- 记名卡标志 -->

        <c:forEach items="${signCards}" var="signCard">
            <c:choose>
                <c:when test="${signCard.code_text == ''}">
                    <option value="${signCard.code}">${signCard.code}</option>
                </c:when>
                <c:otherwise>
                    <option value="${signCard.code}">${signCard.code_text}</option>
                </c:otherwise>
            </c:choose>
        </c:forEach>
    </c:when>

    <c:when test="${templateName == 'option_list_pub_cardmoney'}">
        <!-- 面值 -->

        <c:forEach items="${cardMoneys}" var="cardMoney">
            <c:choose>
                <c:when test="${cardMoney.code_text == ''}">
                    <option value="${cardMoney.code}">${cardMoney.code}</option>
                </c:when>
                <c:otherwise>
                    <option value="${cardMoney.code}">${cardMoney.code_text}</option>
                </c:otherwise>
            </c:choose>
        </c:forEach>
    </c:when>


    <c:when test="${templateName == 'option_list_pub_mode'}">
        <!-- 进出站模式 -->

        <c:forEach items="${modes}" var="mode">
            <c:choose>
                <c:when test="${mode.code_text == ''}">
                    <option value="${mode.code}">${mode.code}</option>
                </c:when>
                <c:otherwise>
                    <option value="${mode.code}">${mode.code_text}</option>
                </c:otherwise>
            </c:choose>
        </c:forEach>
    </c:when>

    <c:when test="${templateName == 'option_list_pub_testflag'}">
        <!-- 测试标识  -->

        <c:forEach items="${testFlags}" var="testFlag">
            <c:choose>
                <c:when test="${testFlag.code_text == ''}">
                    <option value="${testFlag.code}">${testFlag.code}</option>
                </c:when>
                <c:otherwise>
                    <option value="${testFlag.code}">${testFlag.code_text}</option>
                </c:otherwise>
            </c:choose>
        </c:forEach>
    </c:when>


    <c:when test="${templateName == 'option_list_pub_ordertype'}">
        <!-- 订单类型  -->

        <c:forEach items="${orderTypes}" var="orderType">
            <c:choose>
                <c:when test="${orderType.code_text == ''}">
                    <option value="${orderType.code}">${orderType.code}</option>
                </c:when>
                <c:otherwise>
                    <option value="${orderType.code}">${orderType.code_text}</option>
                </c:otherwise>
            </c:choose>
        </c:forEach>
    </c:when>

    <c:when test="${templateName == 'option_list_pub_afcLine'}">
        <!--AFC 线路 add by ysw-->
        <option value="">=请选择=</option>
        <c:forEach items="${afcLines}" var="afcLine">
            <c:choose>
                <c:when test="${afcLine.code_text == ''}">
                    <option value="${afcLine.code}">${afcLine.code}</option>
                </c:when>
                <c:otherwise>
                    <option value="${afcLine.code}">${afcLine.code_text}</option>
                </c:otherwise>
            </c:choose>
        </c:forEach>
    </c:when>

    <c:when test="${templateName == 'option_list_pub_icline'}">
        <!--IC 线路 -->
        <option value="">=请选择=</option>
        <c:forEach items="${icLines}" var="icLine">
            <c:choose>
                <c:when test="${icLine.code_text == ''}">
                    <option value="${icLine.code}">${icLine.code}</option>
                </c:when>
                <c:otherwise>
                    <option value="${icLine.code}">${icLine.code_text}</option>
                </c:otherwise>
            </c:choose>
        </c:forEach>
    </c:when>
    <c:when test="${templateName == 'option_list_pub_icline_for_content'}">
        <!--IC 线路 -->
        <option value="-1">所有</option>
        <c:forEach items="${icLines}" var="icLine">
            <c:choose>
                <c:when test="${icLine.code_text == ''}">
                    <option value="${icLine.code}">${icLine.code}</option>
                </c:when>
                <c:otherwise>
                    <option value="${icLine.code}">${icLine.code_text}</option>
                </c:otherwise>
            </c:choose>
        </c:forEach>
    </c:when>

    <c:when test="${templateName == 'option_list_pub_icline_serial'}">
        <!-- IC 线路串 -->
        <input type="hidden" id="${pVarName}" name="${pVarName}" value="${icLinesSerial}" />
    </c:when>

    <c:when test="${templateName == 'option_list_pub_storage_line_serial'}">
        <!-- 仓库IC线路串 -->
        <input type="hidden" id="${pVarName}" name="${pVarName}" value="${storageLinesSerial}" />
    </c:when>

    <c:when test="${templateName == 'option_list_pub_none'}">
        <!-- IC 车站缺省 -->
        <option value="">=请选择=</option>
    </c:when>

    <c:when test="${templateName == 'option_list_pub_iclinestation'}">
        <!-- ic 车站 -->
        <input type="hidden" id="${pVarName}" name="${pVarName}" value="${icLineStations}" />
    </c:when>

    <c:when test="${templateName == 'option_list_pub_limit_station_flag'}">
        <!-- 限站使用标志 -->
        <input type="hidden" id="${pVarName}" name="${pVarName}" value="${limitStationFlagSerial}" />
    </c:when>
    <c:when test="${templateName == 'option_list_pub_worktype'}">
        <!-- ES工作类型  -->
        <option value="">=请选择=</option>
        <c:forEach items="${esWorkTypes}" var="esWorkType">
            <c:choose>
                <c:when test="${esWorkType.code_text == ''}">
                    <option value="${esWorkType.code}">${esWorkType.code}</option>
                </c:when>
                <c:otherwise>
                    <option value="${esWorkType.code}">${esWorkType.code_text}</option>
                </c:otherwise>
            </c:choose>
        </c:forEach>
    </c:when>

    <c:when test="${templateName == 'option_list_pub_worktype_cancel'}">
        <!-- ES工作类型  核查-->

        <c:forEach items="${esWorkTypes}" var="esWorkType">
            <c:if test="${esWorkType.code == '03'}">
                <c:choose>
                    <c:when test="${esWorkType.code_text == ''}">
                        <option value="${esWorkType.code}">${esWorkType.code}</option>
                    </c:when>
                    <c:otherwise>
                        <option value="${esWorkType.code}">${esWorkType.code_text}</option>
                    </c:otherwise>
                </c:choose>
            </c:if>
        </c:forEach>
    </c:when>

    <c:when test="${templateName == 'option_list_pub_cleanOutBill'}">
        <!-- 清洗入库出库单 add by luck 20170729  -->
        <option value="">===请选择===</option>
        <c:forEach items="${cleanOutBills}" var="cleanOutBill">
            <c:choose>
                <c:when test="${cleanOutBill.code_text == ''}">
                    <option value="${cleanOutBill.code}">${cleanOutBill.code}</option>
                </c:when>
                <c:otherwise>
                    <option value="${cleanOutBill.code}">${cleanOutBill.code_text}</option>
                </c:otherwise>
            </c:choose>
        </c:forEach>
    </c:when>

    <c:when test="${templateName == 'option_list_pub_cancelOutBill'}">
        <!-- 核查入库出库单 add by luck 20170807  -->
        <option value="">===请选择===</option>
        <c:forEach items="${cancelOutBills}" var="cancelOutBill">
            <c:choose>
                <c:when test="${cancelOutBill.code_text == ''}">
                    <option value="${cancelOutBill.code}">${cancelOutBill.code}</option>
                </c:when>
                <c:otherwise>
                    <option value="${cancelOutBill.code}">${cancelOutBill.code_text}</option>
                </c:otherwise>
            </c:choose>
        </c:forEach>
    </c:when>

    <c:when test="${templateName == 'option_list_pub_paraFlag'}">
        <!-- 流失量参数标志 add by ldz 20170807  -->
        <option value="">===请选择===</option>
        <c:forEach items="${paraFlag}" var="paraFlag">
            <c:choose>
                <c:when test="${paraFlag.code_text == ''}">
                    <option value="${paraFlag.code}">${paraFlag.code}</option>
                </c:when>
                <c:otherwise>
                    <option value="${paraFlag.code}">${paraFlag.code_text}</option>
                </c:otherwise>
            </c:choose>
        </c:forEach>
    </c:when>

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
    <c:when test="${templateName == 'option_list_pub_inreason_in_produce'}">
        <!-- 生产入库原因 -->
        <option value="">=请选择=</option>
        <c:forEach items="${inOutReasonForInProduces}" var="inOutReasonForInProduce">
            <c:choose>
                <c:when test="${inOutReasonForInProduce.code_text == ''}">
                    <option value="${inOutReasonForInProduce.code}">${inOutReasonForInProduce.code}</option>
                </c:when>
                <c:otherwise>
                    <option value="${inOutReasonForInProduce.code}">${inOutReasonForInProduce.code_text}</option>
                </c:otherwise>
            </c:choose>
        </c:forEach>
    </c:when>

    <c:when test="${templateName == 'option_list_pub_inreason_in_new'}">
        <!-- 新票入库原因 -->
        <option value="">=请选择=</option>
        <c:forEach items="${inOutReasonForIn}" var="inOutReasonForIn">
            <c:choose>
                <c:when test="${inOutReasonForIn.code_text == ''}">
                    <option value="${inOutReasonForIn.code}">${inOutReasonForIn.code}</option>
                </c:when>
                <c:otherwise>
                    <option value="${inOutReasonForIn.code}">${inOutReasonForIn.code_text}</option>
                </c:otherwise>
            </c:choose>
        </c:forEach>
    </c:when>
    <c:when test="${templateName == 'option_list_pub_inreason_inAdjust'}">
        <!-- 调账入库原因 -->
        <c:forEach items="${inOutReasonForIn}" var="inOutReasonForIn">
            <c:if test="${inOutReasonForIn.code == '16'}">
                <c:choose>
                    <c:when test="${inOutReasonForIn.code_text == ''}">
                        <option value="${inOutReasonForIn.code}">${inOutReasonForIn.code}</option>
                    </c:when>
                    <c:otherwise>
                        <option value="${inOutReasonForIn.code}">${inOutReasonForIn.code_text}</option>
                    </c:otherwise>
                </c:choose>
            </c:if>
        </c:forEach>
    </c:when>
    <c:when test="${templateName == 'option_list_pub_pudProduceBill'}">
        <!-- 生产工作单    -->
        <option value="">=请选择=</option>
        <c:forEach items="${pduProduceBills}" var="pduProduceBill">
            <c:choose>
                <c:when test="${pduProduceBill.code_text == ''}">
                    <option value="${pduProduceBill.code}">${pduProduceBill.code}</option>
                </c:when>
                <c:otherwise>
                    <option value="${pduProduceBill.code}">${pduProduceBill.code_text}</option>
                </c:otherwise>
            </c:choose>
        </c:forEach>
    </c:when>

    <c:when test="${templateName == 'option_list_pub_lendBillNos'}">
        <!-- 借票归还--借票单号    -->
        <option value="">=请选择=</option>
        <c:forEach items="${lendBillNos}" var="lendBillNo">
            <c:choose>
                <c:when test="${lendBillNo.code_text == ''}">
                    <option value="${lendBillNo.code}">${lendBillNo.code}</option>
                </c:when>
                <c:otherwise>
                    <option value="${lendBillNo.code}">${lendBillNo.code_text}</option>
                </c:otherwise>
            </c:choose>
        </c:forEach>
    </c:when>

    <c:when test="${templateName == 'option_list_pub_sex'}">
        <!-- 性别    -->
        <option value="">=请选择=</option>
        <c:forEach items="${sexs}" var="sex">
            <c:choose>
                <c:when test="${sex.code_text == ''}">
                    <option value="${sex.code}">${sex.code}</option>
                </c:when>
                <c:otherwise>
                    <option value="${sex.code}">${sex.code_text}</option>
                </c:otherwise>
            </c:choose>
        </c:forEach>
    </c:when>

    <c:when test="${templateName == 'option_list_pub_crdType'}">
        <!-- 证件类型    -->
        <option value="">=请选择=</option>
        <c:forEach items="${crdTypes}" var="crdType">
            <c:choose>
                <c:when test="${crdType.code_text == ''}">
                    <option value="${crdType.code}">${crdType.code}</option>
                </c:when>
                <c:otherwise>
                    <option value="${crdType.code}">${crdType.code_text}</option>
                </c:otherwise>
            </c:choose>
        </c:forEach>
    </c:when>

    <c:when test="${templateName == 'option_list_pub_testFlag'}">
        <!-- 测试标志    -->
        <option value="">=请选择=</option>
        <c:forEach items="${testFlags}" var="testFlag">
            <c:choose>
                <c:when test="${testFlag.code_text == ''}">
                    <option value="${testFlag.code}">${testFlag.code}</option>
                </c:when>
                <c:otherwise>
                    <option value="${testFlag.code}">${testFlag.code_text}</option>
                </c:otherwise>
            </c:choose>
        </c:forEach>
    </c:when>
    <c:when test="${templateName == 'option_list_pub_years'}">
        <!-- 年   add by luck 20170808 -->
        <c:forEach items="${Years}" var="Year">

            <option value="${Year}">${Year}</option>

        </c:forEach>
    </c:when>

    <c:when test="${templateName == 'option_list_pub_bynum'}">
        <!-- 面值    -->
        <option value="">=请选择=</option>
        <c:forEach items="${cardMoneys}" var="cardMoney">
            <c:choose>
                <c:when test="${cardMoney.code_text == ''}">
                    <option value="${cardMoney.code}">${cardMoney.code}</option>
                </c:when>
                <c:otherwise>
                    <option value="${cardMoney.code}">${cardMoney.code_text}</option>
                </c:otherwise>
            </c:choose>
        </c:forEach>
    </c:when>


    <c:when test="${templateName == 'option_list_pub_produce_factory'}">
        <!-- 生产厂商 -->
        <option value="">=请选择=</option>
        <c:forEach items="${produceFactorys}" var="produceFactorys">
            <c:choose>
                <c:when test="${produceFactorys.code_text == ''}">
                    <option value="${produceFactorys.code}">${produceFactorys.code}</option>
                </c:when>
                <c:otherwise>
                    <option value="${produceFactorys.code}">${produceFactorys.code_text}</option>
                </c:otherwise>
            </c:choose>
        </c:forEach>
    </c:when>

    <c:when test="${templateName == 'option_list_pub_blank_card_type'}">
        <!-- 空白卡订单 票卡类型 -->
        <option value="">=请选择=</option>
        <c:forEach items="${blankCardTypes}" var="blankCardTypes">
            <c:choose>
                <c:when test="${blankCardTypes.code_text == ''}">
                    <option value="${blankCardTypes.code}">${blankCardTypes.code}</option>
                </c:when>
                <c:otherwise>
                    <option value="${blankCardTypes.code}">${blankCardTypes.code_text}</option>
                </c:otherwise>
            </c:choose>
        </c:forEach>
    </c:when>

    <c:when test="${templateName == 'option_list_pub_is_used_flag'}">
        <!-- 是否已使用标识 -->
        <option value="">=请选择=</option>
        <c:forEach items="${isUsedFlag}" var="isUsedFlag">
            <c:choose>
                <c:when test="${isUsedFlag.code_text == ''}">
                    <option value="${isUsedFlag.code}">${isUsedFlag.code}</option>
                </c:when>
                <c:otherwise>
                    <option value="${isUsedFlag.code}">${isUsedFlag.code_text}</option>
                </c:otherwise>
            </c:choose>
        </c:forEach>
    </c:when>

    <c:when test="${templateName == 'option_list_pub_record_flag'}">
        <!-- 单据状态 -->
        <option value="">=请选择=</option>
        <c:forEach items="${recordFlags}" var="recordFlags">
            <c:choose>
                <c:when test="${recordFlags.code_text == ''}">
                    <option value="${recordFlags.code}">${recordFlags.code}</option>
                </c:when>
                <c:otherwise>
                    <option value="${recordFlags.code}">${recordFlags.code_text}</option>
                </c:otherwise>
            </c:choose>
        </c:forEach>
    </c:when>

    <c:when test="${templateName == 'option_list_pub_icCardMainType'}">
        <!-- 票卡主类型（回收入库） -->
        <option value="">=请选择=</option>
        <c:forEach items="${icCardMainType}" var="icCardMainType">
            <c:choose>
                <c:when test="${icCardMainType.code_text == ''}">
                    <option value="${icCardMainType.code}">${icCardMainType.code}</option>
                </c:when>
                <c:otherwise>
                    <option value="${icCardMainType.code}">${icCardMainType.code_text}</option>
                </c:otherwise>
            </c:choose>
        </c:forEach>
    </c:when>

    <c:when test="${templateName == 'option_list_pub_icCardMainTypeHidden'}">
        <!-- 票卡主类型 -->
        <input type="hidden" id="${pVarName}" name="${pVarName}" value="${cardMainForReturn}" />
    </c:when>

    <c:when test="${templateName == 'option_list_pub_icCodStorageLine'}">
        <!-- 票库线路（回收线路） -->
        <option value="">=请选择=</option>
        <c:forEach items="${icCodStorageLine}" var="icCodStorageLine">
            <c:choose>
                <c:when test="${icCodStorageLine.code_text == ''}">
                    <option value="${icCodStorageLine.code}">${icCodStorageLine.code}</option>
                </c:when>
                <c:otherwise>
                    <option value="${icCodStorageLine.code}">${icCodStorageLine.code_text}</option>
                </c:otherwise>
            </c:choose>
        </c:forEach>
    </c:when>

    <c:when test="${templateName == 'option_list_pub_icCodStorageLineHidden'}">
        <!-- 回收线路 -->
        <input type="hidden" id="${pVarName}" name="${pVarName}" value="${icCodStorageLine}" />
    </c:when>

    <c:when test="${templateName == 'option_list_pub_inReason'}">
        <!-- 入库原因（回收入库） -->
        <option value="">=请选择=</option>
        <c:forEach items="${inReason}" var="inReason">
            <c:choose>
                <c:when test="${inReason.code_text == ''}">
                    <option value="${inReason.code}">${inReason.code}</option>
                </c:when>
                <c:otherwise>
                    <option value="${inReason.code}">${inReason.code_text}</option>
                </c:otherwise>
            </c:choose>
        </c:forEach>
    </c:when>
    <c:when test="${templateName == 'option_list_pub_diffReason'}">
        <!-- 出入差额原因 -->
        <option value="">=请选择=</option>
        <c:forEach items="${diffReasons}" var="diffReason">
            <c:choose>
                <c:when test="${diffReason.code_text == ''}">
                    <option value="${diffReason.code}">${diffReason.code}</option>
                </c:when>
                <c:otherwise>
                    <option value="${diffReason.code}">${diffReason.code_text}</option>
                </c:otherwise>
            </c:choose>
        </c:forEach>
    </c:when>
    <c:when test="${templateName == 'option_list_pub_uselessCardType'}">
        <!-- 废票遗失票类型 -->
        <!--<option value="">=请选择=</option>-->
        <c:forEach items="${uselessCardTypes}" var="uselessCardType">
            <c:choose>
                <c:when test="${uselessCardType.code_text == ''}">
                    <option value="${uselessCardType.code}">${uselessCardType.code}</option>
                </c:when>
                <c:otherwise>
                    <option value="${uselessCardType.code}">${uselessCardType.code_text}</option>
                </c:otherwise>
            </c:choose>
        </c:forEach>
    </c:when>
    <c:when test="${templateName == 'option_list_pub_devCodeES'}">
        <!-- ES机器号    -->
        <option value="">任意</option>
        <c:forEach items="${devCodeESs}" var="devCodeES">
            <c:choose>
                <c:when test="${devCodeES.code_text == ''}">
                    <option value="${devCodeES.code}">${devCodeES.code}</option>
                </c:when>
                <c:otherwise>
                    <option value="${devCodeES.code}">${devCodeES.code_text}</option>
                </c:otherwise>
            </c:choose>
        </c:forEach>
    </c:when>
    <c:when test="${templateName == 'option_list_pub_bill_name'}">
        <!-- 单据名称   -->
        <option value="">请选择</option>
        <c:forEach items="${billNames}" var="billNames">
            <c:choose>
                <c:when test="${billNames.code_text == ''}">
                    <option value="${billNames.code}">${billNames.code}</option>
                </c:when>
                <c:otherwise>
                    <option value="${billNames.code}">${billNames.code_text}</option>
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
        <c:set var="save1" scope="request" value="0"/>
        <c:set var="cancel" scope="request" value="0"/>
        <c:set var="updateId" scope="request" value="0"/>
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
        <c:set var="check1" scope="request" value="0"/>
        <c:set var="next" scope="request" value="0"/>
        <c:set var="nextEnd" scope="request" value="0"/>
        <c:set var="back" scope="request" value="0"/>
        <c:set var="backEnd" scope="request" value="0"/>

        <c:set var="clickMethodVisable" scope="request" value=""/>
        <c:set var="addClickMethod" scope="request" value=""/>
        <c:set var="addAfterClickModifyMethod" scope="request" value=""/>
        <c:set var="addAfterClickSaveMethod" scope="request" value=""/>
        <c:set var="addClickMethod1" scope="request" value=""/>
        <c:set var="addClickMethod2" scope="request" value=""/>
        <c:set var="addClickMethod3" scope="request" value=""/>
        <c:set var="addQueryMethod" scope="request" value="''"/>
        <c:set var="addAfterMethod" scope="request" value=""/>
        <c:set var="addBeforeMethod" scope="request" value=""/>
        <c:set var="exportBillBefore" scope="request" value=""/>
        <c:set var="exportBillAfter" scope="request" value=""/>

    </c:when>


    <c:when test="${templateName == 'op_button_list_all'}">

        <table width="95%" align="center">
            <tr>
                <td>


                    <c:if test="${query == 1}"><input type="button" id="btQuery" name="query"  value="查询" class="buttonStyle" onclick="${addClickMethod};${clickMethod};" /></c:if>
                    <c:if test="${add == 1}"><input type="button" id="btAdd" name="add" disabled="true" value="增加" class="buttonStyle" onclick="${addBeforeMethod};${clickMethod};${addAfterMethod}" /></c:if>
                    <c:if test="${del == 1}"><input type="button" id="btDelete" name="del" disabled="true" value="删除" class="buttonStyle" onclick="${addQueryMethod};${clickMethod}" /></c:if>
                    <c:if test="${modify == 1}"><input type="button" id="btModify" name="modify" disabled="true" value="修改" class="buttonStyle" onclick="${clickMethod};${addAfterClickModifyMethod}" /></c:if>
                    <c:if test="${save == 1}"><input type="button" id="btSave" name="save" disabled="true" value="保存" class="buttonStyle" onclick="${addQueryMethod};${addClickMethod};${clickMethod}" /></c:if>
                    <c:if test="${save1 == 1}"><input type="button" id="btSave" name="save" disabled="true" value="保存" class="buttonStyle" onclick="${addQueryMethod};${addClickMethod};${clickMethod};${addAfterClickSaveMethod}" /></c:if>
                    <c:if test="${cancle == 1}"><input type="button" id="btCancle" name="cancle" disabled="true" value="取消" class="buttonStyle" onclick="${clickMethod}" /></c:if>
                    <c:if test="${clone == 1}"><input type="button" id="btClone" name="clone" disabled="true" value="克隆" class="buttonStyle" onclick="${addQueryMethod};${clickMethod}" /></c:if>
                    <c:if test="${submit == 1}"><input type="button" id="btSubmit" name="submit1" disabled="true" value="提交" class="buttonStyle" onclick="${addQueryMethod};${clickMethod}" /></c:if>
                    <c:if test="${import_1 == 1}"><input type="button" id="btImport" name="import" disabled="true" value="导入" class="buttonStyle" onclick="${addClickMethod1}" /></c:if>
                    <c:if test="${updateId == 1}"><input type="button" id="btUpdateId" name="btUpdateId" disabled="true" value="拆分车票ID" class="buttonStyle" onclick="${clickMethodVisable}" /></c:if>

                    <c:if test="${download == 1}"><input type="button" id="btDownload" name="download"  value="下发" class="buttonStyle" onclick="${clickMethod}" /></c:if>
                    <c:if test="${statistic == 1}"><input type="button" id="btStatistic" name="statistic"  value="统计" class="buttonStyle" onclick="${clickMethod}" /></c:if>

                    <c:if test="${btRefundOk == 1}"><input type="button" id="btRefundOk" name="btRefundOk"  value="确认退款" class="buttonStyle" onclick="submitConfirm1All('${addClickMethod}', '${clickMethod}')" /></c:if>
                    <c:if test="${btRefundNo == 1}"><input type="button" id="btRefundNo" name="btRefundNo"  value="拒绝退款" class="buttonStyle" onclick="submitConfirm2All('${addClickMethod1}', '${clickMethod}')" /></c:if>
                    <c:if test="${btRefundMd == 1}"><input type="button" id="btRefundMd" name="btRefundMd"  value="确认修改" class="buttonStyle" onclick="submitConfirm3All('${addClickMethod2}', '${clickMethod}')" /></c:if>
                    <c:if test="${btRefundCk == 1}"><input type="button" id="btRefundCk" name="btRefundCk"  value="确认审核" class="buttonStyle" onclick="submitConfirm4All('${addClickMethod3}', '${clickMethod}')" /></c:if>
                    <c:if test="${check1==1}"><input type="button" id="btCheck1" name="check1"  value="盘点" class="buttonStyle" onclick="${clickMethod};${addClickMethod}" /></c:if>
                    <c:if test="${audit == 1}"><input type="button" id="btAudit" name="audit"  value="审核" class="buttonStyle" onclick="${addClickMethod};${clickMethod};" /></c:if>
                    <%--modyfy by zhongzq 20180620 --%>
                    <%--<c:if test="${exportBill == 1}"><input type="button" id="btExportBill" name="exportBill" disabled="true" value="单据导出" class="buttonStyle" onclick="${exportBillBefore};${clickMethod};${exportBillAfter}" /></c:if>--%>
                    <c:if test="${exportBill == 1}"><input type="button" id="btExportBill" name="exportBill" disabled="true" value="单据导出" class="buttonStyle" onclick="${exportBillBefore};${exportBillAfter}" /></c:if>
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
