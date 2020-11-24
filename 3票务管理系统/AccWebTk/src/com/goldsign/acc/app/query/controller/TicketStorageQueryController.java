/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.goldsign.acc.app.query.controller;

import com.goldsign.acc.app.basicinfo.entity.TicketStorageStListSignCard;
import com.goldsign.acc.app.query.entity.TicketStorageIcStsAreaCard;
import com.goldsign.acc.app.basicinfo.entity.TicketStorageSignCard;
import com.goldsign.acc.app.move.entity.TicketStorageTicketMove;
import com.goldsign.acc.app.query.entity.TicketStorageUchkBill;
import com.goldsign.acc.frame.constant.CommandConstant;
import com.goldsign.acc.frame.constant.InOutConstant;
import com.goldsign.acc.frame.constant.PubFlagConstant;
import com.goldsign.acc.frame.controller.StorageOutInBaseController;
import com.goldsign.acc.frame.entity.PubFlag;
import com.goldsign.acc.frame.mapper.BillMapper;
import com.goldsign.acc.frame.util.LogUtil;
import com.goldsign.acc.frame.util.PageControlUtil;

import com.goldsign.acc.frame.util.StringUtil;
import com.goldsign.acc.frame.vo.OperationResult;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
//import com.goldsign.acc.app.tkqry.mapper.IcOutBillMapper;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import com.goldsign.acc.app.move.entity.TicketStorageIcOutBillDetail;
import com.goldsign.acc.app.move.entity.TicketStorageInStoreBillDetail;
import com.goldsign.acc.app.basicinfo.mapper.TicketStorageSignCardMapper;
import com.goldsign.acc.app.basicinfo.mapper.TicketStorageStListSignCardMapper;
import com.goldsign.acc.frame.util.FltTabUtil;
import com.goldsign.acc.app.move.mapper.TicketStorageIcOutBillDetailMapper;
import com.goldsign.acc.app.move.mapper.TicketStorageInStoreBillDetailMapper;
import com.goldsign.acc.app.move.mapper.TicketStorageTicketMoveMapper;
import com.goldsign.acc.app.query.mapper.TicketStorageIcStsAreaCardMapper;
import com.goldsign.acc.app.query.mapper.TicketStorageTkQryMapper;
import com.goldsign.acc.app.query.mapper.TicketStorageUchkBillMapper;
import com.goldsign.acc.frame.util.ExpUtil;

/**
 * 库存查询
 *
 * @author 刘粤湘
 * @version V1.0
 * @date 2017-8-3 8:46:10
 * @desc
 */
@Controller
public class TicketStorageQueryController extends StorageOutInBaseController {
    @Autowired
    TicketStorageTkQryMapper tkQryMapper;
    @Autowired
    TicketStorageIcStsAreaCardMapper areaCardMapper;
    @Autowired
    TicketStorageUchkBillMapper uchkBillMapper;
    @Autowired
    TicketStorageSignCardMapper signCardMapper;
    private FltTabUtil fltTabUtil;
    private int ticketTotals = 0;
    private int allRecords = 0;
    /**
     * 票卡查询
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/ticketStorageQueryTicket")
    public ModelAndView service(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView("/jsp/query/ticketStorageQueryTicket.jsp");
        String command = request.getParameter("command");
        String operType = request.getParameter("operType");
        OperationResult opResult = new OperationResult();
        try {
            if (command != null) {
                command = command.trim();
                //票卡查询
                if (command.equals(CommandConstant.COMMAND_QUERY)) {
                    Map<String, Object> map = new HashMap();
                    map.put("P_LOGICAL_ID", request.getParameter("q_logicalNo"));
                    tkQryMapper.tkqry(map);

                    opResult.setReturnResultSet((List) map.get("INFO_CUR"));
                }

            }


        } catch (Exception e) {
            e.printStackTrace();
            opResult.addMessage(e.getMessage());
        }


        this.baseHandler(request, response, mv);
        this.divideResultSet(request, mv, opResult);
        this.SaveOperationResult(mv, opResult);
        new PageControlUtil().putBuffer(request, opResult.getReturnResultSet());

        return mv;
    }

    /**
     * 库存查询
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/ticketStorageQueryStorage")
    public ModelAndView serviceStorageQry(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView("/jsp/query/ticketStorageQueryStorage.jsp");
        String command = request.getParameter("command");
//        String _ticketTotals =request.getParameter("ticketTotals");
//        String _allRecords =request.getParameter("allRecords");
//        if(_ticketTotals!=null && _allRecords!=null){
//            ticketTotals += Integer.parseInt(_ticketTotals);
//            allRecords += Integer.parseInt(_allRecords);
//        }

        OperationResult opResult = new OperationResult();
        try {
            if (command != null) {
                command = command.trim();

                //库存查询
                if (command.equals(CommandConstant.COMMAND_QUERY)) {
                    ticketTotals = 0;
                    allRecords = 0;
                    fltTabUtil = new FltTabUtil();
                    fltTabUtil.initFilterNeed(this.pubFlagMapper);

                    Map<String, Object> map = new HashMap();
                    opResult = this.queryStorage(request, mv);
                    allRecords += opResult.getReturnResultSet().size();
//                       mv.addObject("ticketTotals", ticketTotals);
//                       mv.addObject("allRecords", allRecords);
                }

            } else {
                ticketTotals = 0;
                allRecords = 0;
            }


        } catch (Exception e) {
            e.printStackTrace();
            opResult.addMessage(e.getMessage());
        }

        String[] attrNames = this.getAttributeNames();
        this.setPageOptions(attrNames, mv, request, response);
        this.baseHandler(request, response, mv);
        this.divideResultSet(request, mv, opResult);
        this.SaveOperationResult(mv, opResult);

//            System.out.println("sssssssssssssssssssssssssssss");
        mv.addObject("ticketTotals", ticketTotals);
        mv.addObject("allRecords", allRecords);

        return mv;
    }

    /**
     * \
     * 未审核单据查询
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/ticketStorageQueryUncheckedBill")
    public ModelAndView unCheckBill(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView("/jsp/query/ticketStorageQueryUncheckedBill.jsp");
        String command = request.getParameter("command");

        OperationResult opResult = new OperationResult();
        try {
            fltTabUtil = new FltTabUtil();
            fltTabUtil.initFilterNeed(this.pubFlagMapper);
            Map<String, Object> map = new HashMap();
            uchkBillMapper.unchkBillQry(map);
            List<TicketStorageUchkBill> unchkBills = (List) map.get("INFO_CUR");
            for (TicketStorageUchkBill unchkBill : unchkBills) {
                unchkBill.setStorageId(fltTabUtil.mapStorages.get(unchkBill.getStorageId()));
                unchkBill.setRecordFlag(fltTabUtil.mapBillStatus.get(unchkBill.getRecordFlag()));
                unchkBill.setStrBillDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(unchkBill.getBillDate()));
            }
            opResult.setReturnResultSet(unchkBills);
            opResult.addMessage("成功查询" + unchkBills.size() + "条记录");

        } catch (Exception e) {
            e.printStackTrace();
            opResult.addMessage(e.getMessage());
        }


        this.baseHandler(request, response, mv);
        this.divideResultSet(request, mv, opResult);
        this.SaveOperationResult(mv, opResult);
        new PageControlUtil().putBuffer(request, opResult.getReturnResultSet());


        return mv;
    }


    private String[] getAttributeNames() {
        //仓库
        String[] attrNames = {IC_CARD_MAIN, IC_CARD_MAIN_SERIAL, IC_CARD_SUB, IC_CARD_MAIN_SUB, STORAGES, AREAS, STORAGE_AREAS, BILL_STATUES, AFC_CARD_MAIN, AFC_CARD_SUB, CARD_MAIN_SUB, IC_LINES, IC_STATIONS, IC_LINE_STATIONS, CARD_MONEYS};
        return attrNames;
    }


    private OperationResult queryStorage(HttpServletRequest request, ModelAndView mv) throws Exception {

        OperationResult or = new OperationResult();
        LogUtil logUtil = new LogUtil();
        List<TicketStorageIcStsAreaCard> rsAreaCard = new ArrayList();
        List<Map> rsAreaBox = new ArrayList();
        List<TicketStorageIcStsAreaCard> rsAreaBoxs = new ArrayList();
        try {
            Map queryCondition = this.getQryStorageContidion(request);
            //按票区查询
            if (this.isNeedArea(queryCondition.get("q_area_id") == null ? null : (String) queryCondition.get("q_area_id"),
                    queryCondition.get("q_cardMainCode") == null ? null : (String) queryCondition.get("q_cardMainCode"),
                    queryCondition.get("q_boxId") == null ? null : (String) queryCondition.get("q_boxId"),
                    queryCondition.get("q_beginTime") == null ? null : (String) queryCondition.get("q_beginTime"),
                    queryCondition.get("q_endTime") == null ? null : (String) queryCondition.get("q_endTime"))) {
                rsAreaCard = this.areaCardMapper.qryCardArea(queryCondition);
                rsAreaCard = this.fltStorageTab(rsAreaCard);
                or.setReturnResultSet(rsAreaCard);
                or.addMessage("成功查询" + rsAreaCard.size() + "条记录");
            }
            //按盒查询
            if (this.isNeedBox(queryCondition.get("q_area_id") == null ? null : (String) queryCondition.get("q_area_id"),
                    queryCondition.get("q_cardMainCode") == null ? null : (String) queryCondition.get("q_cardMainCode"))) {
                rsAreaBox = this.areaCardMapper.qryCardBox(queryCondition);
                rsAreaBoxs = this.fltAreaBox(rsAreaBox);
                rsAreaBoxs = this.fltStorageTab(rsAreaBoxs);
                or.getReturnResultSet().addAll(rsAreaBoxs);
                or.addMessage("成功查询" + or.getReturnResultSet().size() + rsAreaCard.size() + "条记录");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, this.operationLogMapper);
        }
        return or;
    }

    private boolean isNeedArea(String areaId, String icMainType, String boxId, String beginTime, String endTime) {
        if ((boxId != null && boxId.length() != 0)
                || (beginTime != null && beginTime.length() != 0)
                || (endTime != null && endTime.length() != 0)) {
            return false;
        }
        if (areaId == null || areaId.length() == 0) {
            return true;
        }
        if ((areaId.equals(InOutConstant.AREA_VALUE))
                || (areaId.equals(InOutConstant.AREA_ENCODE) && icMainType != null && !icMainType.equals(InOutConstant.IC_MAIN_TYPE_SJT))) {
            return false;
        }

        return true;

    }

    private boolean isNeedBox(String areaId, String icMainType) {
        if (areaId == null || areaId.length() == 0) {
            return true;
        }
        //AREA_VALUE="03";赋值区
        if (areaId.equals(InOutConstant.AREA_VALUE)) {
            return true;
        }
//        System.out.println(areaId);
//        System.out.println(icMainType);
        //AREA_ENCODE="02";编码区
        //IC_MAIN_TYPE_SJT="12";单乘票
        //&& icMainType != null && !icMainType.equals(InOutConstant.IC_MAIN_TYPE_SJT
        //modify by zhongziqi 20180609
//        if (areaId.equals(InOutConstant.AREA_ENCODE)) {
        if (areaId.equals(InOutConstant.AREA_ENCODE)&&!InOutConstant.IC_MAIN_TYPE_SJT.equals(icMainType)) {
            return true;
        }

        return false;

    }

    private Map getQryStorageContidion(HttpServletRequest request) {
        Map map = new HashMap();
        String q_cardMainCode = request.getParameter("q_cardMainCode");
        if (q_cardMainCode != null && q_cardMainCode.trim().equals("")) {
            q_cardMainCode = null;
        }
        map.put("q_cardMainCode", q_cardMainCode);
//          System.out.println("票卡主类型：" + q_cardMainCode);
        String q_cardSubCode = request.getParameter("q_cardSubCode");
        if (q_cardSubCode != null && q_cardSubCode.trim().equals("")) {
            q_cardSubCode = null;
        }
        map.put("q_cardSubCode", q_cardSubCode);
//         System.out.println("票卡子类型：" + q_cardSubCode);
        String q_cardMoney = request.getParameter("q_cardMoney");
        if (q_cardMoney != null && q_cardMoney.trim().equals("")) {
            q_cardMoney = null;
        }
        map.put("q_cardMoney", q_cardMoney);
        String q_validDate = request.getParameter("q_validDate");
        if (q_validDate != null && q_validDate.trim().equals("")) {
            q_validDate = null;
        }
        map.put("q_validDate", q_validDate);

        String q_storage = request.getParameter("q_storage");
        if (q_storage != null && q_storage.trim().equals("")) {
            q_storage = null;
        }
        map.put("q_storage", q_storage);
        //如果为空需选择用户所有仓库
        if (q_storage == null) {
            String operatorId = PageControlUtil.getOperatorFromSession(request);
            List<PubFlag> pubFlags = this.getStorages(operatorId);
            List storages = new ArrayList();
            for (PubFlag pubFlag : pubFlags) {
                storages.add(pubFlag.getCode());
            }
            map.put("q_storages", storages);
        }


        String q_area_id = request.getParameter("q_area_id");
        if (q_area_id != null && q_area_id.trim().equals("")) {
            q_area_id = null;
        }
        map.put("q_area_id", q_area_id);
        String q_boxId = request.getParameter("q_boxId");
        if (q_boxId != null && q_boxId.trim().equals("")) {
            q_boxId = null;
        }
        map.put("q_boxId", q_boxId);

        String q_beginTime = request.getParameter("q_beginTime");
        if (q_beginTime != null && q_beginTime.trim().equals("")) {
            q_beginTime = null;
        }
        map.put("q_beginTime", q_beginTime);

        String q_endTime = request.getParameter("q_endTime");
        if (q_endTime != null && q_endTime.trim().equals("")) {
            q_endTime = null;
        }
        if (q_endTime != null) {
            q_endTime = q_endTime + " 23:59:59";
        }
        map.put("q_endTime", q_endTime);


        return map;
    }

    private List<TicketStorageIcStsAreaCard> fltAreaBox(List<Map> rsAreaBoxs) {
        List<TicketStorageIcStsAreaCard> areaCards = new ArrayList();
        for (Map rsAreaBox : rsAreaBoxs) {
            TicketStorageIcStsAreaCard areaCard = new TicketStorageIcStsAreaCard();
            areaCard.setIcMainType(rsAreaBox.get("IC_MAIN_TYPE") == null ? null : (String) rsAreaBox.get("IC_MAIN_TYPE"));
            areaCard.setIcSubType(rsAreaBox.get("IC_SUB_TYPE") == null ? null : (String) rsAreaBox.get("IC_SUB_TYPE"));
            areaCard.setCardMoney((BigDecimal) rsAreaBox.get("CARD_MONEY"));
            areaCard.setCardNum((BigDecimal) rsAreaBox.get("CARD_NUM"));
            areaCard.setStorageId(rsAreaBox.get("STORAGE_ID") == null ? null : (String) rsAreaBox.get("STORAGE_ID"));
            areaCard.setAreaId(rsAreaBox.get("AREA_ID") == null ? null : (String) rsAreaBox.get("AREA_ID"));
            areaCard.setPlace(rsAreaBox.get("CHEST_ID") + "-" + (String) rsAreaBox.get("STOREY_ID") + "-" + (String) rsAreaBox.get("BASE_ID"));
            areaCard.setBoxId(rsAreaBox.get("BOX_ID") == null ? null : (String) rsAreaBox.get("BOX_ID"));
            areaCard.setLineId(rsAreaBox.get("LINE_ID") == null ? null : (String) rsAreaBox.get("LINE_ID"));
            areaCard.setStationId(rsAreaBox.get("STATION_ID") == null ? null : (String) rsAreaBox.get("STATION_ID"));
            areaCard.setExitLineId(rsAreaBox.get("EXIT_LINE_ID") == null ? null : (String) rsAreaBox.get("EXIT_LINE_ID"));
            areaCard.setExitStationId(rsAreaBox.get("EXIT_STATION_ID") == null ? null : (String) rsAreaBox.get("EXIT_STATION_ID"));
            areaCard.setModel(rsAreaBox.get("MODEL") == null ? null : (String) rsAreaBox.get("MODEL"));
            String validDate = "";
            if (rsAreaBox.get("VALID_DATE") == null) {
                validDate = null;
            } else {
                validDate = new SimpleDateFormat("yyyy-MM-dd ").format(rsAreaBox.get("VALID_DATE"));
                if (validDate.equals(InOutConstant.DEFAULT_VALUE_VALID_DATE)) {
                    validDate = null;
                }
            }

            areaCard.setValidDate(validDate);
//            areaCard.setProductDate((Date)rsAreaBox.get("PRODUCT_DATE"));
            if (rsAreaBox.get("PRODUCE_DATE") != null) {
                areaCard.setStrProductDate(new SimpleDateFormat("yyyy-MM-dd ").format((Date) rsAreaBox.get("PRODUCE_DATE")));
//                System.out.println(areaCard.getStrProductDate());
            }

            areaCards.add(areaCard);
        }
        return areaCards;
    }

    private List<TicketStorageIcStsAreaCard> fltStorageTab(List<TicketStorageIcStsAreaCard> rsAreaBoxs) {
        List<TicketStorageIcStsAreaCard> areaCards = new ArrayList();
        for (TicketStorageIcStsAreaCard areaCard : rsAreaBoxs) {
            String icSubType = areaCard.getIcMainType() + areaCard.getIcSubType();
            areaCard.setIcMainType(fltTabUtil.mapMainTypes.get(areaCard.getIcMainType()));
            areaCard.setIcSubType(fltTabUtil.mapMainSubs.get(icSubType));
            areaCard.setStorageId(fltTabUtil.mapStorages.get(areaCard.getStorageId()));
            areaCard.setAreaId(fltTabUtil.mapAreas.get(areaCard.getAreaId()));
            //modify by zhongzq 修复映射车站中文异常
            areaCard.setStationId(fltTabUtil.mapStationsIc.get(areaCard.getLineId()+areaCard.getStationId()));
            areaCard.setLineId(fltTabUtil.mapLinesIc.get(areaCard.getLineId()));
//            areaCard.setStationId(fltTabUtil.mapStationsIc.get(areaCard.getStationId()));
            //modify by zhongzq 修复映射车站中文异常
            areaCard.setExitStationId(fltTabUtil.mapStationsIc.get(areaCard.getExitLineId()+areaCard.getExitStationId()));
            areaCard.setExitLineId(fltTabUtil.mapLinesIc.get(areaCard.getExitLineId()));
//            areaCard.setExitStationId(fltTabUtil.mapStationsIc.get(areaCard.getExitStationId()));

            areaCard.setModel(fltTabUtil.mapLimitModels.get(areaCard.getModel()));
//            System.out.println(areaCard.getValidDate()+"=====-->>");
            if (areaCard.getValidDate() != null) {
                if (areaCard.getValidDate().substring(0, 10).equals(InOutConstant.DEFAULT_VALUE_VALID_DATE)) {
                    areaCard.setValidDate("");
                } else {
                    areaCard.setValidDate(areaCard.getValidDate().substring(0, 10));
                }


            }
            if (areaCard.getBoxId() == null) {
                areaCard.setValidDate(null);
            }
            areaCards.add(areaCard);

            ticketTotals = ticketTotals + areaCard.getCardNum().intValue();
        }
        return areaCards;
    }


    public static String get_charset(File file) {
        String charset = "GBK";
        byte[] first3Bytes = new byte[3];
        try {
            boolean checked = false;
            BufferedInputStream bis = new BufferedInputStream(
                    new FileInputStream(file));
            bis.mark(0);
            int read = bis.read(first3Bytes, 0, 3);
            if (read == -1) {
                return charset;
            }
            if (first3Bytes[0] == (byte) 0xFF && first3Bytes[1] == (byte) 0xFE) {
                charset = "UTF-16LE";
                checked = true;
            } else if (first3Bytes[0] == (byte) 0xFE
                    && first3Bytes[1] == (byte) 0xFF) {
                charset = "UTF-16BE";
                checked = true;
            } else if (first3Bytes[0] == (byte) 0xEF
                    && first3Bytes[1] == (byte) 0xBB
                    && first3Bytes[2] == (byte) 0xBF) {
                charset = "UTF-8";
                checked = true;
            }
            bis.reset();
            if (!checked) {
                // int len = 0;
                int loc = 0;

                while ((read = bis.read()) != -1) {
                    loc++;
                    if (read >= 0xF0) {
                        break;
                    }
                    if (0x80 <= read && read <= 0xBF) // 单独出现BF以下的，也算是GBK
                    {
                        break;
                    }
                    if (0xC0 <= read && read <= 0xDF) {
                        read = bis.read();
                        if (0x80 <= read && read <= 0xBF) // 双字节 (0xC0 - 0xDF)
                        // (0x80
                        // - 0xBF),也可能在GB编码内
                        {
                            continue;
                        } else {
                            break;
                        }
                    } else if (0xE0 <= read && read <= 0xEF) {// 也有可能出错，但是几率较小
                        read = bis.read();
                        if (0x80 <= read && read <= 0xBF) {
                            read = bis.read();
                            if (0x80 <= read && read <= 0xBF) {
                                charset = "UTF-8";
                                break;
                            } else {
                                break;
                            }
                        } else {
                            break;
                        }
                    }
                }
                // System.out.println( loc + " " + Integer.toHexString( read )
                // );
            }

            bis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return charset;
    }

    public static List<String> analysisLine(StringBuffer line) {
        StringBuffer str = new StringBuffer();
        line.append(",");
        Pattern pCells = Pattern.compile("(\"[^\"]*(\"{2})*[^\"]*\")*[^,]*,");
        Matcher mCells = pCells.matcher(line);
        List<String> cells = new ArrayList<String>(0);// 每行记录一个list
        // 读取每个单元格
        while (mCells.find()) {
            str.append(mCells.group().replaceAll(
                    "(?sm)\"?([^\"]*(\"{2})*[^\"]*)\"?.*,", "$1").replaceAll(
                    "(?sm)(\"(\"))", "$2"));
            cells.add(str.toString().trim());
            str.delete(0, str.length());
        }
        return cells;
    }

    /**
     * 票卡查询导出全部
     *
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("/ticketStorageQueryTicketExportAll")
    public void exportExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {
        List results = this.getBufferElements(request);
        String expAllFields = request.getParameter("expAllFields");
        List<Map<String, Object>> queryResults
                = ExpUtil.entityToMap(expAllFields, results);
        ExpUtil.renderExportExcelPath(response,
                ExpUtil.exportExcel(queryResults, request));
    }

    /**
     * 库存查询导出全部
     *
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("/ticketStorageQueryStorageExportAll")
    public void exportExcel2(HttpServletRequest request, HttpServletResponse response) throws Exception {
        List results = this.getBufferElements(request);
        String expAllFields = request.getParameter("expAllFields");
        List<Map<String, Object>> queryResults
                = ExpUtil.entityToMap(expAllFields, results);
        ExpUtil.renderExportExcelPath(response,
                ExpUtil.exportExcel(queryResults, request));
    }

    /**
     * 库存查询导出全部
     *
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("/ticketStorageQueryUncheckedBillExportAll")
    public void exportExcel3(HttpServletRequest request, HttpServletResponse response) throws Exception {
        List results = this.getBufferElements(request);
        String expAllFields = request.getParameter("expAllFields");
        List<Map<String, Object>> queryResults
                = ExpUtil.entityToMap(expAllFields, results);
        ExpUtil.renderExportExcelPath(response,
                ExpUtil.exportExcel(queryResults, request));
    }
}
