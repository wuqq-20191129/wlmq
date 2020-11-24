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
function showWindow(moduleID,width,height){
    var authenticated=document.getElementById("authenticated").value;
    //alert('authenticated='+authenticated);
    if(authenticated=='true'){
        parent.contents.location.href="/sysmonitor/leftMenu.do?TopMenuId=" + moduleID;
        return;
    }
   
    var path='jsp/password.jsp?ModuleID='+moduleID;

    //alert('moduleId='+moduleID);
    var rt=window.showModalDialog(path,'','dialogWidth:'+width+'px;dialogHeight:'+height+'px;center:yes;resizable:no;status:no;scroll:yes');
    //alert(rt);
    if(rt!=null && rt!='undefined'){
        // alert("/sysmonitor/leftMenu.do?TopMenuId=" + moduleID);
        document.getElementById("authenticated").value='true';
        parent.contents.location.href="/sysmonitor/leftMenu.do?TopMenuId=" + moduleID;
    }
}
function validPass(){
    var inputPass=document.getElementById("Password").value;
    var dbPass=document.getElementById("DbPassword").value;
    if(inputPass==null || inputPass.length==0){
        alert("密码不能为空");
     
        return ;
    }
//    if(inputPass !=dbPass){
//        alert("密码不对");
//        return ;
//    }
    window.returnValue='sucess';
    window.close();
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