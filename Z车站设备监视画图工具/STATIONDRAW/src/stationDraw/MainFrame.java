/**
 * <p>Copyright: Copyright GuangZhou goldsign Co. 2005</p>
 * <p>Company: GuangZhou goldsign Co</p>
 */

package stationDraw;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;
import stationDraw.Util.ReadWriteXml;

/**
 * <p>Title: MainFrame</p>
 * <p>Description: The MainFrame class contains toolpanel , drawpanel.And
 it is the Application's display frame </p>
 * @author zhanxiaoxin
 * @version 1.0
 */
class MainFrame
    extends JFrame
    implements MouseMotionListener, ActionListener, KeyListener {
    /**  mainframe的主要构成部分:中心位置的画图面板  */
    private DrawPanel drawpane = new DrawPanel();
    /**  mainframe的主要构成部分：顶端的工具面板 */
    private ToolPanel toolpane = new ToolPanel();

    /** 画线条时，用到的两个点：起点xian_p1, 终点xian_p2  */
    private Point xian_p1 = new Point( -1, -1), xian_p2 = new Point( -1, -1);

    /** 用deviceOldPosition 来存储设备的原来的位置  */
    private Point deviceOldPosition = new Point(0, 0);

    /** selectArea 代表鼠标选取的区域的左上角的位置  */
    private Point selectAreaP1 = new Point( -1, -1);

    /**  线路和车站下拉框的监听者  */
    private stationListener sta_listener = new stationListener();

    /**  topPanel包含toolpane, 线路，车站下拉框， dataPanel包含关闭和保存按钮  */
    private JPanel topPanel, dataPanel = new JPanel();

    /**  当前显示的车站的线路代码，车站代码。  */
    private String station = "01", sta_line = "01";

    /**  dataPanel中的保存和关闭按钮   */
    JButton saveButton, closeButton, flushLeft, flushRigth;

    /**  线路选择和车站选择的下拉框  */
    JComboBox Linecombox, Stacombox;

    /**  文本输入的对话框  */
    TextDialog textDialog = new TextDialog(this);

    int framew = ReadWriteXml.screenW, frameh = ReadWriteXml.screenH;
  //表示是否按下Ctrl键
    public static boolean pressCtrl = false;
    /**  mindistance为移动的最小距离变量  */
    private int mindistance = 2;

    /**  stationlist用来存储所有车站的线路代码和车站代码的数组 例如：0101西朗，
     * 第1，2位表示线路ID，第3,4位表示车站ID，西朗为车站名 */
    private ArrayList stationlist;

    /**  当前被选中的设备的类型序列和设备的序号。
       序号为i的CURRENTOBTYPE中的元素对应序号为i的CURRENTOBNUM中的元素  */
    public static ArrayList CURRENTSELECT = new ArrayList();
    //保存按下Ctrl后的设备元素对应序号
    public static ArrayList CtrlSELECT = new ArrayList();
    /**  标志当前选中的是设备的名称，还是设备的总体。  */
    public static int NAMEFLAG = 0;

    /**  用于构造单向闸机的对话框的字符串  */
    private String[] str = {
        "向左", "向右", "向上", "向下"};
    /**  增加进站闸机的弹出对话框   */
    private DeviceDialog dialog = new DeviceDialog(this, str);
     /**  增加出站闸机的弹出对话框   */
    private DeviceDialog dialogOut = new DeviceDialog(this, str);

    /**  用于构造双向闸机的对话框  */
    private String[] str2 = {
        "水平", "垂直"};
    /**  增加双向闸机的弹出对话框  */
    private DeviceDialog dialog2 = new DeviceDialog(this, str2);

    /**
     * mianframe的初始化的主要步骤：
     * 1、从数据库中取出车站设备的位置信息和全部车站的线路ID、车站ID
     * 2、把mianframe的各组成部分布局好。(topPanel, drawPanel, dataPanel)
     * 3、给mainframe加上各种监听器：鼠标监听器，鼠标移动监听器，键盘监听器
     */
    public MainFrame() {
        setTitle("车站设备监视图画图工具");
        setSize(framew, frameh);
        //this.setResizable(false);
        //给主应用程序显示框架添加关闭窗口的动作，当关闭时，先把数据库的连接关闭，再退出应用程序。
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent evt) {
                try {
                    drawpane.devicesDao.closeConnection();
                    System.exit(0);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        //从数据库中取出车站设备的位置信息和全部车站的线路ID、车站ID
        try {
            openData();
            stationlist = drawpane.devicesDao.getStation();
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(this, "读取数据库失败！");
            e.printStackTrace();
        }

        setTopPanel();

        //把画图面板加上滚动栏的功能，并把drawpane布局于mainframe的中心。
        drawpane.setFocusable(true);
        // drawpane.setSize(1024,768);
        JScrollPane scrollpane = new JScrollPane(drawpane);
        getContentPane().add(topPanel, "North");
        getContentPane().add(scrollpane, "Center");
        getContentPane().add(dataPanel, "South");

        dataPanel.setBorder(BorderFactory.createEtchedBorder());

        //左对齐按扭的声明与加入
        addButton(dataPanel, "水平对齐");
        //右对齐按扭的声明与加入
        addButton(dataPanel, "垂直对齐");
        //保存按扭的声明与加入
        addButton(dataPanel, "保存");
        //删除按扭的声明与加入
        addButton(dataPanel, "删除");
        //关闭按扭的声明与加入
        addButton(dataPanel, "关闭");
        //给显示面板加入鼠标监听器和键盘监听器
        drawpane.addMouseMotionListener(this);
        drawpane.addKeyListener(this);
        drawpane.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                insert(e.getX(), e.getY());
                drawpane.repaint();
            }

            /**
             * 鼠标释放的时候有两种情况：
             * 1、鼠标一般的点击一下，然后释放。( 这种情况时，把当前的鼠标形状变成默认的形状 )
             * 2、鼠标拖动的时候释放，（这种情况时，如果鼠标选中某块区域的话，就根据区域来显示当前选中的设备。）
             * @param e MouseEvent
             */
            public void mouseReleased(MouseEvent e) {
                if ((toolpane.gettsource() == Integer.valueOf(AppConstant.source_button_line))
                        ||(toolpane.gettsource() == Integer.valueOf(AppConstant.source_button_border_line))) {
                    //当画完线的时候，把鼠标的形状变成默认的箭头
                    setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                    //当选取的区域不为空时，查找这个范围内是否有设备。
                }
                if (drawpane.area != null) {
                    //如果当前选中的区域存在设备，那么把这些设备的类型和序号分别存到CURRENTOBTYPE, CURRENTOBNUM中。
                    if (drawpane.find(drawpane.area.x, drawpane.area.y,
                                      drawpane.area.x + drawpane.area.width,
                                      drawpane.area.y + drawpane.area.height)) {
                        CURRENTSELECT = drawpane.getNumArray();
                    }
                    //查找完以后把区域赋为空。
                    drawpane.area = null;
                    drawpane.repaint();
                }
            }

            public void mouseClicked(MouseEvent evt) {
                //如果按下了Ctrl键
                if (MainFrame.pressCtrl) {
                    if (toolpane.gettsource() == Integer.valueOf(AppConstant.source_button_blank)) {
                        int tResource = ( (Point) CURRENTSELECT.get(0)).x; //设备的类型变量tResource
                        //设备的序号变量i
                        int i = ( (Point) CURRENTSELECT.get(0)).y;
                        CtrlSELECT.add(new Point(tResource, i));
                    }
                }
                else {
                    CtrlSELECT.clear();
                    //当鼠标双击一个设备时，取出该设备的类型和序号，然后调用改变设备属性的对话框
                    if (CURRENTSELECT.size() == 1 && evt.getClickCount() == 2) {
                        //设备的类型变量tResource
                        int tResource = ( (Point) CURRENTSELECT.get(0)).x;
                        //设备的序号变量i
                        int i = ( (Point) CURRENTSELECT.get(0)).y;
                        changeDev(tResource, i);
                    }
                }
                drawpane.repaint();
            }
        });
    }

    /**
     * 设置应用程序顶端的面板(线路和车站的下拉框，工具栏，删除按钮)
     */
    public void setTopPanel() {

        topPanel = new JPanel();
        //设置topPanel的边界
        topPanel.setBorder(BorderFactory.createEtchedBorder());
        //把topPanel设置成流式的布局方式
        topPanel.setLayout(new FlowLayout());
        //线路下拉框
        JLabel Linelab = new JLabel("线路 :");
        topPanel.add(Linelab);

        //从stationlist数组里面取得全部的线路名称，再新建一个下拉列表。
        ArrayList item = getLineID(stationlist);
        Linecombox = createComboBox(item);
        topPanel.add(Linecombox);

        //车站下拉框
        Stacombox = createComboBox(getStaArray(sta_line));
        Stacombox.setPreferredSize(new Dimension(95, 21));

        JLabel Stalab = new JLabel("车站 :");
        topPanel.add(Stalab);
        topPanel.add(Stacombox);

        //添加空白标签、图片部件标签。
        JLabel spaceLabel = new JLabel("  ");
        topPanel.add(spaceLabel);
        topPanel.add(toolpane);

        //使用空白标签，来隔开图片标签和删除按钮的距离。
        JLabel spaceLabl = new JLabel("   ");
        topPanel.add(spaceLabl);

        //删除按扭的声明与加入
        // addButton(topPanel, "删除");
    }

    /**
     * 删除和保存和关闭按扭的动作事件
     * @param e ActionEvent
     */
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if (command.equals("删除")) {
            remove();
        }
        else if (command.equals("水平对齐")) {
            this.saveFlush("level");
            //JOptionPane.showMessageDialog(this, "水平对齐！");
        }
        else if (command.equals("垂直对齐")) {
            this.saveFlush("vertical");
        }
        else if (command.equals("保存")) {
            save();
        }
        else if (command.equals("关闭")) {
            try {
                drawpane.devicesDao.closeConnection();
                System.exit(0);
            }
            catch (Exception evt) {
                evt.printStackTrace();
            }
        }
    }

    /**
     * 在parent面板上加上按钮button, name是按钮显示的名字
     * @param parent JPanel
     * @param name String
     */
    public void addButton(JPanel parent, String name) {
        JButton button = new JButton(name);
        button.setActionCommand(name);
        button.setFocusable(false);
        button.addActionListener(this);
        parent.add(button);
    }

    /**
     * 从ar中取出该数组中每个元素的头2位，并把结果存在一个ArrayList中，返回ArrayList
     * @param ar ArrayList 全部车站的线路ID和车站ID的数组。（每个元素的头2位为线路ID）
     * @return ArrayList 整个地铁的所有的线路ID（01，02，03，04）
     */
    public ArrayList getLineID(ArrayList ar) {
        ArrayList ary = new ArrayList(4);
        for (int i = 0; i < ar.size(); i++) {
            //提出每个元素的头2位
            String str = ( (String) ar.get(i)).substring(0, 2);
            //在ary中查找是否已经存在str这个线路ID。
            int m = 0;
            boolean flag = true;
            while (m < ary.size() && flag) {
                if (str.equals( (String) ary.get(m))) {
                  flag = false;
                }
                m++;
            }
            //如果不存在则flag返回true，那么把str加入到ary中。
            if (flag) {
                ary.add(str);
            }
        }
        return ary;
    }

    /**
     * 取出lineID线路的所有车站名, 把它组成一个数组并返回。
     * @param lineID String
     * @return ArrayList 返回车站ID和车站名
     */
    public ArrayList getStaArray(String lineID) {
        ArrayList ar = new ArrayList();
        for (int i = 0; i < stationlist.size(); i++) {
            String s = (String) stationlist.get(i);
            //如果从数组中取出的该元素的头两位跟lineID相等，那么就把该元素的其他的字符（车站ID、名称）加到ar中。
            if (s.startsWith(lineID)) {
                ar.add(s.substring(2));
            }
        }
        return ar;
    }

    /**
    *
    * @param x int
    * @param y int
    */
    public void insert(int x, int y) {

        //当鼠标按下时，首先确定是否已经有设备在此位置上
        boolean flag = drawpane.find(x, y);

        /** 当鼠标点击中一个设备的时候，首先找出鼠标点的设备是否已经在选取的设备当中。
             如果鼠标点的设备在选取的设备的之中，那就不改变当前已经选取的设备的类型（CURRENTOBTYPE）
          和序号（CURRENTOBNUM）。
             否则把原来已选取的设备的数组序列清空，把新选中的设备加入到选取的设备的数组序列中。*/
        if (flag) {

            //记录下当前鼠标按下的这个点，以便当鼠标拖动时用得着
            deviceOldPosition.x = x;
            deviceOldPosition.y = y;

            int num = drawpane.getcurrent();
            int tResource = drawpane.getsource();
            boolean f = false;
            //在已选中的CURRENTSELECT中查找是否已包含鼠标点击中的设备。
            for (int i = 0; i < CURRENTSELECT.size(); i++) {
                if (tResource == ( (Point) CURRENTSELECT.get(i)).x) {
                    if (num == ( (Point) CURRENTSELECT.get(i)).y) {
                        f = true;
                    }
                }
            }
            NAMEFLAG = drawpane.getnamesrc();

            if (!f) {
                CURRENTSELECT.clear();
                CURRENTSELECT.add(new Point(tResource, num));
            }
        }

        //如果点击鼠标的时候没选取任何东西，则根据工具栏选中的工具，增加各种设备。
        else {
            x = x - x % mindistance;
            y = y - y % mindistance;
            CURRENTSELECT.clear();
            NAMEFLAG = 0;
            int sourceTemp = toolpane.gettsource();
            switch (sourceTemp) {
                case 0:

                    //当选择鼠标的时候，在一个空白的地方点击 .就把鼠标选中的区域area变量赋值为空
                    selectAreaP1.x = x;
                    selectAreaP1.y = y;
                    drawpane.area = null;
                    break;

                case 4:

                  //插入进站闸机
                  dialog.show();
                  if (dialog.getFlag()) {
                    String machineID = dialog.getText1().trim();
                    String machinename = dialog.getText2().trim();
                    String direction = dialog.getdirection();
                    int b = drawpane.devicesDao.addDevice(toolpane.gettsource(), x, y,
                                                      machineID,
                                                      machinename, direction);
                    if (b < 0) {
                      JOptionPane.showMessageDialog(this, "增加设备失败或设备的ID重复！");
                    }
                    else {
                      CURRENTSELECT.add(new Point(sourceTemp, b));
                    }
                  }
                  break;

                case 5:

                    //插入出站闸机
                    dialogOut.show();
                    if (dialogOut.getFlag()) {
                        String machineID = dialogOut.getText1().trim();
                        String machinename = dialogOut.getText2().trim();
                        String direction = dialogOut.getdirection();
                        int b = drawpane.devicesDao.addDevice(toolpane.gettsource(), x, y,
                                                          machineID,
                                                          machinename, direction);
                        if (b < 0) {
                            JOptionPane.showMessageDialog(this, "增加设备失败或设备的ID重复！");
                        }
                        else {
                            CURRENTSELECT.add(new Point(sourceTemp, b));
                        }
                    }
                    break;

                case 6: //插入双向闸机
                    dialog2.show();
                    if (dialog2.getFlag()) {
                        String machineID = dialog2.getText1().trim();
                        String machinename = dialog2.getText2().trim();
                        String direction = dialog2.getdirection();
                        int b = drawpane.devicesDao.addDevice(toolpane.gettsource(), x, y,
                                                          machineID,
                                                          machinename, direction);
                        if (b < 0) {
                            JOptionPane.showMessageDialog(this, "增加设备失败或设备的ID重复！");
                        }
                        else {
                            CURRENTSELECT.add(new Point(sourceTemp, b));
                        }
                    }

                    break;

                case 12: //插入文本
                    textDialog.show();
                    if (textDialog.getflag()) {
                        String text = textDialog.gettext();
                        String fontsize = textDialog.getsize();
                        String type = textDialog.getNodeType();
                        if (text.length() > 0) {
                            int b = drawpane.text.addText(text, fontsize, type, x, y);
                            CURRENTSELECT.add(new Point(sourceTemp, b));
                        }
                    }
                    break;

                case 13: //插入图象
                    ImageDialog open = new ImageDialog();
                    int receive = open.showOpen(drawpane);
                    if (receive == JFileChooser.APPROVE_OPTION) {
                        if (open.getfile() != null) {
                            String imgurl = open.getfile().toString();
                            if (imgurl.length() < 200) {
                                int b = drawpane.IM.addimage(imgurl, x, y);
                                if (b >= 0) {
                                  CURRENTSELECT.add(new Point(sourceTemp, b));
                                }
                            }
                            else {
                                JOptionPane.showMessageDialog(drawpane, "选择文件路径过长!");
                            }
                        }
                        else {
                            JOptionPane.showMessageDialog(drawpane, "文件选择错误!");
                        }
                    }
                    else if (receive == JFileChooser.ERROR_OPTION) {
                        JOptionPane.showMessageDialog(drawpane, "文件选择错误!");
                    }
                    break;

                  //默认的情况下，当标志为4,5,6,11时,插入BOM,TVM,TCM/PCA,ITM,AQM
                default:
                    if( toolpane.gettsource() == AppConstant.source_button_bom 
                            || toolpane.gettsource() == AppConstant.source_button_aqm
                            || toolpane.gettsource() == AppConstant.source_button_itm 
                            || toolpane.gettsource() == AppConstant.source_button_tvm
                            || toolpane.gettsource() == AppConstant.source_button_pca ) 
                    {
                        Dialog nextDialog = new Dialog(this);
                        nextDialog.show();
                        if (nextDialog.getFlag()) {
                            String machineID = nextDialog.getText1().trim();
                            String machinename = nextDialog.getText2().trim();
                            int b =
                                drawpane.devicesDao.addDevice(toolpane.gettsource(), x, y,
                                                          machineID,
                                                          machinename, "");
                            if (b < 0) {
                                JOptionPane.showMessageDialog(this, "增加设备失败或设备的ID重复！");
                            }
                            else {
                                CURRENTSELECT.add(new Point(sourceTemp, b));
                            }
                        }
                    }                     
            }
        }
        if ((toolpane.gettsource() == 11)||(toolpane.gettsource() == 14)) {
            if (toolpane.gettsource()==11){
                toolpane.lineSource.add("images/xian_icon.gif");
                toolpane.xianName.add("线条");
            }
            else if (toolpane.gettsource()==14){
                toolpane.lineSource.add("images/line.gif");
                toolpane.xianName.add("图例边框线");
            }
            CURRENTSELECT.clear();
            xian_p1.x = x;
            xian_p1.y = y;
            xian_p2.x = -1;
            setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));

        }

    }                     

    /**
     * 该方法用来调用改变设备名称的方法。
     * @param tResource int 设备的类型
     * @param i int 设备的序号
     */
    public void changeDev(int tResource, int i) {
        drawpane.devicesDao.changename(this, tResource, i);
    }

    /**
     * 当工具栏选中鼠标按钮时，鼠标移动在设备上方就把鼠标的形状改成一只手的形状，否则就变成箭头的形状
     * @param evt MouseEvent
     */
    public void mouseMoved(MouseEvent evt) {

        if (toolpane.gettsource() == 1) {
            int x = evt.getX();
            int y = evt.getY();
            if (drawpane.find(x, y)) {
                setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }
            else {
                setCursor(Cursor.getDefaultCursor());
            }
        }
    }

    /**
     * 鼠标拖动，
     * 一、工具栏选择的是鼠标按钮
     *   1、当选中设备时，鼠标拖动就拖动设备和名称.
     *   2、当选中名称时,鼠标拖动就只能拖动名称
     *   3、当没有选中物体时，鼠标拖动就代表绘制选择的区域
     * 二、工具栏选择的是画线按钮
     *   1、那么鼠标拖动就代表画直线
     * @param evt MouseEvent
     */
    public void mouseDragged(MouseEvent evt) {

        Graphics g = drawpane.getGraphics();
        //只有当选中鼠标按扭时，才可以进行拖动操作
        if (toolpane.gettsource() == AppConstant.source_button_blank) {
            int x = evt.getX();
            int y = evt.getY();

            //distanceX,distanceY为鼠标当前所在的位置减去鼠标原来按下的位置。
            int distanceX, distanceY;
            distanceX = x - deviceOldPosition.x;
            distanceY = y - deviceOldPosition.y;

            //movex,movey 为 distanceX,distanceY减去他们对最小间隔mindistance余下的数
            int movex = distanceX - distanceX % mindistance;
            int movey = distanceY - distanceY % mindistance;

            //把设备移动后，鼠标的原来按下的位置就也要移动。
            deviceOldPosition.x = deviceOldPosition.x + movex;
            deviceOldPosition.y = deviceOldPosition.y + movey;
            //如果当前选中的设备个数大于零，那么就随着鼠标的移动，拖动设备。
            if (CURRENTSELECT.size() > 0) {
                for (int i = 0; i < CURRENTSELECT.size(); i++) {
                    int tResource = ( (Point) CURRENTSELECT.get(i)).x;
                    int num = ( (Point) CURRENTSELECT.get(i)).y;
                    switch (tResource) {
                        case 11: //移动线条
                            drawpane.devicesDao.lineAr.moveXiant(num, movex, movey);
                            break;

                        case 12: //移动文本
                            drawpane.text.moveText(num, movex, movey);
                            break;

                        case 13: //移动图片
                            drawpane.IM.moveimage(num, movex, movey);
                            break;

                        default: //当标志为2,3,4,5,6,移动对应的设备
                            if (NAMEFLAG == 0) {
                                drawpane.devicesDao.move(tResource, num, movex, movey);
                            }
                            //只移动设备的名称
                            else {
                                drawpane.devicesDao.moveName(tResource, num, movex, movey);
                            }
                    }
                }
                drawpane.repaint();
            }
            //没有选择某个设备的时候，拖动鼠标就画出选择区域的矩形边框
            //drawpane.area表示选择区域。
            else {
                int startx, starty, swidth, sheight;
                if (x > selectAreaP1.x) {
                    startx = selectAreaP1.x;
                }
                else {
                    startx = x;
                }
                if (y > selectAreaP1.y) {
                    starty = selectAreaP1.y;
                }
                else {
                    starty = y;
                }
                swidth = Math.abs(x - selectAreaP1.x);
                sheight = Math.abs(y - selectAreaP1.y);
                drawpane.area = new Rectangle(startx, starty, swidth, sheight);
                drawpane.repaint();
            }
        }
        //gettsource()等于11/14工具面板选择画线的按钮，拖动鼠标就代表画线
        else if ((toolpane.gettsource() == 11)||(toolpane.gettsource() == 14)) {
            int x = evt.getX();
            int y = evt.getY();

            //当xian_p2点的X小于零时，表示当前拖动鼠标是新画的一条线。
            if (xian_p2.x < 0) {
                xian_p2.x = x;
                xian_p2.y = y;
                //设置X，Y的坐标使得只能画横线，竖线
                int p = Math.abs(xian_p2.x - xian_p1.x);
                int q = Math.abs(xian_p2.y - xian_p1.y);
                if (q > p) {
                    xian_p2.x = xian_p1.x;
                }
                else {
                    xian_p2.y = xian_p1.y;
                }
                int num = drawpane.devicesDao.lineAr.add(xian_p1, xian_p2);
                //CURRENTSELECT.add(new Point(7, num));
                System.out.println("insert="+toolpane.gettsource());
                System.out.println("insert_num="+num);
                CURRENTSELECT.add(new Point( toolpane.gettsource(), num));

            }

            //当xian_p2点的X大于零时，表示当前拖动鼠标是接着刚画的那条线，继续画。
            else {
                //接着刚画的一个点继续画另外一个点而连成线
                xian_p2.x = x;
                xian_p2.y = y;
                //设置X，Y的坐标使得只能画横线，竖线
                int p = Math.abs(xian_p2.x - xian_p1.x);
                int q = Math.abs(xian_p2.y - xian_p1.y);
                if (q >= p) {
                    xian_p2.x = xian_p1.x;
                }
                else {
                    xian_p2.y = xian_p1.y;
                }
                drawpane.devicesDao.lineAr.changeLastXiant(xian_p1, xian_p2);
                drawpane.repaint();
            }
        }
        g.dispose();
    }

    /**
     * 键盘的监听事件
     * 1、如果按下的DEL键的话，就调用remove()方法
     * 2、如果按下的是方向键，就根据方向不同，移动movex,movey也分别不同。然后移动当前选中的设备
     * @param evt KeyEvent
     */
    public void keyPressed(KeyEvent evt) {
        int keyCode = evt.getKeyCode();
        int movex = 0, movey = 0;

        if (keyCode == KeyEvent.VK_DELETE) {
            remove();
        }
        else if (keyCode == KeyEvent.VK_CONTROL) {
            this.pressCtrl = true;
        }
        else if (keyCode == KeyEvent.VK_DOWN || keyCode == KeyEvent.VK_UP ||
                 keyCode == KeyEvent.VK_LEFT || keyCode == KeyEvent.VK_RIGHT) {
            if (keyCode == KeyEvent.VK_DOWN) {
                movey = mindistance;
            }
            else if (keyCode == KeyEvent.VK_UP) {
                movey = -mindistance;
            }
            else if (keyCode == KeyEvent.VK_LEFT) {
                movex = -mindistance;
            }
            else if (keyCode == KeyEvent.VK_RIGHT) {
                movex = mindistance;
            }
            //当前选中的设备的个数大于零的时候。
            if (CURRENTSELECT.size() > 0) {
                for (int i = 0; i < CURRENTSELECT.size(); i++) {
                    int type = ( (Point) CURRENTSELECT.get(i)).x;
                    int num = ( (Point) CURRENTSELECT.get(i)).y;
                    switch (type) {
                        case 11:
                            drawpane.devicesDao.lineAr.moveXiant(num, movex, movey);
                            break;
                        case 12:
                            drawpane.text.moveText(num, movex, movey);
                            break;
                        case 13:
                            drawpane.IM.moveimage(num, movex, movey);
                            break;
                        default:
                    if (type == AppConstant.source_button_bom 
                            || type == AppConstant.source_button_aqm
                            || type == AppConstant.source_button_itm 
                            || type == AppConstant.source_button_tvm
                            || type == AppConstant.source_button_pca
                            || type == AppConstant.source_button_eg
                            || type == AppConstant.source_button_xeg
                            || type == AppConstant.source_button_twg
                            || type >= 14) 
                    {
                        if (NAMEFLAG == 0) { //加入判断:是否当前选中的是设备还是名称,再做移动操作
                            drawpane.devicesDao.move(type, num, movex, movey);
                        }
                        //如果名称标志为1，那么表示选中了名称，那么
                        else {
                            drawpane.devicesDao.moveName(type, num, movex, movey);
                            }
                        }
                    }
                }
            }
        drawpane.repaint();
        }
    }

    public void keyReleased(KeyEvent evt) {
        //JOptionPane.showMessageDialog(drawpane, "preeCtrl为假!");
        this.pressCtrl = false;
    }

    public void keyTyped(KeyEvent evt) {}

    /**
     * 删除当前选中的设备，删除完之后CURRENTSELECT数组也为空。
     */
    public void remove() {
        /**
         * 当删除当前选取的设备的时候，应该把CURRENTOBTYPE里面的元素由后往前删除，因为当删除ArrayList
         * 里面的元素i时，ArrayList中在元素i后面的元素会自动往前移，来填充空缺的元素位置，所以在删除ArrayList
         * 时，应该由后往前删除。
         */
        for (int i = CURRENTSELECT.size() - 1; i >= 0; i--) {

            int type = ( (Point) CURRENTSELECT.get(i)).x;
            int num = ( (Point) CURRENTSELECT.get(i)).y;
            //从CURRENTSELECT数组中提出一个元素后，就删除掉它。
            CURRENTSELECT.remove(i);
            switch (type) {
                case 11:
                    drawpane.devicesDao.lineAr.removeXiant(num);
                    break;
                case 12:
                    drawpane.text.removeText(num);
                    break;
                case 13:
                    drawpane.IM.removeimage(num);
                    break;
                default:
                    if (type == AppConstant.source_button_bom 
                            || type == AppConstant.source_button_aqm
                            || type == AppConstant.source_button_itm 
                            || type == AppConstant.source_button_tvm
                            || type == AppConstant.source_button_pca
                            || type == AppConstant.source_button_eg
                            || type == AppConstant.source_button_xeg
                            || type == AppConstant.source_button_twg
                            || type >= 14) 
                    {
                        if (NAMEFLAG != 1) {
                            //加入判断：如果点中的是设备名，那么删除操作就不做
                            drawpane.devicesDao.remove(type, num);
                        }
                    }
            }
        }
        drawpane.repaint();
    }

    /**
     * 根据数组参数item，来建立下拉框,并给它添加监听器
     * @param item ArrayList
     * @return JComboBox
     */
    public JComboBox createComboBox(ArrayList item) {
        JComboBox combox = new JComboBox();
        //给下拉框添加元素项
        for (int i = 0; i < item.size(); i++) {
            combox.addItem( (String) item.get(i));
        }
        combox.setFocusable(false);
        combox.addActionListener(sta_listener);
        return combox;
    }

    /**
     * 把界面上的设备的信息写入到数据库中，线路ID：sta_line 车站ID：station
     */
    public void save() {
        drawpane.save(sta_line, station);
    }

    /**
     * 对齐操作
     */
    public void saveFlush(String direction) {
        int type = 0, num = 0;
        //参照物X坐标,参照物Y坐标
        int xx = 0, yy = 0;

        //当前选中的设备的个数大于零的时候。
        if (CtrlSELECT.size() > 1) {
            Device one = drawpane.devicesDao.findDev( ( (Point) CtrlSELECT.get(0)).x);
            type = ( (Point) CtrlSELECT.get(0)).x;
            num = ( (Point) CtrlSELECT.get(0)).y;
            switch (type) {
                case 11: {
                    xx = drawpane.devicesDao.lineAr.getXiant(num).one.x;
                    yy = drawpane.devicesDao.lineAr.getXiant(num).one.y;
                    break;
                }
                case 12:{
                    xx=drawpane.text.getText(num).x;
                    yy=drawpane.text.getText(num).y;
                    break;
                }
                case 13: {
                    xx = drawpane.IM.getOneImage(num).x;
                    yy = drawpane.IM.getOneImage(num).y;
                    break;
                 }
                default: {
                    xx = one.getDev(num).getX();
                    yy = one.getDev(num).getY();
                    break;
                }
            }
            for (int i = 1; i < CtrlSELECT.size(); i++) {
                type = ( (Point) CtrlSELECT.get(i)).x;
                num = ( (Point) CtrlSELECT.get(i)).y;
                switch (type) {
                    case 11:
                        drawpane.devicesDao.lineAr.moveXiantFulsh(num, xx, yy, direction);
                        break;
                    case 12:
                        drawpane.text.moveTextFulsh(num,direction, xx, yy);
                        break;
                    case 13:
                        drawpane.IM.moveimageFulsh(num, xx, yy, direction);
                        break;
                    default:
                    if (type == AppConstant.source_button_bom 
                            || type == AppConstant.source_button_aqm
                            || type == AppConstant.source_button_itm 
                            || type == AppConstant.source_button_tvm
                            || type == AppConstant.source_button_pca
                            || type == AppConstant.source_button_eg
                            || type == AppConstant.source_button_xeg
                            || type == AppConstant.source_button_twg
                            || type >= 14) 
                    {
                        if (NAMEFLAG == 0) { //加入判断:是否当前选中的是设备还是名称,再做移动操作
                            drawpane.devicesDao.moveFush(type, num, direction, xx, yy);
                        }
                        else { //如果名称标志为1，那么表示选中了名称，那么
                            drawpane.devicesDao.moveNameFush(type, num, direction, xx, yy);
                        }
                        break;
                    }
                }
            }
            drawpane.repaint();
        }
        else {
            JOptionPane.showMessageDialog(drawpane, "请选择两个或两个以上的设备!");
        }
    }

    /**
     * 在重新打开一个车站的时候，应把CURRENTSELECT 清空和 NAMEFLAG 重新赋值
     */
    public void openData() {
        CURRENTSELECT.clear();
        NAMEFLAG = 0;
        try {
            drawpane.open(sta_line, station);
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(this, "读取数据库失败！");
            e.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * 车站选择和线路的监听器:选择要查看的车站,然后执行OPEN操作
     */
    class stationListener implements ActionListener {
        public void actionPerformed(ActionEvent evt) {
            JComboBox source = (JComboBox) evt.getSource();
            //判断是否是车站的下拉框发生变化。
            if (source.equals(Stacombox)) {
                String item = (String) source.getSelectedItem();
                if (item != null) {
                    //当选中的车站发生变化时，就根据新的车站打开车站的设备图
                    if (!station.equals(item.substring(0, 2))) {
                      station = item.substring(0, 2);
                      openData();
                    }
                }
            }

            //如果线路的下拉框发生改变，那么车站的下拉框就随之变化。
            else if (source.equals(Linecombox)) {
                String selectedLine = (String) source.getSelectedItem();

                //当选中的线路发生变化时，相应的车站下拉框就发生变化。
                if (!sta_line.equals(selectedLine)) {

                    //根据当前选取的线路selectedLine，从车站的数组中取出与线路相符的车站名称
                    ArrayList a = getStaArray(selectedLine);

                    Stacombox.removeAllItems(); //当删除车站的下拉框的元素时，车站的下拉框会产生一个事件。
                    for (int i = 0; i < a.size(); i++) {

                        //下拉框并不一定是选取的项改变，才会产生事件。
                        Stacombox.addItem( (String) a.get(i));
                    }
                    sta_line = selectedLine;
                    openData();
                }
            }
        }
    }
}
