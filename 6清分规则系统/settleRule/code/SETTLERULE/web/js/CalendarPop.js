function openCalenderDialog(obj,isSub){
	var path="./jsp/calenderD.html";

	//当前连接在子模块中
	if (isSub=="true") path="./../jsp/calenderD.html"

	var rt=window.showModalDialog(path,'calender','dialogWidth:220px;dialogHeight:350px;center:yes;resizable:no;status:no;scroll:no');
	if(rt!=null && rt!='undefined'){
		obj.value=rt;
	   }
}

function openCalenderDialogByID(objName,isSub){
	var path="./jsp/calenderD.html";

	//当前连接在子模块中
	if (isSub=="true") path="./../jsp/calenderD.html"

	var rt=window.showModalDialog(path,'0','dialogWidth:220px;dialogHeight:350px;center:yes;resizable:no;status:no;scroll:no');
	if(rt!=null && rt!='undefined'){
		obj =document.getElementById(objName);
		obj.value=rt;
	   }
}
function openDetailCalenderDialogByID(objName,isSub){
	var path="./jsp/calenderD.html";

	//当前连接在子模块中
	if (isSub=="true") path="./../jsp/calenderD.html"

	var rt=window.showModalDialog(path,'1','dialogWidth:220px;dialogHeight:350px;center:yes;resizable:no;status:no;scroll:no');
	if(rt!=null && rt!='undefined'){
		obj =document.getElementById(objName);
		obj.value=rt;
	   }
}
