package com.goldsign.acc.frame.controller;

import com.alibaba.fastjson.JSON;
import com.goldsign.acc.frame.mapper.MonitorStatuMapper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description:
 *
 * @autor: zhongziqi
 * @Date: 2018-06-13
 * @Time: 19:06
 */
@Controller
public class MenuController extends BaseController {
    static Logger logger = Logger.getLogger(MenuController.class);
    @Autowired
    private MonitorStatuMapper monitorStatuMapper;

    @RequestMapping("/menuStatus")
    public void getMenuStatusJson(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<Map> list = getMenuStatus();
        renderList(response, list);
    }

    public List<Map> getMenuStatus() {
        return monitorStatuMapper.getMenuStatus();
    }

    public void renderList(HttpServletResponse response, List<Map> list) throws IOException {
        PrintWriter printWriter = null;
        try {
            String jsonResult = getMeunStatuJson(list);
            printWriter = response.getWriter();
            printWriter.print(jsonResult);
        } catch (IOException ex) {
            logger.info("exception", ex);
            throw ex;
        } finally {
            if (null != printWriter) {
                printWriter.flush();
                printWriter.close();
            }
        }
    }

    public String getMeunStatuJson(List<Map> list) {
        Map<String, Object> data = new HashMap<String, Object>(1);
        data.put("menuStatus", list);
        return JSON.toJSONString(data);
    }

    public void updateMenuStatus(String command) throws Exception {
        Map<String, Object> map = new HashMap<>(2);
        map.put("p_class", command);
        try {
            logger.info("=============更新状态========" + command);
            monitorStatuMapper.updateMenuStatus(map);
            logger.info("=============更新状态结果====" + map.get("p_out"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
