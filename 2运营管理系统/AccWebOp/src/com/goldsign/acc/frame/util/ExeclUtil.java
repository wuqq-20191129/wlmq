/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.goldsign.acc.frame.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * @datetime 2017-7-12 15:17:34
 * @author lind
 * 读取excel
 */
public class ExeclUtil {
    public int totalRows;//总行数
    public int totalCells;//总列数
    public static SimpleDateFormat sdf = new SimpleDateFormat("yyyy/mm/dd");

    public ExeclUtil() {
    }
    
    public List<ArrayList<String>> readExcel(File file){
        if(file == null){
            return null;
        }
        String fileName = file.getName();
        if(fileName.endsWith(".xls") || fileName.endsWith(".XLS")){
            return readXLS(file);
        }
        if(fileName.endsWith(".xlsx") || fileName.endsWith(".XLSX")){
            return readXLSX(file);
        }
        
        return null;
    }

    /*
    read the Excel 2003-2007.xls
    */
    private List<ArrayList<String>> readXLS(File file) {
        List<ArrayList<String>> list = new ArrayList<ArrayList<String>>();
        ArrayList<String> rowList = null;
        HSSFWorkbook hSSFWorkbook = null;
        FileInputStream inputStream = null;
        
        try {
            inputStream = new FileInputStream(file);
            //创建文档
            hSSFWorkbook = new HSSFWorkbook(inputStream);
            //读取sheet页
            for(int numSheet = 0; numSheet < hSSFWorkbook.getNumberOfSheets(); numSheet++){
                HSSFSheet hSSFSheet = hSSFWorkbook.getSheetAt(numSheet);
                if(hSSFSheet == null){
                    continue;
                }
                totalRows = hSSFSheet.getLastRowNum();
                //读取row
                for (int rowNum  = 0; rowNum  < totalRows; rowNum ++) {
                    HSSFRow hSSFRow = hSSFSheet.getRow(rowNum);
                    if(hSSFRow != null){
                        rowList = new ArrayList<String>();
                        totalCells = hSSFRow.getLastCellNum();
                        //读取列
                        for (int cellNum = 0; cellNum < totalCells; cellNum++) {
                            HSSFCell hSSFCell = hSSFRow.getCell(cellNum);
                            if(hSSFCell == null){
                                rowList.add("");
                                continue;
                            }
                            rowList.add(getValue(hSSFCell));
                        }
                        list.add(rowList);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                inputStream.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return list;
    }

    /*
    read the Excel 2007-.xls
    */
    private List<ArrayList<String>> readXLSX(File file) {
        List<ArrayList<String>> list = new ArrayList<ArrayList<String>>();
        ArrayList<String> rowList = null;
        XSSFWorkbook xSSFWorkbook = null;
        FileInputStream inputStream = null;
        
        try {
            inputStream = new FileInputStream(file);
            //创建文档
            xSSFWorkbook = new XSSFWorkbook(inputStream);
            //读取sheet页
            for(int numSheet = 0; numSheet < xSSFWorkbook.getNumberOfSheets(); numSheet++){
                XSSFSheet xSSFSheet = xSSFWorkbook.getSheetAt(numSheet);
                if(xSSFSheet == null){
                    continue;
                }
                totalRows = xSSFSheet.getLastRowNum();
                //读取row
                for (int rowNum  = 0; rowNum  < totalRows; rowNum ++) {
                    XSSFRow xSSFRow = xSSFSheet.getRow(rowNum);
                    if(xSSFRow != null){
                        rowList = new ArrayList<String>();
                        totalCells = xSSFRow.getLastCellNum();
                        //读取列
                        for (int cellNum = 0; cellNum < totalCells; cellNum++) {
                            XSSFCell xSSFCell = xSSFRow.getCell(cellNum);
                            if(xSSFCell == null){
                                rowList.add("");
                                continue;
                            }
                            rowList.add(getValue(xSSFCell));
                        }
                        list.add(rowList);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                inputStream.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return list;
    }

    /*
    值转换
    */
    private String getValue(Cell cell) {
        if(cell.getCellType() == Cell.CELL_TYPE_BOOLEAN){
            return String.valueOf(cell.getBooleanCellValue());
        } else if(cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
            String cellValue = "";
            if(HSSFDateUtil.isCellDateFormatted(cell)){
                Date date = HSSFDateUtil.getJavaDate(cell.getNumericCellValue());
                cellValue = sdf.format(date);
            } else {
                DecimalFormat df = new DecimalFormat("#.##");
                cellValue = df.format(cell.getNumericCellValue());
            }
            return cellValue;
        } else {
            return String.valueOf(cell.getStringCellValue().trim());
        }
    }
    
    
    public static void main(String[] args) {
        ExeclUtil read = new ExeclUtil();
        File file = new File("D:/xxx.xlsx");
        read.readExcel(file);
    }
}
