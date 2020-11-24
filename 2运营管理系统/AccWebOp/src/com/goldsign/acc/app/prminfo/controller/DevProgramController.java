/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.goldsign.acc.app.prminfo.controller;


import com.goldsign.acc.app.config.entity.Config;
import com.goldsign.acc.app.config.entity.ConfigKey;
import com.goldsign.acc.app.config.mapper.ConfigMapper;
import com.goldsign.acc.app.opma.entity.CodPubFlag;
import com.goldsign.acc.app.opma.mapper.CodPubFlagMapper;
import com.goldsign.acc.app.prminfo.entity.PrmDevProgram;
import com.goldsign.acc.app.prminfo.entity.PrmVersion;
import com.goldsign.acc.frame.controller.BaseController;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import com.goldsign.acc.app.prminfo.mapper.PrmDevProgramMapper;
import com.goldsign.acc.app.prminfo.mapper.PrmVersionMapper;
import com.goldsign.acc.frame.constant.OpCodeConstant;
import com.goldsign.acc.frame.constant.ParameterConstant;
import com.goldsign.acc.frame.constant.TypeConstant;
import com.goldsign.login.vo.User;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

/**
 * @author  刘粤湘
 * @date    2017-6-16 17:25:13
 * @version V1.0
 * @desc  设备程序参数管理
 */
@Controller
public class DevProgramController extends BaseController {
    private static Logger logger = Logger.getLogger(DevProgramController.class.getName());
    
    @Autowired
    PrmDevProgramMapper prmDevProgramMapper;
    
    @Autowired
    CodPubFlagMapper codPubFlagMapper;
    
    @Autowired
    PrmVersionMapper verMapper;
    
    @Autowired
    private ConfigMapper configMapper;
    
    @RequestMapping("/devProgram")
    public ModelAndView devProgram(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView("/jsp/prminfo/dev_program.jsp");
        //Type=9120&ModuleID=011001
        String type = request.getParameter("Type");
        List<PrmDevProgram> prmDevPros = new ArrayList();
        try{
            prmDevPros = prmDevProgramMapper.selectByType(type);
            mv.addObject("ResultSet", prmDevPros);
            mv.addObject("Type", type);
            this.baseHandler(request, response, mv);
        }catch(Exception e){
            e.printStackTrace();
        }
       
        return mv;
    }
    
    @RequestMapping("/devImp")
    public ModelAndView devImp(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView("/jsp/prminfo/dev_import.jsp");
        String paramTypeId = request.getParameter("paramTypeId");
       
        return mv;
    }
    
    @RequestMapping("/handleFileUpload")   
    public ModelAndView handleFileUpload(HttpServletRequest request, HttpServletResponse response){
        ModelAndView mv = new ModelAndView("/jsp/prminfo/dev_import.jsp");
        TransactionStatus status = null;
        try{
            if(request == null) {
                mv.addObject("msg", "上下文request为空");
                return mv;
            }
            String paramTypeId = request.getParameter("paramTypeId");
            if(paramTypeId == null) {
                mv.addObject("msg", "paramTypeId为空");
                return mv;
            }
            MultipartHttpServletRequest multipartRequest;
            multipartRequest = (MultipartHttpServletRequest) request;
            CommonsMultipartFile file = (CommonsMultipartFile) multipartRequest
                    .getFile("makeFile");

            // 获得文件名：  
            String realFileName = file.getOriginalFilename();
            if(!realFileName.contains(paramTypeId)){
                mv.addObject("msg", "文件名不合法");
                return mv;
            }
//            System.out.println("获得文件名：" + realFileName);
            String versionNo = realFileName.substring(9, 17) + realFileName.substring(18, 20);
            //通过文件名判断草稿参数是否已经导入过            
            PrmDevProgram devPro = new PrmDevProgram();            
            devPro.setParmTypeId(paramTypeId);            
            devPro.setVersionNo(versionNo);
            devPro.setRecordFlag(ParameterConstant.RECORD_FLAG_DRAFT);
            List<PrmDevProgram> prmDevs = prmDevProgramMapper.qDevsByCon(devPro);
            if(prmDevs!=null&&prmDevs.size()>0){
                mv.addObject("msg", "参数已经导入");
                return mv;
            }
            //判断是否已有被删除的参数
             devPro.setRecordFlag(ParameterConstant.RECORD_FLAG_DELETED_CURRENT);
             prmDevs = prmDevProgramMapper.qDevsByCon(devPro);
            if(prmDevs!=null&&prmDevs.size()>0){
                mv.addObject("msg", "已有被删除参数");
                return mv;
            }
            //根据参数类型ID和参数标志设置该参数当前参数为已删除
            PrmDevProgram devPro1 = new PrmDevProgram();
            devPro1.setParmTypeId(paramTypeId);
            devPro1.setRecordFlag(ParameterConstant.RECORD_FLAG_CURRENT);
            devPro1.setRecordFlag1(ParameterConstant.RECORD_FLAG_DELETED_CURRENT);
            //先删除库里有的同个版本类型和删除标识的记录
            devPro1.setVersionNo(versionNo);
            status = txMgr.getTransaction(this.def);
            prmDevProgramMapper.delByCon(devPro1);
            prmDevProgramMapper.updateByCon(devPro1);
            //开始导入参数
            String filePathIn = "";
            //取得存储路径
            Map<String, Object> map = new HashMap();
            map.put("type", TypeConstant.TYPE_SAVE_PATH);
//            List<CodPubFlag> pathS = codPubFlagMapper.find(map);
           ConfigKey key = new ConfigKey();
            key.setType("1");
            key.setTypeSub("10");
            List<Config> configs = configMapper.selectConfigs(key);
            //保存文件
            if (configs != null && file != null) {
                Config config = configs.get(0);
                filePathIn = config.getConfigValue()  + File.separator + realFileName;
                file.transferTo(new File(filePathIn));
            }
            devPro.setFilePath(filePathIn);
            //取得操作员ID
            User user = (User) request.getSession().getAttribute("User");
            String oprId = user.getAccount();
            devPro.setOperatorId(oprId);
            devPro.setAppLineName(OpCodeConstant.APP_LINE_NAME_NO_DOWN);
            devPro.setRecordFlag(ParameterConstant.RECORD_FLAG_CURRENT);
            int i = prmDevProgramMapper.insertSelective(devPro);
            if(i ==1){
                mv.addObject("msg", "导入成功");
            }else{
                mv.addObject("msg", "导入失败");
            }
            //生成参数表记录
            this.intoParaVer(paramTypeId,versionNo,oprId);
            txMgr.commit(status);
        }catch(Exception e){
            e.printStackTrace();
            mv.addObject("msg", e.getMessage());
            if (txMgr != null) {
                txMgr.rollback(status);
            }
        }
        
       
        return mv;
    }

    private void intoParaVer(String paraTypeId,String versionNo,String oprId) {
        SimpleDateFormat sy1=new SimpleDateFormat("yyyy-MM-dd");          
        String versionDate = sy1.format(new Date());
        String deletedFlag = "";       
        String selectaApLineName  = "";
        versionDate = versionDate + " 00:00:00";
        
        //更新当前参数，把当前版本变为历史版本
        PrmVersion prmVersion = new PrmVersion();
        prmVersion.setParm_type_id(paraTypeId);
        prmVersion.setRecord_flag_old(ParameterConstant.RECORD_FLAG_HISTORY);
        prmVersion.setRecord_flag_new(ParameterConstant.RECORD_FLAG_CURRENT);
        int i = verMapper.modifyPrmVersionForSubmit(prmVersion);
        //如果没有更新到新增一条记录
        if(i == 0){
            prmVersion.setVersion_no_new(versionNo);
            prmVersion.setRecord_flag_new(ParameterConstant.RECORD_FLAG_CURRENT);
            prmVersion.setVersion_date(versionDate);
            prmVersion.setOperator_id(oprId);
            prmVersion.setBegin_time_new(versionDate);
            prmVersion.setEnd_time_new(versionDate);
            prmVersion.setApp_line_name("");
            prmVersion.setRemark("");
            verMapper.addPrmVersion(prmVersion); 
        }
    }
}
        

