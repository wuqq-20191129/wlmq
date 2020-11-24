/*
 * JDatePicker.java
 *
 * Created on 2007年7月24日, 下午11:29
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package com.goldsign.csfrm.ui.common;

import com.goldsign.csfrm.ui.common.refer.JDateDocument;
import com.goldsign.csfrm.ui.common.refer.SingleObjectComboBoxModel;
import com.sun.java.swing.plaf.motif.MotifComboBoxUI;
import com.sun.java.swing.plaf.windows.WindowsComboBoxUI;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.ComboBoxUI;
import javax.swing.plaf.basic.BasicComboPopup;
import javax.swing.plaf.basic.ComboPopup;
import javax.swing.plaf.metal.MetalComboBoxUI;

public class JDatePicker extends JComboBox implements Serializable {

    /**
     * 日期格式类型
     */
    public static final int STYLE_CN_DATE = 0;
    public static final int STYLE_CN_DATE1 = 1;
    public static final int STYLE_CN_DATETIME = 2;
    public static final int STYLE_CN_DATETIME1 = 3;
    /**
     * 日期格式类型
     */
    private int formatStyle = STYLE_CN_DATE;
    /**
     * 当前设置日期格式
     */
    private SimpleDateFormat dateFormat = null;
    /**
     * 只有一个值的ComboBoxModel
     */
    private SingleObjectComboBoxModel model = new SingleObjectComboBoxModel();
    JDateDocument dateDocument = null;

    /**
     * 构造式
     */
    public JDatePicker() throws UnsupportedOperationException {
        this(STYLE_CN_DATE);
    }

    public JDatePicker(int formatStyle) throws UnsupportedOperationException {
        this(formatStyle, new Date());
    }

    public JDatePicker(int formatStyle, Date initialDatetime) throws
            UnsupportedOperationException {
        this(formatStyle, initialDatetime, new java.awt.Font("宋体", 0, 14));
    }
    
    public JDatePicker(int formatStyle, Date initialDatetime, Font font) throws
            UnsupportedOperationException {

        this.setStyle(formatStyle);
        //设置可编辑
        this.setEditable(true);
        //设置编辑器属性(只能输入正确日期)
        JTextField textField = ((JTextField) getEditor().getEditorComponent());
        textField.setHorizontalAlignment(SwingConstants.CENTER);
        textField.setFont(font);
        textField.setForeground(new Color(0, 39, 80));
        dateDocument = new JDateDocument(textField, this.dateFormat);
        textField.setDocument(dateDocument);
        //设置Model为单值Model
        this.setModel(model);
        //设置当前选择日期
        this.setSelectedItem(initialDatetime == null
                ? new Date() : initialDatetime);
    }

    /**
     * 设置日期格式 STYLE_CN_DATE STYLE_CN_DATE1 STYLE_CN_DATETIME STYLE_CN_DATETIME1
     *
     * @param formatStyle int
     */
    public void setStyle(int formatStyle) throws UnsupportedOperationException {
        this.formatStyle = formatStyle;
        dateFormat = getDateFormat(formatStyle);
        model.setDateFormat(dateFormat);
        if (dateDocument != null) {
            dateDocument.setDateFormat(dateFormat);
        }
    }

    /**
     * 取得指定类型的日期格式
     *
     * @param formatStyle int
     * @return SimpleDateFormat
     * @throws UnsupportedOperationException
     */
    private static SimpleDateFormat getDateFormat(int formatStyle) throws
            UnsupportedOperationException {
        switch (formatStyle) {
            case STYLE_CN_DATE:
                return new SimpleDateFormat("yyyy/MM/dd");
            case STYLE_CN_DATE1:
                return new SimpleDateFormat("yyyy-MM-dd");
            case STYLE_CN_DATETIME:
                return new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            case STYLE_CN_DATETIME1:
                return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            default:
                throw new UnsupportedOperationException(
                        "invalid formatStyle parameter!");
        }
    }

    /**
     * 取得日期格式 STYLE_CN_DATE STYLE_CN_DATE1 STYLE_CN_DATETIME STYLE_CN_DATETIME1
     *
     * @return int
     */
    public int getStyle() {
        return formatStyle;
    }

    /**
     * 取得当前选择的日期
     *
     * @return Date
     */
    public Date getSelectedDate() throws ParseException {
        return dateFormat.parse(getSelectedItem().toString());
    }

    /**
     * 设置当前选择的日期
     *
     * @return Date
     */
    public void setSelectedDate(Date date) throws ParseException {
        this.setSelectedItem(dateFormat.format(date));
    }

    public void setSelectedItem(Object anObject) {
        model.setSelectedItem(anObject);
        super.setSelectedItem(anObject);
    }

    class DatePopup extends BasicComboPopup implements ChangeListener {

        JCalendarPanel calendarPanel = null;

        public DatePopup(JComboBox box) {
            super(box);
            setLayout(new BorderLayout());
            calendarPanel = new JCalendarPanel();
            calendarPanel.addDateChangeListener(this);
            add(calendarPanel, BorderLayout.CENTER);
            setBorder(BorderFactory.createEmptyBorder());
        }

        /**
         * 显示弹出面板
         */
        protected void firePropertyChange(String propertyName,
                Object oldValue,
                Object newValue) {
            if (propertyName.equals("visible")) {
                if (oldValue.equals(Boolean.FALSE)
                        && newValue.equals(Boolean.TRUE)) { //SHOW
                    try {
                        String strDate = comboBox.getSelectedItem().toString();
                        Date selectionDate = dateFormat.parse(strDate);
                        calendarPanel.setSelectedDate(selectionDate);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                } else if (oldValue.equals(Boolean.TRUE)
                        && newValue.equals(Boolean.FALSE)) { //HIDE
                }
            }
            super.firePropertyChange(propertyName, oldValue, newValue);
        }

        public void stateChanged(ChangeEvent e) {
            Date selectedDate = (Date) e.getSource();
            String strDate = dateFormat.format(selectedDate);
            if (comboBox.isEditable() && comboBox.getEditor() != null) {
                comboBox.configureEditor(comboBox.getEditor(), strDate);
            }
            comboBox.setSelectedItem(strDate);
            comboBox.setPopupVisible(false);
        }
    }

    /**
     * 更新UI
     */
    public void updateUI() {
        ComboBoxUI cui = (ComboBoxUI) UIManager.getUI(this);
        if (cui instanceof MetalComboBoxUI) {
            cui = new MetalDateComboBoxUI();
        } else if (cui instanceof MotifComboBoxUI) {
            cui = new MotifDateComboBoxUI();
        } else {
            cui = new WindowsDateComboBoxUI();
        }
        setUI(cui);
    }

    class MetalDateComboBoxUI extends MetalComboBoxUI {

        protected ComboPopup createPopup() {
            return new DatePopup(comboBox);
        }
    }

    class WindowsDateComboBoxUI extends WindowsComboBoxUI {

        protected ComboPopup createPopup() {
            return new DatePopup(comboBox);
        }
    }

    class MotifDateComboBoxUI extends MotifComboBoxUI {

        protected ComboPopup createPopup() {
            return new DatePopup(comboBox);
        }
    }

    /**
     * 测试JDatePicker
     */
    public static void main(String args[]) {

        JFrame f = new JFrame();;
        JPanel c = new JPanel();
        c.add(new JLabel("From:"));
        JDatePicker datePickerFrom = new JDatePicker(JDatePicker.STYLE_CN_DATETIME);
        c.add(datePickerFrom);
        c.add(new JLabel("To:"));
        Date d = new Date();
        d.setTime(d.getTime() + 10000000000L);
        JDatePicker datePickerTo = new JDatePicker(JDatePicker.STYLE_CN_DATE, d);
        datePickerTo.setEditable(false);
        c.add(datePickerTo);
        f.getContentPane().add(c, BorderLayout.NORTH);

        f.getContentPane().add(new JDatePicker(), BorderLayout.SOUTH);

        final JTable table = new JTable(20, 10);
        JComboBox editor = new JDatePicker();
        editor.setBorder(null);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table.setDefaultEditor(Object.class, new DefaultCellEditor(editor));
        JScrollPane sp = new JScrollPane(table);
        f.getContentPane().add(sp, BorderLayout.CENTER);

//            f.setSize(600, 400);
        f.setVisible(true);
    }
}
