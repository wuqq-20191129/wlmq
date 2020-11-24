package com.goldsign.acc.frame.mapper;

import com.goldsign.acc.frame.entity.EmailErrorLog;

public interface EmailErrorLogMapper {
    int insert(EmailErrorLog record);

    int insertSelective(EmailErrorLog record);
}