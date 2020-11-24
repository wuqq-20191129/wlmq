/*
 * 文件名：GenerateODService
 * 版权：Copyright: goldsign (c) 2013
 */

package com.goldsign.rule.bo;

import com.goldsign.frame.util.FrameDBUtil;
import com.goldsign.frame.vo.PubFlagVo;
import com.goldsign.rule.dao.GenerateODDao;
import com.goldsign.rule.dao.ParamsStationDao;
import com.goldsign.rule.env.RuleConstant;
import com.goldsign.rule.vo.DistanceChangeVo;
import com.goldsign.rule.vo.DistanceODVo;
import com.goldsign.rule.vo.OperResult;
import com.goldsign.rule.vo.ParamsStationVo;
import com.goldsign.rule.vo.TransferStationVo;
import java.util.Vector;


/*
 * 生成所有OD路径
 * @author     lindaquan
 * @version    V1.0
 * @createTime 2013-12-7
 */

public class GenerateODService {
    
    private GenerateODDao generateODDao = new GenerateODDao();
    private ParamsStationDao paramsStationDao = new ParamsStationDao();
    private String defaultDistance = "0";//默认乘距
    private String defaultChangeTimes  = "0";//默认换乘次数
    private Vector cods = new Vector();//已经计算OD总变量
    private Vector changes = new Vector();//换乘记录总变量
    private Vector transfers = new Vector();//查出所有换乘站信息
    private Vector stationvs = new Vector();//所有站点参数
    private int maxId = 1;//数据库表最大ID;
    private Vector codsO = new Vector();//单个车站循环已经计算OD
    private Vector nods = new Vector();//未计算OD
    private Vector changesO = new Vector();//单个车站循环换乘记录
    private Vector nodsTmp = new Vector();//未计算OD临时变量
    private String startStation = null;//开始站点（线路code + 站点code）临时变量
    private boolean changesOne = true;//首次换乘标志
    
    
    /**
     * 初始化
     * @throws Exception 
     */
    private void init() throws Exception{
//        FrameDBUtil util = new FrameDBUtil();
//        RuleConstant.STATIONS = util.getParamTableFlags("op_prm_station", "line_id", "station_id", "chinese_name");
        
        //查询OD表最大ID
        maxId = generateODDao.getDistanceODMaxID();
        
        //1.查出所有换乘站信息
        transfers = FrameDBUtil.getTransferStation();

        //2.所有站点参数
        ParamsStationVo stationVo = new ParamsStationVo();
        stationVo.setRecordFlag(RuleConstant.RECORD_FLAG_USE);//当前
        stationvs = paramsStationDao.select(stationVo);
    }
    
    /**
     * 插入OD到数据库
     * @param cods 
     */
    public OperResult insertOD() throws Exception{
        return generateODDao.insertOD(cods, changes);
    }
    
    /**
     * 生成所有OD路径
     * @return
     * @throws Exception 
     */
    public OperResult generateOD() throws Exception{
        
        OperResult operResult = null; 
        //初始化变量
        init();

        //4.循环全部车站 找出全部路径
        for(int i = 0; i < RuleConstant.STATIONS.size(); i++){
            PubFlagVo pv = (PubFlagVo) RuleConstant.STATIONS.get(i);
            changesOne = true;//每次站点循环，初始化首次换乘标志
            
            //4.1 当前站到当前站
            addFirstOD(pv);

            //4.2 查找 当前站下一站点，包括换乘
            findFirstNextStation();

            //4.3 清理OD变量值
            nods.addAll(nodsTmp);
            nodsTmp.clear();
            
            //4.4 开始循环未计算OD (主流程)
            while(nods.size() > 0){
                DistanceODVo dVo = (DistanceODVo) nods.get(0);
                String maxIdStr = String.valueOf(cods.size() + codsO.size() + maxId);//最大ID+
                //更新临时开始站点信息
                startStation = dVo.getoLineId() + dVo.getoStationId();

                //4.4.1 设置ID
                dVo.setId(maxIdStr);
                //是否有换乘
                boolean isCopy = false;
                // 插入换乘明细 （复制）
                isCopy = copyChanges(dVo);

                //4.4.2 加入已经计算OD
                codsO.add(dVo);
//                System.out.println(dVo);

                //换乘时，路径完结，插入(换乘站-结束站)记录到换乘变量中
                if(isCopy){
                    addChanges(dVo);
                }

                //4.4.3 同一线路时 ： 循环站点参数，计算下一站, //遇到开始站点时退出循环
                addNextToNodsTmp(dVo);

                //4.4.4 不同线路时： 检测是否为换乘站，并返回换乘站信息
                addCodsForTransfer(dVo);

                //4.4.5 清理OD变量值
                nods.remove(0);
                nods.addAll(nodsTmp);
                nodsTmp.clear();
            }

            //将单个车站循环信息移动到总变量
            cods.addAll(codsO);
            codsO.clear();
            changes.addAll(changesO);
            changesO.clear();
        }

        operResult = insertOD();
        
        return operResult;
    }
    
    /**
     * 添加第一个站为未计算OD (当前站-当前站)
     * @param pv
     */
    private void addFirstOD(PubFlagVo pv) {
        DistanceODVo firstDVo = new DistanceODVo();
        startStation = pv.getStrType() + pv.getCode();
        firstDVo.setoLineId(pv.getStrType());
        firstDVo.setoStationId(pv.getCode());
        firstDVo.seteLineId(pv.getStrType());
        firstDVo.seteStationId(pv.getCode());
        firstDVo.setPassStations(startStation);
        firstDVo.setDistance(defaultDistance);//当前站到当前站 乘距设为0
        firstDVo.setChangeTimes(defaultChangeTimes);//当前站到当前站 换乘次数设为0
        nods.add(firstDVo);
    }

    /**
     * 判断不经过上一路径（dVo）经过的站  的才插入nods(未计算OD)  --2014.01.13
     * @param psvoTmp 循环站点参数
     * @param dvoTmp 当前站点
     * @return 
     */
    private boolean isRepeat(ParamsStationVo psvoTmp, DistanceODVo dvoTmp) {
        boolean isRepeat = false; 
        String passStation = dvoTmp.getPassStations();
        int n = 4;
        
        String psvoTmpStr = psvoTmp.getLine() + psvoTmp.getNextStation();
        for(int j=0; j<passStation.length()/n; j++){//下一站为经过站点时，标志为重复路径
            if(psvoTmpStr.equals(passStation.substring(j*n, j*n+n))){
                isRepeat = true;
            }
        }
        
        if(!isRepeat){
            for(int k=0; k<transfers.size(); k++){
                TransferStationVo tsVo = (TransferStationVo) transfers.get(k);
                if(tsVo.getLineId().equals(psvoTmp.getLine()) && tsVo.getStationId().equals(psvoTmp.getNextStation())){//循环站点参数的换乘站
                    String transferStr = tsVo.getTransferLineId() + tsVo.getTransferStationId();
                    for(int j=0; j<passStation.length()/n; j++){//下一站为经过站点时，标志为重复路径
                        if(transferStr.equals(passStation.substring(j*n, j*n+n))){
                            isRepeat = true;
                            break;
                        }
                    }
                }
            }
        }
        
        return isRepeat;
    }

    /**
     * 复制换乘站
     * @param dVo
     * @param maxIdStr 
     */
    private boolean copyChanges(DistanceODVo dVo) {
        boolean isCopy = false;
        for(int y=0; y<changesO.size(); y++){
            DistanceChangeVo dcvo = (DistanceChangeVo) changesO.get(y);
            if(dVo.getPreId().equals(dcvo.getId()) && !dcvo.getPassLineIn().equals(dcvo.getPassLineOut())//仅复制进出线路不相同的记录,结束站点不复制
                    ){
                DistanceChangeVo dcvoTmp = new DistanceChangeVo();
                dcvoTmp.setPassDistance(dcvo.getPassDistance());
                dcvoTmp.setPassLineIn(dcvo.getPassLineIn());
                dcvoTmp.setPassLineOut(dcvo.getPassLineOut());
                dcvoTmp.setnChangeStationId(dcvo.getnChangeStationId());
                dcvoTmp.setpChangeStationId(dcvo.getpChangeStationId());
                dcvoTmp.setId(dVo.getId());
                changesO.add(dcvoTmp);
                isCopy = true;
                
                //更新临时开始站点信息
                startStation = dcvo.getPassLineIn() + dcvo.getnChangeStationId();
            }
        }
        
        return isCopy;
    }

   /**
    * 添加下一站点到临时变量nodsTmp
    * @param dvoTmp 当前车站
    */
    private void addNextToNodsTmp(DistanceODVo dvoTmp) {
        boolean isRepeat = false; //是否重复
        for(int j = 0; j < stationvs.size(); j++){
            ParamsStationVo psvoTmp = (ParamsStationVo) stationvs.get(j);
            //
            if(psvoTmp.getLine().equals(dvoTmp.geteLineId()) 
                && psvoTmp.getPresentStation().equals(dvoTmp.geteStationId())
                && !(psvoTmp.getNextStation().equals(dvoTmp.getoStationId()) && psvoTmp.getLine().equals(dvoTmp.getoLineId()))//不等于开始站点，即不经过开始站点
                ){
                //4.4.4.4.1 判断不经过上一路径（dVo）经过的换乘站  的才插入nods(未计算OD)  --2014.01.13
                isRepeat = isRepeat(psvoTmp,dvoTmp);
                if(!isRepeat){
                    addNodsTmp(psvoTmp,dvoTmp);
                }
            }
        }
    }

    
    /**
     * 添加换乘站明细
     * @param dvoTmp
     */
    private void addChanges(DistanceODVo dvoTmp) {
        DistanceChangeVo dcvoTmp = new DistanceChangeVo();
        dcvoTmp.setId(dvoTmp.getId());
        dcvoTmp.setPassDistance(getChangeDistance(dvoTmp));
        dcvoTmp.setPassLineIn(dvoTmp.geteLineId());//转入路线
        dcvoTmp.setnChangeStationId(dvoTmp.geteStationId());//转入站点
        if(changesOne){//第一次换乘
            dcvoTmp.setPassLineOut(dvoTmp.getoLineId());//转出线路
            dcvoTmp.setpChangeStationId(dvoTmp.getoStationId());//转出站点
            changesOne = false;
        }else{
            dcvoTmp.setPassLineOut(startStation.substring(0, 2));
            dcvoTmp.setpChangeStationId(startStation.substring(2, 4));
        }
        //更新开始站点信息
//        startStation = dvoTmp.geteLineId() + dvoTmp.geteStationId();
        
        changesO.add(dcvoTmp);
    }
    
    
    /**
     * 取换乘乘距
     * @param dvoTmp
     * @return 
     */
    private String getChangeDistance(DistanceODVo dvoTmp) {
        double changeDistance = Double.valueOf(dvoTmp.getDistance());
        for(int i=0; i<changesO.size(); i++){
            DistanceChangeVo dcvoTmp = (DistanceChangeVo) changesO.get(i);
            if(dcvoTmp.getId().equals(dvoTmp.getId())){
                changeDistance -= Double.valueOf(dcvoTmp.getPassDistance());
            }
        }
        
        return String.valueOf(changeDistance);
    }
      

    /**
     * 添加OD到临时变量nodsTmp
     * @param psvoTmp 循环站点参数
     * @param dvoTmp 当前车站
     */
    private void addNodsTmp(ParamsStationVo psvoTmp, DistanceODVo dvoTmp) {
        DistanceODVo dvoTmpT = new DistanceODVo();
        dvoTmpT.setoLineId(dvoTmp.getoLineId());
        dvoTmpT.setoStationId(dvoTmp.getoStationId());
        dvoTmpT.seteLineId(psvoTmp.getLine());
        dvoTmpT.seteStationId(psvoTmp.getNextStation());
        dvoTmpT.setDistance(String.valueOf(Double.valueOf(psvoTmp.getMileage()) + Double.valueOf(dvoTmp.getDistance())));
        dvoTmpT.setPreId(dvoTmp.getId());
        dvoTmpT.setChangeTimes(dvoTmp.getChangeTimes());
        dvoTmpT.setPassStations(dvoTmp.getPassStations() + psvoTmp.getLine() + psvoTmp.getNextStation());
        nodsTmp.add(dvoTmpT);
    }


    /**
     * 循环换乘站
     * @param dVo
     */
    private void addCodsForTransfer(DistanceODVo dVo) {
        for(int k=0; k<transfers.size(); k++){
            TransferStationVo tsVo = (TransferStationVo) transfers.get(k);
            if(tsVo.getLineId().equals(dVo.geteLineId()) && tsVo.getStationId().equals(dVo.geteStationId())//换乘站等于结束站
                && !(tsVo.getTransferLineId().equals(dVo.getoLineId()) && tsVo.getTransferStationId().equals(dVo.getoStationId()))//换乘后车站不等于开始站
                    ){
                DistanceODVo dvoTmp = new DistanceODVo();
                dvoTmp.setoLineId(dVo.getoLineId());
                dvoTmp.setoStationId(dVo.getoStationId());
                dvoTmp.seteLineId(tsVo.getTransferLineId());
                dvoTmp.seteStationId(tsVo.getTransferStationId());
                dvoTmp.setDistance(dVo.getDistance());
                dvoTmp.setChangeTimes(String.valueOf(Integer.valueOf(dVo.getChangeTimes()) + 1));//换乘次数+1
                dvoTmp.setPassStations(dVo.getPassStations() + tsVo.getTransferLineId() + tsVo.getTransferStationId());//经过站点
                dvoTmp.setPreId(dVo.getId());
                //4.4.4.1 设置ID
                dvoTmp.setId(String.valueOf(cods.size() + codsO.size() + maxId));
                //4.4.4.2 加入已经计算OD
                codsO.add(dvoTmp);
                //复制换乘站
                copyChanges(dvoTmp);
                //4.4.4.3 插入换乘明细
                addChanges(dvoTmp);

                //4.4.4.4  循环站点参数，计算下一站 //遇到开始站点时退出循环
                addNextToNodsTmp(dvoTmp);
            }
        }
    }
    
    /**
     * 第一次循环换乘站
     * @param dVo
     */
    private void addCodsTransferFirst(DistanceODVo dVo) {
        for(int k=0; k<transfers.size(); k++){
            TransferStationVo tsVo = (TransferStationVo) transfers.get(k);
            if(tsVo.getLineId().equals(dVo.geteLineId()) && tsVo.getStationId().equals(dVo.geteStationId())//换乘站等于结束站
                    ){
                DistanceODVo dvoTmp = new DistanceODVo();
                dvoTmp.setoLineId(dVo.getoLineId());
                dvoTmp.setoStationId(dVo.getoStationId());
                dvoTmp.seteLineId(tsVo.getTransferLineId());
                dvoTmp.seteStationId(tsVo.getTransferStationId());
                dvoTmp.setDistance(dVo.getDistance());
                dvoTmp.setChangeTimes(String.valueOf(Integer.valueOf(dVo.getChangeTimes()) + 1));//换乘次数+1
                dvoTmp.setPassStations(dVo.getPassStations() + tsVo.getTransferLineId() + tsVo.getTransferStationId());//经过站点
                dvoTmp.setPreId(dVo.getId());
                //4.4.4.1 设置ID
                dvoTmp.setId(String.valueOf(cods.size() + codsO.size() + maxId));
                //4.4.4.2 加入已经计算OD
                codsO.add(dvoTmp);
                //4.4.4.3 插入换乘明细
                addChanges(dvoTmp);

                //4.4.4.4  循环站点参数，计算下一站 //遇到开始站点时退出循环
                addNextToNodsTmp(dvoTmp);
            }
        }
    }

    /**
     * 第一次查找下一车站
     */
    private void findFirstNextStation() {
        while(nods.size() > 0){
            DistanceODVo dVo = (DistanceODVo) nods.get(0);
            //设置ID
            dVo.setId(String.valueOf(cods.size() + codsO.size() + maxId));
            //加入已经计算OD
            codsO.add(dVo);

            //4.2.1 获得开始循环站点参数，即下一站（同一线路时）
            for(int j = 0; j < stationvs.size(); j++){
                ParamsStationVo psvoTmp = (ParamsStationVo) stationvs.get(j);
                if(psvoTmp.getLine().equals(dVo.geteLineId()) && psvoTmp.getPresentStation().equals(dVo.geteStationId())){
                    DistanceODVo dvoTmp = new DistanceODVo();
                    dvoTmp.setoLineId(dVo.getoLineId());
                    dvoTmp.setoStationId(dVo.getoStationId());
                    dvoTmp.seteLineId(psvoTmp.getLine());
                    dvoTmp.seteStationId(psvoTmp.getNextStation());
                    dvoTmp.setDistance(psvoTmp.getMileage());
                    dvoTmp.setChangeTimes(dVo.getChangeTimes());
                    dvoTmp.setId(dVo.getId());
                    dvoTmp.setPreId(dVo.getId());
                    dvoTmp.setPassStations(dVo.getPassStations() + psvoTmp.getLine() + psvoTmp.getNextStation());
                    nodsTmp.add(dvoTmp);
                }
            }

            //为换乘站时
            addCodsTransferFirst(dVo);

            nods.remove(0);
        }
    }

}
