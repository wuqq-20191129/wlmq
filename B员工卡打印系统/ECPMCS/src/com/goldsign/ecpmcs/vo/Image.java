/*
 * 文件名：Image
 * 版权：Copyright: goldsign (c) 2013
 */

package com.goldsign.ecpmcs.vo;


/*
 * 〈打印图片实体类〉
 * @author     lindaquan
 * @version    V1.0
 * @createTime 2014-6-4
 */

public class Image {
    private String type;
    private String code;
    private String value;
    private int x;
    private int y;
    private int h;
    private int w;
    private String dir;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getH() {
        return h;
    }

    public void setH(int h) {
        this.h = h;
    }

    public int getW() {
        return w;
    }

    public void setW(int w) {
        this.w = w;
    }

    public String getDir() {
        return dir;
    }

    public void setDir(String dir) {
        this.dir = dir;
    }
}
