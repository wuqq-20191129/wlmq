/**
 * <p>Copyright: Copyright GuangZhou goldsign Co. 2005</p>
 * <p>Company: GuangZhou goldsign Co</p>
 */

package stationDraw;

import java.awt.*;
import javax.swing.*;
import java.util.ArrayList;
import stationDraw.Util.ReadWriteXml;

public class DrawPanel extends JPanel {
    /**  该对象包含所有的设备的对象数组。 */
    public DevicesDao devicesDao;

    /**  IM为包含图片数组的对象  */
    public ImageArray IM;

    /**  currentT为当前被选中的设备数组，其中以Point为基本单元，x为设备的类型，y为设备数组中的序号  */
    public ArrayList currentT = new ArrayList();

    /**  text为包含文本数组的对象  */
    public TextArray text;

    /**  用户在drawpanel上选择的区域  */
    public Rectangle area= null;

    /**  current为在drawpanel上点击鼠标选中的设备的序号  */
    private int current;

    /** devsource为在drawpanel上点击鼠标选中的设备的类型，
     * namesrc为是否选中名称的标志(1为选中设备名，0为选中设备的整体) */
    private int devsource=0, namesrc=0;
    //private int framew=1024,frameh=768;
    public DrawPanel(){
        devicesDao=new DevicesDao();
        IM=devicesDao.img;
        text=devicesDao.text;
        this.setSize(ReadWriteXml.screenW,ReadWriteXml.screenH);
        super.setBackground(new Color(190,190,190));
    }

    /**
     * 在面板上画出所有的设备，线条，图片，文本。还有当前被选中的设备。
     * @param g Graphics
     */
    public  void paintComponent(Graphics g)
    {
        super.paintComponent(g);

        g.setColor(Color.black);
        devicesDao.lineAr.drawAll(g);
        devicesDao.eg.drawAll(g);
        devicesDao.xeg.drawAll(g);
        devicesDao.twg.drawAll(g);
        devicesDao.bom.drawAll(g);
        devicesDao.pca.drawAll(g);
        devicesDao.tvm.drawAll(g);
        devicesDao.itm.drawAll(g);
        devicesDao.aqm.drawAll(g);
        IM.drawAllImage(g);
        Font old=g.getFont();
        text.drawText(g);
        g.setFont(old);
        if( MainFrame.CURRENTSELECT.size()>0 )
            demo(g, MainFrame.CURRENTSELECT);
        if( MainFrame.CtrlSELECT.size()>0 )
            demo(g, MainFrame.CtrlSELECT);
        if(area!= null)
            g.drawRect(area.x, area.y, area.width, area.height);

    }

    /**
     * 判断当前点(x, y)是在哪个设备上，还有是否在一个名称上
     */
    public boolean find(int x,int y)
    {
        if ((current=devicesDao.eg.findName(x,y))>=0)
            { devsource=AppConstant.source_button_eg; namesrc=1;}  //当找到的是进站闸机eg，标志source为02
        else if ((current=devicesDao.eg.find(x,y))>=0)
            { devsource=AppConstant.source_button_eg; namesrc=0; }

        else if ((current=devicesDao.xeg.findName(x,y))>=0)
            { devsource=AppConstant.source_button_xeg; namesrc=1;}     //当找到的是出站闸机xeg
        else if ((current=devicesDao.xeg.find(x,y))>=0)
            { devsource=AppConstant.source_button_xeg; namesrc=0;}

        else if ((current=devicesDao.twg.findName(x,y))>=0)
            { devsource=AppConstant.source_button_twg;  namesrc=1;}   //当找到的是双向闸机twg
        else if ((current=devicesDao.twg.find(x,y))>=0)
            { devsource=AppConstant.source_button_twg; namesrc=0;}

        else if ((current=devicesDao.bom.findName(x,y))>=0)
            {devsource=AppConstant.source_button_bom;  namesrc=1;}  //当找到的是BOM
        else if((current=devicesDao.bom.find(x,y))>=0)
            {devsource=AppConstant.source_button_bom; namesrc=0;}

        else if((current=devicesDao.tvm.findName(x,y))>=0)
            { devsource=AppConstant.source_button_tvm; namesrc=1;}   //当找到的是TVM
        else if((current=devicesDao.tvm.find(x,y))>=0)
            {devsource=AppConstant.source_button_tvm; namesrc=0;}

        else if ((current=devicesDao.pca.findName(x,y))>=0)
            { devsource=AppConstant.source_button_pca; namesrc=1;}     //当找到的是PCA
        else if ((current=devicesDao.pca.find(x,y))>=0)
            { devsource=AppConstant.source_button_pca; namesrc=0;}
      
        else if ((current=devicesDao.itm.findName(x,y))>=0)
            { devsource=AppConstant.source_button_itm; namesrc=1;}     //当找到的是ITM
        else if ((current=devicesDao.itm.find(x,y))>=0)
            { devsource=AppConstant.source_button_itm; namesrc=0;}
        
        else if ((current=devicesDao.aqm.findName(x,y))>=0)
            { devsource=AppConstant.source_button_aqm; namesrc=1;}     //当找到的是AQM
        else if ((current=devicesDao.aqm.find(x,y))>=0)
            { devsource=AppConstant.source_button_aqm; namesrc=0;}
        
        else if ((current=devicesDao.lineAr.find(x,y))>=0)
            { devsource=AppConstant.source_button_line; namesrc=0; }    //当找到的是线条
        
        else if ((current=text.findText(x,y))>=0)
            { devsource=AppConstant.source_button_word;namesrc=0; }    //当找到的是文本
        
        else if ((current=IM.findimage(x,y))>=0)
            { devsource=AppConstant.source_button_pic;namesrc=0; }   //当找到是图片
        
        if (current>=0)  return true;
        
        else return  false;
    }

    /**
     * 找出以(x1, y1)和(x2, y2)两点构成的矩形区域里包含的设备。
     * @return boolean 当在矩形区域里找到设备就返回真，没有找到设备的时候就返回假
     */
    public boolean find(int x1, int y1, int x2, int y2){
        ArrayList cur = new ArrayList();

        //此时temp数组为在该区域中找到的BOM设备在BOM数组中的序号。
        ArrayList temp = devicesDao.bom.find(x1, y1, x2, y2);
        if(temp.size()>0){
            for (int i = 0; i < temp.size(); i++) {
                String num = (String) temp.get(i);
                Point p = new Point(AppConstant.source_button_bom, Integer.parseInt(num));
                cur.add(p);
            }
        }

        //此时temp数组为在该区域中找到的TVM设备在TVM数组中的序号。
        temp = devicesDao.tvm.find(x1, y1, x2, y2);
        if(temp.size()>0){
            for (int i = 0; i < temp.size(); i++) {
                String num = (String) temp.get(i);
                Point p = new Point(AppConstant.source_button_tvm, Integer.parseInt(num));
                cur.add(p);
            }
        }

        //此时temp数组为在该区域中找到的TCM/PCA设备在TCM数组中的序号。
        temp = devicesDao.pca.find(x1, y1, x2, y2);
        if(temp.size()>0){
            for (int i = 0; i < temp.size(); i++) {
                String num = (String) temp.get(i);
                Point p = new Point(AppConstant.source_button_pca, Integer.parseInt(num));
                cur.add(p);
            }
         }

        //此时temp数组为在该区域中找到的ITM设备在ITM数组中的序号。
        temp = devicesDao.itm.find(x1, y1, x2, y2);
        if(temp.size()>0){
            for (int i = 0; i < temp.size(); i++) {
                String num = (String) temp.get(i);
                Point p = new Point(AppConstant.source_button_itm, Integer.parseInt(num));
                cur.add(p);
            }
         }

        //此时temp数组为在该区域中找到的进站闸机在agm数组中的序号。
        temp = devicesDao.eg.find(x1, y1, x2, y2);
        if(temp.size()>0){
            for (int i = 0; i < temp.size(); i++) {
                String num = (String) temp.get(i);
                Point p = new Point(AppConstant.source_button_eg, Integer.parseInt(num));
                cur.add(p);
            }
        }


        //此时temp数组为在该区域中找到的出站闸机在agmOut数组中的序号。
        temp = devicesDao.xeg.find(x1, y1, x2, y2);
        if(temp.size()>0){
            for (int i = 0; i < temp.size(); i++) {
                String num = (String) temp.get(i);
                Point p = new Point(AppConstant.source_button_xeg, Integer.parseInt(num));
                cur.add(p);
            }
        }

        //此时temp数组为在该区域中找到的双向闸机在doubleAgm数组中的序号。
        temp = devicesDao.twg.find(x1, y1, x2, y2);
        if(temp.size()>0){
            for (int i = 0; i < temp.size(); i++) {
                String num = (String) temp.get(i);
                Point p = new Point(AppConstant.source_button_twg, Integer.parseInt(num));
                cur.add(p);
            }
         }

        //此时temp数组为在该区域中找到的AQM设备在ITM数组中的序号。
        temp = devicesDao.aqm.find(x1, y1, x2, y2);
        if(temp.size()>0){
            for (int i = 0; i < temp.size(); i++) {
                String num = (String) temp.get(i);
                Point p = new Point(AppConstant.source_button_aqm, Integer.parseInt(num));
                cur.add(p);
            }
         }
        //此时temp数组为在该区域中找到的图片在图片数组中的序号。
        temp = IM.find(x1, y1, x2, y2);
        if(temp.size()>0){
            for(int i = 0; i<temp.size(); i++){
                String num = (String) temp.get(i);
                Point p = new Point(AppConstant.source_button_pic, Integer.parseInt(num));
                cur.add(p);
            }
        }

        //此时temp数组为在该区域中找到的线条在线条数组中的序号。
        temp = devicesDao.lineAr.find(x1, y1, x2, y2);
        if(temp.size()>0){
            for(int i = 0; i<temp.size(); i++){
                String num = (String) temp.get(i);
                Point p = new Point(AppConstant.source_button_line, Integer.parseInt(num));
                cur.add(p);
            }
        }

        if(cur.size()>0){
            currentT = cur;
            return true;
        }
        else 
            return false;
    }


    /**
    * 根据当前选中的设备，在drawpanel中把他们显示出来。
    * 表示设备选中的颜色用蓝色。
    * @param g Graphics
    * @param typeAr ArrayList typeAr中存储的单元是Point(x,y) x代表设备的类型，
    * y代表设备在数组中的序号。
    */
    public void demo(Graphics g ,ArrayList typeAr)
    {
        if(typeAr != null )
        for(int i=0; i<typeAr.size(); i++){
            int type = ((Point) typeAr.get(i)).x;
            int num =  ((Point) typeAr.get(i)).y;
            Color c = g.getColor();
            g.setColor(Color.blue);
            if (type > 0) {
                switch (type) {

                //画出当前的线条
                case 11:
                    devicesDao.lineAr.drawOneXiant(g, num);
                    break;

                //画出当前的文本
                case 12:
                    text.drawOneText(g, num);
                    break;

                  //画出在图片四周的区域
                case 13:
                    g.drawRect(IM.getOneImage(num).x - 2, IM.getOneImage(num).y - 2, 5,
                               5);
                    g.drawRect(IM.getOneImage(num).x + IM.getOneImage(num).imageW - 3,
                               IM.getOneImage(num).y - 2, 5, 5);
                    g.drawRect(IM.getOneImage(num).x - 2,
                               IM.getOneImage(num).y + IM.getOneImage(num).imageH - 3,
                               5, 5);
                    g.drawRect(IM.getOneImage(num).x + IM.getOneImage(num).imageW - 3,
                               IM.getOneImage(num).y + IM.getOneImage(num).imageH - 3,
                               5, 5);
                    break;

                    //画出设备
                    default:
                        if (((type >= 1 && type <= 10))||(type > 14))
                            devicesDao.drawOne(type, g, num);
                        break;

                }
            }
            g.setColor(c);
        }
    }


    public int getcurrent()
    {
        return current;
    }

    public int getsource()
    {
        return  devsource;
    }
    /**  返回矩形区域中选中的设备所构成的数组  */
    public ArrayList getNumArray(){
        return currentT;
    }

    public int getnamesrc()
    {
        return  namesrc;
    }

    /**
     * 新建对象，并从数据库中相应的线路ID和车站ID的车站里取出设备的信息
     * @param line_name String
     * @param station_name String
     * @throws Exception
     */
    public void open (String line_name,String station_name) throws Exception
    {
        devicesDao = new DevicesDao();
        IM = devicesDao.img;
        text = devicesDao.text;
        devicesDao.openData(line_name, station_name);
        repaint();
    }

    /**
     * 把devicesDao对象中设备的信息存储到数据库中相应的线路ID和车站ID的车站里
     * @param line_name String
     * @param station_name String
     */
    public void save(String line_name,String station_name)
    {
        try
        {
            devicesDao.saveData(line_name, station_name);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
