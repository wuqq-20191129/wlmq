/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.goldsign.kmsfront.struct.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Reader;

/**
 *
 * @author lenovo
 */
public class FileBufferReader extends java.io.BufferedReader{

    private static final String ENCODE = "gbk";
    
    public FileBufferReader(Reader in) {
        super(in);
    }

    public FileBufferReader(Reader in, int sz) {
        super(in, sz);
    }
    
    public FileBufferReader(File file, String charset) throws Exception{
        this(new InputStreamReader(
                new FileInputStream(file), charset));
    }
    
    public FileBufferReader(File file) throws Exception{
        this(file, ENCODE);
    }

    public FileBufferReader(String fileName, String charset) throws Exception{
        this(new File(fileName), charset);
    }
    
    public FileBufferReader(String fileName) throws Exception{
        this(fileName, ENCODE);
    }
}
