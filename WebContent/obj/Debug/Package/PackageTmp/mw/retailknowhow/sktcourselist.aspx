<%@ Page Title="" Language="C#" MasterPageFile="~/mw/Default.Master" AutoEventWireup="true" CodeBehind="sktcourselist.aspx.cs" Inherits="PortalWeb.mw.retailknowhow.SktcourseList" %>
<%@ Register TagPrefix="ctl" Namespace="PortalControls" Assembly="PortalControls" %>
<asp:Content ID="Content1" ContentPlaceHolderID="head" runat="server">
</asp:Content>
<asp:Content ID="Content2" ContentPlaceHolderID="ContentPlaceHolder1" runat="server">
       
        <table border="0" cellspacing="0" style="width: 100%;" id="table1">

            <tr class="daohang_title">
 
                <td style="text-align: right; padding-right: 3px;">
                    
                    <%--<input id="btnAdd" type="button" value="新 增" class="btnlistOver" onclick="javascript: openDlg('Add', '新增', ' sktcoursedetail.aspx', 570, 350, '');" />--%>
                     <input id="Button1" type="button" value="新 增" class="btnlistOver" onclick="javascript:location.href='sktcoursedetail.aspx'"/>
                   </td> 
            </tr>
            <tr>
                <td colspan="2">
                 <ctl:JRepeater ID="rpt_List" runat="server" OnItemCommand="rpt_List_ItemCommand" >
                  <HeaderTemplate>
                    <table cellpadding="0" cellspacing="0" border="0" width="100%" id="tableData">
                        <tr>
                          
                            <th>
                                知识名称</th>
                            <th>
                                状态</th>
                            <th>
                                加入时间</th>

                            <th style="width: 150px;">
                                操作</th>
                        </tr>
                       </HeaderTemplate>
                            <ItemTemplate>
                                <tr class="tr_data" onmouseout="mout(this);" onmouseover="mover(this);">
                                    <td>
                                         <%# Eval("C_Name")%>
                                    </td>
                                   <td>
                                         <%# Eval("islock").ToString()=="0"?"正常":"锁定"%>
                                    </td>
                                    <td>
                                        <%# DataBinder.Eval(Container.DataItem, "CreateTime", "{0:yyyy-MM-dd}")%>
                                    </td>
                                    
                                    <td>
                                          <a href="sktchapterlist.aspx?Id=<%# Eval("Id") %>">配置题目</a>
                                        <a href="sktcoursedetail.aspx?Id=<%# Eval("Id") %>">编辑</a>
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
