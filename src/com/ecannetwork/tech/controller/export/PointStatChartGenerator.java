package com.ecannetwork.tech.controller.export;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.Range;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import com.ecannetwork.tech.controller.bean.testactive.StatPoint;


public class PointStatChartGenerator
{
	private String titleIndex;
	private String titlePoints;
	private String xTitle;
	private String yTitle;
	private List<StatPoint> list;

	public PointStatChartGenerator(String titleIndex, String titlePoints, String xTitle,
			String yTitle, List<StatPoint> list)
	{
		this.titleIndex = titleIndex;
		this.titlePoints = titlePoints;
		this.xTitle = xTitle;
		this.yTitle = yTitle;
		this.list = list;
	}

	private CategoryDataset createDataset()
	{
		DefaultCategoryDataset defaultcategorydataset = new DefaultCategoryDataset();

		for (StatPoint p : list)
		{
			defaultcategorydataset.addValue(p.getIndex(), this.titleIndex, p
					.getDimName());
			defaultcategorydataset.addValue(p.getPoint28(), this.titlePoints, p
					.getDimName());
		}

		return defaultcategorydataset;
	}

	private JFreeChart createChart(CategoryDataset categorydataset)
	{
		JFreeChart jfreechart = ChartFactory.createLineChart(null, this.xTitle,
				this.yTitle, categorydataset, PlotOrientation.VERTICAL, true,
				true, false);

		jfreechart.getLegend().setItemFont(new Font("宋体",Font.BOLD,12));
		
		//		
		CategoryPlot categoryplot = (CategoryPlot) jfreechart.getPlot();
		categoryplot.getDomainAxis().setLabelFont(new Font("宋体",Font.BOLD,12));
		categoryplot.getDomainAxis().setTickLabelFont(new Font("宋体",Font.BOLD,12));
		
		// 设定纵轴的轴线的粗细
		categoryplot.getDomainAxis().setAxisLineStroke(new BasicStroke(2f));
		// 设定图标为纵向
		// categoryplot.setOrientation(PlotOrientation.HORIZONTAL);
		categoryplot.setBackgroundAlpha(0.3f);

		// 设定分类背景颜色
		// categoryplot.setBackgroundPaint(Color.white);
		categoryplot.setDomainGridlinesVisible(true);
		// 分类维度的线条
		categoryplot.setDomainGridlinePaint(Color.blue);
		// 数据分割线
		categoryplot.setRangeGridlinesVisible(true);
		// 数据线分割线颜色
		categoryplot.setRangeGridlinePaint(Color.GRAY);

		// 设置这线上
		LineAndShapeRenderer lineandshaperenderer = (LineAndShapeRenderer) categoryplot
				.getRenderer();
		lineandshaperenderer.setBaseShapesVisible(true);
		lineandshaperenderer.setDrawOutlines(true);
		lineandshaperenderer.setUseFillPaint(true);
		lineandshaperenderer.setBaseFillPaint(Color.WHITE);

		// 线条一
		lineandshaperenderer.setSeriesStroke(0, new BasicStroke(3F));
		lineandshaperenderer.setSeriesOutlineStroke(0, new BasicStroke(2.0F));
		lineandshaperenderer.setSeriesShape(0,
				new java.awt.geom.Ellipse2D.Double(-10D, -10D, 20D, 20D));
		lineandshaperenderer.setSeriesPaint(0, Color.green);

		// 线条二
		lineandshaperenderer.setSeriesStroke(1, new BasicStroke(3F));
		lineandshaperenderer.setSeriesOutlineStroke(1, new BasicStroke(2.0F));
		lineandshaperenderer.setSeriesShape(1,
				new java.awt.geom.Ellipse2D.Double(-10D, -10D, 20D, 20D));
		lineandshaperenderer.setSeriesPaint(1, Color.DARK_GRAY);

		// 折线上文字标签颜色
		lineandshaperenderer.setBaseItemLabelsVisible(true);
		lineandshaperenderer
				.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
		lineandshaperenderer.setBaseItemLabelPaint(Color.RED);
		
		// 设定x轴坐标
		NumberAxis numberaxis = (NumberAxis) categoryplot.getRangeAxis();
		numberaxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
		numberaxis.setAutoRangeIncludesZero(false);
		numberaxis.setUpperMargin(0.12D);
		numberaxis.setRange(new Range(0, 6));
		numberaxis.setLabelFont(new Font("宋体",Font.BOLD,12));

		return jfreechart;
	}

	public void generator(OutputStream out, int width, int height)
			throws IOException
	{
		JFreeChart jfreechart = createChart(createDataset());
		ChartUtilities.writeChartAsJPEG(out, jfreechart, width, height);
	}

	public static void main(String args[]) throws IOException
	{
		List<StatPoint> list = new ArrayList<StatPoint>();

		list.add(new StatPoint("尽责性", 3d, 4d));
		list.add(new StatPoint("外向性", 2d, 5d));
		list.add(new StatPoint("团队合作能力", 4d, 3d));
		list.add(new StatPoint("心理灵活性", 3d, 2d));

		list.add(new StatPoint("情绪韧性", 4d, 4d));
		list.add(new StatPoint("沟通能力", 2d, 4.4d));

		list.add(new StatPoint("成就动机", 3d, 3.4d));
		list.add(new StatPoint("影响力", 5d, 5d));
		list.add(new StatPoint("风险倾向", 4d, 2d));
		list.add(new StatPoint("系统化的工作方法", 3d, 1d));
		list.add(new StatPoint("顾客导向", 2.5d, 4.8d));
		list.add(new StatPoint("专业知识", 5d, 2.8d));

		FileOutputStream out = new FileOutputStream(new File("/tmp/abcd.jpg"));
		new PointStatChartGenerator("标准", "用户", "维度", "得分", list).generator(out, 1200,
				500);
		out.close();

	}
}
