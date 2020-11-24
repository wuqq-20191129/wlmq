/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.goldsign.fm.vo;

/**
 *
 * @author Administrator
 */


/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2005-8-26
 * Time: 14:35:47
 * To change this template use File | Settings | File Templates.
 */
public class ViewVo {

    private String line_id="";  //线路 ＩＤ
    private String line_name="";//线路名称　
    private String traffic="";  //客流数量
    private String total="";   //统计
    private String totalIn="";   //进站统计
    private String totalOut="";   //出站统计
    private String station_id="";      //车站ＩＤ
    private String station_name="";   //车站名称
    private String device_id="";      //设置ＩＤ
    private String iccs_status_value="";    //状态值　
    private String datetime="";           //时间
    private String traffic_in="";        //进站客流量
    private String traffic_out="";       //出站客流量
    private String traffic_in_name="";        //进站名称
    private String traffic_out_name="";       //出站名称
    private String imagePath="";//对应的图片路径
    private String mainCardID ="";
    private String mainCardName ="";
    private String subCardID = "";
    private String subCardName= "";
    private String traffic_in_total="";        //进站总客流量
    private String traffic_out_total="";       //出站总客流量

    private boolean isTotal =false;



    public String getTraffic_in_total() {
      if(this.traffic_in_total ==null)
        return "";
        return this.traffic_in_total ;
    }
    public String getTraffic_out_total() {
      if(this.traffic_out_total ==null)
        return "";
        return this.traffic_out_total ;
    }
    public void setTraffic_in_total(String traffic_in_total){
    	this.traffic_in_total  = traffic_in_total;
    }
    public void setTraffic_out_total(String traffic_out_total){
    	this.traffic_out_total  = traffic_out_total;
    }
    public String getTraffic_in() {
      if(this.traffic_in ==null)
        return "";
        return traffic_in;
    }
    public void setImagePath(String imagePath) {
      if(imagePath != null)
        this.imagePath = imagePath;
    }

    public String getImagePath() {
      if(this.imagePath ==null)
        return "";
        return this.imagePath;
    }
    public void setMainCardID(String mainCardID) {
     if(mainCardID != null)
       this.mainCardID = mainCardID;
   }

   public String getMainCardID() {
     if(this.mainCardID ==null)
        return "";
       return this.mainCardID;
   }
   public void setMainCardName(String mainCardName) {
     if(mainCardName != null)
       this.mainCardName = mainCardName;
   }

   public String getMainCardName() {
     if(this.mainCardName ==null)
        return "";
       return this.mainCardName;
   }
   public void setSubCardID(String subCardID) {
     if(subCardID != null)
       this.subCardID = subCardID;
   }

   public String getSubCardID() {
     if(this.subCardID ==null)
        return "";
       return this.subCardID;
   }
   public void setSubCardName(String subCardName) {
     if(subCardName != null)
       this.subCardName = subCardName;
   }

   public String getSubCardName() {
     if(this.subCardName ==null)
        return "";
       return this.subCardName;
   }




    public void setTraffic_in_name(String traffic_in_name) {
      if(traffic_in_name != null)
        this.traffic_in_name = traffic_in_name;
    }

    public String getTraffic_in_name() {
      if(this.traffic_in_name ==null)
        return "";
        return this.traffic_in_name;
    }
    public void setTraffic_out_name(String traffic_out_name) {
     if(traffic_out_name != null)
       this.traffic_out_name = traffic_out_name;
   }

   public String getTraffic_out_name() {
     if(this.traffic_out_name ==null)
        return "";
       return this.traffic_out_name;
   }




    public void setTraffic_in(String traffic_in) {
        this.traffic_in = traffic_in;
    }

    public String getTraffic_out() {
      if(this.traffic_out ==null)
        return "";
        return traffic_out;
    }

    public void setTraffic_out(String traffic_out) {
        this.traffic_out = traffic_out;
    }



    public String getStation_id() {
      if(this.station_id ==null)
        return "";
        return station_id;
    }

    public void setStation_id(String station_id) {
        this.station_id = station_id;
    }

    public String getStation_name() {
      if(this.station_name ==null)
        return "";
        return station_name;
    }

    public void setStation_name(String station_name) {
        this.station_name = station_name;
    }

    public String getDevice_id() {
      if(this.device_id ==null)
        return "";
        return device_id;
    }

    public void setDevice_id(String device_id) {
        this.device_id = device_id;
    }

    public String getIccs_status_value() {
      if(this.iccs_status_value ==null)
        return "";
        return iccs_status_value;
    }

    public void setIccs_status_value(String iccs_status_value) {
        this.iccs_status_value = iccs_status_value;
    }

    public String getDatetime() {
      if(this.datetime ==null)
        return "";
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }


    public String getLine_name() {
      if(this.line_name ==null)
        return "";
        return line_name;
    }

    public void setLine_name(String line_name) {
        this.line_name = line_name;
    }


    public String getTotal() {
      if(this.total ==null)
        return "";
      return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }


    public String getLine_id() {
      if(this.line_id ==null)
        return "";
        return line_id;
    }

    public void setLine_id(String line_id) {
        this.line_id = line_id;
    }

    public String getTraffic() {
      if(this.traffic ==null)
        return "";
        return traffic;
    }

    public void setTraffic(String traffic) {
        this.traffic = traffic;
    }

    /**
     * @return the totalIn
     */
    public String getTotalIn() {
        return totalIn;
    }

    /**
     * @param totalIn the totalIn to set
     */
    public void setTotalIn(String totalIn) {
        this.totalIn = totalIn;
    }

    /**
     * @return the totalOut
     */
    public String getTotalOut() {
        return totalOut;
    }

    /**
     * @param totalOut the totalOut to set
     */
    public void setTotalOut(String totalOut) {
        this.totalOut = totalOut;
    }

    /**
     * @return the isTotal
     */
    public boolean isIsTotal() {
        return isTotal;
    }

    /**
     * @param isTotal the isTotal to set
     */
    public void setIsTotal(boolean isTotal) {
        this.isTotal = isTotal;
    }


}

