<%@ Page Title="" Language="C#" MasterPageFile="~/mw/Default.Master" AutoEventWireup="true" CodeBehind="BiceItem.aspx.cs" Inherits="Plugin.VWSurvey.Admini.VWSurvey.BiceItem" %>
<asp:Content ID="Content1" ContentPlaceHolderID="head" runat="server">
</asp:Content>
<asp:Content ID="Content2" ContentPlaceHolderID="ContentPlaceHolder1" runat="server">
    <div id="h_main">

        <table border="0" cellspacing="0" style="width: 100%;">
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
                              <!--  <td class="td_title">
                                搜索关键字：</td>
                            <td class="td_content">
                                <asp:TextBox runat="server" ID="txtSearchName" Style="width: 208px"></asp:TextBox>
                                </td>-->
                            <td class="td_title">
                                所属类别：</td>
                            <td class="td_content">
                               <asp:DropDownList runat="server" ID="drpType" Width="200"/></td>
                        
                            <td  style="text-align: left; padding-left: 10px;">
                                <asp:Button ID="btnSearch" runat="server" Text="搜 索" CssClass="btnlistOver" OnClick="btnSearch_Click"
                                    OnClientClick="javascript:funbtnClick('#ctl00_cphContent_btnSearch');"></asp:Button>
                                </td>
                        </tr>
                         </table>
                </td>
            </tr>
            <tr class="daohang_title">
                <td style="text-align: left; padding-left: 3px;">
                    列表↓ 
                </td>
                <td style="text-align: right; padding-right: 3px;">
                      <input class="btnlistOver" type="button" value="返回" onclick="goUrl('makevote.aspx')"></input> 
                     <input id="btnAdd" type="button" value="新 增" class="btnlistOver" onclick="javascript: goUrl('biceitemdetail.aspx?sysid=<%=sysid %>');" />
                    <asp:Button ID="lbtnDeletes" runat="server" Text="批量删除" CssClass="btnlistOver" OnClientClick="return confirm('确定删除选中的数据吗？')"
                        OnClick="lbtnDeletes_Click" />
                 
                </td>
            </tr>
            <tr>
                <td colspan="2">
                 <ctl:JRepeater ID="rpt_List" runat="server" OnItemCommand="rpt_List_ItemCommand"  OnItemDataBound="rpt_List_ItemDataBound">
                  <HeaderTemplate>
                    <table cellpadding="0" cellspacing="0" border="0" width="100%" id="tableData">
                        <tr>
                            <th style="width: 30px;">
                                <input id="cheList" type="checkbox" onclick="checkAll(this.form)" /></th>
                            <th style="width: 30px;">
                                ID</th>
                            <th>
                                题目名称</th>
                            <th>
                                所属</th>
                            <th style="width: 100px;">
                                类型</th>
                            <th style="width: 30px;">
                                排序</th>
                            <th style="width: 30px;">
                                必答</th>
                            <th style="width: 120px;">
                                操作</th>
                        </tr>
                       </HeaderTemplate>
                            <ItemTemplate>
                                <tr class="tr_data" onmouseout="mout(this);" onmouseover="mover(this);">
                                    <td>
                                        <input id="cheId" runat="server" value='<%#Eval("N_SubId")%>' type="checkbox" /></td>
                                    <td>
                                         <%# Eval("N_SubId")%>
                                    </td>
                                    <td>
                                    <%#Eval("C_SubTitle") %>
                                    </td>
                                    <td>
                                    <%# new MW.BLL.B_Votesubject().GetName(MW.Common.Utils.StrToInt(Eval("Parentid"),0)) %>
                                    </td>
                                    <td>
                                          <%# GetType(Eval("N_Type"))%>
                                    </td>
                                    <td>
                                          <%# Eval("N_OrderId")%>
                                    </td>
                                    <td>
                                        <%# Eval("N_Need").ToString()=="1"?"是":"否"%>
                                    </td>
                                    
                                    <td>
                                        <a href="biceitemdetail.aspx?sysid=<%=sysid %>&Id=<%# Eval("N_SubId") %>">
                                            编辑</a>
                                        <asp:Literal runat="server" ID="lit"></asp:Literal>   
                                        <asp:LinkButton ID="lbtnDelete" CommandName="lbtnDelete" CommandArgument='<%#Eval("N_SubId")%>'
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