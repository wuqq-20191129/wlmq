package com.goldsign.acc.app.opma.mapper;

import com.goldsign.acc.app.opma.entity.SysModule;
import java.util.List;
import java.util.Map;


public interface SysModuleMapper {
    
    
    /**
     * 通过模块ID查找模块
     * @param map
     * @return 
     */
    List<SysModule> findTreeByModuleId(Map<String, Object> map);
    
    /**
     * 通过操作员ID查找模块
     * @param sysOperatorId
     * @return
     */
    List<SysModule> getModulesByOprId(String sysOperatorId);
    
    /**
     * 通过查询条件查询
     * @param sysModule
     * @return 
     */
    List<SysModule> qModulesByCon(SysModule sysModule);
    
    /**
     * 
     * @param sysModule
     * @return 
     */
    int insertSelective(SysModule sysModule);
    
    /**
     * 通过模块ID统计
     * @param moduleId
     * @return 
     */
    int count(String moduleId);
    
    /**
     * 通过模块ID删除
     * @param moduleId
     * @return 
     */
    int deleteByPrimaryKey(String moduleId);
    
    /**
     * 通过模块ID修改有内容的字段
     * @param sysModule
     * @return 
     */
    int updateByPrimaryKeySelective(SysModule sysModule);

    public List<Map> queryToMap(SysModule queryCondition);
}
