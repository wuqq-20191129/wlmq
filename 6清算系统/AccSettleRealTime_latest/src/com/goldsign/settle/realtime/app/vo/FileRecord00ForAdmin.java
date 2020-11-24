/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.app.vo;

/**
 *
 * @author hejj
 */
public class FileRecord00ForAdmin extends FileRecord00DetailBase{
    private String adminWayId;
    private String adminReasonId;

    /**
     * @return the adminWayId
     */
    public String getAdminWayId() {
        return adminWayId;
    }

    /**
     * @param adminWayId the adminWayId to set
     */
    public void setAdminWayId(String adminWayId) {
        this.adminWayId = adminWayId;
    }

    /**
     * @return the adminReasonId
     */
    public String getAdminReasonId() {
        return adminReasonId;
    }

    /**
     * @param adminReasonId the adminReasonId to set
     */
    public void setAdminReasonId(String adminReasonId) {
        this.adminReasonId = adminReasonId;
    }

  
}
