/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.frame.message;

import com.goldsign.settle.realtime.frame.vo.FileRecordTacBase;
import java.util.Vector;

/**
 *
 * @author hejj
 */
public class MessageTac extends MessageBase{
    private Vector<FileRecordTacBase> datas = new Vector();
    public MessageTac(String fileName,String tradType,Vector datas){
        this.fileName =fileName;
        this.tradType=tradType;
        this.datas = datas;
    }

    /**
     * @return the datas
     */
    public Vector<FileRecordTacBase> getDatas() {
        return datas;
    }

    /**
     * @param datas the datas to set
     */
    public void setDatas(Vector<FileRecordTacBase> datas) {
        this.datas = datas;
    }
    
    
}
