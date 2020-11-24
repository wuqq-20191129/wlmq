/*
 * 文件名：PreviewUtil
 * 版权：Copyright: goldsign (c) 2013
 */

package com.goldsign.ecpmcs.util;

import com.goldsign.ecpmcs.env.AppConstant;
import com.goldsign.ecpmcs.env.ConfigConstant;
import com.goldsign.ecpmcs.ui.panel.ImagePanel;
import com.goldsign.ecpmcs.vo.AnalyzeVo;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.print.PageFormat;
import java.awt.print.PrinterJob;
import java.io.File;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;


/*
 * 〈组合预览控件〉
 * @author     lindaquan
 * @version    V1.0
 * @createTime 2014-5-19
 */

public class PreviewUtil {
//    private static org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(PreviewUtil.class.getName());
    private static double x = 2.5;
    private static double wordx = 1.8;//打印机API尺寸与java图形尺寸 比例系数
    private static double wordy = 2.79;
    private static double xf = 1.7;//打印机API与java图形字体大小 比例系数
    
    /*
     * 设置样版
     */
    public static void setPreviewTemptale(List config, JPanel previewjPanel, ImagePanel tempPanel) {
        
        previewjPanel.remove(tempPanel);
        tempPanel.removeAll();
        tempPanel.setLayout(null);
        
        for(int i=0; i<config.size(); i++){
            String line = (String) config.get(i);
            String[] data = line.split(AppConstant.WELL_SIGN, -1);
            //背景
            if(data[0].equals(ConfigConstant.BACKGROUND)){
//                tempPanel.setRotate(tempPanel.getRotate()-Math.PI/2); 
                ImageIcon tempIcon = printerSet(data); 
                tempPanel.setBackground(tempIcon.getImage());
                tempPanel.setPreferredSize(new Dimension(tempIcon.getIconWidth(), tempIcon.getIconHeight()));
            }
            //图片
            if(data[0].equals(ConfigConstant.IMAGE)){
                ImageIcon im = new ImageIcon(data[3]);
                im.setImage(im.getImage().getScaledInstance((int)(Integer.valueOf(data[6])/x), (int)(Integer.valueOf(data[7])/x), Image.SCALE_DEFAULT));
                JLabel l_pic = new JLabel(im);
                l_pic.setBounds((int)(Integer.valueOf(data[4])/x), (int)(Integer.valueOf(data[5])/x), 
                        (int)(Integer.valueOf(data[6])/x), (int)(Integer.valueOf(data[7])/x));
                tempPanel.add(l_pic);
            }
            //文字
            if(data[0].equals(ConfigConstant.TEXT)){
                //JLabel l_pic = new JLabel(data[2]);
                JLabel l_pic = new JLabel("");
                l_pic.setBounds((int)(Integer.valueOf(data[7])/x),(int)(Integer.valueOf(data[8])/x), 
                        (int)(Integer.valueOf(data[9])/x), (int)(Integer.valueOf(data[10])/x));
                l_pic.setFont(new java.awt.Font(data[3],Integer.valueOf(data[5]),(int)(Integer.valueOf(data[4])*xf)));
                l_pic.setForeground(new Color(Integer.valueOf(data[6])));
                tempPanel.add(l_pic);
            }
        }
        
        tempPanel.updateUI();
        BorderLayout borderLayout1 = new BorderLayout();
        previewjPanel.setLayout(borderLayout1);
        previewjPanel.setPreferredSize(new Dimension((int)tempPanel.getPreferredSize().getWidth(),(int)tempPanel.getPreferredSize().getHeight()));
        previewjPanel.add(tempPanel);
        previewjPanel.updateUI();
    }
    
    
    /*
     * 读卡时显示预览
     */
    public static void setPreviewPrint(List config, JPanel previewjPanel, ImagePanel tempPanel, AnalyzeVo analyzeVo) {
        
        tempPanel.removeAll();
        tempPanel.setLayout(null);
        
        String cardTypeLine = (String) config.get(0);
        String[] lineData = cardTypeLine.split(AppConstant.WELL_SIGN, -1);
        //判断是否是临时员工出入证正面模板
        String card_type = lineData[1].toString();
        boolean isPassFlag = "0504".equals(card_type)?true:false;
        
        for(int i=0; i<config.size(); i++){
            String line = (String) config.get(i);
            String[] data = line.split(AppConstant.WELL_SIGN, -1);
            //背景
            if(data[0].equals(ConfigConstant.BACKGROUND)){
                ImageIcon tempIcon = printerSet(data); 
                tempPanel.setBackground(tempIcon.getImage());
                tempPanel.setPreferredSize(new Dimension(tempIcon.getIconWidth(), tempIcon.getIconHeight()));
            }
            //图片
            File file = new File(analyzeVo.getImgDir());
            if(data[0].equals(ConfigConstant.IMAGE) && data[1].equals(ConfigConstant.IDPHOTO)
                    && file.exists() && !file.isDirectory()){
                ImageIcon im = new ImageIcon(analyzeVo.getImgDir());
                im.setImage(im.getImage().getScaledInstance((int)(Integer.valueOf(data[6])/x), (int)(Integer.valueOf(data[7])/x), Image.SCALE_DEFAULT));
                JLabel l_pic = new JLabel(im);
                l_pic.setBounds((int)(Integer.valueOf(data[4])/x), (int)((Integer.valueOf(data[5])-5)/x), 
                        (int)(Integer.valueOf(data[6])/x), (int)(Integer.valueOf(data[7])/x));
                tempPanel.add(l_pic);
            }
            //文字 -姓名
            if(data[0].equals(ConfigConstant.TEXT) && data[1].equals(ConfigConstant.NAME)
                    && !analyzeVo.getCertificateName().isEmpty()){
                String employee_name = analyzeVo.getCertificateName().trim();
                employee_name = formatNameStr(employee_name);
                int lengthName = count(employee_name);
                int yLine = Integer.valueOf(data[8]);
                int xLine = Integer.valueOf(data[7]);
                int fontSize = Integer.valueOf(data[4]);
                int width = Integer.valueOf(data[9]);
                if(lengthName>8){//名字长度大于8位，调整Y轴高度
                    fontSize -=1;
                    width += 200;
                    xLine -= 60;
                }else if(lengthName>4&&lengthName<=8){//正常显示

                }else{//名字长度小于等于4位，每位中间加空格
                    employee_name = appendSpace(employee_name);
                }
                JLabel l_pic = new JLabel(employee_name);
                l_pic.setBounds((int)(xLine/wordx),(int)(yLine/wordy)+20, 
                        (int)(width/x), (int)(Integer.valueOf(data[10])/x));
                l_pic.setFont(new java.awt.Font(data[3],Integer.valueOf(data[5]),(int)(fontSize*xf)));
                l_pic.setForeground(new Color(Integer.valueOf(data[6])));
                tempPanel.add(l_pic);
            }
            //文字 - 部门
            if(data[0].equals(ConfigConstant.TEXT) && data[1].equals(ConfigConstant.DEPARTMENT)
                    && !analyzeVo.getEmployeeDepartment().isEmpty()){
                String department_temp = PubUtil.getMapString(AppConstant.EMPLOYEE_DEPARTMENT, analyzeVo.getEmployeeDepartment().trim());
                department_temp = formatNameStr(department_temp);
                int lengthName = count(department_temp);
                int yLine = Integer.valueOf(data[8]);
                int xLine = Integer.valueOf(data[7]);
                int fontSize = Integer.valueOf(data[4]);
                int width = Integer.valueOf(data[9]);
                if(lengthName>8){//名字长度大于8位，调整Y轴高度
                    fontSize -=1;
                    width += 200;
                    xLine -= 60;
                }else if(lengthName>4&&lengthName<=8){//正常显示

                }else{//名字长度小于等于4位，每位中间加空格
                    department_temp = appendSpace(department_temp);
                }
                JLabel l_pic = new JLabel(department_temp);
                l_pic.setBounds(
                        (int)(xLine/wordx),(int)((yLine+70)/wordy), 
                        (int)(width/x), (int)(Integer.valueOf(data[10])/x));
                l_pic.setFont(new java.awt.Font(data[3],Integer.valueOf(data[5]),(int)(fontSize*xf)));
                l_pic.setForeground(new Color(Integer.valueOf(data[6])));
                tempPanel.add(l_pic);
            }
            //文字 - 员工号
            if(data[0].equals(ConfigConstant.TEXT) && data[1].equals(ConfigConstant.EMPLOYEEID)
                    && !analyzeVo.getCertificateCode().isEmpty()){
                
                int xLine = Integer.valueOf(data[7]);
                int yLine = Integer.valueOf(data[8]);
                if(isPassFlag){
                    xLine = 340;
                    yLine = -35;
                }
                JLabel l_pic = new JLabel(analyzeVo.getCertificateCode().trim());
                l_pic.setBounds((int)(xLine/wordx),(int)((yLine+80)/wordy), 
                        (int)(Integer.valueOf(data[9])/x), (int)(Integer.valueOf(data[10])/x));
                l_pic.setFont(new java.awt.Font(data[3],Integer.valueOf(data[5]),(int)(Integer.valueOf(data[4])*xf)));
                l_pic.setForeground(new Color(Integer.valueOf(data[6])));
                tempPanel.add(l_pic);
            }
            
        }
        
        tempPanel.updateUI();
        previewjPanel.updateUI();
    }
    
    /*
     * 打印机纵向或横向判断
     */
    private static ImageIcon printerSet(String[] data){
        
        String path = System.getProperty("user.dir");
        path += "/" + ConfigConstant.PRINT_BACKGROUND_PIC_PATH;
        path += data[1];
        
        PrinterJob pj = PrinterJob.getPrinterJob();
        PageFormat pf = pj.defaultPage();
        
        if(pf.getOrientation() == PageFormat.PORTRAIT){
            
        }else{
            path = path.replace(".jpg", "_landscape.jpg");
        }
        
        ImageIcon tempIcon = new ImageIcon(path); 
        if(pf.getOrientation() == PageFormat.PORTRAIT){
            tempIcon.setImage(tempIcon.getImage().getScaledInstance(Integer.valueOf(data[2]), Integer.valueOf(data[3]), Image.SCALE_DEFAULT));
        }else{
            tempIcon.setImage(tempIcon.getImage().getScaledInstance(Integer.valueOf(data[3]), Integer.valueOf(data[2]), Image.SCALE_DEFAULT));
        }
        
        return tempIcon;
    }
    public static String appendSpace(String  para){  
        int length = para.length();  
        char[] value = new char[length << 1];  
        for (int i=0, j=0; i<length; ++i, j = i << 1) {  
            value[j] = para.charAt(i);  
            value[1 + j] = ' ';  
        }  
        String s = new String(value);  
        return s.substring(0,s.length()-1);
    }
    /**
     * 将中文括号替换为英文括号
     * @param text
     * @return 
     */
    public static String formatNameStr(String text){
        String nameStr = text;
        nameStr = nameStr.replaceAll("（", "(");
        nameStr = nameStr.replaceAll("）", ")");
        return nameStr;
    }
    /**
     * 计算字符串的中文长度
     * @param text
     * @return 
     */
    public static int count(String text) {
     String Reg="^[\u4e00-\u9fa5]{1}$";//正则
     String dotReg = "\\.";
     String dotRegCN = "\\·";
     String bracketLeftReg = "\\(";
     String bracketRightReg = "\\)";
     int result=0;
     for(int i=0;i<text.length();i++){
        String b=Character.toString(text.charAt(i));
        if(b.matches(Reg))result++;
        if(b.matches(dotReg)) result++;
        if(b.matches(dotRegCN)) result++;
        if(b.matches(bracketLeftReg)) result++;
        if(b.matches(bracketRightReg)) result++;
     }
     return result;
   }
}
