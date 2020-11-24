/*
 * 文件名：CSVFileWriter
 * 版权：Copyright: goldsign (c) 2013
 * 描述：写出CSV文件
 */

package com.goldsign.csfrm.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import javax.swing.JTable;
import javax.swing.table.TableModel;


/*
 * 将table内容写入CSV文件
 * @author     lindaquan
 * @version    V1.0
 */

public class CSVFileWriter{
    
    /*
     * 写入文件
     * @param ftable
     * @param file
     * @return a boolean 
     */
    public static boolean writeTableModel( JTable fTable, File file ){

        if( fTable == null ){
            return false;
        }

        TableModel tableModel = fTable.getModel();
        StringBuffer fileBuf = new StringBuffer( "" );
        int rowCount = tableModel.getRowCount();
        int columnCount = tableModel.getColumnCount();
        for( int col = 0; col < columnCount; col++ ){
            fileBuf.append( tableModel.getColumnName( col ) );
            fileBuf.append( "," );
        }
        fileBuf.append( "\n" );
        for( int row = 0; row < rowCount; row++ ){
            for( int col = 0; col < columnCount; col++ ){
                if (tableModel.getValueAt( row, col ) != null) {
                    if (StringUtil.isLong(tableModel.getValueAt( row, col ).toString())) {
                        fileBuf.append( "'"+tableModel.getValueAt( row, col ).toString() );
                    } else {
                        fileBuf.append( tableModel.getValueAt( row, col ).toString() );
                        
                    }
                }
                if( col != columnCount - 1 ){
                    fileBuf.append( "," );
                }
            }
            fileBuf.append( "\n" );
        }
        /*
        try{
            FileOutputStream fis=new FileOutputStream(file);
            OutputStreamWriter osw = new OutputStreamWriter(fis,"gb2312");
            osw.write(fileBuf.toString());
            osw.close();
            
            return true;
        }catch(IOException e){
            e.printStackTrace( System.err );
            return false;
        }
        */
        FileOutputStream fis = null;
        OutputStreamWriter osw = null;
        try{
            fis = new FileOutputStream(file);
            osw = new OutputStreamWriter(fis,"gbk");
            osw.write(fileBuf.toString());
          

            return true;
        }catch(IOException e){
            e.printStackTrace( System.err );
            return false;
        }finally{
            try{
                if(null != fis){
                    fis.close();
                }
                if(null != osw){
                    osw.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
