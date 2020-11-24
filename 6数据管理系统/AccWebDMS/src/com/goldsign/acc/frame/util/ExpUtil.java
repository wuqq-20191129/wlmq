/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.frame.util;

import com.alibaba.fastjson.JSON;
import com.goldsign.acc.frame.constant.ConfigConstant;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;

/**
 * @author 钟子奇
 * @date 2018-04-17
 * @desc 导出Excel功能模板
 */
public class ExpUtil {

    private static Logger logger = Logger.getLogger(ExpUtil.class.getName());

    /**
     * 导出Excel文档至固定路径 文档名字为tempyyyyMMdd_HHmmssSSS.xlsx
     *
     * @param objs 数据集合
     * @param request 存储表头列、字段名称、模板名称、导出文件名称
     * @param configMapper 路径配置类
     * @return String 生成文件名字
     * @throws Exception
     */
    public static String exportExcel(List objs, HttpServletRequest request)
            throws Exception {
        /* 表头列名称 */
        String expAllTableHeadName = request.getParameter("expAllTableHeadName");
        /* 字段名称，个数和表头一一对应 */
        String expAllFields = request.getParameter("expAllFields");
        // /*导出文件名称*/
        String expFileName = request.getParameter("expFileName");
        /* 单元格宽度 */
        String expAllTableHeadWidth = request.getParameter("expAllTableHeadWidth");

        if (objs == null) {
            throw new Exception("数据集合为空");
        }
        if (expAllTableHeadName == null) {
            throw new Exception("表头集合为空");
        }
        if (expAllFields == null) {
            throw new Exception("字段数据为空");
        }
        if (expFileName == null) {
            throw new Exception("导出文件名为空");
        }
        if (ConfigConstant.EXPORT_EXCEL_PATH == null || "".equals(ConfigConstant.EXPORT_EXCEL_PATH)) {
            throw new Exception("路径配置类为空");
        }
        if (expAllTableHeadWidth == null) {
            throw new Exception("宽度集合为空");
        }
        //本地测试需求
        Properties props = System.getProperties();
//        System.out.println("操作系统的名称：" + props.getProperty("os.name"));
//        System.out.println("操作系统的版本号：" + props.getProperty("os.version"));
        if (!ConfigConstant.EXPORT_EXCEL_PATH.contains(":") && props.getProperty("os.name").contains("Windows")) {
            ConfigConstant.EXPORT_EXCEL_PATH = ConfigConstant.EXPORT_EXCEL_PATH_WINDOWS;
        }

        String[] fields = expAllFields.split(",");
        String[] heads = expAllTableHeadName.split(",");
        String[] widths = expAllTableHeadWidth.split(",");
        while (heads.length > widths.length) {
            expAllTableHeadWidth = expAllTableHeadWidth.substring(0, expAllTableHeadWidth.lastIndexOf(',')) + "66px,";
            widths = expAllTableHeadWidth.split(",");
        }
        OutputStream out = null;

        try {
//			ConfigKey key = new ConfigKey();
//			key.setType(TEMPLATE_PATH_TYPE);
//			key.setTypeSub(TEMPLATE_PATH_TYPE_SUB);
//			List<Config> configs = configMapper.selectConfigs(key);
            String templateFile = "";
            String configPath = "";
            //开始时间
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmssSSS");
//			if (configs != null) {
//				Config config = configs.get(0);
//				configPath = config.getConfigValue();
            templateFile = ConfigConstant.EXPORT_EXCEL_PATH + "/temp" + sdf.format(date) + ".xlsx";
//
//			}
            // 读取Excel文档
            logger.info("创建Excel文档，time:" + sdf.format(date));
//            Workbook workBook = new XSSFWorkbook();
            Workbook workBook = new SXSSFWorkbook(100);
            Sheet sheet = workBook.createSheet(expFileName);
            CellStyle style = workBook.createCellStyle();
            Font f = workBook.createFont();
            f.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
            style.setFont(f);
            style.setAlignment(HorizontalAlignment.CENTER);
            Row row0 = sheet.createRow(0);
            for (int i = 0; i <= heads.length - 1; i++) {
                // 在一行内循环
                Cell first = row0.createCell(i);
                first.setCellValue(heads[i]);
                first.setCellStyle(style);
                // System.out.println(widths[l].replaceAll("px", ""));
                // System.out.println(Integer.parseInt(widths[l].replaceAll("px", "")));
                if ("".equals(widths[i])) {
                    sheet.setColumnWidth(i, 66 * 40);
                } else {
                    sheet.setColumnWidth(i, Integer.parseInt(widths[i].replaceAll("px", "")) * 40);
                }
            }
            style = workBook.createCellStyle();
            style.setAlignment(HorizontalAlignment.CENTER);
            for (int j = 0; j < objs.size(); j++) {
                // 创建一行：从第二行开始，跳过属性列
                Row row = sheet.createRow(j + 1);
                // 得到要插入的每一条记录
                Map dataMap = (Map) objs.get(j);
//                style = workBook.createCellStyle();
//                style.setAlignment(HorizontalAlignment.CENTER);

                for (int k = 0; k <= fields.length - 1; k++) {
                    // 在一行内循环
                    Cell first = row.createCell(k);
                    Object obj = dataMap.get(fields[k].trim());
                    first.setCellStyle(style);
                    if (obj instanceof String) {
                        first.setCellValue((String) dataMap.get(fields[k]));

                    }
                    else if (obj instanceof Long) {
                        first.setCellValue((Long) dataMap.get(fields[k]));
                    }
                    else if (obj instanceof BigDecimal) {
                        BigDecimal v = (BigDecimal) dataMap.get(fields[k]);
                        first.setCellValue(v.toString());
                    }else if(obj instanceof Date) {
                    	first.setCellValue((Date) dataMap.get(fields[k]));
                    }else if(obj instanceof Integer) {
                    	first.setCellValue((Integer) dataMap.get(fields[k]));
                    }else if(obj instanceof Boolean){
                    	first.setCellValue((Boolean) dataMap.get(fields[k]));
                    }
                    else {
                    	if(obj!=null) {
                    		first.setCellValue(obj.toString());
                    	}else {
                    		first.setCellValue("");
                    	}
                    }
                }
            }
            // 创建文件输出流，准备输出电子表格：这个必须有，否则你在sheet上做的任何操作都不会有效
            out = new FileOutputStream(templateFile);
            workBook.write(out);
            Date endDate = new Date();
            logger.info("写Excel文档结束：" + templateFile + ",结束时间：" + sdf.format(endDate));
            return templateFile;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.flush();
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    /**
     * Excel路径转成json输出
     *
     * @param response
     * @param path 生成Excel绝对路径
     */
    public static void renderExportExcelPath(HttpServletResponse response, String path) throws IOException {
        PrintWriter printWriter = null;
        try {
            Map<String, Object> data = new HashMap<String, Object>(1);
            data.put("exportExcelPath", path);
            String jsonResult = JSON.toJSONString(data);
            printWriter = response.getWriter();
            printWriter.print(jsonResult);
        } catch (IOException ex) {
            logger.info("exception", ex);
            throw ex;
        } finally {
            if (null != printWriter) {
                printWriter.flush();
                printWriter.close();
            }
        }
    }

    /**
     * 转化指定成员信息entity为Map
     *
     * @param fields 方法集
     * @param results 结果集
     */
    public static List<Map<String, Object>> entityToMap(String expAllFields, List results) throws Exception {
        if (expAllFields == null) {
            throw new Exception("字段数据为空");
        }
        String[] fields = expAllFields.split(",");
        List<Map<String, Object>> list = new ArrayList<>();
        if (results.size() > 0 || fields.length == 0) {
            for (Object vo : (List<Object>) results) {
                Class<?> entity = vo.getClass();
                Map<String, Object> map = new HashMap<>();
                for (int i = 0; i < fields.length; i++) {
                    Method method = entity.getMethod(fields[i]);
                    map.put(fields[i], method.invoke(vo));
                }
                list.add(map);
            }
        }
        return list;
    }
}
