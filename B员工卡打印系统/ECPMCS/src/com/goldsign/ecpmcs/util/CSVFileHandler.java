/*
 * 文件名：CSVFileFilter
 * 版权：Copyright: goldsign (c) 2013
 */

package com.goldsign.ecpmcs.util;

import com.goldsign.csfrm.util.CSVFileFilter;
import com.goldsign.csfrm.util.CSVFileWriter;
import java.awt.Component;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;

/**
 * 导出CSV文件
 * @author lindaquan
 */
public class CSVFileHandler {
    
    private JTable table = null;
    private Component parentComp = null;
    private int WIDTH = 1;

    public CSVFileHandler( JTable table, Component parentComp ){
        this.table = table;
        this.parentComp = parentComp;
    }

    public boolean execute(){
        if( table == null ){
            return false;
        }
        File file = showSaveDialog();
        if( file != null ){
            if( file.exists() ){
                if( JOptionPane.showConfirmDialog( table, "文件已经存在，是否覆盖?" ) != JOptionPane.YES_OPTION ){
                    return false;
                }
            }
            
            if(CSVFileWriter.writeTableModel( table, file )){
                JOptionPane.showMessageDialog(table, file.getPath()+" 导出成功！" , "提示", WIDTH);
                return true;
            }
        }
        JOptionPane.showMessageDialog(table, " 导出失败！" , "提示", WIDTH);
        return false;
    }

    //保存提示框
    private File showSaveDialog(){

        //JFileChooser用来提供一个文件对话框，可以通过其showXxxDialog打开一个模态对话框，或直接实例化并加入到其他组件。
        JFileChooser chooser = new JFileChooser();
        chooser.removeChoosableFileFilter( chooser.getAcceptAllFileFilter() );
        chooser.addChoosableFileFilter( new CSVFileFilter() );//添加CSV文件的过滤器
        chooser.setFileSelectionMode( JFileChooser.FILES_ONLY );//只能选择文件

        int ret = chooser.showSaveDialog( parentComp );
        if( ret == JFileChooser.APPROVE_OPTION ){//是个整型常量，代表0。当返回0的值才执行相关操作，否则什么也不做。
            File f = chooser.getSelectedFile();
            javax.swing.filechooser.FileFilter filter = chooser.getFileFilter();

            String extension = getExtension( f );
            if( extension == null || !extension.equalsIgnoreCase( ( (CSVFileFilter)filter ).getExtension() ) ){
                return new File( f.getAbsolutePath() + "." + ( (CSVFileFilter)filter ).getExtension() );
            }

            return f;
        }

        return null;

    }
    
    //取文件后缀
    private String getExtension(File file){
        
        String fileName = file.getName();
        int index = fileName.lastIndexOf('.');
        String extension = "";

        if (index > 0 && index < fileName.length() - 1)
        {
            extension = fileName.substring(index + 1).toLowerCase();
        }
        return extension;
    }
}