//控件失效
function displayOrderNoSelect(formName,name,flag){
    document.forms[formName].elements[name].disabled=true;
}

//控制审核按钮，仅当所选记录未审核时，审核按钮才可用
function disableAudit(formName,auditName,flag){
    //alert(flag);
    //alert(event.srcElement.checked);
    if(event.srcElement.checked&&flag !='3'){
        document.forms[formName].elements[auditName].disabled=true;
    }
}

//控制增加、删、改按钮，仅当所选记录未审核时，审核按钮才可用
function disableControlByFlag(formName,controlName,flag){
    //alert(flag);
    //alert(event.srcElement.checked);
    if(flag =='3'){
        document.forms[formName].elements[controlName].disabled=false;
    }else{
        document.forms[formName].elements[controlName].disabled=true;
    }
}


function disableControlsByFlag(formName,controlNames,flag){
    //alert(flag);
    //alert(event.srcElement.checked);
    for(i=0;i<controlNames.length;i++){
    	//alert(controlNames[i]);
        if(flag =='3'){
            document.forms[formName].elements[controlNames[i]].disabled=false;
        }else{
            document.forms[formName].elements[controlNames[i]].disabled=true;
        }
    }
}


//通过单据状态控制单个操作按钮状态
function controlByFlag(formName,controlName){
    //alert(flag);
    //alert(event.srcElement.checked);
    var checkBoxes = document.getElementsByName('rectNo');
    if(checkBoxes ==null)
    	return ;
    if(checkBoxes.length ==0)
    	return
    for(i=0;i<checkBoxes.length;i++){
        if(checkBoxes[i].checked&&checkBoxes[i].flag !='3'){
            disableControlByFlag(formName,controlName,checkBoxes[i].flag);
            break;
        }
    }
}


//通过单据状态控制多个操作按钮状态
function controlsByFlag(formName,controlNames){
//    alert(flag);
//    alert(event.srcElement.checked);
    var checkBoxes = document.getElementsByName('rectNo');
    if(checkBoxes ==null)
    	return ;
    if(checkBoxes.length ==0)
    	return
    for(i=0;i<checkBoxes.length;i++){
        if(checkBoxes[i].checked&&checkBoxes[i].flag !='3'){
            disableControlsByFlag(formName,controlNames,checkBoxes[i].flag);
            break;
        }
    }
}


//通过单据状态控制多个操作按钮状态
function controlsByFlagWithoutCk(formName,controlNames){
    var rowSelected=document.forms[formName].all["rowSelected"];
    var lineId;
    var lineOb;
    if (rowSelected != null&&rowSelected.value !=null){
        lineId = rowSelected.value;
        lineOb =document.getElementById(lineId);
        if(lineOb !=null){
            disableControlsByFlag(formName,controlNames,lineOb.flag);
        }
    }
}


function controlsByFlagForModify(formName){

    var rowSelected=document.forms[formName].all["rowSelected"];
    var lineId;
    var lineOb;
    if (rowSelected != null){
        lineId = rowSelected.value;
        lineOb =document.getElementById(lineId);
        //alert(lineOb.flag);
        if(lineOb !=null&&lineOb.flag!=null&&lineOb.flag =='3'){
                document.forms[formName].elements['modify'].disabled=false;
        }else{
                document.forms[formName].elements['modify'].disabled=true;
        }
    }

}


function selectItemsForDialog(divName,formName){
    //alert('a');
    var btId=event.srcElement.id;
    if(btId =='cancel'){
      window.returnValue="";
      window.close();
      return;
    }
    
    getAllSelectedIDs(divName,formName);
    var allSelectedIds =document.forms[formName].all['allSelectedIDs'].value;
    
    window.returnValue=allSelectedIds;
    window.close();
    return;
}


function getAllSelectedIDsForStation(listName,formName){
//	alert('call');
	var listDiv = document.getElementById(listName);
	var tbl = listDiv.children[0];
	var selectedIDs = document.forms[formName].all['allSelectedIDs'];
	selectedIDs.value ="";

	for(i=0;i<tbl.rows.length;i++){
            //alert(i);
            var tblRow = tbl.rows[i];
            var checkCell = tblRow.cells[0];
            var selectedCK = checkCell.children[0];
            if( tblRow.id=='ignore')
                    continue;
            //alert(tblRow.id);
            if(selectedCK != null){
               //  alert(selectedCK.checked);
                if(selectedCK.checked){
                        selectedIDs.value +=tblRow.id+";";
//			alert(selectedIDs.value);
                }
            }

	}
//	alert("selectedIDs.value="+selectedIDs.value);
}


function selectItemsForDialogStation(divName,formName){
    //alert('a');
    var btId=event.srcElement.id;
    if(btId =='cancel'){

      window.returnValue="";
      window.close();
      return;
    }
    if(!checkInput('DataTable'))
        return;
    changeTRIdValue('DataTable');
    getAllSelectedIDsForStation(divName,formName);
    var allSelectedIds =document.forms[formName].all['allSelectedIDs'].value;

    window.returnValue=allSelectedIds;
    window.close();
    return;
}


function selectItemsForDialogOperator(divName,formName){
    //alert('a');
    var btId=event.srcElement.id;
    if(btId =='cancel'){

      window.returnValue="";
      window.close();
      return;
    }

    getAllSelectedIDs(divName,formName);
    var allSelectedIds =document.forms[formName].all['allSelectedIDs'].value;
    
    window.returnValue=allSelectedIds;
    window.close();
    return;
}

function isPositive(value){
    if(value >0)
            return true;
    return false;
}


function setCardMoney(formName,cardMoneyName,controlName){
    document.forms[formName].all[cardMoneyName].value = document.forms[formName].all[controlName].value;
}


//清除明细单号
function clearTextByFlag(formName,controlNames){
   var rowSelected=document.forms[formName].all["rowSelected"];
   var lineId;
   var lineOb;

   if (rowSelected != null){
        lineId = rowSelected.value;
 //   	alert(rowSelected.value);
        lineOb =document.getElementById(lineId);

        if(lineOb !=null&&lineOb.flag !=null&&lineOb.flag !='3'){
                for(i=0;i<controlNames.length;i++){
                   document.forms[formName].all[controlNames[i]].value ='';
                }
        }
    }
}


function getOptionIndex(controlValue,options){
    for(var i=0;i<options.length;i++){
            if(controlValue == options[i])
                    return i;
    }
    return -1;
}


//对于提交窗体的一些控件，当其中某一选项取某值时，需将其他选项设为必须检查
function setFormCheckOption(formName,controlName,options,opControls,requireds){
    var controlValue =document.forms[formName].all[controlName].value;
    var index = getOptionIndex(controlValue,options);
    if(index ==-1)
        return ;

    for(var i=0;i<opControls.length;i++){
        //alert(document.forms[formName].all[opControls[i]]);
        document.forms[formName].all[opControls[i]].require = requireds[index];
        //alert(document.forms[formName].all[opControls[i]].require);
    }
}


function setFormCheckOptionForRadios(radioName,relativeObjects){

    var radioObs = document.getElementsByName(radioName);	
    var relativeOb ;
    for(i=0;i<radioObs.length;i++){
            radioOb = radioObs[i];
            relativeOb = document.getElementsByName(relativeObjects[i]);
            if(radioOb.checked){			  
                    relativeOb.require ='true';
            }else{
                    relativeOb.require ='false';
            }
    }

}


function disableControls(controlObs,disabled){
    for(i=0;i<controlObs.length;i++){
            controlObs[i].disabled = disabled;
    }
}


function disableFormControls(formName,controlObs,disabled){
    for(i=0;i<controlObs.length;i++){
            document.forms[formName].all[controlObs[i]].disabled = disabled;
    }
}


function validControls(controlObs,required){
    for(i=0;i<controlObs.length;i++){
        //alert(controlObs[i].name);
            controlObs[i].require = required;
    }
}


function validControlsByName(formName,controlNames,required){
    for(i=0;i<controlNames.length;i++){
        //alert(formName+':'+controlNames[i]);
            document.forms[formName].all[controlNames[i]].require = required;
    }
}


function setAvailableControls(controls){
    for(var i=0;i<controls.length;i++){
            controls[i].disabled = false;
            controls[i].require ='true';
    }
}


function setAvailableControlsNoCheck(controls){
    for(var i=0;i<controls.length;i++){
            controls[i].disabled = false;

    }
}


function setUnavailableControls(controls){
    for(var i=0;i<controls.length;i++){
            controls[i].value ='';
            controls[i].disabled = true;
            controls[i].require ='false';
    }
}


function disableRadio(formName,reasonIdName,radioName){
    var reasonIdOb = document.forms[formName].all[reasonIdName];
    var radioObs = document.getElementsByName(radioName);	

    var radioOb;
    var disabled = true;
    if(	reasonIdOb.value =='17')
            disabled = true;
    else
            disabled = false;
    for(i=0;i<radioObs.length;i++){
            radioOb = radioObs[i];

            if(radioOb.checked)
                    radioOb.checked = false;
            radioOb.disabled = disabled;
            //alert(radioOb.disabled);
    }
}


function setDetailBillNo(formName,billNoName,queryConditionName){
        document.forms[formName].all[billNoName].value =document.forms[formName].all[queryConditionName].value 
}


function controlByRecordFlagForInit(formName,controlNames,disabed){
        var recordFlag = document.forms[formName].all['billRecordFlag'].value;
        if(recordFlag ==null || recordFlag =='')
                return ;
        if(recordFlag !='3')
                disableFormControls(formName,controlNames,true);
}
	
function controlByRestrictFlag(formName,controlNames){
    var frm = document.forms[formName];
    var restrictFlagOb = frm.all['d_restrictFlag'];
    
    for(i=0;i<controlNames.length;i++){
        if(restrictFlagOb.value =='1'){
            frm.all[controlNames[i]].disabled = false;
            if(controlNames[i].name=='d_lineId')
              frm.all[controlNames[i]].require='true';
        }else{
            frm.all[controlNames[i]].disabled = true;
            frm.all[controlNames[i]].require='false';
            frm.all[controlNames[i]].value='';
        }
    }
	
}


function setItemByLine(tableId,lineId){
   //alert('a');
    var tbOb = document.getElementById(tableId);
    var lineOb =document.getElementById(lineId);
    var lineId = lineOb.value;
    var trLineId;
    var rowCount = tbOb.rows.length;
    var rowOb;
    var idx;
    //alert(lineId);
    for (i=0;i<rowCount;i++){
            rowOb =tbOb.rows[i];
            idx = rowOb.id.indexOf(':');
            if(idx ==-1)
                    continue;
            if(lineId=='-1')
            {
                    rowOb.style.display ='';
                    continue;
            }
            trLineId = rowOb.id.substring(0,idx);
            if(trLineId==lineId)
                    rowOb.style.display='';
            else
                    rowOb.style.display='none';
    }
    
}


function getFormatDate(dateOb){
    //var cur = new Date();
    var year = dateOb.getFullYear();
    var month =dateOb.getMonth()+1;
    var date = dateOb.getDate();

    var syear=''+year;
    var smonth=''+month;
    var sdate =''+date;
    if(month<10)
      smonth = '0'+smonth;
    if(date<10)
      sdate='0'+sdate;
    return syear+'-'+smonth+'-'+sdate;
   
}


function getValidDateForSVT(cardMainTye,cardSubType){
    var cur = new Date();
    var miliValue = cur.getTime();
    var validDays = getValidDays(cardMainTye,cardSubType);
    if(validDays==0)
       return '';

    var miliValid=miliValue+validDays*24*3600*1000;
    return getFormatDate(new Date(miliValid));
  
}

function restoreControlDefaultValue(formName,controlnames,defaultValue){
	var frm = document.forms[formName];
	for(i=0;i<controlnames.length;i++){
            frm.all[controlnames[i]].value=defaultValue;
	}
}