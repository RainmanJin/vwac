<%@ Page Title="" Language="C#" MasterPageFile="~/mw/Default.Master" AutoEventWireup="true" CodeBehind="smslist.aspx.cs" Inherits="PortalWeb.mw.plan.smslist" %>
<asp:Content ID="Content1" ContentPlaceHolderID="head" runat="server">
</asp:Content>
<asp:Content ID="Content2" ContentPlaceHolderID="ContentPlaceHolder1" runat="server">
        <table border="0" cellspacing="0" style="width: 100%;" id="table1">
            
            
            <%--<tr class="daohang_title">
                <td style="text-align: left; padding-left: 3px;">
                    列表↓ 
                </td>
                <td style="text-align: right; padding-right: 3px;">
                    <input id="btnAdd" type="button" value="短信群发" class="btnlistOver" onclick="javascript: openDlg('Add', '新增短信群发', 'plan/smsDetail.aspx', 670, 450, '');" />

                    <asp:Button ID="lbtnDeletes" runat="server" Text="批量删除" CssClass="btnlistOver" OnClientClick="return confirm('确定删除选中的数据吗？')"
                        OnClick="lbtnDeletes_Click" /></td>
            </tr>--%>
            <td style="text-align: right; padding-right: 3px;">
                <a href="smsDetail.aspx"><input id="btnAdd" type="button" value="新增短信" class="btnlistOver"  /></a>
            </td>
            <tr>
                <td colspan="2">
                <table cellpadding="0" cellspacing="0" border="0" width="100%" id="tableData">
                        <tr>
                           <%-- <th>
                                <input id="cheList" type="checkbox" onclick="checkAll(this.form)" /></th>--%>
                            <th>
                                ID</th>
                            <th>
                                手机号</th>
                            <th>
                                状态</th>
                            <th>
                                内容</th>
                            <th style="width: 150px;">
                                创建日期</th>
                            <th style="width: 60px;">
                                操作</th>
                        </tr>
                 <ctl:JRepeater ID="rpt_List" runat="server" OnItemCommand="rpt_List_ItemCommand" >
                  <HeaderTemplate>
                    
                       </HeaderTemplate>
                            <ItemTemplate>
                                <tr class="tr_data" onmouseout="mout(this);" onmouseover="mover(this);">
                                    <%--<td>
                                        <input id="cheId" runat="server" value='<%#Eval("Id")%>' type="checkbox" /></td>--%>
                                    <td>
                                         <%# Eval("id")%>
                                    </td>
                                    <td>
                                   <%# Eval("Mobile")%>
                                    </td>
                                    <td>
                                     <%# GetMsgContent(Eval("Status").ToString())%>
                                    </td>
                                    <td>
                                       <%# GetContext(Eval("SendContent").ToString())%>
                                    </td>
                                   
                                    <td>
                                        <%# DataBinder.Eval(Container.DataItem, "SendTime", "{0:yyyy-MM-dd HH:mm}")%>
                                    </td>
                                    
                                    <td>
                                       <a href='SMSList.aspx?id=<%# Eval("ID") %>&type=reset&rurl=<%=Server.UrlEncode(rurl) %>' style="color:#192E32">重发</a>
                                        <asp:LinkButton ID="lbtnDelete" CommandName="lbtnDelete" CommandArgument='<%#Eval("Id")%>'
                                            title="删除" OnClientClick="return confirm('确定删除数据吗？')" runat="server">删除</asp:LinkButton>
                                    </td>
                                </tr>
                            </ItemTemplate>
                        <FooterTemplate>
             
            </FooterTemplate>
         </ctl:JRepeater>
                 </table>  </td>
            </tr>
        </table>
</asp:Content>
