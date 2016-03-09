<%@ Page Title="" Language="C#" MasterPageFile="~/mw/Default.Master" AutoEventWireup="true" CodeBehind="sktitemdetali.aspx.cs" Inherits="PortalWeb.mw.retailknowhow.sktitemdetali" %>
<asp:Content ID="Content1" ContentPlaceHolderID="head" runat="server">
    <script type="text/javascript" src="<%=BaseUi.JqueryPath %>/jquery.validate.js"></script>
</asp:Content>
<asp:Content ID="Content2" ContentPlaceHolderID="ContentPlaceHolder1" runat="server">
    <div id="h_main">
    <div id="h_operate" style="padding-top: 5px;">
		<table border="0" cellpadding="0" cellspacing="1" bgcolor="#ffffff" width="100%">
		
				<tr><td class="td_title"> 项目名称：</td>
        <td class="td_content">                         <asp:TextBox id="txtName" runat="server" Width="80%" CssClass="required"></asp:TextBox>
           
        </td></tr>
				<tr><td class="td_title"> 设定颜色：</td>
                 <td class="td_content">
                    <asp:dropdownlist id="drpColor" runat="server" Width="152px">
                        <asp:ListItem Value="0"  >默认</asp:ListItem>
                        <asp:ListItem Value="1"  >绿</asp:ListItem>
                        <asp:ListItem Value="2"  >深绿</asp:ListItem>
                        <asp:ListItem Value="3"  >蓝</asp:ListItem>
                        <asp:ListItem Value="4"  >深蓝</asp:ListItem>
                        <asp:ListItem Value="5"  >橙</asp:ListItem>
                    </asp:dropdownlist>
        </td></tr>
				<tr><td class="td_title"> 样式：</td>
        <td class="td_content">
                    <asp:dropdownlist id="drpType" runat="server" Width="152px">
                        <asp:ListItem Value="0"  >默认</asp:ListItem>
                        <asp:ListItem Value="1"  >六边形</asp:ListItem>
                        <asp:ListItem Value="2"  >五边形</asp:ListItem>
                        <asp:ListItem Value="3"  >正方形</asp:ListItem>
                        <asp:ListItem Value="4"  >椭圆</asp:ListItem>
                    </asp:dropdownlist>
        </td></tr>
				<tr><td class="td_title"> <asp:Literal ID="lit" runat="server">所属上级：</asp:Literal></td>
        <td class="td_content">
              <asp:DropDownList ID="DrpParentid" runat="server"  Width="152px"></asp:DropDownList>
            <asp:DropDownList ID="DrpParentid2" runat="server" Visible="False"  Width="152px"></asp:DropDownList>
        </td></tr>
				<tr><td class="td_title"> 排序：</td>
        <td class="td_content">                        <asp:TextBox id="txtN_OrderId" runat="server" Text="0"  onkeypress="isNum()" maxlength="10" ></asp:TextBox><span>填写数字</span>
           
        </td></tr>
            <tr><td class="td_title"> 预留1：</td>
        <td class="td_content">                        <asp:TextBox id="txtA1" runat="server" Text="0"  onkeypress="isNum()"  ></asp:TextBox><span>填写数字</span>
        </td></tr>
				<tr><td class="td_title"> 预留2：</td>
        <td class="td_content">                        <asp:TextBox id="txtA2" runat="server" Text="0"  onkeypress="isNum()"  ></asp:TextBox><span>填写数字</span>
        </td></tr>
				<tr><td class="td_title"> 预留3：</td>
        <td class="td_content">                        <asp:TextBox id="txtA3" runat="server" ></asp:TextBox>
        </td></tr>
				<tr><td class="td_title"> 预留4：</td>
        <td class="td_content">                        <asp:TextBox id="txtA4" runat="server" ></asp:TextBox>
        </td></tr>
				</table>
    </div>
    <div id="h_bottom">
        <asp:Button ID="btnSave" onclick="btnSave_Click" runat="server" Text="保 存" ValidationGroup="add" class="btn">
        </asp:Button>
        &nbsp;
            <input id="Button1" value="返回" type="button" class="btn" onclick="javascript: history.go(-1);" />  <asp:ValidationSummary
            ID="ValidationSummary1" runat="server" ShowMessageBox="true" ShowSummary="false"
            ValidationGroup="add" />
    </div></div>
    <script type="text/javascript">
        $(document).ready(function () {

            $("#<%=Master.FindControl("dofrm").ClientID%>").validate();
            
        });
                </script>
</asp:Content>
