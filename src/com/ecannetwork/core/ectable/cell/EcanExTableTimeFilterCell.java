package com.ecannetwork.core.ectable.cell;

import org.extremecomponents.table.bean.Column;
import org.extremecomponents.table.core.TableModel;
import org.extremecomponents.util.HtmlBuilder;

public class EcanExTableTimeFilterCell extends ExtableFilterOperatorCell {

	@Override
	public String getHtmlDisplay(TableModel model, Column column) {

		if ("date".equals(column.getCell())) { // 日期
			HtmlBuilder html = new HtmlBuilder();
			html.td(1).close();
			html.input().id("dateinput").close();

			html.append("<script type=\"text/javascript\" charset=\"utf-8\">");
			html.append("					jQuery(function($) {");
			html.append("						$.datepicker.regional[\'zh-CN\'] = {");
			html.append("						clearText : \'清除\',");
			html.append("clearStatus : \'清除已选日期\',");
			html.append("					closeText : \'关闭\',");
			html.append("					closeStatus : \'不改变当前选择\',");
			html.append("					prevText : \'&lt;上月\',");
			html.append("						prevStatus : \'显示上月\',");
			html.append("						nextText : \'下月&gt;\',");
			html.append("						nextStatus : \'显示下月\',");
			html.append("					currentText : \'今天\',");
			html.append("					currentStatus : \'显示本月\',");
			html.append("					monthNames : [ \'一月\', \'二月\', \'三月\', \'四月\', \'五月\', \'六月\', \'七月\', \'八月\',");
			html.append("							\'九月\', \'十月\', \'十一月\', \'十二月\' ],");
			html.append("					monthNamesShort : [ \'一\', \'二\', \'三\', \'四\', \'五\', \'六\', \'七\', \'八\', \'九\',");
			html.append("							\'十\', \'十一\', \'十二\' ],");
			html.append("				monthStatus : \'选择月份\',");
			html.append("						yearStatus : \'选择年份\',");
			html.append("					weekHeader : \'周\',");
			html.append("					weekStatus : \'年内周次\',");
			html.append("					dayNames : [ \'星期日\', \'星期一\', \'星期二\', \'星期三\', \'星期四\', \'星期五\', \'星期六\' ],");
			html.append("					dayNamesShort : [ \'周日\', \'周一\', \'周二\', \'周三\', \'周四\', \'周五\', \'周六\' ],");
			html.append("					dayNamesMin : [ \'日\', \'一\', \'二\', \'三\', \'四\', \'五\', \'六\' ],");
			html.append("					dayStatus : \'设置 DD 为一周起始\',");
			html.append("					dateStatus : \'选择 m月 d日, DD\',");
			html.append("					dateFormat : \'yy-mm-dd\',");
			html.append("				firstDay : 1,");
			html.append("				initStatus : \'请选择日期\',");
			html.append("				isRTL : false");
			html.append("				};");
			html.append("			$.datepicker.setDefaults($.datepicker.regional[\'zh-CN\']);");
			html.append("			$(\"#dateinput\").datepicker();");
			html.append("			});");
			html.append("				</script>");

		}
		return super.getHtmlDisplay(model, column);
	}

	public String timeInput(TableModel model, Column column) {
		return null;
	}
}
