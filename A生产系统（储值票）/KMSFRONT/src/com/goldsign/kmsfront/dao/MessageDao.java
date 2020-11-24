/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.goldsign.kmsfront.struct.dao;

import com.goldsign.commu.commu.dao.BaseDao;
import com.goldsign.commu.commu.util.DbHelper;
import com.goldsign.commu.commu.util.PubUtil;
import com.goldsign.kmsfront.struct.env.AppConstant;
import com.goldsign.kmsfront.struct.util.FileBufferReader;
import com.goldsign.kmsfront.struct.vo.QueryConVo;
import com.goldsign.kmsfront.struct.vo.QueryRetVo;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

/**
 *
 * @author lenovo
 */
public class MessageDao extends BaseDao implements IMessageDao{

    private static Logger logger = Logger.getLogger(MessageDao.class.getName());

    @Override
    public QueryRetVo queryStructDatas(List<String> accountNos, List<QueryConVo> queryCons) 
            throws Exception {
        
        QueryRetVo queryRetVo = new QueryRetVo();
        List<String> accountNosCopy = new ArrayList<String>(accountNos);
        for(String accountNo: accountNos){
            File file = getHistoryAccountFile(accountNo);
            if(file == null || !file.exists()){
                continue;
            }
            parseHistoryAccountFile(file, queryRetVo, queryCons, accountNosCopy);
        }
        
        return queryRetVo;
    }
    
    private void parseHistoryAccountFile(File hisFile, QueryRetVo queryRetVo, 
            List<QueryConVo> queryCons, List<String> accountNos) throws Exception{
    
        //分析文件， 得到记录
        InputStream is = null;
        BufferedInputStream br = null;
        try {
            is = new FileInputStream(hisFile);
            br = new BufferedInputStream(is);
            byte[] tmpData = new byte[1024]; 
            int i=0;
            byte oneByte = (byte) br.read();
            while(oneByte != -1){
                tmpData[i++] = oneByte;
                //读到一行
                if(oneByte == '\n'){
                    byte[] lineBs = new byte[i];
                    System.arraycopy(tmpData, 0, lineBs, 0, i);
                    String line = new String(lineBs);
                    if(isHistoryRecordOk(line, queryCons, accountNos)){
                        queryRetVo.setLen(line.getBytes().length);
                        queryRetVo.setValue(line);
                    }
                    i=0;
                }
                oneByte = (byte) br.read();
            }
        }finally{
            is.close();
            br.close();
        }
    }
    
    private boolean isHistoryRecordOk(String record, List<QueryConVo> queryCons,
            List<String> accountNos){
    
        if(record.equals("")){
            return false;
        }
        String[] fields = record.split("");
        int len = fields.length;
        if(len == 0){
            return false;
        }
        String accountNo = fields[0];
        if(!accountNos.contains(accountNo)){
            return false;
        }
        for(QueryConVo queryConVo:queryCons){
            int type = queryConVo.getType();
            String sign = queryConVo.getSign();
            String value = queryConVo.getValue();
            if(len <= type){
                continue;
            }
            if(value == null || value.equals("")){
                continue;
            }
            String value2 = fields[type];
            //0 like、1等于、2不等于、3大等于、4小等于、5大于、6小于
            if(sign.equals("0")){
                if(!value2.contains(value)){
                    return false;
                }
            }else if(sign.equals("1")){
                if(!value2.equals(value)){
                    return false;
                }
            }else if(sign.equals("2")){
                if(!(!value2.equals(value))){
                    return false;
                }
            }else if(sign.equals("3")){
                if(!(value2.compareTo(value)>=0)){
                    return false;
                }
            }else if(sign.equals("4")){
                if(!(value2.compareTo(value)<=0)){
                    return false;
                }
            }else if(sign.equals("5")){
                if(!(value2.compareTo(value)>0)){
                    return false;
                }
            }else if(sign.equals("6")){
               if(!(value2.compareTo(value)<0)){
                    return false;
                }
            }else{
                continue;
            }
        }
        
        return true;
    }
    
    private File getHistoryAccountFile(String accountNo) throws Exception{
    
        String fileName = getHistoryAccountFileName(accountNo);
        if(null == fileName || fileName.equals("")){
            return null;
        }
        fileName = getHistoryAccountFileFullName(fileName);
        if(null == fileName || fileName.equals("")){
            return null;
        }
        File file = new File(fileName);
        
        return file;
    }
    
    private String getHistoryAccountFileName(String accountNo){
        
        int len = accountNo.length();
        String accountNo9Str = "";
        if(len < 9){
            return null;
        }
        
        accountNo9Str = accountNo.substring(0, 9);
        int accountNo9Int = Integer.parseInt(accountNo9Str);
        int accountNoMode = accountNo9Int%4095;
        
        String fileName = "history_"+accountNoMode+".bin";
        
        return fileName;
    }
    
    private String getHistoryAccountFileFullName(String fileName) throws Exception{
    
        String fileFullName = getFileFullNameFromDB(fileName);

        return fileFullName;
    }

    @Override
    public List<String> getAccountsByCard(String cardNo) throws Exception{
        
        File file = getAccountCardFile(cardNo);
        List<String> accountNos = new ArrayList<String>();
        if(file == null || !file.exists()){
            return accountNos;
        }
        //分析文件，得到账号
        BufferedReader br = null;
        try {
            br = new FileBufferReader(file);
            String line = br.readLine();
            while(line != null){  
                String[] cardAccount = line.split("");
                if(cardAccount.length != 2){
                    continue;
                }
                String card = cardAccount[0];
                String account = cardAccount[1];
                if(card.equals(cardNo)){
                    accountNos.add(account);
                }
                line = br.readLine();
            }
        }finally{
            br.close();
        }
        
        return accountNos;
    }
    
    private File getAccountCardFile(String cardNo) throws Exception{
        
        String fileName = getAccountCardFileName(cardNo);
        if(null == fileName || fileName.equals("")){
            return null;
        }
        fileName = getAccountCardFileFullName(fileName);
        if(null == fileName || fileName.equals("")){
            return null;
        }
        File file = new File(fileName);
        
        return file;
    }
    
    private String getAccountCardFileName(String cardNo){
        
        int len = cardNo.length();
        String cardNo9Str = "";
        if(len < 9){
            return null;
        }
        cardNo9Str = cardNo.substring(len-9);
        int cardNo9Int = Integer.parseInt(cardNo9Str);
        int cardNoMode = cardNo9Int%4095;
        
        String fileName = "account_"+cardNoMode+".bin";
        
        return fileName;
    }
    
    private String getAccountCardFileFullName(String fileName) throws Exception {
        
        String fileFullName = getFileFullNameFromDB(fileName);
        
        return fileFullName;
        
    }
    
    private String getFileFullNameFromDB(String fileName) throws Exception{
        
        DbHelper dbHelper = null;
        String fileFullName = null;
        try {
            dbHelper = new DbHelper("", AppConstant.DATA_DBCPHELPER.getConnection());
            String sqlStr = "select InodeInfo.FileFullName," 
		+" InodeInfo.FileSize, "
		+" MagzSlotInfo.MediaType,"
		+" MagzSlotInfo.MediaVolume,"
		+" MagazineInfo.MagzLocationFlag,"
		+" MagazineInfo.MagzNoInCabinet"
                +" from InodeInfo, MagzSlotInfo, MagazineInfo"
                +" WHere InodeInfo.MediaUniqueID = MagzSlotInfo.MediaUniqueID"
                +" AND MagzSlotInfo.MagzLable = MagazineInfo.MagzLable"
                +" AND InodeInfo.FileFullName like '%"+fileName+"%'";
            
            Object[] values = {};
            boolean result = dbHelper.getFirstDocument(sqlStr, values);
            if(result){
                fileFullName = dbHelper.getItemValue("FileFullName");
                
            }  
            logger.info("数据库返回路径："+fileFullName);
        } catch (Exception e) {
            PubUtil.handleException(e, logger);

        } finally {
            PubUtil.finalProcess(dbHelper);
        }
        
        return fileFullName;
    }

    @Override
    public List<String> getAccountsById(String idNo) throws Exception{
        
        return new ArrayList<String>();
    }
    
    private File getAccountIdFile(String idNo){
    
        return null;
    }
    
    private String getAccountIdFileName(String fileName){
        
        return null;
    }
    
    private String getAccountIdFileFullName(String idNo){
        
        return null;
    }
}