<%@ Page Title="" Language="C#" MasterPageFile="~/mw/Default.Master" AutoEventWireup="true" CodeBehind="appplatformdetail.aspx.cs" Inherits="PortalWeb.mw.app.appplatformdetail" %>
<asp:Content ID="Content1" ContentPlaceHolderID="head" runat="server">
    <script type="text/javascript" src="<%=BaseUi.JqueryPath %>/jquery.validate.js"></script>
</asp:Content>
<asp:Content ID="Content2" ContentPlaceHolderID="ContentPlaceHolder1" runat="server">   
<div id="h_main">
    <div id="h_operate" style="padding-top: 5px;">

        <table border="0" cellpadding="0" cellspacing="1" bgcolor="#ffffff" width="100%">
            <tr>
                <td class="td_title"><span class="red">◆</span>  课件包名称：</td>
                <td class="td_content">
                    <asp:TextBox ID="txtname" runat="server" CssClass="required" Width="350"></asp:TextBox>
                </td>
            </tr>

            <tr>
                <td class="td_title">缩略图：</td>
                <td class="td_content">
                    <asp:TextBox ID="txticonURL" runat="server" Width="350"></asp:TextBox>
                    <br />
                    <iframe src="../ashx/_upload.aspx?filepath=/uploads/apkimg/&recievetxtname=<%=txticonURL.ClientID %>&filetype=image&filesize=409600"
                        scrolling="no" width="400px" frameborder="0" height="22"></iframe>
                </td>
            </tr>
            <tr>
                <td class="td_title">标识：</td>
                <td class="td_content">
                    <asp:TextBox ID="txtpkgid" runat="server"Width="150"></asp:TextBox>
                </td>
            </tr>
            <tr>
                <td class="td_title">版本：</td>
                <td class="td_content">
                    <asp:TextBox ID="txtversion" runat="server"></asp:TextBox>
                </td>
            </tr>
            <tr>
                <td class="td_title">品牌：</td>
                <td class="td_content">
                    <asp:DropDownList ID="txtbrand" runat="server">
                    </asp:DropDownList>
                </td>
            </tr>
            <tr>
                <td class="td_title">领域：</td>
                <td class="td_content">
                    <asp:DropDownList ID="txtproType" runat="server" >
                    </asp:DropDownList>
                </td>
            </tr>
            <tr>
                <td class="td_title">状态：</td>
                <td class="td_content">
                    <asp:DropDownList ID="txtstatus" runat="server" >
                    </asp:DropDownList>
                </td>
            </tr>

            <tr>
                <td class="td_title">安卓版<br />
                    课件包：</td>
                <td class="td_content">
                    <asp:TextBox ID="txtapkurl" runat="server" Width="450"></asp:TextBox>
                    <br />
                    <iframe src="../ashx/_upload.aspx?filepath=/uploads/apk/&recievetxtname=<%=txtapkurl.ClientID %>&filetype=fj&filesize=4096000"
                        scrolling="no" width="400px" frameborder="0" height="22"></iframe>
                </td>

            </tr>
            <tr>
                <td class="td_title">ios版<br />
                    课件包：</td>
                <td class="td_content">
                    <asp:TextBox ID="txtiosurl" runat="server" Width="450"></asp:TextBox>
                    <br />
                    <iframe src="../ashx/_upload.aspx?filepath=/uploads/apk/&recievetxtname=<%=txtiosurl.ClientID %>&filetype=fj&filesize=4096000"
                        scrolling="no" width="400px" frameborder="0" height="22"></iframe>
                </td>
            </tr>
            <tr>
                <td class="td_title">简介：</td>
                <td class="td_content">
                    <asp:TextBox ID="txtremark" runat="server" Width="100%" Height="100"></asp:TextBox>
                </td>
            </tr>
        </table>
    </div>
    </div>
    <asp:HiddenField runat="server" ID="hidsysid" Value="0"/>
    <div id="h_bottom">
        <asp:Button ID="btnSave" OnClick="btnSave_Click" runat="server" Text="保 存" ValidationGroup="add" class="btn">
        </asp:Button>
        &nbsp;
        <input id="Button2" type="button" class="btn" value="返回" onclick="goUrl()" />
    </div>
    <script type="text/javascript">
        $(document).ready(function () {

            $("#<%=Master.FindControl("dofrm").ClientID%>").validate();

        });
                </script>
</asp:Content>
