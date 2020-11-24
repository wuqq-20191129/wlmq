/*
 * 文件名：ResultVo
 * 版权：Copyright: goldsign (c) 2013
 */

package com.goldsign.ecpmcs.vo;

import com.goldsign.ecpmcs.env.AppConstant;


/*
 * 〈接口调用结果集〉
 * @author     lindaquan
 * @version    V1.0
 * @createTime 2014-4-16
 */

public class ResultVo {
    
    private int resultCode;//1:successful; 0:failed
    
    private int errCode;//返回错误代码
    
    private String errDescription;//错误代码描述
    
    private String content;//返回内容
    
    private int codex;//扩展
    
    private int count;//扩展计数
    
    private String exceptionString;//异常

    public String getExceptionString() {
        return exceptionString;
    }

    public void setExceptionString(String exceptionString) {
        this.exceptionString = exceptionString;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getCodex() {
        return codex;
    }

    public void setCodex(int codex) {
        this.codex = codex;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public int getErrCode() {
        return errCode;
    }

    public void setErrCode(int errCode) {
        this.errCode = errCode;
    }

    public String getErrDescription() {
        return String.valueOf(AppConstant.PRINT_ERROR.get(String.valueOf(this.errCode)));
    }

    public void setErrDescription(String errDescription) {
        this.errDescription = errDescription;
    }
    
}
