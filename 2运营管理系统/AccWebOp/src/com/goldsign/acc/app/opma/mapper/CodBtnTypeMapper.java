package com.goldsign.acc.app.opma.mapper;

import com.goldsign.acc.app.opma.entity.CodBtnType;
import java.util.List;

public interface CodBtnTypeMapper {
    int deleteByPrimaryKey(String btnId);

    int insert(CodBtnType record);

    int insertSelective(CodBtnType record);

    CodBtnType selectByPrimaryKey(String btnId);
    
    List<CodBtnType> selectBy(String btnId);

    int updateByPrimaryKeySelective(CodBtnType record);

    int updateByPrimaryKey(CodBtnType record);
}