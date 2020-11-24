/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.prminfo.mapper;

import com.goldsign.acc.app.prminfo.entity.GateApproEnterPara;
import java.util.List;
import java.util.Map;

/**
 *闸机专用通道参数
 * @author zhouyang
 * 20170609
 */
public interface GateApproEnterParaMapper {
    
    //根据条件查询记录
    public List<GateApproEnterPara> getGateApproEnterParas(GateApproEnterPara gateApproEnterPara);
    
    //添加记录
    public int addGateApproEnterPara(GateApproEnterPara gateApproEnterPara);

    //修改记录
    public int modifyGateApproEnterPara(GateApproEnterPara gateApproEnterPara);

    //根据主键查询判断记录是否存在
    public List<GateApproEnterPara> getGateApproEnterParaById(GateApproEnterPara gateApproEnterPara);

    //删除记录
    public int deleteGateApproEnterPara(GateApproEnterPara gateApproEnterPara);

    //当前参数、未来参数作历史标识或删除标识
    public int submitToOldFlag(GateApproEnterPara gateApproEnterPara);

    //添加新的“未来”或“当前”参数的数据记录
    public int submitFromDraftToCurOrFur(GateApproEnterPara gateApproEnterPara);

    //草稿参数删除
    public int deleteGateApproEnterParasForClone(GateApproEnterPara gateApproEnterPara);

    //未来或当前参数克隆成草稿版本
    public int cloneFromCurOrFurToDraft(GateApproEnterPara gateApproEnterPara);

}
