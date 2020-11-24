package com.goldsign.acc.frame.mapper;

import com.goldsign.acc.frame.entity.EmailRecipients;

import java.util.ArrayList;

public interface EmailRecipientsMapper {
    int deleteByPrimaryKey(String recipients);

    int insert(EmailRecipients record);

    int insertSelective(EmailRecipients record);

    EmailRecipients selectByPrimaryKey(String recipients);

    int updateByPrimaryKeySelective(EmailRecipients record);

    int updateByPrimaryKey(EmailRecipients record);

    ArrayList getTOList();

    ArrayList getCCList();
}