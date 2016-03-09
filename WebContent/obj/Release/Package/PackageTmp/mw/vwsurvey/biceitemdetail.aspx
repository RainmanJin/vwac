<%@ Page Title="" Language="C#" MasterPageFile="~/mw/Default.Master" AutoEventWireup="true" CodeBehind="BiceItemDetail.aspx.cs" Inherits="Plugin.VWSurvey.Admini.VWSurvey.BiceItemDetail" %>
<asp:Content ID="Content1" ContentPlaceHolderID="head" runat="server">
    <script type="text/javascript" src="<%=BaseUi.JqueryPath %>/jquery.validate.js"></script>
</asp:Content>
<asp:Content ID="Content2" ContentPlaceHolderID="ContentPlaceHolder1" runat="server">
    <div id="h_main">
        <span class="red">◆</span> 表示必填字段</div>
    <div id="h_operate" style="padding-top: 5px;">
        <table border="0" cellpadding="0" cellspacing="1" bgcolor="#ffffff" width="100%">
		
				
				<tr><td class="td_title"> <span class="red">◆</span>题目名称：</td>
        <td class="td_content">                        <asp:TextBox id="txtC_SubTitle" runat="server" runat="server"  Rows="4" TextMode="MultiLine" Width="98%" CssClass="required"></asp:TextBox>
            <asp:RequiredFieldValidator ID="rfvTitle" runat="server" ErrorMessage="题目名称不能为空!" ControlToValidate="txtC_SubTitle"
                        ValidationGroup="add" Display="None"></asp:RequiredFieldValidator>
        </td></tr>
				<tr><td class="td_title"> 排序：</td>
        <td class="td_content">                        <asp:TextBox id="txtN_OrderId" runat="server" Text="0"  onkeypress="isNum()" ></asp:TextBox><span>填写数字</span>
            <asp:RangeValidator
                            ID="RangeValidator1" runat="server" ControlToValidate="txtN_OrderId" Display="None"
                            ErrorMessage="请正确填写排序!" MaximumValue="100000" MinimumValue="0" Type="Integer"
                            ValidationGroup="add"></asp:RangeValidator>
        </td></tr>
				<tr><td class="td_title"> 是否必答：</td>
        <td class="td_content">                       <asp:DropDownList id="drpNeed" runat="server">
															<asp:ListItem Value="0" Selected="True">非必答</asp:ListItem>
															<asp:ListItem Value="1">必答</asp:ListItem>
														</asp:DropDownList>
        </td></tr>
            <tr>
                <td class="td_title"><span class="red">◆</span>调查单元：</td>
                <td class="td_content">
                    <asp:dropdownlist id="drpUnit" runat="server" Width="152px">
                    </asp:dropdownlist>
                     <asp:RequiredFieldValidator ID="RequiredFieldValidator1" runat="server" ErrorMessage="调查单元不能为空!"
                        ControlToValidate="drpUnit" ValidationGroup="add" Display="None"></asp:RequiredFieldValidator>
                </td>
            </tr>
        <tr><td class="td_title"><span class="red">◆</span>所属类别：</td>
        <td class="td_content">                        <asp:DropDownList runat="server" ID="drpType"/>
        </td></tr>
				</table>
    </div>
    <div id="h_bottom">
<asp:HiddenField runat="server" ID="hidsysid" Value="0"/>
        <asp:Button ID="btnSave" OnClick="btnSave_Click" runat="server" Text="保 存" ValidationGroup="add" class="btn">
        </asp:Button>
        &nbsp;
        <input id="Button2" type="button" value=" 返 回 " onclick="goUrl()" class="btn" /><asp:ValidationSummary
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
