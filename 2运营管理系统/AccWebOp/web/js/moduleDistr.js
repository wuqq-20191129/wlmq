function listGroupModules(formName,listDivID){
    var divOb = document.getElementById(listDivID);
    if(divOb !=null)
        divOb.disabled = false;
    frm = document.forms[formName];
    row = event.srcElement;
    while(row.tagName !='TR'){
        row = row.parentElement;
    }
    chk = row.children[0].children[0];
    if(!chk.checked){
        frm.submit();
        return;
    }
		
    //	grpID = row.id;
    //	index = frm.action.indexOf("?");
    //	if(index == -1)
    //		frm.action = frm.action+"?groupID="+grpID;
    //	else{
    //		host = frm.action.substring(0,index);
    //		frm.action =host+"?groupID="+grpID;
    //	}
    //	alert("frm.action="+frm.action);
    document.getElementById("currentGroupID").value=row.id;
    frm.submit();
		
	
}

function listGroupModules2(formName,listDivID){
    var divOb = document.getElementById(listDivID);
    if(divOb !=null)
        divOb.disabled = false;
    frm = document.forms[formName];
    row = event.srcElement;
    while(row.tagName !='TR'){
        row = row.parentElement;
    }
    chk = row.children[0].children[0];
    if(!chk.checked){
        frm.submit();
        return;
    }
		
    document.getElementById("sysFlagID").value=row.id;
    frm.submit();
		
	
}

function enableModule(groupDivID,moduleDivID){
    var groupOb = document.getElementById(groupDivID);
    var moduleOb = document.getElementById(moduleDivID);
	
    if(groupOb ==null || moduleOb == null)
        return;
    var tableOb =groupOb.children[0];
    var checkOb = null;
    for(i=0;i< tableOb.rows.length;i++){
        if(tableOb.rows[i].id =="ignore")
            continue;
        checkOb = tableOb.rows[i].cells[0].children[0];
        //		alert(checkOb.checked);
        if(checkOb.checked == true){
            moduleOb.disabled = false;
            return;
        }
    }
	
}

function listGroupModulesTest(formName,listDivID){
    var divOb = document.getElementById(listDivID);
    if(divOb !=null)
        divOb.disabled = false;
    frm = document.forms[formName];
    row = event.srcElement;
    while(row.tagName !='TR'){
        row = row.parentElement;
    }
  
    document.getElementById("currentGroupID").vaule=row.id;
    frm.submit();
		
	
}

function initBack(){
    var str=document.getElementsByName("rectNo");
    var objarray=str.length;
    for (i=0;i<objarray;i++)
    {
        if(str[i].checked == true)
        {
            document.getElementById("currentGroupID").value =str[i].value ;
        }
    }
    
    var str2=document.getElementsByName("rectNo2");
    var objarray2=str2.length;
    for (i=0;i<objarray2;i++)
    {
        if(str2[i].checked == true)
        {
            document.getElementById("sysFlagID").value =str2[i].value ;
        }
    }
    
}