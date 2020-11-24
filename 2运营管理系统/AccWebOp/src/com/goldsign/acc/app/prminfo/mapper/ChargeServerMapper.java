package com.goldsign.acc.app.prminfo.mapper;

import com.goldsign.acc.app.prminfo.entity.ChargeServer;
import java.util.List;
import java.util.Map;

/**
 * 充值终端通讯参数
 * @author xiaowu
 * @version 20170614
 */
public interface ChargeServerMapper {

    public List<ChargeServer> getChargeServers(ChargeServer ChargeServer);
    
    public List<ChargeServer> checkChargeServers(ChargeServer ChargeServer);
    
    public List<ChargeServer> getChargeServersLikeDesc(ChargeServer ChargeServer);

    public int addChargeServer(ChargeServer ChargeServer);

    public int modifyChargeServer(ChargeServer ChargeServer);

    public List<ChargeServer> getChargeServerById(ChargeServer ChargeServer);

    public int deleteChargeServer(ChargeServer ChargeServer);

    public int submitToOldFlag(ChargeServer ChargeServer);

    public int submitFromDraftToCurOrFur(ChargeServer ChargeServer);

    public int deleteChargeServerForClone(ChargeServer ChargeServer);

    public int cloneFromCurOrFurToDraft(ChargeServer ChargeServer);
    
    public List<Map> queryToMap(ChargeServer queryCondition);
}
