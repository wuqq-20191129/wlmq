/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.prminfo.mapper;

import com.goldsign.acc.app.prminfo.entity.OffPeakHours;
import java.util.List;

/**
 *
 * @author mqf
 */
public interface OffPeakHoursMapper {

    public List<OffPeakHours> getOffPeakHourss(OffPeakHours offPeakHours);

    public int addOffPeakHours(OffPeakHours offPeakHours);

    public int modifyOffPeakHours(OffPeakHours offPeakHours);

    public List<OffPeakHours> getOffPeakHoursById(OffPeakHours offPeakHours);

    public int deleteOffPeakHours(OffPeakHours offPeakHours);

    public int submitToOldFlag(OffPeakHours offPeakHours);

    public int submitFromDraftToCurOrFur(OffPeakHours offPeakHours);

    public int deleteOffPeakHourssForClone(OffPeakHours offPeakHours);

    public int cloneFromCurOrFurToDraft(OffPeakHours offPeakHours);
}
