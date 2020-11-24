/*
 * 文件名：CardTypeVo
 * 版权：Copyright: goldsign (c) 2013
 */

package com.goldsign.ecpmcs.vo;


/*
 * 〈票卡类型实例类〉
 * @author     lindaquan
 * @version    V1.0
 * @createTime 2014-4-10
 */

public class CardTypeVo {
    
    private String cardMainId;
    private String cardMainName;
    private String cardSubId;
    private String cardSubName;

    public String getCardMainId() {
        return cardMainId;
    }

    public void setCardMainId(String cardMainId) {
        this.cardMainId = cardMainId;
    }

    public String getCardMainName() {
        return cardMainName;
    }

    public void setCardMainName(String cardMainName) {
        this.cardMainName = cardMainName;
    }

    public String getCardSubId() {
        return cardSubId;
    }

    public void setCardSubId(String cardSubId) {
        this.cardSubId = cardSubId;
    }

    public String getCardSubName() {
        return cardSubName;
    }

    public void setCardSubName(String cardSubName) {
        this.cardSubName = cardSubName;
    }

}
