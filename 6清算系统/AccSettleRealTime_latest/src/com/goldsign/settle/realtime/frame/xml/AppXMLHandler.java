/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.frame.xml;

import com.goldsign.settle.realtime.frame.constant.XMLConstant;
import com.goldsign.settle.realtime.frame.util.EncryptionUtil;
import java.io.BufferedReader;
import java.io.CharArrayWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Hashtable;
import org.apache.log4j.Logger;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

/**
 *
 * @author hejj
 */
public class AppXMLHandler extends DefaultHandler {

    private CharArrayWriter contents = new CharArrayWriter();
    public final static String ENCRYPT_KEY = "GOLDSIGN";
    protected final String NOT_ENCRYPT_PREFIX = "@@";
    private boolean needWriteFile = false;
    private boolean encrypt = false;
    /**
     * 解释标签判断
     */
    private boolean inCommon = false;
    private boolean inDataConnectionPool = false;
    private boolean inThreadPoolFile = false;
    private boolean inThreadPoolBcp = false;
    private boolean inThreadPoolTac = false;
    private boolean inThreadPoolOther = false;
    private boolean inThreadPoolFileMobile = false;
    private boolean inThreadPoolOtherMobile = false;
    private boolean inThreadPoolFileNetPaid = false;
    private boolean inThreadPoolOtherNetPaid = false;

    private boolean inThreadPoolFileQrCode = false;
    private boolean inThreadPoolOtherQrCode = false;

    /**
     * 存储集合
     */
    private Hashtable config = new Hashtable();
    private Hashtable dataConnectionPool = new Hashtable();
    private Hashtable threadPoolFile = new Hashtable();
    private Hashtable threadPoolBcp = new Hashtable();
    private Hashtable threadPoolTac = new Hashtable();
    private Hashtable threadPoolOther = new Hashtable();
    private Hashtable threadPoolFileMobile = new Hashtable();
    private Hashtable threadPoolOtherMobile = new Hashtable();
    private Hashtable threadPoolFileNetPaid = new Hashtable();//added by hejj 20161124
    private Hashtable threadPoolOtherNetPaid = new Hashtable();//added by hejj 20161124
    private Hashtable threadPoolFileQrCode = new Hashtable();//added by 20190612
    private Hashtable threadPoolOtherQrCode = new Hashtable();//added by 20190612

    public void startElement(String namespaceURI, String localName, String qName, Attributes attr) throws SAXException {
        contents.reset();

        if (attr.getValue(XMLConstant.ATTR_ENCRYPT) != null) {
            if (attr.getValue(XMLConstant.ATTR_ENCRYPT).equals("true")) {
                this.encrypt = true;
            }
        }
        /**
         * ******************设置标签开始解释标志****************************************************
         */
        if (localName.equals(XMLConstant.Common_TAG)) {
            this.inCommon = true;
        }

        if (localName.equals(XMLConstant.DataConnectionPool_TAG)) {
            this.inDataConnectionPool = true;
        }
        if (localName.equals(XMLConstant.ThreadPoolFile_TAG)) {
            this.inThreadPoolFile = true;
        }
        if (localName.equals(XMLConstant.ThreadPoolFileMobile_TAG)) {//add by hejj 20160115
            this.inThreadPoolFileMobile = true;
        }
        if (localName.equals(XMLConstant.ThreadPoolFileQrCode_TAG)) {//add by hejj 20190612
            this.inThreadPoolFileQrCode = true;
        }

        if (localName.equals(XMLConstant.ThreadPoolFileNetPaid_TAG)) {//add by hejj 20161124
            this.inThreadPoolFileNetPaid = true;
        }

        if (localName.equals(XMLConstant.ThreadPoolBcp_TAG)) {
            this.inThreadPoolBcp = true;
        }
        if (localName.equals(XMLConstant.ThreadPoolTac_TAG)) {
            this.inThreadPoolTac = true;
        }
        if (localName.equals(XMLConstant.ThreadPoolOther_TAG)) {
            this.inThreadPoolOther = true;
        }
        if (localName.equals(XMLConstant.ThreadPoolOtherMobile_TAG)) {
            this.inThreadPoolOtherMobile = true;
        }
        if (localName.equals(XMLConstant.ThreadPoolOtherNetPaid_TAG)) {//add by hejj 20161124
            this.inThreadPoolOtherNetPaid = true;
        }
        if (localName.equals(XMLConstant.ThreadPoolOtherQrCode_TAG)) {//add by hejj 20190612
            this.inThreadPoolOtherQrCode = true;
        }
        /**
         * **********************设置标签开始解释标志结束***************************************
         */
    }

    public void endElement(String namespaceURI, String localName, String qName) throws SAXException {
        String value;

        value = contents.toString().trim();

//通用配置
        if (inCommon) {
            if (localName.equals(XMLConstant.Common_TAG)) {
                inCommon = false;
            } else {
                if (encrypt) {
                    config.put(localName, getUserIdPwd(value));
                    encrypt = false;
                } else {
                    config.put(localName, value);
                }
            }
        }

//连接程池配置
        if (this.inDataConnectionPool) {
            if (localName.equals(XMLConstant.DataConnectionPool_TAG)) {
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
        //交易文件线程池配置
        if (this.inThreadPoolFile) {
            if (localName.equals(XMLConstant.ThreadPoolFile_TAG)) {
                this.inThreadPoolFile = false;
            } else {
                if (encrypt) {
                    this.threadPoolFile.put(localName, getUserIdPwd(value));
                    encrypt = false;
                } else {
                    this.threadPoolFile.put(localName, value);
                }
            }
        }
        //BCP线程池配置
        if (this.inThreadPoolBcp) {
            if (localName.equals(XMLConstant.ThreadPoolBcp_TAG)) {
                this.inThreadPoolBcp = false;
            } else {
                if (encrypt) {
                    this.threadPoolBcp.put(localName, getUserIdPwd(value));
                    encrypt = false;
                } else {
                    this.threadPoolBcp.put(localName, value);
                }
            }
        }
        //TAC线程池配置
        if (this.inThreadPoolTac) {
            if (localName.equals(XMLConstant.ThreadPoolTac_TAG)) {
                this.inThreadPoolTac = false;
            } else {
                if (encrypt) {
                    this.threadPoolTac.put(localName, getUserIdPwd(value));
                    encrypt = false;
                } else {
                    this.threadPoolTac.put(localName, value);
                }
            }
        }

        //其他线程池配置
        if (this.inThreadPoolOther) {
            if (localName.equals(XMLConstant.ThreadPoolOther_TAG)) {
                this.inThreadPoolOther = false;
            } else {
                if (encrypt) {
                    this.threadPoolOther.put(localName, getUserIdPwd(value));
                    encrypt = false;
                } else {
                    this.threadPoolOther.put(localName, value);
                }
            }
        }

        //手机支付交易文件线程池配置
        if (this.inThreadPoolFileMobile) {
            if (localName.equals(XMLConstant.ThreadPoolFileMobile_TAG)) {
                this.inThreadPoolFileMobile = false;
            } else {
                if (encrypt) {
                    this.threadPoolFileMobile.put(localName, getUserIdPwd(value));
                    encrypt = false;
                } else {
                    this.threadPoolFileMobile.put(localName, value);
                }
            }
        }
        //二维码平台交易文件线程池配置 20190612
        if (this.inThreadPoolFileQrCode) {
            if (localName.equals(XMLConstant.ThreadPoolFileQrCode_TAG)) {
                this.inThreadPoolFileQrCode = false;
            } else {
                if (encrypt) {
                    this.threadPoolFileQrCode.put(localName, getUserIdPwd(value));
                    encrypt = false;
                } else {
                    this.threadPoolFileQrCode.put(localName, value);
                }
            }
        }

        //互联网支付交易文件线程池配置added by hejj 20161124
        if (this.inThreadPoolFileNetPaid) {
            if (localName.equals(XMLConstant.ThreadPoolFileNetPaid_TAG)) {
                this.inThreadPoolFileNetPaid = false;
            } else {
                if (encrypt) {
                    this.threadPoolFileNetPaid.put(localName, getUserIdPwd(value));
                    encrypt = false;
                } else {
                    this.threadPoolFileNetPaid.put(localName, value);
                }
            }
        }

        //手机支付其他线程池配置
        if (this.inThreadPoolOtherMobile) {
            if (localName.equals(XMLConstant.ThreadPoolOtherMobile_TAG)) {
                this.inThreadPoolOtherMobile = false;
            } else {
                if (encrypt) {
                    this.threadPoolOtherMobile.put(localName, getUserIdPwd(value));
                    encrypt = false;
                } else {
                    this.threadPoolOtherMobile.put(localName, value);
                }
            }
        }

        //二维码平台其他线程池配置 20190612
        if (this.inThreadPoolOtherMobile) {
            if (localName.equals(XMLConstant.ThreadPoolOtherQrCode_TAG)) {
                this.inThreadPoolOtherQrCode = false;
            } else {
                if (encrypt) {
                    this.threadPoolOtherQrCode.put(localName, getUserIdPwd(value));
                    encrypt = false;
                } else {
                    this.threadPoolOtherQrCode.put(localName, value);
                }
            }
        }

        //互联网支付其他线程池配置 added by hejj 20161124
        if (this.inThreadPoolOtherNetPaid) {
            if (localName.equals(XMLConstant.ThreadPoolOtherNetPaid_TAG)) {
                this.inThreadPoolOtherNetPaid = false;
            } else {
                if (encrypt) {
                    this.threadPoolOtherNetPaid.put(localName, getUserIdPwd(value));
                    encrypt = false;
                } else {
                    this.threadPoolOtherNetPaid.put(localName, value);
                }
            }
        }

        if (localName.equals(XMLConstant.Config_TAG)) {

            config.put(XMLConstant.DataConnectionPool_TAG, this.dataConnectionPool);
            config.put(XMLConstant.ThreadPoolFile_TAG, this.threadPoolFile);
            config.put(XMLConstant.ThreadPoolBcp_TAG, this.threadPoolBcp);
            config.put(XMLConstant.ThreadPoolTac_TAG, this.threadPoolTac);
            config.put(XMLConstant.ThreadPoolOther_TAG, this.threadPoolOther);

            config.put(XMLConstant.ThreadPoolFileMobile_TAG, this.threadPoolFileMobile);//added by hejj 20160115
            config.put(XMLConstant.ThreadPoolOtherMobile_TAG, this.threadPoolOtherMobile);//added by hejj 20160115

            config.put(XMLConstant.ThreadPoolFileNetPaid_TAG, this.threadPoolFileMobile);//added by hejj 20161124
            config.put(XMLConstant.ThreadPoolOtherNetPaid_TAG, this.threadPoolOtherMobile);//added by hejj 20161124

            config.put(XMLConstant.ThreadPoolFileQrCode_TAG, this.threadPoolFileQrCode);//added by hejj 20190612
            config.put(XMLConstant.ThreadPoolOtherQrCode_TAG, this.threadPoolOtherQrCode);//added by hejj 20190612

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
        EncryptionUtil encryption = new EncryptionUtil();

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
        EncryptionUtil encryption = new EncryptionUtil();

        if (aUserIdPwd.startsWith(NOT_ENCRYPT_PREFIX)) {
            result = aUserIdPwd.substring(NOT_ENCRYPT_PREFIX.length());
            needWriteFile = true;
        } else {
            result = encryption.biDecrypt(ENCRYPT_KEY, aUserIdPwd);
        }

        return result;
    }
}
