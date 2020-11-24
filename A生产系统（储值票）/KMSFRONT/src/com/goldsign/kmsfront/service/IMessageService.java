/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.goldsign.kmsfront.struct.service;

import com.goldsign.commu.commu.service.IBaseService;
import com.goldsign.kmsfront.struct.vo.QueryConVo;
import com.goldsign.kmsfront.struct.vo.QueryRetVo;
import java.util.List;

/**
 *
 * @author lenovo
 */
public interface IMessageService extends IBaseService{
    
    QueryRetVo queryStructDatas(List<QueryConVo> queryCons)throws Exception;
}
