/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.prmdev.mapper;

import com.goldsign.acc.app.prmdev.entity.StationDevice;
import java.util.List;

/**
 *
 * @author hejj
 */
public interface StationDeviceMapper {

    public List<StationDevice> getStationDevices(StationDevice stationDevice);

    public int addStationDevice(StationDevice stationDevice);
    
    public int importStationDevice(StationDevice stationDevice);

    public int modifyStationDevice(StationDevice stationDevice);

    public List<StationDevice> getStationDeviceById(StationDevice stationDevice);

    public int deleteStationDevice(StationDevice stationDevice);

    public int submitToOldFlag(StationDevice stationDevice);

    public int submitFromDraftToCurOrFur(StationDevice stationDevice);

    public int deleteStationDevicesForClone(StationDevice stationDevice);

    public int cloneFromCurOrFurToDraft(StationDevice stationDevice);
}
