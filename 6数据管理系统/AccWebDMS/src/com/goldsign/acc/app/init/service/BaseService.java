/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.goldsign.acc.app.init.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.DefaultTransactionDefinition;

/**
 * @desc:
 * @author:zhongzqi
 * @create date: 2017-12-11
 */
@Service
public class BaseService {
     @Autowired
    protected DataSourceTransactionManager txMgr;
    @Autowired
    protected DefaultTransactionDefinition def;
    
    
}
