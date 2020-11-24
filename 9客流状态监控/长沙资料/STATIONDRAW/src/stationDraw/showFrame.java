package stationDraw;

import java.awt.*;
import javax.swing.UIManager;
import javax.swing.plaf.FontUIResource;
import stationDraw.Util.CharUtil;
import stationDraw.Util.ReadWriteXml;


public class showFrame {
  public static void main(String[] args){

    try {
      //把界面的风格设置为当前系统所用的风格。
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

      //设置界面的字体。
      FontUIResource f = new FontUIResource("宋体", Font.PLAIN, 12);
      CharUtil.setUIFont(f);

      mainframe frame = new mainframe();

      //屏幕尺寸的大小
      Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

      //应用程序的显示框架的大小
      Dimension frameSize = frame.getSize();
      frameSize.height = ReadWriteXml.screenH;
      //frameSize.width=798;
      frame.setSize(ReadWriteXml.screenW,ReadWriteXml.screenH);
      frameSize.width = ReadWriteXml.screenW;
      
      if (frameSize.height > screenSize.height) {
        frameSize.height = screenSize.height;
      }
      if (frameSize.width > screenSize.width) {
        frameSize.width = screenSize.width;
      }
      
      //把应用程序显示在屏幕的中心位置。
      frame.setLocation( (screenSize.width - frameSize.width) / 2,(screenSize.height - frameSize.height) / 2);
      frame.setVisible(true);
     // frame.pack();
      frame.show();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }
}
