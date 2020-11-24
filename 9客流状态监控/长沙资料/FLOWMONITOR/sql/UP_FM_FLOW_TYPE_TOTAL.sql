CREATE OR REPLACE PROCEDURE UP_FM_FLOW_TYPE_TOTAL(V_LINE_STATION          IN VARCHAR2, --线路，车站
                                                 V_TRAFFIC_TIME           IN VARCHAR2, --查询时间
                                                 V_TYPE                   IN VARCHAR2, --主类型/子类型
                                                 INFO_CUR                 OUT SYS_REFCURSOR) --子类型进出站客流

---------------------------------------------------------------------------------
--过程名:  UP_FM_FLOW_TYPE_TOTAL
--系统：状态客流监视系统
--功能描述:  票卡类型的各票卡进站客流数
--输入参数:   line_id线路ID,station_id 车站ID
--返回值：总客流量
--创建者：  lindaquan
--创建日期：20131016
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

          ----取得当前线路进出站的所有客流
          --主类型
          IF V_TYPE = 'MAIN' THEN
            OPEN INFO_CUR FOR
            SELECT nvl(SUM(t.traffic),0) traffic,t.flag,lpad(t.card_main_type,2,'0') card_main_type,s.card_main_name
              FROM acc_commu.cm_traffic_afc_min t,acc_st.op_prm_card_main s
                WHERE t.LINE_ID=TMP_LINE_ID AND t.STATION_ID=TMP_STATION_ID
                   AND to_date(t.traffic_datetime,'yyyyMMddHH24miss') > TMP_TRAFFIC_TIME_ST
                   AND to_date(t.traffic_datetime,'yyyyMMddHH24miss') <= TMP_TRAFFIC_TIME_ED
                   and t.card_main_type = s.card_main_id
                   and s.record_flag = '0'
                   group by t.flag,t.card_main_type,s.card_main_name;
          --子类型
          ELSE
                
            OPEN INFO_CUR FOR
            SELECT nvl(SUM(t.traffic),0) traffic,t.flag,lpad(t.card_main_type,2,'0') card_main_type, lpad(t.card_sub_type,2,'0') card_sub_type,s.card_sub_name 
              FROM acc_commu.cm_traffic_afc_min t,acc_st.op_prm_card_sub s
                WHERE t.LINE_ID=TMP_LINE_ID AND t.STATION_ID=TMP_STATION_ID
                   AND to_date(t.traffic_datetime,'yyyyMMddHH24miss') > TMP_TRAFFIC_TIME_ST
                   AND to_date(t.traffic_datetime,'yyyyMMddHH24miss') <= TMP_TRAFFIC_TIME_ED
                   and s.record_flag='0'
                     and s.card_main_id = t.card_main_type
                     and s.card_sub_id = t.card_sub_type
                   group by t.flag,t.card_main_type,t.card_sub_type,s.card_sub_name;
          END IF;
                 
      END;

  --未选择车站时，统计线路客流
  ELSE
      BEGIN

          --所有线路
          IF TMP_LINE_ID = 'all' THEN
             --主类型
             IF V_TYPE = 'MAIN' THEN
               OPEN INFO_CUR FOR
               SELECT nvl(SUM(t.traffic),0) traffic,t.flag,lpad(t.card_main_type,2,'0') card_main_type,s.card_main_name 
                 FROM acc_commu.cm_traffic_afc_min t,acc_st.op_prm_card_main s
                  WHERE to_date(t.traffic_datetime,'yyyyMMddHH24miss') > TMP_TRAFFIC_TIME_ST
                     AND to_date(t.traffic_datetime,'yyyyMMddHH24miss') <= TMP_TRAFFIC_TIME_ED
                     and t.card_main_type = s.card_main_id
                     and s.record_flag = '0'
                     group by t.flag,t.card_main_type,s.card_main_name;
             --子类型              
             ELSE
             
               OPEN INFO_CUR FOR
               SELECT nvl(SUM(t.traffic),0) traffic,t.flag,lpad(t.card_main_type,2,'0') card_main_type, lpad(t.card_sub_type,2,'0') card_sub_type,s.card_sub_name 
                 FROM acc_commu.cm_traffic_afc_min t,acc_st.op_prm_card_sub s
                  WHERE to_date(t.traffic_datetime,'yyyyMMddHH24miss') > TMP_TRAFFIC_TIME_ST
                     AND to_date(t.traffic_datetime,'yyyyMMddHH24miss') <= TMP_TRAFFIC_TIME_ED
                     and s.record_flag='0'
                     and s.card_main_id = t.card_main_type
                     and s.card_sub_id = t.card_sub_type
                     group by t.flag,t.card_main_type,t.card_sub_type,s.card_sub_name;
             END IF;
                     
          --某条线路
          ELSE
          
             --主类型
             IF V_TYPE = 'MAIN' THEN
             
               OPEN INFO_CUR FOR
               SELECT nvl(SUM(t.traffic),0) traffic,t.flag,lpad(t.card_main_type,2,'0') card_main_type,s.card_main_name
                    FROM acc_commu.cm_traffic_afc_min t,acc_st.op_prm_station s1,acc_st.op_prm_card_main s
                    WHERE to_date(t.traffic_datetime,'yyyyMMddHH24miss') > TMP_TRAFFIC_TIME_ST
                      AND to_date(t.traffic_datetime,'yyyyMMddHH24miss') <= TMP_TRAFFIC_TIME_ED
                      AND s1.station_id = t.station_id
                      and s1.belong_line_id = t.line_id
                      AND s1.belong_line_id = TMP_LINE_ID
                      AND s1.record_flag='0'
                      and t.card_main_type = s.card_main_id
                      and s.record_flag = '0'
                  group by t.flag,t.card_main_type,s.card_main_name;

             --子类型              
             ELSE
             
               OPEN INFO_CUR FOR
               SELECT nvl(SUM(t.traffic),0) traffic,t.flag,lpad(t.card_main_type,2,'0') card_main_type, lpad(t.card_sub_type,2,'0') card_sub_type,s.card_sub_name
                    FROM acc_commu.cm_traffic_afc_min t,acc_st.op_prm_station s1,acc_st.op_prm_card_sub s
                    WHERE to_date(t.traffic_datetime,'yyyyMMddHH24miss') > TMP_TRAFFIC_TIME_ST
                      AND to_date(t.traffic_datetime,'yyyyMMddHH24miss') <= TMP_TRAFFIC_TIME_ED
                      AND s1.station_id = t.station_id
                      and s1.belong_line_id = t.line_id
                      AND s1.belong_line_id = TMP_LINE_ID
                      AND s1.record_flag='0'
                      and s.record_flag='0'
                     and s.card_main_id = t.card_main_type
                     and s.card_sub_id = t.card_sub_type
                  group by t.flag,t.card_main_type,t.card_sub_type,s.card_sub_name;
             END IF;
             
          END IF;

      END;
  END IF;

  DBMS_OUTPUT.PUT_LINE('sqlerrm : ' || SQLERRM);

END UP_FM_FLOW_TYPE_TOTAL;
