/*
 * 文件名：TransferStationVo
 * 版权：Copyright: goldsign (c) 2013
 */

package com.goldsign.rule.vo;


/*
 * 线路换乘站实体
 * @author     lindaquan
 * @version    V1.0
 * @createTime 2013-12-11
 */

public class TransferStationVo {
    
    private String lineId;
    private String stationId;
    private String transferLineId;
    private String transferStationId;

    public String getLineId() {
        return lineId;
    }

    public void setLineId(String lineId) {
        this.lineId = lineId;
    }

    public String getStationId() {
        return stationId;
    }

    public void setStationId(String stationId) {
        this.stationId = stationId;
    }

    public String getTransferLineId() {
        return transferLineId;
    }

    public void setTransferLineId(String transferLineId) {
        this.transferLineId = transferLineId;
    }

    public String getTransferStationId() {
        return transferStationId;
    }

    public void setTransferStationId(String transferStationId) {
        this.transferStationId = transferStationId;
    }
    
}
