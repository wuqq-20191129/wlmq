/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.goldsign.acc.app.opma.controller;

import com.goldsign.acc.app.opma.entity.CodPubFlag;
import com.goldsign.acc.app.opma.entity.SysGroup;
import com.goldsign.acc.app.opma.entity.SysGroupModuleKey;
import com.goldsign.acc.app.opma.entity.SysModule;
import com.goldsign.acc.frame.constant.CommandConstant;
import com.goldsign.acc.frame.controller.BaseController;
import com.goldsign.acc.frame.vo.OperationResult;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import com.goldsign.acc.app.opma.mapper.CodPubFlagMapper;
import com.goldsign.acc.app.opma.mapper.SysGroupMapper;
import com.goldsign.acc.app.opma.mapper.SysGroupModuleMapper;
import com.goldsign.acc.app.opma.mapper.SysModuleMapper;
import com.goldsign.acc.frame.constant.TypeConstant;

/**
 * @author  刘粤湘
 * @date    2017-6-14 17:15:52
 * @version V1.0
 * @desc  权限分配
 */
@Controller
public class DistrController extends BaseController{
    private static Logger logger = Logger.getLogger(DistrController.class.getName());
    
    @Autowired
    SysGroupMapper sysGrpMapper;
    
    @Autowired
    CodPubFlagMapper codPubFlagMapper;
    
    @Autowired
    SysModuleMapper moduleMapper;
    
    @Autowired
    SysGroupModuleMapper  grpModuleMapper;
    
     @RequestMapping("/distr")
    public ModelAndView distr(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView("/jsp/opma/distr.jsp");
        OperationResult opResult = new OperationResult();
        String currentGroupID = request.getParameter("currentGroupID");
        String sysFlagID = request.getParameter("sysFlagID");
        if(sysFlagID !=null && sysFlagID.trim().equals("")){
            sysFlagID = null;
        }
        if(currentGroupID !=null && currentGroupID.trim().equals("")){
            currentGroupID = null;
        }
        String allSelectedIDs = request.getParameter("allSelectedIDs");
        String[] strModuleIds = request.getParameterValues("ids");
        System.out.println(strModuleIds);
        String command = request.getParameter("command");
         try {
             //开始分配权限
             if(/*strModuleIds!=null &&*/ command !=null && command.equals("distribute")){
                 this.distrModules(currentGroupID,sysFlagID,strModuleIds);
             }
             //组别
             List<SysGroup> sysGrps = sysGrpMapper.getAll();
             opResult.setReturnResultSet(sysGrps);
             if (currentGroupID == null) {
                 currentGroupID = sysGrps.get(0).getSysGroupId();
             }

             //系统类型
             Map<String, Object> map = new HashMap();
             map.put("type", TypeConstant.TYPE_SYS);
             List<CodPubFlag> sysTypes = codPubFlagMapper.find(map);
             mv.addObject("ResultSet1", sysTypes);
             if (sysFlagID == null) {
                 sysFlagID = sysTypes.get(0).getCode();
             }

             SysModule sysModule = new SysModule();
             sysModule.setSysFlag(sysFlagID);

             List<SysModule> sysModules = moduleMapper.qModulesByCon(sysModule);
             List<SysModule> sysModulesPage = new ArrayList();
             
             //通过组ID和系统ID查找组有这个系统的权限
             if (currentGroupID != null) {
                 map = new HashMap();
                 map.put("sysGroupId", currentGroupID.trim());
                 List<SysGroupModuleKey> grpModules = grpModuleMapper.findModuleByGroupId(map);
                 
                 for(SysModule sysModuleTmp :sysModules){
                     for(SysGroupModuleKey grpModuleKey:grpModules){
                         if(grpModuleKey.getModuleId().equals(sysModuleTmp.getModuleId())){
                             sysModuleTmp.setChecked("checked");
                         }
                         
                     }
                     sysModulesPage.add(sysModuleTmp);
                 }
             }
             mv.addObject("ResultSet2", sysModulesPage);
             mv.addObject("currentGroupID", currentGroupID);
             mv.addObject("sysFlagID", sysFlagID);
             
             //request.setAttribute("currentGroupID", currentGroupID);
             this.baseHandler(request, response, mv);
             if(command!=null && command.equals(CommandConstant.COMMAND_DISTRIBUTE)){
                 opResult.addMessage("分配成功");
             }else{
                 opResult.addMessage("操作成功");
             }
             
             this.SaveOperationResult(mv, opResult);
         } catch (Exception e) {
              logger.error(e.getMessage());
         }
       

        return mv;
    }

    private void distrModules(String currentGroupID, String sysFlagID, String[] strModuleIds) {
        //查出系统有的模块
        SysModule sysModule = new SysModule();
        sysModule.setSysFlag(sysFlagID);
        List<SysModule> sysModules = moduleMapper.qModulesByCon(sysModule);
        
        //查询出组有的权限模块ID
        Map<String,Object> map = new HashMap();
        map.put("sysGroupId", currentGroupID.trim());
        List<SysGroupModuleKey> grpModules = grpModuleMapper.findModuleByGroupId(map);
        
        //然后咔嚓调该系统下该组有的模块
         for (SysModule sysModuleTmp : sysModules) {
            for (SysGroupModuleKey grpModuleKey : grpModules) {
                if (grpModuleKey.getModuleId().equals(sysModuleTmp.getModuleId())) {                    
                    grpModuleMapper.deleteByPrimaryKey(grpModuleKey);
                }
            }
        }
        
         
        if (strModuleIds != null) {
            //然后录入分配的权限
            for (String id : strModuleIds) {
                SysGroupModuleKey grpModuleKey = new SysGroupModuleKey();
                grpModuleKey.setSysGroupId(currentGroupID);
                grpModuleKey.setModuleId(id);
                grpModuleMapper.insert(grpModuleKey);
            }
        }        
    }

}
