$().ready(function () {
    jQuery.validator.addMethod("noEqualTo", function (value1, element, value2) {
        return this.optional(element) || value1 != $(value2).val();
    }, "两个字段不能相同！");

    //字符验证，只能包含英文、数字、下划线等字符。    
    jQuery.validator.addMethod("stringCheck", function (value, element) {
        return this.optional(element) || /^[a-zA-Z0-9_]+$/.test(value);
    }, "只能包含英文、数字、下划线");

    jQuery.validator.addMethod("safePassword", function (value, element) {
        return this.optional(element) || !(/^(([A-Z]*|[a-z]*|\d*|[-_\~!@#\$%\^&\*\.\(\)\[\]\{\}<>\?\\\/\'\"]*)|.{0,5})$|\s/.test(value));
    }, "密码应包含字母、数字和特殊符号的一种以上，不允许出现空格，至少6位的长度");


    $("form").validate({
        onsubmit: true, //提交时才校验
        focusInvalid: true,
        rules: {
            Account: {
                required: true,
                maxlength: 8,
                minlength: 4,
                stringCheck: true
            },
            Password: {
                required: true,
                maxlength: 8,
                minlength: 4,
                stringCheck: true
            },
            Newpassword: {
                required: true,
                maxlength: 8,
                minlength: 4,
                stringCheck: true,
                safePassword: true,
                noEqualTo: "#Password"
            },
            Repassword: {
                required: true,
                equalTo: "#Newpassword"
            }
        },
        messages: {
            Account: {
                required: "请输入用户名",
                maxlength: "用户名长度小于8",
                minlength: "用户名长度大于4"
            },
            Password: {
                required: "请输入密码",
                maxlength: "密码长度小于8",
                minlength: "密码长度大于4"
            },
            Newpassword: {
                required: "请输入新密码",
                maxlength: "密码长度小于8",
                minlength: "密码长度大于4",
                noEqualTo: "新旧密码不能相同！"
            },
            Repassword: {
                required: "请输入新密码",
                equalTo: "再次密码不相同！"
            }
        },
        submitHandler: function () {
            var formValues = $("form").serializeArray();
            //同步
            $.ajaxSetup({async: false});
            //提交表单
            $.post("logon", formValues, function (data) {
                $("#Password").attr("placeholder", "密码");
                $("#Newpassword").css("display", "none");
                $("#Repassword").css("display", "none");
                $("#cancel").css("display", "none");
                $("label").css("display", "none");
                $("#logon-error").css("display", "none");
                if (data != 0) {
                    $("#logon-error").css("display", "inline");
                }
                switch (data) {
                    case "0":
                        window.location.href = "topFrame";
                        break;
                    case "301":
                        $("#logon-error").text("用户不存在！");
                        break;
                    case "302":
                        $("#logon-error").text("用户被锁定！");
                        break;
                    case "303":
                        $("#logon-error").text("用户已过期！");
                        break;
                    case "304":
                        $("#logon-error").text("密码错误！");
                        break;
                    case "305":
                        $("#Password").attr("placeholder", "旧密码");
                        $("#Newpassword").css("display", "inline");
                        $("#Repassword").css("display", "inline");
                        $("#loginFlag").val("edit");
                        $("#logon-error").text("密码已过期！");
                        $("#cancel").css("display", "inline");
                        break;
                    case "306":
                        $("#logon-error").text("用户IP不能访问应用！");
                        break;
                    case "307":
                        $("#logon-error").text("密码修改失败！");
                        $("#Password").attr("placeholder", "旧密码");
                        $("#Newpassword").css("display", "inline");
                        $("#Repassword").css("display", "inline");
                        $("#cancel").css("display", "inline");
                        break;
                    case "310":
                        $("#logon-error").text("数据库连接错误！");
                        break;
                    default:
                        window.location.href = "login.html";
                }
               if (data != 0) {
                    $(window).on('unload', function () {
                        location.reload();
                    });
                }
            });
//            return false; //此处必须返回false，阻止常规的form提交
        }
    });

    $("#cancel").click(function () {
        $("#Password").attr("placeholder", "密码");
        $("#Newpassword").css("display", "none");
        $("#Repassword").css("display", "none");
        $("#Newpassword").val("");
        $("#Repassword").val("");
        $("#cancel").css("display", "none");
        $("label").css("display", "none");
        $([name = "loginFlag"]).val("login");
        nrequired = false;
    });

    $("input").keydown(function () {
        $("#logon-error").css("display", "none");
    });

    $("input").blur(function () {
        if (this.id == "Newpassword" || this.id == "Password" || this.id == "Repassword") {
            $("#" + this.id + "_caps")[0].style.display = "none";
            $("#" + this.id + "_caps")[0].style.visibility = "hidden";
        }
    });

    //判断大写锁定
    $("input").keyup(function () {
        //IE10,11不显示
        if (navigator.userAgent.indexOf("MSIE 10.0") > -1 || navigator.userAgent.indexOf("rv:11.0") > -1) {
            return;
        }
        if (this.id == "Newpassword" || this.id == "Password" || this.id == "Repassword") {
            var e = event || window.event;
            var keyvalue = e.keyCode ? e.keyCode : e.which;
            var shifKey = e.shiftKey ? e.shiftKey : ((keyvalue == 16) ? true : false);
            var strlen = this.value.length;
            if (strlen) {
                var uniCode = this.value.charCodeAt(this.value.length - 1);
                if (keyvalue >= 65 && keyvalue <= 90) {     //如果是字母键  
                    if (((uniCode >= 65 && uniCode <= 90) && !shifKey) || ((uniCode >= 97 && uniCode <= 122) && shifKey)) {
                        $("#" + this.id + "_caps")[0].style.display = "";
                        $("#" + this.id + "_caps")[0].style.visibility = "";
                        return;
                    }
                }
            }
            $("#" + this.id + "_caps")[0].style.display = "none";
            $("#" + this.id + "_caps")[0].style.visibility = "hidden";
        }
    });

    //显示版本号
    $.get("SysVersion", function (data) {
        $("#version").html("v." + data);
    });
});


/* 检测浏览器是否支持 placeholder 属性
 * 为不支持 placeholder 属性的浏览器写兼容代码
 * 注意，需要 placeholder 的表单控件不要写value属性。
 * 本代码只操作 input 控件，如果需要支持其他控件请自己修改代码
 * 下面对 required 属性的操作同理。
 */
var testElement = document.createElement('input');
var placeholderSupported = 'placeholder' in testElement;
if (!placeholderSupported) {
    var inputs = document.getElementsByTagName('input');
    for (var n = 0; n < inputs.length; n++) {
        var input = inputs[n];
        var placeholder = input.placeholder ? input.placeholder : input.getAttribute('placeholder');
        //部分浏览器不支持直接操作自定义属性，用 getAttribute() 来获取自定义属性。
        if (!placeholder)
            continue;
        input.value = placeholder;
        input.onfocus = function () {
            if (this.value == this.getAttribute('placeholder')) {
                this.value = '';
            }
            this.style.color = '#999';
        };
//                            input.onmouseover=function(){
//                                    this.focus();
//                            }
        input.onblur = function () {
            if (this.value == '') {
                this.value = this.getAttribute('placeholder');
                this.style.color = '#000';
            }
        }
    }
} else {
    var inputs = document.getElementsByTagName('input');
    for (var n = 0; n < inputs.length; n++) {
        inputs[n].onmouseover = function () {
//                                    this.focus();
        }
    }
}
/* 检测浏览器是否支持 required 属性
 * 为不支持 required 属性的浏览器写兼容代码
 */
var requiredSupported = 'required' in testElement && !/Version\/[\d\.]+\s*Safari/i.test(navigator.userAgent);
if (!requiredSupported) {
    document.getElementsByTagName('form')[0].onsubmit = function (e) {
        var inputs = document.getElementsByTagName('input');
        for (var n = 0; n < inputs.length; n++) {
            var input = inputs[n];
            var placeholder = input.placeholder ? input.placeholder : input.getAttribute('placeholder');
            if (!placeholder)
                continue;
            if (!input.value || (input.value == placeholder)) {
//                                alert('请填写'+placeholder);
                $("#logon-error").css("display", "inline");
                $("#logon-error").text('请填写' + placeholder);
                e = e || window.event;
                e.preventDefault && e.preventDefault();
                e.returnValue = false;
                break;
            }
        }
    };


}
$("#Password").focus(function () {
    $("#Password").val("");
    $("#Password").attr('type', 'password');

});
$("#Password").blur(function () {
    var i = $("#Password").val();
    if (i == null || i == '' || i == "密码") {
        $("#Password").attr('type', 'text');
    }

});  

$("#Newpassword").focus(function () {
    $("#Newpassword").val("");
    $("#Newpassword").attr('type', 'password');

});
$("#Newpassword").blur(function () {
    var i = $("#Newpassword").val();
    if (i == null || i == '' || i == "新密码") {
        $("#Newpassword").attr('type', 'text');
    }

});

$("#Repassword").focus(function () {
    $("#Repassword").val("");
    $("#Repassword").attr('type', 'password');

});
$("#Repassword").blur(function () {
    var i = $("#Repassword").val();
    if (i == null || i == '' || i == "再次输入密码") {
        $("#Repassword").attr('type', 'text');
    }

});