CREATE OR REPLACE PROCEDURE UP_FM_FLOW_STATION(V_LINE_ID          IN VARCHAR2, --线路
                                               V_TRAFFIC_TIME     IN VARCHAR2, --查询时间
                                               P_CUR              OUT SYS_REFCURSOR) --客流结果集

---------------------------------------------------------------------------------
--过程名:  UP_FM_FLOW_STATION
--系统：状态客流监视系统
--功能描述:  线路当天客流量
--输入参数:   line_id线路ID
--返回值：出站总客流量、进站总客流量
--创建者：  lindaquan
--创建日期：20131016
--使用:线路小时客流表格
-------------------------------------------------------------------------------

AS
  TMP_LINE_ID       VARCHAR2(10); --线路
  TMP_SHH           VARCHAR2(4); --运营时间（时分）
  TMP_PHH           VARCHAR2(4); --参数时间（时分）
  TMP_TRAFFIC_TIME  VARCHAR2(8); --当前时间
  TMP_TRAFFIC_TIME_ST  DATE;--运营开始时间
  TMP_TRAFFIC_TIME_ED  DATE;--运营结束时间

BEGIN
  --清除缓存表
  DELETE FROM acc_commu.T#FM_TRAFFIC_ON_OUT;

  --线路
  TMP_LINE_ID := V_LINE_ID;
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

  --插入全部车站初始出入站人数0到临时表acc_commu.T#FM_TRAFFIC_ON_OUT
  INSERT INTO acc_commu.T#FM_TRAFFIC_ON_OUT
  SELECT belong_line_id line_id,station_id,0,0 FROM acc_st.op_prm_station WHERE record_flag = '0';

  --更新统计入站总数到临时表
  UPDATE acc_commu.T#FM_TRAFFIC_ON_OUT t SET t.traffic_in =
  (SELECT t1.traffic_in FROM
        (SELECT b.belong_line_id line_id,a.station_id,nvl(sum(a.traffic),0) traffic_in
              FROM acc_commu.cm_traffic_afc_min a,acc_st.op_prm_station b
              WHERE a.TRAFFIC>=0 AND a.flag='0'
                AND to_date(a.traffic_datetime,'yyyyMMddHH24miss') > TMP_TRAFFIC_TIME_ST
                AND to_date(a.traffic_datetime,'yyyyMMddHH24miss') <= TMP_TRAFFIC_TIME_ED
                AND b.record_flag='0'
                AND b.belong_line_id = TMP_LINE_ID
                AND a.station_id = b.station_id
                AND a.line_id = b.belong_line_id
                GROUP BY b.belong_line_id,a.station_id) t1
  WHERE t.line_id=t1.line_id AND trim(t.station_id) = t1.station_id);


  --更新统计出站总数到临时表
  UPDATE acc_commu.T#FM_TRAFFIC_ON_OUT t SET t.traffic_out =
  (SELECT t1.traffic_out FROM
        (SELECT b.belong_line_id line_id,a.station_id,nvl(sum(a.traffic),0) traffic_out
              FROM acc_commu.cm_traffic_afc_min a,acc_st.op_prm_station b
              WHERE a.TRAFFIC>=0 AND a.flag='1'
                AND to_date(a.traffic_datetime,'yyyyMMddHH24miss') > TMP_TRAFFIC_TIME_ST
                AND to_date(a.traffic_datetime,'yyyyMMddHH24miss') <= TMP_TRAFFIC_TIME_ED
                AND b.record_flag='0'
                AND b.belong_line_id = TMP_LINE_ID
                AND a.station_id = b.station_id
                AND a.line_id = b.belong_line_id
                GROUP BY b.belong_line_id,a.station_id) t1
  WHERE t.line_id=t1.line_id AND trim(t.station_id) = t1.station_id);


  --将出入站总数放入存储过程
  OPEN P_CUR FOR
  SELECT line_id,station_id,traffic_in,traffic_out FROM acc_commu.T#FM_TRAFFIC_ON_OUT;

  --清除缓存表
  DELETE FROM acc_commu.T#FM_TRAFFIC_ON_OUT;

  COMMIT;

  DBMS_OUTPUT.PUT_LINE('sqlerrm : ' || SQLERRM);

END UP_FM_FLOW_STATION;
