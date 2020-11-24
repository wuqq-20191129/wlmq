package com.goldsign.acc.app.opma.mapper;

import com.goldsign.acc.app.opma.entity.SysGroupOperatorKey;
import java.util.List;
import java.util.Map;


public interface SysGroupOperatorMapper {
	
	/**
	 * 通过组ID查找记录（组ID、操作员ID）
	 * @param map
	 * @return
	 */
	List<Object> selectByGroupId(Map<String,Object> map);
	
	/**
	 * 通过组ID删除组下的所有操作员
	 * @param grpId
	 * @return
	 */
	int deleteGrpOprByGrpId(String grpId);
	
	/**
	 * 通过操作员ID删除组操作员记录
	 * @param oprId
	 * @return
	 */
	int deleteGrpOprByOprId(String oprId);

        int insertSelective(SysGroupOperatorKey sysGrpOpr);
        
        int countByPrimaryKey(SysGroupOperatorKey sysGrpOpr);
   
}