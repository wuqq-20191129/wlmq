CREATE OR REPLACE PROCEDURE UP_FM_FLOW_MIN(V_LINE_STATION          IN VARCHAR2, --线路，车站
                                           V_TRAFFIC_TIME          IN VARCHAR2, --查询时间
                                           INFO_CUR                OUT SYS_REFCURSOR) --返回结果集

---------------------------------------------------------------------------------
--过程名:  UP_FM_FLOW_MIN
--系统：状态客流监视系统
--功能描述:  进、出站5分钟客流数
--输入参数:   line_id线路ID,station_id 车站ID
--返回值：总客流量
--创建者：  lindaquan
--创建日期：20131016
-------------------------------------------------------------------------------

AS
  TMP_COUNT     INTEGER; --临时计数变量
  TMP_LINE_ID       VARCHAR2(10); --线路
  TMP_STATION_ID    VARCHAR2(10); --车站
  TMP_TRAFFIC_TIME  VARCHAR2(10); --当前时间
  TMP_TRAFFIC_TIME_ST  DATE;--运营开始时间
  TMP_TRAFFIC_TIME_ED  DATE;--运营结束时间
  P_TRAFFIC_IN         INTEGER; --进站客流
  P_TRAFFIC_OUT        INTEGER; --出站客流

BEGIN

  TMP_COUNT := instr(V_LINE_STATION,'_',1,1);
  --线路
  TMP_LINE_ID := substr(V_LINE_STATION,1,TMP_COUNT-1);
  --车站
  TMP_STATION_ID := substr(V_LINE_STATION,TMP_COUNT+1);
  --当前时间
  TMP_TRAFFIC_TIME := substr(V_TRAFFIC_TIME,1,4)||substr(V_TRAFFIC_TIME,6,2)||substr(V_TRAFFIC_TIME,9,2)||substr(V_TRAFFIC_TIME,12,2);
  --起始时间
  TMP_TRAFFIC_TIME_ST := to_date(TMP_TRAFFIC_TIME || '00','yyyyMMddHH24miss')-1/24;
  --终止时间
  TMP_TRAFFIC_TIME_ED := to_date(TMP_TRAFFIC_TIME || '59','yyyyMMddHH24miss');


  --选择车站时，统计车站客流
  IF TMP_STATION_ID != '-1' THEN

      BEGIN

          ----取得当前线路车站的所有客流
          OPEN INFO_CUR FOR

          SELECT t.line_id,t.station_id,substr(t.traffic_datetime,9,4) hour_min,SUM(t.traffic) traffic,flag memo
                 FROM acc_commu.cm_traffic_afc_min t
                 WHERE t.line_id = TMP_LINE_ID
                     AND t.station_id = TMP_STATION_ID
                     AND to_date(traffic_datetime,'yyyyMMddHH24miss') > TMP_TRAFFIC_TIME_ST
                     AND to_date(traffic_datetime,'yyyyMMddHH24miss') <= TMP_TRAFFIC_TIME_ED
                 GROUP BY substr(t.traffic_datetime,9,4),flag,t.line_id,t.station_id;

      END;

  --未选择车站时，统计线路客流
  ELSE
      BEGIN

          --所有线路(全部线路进出客流)
          IF TMP_LINE_ID = 'all' THEN
              OPEN INFO_CUR FOR

              SELECT s.belong_line_id line_id,substr(t.traffic_datetime,9,4) hour_min,SUM(t.traffic) traffic
                 FROM acc_commu.cm_traffic_afc_min t,acc_st.op_prm_station s
                 WHERE t.line_id = s.line_id
                     AND t.station_id = s.station_id
                     AND to_date(traffic_datetime,'yyyyMMddHH24miss') > TMP_TRAFFIC_TIME_ST
                     AND to_date(traffic_datetime,'yyyyMMddHH24miss') <= TMP_TRAFFIC_TIME_ED
                 GROUP BY s.belong_line_id,substr(t.traffic_datetime,9,4);


          --某条线路进出客流
          ELSE
              OPEN INFO_CUR FOR

              SELECT s.belong_line_id line_id,substr(t.traffic_datetime,9,4) hour_min,SUM(t.traffic) traffic,flag memo
                 FROM acc_commu.cm_traffic_afc_min t,acc_st.op_prm_station s
                 WHERE t.line_id = s.line_id
                     AND t.station_id = s.station_id
                     AND s.belong_line_id = TMP_LINE_ID
                     AND to_date(traffic_datetime,'yyyyMMddHH24miss') > TMP_TRAFFIC_TIME_ST
                     AND to_date(traffic_datetime,'yyyyMMddHH24miss') <= TMP_TRAFFIC_TIME_ED
                 GROUP BY substr(t.traffic_datetime,9,4),flag,s.belong_line_id;

          END IF;

      END;
  END IF;

  DBMS_OUTPUT.PUT_LINE('sqlerrm : ' || SQLERRM);

END UP_FM_FLOW_MIN;
