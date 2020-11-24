CREATE OR REPLACE PROCEDURE UP_FM_FLOW_NET(V_LINE_STATION          IN VARCHAR2, --线路，车站
                                           V_TRAFFIC_TIME          IN VARCHAR2, --查询时间
                                           INFO_CUR                OUT SYS_REFCURSOR) --返回结果集

---------------------------------------------------------------------------------
--过程名:  UP_FM_FLOW_NET
--系统：状态客流监视系统
--功能描述:  线网、线路、车站当前客流数
--输入参数:   line_id线路ID,station_id 车站ID
--返回值：总客流量
--创建者：  lindaquan
--创建日期：20131016
--使用:线网、车站小时客流表格
-------------------------------------------------------------------------------

AS
  TMP_COUNT     INTEGER; --临时计数变量
  TMP_LINE_ID       VARCHAR2(10); --线路
  TMP_STATION_ID    VARCHAR2(10); --车站
  TMP_SHH           VARCHAR2(4); --运营时间（时分）
  TMP_PHH           VARCHAR2(4); --参数时间（时分）
  TMP_TRAFFIC_TIME  VARCHAR2(8); --当前时间
  TMP_TRAFFIC_TIME_ST  DATE;--运营开始时间
  TMP_TRAFFIC_TIME_ED  DATE;--运营结束时间
  P_TRAFFIC_IN         INTEGER; --进站客流
  P_TRAFFIC_OUT        INTEGER; --出站客流

BEGIN

  --初始化
  P_TRAFFIC_IN := 0;
  P_TRAFFIC_OUT := 0;
  
  TMP_COUNT := instr(V_LINE_STATION,'_',1,1);
  --线路
  TMP_LINE_ID := substr(V_LINE_STATION,1,TMP_COUNT-1);
  --车站
  TMP_STATION_ID := substr(V_LINE_STATION,TMP_COUNT+1);
  --运营时间
  SELECT LPAD(TRIM(cfg_value),4,'0') INTO TMP_SHH FROM acc_st.st_cfg_sys_base where cfg_key='sys.squadday' and record_flag='0';
  --参数时间（时分）
  TMP_PHH := substr(V_TRAFFIC_TIME,12,2)||substr(V_TRAFFIC_TIME,15,2);
  --当前时间
  TMP_TRAFFIC_TIME := substr(V_TRAFFIC_TIME,1,4)||substr(V_TRAFFIC_TIME,6,2)||substr(V_TRAFFIC_TIME,9,2);

  IF TMP_PHH >= TMP_SHH THEN--当前时间大于运营时间

     --起始时间=当前日期+运营的开始时间
     TMP_TRAFFIC_TIME_ST := to_date(TMP_TRAFFIC_TIME || TMP_SHH,'yyyyMMddHH24miss');
     --终止时间=下一天日期+运营的开始时间
     TMP_TRAFFIC_TIME_ED := to_date(to_char(to_date(TMP_TRAFFIC_TIME||'0000','yyyyMMddHH24miss') + 1,'yyyyMMdd') || TMP_SHH,'yyyyMMddHH24miss');

  ELSE--当前时间小于运营时间

     --起始时间=上一天日期+运营的开始时间
     TMP_TRAFFIC_TIME_ST := to_date(to_char(to_date(TMP_TRAFFIC_TIME||'0000','yyyyMMddHH24miss') - 1,'yyyyMMdd') || TMP_SHH,'yyyyMMddHH24miss');
     --终止时间=当天日期+运营的开始时间
     TMP_TRAFFIC_TIME_ED := to_date(TMP_TRAFFIC_TIME || TMP_SHH,'yyyyMMddHH24miss');
  END IF;

  --清除缓存表
  DELETE FROM acc_commu.T#FM_TRAFFIC_ON_OUT;

  --选择车站时，统计车站客流
  IF TMP_STATION_ID != '-1' THEN

      BEGIN

          ----取得当前线路出站的所有客流
          SELECT nvl(SUM(TRAFFIC),0) INTO P_TRAFFIC_OUT FROM acc_commu.cm_traffic_afc_min
                               WHERE LINE_ID=TMP_LINE_ID AND STATION_ID=TMP_STATION_ID
                                     AND FLAG='1' AND TRAFFIC>=0
                                     AND to_date(traffic_datetime,'yyyyMMddHH24miss') > TMP_TRAFFIC_TIME_ST
                                     AND to_date(traffic_datetime,'yyyyMMddHH24miss') <= TMP_TRAFFIC_TIME_ED
                                     GROUP BY FLAG;

          EXCEPTION 
          WHEN OTHERS THEN
            DBMS_OUTPUT.PUT_LINE('sqlerrm : ' || SQLERRM);                         

      END;
      
      BEGIN
           ----取得当前线路进站的所有客流
          SELECT nvl(SUM(TRAFFIC),0) INTO P_TRAFFIC_IN FROM acc_commu.cm_traffic_afc_min
                               WHERE LINE_ID=TMP_LINE_ID AND STATION_ID=TMP_STATION_ID
                                     AND FLAG='0' AND TRAFFIC>=0
                                     AND to_date(traffic_datetime,'yyyyMMddHH24miss') > TMP_TRAFFIC_TIME_ST
                                     AND to_date(traffic_datetime,'yyyyMMddHH24miss') <= TMP_TRAFFIC_TIME_ED
                                     GROUP BY FLAG;
          EXCEPTION 
          WHEN OTHERS THEN
            DBMS_OUTPUT.PUT_LINE('sqlerrm : ' || SQLERRM);                         

      END;
      
      OPEN INFO_CUR FOR
      SELECT TMP_LINE_ID line_name,TMP_STATION_ID station_name,P_TRAFFIC_IN traffic_in,P_TRAFFIC_OUT traffic_out FROM dual; 

  --未选择车站时，统计线路客流
  ELSE
      BEGIN

          --所有线路
          IF TMP_LINE_ID = 'all' THEN
             --插入全部车站初始出入站人数0到临时表acc_commu.T#FM_TRAFFIC_ON_OUT
             INSERT INTO acc_commu.T#FM_TRAFFIC_ON_OUT 
             SELECT line_id,line_name,0,0 FROM acc_st.op_prm_line WHERE record_flag = '0';
             
             --更新统计入站总数到临时表
             UPDATE acc_commu.T#FM_TRAFFIC_ON_OUT t SET t.traffic_in = (SELECT a.traffic_in  FROM 
                 (SELECT s1.belong_line_id,SUM(t1.TRAFFIC) traffic_in FROM acc_commu.cm_traffic_afc_min t1,acc_st.op_prm_station s1
                                                    WHERE t1.TRAFFIC>=0 AND t1.flag='0'
                                                    AND to_date(t1.traffic_datetime,'yyyyMMddHH24miss') > TMP_TRAFFIC_TIME_ST
                                                    AND to_date(t1.traffic_datetime,'yyyyMMddHH24miss') <= TMP_TRAFFIC_TIME_ED
                                                    AND s1.station_id = t1.station_id
                                                    AND s1.line_id = t1.line_id
                                                    AND s1.record_flag='0'
                                                    GROUP BY t1.FLAG,s1.belong_line_id) a
              WHERE t.line_id=a.belong_line_id);
             
  
             --更新统计出站总数到临时表
             UPDATE acc_commu.T#FM_TRAFFIC_ON_OUT t SET t.traffic_out = (SELECT a.traffic_out  FROM 
                 (SELECT s1.belong_line_id,SUM(t1.TRAFFIC) traffic_out FROM acc_commu.cm_traffic_afc_min t1,acc_st.op_prm_station s1
                                                    WHERE t1.TRAFFIC>=0 AND t1.flag='1'
                                                    AND to_date(t1.traffic_datetime,'yyyyMMddHH24miss') > TMP_TRAFFIC_TIME_ST
                                                    AND to_date(t1.traffic_datetime,'yyyyMMddHH24miss') <= TMP_TRAFFIC_TIME_ED
                                                    AND s1.station_id = t1.station_id
                                                    AND s1.line_id = t1.line_id
                                                    AND s1.record_flag='0'
                                                    GROUP BY t1.FLAG,s1.belong_line_id) a
              WHERE t.line_id=a.belong_line_id);
             
             
              --将出入站总数放入存储过程
              OPEN INFO_CUR FOR
              SELECT line_id,station_id line_name,traffic_in,traffic_out FROM acc_commu.T#FM_TRAFFIC_ON_OUT;
              
          --某条线路
          ELSE
             --插入全部车站初始出入站人数0到临时表acc_commu.T#FM_TRAFFIC_ON_OUT
             INSERT INTO acc_commu.T#FM_TRAFFIC_ON_OUT 
             SELECT line_id,line_name,0,0 FROM acc_st.op_prm_line WHERE record_flag = '0' AND line_id=TMP_LINE_ID;
             
             --更新统计入站总数到临时表
             UPDATE acc_commu.T#FM_TRAFFIC_ON_OUT t SET t.traffic_in = (SELECT a.traffic_in FROM 
                    (SELECT s1.belong_line_id,SUM(t1.TRAFFIC) traffic_in FROM acc_commu.cm_traffic_afc_min t1,acc_st.op_prm_station s1
                                                WHERE t1.TRAFFIC>=0 AND t1.flag='0'
                                                AND to_date(t1.traffic_datetime,'yyyyMMddHH24miss') > TMP_TRAFFIC_TIME_ST
                                                AND to_date(t1.traffic_datetime,'yyyyMMddHH24miss') <= TMP_TRAFFIC_TIME_ED
                                                AND s1.station_id = t1.station_id
                                                AND s1.belong_line_id = TMP_LINE_ID
                                                AND s1.record_flag='0'
                                                GROUP BY s1.belong_line_id) a
                WHERE t.line_id = a.belong_line_id);                                
              
                                               
             --更新统计出站总数到临时表
             UPDATE acc_commu.T#FM_TRAFFIC_ON_OUT t SET t.traffic_out = (SELECT a.traffic_out FROM 
                    (SELECT s1.belong_line_id,SUM(t1.TRAFFIC) traffic_out FROM acc_commu.cm_traffic_afc_min t1,acc_st.op_prm_station s1
                                                WHERE t1.TRAFFIC>=0 AND t1.flag='1'
                                                AND to_date(t1.traffic_datetime,'yyyyMMddHH24miss') > TMP_TRAFFIC_TIME_ST
                                                AND to_date(t1.traffic_datetime,'yyyyMMddHH24miss') <= TMP_TRAFFIC_TIME_ED
                                                AND s1.station_id = t1.station_id
                                                AND s1.belong_line_id = TMP_LINE_ID
                                                AND s1.record_flag='0'
                                                GROUP BY s1.belong_line_id) a
                WHERE t.line_id = a.belong_line_id);
             

             --将出入站总数放入存储过程
             OPEN INFO_CUR FOR
             SELECT line_id,station_id line_name,traffic_in,traffic_out FROM acc_commu.T#FM_TRAFFIC_ON_OUT;

          END IF;

      END;
  END IF;
  
  --清除缓存表
  DELETE FROM acc_commu.T#FM_TRAFFIC_ON_OUT;
  
  COMMIT;
  
  DBMS_OUTPUT.PUT_LINE('sqlerrm : ' || SQLERRM);

END UP_FM_FLOW_NET;
