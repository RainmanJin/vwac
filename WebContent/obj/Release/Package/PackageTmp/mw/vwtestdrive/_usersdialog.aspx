﻿<%@ Page Title="" Language="C#" MasterPageFile="~/mw/DefaultDetail.Master" AutoEventWireup="true" CodeBehind="_usersdialog.aspx.cs" Inherits="PortalWeb.mw.vwtestdrive._usersdialog" %>
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
                    txt += strlist.options[i].text+",";
                }
                var strtxt1 = $("#<%=hidetxt1.ClientID%>").val();//txt隐藏域的值
                parent.$("#" + strtxt1).val(txt);
                var strValue1 = $("#<%=hidevalue1.ClientID%>").val();//value隐藏域的值
                parent.$("#" + strValue1).val(str);
           
                parent.layer.close(index);
            
                //var strValue = str.replace(/,$/, ""); //去掉最后一个逗号
                //var strtxt1 = strtxt1.replace(/,$/, ""); //去掉最后一个逗号
            
            }
            else {
                alert("No Item in Listbox");
            }
        }
        function gb() {

            parent.layer.close(index);
        }

        $(document).ready(function() {
            $("#button1").click(function() {
                var movevalue = $("#<%=lbxUsers.ClientID%> option:selected").val();
                var movetxt = $("#<%=lbxUsers.ClientID%> option:selected").text();
                if (movevalue == undefined) {
                    return;
                }
                $("#<%=lbxUsersSure.ClientID%>").append("<option value=" + movevalue + ">" + movetxt + "</option>");
                $("#<%=lbxUsers.ClientID%> option:selected").remove();
            })
            $("#button2").click(function() {
                var movevalue = $("#<%=lbxUsersSure.ClientID%> option:selected").val();
                var movetxt = $("#<%=lbxUsersSure.ClientID%> option:selected").text();
                if (movevalue == undefined) {
                    return;
                }
                $("#<%=lbxUsers.ClientID%>").append("<option value=" + movevalue + ">" + movetxt + "</option>");
                $("#<%=lbxUsersSure.ClientID%> option:selected").remove();
            });
        });

    </script>
<style type="text/css">
.box_select_Users{ width:220px; overflow:hidden;}
</style>
</asp:Content>
<asp:Content ID="Content2" ContentPlaceHolderID="ContentPlaceHolder1" runat="server">
            <div id="h_main">
                <table border="0" cellpadding="0" cellspacing="0" width="100%">
                    <tr>
                        <td style="padding-left: 15px; vertical-align:top;">
                            <div style="background:#ccc; height: 23px; padding-left: 10px;
                                    border: #828790 1px solid; border-bottom:0px; line-height:23px; width:190px; overflow:hidden;margin-left: auto;margin-right: auto;text-align: center">
                                待选用户
                            </div>
                            <div class="box_select_Users" style="margin-left: auto;margin-right: auto;text-align: center">
                                <asp:ListBox ID="lbxUsers" runat="server" Height="270px" Width="200px" SelectionMode="Multiple"
                                    Style="font-family: 微软雅黑;"></asp:ListBox></div>
                        </td>
                        <td style="padding-left: 5px; padding-right: 5px;">
                            <input type="button" id="button1" class="bgbutton" value=">>"/>
                            <br /><br /><br />
                            <input type="button" id="button2" class="bgbutton" value="<<"/>
                        </td>
                        <td style=" vertical-align:top;">
                            <div style="background:#ccc; height: 23px; padding-left: 10px;
                                    border: #828790 1px solid; border-bottom:0px; line-height:23px; width:190px; overflow:hidden;">
                                已选用户</div>
                            <div class="box_select_Users">
                                <asp:ListBox ID="lbxUsersSure" runat="server" Height="270px" Width="200px" SelectionMode="Multiple"
                                    Style="font-family: 微软雅黑;"></asp:ListBox></div>
                        </td>
                    </tr>
                    <tr>
                      <td align="center" colspan="3"><button id="transmit" onclick="GetValue()" class="btn">提交</button>&nbsp;&nbsp;&nbsp;&nbsp;<button id="closebtn" onclick="gb()" class="btn">关闭</button></td> 
                    </tr>
                      <asp:HiddenField ID="hidetxt1" runat="Server" Value="0" />
                       <asp:HiddenField ID="hidevalue1" runat="Server" Value="0" />
                </table>
            </div>
</asp:Content>
