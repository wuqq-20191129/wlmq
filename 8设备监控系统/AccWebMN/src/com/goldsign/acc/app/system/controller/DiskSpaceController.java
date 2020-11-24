package com.goldsign.acc.app.system.controller;

import com.goldsign.acc.app.system.entity.DiskSpace;
import com.goldsign.acc.app.system.mapper.DiskSpaceMapper;
import com.goldsign.acc.frame.constant.CommandConstant;
import com.goldsign.acc.frame.controller.BaseController;
import com.goldsign.acc.frame.entity.PubFlag;
import com.goldsign.acc.frame.mapper.OperationLogMapper;
import com.goldsign.acc.frame.util.DBUtil;
import com.goldsign.acc.frame.util.ExpUtil;
import com.goldsign.acc.frame.util.LogUtil;
import com.goldsign.acc.frame.vo.OperationResult;
import java.text.NumberFormat;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.TransactionStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 *  服务器硬盘
 *
 * @author xiaowu
 */
@Controller
public class DiskSpaceController extends BaseController {

    public static String[] style_colors = {"#ffffff", "#f7f7f7"}; //行颜色
    
    @Autowired
    private DiskSpaceMapper mapper;

    @RequestMapping(value = "/diskSpace")
    public ModelAndView service(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView("/jsp/system/diskSpace.jsp");
        OperationResult opResult = new OperationResult();
        try {
            opResult = this.queryPlan(request, this.mapper, this.operationLogMapper);
            getResultSetText(opResult.getReturnResultSet(),mv);
        } catch (Exception e) {
            e.printStackTrace();
            opResult.addMessage(e.getMessage());
        }
        this.baseHandler(request, response, mv);
        this.divideResultSet(request, mv, opResult); //结果集分页
        this.SaveOperationResult(mv, opResult); //返回结果集
        return mv;
    }
    
    @RequestMapping("/diskSpaceExportAll")
    public void exportExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {
        List results = this.getBufferElements(request);
        String expAllFields = request.getParameter("expAllFields");
        List<Map<String, Object>> queryResults = ExpUtil.entityToMap(expAllFields, results);
        ExpUtil.renderExportExcelPath(response, ExpUtil.exportExcel(queryResults, request));
    }

    private void getResultSetText(List<DiskSpace> resultSet, ModelAndView mv) throws Exception{
        List<PubFlag> status = pubFlagMapper.getCode("2");
        String capacityConfig = mapper.queryCapacityConfig();
        NumberFormat nf = NumberFormat.getPercentInstance();
//        String ip = "";
//        String ipOrg = "";
//        int i = 0;
//
//        String color;
//        String style = "";
        for (DiskSpace sd : resultSet) {
//            ip = sd.getIp();
//            if (ip != null && !ip.equals(ipOrg)) {
//                color = style_colors[i];
//                style = this.getStyle(color);
//                ipOrg = ip;
//                i++;
//                i = i % 2;
//            }
//            sd.setStyle(style);
            if(nf.parse(capacityConfig + "%").doubleValue() - nf.parse(sd.getCapacity()).doubleValue() <= 0){
                sd.setRedOr("1");   //如果大于capacityConfig，设置为1，显示为红色
            }else{
                sd.setRedOr("0");
            }
            if (status != null && !status.isEmpty()) {
                sd.setStatus_text(DBUtil.getTextByCode(sd.getStatus(), status));
            }
        }
    }

    public OperationResult queryPlan(HttpServletRequest request, DiskSpaceMapper mapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult or = new OperationResult();
        LogUtil logUtil = new LogUtil();
        List<DiskSpace> resultSet;
        try {
            resultSet = mapper.queryPlan();
            or.setReturnResultSet(resultSet);
            or.addMessage("成功查询" + resultSet.size() + "条记录");
        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + resultSet.size() + "条记录", opLogMapper);
        return or;
    }

    private String getStyle(String color) {
        return "background:" + color;
    }
    
    public int addStatus(Vector vos) throws Exception {
        TransactionStatus status = null;
        int n = 0;
        try {
            DiskSpace vo;
            status = txMgr.getTransaction(this.def);
            for (int i = 0; i < vos.size(); i++) {
                vo = (DiskSpace) vos.get(i);
                n = mapper.updatePlan(vo);
                if (n != 1) {
                    mapper.insertPlanOne(vo);
                }
                mapper.insertPlanTwo(vo);
            }
            txMgr.commit(status);
        } catch (Exception e) {
            if (txMgr != null) {
                txMgr.rollback(status);
            }
            e.printStackTrace();
        } finally {
            return n;
        }
    }
}
