/**
 * <p>Copyright: Copyright GuangZhou goldsign Co. 2005</p>
 * <p>Company: GuangZhou goldsign Co</p>
 */

package stationDraw;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * This is a dialog designed by zhanxiaoxin
 * It is for the special input of text .
 * It support inputing text and select the text's size、text's type from keyborad.
 */

public class TextDialog extends JDialog
{
    //send the text and fontsize to mframe to display
    private String text, fontsize;

    /** flag用来表示对话框是以确定动作退出，还是以取消动作退出 */
    private boolean flag;

    /** 文本对话框的输入的文本框 */
    JTextField textfield;

    /** 用于表示动态文本类型还是静态文本类型的复选框 */
    JCheckBox textType;

    /** 从文本对话框返回给文本对象的节点类型：03或05 */
    private String node_type;

    /**
     * 初始化文本对话框，并对文本对话框的各部分布局好。
     * 文本对话框主要包括两部分：centerPanel, southPanel
     * centerPanel包括输入文本框、文本字体大小的下拉框、文本类型
     * southPanel包括确定按钮和取消按钮
     * @param parent JFrame
     */
    public TextDialog(JFrame parent)
    {
        //initialize the dialog size and location,make the dialog can't be resized
        super(parent, "输入", true);
        super.setResizable(false);
        super.setLocation(350, 300);
        setSize(250, 180);

        //create a box for adding component to the dialog
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(3,1));

        //announce the " input the text "JLabel,and the input textfield
        textfield = new JTextField(14);
        JPanel textpanel = new JPanel();
        JLabel textLabel = new JLabel("请输入文本:");
        textpanel.add(textLabel);
        textpanel.add(textfield);

        //define a new jpanel and insert the combobox and jlabel into it
        JPanel fontpanel = new JPanel();
        JLabel fontlabel = new JLabel("请选择字体大小:");

        fontpanel.add(fontlabel);

        //建立文本字体大小的下拉框，里面有1-7号字体可供选择
        JComboBox sizeCombox = new JComboBox();
        for (int i = 1; i <= 7; i++)
          sizeCombox.addItem(Integer.toString(i));
        sizeCombox.addActionListener(new selectListener());
        //设置字体大小的下拉框默认的选择为3
        sizeCombox.setSelectedItem("3");
        fontpanel.add(sizeCombox);
        sizeCombox.setPreferredSize(new Dimension(40,20));

        //JPanel typePanel = new JPanel();
        //textType = new JCheckBox("动态文本");
        //typePanel.add(textType);

        centerPanel.add(textpanel);
        centerPanel.add(fontpanel);
        //centerPanel.add(typePanel);

        //define a jpanel to construct the OK and Cancel button panel
        JPanel southPanel = new JPanel();
        JButton OKbutton = new JButton("确定");
        JButton cancelButton = new JButton("取消");
        OKbutton.addActionListener(new OkListener());
        cancelButton.addActionListener(new CancelListener());
        southPanel.add(OKbutton);
        southPanel.add(cancelButton);

        this.getRootPane().setDefaultButton(OKbutton);
        getContentPane().add(centerPanel, "Center");
        getContentPane().add(southPanel, "South");
    }

    /**
     * 返回标志flag，以表示对话框按下的是确定按钮，还是取消按钮
     * @return boolean
     */
    public boolean getflag()
    {
        return flag;
    }

    /**
     * 返回文本的内容
     * @return String
     */
    public String gettext()
    {
        return text;
    }

    /**
     * 返回文本字体的大小：1，2，3，4，5，6，7
     * @return String
     */
    public String getsize()
    {
        return fontsize;
    }

    /**
     * 返回文本的类型
     * @return String
     */
    public String getNodeType()
    {
        return node_type;
    }

    //set dialog to hidden
    public void setview()
    {
        this.setVisible(false);
    }

    //字体大小的下拉框的监听器
    class selectListener
        implements ActionListener
    {
        public void actionPerformed(ActionEvent evt)
        {
            JComboBox combox = (JComboBox) evt.getSource();
            fontsize = (String) combox.getSelectedItem();
        }
    }

    //监听确定按钮的类
    class OkListener
      implements ActionListener
    {
        public void actionPerformed(ActionEvent evt)
        {
            flag = true;
            text = textfield.getText().trim();
            //屏蔽动态字体，该种字体在“客流状态监控系统”中不会显示
            /*
            if(textType.isSelected())
                node_type = "05";
            else
                node_type = "03";
        */
            node_type = "03";
            setview();
        }
    }

    //取消按钮的监听器
    class CancelListener
        implements ActionListener
    {
        public void actionPerformed(ActionEvent evt)
        {
            flag = false;
            setview();
        }
    }
}



