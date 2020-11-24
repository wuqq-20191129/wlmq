/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.goldsign.acc.app.st.controller;

import com.goldsign.acc.app.st.entity.TkClear;
import com.goldsign.acc.frame.constant.CommandConstant;
import com.goldsign.acc.frame.controller.DMSBaseController;
import com.goldsign.acc.frame.util.FormUtil;
import com.goldsign.acc.frame.vo.OperationResult;
import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author limj
 */
public class TkClearParentController extends DMSBaseController{
    protected TkClear getQueryConditionIn(HttpServletRequest request){
        TkClear pc = new TkClear();
        pc.setOriginTableName(FormUtil.getParameter(request,"q_origin_table_name"));
        pc.setDestTableName(FormUtil.getParameter(request,"q_dest_table_name"));
        pc.setBeginClearDateTime(FormUtil.getParameter(request,"q_begin_clear_datetime"));
        pc.setEndClearDateTime(FormUtil.getParameter(request, "q_end_clear_datetime"));
        return pc;
    }
    protected TkClear getQueryConditionForOpPlan (HttpServletRequest request,OperationResult opResult){
        TkClear tk = new TkClear();
        HashMap vQueryControlDefaultValues = null;
        String queryControlDefaultValues = request.getParameter("queryControlDefaultValues");
        String command = request.getParameter("command");
        if(FormUtil.isAddOrModifyMode(request)){
            //操作处于添加或修改模式
            if(command.equals(CommandConstant.COMMAND_DELETE)){
                return null;
            }
            tk.setOriginTableName(opResult.getAddPrimaryKey());
        }else{
            //操作处于非添加模式
            vQueryControlDefaultValues = FormUtil.getQueryControlDefaultValues(queryControlDefaultValues);
            if(!vQueryControlDefaultValues.isEmpty()){
                tk.setOriginTableName(FormUtil.getQueryControlDefaultValue(vQueryControlDefaultValues, "q_origin_table_name"));
                tk.setDestTableName(FormUtil.getQueryControlDefaultValue(vQueryControlDefaultValues, "q_dest_table_name"));
                tk.setBeginClearDateTime(FormUtil.getQueryControlDefaultValue(vQueryControlDefaultValues, "q_begin_clear_datetime"));
                tk.setEndClearDateTime(FormUtil.getQueryControlDefaultValue(vQueryControlDefaultValues, "q_end_clear_datetime"));
            }
        }
        return tk;
    }
}
