/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.goldsign.kmsfront.struct.dao;

import com.goldsign.commu.commu.dao.IBaseDao;
import com.goldsign.kmsfront.struct.vo.QueryConVo;
import com.goldsign.kmsfront.struct.vo.QueryRetVo;
import java.util.List;

/**
 *
 * @author lenovo
 */
public interface IMessageDao extends IBaseDao{

    QueryRetVo queryStructDatas(List<String> accountNos, List<QueryConVo> queryCons)throws Exception;

    List<String> getAccountsByCard(String cardNo)throws Exception;

    List<String> getAccountsById(String idNo)throws Exception;


}
