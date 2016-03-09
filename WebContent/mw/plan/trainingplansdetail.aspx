<%@ Page Title="" Language="C#" MasterPageFile="~/mw/Default.Master" AutoEventWireup="true" CodeBehind="TrainingPlansDetail.aspx.cs" Inherits="PortalWeb.mw.plan.TrainingPlansDetail" %>
<asp:Content ID="Content1" ContentPlaceHolderID="head" runat="server">
</asp:Content>
<asp:Content ID="Content2" ContentPlaceHolderID="ContentPlaceHolder1" runat="server">
    <div id="h_main">
        <span class="red">◆</span> 表示必填字段</div>
    <div id="h_operate" style="padding-top: 5px;">
        <table border="0" cellpadding="0" cellspacing="1" bgcolor="#ffffff" width="100%">
            <tr>
                <td class="td_title">
                    <span class="red">◆</span>计划类型：
                </td>
                <td class="td_content">
                   <asp:DropDownList ID="dropTypeId" runat="Server"></asp:DropDownList>
                </td>
            </tr>
            <tr>
                <td class="td_title">
                   <span class="red">◆</span> 计划名称：
                </td>
                <td class="td_content">
                    <asp:TextBox ID="txtPlanName" runat="server" MaxLength="150"></asp:TextBox>
                    <asp:RequiredFieldValidator ID="RequiredFieldValidator1" runat="server" ErrorMessage="计划名称!" ControlToValidate="txtPlanName"
                        ValidationGroup="add" Display="None"></asp:RequiredFieldValidator>
                </td>
            </tr>
            <tr>
                <td class="td_title">
                    计划描述：
                </td>
                <td class="td_content">
                    <asp:TextBox ID="txtDescrip" runat="server" Width="90%" TextMode="MultiLine" Height="150" CssClass="xheditor {tools:'Removeformat,|,Source'}" onkeydown="limitChars('ctl00_cphContent_txtDescrip', 200)" onchange="limitChars('ctl00_cphContent_txtDescrip', 200)" onpropertychange="limitChars('ctl00_cphContent_txtDescrip', 200)" ></asp:TextBox>
                    <br/>200字以内
                </td>
            </tr>
            <tr>
                <td class="td_title">
                  <span class="red">◆</span>  开始时间：
                </td>
                <td class="td_content">
                    <asp:TextBox ID="txtBeginTime" runat="server" OnClick="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd'})"
                        SkinID="date" title="选择日期"></asp:TextBox>
                          <asp:RequiredFieldValidator ID="RequiredFieldValidator2" runat="server" ErrorMessage="开始时间!" ControlToValidate="txtBeginTime"
                        ValidationGroup="add" Display="None"></asp:RequiredFieldValidator>
                </td>
            </tr>
            <tr>
                <td class="td_title">
                  <span class="red">◆</span>  结束时间：
                </td>
                <td class="td_content">
                    <asp:TextBox ID="txtEndTime" runat="server" OnClick="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd'})"
                        SkinID="date" title="选择日期"></asp:TextBox>
                          <asp:RequiredFieldValidator ID="RequiredFieldValidator3" runat="server" ErrorMessage="结束时间!" ControlToValidate="txtEndTime"
                        ValidationGroup="add" Display="None"></asp:RequiredFieldValidator>
                </td>
            </tr>
            <tr>
                <td class="td_title">
                    使用状态：
                </td>
                <td class="td_content">
                   <asp:RadioButtonList ID="rbltStatus" runat="server" RepeatDirection="Horizontal">
                        <asp:ListItem Value="0" Selected="True">计划中</asp:ListItem>
                       <asp:ListItem Value="1" >已确认</asp:ListItem>
                        <asp:ListItem Value="2">停用</asp:ListItem>
                    </asp:RadioButtonList>
                </td>
            </tr>
            <tr>
                <td class="td_title">
                   <span class="red">◆</span> 封面：
                </td>
                <td class="td_content">
                   <asp:FileUpload ID="FileUpload1" runat="server" CssClass="fUp" style="width: 400px;" />&nbsp;&nbsp;
                    <asp:Image runat="server" ID="imgPhoto" Height="25" Width="25"/>
                    <br/>(建议尺寸：170*150）
                </td>
            </tr>
          <!--  <tr>
                <td class="td_title">
                    属性：
                </td>
                <td class="td_content">
                    <asp:CheckBox ID="chkIsTop" Text="置顶" runat="server" Checked="False" />  <asp:CheckBox ID="chkIsRecommend" Text="推荐" runat="server" Checked="False" />
                </td>
            </tr>-->
            <tr>
                <td class="td_title">
                    参加学员：
                </td>
                <td class="td_content">
                     <asp:TextBox ID="txtUsers" runat="server" Height="80px" Width="410px" TextMode="MultiLine" ReadOnly="True"  SkinID="find"></asp:TextBox>
                    <asp:HiddenField ID="hidUsersId" runat="Server" Value="0" /><%-- <span>为空为所有用户</span>--%>
                </td>
            </tr>
            <tr>
                <td class="td_title">
                    培训老师：
                </td>
                <td class="td_content">
                     <asp:TextBox ID="txtTearcher" runat="server" Height="40px" Width="410px" TextMode="MultiLine" ReadOnly="True"  SkinID="find"></asp:TextBox>
                    <asp:HiddenField ID="hidtearcherid" runat="Server" Value="0" /><%-- <span>为空为所有用户</span>--%>
                </td>
            </tr>
            <tr>
                <td class="td_title">
                    <span class="red">◆</span> 培训地址：
                </td>
                 <td class="td_content" style="padding-left:8px;"> 
                <asp:TextBox ID="txtAddress" runat="server" MaxLength="150"></asp:TextBox>
                </td>
            </tr>
            <tr>
                <td class="td_title">
                    <span class="red">◆</span> 课程码：
                </td>
                 <td class="td_content" style="padding-left:8px;"> 
                <asp:TextBox ID="txtCode" runat="server" MaxLength="150"></asp:TextBox>
                     <asp:HiddenField ID="hidetxtCode" runat="Server" Value="0" />
                </td>
            </tr>
            <tr>
                <td class="td_title">
                    <span class="red">◆</span> 培训人数：
                </td>
                 <td class="td_content" style="padding-left:8px;"> 
                <asp:TextBox ID="txtApplyNum" runat="server" MaxLength="20"></asp:TextBox>
                </td>
            </tr>
        </table>
    </div>
    <div id="h_bottom">
       <%-- <asp:UpdatePanel ID="UpdatePanel2" runat="server">
            <contenttemplate>--%>
        <asp:Button ID="btnSave" OnClick="btnSave_Click" runat="server" Text="保 存" ValidationGroup="add"  OnClientClick ="return chkSumbit();" class="btn">
        </asp:Button>
        &nbsp;
        <input id="Button2" type="button" value="返 回" onclick="javascript: history.go(-1);" class="btn"  /><asp:ValidationSummary
            ID="ValidationSummary1" runat="server" ShowMessageBox="true" ShowSummary="false"
            ValidationGroup="add" />
       <%-- </contenttemplate>
        </asp:UpdatePanel>--%>
    </div>
     <script type="text/javascript">
         var eanum = '<%=eanum %>';
         $(document).ready(function () {

         });

         function chkSumbit() {
             if ($("#<%=txtPlanName.ClientID %>").val() == "") {
                 alert('请输入标题！');
                 $("#<%=txtPlanName.ClientID %>").focus();
                 return false;
             }
             if ($("#<%=txtBeginTime.ClientID %>").val() == "") {
                 alert('请选择开始时间！');
                 $("#<%=txtBeginTime.ClientID %>").focus();
                 return false;
             }
             if ($("#<%=txtEndTime.ClientID %>").val() == "") {
                 alert('请输入结束时间！');
                 $("#<%=txtEndTime.ClientID %>").focus();
                 return false;
             }

             // alert($("#" + id).val());
             return true;
         }

    </script>
</asp:Content>
