package com.goldsign.acc.app.prminfo.mapper;

import com.goldsign.acc.app.prminfo.entity.TransferStationCode;
import com.goldsign.acc.frame.entity.PubFlag;
import java.util.List;
import java.util.Map;

/**
 * 线路车站--换乘车站
 * @author xiaowu
 * @version 20170612
 */
public interface TransferStationCodeMapper {
    
    //查询车站信息
    public List<TransferStationCode> getTransferStationCodes(TransferStationCode transferStationCode);

    public int addTransferStationCode(TransferStationCode transferStationCode);

    public int modifyTransferStationCode(TransferStationCode transferStationCode);

    public List<TransferStationCode> getTransferStationCodeById(TransferStationCode transferStationCode);

    public int deleteTransferStationCode(TransferStationCode transferStationCode);

    public int submitToOldFlag(TransferStationCode transferStationCode);

    public int submitFromDraftToCurOrFur(TransferStationCode transferStationCode);

    public int deleteTransferStationCodeForClone(TransferStationCode transferStationCode);

    public int cloneFromCurOrFurToDraft(TransferStationCode transferStationCode);
    
    //查询下拉框线路
    public List<PubFlag> getTransferStationCodeLines(TransferStationCode transferStationCode);
    
    //查询下拉框车站
    public List<PubFlag> getTransferStationCodeStations(TransferStationCode transferStationCode);
    
    public List<Map> queryToMap(TransferStationCode queryCondition);
}
