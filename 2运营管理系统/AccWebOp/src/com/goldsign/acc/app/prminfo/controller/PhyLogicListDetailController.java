package com.goldsign.acc.app.prminfo.controller;

import com.goldsign.acc.app.prminfo.entity.PhyLogicList;
import com.goldsign.acc.app.prminfo.mapper.PhyLogicListMapper;
import com.goldsign.acc.frame.constant.CommandConstant;
import com.goldsign.acc.frame.controller.PrmBaseController;
import com.goldsign.acc.frame.entity.PubFlag;
import com.goldsign.acc.frame.mapper.OperationLogMapper;
import com.goldsign.acc.frame.util.DBUtil;
import com.goldsign.acc.frame.util.FormUtil;
import com.goldsign.acc.frame.util.LogUtil;
import com.goldsign.acc.frame.vo.OperationResult;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author xiaowu
 * 物理逻辑卡号对照表  查看操作
 */
@Controller
public class PhyLogicListDetailController extends PrmBaseController{
    
    @Autowired
    private PhyLogicListMapper phyLogicListMapper;


    @RequestMapping("/PhyLogicListDetail")
    public ModelAndView service(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView("/jsp/prminfo/phy_logic_list_detail.jsp");
        OperationResult opResult = new OperationResult();
        try {
            opResult = this.query(request, this.phyLogicListMapper, this.operationLogMapper);
        } catch (Exception e) {
            e.printStackTrace();
        }

        String[] attrNames = {OPERATORS};
        
        this.setPageOptions(attrNames, mv, request, response);//设置页面操作员选项值
        this.getResultSetText((List<PhyLogicList>) opResult.getReturnResultSet(), mv);
        this.baseHandler(request, response, mv);
        this.SaveOperationResult(mv, opResult);//返回结果集
        return mv;

    }
    
    private void getResultSetText(List<PhyLogicList> resultSet, ModelAndView mv) {
    }

    public OperationResult query(HttpServletRequest request, PhyLogicListMapper voMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult or = new OperationResult();
        LogUtil logUtil = new LogUtil();
        PhyLogicList phyLogicList;
        List<PhyLogicList> resultSet;

        try {
            phyLogicList = this.getQueryCondition(request);
            resultSet = voMapper.getPhyLogicLists(phyLogicList);
            or.setReturnResultSet(resultSet);
            or.addMessage("成功查询" + resultSet.size() + "条记录");
        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + resultSet.size() + "条记录", opLogMapper);
        return or;

    }
    
     private PhyLogicList getQueryCondition(HttpServletRequest request) {
        PhyLogicList phyLogicList = new PhyLogicList();
        if(FormUtil.getParameter(request, "waterNo")!=null){
            phyLogicList.setWater_no(FormUtil.getParameter(request, "waterNo"));
        }
        System.out.println(FormUtil.getParameter(request, "waterNo"));
        return phyLogicList;
    }
}
