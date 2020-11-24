/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.prminfo.entity;

import com.goldsign.acc.app.querysys.entity.*;
import java.io.Serializable;

/**
 *
 * @author zhouy
 * 交通部一卡通白名单
 * 20180208
 */
public class WhiteListMoc implements Serializable{
    private String iss_identify_id;//发卡机构代码
    private String iss_identify_num;//发卡机构识别码

    public String getIss_identify_id() {
        return iss_identify_id;
    }

    public void setIss_identify_id(String iss_identify_id) {
        this.iss_identify_id = iss_identify_id;
    }

    public String getIss_identify_num() {
        return iss_identify_num;
    }

    public void setIss_identify_num(String iss_identify_num) {
        this.iss_identify_num = iss_identify_num;
    }
}
