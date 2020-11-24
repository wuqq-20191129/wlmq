/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.ecpmcs.dao;

import com.goldsign.csfrm.dao.IBaseDao;
import com.goldsign.ecpmcs.vo.SignCardVo;
import java.util.List;

/**
 *
 * @author Administrator
 */
public interface ISignCardDao extends IBaseDao{
    
    List<SignCardVo> getSignCardVo(SignCardVo signCardParam);
    
}
