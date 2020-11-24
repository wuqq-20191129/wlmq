package com.goldsign.acc.app.system.mapper;

import com.goldsign.acc.app.system.entity.NtpPcSyn;
import java.util.List;

public interface NtpPcSynMapper {

    public List<NtpPcSyn> getNtpPcSynQuery();

    public int update(NtpPcSyn vo);

    public int insertCur(NtpPcSyn vo);

    public int insertHis(NtpPcSyn vo);
}