<%@ Page Title="" Language="C#" MasterPageFile="~/mw/Default.Master" AutoEventWireup="true" CodeBehind="testdriverdetail.aspx.cs" Inherits="PortalWeb.mw.vwtestdrive.testdriverdetail" %>
<asp:Content ID="Content1" ContentPlaceHolderID="head" runat="server">
        <script type="text/javascript" src="<%=BaseUi.JqueryPath %>/jquery.validate.js"></script>
</asp:Content>
<asp:Content ID="Content2" ContentPlaceHolderID="ContentPlaceHolder1" runat="server">
     <div id="h_operate" style="padding-top: 5px;">
        <table border="0" cellpadding="0" cellspacing="1" bgcolor="#ffffff" width="100%" id="tableAoe">

            <tr>
                <td class="td_title"><span class="red">◆</span>名称：</td>
                <td class="td_content">
                    <asp:TextBox ID="txtC_Name" runat="server" Width="70%"  CssClass="required"></asp:TextBox>
                </td>
            </tr>
            <tr>
                <td class="td_title">识别码：</td>
                <td class="td_content">
                    <asp:TextBox ID="txtC_Code" runat="server"></asp:TextBox>
                </td>
            </tr>
            <tr>
                <td class="td_title">所属课程：</td>
                <td class="td_content">
                    <asp:TextBox ID="txtC_Course" runat="server" Width="70%"></asp:TextBox>
                    <asp:HiddenField ID="hideC_Course" runat="Server" Value="0" />
                    <a href="javascript:" class="btn"  onclick="javascript:modify1('查找课程','coursedetail.aspx?hidCtl=<%=hideC_Course.ClientID %>&amp;txtCtl=<%=txtC_Course.ClientID %>',530);">选择</a>
                </td>
            </tr>
            <tr>
                <td class="td_title">所属老师：</td>
                <td class="td_content">
                    <asp:TextBox ID="txtC_Teacher" runat="server" Width="70%"></asp:TextBox><asp:HiddenField ID="hideC_Teacher" runat="Server" Value="0" />
                     <a href="javascript:" class="btn"  onclick="javascript:modify1('查找老师','_userdialog.aspx?hidCtl=<%=hideC_Teacher.ClientID %>&amp;txtCtl=<%=txtC_Teacher.ClientID %>',530);">选择</a>
       
                </td>
            </tr>
            <tr>
                <td class="td_title">参加学员：</td>
                <td class="td_content">
                    <asp:TextBox ID="txtStudents" runat="server" TextMode="MultiLine" Rows="5" Width="90%"></asp:TextBox><asp:HiddenField ID="hideStudents" runat="Server" Value="0" />
                </td>
            </tr>
            <tr>
                <td class="td_title">参加车型：</td>
                <td class="td_content">
                    <asp:TextBox ID="txtChexi" runat="server" TextMode="MultiLine" Rows="5" Width="90%"></asp:TextBox><asp:HiddenField ID="hideChexi" runat="Server" Value="0" />
                </td>
            </tr>
            <tr>
                <td class="td_title">场地：</td>
                <td class="td_content">
                    <asp:CheckBox runat="server" ID="chkgl" Text="公路"/>
                    <asp:CheckBox runat="server" ID="chkyd" Text="运动"/>
                    <asp:CheckBox runat="server" ID="chkyy" Text="越野"/>
<%--                    <asp:CheckBoxList runat="server" RepeatDirection="Horizontal" ID="txtChangdi">
                        <asp:ListItem Value="0">公路</asp:ListItem>
                        <asp:ListItem Value="1">运动</asp:ListItem>
                        <asp:ListItem Value="2">越野</asp:ListItem>
                    </asp:CheckBoxList>--%>

                </td>
            </tr>
        </table>
    </div>
    <div id="h_bottom">
                <asp:HiddenField runat="server" ID="hidId" Value="0"/>
                <asp:Button ID="btnSave" OnClick="btnSave_Click" runat="server" Text="保 存" ValidationGroup="add" class="btn"></asp:Button>
                &nbsp;
        <input id="Button1" value="返回" type="button" class="btn" onclick="javascript: history.go(-1);" /> 
    </div>
    <script>
        function modify(name, url, width) {
            
            $.layer({
                type: 2,
                border: [0],
                title: false,
                shadeClose: true,
                closeBtn: false,
                iframe: { src: url },
                area: ['550px', '400px']
            });

        }
        function modify1(name, url, width) {
            $.layer({
                type: 2,
                border: [0],
                title: false,
                shadeClose: true,
                closeBtn: false,
                iframe: { src: url },
                area: ['530px', '370px']
            });

        }
        $(document).ready(function () {

            $("#<%=Master.FindControl("dofrm").ClientID%>").validate();

        });
    </script>
</asp:Content>
