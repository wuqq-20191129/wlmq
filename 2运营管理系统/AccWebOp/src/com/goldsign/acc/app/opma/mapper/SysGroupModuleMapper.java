package com.goldsign.acc.app.opma.mapper;

import com.goldsign.acc.app.opma.entity.SysGroupModuleKey;
import java.util.List;
import java.util.Map;


public interface SysGroupModuleMapper  {
	/**
	 * 通过组ID查找模块
	 * 
	 * @param map
	 * @return
	 */
	List<SysGroupModuleKey> findModuleByGroupId(Map<String, Object> map);

	/**
	 * 通过组ID和模块ID集合删除表中不存在的记录
	 * 
	 * @param map
	 * @return
	 */
	int deleteByCondition(Map<String, Object> map);

	/**
	 * 通过组ID和模块ID查找记录
	 * 
	 * @param map
	 * @return
	 */
	int countModuleByGroupIdAndModuleId(Map<String, Object> map);
        
        int deleteByPrimaryKey(SysGroupModuleKey key);
        
        int insert(SysGroupModuleKey key);
}