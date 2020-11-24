<%-- 
    Document   : card_attribute
    Created on : 2017-6-13
    Author     : luck
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>票卡属性</title>


        <script language="javascript" type="text/javascript" charset="utf-8" src="js/common_form.js"></script>
        <script language="javascript" type="text/javascript" charset="utf-8" src="js/Validator.js"></script>
        <script language="JavaScript" src="js/CalendarPop.js"></script>
        <link rel="stylesheet" type="text/css" href="css/frame/simple.css" title="Style"/>
    </head>

    <script  type="text/javascript">
        function show(resluts, jspName, moduleID, width, height, id) {
            var obj = new Object();
            var d_rechargeDeviceType = document.getElementById('d_rechargeDeviceType').value;
            var d_availableDeviceType = document.getElementById('d_availableDeviceType').value;
            var d_saleEquiType = document.getElementById('d_saleEquiType').value;
            obj.value = resluts;

            var path = jspName + "?ModuleID=" + moduleID + "&d_rechargeDeviceType=" + d_rechargeDeviceType + "&d_availableDeviceType=" + d_availableDeviceType + "&d_saleEquiType=" + d_saleEquiType;
            //	alert(path);

            var rt = window.showModalDialog(path, obj, 'dialogWidth:' + width + 'px;dialogHeight:' + height + 'px;center:yes;resizable:no;status:no;scroll:yes');
            //	alert(rt);
            if (rt != null && rt != 'undefined')
                document.getElementById(id).value = rt;

        }

    </script>

    <c:choose>
        <c:when test="${version.version_no == '0000000000'}">
            <body onload="initDocument('queryOp', 'detail');
                    initDocument('detailOp', 'detail');
                    setInvisable('detailOp', 'clone');
                    setPrimaryKeys('detailOp', 'd_cardMainID#d_cardSubID');
                    setControlsDefaultValue('queryOp');
                    setListViewDefaultValue('detailOp', 'clearStart');
                    setQueryControlsDefaultValue('queryOp', 'detailOp');
                    setPageControl('detailOp');
                    setTableRowBackgroundBlock('DataTable')">
            </c:when>
            <c:otherwise>
            <body onload="initDocument('queryOp', 'detail');
                    initDocument('detailOp', 'detail');
                    setInvisable('detailOp', 'add;modify;del;submit1');
                    setPrimaryKeys('detailOp', 'd_cardMainID#d_cardSubID');
                    setControlsDefaultValue('queryOp');
                    setListViewDefaultValue('detailOp', 'clearStart');
                    setQueryControlsDefaultValue('queryOp', 'detailOp');
                    setPageControl('detailOp');
                    setTableRowBackgroundBlock('DataTable')">
            </c:otherwise>
        </c:choose>
        <!--<body >-->
        <table  class="table_title">
            <tr align="center" class="trTitle">
                <td colspan="4">票卡属性（
                    ${version.record_flag_name}：${version.version_no}
                    ）
                </td>
            </tr>
        </table>

        <c:set var="pTitleName" scope="request" value="版本信息"/>
        <c:set var="pTitleWidth" scope="request" value="50"/>
        <c:import  url="/jsp/common/common_template.jsp?template_name=common_table_title" />
        <table class="table_edit" >
            <tr class="table_edit_tr">
                <td class="table_edit_tr_td_label">生效起始时间:</td>
                <td class="table_edit_tr_td_input">
                    <input type="text" name="startDate" id="startDate" size="20" value="${version.begin_time}" readonly="true">
                    </input>
                </td>
                <td class="table_edit_tr_td_label">生效截至时间:</td>
                <td class="table_edit_tr_td_input">
                    <input type="text" name="endDate" id="endDate" size="20" value="${version.end_time}" readonly="true">
                    </input>
                </td>
            </tr>
        </table>


        <!-- 表头 通用模板 -->

        <c:set var="pTitleName" scope="request" value="查询"/>
        <c:set var="pTitleWidth" scope="request" value="50"/>
        <c:import  url="/jsp/common/common_template.jsp?template_name=common_table_title" />

        <form method="post" name="queryOp" id="queryOp" action="CardAttribute">
            <!-- 页面用到的变量 通用模板 -->

            <c:set var="divideShow" scope="request" value="1"/>
            <c:import  url="/jsp/common/common_template_web_variable.jsp?template_name=common_web_variable" />

            <table  class="table_edit">
                <tr class="table_edit_tr">
                    <td class="table_edit_tr_td_label">票卡主类型:</td>
                    <td class="table_edit_tr_td_input">
                        <select id="q_cardMainID" name="q_cardMainID" onchange="setSelectValues('queryOp', 'q_cardMainID', 'q_cardSubID', 'commonVariable');" >
                            <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_cardMain" />
                        </select>
                    </td>
                    <td class="table_edit_tr_td_label">票卡子类型:</td>
                    <td class="table_edit_tr_td_input">
                        <select id="q_cardSubID" name="q_cardSubID" >
                            <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_cardSub_none" />
                        </select>
                        <c:set var="pVarName" scope="request" value="commonVariable"/>
                        <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_cardSub" />
                    </td>    
                    <td class="table_edit_tr_td_label" rowspan="2">

                        <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all_init" />
                        <c:set var="query" scope="request" value="1"/>
                        <c:set var="addClickMethod" scope="request" value="setControlNames('queryOp','q_cardMainID#q_cardSubID');setLineCardNames('queryOp','q_cardMainID','q_cardSubID','commonVariable','','','');"/>
                        <c:set var="clickMethod" scope="request" value="btnClick('queryOp','clearStart','detail');"/>
                        <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all" />
                    </td>
                </tr>
            </table>
            <c:import url="/jsp/common/common_template.jsp?template_name=version_eval" />

        </form>
            
        <!-- 状态栏 通用模板 -->

        <c:import url="/jsp/common/common_template.jsp?template_name=common_status_table" />    
            
        <!-- 表头 通用模板 -->
        <c:set var="pTitleName" scope="request" value="列表"/>
        <c:set var="pTitleWidth" scope="request" value="50"/>
        <c:import url="/jsp/common/common_template.jsp?template_name=common_table_title" />
        <div id="clearStartBlock" class="divForTableBlock">
            <div id="clearStartHead" class="divForTableBlockHead" style="width: 330%;">
                <table class="table_list_block" id="DataTableHead" >

                    <!--说明：列的序号从0开始 isDigit：false 表示按字符串排序 ：true表示按数值排序 sortedby：asc表示按升序 dec表示按降序 -->
                    <tr  class="table_list_tr_head_block" id="ignore">
                        <td   class="table_list_tr_col_head_block">
                            <input type="checkbox" name="rectNoAll" id="rectNoAll"  onclick="selectAllRecord('detailOp', 'rectNoAll', 'rectNo', 'clearStart', 0);"/>
                        </td>	
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="1"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >票卡主类型</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="2"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width:100px">票卡子类型</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="3"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >钱包值类型</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=true  index="4"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >车票余额/次上限</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="5"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >是否允许透支</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=true index="6"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >透支额度</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="7"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >是否允许充值</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=true index="8"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >每次充值上限</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="9"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >更新时收费方式</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="10" sortedby="asc"  onclick="sortForTableBlock('clearStart');">付费区非本站更新检查标志</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="11" sortedby="asc"  onclick="sortForTableBlock('clearStart');">付费区非本日更新检查标志</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="12" sortedby="asc"  onclick="sortForTableBlock('clearStart');">是否允许退款</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=true index="13" sortedby="asc"  onclick="sortForTableBlock('clearStart');">退款时使用次数限制</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=true index="14"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >日乘坐次数上限</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=true index="15"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >月乘坐次数上限</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=true index="16"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >有效期</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="17"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >是否允许延期</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=true  index="18"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >可延长时间</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=true index="19"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >押金</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=true index="20"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >售价</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=true index="21"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >发售手续费</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="22"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >使用前是否需激活</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="23" sortedby="asc"  onclick="sortForTableBlock('clearStart');">是否检查黑名单</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="24" sortedby="asc"  onclick="sortForTableBlock('clearStart');">是否检查余额/余次</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="25" sortedby="asc"  onclick="sortForTableBlock('clearStart');" style="width:150px">逻辑及物理有效期检查</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="26" sortedby="asc"  onclick="sortForTableBlock('clearStart');" style="width:120px">进出站次序检查</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false  index="27"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >是否检查超时</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="28"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >是否检查超乘</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="29"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >是否限制进出站点</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="30"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >是否只允许本站进出</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="31"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width:100px">充值设备</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="32" sortedby="asc"  onclick="sortForTableBlock('clearStart');" style="width:300px">哪种设备可使用</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="33" sortedby="asc"  onclick="sortForTableBlock('clearStart');">本站发售控制标记</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=true index="34" sortedby="asc"  onclick="sortForTableBlock('clearStart');">退款余额上限</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=true index="35" sortedby="asc"  onclick="sortForTableBlock('clearStart');">退款手续费</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="36" sortedby="asc"  onclick="sortForTableBlock('clearStart');" style="width:100px">发售设备</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="37" sortedby="asc"  onclick="sortForTableBlock('clearStart');" style="width:100px">是否允许退押金（工本费）</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="38" sortedby="asc"  onclick="sortForTableBlock('clearStart');" style="width:100px">是否检查票卡发行后有效天数</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="39" sortedby="asc"  onclick="sortForTableBlock('clearStart');" style="width:100px">票卡发行后有效天数（分）</td>
                    </tr>


                </table>

            </div>
            <!-- <DIV id="clearStart" align="center" style="position:relative; left:20; width:95%; height:30%;min-height: 200px;max-height: 250px; layer-background-color:#99CC99; border:thin outset #ffffff; z-index:1; overflow: scroll;">-->
            <div id="clearStart"  class="divForTableBlockData" style="width: 330%">
                <table class="table_list_block" id="DataTable" style="margin-bottom: 20px">

                    <c:forEach items="${ResultSet}" var="rs">
                        <!--class="listTableData" -->
                        <tr class="table_list_tr_data"  onMouseOver="overResultRow('detailOp', this);" 
                            onMouseOut="outResultRow('detailOp', this);" 
                            onclick="setSelectValuesByRow('detailOp', 'd_cardSubID', 'commonVariable');clickResultRow('detailOp', this, 'detail');setPageControl('detailOp');" 
                            id="${rs.card_main_id}#${rs.card_sub_id}#${rs.version_no}#${rs.record_flag}">

                            <td id="rectNo1" class="table_list_tr_col_data_block">
                                <input type="checkbox" name="rectNo" onclick="unSelectAllRecord('detailOp', 'rectNoAll', 'rectNo');"
                                       value="${rs.card_main_id}#${rs.card_sub_id}">

                                </input>
                            </td>
                            <td  id="cardMainID" class="table_list_tr_col_data_block">
                                ${rs.card_main_id_text}
                            </td>
                            <td  id="cardSubID" class="table_list_tr_col_data_block" style="width:100px">
                                ${rs.card_sub_id_text}
                            </td>
                            <td  id="purseValueType" class="table_list_tr_col_data_block">
                                ${rs.purse_value_type_text}
                            </td>
                            <td  id="maxStoreVal" class="table_list_tr_col_data_block">
                                ${rs.max_store_val}
                            </td>
                            <td  id="isOverdraw" class="table_list_tr_col_data_block">
                                ${rs.is_overdraw_text}
                            </td>
                            <td  id="overdrawLimit" class="table_list_tr_col_data_block">
                                ${rs.overdraw_limit}
                            </td>
                            <td  id="isRecharge" class="table_list_tr_col_data_block">
                                ${rs.is_recharge_text}
                            </td>
                            <td  id="maxRecharge" class="table_list_tr_col_data_block">
                                ${rs.max_recharge_val}
                            </td>
                            <td  id="updateFeeType" class="table_list_tr_col_data_block">
                                ${rs.update_fee_type_text}
                            </td>
                            <td  id="isChkCurStation" class="table_list_tr_col_data_block">
                                ${rs.is_chk_cur_station_text}
                            </td>
                            <td  id="isChkCurDate" class="table_list_tr_col_data_block">
                                ${rs.is_chk_cur_date_text}
                            </td>
                            <td  id="isRefund" class="table_list_tr_col_data_block">
                                ${rs.is_refund_text}
                            </td>
                            <td  id="refundLimit" class="table_list_tr_col_data_block" >
                                ${rs.refund_limit}
                            </td>
                            <td  id="maxTripCountDaily" class="table_list_tr_col_data_block">
                                ${rs.daily_trip_cnt_lmt}
                            </td>
                            <td  id="monthTripCntLmt" class="table_list_tr_col_data_block">
                                ${rs.month_trip_cnt_lmt}
                            </td>
                            <td  id="expiredDate" class="table_list_tr_col_data_block">
                                ${rs.exp_date}
                            </td>
                            <td  id="isAllowPostpone" class="table_list_tr_col_data_block">
                                ${rs.is_allow_postpone_text}
                            </td>
                            <td  id="postDays" class="table_list_tr_col_data_block">
                                ${rs.extend_expire_day}
                            </td>
                            <td  id="deposit" class="table_list_tr_col_data_block">
                                ${rs.deposit_amnt}
                            </td>
                            <td  id="cardCost" class="table_list_tr_col_data_block">
                                ${rs.card_cost}
                            </td>
                            <td  id="auxiliaryExpenses" class="table_list_tr_col_data_block">
                                ${rs.auxiliary_expenses}
                            </td>
                            <td  id="isNeedActivat" class="table_list_tr_col_data_block">
                                ${rs.is_activation_text}
                            </td>
                            <td  id="isCheckBlackList" class="table_list_tr_col_data_block">
                                ${rs.is_chk_blk_text}
                            </td>
                            <td  id="isCheckBalance" class="table_list_tr_col_data_block">
                                ${rs.is_chk_remain_text}
                            </td>
                            <td  id="isChkValidPhyLogic" class="table_list_tr_col_data_block" style="width:150px">
                                ${rs.is_chk_valid_phy_logic_text}
                            </td>
                            <td  id="isCheckInOutSequence" class="table_list_tr_col_data_block" style="width:120px">
                                ${rs.is_chk_sequ_text}
                            </td>
                            <td  id="isCheckTimeout" class="table_list_tr_col_data_block">
                                ${rs.is_chk_exce_time_text}
                            </td>
                            <td  id="isCheckTripOut" class="table_list_tr_col_data_block">
                                ${rs.is_chk_exce_st_text}
                            </td>
                            <td  id="isLimitEntryExit" class="table_list_tr_col_data_block">
                                ${rs.is_limit_entry_exit_text}
                            </td>
                            <td  id="isLimitStation" class="table_list_tr_col_data_block">
                                ${rs.is_limit_station_text}
                            </td>
                            <td  id="rechargeDeviceType" class="table_list_tr_col_data_block" style="width:100px">
                                ${rs.add_val_equi_type_text}
                            </td>
                            <td  id="availableDeviceType" class="table_list_tr_col_data_block" style="width:300px">
                                ${rs.use_on_equi_text}
                            </td>
                            <td  id="isLimitSaleEntry" class="table_list_tr_col_data_block">
                                ${rs.is_limit_sale_entry_text}
                            </td>
                            <td  id="refundLimitBalance" class="table_list_tr_col_data_block">
                                ${rs.refund_limit_balance}
                            </td>
                            <td  id="refundAuxiliaryExpense" class="table_list_tr_col_data_block">
                                ${rs.refund_auxiliary_expense}
                            </td>
                            <td  id="saleEquiType" class="table_list_tr_col_data_block" style="width:100px">
                                ${rs.sale_equi_type_text}
                            </td>
                            <td  id="isDepositRefund" class="table_list_tr_col_data_block" style="width:100px">
                                ${rs.is_deposit_refund_text}
                            </td>
                             <td  id="isChkValidDay" class="table_list_tr_col_data_block" style="width:100px">
                                ${rs.is_chk_valid_day_text}
                            </td>
                             <td  id="validDay" class="table_list_tr_col_data_block" style="width:100px">
                                ${rs.valid_day}
                            </td>

                        </tr>

                    </c:forEach>

                </table>
            </div>
        </div>

        <!-- 表头 通用模板 -->
        <c:set var="pTitleName" scope="request" value="明细"/>
        <c:set var="pTitleWidth" scope="request" value="50"/>
        <c:import url="/jsp/common/common_template.jsp?template_name=common_table_title" />

        <FORM method="post" name="detailOp" id="detailOp" action="CardAttribute" >

            <c:set var="divideShow" scope="request" value="1"/>
            <c:import  url="/jsp/common/common_template_web_variable.jsp?template_name=common_web_variable" />

            <!--导出全部参数 -->
            <input type="hidden" name="expAllFields" id="d_expAllFields" value="CARD_MAIN_ID_TEXT,CARD_SUB_ID_TEXT,PURSE_VALUE_TYPE_TEXT,MAX_STORE_VAL,IS_OVERDRAW_TEXT,OVERDRAW_LIMIT,IS_RECHARGE_TEXT,MAX_RECHARGE_VAL,UPDATE_FEE_TYPE_TEXT,IS_CHK_CUR_STATION_TEXT,IS_CHK_CUR_DATE_TEXT,IS_REFUND_TEXT,REFUND_LIMIT,DAILY_TRIP_CNT_LMT,MONTH_TRIP_CNT_LMT,EXP_DATE,IS_ALLOW_POSTPONE_TEXT,EXTEND_EXPIRE_DAY,DEPOSIT_AMNT,CARD_COST,AUXILIARY_EXPENSES,IS_ACTIVATION_TEXT,IS_CHK_BLK_TEXT,IS_CHK_REMAIN_TEXT,IS_CHK_VALID_PHY_LOGIC_TEXT,IS_CHK_SEQU_TEXT,IS_CHK_EXCE_TIME_TEXT,IS_CHK_EXCE_ST_TEXT,IS_LIMIT_ENTRY_EXIT_TEXT,IS_LIMIT_STATION_TEXT,ADD_VAL_EQUI_TYPE_TEXT,USE_ON_EQUI_TEXT,IS_LIMIT_SALE_ENTRY_TEXT,REFUND_LIMIT_BALANCE,REFUND_AUXILIARY_EXPENSE,SALE_EQUI_TYPE_TEXT,IS_DEPOSIT_REFUND_TEXT,IS_CHK_VALID_DAY_TEXT,VALID_DAY" />
            <input type="hidden" name="expFileName" id="d_expFileName" value="票卡子类型.xlsx" />
            <input type="hidden" name="divId" id="d_divId" value="clearStartHead" />
            <input type="hidden" name="methodUrl" id="d_methodUrl" value="/CardAttributeExportAll" />

            <!--<DIV id="detail" align="center" class="divForTableData" style="position:relative; left:20; width:95%; height:10; layer-background-color:#99CC99; border:thin outset #ffffff; z-index:1;">-->
            <DIV id="detail"  class="divForTableDataDetail" >
                <table  class="table_edit_detail">
                    <tr class="table_edit_tr">
                        <td class="table_edit_tr_td_label">票卡主类型: </td>
                        <td class="table_edit_tr_td_input">
                            <select  name="d_cardMainID"  id="d_cardMainID" onChange="setSelectValues('detailOp', 'd_cardMainID', 'd_cardSubID', 'commonVariable');" require="true" dataType="LimitB" min="1" msg="没有选择票卡主类型">
                                <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_cardMain" />
                            </select>	
                        </td>
                        <td class="table_edit_tr_td_label">票卡子类型:</td>
                        <td class="table_edit_tr_td_input">
                            <select id="d_cardSubID" name="d_cardSubID" require="true" dataType="LimitB" min="1" msg="没有选择票卡子类型">
                                <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_cardSub_none" />

                            </select>
                            <c:set var="pVarName" scope="request" value="commonVariable"/>
                            <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_cardSub" />
                        </td>
                        <td class="table_edit_tr_td_label">钱包值类型: </td>
                        <td class="table_edit_tr_td_input">
                            <select  name="d_purseValueType"  id="d_purseValueType"  require="true" dataType="LimitB" min="1" msg="没有选择钱包值类型">
                                <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_purseValueType" />
                            </select>	
                        </td>
                    </tr>

                    <tr class="table_edit_tr">
                        <td class="table_edit_tr_td_label">车票余额/次上限(单位:分/次):</td>
                        <td class="table_edit_tr_td_input">
                            <input type="text" name="d_maxStoreVal" id="d_maxStoreVal" size="10" maxLength="6" require="true" dataType="integer" msg="车票余额/次上限应为正整数！" value="0"/>
                        </td>
                        <td class="table_edit_tr_td_label">是否允许透支:</td>
                        <td class="table_edit_tr_td_input">
                            <input type="radio" name="d_isOverdraw" id="d_isOverdraw" value="1"/>是
                            <input type="radio" name="d_isOverdraw" id="d_isOverdraw" value="0" checked="true"/>否
                        </td>
                        <td class="table_edit_tr_td_label">透支额度(单位:分):</td>
                        <td class="table_edit_tr_td_input">
                            <input type="text" name="d_overdrawLimit" id="d_overdrawLimit" size="10" maxLength="6" require="true" dataType="integer" msg="透支额度上限应为正整数！" value="0"/>
                        </td>
                    </tr>

                    <tr class="table_edit_tr">
                        <td class="table_edit_tr_td_label">是否允许充值:</td>
                        <td class="table_edit_tr_td_input">
                            <input type="radio" name="d_isRecharge" id="d_isRecharge" value="1"/>是
                            <input type="radio" name="d_isRecharge" id="d_isRecharge" value="0" checked="true"/>否
                        </td>
                        <td class="table_edit_tr_td_label">每次充值上限:</td>
                        <td class="table_edit_tr_td_input">
                            <input type="text" name="d_maxRecharge" id="d_maxRecharge" size="10" maxlength="6" require="true" dataType="integer" msg="每次充值上限应为正整数" value="0"/>
                        </td>
                        <td class="table_edit_tr_td_label">更新时收费方式:</td>
                        <td class="table_edit_tr_td_input">
                            <select  name="d_updateFeeType"  id="d_updateFeeType"  require="true" dataType="LimitB" min="1" msg="没有选择更新时收费方式">
                                <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_feeType" />
                            </select>	
                        </td>
                    </tr>
                    <tr class="table_edit_tr">
                        <td class="table_edit_tr_td_label">付费区非本站更新检查标志:</td>
                        <td class="table_edit_tr_td_input">
                            <input type="radio" name="d_isChkCurStation" id="d_isChkCurStation" value="1"/>是
                            <input type="radio" name="d_isChkCurStation" id="d_isChkCurStation" value="0" checked="true"/>否
                        </td>
                        <td class="table_edit_tr_td_label">付费区非本日更新检查标志:</td>
                        <td class="table_edit_tr_td_input">
                            <input type="radio" name="d_isChkCurDate" id="d_isChkCurDate" value="1"/>是
                            <input type="radio" name="d_isChkCurDate" id="d_isChkCurDate" value="0" checked="true"/>否
                        </td>
                        <td class="table_edit_tr_td_label">是否允许退款:</td>
                        <td class="table_edit_tr_td_input">
                            <input type="radio" name="d_isRefund" id="d_isRefund" value="1"/>是
                            <input type="radio" name="d_isRefund" id="d_isRefund" value="0" checked="true"/>否
                        </td>
                    </tr>
                    <tr class="table_edit_tr">
                        <td class="table_edit_tr_td_label">退款时使用次数限制:</td>
                        <td class="table_edit_tr_td_input">
                            <input type="text" name="d_refundLimit" id="d_refundLimit"  size="10" maxlength="3" require="true" dataType="integer" msg="退款时次数限制应为正整数" value="000"/>
                        </td>
                        <td class="table_edit_tr_td_label">日乘坐次数上限:</td>
                        <td class="table_edit_tr_td_input">
                            <input type="text" name="d_maxTripCountDaily" id="d_maxTripCountDaily" size="10" maxlength="2" require="true" dataType="integer" msg="日乘坐次数上限应为正整数" value="00"/>
                        </td>
                        <td class="table_edit_tr_td_label">月乘坐次数上限:</td>
                        <td class="table_edit_tr_td_input">
                            <input type="text" name="d_monthTripCntLmt" id="d_monthTripCntLmt" size="10" maxlength="4" require="true" dataType="integer" msg="月乘坐次数上限应为正整数" value="0000"/>
                        </td>
                    </tr>
                    <tr class="table_edit_tr">
                        <td class="table_edit_tr_td_label">有效期(单位:分钟):</td>
                        <td class="table_edit_tr_td_input">
                            <input type="text" name="d_expiredDate" id="d_expiredDate" size="10" maxlength="7" require="true" dataType="integer" msg="有效期应为正整数" value="0"/>	
                        </td>
                        <td class="table_edit_tr_td_label">是否允许延期:</td>
                        <td class="table_edit_tr_td_input">
                            <input type="radio" name="d_isAllowPostpone" id="d_isAllowPostpone" value="1"/>是
                            <input type="radio" name="d_isAllowPostpone" id="d_isAllowPostpone" value="0" checked="true"/>否
                        </td>
                        <td class="table_edit_tr_td_label">可延长时间(单位:天):</td>
                        <td class="table_edit_tr_td_input">
                            <input type="text" name="d_postDays" id="d_postDays" size="10" maxlength="4" require="true" dataType="integer" msg="可延长时间应为正整数" value="0"/>
                        </td>
                    </tr>
                    <tr class="table_edit_tr">
                        <td class="table_edit_tr_td_label">押金(单位:分):</td>
                        <td class="table_edit_tr_td_input">
                            <input type="text" name="d_deposit" id="d_deposit" size="10" maxlength="6" require="true" dataType="integer" msg="押金应为正整数" value="0"/>	
                        </td>
                        <td class="table_edit_tr_td_label">售价(单位:分):</td>
                        <td class="table_edit_tr_td_input">
                            <input type="text" name="d_cardCost" id="d_cardCost" size="10" maxlength="6" require="true" dataType="integer" msg="卡成本应为正整数" value="0"/>
                        </td>
                        <td class="table_edit_tr_td_label">发售手续费(单位:分):</td>
                        <td class="table_edit_tr_td_input">
                            <input type="text" name="d_auxiliaryExpenses" id="d_auxiliaryExpenses" size="10" maxlength="6" require="true" dataType="integer" msg="发售手续费应为正整数" value="0"/>
                        </td>
                    </tr>
                    <tr class="table_edit_tr">
                        <td class="table_edit_tr_td_label">使用前是否需激活:</td>
                        <td class="table_edit_tr_td_input">
                            <input type="radio" name="d_isNeedActivat" id="d_isNeedActivat" value="1"/>是
                            <input type="radio" name="d_isNeedActivat" id="d_isNeedActivat" value="0" checked="true"/>否
                        </td>
                        <td class="table_edit_tr_td_label">是否检查黑名单:</td>
                        <td class="table_edit_tr_td_input">
                            <input type="radio" name="d_isCheckBlackList" id="d_isCheckBlackList" value="1"/>是
                            <input type="radio" name="d_isCheckBlackList" id="d_isCheckBlackList" value="0" checked="true"/>否
                        </td>
                        <td class="table_edit_tr_td_label">是否检查余额/余次:</td>
                        <td class="table_edit_tr_td_input">
                            <input type="radio" name="d_isCheckBalance" id="d_isCheckBalance" value="1"/>是
                            <input type="radio" name="d_isCheckBalance" id="d_isCheckBalance" value="0" checked="true"/>否
                        </td>
                    </tr>
                    <tr class="table_edit_tr">
                        <td class="table_edit_tr_td_label">逻辑及物理有效期检查:</td>
                        <td class="table_edit_tr_td_input">
                            <select  name="d_isChkValidPhyLogic"  id="d_isChkValidPhyLogic"  require="true" dataType="LimitB" min="1" msg="没有选择逻辑及物理有效期检查">
                                <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_chkValidPhyLogic" />
                            </select>	
                        </td>
                        <td class="table_edit_tr_td_label">进出站次序检查:</td>
                        <td class="table_edit_tr_td_input">
                            <select  name="d_isCheckInOutSequence"  id="d_isCheckInOutSequence"  require="true" dataType="LimitB" min="1" msg="没有选择进出站次序检查:">
                                <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_checkInOutSequence" />
                            </select>	
                        </td>
                        <td class="table_edit_tr_td_label">是否检查超时:</td>
                        <td class="table_edit_tr_td_input">
                            <input type="radio" name="d_isCheckTimeout" id="d_isCheckTimeout" value="1"/>是
                            <input type="radio" name="d_isCheckTimeout" id="d_isCheckTimeout" value="0" checked="true"/>否
                        </td>
                    </tr>
                    <tr class="table_edit_tr">
                        <td class="table_edit_tr_td_label">是否检查超乘:</td>
                        <td class="table_edit_tr_td_input">
                            <input type="radio" name="d_isCheckTripOut" id="d_isCheckTripOut" value="1" />是
                            <input type="radio" name="d_isCheckTripOut" id="d_isCheckTripOut" value="0" checked="true"/>否
                        </td>
                        <td class="table_edit_tr_td_label">是否限制进出站点:</td>
                        <td class="table_edit_tr_td_input">
                            <input type="radio" name="d_isLimitEntryExit" id="d_isLimitEntryExit" value="1"/>是
                            <input type="radio" name="d_isLimitEntryExit" id="d_isLimitEntryExit" value="0" checked="true"/>否
                        </td>
                        <td class="table_edit_tr_td_label">是否只允许本站进出:</td>
                        <td class="table_edit_tr_td_input">
                            <input type="radio" name="d_isLimitStation" id="d_isLimitStation" value="1"/>是
                            <input type="radio" name="d_isLimitStation" id="d_isLimitStation" value="0" checked="true"/>否
                        </td>
                    </tr>
                    <tr class="table_edit_tr">
                        <td class="table_edit_tr_td_label">充值设备:</td>
                        <td class="table_edit_tr_td_input">	
                            <input type="text" name="d_rechargeDeviceType" id="d_rechargeDeviceType" unselectable="on" readonly="true" onclick="show('detailOp', 'CardAttrDevType?command=openwindow&WindowType=1&ModuleID=${ModuleID}', '0305', 600, 600, 'd_rechargeDeviceType');" />
                        </td>
                        <td class="table_edit_tr_td_label">哪种设备可使用:</td>
                        <td class="table_edit_tr_td_input">
                            <input type="text" name="d_availableDeviceType" id="d_availableDeviceType" unselectable="on" readonly="true" onclick="show('detailOp', 'CardAttrDevType?command=openwindow&WindowType=2&ModuleID=${ModuleID}', '0305', 600, 600, 'd_availableDeviceType');" />
                        </td>
                        <td class="table_edit_tr_td_label">本站发售控制标记:</td>
                        <td class="table_edit_tr_td_input">
                            <input type="radio" name="d_isLimitSaleEntry" id="d_isLimitSaleEntry" value="1"/>不控制
                            <input type="radio" name="d_isLimitSaleEntry" id="d_isLimitSaleEntry" value="0" checked="true"/>控制
                        </td>
                    </tr>
                    <tr class="table_edit_tr">
                        <td class="table_edit_tr_td_label">退款余额上限(单位:分):</td>
                        <td class="table_edit_tr_td_input">
                            <input type="text" name="d_refundLimitBalance" id="d_refundLimitBalance" size="10" maxlength="6" require="true" dataType="integer" msg="退款余额上限应为正整数" value="0"/>
                        </td>
                        <td class="table_edit_tr_td_label">退款手续费(单位:分):</td>
                        <td class="table_edit_tr_td_input">
                            <input type="text" name="d_refundAuxiliaryExpense" id="d_refundAuxiliaryExpense" size="10" maxlength="6" require="true" dataType="integer" msg="退款手续费应为正整数" value="0"/>
                        </td>
                        <td class="table_edit_tr_td_label">发售设备:</td>
                        <td class="table_edit_tr_td_input">
                            <input type="text" name="d_saleEquiType" id="d_saleEquiType" unselectable="on" readonly="true" onclick="show('detailOp', 'CardAttrDevType?command=openwindow&WindowType=3&ModuleID=${ModuleID}', '0305', 600, 600, 'd_saleEquiType');" />
                        </td>
                    </tr>
                    <tr class="table_edit_tr">
                        <td class="table_edit_tr_td_label">是否允许退押金（工本费）:</td>
                        <td class="table_edit_tr_td_input">
                            <input type="radio" name="d_isDepositRefund" id="d_isDepositRefund" value="1"/>是
                            <input type="radio" name="d_isDepositRefund" id="d_isDepositRefund" value="0" checked="true"/>否
                        </td>
                        <td class="table_edit_tr_td_label">是否检查票卡发行后有效天数 :</td>
                        <td class="table_edit_tr_td_input">
                            <input type="radio" name="d_isChkValidDay" id="d_isChkValidDay" value="1"/>是
                            <input type="radio" name="d_isChkValidDay" id="d_isChkValidDay" value="0" checked="true"/>否
                        </td>
                        <td class="table_edit_tr_td_label">票卡发行后有效天数(单位:分钟):</td>
                        <td class="table_edit_tr_td_input">
                            <input type="text" name="d_validDay" id="d_validDay" size="18" maxlength="16" require="true" dataType="integer" msg="有效期应为正整数" />	
                        </td>
                    </tr>
                </table>
            </DIV>

            <c:import url="/jsp/common/common_template.jsp?template_name=version_eval" />




            <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all_init" />
            <c:set var="add" scope="request" value="1"/>
            <c:set var="del" scope="request" value="1"/>
            <c:set var="modify" scope="request" value="1"/>
            <c:set var="save" scope="request" value="1"/>
            <c:set var="cancle" scope="request" value="1"/>
            <c:set var="clone" scope="request" value="1"/>
            <c:set var="submit" scope="request" value="1"/>
            <c:set var="print" scope="request" value="1"/>
            <c:set var="export" scope="request" value="1"/>
            <c:set var="expAll" scope="request" value="1" />
            <c:set var="btNext" scope="request" value="1"/>
            <c:set var="btNextEnd" scope="request" value="1"/>
            <c:set var="btBack" scope="request" value="1"/>
            <c:set var="btBackEnd" scope="request" value="1"/>
            <c:set var="btGoPage" scope="request" value="1"/>


            <c:set var="addQueryMethod" scope="request" value="setLineCardNames('detailOp','q_cardMainID','q_cardSubID','commonVariable','','','');"/>
            <c:set var="addClickMethod" scope="request" value="setUpdatePrimaryKey('detailOp','d_lineID#d_stationID#d_deviceType#d_deviceID#Version');"/>
            <c:set var="clickMethod" scope="request" value="btnClick('detailOp','clearStart','detail','','clearStartHead');"/>
            <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all" />
            <br/>
        </FORM>

        
    </body>
</html>
