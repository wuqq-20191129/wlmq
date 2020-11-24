package com.goldsign.acc.app.config.mapper;

import com.goldsign.acc.app.config.entity.Config;
import com.goldsign.acc.app.config.entity.ConfigKey;
import java.util.List;

public interface ConfigMapper {
    int deleteByPrimaryKey(ConfigKey key);

    int insert(Config record);

    int insertSelective(Config record);

    Config selectByPrimaryKey(ConfigKey key);
    
    List<Config> selectConfigs(ConfigKey key);

    int updateByPrimaryKeySelective(Config record);

    int updateByPrimaryKey(Config record);
}