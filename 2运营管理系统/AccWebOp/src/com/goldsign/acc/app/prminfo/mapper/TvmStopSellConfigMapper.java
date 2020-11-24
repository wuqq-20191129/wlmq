/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.prminfo.mapper;

import com.goldsign.acc.app.prminfo.entity.TvmStopSellConfig;
import java.util.List;
import java.util.Map;

/**
 *
 * @author zhouyang
 * 20170612
 * TVM停售时间配置参数
 */
public interface TvmStopSellConfigMapper {

    //根据条件查询记录
    public List<TvmStopSellConfig> getTvmStopSellConfigs(TvmStopSellConfig tvmStopSellConfig);

    //添加记录
    public int addTvmStopSellConfig(TvmStopSellConfig tvmStopSellConfig);

    //修改记录
    public int modifyTvmStopSellConfig(TvmStopSellConfig tvmStopSellConfig);

    //根据主键查询判断记录是否存在
    public List<TvmStopSellConfig> getTvmStopSellConfigById(TvmStopSellConfig tvmStopSellConfig);

    //删除记录
    public int deleteTvmStopSellConfig(TvmStopSellConfig tvmStopSellConfig);

    //当前参数、未来参数作历史标识或删除标识
    public int submitToOldFlag(TvmStopSellConfig tvmStopSellConfig);

    //添加新的“未来”或“当前”参数的数据记录
    public int submitFromDraftToCurOrFur(TvmStopSellConfig tvmStopSellConfig);

    //删除草稿版本
    public int deleteTvmStopSellConfigsForClone();

    //未来或当前参数克隆成草稿版本
    public int cloneFromCurOrFurToDraft(TvmStopSellConfig tvmStopSellConfig);
}
