

function disableAudit(formName, elName, flag) {
    if (event.srcElement.checked && flag != '3')
        document.forms[formName].elements[elName].disabled = true;

}



function auditOutInDiff(formName) {
    if (event.srcElement.name == "audit" && document.forms[formName].elements["d_out_in_diff"].value != "0"
            && document.forms[formName].elements["d_out_in_diff"].value != null && document.forms[formName].elements["d_out_in_diff"].value != '') {
        var rst = confirm("出入库数量不等，确定要审核吗？");
        if (!rst)
            return;
    }
    btnClick('detailOp','clearStart','detail','','clearStartHead');
}

function addCheckNum(formName, controlObs) {
    var num = 0;
    if (event.srcElement.name == "save") {
        for (i = 0; i < controlObs.length; i++) {
            num = num + document.forms[formName].elements[controlObs[i]].value;
        }
        if (num <= 0) {
            alert("所填写的票数量总和不能为0!");
            return;
        }
    }
    btnClick(formName, "clearStart", "detail");
}

function submitid(formName) {
    //alert(flag);
    //alert(event.srcElement.checked);
    if (event.srcElement.name == "detailSubmit") {
        var rst = confirm("提交将自动生成入库明细，确定要提交吗？");
        if (!rst)
            return;
    }
    btnClick(formName, "clearStart", "detail");

}

function setBillNo(querybill) {
    if (querybill == 'SR2009000000' || querybill == 'CR2009000000' || querybill == 'QR2009000000')
        document.getElementById('bill_no').value = '';

}

function disableButtonByReason(formName) {
    var reason = document.getElementById("d_reason_id").value;
    var form = document.forms['query1'];

    if (event.srcElement.name == "modify" && (reason == '10' || reason == '12')) {

        form.elements["d_reason_id"].disabled = true;
        form.elements["d_card_main_code"].disabled = true;
        form.elements["d_card_sub_code"].disabled = true;
        form.elements["d_storage_id"].disabled = false;
        form.elements["d_area_id"].disabled = false;
        form.elements["d_in_num"].disabled = true;
        form.elements["d_card_money"].disabled = true;
        form.elements["d_valid_date"].disabled = true;
        form.elements["d_report_date"].disabled = true;
        form.elements["d_line_id_reclaim"].disabled = true;
        form.elements["d_station_id_reclaim"].disabled = true;
        form.elements["d_start_logical_id"].disabled = true;
        form.elements["d_end_logical_id"].disabled = true;

    }
}

function disableButtonByRecord(formName, controlName) {
    var flag = document.getElementById("record_flag").value;
    if (flag == '3') {
        document.forms[formName].elements[controlName].disabled = false;
    } else
        document.forms[formName].elements[controlName].disabled = true;
}

function disableButtonByInRecord(formName, controlName) {
    //alert("1");
    var flag = document.getElementById("in_flag").value;
    var flag1 = document.getElementById("record_flag").value;
//    alert(flag);
//    alert(flag1);
    if (flag == '1') {
        document.forms[formName].elements[controlName].disabled = false;
    } else
        document.forms[formName].elements[controlName].disabled = true;
//alert();

}

function disableButtonByDetailFlag(formName, controlName) {
    var detail_flag = document.getElementById("detail_flag").value;
    var record_flag = document.getElementById("record_flag").value;
    if (record_flag == '3' && detail_flag == '0')
        document.forms[formName].elements[controlName].disabled = false;
    else
        document.forms[formName].elements[controlName].disabled = true;

}

function disableButtonByRecordArea(formName, controlName) {
    var flag = document.getElementById("record_flag").value;
    var area = document.getElementById("d_area_id").value;
    if (flag == '3' && area != '03') {
        document.forms[formName].elements[controlName].disabled = false;
    } else
        document.forms[formName].elements[controlName].disabled = true;

}

function disableInputByCardType(formName) {
    //alert(flag);
    var cardType = document.getElementById("d_card_main_code").value;
    var inType = document.getElementById("in_type").value;

    if (inType == 'XR' && (cardType == '12' || cardType == '1' || cardType == '2' || cardType == '33')) {
        document.forms[formName].elements["d_start_box_id"].disabled = true;
        document.forms[formName].elements["d_end_box_id"].disabled = true;
        document.forms[formName].elements["d_line_id"].disabled = true;
        document.forms[formName].elements["d_station_id"].disabled = true;
        document.forms[formName].elements["d_start_logical_id"].disabled = true;
        document.forms[formName].elements["d_end_logical_id"].disabled = true;
    } else {
        document.forms[formName].elements["d_start_box_id"].disabled = false;
        document.forms[formName].elements["d_end_box_id"].disabled = false;
        document.forms[formName].elements["d_line_id"].disabled = false;
        document.forms[formName].elements["d_station_id"].disabled = false;
        document.forms[formName].elements["d_start_logical_id"].disabled = false;
        document.forms[formName].elements["d_end_logical_id"].disabled = false;
    }
}



function controlByFlagOper(formName, controlName) {
    var checkBoxes = document.getElementsByName('rectNo');
    var operator = document.forms[formName].elements['operator'];
    
    if (checkBoxes == null)
        return;
    if (checkBoxes.length == 0)
        return

    for (i = 0; i < checkBoxes.length; i++) {
        if (checkBoxes[i].checked) {
            //20170907 mqf
            if (checkBoxes[i].getAttribute('flag') != '3' || checkBoxes[i].getAttribute('formaker') != operator.value) {
                document.forms[formName].elements[controlName].disabled = true;
            } else
                document.forms[formName].elements[controlName].disabled = false;
            break;
        }

    }
}

function controlByFlag(formName, controlName) {
    var checkBoxes = document.getElementsByName('rectNo');
    if (checkBoxes == null)
        return;
    if (checkBoxes.length == 0)
        return
    var cc = 0;
    for (i = 0; i < checkBoxes.length; i++) {
        if (checkBoxes[i].checked) {
            cc++;
            if (checkBoxes[i].getAttribute('flag') != '3') { //20170907 mqf
                document.forms[formName].elements[controlName].disabled = true;
            } else
                document.forms[formName].elements[controlName].disabled = false;
            break;
        }

    }
    if (cc == 0) {
        document.forms[formName].elements[controlName].disabled = true;
    }
}

function selectItemsForDialog(divName, formName) {
//    alert('a');
    var btId = event.srcElement.id;
    if (btId == 'cancel') {

        window.returnValue = "";
        window.close();
        return;
    }
    getAllSelectedIDs(divName, formName);
//    var allSelectedIds = document.forms[formName].all['allSelectedIDs'].value;
    //20170911 mqf
    var allSelectedIds = document.forms[formName].getElementsByTagName("input")['allSelectedIDs'].value;

    window.returnValue = allSelectedIds;
    window.close();
    return;
}

function selectItemsForDialogDevID(divName, formName) {
    //alert('a');
    var btId = event.srcElement.id;
    if (btId == 'cancel') {

        window.returnValue = "";
        window.close();
        return;
    }

    var rowSelected = document.forms[formName].all['rowSelected'].value;

    window.returnValue = rowSelected;
    window.close();
    return;
}

function checkInputNum(curEle, num) {
    var val = curEle.value;
    var len = val.length;
    if (val.length != num && val != '' && val.length != 0) {
        alert("必须输入" + num + "，或者无输入");
        curEle.focus();
    }
}

function checkInputLogical(curEle) {
    var cardType = document.getElementById("d_card_main_code").value;

    var val = curEle.value;
    if ((cardType == '1' || cardType == '12') && val.length != 20 && val != '' && val.length != 0) {
        alert("必须输入20位");

        //curEle.focus();
    }
    if (cardType == '6' && val.length != 10 && val.length != 8 && val.length != 20 && val != '' && val.length != 0) {
        alert("必须输入8位或10位或20位");
        //curEle.focus();
    }
}


function returnAdd() {
    var form = document.forms['query1'];
    for (var i = 0; i < form.elements.length; i++)
    {
        var e = form.elements[i];
        var s = form.elements[i].name;

        if (s.substring(0, 2) == 'd_' && s != 'd_reason_id')
            e.disabled = true;
    }


}

function returnUpdateByReason() {
    var form = document.forms['query1'];

    form.elements["d_card_main_code"].disabled = true;
    form.elements["d_card_sub_code"].disabled = true;
    form.elements["d_storage_id"].disabled = true;
    form.elements["d_area_id"].disabled = true;
    form.elements["d_in_num"].disabled = true;
    form.elements["d_card_money"].disabled = true;
    form.elements["d_valid_date"].disabled = true;
    form.elements["d_report_date"].disabled = true;
    form.elements["d_line_id_reclaim"].disabled = true;
    form.elements["d_station_id_reclaim"].disabled = true;
    form.elements["d_report_date"].disabled = true;
    form.elements["d_report_date"].readonly = true;


}

function returnUpdateRequired() {
    var form = document.forms['detailOp'];
    var btId = event.srcElement.id;
    var reason = form.all['d_reason_id'].value;
    if (reason == "10" || reason == "12" || reason == "25") {
        form.elements["d_card_main_code"].disabled = true;
        form.elements["d_card_sub_code"].disabled = true;
        //2012-03-12 luojun 
        form.elements["d_storage_id"].disabled = false;
        if(reason == "10"){
            form.elements["d_area_id"].disabled = false;
        }else{
            form.elements["d_area_id"].disabled = true;
        }
        
        form.elements["d_in_num"].disabled = true;
        form.elements["d_card_money"].disabled = true;
        form.elements["d_cardMoney1"].disabled = true;
        //form.elements["d_valid_date"].disabled = true;
        //form.elements["img_valid"].disabled=true;
        //form.elements["d_start_logical_id"].disabled = true;
        //form.elements["d_end_logical_id"].disabled = true;

        form.elements["d_card_main_code"].require = "false";
        form.elements["d_card_sub_code"].require = "false";
        //2012-03-12 luojun form.elements["d_storage_id"].require="false";
        form.elements["d_area_id"].require = "false";
        form.elements["d_in_num"].require = "false";

        form.elements["d_line_id_reclaim"].disabled = true;
//        if (reason == "10")
//            form.elements["d_station_id_reclaim"].disabled = false;
//        else
            form.elements["d_station_id_reclaim"].disabled = true;
        form.elements["d_report_date"].disabled = false;
        //form.elements["img_report"].disabled=false;
        form.elements["d_report_date"].require = "true";

    }else if(reason == "" || reason ==null){//zhouyang 20171026
        form.elements["d_card_main_code"].disabled = true;
        form.elements["d_card_sub_code"].disabled = true;
        form.elements["d_storage_id"].disabled = true;
        form.elements["d_area_id"].disabled = true;
        form.elements["d_in_num"].disabled = true;
        form.elements["d_card_money"].disabled = true;
        form.elements["d_cardMoney1"].disabled = true;
        form.elements["d_card_main_code"].require = "false";
        form.elements["d_card_sub_code"].require = "false";
        form.elements["d_area_id"].require = "false";
        form.elements["d_in_num"].require = "false";
        form.elements["d_line_id_reclaim"].disabled = true;
        form.elements["d_station_id_reclaim"].disabled = true;
        form.elements["d_report_date"].disabled = true;
        form.elements["d_report_date"].require = "true";
    }
    else {
        form.elements["d_card_main_code"].disabled = false;
        form.elements["d_card_sub_code"].disabled = false
        form.elements["d_storage_id"].disabled = false;
        form.elements["d_area_id"].disabled = false;
        form.elements["d_in_num"].disabled = false;
        form.elements["d_card_money"].disabled = false;
        form.elements["d_cardMoney1"].disabled = false;
        //form.elements["d_valid_date"].disabled = false;
        //form.elements["img_valid"].disabled=false;
        form.elements["d_line_id_reclaim"].disabled = false;
        form.elements["d_station_id_reclaim"].disabled = false;
        //form.elements["d_start_logical_id"].disabled = false;
        //form.elements["d_end_logical_id"].disabled = false;

        form.elements["d_card_main_code"].require = "true";
        form.elements["d_card_sub_code"].require = "true";
        form.elements["d_storage_id"].require = "true";
        form.elements["d_area_id"].require = "true";
        form.elements["d_in_num"].require = "true";

        form.elements["d_card_main_code"].dataType = "Require";
        form.elements["d_card_sub_code"].dataType = "Require";
        form.elements["d_storage_id"].dataType = "Require";
        form.elements["d_area_id"].dataType = "Require";

        form.elements["d_report_date"].disabled = true;
        form.elements["d_report_date"].require = "false";
        //form.elements["img_report"].disabled=true;
    }
}

function setAreaByCard(formName, type) {
    //	     alert("formName="+formName);
    //	      alert("varName="+varName);
    //	      alert("varValues="+document.forms[formName].all[varName].value);
    var cardType = document.forms[formName].all["d_card_main_code"].value;
    var reason = document.forms[formName].all["d_reason_id"].value;
    var area = document.forms[formName].all["d_area_id"];
    var storage = document.forms[formName].all["d_storage_id"];
    var subValues = getSubValues(formName, 'd_card_main_code', '', "commonVariable1");


    if (area.options.length <= 1)
        return;

    if (type == 'XR') {
        if (cardType == '12' || cardType == '1' || cardType == '33') {
            for (i = area.options.length - 1; i >= 1; i--)
                if (area.options[i].value != '01')
                    area.remove(i);
        } else {
            for (i = area.options.length - 1; i >= 1; i--)
                if (area.options[i].value != '03')
                    area.remove(i);
        }

    }
    if (type == 'HR') {
        if (reason == '10' || reason == '12')
            return;

        if (cardType == '12' && reason == '11') {
            for (i = area.options.length - 1; i >= 1; i--)
                if (area.options[i].value != '07')
                    area.remove(i);
        }
        if (cardType == '12' && reason == '13') {
            for (i = area.options.length - 1; i >= 1; i--)
                if (area.options[i].value != '04' && area.options[i].value != '06' && area.options[i].value != '07')
                    area.remove(i);
        }
        if (cardType == '1' && reason == '13') {
            for (i = area.options.length - 1; i >= 1; i--)
                if (area.options[i].value != '05' && area.options[i].value != '06')
                    area.remove(i);
        }
        if (cardType == '1' && reason == '14') {
            for (i = area.options.length - 1; i >= 1; i--)
                if (area.options[i].value != '05')
                    area.remove(i);
        }
        if (cardType == '6' && reason == '14') {
            for (i = area.options.length - 1; i >= 1; i--)
                if (area.options[i].value != '06')
                    area.remove(i);
        }
    }

}


function setCardByReason(formName, type) {
    var cardType = document.forms[formName].all["d_card_main_code"];
    var reason = document.forms[formName].all["d_reason_id"].value;

    if (reason == '10' || reason == '12')
        return;

    if (cardType.options.length <= 1)
        return;


    if (type = 'HR') {
        if (reason == '11') {
            for (i = cardType.options.length - 1; i >= 1; i--)
                if (cardType.options[i].value != '12')
                    cardType.remove(i);
        }
        if (reason == '14') {
            for (i = cardType.options.length - 1; i >= 1; i--)
                if (cardType.options[i].value != '1')
                    cardType.remove(i);
        }
    }

    function setSelectValuesByRowPropertyName(formName, selectName, varName, rowAttributeName) {

        var cmdV = document.forms[formName].all[varName];
        //     var sltd = document.forms[formName].all[selectedName];
        var slt = document.forms[formName].all[selectName];
        var rowOb = event.srcElement;
        while (rowOb.tagName != "TR")
            rowOb = rowOb.parentElement;
        //     var rowID = rowOb.typeIDs;
        var rowID = rowOb.getAttribute(rowAttributeName);
        var keys = rowID.split("#");

        //        alert(keys[0]+"#"+keys[1]);
        //         alert(cmdV.value);
        var typ = keys[0];
        if (cmdV.value == null || cmdV.value == '')
            return;
        if (cmdV.value.indexOf(":") == -1)
            return;

        var values = cmdV.value.split(":");
        var count = 0;
        if (count == 0 && slt.options.length > 1) {
            for (j = slt.options.length - 1; j >= 1; j--) {
                slt.remove(j);
            }
        }
        for (i = 0; i < values.length; i++) {
            if (values[i].indexOf(",") == -1)
                continue;
            var objs = values[i].split(",");
            if (objs[0] == typ) {
                var opt = new Option(objs[2], objs[1]);
                slt.add(opt);
                count++;
            }

        }
    }



}

function disableInputByBlankCardType(formName) {
    var blankCardType = document.getElementById("d_blankCardType").value;
    //单程值、未选择
    if (blankCardType == '0' || blankCardType == "") {
        document.forms[formName].elements["d_startLogicNo"].disabled = true;
        document.forms[formName].elements["d_endLogicNo"].disabled = true;
        //document.forms[formName].elements["d_settCurrentStartLogicNo"].disabled=true;

        document.forms[formName].elements["d_startLogicNo"].require = "false";
        document.forms[formName].elements["d_startLogicNo"].dataType = "";
    } else {
        document.forms[formName].elements["d_startLogicNo"].disabled = false;
        document.forms[formName].elements["d_endLogicNo"].disabled = true;
        //document.forms[formName].elements["d_settCurrentStartLogicNo"].disabled=false;

        document.forms[formName].elements["d_startLogicNo"].require = "true";
        document.forms[formName].elements["d_startLogicNo"].dataType = "Integer|Limit";
    }
}

function disableInputByBlankCardbtnClick(formName) {
    if (event.srcElement.name == "add" || event.srcElement.name == "modify") {
        var blankCardType = document.getElementById("d_blankCardType").value;
        //单程值、未选择
        if (blankCardType == '0' || blankCardType == "") {
            document.forms[formName].elements["d_startLogicNo"].disabled = true;
            document.forms[formName].elements["d_endLogicNo"].disabled = true;
            //document.forms[formName].elements["d_settCurrentStartLogicNo"].disabled=true;

            document.forms[formName].elements["d_startLogicNo"].require = "false";
        } else if (blankCardType == '1') {
            document.forms[formName].elements["d_startLogicNo"].disabled = false;
            document.forms[formName].elements["d_endLogicNo"].disabled = true;
            //document.forms[formName].elements["d_settCurrentStartLogicNo"].disabled=false;

            document.forms[formName].elements["d_startLogicNo"].require = "true";
        }
    }
}

//取当前逻辑卡号 
function getCurrentStartLogicNo(formName, startLogicNo) {
    var frm = document.forms[formName];
    if (document.getElementById(startLogicNo).disabled == true) {
        return;
    }
    var url = frm.action;
    jQuery.ajax({
        url: url,
        data: {command: 'ajaxGetCurrentStartLogicNo'},
        type: "POST",
        error: function (request) {
            alert("表单提交出错，请稍候再试");
        },
        success: function (data) {

            var currentStartLogicNo = data;
            if (currentStartLogicNo == null || currentStartLogicNo == "") {
                alert("操作失败");
            } else {
                document.getElementById(startLogicNo).value = currentStartLogicNo;
            }
        }
    });
    return;
}

function disableFormControls(formName, controlObs, disabled) {
    for (i = 0; i < controlObs.length; i++) {
//        document.forms[formName].all[controlObs[i]].disabled = disabled;
        //20180313 mqf 支付select控件
        var ob = document.forms[formName].getElementsByTagName('input')[controlObs[i]];
        if(ob == null ){
            ob = document.forms[formName].getElementsByTagName('select')[controlObs[i]];
        }
        if(ob != null ){
            ob.setAttribute('disabled',disabled);
        }
    }
}

function controlByThisFlagValue(formName, controlName, controlValue) {
    if (controlValue != 0) {
        document.forms[formName].elements[controlName].disabled = true;
    } else {
        document.forms[formName].elements[controlName].disabled = false;
    }
}

function controlByFlagValue(formName, controlName) {
    var checkBoxes = document.getElementsByName('rectNo');
    if (checkBoxes == null)
        return;
    if (checkBoxes.length == 0)
        return
    var cc = 0;
    for (i = 0; i < checkBoxes.length; i++) {
        if (checkBoxes[i].checked) {
            cc++;
            if (checkBoxes[i].flag != '3') {
                document.forms[formName].elements[controlName].disabled = true;
            } else
                document.forms[formName].elements[controlName].disabled = false;
            break;
        }

    }
    if (cc == 0) {
        document.forms[formName].elements[controlName].disabled = true;
    }
}