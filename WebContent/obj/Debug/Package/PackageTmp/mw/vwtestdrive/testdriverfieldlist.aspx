<%@ Page Title="" Language="C#" MasterPageFile="~/mw/Default.Master" AutoEventWireup="true" CodeBehind="testdriverfieldlist.aspx.cs" Inherits="PortalWeb.mw.vwtestdrive.testdriverfieldlist" %>
<asp:Content ID="Content1" ContentPlaceHolderID="head" runat="server">
</asp:Content>
<asp:Content ID="Content2" ContentPlaceHolderID="ContentPlaceHolder1" runat="server">
        <div id="h_main">
        <table border="0" cellspacing="0" style="width: 100%;" id="table1">
            <tr class="daohang_title">
                <td style="text-align: right; padding-right: 3px;">
                    <input id="Button1" value="返回" type="button" class="btnlistOver" onclick="javascript: location.href = 'testdriverlist.aspx'" /> 
                    <input id="btnAdd" type="button" value="新 增" class="btnlistOver" onclick="javascript: location.href = 'testdriverfielddetail.aspx'" />
                    
                    <!--<asp:Button ID="lbtnDeletes" runat="server" Text="批量删除" CssClass="btnlistOver" OnClientClick="return confirm('确定删除选中的数据吗？')"
                        OnClick="lbtnDeletes_Click" />-->
                </td>
            </tr>
            <tr>
                <td colspan="2">
                 <ctl:JRepeater ID="rpt_List" runat="server" OnItemCommand="rpt_List_ItemCommand" OnItemDataBound="rpt_List_ItemDataBound">
                  <HeaderTemplate>
                    <table cellpadding="0" cellspacing="0" border="0" width="100%" id="tableData">
                        <tr>
                            <th>
                                字段名</th>
                            <th>
                                父级</th>
                            <th>
                                排序</th>
                            <th>
                                是否使用</th>
                            <th style="width: 120px;">
                                操作</th>
                        </tr>
                       </HeaderTemplate>
                            <ItemTemplate>
                                <tr class="tr_data" onmouseout="mout(this);" onmouseover="mover(this);">
                                    <td>
                                    <%#Eval("C_Name") %>
                                    </td>
                                    <td>
                                          <%#getname( Eval("ParentId"))%>
                                    </td>
                                    <td>
                                         <%# Eval("Orderby")%>
                                    </td>
                                    <td>
                                          <%# Eval("isUse").ToString()=="1"?"是":"否"%>
                                    </td>
                                    <td>
                                        <a href="testdriverfielddetail.aspx?Id=<%# Eval("Id") %>">编辑</a>
                                        <asp:Literal runat="server" ID="lit"></asp:Literal>  
                                        <asp:LinkButton ID="lbtnDelete" CommandName="lbtnDelete" CommandArgument='<%#Eval("Id")%>'
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
