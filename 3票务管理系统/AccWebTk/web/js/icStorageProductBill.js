
//******************author: wujun   *****************************
function BrWindowForDetails(theURL, winName, featur) {
    window.open(theURL, winName, featur);
//window.open(theURL,winName,featur<p align="left"></p>es);
}

function  disableAudit(formName, eName, status) {
    //var os = document.getElementsById(eName);
    //var ok = document.getElementByName(eName);
    var ob = document.forms[formName].getElementsByTagName('input')[eName];

    if (event.srcElement.checked) {
        if (status != '3') {
            ob.disabled = true;
            //ok.disabled=true;
        }
    }

    if (event.srcElement.checked == false) {
        if (status != 3) {
            ob.diabled = true;
        }
    }
}

//通过单据状态控制多个操作按钮状态

//20160330 checkBoxes[i].checked不选时不作任何设置，icStorageIn.js不存在这个问题

function controlsByFlag(formName, controlNames) {
    //	    alert(event.srcElement.checked);
    //alert("111");
    var checkBoxes = document.getElementsByName("rectNo");
    if (checkBoxes == null)
        return;
    if (checkBoxes.length == 0)
        return
    //alert("checkbox.lenth="+checkBoxes.length);
    for (i = 0; i < checkBoxes.length; i++) {
        // alert(checkBoxes[i].getAttribute('flag'));
        if (checkBoxes[i].checked ) {
            disableControlsByFlag(formName, controlNames, checkBoxes[i].getAttribute('flag'));
            break;
        }
    }
}
//20171206 zhongzq 控制判断每一条记录 如果含有一条已审核的则控制不可用状态
function controlsDisableForMulRecords(formName, controlNames) {
    //	    alert(event.srcElement.checked);
//    alert("111");
    var checkBoxes = document.getElementsByName("rectNo");
    if (checkBoxes == null)
        return;
    if (checkBoxes.length == 0)
        return
    //alert("checkbox.lenth="+checkBoxes.length);
    for (i = 0; i < checkBoxes.length; i++) {
//        alert(checkBoxes.length);
//         alert(checkBoxes[i].getAttribute('flag'));
        if (checkBoxes[i].checked && checkBoxes[i].getAttribute('flag')=='0') {
            disableControlsByFlag(formName, controlNames, checkBoxes[i].getAttribute('flag'));
            break;
        }
    }
}
//20170914 mqf 多选时指定按钮状态为不可用（如主单据“审核”只可以选择一条记录时才能审核），选择一条记录时通过单据状态控制按钮状态
function controlsByFlagAndMul(formName, controlNames) {
    //	    alert(event.srcElement.checked);
    var checkBoxes = document.getElementsByName("rectNo");
    var checkedNum = 0;
    if (checkBoxes == null)
        return;
    if (checkBoxes.length == 0)
        return
    //alert("checkbox.lenth="+checkBoxes.length);
        
    for (i = 0; i < checkBoxes.length; i++) {
        if (checkBoxes[i].checked ) {
            checkedNum ++;
        }
    }
//    alert('a1checkedNum'+checkedNum);
    if (checkedNum == 1) {
        
        for (i = 0; i < checkBoxes.length; i++) {
        // alert(checkBoxes[i].getAttribute('flag'));
            if (checkBoxes[i].checked && checkBoxes[i].getAttribute('flag') != '3') {
                disableControlsByFlag(formName, controlNames, checkBoxes[i].getAttribute('flag'));
                break;
            }
        }
    } else if (checkedNum > 1) {
        disableControlsByFlag(formName, controlNames, 'true');
    }
   
}

//通过单据状态控制多个操作按钮状态

function controlsByFlagForSign(formName, controlNames) {
    //	    alert(event.srcElement.checked);
    //alert("111");
    var checkBoxes = document.getElementsByName("rectNo");
    if (checkBoxes == null)
        return;
    if (checkBoxes.length == 0)
        return
    //alert("checkbox.lenth="+checkBoxes.length);
    for (i = 0; i < checkBoxes.length; i++) {
        //alert(checkBoxes[i].flag);
        if (checkBoxes[i].checked && checkBoxes[i].flag != '2') {
            disableControlsByFlag(formName, controlNames, checkBoxes[i].flag);
            break;
        }
    }
}


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

    for (i = 0; i < controlNames.length; i++) {

        if (flag == '3'){

            document.forms[formName].getElementsByTagName('input')[controlNames[i]].disabled = false;
        }
        else
        {

            document.forms[formName].getElementsByTagName('input')[controlNames[i]].disabled = true;
        }
    }
}


function  userPrivilegeWithCheckBox(formName, loginUser, recordUser, status) {

    var audit = document.forms[formName].getElementsByTagName('input')["audit"];
    var modify = document.forms[formName].getElementsByTagName('input')["modify"];
    disableUpdate(formName, 'modify', status);
    disableAudit(formName, 'audit', status);
    var a = loginUser.toString();
    var b = recordUser.toString();

    if (event.srcElement.checked) {
        if (a != b) {
            //alert("进入消息");
            audit.disabled = true;
            modify.disabled = true;
        }
    }

    if (!event.srcElement.checked) {
        //			alert("进入消息");
        audit.disabled = true;
        modify.disabled = true;
    }
}

//20170914 mqf 多选时指定按钮状态为不可用（如主单据“审核”只可以选择一条记录时才能审核），选择一条记录时通过单据状态控制按钮状态
function  userPrivilegeWithCheckBoxByMul(formName, controlNames, loginUser) {

    var checkBoxes = document.getElementsByName("rectNo");
    var checkedNum = 0;
    if (checkBoxes == null)
        return;
    if (checkBoxes.length == 0)
        return
    
    for (i = 0; i < checkBoxes.length; i++) {
        if (checkBoxes[i].checked ) {
            checkedNum ++;
        }
    }
    if (checkedNum == 1) {
        var audit = document.forms[formName].getElementsByTagName('input')["audit"];
        var modify = document.forms[formName].getElementsByTagName('input')["modify"];
        for (i = 0; i < checkBoxes.length; i++) {
        // alert(checkBoxes[i].getAttribute('flag'));
            if (checkBoxes[i].checked) {
                var flag = checkBoxes[i].getAttribute('flag');
                var recordUser = checkBoxes[i].getAttribute('es_operator').toString();
                var loginUser = loginUser.toString();
                
                disableUpdate(formName, 'modify', flag);
                disableAudit(formName, 'audit', flag);
//                alert('recordUser:'+recordUser);
                if (loginUser != recordUser) {
//                    alert("进入消息");
                    audit.disabled = true;
                    modify.disabled = true;
                }
                break;
            }
        }
    } else if (checkedNum > 1) {
        disableControlsByFlag(formName, controlNames, 'true');
    }
}



function  userPrivilegeByClickCurrentRow(formName, loginUser, recordUser, status) {

    var update = document.forms[formName].getElementsByTagName('input')["modify"];
    disableUpdate(formName, 'modify', status);
    if (recordUser != loginUser) {
        update.disabled = true;
    }
}

function setAuditEnable(formName, btn) {
    var frm = document.forms[formName].getElementsByTagName('input')[btn];
    //alert(frm);
    if (frm == null)
        return;
    frm.disabled = false;
}

function disableUpdate(formName, ename, flag) {
    var ob = document.forms[formName].getElementsByTagName('input')[ename];
    if (ob != null) {
        if (flag != 3) {
            //				alert("disableUpdate");
            ob.disabled = true;
        }
    }
}

function checkLogicalCardLength(formName, num) {
    var txt;
    var frm = document.forms[formName];
    if (frm == null)
        return;
    try {
        var len = frm.getElementsByTagName('input')['queryCardNo'].value;
        if (len.length != 0) {
            if (len.length != num)
                alert("票卡为20位整数");
        }
    } catch (err) {
        txt = "当前引用出错:\n\n"
        txt += "错误代码: " + err.description + "\n\n"
        txt += "点击OK确定:\n\n"
        alert(txt)
    }
}

function controlByRecordFlagForInit(formName, controlNames, disabed) {
  
    var recordFlag = document.forms[formName].getElementsByTagName('input')['billRecordFlag'].value;
    if (recordFlag == null || recordFlag == '')
        return;
    if (recordFlag != '3')
        disableFormControls(formName, controlNames, true);
}

function disableFormControls(formName, controlObs, disabled) {
    var ob;
    //alert(controlObs.length);
    for (i = 0; i < controlObs.length; i++) {
        ob=document.forms[formName].getElementsByTagName('input')[controlObs[i]]
        //ob = document.getElementById(controlObs[i]);
        //20180313 mqf 支付select控件
        if (ob == null) {
            ob = document.forms[formName].getElementsByTagName('select')[controlObs[i]];
        }
        if (ob == null)
            continue;
        ob.disabled = disabled;

        // document.forms[formName].all[controlObs[i]].disabled = disabled;
    }
}
//add by zhongzq 20170803
function disableFormControlsById(controlObs, disabled) {
    var ob;
    //alert(controlObs.length);
    for (i = 0; i < controlObs.length; i++) {
        ob=document.getElementById(controlObs[i]);
        //ob = document.getElementById(controlObs[i]);
        if (ob == null)
            continue;
        ob.disabled = disabled;

        // document.forms[formName].all[controlObs[i]].disabled = disabled;
    }
}

//窗体居中链接调用的方法


function openwindow(url, name, iWidth, iHeight) {
    var url;                                 //转向网页的地址;
    var name;                           //网页名称，可为空;
    var iWidth;                          //弹出窗口的宽度;
    var iHeight;                        //弹出窗口的高度;
    var iTop = (window.screen.availHeight - 30 - iHeight) / 2;       //获得窗口的垂直位置;
    var iLeft = (window.screen.availWidth - 10 - iWidth) / 2;           //获得窗口的水平位置;
    window.open(url, name, 'height=' + iHeight + ',,innerHeight=' + iHeight + ',width=' + iWidth + ',innerWidth=' + iWidth + ',top=' + iTop + ',left=' + iLeft + ',toolbar=no,menubar=no,scrollbars=auto,resizeable=no,location=no,status=no');
}

function getCheckMenu(url) {
    //	document.URL= url;
    document.location.href = url;
//document.forms[formName].submit();
}

function selectHiddenForm() {
    var del = document.getElementById("q_definemode").value;
    //alert(del);
    if (del == '1') {
        //alert("盘点");
        document.getElementById("formStyle2").style.display = "none";
        document.getElementById("formStyle1").style.display = "inline";
        document.getElementById("formStyle3").style.display = "none";
    }
    if (del == '2') {
        //alert("审核");
        document.getElementById("formStyle2").style.display = "inline";
        document.getElementById("formStyle1").style.display = "none";
        document.getElementById("formStyle3").style.display = "none";
    }
    if (del == '3') {
        document.getElementById("formStyle3").style.display = "inline";
        document.getElementById("formStyle1").style.display = "none";
        document.getElementById("formStyle2").style.display = "none";
    }
    if (del == '4') {
        document.getElementById("formStyle1").style.display = "none";
        document.getElementById("formStyle2").style.display = "none";
        document.getElementById("formStyle3").style.display = "none";
    }
}

       