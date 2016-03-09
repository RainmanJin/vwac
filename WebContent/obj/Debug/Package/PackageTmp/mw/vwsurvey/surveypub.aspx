<%@ Page Title="" Language="C#" MasterPageFile="~/mw/DefaultDetail.Master" AutoEventWireup="true" CodeBehind="SurveyPub.aspx.cs" Inherits="Plugin.VWSurvey.Admini.VWSurvey.SurveyPub" %>

<asp:Content ID="Content1" ContentPlaceHolderID="head" runat="server">
    <script>
        var index = parent.layer.getFrameIndex(window.name); //获取当前窗体索引
        function gb() {

            parent.layer.close(index);

        }
    </script>
</asp:Content>
<asp:Content ID="Content2" ContentPlaceHolderID="ContentPlaceHolder1" runat="server">
    <div id="h_show">
        <table border="0" cellpadding="0" cellspacing="1" bgcolor="#ffffff" width="100%">
            <tr>
                <td class="td_title">互连网发布地址
                    <br/>(请复制使用)：</td>
                <td class="td_content">
                   <asp:TextBox id="txtUrl" runat="server" Width="90%" ></asp:TextBox>
                </td>
                
            </tr>
            <tr>
                <td class="td_title">二维码发布<br/>(请另存图片)：</td>
                <td class="td_content">
                   <asp:Image ID="Image1" runat="server" Height="50px" Width="50px" />
                </td>
            </tr>
            
            <tr>
                <td class="td_title">站内通行<br/>投票代码<br/>(请复制使用)：</td>
                <td class="td_content">
                    <asp:TextBox ID="txtContent" runat="server" Width="98%" TextMode="MultiLine" Height="350px"></asp:TextBox>
                </td>
            </tr>
        </table>
    </div>
    <div id="h_bottom">
        <input id="Button1" type="button" value="关 闭" onclick="gb()" class="btn" />
    </div>

</asp:Content>
