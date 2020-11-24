package com.goldsign.escommu.util;

import java.math.BigDecimal;
import java.math.BigInteger;

public class NumberUtil {

    public NumberUtil() {
        super();
        // TODO Auto-generated constructor stub
    }

    public static String formatNumber(int number, int len) {
        if (number < 0) {
            return "";
        }
        String sn = new Integer(number).toString();
        String tmp = "";
        for (int i = 0; i < len - sn.length(); i++) {
            tmp += "0";
        }
        return tmp + sn;
    }

    public static String formatNumber(BigInteger number, int len) {

        String sn = number.toString();
        String tmp = "";
        for (int i = 0; i < len - sn.length(); i++) {
            tmp += "0";
        }
        return tmp + sn;
    }

    public static String formatNumber(String number, int len) {

        String sn = number;
        String tmp = "";
        for (int i = 0; i < len - sn.length(); i++) {
            tmp += "0";
        }
        return tmp + sn;
    }

    public static Integer getIntegerValue(String value, int defaultValue) {
        if (value == null || value.length() == 0) {
            return new Integer(defaultValue);
        }
        return new Integer(value);
    }

    public static int getIntValue(String value, int defaultValue) {
        if (value == null || value.length() == 0) {
            return new Integer(defaultValue).intValue();
        }
        return new Integer(value).intValue();
    }

    public static String getIntegerStrValue(String value, int defaultValue) {
        if (value == null || value.length() == 0) {
            return new Integer(defaultValue).toString();
        }
        return value;
    }

    public static String getDateTimeValue(String value, String defaultValue) {
        if (value == null || value.length() == 0) {
            return defaultValue;
        }
        return value;
    }

    public static Object getDateTimeValue(String value, Object defaultValue) {
        if (value == null || value.length() == 0) {
            return defaultValue;
        }
        return value;
    }

    public static String getDateTimeBeginValue(String value) {
        if (value == null || value.length() == 0) {
            return "";
        }
        return value + " 00:00:00";
    }

    public static String getDateTimeEndValue(String value) {
        if (value == null || value.length() == 0) {
            return "";
        }
        return value + " 23:59:59";
    }

    public static String getDateValue(String value) {
        if (value == null || value.length() == 0) {
            return "";
        }
        return value.substring(0, 10);
    }

    public static String getStringValue(String value) {
        if (value == null || value.length() == 0) {
            return "";
        }
        return value;
    }

    public static String getStringValue(String value, String defaultValue) {
        if (value == null || value.length() == 0) {
            return defaultValue;
        }
        return value;
    }

    public static BigDecimal getBigDecimalValue(String value, String defaultValue) {
        if (value == null || value.length() == 0) {
            return new BigDecimal(defaultValue);
        }
        return new BigDecimal(value);
    }

    public static BigDecimal getBigDecimalValue(String value, String defaultValue, int scale) {
        if (value == null || value.length() == 0) {
            value = defaultValue;
        }
        BigDecimal bd = new BigDecimal(value);
        bd.setScale(scale);
        return bd;
    }

    public static String add(String n, int addNum) {
        return new Integer(new Integer(n).intValue() + addNum).toString();
    }

    public static String add(String n, String n1) {
        if (n == null || n.length() == 0) {
            n = "0";
        }
        if (n1 == null || n1.length() == 0) {
            n1 = "0";
        }
        int total = Integer.parseInt(n) + Integer.parseInt(n1);
        return new Integer(total).toString();

    }

}
