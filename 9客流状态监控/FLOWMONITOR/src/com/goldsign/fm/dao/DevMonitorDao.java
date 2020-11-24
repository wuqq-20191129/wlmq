/*
 * 文件名：DevMonitorDao
 * 版权：Copyright: goldsign (c) 2013
 */

package com.goldsign.fm.dao;

import com.goldsign.fm.common.AppConstant;
import com.goldsign.fm.common.PubUtil;
import com.goldsign.fm.vo.DevCurrentStateVo;
import com.goldsign.fm.vo.DevMonitorVo;
import com.goldsign.fm.vo.DevStatusImageVo;
import com.goldsign.lib.db.util.DbHelper;
import java.sql.SQLException;
import java.util.Vector;
import org.apache.log4j.Logger;


/*
 * 设备状态图片DAO
 * @author     lindaquan
 * @version    V1.0
 * @createTime 2013-10-18
 */

public class DevMonitorDao {
    
    private static Logger logger = Logger.getLogger(DevMonitorDao.class.getName());
    
    /**
     * 查询车站对应的所有图片
     * @param line_id 线路
     * @param Station_id 车站
     * @return
     * @throws Exception 
     */
    public Vector getDeviceByLineStation(String lineId, String stationId) throws Exception {
        
        DbHelper dbHelper = null;
        String sql = "SELECT node_id, DESCRIPTION, pos_x, pos_y, start_x, start_y, end_x, end_y, node_type,"
                + " device_id, dev_type_id, station_id, line_id, image_url, fontsize, id_x, id_y, nvl(image_direction,'00') image_direction"
                + " FROM w_acc_mn.w_fm_dev_monitor WHERE line_id = ? AND station_id = ?";
        Object[] values = {lineId,stationId};
        boolean result = false;
        Vector v = new Vector();
        DevMonitorVo vo = new DevMonitorVo();

        try {
            dbHelper = new DbHelper("",AppConstant.dbcpHelper2.getConnection());
            result = dbHelper.getFirstDocument(sql, values);
            while (result) {
                vo = this.getMonitorRecord(dbHelper);
                v.add(vo);
                result = dbHelper.getNextDocument();
            }

        } catch (Exception e) {
            PubUtil.handleException(e, logger);
        } finally {
            PubUtil.finalProcessForTran(dbHelper);
        }
        return v;

    }
    
    
    /**
     * 查询所有设备状态对照图片记录
     * @return
     * @throws Exception 
     */
    public Vector getDeviceStatusImage() throws Exception {
        
        DbHelper dbHelper = null;
        String sql = "SELECT dev_type_id, status, image_direction, image_url, DESCRIPTION FROM w_acc_mn.w_fm_dev_status_image";
        boolean result = false;
        Vector v = new Vector();
        DevStatusImageVo vo = new DevStatusImageVo();

        try {
            dbHelper = new DbHelper("",AppConstant.dbcpHelper2.getConnection());
            result = dbHelper.getFirstDocument(sql);
            while (result) {
                vo = this.getStatusImageRecord(dbHelper);
                v.add(vo);
                result = dbHelper.getNextDocument();
            }
            
        } catch (Exception e) {
            PubUtil.handleException(e, logger);
        } finally {
            PubUtil.finalProcessForTran(dbHelper);
        }
        return v;

    }

    
    /**
     * 查询当前设备状态信息
     * @return
     * @throws Exception 
     */
    public Vector getDeviceCurrentStatus(String lineId, String stationId) throws Exception {
        
        DbHelper dbHelper = null;
        String sql = "select A.line_id ,A.station_id ,A.dev_type_id ,A.device_id ,A.acc_status_value ,"
                + " A.status_datetime,A.status_id,A.status_value,B.status_name,B.acc_status_name"
                + " from w_acc_cm.w_cm_dev_current_status A,w_acc_st.w_op_cod_com_status_mapping B"
                + " WHERE A.line_id=? and A.station_id=?"
                + " AND A.status_id = B.status_id(+) and A.status_value = B.status_value(+)";
        
        Object[] values = {lineId,stationId};
        boolean result = false;
        Vector v = new Vector();
        DevCurrentStateVo vo = new DevCurrentStateVo();

        try {
            dbHelper = new DbHelper("",AppConstant.dbcpHelper3.getConnection());
            result = dbHelper.getFirstDocument(sql,values);
            while (result) {
                vo = this.getCurrentStatusRecord(dbHelper);
                v.add(vo);
                result = dbHelper.getNextDocument();
            }

        } catch (Exception e) {
            PubUtil.handleException(e, logger);
        } finally {
            PubUtil.finalProcessForTran(dbHelper);
        }
        return v;

    }

    
    /**
     * 返回对象实例fm_dev_monitor
     * @param dbHelper
     * @return 
     */
    private DevMonitorVo getMonitorRecord(DbHelper dbHelper) throws SQLException {
        DevMonitorVo vo = new DevMonitorVo();
        vo.setDeviceID(dbHelper.getItemValue("device_id"));
        vo.setDeviceType(dbHelper.getItemValue("dev_type_id"));
        vo.setEndX(dbHelper.getItemIntValue("end_x"));
        vo.setEndY(dbHelper.getItemIntValue("end_y"));
        vo.setLineID(dbHelper.getItemValue("line_id"));
        vo.setStationID(dbHelper.getItemValue("station_id"));
        vo.setImageURL(dbHelper.getItemValue("image_url"));
        vo.setFontSize(dbHelper.getItemValue("fontsize"));
        vo.setPosX(dbHelper.getItemIntValue("pos_x"));
        vo.setPosY(dbHelper.getItemIntValue("pos_y"));
        vo.setStartX(dbHelper.getItemIntValue("start_x"));
        vo.setStartY(dbHelper.getItemIntValue("start_y"));
        vo.setNodeType(dbHelper.getItemValue("node_type"));
        vo.setNodeID(dbHelper.getItemValue("node_id"));
        vo.setNodeDescription(dbHelper.getItemValue("DESCRIPTION"));
        vo.setImageDirection(dbHelper.getItemValue("image_direction"));
        
        return vo;
    }
    
    
    /**
     * 返回对象实例fm_dev_status_image
     * @param dbHelper
     * @return 
     */
    private DevStatusImageVo getStatusImageRecord(DbHelper dbHelper) throws SQLException {
        DevStatusImageVo vo = new DevStatusImageVo();
        
        vo.setDescription(dbHelper.getItemValue("DESCRIPTION"));
        vo.setImagURL(dbHelper.getItemValue("image_url"));
        vo.setImageDirection(dbHelper.getItemValue("image_direction"));
        vo.setStatus(dbHelper.getItemValue("status"));
        vo.setDeviceTypeID(dbHelper.getItemValue("dev_type_id"));
        
        return vo;
    }
    
    
    /**
     * 返回对象实例fm_dev_status_image
     * @param dbHelper
     * @return 
     */
    private DevCurrentStateVo getCurrentStatusRecord(DbHelper dbHelper) throws SQLException {
    
        DevCurrentStateVo vo = new DevCurrentStateVo();
        vo.setLineId(dbHelper.getItemValue("line_id"));
        vo.setStationID(dbHelper.getItemValue("station_id"));
        vo.setDeviceTypeId(dbHelper.getItemValue("dev_type_id"));
        vo.setDeviceID(dbHelper.getItemValue("device_id"));
        vo.setAccStatusValue(dbHelper.getItemValue("acc_status_value"));
        vo.setStatusDateTime(dbHelper.getItemDateValue("status_datetime"));
        vo.setStatusId(dbHelper.getItemValue("status_id"));
        vo.setStatusValue(dbHelper.getItemValue("status_value"));
        vo.setStatusName(dbHelper.getItemValue("status_name"));
        vo.setAccStatusName(dbHelper.getItemValue("acc_status_name"));
        
        return vo;
    }


}
