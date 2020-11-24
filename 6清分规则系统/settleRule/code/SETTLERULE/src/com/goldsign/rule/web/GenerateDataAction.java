/*
 * 文件名：GenerateDataAction
 * 版权：Copyright: goldsign (c) 2013
 */

package com.goldsign.rule.web;

import com.goldsign.frame.constant.FrameDBConstant;
import com.goldsign.frame.struts.BaseAction;
import com.goldsign.frame.util.FrameDBUtil;
import com.goldsign.frame.util.FramePubUtil;
import com.goldsign.frame.util.FrameUtil;
import com.goldsign.rule.bo.DistanceProportionService;
import com.goldsign.rule.bo.GenerateDataService;
import com.goldsign.rule.bo.GenerateODService;
import com.goldsign.rule.env.RuleConstant;
import com.goldsign.rule.util.PageControlUtil;
import com.goldsign.rule.util.ProportionFileWrite;
import com.goldsign.rule.util.Util;
import com.goldsign.rule.vo.DistanceProportionVo;
import com.goldsign.rule.vo.OperResult;
import com.goldsign.rule.vo.PageVo;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Vector;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;


/*
 * 生成权重数据
 * @author     lindaquan
 * @version    V1.0
 * @createTime 2013-11-7
 */

public class GenerateDataAction extends BaseAction {
    
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
        
        PageControlUtil pUtil =new PageControlUtil();
        GenerateDataService bo = new GenerateDataService();
        DistanceProportionService dbo = new DistanceProportionService();
        GenerateODService odbo = new GenerateODService();
        Vector results = new Vector();//返回结果集
        String command = request.getParameter("command");

        try{
            if(command != null){
                command = command.trim();
                //查询或下载时查询结果集
                if(command.equals(FrameDBConstant.COMMAND_QUERY) || command.equals(FrameDBConstant.COMMAND_DOWNLOAD)){
                    am = this.query(request,dbo,results);
                }
                //下载
                if(command.equals(FrameDBConstant.COMMAND_DOWNLOAD)){
                    this.downFile(mapping, request, response, results);
                    return null;
                }
                //查询或下载时查询结果集
                if(command.equals(FrameDBConstant.COMMAND_QUERY) ||command.equals(PageControlUtil.COMMAND_NEXT) ||
                   command.equals(PageControlUtil.COMMAND_NEXTEND) || command.equals(PageControlUtil.COMMAND_BACK) || command.equals(PageControlUtil.COMMAND_BACKEND)){
                    
                    //结果分页
                    results =pUtil.seperateResults(request,this,results);
                    //分页信息
                    if(pUtil.isPageControl(request)){
                       am = pUtil.getMessage(request);
                    }

                    PageVo pageVo = (PageVo) request.getSession().getAttribute(PageControlUtil.NAME_NEXT);
                    this.saveResult(request,"record",pageVo.getTotalRecords()); //总记录数
                }
                
                //生成权重数据
                if(command.equals(FrameDBConstant.COMMAND_GENERATE)){
                    am = this.generate(request, bo);
                }
                
                //生成OD数据
                if(command.equals(FrameDBConstant.COMMAND_GENERATE_OD)){
                    am = this.generateOD(request, odbo);
                }
            }
            
            this.saveQueryResult(request, results, RuleConstant.RESULT_PROPORTIONS);//返回结果集到页面
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
    private ActionMessage query(HttpServletRequest request, DistanceProportionService bo, Vector results) throws Exception {
        
        ActionMessage am = null;
        Util util = new Util();
        DistanceProportionVo vo = getRequestAttribute(request, util);
        String preMsg = "清分权重查询:";
        
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
    * 生成权重数据
    * @param request
    * @param bo 
    */
    private ActionMessage generate(HttpServletRequest request, GenerateDataService bo) throws Exception {
        ActionMessage am = null;
        Util util = new Util();
        OperResult map = new OperResult();
        String preMsg = "生成权重数据:";
        
        try {
            map = bo.generate();
        } catch (Exception e) {
            if(e.getMessage().indexOf("ORA-00001")>-1){
                am = new ActionMessage("operMessage", FrameUtil.GbkToIso("请勿1小时内重复生成数据！"));
                util.logOperation(FrameDBConstant.COMMAND_SUBMIT,request,preMsg+e.getMessage());
                return am;
            }else{
                return util.operationExceptionHandle(request, preMsg, e);
            }
        }
        
        util.logOperation(FrameDBConstant.COMMAND_SUBMIT,request,preMsg+FrameDBConstant.OPERATIION_SUCCESS_LOG_MESSAGE);
        am = new ActionMessage("queryMessage",FrameUtil.GbkToIso(String.valueOf(map.getRetMsg())));
        
        return am;
    }
    
    /**
     * 返回结果集到页面
     * @param request
     * @param results
     * @param name
     * @throws Exception 
     */
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
   private DistanceProportionVo getRequestAttribute(HttpServletRequest request, Util util){
        HashMap vQueryControlDefaultValues = null;
        String queryControlDefaultValues = request.getParameter("queryControlDefaultValues");
        DistanceProportionVo vo = new DistanceProportionVo();
        
        vQueryControlDefaultValues =  FramePubUtil.getQueryControlDefaultValues(queryControlDefaultValues);
        
        if(!vQueryControlDefaultValues.isEmpty()){
            //获取查询条件信息
            vo.setoLineId(util.getQueryControlDefaultValue(vQueryControlDefaultValues, "q_oline_id"));
            vo.setoStationId(util.getQueryControlDefaultValue(vQueryControlDefaultValues, "q_ostation_id"));
            vo.setdLineId(util.getQueryControlDefaultValue(vQueryControlDefaultValues, "q_eline_id"));
            vo.setdStationId(util.getQueryControlDefaultValue(vQueryControlDefaultValues, "q_estation_id"));
            vo.setRecordFlag(util.getQueryControlDefaultValue(vQueryControlDefaultValues, "q_recorq_flag"));
        } else {
            vo.setoLineId(request.getParameter("q_oline_id"));//开始线路
            vo.setoStationId(request.getParameter("q_ostation_id"));//开始站点
            vo.setdLineId(request.getParameter("q_eline_id"));//结束线路
            vo.setdStationId(request.getParameter("q_estation_id"));//结束站点
            vo.setRecordFlag(request.getParameter("q_recorq_flag"));//参数状态
        }
       
        return vo;
   }
   

    /**
     * 实现文件的下载
     * @param request
     * @param response
     * @param results
     * @return
     * @throws Exception 
     */
    public ActionForward downFile(ActionMapping mapping, 
            HttpServletRequest request, HttpServletResponse response, Vector results) throws Exception {
        ActionForward af = null;
        //导出字段
        String params = request.getParameter("c_checkbox");
        String path = pathName(request);
        
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        OutputStream fos = null;
        InputStream fis = null;
        
        //如果是从服务器上取就用这个获得系统的绝对路径方法。 
        String filepath = servlet.getServletContext().getRealPath("/" + path);
        System.out.println("文件路径"+filepath);
        File uploadFile = new File(filepath);
        if(uploadFile.exists()){
            uploadFile.mkdir();
        }
        uploadFile.createNewFile();
        //将查询结果写入文件
        ProportionFileWrite.writeVectorModel(results, uploadFile, params);
        
        fis = new FileInputStream(uploadFile);
        bis = new BufferedInputStream(fis);
        fos = response.getOutputStream();
        bos = new BufferedOutputStream(fos);
        
        //这个就就是弹出下载对话框的关键代码
        response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode(path, "utf-8"));
        int bytesRead = 0;
        
        //这个地方的同上传的一样。我就不多说了，都是用输入流进行先读，然后用输出流去写，唯一不同的是我用的是缓冲输入输出流
        byte[] buffer = new byte[8192];
        while ((bytesRead = bis.read(buffer, 0, 8192)) != -1) {
            bos.write(buffer, 0, bytesRead);
        }
        
        bos.flush();
        fis.close();
        bis.close();
        fos.close();
        bos.close();
        
        af = this.getActionForward(mapping,FrameDBConstant.COMMAND_QUERY);//返回跳转页面
        return af;
        
    }

    /**
     * 取文件名
     * @param request
     * @return 
     */
    private String pathName(HttpServletRequest request) {
        Util util = new Util();
        String path = "PROPORTION.";//临时文件名
        //查询条件
        DistanceProportionVo vo = new DistanceProportionVo();
        vo = this.getRequestAttribute(request, util);
        
        if(FrameUtil.stringIsNotEmpty(vo.getoLineId())){
             path += vo.getoLineId();
        }
        if(FrameUtil.stringIsNotEmpty(vo.getoStationId())){
             path += vo.getoStationId();
        }
        if(FrameUtil.stringIsNotEmpty(vo.getdLineId())){
             path += vo.getdLineId();
        }
        if(FrameUtil.stringIsNotEmpty(vo.getdStationId())){
             path += vo.getdStationId();
        }
        if(path.equals("PROPORTION.")){
            path += "ALL";
        }
        
        return path + ".txt";
    }

    /**
     * 生成OD数据
     * @param request
     * @param odbo
     * @return
     * @throws Exception 
     */
    private ActionMessage generateOD(HttpServletRequest request, GenerateODService odbo) throws Exception {
        ActionMessage am = null;
        Util util = new Util();
        OperResult map = new OperResult();
        String preMsg = "生成OD数据:";
        
        try {
            map = odbo.generateOD();
        } catch (Exception e) {
            if(e.getMessage().indexOf("ORA-00001")>-1){
                am = new ActionMessage("operMessage", FrameUtil.GbkToIso("请勿1小时内重复生成数据！"));
                util.logOperation(FrameDBConstant.COMMAND_SUBMIT,request,preMsg + e.getMessage());
                return am;
            }else{
                am = new ActionMessage("operMessage", FrameUtil.GbkToIso(preMsg + "失败！" + e.getMessage()));
                util.logOperation(FrameDBConstant.COMMAND_SUBMIT,request,preMsg + e.getMessage());
                return am;
            }
        }
        
        util.logOperation(FrameDBConstant.COMMAND_SUBMIT,request,preMsg+FrameDBConstant.OPERATIION_SUCCESS_LOG_MESSAGE);
        am = new ActionMessage("queryMessage",FrameUtil.GbkToIso(preMsg + "成功生成" + map.getUpdateNum() + "条记录！"));
        
        return am;
    }
    
}
