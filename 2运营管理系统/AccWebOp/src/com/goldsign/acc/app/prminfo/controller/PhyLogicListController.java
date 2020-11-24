package com.goldsign.acc.app.prminfo.controller;

import com.alibaba.fastjson.JSON;
import com.goldsign.acc.app.opma.entity.CodPubFlag;
import com.goldsign.acc.app.prminfo.constant.FrameFileHandledConstant;
import com.goldsign.acc.app.prminfo.entity.HandleMessageBase;
import com.goldsign.acc.app.prminfo.entity.OpLogImport;
import com.goldsign.acc.app.prminfo.entity.FileData;
import com.goldsign.acc.app.prminfo.entity.FileRecordHead;
import com.goldsign.acc.app.prminfo.entity.FileRecordCrc;
import com.goldsign.acc.app.prminfo.entity.HisPhyLogicList;
//import com.goldsign.acc.app.prminfo.entity.IcBcLogicNo;
import com.goldsign.acc.app.prminfo.exception.FileNameException;
import com.goldsign.acc.app.prminfo.exception.RecordParseException;
import com.goldsign.acc.app.prminfo.util.FileNameParseUtil;
import com.goldsign.acc.app.prminfo.util.CrcUtil;
import com.goldsign.acc.app.prminfo.entity.PhyLogicList;
import com.goldsign.acc.app.prminfo.mapper.HisPhyLogicListMapper;
//import com.goldsign.acc.app.prminfo.mapper.IcBcLogicNoMapper;
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
import com.goldsign.acc.app.prminfo.mapper.OpLogImportMapper;
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
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import javax.naming.NamingException;
import javax.servlet.ServletOutputStream;
import org.apache.log4j.Logger;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

/**
 * 物理逻辑卡号对照表
 *
 * @author xiaowu
 */
@Controller
public class PhyLogicListController extends PrmBaseController {

    Logger logger = Logger.getLogger(PhyLogicListController.class);

    @Autowired
    private OpLogImportMapper opLogImportMapper;

//    @Autowired
//    private IcBcLogicNoMapper icBcLogicNoMapper;
    @Autowired
    private PhyLogicListMapper phyLogicListMapper;

    @Autowired
    private HisPhyLogicListMapper hisPhyLogicListMapper;

    @RequestMapping("/phyLogicList")
    public ModelAndView service(HttpServletRequest request, HttpServletResponse response) {

        ModelAndView mv = new ModelAndView("/jsp/prminfo/phy_logic_list.jsp");

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
                    //打开导入页面
                    return new ModelAndView("/jsp/prminfo/phy_logic_list_import.jsp");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        String[] attrNames = {OPERATORS};

        this.setPageOptions(attrNames, mv, request, response);//设置页面操作员选项值
        this.getResultSetText((List<OpLogImport>) opResult.getReturnResultSet(), mv);
        this.baseHandler(request, response, mv);
        this.divideResultSet(request, mv, opResult);//结果集分页
        this.SaveOperationResult(mv, opResult);//返回结果集
        return mv;
    }

    @RequestMapping("/phyLogicListImp")
    public ModelAndView devImp(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView("/jsp/prminfo/phy_logic_list_import.jsp");
        return mv;
    }

    private void getResultSetText(List<OpLogImport> resultSet, ModelAndView mv) {
        List<PubFlag> operators = (List<PubFlag>) mv.getModelMap().get(PrmBaseController.OPERATORS);
        for (OpLogImport opLogImport : resultSet) {
            if (operators != null && !operators.isEmpty()) {
                opLogImport.setOperator_name(DBUtil.getTextByCode(opLogImport.getOperator_id(), operators));
            }
        }
    }

    public OperationResult query(HttpServletRequest request, OpLogImportMapper opLogImportMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult or = new OperationResult();
        LogUtil logUtil = new LogUtil();
        OpLogImport opLogImport;
        List<OpLogImport> resultSet;

        try {
            opLogImport = this.getQueryCondition(request);
            opLogImport.setTable_name("w_op_prm_phy_logic_list");
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

    @RequestMapping("/PhyLogicListExportAll")
    public void exportExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {
        List results = this.getBufferElementsForCurClass(request, "com.goldsign.acc.app.prminfo.entity.OpLogImport");
        String expAllFields = request.getParameter("expAllFields");
        List<Map<String, Object>> queryResults = ExpUtil.entityToMap(expAllFields, results);
        ExpUtil.renderExportExcelPath(response, ExpUtil.exportExcel(queryResults, request));
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

    @RequestMapping("/phyLogicListUpload")
    public ModelAndView phyLogicListUpload(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView("/jsp/prminfo/phy_logic_list_import.jsp");
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

    private OperationResult importFile(HttpServletRequest request, HttpServletResponse response) throws Exception {
        PhyLogicList phyLogicList = new PhyLogicList();
        OpLogImport opLogImport = new OpLogImport();
        int Insertnum = 0;
        String errorMsg = "";
        String errorMsg1 = "";
        int error = 0;
        List<String> filePathList = new ArrayList<String>();

        int n = 0;
        String preMsg = "物理逻辑卡号对照表导入：";
        OperationResult operationResult = null;

        HandleMessageBase messageBase = null;
        int finishFlag = 4;
        String filePath = "";
        int waterNo = 0;
        Map<String, Object> m = new HashMap<String, Object>();
        m.put("status", true);
        try {
            MultipartResolver resolver = new CommonsMultipartResolver(request.getSession().getServletContext());
            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
            Iterator<String> iter = multipartRequest.getFileNames();

//            CommonsMultipartFile file = (CommonsMultipartFile) multipartRequest
//                    .getFile("uploader");
//            List<MultipartFile> file = multipartRequest.getFiles("makeFile");
//            MultipartHttpServletRequest multipartRequest = resolver.resolveMultipart(request); 
//            CommonsMultipartFile file = (CommonsMultipartFile) multipartRequest.getFiles("makeFile").get(0);
//                    .getFile("makeFile");
//            FormFile file = uploadForm.getUpload();
            while (iter.hasNext()) {
                MultipartFile file = multipartRequest.getFile(iter.next());
                if (file == null) {
                    throw new Exception("上传的文件为空");
                }
//            for (int i = 0; i < file.size(); i++) {

                String fileName = file.getOriginalFilename();
                m.put("fileName", fileName);
//            String fileName = file.getFileName().substring(file.getFileName().lastIndexOf("\\") + 1);
//            m.put("fileName", fileName);
                request.setAttribute("fileName", fileName);
//           1.1 校验文件名
                parseFileName(fileName, 17, "PRT");
//                if (parseFileName == 1) {
//                    error = 3;
//                    m.put("status", false);
//                    errorMsg = errorMsg + fileName + "导入失败，文件名长度不合法";
//                    m.put("returnMsg", errorMsg);
//                    throw new Exception(FrameFileHandledConstant.FILE_ERR_FILE_NAME_LEN[1]);
//                } else if (parseFileName == 2) {
//                    error = 3;
//                    m.put("status", false);
//                    errorMsg = errorMsg + fileName + "导入失败，文件名格式不合法";
//                    m.put("returnMsg", errorMsg);
//                    throw new Exception(FrameFileHandledConstant.FILE_ERR_FILE_NAME_LEN[1]);
//                }

//           1.2 不能重复导入相同文件名的文件。
//            if (isExistFile(fileName)) {
//                throw new Exception("文件已被导入过。");
//            }
//          2 保存文件
                //保存文件的临时文件夹
                String phylogicUploadPath = ConfigConstant.PHYLOGIC_UPLOAD_PATH;
                //(String) ConfigConstant.FILTER_PROPERTIES.get(AuthenticateInterceptor.PHYLOGIC_UPLOAD_PATH);
                filePath = phylogicUploadPath + File.separator + fileName;

//                filePathList.add(filePath);
                BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(new File(filePath)));
                FileUtil.copy(file.getInputStream(), out);
//          3 校验文件内容

                FileData fd = parseFile(phylogicUploadPath, fileName, waterNo);
                List phyLogicListList = fd.getContent().get(fileName);
                String begin_logic_no = ((PhyLogicList) phyLogicListList.get(0)).getLogic_no();
                String end_logic_no = ((PhyLogicList) phyLogicListList.get(phyLogicListList.size() - 1)).getLogic_no();
//          3.1 校验是否已经添加已审核的空白卡订单 逻辑卡号取后8位
//            String blankLogicBegin = begin_logic_no.substring(begin_logic_no.length() - 8, begin_logic_no.length());
//            String blankLogicEnd = end_logic_no.substring(end_logic_no.length() - 8, end_logic_no.length());
//            if (!phyLogicListBo.findRelatedLogicBill(blankLogicBegin, blankLogicEnd)) {
//                throw new Exception("请添加物理卡号" + blankLogicBegin + "至" + blankLogicEnd + "到空白卡订单！");
//            }

                //if (!phyLogicListBo.findRelatedLogicBill(phyLogicListVoList)) {
                //    throw new Exception("空白卡订单内没有需要导入的逻辑卡号，请在票务系统增加相应订单。！");
                //}
//            if (!this.findRelatedLogicBill(phyLogicListList)) {
//                throw new Exception("空白卡订单内没有需要导入的逻辑卡号，请在票务系统增加相应订单。！");
//            }
////          3.2 校验此段的物理逻辑卡号是否已经在数据日志中存在。            
//            ImportLogVo queryImportVo = new ImportLogVo();
//            queryImportVo.setBegin_logical_id(begin_logic_no);
//            queryImportVo.setEnd_logical_id(end_logic_no);
//            Vector queryImportVector = phyLogicListBo.findImportLog(queryImportVo);
//            if (queryImportVector != null && queryImportVector.size() > 0) {
//                throw new Exception("逻辑卡号段" + begin_logic_no + "至" + end_logic_no + "已被导入过！");
//            }
//          3.3  校验物理卡号是否曾被导入过 逐条判断
                List<PhyLogicList> duplicatePhyLogicList = this.findPhysicDuplicate(phyLogicListList);
                if (!testFilePhysic(phyLogicListList).equals("")) {
                    throw new Exception("导入的文件内有重复的物理卡号:" + testFilePhysic(phyLogicListList) + "等");
                }
                if (!testFileLogic(phyLogicListList).equals("")) {
                    throw new Exception("导入的文件内有重复的逻辑卡号:"+testFileLogic(phyLogicListList)+"等");
                }
                if (duplicatePhyLogicList != null && duplicatePhyLogicList.size() > 0) {
//                    error = 1;
//                    m.put("status", false);
//                    errorMsg = errorMsg + fileName + "导入失败，物理卡号" + this.getDuplicatePhyLogicStr(duplicatePhyLogicList, "physicNo") + "已被导入过！";
//                    m.put("returnMsg", errorMsg);
//                    request.setAttribute("msg", errorMsg);
                    throw new Exception("物理卡号" + this.getDuplicatePhyLogicStr(duplicatePhyLogicList, "physicNo") + "已被导入过");
                }
//          3.4  校验逻辑卡号是否曾被导入过 逐条判断 不足20位增加前补零
                List<PhyLogicList> duplicateLogicList = this.findLogicDuplicate(phyLogicListList);
                if (duplicateLogicList != null && duplicateLogicList.size() > 0) {
//                    error = 2;
//                    m.put("status", false);
//                    errorMsg = errorMsg + fileName + "导入失败，逻辑卡号" + this.getDuplicatePhyLogicStr(duplicateLogicList, "logicNo") + "已被导入过！";
//                    m.put("returnMsg", errorMsg);
//                    request.setAttribute("msg", errorMsg);
                    throw new Exception("逻辑卡号" + this.getDuplicatePhyLogicStr(duplicateLogicList, "logicNo") + "已被导入过");
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
                vo.setTable_name("w_op_prm_phy_logic_list");
                vo.setRecord_count(String.valueOf(phyLogicListList.size()));
                vo.setVersion_no("0000000000");   //草稿参数版本号
                vo.setRemark("导入");
                vo.setFile_name(fileName);
                vo.setBegin_logical_id(begin_logic_no);
                vo.setEnd_logical_id(end_logic_no);
                //插入库
                opLogImportMapper.addOpLogImport(vo);
                //new ImportUtil().logImport(vo);
//          6 复制文件到导入历史文件路径
//            FileCopyUtils.copy(new File(filePath), new File(SysConfigConstant.PHYLOGIC_UPLOAD_PATH + "/his/" + fileName));
//            n = importBo.importData(request, uploadForm.getMakeFile().getInputStream(), terminator);
                //util.logOperation("import", request, preMsg
                //        + ApplicationConstant.OPERATIION_SUCCESS_LOG_MESSAGE);
                Insertnum += fd.getContent().get(fileName).size();
                m.put("returnMsg", "成功导入" + fd.getContent().get(fileName).size() + "条记录");
                request.setAttribute("msg", "成功导入" + Insertnum
                        + "条记录");
                //m.put("returnMsg", "成功导入" + fd.getContent().get(fileName).size() + "条记录");
//            }
            }
        } catch (Exception e) {
            e.printStackTrace();
            //m.put("status", false);
            if (error == 0) {
                m.put("status", false);
                m.put("returnMsg", e.getMessage());
                request.setAttribute("msg", "导入失败；保存数据库时出现异常，请联系管理员！");  //+ e.getMessage()
            }

//          E0 如果出错 则删除此次流水号已插入的数据  
            PhyLogicList deletePhyLogicList = new PhyLogicList();
            deletePhyLogicList.setWater_no(waterNo + "");
            OpLogImport op = new OpLogImport();
            op.setWater_no(waterNo + "");
            opLogImportMapper.deleteOpLog(op);
            phyLogicListMapper.deletePhyLogicList(deletePhyLogicList);

            //this.delete(waterNo + "");
            //am = new ActionMessage(MSG_KEY, e.getMessage());
            // util.operationExceptionHandle(request,
            //       "import", e);
        } finally {
//            F0 删除文件
            File file = new File(filePath);
            // 路径为文件且不为空则进行删除   
            if (file.isFile() && file.exists()) {
                file.delete();
            }

//            request.setAttribute("msg", "导入失败");
            String jsonType = "application/json";
            String responseString = JSON.toJSONString(m);
            response.setContentType(jsonType);
            response.setCharacterEncoding("UTF-8");
            byte[] responseBytes = responseString.getBytes("UTF-8");
            response.setContentLength(responseBytes.length);
            PrintWriter writer = response.getWriter();
            writer.write(responseString);
            writer.flush();
//            ServletOutputStream output = response.getOutputStream();     
//            output.write(responseBytes);
//            output.flush();

        }
        return operationResult;
    }

//    private boolean findRelatedLogicBill(List phyLogicListList){
//        boolean returnFlag = true;
//        //取全部已审核的空白卡订单 看逻辑卡号是否在订单中
//        List<OpLogImport> blacklist = new ArrayList();
//        List<IcBcLogicNo>icBcLogicNos = icBcLogicNoMapper.getIcBcLogicNos();
//        for (int i = 0; i < phyLogicListList.size(); i++) {
//            if (!this.isInLogicBillList(((PhyLogicList) phyLogicListList.get(i)).getLogic_no(), icBcLogicNos)) {
//                returnFlag = false;
//                break;
//            }
//        }
//        return returnFlag;
    //select * from acc_tk.IC_BC_LOGIC_NO where record_flag='0' and blank_card_type='1' order by start_logicno asc 
//        ImportLogVo pg = new ImportLogVo();
//        pg.setRecordCount(Integer.parseInt(dbHelper.getItemValue("qty")));
//        pg.setBegin_logical_id(dbHelper.getItemValue("start_logicno"));
//        pg.setEnd_logical_id(dbHelper.getItemValue("end_logicno"));
//    }
//    private boolean isInLogicBillList(String logicNo, List<IcBcLogicNo> icBcLogicNos) {
//        boolean returnFlag = false;
//        //逻辑卡号取后8位
//        String checkLogicNo = logicNo.substring(logicNo.length() - 8, logicNo.length());
//        for (IcBcLogicNo icBcLogicNo : icBcLogicNos) {
//            if (Integer.parseInt(checkLogicNo) >= Integer.parseInt(icBcLogicNo.getStart_logicno())
//                    && Integer.parseInt(checkLogicNo) <= Integer.parseInt(icBcLogicNo.getEnd_logicno())) {
//                returnFlag = true;
//                break;
//            }
//        }
//        return returnFlag;
//    }
    private String testFilePhysic(List phyLogicListList) {
        Set<String> set = new HashSet<String>();
        String repeatNo = "";
        List physicNoList = new ArrayList();
        if (phyLogicListList != null && phyLogicListList.size() > 0) {
            for (int i = 0; i < phyLogicListList.size(); i++) {
                physicNoList.add(((PhyLogicList) phyLogicListList.get(i)).getPhysic_no());
            }
            for (int j = 0; j < physicNoList.size(); j++) {
                set.add(physicNoList.get(j).toString());
                if (set.size() != (j + 1)) {
                    repeatNo = physicNoList.get(j).toString();
                    return repeatNo;
                }
            }

        }
        return repeatNo;
    }

    private String testFileLogic(List phyLogicListList) {
        Set<String> set = new HashSet<String>();
        String repeatNo = "";
        List physicNoList = new ArrayList();
        if (phyLogicListList != null && phyLogicListList.size() > 0) {
            for (int i = 0; i < phyLogicListList.size(); i++) {
                physicNoList.add(((PhyLogicList) phyLogicListList.get(i)).getLogic_no());
            }
            for (int j = 0; j < physicNoList.size(); j++) {
                set.add(physicNoList.get(j).toString());
                if (set.size() != (j + 1) ) {
                    repeatNo = physicNoList.get(j).toString();
                    return repeatNo;
                }
            }

        }
        return repeatNo;
    }

    private List<PhyLogicList> findPhysicDuplicate(List phyLogicListList) {
        List<PhyLogicList> resultPhyLogicLists = new ArrayList<PhyLogicList>();
        List<PhyLogicList> duplicatePhyLogicList = new ArrayList();
        String[] tableNames = {"w_op_prm_phy_logic_list", "w_op_his_phy_logic_list"};
        //            500个waterNo用in(?,?)查询一次
        List physicNoList = new ArrayList();
        List<List> physicNoListList = new ArrayList<List>();
        StringBuilder inClause = new StringBuilder();
        for (int i = 0; i < phyLogicListList.size(); i++) {
            physicNoList.add(((PhyLogicList) phyLogicListList.get(i)).getPhysic_no());
            if (i != 0 && i % 500 == 0 || i == phyLogicListList.size() - 1) {
                List physicNoListCopy = new ArrayList();
                physicNoListCopy.addAll(physicNoList);
                physicNoListList.add(physicNoListCopy);
                physicNoList.clear(); //清空再添加下一个500
            }
        }
        try {
            List<HisPhyLogicList> hisPhyLogicLists = new ArrayList<HisPhyLogicList>();
            List<PhyLogicList> phyLogicLists = new ArrayList<PhyLogicList>();
            if (physicNoListList != null && phyLogicListList.size() > 0) {
                for (String tableName : tableNames) {
                    for (List tempList : physicNoListList) {
                        if (tableName.equals("w_op_prm_phy_logic_list")) {
                            phyLogicLists = phyLogicListMapper.getPhyLogicListsByInPhysicNos(tempList);
                            if (phyLogicLists != null && phyLogicLists.size() > 0) {
                                for (PhyLogicList tempPLL : phyLogicLists) {
                                    if (tempPLL != null) {
                                        resultPhyLogicLists.add(tempPLL);
                                    }
                                }
                            }
                            phyLogicLists.clear();
                        } else {
                            hisPhyLogicLists = hisPhyLogicListMapper.getHisPhyLogicListsByInPhysicNos(tempList);
                            if (hisPhyLogicLists != null && hisPhyLogicLists.size() > 0) {
                                for (HisPhyLogicList tempHPLL : hisPhyLogicLists) {
                                    if (tempHPLL != null) {
                                        PhyLogicList tempLogicList = new PhyLogicList();
                                        tempLogicList.setPhysic_no(tempHPLL.getPhysic_no());
                                        tempLogicList.setLogic_no(tempHPLL.getLogic_no());
                                        resultPhyLogicLists.add(tempLogicList);
                                    }
                                }
                            }
                            hisPhyLogicLists.clear();
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultPhyLogicLists;
    }

    private List<PhyLogicList> findLogicDuplicate(List phyLogicListList) {
        List<PhyLogicList> resultPhyLogicLists = new ArrayList<PhyLogicList>();
        List<PhyLogicList> duplicatePhyLogicList = new ArrayList();
        String[] tableNames = {"w_op_prm_phy_logic_list", "w_op_his_phy_logic_list"};
        //            500个waterNo用in(?,?)查询一次
        List logicNoList = new ArrayList();
        List<List> logicNoListList = new ArrayList<List>();
        StringBuilder inClause = new StringBuilder();
        for (int i = 0; i < phyLogicListList.size(); i++) {
            logicNoList.add(((PhyLogicList) phyLogicListList.get(i)).getLogic_no());
            if (i != 0 && i % 500 == 0 || i == phyLogicListList.size() - 1) {
                List logicNoListCopy = new ArrayList();
                logicNoListCopy.addAll(logicNoList);
                logicNoListList.add(logicNoListCopy);
                logicNoList.clear(); //清空再添加下一个500 
            }
        }
        List<HisPhyLogicList> hisPhyLogicLists = new ArrayList<HisPhyLogicList>();
        List<PhyLogicList> phyLogicLists = new ArrayList<PhyLogicList>();
        if (phyLogicListList != null && phyLogicListList.size() > 0) {
            for (String tableName : tableNames) {
                for (List tempList : logicNoListList) {
                    if (tableName.equals("w_op_prm_phy_logic_list")) {
                        phyLogicLists = phyLogicListMapper.getPhyLogicListsByInLogicNos(tempList);
                        if (phyLogicLists != null && phyLogicLists.size() > 0) {
                            for (PhyLogicList tempPLL : phyLogicLists) {
                                if (tempPLL != null) {
                                    resultPhyLogicLists.add(tempPLL);
                                }
                            }
                        }
                        phyLogicLists.clear();
                    } else {
                        hisPhyLogicLists = hisPhyLogicListMapper.getHisPhyLogicListsByInLogicNos(tempList);
                        if (hisPhyLogicLists != null && hisPhyLogicLists.size() > 0) {
                            for (HisPhyLogicList tempHPLL : hisPhyLogicLists) {
                                if (tempHPLL != null) {
                                    PhyLogicList tempLogicList = new PhyLogicList();
                                    tempLogicList.setPhysic_no(tempHPLL.getPhysic_no());
                                    tempLogicList.setLogic_no(tempHPLL.getLogic_no());
                                    resultPhyLogicLists.add(tempLogicList);
                                }
                            }
                        }
                        hisPhyLogicLists.clear();
                    }
                }
            }
        }
        return resultPhyLogicLists;
    }

    private String getDuplicatePhyLogicStr(List<PhyLogicList> duplicatePhyLogicList, String columName) {
        String returnStr = "";
        for (int i = 0; i < duplicatePhyLogicList.size(); i++) {
            PhyLogicList phyLogicListVO = duplicatePhyLogicList.get(i);
            if (columName.equals("physicNo")) {
                returnStr += phyLogicListVO.getPhysic_no();
            } else {
                returnStr += phyLogicListVO.getLogic_no();
            }
            if (i > 2) {
                returnStr += "等,";
                break;
            }
            returnStr += ",";
        }
        if (returnStr.length() > 0) {
            returnStr = returnStr.substring(0, returnStr.length() - 1);
        }
        return returnStr;
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
//        try {
//            util.parseLen(fileName, nameLen);
//
//        } catch (Exception e) {
//            return 1;
//        }
//        try {
//            util.parseFmtForOne(fileName, namePrefix, 6);
//        } catch (Exception e) {
//            return 2;
//        }
//        return 0;
        util.parseLen(fileName, nameLen);
        util.parseFmtForOne(fileName, namePrefix, 6);
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
                e.printStackTrace();
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

    public PhyLogicList parseLine(String line)
            throws RecordParseException {
        PhyLogicList r = new PhyLogicList();
        char[] b = null;
        int offset = 0;
        try {
            b = line.toCharArray();

            int len = 20;
            r.setPhysic_no(getCharString(b, offset, len).trim());//物理卡号
            offset += len;

            len = 20;
            r.setLogic_no(getCharString(b, offset, len).trim());// 逻辑卡号
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
            throw new RecordParseException("文件头与记录数不匹配");
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
            e.printStackTrace();
        }

    }

    public void batchInsert(HandleMessageBase handlingMsg) throws SQLException, NamingException {
        logger.info("物理逻辑卡号对照表入库开始");
        List<Object> content = handlingMsg.getContent();
        int n = 0;
        String[] fieldValues;
        for (Object obj : content) {
            PhyLogicList phyLogicList = (PhyLogicList) obj;
            phyLogicList.setWater_no(String.valueOf(handlingMsg.getWaterNo()));
            phyLogicListMapper.addPhyLogicList(phyLogicList);
            n++;
        }
        logger.info("物理逻辑卡号对照表入库结束，插入" + n + "条数据");
    }

    protected HandleMessageBase batchDB(String fileName, FileData fd, int waterNo) throws SQLException, NamingException {
        HandleMessageBase msg = new HandleMessageBase();
        msg.setFileName(fileName);
        msg.setContent(fd.getContent().get(fileName));
        msg.setWaterNo(waterNo);

        this.batchInsert(msg);
        return msg;
    }

    private int findWaterNo() throws NamingException, SQLException, Exception {
        return DBUtil.getTableSequence("W_SEQ_W_OP_LOG_PHY_IMPORT");
    }

//    public int delete(String waterNo)
//            throws Exception {
//        DbHelper dbHelper = null;
//        int result = 0;
//
//        String strSql = "delete from op_prm_phy_logic_list where water_no=? ";
//        try {
//            dbHelper = new DbHelper(FrameDBConstant.MAIN_DATASOURCE);   
//            dbHelper.setAutoCommit(false);
//            ArrayList pStmtValues = new ArrayList();
//            pStmtValues.clear();
//            pStmtValues.add(waterNo);
//
//            result += dbHelper.executeUpdate(strSql, pStmtValues.toArray());
//            dbHelper.commitTran();
//            dbHelper.setAutoCommit(true);
//        } catch (Exception e) {
//            PubUtil.handleExceptionForTran(e, logger, dbHelper);
//        } finally {
//            PubUtil.finalProcess(dbHelper, logger);
//        }
//        return result;
//    }
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

//        DbHelper dbHelper = null;
//        String strSql = null;
//        boolean result = false;
//        ArrayList pStmtValues = new ArrayList();
//        pStmtValues.add("op_prm_phy_logic_list");
//        pStmtValues.add(fileName);
//
//        try {
//            dbHelper = new DbHelper(FrameDBConstant.MAIN_DATASOURCE);
//            strSql = "select water_no from op_log_import where table_name=? and file_name=?";
//            result = dbHelper.getFirstDocument(strSql, pStmtValues.toArray());
//
//        } catch (Exception e) {
//            PubUtil.handleExceptionForTran(e, logger, dbHelper);
//        } finally {
//            PubUtil.finalProcess(dbHelper, logger);
//        }
//        return result;
    }
}
