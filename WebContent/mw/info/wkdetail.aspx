<%@ Page Title="" Language="C#" MasterPageFile="~/mw/Default.Master" AutoEventWireup="true" CodeBehind="wkdetail.aspx.cs" Inherits="PortalWeb.mw.Info.WKDetail" %>

<asp:Content ID="Content1" ContentPlaceHolderID="head" runat="server">
</asp:Content>
<asp:Content ID="Content2" ContentPlaceHolderID="ContentPlaceHolder1" runat="server">
    <div id="h_main">
        <span class="red">◆</span> 表示必填字段
    </div>
    <div id="h_operate" style="padding-top: 5px;">
        <table border="0" cellpadding="0" cellspacing="1" bgcolor="#ffffff" width="100%">

            <tr>
                <td class="td_title"><span class="red">◆</span> 标题：</td>
                <td class="td_content">
                    <asp:TextBox ID="txtTitle" runat="server" Width="200px"></asp:TextBox>
                    <asp:RequiredFieldValidator ID="rfvTitle" runat="server" ErrorMessage="标题不能为空!" ControlToValidate="txtTitle"
                        ValidationGroup="post" Display="None"></asp:RequiredFieldValidator>
                </td>
            </tr>
            <tr>
                <td class="td_title"><span class="red">◆</span> 新闻类别：</td>
                <td class="td_content">
                    <asp:DropDownList ID="dropTypeId" runat="server"></asp:DropDownList>
               <%--     <asp:RangeValidator ID="rvdTypeId" runat="server" ErrorMessage="请选择类别" Display="None"
                        ControlToValidate="dropTypeId" ValidationGroup="post" Type="Integer" MinimumValue="1" MaximumValue="99999999"></asp:RangeValidator>--%>
                </td>
            </tr>

            <tr>
                <td class="td_title">封面图：</td>
                <td class="td_content">
                    <asp:FileUpload ID="fileFilePath" runat="server" CssClass="fUp" />&nbsp;
                    <asp:Image runat="server" ID="imgPhoto" Height="25" Width="45" />
                </td>
            </tr>
            <tr>
                <td class="td_title">排序：</td>
                <td class="td_content">
                    <asp:TextBox ID="txtSort" runat="server" onkeypress="isNum()" Width="60px">0</asp:TextBox>
                    <asp:RangeValidator ID="rvdSort" runat="server" ErrorMessage="排序格式不正确" Display="None"
                        ControlToValidate="txtSort" ValidationGroup="post" Type="Integer" MinimumValue="0" MaximumValue="99999999"></asp:RangeValidator>
                </td>
            </tr>
            <tr>
                <td class="td_title">文章内容：</td>
                <td class="td_content">
                    <asp:TextBox ID="txtContents" runat="server" TextMode="MultiLine" Width="95%" Height="270"></asp:TextBox>
                </td>
            </tr>
            <%--<tr><td class="td_title"> 资源所属：</td>
        <td class="td_content">  <asp:TextBox ID="txtAidUsers" runat="server" SkinID="find"
                     ReadOnly="True"></asp:TextBox>
                <asp:HiddenField ID="hidAidUsers" runat="server" Value="0" />(默认为当前用户)
        </td></tr>--%>
            <tr>
                <td class="td_title">课件包：</td>
                <td class="td_content">
                    <asp:TextBox ID="txtFj" runat="server"></asp:TextBox>
                    &nbsp;附件格式：zip
       &nbsp;&nbsp;
                    <iframe src="../ashx/_upload.aspx?filepath=/UpLoads/wk/&recievetxtname=<%=txtFj.ClientID %>&filetype=fj&filesize=409600"
                        scrolling="no" width="400px" frameborder="0" height="22" top="-8"></iframe>
                </td>
            </tr>
        </table>
    </div>
    <div id="h_bottom">
        <%--<asp:UpdatePanel ID="UpdatePanel2" runat="server">
            <contenttemplate>--%>
        <asp:Button ID="btnSave" OnClick="btnSave_Click" runat="server" Text="保 存" ValidationGroup="post" class="btn"></asp:Button>
        &nbsp;
        <input id="Button2" type="button" value="返 回" onclick="javascript: history.go(-1);" class="btn" /><asp:ValidationSummary
            ID="ValidationSummary1" runat="server" ShowMessageBox="true" ShowSummary="false"
            ValidationGroup="post" />
        <%-- </contenttemplate>
        </asp:UpdatePanel>--%>
    </div>
    <script>
        $(document).ready(function () {
            $('#<%=txtContents.ClientID%>').xheditor({ upImgUrl: '<%=MW.BasePage.GetWebPath %>/ashx/_upfile.aspx?type=1&filetype=image&filesize=1000&filepath=/UpLoads/wk/' });
        });
    </script>
</asp:Content>
