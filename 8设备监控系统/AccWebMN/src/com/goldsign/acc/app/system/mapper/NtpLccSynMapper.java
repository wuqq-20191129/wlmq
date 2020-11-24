package com.goldsign.acc.app.system.mapper;

import com.goldsign.acc.app.system.entity.NtpLccSyn;
import java.util.List;

public interface NtpLccSynMapper {
    public List<NtpLccSyn> getNtpLccSynQuery();

    public int update(NtpLccSyn vo);

    public int insertCur(NtpLccSyn vo);

    public int insertHis(NtpLccSyn vo);
    
    
}