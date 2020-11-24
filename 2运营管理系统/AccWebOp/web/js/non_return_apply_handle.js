function isNumeric(strNum)
{
    var newPar = /^[+]?\d+(\.\d+)?$/;
    if (newPar.test(strNum))
        return true;
    else
        return false;
}

function afterClickARow() {
    var frm = document.getElementById('detailReceiptId');
    var recipt = frm.value;
//    alert("recipt" + recipt);
    if (recipt.length > 0) {
        var oldObject = document.getElementById(recipt);
        oldObject.style.background = "#97CBFF";
        setCurrentLine('queryOp', recipt);
        var table = document.getElementById("DataTable1");
        var rows = table.rows.length;
        var realRow = null;
        for (var i = 0; i < rows; i++)
        {
            var rowReciptId = table.rows[i].cells[0].innerText;
            if (rowReciptId.trim() == recipt) {
                realRow = i;
                break;
            }
        }
        if (realRow != null)
            table.rows[realRow].scrollIntoView();
//        alert(realRow);
    }

}
function setDefaultSelect() {
    var targer = document.getElementById('hidden_penatlyReason');
    var selectList = document.getElementById('d_penatlyReason');
    if (selectList != null && selectList != "undefined" && targer.value.length > 0) {
        if (selectList.tagName == "SELECT") {
            if (selectList.type == "select-one") {
                for (j = 0; j < selectList.options.length; j++) {
//                     alert("optionsValue="+selectList.options[j].value+" ||optionsText==" + selectList.options[j].text+"||targerValue==" +targer.value+"||boolean=" + (selectList.options[j].value == targer.value || selectList.options[j].text ==targer.value));
                    if (selectList.options[j].value.trim() == targer.value.trim() || selectList.options[j].text == targer.value.trim()) {
                        selectList.options[j].selected = "true";
                        break;
                    }
                }
            }
        }
    }
}

function selectedRow1(thisObject, formName)
{
    var selectedRow;
    if (selectedRow != null)
    {
        selectedRow.value = "unselected";
        selectedRow.style.backgroundColor = "";
    }
    thisObject.value = "selected";
    thisObject.style.backgroundColor = "#94CBFF";
    selectedRow = thisObject;
    document.getElementById('selectReceiptId').value = selectedRow.childNodes.item(1).innerText;
    document.getElementById('selectLogicalId').value = selectedRow.childNodes.item(3).innerText;
    document.getElementById('selectApplyTime').value = selectedRow.childNodes.item(9).innerText;
    document.getElementById('selectRowFlag').value = "true";
    setCommand(formName, "query");
    setControlNames('queryOp', 'q_businessReceiptId#q_beginDatetime#q_endDatetime#q_handleType#q_queryCondition#q_lineId#q_stationId');
    setLineCardNames('queryOp', 'q_lineId', 'q_stationId', 'commonVariable', '', '', '');
    document.queryOp.submit();
}


function submitConfirm1All(a, b) {
    frm = document.forms['detailOp'];
    checkResult = Validator.Validate(frm, 1);
//    alert("checkResult:"+checkResult);
    if (checkResult) {
        if (confirm('确定退款吗？')) {
            eval(a);
            eval(b);
        }
    }
}
function beforeConfirmRefund()
{
//    var balance, penalty, actual_return_bala;
//    balance = document.getElementById('balance').value;
//    penalty = document.getElementById('penalty').value;
//    setControlNames('query1', 'd_line_id#d_station_id#b_time#oper_type#card_no#e_time#d_type');
//    setLineCardNames('query1', '', '', '', 'd_line_id', 'd_station_id', 'commonVariable');
    setControlNames('queryOp', 'q_businessReceiptId#q_beginDatetime#q_endDatetime#q_handleType#q_queryCondition#q_lineId#q_stationId');
//    beginDatetime = document.getElementById('q_beginDatetime').value;
//    alert(beginDatetime);
    setLineCardNames('queryOp', 'q_lineId', 'q_stationId', 'commonVariable', '', '', '');
    setQueryControlsDefaultValue('queryOp', 'detailOp');
    //balance 和penalty都是不能改的 无需再判断
//    if (!isNumeric(balance))
//    {
//        alert("票卡余额必须为正实数");
//        return false;
//    }
//    if (!isNumeric(penalty))
//    {
//        alert("欠车程费必须为正实数");
//        return false;
//    }
    //    if (confirm('确定退款吗？'))
    //    {
//    setValue1();
//        return true;
//    }
//    else
//        return false;
}
function submitConfirm2All(a, b) {
    frm = document.forms['detailOp'];
    checkResult = Validator.Validate(frm, 1);
//    alert("checkResult:"+checkResult);
    if (checkResult) {
        if (confirm('拒绝退款吗？')) {
            eval(a);
            eval(b);
        }
    }
}
function beforeRefusedRefund()
{
    setControlNames('queryOp', 'q_businessReceiptId#q_beginDatetime#q_endDatetime#q_handleType#q_queryCondition#q_lineId#q_stationId');
    setLineCardNames('queryOp', 'q_lineId', 'q_stationId', 'commonVariable', '', '', '');
    setQueryControlsDefaultValue('queryOp', 'detailOp');
}
function submitConfirm3All(a, b) {
    frm = document.forms['detailOp'];
    checkResult = Validator.Validate(frm, 1);
//    alert("checkResult:"+checkResult);
    var selectHandleFlag = document.getElementById("d_handleFlag").value;
    var disabled = document.getElementById("d_handleFlag").disabled;
//    alert(disabled);
    if (checkResult) {
            if (selectHandleFlag == "2") {
                if (confirm('修改处理状态：车票未处理，历史交易数据会清空，清算系统重新计算票卡的余额后才能进行相关操作，确定修改吗？')) {
                    eval(a);
                    eval(b);
                }
            } else if (selectHandleFlag == "6"){
                if (confirm('修改处理状态：退款申请已撤销，确定修改吗？')) {
                    eval(a);
                    eval(b);
                }

            }else {
            	if(disabled){//操作员点击
                    if (confirm('确认修改吗？')) {
                        eval(a);
                        eval(b);
                    }
            	}else{
                if (confirm('未选择修改处理状态（重置审核状态以及备注），确认修改吗？')) {
                    eval(a);
                    eval(b);
                }
            	}
            }
    }
}

function beforeConfirmModify()
{
    setControlNames('queryOp', 'q_businessReceiptId#q_beginDatetime#q_endDatetime#q_handleType#q_queryCondition#q_lineId#q_stationId');
    setLineCardNames('queryOp', 'q_lineId', 'q_stationId', 'commonVariable', '', '', '');
    setQueryControlsDefaultValue('queryOp', 'detailOp');
}
function submitConfirm4All(a, b) {
    frm = document.forms['detailOp'];
    checkResult = Validator.Validate(frm, 1);
//    alert("checkResult:"+checkResult);
    if (checkResult) {
        if (confirm('确认审核吗？')) {
            eval(a);
            eval(b);
        }
    }
}
function beforeConfirmAudit()
{
    setControlNames('queryOp', 'q_businessReceiptId#q_beginDatetime#q_endDatetime#q_handleType#q_queryCondition#q_lineId#q_stationId');
    setLineCardNames('queryOp', 'q_lineId', 'q_stationId', 'commonVariable', '', '', '');
    setQueryControlsDefaultValue('queryOp', 'detailOp');
}

function ickInit() {
    //一开始先全锁了
    document.getElementById('btRefundOk').disabled = true;
    document.getElementById('btRefundOk').className = "buttonno";
    document.getElementById('btRefundNo').disabled = true;
    document.getElementById('btRefundNo').className = "buttonno";
    document.getElementById('btRefundMd').disabled = true;
    document.getElementById('btRefundMd').className = "buttonno";
    document.getElementById('btRefundCk').disabled = true;
    document.getElementById('btRefundCk').className = "buttonno";

    document.getElementById('d_penaltyFee').disabled = true;
    document.getElementById('d_handleFlag').disabled = true;
    document.getElementById('d_penatlyReason').disabled = true;
    document.getElementById('d_remark').disabled = true;
    var hdlFlag = document.getElementById('hidden_hdlFlag').value;
//    var hdlFlag = document.forms['detailOp'].getElementsByTagName("input")['hdlFlag'];
//    alert("hdlFlag:"+hdlFlag);
    var minReturnDate = document.getElementById('hidden_minReturnDate').value;
//    alert(minReturnDate);
    var isEnoughTime = false;
    if (minReturnDate !== "") {
        var myDate = new Date();
        var month = myDate.getMonth() + 1;
        var day = myDate.getDate();
        if (month < 10)
            month = '0' + month;
        if (day < 10)
            day = '0' + day;
        var curr_date = myDate.getFullYear() + "-" + month + "-" + day;
        if (curr_date > minReturnDate) {
            isEnoughTime = true;//时间不到 谁也别想玩
        }
    }
    if (isEnoughTime && hdlFlag !== "") {
        var right = document.getElementById('hidden_confirmAuditRight').value;
        var auditFlag = document.getElementById('hidden_auditFlag').value;
        //已经申请过标志
        var appliedFlag = document.getElementById('hidden_appliedFlag').value;//相当于hdlFlag 1||3||4 可能存在其他申请
        var hisRecordFlag = document.getElementById('hidden_hisRecordFlag').value;//历史交易记录 如果没有则不能做非即退
//        alert("hisRecordFlag:" + hisRecordFlag);
        if (right === '0') {//对于操作员
            if (hisRecordFlag === '1') {
                if (auditFlag === '0' || auditFlag === null || auditFlag === "") {//没审核 能玩3个按钮 做了审核的啥的不能玩
                    if (appliedFlag === '0' && hdlFlag !== "6") {//没做申请的
                        var blackCardFlag = document.getElementById('blackCardFlag').value;
                        if (blackCardFlag === '1') {//如果是黑名单，则只能拒绝退款
                            document.getElementById('btRefundNo').disabled = false;
                            document.getElementById('btRefundNo').className = "buttonStyle";
                            //拒绝退款仅是填备注
                            document.getElementById('d_remark').disabled = false;

                        } else {//正常卡
                            var returnFlag = document.getElementById('returnFlag');
                            if (returnFlag === null) {//不含退款记录 能玩确认退款和拒绝退款
                                document.getElementById('btRefundOk').disabled = false;
                                document.getElementById('btRefundOk').className = "buttonStyle";
                                document.getElementById('btRefundNo').disabled = false;
                                document.getElementById('btRefundNo').className = "buttonStyle";
                                //确认退款罚金、备注、扣款原因有用 拒绝退款仅是填备注
                                document.getElementById('d_penaltyFee').disabled = false;
                                document.getElementById('d_penatlyReason').disabled = false;
                                document.getElementById('d_remark').disabled = false;

                            } else {
                                document.getElementById('btRefundNo').disabled = false;
                                document.getElementById('btRefundNo').className = "buttonStyle";
                                document.getElementById('d_remark').disabled = false;
                            }
                        }
                    } else if (appliedFlag === '1') {//做了申请的只没做审核只能玩确认修改 做了申请可能是曾有数据
                        if (hdlFlag !== "1" && hdlFlag !== "2") {
                            //操作员确认修改 罚金 备注
                            document.getElementById('btRefundMd').disabled = false;
                            document.getElementById('btRefundMd').className = "buttonStyle";
                            document.getElementById('d_penaltyFee').disabled = false;
                            document.getElementById('d_penatlyReason').disabled = false;
                            document.getElementById('d_remark').disabled = false;
                        } else if (hdlFlag === "2") {//存在其他数据已申请处理 而本身这条数据是未申请的
                            document.getElementById('btRefundNo').disabled = false;
                            document.getElementById('btRefundNo').className = "buttonStyle";
                            document.getElementById('d_remark').disabled = false;
                        }
                    }
                }
            }

        } else if (right === '1') {//对于审核员
            if (auditFlag === '0' || auditFlag === null || auditFlag === "") {//未审核
                if (appliedFlag === '1' || hdlFlag == "6") {//已经申请可以审核
                    document.getElementById('btRefundCk').disabled = false;
                    document.getElementById('btRefundCk').className = "buttonStyle";

                }
            } else if (auditFlag === '1') {//已审核
                if (hdlFlag !== "1" && hdlFlag !== "7") {//只要不是已退款就可以修改
                    document.getElementById('btRefundMd').disabled = false;
                    document.getElementById('btRefundMd').className = "buttonStyle";
                    //审核人员确认修改
                    document.getElementById('d_remark').disabled = false;
                    document.getElementById('d_handleFlag').disabled = false;
                }
            }

        }


    }

}


function signCardInit() {
    //一开始先全锁了
    document.getElementById('btRefundOk').disabled = true;
    document.getElementById('btRefundOk').className = "buttonno";
    document.getElementById('btRefundNo').disabled = true;
    document.getElementById('btRefundNo').className = "buttonno";
    document.getElementById('btRefundMd').disabled = true;
    document.getElementById('btRefundMd').className = "buttonno";
    document.getElementById('btRefundCk').disabled = true;
    document.getElementById('btRefundCk').className = "buttonno";

    document.getElementById('d_penaltyFee').disabled = true;
    document.getElementById('d_handleFlag').disabled = true;
    document.getElementById('d_penatlyReason').disabled = true;
    document.getElementById('d_remark').disabled = true;
    var hdlFlag = document.getElementById('hidden_hdlFlag').value;
//    var hdlFlag = document.forms['detailOp'].getElementsByTagName("input")['hdlFlag'];
//    alert("hdlFlag:"+hdlFlag);
    var minReturnDate = document.getElementById('hidden_minReturnDate').value;
//    alert(minReturnDate);
    var isEnoughTime = false;
    if (minReturnDate !== "") {
        var myDate = new Date();
        var month = myDate.getMonth() + 1;
        var day = myDate.getDate();
        if (month < 10)
            month = '0' + month;
        if (day < 10)
            day = '0' + day;
        var curr_date = myDate.getFullYear() + "-" + month + "-" + day;
        if (curr_date > minReturnDate) {
            isEnoughTime = true;//时间不到 谁也别想玩
        }
    }
    if (isEnoughTime && hdlFlag !== "") {
        var right = document.getElementById('hidden_confirmAuditRight').value;
        var auditFlag = document.getElementById('hidden_auditFlag').value;
        //已经申请过标志
        var appliedFlag = document.getElementById('hidden_appliedFlag').value;//相当于hdlFlag 1||3||4 可能存在其他申请
        var hisRecordFlag = document.getElementById('hidden_hisRecordFlag').value;//历史交易记录 如果没有则不能做非即退
//        alert("hisRecordFlag:" + hisRecordFlag);
        if (right === '0') {//对于操作员
            if (hisRecordFlag === '1') {
                if (auditFlag === '0' || auditFlag === null || auditFlag === "") {//没审核 能玩3个按钮 做了审核的啥的不能玩
                    if (appliedFlag === '0' && hdlFlag !== "6") {//没做申请的
                        var blackCardFlag = document.getElementById('blackCardFlag').value;
                        if (blackCardFlag === '0') {//如果不是是黑名单，则只能拒绝退款
                            document.getElementById('btRefundNo').disabled = false;
                            document.getElementById('btRefundNo').className = "buttonStyle";
                            //拒绝退款仅是填备注
                            document.getElementById('d_remark').disabled = false;

                        } else {//黑名单
                            var returnFlag = document.getElementById('returnFlag');
                            if (returnFlag === null) {//不含退款记录 能玩确认退款和拒绝退款
                                document.getElementById('btRefundOk').disabled = false;
                                document.getElementById('btRefundOk').className = "buttonStyle";
                                document.getElementById('btRefundNo').disabled = false;
                                document.getElementById('btRefundNo').className = "buttonStyle";
                                //确认退款罚金、备注、扣款原因有用 拒绝退款仅是填备注
                                document.getElementById('d_penaltyFee').disabled = false;
                                document.getElementById('d_penatlyReason').disabled = false;
                                document.getElementById('d_remark').disabled = false;

                            } else {
                                document.getElementById('btRefundNo').disabled = false;
                                document.getElementById('btRefundNo').className = "buttonStyle";
                                document.getElementById('d_remark').disabled = false;
                            }
                        }
                    } else if (appliedFlag === '1') {//做了申请的只没做审核只能玩确认修改 做了申请可能是曾有数据
                        if (hdlFlag !== "1" && hdlFlag !== "2") {
                            //操作员确认修改 罚金 备注
                            document.getElementById('btRefundMd').disabled = false;
                            document.getElementById('btRefundMd').className = "buttonStyle";
                            document.getElementById('d_penaltyFee').disabled = false;
                            document.getElementById('d_penatlyReason').disabled = false;
                            document.getElementById('d_remark').disabled = false;
                        } else if (hdlFlag === "2") {//存在其他数据已申请处理 而本身这条数据是未申请的
                            document.getElementById('btRefundNo').disabled = false;
                            document.getElementById('btRefundNo').className = "buttonStyle";
                            document.getElementById('d_remark').disabled = false;
                        }
                    }
                }
            }

        } else if (right === '1') {//对于审核员
            if (auditFlag === '0' || auditFlag === null || auditFlag === "") {//未审核
                if (appliedFlag === '1' || hdlFlag == "6") {//已经申请可以审核
                    document.getElementById('btRefundCk').disabled = false;
                    document.getElementById('btRefundCk').className = "buttonStyle";

                }
            } else if (auditFlag === '1') {//已审核
                if (hdlFlag !== "1" && hdlFlag !== "7") {//只要不是已退款就可以修改
                    document.getElementById('btRefundMd').disabled = false;
                    document.getElementById('btRefundMd').className = "buttonStyle";
                    //审核人员确认修改
                    document.getElementById('d_remark').disabled = false;
                    document.getElementById('d_handleFlag').disabled = false;
                }
            }

        }


    }

}


