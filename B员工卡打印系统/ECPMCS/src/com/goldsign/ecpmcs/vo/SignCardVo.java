/*
 * 文件名：SignCardVo
 * 版权：Copyright: goldsign (c) 2013
 */

package com.goldsign.ecpmcs.vo;


/*
 * 〈记名卡实例类〉
 * @author     lindaquan
 * @version    V1.0
 * @createTime 2014-4-10
 */

public class SignCardVo {
    
    private String applyName;//名字
    private String applySex;//性别
    private String identityType;//证件类型
    private String identityId;//证件号
    private String expiredDate;//有效期
    private String address;//地址
    private String applyDatetime;//申请时间
    private String cardSubId;//子类型
    private String cardMainId;//主类型
    private String imgDir;//相片路径
    
    private String beginTime;//开始时间
    private String endTime;//结束时间
    private String applySexTxt;//性别
    private String identityTypeTxt;//证件类型
    private String cardSubIdTxt;//子类型
    private String cardMainIdTxt;//主类型
    private String cardType;//票卡类型  cardMainId||cardSubId
    private String cardTypeTxt;//票卡类型 
    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getApplyName() {
        return applyName;
    }

    public void setApplyName(String applyName) {
        this.applyName = applyName;
    }

    public String getApplySex() {
        return applySex;
    }

    public void setApplySex(String applySex) {
        this.applySex = applySex;
    }

    public String getIdentityType() {
        return identityType;
    }

    public void setIdentityType(String identityType) {
        this.identityType = identityType;
    }

    public String getIdentityId() {
        return identityId;
    }

    public void setIdentityId(String identityId) {
        this.identityId = identityId;
    }

    public String getExpiredDate() {
        return expiredDate;
    }

    public void setExpiredDate(String expiredDate) {
        this.expiredDate = expiredDate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getApplyDatetime() {
        return applyDatetime;
    }

    public void setApplyDatetime(String applyDatetime) {
        this.applyDatetime = applyDatetime;
    }

    public String getCardSubId() {
        return cardSubId;
    }

    public void setCardSubId(String cardSubId) {
        this.cardSubId = cardSubId;
    }

    public String getCardMainId() {
        return cardMainId;
    }

    public void setCardMainId(String cardMainId) {
        this.cardMainId = cardMainId;
    }

    public String getImgDir() {
        return imgDir;
    }

    public void setImgDir(String imgDir) {
        this.imgDir = imgDir;
    }

    public String getApplySexTxt() {
        return applySexTxt;
    }

    public void setApplySexTxt(String applySexTxt) {
        this.applySexTxt = applySexTxt;
    }

    public String getIdentityTypeTxt() {
        return identityTypeTxt;
    }

    public void setIdentityTypeTxt(String identityTypeTxt) {
        this.identityTypeTxt = identityTypeTxt;
    }

    public String getCardSubIdTxt() {
        return cardSubIdTxt;
    }

    public void setCardSubIdTxt(String cardSubIdTxt) {
        this.cardSubIdTxt = cardSubIdTxt;
    }

    public String getCardMainIdTxt() {
        return cardMainIdTxt;
    }

    public void setCardMainIdTxt(String cardMainIdTxt) {
        this.cardMainIdTxt = cardMainIdTxt;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getCardTypeTxt() {
        return cardTypeTxt;
    }

    public void setCardTypeTxt(String cardTypeTxt) {
        this.cardTypeTxt = cardTypeTxt;
    }
    
}
