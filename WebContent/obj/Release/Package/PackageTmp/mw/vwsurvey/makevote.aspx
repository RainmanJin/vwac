<%@ Page Title="" Language="C#" MasterPageFile="~/mw/Default.Master" AutoEventWireup="true" CodeBehind="MakeVote.aspx.cs" Inherits="Plugin.VWSurvey.Admini.VWSurvey.MakeVote" %>

<asp:Content ID="Content1" ContentPlaceHolderID="head" runat="server">
</asp:Content>
<asp:Content ID="Content2" ContentPlaceHolderID="ContentPlaceHolder1" runat="server">
    <div id="h_main">
        <table border="0" cellspacing="0" style="width: 100%;">
          <!-- 
        <tr class="daohang_title">
                <td style="text-align: left; padding-left: 3px;">
                    搜索条件↓
                </td>
                <td style="text-align: right; padding-right: 3px;">
                </td>
            </tr>
            <tr>
                <td colspan="2" style="padding-top: 5px;">
                    <table border="0" cellpadding="0" cellspacing="1" style="margin-bottom: 15px;">
                                <tr>
                                <td class="td_title">
                                搜索关键字：</td>
                            <td class="td_content">
                                <asp:TextBox runat="server" ID="txtSearchName" Style="width: 318px"></asp:TextBox>
                                <span class="bak">(名称，关键字，说明)</span></td>
                        </tr>
                        <tr>
                            <td colspan="2" style="text-align: left; padding-top: 5px;">
                                <asp:Button ID="btnSearch" runat="server" Text="搜 索" CssClass="btnlistOver" OnClick="btnSearch_Click"
                                    OnClientClick="javascript:funbtnClick('#ctl00_cphContent_btnSearch');"></asp:Button>
                                </td>
                        </tr>
                         </table>
                </td>
            </tr>-->
            <tr class="daohang_title">
                <td style="text-align: left; padding-left: 3px;">
                    列表↓ 
                </td>
                <td style="text-align: right; padding-right: 3px;">
                    
                    
                    <input id="btnAdd" type="button" value="新 增" class="btnlistOver" onclick="goUrl('makevotedetail.aspx')" />
                    <%--<asp:Button ID="lbtnDeletes" runat="server" Text="批量删除" CssClass="btnlistOver" OnClientClick="return confirm('确定删除选中的数据吗？')"
                        OnClick="lbtnDeletes_Click" />--%>
                </td>
            </tr>
            <tr>
                <td colspan="2">
                 <ctl:JRepeater ID="rpt_List" runat="server" OnItemCommand="rpt_List_ItemCommand" OnItemDataBound="rpt_List_ItemDataBound" >
                  <HeaderTemplate>
                    <table cellpadding="0" cellspacing="0" border="0" width="100%" id="tableData">
                        <tr>
                          <th> <%--<input id="cheList" type="checkbox" onclick="checkAll(this.form)" />--%></th>

                            <th>调查名称</th>
                            <th style="width: 260px;">操作</th>
                        </tr>
                       </HeaderTemplate>
                            <ItemTemplate>
                                <tr class="tr_data" onmouseout="mout(this);" onmouseover="mover(this);">
                                    <td>
                                      <%--  <input id="cheId" runat="server" value='<%#Eval("N_SysId")%>' type="checkbox" />--%></td>
                                    <td>
                                          <%# Eval("C_Title") %>
                                    </td>
                                   
                                    <td>
                                        <a class="smallbutton" href="voteunit.aspx?SysId=<%#Eval("N_SysId") %>">调查单元</a> 
                                         <a class="smallbutton" href="biceitem.aspx?SysId=<%#Eval("N_SysId") %>">调查选项</a> 
                                        <asp:LinkButton ID="lbtnCopy" Visible="False" CommandName="lbtnCopy" CommandArgument='<%#Eval("N_SysId")%>'
                                            title="复制" OnClientClick="return confirm('确定复制投票项目吗？')" runat="server" CssClass="smallbutton">复制</asp:LinkButton>
                                       <asp:Literal runat="server" ID="lit"></asp:Literal>
                                        <a href="makevotedetail.aspx?Id=<%#Eval("N_SysId")%>"> 编辑</a>
                                        
                                        <asp:LinkButton  ID="lbtnDelete"  CommandName="lbtnDelete" CommandArgument='<%#Eval("N_SysId")%>'
                                            title="删除" OnClientClick="return confirm('确定删除数据吗？')" runat="server">删除</asp:LinkButton>

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
