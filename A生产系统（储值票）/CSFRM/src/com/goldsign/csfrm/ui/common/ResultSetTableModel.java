/*
 * ResultSetTable.java
 *
 * Created on 2007年5月1日, 上午9:27
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package com.goldsign.csfrm.ui.common;

import javax.swing.table.DefaultTableModel;

/**
 *
 * @author admin
 */
public class ResultSetTableModel extends DefaultTableModel {
    //  private String[] columnNames =null;

    private String[] columnNames = null;
    private Object[][] data = null;

    /**
     * Creates a new instance of ResultSetTable
     */
    public ResultSetTableModel() {
    }

    public ResultSetTableModel(String[] columnNames, Object[][] data) {
        this.columnNames = columnNames;
        this.data = data;
    }

    public int getRowCount() {
        if (data == null) {
            return 0;
        }
        return this.data.length;
    }

    public int getColumnCount() {
        if (this.columnNames == null) {
            return 0;
        }
        return this.columnNames.length;
    }

    public String getColumnName(int col) {
        return this.columnNames[col];
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        return this.data[rowIndex][columnIndex];
    }

    public Class getColumnClass(int c) {
        return getValueAt(0, c).getClass();
    }

    public void setValueAt(Object value, int row, int col) {
        /*if (true) {
            System.out.println("Setting value at " + row + "," + col
                    + " to " + value
                    + " (an instance of "
                    + value.getClass() + ")");
        }*/

        data[row][col] = value;
        fireTableCellUpdated(row, col);

        /*
        if (true) {
            System.out.println("New value of data:");
            printDebugData();
        }*/
    }

    public boolean isCellEditable(int row, int col) {
        //Note that the data/cell address is constant,
        //no matter where the cell appears onscreen.
        return false;
    }

    private void printDebugData() {
        int numRows = getRowCount();
        int numCols = getColumnCount();

        for (int i = 0; i < numRows; i++) {
            System.out.print("    row " + i + ":");
            for (int j = 0; j < numCols; j++) {
                System.out.print("  " + data[i][j]);
            }
            System.out.println();
        }
        System.out.println("--------------------------");
    }
}
