
function openMenu(divName){
    if(divName ==null)
        return;
    document.getElementById("openDiv").value=divName;
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
//改变选择的子菜单的颜色


function setSubMenuColor(){
    var selectedSubMenu=document.getElementById("selectedSubMenu");
    if (selectedSubMenu != null){
        var obj=event.srcElement;
        while (obj.tagName != "TD"){
            obj=obj.parentElement;
        }
		
        if (selectedSubMenu.value != "" || selectedSubMenu.value != null){
            //alert(selectedSubMenu.value);
            var oldObj=document.getElementById(selectedSubMenu.value);
            if (oldObj != null){
                oldObj.bgColor="";
            }
        }

        selectedSubMenu.value=obj.id;
        document.getElementById("selectedTd").value=obj.id;
        //	obj.bgColor="#97CBFF";
        // alert("tagName:"+obj.tagName);
        obj.bgColor ="#D1D1D1";
    }
}
function setSubMenuColorForRefresh(selectedTD){
    var selectedSubMenu=document.getElementById("selectedSubMenu");
    if(selectedSubMenu==null)
        return;
    var obj=document.getElementById(selectedTD);
    if(obj==null)
        return;
        
    if(selectedSubMenu !=null)
        selectedSubMenu.value=obj.id;
    document.getElementById("selectedTd").value=obj.id;

    obj.bgColor ="#D1D1D1";
}

function freshLeftMenu(formName,isNeedRefresh){
    if(isNeedRefresh=='0')
        return;
        
    var tmp = "sumitCurrentForm('"+formName +"')";
    //	 alert(tmp);
    var interval = 180000;
    var  frm = document.forms[formName];
    if(frm == null)
        return;
	
    setInterval(tmp,interval)	;	
}
function sumitCurrentForm(formName){
    document.forms[formName].submit();
	  
}
function refreshMain(){
    var topMenuName=document.getElementById('topMenuName').value;
    var topMenuId = document.getElementById('topMenuId').value;
    //alert('topMenuName='+topMenuName+' topMenuId='+topMenuId);
  
    var obj=document.getElementById('selectedTd');
    // alert('obj.value='+obj.value+' length='+obj.value.length);
    if(obj == null || obj.value == 'null' || obj.value=='')
    { 
        //alert('a1');
        parent.main.location.href="/sysmonitor/jsp/homepage.jsp?topMenuName="+topMenuName+"&topMenuId="+topMenuId;
        return
    }
    // alert('a');
    var objSelectedTd =document.getElementById(obj.value);
   
    var objA=objSelectedTd.children[0];
    // alert('objA.href='+objA.href);
    if(objA.target !='_blank')
        parent.main.location.href =objA.href;
   
   
   
}


