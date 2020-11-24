/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.prmdev.mapper;

import com.goldsign.acc.app.prmdev.entity.ACCDevice;
import java.util.List;

/**
 *
 * @author hejj
 */
public interface ACCDeviceMapper {

    public List<ACCDevice> getACCDevices(ACCDevice accDevice);

    public int addACCDevice(ACCDevice accDevice);

    public int modifyACCDevice(ACCDevice accDevice);

    public List<ACCDevice> getACCDeviceById(ACCDevice accDevice);

    public int deleteACCDevice(ACCDevice accDevice);

    public int submitToOldFlag(ACCDevice accDevice);

    public int submitFromDraftToCurOrFur(ACCDevice accDevice);

    public int deleteACCDevicesForClone(ACCDevice accDevice);

    public int cloneFromCurOrFurToDraft(ACCDevice accDevice);
}
