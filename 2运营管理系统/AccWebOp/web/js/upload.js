//导入上传 by lindaquan in 20170706
function openUpload(formName){
    var submitFrom = document.getElementById(formName);
    var event = event||window.event;
    //获取页面的高度和宽度
    var sWidth=document.documentElement.scrollWidth;
    var sHeight=document.documentElement.scrollHeight;  //获取页面的高度
    
    //弹出框
    var uploadDiv = document.createElement("div");
    uploadDiv.id = "uploadDiv";
    
    //弹出标题
    var span = document.createElement("span");
    span.innerHTML = "　";
    span.id = "uploadSpan";
    uploadDiv.appendChild(span);
    //上传form
    var uploadForm = document.createElement("div");
    uploadForm.id = "uploadForm";
    uploadForm.className = "upload-form";
    uploadForm.innerHTML = "<input type='file' id='file' name='file'/><input type='button' id='upload' name='upload' value='导入'/>";
    
    uploadDiv.appendChild(uploadForm);
    uploadDiv.style.left = event.x+"px";
    uploadDiv.style.top = event.y+"px";
    submitFrom.appendChild(uploadDiv);
    submitFrom.enctype = "multipart/form-data";
    document.getElementById("upload").onclick = function(){upload(formName);};
    
    //底层
    var uploadBg = document.createElement("div");
    uploadBg.id = "uploadBg";
    uploadBg.style.height = sHeight+"px";
    uploadBg.style.width = sWidth+"px";
    uploadBg.onclick = function(){
        document.body.removeChild(uploadBg);
        submitFrom.removeChild(uploadDiv);
        submitFrom.removeAttribute("enctype");
    };
    document.body.appendChild(uploadBg);
}

function upload(formName){
    setCommand(formName, "import");
    setSubmitForm(formName);
    queryAction(formName);
}

