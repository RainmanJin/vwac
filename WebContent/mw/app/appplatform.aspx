<%@ Page Title="" Language="C#" MasterPageFile="~/mw/Default.Master" AutoEventWireup="true" CodeBehind="appplatform.aspx.cs" Inherits="PortalWeb.mw.app.appplatform" %>
<asp:Content ID="Content1" ContentPlaceHolderID="head" runat="server">
</asp:Content>
<asp:Content ID="Content2" ContentPlaceHolderID="ContentPlaceHolder1" runat="server">
    <div id="h_main">
        <table border="0" cellspacing="0" style="width: 100%;">
            <tr class="daohang_title">
                <td style="text-align: left; padding-left: 3px;">
                    搜索条件↓
                </td>
            </tr>
            <tr>
                <td  style="padding-top: 5px;">
                                名称：
                                <asp:TextBox runat="server" ID="txtSearchName" Style="width: 150px"></asp:TextBox>
                               &nbsp; &nbsp;品牌：
                    <asp:dropdownlist id="txtbrand" runat="server">
                    </asp:dropdownlist>
               &nbsp;&nbsp;领域：
                    <asp:dropdownlist id="txtproType" runat="server">
                    </asp:dropdownlist>
                                    &nbsp;&nbsp;
                                <asp:Button ID="btnSearch" runat="server" Text="搜 索" CssClass="btnlistOver" OnClick="btnSearch_Click"></asp:Button>        
                </td>
            </tr>
            <tr >
                <td style="text-align: right; padding-right: 3px;" >
                     </td>
            </tr>
            <tr class="daohang_title">
                <td style="text-align: right; padding-right: 3px;" >
                   <input id="Button1" type="button" value="新 增" class="btnlistOver" onclick="goUrl('appplatformdetail.aspx')" />
                    </td>
            </tr>
            <tr>
                <td >
                 <ctl:JRepeater ID="rpt_List" runat="server" OnItemCommand="rpt_List_ItemCommand" >
                  <HeaderTemplate>
                    <table cellpadding="0" cellspacing="0" border="0" width="100%" id="tableData">
                        <tr>
                            <th>
                                名称</th>
                            <th>
                                领域</th>
                            <th>
                                版本</th>
                             <th>
                                状态</th>
                            <th>
                                更新时间</th>
                            <th style="width: 60px;">
                                操作</th>
                        </tr>
                       </HeaderTemplate>
                            <ItemTemplate>
                                <tr class="tr_data" onmouseout="mout(this);" onmouseover="mover(this);">
                                    <td>
                                          <%# Eval("name")%>
                                    </td>
                                    <td>
                                        <%#GetType(Eval("proType"))%>
                                    </td>
                                    <td>
                                         <%# Eval("version")%>
                                    </td>
                                    <td>
                                          <%# Eval("status").ToString()=="1"?"启用":"停用"%>
                                    </td>
                                    <td>
                                        <%# DataBinder.Eval(Container.DataItem, "lastUpdateTime", "{0:yyyy-MM-dd HH:mm}")%>
                                    </td>
                                    
                                    <td>
                                        <a href="appplatformdetail.aspx?Id=<%# Eval("Id") %>">编辑</a>
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
