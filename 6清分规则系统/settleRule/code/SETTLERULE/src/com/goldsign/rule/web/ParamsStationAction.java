/*
 * 文件名：ParamsStationAction
 * 版权：Copyright: goldsign (c) 2013
 */

package com.goldsign.rule.web;

import com.goldsign.frame.constant.FrameDBConstant;
import com.goldsign.frame.struts.BaseAction;
import com.goldsign.frame.util.FrameDBUtil;
import com.goldsign.frame.util.FramePubUtil;
import com.goldsign.frame.util.FrameUtil;
import com.goldsign.rule.bo.ParamsStationService;
import com.goldsign.rule.env.RuleConstant;
import com.goldsign.rule.util.Util;
import com.goldsign.rule.vo.OperResult;
import com.goldsign.rule.vo.ParamsStationVo;
import java.util.HashMap;
import java.util.Vector;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;


/*
 * 清分规则系统 参数设置Action类
 * @author     wangkejia
 * @version    V1.0
 */

public class ParamsStationAction extends BaseAction {

    /**
    * @param ActionMapping mapping 转发的动作集合
    * @param ActionForm form  提交的FORM窗体
    * @param HttpServletRequest request HTTP请求对象
    * @param HttpServletResponse response HTTP响应对象
    * @throws Exception 异常
    * @return ActionForward 转发的URL对象
    */
   public ActionForward processLogic(ActionMapping mapping, ActionForm form,
                   HttpServletRequest request, HttpServletResponse response)
   throws Exception {
           ActionForward af = null;		
           ActionMessage am =  new ActionMessage("queryMessage", FrameUtil.GbkToIso("操作成功"));

           ParamsStationService bo = new ParamsStationService();
           Vector results = new Vector();//返回结果集
           Vector operResults = new Vector();//修改，添加信息
           
           String command = request.getParameter("command");
           
           try{
                if(command != null){
                    command = command.trim();
                    //增加
                    if(command.equals(FrameDBConstant.COMMAND_ADD)){
                        am = this.add(request,bo,operResults);
                    }
                    //删除
                    if(command.equals(FrameDBConstant.COMMAND_DELETE)){
                        am = this.delete(request,bo);
                    }
                    //修改
                    if(command.equals(FrameDBConstant.COMMAND_MODIFY)){
                        am = this.modify(request,bo,operResults);
                    }
                    //审核
                     if(command.equals(FrameDBConstant.COMMAND_AUDIT)){
                        am = this.audit(request,bo,operResults);
                    }
                    //查询
                    if(command.equals(FrameDBConstant.COMMAND_QUERY)){
                        am = this.query(request,bo,results,command,operResults);
                    }else{
                        this.query(request,bo,results,command,operResults);
                    }

                }
          
                this.saveQueryResult(request, results, RuleConstant.RESULT_PARAMSSTATION);//返回结果集到页面
                af = this.getActionForward(mapping,FrameDBConstant.COMMAND_QUERY);//返回跳转页面
                getMessages(request).add("operationMessage", am);
                return af;
                
           }catch(Exception e){
                af = mapping.findForward("error");
                String error =e.getMessage();
                request.setAttribute("Error",error);
                return af ;
           }

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

    /**
     * 查询
     * @param request
     * @param bo
     * @return 
     */
    private ActionMessage query(HttpServletRequest request, ParamsStationService bo, Vector results, 
            String command, Vector operResults) throws Exception {
        ActionMessage am = null;
        ParamsStationVo vo = new ParamsStationVo();
        Util util = new Util();
        
        String preMsg = "当前站点：" +vo.getPresentStation()+"下一站点："+vo.getNextStation();
        try{
                if(util.isAddMode(request) && !(operResults ==null || operResults.isEmpty())){//操作处于添加模式
                    if(command.equals("delete"))
                            return null;
                    //获取添加的主键信息
                    vo = this.getPrimaryKeyInfo(operResults);
                }
                else//操作处于非添加模式
                {
                    vo = this.getRequestAttribute(request,util);
                }
                results.addAll(bo.query(vo));
        }catch(Exception e){
                return util.operationExceptionHandle(request,FrameDBConstant.COMMAND_QUERY,e);
        }
        util.logOperation(FrameDBConstant.COMMAND_QUERY,request,preMsg
                         +FrameDBConstant.OPERATIION_SUCCESS_LOG_MESSAGE);

        am = new ActionMessage("addMessage",FrameUtil.GbkToIso("成功查询"+results.size()+"条记录"));

        return am;
    }
    
       /**
    * 增加
    * @param request
    * @param bo
    * @return 
    */ 
     private synchronized ActionMessage add(HttpServletRequest request, ParamsStationService bo, Vector operResults) throws Exception {
        ActionMessage am = null;
        Util util = new Util();
        ParamsStationVo vo = this.getRequestAttributeforAdd(request,util);
        OperResult result =null;
        String operatorID=Util.getCurrentOperator(request);
       
        String preMsg = "当前站点：" +vo.getPresentStation()+"下一站点："+vo.getNextStation();
        try{

              result = bo.insert(vo,operatorID);
              
        }catch(Exception e){
                return util.operationExceptionHandle(request,FrameDBConstant.COMMAND_ADD,e);
        }
        util.logOperation(FrameDBConstant.COMMAND_ADD,request,preMsg
                         +FrameDBConstant.OPERATIION_SUCCESS_LOG_MESSAGE);

        am = new ActionMessage("addMessage",FrameUtil.GbkToIso("成功添加"+result.getUpdateNum()+"条记录"));
        operResults.add(result);

        return am;
    }
    
    /**
     * 删除
     * @param request
     * @param bo
     * @return 
     */
  private ActionMessage delete(HttpServletRequest request, ParamsStationService bo) throws Exception {
        ActionMessage am = null;
        String strKeyIDs = request.getParameter("allSelectedIDs");// allSelectedGroupIDs
        Vector keyIDs = new Vector();
        Util util = new Util();
        FrameUtil.getIDs(strKeyIDs, keyIDs,";");

        int n = 0;
        String preMsg = "当前站点：" + strKeyIDs.substring(0,2) + "下一站点:"+strKeyIDs.substring(2,4);

        try {
                n = bo.remove(keyIDs);
        }
        catch (Exception e) {
                return util.operationExceptionHandle(request, FrameDBConstant.COMMAND_DELETE, e);
        }
        util.logOperation(FrameDBConstant.COMMAND_DELETE, request, preMsg
                        + FrameDBConstant.OPERATIION_SUCCESS_LOG_MESSAGE);
        am = new ActionMessage("deleteMessage", FrameUtil.GbkToIso("成功删除" + n + "条记录"));
        return am;
    }

    /**
     * 修改
     * @param request
     * @param bo
     * @param operResults
     * @return 
     */
      private ActionMessage modify(HttpServletRequest request, ParamsStationService bo, Vector operResults) throws Exception {
        ActionMessage am = null;
        Util util=new Util();
        ParamsStationVo vo = this.getRequestAttributeforAdd(request,util);

        String strKeyIDs = request.getParameter("allSelectedIDs");// allSelectedGroupIDs
        Vector keyIDs = new Vector();
        FrameUtil.getIDs(strKeyIDs, keyIDs,";");
        String operatorID = Util.getCurrentOperator(request);
        String preMsg = "当前站点：" +vo.getPresentStation()+"下一站点："+vo.getNextStation();        
        OperResult result =null;

        try {
            
              result = bo.update(keyIDs,vo,operatorID);
            } catch (Exception e) {
                return util.operationExceptionHandle(request, FrameDBConstant.COMMAND_MODIFY, e);
            }
        util.logOperation(FrameDBConstant.COMMAND_MODIFY, request, preMsg
                        + FrameDBConstant.OPERATIION_SUCCESS_LOG_MESSAGE);

        operResults.add(result);
        am = new ActionMessage("modifyMessage", FrameUtil.GbkToIso("成功修改" + result.getUpdateNum() + "条记录"));
        return am;
    }
     
     private synchronized ActionMessage audit(HttpServletRequest request,ParamsStationService bo,Vector operResults ) throws Exception{		
        Util util = new Util();
        ActionMessage am = null;
        String strKeyIDs = request.getParameter("allSelectedIDs");// allSelectedGroupIDs
        Vector keyIDs = new Vector();
        FrameUtil.getIDs(strKeyIDs, keyIDs,";");
        String operatorID = Util.getCurrentOperator(request);
        OperResult result =null;
        int n = 0;
        String preMsg = "当前站点：" + strKeyIDs.substring(0,2) + "下一站点:"+strKeyIDs.substring(2,4);

        try {
            result = bo.audit(keyIDs,operatorID);
            operResults.add(result);
        }
        catch (Exception e) {
            return util.operationExceptionHandle(request, FrameDBConstant.COMMAND_AUDIT, e);
        }
        util.logOperation(FrameDBConstant.COMMAND_AUDIT, request, preMsg
                        + FrameDBConstant.OPERATIION_SUCCESS_LOG_MESSAGE);
        am = new ActionMessage("auditMessage", FrameUtil.GbkToIso("成功审核" +result.getUpdateNum() + "条记录"));
        return am;

    }

   
    
    //返回结果集到页面
    private void saveQueryResult(HttpServletRequest request,Vector results,String name) throws Exception{
                    if(results !=null&&!results.isEmpty()) {
                this.saveResult(request,name,results);
                            }
            FrameDBUtil util = new FrameDBUtil();
        this.saveActionResult(request, RuleConstant.RESULT_LINES, RuleConstant.LINES);
        this.saveActionResult(request, RuleConstant.RESULT_STATIONS, RuleConstant.STATIONS);
        this.saveActionResult(request, RuleConstant.RESULT_RECORD_FLAG, 
                util.getPubFlagsByType(Integer.valueOf(RuleConstant.PARAMS_VERSION), FrameDBUtil.PUB_FLAGS));
        
        this.saveQueryControlDefaultValues(request);   
    }

       

    /**
    * 
    * @param request 请求对象
    * @return 封装的页面控件值对象ParamsVo
    */
    private ParamsStationVo getRequestAttribute(HttpServletRequest request, Util util){
        HashMap vQueryControlDefaultValues = null;
        String queryControlDefaultValues = request.getParameter("queryControlDefaultValues");
        ParamsStationVo vo = new ParamsStationVo();
        
        vQueryControlDefaultValues =  FramePubUtil.getQueryControlDefaultValues(queryControlDefaultValues);
        
        if(!vQueryControlDefaultValues.isEmpty()){
            //获取查询条件信息
            vo.setPresentStation(util.getQueryControlDefaultValue(vQueryControlDefaultValues, "q_ostation_id"));
            vo.setLine(util.getQueryControlDefaultValue(vQueryControlDefaultValues, "q_oline_id"));
            vo.setRecordFlag(util.getQueryControlDefaultValue(vQueryControlDefaultValues, "q_recorq_flag"));
            vo.setCreateTime(util.getQueryControlDefaultValue(vQueryControlDefaultValues, "q_createTime"));
            vo.setOperator(util.getQueryControlDefaultValue(vQueryControlDefaultValues, "q_operator"));
                
                
        } else {
            vo.setPresentStation(request.getParameter("q_ostation_id"));//
            vo.setLine(request.getParameter("q_oline_id"));//
            vo.setRecordFlag(request.getParameter("q_recorq_flag"));
            vo.setBeginCreateTime(request.getParameter("q_beginOpTime"));  
            vo.setEndCreateTime(request.getParameter("q_endOpTime"));
                            
        }
       
        return vo;
   }
    
    private ParamsStationVo getRequestAttributeforAdd(HttpServletRequest request, Util util){
        ParamsStationVo vo = new ParamsStationVo();
            String NTStation="99";   
            vo.setPresentStation(request.getParameter("d_presentStation"));//
            vo.setNextStation(request.getParameter("d_nextStation"));//
            vo.setLine(request.getParameter("d_line"));//
            if(request.getParameter("d_nextTransferStation")!=null&&request.getParameter("d_nextTransferStation").length()!=0){
            vo.setNextTransferStation(request.getParameter("d_nextTransferStation"));//下一站点为空，设置成‘99’
            }else{
                 vo.setNextTransferStation(NTStation);
            }
            vo.setMileage(request.getParameter("d_mileage"));//参数状态
            vo.setRecordFlag(request.getParameter("d_recordFlag"));
            vo.setVersion(request.getParameter("d_version"));

        return vo;
   }
    private ParamsStationVo getPrimaryKeyInfo(Vector operResults) throws Exception{
            if(operResults ==null || operResults.isEmpty())
                    throw new Exception("没有主键信息");
            OperResult result = (OperResult)operResults.get(0);
            ParamsStationVo vo = (ParamsStationVo)result.getUpdateOb();
            return vo;
    }
}
