<%@ Page Title="" Language="C#" MasterPageFile="~/mw/Default.Master" AutoEventWireup="true" CodeBehind="BiceOption.aspx.cs"  Inherits="Plugin.VWSurvey.Admini.VWSurvey.BiceOption" %>
<asp:Content ID="Content1" ContentPlaceHolderID="head" runat="server">
    <script type="text/javascript" src="<%=BaseUi.JqueryPath %>/jquery.validate.js"></script>
</asp:Content>
<asp:Content ID="Content2" ContentPlaceHolderID="ContentPlaceHolder1" runat="server">
    <div id="h_operate" style="padding-top: 5px;">
		<table border="0" cellpadding="0" cellspacing="1" bgcolor="#ffffff" width="100%">
		      
				<tr><td class="td_title"><span class="red">◆</span> 培训调查名称：</td>
        <td class="td_content">                        <asp:TextBox id="txtC_Title" runat="server" Width="500" Rows="4" TextMode="MultiLine" CssClass="required"></asp:TextBox>
             
        </td></tr>

				<tr><td class="td_title"><span class="red">◆</span> 开始时间：</td>
        <td class="td_content">                        <asp:TextBox id="StartTime" runat="server"  OnClick="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd'})"
                        SkinID="date" title="选择日期" CssClass="required"></asp:TextBox>
             <asp:RequiredFieldValidator ID="RequiredFieldValidator1" runat="server" ErrorMessage="开始时间不能为空!" ControlToValidate="StartTime"
                        ValidationGroup="add" Display="None"></asp:RequiredFieldValidator>
        </td></tr>
                <tr><td class="td_title"><span class="red">◆</span> 结束时间：</td>
        <td class="td_content">                        <asp:TextBox id="EndTime" runat="server"  OnClick="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd'})"
                        SkinID="date" title="选择日期" CssClass="required"></asp:TextBox>
              <asp:RequiredFieldValidator ID="RequiredFieldValidator2" runat="server" ErrorMessage="结束时间不能为空!" ControlToValidate="EndTime"
                        ValidationGroup="add" Display="None"></asp:RequiredFieldValidator>
        </td></tr>
            <tr><td class="td_title"><span class="red">◆</span> 培训课程名称：</td>
        <td class="td_content">                        <asp:TextBox id="txtC_course" runat="server" Width="500" Rows="4" TextMode="MultiLine" CssClass="required"></asp:TextBox>
            <asp:HiddenField ID="hideC_Course" runat="Server" Value="0" />
            <a href="javascript:"  onclick="javascript:modify1('查找课程','coursedetail.aspx?hidCtl=<%=hideC_Course.ClientID %>&amp;txtCtl=<%=txtC_course.ClientID %>',530);">选择</a>
             <asp:RequiredFieldValidator ID="RequiredFieldValidator6" runat="server" ErrorMessage="名称不能为空!" ControlToValidate="txtC_course"
                        ValidationGroup="add" Display="None"></asp:RequiredFieldValidator>
        </td></tr>
				<tr><td class="td_title"><span class="red">◆</span> 培训讲师：</td>
        <td class="td_content">                        <asp:TextBox id="txtC_Tearcher" runat="server" Width="200" Rows="4" TextMode="MultiLine" CssClass="required" ></asp:TextBox>
            <asp:HiddenField ID="hideC_Teacher" runat="Server" Value="0" />
             <a href="javascript:"  onclick="javascript:modify1('查找老师','_userdialog.aspx?hidCtl=<%=hideC_Teacher.ClientID %>&amp;txtCtl=<%=txtC_Tearcher.ClientID %>',530);">选择</a>
          
            <asp:RequiredFieldValidator ID="RequiredFieldValidator3" runat="server" ErrorMessage="培训讲师不能为空!" ControlToValidate="txtC_Tearcher"
                        ValidationGroup="add" Display="None"></asp:RequiredFieldValidator>
        </td></tr>
				<tr><td class="td_title"><span class="red">◆</span> 培训地点：</td>
        <td class="td_content">                        <asp:TextBox id="txtC_Adrees" runat="server" Width="200" Rows="4" TextMode="MultiLine" CssClass="required"></asp:TextBox>
 <asp:RequiredFieldValidator ID="RequiredFieldValidator5" runat="server" ErrorMessage="培训地点不能为空!" ControlToValidate="txtC_Adrees"
                        ValidationGroup="add" Display="None"></asp:RequiredFieldValidator>
        </td></tr>
            <tr><td class="td_title"> 培训人数：</td>
        <td class="td_content">                        <asp:TextBox id="txtpxnum" runat="server" Width="150"></asp:TextBox>
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
            <asp:DropDownList runat="server" ID="drpVoteTmp"/>
              </td></tr>
				</table>
    </div>
    <div id="h_bottom">
       <asp:Button ID="btnSave" OnClick="btnSave_Click" runat="server" Text="提交" class="btn"  ValidationGroup="add" >
        </asp:Button>
        
        &nbsp;
        <input id="Button2" type="button" value="返回" onclick="goUrl()" class="btn" /><asp:ValidationSummary
            ID="ValidationSummary1" runat="server" ShowMessageBox="true" ShowSummary="false"
            ValidationGroup="add" />
    </div>
    <script>
        function modify1(name, url, width) {
            $.layer({
                type: 2,
                border: [0],
                title: false,
                shadeClose: true,
                closeBtn: false,
                iframe: { src: url },
                area: ['550px', '400px']
            });
        }
        $(document).ready(function () {
            $("#<%=Master.FindControl("dofrm").ClientID%>").validate();
           
        });
    </script>
</asp:Content>
