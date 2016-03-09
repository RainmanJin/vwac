<%@ Page Title="" Language="C#" MasterPageFile="~/mw/Default.Master" AutoEventWireup="true" CodeBehind="tmpcodelist.aspx.cs" Inherits="PortalWeb.mw.plan.tmpcodelist" %>
<asp:Content ID="Content1" ContentPlaceHolderID="head" runat="server">
</asp:Content>
<asp:Content ID="Content2" ContentPlaceHolderID="ContentPlaceHolder1" runat="server">
        <table border="0" cellspacing="0" style="width: 100%;" id="table1">
            <td style="text-align: right; padding-right: 3px;">
               <input id="btnAdd" type="button" value="生成临时码" class="btnlistOver" onclick="goUrl('tmpcodedetail.aspx');"  />
            </td>
            <tr>
                <td colspan="2">
                 <table cellpadding="0" cellspacing="0" border="0" width="100%" id="tableData">
                        <tr>
                        
                            <th>
                                临时码</th>
                            <th style="width: 150px;">
                                类型</th>
                            <th style="width: 110px;">
                                状态</th>
                            <th style="width: 150px;">
                                有效期</th>
                            <th style="width: 60px;">
                                操作</th>
                        </tr>
                 <ctl:JRepeater ID="rpt_List" runat="server" OnItemCommand="rpt_List_ItemCommand" >
                  <HeaderTemplate>
                   
                       </HeaderTemplate>
                            <ItemTemplate>
                                <tr class="tr_data" onmouseout="mout(this);" onmouseover="mover(this);">
                                
                                    <td>
                                    <%#MW.BLL.B_Tempcode.DesDecrypt(Eval("CardNumber").ToString()) %>
                                    </td>
                                    <td>
                                          <%# Eval("Type").ToString()=="1"?"课程码":"认证码"%>
                                    </td>
                                    <td>
                                        <%# Eval("State").ToString()=="1"?"无效":(Eval("State").ToString()=="2"?"已使用":"未使用")%>
                                    </td>
                                    <td>
                                          <%# MW.Common.Utils.GetStandardDate(Eval("indate").ToString())%>
                                    </td>
                                   <%-- <td>
                                        <%# DataBinder.Eval(Container.DataItem, "CreateTime", "{0:yyyy-MM-dd HH:mm}")%>
                                    </td>--%>
                                    
                                    <td>
                                        <asp:LinkButton ID="lbtnDelete" CommandName="lbtnDelete" CommandArgument='<%#Eval("Id")%>'
                                            title="删除" OnClientClick="return confirm('确定删除数据吗？')" runat="server">删除</asp:LinkButton>
                                    </td>
                                </tr>
                            </ItemTemplate>
                        <FooterTemplate>
              
            </FooterTemplate>
         </ctl:JRepeater>
               </table>   </td>
            </tr>
        </table>
</asp:Content>
