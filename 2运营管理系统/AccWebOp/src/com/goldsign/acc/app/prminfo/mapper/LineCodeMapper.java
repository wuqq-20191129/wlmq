package com.goldsign.acc.app.prminfo.mapper;

import com.goldsign.acc.app.prminfo.entity.LineCode;
import com.goldsign.acc.app.prminfo.entity.PrmVersion;
import java.util.List;
import java.util.Map;

/**
 * @author xiaowu
 * @version 20170608
 */
public interface LineCodeMapper {
    //查询线路信息
    public List<LineCode> getLineCodes(LineCode lineCode);
    
    public List<LineCode> getLineCodesLikeName(LineCode lineCode);

    public int addLineCode(LineCode lineCode);

    public int modifyLineCode(LineCode lineCode);

    public List<LineCode> getLineCodeById(LineCode lineCode);

    public int deleteLineCode(LineCode lineCode);

    public int submitToOldFlag(LineCode lineCode);

    public int submitFromDraftToCurOrFur(LineCode lineCode);

    public int deleteLineCodeForClone(LineCode lineCode);

    public int cloneFromCurOrFurToDraft(LineCode lineCode);
    
    public List<Map> queryToMap(LineCode queryCondition);
}
