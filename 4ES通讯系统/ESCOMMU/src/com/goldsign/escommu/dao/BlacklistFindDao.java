/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.goldsign.escommu.dao;
import com.goldsign.escommu.parmdstrb.*;
import com.goldsign.escommu.util.PubUtil;
import com.goldsign.escommu.vo.BlacklistReqVo;
import com.goldsign.escommu.vo.BlacklistRspVo;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

/**
 *
 * @author Administrator
 */
public class BlacklistFindDao {
    private static Logger logger = Logger.getLogger(BlacklistFindDao.class.
			getName());
    
    public List<BlacklistRspVo> blacklistFind(BlacklistReqVo blacklistReqVo) throws Exception {
       
        List<BlacklistRspVo> blacklistRspVos = new ArrayList<BlacklistRspVo>();
        BlacklistRspVo blacklistRspVo = null;
        try {
            ParameterBase parameter0601 = new Parameter0601();
            parameter0601.genParameterFile(blacklistReqVo.getDeviceId());
            blacklistRspVo = getResultRecord(parameter0601);
            blacklistRspVos.add(blacklistRspVo);
            
            ParameterBase parameter0602 = new Parameter0602();
            parameter0602.genParameterFile(blacklistReqVo.getDeviceId());  
            blacklistRspVo = getResultRecord(parameter0602);
            blacklistRspVos.add(blacklistRspVo);
            
            ParameterBase parameter0603 = new Parameter0603();
            parameter0603.genParameterFile(blacklistReqVo.getDeviceId()); 
            blacklistRspVo = getResultRecord(parameter0603);
            blacklistRspVos.add(blacklistRspVo);
            
            ParameterBase parameter0604 = new Parameter0604();
            parameter0604.genParameterFile(blacklistReqVo.getDeviceId());
            blacklistRspVo = getResultRecord(parameter0604);
            blacklistRspVos.add(blacklistRspVo);
            
       } catch (Exception e) {
            PubUtil.handleException(e, logger);
        }
        
        return blacklistRspVos;
    }
    
    private BlacklistRspVo  getResultRecord(ParameterBase parameter) throws Exception{
        
        BlacklistRspVo blacklistRspVo = new BlacklistRspVo();

        //********************************************
        String fileName = parameter.getParFileName();

        blacklistRspVo.setFileName(fileName);

        return blacklistRspVo;
    }
}
