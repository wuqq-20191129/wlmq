/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.fm.common;

import com.goldsign.fm.dao.LineDao;
import com.goldsign.fm.dao.StationDao;
import com.goldsign.fm.dao.TrafficCardTypeDao;
import com.goldsign.fm.dao.TrafficHourDao;
import com.goldsign.fm.dao.TrafficLineStationDao;
import com.goldsign.fm.dao.TrafficMinDao;
import com.goldsign.fm.dao.TrafficNetDao;

import com.goldsign.fm.listener.TreeNetSelectListener;
import com.goldsign.fm.table.JTableData;
import com.goldsign.fm.table.JTableDataCellRender;
import com.goldsign.fm.table.JTableModeData;
import com.goldsign.fm.thread.RefreshThread;
import com.goldsign.fm.thread.TimeThread;
import com.goldsign.fm.vo.DrawOriginResult;
import com.goldsign.fm.vo.DrawResult;
import com.goldsign.fm.vo.LineTitle;
import com.goldsign.fm.vo.LineVo;
import com.goldsign.fm.vo.RowTitle;
import com.goldsign.fm.vo.ScreenStationTrafficVO;
import com.goldsign.fm.vo.ScreenStationsTrafficVO;
import com.goldsign.fm.vo.StationVo;
import com.goldsign.fm.vo.SynVo;
import com.goldsign.fm.vo.TreeNode;
import com.goldsign.fm.vo.ViewVo;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.DisplayMode;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.text.NumberFormat;
import java.util.Date;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.Vector;


import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import javax.swing.JTree;
import javax.swing.ScrollPaneConstants;


import javax.swing.table.JTableHeader;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeSelectionModel;
import org.apache.log4j.Logger;

import org.apache.log4j.xml.DOMConfigurator;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;

/**
 *
 * @author Administrator
 */
public class AppUtil {

    private static final SynVo synVo = new SynVo();
    private static Logger logger = Logger.getLogger(AppUtil.class.getName());
    private static String prefix = "chart";
    //private static String surfix = ".jpg";
    private static String surfix = ".png";
    private final static String CHN = "GBK";
    private final static String ISO = "8859_1";
    public final static String Hour = "小时";
    //public final static String[] Hours={"1h","2h","3h","4h","5h","6h","7h","8h","9h","10h","11h","12h","13h","14h","15h","16h","17h","18h","19h","20h","21h","22h","23h","24h"};
    public final static int[] HoursKey = {2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 0, 1,-1};
    public final static String[] Hours = {"2+", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "0", "1","2-"};
    public final static String[] Mins = {"00", "05", "10", "15", "20", "25", "30", "35", "40", "45", "50", "55"};
    public static String[] MinTitles = null;
    public static String[] MinTitlesFormat = null;
    public final static String Day = "日期";
    //public final static String[] Days={"1号","2号","3号","4号","5号","6号","7号","8号","9号","10号","11号","12号","13号","14号","15号","16号","17号","18号","19号","20号","21号","22号","23号","24号","25号","26号","27号","28号","29号","30号","31号"};
    public final static String[] Days = {"", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"};
    public final static String Month = "月份";
    //public final static String[] Months={"","1月","2月","3月","4月","5月","6月","7月","8月","9月","10月","11月","12月"};
    public final static String[] Months = {"", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};
    private static int picNo = 0;
    //reduce dump pictures,if more than 99 operators use this application at the same time,this parameter should be enlarged.
    private static final int tmpPics = 99;

    /**
     * 取配置文件线路颜色值
     * @param common 
     */
    private void getColorLine(Hashtable common) {
        String colorLines = (String) common.get(XMLSAXHandler.Common_ColorLine_TAG);
        StringTokenizer st = new StringTokenizer(colorLines,"#");
        String token;
        int index;
        String line;
        String color;
        int[] rgb;
        while(st.hasMoreTokens()){
            token = st.nextToken();
            index = token.indexOf(",");
            line = token.substring(0,index);
            color = token.substring(index+1);
            rgb =this.getColorRGB(color);
            DrawUtil.COLOR_LINES.put(new Integer(line), new Color(rgb[0],rgb[1],rgb[2]));
        }
    }
    
    /**
     * 取配置文件票卡类型颜色值
     * @param common 
     */
    private void getColorCard(Hashtable common) {
        String colorLines = (String) common.get(XMLSAXHandler.Common_ColorCard_TAG);
        StringTokenizer st = new StringTokenizer(colorLines,"#");
        String token;
        int index;
        String cardType;
        String color;
        int[] rgb;
        while(st.hasMoreTokens()){
            token = st.nextToken();
            index = token.indexOf(",");
            cardType = token.substring(0,index);
            color = token.substring(index+1);
            rgb =this.getColorRGB(color);
            DrawUtil.COLOR_CARDS.put(cardType, new Color(rgb[0],rgb[1],rgb[2]));
        }
    }
    
    /**
     * 取RGB
     * @param color
     * @return 
     */
    private int[] getColorRGB(String color){
        int[] rgb =new int[3];
        StringTokenizer st = new StringTokenizer(color,",");
        int i=0;
        while(st.hasMoreTokens()){
            rgb[i]=Integer.parseInt((String)st.nextToken());
            i++;
        }
        return rgb;
    }

    /**
     * 初始化加载XML配置文件信息
     * @throws Exception 
     */
    public void getInitConfiguration() throws Exception {
        XMLSAXHandler handler = new XMLSAXHandler();
        Hashtable config = handler.parseConfigFile(AppConstant.configFile);
        AppConstant.dbConfig = (Hashtable) config.get(XMLSAXHandler.DataConnectionPool_TAG);
        //数据源1
        String driver1 = (String) AppConstant.dbConfig.get(XMLSAXHandler.ConnectionPool_Driver_TAG1);
        String url1 = (String) AppConstant.dbConfig.get(XMLSAXHandler.ConnectionPool_URL_TAG1);
        String userId1 = (String) AppConstant.dbConfig.get(XMLSAXHandler.ConnectionPool_User_TAG1);
        String password1 = (String) AppConstant.dbConfig.get(XMLSAXHandler.ConnectionPool_Password_TAG1);
        //数据源2
        String driver2 = (String) AppConstant.dbConfig.get(XMLSAXHandler.ConnectionPool_Driver_TAG2);
        String url2 = (String) AppConstant.dbConfig.get(XMLSAXHandler.ConnectionPool_URL_TAG2);
        String userId2 = (String) AppConstant.dbConfig.get(XMLSAXHandler.ConnectionPool_User_TAG2);
        String password2 = (String) AppConstant.dbConfig.get(XMLSAXHandler.ConnectionPool_Password_TAG2);
        
        int maxActive = new Integer((String) AppConstant.dbConfig.get(XMLSAXHandler.ConnectionPool_MaxActive_TAG)).intValue();
        int maxIdle = new Integer((String) AppConstant.dbConfig.get(XMLSAXHandler.ConnectionPool_MaxIdle_TAG)).intValue();
        int maxWait = new Integer((String) AppConstant.dbConfig.get(XMLSAXHandler.ConnectionPool_MaxWait_TAG)).intValue();
        AppConstant.dbcpHelper1 = new DbcpHelper(driver1, url1, userId1, password1, maxActive, maxIdle, maxWait);
        AppConstant.dbcpHelper2 = new DbcpHelper(driver2, url2, userId2, password2, maxActive, maxIdle, maxWait);
        
        //获取日志备份目录
        Hashtable common = (Hashtable) config.get(XMLSAXHandler.Common_TAG);
        //线路颜色
        this.getColorLine(common);
        //票卡类型颜色
        this.getColorCard(common);
        //刷新时间间隔
        AppConstant.TIME_INTERVAL_REFRESH = new Long((String) common.get(XMLSAXHandler.Common_FreshInterval_TAG)).longValue();
        //设备状态图片大小
        AppConstant.IMAGE_SIZES_BUFFER = ConfigUtil.getConfigProperties(AppConstant.IMAGE_PROPERTIES_NAME);
        //是否锁定键盘
        AppConstant.isLocked = new Boolean((String) common.get(XMLSAXHandler.Common_isLocked_TAG)).booleanValue();
        //初始化屏幕大小比率
        setScreenSize(common);

        //生成日志文件
        String backupDir = AppConstant.appWorkDir + "/log";
        int index = AppConstant.logFileName.indexOf(".");
        String logName = AppConstant.logFileName.substring(0, index);
        String oldLog = logName + "-" + DateHelper.datetimeToStringForWindows(new Date()) + ".log";
        File oldLogFile = new File(backupDir, oldLog);
        File logFile = new File(AppConstant.logFileName);
        if (logFile.exists()) {
            logFile.renameTo(oldLogFile);
        }

        //加载Log4j
        try {
            DOMConfigurator.configureAndWatch(AppConstant.log4jConfigFile);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

    }

    /**
     * 生成左边线网树
     * @param scrollPannel
     * @param jPanelChart JfreeChart图形
     * @param jScrollPannel 
     * @param jLabelCaptionData 表格标题
     * @param jLabelCaptionGraph 图形标题
     * @param jScrollPane1Dev 设备图
     * @throws Exception 
     */
    public void showLineNet(JScrollPane scrollPannel, JPanel jPanelChart, JScrollPane jScrollPannel,
            JLabel jLabelCaptionData, JLabel jLabelCaptionGraph ,JScrollPane jScrollPane1Dev) throws Exception {
        LineDao lineDao = new LineDao();
        StationDao stationDao = new StationDao();
        Vector lines = lineDao.getLineAll();
        HashMap stations = stationDao.getStationAllByLine();
        LineVo lineVo;
        
        //根节点（线网）
        DefaultMutableTreeNode root = new DefaultMutableTreeNode(this.getTreeNode("all_-1", "线网", AppConstant.NODE_TYPE_NET));
        DefaultMutableTreeNode child;
        // DefaultTreeModel model = new DefaultTreeModel();

        //二级节点(线路）
        for (int i = 0; i < lines.size(); i++) {
            lineVo = (LineVo) lines.get(i);
            child = new DefaultMutableTreeNode(this.getTreeNode(lineVo.getLineId() + "_-1", lineVo.getLineName(), AppConstant.NODE_TYPE_LINE));
            root.add(child);
            //添加三级节点（车站）
            this.addLineStations(child, lineVo.getLineId(), stations);
        }
        
        JTree tree = new JTree(root);
        //TreeNetListener listener = new TreeNetListener(tree,jPanelChart);
        //tree.addMouseListener(listener);
        //添加树节点的选择监听器
        tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        TreeNetSelectListener listener = new TreeNetSelectListener(tree, jPanelChart, jScrollPannel, jLabelCaptionData, jLabelCaptionGraph, jScrollPane1Dev);
        tree.addTreeSelectionListener(listener);
        this.setTreeProperties(tree);

        scrollPannel.setViewportView(tree);
    }

    /**
     * 设置树属性
     * @param tree 
     */
    private void setTreeProperties(JTree tree) {
        tree.setBackground(AppConstant.TREE_COLOR_SHOW_BACKGROUND);
        DefaultTreeCellRenderer cellRender = (DefaultTreeCellRenderer) tree.getCellRenderer();
        cellRender.setBackground(AppConstant.TREE_COLOR_SHOW_BACKGROUND);
        cellRender.setBackgroundNonSelectionColor(AppConstant.TREE_COLOR_SHOW_BACKGROUND);
        cellRender.setTextNonSelectionColor(AppConstant.TREE_COLOR_FONT_FOREGROUND);
        cellRender.setFont(AppConstant.TREE_FONT);
        cellRender.repaint();
    }

    /**
     * 树节点对象
     */
    private TreeNode getTreeNode(String id, String name, String nodeType) {
        TreeNode node = new TreeNode();
        node.setNodeType(nodeType);
        node.setNodeId(id);
        node.setName(name);
        return node;
    }

    /**
     * 展开线路车站节点
     */
    private void addLineStations(DefaultMutableTreeNode lineNode, String line, HashMap stations) {
        Vector lineStations = (Vector) stations.get(line);
        if(lineStations ==null)
            return;
        StationVo stationVo;
        DefaultMutableTreeNode child;
        for (int i = 0; i < lineStations.size(); i++) {
            stationVo = (StationVo) lineStations.get(i);
            child = new DefaultMutableTreeNode(this.getTreeNode(stationVo.getLineId() + "_" + stationVo.getStationId(), stationVo.getChineseName(), AppConstant.NODE_TYPE_STATION));
            lineNode.add(child);
        }
    }

    /**
     * 启动时间线程
     * @param label 
     */
    public void startTimeThread(JLabel label) {
        TimeThread t = new TimeThread(label);
        t.start();
    }

    /**
     * 刷新线程
     */
    public void startRefrshThread(JTree tree,
            JPanel jPanelGraph, JScrollPane jScrollPane1Data, JLabel jLabelDataCaption, JLabel jLabelGraphCaption, JScrollPane jScrollPane1Dev) {
        RefreshThread t = new RefreshThread(tree, jPanelGraph, jScrollPane1Data, jLabelDataCaption, jLabelGraphCaption, jScrollPane1Dev);
        t.start();
    }

    public String getFullChartName(String charName) {
        return AppConstant.appWorkDir + "/images/chart/" + charName;
    }
    
    /**
     * 更新图表到图形区（线性图）
     * @param jPanelChart
     * @param chart 
     */
    public void addChart(JPanel jPanelChart, JFreeChart chart) {
        jPanelChart.removeAll();

        ChartPanel jPanelChild = new ChartPanel(chart);

        jPanelChart.updateUI();
        jPanelChild.updateUI();

        javax.swing.GroupLayout jPanelChildLayout = new javax.swing.GroupLayout(jPanelChild);
        jPanelChild.setLayout(jPanelChildLayout);
        jPanelChildLayout.setHorizontalGroup(
                jPanelChildLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 295, Short.MAX_VALUE));
        jPanelChildLayout.setVerticalGroup(
                jPanelChildLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 297, Short.MAX_VALUE));
        javax.swing.GroupLayout jPanelChartLayout = new javax.swing.GroupLayout(jPanelChart);
        jPanelChart.setLayout(jPanelChartLayout);

        jPanelChart.updateUI();
        jPanelChild.updateUI();

        jPanelChartLayout.setHorizontalGroup(
                jPanelChartLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(jPanelChild, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));

        jPanelChartLayout.setVerticalGroup(
                jPanelChartLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(jPanelChild, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
        jPanelChart.updateUI();
        jPanelChild.updateUI();

    }

    public static void setIcon(JDialog comp, String imageName) {

        ImageIcon icon = new ImageIcon(AppConstant.appWorkDir + "/images/" + imageName);
        int width = 16;
        int height = 16;
        // logger.debug("lable width="+width+" lable heigth="+height);

        Image img = icon.getImage();
        img = img.getScaledInstance(width, height, Image.SCALE_DEFAULT);
        // ImageIcon icon = new ImageIcon(AppConstant.appWorkDir + "/images/logo.jpg");
        comp.setIconImage(img);
    }

    public void addLogo(JLabel label) {
        ImageIcon icon = new ImageIcon(AppConstant.appWorkDir + "/images/logo.jpg");
        int width = 0;
        int height = 0;

        Image img = icon.getImage();
        width = label.getWidth();
        height = label.getHeight();
        // logger.debug("lable width="+width+" lable heigth="+height);

        img = img.getScaledInstance(width, height, Image.SCALE_DEFAULT);

        icon.setImage(img);
        width = icon.getIconWidth();
        height = icon.getIconHeight();
        // logger.debug("new width="+width+" new heigth="+height);

        label.setIcon(icon);

    }

    /**
     * 设置主窗口大小
     * @param frame 
     */
    public void maxWindow(JFrame frame) {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd = ge.getDefaultScreenDevice();
        //这个代码有问题，在弹出对话框后，移动对话框，会出现对话框看不见的情况
        //gd.setFullScreenWindow(this);
        DisplayMode dm = gd.getDisplayMode();
        frame.setBounds(0, 0, dm.getWidth(), dm.getHeight());
        frame.setVisible(true);
        frame.toFront();
    }

    public String getPicForSpanHour(Vector drV, String prePicName) {
        return this.drawBarScreen("",
                AppConstant.SCREEN_CAPTION_ROW_HOUR,
                AppConstant.SCREEN_CAPTION_COLUMN_PERSON, drV,
                prePicName);
    }

    public JFreeChart getPicForSpanHourForLocal(Vector drV) {
        return this.drawBarScreenForLocal("",
                AppConstant.SCREEN_CAPTION_ROW_HOUR,
                AppConstant.SCREEN_CAPTION_COLUMN_PERSON, drV);
    }

    public JFreeChart getPicForSpanMinForLocal(Vector drV) {
        return this.drawLineScreenForLocal("",
                AppConstant.SCREEN_CAPTION_ROW_MIN,
                AppConstant.SCREEN_CAPTION_COLUMN_PERSON, drV);
    }

    private String drawBarScreen(String picTitle, String hTitle, String vTitle, Vector drawResultV, String picName) {
        DrawUtil drawUtil = new DrawUtil();
        //String file = getPicName();
        String result = drawUtil.drawBarScreen(picName, picTitle, hTitle, vTitle, drawResultV, "");
        return picName;
    }

    private JFreeChart drawBarScreenForLocal(String picTitle, String hTitle, String vTitle, Vector drawResultV) {
        DrawUtil drawUtil = new DrawUtil();
        //String file = getPicName();
        return drawUtil.drawBarScreenForLocal(picTitle, hTitle, vTitle, drawResultV);
    }

    private JFreeChart drawLineScreenForLocal(String picTitle, String hTitle, String vTitle, Vector drawResultV) {
        DrawUtil drawUtil = new DrawUtil();
        //String file = getPicName();
        return drawUtil.drawLineScreenForLocal(picTitle, hTitle, vTitle, drawResultV);

    }
    
    public Vector getLineStationSpanTraffic(
            String clickNode, String currentTrafficDate) throws Exception {
        Vector drV = null;
        Vector dorV = new Vector();
        Vector lineTitleV = new Vector();
        TrafficHourDao trafficDao = new TrafficHourDao();
//        LineDao lineDao = new LineDao();
        PubUtil util = new PubUtil();

        long startTime =System.currentTimeMillis();
        long endTime;

        //获取小时客流原始数据
        dorV = (Vector) trafficDao.getLineStationSpanTraffic(clickNode, currentTrafficDate);
        for (int i = 0; i < dorV.size(); i++) {
            DrawOriginResult d = (DrawOriginResult) dorV.get(i);
            logger.info("dorv result:" + d.getLinekey() + "," + d.getRowkey()
                    + "," + d.getValue());
        }

        endTime =System.currentTimeMillis();
        logger.info("从数据库获取小时客流原始分时客流时间"+((endTime-startTime)/1000)+"秒");

        //获取图形序列，线路或进出站的名称,封装对象为LineTitle
/*
        if (util.getNodeFlag(clickNode) == AppConstant.FLAG_ALLLINE) {
        lineTitleV = (Vector) lineDao.getLineList();
        }
         */
         startTime =System.currentTimeMillis();

        if (util.getNodeFlag(clickNode) == AppConstant.FLAG_ALLLINE || util.getNodeFlag(clickNode) == AppConstant.FLAG_SINGLELINE
                || util.getNodeFlag(clickNode) == AppConstant.FLAG_STATION) {
            lineTitleV = this.getInOutTitle();
        }
        for (int i = 0; i < lineTitleV.size(); i++) {
            LineTitle d = (LineTitle) lineTitleV.get(i);
            logger.info("lineCode result:" + d.getKey() + ","
                    + d.getTitle());
        }
        //获取图形的横坐标，各时刻，使用RowTitle封装
        drV = this.getHoursConvertedResult(dorV, lineTitleV);

        endTime =System.currentTimeMillis();
        logger.info("小时客流原始分时客流数据整理时间"+((endTime-startTime)/1000)+"秒");
        return drV;

    }

    /**
     * 从数据库获取票卡类型客流
     */
    public Map<String, Vector> getCardTypeTraffic(String clickNode, String currentTrafficDate) throws Exception {
        Map<String, Vector> dorV = new HashMap<String, Vector>();
        TrafficCardTypeDao trafficDao = new TrafficCardTypeDao();

        long startTime = System.currentTimeMillis();
        long endTime;

        //获取小时客流原始数据
        dorV = trafficDao.getTrafficForCardType(clickNode, currentTrafficDate);

        endTime =System.currentTimeMillis();
        logger.info("从数据库获取票卡类型客流时间"+((endTime-startTime)/1000)+"秒");

        return dorV;
    }

    public Vector getLineStationSpanMinTraffic(
            String clickNode, String currentTrafficDate) throws Exception {
        Vector drV = null;
        Vector dorV = new Vector();
        Vector lineTitleV = new Vector();
        TrafficMinDao trafficDao = new TrafficMinDao();
        LineDao lineDao = new LineDao();
        PubUtil util = new PubUtil();
        //获取分钟客流原始数据
        dorV = (Vector) trafficDao.getLineStationSpanMinTraffic(clickNode, currentTrafficDate);
        for (int i = 0; i < dorV.size(); i++) {
            DrawOriginResult d = (DrawOriginResult) dorV.get(i);
            logger.info("dorv result:" + d.getLinekey() + "," + d.getRowkey()
                    + "," + d.getValue());
        }
        //获取图形序列，线路或进出站的名称,封装对象为LineTitle
        if (util.getNodeFlag(clickNode) == AppConstant.FLAG_ALLLINE) {
            lineTitleV = (Vector) lineDao.getLineList();
        }
        if (util.getNodeFlag(clickNode) == AppConstant.FLAG_SINGLELINE
                || util.getNodeFlag(clickNode) == AppConstant.FLAG_STATION) {
            lineTitleV = this.getInOutTitle();
        }
        for (int i = 0; i < lineTitleV.size(); i++) {
            LineTitle d = (LineTitle) lineTitleV.get(i);
            logger.info("lineCode result:" + d.getKey() + ","
                    + d.getTitle());
        }
        //获取图形的横坐标，各分钟，使用RowTitle封装
        drV = this.getMinsConvertedResult(dorV, lineTitleV);
        return drV;

    }

    private Vector lineTitleToCHN(Vector lineTitleV) {
        Vector result = new Vector();
        int size = lineTitleV.size();
        for (int i = 0; i < size; i++) {
            LineTitle lt = (LineTitle) lineTitleV.get(i);
            LineTitle newLt = new LineTitle();
            newLt.setKey(lt.getKey());
            newLt.setTitle(lt.getTitle());
            result.add(newLt);
        }
        return result;
    }

    private Vector getHoursConvertedResult(Vector drawOriginResultV, Vector lineTitleV) {
        //图形横坐标
        Vector rowTitleV = getItemsTitle(Hours, HoursKey, 25);
        //获取绘图数据，横坐标、绘图数据封装在DrawResult对象
        //返回结果第一个是横坐标封装对象、后面是绘图数据对象，对象按序列排序，如序列是线路，则数据是1号线、2号线.....
        //每个数据对象已包含序列的完整数据，即每个序列对应的所有横坐标数据
        return this.getConvertedResult(Hour, drawOriginResultV, lineTitleToCHN(lineTitleV), rowTitleV);
    }

    private Vector getMinsConvertedResult(Vector drawOriginResultV, Vector lineTitleV) {
        Vector rowTitleV = getItemsTitle(AppUtil.MinTitlesFormat, 24);
        return this.getConvertedResult(AppConstant.SCREEN_CAPTION_ROW_MIN, drawOriginResultV, lineTitleToCHN(lineTitleV), rowTitleV);
    }

    private Vector getItemsTitle(String items[], int count) {
        Vector result = new Vector();

        int size = items.length;
        for (int i = 0; i < count && i < size; i++) {
            RowTitle rt = new RowTitle();
            rt.setKey(i);
            rt.setTitle(items[i]);
            result.add(rt);
        }
        return result;
    }

    private Vector getItemsTitle(String items[], int itemsKey[], int count) {
        Vector result = new Vector();

        int size = items.length;
        for (int i = 0; i < count && i < size; i++) {
            RowTitle rt = new RowTitle();
            rt.setKey(itemsKey[i]);
            rt.setTitle(items[i]);
            result.add(rt);
        }
        return result;
    }

    public int getTitlesIndexForMin(String[] minTitles, String title) {
        int index = -1;
        for (int i = 0; i < minTitles.length; i++) {
            if (title.equals(minTitles[i])) {
                return i;
            }
        }
        return index;
    }

    /**
     * 根据查询时间取得5分钟横坐标标题
     * @param currentDate
     * @param mins
     * @return 
     */
    public String[] getTitlesForMin(String currentDate, String[] mins) {
        Date cur = DateHelper.getDateTimeFromString(currentDate);
        Date curPre = new Date(cur.getTime() - 3600000);
        String hour = DateHelper.getHour(cur);
        String hourPre = DateHelper.getHour(curPre);
        String minTitle;
        int size = mins.length;
        String[] minTitles = new String[24];

        for (int i = 0; i < size; i++) {
            minTitle = hourPre + mins[i];
            minTitles[i] = minTitle;
        }
        for (int i = 0; i < size; i++) {
            minTitle = hour + mins[i];
            minTitles[i + 12] = minTitle;
        }
        
        return minTitles;
    }

    public String[] getTitlesForMinFormated(String[] mins) {

        String[] minTitles = new String[24];
        int size = mins.length;

        for (int i = 0; i < size; i++) {
            minTitles[i] = this.formatMin(mins[i]);
        }

        return minTitles;
    }

    private String formatMin(String min) {
        String hour = min.substring(0, 2);
        String hm = min.substring(2);
        return hour + ":" + hm;
    }

    private Vector getInOutTitle() {
        Vector lineTitleV = new Vector();
        LineTitle lineTitle = new LineTitle();
        lineTitle.setKey(AppConstant.FLAG_IN);
        lineTitle.setTitle(AppConstant.SCREEN_CAPTION_IN);
        lineTitleV.add(lineTitle);
        LineTitle lineTitle1 = new LineTitle();
        lineTitle1.setKey(AppConstant.FLAG_OUT);
        lineTitle1.setTitle(AppConstant.SCREEN_CAPTION_OUT);
        lineTitleV.add(lineTitle1);
        return lineTitleV;

    }
    
    //设置横坐标的数据,数据使用DrawResult对象封装,对象中的元素(坐标值最大为32个)
    private void getConvertedResultForRowData(String title, Vector rowTitleV, Vector result) {

        //first line for row title
        DrawResult titleDr = new DrawResult();
        titleDr.setTitle(title);
        int row = rowTitleV.size();
        int j = 0;
        /***************************************设置横坐标的数据开始********************************************************/
        /**
         * 横坐标数据封装在DrawResult对象title取单位,result取各小时或各分钟
         */
        if (j < row) {
            titleDr.setResult01(((RowTitle) rowTitleV.get(j)).getTitle());
        } else {
            titleDr.setResult01("");
        }
        j++;
        if (j < row) {
            titleDr.setResult02(((RowTitle) rowTitleV.get(j)).getTitle());
        } else {
            titleDr.setResult02("");
        }
        j++;
        if (j < row) {
            titleDr.setResult03(((RowTitle) rowTitleV.get(j)).getTitle());
        } else {
            titleDr.setResult03("");
        }
        j++;
        if (j < row) {
            titleDr.setResult04(((RowTitle) rowTitleV.get(j)).getTitle());
        } else {
            titleDr.setResult04("");
        }
        j++;
        if (j < row) {
            titleDr.setResult05(((RowTitle) rowTitleV.get(j)).getTitle());
        } else {
            titleDr.setResult05("");
        }
        j++;
        if (j < row) {
            titleDr.setResult06(((RowTitle) rowTitleV.get(j)).getTitle());
        } else {
            titleDr.setResult06("");
        }
        j++;
        if (j < row) {
            titleDr.setResult07(((RowTitle) rowTitleV.get(j)).getTitle());
        } else {
            titleDr.setResult07("");
        }
        j++;
        if (j < row) {
            titleDr.setResult08(((RowTitle) rowTitleV.get(j)).getTitle());
        } else {
            titleDr.setResult08("");
        }
        j++;
        if (j < row) {
            titleDr.setResult09(((RowTitle) rowTitleV.get(j)).getTitle());
        } else {
            titleDr.setResult09("");
        }
        j++;
        if (j < row) {
            titleDr.setResult10(((RowTitle) rowTitleV.get(j)).getTitle());
        } else {
            titleDr.setResult10("");
        }
        j++;
        if (j < row) {
            titleDr.setResult11(((RowTitle) rowTitleV.get(j)).getTitle());
        } else {
            titleDr.setResult11("");
        }
        j++;
        if (j < row) {
            titleDr.setResult12(((RowTitle) rowTitleV.get(j)).getTitle());
        } else {
            titleDr.setResult12("");
        }
        j++;
        if (j < row) {
            titleDr.setResult13(((RowTitle) rowTitleV.get(j)).getTitle());
        } else {
            titleDr.setResult13("");
        }
        j++;
        if (j < row) {
            titleDr.setResult14(((RowTitle) rowTitleV.get(j)).getTitle());
        } else {
            titleDr.setResult14("");
        }
        j++;
        if (j < row) {
            titleDr.setResult15(((RowTitle) rowTitleV.get(j)).getTitle());
        } else {
            titleDr.setResult15("");
        }
        j++;
        if (j < row) {
            titleDr.setResult16(((RowTitle) rowTitleV.get(j)).getTitle());
        } else {
            titleDr.setResult16("");
        }
        j++;
        if (j < row) {
            titleDr.setResult17(((RowTitle) rowTitleV.get(j)).getTitle());
        } else {
            titleDr.setResult17("");
        }
        j++;
        if (j < row) {
            titleDr.setResult18(((RowTitle) rowTitleV.get(j)).getTitle());
        } else {
            titleDr.setResult18("");
        }
        j++;
        if (j < row) {
            titleDr.setResult19(((RowTitle) rowTitleV.get(j)).getTitle());
        } else {
            titleDr.setResult19("");
        }
        j++;
        if (j < row) {
            titleDr.setResult20(((RowTitle) rowTitleV.get(j)).getTitle());
        } else {
            titleDr.setResult20("");
        }
        j++;
        if (j < row) {
            titleDr.setResult21(((RowTitle) rowTitleV.get(j)).getTitle());
        } else {
            titleDr.setResult21("");
        }
        j++;
        if (j < row) {
            titleDr.setResult22(((RowTitle) rowTitleV.get(j)).getTitle());
        } else {
            titleDr.setResult22("");
        }
        j++;
        if (j < row) {
            titleDr.setResult23(((RowTitle) rowTitleV.get(j)).getTitle());
        } else {
            titleDr.setResult23("");
        }
        j++;
        if (j < row) {
            titleDr.setResult24(((RowTitle) rowTitleV.get(j)).getTitle());
        } else {
            titleDr.setResult24("");
        }
        j++;
        if (j < row) {
            titleDr.setResult25(((RowTitle) rowTitleV.get(j)).getTitle());
        } else {
            titleDr.setResult25("");
        }
        j++;
        if (j < row) {
            titleDr.setResult26(((RowTitle) rowTitleV.get(j)).getTitle());
        } else {
            titleDr.setResult26("");
        }
        j++;
        if (j < row) {
            titleDr.setResult27(((RowTitle) rowTitleV.get(j)).getTitle());
        } else {
            titleDr.setResult27("");
        }
        j++;
        if (j < row) {
            titleDr.setResult28(((RowTitle) rowTitleV.get(j)).getTitle());
        } else {
            titleDr.setResult28("");
        }
        j++;
        if (j < row) {
            titleDr.setResult29(((RowTitle) rowTitleV.get(j)).getTitle());
        } else {
            titleDr.setResult29("");
        }
        j++;
        if (j < row) {
            titleDr.setResult30(((RowTitle) rowTitleV.get(j)).getTitle());
        } else {
            titleDr.setResult30("");
        }
        j++;
        if (j < row) {
            titleDr.setResult31(((RowTitle) rowTitleV.get(j)).getTitle());
        } else {
            titleDr.setResult31("");
        }
        j++;
        if (j < row) {
            titleDr.setResult32(((RowTitle) rowTitleV.get(j)).getTitle());
        } else {
            titleDr.setResult32("");
        }
        j++;
        result.add(titleDr);

        /***************************************设置横坐标的数据结束********************************************************/
    }
    //横坐标集合rowTitleV,序列集合lineTitleV,响应元素对应的值,最多32个值

    /**
     * 进站-98线路
     * 出站-99线路
     * @param lineTitle
     * @return 
     */
    private int getLineId(LineTitle lineTitle) {
        String lineKey = lineTitle.getKey();
        if (lineKey.equals(AppConstant.FLAG_IN)) {
            return AppConstant.IN_LINE_ID;
        }
        if (lineKey.equals(AppConstant.FLAG_OUT)) {
            return AppConstant.OUT_LINE_ID;
        }
        return Integer.parseInt(lineKey);
    }

    private void getConvertedResultForDrawData(Vector drawOriginResultV, Vector lineTitleV, Vector rowTitleV, Vector result, int i, int row) {

        DrawResult dr = new DrawResult();
        String lineTitle = ((LineTitle) lineTitleV.get(i)).getTitle();
        int lineId = this.getLineId((LineTitle) lineTitleV.get(i));
        dr.setTitle(lineTitle);//序列
        dr.setLineId(lineId);//设置线路ID,进站设为98 出站设为99

        //find result from origin result
        String lineKey = ((LineTitle) lineTitleV.get(i)).getKey();
        int k = 0;
        if (k < row) {
            int rowKey = ((RowTitle) rowTitleV.get(k)).getKey();
            dr.setResult01("" + getValueFromOrigin(drawOriginResultV, lineKey, rowKey));
        } else {
            dr.setResult01("");
        }
        k++;
        if (k < row) {
            int rowKey = ((RowTitle) rowTitleV.get(k)).getKey();
            dr.setResult02("" + getValueFromOrigin(drawOriginResultV, lineKey, rowKey));
        } else {
            dr.setResult02("");
        }
        k++;
        if (k < row) {
            int rowKey = ((RowTitle) rowTitleV.get(k)).getKey();
            dr.setResult03("" + getValueFromOrigin(drawOriginResultV, lineKey, rowKey));
        } else {
            dr.setResult03("");
        }
        k++;
        if (k < row) {
            int rowKey = ((RowTitle) rowTitleV.get(k)).getKey();
            dr.setResult04("" + getValueFromOrigin(drawOriginResultV, lineKey, rowKey));
        } else {
            dr.setResult04("");
        }
        k++;
        if (k < row) {
            int rowKey = ((RowTitle) rowTitleV.get(k)).getKey();
            dr.setResult05("" + getValueFromOrigin(drawOriginResultV, lineKey, rowKey));
        } else {
            dr.setResult05("");
        }
        k++;
        if (k < row) {
            int rowKey = ((RowTitle) rowTitleV.get(k)).getKey();
            dr.setResult06("" + getValueFromOrigin(drawOriginResultV, lineKey, rowKey));
        } else {
            dr.setResult06("");
        }
        k++;
        if (k < row) {
            int rowKey = ((RowTitle) rowTitleV.get(k)).getKey();
            dr.setResult07("" + getValueFromOrigin(drawOriginResultV, lineKey, rowKey));
        } else {
            dr.setResult07("");
        }
        k++;
        if (k < row) {
            int rowKey = ((RowTitle) rowTitleV.get(k)).getKey();
            dr.setResult08("" + getValueFromOrigin(drawOriginResultV, lineKey, rowKey));
        } else {
            dr.setResult08("");
        }
        k++;
        if (k < row) {
            int rowKey = ((RowTitle) rowTitleV.get(k)).getKey();
            dr.setResult09("" + getValueFromOrigin(drawOriginResultV, lineKey, rowKey));
        } else {
            dr.setResult09("");
        }
        k++;
        if (k < row) {
            int rowKey = ((RowTitle) rowTitleV.get(k)).getKey();
            dr.setResult10("" + getValueFromOrigin(drawOriginResultV, lineKey, rowKey));
        } else {
            dr.setResult10("");
        }
        k++;
        if (k < row) {
            int rowKey = ((RowTitle) rowTitleV.get(k)).getKey();
            dr.setResult11("" + getValueFromOrigin(drawOriginResultV, lineKey, rowKey));
        } else {
            dr.setResult11("");
        }
        k++;
        if (k < row) {
            int rowKey = ((RowTitle) rowTitleV.get(k)).getKey();
            dr.setResult12("" + getValueFromOrigin(drawOriginResultV, lineKey, rowKey));
        } else {
            dr.setResult12("");
        }
        k++;
        if (k < row) {
            int rowKey = ((RowTitle) rowTitleV.get(k)).getKey();
            dr.setResult13("" + getValueFromOrigin(drawOriginResultV, lineKey, rowKey));
        } else {
            dr.setResult13("");
        }
        k++;
        if (k < row) {
            int rowKey = ((RowTitle) rowTitleV.get(k)).getKey();
            dr.setResult14("" + getValueFromOrigin(drawOriginResultV, lineKey, rowKey));
        } else {
            dr.setResult14("");
        }
        k++;
        if (k < row) {
            int rowKey = ((RowTitle) rowTitleV.get(k)).getKey();
            dr.setResult15("" + getValueFromOrigin(drawOriginResultV, lineKey, rowKey));
        } else {
            dr.setResult15("");
        }
        k++;
        if (k < row) {
            int rowKey = ((RowTitle) rowTitleV.get(k)).getKey();
            dr.setResult16("" + getValueFromOrigin(drawOriginResultV, lineKey, rowKey));
        } else {
            dr.setResult16("");
        }
        k++;
        if (k < row) {
            int rowKey = ((RowTitle) rowTitleV.get(k)).getKey();
            dr.setResult17("" + getValueFromOrigin(drawOriginResultV, lineKey, rowKey));
        } else {
            dr.setResult17("");
        }
        k++;
        if (k < row) {
            int rowKey = ((RowTitle) rowTitleV.get(k)).getKey();
            dr.setResult18("" + getValueFromOrigin(drawOriginResultV, lineKey, rowKey));
        } else {
            dr.setResult18("");
        }
        k++;
        if (k < row) {
            int rowKey = ((RowTitle) rowTitleV.get(k)).getKey();
            dr.setResult19("" + getValueFromOrigin(drawOriginResultV, lineKey, rowKey));
        } else {
            dr.setResult19("");
        }
        k++;
        if (k < row) {
            int rowKey = ((RowTitle) rowTitleV.get(k)).getKey();
            dr.setResult20("" + getValueFromOrigin(drawOriginResultV, lineKey, rowKey));
        } else {
            dr.setResult20("");
        }
        k++;
        if (k < row) {
            int rowKey = ((RowTitle) rowTitleV.get(k)).getKey();
            dr.setResult21("" + getValueFromOrigin(drawOriginResultV, lineKey, rowKey));
        } else {
            dr.setResult21("");
        }
        k++;
        if (k < row) {
            int rowKey = ((RowTitle) rowTitleV.get(k)).getKey();
            dr.setResult22("" + getValueFromOrigin(drawOriginResultV, lineKey, rowKey));
        } else {
            dr.setResult22("");
        }
        k++;
        if (k < row) {
            int rowKey = ((RowTitle) rowTitleV.get(k)).getKey();
            dr.setResult23("" + getValueFromOrigin(drawOriginResultV, lineKey, rowKey));
        } else {
            dr.setResult23("");
        }
        k++;
        if (k < row) {
            int rowKey = ((RowTitle) rowTitleV.get(k)).getKey();
            dr.setResult24("" + getValueFromOrigin(drawOriginResultV, lineKey, rowKey));
        } else {
            dr.setResult24("");
        }
        k++;
        if (k < row) {
            int rowKey = ((RowTitle) rowTitleV.get(k)).getKey();
            dr.setResult25("" + getValueFromOrigin(drawOriginResultV, lineKey, rowKey));
        } else {
            dr.setResult25("");
        }
        k++;
        if (k < row) {
            int rowKey = ((RowTitle) rowTitleV.get(k)).getKey();
            dr.setResult26("" + getValueFromOrigin(drawOriginResultV, lineKey, rowKey));
        } else {
            dr.setResult26("");
        }
        k++;
        if (k < row) {
            int rowKey = ((RowTitle) rowTitleV.get(k)).getKey();
            dr.setResult27("" + getValueFromOrigin(drawOriginResultV, lineKey, rowKey));
        } else {
            dr.setResult27("");
        }
        k++;
        if (k < row) {
            int rowKey = ((RowTitle) rowTitleV.get(k)).getKey();
            dr.setResult28("" + getValueFromOrigin(drawOriginResultV, lineKey, rowKey));
        } else {
            dr.setResult28("");
        }
        k++;
        if (k < row) {
            int rowKey = ((RowTitle) rowTitleV.get(k)).getKey();
            dr.setResult29("" + getValueFromOrigin(drawOriginResultV, lineKey, rowKey));
        } else {
            dr.setResult29("");
        }
        k++;
        if (k < row) {
            int rowKey = ((RowTitle) rowTitleV.get(k)).getKey();
            dr.setResult30("" + getValueFromOrigin(drawOriginResultV, lineKey, rowKey));
        } else {
            dr.setResult30("");
        }
        k++;
        if (k < row) {
            int rowKey = ((RowTitle) rowTitleV.get(k)).getKey();
            dr.setResult31("" + getValueFromOrigin(drawOriginResultV, lineKey, rowKey));
        } else {
            dr.setResult31("");
        }
        k++;
        if (k < row) {
            int rowKey = ((RowTitle) rowTitleV.get(k)).getKey();
            dr.setResult32("" + getValueFromOrigin(drawOriginResultV, lineKey, rowKey));
        } else {
            dr.setResult32("");
        }
        k++;
        if (this.isValidResult(dr)) {
            result.add(dr);
        }

    }

    private boolean isValidData(String data) {
        if (data == null || data.length() == 0 || data.trim().equals("0") || data.trim().equals("0.0")) {
            return false;
        }
        return true;
    }

    private boolean isValidResult(DrawResult dr) {
        boolean result = this.isValidData(dr.getResult01())
                | this.isValidData(dr.getResult02())
                | this.isValidData(dr.getResult03())
                | this.isValidData(dr.getResult04())
                | this.isValidData(dr.getResult05())
                | this.isValidData(dr.getResult06())
                | this.isValidData(dr.getResult07())
                | this.isValidData(dr.getResult08())
                | this.isValidData(dr.getResult09())
                | this.isValidData(dr.getResult10())
                | this.isValidData(dr.getResult11())
                | this.isValidData(dr.getResult12())
                | this.isValidData(dr.getResult13())
                | this.isValidData(dr.getResult14())
                | this.isValidData(dr.getResult15())
                | this.isValidData(dr.getResult16())
                | this.isValidData(dr.getResult17())
                | this.isValidData(dr.getResult18())
                | this.isValidData(dr.getResult19())
                | this.isValidData(dr.getResult20())
                | this.isValidData(dr.getResult21())
                | this.isValidData(dr.getResult22())
                | this.isValidData(dr.getResult23())
                | this.isValidData(dr.getResult24())
                | this.isValidData(dr.getResult25())
                | this.isValidData(dr.getResult26())
                | this.isValidData(dr.getResult27())
                | this.isValidData(dr.getResult28())
                | this.isValidData(dr.getResult29())
                | this.isValidData(dr.getResult30())
                | this.isValidData(dr.getResult31())
                | this.isValidData(dr.getResult32());

        return result;
    }

    private Vector getConvertedResult(String title, Vector drawOriginResultV, Vector lineTitleV, Vector rowTitleV) {
        Vector result = new Vector();
        //设置横坐标的显示数据，将rowTitleV中的对象转换成drawResult对象，并放置在result对象的第一个
        this.getConvertedResultForRowData(title, rowTitleV, result);
        int row = rowTitleV.size();

        /***************************************设置绘图的数据开始********************************************************/
        /*
         * 绘图的基本数据是每个横坐标单位的每个序列对应的数值，如横坐标是小时、序列是线路，则为每各小时的各线路客流
         * 如横坐标是小时、序列是进出，则为每各小时的进出客流
         * DrawResult对象中，title取序列名称、result取序列的各坐标客流数值
         */
        int line = lineTitleV.size();//图形序列
        //设置绘图数据,使用DrawResult对象封装,数据对应横坐标值及序列值如小时各线路客流,数据表示某个小时各线路的数值
        for (int i = 0; i < line; i++) {
            this.getConvertedResultForDrawData(drawOriginResultV, lineTitleV, rowTitleV, result, i, row);
        }

        return result;
    }

    private double getValueFromOrigin(Vector orginResultV, String lineKey, int rowKey) {
        double value = 0;
        int size = orginResultV.size();

        for (int i = 0; i < size; i++) {
            DrawOriginResult dor = (DrawOriginResult) orginResultV.get(i);
            if (dor.getLinekey() == null || lineKey == null) {
                continue;
            }
            if ((dor.getLinekey().trim()).equals(lineKey.trim()) && dor.getRowkey() == rowKey) {
                value = dor.getValue();
                break;
            }
        }
        //System.out.println("lineKey=" + lineKey + " rowKey=" + rowKey + " value=" + value);
        return value;
    }

    
    /**
     * 选择线网节点  
     * 表格显示线网各线路进出站总客流
     */
    public void addTableForNet(String clickNode, String currentTrafficDate, JScrollPane jScrollPannel) throws Exception {
        //获取表格显示数据，线网节点时：各线路进出站总客流
        Vector traffics = this.getTableDataForNet(clickNode, currentTrafficDate);
        //表头
        Vector columnNames = new Vector();
        columnNames.add("线路");
        columnNames.add("进站(人次)");
        columnNames.add("出站(人次)");
        Vector dataV = new Vector();
        //表格数据
        this.getTableDataForNet(dataV, traffics);
        //生成表格
        this.genTable(columnNames, dataV, jScrollPannel);
    }

    /**
     * 选择车站节点 表格显示车站进出站总客流
     * @throws Exception 
     */
    public void addTableForStation(String clickNode, String currentTrafficDate, JScrollPane jScrollPannel) throws Exception {
        Vector traffics = this.getTableDataForNet(clickNode, currentTrafficDate);
        Vector columnNames = new Vector();
        columnNames.add(AppConstant.SCREEN_CAPTION_IN + "(" + AppConstant.SCREEN_CAPTION_COLUMN_PERSON + ")");
        columnNames.add(AppConstant.SCREEN_CAPTION_OUT + "(" + AppConstant.SCREEN_CAPTION_COLUMN_PERSON + ")");

        Vector dataV = new Vector();
        this.getTableDataForStation(dataV, traffics);
        this.genTable(columnNames, dataV, jScrollPannel);

    }

    public void addTableForStationTotal(String clickNode, String currentTrafficDate, JScrollPane jScrollPannel) throws Exception {
        Vector traffics = this.getTableDataForNetTotal(clickNode, currentTrafficDate);
        Vector columnNames = new Vector();
        columnNames.add(AppConstant.SCREEN_CAPTION_IN + "(" + AppConstant.SCREEN_CAPTION_COLUMN_PERSON + ")");
        columnNames.add(AppConstant.SCREEN_CAPTION_OUT + "(" + AppConstant.SCREEN_CAPTION_COLUMN_PERSON + ")");

        Vector dataV = new Vector();
        this.getTableDataForStation(dataV, traffics);
        this.genTable(columnNames, dataV, jScrollPannel);

    }

    private void getTableDataForNet(Vector dataV, Vector traffics) {
        ViewVo vo;
        Vector data;
        for (int i = 0; i < traffics.size(); i++) {
            vo = (ViewVo) traffics.get(i);
            data = new Vector();
            data.add(vo.getLine_name());
            data.add(this.getValueIn(vo));
            data.add(this.getValueOut(vo));
            dataV.add(data);
        }
    }

    private String getValueIn(ViewVo vo) {
        if (vo.isIsTotal()) {
            return "[" + vo.getTraffic_in() + "]";
        }
        return vo.getTraffic_in();
    }

    private String getValueOut(ViewVo vo) {
        if (vo.isIsTotal()) {
            return "[" + vo.getTraffic_out() + "]";
        }
        return vo.getTraffic_out();
    }

    /**
     * 组合车站客流数据
     * @param dataV
     * @param traffics 
     */
    private void getTableDataForStation(Vector dataV, Vector traffics) {
        ViewVo vo;
        Vector data;
        for (int i = 0; i < traffics.size(); i++) {
            vo = (ViewVo) traffics.get(i);
            data = new Vector();
            data.add(vo.getTraffic_in());
            data.add(vo.getTraffic_out());
            dataV.add(data);
        }
    }

    /**
     * 选择线路节点  表格显示线路各车站进出站总客流
     * @throws Exception 
     */
    public void addTableForLineStation(String lineCode, String currentTrafficDate, JScrollPane jScrollPannel) throws Exception {
        //获取线路各车站进出站客流汇总数据
        ScreenStationsTrafficVO sstv = this.getTableDataForLineStation(lineCode, currentTrafficDate);
        int colNum = this.getTableColumnNumber(sstv);
        //动态生成表头，表头最多分成3大列，每大列包括（站明、进站、出站）
        Vector columnNames = new Vector();
        int i = 0;
        for (i = 0; i < colNum / 3; i++) {
            columnNames.add("站点");
            columnNames.add("进站(人次)");
            columnNames.add("出站(人次)");
        }
        Vector dataV = new Vector();

        i = colNum / 3;
        //动态生成表数据，表数据最多分成3大列，每大列包括（站名、进站、出站）
        this.getTableDataForColumnFormated(sstv, i, dataV);
        //生成表格
        this.genTable(columnNames, dataV, jScrollPannel);

    }
    
    /**
     * 票卡类型客流表格
     */
    public void addTableForCard(Vector dataSub, JScrollPane jScrollPannel){
        int colNum = (dataSub.size()+11)/11;
        //动态生成表头，表头最多分成3大列，每大列包括（票卡类型、进站、出站）
        Vector columnNames = new Vector();
        int i = 0;
        for (i = 0; i < colNum; i++) {
            columnNames.add("票卡类型");
            columnNames.add("进站(人次)");
            columnNames.add("出站(人次)");
        }
        Vector dataV = new Vector();

        //动态生成表数据，表数据最多分成3大列，每大列包括（票卡类型、进站、出站）
        this.getTableDataForCardFormated(dataSub, dataV, colNum);
        //生成表格
        this.genTable(columnNames, dataV, jScrollPannel);

    }
    
    /**
     * 组合票卡子类型数据 同一类型组合出站进站数据
     * @param map
     * @return 
     */
    private Vector setTableDataCard(Map<String, Vector> map) {
        Vector dataSubIn = new Vector();
        Vector dataSubOut = new Vector();
        Vector dataSub = new Vector();
        dataSubIn = map.get(AppConstant.CARD_IN_SUB);
        dataSubOut = map.get(AppConstant.CARD_OUT_SUB);
        if(dataSubIn.size() > dataSubOut.size()){
            setTrafficInOutCard(dataSub,dataSubIn,dataSubOut,AppConstant.CARD_IN_SUB);
        }else{
            setTrafficInOutCard(dataSub,dataSubOut,dataSubIn,AppConstant.CARD_OUT_SUB);
        }
        return dataSub;
    }
    
    /**
     * 票卡类型集 更新客流进出站数量
     */
    private void setTrafficInOutCard(Vector dataSub, Vector dataMax, Vector dataMin, String flag) {
        Vector dataMinT = new Vector();
        dataMinT.addAll(dataMin);
        for(int i=0; i<dataMax.size(); i++){
            ViewVo voi = new ViewVo();
            voi = (ViewVo) dataMax.get(i);
            for(int j=dataMinT.size()-1; j>=0; j--){
                ViewVo voo = new ViewVo();
                voo = (ViewVo) dataMinT.get(j);
                //添加匹配的数据到结果集
                if(!voi.getSubCardID().isEmpty() && !voi.getMainCardID().isEmpty() &&
                        voi.getSubCardID().equals(voo.getSubCardID()) && voi.getMainCardID().equals(voo.getMainCardID())){
                    if(flag.equals(AppConstant.CARD_IN_SUB)){
                        voi.setTraffic_out(getValue(voo.getTraffic_out()));
                    }else{
                        voi.setTraffic_in(getValue(voo.getTraffic_in()));
                    }
                    //删除已经匹配的数据
                    dataMinT.remove(j);
                }
            }
            //空值设0
            voi.setTraffic_out(getValue(voi.getTraffic_out()));
            voi.setTraffic_in(getValue(voi.getTraffic_in()));
            dataSub.add(voi);
        }
        
        //未匹配值添加
        for(int k=dataMinT.size()-1; k>=0; k--){
            ViewVo vok = new ViewVo();
            vok = (ViewVo) dataMinT.get(k);
            //添加匹配的数据到结果集
            vok.setTraffic_out(getValue(vok.getTraffic_out()));
            vok.setTraffic_in(getValue(vok.getTraffic_in()));
            dataSub.add(vok);
        }
        
    }
    
    /**
     * 空值赋0
     * @param value
     * @return 
     */
    private String getValue(String value){
        return value.equals("")?"0":value;
    }

    private String getValueIn(ScreenStationTrafficVO vo) {
        if (vo.getIsTotal()) {
            return "[" + vo.getTrafficIn() + "]";
        }
        return vo.getTrafficIn();
    }

    private String getValueOut(ScreenStationTrafficVO vo) {
        if (vo.getIsTotal()) {
            return "[" + vo.getTrafficOut() + "]";
        }
        return vo.getTrafficOut();
    }


    /**
     * 动态生成表数据，表数据最多分成3大列，每大列包括（站名、进站、出站）
     */
    private void getTableDataForColumnFormated(ScreenStationsTrafficVO sstv, int i, Vector dataV) {
        Vector data = new Vector();
        data.add(sstv.getStationTraffic0().getStationText());
        data.add(this.getValueIn(sstv.getStationTraffic0()));
        data.add(this.getValueOut(sstv.getStationTraffic0()));
        if (i >= 2) {
            data.add(sstv.getStationTraffic11().getStationText());
            data.add(this.getValueIn(sstv.getStationTraffic11()));
            data.add(this.getValueOut(sstv.getStationTraffic11()));
        }
        if (i >= 3) {
            data.add(sstv.getStationTraffic22().getStationText());
            data.add(this.getValueIn(sstv.getStationTraffic22()));
            data.add(this.getValueOut(sstv.getStationTraffic22()));
        }

        dataV.add(data);
        data = new Vector();

        data.add(sstv.getStationTraffic1().getStationText());
        data.add(this.getValueIn(sstv.getStationTraffic1()));
        data.add(this.getValueOut(sstv.getStationTraffic1()));
        if (i >= 2) {
            data.add(sstv.getStationTraffic12().getStationText());
            data.add(this.getValueIn(sstv.getStationTraffic12()));
            data.add(this.getValueOut(sstv.getStationTraffic12()));
        }
        if (i >= 3) {
            data.add(sstv.getStationTraffic23().getStationText());
            data.add(this.getValueIn(sstv.getStationTraffic23()));
            data.add(this.getValueOut(sstv.getStationTraffic23()));
        }

        dataV.add(data);
        data = new Vector();

        data.add(sstv.getStationTraffic2().getStationText());
        data.add(this.getValueIn(sstv.getStationTraffic2()));
        data.add(this.getValueOut(sstv.getStationTraffic2()));
        if (i >= 2) {
            data.add(sstv.getStationTraffic13().getStationText());
            data.add(this.getValueIn(sstv.getStationTraffic13()));
            data.add(this.getValueOut(sstv.getStationTraffic13()));
        }
        if (i >= 3) {
            data.add(sstv.getStationTraffic24().getStationText());
            data.add(this.getValueIn(sstv.getStationTraffic24()));
            data.add(this.getValueOut(sstv.getStationTraffic24()));
        }

        dataV.add(data);
        data = new Vector();

        data.add(sstv.getStationTraffic3().getStationText());
        data.add(this.getValueIn(sstv.getStationTraffic3()));
        data.add(this.getValueOut(sstv.getStationTraffic3()));
        if (i >= 2) {
            data.add(sstv.getStationTraffic14().getStationText());
            data.add(this.getValueIn(sstv.getStationTraffic14()));
            data.add(this.getValueOut(sstv.getStationTraffic14()));
        }
        if (i >= 3) {
            data.add(sstv.getStationTraffic25().getStationText());
            data.add(this.getValueIn(sstv.getStationTraffic25()));
            data.add(this.getValueOut(sstv.getStationTraffic25()));
        }

        dataV.add(data);
        data = new Vector();


        data.add(sstv.getStationTraffic4().getStationText());
        data.add(this.getValueIn(sstv.getStationTraffic4()));
        data.add(this.getValueOut(sstv.getStationTraffic4()));
        if (i >= 2) {
            data.add(sstv.getStationTraffic15().getStationText());
            data.add(this.getValueIn(sstv.getStationTraffic15()));
            data.add(this.getValueOut(sstv.getStationTraffic15()));
        }
        if (i >= 3) {
            data.add(sstv.getStationTraffic26().getStationText());
            data.add(this.getValueIn(sstv.getStationTraffic26()));
            data.add(this.getValueOut(sstv.getStationTraffic26()));
        }

        dataV.add(data);
        data = new Vector();


        data.add(sstv.getStationTraffic5().getStationText());
        data.add(this.getValueIn(sstv.getStationTraffic5()));
        data.add(this.getValueOut(sstv.getStationTraffic5()));
        if (i >= 2) {
            data.add(sstv.getStationTraffic16().getStationText());
            data.add(this.getValueIn(sstv.getStationTraffic16()));
            data.add(this.getValueOut(sstv.getStationTraffic16()));
        }
        if (i >= 3) {
            data.add(sstv.getStationTraffic27().getStationText());
            data.add(this.getValueIn(sstv.getStationTraffic27()));
            data.add(this.getValueOut(sstv.getStationTraffic27()));
        }

        dataV.add(data);
        data = new Vector();


        data.add(sstv.getStationTraffic6().getStationText());
        data.add(this.getValueIn(sstv.getStationTraffic6()));
        data.add(this.getValueOut(sstv.getStationTraffic6()));
        if (i >= 2) {
            data.add(sstv.getStationTraffic17().getStationText());
            data.add(this.getValueIn(sstv.getStationTraffic17()));
            data.add(this.getValueOut(sstv.getStationTraffic17()));
        }
        if (i >= 3) {
            data.add(sstv.getStationTraffic28().getStationText());
            data.add(this.getValueIn(sstv.getStationTraffic28()));
            data.add(this.getValueOut(sstv.getStationTraffic28()));
        }

        dataV.add(data);
        data = new Vector();


        data.add(sstv.getStationTraffic7().getStationText());
        data.add(this.getValueIn(sstv.getStationTraffic7()));
        data.add(this.getValueOut(sstv.getStationTraffic7()));
        if (i >= 2) {
            data.add(sstv.getStationTraffic18().getStationText());
            data.add(this.getValueIn(sstv.getStationTraffic18()));
            data.add(this.getValueOut(sstv.getStationTraffic18()));
        }
        if (i >= 3) {
            data.add(sstv.getStationTraffic29().getStationText());
            data.add(this.getValueIn(sstv.getStationTraffic29()));
            data.add(this.getValueOut(sstv.getStationTraffic29()));
        }

        dataV.add(data);
        data = new Vector();


        data.add(sstv.getStationTraffic8().getStationText());
        data.add(this.getValueIn(sstv.getStationTraffic8()));
        data.add(this.getValueOut(sstv.getStationTraffic8()));
        if (i >= 2) {
            data.add(sstv.getStationTraffic19().getStationText());
            data.add(this.getValueIn(sstv.getStationTraffic19()));
            data.add(this.getValueOut(sstv.getStationTraffic19()));
        }
        if (i >= 3) {
            data.add(sstv.getStationTraffic30().getStationText());
            data.add(this.getValueIn(sstv.getStationTraffic30()));
            data.add(this.getValueOut(sstv.getStationTraffic30()));
        }

        dataV.add(data);
        data = new Vector();


        data.add(sstv.getStationTraffic9().getStationText());
        data.add(this.getValueIn(sstv.getStationTraffic9()));
        data.add(this.getValueOut(sstv.getStationTraffic9()));
        if (i >= 2) {
            data.add(sstv.getStationTraffic20().getStationText());
            data.add(this.getValueIn(sstv.getStationTraffic20()));
            data.add(this.getValueOut(sstv.getStationTraffic20()));
        }
        if (i >= 3) {
            data.add(sstv.getStationTraffic31().getStationText());
            data.add(this.getValueIn(sstv.getStationTraffic31()));
            data.add(this.getValueOut(sstv.getStationTraffic31()));
        }

        dataV.add(data);
        data = new Vector();


        data.add(sstv.getStationTraffic10().getStationText());
        data.add(this.getValueIn(sstv.getStationTraffic10()));
        data.add(this.getValueOut(sstv.getStationTraffic10()));
        if (i >= 2) {
            data.add(sstv.getStationTraffic21().getStationText());
            data.add(this.getValueIn(sstv.getStationTraffic21()));
            data.add(this.getValueOut(sstv.getStationTraffic21()));
        }
        if (i >= 3) {
            data.add(sstv.getStationTraffic32().getStationText());
            data.add(this.getValueIn(sstv.getStationTraffic32()));
            data.add(this.getValueOut(sstv.getStationTraffic32()));
        }

        dataV.add(data);

    }

    /**
     * 动态生成表数据 票卡类型
     */
    private void getTableDataForCardFormated(Vector dataSub, Vector dataV, int num) {
        int totalOut = 0;
        int totalIn = 0;
        Vector dataT = new Vector();
        
        for(int i=0; i<dataSub.size(); i++){
            ViewVo vo = (ViewVo) dataSub.get(i);
            dataT.add(vo.getSubCardName());
            dataT.add(vo.getTraffic_in());
            dataT.add(vo.getTraffic_out());
            totalIn += Integer.valueOf(vo.getTraffic_in()); 
            totalOut += Integer.valueOf(vo.getTraffic_out()); 
        }
        dataT.add("[总计]");
        dataT.add("[" + totalIn + "]");
        dataT.add("[" + totalOut + "]");
        
        //按num 数量分组
        for(int j=0; j < (dataT.size()+num*3)/(num*3); j++){
            Vector data = new Vector();
            for(int k=0; k<num*3; k++){
                if((j*num*3 + k)<dataT.size()){
                    data.add(dataT.get(j*num*3 + k));
                }else{
                    data.add("");
                }
            }
            dataV.add(data);
        }
    }
    
    private int getTableColumnNumber(ScreenStationsTrafficVO sstv) {
        if (sstv == null) {
            return 0;
        }
        String stationCode = sstv.getStationTraffic0().getStationCode();
        int n = 0;
        if (stationCode != null && stationCode.length() != 0) {
            n += 3;
        }
        stationCode = sstv.getStationTraffic11().getStationCode();
        if (stationCode != null && stationCode.length() != 0) {
            n += 3;
        }
        stationCode = sstv.getStationTraffic22().getStationCode();
        if (stationCode != null && stationCode.length() != 0) {
            n += 3;
        }
        return n;
    }

    /**
     * 生成表格
     * @param columnNames
     * @param data
     * @param jScrollPannel 
     */
    private void genTable(Vector columnNames, Vector data, JScrollPane jScrollPannel) {
        //自定义表格，表格的数据居中
        // JTableData table = new JTableData(data, columnNames, new JTableDataCellRender());
        JTableModeData mode = new JTableModeData(columnNames, data);
        JTableData table = new JTableData(mode, new JTableDataCellRender());

        //表格的滚动框背景色
        jScrollPannel.getViewport().setBackground(AppConstant.COLOR_SHOW_BACKGROUND);
        //表格的垂直滚动条不显示
        jScrollPannel.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        //表格的背景色
        table.setBackground(AppConstant.COLOR_TABLE_DATA_BACKGROUND);
        //表格的前景色
        table.setForeground(Color.white);

        //表格数据字体
        table.setFont(AppConstant.FONT_TABLE_DATA);
        //表格行高
        table.setRowHeight(AppConstant.HIGH_TABLE_ROW);
        table.setCellEditor(null);
        
//        table.setAutoResizeMode(JTableData.AUTO_RESIZE_ALL_COLUMNS);
//        table.setAutoResizeMode(JTableData.AUTO_RESIZE_OFF);
//        Dimension d =  jScrollPannel.getSize();
//        table.setPreferredSize(new Dimension((int)(d.getWidth() - 50 ), (int)d.getHeight() - 200));

        JTableHeader head = table.getTableHeader();
        //表头背景色
        head.setBackground(AppConstant.COLOR_TABLE_HEAD_BACKGROUND);
        //表头前景色
        head.setForeground(Color.white);
        //表头字体
        head.setFont(AppConstant.FONT_TABLE_HEAD);

        //table.setBackground(new Color(0,101,153));
//        jScrollPannel.removeAll();
        jScrollPannel.setViewportView(table);
        //表格滚动框边界不设置
        jScrollPannel.setBorder(null);

    }

    /**
     * 线网各线路进出站总客流
     * @throws Exception 
     */
    private Vector getTableDataForNet(String clickNode, String currentTrafficDate) throws Exception {
        TrafficNetDao dao = new TrafficNetDao();
        //获取原始表数据
        Vector traffics = dao.getLineStationTraffic(clickNode, currentTrafficDate);
        //数据格式化成逗号分隔
        this.formatListIntegerValueForInOut(traffics);

        return traffics;
    }

    private Vector getTableDataForNetTotal(String clickNode, String currentTrafficDate) throws Exception {
        TrafficNetDao dao = new TrafficNetDao();
        Vector traffics = dao.getLineStationTrafficForMin(clickNode, currentTrafficDate);
        this.formatListIntegerValue(traffics);
        return traffics;

    }

    private Vector formatListIntegerValue(Vector list) {
        if (list == null || list.isEmpty()) {
            return list;
        }
        for (int i = 0; i < list.size(); i++) {
            ViewVo vo = (ViewVo) list.get(i);
            vo.setTraffic(this.formatInteger(vo.getTraffic()));

        }
        return list;

    }

    /**
     * 数据格式化成逗号分隔
     */
    private Vector formatListIntegerValueForInOut(Vector list) {
        if (list == null || list.isEmpty()) {
            return list;
        }
        for (int i = 0; i < list.size(); i++) {
            ViewVo vo = (ViewVo) list.get(i);
            vo.setTraffic_in(this.formatInteger(vo.getTraffic_in()));
            vo.setTraffic_out(this.formatInteger(vo.getTraffic_out()));
        }
        return list;
    }

    /**
     * 线路各车站进出站总客流
     * @throws Exception 
     */
    private ScreenStationsTrafficVO getTableDataForLineStation(String lineCode, String currentTrafficDate) throws Exception {
        ScreenStationsTrafficVO sstv = null;
        TrafficLineStationDao dao = new TrafficLineStationDao();
        //获取线路各车站原始数据
        Vector stationTraffics = dao.getLineAllStationInOutTraffic(lineCode, currentTrafficDate);
        //数据格式化成逗号分隔
        this.formatStationTrafficIntegerValue(stationTraffics);
        // 数据整理成列，最多分3列
        sstv = this.getAllStationsTraffic(stationTraffics);

        return sstv;
    }

    private String formatInteger(String value) {
        NumberFormat nf = NumberFormat.getIntegerInstance();
        nf.setGroupingUsed(true);
        int nValue = 0;
        try {
            nValue = Integer.parseInt(value);
            return nf.format(nValue);
        } catch (NumberFormatException e) {
            return "0";
        }

    }

    /**
     * 线路各车站进出站总客流
     * 数据格式化成逗号分隔
     * @param stationTraffics
     * @return 
     */
    private Vector formatStationTrafficIntegerValue(Vector stationTraffics) {
        if (stationTraffics == null || stationTraffics.isEmpty()) {
            return stationTraffics;
        }

        for (int i = 0; i < stationTraffics.size(); i++) {
            ScreenStationTrafficVO vo = (ScreenStationTrafficVO) stationTraffics.get(
                    i);
            vo.setTrafficIn(this.formatInteger(vo.getTrafficIn()));
            vo.setTrafficOut(this.formatInteger(vo.getTrafficOut()));

            vo.setIsEnglishVersion("0");
        }
        return stationTraffics;

    }

    /**
     * 数据整理成列数据，最多3列，每列最多12行数据，3列最多36行数据
     * @param stationTraffics
     * @return 
     */
    private Vector splitStationTraffics(Vector stationTraffics) {
        Vector firstColStations = new Vector();
        Vector secondColStations = new Vector();
        Vector thirdColStations = new Vector();
        Vector totalColStations = new Vector();
        int size = 0;
        int n = 0;
        int rest = 0;
        int i = 0;

        totalColStations.add(firstColStations);
        totalColStations.add(secondColStations);
        totalColStations.add(thirdColStations);

        if (stationTraffics == null || stationTraffics.isEmpty()) {
            return totalColStations;
        }

        size = stationTraffics.size();
        if (size <= 11) {
            firstColStations.addAll(stationTraffics);
        }
        if (size > 11 && size <= 22) {
            n = size / 2;
            rest = size % 2;
            if (rest != 0) {
                n = n + 1;
            }
            for (i = 0; i < n; i++) {
                firstColStations.add(stationTraffics.get(i));
            }
            for (i = n; i < size; i++) {
                secondColStations.add(stationTraffics.get(i));
            }

        }
        if (size > 22) {
            n = size / 3;
            rest = size % 3;
            if (rest != 0) {
                n = n + 1;
            }
            for (i = 0; i < n; i++) {
                firstColStations.add(stationTraffics.get(i));
            }
            for (i = n; i < 2 * n; i++) {
                secondColStations.add(stationTraffics.get(i));
            }
            for (i = 2 * n; i < size; i++) {
                thirdColStations.add(stationTraffics.get(i));
            }
        }

        return totalColStations;

    }

    /**
     * 线路各车站进出站总客流数据格式化成逗号分隔后
     * 数据整理成列，最多分3列
     * @param stationTraffics
     * @return 
     */
    private ScreenStationsTrafficVO getAllStationsTraffic(Vector stationTraffics) {
        if (stationTraffics == null) {
            return null;
        }
        int len = stationTraffics.size();
        ScreenStationsTrafficVO vo = new ScreenStationsTrafficVO();
        //数据整理成列数据，最多3列，每列最多12行数据，3列最多36行数据
        Vector totalStations = this.splitStationTraffics(stationTraffics);
        Vector firstColStations = (Vector) totalStations.get(0);
        Vector secondColStations = (Vector) totalStations.get(1);
        Vector thirdColStations = (Vector) totalStations.get(2);
        //分列后数据封装到一个对象，最多36个数据
        //第一列数据序号0－11 第二列数据12－23 第三列数据24－35
        if (!firstColStations.isEmpty()) {
            this.setFirstColData(vo, firstColStations);
        }
        if (!secondColStations.isEmpty()) {
            this.setSecondColData(vo, secondColStations);
        }
        if (!thirdColStations.isEmpty()) {
            this.setThirdColData(vo, thirdColStations);
        }

        return vo;
    }

    /**
     * 第一列数据序号0－11
     * @param vo
     * @param stationTrafficsFirst 
     */
    private void setFirstColData(ScreenStationsTrafficVO vo,
            Vector stationTrafficsFirst) {
        //int len = stationTrafficsFirst.size();
        int len = stationTrafficsFirst.size();
        for (int i = 0; i < len; i++) {
            if (i == 0 && i < len) {
                vo.setStationTraffic0((ScreenStationTrafficVO) stationTrafficsFirst.get(i));
                continue;
            }
            if (i == 1 && i < len) {
                vo.setStationTraffic1((ScreenStationTrafficVO) stationTrafficsFirst.get(i));
                continue;
            }
            if (i == 2 && i < len) {
                vo.setStationTraffic2((ScreenStationTrafficVO) stationTrafficsFirst.get(i));
                continue;
            }
            if (i == 3 && i < len) {
                vo.setStationTraffic3((ScreenStationTrafficVO) stationTrafficsFirst.get(i));
                continue;
            }
            if (i == 4 && i < len) {
                vo.setStationTraffic4((ScreenStationTrafficVO) stationTrafficsFirst.get(i));
                continue;
            }
            if (i == 5 && i < len) {
                vo.setStationTraffic5((ScreenStationTrafficVO) stationTrafficsFirst.get(i));
                continue;
            }
            if (i == 6 && i < len) {
                vo.setStationTraffic6((ScreenStationTrafficVO) stationTrafficsFirst.get(i));
                continue;
            }
            if (i == 7 && i < len) {
                vo.setStationTraffic7((ScreenStationTrafficVO) stationTrafficsFirst.get(i));
                continue;
            }
            if (i == 8 && i < len) {
                vo.setStationTraffic8((ScreenStationTrafficVO) stationTrafficsFirst.get(i));
                continue;
            }
            if (i == 9 && i < len) {
                vo.setStationTraffic9((ScreenStationTrafficVO) stationTrafficsFirst.get(i));
                continue;
            }
            if (i == 10 && i < len) {
                vo.setStationTraffic10((ScreenStationTrafficVO) stationTrafficsFirst.get(i));
                continue;
            }

        }
    }

    /**
     *  第二列数据12－23
     * @param vo
     * @param stationTrafficsSecond 
     */
    private void setSecondColData(ScreenStationsTrafficVO vo,
            Vector stationTrafficsSecond) {
        int len = stationTrafficsSecond.size();
        for (int i = 0; i < len; i++) {
            if (i == 0 && i < len) {
                vo.setStationTraffic11((ScreenStationTrafficVO) stationTrafficsSecond.get(i));
                continue;
            }
            if (i == 1 && i < len) {
                vo.setStationTraffic12((ScreenStationTrafficVO) stationTrafficsSecond.get(i));
                continue;
            }
            if (i == 2 && i < len) {
                vo.setStationTraffic13((ScreenStationTrafficVO) stationTrafficsSecond.get(i));
                continue;
            }
            if (i == 3 && i < len) {
                vo.setStationTraffic14((ScreenStationTrafficVO) stationTrafficsSecond.get(i));
                continue;
            }
            if (i == 4 && i < len) {
                vo.setStationTraffic15((ScreenStationTrafficVO) stationTrafficsSecond.get(i));
                continue;
            }
            if (i == 5 && i < len) {
                vo.setStationTraffic16((ScreenStationTrafficVO) stationTrafficsSecond.get(i));
                continue;
            }
            if (i == 6 && i < len) {
                vo.setStationTraffic17((ScreenStationTrafficVO) stationTrafficsSecond.get(i));
                continue;
            }
            if (i == 7 && i < len) {
                vo.setStationTraffic18((ScreenStationTrafficVO) stationTrafficsSecond.get(i));
                continue;
            }
            if (i == 8 && i < len) {
                vo.setStationTraffic19((ScreenStationTrafficVO) stationTrafficsSecond.get(i));
                continue;
            }
            if (i == 9 && i < len) {
                vo.setStationTraffic20((ScreenStationTrafficVO) stationTrafficsSecond.get(i));
                continue;
            }
            if (i == 10 && i < len) {
                vo.setStationTraffic21((ScreenStationTrafficVO) stationTrafficsSecond.get(i));
                continue;
            }

        }
    }

    /**
     *  第三列数据24－35
     * @param vo
     * @param stationTrafficsThird 
     */
    private void setThirdColData(ScreenStationsTrafficVO vo,
            Vector stationTrafficsThird) {
        int len = stationTrafficsThird.size();
        for (int i = 0; i < len; i++) {
            if (i == 0 && i < len) {
                vo.setStationTraffic22((ScreenStationTrafficVO) stationTrafficsThird.get(i));
                continue;
            }
            if (i == 1 && i < len) {
                vo.setStationTraffic23((ScreenStationTrafficVO) stationTrafficsThird.get(i));
                continue;
            }
            if (i == 2 && i < len) {
                vo.setStationTraffic24((ScreenStationTrafficVO) stationTrafficsThird.get(i));
                continue;
            }
            if (i == 3 && i < len) {
                vo.setStationTraffic25((ScreenStationTrafficVO) stationTrafficsThird.get(i));
                continue;
            }
            if (i == 4 && i < len) {
                vo.setStationTraffic26((ScreenStationTrafficVO) stationTrafficsThird.get(i));
                continue;
            }
            if (i == 5 && i < len) {
                vo.setStationTraffic27((ScreenStationTrafficVO) stationTrafficsThird.get(i));
                continue;
            }
            if (i == 6 && i < len) {
                vo.setStationTraffic28((ScreenStationTrafficVO) stationTrafficsThird.get(i));
                continue;
            }
            if (i == 7 && i < len) {
                vo.setStationTraffic29((ScreenStationTrafficVO) stationTrafficsThird.get(i));
                continue;
            }
            if (i == 8 && i < len) {
                vo.setStationTraffic30((ScreenStationTrafficVO) stationTrafficsThird.get(i));
                continue;
            }
            if (i == 9 && i < len) {
                vo.setStationTraffic31((ScreenStationTrafficVO) stationTrafficsThird.get(i));
                continue;
            }
            if (i == 10 && i < len) {
                vo.setStationTraffic32((ScreenStationTrafficVO) stationTrafficsThird.get(i));
                continue;
            }

        }
    }

    /**
     * 添加小时客流标题
     */
    public void addCaptionForHour(TreeNode treeNode, JLabel jLabelDataCaption, JLabel jLabelGraphCaption) {
        String captionData = this.getCaptionDataForHour(treeNode);
        String captionGraph = this.getCaptionGraphForHour(treeNode);
        jLabelDataCaption.setText(captionData);
        jLabelGraphCaption.setText(captionGraph);
    }

    /**
     * 添加分钟客流标题
     */
    public void addCaptionForMin(TreeNode treeNode, JLabel jLabelDataCaption, JLabel jLabelGraphCaption) {
        String captionData = this.getCaptionDataForMin(treeNode);
        String captionGraph = this.getCaptionGraphForMin(treeNode);
        jLabelDataCaption.setText(captionData);
        jLabelGraphCaption.setText(captionGraph);
    }

    /**
     * 表格区小时客流标题
     * @param treeNode
     * @return 
     */
    private String getCaptionDataForHour(TreeNode treeNode) {
        if (treeNode.getNodeType().equals(AppConstant.NODE_TYPE_NET)) {
            return treeNode.getName() + "各线路今日累计客流";
        }
        if (treeNode.getNodeType().equals(AppConstant.NODE_TYPE_LINE)) {
            return treeNode.getName() + "各车站今日累计客流";
        }
        if (treeNode.getNodeType().equals(AppConstant.NODE_TYPE_STATION)) {
            return treeNode.getName() + "今日累计客流";
        }
        return "";
    }
    
    /**
     * 表格区分钟客流标题
     * @param treeNode
     * @return 
     */
    private String getCaptionDataForMin(TreeNode treeNode) {
        if (treeNode.getNodeType().equals(AppConstant.NODE_TYPE_NET)) {
            return treeNode.getName() + "今日累计客流";
        }
        if (treeNode.getNodeType().equals(AppConstant.NODE_TYPE_LINE)) {
            return treeNode.getName() + "今日累计客流";
        }
        if (treeNode.getNodeType().equals(AppConstant.NODE_TYPE_STATION)) {
            return treeNode.getName() + "今日累计客流";
        }
        return "";
    }

    /**
     * 图形区小时客流标题
     * @param treeNode
     * @return 
     */
    private String getCaptionGraphForHour(TreeNode treeNode) {
        if (treeNode.getNodeType().equals(AppConstant.NODE_TYPE_NET)) {
            return treeNode.getName() + "今日小时客流";
        }
        if (treeNode.getNodeType().equals(AppConstant.NODE_TYPE_LINE)) {
            return treeNode.getName() + "今日小时客流";
        }
        if (treeNode.getNodeType().equals(AppConstant.NODE_TYPE_STATION)) {
            return treeNode.getName() + "今日小时客流";
        }
        return "";
    }

    /**
     * 图形区分钟客流标题
     * @param treeNode
     * @return 
     */
    private String getCaptionGraphForMin(TreeNode treeNode) {
        if (treeNode.getNodeType().equals(AppConstant.NODE_TYPE_NET)) {
            return treeNode.getName() + "今日5分钟进站客流";
        }
        if (treeNode.getNodeType().equals(AppConstant.NODE_TYPE_LINE)) {
            return treeNode.getName() + "今日5分钟客流";
        }
        if (treeNode.getNodeType().equals(AppConstant.NODE_TYPE_STATION)) {
            return treeNode.getName() + "今日5分钟客流";
        }
        return "";
    }

    //小时客流模式
    public boolean isModeForHour() {
        if (AppConstant.CURRENT_MODE == AppConstant.MODE_SHOW_HOUR) {
            return true;
        }
        return false;
    }

    //5分钟客流模式
    public boolean isModeForMin() {
        if (AppConstant.CURRENT_MODE == AppConstant.MODE_SHOW_MIN) {
            return true;
        }
        return false;
    }

    //设备监控模式
    public boolean isModeForDev() {
        if (AppConstant.CURRENT_MODE == AppConstant.MODE_SHOW_DEV) {
            return true;
        }
        return false;
    }
    
    //票卡类型客流监控模式
    public boolean isModeForCard() {
        if (AppConstant.CURRENT_MODE == AppConstant.MODE_SHOW_CARD_TYPE) {
            return true;
        }
        return false;
    }
    
    /**
     * 选择显示模式主方法
     */
    public void processFlow(JTree tree, JPanel jPanelGraph, JScrollPane jScrollPane1Data,
            JLabel jLabelDataCaption, JLabel jLabelGraphCaption, JScrollPane jScrollPane1Dev) {
        synchronized (AppUtil.synVo) {
            AppUtil util = new AppUtil();
            try {
                //获取选择的节点
                DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
                if (node == null) {
                    return;
                }
                TreeNode treeNode = (TreeNode) node.getUserObject();
                logger.info("id:" + treeNode.getNodeId() + " name:" + treeNode.getName());
                
                //处理小时客流
                if (util.isModeForHour()) {
                    this.processHour(treeNode, jPanelGraph, jScrollPane1Data,
                            jLabelDataCaption, jLabelGraphCaption);
                }
                
                //处理分钟客流
                if (util.isModeForMin()) {
                    this.processMin(treeNode, jPanelGraph, jScrollPane1Data,
                            jLabelDataCaption, jLabelGraphCaption);
                }
                
                //处理设备状态
                if (util.isModeForDev()) {
                    DevUtil dUtil = new DevUtil();
                    dUtil.processDev(treeNode, jScrollPane1Dev, jLabelDataCaption);
                    jScrollPane1Dev.setBorder(null);
                    jScrollPane1Data.setViewportView(jScrollPane1Dev);
                }
                        
                //处理票卡类型客流
                if (util.isModeForCard()) {
                    this.processCard(treeNode, jPanelGraph,jScrollPane1Data, jLabelDataCaption);
                }

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
    
    /**
     * 票卡类型客流显示
     * @param treeNode 网点
     * @param jScrollPane1Dev 显示区
     * @param jLabelDataCaption 顶title
     */
    void processCard(TreeNode treeNode, JPanel jPanelGraph,JScrollPane jScrollPane1Data, JLabel jLabelDataCaption) throws Exception {
        String currentDate = DateHelper.datetimeToString(new Date());
        DrawUtil dutil = new DrawUtil();
        AppUtil util = new AppUtil();
        long startTime =System.currentTimeMillis();
        long endTime;
        //修改title文字
        jLabelDataCaption.setText(this.getCaptionDataForMin(treeNode) + "（票卡类型）");
        //数据库取票卡类型客流数据
        Map<String, Vector> traffics = util.getCardTypeTraffic(treeNode.getNodeId(), currentDate);
        endTime =System.currentTimeMillis();
        logger.info("从数据库获取票卡类型客流时间"+((endTime-startTime)/1000)+"秒");
        
        //组合票卡子类型数据 同一类型组合出站进站数据
        Vector dataSub = this.setTableDataCard(traffics);
        //生成表格
        util.addTableForCard(dataSub,jScrollPane1Data);
        
        //生成饼图
        //清除之前的内容
        jPanelGraph.removeAll();
        jPanelGraph.updateUI();
        //设置2区框架，背景颜色
        jPanelGraph.setLayout(new GridLayout(1, 2));//jPanelGraph.setLayout(new GridLayout(2, 2));
        jPanelGraph.setBackground(DrawUtil.COLOR_PLOT_BACKGROUND);
        
        /* 单图组成多图
        JFreeChart chartInMain = dutil.getPieForCard(traffics, AppConstant.CARD_IN_MAIN, "进站主类型客流");
        JFreeChart chartOutMain = dutil.getPieForCard(traffics, AppConstant.CARD_OUT_MAIN, "出站主类型客流");
        jPanelGraph.add(new ChartPanel(chartInMain));
        jPanelGraph.add(new ChartPanel(chartOutMain));
        JFreeChart chartInSub = dutil.getPieForCard(traffics, AppConstant.CARD_IN_SUB, "进站子类型客流");
        JFreeChart chartOutSub = dutil.getPieForCard(traffics, AppConstant.CARD_OUT_SUB, "出站子类型客流");
        jPanelGraph.add(new ChartPanel(chartInSub));
        jPanelGraph.add(new ChartPanel(chartOutSub));
        */
        //多图式
        JFreeChart chart = dutil.getMultiPieForCard(traffics,dataSub);
        jPanelGraph.add(new ChartPanel(chart));
        
        jPanelGraph.updateUI();
        jPanelGraph.setVisible(true);
        
        endTime=System.currentTimeMillis();
        logger.info("生成票卡类型客流图形时间"+((endTime-startTime)/1000)+"秒");
    }

    /**
     * 5分钟客流模式
     * @throws Exception 
     */
    private void processMin(TreeNode treeNode, JPanel jPanelGraph, JScrollPane jScrollPane1Data,
            JLabel jLabelDataCaption, JLabel jLabelGraphCaption) throws Exception {
        String currentDate = DateHelper.datetimeToString(new Date());// "2009-06-21 14:00:00";
        AppUtil util = new AppUtil();
        //添加分钟客流的标题包括表格区、折线区
        util.addCaptionForMin(treeNode, jLabelDataCaption, jLabelGraphCaption);
        //选择线网节点
        if (treeNode.getNodeType().equals(AppConstant.NODE_TYPE_NET)) {
            //表格显示线网进出站总客流
            util.addTableForStationTotal(treeNode.getNodeId(), currentDate, jScrollPane1Data);
        }
        //选择线路节点
        if (treeNode.getNodeType().equals(AppConstant.NODE_TYPE_LINE)) {
            //表格显示线路进出站总客流
            util.addTableForStationTotal(treeNode.getNodeId(), currentDate, jScrollPane1Data);
        }
        //选择车站节点
        if (treeNode.getNodeType().equals(AppConstant.NODE_TYPE_STATION)) {
            //表格显示车站进出站总客流
            util.addTableForStationTotal(treeNode.getNodeId(), currentDate, jScrollPane1Data);
        }
        //获取横坐标动态分钟坐标值，该值随不同的小时而不同
        //格式化分钟坐标值
        // if (AppUtil.MinTitles == null) {
        AppUtil.MinTitles = util.getTitlesForMin(currentDate, AppUtil.Mins);
        AppUtil.MinTitlesFormat = util.getTitlesForMinFormated(AppUtil.MinTitles);
        // }
        //获取分钟客流的数据，并将数据转换成绘图数据
        //图形的横坐标是分钟，使用RowTitle对象封装
        //图形的序列是线路或进出站，使用LineTitle对象封装
        //返回结果第一个是横坐标封装对象、后面是绘图数据对象
        Vector traffics = util.getLineStationSpanMinTraffic(treeNode.getNodeId(), currentDate);

        JFreeChart chart = util.getPicForSpanMinForLocal(traffics);
        util.addChart(jPanelGraph, chart);
    }

    /**
     * 小时客流模式
     */
    private void processHour(TreeNode treeNode, JPanel jPanelGraph, JScrollPane jScrollPane1Data,
            JLabel jLabelDataCaption, JLabel jLabelGraphCaption) throws Exception {
        String currentDate = DateHelper.datetimeToString(new Date());
        String lineId = null;
        int index = -1;
        AppUtil util = new AppUtil();
        long startTime =System.currentTimeMillis();
        long endTime;
        //添加小时客流的标题包括表格区、柱状图区
        util.addCaptionForHour(treeNode, jLabelDataCaption, jLabelGraphCaption);
        //选择线网节点
        if (treeNode.getNodeType().equals(AppConstant.NODE_TYPE_NET)) {
            //表格显示线网各线路进出站总客流
            util.addTableForNet(treeNode.getNodeId(), currentDate, jScrollPane1Data);

            endTime =System.currentTimeMillis();
            logger.info("生成小时客流线网进出客流时间"+((endTime-startTime)/1000)+"秒");
        }
         
        //选择线路节点
        if (treeNode.getNodeType().equals(AppConstant.NODE_TYPE_LINE)) {
            index = treeNode.getNodeId().indexOf("_");
            lineId = treeNode.getNodeId().substring(0, index);
            //表格显示线路各车站进出站总客流
            util.addTableForLineStation(lineId, currentDate, jScrollPane1Data);

            endTime =System.currentTimeMillis();
            logger.info("生成小时客流线路各车站客流时间"+((endTime-startTime)/1000)+"秒");
        }
        
        //选择车站节点
        if (treeNode.getNodeType().equals(AppConstant.NODE_TYPE_STATION)) {
            //表格显示车站进出站总客流
            util.addTableForStation(treeNode.getNodeId(), currentDate, jScrollPane1Data);

            endTime =System.currentTimeMillis();
            logger.info("生成小时客流车站客流时间"+((endTime-startTime)/1000)+"秒");
        }
         
        //获取小时客流的数据，并将数据转换成绘图数据
        //图形的横坐标是小时，使用RowTitle对象封装
        //图形的序列是线路或进出站，使用LineTitle对象封装
        //返回结果第一个是横坐标封装对象、后面是绘图数据对象
        startTime=System.currentTimeMillis();

        Vector traffics = util.getLineStationSpanTraffic(treeNode.getNodeId(), currentDate);

        endTime =System.currentTimeMillis();
        logger.info("从数据库获取小时客流分时客流时间"+((endTime-startTime)/1000)+"秒");

        startTime=System.currentTimeMillis();
        JFreeChart chart = util.getPicForSpanHourForLocal(traffics);
        //柱状图显示线网各线路、线路进出站、车站进出站的小时客流
        util.addChart(jPanelGraph, chart);

        logger.info("生成小时客流分时客流图形时间"+((endTime-startTime)/1000)+"秒");
    }
    
    /**
     * 取当前显示器分辨率比率
     * @param common 
     */
    public void setScreenSize(Hashtable common){
        Dimension d =  Toolkit.getDefaultToolkit().getScreenSize();
        AppConstant.BATE_HEIGHT = d.getHeight()/Double.valueOf(common.get(XMLSAXHandler.Common_ScreenH_TAG).toString());
        AppConstant.BATE_WIDTH = d.getWidth()/Double.valueOf(common.get(XMLSAXHandler.Common_ScreenW_TAG).toString());
    }
}
