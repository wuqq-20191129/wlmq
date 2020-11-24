/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.frame.vo;

import java.io.File;

/**
 *
 * @author hejj
 */
public class FileFindResult {
    private boolean existed= false;
    private File[] files =null;

    /**
     * @return the existed
     */
    public boolean isExisted() {
        return existed;
    }

    /**
     * @param existed the existed to set
     */
    public void setExisted(boolean existed) {
        this.existed = existed;
    }

    /**
     * @return the files
     */
    public File[] getFiles() {
        return files;
    }

    /**
     * @param files the files to set
     */
    public void setFiles(File[] files) {
        this.files = files;
    }
    
}
