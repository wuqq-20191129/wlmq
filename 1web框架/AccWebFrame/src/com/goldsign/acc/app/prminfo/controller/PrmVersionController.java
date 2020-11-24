/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.prminfo.controller;

import com.goldsign.acc.app.prminfo.entity.PrmVersion;
import com.goldsign.acc.app.prminfo.mapper.PrmVersionMapper;

import com.goldsign.acc.frame.controller.PrmBaseController;
import com.goldsign.acc.frame.entity.PubFlag;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author hejj
 */
@Controller
public class PrmVersionController extends PrmBaseController {

    @Autowired
    private PrmVersionMapper prmVersionMapper;

    @RequestMapping("/Versions")
    public ModelAndView service(HttpServletRequest request, HttpServletResponse response) {
        String type = (String) request.getParameter("type");
        String action = (String) request.getParameter("action");
        List<PrmVersion> versions = prmVersionMapper.selectPrmVersionByID(type);
        this.getRecordflagNames(versions);
        
        ModelAndView mv = new ModelAndView("/jsp/prminfo/common_version.jsp");
        mv.addObject("versions", versions);
        mv.addObject("action", action);
        this.baseHandler(request, response, mv);
        return mv;

    }

    private void getRecordflagNames(List<PrmVersion> versions) {
        for (PrmVersion version : versions) {
            PubFlag recordFlagName = pubFlagMapper.getRecordFlags(version.getRecord_flag());
            this.getRecordFlagName(version, recordFlagName);
        }

    }

}
