
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.querysys.mapper;

import com.goldsign.acc.app.querysys.entity.CodePubFlag;
import com.goldsign.acc.app.querysys.entity.QrcodeOrderQuery;
import java.util.List;

/**
 *
 * @author wuqq
 */
//20190906 wuqq 增加查询w_ol_buf_qrcode_order表
public interface QrcodeOrderQueryMapper {
    public List<QrcodeOrderQuery> getQrcodeOrder(QrcodeOrderQuery queryCondition);
    public List<QrcodeOrderQuery> getQrcodeOrderadd(QrcodeOrderQuery queryCondition);
    public List<CodePubFlag> getStation ();

    public List<QrcodeOrderQuery> getQrcodOrderForAudit(QrcodeOrderQuery qo);

    public int auditQrcodOrderForY(QrcodeOrderQuery queryCondition);

    public int modifyQrcodOrder(QrcodeOrderQuery queryCondition);

    public int auditQrcodOrderForNo(QrcodeOrderQuery qo);

    public void insertModifyOpe(QrcodeOrderQuery queryCondition);

    

    public int getdate(String orderNo);
}
