/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.frame.download;

/**
 *
 * @author hejj
 */
public class DownloadFileTccBase {

    protected String getDataTypeName(String dataType) {
        if (dataType.equals("TccEntryStationAllMin")) {
            return "5分钟车站进站量";
        }
        if (dataType.equals("TccEntryLineAllMin")) {
            return "5分钟线路进站量";
        }
        if (dataType.equals("TccExitStationAllMin")) {
            return "5分钟车站出站量";
        }
        if (dataType.equals("TccExitLineAllMin")) {
            return "5分钟线路出站量";
        }
        if (dataType.equals("TccEntryStationTkMin")) {
            return "5分钟车站分票种进站量";
        }
        if (dataType.equals("TccEntryLineTkMin")) {
            return "5分钟线路分票种进站量";
        }
        if (dataType.equals("TccExitStationTkMin")) {
            return "5分钟车站分票种出站量";
        }
        if (dataType.equals("TccExitLineTkMin")) {
            return "5分钟线路分票种出站量";
        }
        if (dataType.equals("TccTrafficLineAllMin")) {
            return "5分钟线路客运量";
        }
        if (dataType.equals("TccDistributeStationAllMin")) {
            return "5分钟车站集散量";
        }
        if (dataType.equals("TccTurnLineAllMin")) {
            return "5分钟线路客运周转量";
        }
        if (dataType.equals("TccSjtUsedDay")) {
            return "日单程票使用数量及比例";
        }
        if (dataType.equals("TccOdStationAllDay")) {
            return "日车站OD客流量";
        }
        if (dataType.equals("TccProfileNetAllHour")) {
            return "小时断面客流量";
        }
        if (dataType.equals("TccLineInfo")) {
            return "线路信息";
        }
        if (dataType.equals("TccProfileInfo")) {
            return "断面信息";
        }
        if (dataType.equals("TccStationInfo")) {
            return "车站信息";
        }
        return "";
    }

}
