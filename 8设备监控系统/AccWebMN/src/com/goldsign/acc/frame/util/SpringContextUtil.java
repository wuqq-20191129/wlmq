/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.frame.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/** 
 * 类名: SpringContextUtil
 * 描述： 获取bean的工具类，可用于在线程里面获取bean
 * 创建人: luck
 */
public class SpringContextUtil implements ApplicationContextAware {

    private static ApplicationContext context = null;

    /* (non Javadoc)
     * @Title: setApplicationContext
     * @Description: spring获取bean工具类
     * @param applicationContext
     * @throws BeansException
     * @see org.springframework.context.ApplicationContextAware#setApplicationContext(org.springframework.context.ApplicationContext)
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        this.context = applicationContext;
    }

    public static <T> T getBean(String beanName){
        return (T) context.getBean(beanName);
    }
    //add by zhongzq
    public static String[] getBeanDefinitionNames() {
        return context.getBeanDefinitionNames();
    }

}
