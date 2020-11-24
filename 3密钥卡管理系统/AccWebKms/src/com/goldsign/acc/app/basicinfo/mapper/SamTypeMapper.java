/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.basicinfo.mapper;

import com.goldsign.acc.app.basicinfo.entity.SamType;
import java.util.List;

/**
 *
 * @author mh
 */
public interface SamTypeMapper {
    List<SamType> querySamType(SamType samType);
    
    List<SamType> querySamTypeById(SamType samType);
    
    List<SamType> querySamTypeByName(SamType samType);
    
    List<SamType> queryIsUsed(SamType samType);//删除时查询卡类型是否被使用
    
    List<SamType> checkIsDefin(SamType samType);//修改时查询名称是否已定义
    
    List<SamType> checkInNew(SamType samType);//删除时查询新卡入库未审核的单据是否使用卡类型
    
    public int addSamType(SamType samType);
    
    public int deleteSamType(SamType samType);

    public int modifySamType(SamType samType);

    public String getSamTypeDesc(String sam_type_desc);//查询卡类型定义名称
}
