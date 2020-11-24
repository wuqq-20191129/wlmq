/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.goldsign.kmsfront.struct.vo;

import com.goldsign.commu.commu.vo.RequestVo;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author lenovo
 */
public class Request1Vo extends RequestVo{

    private List<QueryConVo> queryConVos = new ArrayList<QueryConVo>();

    public void addQueryCon(QueryConVo queryConVo){
        queryConVos.add(queryConVo);
    }
    
    public List<QueryConVo> getQueryCons(){
        return this.queryConVos;
    }
}
