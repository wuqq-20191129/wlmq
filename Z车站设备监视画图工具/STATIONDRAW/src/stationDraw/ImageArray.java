/**
 * <p>Copyright: Copyright GuangZhou goldsign Co. 2005</p>
 * <p>Company: GuangZhou goldsign Co</p>
 */

package stationDraw;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * <p>Title: OneImage</p>
 * <p>Description: OneImage is a imageArray's element, it express one image</p>
 * @author zhanxiaoxin
 * @version 1.0
 */
class OneImage
{
    /** 图片的路径 */
    String url;
    /** 图片的左上角的坐标和图片的宽、高 */
    int x, y, imageW, imageH;
    /** 图片的对象 */
    ImageIcon image;

    /**
     * 初始化一个图片的对象.
     * @param path String
     * @param x int
     * @param y int
     */
    public OneImage(String path, int x, int y)
    {   
        url = path;
        this.x = x ;
        this.y = y ;
        image = new ImageIcon(url);
        imageW = image.getIconWidth();
        imageH = image.getIconHeight();
    }

    /**
     * 在面板上画出图片
     * @param g Graphics
     */
    public void drawimage(Graphics g)
    {
        g.drawImage(image.getImage(), x, y, null);
    }
}

/**
 * the class contained all 'url' and coordinate of image
 * <p>Title: </p>
 * <p>Description: </p>
 * @author zhanxiaoxin
 */
public class ImageArray
{
    /** the imgArray contains all the image */
    private ArrayList imgArray;
    /** 放置图片的文件夹路径 */
    private static String imgDir = "images/";
    /** 图片的node_type为02 */
    public static String node_type="02";  //图片类型：0为静态图片，1为动态图片

    /** initialize the image's array */
    public ImageArray()
    {
        imgArray = new ArrayList();
    }

    /**
     * 往图片数组里添加一个新的图片。如果添加成功就返回图片在数组中的位置，否则返回-1
     * @param path String
     * @param x int
     * @param y int
     * @return int the new image's number in the imageArrray
     */
    public int addimage(String path, int x, int y)
    {
        if(path.toLowerCase().endsWith(".jpg")||path.toLowerCase().endsWith(".gif"))
        {
            //当是从数据库中读入一个图片数据时，path为图片的文件名，而当从文件夹里插入一个图片到界面上时，
            //path为图片的绝对路径，所以要把图片的绝对路径变成图片的文件名。
            int n = path.lastIndexOf("\\");
            path = path.substring(n+1);
            //imgDir表示图片文件夹的路径，imgDir+path表示图象对象的路径。
            OneImage img = new OneImage( imgDir+path, x, y);
            imgArray.add(img);
            return imgArray.size() - 1;
        }
        else 
            return -1;
    }

    /**
     * delete element[i] from the image arry
     * @param i int
     */
    public void removeimage(int i)
    {
        if(imgArray.size()>i)
            imgArray.remove(i);
    }

    /**
     * return OneImage object which is element[i] in imageArray
     * @param i int
     * @return OneImage
     */
    public OneImage getOneImage(int i) {
        if(imgArray.size() >= i){
            OneImage img = (OneImage) imgArray.get(i);
            return img;
        }
        else {
            System.out.println("size:"+imgArray.size()+"  i: "+i);
            return null;
        }
     }

    /**
     * return the size of current array
     * @return int
     */
    public int getArrayLength()
    {
        return imgArray.size();
    }

    /**
     * move image which is element[i] in imageArray
     * @param i int the number in imageArray
     * @param x int 在X方向上移动x
     * @param y int 在Y方向上移动y
     */
    public void moveimage(int i,int x,int y)
    {
        getOneImage(i).x += x;
        getOneImage(i).y += y;
    }
    //对齐操作
      public void moveimageFulsh(int i,int x,int y,String direction)
    {
        if (direction.equals("level")){
            getOneImage(i).y = y;
        }
        else if(direction.equals("vertical")){
            getOneImage(i).x = x;
        }
    }

    /**
     * 查找点(x, y)是否存在图片，如果存在就返回图片在数组中的序号，如果不存在就返回-1
     * @param x int
     * @param y int
     * @return int
     */
    public int findimage(int x, int y)
    {
        for(int i = 0; i < imgArray.size(); i++)
        {
            //判断（x, y）是否在图片的显示范围内
            if ( x >= getOneImage(i).x && x <= getOneImage(i).x + getOneImage(i).imageW
            && y >= getOneImage(i).y && y <= getOneImage(i).y +getOneImage(i).imageH )
            return i;
        }
        return -1;
    }

    /**
     * 在点(x1, y1)和点(x2, y2)构成的矩形区域里，查找有哪些图片被包含在这个范围内，
     * 如果有图片包含在这个范围内，就把这些图片在数组中的序号i存储到ArrayList中，并返回ArrayList
     * @param x1 int
     * @param y1 int
     * @param x2 int
     * @param y2 int
     * @return ArrayList
     */
    public ArrayList find(int x1, int y1, int x2, int y2){
        ArrayList ar = new ArrayList();
            for(int i=0; i<imgArray.size(); i++){
                if((getOneImage(i).x-x1)*(getOneImage(i).x-x2)<0 &&
                   (getOneImage(i).y-y1)*(getOneImage(i).y-y2)<0)
                    ar.add(Integer.toString(i));
                 else if((getOneImage(i).x+getOneImage(i).imageW-x1)*(getOneImage(i).x+getOneImage(i).imageW-x2)<0
                         && (getOneImage(i).y+getOneImage(i).imageH-y1)*(getOneImage(i).y+getOneImage(i).imageH-y2)<0)
                    ar.add(Integer.toString(i));
            }
        return ar;
    }

    /**
     * 根据x,y 改变图片的位置
     * @param i int
     * @param x int
     * @param y int
     */
    public void changeimage(int i,int x,int y)
    {
        getOneImage(i).x = x;
        getOneImage(i).y = y;
    }

    /**
     * 画出图片数组中所有的图片
     * @param g Graphics
     */
    public void drawAllImage(Graphics g)
    {
        for (int i = 0; i < imgArray.size(); i++)
            getOneImage(i).drawimage(g);
    }

    /**
     * 画出图片数组中序号为i的图片
     * @param g Graphics
     * @param i int 图片在数组中的位置。
     */
    public void drawOneImage(Graphics g, int i)
    {
        getOneImage(i).drawimage(g);
    }

    /**
     * 返回图片的数据，把图片的文件名和图片的位置存到一个数组里，然后返回这个数组。
     * 当把图片的数据存到数据库中，调用此函数来存储图片的数据。
     * @param i int
     * @return String[]
     */
    public String[] getOneImageData( int i )
    {
        //图片的url为image/... 形式，但是在数据库里应只存入图片的文件名，所以要把前面的相对路径去掉
        int n = getOneImage(i).url.lastIndexOf("/");
        String s = getOneImage(i).url.substring(n+1);

        String[] values = { "","","" };
        //图片的文件名
        values[0] =  s ;
        //返回图片的X的坐标
        values[1] = Integer.toString( getOneImage(i).x );
        //返回图片的Y的坐标
        values[2] = Integer.toString( getOneImage(i).y );
        return values;
    }
}
