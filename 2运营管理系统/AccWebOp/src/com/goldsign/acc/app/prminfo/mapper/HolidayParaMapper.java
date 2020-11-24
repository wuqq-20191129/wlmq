/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.prminfo.mapper;

import com.goldsign.acc.app.prminfo.entity.HolidayPara;
import java.util.List;

/**
 *
 * @author mqf
 */
public interface HolidayParaMapper {

    public List<HolidayPara> getHolidayParas(HolidayPara holidayPara);

    public int addHolidayPara(HolidayPara holidayPara);

    public int modifyHolidayPara(HolidayPara holidayPara);

    public List<HolidayPara> getHolidayParaById(HolidayPara holidayPara);

    public int deleteHolidayPara(HolidayPara holidayPara);

    public int submitToOldFlag(HolidayPara holidayPara);

    public int submitFromDraftToCurOrFur(HolidayPara holidayPara);

    public int deleteHolidayParasForClone(HolidayPara holidayPara);

    public int cloneFromCurOrFurToDraft(HolidayPara holidayPara);
}
