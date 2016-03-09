<%@ Page Title="" Language="C#" MasterPageFile="~/mw/DefaultDetail.Master" AutoEventWireup="true" CodeBehind="StyleDetail.aspx.cs" Inherits="Plugin.VWSurvey.Admini.VWSurvey.StyleDetail" %>
<asp:Content ID="Content1" ContentPlaceHolderID="head" runat="server">
    <script src="<%=BaseUi.CtxPath %>/platform//xheditor/xheditor-1.1.14-zh-cn.min.js" type="text/javascript"></script>
</asp:Content>
<asp:Content ID="Content2" ContentPlaceHolderID="ContentPlaceHolder1" runat="server">
     <div class="main">
           <div class="headtitle"> &nbsp;<span class="BannerFont">编辑调查页面</span> </div>
        <div id="h_operate" style="padding-top: 5px;">
		<table border="0" cellpadding="0" cellspacing="1" bgcolor="#ffffff" width="100%">
		   <tr>
          <td colspan="2" align="center" valign="top" bgcolor="#f4f4f4">
             （ 页面编辑后，请勿在生成）<br/>
          <asp:Label ID="Label3" runat="server" BorderStyle="Solid" BorderWidth="1px"></asp:Label>

          <asp:TextBox ID="TextBox1" TextMode="MultiLine" runat="server"  Width="95%" Height="280"></asp:TextBox>
              <!--CssClass="xheditor {sourceMode:true,inlineScript:true,linkTag:true,upImgUrl:'/ashx/_upfile.aspx?type=1&filetype=image&filesize=100&filepath=/UpLoads/'}"-->
              <asp:HiddenField runat="server" ID="hidid" Value="0"/>
            <br />
              <asp:Button ID="btnSave" OnClick="btnSave_Click" runat="server" Text="保 存" ValidationGroup="add" class="btn">
        </asp:Button>  <!-- &nbsp;<input id="Button1" type="button" value="关 闭" onclick="closeDlg()" class="btn" />
           -->    </td>
        </tr>
      </table>

    </div>
   </div>
</asp:Content>
