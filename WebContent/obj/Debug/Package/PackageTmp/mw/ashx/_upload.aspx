<%@ Page Language="C#" AutoEventWireup="true" CodeBehind="_upload.aspx.cs" Inherits="PortalWeb.mw.ashx._upload" %>

<!DOCTYPE html>

<html xmlns="http://www.w3.org/1999/xhtml">
<head runat="server">
     <title>ART</title>
    <script type="text/javascript">

        function ShowLoad() {
            document.getElementById("Lbl_Tip").innerText = "正在提交请求...";
        }
    </script>
</head>
<body>
    <form id="form1" runat="server">
    <div style="float:left;margin-top: -8px">
   <asp:FileUpload ID="File_Upload" Width="180px" runat="server" /></div>
<div style="float:left; padding-left:40px;margin-top: -8px"><asp:Button ID="Btn_Submit" runat="server" 
        Text="上传" Height="22px"  OnClientClick="ShowLoad()"  onclick="Btn_Submit_Click" /> <asp:Label ID="Lbl_Tip" runat="server" ForeColor="Red" Font-Size="12px"></asp:Label></div>
    </form>
</body>
</html>
