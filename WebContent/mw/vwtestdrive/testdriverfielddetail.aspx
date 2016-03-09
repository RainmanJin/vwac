<%@ Page Title="" Language="C#" MasterPageFile="~/mw/Default.Master" AutoEventWireup="true" CodeBehind="testdriverfielddetail.aspx.cs" Inherits="PortalWeb.mw.vwtestdrive.testdriverfielddetail" %>
<asp:Content ID="Content1" ContentPlaceHolderID="head" runat="server">
</asp:Content>
<asp:Content ID="Content2" ContentPlaceHolderID="ContentPlaceHolder1" runat="server">
    <div id="h_operate" style="padding-top: 5px;">
		<table border="0" cellpadding="0" cellspacing="1" bgcolor="#ffffff" width="100%">
		
				<tr><td class="td_title"><span class="red">◆</span>字段名：</td>
        <td class="td_content">                        <asp:TextBox id="txtC_Name" runat="server" ></asp:TextBox>
             <asp:RequiredFieldValidator ID="rfvTitle" runat="server" ErrorMessage="字段名不能为空!" ControlToValidate="txtC_Name"
                        ValidationGroup="add" Display="None"></asp:RequiredFieldValidator>
        </td></tr>
				<tr><td class="td_title"> 父级：</td>
        <td class="td_content">                        <asp:DropDownList runat="server" ID="DropDownList1">
                </asp:DropDownList>

        </td></tr>
				<tr><td class="td_title"> 排序：</td>
        <td class="td_content">                        <asp:TextBox id="txtOrderby" runat="server" Text="0"></asp:TextBox>
        </td></tr>
				<tr><td class="td_title"> 是否使用：</td>
        <td class="td_content">                         <asp:RadioButtonList runat="server" RepeatDirection="Horizontal" ID="txtChangdi" BorderStyle="None" RepeatLayout="Flow">
                                                        <asp:ListItem Value="1" Selected="True">是</asp:ListItem>
                                                        <asp:ListItem Value="0">否</asp:ListItem>
                                                  </asp:RadioButtonList> 
            
        </td></tr>
            <tr><td class="td_title">备注说明：</td>
        <td class="td_content">                        <asp:TextBox id="txtRemark" runat="server" TextMode="MultiLine" Rows="5" Width="90%" ></asp:TextBox>
        </td></tr>
				</table>
    </div>
    <div id="h_bottom">
        <asp:Button ID="btnSave" OnClick="btnSave_Click" runat="server" Text="保 存" ValidationGroup="add" class="btn">
        </asp:Button>
        &nbsp;
       <input id="Button1" value="返回" type="button" class="btn" onclick="javascript: history.go(-1);" /> 
    </div>
</asp:Content>
