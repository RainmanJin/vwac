<%@ Page Title="" Language="C#" MasterPageFile="~/mw/wap/Wap.Master" AutoEventWireup="true" CodeBehind="wkview.aspx.cs" Inherits="PortalWeb.mw.wap.Info.wkview" %>
<asp:Content ID="Content2" ContentPlaceHolderID="head" runat="server">
      <script>
          var url = '<%=url%>';
    </script>
</asp:Content>
<asp:Content ID="Content3" ContentPlaceHolderID="ContentPlaceHolder1" runat="server">
  <%--  <div class="header"><img src="<%=MW.BasePage.GetWebPath %>/mw/wap/images/header.jpg"/></div>--%>
    <div class="page">
        <div class="box-heading">
            <h1><%=title %></h1>
            <p class="activity-name"><span class="time"><%=MW.Common.Utils.GetStandardDate(time) %></span><span class="name">大众汽车中国微学苑</span></p>
          <!-- <p>  <label>  {MPNICK}</label> <span style="color: #ccc;">  {MPUSER}</span></p> -->
        </div>
        <div class="box-content">
            <% if (img != "")
               { %>
             <div class="box-img">
                <img src="<%=img %>" alt=""/>
            </div>
            <% } %>
            <div class="box-text">
              <div style="min-height:100px"><%=conent %></div>
               
            </div>
  
            
        </div>
        <div class="box-share">
        <div id="mcover" onclick="document.getElementById('mcover').style.display='none';" style="display:none;">
            <img src="<%=MW.BasePage.GetWebPath %>/mw/wap/images/guide.png" />
        </div>
	<div class="text" id="content">
		<div id="mess_share">
			<div id="share_1">
				<button class="button2" onclick="document.getElementById('mcover').style.display='block';">
					<img src="<%=MW.BasePage.GetWebPath %>/mw/wap/images/icon_msg.png"/>&nbsp;发送给朋友
				</button>
			</div>
			<div id="share_2">
				<button class="button2" onclick="document.getElementById('mcover').style.display='block';">
					<img src="<%=MW.BasePage.GetWebPath %>/mw/wap/images/icon_timeline.png"/>&nbsp;分享到朋友圈
				</button>
			</div>
			<div class="clr"></div>
		</div>
	</div>
</div>
    </div>
    <div class="code"><img src="<%=MW.BasePage.GetWebPath %>/mw/wap/images/2code.jpg"/></div>
    <div class="footer"><img src="<%=MW.BasePage.GetWebPath %>/mw/wap/images/footer.jpg"/></div>
</asp:Content>
