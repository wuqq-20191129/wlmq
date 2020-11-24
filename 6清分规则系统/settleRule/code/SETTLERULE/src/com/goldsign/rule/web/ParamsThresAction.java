/*
 * 文件名：ParamsThresAction
 * 版权：Copyright: goldsign (c) 2013
 */

package com.goldsign.rule.web;

import com.goldsign.frame.constant.FrameDBConstant;
import com.goldsign.frame.struts.BaseAction;
import com.goldsign.frame.util.FrameDBUtil;
import com.goldsign.frame.util.FramePubUtil;
import com.goldsign.frame.util.FrameUtil;
import com.goldsign.rule.bo.ParamsThresService;
import com.goldsign.rule.env.RuleConstant;
import com.goldsign.rule.util.Util;
import com.goldsign.rule.vo.OperResult;
import com.goldsign.rule.vo.ParamsThresVo;
import java.util.HashMap;
import java.util.Vector;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;


/*
 * 阀值参数配置
 * @author     lindaquan
 * @version    V1.0
 * @createTime 2013-11-25
 */

public class ParamsThresAction extends BaseAction {
    
    /**
    * @param ActionMapping mapping 转发的动作集合
    * @param ActionForm form  提交的FORM窗体
    * @param HttpServletRequest request HTTP请求对象
    * @param HttpServletResponse response HTTP响应对象
    * @throws Exception 异常
    * @return ActionForward 转发的URL对象
    */
   public ActionForward processLogic(ActionMapping mapping, ActionForm form,
                   HttpServletRequest request, HttpServletResponse response) throws Exception {
        ActionForward af = null;		
        ActionMessage am = new ActionMessage("queryMessage", FrameUtil.GbkToIso("操作成功"));

        ParamsThresService bo = new ParamsThresService();
        Vector results = new Vector();//返回结果集

        String command = request.getParameter("command");

        try{
            if(command != null){
                command = command.trim();
                //查询
                if(command.equals(FrameDBConstant.COMMAND_QUERY)){
                    am = this.query(request,bo,results,command);
                }
                //审核
                if(command.equals(FrameDBConstant.COMMAND_AUDIT)){
                    am = this.audit(request,bo,results,command);
                }
                //增加
                if(command.equals(FrameDBConstant.COMMAND_ADD)){
                    am = this.add(request,bo,results,command);
                }
                //删除
                if(command.equals(FrameDBConstant.COMMAND_DELETE)){
                    am = this.delete(request,bo,results,command);
                }
                //修改
                if(command.equals(FrameDBConstant.COMMAND_MODIFY)){
                    am = this.modify(request,bo,results,command);
                }
            }
            
            this.saveQueryResult(request, results, RuleConstant.RESULT_PARAMS_THRES);//返回结果集到页面
            af = this.getActionForward(mapping,FrameDBConstant.COMMAND_QUERY);//返回跳转页面
            getMessages(request).add("operationMessage", am);
            return af;
        }catch(Exception e){
            af = mapping.findForward("error");
            String error =e.getMessage();
            request.setAttribute("Error",error);
            return af;
        }
   }
   
   /**
     * 查询
     * @param request
     * @param bo
     * @return 
     */
    private ActionMessage query(HttpServletRequest request, ParamsThresService bo, Vector results, 
            String command) throws Exception {
        ActionMessage am = null;
        Util util = new Util();
        ParamsThresVo vo = getRequestAttribute(request, util, command);
        String preMsg = "阀值参数配置:";
        
        try{
            results.addAll(bo.query(vo));
            
        }catch(Exception e){
            return util.operationExceptionHandle(request,command,e);
        }
        util.logOperation(command,request,preMsg+FrameDBConstant.OPERATIION_SUCCESS_LOG_MESSAGE);
        am = new ActionMessage("queryMessage",FrameUtil.GbkToIso("成功查询"+results.size() +"条记录"));
        return am;
    }
   
    
    /**
     * 审核
     * @param request
     * @param bo
     * @param operResults
     * @return
     * @throws Exception 
     */
    private ActionMessage audit(HttpServletRequest request,ParamsThresService bo, Vector results, 
            String command) throws Exception{		
        Util util = new Util();
        ActionMessage am = null;
        String strKeyIDs = request.getParameter("allSelectedIDs");// allSelectedGroupIDs
        Vector keyIDs = new Vector();
        FrameUtil.getIDs(strKeyIDs, keyIDs,";");
        String operatorID = Util.getCurrentOperator(request);
        ParamsThresVo vo = new ParamsThresVo();
        OperResult result =null;
        String preMsg = "阀值参数配置:" + "主键：" + strKeyIDs + ":";

        try {
            if(keyIDs.size() > 1){
                results.addAll(bo.query(vo));
                util.logOperation(command, request, preMsg + FrameDBConstant.OPERATIION_SUCCESS_LOG_MESSAGE);
                am = new ActionMessage("auditMessage", FrameUtil.GbkToIso("请选择一条记录审核！"));
                return am;
            }else{
                vo.setId(String.valueOf(keyIDs.get(0)));
                vo.setUpdateOperator(operatorID);
                result = bo.audit(vo);
            }
            results.addAll(bo.query(vo));
        }
        catch (Exception e) {
            return util.operationExceptionHandle(request, command, e);
        }
        util.logOperation(command, request, preMsg + FrameDBConstant.OPERATIION_SUCCESS_LOG_MESSAGE);
        am = new ActionMessage("auditMessage", FrameUtil.GbkToIso("成功审核" +result.getUpdateNum() + "条记录"));
        return am;

    }
    
    
    /**
    * 增加
    * @param request
    * @param bo
    * @return 
    */
    private ActionMessage add(HttpServletRequest request, ParamsThresService bo, Vector results,
            String command) throws Exception {
        ActionMessage am = null;
        Util util = new Util();
        ParamsThresVo vo = getRequestAttribute(request, util, command);
        OperResult result =null;

        try{
            result = bo.insert(vo);//新建阀值参数草稿版本
                
        }catch(Exception e){
            return util.operationExceptionHandle(request,command,e);
        }
        util.logOperation(command,request,"阀值参数配置:" + FrameDBConstant.OPERATIION_SUCCESS_LOG_MESSAGE);

        am = new ActionMessage("addMessage",FrameUtil.GbkToIso("成功添加"+result.getUpdateNum()+"条记录"));
        vo = (ParamsThresVo) result.getUpdateOb();
        results.addAll(bo.query(vo));

        return am;
    }
    
    /**
     * 删除
     * @param request
     * @param bo
     * @return 
     */
    private ActionMessage delete(HttpServletRequest request, ParamsThresService bo, Vector results, 
            String command) throws Exception {
        ActionMessage am = null;
        String strKeyIDs = request.getParameter("allSelectedIDs");// allSelectedGroupIDs
        Vector keyIDs = new Vector();
        Util util = new Util();
        FrameUtil.getIDs(strKeyIDs, keyIDs,";");
        int result =0;
        String preMsg = "阀值参数配置:" + strKeyIDs + "，";

        try {
                result = bo.remove(keyIDs);
                ParamsThresVo vo = new ParamsThresVo();
                results.addAll(bo.query(vo));
        }
        catch (Exception e) {
                return util.operationExceptionHandle(request, command, e);
        }
        util.logOperation(command, request, preMsg + FrameDBConstant.OPERATIION_SUCCESS_LOG_MESSAGE);
        am = new ActionMessage("deleteMessage", FrameUtil.GbkToIso("成功删除" + result + "条记录"));
        return am;
    }

    /**
     * 修改
     * @param request
     * @param bo
     * @param operResults
     * @return 
     */ 
    private ActionMessage modify(HttpServletRequest request, ParamsThresService bo, Vector results,
            String command) throws Exception {
        ActionMessage am = null;
        Util util = new Util();
        ParamsThresVo vo = getRequestAttribute(request, util, command);
        OperResult result =null;

        try{
            result = bo.update(vo);//修改
        } catch (Exception e) {
            return util.operationExceptionHandle(request, command, e);
        }
        util.logOperation(command, request, "阀值参数配置:" + FrameDBConstant.OPERATIION_SUCCESS_LOG_MESSAGE);
        am = new ActionMessage("modifyMessage", FrameUtil.GbkToIso("成功修改" + result.getUpdateNum() + "条记录"));
        
        vo = (ParamsThresVo) result.getUpdateOb();
        results.addAll(bo.query(vo));
        return am;
    }
    
    
   /**
    * @param mapping
    * @param command
    * @return 
    */
   private ActionForward getActionForward(ActionMapping mapping,String command){
        ActionForward af ;
        af= mapping.findForward(command);
        return af;
   }
   
   //返回结果集到页面
    private void saveQueryResult(HttpServletRequest request,Vector results,String name) throws Exception{
        
        FrameDBUtil util = new FrameDBUtil();
        if(results !=null&&!results.isEmpty()) {
            this.saveResult(request,name,results);
        }
        this.saveActionResult(request, RuleConstant.RESULT_RECORD_FLAG, 
                util.getPubFlagsByType(Integer.valueOf(RuleConstant.PARAMS_VERSION), FrameDBUtil.PUB_FLAGS));
    }
    
    /**
    * 
    * @param request 请求对象
    * @return 封装的页面控件值对象DistanceProportionVo
    */
   private ParamsThresVo getRequestAttribute(HttpServletRequest request, Util util, String command){
       ParamsThresVo vo = new ParamsThresVo();
       
       if(command.equals(FrameDBConstant.COMMAND_QUERY)){
            HashMap vQueryControlDefaultValues = null;
            String queryControlDefaultValues = request.getParameter("queryControlDefaultValues");
            vQueryControlDefaultValues =  FramePubUtil.getQueryControlDefaultValues(queryControlDefaultValues);

            if(!vQueryControlDefaultValues.isEmpty()){
                //获取查询条件信息
                vo.setRecordFlag(util.getQueryControlDefaultValue(vQueryControlDefaultValues, "q_recorqFlag"));
            } else {
                vo.setRecordFlag(request.getParameter("q_recorqFlag"));//参数状态
            }
       }
       
       if(command.equals(FrameDBConstant.COMMAND_ADD) || command.equals(FrameDBConstant.COMMAND_MODIFY)){
           String operatorID = Util.getCurrentOperator(request);
           vo.setUpdateOperator(operatorID);
           vo.setId(request.getParameter("d_id"));
           vo.setChangeThres(request.getParameter("d_changeThres"));//
           vo.setDistanceThres(request.getParameter("d_distanceThres"));//
           vo.setStationThres(request.getParameter("d_stationThres"));//
           vo.setTimeThres(request.getParameter("d_timeThres"));//
           vo.setDescription(FrameUtil.ChineseToIso(request.getParameter("d_description")));//
       }
        return vo;
   }

}
