package com.goldsign.acc.frame.mapper;

import com.goldsign.acc.app.system.entity.BaseMessage;
import com.goldsign.acc.frame.entity.EmailContent;

import java.util.ArrayList;

public interface EmailContentMapper {
    int insert(EmailContent record);

    int insertSelective(EmailContent record);

    ArrayList<EmailContent> getContents();

    int updateForSend(EmailContent record);

    int checkOldAbnormal(EmailContent record);

    int checkNeedUpdate(BaseMessage vo);

    int updateHandleAbnormal(BaseMessage vo);

}