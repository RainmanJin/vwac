<%@ Page Title="" Language="C#" MasterPageFile="~/mw/DefaultDetail.master" AutoEventWireup="true" CodeBehind="_VoteDialog.aspx.cs" Inherits="Plugin.VWSurvey.Admini.VWSurvey._VoteDialog" %>
<asp:Content ID="Content1" ContentPlaceHolderID="head" runat="server">
     <script type="text/javascript">
         function funGetValue(objUserName, objUserId) {
             getValue("hidCtl", "txtCtl", null, objUserId, objUserName, null, getQueryString("btnUniqueId") != null ? "btnUniqueId" : null);
         }
</script>

</asp:Content>
<asp:Content ID="Content2" ContentPlaceHolderID="ContentPlaceHolder1" runat="server">
    <div id="h_main">
        <table border="0" cellpadding="0" cellspacing="0" width="100%">
            <tr>
                <td>
                    <table border="0" cellpadding="0" cellspacing="0">
                        <tr>
                            <td>
                                调查模版名称：</td>
                            <td style="padding-left: 5px;">
                                <asp:TextBox ID="txtSearch" runat="server" Width="220px"></asp:TextBox></td>
                            <td style="padding-left: 5px;">
                                <asp:Button ID="btnSearch" runat="server" Text="搜 索" OnClick="btnSearch_Click" ValidationGroup="search" /><asp:ValidationSummary
                                    ID="ValidationSummary1" runat="server" ShowMessageBox="true" ShowSummary="false"
                                    ValidationGroup="search" />
                                &nbsp;
                            </td>
                        </tr>
                          
                    </table>
                </td>
            </tr>
            <tr>
                <td>
                <ctl:JRepeater ID="rptCustomer" runat="server">
                    <HeaderTemplate>
                    <table cellpadding="0" cellspacing="0" border="0" width="100%" id="tableData">
                        <tr>
                            
                            <th>
                               调查模版名称</th>
                        </tr>
                        <tr class="tr_data" onmouseout="mout(this);" onmouseover="mover(this);">
                                    <td>
                                        <a href="javascript:void(0)" onclick="funGetValue(' ',0)">
                                          <span style="color:red; font-weight: bold;"> 置空</span>
                                        </a>
                                    </td>
                                </tr>
                        </HeaderTemplate>
                            <ItemTemplate>
                                <tr class="tr_data" onmouseout="mout(this);" onmouseover="mover(this);">
                                    <td>
                                        <a href="javascript:void(0)" onclick="funGetValue('<%#Eval("C_Title")%>','<%#Eval("N_SysId")%>')">
                                            <%#Eval("C_Title")%>
                                        </a>
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
