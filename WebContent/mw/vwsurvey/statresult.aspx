<%@ Page Title="" Language="C#" MasterPageFile="~/mw/Default.Master" AutoEventWireup="true" CodeBehind="statresult.aspx.cs" Inherits="Plugin.VWSurvey.Admini.VWSurvey.statresult" %>
<asp:Content ID="Content1" ContentPlaceHolderID="head" runat="server">
    <style>*{ padding: 0px;}</style>
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
                <ctl:JRepeater ID="rpt_List" runat="server" >
                  <HeaderTemplate>
                    <table cellpadding="0" cellspacing="0" border="0" width="100%" id="tableData">
                        <tr>
                            <th style="width: 70%;text-align: left;" >
                                调查名称</th>
                            <th  style="width: 100px;">票数</th>
                          <%--  <th  style="width: 100px;">平均</th>--%>
                        </tr>
            </HeaderTemplate>
                            <ItemTemplate>
                                <tr class="tr_data" onmouseout="mout(this);" onmouseover="mover(this);">
                                
                                    <td style="text-align: left;">
                                       <%#Eval("sub") %>
                                    </td>
                                    <td>
                                         <%# Eval("tp")%>
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
