<%@ Page Title="" Language="C#" MasterPageFile="~/mw/Default.Master" AutoEventWireup="true" CodeBehind="VoteUnitDetail.aspx.cs"  Inherits="Plugin.VWSurvey.Admini.VWSurvey.VoteUnitDetail" %>
<asp:Content ID="Content1" ContentPlaceHolderID="head" runat="server">
    <script type="text/javascript" src="<%=BaseUi.JqueryPath %>/jquery.validate.js"></script>
    <style>*{ padding: 0px;}</style>
</asp:Content>
<asp:Content ID="Content2" ContentPlaceHolderID="ContentPlaceHolder1" runat="server">
    <div id="h_operate" style="padding-top: 5px;">
		<table border="0" cellpadding="0" cellspacing="1" bgcolor="#ffffff" width="100%">
		      
				<tr><td class="td_title"> 调查单元名称：</td>
        <td class="td_content">                        <asp:TextBox id="txtC_Title" runat="server" Width="96%" Rows="4" TextMode="MultiLine" CssClass="required" ></asp:TextBox>
             <asp:RequiredFieldValidator ID="rfvTitle" runat="server" ErrorMessage="名称不能为空!" ControlToValidate="txtC_Title"
                        ValidationGroup="add" Display="None"></asp:RequiredFieldValidator>
        </td></tr>
				<tr>
                <td class="td_title">调查类型：</td>
                <td class="td_content">
                    <asp:dropdownlist id="drpType" runat="server" Width="152px">
                        <asp:ListItem Value="2">单选</asp:ListItem>
                        <asp:ListItem Value="5">多行文本</asp:ListItem>
                        <asp:ListItem Value="3" >多选</asp:ListItem>
                        
                    </asp:dropdownlist>
                    <!--
                        <asp:ListItem Value="1" Enabled="False">文本行</asp:ListItem>
                        
                        <asp:ListItem Value="4"  Enabled="False">下拉列表</asp:ListItem>-->
                </td>
            </tr>
				</table>
    </div>
    <div id="h_bottom">
                <asp:HiddenField runat="server" ID="hidSysvoteid" Value="0"/>
         <asp:Button ID="btnSave" OnClick="btnSave_Click" runat="server" Text="提交" class="btn"  ValidationGroup="add" >
        </asp:Button>
        &nbsp;
        <input id="Button2" type="button" value="返 回" onclick="goUrl()" class="btn"/><asp:ValidationSummary
            ID="ValidationSummary1" runat="server" ShowMessageBox="true" ShowSummary="false"
            ValidationGroup="add" />
    </div>
     <script>
         $(document).ready(function () {
             var fromid = '<%=Master.FindControl("dofrm").ClientID%>';
            $("#" + fromid).validate();
        });
    </script>
</asp:Content>
