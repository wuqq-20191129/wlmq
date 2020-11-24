package com.goldsign.acc.app.prminfo.mapper;

import com.goldsign.acc.app.prminfo.entity.IcBcLogicNo;
import java.util.List;

/**
 * @author xiaowu
 * @version 20170627
 */
public interface IcBcLogicNoMapper {
    //取全部已审核的空白卡订单 看逻辑卡号是否在订单中
    public List<IcBcLogicNo> getIcBcLogicNos();
}
