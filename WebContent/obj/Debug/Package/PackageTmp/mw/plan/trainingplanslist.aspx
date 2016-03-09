<%@ Page Title="" Language="C#" MasterPageFile="~/mw/Default.Master" AutoEventWireup="true" CodeBehind="trainingplanslist.aspx.cs" Inherits="PortalWeb.mw.TrainingPlansList" %>
<asp:Content ID="Content1" ContentPlaceHolderID="head" runat="server">
    <script type="text/javascript" src="<%=BaseUi.JqueryPath %>/jquery.min.js"></script>
    <script type="text/javascript" src="<%=BaseUi.JqueryPath %>/layer/layer.min.js"></script>
</asp:Content>
<asp:Content ID="Content2" ContentPlaceHolderID="ContentPlaceHolder1" runat="server">
        <table border="0" cellspacing="0" style="width: 100%;">
           
            <tr>
                <td colspan="2">
                 
                 <ctl:JRepeater ID="rpt_List" runat="server" >
                  <HeaderTemplate>
                   <table cellpadding="0" cellspacing="0" border="0" width="100%" id="tableData">
                        <tr>
                            <th>
                                课程名称</th>
                             <th>
                                课程码</th>
                            <th style="width: 210px;">
                                操作</th>
                        </tr>
                       </HeaderTemplate>
                            <ItemTemplate>
                                <tr class="tr_data" onmouseout="mout(this);" onmouseover="mover(this);">
                                   
                                    <td>
                                    <%#BaseUi.GetLang(Eval("Name").ToString())%>
                                    </td>
                                    <td>
                                      <%#Eval("tmpcode").ToString()%>  </td>
                                    <td>
                                       <%#Eval("tmpcode").ToString()==""?"<a href=\"javascript:;\" onclick=\"addtmp('"+Eval("Id")+"');\">设置临时码</a>":""%>
                                      
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
    <script>

        function addtmp(id) {
            $.layer({
                type: 2,
                border: [0],
                title: "请选择课程码",
                shadeClose: true,
                iframe: {src : 'tmpcodedialog.aspx?groupid=1&id='+id},
                area: ['470px', '400px'],
                
            });
           
        }
    </script>
</asp:Content>
