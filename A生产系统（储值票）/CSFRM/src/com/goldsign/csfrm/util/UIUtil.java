package com.goldsign.csfrm.util;

import com.goldsign.csfrm.ui.common.ResultSetTableModel;
import com.goldsign.csfrm.ui.common.ResultSetTableSorter;
import com.goldsign.csfrm.ui.common.TableHelper;
import com.goldsign.csfrm.vo.KeyValueVo;
import com.goldsign.csfrm.vo.SelectOptionVo;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

/**
 * 界面处理工具类
 * 
 * @author lenovo
 */
public class UIUtil {

    /**
     * Creates a new instance of Util
     */
    public UIUtil() {
    }

    /**
     * 实现容器居中功能
     * 
     * @param c 
     */
    public static void makeContainerInScreenMiddle(Container c) {
        Toolkit kit = Toolkit.getDefaultToolkit();

        Dimension screenSize = kit.getScreenSize();
        int width = (int) screenSize.getWidth();
        int height = (int) screenSize.getHeight();
        int w = c.getWidth();
        int h = c.getHeight();
        //System.out.println("LoginDialog w:" + w);
        //System.out.println("LoginDialog h:" + h);
        c.setLocation((width - w) / 2, (height - h) / 2);

    }
    
    /**
     * 提父组件，实现容器居中功能
     * 
     * @param c
     * @param pc 
     */
    public static void makeContainerInScreenMiddle(Component c, Container pc) {

        Dimension size = pc.getSize();
        int width = (int) size.getWidth();
        int height = (int) size.getHeight();
        int w = c.getWidth();
        int h = c.getHeight();
        //System.out.println("LoginDialog w:" + w);
        //System.out.println("LoginDialog h:" + h);
        c.setLocation((width - w) / 2, (height - h) / 2);

    }

    /**
     * 在指定容器，生成列表
     * 
     * @param scrollPane    容器
     * @param result    数据
     * @param columnNames   列表
     * @param columnSize    列宽
     * @param horizontalAlignments 对齐方式 
     */
    public static JTable genResultSetTable(JScrollPane scrollPane,
            List<Object[]> result, String[] columnNames, int[] columnSize, boolean isSort) {

        Object[][] data = new Object[result.size()][columnNames.length];

        for (int i = 0; i < result.size(); i++) {
            Object[] arr = result.get(i);
            for (int k = 0; k < arr.length; k++) {
                data[i][k] = arr[k];
            }
        }
        DefaultTableModel tableModel = null;
        ResultSetTableSorter sorter = null;
        JTable resultSetTableMain = null;
        if(isSort){
            tableModel = new ResultSetTableModel(columnNames, data);
            sorter = new ResultSetTableSorter(tableModel);
            resultSetTableMain = new JTable(sorter);
        }else{
            tableModel = new DefaultTableModel(data, columnNames);
            resultSetTableMain = new JTable(tableModel);
        }
        
        resultSetTableMain.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        //设置列宽
        //对齐方式LEFT = -1，CENTER = 0，RIGHT = 1
        int[] horizontalAlignments = new int[columnNames.length];
        new TableHelper().setColumn(resultSetTableMain, columnSize, horizontalAlignments);

        //this.resultSetTableMain.setSize(this.mainSPn.getSize());

        resultSetTableMain.getTableHeader().setBackground(new Color(255, 255, 255));
        resultSetTableMain.getTableHeader().setForeground(new Color(0, 39, 80));
        resultSetTableMain.getTableHeader().setFont(new java.awt.Font("宋体", 1, 14));
        if(isSort){
            sorter.setTableHeader(resultSetTableMain.getTableHeader());
        }
        resultSetTableMain.setBackground(new Color(255, 255, 255));
        resultSetTableMain.setFont(new java.awt.Font("宋体", 0, 14));
        resultSetTableMain.setForeground(new Color(0, 39, 80));
        scrollPane.setViewportView(resultSetTableMain);

        scrollPane.updateUI();
        
        return resultSetTableMain;
    }
    
    public static JTable genResultSetTable(JScrollPane scrollPane,
            List<Object[]> result, String[] columnNames, int[] columnSize) {
        return genResultSetTable(scrollPane, result, columnNames, columnSize, true);
    }
    
    /**
     * 隐藏表格中的某一列
     *
     * @param table 表格
     * @param index 要隐藏的列 的索引
     */
    public static void hideTableColumn(JTable table, int index){ 
        
        TableColumn tc = table.getColumnModel().getColumn(index); 
        tc.setMaxWidth(0); 
        tc.setPreferredWidth(0); 
        tc.setMinWidth(0); 
        tc.setWidth(0); 

        table.getTableHeader().getColumnModel().getColumn(index).setMaxWidth(0); 
        table.getTableHeader().getColumnModel().getColumn(index).setMinWidth(0); 
    } 
    
    /**
     * 隐藏表格中的列
     * 
     * @param table
     * @param indexs 
     */
    public static void hideTableColumns(JTable table, int[] indexs){
        for(int index: indexs){
            hideTableColumn(table, index);
        }
    }
    
    /**
     * 最大化窗口
     * 
     * @param c 
     */
    public static void maxWindow(Container c) {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd = ge.getDefaultScreenDevice();
        //这个代码有问题，在弹出对话框后，移动对话框，会出现对话框看不见的情况
        //gd.setFullScreenWindow(this);
        DisplayMode dm = gd.getDisplayMode();
        c.setBounds(0, 0, dm.getWidth(), dm.getHeight());
        //c.setVisible(true);
    }
    
    /**
     * 初始化下拉表
     * 
     * @param comboBox
     * @param keyValues 
     */
    public static void initComBoxValue(JComboBox comboBox, KeyValueVo[] keyValues){
        initComBoxValue(comboBox, Arrays.asList(keyValues));
    }
    
    /**
     * 初始化下拉表
     * 
     * @param comboBox
     * @param keyValues 
     */
    public static void initComBoxValue(JComboBox comboBox, List<KeyValueVo> keyValues){
        
        comboBox.removeAllItems();
        for(KeyValueVo keyValue:keyValues){
            comboBox.addItem(new SelectOptionVo(keyValue.getKey(), keyValue.getValue()));
        }
    }
    
    /**
     * 初始化下拉表
     *
     * @param comboBox
     * @param keyValues
     */
    public static void initComBoxValueWithDefault(JComboBox comboBox, KeyValueVo[] keyValues){
       initComBoxValueWithDefault(comboBox, Arrays.asList(keyValues));
    }
    
    /**
     * 初始化下拉表
     * 
     * @param comboBox
     * @param keyValues 
     */
    public static void initComBoxValueWithDefault(JComboBox comboBox, List<KeyValueVo> keyValues){
        
        List<KeyValueVo> list = new ArrayList<KeyValueVo>();
        list.add(new KeyValueVo("", ""));
        list.addAll(keyValues);;
        initComBoxValue(comboBox, list);
    }
    
    /**
     * 修改分辨率
     * 
     */
    public static void setScreenResolution(){
        
        GraphicsEnvironment environment = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice device=environment.getDefaultScreenDevice(); 
        DisplayMode displayMode=new DisplayMode(1024,768,16,75);/*分辨率1024*768 16位字 节 刷新频率75 */ 
        device.setDisplayMode(displayMode);
    }
    
    public static void main(String args[]) { 
        setScreenResolution(); 
    }
}
