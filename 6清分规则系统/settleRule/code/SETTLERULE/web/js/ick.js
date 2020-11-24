
function setOperationType(op)
{
	var optype=document.getElementById("command");
	if (optype != null)
	{
		if (op !="")
		{
 		  optype.value=op;
		}
	}
}

function showgrh()
{
	if (document.all.grhzl.style.display=="")
		document.all.grhzl.style.display="none";
	else
		document.all.grhzl.style.display="";
}

function changeAction(action)
{
	window.location.href=action;
}


function selectAll()
{
	if (document.result_list.chkno.checked=="1")
		document.result_list.chkno.checked="";
	else
		document.result_list.chkno.checked="1";
}

function unSelectAll()
{
	window.alert("unSelectAll");
}

function addInitRecord(form)
{
	form.all.orgRecord.style.display="none";
	form.all.initRecord1.style.display="";
	form.all.initRecord2.style.display="";
	form.all.initRecord3.style.display="";
	form.all.initRecord4.style.display="";
	form.all.initRecord5.style.display="";
}

function cancleInitRecord(form){
	form.all.orgRecord.style.display="";
	form.all.initRecord1.style.display="none";
	form.all.initRecord2.style.display="none";
	form.all.initRecord3.style.display="none";
	form.all.initRecord4.style.display="none";
	form.all.initRecord5.style.display="none";
}

function checkAll(form)
{
	for (var i=0;i<form.elements.length;i++)
  {
	  var e = form.elements[i];
		if (e.name == 'chkall')
			if (e.checked==false)
				e.checked=true;
			else
				e.checked="";
	}
}

function enableAdd(form)
{
	for (var i=0;i<form.elements.length;i++)
  {
		var e = form.elements[i];
			if (e.disabled != "")
      {
		   	e.disabled="";
		   //window.alert("name=" + e.name);
			}
	 }
}

function openDialog(path)
{
	//window.showModalDialog('IckRkHshk.do','calender','dialogWidth:390px;dialogHeight:545px;center:yes;resizable:no;status:no');
	window.open(path,'calender','width=580,height=520,left=280,top=160,resizable=no,status=no');
}

//function MM_openBrWindow() { //v2.0
//弹出窗口
function MM_openBrWindow(theURL,winName,featur)
 { //v2.0
  window.open(theURL,winName,featur);
  //window.open(theURL,winName,featur<p align="left"></p>es);
}

//全选按钮
function selectAllRecord(name)
{
  alert("aaaa");	
	var currentOperation=getCurrentOpertion();
	if (currentOperation == "op_modify" || currentOperation == "op_add") return false;

	var obj=document.all.rectNoAll;
	var count=document.getElementById("rowTickedCount");

	var flag=false;
	var temp=0;
  

	for (var i=0;i<document.all.length ;i++ )
	{
		var e=document.all[i];

		if (e.name==name && e.type=="checkbox")
		{
			if (obj.checked){
				e.checked="1";
				flag=true;
			}
			else{
				e.checked="";
				temp=0;
			}
			if (flag) temp++;
		}
	}
	count.value=temp;

	//改变操作模式（改变按钮的可用状态）
	if (flag)
		if (getCurrentLine() != "")
			setOperation("op_select");
		else
			setOperation("op_select_all");
	else
		if (getCurrentLine() != "")
			setOperation("op_select_browse");
		else
			setOperation("op_browse");
}

//取消全选按钮
function unSelectAllRecord(){
	var currentOperation=getCurrentOpertion();
	if (currentOperation == "op_modify" || currentOperation == "op_add") return false;

	var obj=document.all.rectNoAll;
	var count=document.getElementById("rowTickedCount");
	var temp=count.value;

	headEventObject=event.srcElement
	if(headEventObject.type == "checkbox" && headEventObject.name == "rectNo"){
		//alert("value=" + headEventObject.value);
		if (headEventObject.checked)
			temp++;
		else
			temp--;
	}

	obj.checked="";
	count.value=temp;

	//改变操作模式（改变按钮的可用状态）
	if (temp>0)
		setOperation("op_select");
	else
		setOperation("op_select_browse");
}


function overResultRow(object){
	var rowSelected=document.getElementById("rowSelected");

	object.style.cursor="hand";
	if (object.id != rowSelected.value)
	{
		object.style.background="#E8E8E8";
	}
}

function outResultRow(object){
	var rowSelected=document.getElementById("rowSelected");

	if (object.id != rowSelected.value)
	{
		object.style.background="#FFFFFF";
	}
}


function setDeleteOrModify(){
	var opertion=document.getElementById("operation");
	if (opertion.value !== "op_select"){  //如果在op_select模式下则不用改变，否则改变为op_select_browse
		//改变操作模式（改变按钮的可用状态）
		//disableAllButtons();
		setOperation("op_select_browse")
	}
	else
		return false;
}


//设置明细框(detail)为可编辑
//--输入参数：formName  明细框(DIV)的ID
//--输入参数：flag      标志，true＝enable false＝disable
function setDetailFormEnabled(formName,flag){
	var detail=document.getElementById(formName);
	if (detail != null){
		var detailTable=detail.children[0];//table
		while (detailTable.children[0].tagName != "TR"){
			detailTable=detailTable.children[0];
		}

		for (var i=0;i<detailTable.children.length;i++){
			var detailTR=detailTable.children[i];//TR
			for (var j=0;j<detailTR.children.length;j++){
				var detailTD=detailTR.children[j]; //TD
				var tmpLen=detailTD.children.length;
				if (tmpLen>0){
					for (var jj=0;jj<tmpLen;jj++){
						//alert(detailTD.children[jj].name + " " + detailTD.children[jj].disabled);
						if (flag)
							detailTD.children[jj].disabled="";
						else
							detailTD.children[jj].disabled="true";
					}//for jj
				}//if
			}//for j
		}//for i
	}//if
}

//清空明细框内容
function clearDetailFormContent(formName){
	alert("测试清空内容!");
	var detail=document.getElementById(formName);
	if (detail != null){
		var detailTable=detail.children[0];//table
		while (detailTable.children[0].tagName != "TR"){
			detailTable=detailTable.children[0];
		}

		for (var i=0;i<detailTable.children.length;i++){
			var detailTR=detailTable.children[i];//TR
			for (var j=0;j<detailTR.children.length;j++){
				var detailTD=detailTR.children[j]; //TD
				var tmpLen=detailTD.children.length;
				if (tmpLen>0){
					for (var jj=0;jj<tmpLen;jj++){
						var tmpObj=detailTD.children[jj];

						switch (tmpObj.tagName){
							case "INPUT":{
								switch (tmpObj.type){
									case "text":{
										tmpObj.value="";
										break;
									}
								}//switch
								break;
							}//case "INPUT"
							case "SELECT":{
								//alert(tmpObj.selectedIndex);
								tmpObj.selectedIndex=0;
								break;
							}
						}//switch

					}//for jj
				}//if
			}//for j
		}//for i
	}//if
}


//动态改变明细框内容为当前行各个字段(列表当前行信息) (*************代码有待改善************)
//--输入参数：detailForm  明细框(DIV)的ID
function setDetailFormContents(detailForm){
	if (detailForm != ""){
		var detail=document.getElementById(detailForm);
		if (detail != null){
			var detailTable=detail.children[0];//table
			while (detailTable.children[0].tagName != "TR"){
				detailTable=detailTable.children[0];
			}

			var currentLine=getCurrentLine();
			if (currentLine != ""){
				var currentObject=document.getElementById(currentLine);
				var tmp=currentObject.children.length
				
				if (tmp !=0){
					for (var i=0;i<tmp;i++){
						var child=currentObject.children[i];
						alert("child.id=" + child.id + " detail=" + detail.id + " tmpId=" + "d_" + child.id);

						/******** 设置detailForm **********/
						if (child.id != ""){
							alert(child.id + " to find " + "d_" + child.id);
							var tmpObj=document.getElementById("d_" + child.id);//明细输入框id加前缀 d_
							if (tmpObj != null && tmpObj != "undefined"){
								switch (tmpObj.tagName){
									case "INPUT":{
										switch (tmpObj.type){
											case "text":{
												tmpObj.value=child.innerHTML;
												break;
											}
										}//switch
										break;
									}
									case "SELECT":{
										tmpObj.selectedIndex=1; /***********  不能使用 selectedIndex 属性 ****************/
										break;
									}
								}
							}
						}

						/******** 设置detailForm **********/

					}//for
				}//if tmp
			}//if currentLine
		}//if detailObj
	}//if detailForm
}


//设置列表中的当前行信息
function setCurrentLine(lineId){
	var rowSelected=document.getElementById("rowSelected");
	if (rowSelected != null) rowSelected.value=lineId;
}

//读取列表中的当前行信息
function getCurrentLine(){
	var rowSelected=document.getElementById("rowSelected");
	if (rowSelected != null){
		var line=rowSelected.value;
		if (line != "init"){
			return line;
		}
		else
			return "";
	}
	else return "";
}

//选择改变当前行
function clickResultRow(object,detailFrom){
	var currentOperation=getCurrentOpertion();
	
	//alert("currentOperation="+currentOperation);

	if (currentOperation == "op_modify" || currentOperation == "op_add") return false;

	if (currentOperation == "op_select_all") setOperation("op_select");

	headEventObject=event.srcElement

	while(headEventObject.tagName!="TR"){
		headEventObject=headEventObject.parentElement
	}

	//取消原选择行的信息，及保存新选择的行信息
	var oldLine=getCurrentLine();
	if (oldLine != ""){
		var oldObject=document.getElementById(oldLine);
		oldObject.style.background="#FFFFFF";
	}
	//设置当前行
//	alert("headEventObject.tagName"+headEventObject.tagName)
	alert("headEventObject.id"+headEventObject.id)
	setCurrentLine(headEventObject.id);
	//改变背景色
	headEventObject.style.background="#97CBFF";

	//改变操作模式（改变按钮的可用状态）
	setDeleteOrModify();
	//填充明细框内容
	setDetailFormContents(detailFrom);
}

function getCurrentOpertion(){
	var opertion=document.getElementById("operation");
	if (opertion !== null)
		return opertion.value;
	else
		return "";
}

//读取操作模式
function getInitOpertion(){
	var opertion=document.getElementById("operation");
	if (opertion !== null)
		return opertion.value;
	else
		return "";
}

//读取操作模式
function setInitOpertion(value){
	if (value != ""){
		var opertion=document.getElementById("operation");
		if (opertion !== null)
			opertion.value=value;
	}
}

//读取操作命令
function getCommand(){
	var command=document.getElementById("command");
	if (command !== null)
		return command.value;
	else
		return "";
}

//设置操作命令
function setCommand(value){
	if (value != ""){
		var command=document.getElementById("command");
		if (command !== null)
			command.value=value;
	}
}


function getAvriableOpertions(opCode){
	if (opCode!=""){
		var avariableOpertions=document.getElementById(opCode);
		if (avariableOpertions != null)
			return avariableOpertions.avriable;
		else
			return "";
	}
}

function disableButton(id,flag){
	var button=document.getElementById(id);
	if (button !=null){
		//alert("id='" + id + "'");
		button.disabled=flag;
	}
}

function setAvriableButton(avriableOpertions,flag){
	//alert(avriableOpertions);
	if (avriableOpertions != ""){
		var index_p=0;
		var index_n=0;

		if (avriableOpertions.indexOf(",") != -1){
			while (index_n != -1){
				index_n=avriableOpertions.indexOf(",",index_n);
				if (index_n > 0){
					index_n++;
					var buttonName=avriableOpertions.substring(index_p,index_n-1);
					//alert(buttonName);
					disableButton(buttonName,flag);
					index_p=index_n;
				}
			}
			if (index_n == -1){
				//alert(avriableOpertions.substring(index_p,avriableOpertions.length));
				var buttonName=avriableOpertions.substring(index_p,avriableOpertions.length);
				//alert(buttonName);
				disableButton(buttonName,flag);
			}
		}
		else{
			var index_n=avriableOpertions.length;
			//alert(avriableOpertions.substring(index_p,index_n));
			var buttonName=avriableOpertions.substring(index_p,index_n);
			//alert(buttonName);
			disableButton(buttonName,flag);
		}

	}
}

function disableAllButtons(){
	var buttons=document.getElementById("functions");
	if (buttons !== null)
			setAvriableButton(buttons.value,true);
}

//页面初始化，例如操作按钮的可操作性
function init(){
	var initOpertion=getInitOpertion();
	var avriableOpertions=getAvriableOpertions(initOpertion);
	//alert(initOpertion + ":" + getAvriableOpertions(initOpertion));
	setAvriableButton(avriableOpertions,false);
}

//改变页面当前操作状态 opCode=""即初始化
function setOperation(opCode){
	disableAllButtons();
	setInitOpertion(opCode);
	var initOpertion=getInitOpertion();
	var avriableOpertions=getAvriableOpertions(initOpertion);
	//alert("avriableOpertions=" + avriableOpertions);
	setAvriableButton(avriableOpertions,false);
}

//改变页面当前操作状态 opCode=""即初始化
function getOperation(){
	var opType=getInitOpertion();
	if (opType == "")
		return opType;
	else
		return "";
}



function openConditionWindow(){
	var rt=showModalDialog("./jsp/condiction.jsp","","center:true;status:no;dialogWidth:500px;dialogHeight:300px;");
	//处理返回值
	if (rt[0]==true){
		switch (rt[1]){
			case "1":{//增加单条规则
				//alert("add only one rule");
				//设置明细框为编辑
				setDetailFormEnabled("detail",true);
				//清空明细框等待输入
				//alert("测试");
				clearDetailFormContent("detail");
				break;
			}
			case "2":{//增加全套参数
				alert("add a set rules");
				//不操作，返回浏览模式
				setOperation("op_browse");
				break;
			}
			case "3":{//增加整条线路
				alert("add a line rules");
				//不操作，返回浏览模式
				setOperation("op_browse");
				break;
			}
			case "4":{//增加一个运营商
				alert("add a ower rules");
				//不操作，返回浏览模式
				setOperation("op_browse");
				break;
			}
			case "5":{//增加一个站点
				alert("add a station rules");
				//不操作，返回浏览模式
				setOperation("op_browse");
				break;
			}
		}
	}
	else{
		//不操作，返回浏览模式
		setOperation("op_browse");
	}
}

//addAction
function addAction(){
	//弹出窗口选择增加条件
	openConditionWindow();

	//设置操作模式
}

//设置列表中记录的操作状态（新增add、删除delete、修改modify）
function setRecordStatus(obj,status){
	if (obj.tagName =="TR" ){
		obj.optype=status;
	}
}

function deleteFromDB(idStr,chkName){
	var form=document.getElementById("list_form");
	if (form != "undefined"){
		//alert(form.action);
		form.submit();
	}

	//document.list_form.submit();
}

//delAction
function delAction(){
	//已勾选行的数量
	var rowTickedCount=document.getElementById("rowTickedCount");
	var tmpObj;

	for (var i=0;i<document.all.length ;i++ ){
		var e=document.all[i];
		if (e.name=="rectNo" && e.type=="checkbox"){
			if (e.checked){
				deleteFromDB(e.value,"rectNo");
				//从数据库删除

				//从页面列表删除(暂时取消)
				//e.checked="";
				while(e.tagName!="TR" ) e=e.parentElement;
				if (e.style.display==""){
					setRecordStatus(e,"delete");
					e.style.display = "none";
				}//if

			}//if (e.checked)
		}//if
	}//for
	setOperation("op_browse");
}


//取消列表框构选
//--输入参数：listForme  列表框(DIV)的ID
function resetListForm(listForm){
	for (var i=0;i<document.all.length ;i++ ){
		var e=document.all[i];
		if (e.type=="checkbox")
			if (e.name.indexOf("rectNo")>=0) e.checked="";
	}
}


//取消当前操作，返回到浏览模式 1.重置明细框（disable）2.取消列表框构选 3.取消列表框中的当前行颜色 4.取消当前选择行信息
//--输入参数：detailForme  明细框(DIV)的ID
//--输入参数：listForme  列表框(DIV)的ID
function cancleAction(detailForm,listForm){
	setDetailFormEnabled(detailForm,false);
	resetListForm(listForm);

	var oldLine=getCurrentLine();
	if (oldLine != ""){
		var oldObject=document.getElementById(oldLine);
		oldObject.style.background="#FFFFFF";
	}
	setCurrentLine("");
}


//设置列表中记录的修改状态（修改后还没有保存）
function setRecordModify(obj,status){
	if (obj.tagName =="TR" ){
		//alert("optype_old=" + obj.optype);
		obj.modified=status;
		//alert(obj.id + "optype_new=" + obj.optype);
	}
}


//增加或者修改内容后按“保存”触发
function dataSave(){
	var opertion=document.getElementById("operation");
	if (opertion !== "op_modify"){
		//取消修改状态（1.输入框disabled,2.构选取消）3.修改记录状态（optype设置为“update”）
		for (var i=0;i<document.all.length ;i++ ){
			var e=document.all[i];
			//if (e.name=="rectNo" && e.type=="checkbox"){
			if (e.type=="checkbox"){
				if (e.checked){
					e.checked=""; // 2.构选取消
					while(e.tagName!="TR" ) e=e.parentElement; //<tr>
						if (e.modified=="true"){ //3.修改记录状态（optype设置为“update”）
							setRecordStatus(e,"update");
						}

						for (var j=0;j<e.children.length;j++)//<td>
							for (var jj=0;jj<e.children[j].children.length;jj++ ){
								//alert(e.children[j].children[jj].tagName + " " + e.children[j].children[jj].type);
								if (e.children[j].children[jj].tagName == "INPUT" && e.children[j].children[jj].type == "text"){
									var txtObj=e.children[j].children[jj];
									txtObj.disabled="true"; //1.输入框disabled
								}
							}//for
				}//while
			}//if
		}//for

		//改变操作模式（改变按钮的可用状态）
		setOperation("op_browse");
	}//if opertion (op_modify)
	else if (opertion == "op_add"){
		alert("add saved");
	}

}

//修改内容后 检验及标志(按“保存”前)
function modifiedAfter(){
	eventObj=event.srcElement
	//初步判断输入数据的正确性
	if (true){
		 //alert("the input date have not been checked.");
		 eventObj.setFocus();
		//return;
	}
	//alert("after input check");
	while(eventObj.tagName!="TR") eventObj=eventObj.parentElement;
	setRecordModify(eventObj,"true");
}

//修改(内容修改前)
function modifyAction(){
	//已勾选行的数量
	var rowTickedCount=document.getElementById("rowTickedCount");
	var tmpObj;

	for (var i=0;i<document.all.length ;i++ ){
		var e=document.all[i];
		if (e.name=="rectNo" && e.type=="checkbox"){
			if (e.checked){
				while(e.tagName!="TR" ) e=e.parentElement; //<tr>
					for (var j=0;j<e.children.length;j++)//<td>
						for (var jj=0;jj<e.children[j].children.length;jj++ ){
							//alert(e.children[j].children[jj].tagName + " " + e.children[j].children[jj].type);
							if (e.children[j].children[jj].tagName == "INPUT" && e.children[j].children[jj].type == "text"){
								var txtObj=e.children[j].children[jj];
								txtObj.disabled="";
							}
						}

					//if (e.style.display=="")
						//e.style.display = "none";
						//setRecordStatus("modify");
			}
		}
	}
	//改变操作模式（改变按钮的可用状态）
	//disableAllButtons();
	setOperation("op_modify");

	//设置明细框为编辑
	setDetailFormEnabled("detail",true);
}


//回车跳转到下一个输入框
function enterNext(){
	var srcElement=event.srcElement;
	var iKeyCode=event.keyCode;

	alert("form name=" + srcElement.form.name);
	/*
	if (iKeyCode==13){
		event.keyCode=9;
	}
	*/
}


//类表导出到文件 txt或者excel
function exportAction(){
	table2Excel("DataTable")
}


function btClick(){
	var btId=event.srcElement.id;
	//alert(btId);

	switch (btId){
	case "btClone":{
			setCommand("clone");
			setOperation("op_clone");
			break;
		}
	case "btAdd":{
			setCommand("add");
			setOperation("op_add");
			addAction();
			break;
		}
	case "btDelete":{
			setCommand("delete");
			setOperation("op_del");
			delAction();
			break;
		}
	case "btModify":{
			setCommand("modify");
			setOperation("op_modify");
			modifyAction();
			break;
		}
	case "btSave":{
			setOperation("op_save");
			dataSave();
			break;
		}
	case "btCancle":{
			setOperation("op_browse");
			cancleAction("detail","clearStart");
			break;
		}
	case "btPrint":{
			setOperation("op_browse");
			break;
		}
	case "btExport":{
			setOperation("op_browse");
			exportAction();
			break;
		}
	default:{
			setOperation("");
		}
	}
}

//*********************************************  导出文件  开始  *************************************
function table2Excel(tableId) {
	var table = document.getElementById(tableId);
	var rows = table.rows.length;
	var cols = table.rows(0).cells.length;
	alert("export data rows=" + (rows-2) + " cols=" + (cols-1));

/*
	// Start Excel and get Application object.
	var oXL = new ActiveXObject("Excel.Application");

	// Get a new workbook.
	var oWB = oXL.Workbooks.Add();
	var oSheet = oWB.ActiveSheet;
	//var table = document.all.data;
	// Add table headers going cell by cell.
	for (i=0;i<rows;i++) {
		for (j=0;j<cols;j++) {
			//oSheet.Cells(i+1,j+1).Font.Bold = True
			//oSheet.Cells(i+1,j+1).Font.Size = 50
			//oSheet.Cells(i+1,j+1).Alignment = 2
			oSheet.Cells(i+1,j+1).value = table.rows(i).cells(j).innerText;
		}
	}

	oXL.Visible = true;
	oXL.UserControl = true;
*/
}

//*********************************************  导出文件  结束  *************************************


//*********************************************  页面排序  开始  *************************************
function JM_HLTr(hStyle){
	eSrcObject=event.srcElement
	if (eSrcObject.tagName=="TABLE")
		return
	while(eSrcObject.tagName!="TR"&&eSrcObject.onyes!='head')
		eSrcObject=eSrcObject.parentElement
	if (eSrcObject.className!=hStyle&&eSrcObject.onyes!='head'&&eSrcObject.id!="ignore"&&eSrcObject.className!='delStyle'&&eSrcObject.className!='listTableHead'&&eSrcObject.className!='listTableHead0'){
		eSrcObject.className=hStyle
	}
	if (eSrcObject.onyes=='head'&&eSrcObject.className!='listHeadClicked'&&eSrcObject.className!='listHeadClicked0'){
		eSrcObject.className='listTableHeadO';
	}
}

function JM_HLTrRestore(sStyle){
	if (event.fromElement.contains(event.toElement)||eSrcObject.contains(event.toElement)||eSrcObject.id=="ignore"||eSrcObject.className=='delStyle')
		return
	if (event.toElement!=eSrcObject){
		if (event.srcElement.onyes=='head'&&eSrcObject.className!='listHeadClicked'&&eSrcObject.className!='listHeadClicked0'){
			event.srcElement.className='listTableHead'}
		else if(eSrcObject.className!='listHeadClicked'&&eSrcObject.className!='listHeadClicked0'){
			eSrcObject.className=sStyle}
	}
}

function JM_PowerList(colNum){
	headEventObject=event.srcElement
	while(headEventObject.tagName!="TR"){
		headEventObject=headEventObject.parentElement
	}

//	alert("chileren=" + headEventObject.children.length+":headEventObject.tagName="+headEventObject.tagName);
//alert("headEventObject.innerHTML="+headEventObject.innerHTML);
	for (i=0;i<headEventObject.children.length;i++){
		if (headEventObject.children[i]!=event.srcElement){
			headEventObject.children[i].className='listTableHead'
		}
	}

	var tableRows=0;
	trObject=DataTable.children[0].children
//	alert("DataTable.children[0].outerHTML="+DataTable.children[0].outerHTML);
//alert("DataTable.outerHTML"+DataTable.outerHTML);
//	alert("DataTable.children[0].tagName="+DataTable.children[0].tagName);
	//alert("trObject.tagName="+trObject.tagName+"trObject.id="+trObject.id);
	for (i=0;i<trObject.length;i++){
		Object=DataTable.children[0].children[i];
//		alert("Object=" + Object.id+"Object.tabName="+Object.tabName);

		tableRows=(trObject[i].id=='ignore')?tableRows:tableRows+1;
	}

	//alert("tableRows=" + tableRows);
	var trinnerHTML=new Array(tableRows)
	var tdinnerHTML=new Array(tableRows)
	var tdNumber=new Array(tableRows)
	var i0=0
	var i1=0

	for (i=0;i<trObject.length;i++){
//		alert("trObject[i].id="+trObject[i].id);
		if (trObject[i].id!='ignore'){
			trinnerHTML[i0]=trObject[i].innerHTML;
			tdinnerHTML[i0]=trObject[i].children[colNum].innerHTML;
//			alert("trinnerHTML[i0]="+trinnerHTML[i0]);
//			alert("tdinnerHTML[i0]="+tdinnerHTML[i0]);
			tdNumber[i0]=i;
			i0++;
		}
	}
	sourceHTML=clearStart.innerHTML;
	//alert(sourceHTML);

	for (bi=0;bi<tableRows;bi++){
		for (i=0;i<tableRows;i++){
			if(tdinnerHTML[i]>tdinnerHTML[i+1]){
				t_s=tdNumber[i+1];
				t_b=tdNumber[i];
				tdNumber[i+1]=t_b;
				tdNumber[i]=t_s;
				temp_small=tdinnerHTML[i+1];
				temp_big=tdinnerHTML[i];
				tdinnerHTML[i+1]=temp_big;
				tdinnerHTML[i]=temp_small;
			}
		}
	}

	var showshow='';
	var numshow='';
	for (i=0;i<tableRows;i++){
		showshow=showshow+tdinnerHTML[i]+'\n';
		numshow=numshow+tdNumber[i]+'|';
	}
	//alert("showshow="+showshow);
	//alert("numshow="+numshow);

	sourceHTML_head=sourceHTML.split("<TBODY>");
	numshow=numshow.split("|");

	//alert("sourceHTML="+sourceHTML);
//	alert("numshow="+numshow);
//	alert("sourceHTML_head="+sourceHTML_head);
	//alert("numshow="+numshow);

	var trRebuildHTML='';
//	alert("event.srcElement.className="+event.srcElement.className);
	if (event.srcElement.className=='listHeadClicked'){
		for (i=0;i<tableRows;i++){
//			alert("trObject[numshow[tableRows-1-i]].tagName="+trObject[numshow[tableRows-1-i]].tagName);
//			alert("trObject[numshow[tableRows-1-i]].outerHTML="+trObject[numshow[tableRows-1-i]].outerHTML);
//			alert("trObject[numshow[tableRows-1-i]].innerHTML="+trObject[numshow[tableRows-1-i]].innerHTML);
			trRebuildHTML=trRebuildHTML+trObject[numshow[tableRows-1-i]].outerHTML;
		}
		event.srcElement.className='listHeadClicked0';
	}else{
		for (i=0;i<tableRows;i++){
			trRebuildHTML=trRebuildHTML+trObject[numshow[i]].outerHTML;
		}
		event.srcElement.className='listHeadClicked';
	}

	var DataRebuildTable='';
//	alert("sourceHTML_head[0]="+sourceHTML_head[0]);
// alert("trObject[0].outerHTML="+trObject[0].outerHTML);
//	alert("trRebuildHTML="+trRebuildHTML);
//	alert("trObject[tableRows+1].outerHTML="+trObject[tableRows+1].outerHTML);
	DataRebuildTable=sourceHTML_head[0]+trObject[0].outerHTML+trRebuildHTML+trObject[tableRows+1].outerHTML+'</TABLE>';
	clearStart.innerHTML='';
	clearStart.innerHTML=DataRebuildTable;
}

//*********************************************  页面排序  结束*************************************


//改变选择的子菜单的颜色
function setSubMenuColor()        {
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
		obj.bgColor="#97CBFF";
	}
}



function testff(){
	window.alert("ddd");
}

//计算一个form中两个日期的差，且不大于90
 function dateDiff(dateform,datetime1,datetime2) {
   var frm = document.forms[dateform];
   alert(Validator.Validate(frm,2));
    if (Validator.Validate(frm,2)) {
     var   date1 = new Date();
     var   date2 = new Date();
     var   diff  = new Date();
     var M1= document.getElementById(datetime1).value;
       date1temp = new Date(M1.split("-")[0],M1.split("-")[1] - 1,M1.split("-")[2]);
       date1.setTime(date1temp.getTime());

       var M2= document.getElementById(datetime2).value;
       date2temp = new Date(M2.split("-")[0],M2.split("-")[1] - 1,M2.split("-")[2]);
       date2.setTime(date2temp.getTime());

       diff.setTime(Math.abs(date1.getTime() - date2.getTime()));
       timediff = diff.getTime();
       alert(Math.floor(timediff / (1000 * 60 * 60 * 24)));
       if (Math.floor(timediff / (1000 * 60 * 60 * 24))<=90)
        return true;
      else
       return false;

  }

}

//函数名称：clearDetailInfo
//函数功能：添加记录前清除数据录入拦
//入口参数：窗体或框架名
//创建日期：2006-08-30
//程序编制：yangjihe
function clearDetailInfo(sltcarddeposit,sltdpronum,sltdcardmon,sltdfinipronum,sltdorderno,sltdcardperava,sltdgentime,sltdhdlflag,sltcardsubcode)
{
//card_deposit,d_pro_num,d_card_mon,d_fini_pronum,d_order_no,d_card_per_ava,d_gen_time,d_hdl_flag,card_sub_code	
	var card_deposit=document.getElementById(sltcarddeposit);
	var d_pro_num=document.getElementById(sltdpronum);
	var d_card_mon=document.getElementById(sltdcardmon);
	var d_fini_pronum=document.getElementById(sltdfinipronum);
	var d_order_no=document.getElementById(sltdorderno);
	var d_card_per_ava=document.getElementById(sltdcardperava);
	var d_gen_time=document.getElementById(sltdgentime);
	var d_hdl_flag=document.getElementById(sltdhdlflag);
	var card_sub_code=document.getElementById(sltcardsubcode);
		
	if (card_deposit != null){
		card_deposit.value=""
	}
	
	if (d_pro_num != null){
		d_pro_num.value=""
	}

	if (d_card_mon != null){
		d_card_mon.value=""
	}

	if (d_fini_pronum != null){
		d_fini_pronum.value=""
	}

	if (d_order_no != null){
		d_order_no.value=""
	}
	
	if (d_card_per_ava != null){
		d_card_per_ava.value=""
	}

	if (d_gen_time != null){
		d_gen_time.value=""
	}

	if (d_hdl_flag != null){
		d_hdl_flag.value=""
	}
	
	if (card_sub_code != null){
		card_sub_code.value=""
	}	
}

//函数名称：setStationEnbale
//函数功能：根据票卡类型控制面值是否可录入
//入口参数：窗体或框架名
//创建日期：2006-08-30
//程序编制：yangjihe
function setStationEnbale(selectedName,selectName)
{
	var sltd = document.getElementById(selectedName);
	var slt=document.getElementById(selectName);

	//alert(sltd.value);
	if (sltd.value=="07")
	{
		slt.disabled=true;
		slt.dataType=""
	}else{
		slt.disabled=false;
		slt.dataType="Require"
		slt.msg="票卡子类型不能为空!"
	}
}

//函数名称：setInitEnableCardMon
//函数功能：根据票卡类型控制面值是否可录入(初始化订单)
//入口参数：窗体或框架名
//创建日期：2006-08-30
//程序编制：yangjihe

function setInitEnableCardMon(formName,sltdMainCode,sltdSubCode,stldPara)
{
	var sltdMainCardCode = document.forms[formName].all[sltdMainCode];
	var sltdSubCardCode = document.forms[formName].all[sltdSubCode];
	var sltdCardPara = document.forms[formName].all[stldPara];

	var maincode= sltdMainCardCode.value;
	var subcode= sltdSubCardCode.value;
	var cardvalue=sltdCardPara.value;

	var values = cardvalue.split(":");

	var count = 0;
	var i=-1;
	var j=-1;
	
	//当前日期+日期数量
	var  curdate = new Date();
	var  perioddate = new Date();

	var d, s, t,m,cd,strm,strcd;
  var MinMilli = 1000 * 60;
	var HrMilli = MinMilli * 60;
  var DyMilli = HrMilli * 24;
	var detailName="clearStart";
	var listName="detail";

	if(cardvalue.indexOf(":")==-1)
		return;

	for(i=0;i<values.length;i++)
	{
		if(values[i].indexOf(",")==-1)
			continue;
    var objs = values[i].split(",");

		if(objs[0]==maincode&& objs[1]==subcode)
		{
	

		}
	}
	
	alert("该车票还未配置参数，不允许生成订单！");
	setOperation(formName,"op_browse");
	resetListForm(listName);
	setAlwaysEnableForObj(formName,false);
	setDetailFormEnabled("detail",false);
}

//函数名称：setEnableCardMon
//函数功能：根据票卡类型控制面值是否可录入(预赋值订单)
//入口参数：窗体或框架名
//创建日期：2006-08-30
//程序编制：yangjihe
function setEnableCardMon(formName,sltdMainCode,sltdSubCode,stldPara,stldcardMon,stldperiod,stlddeposit,stldpicperiod,sltdcardmon1,sltlcccode,sltstationcode,sltcardstartava,sltcardpicstartava)
{
	var sltdMainCardCode = document.forms[formName].all[sltdMainCode];
	var sltdSubCardCode = document.forms[formName].all[sltdSubCode];
	var sltdCardPara = document.forms[formName].all[stldPara];

	var cardMon= document.forms[formName].all[stldcardMon];	
	var period= document.forms[formName].all[stldperiod];
	var deposit= document.forms[formName].all[stlddeposit];	
	var picperiod= document.forms[formName].all[stldpicperiod];
	var cardMon1= document.forms[formName].all[sltdcardmon1];
	
	//var stationCheck= document.forms[formName].all[sltstationCheck];
	var lcccode= document.forms[formName].all[sltlcccode];	
	var stationcode= document.forms[formName].all[sltstationcode];
	var cardstartava= document.forms[formName].all[sltcardstartava];	
	var cardpicstartava= document.forms[formName].all[sltcardpicstartava];	

	
	var maincode= sltdMainCardCode.value;
	var subcode= sltdSubCardCode.value;
	var cardvalue=sltdCardPara.value;

	var values = cardvalue.split(":");
	var count = 0;
	var i=-1;
	var j=-1;

	//当前日期+日期数量
	var  curdate = new Date();
	var  perioddate = new Date();

	var d, s, t,m,cd,strm,strcd;
  var MinMilli = 1000 * 60;
  var HrMilli = MinMilli * 60;
  var DyMilli = HrMilli * 24;
  var detailName="clearStart";
	var listName="detail";

	if(cardvalue.indexOf(":")==-1)
		return;

	for(i=0;i<values.length;i++){
		if(values[i].indexOf(",")==-1)
			continue;
    var objs = values[i].split(",");
    
    //if (objs[0]==null||objs[1]==null){
    //cardMon.value="";
		//period.value="";
    //return;
    //}
    
		if(objs[0]==maincode&& objs[1]==subcode){
			if (objs[3]=="1"){										//允许预赋值
				cardMon.disabled=false;
				cardMon.value=objs[2];
				//cardMon.max=objs[6];								        //设置上限
    		//cardMon.msg="预赋值金额范围为："+"0"+"<-->"+objs[6]+"分";
    		
			}else{																//不允许预赋值
	      cardMon.value=0;
				cardMon.disabled=true;
			}

			//有效期
			if (maincode!="01"){
   			d = new Date();
   			t = d.getTime();
   			perioddate.setTime(t+objs[4]*DyMilli);
				m=perioddate.getMonth()+1;
				strm=m.toString(10);
				if (strm.length<2)
					strm="0"+strm;
				cd=perioddate.getDate();
				strcd=cd.toString(10);
				if (strcd.length<2)
					strcd="0"+strcd;
				period.value=perioddate.getFullYear()+"-"+strm+"-"+strcd;

				period.readonly=true;				
				picperiod.src="";
				picperiod.width=1;
				picperiod.height=1;				
				cardMon1.disabled=true;		
								
	      //stationCheck.disabled=true;
	      if(lcccode !=null)
	      	lcccode.disabled=true;	
	      if(stationcode !=null)
	      	stationcode.disabled=true;
	      							
			}else{
				picperiod.src="./images/calendar.gif";
				picperiod.width=12;
				picperiod.height=12;								
				period.value="";		
				period.readonly=false;
				period.disabled=false;	
				cardMon1.disabled=false;	
				
				//stationCheck.disabled=false;
				if(lcccode !=null)
	      	lcccode.disabled=false;	
	    //  alert("test");
	      if(stationcode !=null)
	      	stationcode.disabled=false;
			}		
			
			//2008-04-21 新增 控制乘次票生效日期
			if (maincode=="03"){				        
	     	 	cardstartava.disabled=false;

					cardpicstartava.src="./images/calendar.gif";
					cardpicstartava.width=12;
					cardpicstartava.height=12;
     	 	
	     	 	
	     	 	cardstartava.value=""
			}else{
					cardpicstartava.src="";
					cardpicstartava.width=1;
					cardpicstartava.height=1;		     	 	
						      	
	      	cardstartava.value="00000000"
	      	cardstartava.disabled=true;
			}
					
			if (deposit!= null)		//押金
			{				
				deposit.value=objs[5];
				deposit.disabled=true;
			}
			
			return;
		}
	}
	
	alert("该车票还未配置参数，不允许生成订单！");
	cardMon.msg="面值为大于等于零的整数!";
	setOperation(formName,"op_browse");
	resetListForm(listName);
	setAlwaysEnableForObj(formName,false);
	setDetailFormEnabled("detail",false);
}

//函数名称：setReCode
//函数功能：根据票卡类型控制面值是否可录入(重编码订单)
//入口参数：窗体或框架名
//创建日期：2006-08-30
//程序编制：yangjihe
function setReCode(formName,sltdMainCode,sltdSubCode,stldPara,stldcardMon,stldperiod,stldpicperiod){

	var sltdMainCardCode = document.forms[formName].all[sltdMainCode];
	var sltdSubCardCode = document.forms[formName].all[sltdSubCode];
	var sltdCardPara = document.forms[formName].all[stldPara];

	var cardMon= document.forms[formName].all[stldcardMon];
	var period= document.forms[formName].all[stldperiod];

	var picperiod= document.forms[formName].all[stldpicperiod];
	
	var maincode= sltdMainCardCode.value;
	var subcode= sltdSubCardCode.value;
	var cardvalue=sltdCardPara.value;

	var values = cardvalue.split(":");
	var count = 0;
	var i=-1;
	var j=-1;

	//当前日期+日期数量
	var  curdate = new Date();
	var  perioddate = new Date();

	var d, s, t,m,cd,strm,strcd;
  var MinMilli = 1000 * 60;
  var HrMilli = MinMilli * 60;
  var DyMilli = HrMilli * 24;
  var detailName="clearStart";
	var listName="detail";

	if(cardvalue.indexOf(":")==-1)
		return;

	for(i=0;i<values.length;i++){
		if(values[i].indexOf(",")==-1)
			continue;
		var objs = values[i].split(",");

		if(objs[0]==maincode&& objs[1]==subcode){
			//cardMon.value=0;
			//cardMon.disabled=true;
			
			//有效期
			if (maincode!="01"){
   			d = new Date();
   			t = d.getTime();
   			perioddate.setTime(t+objs[4]*DyMilli);
				strm=perioddate.getMonth()+1;
				if (strm.length<2)
					strm="0"+strm;
				cd=perioddate.getDate();
				strcd=cd.toString(10);
				if (strcd.length<2)
					strcd="0"+strcd;
					
				period.value=perioddate.getFullYear()+"-"+strm+"-"+strcd;
				period.readonly=true;				
				picperiod.src="";
				picperiod.width=1;
				picperiod.height=1;			
			}else{			
				picperiod.src="./images/calendar.gif";
				picperiod.width=12;
				picperiod.height=12;								
				period.value="";		
				period.readonly=false;
				period.disabled=false;									
			}
			return;
		}
	}
	
	alert("该车票还未配置参数，不允许生成订单！");
	//cardMon.msg="面值为大于等于零的整数!";
	setOperation(formName,"op_browse");
	resetListForm(listName);
	setAlwaysEnableForObj(formName,false);
	setDetailFormEnabled("detail",false);
}

//函数名称：setEdit
//函数功能：当
//入口参数：窗体或框架名
//创建日期：2006-08-30
//程序编制：yangjihe

function setEdit(formName,sltdMainCode,sltdSubCode,stldPara,stldcardMon,stldperiod,stlddeposit)
{
	var sltdMainCardCode = document.forms[formName].all[sltdMainCode];
	var sltdSubCardCode = document.forms[formName].all[sltdSubCode];
	var sltdCardPara = document.forms[formName].all[stldPara];

	var cardMon= document.forms[formName].all[stldcardMon];
	var period= document.forms[formName].all[stldperiod];
	var deposit= document.forms[formName].all[stlddeposit];

	var maincode= sltdMainCardCode.value;
	var subcode= sltdSubCardCode.value;
	var cardvalue=sltdCardPara.value;

	var values = cardvalue.split(":");

	var count = 0;
	var i=-1;
	var j=-1;
	
	//当前日期+日期数量
	var  curdate = new Date();
	var  perioddate = new Date();

	var d, s, t,m,cd,strm,strcd;
  var MinMilli = 1000 * 60;
	var HrMilli = MinMilli * 60;
  var DyMilli = HrMilli * 24;
	var detailName="clearStart";
	var listName="detail";

	if(cardvalue.indexOf(":")==-1)
		return;

	for(i=0;i<values.length;i++)
	{
		if(values[i].indexOf(",")==-1)
			continue;
    var objs = values[i].split(",");

		if(objs[0]==maincode&& objs[1]==subcode)
		{
			if (objs[3]=="1"){										//允许预赋值
				cardMon.disabled=false;
				cardMon.value=0;
				cardMon.max=objs[6];								//设置上限
				//cardMon.msg="每次充值金额范围为："+"0"+"--"+objs[6]+"分";
			}else{																//不允许预赋值
				cardMon.max="999999";
				//cardMon.msg="每次充值金额范围为："+"0"+"--"+cardMon.max+"分";
				cardMon.value=0;
				cardMon.disabled=true;
			}

			//有效期
   		d = new Date();
	  	t = d.getTime();
   		perioddate.setTime(t+objs[4]*DyMilli);
			m=perioddate.getMonth()+1;
			strm=m.toString(10);
			if (strm.length<2)
				strm="0"+strm;
			cd=perioddate.getDate();
			strcd=cd.toString(10);
			if (strcd.length<2)
				strcd="0"+strcd;
			period.value=perioddate.getFullYear()+"-"+strm+"-"+strcd;
			period.disabled=true;
			if (deposit!= null)		//押金
			{
				deposit.value=objs[5];
				deposit.disabled=true;
			}
			return;
		}
	}
	
	cardMon.msg="面值为大于等于零的整数!";
	setOperation(formName,"op_browse");
	resetListForm(listName);
	setAlwaysEnableForObj(formName,false);
}


function setCardMon(formName,cardMon,sltcardMon)
{
	var cardmon = document.forms[formName].all[cardMon];
	var sltcardmon = document.forms[formName].all[sltcardMon];
	cardmon.value=sltcardmon.value;
}
function submitCardForm(formName,sltcardMon,sltProNum,sltCardPerAva,sltEsOperId,sltMainCode,sltSubCode){  
  	var frm = document.forms[formName];
  	
  	var cardmon = document.forms[formName].all[sltcardMon];
  	
  	var pronum = document.forms[formName].all[sltProNum];
  	var cardperava = document.forms[formName].all[sltCardPerAva];
  	var esoperid = document.forms[formName].all[sltEsOperId];
  	
  	var maincode = document.forms[formName].all[sltMainCode];
  	var subcode = document.forms[formName].all[sltSubCode];

  	if (maincode.value==""){
			alert("请选择车票主类型！");	
  		return;  		  		
  	}
  	
  	if (subcode.value==""){
			alert("请选择车票子类型！");	
  		return;  		  		
  	}
  	
  	if (pronum.value==""){			
			alert("请录入订单数量！");	
  		return;  		  		
  	}else{
			if (pronum.value==0){
				alert("订单数量为大于零的整数！");	
  			return;  		  		  	
  		}
  	}
  	  	
  	if (cardmon.value==""){
			alert("请录入或选择车票面值！");
  		return;  		  		
  	}

  	if (cardperava.value==""){
			alert("请录入车票有效期！");
  		return;  		  		
  	}
  	
  	if (esoperid.value==""){
			alert("请选择制票员ID！");
  		return;  		  		
  	}
	
  	if (cardmon.value==0){
  		if(confirm("订单中预赋值金额为零，是否继续？")==false){
	    	return ;
	    }else{
	    	if (Validator.Validate(frm,2)==true){
	    		frm.submit();
	    	}				
			}
		}else{
	    	if (Validator.Validate(frm,2)==true){
	    		frm.submit();
	    	}	
		}
}

Validator = {
	phyCard				:/^([0-9A-Fa-f]|[A-F0-9a-f]|[a-fA-F0-9]|[a-f0-9A-F])+$/,
	IPAddress			:/^[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}$/,
	ICCSDate			:/^[0-9]{4}-[0-9]{2}-[0-9]{2}$/,
	ICCSDateTime	:/^[0-9]{4}-[0-9]{2}-[0-9]{2} [0-9]{2}:[0-9]{2}:[0-9]{2}$/,
	Require 			: /.+/,
	NotNull 			: /.+/,
	Email 				: /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/,
	Phone 				: /^((\(\d{2,3}\))|(\d{3}\-))?(\(0\d{2,3}\)|0\d{2,3}-)?[1-9]\d{6,7}(\-\d{1,4})?$/,
	Mobile 				: /^((\(\d{2,3}\))|(\d{3}\-))?13\d{9}$/,
	Url 					: /^http:\/\/[A-Za-z0-9]+\.[A-Za-z0-9]+[\/=\?%\-&_~`@[\]\':+!]*([^<>\"\"])*$/,
	IdCard 				: /^\d{15}(\d{2}[A-Za-z0-9])?$/,
	Currency 			: /^\d+(\.\d+)?$/,
	Number 				: /^\d+$/,
	Zip 					: /^[1-9]\d{5}$/,
	QQ 						: /^[1-9]\d{4,8}$/,
	Integer 			: /^[-\+]?\d+$/,
	integer 			: /^[+]?\d+$/,
	Double 				: /^[-\+]?\d+(\.\d+)?$/,
	double 				: /^[+]?\d+(\.\d+)?$/,
	English 			: /^([A-Za-z]|[,\!\*\.\ \(\)\[\]\{\}<>\?\\\/\'\"])+$/,
	Chinese 			: /^[\u0391-\uFFE5]|[,\!\*\.\ \(\)\[\]\{\}<>\?\\\/\'\"]+$/,
	Username 			: /^[a-z]\w{3,}$/i,
	id						:/^[0-9]\d{0,18}$/,
	order					:/^[0-9]\d{0,10}$/,
	struts_id			:/^[0-9]\d{0,2}$/,
	struts_value	:/^[0-9]\d{0}$/,
	iccs_value		:/^[0-9]\d{0}$/,
	UnSafe 				: /^(([A-Z]*|[a-z]*|\d*|[-_\~!@#\$%\^&\*\.\(\)\[\]\{\}<>\?\\\/\'\"]*)|.{0,5})$|\s/,
	IsSafe 				: function(str){return !this.UnSafe.test(str);},
	SafeString 		: "this.IsSafe(value)",
	Filter 				: "this.DoFilter(value, getAttribute('accept'))",
	Limit 				: "this.limit(value.length,getAttribute('min'), getAttribute('max'))",
	LimitB 				: "this.limit(this.LenB(value), getAttribute('min'), getAttribute('max'))",
	Date 					: "this.IsDate(value, getAttribute('min'), getAttribute('format'))",
	Repeat 				: "value == document.getElementsByName(getAttribute('to'))[0].value",
	ThanDate 			: "value >= document.getElementsByName(getAttribute('to'))[0].value",
	thanDate 			: "value > document.getElementsByName(getAttribute('to'))[0].value",
	Range 				: "getAttribute('min') <= (value|0) && (value|0) <= getAttribute('max')",
	Compare 			: "this.compare(value,getAttribute('operator'),getAttribute('to'))",
	Custom 				: "this.Exec(value, getAttribute('regexp'))",
	Group 				: "this.MustChecked(getAttribute('name'), getAttribute('min'), getAttribute('max'))",
	NotEmpty			:"this.IsNotEmpty(value)",
	dateDiff			:"this.dateTimeDiff(getAttribute('name'), getAttribute('to'))",
	ErrorItem 		:[document.forms[0]],
	ErrorMessage 	: [],
	IsNotEmpty		:function(ctrValue)
	{
		if(ctrValue ==null)
		return false;
		//alert(ctrValue);
		for(i=0;i<ctrValue.length;i++){
		//alert(ctrValue.charAt(i));
			if(ctrValue.charAt(i) !=' ')
				return true
		}
		return false;
	},
	Validate 			: function(theForm, mode)
	{
		var obj = theForm || event.srcElement;
		var count = obj.elements.length;
		this.ErrorMessage.length = 1;
		this.ErrorItem.length = 1;
		this.ErrorItem[0] = obj;
		for(var i=0;i<count;i++)
		{
				with(obj.elements[i]){
				var _dataType = "";
				//getAttribute("dataType");
				var _dataTypes = getAttribute("dataType");
				if(_dataTypes == null)
					continue;
				//alert("_dataTypes="+_dataTypes);
				var index =_dataTypes.indexOf("|");
				var _dataTypesArray = [];
				var result = true;
				if(index ==-1)
   					_dataTypesArray[0] =_dataTypes;
				else
    				_dataTypesArray = _dataTypes.split("|");
  
				for(j=0;j< _dataTypesArray.length;j++)
				{
					_dataType = _dataTypesArray[j];
					//alert("_dataType="+_dataType);
					if(typeof(_dataType) == "object" || typeof(this[_dataType]) == "undefined") 
						continue;
					this.ClearState(obj.elements[i]);
					if(getAttribute("require") == "false" && value == "") 
						continue;
					switch(_dataType)
					{
						case "Date" :
						case "Repeat" :
						case "Range" :
						case "Compare" :
						case "Custom" :
						case "Group" : 
						case "Limit" :
						case "LimitB" :
						case "SafeString" :
						case "Filter" :
						case "ThanDate" :
						case "dateDiff" :
						case "thanDate" :
						case "NotEmpty":
							if(!eval(this[_dataType]))
							{
								result = false;
								//this.AddError(i, getAttribute("msg"));
							}
							break;
						default :
							if(!this[_dataType].test(value))
							{
								//this.AddError(i, getAttribute("msg"));
								result = false;
							}
						break;
					}//end switch
	 			}//end for j
	 		if(!result)
				this.AddError(i, getAttribute("msg"));	 
			}//end with
		}// end for i
		if(this.ErrorMessage.length > 1)
		{
			mode = mode || 1;
			var errCount = this.ErrorItem.length;
			switch(mode)
			{
				case 2 :
					for(var i=1;i<errCount;i++)
						this.ErrorItem[i].style.color = "red";
				case 1 :
						alert(this.ErrorMessage.join("\n"));
						this.ErrorItem[1].focus();
				break;
				case 3 :
					for(var i=1;i<errCount;i++)
					{
						try
						{
							var span = document.createElement("SPAN");
							span.id = "__ErrorMessagePanel";
							span.style.color = "red";
							this.ErrorItem[i].parentNode.appendChild(span);
							span.innerHTML = this.ErrorMessage[i].replace(/\d+:/,"*");
						}	catch(e){
							alert(e.description);
						}
					}
					this.ErrorItem[1].focus();
					break;
				default :
					alert(this.ErrorMessage.join("\n"));
					break;
			}
			return false;
		}
		
		return true;
	},
	limit 			: function(len,min, max)
	{
		min = min || 0;
		max = max || Number.MAX_VALUE;
		return min <= len && len <= max;
	},
	LenB				: function(str)
	{
		return str.replace(/[^\x00-\xff]/g,"**").length;
	},
	ClearState 	: function(elem)
	{
		with(elem)
		{
			if(style.color == "red")
			style.color = "";
			var lastNode = parentNode.childNodes[parentNode.childNodes.length-1];
			if(lastNode.id == "__ErrorMessagePanel")
			parentNode.removeChild(lastNode);
		}
	},
	AddError 		: function(index, str)
	{
		this.ErrorItem[this.ErrorItem.length] = this.ErrorItem[0].elements[index];
		this.ErrorMessage[this.ErrorMessage.length] = this.ErrorMessage.length + ":" + str;
	},
	Exec 				: function(op, reg)
	{
		return new RegExp(reg,"g").test(op);
	},
	compare 		: function(op1,operator,op2)
	{
		switch (operator)
		{
			case "NotEqual":
				return (op1 != op2);
			case "GreaterThan":
				return (op1 > op2);
			case "GreaterThanEqual":
				return (op1 >= op2);
			case "LessThan":
				return (op1 < op2);
			case "LessThanEqual":
				return (op1 <= op2);
			default:
				return (op1 == op2); 
		}
	},
	MustChecked 	: function(name, min, max)
	{
		var groups = document.getElementsByName(name);
		var hasChecked = 0;
		min = min || 1;
		max = max || groups.length;
		for(var i=groups.length-1;i>=0;i--)
			if(groups[i].checked) hasChecked++;
				return min <= hasChecked && hasChecked <= max;
	},
	DoFilter 			: function(input, filter)
	{
		return new RegExp("^.+\.(?=EXT)(EXT)$".replace(/EXT/g, filter.split(/\s*,\s*/).join("|")), "gi").test(input);
	},
	
	IsDate : function(op, formatString)
	{
		formatString = formatString || "ymd";
		var m, year, month, day;
		switch(formatString)
		{
			case "ymd" :
					m = op.match(new RegExp("^((\\d{4})|(\\d{2}))([-./])(\\d{1,2})\\4(\\d{1,2})$"));
					if(m == null ) 
						return false;
					day = m[6];
					month = m[5]*1;
					year = (m[2].length == 4) ? m[2] : GetFullYear(parseInt(m[3], 10));
			break;
			case "dmy" :
					m = op.match(new RegExp("^(\\d{1,2})([-./])(\\d{1,2})\\2((\\d{4})|(\\d{2}))$"));
					if(m == null ) return false;
						day = m[1];
						month = m[3]*1;
						year = (m[5].length == 4) ? m[5] : GetFullYear(parseInt(m[6], 10));
			break;
			default :
			break;
		}
		if(!parseInt(month))
			return false;
			month = month==0 ?12:month;
			var date = new Date(year, month-1, day);
			return (typeof(date) == "object" && year == date.getFullYear() && month == (date.getMonth()+1) && day == date.getDate());
			function GetFullYear(y)
			{
				return ((y<30 ? "20" : "19") + y)|0;
			}
	},
 	dateTimeDiff:function dateDiff(datetime1,datetime2)
 	{
		var   date1 = new Date();
		var   date2 = new Date();
		var   diff  = new Date();
   	var M1= document.getElementById(datetime1).value;
		
		date1temp = new Date(M1.split("-")[0],M1.split("-")[1] - 1,M1.split("-")[2]);
		date1.setTime(date1temp.getTime());
		var M2= document.getElementById(datetime2).value;
		date2temp = new Date(M2.split("-")[0],M2.split("-")[1] - 1,M2.split("-")[2]);
		date2.setTime(date2temp.getTime());

		diff.setTime(Math.abs(date1.getTime() - date2.getTime()));
		timediff = diff.getTime();
      // alert(Math.floor(timediff / (1000 * 60 * 60 * 24)));
		if (Math.floor(timediff / (1000 * 60 * 60 * 24))<=90)
			return true;
		else
			return false;
	}
}


//取消按钮,并且判断所选择的记录是否为 "已处理订单"
function unSelectAllRecordNew(formName,headCheckName,name){
	
	var currentOperation=getCurrentOpertion(formName);	
	if (currentOperation == "op_modify" || currentOperation == "op_add")
		return false;

	var obj=document.all[headCheckName];
	var count=document.forms[formName].all["rowTickedCount"];
	var temp=count.value;
	var handleflag=0;		
	
	//取得当前行
	headEventObject=event.srcElement
	//判断当前行已处理状态
	headEventObject=event.srcElement
	if(headEventObject.type == "checkbox" && headEventObject.name == name)
	{
		setCurrentLine(formName,headEventObject.value);		
		var currLine=getCurrentLine(formName);		
		if (currLine != "")
		{
			var currObject=document.getElementById(currLine);
			var tmp=currObject.children.length
			if (tmp !=0){
				for (var i=0;i<tmp;i++)
				{
						var child=currObject.children[i];
						if (child.id != "")	{							
								var valTmp=child.innerHTML;
								//alert(child.innerHTML);
							 	if (valTmp=="未处理订单")
							 		 handleflag=1;	
						}
				}	
			}
		}
		
		if (handleflag==1)
		{						
		  
		  
		   	
		  if (headEventObject.checked){
				temp++;
				headEventObject.checked="1";	
			}
			else{
				temp--;
				headEventObject.checked="";	
			}	
		}else{
				headEventObject.checked="";
				alert("该订单不允许编辑或删除！");
		}						
	}

	obj.checked="";
	count.value=temp;

//改变操作模式（改变按钮的可用状态）
//	alert("temp="+temp);
	if (temp>0)
		setOperation(formName,"op_select");
	else
		setOperation(formName,"op_select_browse");
}


//全选按钮
function selectAllRecordNew(formName,headCheckName,name,listDivName,colNo,colNo1){
	var currentOperation=getCurrentOpertion(formName);
	if (currentOperation == "op_modify" || currentOperation == "op_add") return false;

	var obj=document.all[headCheckName];
	var count=document.forms[formName].all["rowTickedCount"];

	var flag=false;
	var temp=0;
	var listDiv = document.all[listDivName];
	var tbl = listDiv.children[0];
	if(tbl.tagName !='TABLE'){
//		alert("tbl.tagName="+tbl.tagName);
		return;
	}

  var checkCell = null;
  var checkCell1 = null;
    
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

		 checkCell1= tbl.rows[i].cells[colNo1];
		 
		var tmp=checkCell1.innerHTML;
		if (tmp=="未处理订单"){
			if (chk.name==name && chk.type=="checkbox")
			{
				if (obj.checked){
					chk.checked="1";
					flag=true;
				}
				else{
					chk.checked="";
					temp=0;
				}
				if (flag) temp++;
			}
		}
	}
	count.value=temp;

	//改变操作模式（改变按钮的可用状态）
	if (flag)
		if (getCurrentLine(formName) != "")
			setOperation(formName,"op_select");
		else
			setOperation(formName,"op_select_all");
	else
		if (getCurrentLine(formName) != "")
			setOperation(formName,"op_select_browse");
		else
			setOperation(formName,"op_browse");
}