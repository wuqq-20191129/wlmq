package com.goldsign.acc.app.prminfo.mapper;

import com.goldsign.acc.app.prminfo.entity.LogicIccList;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * 逻辑卡号刻印号对照表
 * @author xiaowu
 * @version 20170622
 */
public interface LogicIccListMapper {

    public List<LogicIccList> getLogicIccLists(LogicIccList logicIccList); 

    public int addLogicIccList(LogicIccList logicIccList);

    public int deleteLogicIccList(LogicIccList logicIccList);

}
