/**
 * <p>Copyright: Copyright GuangZhou goldsign Co. 2005</p>
 * <p>Company: GuangZhou goldsign Co</p>
 */

package stationDraw;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 *
 * <p>Title: Dev</p>
 * <p>Description: 该类的一个对象代表一个设备。</p>
 * @author zhanxiaoxin
 * @version 1.0
 */
public class Dev extends DevAncestor
{
    /**  初始化设备名的位置在设备的左上角 */
    private int namex = 0, namey = -2;
    /**  初始化设备的方向，只有单向闸机和双向闸机才有设备方向，其他的设备赋值为空  */
    private String img_direction = "";
    /**  设备的ID  */
    private String id;

    /**
     * 初始化一个设备
     * @param x int 设备在X轴上的坐标
     * @param y int 设备在Y轴上的坐标
     * @param dev_id String 设备的ID
     * @param name String 设备的名称
     * @param direction String 设备的方向
     */
    public Dev(int x, int y , String dev_id, String name, String direction)
     {
        super(x,y,name);
        id = dev_id;
        this.img_direction = direction;
     }
     /**
      * 设置设备的名称的坐标
      * @param idx int
      * @param idy int
      */
     public void setNamexy(int idx,int idy)
     {
        namex = idx;
        namey = idy;
     }

     /**
      * 移动设备的名称
      * @param dx int
      * @param dy int
      */
     public void moveName(int dx,int dy)
     {
        namex += dx;
        namey += dy;
     }

     /**
      * 把设备的ID变成newID
      * @param newID String
      */
     public void setID(String newID)
     {
        this.id=newID;
     }

     /**
      * 返回设备名称的坐标
      * @return Point
      */
     public Point getNameXY()
     {
        Point p = new Point(namex, namey);
        return p;
     }

     /**
      * 返回设备的ID
      * @return String
      */
     public String getID()
     {
        return id;
     }

     /**
      * 返回设备的方向
      * @return String
      */
     public String getImg_direction()
     {
        return img_direction;
     }

   }
   /**
    * 设备BOM, TVM, TCM, ITM的类
    */
   class Device
   {
        /**  存储设备的数组  */
        ArrayList array;
        /**  该设备的图片  */
        ImageIcon image;
        /**  该设备图片的路径  */
        String img_url ;
        /**  设备在数据库中的节点类型  */
        String  node_type="01";
        /**  设备的类型ID和设备的中文描述  */
        String dev_type, description;
        /**  设备图片的宽度和高度  */
        public int width, height;

        /**
         * 声明设备对象数组。
         * @param url String 设备的图片路径
         * @param type String 设备的类型ID
         */
        public Device(String url,String type)
        {
            array = new ArrayList();
            img_url = url;
            dev_type = type;
       
            if(type.equals(AppConstant.dev_type_eg)) description = "进站闸机";
            else if(type.equals(AppConstant.dev_type_xeg)) description = "出站闸机";
            else if(type.equals(AppConstant.dev_type_twg)) description = "双向闸机";
            else if(type.equals(AppConstant.dev_type_bom)) description = "票房售票机";
            else if(type.equals(AppConstant.dev_type_tvm)) description = "自动售票机";
            else if(type.equals(AppConstant.dev_type_pca)) description = "便携式验票机";
            else if(type.equals(AppConstant.dev_type_itm)) description = "综合验票机";
            else if(type.equals(AppConstant.dev_type_aqm)) description = "自动查询机";

            image = new ImageIcon(img_url);
            width = image.getIconWidth();
            height = image.getIconHeight();
        }

        /**
         * 增加一个设备
         * @param x int
         * @param y int
         * @param id String
         * @param name String
         * @param direction String
         * @return int
         */
        public  int add(int x, int y, String id, String name, String direction)
        {
            if(!existID(id))
            {
                Dev dev = new Dev(x, y, id, name, direction);
                array.add(dev);
                return array.size()-1;
            }else 
                return -1;
        }

        /**
         * 从数据库里读出设备的数据, 读出设备的坐标(x,y),设备的ID,设备的名称,设备图片的方向，名称的坐标
         * @param x int
         * @param y int
         * @param id String
         * @param name String
         * @param direction String
         * @param idx int
         * @param idy int
         * @return boolean
         */
        public boolean add(int x,int y, String id , String name ,String direction, int idx, int idy)
        {
            if(!existID(id))
            {
                Dev dev = new Dev(x, y, id, name, direction);
                dev.setNamexy(idx, idy);
                array.add(dev);
                return true;
            }
            else 
                return false;
        }

        /**
         * 删除数组中序号为i的设备
         * @param i int
         */
        public void remove(int i)
        {
            if(array.size() > i)
            array.remove(i);
        }

        /**
         * 返回数组中序号为i的设备对象，在返回前对象先判断序号i是否在数组的范围内，如果是则返回对象，否则返回空
         * @param i int
         * @return Dev
         */
        public Dev getDev(int i)
        {
            if(array.size() > i)
            {
                Dev d = (Dev) array.get(i);
                return d;
            }
            else return null;
        }

        /**
         * 返回设备数组中设备的数量
         * @return int
         */
        public int getArrayLength()
        {
            return array.size();
        }

        /**
         * 画出一个设备和设备的名称
         * @param g Graphics
         * @param i int
         */
        public void drawOne(Graphics g,int i)
        {
            g.drawImage(image.getImage(), getDev(i).getX(), getDev(i).getY(), null);
            //由于我的界面，默认下的设备名称的字体和何建军页面上显示的大小不一致，所以在画设备名称前要改变默认的字体。
            Font old = g.getFont();
            Font new1 = new Font("宋体",Font.PLAIN, 13);
            g.setFont(new1);
            g.drawString( getDev(i).getName(), getDev(i).getX() + getDev(i).getNameXY().x,
                          getDev(i).getY() + getDev(i).getNameXY().y );
            g.setFont(old);
         }

        /**
         * 此方法用于当设备被选中时调用的方法，用来显示该设备被选中了
         * 在设备的四个角上画出四个方格，并重画设备的名称
         * @param g Graphics
         * @param i int
         */
        public void displayOne(Graphics g, int i)
        {
            int x = getDev(i).getX();
            int y = getDev(i).getY();
            g.drawRect(x-2, y-2, 5, 5);
            g.drawRect(x-2,y+height-3, 5, 5);
            g.drawRect(x+width-3, y-2, 5, 5);
            g.drawRect(x+width-3, y+height-3, 5, 5);
            Font old = g.getFont();
            Font new1 = new Font("宋体", Font.PLAIN, 13);
            g.setFont(new1);
            g.drawString( getDev(i).getName(), getDev(i).getX() + getDev(i).getNameXY().x,
                          getDev(i).getY() + getDev(i).getNameXY().y );
            g.setFont(old);

        }

        /**
         * 画出设备对象数组中的所有设备
         * @param g Graphics
         */
        public void drawAll(Graphics g)
        {
            for (int i=0; i<array.size(); i++)
                drawOne(g,i);
        }

        /**
         * 移动整个设备
         * @param i int
         * @param x int
         * @param y int
         */
        public void move(int i,int x,int y)
        {
            getDev(i).move(x,y);
        }
        //移动设备，对齐操作,xx:参照物横坐标，yy:参照物纵坐标
        public void moveFulsh(int i,String direction,int xx,int yy){
            int  x=this.getDev(i).getX();
            int  y=this.getDev(i).getY();
            if (direction.equals("level")){
               this.getDev(i).moveFulsh(x,yy);
            }
            else if(direction.equals("vertical")){
                this.getDev(i).moveFulsh(xx,y);
            }
        }

        /**
         * 移动设备的名称
         * @param i int
         * @param x int
         * @param y int
         */
        public void moveName(int i ,int x,int y)
        {
            getDev(i).moveName(x,y);
        }
        
        //移动设备，对齐操作,xx:参照物横坐标，yy:参照物纵坐标
         public void moveNameFulsh(int i,int xx,int yy,String direction){
            int  x=this.getDev(i).getX();
            int  y=this.getDev(i).getY();

            if (direction.equals("level"))
                this.getDev(i).moveName(x,yy);
            else if(direction.equals("vertical"))
                this.getDev(i).moveName(xx,y);
        }

        /**
         * 查找（x,y）点是否在设备的图片的显示范围内
         * @param x int
         * @param y int
         * @return int
         */
        public int find(int x,int y)
        {
            for (int i=0; i<array.size();i++)
            { if(x > getDev(i).getX() && x < getDev(i).getX()+width
                 &&y > getDev(i).getY() && y < getDev(i).getY()+height)
                return i;
            }
            return -1;
        }

        /**
         * 查找在点（x1, y1）和点（x2, y2）形成的矩形区域里，有哪些设备被包括在这个区域里，
         * 被包括的设备组成一个ArrayList， 并返回ArrayList
         * @param x1 int
         * @param y1 int
         * @param x2 int
         * @param y2 int
         * @return ArrayList
         */
        public ArrayList find(int x1, int y1, int x2, int y2){
            ArrayList ar = new ArrayList();
            for (int i=0; i<array.size(); i++ ){
                if(((getDev(i).getX()-x1)*(getDev(i).getX()-x2))<0&&
                   ((getDev(i).getY()-y1)*(getDev(i).getY()-y2))<0)
                    ar.add(Integer.toString(i));
                else if(((getDev(i).getX()+width-x1)*(getDev(i).getX()+width-x2))<0&&
                   ((getDev(i).getY()+height-y1)*(getDev(i).getY()+height-y2))<0)
                    ar.add(Integer.toString(i));
            }
            return ar;
        }

        /**
         * 查找（x,y）点是否在设备的名称的显示范围内
         * @param x int
         * @param y int
         * @return int
         */
        public int findName(int x, int y)
        {
            for(int i=0; i<array.size();i++)
            {
                if (x >= getDev(i).getX() + getDev(i).getNameXY().x
                        &&x <= getDev(i).getX() + getDev(i).getNameXY().x + getDev(i).getName().length()*6
                        &&y <= getDev(i).getY() + getDev(i).getNameXY().y
                        &&y >= getDev(i).getY() + getDev(i).getNameXY().y - 9)
                return i;

            } 
            return -1;
        }

        /**
         * 改变设备的ID和名称，当ID重复时弹出提示框
         * @param parent JFrame
         * @param i int
         */
        public void changeName(JFrame parent ,int i )
        {
            Dialog changeDialog = new Dialog(parent, getDev(i).getID() , getDev(i).getName());
            changeDialog.show();
            if(changeDialog.getFlag())
            {
                String newname = changeDialog.getText2().trim();
                String newID = changeDialog.getText1().trim();
                if (!existID(newID, i))
                { 
                    getDev(i).setName(newname);
                    getDev(i).setID(newID);
                }else
                    JOptionPane.showMessageDialog( parent , "设备ID输入值重复!");
            }
        }

        /**
         * 查找输入的ID是否在数组中已经存在
         * @param id String
         * @return boolean 如果已经存在返回真，否则返回假
         */
        public boolean existID(String id)
        {
            for(int i=0; i<array.size(); i++)
            {
                if(id.equals(getDev(i).getID() ))
                    return true;
            }
            return false;
        }

        /**
         * 判断设备ID是否和数组中序号不为n的设备的ID相同，当相同就返回真，不同就返回假
         * @param id String
         * @param n int
         * @return boolean
         */
        public boolean existID(String id ,int n)
        {
            for(int i=0; i<array.size(); i++)
            {
                if(i != n)
                    if(id.equals(getDev(i).getID() ))
                        return true;
            }
            return false;
        }

        /**
         * 得到数组中序号为i的设备的数据,这些数据中包括了所有要存入数据库中的信息。
         * @param i int
         * @return String[]
         */
        public String[] getOneDevData(int i)
        {
            String[] values = new String[11];
            values[0] = dev_type;                                 //设备类型对应数据库里的dev_type_id
            values[1] = getDev(i).getID();                        //设备的ID对应数据库里的device_id
            values[2] = node_type;                                //设备的节点类型对应数据库里的node_type
            values[3] = description;           //设备的描述对应数据库里的description
            values[4] = img_url;               //设备的图片路径对应数据库里的image_url
            values[5] = getDev(i).getImg_direction();             //设备的图片方向对应数据库里的image_direction
            values[6] = getDev(i).getName();                      //设备的名称对应数据库里的设备的第2条记录的description
            values[7] = Integer.toString(getDev(i).getX());       //设备的X轴坐标对应数据库里的pos_x
            values[8] = Integer.toString(getDev(i).getY());       //设备的Y轴坐标对应数据库里的pos_y
            values[9] = Integer.toString(getDev(i).getNameXY().x); //设备的名称的X轴坐标对应数据库里的ID_x
            values[10] = Integer.toString(getDev(i).getNameXY().y);//设备的名称的Y轴坐标对应数据库里的ID_y

            //判断传给数据库的存储的数据中，同类型设备的ID时候有重复，没重复则返回字符串，有重复则存储操作失败
            if(!existID( getDev(i).getID(), i) )
                return values;
            else
            {
                JOptionPane.showMessageDialog(null , "存在重复的设备ID,存储数据库操作失败！");
                return null;
            }
        }
    }

    /**这个类是专门为闸机而建的，它能接受图片数组，和图片方向数组,作为构造函数的参数
     * 它的重构了父类的构造函数。
     */

    class AGMDev extends Device
    {
        private String[] urlArray , directionArray;
        ImageIcon[] imageArray;
        int[] widthArray,heightArray;
        public AGMDev(String[] imgUrl, String type, String[] direction)
        {
            super("",type);
            this.urlArray = imgUrl;
            directionArray = direction;
            //初始化图片数组.
            imageArray = new ImageIcon[urlArray.length];
           //初始化图片的宽度数组
            widthArray = new int[urlArray.length];
            //初始化图片的长度数组
            heightArray = new int[urlArray.length];

            for(int i=0; i < urlArray.length; i++)
            {
                imageArray[i] = new ImageIcon(urlArray[i]);
                widthArray[i] = imageArray[i].getIconWidth();
                heightArray[i] = imageArray[i].getIconHeight();
            }
        }

        /**
         * 画出数组里序号为i的设备, 先根据设备的图片方向代号,来获取对应的图片在图片数组中的序号n
         * @param g Graphics
         * @param i int
         */
        public void drawOne(Graphics g,int i)
        {
            int n = getImageFromArray( getDev(i).getImg_direction() );
            if(n >= 0)
            {
                g.drawImage(imageArray[n].getImage(), getDev(i).getX(), getDev(i).getY(), null);
                Font old = g.getFont();
                Font new1 = new Font("宋体",Font.PLAIN, 13);
                g.setFont(new1);
                g.drawString( getDev(i).getName(), getDev(i).getX() + getDev(i).getNameXY().x,
                              getDev(i).getY() + getDev(i).getNameXY().y );
                g.setFont(old);
            }
        }

        /**
         * 当闸机被选中时显示闸机四个角上的方格，和闸机的名称
         * @param g Graphics
         * @param i int
         */
        public void displayOne(Graphics g ,int i)
        {
            int n = getImageFromArray( getDev(i).getImg_direction() );
            int x = getDev(i).getX();
            int y = getDev(i).getY();
            if(n >= 0)
            {
                g.drawRect(x-2, y-2, 5, 5);
                g.drawRect(x-2, y+heightArray[n]-3, 5, 5);
                g.drawRect(x+widthArray[n]-3, y+heightArray[n]-3, 5, 5);
                g.drawRect(x+widthArray[n]-3, y-2, 5, 5);
                Font old = g.getFont();
                Font new1 = new Font("宋体",Font.PLAIN, 13);
                g.setFont(new1);
                g.drawString( getDev(i).getName(), getDev(i).getX() + getDev(i).getNameXY().x,
                              getDev(i).getY() + getDev(i).getNameXY().y );
                g.setFont(old);
            }
        }

        /**
         * 根据设备的图片方向代号,来获取对应的图片在图片数组中的序号i
         * @param direction String
         * @return int
         */
        public int getImageFromArray(String direction)
        {
            for(int i = 0;i < directionArray.length ; i++)
                if (direction.equals(directionArray[i]))
                    return i;
            return -1;
        }

        /**
         * 查找点( x, y )是否在某个设备的显示范围里
         * @param x int
         * @param y int
         * @return int
         */
        public int find(int x,int y)
        {
            for (int i=0; i<array.size();i++)
            { 
                int n = getImageFromArray( getDev(i).getImg_direction() );
                if (n > -1 && x > getDev(i).getX() && x < getDev(i).getX() + widthArray[n] &&
                    y > getDev(i).getY()
                    && y < getDev(i).getY() + heightArray[n]){
                    return i;
                }
            }
            return -1;
        }

        /**
         * 查找在点（x1, y1）和点（x2, y2）形成的矩形区域里，有哪些设备被包括在这个区域里，
         * 被包括的设备组成一个ArrayList， 并返回ArrayList
         * @param x1 int
         * @param y1 int
         * @param x2 int
         * @param y2 int
         * @return ArrayList
         */
        public ArrayList find(int x1, int y1, int x2, int y2){
            ArrayList ar = new ArrayList();
            for (int i=0; i<array.size(); i++ ){
                int n = getImageFromArray( getDev(i).getImg_direction() );
                if(((getDev(i).getX()-x1)*(getDev(i).getX()-x2))<0&&
                   ((getDev(i).getY()-y1)*(getDev(i).getY()-y2))<0)
                    ar.add(Integer.toString(i));
                else if(((getDev(i).getX()+widthArray[n]-x1)*(getDev(i).getX()+widthArray[n]-x2))<0&&
                   ((getDev(i).getY()+heightArray[n]-y1)*(getDev(i).getY()+heightArray[n]-y2))<0)
                    ar.add(Integer.toString(i));
            }
            return ar;
        }

        /**
         * 得到数组中序号为i的设备的数据,这些数据中包括了所有要存入数据库中的信息。
         * @param i int
         * @return String[]
         */
        public String[] getOneDevData(int i)
        {
            String[] values = new String[11];
            values[0] = dev_type;                                       //设备类型对应数据库里的dev_type_id
            values[3] = description;                 //设备的描述对应数据库里的description
        //    if(dev_type.equals("04") && getDev(i).getName().startsWith("O")){
        //        values[0] = "05";
        //        values[3] = "出站闸机";
        //    }
            values[1] = getDev(i).getID();                              //设备的ID对应数据库里的device_id
            values[2] = node_type;                                      //设备的节点类型对应数据库里的node_type
            int n = getImageFromArray( getDev(i).getImg_direction() );
            values[4] = urlArray[n];                  //设备的图片路径对应数据库里的image_url
            values[5] = getDev(i).getImg_direction();                    //设备的图片方向对应数据库里的image_direction
            values[6] = getDev(i).getName();                             //设备的名称对应数据库里的设备的第2条记录的description
            values[7] = Integer.toString(getDev(i).getX());              //设备的X轴坐标对应数据库里的pos_x
            values[8] = Integer.toString(getDev(i).getY());              //设备的Y轴坐标对应数据库里的pos_y
            values[9] = Integer.toString(getDev(i).getNameXY().x);       //设备的名称的X轴坐标对应数据库里的ID_x
            values[10] = Integer.toString( getDev(i).getNameXY().y );    //设备的名称的Y轴坐标对应数据库里的ID_y

            //判断传给数据库的存储的数据中，同类型设备的ID时候有重复，没重复则返回字符串，有重复则存储操作失败
            if(!existID( getDev(i).getID(), i) )
                return values;
            else
            {
                JOptionPane.showMessageDialog(null , "存在重复的设备ID,存储数据库操作失败！");
                return null;
            }
        }
    }
