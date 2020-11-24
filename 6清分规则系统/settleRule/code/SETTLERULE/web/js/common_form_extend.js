function openwindow(url,name,iWidth,iHeight)
 {
  var url;                                 //转向网页的地址;
  var name;                           //网页名称，可为空;
  var iWidth;                          //弹出窗口的宽度;
  var iHeight;                        //弹出窗口的高度;
  var iTop = (window.screen.availHeight-30-iHeight)/2;       //获得窗口的垂直位置;
  var iLeft = (window.screen.availWidth-10-iWidth)/2;           //获得窗口的水平位置;
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
 function openWindowDialogDevId(formName,valueCtrName,jspName,moduleID,width,height){
	
	
	var q_deviceType = document.forms[formName].all['q_deviceType'].value;
	var q_lineId = document.forms[formName].all['q_lineId'].value;
	var q_stationId = document.forms[formName].all['q_stationId'].value;
	
	var path=jspName+"?ModuleID="+moduleID+"&q_deviceType="+q_deviceType+"&q_lineId="+q_lineId+"&q_stationId="+q_stationId;
//	alert(path);
	

	var rt=window.showModalDialog(path,'','dialogWidth:'+width+'px;dialogHeight:'+height+'px;center:yes;resizable:no;status:no;scroll:yes');
//	alert(rt);
	if(rt!=null && rt!='undefined')
	   document.forms[formName].all[valueCtrName].value = rt;

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
    		  alert(msgs1[j]+'不能为空');
	    	  return;
    		}
    	}
    }
	for(var i=0;i<paramCtrNames.length;i++){
	    if(document.forms[formName].all[paramCtrNames[i]].value ==''&&isChecks[i]){
	    	alert(msgs[i]+'不能为空');
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
//取消全选按钮
function unSelectAllRecordForDialog(headCheckName,name){
	var obj=document.all[headCheckName];
	headEventObject=event.srcElement
	if(headEventObject.type == "checkbox" && headEventObject.name == name)
	{
	  if (!headEventObject.checked)
	    obj.checked="";
	}

		

}
function clickResultRowForDialog(formName,object){
	
	headEventObject=event.srcElement

	while(headEventObject.tagName!="TR"){
		headEventObject=headEventObject.parentElement
	}
	


	//取消原选择行的信息，及保存新选择的行信息
	var oldLine=getCurrentLine(formName);
	if (oldLine != ""){
		var oldObject=document.getElementById(oldLine);
		oldObject.style.background="#FFFFFF";
	}
	//设置当前行
	setCurrentLine(formName,headEventObject.id);
	//改变背景色
	headEventObject.style.background="#97CBFF";


}

//2012-06-29 设备参数查询限制字段项显示 luojun
function controlByColFlag(formName,changeCloumn,controlNames,revControlNames){
    var frm = document.forms[formName];
    var restrictFlagOb = frm.all[changeCloumn];
    
    
		for(i=0;i<controlNames.length;i++){
		    if(restrictFlagOb.value =='98'){
	  	 		frm.all[controlNames[i]].disabled = false;
	   			frm.all[controlNames[i]].require='true';
	   		}
	   		else{
	   			frm.all[controlNames[i]].disabled = true;
	   			frm.all[controlNames[i]].require='false';
	   			
	   		}
		}
		
		for(i=0;i<revControlNames.length;i++){
		    if(restrictFlagOb.value =='98'){
		    	frm.all[revControlNames[i]].disabled = true;
	   			frm.all[revControlNames[i]].require='false';
	   			
	  	 		
	   		}
	   		else{
	   			frm.all[revControlNames[i]].disabled = false;
	   			frm.all[revControlNames[i]].require='true';
	   		}
		}

	
}

//全选按钮
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


