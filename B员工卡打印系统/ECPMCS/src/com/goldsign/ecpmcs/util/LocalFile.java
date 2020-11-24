/*
 * 文件名：LocalFile
 * 版权：Copyright: goldsign (c) 2013
 */

package com.goldsign.ecpmcs.util;

import com.goldsign.csfrm.env.BaseConstant;
import com.goldsign.csfrm.util.DateHelper;
import com.goldsign.lib.db.util.DbHelper;
import com.goldsign.ecpmcs.env.AppConstant;
import com.goldsign.ecpmcs.env.ConfigConstant;
import com.goldsign.ecpmcs.vo.SignCardPrintVo;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import org.apache.log4j.Logger;


/*
 * 〈处理离线数据〉
 * @author     lindaquan
 * @version    V1.0
 * @createTime 2014-4-27
 */

public class LocalFile {

    private static Logger logger = Logger.getLogger(LocalFile.class.getName());
    
    /*
     * 写入本地文件
     * @param vo
     * @return file
     */
    public static File writeLocalFile(SignCardPrintVo vo){
        String dir = getLocalFleDir();//保存路径
        File parentFile = new File(dir);
        if(!parentFile.isDirectory()){
            parentFile.mkdirs();
        }
        File file = new File(dir + "/" + vo.getIdentityType() + vo.getIdentityId() + AppConstant.LOCAL_FILE_TO_FILE_SUFFIX);
        PrintWriter pw = null;
        
        try{
            if(!file.exists()){
                file.createNewFile();
            }
        
            pw = new PrintWriter(new FileWriter(file,true));
            String dataStr = fmtToDataFileStr(vo, AppConstant.VER_SIGN);
            pw.println(dataStr);
            pw.flush();;
        }catch(Exception e) {
            logger.error(e);
        }finally {
            if(null != pw){
                pw.close();
            }
        }
        return file;
    }

    /**
     * 取服务器信息文件,并将每条记录插入数据库
     * @param vo
     * @return
     * @throws IOException 
     */
    public static Boolean readLocalFile(File file) throws IOException{
        
        Boolean result = false;
        if (!file.exists()) {
            return result;
        }
        
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(file));
            String line = br.readLine();
            while(line != null){
                String[] data = line.split(AppConstant.SEP_VER_SIGN, -1);
                if(data.length>0){
                    if(!data[4].isEmpty() && data[4].equals(AppConstant.TICKET_TYPE_EMPLOYEE)){
                        if(!insertForFileEmployee(data)){
                            return result;
                        }
                    }else{
                        if(!insertForFile(data)){
                            return result;
                        }
                    }
                }
                line = br.readLine();
            }
            result = true;
        }finally{
            if(null != br){
                br.close();
            }
        }
        
        return result;
    }
    
    /**
     * 取服务器信息文件保存路径
     * @return String
     */
    private static String getLocalFleDir() {
        return BaseConstant.appWorkDir + ConfigUtil.getConfigValue(ConfigConstant.UploadTag, ConfigConstant.UploadLocalFileTag);
    }

    /**
     * 格式化待写入的订单内容，以指定符号分隔
     * @param vo
     * @return String
     */
    private static String fmtToDataFileStr(SignCardPrintVo vo,String sign) {
        StringBuffer dataStr = new StringBuffer("");

        dataStr.append(vo.getName()!=null? vo.getName(): "").append(sign);
        dataStr.append(vo.getGender()!=null? vo.getGender(): "").append(sign);
        dataStr.append(vo.getIdentityId()!=null? vo.getIdentityId(): "").append(sign);
        dataStr.append(vo.getIdentityType()!=null? vo.getIdentityType(): "").append(sign);
        dataStr.append(vo.getCardType()!=null? vo.getCardType(): "").append(sign);
        dataStr.append(vo.getPhotoName()!=null? vo.getPhotoName(): "").append(sign);
        dataStr.append(vo.getPrintOper()!=null? vo.getPrintOper(): "").append(sign);
        dataStr.append(vo.getPrintTime()!=null? vo.getPrintTime(): (DateHelper.curDateToStr19yyyy_MM_dd_HH_mm_ss())).append(sign);
        dataStr.append(vo.getIssueTime()!=null? vo.getIssueTime(): "").append(sign);
        dataStr.append(vo.getRemark()!=null? vo.getRemark(): "").append(sign);
        dataStr.append(vo.getDepartment()!=null? vo.getDepartment(): "").append(sign);
        dataStr.append(vo.getPosition()!=null? vo.getPosition(): "").append(sign);;
        dataStr.append(vo.getLogicalId()!=null? vo.getLogicalId(): "");
        
        return dataStr.toString();
    }
    
    
    /*
     * 将读取文件信息插入数据库
     */
    public static Boolean insertForFile(Object[] values) {
        boolean result = false;
        DbHelper dbHelper = null;
    
        try {
            dbHelper = new DbHelper("", AppConstant.dbcpHelper.getConnection());
            dbHelper.setAutoCommit(false);
            
            //当数据库存在员工信息，更新该条记录
            String sqlStr = "insert into "+AppConstant.DATABASE_USER_TK+"w_ic_ecp_print_list"
                    + " (id, name, gender, identity_id, identity_type, card_type, photo_name, print_oper, print_time, issue_time, remark, department, position,logical_id)"
                    + " values ("+AppConstant.DATABASE_USER_TK+"w_seq_w_ic_ecp_print_list.nextval, ?, ?, ?, ?, ?, ?, ?, to_date(?,'yyyy-MM-dd hh24:mi:ss'), to_date(?,'yyyymmddhh24miss'), ?, ?, ?, ?)";
            
            logger.info("将读取文件信息插入数据库sqlStr:" + sqlStr);
            result = dbHelper.executeUpdate(sqlStr, values)>0;

            dbHelper.commitTran();
            dbHelper.setAutoCommit(true);
            result = true;
            
        } catch (Exception e) {
            PubUtil.handleExceptionNoThrow(e, logger);
            return false;
        } finally {
            PubUtil.finalProcess(dbHelper);
        }
        return result;
    }
    
    
    /*
     * 将读取文件信息插入数据库员工卡打印记录表
     */
    public static Boolean insertForFileEmployee(Object[] values) {
        boolean result = false;
        DbHelper dbHelper = null;
    
        try {
            dbHelper = new DbHelper("", AppConstant.dbcpHelper.getConnection());
            dbHelper.setAutoCommit(false);
            
            //当数据库存在员工信息，更新该条记录
            String sqlStr = "insert into "+AppConstant.DATABASE_USER_TK+"w_ic_ecp_print_list"
                    + " (id, name, gender, identity_id, identity_type, card_type, photo_name, print_oper, print_time, issue_time, remark, department, position, logical_id)"
                    + " values ("+AppConstant.DATABASE_USER_TK+"w_seq_w_ic_ecp_print_list.nextval, ?, ?, ?, ?, ?, ?, ?, to_date(?,'yyyy-MM-dd hh24:mi:ss'), to_date(?,'yyyymmddhh24miss'), ?, ?, ?, ?)";
            
            logger.info("将读取文件信息插入数据库sqlStr:" + sqlStr);
            result = dbHelper.executeUpdate(sqlStr, values)>0;

            dbHelper.commitTran();
            dbHelper.setAutoCommit(true);
            result = true;
            
        } catch (Exception e) {
            PubUtil.handleExceptionNoThrow(e, logger);
            return false;
        } finally {
            PubUtil.finalProcess(dbHelper);
        }
        return result;
    }
}
