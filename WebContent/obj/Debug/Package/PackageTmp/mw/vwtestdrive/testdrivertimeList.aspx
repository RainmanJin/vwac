<%@ Page Title="" Language="C#" MasterPageFile="~/mw/Default.Master" AutoEventWireup="true" CodeBehind="testdrivertimeList.aspx.cs" Inherits="PortalWeb.mw.vwtestdrive.testdrivertimeList" %>
<asp:Content ID="Content1" ContentPlaceHolderID="head" runat="server">
</asp:Content>
<asp:Content ID="Content2" ContentPlaceHolderID="ContentPlaceHolder1" runat="server">
        <table border="0" cellspacing="0" style="width: 100%;" id="table1">
         
            <tr>
                <td colspan="2">
                 <ctl:JRepeater ID="rpt_List" runat="server" OnItemCommand="rpt_List_ItemCommand" >
                  <HeaderTemplate>
                    <table cellpadding="0" cellspacing="0" border="0" width="100%" id="tableData">
                        <tr>
                            <th>
                                名称</th>
                            <th>
                                车型</th>
                            <th>
                                学员</th>
                            <th>
                                场地</th>
                            <th>
                                时间</th>
                            <th style="width: 60px;">
                                操作</th>
                        </tr>
                       </HeaderTemplate>
                            <ItemTemplate>
                                <tr class="tr_data" onmouseout="mout(this);" onmouseover="mover(this);">
                                    <td>
                                         <%# Eval("C_Course")%>
                                    </td>
                                    <td>
                                         <%#Eval("Chexi") %>
                                    </td>
                                    <td>
                                         <%# Eval("Student")%>
                                    </td>
                                    <td>
                                         <%# Eval("Changdi")%>
                                    </td>
                                    <td>
                                         <%# DataBinder.Eval(Container.DataItem, "CreateTime", "{0:yyyy-MM-dd}")%>
                                    </td>
                                    <td>

                                        <a href="testdrivertimedetail.aspx?Id=<%# Eval("Id") %>" >查看</a>
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
