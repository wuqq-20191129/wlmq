/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.goldsign.acc.app.opma.mapper;

import com.goldsign.acc.app.opma.entity.SysGroup;
import java.util.List;

/**
 * @author  lyx
 * @date    2017-3-3 10:39:35
 * @version V1.0
 * @desc 系统组管理接口
 */
public interface SysGroupMapper  {
	
	/**
	 * 查询出所有权限组
	 * @return
	 */
	public List<SysGroup > getAll();
        
        public SysGroup selectByPrimaryKey(String grpId);
        
        public int getMax();
        
        public int insert(SysGroup sysGrp);
        
        int updateByPrimaryKeySelective(SysGroup sysGrp);
        
        int deleteByPrimaryKey(String grpId);
        
        public int count(SysGroup sysGrp);
        
        public List<SysGroup > querySysGroup(SysGroup sysGrp);
    
}
