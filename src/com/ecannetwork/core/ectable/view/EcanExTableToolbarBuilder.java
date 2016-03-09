/**
 * 
 */
package com.ecannetwork.core.ectable.view;

import org.extremecomponents.table.core.TableModel;
import org.extremecomponents.table.view.html.BuilderUtils;
import org.extremecomponents.table.view.html.ToolbarBuilder;
import org.extremecomponents.table.view.html.toolbar.ButtonItem;
import org.extremecomponents.table.view.html.toolbar.ToolbarItemUtils;
import org.extremecomponents.util.HtmlBuilder;

import com.ecannetwork.core.i18n.I18N;

/**
 * @author liulibin@sinovatech.com
 * 
 */
public class EcanExTableToolbarBuilder extends ToolbarBuilder
{

	public EcanExTableToolbarBuilder(HtmlBuilder html, TableModel model)
	{
		super(html, model);
	}

	public void statusMessage()
	{

		if (this.getTableModel().getLimit().getTotalRows() == 0)
		{
			this.getHtmlBuilder().append(
					I18N.parse("i18n.statusbar.noResultsFound"));
		} else
		{
			Integer total = new Integer(this.getTableModel().getLimit()
					.getTotalRows());
			// Integer from = new Integer(this.model.getLimit().getRowStart() +
			// 1);
			// Integer to = new Integer(this.model.getLimit().getRowEnd());
			// Object[] messageArguments = { total, from, to };
			this.getHtmlBuilder().append(
					I18N.parse("i18n.statusbar.resultsFound", total));
		}
	}

	public void firstPageItemAsText()
	{
		int page = this.getTableModel().getLimit().getPage();
		if (BuilderUtils.isFirstPageEnabled(page))
		{
			ButtonItem item = new ButtonItem();
			item.setTooltip(I18N.parse("i18n.toolbar.tooltip.firstPage"));
			item.setContents(I18N.parse("i18n.toolbar.text.firstPage"));
			ToolbarItemUtils.buildFirstPage(this.getHtmlBuilder(), this
					.getTableModel(), item);
		}
	}

	public void prevPageItemAsText()
	{
		int page = this.getTableModel().getLimit().getPage();
		if (BuilderUtils.isPrevPageEnabled(page))
		{
			ButtonItem item = new ButtonItem();
			item.setTooltip(I18N.parse("i18n.toolbar.tooltip.prevPage"));
			item.setContents(I18N.parse("i18n.toolbar.text.prevPage"));
			ToolbarItemUtils.buildPrevPage(this.getHtmlBuilder(), this
					.getTableModel(), item);
		}
	}

	public void nextPageItemAsText()
	{
		int totalPages = BuilderUtils.getTotalPages(this.getTableModel());
		int page = this.getTableModel().getLimit().getPage();
		if (BuilderUtils.isNextPageEnabled(page, totalPages))
		{
			ButtonItem item = new ButtonItem();
			item.setTooltip(I18N.parse("i18n.toolbar.tooltip.nextPage"));
			item.setContents(I18N.parse("i18n.toolbar.text.nextPage"));
			ToolbarItemUtils.buildNextPage(this.getHtmlBuilder(), this
					.getTableModel(), item);
		}
	}

	public void lastPageItemAsText()
	{
		int totalPages = BuilderUtils.getTotalPages(this.getTableModel());
		int page = this.getTableModel().getLimit().getPage();
		if (BuilderUtils.isLastPageEnabled(page, totalPages))
		{
			ButtonItem item = new ButtonItem();
			item.setTooltip(I18N.parse("i18n.toolbar.tooltip.lastPage"));
			item.setContents(I18N.parse("i18n.toolbar.text.lastPage"));
			ToolbarItemUtils.buildLastPage(this.getHtmlBuilder(), this
					.getTableModel(), item);
		}
	}

	public void rowsDisplayedDroplist()
	{
		// int totalRows = this.getTableModel().getLimit().getTotalRows();
		// int rowDisplay = this.getTableModel().getLimit()
		// .getCurrentRowsDisplayed();

		// int totalPages = (totalRows + rowDisplay - 1) / rowDisplay;

		this.getHtmlBuilder().append(I18N.parse("i18n.statusbar.rowDisplay"));
		super.rowsDisplayedDroplist();
		this.getHtmlBuilder().append(
				I18N.parse("i18n.statusbar.rowDisplayUnit"));
		// this.getHtmlBuilder().append(",&nbsp;跳转到第");
		// this.getHtmlBuilder().input("text").size("3").value(
		// String.valueOf(this.getTableModel().getLimit().getPage()));
		// // 录入
		// this.getHtmlBuilder().append(" onkeyup=").quote().append(
		// "this.value=this.value.replace(/[^1-9]/g,''); if(this.value>")
		// .append(totalPages).append("){this.value=").append(totalPages)
		// .append(";}").quote();
		//
		// // 回车
		// this.getHtmlBuilder().onkeypress(
		// "if (event.keyCode == 13) {"
		// + new ABaneExTableActions(this.getTableModel())
		// .getInputTargetPageAction() + "return false;}")
		// .xclose();

		// this.getHtmlBuilder().append("页");
	}
}
