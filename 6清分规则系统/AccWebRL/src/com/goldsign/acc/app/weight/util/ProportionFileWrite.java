/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.weight.util;

import com.goldsign.acc.app.weight.entity.DistanceProportion;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;
import java.util.Vector;

/*
 * 将内容写入文件
 * @author     liudz
 * @version    V1.0
 */

public class ProportionFileWrite{
    
    /*
     * 写入文件
     * @param results
     * @param file
     * @return a boolean 
     */
    public static boolean writeVectorModel( List<DistanceProportion> resultSet, File file, String params){

        String split = ",";
        
        if( resultSet == null ){
            return false;
        }

        StringBuffer fileBuf = new StringBuffer("");
        
        for( int i = 0; i < resultSet.size(); i++ ){
            DistanceProportion vo = new DistanceProportion();
            vo = (DistanceProportion) resultSet.get(i);
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
    private static void paramsAppend(DistanceProportion vo, String split, String params, StringBuffer fileBuf) {
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
