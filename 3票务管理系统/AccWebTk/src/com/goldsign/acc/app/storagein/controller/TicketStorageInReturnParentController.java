
package com.goldsign.acc.app.storagein.controller;

import com.goldsign.acc.app.storagein.entity.TicketStorageInBill;
import com.goldsign.acc.app.storagein.entity.TicketStorageInNew;
import com.goldsign.acc.app.storagein.entity.TicketStorageInNewDetail;
import com.goldsign.acc.app.storagein.entity.TicketStorageInReturn;
import com.goldsign.acc.app.storagein.entity.TicketStorageInReturnDetail;
import com.goldsign.acc.app.storagein.mapper.TicketStorageInNewDetailMapper;
import com.goldsign.acc.app.storagein.mapper.TicketStorageInNewMapper;
import com.goldsign.acc.app.storagein.mapper.TicketStorageInReturnDetailMapper;
import com.goldsign.acc.app.storagein.mapper.TicketStorageInReturnMapper;
import com.goldsign.acc.frame.constant.CommandConstant;
import com.goldsign.acc.frame.entity.PubFlag;
import com.goldsign.acc.frame.mapper.PubFlagMapper;
import com.goldsign.acc.frame.util.FormUtil;
import com.goldsign.acc.frame.util.PageControlUtil;
import com.goldsign.acc.frame.util.PubUtil;
import com.goldsign.acc.frame.vo.OperationResult;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeSet;
import java.util.Vector;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.TransactionStatus;

/**
 *
 * @author zhouyang
 * 回收入库共用类
 * 20170815
 */
public class TicketStorageInReturnParentController extends TicketStorageInParentController{
     @Autowired
    private TicketStorageInReturnMapper tsirMapper;
    
     @Autowired
    private TicketStorageInReturnDetailMapper tsirdMapper;
     
     @Autowired
    private PubFlagMapper pfMapper;
     
    public String getWhereParamsByInType(TicketStorageInNew tsin) {
        String whereParams = "";
//        String relatedBillNo1 = vo.getRelatedBillNo1();
        String billNo = tsin.getBill_no();
        String recordFlag = tsin.getRecord_flag();
        
        whereParams = "where substr(a.bill_no,1,2)='XR'";
        

        if (billNo != null && !billNo.equals("")) {
            whereParams += " and a.bill_no like '%" + billNo + "%'";
        }

        if (recordFlag != null && !recordFlag.equals("")) {
            whereParams += " and a.record_flag='" + recordFlag + "'";
        }
        if (tsin.getBegin_time() != null && !tsin.getBegin_time().equals("")) {
            String btime = this.getDateTimeBeginValue(tsin.getBegin_time());
            whereParams += " and  a.bill_date>=to_date('" + btime + "','yyyy-MM-dd hh24:mi:ss')";
        }

        if (tsin.getEnd_time() != null && !tsin.getEnd_time().equals("")) {
            String etime = this.getDateTimeEndValue(tsin.getEnd_time());
            whereParams += " and  a.bill_date<=to_date('" + etime + "','yyyy-MM-dd hh24:mi:ss')";
        }
        return whereParams;
    }

    public List<String> getWhereDetails(TicketStorageInNew tsin, String operatorID) throws Exception {
        String inType = tsin.getInType();
        List<String> returnWhereDetail = new ArrayList<String>();
        String whereDetail = "";
        String notExists = " and not exists(select d.bill_no from w_acc_tk.w_ic_in_store_detail d where a.bill_no=d.bill_no) ";
        String[] fieldsDetail = {"trim(c.ic_main_type)", "trim(c.ic_sub_type)", "c.reason_id", "c.storage_id"};
        String[] operatorsDetail;
        boolean notExistsFlag = false;
        if (tsin.getStorage_id() == null || tsin.getStorage_id().trim().isEmpty()) {
            tsin.setStorage_id(this.getStoreId(operatorID));
            notExistsFlag = true;
            operatorsDetail = new String[]{"=", "=", "=", "in"};
        } else {
            operatorsDetail = new String[]{"=", "=", "=", "="};
        }
        String[] valuesDetail = {tsin.getIc_main_type(), tsin.getIc_sub_type(), tsin.getReason_id(), tsin.getStorage_id()};

        String inWhereDetail = this.formCondition(fieldsDetail, valuesDetail, operatorsDetail);

        if (inWhereDetail != null && !inWhereDetail.isEmpty()) {
            whereDetail = " and exists (select c.bill_no from w_acc_tk.w_ic_in_store_detail c"
                        + " where a.bill_no=c.bill_no and " + inWhereDetail.substring(6) + ") ";
            returnWhereDetail.add(whereDetail);
//                取明细表要从多个表的明细数据里面取数据
            try{
                List<String> resultSet;
                resultSet = tsirMapper.getStorageInDetailByBillNo(tsin.getBill_no());
                if (resultSet ==null || resultSet.isEmpty()) {
                    List<TicketStorageInReturn> result;
                    String betweenStr = "";
                    result=tsirMapper.getHistTable();
                    for(TicketStorageInReturn histTable :result){
                        if (histTable.getBegin_recd() != null && !histTable.getBegin_recd().trim().isEmpty()
                                && histTable.getEnd_recd() != null && !histTable.getEnd_recd().trim().isEmpty()) {
                            betweenStr = "and substr(a.bill_no,1,3)!='" + inType + "T' "
                                    + "and substr(a.bill_no,3,10) "
                                    + "between " + histTable.getBegin_recd()
                                    + " and " + histTable.getEnd_recd() + " ";
                        }
                        if (notExistsFlag) {
                            notExists += " and not exists(select d.bill_no from w_acc_tk." + histTable.getHis_table()
                                    + " d where a.bill_no=d.bill_no) ";
                        }
                            returnWhereDetail.add(
                                    " and a.record_flag='0' "
                                    + betweenStr
                                    + "and exists "
                                    + "(select c.bill_no from w_acc_tk." + histTable.getHis_table() + " c"
                                    + " where a.bill_no=c.Bill_No and " + inWhereDetail.substring(6) + ") ");
                    }
                }
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        } else {
            returnWhereDetail.add("");
        }
        if (notExistsFlag && !inWhereDetail.isEmpty()) {
            returnWhereDetail.add(notExists);
        }
        
        return returnWhereDetail;
    }

    public static String getQueryString(String inType, String whereParams, String whereDetail) {
        String strSql;
        if (inType.equals("GH")) {
            strSql = "select a.bill_no ,a.lend_bill_no,a.in_bill_no ,a.bill_date,a.return_man,a.receive_man ,a.unit_id,a.remark ,"
                    + "a.record_flag,a.verify_person ,a.verify_date,b.record_flag record_in "
                    + "from ic_in_return_bill a , ic_in_store_bill b " + whereParams + whereDetail + " order by a.bill_no desc ";
        } else if (inType.equals("SR")) {
            strSql = "select a.bill_no,a.bill_date,a.form_maker,a.hand_man ,a.administer ,"
                    + "a.accounter,a.related_bill_no,a.record_flag,a.verify_date,a.verify_person,b.out_in_diff,a.remark "
                    + "from ic_in_store_bill a, ic_in_out_diff_produce b  "
                    + whereParams + whereDetail + " order by a.bill_no desc ";
        } else if (inType.equals("CR") || inType.equals("QR")) {
            strSql = "select distinct a.bill_no   ,a.bill_date  ,a.form_maker ,a.hand_man ,a.administer ,"
                    + "a.accounter ,a.related_bill_no,a.record_flag ,a.verify_date ,a.verify_person,b.out_in_diff,a.remark "
                    + "from ic_in_store_bill a , ic_in_out_diff b " + whereParams + whereDetail + " order by a.bill_no desc ";
        } else {
            strSql = "select bill_no,bill_date  ,form_maker ,hand_man ,administer ,"
                    + "accounter ,related_bill_no,record_flag ,verify_date ,verify_person,remark "
                    + "from ic_in_store_bill a " + whereParams + whereDetail + " order by a.bill_no desc ";
        }
        return strSql;
    }
    
    public String getDateTimeBeginValue(String value) {
        if (value == null || value.length() == 0) {
            return "";
        }
        return "" + value + " 00:00:00";
    }

    public String getDateTimeEndValue(String value) {
        if (value == null || value.length() == 0) {
            return "";
        }
        //return "to_date('"value + " 23:59:59'"+",'yyyy-mm-dd hh24:mi:ss')";
        return "" + value + " 23:59:59";
    }
    
    /**
     * 查询全部的仓库ID,多个ID时以 ; 分隔
     *功能：根据当前用户的权限获取其权限内的仓库类型。
     * @param operatorId
     * @return
     * @throws Exception
     */
    public String getStoreId(String operatorId) throws Exception {
        String returnStr = "";
        Set<String> storeSet = getUserStoreSet(operatorId);

//        "9999"代表全部
        if (storeSet.contains("9999")) {
            return returnStr;
        } else {
            for (String str : storeSet) {
                returnStr += str + ";";
            }
        }
        if (returnStr.trim().length() > 1) {
            returnStr = returnStr.substring(0, returnStr.length() - 1) ;
        }
        return returnStr;
    }
    
    public  Set getUserStoreSet(String operatorId) throws Exception {
        Set vectorSet = new TreeSet();
        List<TicketStorageInReturn> resultSet;
        try {
            resultSet = tsirMapper.getUserStorege(operatorId);
            if (resultSet != null && !resultSet.isEmpty()) {
                String sysStorageId;
                for(TicketStorageInReturn tsin :resultSet){
                    sysStorageId= tsin.getSys_storage_id();
                    if (sysStorageId != null && !(sysStorageId.trim().isEmpty())) {
//                        "0000"代表 无 
                        if (!sysStorageId.equals("0000")) {
                            //                        "9999"代表 全部
                            if (sysStorageId.equals("9999")) {
                                vectorSet.add(sysStorageId);
                                break;
                            } else {
                                vectorSet.add(sysStorageId);
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return vectorSet;
    }
    
    /**
     * <p> description:根据条件生成查询的where条件 </p>
     *
     * @param fields 字段名称
     * @param values 字段可能取值

     * @param operators 操作符

     * @return
     */
    public String formCondition(String[] fields, String[] values,
            String[] operators) {
        int fLen = fields.length;
        int vLen = values.length;
        int oLen = operators.length;
        if (fLen != vLen || fLen != oLen) {
            return "";
        }
        String field;
        String value;
        String oper;
        String condition = "";
        String comp = "";

        for (int i = 0; i < fLen; i++) {
            field = fields[i];
            value = values[i];
            oper = operators[i];
            if (!isValideValue(value)) {
                continue;
            }
//            sValue = getValue(value);
            if (value == null || value.length() == 0) {
                continue;
            }
            comp = field + " " + oper + " " + value;

            condition += " " + comp + " and";

        }
        if (condition.length() != 0) {
            condition = " where "
                    + condition.substring(0, condition.length() - 3);
        }
        return condition;
    }
    
     private boolean isValideValue(Object value) {
        if (value == null) {
            return false;
        }
        if (value instanceof java.lang.String) {
            if (((String) value).trim().length() == 0) {
                return false;
            }
        }

        return true;
    }
     
    protected List<TicketStorageInReturn> getReqAttributeForNew(HttpServletRequest request) {
        TicketStorageInReturn po = new TicketStorageInReturn();
        String selectIds = request.getParameter("allSelectedIDs");
        List<TicketStorageInReturn> selectedItems = this.getNewSelectIDs(selectIds, request);
        return selectedItems;
    }
     
    protected Vector<TicketStorageInReturn> getNewSelectIDs(String selectedIds, HttpServletRequest request) {
        StringTokenizer st = new StringTokenizer(selectedIds, ";");
        String strIds = null;
        Vector<TicketStorageInReturn> sds = new Vector();
        TicketStorageInReturn sd;
        String operatorId = PageControlUtil.getOperatorFromSession(request);
        while (st.hasMoreTokens()) {
            strIds = st.nextToken();
            sd = this.getPlan(strIds, "#");
            sd.setOperator(operatorId);
            sds.add(sd);
        }
        return sds;
    }
    
    protected TicketStorageInReturn getPlan(String strIDs, String delim) {
        StringTokenizer st = new StringTokenizer(strIDs, delim);
        String tmp = null;
        int i = 0;
        TicketStorageInReturn sd = new TicketStorageInReturn();
        while (st.hasMoreTokens()) {
            i++;
            tmp = st.nextToken();
            if (i == 1) {
                sd.setBill_no(tmp);
                continue;
            }
        }
        return sd;
    }
    
    protected int deleteByTrans(HttpServletRequest request, List<TicketStorageInReturn> pos) throws Exception {
        TransactionStatus status = null;
        int n = 0;
        try {
            //排除明细有记录的情况
            for (TicketStorageInReturn po : pos) {
                //如果明细中没有记录，继续执行删除操作；如果明细中有记录，则终止删除操作，返回-2。
                if(this.getDetailByBillNo(po, tsirMapper)){
                    return -2;
                }
            }
            status = txMgr.getTransaction(this.def);
            //执行删除操作
            for (TicketStorageInReturn po : pos) {
                n += tsirMapper.deleteTicketStorageInReturn(po);
            }
            txMgr.commit(status);
        } catch (Exception e) {
            PubUtil.handExceptionForTran(e, txMgr, status);
        }
        return n;
    }
    
    /**
     * 判断入库明细中是否存在记录
     * @param ticketStorageInReturn
     * @param tsinMapper
     * @return 
     * @throws java.lang.Exception
     */
    public boolean getDetailByBillNo(TicketStorageInReturn ticketStorageInReturn,TicketStorageInReturnMapper tsinMapper) throws Exception{
        boolean bl = false;
        List<TicketStorageInReturn> result;
        String billNo = ticketStorageInReturn.getBill_no();
        try {
            if(billNo!=null && !billNo.equals("")){
                result = tsinMapper.getDetailByBillNo(billNo);
                if(result!=null && !result.isEmpty()){
                    return true;
                }
            }else{
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bl;
    }
    
    protected TicketStorageInReturnDetail getQueryCondition(HttpServletRequest request) {
        TicketStorageInReturnDetail qCon = new TicketStorageInReturnDetail();
        qCon.setBill_no(FormUtil.getParameter(request, "queryCondition"));
        qCon.setRecord_flag(FormUtil.getParameter(request, "billRecordFlag"));
        return qCon;
    }
    
    protected List<TicketStorageInReturnDetail> getTicketStorageInReturnDetails(TicketStorageInReturnDetail tsind){
        List<TicketStorageInReturnDetail> details = new ArrayList<>();
        String histable ;
        
         try {
             //如果是已审核的有效单据，则有可能存在于分表中，而不在明细表中，所以需要判断：如果不在明细表中，就查询分表。
            if(tsind.getRecord_flag()!=null && tsind.getRecord_flag().equals("0")){
                details=tsirdMapper.getTicketStorageInReturnDetailByBillNo(tsind.getBill_no());
                //如果明细表中有记录，则直接返回结果集。
                if(details != null && !details.isEmpty()){
                    return details;
                }else{
                    histable= tsirdMapper.getHisTable(tsind.getBill_no());
                    //如果存在对应历史表，则获取历史表中的数据
                    if(histable!=null && !histable.isEmpty()){
                        Map<String,String> queryInDetailMap = new HashMap<String,String>();
                        queryInDetailMap.put("tableName", histable);
                        queryInDetailMap.put("billNo", tsind.getBill_no());
                        details = tsirdMapper.getTicketStorageInReturnDetailHisByBillNo(queryInDetailMap);
                        return details;
                    }
                }
            }else{//如果不是有效单据，直接查询明细表
                details=tsirdMapper.getTicketStorageInReturnDetailByBillNo(tsind.getBill_no());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return details;
    }
    
    protected int addTicketStorageInReturnDetail(HttpServletRequest request, TicketStorageInReturnDetail vo) throws Exception {
        int n = 0;
        
        String report_date = vo.getReport_date();
        String reason_id = null;
        String line_id_reclaim = vo.getLine_id_reclaim();
        reason_id = vo.getReason_id();
        String start_logical_id = vo.getStart_logical_id();
        String end_logical_id  = vo.getEnd_logical_id();
        String ic_sub_type = vo.getIc_sub_type();
        
        if(!reason_id.equals("10") && !reason_id.equals("12")){
            if(ic_sub_type==null || ic_sub_type.equals("")){
                return -6;
            }
        }
        if(reason_id.equals("10")|| reason_id.equals("12")){
            if(report_date==null || report_date.equals("")){
                return -7;
            }
        }
        if(report_date!=null && !report_date.equals("")){
            vo.setDate(report_date.substring(0, 4) + report_date.substring(5, 7)+ report_date.substring(8, 10));
        }else{
            vo.setDate("");
        }
        //只选择仓库，不选择回收线路，回收站点时需要限制为仓库所属的线路
        String lineAuthSql = this.getLineAuthSql(vo);
        vo.setLineAuthSql(lineAuthSql);
        TransactionStatus status = null;
        //判断是否存在与之记录不同的票库类型
        if(this.getTicStoInNewDetByStorage(vo)){
            return -2;
        }
        //入库原因是“车站上交”
        if(reason_id.equals("10")){
            try {
                
                List<String> list= tsirdMapper.getUploadRecordOfStationHandIn(vo);
                if (list==null || list.isEmpty()) {
                    return -3;  //返回值-3，表示没有车站当日的上传记录
                }
                List<Integer> nums =tsirdMapper.getAreaOfStationHandIn(vo);
                for(int num : nums){
                    if(num<0){
                        return -4; //返回值-4，入库数量超过最大剩余数量
                    }
                }
                status = txMgr.getTransaction(this.def);
                //更新表w_ic_inf_station_handin中的流水号
                int m=tsirdMapper.modifyStationDetailNo(vo);
                //将w_ic_inf_station_handin的中对应的数据插入到w_ic_in_store_detail中
                n=tsirdMapper.addByStationHandIn(vo);
                //更新表w_acc_tk.w_ic_inf_station_handin中in_flag状态为1
                int f=tsirdMapper.modifyStationInFlag(vo);
                if(m==n && f==n){//如果更新的记录与增加的相等，则提交事务，否则回滚事务
                    txMgr.commit(status);
                }else{
                    txMgr.rollback(status);
                    return -8;
                }
                
            } catch (Exception e) {
                PubUtil.handExceptionForTran(e, txMgr, status);
            }
        }
        else if(reason_id.equals("12")){//入库原因是“收益组移交”
            try {
                List<String> list= tsirdMapper.getUploadRecordOfIncomeDepHandIn(vo);
                if (list==null || list.isEmpty()) {
                    return -3;   //返回值-3，表示没有车站/收益组当日的上传记录
                }
                //字段新增2个字段，通过in_flag字段判断即可，不需要校验是否已存在库中的情况
//                List<TicketStorageInReturnDetail> result = tsirdMapper.getDetailTableRecordOfIncomeDepHandIn(vo);
//                if(line_id_reclaim!=null && !line_id_reclaim.equals("")){
//                    if(result!=null && !result.isEmpty()){
//                        return -5;  //返回值-5，表示收益组当日的上传记录已经存在库中
//                    }
//                }else{
//                    if(result==null || result.isEmpty()){
//                        return -5;  //返回值-5，表示收益组当日的上传记录已经存在库中
//                    }
//                }
                List<Integer> nums =tsirdMapper.getAreaOfIncomeDepHandIn(vo);
                for(int num : nums){
                    if(num<0){
                        return -4; //返回值-4，入库数量超过最大剩余数量
                    }
                }
                status = txMgr.getTransaction(this.def);
                //先给表w_acc_tk.w_ic_inf_incomedep_handin中的in_store_detail_no字段增加流水号
                int m=tsirdMapper.modifyIncomedepDetailNo(vo);
                //将数据插入到w_ic_in_store_detail中
                n=tsirdMapper.addByIncomeDepHandIn(vo);
                //更新表w_acc_tk.w_ic_inf_incomedep_handin中in_flag状态为1
                int f=tsirdMapper.modifyIncomedepInFlag(vo);
                if(m==n && f==n){//如果更新的记录与增加的相等，则提交事务，否则回滚事务
                    txMgr.commit(status);
                }else{
                    txMgr.rollback(status);
                    return -8;//可能存在组织移交记录中票卡类型错误的情况
                }
            } catch (Exception e) {
                PubUtil.handExceptionForTran(e, txMgr, status);
            }
        }
        else{//除了“车站上交”与“收益组移交”的其它入库原因
            if (start_logical_id != null && !start_logical_id.equals("") && start_logical_id.length() != 20) {
                    vo.setStart_logical_id(("00000000000000000000" + start_logical_id).substring(("00000000000000000000" + start_logical_id).length() - 20, ("00000000000000000000" + start_logical_id).length()));
            }
            if (end_logical_id != null && !end_logical_id.equals("") && end_logical_id.length() != 20) {
                vo.setEnd_logical_id(("00000000000000000000" + end_logical_id).substring(("00000000000000000000" + end_logical_id).length() - 20, ("00000000000000000000" + end_logical_id).length()));
            }
            try{
                //判断入库数量是否超过最大剩余数量
                List<Integer> nums =tsirdMapper.getAreaOfOthers(vo);
                for(int num : nums){
                    if(num<0){
                        return -4; //返回值-4，入库数量超过最大剩余数量
                    }
                }
                status = txMgr.getTransaction(this.def);
                n=tsirdMapper.addByOthers(vo);
                txMgr.commit(status);
            } catch (Exception e) {
                PubUtil.handExceptionForTran(e, txMgr, status);
            }
        }
        return n;
    }
    
    // 只选择仓库，不选择回收线路，回收站点时需要限制为仓库所属的线路
    private String getLineAuthSql(TicketStorageInReturnDetail vo) throws Exception {
        
        String lineAuthSql = "";
        List<PubFlag> icLines = tsirdMapper.getTableFlagsByCondition(vo);
        if ((null != icLines) && !icLines.isEmpty()) {
            for (PubFlag pubFlagVo: icLines) {
                String lineCodeStr = "";
                if (pubFlagVo.getCode().length() == 1) {
                    lineCodeStr = "0" + pubFlagVo.getCode();
                } else {
                    lineCodeStr = pubFlagVo.getCode();
                }
                lineAuthSql += lineCodeStr + "','";

            }
            if (lineAuthSql.trim().length() > 1) {
                lineAuthSql = "('" + lineAuthSql.substring(0, lineAuthSql.length() - 2) + ")";
            }
        } else {
            //为空则没有权限
            lineAuthSql = " ( ' ')";
        }
        return lineAuthSql;
    }
    
    //如果明细表中存在不同票库的记录，则返回true,否则返回false
    protected boolean getTicStoInNewDetByStorage(TicketStorageInReturnDetail tsind){
        List<TicketStorageInReturnDetail> result;
        try{
            result=tsirdMapper.getDetailByStorage(tsind);
        if(result!=null && !result.isEmpty()){
            return true;
        }
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return false;
    }
    
    protected int deleteReturnDetailByTrans(HttpServletRequest request,List<TicketStorageInReturnDetail> vos) throws Exception {
        TransactionStatus status = null;
        int n = 0;
        String reasonId =null;
        try {
//            //如果没有选择必须的选项，则不能执行删除操作
//            if(!this.selectNecessaryRecord(vos)){
//                return -2;
//            }
            status = txMgr.getTransaction(this.def);
            
            for (TicketStorageInReturnDetail vo : vos) {
                reasonId = tsirdMapper.getInReasonByWaterNo(vo);
                if("10".equals(reasonId)){//如果是车站上交，则需要更改w_ic_inf_station_handin为未入库状态0,并删除流水号
                    tsirdMapper.UpdateINFlagForStationDelete(vo);
                }
                if("12".equals(reasonId)){//如果是收益组移交，则需要更改w_ic_inf_incomedep_handin为未入库状态0,并删除流水号
                    tsirdMapper.UpdateINFlagForIncomedepDelete(vo);
                }
                n += tsirdMapper.deleteTicketStorageInReturnDetail(vo);
            }
            txMgr.commit(status);
        } catch (Exception e) {
            PubUtil.handExceptionForTran(e, txMgr, status);
        }
        return n;
    }
    
    //判断是否选择必需的删除项（如果是收益组移交，存在几条报表日期相同的记录时，需要将报表日期相同的所有记录进行删除，否则不能删除）
    protected boolean selectNecessaryRecord(List<TicketStorageInReturnDetail> vos){
        boolean bl = true;
        List<String> waterNoLists = new ArrayList();//当前所选的全部记录的流水号
        List<String> waterNoOfInComes = new ArrayList();
        List<String> waterNoOfInComeList = new ArrayList();
        String reasonId = null;
        for(TicketStorageInReturnDetail vo : vos){
            reasonId = tsirdMapper.getInReasonByWaterNo(vo);
            if("12".equals(reasonId)){
                waterNoLists.add(vo.getWater_no());
                vo.setReason_id(reasonId);
                vo.setReport_date(tsirdMapper.getReporDateByWaterNo(vo).substring(0,10));
                waterNoOfInComes = tsirdMapper.getWaterNoByInReasonRepordate(vo);//根据入库原因与报表日期获取流水号
                waterNoOfInComeList.addAll(waterNoOfInComes);
            }
        }
        for(String waterNo : waterNoOfInComes){
            if(!waterNoLists.contains(waterNo)){//如果所选记录不包含明细表中所有与之相同报表日期的记录，则返回false（针对收益组移交）
                return false;
            }
        }
        return bl;
    }
    
    protected List<TicketStorageInReturnDetail> getReqAttributeForReturnDetailDelete(HttpServletRequest request) {
        String selectIds = request.getParameter("allSelectedIDs");
        List<TicketStorageInReturnDetail> selectedItems = this.getDetailSelectIDs(selectIds, request);
        return selectedItems;
    }
    
    protected List<TicketStorageInReturnDetail> getDetailSelectIDs(String selectedIds, HttpServletRequest request) {
        StringTokenizer st = new StringTokenizer(selectedIds, ";");
        String strIds = null;
        List<TicketStorageInReturnDetail> sds = new ArrayList();
        TicketStorageInReturnDetail sd;
        while (st.hasMoreTokens()) {
            strIds = st.nextToken();
            sd = this.getDetailDeleteId(strIds, "#");
            sds.add(sd);
        }
        return sds;
    }
    protected TicketStorageInReturnDetail getDetailDeleteId(String strIDs, String delim) {
        StringTokenizer st = new StringTokenizer(strIDs, delim);
        String tmp = null;
        int i = 0;
        TicketStorageInReturnDetail sd = new TicketStorageInReturnDetail();
        while (st.hasMoreTokens()) {
            i++;
            tmp = st.nextToken();
            if (i == 1) {
                sd.setWater_no(tmp);
                continue;
            }
        }
        return sd;
    }
    
    //修改新票入库单明细记录
    protected int modifyTicketStorageInReturnDetail(HttpServletRequest request, TicketStorageInReturnDetail vo) throws Exception {
        TransactionStatus status = null;
        int n = 0;
        try {
            //入库原因为“车站上交”或“收益组移交”时，不能修改，只能增加与删除
            if(this.isHandInRecord(vo)){
                return -2;
            }
            //如果明细表中存在不同票库的记录，则不能进行修改操作
            if(this.getTicStoInNewDetByStorage(vo) && !this.onlyOneContent(vo)){
                return -3;
            }//如果入库数据大于最大剩余数据，则不能修改
            else if(!this.haveEnoughArea(vo)){
                return -4;
            }
            else{
                status = txMgr.getTransaction(this.def);
                n = tsirdMapper.modifyTicketStorageInReturnDetail(vo);
                txMgr.commit(status);
            }
        } catch (Exception e) {
            PubUtil.handExceptionForTran(e, txMgr, status);
        }
        return n;
    }
    
    //判断修改的记录的入库原因是否为“车站上交”或“收益组移交”，如果是则返回true,否则返回false
    protected boolean isHandInRecord(TicketStorageInReturnDetail vo){
        boolean bl = false;
        try {
            //获取当前修改记录的入库原因
            String reasonId =tsirdMapper.getInReasonByWaterNo(vo);
            if("10".equals(reasonId) || "12".equals(reasonId)){
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return true;
        }
        return bl;
    }
    
    //判断入库数量是否有足够空间，若有则返回true，否则返回false
    protected boolean haveEnoughArea(TicketStorageInReturnDetail vo) throws Exception{
        boolean bl = true;
        
        try {
            List<Integer> nums =tsirdMapper.getAreaOfOthers(vo);
            for(int num : nums){
                if(num<0){
                    return false; 
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bl;
    }
    
    //判断明细是否只有一条记录
    protected boolean onlyOneContent(TicketStorageInReturnDetail tsinds){
        boolean bl = false;
        List<TicketStorageInReturnDetail> result;
        String billNo =tsinds.getBill_no();
        try {
            result = tsirdMapper.getTicketStorageInReturnDetailByBillNo(billNo);
            if(result!=null && result.size()==1){
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bl;
    }
    
    protected HashMap<String, Object> auditNewByTrans(HttpServletRequest request, TicketStorageInReturn po) throws Exception {
        TransactionStatus status = null;
        HashMap<String, Object> parmMap = new HashMap();
        try {
            status = txMgr.getTransaction(this.def);
            this.setMapParamsForNew(parmMap, po);
            tsirMapper.audit(parmMap);
            int result = (Integer) parmMap.get("p_result");
            if(result ==1){
                txMgr.commit(status);
            }else{
                txMgr.rollback(status);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            PubUtil.handExceptionForTran(e, txMgr, status);
        } finally {
            return parmMap;
        }
    }
    
    protected void setMapParamsForNew(HashMap<String, Object> parmMap, TicketStorageInReturn po) {
        parmMap.put("p_bill_no", po.getBill_no());
        parmMap.put("p_operator_id", po.getOperator());
    }
    
    protected void handleResult(HashMap<String, Object> resultReturn) throws Exception {
        if (resultReturn == null || resultReturn.isEmpty()) {
            throw new Exception("执行存储过程没有返回结果！");
        }
        int result = (Integer) resultReturn.get("p_result");
        String errmsg = (String) resultReturn.get("p_errmsg");
        if (result == 1) {
            return;
        }
        throw new Exception(errmsg);
    }
    
     protected TicketStorageInBill getQueryConditionForOpPlan(HttpServletRequest request, OperationResult opResult,String inType) {
        TicketStorageInBill qCon = new TicketStorageInBill();
        HashMap vQueryControlDefaultValues = null;
        String queryControlDefaultValues = request.getParameter("queryControlDefaultValues");
        String command = request.getParameter("command");
        qCon.setIn_type(inType);
        if (FormUtil.isAddOrModifyMode(request)) {
            //操作处于添加或修改模式
            if (!command.equals(CommandConstant.COMMAND_DELETE)) {
                qCon.setBill_no(opResult.getAddPrimaryKey());
            }else{
                vQueryControlDefaultValues = FormUtil.getQueryControlDefaultValues(queryControlDefaultValues);
                if (!vQueryControlDefaultValues.isEmpty()) {
                    qCon.setBill_no(FormUtil.getQueryControlDefaultValue(vQueryControlDefaultValues, "q_billNo"));
                    String begin_date = FormUtil.getQueryControlDefaultValue(vQueryControlDefaultValues, "q_beginTime");
                    String end_date = FormUtil.getQueryControlDefaultValue(vQueryControlDefaultValues, "q_endTime");
                    if(begin_date!= null && !begin_date.equals(""))
                        qCon.setBill_date_begin(begin_date + " 00:00:00");
                    if(end_date!= null && !end_date.equals(""))
                        qCon.setBill_date_end(end_date + " 23:59:59");
                    qCon.setForm_maker(FormUtil.getQueryControlDefaultValue(vQueryControlDefaultValues, "q_operator"));
                    qCon.setIc_main_type(FormUtil.getQueryControlDefaultValue(vQueryControlDefaultValues, "q_cardMainCode"));
                    qCon.setIc_sub_type(FormUtil.getQueryControlDefaultValue(vQueryControlDefaultValues, "q_cardSubCode"));
                    qCon.setStorage_id(FormUtil.getQueryControlDefaultValue(vQueryControlDefaultValues, "q_storage"));
                    qCon.setRecord_flag(FormUtil.getQueryControlDefaultValue(vQueryControlDefaultValues, "q_recordFlag"));
                    qCon.setRelated_bill_no(FormUtil.getQueryControlDefaultValue(vQueryControlDefaultValues, "q_relatedBillno"));
                }
            }
            
        } else {
            //操作处于非添加模式
            vQueryControlDefaultValues = FormUtil.getQueryControlDefaultValues(queryControlDefaultValues);
            if (!vQueryControlDefaultValues.isEmpty()) {
                qCon.setBill_no(FormUtil.getQueryControlDefaultValue(vQueryControlDefaultValues, "q_billNo"));
                String begin_date = FormUtil.getQueryControlDefaultValue(vQueryControlDefaultValues, "q_beginTime");
                String end_date = FormUtil.getQueryControlDefaultValue(vQueryControlDefaultValues, "q_endTime");
                if(begin_date!= null && !begin_date.equals(""))
                    qCon.setBill_date_begin(begin_date + " 00:00:00");
                if(end_date!= null && !end_date.equals(""))
                    qCon.setBill_date_end(end_date + " 23:59:59");
                qCon.setForm_maker(FormUtil.getQueryControlDefaultValue(vQueryControlDefaultValues, "q_operator"));
                qCon.setIc_main_type(FormUtil.getQueryControlDefaultValue(vQueryControlDefaultValues, "q_cardMainCode"));
                qCon.setIc_sub_type(FormUtil.getQueryControlDefaultValue(vQueryControlDefaultValues, "q_cardSubCode"));
                qCon.setStorage_id(FormUtil.getQueryControlDefaultValue(vQueryControlDefaultValues, "q_storage"));
                qCon.setRecord_flag(FormUtil.getQueryControlDefaultValue(vQueryControlDefaultValues, "q_recordFlag"));
                qCon.setRelated_bill_no(FormUtil.getQueryControlDefaultValue(vQueryControlDefaultValues, "q_relatedBillno"));
            }
        }
        return qCon;
    }
     
    
}
