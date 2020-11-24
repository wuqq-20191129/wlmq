/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.escommu.util;

import com.goldsign.escommu.env.AppConstant;
import com.goldsign.escommu.env.CardConstant;
import com.goldsign.escommu.env.FileConstant;
import com.goldsign.escommu.dao.FileCheckDao;
import com.goldsign.escommu.dao.FileErrorDao;
import com.goldsign.escommu.dao.OrderInfoDao;
import com.goldsign.escommu.dbutil.DbHelper;
import com.goldsign.escommu.exception.*;
import com.goldsign.escommu.vo.FileErrorVo;
import com.goldsign.escommu.vo.FileListVo;
import com.goldsign.escommu.vo.FileStatVo;
import com.goldsign.escommu.vo.FindFileVo;
import java.io.*;
import java.util.Date;
import java.util.TreeSet;
import java.util.Vector;
import java.util.logging.Level;
import org.apache.log4j.Logger;

/**
 *
 * @author Administrator
 */
public class FileUtil {

    private static Logger logger = Logger.getLogger(FileUtil.class.getName());

    public void processFilesIllegalForOne(String fileNameIllegal, String errorCode) throws Exception {
        //写错误文件列表
        FileErrorDao dao = new FileErrorDao();
        dao.insertErrorFilesForOne(fileNameIllegal, errorCode);
        //文件移至错误文件目录
        if (this.isNeedMoveFile(errorCode)) {
            this.moveFilesToErrorDirForOne(fileNameIllegal);
        }
    }

    private boolean isNeedMoveFile(String errorCode) {
        if (errorCode == null || errorCode.length() == 0) {
            return false;
        }
        if (errorCode.equals(FileConstant.FILE_ERRO_CODE_FILE_NOTEXIST)
                || errorCode.equals(FileConstant.FILE_ERRO_CODE_FILE_NAME)) {
            return false;
        }
        return true;
    }

    private boolean isNeedBcp(FileStatVo statVo) {

        if (statVo == null || statVo.getStatNumDetail() == 0) {
            return false;
        }
        return true;
    }
    
    private boolean isMBProductType(String fileName){
    
        return fileName.startsWith(FileConstant.KC_PDU_TYPE_PRE);
    }
    
    private String getMBProductType(String fileName){
    
        if(isMBProductType(fileName)){
            return "1";//空充
        }else{
            return "0";//其它
        }
    }

    public void processFilesLegalForOne(String fileName)
            throws
            FileFormatException, FileNotFoundException, IOException,
            FileTotalDetailNoSameException, FileCRCException,
            Exception {
        //校验文件内容 生成BCP文件 移动文件
        boolean isBcpFile = false;
        FileStatVo statVo = this.convertFileToBuff(AppConstant.FtpLocalDir, fileName, AppConstant.FtpLocalDirBcp);
        //BCP导入文件
        if (this.isNeedBcp(statVo)) {
            this.bcpFile(fileName);
            isBcpFile = true;
        }
        //更新订单相关信息
        DbHelper dbHelper = null;
        try {
            dbHelper = new DbHelper("", AppConstant.DATA_DBCPHELPER.getConnection());
            if (statVo.isResetNum()) {
                this.insertOrderNumChange(statVo, dbHelper);
                logger.info("订单数据作改变由" + statVo.getResetNumBefore() + "改为" + statVo.getResetNum());
            }
            //如有重号卡，记录重号记录及更新订单备注
            if (statVo.isIsExistReapeat()) {
                this.insertReapeatRecords(statVo, dbHelper);
                this.updateOrderRemark(statVo, dbHelper);
            }
            this.genProduceBill(statVo);
            logger.info("成功生成生产单");

        } catch (Exception e) {
            //回滚BCP导入数据
            if (isBcpFile) {
                this.deleteRecordsFromBcp(statVo, dbHelper);
            }

            PubUtil.handleException(e, logger);
        } finally {
            PubUtil.finalProcess(dbHelper);

        }

    }
    /*
     * private int updateOrderInfo(FileStatVo vo,DbHelper dbHelper) throws
     * Exception { OrderInfoDao dao = new OrderInfoDao(); return
     * dao.updateOrderInfo(vo,dbHelper); }
     */

    private int deleteRecordsFromBcp(FileStatVo vo, DbHelper dbHelper) throws Exception {
        OrderInfoDao dao = new OrderInfoDao();
        return dao.deleteRecordsFromBcp(vo, dbHelper);
    }

    private int insertOrderNumChange(FileStatVo vo, DbHelper dbHelper) throws Exception {
        OrderInfoDao dao = new OrderInfoDao();
        return dao.insertOrderNumChange(vo, dbHelper);
    }

    private int insertReapeatRecords(FileStatVo vo, DbHelper dbHelper) throws Exception {
        OrderInfoDao dao = new OrderInfoDao();
        return dao.insertReapeatRecords(vo, dbHelper);
    }

    private int updateOrderRemark(FileStatVo vo, DbHelper dbHelper) throws Exception {
        OrderInfoDao dao = new OrderInfoDao();
        return dao.updateOrderRemark(vo, dbHelper);
    }

    /*
     * private boolean isFinishPlan(FileStatVo vo) throws Exception {
     * OrderInfoDao dao = new OrderInfoDao(); return
     * dao.isFinishPlan(vo.getOrderNo()); }
     */
    private void genProduceBill(FileStatVo vo) throws Exception {
        OrderInfoDao dao = new OrderInfoDao();
        dao.genProduceBill(vo);
    }

    public String getTableBcp(String fileName) throws Exception {
        String workType = this.getWorktype(fileName);
        if (workType.equals(FileConstant.WORKTYPE_INITIAL)) {
            if(this.isMBProductType(fileName)){
                return FileConstant.TABLE_MB_INITIAL;
            }
            return FileConstant.TABLE_ES_INITIAL;
        }
        if (workType.equals(FileConstant.WORKTYPE_HUNCH)) {
            if(this.isMBProductType(fileName)){
                return FileConstant.TABLE_MB_HUNCH;
            }
            return FileConstant.TABLE_ES_HUNCH;
        }
        if (workType.equals(FileConstant.WORKTYPE_AGAIN)) {
            if(this.isMBProductType(fileName)){
                return FileConstant.TABLE_MB_AGAIN;
            }
            return FileConstant.TABLE_ES_AGAIN;
        }
        if (workType.equals(FileConstant.WORKTYPE_LOGOUT)) {
            if(this.isMBProductType(fileName)){
                return FileConstant.TABLE_MB_LOGOUT;
            }
            return FileConstant.TABLE_ES_LOGOUT;
        }
        throw new Exception("没有找到文件" + fileName + "对应的BCP表");


    }

    private void bcpFile(String fileName) throws Exception {
        BcpUtil util = new BcpUtil();
        String fileNameBcp = this.getPathBcp(AppConstant.FtpLocalDirBcp, fileName) + this.getFileNameBcp(fileName);
        String bcp = util.getBcp(AppConstant.importConfig, this.getTableBcp(fileName), fileNameBcp, "");
        util.bcpFile(bcp, 0);


    }

    private String getFileNameDev(String fileName) {
        //hwj modify 20160107 空充
        //if(this.isMBProductType(fileName)){
        //    return fileName.substring(4, 7);
        //}else{
            return fileName.substring(2, 5);
        //}
        //String[] arr = fileName.split(FileConstant.FILE_NAME_DELIM);
        //return arr[0].substring(arr[0].length()-3);
    }

    private String getFileNameOrderNo(String fileName) {
        //hwj modify 20160107 空充
        //if(this.isMBProductType(fileName)){
        //    return fileName.substring(8);
        //}else{
            return fileName.substring(6);
        //}
        //String[] arr = fileName.split(FileConstant.FILE_NAME_DELIM);
        //return arr[1];
    }

    public boolean checkFileByFileLevel(String fileName) throws FileNameException, Exception {
        if (fileName == null || fileName.length() == 0 || fileName.length() != FileConstant.FILE_NAME_LENGTH) {//hwj modify 20160107 空充
            throw new FileNameException("文件名长度" + fileName.length() + "非法");
        }
        if (!fileName.startsWith(FileConstant.FILE_NAME_PREFIX) && !fileName.startsWith(FileConstant.FILE_NAME_PREFIX2)) {
            throw new FileNameException("文件名的前缀" + fileName + "非法");
        }
        /*
        //hwj modify 20160107 空充
        if(fileName.startsWith(FileConstant.FILE_NAME_PREFIX) && fileName.length() != FileConstant.FILE_NAME_LENGTH){
            throw new FileNameException("ES文件名长度" + fileName.length() + "非法");
        }
        //if(fileName.startsWith(FileConstant.FILE_NAME_PREFIX2) && fileName.length() != FileConstant.FILE_NAME_LENGTH+2){
        if(fileName.startsWith(FileConstant.FILE_NAME_PREFIX2) && fileName.length() != FileConstant.FILE_NAME_LENGTH){
            throw new FileNameException("MB文件名长度" + fileName.length() + "非法");
        }*/
        
        FileCheckDao dao = new FileCheckDao();
        if (!dao.checkForDevice(this.getFileNameDev(fileName))) {
            throw new FileNameException("文件名的设备号" + this.getFileNameDev(fileName) + "不存在");
        }
        if (!this.isMBProductType(fileName) && !dao.checkForOrder(this.getFileNameOrderNo(fileName))) {
            throw new FileNameException("文件名的订单号" + this.getFileNameOrderNo(fileName) + "非法或订单状态非法，订单状态为0");
        }
        if (!dao.checkForOrderStatus(this.getFileNameOrderNo(fileName), isMBProductType(fileName))) {
            throw new FileNameException("文件名的订单号" + this.getFileNameOrderNo(fileName) + "订单已正常处理，无需再处理");
        }

        return true;
    }
    
    public void checkFile(FindFileVo vo) throws FileNameException, Exception {
        String fileName = vo.getFileName();
        vo.setStatus(AppConstant.FTP_STATUS_SUCCESS);
        vo.setRemark("");
        if (fileName == null || fileName.length() == 0 || fileName.length() != FileConstant.FILE_NAME_LENGTH) {//hwj modify 20160107 空充
            vo.setRemark("文件名长度" + fileName.length() + "非法");
            vo.setStatus(AppConstant.FTP_STATUS_ILLEGAL);
        }
        if (!fileName.startsWith(FileConstant.FILE_NAME_PREFIX) && !fileName.startsWith(FileConstant.FILE_NAME_PREFIX2)) {
            vo.setRemark("文件名的前缀" + fileName + "非法");
            vo.setStatus(AppConstant.FTP_STATUS_ILLEGAL);
        }
        
        FileCheckDao dao = new FileCheckDao();
        if (!dao.checkForDevice(this.getFileNameDev(fileName))) {
            vo.setRemark("文件名的设备号" + this.getFileNameDev(fileName) + "不存在");
            vo.setStatus(AppConstant.FTP_STATUS_ILLEGAL);
        }
        if (!this.isMBProductType(fileName) && !dao.checkForOrder(this.getFileNameOrderNo(fileName))) {
            vo.setRemark("文件名的订单号" + this.getFileNameOrderNo(fileName) + "非法或订单状态非法，订单状态为0");
            vo.setStatus(AppConstant.FTP_STATUS_ILLEGAL);
        }
        if (!dao.checkForOrderStatus(this.getFileNameOrderNo(fileName), isMBProductType(fileName))) {
            vo.setRemark("文件名的订单号" + this.getFileNameOrderNo(fileName) + "订单已正常处理，无需再处理");
            vo.setStatus(AppConstant.FTP_STATUS_ILLEGAL);
        }
    }

    private void moveFilesToErrorDirForOne(String fileName) {

        String pathError = this.getDestinationPathByDate(AppConstant.FtpLocalDirError);
        this.moveFileToErrorDir(AppConstant.FtpLocalDir, fileName, pathError);


    }

    private String getPath(String path) {
        String sep = "/";
        System.getProperty("file.separator");
        if (!path.endsWith(sep)) {
            return path + sep;
        }
        return path;
    }

    private String getDestinationPathByDate(String path) {
        return this.getPath(path) + DateHelper.datetimeToYYYYMMDD(new Date());
    }

    private void moveFileToErrorDir(String dir, String fileName, String dDir) {
        File f = new File(dir, fileName);
        File dPath = new File(dDir);
        if (!dPath.exists()) {
            dPath.mkdirs();
        }
        File df = new File(dPath, fileName);
        f.renameTo(df);

    }

    public Vector getFileErrorVoByFileNames(Vector fileNames, String errorCode) {
        String fileName;
        FileErrorVo vo;
        Vector v = new Vector();
        String genTime = DateHelper.datetimeToYYYY_MM_DD_HH_MM_SS_ML(new Date());
        for (int i = 0; i < fileNames.size(); i++) {
            fileName = (String) fileNames.get(i);
            vo = this.getFileErrorVoByFileName(fileName, errorCode, genTime);
            v.add(vo);
        }
        return v;

    }

    public FileErrorVo getFileErrorVoByFileNamesForOne(String fileName, String errorCode) {

        FileErrorVo vo;
        Vector v = new Vector();
        String genTime = DateHelper.datetimeToYYYY_MM_DD_HH_MM_SS_ML(new Date());

        vo = this.getFileErrorVoByFileName(fileName, errorCode, genTime);

        return vo;

    }

    private String getErrorCodeDes(String errorCode) {
        if (errorCode == null || errorCode.length() == 0) {
            return "";
        }
        if (errorCode.equals(FileConstant.FILE_ERRO_CODE_FILE_NAME)) {
            return FileConstant.FILE_ERRO_CODE_FILE_NAME_DESC;
        }
        if (errorCode.equals(FileConstant.FILE_ERRO_CODE_FILE_CONTENT)) {
            return FileConstant.FILE_ERRO_CODE_FILE_CONTENT_DESC;
        }
        if (errorCode.equals(FileConstant.FILE_ERRO_CODE_FILE_FORMAT)) {
            return FileConstant.FILE_ERRO_CODE_FILE_FORMAT_DESC;
        }
        if (errorCode.equals(FileConstant.FILE_ERRO_CODE_FILE_UNKOWN)) {
            return FileConstant.FILE_ERRO_CODE_FILE_UNKOWN_DESC;
        }
        if (errorCode.equals(FileConstant.FILE_ERRO_CODE_FILE_NOTEXIST)) {
            return FileConstant.FILE_ERRO_CODE_FILE_NOTEXIST_DESC;
        }
        if (errorCode.equals(FileConstant.FILE_ERRO_CODE_FILE_NOTSAME_TOTAL_DETAIL)) {
            return FileConstant.FILE_ERRO_CODE_FILE_NOTSAME_TOTAL_DETAIL_DESC;
        }
        if (errorCode.equals(FileConstant.FILE_ERRO_CODE_FILE_REPEAT)) {
            return FileConstant.FILE_ERRO_CODE_FILE_REPEAT_DESC;
        }
        return "";

    }

    private FileErrorVo getFileErrorVoByFileName(String fileName, String errorCode, String genTime) {
        FileErrorVo vo = new FileErrorVo();
        vo.setDeviceId(this.getFileErrorVoForDev(fileName));
        vo.setFileName(fileName);
        vo.setErrorCode(errorCode);
        vo.setGenTime(genTime);
        vo.setInfoFlag(FileConstant.FILE_INFO_FLAG_NOT);
        // vo.setRemark("");
        vo.setRemark(this.getErrorCodeDes(errorCode));
        return vo;
    }

    private String getFileErrorVoForDev(String fileName) {
        //hwj 20160113 modify 增加空发时修改
        //if(this.isMBProductType(fileName)){
        //    String devId = fileName.substring(4, 7);
        //    return devId;
        //}else{
            String devId = fileName.substring(2, 5);
            return devId;  
        //}
        //return this.getFileNameDev(fileName);
    }

    private boolean isStatData(String line) {
        if (line.startsWith(FileConstant.RECORD_PREFIX_STAT)) {
            return true;
        }
        return false;
    }

    private boolean isStatNumData(String line) {
        if (line.startsWith(FileConstant.RECORD_PREFIX_LISTNUM)) {
            return true;
        }
        return false;
    }

    private boolean isListData(String line) {
        if (line.startsWith(FileConstant.RECORD_PREFIX_LIST)) {
            return true;
        }
        return false;
    }

    private boolean isCrcData(String line) {
        if (line.startsWith(FileConstant.RECORD_PREFIX_CRC)) {
            return true;
        }
        return false;
    }
    // private FileStatVo getFieldForStat(String)

    private FileStatVo getFileSectionForStat(String line, String fileName, int lineNum) throws FileFormatException {
        line = line.substring(FileConstant.RECORD_PREFIX_STAT.length());
        String[] fields = line.split(FileConstant.RECORD_DELIM);
        if (fields.length != FileConstant.RECORD_LENGTH_STAT) {
            throw new FileFormatException("文件" + fileName + "" + lineNum + "字段数不为" + FileConstant.RECORD_LENGTH_STAT);
        }
        FileStatVo vo = new FileStatVo();
        vo.setEsWorktypeId(fields[0]);
        vo.setOrderNo(fields[1]);

        vo.setDrawNum(NumberUtil.getIntValue(fields[2], 0));
        vo.setFiniPronum(NumberUtil.getIntValue(fields[3], 0));
        vo.setSurplusNum(NumberUtil.getIntValue(fields[4], 0));
        vo.setTrashyNum(NumberUtil.getIntValue(fields[5], 0));

        vo.setAchieveTime(fields[6]);
        vo.setHdlFlag(fields[7]);
        vo.setOrderMemo(fields[8]);

        return vo;



    }

    private void getFileSectionForStatNum(FileStatVo statVo, String line, String fileName, int lineNum) throws FileFormatException {
        line = line.substring(FileConstant.RECORD_PREFIX_LISTNUM.length());
        String[] fields = line.split(FileConstant.RECORD_DELIM);
        if (fields.length != FileConstant.RECORD_LENGTH_STAT_NUM) {
            throw new FileFormatException("文件" + fileName + "" + lineNum + "字段数不为" + FileConstant.RECORD_LENGTH_STAT_NUM);
        }
        statVo.setStatNum(NumberUtil.getIntValue(fields[0], 0));

    }

    private void getFileSectionForCRC(FileStatVo statVo, String line, String fileName, int lineNum) throws FileFormatException {
        line = line.substring(FileConstant.RECORD_PREFIX_CRC.length());
        line = line.trim();
        if (line.length() == 0) {
            throw new FileFormatException("文件" + fileName + "" + lineNum + "CRC校验码为空");
        }
        statVo.setCRC(line);

    }

    private String getCardType(String logicalId) {
        if (logicalId == null || logicalId.length() == 0) {
            return "";
        }
        return logicalId.substring(0, 3);

    }

    private String getCardMoney(String cardMoney) {
        return "" + Integer.parseInt(cardMoney);
    }

    private String getDatetime(String d) {
        return d.substring(0, 4) + "-" + d.substring(4, 6) + "-" + d.substring(6, 8) + " " + d.substring(8, 10) + ":" + d.substring(10, 12) + ":" + d.substring(12, 14);
    }

    private String getDatetimeShort(String d) {
        return d.substring(0, 4) + "-" + d.substring(4, 6) + "-" + d.substring(6, 8);
    }

    private FileListVo getFileSectionForList(String line, String fileName, int lineNum, FileStatVo statTotalVo, int j) throws FileFormatException {
        line = line.substring(FileConstant.RECORD_PREFIX_LIST.length());
        String[] fields = line.split(FileConstant.RECORD_DELIM);
        if (fields.length != FileConstant.RECORD_LENGTH_LIST) {
            throw new FileFormatException("文件" + fileName + "" + lineNum + "字段数不为" + FileConstant.RECORD_LENGTH_LIST);
        }
        FileListVo vo = new FileListVo();
        vo.setEsWorktypeId(fields[0]);
        vo.setOrderNo(fields[1]);

        vo.setCardMainCode(fields[2].substring(0, 2));
        vo.setCardSubCode(fields[2].substring(2, 4));
        vo.setReqNo(fields[3]);
        vo.setLogiId(fields[4]);



        vo.setPrintId(fields[5]);
        vo.setPhyId(fields[6]);
        vo.setManuTime(this.getDatetime(fields[7]));

        vo.setCardMon(this.getCardMoney(fields[8]));
        vo.setPeriAvadate(this.getDatetimeShort(fields[9]));
        if (j == 1)//设置SAM卡逻辑卡号
        {
            statTotalVo.setEsSamNo(fields[10]);
        }

        vo.setLineCode(fields[11]);
        vo.setStationCode(fields[12]);
        vo.setCardStartAva(fields[13]);
        vo.setCardAvaDays(fields[14]);
        vo.setExitLineCode(fields[15]);
        vo.setExitStationCode(fields[16]);
        vo.setMode(fields[17]);
        //hwj 20160107增加卡商代码和手机号码
        vo.setCardProducerCode(fields[18]);
        vo.setPhoneNo(fields[19]);

        vo.setCardType(this.getCardType(vo.getLogiId()));
        vo.setStatusFlag(FileConstant.STATUS_FLAG);
        vo.setKdcVersion("");
        //累计明细总数
        statTotalVo.setStatNumDetail(statTotalVo.getStatNumDetail() + 1);

        return vo;

    }

    private String getCRC(String str) {

        byte[] b = null;
        b = str.getBytes();
        long crc32 = CharUtil.getCRC32Value(b);
        String crc = Long.toHexString(crc32);
        for (int i = crc.length(); i < 8; i++) {
            crc = "0" + crc;
        }
        return crc;

    }

    private String getWorktype(String fileName) {
        int index = fileName.indexOf(".");
        String worktype = fileName.substring(index + 1, index + 3);
        return worktype;

    }

    private String getPathBcp(String dirBcp, String fileNameUpload) {
        String worktype = this.getWorktype(fileNameUpload);
        String path = this.getPath(dirBcp) + worktype + "/";//System.getProperty("file.separator");
        this.createPath(path);
        return path;
    }

    private void createPath(String pathName) {
        File path = new File(pathName);
        path.mkdirs();
        /*
         * if(path.isDirectory()) { if(!path.exists()) path.mkdirs(); }
         */
    }

    private void printbyte(StringBuffer sb) {
        String sbstr = sb.toString();
        byte[] bs = sbstr.getBytes();
        int i;
        boolean isOd = false;
        boolean isOA = false;
        for (i = 0; i < bs.length; i++) {
            System.out.print(Integer.toHexString(bs[i]) + " ");
            if (bs[i] == 0x0d) {
                isOd = true;
            }
            if (bs[i] == 0x0a) {
                isOA = true;
            }
            if (isOd && isOA) {
                System.out.println();
                isOd = false;
                isOA = false;
            }
        }


    }

    private void addToBufferForLine(StringBuffer sb, String line) {
        sb.append(line);
        sb.append(FileConstant.CRLF_1);
    }

    private void addToBufferForField(StringBuffer sb, String field) {
        sb.append(field);
        sb.append(FileConstant.TAB);
    }

    private void addToBufferForFieldLast(StringBuffer sb, String field) {
        sb.append(field);
        //sb.append(FileConstant.CRLF_1);
        sb.append(FileConstant.CRLF_2);
    }

    private String getFileNameBcp(String fileName) {
        return "bcp." + fileName;
    }

    private void checkFileByContent(TreeSet keys, TreeSet cards, TreeSet stations, FileListVo listVo, String fileName, int i) throws FileContentException {
        try {
            //主键重复校验
            // this.checkFileByContentKey(keys, listVo, fileName, i);
            this.checkFileByContentLogical(listVo, fileName, i);//逻辑卡号校验
            this.checkFileByContentStation(stations, this.getRecordKeyStation(listVo), fileName, i);
            this.checkFileByContentStation(stations, this.getRecordKeyStationExit(listVo), fileName, i);
            this.checkFileByContentCard(cards, listVo, fileName, i);

        } catch (Exception ex) {
            throw new FileContentException(ex.getMessage());

        }
    }

    private boolean isReapeatRecord(TreeSet keys, FileListVo listVo, String fileName, int i) throws FileRecordReapeatException {
        String key = this.getRecordKeyForLogical(listVo);
        //主键重复校验
        if (!keys.contains(key)) {
            keys.add(key);
            return false;
        } else {
            return true;
            //throw new FileRecordReapeatException("文件" + fileName + "第" + i + "行主键重复");
        }
    }

    private boolean isNeedCheckStation(String key) {
        if (key == null || key.length() == 0) {
            return false;
        }
        if (key.equals("0000")) {
            return false;
        }
        return true;

    }

    private boolean isNeedCheckCard(String key) {
        if (key == null || key.length() == 0) {
            return false;
        }
        if (key.equals("0000")) {
            return false;
        }
        return true;

    }

    private void checkFileByContentStation(TreeSet stations, String key, String fileName, int i) throws FileContentException {
        //String key = this.getRecordKeyStation(listVo);
        if (!this.isNeedCheckStation(key)) {
            return;
        }
        //车站校验
        if (!stations.contains(key)) {
            throw new FileContentException("文件" + fileName + "第" + i + "行车站" + key + "无效");
        }
    }

    private void checkFileByContentCard(TreeSet cards, FileListVo listVo, String fileName, int i) throws FileContentException {
        String key = this.getRecordKeyCard(listVo);
        if (!this.isNeedCheckCard(key)) {
            return;
        }
        //票卡校验
        if (!cards.contains(key)) {
            throw new FileContentException("文件" + fileName + "第" + i + "行卡类" + key + "无效");
        }
    }

    private void checkFileByContentLogical(FileListVo listVo, String fileName, int i) throws FileContentException {
        String logicalId = listVo.getLogiId();
        if (logicalId == null || logicalId.length() != CardConstant.LEN_LOGICAL) {
            throw new FileContentException("文件" + fileName + "第" + i + "行逻辑卡号长度不是20位");
        }
        if (!isCardForLogicalEffect(listVo.getCardMainCode())) {
            return;
        }
    }

    private String getRecordKeyForLogical(FileListVo listVo) {
        return listVo.getLogiId();
    }

    private String getRecordKeyStation(FileListVo listVo) {
        return listVo.getLineCode() + listVo.getStationCode();
    }

    private String getRecordKeyStationExit(FileListVo listVo) {
        return listVo.getExitLineCode() + listVo.getExitStationCode();
    }

    private String getRecordKeyCard(FileListVo listVo) {
        return listVo.getCardMainCode() + listVo.getCardSubCode();
    }

    private boolean isSameOrder(String fileNameOrderNo, String contentOrderNo) {
        if (fileNameOrderNo == null || contentOrderNo == null
                || fileNameOrderNo.length() == 0 || contentOrderNo.length() == 0) {
            return false;
        }
        if (fileNameOrderNo.equals(contentOrderNo)) {
            return true;
        }
        return false;
    }

    private void handleReapeat(FileStatVo statTotalVo, FileListVo listVo) {
        statTotalVo.setStatNumDetail(statTotalVo.getStatNumDetail() - 1);
        statTotalVo.setFiniPronum(statTotalVo.getFiniPronum() - 1);
        statTotalVo.setStatNum(statTotalVo.getStatNum() - 1);
        statTotalVo.setIsExistReapeat(true);
        statTotalVo.getReapeatRecords().add(listVo);

        logger.info("数据重复:订单完成数-1;统计数-1;明细-1");

    }

    private FileStatVo convertFileToBuff(String dir, String fileNameUpload, String dirBcp)
            throws FileFormatException, FileNotFoundException, IOException,
            FileTotalDetailNoSameException, FileCRCException,
            FileRecordReapeatException,
            FileContentException {

        String fileName = this.getPath(dir) + fileNameUpload;
        String fileNameBcp = this.getPathBcp(dirBcp, fileNameUpload) + this.getFileNameBcp(fileNameUpload);
        FileInputStream fis = new FileInputStream(fileName);
        FileOutputStream fos = new FileOutputStream(fileNameBcp);
        String line = null;
        boolean existStat = false;
        boolean existStatNum = false;
        String fileNameOrderNo = this.getFileNameOrderNo(fileNameUpload);

        InputStreamReader isr = null;
        BufferedReader br = null;
        OutputStreamWriter osr = null;
        BufferedWriter bw = null;


        // Vector fileSectionStat = new Vector();
        //Vector fileSectionList = new Vector();
        //FileStatNumVo statNumVo = new FileStatNumVo();
        FileStatVo statVo = new FileStatVo();
        FileStatVo statTotalVo = new FileStatVo();
        FileListVo listVo = new FileListVo();
        StringBuffer sb = new StringBuffer();
        TreeSet keys = new TreeSet();
        //HashMap hm = new HashMap();

        statTotalVo.setFileName(fileNameUpload);
        statTotalVo.setmBPduType(this.getMBProductType(fileNameUpload));//hwj add 20160108增加制卡类型，0：ES，1:MB
        //hm.put(FileConstant.RECORD_PREFIX_STAT, fileSectionStat);
        //hm.put(FileConstant.RECORD_PREFIX_LIST, fileSectionList);
        //  hm.put(FileConstant.RECORD_PREFIX_LISTNUM, statNumVo);
        String hdlTime = DateHelper.datetimeToYYYY_MM_DD_HH_MM_SS_ML(new Date());
        boolean isReapeat = false;

        int i = 0;
        int j = 0;
        try {
            isr = new InputStreamReader(fis, "UTF8");
            br = new BufferedReader(isr);
            osr = new OutputStreamWriter(fos, "UTF8");
            bw = new BufferedWriter(osr);
            while ((line = br.readLine()) != null) {
                isReapeat = false;
                i++;

                if (this.isStatData(line)) {
                    statVo = this.getFileSectionForStat(line, fileName, i);
                    if (!this.isSameOrder(fileNameOrderNo, statVo.getOrderNo())) {
                        throw new FileContentException("文件" + fileName + " STAT的订单号与文件名称对应的订单号不相同!");
                    }
                    this.totalStat(statTotalVo, statVo);//对统计数据汇总

                    this.addToBufferForLine(sb, line);
                    existStat = true;
                    continue;
                }
                if (this.isStatNumData(line)) {
                    this.getFileSectionForStatNum(statTotalVo, line, fileName, i);

                    this.addToBufferForLine(sb, line);
                    existStatNum = true;
                    continue;
                }
                if (this.isListData(line)) {
                    j++;
                    listVo = this.getFileSectionForList(line, fileName, i, statTotalVo, j);
                    if (!this.isSameOrder(fileNameOrderNo, listVo.getOrderNo())) {
                        throw new FileContentException("文件" + fileName + "第" + i + "行STATLIST的订单号与文件名称对应的订单号不相同!");
                    }

                    this.checkFileByContent(keys, AppConstant.cards, AppConstant.stations, listVo, fileName, i);
                    //订单内重复性校验如重复订单的完成数-1,明细数-1,统计数-1,不写BCP文件
                    isReapeat = this.isReapeatRecord(keys, listVo, fileName, i);
                    if (isReapeat) {
                        this.handleReapeat(statTotalVo, listVo);
                    }
                    listVo.setHdlTime(hdlTime);
                    if (!isReapeat) {
                        this.writeBcpFileLine(listVo, bw);//写BCP文件
                    }
                    this.addToBufferForLine(sb, line);
                    continue;
                }

                if (this.isCrcData(line)) {
                    this.getFileSectionForCRC(statTotalVo, line, fileName, i);
                    logger.info("计算CRC的数据");
                    //this.printbyte(sb);
                    logger.info("计算CRC的数据结束");
                    String crc = this.getCRC(sb.toString());

                    //CRC校验
                    logger.info("CRC:" + crc);
                    if (!crc.equals(statTotalVo.getCRC())) {
                        throw new FileCRCException("文件" + fileName + "CRC校验错误!" + "通讯:" + crc + " 文件:" + statTotalVo.getCRC());
                    }

                    continue;
                }

            }
            //校验明细数与汇总数是否相同
            if (!existStat || !existStatNum) {
                throw new FileContentException("文件" + fileName + " STAT或LISTNUM标签不存在!");
            }
            if (statTotalVo.getStatNumDetail() != statTotalVo.getStatNum()) {
                throw new FileTotalDetailNoSameException("文件" + fileName + "明细数与汇总数不一致!");
            }
            //明细数与订单说明的汇总数不一致，取明细数，同时更新订单的备注
            logger.info("订单的生产数量为" + statTotalVo.getFiniPronum() + " 明细数量为" + statTotalVo.getStatNumDetail());
            if (statTotalVo.getFiniPronum() != statTotalVo.getStatNumDetail()) {
                statTotalVo.setResetNum(true);
                statTotalVo.setResetNumBefore(statTotalVo.getFiniPronum());
                statTotalVo.setResetNum(statTotalVo.getStatNumDetail());
                statTotalVo.setFiniPronum(statTotalVo.getStatNumDetail());
            }


        } /*
         * catch (FileFormatException e) { // e.printStackTrace(); throw e; }
         */ finally {
            this.closeFile(fis, isr, br, fos, osr, bw);

        }
        return statTotalVo;
    }

    public static void main(String[] args) {
        String dir = "F:/netbeansproject/files/ftp/server";
        String fileNameUpload = "ES3008_2.01201011300012";
        String dirBcp = "F:/netbeansproject/files/bcp";
        FileUtil util = new FileUtil();
        try {
            util.convertFileToBuffForTest(dir, fileNameUpload, dirBcp);

        } catch (FileFormatException ex) {
            java.util.logging.Logger.getLogger(FileUtil.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException ex) {
            java.util.logging.Logger.getLogger(FileUtil.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(FileUtil.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileTotalDetailNoSameException ex) {
            java.util.logging.Logger.getLogger(FileUtil.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileCRCException ex) {
            java.util.logging.Logger.getLogger(FileUtil.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileRecordReapeatException ex) {
            java.util.logging.Logger.getLogger(FileUtil.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileContentException ex) {
            java.util.logging.Logger.getLogger(FileUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public FileStatVo convertFileToBuffForTest(String dir, String fileNameUpload, String dirBcp)
            throws FileFormatException, FileNotFoundException, IOException,
            FileTotalDetailNoSameException, FileCRCException,
            FileRecordReapeatException,
            FileContentException {

        String fileName = this.getPath(dir) + fileNameUpload;
        String fileNameBcp = this.getPathBcp(dirBcp, fileNameUpload) + this.getFileNameBcp(fileNameUpload);
        FileInputStream fis = new FileInputStream(fileName);
        FileOutputStream fos = new FileOutputStream(fileNameBcp);
        String line = null;

        InputStreamReader isr = null;
        BufferedReader br = null;
        OutputStreamWriter osr = null;
        BufferedWriter bw = null;

        FileStatVo statVo = new FileStatVo();
        FileStatVo statTotalVo = new FileStatVo();
        FileListVo listVo = new FileListVo();
        StringBuffer sb = new StringBuffer();
        TreeSet keys = new TreeSet();


        statTotalVo.setFileName(fileNameUpload);

        String hdlTime = DateHelper.datetimeToYYYY_MM_DD_HH_MM_SS_ML(new Date());

        int i = 0;
        int j = 0;
        try {
            isr = new InputStreamReader(fis, "GBK");
            br = new BufferedReader(isr);
            osr = new OutputStreamWriter(fos, "GBK");
            bw = new BufferedWriter(osr);
            while ((line = br.readLine()) != null) {
                i++;

                if (this.isStatData(line)) {
                    statVo = this.getFileSectionForStat(line, fileName, i);
                    this.totalStat(statTotalVo, statVo);//对统计数据汇总

                    this.addToBufferForLine(sb, line);
                    continue;
                }
                if (this.isListData(line)) {
                    j++;
                    listVo = this.getFileSectionForList(line, fileName, i, statTotalVo, j);
                    //this.checkFileByContent(keys,ApplicationConstant.cards,ApplicationConstant.stations, listVo, fileName, i);
                    listVo.setHdlTime(hdlTime);
                    this.writeBcpFileLine(listVo, bw);//写BCP文件

                    this.addToBufferForLine(sb, line);
                    continue;
                }
                if (this.isStatNumData(line)) {
                    this.getFileSectionForStatNum(statTotalVo, line, fileName, i);

                    this.addToBufferForLine(sb, line);
                    continue;
                }
                if (this.isCrcData(line)) {
                    this.getFileSectionForCRC(statTotalVo, line, fileName, i);
                    logger.info("计算CRC的数据");
                    this.printbyte(sb);
                    logger.info("计算CRC的数据结束");
                    String crc = this.getCRC(sb.toString());


                    //CRC校验
                    logger.info("CRC:" + crc);
                    if (!crc.equals(statTotalVo.getCRC())) {
                        throw new FileCRCException("文件" + fileName + "CRC校验错误!" + "通讯:" + crc + " 文件:" + statTotalVo.getCRC());
                    }

                    continue;

                }

            }
            //校验明细数与汇总数是否相同
            if (statTotalVo.getStatNumDetail() != statTotalVo.getStatNum()) {
                throw new FileTotalDetailNoSameException("文件" + fileName + "明细数与汇总数不一致!");
            }


        } /*
         * catch (FileFormatException e) { // e.printStackTrace(); throw e; }
         */ finally {
            this.closeFile(fis, isr, br, fos, osr, bw);

        }
        return statTotalVo;
    }

    public FileStatVo convertFileToBuffForTest1(String dir, String fileNameUpload, String dirBcp)
            throws FileFormatException, FileNotFoundException, IOException,
            FileTotalDetailNoSameException, FileCRCException,
            FileRecordReapeatException,
            FileContentException {

        String fileName = this.getPath(dir) + fileNameUpload;
        String fileNameBcp = this.getPathBcp(dirBcp, fileNameUpload) + this.getFileNameBcp(fileNameUpload);
        FileInputStream fis = new FileInputStream(fileName);
        FileOutputStream fos = new FileOutputStream(fileNameBcp);
        String line = null;

        InputStreamReader isr = null;
        BufferedReader br = null;
        OutputStreamWriter osr = null;
        BufferedWriter bw = null;

        FileStatVo statVo = new FileStatVo();
        FileStatVo statTotalVo = new FileStatVo();
        FileListVo listVo = new FileListVo();
        StringBuffer sb = new StringBuffer();
        TreeSet keys = new TreeSet();


        statTotalVo.setFileName(fileNameUpload);

        String hdlTime = DateHelper.datetimeToYYYY_MM_DD_HH_MM_SS_ML(new Date());

        int i = 0;
        int j = 0;
        try {
            isr = new InputStreamReader(fis, "GBK");
            br = new BufferedReader(isr);
            osr = new OutputStreamWriter(fos, "GBK");
            bw = new BufferedWriter(osr);
            while ((line = br.readLine()) != null) {
                i++;
                this.addToBufferForLine(sb, line);
                /*
                 * if (this.isStatData(line)) { statVo =
                 * this.getFileSectionForStat(line, fileName, i);
                 * this.totalStat(statTotalVo, statVo);//对统计数据汇总
                 *
                 * this.addToBufferForLine(sb, line); continue; } if
                 * (this.isListData(line)) { j++; listVo =
                 * this.getFileSectionForList(line, fileName, i, statTotalVo,
                 * j);
                 * //this.checkFileByContent(keys,ApplicationConstant.cards,ApplicationConstant.stations,
                 * listVo, fileName, i); listVo.setHdlTime(hdlTime);
                 * this.writeBcpFileLine(listVo, bw);//写BCP文件
                 *
                 * this.addToBufferForLine(sb, line); continue; } if
                 * (this.isStatNumData(line)) {
                 * this.getFileSectionForStatNum(statTotalVo, line, fileName,
                 * i);
                 *
                 * this.addToBufferForLine(sb, line); continue; }
                 */
                //if (this.isCrcData(line)) {
                //this.getFileSectionForCRC(statTotalVo, line, fileName, i);
                logger.info("计算CRC的数据");
                //this.printbyte(sb);
                logger.info("计算CRC的数据结束");
                // String crc = this.getCRC(sb.toString());


                //CRC校验
                // System.out.println("CRC:" + crc);
                //if(!crc.equals(statTotalVo.getCRC()))
                //    throw new FileCRCException("文件"+fileName+"CRC校验错误!"+"通讯:"+crc+" 文件:"+statTotalVo.getCRC());

                //continue;

                //}

            }
            String crc = this.getCRC(sb.toString());


            //CRC校验
            System.out.println("CRC:" + crc);
            //校验明细数与汇总数是否相同
            if (statTotalVo.getStatNumDetail() != statTotalVo.getStatNum()) {
                throw new FileTotalDetailNoSameException("文件" + fileName + "明细数与汇总数不一致!");
            }


        } /*
         * catch (FileFormatException e) { // e.printStackTrace(); throw e; }
         */ finally {
            this.closeFile(fis, isr, br, fos, osr, bw);

        }
        return statTotalVo;
    }

    private boolean isFinishOrder(String hdlFlag) {
        if (hdlFlag.equals(FileConstant.HDL_FALG_FINISH) || hdlFlag.equals(FileConstant.HDL_FALG_FINISH_NOCOMPLETE)) {
            return true;
        }
        return false;
    }

    private void totalStat(FileStatVo statTotalVo, FileStatVo statVo) {
        statTotalVo.setFiniPronum(statTotalVo.getFiniPronum() + statVo.getFiniPronum());
        statTotalVo.setTrashyNum(statTotalVo.getTrashyNum() + statVo.getTrashyNum());
        statTotalVo.setEsWorktypeId(statVo.getEsWorktypeId());

        if (this.isFinishOrder(statVo.getHdlFlag())) {
            statTotalVo.setDrawNum(statVo.getDrawNum());
            statTotalVo.setSurplusNum(statVo.getSurplusNum());
            //statTotalVo.setTrashyNum(statVo.getTrashyNum());
            statTotalVo.setAchieveTime(statVo.getAchieveTime());
            ;
            statTotalVo.setOrderNo(statVo.getOrderNo());
            statTotalVo.setOrderMemo(statVo.getOrderMemo());
            statTotalVo.setHdlFlag(statVo.getHdlFlag());
        }


    }

    private boolean isInitial(String orderNo) {
        if (orderNo.startsWith(FileConstant.WORKTYPE_INITIAL)) {
            return true;
        }
        return false;
    }

    private boolean isHunch(String orderNo) {
        if (orderNo.startsWith(FileConstant.WORKTYPE_HUNCH)) {
            return true;
        }
        return false;
    }

    private boolean isAgain(String orderNo) {
        if (orderNo.startsWith(FileConstant.WORKTYPE_AGAIN)) {
            return true;
        }
        return false;
    }

    private boolean isLogout(String orderNo) {
        if (orderNo.startsWith(FileConstant.WORKTYPE_LOGOUT)) {
            return true;
        }
        return false;
    }

    private void writeBcpFileLine(FileListVo vo, BufferedWriter bw) throws IOException {
        String line;
        if (this.isHunch(vo.getOrderNo())) {
            line = this.getBcpFileLine(vo, FileConstant.WORKTYPE_HUNCH);
        } else {
            line = this.getBcpFileLine(vo, "-1");
        }
        bw.write(line);
        bw.flush();

    }

    private String getBcpFileLine(FileListVo vo, String worktype) {
        StringBuffer sb = new StringBuffer();
        addToBufferForField(sb, vo.getLogiId().trim());
        addToBufferForField(sb, vo.getCardMainCode());
        addToBufferForField(sb, vo.getCardSubCode());
        addToBufferForField(sb, vo.getReqNo());
        addToBufferForField(sb, vo.getPhyId().trim());
        addToBufferForField(sb, vo.getPrintId().trim());
        addToBufferForField(sb, vo.getManuTime());
        addToBufferForField(sb, vo.getCardMon());
        addToBufferForField(sb, vo.getPeriAvadate());
        addToBufferForField(sb, vo.getKdcVersion());
        addToBufferForField(sb, vo.getHdlTime());
        addToBufferForField(sb, vo.getOrderNo());
        addToBufferForField(sb, vo.getStatusFlag());
        addToBufferForField(sb, vo.getCardType());
        if (this.isHunch(worktype)) {
            addToBufferForField(sb, vo.getLineCode());
            addToBufferForField(sb, vo.getStationCode());

            addToBufferForField(sb, vo.getCardStartAva());
        }
        addToBufferForField(sb, vo.getCardAvaDays());
        addToBufferForField(sb, vo.getExitLineCode());
        addToBufferForField(sb, vo.getExitStationCode());
        //hwj 20160107 增加卡商代码和手机号码
        addToBufferForField(sb, vo.getMode());
        
        addToBufferForField(sb, vo.getCardProducerCode());
        addToBufferForFieldLast(sb, vo.getPhoneNo());
        //addToBufferForField(sb,FileConstant.RECORD_LINE_DELIM);

        return sb.toString();
    }

    private void closeFile(FileInputStream fis, InputStreamReader isr, BufferedReader br,
            FileOutputStream fos, OutputStreamWriter osr, BufferedWriter bw) {
        try {
            if (fis != null) {
                fis.close();
            }
            if (isr != null);
            isr.close();
            if (br != null) {
                br.close();
            }
            if (fos != null) {
                fos.close();
            }
            if (osr != null) {
                osr.close();
            }
            if (bw != null) {
                bw.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    
    private static boolean isCardForLogicalEffect(String icMainType) {
        if (icMainType == null || icMainType.length() == 0) {
            return false;
        }
        for (int i = 0; i < CardConstant.CARD_TYPE_LOGICAL_EFFECT.length; i++) {
            if (icMainType.equals(CardConstant.CARD_TYPE_LOGICAL_EFFECT[i])) {
                return true;
            }
        }

        return false;

    }
}
