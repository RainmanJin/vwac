<%@ Page Title="" Language="C#" MasterPageFile="~/mw/DefaultDetail.Master" AutoEventWireup="true" CodeBehind="_userdialog.aspx.cs" Inherits="PortalWeb.mw.vwsurvey._userdialog" %>
<asp:Content ID="Content1" ContentPlaceHolderID="head" runat="server">
    <script type="text/javascript">
        function getRadioValue() {
            var zoneNames = document.getElementsByName("dx");
            var myvalue = "";
            for (var i = 0; i < zoneNames.length; i++) {
                var zoneName = zoneNames[i];
                if (zoneName.checked) {
                    myvalue = zoneName.value;
                    
                }
            }
            var arr = myvalue.split("|");
            var stradio = $("#<%=hideradio.ClientID%>").val();//txt隐藏域的值
            parent.$("#" + stradio).val(arr[1]);
            var strid = $("#<%=hideid.ClientID%>").val();//id隐藏域的值
            parent.$("#" + strid).val(arr[0]);
            parent.layer.close(index);
        }
        
        var index = parent.layer.getFrameIndex(window.name); //获取当前窗体索引

        function gb() {

                parent.layer.close(index);
        }
    
        
    </script>
</asp:Content>
<asp:Content ID="Content2" ContentPlaceHolderID="ContentPlaceHolder1" runat="server">
    <div id="h_main">
        <table border="0" cellpadding="0" cellspacing="0" width="100%">
            <tr>
                <td>
                 <ctl:JRepeater ID="rptCustomer" runat="server" >
                    <HeaderTemplate>
                  <table cellpadding="0" cellspacing="0" border="0" width="100%" id="tableData">
                        <tr>
                               <th style="width: 30px;">
                                </th>
                            <th>
                                用户名称</th>
                        </tr>
                        <tr class="tr_data" onmouseout="mout(this);" onmouseover="mover(this);">
                              <td>
                                        <input id="cheId"  value="" type="radio" name="dx" /></td>
                                    <td>
                                        <a href="javascript:void(0)" onclick="funGetValue(' ',0)">
                                          <span style="color:red; font-weight: bold;"> 置空</span>
                                        </a>
                                    </td>
                                </tr>
                        </HeaderTemplate>
                            <ItemTemplate>
                                <tr class="tr_data" onmouseout="mout(this);" onmouseover="mover(this);">
                                      <td>
                                        <input id="cheId"  value='<%#Eval("ID")%>|<%#Eval("NAME")%>' type="radio" name="dx" /></td>
                                    <td>
                                            <%#Eval("NAME").ToString()%>
                                    </td>
                                </tr>
                                
                            </ItemTemplate>
                        <FooterTemplate>
                    </table>
            </FooterTemplate>
         </ctl:JRepeater>
                   
                </td>
            </tr>
            <tr>
                <td style="text-align: center;"><button id="transmit" onclick="getRadioValue()" class="btn" style="margin-top: 20px">提交</button>&nbsp;&nbsp;&nbsp;&nbsp;<button id="closebtn" onclick="gb()" class="btn">关闭</button></td> 
            </tr>
            <asp:HiddenField runat="server" ID="hideradio" Value="0"/>
            <asp:HiddenField runat="server" ID="hideid" Value="0"/>
        </table>
    </div>
</asp:Content>
