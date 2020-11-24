function openwindow(url,name,iWidth,iHeight)
{
    var url;                                 //ת����ҳ�ĵ�ַ;
    var name;                           //��ҳ���ƣ���Ϊ��;
    var iWidth;                          //�������ڵĿ��;
    var iHeight;                        //�������ڵĸ߶�;
    var iTop = (window.screen.availHeight-30-iHeight)/2;       //��ô��ڵĴ�ֱλ��;
    var iLeft = (window.screen.availWidth-10-iWidth)/2;           //��ô��ڵ�ˮƽλ��;
    var spec = 'height='+iHeight+',,innerHeight='+iHeight+',width='+iWidth+',innerWidth='+iWidth+',top='+iTop+',left='+iLeft+',toolbar=no,menubar=no,scrollbars=auto,resizable=yes,location=no,status=no';
    // alert(spec);
    window.open(url,name,spec);
}
function openWindowDialog(formName,valueCtrName,jspName,moduleID,width,height){
    var path=jspName+"?ModuleID="+moduleID;
    //	alert(path);
	

    var rt=window.showModalDialog(path,'','dialogWidth:'+width+'px;dialogHeight:'+height+'px;center:yes;resizable:no;status:no;scroll:yes');
    //	alert(rt);
    if(rt!=null && rt!='undefined')
        document.forms[formName].all[valueCtrName].value = rt;

}
function openWindowDialogDevId(resluts,jspName,moduleID,width,height){
	
	
    var q_deviceType = document.getElementById("q_deviceType").value;
    var q_lineId = document.getElementById("q_lineID").value;
    var q_stationId = document.getElementById("q_stationID").value;
    var obj = new Object();
    obj.value=resluts;
	
    var path=jspName+"?ModuleID="+moduleID+"&q_deviceType="+q_deviceType+"&q_lineId="+q_lineId+"&q_stationId="+q_stationId;
    //	alert(path);
    
	

    var rt=window.showModalDialog(path,obj,'dialogWidth:'+width+'px;dialogHeight:'+height+'px;center:yes;resizable:no;status:no;scroll:yes');
    //	alert(rt);
    if(rt!=null && rt!='undefined')
        document.getElementById("device_id").value = rt;

}

function openWindowDeviceId(resluts,jspName,moduleID,width,height){
	
	
    var q_deviceType = document.getElementById("d_devTypeId").value;
    var q_lineId = document.getElementById("d_lineId").value;
    var q_stationId = document.getElementById("d_stationId").value;
    var obj = new Object();
    obj.value=resluts;
	
    var path=jspName+"?ModuleID="+moduleID+"&q_deviceType="+q_deviceType+"&q_lineId="+q_lineId+"&q_stationId="+q_stationId;
    //	alert(path);
    
	

    var rt=window.showModalDialog(path,obj,'dialogWidth:'+width+'px;dialogHeight:'+height+'px;center:yes;resizable:no;status:no;scroll:yes');
    //	alert(rt);
    if(rt!=null && rt!='undefined')
        document.getElementById("d_deviceId").value = rt;

}

function openWindowDialogDevIdQry(formName,valueCtrName,jspName,moduleID,width,height){
	
	
    var q_deviceType = document.forms[formName].all['deviceType'].value;
    var q_lineId = document.forms[formName].all['lineId'].value;
    var q_stationId = document.forms[formName].all['stationId'].value;
	
    var path=jspName+"?ModuleID="+moduleID+"&q_deviceType="+q_deviceType+"&q_lineId="+q_lineId+"&q_stationId="+q_stationId;
    //	alert(path);
	

    var rt=window.showModalDialog(path,'','dialogWidth:'+width+'px;dialogHeight:'+height+'px;center:yes;resizable:no;status:no;scroll:yes');
    //	alert(rt);
    if(rt!=null && rt!='undefined')
        document.forms[formName].all[valueCtrName].value = rt;

}
function openWindowDialogOnly(formName,jspName,moduleID,width,height){
    var path=jspName+"?ModuleID="+moduleID;
    //	alert(path);
	

    var rt=window.showModalDialog(path,'','dialogWidth:'+width+'px;dialogHeight:'+height+'px;center:yes;resizable:no;status:no;scroll:yes');
//	alert(rt);
//	if(rt!=null && rt!='undefined')
//	   document.forms[formName].all[valueCtrName].value = rt;

}
function openWindowDialogForParam(formName,valueCtrName,paramCtrNames,isChecks,msgs,paramCtrNames1,msgs1,jspName,moduleID,width,height){
    var path=jspName+"?ModuleID="+moduleID;
    var param ='';
    if(document.forms[formName].all[paramCtrNames1[0]].value != ''){
        for(var j=0;j<paramCtrNames1.length;j++){
            if(document.forms[formName].all[paramCtrNames1[j]].value == ''){
                alert(msgs1[j]+'����Ϊ��');
                return;
            }
        }
    }
    for(var i=0;i<paramCtrNames.length;i++){
        if(document.forms[formName].all[paramCtrNames[i]].value ==''&&isChecks[i]){
            alert(msgs[i]+'����Ϊ��');
            return;
        }
        param +='&'+document.forms[formName].all[paramCtrNames[i]].name+'='+document.forms[formName].all[paramCtrNames[i]].value;
    }
    path +=param;
    //alert(param);
	

    var rt=window.showModalDialog(path,'','dialogWidth:'+width+'px;dialogHeight:'+height+'px;center:yes;resizable:no;status:no;scroll:yes');
    //	alert(rt);
    if(rt!=null && rt!='undefined')
        document.forms[formName].all[valueCtrName].value = rt;

}
function openWindowDialogForParamValues(formName,valueCtrName,paramNames,paramValues,jspName,moduleID,width,height){
    var path=jspName+"?ModuleID="+moduleID;
    var param ='';

    for(var i=0;i<paramNames.length;i++){

        param +='&'+paramNames[i]+'='+paramValues[i];
    }
    path +=param;
    //alert(param);
	

    var rt=window.showModalDialog(path,'','dialogWidth:'+width+'px;dialogHeight:'+height+'px;center:yes;resizable:no;status:no;scroll:yes');
    //	alert(rt);
    if(rt!=null && rt!='undefined')
        document.forms[formName].all[valueCtrName].value = rt;

}
//ȡ��ȫѡ��ť
function unSelectAllRecordForDialog(headCheckName,name){
    var obj=document.all[headCheckName];
    headEventObject=event.srcElement
    if(headEventObject.type == "checkbox" && headEventObject.name == name)
    {
        if (!headEventObject.checked)
            obj.checked="";
    }

		

}

var rowBackGround="#FFFFFF";
function clickResultRowForDialog(formName,object){
	
    headEventObject=event.srcElement;

    while(headEventObject.tagName!=="TR"){
        headEventObject=headEventObject.parentElement;
    }
	


    //取消原选择行的信息，及保存新选择的行信息
    var oldLine=getCurrentLineValue(formName);
    if (oldLine !== ""){
        var oldObject=document.getElementById(oldLine);
        oldObject.style.background=rowBackGround;
    }
    //设置当前行��
    setCurrentLineValue(formName,headEventObject.id);
    //改变背景色
    rowBackGround=headEventObject.style.backgroundColor;
    headEventObject.style.background="#97CBFF";


}

function getCurrentLineValue(formName) {
    var frm = document.forms[formName];
    var rowSelected = frm.getElementsByTagName("input")["rowSelected"];
    if (rowSelected != null) {
        var line = rowSelected.getAttribute('value');
        if (line != "init") {
            return line;
        } else
            return "";
    } else
        return "";
}

function setCurrentLineValue(formName,lineId){
    var rowSelected=document.forms[formName].getElementsByTagName("input")["rowSelected"];
    if (rowSelected != null) rowSelected.value=lineId;
}

//2012-06-29 �豸������ѯ�����ֶ�����ʾ luojun
function controlByColFlag(formName,changeCloumn,controlNames,revControlNames){
    var frm = document.forms[formName];
    var restrictFlagOb = frm.all[changeCloumn];
    
    
    for(i=0;i<controlNames.length;i++){
        if(restrictFlagOb.value =='00'){
            frm.all[controlNames[i]].disabled = false;
            frm.all[controlNames[i]].require='true';
        }
        else{
            frm.all[controlNames[i]].disabled = true;
            frm.all[controlNames[i]].require='false';
	   			
        }
    }
		
    for(i=0;i<revControlNames.length;i++){
        if(restrictFlagOb.value =='00'){
            frm.all[revControlNames[i]].disabled = true;
            frm.all[revControlNames[i]].require='false';
	   			
	  	 		
        }
        else{
            frm.all[revControlNames[i]].disabled = false;
            frm.all[revControlNames[i]].require='true';
        }
    }

	
}

//ȫѡ��ť
function selectAllRecordForDialog(formName,headCheckName,name,listDivName,colNo){


    var obj=document.all[headCheckName];
    var count=document.forms[formName].all["rowTickedCount"];

    var listDiv = document.all[listDivName];
    var tbl = listDiv.children[0];
    if(tbl.tagName !='TABLE'){
        //		alert("tbl.tagName="+tbl.tagName);
        return;
    }

    var checkCell = null;
    var chk = null;
    for (var i=0;i<tbl.rows.length ;i++ )
    {
        if(tbl.rows[i].id =='ignore')
            continue;

        checkCell= tbl.rows[i].cells[colNo];
        if(checkCell.children ==null)
            continue;

        chk=checkCell.children[0];
        if(chk.name == null)
            continue;

        if (chk.name==name && chk.type=="checkbox")
        {
            if (obj.checked){
                chk.checked="1";
            }
            else{
                chk.checked="";
            }
        }
    }

}

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

        function controlsByFlag(formName, controlNames) {
            //	    alert(event.srcElement.checked);
//                alert("111");
            var checkBoxes = document.getElementsByName("rectNo");
            if (checkBoxes == null)
                return;
            if (checkBoxes.length == 0)
                return
            //alert("checkbox.lenth="+checkBoxes.length);
            for (i = 0; i < checkBoxes.length; i++) {
                // alert(checkBoxes[i].getAttribute('flag'));
                if (checkBoxes[i].checked) {
                    disableControlsByFlag(formName, controlNames, checkBoxes[i].getAttribute('flag'));
                    break;
                }
            }
        }

        function disableControlsByFlag(formName, controlNames, flag) {

            for (i = 0; i < controlNames.length; i++) {

                if (flag == '2') {

                    document.forms[formName].getElementsByTagName('input')[controlNames[i]].disabled = false;
                } else
                {

                    document.forms[formName].getElementsByTagName('input')[controlNames[i]].disabled = true;
                }
            }
        }

        //控制增加、删、改按钮，仅当所选记录未审核时，审核按钮才可用

        function disableControlByFlag(formName, controlName, flag) {
            //alert(flag);
            //alert(event.srcElement.checked);
            if (flag == '2')
                document.forms[formName].getElementsByTagName('input')[controlName].disabled = false;
            else
                document.forms[formName].getElementsByTagName('input')[controlName].disabled = true;
        }


        function set(formName,recordFlag) {
//            var recordFlag = document.forms[formName].getElementsByTagName('input')['flag'].value;
            if (recordFlag == '3') {
                document.getElementById('btModify').disabled = false;
                document.getElementById('btModify').className = 'NewButtonStyle';
            }
        }
        
        //20191219 ldz  控制判断每一条记录 如果含有一条已审核的则控制不可用状态
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

//20191219 ldz 多选时指定按钮状态为不可用（如主单据“审核”只可以选择一条记录时才能审核），选择一条记录时通过单据状态控制按钮状态
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
//    if (checkedNum == 1) {
//
////        for (i = 0; i < checkBoxes.length; i++) {
////         //alert(checkBoxes[i].getAttribute('flag'));
//////            if (checkBoxes[i].checked ) {
//////                disableControlsByFlagForAudit(formName, controlNames, checkBoxes[i].getAttribute('flag'));
//////                break;
//////            }
////        }
//    } else 
    if (checkedNum > 1) {
        disableControlsByFlag(formName, controlNames, 'true');
    }

}

function disableControlsByFlagForAudit(formName, controlNames, flag) {

            for (i = 0; i < controlNames.length; i++) {


                    document.forms[formName].getElementsByTagName('input')[controlNames[i]].disabled = false;
                
            }
        }


