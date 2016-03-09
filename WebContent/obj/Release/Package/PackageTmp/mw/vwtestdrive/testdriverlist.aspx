<%@ Page Title="" Language="C#" MasterPageFile="~/mw/Default.Master" AutoEventWireup="true" CodeBehind="testdriverlist.aspx.cs" Inherits="PortalWeb.mw.vwtestdrive.testdriverlist" %>
<asp:Content ID="Content1" ContentPlaceHolderID="head" runat="server">
</asp:Content>
<asp:Content ID="Content2" ContentPlaceHolderID="ContentPlaceHolder1" runat="server">
        <table border="0" cellspacing="0" style="width: 100%;" id="table1">
            <tr class="daohang_title">
                <td style="text-align: right; padding-right: 3px;">
                    <input id="Button1" type="button" value="字段管理" class="btnlistOver" onclick="javascript: location.href = 'testdriverfieldlist.aspx'" />
                    <input id="btnAdd" type="button" value="新 增" class="btnlistOver" onclick="javascript: location.href = 'testdriverdetail.aspx'" />
                  <!--  <asp:Button ID="lbtnDeletes" runat="server" Text="批量删除" CssClass="btnlistOver" OnClientClick="return confirm('确定删除选中的数据吗？')"
                        OnClick="lbtnDeletes_Click" />-->
                </td>

            </tr>
            <tr>
                <td colspan="2">
                 <ctl:JRepeater ID="rpt_List" runat="server" OnItemCommand="rpt_List_ItemCommand" >
                  <HeaderTemplate>
                    <table cellpadding="0" cellspacing="0" border="0" width="100%" id="tableData">
                        <tr>
                           
                            <th>
                                名称</th>
                            <th>
                                字段配置</th>
                            <th>
                                操作</th>
                           
                        </tr>
                       </HeaderTemplate>
                            <ItemTemplate>
                                <tr class="tr_data" onmouseout="mout(this);" onmouseover="mover(this);">
                                    
                                    <td>
                                         <%# Eval("C_Name")%>
                                    </td>
                                    <td>
                                         <%#getname(Eval("Id"),Eval ("Changdi"))%>
                                    </td>
                                    <td>
                                        <a href="testdriverdetail.aspx?Id=<%# Eval("Id") %>">编辑</a>
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
</asp:Content>
