/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.fm.common;

/**
 *
 * @author Administrator
 */
import static com.goldsign.fm.common.AppUtil.SelectedSortViewvos;
import com.goldsign.fm.vo.DrawResult;
import com.goldsign.fm.vo.ViewVo;
import java.util.*;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import javafx.scene.shape.Circle;
import org.apache.log4j.Logger;


import org.jfree.chart.*;


import org.jfree.chart.renderer.category.BarRenderer3D;
import org.jfree.chart.renderer.category.LineRenderer3D;



import org.jfree.chart.renderer.AbstractRenderer;


import org.jfree.chart.axis.*;
import org.jfree.chart.block.LineBorder;
import org.jfree.chart.plot.*;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.ui.TextAnchor;



import org.jfree.chart.labels.*;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.title.LegendTitle;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.CategoryDataset;
import org.jfree.ui.HorizontalAlignment;
import org.jfree.ui.RectangleEdge;
import org.jfree.ui.RectangleInsets;
import org.jfree.util.TableOrder;

//import org.jfree.ui.Spacer;

//import junit.framework.Assert;
public class DrawUtil {

    private static Logger logger = Logger.getLogger(DrawUtil.class.getName());
    private final int picW = 800;
    private final int picH = 300;
    private final int picWScreen = 900;
    private final int picHScreen = 330;
    private final String IN_NAME = "进";
    private final String OUT_NAME = "出";
    private final String IN_NAME_ENGLISH = "Entry";
    private final String OUT_NAME_ENGLISH = "Exit";
    public static HashMap COLOR_LINES =new HashMap();//线路颜色，主键为线路ID,值为颜色
    public static HashMap COLOR_5MinLINES =new HashMap();//五分钟客流进出站线路颜色，主键为线路ID,值为颜色
    public static HashMap COLOR_CARDS = new HashMap();//各票卡子类型颜色，主键为票卡主类型||子类型ID,值为颜色
    
    
    //图片属性
    public static Color COLOR_CHART_BACKGROUD = new Color(0, 101, 153); //图片背景颜色
    public static Color COLOR_CHART_BORDE = new Color(0, 101, 153); //图片边框颜色
    private final static Font FONT_CHART_TITLE = new Font("黑体", Font.CENTER_BASELINE, 20); //标题的字体
    //绘图区属性
    public static Color COLOR_PLOT_BACKGROUND = new Color(0, 101, 153); //绘图区背景颜色
    public static Color COLOR_PLOT_OUTLINE = new Color(0, 101, 153); //绘图区边框颜色
    public static Color COLOR_PLOT_LABEL = new Color(255, 255, 255); //字颜色
    public static Color COLOR_PLOT_LABEL_BACKGROUD = new Color(0, 101, 153); //字的背景颜色
    public static Color COLOR_PLOT_LABEL_SHADOW = new Color(0, 101, 153); //字的阴影颜色
    public static Color COLOR_PLOT_LABEL_OUTLINE = new Color(0, 101, 153); //字的边框颜色
    private final static Font FONT_PLOT_PIE_LABEL = new Font("黑体", Font.CENTER_BASELINE, 9); //餠图的字体
    public static Color COLOR_PLOT_SECTION_OUTLINE = new Color(255, 255, 255); //字的边框颜色
    //纵坐标属性
    public static Color COLOR_COLUMN_AXIS_LABEL = new Color(255, 255, 255); //坐标轴标题颜色
    private final static Font FONT_COLUMN_AXIS_LABEL = new Font("黑体", Font.CENTER_BASELINE, 18); //坐标轴标题字体
    public static Color COLOR_COLUMN_AXIS_TICK_LABEL = new Color(255, 255, 255); //坐标轴标尺值颜色
    private final static Font FONT_COLUMN_AXIS_TICK_LABEL = new Font("黑体", Font.CENTER_BASELINE, 18); //坐标轴标尺值字体\
    public static Color COLOR_COLUMN_AXIS = new Color(0, 101, 153); //坐标轴颜色
    //横坐标属性
    public static Color COLOR_ROW_AXIS_LABEL = new Color(255, 255, 255); //坐标轴标题颜色
    private final static Font FONT_ROW_AXIS_LABEL = new Font("黑体", Font.CENTER_BASELINE, 18); //坐标轴标题字体
    public static Color COLOR_ROW_AXIS = new Color(0, 101, 153); //坐标轴颜色
    public static Color COLOR_ROW_AXIS_TICK_LABEL = new Color(255, 255, 255); //坐标轴标尺值颜色
    private final static Font FONT_ROW_AXIS_TICK_LABEL = new Font("黑体", Font.CENTER_BASELINE, 18); //坐标轴标尺值字体
    private final static Font FONT_ROW_AXIS_TICK_LABEL_BIG = new Font("黑体", Font.BOLD, 16); //坐标轴标尺值字体
    //设置绘图笔属性
    private final static Font FONT_RENDERER_BAR_LABEL = new Font("黑体", Font.BOLD,18); //柱体的字体
    public static Color COLOR_RENDERER_AXISWALL = new Color(0, 102, 102);//new Color(85, 185, 157); //三维坐标墙颜色
    public static Color COLOR_RENDERER_LABEL = new Color(255, 255, 255); //字颜色
    //设置图例属性
    private final static Font FONT_LEGEND_LABEL = new Font("黑体", Font.BOLD, 14); //图例的字体
    public static Color COLOR_LEGEND_BACKGROUND = new Color(0, 101, 153); //背景颜色
    public static Color COLOR_LEGEND_OUTLINE = new Color(255, 255, 255); //图例边框线颜色
    public static Color COLOR_LEGEND_LABEL = new Color(255, 255, 255); //图例边框线颜色
    
    private boolean isExistingValidDataForLineOne = false;
    private boolean isExistingValidDataForLineTwo = false;
    private boolean isExistingValidDataForLineThree = false;
    private boolean isExistingValidDataForLineFour = false;
    private boolean isExistingValidDataForLineFive = false;
    private boolean isExistingValidDataForLineSix = false;
    private boolean isExistingValidDataForLineSeven = false;
    private boolean isExistingValidDataForLineEight = false;
    private boolean isExistingValidDataForLineNine = false;
    private boolean isExistingValidDataForLineTen = false;
    private boolean isExistingValidDataForLineElevent = false;
    private String firstSeriesName = null;
    private static final int FLAG_IN = 0;
    private static final int FLAG_OUT = 1;
    private static HashMap COLOR_BUFFER = new HashMap();
    private static boolean IMAGE_DIR_INIT_FLAG = false;

    public DrawUtil() {
    }

    /**
     * 生成票类型饼图 -- multiple
     * @param traffics
     * @param type
     * @param title
     * @return 
     */
    JFreeChart getMultiPieForCard(Map<String, Vector> traffics, Vector dataSub) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        //个性化展示 OTC按大类展示，不展示子类；除员工票、车站工作票单独展示，其它员工票统一展示位“其它员工票”
        Vector dataPer = cardPersonalizedDisplay(traffics, dataSub);
        //进子类型
        for(int i=0; i < dataPer.size(); i++){
            ViewVo vo = (ViewVo) dataPer.get(i);
            dataset.setValue(Double.valueOf(vo.getTraffic_in()), vo.getSubCardName()+" ", "票卡进站客流");
        }
        //出子类型
        for(int i=0; i < dataPer.size(); i++){
            ViewVo vo = (ViewVo) dataPer.get(i);
            dataset.setValue(Double.valueOf(vo.getTraffic_out()), vo.getSubCardName()+" ", "票卡出站客流");
        }
        
        //创建主题样式         
        StandardChartTheme standardChartTheme=new StandardChartTheme("CN");         
        //设置标题字体         
        standardChartTheme.setExtraLargeFont(DrawUtil.FONT_CHART_TITLE);
        //设置标题颜色
        standardChartTheme.setTitlePaint(DrawUtil.COLOR_PLOT_LABEL);
        //设置图例的字体         
        standardChartTheme.setRegularFont(DrawUtil.FONT_LEGEND_LABEL); 
        //应用主题样式         
        ChartFactory.setChartTheme(standardChartTheme);
        
        JFreeChart chart = ChartFactory.createMultiplePieChart("", dataset,TableOrder.BY_COLUMN, true, true, false);
        chart.setBackgroundPaint(DrawUtil.COLOR_CHART_BACKGROUD);//绘图区颜色
        MultiplePiePlot multiplepieplot = (MultiplePiePlot)chart.getPlot();
        //绘图区与图形区之间背景颜色
        multiplepieplot.getPieChart().setBackgroundPaint(DrawUtil.COLOR_CHART_BACKGROUD);
        PiePlot plot = (PiePlot)multiplepieplot.getPieChart().getPlot();
        
        //设置xml配置文件票卡类型颜色
        setSectionColorForCard(plot, dataPer);
        //设置饼图正图属性
        setPiePlotCard(plot);
        
        return chart;
    }
    
    
    /**
     * 生成票类型饼图 -- 单图
     * @param traffics
     * @param type
     * @param title
     * @return 
     */
    JFreeChart getPieForCard(Map<String, Vector> traffics, String type, String title) {
        Vector trVector = new Vector();
        trVector = traffics.get(type);
        
        DefaultPieDataset dataset = new DefaultPieDataset();
        for(int i=0; i < trVector.size(); i++){
            ViewVo vo = (ViewVo) trVector.get(i);
            //进主类型
            if(type.equals(AppConstant.CARD_IN_MAIN)){
                dataset.setValue(vo.getMainCardName(), Double.valueOf(vo.getTraffic_in()));
            }
            //出主类型
            if(type.equals(AppConstant.CARD_OUT_MAIN)){
                dataset.setValue(vo.getMainCardName(), Double.valueOf(vo.getTraffic_out()));
            }
            //进子类型
            if(type.equals(AppConstant.CARD_IN_SUB)){
                dataset.setValue(vo.getSubCardName(), Double.valueOf(vo.getTraffic_in()));
            }
            //出子类型
            if(type.equals(AppConstant.CARD_OUT_SUB)){
                dataset.setValue(vo.getSubCardName(), Double.valueOf(vo.getTraffic_out()));
            }
        }
        
        JFreeChart chart = ChartFactory.createPieChart(title, dataset, true, true, false);
        chart.setBackgroundPaint(DrawUtil.COLOR_CHART_BACKGROUD);//绘图区颜色
//        chart.addSubtitle(new TextTitle("setCircular(false);", new Font("Dialog", Font.PLAIN, 12)));//设置子标题
        // JFreeChart主要由三个部分构成：title(标题),legend(图释),plot(图表主体)。
        TextTitle textTitle = chart.getTitle();
        textTitle.setFont(DrawUtil.FONT_CHART_TITLE);//标题字体
        textTitle.setPaint(DrawUtil.COLOR_PLOT_LABEL);//标题颜色
        LegendTitle legend = chart.getLegend();
        if (legend != null) {
            legend.setItemFont(DrawUtil.FONT_LEGEND_LABEL);//图例字体
        } 
        PiePlot plot = (PiePlot) chart.getPlot();
        
        //设置xml配置文件票卡类型颜色
        setSectionColorForCard(plot, trVector);
        //设置饼图正图属性
        setPiePlotCard(plot);
        
        return chart;
    }
    public static Vector cardPersonalizedDisplay(Map<String, Vector> traffics, Vector dataVec){
        if(dataVec.size()>0){
            //1.获取除员工票、车站工作票以外的其它员工票的进出站数据
            processEmployeeOther(dataVec);

            //2.获取OTC总进出站数据
            processMainCardTypeOTC(traffics, dataVec);

            //3.对结果进行排序
            Vector dataSorted = new Vector();
            if(dataVec.size()>0){
                dataSorted = SelectedSortViewvos(dataVec);
                return dataSorted;
            }
        }
        
        return dataVec;
    }
    public static void processEmployeeOther(Vector dataVec){
        
        int employeeOtherIn = 0;
        int employeeOtherOut = 0;
        ViewVo vvo = new ViewVo();
        int c0502 = issubCardTypeExisted(dataVec,"0502");
        if(c0502>-1){
            vvo = (ViewVo) dataVec.get(c0502);
            employeeOtherIn += Integer.parseInt(getValue(vvo.getTraffic_in()));
            employeeOtherOut += Integer.parseInt(getValue(vvo.getTraffic_out()));
            dataVec.remove(c0502);
        }
        int c0503 = issubCardTypeExisted(dataVec,"0503");
        if(c0503>-1){
            vvo = (ViewVo) dataVec.get(c0503);
            employeeOtherIn += Integer.parseInt(getValue(vvo.getTraffic_in()));
            employeeOtherOut += Integer.parseInt(getValue(vvo.getTraffic_out()));
            dataVec.remove(c0503);
        }
        int c0504 = issubCardTypeExisted(dataVec,"0504");
        if(c0504>-1){
            vvo = (ViewVo) dataVec.get(c0504);
            employeeOtherIn += Integer.parseInt(getValue(vvo.getTraffic_in()));
            employeeOtherOut += Integer.parseInt(getValue(vvo.getTraffic_out()));
            dataVec.remove(c0504);
        }
        int c0505 = issubCardTypeExisted(dataVec,"0505");
        if(c0505>-1){
            vvo = (ViewVo) dataVec.get(c0505);
            employeeOtherIn += Integer.parseInt(getValue(vvo.getTraffic_in()));
            employeeOtherOut += Integer.parseInt(getValue(vvo.getTraffic_out()));
            dataVec.remove(c0505);
        }
        if(employeeOtherIn>0 || employeeOtherOut>0){
            vvo.setMainCardID("05");
            vvo.setMainCardName("");
            vvo.setSubCardID("0x");
            vvo.setSubCardName("其它员工票");
            vvo.setTraffic_in(employeeOtherIn+"");
            vvo.setTraffic_out(employeeOtherOut+"");
            dataVec.add(vvo);
        }
        
    }
    public static int issubCardTypeExisted(Vector dataVec, String cardType){
        int index = -1;
        ViewVo vvo = new ViewVo();
        for(int i=0;i<dataVec.size();i++){
            vvo = (ViewVo) dataVec.get(i);
            if(cardType.equals(vvo.getMainCardID()+vvo.getSubCardID())){
                index = i;
                break;
            }
        }
        return index;
    }
    public static void processMainCardTypeOTC(Map<String, Vector> traffics, Vector dataVec){
        Vector dataMainIn = new Vector();
        Vector dataMainOut = new Vector();
        dataMainIn = traffics.get(AppConstant.CARD_IN_MAIN);
        dataMainOut = traffics.get(AppConstant.CARD_OUT_MAIN);
        int mainOTCIn = 0;
        int mainOTCOut = 0;
        ViewVo vvo = new ViewVo();
        ViewVo vvo_OTC = new ViewVo();
        int index1 = isMainCardTypeExisted(dataMainIn, "06");
        int index2 = isMainCardTypeExisted(dataMainOut, "06");
        vvo = (ViewVo)dataMainIn.get(index1);
        mainOTCIn = Integer.parseInt(getValue(vvo.getTraffic_in()));
        vvo = (ViewVo)dataMainOut.get(index2);
        mainOTCOut = Integer.parseInt(getValue(vvo.getTraffic_out()));
        if(mainOTCIn>0||mainOTCOut>0){
            for(int i=0;i<dataVec.size();i++){//删除所有OTC的子类
                vvo = (ViewVo) dataVec.get(i);
                if("06".equals(vvo.getMainCardID())){
                    vvo_OTC = (ViewVo)dataVec.get(i);
                    dataVec.remove(i);
                    i--;
                }
            }
            //添加OTC大类
            vvo_OTC.setMainCardID("06");
            vvo_OTC.setMainCardName("");
            vvo_OTC.setSubCardID("0x");
            vvo_OTC.setSubCardName("OTC总计");
            vvo_OTC.setTraffic_in(mainOTCIn+"");
            vvo_OTC.setTraffic_out(mainOTCOut+"");
            dataVec.add(vvo_OTC);
        }
        
    }
    
    public static int isMainCardTypeExisted(Vector dataVec, String cardType){
        int index = -1;
        ViewVo vvo = new ViewVo();
        for(int i=0;i<dataVec.size();i++){
            vvo = (ViewVo) dataVec.get(i);
            if(cardType.equals(vvo.getMainCardID())){
                index = i;
                break;
            }
        }
        return index;
    }
    /**
     * 空值赋0
     * @param value
     * @return 
     */
    private static String getValue(String value){
        return value.equals("")?"0":value;
    }
    /**
     * 设置票类型饼图正图属性
     * @param plot 
     */
    private void setPiePlotCard(PiePlot plot) {
        //设置饼图是否为正圆。false饼图会不够圆，有点扁。
        plot.setCircular(true);
        //设置绘图区的背景色
        plot.setBackgroundPaint(DrawUtil.COLOR_PLOT_BACKGROUND);
        //文本颜色
        plot.setLabelPaint(DrawUtil.COLOR_PLOT_LABEL);
        //连接线label宽度
        plot.setMaximumLabelWidth(0.23);
        //连接线颜色
        plot.setLabelLinkPaint(DrawUtil.COLOR_PLOT_LABEL);
        //连接线样式 STANDARD 为直线
        plot.setLabelLinkStyle(PieLabelLinkStyle.STANDARD);
        //文本背景颜色
        plot.setLabelBackgroundPaint(DrawUtil.COLOR_PLOT_LABEL_BACKGROUD);
        //文本字体大小
        plot.setLabelFont(DrawUtil.FONT_PLOT_PIE_LABEL);
        //文本边框线颜色
        plot.setLabelOutlinePaint(DrawUtil.COLOR_PLOT_LABEL_OUTLINE);
        //注释阴影颜色
        plot.setLabelShadowPaint(DrawUtil.COLOR_PLOT_LABEL_SHADOW);
        //饼图阴影颜色
        plot.setShadowPaint(DrawUtil.COLOR_PLOT_LABEL_SHADOW);
        //边框颜色
        plot.setOutlinePaint(DrawUtil.COLOR_PLOT_OUTLINE);
        //设置餠图的文本格式
        StandardPieSectionLabelGenerator gen = new StandardPieSectionLabelGenerator("{0}{2}", 
                NumberFormat.getNumberInstance(), new DecimalFormat("0.00%"));
        plot.setLabelGenerator(gen);
    }
    
    /**
     * 匹配票卡类型饼图颜色
     * @param ploter
     * @param drawResultV 
     */
    public void setSectionColorForCard(Plot ploter, Vector drawResultV) {
        PiePlot plot = (PiePlot) ploter;
        String traffic = "";
        String id = "";
        String name = "";
        for (int i = 0; i < drawResultV.size(); i++) {
            ViewVo vo = (ViewVo) drawResultV.get(i);
            traffic = vo.getTraffic();
            id = vo.getMainCardID()+vo.getSubCardID();
            name = vo.getSubCardName();
            if (!traffic.equals("0")) {
                plot.setSectionPaint(name, (Color)COLOR_CARDS.get(id));
                continue;
            }
        }
    }

    public String drawBar(String pic, String picTitle, String hTitle, String vTitle, Vector drawResultV) {
        DefaultCategoryDataset dataset = null;
        try {
            dataset = prepareCategoryData(drawResultV);
        } catch (Exception e) {
            System.out.println("Prepare data for chart error!");
            return null;
        }

        try {
            JFreeChart chart = ChartFactory.createBarChart3D(picTitle, hTitle, vTitle, dataset, PlotOrientation.VERTICAL, true, false, false);
            //设置背景色
            chart.setBackgroundPaint(new GradientPaint(0, 0, Color.white, 0, 1000, Color.blue));

            //System.out.println("Bar Chart Ready!");
            saveChartToPic(pic, chart);
            //System.out.println("Draw bar chart ok!");
        } catch (Exception exc) {
            System.out.println("Draw bar chart error!");
            pic = null;
        }
        return pic;
    }

    public String drawBarScreen(String pic, String picTitle, String hTitle, String vTitle, Vector drawResultV, String prePicName) {
        return this.drawBarScreen(pic, picTitle, hTitle, vTitle, drawResultV, true, prePicName);
    }

    public JFreeChart drawBarScreenForLocal(String picTitle, String hTitle, String vTitle, Vector drawResultV) {
        return this.drawBarScreenForLocal(picTitle, hTitle, vTitle, drawResultV, true);
    }

    public JFreeChart drawLineScreenForLocal(String picTitle, String hTitle, String vTitle, Vector drawResultV) {
        return this.drawLineScreenForLocal(picTitle, hTitle, vTitle, drawResultV, true);
    }

    public String drawLineScreen(String pic, String picTitle, String hTitle, String vTitle, Vector drawResultV, String prePicName) {
        return this.drawLineScreen(pic, picTitle, hTitle, vTitle, drawResultV, true, prePicName);
    }

    public String drawBarScreen(String pic, String picTitle, String hTitle, String vTitle, Vector drawResultV, boolean isNeedLegend, String prePicName) {
        //     List list=new ArrayList();
        DefaultCategoryDataset dataset = null;
        try {
            dataset = prepareCategoryDataForScreen(drawResultV);
        } catch (Exception e) {
            System.out.println("Prepare data for chart error!");
            return null;
        }

        try {
            JFreeChart chart = ChartFactory.createBarChart3D(picTitle, hTitle, vTitle, dataset, PlotOrientation.VERTICAL, isNeedLegend, false, false);

            CategoryPlot plot = (CategoryPlot) chart.getPlot();
            NumberAxis columnAxis = (NumberAxis) plot.getRangeAxis();
            CategoryAxis rowAxis = (CategoryAxis) plot.getDomainAxis();
            LegendTitle legend = null;
            if (isNeedLegend) {
                legend = (LegendTitle) chart.getLegend();
            }
            BarRenderer3D renderer = (BarRenderer3D) plot.getRenderer();

            //设置图片属性
            this.setChartProperties(chart);
            //设置绘图区属性
            this.setPlotProperties(plot);

            this.setRenderProperties(renderer, drawResultV);

            //设置纵坐标的属性
            this.setCollumnAxisProperties(columnAxis);
            //设置横坐标的属性
            this.setRowAxisProperties(rowAxis);
            //图示显示位置
            if (isNeedLegend) {
                this.setLegendProperties(legend, drawResultV);
            }

            saveChart(pic, chart, AppConstant.SCREEN_GRAPH_WIDTH, AppConstant.SCREEN_GRAPH_HEIGHT, prePicName);

            //设置小时、人次的显示位置
            //    plot.setDomainAxisLocation(AxisLocation.TOP_OR_RIGHT);

        } catch (Exception exc) {
            exc.printStackTrace();
            System.out.println("Draw bar chart error!");
            pic = null;
        }
        return pic;
    }
    //小时客流柱状图生成方法
    public JFreeChart drawBarScreenForLocal(String picTitle, String hTitle, String vTitle, Vector drawResultV, boolean isNeedLegend) {
        //     List list=new ArrayList();
        DefaultCategoryDataset dataset = null;
        JFreeChart chart = null;
        try {
            dataset = prepareCategoryDataForScreen(drawResultV);
        } catch (Exception e) {
            System.out.println("Prepare data for chart error!");
            return null;
        }

        try {
            chart = ChartFactory.createBarChart3D(picTitle, hTitle, vTitle, dataset, PlotOrientation.VERTICAL, isNeedLegend, true, false);

            CategoryPlot plot = (CategoryPlot) chart.getPlot();
            NumberAxis columnAxis = (NumberAxis) plot.getRangeAxis();
            CategoryAxis rowAxis = (CategoryAxis) plot.getDomainAxis();
            LegendTitle legend = null;
            if (isNeedLegend) {
                legend = (LegendTitle) chart.getLegend();
            }
            BarRenderer3D renderer = (BarRenderer3D) plot.getRenderer();

            //设置图片属性，背景色、图片边框颜色
            this.setChartProperties(chart);
            //设置绘图区属性，绘图区的背景颜色、绘图区的边框颜色、设置坐标轴等对象间隙
            this.setPlotProperties(plot);

            this.setRenderProperties(renderer, drawResultV);

            //设置纵坐标的属性
            this.setCollumnAxisProperties(columnAxis);
            //设置横坐标的属性
            this.setRowAxisProperties(rowAxis);
            //图示显示位置
            if (isNeedLegend) {
                this.setLegendProperties(legend, drawResultV);
            }

        } catch (Exception exc) {
            exc.printStackTrace();
            System.out.println("Draw bar chart error!");

        }
        return chart;
    }

    public String drawLineScreen(String pic, String picTitle, String hTitle, String vTitle, Vector drawResultV, boolean isNeedLegend, String prePicName) {
        //     List list=new ArrayList();
        DefaultCategoryDataset dataset = null;
        try {
            dataset = prepareCategoryDataForScreenLine(drawResultV);
        } catch (Exception e) {
            System.out.println("Prepare data for chart error!");
            return null;
        }

        try {
            JFreeChart chart = ChartFactory.createLineChart3D(picTitle, hTitle, vTitle, dataset, PlotOrientation.VERTICAL, isNeedLegend, false, false);
            //      JFreeChart chart = ChartFactory.createLineChart(picTitle, hTitle, vTitle,dataset, PlotOrientation.VERTICAL, isNeedLegend, false, false);

            CategoryPlot plot = (CategoryPlot) chart.getPlot();
            NumberAxis columnAxis = (NumberAxis) plot.getRangeAxis();
            //        CategoryAxis rowAxis  (CategoryAxis) plot.getDomainAxis();
            CategoryAxis rowAxis = (CategoryAxis) plot.getDomainAxis();
            LegendTitle legend = null;
            if (isNeedLegend) {
                legend = (LegendTitle) chart.getLegend();
            }
            LineRenderer3D renderer = (LineRenderer3D) plot.getRenderer();
            //      LineAndShapeRenderer renderer = (LineAndShapeRenderer) plot.getRenderer();

            //设置图片属性
            this.setChartProperties(chart);
            //设置绘图区属性
            this.setPlotProperties(plot);

            this.setLineRenderProperties(renderer, drawResultV);

            //设置纵坐标的属性
            this.setCollumnAxisProperties(columnAxis);
            //设置横坐标的属性
            this.setRowAxisProperties(rowAxis);
            //图示显示位置
            if (isNeedLegend) {
                this.setLegendProperties(legend, drawResultV);
            }

            saveChart(pic, chart, AppConstant.SCREEN_GRAPH_WIDTH, AppConstant.SCREEN_GRAPH_HEIGHT, prePicName);

            //设置小时、人次的显示位置
            //    plot.setDomainAxisLocation(AxisLocation.TOP_OR_RIGHT);

        } catch (Exception exc) {
            exc.printStackTrace();
            System.out.println("Draw bar chart error!");
            pic = null;
        }
        return pic;
    }
    //5分钟客流画图方法
    public JFreeChart drawLineScreenForLocal(String picTitle, String hTitle, String vTitle, Vector drawResultV, boolean isNeedLegend) {
        //     List list=new ArrayList();
        DefaultCategoryDataset dataset = null;
        JFreeChart chart = null;
        try {
            dataset = prepareCategoryDataForScreenLine(drawResultV);
        } catch (Exception e) {
            System.out.println("Prepare data for chart error!");
            return null;
        }

        try {
            chart = ChartFactory.createLineChart3D(picTitle, hTitle, vTitle, dataset, PlotOrientation.VERTICAL, isNeedLegend, true, false);
            //      JFreeChart chart = ChartFactory.createLineChart(picTitle, hTitle, vTitle,dataset, PlotOrientation.VERTICAL, isNeedLegend, false, false);

            CategoryPlot plot = (CategoryPlot) chart.getPlot();
            //plot.setDomainGridlinesVisible(true); // 设置是否显示垂直方向背景线,默认值为false 
            // 设置前景色透明度（0~1）
            plot.setForegroundAlpha(0.5f);
            NumberAxis columnAxis = (NumberAxis) plot.getRangeAxis();
            //        CategoryAxis rowAxis  (CategoryAxis) plot.getDomainAxis();
            CategoryAxis rowAxis = (CategoryAxis) plot.getDomainAxis();
            

            LegendTitle legend = null;
            if (isNeedLegend) {
                legend = (LegendTitle) chart.getLegend();
                legend.setLineChartFlag(true);
            }
            
            
            LineRenderer3D renderer = (LineRenderer3D) plot.getRenderer();
            
            
            //设置图片属性
            this.setChartProperties(chart);
            //设置绘图区属性
            this.setPlotProperties(plot);
            
            this.setLineRenderProperties(renderer, drawResultV);

            //设置纵坐标的属性
            this.setCollumnAxisProperties(columnAxis);
            //设置横坐标的属性
            this.setRowAxisPropertiesForBig(rowAxis);
            //图示显示位置
            if (isNeedLegend) {
                this.setLegendProperties(legend, drawResultV);
            }
            
//            renderer.setBaseShapesVisible(true);// 设置拐点是否可见/是否显示拐点
//            renderer.setDrawOutlines(true);// 设置拐点不同用不同的形状
//            renderer.setUseFillPaint(false);// 设置线条是否被显示填充颜色
//            renderer.setBaseFillPaint(Color.BLACK);//// 设置拐点颜色

//            CategoryItemLabelGenerator generator = new StandardCategoryItemLabelGenerator("{2}", new DecimalFormat("0"));
//            renderer.setBaseItemLabelGenerator(generator);
//            renderer.setBaseItemLabelsVisible(true);
            
            //renderer.setBaseShapesVisible(true); // series 点（即数据点）可见 
            //renderer.setSeriesVisible(0, false);
            // saveChart(pic, chart,AppConstant.SCREEN_GRAPH_WIDTH,AppConstant.SCREEN_GRAPH_HEIGHT,prePicName);

            //设置小时、人次的显示位置
            //    plot.setDomainAxisLocation(AxisLocation.TOP_OR_RIGHT);

        } catch (Exception exc) {
            exc.printStackTrace();
            System.out.println("Draw bar chart error!");

        }
        return chart;

    }

    public boolean isInOut(Vector drawResultV) {
        if (drawResultV == null || drawResultV.isEmpty()) {
            return false;
        }
        String lineName = "";
        for (int i = 0; i < drawResultV.size(); i++) {
            DrawResult dr = (DrawResult) drawResultV.get(i);
            lineName = dr.getTitle();
            if (lineName == null) {
                continue;
            }
            if (lineName.equals(this.IN_NAME) || lineName.equals(this.OUT_NAME) || lineName.equalsIgnoreCase(this.IN_NAME_ENGLISH) || lineName.equalsIgnoreCase(this.OUT_NAME_ENGLISH)) {
                return true;
            }

        }
        return false;

    }

    private void setChartProperties(JFreeChart chart) {
        //设置背景色
        chart.setBackgroundPaint(DrawUtil.COLOR_CHART_BACKGROUD);
        //设置图片边框颜色
        chart.setBorderPaint(DrawUtil.COLOR_CHART_BORDE);
    }

    private void setPlotProperties(Plot plot) {
        //设置绘图区的背景颜色
        CategoryPlot cplot = (CategoryPlot) plot;
        cplot.setBackgroundPaint(DrawUtil.COLOR_PLOT_BACKGROUND);
        //设置绘图区的边框颜色
        cplot.setOutlinePaint(DrawUtil.COLOR_PLOT_OUTLINE);
        //设置坐标轴间隙
        cplot.setAxisOffset(RectangleInsets.ZERO_INSETS);
        //cplot.setDomainGridlinesVisible(true); // 设置是否显示垂直方向背景线,默认值为false 
        //cplot.setRangeGridlinesVisible(true);
    }

    private void setRowAxisProperties(Axis axis) {
        //设置横坐标的属性
        CategoryAxis rowAxis = (CategoryAxis) axis;
        rowAxis.setLabelFont(FONT_ROW_AXIS_LABEL);  //坐标轴标题字体
        rowAxis.setLabelPaint(COLOR_ROW_AXIS_LABEL);  //坐标轴标题颜色
        rowAxis.setTickLabelPaint(COLOR_ROW_AXIS_TICK_LABEL);   //坐标轴标尺值颜色
        rowAxis.setTickLabelFont(FONT_ROW_AXIS_TICK_LABEL);           //坐标轴标尺值字体

        //        plot.setDomainAxis(rowAxis);

    }

    private void setRowAxisPropertiesForBig(Axis axis) {
        //设置横坐标的属性
        CategoryAxis rowAxis = (CategoryAxis) axis;
        rowAxis.setLabelFont(FONT_ROW_AXIS_LABEL);  //坐标轴标题字体
        rowAxis.setLabelPaint(COLOR_ROW_AXIS_LABEL);  //坐标轴标题颜色
        rowAxis.setTickLabelPaint(COLOR_ROW_AXIS_TICK_LABEL);   //坐标轴标尺值颜色
        rowAxis.setTickLabelFont(FONT_ROW_AXIS_TICK_LABEL_BIG);           //坐标轴标尺值字体
        //rowAxis.setTickLabelFont(new Font("黑体", Font.CENTER_BASELINE, 9));

        rowAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45);
        // rowAxis.setAxisLinePaint(COLOR_ROW_AXIS);

        //        plot.setDomainAxis(rowAxis);

    }

    private void setCollumnAxisProperties(Axis axis) {
        NumberFormat nf = NumberFormat.getInstance();
        nf.setParseIntegerOnly(true);
        //设置纵坐标的属性
        NumberAxis columnAxis = (NumberAxis) axis;
        columnAxis.setLabelFont(FONT_COLUMN_AXIS_LABEL); //坐标轴标题字体
        columnAxis.setLabelPaint(COLOR_COLUMN_AXIS_LABEL); //坐标轴标题颜色
        columnAxis.setTickLabelPaint(COLOR_COLUMN_AXIS_TICK_LABEL); //坐标轴标尺值颜色
        columnAxis.setTickLabelFont(FONT_COLUMN_AXIS_TICK_LABEL); //坐标轴标尺值字体
        columnAxis.setNumberFormatOverride(nf); //坐标数值表示格式

        columnAxis.setLowerBound(0.001);
        columnAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        //columnAxis.setAxisLinePaint(COLOR_COLUMN_AXIS);

    }

    public int getSerialColor(int flag) {
        Integer fOb = new Integer(flag);
        Integer sOb = null;
        Set keys = DrawUtil.COLOR_BUFFER.keySet();
        Iterator it = keys.iterator();
        Integer key;
        Integer value;
        while (it.hasNext()) {
            key = (Integer) it.next();
            value = (Integer) DrawUtil.COLOR_BUFFER.get(key);
            if (value.intValue() == flag) {
                return key.intValue();
            }
        }
        return -1;

    }

    private Color getColorFromBuffer(Integer line){
        Set lines = DrawUtil.COLOR_LINES.keySet();
        Iterator it = lines.iterator();
        Color color;
        Integer key;
        while(it.hasNext()){
           key =(Integer)it.next();
           if(key.intValue()==line.intValue())
               return (Color)DrawUtil.COLOR_LINES.get(key);

        }
        return new Color(255,255,255);

    }
    private void setLineSerialPaintByBuffer(AbstractRenderer render) {
        AbstractRenderer renderer = (AbstractRenderer) render;
        //    int j=0;
        if(DrawUtil.COLOR_BUFFER.isEmpty())
            return;
        Set serials = DrawUtil.COLOR_BUFFER.keySet();
        Iterator it = serials.iterator();
        int serial;
        Integer line;
        Integer key;
        Color c;
        while(it.hasNext()){
           key =(Integer)it.next();
           serial = key.intValue();
           line = (Integer)DrawUtil.COLOR_BUFFER.get(key);
           c= this.getColorFromBuffer(line);
           logger.info("序列："+serial+" 线路："+line+ " 颜色："+c.getRed()+","+c.getGreen()+","+c.getBlue());
           renderer.setSeriesPaint(serial, c);

        }
        
    }


    @SuppressWarnings("static-access")
    public void setInOutSeriesPaint(AbstractRenderer render) {
        AbstractRenderer renderer = (AbstractRenderer) render;
        if (this.firstSeriesName == null) {
            return;
        }
        if (this.firstSeriesName.equals(this.IN_NAME) || this.firstSeriesName.equals(this.IN_NAME_ENGLISH)) {
            renderer.setSeriesPaint(1, (Color)DrawUtil.COLOR_5MinLINES.get(AppConstant.COLOR_IN)); //进站颜色
            renderer.setSeriesPaint(2, (Color)DrawUtil.COLOR_5MinLINES.get(AppConstant.COLOR_OUT)); //出站颜色
        } else {
            renderer.setSeriesPaint(1, (Color)DrawUtil.COLOR_5MinLINES.get(AppConstant.COLOR_OUT)); //出站颜色
            renderer.setSeriesPaint(2, (Color)DrawUtil.COLOR_5MinLINES.get(AppConstant.COLOR_IN)); //进站颜色
        }

    }

    private void setRenderProperties(AbstractRenderer render, Vector drawResultV) {
        //设置柱状图的颜色
        BarRenderer3D renderer = (BarRenderer3D) render;
        //设置轮廓线颜色
        renderer.setBaseOutlinePaint(Color.BLACK);
        //第一序列不可见
        renderer.setSeriesVisible(0, false);
        if (!this.isInOut(drawResultV)) {
            /*
            renderer.setSeriesPaint(0, this.COLOR_LINE_ONE); //一号线颜色
            renderer.setSeriesPaint(1, this.COLOR_LINE_TWO); //二号线颜色
            renderer.setSeriesPaint(2, this.COLOR_LINE_THREE); //三号线颜色
            renderer.setSeriesPaint(3, this.COLOR_LINE_FOUR); //四号线颜色
             */
            //设置柱状图颜色
            //this.setLineSerialPaint(render);
            this.setLineSerialPaintByBuffer(render);
        } else {
            //     renderer.setSeriesPaint(0, this.COLOR_IN); //进站颜色
            //     renderer.setSeriesPaint(1, this.COLOR_OUT); //出站颜色
            //    this.stInOutSeriesPaint(render);
            this.setInOutSeriesPaint(render);
        }
        //柱图边框
        //    renderer.setSeriesOutlinePaint(0, Color.blue);
        //     renderer.setSeriesOutlinePaint(1, Color.blue);
        //    renderer.setSeriesOutlinePaint(2, Color.blue);
        //   renderer.setSeriesOutlinePaint(3, Color.blue);

        //设置每个平行柱的之间距离
        renderer.setItemMargin(0.1);
        //显示每个柱的数值，并修改该数值的字体属性
      /*
        renderer.setSeriesLabelGenerator(0, new StandardCategoryLabelGenerator());
        renderer.setSeriesLabelGenerator(1, new StandardCategoryLabelGenerator());
        renderer.setSeriesLabelGenerator(2, new StandardCategoryLabelGenerator());
        renderer.setSeriesLabelGenerator(3, new StandardCategoryLabelGenerator());
        renderer.setSeriesLabelGenerator(4, new StandardCategoryLabelGenerator());
        renderer.setSeriesLabelGenerator(5, new StandardCategoryLabelGenerator());
        renderer.setSeriesLabelGenerator(6, new StandardCategoryLabelGenerator());
        renderer.setSeriesLabelGenerator(7, new StandardCategoryLabelGenerator());
        renderer.setSeriesLabelGenerator(8, new StandardCategoryLabelGenerator());
        renderer.setSeriesLabelGenerator(9, new StandardCategoryLabelGenerator());
        renderer.setSeriesLabelGenerator(10, new StandardCategoryLabelGenerator());
         */

        //每个柱的数值的颜色
        //  renderer.setseriesiteml

        renderer.setSeriesItemLabelPaint(0, DrawUtil.COLOR_RENDERER_LABEL);
        renderer.setSeriesItemLabelPaint(1, DrawUtil.COLOR_RENDERER_LABEL);
        renderer.setSeriesItemLabelPaint(2, DrawUtil.COLOR_RENDERER_LABEL);
        renderer.setSeriesItemLabelPaint(3, DrawUtil.COLOR_RENDERER_LABEL);
        renderer.setSeriesItemLabelPaint(4, DrawUtil.COLOR_RENDERER_LABEL);
        renderer.setSeriesItemLabelPaint(5, DrawUtil.COLOR_RENDERER_LABEL);
        renderer.setSeriesItemLabelPaint(6, DrawUtil.COLOR_RENDERER_LABEL);
        renderer.setSeriesItemLabelPaint(7, DrawUtil.COLOR_RENDERER_LABEL);
        renderer.setSeriesItemLabelPaint(8, DrawUtil.COLOR_RENDERER_LABEL);
        renderer.setSeriesItemLabelPaint(9, DrawUtil.COLOR_RENDERER_LABEL);
        renderer.setSeriesItemLabelPaint(10, DrawUtil.COLOR_RENDERER_LABEL);


        //显示每个柱的数值
        //            renderer.setSeriesItemLabelsVisible(0,true);
        //             renderer.setSeriesItemLabelsVisible(1,true);
        //             renderer.setSeriesItemLabelsVisible(2,true);
        //             renderer.setSeriesItemLabelsVisible(3,true);
        //数值显示的方式与位置
        renderer.setItemLabelAnchorOffset(10.0);
        renderer.setPositiveItemLabelPosition(new ItemLabelPosition(ItemLabelAnchor.OUTSIDE12, TextAnchor.BASELINE_LEFT));
        renderer.setItemLabelsVisible(false);
        //数值显示的字体及大小
        renderer.setItemLabelFont(DrawUtil.FONT_RENDERER_BAR_LABEL);  //柱体的字体
        renderer.setWallPaint(DrawUtil.COLOR_RENDERER_AXISWALL);//三维坐标墙颜色
    }

    private void setLineRenderProperties(AbstractRenderer render, Vector drawResultV) {
        //设置柱状图的颜色
        LineRenderer3D renderer = (LineRenderer3D) render;

        renderer.setSeriesVisible(0, false);

        if (!this.isInOut(drawResultV)) {
            //this.setLineSerialPaint(render);
            this.setLineSerialPaintByBuffer(render);
        } else {
            this.setInOutSeriesPaint(render);
        }
        //柱图边框
        //    renderer.setSeriesOutlinePaint(0, Color.blue);
        //     renderer.setSeriesOutlinePaint(1, Color.blue);
        //    renderer.setSeriesOutlinePaint(2, Color.blue);
        //   renderer.setSeriesOutlinePaint(3, Color.blue);

        //设置每个平行柱的之间距离
        //  renderer.setItemMargin(0.1);
        //显示每个柱的数值，并修改该数值的字体属性
        /*
        renderer.setSeriesLabelGenerator(0, new StandardCategoryLabelGenerator());
        renderer.setSeriesLabelGenerator(1, new StandardCategoryLabelGenerator());
        renderer.setSeriesLabelGenerator(2, new StandardCategoryLabelGenerator());
        renderer.setSeriesLabelGenerator(3, new StandardCategoryLabelGenerator());
         */
        //每个柱的数值的颜色
        //  renderer.setseriesiteml

        //    renderer.setSeriesItemLabelPaint(0, this.COLOR_RENDERER_LABEL);
        //    renderer.setSeriesItemLabelPaint(1, this.COLOR_RENDERER_LABEL);
        //    renderer.setSeriesItemLabelPaint(2, this.COLOR_RENDERER_LABEL);
        //    renderer.setSeriesItemLabelPaint(3, this.COLOR_RENDERER_LABEL);

        //显示每个柱的数值
        //              renderer.setSeriesItemLabelsVisible(0,true);
        //              renderer.setSeriesItemLabelsVisible(1,true);
        //               renderer.setSeriesItemLabelsVisible(2,true);
        //              renderer.setSeriesItemLabelsVisible(3,true);
        //数值显示的方式与位置
//               renderer.setItemLabelAnchorOffset(5.0);
//              renderer.setPositiveItemLabelPosition(new ItemLabelPosition(ItemLabelAnchor.OUTSIDE12, TextAnchor.BASELINE_LEFT));
//               renderer.setItemLabelsVisible(true);
        //数值显示的字体及大小
        //       renderer.setItemLabelFont(this.FONT_RENDERER_LINE_LABEL);  //柱体的字体
        renderer.setWallPaint(DrawUtil.COLOR_RENDERER_AXISWALL);//三维坐标墙颜色

    }

    private void setLegendProperties(LegendTitle leg, Vector drawResultV) {
        LegendTitle legend = (LegendTitle) leg;
        //设置图例位置
        legend.setHorizontalAlignment(HorizontalAlignment.CENTER);
        //legend.setVerticalAlignment(VerticalAlignment.TOP);
        legend.setPosition(RectangleEdge.TOP);
        legend.setMargin(0, 0, 0, 60);

        //legend.setLegendItemGraphicPadding(new RectangleInsets().extendHeight(3.0));

        //设置图例边框
        legend.setFrame(new LineBorder(DrawUtil.COLOR_LEGEND_OUTLINE, new BasicStroke(0.5f), new RectangleInsets(1.0, 1.0, 1.0, 1.0)));


        //－－legend.setAnchor(177);
        // legend.setAnchor(150);
        //设置图例内文字字体
        legend.setItemFont(DrawUtil.FONT_LEGEND_LABEL);
        // legend.setInnerGap(Spacer.NO_SPACE);
        //设置图例内文字颜色
        legend.setItemPaint(DrawUtil.COLOR_LEGEND_LABEL);
        //设置图例内背景色
        legend.setBackgroundPaint(DrawUtil.COLOR_LEGEND_BACKGROUND);
        //-- legend.setOutlinePaint(DrawUtil.COLOR_LEGEND_OUTLINE);
        if (this.isInOut(drawResultV) && this.firstSeriesName != null) {
            if (this.firstSeriesName.equals(this.OUT_NAME) || this.firstSeriesName.equals(this.OUT_NAME_ENGLISH)) {
                System.out.print("aa");
            }
//--          legend.setRenderingOrder(LegendRenderingOrder.REVERSE);
        }

    }

    public String drawLine(String pic, String picTitle, String hTitle, String vTitle, Vector drawResultV) {
        DefaultCategoryDataset dataset = null;
        try {
            dataset = prepareCategoryData(drawResultV);
        } catch (Exception e) {
            System.out.println("Prepare data for chart error!");
            return null;
        }

        try {
            JFreeChart chart = ChartFactory.createLineChart(picTitle, hTitle, vTitle, dataset, PlotOrientation.VERTICAL, true, false, false);
            //设置背景色
            chart.setBackgroundPaint(new GradientPaint(0, 0, Color.white, 0, 1000, Color.blue));

            //System.out.println("Line Chart Ready!");
            saveChartToPic(pic, chart);
            //System.out.println("Draw line chart ok!");
        } catch (Exception exc) {
            System.out.println("Draw line chart error!");
            pic = null;
        }
        return pic;
    }

    private void saveChartToPic(String pic, JFreeChart chart) throws Exception {
        //ChartUtilities.saveChartAsJPEG(new java.io.File(pic), chart, picW, picH);
        ChartUtilities.saveChartAsPNG(new java.io.File(pic), chart, picW, picH);
    }

    private void saveChartToPicScreen(String pic, JFreeChart chart) throws Exception {
        //     ChartUtilities.saveChartAsPNG(new java.io.File(pic),chart, picWScreen, picHScreen);
        ChartUtilities.saveChartAsPNG(new java.io.File(pic), chart, AppConstant.SCREEN_GRAPH_WIDTH, AppConstant.SCREEN_GRAPH_HEIGHT);
    }

    private void savePieChartForAllLines(String pic, JFreeChart chart) throws Exception {
        //     ChartUtilities.saveChartAsPNG(new java.io.File(pic),chart, picWScreen, picHScreen);
        java.io.File f = new java.io.File(pic);
        if (f.exists()) {
            f.delete();
        }
        ChartUtilities.saveChartAsPNG(new java.io.File(pic), chart, AppConstant.SCREEN_PIE_GRAPH_WIDTH, AppConstant.SCREEN_PIE_GRAPH_HEIGHT);
    }

    private void saveChart(String pic, JFreeChart chart, int width, int height, String prePicName) throws Exception {
        //     ChartUtilities.saveChartAsPNG(new java.io.File(pic),chart, picWScreen, picHScreen);

        //        java.io.File f = new java.io.File(pic);
        //        if(f.exists())
        //          f.delete();
        this.initDir();
        this.deletePrePic(prePicName);
        ChartUtilities.saveChartAsPNG(new java.io.File(pic), chart, width, height);

    }

    public void deletePrePic(String prePicName) {
        if (prePicName == null || prePicName.trim().length() == 0) {
            return;
        }
        prePicName = AppConstant.appWorkDir + AppConstant.DICISION_IMG_PATH + prePicName;
        java.io.File f = new java.io.File(prePicName);
        if (f.exists()) {
            f.delete();
        }

    }

    public void initDir() {
        if (DrawUtil.IMAGE_DIR_INIT_FLAG) {
            return;
        }

        String imgDir = AppConstant.appWorkDir + AppConstant.DICISION_IMG_PATH;

        if (imgDir == null) {
            return;
        }
        java.io.File imgDirOb = new java.io.File(imgDir);
        String[] fileNames = null;
        if (imgDirOb.exists() && imgDirOb.isDirectory()) {
            fileNames = imgDirOb.list();
            for (int i = 0; i < fileNames.length; i++) {
                //             System.out.println("imgDiri="+imgDir+" fileName="+fileNames[i]);
                java.io.File oldFile = new java.io.File(imgDir + fileNames[i]);
                //     ldFile.delete();
                oldFile.delete();
            }
        } else {
            try {
                boolean result = imgDirOb.mkdirs();

                //             System.out.println("create dir="+result);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        DrawUtil.IMAGE_DIR_INIT_FLAG = true;

    }
    
    public boolean isAllZeroValue(Vector drawResultV) {
        if (drawResultV == null || drawResultV.isEmpty()) {
            return true;
        }
        String trafic = "";
        for (int i = 0; i < drawResultV.size(); i++) {
            ViewVo vo = (ViewVo) drawResultV.get(i);
            trafic = vo.getTraffic();
            if (trafic == null) {
                continue;
            }
            if (trafic.trim().length() == 0) {
                continue;
            }
            trafic = trafic.trim();
            if (!trafic.equals("0")) {
                return false;
            }

        }
        return true;

    }

    public boolean isAllZeroValueForSingleLine(Vector drawResultV) {
        if (drawResultV == null || drawResultV.isEmpty()) {
            return true;
        }
        String inTrafic = "";
        String outTrafic = "";
        for (int i = 0; i < drawResultV.size(); i++) {
            ViewVo vo = (ViewVo) drawResultV.get(i);
            inTrafic = vo.getTraffic_in();
            outTrafic = vo.getTraffic_out();
            if (inTrafic == null || outTrafic == null) {
                continue;
            }
            if (inTrafic.trim().length() == 0 && outTrafic.trim().length() == 0) {
                continue;
            }
            inTrafic = inTrafic.trim();
            outTrafic = outTrafic.trim();
            if (!inTrafic.equals("0") || !outTrafic.equals("0")) {
                return false;
            }

        }
        return true;

    }

    public boolean isAllZeroValueForSingleLineForInOrOut(Vector drawResultV, int flag) {
        if (drawResultV == null || drawResultV.isEmpty()) {
            return true;
        }
        String inTrafic = "";
        String outTrafic = "";
        for (int i = 0; i < drawResultV.size(); i++) {
            ViewVo vo = (ViewVo) drawResultV.get(i);
            inTrafic = vo.getTraffic_in();
            outTrafic = vo.getTraffic_out();
            if (inTrafic == null || outTrafic == null) {
                continue;
            }
            if (inTrafic.trim().length() == 0 && outTrafic.trim().length() == 0) {
                continue;
            }
            inTrafic = inTrafic.trim();
            outTrafic = outTrafic.trim();
            if (flag == DrawUtil.FLAG_IN) {
                if (!inTrafic.equals("0")) {
                    return false;
                }
            }
            if (flag == DrawUtil.FLAG_OUT) {
                if (!outTrafic.equals("0")) {
                    return false;
                }
            }
        }
        return true;

    }

    private DefaultPieDataset preparePieData(Vector drawResultV) throws Exception {
        DefaultPieDataset dataset = new DefaultPieDataset();
        DrawResult rowTitle = (DrawResult) drawResultV.get(0);
        DrawResult dr = (DrawResult) drawResultV.get(1);
        if ((rowTitle.getResult01()).length() > 0) {
            dataset.setValue(rowTitle.getResult01(), Double.parseDouble(dr.getResult01()));
        }
        if ((rowTitle.getResult02()).length() > 0) {
            dataset.setValue(rowTitle.getResult02(), Double.parseDouble(dr.getResult02()));
        }
        if ((rowTitle.getResult03()).length() > 0) {
            dataset.setValue(rowTitle.getResult03(), Double.parseDouble(dr.getResult03()));
        }
        if ((rowTitle.getResult04()).length() > 0) {
            dataset.setValue(rowTitle.getResult04(), Double.parseDouble(dr.getResult04()));
        }
        if ((rowTitle.getResult05()).length() > 0) {
            dataset.setValue(rowTitle.getResult05(), Double.parseDouble(dr.getResult05()));
        }
        if ((rowTitle.getResult06()).length() > 0) {
            dataset.setValue(rowTitle.getResult06(), Double.parseDouble(dr.getResult06()));
        }
        if ((rowTitle.getResult07()).length() > 0) {
            dataset.setValue(rowTitle.getResult07(), Double.parseDouble(dr.getResult07()));
        }
        if ((rowTitle.getResult08()).length() > 0) {
            dataset.setValue(rowTitle.getResult08(), Double.parseDouble(dr.getResult08()));
        }
        if ((rowTitle.getResult09()).length() > 0) {
            dataset.setValue(rowTitle.getResult09(), Double.parseDouble(dr.getResult09()));
        }
        if ((rowTitle.getResult10()).length() > 0) {
            dataset.setValue(rowTitle.getResult10(), Double.parseDouble(dr.getResult10()));
        }
        if ((rowTitle.getResult11()).length() > 0) {
            dataset.setValue(rowTitle.getResult11(), Double.parseDouble(dr.getResult11()));
        }
        if ((rowTitle.getResult12()).length() > 0) {
            dataset.setValue(rowTitle.getResult12(), Double.parseDouble(dr.getResult12()));
        }
        if ((rowTitle.getResult13()).length() > 0) {
            dataset.setValue(rowTitle.getResult13(), Double.parseDouble(dr.getResult13()));
        }
        if ((rowTitle.getResult14()).length() > 0) {
            dataset.setValue(rowTitle.getResult14(), Double.parseDouble(dr.getResult14()));
        }
        if ((rowTitle.getResult15()).length() > 0) {
            dataset.setValue(rowTitle.getResult15(), Double.parseDouble(dr.getResult15()));
        }
        if ((rowTitle.getResult16()).length() > 0) {
            //	dataset.setValue(rowTitle.getResult16(),Double.parseDouble(dr.getResult16());
            dataset.setValue(rowTitle.getResult16(), Double.parseDouble(dr.getResult16()));
        }
        //if((rowTitle.getResult17()).length()>0){
        if ((rowTitle.getResult17()).length() > 0) {
            dataset.setValue(rowTitle.getResult17(), Double.parseDouble(dr.getResult17()));
        }
        if ((rowTitle.getResult18()).length() > 0) {
            dataset.setValue(rowTitle.getResult18(), Double.parseDouble(dr.getResult18()));
        }
        if ((rowTitle.getResult19()).length() > 0) {
            dataset.setValue(rowTitle.getResult19(), Double.parseDouble(dr.getResult19()));
        }
        if ((rowTitle.getResult20()).length() > 0) {
            dataset.setValue(rowTitle.getResult20(), Double.parseDouble(dr.getResult20()));
        }
        if ((rowTitle.getResult21()).length() > 0) {
            dataset.setValue(rowTitle.getResult21(), Double.parseDouble(dr.getResult21()));
        }
        if ((rowTitle.getResult22()).length() > 0) {
            dataset.setValue(rowTitle.getResult22(), Double.parseDouble(dr.getResult22()));
        }
        if ((rowTitle.getResult23()).length() > 0) {
            dataset.setValue(rowTitle.getResult23(), Double.parseDouble(dr.getResult23()));
        }
        if ((rowTitle.getResult24()).length() > 0) {
            dataset.setValue(rowTitle.getResult24(), Double.parseDouble(dr.getResult24()));
        }
        if ((rowTitle.getResult25()).length() > 0) {
            dataset.setValue(rowTitle.getResult25(), Double.parseDouble(dr.getResult25()));
        }
        if ((rowTitle.getResult26()).length() > 0) {
            dataset.setValue(rowTitle.getResult26(), Double.parseDouble(dr.getResult26()));
        }
        if ((rowTitle.getResult27()).length() > 0) {
            dataset.setValue(rowTitle.getResult27(), Double.parseDouble(dr.getResult27()));
        }
        if ((rowTitle.getResult28()).length() > 0) {
            dataset.setValue(rowTitle.getResult28(), Double.parseDouble(dr.getResult28()));
        }
        if ((rowTitle.getResult29()).length() > 0) {
            dataset.setValue(rowTitle.getResult29(), Double.parseDouble(dr.getResult29()));
        }
        if ((rowTitle.getResult30()).length() > 0) {
            dataset.setValue(rowTitle.getResult30(), Double.parseDouble(dr.getResult30()));
        }
        if ((rowTitle.getResult31()).length() > 0) {
            dataset.setValue(rowTitle.getResult31(), Double.parseDouble(dr.getResult31()));
        }
        return dataset;
    }

    public boolean isNeedDraw(String colKey) {
        //System.out.println("colkey="+colKey);
        if (colKey == null || colKey.length() == 0) {
            return false;
        }
        return true;

    }

    private DefaultCategoryDataset prepareCategoryData(Vector drawResultV) throws Exception {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        DrawResult rowTitle = (DrawResult) drawResultV.get(0);
        int line = drawResultV.size();
        for (int i = 1; i < line; i++) {
            DrawResult dr = (DrawResult) drawResultV.get(i);
            String lineTitle = dr.getTitle();
            if ((rowTitle.getResult01()).length() > 0) {
                dataset.setValue(Double.parseDouble(dr.getResult01()), lineTitle, rowTitle.getResult01());
            }
            if ((rowTitle.getResult02()).length() > 0) {
                dataset.setValue(Double.parseDouble(dr.getResult02()), lineTitle, rowTitle.getResult02());
            }
            if ((rowTitle.getResult03()).length() > 0) {
                dataset.setValue(Double.parseDouble(dr.getResult03()), lineTitle, rowTitle.getResult03());
            }
            if ((rowTitle.getResult04()).length() > 0) {
                dataset.setValue(Double.parseDouble(dr.getResult04()), lineTitle, rowTitle.getResult04());
            }
            if ((rowTitle.getResult05()).length() > 0) {
                dataset.setValue(Double.parseDouble(dr.getResult05()), lineTitle, rowTitle.getResult05());
            }
            if ((rowTitle.getResult06()).length() > 0) {
                dataset.setValue(Double.parseDouble(dr.getResult06()), lineTitle, rowTitle.getResult06());
            }
            if ((rowTitle.getResult07()).length() > 0) {
                dataset.setValue(Double.parseDouble(dr.getResult07()), lineTitle, rowTitle.getResult07());
            }
            if ((rowTitle.getResult08()).length() > 0) {
                dataset.setValue(Double.parseDouble(dr.getResult08()), lineTitle, rowTitle.getResult08());
            }
            if ((rowTitle.getResult09()).length() > 0) {
                dataset.setValue(Double.parseDouble(dr.getResult09()), lineTitle, rowTitle.getResult09());
            }
            if ((rowTitle.getResult10()).length() > 0) {
                dataset.setValue(Double.parseDouble(dr.getResult10()), lineTitle, rowTitle.getResult10());
            }
            if ((rowTitle.getResult11()).length() > 0) {
                dataset.setValue(Double.parseDouble(dr.getResult11()), lineTitle, rowTitle.getResult11());
            }
            if ((rowTitle.getResult12()).length() > 0) {
                dataset.setValue(Double.parseDouble(dr.getResult12()), lineTitle, rowTitle.getResult12());
            }
            if ((rowTitle.getResult13()).length() > 0) {
                dataset.setValue(Double.parseDouble(dr.getResult13()), lineTitle, rowTitle.getResult13());
            }
            if ((rowTitle.getResult14()).length() > 0) {
                dataset.setValue(Double.parseDouble(dr.getResult14()), lineTitle, rowTitle.getResult14());
            }
            if ((rowTitle.getResult15()).length() > 0) {
                dataset.setValue(Double.parseDouble(dr.getResult15()), lineTitle, rowTitle.getResult15());
            }
            if ((rowTitle.getResult16()).length() > 0) {
                dataset.setValue(Double.parseDouble(dr.getResult16()), lineTitle, rowTitle.getResult16());
            }
            if ((rowTitle.getResult17()).length() > 0) {
                dataset.setValue(Double.parseDouble(dr.getResult17()), lineTitle, rowTitle.getResult17());
            }
            if ((rowTitle.getResult18()).length() > 0) {
                dataset.setValue(Double.parseDouble(dr.getResult18()), lineTitle, rowTitle.getResult18());
            }
            if ((rowTitle.getResult19()).length() > 0) {
                dataset.setValue(Double.parseDouble(dr.getResult19()), lineTitle, rowTitle.getResult19());
            }
            if ((rowTitle.getResult20()).length() > 0) {
                dataset.setValue(Double.parseDouble(dr.getResult20()), lineTitle, rowTitle.getResult20());
            }
            if ((rowTitle.getResult21()).length() > 0) {
                dataset.setValue(Double.parseDouble(dr.getResult21()), lineTitle, rowTitle.getResult21());
            }
            if ((rowTitle.getResult22()).length() > 0) {
                dataset.setValue(Double.parseDouble(dr.getResult22()), lineTitle, rowTitle.getResult22());
            }
            if ((rowTitle.getResult23()).length() > 0) {
                dataset.setValue(Double.parseDouble(dr.getResult23()), lineTitle, rowTitle.getResult23());
            }
            if ((rowTitle.getResult24()).length() > 0) {
                dataset.setValue(Double.parseDouble(dr.getResult24()), lineTitle, rowTitle.getResult24());
            }
            if ((rowTitle.getResult25()).length() > 0) {
                dataset.setValue(Double.parseDouble(dr.getResult25()), lineTitle, rowTitle.getResult25());
            }
            if ((rowTitle.getResult26()).length() > 0) {
                dataset.setValue(Double.parseDouble(dr.getResult26()), lineTitle, rowTitle.getResult26());
            }
            if ((rowTitle.getResult27()).length() > 0) {
                dataset.setValue(Double.parseDouble(dr.getResult27()), lineTitle, rowTitle.getResult27());
            }
            if ((rowTitle.getResult28()).length() > 0) {
                dataset.setValue(Double.parseDouble(dr.getResult28()), lineTitle, rowTitle.getResult28());
            }
            if ((rowTitle.getResult29()).length() > 0) {
                dataset.setValue(Double.parseDouble(dr.getResult29()), lineTitle, rowTitle.getResult29());
            }
            if ((rowTitle.getResult30()).length() > 0) {
                dataset.setValue(Double.parseDouble(dr.getResult30()), lineTitle, rowTitle.getResult30());
            }
            if ((rowTitle.getResult31()).length() > 0) {
                dataset.setValue(Double.parseDouble(dr.getResult31()), lineTitle, rowTitle.getResult31());
            }
        }
        return dataset;
    }

    public boolean isValidData(String data) {
        //System.out.println("data="+data);
        if (data == null || data.trim().length() == 0) {
            return false;
        }
        // if(data.trim().equals("0.0"))
        //  return false;
        return true;

    }

    public boolean isValidDataExceptZero(String data) {
        //System.out.println("data="+data);
        if (data == null || data.trim().length() == 0) {
            return false;
        }
        if (data.trim().equals("0.0")) {
            return false;
        }
        return true;

    }

    private boolean setDataForDatasetRowForLine(DefaultCategoryDataset dataset, Vector drawResultV, DrawResult rowTitle, int i, int j) {
        DrawResult dr = (DrawResult) drawResultV.get(j);
        String lineTitle = dr.getTitle();
        this.HandleOnlyOneData(dr);
        if (i == 1) {
            if (this.isNeedDraw(rowTitle.getResult01()) && isValidData(dr.getResult01())) {
                dataset.setValue(Double.parseDouble(dr.getResult01()), lineTitle,
                        rowTitle.getResult01());
                return true;
            }
        }
        if (i == 2) {
            if (this.isNeedDraw(rowTitle.getResult02()) && isValidData(dr.getResult02())) {
                //         dataset.setValue(Double.parseDouble(dr.getResult02()), lineTitle,
                //                          rowTitle.getResult02());
                dataset.setValue(Double.parseDouble(dr.getResult02()), lineTitle,
                        rowTitle.getResult02());
                return true;
            }
        }
        if (i == 3) {
            if (this.isNeedDraw(rowTitle.getResult03()) && isValidData(dr.getResult03())) {
                dataset.setValue(Double.parseDouble(dr.getResult03()), lineTitle,
                        rowTitle.getResult03());
                return true;
            }
        }
        if (i == 4) {
            if (this.isNeedDraw(rowTitle.getResult04()) && isValidData(dr.getResult04())) {
                dataset.setValue(Double.parseDouble(dr.getResult04()), lineTitle,
                        rowTitle.getResult04());
                return true;
            }
        }
        if (i == 5) {
            if (this.isNeedDraw(rowTitle.getResult05()) && isValidData(dr.getResult05())) {
                dataset.setValue(Double.parseDouble(dr.getResult05()), lineTitle,
                        rowTitle.getResult05());
                return true;
            }
        }
        if (i == 6) {
            if (this.isNeedDraw(rowTitle.getResult06()) && isValidData(dr.getResult06())) {
                dataset.setValue(Double.parseDouble(dr.getResult06()), lineTitle,
                        rowTitle.getResult06());
                return true;
            }
        }
        if (i == 7) {
            if (this.isNeedDraw(rowTitle.getResult07()) && isValidData(dr.getResult07())) {
                dataset.setValue(Double.parseDouble(dr.getResult07()), lineTitle,
                        rowTitle.getResult07());
                return true;
            }
        }
        if (i == 8) {
            if (this.isNeedDraw(rowTitle.getResult08()) && isValidData(dr.getResult08())) {
                dataset.setValue(Double.parseDouble(dr.getResult08()), lineTitle,
                        rowTitle.getResult08());
                return true;
            }
        }
        if (i == 9) {
            if (this.isNeedDraw(rowTitle.getResult09()) && isValidData(dr.getResult09())) {
                dataset.setValue(Double.parseDouble(dr.getResult09()), lineTitle,
                        rowTitle.getResult09());
                return true;
            }
        }
        if (i == 10) {
            if (this.isNeedDraw(rowTitle.getResult10()) && isValidData(dr.getResult10())) {
                dataset.setValue(Double.parseDouble(dr.getResult10()), lineTitle,
                        rowTitle.getResult10());
                return true;
            }
        }
        if (i == 11) {
            if (this.isNeedDraw(rowTitle.getResult11()) && isValidData(dr.getResult11())) {
                dataset.setValue(Double.parseDouble(dr.getResult11()), lineTitle,
                        rowTitle.getResult11());
                return true;
            }
        }
        if (i == 12) {
            if (this.isNeedDraw(rowTitle.getResult12()) && isValidData(dr.getResult12())) {
                dataset.setValue(Double.parseDouble(dr.getResult12()), lineTitle,
                        rowTitle.getResult12());
                return true;
            }
        }

        if (i == 13) {
            if (this.isNeedDraw(rowTitle.getResult13()) && isValidData(dr.getResult13())) {
                dataset.setValue(Double.parseDouble(dr.getResult13()), lineTitle,
                        rowTitle.getResult13());
                return true;
            }
        }
        if (i == 14) {
            if (this.isNeedDraw(rowTitle.getResult14()) && isValidData(dr.getResult14())) {
                dataset.setValue(Double.parseDouble(dr.getResult14()), lineTitle,
                        rowTitle.getResult14());
                return true;
            }
        }
        if (i == 15) {
            if (this.isNeedDraw(rowTitle.getResult15()) && isValidData(dr.getResult15())) {
                dataset.setValue(Double.parseDouble(dr.getResult15()), lineTitle,
                        rowTitle.getResult15());
                return true;
            }
        }
        if (i == 16) {
            if (this.isNeedDraw(rowTitle.getResult16()) && isValidData(dr.getResult16())) {
                dataset.setValue(Double.parseDouble(dr.getResult16()), lineTitle,
                        rowTitle.getResult16());
                return true;
            }
        }
        if (i == 17) {
            if (this.isNeedDraw(rowTitle.getResult17()) && isValidData(dr.getResult17())) {
                dataset.setValue(Double.parseDouble(dr.getResult17()), lineTitle,
                        rowTitle.getResult17());
                return true;
            }
        }
        if (i == 18) {
            if (this.isNeedDraw(rowTitle.getResult18()) && isValidData(dr.getResult18())) {
                dataset.setValue(Double.parseDouble(dr.getResult18()), lineTitle,
                        rowTitle.getResult18());
                return true;
            }
        }
        if (i == 19) {
            if (this.isNeedDraw(rowTitle.getResult19()) && isValidData(dr.getResult19())) {
                dataset.setValue(Double.parseDouble(dr.getResult19()), lineTitle,
                        rowTitle.getResult19());
                return true;
            }
        }
        if (i == 20) {
            if (this.isNeedDraw(rowTitle.getResult20()) && isValidData(dr.getResult20())) {
                dataset.setValue(Double.parseDouble(dr.getResult20()), lineTitle,
                        rowTitle.getResult20());
                return true;
            }
        }
        if (i == 21) {
            if (this.isNeedDraw(rowTitle.getResult21()) && isValidData(dr.getResult21())) {
                dataset.setValue(Double.parseDouble(dr.getResult21()), lineTitle,
                        rowTitle.getResult21());
                return true;
            }
        }
        if (i == 22) {
            if (this.isNeedDraw(rowTitle.getResult22()) && isValidData(dr.getResult22())) {
                dataset.setValue(Double.parseDouble(dr.getResult22()), lineTitle,
                        rowTitle.getResult22());
                return true;
            }
        }
        if (i == 23) {
            if (this.isNeedDraw(rowTitle.getResult23()) && isValidData(dr.getResult23())) {
                dataset.setValue(Double.parseDouble(dr.getResult23()), lineTitle,
                        rowTitle.getResult23());
                return true;
            }
        }
        if (i == 24) {
            if (this.isNeedDraw(rowTitle.getResult24()) && isValidData(dr.getResult24())) {
                dataset.setValue(Double.parseDouble(dr.getResult24()), lineTitle,
                        rowTitle.getResult24());
                return true;
            }
        }
        if (i == 25) {
            if (this.isNeedDraw(rowTitle.getResult25()) && isValidData(dr.getResult25())) {
                dataset.setValue(Double.parseDouble(dr.getResult25()), lineTitle,
                        rowTitle.getResult25());
                return true;
            }
        }
        if (i == 26) {
            if (this.isNeedDraw(rowTitle.getResult26()) && isValidData(dr.getResult26())) {
                dataset.setValue(Double.parseDouble(dr.getResult26()), lineTitle,
                        rowTitle.getResult26());
                return true;
            }
        }
        if (i == 27) {
            if (this.isNeedDraw(rowTitle.getResult27()) && isValidData(dr.getResult27())) {
                dataset.setValue(Double.parseDouble(dr.getResult27()), lineTitle,
                        rowTitle.getResult27());
                return true;
            }
        }
        if (i == 28) {
            if (this.isNeedDraw(rowTitle.getResult28()) && isValidData(dr.getResult28())) {
                dataset.setValue(Double.parseDouble(dr.getResult28()), lineTitle,
                        rowTitle.getResult28());
                return true;
            }
        }
        if (i == 29) {
            if (this.isNeedDraw(rowTitle.getResult29()) && isValidData(dr.getResult29())) {
                dataset.setValue(Double.parseDouble(dr.getResult29()), lineTitle,
                        rowTitle.getResult29());
                return true;
            }
        }
        if (i == 30) {
            if (this.isNeedDraw(rowTitle.getResult30()) && isValidData(dr.getResult30())) {
                dataset.setValue(Double.parseDouble(dr.getResult30()), lineTitle,
                        rowTitle.getResult30());
                return true;
            }
        }
        if (i == 31) {
            if (this.isNeedDraw(rowTitle.getResult31()) && isValidData(dr.getResult31())) {
                dataset.setValue(Double.parseDouble(dr.getResult31()), lineTitle,
                        rowTitle.getResult31());
                return true;
            }
        }
        if (i == 32) {
            if (this.isNeedDraw(rowTitle.getResult32()) && isValidData(dr.getResult32())) {
                dataset.setValue(Double.parseDouble(dr.getResult32()), lineTitle,
                        rowTitle.getResult32());
                return true;
            }
        }
        return false;

    }

    private boolean isNeedDraw(int i, Vector flags) {
        return ((Boolean) flags.get(i)).booleanValue();
    }

    private boolean setDataForDatasetRowForLineWithoutTail(DefaultCategoryDataset dataset, Vector drawResultV, DrawResult rowTitle, int i, int j) {
        DrawResult dr = (DrawResult) drawResultV.get(j);
        String lineTitle = dr.getTitle();
       // this.HandleOnlyOneData(dr);
        Vector flags = this.getDrawFlag(dr);
        if (i == 1) {
            if (this.isNeedDraw(i - 1, flags)) {
                dataset.setValue(Double.parseDouble(dr.getResult01()), lineTitle,
                        rowTitle.getResult01());
                return true;
            }
        }
        if (i == 2) {
            if (this.isNeedDraw(i - 1, flags)) {
                //         dataset.setValue(Double.parseDouble(dr.getResult02()), lineTitle,
                //                          rowTitle.getResult02());
                dataset.setValue(Double.parseDouble(dr.getResult02()), lineTitle,
                        rowTitle.getResult02());
                return true;
            }
        }
        if (i == 3) {
            if (this.isNeedDraw(i - 1, flags)) {
                dataset.setValue(Double.parseDouble(dr.getResult03()), lineTitle,
                        rowTitle.getResult03());
                return true;
            }
        }
        if (i == 4) {
            if (this.isNeedDraw(i - 1, flags)) {
                dataset.setValue(Double.parseDouble(dr.getResult04()), lineTitle,
                        rowTitle.getResult04());
                return true;
            }
        }
        if (i == 5) {
            if (this.isNeedDraw(i - 1, flags)) {
                dataset.setValue(Double.parseDouble(dr.getResult05()), lineTitle,
                        rowTitle.getResult05());
                return true;
            }
        }
        if (i == 6) {
            if (this.isNeedDraw(i - 1, flags)) {
                dataset.setValue(Double.parseDouble(dr.getResult06()), lineTitle,
                        rowTitle.getResult06());
                return true;
            }
        }
        if (i == 7) {
            if (this.isNeedDraw(i - 1, flags)) {
                dataset.setValue(Double.parseDouble(dr.getResult07()), lineTitle,
                        rowTitle.getResult07());
                return true;
            }
        }
        if (i == 8) {
            if (this.isNeedDraw(i - 1, flags)) {
                dataset.setValue(Double.parseDouble(dr.getResult08()), lineTitle,
                        rowTitle.getResult08());
                return true;
            }
        }
        if (i == 9) {
            if (this.isNeedDraw(i - 1, flags)) {
                dataset.setValue(Double.parseDouble(dr.getResult09()), lineTitle,
                        rowTitle.getResult09());
                return true;
            }
        }
        if (i == 10) {
            if (this.isNeedDraw(i - 1, flags)) {
                dataset.setValue(Double.parseDouble(dr.getResult10()), lineTitle,
                        rowTitle.getResult10());
                return true;
            }
        }
        if (i == 11) {
            if (this.isNeedDraw(i - 1, flags)) {
                dataset.setValue(Double.parseDouble(dr.getResult11()), lineTitle,
                        rowTitle.getResult11());
                return true;
            }
        }
        if (i == 12) {
            if (this.isNeedDraw(i - 1, flags)) {
                dataset.setValue(Double.parseDouble(dr.getResult12()), lineTitle,
                        rowTitle.getResult12());
                return true;
            }
        }

        if (i == 13) {
            if (this.isNeedDraw(i - 1, flags)) {
                dataset.setValue(Double.parseDouble(dr.getResult13()), lineTitle,
                        rowTitle.getResult13());
                return true;
            }
        }
        if (i == 14) {
            if (this.isNeedDraw(i - 1, flags)) {
                dataset.setValue(Double.parseDouble(dr.getResult14()), lineTitle,
                        rowTitle.getResult14());
                return true;
            }
        }
        if (i == 15) {
            if (this.isNeedDraw(i - 1, flags)) {
                dataset.setValue(Double.parseDouble(dr.getResult15()), lineTitle,
                        rowTitle.getResult15());
                return true;
            }
        }
        if (i == 16) {
            if (this.isNeedDraw(i - 1, flags)) {
                dataset.setValue(Double.parseDouble(dr.getResult16()), lineTitle,
                        rowTitle.getResult16());
                return true;
            }
        }
        if (i == 17) {
            if (this.isNeedDraw(i - 1, flags)) {
                dataset.setValue(Double.parseDouble(dr.getResult17()), lineTitle,
                        rowTitle.getResult17());
                return true;
            }
        }
        if (i == 18) {
            if (this.isNeedDraw(i - 1, flags)) {
                dataset.setValue(Double.parseDouble(dr.getResult18()), lineTitle,
                        rowTitle.getResult18());
                return true;
            }
        }
        if (i == 19) {
            if (this.isNeedDraw(i - 1, flags)) {
                dataset.setValue(Double.parseDouble(dr.getResult19()), lineTitle,
                        rowTitle.getResult19());
                return true;
            }
        }
        if (i == 20) {
            if (this.isNeedDraw(i - 1, flags)) {
                dataset.setValue(Double.parseDouble(dr.getResult20()), lineTitle,
                        rowTitle.getResult20());
                return true;
            }
        }
        if (i == 21) {
            if (this.isNeedDraw(i - 1, flags)) {
                dataset.setValue(Double.parseDouble(dr.getResult21()), lineTitle,
                        rowTitle.getResult21());
                return true;
            }
        }
        if (i == 22) {
            if (this.isNeedDraw(i - 1, flags)) {
                dataset.setValue(Double.parseDouble(dr.getResult22()), lineTitle,
                        rowTitle.getResult22());
                return true;
            }
        }
        if (i == 23) {
            if (this.isNeedDraw(i - 1, flags)) {
                dataset.setValue(Double.parseDouble(dr.getResult23()), lineTitle,
                        rowTitle.getResult23());
                return true;
            }
        }
        if (i == 24) {
            if (this.isNeedDraw(i - 1, flags)) {
                dataset.setValue(Double.parseDouble(dr.getResult24()), lineTitle,
                        rowTitle.getResult24());
                return true;
            }
        }
        if (i == 25) {
            if (this.isNeedDraw(i - 1, flags)) {
                dataset.setValue(Double.parseDouble(dr.getResult25()), lineTitle,
                        rowTitle.getResult25());
                return true;
            }
        }
        if (i == 26) {
            if (this.isNeedDraw(i - 1, flags)) {
                dataset.setValue(Double.parseDouble(dr.getResult26()), lineTitle,
                        rowTitle.getResult26());
                return true;
            }
        }
        if (i == 27) {
            if (this.isNeedDraw(i - 1, flags)) {
                dataset.setValue(Double.parseDouble(dr.getResult27()), lineTitle,
                        rowTitle.getResult27());
                return true;
            }
        }
        if (i == 28) {
            if (this.isNeedDraw(i - 1, flags)) {
                dataset.setValue(Double.parseDouble(dr.getResult28()), lineTitle,
                        rowTitle.getResult28());
                return true;
            }
        }
        if (i == 29) {
            if (this.isNeedDraw(i - 1, flags)) {
                dataset.setValue(Double.parseDouble(dr.getResult29()), lineTitle,
                        rowTitle.getResult29());
                return true;
            }
        }
        if (i == 30) {
            if (this.isNeedDraw(i - 1, flags)) {
                dataset.setValue(Double.parseDouble(dr.getResult30()), lineTitle,
                        rowTitle.getResult30());
                return true;
            }
        }
        if (i == 31) {
            if (this.isNeedDraw(i - 1, flags)) {
                dataset.setValue(Double.parseDouble(dr.getResult31()), lineTitle,
                        rowTitle.getResult31());
                return true;
            }
        }
        if (i == 32) {
            if (this.isNeedDraw(i - 1, flags)) {
                dataset.setValue(Double.parseDouble(dr.getResult32()), lineTitle,
                        rowTitle.getResult32());
                return true;
            }
        }
        return false;

    }

    private boolean setDataForDatasetRow(DefaultCategoryDataset dataset, Vector drawResultV, DrawResult rowTitle, int i, int j) {
        DrawResult dr = (DrawResult) drawResultV.get(j);
        String lineTitle = dr.getTitle();
        //   this.HandleOnlyOneData( dr);
        if (i == 1) {
            if (this.isNeedDraw(rowTitle.getResult01()) && isValidData(dr.getResult01())) {
                dataset.setValue(Double.parseDouble(dr.getResult01()), lineTitle,
                        rowTitle.getResult01());
                return true;
            }
        }
        if (i == 2) {
            if (this.isNeedDraw(rowTitle.getResult02()) && isValidData(dr.getResult02())) {
                dataset.setValue(Double.parseDouble(dr.getResult02()), lineTitle,
                        rowTitle.getResult02());
                return true;
            }
        }
        if (i == 3) {
            if (this.isNeedDraw(rowTitle.getResult03()) && isValidData(dr.getResult03())) {
                dataset.setValue(Double.parseDouble(dr.getResult03()), lineTitle,
                        rowTitle.getResult03());
                return true;
            }
        }
        if (i == 4) {
            if (this.isNeedDraw(rowTitle.getResult04()) && isValidData(dr.getResult04())) {
                dataset.setValue(Double.parseDouble(dr.getResult04()), lineTitle,
                        rowTitle.getResult04());
                return true;
            }
        }
        if (i == 5) {
            if (this.isNeedDraw(rowTitle.getResult05()) && isValidData(dr.getResult05())) {
                dataset.setValue(Double.parseDouble(dr.getResult05()), lineTitle,
                        rowTitle.getResult05());
                return true;
            }
        }
        if (i == 6) {
            if (this.isNeedDraw(rowTitle.getResult06()) && isValidData(dr.getResult06())) {
                dataset.setValue(Double.parseDouble(dr.getResult06()), lineTitle,
                        rowTitle.getResult06());
                return true;
            }
        }
        if (i == 7) {
            if (this.isNeedDraw(rowTitle.getResult07()) && isValidData(dr.getResult07())) {
                dataset.setValue(Double.parseDouble(dr.getResult07()), lineTitle,
                        rowTitle.getResult07());
                return true;
            }
        }
        if (i == 8) {
            if (this.isNeedDraw(rowTitle.getResult08()) && isValidData(dr.getResult08())) {
                dataset.setValue(Double.parseDouble(dr.getResult08()), lineTitle,
                        rowTitle.getResult08());
                return true;
            }
        }
        if (i == 9) {
            if (this.isNeedDraw(rowTitle.getResult09()) && isValidData(dr.getResult09())) {
                dataset.setValue(Double.parseDouble(dr.getResult09()), lineTitle,
                        rowTitle.getResult09());
                return true;
            }
        }
        if (i == 10) {
            if (this.isNeedDraw(rowTitle.getResult10()) && isValidData(dr.getResult10())) {
                dataset.setValue(Double.parseDouble(dr.getResult10()), lineTitle,
                        rowTitle.getResult10());
                return true;
            }
        }
        if (i == 11) {
            if (this.isNeedDraw(rowTitle.getResult11()) && isValidData(dr.getResult11())) {
                dataset.setValue(Double.parseDouble(dr.getResult11()), lineTitle,
                        rowTitle.getResult11());
                return true;
            }
        }
        if (i == 12) {
            if (this.isNeedDraw(rowTitle.getResult12()) && isValidData(dr.getResult12())) {
                dataset.setValue(Double.parseDouble(dr.getResult12()), lineTitle,
                        rowTitle.getResult12());
                return true;
            }
        }

        if (i == 13) {
            if (this.isNeedDraw(rowTitle.getResult13()) && isValidData(dr.getResult13())) {
                dataset.setValue(Double.parseDouble(dr.getResult13()), lineTitle,
                        rowTitle.getResult13());
                return true;
            }
        }
        if (i == 14) {
            if (this.isNeedDraw(rowTitle.getResult14()) && isValidData(dr.getResult14())) {
                dataset.setValue(Double.parseDouble(dr.getResult14()), lineTitle,
                        rowTitle.getResult14());
                return true;
            }
        }
        if (i == 15) {
            if (this.isNeedDraw(rowTitle.getResult15()) && isValidData(dr.getResult15())) {
                dataset.setValue(Double.parseDouble(dr.getResult15()), lineTitle,
                        rowTitle.getResult15());
                return true;
            }
        }
        if (i == 16) {
            if (this.isNeedDraw(rowTitle.getResult16()) && isValidData(dr.getResult16())) {
                dataset.setValue(Double.parseDouble(dr.getResult16()), lineTitle,
                        rowTitle.getResult16());
                return true;
            }
        }
        if (i == 17) {
            if (this.isNeedDraw(rowTitle.getResult17()) && isValidData(dr.getResult17())) {
                dataset.setValue(Double.parseDouble(dr.getResult17()), lineTitle,
                        rowTitle.getResult17());
                return true;
            }
        }
        if (i == 18) {
            if (this.isNeedDraw(rowTitle.getResult18()) && isValidData(dr.getResult18())) {
                dataset.setValue(Double.parseDouble(dr.getResult18()), lineTitle,
                        rowTitle.getResult18());
                return true;
            }
        }
        if (i == 19) {
            if (this.isNeedDraw(rowTitle.getResult19()) && isValidData(dr.getResult19())) {
                dataset.setValue(Double.parseDouble(dr.getResult19()), lineTitle,
                        rowTitle.getResult19());
                return true;
            }
        }
        if (i == 20) {
            if (this.isNeedDraw(rowTitle.getResult20()) && isValidData(dr.getResult20())) {
                dataset.setValue(Double.parseDouble(dr.getResult20()), lineTitle,
                        rowTitle.getResult20());
                return true;
            }
        }
        if (i == 21) {
            // if (this.isNeedDraw(rowTitle.getResult21()) && isValidData(dr.getResult21())) {
            if (this.isNeedDraw(rowTitle.getResult21()) && isValidData(dr.getResult21())) {
                dataset.setValue(Double.parseDouble(dr.getResult21()), lineTitle,
                        rowTitle.getResult21());
                //  return true;
                return true;
            }
        }
        if (i == 22) {
            if (this.isNeedDraw(rowTitle.getResult22()) && isValidData(dr.getResult22())) {
                dataset.setValue(Double.parseDouble(dr.getResult22()), lineTitle,
                        rowTitle.getResult22());
                return true;
            }
        }
        //  if (i == 23) {
        if (i == 23) {
            if (this.isNeedDraw(rowTitle.getResult23()) && isValidData(dr.getResult23())) {
                dataset.setValue(Double.parseDouble(dr.getResult23()), lineTitle,
                        rowTitle.getResult23());
                return true;
            }
        }
        if (i == 24) {
            if (this.isNeedDraw(rowTitle.getResult24()) && isValidData(dr.getResult24())) {
                dataset.setValue(Double.parseDouble(dr.getResult24()), lineTitle,
                        rowTitle.getResult24());
                return true;
            }
        }
        if (i == 25) {
            if (this.isNeedDraw(rowTitle.getResult25()) && isValidData(dr.getResult25())) {
                dataset.setValue(Double.parseDouble(dr.getResult25()), lineTitle,
                        rowTitle.getResult25());
                return true;
            }
        }
        if (i == 26) {
            if (this.isNeedDraw(rowTitle.getResult26()) && isValidData(dr.getResult26())) {
                dataset.setValue(Double.parseDouble(dr.getResult26()), lineTitle,
                        rowTitle.getResult26());
                return true;
            }
        }
        if (i == 27) {
            if (this.isNeedDraw(rowTitle.getResult27()) && isValidData(dr.getResult27())) {
                dataset.setValue(Double.parseDouble(dr.getResult27()), lineTitle,
                        rowTitle.getResult27());
                return true;
            }
        }
        if (i == 28) {
            if (this.isNeedDraw(rowTitle.getResult28()) && isValidData(dr.getResult28())) {
                dataset.setValue(Double.parseDouble(dr.getResult28()), lineTitle,
                        rowTitle.getResult28());
                return true;
            }
        }
        if (i == 29) {
            if (this.isNeedDraw(rowTitle.getResult29()) && isValidData(dr.getResult29())) {
                dataset.setValue(Double.parseDouble(dr.getResult29()), lineTitle,
                        rowTitle.getResult29());
                return true;
            }
        }
        if (i == 30) {
            if (this.isNeedDraw(rowTitle.getResult30()) && isValidData(dr.getResult30())) {
                dataset.setValue(Double.parseDouble(dr.getResult30()), lineTitle,
                        rowTitle.getResult30());
                return true;
            }
        }
        if (i == 31) {
            if (this.isNeedDraw(rowTitle.getResult31()) && isValidData(dr.getResult31())) {
                dataset.setValue(Double.parseDouble(dr.getResult31()), lineTitle,
                        rowTitle.getResult31());
                return true;
            }
        }
        if (i == 32) {
            if (this.isNeedDraw(rowTitle.getResult32()) && isValidData(dr.getResult32())) {
                dataset.setValue(Double.parseDouble(dr.getResult32()), lineTitle,
                        rowTitle.getResult32());
                return true;
            }
        }
        //    return false;
        return false;

    }
    //   public void setLineDataResultFlag(int i){
    //    public void setLineDataResultFlag(int i){

    public void setLineDataResultFlag(int i) {
        if (i == 1) {
            this.isExistingValidDataForLineOne = true;
        }
        if (i == 2) {
            this.isExistingValidDataForLineTwo = true;
        }
        if (i == 3) {
            this.isExistingValidDataForLineThree = true;
        }
        if (i == 4) {
            this.isExistingValidDataForLineFour = true;
        }
        if (i == 5) {
            this.isExistingValidDataForLineFive = true;
        }
        if (i == 6) {
            this.isExistingValidDataForLineSix = true;
        }
        if (i == 7) {
            this.isExistingValidDataForLineSeven = true;
        }
        if (i == 8) {
            this.isExistingValidDataForLineEight = true;
        }
        if (i == 9) {
            this.isExistingValidDataForLineNine = true;
        }
        if (i == 10) {
            this.isExistingValidDataForLineTen = true;
        }
        if (i == 11) {
            this.isExistingValidDataForLineElevent = true;
        }


    }

    public boolean setSerialColorBuffer(int j, int i) {
        Integer jOb = new Integer(j);
        Integer iOb = new Integer(i);
        
        if (DrawUtil.COLOR_BUFFER.containsValue(iOb)) {
            return false;
        }
        DrawUtil.COLOR_BUFFER.put(jOb, iOb);
        return true;

    }

    public void setFirstSeries(Vector drawResultV, int j) {
        DrawResult dr = (DrawResult) drawResultV.get(j);
        String lineTitle = dr.getTitle();
        this.firstSeriesName = lineTitle;

    }

    public void HandleOnlyOneData(DrawResult dr) {
        int i = -1;
        int n = 0;
        if (this.isValidData(dr.getResult01())) {
            i = 1;
            n++;
        }
        if (this.isValidData(dr.getResult02())) {
            i = 2;
            n++;
        }
        if (this.isValidData(dr.getResult03())) {
            i = 3;
            n++;
        }
        if (this.isValidData(dr.getResult04())) {
            i = 4;
            n++;
        }
        if (this.isValidData(dr.getResult05())) {
            i = 5;
            n++;
        }
        if (this.isValidData(dr.getResult06())) {
            i = 6;
            n++;
        }
        if (this.isValidData(dr.getResult07())) {
            i = 7;
            n++;
        }
        if (this.isValidData(dr.getResult08())) {
            i = 8;
            n++;
        }
        if (this.isValidData(dr.getResult09())) {
            i = 9;
            n++;
        }
        if (this.isValidData(dr.getResult10())) {
            i = 10;
            n++;
        }
        if (this.isValidData(dr.getResult11())) {
            i = 11;
            n++;
        }
        if (this.isValidData(dr.getResult12())) {
            i = 12;
            n++;
        }
        if (this.isValidData(dr.getResult13())) {
            i = 13;
            n++;
        }
        if (this.isValidData(dr.getResult14())) {
            i = 14;
            n++;
        }
        if (this.isValidData(dr.getResult15())) {
            i = 15;
            n++;
        }
        if (this.isValidData(dr.getResult16())) {
            i = 16;
            n++;
        }
        if (this.isValidData(dr.getResult17())) {
            i = 17;
            n++;
        }
        if (this.isValidData(dr.getResult18())) {
            i = 18;
            n++;
        }
        if (this.isValidData(dr.getResult19())) {
            i = 19;
            n++;
        }
        if (this.isValidData(dr.getResult20())) {
            i = 20;
            n++;
        }
        if (this.isValidData(dr.getResult21())) {
            i = 21;
            n++;
        }
        if (this.isValidData(dr.getResult22())) {
            i = 22;
            n++;
        }
        if (this.isValidData(dr.getResult23())) {
            i = 23;
            n++;
        }
        if (this.isValidData(dr.getResult24())) {
            i = 24;
            n++;
        }
        if (this.isValidData(dr.getResult25())) {
            i = 25;
            n++;
        }
        if (this.isValidData(dr.getResult26())) {
            i = 26;
            n++;
        }
        if (this.isValidData(dr.getResult27())) {
            i = 27;
            n++;
        }
        if (this.isValidData(dr.getResult28())) {
            i = 28;
            n++;
        }
        if (this.isValidData(dr.getResult29())) {
            i = 29;
            n++;
        }
        if (this.isValidData(dr.getResult30())) {
            i = 30;
            n++;
        }
        if (this.isValidData(dr.getResult31())) {
            i = 31;
            n++;
        }
        if (this.isValidData(dr.getResult32())) {
            i = 32;
            n++;
        }

        if (n == 1) {//仅有一个有效数据
            //if(i=1)
            if (i == 1) {
                dr.setResult02("0.1");
            }
            if (i == 2) {
                dr.setResult03("0.1");
            }
            if (i == 3) {
                dr.setResult02("0.1");
            }
            if (i == 4) {
                dr.setResult03("0.1");
            }
            if (i == 5) {
                dr.setResult04("0.1");
            }
            if (i == 6) {
                dr.setResult05("0.1");
            }
            if (i == 7) {
                dr.setResult06("0.1");
            }
            if (i == 8) {
                dr.setResult07("0.1");
            }
            if (i == 9) {
                dr.setResult08("0.1");
            }
            if (i == 10) {
                dr.setResult09("0.1");
            }
            if (i == 11) {
                dr.setResult10("0.1");
            }
            if (i == 12) {
                dr.setResult11("0.1");
            }
            if (i == 13) {
                dr.setResult12("0.1");
            }
            if (i == 14) {
                dr.setResult13("0.1");
            }
            if (i == 15) {
                dr.setResult14("0.1");
            }
            if (i == 16) {
                dr.setResult15("0.1");
            }
            if (i == 17) {
                dr.setResult16("0.1");
            }
            if (i == 18) {
                dr.setResult17("0.1");
            }
            if (i == 19) {
                dr.setResult18("0.1");
            }
            if (i == 20) {
                dr.setResult19("0.1");
            }
            if (i == 21) {
                dr.setResult20("0.1");
            }
            if (i == 22) {
                dr.setResult21("0.1");
            }
            if (i == 23) {
                dr.setResult22("0.1");
            }
            if (i == 24) {
                dr.setResult23("0.1");
            }
            if (i == 25) {
                dr.setResult24("0.1");
            }
            if (i == 26) {
                dr.setResult25("0.1");
            }
            if (i == 27) {
                dr.setResult26("0.1");
            }
            if (i == 28) {
                dr.setResult27("0.1");
            }
            if (i == 29) {
                dr.setResult28("0.1");
            }
            if (i == 30) {
                dr.setResult29("0.1");
            }
            if (i == 31) {
                dr.setResult30("0.1");
            }
            if (i == 32) {
                dr.setResult31("0.1");
            }

        }

    }

    private DefaultCategoryDataset prepareCategoryDataForScreen(Vector drawResultV) throws Exception {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        //横坐标数据
        DrawResult rowTitle = (DrawResult) drawResultV.get(0);
        int line = drawResultV.size();
        boolean result = false;
        boolean isInOut = this.isInOut(drawResultV);
        int k = 0;
        //设置横坐标轴数据
        this.setDataForXAxis(rowTitle, dataset);

        DrawUtil.COLOR_BUFFER.clear();
        DrawResult dr;
        //绘图数据先按时间，再按序列
        //按时间最大为32，按序列则是所有线路或进出
        //如先赋01时的1、2、3、4、5号线数据，再赋02时的1、2、3、4、5号线数据........
        for (int i = 1; i <= 32; i++) {//按横坐标的设置数据
            for (int j = 1; j < line; j++) {//再按序列如线路或进、出
                result = this.setDataForDatasetRow(dataset, drawResultV, rowTitle, i, j);
                if (result) {
                    dr = (DrawResult) drawResultV.get(j);
                    k = dr.getLineId();
                    //设置绘图数据有效标志
                    this.setLineDataResultFlag(k);
                    //设置序列的绘图颜色索引对照关系 键：数据在集合的索引1、2、3... 值：线路id的整数值
                    this.setSerialColorBuffer(j, k);

                    if (isInOut) {
                        if (this.firstSeriesName == null) {
                            this.setFirstSeries(drawResultV, j);
                        }

                    }
                    result = false;
                }
            }

        }
        return dataset;
    }


    private void setDataForXAxis(DrawResult rowTitle, DefaultCategoryDataset dataset) {
        double n = 0.0;
        String rowKey = "";
        this.setDataForXAxisUnit(dataset, rowKey, rowTitle.getResult01(), n);
        this.setDataForXAxisUnit(dataset, rowKey, rowTitle.getResult02(), n);
        this.setDataForXAxisUnit(dataset, rowKey, rowTitle.getResult03(), n);
        this.setDataForXAxisUnit(dataset, rowKey, rowTitle.getResult04(), n);
        this.setDataForXAxisUnit(dataset, rowKey, rowTitle.getResult05(), n);
        this.setDataForXAxisUnit(dataset, rowKey, rowTitle.getResult06(), n);
        this.setDataForXAxisUnit(dataset, rowKey, rowTitle.getResult07(), n);
        this.setDataForXAxisUnit(dataset, rowKey, rowTitle.getResult08(), n);
        this.setDataForXAxisUnit(dataset, rowKey, rowTitle.getResult09(), n);
        this.setDataForXAxisUnit(dataset, rowKey, rowTitle.getResult10(), n);
        this.setDataForXAxisUnit(dataset, rowKey, rowTitle.getResult11(), n);
        this.setDataForXAxisUnit(dataset, rowKey, rowTitle.getResult12(), n);
        this.setDataForXAxisUnit(dataset, rowKey, rowTitle.getResult13(), n);
        this.setDataForXAxisUnit(dataset, rowKey, rowTitle.getResult14(), n);
        this.setDataForXAxisUnit(dataset, rowKey, rowTitle.getResult15(), n);
        this.setDataForXAxisUnit(dataset, rowKey, rowTitle.getResult16(), n);
        this.setDataForXAxisUnit(dataset, rowKey, rowTitle.getResult17(), n);
        this.setDataForXAxisUnit(dataset, rowKey, rowTitle.getResult18(), n);
        this.setDataForXAxisUnit(dataset, rowKey, rowTitle.getResult19(), n);
        this.setDataForXAxisUnit(dataset, rowKey, rowTitle.getResult20(), n);
        this.setDataForXAxisUnit(dataset, rowKey, rowTitle.getResult21(), n);
        this.setDataForXAxisUnit(dataset, rowKey, rowTitle.getResult22(), n);
        this.setDataForXAxisUnit(dataset, rowKey, rowTitle.getResult23(), n);
        this.setDataForXAxisUnit(dataset, rowKey, rowTitle.getResult24(), n);
        this.setDataForXAxisUnit(dataset, rowKey, rowTitle.getResult25(), n);
        this.setDataForXAxisUnit(dataset, rowKey, rowTitle.getResult26(), n);
        this.setDataForXAxisUnit(dataset, rowKey, rowTitle.getResult27(), n);
        this.setDataForXAxisUnit(dataset, rowKey, rowTitle.getResult28(), n);
        this.setDataForXAxisUnit(dataset, rowKey, rowTitle.getResult29(), n);
        this.setDataForXAxisUnit(dataset, rowKey, rowTitle.getResult30(), n);
        this.setDataForXAxisUnit(dataset, rowKey, rowTitle.getResult31(), n);
        this.setDataForXAxisUnit(dataset, rowKey, rowTitle.getResult32(), n);


    }

    private void setDataForXAxisUnit(DefaultCategoryDataset dataset, String rowKey, String colKey, double n) {
        if (this.isValidData(colKey)) {
            dataset.setValue(n, rowKey, colKey);
        }
    }

    private Vector getDrawFlag(DrawResult dr) {
        Vector flags = new Vector();
        int index = this.getLastestNonZeroIndex(dr);
        int i;
        for (i = 0; i <= index; i++) {
            flags.add(new Boolean(true));
        }
        for (i = index + 1; i < 32; i++) {
            flags.add(new Boolean(false));
        }

        return flags;
    }

    private int getLastestNonZeroIndex(DrawResult dr) {

        if (this.isValidDataExceptZero(dr.getResult32())) {
            return 31;
        }
        if (this.isValidDataExceptZero(dr.getResult31())) {
            return 30;
        }
        if (this.isValidDataExceptZero(dr.getResult30())) {
            return 29;
        }
        if (this.isValidDataExceptZero(dr.getResult29())) {
            return 28;
        }
        if (this.isValidDataExceptZero(dr.getResult28())) {
            return 27;
        }
        if (this.isValidDataExceptZero(dr.getResult27())) {
            return 26;
        }
        if (this.isValidDataExceptZero(dr.getResult26())) {
            return 25;
        }
        if (this.isValidDataExceptZero(dr.getResult25())) {
            return 24;
        }
        if (this.isValidDataExceptZero(dr.getResult24())) {
            return 23;
        }
        if (this.isValidDataExceptZero(dr.getResult23())) {
            return 22;
        }
        if (this.isValidDataExceptZero(dr.getResult22())) {
            return 21;
        }
        if (this.isValidDataExceptZero(dr.getResult21())) {
            return 20;
        }
        if (this.isValidDataExceptZero(dr.getResult20())) {
            return 19;
        }
        if (this.isValidDataExceptZero(dr.getResult19())) {
            return 18;
        }
        if (this.isValidDataExceptZero(dr.getResult18())) {
            return 17;
        }
        if (this.isValidDataExceptZero(dr.getResult17())) {
            return 16;
        }
        if (this.isValidDataExceptZero(dr.getResult16())) {
            return 15;
        }
        if (this.isValidDataExceptZero(dr.getResult15())) {
            return 14;
        }
        if (this.isValidDataExceptZero(dr.getResult14())) {
            return 13;
        }
        if (this.isValidDataExceptZero(dr.getResult13())) {
            return 12;
        }
        if (this.isValidDataExceptZero(dr.getResult12())) {
            return 11;
        }
        if (this.isValidDataExceptZero(dr.getResult11())) {
            return 10;
        }
        if (this.isValidDataExceptZero(dr.getResult10())) {
            return 9;
        }
        if (this.isValidDataExceptZero(dr.getResult09())) {
            return 8;
        }
        if (this.isValidDataExceptZero(dr.getResult08())) {
            return 7;
        }
        if (this.isValidDataExceptZero(dr.getResult07())) {
            return 6;
        }
        if (this.isValidDataExceptZero(dr.getResult06())) {
            return 5;
        }
        if (this.isValidDataExceptZero(dr.getResult05())) {
            return 4;
        }
        if (this.isValidDataExceptZero(dr.getResult04())) {
            return 3;
        }
        if (this.isValidDataExceptZero(dr.getResult03())) {
            return 2;
        }
        if (this.isValidDataExceptZero(dr.getResult02())) {
            return 1;
        }
        if (this.isValidDataExceptZero(dr.getResult01())) {
            return 0;
        }
        return 0;
    }

    private DefaultCategoryDataset prepareCategoryDataForScreenLine(Vector drawResultV) throws Exception {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        //时间
        DrawResult rowTitle = (DrawResult) drawResultV.get(0);
        //序列
        int line = drawResultV.size();
        boolean result = false;
        boolean isInOut = this.isInOut(drawResultV);
        int k = 1;
        //设置横坐标轴数据
        this.setDataForXAxis(rowTitle, dataset);
        DrawResult dr;

        DrawUtil.COLOR_BUFFER.clear();
        for (int i = 1; i <= 32; i++) {//按横坐标的设置数据
            for (int j = 1; j < line; j++) {
                result = this.setDataForDatasetRowForLineWithoutTail(dataset, drawResultV, rowTitle, i, j);
                if (result) {
                    dr = (DrawResult) drawResultV.get(j);
                    k = dr.getLineId();
                    //设置绘图数据有效标志
                    this.setLineDataResultFlag(k);
                    //设置序列的绘图颜色索引对照关系 键：数据在集合的索引1、2、3... 值：线路id的整数值

                    this.setSerialColorBuffer(j, k);
                    /*
                    if (this.setSerialColorBuffer(j, k)) {
                    k++;
                    }
                     */
                    if (isInOut) {
                        if (this.firstSeriesName == null) {
                            this.setFirstSeries(drawResultV, j);//设置第一个可视序列名称
                        }

                    }
                    result = false;
                }
            }

        }

        return dataset;
    }
}

