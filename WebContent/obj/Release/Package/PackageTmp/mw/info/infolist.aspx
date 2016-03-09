<%@ Page Title="" Language="C#" MasterPageFile="~/mw/Default.Master" AutoEventWireup="true" CodeBehind="infolist.aspx.cs" Inherits="PortalWeb.mw.Info.InfoList" %>
<%@ Import Namespace="PortalWeb.mw.wap" %>
<asp:Content ID="Content1" ContentPlaceHolderID="head" runat="server">
</asp:Content>
<asp:Content ID="Content2" ContentPlaceHolderID="ContentPlaceHolder1" runat="server">
        <table border="0" cellspacing="0"width="100%" id="table1">
            <tr class="daohang_title">
                <td style="text-align: right;">
                    
                     <input id="Button2" type="button" value="新 增" class="btnlistOver" onclick="javascript: location.href = 'Infodetail.aspx'" />
                  </td>
            </tr>
            <tr>
                <td colspan="2">
                <table cellpadding="0" cellspacing="0" border="0" width="100%" id="tableData">
                        <tr>
                            <th style="width: 60px;">
                                ID</th>
                            <th style="width: 30%;">
                                名称</th>
                            <th style="width:20%">
                                分类名称</th>
                            <th style="width: 150px;">
                                录入日期</th>
                            <th style="width: 120px;">
                                操作</th>
                        </tr>
                 <ctl:JRepeater ID="rpt_List" runat="server" OnItemCommand="rpt_List_ItemCommand" OnItemDataBound="rpt_List_ItemDataBound">
 
                            <ItemTemplate>
                                <tr class="tr_data" onmouseout="mout(this);" onmouseover="mover(this);">
                                    <td>
                                         <%# Eval("id")%>
                                    </td>
                                    <td>
                                    <div style='display:none'><asp:Literal ID="litName" runat="Server"></asp:Literal></div>
                                        <a href="javascript:void(0)" onclick="javascript:openDlg('InfoView<%#Eval("Id") %>','查看信息-<%#Eval("Title") %>','/Wap/Info/InfoView.aspx?pageid=<%=PageID %>&Id=<%# Eval("Id") %>',800,'auto','');">  <%# Eval("Title") %></a>
                                    </td>
                                    <td><%#BaseUi.GetLang( new MW.BLL.B_ecan_domainvalue().GetModelAoe(Eval("TypeId").ToString()).DOMAINLABEL)%></td>
                                    <td>
                                        <%# DataBinder.Eval(Container.DataItem, "CreateTime", "{0:yyyy-MM-dd}")%>
                                    </td>
                                    <td>
                                        <a href="InfoDetail.aspx?pageid=<%=PageID %>&Id=<%# Eval("Id") %>">
                                            编辑</a>
                                        <asp:LinkButton ID="lbtnDelete" CommandName="lbtnDelete" CommandArgument='<%#Eval("Id")%>'
                                            title="删除" OnClientClick="return confirm('确定删除数据吗？')" runat="server">删除</asp:LinkButton>
                                        <a href="javascript:void(0)" onclick="javascript:_Copy(<%# Eval("Id") %>);">
                                            外调链接</a> 
                                    </td>
                                </tr>
                            </ItemTemplate>
                        <FooterTemplate>
               
            </FooterTemplate>
         </ctl:JRepeater>
               </table>  </td>
            </tr>
        </table>
    <script type="text/javascript">
        function _Copy(id) {
            var url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=<%=WxBase.AppId%>&redirect_uri=<%=WxBase.SiteUrl%>/wap/OAuth2.ashx&response_type=code&scope=snsapi_base&state=info_" + id + "#wechat_redirect";
            window.clipboardData.setData("Text",url);
            alert("复制成功，请粘贴到微信公众平台原文链接处");
        }
    </script>
</asp:Content>
