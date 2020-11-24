//日期控件
function openCalenderDialog(obj,isSub){
	var path="jsp/component/calenderD.html";

	//当前连接在子模块中
	if (isSub=="true") path="../../jsp/component/calenderD.html"

	var rt=window.showModalDialog(path,'calender','dialogWidth:220px;dialogHeight:350px;center:yes;resizable:no;status:no;scroll:no');
	if(rt!=null && rt!='undefined'){
		obj.value=rt;
	   }
}
//日期控件
function openCalenderDialogByID(objName,isSub){
       // alert('openCalenderDialogByID');
	var path="jsp/component/calenderD.html";

	//当前连接在子模块中
	if (isSub=="true") path="../../jsp/component/calenderD.html"

	var rt=window.showModalDialog(path,'0','dialogWidth:220px;dialogHeight:350px;center:yes;resizable:no;status:no;scroll:no');
	if(rt!=null && rt!='undefined'){
		obj =document.getElementById(objName);
		obj.value=rt;
	   }
}
//日期 时分秒 的时间控件
function openDetailCalenderDialogByID(objName,isSub){
	var path="jsp/component/calenderD.html";

	//当前连接在子模块中
	if (isSub=="true") path="../../jsp/component/calenderD.html"

	var rt=window.showModalDialog(path,'1','dialogWidth:220px;dialogHeight:350px;center:yes;resizable:no;status:no;scroll:no');
	if(rt!=null && rt!='undefined'){
		obj =document.getElementById(objName);
		obj.value=rt;
	   }
}

//日期 时分秒 的时间控件 返回格式yyyyMMddHHmiss
function openDetailCalenderDialogByIDx(objName,isSub){
	var path="jsp/component/calenderD.html";

	//当前连接在子模块中
	if (isSub=="true") path="../../jsp/component/calenderD.html"

	var rt=window.showModalDialog(path,'1','dialogWidth:220px;dialogHeight:350px;center:yes;resizable:no;status:no;scroll:no');
	if(rt!=null && rt!='undefined'){
                rt=rt.replace(/[\s\-\:]/g, '');
		obj =document.getElementById(objName);
		obj.value=rt;
	   }
}

function preLoadVal(q_b_time , q_e_time ,days){

//            获取选择的时间
            var current = new  Date();
//  		var year = current.getYear(); 
                var year = current.getFullYear();//modify by zhongziqi 20170616       
  		var month = current.getMonth()+1;
  		var day = current.getDate();
 
  		if(month <10)
  			month = "0"+month;
  		if(day <10 )
  			day = "0"+day;
                    
              var etime ;
              etime    =  year+"-"+month+"-"+day ;
              
              document.getElementById(q_e_time).value = etime ;
         
         
         current = new Date(current.valueOf()- (3* 24 * 60 * 60 * 1000));
         if(days!=null){
             current = new Date(current.valueOf()- (days* 24 * 60 * 60 * 1000));
         }
         
//                year = current.getYear();
                  year = current.getFullYear();//modify by zhongziqi 20170616   
  		 month = current.getMonth()+1;
  		 day = current.getDate();
 
  		if(month <10)
  			month = "0"+month;
  		if(day <10 )
  			day = "0"+day;
                    
              var btime ;
              btime    =  year+"-"+month+"-"+day ;
         
         document.getElementById(q_b_time).value = btime ;
        }
        
function getCurrentDate(id) {//add by zhongziqi 20170819 
//            获取选择的时间
    var current = new Date();
    var year = current.getFullYear();
    var month = current.getMonth() + 1;
    var day = current.getDate();
    if (month < 10)
        month = "0" + month;
    if (day < 10)
        day = "0" + day;
    var curtDate;
    curtDate = year + "-" + month + "-" + day;
    document.getElementById(id).value = curtDate;
}
