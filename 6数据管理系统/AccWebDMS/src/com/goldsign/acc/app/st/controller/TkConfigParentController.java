/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.st.controller;

import com.goldsign.acc.app.st.entity.TkConfig;
import com.goldsign.acc.app.st.mapper.TkConfigMapper;
import com.goldsign.acc.frame.constant.CommandConstant;
import com.goldsign.acc.frame.controller.DMSBaseController;
import com.goldsign.acc.frame.util.FormUtil;
import com.goldsign.acc.frame.util.PubUtil;
import com.goldsign.acc.frame.vo.OperationResult;
import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import org.springframework.transaction.TransactionStatus;

/**
 *
 * @author limj
 */
public class TkConfigParentController extends DMSBaseController{
   protected TkConfig getQueryConditionIn(HttpServletRequest request){
       TkConfig tk = new TkConfig();
       tk.setOriginTableName(FormUtil.getParameter(request, "q_origin_table_name"));
       return tk;
   }
   
   protected TkConfig getReAttributePlan (HttpServletRequest request){
       TkConfig tk = new TkConfig();
       tk.setOriginTableName(FormUtil.getParameter(request, "d_origin_table_name"));
       tk.setKeepDays(FormUtil.getParameterIntVal(request, "d_keep_days"));
       tk.setDivideRecdCount(FormUtil.getParameterIntVal(request, "d_divide_recd_count"));
       return tk;
   }
   
   protected TkConfig getQueryConditionForOpPlan(HttpServletRequest request,OperationResult opResult){
       TkConfig tk = new TkConfig();
       HashMap vQueryControlDefaultValues = null;
       String queryControlDefaultValues = request.getParameter("queryControlDefaultValues");
       String command = request.getParameter("command");
       if(FormUtil.isAddOrModifyMode(request)){
           //操作处于添加或修改模式
           if(command.equals(CommandConstant.COMMAND_DELETE)){
               return null;
           }
           tk.setOriginTableName(opResult.getAddPrimaryKey());
       } else{
           //操作处于非添加模式
           vQueryControlDefaultValues = FormUtil.getQueryControlDefaultValues(queryControlDefaultValues);
           if(!vQueryControlDefaultValues.isEmpty()){
               tk.setOriginTableName(FormUtil.getQueryControlDefaultValue(vQueryControlDefaultValues,"q_origin_table_name"));
           }
       }
       return tk;
   }
   
   protected int modifyPlanByTrans(HttpServletRequest request,TkConfigMapper tkMapper,TkConfig vo,OperationResult opResult) throws Exception{
       TransactionStatus status = null;
       int n = 0;
       try{
           opResult.setAddPrimaryKey(vo.getOriginTableName());
           status = txMgr.getTransaction(this.def);
           n = tkMapper.modifyPlan(vo);
           txMgr.commit(status);
       }catch(Exception e){
           PubUtil.handExceptionForTran(e, txMgr, status);
       }
       return n;
   }
   
}
