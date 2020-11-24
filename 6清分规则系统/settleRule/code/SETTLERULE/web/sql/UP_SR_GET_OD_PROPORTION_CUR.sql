CREATE OR REPLACE PROCEDURE UP_SR_GET_OD_PROPORTION_CUR(V_O_LINE_ID           IN  VARCHAR2,--开始线路
                                                        V_O_STATION_ID        IN  VARCHAR2,--开始站点
                                                        V_D_LINE_ID           IN  VARCHAR2,--结束线路
                                                        V_D_STATION_ID        IN  VARCHAR2,--结束站点
                                                        P_RESULTS             OUT SYS_REFCURSOR,--结果集
                                                        P_CODE                OUT INTEGER,--结果代码
                                                        P_ERRMSG              out varchar2)--结果信息

---------------------------------------------------------------------------------
--过程名:  UP_SR_GET_OD_PROPORTION_CUR
--系统：清分规则系统
--功能: 由OD路径条件实时返回OD线路权重数据
--返回结果代码：P_CODE
-------------------------------------------------------------------------------

AS
  TMP_PROPORTION_PERCENT     INTEGER; --临时 比例阀值
  TMP_O_LINE_ID              VARCHAR2(2);
  TMP_O_STATION_ID           VARCHAR2(2);
  TMP_D_LINE_ID              VARCHAR2(2);
  TMP_D_STATION_ID           VARCHAR2(2);

BEGIN

  --初始化入参
  if V_O_LINE_ID IS NULL then
     TMP_O_LINE_ID := '';
  ELSE
     TMP_O_LINE_ID := V_O_LINE_ID;
  end if;

  if V_O_STATION_ID IS NULL then
     TMP_O_STATION_ID := '';
  ELSE
     TMP_O_STATION_ID := V_O_STATION_ID;
  end if;

  if V_D_LINE_ID IS NULL then
     TMP_D_LINE_ID := '';
  ELSE
     TMP_D_LINE_ID := V_D_LINE_ID;
  end if;

  if V_D_STATION_ID IS NULL then
     TMP_D_STATION_ID := '';
  ELSE
     TMP_D_STATION_ID := V_D_STATION_ID;
  end if;

  -- 清理临时表
  delete T#SR_MIN_AVG_DISTANCE;

  -- 比例阀值
  select t.distance_thres into TMP_PROPORTION_PERCENT from sr_params_threshold t where t.record_flag='0' and rownum=1;

  -- 插入分组数据和最短里程路径到临时表
  INSERT INTO T#SR_MIN_AVG_DISTANCE (o_line_id, o_station_id, e_line_id, e_station_id,min_distance)
  select o_line_id, o_station_id, e_line_id, e_station_id,min(distance)
    from sr_distance_od
    where nvl(distance,0) <> 0 and record_flag='0' --非 换乘站到到换乘站
          and o_line_id = nvl(TMP_O_LINE_ID,o_line_id)
          and o_station_id = nvl(TMP_O_STATION_ID,o_station_id)
          and e_line_id = nvl(TMP_D_LINE_ID,e_line_id)
          and e_station_id = nvl(TMP_D_STATION_ID,e_station_id)
    group by o_line_id, o_station_id, e_line_id, e_station_id;


  -- 更新平均路径里程到临时表
  INSERT INTO T#SR_MIN_AVG_DISTANCE (o_line_id, o_station_id, e_line_id, e_station_id, min_distance, avg_distance)
  select a.o_line_id, a.o_station_id, a.e_line_id, a.e_station_id, b.min_distance, avg(a.distance)
    from sr_distance_od a,T#SR_MIN_AVG_DISTANCE b
    where  nvl(a.distance,0) <> 0 and a.record_flag='0' --非 换乘站到到换乘站
      and a.o_line_id=b.o_line_id(+) and a.o_station_id = b.o_station_id(+)
      and a.e_line_id = b.e_line_id(+) and a.e_station_id = b.e_station_id(+)
      and (a.distance - b.min_distance)/b.min_distance <= TMP_PROPORTION_PERCENT
      and a.o_line_id = nvl(TMP_O_LINE_ID,a.o_line_id)
      and a.o_station_id = nvl(TMP_O_STATION_ID,a.o_station_id)
      and a.e_line_id = nvl(TMP_D_LINE_ID,a.e_line_id)
      and a.e_station_id = nvl(TMP_D_STATION_ID,a.e_station_id)
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
        and (a.distance - b.min_distance)/b.min_distance <= TMP_PROPORTION_PERCENT
   );

  -- 分摊比例
  begin
    update sr_distance_od a set a.distance_percent = (a.distance_base * a.distance_base * a.distance_base)/(
      select sum(b.distance_base * b.distance_base * b.distance_base)
        from sr_distance_od b
        where b.record_flag='0' and a.id = b.id
            and b.o_line_id = nvl(TMP_O_LINE_ID,b.o_line_id)
            and b.o_station_id = nvl(TMP_O_STATION_ID,b.o_station_id)
            and b.e_line_id = nvl(TMP_D_LINE_ID,b.e_line_id)
            and b.e_station_id = nvl(TMP_D_STATION_ID,b.e_station_id)
        group by b.o_line_id, b.o_station_id, b.e_line_id, b.e_station_id
     );
   EXCEPTION
    WHEN OTHERS THEN
      ROLLBACK;
      P_CODE := -1;
      DBMS_OUTPUT.PUT_LINE('sqlerrm : ' || SQLERRM);
      P_ERRMSG := SQLCODE || ':' || SQLERRM;
      RETURN;
  end;

  --将结果集放入游标
  BEGIN
      open P_RESULTS for
          select e.o_line_id, e.o_station_id, e.e_line_id d_line_id, e.e_station_id d_station_id,
                 f.pass_line_out dispart_line_id, round(sum(e.distance_percent * f.pass_percent),5) in_percent
          from sr_distance_od e, (
             select c.pass_distance/d.sum_distance pass_percent,c.* from sr_distance_change c,(
               select a.od_id,sum(a.pass_distance) sum_distance from sr_distance_change a,sr_distance_od b
               where a.od_id = b.id
               and b.record_flag = '0'
               and b.distance_percent > 0
               and b.o_line_id <> b.e_line_id --OD不同一线路
                and b.o_line_id = nvl(TMP_O_LINE_ID,b.o_line_id)
                and b.o_station_id = nvl(TMP_O_STATION_ID,b.o_station_id)
                and b.e_line_id = nvl(TMP_D_LINE_ID,b.e_line_id)
                and b.e_station_id = nvl(TMP_D_STATION_ID,b.e_station_id)
               group by a.od_id
               ) d
             where c.od_id = d.od_id) f
           where f.od_id = e.id
                  and e.o_line_id = nvl(TMP_O_LINE_ID,e.o_line_id)
                  and e.o_station_id = nvl(TMP_O_STATION_ID,e.o_station_id)
                  and e.e_line_id = nvl(TMP_D_LINE_ID,e.e_line_id)
                  and e.e_station_id = nvl(TMP_D_STATION_ID,e.e_station_id)
          group by e.o_line_id, e.o_station_id, e.e_line_id, e.e_station_id, f.pass_line_out

          union

          -- 插入OD同一线路 or 换乘站到换乘站 (e.o_line_id = e.e_line_id or e.distance = 0)
          select distinct e.o_line_id, e.o_station_id, e.e_line_id d_line_id, e.e_station_id d_station_id,
                 e.o_line_id dispart_line_id, 1 in_percent
          from sr_distance_od e
          where (e.o_line_id = e.e_line_id or e.distance = 0)
              and e.o_line_id = nvl(TMP_O_LINE_ID,e.o_line_id)
              and e.o_station_id = nvl(TMP_O_STATION_ID,e.o_station_id)
              and e.e_line_id = nvl(TMP_D_LINE_ID,e.e_line_id)
              and e.e_station_id = nvl(TMP_D_STATION_ID,e.e_station_id)

          union

          -- 换乘站到换乘站 (e.o_line_id <> e.e_line_id and e.distance = 0)
          select distinct e.o_line_id, e.o_station_id, e.e_line_id d_line_id, e.e_station_id d_station_id,
                 e.e_line_id dispart_line_id, 0 in_percent
          from sr_distance_od e
          where e.distance = 0 and e.o_line_id <> e.e_line_id
              and e.o_line_id = nvl(TMP_O_LINE_ID,e.o_line_id)
              and e.o_station_id = nvl(TMP_O_STATION_ID,e.o_station_id)
              and e.e_line_id = nvl(TMP_D_LINE_ID,e.e_line_id)
              and e.e_station_id = nvl(TMP_D_STATION_ID,e.e_station_id);
  end;

  -- 清理临时表
  delete T#SR_MIN_AVG_DISTANCE;
  commit;

  P_CODE := 0;
  P_ERRMSG := '查询到线路权重数据！';

END UP_SR_GET_OD_PROPORTION_CUR;
