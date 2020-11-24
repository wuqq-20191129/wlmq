package com.goldsign.acc.app.prminfo.mapper;

import com.goldsign.acc.app.prminfo.entity.PrmDevProgram;
import java.util.List;

public interface PrmDevProgramMapper {
    int deleteByPrimaryKey(Long waterNo);

    int insert(PrmDevProgram record);

    int insertSelective(PrmDevProgram record);

    PrmDevProgram selectByPrimaryKey(Long waterNo);

    int updateByPrimaryKeySelective(PrmDevProgram record);

    int updateByPrimaryKey(PrmDevProgram record);
    
    List<PrmDevProgram> selectByType(String type);
    
    List<PrmDevProgram> qDevsByCon(PrmDevProgram record);
    
    int updateByCon(PrmDevProgram record);
    
    int delByCon(PrmDevProgram record);
}