
//控制审核按钮，仅当所选记录未审核时，审核按钮才可用

function disableAudit(formName, auditName, flag) {
    //alert(flag);
    //alert(event.srcElement.checked);
    if (event.srcElement.checked && flag != '3')
        document.forms[formName].getElementsByTagName('input')[auditName].disabled = true;
}
/*
 function showMulArray(mArray){
 for(var i=0;i<mArray.length;i++){
 for(var j=0;j<mArray[i].length;j++)
 alert(mArray[i][j]);
 }
 }
 */

//控制增加、删、改按钮，仅当所选记录未审核时，审核按钮才可用

function disableControlByFlag(formName, controlName, flag) {
    //alert(flag);
    //alert(event.srcElement.checked);
    if (flag == '3')
        document.forms[formName].getElementsByTagName('input')[controlName].disabled = false;
    else
        document.forms[formName].getElementsByTagName('input')[controlName].disabled = true;
}
function disableControlsByFlag(formName, controlNames, flag) {
//    alert(flag);
    //alert(event.srcElement.checked);
    for (i = 0; i < controlNames.length; i++) {
        //alert(controlNames[i]);
        if (flag == '3')
            //document.forms[formName].elements[controlNames[i]].disabled=false;
            document.forms[formName].getElementsByTagName('input')[controlNames[i]].disabled = false;
        else
            // document.forms[formName].getElementsByTagName('input')[controlNames[i]].disabled = true;
            document.forms[formName].getElementsByTagName('input')[controlNames[i]].disabled = true;
    }
}
//通过单据状态控制单个操作按钮状态

//20160330 checkBoxes[i].checked不选时不作任何设置，icStorageIn.js不存在这个问题，增加controlByFlagForProduceOut
function controlByFlag(formName, controlName) {
    //alert(flag);
    //alert(event.srcElement.checked);
    var checkBoxes = document.getElementsByName('rectNo');
    if (checkBoxes == null)
        return;
    if (checkBoxes.length == 0)
        return
    for (i = 0; i < checkBoxes.length; i++) {
        if (checkBoxes[i].checked && checkBoxes[i].flag != '3') {
            disableControlByFlag(formName, controlName, checkBoxes[i].flag);
            break;
        }
    }
}

//通过单据状态控制多个操作按钮状态

function controlsByFlagWithoutCk(formName, controlNames) {
    var rowSelected = document.forms[formName].getElementsByTagName('div')["rowSelected"];
    var lineId;
    var lineOb;
    var value = rowSelected.getAttribute('value');
    if (rowSelected != null && value != null) {
        lineId = value;
        lineOb = document.getElementById(lineId);
        if (lineOb != null){
            var flag = lineOb.getAttribute('flag');
            disableControlsByFlag(formName, controlNames, flag);
        }
            

    }



}
function controlsByFlagForModify(formName) {

    var rowSelected = document.forms[formName].getElementsByTagName('input')["rowSelected"];
    var lineId;
    var lineOb;
    if (rowSelected != null) {
        lineId = rowSelected.value;
        lineOb = document.getElementById(lineId);
        //alert(lineOb.flag);
        if (lineOb != null && lineOb.flag != null && lineOb.flag == '3')
            document.forms[formName].getElementsByTagName('input')['modify'].disabled = false;
        else
            document.forms[formName].getElementsByTagName('input')['modify'].disabled = true;

    }

}
function selectItemsForDialog(divName, formName) {
    //alert('a');
    var btId = event.srcElement.id;
    if (btId == 'cancel') {

        window.returnValue = "";
        window.close();
        return;
    }
    getAllSelectedIDs(divName, formName);
    var allSelectedIds = document.forms[formName].getElementsByTagName('input')['allSelectedIDs'].value;

    window.returnValue = allSelectedIds;
    window.close();
    return;
}

function getAllSelectedIDsForStation(listName, formName) {
    //	alert('call');
    var listDiv = document.getElementById(listName);
    var tbl = listDiv.children[0];
    var selectedIDs = document.forms[formName].getElementsByTagName('input')['allSelectedIDs'];
    selectedIDs.value = "";

    for (i = 0; i < tbl.rows.length; i++) {
        //    alert(i);
        var tblRow = tbl.rows[i];
        var checkCell = tblRow.cells[0];
        var selectedCK = checkCell.children[0];
        if (tblRow.id == 'ignore')
            continue;
        //  alert(tblRow.id);
        if (selectedCK != null) {
            //  alert(selectedCK.checked);
            if (selectedCK.checked) {
                selectedIDs.value += tblRow.id + ";";
                //				alert(selectedIDs.value);
            }
        }

    }
//	alert("selectedIDs.value="+selectedIDs.value);
}


function selectItemsForDialogStation(divName, formName) {
    //alert('a');
    var btId = event.srcElement.id;
    if (btId == 'cancel') {

        window.returnValue = "";
        window.close();
        return;
    }
    if (!checkInput('DataTable'))//检查所选行是否为正实数
        return;
    changeTRIdValue('DataTable');
    getAllSelectedIDsForStation(divName, formName);
    var allSelectedIds = document.forms[formName].getElementsByTagName('input')['allSelectedIDs'].value;

    window.returnValue = allSelectedIds;
    window.close();
    return;
}
function selectItemsForDialogOperator(divName, formName) {
    //alert('a');
    var btId = event.srcElement.id;
    if (btId == 'cancel') {

        window.returnValue = "";
        window.close();
        return;
    }

    getAllSelectedIDs(divName, formName);
    var allSelectedIds = document.forms[formName].getElementsByTagName('input')['allSelectedIDs'].value;

    window.returnValue = allSelectedIds;
    window.close();
    return;
}
//改变列表行的ID值，用于配票出库的车站

function changeTRIdValue(tableId) {
    var tabOb = document.getElementById(tableId);
    var rows = tabOb.rows;
    var row;
    var disQuntityId;
    for (i = 0; i < rows.length; i++) {
        row = rows[i];
        if (row.id == 'ignore')
            continue;
        disQuntityId = row.id + 'distributeQuantity';
//        alert(disQuntityId);
        row.id = row.id + '#' + document.getElementById(disQuntityId).value;
        //alert(row.id);
    }
}
//由出库原因控制明细的各个控件可见性，用于配票出库
function showControlByReasonId(reasonIdName, disControlNames, enControlNames) {
    //modify by zhongzq 20170803
    var reasonIdOb = document.getElementById(reasonIdName);
    var selectedOb = reasonIdOb.options[reasonIdOb.selectedIndex];
    if (selectedOb.value == '17') { //配票到线路
                                    //不可见:配票线路、车站、出库方式（数量、逻辑卡号、盒号）
                                    //可见：配票内容
        for (var i = 0; i < disControlNames.length; i++) {
            document.getElementById(disControlNames[i]).disabled = true;
        }
        for (var i = 0; i < enControlNames.length; i++) {
//            alert("enControlNames:"+enControlNames[i]+"||enControlNames.length:"+enControlNames.length);
            document.getElementById(enControlNames[i]).disabled = false;
        }
    }
    else {   //配票其他部门、员工票办理点、车票移交
             //可见:配票线路、车站、出库方式（数量、逻辑卡号、盒号）
             //不可见：配票内容
        for (var i = 0; i < disControlNames.length; i++)
            document.getElementById(disControlNames[i]).disabled = false;
        for (var i = 0; i < enControlNames.length; i++){
//            alert("enControlNames:"+enControlNames[i]);
            document.getElementById(enControlNames[i]).disabled = true;
        }
    }

}
function checkInput(tableId) {
    var tableOb = document.getElementById(tableId);
    var row;
    var ckOb;
    var inpOb;
    //alert(tableOb.rows.length);

    for (var i = 0; i < tableOb.rows.length; i++) {
        row = tableOb.rows[i];
        //alert(row.children[2]);
        //alert(row.children[3]);
        if (row.id == 'ignore')
            continue;
        ckOb = row.children[0].children[0];
        if (!ckOb.checked)
            continue;
        inpOb = row.children[3].children[0];
        //alert(inpOb.value); 

        if (!(/^[+]?\d+$/.test(inpOb.value)) || !isPositive(inpOb.value)) {
//            alert(row.children[2].innerHTML + '的配票数量应是正整数');//'选择的'+row.children[1].innerHTML+
            alert(row.children[2].innerText + '的配票数量应是正整数');//'选择的'+row.children[1].innerHTML+
            if (row.style.display != 'none')
                inpOb.focus();
            return false;
        }

    }
    return true;
}
function isPositive(value) {
    if (value > 0)
        return true;
    return false;
}
function setCardMoney(formName, cardMoneyName, controlName) {
    document.forms[formName].getElementsByTagName('input')[cardMoneyName].value = document.forms[formName].getElementsByTagName('select')[controlName].value;
}
//add by zhongzq 20170805
function setCardMoneyById(cardMoneyName, controlName) {
    document.getElementById(cardMoneyName).value = document.getElementById(controlName).value;
}
/*
 function setCardMoney(formName,cardMoneyName,controlName,zoneName){
 alert(document.forms[formName].all[zoneName].value);
 if(document.forms[formName].all[zoneName].value=='02'){
 document.forms[formName].all[CardMoneyDiv].setAttribute("display","none");
 }else{
 document.forms[formName].all[cardMoneyName].value = document.forms[formName].all[controlName].value;
 }
 }
 */
//清除明细单号
function clearTextByFlag(formName, controlNames) {
    var rowSelected = document.forms[formName].getElementsByTagName('input')["rowSelected"];
    var lineId;
    var lineOb;

    if (rowSelected != null) {
        lineId = rowSelected.value;
        //   	alert(rowSelected.value);
        lineOb = document.getElementById(lineId);

        if (lineOb != null && lineOb.flag != null && lineOb.flag != '3') {
            for (i = 0; i < controlNames.length; i++)
                document.forms[formName].getElementsByTagName('input')[controlNames[i]].value = '';
        }


    }


}
function getOptionIndex(controlValue, options) {
    for (var i = 0; i < options.length; i++) {
        if (controlValue == options[i])
            return i;
    }
    return -1;
}
//对于提交窗体的一些控件，当其中某一选项取某值时，需将其他选项设为必须检查

function setFormCheckOption(formName, controlName, options, opControls, requireds) {
    var controlValue = document.forms[formName].getElementsByTagName('input')[controlName].value;
    var index = getOptionIndex(controlValue, options);
    if (index == -1)
        return;

    for (var i = 0; i < opControls.length; i++) {
        //alert(document.forms[formName].all[opControls[i]]);
        document.forms[formName].getElementsByTagName('input')[opControls[i]].require = requireds[index];
        //alert(document.forms[formName].all[opControls[i]].require);
    }
}
//zhongzq 20170803
function setFormCheckOptionBySelect(controlName, options, opControls, requireds) {
    var reasonIdOb = document.getElementById(controlName);
    var controlValue = reasonIdOb.options[reasonIdOb.selectedIndex].value;
//       alert("controlValue:"+controlValue);
    var index = getOptionIndex(controlValue, options);
    if (index == -1)
        return;
    
    for (var i = 0; i < opControls.length; i++) {
        //alert(document.forms[formName].all[opControls[i]]);
        document.getElementById(opControls[i]).require = requireds[index];
//         alert("require:"+ document.getElementById(opControls[i]).require);
        //alert(document.forms[formName].all[opControls[i]].require);
    }
}

function setFormCheckOptionForRadios(radioName, relativeObjects) {

    var radioObs = document.getElementsByName(radioName);
    var relativeOb;
    for (i = 0; i < radioObs.length; i++) {
        radioOb = radioObs[i];
        relativeOb = document.getElementsByName(relativeObjects[i]);
        if (radioOb.checked)
            relativeOb.require = 'true';
        else
            relativeOb.require = 'false';

    }

}

function controlByAreaForBorrow() {
    var frm = document.forms['detailOp'];
    var areaIdOb = frm.getElementsByTagName('select')['d_areaId'];
    var validDateOb = frm.getElementsByTagName('input')['d_validDate'];

    if (areaIdOb.value == '03') {//赋值区
        //if(isSJTFromValueArea('detailOp','d_icMainType','d_areaId'))
        validControls([validDateOb], 'true');
    } else
    {
        validControls([validDateOb], 'false');
    }
}

function controlByCardTypeAndArea() {
    var frm = document.forms['detailOp'];
    var icMainTypeOb = frm.getElementsByTagName('select')['d_icMainType'];
    var areaIdOb = frm.getElementsByTagName('select')['d_areaId'];
    var outNumOb = frm.getElementsByTagName('input')['d_lendQuantity'];
    var startLogicalIdOb = frm.getElementsByTagName('input')['d_startLogicalId'];
    var endLogicalIdOb = frm.getElementsByTagName('input')['d_endLogicalId'];
    var cardMoneyOb = frm.getElementsByTagName('select')['d_cardMoney'];
    var cardMoneyOb1 = frm.getElementsByTagName('select')['d_cardMoney1'];
    var validDateOb = frm.getElementsByTagName('input')['d_validDate'];
    var selectOb = frm.getElementsByTagName('input')['d_select'];


    //新票区、单程票编码区出库，仅按数量
    //  alert(icMainTypeOb.value);
    //  alert(areaIdOb.value);

    if ((areaIdOb.value == '01') ||
            (areaIdOb.value == '04') ||
            (areaIdOb.value == '05') ||
            (areaIdOb.value == '06') ||
            (areaIdOb.value == '07') ||
            (areaIdOb.value == '02' && icMainTypeOb.value == '12')) {

        //数量可见
        disableControls([outNumOb], false);

        //面值、有效期、逻辑卡号、逻辑卡号复选框不可见

        disableControls([startLogicalIdOb, endLogicalIdOb, selectOb[1]], true);
        //数量校验
        validControls([outNumOb], 'true');

        //数量复选框选中
        selectOb[0].checked = true;


    }
    //赋值区、储值票编码区出库，按数量或逻辑卡号

    else {
        //逻辑卡号不可见

        disableControls([startLogicalIdOb, endLogicalIdOb], true);
        //数量、逻辑卡号复选框可见
        //disableControls([cardMoneyOb,cardMoneyOb1,validDateOb,outNumOb,selectOb[1]],false);
        disableControls([outNumOb, selectOb[1]], false);
        //逻辑卡号不校验

        validControls([startLogicalIdOb, endLogicalIdOb], 'false');
        //数量复选框选中
        selectOb[0].checked = true;


    }


}
function controlByCardTypeAndAreaForBorrow() {
    var frm = document.forms['detailOp'];
    var icMainTypeOb = frm.getElementsByTagName('select')['d_icMainType'];
    var areaIdOb = frm.getElementsByTagName('select')['d_areaId'];
    var outNumOb = frm.getElementsByTagName('input')['d_lendQuantity'];
    var startLogicalIdOb = frm.getElementsByTagName('input')['d_startLogicalId'];
    var endLogicalIdOb = frm.getElementsByTagName('input')['d_endLogicalId'];
    var cardMoneyOb = frm.getElementsByTagName('input')['d_cardMoney'];
    var cardMoneyOb1 = frm.getElementsByTagName('select')['d_cardMoney1'];
    var validDateOb = frm.getElementsByTagName('input')['d_validDate'];
    var selectOb = frm.getElementsByTagName('input')['d_select'];


    //新票区、单程票编码区出库，仅按数量
//      alert(icMainTypeOb.value);
//      alert(areaIdOb.value);
    if (isSJTFromValueArea('detailOp', 'd_icMainType', 'd_areaId')){
        validControls([validDateOb], 'true');
//        alert(validDateOb.require);
    }
    else
        validControls([validDateOb], 'false');


    if ((areaIdOb.value == '01') ||
            (areaIdOb.value == '04') ||
            (areaIdOb.value == '05') ||
            (areaIdOb.value == '06') ||
            (areaIdOb.value == '07') ||
            (areaIdOb.value == '02' && icMainTypeOb.value == '12')) {

        //数量可见
        disableControls([outNumOb], false);

        //面值、有效期、逻辑卡号、逻辑卡号复选框不可见

        disableControls([startLogicalIdOb, endLogicalIdOb, selectOb[1]], true);
        //数量校验
        validControls([outNumOb], 'true');

        //数量复选框选中
        selectOb[0].checked = true;


    }
    //赋值区、储值票编码区出库，按数量或逻辑卡号

    else {
        //逻辑卡号不可见

        disableControls([startLogicalIdOb, endLogicalIdOb], true);
        //数量、逻辑卡号复选框可见
        //disableControls([cardMoneyOb,cardMoneyOb1,validDateOb,outNumOb,selectOb[1]],false);
        disableControls([outNumOb, selectOb[1]], false);
        //逻辑卡号不校验

        validControls([startLogicalIdOb, endLogicalIdOb], 'false');
        //数量复选框选中
        selectOb[0].checked = true;


    }


}

function controlForCardMoneyProduce(formName, areaIdName, cardMoneyProduceName, cardMoneyProduceName1) {
    areaIdOb = document.forms[formName].getElementsByTagName('select')[areaIdName];
    cardMoneyProduceOb = document.forms[formName].getElementsByTagName('select')[cardMoneyProduceName];
    cardMoneyProduceOb1 = document.forms[formName].getElementsByTagName('select')[cardMoneyProduceName1];
    //循环区时，应输入生产卡面值

    if (areaIdOb.value == '04')
    {
        disableControls([cardMoneyProduceOb, cardMoneyProduceOb1], false);
        validControls([cardMoneyProduceOb], 'false');
    } else
    {
        disableControls([cardMoneyProduceOb, cardMoneyProduceOb1], true);
        validControls([cardMoneyProduceOb], 'true');
    }
}
function  isSJTFromValueArea(formName, icMainType, areaId) {
    var frm = document.forms[formName];
    var icMainTypeOb = frm.getElementsByTagName('select')[icMainType];
    var areaIdOb = frm.getElementsByTagName('select')[areaId];
    if (icMainTypeOb.value == '12' && areaIdOb.value == '03'){
        return true;
    }
    return false;
}
function controlByCardTypeAndAreaForProduce() {
    var frm = document.forms['detailOp'];
    var workType = frm.getElementsByTagName('select')['d_workType'];
    var icMainTypeOb = frm.getElementsByTagName('select')['d_cardMainType'];
    //alert(icMainTypeOb.value);
    if (workType.value == '01' && icMainTypeOb.value == '12')
        validControlsByName('detailOp', ['d_validDate'], 'true');

}

//由卡类型、卡票区控制控件是否需要校验、可见

function controlByCardTypeAndAreaForDistribute() {
    var frm = document.forms['detailOp'];
    var icMainTypeOb = frm.getElementsByTagName('select')['d_icMainType'];
    var areaIdOb = frm.getElementsByTagName('select')['d_areaId'];
    var outNumOb = frm.getElementsByTagName('input')['d_distributeQuantity'];
    var startLogicalIdOb = frm.getElementsByTagName('input')['d_startLogicalId'];
    var endLogicalIdOb = frm.getElementsByTagName('input')['d_endLogicalId'];
    var boxIdOb = frm.getElementsByTagName('input')['d_boxId'];
    var endBoxIdOb = frm.getElementsByTagName('input')['d_endBoxId'];
//    var cardMoneyOb = frm.getElementsByTagName('select')['d_cardMoney'];
var cardMoneyOb = frm.getElementsByTagName('input')['d_cardMoney']; //modify by zhongzq 20170804
    var cardMoneyOb1 = frm.getElementsByTagName('select')['d_cardMoney1'];
    var validDateOb = frm.getElementsByTagName('input')['d_validDate'];
    var selectOb = frm.getElementsByTagName('input')['d_select'];
    var reasonIdOb = frm.getElementsByTagName('select')['d_reasonId'];
    //单程票从赋值区出库一定需输入有效期
    if (isSJTFromValueArea('detailOp', 'd_icMainType', 'd_areaId'))
        validControls([validDateOb], 'true');
    else
        validControls([validDateOb], 'false');
    if (reasonIdOb.value == '17') {//车站配票，数量及逻辑卡号、盒号不作校验
        validControls([startLogicalIdOb, endLogicalIdOb, boxIdOb, endBoxIdOb, outNumOb], 'false');
        return;
    }
    //新票区、循环区、回收区、待注销区、待销毁区、单程票编码区出库，仅按数量
    // alert(icMainTypeOb.value);
    // alert(areaIdOb.value);
    if ((areaIdOb.value == '01') ||
            (areaIdOb.value == '04') ||
            (areaIdOb.value == '05') ||
            (areaIdOb.value == '06') ||
            (areaIdOb.value == '07') ||
            (areaIdOb.value == '02' && icMainTypeOb.value == '12')
        )
    {
        //数量可见
        disableControls([outNumOb], false);
        //逻辑卡号、盒号、逻辑卡号及盒号复选框不可见
        //disableControls([cardMoneyOb,cardMoneyOb1,validDateOb,startLogicalIdOb,endLogicalIdOb,selectOb[1]],true);
        disableControls([startLogicalIdOb, endLogicalIdOb, boxIdOb, endBoxIdOb, selectOb[1], selectOb[2]], true);
        //数量校验
        validControls([outNumOb], 'true');
        //逻辑卡号、盒号不校验
        validControls([startLogicalIdOb, endLogicalIdOb, boxIdOb, endBoxIdOb], 'false');
        //数量复选框选中
        selectOb[0].checked = true;
    }
    //赋值区、储值票编码区出库，按数量或逻辑卡号、盒号，默认：按数量
    else {
        //逻辑卡号、盒号不可见
        disableControls([startLogicalIdOb, endLogicalIdOb, boxIdOb, endBoxIdOb], true);
        //数量，逻辑卡号、盒号复选框可见
        //disableControls([cardMoneyOb,cardMoneyOb1,validDateOb,outNumOb,selectOb[1]],false);
        disableControls([outNumOb, selectOb[1], selectOb[2]], false);
        //数量、面值校验
        validControls([outNumOb, cardMoneyOb], 'true');
        //逻辑卡号、盒号不校验
        validControls([startLogicalIdOb, endLogicalIdOb, boxIdOb, endBoxIdOb], 'false');
        //数量复选框选中
        selectOb[0].checked = true;
    }
}
//控制控件是否需要校验、可见

function controlByCardTypeAndAreaForDistributeModify() {
    var frm = document.forms['detailOp'];
    var icMainTypeOb = frm.getElementsByTagName('select')['d_icMainType'];
    var areaIdOb = frm.getElementsByTagName('select')['d_areaId'];
    var outNumOb = frm.getElementsByTagName('input')['d_distributeQuantity'];
    var startLogicalIdOb = frm.getElementsByTagName('input')['d_startLogicalId'];
    var endLogicalIdOb = frm.getElementsByTagName('input')['d_endLogicalId'];
    var boxIdOb = frm.getElementsByTagName('input')['d_boxId'];
    var endBoxIdOb = frm.getElementsByTagName('input')['d_endBoxId'];
    var cardMoneyOb = frm.getElementsByTagName('select')['d_cardMoney'];
    var cardMoneyOb1 = frm.getElementsByTagName('select')['d_cardMoney1'];
    var validDateOb = frm.getElementsByTagName('input')['d_validDate'];
    var selectOb = frm.getElementsByTagName('input')['d_select'];
    var reasonIdOb = frm.getElementsByTagName('select')['d_reasonId'];
    if (reasonIdOb.value == '17') {//车站配票，数量及逻辑卡号、盒号不作效验

        validControls([outNumOb], 'true');
        disableControls([startLogicalIdOb, endLogicalIdOb, boxIdOb, endBoxIdOb, selectOb[1], selectOb[2]], true);
        return;
    }
    //新票区、单程票编码区出库，仅按数量
    //  alert(icMainTypeOb.value);
    // alert(areaIdOb.value);
    if ((areaIdOb.value == '01') ||
            (areaIdOb.value == '04') ||
            (areaIdOb.value == '05') ||
            (areaIdOb.value == '06') ||
            (areaIdOb.value == '07') ||
            (areaIdOb.value == '02' && icMainTypeOb.value == '12')
            )
    {
        //数量可见
        disableControls([outNumOb], false);
        //面值、有效期、逻辑卡号、逻辑卡号、盒号复选框不可见

        //disableControls([cardMoneyOb,cardMoneyOb1,validDateOb,startLogicalIdOb,endLogicalIdOb,selectOb[1]],true);
        disableControls([startLogicalIdOb, endLogicalIdOb, boxIdOb, endBoxIdOb, selectOb[1], selectOb[2]], true);
        //数量校验
        validControls([outNumOb], 'true');
        //逻辑卡号、盒号不校验
        validControls([startLogicalIdOb, endLogicalIdOb, boxIdOb, endBoxIdOb], 'false');
        //数量复选框选中
        selectOb[0].checked = true;


    }
    //赋值区、储值票编码区出库，按数量或逻辑卡号、盒号

    else {
        //逻辑卡号、盒号不可见
        disableControls([startLogicalIdOb, endLogicalIdOb, boxIdOb, endBoxIdOb], true);

        //数量、面值、有效期、逻辑卡号复选框、盒号复选框可见
        //disableControls([cardMoneyOb,cardMoneyOb1,validDateOb,outNumOb,selectOb[1]],false);
        disableControls([outNumOb, selectOb[1], selectOb[2]], false);
        // alert(selectOb[0].checked+','+selectOb[1].checked+','+selectOb[2].checked);
        //数量复选框选中
        if (selectOb[0].checked == true) {
            //逻辑卡号、盒号不可见
            disableControls([startLogicalIdOb, endLogicalIdOb, boxIdOb, endBoxIdOb], true);
            //数量可见
            disableControls([outNumOb], false);

            //数量、面值校验

            validControls([outNumOb, cardMoneyOb], 'true');
            //逻辑卡号、盒号不校验
            validControls([startLogicalIdOb, endLogicalIdOb, boxIdOb], 'false');

            //数量复选框选中
            selectOb[0].checked = true;
        }
        //逻辑卡号复选框选中
        if (selectOb[1].checked == true) {
            //数量、盒号不可见
            disableControls([outNumOb, boxIdOb, endBoxIdOb], true);
            //逻辑卡号可见
            disableControls([startLogicalIdOb, endLogicalIdOb], false);


            //逻辑卡号校验
            validControls([startLogicalIdOb, endLogicalIdOb], 'true');
            //数量、盒号不校验
            validControls([outNumOb, boxIdOb], 'false');

            //逻辑卡号复选框选中
            selectOb[1].checked = true;
        }
        //盒号复选框选中
        if (selectOb[2].checked == true) {
            //数量、逻辑卡号不可见

            disableControls([outNumOb, startLogicalIdOb, endLogicalIdOb], true);
            //盒号可见
            disableControls([boxIdOb, endBoxIdOb], false);


            //起始盒号校验
            validControls([boxIdOb], 'true');
            //数量、逻辑卡号不校验

            validControls([outNumOb, startLogicalIdOb, endLogicalIdOb], 'false');

            //逻辑卡号复选框选中
            selectOb[2].checked = true;
        }

    }


}
function disableControls(controlObs, disabled) {
    for (i = 0; i < controlObs.length; i++) {
        controlObs[i].disabled = disabled;
    }
}

function disableFormControls(formName, controlObs, disabled) {
    for (i = 0; i < controlObs.length; i++) {
        document.forms[formName].getElementsByTagName('input')[controlObs[i]].disabled = disabled;
    }
}
//add by zhongzq 20170805
function disableFormControlsById(controlObs, disabled) {
    for (i = 0; i < controlObs.length; i++) {
         ob =document.getElementById(controlObs[i]) ;
        if(ob != null ){
            ob.setAttribute('disabled',disabled);
        }
    }
}

function validControls(controlObs, required) {
//    alert("required:"+required);
    for (i = 0; i < controlObs.length; i++) {
//        alert(controlObs[i].name+"||"+controlObs[i].require);
//        controlObs[i].require = required;
          controlObs[i] .setAttribute("require",required);//modify by zhongzq 20170819

    }
}
function validControlsByName(formName, controlNames, required) {
    var ob;
    for (i = 0; i < controlNames.length; i++) {
        //alert(formName+':'+controlNames[i]);
        ob =document.forms[formName].getElementsByTagName('input')[controlNames[i]] ;
        if(ob ==null || ob =='undefined'){
            ob ==document.forms[formName].getElementsByTagName('select')[controlNames[i]];
        }
        if(ob != null ){
            ob.setAttribute('require',required);
        }
    }
}



//数量与逻辑卡号互斥
function controlByRadio() {
    //alert(event.srcElement.value);
    var frm = document.forms['detailOp'];
    
    var outNumOb = frm.getElementsByTagName('input')['d_lendQuantity'];
    var startLogicalIdOb = frm.getElementsByTagName('input')['d_startLogicalId'];
    var endLogicalIdOb = frm.getElementsByTagName('input')['d_endLogicalId'];

    var selectOb = frm.getElementsByTagName('input')['d_select'];

    // alert(outNumOb.value)
    if (selectOb[0].checked) {//数量
        setAvailableControls([outNumOb]);
        setUnavailableControls([startLogicalIdOb, endLogicalIdOb]);
    }
    if (selectOb[1].checked) {//逻辑卡号
        setUnavailableControls([outNumOb]);
        setAvailableControls([startLogicalIdOb, endLogicalIdOb]);
    }
}
function controlByRadioFoaBorrow() {
    //alert(event.srcElement.value);
    var frm = document.forms['detailOp'];
    var icMainTypeOb = frm.getElementsByTagName('select')['d_icMainType'];
    var areaIdOb = frm.getElementsByTagName('select')['d_areaId'];
    var outNumOb = frm.getElementsByTagName('input')['d_lendQuantity'];
    var startLogicalIdOb = frm.getElementsByTagName('input')['d_startLogicalId'];
    var endLogicalIdOb = frm.getElementsByTagName('input')['d_endLogicalId'];

    var selectOb = frm.getElementsByTagName('input')['d_select'];

    // alert(outNumOb.value)
    if (selectOb[0].checked) {//数量
        
        setAvailableControls([outNumOb]);
        setUnavailableControls([startLogicalIdOb, endLogicalIdOb]);
        outNumOb.setAttribute("require","true");
        startLogicalIdOb.setAttribute("require","false");
        endLogicalIdOb.setAttribute("require","false");
        

    }
    if (selectOb[1].checked) {//逻辑卡号
        setUnavailableControls([outNumOb]);
        setAvailableControls([startLogicalIdOb, endLogicalIdOb]);
        outNumOb.setAttribute("require","false");
        startLogicalIdOb.setAttribute("require","true");
        endLogicalIdOb.setAttribute("require","true");
    }
   
      
}
//数量与逻辑卡号互斥
function controlByRadioForNum(formName, controlNames) {
    //alert(event.srcElement.value);
    var frm = document.forms[formName];
    var outNumOb = frm.getElementsByTagName('input')[controlNames[0]];
    var startLogicalIdOb = frm.getElementsByTagName('input')[controlNames[1]];
    var endLogicalIdOb = frm.getElementsByTagName('input')[controlNames[2]];

    var selectOb = frm.getElementsByTagName('input')['d_select'];

    // alert(outNumOb.value)
    if (selectOb[0].checked) {//数量
        setAvailableControls([outNumOb]);
        setUnavailableControls([startLogicalIdOb, endLogicalIdOb]);

    }
    if (selectOb[1].checked) {//逻辑卡号
        setUnavailableControls([outNumOb]);
        setAvailableControls([startLogicalIdOb, endLogicalIdOb]);


    }


}
//数量与逻辑卡号、盒号互斥

function controlByRadioForNumForDistribute(formName, controlNames) {
    //alert(event.srcElement.value);
    var frm = document.forms[formName];
    var outNumOb = frm.getElementsByTagName('input')[controlNames[0]];
    var startLogicalIdOb = frm.getElementsByTagName('input')[controlNames[1]];
    var endLogicalIdOb = frm.getElementsByTagName('input')[controlNames[2]];
    var boxIdOb = frm.getElementsByTagName('input')[controlNames[3]];
    var endBoxIdOb = frm.getElementsByTagName('input')[controlNames[4]];

    var selectOb = frm.getElementsByTagName('input')['d_select'];

    // alert(outNumOb.value)
    if (selectOb[0].checked) {//数量
        setAvailableControls([outNumOb]);
        setUnavailableControls([startLogicalIdOb, endLogicalIdOb, boxIdOb, endBoxIdOb]);

    }
    if (selectOb[1].checked) {//逻辑卡号
        setUnavailableControls([outNumOb, boxIdOb, endBoxIdOb]);
        setAvailableControls([startLogicalIdOb, endLogicalIdOb]);


    }
    if (selectOb[2].checked) {//盒号
        setUnavailableControls([outNumOb, startLogicalIdOb, endLogicalIdOb]);
        setAvailableControls([boxIdOb]);
        setAvailableControlsNoCheck([endBoxIdOb]);


    }


}

function setAvailableControls(controls) {
    for (var i = 0; i < controls.length; i++) {
        controls[i].disabled = false;
        controls[i].require = 'true';
    }
}
function setAvailableControlsNoCheck(controls) {
    for (var i = 0; i < controls.length; i++) {
        controls[i].disabled = false;

    }
}
function setUnavailableControls(controls) {
    for (var i = 0; i < controls.length; i++) {
        controls[i].value = '';
        controls[i].disabled = true;
        controls[i].require = 'false';
    }
}
function disableRadio(formName, reasonIdName, radioName) {
    var reasonIdOb = document.forms[formName].getElementsByTagName('select')[reasonIdName];
    var radioObs = document.getElementsByName(radioName);

    var radioOb;
    var disabled = true;
    if (reasonIdOb.value == '17')
        disabled = true;
    else
        disabled = false;
    for (i = 0; i < radioObs.length; i++) {
        radioOb = radioObs[i];

        if (radioOb.checked)
            radioOb.checked = false;
        radioOb.disabled = disabled;



        //alert(radioOb.disabled);
    }
}

function controlRadios(formName, outNum, startLogicalId, endLogicalId, controls) {
    radioObs = document.getElementsByName(controls[0]);
    outNumOb = document.forms[formName].getElementsByTagName('input')[controls[1]];
    startLogicalIdOb = document.forms[formName].getElementsByTagName('input')[controls[2]];
    endLogicalIdOb = document.forms[formName].getElementsByTagName('input')[controls[3]];
    if (startLogicalId != '' && endLogicalId != '') {//按逻辑卡号
        radioObs[1].checked = true;
        radioObs[0].checked = false;
        outNumOb.value = '';
    } else {//按数量

        radioObs[0].checked = true;
        radioObs[1].checked = false;
        startLogicalIdOb.value = '';
        endLogicalIdOb.value = '';
    }
}
function controlLineStationForDistribute(formName, controlObs) {
    var reasonId = document.forms[formName].getElementsByTagName('select')['d_reasonId'].value;
    // alert(reasonId);
    disableFormControls(formName, controlObs, true);
    /*
     if(reasonId =='17'){
     disableFormControls(formName,controlObs,false)
     }
     else{
     disableFormControls(formName,controlObs,true);
     }
     */
}
//控制复选框的选择状态及其控制的文本内容初始化

function controlRadiosForDistribute(formName, outNum, startLogicalId, endLogicalId, boxId, controls) {
    // alert(outNum+':'+startLogicalId+':'+endLogicalId+':'+boxId);
    radioObs = document.getElementsByName(controls[0]);
    outNumOb = document.forms[formName].getElementsByTagName('input')[controls[1]];
    startLogicalIdOb = document.forms[formName].getElementsByTagName('input')[controls[2]];
    endLogicalIdOb = document.forms[formName].getElementsByTagName('input')[controls[3]];
    boxIdOb = document.forms[formName].getElementsByTagName('input')[controls[4]];
    endBoxIdOb = document.forms[formName].getElementsByTagName('input')[controls[5]];
    if (startLogicalId != '' && endLogicalId != '') {//按逻辑卡号
        radioObs[1].checked = true;
        radioObs[0].checked = false;
        radioObs[2].checked = false;
        outNumOb.value = '';
        boxIdOb.value = '';
        endBoxIdOb.value = '';
    } else {//按盒号

        if (boxId != '') {
            radioObs[2].checked = true;
            radioObs[0].checked = false;
            radioObs[1].checked = false;
            outNumOb.value = '';
            startLogicalIdOb.value = '';
            endLogicalIdOb.value = '';

        } else {//按数量

            radioObs[0].checked = true;
            radioObs[1].checked = false;
            radioObs[2].checked = false;

            startLogicalIdOb.value = '';
            endLogicalIdOb.value = '';
            boxIdOb.value = '';
            endBoxIdOb.value = '';
        }
    }
}
function setDetailBillNo(formName, billNoName, queryConditionName) {
    document.forms[formName].getElementsByTagName('input')[billNoName].value = document.forms[formName].getElementsByTagName('input')[queryConditionName].value
}

function controlByRecordFlagForInit(formName, controlNames, disabed) {
   
    
    var recordFlag = document.forms[formName].getElementsByTagName('input')['billRecordFlag'].value;
     alert(recordFlag);
    if (recordFlag == null || recordFlag == '')
        return;
    if (recordFlag != '3')
        disableFormControls(formName, controlNames, true);
}

function controlByRestrictFlag(formName, controlNames) {
    var frm = document.forms[formName];
    var restrictFlagOb = frm.getElementsByTagName('select')['d_restrictFlag'];



    for (i = 0; i < controlNames.length; i++) {
        if (restrictFlagOb.value == '1') {
            frm.getElementsByTagName('select')[controlNames[i]].disabled = false;
            if (controlNames[i].name == 'd_lineId')
                frm.getElementsByTagName('select')[controlNames[i]].require = 'true';
        } else {
            frm.getElementsByTagName('select')[controlNames[i]].disabled = true;
            frm.getElementsByTagName('select')[controlNames[i]].require = 'false';
            frm.getElementsByTagName('select')[controlNames[i]].value = '';
        }
    }


}

function setItemByLine(tableId, lineId) {
    //alert('a');
    var tbOb = document.getElementById(tableId);
    var lineOb = document.getElementById(lineId);
    var lineId = lineOb.value;
    var trLineId;
    var rowCount = tbOb.rows.length;
    var rowOb;
    var idx;
    //alert(lineId);
    for (i = 0; i < rowCount; i++)
    {
        rowOb = tbOb.rows[i];
        //modify by zhongzq 20170808 去掉:
//        idx = rowOb.id.indexOf(':');
//        if (idx == -1)
//            continue;
        if (lineId == '-1')
        {
            rowOb.style.display = '';
            continue;
        }
        //modify by zhongzq 20170808
//        trLineId = rowOb.id.substring(0, idx);
          trLineId = rowOb.id;
        if (trLineId == lineId)
            rowOb.style.display = '';
        else
            rowOb.style.display = 'none';

    }
}
function setVisabledByCardType(formName, icMainType, mainTypeValues, controlNames, values) {
    var frm = document.forms[formName];
    var icMainTypeValue = frm.getElementsByTagName('select')[icMainType].value;
    var index = -1;
    var i;
    // alert("icMainTypeValue="+icMainTypeValue);


    for (i = 0; i < mainTypeValues.length; i++) {
        if (icMainTypeValue == mainTypeValues[i]) {
            index = i;
            break;
        }
    }
    // alert("index="+index);
    if (index == -1)
        return;


    for (i = 0; i < controlNames.length; i++) {
        //alert("values[index]="+values[index]);
        frm.getElementsByTagName('select')[controlNames[i]].disabled = values[index];
    }
}
//乘次票子票卡不设控制 20120823 luojun
//modify by liangminglong 2013-11-16
/*
 function controlDisableForTctAtt(formName,workTypeName,cardMainTypeName,controlNames){
 //alert('a');
 var frm = document.forms[formName];
 var workType = frm.getElementsByTagName('select')[workTypeName].value;
 var cardMainType=frm.getElementsByTagName('select')[cardMainTypeName].value;
 // alert('worktype='+workType+' cardSubType='+cardSubType);
 if(((workType=='00'||workType=='01'||workType=='02')&&cardMainType=='40')||((workType=='00'||workType=='01'||workType=='02')&&cardMainType=='12'))
 disableFormControls(formName,controlNames,false);
 else
 disableFormControls(formName,controlNames,true);
 
 }
 */
//乘次票子票卡不设控制 20120823 luojun
function controlDisableForTctAtt(formName, workTypeName, cardMainTypeName, controlNames) {
    //alert('a');
    var frm = document.forms[formName];
    var workType = frm.getElementsByTagName('select')[workTypeName].value;
    var cardMainType = frm.getElementsByTagName('select')[cardMainTypeName].value;
    // alert('worktype='+workType+' cardSubType='+cardSubType);
    if (workType == '01' && cardMainType == '40')
        disableFormControls(formName, controlNames, false);
    else
        disableFormControls(formName, controlNames, true);

}

//乘次票子票卡不设控制 20120823 luojun
function controlEnableForTctAtt(formName, workTypeName, cardMainTypeName, controlNames) {
    //alert('a');
    var frm = document.forms[formName];
    var workType = frm.getElementsByTagName('select')[workTypeName].value;
    var cardMainType = frm.getElementsByTagName('select')[cardMainTypeName].value;
    // alert('worktype='+workType+' cardSubType='+cardSubType);
    if (workType == '01' && cardMainType == '40')
        disableFormControls(formName, controlNames, true);
    else
        disableFormControls(formName, controlNames, false);

}
function setValidDefaultValue(formName, workTypeName, cardMainTypeName, cardSubTypeName, validDateName) {
    var frm = document.forms[formName];
    var workTypeOb = frm.getElementsByTagName('select')[workTypeName];
    var cardMainTypeOb = frm.getElementsByTagName('select')[cardMainTypeName];
    var cardSubTypeOb = frm.getElementsByTagName('select')[cardSubTypeName];
    var validDateOb = frm.getElementsByTagName('input')[validDateName];
    /*
     初始化或重编码：
     单程票：当前日期 提示值：无
     
     储值票：当前日期 提示值：无
     
     乘次票：当前日期 提示值：无
     
     */
    if (workTypeOb.value == '00' || workTypeOb.value == '02') {
        validDateOb.value = '';
        return;
    }
    /*
     预赋值
     
     单程票：输入日期  提示值：无
     
     储值票：当前日期+有效期天数 提示值：当前日期+有效期天数
     
     乘次票：当前日期+有效期天数 提示值：当前日期+有效期天数
     
     */
    if (workTypeOb.value == '01') {
        if (cardMainTypeOb.value == '12') {
            validDateOb.value = '';
            return;
        }
        //alert("进入消息1");
        if (cardMainTypeOb.value == '1' || cardMainTypeOb.value == '40') {
            //alert("进入消息");
            validDateOb.value = getValidDateForSVT(cardMainTypeOb.value, cardSubTypeOb.value);
            return;
        }
    }
    /*
     注销：
     
     单程票：无
     
     储值票：无
     乘次票：无
     
     */
    if (workTypeOb.value == '03') {
        validDateOb.value = '';
    }

}
function getFormatDate(dateOb) {
    //var cur = new Date();
    var year = dateOb.getFullYear();
    var month = dateOb.getMonth() + 1;
    var date = dateOb.getDate();

    var syear = '' + year;
    var smonth = '' + month;
    var sdate = '' + date;
    if (month < 10)
        smonth = '0' + smonth;
    if (date < 10)
        sdate = '0' + sdate;
    return syear + '-' + smonth + '-' + sdate;


}
function getValidDateForSVT(cardMainTye, cardSubType) {
    var cur = new Date();
    var miliValue = cur.getTime();
    //alert(miliValue);
    var validDays = getValidDays(cardMainTye, cardSubType);
    //alert(validDays);
    if (validDays == 0)
        return '';

    var miliValid = miliValue + validDays * 24 * 3600 * 1000;
    return getFormatDate(new Date(miliValid));

}
function getValidDays(cardMainTye, cardSubType) {
    var validDays = document.getElementById('validDays').value;
    //alert(validDays);
    var validDaysArray = validDays.split('#');
    var idx;
    var key;
    var value;
    var findKey = cardMainTye + '_' + cardSubType;
    for (i = 0; i < validDaysArray.length; i++) {
        idx = validDaysArray[i].indexOf('=');
        key = validDaysArray[i].substring(0, idx);
        value = validDaysArray[i].substring(idx + 1);
        if (key == findKey)
            return value;

    }
    //alert('没有找到票卡'+findKey+'的有效期天数');
    return 0;


}
function restoreControlDefaultValue(formName, controlnames, defaultValue) {
    var frm = document.forms[formName];
    for (i = 0; i < controlnames.length; i++) {
        frm.getElementsByTagName('select')[controlnames[i]].value = defaultValue;
    }
}

function controlLineStationByMode(formName, modeName, lineIdName, stationIdName, lineIdExitName, stationIdExitName) {

    var frm = document.forms[formName];
    var obLineId = frm.getElementsByTagName('select')[lineIdName];

    var obStationId = frm.getElementsByTagName('select')[stationIdName];
    var obLineIdExit = frm.getElementsByTagName('select')[lineIdExitName];

    var obStationIdExit = frm.getElementsByTagName('select')[stationIdExitName];

    var obMode = frm.getElementsByTagName('select')[modeName];

    //不限制

    if (obMode.value == null || obMode.value == '' || obMode.value == '000') {
        //恢复默认校验项

        validControlsByName(formName, [lineIdName, lineIdExitName], 'false');
        //设置为缺省值:进出线路车站
        restoreControlDefaultValue(formName, [lineIdName, stationIdName, lineIdExitName, stationIdExitName], '');
        //灰选:进出线路车站
        disableFormControls(formName, [lineIdName, stationIdName, lineIdExitName, stationIdExitName], 'true');
        return;
    }

    //限制进站

    if (obMode.value == '001') {

        //恢复默认校验项

        validControlsByName(formName, [lineIdName, lineIdExitName], 'false');

        //设置为缺省值:出线路车站

        restoreControlDefaultValue(formName, [lineIdExitName, stationIdExitName], '');

        //灰选:出站线路车站 亮选：进站线路车站
        disableFormControls(formName, [lineIdExitName, stationIdExitName], 'true');
        disableFormControls(formName, [lineIdName, stationIdName], '');

        obLineId.require = 'true';
        return;
    }

    //限制出站
    if (obMode.value == '002') {
        //恢复默认校验项

        validControlsByName(formName, [lineIdName, lineIdExitName], 'false');
        //设置为缺省值:进站线路车站
        restoreControlDefaultValue(formName, [lineIdName, stationIdName], '');
        //灰选:进站线路车站 亮选：出站线路车站
        disableFormControls(formName, [lineIdExitName, stationIdExitName], '');
        disableFormControls(formName, [lineIdName, stationIdName], 'true');
        obLineIdExit.require = 'true';

        return;
    }
    //限制进出站

    if (obMode.value == '003') {
        //亮选:进出线路车站
        disableFormControls(formName, [lineIdName, stationIdName, lineIdExitName, stationIdExitName], '');

        obLineId.require = 'true';
        obLineIdExit.require = 'true';

        return;
    }

}
//add by zhongzq 20170805
function controlLineStationByModeById(formName, modeName, lineIdName, stationIdName, lineIdExitName, stationIdExitName) {
    var frm = document.forms[formName];
    var obLineId = frm.getElementsByTagName('select')[lineIdName];
    var obStationId = frm.getElementsByTagName('select')[stationIdName];
    var obLineIdExit = frm.getElementsByTagName('select')[lineIdExitName];
    var obStationIdExit = frm.getElementsByTagName('select')[stationIdExitName];
    var obMode = frm.getElementsByTagName('select')[modeName];
    //不限制
    if (obMode.value == null || obMode.value == '' || obMode.value == '000') {
        //恢复默认校验项
        validControlsByName(formName, [lineIdName, lineIdExitName], 'false');
        //设置为缺省值:进出线路车站
        restoreControlDefaultValue(formName, [lineIdName, stationIdName, lineIdExitName, stationIdExitName], '');
        //灰选:进出线路车站
        disableFormControlsById([lineIdName, stationIdName, lineIdExitName, stationIdExitName], 'true');
        return;
    }
    //限制进站
    if (obMode.value == '001') {
        //恢复默认校验项
        validControlsByName(formName, [lineIdName, lineIdExitName], 'false');
        //设置为缺省值:出线路车站
        restoreControlDefaultValue(formName, [lineIdExitName, stationIdExitName], '');
        //灰选:出站线路车站 亮选：进站线路车站
        disableFormControlsById([lineIdExitName, stationIdExitName], 'true');
        disableFormControlsById([lineIdName, stationIdName], '');
        obLineId.require = 'true';
        return;
    }
    //限制出站
    if (obMode.value == '002') {
        //恢复默认校验项
        validControlsByName(formName, [lineIdName, lineIdExitName], 'false');
        //设置为缺省值:进站线路车站
        restoreControlDefaultValue(formName, [lineIdName, stationIdName], '');
        //灰选:进站线路车站 亮选：出站线路车站
        disableFormControlsById([lineIdExitName, stationIdExitName], '');
        disableFormControlsById([lineIdName, stationIdName], 'true');
        obLineIdExit.require = 'true';
        return;
    }
    //限制进出站
    if (obMode.value == '003') {
        //亮选:进出线路车站
        disableFormControlsById([lineIdName, stationIdName, lineIdExitName, stationIdExitName], '');
        obLineId.require = 'true';
        obLineIdExit.require = 'true';
        return;
    }
}
function controlLineStationByCardTypeAndWorkType(formName, workTypeName, cardMainType,
        lineIdName, stationIdName) {
    var frm = document.forms[formName];
    var obWorkType = frm.getElementsByTagName('select')[workTypeName];
    var obCardMainType = frm.getElementsByTagName('select')[cardMainType];
    var obLineId = frm.getElementsByTagName('select')[lineIdName];

    var obStationId = frm.getElementsByTagName('select')[stationIdName];


    //alert(obWorkType.value);
    //alert(obWorkType.value);
    if (obWorkType.value == '01' && obCardMainType.value == '12') {
        //亮选：进站线路车站
        disableFormControls(formName, [lineIdName, stationIdName], '');
    } else
    {
        //灰选：进站线路车站
        disableFormControls(formName, [lineIdName, stationIdName], 'true');
    }

}

//由卡类型、卡票区控制模式的可见性及不可见，恢复默认值

//20120828 luojun 去了是否限制本站使用 条件 && restrictOb.value=='1'
function controlByCardTypeAndAreaForMode() {
    var frm = document.forms['detailOp'];
    var icMainTypeOb = frm.getElementsByTagName('select')['d_icMainType'];
    var areaIdOb = frm.getElementsByTagName('select')['d_areaId'];
    var modeOb = frm.getElementsByTagName('select')['d_mode'];
    //var restrictOb = frm.getElementsByTagName('select')['d_restrictFlag'];
    //多日票、赋值区
    if ((icMainTypeOb.value == '40' && areaIdOb.value == '03')) {

        modeOb.disabled = false;
    } else {
        modeOb.value = '000';
        modeOb.disabled = true;
    }
}
//add by liangminglong 2013-10-29
function controlToCardMoney() {
    var frm = document.forms['detailOp'];
    var icCardMoneyDiv = frm.getElementsByTagName('select')['CardMoneyDiv'];
    var icCardMoney = frm.getElementsByTagName('select')['d_cardMoney'];
    var icCardMoney1 = frm.getElementsByTagName('select')['d_cardMoney1'];
    var areaIdOb = frm.getElementsByTagName('select')['d_areaId'];
    if (areaIdOb.value == '02') {
        icCardMoney.value = '0';
        icCardMoney1.value = '000';
        icCardMoneyDiv.disabled = true;
    } else {
        icCardMoneyDiv.disabled = false;
    }
}
//add by zhongzq 20170804
function controlToCardMoneyByDefualt() {
    var icCardMoney = document.getElementById('d_cardMoney');
    var icCardMoney1 = document.getElementById('d_cardMoney1');
    var areaIdOb = document.getElementById('d_areaId');
    if (areaIdOb.value == '02') {
        icCardMoney.value = '0';
        for(var i = 0; i < icCardMoney1.options.length;i++){
//            alert(icCardMoney1.options[i].value);
            if(icCardMoney1.options[i].value =='0'||icCardMoney1.options[i].value =='000'){
                icCardMoney1.options[i].selected = true;  
//                alert("icCardMoney1.options[i].selected:"+i);
                 break;  
            }
        }
        
        icCardMoney.disabled = true;
        icCardMoney1.disabled = true;
    } else {
         icCardMoney.disabled = false;
        icCardMoney1.disabled = false;
    }
}

function controlByAreaForProduce() {
    var frm = document.forms['detailOp'];
    var outReason = frm.getElementsByTagName('select')['d_outReason'].value;
    var outMainType = frm.getElementsByTagName('select')['d_cardMainType'].value;
    // alert(outReason);
    //alert(outMainType);
    if (outReason == '02' || outReason == '07') {
        frm.getElementsByTagName('select')['d_lineId'].disabled = true;
        frm.getElementsByTagName('select')['d_stationId'].disabled = true;
        frm.getElementsByTagName('select')['d_exitLineId'].disabled = true;
        frm.getElementsByTagName('select')['d_exitStationId'].disabled = true;
        //frm.getElementsByTagName('select')['d_mode'].disabled=true;
        if (outMainType == '9') {
            // alert(outMainType);
            frm.getElementsByTagName('select')['d_mode'].disabled = false;
        }
    }
}

function controlByCardTypeAndAreaForModeForBorrow() {
    var frm = document.forms['detailOp'];
    var icMainTypeOb = frm.getElementsByTagName('select')['d_icMainType'];
    var areaIdOb = frm.getElementsByTagName('select')['d_areaId'];
    var modeOb = frm.getElementsByTagName('select')['d_mode'];
    //var restrictOb = frm.getElementsByTagName('select')['d_restrictFlag'];
    //多日票、赋值区
    if (icMainTypeOb.value == '40' && areaIdOb.value == '03') {

        modeOb.disabled = false;
    } else {
        modeOb.value = '000';
        modeOb.disabled = true;
    }
}

function controlByAreaIdForBorrow() {
    var frm = document.forms['detailOp'];
    var icMoneyOb = frm.getElementsByTagName('input')['d_cardMoney'];
    var icMoneyOb1 = frm.getElementsByTagName('select')['d_cardMoney1'];
    var areaIdOb = frm.getElementsByTagName('select')['d_areaId'];
    //var restrictOb = frm.getElementsByTagName('select')['d_restrictFlag'];
    //多日票、赋值区
    if (areaIdOb.value == '03') {
        icMoneyOb.disabled = false;
        icMoneyOb1.disabled = false;
    } else {
        icMoneyOb.disabled = true;
        icMoneyOb1.disabled = true;
    }
}





function controlCardMoneyWorkType(formName, workTypeName, cardMoney, cardMoney1) {
    var frm = document.forms[formName];
    var obWorkType = frm.getElementsByTagName('select')[workTypeName];
    var cardMoney = frm.getElementsByTagName('input')[cardMoney];
    var cardMoney1 = frm.getElementsByTagName('select')[cardMoney1];
    //alert(obWorkType.value);
    //alert(cardMoney.value);
    if (obWorkType.value == '00' || obWorkType.value == '02') {
        cardMoney.disabled = true;
        cardMoney1.disabled = true;
        cardMoney.value = 0;
    }
}


function controlOldNewFlagWorkType(formName, workTypeName, oldNewFlag) {
    var frm = document.forms[formName];
    var obWorkType = frm.getElementsByTagName('select')[workTypeName];
    var oldNewFlag = frm.getElementsByTagName('select')[oldNewFlag];
    //alert(obWorkType.value);
    //alert(cardMoney.value);
    if (obWorkType.value == '01' || obWorkType.value == '03') {
        oldNewFlag.disabled = true;
    } else {
        oldNewFlag.disabled = false;
    }
}

//add by liangminglong 2013-11-23
function controlCradAvaDaysByCardType(formName, mainName, avaDayName) {
    var frm = document.forms[formName];
    var mainName = frm.getElementsByTagName('select')[mainName];
    var avaDayName = frm.getElementsByTagName('input')[avaDayName];
    //alert(mainName.value);
    //alert(avaDayName.value);
    if (mainName.value == '40') {
        avaDayName.disabled = false;
    }
}

//20151207 add by mqf
function clearFormControls(formName, controlNames) {
    for (i = 0; i < controlNames.length; i++)
        document.forms[formName].getElementsByTagName('input')[controlNames[i]].value = '';

}

//20151230 add by mqf
function clearFormControlsByMainName(formName, mainName, controlNames) {
    var mainName = document.forms[formName].getElementsByTagName('input')[mainName];
    if (mainName.value == null || mainName.value == '') {
        for (i = 0; i < controlNames.length; i++) {
            var controlName = document.forms[formName].getElementsByTagName('input')[controlNames[i]];
            controlName.value = '';

            var j = -1;
            if (controlName.options.length > 1) {
                for (j = controlName.options.length - 1; j >= 1; j--)
                    controlName.remove(j);
            }
        }
    }

}

//20160324 add by mqf 数量与盒号互斥(不需要逻辑卡号)
function controlByRadioForNumForMove(formName, controlNames) {
    //alert(event.srcElement.value);
    var frm = document.forms[formName];
    var outNumOb = frm.getElementsByTagName('input')[controlNames[0]];
//    var startLogicalIdOb =  frm.getElementsByTagName('input')[controlNames[1]];
//    var endLogicalIdOb =  frm.getElementsByTagName('input')[controlNames[2]];
    var boxIdOb = frm.getElementsByTagName('input')[controlNames[1]];
    var endBoxIdOb = frm.getElementsByTagName('input')[controlNames[2]];

    var selectOb = frm.getElementsByTagName('input')['d_select'];

    // alert(outNumOb.value)
    if (selectOb[0].checked) {//数量
        setAvailableControls([outNumOb]);
        setUnavailableControls([boxIdOb, endBoxIdOb]);

    }
//    if(selectOb[1].checked){//逻辑卡号
//        setUnavailableControls([outNumOb,boxIdOb,endBoxIdOb]);
//        setAvailableControls([startLogicalIdOb,endLogicalIdOb]);
//
//	 	
//    }
    if (selectOb[1].checked) {//盒号
        setUnavailableControls([outNumOb]);
        setAvailableControls([boxIdOb, endBoxIdOb]);
//        setAvailableControlsNoCheck([endBoxIdOb]);


    }


}


/*
 function controlValidDaysByCardType(formName,areaId,validDay){
 var frm = document.forms[formName];
 var areaId =frm.getElementsByTagName('select')[areaId];
 var validDay =frm.getElementsByTagName('input')[validDay];
 //alert(areaId.value);
 //alert(validDay.value);
 //alert(avaDayName.value);
 
 if(areaId.value =='03' && validDay.value =='' ){
 alert("有效期为空！");
 }
 
 }
 */

//新增、修改时控制迁移数量、始盒号、止盒号状态

function disableMoveControls(formName, quantityName, startBoxIdName, endBoxIdName, radioSelectName) {
    var frm = document.forms[formName];
    var quantity = frm.getElementsByTagName('input')[quantityName];
    var startBoxId = frm.getElementsByTagName('input')[startBoxIdName];
    var endBoxId = frm.getElementsByTagName('input')[endBoxIdName];
    var radioSelect = frm.getElementsByTagName('input')[radioSelectName];

    if ((quantity.value == null || quantity.value == '')
            && (startBoxId.value == null || startBoxId.value == '')
            && (endBoxId.value == null || endBoxId.value == '')) {
        quantity.disabled = true;
        startBoxId.disabled = true;
        endBoxId.disabled = true;

        radioSelect[0].checked = false;
        radioSelect[1].checked = false;

    } else if (startBoxId.value != null && startBoxId.value != ''
            && endBoxId.value != null && endBoxId.value != '') {
//            quantity.disabled = true;
//            startBoxId.disabled = false;
//            endBoxId.disabled = false;
        //按盒号

        setAvailableControls([startBoxId, endBoxId]);
        setUnavailableControls([quantity]);

        radioSelect[1].checked = true;
    } else if (quantity.value != null && quantity.value != '') {
//            quantity.disabled = false;
//            startBoxId.disabled = true;
//            endBoxId.disabled = true;
        //按数量

        setAvailableControls([quantity]);
        setUnavailableControls([startBoxId, endBoxId]);

        radioSelect[0].checked = true;
    }

}


function controlMoveRadio(formName, icMainTypeName, icSubTypeName, areaIdName,
        quantityName, startBoxIdName, endBoxIdName, radioSelectName) {

    var frm = document.forms[formName];
    var icMainType = frm.getElementsByTagName('select')[icMainTypeName];
    var icSubType = frm.getElementsByTagName('select')[icSubTypeName];
    var areaId = frm.getElementsByTagName('select')[areaIdName];

    var quantity = frm.getElementsByTagName('input')[quantityName];
    var startBoxId = frm.getElementsByTagName('input')[startBoxIdName];
    var endBoxId = frm.getElementsByTagName('input')[endBoxIdName];
    var radioSelect = frm.getElementsByTagName('input')[radioSelectName];
//        alert("icMainType:"+icMainType.value+"icSubType:"+icSubType.value+"areaId:"+areaId.value);
    var flag = 0;
    if (areaId.value == '02' || areaId.value == '03') {
        if (icMainType.value == '12' && areaId.value == '03') {
            flag = 1;
        }
        if (icMainType.value == '1' || icMainType.value == '2' || icMainType.value == '40'
                || icMainType.value == '7' || icMainType.value == '8') {
            flag = 1;
        }
    }
    if (flag == 0) {
        //按数量

//            quantity.disabled = false;
//            startBoxId.disabled = true;
//            endBoxId.disabled = true;
        setAvailableControls([quantity]);
        setUnavailableControls([startBoxId, endBoxId]);

        radioSelect[0].checked = true;
    } else {
//            quantity.disabled = true;
//            startBoxId.disabled = false;
//            endBoxId.disabled = false;
        //按盒号

        setAvailableControls([startBoxId, endBoxId]);
        setUnavailableControls([quantity]);

        radioSelect[1].checked = true;
    }

}

//20160330 add by mqf
function deleteControlsOptions(formName, controlName, controlValue) {
    var opt = document.forms[formName].getElementsByTagName('input')[controlName];
    var j = -1;
    if (opt.options.length > 1) {
        for (j = opt.options.length - 1; j >= 1; j--) {
            if (opt[j].value == controlValue) {
                opt.remove(j);
            }
        }
    }

}

//控件失效
function displayOrderNoSelect(formName,name,flag){
    document.forms[formName].elements[name].disabled=true;
}

//20160330 checkBoxes[i].checked不选时设置true
function controlByFlagForProduceOut(formName, controlName) {
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
                document.forms[formName].getElementsByTagName('input')[controlName].disabled = true;
            } else
                document.forms[formName].getElementsByTagName('input')[controlName].disabled = false;
            break;
        }

    }
    if (cc == 0) {
        document.forms[formName].getElementsByTagName('input')[controlName].disabled = true;
    }
}











