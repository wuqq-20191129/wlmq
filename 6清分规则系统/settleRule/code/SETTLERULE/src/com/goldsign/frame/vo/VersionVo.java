/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.frame.vo;

/**
 *
 * @author hejj
 */
public class VersionVo {
    private String version_num = null;   //版本号

	private String sequ = null;//序号

	private String parameter_type = null;//参数类型
	private String b_ava_time = null;    //版本起始时间
	private String e_ava_time = null;    //版本结束时间
	private String ver_date = null;//版本生成时间
	private String oper_id = null; //版本生成操作员
	private String ver_type = null;//版本类型
	

	private String b_station = null; //起始车站
	private String e_station = null; //结束车站
	private String contc_id = null;  //运营商
	private String pay_type = null; //支付方式
	
	private String in_percent = null; //分帐比例
	
	public String getVer_type(){
		return ver_type;
	}
	
	public void setVer_type(String ver_type){
		this.ver_type = ver_type;
	}
	
	public String getVer_date(){
		return ver_date;		
	}
	
	public void setVer_date(String ver_date){
		this.ver_date = ver_date;
	}
	
	public String getOper_id(){
		return oper_id;
	}
	
	public void setOper_id(String oper_id){
		this.oper_id = oper_id;
	}
	
	public String getB_station() {
		return b_station;
	}
	public void setB_station(String b_station) {
		this.b_station = b_station;
	}
	public String getE_station() {
		return e_station;
	}
	public void setE_station(String e_station) {
		this.e_station = e_station;
	}
	public String getContc_id() {
		return contc_id;
	}
	public void setContc_id(String contc_id) {
		this.contc_id = contc_id;
	}
	public String getPay_type() {
		return pay_type;
	}
	public void setPay_type(String pay_type) {
		this.pay_type = pay_type;
	}
	
	public String getB_ava_time() {
		return b_ava_time;
	}
	public void setB_ava_time(String b_ava_time) {
		this.b_ava_time = b_ava_time;
	}
	public String getE_ava_time() {
		return e_ava_time;
	}
	public void setE_ava_time(String e_ava_time) {
		this.e_ava_time = e_ava_time;
	}
	public String getParameter_type() {
		return parameter_type;
	}
	public void setParameter_type(String parameter_type) {
		this.parameter_type = parameter_type;
	}
	public String getVersion_num() {
		return version_num;
	}
	public void setVersion_num(String version_num) {
		this.version_num = version_num;
	}
	public String getIn_percent() {
		return in_percent;
	}
	public void setIn_percent(String in_percent) {
		this.in_percent = in_percent;
	}
	
	public String getSequ() {
		return sequ;
	}

	public void setSequ(String sequ) {
		this.sequ = sequ;
	}
    
}
