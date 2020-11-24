/**
 * <p>Copyright: Copyright GuangZhou goldsign Co. 2005</p>
 * <p>Company: GuangZhou goldsign Co</p>
 */

package stationDraw;

import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.*;
import java.util.ArrayList;

/**
 * <p>Title: 工具栏</p>
 * <p>Description: 该面板为主界面中上端的工具栏面板，它主要包括(鼠标)，(单向闸机)，(双向闸机)，(票亭)，
 * (售票机)，(ITM 综合验票机),(PCA 便携式验票机)，(线条)，(文本)，(图片)按钮</p>
 * @author zhanxiaoxin
 */
class ToolPanel extends JPanel implements ActionListener{
    //TSOURCE代表当前选择的工具的类型。
    private static int TSOURCE=1;
    //所有工具按钮都放在group中
    private  ButtonGroup group;
    static ArrayList lineSource=new ArrayList();//TSOURCE代表线条当前选择的工具的类型
    static ArrayList xianName=new ArrayList();//线条的名称说明
    
    public ToolPanel(){
        group=new ButtonGroup();
        this.setLayout(new FlowLayout());
        //各项增加功能键的声明与添加
        addToggleButton(new ImageIcon("images/jian_icon.gif"),true, "");
        addToggleButton(new ImageIcon("images/eg_icon.gif"),false, "进站闸机");
        addToggleButton(new ImageIcon("images/xeg_icon.gif"),false, "出站闸机");
        addToggleButton(new ImageIcon("images/twg_icon.gif"),false, "双向闸机");
        addToggleButton(new ImageIcon("images/bom_icon.gif"),false, "票亭");
        addToggleButton(new ImageIcon("images/tvm_icon.gif"),false, "自动售票机");
        addToggleButton(new ImageIcon("images/pca_icon.gif"),false, "便携式验票机");
        addToggleButton(new ImageIcon("images/itm_icon.gif"),false, "综合验票机");
        addToggleButton(new ImageIcon("images/aqm_icon.gif"),false, "自动查询机");
        addToggleButton(new ImageIcon("images/xian_icon.gif"),false, "线条");
        addToggleButton(new ImageIcon("images/A_icon.gif"), false, "文本");
        addToggleButton(new ImageIcon("images/photo_icon.gif"),false, "图片");
        addToggleButton(new ImageIcon("images/line.gif"),false, "图例边框线");

    }

    /**
     * 根据指定的图标icon, 标志v, 往ToolPanel中添加按钮，并在按钮的右边加上按钮的名称
     * @param icon Icon
     * @param v boolean
     * @param name String
     */
    public void addToggleButton(Icon icon, boolean v, String name ){
        JToggleButton button = new JToggleButton(icon, v);
        //设置按钮不能成为焦点，因为我把界面的焦点一直设置在DrawPanel中，
        button.setFocusable(false);
        //设置按钮的周围无边界
        button.setBorder(null);
        //设置按钮的动作命令
        button.setActionCommand(name);
        button.addActionListener(this);
        group.add(button);
        this.add(button);
        JLabel label = new JLabel(name);
        this.add(label);
     }

    /**
     * 当按下某种工具按钮时，根据按下的按钮的类型来改变TSOURCE的值,TSOURCE为用来标识按钮类型的变量
     * @param e ActionEvent
     */
    public void actionPerformed(ActionEvent e ){
        String command=group.getSelection().getActionCommand();
        if (command.equals(""))
            TSOURCE = AppConstant.source_button_blank;
        else if (command.equals("进站闸机"))
            TSOURCE = AppConstant.source_button_eg;
        else if (command.equals("出站闸机"))
            TSOURCE = AppConstant.source_button_xeg;
        else if (command.equals("双向闸机"))
            TSOURCE = AppConstant.source_button_twg;
        else if (command.equals("票亭"))
            TSOURCE = AppConstant.source_button_bom;
        else if (command.equals("自动售票机"))
            TSOURCE = AppConstant.source_button_tvm;
        else if (command.equals("便携式验票机"))
            TSOURCE = AppConstant.source_button_pca;
        else if (command.equals("综合验票机"))
            TSOURCE = AppConstant.source_button_itm;
        else if (command.equals("自动查询机"))
            TSOURCE = AppConstant.source_button_aqm;
        else if (command.equals("线条"))
            TSOURCE = AppConstant.source_button_line;
        else if (command.equals("文本"))
            TSOURCE = AppConstant.source_button_word;
        else if (command.equals("图片"))
            TSOURCE = AppConstant.source_button_pic;
        else if (command.equals("图例边框线"))
            TSOURCE = AppConstant.source_button_border_line;
    }

    /**
     * 返回TSOURCE的值，即返回当前按钮的类型的标识
     * @return int
     */
    public int gettsource()
    {
        return TSOURCE;
    }

}
