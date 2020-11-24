/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.goldsign.etmcs.dao;

import com.goldsign.csfrm.dao.IBaseDao;
import com.goldsign.etmcs.vo.MakeCardParam;
import com.goldsign.etmcs.vo.MakeCardVo;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 *
 * @author lenovo
 */
public interface IMakeCardDao extends IBaseDao{

    List<MakeCardVo> getMakeCards(MakeCardParam makeCardParam);
    
    /*
     * 按工号查询员工发卡信息
     */
    public MakeCardVo getMakeCardsByEmployeeId(MakeCardVo vo);
    
    //检验数据库是否存在员工信息"W_ACC_TK"."W_IC_ET_ISSUE"
    public int isExistsMakeCard(MakeCardVo vo);
    
    //获取员工信息"W_ACC_TK"."W_IC_ET_ISSUE"
    public MakeCardVo getEmployeeInfo(MakeCardVo vo);

    //发卡
    public Boolean writeMakeCard(MakeCardVo vo);

    //员工退卡
    public Boolean writeReturnCard(MakeCardVo vo);

    //统计查询
    public List<Map<String, String>> getMakeCardsCount(MakeCardParam makeCardParam);

    //写本地文件
    public File writeLocalFile(MakeCardVo vo) throws IOException;
    
    //读本地文件
    public Boolean readLocalFile(File file) throws IOException;
    
    //修改卡信息
    public Boolean editCard(MakeCardVo vo);
    
    //查询是否已经退卡
    public Boolean isCardReturned(MakeCardVo vo);
    
    //检测设备配置是否正确
    public Boolean isDeviceConfigureRight(String ip, String deviceType, String deviceNo);
}
