<%@ Page Title="" Language="C#" MasterPageFile="~/mw/Default.Master" AutoEventWireup="true" CodeBehind="sktitemlist.aspx.cs" Inherits="PortalWeb.mw.retailknowhow.sktitemlist" %>
<%@ Register TagPrefix="ctl" Namespace="PortalControls" Assembly="PortalControls" %>
<asp:Content ID="Content1" ContentPlaceHolderID="head" runat="server">
</asp:Content>
<asp:Content ID="Content2" ContentPlaceHolderID="ContentPlaceHolder1" runat="server">
        <table border="0" cellspacing="0" style="width: 100%;" id="table1">


            <tr class="daohang_title">
                <td style="text-align: right; padding-right: 3px;">
                    <input id="Button2" type="button" value="返 回" class="btnlistOver" onclick="javascript:goUrl('sktchapterlist.aspx?id=<%=CId %>');"/>
                    <input id="Button1" type="button" value="新 增" class="btnlistOver" onclick="javascript:location.href = 'sktitemdetali.aspx?type=<%=Type %>&CId=<%=CId%>&chapterid=<%=Id%>'"/>
                </td>
                
            </tr>
            <tr>
                <td colspan="2">
                 <ctl:JRepeater ID="rpt_List" runat="server" OnItemCommand="rpt_List_ItemCommand" OnItemDataBound="rpt_List_ItemDataBound">
                  <HeaderTemplate>
                    <table cellpadding="0" cellspacing="0" border="0" width="100%" id="tableData">
                        <tr>
                            <th>
                                序号</th>
                            <th>
                                内容</th>
                            <th>
                                颜色</th>
                            <th>
                                样式</th>
                            <th>
                                操作</th>
                        </tr>
                       </HeaderTemplate>
                            <ItemTemplate>
                                <tr class="tr_data" onmouseout="mout(this);" onmouseover="mover(this);">
                                    <td>
                                         <%# Eval("Id")%>
                                    </td>
                                    <td>
                                    <%#Eval("C_Name") %>
                                    </td>
                                    <td>
                                          <%#Character(Convert.ToInt32(Eval("C_Color")))%>
                                    </td>
                                    <td>
                                          <%#_Type(Convert.ToInt32(Eval("C_Type")))%>
                                    </td>
                                    <td>
                                        <asp:Panel runat="server" ID="panAdd" style="display: inline;" Visible="False">
                                        <a href="sktitemdetali.aspx?dis=<%#Eval("C_dimension") %>&type=<%=Type %>&CId=<%=CId%>&chapterid=<%=Id%>&parentid=<%# Eval("Id") %>" >
                                            添加子项</a>

                                        </asp:Panel>
                                        <a href="sktitemdetali.aspx?dis=<%#Eval("C_dimension") %>&type=<%=Type %>&CId=<%=CId%>&chapterid=<%=Id%>&Id=<%# Eval("Id") %>" >
                                            编辑</a>
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
