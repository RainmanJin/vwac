<%@ Page Title="" Language="C#" MasterPageFile="~/mw/Default.Master" AutoEventWireup="true" CodeBehind="tmpcodedetail.aspx.cs" Inherits="PortalWeb.mw.plan.tmpcodeDetail" %>
<asp:Content ID="Content1" ContentPlaceHolderID="head" runat="server">
</asp:Content>
<asp:Content ID="Content2" ContentPlaceHolderID="ContentPlaceHolder1" runat="server">
    <div id="h_main">
        <span class="red">◆</span> 表示必填字段</div>
    <div id="h_operate" style="padding-top: 5px;">
		<table border="0" cellpadding="0" cellspacing="1" bgcolor="#ffffff" width="100%">
       <tr><td class="td_title"> 类型：</td>
        <td class="td_content">
             <asp:RadioButtonList ID="rbltType" runat="server" RepeatDirection="Horizontal">
                        <asp:ListItem Value="0" Selected="True">认证码</asp:ListItem>
                       <asp:ListItem Value="1" >课程码</asp:ListItem>
                    </asp:RadioButtonList>                       
        </td></tr>
				
				<tr><td class="td_title"> 可允许使用次数：</td>
        <td class="td_content">                        <asp:TextBox id="txtCanUse" runat="server" Text="0"></asp:TextBox>
        0 不限制，直到过期！</td></tr>
				<tr>
                <td class="td_title">
                  <span class="red">◆</span>  结束时间：
                </td>
                <td class="td_content">
                    <asp:TextBox ID="txtIndate" runat="server" OnClick="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd'})"
                        SkinID="date" title="选择日期"></asp:TextBox>
                          <asp:RequiredFieldValidator ID="RequiredFieldValidator3" runat="server" ErrorMessage="结束时间!" ControlToValidate="txtIndate"
                        ValidationGroup="add" Display="None"></asp:RequiredFieldValidator>
                </td>
            </tr>
            <tr><td class="td_title"> 生成数量：</td>
        <td class="td_content">                        <asp:TextBox id="txtNum" runat="server" Text="10"></asp:TextBox>
        批量生成数量</td></tr>
            <tr><td class="td_title"> 随机码长度：</td>
        <td class="td_content">                        <asp:TextBox id="txtLen" runat="server" Text="6"></asp:TextBox>
        生成码长度</td></tr>
				</table>
    </div>
    <div id="h_bottom">
        <asp:Button ID="btnSave" OnClick="btnSave_Click" runat="server" Text="批量生成" ValidationGroup="add" class="btn">
        </asp:Button>
        &nbsp;
        <input id="Button2" type="button" value="返 回" onclick="javascript: history.go(-1);" class="btn"/><asp:ValidationSummary
            ID="ValidationSummary1" runat="server" ShowMessageBox="true" ShowSummary="false"
            ValidationGroup="add" />
       
    </div>
</asp:Content>
