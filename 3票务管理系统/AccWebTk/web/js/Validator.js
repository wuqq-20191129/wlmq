
/*************************************************
 Validator form check
 
 *************************************************/
Validator = {
    phyCard: /^([0-9A-Fa-f]|[A-F0-9a-f]|[a-fA-F0-9]|[a-f0-9A-F])+$/,
    logicNo: /^([0-9A-Za-z]{15}[0-9]{5})+$/,
//IPAddress:/^[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}$/,
    IPAddress: /^([1-9]|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.([0-9]|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.([0-9]|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.([1-9]|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])$/,
    ICCSDate: /^[0-9]{4}-[0-9]{2}-[0-9]{2}$/,
    ICCSDateTime: /^[0-9]{4}-[0-9]{2}-[0-9]{2} [0-9]{2}:[0-9]{2}:[0-9]{2}$/,
    ICCSDateHHmm: /^([0-1]?[0-9]|2[0-3])([0-5][0-9])$/,
    ICCSDateHHmmss: /^([0-1]?[0-9]|2[0-3]):([0-5][0-9]):([0-5][0-9])$/,
    Require: /.+/,
    NotNull: /.+/,
    Email: /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/,
    Phone: /^((\(\d{2,3}\))|(\d{3}\-))?(\(0\d{2,3}\)|0\d{2,3}-)?[1-9]\d{6,7}(\-\d{1,4})?$/,
    Mobile: /^((\(\d{2,3}\))|(\d{3}\-))?13\d{9}$/,
    Url: /^http:\/\/[A-Za-z0-9]+\.[A-Za-z0-9]+[\/=\?%\-&_~`@[\]\':+!]*([^<>\"\"])*$/,
    IdCard: /^\d{15}(\d{2}[A-Za-z0-9])?$/,
    Currency: /^\d+(\.\d+)?$/,
    Number: /^\d+$/,
    NumAndEng: /^[0-9A-Za-z]+$/,
    Zip: /^[1-9]\d{5}$/,
    QQ: /^[1-9]\d{4,8}$/,
    Integer: /^[-\+]?\d+$/,
    integer: /^[+]?\d+$/,
    Double: /^[-\+]?\d+(\.\d+)?$/,
    double: /^[+]?\d+(\.\d+)?$/,
    English: /^([A-Za-z]|[,\!\*\.\ \(\)\[\]\{\}<>\?\\\/\'\"])+$/,
    Chinese: /^[\u0391-\uFFE5]|[,\!\*\.\ \(\)\[\]\{\}<>\?\\\/\'\"]+$/,
    Username: /^[a-z]\w{3,}$/i,
    id: /^[0-9]\d{0,18}$/,
    order: /^[0-9]\d{0,14}$/,
    struts_id: /^[0-9]\d{0,2}$/,
    struts_value: /^[0-9]\d{0}$/,
    iccs_value: /^[0-9]\d{0}$/,
    UnSafe: /^(([A-Z]*|[a-z]*|\d*|[-_\~!@#\$%\^&\*\.\(\)\[\]\{\}<>\?\\\/\'\"]*)|.{0,5})$|\s/,
    IsSafe: function (str) {
        return !this.UnSafe.test(str);
    },
    Positive: "this.isPositive(value)",
    NotNegative: "this.isNotNegative(value)",
    SafeString: "this.IsSafe(value)",
    Filter: "this.DoFilter(value, getAttribute('accept'))",
    Limit: "this.limit(value.length,getAttribute('min'), getAttribute('max'))",
    LimitB: "this.limit(this.LenB(value), getAttribute('min'), getAttribute('max'))",
    LimitContainChinese:"this.limit(this.CountStr(value), getAttribute('min'), getAttribute('max'))",
    LimitContainForBlank:"this.limitForBlank(this.CountStr(value), getAttribute('min'), getAttribute('max'))",
    Date: "this.IsDate(value, getAttribute('min'), getAttribute('format'))",
    CherkDate: "this.IsCheckDate(value)",
    Repeat: "value == document.getElementsByName(getAttribute('to'))[0].value",
    ThanDate: "value >= document.getElementsByName(getAttribute('to'))[0].value",
    thanDate: "value > document.getElementsByName(getAttribute('to'))[0].value",
    Range: "getAttribute('min') <= (value|0) && (value|0) <= getAttribute('max')",
    Compare: "this.compare(value,getAttribute('operator'),getAttribute('to'))",
    CompareValue: "this.compare(value,getAttribute('operator1'),getAttribute('to1'))",
    CompareControl: "this.compare(value,getAttribute('operator'),document.getElementById(getAttribute('to')).value)",
    CompareNum: "this.compareNum(value,getAttribute('operator'),document.getElementById(getAttribute('to')).value)",
    CompareBigNum : "this.compareBigNum(value,getAttribute('operator'),document.getElementById(getAttribute('to')).value)",
    CompareDateByNum: "this.compareDateByNum(value,getAttribute('operator'),document.getElementById(getAttribute('to')).value,getAttribute('num'))",
    SameLen:"this.isSameLen(value,document.getElementById(getAttribute('to')).value)",
    Custom: "this.Exec(value, getAttribute('regexp'))",
    Group: "this.MustChecked(getAttribute('name'), getAttribute('min'), getAttribute('max'))",
    NotEmpty: "this.IsNotEmpty(value)",
    NotEmptyForBlank: "this.IsNotEmptyForBlank(value)",
    MaxValue: "this.IsMaxValue(value)",
    dateDiff: "this.dateTimeDiff(getAttribute('name'), getAttribute('to'))",
    ErrorItem: [document.forms[0]],
    ErrorMessage: [],
    IsCheckDate: function (value)//校验输入日期不大于系统当前日期  ldz 20170904
    {
        var arr = value.split("-");
        var starttime = new Date(arr[0], arr[1], arr[2]);
        var starttimes = starttime.getTime();
        var d = new Date();
        var s = d.getFullYear()+ "-" +(d.getMonth() + 1) + "-" + d.getDate();
       var arrs = s.split("-");
       var lktime = new Date(arrs[0], arrs[1], arrs[2]);
       var lktimes = lktime.getTime();
        if (starttimes  > lktimes) {
            return false;
        } else {
            return true;
        }

    },
    IsNotEmpty: function (ctrValue) {
        if (ctrValue == null)
            return false;
        //alert(ctrValue);
        for (i = 0; i < ctrValue.length; i++) {
            //	alert(ctrValue.charAt(i));
            if (ctrValue.charAt(i) != ' ')
                return true
        }
        return false;
    },
    IsNotEmptyForBlank: function (ctrValue) {
        var a  = document.getElementById("d_start_logicno").disabled;
//        alert(a);
        if(!a){
        if (ctrValue == null)
            return false;
        //alert(ctrValue);
        for (i = 0; i < ctrValue.length; i++) {
            //	alert(ctrValue.charAt(i));
            if (ctrValue.charAt(i) != ' ')
                return true
        }
        return false;
    }
    return true;
},
    IsMaxValue: function (ctrValue) {
        if (ctrValue == null)
            return false;
        //alert(ctrValue);
        if (ctrValue >= 256)
            return false;
        return true;
    },
    Validate: function (theForm, mode) {
        var obj = theForm || event.srcElement;
        var count = obj.elements.length;
        this.ErrorMessage.length = 1;
        this.ErrorItem.length = 1;
        this.ErrorItem[0] = obj;
        for (var i = 0; i < count; i++) {
            with (obj.elements[i]) {
                var _dataType = "";
//getAttribute("dataType");
                var _dataTypes = getAttribute("dataType");
                if (_dataTypes == null)
                    continue;
                //	alert("_dataTypes="+_dataTypes);
                var index = _dataTypes.indexOf("|");
                var _dataTypesArray = [];
                var result = true;
                if (index == -1)
                    _dataTypesArray[0] = _dataTypes;
                else
                    _dataTypesArray = _dataTypes.split("|");

                for (j = 0; j < _dataTypesArray.length; j++) {
                    _dataType = _dataTypesArray[j];
//			alert("_dataType="+_dataType);
                    if (typeof (_dataType) == "object" || typeof (this[_dataType]) == "undefined")
                        continue;
                    this.ClearState(obj.elements[i]);
                    if (getAttribute("require") == "false" && value == "")
                        continue;
                    switch (_dataType) {
                        case "Date" :
                        case "Repeat" :
                        case "Range" :
                        case "Compare" :
                        case "Custom" :
                        case "Group" :
                        case "Limit" :
                        case "LimitB" :
                        case "LimitContainChinese" :
                        case "LimitContainForBlank":
                        case "SafeString" :
                        case "Filter" :
                        case "ThanDate" :
                        case "dateDiff" :
                        case "thanDate" :
                        case "NotEmpty":
                        case "NotEmptyForBlank":
                        case "MaxValue":
                        case "CompareControl":
                        case "CompareNum":
                        case "CompareBigNum":
                        case "SameLen":
                        case "CompareDateByNum":
                        case "CompareValue":
                        case "Positive":
                        case "NotNegative":
                            case "CherkDate":
                            if (!eval(this[_dataType])) {
                                result = false;
//this.AddError(i, getAttribute("msg"));
                            }
                            break;
                        default :
                            if (!this[_dataType].test(value)) {
//				this.AddError(i, getAttribute("msg"));
                                result = false;
                            }
                            break;
                    }//end switch
                }//end for j
                if (!result)
                    this.AddError(i, getAttribute("msg"));

            }//end with
        }// end for i
        if (this.ErrorMessage.length > 1) {
            mode = mode || 1;
            var errCount = this.ErrorItem.length;
            switch (mode) {
                case 2 :
                for (var i = 1; i < errCount; i++)
                    this.ErrorItem[i].style.color = "red";
                case 1 :
                    alert(this.ErrorMessage.join("\n"));
                    this.ErrorItem[1].focus();
                    break;
                case 3 :
                    for (var i = 1; i < errCount; i++) {
                        try {
                            var span = document.createElement("SPAN");
                            span.id = "__ErrorMessagePanel";
                            span.style.color = "red";
                            this.ErrorItem[i].parentNode.appendChild(span);
                            span.innerHTML = this.ErrorMessage[i].replace(/\d+:/, "*");
                        } catch (e) {
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
    isPositive: function (value) {
        //alert(value);
        if (value > 0)
            return true;
        return false;
    },
    isNotNegative: function (value) {
        //alert(value);
        if (value >= 0)
            return true;
        return false;
    },
    limit: function (len, min, max) {
        min = min || 0;
        max = max || Number.MAX_VALUE;
        return min <= len && len <= max;
    },
    limitForBlank: function (len, min, max) {
        var a =document.getElementById("d_start_logicno").disabled;
        if(!a){
        var logicNo = document.getElementById("d_start_logicno").value;
        var factory_id = document.getElementById("d_produce_factory_id").value;
        var ticket = document.getElementById("d_blank_card_type").value;
        document.getElementById("d_start_logicno").setAttribute("msg","开始序列号应为9位数字");
        if(ticket == '80'){
             if(factory_id == '80' ){
                if(logicNo.substr(0,1) != '1'){
                document.getElementById("d_start_logicno").setAttribute("msg","开始序列号应为9位数字,且该厂商手机票序列第一位的值必须为1");
                
                return false;
            }
            }else if(factory_id == '81'){
                if(logicNo.substr(0,1) != '9'){
                document.getElementById("d_start_logicno").setAttribute("msg","开始序列号应为9位数字,且该厂商手机票序列第一位的值必须为9");
                return false;
            }
        }
        }
       
        min = min || 0;
        max = max || Number.MAX_VALUE;
        return min <= len && len <= max;
    }
    return true;
    },
    LenB: function (str) {
        return str.replace(/[^\x00-\xff]/g, "**").length;
},
CountStr : function(str){//add by zhongziqi 201707029
    var realLength = 0, len = str.length, charCode = -1;
    for (var i = 0; i < len; i++) {
    charCode = str.charCodeAt(i);
    if (charCode >= 0 && charCode <= 128) realLength += 1;
    else realLength += 3;
    }
return realLength;
    },
    CountStrForBlank : function(str){//add by ldz
var a =document.getElementById("d_start_logicno").disabled;
        if(!a){
    var realLength = 0, len = str.length, charCode = -1;
    for (var i = 0; i < len; i++) {
    charCode = str.charCodeAt(i);
    if (charCode >= 0 && charCode <= 128) realLength += 1;
    else realLength += 3;
    }
return realLength;
    }
},

    ClearState: function (elem) {
        with (elem) {
            if (style.color == "red")
                style.color = "";
            var lastNode = parentNode.childNodes[parentNode.childNodes.length - 1];
            if (lastNode.id == "__ErrorMessagePanel")
                parentNode.removeChild(lastNode);
        }
    },
    AddError: function (index, str) {
        this.ErrorItem[this.ErrorItem.length] = this.ErrorItem[0].elements[index];
        this.ErrorMessage[this.ErrorMessage.length] = this.ErrorMessage.length + ":" + str;
    },
    Exec: function (op, reg) {
        return new RegExp(reg, "g").test(op);
    },
    compare: function (op1, operator, op2) {
        switch (operator) {
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
    compareNum: function (op1, operator, op2) {
        //alert((op1-0)+","+(op2-0));
        switch (operator) {
            case "NotEqual":
                return ((op1 - 0) != (op2 - 0));
            case "GreaterThan":
                return ((op1 - 0) > (op2 - 0));
            case "GreaterThanEqual":
                return ((op1 - 0) >= (op2 - 0));
            case "LessThan":
                return ((op1 - 0) < (op2 - 0));
            case "LessThanEqual":
                return ((op1 - 0) <= (op2 - 0));
            default:
                return ((op1 - 0) == (op2 - 0));
        }
    },
    
    compareBigNum: function (op1, operator, op2) {//add by zhongziqi 20170821
        //比较超越js Number类型最大整数值，方法限制判断16-30位的值
//        alert("o2:"+op2);
        if (op2 == "") {
            return true;
        }
        var num1Part1 = new Number(op1.substr(0, 15));
        var num1Part2 = new Number(op1.substr(15, op1.length));
        var num2Part1 = new Number(op2.substr(0, 15));
        var num2Part2 = new Number(op2.substr(15, op2.length));
        var len1 = 0;
        var len2 = 0;
        if (num1Part1 != 0) {
            len1 = num1Part1.toString().length + op1.substr(15, op1.length).length;//真实长度
        } else {
            len1 = num1Part2.toString().length;
        }
        if (num2Part1 != 0) {
            len2 = num2Part1.toString().length + op2.substr(15, op2.length).length;//真实长度
        } else {
            len2 = num2Part2.toString().length;
        }
//        alert ("num1Part1:"+num1Part1+"\rnum1Part2:"+num1Part2+"\rnum2Part1:"+num2Part1+"\rnum2Part2:"+num2Part2);
//        alert ("Part1>=0:"+(num1Part1 - num2Part1 >=0)+"\rPart2:"+(num1Part2 -num2Part2));
//        alert("len1:"+len1+"\rlen2："+len2+"\r"+operator);

        switch (operator) {
            case "NotEqual":
                return ((num1Part1 != num2Part1) && (num1Part2 != num2Part2));
            case "GreaterThan":
                if (len1 < len2) {
                    return false;
                } else if (len1 == len2) {
                    if(num1Part1 - num2Part1> 0){
                        return true;
                    }else if(num1Part1-num2Part1==0){
                        if(num1Part2 -num2Part2>0){
                            return true;
                        }
                        return false;
                    }
                    else{
                        return false;
                    }
                } else if (len1 > len2) {
                    return true;
                }
            case "GreaterThanEqual":
                if (len1 < len2) {
                    return false;
                } else if (len1 == len2) {
                    if(num1Part1 - num2Part1>0){
                        return true;
                    }else if(num1Part1 - num2Part1==0){
                        if(num1Part2 -num2Part2>=0){
                            return true;
                        }
                        return false;
                    }else{
                        return false;
                    }
                } else if (len1 > len2) {
                    return true;
                }
            case "LessThan":
                if (len1 < len2) {
                    return true;
                } else if (len1 == len2) {
                    if(num1Part1 - num2Part1< 0){
                        return true;
                    }else if(num1Part1 - num2Part1 ==0){
                        if(num1Part2 -num2Part2<0){
                            return true;
                        }
                        return false;
                    }else{
                        return false;
                    }
                    
                } else if (len1 > len2) {
                    return false;
                }
            case "LessThanEqual":
                if (len1 < len2) {
                    return true;
                } else if (len1 == len2) {
                    if(num1Part1 - num2Part1 <0){
                        return true;
                    }else if(num1Part1 - num2Part1 == 0){
                        if(num1Part2 -num2Part2 <=0){
                            return true;
                        }
                        return false;
                    }else{
                        return false;
                    }
                } else if (len1 > len2) {
                    return false;
                }
            default:
                return ((num1Part1 - num2Part1==0) && (num1Part2 - num2Part2==0));
        }
    },
    isSameLen:function(op1,op2){
        var num1 = op1.length;
        var num2 = op2.length;
//        alert(num1+"||"+num2);
        if(num1==num2){
            return true
        }
        return false;
    },
    compareDateByNum: function (op1, operator, op2, num) {
//alert(op1+':'+operator+':'+op2+':'+num);
        var d1 = new Date(op1.substr(0, 4), op1.substr(5, 2) - 1, op1.substr(8, 2));
        var d2 = new Date(op2.substr(0, 4), op2.substr(5, 2) - 1, op2.substr(8, 2));
//alert(op1.substr(0,4)+":"+op1.substr(5,2)+":"+op1.substr(8,2));
        var ms1 = d1.getTime();
//alert(ms1);
        var ms2 = d2.getTime() + ((num - 1) * 24 * 3600 * 1000);
//alert(ms2);

        switch (operator) {
            case "NotEqual":
                return ((ms1) != (ms2));
            case "GreaterThan":
                return ((ms1) > (ms2));
            case "GreaterThanEqual":
                return ((ms1) >= (ms2));
            case "LessThan":
                return ((ms1) < (ms2));
            case "LessThanEqual":
                return ((ms1) <= (ms2));
            default:
                return ((ms1) == (ms2));
        }
    },
    MustChecked: function (name, min, max) {
        var groups = document.getElementsByName(name);
        var hasChecked = 0;
        min = min || 1;
        max = max || groups.length;
        for (var i = groups.length - 1; i >= 0; i--)
            if (groups[i].checked)
                hasChecked++;
        return min <= hasChecked && hasChecked <= max;
    },
    DoFilter: function (input, filter) {
        return new RegExp("^.+\.(?=EXT)(EXT)$".replace(/EXT/g, filter.split(/\s*,\s*/).join("|")), "gi").test(input);
    },
    IsDate: function (op, formatString) {
        formatString = formatString || "ymd";
        var m, year, month, day;
        switch (formatString) {
            case "ymd" :
                m = op.match(new RegExp("^((\\d{4})|(\\d{2}))([-./])(\\d{1,2})\\4(\\d{1,2})$"));
                if (m == null)
                    return false;
                day = m[6];
                month = m[5] * 1;
                year = (m[2].length == 4) ? m[2] : GetFullYear(parseInt(m[3], 10));
                break;
            case "dmy" :
                m = op.match(new RegExp("^(\\d{1,2})([-./])(\\d{1,2})\\2((\\d{4})|(\\d{2}))$"));
                if (m == null)
                    return false;
                day = m[1];
                month = m[3] * 1;
                year = (m[5].length == 4) ? m[5] : GetFullYear(parseInt(m[6], 10));
                break;
            default :
                break;
        }
        if (!parseInt(month))
            return false;
        month = month == 0 ? 12 : month;
        var date = new Date(year, month - 1, day);
        return (typeof (date) == "object" && year == date.getFullYear() && month == (date.getMonth() + 1) && day == date.getDate());
        function GetFullYear(y) {
            return ((y < 30 ? "20" : "19") + y) | 0;
        }
    },
    dateTimeDiff: function dateDiff(datetime1, datetime2) {
        var date1 = new Date();
        var date2 = new Date();
        var diff = new Date();
        var M1 = document.getElementById(datetime1).value;
        date1temp = new Date(M1.split("-")[0], M1.split("-")[1] - 1, M1.split("-")[2]);
        date1.setTime(date1temp.getTime());

        var M2 = document.getElementById(datetime2).value;
        date2temp = new Date(M2.split("-")[0], M2.split("-")[1] - 1, M2.split("-")[2]);
        date2.setTime(date2temp.getTime());

        diff.setTime(Math.abs(date1.getTime() - date2.getTime()));
        timediff = diff.getTime();
        // alert(Math.floor(timediff / (1000 * 60 * 60 * 24)));
        if (Math.floor(timediff / (1000 * 60 * 60 * 24)) <= 90)
            return true;
        else
            return false;
    }

}
