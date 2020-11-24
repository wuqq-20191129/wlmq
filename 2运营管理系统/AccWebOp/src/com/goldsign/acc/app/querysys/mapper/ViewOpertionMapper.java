/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.querysys.mapper;

import com.goldsign.acc.app.querysys.entity.OperatorLog;
import com.goldsign.acc.app.querysys.entity.ViewOpertion;
import java.util.List;

/**
 *
 * @author zhouyang
 * 20170619
 * 安全日志——查看操作
 */
public interface ViewOpertionMapper {
    //运营管理系统操作日志
    public List<ViewOpertion> getOPViewOpertions(ViewOpertion viewOpertion);
    //票务管理系统操作日志
    public List<ViewOpertion> getTKViewOpertions(ViewOpertion viewOpertion);
    //密钥卡管理系统操作日志
    public List<ViewOpertion> getSAMViewOpertions(ViewOpertion viewOpertion);
    //数据管理系统操作日志
    public List<ViewOpertion> getDMViewOpertions(ViewOpertion viewOpertion);
    //设备监控系统操作日志
    public List<ViewOpertion> getMNViewOpertions(ViewOpertion viewOpertion);
    //清分规则系统操作日志
    public List<ViewOpertion> getRLViewOpertions(ViewOpertion viewOpertion);
    //获取模块名称
    public String getModuleName(ViewOpertion viewOpertion);
}
