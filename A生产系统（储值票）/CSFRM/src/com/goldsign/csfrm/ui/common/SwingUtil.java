/*
 * SwingUtil.java
 *
 * Created on 2007年7月24日, 下午11:54
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package com.goldsign.csfrm.ui.common;

import java.awt.MediaTracker;
import java.io.Serializable;
import java.util.ResourceBundle;
import javax.swing.ImageIcon;

/**
 *
 * @author
 */
public final class SwingUtil
        implements Serializable {

    private static ResourceBundle rb;

    public static ImageIcon getSwingImage(String name) {
        return getSwingImage(name, null);
    }

    /**
     * 取得图象资源
     *
     * @param name String 图像名
     * @param defaultIcon ImageIcon 未取到时赋予的默认图像
     * @return ImageIcon
     */
    public static ImageIcon getSwingImage(String name,
            ImageIcon defaultIcon) {
        ImageIcon icon = null;
        try {
            java.net.URL url = ClassLoader.getSystemResource(
                    "com/goldsign/csfrm/ui/common/images/" + name);
            icon = new ImageIcon(url);
        } catch (Exception ex) {
        }
        if (icon == null
                || icon.getImageLoadStatus() != MediaTracker.COMPLETE
                || icon.getIconHeight() <= 0) {
            icon = defaultIcon;
        }
        return icon;
    }
}
