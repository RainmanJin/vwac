<%@ Page Title="" Language="C#" MasterPageFile="~/mw/DefaultDetail.Master" AutoEventWireup="true" CodeBehind="tmpcodedialog.aspx.cs" Inherits="PortalWeb.mw.plan.tmpcodedialog" %>
<asp:Content ID="Content1" ContentPlaceHolderID="head" runat="server">
    <script type="text/javascript">
        var index = parent.layer.getFrameIndex(window.name); //获取当前窗体索引
        function getRadioValue() {
            var u=$('input[name="dx"]:checked').val();
            var id = '<%=id%>';
            var url = "tmpcodedialog.aspx?action=set";
            $.post(url, { "tmpcode": u, "id": id }, function (result) {
                //结果返回
                if (result == "1") {
                    alert("设置临时码失败！");
                } else {
                    alert("设置临时码成功");
                    parent.location.reload();
                }
            });
        }
       
        function gb() {

            parent.layer.close(index);
            
        }
    </script>
</asp:Content>
<asp:Content ID="Content2" ContentPlaceHolderID="ContentPlaceHolder1" runat="server">
    <div id="h_main">
        <table border="0" cellpadding="0" cellspacing="0" width="100%" id="tableData">
            <tr>
                <td>
                     <ctl:JRepeater ID="rptCustomer" runat="server">
                    <HeaderTemplate>
                  <table cellpadding="0" cellspacing="0" border="0" width="100%" id="tableData">

                      
                        <tr class="tr_data" onmouseout="mout(this);" onmouseover="mover(this);">
                              <td>
                                        <input id="cheId"  value="" type="radio" name="dx" /></td>
                                    <td>
                                        <a href="javascript:void(0)">
                                          <span style="color:red; font-weight: bold;"> 置空</span>
                                        </a>
                                    </td>
                                </tr>
               
                  
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
                    </table>
            </FooterTemplate>
         </ctl:JRepeater>
             <tr>
                <td style="text-align: center;" colspan="2"><button id="transmit" onclick="getRadioValue()" class="btn">提交</button>&nbsp;&nbsp;&nbsp;&nbsp;<button id="closebtn" onclick="gb()" class="btn">关闭</button></td> 
            </tr>
                </td>
            </tr>
            <asp:HiddenField runat="server" ID="hideradio" Value="0"/>
            <asp:HiddenField runat="server" ID="hideid" Value="0"/>
        </table>
    </div>
</asp:Content>
