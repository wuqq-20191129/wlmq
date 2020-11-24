package com.goldsign.acc.app.opma.mapper;

import com.goldsign.acc.app.opma.entity.NonReturnApplyHandle;
import com.goldsign.acc.app.opma.entity.NonReturnApplyHandleHis;
import com.goldsign.acc.app.querysys.entity.CodePubFlag;
import java.util.List;
import java.util.Map;

public interface NonReturnApplyHandleMapper {

    public List<NonReturnApplyHandle> getNonReturnApplyQuery(NonReturnApplyHandle queryCondition);

    public List<CodePubFlag> getIdTypes();

    public String isApplied(NonReturnApplyHandle vo);

    public String getPenatly(Map<String, Object> map);

    public String isBlackCard(NonReturnApplyHandle vo);

    public String checkInBlackListMtr(NonReturnApplyHandle vo);

    public String checkInBlackListMtrSec(NonReturnApplyHandle vo);

    public String getDeposit(NonReturnApplyHandle vo);

    public NonReturnApplyHandle getBalance(NonReturnApplyHandle vo);

    public NonReturnApplyHandleHis getHisBalance(NonReturnApplyHandle vo);

    public int confirmRefundOrNot(NonReturnApplyHandle vo);

    public int confirmAudit(NonReturnApplyHandle vo);

    public int confirmModify(NonReturnApplyHandle vo);

    public NonReturnApplyHandle getNonReturnApplyQueryByBusinessReciptId(String businessReceiptId);

	public List<Map> queryToMap(NonReturnApplyHandle queryCondition);


}