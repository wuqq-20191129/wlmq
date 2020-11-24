/*
 * 文件名：DistanceDetailAction
 * 版权：Copyright: goldsign (c) 2013
 */

package com.goldsign.rule.web;

import com.goldsign.frame.constant.FrameDBConstant;
import com.goldsign.frame.struts.BaseAction;
import com.goldsign.frame.util.FrameUtil;
import com.goldsign.rule.bo.DistanceDetailService;
import com.goldsign.rule.env.RuleConstant;
import com.goldsign.rule.util.Util;
import com.goldsign.rule.vo.DistanceChangeVo;
import java.util.Vector;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;


/*
 * 密钥管理系统 订单对应详细逻辑卡号Action类
 * @author     lindaquan
 * @version    V1.0
 */

public class DistanceDetailAction extends BaseAction {
    
    public DistanceDetailAction(){
        super();
    }
    
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
           ActionMessage am = null;

           DistanceDetailService bo = new DistanceDetailService();
           Vector results = new Vector();//返回结果集
           Vector operResults = new Vector();//修改，添加信息
           
           String command = request.getParameter("command");
           if(command == null){
               //默认为查询命令
               command = FrameDBConstant.COMMAND_QUERY;
           }
           command = command.trim();
           //查询
           am = this.query(request,bo,results);

           try{
                this.saveQueryResult(request, results, RuleConstant.RESULT_OD_DISTANCE_DETAIL);//返回结果集到页面
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
     * 查询
     * @param request
     * @param bo
     * @return 
     */
    private ActionMessage query(HttpServletRequest request, DistanceDetailService bo, Vector results) throws Exception {
        ActionMessage am = null;

        DistanceChangeVo vo = new DistanceChangeVo();
        Util util = new Util();
        
        try{
                vo.setId(request.getParameter("q_od_id"));//查询订单号
                results.addAll(bo.query(vo));
        }catch(Exception e){
                return util.operationExceptionHandle(request,FrameDBConstant.COMMAND_QUERY,e);
        }
        util.logOperation(FrameDBConstant.COMMAND_QUERY,request,"查询明细"+FrameDBConstant.OPERATIION_SUCCESS_LOG_MESSAGE);
        am = new ActionMessage("queryMessage",FrameUtil.GbkToIso("成功查询"+results.size() +"条记录"));
        return am;
    }
    
    //返回结果集到页面
    private void saveQueryResult(HttpServletRequest request,Vector results,String name) throws Exception{
            if(results !=null&&!results.isEmpty()) {
                this.saveResult(request,name,results);
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
    
}
