package com.ecannetwork.core.ectable.cell;

import org.extremecomponents.table.bean.Column;
import org.extremecomponents.table.cell.FilterCell;
import org.extremecomponents.table.core.TableModel;
import org.extremecomponents.table.view.html.BuilderUtils;
import org.extremecomponents.table.view.html.TableActions;
import org.extremecomponents.util.HtmlBuilder;

/**
 * 
 * 
 */
public class ExtableFilterOperatorCell extends FilterCell
{

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.extremecomponents.table.cell.FilterCell#getHtmlDisplay(org.
	 * extremecomponents.table.core.TableModel,
	 * org.extremecomponents.table.bean.Column)
	 */
	@Override
	public String getHtmlDisplay(TableModel model, Column column)
	{
		if (column.getProperty().equals("oper"))
		{
			// 查询
			HtmlBuilder html = new HtmlBuilder();
			html.td(1).close();
			html.a("#nogo").onclick(new TableActions(model).getFilterAction())
					.close();
			html.img().src(BuilderUtils.getImage(model, "filter")).title(
					model.getMessages().getMessage("toolbar.tooltip.filter"))
					.alt(model.getMessages().getMessage("toolbar.text.filter"))
					.border("0").close();
			html.aEnd();

			html.nbsp();

			// 清除
			html.a("#nogo").onclick(new TableActions(model).getClearAction())
					.close();
			html.img().src(BuilderUtils.getImage(model, "clear")).title(
					model.getMessages().getMessage("toolbar.tooltip.clear"))
					.alt(model.getMessages().getMessage("toolbar.text.clear"))
					.border("0").close();

			html.aEnd();
			html.tdEnd();

			return html.toString();
		}

		return super.getHtmlDisplay(model, column);
	}
}