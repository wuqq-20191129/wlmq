package com.goldsign.acc.app.password.mapper;

import com.goldsign.acc.app.password.entity.SysOperator;
import java.util.List;
import java.util.Map;

public interface SysOperatorMapper {

    /**
     * 通过操作员ID 列表查找操作员和对应权限组信息
     *
     * @param map 使用字符串'oprIds'传入一个操作员ID List
     * @return 返回操作员列表和对应的权限组
     */
    public List<Object> selectByOprId(Map<String, Object> map);

    public int count(Map<String, Object> map);

    public int updateByPrimaryKeySelective(SysOperator sysOpr);

    public SysOperator selectByOperatorId(String operatorId);
    
    public SysOperator selectByEmployeeId(String eid);

    public List<SysOperator> selectAll(Map<String, Object> map);

    public int insert(SysOperator sysOpr);

    public int insertSelective(SysOperator sysOpr);

    public int deleteByPrimaryKey(String operatorId);
    
    public List<SysOperator> querySysOperators(SysOperator sysOperator);
    
    public int checkEmployeeId(Map<String, Object> map);

    public int resetPassword(SysOperator sysOpr);

}
