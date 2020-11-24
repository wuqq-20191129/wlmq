CREATE OR REPLACE PROCEDURE UP_IC_ES_GEN_PRODUCE_BILL2
( orderNo IN CHAR                   --订单号
, finiPronum IN INTEGER             --有效票
, surplusNum IN INTEGER             --结余票
, trashyNum IN INTEGER              --废票
, esSamno IN VARCHAR2               --SAM卡号
, hdlFlag IN VARCHAR2               --处理标志
, achieveTime IN DATE               --生产时间
, orderMemo IN VARCHAR2             --备注
, esWorkTypeId IN VARCHAR2          --工作类型
, retResult  OUT INTEGER            --返回结果
, msg OUT VARCHAR2                  --输出信息
) AS

  finishFlag CHAR(1);               --订单完成标志
  planBillNo CHAR(12);              --生产计划单
  unFinishCount INTEGER;            --未完成数量
  
  --单号生成使用变量
  currentIntNo INTEGER;             --表记录的当前流水号
  initValue INTEGER;                --流水初始化值
  billType INTEGER;                 --生产单类型
  billYear INTEGER;                 --单据年
  curYear  INTEGER;                 --当前年
  waterNo INTEGER;                  --流水号
  strWaterNo VARCHAR2(16);          --流水号字符串值 
  billNo CHAR(12);                  --单号
  
  --统计生产使用数量
  totalDraw INTEGER;  --领票总数量
  totalSurplus INTEGER; --结余总数量
  totalTrash INTEGER;  --费票总数量
  totalProduce INTEGER; --生产数量
  totalProduceDetail INTEGER; --卡明细上传有效数量
  outBillNo CHAR(12); --出库单号
  addNum INTEGER;--票卡明细数量的增加数量正常情况下是1,如未完整完成且完成数量未0时,取0值
  totalPlanProduce INTEGER; --计划单总生产数量
  esOperator VARCHAR2(10); --ES操作员
  
  icMainType char(2);  --票卡主类型 员工票使用
  icSubType char(2);   --票卡子类型 员工票使用
        
  totalProduceBill INTEGER; --生产工作单记录数
  
  rec INTEGER;
  strSql VARCHAR2(1000);

  bill_no         char(12);    
  afc_main_type   char(2);   
  afc_sub_type    char(2);
  card_money     int;           
  vaild_date     date;     
  afc_line_id    char(2); 
  afc_station_id char(2);         
  machine_no     varchar2(4);  
  draw_quantity   int;            
  order_no       char(14);  
  out_bill_no    char(12);  
  out_water_no   varchar2(18); 
  card_ava_days   varchar2(3);  --多日票有效天数
  afc_exit_line_id    varchar2(2); --多日票限制出站线路
  afc_exit_station_id varchar2(2);  --多日票限制出站站点
  modal varchar2(3);  --多日票限制模式
    
  bill_no_1         char(12);    
  afc_main_type_1   char(2);   
  afc_sub_type_1    char(2);
  card_money_1      int;           
  vaild_date_1      date;     
  afc_line_id_1     char(2); 
  afc_station_id_1  char(2);         
  machine_no_1      varchar2(4);  
  draw_quantity_1   int;            
  order_no_1        char(14);  
  out_bill_no_1     char(12);  
  out_water_no_1    varchar2(18); 
  card_ava_days_1   varchar2(3);  --多日票有效天数
  afc_exit_line_id_1    varchar2(2); --多日票限制出站线路
  afc_exit_station_id_1 varchar2(2);  --多日票限制出站站点
  mode_1               varchar2(3);  --多日票限制模式
    
  total int; 
  n int;
BEGIN
  --更新订单的有效票、废票、结余票、SAM卡号、处理标志、生产时间、备注
  UPDATE ACC_TK.IC_PDU_ORDER_FORM 
    SET fini_pronum= finiPronum, surplus_num= surplusnum, trashy_num= trashynum, 
      es_samno= essamno, hdl_flag= hdlflag, achieve_time= achievetime, order_memo= ordermemo
    where order_no=orderNo;
    
  --将订单单对应的明细逻辑卡号合并，合并后与历史卡号段比较判断
  IF esworktypeid='00' THEN
  BEGIN
    --历史查重，如存在重复，重号卡写入重号表，更新订单备注
    ACC_TK.UP_IC_ES_CHECK_LOGIC(orderno);
    --卡号合并
    ACC_TK.UP_IC_ES_COMBINE_LOGIC(orderno);
  END;
  END IF;
  
  --更新订单完成标志
  BEGIN
    IF hdlflag='2' OR hdlflag='3' THEN
    BEGIN 
      UPDATE ACC_TK.IC_PDU_PLAN_ORDER_MAPPING 
        SET finish_flag= '1', detail_total= finipronum
        WHERE order_no= orderno;
    END;
    END IF;
    --判断生产计划是否还有未完成订单
    SELECT finish_flag, plan_bill_no, out_bill_no 
      into finishflag, planbillno, outbillno 
      FROM ACC_TK.ic_pdu_plan_order_mapping
      WHERE order_no = orderno;
    COMMIT;
  EXCEPTION
    WHEN OTHERS THEN
      BEGIN
        ROLLBACK;
        retResult := 1;
        msg := '更新订单表或对照表失败';
        return;
      END; 
  END;
  
  --判断生产计划是否还有未完成订单
  select count(*) into rec from dual 
    where EXISTS(select 1 from acc_tk.IC_PDU_PLAN_ORDER_MAPPING where plan_bill_no=planBillNo and finish_flag <>'1');
  if rec!=0 then
    begin
      retResult := 2;
      msg := '还有未完成订单';
      return;
    end;
  end if;
  
  ---------------------------------------需满足条件-----------------------------------------------------------------

  ---判断是否需生成生产单，如存在生产单，则返回
  select count(*) into totalProduceBill from acc_tk.IC_PDU_PRODUCE_BILL where out_bill_no=outBillNo;
  if totalProduceBill<>0 then
    begin
       retresult := 1;
       msg := '生产单已存在';
       return;
    end;
  end if;
  
  ----------------------------------------------需满足条件---------------------------------------------- 

  ----------------------------临时表-------------------------------------------------------------------
  --缓存生产明细
  strsql :=
  'CREATE GLOBAL TEMPORARY TABLE #ic_produce_bill_detail
  (
    bill_no             char(12)      NOT NULL,
    ic_main_type        char(2)       NULL,
    ic_sub_type         char(2)        NULL,
    card_money          int           NULL,
    vaild_date          date      NULL,
    line_id             char(2)       NULL,
    station_id          char(2)       NULL,
    draw_quantity       int           NULL,
    start_box_id        char(14)      NULL,
    end_box_id          char(14)      NULL,
    machine_no          varchar2(4)    NULL,

    es_samno            char(16)     NULL,
    afc_main_type       char(2)    null,
    afc_sub_type        char(2)    null,
    afc_line_id         char(2)    NULL,
    afc_station_id      char(2)    NULL,
    order_no            char(14)   null,
    out_bill_no         char(12)   null,
    out_water_no        varchar2(18) null,
    card_ava_days       varchar2(3) null, --多日票有效天数
    exit_line_id        varchar2(2) null, --多日票限制出站线路
    exit_station_id     varchar(2) null, --多日票限制出站站点
    modal               varchar2(3) null, --多日票限制模式
    
    afc_exit_line_id    varchar2(2) null, --多日票限制出站线路
    afc_exit_station_id varchar2(2) null --多日票限制出站站点
  )ON COMMIT PRESERVE ROWS;';
  execute immediate strSql;
  
  --生产明细缓存汇总
  strSql :=
  'CREATE GLOBAL TEMPORARY TABLE #result_detail
  (
    bill_no           char(12)        NOT NULL,
    ic_main_type      char(2)         NULL,
    ic_sub_type       char(2)         NULL,
    card_money        int             NULL,
    vaild_date        date            NULL,
    line_id           char(2)         NULL,
    station_id        char(2)         NULL,
    draw_quantity     int             NULL,
    start_box_id      char(14)        NULL,
    end_box_id        char(14)        NULL,
    machine_no        varchar2(4)     NULL,

    es_samno          char(16)        NULL,
    afc_main_type     char(2)         null,
    afc_sub_type      char(2)         null,
    afc_line_id       char(2)         NULL,
    afc_station_id    char(2)         NULL,
    order_no          char(14)        null,
    out_bill_no       char(12)        null,
    out_water_no      varchar2(18)    null,
    
    card_ava_days     varchar2(3) null, --多日票有效天数
    exit_line_id      varchar2(2) null, --多日票限制出站线路
    exit_station_id   varchar2(2) null, --多日票限制出站站点
    modal             varchar2(3) null, --多日票限制模式
    
    afc_exit_line_id  varchar2(2) null, --多日票限制出站线路
    afc_exit_station_id varchar2(2) null, --多日票限制出站站点
    
    n int null
  )ON COMMIT PRESERVE ROWS;';
  execute immediate strSql;
  
  --订单缓存
  strSql :=
  'CREATE GLOBAL TEMPORARY TABLE #order_form
  (
    order_no            char(14)  NOT NULL,
    card_main_code      char(2)   NULL,
    card_sub_code       char(2)   NULL,
    card_per_ava        date  NULL,
    card_mon            number(18,0)  NULL,
    draw_num            int       NULL,
    fini_pronum         int       NULL,
    surplus_num         int       NULL,
    trashy_num          int       NULL,
    es_samno            char(16)  NULL,
    line_code           char(2)    NULL,
    station_code        char(2)    NULL,
    card_ava_days       varchar2(3) null, --多日票有效天数
    exit_line_code      varchar2(2) null, --多日票限制出站线路
    exit_station_code   varchar2(2) null, --多日票限制出站站点
    modal               varchar2(3) null --多日票限制模式
  )ON COMMIT PRESERVE ROWS;';
  execute immediate strSql;
  
--订单映射表缓存
  strSql :=
  'CREATE GLOBAL TEMPORARY TABLE #ic_plan_order_mappi                                       ng
  (
    plan_bill_no char(12)     NOT NULL,
    order_no     char(14)     NOT NULL,
    finish_flag  char(1)      NULL,
    out_bill_no  char(12)     NULL,
    out_water_no varchar2(18) NULL

  )ON COMMIT PRESERVE ROWS;';
  execute immediate strSql;
  
  ---------------------------------------
  ---处理重号问题时使用---------------
  strSql :=
  'CREATE GLOBAL TEMPORARY TABLE #es_hunch_info
  (
    logi_id        char(20)       NULL,
    card_main_code char(2)        NULL,
    card_sub_code  char(2)        NULL,
    req_no         char(10)       NULL,
    phy_id         char(20)       NULL,
    prin_id        char(16)       NULL,
    manu_time      date           NULL,
    card_mon       number(12,2)   NULL,
    peri_avadate   date           NULL,
    kdc_version    char(2)        NULL,
    hdl_time       date           NULL,
    order_no       char(14)       NULL,
    status_flag    char(2)        NULL,
    card_type      char(3)        NULL,
    line_code      char(2)        NULL,
    station_code   char(2)        NULL,
    card_start_ava char(8)        NULL,
  )ON COMMIT PRESERVE ROWS;';
  execute immediate strSql;
  
  
  --统计废票数
  strSql :=
  'CREATE GLOBAL TEMPORARY TABLE #trash_num(
     order_no       char(14)       NULL,
     trash_num      int null
  )ON COMMIT PRESERVE ROWS;';
  execute immediate strSql;
  
  --废票临时表
  strSql :=
  'CREATE GLOBAL TEMPORARY TABLE #ic_useless_detail
  (
    bill_no      char(12)  NULL,
    order_no     char(14) NULL,
    card_no      char(20)  NULL,
    card_type    char(1)   NULL,
    ic_main_type char(2)  NULL,
    ic_sub_type  char(2)  NULL,
    line_id      char(2)  NULL,
    station_id   char(2)  NULL,
    card_money   int       NULL,
    valid_date   date NULL,
    machine_no   char(8)  NULL,
    flag         char(1)  NULL,
    phy_id       char(20) null,

    card_main_code  char(2) null,
    card_sub_code   char(2) null,
    line_code       char(2) null,
    station_code    char(2) null
  )ON COMMIT PRESERVE ROWS;';
  execute immediate strSql;

----------------------------临时表----------------------------------------------------------------------- 

    curYear := TO_NUMBER(to_char(SYSDATE,'yyyy'));
    initValue := 1;
    billType := 'SD';
    --取出工作类型
    select distinct ic_main_type,ic_sub_type into icMainType,icSubType
      from ACC_TK.IC_OUT_DATE_PLAN_DETAIL where bill_no = planBillNo;
    
    --生成生产单号------------------------------------
    --取出当前流水号
    select current_int_no into currentIntNo from ACC_TK.IC_PRM_BILL_CURRENT_FLOW where bill_main_type_id=billType;
    if(currentIntNo is not null) THEN--存在当前流水号
      begin
        waterNo := currentIntNo;
        --取出当前年
        select bill_year INTO billYear from ACC_TK.IC_PRM_BILL_CURRENT_DATE;
        if(billYear is null) THEN--当前年不存在
            begin
                insert into ACC_TK.IC_PRM_BILL_CURRENT_DATE(bill_year) values(curYear);
            end;
        else
            begin
              if(billYear<>curYear) THEN--系统当前年与表当前年不一致 已过1年
                begin
                  waterNo := initValue;
                  update ACC_TK.IC_PRM_BILL_CURRENT_DATE set bill_year=curYear;
                end;
              END IF;
              update ACC_TK.IC_PRM_BILL_CURRENT_FLOW set current_int_no=waterNo+1 where bill_main_type_id=billType;  
            end;
        end IF;
      END;
    else --当前流水号不存在
      begin
        waterNo := initValue;
          --流水号+1
        insert into ACC_TK.IC_PRM_BILL_CURRENT_FLOW(bill_main_type_id,current_no,current_int_no) values(billType,'',initValue+1);
      end;
    end if;
    msg :=  waterNo;
 
    --格式化流水号
    strWaterNo := LPAD(waterNo,6,'0');
    --单号=单类型+当前年+6位流水号
    billNo := billType||curYear||strWaterNo;

   -------------------------------------------------
   
   -----------------------生成生产单 ----------------------------------------
   --取出出库单号及ES操作员
   select out_bill_no,operator into outBillNo,esOperator 
      from ACC_TK.IC_OUT_DATE_PLAN where bill_no=planBillNo;
   
   --从生产出库单中查找领票数量
   select sum(out_num) into totalDraw from ACC_TK.IC_OUT_BILL_DETAIL where bill_no=outBillNo;
   
   ---统计数量
   select sum(trashy_num), sum(fini_pronum) into totalTrash, totalPlanProduce
      from ACC_TK.IC_PDU_ORDER_FORM
      where order_no in(select order_no from ACC_TK.IC_PDU_PLAN_ORDER_MAPPING where plan_bill_no=planBillNo);
      
   --统 计节余数
   totalSurplus := totalDraw-totalTrash-totalPlanProduce;
   
   ----生产生产单总表
   begin
     insert into ACC_TK.IC_PDU_PRODUCE_BILL(bill_no,out_bill_no,es_worktype_id,bill_date,
                                record_flag,remarks,es_operator,draw_total,system_balance,
                                es_useless_num,real_balance,man_useless_num,lost_num)
               values(billNo,outBillNo,esWorkTypeId,sysdate,'3','系统自动生成',esOperator,
                      totalDraw,totalSurplus,totalTrash,0,0,0);
     commit;
   exception
     when OTHERS THEN
     begin
       ROLLBACK;
       msg := '生成生产单失败';
       retresult := 1;
       return;
     end;
   end;
   
   --生成生产明细
   --缓存生产单订单映射表
   strSql := 'insert into #ic_plan_order_mapping(plan_bill_no,order_no,finish_flag,out_bill_no,out_water_no)
                select plan_bill_no,order_no,finish_flag,out_bill_no,out_water_no
                                from  ACC_TK.IC_PDU_PLAN_ORDER_MAPPING
                                where plan_bill_no=planBillNo';
    execute immediate strSql;
  
    --缓存订单表
    strSql := 'insert into #order_form(order_no,card_main_code,card_sub_code,card_per_ava,
                            card_mon,draw_num,fini_pronum,surplus_num,trashy_num,es_samno,
                            line_code,station_code,card_ava_days,exit_line_code,exit_station_code,modal)
                select order_no,card_main_code,card_sub_code,card_per_ava,
                       card_mon,draw_num,fini_pronum,surplus_num,trashy_num,
                       es_samno,line_code,station_code,card_ava_days,exit_line_code,exit_station_code,modal
                from ACC_TK.IC_PDU_ORDER_FORM where order_no in(select order_no from #ic_plan_order_mapping)';
    execute immediate strSql;
     
    --缓存生产明细
    strSql := 'insert into #ic_produce_bill_detail(bill_no,afc_main_type,afc_sub_type,
                                        card_money,vaild_date,afc_line_id,afc_station_id,es_samno,
                                        draw_quantity,order_no,out_bill_no,out_water_no,
                                        card_ava_days,afc_exit_line_id,afc_exit_station_id,modal)
                    select billNo ,A.card_main_code,A.card_sub_code,
                           A.card_mon,A.card_per_ava,A.line_code,A.station_code,A.es_samno,
                           A.fini_pronum,B.order_no,B.out_bill_no,B.out_water_no,
                           A.card_ava_days,A.exit_line_code,A.exit_station_code,A.mode
                           from #order_form A,#ic_plan_order_mapping B
                           where A.order_no =B.order_no';   
    execute immediate strSql;
    
    --更新机器号
    strSql := 'update #ic_produce_bill_detail set machine_no = B.device_id
                     from #ic_produce_bill_detail A,ACC_ST.OP_PRM_SAM_LIST B
                      where A.es_samno = B.sam_logical_id and B.dev_type_id="07" and B.record_flag="0"';
    execute immediate strSql;
    
    -----------------------------------------------合并订单号开始-----------------------------------------------------------
    n := 0;
    total := 0; 
    
  --NULL; 
END UP_IC_ES_GEN_PRODUCE_BILL2;
 