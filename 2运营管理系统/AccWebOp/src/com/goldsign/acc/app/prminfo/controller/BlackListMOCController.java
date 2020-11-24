package com.goldsign.acc.app.prminfo.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.goldsign.acc.app.prminfo.entity.BlackListMOC;
import com.goldsign.acc.app.prminfo.mapper.BlackListMOCMapper;
import com.goldsign.acc.app.querysys.controller.CodePubFlagController;
import com.goldsign.acc.app.querysys.entity.CodePubFlag;
import com.goldsign.acc.frame.constant.CommandConstant;
import com.goldsign.acc.frame.constant.WebConstant;
import com.goldsign.acc.frame.mapper.OperationLogMapper;
import com.goldsign.acc.frame.util.ExpUtil;
import com.goldsign.acc.frame.util.FormUtil;
import com.goldsign.acc.frame.util.LogUtil;
import com.goldsign.acc.frame.vo.OperationResult;

/**
 * @desc:交通部一卡通黑名单
 * @author:zhongziqi
 * @create 2018年2月7日
*/
@Controller
public class BlackListMOCController extends CodePubFlagController{
	@Autowired
    private BlackListMOCMapper mapper;

    @RequestMapping("/BlackListMOC")
    public ModelAndView service(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView("/jsp/prminfo/black_list_moc.jsp");
        String command = request.getParameter("command");
        OperationResult opResult = new OperationResult();
        try {
            if (command != null) {
                command = command.trim();
                if (command.equals(CommandConstant.COMMAND_QUERY))
                {
                    opResult = this.query(request, this.mapper, this.operationLogMapper);
                }
            }
            if (command == null) {
                opResult = this.queryNum(request, this.mapper, this.operationLogMapper);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.baseHandler(request, response, mv);
        this.setResultText(mv,opResult,command);
        this.divideResultSet(request, mv, opResult);
        this.SaveOperationResult(mv, opResult);
        this.getDistinctOperatorId(mv);
        return mv;
    }

    private void setResultText(ModelAndView mv, OperationResult opResult, String command) {
		// TODO Auto-generated method stub
    	List<BlackListMOC> resultSet = (List<BlackListMOC>) opResult.getReturnResultSet();
        List<CodePubFlag> blackResons = this.getBlackReson();
        mv.addObject("blackResons", blackResons);
        if(resultSet==null ||resultSet.isEmpty()){
            return ;
        }
        if (command == null || command.equals(CommandConstant.COMMAND_NEXT)
				|| command.equals(CommandConstant.COMMAND_NEXTEND) || command.equals(CommandConstant.COMMAND_BACK)
				|| command.equals(CommandConstant.COMMAND_BACKEND)||command.equals(CommandConstant.COMMAND_GOPAGE)) {
			return;

		}

         for (BlackListMOC vo : resultSet) {
             if (blackResons != null && !blackResons.isEmpty()) {
                 vo.setBlackTypeText(getTextByCode(vo.getBlackType(), blackResons));
             }
         }
	}

	private void getDistinctOperatorId(ModelAndView mv) {
        List<BlackListMOC> operatorIds = mapper.getDistinctOperatorId();
        mv.addObject("operatorIds", operatorIds);

    }

	private OperationResult query(HttpServletRequest request, BlackListMOCMapper mapper, OperationLogMapper operationLogMapper) throws Exception {
        OperationResult or = new OperationResult();
        LogUtil logUtil = new LogUtil();
        BlackListMOC queryCondition;
        List<BlackListMOC> resultSet;
        try {
            queryCondition = this.getQueryCondition(request);
            resultSet = mapper.getBlackListMOC(queryCondition);
            or.setReturnResultSet(resultSet);
            or.addMessage("成功查询" + resultSet.size() + "条记录");
        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, operationLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + resultSet.size() + "条记录", operationLogMapper);
        return or;
    }

    private BlackListMOC getQueryCondition(HttpServletRequest request) {
        BlackListMOC qCon = new BlackListMOC();
        qCon.setCardLogicalId(FormUtil.getParameter(request, "q_cardLogicalId"));
        String beginTime = FormUtil.getParameter(request, "q_beginDatetime");
        String endTime = FormUtil.getParameter(request, "q_endDatetime");
//        if (!"".equals(beginTime)) {
//            beginTime = beginTime + " 00:00:00";
//        }
//        if (!"".equals(endTime)) {
//            endTime = endTime + " 23:59:59";
//        }
        qCon.setBlackType(FormUtil.getParameter(request, "q_blackType"));
        qCon.setOperatorId(FormUtil.getParameter(request, "q_operatorId"));
        qCon.setBeginEffectiveTime(beginTime);
        qCon.setEndEffectiveTime(endTime);
        return qCon;
    }

    public static String getTextByCode(String code, List tableFlags) {
        CodePubFlag pv = null;
        for (int i = 0; i < tableFlags.size(); i++) {
            pv = (CodePubFlag) tableFlags.get(i);
            if (pv.getCode().equals(code)) {
                return pv.getCodeText();
            }
        }
        return code;
    }

    private OperationResult queryNum(HttpServletRequest request, BlackListMOCMapper mapper, OperationLogMapper operationLogMapper) throws Exception {
        OperationResult or = new OperationResult();
        LogUtil logUtil = new LogUtil();
        int n = 0;
        try {
            n = mapper.queryNum();
            or.addMessage("交通部一卡通黑名单累计已有" + n + "条记录");
        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, operationLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + n + "条记录", operationLogMapper);
        return or;
    }
    @RequestMapping("/BlackListMOCExportAll")
    public void exportExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {
        List results = this.getBufferElementsForCurClass(request,BlackListMOC.class.getName());
        String expAllFields = request.getParameter("expAllFields");
        List<Map<String, Object>> queryResults = ExpUtil.entityToMap(expAllFields,results);
        ExpUtil.renderExportExcelPath(response, ExpUtil.exportExcel(queryResults, request));
    }


}
