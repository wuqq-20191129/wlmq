/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.frame.vo;


import java.util.HashMap;
import java.util.Vector;

/**
 *
 * @author hejj
 */
public class FileData {
    private FileRecordHead head;
    private HashMap<String,Vector> content;
    private FileRecordCrc crc;

    /**
     * @return the head
     */
    public FileRecordHead getHead() {
        return head;
    }

    /**
     * @param head the head to set
     */
    public void setHead(FileRecordHead head) {
        this.head = head;
    }

    /**
     * @return the content
     */
    public HashMap<String,Vector> getContent() {
        return content;
    }

    /**
     * @param content the content to set
     */
    public void setContent(HashMap<String,Vector> content) {
        this.content = content;
    }

    /**
     * @return the crc
     */
    public FileRecordCrc getCrc() {
        return crc;
    }

    /**
     * @param crc the crc to set
     */
    public void setCrc(FileRecordCrc crc) {
        this.crc = crc;
    }
    
}
