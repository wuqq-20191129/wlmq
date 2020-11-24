/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.goldsign.fm.table;

import com.goldsign.fm.common.AppConstant;

import java.awt.Component;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author Administrator
 */
public class JTableDataCellRender extends DefaultTableCellRenderer {

    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row,
      int column) {
    super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

    this.setHorizontalAlignment(SwingConstants.CENTER);
    this.setVerticalAlignment(SwingConstants.CENTER);
    this.setSpecialFont();
    

    
    return this;
  }
    private void setSpecialFont(){
        String text = this.getText();
        if(text.equals(AppConstant.SCREEN_CAPTION_TOTAL)|| 
                text.equals(AppConstant.SCREEN_CAPTION_SUM)||
                text.startsWith(AppConstant.SCREEN_CAPTION_SUM_PRE))
            this.setForeground(AppConstant.COLOR_TABLE_DATA_TOTAL);
        else
            this.setForeground(AppConstant.COLOR_TABLE_DATA);
    }


}
