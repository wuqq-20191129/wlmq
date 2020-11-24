/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.goldsign.fm.table;

import java.util.Vector;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Administrator
 */
public class JTableModeData extends AbstractTableModel {
    private Vector columnNames ;;
    private Vector dataV ;
    public JTableModeData(Vector columnNames,Vector dataV){
        this.dataV=dataV;
        this.columnNames = columnNames;

    }
    public String getColumnName(int column){
        return (String)columnNames.get(column);
    }
    public int getRowCount() {
        return dataV.size();
    }

    public int getColumnCount() {
       return columnNames.size();
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        return ((Vector)dataV.get(rowIndex)).get(columnIndex);
    }

}
