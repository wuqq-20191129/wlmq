/**
 * <p>Copyright: Copyright GuangZhou goldsign Co. 2005</p>
 * <p>Company: GuangZhou goldsign Co</p>
 */

package stationDraw;

import javax.swing.JFrame;
import javax.swing.JComboBox;
import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;

/**
 *
 * <p>Title: 单向闸机和双向闸机的增加的对话框</p>
 * <p>Description: </p>
 * @author zhanxiaoxin
 */
class DeviceDialog extends Dialog{

    /** 设备的方向 */
    private String direction;
    /** 用来表示设备可选择的几种方向的下拉框 */
    private JComboBox combox;

    /**
     * 构造对话框,并定好位置
     * @param parent JFrame 对话框的父窗口
     * @param comboxItem String[] 几钟可供选择的设备方向构成的字符串数组
     */
    public DeviceDialog(JFrame parent, String[] comboxItem){
        super(parent);
        setLabel1("设备方向: ",20,92);
        setCombox(comboxItem,80,92);
    }

    /**
     * 初始化下拉框，用str数组中的每个元素做为下拉框的每个可选项
     * @param str String[]
     * @param x int
     * @param y int
     */
    public void setCombox(String[] str , int x, int y){
        combox=new JComboBox();
        for(int i=0;i<str.length;i++)
        combox.addItem(str[i]);
        //设置下拉框的位置。
        combox.setBounds( x , y , 55 ,20);
        combox.addActionListener(this);
        pane.add(combox);
    }

    /**
     * 在添加设备时，如果用户按下确定按钮，那么flag就返回true,并把对话框隐藏起来，供下次再调用
     * 如果用户按下的是取消按钮，那么flag就返回false，并把对话框隐藏起来，供下次调用
     * @param evt ActionEvent
     */
    public void actionPerformed(ActionEvent evt){
        String source=evt.getActionCommand();
        if(source.equals("确定")) {
            text1=inputfield1.getText().trim();
            text2=inputfield2.getText().trim();
            direction=(String) combox.getSelectedItem();
            //设备ID如果大于4位字符，则提示并清空，用户重新填写
            if(text1.length()>4){
                JOptionPane.showMessageDialog(this , "设备ID超过4位字符，请重新输入！" );
                inputfield1.setText(null);
                return;
            }
            //只有文本输入框的输入不为空时，才返回真。否则弹出警告
            if(text1.length()>0&&text2.length()>0)
            { 
                flag = true;
                this.setVisible(false);
            }
            else
                JOptionPane.showMessageDialog(this , "设备ID或设备名称输入不正确" );
        }
        else if (source.equals("取消"))
        { 
            flag = false;
            this.setVisible(false);
        }
    }

    /**
     * 根据得到下拉框返回的字符,来得到对应的image_direction的代码
     * 有两种情况：1 单向闸机：可选择的方向有4种 当可选方向为4种时，
     * 就在单向闸机的方向ID（01，02，11，12）里查找对应的方向ID
     * 2：双向闸机：可选择的方向有2种 当为2种时，就在双向闸机的方向ID（00，11）里查找对应的方向ID。
     * @return String 设备的方向ID
     */
    public String getdirection(){
        if (combox.getItemCount() == 4) {
            if (direction.equals(combox.getItemAt(0)))
                return "01";
            else if (direction.equals(combox.getItemAt(1)))
                return "02";
            else if (direction.equals(combox.getItemAt(2)))
                return "11";
            else
                return "12";
        }
        else if(direction.equals(combox.getItemAt(0)))
            return "00";
        else 
            return  "11";
    }
}
