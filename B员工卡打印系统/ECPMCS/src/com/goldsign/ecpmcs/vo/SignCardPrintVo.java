/*
 * 文件名：SignCardPrintVo
 * 版权：Copyright: goldsign (c) 2013
 */

package com.goldsign.ecpmcs.vo;

import com.goldsign.ecpmcs.env.AppConstant;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;


/*
 * 〈票卡打印记录实例〉
 * @author     lindaquan
 * @version    V1.0
 * @createTime 2014-4-11
 */

public class SignCardPrintVo {
    
    private String id;//
    private String name;//姓名
    private String gender;//性别
    private String identityId;//证件号
    private String identityType;//证件类型
    private String cardType;//票卡类型
    private String photoName;//相片名称
    private String printOper;//操作员
    private String printTime;//打印时间
    private String issueTime;//发证日期
    private String remark;//备注
    private String department;//单位
    private String position;//职务
    private String logicalId;//逻辑卡号
    private String employeeClass;//员工等级
    
    private String beginTime;//开始时间
    private String endTime;//结束时间
    
    private String rownum;//查询行数
    
    private String genderTxt;
    private String identityTypeTxt;
    private String cardTypeTxt;
    private String departmentTxt;
    private String employeeClassTxt;
    private String positionTxt;

    public String getLogicalId() {
        return logicalId;
    }

    public void setLogicalId(String logicalId) {
        this.logicalId = logicalId;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getDepartmentTxt() {
        return departmentTxt;
    }

    public String getPositionTxt() {
        return positionTxt;
    }

    public String getRownum() {
        return rownum;
    }

    public void setRownum(String rownum) {
        this.rownum = rownum;
    }

    public String getGenderTxt() {
        return genderTxt;
    }

    public String getIdentityTypeTxt() {
        return identityTypeTxt;
    }

    public String getCardTypeTxt() {
        return cardTypeTxt;
    }

    public String getIdentityId() {
        return identityId;
    }

    public void setIdentityId(String identityId) {
        this.identityId = identityId;
    }

    public String getIdentityType() {
        return identityType;
    }

    public void setIdentityType(String identityType) {
        this.identityType = identityType;
    }

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getPhotoName() {
        return photoName;
    }

    public void setPhotoName(String photoName) {
        this.photoName = photoName;
    }

    public String getPrintOper() {
        return printOper;
    }

    public void setPrintOper(String printOper) {
        this.printOper = printOper;
    }

    public String getPrintTime() {
        return printTime;
    }

    public void setPrintTime(String printTime) {
        this.printTime = printTime;
    }

    public String getIssueTime() {
        return issueTime;
    }

    public void setIssueTime(String issueTime) {
        this.issueTime = issueTime;
    }

    public void setGenderTxt(String genderTxt) {
        this.genderTxt = genderTxt;
    }

    public void setIdentityTypeTxt(String identityTypeTxt) {
        this.identityTypeTxt = identityTypeTxt;
    }

    public void setCardTypeTxt(String cardTypeTxt) {
        this.cardTypeTxt = cardTypeTxt;
    }

    public void setDepartmentTxt(String departmentTxt) {
        this.departmentTxt = departmentTxt;
    }

    public void setPositionTxt(String positionTxt) {
        this.positionTxt = positionTxt;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getEmployeeClass() {
        return employeeClass;
    }

    public void setEmployeeClass(String employeeClass) {
        this.employeeClass = employeeClass;
    }

    public String getEmployeeClassTxt() {
        return employeeClassTxt;
    }

    public void setEmployeeClassTxt(String employeeClassTxt) {
        this.employeeClassTxt = employeeClassTxt;
    }

    /**
     * 取中文
     * @param map
     * @param id
     * @return 
     */
    private String getTxt(Map map, String id) {
        String txt = "";
        Set<String> dkey = map.keySet();
        for (Iterator it = dkey.iterator(); it.hasNext();) {
            String s = (String) it.next();
            if(s.equals(id)){
                txt = String.valueOf(map.get(s));
                break;
            }
        }
        return txt;
    }

}
