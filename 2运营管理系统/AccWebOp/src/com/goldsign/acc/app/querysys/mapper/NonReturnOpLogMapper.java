package com.goldsign.acc.app.querysys.mapper;

import com.goldsign.acc.app.querysys.entity.CodePubFlag;
import com.goldsign.acc.app.querysys.entity.NonReturnOpLog;
import java.util.List;
import java.util.Map;

public interface NonReturnOpLogMapper {

    public List<NonReturnOpLog> getNonReturnOpLog(NonReturnOpLog queryCondition);
    
    public List<CodePubFlag> getOperatorName ();
   
    public List<CodePubFlag> getOperationType();
    
    public int insertLog(NonReturnOpLog vo);

	public List<Map> queryToMap(NonReturnOpLog queryCondition);
}