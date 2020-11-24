/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.query.mapper;

import com.goldsign.acc.app.query.entity.BoxLogicalDetail;
import java.util.List;

/**
 *
 * @author zhouy
 * 2018-02-13
 * 库存查询--盒内逻辑卡号明细
 */
public interface BoxLogicalDetailMapper {
    public List<BoxLogicalDetail> query(BoxLogicalDetail vo);
}
