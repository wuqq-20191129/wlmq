package com.goldsign.csfrm.ui.panel;

import com.goldsign.csfrm.env.BaseConstant;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Hashtable;
import javax.swing.JLabel;

/**
 *
 * @author lenvo
 * 
 * 公共状态栏
 */
public class PublicPanel implements ActionListener {

    private JLabel statusOpResult = null;//状态栏
    private Hashtable<String, JLabel> statusOpLinks = null;//连接栏
    public static final int OP_RESULT_TYPE_INFO = 0;//信息
    public static final int OP_RESULT_TYPE_WARNING = 1;//警告
    public static final int OP_RESULT_TYPE_ERROR = 2;//错误

    /**
     * Creates a new instance of PublicPanel
     */
    public PublicPanel() {
    }

    /**
     * 
     * @param label 
     */
    public void setStatusOpResultComp(JLabel label) {
        this.statusOpResult = label;
    }

    /**
     * 
     * @param label
     * @return 
     */
    public JLabel getStatusOpResultComp(JLabel label) {
        return this.statusOpResult;
    }

    /**
     * 
     * @param statusOpLinks 
     */
    public void setStatusOpLinkComp(Hashtable<String, JLabel> statusOpLinks) {
        this.statusOpLinks = statusOpLinks;
    }

    /**
     * 
     * @param name
     * @return 
     */
    public JLabel getStatusOpLinkComp(String name) {
        return this.statusOpLinks.get(name);
    }

    /**
     * 设置结果状态栏提示信息
     * 
     * @param text 
     */
    public void setOpResult(String text) {
        this.setOpResult(text, this.OP_RESULT_TYPE_INFO);

    }

    /**
     * 设置结果状态栏警告信息
     * 
     * @param text 
     */
    public void setOpResultWarn(String text) {
        this.setOpResult(text, this.OP_RESULT_TYPE_WARNING);
    }

    /**
     * 设置结果状态栏错误信息
     * 
     * @param text 
     */
    public void setOpResultError(String text) {
        this.setOpResult(text, this.OP_RESULT_TYPE_ERROR);
    }

    /**
     * 设置状态栏信息，包括提示:0、警告:1、错误:2
     * 
     * @param text
     * @param type 
     */
    public void setOpResult(String text, int type) {
        switch (type) {
            case OP_RESULT_TYPE_INFO:
                this.statusOpResult.setForeground(BaseConstant.SYS_INFO_COLOR);
                break;
            case OP_RESULT_TYPE_WARNING:
                this.statusOpResult.setForeground(BaseConstant.SYS_WARN_COLOR);
                break;
            case OP_RESULT_TYPE_ERROR:
                this.statusOpResult.setForeground(BaseConstant.SYS_ERROR_COLOR);
                break;
            default:
                this.statusOpResult.setForeground(BaseConstant.SYS_INFO_COLOR);

        }
        this.statusOpResult.setText(text);

    }

    /**
     * 设置连接状态栏提示信息
     * 
     * @param label 
     */
    public void setOpLink(String name, String text) {
        this.setOpLink(name, text, this.OP_RESULT_TYPE_INFO);

    }

    /**
     * 设置连接状态栏警告信息
     * 
     * @param label 
     */
    public void setOpLinkWarn(String name, String text) {
        this.setOpLink(name, text, this.OP_RESULT_TYPE_WARNING);
    }

    /**
     * 设置连接状态栏错误信息
     * 
     * @param label 
     */
    public void setOpLinkError(String name, String text) {
        this.setOpLink(name, text, this.OP_RESULT_TYPE_ERROR);
    }

    /**
     * 设置连接栏信息，包括提示:0、警告:1、错误:2
     * 
     * @param label
     * @param type 
     */
    private void setOpLink(String name, String text, int type) {
        JLabel label = getStatusOpLinkComp(name);
        if(null == label){
            return;
        }
        label.setText(text);
        switch (type) {
            case OP_RESULT_TYPE_INFO:
                label.setForeground(BaseConstant.SYS_INFO_COLOR);
                break;
            case OP_RESULT_TYPE_WARNING:
                label.setForeground(BaseConstant.SYS_WARN_COLOR);
                break;
            case OP_RESULT_TYPE_ERROR:
                label.setForeground(BaseConstant.SYS_ERROR_COLOR);
                break;
            default:
                label.setForeground(BaseConstant.SYS_DFT_COLOR);

        }
    }

    /**
     * 
     * @param e 
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
