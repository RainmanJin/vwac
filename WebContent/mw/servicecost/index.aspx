<%@ Page Title="" Language="C#" MasterPageFile="~/mw/Default.Master" AutoEventWireup="true" CodeBehind="index.aspx.cs" Inherits="PortalWeb.mw.servicecost.index" %>
<%@ Register TagPrefix="ctl" Namespace="PortalControls" Assembly="PortalControls" %>
<asp:Content ID="Content1" ContentPlaceHolderID="head" runat="server">
</asp:Content>
<asp:Content ID="Content2" ContentPlaceHolderID="ContentPlaceHolder1" runat="server">
   
        <div id="h_main">
        <table border="0" cellspacing="0" style="width: 100%;">
            <tr class="daohang_title">
               
                <td style="text-align: right; padding-right: 3px;">
                </td>
            </tr>
           <!--    <tr>
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
            
            <tr>
                <td colspan="2">
                 <ctl:JRepeater ID="rpt_List" runat="server" OnItemCommand="rpt_List_ItemCommand" >
                  <HeaderTemplate>
                    <table cellpadding="0" cellspacing="0" border="0" width="100%" id="tableData">
                        <tr>
                            <th>
                                ID</th>
                            <th>
                                项目名称</th>
                            <th style="width: 120px;">
                                创建日期</th>
                            <th style="width: 60px;">
                                操作</th>
                        </tr>
                       </HeaderTemplate>
                            <ItemTemplate>
                                <tr class="tr_data" onmouseout="mout(this);" onmouseover="mover(this);">
                                    <td>
                                         <%# Eval("id")%>
                                    </td>
                                    <td>
                                    <%#Eval("A3") %>
                                    </td>
                                    <td>
                                        <%# DataBinder.Eval(Container.DataItem, "CreateTime", "{0:yyyy-MM-dd HH:mm}")%>
                                    </td>
                                    
                                    <td>
                                       <%-- <a href="javascript:void(0)" onclick="javascript:openDlg('Edit','编辑','Detail.aspx?Id=<%# Eval("Id") %>',650,450,'');">
                                            编辑</a>--%>
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
