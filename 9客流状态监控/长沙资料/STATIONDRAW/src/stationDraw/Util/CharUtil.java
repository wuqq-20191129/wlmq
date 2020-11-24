/**
 * <p>Copyright: Copyright GuangZhou goldsign Co. 2005</p>
 * <p>Company: GuangZhou goldsign Co</p>
 */

package stationDraw.Util;

import java.io.UnsupportedEncodingException;
import javax.swing.UIManager;

/**
 * <p>Title: CharUtil</p>
 * <p>Description: The class is design for converting the chinese word code
 * and setUIfont() is for seting application's font </p>
 * @author zhanxiaoxin
 */

public class CharUtil {

  /**
   * 把中文存入sybase数据库中，需要调用此函数来转化。
   * @param str String
   * @return String
   */
  public static String GbkToIso(String str) {
    if (str == null) {
      return str;
    }
    try {
      return new String(str.getBytes("GBK"), "8859_1");
    }
    catch (UnsupportedEncodingException e) {
      return str;
    }
  }

  public static String ChineseToIso(String str) {
    return GbkToIso(IsoToUTF8(str));
  }

  /**
   * 从数据库中读出中文时，需要调用此函数来转化。
   * @param str String
   * @return String
   */
  public static String IsoToGbk(String str) {
    if (str == null) {
      return str;
    }
    try {
      return new String(str.getBytes("8859_1"), "GBK");
    }
    catch (UnsupportedEncodingException e) {
      return str;
    }
  }

  public static String UTF8ToIso(String str) {
    if (str == null) {
      return str;
    }
    try {
      return new String(str.getBytes("UTF-8"), "8859_1");
    }
    catch (UnsupportedEncodingException e) {
      return str;
    }
  }

  public static String IsoToUTF8(String str) {
    if (str == null) {
      return str;
    }
    try {
      return new String(str.getBytes("8859_1"), "UTF-8");
    }
    catch (UnsupportedEncodingException e) {
      return str;
    }
  }

  /**
   * 该方法用于设置整个界面的字体，参数f为整个界面设置的字体
   * @param f FontUIResource
   */
  public static void setUIFont(javax.swing.plaf.FontUIResource f) {
    java.util.Enumeration keys = UIManager.getDefaults().keys();
    while (keys.hasMoreElements()) {
      Object key = keys.nextElement();
      Object value = UIManager.get(key);
      if (value instanceof javax.swing.plaf.FontUIResource) {
        UIManager.put(key, f);
      }
    }
  }

}
