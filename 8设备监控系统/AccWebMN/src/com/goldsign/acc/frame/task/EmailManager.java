package com.goldsign.acc.frame.task;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

/**
 * Description:
 *
 * @author: zhongziqi
 * @Date: 2019-04-11
 * @Time: 14:26
 */
@Component
public class EmailManager implements InitializingBean {
    @Override
    public void afterPropertiesSet() throws Exception {

    }

    public EmailManager() {
    }
}
