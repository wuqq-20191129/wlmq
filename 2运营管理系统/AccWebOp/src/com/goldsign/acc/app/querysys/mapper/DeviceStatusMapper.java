/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.querysys.mapper;

import com.goldsign.acc.app.querysys.entity.DeviceStatus;
import java.util.List;

/**
 *
 * @author zhouy
 * 设备状态查询
 * 20171128
 */
public interface DeviceStatusMapper {
    public List<DeviceStatus> getDeviceStatus(DeviceStatus stationDevice);
}
