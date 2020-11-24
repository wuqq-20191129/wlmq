
function openMenu(divName){

 var pMenuIdsOb = document.getElementById("pMenuIdsCtr");
	if(pMenuIdsOb == null || pMenuIdsOb.value =="")
		return;
//	alert(pMenuIdsOb.value);
	var pMenuIdsArray = pMenuIdsOb.value.split("#");
	var divOb = null;
	var imgOb = null;
	var clickDivName = '';
	var clickImgName = '';
	for(i=0;i<pMenuIdsArray.length-1;i++){
	//	alert("pMenuIdsArray="+pMenuIdsArray[i]);
		clickDivName ='p'+pMenuIdsArray[i];
		clickImgName ='i'+pMenuIdsArray[i];
		divOb = document.getElementById(clickDivName);
		if(divOb == null)
		 continue;
		if(clickDivName != divName){
			divOb.style.display = 'none';
			imgOb = document.getElementById(clickImgName);
			if(imgOb != null)
				imgOb.src = "images/menu/node_close.gif";
		}
		else{
			divOb.style.display = "";
			imgOb = document.getElementById(clickImgName);
			if(imgOb != null)
				imgOb.src = "images/menu/node_open.gif";
		}		
		
	}		
}
function changTDBgColor(tdID){
	var aOb = event.srcElement;
	var tbOb = aOb.parentElement.parentElement.parentElement.parentElement;
//	alert(tbOb.tagName);
	if(tbOb.tagName !="TABLE")
		return;
	var rows = tbOb.rows;
	var row = null;
  var cell = null;
  var imgSrc = "";
  var imgOb = null;
  var idx = -1;
	for(i=0;i<rows.length;i++){
		row = rows[i];
		cell = row.cells[1];
//		alert(cell.id);
		if(cell.id !=tdID)
			 continue;
	 imgOb = cell.children[0].children[0] ;
	 imgSrc = imgOb.src;
	 idx = imgSrc.indexOf("_click");
	 if(idx !=-1)
	  imgOb.src = imgSrc.substring(0,idx)+".gif";
		
	}
  imgOb = aOb.children[0];
  imgSrc = imgOb.src;
	idx = imgSrc.indexOf(".gif");
	if(idx !=-1)
	  imgOb.src = imgSrc.substring(0,idx)+"_click.gif";
  
	
}