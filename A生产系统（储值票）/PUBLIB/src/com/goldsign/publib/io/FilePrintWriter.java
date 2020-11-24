/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.goldsign.publib.io;

import java.io.*;

/**
 *
 * @author lenovo
 */
public class FilePrintWriter extends PrintWriter{

    private static final String ENCODE = "utf-8";
    
    public FilePrintWriter(File file, String csn) throws FileNotFoundException, UnsupportedEncodingException {
        super(file, csn);
    }

    public FilePrintWriter(File file) throws FileNotFoundException, UnsupportedEncodingException {
        super(file, ENCODE);
    }

    public FilePrintWriter(String fileName, String csn) throws FileNotFoundException, UnsupportedEncodingException {
        super(fileName, csn);
    }

    public FilePrintWriter(String fileName) throws FileNotFoundException, UnsupportedEncodingException {
        super(fileName, ENCODE);
    }

    public FilePrintWriter(OutputStream out, boolean autoFlush) {
        super(out, autoFlush);
    }

    public FilePrintWriter(OutputStream out) {
        super(out);
    }

    public FilePrintWriter(Writer out, boolean autoFlush) {
        super(out, autoFlush);
    }

    public FilePrintWriter(Writer out) {
        super(out);
    }

    public FilePrintWriter(File file, boolean append) throws IOException{
        this(file, append, ENCODE);
    }
    
    public FilePrintWriter(File file, boolean append, String charset) throws IOException {
        this(new OutputStreamWriter(
                new FileOutputStream(file, append), charset));
    }
}
