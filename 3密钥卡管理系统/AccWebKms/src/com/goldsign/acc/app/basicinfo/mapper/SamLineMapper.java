/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.basicinfo.mapper;

import com.goldsign.acc.app.basicinfo.entity.SamLine;
import java.util.List;

/**
 *
 * @author mh
 */
public interface SamLineMapper {
   public List<SamLine> querySamLine(SamLine samLine);
   
   public List<SamLine> querySamLineById(SamLine samLine);
   
   public List<SamLine> querySamLineByName(SamLine samLine);
   
   public List<SamLine> queryIsUsed(SamLine samLine);//删除时查询线路是否被使用
   
   public List<SamLine> checkIsDefin(SamLine samLine);//修改时查询名称是否已定义
   
   public int addSamLine(SamLine samLine);
   
   public int deleteSamLine(SamLine samLine);
   
   public int modifySamLine(SamLine samLine);
    
}
