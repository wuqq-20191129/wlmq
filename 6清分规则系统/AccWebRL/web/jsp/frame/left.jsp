<!DOCTYPE html>
<%@page import="com.goldsign.login.util.ModuleUtil"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.goldsign.login.vo.User"%>
<%@page import="com.goldsign.login.vo.ModuleDistrVo"%>
<%@page import="java.util.List"%>
<%@ page import="java.util.Vector,java.util.Hashtable"%>
<%@ page language="java" contentType="text/html;charset=UTF-8" %>

<%
    List<ModuleDistrVo> moduleDistrVoList = (List<ModuleDistrVo>)session.getAttribute("ModulePrilivedges");
    String topMenuId = (String) request.getParameter("TopMenuId");
    if(topMenuId == null){
        int k=0;
        for(int i=0; i<moduleDistrVoList.size(); i++){
            ModuleDistrVo vo = moduleDistrVoList.get(i);
            String topMId = (String)vo.getTopMenuId();
            if(topMId.equals("0") && k==0){
                topMenuId = (String)vo.getModuleID();
                k++;
            }
        }
    }
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <!--modify by zhongzq 20180608-->
        <meta http-equiv="X-UA-Compatible" content="IE=9,Chrome=1" />
        <link href="././css/frame/style.css" rel="stylesheet" type="text/css"/>
        <script src="././js/jquery-1.12.4.min.js" type="text/javascript"></script>
        <!--[if lt IE 9]>
            <script src="././js/html5shiv.min.js"></script>
            <script src="././js/respond.min.js"></script>
         <![endif]-->
    </head>

    <body>
        <div class="leftMain-content">
            <ul>
                <% for(int i=0; i<moduleDistrVoList.size(); i++){
                    ModuleDistrVo vo = moduleDistrVoList.get(i);
                    String topMId = (String)vo.getTopMenuId();
                    String parentMId = (String)vo.getParentId();
                    String cMid = (String)vo.getModuleID();
                    if(!topMId.equals("0") && topMId.equals(parentMId) && topMId.equals(topMenuId)){
                %>
                <li>
                    <div class="li-header">
                        <a href="#" title="<%=vo.getMenuUrl()%>&ModuleID=<%=vo.getModuleID()%>">
                            <div class="li-header-c"><%=vo.getMenuName()%>
                            <input type="hidden" name="clickFlag" id="<%=vo.getModuleID()%>" value="0"/>
                            <input type="hidden" name="clickName" id="clickName" value="<%=vo.getModuleID()%>"/>
                            <input type="hidden" name="LastClickID" id="LastClickID" value=""/></div>
                        </a>
                            <% if(vo.getMenuUrl()!=null && !vo.getMenuUrl().trim().equals("")){ %>
                        <input type="hidden" name="hurl" id="hurl" value="<%=vo.getMenuUrl()%>&ModuleID=<%=vo.getModuleID()%>"/>
                        <% } %>
                    </div>
                    <div class="clr"></div>
                    <div class="li-content">
                        <ul>
                            <% for(int j=0; j<moduleDistrVoList.size(); j++){
                                ModuleDistrVo voj = moduleDistrVoList.get(j);
                                String mid = (String)voj.getParentId();
                                if(mid.equals(cMid)){
                            %>
                            <li class="li-item">
                                <a href="#" title="<%=voj.getMenuUrl()%>&ModuleID=<%=voj.getModuleID()%>">
                                    <div class="li-item-arrow"></div>
                                    <div class="li-item-c"><%=voj.getMenuName()%></div>
                                    <div class="arrow-left"></div>
                                    <div class="clr"><input type="hidden" name="url" id="url" value="<%=voj.getMenuUrl()%>&ModuleID=<%=voj.getModuleID()%>"/></div>
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
        $(document).ready(function(){
            //三级菜单展示
            $(".li-header").click(function(){
                var hurl=$(this).find("#hurl").val();
                var id = $(this).find("#clickName").val();
                var clickFlag =$(this).find("#"+id).val();
                var LastClickID = document.getElementById("LastClickID").value;
                $(".li-content").css("display","none");
                
                //将前一次点击的标志改为0；
                if(LastClickID!=undefined && LastClickID!=""){
                    document.getElementById(LastClickID).value = 0;
                }
                if(hurl!=undefined && hurl!=""){
                    window.parent.frames['ifRight'].location.href=hurl;
                    //菜单样式变化
                    $(".li-item-select").addClass("li-item");
                    $(".li-item-select").removeClass("li-item-select");
                    $(".li-header-select").removeClass("li-header-select");
                    $(this).addClass("li-header-select");
                }else if(clickFlag == 1){
                    document.getElementById(id).value=0;
                }else{
                    document.getElementById("LastClickID").value=id;
                    document.getElementById(id).value=1;
                    $(this).siblings("div.li-content").css("display","block");
                }
            });

            $(".li-content ul li").click(function(){
                //跨域打开右边iframe页面
                var url=$(this).find("#url").val();
                window.parent.frames['ifRight'].location.href=url;
                //菜单样式变化
                $(".li-item-select").addClass("li-item");
                $(".li-item-select").removeClass("li-item-select");
                $(".li-header-select").removeClass("li-header-select");
                $(this).addClass("li-item-select");

                $(".li-item-arrow-select").addClass("li-item-arrow");
                $(".li-item-arrow-select").removeClass("li-item-arrow-select");
                $(this).children("div.li-item-arrow").addClass("li-item-arrow-select");
                $(this).children("div.arrow-left").css("display","block");
            });

            window.history.forward(1);// 原理就是产生一个“前进”的动作，以抵消后退功能
        });
    </script>
    </body>
</html>