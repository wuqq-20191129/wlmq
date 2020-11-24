/*
 * JDayLabel.java
 *
 * Created on 2007年7月24日, 下午11:31
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package com.goldsign.csfrm.ui.common.refer;

import com.goldsign.csfrm.ui.common.SwingUtil;
import java.awt.Dimension;
import java.awt.Graphics;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class JDayLabel extends JLabel {

    private static ImageIcon todayIcon =
            SwingUtil.getSwingImage("today.gif", new ImageIcon());
    Date date = null;
    ImageIcon currentIcon = null;
    /**
     * 日期格式（TODAY/TIP用）
     */
    final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
    /**
     * 日格式
     */
    final SimpleDateFormat dayFormat = new SimpleDateFormat("d");

    public JDayLabel(Date date) {
        this(date, true);
    }

    public JDayLabel(Date date, boolean isSmallLabel) {
        setPreferredSize(new Dimension(40, 20));
        setToolTipText(dateFormat.format(date));
        this.date = date;
        if (isSmallLabel) {
            setHorizontalAlignment(JLabel.CENTER);
            setText(dayFormat.format(date));
            Date d = new Date();
            if (dateFormat.format(date).equals(dateFormat.format(d))) {
                currentIcon = todayIcon;
            }
        } else {
            setText("Today:" + dateFormat.format(new Date()));
            setIcon(todayIcon);
            setHorizontalAlignment(JLabel.LEFT);
        }
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void paint(Graphics g) {
        super.paint(g);
        if (currentIcon != null && isEnabled()) {
            int x = (this.getWidth() - currentIcon.getIconWidth()) / 2;
            int y = (this.getHeight() - currentIcon.getIconHeight()) / 2;
            currentIcon.paintIcon(this, g, x, y);
        }
    }
}
