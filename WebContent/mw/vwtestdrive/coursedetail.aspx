<%@ Page Title="" Language="C#" MasterPageFile="~/mw/DefaultDetail.Master" AutoEventWireup="true" CodeBehind="coursedetail.aspx.cs" Inherits="PortalWeb.mw.vwtestdrive.coursedetail" %>
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
               var stradio = $("#<%=hideradio1.ClientID%>").val();//txt隐藏域的值
               parent.$("#" + stradio).val(arr[1]);
               var strid = $("#<%=hideid1.ClientID%>").val();//id隐藏域的值
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
        <table border="0" cellspacing="0" style="width: 100%;">
            <tr>
                <td colspan="2" style="padding-top: 5px;">
                    
                </td>
            </tr>
            <tr>
                <td colspan="2">
                 <ctl:JRepeater ID="rpt_List" runat="server" OnItemCommand="rpt_List_ItemCommand" >
                  <HeaderTemplate>
                    <table cellpadding="0" cellspacing="0" border="0" width="100%" id="tableData">
                        <tr>
                            <td style="width: 50px">
                                </td>
                            <td>
                                课程名称</td>
                        </tr>
                       </HeaderTemplate>
                            <ItemTemplate>
                                <tr class="tr_data" onmouseout="mout(this);" onmouseover="mover(this);">
                                   <td>
                                        <input id="cheId"  value='<%#Eval("ID")%>|<%#BaseUi.GetLang(Eval("NAME").ToString())%>' type="radio" name="dx" /></td>
                                    <td>
                                            <%#BaseUi.GetLang(Eval("NAME").ToString())%>
                                    </td>
                                </tr>
                            </ItemTemplate>
                        <FooterTemplate>
                </table>
            </FooterTemplate>
         </ctl:JRepeater>
                </td>
            </tr>
            <tr> <td style="text-align: center;"><button id="transmit" onclick="getRadioValue()" class="btn" >提交</button>&nbsp;&nbsp;&nbsp;&nbsp;<button id="closebtn" onclick="gb()" class="btn">关闭</button></td> </tr>
              <asp:HiddenField runat="server" ID="hideradio1" Value="0"/>
            <asp:HiddenField runat="server" ID="hideid1" Value="0"/>
        </table>
</asp:Content>
