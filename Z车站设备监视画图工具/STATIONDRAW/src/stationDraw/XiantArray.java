package stationDraw;

import java.util.ArrayList;
import java.awt.Point;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.BasicStroke;

    /**
    * 线条单元的类
    * @param null
    */
    class Xiant {
        /**  线条的两个端点  */
        public Point one = new Point( -1, -1), another = new Point( -1, -1);
        /**  线条在数据库中的节点类型  */
        public String node_type = "04";

        /**
         * 根据两点 m,n 初始化一条线条
         * @param m Point
         * @param n Point
         */
        public Xiant(Point m, Point n) {
            one.x = m.x;
            one.y = m.y;
            another.x = n.x;
            another.y = n.y;
        }

        /**
         * 界面上默认的画线的象素是1，要把线条画得粗一点，应该画线的象素变成3
         * @param g Graphics
         */
        public void draw(Graphics g) {

            Graphics2D g2 = (Graphics2D) g;
            BasicStroke old = (BasicStroke) g2.getStroke();
            BasicStroke n = new BasicStroke(3);
            g2.setStroke(n);
            g2.drawLine(one.x, one.y, another.x, another.y);
            g2.setStroke(old);

        }
    }

public class XiantArray {
    /**  全部线条对象的数组  */
    private ArrayList xiantAr;

    /**
     * 初始化线条对象的数组
     */
    public XiantArray(){
        xiantAr = new ArrayList();
    }

    /**
     * 返回线条数组的长度
     * @return int
     */
    public int getArrayLenth(){
        return xiantAr.size();
    }

    /**
     * 往线条数组中添加一条线，并返回线条在数组中的序号
     * @param m Point
     * @param n Point
     * @return int
     */
    public int add( Point m, Point n) {
        Xiant x = new Xiant(m, n);
        xiantAr.add(x);
        return xiantAr.size()-1;
    }
    public void add(String imagesUrl,String description){
        new ToolPanel().lineSource.add(imagesUrl);
        new ToolPanel().xianName.add(description);
        System.out.println("线条数组Url="+imagesUrl);
    }
    
    /**
     * 线条数组中删除序号为x的线
     * @param x int
     */
    public void removeXiant(int x) {
        if(xiantAr.size() > x){
            xiantAr.remove(x);
            new ToolPanel().lineSource.remove(x);
            new ToolPanel().xianName.remove(x);
        }
    }

    /**
     * 判断x,y是否在线条数组中的某条线上，如果在，就返回线条的序号
     * @param x int
     * @param y int
     * @return int
     */
    public int find(int x, int y) {
        for (int i = 0; i < xiantAr.size(); i++) {
            if ( (getXiant(i).one.x == getXiant(i).another.x &&
                  (x <= getXiant(i).one.x + 1 && x >= getXiant(i).one.x - 1) &&
                  ( (y - getXiant(i).one.y) * (y - getXiant(i).another.y)) <= 0)
                ||
                (getXiant(i).another.y == getXiant(i).one.y &&
                 (y <= getXiant(i).one.y + 1 && y >= getXiant(i).one.y - 1) &&
                 ( (x - getXiant(i).one.x) * (x - getXiant(i).another.x)) <= 0)) {
                    return i;
                }
        }
        return -1;
    }

    /**
     * 判断有哪些线条在(x1, y1)和(x2, y2)两点形成的矩形区域里，
     * 如果没有线条在矩形区域里，就返回空的ArrayList，否则在ArrayList里加入包含在矩形区域里的线条的序号i
     * @param x1 int
     * @param y1 int
     * @param x2 int
     * @param y2 int
     * @return ArrayList
     */
    public ArrayList find(int x1, int y1, int x2, int y2){
        ArrayList ar = new ArrayList();
        for(int i=0; i<xiantAr.size(); i++){
            if((getXiant(i).one.x-x1)*(getXiant(i).one.x-x2)<0 &&
               (getXiant(i).one.y-y1)*(getXiant(i).one.y-y2)<0)
                ar.add(Integer.toString(i));
            else if((getXiant(i).another.x-x1)*(getXiant(i).another.x-x2)<0 &&
                    (getXiant(i).another.y-y1)*(getXiant(i).another.y-y2)<0)
                ar.add(Integer.toString(i));
        }
        return ar;
    }

    /**
     * 得到序号为i的线条对象
     * @param i int
     * @return Xiant
     */
    public Xiant getXiant(int i){
        Xiant x = (Xiant) xiantAr.get(i);
        return x;
    }

    /**
     * 把线条数组中的线条全都画出来
     * @param g Graphics
     */
    public void drawAll(Graphics g) {
        for (int i = 0; i < xiantAr.size(); i++) {
            getXiant(i).draw(g);
        }
    }

    /**
     * 画出线条数组中的序号为i的线条
     * @param g Graphics
     * @param i int
     */
    public void drawOneXiant(Graphics g, int i) {
        getXiant(i).draw(g);
    }

    /**
     * 改变刚画的线条两端的坐标
     * @param m Point
     * @param n Point
     */
    public void changeLastXiant( Point m, Point n) {
        getXiant(xiantAr.size()-1).one.x = m.x;
        getXiant(xiantAr.size()-1).one.y = m.y;
        getXiant(xiantAr.size()-1).another.x = n.x;
        getXiant(xiantAr.size()-1).another.y = n.y;
    }

    /**
     * 移动数组中序号为i的线条
     * @param i int
     * @param x int
     * @param y int
     */
    public void moveXiant(int i, int x, int y) {
        getXiant(i).one.x += x;
        getXiant(i).another.x += x;
        getXiant(i).one.y += y;
        getXiant(i).another.y += y;
    }
    public void moveXiantFulsh(int i, int x, int y,String direction) {
        if (direction.equals("level")){
            int yy=y-getXiant(i).one.y;
            getXiant(i).one.y = y;
            getXiant(i).another.y += yy;
    //      System.out.println("line_YY="+yy);
        }
        else  if (direction.equals("vertical")){
            int xx=x-getXiant(i).one.x;
            getXiant(i).one.x = x;
            getXiant(i).another.x += xx;
      //    System.out.println("line_XX="+xx);
        }
    }

    /**
     * 从线条数组里取出序号为i的线条的数据:返回线条的两端的端点,及线条的左上角的端点pos_x, pos_y
     * @param i int
     * @return int[]
     */
    public int[] getOneXiantData(int i){
        int[] values=new int[6];
        //比较两端点x的大小，让values[0]得到的是两端点中x的最小值
        //values[0]对应于数据库中pos_x
        if (getXiant(i).one.x < getXiant(i).another.x){
            values[0]=values[2]=getXiant(i).one.x;
            values[4]= getXiant(i).another.x;
        }
        else {
            values[0]=values[2]=getXiant(i).another.x;
            values[4]=getXiant(i).one.x;
        }
        //比较两端点y的大小，让values[1]得到的是两端点中x的最小值
        //values[1]对应于数据库中pos_y
        if(getXiant(i).one.y < getXiant(i).another.y){
            values[1]=values[3]=getXiant(i).one.y;
            values[5]=getXiant(i).another.y;
        }
        else {
            values[1]=values[3]=getXiant(i).another.y;
            values[5]=getXiant(i).one.y;
        }
        return  values;
    }
    //返回线条的ImgUrl
    public String getLineImgUrl(int i){
        String lineUrl="";
        if ((!new ToolPanel().lineSource.isEmpty())&&(i<new ToolPanel().lineSource.size()))
            lineUrl= (String)new ToolPanel().lineSource.get(i);
        return lineUrl;
    }
    //返回线条的ImgUrl
    public String getXianName(int i){
        String XianName="";
        if ((!new ToolPanel().lineSource.isEmpty())&&(i<new ToolPanel().lineSource.size()))
            XianName= (String)new ToolPanel().xianName.get(i);
        return XianName;
    }

}
