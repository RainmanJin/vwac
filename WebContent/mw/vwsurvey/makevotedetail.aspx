<%@ Page Title="" Language="C#" MasterPageFile="~/mw/Default.Master" AutoEventWireup="true" CodeBehind="MakeVoteDetail.aspx.cs"  Inherits="Plugin.VWSurvey.Admini.VWSurvey.MakeVoteDetail" %>
<asp:Content ID="Content1" ContentPlaceHolderID="head" runat="server">
     <script type="text/javascript" src="<%=BaseUi.JqueryPath %>/jquery.validate.js"></script>
    <style>*{ padding: 0px;}</style>
</asp:Content>
<asp:Content ID="Content2" ContentPlaceHolderID="ContentPlaceHolder1" runat="server">
    <div id="h_operate" style="padding-top: 5px;">
		<table border="0" cellpadding="0" cellspacing="1" bgcolor="#ffffff" width="100%">
		      
				<tr><td class="td_title"> 调查模版名称：</td>
        <td class="td_content">                        <asp:TextBox id="txtC_Title" runat="server" Width="96%" Rows="4" TextMode="MultiLine" CssClass="required"></asp:TextBox>
             <asp:RequiredFieldValidator ID="rfvTitle" runat="server" ErrorMessage="名称不能为空!" ControlToValidate="txtC_Title"
                        ValidationGroup="add" Display="None"></asp:RequiredFieldValidator>
        </td></tr>
				<!--<tr><td class="td_title"> 开始时间：</td>
        <td class="td_content">                        <asp:TextBox id="StartTime" runat="server"  OnClick="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd'})"
                        SkinID="date" title="选择日期"></asp:TextBox>
             <asp:RequiredFieldValidator ID="RequiredFieldValidator1" runat="server" ErrorMessage="开始时间不能为空!" ControlToValidate="StartTime"
                        ValidationGroup="add" Display="None"></asp:RequiredFieldValidator>
        </td></tr>
                <tr><td class="td_title"> 结束时间：</td>
        <td class="td_content">                        <asp:TextBox id="EndTime" runat="server"  OnClick="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd'})"
                        SkinID="date" title="选择日期"></asp:TextBox>
              <asp:RequiredFieldValidator ID="RequiredFieldValidator2" runat="server" ErrorMessage="结束时间不能为空!" ControlToValidate="EndTime"
                        ValidationGroup="add" Display="None"></asp:RequiredFieldValidator>
        </td></tr>
				<tr><td class="td_title"> 培训讲师：</td>
        <td class="td_content">                        <asp:TextBox id="txtC_Tearcher" runat="server" Width="200" Rows="4" TextMode="MultiLine"  ></asp:TextBox>
        </td></tr>
				<tr><td class="td_title"> 培训地点：</td>
        <td class="td_content">                        <asp:TextBox id="txtC_Adrees" runat="server" Width="200" Rows="4" TextMode="MultiLine"></asp:TextBox>
        </td></tr>
				<tr><td class="td_title"> 完成跳转地址：</td>
        <td class="td_content">                        <asp:TextBox id="txtC_ReturnUrl" runat="server" Width="200"></asp:TextBox>
        </td></tr>
              <tr><td class="td_title"> 是否验证：</td>
        <td class="td_content">                        <asp:CheckBox id="txtN_CodeSurvey" runat="server" ></asp:CheckBox>
        </td></tr>
            <tr><td class="td_title"> 课程调查码：</td>
        <td class="td_content">                        <asp:TextBox id="txtC_Code" runat="server" Width="100" ></asp:TextBox>
            
        </td></tr>
            <tr><td class="td_title"> <span class="red">◆</span> 类别：</td>
        <td class="td_content">   <asp:DropDownList ID="dropTypeId" runat="server">
                </asp:DropDownList>
                <asp:RangeValidator ID="rvdTypeId" runat="server" ErrorMessage="请选择类别" Display="None" 
                    ControlToValidate="dropTypeId" ValidationGroup="post" Type="Integer" MinimumValue="1" MaximumValue="99999999" ></asp:RangeValidator>
        </td></tr>
            <tr><td class="td_title"> 调查模版：</td>
        <td class="td_content">    
            <asp:TextBox id="txtSysvote" runat="server" Width="200" ></asp:TextBox>
             <asp:HiddenField id="hidSysvoteid" runat="server" Value="0" ></asp:HiddenField>
        </td></tr>-->
				</table>
    </div>
    <div id="h_bottom">
         <asp:Button ID="btnSave" OnClick="btnSave_Click" runat="server" Text="提交" class="btn"  ValidationGroup="add" >
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
