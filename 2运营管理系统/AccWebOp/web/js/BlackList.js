/* 
 * @author：zhongziqi
 * @datetime：2018-01-02
 */

//粘贴时取出空格 Ie下生效
function removeBlankSpace(form, id) {
    //window.clipboardData.setData("Text", "Hello");
//                alert(navigator.userAgent);
    if (isIE())
    {
        var clipData = window.clipboardData;
        var value = clipData.getData("text");
//                    alert("value:"+value+"||");
        value = value.toString().replace(/\s/g, "");
//                    alert("value:"+value+"||");
//                    var flag = window.clipboardData.setData("text", value);
//                    window.clipboardData.getData("text");
//                    alert(flag);
        setTimeout(function () {
            var frm = document.forms[form];
            var text = frm.getElementsByTagName("input")[id];
            text.value = value;
        }, 10);
    }
//                alert(window.clipboardData.getData("text"));
//                var value = document.getElementById(id).value;
//                alert("value：" + value);
//                if (value.length > 0) {
//                    alert("有值：" + value.length);
//                } else {
//                    alert("没值");
//                }
}
function isIE() { //ie?
    if (!!window.ActiveXObject || "ActiveXObject" in window) {
//                    alert("ie");
        return true;
    } else {
//                     alert("非ie:");
        return false;
    }
}