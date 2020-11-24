/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @desc:
 * @author:zhongzqi
 * @create date: 2017-12-12
 */
@Component
public class SpringApplicationContextHolder implements ApplicationContextAware {

    private static ApplicationContext context;
    @Override
    public  void  setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }

    public static Object getSpringBean(String beanName) {
        return context == null ? null : context.getBean(beanName);
    }

    public static String[] getBeanDefinitionNames() {
        return context.getBeanDefinitionNames();
    }
}
