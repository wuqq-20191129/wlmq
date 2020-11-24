/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.storagein.controller;

import com.goldsign.acc.app.storagein.entity.TicketStorageInBill;
import com.goldsign.acc.app.storagein.entity.TicketStorageInNew;
import com.goldsign.acc.app.storagein.entity.TicketStorageInNewDetail;
import com.goldsign.acc.app.storagein.mapper.TicketStorageInNewDetailMapper;
import com.goldsign.acc.app.storagein.mapper.TicketStorageInNewMapper;
import com.goldsign.acc.frame.constant.CommandConstant;
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
 * 新票入库与回收入库共用类
 */
public class TicketStorageInNewParentController extends TicketStorageInParentController{
     @Autowired
    private TicketStorageInNewMapper tsinMapper;
    
     @Autowired
    private TicketStorageInNewDetailMapper tsindMapper;
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
                resultSet = tsinMapper.getStorageInDetailByBillNo(tsin.getBill_no());
                if (resultSet ==null || resultSet.isEmpty()) {
                    List<TicketStorageInNew> result;
                    String betweenStr = "";
                    result=tsinMapper.getHistTable();
                    for(TicketStorageInNew histTable :result){
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
        List<TicketStorageInNew> resultSet;
        try {
            resultSet = tsinMapper.getUserStorege(operatorId);
            if (resultSet != null && !resultSet.isEmpty()) {
                String sysStorageId;
                for(TicketStorageInNew tsin :resultSet){
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
     
    protected List<TicketStorageInNew> getReqAttributeForNew(HttpServletRequest request) {
        TicketStorageInNew po = new TicketStorageInNew();
        String selectIds = request.getParameter("allSelectedIDs");
        List<TicketStorageInNew> selectedItems = this.getNewSelectIDs(selectIds, request);
        return selectedItems;
    }
     
    protected Vector<TicketStorageInNew> getNewSelectIDs(String selectedIds, HttpServletRequest request) {
        StringTokenizer st = new StringTokenizer(selectedIds, ";");
        String strIds = null;
        Vector<TicketStorageInNew> sds = new Vector();
        TicketStorageInNew sd;
        String operatorId = PageControlUtil.getOperatorFromSession(request);
        while (st.hasMoreTokens()) {
            strIds = st.nextToken();
            sd = this.getPlan(strIds, "#");
            sd.setOperator(operatorId);
            sds.add(sd);
        }
        return sds;
    }
    
    protected TicketStorageInNew getPlan(String strIDs, String delim) {
        StringTokenizer st = new StringTokenizer(strIDs, delim);
        String tmp = null;
        int i = 0;
        TicketStorageInNew sd = new TicketStorageInNew();
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
    
    protected int deleteNewByTrans(HttpServletRequest request, List<TicketStorageInNew> pos) throws Exception {
        TransactionStatus status = null;
        int n = 0;
        try {
            //排除明细有记录的情况
            for (TicketStorageInNew po : pos) {
                //如果明细中没有记录，继续执行删除操作；如果明细中有记录，则终止删除操作，返回-2。
                if(this.getDetailByBillNo(po, tsinMapper)){
                    return -2;
                }
            }
            status = txMgr.getTransaction(this.def);
            //执行删除操作
            for (TicketStorageInNew po : pos) {
                n += tsinMapper.deleteTicketStorageInNew(po);
            }
            txMgr.commit(status);
        } catch (Exception e) {
            PubUtil.handExceptionForTran(e, txMgr, status);
        }
        return n;
    }
    
    /**
     * 判断入库明细中是否存在记录
     */
    public boolean getDetailByBillNo(TicketStorageInNew ticketStorageInNew,TicketStorageInNewMapper tsinMapper) throws Exception{
        boolean bl = false;
        List<TicketStorageInNew> result;
        String billNo = ticketStorageInNew.getBill_no();
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
    
    protected TicketStorageInNewDetail getQueryConditionNewDetail(HttpServletRequest request) {
        TicketStorageInNewDetail qCon = new TicketStorageInNewDetail();
        qCon.setBill_no(FormUtil.getParameter(request, "queryCondition"));
        qCon.setRecord_flag(FormUtil.getParameter(request, "billRecordFlag"));
        return qCon;
    }
    
    protected List<TicketStorageInNewDetail> getTicketStorageInNewDetails(TicketStorageInNewDetail tsind){
        List<TicketStorageInNewDetail> details = new ArrayList<>();
        String histable ;
        
         try {
             //如果是已审核的有效单据，则有可能存在于分表中，而不在明细表中，所以需要判断：如果不在明细表中，就查询分表。
            if(tsind.getRecord_flag()!=null && tsind.getRecord_flag().equals("0")){
                details=tsindMapper.getTicketStorageInNewDetailByBillNo(tsind.getBill_no());
                //如果明细表中有记录，则直接返回结果集。
                if(details != null && !details.isEmpty()){
                    return details;
                }else{
                    histable= tsindMapper.getHisTable(tsind.getBill_no());
                    //如果存在对应历史表，则获取历史表中的数据
                    if(histable!=null && !histable.isEmpty()){
                        Map<String,String> queryInDetailMap = new HashMap<String,String>();
                        queryInDetailMap.put("tableName", histable);
                        queryInDetailMap.put("billNo", tsind.getBill_no());
                        details = tsindMapper.getTicketStorageInNewHisByBillNo(queryInDetailMap);
                        return details;
                    }
                }
            }else{//如果不是有效单据，直接查询明细表
                details=tsindMapper.getTicketStorageInNewDetailByBillNo(tsind.getBill_no());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return details;
    }
    
    protected int addTicketStorageInNewDetail(HttpServletRequest request, TicketStorageInNewDetail tsinds) throws Exception {
        TransactionStatus status = null;
        int n = 0;
        try {
                //如果明细表中存在不同票库的记录，则不能进行添加操作
            if(this.getTicStoInNewDetByStorage(tsinds)){
                return -2;
            }//如果入库数据大于最大剩余数据，则不能增加
            else if(!this.haveEnoughArea(tsinds)){
                return -3;
            }
            else{
                status = txMgr.getTransaction(this.def);
                n = tsindMapper.addTicketStorageInNewDetail(tsinds);
                txMgr.commit(status);
            }
        } catch (Exception e) {
            PubUtil.handExceptionForTran(e, txMgr, status);
        }
        return n;
    }
    //如果明细表中存在不同票库的记录，则返回true,否则返回false
    protected boolean getTicStoInNewDetByStorage(TicketStorageInNewDetail tsind){
        List<TicketStorageInNewDetail> result;
        
        result=tsindMapper.getDetailByStorage(tsind);
        if(result!=null && !result.isEmpty()){
            return true;
        }
        
        return false;
    }
    
    protected int deleteNewDetailByTrans(HttpServletRequest request,List<TicketStorageInNewDetail> pos) throws Exception {
        TransactionStatus status = null;
        int n = 0;
        try {
            status = txMgr.getTransaction(this.def);
            for (TicketStorageInNewDetail po : pos) {
                    n += tsindMapper.deleteTicketStorageInNewDetail(po);
            }
            txMgr.commit(status);
        } catch (Exception e) {
            PubUtil.handExceptionForTran(e, txMgr, status);
        }
        return n;
    }
    
    protected List<TicketStorageInNewDetail> getReqAttributeForNewDetailDelete(HttpServletRequest request) {
        TicketStorageInNewDetail po = new TicketStorageInNewDetail();
        String selectIds = request.getParameter("allSelectedIDs");
        List<TicketStorageInNewDetail> selectedItems = this.getNewDetailSelectIDs(selectIds, request);
        return selectedItems;
    }
    
    protected List<TicketStorageInNewDetail> getNewDetailSelectIDs(String selectedIds, HttpServletRequest request) {
        StringTokenizer st = new StringTokenizer(selectedIds, ";");
        String strIds = null;
        List<TicketStorageInNewDetail> sds = new ArrayList();
        TicketStorageInNewDetail sd;
        String operatorId = PageControlUtil.getOperatorFromSession(request);
        while (st.hasMoreTokens()) {
            strIds = st.nextToken();
            sd = this.getNewDetail(strIds, "#");
            sds.add(sd);
        }
        return sds;
    }
    protected TicketStorageInNewDetail getNewDetail(String strIDs, String delim) {
        StringTokenizer st = new StringTokenizer(strIDs, delim);
        String tmp = null;
        int i = 0;
        TicketStorageInNewDetail sd = new TicketStorageInNewDetail();
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
    protected int modifyTicketStorageInNewDetail(HttpServletRequest request, TicketStorageInNewDetail tsinds) throws Exception {
        TransactionStatus status = null;
        int n = 0;
        try {
            //如果明细表中存在不同票库的记录，则不能进行修改操作
            if(this.getTicStoInNewDetByStorage(tsinds) && !this.onlyOneContent(tsinds)){
                return -2;
            }//如果入库数据大于最大剩余数据，则不能修改
            else if(!this.haveEnoughArea(tsinds)){
                return -3;
            }
            else{
                status = txMgr.getTransaction(this.def);
                n = tsindMapper.modifyTicketStorageInNewDetail(tsinds);
                txMgr.commit(status);
            }
        } catch (Exception e) {
            PubUtil.handExceptionForTran(e, txMgr, status);
        }
            
        return n;
    }
    
    //判断入库数量是否小于最大剩余数量
    protected boolean haveEnoughArea(TicketStorageInNewDetail tsinds){
        boolean bl = false;
        int maxNum;
        int allNum;
        int inNum;
        inNum = Integer.parseInt(tsinds.getIn_num());
        
            allNum = inNum + tsindMapper.getAllNumByBillNo(tsinds);
            maxNum=tsindMapper.getMaxNumOfArea(tsinds);
            if(allNum<=maxNum){
                return true;
            }
        return bl;
    }
    
    protected boolean onlyOneContent(TicketStorageInNewDetail tsinds){
        boolean bl = false;
        List<TicketStorageInNewDetail> result;
        String billNo =tsinds.getBill_no();
        try {
            result = tsindMapper.getTicketStorageInNewDetailByBillNo(billNo);
            if(result!=null && result.size()==1){
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bl;
    }
    
    protected HashMap<String, Object> auditNewByTrans(HttpServletRequest request, TicketStorageInNew po) throws Exception {
        TransactionStatus status = null;
        HashMap<String, Object> parmMap = new HashMap();
        try {
            status = txMgr.getTransaction(this.def);
            this.setMapParamsForNew(parmMap, po);
            tsinMapper.auditNew(parmMap);
            txMgr.commit(status);
        } catch (Exception e) {
            e.printStackTrace();
            PubUtil.handExceptionForTran(e, txMgr, status);
        } finally {
            return parmMap;
        }
    }
    
    protected void setMapParamsForNew(HashMap<String, Object> parmMap, TicketStorageInNew po) {
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
        if (FormUtil.isAddOrModifyMode(request) && !command.equals(CommandConstant.COMMAND_DELETE)) {
            //操作处于添加或修改模式
            qCon.setBill_no(opResult.getAddPrimaryKey());
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
