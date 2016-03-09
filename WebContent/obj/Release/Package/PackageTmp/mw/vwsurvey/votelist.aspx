<%@ Page Title="" Language="C#" MasterPageFile="~/mw/Default.Master" AutoEventWireup="true" CodeBehind="VoteList.aspx.cs" Inherits="Plugin.VWSurvey.Admini.VWSurvey.VoteList" %>

<asp:Content ID="Content1" ContentPlaceHolderID="head" runat="server">
</asp:Content>
<asp:Content ID="Content2" ContentPlaceHolderID="ContentPlaceHolder1" runat="server">
    <div id="h_main">
        <table border="0" cellspacing="0" style="width: 100%;">
            <!--
           <tr class="daohang_title">
                <td style="text-align: left; padding-left: 3px;">
                    搜索条件↓
                </td>
                <td style="text-align: right; padding-right: 3px;">
                </td>
            </tr>
            <tr>
                <td colspan="2" style="padding-top: 5px;">
                    <table border="0" cellpadding="0" cellspacing="1" style="margin-bottom: 15px;">
                                <tr>
                                 <td class="td_title">
                                类型：</td>
                            <td class="td_content">
                                <asp:DropDownList runat="server" ID="drpType"/></td>
                                <td class="td_title">
                                培训名称：</td>
                            <td class="td_content">
                                <asp:TextBox runat="server" ID="txtSearchName"></asp:TextBox></td>
                                    <td class="td_title">
                                培训老师：</td>
                            <td class="td_content">
                                <asp:TextBox runat="server" ID="txtTeacherName"></asp:TextBox></td>
                                    <td class="td_title">
                                时间：</td>
                            <td class="td_content">
                                <asp:TextBox ID="txtBeginTime" runat="server" OnClick="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd'})"
                        SkinID="date" title="选择日期"></asp:TextBox>- <asp:TextBox ID="txtEndTime" runat="server" OnClick="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd'})"
                        SkinID="date" title="选择日期"></asp:TextBox></td>
                        </tr>
                        <tr>
                            <td colspan="2" style="text-align: left; padding-top: 5px;">
                                <asp:Button ID="btnSearch" runat="server" Text="搜 索" CssClass="btnlistOver" OnClick="btnSearch_Click"
                                    OnClientClick="javascript:funbtnClick('#ctl00_cphContent_btnSearch');"></asp:Button>
                                </td>
                        </tr>
                         </table>
                </td>
            </tr>
        -->
            <tr class="daohang_title">
                <td style="text-align: left; padding-left: 3px;">
                    列表↓ 
                </td>
                 <td style="text-align: right; padding-right: 3px;">
                     <input id="btnAdd" type="button" value="新 增" class="btnlistOver" onclick="javascript:location.href='biceoption.aspx';" />
                  
                </td>
                    
            </tr>
            <tr>
                <td colspan="2">
                 <ctl:JRepeater ID="rpt_List" runat="server" OnItemCommand="rpt_List_ItemCommand" >
                  <HeaderTemplate>
                    <table cellpadding="0" cellspacing="0" border="0" width="100%" id="tableData">
                        <tr>
                          <th> <%--<input id="cheList" type="checkbox" onclick="checkAll(this.form)" />--%></th>

                            <th>调查名称</th>
                                
                            <th>开始日期</th>
                                
                            <th>结束日期</th>
                            <th style="width: 260px;">操作</th>
                        </tr>
                       </HeaderTemplate>
                            <ItemTemplate>
                                <tr class="tr_data" onmouseout="mout(this);" onmouseover="mover(this);">
                                    <td>
                                       <%-- <input id="cheId" runat="server" value='<%#Eval("N_SysId")%>' type="checkbox" />--%></td>
                                    <td>
                                         <a href="/onlinesurvey/<%#Eval("Id") %>.html" target="_blank">  <%# Eval("C_Title") %></a>
                                    </td>
                                    <td>
                                    <%# MW.Common.Utils.GetStandardDate(Eval("DT_StartDate").ToString())%>
                                    </td>
          <td><%# MW.Common.Utils.GetStandardDate(Eval("DT_OverDate").ToString())%>
                                    </td>
                                    <td>
                                       <a href="biceoption.aspx?&Id=<%# Eval("Id") %>"> 编辑</a>

                                         <asp:LinkButton ID="lbtnCopy" CommandName="lbtnCopy" CommandArgument='<%#Eval("Id")%>'
                                            title="复制" OnClientClick="return confirm('确定复制投票项目吗？')" runat="server" CssClass="smallbutton">复制</asp:LinkButton>
                                        
                                        <asp:LinkButton  ID="lbtnDelete" CommandName="lbtnDelete" CommandArgument='<%#Eval("Id")%>'
                                            title="删除" OnClientClick="return confirm('确定删除数据吗？')" runat="server">删除</asp:LinkButton>
                                        <asp:LinkButton  ID="lbtnBuild" CommandName="lbtnBuild" CommandArgument='<%#Eval("Id")+"|"+Eval("N_SysId") %>'
                                            title="生成问卷" OnClientClick="return confirm('确定要生成问卷吗？')" runat="server">生成问卷</asp:LinkButton> 
                                        <a class="smallbutton" href="javascript:void(0)" onclick="javascript:view('发布问卷','surveypub.aspx?id=<%#Eval("id") %>&SysId=<%#Eval("N_SysId") %>');">发布问卷</a>
                                         <a class="smallbutton" href="javascript:void(0)" onclick="javascript:view('样式编辑','styledetail.aspx?id=<%#Eval("id") %>&SysId=<%#Eval("N_SysId") %>');">样式编辑</a>
                                    </td>
                                </tr>
                            </ItemTemplate>
                        <FooterTemplate>
                </table>
            </FooterTemplate>
         </ctl:JRepeater>
                </td>
            </tr>
        </table>
       </div>
    <script>
        function modify(name,url) {
            $.fn.jmodal({
                title: name,
                width: 900,
                content: function (body) {
                    body.html('loading...');
                    body.load(url);
                },
                buttonText: { ok: '<%=BaseUi.GetLang("i18n.button.ok")%>', cancel: '<%=BaseUi.GetLang("i18n.button.cancel")%>' },
                okEvent: function (data, args) {
                    $('#<%=Master.FindControl("dofrm").ClientID%>').submit();
                }
            });
        }
        function view(name, url) {
            $.fn.jmodal({
                title: name,
                width: 900,
                content: function (body) {
                    body.html('loading...');
                    body.load(url);
                },
                buttonText: { cancel: '<%=BaseUi.GetLang("i18n.button.cancel")%>' },
                okEvent: function (data, args) {
                    //$('#myForm1').submit();
                }
            });
        }
    </script>
</asp:Content>
