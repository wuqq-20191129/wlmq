/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.opma.mapper;

import com.goldsign.acc.app.opma.entity.DevParaVerCur;
import java.util.List;
import java.util.Map;

/**
 *
 * @author zhouyang
 * 20170623
 * 设备参数版本查询结果
 */
public interface DevParaVerCurMapper {
    //获取查询条件设备ID
    public List<DevParaVerCur> getDevIdList(DevParaVerCur devParaVerCur);
    
    public List<DevParaVerCur> getDevParaVerCurs(Map map);
    
}
