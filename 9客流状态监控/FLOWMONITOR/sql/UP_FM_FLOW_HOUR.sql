CREATE OR REPLACE PROCEDURE UP_FM_FLOW_HOUR(V_LINE_STATION          IN VARCHAR2, --线路，车站
                                            V_TRAFFIC_TIME          IN VARCHAR2, --查询时间
                                            INFO_CUR                OUT SYS_REFCURSOR) --返回结果集

---------------------------------------------------------------------------------
--过程名:  UP_FM_FLOW_HOUR
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


  --选择车站时，统计车站客流
  IF TMP_STATION_ID != '-1' THEN

      BEGIN

          ----取得当前线路车站的所有客流
          OPEN INFO_CUR FOR

          SELECT t.line_id,t.station_id,substr(t.traffic_datetime,9,2) hour,SUM(t.traffic) traffic,flag memo
                 FROM acc_commu.cm_traffic_afc_min t
                 WHERE t.line_id = TMP_LINE_ID
                     AND t.station_id = TMP_STATION_ID
                     AND to_date(traffic_datetime,'yyyyMMddHH24miss') > TMP_TRAFFIC_TIME_ST
                     AND to_date(traffic_datetime,'yyyyMMddHH24miss') <= TMP_TRAFFIC_TIME_ED
                 GROUP BY substr(t.traffic_datetime,9,2),t.line_id,t.station_id,flag;

      END;

  --未选择车站时，统计线路客流
  ELSE
      BEGIN

          --所有线路(全部线路进出客流)
          IF TMP_LINE_ID = 'all' THEN
              OPEN INFO_CUR FOR

              SELECT substr(t.traffic_datetime,9,2) hour,SUM(t.traffic) traffic,flag memo
                     FROM acc_commu.cm_traffic_afc_min t
                     WHERE to_date(traffic_datetime,'yyyyMMddHH24miss') > TMP_TRAFFIC_TIME_ST
                         AND to_date(traffic_datetime,'yyyyMMddHH24miss') <= TMP_TRAFFIC_TIME_ED
                     GROUP BY substr(t.traffic_datetime,9,2),flag;


          --某条线路进出客流
          ELSE
              OPEN INFO_CUR FOR

              SELECT t.line_id,substr(t.traffic_datetime,9,2) hour,SUM(t.traffic) traffic,flag memo
                     FROM acc_commu.cm_traffic_afc_min t
                     WHERE t.line_id = TMP_LINE_ID
                         AND to_date(traffic_datetime,'yyyyMMddHH24miss') > TMP_TRAFFIC_TIME_ST
                         AND to_date(traffic_datetime,'yyyyMMddHH24miss') <= TMP_TRAFFIC_TIME_ED
                     GROUP BY substr(t.traffic_datetime,9,2),t.line_id,flag;

          END IF;

      END;
  END IF;

  DBMS_OUTPUT.PUT_LINE('sqlerrm : ' || SQLERRM);

END UP_FM_FLOW_HOUR;
