/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.goldsign.etmcs.vo;

/**
 *
 * @author lenovo
 */
public class AnalyzeVo {

    private String bIssueStatus;
    private String bStatus;
    private String cTicketType;
    private String cLogicalID;
    private String cPhysicalID;
    private String bCharacter;
    private String cIssueDate;
    private String cExpire;//物理有效开始时间
    private String cEndExpire;
    private String rfu;
    private long lBalance;
    private long lDeposite;
    private String cLine;
    private String cStationNo;
    private String cDateStart;//逻辑有效开始时间
    private String cDateEnd;
    private String dtDaliyActive;
    private String bEffectDay;
    private String cLimitEntryLine;
    private String cLimitEntryStation;
    private String cLimitExitLine;
    private String cLimitExitStation;
    private String cLimitMode;
    private String certificateIscompany;
    private String certificateIsmetro;
    private String certificateName;
    private String certificateCode;
    private String certificateType;
    private String certificateSex;
    private int tradeCount;
    private String employeePositions;//职务
    private String employeeDepartment;//单位
    private String employeePositionsTxt;//职务
    private String employeeDepartmentTxt;//单位
    private String employeeClass;//员工级别
    private String employeeClassTxt;//员工级别
    private String cardProducerCode;//卡商代码
    
    public String getRfu() {
        return rfu;
    }

    public void setRfu(String rfu) {
        this.rfu = rfu;
    }
    
    public String getcEndExpire() {
        return cEndExpire;
    }

    public void setcEndExpire(String cEndExpire) {
        this.cEndExpire = cEndExpire;
    }

    public String getCardProducerCode() {
        return cardProducerCode;
    }

    public void setCardProducerCode(String cardProducerCode) {
        this.cardProducerCode = cardProducerCode;
    }
    public String getEmployeeClassTxt() {
        return employeeClassTxt;
    }

    public void setEmployeeClassTxt(String employeeClassTxt) {
        this.employeeClassTxt = employeeClassTxt;
    }

    public String getEmployeeClass() {
        return employeeClass;
    }

    public void setEmployeeClass(String employeeClass) {
        this.employeeClass = employeeClass;
    }

    public String getEmployeePositionsTxt() {
        return employeePositionsTxt;
    }

    public void setEmployeePositionsTxt(String employeePositionsTxt) {
        this.employeePositionsTxt = employeePositionsTxt;
    }

    public String getEmployeeDepartmentTxt() {
        return employeeDepartmentTxt;
    }

    public void setEmployeeDepartmentTxt(String employeeDepartmentTxt) {
        this.employeeDepartmentTxt = employeeDepartmentTxt;
    }

    public String getEmployeePositions() {
        return employeePositions;
    }

    public void setEmployeePositions(String employeePositions) {
        this.employeePositions = employeePositions;
    }

    public String getEmployeeDepartment() {
        return employeeDepartment;
    }

    public void setEmployeeDepartment(String employeeDepartment) {
        this.employeeDepartment = employeeDepartment;
    }

    /**
     * @return the bIssueStatus
     */
    public String getbIssueStatus() {
        return bIssueStatus;
    }

    /**
     * @param bIssueStatus the bIssueStatus to set
     */
    public void setbIssueStatus(String bIssueStatus) {
        this.bIssueStatus = bIssueStatus;
    }

    /**
     * @return the bStatus
     */
    public String getbStatus() {
        return bStatus;
    }

    /**
     * @param bStatus the bStatus to set
     */
    public void setbStatus(String bStatus) {
        this.bStatus = bStatus;
    }

    /**
     * @return the cTicketType
     */
    public String getcTicketType() {
        return cTicketType;
    }

    /**
     * @param cTicketType the cTicketType to set
     */
    public void setcTicketType(String cTicketType) {
        this.cTicketType = cTicketType;
    }

    /**
     * @return the cLogicalID
     */
    public String getcLogicalID() {
        return cLogicalID;
    }

    /**
     * @param cLogicalID the cLogicalID to set
     */
    public void setcLogicalID(String cLogicalID) {
        this.cLogicalID = cLogicalID;
    }

    /**
     * @return the cPhysicalID
     */
    public String getcPhysicalID() {
        return cPhysicalID;
    }

    /**
     * @param cPhysicalID the cPhysicalID to set
     */
    public void setcPhysicalID(String cPhysicalID) {
        this.cPhysicalID = cPhysicalID;
    }

    /**
     * @return the bCharacter
     */
    public String getbCharacter() {
        return bCharacter;
    }

    /**
     * @param bCharacter the bCharacter to set
     */
    public void setbCharacter(String bCharacter) {
        this.bCharacter = bCharacter;
    }

    /**
     * @return the cIssueDate
     */
    public String getcIssueDate() {
        return cIssueDate;
    }

    /**
     * @param cIssueDate the cIssueDate to set
     */
    public void setcIssueDate(String cIssueDate) {
        this.cIssueDate = cIssueDate;
    }

    /**
     * @return the cExpire
     */
    public String getcExpire() {
        return cExpire;
    }

    /**
     * @param cExpire the cExpire to set
     */
    public void setcExpire(String cExpire) {
        this.cExpire = cExpire;
    }


    /**
     * @return the lBalance
     */
    public long getlBalance() {
        return lBalance;
    }

    /**
     * @param lBalance the lBalance to set
     */
    public void setlBalance(long lBalance) {
        this.lBalance = lBalance;
    }

    /**
     * @return the lDeposite
     */
    public long getlDeposite() {
        return lDeposite;
    }

    /**
     * @param lDeposite the lDeposite to set
     */
    public void setlDeposite(long lDeposite) {
        this.lDeposite = lDeposite;
    }

    /**
     * @return the cLine
     */
    public String getcLine() {
        return cLine;
    }

    /**
     * @param cLine the cLine to set
     */
    public void setcLine(String cLine) {
        this.cLine = cLine;
    }

    /**
     * @return the cStationNo
     */
    public String getcStationNo() {
        return cStationNo;
    }

    /**
     * @param cStationNo the cStationNo to set
     */
    public void setcStationNo(String cStationNo) {
        this.cStationNo = cStationNo;
    }

    /**
     * @return the cDateStart
     */
    public String getcDateStart() {
        return cDateStart;
    }

    /**
     * @param cDateStart the cDateStart to set
     */
    public void setcDateStart(String cDateStart) {
        this.cDateStart = cDateStart;
    }

    /**
     * @return the cDateEnd
     */
    public String getcDateEnd() {
        return cDateEnd;
    }

    /**
     * @param cDateEnd the cDateEnd to set
     */
    public void setcDateEnd(String cDateEnd) {
        this.cDateEnd = cDateEnd;
    }

    /**
     * @return the dtDaliyActive
     */
    public String getDtDaliyActive() {
        return dtDaliyActive;
    }

    /**
     * @param dtDaliyActive the dtDaliyActive to set
     */
    public void setDtDaliyActive(String dtDaliyActive) {
        this.dtDaliyActive = dtDaliyActive;
    }

    /**
     * @return the bEffectDay
     */
    public String getbEffectDay() {
        return bEffectDay;
    }

    /**
     * @param bEffectDay the bEffectDay to set
     */
    public void setbEffectDay(String bEffectDay) {
        this.bEffectDay = bEffectDay;
    }

    /**
     * @return the cLimitEntryLine
     */
    public String getcLimitEntryLine() {
        return cLimitEntryLine;
    }

    /**
     * @param cLimitEntryLine the cLimitEntryLine to set
     */
    public void setcLimitEntryLine(String cLimitEntryLine) {
        this.cLimitEntryLine = cLimitEntryLine;
    }

    /**
     * @return the cLimitEntryStation
     */
    public String getcLimitEntryStation() {
        return cLimitEntryStation;
    }

    /**
     * @param cLimitEntryStation the cLimitEntryStation to set
     */
    public void setcLimitEntryStation(String cLimitEntryStation) {
        this.cLimitEntryStation = cLimitEntryStation;
    }

    /**
     * @return the cLimitExitLine
     */
    public String getcLimitExitLine() {
        return cLimitExitLine;
    }

    /**
     * @param cLimitExitLine the cLimitExitLine to set
     */
    public void setcLimitExitLine(String cLimitExitLine) {
        this.cLimitExitLine = cLimitExitLine;
    }

    /**
     * @return the cLimitExitStation
     */
    public String getcLimitExitStation() {
        return cLimitExitStation;
    }

    /**
     * @param cLimitExitStation the cLimitExitStation to set
     */
    public void setcLimitExitStation(String cLimitExitStation) {
        this.cLimitExitStation = cLimitExitStation;
    }

    /**
     * @return the cLimitMode
     */
    public String getcLimitMode() {
        return cLimitMode;
    }

    /**
     * @param cLimitMode the cLimitMode to set
     */
    public void setcLimitMode(String cLimitMode) {
        this.cLimitMode = cLimitMode;
    }

    /**
     * @return the certificateIscompany
     */
    public String getCertificateIscompany() {
        return certificateIscompany;
    }

    /**
     * @param certificateIscompany the certificateIscompany to set
     */
    public void setCertificateIscompany(String certificateIscompany) {
        this.certificateIscompany = certificateIscompany;
    }

    /**
     * @return the certificateIsmetro
     */
    public String getCertificateIsmetro() {
        return certificateIsmetro;
    }

    /**
     * @param certificateIsmetro the certificateIsmetro to set
     */
    public void setCertificateIsmetro(String certificateIsmetro) {
        this.certificateIsmetro = certificateIsmetro;
    }

    /**
     * @return the certificateName
     */
    public String getCertificateName() {
        return certificateName;
    }

    /**
     * @param certificateName the certificateName to set
     */
    public void setCertificateName(String certificateName) {
        this.certificateName = certificateName;
    }

    /**
     * @return the certificateCode
     */
    public String getCertificateCode() {
        return certificateCode;
    }

    /**
     * @param certificateCode the certificateCode to set
     */
    public void setCertificateCode(String certificateCode) {
        this.certificateCode = certificateCode;
    }

    /**
     * @return the certificateType
     */
    public String getCertificateType() {
        return certificateType;
    }

    /**
     * @param certificateType the certificateType to set
     */
    public void setCertificateType(String certificateType) {
        this.certificateType = certificateType;
    }

    /**
     * @return the certificateSex
     */
    public String getCertificateSex() {
        return certificateSex;
    }

    /**
     * @param certificateSex the certificateSex to set
     */
    public void setCertificateSex(String certificateSex) {
        this.certificateSex = certificateSex;
    }

    @Override
    public String toString() {
        return "AnalyzeVo{" + "bIssueStatus=" + bIssueStatus + ", bStatus=" + bStatus + ", cTicketType=" + cTicketType + ", cLogicalID=" + cLogicalID + ", cPhysicalID=" + cPhysicalID + ", bCharacter=" + bCharacter + ", cIssueDate=" + cIssueDate + ", cExpire=" + cExpire + ", cEndExpire=" + cEndExpire + ", rfu=" + rfu + ", lBalance=" + lBalance + ", lDeposite=" + lDeposite + ", cLine=" + cLine + ", cStationNo=" + cStationNo + ", cDateStart=" + cDateStart + ", cDateEnd=" + cDateEnd + ", dtDaliyActive=" + dtDaliyActive + ", bEffectDay=" + bEffectDay + ", cLimitEntryLine=" + cLimitEntryLine + ", cLimitEntryStation=" + cLimitEntryStation + ", cLimitExitLine=" + cLimitExitLine + ", cLimitExitStation=" + cLimitExitStation + ", cLimitMode=" + cLimitMode + ", certificateIscompany=" + certificateIscompany + ", certificateIsmetro=" + certificateIsmetro + ", certificateName=" + certificateName + ", certificateCode=" + certificateCode + ", certificateType=" + certificateType + ", certificateSex=" + certificateSex + ", tradeCount=" + tradeCount + ", employeePositions=" + employeePositions + ", employeeDepartment=" + employeeDepartment + ", employeePositionsTxt=" + employeePositionsTxt + ", employeeDepartmentTxt=" + employeeDepartmentTxt + ", employeeClass=" + employeeClass + ", employeeClassTxt=" + employeeClassTxt + ", cardProducerCode=" + cardProducerCode + '}';
    }

    /**
     * @return the tradeCount
     */
    public int getTradeCount() {
        return tradeCount;
    }

    /**
     * @param tradeCount the tradeCount to set
     */
    public void setTradeCount(int tradeCount) {
        this.tradeCount = tradeCount;
    }

}
