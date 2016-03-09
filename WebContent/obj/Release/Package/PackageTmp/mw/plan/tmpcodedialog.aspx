<%@ Page Title="" Language="C#" MasterPageFile="~/mw/DefaultDetail.Master" AutoEventWireup="true" CodeBehind="tmpcodedialog.aspx.cs" Inherits="PortalWeb.mw.plan.tmpcodedialog" %>
<asp:Content ID="Content1" ContentPlaceHolderID="head" runat="server">
    <script type="text/javascript">
        function getRadioValue() {
            var u=$('input[name="dx"]:checked').val();
            var id = <%=id%>;
            var url = "trainingplanslist.aspx?action=set";
            $.post(url, { "tmpcode": u, "id": id }, function (result) {
                //结果返回
                if (result == "1") {
                    alert("设置临时码失败！");
                } else {
                    alert("设置临时码成功:" + result);
                    
                }
            });
            
        }
</script>
</asp:Content>
<asp:Content ID="Content2" ContentPlaceHolderID="ContentPlaceHolder1" runat="server">
    <div id="h_main">
        <table border="0" cellpadding="0" cellspacing="0" width="100%">
            <tr>
                <td>
                  <table cellpadding="0" cellspacing="0" border="0" width="100%" id="tableData">
                        <tr>
                               <th style="width: 30px;">
                                </th>
                            <th>
                                临时码选择</th>
                        </tr>
                        <tr class="tr_data" onmouseout="mout(this);" onmouseover="mover(this);">
                              <td>
                                        <input id="cheId"  value="" type="radio" name="dx" /></td>
                                    <td>
                                        <a href="javascript:void(0)">
                                          <span style="color:red; font-weight: bold;"> 置空</span>
                                        </a>
                                    </td>
                                </tr>
                <ctl:JRepeater ID="rptCustomer" runat="server">
                    <HeaderTemplate>
                  
                        </HeaderTemplate>
                            <ItemTemplate>
                                <tr class="tr_data" onmouseout="mout(this);" onmouseover="mover(this);">
                                      <td>
                                        <input id="cheId"  value='<%#Eval("CardNumber")%>' type="radio" name="dx" /></td>
                                    <td>
                                            <%#Eval("CardNumber").ToString()%>
                                    </td>
                                </tr>
                            </ItemTemplate>
                        <FooterTemplate>
               
            </FooterTemplate>
         </ctl:JRepeater>
                    </table>
                </td>
            </tr>
            <asp:HiddenField runat="server" ID="hideradio" Value="0"/>
            <asp:HiddenField runat="server" ID="hideid" Value="0"/>
        </table>
    </div>
</asp:Content>
