/*
 * 文件名：AuthInInf
 * 版权：Copyright: goldsign (c) 2013
 */

package com.goldsign.sammcs.vo;


/*
 * PSAM VO实体类
 * @author     lindaquan
 * @version    V1.0
 */

public class IssueVo{

    //00
    private String keyVerstion ;//密钥版本
    //0000100000000001
    private String psamCardNo ;//psam卡号
    //00
    private String psamCardVersion ;//SAM版本
    //00
    private String psamCardType ;//psam卡类型
    //01
    private String keyIndex ;//密钥索引号
    //4100073100000000
    private String issuerId ;//发行者标识
    //4100073100000000
    private String receiverId ;//接收者标识
    //20131001
    private String startDate ;//启用日期
    //20231001
    private String validDate ;//有效日期
    
    private String psamCardPhyNo;
    
    //20160126 add by mqf
    private String cardProducerCode; //卡商代码

    /**
     * @return the keyVerstion
     */
    public String getKeyVerstion() {
        return keyVerstion;
    }

    /**
     * @param keyVerstion the keyVerstion to set
     */
    public void setKeyVerstion(String keyVerstion) {
        this.keyVerstion = keyVerstion;
    }

    /**
     * @return the psamCardNo
     */
    public String getPsamCardNo() {
        return psamCardNo;
    }

    /**
     * @param psamCardNo the psamCardNo to set
     */
    public void setPsamCardNo(String psamCardNo) {
        this.psamCardNo = psamCardNo;
    }

    /**
     * @return the psamCardVersion
     */
    public String getPsamCardVersion() {
        return psamCardVersion;
    }

    /**
     * @param psamCardVersion the psamCardVersion to set
     */
    public void setPsamCardVersion(String psamCardVersion) {
        this.psamCardVersion = psamCardVersion;
    }

    /**
     * @return the psamCardType
     */
    public String getPsamCardType() {
        return psamCardType;
    }

    /**
     * @param psamCardType the psamCardType to set
     */
    public void setPsamCardType(String psamCardType) {
        this.psamCardType = psamCardType;
    }

    /**
     * @return the keyIndex
     */
    public String getKeyIndex() {
        return keyIndex;
    }

    /**
     * @param keyIndex the keyIndex to set
     */
    public void setKeyIndex(String keyIndex) {
        this.keyIndex = keyIndex;
    }

    /**
     * @return the issuerId
     */
    public String getIssuerId() {
        return issuerId;
    }

    /**
     * @param issuerId the issuerId to set
     */
    public void setIssuerId(String issuerId) {
        this.issuerId = issuerId;
    }

    /**
     * @return the receiverId
     */
    public String getReceiverId() {
        return receiverId;
    }

    /**
     * @param receiverId the receiverId to set
     */
    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    /**
     * @return the startDate
     */
    public String getStartDate() {
        return startDate;
    }

    /**
     * @param startDate the startDate to set
     */
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    /**
     * @return the validDate
     */
    public String getValidDate() {
        return validDate;
    }

    /**
     * @param validDate the validDate to set
     */
    public void setValidDate(String validDate) {
        this.validDate = validDate;
    }

    /**
     * @return the psamCardPhyNo
     */
    public String getPsamCardPhyNo() {
        return psamCardPhyNo;
    }

    /**
     * @param psamCardPhyNo the psamCardPhyNo to set
     */
    public void setPsamCardPhyNo(String psamCardPhyNo) {
        this.psamCardPhyNo = psamCardPhyNo;
    }

    public String getCardProducerCode() {
        return cardProducerCode;
    }

    public void setCardProducerCode(String cardProducerCode) {
        this.cardProducerCode = cardProducerCode;
    }
    
    

}
