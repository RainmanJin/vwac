<%@ Page Title="" Language="C#" MasterPageFile="~/mw/Default.Master" AutoEventWireup="true" CodeBehind="infodetail.aspx.cs" Inherits="PortalWeb.mw.InfoDetail" %>
<asp:Content ID="Content1" ContentPlaceHolderID="head" runat="server">
    <script type="text/javascript" src="<%=BaseUi.CtxPath %>/platform/xheditor/xheditor-1.1.14-zh-cn.min.js"></script>
    <script type="text/javascript" src="<%=BaseUi.JqueryPath %>/jquery.validate.js"></script>
</asp:Content>
<asp:Content ID="Content2" ContentPlaceHolderID="ContentPlaceHolder1" runat="server">
    <div id="h_main">
        <span class="red">◆</span> 表示必填字段</div>
    <div id="h_operate" style="padding-top: 5px;">
		<table border="0" cellpadding="0" cellspacing="1" bgcolor="#ffffff" width="100%" >
				
				<tr><td class="td_title"> <span class="red">◆</span> 标题：</td>
        <td class="td_content">                        <asp:TextBox id="txtTitle" runat="server" Width="200px" CssClass="required"></asp:TextBox>
        </td></tr>
				<tr><td class="td_title"> <span class="red">◆</span> 新闻类别：</td>
        <td class="td_content">   <asp:DropDownList ID="dropTypeId" runat="server">
                </asp:DropDownList>
          <%--      <asp:RangeValidator ID="rvdTypeId" runat="server" ErrorMessage="请选择类别" Display="None" 
                    ControlToValidate="dropTypeId" ValidationGroup="post" Type="Integer" MinimumValue="1" MaximumValue="99999999" ></asp:RangeValidator>--%>
        </td></tr>
				<%--<tr><td class="td_title"> 共享用户：</td>
        <td class="td_content">  <asp:TextBox ID="txtShareUsers" runat="server" SkinID="find" Width="514" Height="50" 
                    onclick='javascript:openDlg("EmploeeDialog","查找用户","Controls/__UserDialog.aspx?hidCtl=ctl00_cphContent_hidShareUsers&txtCtl=ctl00_cphContent_txtShareUsers","500px","auto","InfoDetail");'></asp:TextBox>
                <asp:HiddenField ID="hidShareUsers" runat="server" Value="0" />(默认为全部用户)
        </td></tr>--%>
            <tr><td class="td_title"> 跳转链接：</td>
        <td class="td_content">  <asp:TextBox ID="txtShareUsers" runat="server" SkinID="find" Width="80%"></asp:TextBox>
               如设置优先内容
        </td></tr>
				<tr><td class="td_title"> 封面图：</td>
        <td class="td_content">  <asp:FileUpload ID="fileFilePath" runat="server" CssClass="fUp" />&nbsp; <asp:Image runat="server" ID="imgPhoto" Height="25" Width="45"/>
        </td></tr>
				<tr><td class="td_title"> 排序：</td>
        <td class="td_content">                        <asp:TextBox id="txtSort" runat="server" onkeypress="isNum()" Width="60px">0</asp:TextBox>
        <asp:RangeValidator ID="rvdSort" runat="server" ErrorMessage="排序格式不正确" Display="None" 
                    ControlToValidate="txtSort" ValidationGroup="post" Type="Integer" MinimumValue="0" MaximumValue="99999999" ></asp:RangeValidator>
        </td></tr>
        <tr><td class="td_title"> 文章内容：</td>
        <td class="td_content">                        <asp:TextBox id="txtContents" runat="server" TextMode="MultiLine"  Width="95%" Height="340"></asp:TextBox>
        </td></tr>
        <%--<tr><td class="td_title"> 资源所属：</td>
        <td class="td_content">  <asp:TextBox ID="txtAidUsers" runat="server" SkinID="find"
                     ReadOnly="True"></asp:TextBox>
                <asp:HiddenField ID="hidAidUsers" runat="server" Value="0" />(默认为当前用户)
        </td></tr>--%>
        <tr><td class="td_title"> 附件：</td>
        <td class="td_content"> <asp:TextBox id="txtFj" runat="server"></asp:TextBox>
        &nbsp;附件格式：pdf,视频
       &nbsp;&nbsp; <iframe src="../ashx/_upload.aspx?filepath=/UpLoads/fj/&recievetxtname=<%=txtFj.ClientID %>&filetype=fj&filesize=409600"
                            scrolling="no" width="400px" frameborder="0" height="22"></iframe>
        </td></tr>
				</table>
    </div>
    <div id="h_bottom">
        <%--<asp:UpdatePanel ID="UpdatePanel2" runat="server">
            <contenttemplate>--%>
        <asp:Button ID="btnSave" OnClick="btnSave_Click" runat="server" Text="保 存" ValidationGroup="post" class="btn">
        </asp:Button>
        &nbsp;
        <input id="Button2" type="button" value="返 回" onclick="javascript: history.go(-1);" class="btn" /><asp:ValidationSummary
            ID="ValidationSummary1" runat="server" ShowMessageBox="true" ShowSummary="false"
            ValidationGroup="post" />
       <%-- </contenttemplate>
        </asp:UpdatePanel>--%>
    </div>
     <script>
         
         $(document).ready(function () {
             $("#<%=Master.FindControl("dofrm").ClientID%>").validate();
             $('#<%=txtContents.ClientID%>').xheditor({ upImgUrl: '../ashx/_upfile.aspx?type=1&filetype=image&filesize=1000&filepath=/upLoads/info/', upLinkUrl: '../ashx/_upfile.aspx?type=1&filetype=fj&filesize=500&filepath=/upLoads/info/' });
         });

     </script>
</asp:Content>
