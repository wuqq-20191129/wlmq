/**
 * <p>Copyright: Copyright GuangZhou goldsign Co. 2005</p>
 * <p>Company: GuangZhou goldsign Co</p>
 */

package stationDraw;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * <p>Title: Dialog</p>
 * <p>Description: 这个对话框是插入BOM，TVM，TCM 时和修改设备的名称时弹出的对话框</p>
 * @author zhanxiaoxin
 * @version 1.0
 */
class Dialog extends JDialog implements ActionListener {
    /**  text1保存inputfield1的输入，text2保存inputfield2的输入  */
    protected String text1, text2;

    /**  inputfield1输入设备的ID，inputfield2输入设备的名称 */
    protected JTextField inputfield1 = new JTextField(), inputfield2=new JTextField();

    /**  flag用来标识对话框的确定退出，还是取消推出。对话框的调用者根据它来做处理  */
    protected  boolean flag = false;
    /**  对话框中  */
    //private JLabel label1;

    /**  用来装对话框的输入框和标签和按钮的容器  */
    private Box b;

    /**  pane包含设备ID输入框和设备名称输入框、另外两个标签  */
    protected JPanel pane = new JPanel();
    
    public Dialog(JFrame parent){

        //设置对话框的标题，对话框的大小
        super( parent, "输入", true );
        super.setResizable(false);
        super.setLocation(350, 300);    //？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？
        setSize( 240, 200 );
        //setDialogSize();

        b = Box.createVerticalBox();
        //设置pane的布局方式
        pane.setLayout(null);
        //设置pane的大小
        pane.setPreferredSize( new Dimension(200,100) );

        //给pane面板加上JLabel标签和JTextField文本输入框
        setLabel1( "请输入设备ID", 50 , 5 );
        setInputField( inputfield1, 35 , 25 );
        setLabel1( "请输入设备的名称", 50 , 45 );
        setInputField( inputfield2, 35 ,65);

        //把pane面板加到盒式布局的容器中
        b.add( Box.createGlue() );
        b.add(pane);
        b.add( Box.createGlue() );

        getContentPane().add( pane, "Center");
        setButton();

    }

    /**
     * 用message1和message2来初始化两个文本输入框的内容
     * @param parent JFrame
     * @param message1 String
     * @param message2 String
     */
    public Dialog( JFrame parent , String message1 , String message2){
        this(parent);
        inputfield1.setText(message1);
        inputfield2.setText(message2);
    }

    /**
     * 新建一个文本内容为str的JLabel，设置好位置，并把它加入到pane面板中
     * @param str String
     * @param x int
     * @param y int
     */
    public void setLabel1( String str,int x, int y){
        JLabel label1=new JLabel(str);
        pane.add(label1);
        label1.setBounds( x, y, 140, 20); // x=50, y=5
    }

    /**
     * 新建一个文本输入框，设置好位置，并把它加入到pane面板中
     * @param field JTextField
     * @param x int
     * @param y int
     */
    public void setInputField( JTextField field , int x, int y){
        pane.add(field);
        field.setBounds( x, y, 150, 20); //x=35,y=25;
     }

    /**
     * 设置对话框的确定和取消按钮，把确定按钮设置为对话框的默认按钮，
     * 并为确定、取消按钮设置快捷键（确定：Enter键 ，取消：Esc键）
     */
    public void setButton(){
        JPanel pane3 = new JPanel();
        JButton okButton = new JButton("确定");
        okButton.setActionCommand("确定");
        //设置对话框的默认按钮
        this.getRootPane().setDefaultButton(okButton);
        JButton cancelButton = new JButton("取消");
        cancelButton.setActionCommand("取消");

        okButton.addActionListener(this);
        cancelButton.addActionListener(this);
        //给取消按钮设置快捷键Esc
        this.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).
            put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "dispose");
        this.getRootPane().getActionMap().put("dispose", new AbstractAction(){
            public void actionPerformed(ActionEvent e){
                flag = false;
                setVisible(false);
            }
        });

        pane3.add(okButton);
        pane3.add(cancelButton);
        getContentPane().add(pane3,"South");
    }

    public void actionPerformed(ActionEvent evt){
        String source = evt.getActionCommand();
        if(source.equals("确定")) {
            submit();
            if(flag==true)
            setVisible(false);
        }
        else if (source.equals("取消"))
        { 
            flag = false;
            setVisible(false);
        }
    }

    /**
     * 当按下确定按钮，那么就将输入的设备ID和设备名保存到text1和text2变量里。
     */
    public void submit(){
        text1 = inputfield1.getText().trim();
        if(text1.length()>4){
            JOptionPane.showMessageDialog(this , "设备ID超过4位字符，请重新输入！" );
            inputfield1.setText(null);
            return;
        }
        text2 = inputfield2.getText().trim();
        if( text1.length() > 0 && text2.length() > 0 )
            flag = true;
        else {
            JOptionPane.showMessageDialog(this , "设备ID或设备名称输入不正确" );
        }
    }

    /** 返回变量text1的值 */
    public String getText1(){
        return text1;
    }

    /** 返回变量flag的值 */
    public boolean getFlag(){
        return flag;
    }

    /** 返回变量text2的值 */
    public String getText2(){
        return text2;
    }

}
