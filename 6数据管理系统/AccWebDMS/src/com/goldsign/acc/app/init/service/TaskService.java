package com.goldsign.acc.app.init.service;

import com.goldsign.acc.app.constant.AppCodeConstant;
import com.goldsign.acc.app.init.mapper.TaskMapper;
import com.goldsign.acc.app.sysconfig.entity.TaskConfig;
import com.goldsign.acc.app.sysconfig.entity.TaskLog;
import com.goldsign.acc.app.sysconfig.mapper.TaskConfigMapper;
import com.goldsign.acc.app.sysconfig.mapper.TaskLogMapper;
import com.goldsign.acc.frame.entity.PubFlag;
import com.goldsign.acc.frame.util.PubUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @desc:
 * @author:zhongzqi
 * @create date: 2017-12-7
 */
@Service
@Scope("prototype")
public class TaskService extends BaseService {

    private final static Logger logger = Logger.getLogger(TaskService.class);
    /**
     * 结果描述
     */
    public final static Map<Integer, String> RESULT_DESC_MAP = new HashMap();
    public static final String RETURN_CODE = "out_retResult";
    public static final String RETURN_MESSAGE = "out_msg";

    static {
        RESULT_DESC_MAP.put(Integer.valueOf(0), "执行成功");
        RESULT_DESC_MAP.put(Integer.valueOf(1), "执行失败");
        RESULT_DESC_MAP.put(Integer.valueOf(2), "正在执行");
    }
    @Autowired
    private TaskMapper taskMapper;
    @Autowired
    private TaskLogMapper taskLogMapper;
    @Autowired
    private TaskConfigMapper taskConfigMapper;
    /**
     * 执行状态
     */
    private final static List<PubFlag> TASK_STATUS = new ArrayList<PubFlag>();

    static {
        PubFlag vo1 = new PubFlag();
        vo1.setCode("0");
        vo1.setCode_text("待执行");
        PubFlag vo2 = new PubFlag();
        vo2.setCode("1");
        vo2.setCode_text("执行中");
        TASK_STATUS.add(vo1);
        TASK_STATUS.add(vo2);
    }

    /**
     * 运行结果
     */
    private final static List<PubFlag> TASK_RESULT = new ArrayList<PubFlag>();

    static {
        PubFlag vo3 = new PubFlag();
        vo3.setCode("0");
        vo3.setCode_text("正常结束");
        PubFlag vo4 = new PubFlag();
        vo4.setCode("1");
        vo4.setCode_text("异常结束");
        PubFlag vo5 = new PubFlag();
        vo5.setCode("2");
        vo5.setCode_text("线程挂起");
        TASK_RESULT.add(vo3);
        TASK_RESULT.add(vo4);
        TASK_RESULT.add(vo5);
    }

    public Map<String, Object> doTask(String prcName, String prcType)
            throws Exception {
        HashMap<String, Object> hmParm = new HashMap(10);
        String sql = "";
        sql = prcName;
        String retCode = "0";
        String retMsg = "";
        if (AppCodeConstant.TYPE_RESULT.equals(prcType)) {
            sql = sql + "(#{out_retResult,mode=OUT,jdbcType=INTEGER},#{out_msg,mode=OUT,jdbcType=VARCHAR})";
            hmParm.put(RETURN_CODE, "");
            hmParm.put(RETURN_MESSAGE, "");
        } else if (AppCodeConstant.TYPE_NO_RESULT.equals(prcType)) {
            sql = sql + "()";
        }
        hmParm.put("sql", sql);
        doTaskByTrans(hmParm);
        if (AppCodeConstant.TYPE_RESULT.equals(prcType)) {
            retCode = hmParm.get("out_retResult").toString();
            Object outMsg = hmParm.get("out_msg");
            if (null != outMsg) {
                retMsg = outMsg.toString();
            }
        }
        logger.info("执行" + prcName + "，返回码：" + retCode + ",返回信息：" + retMsg);
        return hmParm;
    }

    public int queryTaskStatus(String taskName)
            throws Exception {
        TaskConfig vo = new TaskConfig();
        vo.setTaskName(taskName.trim());
        return this.queryTaskStatusByTrans(vo);
    }

    public void updateTaskStatus(String taskName, int status) {
        try {
            TaskConfig vo = new TaskConfig();

            vo.setTaskName(taskName);
            vo.setStatus(status);
            int n = 0;
            n = updateTaskStatusByTrans(vo);
        } catch (Exception e) {
            logger.error("exception", e);
        }
    }

    public int inserTaskBeginInfo(String taskName, int flag) throws Exception {
        int waterNo = taskLogMapper.getWaterNo();
        logger.info("任务" + taskName + "取得流水号：" + waterNo);
        TaskLog vo = new TaskLog();
        //WATER_NO,  TASK_NAME,  TASK_DESC,  START_TIME,  END_TIME,  TASK_RESULT,  RESULT_DESC
        vo.setWaterNo(waterNo);
        vo.setTaskName(taskName);
        TaskConfig po = (TaskConfig) TaskConfigService.getConfigMaps().get(taskName);
        vo.setTaskDesc(po.getRemark());
        vo.setTaskResult(flag);
        vo.setResultDesc(RESULT_DESC_MAP.get(flag));
        inserTaskBeginInfoByTrans(vo);
        return waterNo;
    }

    public void updateTaskBeginInfo(int waterNo, int flag, String taskDesc) throws Exception {
        TaskLog vo = new TaskLog();
        //op_dm_task_log set END_TIME=sysdate,TASK_RESULT=?,RESULT_DESC=?  where WATER_NO=? 
        vo.setWaterNo(waterNo);
        vo.setResultDesc(taskDesc);
        vo.setTaskResult(flag);
//        vo.setResultDesc(RESULT_DESC_MAP.get(flag));
        updateTaskBeginInfoBytrans(vo);
    }

    private int updateTaskStatusByTrans(TaskConfig vo) throws Exception {
        TransactionStatus status = null;
        int n = 0;
        try {
            status = txMgr.getTransaction(this.def);
            n = taskConfigMapper.updateTaskStatus(vo);
            txMgr.commit(status);
        } catch (Exception e) {
            PubUtil.handExceptionForTran(e, txMgr, status);
        }
        return n;
    }

    private int queryTaskStatusByTrans(TaskConfig vo) {
        int status = 0;
        try {
            List<TaskConfig> result = taskConfigMapper.queryTaskStatus(vo);
            if (!result.isEmpty()) {
                status = result.get(0).getStatus();
            }
        } catch (Exception e) {
            logger.error("查询过程发生异常:", e);
        }
        return status;
    }

    private int inserTaskBeginInfoByTrans(TaskLog vo) throws Exception {
        TransactionStatus status = null;
        int n = 0;
        try {
            status = txMgr.getTransaction(this.def);
            n = taskLogMapper.addTaskLog(vo);
            txMgr.commit(status);
        } catch (Exception e) {
            PubUtil.handExceptionForTran(e, txMgr, status);
        }
        return n;

    }

    private int updateTaskBeginInfoBytrans(TaskLog vo) throws Exception {
        TransactionStatus status = null;
        int n = 0;
        try {
            status = txMgr.getTransaction(this.def);
            n = taskLogMapper.modifyTaskLog(vo);
            txMgr.commit(status);
        } catch (Exception e) {
            PubUtil.handExceptionForTran(e, txMgr, status);
        }
        return n;
    }

    private void doTaskByTrans(HashMap<String, Object> hmParm) throws Exception {
        TransactionStatus status = null;
        int n = 0;
        try {
            status = txMgr.getTransaction(this.def);
            taskMapper.doTask(hmParm);
            txMgr.commit(status);
        } catch (Exception e) {
            PubUtil.handExceptionForTran(e, txMgr, status);
        }
    }
}
