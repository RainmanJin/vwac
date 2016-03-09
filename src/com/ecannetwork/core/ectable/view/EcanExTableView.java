package com.ecannetwork.core.ectable.view;

import org.extremecomponents.table.core.TableModel;
import org.extremecomponents.table.view.AbstractHtmlView;
import org.extremecomponents.util.HtmlBuilder;

/**
 * @author Jeff Johnston
 */
public class EcanExTableView extends AbstractHtmlView
{
	protected void beforeBodyInternal(TableModel model)
	{
		getTableBuilder().tableStart();

		getTableBuilder().theadStart();

		getTableBuilder().titleRowSpanColumns();

		getTableBuilder().headerRow();
		
		getTableBuilder().filterRow();

		getTableBuilder().theadEnd();

		getTableBuilder().tbodyStart();
	}

	protected void afterBodyInternal(TableModel model)
	{
		getCalcBuilder().defaultCalcLayout();

		toolbar(getHtmlBuilder(), getTableModel());

		getTableBuilder().tbodyEnd();

		getTableBuilder().tableEnd();
	}

	protected void toolbar(HtmlBuilder html, TableModel model)
	{
		new EcanExTableToolbar(html, model).layout();
	}
}
