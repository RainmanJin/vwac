<%@ Page Title="" Language="C#" MasterPageFile="~/mw/wap/Wap.Master" AutoEventWireup="true" CodeBehind="infolist.aspx.cs" Inherits="PortalWeb.mw.wap.Info.infolist" %>
<asp:Content ID="Content1" ContentPlaceHolderID="head" runat="server">
</asp:Content>
<asp:Content ID="Content2" ContentPlaceHolderID="ContentPlaceHolder1" runat="server">
    <div class="header"><h2>新闻中心</h2><img src="<%=MW.BasePage.GetWebPath %>/mw/wap/images/header.jpg"/></div>
 
  <ul class="news info-list">
      
  </ul>
    <script type="text/javascript">
        $(document).ready(function () {
            var range = 50; //距下边界长度/单位px  
            var maxnum = 20; //设置加载最多次数  
            var num = 2;
            var totalheight = 0;

            $(window).scroll(function () {
                var srollPos = $(window).scrollTop(); //滚动条距顶部距离(页面超出窗口的高度)   
                totalheight = parseFloat($(window).height()) + parseFloat(srollPos);
                if (($(document).height() - range) <= totalheight && num != maxnum) {
                    loadpage(num);
                    num++;
                }
            });
        });
        function loadpage(p) {
            $.post(document.location.href, { "page": p, "cmd": "list" }, function (result) {
                maxnum = parseInt(result.total);
                var data = result.data;
                var _html = "";
                $.each(data, function (j, o) {
                    var t = formatDate(o["CreateTime"], 'yyyy-MM-dd');
                    var video = "";//o["fj"]==""?"":" class='video'";
                    _html += '<li ' + video + '><a href="<%=MW.BasePage.GetWebPath %>/mw/wap/info/infoview.aspx?id=' + o["Id"] + '"><img src="' + o["FilePath"] + '"><span><p>' + o["Title"] + '</p><time>' + t + '</time></span></a></li>';
             });

             $(".info-list").append(_html);
         }, "json");
     }
     loadpage(1);
     function date(_date) { return new Date(parseInt(_date.replace("/Date(", "").replace(")/", ""))) }
     function formatDate(v, format) {
         var d = this.date(v);
         if (format == null) {
             return d.getFullYear() + "/" + (d.getMonth() + 1) + "/" + d.getDate() + " " + d.getHours() + ":" + d.getMinutes() + ":" + d.getSeconds();
         } else { return format.replace("yyyy", d.getFullYear()).replace("MM", (d.getMonth() + 1)).replace("dd", d.getDate()).replace("hh", d.getHours()); }
     }
    </script>   
</asp:Content>
