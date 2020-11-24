/*
 * 文件名：PubFlagVo
 * 版权：Copyright: goldsign (c) 2013
 */

package com.goldsign.etmcs.vo;


/*
 * pub_flag表实体类
 * @author     lindaquan
 * @version    V1.0
 * @createTime 2013-8-30
 */

public class PubFlagVo {
    
    private String type;//类型
    
    private String code;//代码
    
    private String codeText;//名称
    
    private String discription;//描述

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCodeText() {
        return codeText;
    }

    public void setCodeText(String codeText) {
        this.codeText = codeText;
    }

    public String getDiscription() {
        return discription;
    }

    public void setDiscription(String discription) {
        this.discription = discription;
    }
}
