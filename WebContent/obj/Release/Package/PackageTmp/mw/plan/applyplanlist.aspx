<%@ Page Title="" Language="C#" MasterPageFile="~/mw/Default.Master" AutoEventWireup="true" CodeBehind="applyplanlist.aspx.cs" Inherits="PortalWeb.mw.plan.applyplanlist" %>
<%@ Import Namespace="MW.BLL" %>
<asp:Content ID="Content1" ContentPlaceHolderID="head" runat="server">
</asp:Content>
<asp:Content ID="Content2" ContentPlaceHolderID="ContentPlaceHolder1" runat="server">
        <table border="0" cellspacing="0" style="width: 100%;" >

            <tr>
                <td colspan="2">
                 <table cellpadding="0" cellspacing="0" border="0" width="100%" id="tableData">
                        <tr>
                            <th style="width: 110px;">
                                ID</th>
                            <th>
                                课程名</th>
                            <th style="width: 150px;">
                                学员</th>
                            <th style="width: 150px;">
                                报名时间</th>
                            <th style="width: 60px;">
                                操作</th>
                        </tr>
                 <ctl:JRepeater ID="rpt_List" runat="server" OnItemCommand="rpt_List_ItemCommand" >
                  <HeaderTemplate>
                   
                       </HeaderTemplate>
                            <ItemTemplate>
                                <tr class="tr_data" onmouseout="mout(this);" onmouseover="mover(this);">
                                    <td>
                                        <%# Eval("id")%>
                                    </td>
                                    <td>
                                        <%#BaseUi.GetLang(MW.BLL.B_tech_train_course.inance.GetPlanName(Eval("ApplyPlanID").ToString())) %>
                                    </td>
                                    <td>
                                       <%#MW.BLL.B_ecan_user.inance.GetUserName(Eval("ApplierID").ToString()) %>
                                    </td>
                                    
                                    <td>
                                       <%# DataBinder.Eval(Container.DataItem, "ApplyTime", "{0:yyyy-MM-dd HH:mm}")%>
                                    </td>
                                    
                                    <td>
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
