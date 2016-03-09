<%@ Page Title="" Language="C#" MasterPageFile="~/mw/wap/Wap.Master" AutoEventWireup="true" CodeBehind="oauth.aspx.cs" Inherits="PortalWeb.mw.wap.plan.oauth" %>
<asp:Content ID="Content1" ContentPlaceHolderID="head" runat="server">
      <script src="<%=BaseUi.JqueryPath %>/lhgdialog/lhgdialog.js?skin=idialog" type="text/javascript"></script>
    <script src="<%=MW.BasePage.GetWebPath %>/mw/wap/js/regex.js"></script>
</asp:Content>
<asp:Content ID="Content2" ContentPlaceHolderID="ContentPlaceHolder1" runat="server">
    <div class="header"><h2>学员认证</h2><img src="<%=MW.BasePage.GetWebPath %>/mw/wap/images/header.jpg"/></div>
    <div class="page">
        <form id="Form1" runat="server">
    <div class="main">
    <div><span>姓&nbsp;&nbsp;&nbsp;&nbsp;名：</span>
         <asp:TextBox ID="txtName" runat="server" MaxLength="25"></asp:TextBox>
    </div>
    <div><span>手机号：</span> <asp:TextBox ID="txtMobile" runat="server" MaxLength="25"></asp:TextBox></div>
    <div><span>邮&nbsp;&nbsp;&nbsp;&nbsp;箱：</span> <asp:TextBox ID="txtEmail" runat="server" ToolTip="有效电子邮箱"></asp:TextBox></div>
    <div><span>所属单位/部门：</span><asp:DropDownList ID="drpDW" runat="server">
                                </asp:DropDownList>/<asp:DropDownList ID="dropAreaId" runat="server">
                                </asp:DropDownList></div>
    <div><span>认证码：</span><asp:TextBox ID="txtTmpCode" runat="server" MaxLength="25"></asp:TextBox></div>
<button class="button btnsumbit" type="button">
					<img src="<%=MW.BasePage.GetWebPath %>/mw/wap/images/icon_hand.png">&nbsp;提交认证
</button>
  </div>
            </form>
 <div class="clear"></div>
        
  </div>
  <script>
      $(document).ready(function () {
          $(".btnsumbit").click(function () {
              var name = $("#<%=txtName.ClientID%>").val();
          var mobile = $("#<%=txtMobile.ClientID%>").val();
          var email = $("#<%=txtEmail.ClientID%>").val();
          var area = $("#<%=dropAreaId.ClientID%>").val();
              var code = $("#<%=txtTmpCode.ClientID%>").val();
              var dw = $("#<%=drpDW.ClientID%>").val();
          if (name == "") {
              lhgdialog.alert("姓名不能空！");
              return;
          }
          if (mobile == "" || !isMobil(mobile)) {
              lhgdialog.alert("手机号不能空或者格式不对！");
              return;
          }
          if (email == "" || !isEmail(email)) {
              lhgdialog.alert("邮箱不能空或者格式不对！");
              return;
          }
          if (area == "" || area == "0") {
              lhgdialog.alert("请选择所属单位部门！");
              return;
          }
          if (dw == "" || dw == "0") {
              lhgdialog.alert("请选择所属单位！");
              return;
          }
          if (code == "" || code.length < 4) {
              lhgdialog.alert("认证码不能空或者格式不对！");
              return;
          }
          $.post(document.location.href, { "cmd": "reg", "name": name, "mobile": mobile, "email": email, "area": area, "dw": dw, "code": code }, function (data) {
              if (data.status == "success") {
                  lhgdialog.alert("身份验证成功!");
                  if (data.message != "") {
                      window.location.href = data.message;
                  } else {
                      window.location.href = "<%=MW.BasePage.GetWebPath %>/mw/wap/plan/planlist.aspx";
                      }
                  }
                  else if (data.error == "verification") {
                      lhgdialog.alert("身份验证失败,原因:认证码错误");
                  } else if (data.error == "exists") {
                      lhgdialog.alert("身份验证失败,原因:邮箱已被注册");
                  } else {
                      lhgdialog.alert("身份验证失败,请检查!");
                  }
              }, "json");
      });
      });
  </script>
</asp:Content>
