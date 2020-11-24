/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.goldsign.escommu.dao;
import com.goldsign.escommu.dbutil.DbHelper;
import com.goldsign.escommu.env.AppConstant;
import com.goldsign.escommu.env.FileConstant;
import com.goldsign.escommu.util.CharUtil;
import com.goldsign.escommu.util.DateHelper;
import com.goldsign.escommu.util.PubUtil;
import com.goldsign.escommu.util.StringUtil;
import com.goldsign.escommu.vo.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.log4j.Logger;

/**
 *
 * @author Administrator
 */
public class CardSectionDao {
    
    private static Logger logger = Logger.getLogger(CardSectionDao.class.getName());
    
    public CardSectionRspVo findCardSections(CardSectionReqVo cardSectionReqVo) throws Exception {
       
        CardSectionRspVo cardSectionRspVo = new CardSectionRspVo();
        cardSectionRspVo.setDeviceId(cardSectionReqVo.getDeviceId());
        cardSectionRspVo.setReqDatetime(cardSectionReqVo.getReqDatetime());
           
        String fileName = "";
        boolean result = false;
        DbHelper dbHelper = null;
        Object[] values = {};
                    
        List<CardSectionVo> cardSectionVos = new ArrayList<CardSectionVo>();
        CardSectionVo cardSectionVo = null;
        try {
            dbHelper = new DbHelper("", AppConstant.DATA_DBCPHELPER.getConnection());
            //********************************************
            String sqlStr = "select bill_no,start_logicno,end_logicno,is_used from "+AppConstant.COM_TK_P+"IC_BC_LOGIC_NO where blank_card_type='2' and record_flag='0' and is_used='0' ";

            result = dbHelper.getFirstDocument(sqlStr, values);
            while (result) {
                cardSectionVo = getResultRecord(dbHelper);
                cardSectionVos.add(cardSectionVo);
                
                result = dbHelper.getNextDocument();
            }
            
            if(cardSectionVos.size()>0){
                //生成文件
                fileName = getCardSectionFileName(cardSectionReqVo);
                if(makeCardSectionFile(cardSectionVos, fileName)){
                     cardSectionRspVo.setReqResult("00");//生成文件成功
                     cardSectionRspVo.setResResult(true);
                }else{
                     cardSectionRspVo.setReqResult("02");//生成文件失败
                }
                cardSectionRspVo.getCardSectionVos().addAll(cardSectionVos);
            }else{
                cardSectionRspVo.setReqResult("01");//没有卡号段
            }
        } catch (Exception e) {
            PubUtil.handleException(e, logger);
            cardSectionRspVo.setReqResult("09");
        } finally {
            PubUtil.finalProcess(dbHelper);

        }
        cardSectionRspVo.setFileName(StringUtil.addEmptyAfter(fileName, 30));

        return cardSectionRspVo;
    }
    
    private String getCardSectionFileName(CardSectionReqVo cardSectionReqVo){
    
        String path = getPath();
        String fileName = FileConstant.CARD_LOGIC_FILE_PRE;
        fileName += cardSectionReqVo.getDeviceId();
        fileName += "." +  DateHelper.dateToYYYYMMDD(new Date());
        fileName += ".";
        int num = 1;
        String numStr = "001";
        while(num<1000){
            numStr = StringUtil.addBeforeZero(num+"", 3);
            File file = new File(path, fileName+numStr);
            if(!file.exists()){
                break;
            }else{
                numStr = "001";
            }
            num++;
        }
        
        fileName += numStr;
        
        return fileName;
    }
    
    private boolean makeCardSectionFile(List<CardSectionVo> cardSectionVos, String fileName){
    
        boolean result = false;
        OutputStream outputStream = null;
        BufferedWriter bufferedWriter = null;
        StringBuffer stringBuffer = new StringBuffer();
        File file = null;
        try {
            String path = getPath();
            file = new File(path, fileName);
            outputStream = new FileOutputStream(file);
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream));
            for (CardSectionVo cardSectionVo : cardSectionVos) {
                String startLogicno = "0000"+"4100"+"0000"+StringUtil.addBeforeZero(cardSectionVo.getStartLogicno(),8);
                String endLogicno = "0000"+"4100"+"0000"+StringUtil.addBeforeZero(cardSectionVo.getEndLogicno(),8);
                String line = startLogicno+new String(FileConstant.TAB)+endLogicno+new String(FileConstant.CRLF_1);
                bufferedWriter.write(line);
                stringBuffer.append(line);
            }
            String crc = getCRC32Value(stringBuffer);
            crc = FileConstant.SVER + crc;
            bufferedWriter.write(crc);
            bufferedWriter.flush();
            
            result = true;
        } catch (IOException e) {
            logger.error(e);
        }finally{
            try{
                if(bufferedWriter!=null){
                    bufferedWriter.close();
                }
                if(outputStream!=null){
                    outputStream.close();
                }
            }catch(Exception e){
                e.printStackTrace();
            }
        }
      
        return result;
    }
    
    public static String getCRC32Value(StringBuffer sb) {

        String crcRet = "";
        if (sb.length() != 0) {
            String s = sb.toString();
            System.out.println("CRC的内容：");
            System.out.print(s);
            byte[] b = null;//new byte[2800000];
            b = s.getBytes();
            long crc32 = CharUtil.getCRC32Value(b);
            String crc = Long.toHexString(crc32);
            for (int i = crc.length(); i < 8; i++) {
                crc = "0" + crc;
            }
            crcRet = crc;
        } else {
            crcRet = "00000000";
        }
        System.out.println("CRC:"+crcRet);
        return crcRet;
    }
    
        /**
     * 取得文件路径
     * 
     * @return 
     */
    private String getPath() {
        String path = AppConstant.PhyLogicFileMakeDir;
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        return file.getPath();
    }
    
    private CardSectionVo  getResultRecord(DbHelper dbHelper) throws Exception{
        
        CardSectionVo cardSectionVo = new CardSectionVo();
        
        //********************************************
        String billNo = dbHelper.getItemValue("bill_no");
        String startLoginno = dbHelper.getItemValue("start_logicno");
        String endLoginno = dbHelper.getItemValue("end_logicno");
        String isUsed = dbHelper.getItemValue("is_used");
        cardSectionVo.setBillNo(billNo);
        cardSectionVo.setStartLogicno(startLoginno);
        cardSectionVo.setEndLogicno(endLoginno);
        cardSectionVo.setIsUsed(isUsed);
        
        return cardSectionVo;
    }
    
    public void updateCardSections(List<CardSectionVo> cardSectionVos) throws Exception {

        DbHelper dbHelper = null;
        
        try {
            dbHelper = new DbHelper("", AppConstant.DATA_DBCPHELPER.getConnection());
            
            String sqlStr = "update "+AppConstant.COM_TK_P+"IC_BC_LOGIC_NO set is_used = '1' where bill_no = ? ";
            dbHelper.setAutoCommit(false);
            for(CardSectionVo cardSectionVo: cardSectionVos){
                dbHelper.executeUpdate(sqlStr, new Object[]{cardSectionVo.getBillNo()});
            }
            dbHelper.commitTran();
        } catch (Exception e) {
            //PubUtil.handleExceptionForTranNoThrow(e,logger,dbHelper);
            PubUtil.handleExceptionForTran(e,logger,dbHelper);
        } finally {
            PubUtil.finalProcessForTran(dbHelper);
        }
    }
}
