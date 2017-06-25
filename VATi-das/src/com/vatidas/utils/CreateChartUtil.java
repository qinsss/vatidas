package com.vatidas.utils;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Paint;
import java.awt.Rectangle;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.DefaultDrawingSupplier;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.renderer.category.StandardBarPainter;
import org.jfree.chart.renderer.xy.StandardXYBarPainter;
import org.jfree.chart.title.TextTitle;
import org.jfree.chart.title.Title;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.ui.HorizontalAlignment;
import org.jfree.ui.RectangleEdge;
import org.jfree.ui.RectangleInsets;
import org.jfree.ui.VerticalAlignment;

import com.vatidas.entity.InOutStatistic;

public class CreateChartUtil {
	/**
	 * 创建图表
	 * @param analyzeItem
	 * @param inOutList
	 * @param analyzeView
	 * @param startYm
	 * @param endYm
	 * @return
	 */
	public static JFreeChart createAnalyzeView(String analyzeItem, List<InOutStatistic> inOutList, String analyzeView, Date startYm, Date endYm) {
		JFreeChart chart = null;
		Iterator<InOutStatistic> iterator = inOutList.iterator();
		//创建一个二维chart
		chart = createChart2D(iterator, analyzeItem, startYm, endYm, analyzeView);
		//创建三维chart
		//chart = createChart3D();
		//设置图表的样式和渲染
		setStyle(chart, analyzeView);
		return chart;
	}
	
	
	/**
	 * 根据图表类别创建所需图表
	 * @param iterator
	 * @param analyzeItem
	 * @param startYm
	 * @param endYm
	 * @param analyzeView
	 * @return
	 */
	private static JFreeChart createChart2D(Iterator<InOutStatistic> iterator, String analyzeItem, Date startYm, Date endYm, String analyzeView) {
		JFreeChart chart = null;
		CategoryDataset cds = null;
		PieDataset pds = null;
		StringBuffer title = getChartTitle(analyzeItem, startYm, endYm, analyzeView);
		//分析视图类型判断
		if("bar".equals(analyzeView)){ //创建柱状图
			//获取数据集
			cds = getAnalyzeDateSetLinear(analyzeItem, iterator);
			//设置主题
			setTheme();
			chart = ChartFactory.createBarChart(title+"柱状图","", "金额/万元", cds );
		}else if("line".equals(analyzeView)){ //创建折线图
			cds = getAnalyzeDateSetLinear(analyzeItem, iterator);
			//设置主题
			setTheme();
			chart = ChartFactory.createLineChart(title+"折线图", "", "金额/万元", cds, PlotOrientation.VERTICAL, true, true, false );
		}else if("pie".equals(analyzeView)){	//创建饼图
			//设置主题
			setTheme();
			pds = getAnalyzeDateSetPie(analyzeItem, iterator);
			chart = ChartFactory.createPieChart(title+"饼图", pds , true, false, false);
		}else{
			return null;
		}
		//设置chart抗锯齿 和添加一个副题作为提示
		chart.setTextAntiAlias(true);
		Title titleTip = new TextTitle("如果有未显现的月则表示该月没有数据", new Font("仿宋",Font.PLAIN,15),
				new Color(119,136,153),RectangleEdge.TOP, HorizontalAlignment.RIGHT,VerticalAlignment.BOTTOM,new RectangleInsets(0, 0, 0, 5));
		chart.addSubtitle(titleTip);
		return chart;
	}
	
	/**
	 * 获取创建图表的标题
	 * @param analyzeItem
	 * @param startYm
	 * @param endYm
	 * @param analyzeView
	 * @return
	 */
	private static StringBuffer getChartTitle(String analyzeItem, Date startYm, Date endYm, String analyzeView){
		StringBuffer title = new StringBuffer();
		String invoicetype;
		String moneyType;
		title.append(CommonUtils.DateTransform(startYm));
		title.append("至");
		title.append(CommonUtils.DateTransform(endYm));
		if(analyzeItem.startsWith("inOut"))invoicetype = "进销项";
		else if(analyzeItem.startsWith("in"))invoicetype = "进项";
		else if(analyzeItem.startsWith("out"))invoicetype = "销项";
		else invoicetype="";
		title.append(invoicetype);
		title.append("发票");
		if(analyzeItem.contains("Tax"))	moneyType = "税额";
		else if(analyzeItem.contains("vat")) moneyType = "增值税额";
		else moneyType = "金额";
		title.append(moneyType);
		System.out.println(title);
		return title;
	}
	
	/**
	 * 创建折线图和柱状图的数据集
	 * @param analyzeItem
	 * @param iterator
	 * @return
	 */
	private static DefaultCategoryDataset getAnalyzeDateSetLinear(String analyzeItem, Iterator<InOutStatistic> iterator) {
		InOutStatistic inOutStatistic = null;
		DefaultCategoryDataset cds = new DefaultCategoryDataset();
		if("inOutMoney".equals(analyzeItem)){	//进销项金额的数据集构造  应将进项和单项分开来显示
			while(iterator.hasNext()){
				inOutStatistic = iterator.next();
				if("进项".equals(inOutStatistic.getType())){
					cds.addValue(inOutStatistic.getMoney(), "进项金额", CommonUtils.DateTransform(inOutStatistic.getYearMonth()));
				}else{
					cds.addValue(inOutStatistic.getMoney(), "销项金额", CommonUtils.DateTransform(inOutStatistic.getYearMonth()));
				}
			}
		}else if("inOutTaxMoney".equals(analyzeItem)){	//进销项税额数据集构造
			while(iterator.hasNext()){
				inOutStatistic = iterator.next();
				System.out.println(inOutStatistic.getTaxMoney());
				if("进项".equals(inOutStatistic.getType())){
					cds.addValue(inOutStatistic.getTaxMoney(), "进项税额", CommonUtils.DateTransform(inOutStatistic.getYearMonth()));
				}else{
					cds.addValue(inOutStatistic.getTaxMoney(), "销项税额", CommonUtils.DateTransform(inOutStatistic.getYearMonth()));
				}
			}
		}else if("vatMoney".equals(analyzeItem)){	
			Map<Date, BigDecimal> vat = CommonUtils.getVat(iterator);
			for(Date key : vat.keySet()){
				cds.addValue(vat.get(key),"增值税额", CommonUtils.DateTransform(key));
			}
		}
		else if(analyzeItem.contains("Tax")){	//最后判断单项
			while(iterator.hasNext()){
				inOutStatistic = iterator.next();
				cds .addValue(inOutStatistic.getTaxMoney(), "发票税额", CommonUtils.DateTransform(inOutStatistic.getYearMonth()));
			}
			System.out.println("Tax");
		}else{
			int i =0 ;
			while(iterator.hasNext()){
				inOutStatistic = iterator.next();
				cds.addValue(inOutStatistic.getMoney(), "发票金额", CommonUtils.DateTransform(inOutStatistic.getYearMonth()));
				System.out.println(CommonUtils.DateTransform(inOutStatistic.getYearMonth()));
				System.out.println(i++);
			}
		}
		return cds;
	}
	
	/**
	 * 构造饼图的数据集
	 * @param analyzeItem
	 * @param iterator
	 * @return
	 */
	private static DefaultPieDataset getAnalyzeDateSetPie(String analyzeItem,
		Iterator<InOutStatistic> iterator) {
		InOutStatistic inOutStatistic = null;
		DefaultPieDataset pds = new DefaultPieDataset();
		//判断分析项为税额
		if(analyzeItem.contains("Tax")){
			while(iterator.hasNext()){
				inOutStatistic = iterator.next();
				pds.setValue(CommonUtils.DateTransform(inOutStatistic.getYearMonth()), inOutStatistic.getTaxMoney());
			}
		}else {
			while(iterator.hasNext()){
				inOutStatistic = iterator.next();
				pds.setValue(CommonUtils.DateTransform(inOutStatistic.getYearMonth()), inOutStatistic.getMoney());
			}
		}
		return pds;
	}
	
	
	/**
	 * 根据图表类型设置相应样式
	 * @param chart
	 * @param analyzeView
	 */
	private static void setStyle(JFreeChart chart, String analyzeView) {
		if("pie".equals(analyzeView)){
			setPieStyle(chart);
		}else if("bar".equals(analyzeView)){
			setBarStyle(chart);
		}else if("line".equals(analyzeView)){
			setLineStyle(chart);
		}else{
			return;
		}
	}
	

	//图表label的颜色  柱子/折线/饼的颜色
	private static Color[] CHART_COLORS = {
			new Color(31,129,188),new Color(92,92,97),new Color(144,237,125), new Color(253,236,109), new Color(128,133,232),
			new Color(158,90,102),new Color(255, 204, 102), new Color(255,188,117),
			new Color(153,158,255), new Color(255,117,153)};
	
	private static final String NO_DATA_MESSAGE = "没有有效数据";
	/**
	 * 对所有图表统一主题
	 */
	public static void setTheme() {
		Font titleFont = new Font("仿宋",Font.BOLD,21); //图表标题字体
		Font axisFont = new Font("仿宋",Font.BOLD,17);  //坐标轴标题字体
		Font dateFont = new Font("仿宋",Font.BOLD,15);  //数据字体
		StandardChartTheme theme = new StandardChartTheme("cn");//设置中文
		theme.setExtraLargeFont(titleFont);//设置标题字体
		theme.setTitlePaint(new Color(31,121,170));//标题颜色
		theme.setLargeFont(axisFont);//设置坐标轴字体
		theme.setSmallFont(axisFont);
		
		theme.setRegularFont(dateFont);	//设置图例字体
		
		theme.setLegendBackgroundPaint(new Color(218,218,218));//标注背景颜色
		theme.setLegendItemPaint(new Color(67, 67, 72));	   //标注字体颜色
		
		theme.setChartBackgroundPaint(new Color(218,218,218));//图表背景色
		
		//色源
		Paint[] OUTLINE_PAINT_SEQUENCE = new Paint[] { Color.WHITE };
		DefaultDrawingSupplier drawingSupplier = new DefaultDrawingSupplier(CHART_COLORS, CHART_COLORS, OUTLINE_PAINT_SEQUENCE,
				DefaultDrawingSupplier.DEFAULT_STROKE_SEQUENCE, DefaultDrawingSupplier.DEFAULT_OUTLINE_STROKE_SEQUENCE,
				DefaultDrawingSupplier.DEFAULT_SHAPE_SEQUENCE);
		theme.setDrawingSupplier(drawingSupplier);
		
		theme.setPlotBackgroundPaint(new Color(232,232,232));// 绘制区域背景色
		theme.setPlotOutlinePaint(Color.WHITE);// 绘制区域外边框

		theme.setAxisOffset(new RectangleInsets(0,5,5,5));//设置X-Y坐标轴偏移量
		theme.setDomainGridlinePaint(new Color(192, 208, 224));// X坐标轴垂直网格颜色
		theme.setRangeGridlinePaint(new Color(192, 192, 192));// Y坐标轴水平网格颜色

		theme.setBaselinePaint(Color.WHITE);
		theme.setAxisLabelPaint(new Color(31,121,170));// 坐标轴标题文字颜色
		theme.setTickLabelPaint(new Color(67, 67, 72));// 刻度数字颜色
		theme.setBarPainter(new StandardBarPainter());// 设置柱状图渲染
		theme.setXYBarPainter(new StandardXYBarPainter());// XYBar渲染，只使用颜色渲染，不采用渐变

		theme.setItemLabelPaint(new Color(31,121,170));
		theme.setThermometerPaint(Color.white);// 温度计
		ChartFactory.setChartTheme(theme);
	}
	
	/**
	 * 柱状图样式
	 * @param chart
	 */
	private static void setBarStyle(JFreeChart chart) {
		CategoryPlot plot = (CategoryPlot) chart.getPlot();
		plot.setNoDataMessage(NO_DATA_MESSAGE);
		plot.setInsets(new RectangleInsets(0, 5, 5, 5));
		Color axisColor = new Color(31, 121, 170);
		plot.getDomainAxis().setAxisLinePaint(axisColor);// X坐标轴颜色
		plot.getDomainAxis().setTickMarkPaint(axisColor);// X坐标轴标记竖线颜色
		ValueAxis axis = plot.getRangeAxis();
		axis.setAxisLinePaint(axisColor);// Y坐标轴颜色
		axis.setTickMarkPaint(axisColor);// Y坐标轴标记竖线颜色
		axis.setTickMarksVisible(false);
		plot.setRangeGridlinePaint(new Color(222, 220, 227));// Y轴网格线条
		plot.setRangeGridlineStroke(new BasicStroke(0.5F));
		axis.setUpperMargin(0.1);// 设置顶部Y坐标轴间距,防止数据无法显示
		axis.setLowerMargin(0.1);// 设置底部Y坐标轴间距
		chart.getLegend().setFrame(new BlockBorder(new Color(218,218,218)));// 设置标注无边框
		BarRenderer renderer = (BarRenderer) plot.getRenderer();
		//设置柱子标签的数据显示格式
		renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator("{2}", new DecimalFormat("#.00")));
		renderer.setMaximumBarWidth(0.05);//设置柱子最大宽度
		renderer.setItemMargin(0);
		renderer.setBaseItemLabelsVisible(true);
		plot.setRenderer(renderer);
	}
	
	/**
	 * 设置饼图样式	
	 * @param chart
	 */
	private static void setPieStyle(JFreeChart chart) {
		PiePlot plot = (PiePlot) chart.getPlot();
		plot.setNoDataMessage(NO_DATA_MESSAGE);
		plot.setNoDataMessageFont(new Font("仿宋",Font.BOLD,30));
		plot.setIgnoreNullValues(true); //忽略无值分类
		plot.setCircular(true);		//饼图为正圆
		plot.setInsets(new RectangleInsets(0, 5, 5, 5));
		plot.setLabelBackgroundPaint(Color.white);// 分类标签的底色
		plot.setLabelFont(new Font("仿宋",Font.BOLD, 16));	//分类标签字体
		plot.setLabelPaint(new Color(31, 121, 170));//分类标签字体颜色
		plot.setLabelLinkPaint(new Color(192, 192, 192)); //线的颜色
		plot.setLabelLinkStroke(new BasicStroke(2));//线的笔触
		plot.setLabelGenerator(new StandardPieSectionLabelGenerator("{0}:{1}元"+'\n'+"占百分比{2}",new DecimalFormat("#.00"),new DecimalFormat("#.00%")));//设置显示格式
		plot.setLegendItemShape(new Rectangle(10, 10));
		plot.setLabelGap(0.05);
		plot.setLabelShadowPaint(null);	//去掉显示数字标签的阴影
		//plot.setLabelOutlinePaint(null);
		plot.setShadowPaint(null);	//去掉饼的阴影
	}
	
	/**
	 * 设置折线图样式
	 * @param chart
	 */
	private static void setLineStyle(JFreeChart chart) {
		CategoryPlot plot = chart.getCategoryPlot();
		plot.setNoDataMessage(NO_DATA_MESSAGE);
		plot.setInsets(new RectangleInsets(0, 5, 5, 5), false);
		Color axisColor = new Color(31, 121, 170);
		plot.getDomainAxis().setAxisLinePaint(axisColor);// X坐标轴颜色
		plot.getDomainAxis().setTickMarkPaint(axisColor);// X坐标轴标记竖线颜色
		ValueAxis axis = plot.getRangeAxis();
		axis.setAxisLinePaint(axisColor);// Y坐标轴颜色
		axis.setTickMarkPaint(axisColor);// Y坐标轴标记竖线颜色
		axis.setTickMarksVisible(false);//y轴刻度值不可见
		plot.setRangeGridlinePaint(new Color(222, 220, 227));// Y轴网格线条
		plot.setRangeGridlineStroke(new BasicStroke(1.0f));
		axis.setUpperMargin(0.1);// 设置顶部Y坐标轴间距,防止数据无法显示
		axis.setLowerMargin(0.1);// 设置底部Y坐标轴间距
		chart.getLegend().setFrame(new BlockBorder(new Color(218,218,218)));// 设置标注无边框
		//获取渲染器
		LineAndShapeRenderer renderer = (LineAndShapeRenderer) plot.getRenderer();
		/*renderer.setBaseStroke(new BasicStroke(5.0f));
		for(int i = 0; i < plot.getDomainAxisCount(); i++)
			renderer.setSeriesStroke(i, new BasicStroke(3.0f));//设置线条的笔触*/
		renderer.setBaseItemLabelsVisible(true);
		//renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator(StandardCategoryItemLabelGenerator.DEFAULT_LABEL_FORMAT_STRING,
			//	NumberFormat.getInstance()));
		//renderer.setBasePositiveItemLabelPosition(new ItemLabelPosition(ItemLabelAnchor.INSIDE5, TextAnchor.HALF_ASCENT_CENTER));//数据显示位置
		renderer.setBaseShapesVisible(true);
		plot.setRenderer(renderer);
	}
}
