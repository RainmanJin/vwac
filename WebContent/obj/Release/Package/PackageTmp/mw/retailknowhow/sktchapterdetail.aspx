<%@ Page Title="" Language="C#" MasterPageFile="~/mw/Default.Master" AutoEventWireup="true" CodeBehind="sktchapterdetail.aspx.cs" Inherits="PortalWeb.mw.retailknowhow.SktchapterDetail" %>
<asp:Content ID="Content1" ContentPlaceHolderID="head" runat="server">
        <script type="text/javascript" src="<%=BaseUi.JqueryPath %>/jquery.validate.js"></script>
</asp:Content>
<asp:Content ID="Content2" ContentPlaceHolderID="ContentPlaceHolder1" runat="server">
     <div id="h_operate" style="padding-top: 5px;">
		<table border="0" cellpadding="0" cellspacing="1" bgcolor="#ffffff" width="100%">
		
				<tr><td class="td_title"> 类型：</td>
        <td class="td_content">
                    <asp:dropdownlist id="C_Type" runat="server" Width="152px">
                        <asp:ListItem Value="1"  >结构图</asp:ListItem>
                        <asp:ListItem Value="2"  >排序图</asp:ListItem>
                        <asp:ListItem Value="3"  >树状图</asp:ListItem>
                    </asp:dropdownlist>
        </td></tr>
				<tr><td class="td_title"> 题目名称：</td>
        <td class="td_content">                        <asp:TextBox id="txtName" runat="server" Width="80%" CssClass="required"></asp:TextBox>

        </td></tr>
            <tr>
                <td class="td_title">状态：</td>
                <td class="td_content">
                    <asp:CheckBox runat="server" ID="chkislock"/> 是否锁定
                </td>
            </tr>
            
				<tr><td class="td_title"> 排序：</td>
        <td class="td_content">                        <asp:TextBox id="txtN_OrderId" runat="server" Text="0"  onkeypress="isNum()" ></asp:TextBox><span>填写数字</span>
            <asp:RangeValidator
                            ID="RangeValidator1" runat="server" ControlToValidate="txtN_OrderId" Display="None"
                            ErrorMessage="请正确填写排序!" MaximumValue="100000" MinimumValue="0" Type="Integer"
                            ValidationGroup="add"></asp:RangeValidator>
        </td></tr>
            <tr>
                <td class="td_title">课程介绍：</td>
                <td class="td_content">
                    <asp:TextBox ID="txtC_Content" runat="server" Rows="10" TextMode="MultiLine" Width="98%" CssClass="xheditor({tools:'mini'})"></asp:TextBox>
                </td>
            </tr>
				</table>
    </div>
    <div id="h_bottom">
        <asp:Button ID="btnSave" OnClick="btnSave_Click" runat="server" Text="保 存" ValidationGroup="add" class="btn">
        </asp:Button>
        &nbsp;
         <input id="Button1" value="返回" type="button" class="btn" onclick="javascript: history.go(-1);" /> 
        <asp:ValidationSummary
            ID="ValidationSummary1" runat="server" ShowMessageBox="true" ShowSummary="false"
            ValidationGroup="add" />
    </div>
    <script type="text/javascript">
        $(document).ready(function () {

            $("#<%=Master.FindControl("dofrm").ClientID%>").validate();

        });
                </script>
</asp:Content>
