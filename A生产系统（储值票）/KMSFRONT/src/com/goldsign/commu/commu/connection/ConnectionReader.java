package com.goldsign.commu.commu.connection;

import com.goldsign.commu.commu.env.CommuConstant;
import com.goldsign.commu.commu.exception.CommuException;
import com.goldsign.commu.commu.exception.MessageException;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.Vector;
import org.apache.log4j.Logger;

public class ConnectionReader {

    private static Logger logger = Logger.getLogger(ConnectionReader.class.getName());
    
    protected BufferedInputStream in;
    private String resultCode = "";

    public ConnectionReader(BufferedInputStream bin) {
        in = bin;
    }

    public Vector read() {
        Vector readerResult = new Vector();
        Object data = null;
        try {
            data = readFrameData();
            if(null != data){
                resultCode = CommuConstant.RESULT_CODE_DTA;
            }else{
                resultCode = CommuConstant.RESULT_CODE_NDT;
            }
        } catch (IOException e) {
            resultCode = "1001";
            logger.error("IO 异常:" + e.getMessage());
        }catch (MessageException e) {
            logger.error("接收的数据处理时有错,错误代码为:" + e.getMessage());
        } finally {
            readerResult.add(resultCode);
            readerResult.add(getRealData(data));
            return readerResult;
        }
    }
    
    protected Object[] getRealData(Object obj){
        
        String data = (String)obj;
        return data.substring(1, data.length()-1).split(CommuConstant.COMM_DATA_SEP);
    }
    
    protected Object readFrameData() throws IOException, CommuException{

        String data = "";
        boolean flag = false;
        char oneByte = (char) in.read();
        while (oneByte != -1) {
            if (CommuConstant.COMM_STX_SEP.equals(oneByte+"")) {
                if(flag){
                    throw new CommuException("1002");
                }
                flag = true;
            }
            if (CommuConstant.COMM_ETX_SEP.equals(oneByte+"")) {
                if (flag) {
                    data = data + oneByte;
                }
                break;
            }
            if (flag) {
                data = data + oneByte;
            }
            oneByte = (char) in.read();
        }
        return data;
    }
}
