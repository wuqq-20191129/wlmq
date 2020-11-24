package com.goldsign.kmsfront.struct.connection;

import com.goldsign.commu.commu.connection.ConnectionReader;
import com.goldsign.commu.commu.exception.CommuException;
import com.goldsign.commu.commu.util.CharUtil;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

public class AppConnectionReader extends ConnectionReader{

    private static Logger logger = Logger.getLogger(AppConnectionReader.class.getName());
    
    public AppConnectionReader(BufferedInputStream bin) {
        super(bin);
    }

    @Override
    protected Object[] getRealData(Object obj) {
        
        List<Object> retData = new ArrayList<Object>();
        byte[] dataBs = (byte[])obj;
        retData.add((char)dataBs[0]+"");
        retData.add(CharUtil.getByteArr(dataBs, 1, dataBs.length-1));
        
        return retData.toArray();
    }

    @Override
    protected Object readFrameData() throws IOException, CommuException {
        
        char rptType = (char) in.read();//报文类型
        logger.info("报文类型："+rptType);
        if('1' != rptType){
            throw new CommuException("1002:报文类型"+rptType+"不正确.");
        }
        byte[] rptLenBs = new byte[2];
        rptLenBs[0] = (byte) in.read();
        rptLenBs[1] = (byte) in.read();
        int rptLen = CharUtil.byteToInt(rptLenBs);//报文长度
        logger.info("报文长度："+rptLen);
        
        if(rptLen <= 0){
            throw new CommuException("1002:报文长度"+rptLen+"不正确.");
        }
        byte[] dataBs = new byte[rptLen+1];
        dataBs[0] = (byte)rptType;
        for(int i=0; i<rptLen; i++){
            dataBs[i+1] = (byte) in.read();
        }
        
        return dataBs;
    }

}
