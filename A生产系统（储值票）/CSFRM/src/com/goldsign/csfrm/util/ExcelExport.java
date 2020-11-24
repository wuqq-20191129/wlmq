/*
 * 文件名：ExcelExport
 * 版权：Copyright: goldsign (c) 2013
 */

package com.goldsign.csfrm.util;

import java.awt.Component;
import java.io.File;
import java.io.FileOutputStream;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.TableModel;
import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Colour;
import jxl.format.UnderlineStyle;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;


/*
 * XLS导出
 * @author     lindaquan
 * @version    V1.0
 * @createTime 2013-10-24
 */

public class ExcelExport {
    
    private static int WIDTH = 1;
    
    /**
     * 导出多个Jtable表数据内容,每列完全展开
     * @param fTables
     * @param file 
     */
    public static void exportXLSJTableColumnSpread(JTable[] fTables, Component parentComp, int[] column_Sizes){
        
        File file = showSaveDialog(parentComp);
        
        if(file != null){
            
            if( file.exists() ){
                if( JOptionPane.showConfirmDialog( parentComp, "文件已经存在，是否覆盖?" ) != JOptionPane.YES_OPTION ){
                    return;
                }
            }
        
            WritableWorkbook wwb = null;
            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream(file.getPath());
                //创建XLS
                wwb = Workbook.createWorkbook(fos);
                
                //设置标题单元格的文字格式
                WritableCellFormat wcf = setColumnTitleFormat();
                //设置正文单元格的文字格式
                WritableCellFormat wcfc = setColumnFormat();
                int len = fTables.length;
                for(int j=0; j < len; j++){

                    TableModel tableModel = fTables[j].getModel();
                    WritableSheet ws = null;
                    // 创建一个工作表
                    if(fTables[j].getName() != null){
                        ws = wwb.createSheet(fTables[j].getName(), j);
                    }else{
                        ws = wwb.createSheet(""+j, j);
                    }
                    //ws.setColumnView(0, 20);  //列宽自适应       
                    //ws.setRowView(1, 500);
                    updateSheetColumnSpread(tableModel,wcf,wcfc,ws,column_Sizes);

                }

                wwb.write();
                //wwb.close(); 
                fos.flush();  
                //fos.close();  
                
                JOptionPane.showMessageDialog(parentComp, file.getPath()+" 导出成功！" , "提示", WIDTH);

            } catch (Exception e){
                e.printStackTrace();
                JOptionPane.showMessageDialog(parentComp, file.getPath()+" 导出失败！" , "提示", WIDTH);
            }finally{
                try {
                    if(null != wwb){
                        wwb.close();
                    }
                    if(null != fos){
                        fos.close();
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
    
    /**
     * 导出多个Jtable表数据内容
     * @param fTables
     * @param file 
     */
    public static void exportXLSJTable(JTable[] fTables, Component parentComp){
        
        File file = showSaveDialog(parentComp);
        
        if(file != null){
            
            if( file.exists() ){
                if( JOptionPane.showConfirmDialog( parentComp, "文件已经存在，是否覆盖?" ) != JOptionPane.YES_OPTION ){
                    return;
                }
            }
        /*
            WritableWorkbook wwb;
            FileOutputStream fos;
            try {
                fos = new FileOutputStream(file.getPath());
                //创建XLS
                wwb = Workbook.createWorkbook(fos);
                
                //设置标题单元格的文字格式
                WritableCellFormat wcf = setColumnTitleFormat();
                //设置正文单元格的文字格式
                WritableCellFormat wcfc = setColumnFormat();

                for(int j=0; j < fTables.length; j++){

                    TableModel tableModel = fTables[j].getModel();
                    WritableSheet ws = null;
                    // 创建一个工作表
                    if(fTables[j].getName() != null){
                        ws = wwb.createSheet(fTables[j].getName(), j);
                    }else{
                        ws = wwb.createSheet(""+j, j);
                    }
                    ws.setColumnView(0, 20);         
                    ws.setRowView(1, 500);
                    updateSheet(tableModel,wcf,wcfc,ws);

                }

                wwb.write();
                wwb.close(); 
                fos.flush();  
                fos.close();  
                
                JOptionPane.showMessageDialog(parentComp, file.getPath()+" 导出成功！" , "提示", WIDTH);

            } catch (Exception e){
                e.printStackTrace();
                JOptionPane.showMessageDialog(parentComp, file.getPath()+" 导出失败！" , "提示", WIDTH);
            }
            */
            WritableWorkbook wwb = null;
            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream(file.getPath());
                //创建XLS
                wwb = Workbook.createWorkbook(fos);
                
                //设置标题单元格的文字格式
                WritableCellFormat wcf = setColumnTitleFormat();
                //设置正文单元格的文字格式
                WritableCellFormat wcfc = setColumnFormat();
                int len = fTables.length;
                for(int j=0; j < len; j++){

                    TableModel tableModel = fTables[j].getModel();
                    WritableSheet ws = null;
                    // 创建一个工作表
                    if(fTables[j].getName() != null){
                        ws = wwb.createSheet(fTables[j].getName(), j);
                    }else{
                        ws = wwb.createSheet(""+j, j);
                    }
                    ws.setColumnView(0, 20);         
                    ws.setRowView(1, 500);
                    updateSheet(tableModel,wcf,wcfc,ws);

                }

                wwb.write();
                //wwb.close(); 
                fos.flush();  
                //fos.close();  
                
                JOptionPane.showMessageDialog(parentComp, file.getPath()+" 导出成功！" , "提示", WIDTH);

            } catch (Exception e){
                e.printStackTrace();
                JOptionPane.showMessageDialog(parentComp, file.getPath()+" 导出失败！" , "提示", WIDTH);
            }finally{
                try {
                    if(null != wwb){
                        wwb.close();
                    }
                    if(null != fos){
                        fos.close();
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
     /**
     * 填充内容（行，列）,列完全伸展
     * @param tableModel
     * @param wcf
     * @param ws
     * @throws WriteException 
     */
    private static void updateSheetColumnSpread(TableModel tableModel, WritableCellFormat wcf, WritableCellFormat wcfc, WritableSheet ws, int[] column_Sizes ) throws WriteException {
        
        int rowCount = tableModel.getRowCount();
        int columnCount = tableModel.getColumnCount();
        
        for (int j = 0; j < columnCount; j++){
            //标题
            ws.addCell(new Label(j, 0, tableModel.getColumnName(j), wcf));
            ws.setColumnView(j, (int)(column_Sizes[j]/6)==0?20:(int)(column_Sizes[j]/6));
            //填充数据的内容
            for (int i = 0; i < rowCount; i++){
                String column = tableModel.getValueAt(i, j)==null?"":String.valueOf(tableModel.getValueAt(i, j));
                ws.addCell(new Label(j, i+1, column, wcfc));
            }
        }
    }
    /**
     * 填充内容（行，列）
     * @param tableModel
     * @param wcf
     * @param ws
     * @throws WriteException 
     */
    private static void updateSheet(TableModel tableModel, WritableCellFormat wcf, WritableCellFormat wcfc, WritableSheet ws) throws WriteException {
        
        int rowCount = tableModel.getRowCount();
        int columnCount = tableModel.getColumnCount();
        
        for (int j = 0; j < columnCount; j++){
            //标题
            ws.addCell(new Label(j, 0, tableModel.getColumnName(j), wcf));
            //填充数据的内容
            for (int i = 0; i < rowCount; i++){
                String column = tableModel.getValueAt(i, j)==null?"":String.valueOf(tableModel.getValueAt(i, j));
                ws.addCell(new Label(j, i+1, column, wcfc));
            }
        }
    }
    
    
    //保存提示框
    private static File showSaveDialog(Component parentComp){

        //JFileChooser用来提供一个文件对话框，可以通过其showXxxDialog打开一个模态对话框，或直接实例化并加入到其他组件。
        JFileChooser chooser = new JFileChooser();
        chooser.removeChoosableFileFilter( chooser.getAcceptAllFileFilter() );
        chooser.addChoosableFileFilter(new XFileFilter("xls"));
        chooser.setFileSelectionMode( JFileChooser.FILES_ONLY );//只能选择文件

        int ret = chooser.showSaveDialog( parentComp );
        if( ret == JFileChooser.APPROVE_OPTION ){//是个整型常量，代表0。当返回0的值才执行相关操作，否则什么也不做。
            File f = chooser.getSelectedFile();
            javax.swing.filechooser.FileFilter filter = chooser.getFileFilter();

            String extension = getExtension( f );
            if( extension == null || !extension.equalsIgnoreCase( ( (XFileFilter)filter ).getExtension() ) ){
                return new File( f.getAbsolutePath() + "." + ( (XFileFilter)filter ).getExtension() );
            }
            return f;
        }
        return null;
    }
    
    //取文件后缀
    private static String getExtension(File file){
        
        String fileName = file.getName();
        int index = fileName.lastIndexOf('.');
        String extension = "";

        if (index > 0 && index < fileName.length() - 1)
        {
            extension = fileName.substring(index + 1).toLowerCase();
        }
        return extension;
    }

    private static WritableCellFormat setColumnTitleFormat() throws WriteException {
        //设置标题单元格的文字格式
        WritableFont wf = new WritableFont(WritableFont.ARIAL,12,WritableFont.BOLD,false,
                UnderlineStyle.NO_UNDERLINE,Colour.BLUE);
        WritableCellFormat wcf = new WritableCellFormat(wf);
        wcf.setVerticalAlignment(VerticalAlignment.CENTRE); 
        wcf.setAlignment(Alignment.LEFT); 
        //wcf.setWrap(false);
        wcf.setWrap(true);
        wcf.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN);
        
        return wcf;
    }
    
    private static WritableCellFormat setColumnFormat() throws WriteException {
        //设置标题单元格的文字格式
        WritableFont wf = new WritableFont(WritableFont.ARIAL,10,WritableFont.NO_BOLD,false,
                UnderlineStyle.NO_UNDERLINE,Colour.BLACK);
        WritableCellFormat wcf = new WritableCellFormat(wf);
        wcf.setVerticalAlignment(VerticalAlignment.CENTRE); 
        wcf.setAlignment(Alignment.LEFT); 
        //wcf.setWrap(false);
        wcf.setWrap(true);
        wcf.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN);
        
        return wcf;
    }

}
