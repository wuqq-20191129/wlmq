package com.goldsign.acc.app.prminfo.controller;

import com.goldsign.acc.app.prminfo.entity.HandleMessageBase;
import com.goldsign.acc.app.prminfo.entity.OpLogImport;
import com.goldsign.acc.app.prminfo.entity.FileData;
import com.goldsign.acc.app.prminfo.entity.FileRecordHead;
import com.goldsign.acc.app.prminfo.entity.FileRecordCrc;
import com.goldsign.acc.app.prminfo.entity.LogicIccList;
import com.goldsign.acc.app.prminfo.exception.FileNameException;
import com.goldsign.acc.app.prminfo.exception.RecordParseException;
import com.goldsign.acc.app.prminfo.util.FileNameParseUtil;
import com.goldsign.acc.app.prminfo.util.CrcUtil;
import com.goldsign.acc.app.prminfo.entity.PhyLogicList;
import com.goldsign.acc.app.prminfo.mapper.HisPhyLogicListMapper;
import com.goldsign.acc.app.prminfo.mapper.LogicIccListMapper;
import com.goldsign.acc.frame.constant.CommandConstant;
import com.goldsign.acc.frame.constant.ConfigConstant;
import com.goldsign.acc.frame.controller.PrmBaseController;
import com.goldsign.acc.frame.entity.PubFlag;
import com.goldsign.acc.frame.mapper.OperationLogMapper;
import com.goldsign.acc.frame.util.DBUtil;
import com.goldsign.acc.frame.util.FormUtil;
import com.goldsign.acc.frame.util.FileUtil;
import com.goldsign.acc.frame.util.LogUtil;
import com.goldsign.acc.frame.vo.OperationResult;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import com.goldsign.acc.app.prminfo.mapper.OpLogLogicImportMapper;
import com.goldsign.acc.app.prminfo.mapper.PhyLogicListMapper;
import com.goldsign.acc.frame.interceptor.AuthenticateInterceptor;
import com.goldsign.acc.frame.util.ExpUtil;
import com.goldsign.lib.db.util.DbHelper;
import com.goldsign.login.vo.User;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.naming.NamingException;
import org.apache.log4j.Logger;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

/**
 * * 逻辑卡号刻印号对照表

 * @author xiaowu
 */
@Controller
public class LogicIccListController extends PrmBaseController {

    Logger logger = Logger.getLogger(LogicIccListController.class);

    @Autowired
    private OpLogLogicImportMapper opLogImportMapper;

    @Autowired
    private PhyLogicListMapper phyLogicListMapper;

    @Autowired
    private LogicIccListMapper logicIccListMapper;

    @Autowired
    private HisPhyLogicListMapper hisPhyLogicListMapper;

    @RequestMapping("/logicIccList")
    public ModelAndView service(HttpServletRequest request, HttpServletResponse response) {

        ModelAndView mv = new ModelAndView("/jsp/prminfo/logic_icc_list.jsp");

        String command = request.getParameter("command");
        String moduleId = request.getParameter("ModuleID");
        request.setAttribute("ModuleID", moduleId);
        OperationResult opResult = new OperationResult();
        try {
            if (command != null) {
                command = command.trim();
                if (command.equals(CommandConstant.COMMAND_QUERY))//查询操作
                {
                    opResult = this.query(request, this.opLogImportMapper, this.operationLogMapper);
                }
                if (command.equals(CommandConstant.COMMAND_IMPORT))//导入操作
                {
                    return new ModelAndView("/jsp/prminfo/logic_icc_list_import.jsp");  //打开导入页面
                }
                if (command.equals("detail"))//明细
                {
                    mv = new ModelAndView("/jsp/prminfo/logic_icc_list_detail.jsp");
                    opResult = this.queryDetail(request, this.logicIccListMapper, this.operationLogMapper);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        String[] attrNames = {OPERATORS};

        this.setPageOptions(attrNames, mv, request, response);//设置页面操作员选项值
        if (command != null && command.equals("detail")) {
//            this.getResultSetText((List<LogicIccList>) opResult.getReturnResultSet(), mv);
            this.baseHandler(request, response, mv);
        } else {
            this.getResultSetText((List<OpLogImport>) opResult.getReturnResultSet(), mv);
            this.baseHandler(request, response, mv);
            this.divideResultSet(request, mv, opResult);//结果集分页
        }
        this.SaveOperationResult(mv, opResult);//返回结果集
        return mv;
    }

    @RequestMapping("/logicIccListExportAll")
    public void exportExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {
//	List results = this.getBufferElements(request);
        List results = this.getBufferElementsForCurClass(request, "com.goldsign.acc.app.prminfo.entity.OpLogImport");
        String expAllFields = request.getParameter("expAllFields");
        List<Map<String, Object>> queryResults = ExpUtil.entityToMap(expAllFields, results);
        ExpUtil.renderExportExcelPath(response, ExpUtil.exportExcel(queryResults, request));
    }

    private void getResultSetText(List<OpLogImport> resultSet, ModelAndView mv) {
        List<PubFlag> operators = (List<PubFlag>) mv.getModelMap().get(PrmBaseController.OPERATORS);
        for (OpLogImport opLogImport : resultSet) {
            if (operators != null && !operators.isEmpty()) {
                opLogImport.setOperator_name(DBUtil.getTextByCode(opLogImport.getOperator_id(), operators));
            }
        }
    }

    public OperationResult queryDetail(HttpServletRequest request, LogicIccListMapper logicIccListMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult or = new OperationResult();
        LogUtil logUtil = new LogUtil();
        String waterNo = request.getParameter("waterNo");
        LogicIccList logicIccList = new LogicIccList();
        logicIccList.setWater_no(waterNo);
        List<LogicIccList> resultSet;

        try {
            resultSet = logicIccListMapper.getLogicIccLists(logicIccList);
            or.setReturnResultSet(resultSet);
            or.addMessage("成功查询" + resultSet.size() + "条记录");
        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + resultSet.size() + "条记录", opLogMapper);
        return or;
    }

    public OperationResult query(HttpServletRequest request, OpLogLogicImportMapper opLogImportMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult or = new OperationResult();
        LogUtil logUtil = new LogUtil();
        OpLogImport opLogImport;
        List<OpLogImport> resultSet;

        try {
            opLogImport = this.getQueryCondition(request);
            opLogImport.setTable_name("w_op_prm_logic_icc_list");
            if (!"".equals(opLogImport.getFile_name())) {
                opLogImport.setFile_name("%" + opLogImport.getFile_name() + "%");
            }
            request.getSession().setAttribute("queryCondition", opLogImport);
            resultSet = opLogImportMapper.getOpLogImportsLikeFileName(opLogImport);
            or.setReturnResultSet(resultSet);
            or.addMessage("成功查询" + resultSet.size() + "条记录");
        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + resultSet.size() + "条记录", opLogMapper);
        return or;
    }

    private OpLogImport getQueryCondition(HttpServletRequest request) {
        OpLogImport opLogImport = new OpLogImport();
        if (FormUtil.getParameter(request, "q_begin_time") != null && !FormUtil.getParameter(request, "q_begin_time").isEmpty()) {
            opLogImport.setBegin_time(FormUtil.getParameter(request, "q_begin_time") + " 00:00:01");
        }
        if (FormUtil.getParameter(request, "q_end_time") != null && !FormUtil.getParameter(request, "q_end_time").isEmpty()) {
            opLogImport.setEnd_time(FormUtil.getParameter(request, "q_end_time") + " 23:59:59");
        }
        opLogImport.setOperator_id(FormUtil.getParameter(request, "q_operator_id"));
        opLogImport.setFile_name(FormUtil.getParameter(request, "q_file_name"));
        opLogImport.setBegin_logical_id(FormUtil.getParameter(request, "q_begin_logical"));
        opLogImport.setEnd_logical_id(FormUtil.getParameter(request, "q_end_logical"));

        return opLogImport;
    }

    @RequestMapping("/LogicIccListUpload")
    public ModelAndView logicIccListUpload(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView("/jsp/prminfo/logic_icc_list_import.jsp");
        OperationResult opResult = new OperationResult();

        try {
            String command = request.getParameter("command");
            try {
                if (command != null) {
                    if (command.equals("import")) {
//                        am = this.importFile(request, response, form);
                        opResult = this.importFile(request, response);
                    }
                }
            } catch (Exception e) {
                //mv.addObject("msg", "物理逻辑卡号对照表导入失败");
                return mv;
            }
        } catch (Exception e) {
            e.printStackTrace();
            mv.addObject("msg", e.getMessage());
        }
        return mv;
    }

    private OperationResult importFile(HttpServletRequest request, HttpServletResponse resp) throws Exception {
//        ImportPhyLogicBo importBo = new ImportPhyLogicBo(); 
//        PluploadForm uploadForm = (PluploadForm) form;

        int n = 0;
        String preMsg = "逻辑卡号刻印号对照表导入：";
        OperationResult operationResult = null;
//        AccUtil util = new AccUtil();
//        ActionMessage am = null;
//        String terminator = this.getFieldTerminator(form);

        HandleMessageBase messageBase = null;
        int finishFlag = 4;
        String filePath = "";
        int waterNo = 0;
//        Map<String, Object> m = new HashMap<String, Object>();
//        m.put("status", true);
        try {
//            FormFile file = uploadForm.getUpload();
//            MultipartResolver resolver = new CommonsMultipartResolver(request.getSession().getServletContext());
//            MultipartHttpServletRequest multipartRequest = resolver.resolveMultipart(request); 
//            CommonsMultipartFile file = (CommonsMultipartFile) multipartRequest.getFiles("makeFile").get(0);
            MultipartResolver resolver = new CommonsMultipartResolver(request.getSession().getServletContext());
            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
            CommonsMultipartFile file = (CommonsMultipartFile) multipartRequest
                    .getFile("makeFile");
//                    .getFile("makeFile");
//            String fileName = file.getFileName().substring(file.getFileName().lastIndexOf("\\") + 1);
            String fileName = file.getOriginalFilename();
//            m.put("fileName", fileName);
//           1.1 校验文件名
            parseFileName(fileName, 18, "PRTM");
//           1.2 不能重复导入相同文件名的文件。
//            if (isExistFile(fileName)) {
//                throw new Exception("文件已被导入过。");
//            }
//          2 保存文件
            //保存文件的临时文件夹
            String phylogicUploadPath = ConfigConstant.PHYLOGIC_UPLOAD_PATH;
            //(String) ConfigConstant.FILTER_PROPERTIES.get(AuthenticateInterceptor.PHYLOGIC_UPLOAD_PATH);
            filePath = phylogicUploadPath + File.separator + fileName;
            BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(new File(filePath)));
            FileUtil.copy(file.getInputStream(), out);
//          3 校验文件内容

            FileData fd = parseFile(phylogicUploadPath, fileName, waterNo);
            List<Object> logicIccListList = fd.getContent().get(fileName);
//          3.1 校验此段的物理逻辑卡号是否已经在数据日志中存在。
            String begin_logic_no = ((LogicIccList) logicIccListList.get(0)).getLogic_no();
            String end_logic_no = ((LogicIccList) logicIccListList.get(logicIccListList.size() - 1)).getLogic_no();
            OpLogImport opLogImport = new OpLogImport();
            opLogImport.setBegin_logical_id(begin_logic_no);
            opLogImport.setEnd_logical_id(end_logic_no);
            opLogImport.setTable_name("w_op_prm_logic_icc_list");
//            LogicIccListBo logicIccListBpo = new LogicIccListBo();
            List<OpLogImport> opLogImports = opLogImportMapper.getOpLogImports(opLogImport);
//                    this.findOpLogImport(opLogImport);
            if (opLogImports != null && opLogImports.size() > 0) {
                throw new Exception("逻辑卡号段" + begin_logic_no + "至" + end_logic_no + "已被导入过！");
            }

//          4 将信息入库  
            waterNo = findWaterNo();
            messageBase = batchDB(fileName, fd, waterNo);
            finishFlag = messageBase.getFinished();
//          5 记录导入日志
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
            OpLogImport vo = new OpLogImport();
            vo.setWater_no(waterNo + "");
            vo.setOperator_id(((User) request.getSession().getAttribute("User")).getAccount());
            vo.setOp_time(sdf.format(new Date()));
            vo.setOp_type("1");  // 1  表示增加
            vo.setTable_name("w_op_prm_logic_icc_list");
            vo.setRecord_count(String.valueOf(logicIccListList.size()));
            vo.setVersion_no("0000000000");   //草稿参数版本号
            vo.setRemark("导入");
            vo.setFile_name(fileName);
            vo.setBegin_logical_id(begin_logic_no);
            vo.setEnd_logical_id(end_logic_no);
            //插入库
            opLogImportMapper.addOpLogImport(vo);
//          6 复制文件到导入历史文件路径
//            FileCopyUtils.copy(new File(filePath), new File(SysConfigConstant.PHYLOGIC_UPLOAD_PATH + "/his/" + fileName));
//            n = importBo.importData(request, uploadForm.getMakeFile().getInputStream(), terminator);
//            util.logOperation("import", request, preMsg
//                    + ApplicationConstant.OPERATIION_SUCCESS_LOG_MESSAGE);
//            am = new ActionMessage(MSG_KEY, "成功导入" + fd.getContent().get(fileName).size()
//                    + "条记录");
            request.setAttribute("msg", "成功导入" + fd.getContent().get(fileName).size() + "条记录");
//            m.put("returnMsg", "成功导入" + fd.getContent().get(fileName).size() + "条记录");
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("msg", e.getMessage());  // + e.getMessage()
//            m.put("status", false);
//            m.put("returnMsg", "导入失败，" + e.getMessage());
//          E0 如果出错 则删除此次流水号已插入的数据  
//            this.delete(waterNo + "");
//            am = new ActionMessage(MSG_KEY, e.getMessage());
//            util.operationExceptionHandle(request,
//                    "import", e);
            //          E0 如果出错 则删除此次流水号已插入的数据  
            PhyLogicList deletePhyLogicList = new PhyLogicList();
            deletePhyLogicList.setWater_no(waterNo + "");
            phyLogicListMapper.deletePhyLogicList(deletePhyLogicList);
        } finally {
//            F0 删除文件
            File file = new File(filePath);
            // 路径为文件且不为空则进行删除   
            if (file.isFile() && file.exists()) {
                file.delete();
            }
//            String jsonType = "application/json";
//            String responseString = JSON.toJSONString(m);
//            resp.setContentType(jsonType);
//            resp.setCharacterEncoding("UTF-8");
//            byte[] responseBytes = responseString.getBytes("UTF-8");
//            resp.setContentLength(responseBytes.length);
//            ServletOutputStream output = resp.getOutputStream();
//            output.write(responseBytes);
//            output.flush();
        }

        return operationResult;
    }

//    private void returnOpResult(HttpServletRequest req, ActionMessage am) {
//        String msg = CharUtil.IsoToGbk((String) am.getValues()[0]);
//        req.getSession().setAttribute("Message", msg);
//    }
    /**
     *
     * @param fileName
     * @param nameLen
     * @param namePrefix
     * @throws FileNameException
     */
    protected void parseFileName(String fileName, int nameLen, String namePrefix)
            throws FileNameException {
        FileNameParseUtil util = new FileNameParseUtil();
        util.parseLen(fileName, nameLen);
        util.parseFmtForOne(fileName, namePrefix, 7);
        // util.parseDate(fileName);
    }

    /**
     * 文件处理
     *
     * @param path
     * @param fileName
     * @param batchWaterNo
     * @return
     * @throws RecordParseException
     */
    protected FileData parseFile(String path, String fileName, int batchWaterNo)
            throws RecordParseException {
        BufferedReader fis = null;
        // 文件全路径
        String fileNameFull = path + File.separator + fileName;
        StringBuilder sBuilder = new StringBuilder();
        String line;
        String[] lines = null;
        int index = 0;
        // 文件校验使用
        StringBuffer sb = new StringBuffer();
        // 文件的交易记录行数
        int rowCount = 0;
        // 文件处理结果
        FileData fd = new FileData();
        FileRecordHead frh = null;
        FileRecordCrc frc = null;
        Map<String, List<Object>> hm = new HashMap<String, List<Object>>();
        int iChar = 0;
//        FileRecordAddVo lineAdd = new FileRecordAddVo(batchWaterNo, fileName);
        try {
            fis = new BufferedReader(new InputStreamReader(new FileInputStream(fileNameFull)));

            while ((line = fis.readLine()) != null) {
                index++;
                if (index == 1) {
                    frh = parseFileHead(line);// 解析文件头
                    addLineToBuff(sb, line);
                    continue;
                }
                if (isFileCRC(line)) {
                    frc = parseFileCrc(line);// 解析CRC
                    continue;
                }

                // 解析文件内容
                parseFileData(line, hm, fileName);

                addLineToBuff(sb, line);
                // sb.append(line);
                rowCount++;

            }
            checkFileContent(frh, frc, fileName, rowCount, sb);// 校验文件内容
            fd.setHead(frh);
            fd.setCrc(frc);
            fd.setContent(hm);

        } catch (RecordParseException e) {
            throw new RecordParseException(e.getMessage(), "解析文件出错");
        } catch (Exception e) {
            throw new RecordParseException(e.getMessage(), "解析文件出错");
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                }
            } catch (IOException e) {
                logger.error(e.getMessage(), e);
            }
        }
        return fd;

    }

    public FileRecordHead parseFileHead(String line)
            throws RecordParseException {
        FileRecordHead r = new FileRecordHead();
        char[] b = null;
        int offset = 0;
        int len = 2;
        try {
            b = line.toCharArray();
            r.setProducer(this.getCharString(b, offset, len));// 卡生产商
            offset += 2;

            len = 2;
            r.setYear(this.getCharString(b, offset, len));// 封装年份
            offset += 2;

            len = 2;
            r.setSeq(this.getCharString(b, offset, len));// 封装批次号
            offset += 2;

            len = 8;
            r.setRowCount(Integer.parseInt(this.getCharString(b, offset, len)));// 记录数
            offset += 8;

        } catch (Exception ex) {
            throw new RecordParseException("解析文件头出现异常.");
        }
        return r;

    }

    public String getCharString(char[] data, int offset, int length) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(data[offset + i]);
        }
        return sb.toString();
    }

    protected boolean isFileCRC(String line) throws Exception {
        // byte[] b = this.getLineByteFromFileTest(line);
        char[] b = line.toCharArray();
        String crcFlag = this.getCharString(b, 0, 4);
        return crcFlag.startsWith("CRC:");
    }

    public FileRecordCrc parseFileCrc(String line)
            throws RecordParseException {
        FileRecordCrc r = new FileRecordCrc();
        // byte[] b ;//line.getBytes();
        char[] b;
        int offset = 0;
        int len = 4;
        try {
            b = line.toCharArray();

            r.setCrcFlag(this.getCharString(b, offset, len));// CRC:标识
            offset += 4;
            len = 8;
            r.setCrc(this.getCharString(b, offset, len));// CRC值

        } catch (Exception ex) {
            throw new RecordParseException(ex.getMessage());
        }

        return r;

    }

    /**
     * 解析文件内容
     *
     * @param line 当前行内容
     * @param hm
     * @param lineAdd
     * @param trdType
     * @throws RecordParseException
     */
    protected void parseFileData(String line, Map<String, List<Object>> hm,
            String fileName)
            throws RecordParseException {
        try {

            Object ob;
            ob = parseLine(line);
            this.putMap(fileName, ob, hm);
        } catch (Exception e) {
            throw new RecordParseException("解释记录类型" + "出错："
                    + e.getMessage());
        }
    }

    public LogicIccList parseLine(String line)
            throws RecordParseException {
        LogicIccList r = new LogicIccList();
        char[] b = null;
        int offset = 0;
        try {
            b = line.toCharArray();

            int len = 20;
            r.setLogic_no(getCharString(b, offset, len).trim());//逻辑卡号
            offset += len;

            len = 20;
            r.setIcc_id(getCharString(b, offset, len).trim());// 刻印号
            offset += len;

        } catch (Exception ex) {
            throw new RecordParseException("解析文件内容出现异常.");
        }
        return r;

    }

////    物理卡号14位，右补0
//    public  String parsePhysicalNo(String physicalNo){
//        String physicalNoReturn = physicalNo ;
//        int n = 14;
//        n = 14-physicalNo.length() ;
//        if(n>0){
//            for (int i = 0; i < n; i++) {
//                physicalNoReturn += "0";
//            }
//        }
//        return physicalNoReturn ; 
//    }
    protected void putMap(String fileName, Object ob,
            Map<String, List<Object>> hm) {
        if (!hm.containsKey(fileName)) {
            hm.put(fileName, new ArrayList<Object>());
        }
        List<Object> list = hm.get(fileName);
        list.add(ob);
    }

    protected void addLineToBuff(StringBuffer sb, String line) {
        sb.append(line);
        sb.append((char) 13);
        sb.append((char) 10);
    }

    private void checkFileContent(FileRecordHead fh, FileRecordCrc frc,
            String fileName, int rowCount, StringBuffer sb)
            throws RecordParseException {
//		FileNameSection sect = FileUtil.getFileSectForTwo(fileName);
//		checkFileHeadTwo(fh, sect);
        checkFileRecordNum(fh, rowCount);
        checkFileCrc(frc, sb);
    }

    protected void checkFileRecordNum(FileRecordHead fh, int rowCount)
            throws RecordParseException {
        if (rowCount != fh.getRowCount()) {
            throw new RecordParseException("文件的CRC码不正确");
        }
    }

    protected void checkFileCrc(FileRecordCrc frc, StringBuffer sb)
            throws RecordParseException {
        try {
            // String crcCal = CrcUtil.getCRC32Value(sb, CrcUtil.CRC_LEN);
            String crcCal = CrcUtil.getCRC32ValueByChar(sb, CrcUtil.CRC_LEN);

//            logger.info("系统计算CRC码：" + crcCal.toUpperCase() + " 文件计算的CRC:"
//                    + frc.getCrc().toUpperCase());
            if (!crcCal.equalsIgnoreCase(frc.getCrc())) {
                throw new RecordParseException("文件的CRC码不正确");
            }
        } catch (Exception e) {
            throw new RecordParseException("文件的CRC码不正确");
        }
    }

    protected void closeFile(FileInputStream fis) {
        try {
            if (fis != null) {
                fis.close();
            }

        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }

    }

    public void batchInsert(HandleMessageBase handlingMsg) throws SQLException, NamingException {
        logger.info("逻辑卡号刻印号对照表入库开始");
        List<Object> content = handlingMsg.getContent();
        int n = 0;
        String[] fieldValues;
        for (Object obj : content) {
            LogicIccList logicIccList = (LogicIccList) obj;
            logicIccList.setWater_no(String.valueOf(handlingMsg.getWaterNo()));
            logicIccListMapper.addLogicIccList(logicIccList);
            n++;
        }
        logger.info("逻辑卡号刻印号对照表入库结束，插入" + n + "条数据");
    }

    public void addValuesByBatch(DbHelper dbHelper, String[] fieldValues,
            String[] fieldTypes) throws IllegalArgumentException,
            IllegalStateException, SQLException {

        Object[] values = this.getFieldValues(fieldValues, fieldTypes);
        dbHelper.addBatch(values);

        values = null;

    }

    public Object[] getFieldValues(String[] fieldValuesStr, String[] fieldTypes) {

        // if (fieldValuesStr.length != fieldTypes.length)
        // throw new Exception("字段数量与字段类型数量不一致");
        Object[] fieldValues = new Object[fieldValuesStr.length];
        Object fieldValue;
        String fieldType;

        for (int i = 0; i < fieldValuesStr.length; i++) {
            fieldType = (String) fieldTypes[i];

            if (fieldType.equals("string") || fieldType.equals("date")) {
                fieldValue = fieldValuesStr[i];
                fieldValues[i] = fieldValue;
                continue;
            }
            if (fieldType.equals("decimal")) {
                fieldValue = new BigDecimal(fieldValuesStr[i]);
                fieldValues[i] = fieldValue;
                continue;
            }
            if (fieldType.equals("int")) {
                fieldValue = new Integer(fieldValuesStr[i]);
                fieldValues[i] = fieldValue;
                continue;
            }
        }
        return fieldValues;
    }

    protected HandleMessageBase batchDB(String fileName, FileData fd, int waterNo) throws SQLException, NamingException {
        HandleMessageBase msg = new HandleMessageBase();
        msg.setFileName(fileName);
        msg.setContent(fd.getContent().get(fileName));
//		msg.setTradType(tradType);
        msg.setWaterNo(waterNo);

        this.batchInsert(msg);
        return msg;
    }

    private int findWaterNo() throws NamingException, SQLException, Exception {
        return DBUtil.getTableSequence("W_SEQ_W_OP_LOG_LOGIC_IMPORT");
    }

    public boolean isExistFile(String fileName) throws Exception {
        List<OpLogImport> opLogImports = new ArrayList<OpLogImport>();
        OpLogImport opLogImport = new OpLogImport();
        opLogImport.setTable_name("w_op_prm_phy_logic_list");
        opLogImport.setFile_name(fileName);
        opLogImports = opLogImportMapper.getOpLogImports(opLogImport);
        if (opLogImports != null && opLogImports.size() > 0) {
            return true;
        } else {
            return false;
        }
    }
}
