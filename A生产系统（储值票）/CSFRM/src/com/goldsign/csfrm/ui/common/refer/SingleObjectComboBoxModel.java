/*
 * SingleObjectComboBoxModel.java
 *
 * Created on 2007年7月24日, 下午11:31
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package com.goldsign.csfrm.ui.common.refer;

import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;

/**
 *
 * @author
 */
public class SingleObjectComboBoxModel extends AbstractListModel implements
        ComboBoxModel {

    private SimpleDateFormat dateFormat;
    private String selectedDate = "";

    public SingleObjectComboBoxModel() {
    }

    public SingleObjectComboBoxModel(SimpleDateFormat dateFormat) {
        setDateFormat(dateFormat);
    }

    public void setDateFormat(SimpleDateFormat dateFormat) {
        this.dateFormat = dateFormat;
    }

    public SimpleDateFormat getDateFormat() {
        return dateFormat;
    }

    public void setSelectedItem(Object anItem) {
        if (anItem == null) {
            return;
        }
        if (anItem instanceof Date) {
            try {
                selectedDate = this.dateFormat.format((Date) anItem);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else {
            try {
                String strDate = anItem.toString().trim();
                if (strDate.length() != 10 && strDate.length() != 19) {
                    return;
                }
                String pattern = dateFormat.toPattern();
                if (strDate.length() == 10 && pattern.length() == 19) {
                    strDate = strDate + selectedDate.substring(10);
                }
                dateFormat.parse(strDate);
                selectedDate = strDate;
            } catch (Exception ex) {
                throw new UnsupportedOperationException(
                        "Invalid datetime: string [" + anItem
                        + "], format is [" + dateFormat.toPattern() + "]. ");
            }
        }
        fireContentsChanged(this, -1, -1);
    }

    public Object getSelectedItem() {
        return selectedDate;
    }

    public Object getElementAt(int index) {
        return selectedDate;
    }

    public int getSize() {
        return 1;
    }
}
