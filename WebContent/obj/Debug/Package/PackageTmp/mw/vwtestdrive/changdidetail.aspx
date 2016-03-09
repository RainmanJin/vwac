<%@ Page Title="" Language="C#" MasterPageFile="~/mw/Default.Master" AutoEventWireup="true" CodeBehind="changdidetail.aspx.cs" Inherits="PortalWeb.mw.vwtestdrive.changdidetail" %>
<asp:Content ID="Content1" ContentPlaceHolderID="head" runat="server">
</asp:Content>
<asp:Content ID="Content2" ContentPlaceHolderID="ContentPlaceHolder1" runat="server">
     <div id="h_operate" style="padding-top: 5px;">
        <table border="0" cellpadding="0" cellspacing="1" bgcolor="#ffffff" width="100%">
            <tr>
                <td class="td_title">字段设置：</td>
                <td class="td_content">
                    <asp:Repeater ID="repPermissions" runat="server" OnItemDataBound="repPermissions_ItemDataBound">
                        <HeaderTemplate>
                            <table border="0" cellpadding="5" cellspacing="1" style="width: 100%; background: #ccc;">
                        </HeaderTemplate>
                        <ItemTemplate>
                            <tr class="tr_data" style="border-bottom:1px #ccc solid;">
                                <td style="background: #fff; width: 80px;">
                                    <asp:CheckBox ID="cheFirst" Text='<%# Eval("C_name") %>' ToolTip='<%# Eval("Id") %>'
                                        runat="server"/> <%--AutoPostBack="true" OnCheckedChanged="cheFirst_CheckedChanged"--%>
                                    <div class="bak" style="text-align: left;">
                                        <%# Eval("C_name")%>
                                    </div>
                                    <asp:HiddenField ID="hidPermissionsId" Value='<%# Eval("Id")%>' runat="server" />
                                </td>
                                <td style="background: #fff; vertical-align: top;">
                                    <asp:Repeater ID="repItemPermissions" runat="server" OnItemDataBound="repItemPermissions_ItemDataBound">
                                        <HeaderTemplate>
                                            <table border="0" cellpadding="0" cellspacing="0" style="width: 100%">
                                        </HeaderTemplate>
                                        <ItemTemplate>
                                            <tr>
                                                <td style="width: 100px;">
                                                    <asp:CheckBox ID="cheSecond" Text='<%# Eval("C_name") %>' ToolTip='<%# Eval("Id") %>'
                                                        runat="server" /><%--AutoPostBack="true" OnCheckedChanged="cheSecond_CheckedChanged"--%> 
                                                    <asp:HiddenField ID="hidItemPermissionsId" Value='<%#Eval("Id")%>' runat="server" />
                                                </td>
                                                <td>
                                                    <asp:Repeater runat="server" ID="repChkbox" OnItemDataBound="repChkbox_ItemDataBound">
                                                        <ItemTemplate>
                                                        <asp:CheckBox runat="server" ID="chkthrid"/>
                                                            </ItemTemplate>
                                                    </asp:Repeater>
                                                    <%--<asp:CheckBoxList ID="chelPermissionsIds" runat="server" RepeatColumns="3" RepeatDirection="Horizontal"
                                                        DataTextField="C_name" DataValueField="id">
                                                    </asp:CheckBoxList>--%>
                                                </td>
                                            </tr>
                                        </ItemTemplate>
                                        <FooterTemplate>
                                            </table>
                                        </FooterTemplate>
                                     </asp:Repeater>
                                    </td>
                                </tr>
                             </ItemTemplate>
                                <FooterTemplate>
                                    </table>
                                </FooterTemplate>
                            </asp:Repeater>
                </td>
        </table>
    </div>
    <div id="h_bottom">
                <asp:HiddenField runat="server" ID="hidSysid" Value="0" />
                <asp:HiddenField runat="server" ID="HiddenField1" Value="0" />
                <asp:HiddenField runat="server" ID="hidField" Value="0"/>
                <asp:Button ID="btnSave" OnClientClick="savedata()" OnClick="btnSave_Click" runat="server" Text="保 存" ValidationGroup="add" class="btn"></asp:Button>
                &nbsp;
      <input id="Button1" value="返回" type="button" class="btn" onclick="javascript: history.go(-1);" /> 
    </div>
    <script>
        function savedata() {
            var str = "";
            $("input:checkbox:checked").each(function () {
                str += $(this).parent().attr("title") + ",";
            });
            $("#<%=hidField.ClientID%>").val(str);
        }
    </script>
</asp:Content>
