<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>



<html> 
    <head><title>物理逻辑卡号导入</title> 
        <style type="text/css">@import url(js/plupload/jquery.plupload.queue/css/jquery.plupload.queue.css);</style>
        <script src="js/jquery-1.6.2.min.js" type="text/javascript"></script> 
        <script type="text/javascript" src="js/plupload/plupload.full.js"></script>
        <script type="text/javascript" src="js/plupload/jquery.plupload.queue/jquery.plupload.queue.js"></script>
        <script type="text/javascript" src="js/plupload/i18n/cn.js"></script>
        <script type="text/javascript">
            // Convert divs to queue widgets when the DOM is ready
            $(function () {
                $("#uploader").pluploadQueue({
                    // General settings
                    runtimes: 'gears,flash,silverlight,browserplus,html5,html4',
                    url: 'phyLogicListUpload?command=import&&ModuleID=010510',
                    max_file_size: '10mb',
                    unique_names: true,
                    //			chunk_size: '2mb',
                    // Specify what files to browse for
                    //filters : [
                    //	{title : "xls, xlsx文档", extensions : "xls,xlsx"}
                    //],

                    // Flash settings
                    flash_swf_url: 'js/plupload/plupload.flash.swf',
                    // Silverlight settings
                    silverlight_xap_url: 'js/plupload/plupload.silverlight.xap',
                    init: {
                        FileUploaded: function (up, file, info) {
                            //                                                        alert("info====>"+info,"up====>"+up);
                            //文件上传完毕触发
                            var response = $.parseJSON(info.response);
                            //                                                    alert("status====>"+response.status);
                            if (response.status) {
                                //                                                        alert("come into response");
                                $('#uploadMsg').append(response.fileName + ":" + response.returnMsg + "!<br/> ");
                                //                                                        alert(response.fileName+":"+response.returnMsg);
                                //$('#f1').append('<input type="hidden" name="fileUrl" value="'+response.fileUrl+'"/>');
                                //$('#f1').append('<input type="hidden" name="fileName" value="'+file.name+'"/><br/>');
                            } else {
                                //                                                        alert("come into else");
                                $('#uploadMsg').append(response.fileName + ":" + response.returnMsg + "!<br/> ");
                                //                                                        alert(response.fileName+":"+response.returnMsg);
                            }
                        },
                        Error: function (up, err) {
                            //当服务器端返回错误信息时error方法显示错误提示，


                            //服务器端返回错误信息会被plupload转换为-200 http错误,
                            //所以只能做==-200比较。更好的提示，需要修改插件源代码。


                            if (err.code == -200) {
                                alert("文件格式错误，请检查后重新上传!");
                            }
                            if (err.code == -602) {
                                alert("文件已存在!");
                            }
                        }
                    }
                });


                $('form').submit(function (e) {
                    var uploader = $('#uploader').pluploadQueue();
                    if (uploader.files.length > 0) {
                        // When all files are uploaded submit form
                        uploader.bind('StateChanged', function () {
                            if (uploader.files.length === (uploader.total.uploaded + uploader.total.failed)) {
                                $('form')[0].submit();
                            }
                        });
                        uploader.start();
                    } else {
                        alert('请先上传数据文件.');
                    }
                    return false;
                });
            });


        </script>

    </head>

    <body>
        <div>
            <div style="width: 750px; margin: 0px auto">
                <!--<form id="formId" action="Submit.action" method="post">-->
                <form id="formId"  method="post"
                      action="phyLogicListUpload?command=import&&ModuleID=010510">
                    <div id="uploader">
                        <p>您的浏览器未安装 Flash, Silverlight, Gears, BrowserPlus 或者支持 HTML5 .</p>
                    </div>
                    <font  >
                    返回信息:
                    </font>
                    <div style="width:734;height:60;margin-left:9px;overflow:scroll;border:1px solid lightgray;background-color:F5F5F5;" >
                        <span id="uploadMsg" style="font-weight:bold;color:Red;" >

                        </span>
                    </div>
                    <!--<input type="submit" value="完成"/>-->
                </form>
            </div>
        </div>
    </body>

</html>
