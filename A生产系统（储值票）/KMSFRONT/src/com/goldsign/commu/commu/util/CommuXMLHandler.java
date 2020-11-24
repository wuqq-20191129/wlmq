package com.goldsign.commu.commu.util;

import com.goldsign.commu.commu.env.ConfigConstant;
import java.io.*;
import java.util.Hashtable;
import java.util.Vector;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

/**
 * This class is used to read the config file.
 */
public class CommuXMLHandler extends DefaultHandler {

    protected final String ENCRYPT_KEY = "OCT";
    protected final String NOT_ENCRYPT_PREFIX = "@@";
    private boolean needWriteFile = false;
    private boolean encrypt = false;
    private static final String encrypt_ATTR = "encrypt";
    private static final String id_ATTR = "id";
    private static final String value_ATTR = "value";
    private Hashtable config = new Hashtable();
    private Hashtable dataConnectionPool = new Hashtable();
    private Hashtable connectionPoolListenerThread = new Hashtable();
    private Hashtable communicationThreadPool = new Hashtable();
    private Hashtable messages = new Hashtable();
    private Vector ipConfgis = new Vector();
    private CharArrayWriter contents = new CharArrayWriter();
    private boolean inCommon = false;
    private boolean inDataConnectionPool = false;
    private boolean inCommunicationThreadPool = false;
    private boolean inConnectionPoolListenerThread = false;

    public void startElement(String namespaceURI, String localName, String qName, Attributes attr) throws SAXException {
        contents.reset();

        if (attr.getValue(encrypt_ATTR) != null) {
            if (attr.getValue(encrypt_ATTR).equals("true")) {
                this.encrypt = true;
            }
        }
        if (localName.equals(ConfigConstant.Common_TAG)) {
            this.inCommon = true;
        }
        if (localName.equals(ConfigConstant.Message_TAG)) {
            messages.put(attr.getValue(id_ATTR), attr.getValue(value_ATTR));
        }
        if (localName.equals(ConfigConstant.IpConfig_TAG)) {
            ipConfgis.add(attr.getValue(value_ATTR));
        }
        if (localName.equals(ConfigConstant.DataConnectionPool_TAG)) {
            this.inDataConnectionPool = true;
        }

        if (localName.equals(ConfigConstant.CommunicationThreadPool_TAG)) {
            this.inCommunicationThreadPool = true;
        }
        if (localName.equals(ConfigConstant.ConnectionPoolListenerThread_TAG)) {
            this.inConnectionPoolListenerThread = true;
        }

    }

    public void endElement(String namespaceURI, String localName, String qName) throws SAXException {
        String value;

        value = contents.toString().trim();
        /*
         * if set <tag></tag>,value will be "" if (value.equals("")){ value =
         * null;	//value can not be null,otherwise NullPointerException when
         * Hashtable.put(key,value) }
         */

        if (inCommon) {
            if (localName.equals(ConfigConstant.Common_TAG)) {
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


        if (this.inDataConnectionPool) {
            if (localName.equals(ConfigConstant.DataConnectionPool_TAG)) {
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

        if (this.inCommunicationThreadPool) {
            if (localName.equals(ConfigConstant.CommunicationThreadPool_TAG)) {
                this.inCommunicationThreadPool = false;
            } else {
                this.communicationThreadPool.put(localName, value);
            }
        }

        if (this.inConnectionPoolListenerThread) {
            if (localName.equals(ConfigConstant.ConnectionPoolListenerThread_TAG)) {
                this.inConnectionPoolListenerThread = false;
            } else {
                this.connectionPoolListenerThread.put(localName, value);
            }
        }

        if (localName.equals(ConfigConstant.Config_TAG)) {
            config.put(ConfigConstant.Messages_TAG, messages);
            config.put(ConfigConstant.IpConfigs_TAG, ipConfgis);
            config.put(ConfigConstant.DataConnectionPool_TAG, this.dataConnectionPool);
            config.put(ConfigConstant.CommunicationThreadPool_TAG, this.communicationThreadPool);
            config.put(ConfigConstant.ConnectionPoolListenerThread_TAG, this.connectionPoolListenerThread);

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
