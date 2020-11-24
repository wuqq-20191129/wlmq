function menuClick(){
	var topMenuImgs = document.getElementsByName("topMenuImage");
	if(topMenuImgs == null)
		return;
	var menuOb = null;
	for(i=0;i<topMenuImgs.length;i++){
		menuOb =topMenuImgs[i];
		changeMenuImgByOb(menuOb,"_normal.gif");
	}
	changeMenuImg("_click.gif");
}
function menuMouseOver(){
	var menuOb = event.srcElement;
	if(menuOb.tagName !="IMG")
		return;
	var imgUrl = menuOb.src ;
	var idx = imgUrl.indexOf("_red");
	if(idx != -1)
		return;
	changeMenuImg("_white.jpg");
	
}
function menuMouseOut(){
	
	var menuOb = event.srcElement;
	if(menuOb.tagName !="IMG")
		return;
	var imgUrl = menuOb.src ;
	var idx = imgUrl.indexOf("_red");
	if(idx != -1)
		return;
	changeMenuImg("_yellow.jpg");
	
}
function changeMenuImg(imgSufix){
	var menuOb = event.srcElement;
	if(menuOb.tagName !="IMG")
		return;
	var imgUrl = menuOb.src ;
	var idx = imgUrl.indexOf("_");
	if(idx == -1)
		return;
	imgUrl = imgUrl.substring(0,idx)+imgSufix;
	menuOb.src = imgUrl;
}
function changeMenuImgByOb(menuOb,imgSufix){

	if(menuOb.tagName !="IMG")
		return;
	var imgUrl = menuOb.src ;
	var idx = imgUrl.indexOf("_");
	if(idx == -1)
		return;
	imgUrl = imgUrl.substring(0,idx)+imgSufix;
	menuOb.src = imgUrl;
}