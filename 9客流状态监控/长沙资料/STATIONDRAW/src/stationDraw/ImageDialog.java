/**
 * <p>Copyright: Copyright GuangZhou goldsign Co. 2005</p>
 * <p>Company: GuangZhou goldsign Co</p>
 */

package stationDraw;

import java.awt.*;
import javax.swing.JFileChooser;
import javax.swing.JFileChooser.*;
import javax.swing.filechooser.FileFilter;
import java.io.File;
import java.beans.*;
import javax.swing.*;

/**
 * <p>Title: Image Dialog</p>
 * <p>Description: The ImageDialog class is used for pop up a dialog
 * for inserting new image.</p>
 * @author zhanxiaoxin
 * @version 1.0
 */
public class ImageDialog
{
  /** 图片选择对话框的文件选择器 */
  JFileChooser imgfile;

  /** 图片选择对话框的过滤器，用来过滤掉除GIF，JPG文件的其他文件。 */
  imgFilter fi=new imgFilter();

  /** 图象预览面板 */
  FilePreviewer previewer;

  /**
   * 初始化文件选择器，并给文件选择器加上图象预览面板，给选择器加上过滤器
   */
  public ImageDialog()
  {
    imgfile = new JFileChooser();
    //初始化预览面板
    previewer = new FilePreviewer(imgfile);
    //给文件选择器加上预览面板
    imgfile.setAccessory(previewer);
    //设置文件选择器的当前目录
    imgfile.setCurrentDirectory(new File("./images"));
    //给文件选择器加上过滤器
    imgfile.setFileFilter(fi);
  }

  /**
   * 显示图片选择对话框
   * @param co Component
   * @return int
   */
  public int showOpen(Component co)
 {
   return imgfile.showOpenDialog(co);
  }

  /**
   * 返回文件选择器选中的文件
   * @return File
   */
  public File getfile()
 {
   return imgfile.getSelectedFile();
 }

//图象文件的过滤器(只能看到gif , jpeg, jpg 文件)
class imgFilter extends FileFilter
{
  /**
   * 文件过滤器通过accept()来判断文件是否应该被过滤掉
   * @param f File
   * @return boolean
   */
  public boolean accept(File f)
  {
    return f.getName().toLowerCase().endsWith(".gif")
        || f.getName().toLowerCase().endsWith(".jpg")
        || f.isDirectory();
  }

  /**
   * 该方法做为文件选择对话框的对文件类型的描述。
   * @return String
   */
  public String getDescription()
    {
      return "Image File(*.gif,*.jpg)";
    }
}
  /** 该类是一个图象预览板的类。*/
  class FilePreviewer extends JComponent implements PropertyChangeListener
  {
      /** 当前被选中的图象文件的图象。*/
      ImageIcon thumbnail = null;

      /** 设置文件预览面板的大小。 */
      public FilePreviewer(JFileChooser fc)
      {
          setPreferredSize(new Dimension(100, 50));
          fc.addPropertyChangeListener(this);
      }

      /** 导入当前选择文件的图象 */
      public void loadImage(File f)
      {
          if (f == null)
          {
              thumbnail = null;
          }
          else
          {
              //把当前选择的文件的图象导入到tmpIcon中
              ImageIcon tmpIcon = new ImageIcon(f.getPath());
              //如果图象的宽度大于90的话，就按比例缩小，把宽度缩小到90
              if(tmpIcon.getIconWidth() > 90)
              {
                thumbnail = new ImageIcon(
                  tmpIcon.getImage().getScaledInstance(90, -1, Image.SCALE_DEFAULT));
              }
              else
              {
                thumbnail = tmpIcon;
              }
          }
      }

/**  对话框的监听选择文件的改变，当文件改变了，图象预览板就重绘 */
      public void propertyChange(PropertyChangeEvent e)
      {
          String prop = e.getPropertyName();
          if(prop == JFileChooser.SELECTED_FILE_CHANGED_PROPERTY) {
              if(isShowing()) {
                  loadImage((File) e.getNewValue());
                  repaint();
              }
          }
      }

/**  绘出预览板上的图象  */
      public void paint(Graphics g)
      {
          if(thumbnail != null)
          {
            //控制图象的位置，把图象在预览面板的中间位置显示。
              int x = getWidth()/2 - thumbnail.getIconWidth()/2;
              int y = getHeight()/2 - thumbnail.getIconHeight()/2;
              if(y < 0)
              {
                  y = 0;
              }
              if(x < 5)
              {
                  x = 5;
              }
              thumbnail.paintIcon(this, g, x, y);
          }
      }
  }
}
