<%@ Page Title="" Language="C#" MasterPageFile="~/mw/Default.Master" AutoEventWireup="true" CodeBehind="test.aspx.cs" Inherits="PortalWeb.mw.test" %>
<asp:Content ID="Content1" ContentPlaceHolderID="head" runat="server">
    <script type="text/javascript" src="<%=BaseUi.JqueryPath %>/jquery.cookie.js"></script>
</asp:Content>
<asp:Content ID="Content2" ContentPlaceHolderID="ContentPlaceHolder1" runat="server">
     <script>
         $.cookie('testcooke', "111");
         document.writeln("testcooke2:" + $.cookie("testcooke2"));
    </script>
</asp:Content>
