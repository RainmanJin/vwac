<%@ Page Title="" Language="C#" MasterPageFile="~/mw/wap/Wap.Master" AutoEventWireup="true" CodeBehind="searchplanlist.aspx.cs" Inherits="PortalWeb.mw.wap.plan.searchplanlist" %>
<asp:Content ID="Content1" ContentPlaceHolderID="head" runat="server">
</asp:Content>
<asp:Content ID="Content2" ContentPlaceHolderID="ContentPlaceHolder1" runat="server">
    <div class="header"><div class="menu_control menu_control_h"><a href="#"></a></div><h2><%=typename %>排期查询</h2><img src="<%=MW.BasePage.GetWebPath %>/mw/wap/images/header.jpg"/></div>
    <div class="page2">
    <div class="main">
         <form id="Form1" runat="server">
    
    <div class="search" style=" display:none">
        <div class="searchbox">
    <div>领&nbsp;&nbsp;&nbsp;&nbsp;域：
         <asp:DropDownList ID="dropTypeId" runat="Server"></asp:DropDownList>
    </div>
   <div>     <button class="button btnsubmit" type="button">
					<img src="<%=MW.BasePage.GetWebPath %>/mw/wap/images/icon_search.png" />&nbsp;检索
</button>
<button class="close">关闭</button>
        
        </div>
     </div>   
 </div>
             </form>
  </div>
  
  <ul class="course2">
      <ctl:JRepeater runat="server" ID="rpt_List">
          <ItemTemplate>
              <li><a href='<%=MW.BasePage.GetWebPath %>/mw/wap/plan/planview.aspx?type=0&id=<%# Eval("id") %>'>
              <%# Eval("PlanName") %>
              <span><%# Eval("BeginTime") %></span></a></li>
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
                var url = "<%=MW.BasePage.GetWebPath %>/mw/wap/plan/searchplanlist.aspx?typeid=" + $("#<%=dropTypeId.ClientID%>").val();
                //alert(url);
                window.location.href = url;
                return false;
            });
        });
    </script>
</asp:Content>
