/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.frame.controller;

import com.goldsign.acc.frame.entity.PubFlag;
import com.goldsign.acc.frame.util.DateUtil;
import com.goldsign.acc.frame.util.FormUtil;
import com.goldsign.acc.frame.util.PageControlUtil;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author hejj
 */
public class DMSBaseController extends BaseController {

    protected static String TABLETYPES = "tableTypes";//源表类型

    protected void setPageOptions(String[] attrNames, ModelAndView mv, HttpServletRequest request, HttpServletResponse response) {
        List<PubFlag> options;
        for (String attrName : attrNames) {
            if (attrName.equals(TABLETYPES)) {//源表类型  
                options = pubFlagMapper.getCode("71");
                mv.addObject(attrName, options);
                continue;
            }
        }
    }

    protected void setOperatorId(ModelAndView mv, HttpServletRequest request) {
        String operatorId = PageControlUtil.getOperatorFromSession(request);
        mv.addObject("OperatorID", operatorId);
    }

}
