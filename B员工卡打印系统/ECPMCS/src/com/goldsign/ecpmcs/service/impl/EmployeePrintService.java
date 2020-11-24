/*
 * 文件名：SignCardService
 * 版权：Copyright: goldsign (c) 2013
 */

package com.goldsign.ecpmcs.service.impl;

import com.goldsign.csfrm.service.impl.BaseService;
import com.goldsign.ecpmcs.dao.IEmployeePrintDao;
import com.goldsign.ecpmcs.dao.impl.EmployeePrintDao;
import com.goldsign.ecpmcs.service.IEmployeePrintService;
import com.goldsign.ecpmcs.vo.SignCardPrintVo;
import java.util.ArrayList;
import java.util.List;


/*
 * 〈员工卡打印记录查询〉
 * @author     lindaquan
 * @version    V1.1
 * @createTime 2014-12-18
 */

public class EmployeePrintService extends BaseService implements IEmployeePrintService {
    
    private IEmployeePrintDao printDao;
    
    public EmployeePrintService(){
        printDao = new EmployeePrintDao();
    }

    /**
     * 员工卡打印记录
     * @param SignCardPrintParam
     * @return 
     */
    @Override
    public List<Object[]> getPrintListAll(SignCardPrintVo SignCardPrintParam) {
        List<SignCardPrintVo> signCardVoRets = printDao.getPrintList(SignCardPrintParam);
        //"姓名", "证件类型", "证件号", "票卡类型", "职务", "部门", "性别","级别","逻辑卡号", 
        //"操作员", "打印时间", "相片名称", "票卡类型代码" ,"证件类型代码", "部门代码" ,"职务代码","级别代码"};
        List<Object[]> signCardRets = new ArrayList<Object[]>();
        for(SignCardPrintVo signCardVoRet: signCardVoRets){
            Object[] signCardRet = new Object[]{
                signCardVoRet.getName(), signCardVoRet.getIdentityTypeTxt(), 
                signCardVoRet.getIdentityId(), signCardVoRet.getCardTypeTxt(),
                signCardVoRet.getPositionTxt(), signCardVoRet.getDepartmentTxt(),
                signCardVoRet.getGenderTxt(), signCardVoRet.getEmployeeClassTxt(),
                signCardVoRet.getLogicalId(), signCardVoRet.getPrintOper(),
                signCardVoRet.getPrintTime(), signCardVoRet.getPhotoName(),
                signCardVoRet.getCardType(), signCardVoRet.getIdentityType(),
                signCardVoRet.getDepartment(),signCardVoRet.getPosition(),
                signCardVoRet.getEmployeeClass()
                
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

    @Override
    public String getEmployeeClassByLogicNo(String logicNo) {
        return printDao.getEmployeeClass(logicNo);
    }
    
    

}
