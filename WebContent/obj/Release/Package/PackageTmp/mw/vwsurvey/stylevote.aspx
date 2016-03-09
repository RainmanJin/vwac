<%@ Page Title="" Language="C#" MasterPageFile="~/mw/Default.Master" AutoEventWireup="true" CodeBehind="StyleVote.aspx.cs" Inherits="Plugin.VWSurvey.Admini.VWSurvey.StyleVote" %>
<asp:Content ID="Content1" ContentPlaceHolderID="head" runat="server">
</asp:Content>
<asp:Content ID="Content2" ContentPlaceHolderID="ContentPlaceHolder1" runat="server">
                      <div id="h_main">

        <table border="0" cellspacing="0" style="width: 100%;">
            <tr class="daohang_title">
                <td style="text-align: left; padding-left: 3px;">
                    列表↓ 
                </td>
            </tr>
            <tr>
                <td colspan="2">
                 <ctl:JRepeater ID="rpt_List" runat="server" >
                  <HeaderTemplate>
                    <table cellpadding="0" cellspacing="0" border="0" width="100%" id="tableData">
                        <tr>
                           
                             
                            <th>题目名称</th>
                         
                               <th>操作</th>
                        </tr>
            </HeaderTemplate>
                            <ItemTemplate>
                                <tr class="tr_data" onmouseout="mout(this);" onmouseover="mover(this);">
                                
                                    <td>
                                    <%# Eval("C_Title") %>
                                    </td>
                                    <td>
                                       <a href="javascript:void(0)" onclick="javascript:openDlg('Edit','编辑编辑生成页面','styleDetail.aspx?sysId=<%# Eval("N_SysId") %>',650,450,'');"> 编辑生成页面</a>
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
