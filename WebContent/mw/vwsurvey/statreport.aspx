<%@ Page Title="" Language="C#" MasterPageFile="~/mw/Default.Master" AutoEventWireup="true" CodeBehind="StatReport.aspx.cs" Inherits="Plugin.VWSurvey.Admini.VWSurvey.StatReport" %>
<asp:Content ID="Content1" ContentPlaceHolderID="head" runat="server">
    <style>*{ padding: 0px;}</style>
        <script type="text/javascript">
            function SetProgress(id) {
                var val = $("#l_" + id + " > div > span").html();
                $("#l_" + id + " > div").css("width", val + "%"); //控制#loading div宽度 
                $("#l_" + id + " > div").html(val + "%"); //显示百分比 
            }

    </script>
    <style>
#tableData .loading{ 
width:350px; 
height:15px;
border:1px solid #ccc; 
text-shadow: 0.5px 0.5px 1px #fff;
border-radius: 3px;
} 
#tableData .loading div{ 
width:0px; 
height:14px; background:#2696df;
text-shadow: 0.5px 0.5px 1px #fff;
border-radius: 3px;
color:#fff; 
text-align:center; 
font-family:Tahoma; 
font-size:14px; 
line-height:13px; 
}
    </style>
</asp:Content>
<asp:Content ID="Content2" ContentPlaceHolderID="ContentPlaceHolder1" runat="server">
    <div id="h_main">
        <table border="0" cellspacing="0" style="width: 100%;">
            <tr class="daohang_title">
                <td><div class="info">当前调查问卷有效样本数：<asp:Literal runat="server" ID="litNum"></asp:Literal></div></td>
                <td style="text-align: left; padding-left: 3px;">
                   <input class="btnlistOver" type="button" value="返回" onclick="goUrl('statvote.aspx')"></input>
                    
                </td>
            </tr>
            <tr>
                <td colspan="2">
                 <ctl:JRepeater ID="rpt_List" runat="server" OnItemDataBound="rpt_List_OnItemDataBound">
                  <HeaderTemplate>
                    <table cellpadding="0" cellspacing="0" border="0" width="100%" id="tableData">
                        <tr>
                            <th style="width: 45%;text-align: left;" >
                                调查名称</th>
                            <th >所占比例</th>
                        </tr>
            </HeaderTemplate>
                            <ItemTemplate>
                                <tr class="tr_data" onmouseout="mout(this);" onmouseover="mover(this);">
                                    <td style="text-align: left;">
                                       <%#Eval("sub") %>
                                    </td>
                                    <td>
                                        <asp:Literal runat="server" ID="litjd"></asp:Literal>
                                    </td> 
                                   </tr>
                            </ItemTemplate>
                        <FooterTemplate>
                </table>
            </FooterTemplate>
         </ctl:JRepeater>
                </td>
            </tr>
        </table>
       </div>

</asp:Content>
