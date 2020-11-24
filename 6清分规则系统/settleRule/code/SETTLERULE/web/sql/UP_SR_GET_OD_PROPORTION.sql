CREATE OR REPLACE PROCEDURE UP_SR_GET_OD_PROPORTION(P_RESULT              OUT INTEGER,
                                                    P_ERRMSG              out varchar2)

---------------------------------------------------------------------------------
--过程名:  UP_SR_GET_OD_PROPORTION
--系统：清分规则系统
--功能: 由OD路径表数据生成OD线路权重表数据
--返回结果代码：P_RESULT
-------------------------------------------------------------------------------

AS
  V_PROPORTION_PERCENT     INTEGER; --临时 比例阀值
  TMP_VERSION              VARCHAR(12);

BEGIN

  -- 清理临时表
  delete T#SR_MIN_AVG_DISTANCE;

  select to_char(sysdate,'yyyyMMddhh24mi') into TMP_VERSION from dual;

  -- 比例阀值
  select t.distance_thres into V_PROPORTION_PERCENT from sr_params_threshold t where t.record_flag='0' and rownum=1;

  -- 插入分组数据和最短里程路径到临时表
  INSERT INTO T#SR_MIN_AVG_DISTANCE (o_line_id, o_station_id, e_line_id, e_station_id,min_distance)
  select o_line_id, o_station_id, e_line_id, e_station_id,min(distance)
    from sr_distance_od
    where nvl(distance,0) <> 0 and record_flag='0' --非 换乘站到到换乘站
    group by o_line_id, o_station_id, e_line_id, e_station_id;


  -- 更新平均路径里程到临时表
  INSERT INTO T#SR_MIN_AVG_DISTANCE (o_line_id, o_station_id, e_line_id, e_station_id, min_distance, avg_distance)
  select a.o_line_id, a.o_station_id, a.e_line_id, a.e_station_id, b.min_distance, avg(a.distance)
    from sr_distance_od a,T#SR_MIN_AVG_DISTANCE b
    where  nvl(a.distance,0) <> 0 and a.record_flag='0' --非 换乘站到到换乘站
      and a.o_line_id=b.o_line_id(+) and a.o_station_id = b.o_station_id(+)
      and a.e_line_id = b.e_line_id(+) and a.e_station_id = b.e_station_id(+)
      and (a.distance - b.min_distance)/b.min_distance <= V_PROPORTION_PERCENT
    group by a.o_line_id, a.o_station_id, a.e_line_id, a.e_station_id, b.min_distance;

  -- 删除最小路径临时数据
  delete T#SR_MIN_AVG_DISTANCE where avg_distance is null or avg_distance = '';

  -- 分摊基数
  update sr_distance_od a set a.distance_base = (
    select a.distance + 2 * (b.avg_distance - a.distance)
      from T#SR_MIN_AVG_DISTANCE b
      where a.o_line_id=b.o_line_id(+) and a.o_station_id = b.o_station_id(+)
        and a.e_line_id = b.e_line_id(+) and a.e_station_id = b.e_station_id(+)
        and a.record_flag='0'
        and (a.distance - b.min_distance)/b.min_distance <= V_PROPORTION_PERCENT
   );

  -- 分摊比例
  begin
    update sr_distance_od a set a.distance_percent = (a.distance_base * a.distance_base * a.distance_base)/(
      select sum(b.distance_base * b.distance_base * b.distance_base)
        from sr_distance_od b
        where b.record_flag='0' and a.id = b.id
        group by b.o_line_id, b.o_station_id, b.e_line_id, b.e_station_id
     );
   EXCEPTION
    WHEN OTHERS THEN
      ROLLBACK;
      P_RESULT := -1;
      DBMS_OUTPUT.PUT_LINE('sqlerrm : ' || SQLERRM);
      P_ERRMSG := SQLCODE || ':' || SQLERRM;
      RETURN;
  end;


  -- 线路权重 插入OD不同一线路
  update sr_proportion set record_flag = '2' where record_flag = '0'; -- 更新原来数据为历史版本

  insert into sr_proportion
    (o_line_id, o_station_id, d_line_id, d_station_id, dispart_line_id, in_percent,
     version, record_flag, create_time, create_operator)
  select e.o_line_id, e.o_station_id, e.e_line_id, e.e_station_id, f.pass_line_out, sum(e.distance_percent * f.pass_percent),
         TMP_VERSION, '0', sysdate, 'system'
  from sr_distance_od e, (
     select c.pass_distance/d.sum_distance pass_percent,c.* from sr_distance_change c,(
       select a.od_id,sum(a.pass_distance) sum_distance from sr_distance_change a,sr_distance_od b
       where a.od_id = b.id
       and b.record_flag = '0'
       and b.distance_percent > 0
       and b.o_line_id <> b.e_line_id --OD不同一线路
       group by a.od_id
       ) d
     where c.od_id = d.od_id) f
   where f.od_id = e.id
  group by e.o_line_id, e.o_station_id, e.e_line_id, e.e_station_id, f.pass_line_out;


  -- 插入OD同一线路 or 换乘站到换乘站 (e.o_line_id = e.e_line_id or e.distance = 0)
  insert into sr_proportion
    (o_line_id, o_station_id, d_line_id, d_station_id, dispart_line_id, in_percent,
     version, record_flag, create_time, create_operator)
  select distinct e.o_line_id, e.o_station_id, e.e_line_id, e.e_station_id, e.o_line_id, 1,
         TMP_VERSION, '0', sysdate, 'system'
  from sr_distance_od e
  where e.o_line_id = e.e_line_id or e.distance = 0;

  -- 换乘站到换乘站 (e.o_line_id <> e.e_line_id and e.distance = 0)
  insert into sr_proportion
    (o_line_id, o_station_id, d_line_id, d_station_id, dispart_line_id, in_percent,
     version, record_flag, create_time, create_operator)
  select distinct e.o_line_id, e.o_station_id, e.e_line_id, e.e_station_id, e.e_line_id, 0,
         TMP_VERSION, '0', sysdate, 'system'
  from sr_distance_od e
  where e.o_line_id <> e.e_line_id and e.distance = 0;


  -- 清理临时表
  delete T#SR_MIN_AVG_DISTANCE;
  commit;

  select count(1) into P_RESULT from sr_distance_od where record_flag = 0;
  --P_RESULT := 0;
  P_ERRMSG := '生成线路权重数据' || P_RESULT || '条记录！';

END UP_SR_GET_OD_PROPORTION;
