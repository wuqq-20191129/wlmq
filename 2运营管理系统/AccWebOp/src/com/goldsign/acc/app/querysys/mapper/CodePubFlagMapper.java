/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.goldsign.acc.app.querysys.mapper;

import com.goldsign.acc.app.querysys.entity.CodePubFlag;
import java.util.List;

/**
 * @desc:
 * @author:zhongzqi
 * @create date: 2017-6-9
 */
public interface CodePubFlagMapper {
    public List<CodePubFlag> getListByType(CodePubFlag codePubFlag);

}
