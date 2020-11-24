/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.frame.handler;


import com.goldsign.settle.realtime.frame.exception.FileNameException;

import com.goldsign.settle.realtime.frame.exception.RecordParseForFileException;

import com.goldsign.settle.realtime.frame.util.FileNameParseUtil;

import com.goldsign.settle.realtime.frame.vo.FileRecordCrc;
import com.goldsign.settle.realtime.frame.vo.FileRecordHead;

import org.apache.log4j.Logger;

/**
 *
 * @author hejj
 */
public class HandlerOctCJ extends HandlerOctBase{
     private static Logger logger = Logger.getLogger(HandlerOctCJ.class.getName());

    
    public void parseFileName(String fileName) throws FileNameException {
        FileNameParseUtil util = new FileNameParseUtil();
        util.parseLen(fileName, FileNameParseUtil.FILE_LEN_OCT_IMPORT);
        util.parseFmtForOctImport(fileName, FileNameParseUtil.FILE_START_OCT_AUS);
        util.parseDate(fileName);

    }
    

    public void checkFileContent(FileRecordHead fh, FileRecordCrc frc, String fileName, int rowCount, StringBuffer sb) throws RecordParseForFileException {
       
    }
    
}
