<%@ Page Title="" Language="C#" MasterPageFile="~/mw/wap/Wap.Master" AutoEventWireup="true" CodeBehind="infoview.aspx.cs" Inherits="PortalWeb.mw.wap.Info.infoview" %>
<asp:Content ID="Content1" ContentPlaceHolderID="head" runat="server">
</asp:Content>
<asp:Content ID="Content2" ContentPlaceHolderID="ContentPlaceHolder1" runat="server">
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
                <% if (fj != ""){ 
                       if (fj.EndsWith(".pdf"))
                       {
                %>
                <div style="min-height:40px">附件下载：<a href="<%= fj %>" class="fj_pdf">附件</a></div>
                <%
                       }
                       else
                       {
                           %>
                  <div style="min-height:40px;">
<br/>
                      <p>建议在WIFI网络环境下浏览视频</p>
                      <video src="<%=fj %>" controls="controls" preload="auto"></video>
                  </div>
                <%
                       }
                       %>

                <% } %>
            </div>
  
            
        </div>
        <div class="box-share">
        <div id="mcover" onclick="document.getElementById('mcover').style.display='none';" style="display:none;">
            <img src="<%=MW.BasePage.GetWebPath %>/wap/images/guide.png" />
        </div>
	<div class="text" id="content">
		<div id="mess_share">
			<div id="share_1">
				<button class="button2" onclick="document.getElementById('mcover').style.display='block';">
					<img src="<%=MW.BasePage.GetWebPath %>/wap/images/icon_msg.png"/>&nbsp;发送给朋友
				</button>
			</div>
			<div id="share_2">
				<button class="button2" onclick="document.getElementById('mcover').style.display='block';">
					<img src="<%=MW.BasePage.GetWebPath %>/wap/images/icon_timeline.png"/>&nbsp;分享到朋友圈
				</button>
			</div>
			<div class="clr"></div>
		</div>
	</div>
</div>
    </div>
    <div class="code"><img src="<%=MW.BasePage.GetWebPath %>/wap/images/2code.jpg"/></div>
    <div class="footer"><img src="<%=MW.BasePage.GetWebPath %>/wap/images/footer.jpg"/></div>
</asp:Content>
