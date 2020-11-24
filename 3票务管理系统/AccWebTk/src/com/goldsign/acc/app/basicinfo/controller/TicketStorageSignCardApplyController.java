/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.goldsign.acc.app.basicinfo.controller;

import com.goldsign.acc.app.basicinfo.entity.TicketStorageSignCard;
import com.goldsign.acc.frame.constant.CommandConstant;
import com.goldsign.acc.frame.constant.PubFlagConstant;
import com.goldsign.acc.frame.controller.StorageOutInBaseController;
import com.goldsign.acc.frame.entity.PubFlag;
import com.goldsign.acc.frame.util.PageControlUtil;
import com.goldsign.acc.frame.vo.OperationResult;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.TransactionStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import com.goldsign.acc.app.basicinfo.mapper.TicketStorageSignCardMapper;
import static com.goldsign.acc.app.query.controller.TicketStorageQueryController.analysisLine;
import static com.goldsign.acc.app.query.controller.TicketStorageQueryController.get_charset;
import com.goldsign.acc.app.basicinfo.entity.TicketStorageStListSignCard;
import com.goldsign.acc.frame.util.StringUtil;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import com.goldsign.acc.app.basicinfo.mapper.TicketStorageStListSignCardMapper;
import com.goldsign.acc.app.config.entity.Config;
import com.goldsign.acc.app.config.entity.ConfigKey;
import com.goldsign.acc.app.config.mapper.ConfigMapper;
import com.goldsign.acc.frame.util.ExpUtil;
import com.goldsign.acc.frame.util.FltTabUtil;

/**
 * @author  刘粤湘
 * @date    2017-9-2 14:25:33
 * @version V1.0
 * @desc  
 */
@Controller    
public class TicketStorageSignCardApplyController extends StorageOutInBaseController {
    
     @Autowired
    TicketStorageSignCardMapper signCardMapper;
     
    @Autowired
    TicketStorageStListSignCardMapper stListSignCardMapper;
     
    @Autowired
    private ConfigMapper configMapper;    
    
    private FltTabUtil fltTabUtil;
  
    @RequestMapping(value = "/ticketStorageSignCardApply")
    public ModelAndView nameCardReq(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView("/jsp/basicinfo/ticketStorageSignCardApply.jsp");
        String command = request.getParameter("command");    
        
        OperationResult opResult = new OperationResult();
        fltTabUtil = new FltTabUtil();
        fltTabUtil.initFilterNeed(this.pubFlagMapper);
        try{
            if (command != null) {
                command = command.trim();
                TicketStorageSignCard signCard = new TicketStorageSignCard();
                
                int i = 0;
                
                if (command.equals(CommandConstant.COMMAND_QUERY)) {
                    
                    signCard = this.getQrySignCardObj(request,signCard);
                    List<TicketStorageSignCard> signCards = signCardMapper.qrySignCard(signCard);
                    signCards = this.fltSingCards(signCards);
                    opResult.setReturnResultSet(signCards);
                    opResult.addMessage("成功查询" + signCards.size() + "条记录");
                }
                if (command.equals(CommandConstant.COMMAND_ADD)) {
                    signCard = this.getInSignCardObj(request,signCard);
                    TicketStorageSignCard qrySignCard  = new TicketStorageSignCard ();
                    qrySignCard.setIdentityId(signCard.getIdentityId());
                    List<TicketStorageSignCard> signCards = signCardMapper.qrySignCard(qrySignCard);
                    if(signCards!=null && signCards.size()>0){
                        opResult.addMessage("证件号码重复");
                        opResult.setReturnResultSet(signCards);
                    }
                    else{
                        i = signCardMapper.insertSelective(signCard);
                        opResult.addMessage("成功增加" + i + "条记录");
//                    qrySignCard  = new TicketStorageSignCard ();
//                    qrySignCard.setIdentityId(signCard.getIdentityId());
                        signCards = signCardMapper.qrySignCard(qrySignCard);
                        signCards = this.fltSingCards(signCards);
                        opResult.setReturnResultSet(signCards);
                    }
                }
                if (command.equals(CommandConstant.COMMAND_MODIFY)) {
                    signCard = this.getInSignCardObj(request,signCard);
                    i = signCardMapper.updateByPrimaryKeySelective(signCard);
                    opResult.addMessage("成功修改" + i  + "条记录");
                    TicketStorageSignCard qrySignCard  = new TicketStorageSignCard ();
                    qrySignCard.setReqNo(signCard.getReqNo());
                    List<TicketStorageSignCard> signCards = signCardMapper.qrySignCard(qrySignCard);
                    signCards = this.fltSingCards(signCards);
                    opResult.setReturnResultSet(signCards);
                }
                if (command.equals(CommandConstant.COMMAND_DELETE)) {
                    String strKeyIDs = request.getParameter("allSelectedIDs");
                    
                    String[] szKeyIds = strKeyIDs.split(";");
                    TransactionStatus status = null;

                    try {
                        status = txMgr.getTransaction(this.def);
                        for (String keyId : szKeyIds) {
                            i += signCardMapper.deleteByPrimaryKey(keyId);
                        }

                        opResult.addMessage("成功删除" + i + "条记录");
                        txMgr.commit(status);

                    } catch (Exception e) {
                        e.printStackTrace();

                        if (txMgr != null) {
                            txMgr.rollback(status);
                        }
                        throw e;
                    }
                }
            }
               
        }catch(Exception e){
            e.printStackTrace();
            opResult.addMessage(e.getMessage());
        }
        
         //性别 证件类型 测试标志
         
         mv.addObject("sexs", fltTabUtil.sexsTmp);
         mv.addObject("crdTypes", fltTabUtil.crdTypesTmp);
         mv.addObject("testFlags", fltTabUtil.testFlagsTmp);

        String[] attrNames = this.getAttributeNames();
         this.setPageOptions(attrNames, mv, request, response);
         this.baseHandler(request, response, mv);
         this.divideResultSet(request, mv, opResult);
         this.SaveOperationResult(mv, opResult); 
      
        return mv;
    }
    
    private TicketStorageSignCard getQrySignCardObj(HttpServletRequest request,TicketStorageSignCard signCard) {
        if(signCard == null){
            return signCard;
        }
        
        String q_operator = request.getParameter("q_operator");
        if(q_operator!=null && q_operator.trim().equals("")){
            q_operator = null;
        }
        signCard.setOperatorId(q_operator);
        
        String q_apply_name = request.getParameter("q_apply_name");
        if(q_apply_name!=null && q_apply_name.trim().equals("")){
            q_apply_name = null;
        }
        signCard.setApplyName(q_apply_name);
        
        String q_identityTd = request.getParameter("q_identityTd");
        if(q_identityTd!=null && q_identityTd.trim().equals("")){
            q_identityTd = null;
        }
        signCard.setIdentityId(q_identityTd);
        
        String q_app_business_type = request.getParameter("q_app_business_type");
        if(q_app_business_type!=null && q_app_business_type.trim().equals("")){
            q_app_business_type = null;
        }
        signCard.setAppBusinessType(q_app_business_type);
        
        String q_beginTime = request.getParameter("q_beginTime");
        if(q_beginTime!=null && q_beginTime.trim().equals("")){
            q_beginTime = null;
        }
        signCard.setBeginTime(q_beginTime);
        
        String q_endTime = request.getParameter("q_endTime");
        if(q_endTime!=null && q_endTime.trim().equals("")){
            q_endTime = null;
        }
        signCard.setEndTime(q_endTime);
        
         String q_hdl_flag = request.getParameter("q_hdl_flag");
        if(q_hdl_flag!=null && q_hdl_flag.trim().equals("")){
            q_hdl_flag = null;
        }
        signCard.setHdlFlag(q_hdl_flag);
        
         String q_cardMainCode = request.getParameter("q_cardMainCode");
        if(q_cardMainCode!=null && q_cardMainCode.trim().equals("")){
            q_cardMainCode = null;
        }
        signCard.setCardMainId(q_cardMainCode);
        
        String q_cardSubCode = request.getParameter("q_cardSubCode");
        if(q_cardSubCode!=null && q_cardSubCode.trim().equals("")){
            q_cardSubCode = null;
        }
        signCard.setCardSubId(q_cardSubCode);
        
         String q_line_id = request.getParameter("q_line_id");
        if(q_line_id!=null && q_line_id.trim().equals("")){
            q_line_id = null;
        }
        signCard.setLineId(q_line_id);
        
        String q_station_id = request.getParameter("q_station_id");
        if(q_station_id!=null && q_station_id.trim().equals("")){
            q_station_id = null;
        }        
        signCard.setStationId(q_station_id);
        
        return signCard;
    }
    
    private List<TicketStorageSignCard> fltSingCards(List<TicketStorageSignCard> signCards) {
        
        if(signCards == null){
            return signCards;
        }
        
        for(TicketStorageSignCard signCard: signCards){
            if(signCard.getCardMainId()!=null){
                signCard.setCardMainIdText(fltTabUtil.mapAfcMainTypes.get(signCard.getCardMainId().trim()));
            }
            if(signCard.getCardMainId()!=null&&signCard.getCardSubId()!=null){
                signCard.setCardSubIdText(fltTabUtil.mapAfcMainSubs.get(signCard.getCardMainId().trim() + signCard.getCardSubId().trim()));
            }
            if(signCard.getLineId()!=null){
                signCard.setLineIdText(fltTabUtil.mapLines.get(signCard.getLineId().trim()));
            }
//            System.out.println(signCard.getLineIdText());
            System.out.println(signCard.getStationId());
            if(signCard.getStationId() !=null && signCard.getLineId()!=null){
                signCard.setStationIdText(fltTabUtil.mapStations.get(signCard.getLineId().trim() +  signCard.getStationId().trim()));
            }
//            signCard.setCardMainId(signCard.getCardMainId().trim());
//            signCard.setCardSubId(fltTabUtil.mapAfcMainSubs.get(signCard.getCardMainId().trim() + signCard.getCardSubId().trim()));
//            signCard.setLineId(signCard.getLineId().trim());
//            signCard.setStationId(signCard.getStationId().trim());
            signCard.setApplySex(fltTabUtil.mapSexs.get(signCard.getApplySex()));
            signCard.setIdentityType(fltTabUtil.mapCrdTypes.get(signCard.getIdentityType()));
            signCard.setCardAppFlag(fltTabUtil.mapTestFlags.get(signCard.getCardAppFlag()));
            signCard.setHdlFlag(fltTabUtil.mapDealResults.get(signCard.getHdlFlag()));
            signCard.setStrExpDate(signCard.getExpiredDate() == null ? null : new SimpleDateFormat(fltTabUtil.DATE_FORMAT).format(signCard.getExpiredDate()));
            signCard.setStrAppDate(signCard.getApplyDatetime() == null ? null : new SimpleDateFormat(fltTabUtil.DATE_FORMAT).format(signCard.getApplyDatetime()));
        }
         
        return signCards;
    }
    
    

    private TicketStorageSignCard getInSignCardObj(HttpServletRequest request, TicketStorageSignCard signCard) {
         if(signCard == null){
            return signCard;
        }
        String operatorId = PageControlUtil.getOperatorFromSession(request);       
        signCard.setOperatorId(operatorId);
        
         String d_cardMainId = request.getParameter("d_cardMainId");      
         signCard.setCardMainId(d_cardMainId);
        
        String d_cardSubId = request.getParameter("d_cardSubId");      
        signCard.setCardSubId(d_cardSubId);
        
         String d_line_id = request.getParameter("d_line_id");      
        signCard.setLineId(d_line_id);
        
        String d_station_id = request.getParameter("d_station_id");              
        signCard.setStationId(d_station_id);
        
        String d_applyName = request.getParameter("d_applyName");       
        signCard.setApplyName(d_applyName);
        
        String d_applySex = request.getParameter("d_applySex");       
        signCard.setApplySex(d_applySex);
        
        String d_identityType = request.getParameter("d_identityType");      
        signCard.setIdentityType(d_identityType);
        
        String d_identityTd = request.getParameter("d_identityId");       
        signCard.setIdentityId(d_identityTd);
        
        String d_expiredDate = request.getParameter("d_strExpDate");      
        signCard.setStrExpDate(d_expiredDate);
        
        String d_telNo = request.getParameter("d_telNo");       
        signCard.setTelNo(d_telNo);
        
        String d_fax = request.getParameter("d_fax");       
        signCard.setFax(d_fax);
        
        String d_cardAppFlag = request.getParameter("d_cardAppFlag");       
        signCard.setCardAppFlag(d_cardAppFlag);
        
        String d_address = request.getParameter("d_address");       
        signCard.setAddress(d_address);
        
        String d_applyDate = request.getParameter("d_strAppDate");       
        signCard.setStrAppDate(d_applyDate);
        
        signCard.setReqNo(request.getParameter("d_reqNo") == null ? "" : request.getParameter("d_reqNo") );
        signCard.setWaterNo(Long.MIN_VALUE);
        
        signCard.setHdlFlag("0");
       
        
        return signCard;
    }
    
    
    private String[] getAttributeNames() {
        //仓库
        String[] attrNames = {IC_CARD_MAIN, IC_CARD_MAIN_SERIAL, IC_CARD_SUB, IC_CARD_MAIN_SUB,STORAGES,AREAS,STORAGE_AREAS,BILL_STATUES,AFC_CARD_MAIN, AFC_CARD_SUB, CARD_MAIN_SUB,IC_LINES, IC_STATIONS, IC_LINE_STATIONS,CARD_MONEYS,LINES, STATIONS,LINE_STATIONS};
        
        return attrNames;
    }
    
      @RequestMapping(value = "/ticketStorageImpSignCard")
    public ModelAndView ticketStorageImportNamed(HttpServletRequest request, HttpServletResponse response)  {
        ModelAndView mv = new ModelAndView("/jsp/basicinfo/ticketStorageImpSignCard.jsp");
      
        return mv;
    }
    
    @RequestMapping(value = "/ticketStorageImpSignCard1")
    public ModelAndView ticketStorageImportNamed1(HttpServletRequest request, HttpServletResponse response)  {
        ModelAndView mv = new ModelAndView("/jsp/basicinfo/ticketStorageImpSignCard.jsp");
        String command = request.getParameter("command");

        
           try {
               if (command != null) {
                   command = command.trim();
                   if (command.equals("stimp")) {
                       String d_applyDate = request.getParameter("d_applyDate");
//                       System.out.println("d_applyDate=>>" + d_applyDate);
                       String d_station_id = request.getParameter("d_station_id");
//                       System.out.println(d_station_id);
                       String line_id = "";
                       String station_id = "";
                       if(d_station_id!=null){
                           line_id = d_station_id.substring(0, 2);
                           station_id = d_station_id.substring(2, 4);
                       }
                       String operatorId = PageControlUtil.getOperatorFromSession(request);
                       MultipartHttpServletRequest multipartRequest;
                       multipartRequest = (MultipartHttpServletRequest) request;
                       CommonsMultipartFile file = (CommonsMultipartFile) multipartRequest
                               .getFile("makeFile");

                       String filePathIn = "";
//                       List<PubFlag> pathS = pubFlagMapper.getCodesByType(57);
                       ConfigKey key = new ConfigKey();
                       key.setType("1");
                       key.setTypeSub("11");
                       List<Config> configs = configMapper.selectConfigs(key);
                       if (configs != null && file != null) {
                           String realFileName = file.getOriginalFilename();
                           Config config = configs.get(0);
                           filePathIn = config.getConfigValue() + "\\" + realFileName;
                           file.transferTo(new File(filePathIn));
                           List<List<String>> xlsContent = this.readFileByType(".xls", new File(filePathIn), "");

                           mv = this.importData(mv,xlsContent, operatorId, line_id, station_id, d_applyDate);
                           

                       }
                   }
               }
           } catch (Exception e) {
               
               mv.addObject("msg",e.getMessage());
               e.printStackTrace();
           }

//        this.baseHandler(request, response, mv);
//        this.divideResultSet(request, mv, opResult);
//        this.SaveOperationResult(mv, opResult);
        return mv;
    }
    
    public static List<List<String>> readFileByType(String fileType, File file,
            String headTitle) throws Exception {

        List<List<String>> cells = new ArrayList<List<String>>(0);// 记录一个list
        if (".csv".equalsIgnoreCase(fileType)
                || ".txt".equalsIgnoreCase(fileType)) {
            BufferedReader br = null;
            InputStreamReader isr = null;
            try {
                String encoding = get_charset(file);
                isr = new InputStreamReader(new FileInputStream(file), encoding);
                br = new BufferedReader(isr);
                boolean hasHeadLine = false;
                String line = null;
                StringBuffer strBuffer = null;
                List<String> lineList = null;
                line = br.readLine();
                while (line != null && line.trim().length() > 0) {
                    strBuffer = new StringBuffer();
                    strBuffer.append(line);
                    lineList = analysisLine(strBuffer);
                    strBuffer.delete(0, strBuffer.length());

                    // 构建数据对象
                    if (headTitle != null && headTitle.length() > 0) {
                        if (lineList != null && lineList.size() >= 1) {
                            for (int j = 0; j < lineList.size(); j++) {
                                if (headTitle != null
                                        && headTitle.length() > 0
                                        && lineList.get(j)
                                        .trim().equals(headTitle)) {
                                    hasHeadLine = true;
                                }
                            }
                        }
                        if (hasHeadLine) {
                            line = br.readLine();// 从文件中继续读取一行数据
                            hasHeadLine = false;
                            continue;
                        }
                    }
                    cells.add(lineList);
                    line = br.readLine();// 从文件中继续读取一行数据
                }
            } catch (Throwable e) {
                e.printStackTrace();
                return null;
            } finally {
                try {
                    if (br != null) {
                        br.close();
                    }
                    if (isr != null) {
                        isr.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else if (".xls".equalsIgnoreCase(fileType)) {
            InputStream inp = null;
            try {
                inp = new FileInputStream(file);
                // 根据输入流创建Workbook对象
                Workbook wb = WorkbookFactory.create(inp);
                // get到Sheet对象
                Sheet sheet = wb.getSheetAt(0);
                // xls文件中科学计数法转换为正常数字
                java.text.DecimalFormat formatter = new java.text.DecimalFormat(
                        "########");
                String cellString = null;
                // 行循环
                int cols = 0;
                Cell cell = null;
                outLoop:
                for (Row row : sheet) {
                    List<String> lineList = new ArrayList<String>(0);
                    cols = row.getLastCellNum();
                    for (int i = 0; i < cols; i++) {
                        cell = row.getCell(i);
                        cellString = "";
                        // cell.getCellType是获得cell里面保存的值的type
                        // 如Cell.CELL_TYPE_STRING
                        if (cell != null) {
                            switch (cell.getCellType()) {
                                case Cell.CELL_TYPE_BOOLEAN:
                                    // 得到Boolean对象的方法
                                    cellString = cell.getBooleanCellValue() + "";
                                    break;
                                case Cell.CELL_TYPE_NUMERIC:
                                    // 先看是否是日期格式
                                    if (DateUtil.isCellDateFormatted(cell)) {
                                        // 读取日期格式
                                        cellString = cell.getDateCellValue() + "";
                                    } else {
                                        // 读取数字
                                        cellString = formatter.format(cell
                                                .getNumericCellValue());
                                    }
                                    break;
                                case Cell.CELL_TYPE_FORMULA:
                                    // 读取公式
                                    cellString = cell.getCellFormula();
                                    break;
                                case Cell.CELL_TYPE_STRING:
                                    // 读取String
                                    cellString = cell.getRichStringCellValue()
                                            .toString();
                                    break;
                            }
                        }
                        if (headTitle != null && headTitle.length() > 0
                                && cellString.trim().equals(headTitle)) {
                            continue outLoop;
                        }
                        lineList.add(cellString.trim());
                    }
                    cells.add(lineList);
                }
            } catch (Throwable e) {
                e.printStackTrace();
                return null;
            } finally {
                try {
                    if (inp != null) {
                        inp.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else if (".properties".equalsIgnoreCase(fileType)) {
            BufferedReader br = null;
            InputStreamReader isr = null;
            try {
                String encoding = get_charset(file);
                isr = new InputStreamReader(new FileInputStream(file), encoding);
                br = new BufferedReader(isr);
                boolean hasHeadLine = false;
                String line = null;
                StringBuffer strBuffer = null;
                List<String> lineList = null;
                line = br.readLine();
                while (line != null) {
                    strBuffer = new StringBuffer();
                    strBuffer.append(line);
                    lineList = analysisLine(strBuffer);
                    strBuffer.delete(0, strBuffer.length());

                    // 构建数据对象
                    if (headTitle != null && headTitle.length() > 0) {
                        if (lineList != null && lineList.size() >= 1) {
                            for (int j = 0; j < lineList.size(); j++) {
                                if (headTitle != null
                                        && headTitle.length() > 0
                                        && lineList.get(j)
                                        .trim().equals(headTitle)) {
                                    hasHeadLine = true;
                                }
                            }
                        }
                        if (hasHeadLine) {
                            line = br.readLine();// 从文件中继续读取一行数据
                            hasHeadLine = false;
                            continue;
                        }
                    }
                    cells.add(lineList);
                    line = br.readLine();// 从文件中继续读取一行数据
                }
            } catch (Throwable e) {
                e.printStackTrace();
                return null;
            } finally {
                try {
                    if (br != null) {
                        br.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return cells;
    }
    
    private ModelAndView importData(ModelAndView mv,List<List<String>> xlsContent, String operatorId, String line_id, String station_id, String d_applyDate) throws Exception {
        Vector<Object> devCodeV = getFromXlsContent(xlsContent);
        int i = 0;
        int k =0;
        String ids = "";
        TransactionStatus status = null;

        try {
            status = txMgr.getTransaction(this.def);
            for (Object obj : devCodeV) {
                TicketStorageStListSignCard ticketStorageQueryNamedVo = (TicketStorageStListSignCard) obj;
                ticketStorageQueryNamedVo.setLineId(line_id);
                ticketStorageQueryNamedVo.setStationId(station_id);
                ticketStorageQueryNamedVo.setStrAppDateTime(d_applyDate);
                ticketStorageQueryNamedVo.setOperatorId(operatorId);
                ticketStorageQueryNamedVo.setReqNo("0");
                ticketStorageQueryNamedVo.setWaterNo(Long.parseLong("0"));
                fillTempAttr(ticketStorageQueryNamedVo);
                int j = stListSignCardMapper.qryStListSignCard(ticketStorageQueryNamedVo);
                if(j == 0){
                    i += stListSignCardMapper.insertSelective(ticketStorageQueryNamedVo);
                }
                else{
                    k++;
                    ids = ids + "'" + ticketStorageQueryNamedVo.getIdentityId() + "',";
                }
                
                if(i % 400 == 0){
                    txMgr.commit(status);
                    status = txMgr.getTransaction(this.def);
                }
            }
            txMgr.commit(status);

        } catch (Exception e) {
            e.printStackTrace();

            if (txMgr != null) {
                txMgr.rollback(status);
            }
            throw e;
        }
        mv.addObject("msg","成功导入" + i + "条记录!" + "其中重号记录有" +  k + "条记录!" + ids);
        return mv;
    }
    
    public void fillTempAttr(TicketStorageStListSignCard ticketStorageQueryNamedVo) {
        if (ticketStorageQueryNamedVo.getStrExpDate().isEmpty() || ticketStorageQueryNamedVo.getStrExpDate().equals("0")) {
            ticketStorageQueryNamedVo.setStrExpDate("20400503");
        }
        ticketStorageQueryNamedVo.setDevTypeId("03");//BOM
        ticketStorageQueryNamedVo.setDeviceId("001");//001机器
        Date date = new Date();
        ticketStorageQueryNamedVo.setShiftId("01");//shift_id
        ticketStorageQueryNamedVo.setBalanceWaterNo("2014040101");
        ticketStorageQueryNamedVo.setFileName("TRX" + ticketStorageQueryNamedVo.getLineId() + ticketStorageQueryNamedVo.getStationId() + "." + new SimpleDateFormat("yyyyMMdd").format(date) + ".001");
        ticketStorageQueryNamedVo.setCheckFlag("0");
        ticketStorageQueryNamedVo.setHdlFlag("0");
        ticketStorageQueryNamedVo.setFax("0");
    }
    
    private Vector<Object> getFromXlsContent(List<List<String>> xlsContent) throws Exception {
//        List<TicketStorageQueryNamedVo> devCodeList = new ArrayList<TicketStorageQueryNamedVo>();
        //20160805 modify by mqf List 改为 Vector
        Vector<Object> devCodeV = new Vector<Object>();
        TicketStorageStListSignCard signCardVO;
        String returnStr = "";
//        先验证格式。
        for (int i = 0; i < xlsContent.size(); i++) {
            List<String> rowContent = xlsContent.get(i);
            if (i == 0) {
                continue;//第一行是标题
            }
            if (rowContent.size() >= 10 && !rowContent.get(1).isEmpty()) {
//                this.dealWrongLineData(rowContent, i);
                returnStr += findWrongLineInfo(rowContent, i);
            }
        }
//        再添加实体。
        for (int i = 0; i < xlsContent.size(); i++) {
            List<String> rowContent = xlsContent.get(i);
            if (i == 0) {
                continue;//第一行是标题
            }
            if (rowContent.size() >= 10 && !rowContent.get(1).isEmpty()) {
                signCardVO = new TicketStorageStListSignCard();
//                this.dealWrongLineData(rowContent, i);

                signCardVO.setApplyName(rowContent.get(0));
                signCardVO.setApplySex(rowContent.get(1).substring(0, 1));
                signCardVO.setCardMainId(rowContent.get(2).substring(0, 2));
                signCardVO.setCardSubId(rowContent.get(2).substring(2, 4));
                signCardVO.setCardAppFlag(rowContent.get(3).substring(0, 1));
                signCardVO.setIdentityType(rowContent.get(4).substring(0, 1));
                signCardVO.setIdentityId(rowContent.get(5).toUpperCase());//2016.08.04 modified by zhouyang
                signCardVO.setStrExpDate(rowContent.get(6));

                signCardVO.setTelNo(rowContent.get(7));
                signCardVO.setFax(rowContent.get(8));

                signCardVO.setAddress(rowContent.get(9));
//                signCardVO.setLineId(rowContent.get(10));
//                signCardVO.setStationId(rowContent.get(11));
//                devCodeList.add(signCardVO);
                //20160805 modify by mqf devCodeList 改为 devCodeV
                devCodeV.add(signCardVO);

            }
        }
        
        if (!returnStr.isEmpty()) {
            throw new Exception(returnStr);
        }
        return devCodeV;
    }
    
    private String findWrongLineInfo(List<String> rowContent, int lineInx) throws Exception {
        String returnStr = "";
//        1男 2女
        if (rowContent.get(1).length()!=2||
                !(rowContent.get(1).substring(0, 2).equals("1男") || rowContent.get(1).substring(0, 2).equals("2女"))) {
            returnStr += "第" + (lineInx + 1) + "行性别信息错误，请输入正确的性别信息（1男，2女）。<br/>";
        }
//        0201学生票 0203老人票
        if (rowContent.get(2).length()!=7||
                !(rowContent.get(2).substring(0, 7).equals("0201学生票") || rowContent.get(2).substring(0, 7).equals("0203老人票"))) {
            returnStr += ("第" + (lineInx + 1) + "行票卡类型错误，请输入正确的票卡类型（0201学生票，0203老人票）。<br/>");
        }
//        0普通票卡 1测试票卡
        if (rowContent.get(3).length()!=5||
                !(rowContent.get(3).substring(0, 5).equals("0普通票卡") || rowContent.get(3).substring(0, 5).equals("1测试票卡"))) {
            returnStr += ("第" + (lineInx + 1) + "行卡标识错误，请输入正确的卡标识（0普通票卡，1测试票卡）。<br/>");
        }
//        1身份证 2学生证 3军人证 4老人证 5员工证 9其他
        //20160413 modify by mqf 限制员工证记录不能导入
//        if (rowContent.get(4).length()<=3||
        if (rowContent.get(4).length()<=2||
                !(rowContent.get(4).substring(0, 3).equals("1身份") || rowContent.get(4).substring(0, 3).equals("2学生")
                || rowContent.get(4).substring(0, 3).equals("3军人") || rowContent.get(4).substring(0, 3).equals("4老人") 
//                || rowContent.get(4).substring(0, 3).equals("5员工") 
                || rowContent.get(4).substring(0, 3).equals("9其他"))) {
//            returnStr += ("第" + (lineInx + 1) + "行证件类型错误，请输入正确的证件类型（1身份证,2学生证,3军人证,4老人,5员工,9其他）。<br/>");
            returnStr += ("第" + (lineInx + 1) + "行证件类型错误，请输入正确的证件类型（1身份证,2学生证,3军人证,4老人证,9其他）。<br/>");
        }
        
//      
        if (StringUtil.isHaveChinese(rowContent.get(5)) || StringUtil.getDBLenth(rowContent.get(5)) > 18) {
            returnStr += ("第" + (lineInx + 1) + "行证件号码错误，证件号码中不能包含中文字符且不能超过18位。<br/>");
        }
//        日期格式是6位数字yyyyMMdd
        if(!"".equals(rowContent.get(6))){
            if(!StringUtil.checkDateFormatAndValite(rowContent.get(6), "yyyyMMdd")){
                returnStr += ("第" + (lineInx + 1) + "行证件有效期错误，请按格式正确填写，如：20120501。<br/>");
            }
        }
        if (StringUtil.getDBLenth(rowContent.get(7)) > 18) {
            returnStr += ("第" + (lineInx + 1) + "行电话号码错误，电话号码不能超过16位。<br/>");
        }
        if (StringUtil.getDBLenth(rowContent.get(9)) > 200) {
            returnStr += ("第" + (lineInx + 1) + "行地址信息超过200位（一个中文字符为2位）。<br/>");
        }
        return returnStr;
    }
    
     @RequestMapping("/TicketStorageSignCardApplyExportAll")
    public void exportExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	
        List results = this.getBufferElementsForCurClass(request,"com.goldsign.acc.app.basicinfo.entity.TicketStorageSignCard");
        List<Map<String,String>> queryResults = this.entityToMap(results);
        ExpUtil.renderExportExcelPath(response, ExpUtil.exportExcel(queryResults, request));
    }

    private List<Map<String, String>> entityToMap(List results) {
        List<Map<String,String>> list =  new ArrayList<>();
        int row = results.size();
        for(int n=0;n<row ;n++){
            TicketStorageSignCard vo = (TicketStorageSignCard)results.get(n);
            Map<String,String> map = new HashMap<>();
            map.put("waterNo", vo.getReqNo());
            map.put("applyName", vo.getApplyName());
            map.put("applySex", vo.getApplySex());
            map.put("identityType", vo.getIdentityType());
            map.put("identityId", vo.getIdentityId());
            map.put("strExpDate", vo.getStrExpDate());
            map.put("telNo", vo.getTelNo());
            map.put("fax", vo.getFax());
            map.put("address", vo.getAddress());
            map.put("strAppDate", vo.getStrAppDate());
            map.put("operatorId", vo.getOperatorId());
            map.put("lineIdText", vo.getLineIdText());
            map.put("stationIdText", vo.getStationIdText());
            map.put("cardMainIdText", vo.getCardMainIdText());
            map.put("cardSubIdText", vo.getCardSubIdText());
            map.put("cardAppFlag", vo.getCardAppFlag());
            map.put("hdlFlag", vo.getHdlFlag());
           
            list.add(map);
        }
        return list;
    }
    
}
