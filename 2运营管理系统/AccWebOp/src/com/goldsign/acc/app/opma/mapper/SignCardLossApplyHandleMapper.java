package com.goldsign.acc.app.opma.mapper;

import com.goldsign.acc.app.opma.entity.SignCardLossApplyHandle;
import com.goldsign.acc.app.opma.entity.SignCardLossApplyHandleHis;
import com.goldsign.acc.app.querysys.entity.CodePubFlag;
import java.util.List;
import java.util.Map;

public interface SignCardLossApplyHandleMapper {

    public List<SignCardLossApplyHandle> getSignCardLossApplyQuery(SignCardLossApplyHandle queryCondition);

    public List<CodePubFlag> getIdTypes();

    public String isApplied(SignCardLossApplyHandle vo);

    public String getPenatly(Map<String, Object> map);

    public String isBlackCard(SignCardLossApplyHandle vo);

    public String checkInBlackListMtr(SignCardLossApplyHandle vo);

    public String checkInBlackListMtrSec(SignCardLossApplyHandle vo);

    public String getDeposit(SignCardLossApplyHandle vo);

    public SignCardLossApplyHandle getBalance(SignCardLossApplyHandle vo);

    public SignCardLossApplyHandleHis getHisBalance(SignCardLossApplyHandle vo);

    public int confirmRefundOrNot(SignCardLossApplyHandle vo);

    public int confirmAudit(SignCardLossApplyHandle vo);

    public int confirmModify(SignCardLossApplyHandle vo);

    public SignCardLossApplyHandle getSignCardLossApplyQueryByBusinessReciptId(String businessReceiptId);

	public List<Map> queryToMap(SignCardLossApplyHandle queryCondition);

}