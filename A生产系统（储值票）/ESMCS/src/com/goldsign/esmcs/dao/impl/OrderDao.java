/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.goldsign.esmcs.dao.impl;

import com.goldsign.csfrm.dao.impl.BaseDao;
import com.goldsign.csfrm.util.DateHelper;
import com.goldsign.esmcs.dao.IOrderDao;
import com.goldsign.esmcs.env.AppConstant;
import com.goldsign.esmcs.env.ConfigConstant;
import com.goldsign.esmcs.env.FileConstant;
import com.goldsign.esmcs.util.Converter;
import com.goldsign.esmcs.vo.*;
import com.goldsign.publib.io.FileBufferReader;
import com.goldsign.publib.io.FilePrintWriter;
import java.io.*;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.logging.Level;
import org.apache.log4j.Logger;

/**
 * 订单相关文件访问类
 * 
 * @author lenovo
 */
public class OrderDao extends BaseDao implements IOrderDao{

    private static final Logger logger = Logger.getLogger(OrderDao.class.getName());
    
    /**
     * 取已开始（未完成）订单的文件
     * 
     * @param employeeId
     * @param workType
     * @return
     * @throws IOException 
     */
    private File getMakeingFile(String employeeId, String workType) throws IOException{
        
        String fileName = employeeId + AppConstant.DOT_SIGN + workType;
        fileName = AppConstant.sysAppVo.getAppCode()+ AppConstant.DOT_SIGN + fileName;
        Hashtable uploads = (Hashtable) AppConstant.configs.get(ConfigConstant.UploadTag);
        String MAKING_PATH = (String) uploads.get(ConfigConstant.UploadMakingPathTag);
        
        File file = new File(MAKING_PATH + AppConstant.SLASH_LIX_SIGN + fileName);
        
        return file;
    }
    
    /**
     * 取已开始（未完成）订单
     * 
     * @param orderParam
     * @return
     * @throws IOException 
     */
    @Override
    public String getMakingOrder(OrderParam orderParam)throws Exception{
    
        File file = getMakeingFile(orderParam.getEmployeeId(), 
                orderParam.getWorkType());
        if (!file.exists()) {
            return null;
        }
        BufferedReader br = new FileBufferReader(file);
        String line = null;
        try{
            line = br.readLine();
        }finally{
            if(null != br){
                br.close();
            }
        }

        return line;
    }

    /**
     * 取服务器(新)订单的文件
     * 
     * @param employeeId
     * @return
     * @throws IOException 
     */
    private File getFinishFile(String employeeId) throws IOException{
        
        String fileName = employeeId;
        Hashtable uploads = (Hashtable) AppConstant.configs.get(ConfigConstant.UploadTag);
        String FINISH_PATH = (String) uploads.get(ConfigConstant.UploadFinishPathTag);
        
        File file = new File(FINISH_PATH + AppConstant.SLASH_LIX_SIGN + fileName);
        
        return file;
    }
    
    /**
     * 取服务器(新)订单
     * 
     * @param orderParam
     * @return
     * @throws IOException 
     */
    @Override
    public List<Object[]> getFinishOrders(OrderParam orderParam) throws Exception{
        
        File file = getFinishFile(orderParam.getEmployeeId());
        if (!file.exists()) {
            return new ArrayList<Object[]>();
        }
        
        List<Object[]> orders = new ArrayList<Object[]>();
        BufferedReader br = null;
        try {
            br = new FileBufferReader(file);
            String line = br.readLine();
            while(line != null){
                if(line.length()>=10){
                    String workType = line.substring(0, 2);
                    String orderDate = line.substring(2, 10);
                    if((orderParam.getWorkType().isEmpty() || orderParam.getWorkType().equals(workType)) 
                            && orderDate.compareTo(orderParam.getBeginDate())>=0
                            && orderDate.compareTo(orderParam.getEndDate())<=0){
                        line = Converter.getEsWorkTypeDes(workType)+AppConstant.VER_SIGN+line;
                        String[] order = line.split(AppConstant.SEP_VER_SIGN);
                        orders.add(order);
                    }
                }
                line = br.readLine();
            }
        }finally{
            if(null != br){
                br.close();
            } 
        }
        
        return orders;
    }

    /**
     * 写完成订单文件
     * 
     * @param orderVo
     * @throws IOException 
     */
    @Override
    public void writeFinishOrder(OrderVo orderVo) throws IOException{
        
        File file = getFinishFile(orderVo.getEmployeeId());
        if (!file.exists()) {
            createFileDir(file);
            file.createNewFile();
        }
        PrintWriter pw = null;
        try {
            pw = new FilePrintWriter(file, true);
            String orderStr = fmtOrderToFileStr(orderVo, AppConstant.VER_SIGN);
            pw.println(orderStr);
            pw.flush();
        }finally{
            if(null != pw){
                pw.close();
            } 
        }

    }
    
    /**
     * 格式化待写入的订单内容，以指定符号分隔
     * 
     * @param orderVo
     * @param sign
     * @return 
     */
    private String fmtOrderToFileStr(OrderVo orderVo, String sign){
        
        StringBuffer orderVoStringBuffer = new StringBuffer("");
        
        //orderVoStringBuffer.append(orderVo.getStatus()).append(sign);
        orderVoStringBuffer.append(orderVo.getOrderNo()).append(sign);
        orderVoStringBuffer.append(orderVo.getCardTypeCode()).append(sign);
        orderVoStringBuffer.append(orderVo.getCardTypeDesc()).append(sign);
        orderVoStringBuffer.append(orderVo.getCardEffTime()).append(sign);
        orderVoStringBuffer.append(orderVo.getPrintMoney()).append(sign);
        orderVoStringBuffer.append(orderVo.getDeposit()).append(sign);
        orderVoStringBuffer.append(orderVo.getBeginReqNo()).append(sign);
        orderVoStringBuffer.append(orderVo.getEndReqNo()).append(sign);
        orderVoStringBuffer.append(orderVo.getBeginSeqNo()).append(sign);
        orderVoStringBuffer.append(orderVo.getEndSeqNo()).append(sign);
        orderVoStringBuffer.append(orderVo.getDate()).append(sign);
        orderVoStringBuffer.append(orderVo.getOrderNum()).append(sign);
        orderVoStringBuffer.append(orderVo.getGoodCardNum()).append(sign);
        orderVoStringBuffer.append(orderVo.getBadCardNum()).append(sign);
        orderVoStringBuffer.append(orderVo.getUnFinishNum()).append(sign);
        orderVoStringBuffer.append(orderVo.getIdCode()).append(sign);
        orderVoStringBuffer.append(orderVo.getLineCode()).append(sign);
        orderVoStringBuffer.append(orderVo.getStationCode()).append(sign);
        orderVoStringBuffer.append(orderVo.getTctEffBeginTime()).append(sign);
        orderVoStringBuffer.append(orderVo.getTctEffTime()).append(sign);
        orderVoStringBuffer.append(orderVo.getLimitExitLineCode()).append(sign);
        orderVoStringBuffer.append(orderVo.getLimitExitStationCode()).append(sign);
        orderVoStringBuffer.append(orderVo.getLimitMode()).append(sign);
        orderVoStringBuffer.append(orderVo.getSaleFlag()).append(sign);
        orderVoStringBuffer.append(orderVo.getTestFlag()).append(sign);
        orderVoStringBuffer.append(orderVo.getMaxRecharge());
       
        return orderVoStringBuffer.toString();
    }
   
    /**
     * 写未完成订单文件
     * 
     * @param orderVo
     * @throws IOException 
     */
    @Override
    public void writeMakingOrder(OrderVo orderVo) throws IOException {
        
        File file = getMakeingFile(orderVo.getEmployeeId(), orderVo.getWorkType());
        if (!file.exists()) {
            createFileDir(file);
            file.createNewFile();
        }
        PrintWriter pw = null;
        try {
            pw = new FilePrintWriter(file);
            String orderStr = fmtOrderToFileStr(orderVo, AppConstant.VER_SIGN);
            pw.println(orderStr);
            pw.flush();
        }finally{
            if(null != pw){
                pw.close();
            }
        }
    }
    
    /**
     * 删除未完成订单文件
     * 
     * @param orderVo
     * @throws IOException 
     */
    @Override
    public void deleteMakingOrder(OrderVo orderVo) throws IOException {
        
        File file = getMakeingFile(orderVo.getEmployeeId(), orderVo.getWorkType());
        
        if(file.exists()){
            file.delete();
        }
    }

    /**
     * 取制卡好卡订单文件
     * 
     * @param orderNo
     * @return 
     */
    private File getGoodFile(String orderNo){
        
        Hashtable commons = (Hashtable) AppConstant.configs.get(ConfigConstant.CommonTag);
        String deviceNo = (String) commons.get(ConfigConstant.CommonDeviceNoTag);
        String fileName = FileConstant.ES_ORDER_FILENAME_PRE + deviceNo 
                + FileConstant.ES_ORDER_FILENAME_SEP + orderNo;
        Hashtable uploads = (Hashtable) AppConstant.configs.get(ConfigConstant.UploadTag);
        String GOOD_CARD_PATH = (String) uploads.get(ConfigConstant.UploadGoodCardPathTag);
        fileName = GOOD_CARD_PATH + AppConstant.SLASH_LIX_SIGN + fileName;
        
        File file = new File(fileName);
        
        return file;
    }
    
    /**
     * 写制卡好卡订单
     * 
     * @param esOrderDetailVo
     * @throws IOException 
     */
    @Override
    public void writeGoodOrder(EsOrderDetailVo esOrderDetailVo) throws IOException {
        
        File file = getGoodFile(esOrderDetailVo.getOrderNo());
        if (!file.exists()) {
            createFileDir(file);
            file.createNewFile();
        }
        PrintWriter pw = null;
        try {
            pw = new FilePrintWriter(file, true);
            String goodOrderStr = fmtOrderToEsFileDetail(esOrderDetailVo);
            pw.println(goodOrderStr);
            pw.flush();
        }finally{
            if(null != pw){
                pw.close();
            }
        }
    }
    
    /**
     * 格式化等写入的ES订单明细内容
     * 
     * @param esOrderDetailVo
     * @return 
     */
    private String fmtOrderToEsFileDetail(EsOrderDetailVo esOrderDetailVo){
        
        StringBuffer orderVoStringBuffer = new StringBuffer("");

        orderVoStringBuffer.append(esOrderDetailVo.getWorkType()).append(FileConstant.TAB);
        orderVoStringBuffer.append(esOrderDetailVo.getOrderNo()).append(FileConstant.TAB);
        orderVoStringBuffer.append(esOrderDetailVo.getCardTypeCode()).append(FileConstant.TAB);
        orderVoStringBuffer.append(esOrderDetailVo.getReqNo()).append(FileConstant.TAB);
        orderVoStringBuffer.append(esOrderDetailVo.getLogicNo()).append(FileConstant.TAB);
        orderVoStringBuffer.append(esOrderDetailVo.getPrintNo()).append(FileConstant.TAB);
        orderVoStringBuffer.append(esOrderDetailVo.getPhyNo()).append(FileConstant.TAB);
        orderVoStringBuffer.append(esOrderDetailVo.getDate()).append(FileConstant.TAB);
        orderVoStringBuffer.append(esOrderDetailVo.getPrintMoney()).append(FileConstant.TAB);
        orderVoStringBuffer.append(esOrderDetailVo.getCardEffTime()).append(FileConstant.TAB);
        orderVoStringBuffer.append(esOrderDetailVo.getSamNo()).append(FileConstant.TAB);
        orderVoStringBuffer.append(esOrderDetailVo.getLineCode()).append(FileConstant.TAB);
        orderVoStringBuffer.append(esOrderDetailVo.getStationCode()).append(FileConstant.TAB);
        orderVoStringBuffer.append(esOrderDetailVo.getTctEffBeginTime()).append(FileConstant.TAB);
        orderVoStringBuffer.append(esOrderDetailVo.getTctEffTime()).append(FileConstant.TAB);
        orderVoStringBuffer.append(esOrderDetailVo.getLimitExitLineCode()).append(FileConstant.TAB);
        orderVoStringBuffer.append(esOrderDetailVo.getLimitExitStationCode()).append(FileConstant.TAB);
        //hwj add 20160107增加卡商代码和手机号码
        orderVoStringBuffer.append(esOrderDetailVo.getLimitMode()).append(FileConstant.TAB);
        orderVoStringBuffer.append(esOrderDetailVo.getCardProducerCode()).append(FileConstant.TAB);
        orderVoStringBuffer.append(esOrderDetailVo.getPhoneNo());//.append(FileConstant.CRLF);
                
        return orderVoStringBuffer.toString();
    }

    /**
     * 取制卡坏卡订单文件
     * 
     * @param orderNo
     * @return 
     */
    private File getBadFile(String orderNo){
        
        Hashtable commons = (Hashtable) AppConstant.configs.get(ConfigConstant.CommonTag);
        String deviceNo = (String) commons.get(ConfigConstant.CommonDeviceNoTag);
        String fileName = FileConstant.ES_ORDER_FILENAME_PRE + deviceNo 
                + FileConstant.ES_ORDER_FILENAME_SEP + orderNo;
        Hashtable uploads = (Hashtable) AppConstant.configs.get(ConfigConstant.UploadTag);
        String BAD_CARD_PATH = (String) uploads.get(ConfigConstant.UploadBadCardPathTag);
        fileName = BAD_CARD_PATH + AppConstant.SLASH_LIX_SIGN + fileName;
        
        File file = new File(fileName);
        
        return file;
    }
        
    /**
     * 写制卡坏卡订单
     * 
     * @param esOrderDetailVo
     * @throws IOException 
     */
    @Override
    public void writeBadOrder(EsOrderDetailVo esOrderDetailVo) 
            throws IOException {
        
        File file = getBadFile(esOrderDetailVo.getOrderNo());
        if (!file.exists()) {
            createFileDir(file);
            file.createNewFile();
        }
        PrintWriter pw = null;
        try {
            if(!file.exists()){
                file.createNewFile();
            }
            pw = new FilePrintWriter(file, true);
            String badOrderStr = fmtOrderToEsFileDetail(esOrderDetailVo);
            badOrderStr = badOrderStr + FileConstant.TAB + esOrderDetailVo.getCode()+" ";
            badOrderStr = badOrderStr + FileConstant.TAB + esOrderDetailVo.getMsg()+" ";
            pw.println(badOrderStr);
            pw.flush();
        }finally{
            if(null != pw){
                pw.close();
            }
        }

    }

    /**
     * 取得未通知的订单消息文件
     * 
     * @return 
     */
    private File getUnNoticeFile(){
        
        Hashtable uploads = (Hashtable) AppConstant.configs.get(ConfigConstant.UploadTag);
        String ERROR_FILE = (String) uploads.get(ConfigConstant.UploadErrorFileTag);
        String fileName = ERROR_FILE;
        
        File file = new File(fileName);
                
        return file;
    }
    
        /**limj
     * 取得审计错误文件记录文件
     * 
     * @return 
     */
    private File getrecordAuditAndErrorFile(){
        
        Hashtable uploads = (Hashtable) AppConstant.configs.get(ConfigConstant.UploadTag);
        String ERROR_FILE = (String) uploads.get(ConfigConstant.RecordFileTag);
        String fileName = ERROR_FILE;
        
        File file = new File(fileName);
                
        return file;
    }
    
      /**
     * 获取审计和错误文件
     * @return
     * @throws IOException 
     */
    private File getAuditFile(String filename) throws IOException {
        
        Hashtable uploads = (Hashtable) AppConstant.configs.get(ConfigConstant.DownloadTag);
        String AUDIT_FILE_PATH = (String) uploads.get(ConfigConstant.FtpAuditLocalPathTag);
        AUDIT_FILE_PATH = AUDIT_FILE_PATH+"/"+filename;
        File file = new File(AUDIT_FILE_PATH);
        
        return file;
    }
    
    /**
     * 取得未通知的订单更新前消息文件
     * 
     * @return 
     */
    private File getUpdateUnNoticeFile(String str){
        
        Hashtable uploads = (Hashtable) AppConstant.configs.get(ConfigConstant.UploadTag);
        String ERROR_FILE = (String) uploads.get(ConfigConstant.UploadErrorFileTag)+str;
        String fileName = ERROR_FILE;
        
        File file = new File(fileName);
                
        return file;
    }
    
    /**
     * 取得未通知的订单消息，取后删除内容
     * @return
     * @throws IOException 
     */
    @Override
    public List<String> getUnNoticeOrderMsg() throws Exception {
        
        File file = getUnNoticeFile();
        if(!file.exists()){
            return new ArrayList<String>();
        }
        
        List<String> unnoticeMsgs = new ArrayList<String>();
        BufferedReader br = null;
        try {
            br = new FileBufferReader(file);
            
            String line = br.readLine();
            while(line != null){
                String[] arr = line.split(AppConstant.MAO_SIGN);
                if (null != arr && arr.length == 3) {
                    unnoticeMsgs.add(arr[0]+AppConstant.MAO_SIGN+arr[1]);
                }
                line = br.readLine();
            }
        }finally{
            if(null != br){
                br.close();
            }
        }
        
        FileWriter fw = null;
        try{
            fw = new FileWriter(file);
            fw.write("");
        }finally{
            if(null != fw){
                fw.close();
            } 
        }
 
        return unnoticeMsgs;
    }

    /**
     * 写通知失败订单消息
     * 
     * @param fileName
     * @throws IOException 
     */
    @Override
    public void writeUnNoticeOrderMsg(String fileName) throws IOException {
        
        File file = getUnNoticeFile();
        if(!file.exists()){
            file.createNewFile();
        }
      
        PrintWriter pw = null;
        try {
            pw = new FilePrintWriter(new FileWriter(file, true));
            pw.println(fileName);
            pw.flush();
        }finally{
            if(null != pw){
                pw.close();
            } 
        }
    }
  
    /**
     * 手工上传通知后更新订单消息
     * 
     * @param fileName
     * @throws IOException 
     */
   public boolean updateNoticeOrderMsg(List<String> fileName) throws IOException,Exception{
       File file1 = getUnNoticeFile();
       File file2 = getUpdateUnNoticeFile("_1");
       File file3 = getAuditFile();
       File file4 = getUpdateUnNoticeFile("_shougscbf");
       if(!file1.exists()){
            file1.createNewFile();
        }
       if(!file2.exists()){
            file2.createNewFile();
        }
       if(!file3.exists()){
            file3.createNewFile();
        }
        PrintWriter pw1 = null;
        PrintWriter pw2 = null;
        BufferedReader br = null;
        List<String> unnoticeMsgs = new ArrayList<String>();
        boolean dealSuccess = false;
        try{
            br = new FileBufferReader(file1);
            String line = br.readLine();
            boolean flag = true;
            while(null!=line){
                flag = true;
                for(String str:fileName){
                    if(line.contains(str)){
                        flag = false;
                        break;
                    }
                }
                if(flag){
                  unnoticeMsgs.add(line);  
                }                
                line = br.readLine();
            }
            pw1 = new FilePrintWriter(new FileWriter(file2, false));
            for(String str1:unnoticeMsgs){
                pw1.println(str1);
                pw1.flush();
            }
            pw2 = new FilePrintWriter(new FileWriter(file3, true));
            for(String str1:fileName){
                pw2.println(str1);
                pw2.flush();
            }
            dealSuccess = true;
        }finally{
            if(null!= pw1){
                pw1.close();    
            }
            if(null!= pw2){
                pw2.close();    
            }
            if(null!= br){
                br.close();
            }
        }
        if(dealSuccess){
            if(file4.exists()){
                file4.delete();
            }
            file1.renameTo(file4);
            file2.renameTo(file1);
        }
        return dealSuccess;
   }
    
    /**
     * 取得ES订单文件的写入流
     * 
     * @param orderNo
     * @return
     * @throws IOException 
     */
    private PrintWriter getEsFileWriter(String orderNo) throws IOException{
        
        String fileName = getEsFileName(orderNo);
        Hashtable uploads = (Hashtable) AppConstant.configs.get(ConfigConstant.UploadTag);
        String ORDER_PATH = (String) uploads.get(ConfigConstant.UploadOrderPathTag);
        fileName = ORDER_PATH + AppConstant.SLASH_LIX_SIGN + fileName;
        
        File file = new File(fileName);
        createFileDir(file); 
        
        PrintWriter pw = null;
        pw = new FilePrintWriter(file);
        
        return pw;
    }
    
    private String getEsFileName(String orderNo){
    
        Hashtable commons = (Hashtable) AppConstant.configs.get(ConfigConstant.CommonTag);
        String deviceNo = (String) commons.get(ConfigConstant.CommonDeviceNoTag);
        String fileName = FileConstant.ES_ORDER_FILENAME_PRE + deviceNo 
                + FileConstant.ES_ORDER_FILENAME_SEP + orderNo;
        
        return fileName;
    }
    
    /**
     * 格式化等写入的ES订单统计内容
     * 
     * @param orderStatisVo
     * @return 
     */
    private String fmtOrderToEsFileStatis(EsOrderStatisVo orderStatisVo){
    
        StringBuffer statis = new StringBuffer("");
        statis.append(orderStatisVo.getWorkType()).append(FileConstant.TAB);
        statis.append(orderStatisVo.getOrderNo()).append(FileConstant.TAB);
        statis.append(orderStatisVo.getOrderNum()).append(FileConstant.TAB);
        statis.append(orderStatisVo.getGoodCardNum()).append(FileConstant.TAB);
        statis.append(orderStatisVo.getUnFinishNum()).append(FileConstant.TAB);
        statis.append(orderStatisVo.getBadCardNum()).append(FileConstant.TAB);
        statis.append(orderStatisVo.getDate()).append(FileConstant.TAB);
        statis.append(orderStatisVo.getFinishFlag()).append(FileConstant.TAB);
        statis.append(orderStatisVo.getRemark());
        
        return statis.toString();
    }
    
    /**
     * 格式化等写入的ES订单明细头
     * 
     * @param orderStatisVo
     * @return 
     */
    private String fmtOrderToEsFileDetailNum(EsOrderStatisVo orderStatisVo){
    
        StringBuffer detailNum = new StringBuffer("");
        detailNum.append(orderStatisVo.getGoodCardNum());
        
        return detailNum.toString();
    }
    
    /**
     * 生成ES订单文件
     * 
     * @param orderStatisVo
     * @throws IOException 
     */
    @Override
    public void makeEsFileOrder(EsOrderStatisVo orderStatisVo) throws Exception {
        
        File goodFile = getGoodFile(orderStatisVo.getOrderNo());
        if (!goodFile.exists()) {
            createFileDir(goodFile);
            goodFile.createNewFile();
            //return;
        }
        PrintWriter pw = null;
        BufferedReader br = null;
        StringBuffer stringBuffer = new StringBuffer();
        try {
            pw = getEsFileWriter(orderStatisVo.getOrderNo());
            String statisLine = FileConstant.STAT+fmtOrderToEsFileStatis(orderStatisVo);
            statisLine = statisLine+FileConstant.CRLF;
            pw.print(statisLine);
            stringBuffer.append(statisLine);
            String detailNumLine = FileConstant.LISTNUM+fmtOrderToEsFileDetailNum(orderStatisVo);
            detailNumLine = detailNumLine+FileConstant.CRLF;
            pw.print(detailNumLine);
            stringBuffer.append(detailNumLine);
            br = new FileBufferReader(goodFile);
            String line = br.readLine();
            while(line != null){
                line = FileConstant.LIST + line;
                line = line+FileConstant.CRLF;
                pw.print(line);
                stringBuffer.append(line);
                line = br.readLine();
            }
            String crc = Converter.getCRC32Value(stringBuffer);
            crc = FileConstant.CRC + crc;// + FileConstant.CRLF;
            pw.print(crc);
            pw.flush();
        }finally{
            if(null != br){
                br.close();
            }
            if(null != pw){
                pw.close();
            }
        }
    }

    /**
     * 取审核文件
     * 
     * @return
     * @throws IOException 
     */
    private File getAuditFile() throws IOException {

        Hashtable uploads = (Hashtable) AppConstant.configs.get(ConfigConstant.UploadTag);
        String AUDIT_FILE = (String) uploads.get(ConfigConstant.UploadAuditFileTag);
        
        File file = new File(AUDIT_FILE);
        
        return file;
    }
        
    /**
     * 写审核文件
     * 
     * @param orderNo
     * @throws IOException 
     */
    @Override
    public void writeAuditFile(String fileName) throws IOException {
        
        File file = getAuditFile();
        if (!file.exists()) {
            file.createNewFile();
        }

        PrintWriter pw = null;
        try {
            pw = new FilePrintWriter(file, true);
            pw.println(fileName);
            pw.flush();
        } finally {
            if(null != pw){
                pw.close();
            } 
        }
    }

    /**
     * 取ES已通知订单文件
     * 
     * @param noticeParam
     * @return
     * @throws IOException 
     */
    @Override
    public List<Object[]> getEsNoticeFiles(NoticeParam noticeParam) throws Exception {
        
        File file = getAuditFile();
        if (!file.exists()) {
            return new ArrayList<Object[]>();
        }
        
        List<Object[]> orders = new ArrayList<Object[]>();
        BufferedReader br = null;
        try {
            br = new FileBufferReader(file);
            String line = br.readLine();
            while(line != null){
                String[] arr = line.split(AppConstant.MAO_SIGN);
                if(null != arr && arr.length == 3){
                    if(arr[2].length()>=8){
                        String noticeDate = arr[2].substring(0, 8);
                        if(noticeDate.compareTo(noticeParam.getBeginDate())>=0
                                && noticeDate.compareTo(noticeParam.getEndDate())<=0){
                            orders.add(new Object[]{arr[0], arr[1], arr[2]});
                        }
                    }  
                }
                line = br.readLine();
            }
        }finally{
            if(null != br){
                br.close();
            }
        }
        
        return orders;
    }

    /**
     * 取ES未通知订单文件
     * 
     * @param noticeParam
     * @return
     * @throws IOException 
     */
    @Override
    public List<Object[]> getEsUnNoticeFiles(NoticeParam noticeParam) throws Exception {
        
        File file = getUnNoticeFile();
        if (!file.exists()) {
            return new ArrayList<Object[]>();
        }
        
        List<Object[]> orders = new ArrayList<Object[]>();
        BufferedReader br = null;
        try {
            br = new FileBufferReader(file);
            String line = br.readLine();
            while(line != null){
                String[] arr = line.split(AppConstant.MAO_SIGN);
                if (null != arr && arr.length == 3) {
                    if(arr[2].length()>=8){
                        String noticeDate = arr[2].substring(0, 8);
                        if (noticeDate.compareTo(noticeParam.getBeginDate()) >= 0
                                && noticeDate.compareTo(noticeParam.getEndDate()) <= 0) {
                            orders.add(new Object[]{arr[0], arr[1], arr[2]});
                        }
                    }
                }                
                line = br.readLine();
            }
        }finally{
            if(null != br){
                br.close();
            } 
        }
        
        return orders;
    }

    //创建文件所在文件夹
    private void createFileDir(File file) {
        
        File fDir = file.getParentFile();
        if(!fDir.exists()){
            fDir.mkdirs();
        }
    }

    /**
     * 取好卡订单文件
     * 
     * @param orderNo
     * @return
     * @throws Exception 
     */
    @Override
    public List<Object[]> getGoodOrder(String orderNo) throws Exception {
        
        File file = getGoodFile(orderNo);
        if (!file.exists()) {
            return new ArrayList<Object[]>();
        }
        
        List<Object[]> orders = new ArrayList<Object[]>();
        BufferedReader br = null;
        try {
            br = new FileBufferReader(file);
            String line = br.readLine();
            while(line != null){
                String[] order = line.split(AppConstant.SEP_TAB_SIGN);
                orders.add(order);
                line = br.readLine();
            }
        }finally{
            if(null != br){
                br.close();
            }
        }
        
        return orders;
    }

    /**
     * 取坏卡订单文件
     * 
     * @param orderNo
     * @return
     * @throws Exception 
     */
    @Override
    public List<Object[]> getBadOrder(String orderNo) throws Exception {
        
        File file = getBadFile(orderNo);
        if (!file.exists()) {
            return new ArrayList<Object[]>();
        }
        
        List<Object[]> orders = new ArrayList<Object[]>();
        BufferedReader br = null;
        try {
            br = new FileBufferReader(file);
            String line = br.readLine();
            while(line != null){
                String[] order = line.split(AppConstant.SEP_TAB_SIGN);
                orders.add(order);
                line = br.readLine();
            }
        }finally{
            if(null != br){
                br.close();
            }
        }
        
        return orders;
    }
    
    /**limj
     * 获取当天下载的审计错误文件明细
     * 
     * @param noticeParam
     * @return
     * @throws IOException 
     */
    public List<String> getEsAuditContentFiles(List<String> filenames) throws Exception {
        List<String> auditList = new ArrayList<String>();
        BufferedReader br = null;
        for(String filename:filenames){
        File file = getAuditFile(filename);
        if (!file.exists()) {
            return null;
        }
                
        try {
            br = new FileBufferReader(file);
            String line = br.readLine();           
            while(line != null){
                if(line.startsWith("ES")){
                   auditList.add(line);
                }
                line = br.readLine();
            }
        }finally{
            if(null != br){
                br.close();
            }
        }
        }
        return auditList;
    }

      /**limj
     * 记录已获取过的审计和错误文件
     * 
     * @param fileName
     * @throws IOException 
     */
    public void recordDownAuditAndErrorFile(List<String> fileName) throws IOException,Exception {
        
        File file = getrecordAuditAndErrorFile();
        if(!file.exists()){
            file.createNewFile();
        }
      
        PrintWriter pw = null;
        BufferedReader br = null;
        try {
                br = new FileBufferReader(file);
                String line = br.readLine();
                String str[] = null;
                List<String> recordList = new ArrayList<String>();
                while(null!=line){
                    str=line.split("##");
                    if(str[1].equals(DateHelper.curDateToStr8yyyyMMdd())){
                        recordList.add(str[0]);
                    }
                    line = br.readLine();
                }
                pw = new FilePrintWriter(new FileWriter(file, true));
                for(String str1:fileName){
                    if(!recordList.contains(str1)){
                        pw.println(str1+"##"+DateHelper.curDateToStr8yyyyMMdd());
                        pw.flush();
                        AppConstant.CHECK_RECORD.add(str1);
                    }
                }
        }finally{
            if(null != pw){
                pw.close();
            } 
        }
    }
}
