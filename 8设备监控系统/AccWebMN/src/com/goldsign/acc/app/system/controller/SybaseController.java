package com.goldsign.acc.app.system.controller;

import com.goldsign.acc.app.system.entity.Sybase;
import com.goldsign.acc.app.system.mapper.SybaseMapper;
import com.goldsign.acc.frame.constant.CommandConstant;
import com.goldsign.acc.frame.constant.FrameDBConstant;
import com.goldsign.acc.frame.controller.BaseController;
import com.goldsign.acc.frame.entity.PubFlag;
import com.goldsign.acc.frame.mapper.OperationLogMapper;
import com.goldsign.acc.frame.util.DBUtil;
import com.goldsign.acc.frame.util.ExpUtil;
import com.goldsign.acc.frame.util.LogUtil;
import com.goldsign.acc.frame.vo.OperationResult;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
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
 * ORACLE监视
 *
 * @author xiaowu
 */
@Controller
public class SybaseController extends BaseController {

    @Autowired
    private SybaseMapper mapper;

    @RequestMapping(value = "/sybase")
    public ModelAndView service(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView("/jsp/system/sybase.jsp");
        OperationResult opResult = new OperationResult();
        try {
            opResult = this.queryPlan(request, this.mapper, this.operationLogMapper);
            getResultSetText(opResult.getReturnResultSet(), mv);
        } catch (Exception e) {
            e.printStackTrace();
            opResult.addMessage(e.getMessage());
        }
        this.baseHandler(request, response, mv);
        this.divideResultSet(request, mv, opResult); //结果集分页
        this.SaveOperationResult(mv, opResult); //返回结果集
        return mv;
    }

    @RequestMapping("/sybaseExportAll")
    public void exportExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {
        List results = this.getBufferElements(request);
        String expAllFields = request.getParameter("expAllFields");
        List<Map<String, Object>> queryResults = ExpUtil.entityToMap(expAllFields, results);
        ExpUtil.renderExportExcelPath(response, ExpUtil.exportExcel(queryResults, request));
    }

    private void getResultSetText(List<Sybase> resultSet, ModelAndView mv) throws Exception {
        List<PubFlag> status = pubFlagMapper.getCodeByType(2);
        String capacityConfig = mapper.queryCapacityConfig();
        NumberFormat nf = NumberFormat.getPercentInstance();
        for (Sybase sd : resultSet) {
            if (sd.getUsed_index_rate() != null && capacityConfig != null && nf.parse(capacityConfig + "%").doubleValue() - nf.parse(sd.getUsed_index_rate()).doubleValue() <= 0) {
                sd.setRedOr("1");   //如果大于capacityConfig，设置为1，显示为红色
            } else {
                sd.setRedOr("0");
            }
            //add by zhongzq 20191021
            if (sd.getRecovery_used_pct() != null && sd.getRecovery_used_pct() != null && nf.parse(FrameDBConstant.RECOVERY_USE_SPACE_WARNING_VALUE + "%").doubleValue() - nf.parse(sd.getRecovery_used_pct()).doubleValue() <= 0) {
                sd.setRedOrForRecovery("1");   //如果大于recoveryWaring，设置为1，显示为红色
            } else {
                sd.setRedOrForRecovery("0");
            }
            if (status != null && !status.isEmpty()) {
                sd.setStatus_text(DBUtil.getTextByCode(sd.getStatus(), status));
            }
        }
    }

    public OperationResult queryPlan(HttpServletRequest request, SybaseMapper mapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult or = new OperationResult();
        LogUtil logUtil = new LogUtil();
        List<Sybase> resultSet;
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

    public int addStatus(Sybase vo) throws Exception {
        TransactionStatus status = null;
        int n = 0;
        try {
            status = txMgr.getTransaction(this.def);
            n = mapper.updatePlan(vo);
            if (n != 1) {
                mapper.insertPlanOne(vo);
            }
            mapper.insertPlanTwo(vo);
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

    public void addStatuses(List<Sybase> vos) {
        try {
            for (int i = 0; i < vos.size(); i++) {
                Sybase vo = (Sybase) vos.get(i);
                int n = mapper.updatePlanField(vo);
                if (n != 1) {
                    mapper.insertPlanOneField(vo);
                }
                mapper.insertPlanTwoField(vo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //获取数据库的存储空间使用状态
    public Vector<Sybase> getDbMessageList() {
        TransactionStatus status = null;
        Vector<Sybase> vector = new Vector<>();
        Map resultMap = new HashMap();
        try {
            status = txMgr.getTransaction(this.def);
            mapper.getDbMessageList(resultMap);
            ArrayList<Sybase> arrayList = (ArrayList<Sybase>) resultMap.get("result");
//            System.out.println("SybaseList = " + arrayList.size());
            if (arrayList != null && arrayList.size() > 0) {
                for (Sybase vo : arrayList) {
                    vector.add(vo);
                }
            }
            txMgr.commit(status);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return vector;
    }

    //add by zhongzq 20190903
    public Sybase getDbMessage(String tablespaceName) {
        TransactionStatus status = null;
        Sybase sybase = new Sybase();
        try {
            status = txMgr.getTransaction(this.def);
            sybase = mapper.getDbMessage(tablespaceName);

            txMgr.commit(status);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sybase;
    }

    public String queryCapacityConfig() {
        String capacity = mapper.queryCapacityConfig();
        return capacity;
    }

    public List<PubFlag> getTableSpaceList() {
        List spcaceList = mapper.getTableSpaceNameList();
        return spcaceList;
    }

    public Sybase getRecoveryMsg() {
        Sybase vo = mapper.getRecoveryMsg();
        return vo;
    }
}
