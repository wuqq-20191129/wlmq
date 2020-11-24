<!DOCTYPE html>
<%@page import="com.goldsign.login.vo.ModuleDistrVo" %>
<%@page import="java.util.List" %>
<%@ page language="java" contentType="text/html;charset=UTF-8" %>

<%
    List<ModuleDistrVo> moduleDistrVoList = (List<ModuleDistrVo>) session.getAttribute("ModulePrilivedges");
    String topMenuId = (String) request.getParameter("TopMenuId");
    if (topMenuId == null) {
        int k = 0;
        for (int i = 0; i < moduleDistrVoList.size(); i++) {
            ModuleDistrVo vo = moduleDistrVoList.get(i);
            String topMId = (String) vo.getTopMenuId();
            if (topMId.equals("0") && k == 0) {
                topMenuId = (String) vo.getModuleID();
                k++;
            }
        }

    }
%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <!--modify by zhongzq 20180608-->
    <meta http-equiv="X-UA-Compatible" content="IE=9,Chrome=1"/>
    <link href="././css/frame/style.css" rel="stylesheet" type="text/css"/>
    <script language="javascript" type="text/javascript" charset="utf-8" src="js/sockjs.js"></script>
    <!--add by zhongzq 自动刷新菜单颜色-->
    <script language="JavaScript">
        //add by zhongzq 使用websocket更新 20180816
        var websocket = null;
        //判断当前浏览器是否支持WebSocket
        var curWwwPath = window.document.location.href;
        //获取主机地址之后的目录，如： uimcardprj/share/meun.jsp
        var pathName = window.document.location.pathname;
        var pos = curWwwPath.indexOf(pathName);
        //获取主机地址，如： localhost:8080
        var localhostPath = curWwwPath.substring(7, pos);
        //获取带"/"的项目名，如：/mn
        var projectName = pathName.substring(0,
            pathName.substr(1).indexOf('/') + 1);
        // alert(localhostPath + projectName );
        if ('WebSocket' in window) {
            websocket = new WebSocket("ws://" + localhostPath + projectName + "/updateWebSocketHandler");
        }
        else {
            //兼容ie9
            websocket = new SockJS("http://" + localhostPath + projectName + "/updateWebSocketHandler/socketJs");
            // alert('当前浏览器 Not support websocket');
        }

        //连接发生错误的回调方法
        websocket.onerror = function () {
            alert("WebSocket连接发生错误");
        }

        //连接成功建立的回调方法
        websocket.onopen = function () {
            // alert("WebSocket连接成功");
            send("update");
        }

        //接收到消息的回调方法
        websocket.onmessage = function (event) {
            // alert(event.data);
            // if("update"==event.data){
            var json = eval("(" + event.data + ")");
            updateMenuStatu(json);
            // autoRefreshMenuStatu();
            // }
        }

        //连接关闭的回调方法
        websocket.onclose = function () {
            alert("WebSocket连接关闭，请重新登录");
            // alert(window.navigator.userAgent);
        }

        //监听窗口关闭事件，当窗口关闭时，主动去关闭websocket连接，防止连接还没断开就关闭窗口，server端会抛异常。
        window.onbeforeunload = function () {
            closeWebSocket();
        }


        //关闭WebSocket连接
        function closeWebSocket() {
            websocket.close();
        }

        //发送消息
        function send(message) {
            websocket.send(message);
        }

        function updateMenuStatu(json) {
            var menuStatus = json.menuStatus;
            // alert(menuStatus.length);
            for (var i = 1; i < menuStatus.length; i++) {
                // alert(menuStatus[i].MODULE_ID);
                // alert(menuStatus[i].MODULE_ID.length);
                // alert(menuStatus[i].PARENT_ID);
                // alert( menuStatus[i].PARENT_ID.length);
                // alert(menuStatus[i].TOP_MENU_ID);
                // alert(menuStatus[i].TOP_MENU_ID.length);
                // alert(menuStatus[i].STATUS);
                // alert(menuStatus[i].STATUS.length);
                //表头
                // alert(menuStatus[i].MODULE_ID+"||"+i);
                if (menuStatus[i].PARENT_ID.length == 2) {
                    //二级菜单
                    var headId = "head_" + menuStatus[i].MODULE_ID;
                    var head = document.getElementById(headId);
                    // alert(head);
                    if (head == null) {
                        continue;
                    }
                    if (menuStatus[i].STATUS == '1') {
                        head.className = 'li-header-red';
                    } else if (menuStatus[i].STATUS == '2') {//未完成
                        head.className = 'li-header-gray';
                    } else if (menuStatus[i].STATUS == '3') {
                        head.className = 'li-header-orange';
                    } else {
                        head.className = 'li-header-c';
                    }

                } else if (menuStatus[i].PARENT_ID.length == 4) {
                    //三级菜单
                    var itemSymbolId = "item_symbol_" + menuStatus[i].MODULE_ID;
                    var itemId = "item_" + menuStatus[i].MODULE_ID;
                    var itemSymbol = document.getElementById(itemSymbolId);
                    var item = document.getElementById(itemId);
                    // alert(itemId+"||"+menuStatus[i].STATUS);
                    //有些权限不足没有对应菜单栏
                    if (itemSymbol == null || item == null) {
                        continue;
                    }
                    if (menuStatus[i].STATUS == '1') {
                        itemSymbol.className = "li-item-arrow_red";
                        item.className = "li-item-red";
                    } else if (menuStatus[i].STATUS == '2') {//未完成
                        itemSymbol.className = "li-item-arrow_gray";
                        item.className = "li-item-gray";
                    } else if (menuStatus[i].STATUS == '3') {
                        itemSymbol.className = "li-item-arrow_orange";
                        item.className = "li-item-orange";
                    } else {
                        itemSymbol.className = "li-item-arrow";
                        item.className = "li-item-c";
                    }
                }
                // break;
                // if(menuStatus[i].PARENT_ID)
            }
            // alert(menuStatus);
        }

        //弃用该方法 由于ie9 加入socketJs后没有权限执行，改用websocket直接截取Json zhongzq 20180817
        function autoRefreshMenuStatu() {
            //定期执行
            // alert(window.location.hash);
            var curWwwPath = window.document.location.href;
            //获取主机地址之后的目录，如： uimcardprj/share/meun.jsp
            var pathName = window.document.location.pathname;
            var pos = curWwwPath.indexOf(pathName);
            //获取主机地址，如： http://localhost:8080
            var localhostPath = curWwwPath.substring(0, pos);
            //获取带"/"的项目名，如：/mn
            var projectName = pathName.substring(0,
                pathName.substr(1).indexOf('/') + 1);
            var url = localhostPath + projectName + '/menuStatus';
            //alert(localhostPaht + projectName);
            // alert(url);
            // code for IE7+, Firefox, Chrome, Opera, Safari
            var xhr = new XMLHttpRequest();
            xhr.onload = function () {
                // 请求完成
                if (this.status === 200) {
                    // 返回200
                    var json = eval("(" + xhr.responseText + ")");
                    updateMenuStatu(json);
                }
                else {
                    // alert("更新菜单颜色失败失败");
                }
            };
            xhr.open('POST', url, true);
            xhr.setRequestHeader("Content-Type",
                "application/x-www-form-urlencoded;charset=UTF-8");
            // 发送ajax请求
            xhr.send();
            setTimeout("autoRefreshMenuStatu()", 30000);
        }

    </script>
    <script src="././js/jquery-1.12.4.min.js" type="text/javascript"></script>
    <!--[if lt IE 9]>
    <script src="././js/html5shiv.min.js"></script>
    <script src="././js/respond.min.js"></script>
    <![endif]-->
</head>

<body>
<div class="leftMain-content">
    <ul>
        <% for (int i = 0; i < moduleDistrVoList.size(); i++) {
            ModuleDistrVo vo = moduleDistrVoList.get(i);
            String topMId = (String) vo.getTopMenuId();
            String parentMId = (String) vo.getParentId();
            String cMid = (String) vo.getModuleID();
            if (!topMId.equals("0") && topMId.equals(parentMId) && topMId.equals(topMenuId)) {
        %>
        <li>
            <div class="li-header">
                <a href="#" title="<%=vo.getMenuUrl()%>&ModuleID=<%=vo.getModuleID()%>">
                    <div id="head_<%=vo.getModuleID()%>" class="li-header-c"><%=vo.getMenuName()%>
                        <input type="hidden" name="clickFlag" id="<%=vo.getModuleID()%>" value="0"/>
                        <input type="hidden" name="clickName" id="clickName" value="<%=vo.getModuleID()%>"/>
                        <input type="hidden" name="LastClickID" id="LastClickID" value=""/></div>
                </a>
                <% if (vo.getMenuUrl() != null && !vo.getMenuUrl().trim().equals("")) { %>
                <input type="hidden" name="hurl" id="hurl" value="<%=vo.getMenuUrl()%>&ModuleID=<%=vo.getModuleID()%>"/>
                <% } %>
            </div>
            <div class="clr"></div>
            <div class="li-content">
                <ul>
                    <% for (int j = 0; j < moduleDistrVoList.size(); j++) {
                        ModuleDistrVo voj = moduleDistrVoList.get(j);
                        String mid = (String) voj.getParentId();
                        if (mid.equals(cMid)) {
                    %>
                    <li class="li-item">
                        <a href="#" title="<%=voj.getMenuUrl()%>&ModuleID=<%=voj.getModuleID()%>">
                            <div id="item_symbol_<%=voj.getModuleID()%>" class="li-item-arrow"></div>
                            <div id="item_<%=voj.getModuleID()%>" class="li-item-c"><%=voj.getMenuName()%>
                            </div>
                            <div class="arrow-left"></div>
                            <div class="clr"><input type="hidden" name="url" id="url"
                                                    value="<%=voj.getMenuUrl()%>&ModuleID=<%=voj.getModuleID()%>"/>
                            </div>
                        </a>
                    </li>
                    <%
                            }
                        }
                    %>
                </ul>
            </div>
        </li>
        <%
                }
            }
        %>
    </ul>
</div>

<script language="JavaScript">
    $(document).ready(function () {
        //三级菜单展示
        $(".li-header").click(function () {
            var hurl = $(this).find("#hurl").val();
            var id = $(this).find("#clickName").val();
            var clickFlag = $(this).find("#" + id).val();
            var LastClickID = document.getElementById("LastClickID").value;
            $(".li-content").css("display", "none");

            //将前一次点击的标志改为0；
            if (LastClickID != undefined && LastClickID != "") {
                document.getElementById(LastClickID).value = 0;
            }
            if (hurl != undefined && hurl != "") {
                window.parent.frames['ifRight'].location.href = hurl;
                //菜单样式变化
                $(".li-item-select").addClass("li-item");
                $(".li-item-select").removeClass("li-item-select");
                $(".li-header-select").removeClass("li-header-select");
                $(this).addClass("li-header-select");
            } else if (clickFlag == 1) {
                document.getElementById(id).value = 0;
            } else {
                document.getElementById("LastClickID").value = id;
                document.getElementById(id).value = 1;
                $(this).siblings("div.li-content").css("display", "block");
            }
        });

        $(".li-content ul li").click(function () {
            //跨域打开右边iframe页面
            var url = $(this).find("#url").val();
            if (url.indexOf("http") > -1) {
                //add by zhongzq 20180614 过滤无用参数
                if (url.indexOf("&ModuleID") > -1) {
                    url = url.substring(0, url.indexOf("&ModuleID"));
                }
                window.open(url);

            } else {
                window.parent.frames['ifRight'].location.href = url;
            }
            //菜单样式变化
            $(".li-item-select").addClass("li-item");
            $(".li-item-select").removeClass("li-item-select");
            $(".li-header-select").removeClass("li-header-select");
            $(this).addClass("li-item-select");

            $(".li-item-arrow-select").addClass("li-item-arrow");
            $(".li-item-arrow-select").removeClass("li-item-arrow-select");
            $(this).children("div.li-item-arrow").addClass("li-item-arrow-select");
            $(this).children("div.arrow-left").css("display", "block");
        });

        window.history.forward(1);// 原理就是产生一个“前进”的动作，以抵消后退功能
    });
</script>
</body>
</html>