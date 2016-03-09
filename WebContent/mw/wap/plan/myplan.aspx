<%@ Page Title="" Language="C#" MasterPageFile="~/mw/wap/Wap.Master" AutoEventWireup="true" CodeBehind="myplan.aspx.cs" Inherits="PortalWeb.mw.wap.plan.myplan" %>
<asp:Content ID="Content1" ContentPlaceHolderID="head" runat="server">
</asp:Content>
<asp:Content ID="Content2" ContentPlaceHolderID="ContentPlaceHolder1" runat="server">
     <div class="header"><div class="menu_control menu_control_h"><a href="javascript:;"></a></div><h2>我的培训课程</h2><img src="<%=MW.BasePage.GetWebPath %>/mw/wap/images/header.jpg"/></div>
    <div class="page">
     <div class="main">
         <form id="Form1" runat="server">
    
    <div class="search" style=" display:none">
        <div class="searchbox">
    <div>所&nbsp;&nbsp;&nbsp;&nbsp;属：
         <asp:DropDownList ID="dropTypeId" runat="Server"></asp:DropDownList>
    </div>
   <div>     <button class="button btnsubmit" type="button">
					<img src="<%=MW.BasePage.GetWebPath %>/mw/wap/images/icon_search.png"/>&nbsp;检索
</button>
<button class="close">关闭</button>
        
        </div>
     </div>   
 </div>
             </form>
  </div>
  <ul class="myplan">
      <ctl:JRepeater runat="server" ID="rpt_List">
          <ItemTemplate>
              <li><a href="<%=MW.BasePage.GetWebPath %>/mw/wap/plan/planview.aspx?type=1&id=<%#Eval("ID") %>"><p> <%#Eval("PlanName") %></p><i>培训讲师：<%# Eval("Teacher")%></i><i>培训日期：<%#Eval("BeginTime") %></i><i>培训天数：<%#Eval("days") %></i></a></li>
          </ItemTemplate>
      </ctl:JRepeater>
  </ul>
 <div class="clear"></div>
        
  </div>
    <script>
        $(document).ready(function () {
            $(".menu_control").click(function () {
                $(".search").show();
            });
            $(".close").click(function () {
                $(".search").hide();
            });
            $(".btnsubmit").click(function () {

                window.location.href = "myplan.aspx?typeid=" + $("#<%=dropTypeId.ClientID%>").val();
                return false;
            });
        });
    </script>
</asp:Content>
