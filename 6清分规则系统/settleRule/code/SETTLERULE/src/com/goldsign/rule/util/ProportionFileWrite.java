/*
 * 文件名：ProportionFileWrite
 * 版权：Copyright: goldsign (c) 2013
 * 描述：写文件
 */

package com.goldsign.rule.util;

import com.goldsign.rule.vo.DistanceProportionVo;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Vector;


/*
 * 将内容写入文件
 * @author     lindaquan
 * @version    V1.0
 */

public class ProportionFileWrite{
    
    /*
     * 写入文件
     * @param results
     * @param file
     * @return a boolean 
     */
    public static boolean writeVectorModel( Vector results, File file, String params){

        String split = ",";
        
        if( results == null ){
            return false;
        }

        StringBuffer fileBuf = new StringBuffer("");
        
        for( int i = 0; i < results.size(); i++ ){
            DistanceProportionVo vo = new DistanceProportionVo();
            vo = (DistanceProportionVo) results.get(i);
            paramsAppend(vo,split,params, fileBuf);
        }
        try{
            FileOutputStream fis=new FileOutputStream(file);
            OutputStreamWriter osw = new OutputStreamWriter(fis,"gb2312");
            osw.write(fileBuf.toString());
            osw.close();
            
            return true;
        }catch(IOException e){
            e.printStackTrace( System.err );
            return false;
        }
    }

    /**
     * 选择导出字段拼接
     * @param vo
     * @param split
     * @param params
     * @param fileBuf 
     */
    private static void paramsAppend(DistanceProportionVo vo, String split, String params, StringBuffer fileBuf) {
        fileBuf.append(vo.getoLineId());
        fileBuf.append(split);
        fileBuf.append(vo.getoStationId());
        fileBuf.append(split);
        fileBuf.append(vo.getdLineId());
        fileBuf.append(split);
        fileBuf.append(vo.getdStationId());
        fileBuf.append(split);
        fileBuf.append(vo.getDispartLineId());
        fileBuf.append(split);
        fileBuf.append(vo.getInPrecent());
        if(params != null && params.indexOf("c_version") > -1){
            fileBuf.append(split);
            fileBuf.append(vo.getVersion());
        }        
        fileBuf.append( "\r\n" );
    }

}
