/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.goldsign.acc.app.system.controller;

import com.goldsign.acc.frame.constant.FrameDBConstant;
import com.goldsign.acc.app.system.entity.NtpAccSyn;
import com.goldsign.acc.app.system.mapper.NtpAccSynMapper;
import com.goldsign.acc.frame.constant.CommandConstant;
import com.goldsign.acc.frame.constant.WebConstant;
import com.goldsign.acc.frame.controller.BaseController;
import com.goldsign.acc.frame.entity.PubFlag;
import com.goldsign.acc.frame.mapper.OperationLogMapper;
import com.goldsign.acc.frame.util.DBUtil;
import com.goldsign.acc.frame.util.LogUtil;
import com.goldsign.acc.frame.vo.OperationResult;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.TransactionStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @desc:ACC同步
 * @author:zhongzqi
 * @create date: 2017-9-19
 */
@Controller
public class NtpAccSynController extends BaseController {
private static Logger logger = Logger.getLogger(NtpAccSynController.class);
    @Autowired
    private NtpAccSynMapper mapper;

    @RequestMapping("/NtpAccSyn")
    public ModelAndView service(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView("/jsp/system/ntpAccSyn.jsp");
        String command = request.getParameter("command");
        OperationResult opResult = new OperationResult();
        try {
            opResult = this.query(request, this.mapper, this.operationLogMapper);
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.baseHandler(request, response, mv);
        this.SaveOperationResult(mv, opResult);
        this.setResultText(mv);
        return mv;
    }

    private OperationResult query(HttpServletRequest request, NtpAccSynMapper mapper, OperationLogMapper operationLogMapper) throws Exception {
        OperationResult or = new OperationResult();
        LogUtil logUtil = new LogUtil();
        List<NtpAccSyn> resultSet;
        try {
            resultSet = mapper.getNtpAccSynQuery();
//            this.addStatus(resultSet.get(0));
            or.setReturnResultSet(resultSet);
            or.addMessage("成功查询" + resultSet.size() + "条记录");
        } catch (Exception e) {
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, operationLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + resultSet.size() + "条记录", operationLogMapper);
        return or;
    }

    private void setResultText(ModelAndView mv) {
        List<NtpAccSyn> resultSet = (List<NtpAccSyn>) mv.getModelMap().get(WebConstant.ATT_ResultSet);
        if (resultSet == null || resultSet.isEmpty()) {
            return;
        }
        List<PubFlag> statuesFlags = pubFlagMapper.getCodeByType(FrameDBConstant.TYPE_DEVICE_STATUS_NAME);
        for (NtpAccSyn vo : resultSet) {
            if (statuesFlags != null && !statuesFlags.isEmpty()) {
                vo.setStatusName(DBUtil.getTextByCode(vo.getStatus(), statuesFlags));
            }

        }
    }
    public int addStatus(NtpAccSyn vo) throws Exception {
        TransactionStatus status = null;
        int n = 0;
        try {
            System.out.println("vo====>" + vo.getIp());
            status = txMgr.getTransaction(this.def);
            n = mapper.update(vo);
            if (n != 1) {
                int insertCur = mapper.insertCur(vo);
                System.out.println("insertCur====>" + insertCur);
            }
            int insertHis = mapper.insertHis(vo);
            System.out.println("insertHis====>" + insertHis);
            txMgr.commit(status);
        } catch (Exception e) {
            if (txMgr != null) {
                txMgr.rollback(status);
            }
            e.printStackTrace();
            logger.error("错误:", e);
        } finally {
            return n; 
        }
    }
}
