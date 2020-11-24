<!DOCTYPE html>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="com.goldsign.login.vo.User"%>
<%@page import="com.goldsign.login.vo.ModuleDistrVo"%>

<%@page language="java" contentType="text/html;charset=UTF-8" %>


<%
    //取登录信息，得到用户信息	
    User user =  (User) session.getAttribute("User");
    List<ModuleDistrVo> moduleDistrVoList = (List<ModuleDistrVo>)session.getAttribute("ModulePrilivedges");
    String strDays = (String) session.getAttribute("ValidDays");
    int iDays = new Integer(strDays).intValue();
%>

<html>

    <head>

        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <!--modify by zhongzq 20180608-->
        <meta http-equiv="X-UA-Compatible" content="IE=9,Chrome=1" />
        <title>::乌市轨道交通自动售检票系统--密钥卡管理系统::</title>
        <link href="././css/frame/style.css" rel="stylesheet" type="text/css"/>
        <script src="././js/jquery-1.12.4.min.js" type="text/javascript"></script>
        <!--[if lt IE 9]>
            <script src="././js/html5shiv.min.js"></script>
            <script src="././js/respond.min.js"></script>
         <![endif]-->
        <script type="text/javascript">
            function showTime(){
                var nowDateTime;
                nowDateTime=new Date();
                var year=nowDateTime.getFullYear();
                var month=nowDateTime.getMonth()+1;
                var day=nowDateTime.getDate();
                var hour=nowDateTime.getHours();
                var min=nowDateTime.getMinutes();
                month  = month<10  ? "0"+month : month;
                day  = day <10  ? "0"+day : day;
                hour  = hour<10  ? "0"+hour : hour;
                min = min<10 ? "0"+min : min;
                document.getElementById("curDateTime").innerText=year+"年"+month+"月"+day+"日"+" "+hour+":"+min;
            }

            setInterval("showTime()",1000);

        </script>
    </head>

    <body>
        <div class="header header-bg">
            <div class="header-logo"><img src="././images/logo/logo.png"/>
	        <div class="header-right-fa-a" id="curDateTime" ></div>
	    </div>
            <div class="header-title"><img src="././images/logo/yyzxxt.png" width="244" height="36"/></div>
            <div class="header-right">
              <div class="header-rigth-fa"> 
                  <a href="#" target="_self" class="header-right-fa-a" onclick="join_favorite();">收藏本站</a> <i class="header-feige">&nbsp;|</i> 
                  <a href="#" target="_self" onclick="myrefresh()" class="header-right-fa-a">刷新</a> <i class="header-feige">&nbsp;|</i> 
                  <a href="#" onclick="password_edit()" target="_self" class="header-right-fa-a">更改密码</a> <i class="header-feige">&nbsp;|</i> 
                  <a href="logout" target="_top" class="header-right-fa-a">退出</a> </div>
              <div class="header-right-wel">
                <div class="header-right-wel-img"><img src="././images/logo/touxiang.png"/></div>
                <!--<div class="header-right-wel-i">-<%=user.getUsername()%></div>-->
                <%
                    boolean showIDays = false;        
                    if (iDays <= 10 && iDays >= 0) {
                        showIDays = true;   
                    }
                    String userName = user.getUsername();
                    //页面只能显示12个中文姓名，有"密码有效剩:1天"时，只能显示5个中文姓名
                    if ((showIDays && userName.length()>5) || (userName.length()>12)) {
                          String shortName = "";
                        if (showIDays) {
                            shortName = userName.substring(0, 4)+"···";
                        } else {
                            shortName = userName.substring(0, 11)+"···";
                        }
                %>
                <div class="header-right-wel-i" title=<%=user.getUsername()%>>-欢迎您！<%=shortName%></div>
                <%}%>
                <% if (!((showIDays && userName.length()>5) || (userName.length()>12))) {%>
                <div class="header-right-wel-i">-欢迎您！<%=user.getUsername()%></div>
                <%}%>
                <div class="header-right-wel-ir">
                    <!-- 判断是否到提醒日期 -->
                    <%
                        if (iDays <= 10 && iDays >= 0) {
                    %>
                    <font color="red"> &nbsp;密码有效剩:<%=strDays%>天</font>
                    <%}%>
                </div>
              </div>
            </div>
            <div class="clr"></div>
        </div>
        
        <div class="leftNavigation">
          <div class="leftNavigation-header">
            <div class="leftNavigation-header-img"></div>
            <div  class="leftNavigation-header-c">功能菜单</div>
          </div>
          <div class="clr"></div>
        </div>
        
        <div class="rightNavigation">
            <div class="rightNavigation-header">
              <div class="rightNavigation-header-fi"></div>
              <div class="rightNavigation-header-nav">
                <ul>
                    <%for(int i=0; i<moduleDistrVoList.size(); i++){
                        ModuleDistrVo vo = moduleDistrVoList.get(i);

                        String topMId = (String)vo.getTopMenuId();
                        if(topMId.equals("0")){
                            
                    %>
                    <li class="rightNavigation-header-nav-item"><a style="display: block;" target="ifLeft" href="leftMenu?TopMenuId=<%=vo.getModuleID()%>"><%=vo.getMenuName()%></a></li>
                    <%
                        }
                    }%>
                </ul>
              </div>
            </div>
            <div class="clr"></div>
        </div>
                
    <script language="JavaScript">
        $(document).ready(function(){
            $("li:first").addClass("item-select");
            $("li").click(function(){
                $(this).siblings("li").removeClass("item-select");
                $(this).addClass("item-select");
            });

            window.history.forward(1);// 原理就是产生一个“前进”的动作，以抵消后退功能
        });
        
        function myrefresh(){
            parent.location.reload();
        }
        function join_favorite(){  
            var url = window.location;
            var title = document.title;
            var ua = navigator.userAgent.toLowerCase();
            if (ua.indexOf("360se") > -1) {
                alert("由于360浏览器功能限制，请按 Ctrl+D 手动收藏！");
            }
            else if (ua.indexOf("msie 8") > -1) {
                window.external.AddToFavoritesBar(url, title); //IE8
            }
            else if (document.all) {
                try{
                    window.external.addFavorite(url, title);
                }catch(e){
                    alert('您的浏览器不支持,请按 Ctrl+D 手动收藏!');
                }
            }
            else if (window.sidebar) {
                window.sidebar.addPanel(title, url, "");
            }
            else {
                alert('您的浏览器不支持,请按 Ctrl+D 手动收藏!');
            }
        }
        function password_edit(){
            window.showModalDialog("passwordEdit?ModuleID=020401",null,"dialogWidth=400px;dialogHeight=300px");
        }
    </script>  
   
    </body>
</html>

