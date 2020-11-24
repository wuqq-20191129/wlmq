package com.goldsign.acc.app.opma.mapper;

import com.goldsign.acc.app.opma.entity.ReportDataSoure;
import com.goldsign.acc.app.prminfo.entity.BlackListMtr;
import java.util.List;

public interface ReportDataSoureMapper {

    public List<ReportDataSoure> getReportDataSourceList(ReportDataSoure vo);

    public int addWithModel(ReportDataSoure vo);

    public int updateWithModel(ReportDataSoure vo);
    
     public int deleteByDsId(ReportDataSoure vo);
}