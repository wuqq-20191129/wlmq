 
 function loading() {
 // alert('a');
  var frm = document.forms["formOp"];
//   if (Validator.Validate(frm,2))  {
//     frm.all['confirm'].disabled='true';
     //alert( frm.all['makeFile'].value);
//     frm.all['_fileName'].value = frm.all['makeFile'].value;
//     frm.all['_terminator'].value = frm.all['seperator'].value;
     flashs();
     document.formOp.submit();//�ύ�ñ�;
//     }
}
  function loadingPhy() {
 // alert('a');
  var frm = document.forms["formOp"];
   if (Validator.Validate(frm,2))  {
     frm.all['confirm'].disabled='true';
     //alert( frm.all['makeFile'].value);
     frm.all['_fileName'].value = frm.all['makeFile'].value;
     flashs();
     document.formOp.submit();//�ύ�ñ�;
     }
 }
 function clearMsg(){
   var tdob =document.getElementById('retMsg');
   tdob.innerHTML="正在导入:<font color='red'></font>";
 }
 
  function loadingForFarePlan() {
 // alert('a');
    var frm = document.forms["formOp"];
   if (Validator.Validate(frm,2))  {
     var cmd = document.forms["formOp"].all['command'].value;
     if(cmd =="add"){
        flashs();
        
     }
     document.forms["formOp"].all['save1'].disabled=true;
     document.forms["formOp"].all['cancle'].disabled=true;
     document.formOp.submit();
     
     
 }
 }


 
function flashs(){
if(runPrompt.style.display =="none"){
	runPrompt.style.display="";
	setTimeout('flashs()',2000);
	}
else{
	runPrompt.style.display="none";
	setTimeout('flashs()',500);
	}
}
function flashsForFarePlan(){
var runPrompt = document.getElementById('runPrompt');
if(runPrompt.style.display =="none"){
	runPrompt.style.display="";
	setTimeout('flashsForFarePlan()',500);
	}
else{
	runPrompt.style.display="none";
	setTimeout('flashsForFarePlan()',500);
	}
}
function setDefaultValue(){
 var frm = document.forms["formOp"];
 var _terminatorValue =frm.all['_terminator'].value;
 var _fileNameValue =frm.all['_fileName'].value;
 
 if(_terminatorValue !=null && _terminatorValue !='')
   frm.all['seperator'].value=_terminatorValue;
   /*
 if(_fileNameValue !=null&& _fileNameValue !='')
 {
   frm.all['makeFile'].defaultValue=_fileNameValue;
   alert(_fileNameValue);
 //  alert(frm.all['makeFile'].value);
   }
   */
   
   
}