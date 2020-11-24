/*
 * 文件名：SignCardService
 * 版权：Copyright: goldsign (c) 2013
 */

package com.goldsign.ecpmcs.service.impl;

import com.goldsign.csfrm.service.impl.BaseService;
import com.goldsign.ecpmcs.dao.IPrintDao;
import com.goldsign.ecpmcs.dao.impl.PrintDao;
import com.goldsign.ecpmcs.service.IPrintService;
import com.goldsign.ecpmcs.vo.SignCardPrintVo;
import java.util.ArrayList;
import java.util.List;


/*
 * 〈记名卡信息实现类〉
 * @author     lindaquan
 * @version    V1.0
 * @createTime 2014-4-10
 */

public class PrintService extends BaseService implements IPrintService {
    
    private IPrintDao printDao;
    
    public PrintService(){
        printDao = new PrintDao();
    }

    /**
     * 打印记录(包含员工卡)
     * @param SignCardPrintParam
     * @return 
     */
    @Override
    public List<Object[]> getPrintListAll(SignCardPrintVo SignCardPrintParam) {
        List<SignCardPrintVo> signCardVoRets = printDao.getPrintList(SignCardPrintParam);
        
        List<Object[]> signCardRets = new ArrayList<Object[]>();
        for(SignCardPrintVo signCardVoRet: signCardVoRets){
            Object[] signCardRet = new Object[]{
                signCardVoRet.getName(), signCardVoRet.getIdentityTypeTxt(), 
                signCardVoRet.getIdentityId(), signCardVoRet.getCardTypeTxt(),
                signCardVoRet.getPositionTxt(), signCardVoRet.getDepartmentTxt(),
                signCardVoRet.getGenderTxt(), signCardVoRet.getPrintOper(),
                signCardVoRet.getPrintTime(), signCardVoRet.getPhotoName()
            };
            signCardRets.add(signCardRet);
        }
        
        return signCardRets;
    }
    
    
    /**
     * 打印记录
     * @param SignCardPrintParam
     * @return 
     */
    @Override
    public List<Object[]> getPrintList(SignCardPrintVo SignCardPrintParam) {
        List<SignCardPrintVo> signCardVoRets = printDao.getPrintList(SignCardPrintParam);
        
        List<Object[]> signCardRets = new ArrayList<Object[]>();
        for(SignCardPrintVo signCardVoRet: signCardVoRets){
            Object[] signCardRet = new Object[]{
                signCardVoRet.getName(), signCardVoRet.getIdentityTypeTxt(), 
                signCardVoRet.getIdentityId(), signCardVoRet.getGenderTxt(),
                signCardVoRet.getCardTypeTxt(), signCardVoRet.getPhotoName(),
                signCardVoRet.getPrintOper(), signCardVoRet.getPrintTime(),
                signCardVoRet.getCardType(),signCardVoRet.getIdentityType()
            };
            signCardRets.add(signCardRet);
        }
        
        return signCardRets;
    }

    
    /**
     * 分类
     * @param SignCardPrintParam
     * @return 
     */
    @Override
    public List<Object[]> getCountList(SignCardPrintVo SignCardPrintParam) {
        List<SignCardPrintVo> signCardVoRets = printDao.countPrintList(SignCardPrintParam);
        
        List<Object[]> signCardRets = new ArrayList<Object[]>();
        for(SignCardPrintVo signCardVoRet: signCardVoRets){
            Object[] signCardRet = new Object[]{signCardVoRet.getCardTypeTxt(), signCardVoRet.getRownum()};
            signCardRets.add(signCardRet);
        }
        
        return signCardRets;
    }

    /**
     * 插入打印记录
     * @param vo
     * @return 
     */
    @Override
    public Boolean insertPrint(SignCardPrintVo vo) {
        return printDao.insertPrint(vo);
    }
    
    

}
