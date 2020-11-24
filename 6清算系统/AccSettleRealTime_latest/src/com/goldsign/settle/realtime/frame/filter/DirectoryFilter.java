/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.frame.filter;

import com.goldsign.settle.realtime.frame.util.DateHelper;
import java.io.File;
import java.io.FileFilter;

/**
 *
 * @author hejj
 */
public class DirectoryFilter implements FileFilter {
    private String balanceDate = "";
    private int reserveDays = 0;

    public DirectoryFilter(String balanceDate, int reserveDays) {
        this.balanceDate = balanceDate;
        this.reserveDays = reserveDays;

    }

    @Override
    public boolean accept(File path) {
        if (!path.isDirectory()) {
            return false;
        }
        if (this.isInReserveDays(path, balanceDate, reserveDays)) {
            return false;
        }
        return true;

    }

    private boolean isInReserveDays(File path, String balanceWaterNo, int reserveDays) {
        String balDate = balanceWaterNo.substring(0, 8);
        String dirDate = path.getName().substring(0, 8);
        try {
            long dif = DateHelper.getDifferInDaysForYYYYMMDD(dirDate, balDate);
            if (dif <= reserveDays) {
                return true;
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;

        }

    }
    
}
