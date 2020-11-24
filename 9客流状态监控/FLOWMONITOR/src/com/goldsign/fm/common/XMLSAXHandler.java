/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.fm.common;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.InputSource;
import org.xml.sax.helpers.XMLReaderFactory;
import org.xml.sax.helpers.DefaultHandler;
import java.io.CharArrayWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Hashtable;

public class XMLSAXHandler extends DefaultHandler {

    protected final String ENCRYPT_KEY = "GOLDSIGN";
    protected final String NOT_ENCRYPT_PREFIX = "@@";
    private boolean needWriteFile = false;
    private boolean encrypt = false;
    private static final String Config_TAG = "Config";
    public static final String DataConnectionPool_TAG = "DataConnectionPool";
    public static final String ConnectionPool_Database_TAG = "Database";
    
    public static final String ConnectionPool_Driver_TAG1 = "Driver1";
    public static final String ConnectionPool_URL_TAG1 = "URL1";
    public static final String ConnectionPool_User_TAG1 = "User1";
    public static final String ConnectionPool_Password_TAG1 = "Password1";
    
    public static final String ConnectionPool_Driver_TAG2 = "Driver2";
    public static final String ConnectionPool_URL_TAG2 = "URL2";
    public static final String ConnectionPool_User_TAG2 = "User2";
    public static final String ConnectionPool_Password_TAG2 = "Password2";
    
    public static final String ConnectionPool_Driver_TAG3 = "Driver3";
    public static final String ConnectionPool_URL_TAG3 = "URL3";
    public static final String ConnectionPool_User_TAG3 = "User3";
    public static final String ConnectionPool_Password_TAG3 = "Password3";
    
    public static final String ConnectionPool_MaxActive_TAG = "MaxActive";
    public static final String ConnectionPool_MaxIdle_TAG = "MaxIdle";
    public static final String ConnectionPool_MaxWait_TAG = "MaxWait";
    public static final String Common_TAG = "Common";
    public static final String Common_BackUpLog_TAG = "BackUpLog";
    public static final String Common_FreshInterval_TAG = "FreshInterval";
    public static final String Common_isLocked_TAG = "isLocked";
    public static final String Common_ColorLine_TAG = "ColorLine";//xml配置文件线路颜色标签
    public static final String Common_ColorLine5Min_TAG = "Color5MinLine";//xml配置文件线路颜色标签
    public static final String Common_ColorCard_TAG = "ColorCard";//xml配置文件票卡类型颜色标签
    public static final String Common_ScreenW_TAG = "ScreenW";//xml配置文件设备画图工具使用屏幕宽标签
    public static final String Common_ScreenH_TAG = "ScreenH";//xml配置文件设备画图工具使用屏幕长标签
    private static final String encrypt_ATTR = "encrypt";
    private static final String id_ATTR = "id";
    private static final String value_ATTR = "value";
    private Hashtable config = new Hashtable();
    private Hashtable dataConnectionPool = new Hashtable();
    private Hashtable common = new Hashtable();
    private CharArrayWriter contents = new CharArrayWriter();
    private boolean inDataConnectionPool = false;
    private boolean inCommon = false;

    /**
     * Creates a new instance of XMLSAXHandler
     */
    public XMLSAXHandler() {
    }

    public void startElement(String namespaceURI, String localName, String qName, Attributes attr) throws SAXException {
        contents.reset();

        if (attr.getValue(encrypt_ATTR) != null) {
            if (attr.getValue(encrypt_ATTR).equals("true")) {
                this.encrypt = true;
            }
        }

        if (localName.equals(XMLSAXHandler.DataConnectionPool_TAG)) {
            this.inDataConnectionPool = true;
        }
        if (localName.equals(XMLSAXHandler.Common_TAG)) {
            this.inCommon = true;
        }


    }

    public void endElement(String namespaceURI, String localName, String qName) throws SAXException {
        String value;

        value = contents.toString().trim();

        if (this.inDataConnectionPool) {
            if (localName.equals(XMLSAXHandler.DataConnectionPool_TAG)) {
                this.inDataConnectionPool = false;
            } else {
                if (encrypt) {
                    this.dataConnectionPool.put(localName, getUserIdPwd(value));
                    encrypt = false;
                } else {
                    this.dataConnectionPool.put(localName, value);
                }
            }
        }
        if (this.inCommon) {
            if (localName.equals(XMLSAXHandler.Common_TAG)) {
                this.inCommon = false;
            } else {
                if (encrypt) {
                    this.common.put(localName, getUserIdPwd(value));
                    encrypt = false;
                } else {
                    this.common.put(localName, value);
                }
            }
        }

        if (localName.equals(Config_TAG)) {
            config.put(XMLSAXHandler.DataConnectionPool_TAG, this.dataConnectionPool);
            config.put(XMLSAXHandler.Common_TAG, this.common);
        }

    }

    public void characters(char[] ch, int start, int length) throws SAXException {
        contents.write(ch, start, length);
    }

    public Hashtable parseConfigFile(String aFileName) throws SAXException, FileNotFoundException, IOException {
        XMLReader xr;

        xr = XMLReaderFactory.createXMLReader("org.apache.xerces.parsers.SAXParser");
        xr.setContentHandler(this);
        xr.parse(new InputSource(new FileReader(aFileName)));

        // Save the encrypted pasword to the config file
        if (needWriteFile) {
            encryptUserPwdInFile(aFileName);
        }

        return config;
    }

    protected void encryptUserPwdInFile(String aFileName) throws FileNotFoundException, IOException {
        int pos, posEnd, posCom1, posCom2;
        String value;
        StringBuffer fileContent = new StringBuffer();
        Encryption encryption = new Encryption();

        // Read the config file
        BufferedReader br = new BufferedReader(new FileReader(aFileName));
        String line = br.readLine();
        while (line != null) {
            // Skip the comment line
            posCom1 = line.indexOf("<!--");
            posCom2 = line.indexOf("-->");

            pos = line.indexOf(NOT_ENCRYPT_PREFIX);
            // Encrypt the userId or password
            if (pos != -1 && posCom1 == -1 && posCom2 == -1) {
                StringBuffer lineBuf = new StringBuffer(line);

                // Search the end position
                posEnd = line.indexOf("<", pos);

                // Encrypt and replace
                value = line.substring(pos + NOT_ENCRYPT_PREFIX.length(), posEnd).trim();
                value = encryption.biEncrypt(ENCRYPT_KEY, value);
                line = lineBuf.replace(pos, posEnd, value).toString();

            }

            fileContent.append("\r\n").append(line);

            line = br.readLine();
        }

        // Write new content to the config file
        FileWriter fw = new FileWriter(aFileName);
        fw.write(fileContent.toString().substring(2));
        fw.flush();
        fw.close();
    }

    protected String getUserIdPwd(String aUserIdPwd) {
        String result = "";
        Encryption encryption = new Encryption();

        if (aUserIdPwd.startsWith(NOT_ENCRYPT_PREFIX)) {
            result = aUserIdPwd.substring(NOT_ENCRYPT_PREFIX.length());
            needWriteFile = true;
        } else {
            result = encryption.biDecrypt(ENCRYPT_KEY, aUserIdPwd);
        }

        return result;
    }
}
