/**
 * <p>Copyright: Copyright GuangZhou goldsign Co. 2005</p>
 * <p>Company: GuangZhou goldsign Co</p>
 */

package stationDraw;

import java.awt.*;
import java.util.ArrayList;
import stationDraw.Util.CharUtil;

/**
 * 这个类做为文本类, 它包括一个Word 对象数组
 * @author not attributable
 * @version 1.0
 */

public class TextArray
{
  /** 包含所有的文本对象的数组 */
 private ArrayList textAr;

 /**
  * 初始化文本对象的数组
  */
 public TextArray()
 {
    textAr = new ArrayList();
  }

 /**
  * 在对象数组中增加一个元素---Word 对象 ，
  * @param str String 文本的内容
  * @param size String 文本字体的大小
  * @param node_type String 文本的类型：04 静态文本， 05 动态文本
  * @param x int
  * @param y int
  * @return int 返回新添加的文本在数组中的序号
  */
 public int addText(String str, String size, String node_type, int x, int y)
{
  Word w = new Word(str, size, x, y);
   w.setNodeType(node_type);
   textAr.add(w);
   return textAr.size()-1;
 }

 /**
  * 在对象数组中删除序号为i的对象
  * @param i int
  */
 public void removeText(int i)
 {
   //当序号i没超过数组的大小时，才可以删除文本，否则不做操作。
   if(textAr.size() > i)
   {
     textAr.remove(i);
    }
 }

 /**
  * 返回在对象数组中序号为i的对象
  * @param i int 在数组中的位置序号
  * @return Word 返回的文本对象
  */
 public Word getText(int i)
{
  Word w = (Word) textAr.get(i);
  return w;
}

/**
 * 返回对象数组的长度
 * @return int
 */
public int getArrayLength()
{
  return textAr.size();
}

/**
 * 移动对象数组中序号为i的对象在显示面板的坐标
 * @param i int 文本对象在数组中的序号
 * @param x int 在X轴上移动的距离
 * @param y int 在Y轴上移动的距离
 */
public void moveText(int i,int x,int y)
 {
   getText(i).move(x, y);
 }
//对齐操作
 public void moveTextFulsh(int i,String direction,int xx,int yy){
     this.getText(i).moveFulsh(xx,yy,direction);
}


 /**
  * 把文本分成3种情况讨论：中文，字母，阿拉伯数字
  * 然后查找当前点(x, y)是否在文本的范围内，如果点(x, y)在文本的范围内，就返回该文本在数组中的序号。
  * @param x int
  * @param y int
  * @return int
  */
 public int findText(int x,int y)
 {
   for (int i = 0; i < textAr.size(); i++)
   {
     //当文本为字母的时候，那么文本的字母宽度为真实的大小的一半，字母的高度为真实大小的0.4倍
     if (getText(i).getWords().compareTo("a") >= 0 &&
         getText(i).getWords().compareTo("zzz") <= 0)
     {
       if (x >= getText(i).x &&
           x <= getText(i).x + getText(i).wordlength * getText(i).RealSize / 2 &&
           y >= getText(i).y - getText(i).RealSize * 0.4 &&
           y <= getText(i).y)
         return i;
     }
     //当文本为数字的时候，那么数字的宽度为真实大小的一半，字母的高度为真实大小的0.6倍
     else if (getText(i).getWords().compareTo("0") >= 0 &&
              getText(i).getWords().compareTo("100000") < 0)
     {
       if (x >= getText(i).x &&
           x <= getText(i).x + getText(i).wordlength * getText(i).RealSize / 2 &&
           y >= getText(i).y - getText(i).RealSize * 0.6 &&
           y <= getText(i).y)
         return i;
     }
     //其他情况，即文本为中文时
     else if (x >= getText(i).x &&
              x <= getText(i).x + getText(i).wordlength * getText(i).RealSize &&
              y >= getText(i).y - getText(i).RealSize * 0.6 &&
              y <= getText(i).y)
       return i;
   }
   return -1;
 }

 /**
  * 画出所有的文本对象
  * @param g Graphics
  */
 public void drawText(Graphics g)
{
  for(int i=0; i<textAr.size(); i++)
  getText(i).drawWord(g);
 }

 /**
  * 画出序号为i的文本对象
  * @param g Graphics
  * @param i int
  */
 public void drawOneText(Graphics g,int i)
{
   getText(i).drawWord(g);
 }

 /**
  * 得到一个文本对象的各个属性值，该方法主要用于把文本存储到数据库中的时候。
  * @param i int
  * @return String[]
  */
 public String[] getOneTextData(int i)
{
    String[] str = {
        "", "", "", "", "", "", ""};
    str[0] = getText(i).node_type;
    str[1] = getText(i).words;
    str[4] = getText(i).fontsize;
    str[5] = Integer.toString(getText(i).x);
    str[6] = Integer.toString(getText(i).y);
    return str;
  }
 /** 文本数组中的每个单元的文本对象的类 */
  class Word
  {
  /** 文本在画板中的位置，文本的字符个数 */
 //private int x,y,wordlength;
 public int x,y,wordlength;
 /** 文本 */
 private String words = null;

 /** 文本中的字体大小：1号、2号、3号、4号、5号、6号、7号 */
 private String fontsize;

 /** 用于数据库中区别文本和别的对象的标识 */
 private String node_type;

 /** 文本的真实大小，初始化为16 */
  int RealSize = 16;

  /**
   * 初始化一个新的文本对象，并初始化文本的长度，
   * @param word String 文本
   * @param size String 文本字体的大小
   * @param x int 文本的X轴位置
   * @param y int 文本的Y轴位置
   */
  public Word(String word, String size, int x, int y)
 {
   words = word;
   this.x = x ;
   this.y = y ;
   wordlength = word.length();
   fontsize = size;
   if ( fontsize.equals("1") )  RealSize=9;
   else if ( fontsize.equals("2") ) RealSize=12;
   else if ( fontsize.equals("3") )  RealSize=16;
   else if ( fontsize.equals("4") )  RealSize=18;
   else if ( fontsize.equals("5") )  RealSize=23;
   else if ( fontsize.equals("6") )  RealSize=28;
   else if ( fontsize.equals("7") )  RealSize=37;
 }

 /**
  * 设置文本的节点标识
  * @param str String
  */
 public void setNodeType(String str)
 {
  node_type = str;
 }

 /**
  * 画出文本：当文本是静态文本时，那么用普通的格式画文本
  * 当文本是动态文本时，那么用粗体加斜体的格式画文本
  * @param g Graphics
  */
 public void drawWord(Graphics g)
 {
   if(node_type.equals("03"))
     g.setFont(new Font("宋体", 0 , RealSize));
   else
     g.setFont(new Font("宋体", Font.BOLD+Font.ITALIC , RealSize));
   g.drawString(words, x, y);
  }

  /**
   * 把文本在X轴方向上移动dx,在Y轴方向上移动dy
   * @param dx int
   * @param dy int
   */
  public void move(int dx, int dy)
  {
    x += dx;
    y += dy;
  }
  //移动对齐设备
  public void moveFulsh(int x,int y,String direction){
    if (direction.equals("level"))
      this.y = y;
     else if(direction.equals("vertical"))
       this.x = x;
  }

  /**
   * 返回文本的内容
   * @return String
   */
  public String getWords()
  {
    return words;
  }
 }
}
