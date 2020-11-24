
function setCommand(formName,value){
	if (value != ""){
		var command=document.forms[formName].all["command"];
		if (command !== null)
			command.value=value;

//		alert("command.value"+command.value);
	}
}

function setOperation(formName,opCode){
//	alert("setOperation");
	disableAllButtons(formName);
//	alert("disableAllButtons");
	//设置operation对象值为opCode
	setInitOpertion(formName,opCode);
//	alert("setInitOpertion");
	var initOpertion=getInitOpertion(formName);
//	alert("getInitOpertion");

	//获得当前操作opCode对应的可用按钮名称集合，其存储在操作名称对应的对象上
	var avriableOpertions=getAvriableOpertions(formName,initOpertion);
//	 alert("opCode="+opCode+"avriableOpertions="+avriableOpertions);
	//使可用按钮名称集合可用

	setAvriableButton(formName,avriableOpertions,false);
//	alert("setAvriableButton");

}

function disableAllButtons(formName){
    //	alert("formName="+formName);
      if(formName == null || formName =='')
            return;
      var frm =document.forms[formName];
      if(frm ==null)
            return;
            var buttons=frm.all["functions"];
            if (buttons !== null)
                            setAvriableButton(formName,buttons.value,true);
}


function setAvriableButton(formName,avriableOpertions,flag){
	//alert("avriableOpertions="+avriableOpertions);
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
					disableButton(formName,buttonName,flag);
					index_p=index_n;
				}
			}
			if (index_n == -1){
				//alert(avriableOpertions.substring(index_p,avriableOpertions.length));
				var buttonName=avriableOpertions.substring(index_p,avriableOpertions.length);
				//alert(buttonName);
				disableButton(formName,buttonName,flag);
			}
		}
		else{
			var index_n=avriableOpertions.length;
			//alert(avriableOpertions.substring(index_p,index_n));
			var buttonName=avriableOpertions.substring(index_p,index_n);
			//alert(buttonName);
			disableButton(formName,buttonName,flag);
		}

	}
}

function disableButton(formName,id,flag){
	var button=document.forms[formName].all[id];
	if (button !=null){
		//alert("id='" + id + "'");
		button.disabled=flag;
	}
}

//读取操作模式
function setInitOpertion(formName,value){
//	alert("formName="+formName);
	if (value != ""){
		var opertion=document.forms[formName].all["operation"];
		if (opertion !== null)
			opertion.value=value;
	}
}


function getInitOpertion(formName){
	var frm = opertion=document.forms[formName];
	if(frm ==null)
		return "";

	var opertion=document.forms[formName].all["operation"];
	if (opertion !== null)
		return opertion.value;
	else
		return "";
}

function getAvriableOpertions(formName,opCode){
	if (opCode!=""){
		var avariableOpertions=document.forms[formName].all[opCode];
		//alert("opCode="+opCode+" avariableOpertions.avriable="+avariableOpertions.avriable);

		if (avariableOpertions != null)
			return  getEffectiveAvailButton(formName,avariableOpertions.avriable);
		else
			return "";
	}
}


function setAvriableButton(formName,avriableOpertions,flag){
	//alert("avriableOpertions="+avriableOpertions);
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
					disableButton(formName,buttonName,flag);
					index_p=index_n;
				}
			}
			if (index_n == -1){
				//alert(avriableOpertions.substring(index_p,avriableOpertions.length));
				var buttonName=avriableOpertions.substring(index_p,avriableOpertions.length);
				//alert(buttonName);
				disableButton(formName,buttonName,flag);
			}
		}
		else{
			var index_n=avriableOpertions.length;
			//alert(avriableOpertions.substring(index_p,index_n));
			var buttonName=avriableOpertions.substring(index_p,index_n);
			//alert(buttonName);
			disableButton(formName,buttonName,flag);
		}

	}
}


//选择改变当前行 formName:提交窗体ID;object:this;detailForm:明细对应DIV ID
function clickResultRow(formName,object,detailFrom){
    
    //alert("currentOperation=");
	var currentOperation=getCurrentOpertion(formName);
//	alert("currentOperation="+currentOperation);

	if (currentOperation == "op_modify" || currentOperation == "op_add") return false;

	if (currentOperation == "op_select_all") setOperation(formName,"op_select");

	headEventObject=event.srcElement

	while(headEventObject.tagName!="TR"){
		headEventObject=headEventObject.parentElement
	}


	//取消原选择行的信息，及保存新选择的行信息
	var oldLine=getCurrentLine(formName);
	if (oldLine != ""){
		var oldObject=document.getElementById(oldLine);
		oldObject.style.background="#FFFFFF";
	}
	//设置当前行


	setCurrentLine(formName,headEventObject.id);
	//改变背景色


	headEventObject.style.background="#97CBFF";

	//改变操作模式（改变按钮的可用状态）

	setDeleteOrModify(formName);
	//填充明细框内容


	setDetailFormContents(formName,detailFrom);
}


function clickResultRowForCardAttribute(formName,object,detailFrom){

	var currentOperation=getCurrentOpertion(formName);
//	alert("currentOperation="+currentOperation);
	if (currentOperation == "op_modify" || currentOperation == "op_add") return false;

	if (currentOperation == "op_select_all") setOperation(formName,"op_select");

	headEventObject=event.srcElement

	while(headEventObject.tagName!="TR"){
		headEventObject=headEventObject.parentElement
	}
	
	//alert("headEventObject.tagName="+headEventObject.tagName);
	//alert("headEventObject.id="+headEventObject.id);
	//alert("left="+headEventObject.offsetLeft+" top="+headEventObject.offsetTop);
	//headEventObject.parentElement.parentElement.scrollTo(2,1000);
//	var rowScrollTop = headEventObject.offsetTop;
//	alert("top="+headEventObject.offsetTop);
//	var divOb = document.getElementById("clearStart");
//	divOb.scrollTop = rowScrollTop;
//   var tblOb = headEventObject.parentElement;
//   var tblHead =tblOb.rows[0];
 //  tblHead.scrollIntoView(true);
	//for(var i in divOb)
	// alert(i+"="+divOb[i]);
	//divOb.moveTo(100,100);
//	window.scrollTo(1000,1000);
//  for(var i in headEventObject){
 // 	alert(i+"="+headEventObject[i]);
 // }

	//取消原选择行的信息，及保存新选择的行信息
	var oldLine=getCurrentLine(formName);
	if (oldLine != ""){
		var oldObject=document.getElementById(oldLine);
		oldObject.style.background="#FFFFFF";
	}
	//设置当前行

	setCurrentLine(formName,headEventObject.id);
	//改变背景色

	headEventObject.style.background="#97CBFF";

	//改变操作模式（改变按钮的可用状态）
	setDeleteOrModify(formName);
	//填充明细框内容

	setDetailFormContentsForCardAttribute(formName,detailFrom);
}


function getCurrentOpertion(formName){
	var frm = document.forms[formName];
	if(frm ==null)
		return "";
	var opertion=document.forms[formName].all["operation"];
	if (opertion !== null)
		return opertion.value;
	else
		return "";
}


function getCurrentLine(formName){
	var rowSelected=document.forms[formName].all["rowSelected"];
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

function setCurrentLine(formName,lineId){
	var rowSelected=document.forms[formName].all["rowSelected"];
	if (rowSelected != null) rowSelected.value=lineId;
}

function setDeleteOrModify(formName){
	var opertion=document.forms[formName].all["operation"];
	if (opertion.value !== "op_select"){  //如果在op_select模式下则不用改变，否则改变为op_select_browse
		//改变操作模式（改变按钮的可用状态）
		//disableAllButtons();
		setOperation(formName,"op_select_browse")
	}
	else
		return false;
}


//动态改变明细框内容为当前行各个字段(列表当前行信息) (*************代码有待改善************)
//--输入参数：detailForm  明细框(DIV)的ID
function setDetailFormContents(formName,detailForm){
   // alert(detailForm);
   //alert("111");
	if (detailForm != ""){
		var detail=document.getElementById(detailForm);
	//	alert(detail);
		if (detail != null){
			var detailTable=detail.children[0];//table
			while (detailTable.children[0].tagName != "TR"){
				detailTable=detailTable.children[0];
			}

			var currentLine=getCurrentLine(formName);
	//		alert(currentLine);
			if (currentLine != ""){
				var currentObject=document.getElementById(currentLine);
				var tmp=currentObject.children.length
				if (tmp !=0){
					for (var i=0;i<tmp;i++){
						var child=currentObject.children[i];
						//alert("child.id=" + child.id + " detail=" + detail.id + " tmpId=" + "d_" + child.id);

						/******** 设置detailForm **********/
						if (child.id != ""){
							//alert(child.id + " to find " + "d_" + child.id);
							var tmpObj=document.getElementById("d_" + child.id);//明细输入框id加前缀 d_
							if (tmpObj != null && tmpObj != "undefined"){
								switch (tmpObj.tagName){
									case "INPUT":{
										switch (tmpObj.type){
											case "text":{
												tmpObj.value=child.innerHTML;	
												if(child.children[0] !=null && child.children[0].tagName == 'A')
													tmpObj.value=child.children[0].innerHTML;
												break;
											}
											case "radio":{
												var radioGroup =document.getElementsByName("d_" + child.id);
//												alert("name="+"d_" + child.id);
												if(radioGroup == null)
													break;
//												alert("radioGroup not empty");
												for(var k=0;k<radioGroup.length;k++){
													var radioGroupChild =radioGroup[k];
													var cmpValue =-1;
//													alert("radioGroupChild.value="+radioGroupChild.value);
//													alert("child.innerHTML="+child.innerHTML);
													if(child.innerHTML =="是")
														cmpValue =1;
													if(child.innerHTML =="否")
														cmpValue =0;
//													alert("cmpValue="+cmpValue);
													if(radioGroupChild.value ==cmpValue)
														{
															//radioGroupChild.defaultChecked = true;
															radioGroupChild.checked = true;
															break;
														}
												}

												break;
											}
										}//switch
										break;
									}
									case "SELECT":{
									 if(tmpObj.type =="select-one"){
                                                                                if(tmpObj.options.length < 1){
                                                                                    tmpObj.options[tmpObj.options.length] = new Option(child.innerHTML, child.innerHTML); 
                                                                                }else{
                                                                                    for(j=0;j<tmpObj.options.length;j++){
                                                                                            if(tmpObj.options[j].value == child.innerHTML || tmpObj.options[j].text ==child.innerHTML){
                                                                                                    tmpObj.options[j].selected ="true";
                                                                                                    break;
                                                                                            }
                                                                                            if(j==tmpObj.options.length-1){
                                                                                                tmpObj.options[0].selected ="true";
                                                                                                tmpObj.options[0].value = child.innerHTML;
                                                                                                tmpObj.options[0].text = child.innerHTML;
                                                                                            }
                                                                                    }
                                                                                }
									}
									if(tmpObj.type =="select-multiple"){
                                                                                if(tmpObj.options.length < 1){
                                                                                    tmpObj.options[tmpObj.options.length] = new Option(child.innerHTML, child.innerHTML); 
                                                                                }else{
                                                                                    for(j=0;j<tmpObj.options.length;j++){
                                                                                            var idx = child.innerHTML.indexOf(tmpObj.options[j].text);
                                                                                            var idx1 =child.innerHTML.indexOf(tmpObj.options[j].value);
                                                                                            tmpObj.options[j].selected = false;
                                                                            //		alert("tmpObj.options[j].selected="+tmpObj.options[j].selected);
                                                                            //		alert("tmpObj.options[j].defaultSelected="+tmpObj.options[j].defaultSelected);

                                                                                            if(idx !=-1 || idx1 !=-1){
                                                                                                    tmpObj.options[j].selected =true;
                                                                                            }
                                                                                    }
                                                                                }

									}
										break;
									}
								}
							}

							tmpObj=document.getElementById("d1_" + child.id);//明细输入框id加前缀 d_
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

function setDetailFormContentsForCardAttribute(formName,detailForm){
	if (detailForm != ""){
		var detail=document.getElementById(detailForm);
		if (detail != null){
			var detailTable=detail.children[0];//table
			while (detailTable.children[0].tagName != "TR"){
				detailTable=detailTable.children[0];
			}

			var currentLine=getCurrentLine(formName);
			if (currentLine != ""){
				var currentObject=document.getElementById(currentLine);
				var tmp=currentObject.children.length
				if (tmp !=0){
					for (var i=0;i<tmp;i++){
						var child=currentObject.children[i];
						//alert("child.id=" + child.id + " detail=" + detail.id + " tmpId=" + "d_" + child.id);

						/******** 设置detailForm **********/
						if (child.id != ""){
							//alert(child.id + " to find " + "d_" + child.id);
							var tmpObj=document.getElementById("d_" + child.id);//明细输入框id加前缀 d_
							if (tmpObj != null && tmpObj != "undefined"){
								switch (tmpObj.tagName){
									case "INPUT":{
										switch (tmpObj.type){
											case "text":{
												tmpObj.value=child.innerHTML;
												break;
											}
											case "radio":{
												var radioGroup =document.getElementsByName("d_" + child.id);
//												alert("name="+"d_" + child.id);
												if(radioGroup == null)
													break;
//												alert("radioGroup not empty");
												for(var k=0;k<radioGroup.length;k++){
													var radioGroupChild =radioGroup[k];
													var cmpValue =-1;
//													alert("radioGroupChild.value="+radioGroupChild.value);
//													alert("child.innerHTML="+child.innerHTML);
													if(child.innerHTML =="是"){
													  if(radioGroupChild.name =='d_isEntryControl')
													   	cmpValue =0;
													  else
															cmpValue =1;
													}
													if(child.innerHTML =="否"){
														if(radioGroupChild.name =='d_isEntryControl')
														  cmpValue =1;
														 else
															cmpValue =0;
													}
//													alert("cmpValue="+cmpValue);
													if(radioGroupChild.value ==cmpValue)
														{
															//radioGroupChild.defaultChecked = true;
															radioGroupChild.checked = true;
															break;
														}
												}

												break;
											}
										}//switch
										break;
									}
									case "SELECT":{
									 if(tmpObj.type =="select-one"){
										for(j=0;j<tmpObj.options.length;j++){
											if(tmpObj.options[j].value == child.innerHTML || tmpObj.options[j].text ==child.innerHTML){
												tmpObj.options[j].selected ="true";
												break;
											}
										}
									}
									if(tmpObj.type =="select-multiple"){
										for(j=0;j<tmpObj.options.length;j++){
											var idx = child.innerHTML.indexOf(tmpObj.options[j].text);
											var idx1 =child.innerHTML.indexOf(tmpObj.options[j].value);
											tmpObj.options[j].selected = false;
									//		alert("tmpObj.options[j].selected="+tmpObj.options[j].selected);
									//		alert("tmpObj.options[j].defaultSelected="+tmpObj.options[j].defaultSelected);

											if(idx !=-1 || idx1 !=-1){
												tmpObj.options[j].selected =true;
											}
										}

									}
										break;
									}
								}
							}

							tmpObj=document.getElementById("d1_" + child.id);//明细输入框id加前缀 d_
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



function overResultRow(formName,object){
    //alert(formName);
	var frm = document.forms[formName];
        
	if(frm ==null)
		return;
	var rowSelected=frm.all["rowSelected"];
	if(rowSelected == null)
		return;

	object.style.cursor="hand";
	if (object !=null&&object.id !=null&&object.id != rowSelected.value)
	{
		object.style.background="#E8E8E8";
	}
}


function outResultRow(formName,object){
	var frm = document.forms[formName];
	if(frm == null)
		return;
	var rowSelected=frm.all["rowSelected"];
	if(rowSelected ==null)
		return;

	if (object !=null&&object.id !=null&&object.id != rowSelected.value)
	{
		object.style.background="#FFFFFF";
	}
}


//全选按钮

/*
function selectAllRecord(formName,headCheckName,name){
	var currentOperation=getCurrentOpertion(formName);
	if (currentOperation == "op_modify" || currentOperation == "op_add") return false;

	var obj=document.all[headCheckName];
	var count=document.forms[formName].all["rowTickedCount"];

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
*/
//全选按钮

function selectAllRecord(formName,headCheckName,name,listDivName,colNo){
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


//取消全选按钮
function unSelectAllRecord(formName,headCheckName,name){
	var currentOperation=getCurrentOpertion(formName);
	if (currentOperation == "op_modify" || currentOperation == "op_add") return false;

	var obj=document.all[headCheckName];
	var count=document.forms[formName].all["rowTickedCount"];
	var temp=count.value;

	headEventObject=event.srcElement
	if(headEventObject.type == "checkbox" && headEventObject.name == name){
		//alert("value=" + headEventObject.value);
		if (headEventObject.checked)
			temp++;
		else
			temp--;
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

function JM_PowerListByIDs(clearStart,DataTable,colNum){
        //点击对象
	headEventObject=event.srcElement;
        
	//点击行，找到tr行，即表头行
	while(headEventObject.tagName!="TR"){
		headEventObject=headEventObject.parentElement;
	}

        //改变点击列的类风格
	for (i=0;i<headEventObject.children.length;i++){
		if (headEventObject.children[i]!=event.srcElement){
			headEventObject.children[i].className='listTableHead';
		}
	}

	var tableRows=0;
        
	//表对象 全部tr集
	trObject=DataTable.children[0].children;
	//表行数
	if(trObject.length>1000){
		if(confirm("排序的数据为"+trObject.length+"条记录，其速度较慢，是否继续")==false)
			return;
	}
        //计算总行数
	for (i=0;i<trObject.length;i++){
		Object=DataTable.children[0].children[i];
		tableRows=(trObject[i].id=='ignore')?tableRows:tableRows+1;
                //重置表头样式
                if(trObject[i].id=='ignore'){
                    var cssText = "position: relative;top: expression(this.offsetParent.scrollTop);";
                    trObject[i].style.cssText = cssText;
                }
	}

	var trinnerHTML=new Array(tableRows)
	var tdinnerHTML=new Array(tableRows)
	var tdNumber=new Array(tableRows)
	var i0=0
        var i1=0

        //存储表行及单击列数据到数组
	for (i=0;i<trObject.length;i++){
		if (trObject[i].id!='ignore'){
			trinnerHTML[i0]=trObject[i].innerHTML;
			if(trObject[i].children[colNum]==null ||trObject[i].children[colNum].innerHTML ==null){
                            tdinnerHTML[i0]="";
			}else{
                            tdinnerHTML[i0]=trObject[i].children[colNum].innerHTML;
                            if(parseFloat(tdinnerHTML[i0])){
                                tdinnerHTML[i0] = parseFloat(tdinnerHTML[i0]);
                            }
                        }
			tdNumber[i0]=i;
			i0++;
		}
	}
	sourceHTML=clearStart.innerHTML;
	
        //堆排序	
        var arrayResult =heapSort(tdinnerHTML);
	tdNumber =heepSortNum;

	var showshow='';
	var numshow='';
	for (i=0;i<tableRows;i++){
		showshow=showshow+tdinnerHTML[i]+'\n';
		numshow=numshow+tdNumber[i]+'|';
	}
	sourceHTML_head=sourceHTML.split("<TBODY>");
	numshow=numshow.split("|");
	var trRebuildHTML='';
	if (event.srcElement.className=='listHeadClicked'){
		for (i=0;i<tableRows;i++){
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
	if(trObject[tableRows+1] !=null){
		DataRebuildTable=sourceHTML_head[0]+trObject[0].outerHTML+trRebuildHTML+trObject[tableRows+1].outerHTML+'</TABLE>';
	}else{
		DataRebuildTable=sourceHTML_head[0]+trObject[0].outerHTML+trRebuildHTML+'</TABLE>';
        }
	clearStart.innerHTML='';
	clearStart.innerHTML=DataRebuildTable;
}

//*********************************************  页面排序  结束*************************************
//堆排序

var heepSortData,heepSortNum;
function sift(n,s) {
//	  alert("n="+n+" s="+s);
    var t, k, j, tn;
    t = heepSortData[s];
    k = s;
    j = 2 * k + 1;
    tn = heepSortNum[s];
    while (j < n) {
      if (j < n - 1 && heepSortData[j] < heepSortData[j + 1])
        j++;
      if (t < heepSortData[j]) {
        heepSortData[k] = heepSortData[j];
        heepSortNum[k] = heepSortNum[j];
        k = j;
        j = 2 * k + 1;
      }
      else
        break;
      heepSortData[k] = t;
      heepSortNum[k] = tn;
    }
}
  
function heapSort(e) {
    var i, k, t, tn;    
    var n = e.length;
    var n1 =0;
    var es = new Array(e.length);
    var m =0;
    heepSortNum = new Array(e.length);
    heepSortData =e;//new Array(e.length);
    for (i = 0; i < e.length; i++){
  //  	heepSortData[i]=e[i];
      heepSortNum[i] = i;
//      alert(heepSortData[i]);
    }
    m =n/2;
    if(n%2 !=0)
    	m =(n-1)/2;
    for (i = m - 1; i >= 0; i--) {
      sift(n, i);
    }
    for (k = n - 1; k >= 1; k--) {
      t = heepSortData[0];
      heepSortData[0] = heepSortData[k];
      heepSortData[k] = t;
      
      tn = heepSortNum[0];
      heepSortNum[0] = heepSortNum[k];
      heepSortNum[k] = tn;

      sift(k, 0);
    }
    for (i = 0; i < heepSortData.length; i++){
     heepSortNum[i] = heepSortNum[i]+1;
    }

  }

function setSubmitForm(formName){
//	alert("formName="+formName);
   var frm = document.forms[formName];
//   alert("frm.action="+frm.action);

   var index = frm.action.indexOf("?");
   var path="";
   if(index == -1)
   	path=frm.action;
   else
   	path =frm.action.substring(0,index);
   frm.action = path+"?submitForm="+formName;
 //  alert("frm.action="+frm.action);
}


function btnClickForDecisionReport(formName,controlNames){
	setOperation(formName,"op_statistic");
	setCommand(formName,"statistic");
	setSubmitForm(formName);
	setControlNames(formName,controlNames);
	statisticAction(formName);
}

//formName 提交窗体名称；reportType报表类型；controlNames查找报表代码的控件名称按顺序用＃分隔;tableNamex显示列表名称
function btnClickForReport(formName,reportType,controlNames){
	if(formName =='')
		return;
//	if(controlNames =='')
//		return;
	if(reportType =='')
		return;
	var frm = document.forms[formName];
	if(frm == null)
		return;
	if(!Validator.Validate(frm,'1'))
		return;
	var tableName = "reportList";
	var ctrNames ="";

	switch(reportType){
		case "1":{
			if(!yearReportAction(formName,controlNames,tableName))
				return;
			ctrNames = "year#"+controlNames;
			break;
		}
		case "2":{
			if(!monthReportAction(formName,controlNames,tableName))
			 return;
			ctrNames = "year#month#isBalanceDate#balanceDate#"+controlNames;
			break;
		}
		case "3":{
			if(!dateReportAction(formName,controlNames,tableName))
			  return ;
			ctrNames = "date#isBalanceDate#"+controlNames;
			break;
		}
		default:{
			break;
	}
}
	setOperation(formName,"op_reportQuery");
	setCommand(formName,"reportQuery");
	setSubmitForm(formName);
	setControlNames(formName,ctrNames);
	queryReport(formName,controlNames);

}


function queryReport(formName,controlNames){

	document.forms[formName].submit();
}


function dateReportAction(formName,controlNames,tableName){
	if(formName =="")
		return false;
//	if(controlNames =="")
//		return false;
	if(tableName =="")
		return false;
	if(!checkTime(formName,'3'))
		return false;

	var dateOb = document.forms[formName].all['date'];
	if(dateOb == null)
		return false;

	var date = dateOb.value;
	if(date ==""){
		alert("请选择报表日期");
		hideReport(tableName);
		return false;
	}
	var idx = date.indexOf("-");
	var idx1 = date.indexOf("-",idx+1);
	date = date.substring(0,idx)+date.substring(idx+1,idx1)+date.substring(idx1+1);
	var rptCode = getReportCode(formName,controlNames);
	if(rptCode ==""){
		hideReport(tableName);
		return false;
	}
	var fleName = date+"."+rptCode;
	setReportNamePrefix(formName,fleName);
	return true;

	/*
	var fleName = date+"-"+rptCode+".pdf";
	var caption = dateOb.value;
	caption = addCaptionInfo(formName,controlNames,caption);
	displayReport(tableName,fleName,caption);
	*/

}


function setLinkText(formName,reportType,controlNames){
	if(formName =="")
		return;
	var frm = document.forms[formName];
	if(frm == null)
		return;
	var caption = "";
	var tableName="reportList";
	/*
	switch(reportType){
		case "1" :{
			var yearOb = frm.all['year'];
			caption = yearOb.value;
			break;
		}
		case "2" :{
			var yearOb = frm.all['year'];
			var monthOb = frm.all['month'];
			caption = yearOb.value+'-'+monthOb.value;
			break;
		}
		case "3" :{
			var dateOb = frm.all['date'];
			caption = dateOb.value;
			break;
		}
	}
	*/
	caption = addCaptionInfo(formName,controlNames,caption);
	setTableLinkText(tableName,caption);
}


function setReportNamePrefix(formName,reportNamePrefix){
	var frm = document.forms[formName];
	var ob = frm.all['ReportNamePrefix'];
	ob.value = reportNamePrefix;
}


function monthReportAction(formName,controlNames,tableName){
	if(formName =="")
		return false;
//	if(controlNames =="")
//		return false;
	if(tableName =="")
		return false;
	if(!checkTime(formName,'2'))
		return false;

	var yearOb = document.forms[formName].all['year'];
	var monthOb = document.forms[formName].all['month'];
	var balanceDateOb = null;
	var balanceDate ="";
	if(yearOb == null)
		return false;
	if(monthOb == null)
		return false;
	var month = yearOb.value+monthOb.value+"00";
	var rptCode = getReportCode(formName,controlNames);
	if(rptCode ==""){
		hideReport(tableName);
		return false;
	}
	var fleName = month+"."+rptCode;
	/*
	if(isBalanceDate(formName))
	{
		balanceDateOb =document.forms[formName].all['balanceDate'];
		balanceDate =balanceDateOb.value;
		if(checkBalanceDate(balanceDate)==false)
			return false;
		

		balanceDate = balanceDate.substring(0,4)+balanceDate.substring(5,7)+balanceDate.substring(8,10);
		fleName = balanceDate+"."+rptCode;
	}
	*/
	setReportNamePrefix(formName,fleName);
	return true;
	/*
	var fleName = month+"-"+rptCode+".pdf";
	var caption = yearOb.value+"-"+monthOb.value;
	caption = addCaptionInfo(formName,controlNames,caption);
	displayReport(tableName,fleName,caption);
	*/

}


function checkBalanceDate(balanceDateValue){
	var rg = new RegExp("^\\d\\d\\d\\d-\\d\\d-\\d\\d$");
	var rst =rg.exec(balanceDateValue);
	if(rst == null){
		alert("清算日期格式应为yyyy-mm-dd");
		return false;
	}
	if(checkDate(balanceDateValue)==false){
		return false;
	}
	return true;
	
}


function isBalanceDate(formName){
	var frm = document.forms[formName];
	if(frm == null)
		return false;
	var isCheckOb = frm.all['isBalanceDate'];
	if(isCheckOb == null)
		return false;
	if(isCheckOb.checked)
		return true;
	return false;
}


function checkTime(formName,timeType){
	var frm = document.forms[formName];
	var date = new Date();
	var year = date.getFullYear();
	var month = date.getMonth()+1;
	var day = date.getDate();
  var sYear = year+"";
  var sMonth =month+"";
  var sDay = day+"";
  var sDate ="";
  if(sMonth.length <2)
    sMonth = "0"+sMonth;
  if(sDay.length <2)
    sDay = "0"+sDay;
  sDate = sYear+"-"+sMonth+"-"+sDay;

	var selYear = "";
	var selMonth ="";
	var selDate = "";

	switch(timeType){
		case "1" :{
			selYear = frm.all['year'].value;
			if(selYear >sYear){
				alert("选择的年份不能大于当前年");
				return false;
			}
			break;
		}
	 case "2" :{
	 	  selYear = frm.all['year'].value;
	 	  selMonth = frm.all['month'].value;
			if(selYear+selMonth >sYear+sMonth){
				alert("选择的年月不能大于当前年月");
				return false;
			}
			break;
		}
		case "3" :{
			selDate = frm.all['date'].value;
			if(selDate >sDate){
				alert("选择的日期不能大于当前日期");
				return false;
			}
			break;
		}

	}
	return true;
}


function checkDate(dateValue){
	var date = new Date();
	var year = date.getFullYear();
	var month = date.getMonth()+1;
	var day = date.getDate();
  var sYear = year+"";
  var sMonth =month+"";
  var sDay = day+"";
  var sDate ="";
  if(sMonth.length <2)
    sMonth = "0"+sMonth;
  if(sDay.length <2)
    sDay = "0"+sDay;
  sDate = sYear+"-"+sMonth+"-"+sDay;
  if(dateValue >sDate){
				alert("清算日期不能大于当前日期");
				return false;
			}
	return true;

	
}


function addCaptionInfo(formName,controlNames,caption){
	var controlsText = getControlsText(formName,controlNames);
	var result = caption;
	if(controlsText !="")
	  result += "(" + controlsText +")";
	return result;

}


function getControlsText(formName,controlNames){
	if(controlNames == "")
		return "";
	var controlNamesArray =[];
	var idx = controlNames.indexOf("#");
	if(idx == -1)
		controlNamesArray[0] = controlNames;
	else
		controlNamesArray =controlNames.split("#");
	var controlName = "";
	var result = "";
	var controlText = "";
	for(i=0;i<controlNamesArray.length;i++){
		controlName = controlNamesArray[i];
		controlText = getControlText(formName,controlName);

		result +=controlText+";";
	}
	if(result !="")
		result = result.substring(0,result.length-1);
	return result;
}


function getControlText(formName,controlName){
	var frm = document.forms[formName];
	if(frm == null)
		return "";
	if(controlName =="")
		return "";
	var ob = frm.all[controlName];
	if(ob == null)
		return "";
	if(ob.tagName =="SELECT"){
		var sltIndex = ob.selectedIndex;
		return ob.options[sltIndex].text;
	}

	if(ob.tagName == "INPUT")
		return ob.value;
}

function yearReportAction(formName,controlNames,tableName){
	if(formName =="")
		return false;
//	if(controlNames =="")
//		return false;
	if(tableName =="")
		return false;
	if(!checkTime(formName,'1'))
		return false;
	var yearOb = document.forms[formName].all['year'];
	if(yearOb == null)
		return false;
	var year =yearOb.value+"0000";
	var rptCode = getReportCode(formName,controlNames);
	if(rptCode ==""){
		hideReport(tableName);
		return false;
	}
	var fleName = year+"."+rptCode;
	setReportNamePrefix(formName,fleName);
	return true;
	/*
	var fleName = year+"-"+rptCode+".pdf";
	var caption = yearOb.value;
	caption = addCaptionInfo(formName,controlNames,caption);
	displayReport(tableName,fleName,caption);
	*/

}


function hideReport(tableName){

	var tbl = document.getElementById(tableName);
	if(tbl == null)
		return;

	tbl.style.display ="none";

}

function displayReport(tableName,fleName,caption){
	if(fleName =="")
		return;

	var tbl = document.getElementById(tableName);
	if(tbl == null)
		return;
	var linkCell = tbl.rows[1].cells[1];
	var linkOb = linkCell.children[0].children[0];
	if(linkOb.tagName !="A")
		return;
	linkOb.innerHTML = caption;
	linkOb.href = "./pdf/"+fleName;
	tbl.style.display ="";

}


function setTableLinkText(tableName,caption){
	var tbl = document.getElementById(tableName);
	if(tbl == null)
		return;
	var rows = tbl.rows;
	var linkCell = null;
	var linkOb = null;
	for(i=1;i<rows.length;i++){
		linkCell = tbl.rows[i].cells[1];
		linkOb = linkCell.children[0].children[0];
		linkOb.innerHTML = linkOb.innerHTML+caption;
	}

}


function getReportCode(formName,controlNames){
	var controlNamesArray =[];
	var key ="";
	if(controlNames =="")
	{
		key ="000000";
		rptCode =getReportcodeByKey(formName,key);
		if(rptCode =="")
			alert("键值为"+key+"没有对应的报表代码，请确定输入是否正确或增加键值对应代码");
		return rptCode;
	}

	var index = controlNames.indexOf("#");
	if(index == -1)
		controlNamesArray[0] = controlNames;
	else
		controlNamesArray = controlNames.split("#");
	var ob = null;

	var rptCode = "";
	for(i=0;i<controlNamesArray.length;i++){
		ob = document.forms[formName].all[controlNamesArray[i]];
		if(ob == null)
			continue;
		key +=ob.value;
	}
	rptCode =getReportcodeByKey(formName,key);
	if(rptCode =="")
		alert("键值为"+key+"没有对应的报表代码，请确定输入是否正确或增加键值对应代码");
	return rptCode;

}


function getReportcodeByKey(formName,key){
	var frm = document.forms[formName];
	if(frm == null)
		return;
	var rptCodeMappingOb = frm.all['reportCodeMapping'];
	if(rptCodeMappingOb == null || rptCodeMappingOb.value =='')
		return "";
	rptCodeMapping =rptCodeMappingOb.value;
	rptCodeMapping = rptCodeMapping.substring(0,rptCodeMapping.length-1);
	//	alert("rptCodeMapping="+rptCodeMapping);
//	alert("key="+key);
	var index = rptCodeMapping.indexOf(";");
	var rptCodeMappingArray = [];
	if(index == -1)
		rptCodeMappingArray[0] = rptCodeMapping;
	else
		rptCodeMappingArray = rptCodeMapping.split(";");
	var code ="";
	var codeText = "";
	var record ="";
	for(i=0;i<rptCodeMappingArray.length;i++){
		record =rptCodeMappingArray[i];
		index = record.indexOf("#");
		if(index == -1)
			continue;
		code = record.substring(0,index);
		codeText = record.substring(index+1);
		if(code == key)
			return codeText;
	}
	return "";

}


//formName 提交窗体名称；listName 结果列表DIV ID；detailName 明细DIV ID
function btnClick(formName,listName,detailName){
	var btId=event.srcElement.id;
	//alert("btId="+btId);
	//alert("formName="+formName);


	switch (btId){
	case "btClone":{
		//	alert("call btClone");
			setCommand(formName,"clone");
			setOperation(formName,"op_clone");
//			setDetailFormEnabled(detailName,true);
			break;
		}
	case "btAdd":{
			setCommand(formName,"add");
			setOperation(formName,"op_add");
			setDetailFormEnabled(detailName,true);
			setAlwaysDisableForObj(formName,true);
	//		addAction();
	//		btnAddAction();
			break;
		}
	case "btDelete":{
		  var rst = confirm("确定删除所选记录？");
			if(!rst)
				return;
			setCommand(formName,"delete");
	//		alert("case "btDelete"");
			setOperation(formName,"op_del");
			setSubmitForm(formName);
			getAllSelectedIDs(listName,formName);
			deleteAction(formName);
			break;
		}
	case "btModify":{
			setCommand(formName,"modify");
			setOperation(formName,"op_modify");
			setDetailFormEnabled(detailName,true);
			setAlwaysDisableForObj(formName,true);
			disablePrimaryKeys(formName);
	//		modifyAction();
			break;
		}
	case "btModify1":{
//		  alert("call btModify1");
			setCommand(formName,"modify");
			setOperation(formName,"op_modify1");
			setDetailFormEnabled(detailName,true);
		//	setAlwaysDisableForObj(formName,true);
	//		modifyAction();
			break;
		}
	case "btSave":{
		//	setOperation("op_save");
		//	document.getElementById("groupOp").submit();
	//		btnDataSave();
	//    alert("btSave");
	//改在校验后做
	    //if(document.forms[formName].all["command"].value =="modify")
	    	//enablePrimaryKeys(formName);
			setSubmitForm(formName);
	//		alert("setSubmitForm");
	//改在校验后做
		//	setAlwaysDisableForObj(formName,false);
	//		alert("setAlwaysDisableForObj");
	        
			saveAction(formName);
			break;
		}
	case "btSave1":{
		//	setOperation("op_save");
		//	document.getElementById("groupOp").submit();
	//		btnDataSave();
	//    alert("btSave");
	    if(document.forms[formName].all["command"].value =="modify")
	    	enablePrimaryKeys(formName);
			setSubmitForm(formName);
	//		alert("setSubmitForm");
			setAlwaysDisableForObj(formName,false);
	//		alert("setAlwaysDisableForObj");
			saveAction1(formName);
			break;
		}
	case "btQuery":{
		//	setOperation("op_query");
		//	document.getElementById("groupOp").submit();
	//		btnDataSave();
	    
			setCommand(formName,"query");
			setSubmitForm(formName);
			queryAction(formName);
			break;
		}
	case "btReportQuery":{
			setOperation(formName,"op_reportQuery");
		//	document.getElementById("groupOp").submit();
	//		btnDataSave();
			setCommand(formName,"reportQuery");
			setSubmitForm(formName);
//			reportQueryAction(formName);
			break;
		}
	case "btRefundOk":{
			setOperation(formName,"op_refundOk");
			setCommand(formName,"refundOk");
			formSubmitAction(formName);
			break;
		}
	case "btRefundNo":{
			setOperation(formName,"op_refundNo");
			setCommand(formName,"refundNo");
			formSubmitAction(formName);
			break;
		}
	case "btRefundMd":{
			setOperation(formName,"op_refundMd");
			setCommand(formName,"refundMd");
			formSubmitAction(formName);
			break;
		}
	case "btRefundCk":{
			setOperation(formName,"op_refundCk");
			setCommand(formName,"refundCk");
			formSubmitAction(formName);
			break;
		}

	case "btStatistic":{
                        setOperation(formName,"op_statistic");
			setCommand(formName,"statistic");
			setSubmitForm(formName);
			statisticAction(formName);
			break;
		}
	case "btDownload":{
		//	setOperation("op_query");
		//	document.getElementById("groupOp").submit();
	//		btnDataSave();
                        setOperation(formName,"op_download");
                        getAllSelectedIDs(listName,formName);
			setCommand(formName,"dowload");
			setSubmitForm(formName);
			downloadAction(formName);
			break;
		}
	case "btDownload1":{

                        setOperation(formName,"op_download1");
                        getAllSelectedIDs(listName,formName);
			setCommand(formName,"download1");
			setSubmitForm(formName);
			downloadAction(formName);
			break;
		}
	case "btSubmit":{
		//	setOperation("op_query");
		//	document.getElementById("groupOp").submit();
	//		btnDataSave();
			setCommand(formName,"submit");
			setSubmitForm(formName);
			submitAction(formName);
			break;
		}
        case "btGenerate":{
                        setCommand(formName,"generate");
                        setSubmitForm(formName);
                        generateAction(formName,"btGenerate");
                        break;
                }
        case "btGenerateOD":{
                        setCommand(formName,"generateOD");
                        setSubmitForm(formName);
                        generateAction(formName,"btGenerateOD");
                        break;
                }
	case "btImport":{

			setCommand(formName,"import");
			setSubmitForm(formName);
			importAction(formName);
			break;
		}
	case "btImport1":{

			setCommand(formName,"import1");
			setSubmitForm(formName);
			break;
		}
	case "btCheck1":{
                        setOperation(formName,"op_check1");
			setCommand(formName,"check1");
			setSubmitForm(formName);
			break;
		}

	case "btCancle":{
//			alert("call btCancle");
			setOperation(formName,"op_browse");
//			alert("call setOperation");
			cancleAction(formName,detailName,listName);
			setAlwaysEnableForObj(formName,false);
	//		alert("call cancleAction");

			break;
		}
	case "btPrint":{
			setOperation(formName,"op_browse");
			printData(listName);
			break;
		}
	case "btPrint1":{
			setOperation(formName,"op_browse");
			printData1(listName);
			break;
		}
	//票卡入库
        case "btPrint2":{
			setOperation(formName,"op_browse");
			printData2(listName);
			break;
		}
	//票卡出库
	case "btPrint3":{
			setOperation(formName,"op_browse");
			printData3(listName);
			break;
		}
	case "btExport":{
//			alert("btExport");
			setOperation(formName,"op_browse");
			exportExcel(listName);
			break;
		}
        case "btExport1":{
//			alert("btExport");
			setOperation(formName,"op_browse");
			exportExcel1(listName);
			break;
		}
	case "btDistribute":{
			setCommand(formName,"distribute");
	//		alert("case "btDelete"");
			setOperation(formName,"op_distribute");
			setSubmitForm(formName);
                        getAllSelectedIDs(listName,formName);

			deleteAction(formName);
			break;
		}
		
        case "btAudit":{
		  var rst = confirm("确定对所选记录审核？");
			if(!rst)
				return;
			clearMessageForAudit();
			setCommand(formName,"audit");
	//		alert("case "btDelete"");
			setOperation(formName,"op_audit");
			setSubmitForm(formName);
			getAllSelectedIDs(listName,formName);
			auditAction(formName);
			break;
		}
		
        case "btSubmitSam":{
            
			setCommand(formName,"submitSam");
			setSubmitForm(formName);
			submitSamAction(formName);
			break;
		}
		

	default:{
			setOperation("");
		}
	}
}


function clearMessageForAudit(){
	var ob = document.getElementById("promptMessageId");
	//alert(ob.innerHTML);
	
	ob.innerHTML='提示信息：<strong><font color="red">正在审核...请勿进行其他操作</font></strong>';
}

function clearMessageForAddAndModify(formName){
	var ob = document.getElementById("promptMessageId");
	//alert(ob.innerHTML);
	var command=document.forms[formName].all["command"];
	if(command.value =='add')
	   ob.innerHTML='提示信息：<strong><font color="red">正在增加记录...请勿进行其他操作</font></strong>';
	if(command.value =='modify')
	   ob.innerHTML='提示信息：<strong><font color="red">正在修改记录...请勿进行其他操作</font></strong>';
}


//formName 提交窗体名称；listName 结果列表DIV ID；detailName 明细DIV ID;listColums 列表对应列
function btnClick(formName,listName,detailName,listColums){
	var btId=event.srcElement.id;
//	alert("btId="+btId);
//	alert("formName="+formName);


	switch (btId){
	case "btClone":{
		//	alert("call btClone");
			setCommand(formName,"clone");
			setOperation(formName,"op_clone");
//			setDetailFormEnabled(detailName,true);
			break;
		}
	case "btAdd":{
			setCommand(formName,"add");
			setOperation(formName,"op_add");
			setDetailFormEnabled(detailName,true);
			setAlwaysDisableForObj(formName,true);
	//		addAction();
	//		btnAddAction();
			break;
		}
	case "btDelete":{
		  var rst = confirm("确定删除所选记录？");
			if(!rst)
				return;
			setCommand(formName,"delete");
	//		alert("case "btDelete"");
			setOperation(formName,"op_del");
			setSubmitForm(formName);
			getAllSelectedIDs(listName,formName);
			deleteAction(formName);
			break;
		}
	case "btModify":{
			setCommand(formName,"modify");
			setOperation(formName,"op_modify");
			setDetailFormEnabled(detailName,true);
			setAlwaysDisableForObj(formName,true);
			disablePrimaryKeys(formName);
	//		modifyAction();
			break;
		}
	case "btModify1":{
//		  alert("call btModify1");
			setCommand(formName,"modify");
			setOperation(formName,"op_modify1");
			setDetailFormEnabled(detailName,true);
		//	setAlwaysDisableForObj(formName,true);
	//		modifyAction();
			break;
		}
	case "btSave":{
		//	setOperation("op_save");
		//	document.getElementById("groupOp").submit();
	//		btnDataSave();
	//    alert("btSave");
	    //if(document.forms[formName].all["command"].value =="modify")
	    //改为在saveAction中校验通过后20110528 by hejj
	    //	enablePrimaryKeys(formName);
			setSubmitForm(formName);
	//		alert("setSubmitForm");
	//改为在saveAction中校验通过后20110528 by hejj
		//	setAlwaysDisableForObj(formName,false);
	//		alert("setAlwaysDisableForObj");
	
	        
	        
			saveAction(formName);
			break;
		}
	case "btSave1":{
		//	setOperation("op_save");
		//	document.getElementById("groupOp").submit();
	//		btnDataSave();
	//    alert("btSave");
	    if(document.forms[formName].all["command"].value =="modify")
	    	enablePrimaryKeys(formName);
			setSubmitForm(formName);
	//		alert("setSubmitForm");
			setAlwaysDisableForObj(formName,false);
	//		alert("setAlwaysDisableForObj");
			saveAction1(formName);
			break;
		}
	case "btQuery":{
		//	setOperation("op_query");
		//	document.getElementById("groupOp").submit();
                //	btnDataSave();
                        if(!cDate){
                            alert("结束日期必须不小开始日期！")
                            break;
                        }
			setCommand(formName,"query");
			setSubmitForm(formName);
			queryAction(formName);
			break;
		}
	case "btReportQuery":{
			setOperation(formName,"op_reportQuery");
		//	document.getElementById("groupOp").submit();
                //	btnDataSave();
			setCommand(formName,"reportQuery");
			setSubmitForm(formName);
                //	reportQueryAction(formName);
			break;
		}
	case "btRefundOk":{
			setOperation(formName,"op_refundOk");
			setCommand(formName,"refundOk");
			formSubmitAction(formName);
			break;
		}
	case "btRefundNo":{
			setOperation(formName,"op_refundNo");
			setCommand(formName,"refundNo");
			formSubmitAction(formName);
			break;
		}
	case "btRefundMd":{
			setOperation(formName,"op_refundMd");
			setCommand(formName,"refundMd");
			formSubmitAction(formName);
			break;
		}
	case "btRefundCk":{
			setOperation(formName,"op_refundCk");
			setCommand(formName,"refundCk");
			formSubmitAction(formName);
			break;
		}

	case "btStatistic":{
                        setOperation(formName,"op_statistic");
			setCommand(formName,"statistic");
			setSubmitForm(formName);
			statisticAction(formName);
			break;
		}
	case "btDownload":{
		//	setOperation("op_query");
		//	document.getElementById("groupOp").submit();
	//		btnDataSave();
                        setOperation(formName,"op_download");
                        getAllSelectedIDs(listName,formName);
			setCommand(formName,"dowload");
			setSubmitForm(formName);
			downloadAction(formName);
			break;
		}
	case "btDownload1":{

	    setOperation(formName,"op_download1");
	    getAllSelectedIDs(listName,formName);
			setCommand(formName,"download1");
			setSubmitForm(formName);
			downloadAction(formName);
			break;
		}
	case "btSubmit":{
		//	setOperation("op_query");
		//	document.getElementById("groupOp").submit();
	//		btnDataSave();
			setCommand(formName,"submit");
			setSubmitForm(formName);
			submitAction(formName);
			break;
		}
        case "btGenerate":{
                        setCommand(formName,"generate");
                        setSubmitForm(formName);
                        generateAction(formName,"btGenerate");
                        break;
                }
        case "btGenerateOD":{
                        setCommand(formName,"generateOD");
                        setSubmitForm(formName);
                        generateAction(formName,"btGenerateOD");
                        break;
                }
	case "btImport":{

			setCommand(formName,"import");
			setSubmitForm(formName);
			importAction(formName);
			break;
		}
	case "btImport1":{

			setCommand(formName,"import1");
			setSubmitForm(formName);
			break;
		}
	case "btCheck1":{
      setOperation(formName,"op_check1");
			setCommand(formName,"check1");
			setSubmitForm(formName);
			break;
		}

	case "btCancle":{
//			alert("call btCancle");
			setOperation(formName,"op_browse");
//			alert("call setOperation");
			cancleAction(formName,detailName,listName);
			setAlwaysEnableForObj(formName,false);
	//		alert("call cancleAction");

			break;
		}
	case "btPrint":{
//			setOperation(formName,"op_browse");
			printData(listName);
			break;
		}
	case "btPrint1":{
//			setOperation(formName,"op_browse");
			printData1(listName);
			break;
		}
	//票卡入库
  case "btPrint2":{
			setOperation(formName,"op_browse");
			printData2(listName);
			break;
		}
	//票卡出库
	case "btPrint3":{
			setOperation(formName,"op_browse");
			printData3(listName);
			break;
		}
	case "btExport":{
//			alert("btExport");
//			setOperation(formName,"op_browse");
			exportExcel(listName);
			break;
		}
		case "btExport1":{
//			alert("btExport");
//			setOperation(formName,"op_browse");
			exportExcel1(listName);
			break;
		}
	case "btDistribute":{
			setCommand(formName,"distribute");
	//		alert("case "btDelete"");
			setOperation(formName,"op_distribute");
			setSubmitForm(formName);
  		getAllSelectedIDs(listName,formName);

			deleteAction(formName);
			break;
		}
		
	case "btAudit":{
		  var rst = confirm("确定对所选记录审核？");
			if(!rst)
				return;
		    clearMessageForAudit();
			setCommand(formName,"audit");
	//		alert("case "btDelete"");
			setOperation(formName,"op_audit");
			setSubmitForm(formName);
			getAllSelectedIDs(listName,formName);
			auditAction(formName);
			break;
		}
		case "btExportBill":{//单据导出

		  setCommand(formName,"export");
		  setOperation(formName,"op_export");
		  break;
		  
		}
		case "btBack":{//前一页


		  setCommand(formName,"back");
		  setOperation(formName,"op_back");
		  pageControlAction(formName);
		  break;
		  
		}
		
		case "btNext":{//下一页


		  setCommand(formName,"next");
		  setOperation(formName,"op_next");
		  pageControlAction(formName);
		  break;
		  
		}
		case "btBackEnd":{//始页

		  setCommand(formName,"backEnd");
		  setOperation(formName,"op_backEnd");
		  pageControlAction(formName);
		  break;
		  
		}
		case "btNextEnd":{//止页

		  setCommand(formName,"nextEnd");
		  setOperation(formName,"op_nextEnd");
		  pageControlAction(formName);
		  break;
		  
		}
		case "btDetailSubmit":{

			setCommand(formName,"detailSubmit");
			setOperation(formName,"op_detailSubmit");
			detailSubmitAction(formName);
			break;
		}
		case "btSubmitSam":{
            
			setCommand(formName,"submitSam");
			setSubmitForm(formName);
			submitSamAction(formName);
			break;
		}

	default:{
			setOperation("");
		}
	}
}


function detailSubmitAction(formName){
  frm = document.forms[formName];
  frm.submit();
}
/*
导出单据
formName:窗体名称
template：模板名称

paramNames：参数名称

paramValueNames：参数值的控件名称
iWidth:宽

iHeight：高
*/
function exportBillDetail(formName,template,paramNames,paramValueNames,iWidth,iHeight){
  
  var url='ticketStorageExportBillManage.do';                                 //转向网页的地址;
  var name;                           //网页名称，可为空;
  var iWidth;                          //弹出窗口的宽度;
  var iHeight;                        //弹出窗口的高度;
  var iTop = (window.screen.availHeight-30-iHeight)/2;       //获得窗口的垂直位置;
  var iLeft = (window.screen.availWidth-10-iWidth)/2;           //获得窗口的水平位置;
  var param ='';
  var value;
  var strParamNames='';
  for(i=0;i<paramNames.length;i++){
    value = document.forms[formName].all[paramValueNames[i]].value;
    if(value ==null || value.length ==0)
    {
    	alert('参数没有赋值');
    	return;
    }
  	param +=paramNames[i]+'='+value+'&';
  	strParamNames +=paramNames[i]+'#';
  	}
  
  url +='?'+'template='+template+'&'+param+'&paramNames='+strParamNames
 // alert(url);
  window.open(url,'','height='+iHeight+',,innerHeight='+iHeight+',width='+iWidth+',innerWidth='+iWidth+',top='+iTop+',left='+iLeft+',toolbar=no,menubar=no,scrollbars=auto,resizable=yes,location=no,status=no');
  
 }


function disabeQueryControl(formName,cName){
	var ob =document.forms[formName].all[cName];
	ob.disabled = true;
	//alert("disable"+cName);
}

function formSubmitAction(formName){
	if(formName =="")
		return;
	frm = document.forms[formName];
	if(frm == null)
	 return;
	frm.submit();
}

function statisticAction(formName){
	if(formName =="")
		return;
	frm = document.forms[formName];
	if(frm == null)
		return;
  if(!Validator.Validate(frm,1))
   return;
  if(!checkForStatistic(formName))
  	return;
	setSelectedRadio(formName);
	
	disabeQueryControl(formName,"statistic");
	frm.submit();

}

function checkForStatistic(formName){
	frm = document.forms[formName];
	var dateOb = frm.all['date'];
	var weekOb = frm.all['week'];
	var monthWeekOb = frm.all['monthWeek'];
	var selIndex = getSelectedRadioIndex('selectedDate');
	var relIndex = -1;
	var relIndex1 =-1;
	var relIndex2 =-1;

	var reg = /^\s*$/;
	var reg1 = /^\d\d\d\d-\d\d-\d\d$/
	if(dateOb !=null)
	  relIndex = dateOb.getAttribute("relRadioIndex");
	if(weekOb !=null)
	  relIndex1 = weekOb.getAttribute("relRadioIndex");
	if(monthWeekOb !=null)
	  relIndex2 = monthWeekOb.getAttribute("relRadioIndex");

//	alert("selIndex="+selIndex+" relIndex="+relIndex+" relIndex1="+relIndex1+" relIndex2="+relIndex2);
	if(selIndex !=-1){
		if(dateOb !=null &&selIndex ==relIndex ){

	//		if(dateOb.value =="")

	      if(reg.test(dateOb.value))
				{
					alert("日期不能为空");
					return false;
				}
				if(!reg1.test(dateOb.value))
				{
					alert("日期格式为yyyy-mm-dd");
					return false;
				}
		}
		if(monthWeekOb !=null &&selIndex ==relIndex2 ){
			if(reg.test(monthWeekOb.value))
				{
					alert("月不能为空");
					return false;
				}
		}
		if(weekOb !=null &&selIndex ==relIndex1 ){
			if(reg.test(weekOb.value))
				{
					alert("周不能为空");
					return false;
				}
		}


	}
	return true;

}


function getSelectedRadioIndex(radioName){
	var radios = document.getElementsByName(radioName);
	if(radios==null ||radios.length ==0)
		return -1;
	var radio =null;
	for(i=0;i<radios.length;i++){
		radio = radios[i];
		if(radio.type !="radio")
			continue;
		if(radio.checked)
			return i;
	}
	return -1;
}

function setSelectedRadio(formName){
//	alert("ob.setSelectedRadio begin");
	if(formName =="")
		return;
	var frm = document.forms[formName];
	if(frm == null)
		return;
	var obName = "";
	var sltRadios = frm.all["selectedRadios"];
	if(sltRadios == null)
		return;
	for(i=0;i<frm.all.length;i++){
		ob = frm.all[i];
		if(ob.tagName !="INPUT")
			continue;
		if(ob.type !="radio")
			continue;
		if(!ob.checked)
			continue;
//		alert("ob.name="+ob.name);
		obName = ob.name;
		obGroup = document.getElementsByName(obName);

		for(j=0;j<obGroup.length;j++){
			if(!obGroup[j].checked)
				continue;
			sltRadios.value +=obGroup[j].name+"#"+j+",";
//			alert("sltRadios.value="+sltRadios.value);

		}

	}
	if(sltRadios.value !="")
	 	sltRadios.value =sltRadios.value.substring(0,sltRadios.value.length-1);
}

function isSelectGroup(groupDivID){
	var groupOb = document.getElementById(groupDivID);
		
	if(groupOb ==null)
	  return false;
	var tableOb =groupOb.children[0];
	var checkOb = null;
	for(i=0;i< tableOb.rows.length;i++){
		if(tableOb.rows[i].id =="ignore")
			continue;
		checkOb = tableOb.rows[i].cells[0].children[0];
//		alert(checkOb.checked);
		if(checkOb.checked == true){
			return true;
		}
	}
	return false;
	
}

//formName 提交窗体名称；listName 结果列表DIV ID；detailName 明细DIV ID;
//addListName 附加列表DIV ID;addColNames 附加列表中复选筐的名称列表，以","分隔
function btnClickEx(formName,listName,detailName,addListName,addChkNames){
	var btId=event.srcElement.id;
//	alert("btId="+btId);
//	alert("formName="+formName);


	switch (btId){
	case "btClone":{
			setCommand("clone");
			setOperation("op_clone");
			break;
		}
	case "btAdd":{
			setCommand(formName,"add");
			setOperation(formName,"op_add");
			setDetailFormEnabled(detailName,true);
			setAlwaysDisableForObj(formName,true);
	//		addAction();
	//		btnAddAction();
			break;
		}
	case "btDelete":{
			setCommand(formName,"delete");
	//		alert("case "btDelete"");
			setOperation(formName,"op_del");
			setSubmitForm(formName);
			getAllSelectedIDs(listName,formName);
			deleteAction(formName);
			break;
		}
	case "btModify":{
			setCommand(formName,"modify");
			setOperation(formName,"op_modify");
			setDetailFormEnabled(detailName,true);
			setAlwaysDisableForObj(formName,true);
	//		modifyAction();
			break;
		}
	case "btSave":{
		//	setOperation("op_save");
		//	document.getElementById("groupOp").submit();
	//		btnDataSave();
			setSubmitForm(formName);
			setAlwaysDisableForObj(formName,false);
			saveAction(formName);
			break;
		}
	case "btCancle":{
//			alert("call btCancle");
			setOperation(formName,"op_browse");
//			alert("call setOperation");
			cancleAction(formName,detailName,listName);
	//		alert("call cancleAction");
			break;
		}
	case "btPrint":{
			setOperation("op_browse");
			printData(listName);
			break;
		}
	case "btExport":{
			setOperation("op_browse");
//			alert("export");
			exportExcell(listName);
	//		exportAction();
			break;
		}
	case "btDistribute":{
		  if(!isSelectGroup(listName)){
		  	alert("请选择需要分配权限的组");
		  	return;
		  }
			setCommand(formName,"distribute");
	//		alert("case "btDelete"");
			setOperation(formName,"op_distribute");
			setSubmitForm(formName);
  		getAllSelectedIDs(listName,formName);
  		getAllAddSelectedIDs(addListName,addChkNames);
			distrAction(formName);
			break;
		}
	default:{
			setOperation("");
		}
	}
}

function downloadAction(formName){
	var frm = document.forms[formName];
	if(frm != null)
        frm.submit();
}


function submitAction(formName){
	var currentVersionStartDateOb = document.getElementById('curent_version_start_date_value');
	currentVersionStartDate ="";
	if(currentVersionStartDateOb !=null)
	  currentVersionStartDate = currentVersionStartDateOb.value;
	var rt=showModalDialog("./futureVersion.jsp",currentVersionStartDate,"center:true;status:no;dialogWidth:250px;dialogHeight:150px;help:no");
	if(rt == null)
		return;
//	alert("rt="+rt);
  if(rt == 'undefined'){
  	return;
  }
    disabeQueryControl(formName,"submit1");
	var art = rt.split(";");
	document.forms[formName].all['ver_date_begin'].value=art[0];
	document.forms[formName].all['ver_date_end'].value=art[1];
	document.forms[formName].all['ver_generate'].value=art[2];
	document.forms[formName].submit();
}


function importAction(formName){
	window.open("./fileUpload.jsp","","width=300,height=150,left=360,top=310");
	//alert("rt="+rt);
  //if(rt == 'undefined'){
  //	return;
 // }
 // frm = document.forms[formName];
//  if(frm !=null){
 // 	frm.enctype ="multipart/form-data";
 //   frm.submit();
 // }


//	document.forms[formName].submit();
}


function distrAction(formName){
//	var addObj = document.getElementById("allAddSelectedIDs");
//	var allObj = document.getElementById("allSelectedIDs");
//	alert("allObj.value="+allObj.value);
//	if(addObj.value == ''){
//		alert("请选择分配项");
//		return;++
//	}

	document.forms[formName].submit();
}

 //formName 提交窗体ID;detailForm 提交窗体的DIV ID;listForm 明细的DIV ID
function cancleAction(formName,detailForm,listForm){
	setDetailFormEnabled(detailForm,false);
	resetListForm(listForm);

	var oldLine=getCurrentLine(formName);
	if (oldLine != ""){
		var oldObject=document.getElementById(oldLine);
		oldObject.style.background="#FFFFFF";
	}
	setCurrentLine(formName,"");
}

//设置明细框(detail)为可编辑
//--输入参数：formName  明细框(DIV)的ID
//--输入参数：flag      标志，true＝enable false＝disable
/*
function setDetailFormEnabled(formName,flag){
	if(formName == '')
		return;
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
*/

function setDetailFormEnabled(formName,flag){
	var detail=document.getElementById(formName);
	if (detail != null){
		for(i=0;i<detail.all.length;i++){
			var ob = detail.all[i];
			if(flag)
			  ob.disabled = "";
			else
				ob.disabled ="true";
		}
	}
}


function setButtonsEnabled(formName,flag){
	var detail=document.getElementById(formName);
	if (detail != null){
		for(i=0;i<detail.all.length;i++){
			var ob = detail.all[i];
			//alert('button type='+ob.type);
			if(ob.type=='button'){
				if(flag)
			  		ob.disabled = "";
				else
					ob.disabled ="true";
		   }
		}
	}
}

function setDetailFormEnabledByName(formName,flag){
	var detail=document.forms[formName];
	if (detail != null){
		for(i=0;i<detail.all.length;i++){
			var ob = detail.all[i];
			if(flag)
			  ob.disabled = "";
			else
				ob.disabled ="true";
		}
	}
}

//取消列表框构选

//--输入参数：listForme  列表框(DIV)的ID
function resetListForm(listForm){
	var listOb = document.getElementById(listForm);
	if(listOb == null)
		return;
	var tbl = listOb.children[0];
//	alert("tbl.tabName="+tbl.tagName);
	if(tbl.tagName !="TABLE")
		return ;
	var rows = tbl.rows;
	var rowOb = null;
	var cellOb = null;
	var chkOb = null;
	for(i=0;i<rows.length;i++){
		rowOb = rows[i];
		cellOb = rowOb.cells[0];
		if(cellOb ==null)
			continue;
		chkOb = cellOb.children[0];
		if(chkOb == null)
			continue;
		if(chkOb.type == "checkbox")
		 chkOb.checked = "";

	}

//	for (var i=0;i<document.all.length ;i++ ){
//		var e=document.all[i];
	//	if (e.type=="checkbox")
//			if (e.name.indexOf("rectNo")>=0) e.checked="";
	//		e.checked="";
	//}
}

//
//listDivName 列表的DIV 标志；colNum列表的控制显示列；ob 单击的行对象
function showListItems(listDivName,colNum,ob){
//	alert("showListItems");
	var listDiv = document.getElementById(listDivName);
	if(listDiv.tagName != 'DIV')
		return false;
	var key = '';
	while(ob.tagName !='TR'){
		ob = ob.parentElement;
	}
	key =ob.id;
	var idx = -1;
	var cellText = "";
	for(i=0;i<listDiv.all.length;i++){
		if(listDiv.all[i].tagName !='TR')
			continue;
		if(listDiv.all[i].id == 'ignore')
			continue;
		var dataRow = listDiv.all[i];
//		alert("dataRow.cells[colNum].innerText="+dataRow.cells[colNum].innerText);
//		alert("key="+key);
    cellText = dataRow.cells[colNum].innerText;
    idx =cellText.indexOf(key);
		if(idx ==-1)
			dataRow.style.display ='none';
		else
			dataRow.style.display ='';
	}
	return true;

}

//listDivName 列表的DIV 标志；colNum列表的控制显示列；ob 单击的行对象
function showListItemsByCheck(listDivName,colNum,ob){
	//alert("showListItemsByCheck");
	var listDiv = document.getElementById(listDivName);
	if(listDiv.tagName != 'DIV')
		return false;
	var key = '';
	while(ob.tagName !='TR'){
		ob = ob.parentElement;
	}
	key =ob.id;
	for(i=0;i<listDiv.all.length;i++){
		if(listDiv.all[i].tagName !='TR')
			continue;
		if(listDiv.all[i].id == 'ignore')
			continue;
		var dataRow = listDiv.all[i];
//		alert("dataRow.cells[colNum].innerText="+dataRow.cells[colNum].innerText);
//		alert("key="+key);
		if(dataRow.cells[colNum].innerText == key){
			checkCell = dataRow.cells[0];
			chk = checkCell.children[0];
			chk.checked = true;
		}

	}
	return true;

}


//listName 列表的DIV ID； 存储选择项ID的对象名称应为 allSelectedIDs；行ID必须存储记录ID
function getAllSelectedIDs(listName,formName){
//	alert('call');
	var listDiv = document.getElementById(listName);
	var tbl = listDiv.children[0];
	var selectedIDs = document.forms[formName].all['allSelectedIDs'];
	selectedIDs.value ="";

	for(i=0;i<tbl.rows.length;i++){
	//    alert(i);
		var tblRow = tbl.rows[i];
		var checkCell = tblRow.cells[0];
		var selectedCK = checkCell.children[0];
		if(tblRow.style.display == 'none' || tblRow.id=='ignore')
			continue;
      //  alert(tblRow.id);
		if(selectedCK != null){
		   //  alert(selectedCK.checked);
			if(selectedCK.checked){
				selectedIDs.value +=tblRow.id+";";
//				alert(selectedIDs.value);
		}
	}

	}
//	alert("selectedIDs.value="+selectedIDs.value);
}

function getAllAddSelectedIDs(addListName,addChkNames){
	var listDiv = document.getElementById(addListName);
	if(listDiv == null)
		return;
	if(addChkNames =='')
		return;
	var row = null;
	var addObj = document.getElementById("allAddSelectedIDs");
	var chks = null;
	if(addChkNames.indexOf(",")!=-1)
		chks = addChkNames.split(",");

	addObj.value ='';
	var tbl = listDiv.children[0];
	if(tbl.tagName !="TABLE")
	{
		alert("没有找到表格");
		return;
	}

//	for(i=0;i<listDiv.all.length;i++){
		for(i=0;i<tbl.rows.length;i++){
//		if(listDiv.all[i].tagName != 'TR')
//			continue;
//		row = listDiv.all[i];
    row = tbl.rows[i];
		if(row.id == 'ignore')
			continue;
		if(!row.cells[0].children[0].checked)
			continue;
		addObj.value +=row.id+",";

		if(addChkNames.indexOf(",")==-1)
		{
			chkObj =row.all[addChkNames];
			if(chkObj.checked)
				addObj.value +="1";
			else
				addObj.value +="0";
			addObj.value +=":";
		}
	else
		{
			for(j=0;j<chks.length;j++){
				chkObj =row.all[chks[j]];
				if(chkObj.checked)
					addObj.value +="1";
				else
					addObj.value +="0";
				if(j !=chks.length-1)
					addObj.value +=",";
			}
			addObj.value +=":";
		}

	}
//	alert("addObj.value"+addObj.value);
}


function pageControlAction(formName){
        setSubmitForm(formName);
        frm = document.forms[formName];
        frm.submit();
}


function saveAction(formName){
	frm = document.forms[formName];
//	alert("frmdisbled="+frm.disabled);
 cmd = document.forms[formName].all['command'].value;
 var checkResult = true;
 if(cmd !="clone")
	 checkResult = Validator.Validate(frm,1);
//	alert("checkResult="+checkResult);
 if(checkResult){
	//	alert("before submit");
	//modify by hejj 20110528
	    /*
	    if(document.forms[formName].all["command"].value =="modify")
	    	enablePrimaryKeys(formName);
	    setAlwaysDisableForObj(formName,false);
	    
	    disabeQueryControl(formName,'save');
	    */
	    clearMessageForAddAndModify(formName);
	    setDetailFormEnabledByName(formName,true);
	    setButtonsEnabled(formName,false);
	    disabeQueryControl(formName,"save");
	   
		frm.submit();
//		alert("after submit");
	}
}

function saveAction1(formName){
	frm = document.forms[formName];
        //	alert("frmdisbled="+frm.disabled);
         cmd = document.forms[formName].all['command'].value;
         var checkResult = true;
        // if(cmd !="clone")
        //	 checkResult = Validator.Validate(frm,1);

}


function queryAction(formName){

	frm = document.forms[formName];
	if(!Validator.Validate(frm,'1'))
		return;
	disabeQueryControl(formName,"query");
        frm.submit();
}

function reportQueryAction(formName){
	alert("formName="+formName);
	frm = document.forms[formName];
	alert("reportCodeMapping="+frm.all['reportCodeMapping'].value);
        frm.submit();
}


function deleteAction(formName){

	frm = document.forms[formName];
	frm.submit();
}


function submitSamAction(formName){

	frm = document.forms[formName];
	frm.submit();
}

function auditAction(formName){

	frm = document.forms[formName];
	disabeQueryControl(formName,"audit");
	frm.submit();
}

function initDocument(formName,detailDivName){
//	alert("formName="+formName);
//	alert("detailDivName="+detailDivName);
	setOperation(formName,'');
//	alert("setOperation");
	setDetailFormEnabled(detailDivName,false);
//	alert("setDetailFormEnabled");
}


function setDisableAlwaysObs(formName,objNames){
	frm = document.getElementById(formName);
	obj = frm.all['disableAlwaysObs'];
	obj.value = objNames;
}


function setAlwaysDisableForObj(formName,flag){
	var frm = document.getElementById(formName);
	var obj = frm.all['disableAlwaysObs'];
	var objNames = obj.value;
	var objName ="";

	if(objNames == '')
	 return ;
	index = objNames.indexOf(',');
	if(index == -1){
	  objName = objNames;
	  if(frm.all[objName] !=null)
	  	frm.all[objName].disabled = flag;
	}
	objs =objNames.split(',');
	for(i=0;i<objs.length;i++){
		if(frm.all[objs[i]] !=null)
	  	frm.all[objs[i]].disabled = flag;
	}

}

function setEnableAlwaysObs(formName,objNames){
	frm = document.getElementById(formName);
	obj = frm.all['enableAlwaysObs'];
	obj.value = objNames;
}


function setAlwaysEnableForObj(formName,flag){
	var frm = document.getElementById(formName);
	var obj = frm.all['enableAlwaysObs'];
	var objNames = obj.value;
	var objName ="";

	if(objNames == '')
	 return ;
	index = objNames.indexOf(',');
	if(index == -1){
	  objName = objNames;
	  if(frm.all[objName] !=null)
	  	frm.all[objName].disabled = flag;
	}
	objs =objNames.split(',');
	for(i=0;i<objs.length;i++){
		if(frm.all[objs[i]] !=null)
	  	frm.all[objs[i]].disabled = flag;
	}

}


function setInitEnabledButtons(formName,listName,buttonList){
	if(buttonList == null || buttonList =='')
		return;
	var listDiv = document.getElementById(listName);
	if(listDiv == null)
		return;
	var count =0;
	for(i=0;i<listDiv.all.length;i++){
		var ob = listDiv.all[i];
		if(ob.tagName !='INPUT')
			continue;
		if(ob.type == 'checkbox')
		{
			if(ob.checked)
				count++;
		}
	}	
  //alert("count="+count);
	if(count ==0)
		return;

	var index = buttonList.indexOf(",");
	var button = null;
//	alert("buttonList="+buttonList);
//	alert("index="+index);
	if(index ==-1)
		{
			button = document.forms[formName].all[buttonList];
			button.disabled = false;
		}
	else
		{
			var buttonNames = buttonList.split(",");
			for(i=0;i<buttonNames.length;i++){
				button = document.forms[formName].all[buttonNames[i]];
				button.disabled = false;
			}
		}
}


function setVersion(formName,versionObj,paramType,startDate,endDate,versionType,versionSerialNo,paramTypeValue,startDateValue,endDateValue,versionTypeValue){
	if (versionObj != ""){
		var obj=document.forms[formName].all[versionObj];
		var objType=document.forms[formName].all[paramType];
		if (obj != "undefined")
			obj.value=versionSerialNo;
		if (objType !="undefined")
			objType.value=paramTypeValue;
		document.forms[formName].all[startDate].value=startDateValue;
		document.forms[formName].all[endDate].value=endDateValue;
		document.forms[formName].all[versionType].value=versionTypeValue;
	}
	//提交FORM
	if (formName != ""){
		var frm=document.forms[formName];
		if (frm != "undefined"){
			frm.submit();
		}
	}
}


function setInvisable(formName,buttonList){
//	alert("buttonList ="+buttonList);
	var index = buttonList.indexOf(";");
//	alert("name="+document.forms[formName].all[buttonList].style);
	if(index == -1){
		document.forms[formName].all[buttonList].style.display="none";
		return;
	}
	var list = buttonList.split(";");
	for(i=0;i<list.length;i++)
		document.forms[formName].all[list[i]].style.display="none";

}


function setSelectValues(formName,selectedName,selectName,varName){
//	     alert("formName="+formName);
//	      alert("varName="+varName);
//	      alert("varValues="+document.forms[formName].all[varName].value);

        var cmdV = document.forms[formName].all[varName];
        var sltd = document.forms[formName].all[selectedName];
        var slt = document.forms[formName].all[selectName];

        var typ = sltd.value;
        if(cmdV.value ==null || cmdV.value =='')
        	return;
        if(cmdV.value.indexOf(":")==-1)
        	return;
//        alert("cmdV.value="+cmdV.value);
//        alert("slt.options.length="+slt.options.length);
      	var values = cmdV.value.split(":");
      	var count = 0;
      	var i=-1;
      	if(slt.options.length>1){
      		for(i=slt.options.length-1;i>=1;i--)
      			slt.remove(i);
      	}
      	for(i=0;i<values.length;i++){
      	  if(values[i].indexOf(",")==-1)
      	  	continue;
     			var objs = values[i].split(",");
     			if(objs[0]==typ){
     				var opt = new Option(objs[2],objs[1]);
     				slt.add(opt);
     				if(count ==0)
     					opt.selected =true;
     				count++;
     			}

      	}

      	if(count ==0&& slt.options.length >1){
      		for(j=slt.options.length-1;j>=1;j--){
      			slt.remove(j);
      		}
      	}

}

function getSubValues(formName,selectedName,mainValues,subValuesList){
	var sltd = document.forms[formName].all[selectedName];
	var sltdValue = sltd.value;
	//alert(sltdValue);
	if(sltdValue ==null || sltdValue=='')
		return null;
	var i;
	for(var i=0;i<mainValues.length;i++){
		if(sltdValue ==mainValues[i])
			return subValuesList[i];
	}
	return null;
	
} 


function getMulSubValues(formName,selectedName,selectedSubName,mainValues,subValuesList){
	var sltd = document.forms[formName].all[selectedName];
	var sltdSub = document.forms[formName].all[selectedSubName];
	var sltdValue = sltd.value+'#'+sltdSub.value;
	if(sltdValue ==null || sltdValue=='')
		return null;
	var i;
	for(var i=0;i<mainValues.length;i++){
		if(sltdValue ==mainValues[i])
			return subValuesList[i];
	}
	return null;
	
} 


function isLegalValue(opValue,subValues){
	for(var i=0;i<subValues.length;i++){
		if(opValue ==subValues[i])
			return true;
	}
	return false;
}   


function setSelectValuesByMapping(formName,selectedName,selectName,varName,mainValues,subValuesList){
//	     alert("formName="+formName);
//	      alert("varName="+varName);
//	      alert("varValues="+document.forms[formName].all[varName].value);

		var subValues =getSubValues(formName,selectedName,mainValues,subValuesList);
		if(subValues ==null)
			return;

        var cmdV = document.forms[formName].all[varName];
        var sltd = document.forms[formName].all[selectedName];
        var slt = document.forms[formName].all[selectName];
        
        
        

        var typ = sltd.value;
        if(cmdV.value ==null || cmdV.value =='')
        	return;
        if(cmdV.value.indexOf(":")==-1)
        	return;
//        alert("cmdV.value="+cmdV.value);
//        alert("slt.options.length="+slt.options.length);
      	var values = cmdV.value.split(":");
      	var count = 0;
      	var i=-1;
      	//删除原选项值

      	if(slt.options.length>1){
      		for(i=slt.options.length-1;i>=1;i--)
      			slt.remove(i);
      	}
      	for(i=0;i<values.length;i++){
      	  if(values[i].indexOf(",")==-1)
      	  	continue;
     			var objs = values[i].split(",");
     			//alert(objs[1]);
     			if(isLegalValue(objs[1],subValues)){
     				var opt = new Option(objs[2],objs[1]);
     				if(isReapeatValue(formName,selectName,opt.value))
     				  continue;
     				slt.add(opt);
     				if(count==0)
     					opt.selected = true;
     				count++;
     			}

      	}

      	if(count ==0&& slt.options.length >1){
      		for(j=slt.options.length-1;j>=1;j--){
      			slt.remove(j);
      		}
      	}

}


function isReapeatValue(formName,selectName,valueNew){
   var slt = document.forms[formName].all[selectName];
   var opt;
   for(i=0;i<slt.options.length;i++){
      opt=slt.options[i];
      if(opt.value==valueNew)
         return true;
      
   }
   return false;
  
} 
     
function setSelectValuesByMulMapping(formName,selectedName,selectedSubName,selectName,varName,mainValues,subValuesList){
//	     alert("formName="+formName);
//	      alert("varName="+varName);
//	      alert("varValues="+document.forms[formName].all[varName].value);

		var subValues =getMulSubValues(formName,selectedName,selectedSubName,mainValues,subValuesList);
		if(subValues ==null)
			return;

        var cmdV = document.forms[formName].all[varName];
        var sltd = document.forms[formName].all[selectedName];
        var slt = document.forms[formName].all[selectName];
        
        
        

        var typ = sltd.value;
        if(cmdV.value ==null || cmdV.value =='')
        	return;
        if(cmdV.value.indexOf(":")==-1)
        	return;
//        alert("cmdV.value="+cmdV.value);
//        alert("slt.options.length="+slt.options.length);
      	var values = cmdV.value.split(":");
      	var count = 0;
      	var i=-1;
      	//删除原选项值

      	if(slt.options.length>1){
      		for(i=slt.options.length-1;i>=1;i--)
      			slt.remove(i);
      	}
      	for(i=0;i<values.length;i++){
      	  if(values[i].indexOf(",")==-1)
      	  	continue;
     			var objs = values[i].split(",");
     			if(isLegalValue(objs[1],subValues)){
     				var opt = new Option(objs[2],objs[1]);
     				if(isReapeatValue(formName,selectName,opt.value))
     				  continue;
     				slt.add(opt);
     				if(count==0)
     					opt.selected = true;
     				count++;
     			}

      	}

      	if(count ==0&& slt.options.length >1){
      		for(j=slt.options.length-1;j>=1;j--){
      			slt.remove(j);
      		}
      	}

}     


function setSelectValues_1(formName,selectedName,selectName,varName){
//	     alert("formName="+formName);
//	      alert("varName="+varName);
//	      alert("varValues="+document.forms[formName].all[varName].value);

        var cmdV = document.forms[formName].all[varName];
        var sltd = document.forms[formName].all[selectedName];
        var slt = document.forms[formName].all[selectName];

        var typ = sltd.value;
        if(cmdV.value ==null || cmdV.value =='')
        	return;
        if(cmdV.value.indexOf(":")==-1)
        	return;
//        alert("cmdV.value="+cmdV.value);
//        alert("slt.options.length="+slt.options.length);
      	var values = cmdV.value.split(":");
      	var count = 0;
      	var i=-1;
      	if(slt.options.length>2){
      		for(i=slt.options.length-1;i>=2;i--)
      			slt.remove(i);
      	}
      	for(i=0;i<values.length;i++){
      	  if(values[i].indexOf(",")==-1)
      	  	continue;
     			var objs = values[i].split(",");
     			if(objs[0]==typ){
     				var opt = new Option(objs[2],objs[1]);
     				slt.add(opt);
     				count++;
     			}

      	}

      	if(count ==0&& slt.options.length >1){
      		for(j=slt.options.length-1;j>=2;j--){
      			slt.remove(j);
      		}
      	}

}


function setSelectValueByRowValue(formName,selectName,selectvalue){
	selectOb = document.forms[formName].all[selectName];
	for(i=0;i<selectOb.options.length;i++){
		if(selectOb.options[i].value ==selectvalue){
			selectOb.options[i].selected = true;
			break;
			}
	}
}


function setSelectValuesByRow(formName,selectName,varName){

        var cmdV = document.forms[formName].all[varName];
   //     var sltd = document.forms[formName].all[selectedName];
        var slt = document.forms[formName].all[selectName];
        var rowOb = event.srcElement;
        while(rowOb.tagName != "TR")
        		rowOb = rowOb.parentElement;
        var rowID = rowOb.id;
        var keys = rowID.split("#");


        var typ = keys[0];
        if(cmdV.value ==null || cmdV.value =='')
        	return;
        if(cmdV.value.indexOf(":")==-1)
        	return;

      	var values = cmdV.value.split(":");
      	var count = 0;
      	if(count ==0&& slt.options.length >1){
      		for(j=slt.options.length-1;j>=1;j--){
      			slt.remove(j);
      		}
      	}
 //     	alert(slt.options.length);
      	for(i=0;i<values.length;i++){
      	  if(values[i].indexOf(",")==-1)
      	  	continue;
     			var objs = values[i].split(",");
     			if(objs[0]==typ){
     				var opt = new Option(objs[2],objs[1]);
     				slt.add(opt);
     				count++;
     			}

      	}

      	

}


function setSelectValuesByRowProperty(formName,selectName,varName){

        var cmdV = document.forms[formName].all[varName];
   //     var sltd = document.forms[formName].all[selectedName];
        var slt = document.forms[formName].all[selectName];
        var rowOb = event.srcElement;
        while(rowOb.tagName != "TR")
        		rowOb = rowOb.parentElement;
   //     var rowID = rowOb.typeIDs;
        var rowID = rowOb.getAttribute('typeIDs');
        var keys = rowID.split("#");


        var typ = keys[0];
        if(cmdV.value ==null || cmdV.value =='')
        	return;
        if(cmdV.value.indexOf(":")==-1)
        	return;

      	var values = cmdV.value.split(":");
      	var count = 0;
      	if(count ==0&& slt.options.length >1){
      		for(j=slt.options.length-1;j>=1;j--){
      			slt.remove(j);
      		}
      	}
      	for(i=0;i<values.length;i++){
      	  if(values[i].indexOf(",")==-1)
      	  	continue;
     			var objs = values[i].split(",");
     			if(objs[0]==typ){
     				var opt = new Option(objs[2],objs[1]);
     				slt.add(opt);
     				count++;
     			}

      	}

}


function setSelectValuesByRowPropertyName(formName,selectName,varName,rowAttributeName){

        var cmdV = document.forms[formName].all[varName];
   //     var sltd = document.forms[formName].all[selectedName];
        var slt = document.forms[formName].all[selectName];
        var rowOb = event.srcElement;
        while(rowOb.tagName != "TR")
        		rowOb = rowOb.parentElement;
   //     var rowID = rowOb.typeIDs;
        var rowID = rowOb.getAttribute(rowAttributeName);
        var keys = rowID.split("#");

 //        alert(keys[0]+"#"+keys[1]);
 //        alert(cmdV.value);
        var typ = keys[0];
        if(cmdV.value ==null || cmdV.value =='')
        	return;
        if(cmdV.value.indexOf(":")==-1)
        	return;

      	var values = cmdV.value.split(":");
      	var count = 0;
        if(count ==0&& slt.options.length >1){
      		for(j=slt.options.length-1;j>=1;j--){
      			slt.remove(j);
      		}
      	}
        
        slt.innerHTML = "";//清除已有值
      	for(i=0;i<values.length;i++){
      	  if(values[i].indexOf(",")==-1)
      	  	continue;
     			var objs = values[i].split(",");
 //    			alert('objs[0]:'+objs[0]+' typ:'+typ);
     			if(objs[0]==typ){
     				var opt = new Option(objs[2],objs[1]);
                                if(objs[1] == keys[1]){
                                    opt.selected = 'selected';
                                }
     				slt.add(opt);
     				count++;
     			}

      	}

}


function setPrimaryKeys(formName,ctrNames){
 	 var prmKeys = document.forms[formName].all['primaryKeys'];
 	 prmKeys.value=ctrNames;
}


function disablePrimaryKeys(formName){
	controlPrimaryKeys(formName,true);
}


function enablePrimaryKeys(formName){

	controlPrimaryKeys(formName,false);
}


function controlPrimaryKeys(formName,Disabled){
	var prmKeys = document.forms[formName].all['primaryKeys'];
	if(prmKeys == null)
	  return;
	if(prmKeys.value =="")
		return;
	var ctrNames = prmKeys.value;
//	alert("ctrNames="+ctrNames);
	var index = ctrNames.indexOf("#");
	var ctr = null;
	if(index == -1){
		 ctr = document.forms[formName].all[ctrNames];
		 if(ctr == null){
	//	 	alert("ctr is null");
		 	return;
		}
		 ctr.disabled = Disabled;
		 return;
	}
	var ctrs = ctrNames.split("#");
	var ctrName = "";
	for(i=0;i<ctrs.length;i++){
		ctrName = ctrs[i];
		ctr = document.forms[formName].all[ctrName];
		if(ctr == null)
		 	continue;
		ctr.disabled = Disabled;
	}
}

function exportExcel(listName)
{
	var oXL = new ActiveXObject("Excel.Application");
	var oWB = oXL.Workbooks.Add();
	var oSheet = oWB.ActiveSheet;
        
	var divOb = document.getElementById(listName);
        if(divOb ==null)
            return;
        var table = divOb.children[0];
        if(table == null)
            return;

	if(document.all[listName]==null)
		return;
//	var table = document.all[listName].children[0];
	if(table == null)
		return;
	var rowCount = table.rows.length;
	var colCount = table.rows[0].cells.length;
	
        if(rowCount >100){
            if(confirm("导出的记录为"+rowCount+"条，其需要较长时间，处理速率约150条/分钟，是否继续？")==false)
                    return null;
        }

	var k=0;
	var l=0;

	oXL.Caption ="列表数据打印";
        //	oSheet.Cells.HorizontalAlignment = -4108;
        //  oSheet.Cells.WrapText = true;
        // initPageSetup(oSheet,0.5,colCount);
        var cellText = "";

	for (i=0;i<rowCount;i++)
	{
		if(table.rows[i].id == "ignore"&&i !=0)
			continue;
		for (j=0;j<colCount;j++)
		{
			if(j==0){
				if(table.rows[i].cells[j].children[0]!=null
					&&table.rows[i].cells[j].children[0].tagName =="INPUT"&&
					table.rows[i].cells[j].children[0].type !="hidden")
					continue;
			}
		  oSheet.Cells(l+1,k+1).NumberFormat ="\@";//显示文字格式

		  cellText =table.rows[i].cells[j].innerText;
		  if(table.rows[i].cells[j].children[0] !=null
		  	  &&table.rows[i].cells[j].children[0].tagName =="INPUT"&&
		  	  table.rows[i].cells[j].children[0].type !="hidden")
		  	 cellText =table.rows[i].cells[j].children[0].value;
		  	  
		 // alert('i='+i+' j='+j+' '+cellText);
		  if(cellText.length ==0)
		    {
		    	 if(table.rows[i].cells[j].children[0]!=null&&table.rows[i].cells[j].children[0].tagName =="INPUT" && table.rows[i].cells[j].children[0].type =="checkbox")
		    	 {
		    	 	 if(table.rows[i].cells[j].children[0].checked)
                                        cellText ="1";
		    	 	  else
		    	 	  	cellText ="0";
		    	}
		    }
                    
                    oSheet.Cells(l+1,k+1).value =  cellText;
                    oSheet.Cells(l+1,k+1).Borders.Weight = 2;        //设置单元格边框*()
//                    oSheet.Cells(l+1,k+1).WrapText=true;             //设置为自动换行*
                    oSheet.Cells(l+1,k+1).Font.Size = 10;            //设置为10号字*
                    oSheet.Columns.AutoFit();          //自动列宽
                    if(i == 0){
                         oSheet.Cells(l+1,k+1).Font.Bold = true;             //设置为粗体*
                    }

                    k++;
		}
		k=0;
		l++;

	}
	oXL.Visible = true;
//	oXL.UserControl = true;
	return oXL;
}

function exportExcel_test(listName)
{
	var oXL = new ActiveXObject("Excel.Application");
	var oWB = oXL.Workbooks.Add();
	var oSheet = oWB.ActiveSheet;

	if(document.all[listName]==null)
		return;
  
  
	var table = document.all[listName].children[0];
	if(table == null)
		return;
	var rowCount = table.rows.length;
	var colCount = table.rows[0].cells.length;

	var k=0;
	var l=0;

	oXL.Caption ="列表数据打印";
//	oSheet.Cells.HorizontalAlignment = -4108;
//  oSheet.Cells.WrapText = true;
// initPageSetup(oSheet,0.5,colCount);
  var cellText = "";
  

	for (i=0;i<rowCount;i++)
	{
		if(table.rows[i].id == "ignore"&&i !=0)
			continue;
		for (j=0;j<colCount;j++)
		{
			if(j==0){
				if(table.rows[i].cells[j].children[0]!=null&&table.rows[i].cells[j].children[0].tagName =="INPUT")
					continue;
			}
		  oSheet.Cells(l+1,k+1).NumberFormat ="\@";

		  cellText =table.rows[i].cells[j].innerText;
		  if(cellText.length ==0)
		    {
		    	 if(table.rows[i].cells[j].children[0]!=null&&table.rows[i].cells[j].children[0].tagName =="INPUT" && table.rows[i].cells[j].children[0].type =="checkbox")
		    	 {
		    	 	 if(table.rows[i].cells[j].children[0].checked)
		    	 	   cellText ="1";
		    	 	  else
		    	 	  	cellText ="0";
		    	}
		    }
			oSheet.Cells(l+1,k+1).value =  cellText;

			k++;
		}
		k=0;
		l++;

	}
	oXL.Visible = true;
	oXL.UserControl = true;
	return oXL;
}


function setMergeCellValue(oSheet,startRow,startCol,endRow,endCol,haValue,cellValue){
	var rangOb =null;
	rangOb = oSheet.Range(oSheet.Cells(startRow,startCol),oSheet.Cells(endRow,endCol));
  rangOb.MergeCells = true;
  rangOb.HorizontalAlignment = haValue;
  rangOb.value = cellValue;
}


function setMergeCellValueWithFont(oSheet,startRow,startCol,endRow,endCol,haValue,cellValue,isBold,cellFs){
	var rangOb =null;
	rangOb = oSheet.Range(oSheet.Cells(startRow,startCol),oSheet.Cells(endRow,endCol));
  rangOb.MergeCells = true;
  rangOb.HorizontalAlignment = haValue;
  rangOb.Font.Bold =isBold;
  rangOb.Font.Size = cellFs;
  rangOb.value = cellValue;
}


function initPageSetup(oSheet,sideMargin,colCount){
	oSheet.Cells.HorizontalAlignment = -4108;
  oSheet.Cells.WrapText = true;
  oSheet.Cells.NumberFormat ="\@";
  oSheet.PageSetup.CenterFooter="第&P页";
  oSheet.PageSetup.PrintGridLines=true;
//  oSheet.PageSetup.LeftMargin = oSheet.Application.CentimetersToPoints(sideMargin);
//  oSheet.PageSetup.RightMargin =oSheet.Application.CentimetersToPoints(sideMargin);
//  oSheet.PageSetup.TopMargin =oSheet.Application.CentimetersToPoints(1);
//  if(colCount >8)
//   oSheet.PageSetup.Orientation =2;
}


function initPageSetupForPrint(oSheet,sideMargin,colCount){
//	oSheet.Cells.HorizontalAlignment = -4108;
//  oSheet.Cells.WrapText = true;
//  oSheet.Cells.NumberFormat ="\@";
  oSheet.PageSetup.LeftMargin = oSheet.Application.CentimetersToPoints(sideMargin);
  oSheet.PageSetup.RightMargin =oSheet.Application.CentimetersToPoints(sideMargin);
  oSheet.PageSetup.TopMargin =oSheet.Application.CentimetersToPoints(1);
  if(colCount >8)
   oSheet.PageSetup.Orientation =2;
}


function printData(listName){
    //var oXL = exportExcel(listName);
    var oXL = exportExcelForPrint(listName);
    if(oXL ==null)
      return;
    oXL.DisplayAlerts = false;
    oXL.ActiveSheet.PrintPreview();
}


function printData1(listName){

    var oXL = exportExcel1(listName);
    oXL.DisplayAlerts = false;
    oXL.ActiveSheet.PrintPreview();
}


function printData2(listName){

    var oXL = exportExcel2(listName);
    oXL.DisplayAlerts = false;
    oXL.ActiveSheet.PrintPreview();
}


function printData3(listName){

    var oXL = exportExcel3(listName);
    oXL.DisplayAlerts = false;
    oXL.ActiveSheet.PrintPreview();
}


function getPriviAvailButton(formName){
	var priviCtr = document.forms[formName].all['modulePriviledges'];
	if(priviCtr == null)
		return;
	if(priviCtr.value =="")
		return;
//	alert("priviCtr.value="+priviCtr.value);
	var privis = priviCtr.value.split("#");
	var privi ="";
	var result ="";

	for(i=0;i<privis.length;i++){
		privi = privis[i];
		if(privi =="1"){
			if(i==0){
		 	result +=getPriviAvailButtonList(formName,"query")+",";
		 	continue;
		}
			if(i==1){
		 		result +=getPriviAvailButtonList(formName,"add")+",";
		 		continue;
			}
			if(i==2){
		 		result +=getPriviAvailButtonList(formName,"delete")+",";
		 		continue;
			}
			if(i==3){
		 		result +=getPriviAvailButtonList(formName,"modify")+",";
		 		continue;
		  }

		  if(i==4){
		 		result +=getPriviAvailButtonList(formName,"clone")+",";
		 		continue;
		  }
		  if(i==5){
		 		result +=getPriviAvailButtonList(formName,"submit")+",";
		 		continue;
		  }
		  if(i==6){
		 		result +=getPriviAvailButtonList(formName,"export")+",";
		 		continue;
		  }
		  if(i==7){
		 		result +=getPriviAvailButtonList(formName,"import")+",";
		 		continue;
		  }
		  if(i==8){
		 		result +=getPriviAvailButtonList(formName,"print")+",";
		 		continue;
		  }


		  if(i==9){
		 		result +=getPriviAvailButtonList(formName,"download")+",";
		 		continue;
		  }
		  if(i==10){
		 		result +=getPriviAvailButtonList(formName,"rptQuery")+",";
		 		continue;
		  }
		  if(i==11){
		 		result +=getPriviAvailButtonList(formName,"statistic")+",";
		 		continue;
		  }
		  if(i==12){
		 		result +=getPriviAvailButtonList(formName,"check")+",";
		 		continue;
		  }
		  if(i==13){
		 		result +=getPriviAvailButtonList(formName,"distribute")+",";
		 		continue;
		  }
		  if(i==14){
		 		result +=getPriviAvailButtonList(formName,"refundOk")+",";
		 		continue;
		  }
		  if(i==15){
		 		result +=getPriviAvailButtonList(formName,"refundNo")+",";
		 		continue;
		  }
		  if(i==16){
		 		result +=getPriviAvailButtonList(formName,"refundMd")+",";
		 		continue;
		  }
		  if(i==17){
		 		result +=getPriviAvailButtonList(formName,"refundCk")+",";
		 		continue;
		  }
		  if(i==18){
		 		result +=getPriviAvailButtonList(formName,"audit")+",";
		 		continue;
		  }



	}

	}
	if(result !="")
		result = result.substring(0,result.length-1);
	return result;

}


function getPriviAvailButtonList(formName,priviID){
	if(priviID =="query")
		 return document.forms[formName].all['priviQuery'].value;
	if(priviID =="add")
		 return document.forms[formName].all['priviAdd'].value;
	if(priviID =="delete")
		 return document.forms[formName].all['priviDelete'].value;
	if(priviID =="modify")
		 return document.forms[formName].all['priviModify'].value;

	if(priviID =="clone")
		 return document.forms[formName].all['priviClone'].value;
	if(priviID =="submit")
		 return document.forms[formName].all['priviSubmit'].value;
	if(priviID =="export")
		 return document.forms[formName].all['priviExport'].value;
	if(priviID =="import")
		 return document.forms[formName].all['priviImport'].value;
	if(priviID =="print")
		 return document.forms[formName].all['priviPrint'].value;


	if(priviID =="download")
		 return document.forms[formName].all['priviDownload'].value;
	if(priviID =="rptQuery")
		 return document.forms[formName].all['priviRptQuery'].value;
  if(priviID =="statistic")
		 return document.forms[formName].all['priviStatistic'].value;
	if(priviID =="check")
		 return document.forms[formName].all['priviCheck'].value;
	if(priviID =="distribute")
		 return document.forms[formName].all['priviDistribute'].value;
	if(priviID =="refundOk")
		 return document.forms[formName].all['priviRefundOk'].value;
	if(priviID =="refundNo")
		 return document.forms[formName].all['priviRefundNo'].value;
	if(priviID =="refundMd")
		 return document.forms[formName].all['priviRefundMd'].value;
	if(priviID =="refundCk")
		 return document.forms[formName].all['priviRefundCk'].value;
    if(priviID =="audit")
		 return document.forms[formName].all['priviAudit'].value;		 

}

function getEffectiveAvailButton(formName,availButton){
	if(availButton =="")
		return "";

	var privAvail = getPriviAvailButton(formName);
//	 alert("availButton="+availButton);
//	 alert("privAvail="+privAvail);
	if(privAvail =="" || privAvail == undefined){
//		alert("return");
		return "";
	}
	if(availButton.indexOf(",")==-1){
		if(isPriviAvailButton(privAvail,availButton))
			return availButton;
		return "";
	}
	var availNames  = availButton.split(",");
	var availName = "";
	var result = "";
	for(i=0;i<availNames.length;i++){
		availName =availNames[i];
		if(isPriviAvailButton(privAvail,availName))
			result +=availName+",";
	}
	if(result !="")
		result = result.substring(0,result.length-1);
//	alert("result="+result);
	return result;


}


function isPriviAvailButton(privAvail,availButton){
	if(privAvail =="" || availButton=="")
		return false;
	var ind =privAvail.indexOf(availButton);
	if(ind == -1)
		return false;
	return true;

}


function setQueryControlsDefaultValue(formName,formName1){
	if(formName =="" || formName1 =="")
		return;
	var frm = document.forms[formName];
	var frm1 = document.forms[formName1];
	if(frm == null || frm1 ==null)
		return;
	defaultValues = frm.all['controlDefaultValues'].value;
	frm1.all['queryControlDefaultValues'].value =defaultValues;
}


function setControlsDefaultValue(formName){
	if(formName =="")
		return;
	var frm = document.forms[formName];
	if(frm == null)
		return;
	defaultValues = frm.all['controlDefaultValues'].value;
//	alert("defaultValues="+defaultValues);
	if(defaultValues =="")
		return;
	var idx = defaultValues.indexOf(";");
	var defaultValuesArray = [];
	if(idx == -1)
	 defaultValuesArray[0] =defaultValues;
	else
	 defaultValuesArray = defaultValues.split(";");
	var controlName = "";
	var defValue = "";
	var compValue = "";
	for(i=0;i<defaultValuesArray.length;i++){
		compValue = defaultValuesArray[i];
		idx = compValue.indexOf("#");
		if(idx ==-1)
			continue;
		controlName = compValue.substring(0,idx);
		defValue = compValue.substring(idx+1);
		setcontrolDefaultValue(formName,controlName,defValue);
	}
}

function setcontrolDefaultValue(formName,controlName,defValue){
//	alert("controlName="+controlName+" defValue="+defValue);
	var frm = document.forms[formName];
	if(frm == null)
		return;
	if(controlName == "")
		return;
	var ob = frm.all[controlName];
	if(ob == null)
		return;
	var radioOb = null;
	var sK ="";
	switch(ob.tagName){
            case "INPUT" :{
//		alert("ob.type="+ob.type);
                if(ob.type !="radio"){
                    ob.value = defValue;
                    if(ob.type =="checkbox"){
                            if(defValue =="0")
                              ob.checked = true;
                    }
                    break;
                }
              }

            case "SELECT":{
                if(ob.type =="select-one"){
//                  alert("ob.name="+ob.name);
                    if(ob.name == "q_ostation_id")
                          setSelectValues(formName,'q_oline_id','q_ostation_id','commonVariable');
                    if(ob.name == "q_estation_id")
                          setSelectValues(formName,'q_eline_id','q_estation_id','ecommonVariable');
                    if(ob.name == "week")
                          setReportDecisionWeek(formName,'yearWeek','monthWeek','week');
                          for(j=0;j<ob.options.length;j++){
                                  if(ob.options[j].value == defValue || ob.options[j].text ==defValue){
                                          ob.options[j].selected ="true";
                                          break;
                                  }
                          }
                }
                if(ob.type =="select-multiple"){
                        for(j=0;j<ob.options.length;j++){
                                var idx = defValue.indexOf(ob.options[j].text);
                                var idx1 =defValue.indexOf(ob.options[j].value);
                                if(idx !=-1 || idx1 !=-1){
                                        ob.options[j].selected ="true";
                                }
                        }
                }
                break;
            }
            default :{
//		alert("defValue="+defValue);
                for(k=0;k<ob.length;k++){
                        radioOb = ob[k];
//			alert("radioOb.checked="+radioOb.checked);
                        radioOb.checked =false;
                        sK =k;
                        if(sK ==defValue){
                            radioOb.checked = true;
                        }
                }
                break;
            }

	}
}

function setControlNames(formName,controlNames){
	if(formName =="")
		return;
	//alert("formName="+formName+" controlNames="+controlNames);
	var frm = document.forms[formName];
	frm.all['ControlNames'].value=controlNames;
}


function setQueryDateNames(formName,queryDateNames){
	if(formName =="")
		return;
	//alert("formName="+formName+" controlNames="+controlNames);
	var frm = document.forms[formName];
	frm.all['QueryDateNames'].value=queryDateNames;
}


function displayControls(controlNames){
//	alert("controlNames"+controlNames);
	if(controlNames == null || controlNames =='')
		return;
	var idx = controlNames.indexOf("#");
	var controlNamesArray =[];
	if(idx == -1)
		controlNamesArray[0] =controlNames;
	else
		controlNamesArray =controlNames.split("#");
	var controlName = "";
	var ob = null;
	for(i=0;i<controlNamesArray.length;i++){
		controlName =controlNamesArray[i];
//		alert("controlName="+controlName);
		ob = document.getElementById(controlName);
		if(ob == null)
			continue;
//		alert("display");
		ob.style.display ="";
	}
}


function setReportDecisionWeek(formName,yearName,monthName,weekName){
	//alert("formName="+formName+" yearName="+yearName+" monthName="+monthName+" weekName="+weekName);
	var frm = document.forms[formName];
	var yearOb = frm.all[yearName];
	var monthOb = frm.all[monthName];
	var weekOb = frm.all[weekName];
	var currentDate = new Date();
	var current_day = currentDate.getDate();
	
	if(frm == null || yearOb==null || monthOb==null || weekOb ==null){
		alert("窗体或年月周控件不存在");
		return ;
	}
        var year = yearOb.value;
        var month = monthOb.value;

      //  var year = '2005';
      //  var month = '06';
        var end_day_in_month = new Array(31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31);

        if (month == '01') {
              if ((year % 400 == 0) || ((year % 4 == 0) && (year % 100 != 0))) {
                      end_day_in_month[1] = 29;
              }
         }
         if(month < '10')
                 month = month.substring(1);

         var begin_date = new Date(year,month-1,1);
         var begin_date_day = begin_date.getDay();
         var begin_week_day =1;
         var end_week_day = 0;
         var end_month_day = end_day_in_month[month-1];
         var week_spans =[];
         var week_num =0;

      //   alert(" begin_date="+begin_date.toLocaleString()+" begin_date_day="+begin_date_day+" end_month_day="+end_month_day);

         while(begin_week_day <= end_month_day){
           if(week_num ==0)
            end_week_day =begin_week_day+(6-begin_date_day);
           else
              {
              end_week_day =begin_week_day+6;
              if(end_week_day>end_month_day)
                      end_week_day =end_month_day;
            }

           week_spans[week_num]=begin_week_day+':'+end_week_day;
      //     alert(" week_spans="+ week_spans[week_num]);
           week_num++;

           begin_week_day =end_week_day+1;

         }

         var option = null;
         var opvalue = "";
         var caption = "";
         var i=0;
         for(i=weekOb.options.length-1;i>=0;i--)
                      weekOb.remove(i);

         for(i=0;i<week_spans.length;i++){
          opvalue =week_spans[i];
          caption ="第"+(i+1)+"周";
              option = new Option(caption,opvalue);

              weekOb.add(option);
              if(isInRang(year,month,opvalue,current_day))
                option.selected = true;
         }

}


function isInRang(year,month,rang,current_day){
//	alert("year="+year+" month="+month+" rang="+rang+" current_day="+current_day);
	var current_date = new Date();
	var cur_year = current_date.getFullYear();
	var cur_month = current_date.getMonth()+1;
	var sCur_year =""+cur_year;
	var sCur_month =""+cur_month;
	if(sCur_month.length <2)
	  sCur_month ='0'+sCur_month;
	if(year !=sCur_year)
	  return false;
	if(month !=sCur_month)
	  return false;
	  
	var ind = rang.indexOf(":");
	if(ind == -1)
	 return false;
	var begin = rang.substring(0,ind);
	var end = rang.substring(ind+1);
	var iBegin = 0+begin;
	var iEnd = 0+end;
	if(current_day >=iBegin && current_day<=iEnd)
	 return true;
	else
		return false;
}


function setUpdatePrimaryKey(formName,pkControlNames){
    if(formName =='')
            return;
    var frm = document.forms[formName];
    if(frm == null)
          return;
    var ob = frm.all['_updatePKControlNames'];
    if(ob == null)
          return;
    ob.value = pkControlNames;
  //  alert("ob.value="+ob.value);
}


function setListViewDefaultValue(formName,divID){
	if(divID =="" || formName =="")
		return;
	var divOb = document.getElementById(divID);
	var frm = document.forms[formName];
	if(divOb == null || frm ==null)
		return;
	defaultValues = frm.all['updatePKControlValues'].value;
//	alert("defaultValues="+defaultValues);
	if(defaultValues =="")
		return;
	var idx = defaultValues.indexOf(";");
	var defaultValuesArray = [];
	if(idx == -1)
	 defaultValuesArray[0] =defaultValues;
	else
	 defaultValuesArray = defaultValues.split(";");
	var controlName = "";
	var defValue = "";
	var compValue = "";
	for(i=0;i<defaultValuesArray.length;i++){
		compValue = defaultValuesArray[i];
		idx = compValue.indexOf("#");
		if(idx ==-1)
			continue;
		controlName = compValue.substring(0,idx);
		defValue += compValue.substring(idx+1)+"#";
	}
	defValue = defValue.substring(0,defValue.length-1);
//	alert("defValue="+defValue);
	findListViewRecordByDefaultValue(divID,defValue);
}


function findListViewRecordByDefaultValue(divID,defValue){
	divOb = document.getElementById(divID);
	tableOb = divOb.children[0];
	if(tableOb.tagName !="TABLE")
		return;
	var rows = tableOb.rows;
	var row = null;
	for(i=0;i<rows.length;i++){
		row = rows[i];
		if(row.id == "ignore")
			continue;
		if(row.id == defValue){
			row.style.background="#97CBFF";
			row.scrollIntoView(true);
			return;
		}
	}
}
function freshView(formName){
	 var tmp = "sumitCurrentForm('"+formName +"')";
//	 alert(tmp);
	 var interval = 240000;
   setInterval(tmp,interval)		
}


function sumitCurrentForm(formName){
	document.forms[formName].submit();
}


function exportExcelForPrint(listName)
{
	var oXL = new ActiveXObject("Excel.Application");
	var oWB = oXL.Workbooks.Add();
	var oSheet = oWB.ActiveSheet;
        
	var divOb = document.getElementById(listName);
        if(divOb ==null)
            return;
        var table = divOb.children[0];
        if(table == null)
            return;

	if(document.all[listName]==null)
		return;
//	var table = document.all[listName].children[0];
	if(table == null)
		return;
	var rowCount = table.rows.length;
	var colCount = table.rows[0].cells.length;
	

	if(rowCount >100){
	  if(confirm("打印的记录为"+rowCount+"条，其需要较长时间，处理速率约150条/分钟，是否继续？")==false)
	    return null;
        }

	var k=0;
	var l=0;
	var m=0;

	oXL.Caption ="列表数据打印";
//        oSheet.Cells.HorizontalAlignment = -4108;
//        oSheet.Cells.WrapText = true;
//        initPageSetup(oSheet,0.5,colCount);
        if(colCount >8)
                oSheet.PageSetup.Orientation =2;//横向
        var cellText = "";
        var printRows =0;//统计总行数
        
        var printEachRows =0;//统计单行数据占用的打印行数

        var printMaxLen=0;
        var pageFixVMaxRows =47;

        var temp =0;
        var pageMaxRows =45;//A4纸纵向最大行数

        if(colCount >8){
           pageMaxRows =25;//A4纸横向最大行数
           pageFixVMaxRows =29;
        }
        
        var pageNum = 1;//统计页数
        var pageCount = Math.floor(rowCount/pageMaxRows + 0.5);//统计总页数

	for (i=0;i<rowCount;i++)
	{
            if(table.rows[i].id == "ignore"&&i !=0)
                    continue;
                
            //添加页眉
            if(document.getElementById("menuTitle") != undefined){
                oSheet.PageSetup.LeftHeader = document.getElementById("menuTitle").innerHTML;
            }
            
            if(printRows>pageMaxRows)//当累计行数达到一定时，才开始判断是否需要换页
            {

                  //换页
            //      alert("printRows="+printRows);
                  if(isNeedNewPage(table,printRows,colCount,i,pageFixVMaxRows)){//通过本行所占的打印行数判断是否需要换页
                            
//                            oSheet.PageSetup.LeftFooter = "第" + pageNum + "页，共" + pageCount + "页";
                            
                            oSheet.Rows(l+1).PageBreak =1;
                            printRows =0;
            //	  	l++;

            //	  	增加表头行
                            for (m=0;m<colCount;m++){
                                if(m==0){
                                    if(table.rows[0].cells[m].children[0]!=null&&table.rows[0].cells[m].children[0].tagName =="INPUT")//跳过checkbox列
                                            continue;
                                }
                                cellText =table.rows[0].cells[m].innerText;
//                                temp  =getPrintTextRows(cellText);
//                                if(temp>printEachRows)
//                                printEachRows =temp;

                                oSheet.Cells(l+1,m+1).value =  cellText;
                                oSheet.Cells(l+1,m+1).Borders.Weight = 2;        //设置单元格边框*()
                                oSheet.Cells(l+1,m+1).WrapText=true;               //设置为自动换行*
//                                oSheet.Columns.AutoFit();   //自动列宽
                                oSheet.Cells(l+1,m+1).Font.Bold = true;             //设置为粗体*
                            }
//                            printRows +=printEachRows;
//                            printEachRows =0;
                            printRows ++;
                            l++;
                            pageNum++;
                    }
            }
            
            for (j=0;j<colCount;j++)
            {
                if(j==0){
                    if(table.rows[i].cells[j].children[0]!=null&&table.rows[i].cells[j].children[0].tagName =="INPUT")
                            continue;
                }
                
                oSheet.Cells(l+1,k+1).NumberFormat ="\@";//显示为文本格式

                cellText = table.rows[i].cells[j].innerText;
//                temp = getPrintTextRows(cellText);
//                if(temp>printEachRows)
//                    printEachRows =temp;

                if(cellText.length ==0)//处理checkbox的值
                {
                     if(table.rows[i].cells[j].children[0]!=null&&table.rows[i].cells[j].children[0].tagName =="INPUT" && table.rows[i].cells[j].children[0].type =="checkbox")
                     {
                             if(table.rows[i].cells[j].children[0].checked)
                                    cellText ="1";
                              else
                                    cellText ="0";
                    }
                }
                oSheet.Cells(l+1,k+1).value =  cellText;
                oSheet.Cells(l+1,k+1).Borders.Weight = 2;        //设置单元格边框*()
//                oSheet.Cells(l+1,k+1).WrapText=true;               //设置为自动换行*
                oSheet.Columns.AutoFit();   //自动列宽
                if(i == 0){
                     oSheet.Cells(l+1,k+1).WrapText=true;               //设置为自动换行*
                     oSheet.Cells(l+1,k+1).Font.Bold = true;             //设置为粗体*
                }
                
                k++;
            }//换行

            k=0;
            l++;
            printRows ++;
            
//            printRows +=printEachRows;
//            printEachRows =0;

	}
	oXL.Visible = true;
//	oXL.UserControl = true;
	return oXL;
}
//获得文本所占行数

function getPrintTextRows(printText){
	var len = printText.lenb();
//	alert("text="+printText+"len="+len);
	var i = len/8;
	i =Math.round(i);
	if(i>len/8)
	  i=i-1;
	var rest =len%8;
	var printRows =0;
//	alert("i="+i+"rest="+rest);
	if(i==0)
		  i=1;
	if(rest ==0)
		printRows =i;	
	else
		printRows =i+1;
	return printRows;
}

/*** 返回字节数 ***/
String.prototype.lenb = function() {
  return this.replace(/[^x00-xff]/g,"**").length;
}


function isNeedNewPage(table,printRows,colCount,i,pageFixRows){
	var  j=0;
	var cellText ="";
	var temp ="";
	var printEachRows =0;

	
	for (j=0;j<colCount;j++)
		{
			if(j==0){
				if(table.rows[i].cells[j].children[0]!=null&&table.rows[i].cells[j].children[0].tagName =="INPUT")
					continue;
			}
	//	  oSheet.Cells(l+1,k+1).NumberFormat ="\@";

		  cellText =table.rows[i].cells[j].innerText;
		  temp  =getPrintTextRows(cellText);
	//	  alert(temp);
		  if(temp>printEachRows)
		    printEachRows =temp;
		}
		if(pageFixRows <(printRows+printEachRows))
		 return true;
		else
			return false;
}


function displayBalanceDate(){
	 alert("displayBalanceDate");
	if(this.checked)
		this.parent.style="display:true";
	else
		this.parent.style="display:none";
}


//formName 提交窗体名称；reportType报表类型；controlNames查找报表代码的控件名称按顺序用＃分隔;tableNamex显示列表名称
function btnClickForGenReport(formName,controlNames,paramNames,queryDateNames){
	if(formName =='')
		return;

	var frm = document.forms[formName];
	if(frm == null)
		return;
		
	if(!Validator.Validate(frm,'1'))
		return;
	var tableName = "reportList";
	
	setOperation(formName,"op_reportQuery");
	setCommand(formName,"reportQuery");
	setSubmitForm(formName);
	setControlNames(formName,controlNames);
	setQueryDateNames(formName,queryDateNames);
    setParameterNames(formName,paramNames);
    disabeQueryControl(formName,"ReportQuery");
	queryReport(formName,controlNames);

}


function setParameterNames(formName,parameterNames){
	if(formName =="")
		return;
	//alert("formName="+formName+" parameterNames="+controlNames);
	var frm = document.forms[formName];
	frm.all['parameterNames'].value=parameterNames;
}


function setLinkTextForRT(formName,controlNames){
	if(formName =="")
		return;
	var frm = document.forms[formName];
	if(frm == null)
		return;
	var caption = "";
	var tableName="reportList";

	caption = addCaptionInfoForRT(formName,controlNames,caption);
	setTableLinkTextForRT(tableName,caption);
}


function addCaptionInfoForRT(formName,controlNames,caption){
	var controlsText = getControlsText(formName,controlNames);
//	alert("controlsText="+controlsText+" caption="+caption);
	var result = caption;
	if(controlsText !="")
	  result +=  controlsText ;
	 // result += "(" + controlsText +")";
	return result;

}

function setTableLinkTextForRT(tableName,caption){
	var tbl = document.getElementById(tableName);
	if(tbl == null)
		return;
	var rows = tbl.rows;
	var linkCell = null;
	var linkOb = null;
	for(i=1;i<rows.length;i++){
		linkCell = tbl.rows[i].cells[1];
		linkOb = linkCell.children[0].children[0];
		linkOb.innerHTML = linkOb.innerHTML+caption;
	}
	
}

function setPageControl(formName){
  setPageControlForBack(formName);
  setPageControlForNext(formName);
  setPageControlForNextEnd(formName);
  setPageControlForBackEnd(formName);
}

function setPageControlForBack(formName){
  var oBack = document.forms[formName].all['_back'];
  //var oCurrent = document.forms[formName].all['_current'];
  var sBack = oBack.value;
  var back = document.forms[formName].all['btBack'];
  if(sBack ==null ||sBack.length ==0){
    back.disabled = true;
    return;
  }
  var iBack = sBack-0;
  if(iBack <1)
     back.disabled = true;
   else
     back.disabled = false;


}

function setPageControlForNext(formName){
  var oNext = document.forms[formName].all['_next'];
  var oNextEnd = document.forms[formName].all['_nextEnd'];
  //var oCurrent = document.forms[formName].all['_current'];
  var sNext = oNext.value;
  var sNextEnd =oNextEnd.value;

  var next = document.forms[formName].all['btNext'];
  if(sNext ==null ||sNext.length ==0){
    next.disabled = true;
    return;
  }
  var iNext = sNext-0;
  var iNextEnd = sNextEnd-0;
  if(iNext >iNextEnd)
     next.disabled = true;
   else
     next.disabled = false;
}

function setPageControlForNextEnd(formName){

  var oNextEnd = document.forms[formName].all['_nextEnd'];
  var oCurrent = document.forms[formName].all['_current'];

  var sNextEnd =oNextEnd.value;
  var sCurrent = oCurrent.value;

  var nextEnd = document.forms[formName].all['btNextEnd'];
  if(sNextEnd ==null ||sNextEnd.length ==0){
    nextEnd.disabled = true;
    return;
  }
  var iNextEnd = sNextEnd-0;
  var iCurrent = sCurrent-0;

  if(iNextEnd ==1 || iNextEnd == iCurrent)
     nextEnd.disabled = true;
   else
     nextEnd.disabled = false;
}

function setPageControlForBackEnd(formName){

  var oBackEnd = document.forms[formName].all['_backEnd'];
  var oCurrent = document.forms[formName].all['_current'];

  var sBackEnd =oBackEnd.value;
  var sCurrent = oCurrent.value;

  var backEnd = document.forms[formName].all['btBackEnd'];
  if(sBackEnd ==null ||sBackEnd.length ==0){
    backEnd.disabled = true;
    return;
  }
  var iBackEnd = sBackEnd-0;
  var iCurrent = sCurrent-0;

  if( iBackEnd == iCurrent)
     backEnd.disabled = true;
   else
     backEnd.disabled = false;
}

function returnByMinDate(){
        var min_return_date2=document.getElementById('min_return_date2').value;
        if (min_return_date2==null) return;
        var myDate=new Date();
        var month=myDate.getMonth()+1;
        var day=myDate.getDate();
        if (month<10)
                month='0'+month;
        if (day<10)
                day='0'+day;
        var curr_date=myDate.getFullYear()+"-"+month+"-"+day; 

        if (curr_date<min_return_date2){
           document.getElementById('btRefundOk').disabled = true;
           document.getElementById('btRefundNo').disabled = true;
           document.getElementById('btRefundMd').disabled = true;
           document.getElementById('btRefundCk').disabled = true;
        }  	 	
}

//20120606 luojun
function changeRowColor(divID){
        divOb = document.getElementById(divID);
        tableOb = divOb.children[0];
        if(tableOb.tagName !="TABLE")
                return;
        var rows = tableOb.rows;
        var row = null;
        for(i=0;i<rows.length;i++){
            row = rows[i];
            if(row.id == "ignore")
                    continue;
            //alert(row.cells[6].innerText);
            if(row.cells[10].innerText == "0"){
                    row.style.background="red";
                    //row.scrollIntoView(true);
                    //return;
            }
        }
}

//保存文件
function saveFile(formName){
    
    setCommand(formName,"download");
    var checkBoxStr = getCheckBox(formName);
    setCcheckbox(formName,checkBoxStr);
    setSubmitForm(formName);
    fileDownAction(formName);
    
//    setCommand(formName,"query");
//    queryAction(formName);
}

//清分权重导出提交
function fileDownAction(formName){

    frm = document.forms[formName];
    if(!Validator.Validate(frm,'1')){
        return;
    }
//    disabeQueryControl(formName,"btFileDown");
    frm.submit();
}

//生成按钮提交
function generateAction(formName,buttonName){

	frm = document.forms[formName];
	if(!Validator.Validate(frm,'1'))
		return;
	document.getElementById('generating').innerHTML="请稍等...";
        disabeQueryControl(formName,buttonName);
        frm.submit();
}

//清分权重导出字段
 function getCheckBox(formName)
{
    frm = document.forms[formName];
    var obj = frm.c;
    var array = '';
    if(obj.length<1){
        alert("请选择导出字段");
        return;
    }
    for(var i=0;i<obj.length;i++)
   {
       if(obj[i].checked){
            array += obj[i].value;
            array += "#";
        }
    }
    return array;
}

//清分权重导出字段传到后台
function setCcheckbox(formName,value){
	if (value != ""){
		var checkbox=document.forms[formName].all["c_checkbox"];
		if (checkbox !== null){
			checkbox.value=value;
                }
	}
}

//检验结束时间大于开始时间
var cDate = true;
function checkDate(stDate,edDate){
    cDate = true;
    
    if(checkNull(stDate) && checkNull(edDate)){
        var sd = document.getElementById(stDate).value;
        var ed = document.getElementById(edDate).value;
        if(sd > ed){
            cDate = false;
        }
    }
    return cDate;
}

//检验元素是否为null或undefined,''
function checkNull(id){
    var vid = document.getElementById(id);
    return  vid != null && vid != 'undefined' && vid != undefined && vid.value != '';
}
