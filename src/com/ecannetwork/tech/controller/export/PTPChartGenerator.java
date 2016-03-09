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

import com.ecannetwork.tech.controller.bean.testactive.Point;

public class PTPChartGenerator
{
	private List<Point> points = new ArrayList<Point>();

	private String title;
	private String dimTitle;
	private String dimValue;

	public PTPChartGenerator(String title, String dimTitle, String dimValue,
			List<Point> points)
	{
		this.title = title;
		this.points = points;
	}

	private CategoryDataset createDataset()
	{
		DefaultCategoryDataset defaultcategorydataset = new DefaultCategoryDataset();
		for (Point p : points)
		{
			defaultcategorydataset.addValue(p.getPoint(), title, p.getDim());
		}

		return defaultcategorydataset;
	}

	private JFreeChart createChart(CategoryDataset categorydataset)
	{
		JFreeChart jfreechart = ChartFactory.createLineChart(null, dimTitle,
				dimValue, categorydataset, PlotOrientation.VERTICAL, true,
				true, false);
		jfreechart.getLegend().setItemFont(new Font("宋体",Font.BOLD,12));
		//		
		CategoryPlot categoryplot = (CategoryPlot) jfreechart.getPlot();
		
		categoryplot.getDomainAxis().setLabelFont(new Font("宋体",Font.BOLD,12));
		categoryplot.getDomainAxis().setTickLabelFont(new Font("宋体",Font.BOLD,12));
		
		
		// 设定纵轴的轴线的粗细
		categoryplot.getDomainAxis().setAxisLineStroke(new BasicStroke(1f));
		// 设定图标为纵向
		categoryplot.setOrientation(PlotOrientation.HORIZONTAL);
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

		// 设置这线上现实
		// LineAndShapeRenderer lineandshaperenderer = (LineAndShapeRenderer)
		// categoryplot
		// .getRenderer();
		// lineandshaperenderer.setBaseShapesVisible(true);

		// lineandshaperenderer.setSeriesOutlinePaint(1, Color.green);

		LineAndShapeRenderer lineandshaperenderer = (LineAndShapeRenderer) categoryplot
				.getRenderer();
		lineandshaperenderer.setBaseShapesVisible(true);
		lineandshaperenderer.setDrawOutlines(true);
		lineandshaperenderer.setUseFillPaint(true);
		lineandshaperenderer.setBaseFillPaint(Color.WHITE);
		lineandshaperenderer.setSeriesStroke(0, new BasicStroke(3F));
		lineandshaperenderer.setSeriesOutlineStroke(0, new BasicStroke(2.0F));
		lineandshaperenderer.setSeriesShape(0,
				new java.awt.geom.Ellipse2D.Double(-10D, -10D, 20D, 20D));
		lineandshaperenderer.setSeriesPaint(0, Color.gray);

		// 折线上文字标签颜色
		lineandshaperenderer.setBaseItemLabelsVisible(true);
		lineandshaperenderer
				.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
		lineandshaperenderer.setBaseItemLabelPaint(Color.DARK_GRAY);

		// 设定x轴坐标
		NumberAxis numberaxis = (NumberAxis) categoryplot.getRangeAxis();
		numberaxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
		numberaxis.setAutoRangeIncludesZero(false);
		numberaxis.setUpperMargin(0.12D);
		numberaxis.setRange(new Range(0, 100));
		numberaxis.setLabelFont(new Font("宋体",Font.BOLD,12));

		return jfreechart;
	}

	public void generator(OutputStream out, int width, int height)
			throws IOException
	{
		JFreeChart jfreechart = createChart(createDataset());
		// ChartUtilities.saveChartAsPNG(new File("/tmp/BarChart3D.png"),
		// jfreechart, 800, 500);
		ChartUtilities.writeChartAsJPEG(out, jfreechart, width, height);
	}

	public static void main(String args[]) throws IOException
	{
		OutputStream out = new FileOutputStream(new File("/tmp/abc.jpeg"));
		List<Point> list = new ArrayList<Point>();
		list.add(new Point("情绪韧性", 91d));
		list.add(new Point("外向型", 33d));
		list.add(new Point("心理灵活性", 1d));
		list.add(new Point("尽责性", 97d));
		list.add(new Point("成就动机", 90d));
		list.add(new Point("风险倾向", 38d));
		list.add(new Point("团队合作能力", 77d));

		new PTPChartGenerator("性格测试结果图表", "维度", "百分位", list).generator(
				out, 500, 500);

		out.close();
	}
}
