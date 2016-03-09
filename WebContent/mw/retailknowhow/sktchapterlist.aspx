<%@ Page Title="" Language="C#" MasterPageFile="~/mw/Default.Master" AutoEventWireup="true" CodeBehind="sktchapterlist.aspx.cs" Inherits="PortalWeb.mw.retailknowhow.SktchapterList" %>
<%@ Register TagPrefix="ctl" Namespace="PortalControls" Assembly="PortalControls" %>
<asp:Content ID="Content1" ContentPlaceHolderID="head" runat="server">
</asp:Content>
<asp:Content ID="Content2" ContentPlaceHolderID="ContentPlaceHolder1" runat="server">
      
        <table border="0" cellspacing="0" style="width: 100%;" id="table1">
            <tr class="daohang_title">
              
                <td style="text-align: right; padding-right: 3px;">
                     <input id="Button2" type="button" value="返 回" class="btnlistOver" onclick="javascript: goUrl('sktcourselist.aspx');"/>
                    <input id="Button3" type="button" value="新 增" class="btnlistOver" onclick="javascript: location.href = 'sktchapterdetail.aspx?Cid=<%=Id %>'"/>

                </td>
            </tr>
            <tr>
                <td colspan="2">
                 <ctl:JRepeater ID="rpt_List" runat="server" OnItemCommand="rpt_List_ItemCommand" >
                  <HeaderTemplate>
                    <table cellpadding="0" cellspacing="0" border="0" width="100%" id="tableData">
                        <tr>
                            <th>
                                题目名</th>
                            <th style="width: 60px;">
                                排序</th>
                            <th style="width: 60px;">
                                状态</th>
                            <th style="width: 60px;">
                                类型</th>
                            <th style="width: 180px;">
                                操作</th>
                        </tr>
                       </HeaderTemplate>
                            <ItemTemplate>
                                <tr class="tr_data" onmouseout="mout(this);" onmouseover="mover(this);">
                                    <td>
                                    <%# Eval("C_Name")%>
                                    </td>
                                    <td>
                                         <%# Eval("sort")%>
                                    </td>
                                    <td>
                                         <%# Eval("islock").ToString()=="0"?"正常":"锁定"%>
                                    </td>
                                    <td>
                                    <%#_Type(Convert.ToInt32(Eval("C_Type"))) %>
                                    </td>
                                    <td>
                                        <a href="sktitemlist.aspx?&type=<%#Eval("c_type") %>&CId=<%=Id %>&Id=<%# Eval("Id") %>">配置项目</a>
                                        <a href="sktchapterdetail.aspx?CId=<%=Id %>&Id=<%# Eval("Id") %>" >编辑题目</a>

                                        <asp:LinkButton ID="lbtnDelete" CommandName="lbtnDelete" CommandArgument='<%#Eval("Id")%>'
                                            title="删除题目" OnClientClick="return confirm('确定删除数据吗？')" runat="server">删除题目</asp:LinkButton>
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
