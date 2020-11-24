/*
 * 文件名：DistanceODAction
 * 版权：Copyright: goldsign (c) 2013
 */

package com.goldsign.rule.web;

import com.goldsign.frame.constant.FrameDBConstant;
import com.goldsign.frame.struts.BaseAction;
import com.goldsign.frame.util.FrameDBUtil;
import com.goldsign.frame.util.FramePubUtil;
import com.goldsign.frame.util.FrameUtil;
import com.goldsign.rule.bo.DistanceODService;
import com.goldsign.rule.env.RuleConstant;
import com.goldsign.rule.util.Util;
import com.goldsign.rule.vo.DistanceODVo;
import java.util.HashMap;
import java.util.Vector;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;


/*
 * 里程权重比例查询
 * @author     lindaquan
 * @version    V1.0
 * @createTime 2013-11-7
 */

public class DistanceODAction extends BaseAction {
    
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
        ActionMessage am = new ActionMessage("queryMessage", FrameUtil.GbkToIso("操作成功"));

        DistanceODService bo = new DistanceODService();
        Vector results = new Vector();//返回结果集

        String command = request.getParameter("command");

        try{
            if(command != null){
                command = command.trim();
                //查询
                if(command.equals(FrameDBConstant.COMMAND_QUERY)){
                    am = this.query(request,bo,results);
                }

            }else{
                command = FrameDBConstant.COMMAND_QUERY;
            }
            
            this.saveQueryResult(request, results, RuleConstant.RESULT_OD_DISTANCE);//返回结果集到页面
            af = this.getActionForward(mapping,command);//返回跳转页面
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
    private ActionMessage query(HttpServletRequest request, DistanceODService bo, Vector results) throws Exception {
        ActionMessage am = null;
        Util util = new Util();
        DistanceODVo vo = getRequestAttribute(request, util);
        String preMsg = "清分OD路径查询:";
        
        try{
            results.addAll(bo.query(vo));
            
        }catch(Exception e){
            return util.operationExceptionHandle(request,FrameDBConstant.COMMAND_QUERY,e);
        }
        util.logOperation(FrameDBConstant.COMMAND_QUERY,request,preMsg+FrameDBConstant.OPERATIION_SUCCESS_LOG_MESSAGE);
        am = new ActionMessage("queryMessage",FrameUtil.GbkToIso("成功查询"+results.size() +"条记录"));
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
        this.saveActionResult(request, RuleConstant.RESULT_LINES, RuleConstant.LINES);
        this.saveActionResult(request, RuleConstant.RESULT_STATIONS, RuleConstant.STATIONS);
        this.saveActionResult(request, RuleConstant.RESULT_RECORD_FLAG, 
                util.getPubFlagsByType(Integer.valueOf(RuleConstant.PARAMS_VERSION), FrameDBUtil.PUB_FLAGS));
        
        this.saveQueryControlDefaultValues(request);
    }
    
    /**
    * 
    * @param request 请求对象
    * @return 封装的页面控件值对象DistanceProportionVo
    */
   private DistanceODVo getRequestAttribute(HttpServletRequest request, Util util){
        HashMap vQueryControlDefaultValues = null;
        String queryControlDefaultValues = request.getParameter("queryControlDefaultValues");
        DistanceODVo vo = new DistanceODVo();
        
        vQueryControlDefaultValues =  FramePubUtil.getQueryControlDefaultValues(queryControlDefaultValues);
        
        if(!vQueryControlDefaultValues.isEmpty()){
            //获取查询条件信息
            vo.setoLineId(util.getQueryControlDefaultValue(vQueryControlDefaultValues, "q_oline_id"));
            vo.setoStationId(util.getQueryControlDefaultValue(vQueryControlDefaultValues, "q_ostation_id"));
            vo.seteLineId(util.getQueryControlDefaultValue(vQueryControlDefaultValues, "q_eline_id"));
            vo.seteStationId(util.getQueryControlDefaultValue(vQueryControlDefaultValues, "q_estation_id"));
            vo.setRecordFlag(util.getQueryControlDefaultValue(vQueryControlDefaultValues, "q_recorq_flag"));
        } else {
            vo.setoLineId(request.getParameter("q_oline_id"));//开始线路
            vo.setoStationId(request.getParameter("q_ostation_id"));//开始站点
            vo.seteLineId(request.getParameter("q_eline_id"));//结束线路
            vo.seteStationId(request.getParameter("q_estation_id"));//结束站点
            vo.setRecordFlag(request.getParameter("q_recorq_flag"));//参数状态
        }
       
        return vo;
   }

}
