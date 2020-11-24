package stationDraw.Util;

import java.util.Iterator;
import java.util.List;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.XMLOutputter;
import stationDraw.Frame.Encryption;

public class ReadWriteXml {
    //设备数据库
    public static String driver="";
    public static String url="";
    public static String userName="";
    public static String password="";
    
    //线路数据库
    public static String userName2="";
    public static String password2="";
    
    //屏幕大小
    public static int screenH = 768;
    public static int screenW = 1024;
    
    protected final String ENCRYPT_KEY = "GOLDSIGN";
    
    public ReadWriteXml() {
    }
    public ReadWriteXml(String readFilePath) {
        this.ReadXml(readFilePath);
     }
    //读取xml
    public void ReadXml(String filePath){
        SAXBuilder builder = new SAXBuilder();
        Document doc = null;
        try {
            doc = builder.build(filePath);
            //取得根元素
            Element rootE = doc.getRootElement();
            //根元素的子元素
            List rootL = rootE.getChildren();

            Iterator rootItr = rootL.iterator();
            //获取EJB
            while (rootItr.hasNext()) {
                Element busiE = (Element) rootItr.next();
                if(busiE.getName().equals("OtherDb")){
                    //数据库驱动
                    this.driver = busiE.getChildTextTrim("Driver");
                    //数据库URL
                    this.url = busiE.getChildTextTrim("URL");
                    //用户名
                    this.userName = getUserIdPwd(busiE.getChildTextTrim("UserName"));
                    //密码
                    this.password = getUserIdPwd(busiE.getChildTextTrim("Password"));

                    //线路数据库
                    //用户名
                    this.userName2 = getUserIdPwd(busiE.getChildTextTrim("UserName2"));
                    //密码
                    this.password2 = getUserIdPwd(busiE.getChildTextTrim("Password2"));
                }
                if(busiE.getName().equals("ScreenSize")){
                    //屏幕大小
                    this.screenH = Integer.valueOf(busiE.getChildTextTrim("height"));
                    this.screenW = Integer.valueOf(busiE.getChildTextTrim("width"));
                }
            }
        } catch (JDOMException ex) {
            ex.printStackTrace();
        }


    }
//写入xml

    //把xml的内容输出到指定的目录

    public void outputxml(String sourceFilePath,String aimFilePath){

        try{
            SAXBuilder builder = new SAXBuilder();
             Document doc = null;
            XMLOutputter xout = new XMLOutputter();
            doc=builder.build(sourceFilePath);//读取源文件内容

            xout.setEncoding("gb2312");

            java.io.FileOutputStream fileout = new java.io.FileOutputStream(aimFilePath);//目标文件

            xout.output(doc,fileout);

        }catch(Exception e){

            e.printStackTrace();

        }
    }
    public static void main(String args[])throws Exception{
        try{

          ReadWriteXml rwx=new ReadWriteXml("config.xml"); //读取文件config.xml中内容
           rwx.outputxml( "config.xml","aaa.xml");//把源文件config.xml内容写到目标文件aaa.xml里面
        }
        catch(Exception e){
            throw e;
        }
        System.out.println("driver="+ReadWriteXml.driver);
        System.out.println("url="+ReadWriteXml.url);
        System.out.println("userName="+ReadWriteXml.userName);
    }
    
    /**
 * 解析加密用户名密码
 * @param aUserIdPwd
 * @return 
 */
protected String getUserIdPwd(String aUserIdPwd) {
        String result = "";
        Encryption encryption = new Encryption();
        
        result = encryption.biDecrypt(ENCRYPT_KEY, aUserIdPwd);

        return result;
    }

}
