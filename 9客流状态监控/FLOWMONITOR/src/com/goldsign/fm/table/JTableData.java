/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.fm.table;

import java.util.Vector;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author Administrator
 */
public class JTableData extends JTable {

    private JTableDataCellRender dataCellRender;

    public JTableData(Vector data,Vector col,JTableDataCellRender dataCellRender) {
        
        super(data,col);
        this.dataCellRender = dataCellRender;

    }
    public JTableData(JTableModeData model,JTableDataCellRender dataCellRender) {

        super(model);
        this.dataCellRender = dataCellRender;

    }

    public void setDataCellRender(JTableDataCellRender dataCellRender){
        this.dataCellRender = dataCellRender;
    }

    public TableCellRenderer getCellRenderer(int row, int column) {
        return dataCellRender;
    }
}
