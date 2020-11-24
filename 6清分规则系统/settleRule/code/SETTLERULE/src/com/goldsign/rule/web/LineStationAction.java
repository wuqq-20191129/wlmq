/*
 * 文件名：LineStationAction
 * 版权：Copyright: goldsign (c) 2013
 */

package com.goldsign.rule.web;

import com.goldsign.frame.constant.FrameDBConstant;
import com.goldsign.frame.struts.BaseAction;
import com.goldsign.frame.util.FrameDBUtil;
import com.goldsign.frame.util.FramePubUtil;
import com.goldsign.frame.util.FrameUtil;
import com.goldsign.rule.bo.LineStationService;
import com.goldsign.rule.env.RuleConstant;
import com.goldsign.rule.util.Util;
import com.goldsign.rule.vo.LineStationVo;
import com.goldsign.rule.vo.OperResult;
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

public class LineStationAction extends BaseAction {

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

           LineStationService bo = new LineStationService();
           Vector results = new Vector();//返回结果集
           Vector operResults = new Vector();//修改，添加信息
           
           String command = request.getParameter("command");
           
           try{
           if(command != null){
            command = command.trim();
            //查询
            if(command.equals(FrameDBConstant.COMMAND_QUERY)){
                am = this.query(request,bo,results,command,operResults);
            }
           
           }else{
           command = FrameDBConstant.COMMAND_QUERY;               
               //默认为查询命令
               //command = FrameDBConstant.COMMAND_QUERY;     
           }
          
                this.saveQueryResult(request, results, RuleConstant.RESULT_LINESTATION);//返回结果集到页面
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
    private ActionMessage query(HttpServletRequest request, LineStationService bo, Vector results, 
            String command, Vector operResults) throws Exception {
        ActionMessage am = null;
        Util util = new Util();
        LineStationVo vo = this.getRequestAttribute(request,util);
        OperResult result =null;
        String preMsg = "线路ID：" +vo.getLine()+"站点ID："+vo.getStationId();
        try{
               
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
    
    /**
     * 删除
     * @param request
     * @param bo
     * @return 
     */


    /**
     * 修改
     * @param request
     * @param bo
     * @param operResults
     * @return 
     */



   
    
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
    private LineStationVo getRequestAttribute(HttpServletRequest request, Util util){
        HashMap vQueryControlDefaultValues = null;
        String queryControlDefaultValues = request.getParameter("queryControlDefaultValues");
        LineStationVo vo = new LineStationVo();
        
        vQueryControlDefaultValues =  FramePubUtil.getQueryControlDefaultValues(queryControlDefaultValues);
        
        if(!vQueryControlDefaultValues.isEmpty()){
            //获取查询条件信息
            vo.setLine(util.getQueryControlDefaultValue(vQueryControlDefaultValues, "q_oline_id"));
            vo.setStationId(util.getQueryControlDefaultValue(vQueryControlDefaultValues, "q_ostation_id"));
            vo.setRecordFlag(util.getQueryControlDefaultValue(vQueryControlDefaultValues, "q_recorq_flag"));     
        } else {
            vo.setLine(request.getParameter("q_oline_id"));
            vo.setStationId(request.getParameter("q_ostation_id"));
            vo.setRecordFlag(request.getParameter("q_recorq_flag"));
        }
       
        return vo;
   }

}
