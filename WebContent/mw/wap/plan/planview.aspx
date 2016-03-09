<%@ Page Title="" Language="C#" MasterPageFile="~/mw/wap/Wap.Master" AutoEventWireup="true" CodeBehind="planview.aspx.cs" Inherits="PortalWeb.mw.wap.plan.planview" %>
<asp:Content ID="Content1" ContentPlaceHolderID="head" runat="server">
     <script src="<%=BaseUi.JqueryPath %>/lhgdialog/lhgdialog.js?skin=idialog" type="text/javascript"></script>
    <script src="<%=MW.BasePage.GetWebPath %>/mw/wap/js/regex.js"></script>
</asp:Content>
<asp:Content ID="Content2" ContentPlaceHolderID="ContentPlaceHolder1" runat="server">
    <div class="header"><h2><%=type==0?"培训课程排期查询":"已报培训课程" %></h2><img src="<%=MW.BasePage.GetWebPath %>/mw/wap/images/header.jpg"/></div>
    <div class="page">
    <div class="main">
    
    <div>培训课程：<i><asp:Literal runat="server" ID="txtPlanName"></asp:Literal></i></div><div class="clear"></div>
    <div>培训讲师：<i><asp:Literal runat="server" ID="txtTearcher"></asp:Literal></i></div>
    <div>培训时间：<i><asp:Literal runat="server" ID="txtBeginTime"></asp:Literal> </i></div>
    <div>培训天数：<i><asp:Literal runat="server" ID="txtAddress"></asp:Literal></i></div>
     <div>课程状态：<i><asp:Literal runat="server" ID="rbltStatus"></asp:Literal></i></div>
    <div>课程简介：<i><asp:Literal runat="server" ID="txtDescrip"></asp:Literal></i></div>
<% if (canapply)
   { %>
        <input type="hidden" id="hidid" value="<%=Id %>"/>
        <input type="hidden" id="hidcourseid" value="<%=courseid %>"/>
    <button class="button dosubmit">
	<img src="<%=MW.BasePage.GetWebPath %>/mw/wap/images/icon_hand.png">&nbsp;我确认参加
   </button>
        <div class="baoming" style="display: none">
    <div><span>姓&nbsp;&nbsp;&nbsp;&nbsp;名：</span>
        <input type="text" ID="txtName" MaxLength="25" ReadOnly="True" value="<%=base.CurrentUser.NAME %>"/>
    </div>
    <div><span>手机号：</span> <input type="text"  ID="txtMobile" MaxLength="25" value="<%=base.CurrentUser.MOBILE %>"/></div>
            <div><span>邮&nbsp;&nbsp;&nbsp;&nbsp;箱：</span> <input type="text"  ID="txtEmail" ReadOnly="True" value="<%=base.CurrentUser.EMAIL %>"/></div>
            <div><span>报名码：</span><input type="text"  ID="txtTmpCode" MaxLength="25"/></div>
<div class="center"><button class="button btnsumbit" type="button">
                        <img src="<%=MW.BasePage.GetWebPath %>/mw/wap/images/icon_hand.png">&nbsp;提交确认
</button></div>
  </div>
        <% } %>
  </div>
 <div class="clear"></div>
        
  </div>
    <script>
        $(document).ready(function () {
            $(".dosubmit").click(function () {
                $(".baoming").show();
                $(".dosubmit").hide();
            });
            $(".btnsumbit").click(function () {
                var name = $("#txtName").val();
                var mobile = $("#txtMobile").val();
                var email = $("#txtEmail").val();
                var code = $("#txtTmpCode").val();
                var planid = $("#hidid").val();
                var courseid = $("#hidcourseid").val();
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
                if (code == "" || code.length < 4) {
                    lhgdialog.alert("认证码不能空或者格式不对！");
                    return;
                }
                $(".btnsumbit").attr({ "disabled": "disabled" });
                $.post(document.location.href, { "cmd": "bm","courseid":courseid, "planid": planid, "name": name, "mobile": mobile, "email": email, "code": code }, function (data) {
                    if (data.status == "success") {
                        $(".dosubmit").hide();
                        $("#txtTmpCode").val("");
                        lhgdialog.alert("报名成功!");
                        if (data.message != "") {
                            window.location.href = data.message;
                        } else {
                            window.location.href = "<%=MW.BasePage.GetWebPath %>/mw/wap/plan/myplan.aspx";
                        }
                    }
                    else if (data.error == "verification") {
                        $(".btnsumbit").removeAttr("disabled");
                        lhgdialog.alert("报名失败,原因:认证码错误");
                    } else if (data.error == "exists") {
                        lhgdialog.alert("报名失败,原因:您已经报名!");
                    } else {
                        $(".btnsumbit").removeAttr("disabled");
                        lhgdialog.alert("报名失败,请检查!");
                    }
                }, "json");
            });
        });
    </script>
</asp:Content>
