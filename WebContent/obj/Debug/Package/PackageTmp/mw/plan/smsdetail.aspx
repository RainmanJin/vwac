<%@ Page Title="" Language="C#" MasterPageFile="~/mw/Default.Master" AutoEventWireup="true" CodeBehind="smsdetail.aspx.cs" Inherits="PortalWeb.mw.plan.smsDetail" %>
<asp:Content ID="Content1" ContentPlaceHolderID="head" runat="server">
</asp:Content>
<asp:Content ID="Content2" ContentPlaceHolderID="ContentPlaceHolder1" runat="server">
      <div id="h_main">
        <span class="red">◆</span> 表示必填字段</div>
    <div id="h_operate" style="padding-top: 5px;">
		<table border="0" cellpadding="0" cellspacing="1" bgcolor="#ffffff" width="100%">
		
				<tr><td class="td_title"> 手机号：</td>
        <td class="td_content">                        <asp:TextBox id="txtMobile" runat="server" ></asp:TextBox>
            <asp:RequiredFieldValidator ID="txtMobile1" runat="server" ErrorMessage="手机号码不能空!" ControlToValidate="txtMobile"
                        ValidationGroup="add" Display="None"></asp:RequiredFieldValidator>
        <br />注：当有多个手机号时，请用逗号分开(英文半角);最多可向50个手机发送消息!</td></tr>
				<tr><td class="td_title"> 发送内容：</td>
        <td class="td_content">                        <asp:TextBox id="txtSendContent" runat="server" TextMode="MultiLine" style="height:140px;width:300px;"></asp:TextBox>
            <asp:RequiredFieldValidator ID="RequiredFieldValidator1" runat="server" ErrorMessage="发送内容不能空!" ControlToValidate="txtSendContent"
                        ValidationGroup="add" Display="None"></asp:RequiredFieldValidator>
            <br/>注：一条短信的长度是64个字符.您已经输入<span id="strNum" style="color:green;font-size:16px; font-weight:bold; "><%=strNum%></span>个字符.
        </td></tr>
				<tr><td class="td_title"> 类型：</td>
        <td class="td_content">                      
              <asp:DropDownList ID="ddlTypeList" runat="server" >
                  <asp:ListItem Value="0">系统</asp:ListItem>
                  <asp:ListItem Value="11">培训计划</asp:ListItem>
              </asp:DropDownList>
        </td></tr>
				
				</table>
    </div>
    <div id="h_bottom">
        <asp:Button ID="btnSave" OnClick="btnSave_Click" runat="server" Text="保 存" ValidationGroup="add" class="btn">
        </asp:Button>
        &nbsp;
        <input id="Button2" type="button" value="返 回" class="btn" onclick="javascript: history.go(-1);" /><asp:ValidationSummary
            ID="ValidationSummary1" runat="server" ShowMessageBox="true" ShowSummary="false"
            ValidationGroup="add" />
    </div>
    <script>
        $(function () {
            $("#<%=txtMobile.ClientID%>").keypress(function (event) {
                this.value = this.value.replace(/[^0-9\,]/, "");
            }).keyup(function (event) {
                this.value = this.value.replace(/[^0-9\,]/, "");
            });
            $("#<%=txtSendContent.ClientID%>").keyup(function () {
                $("#strNum").text($(this).val().length);
            });
        });
    </script>
</asp:Content>
