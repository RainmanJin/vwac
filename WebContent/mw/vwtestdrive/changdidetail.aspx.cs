using System;
using System.Collections.Generic;
using System.Data;
using System.Linq;
using System.Text;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using MW.Common;

namespace PortalWeb.mw.vwtestdrive
{
    public partial class changdidetail : System.Web.UI.Page
    {
        protected int Id = 0;
        protected int type = 0;

        private MW.BLL.B_Testdriverfield bll = new MW.BLL.B_Testdriverfield();
        private MW.Model.M_Mw_testdriverfield model = new MW.Model.M_Mw_testdriverfield();
        private MW.BLL.B_Testdriver ber = new MW.BLL.B_Testdriver();
        private MW.Model.M_Mw_testdriver mer = new MW.Model.M_Mw_testdriver();


        protected void Page_Load(object sender, EventArgs e)
        {

            Id = MW.Common.LYRequest.GetInt("Id", 0);
            type = MW.Common.LYRequest.GetInt("type", 0);
            hidSysid.Value = Id.ToString();
            HiddenField1.Value = type.ToString();
            if (!IsPostBack)
            {
                InitData();
            }
        }

        private void InitData()
        {
            JBind.BindList(repPermissions, bll.GetList(0, "Id, C_Name", "parentid=0", "").Tables[0]);
            mer = ber.GetModel(Id);
            string[] num = Utils.SplitString(mer.pgfield, "|", 3);
            hidField.Value = num[type];
            this.setCheckbox(num[type]);
        }

        private string getCheckBox()
        {
            StringBuilder builder = new StringBuilder();
            foreach (RepeaterItem item in this.repPermissions.Items)
            {
                CheckBox box = (CheckBox)item.FindControl("cheFirst");
                if (box.Checked)
                {
                    builder.Append(box.ToolTip).Append(",");
                }
                Repeater repeater = (Repeater)item.FindControl("repItemPermissions");
                foreach (RepeaterItem item2 in repeater.Items)
                {
                    CheckBox box2 = (CheckBox)item2.FindControl("cheSecond");
                    if (box2.Checked)
                    {
                        builder.Append(box2.ToolTip).Append(",");
                    }
                    Repeater repeater2 = (Repeater)item2.FindControl("repChkbox");

                    foreach (RepeaterItem item3 in repeater2.Items)
                    {
                        CheckBox box3 = (CheckBox)item3.FindControl("chkthrid");
                        if (box3.Checked)
                        {
                            builder.Append(box3.ToolTip).Append(",");
                        }
                    }
                }
            }
            builder.Append(0);
            return builder.ToString();
        }

        private void setCheckbox(string p0)
        {
            string[] array = p0.Split(new char[] { ',' });
            foreach (RepeaterItem ri in repPermissions.Items)
            {
                var box = (CheckBox)ri.FindControl("cheFirst");
                if (box != null)
                {
                    if (Array.IndexOf<string>(array, box.ToolTip) > -1)
                    {
                        box.Checked = true;
                    }
                }

                var repeater = (Repeater)ri.FindControl("repItemPermissions");
                if (repeater != null)
                {
                    foreach (RepeaterItem item2 in repeater.Items)
                    {
                        var box2 = (CheckBox)item2.FindControl("cheSecond");
                        if (box2 != null)
                        {
                            if (Array.IndexOf<string>(array, box2.ToolTip) > -1)
                            {
                                box2.Checked = true;
                            }
                        }
                        var repeater2 = (Repeater)item2.FindControl("repChkbox");
                        if (repeater2 != null)
                        {
                            foreach (RepeaterItem item3 in repeater2.Items)
                            {
                                var box3 = (CheckBox)item3.FindControl("chkthrid");
                                if (Array.IndexOf<string>(array, box3.ToolTip) > -1)
                                {
                                    box3.Checked = true;
                                }
                            }
                        }
                    }
                }
            }
        }

        protected void btnSave_Click(object sender, EventArgs p1)
        {
            string a = hidSysid.Value;
            string b = HiddenField1.Value;
            string hidf = hidField.Value.Trim(',');
            mer = ber.GetModel(Convert.ToInt32(a));
            string[] san = Utils.SplitString(mer.pgfield, "|", 3);
            if (b == "0")
            {
                mer.pgfield = hidf + "|" + san[1] + "|" + san[2];
            }
            else if (b == "1")
            {
                mer.pgfield = san[0] + "|" + hidf + "|" + san[2];
            }
            else if (b == "2")
            {
                mer.pgfield = san[0] + "|" + san[1] + "|" + hidf;
            }

            ber.Updatepgfield(mer);
            MessageBox.JSLoad(this, "alert('保存成功!');window.location.href='testdriverlist.aspx'");

        }
        protected void repItemPermissions_ItemDataBound(object sender, RepeaterItemEventArgs p1)
        {
            if ((p1.Item.ItemType == ListItemType.Item) || (p1.Item.ItemType == ListItemType.AlternatingItem))
            {
                HiddenField field = (HiddenField)p1.Item.FindControl("hidItemPermissionsId");
                Repeater list = (Repeater)p1.Item.FindControl("repChkbox");
                if (field != null && list != null)
                {
                    list.DataSource = bll.GetList(0, string.Format("parentid={0}", field.Value), "");
                    list.DataBind();
                }
            }
        }
        protected void repChkbox_ItemDataBound(object sender, RepeaterItemEventArgs p1)
        {
            if ((p1.Item.ItemType == ListItemType.Item) || (p1.Item.ItemType == ListItemType.AlternatingItem))
            {
                CheckBox cb = (CheckBox)p1.Item.FindControl("chkthrid");
                var data = (DataRowView)p1.Item.DataItem;
                if (data != null && cb != null)
                {
                    cb.Text = data["C_name"].ToString();
                    cb.ToolTip = data["id"].ToString();
                }
            }
        }
        protected void repPermissions_ItemDataBound(object sender, RepeaterItemEventArgs p1)
        {
            if ((p1.Item.ItemType == ListItemType.Item) || (p1.Item.ItemType == ListItemType.AlternatingItem))
            {
                HiddenField field = (HiddenField)p1.Item.FindControl("hidPermissionsId");
                Repeater repeater = (Repeater)p1.Item.FindControl("repItemPermissions");
                if (field != null && repeater != null)
                {
                    repeater.DataSource = bll.GetList(0, string.Format("parentid={0}", field.Value), "");
                    repeater.DataBind();
                }
            }
        }

        protected void cheFirst_CheckedChanged(object sender, EventArgs p1)
        {
            foreach (RepeaterItem ri in repPermissions.Items)
            {
                var box = (CheckBox)ri.FindControl("cheFirst");
                var box2 = (CheckBox)sender;
                if (box != null && box.ToolTip == box2.ToolTip)
                {
                    var repeater = (Repeater)ri.FindControl("repItemPermissions");
                    if (repeater != null)
                    {
                        foreach (RepeaterItem item2 in repeater.Items)
                        {
                            var box3 = (CheckBox)item2.FindControl("cheSecond");
                            if (box3 != null) box3.Checked = box2.Checked;
                        }
                    }
                }

            }
        }

        protected void cheSecond_CheckedChanged(object sender, EventArgs p1)
        {
            foreach (RepeaterItem ri in repPermissions.Items)
            {
                var repeater = (Repeater)ri.FindControl("repItemPermissions");
                if (repeater != null)
                {
                    foreach (RepeaterItem item2 in repeater.Items)
                    {
                        var box = (CheckBox)item2.FindControl("cheSecond");
                        var box2 = (CheckBox)sender;
                        if (box != null && box.ToolTip == box2.ToolTip)
                        {
                            var repeater2 = (Repeater)item2.FindControl("repChkbox");
                            if (repeater2 != null)
                            {
                                foreach (RepeaterItem item3 in repeater2.Items)
                                {
                                    CheckBox box3 = (CheckBox)item3.FindControl("chkthrid");
                                    box3.Checked = box2.Checked;
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}