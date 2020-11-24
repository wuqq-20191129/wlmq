/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.prminfo.mapper;

import com.goldsign.acc.app.prminfo.entity.WhiteListMoc;
import java.util.List;

/**
 *
 * @author zhouy
 * 交通部一卡通白名单
 * 20180208
 */
public interface WhiteListMocMapper {
    public List<WhiteListMoc> getWhiteListMocs(WhiteListMoc whiteListMoc);
}
