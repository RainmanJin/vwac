<%@ Page Title="" Language="C#" MasterPageFile="~/mw/DefaultDetail.Master" AutoEventWireup="true" CodeBehind="chexidetail.aspx.cs" Inherits="PortalWeb.mw.vwtestdrive.chexidetail" %>
<asp:Content ID="Content1" ContentPlaceHolderID="head" runat="server">

<script>
    var index = parent.layer.getFrameIndex(window.name); //获取当前窗体索引
    function GetValue() {
        var strlist = document.getElementById("<%=lbxUsersSure.ClientID%>");//获取Listbox
        var str = "";
        var txt = "";
        //遍历Listbox，取得选中项的值
        if (strlist.options.length > 0) {
            for (var i = 0; i < strlist.options.length; i++) {
                var j = strlist.options[i].value;
                str += j + ","; //把Value值串起来
                txt += strlist.options[i].text + ",";
                    
                    
            }
           
            var strtxt = $("#<%=hidetxt.ClientID%>").val();//txt隐藏域的值
            parent.$("#" + strtxt).val(txt);
            var strValue = $("#<%=hidevalue.ClientID%>").val();//value隐藏域的值
            parent.$("#" + strValue).val(str);
           
            parent.layer.close(index);
            //var strValue = str.replace(/,$/, ""); //去掉最后一个逗号
            //var strtxt = strtxt.replace(/,$/, ""); //去掉最后一个逗号
        }
        else {
            alert("No Item in Listbox");
        }
    }

    $(document).ready(function() {
        $("#button1").click(function() {
      
            add('<%=lbxUsers.ClientID%>', '<%=lbxUsersSure.ClientID%>');
            del('<%=lbxUsers.ClientID%>');
        });
        $("#button2").click(function() {
          
            add('<%=lbxUsersSure.ClientID%>', '<%=lbxUsers.ClientID%>');
            del('<%=lbxUsersSure.ClientID%>');
            
        });
    });
    function gb() {

        parent.layer.close(index);
    }
    function del(dels) {
        var objdel = document.getElementById(dels);
        for (var i = objdel.options.length - 1; i >= 0; i--) {
            if (objdel.options[i].selected) {
                objdel.remove(i);
            }
        }
    }
    function add(res, sel) {
        var objres = document.getElementById(res);
        var objsel = document.getElementById(sel);
        var customOptions;
        for (var i = 0; i < objres.options.length; i++) {
            if (objres.options[i].selected) {
                customOptions = document.createElement("OPTION");
                customOptions.text = objres.options[i].text.replace(/├/g, "").replace(/　/g, "");
                customOptions.value = objres.options[i].value;
                objsel.add(customOptions, 0);
            }
        }
        return false;
    }
</script>
<style type="text/css">
 .box_select_Users{ width:220px; }
  
</style>
</asp:Content>
<asp:Content ID="Content2" ContentPlaceHolderID="ContentPlaceHolder1" runat="server">
            <div id="h_main">
                <table border="0" cellpadding="0" cellspacing="0" width="100%">
                    <tr>
                        <td style="padding-left: 15px; vertical-align:top;" class="auto-style1">
                            <div style="background:#ccc; height: 23px; 
                                    border: #efefef 1px solid; border-bottom:0px; line-height:23px; width:200px; overflow:hidden; margin-left: auto;margin-right: auto;text-align: center">
                                待选车型
                            </div>
                            <div class="box_select_Users" style=" margin-left: auto;margin-right: auto;text-align: center">
                                <asp:ListBox ID="lbxUsers" runat="server" Height="270px" Width="200px" SelectionMode="Multiple"
                                    Style="font-family: 微软雅黑;border: 1px solid #CCC;"></asp:ListBox></div>
                        </td>
                        <td align="center" style="padding-left: 5px; padding-right: 5px;margin-left: auto;margin-right: auto;text-align: center">
                             <input type="button" id="button1" class="bgbutton" value=">>"/>
                            <%--<button  ID="btnRight" Text=">>" OnClick="btnLeft_Click"></button><br />--%>
                            <br /><br /><br />
                            <input type="button" id="button2" class="bgbutton" value="<<"/>
                            <%--<button  ID="btnLeft" Text="<<" OnClick="btnRight_Click()"></button>--%>
                        </td>
                        <td style=" vertical-align:top;">
                            <div style="background:#ccc; height: 23px;
                                    border: #efefef 1px solid; border-bottom:0px; line-height:23px; width:200px; overflow:hidden;text-align: center;">
                                已选车型</div>
                            <div class="box_select_Users">
                                <asp:ListBox ID="lbxUsersSure" runat="server" Height="270px" Width="200px" SelectionMode="Multiple"
                                    Style="font-family: 微软雅黑;border: 1px solid #CCC;"></asp:ListBox></div>
                        </td>
                    </tr>
                    <tr>
                         <td align="center" colspan="3"><button id="transmit" onclick="GetValue()" class="btn" style="margin-top: 20px">提交</button>&nbsp;&nbsp;&nbsp;&nbsp;<button id="closebtn" onclick="gb()" class="btn">关闭</button></td> 
                    </tr>
                </table>
                <asp:HiddenField ID="hidetxt" runat="Server" Value="0" />
                <asp:HiddenField ID="hidevalue" runat="Server" Value="0" />
            </div>
</asp:Content>
