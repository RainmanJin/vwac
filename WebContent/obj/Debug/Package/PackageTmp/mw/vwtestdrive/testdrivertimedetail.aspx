<%@ Page Title="" Language="C#" MasterPageFile="~/mw/Default.Master" AutoEventWireup="true" CodeBehind="testdrivertimedetail.aspx.cs" Inherits="PortalWeb.mw.vwtestdrive.testDrivertimedetail" %>
<asp:Content ID="Content1" ContentPlaceHolderID="head" runat="server">
</asp:Content>
<asp:Content ID="Content2" ContentPlaceHolderID="ContentPlaceHolder1" runat="server">
    <div id="h_main"></div>
    <div id="h_operate" style="padding-top: 5px;">
		<table border="0" cellpadding="0" cellspacing="1" bgcolor="#ffffff" width="100%">
		
				<tr><td class="td_title"> 项目名称：</td>
        <td class="td_content">                        <asp:Label id="txtC_Course" runat="server" ></asp:Label>
        </td></tr>
				<tr><td class="td_title"> 学员：</td>
        <td class="td_content">                        <asp:Label id="txtStudent" runat="server" ></asp:Label>
        </td></tr>
				<tr><td class="td_title"> 老师：</td>
        <td class="td_content">                        <asp:Label id="txtC_Teacher" runat="server" ></asp:Label>
        </td></tr>
				<tr><td class="td_title"> 开始时间：</td>
        <td class="td_content">                        <asp:Label id="txtStartTime" runat="server" SkinID="date"></asp:Label>
        </td></tr>
                <tr><td class="td_title"> 结束时间：</td>
        <td class="td_content">                        <asp:Label id="txtEndTime" runat="server"  SkinID="date"></asp:Label>
        </td></tr>
				<tr><td class="td_title"> 车型：</td> 
        <td class="td_content">                        <asp:Label id="txtChexi" runat="server" ></asp:Label>
        </td></tr>
				<tr><td class="td_title"> 场地：</td>
        <td class="td_content">                        <asp:Label id="txtChangdi" runat="server" ></asp:Label>
        </td></tr>
				</table>
    </div>
    <div id="h_bottom">
      <%--  <input id="Button2" type="button" value="关 闭" onclick="closeDlg()" /><asp:ValidationSummary
            ID="ValidationSummary1" runat="server" ShowMessageBox="true" ShowSummary="false"
            ValidationGroup="add" />--%>
       <input id="Button1" value="返回" type="button" class="btn" onclick="javascript: history.go(-1);" /> 
    </div>
    <script language="javascript" type="text/javascript">
        function closeWindow() {
            
            window.opener = null; window.close();
            
        }

    </script> 
</asp:Content>
