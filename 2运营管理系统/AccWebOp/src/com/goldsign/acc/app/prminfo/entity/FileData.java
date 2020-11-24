package com.goldsign.acc.app.prminfo.entity;

import java.util.List;
import java.util.Map;

public class FileData {
    private FileRecordHead head;
    private FileRecordCrc crc;
    private Map<String, List<Object>> content;

    public FileRecordHead getHead() {
        return head;
    }

    public void setHead(FileRecordHead head) {
        this.head = head;
    }

    public FileRecordCrc getCrc() {
        return crc;
    }

    public void setCrc(FileRecordCrc crc) {
        this.crc = crc;
    }

    public Map<String, List<Object>> getContent() {
        return content;
    }

    public void setContent(Map<String, List<Object>> content) {
        this.content = content;
    }
    
    
}
