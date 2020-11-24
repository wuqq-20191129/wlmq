/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.goldsign.escommu.dao;
import com.goldsign.escommu.parmdstrb.Parameter0203;
import com.goldsign.escommu.parmdstrb.ParameterBase;
import com.goldsign.escommu.util.PubUtil;
import com.goldsign.escommu.vo.SamCardReqVo;
import com.goldsign.escommu.vo.SamCardRspVo;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

/**
 *
 * @author Administrator
 */
public class SamCardFindDao {
    private static Logger logger = Logger.getLogger(SamCardFindDao.class.
			getName());
    
    public List<SamCardRspVo> samCardFind(SamCardReqVo samCardReqVo) throws Exception {
            
        List<SamCardRspVo> samCardRspVos = new ArrayList<SamCardRspVo>();
        SamCardRspVo samCardRspVo = null;
        try {
            ParameterBase parameter0203 = new Parameter0203();
            parameter0203.genParameterFile(samCardReqVo.getDeviceId());
            
            samCardRspVo = getResultRecord(parameter0203);
            samCardRspVos.add(samCardRspVo);
        } catch (Exception e) {
            PubUtil.handleException(e, logger);
        }

        return samCardRspVos;
    }
    
    private SamCardRspVo  getResultRecord(ParameterBase parameter) throws Exception{
        
        SamCardRspVo samCardRspVo = new SamCardRspVo();

        //********************************************
        String fileName = parameter.getParFileName();

        samCardRspVo.setFileName(fileName);

        return samCardRspVo;
    }
}
