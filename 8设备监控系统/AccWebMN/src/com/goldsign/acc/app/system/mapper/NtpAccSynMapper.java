package com.goldsign.acc.app.system.mapper;

import com.goldsign.acc.app.system.entity.NtpAccSyn;
import java.util.List;

public interface NtpAccSynMapper {

    public List<NtpAccSyn> getNtpAccSynQuery();

    public int update(NtpAccSyn vo);

    public int insertHis(NtpAccSyn vo);

    public int insertCur(NtpAccSyn vo);
}