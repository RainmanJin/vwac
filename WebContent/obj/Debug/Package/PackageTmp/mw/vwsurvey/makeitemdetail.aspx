<%@ Page Title="" Language="C#" MasterPageFile="~/mw/Default.Master" AutoEventWireup="true" CodeBehind="MakeItemDetail.aspx.cs" Inherits="Plugin.VWSurvey.Admini.VWSurvey.MakeItemDetail" %>

<asp:Content ID="Content1" ContentPlaceHolderID="head" runat="server">
</asp:Content>
<asp:Content ID="Content2" ContentPlaceHolderID="ContentPlaceHolder1" runat="server">
    <div id="h_main">
<%--        <span class="red">◆</span> 表示必填字段--%>
    </div>
    <div id="h_operate" style="padding-top: 5px;">
        <table border="0" cellpadding="0" cellspacing="1" bgcolor="#ffffff" width="100%">
            <tr>
                <td class="td_title">选项名称：</td>
                <td class="td_content">
                    <asp:TextBox ID="txtC_KeyTitle" runat="server" Rows="4" TextMode="MultiLine" Width="98%"></asp:TextBox>
                </td>
            </tr>
            <tr style="display: none">
                <td class="td_title">类型：</td>
                <td class="td_content">
                    <asp:dropdownlist id="drpType" runat="server" Width="152px">
                        <asp:ListItem Value="2">单选</asp:ListItem>
                        <asp:ListItem Value="1" Enabled="False">文本行</asp:ListItem>
                        <asp:ListItem Value="5">多行文本</asp:ListItem>
                        <asp:ListItem Value="3"  Enabled="False">多选</asp:ListItem>
                        <asp:ListItem Value="4"  Enabled="False">下拉列表</asp:ListItem>
                    </asp:dropdownlist>
                </td>
            </tr>
            <tr>
                <td class="td_title">排序：</td>
                <td class="td_content">
                    <asp:TextBox ID="txtN_OrderId" runat="server" Text="0"></asp:TextBox>
                </td>
            </tr>
            <tr>
                <td class="td_title">分值：</td>
                <td class="td_content">
                    <asp:TextBox ID="txtN_Score" runat="server" Text="0"></asp:TextBox>
                </td>
            </tr>
            <tr>
                <td class="td_title">验证规则：</td>
                <td class="td_content">
                  <asp:DropDownList id="DDLRule" runat="server" >
                                    </asp:DropDownList>
                </td>
            </tr>
            
        </table>
    </div>
    <div id="h_bottom">

                <asp:HiddenField runat="server" ID="hidsubid" Value="0"/>
                <asp:HiddenField runat="server" ID="hidtype" Value="0"/>
                <asp:Button ID="btnSave" OnClick="btnSave_Click" runat="server" Text="保 存" ValidationGroup="add" class="btn"></asp:Button>
                &nbsp;
        <input id="Button2" type="button" value="返 回" onclick="goUrl()" class="btn"/><asp:ValidationSummary
            ID="ValidationSummary1" runat="server" ShowMessageBox="true" ShowSummary="false"
            ValidationGroup="add" />
    </div>
</asp:Content>
