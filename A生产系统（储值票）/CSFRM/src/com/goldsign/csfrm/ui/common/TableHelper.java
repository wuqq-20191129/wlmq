/*
 * TableHelper.java
 *
 * Created on 2007年6月26日, 下午3:04
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package com.goldsign.csfrm.ui.common;

import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumnModel;

/**
 *
 * @author wang
 */
public class TableHelper {

    private DefaultTableCellRenderer renderCenter = null;
    private DefaultTableCellRenderer renderLeft = null;
    private DefaultTableCellRenderer renderRight = null;
    public static final int LEFT = -1;
    public static final int CENTER = 0;
    public static final int RIGHT = 1;

    /**
     * Creates a new instance of TableHelper
     */
    public TableHelper() {
        renderCenter = new DefaultTableCellRenderer();
        renderCenter.setHorizontalAlignment(SwingConstants.CENTER);
        renderLeft = new DefaultTableCellRenderer();
        renderLeft.setHorizontalAlignment(SwingConstants.LEFT);
        renderRight = new DefaultTableCellRenderer();
        renderRight.setHorizontalAlignment(SwingConstants.RIGHT);
    }

    /**
     * @param table 需要设置的表
     * @param columnSize 需要修改的列宽
     */
    public void setColumn(JTable table, int[] columnSize, int[] horizontalAlignments) {
        if (table == null) {
            return;
        }
        TableColumnModel cm = table.getColumnModel();
        if (cm == null) {
            return;
        }
        for (int i = 0; i < columnSize.length; i++) {
            if (columnSize[i] <= 0) {
                continue;
            }
            if (cm.getColumn(i) == null) {
                return;
            }
            cm.getColumn(i).setPreferredWidth(columnSize[i]);
            switch (horizontalAlignments[i]) {
                case LEFT:
                    cm.getColumn(i).setCellRenderer(renderLeft);
                    break;
                case RIGHT:
                    cm.getColumn(i).setCellRenderer(renderRight);
                    break;
                default:
                    cm.getColumn(i).setCellRenderer(renderCenter);
                    break;
            }
        }
    }
    /**
     * String value = getColumnName(pColumn); //计算列宽 FontMetrics metrics =
     * getGraphics().getFontMetrics(); int width = metrics.stringWidth(value) +
     * (2*getColumnModel().getColumnMargin());
     *
     **
     */
}
