/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.goldsign.etmcs.service;

import com.goldsign.csfrm.service.IBaseService;
import com.goldsign.etmcs.vo.MakeCardParam;
import com.goldsign.etmcs.vo.MakeCardVo;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author lenovo
 */
public interface IMakeCardService extends IBaseService{

    //查询
    List<Object[]> getMakeCards(MakeCardParam makeCardParam);
    
    /*
     * 按工号查询员工发卡信息
     */
    public MakeCardVo getMakeCardsByEmployeeId(MakeCardVo vo);
    
    //获取员工信息"W_ACC_TK"."W_IC_ET_ISSUE"
    public MakeCardVo getEmployeeInfoService(MakeCardVo vo);
    
    //检验数据库是否存在员工信息"ACC_TK"."IC_ET_ISSUE"
    int isExistsMakeCard(MakeCardVo vo);
    
    //leadOutXls
    //员工发卡
    public Boolean writeMakeCard(MakeCardVo vo);
    
    //员工退卡
    public Boolean writeReturnCard(MakeCardVo vo);

    //统计查询
    public List<Object[]> getMakeCardsCount(MakeCardParam callParam);

    //插入数据库异常时，写到本地文件
    public File writeLocalFile(MakeCardVo vo) throws IOException;
    
    //读本地文件
    public Boolean readLocalFile(File file) throws IOException;
    
    //员工修改
    public Boolean editCard(MakeCardVo vo);
    
    //
    public Boolean isCardReturned(MakeCardVo vo);
}
