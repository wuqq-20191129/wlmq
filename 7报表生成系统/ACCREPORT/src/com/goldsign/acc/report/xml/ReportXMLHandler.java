
package com.goldsign.acc.report.xml;

import com.goldsign.acc.report.constant.XMLConstant;
import com.goldsign.acc.report.util.EncryptionUtil;
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



/**
 * This class is used to read the config file.
 */
public class ReportXMLHandler
        extends DefaultHandler {

    protected final String ENCRYPT_KEY = "GOLDSIGN";
    protected final String NOT_ENCRYPT_PREFIX = "@@";
    private boolean needWriteFile = false;
    private boolean encrypt = false;
    private Hashtable config = new Hashtable();
    private Hashtable ReportDbCP = new Hashtable();
    private Hashtable ReportThreads = new Hashtable();
    private Hashtable ReportServer = new Hashtable();
    
    private CharArrayWriter contents = new CharArrayWriter();
    private boolean inCommon = false;
    private boolean inReportDbCP = false;
    private boolean inReportThreads = false;
    private boolean inReportServer = false;

    public void startElement(String namespaceURI, String localName, String qName,
            Attributes attr) throws SAXException {
        contents.reset();

        if (attr.getValue(XMLConstant.encrypt_ATTR) != null) {
            if (attr.getValue(XMLConstant.encrypt_ATTR).equals("true")) {
                this.encrypt = true;
            }
        }
        if (localName.equals(XMLConstant.Common_TAG)) {
            this.inCommon = true;
        }
        if (localName.equals(XMLConstant.ReportDbCP_TAG)) {
            this.inReportDbCP = true;
        }
        if (localName.equals(XMLConstant.ReportThreads_TAG)) {
            this.inReportThreads = true;
        }
        if (localName.equals(XMLConstant.ReportServer_TAG)) {
            this.inReportServer = true;
        }

    }

    public void endElement(String namespaceURI, String localName, String qName) throws
            SAXException {
        String value;

        value = contents.toString().trim();
        /*if set <tag></tag>,value will be ""
         if (value.equals("")){
         value = null;		//value can not be null,otherwise NullPointerException when Hashtable.put(key,value)
         }
         */

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

        if (inReportDbCP) {
            if (localName.equals(XMLConstant.ReportDbCP_TAG)) {
                inReportDbCP = false;
            } else {
                if (encrypt) {
                    ReportDbCP.put(localName, getUserIdPwd(value));
                    encrypt = false;
                } else {
                    ReportDbCP.put(localName, value);
                }
            }
        }
        if (this.inReportThreads) {
            if (localName.equals(XMLConstant.ReportThreads_TAG)) {
                inReportThreads = false;
            } else {
                if (encrypt) {
                    ReportThreads.put(localName, getUserIdPwd(value));
                    encrypt = false;
                } else {
                    ReportThreads.put(localName, value);
                }
            }
        }
        if (this.inReportServer) {
            if (localName.equals(XMLConstant.ReportServer_TAG)) {
                this.inReportServer = false;
            } else {
                if (encrypt) {
                    this.ReportServer.put(localName, getUserIdPwd(value));
                    encrypt = false;
                } else {
                    this.ReportServer.put(localName, value);
                }
            }
        }

        if (localName.equals(XMLConstant.Config_TAG)) {
            config.put(XMLConstant.ReportDbCP_TAG, ReportDbCP);
            config.put(XMLConstant.ReportThreads_TAG, ReportThreads);
            config.put(XMLConstant.ReportServer_TAG, this.ReportServer);
        }

    }

    public void characters(char[] ch, int start, int length) throws SAXException {
        contents.write(ch, start, length);
    }

    public Hashtable parseConfigFile(String aFileName) throws SAXException,
            FileNotFoundException, IOException {
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

    protected void encryptUserPwdInFile(String aFileName) throws
            FileNotFoundException, IOException {
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
