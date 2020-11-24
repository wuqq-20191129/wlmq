/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.prminfo.mapper;

import com.goldsign.acc.app.prminfo.entity.TvmStopsellDef;
import java.util.List;
import java.util.Map;

/**
 *
 * @author zhouyang
 * 20170614
 * 工作日时间表参数 
 */
public interface TvmStopsellDefMapper {
    //根据条件查询记录
    public List<TvmStopsellDef> getTvmStopsellDefs(TvmStopsellDef tvmStopsellDef);
    
    //添加记录
    public int addTvmStopsellDef(TvmStopsellDef tvmStopsellDef);

    //修改记录
    public int modifyTvmStopsellDef(TvmStopsellDef tvmStopsellDef);

    //根据主键查询判断记录是否存在
    public List<TvmStopsellDef> getTvmStopsellDefById(TvmStopsellDef tvmStopsellDef);

    //删除记录
    public int deleteTvmStopsellDef(TvmStopsellDef tvmStopsellDef);

    //当前参数、未来参数作历史标识或删除标识
    public int submitToOldFlag(TvmStopsellDef tvmStopsellDef);

    //添加新的“未来”或“当前”参数的数据记录
    public int submitFromDraftToCurOrFur(TvmStopsellDef tvmStopsellDef);
    
    //草稿参数删除
    public int deleteTvmStopsellDefsForClone(TvmStopsellDef tvmStopsellDef);

    //未来或当前参数克隆成草稿版本
    public int cloneFromCurOrFurToDraft(TvmStopsellDef tvmStopsellDef);
    
}
