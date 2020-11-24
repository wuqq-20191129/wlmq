package com.goldsign.csfrm.util;

import com.goldsign.csfrm.vo.XmlConfigVo;
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
public class XMLConfigHandler extends DefaultHandler {

    protected final String ENCRYPT_KEY = "GOLDSIGN";
    protected final String NOT_ENCRYPT_PREFIX = "@@";
    private boolean needWriteFile = false;
    private boolean encrypt = false;
    private static final String encrypt_ATTR = "encrypt";
    private static final String id_ATTR = "id";
    private static final String value_ATTR = "value";
    private CharArrayWriter contents = new CharArrayWriter();
    private Vector<XmlConfigVo> configVos;

    /**
     * 开始节点
     * 
     * @param namespaceURI
     * @param localName
     * @param qName
     * @param attr
     * @throws SAXException 
     */
    public void startElement(String namespaceURI, String localName, String qName, Attributes attr)
            throws SAXException {

        contents.reset();

        if (attr.getValue(encrypt_ATTR) != null) {
            if (attr.getValue(encrypt_ATTR).equals("true")) {
                this.encrypt = true;
            }
        }
        for(XmlConfigVo configVo: configVos){
            if(configVo.isIsIn() && configVo.isIsAttr()){
               configVo.put(attr.getValue(id_ATTR), attr.getValue(value_ATTR));
               return;
            }
        }
        for(XmlConfigVo configVo: configVos){
            if(localName.equals(configVo.getTagName())){
                configVo.setIsIn(true);
                break;
            }
        }
    }

    /**
     * 结束节点
     * 
     * @param namespaceURI
     * @param localName
     * @param qName
     * @throws SAXException 
     */
    public void endElement(String namespaceURI, String localName, String qName) throws SAXException {
        String value;

        value = contents.toString().trim();

        for(XmlConfigVo configVo: configVos){
            if(configVo.isIsIn()){
                if(localName.equals(configVo.getTagName())){
                    configVo.setIsIn(false);
                }else{
                    if(!configVo.isIsAttr()){
                        if (encrypt) {
                            configVo.put(localName, getPwdValue(value));
                            encrypt = false;
                        } else {
                            configVo.put(localName, value);
                        }
                    }
                }
                break;
            }
        }
    }

    /**
     *内容添加
     * 
     * @param ch
     * @param start
     * @param length
     * @throws SAXException 
     */
    public void characters(char[] ch, int start, int length) throws SAXException {
        contents.write(ch, start, length);
    }
        
    /**
     * 分析文件
     * 
     * @param aFileName
     * @param configVos
     * @return
     * @throws SAXException
     * @throws FileNotFoundException
     * @throws IOException 
     */
    public Hashtable parseConfigFile(String aFileName, Vector<XmlConfigVo> configVos) 
            throws SAXException, FileNotFoundException, IOException {
        
        this.configVos = configVos;
        
        XMLReader xr;

        xr = XMLReaderFactory.createXMLReader("org.apache.xerces.parsers.SAXParser");
        xr.setContentHandler(this);
        xr.parse(new InputSource(new InputStreamReader(new FileInputStream(aFileName),"utf8")));

        // Save the encrypted pasword to the config file
        if (needWriteFile) {
            encryptPwdInFile(aFileName);
        }
        
        Hashtable<String, Hashtable> configs = new Hashtable<String, Hashtable>();
        for(XmlConfigVo configVo: configVos){
            Hashtable config = new Hashtable();
            config.putAll(configVo);
            configs.put(configVo.getTagName(), config);
        }

        return configs;
    }

    /**
     * 加密
     * 
     * @param aFileName
     * @throws FileNotFoundException
     * @throws IOException 
     */
    protected void encryptPwdInFile(String aFileName) throws FileNotFoundException, IOException {
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

    /**
     * 取密码值
     * 
     * @param pwd
     * @return 
     */
    protected String getPwdValue(String pwd) {
        String result = "";
        Encryption encryption = new Encryption();

        if (pwd.startsWith(NOT_ENCRYPT_PREFIX)) {
            result = pwd.substring(NOT_ENCRYPT_PREFIX.length());
            needWriteFile = true;
        } else {
            result = encryption.biDecrypt(ENCRYPT_KEY, pwd);
        }

        return result;
    }
}
