CREATE OR REPLACE PROCEDURE UP_IC_ES_GEN_PRODUCE_BILL
( orderNo IN CHAR                   --订单号
, finiPronum IN INTEGER             --有效票
, surplusNum IN INTEGER             --结余票
, trashyNum IN INTEGER              --废票
, esSamno IN VARCHAR2               --SAM卡号
, hdlFlag IN VARCHAR2               --处理标志
, achieveTime IN DATE               --生产时间
, orderMemo IN VARCHAR2             --备注
, esWorkTypeId IN VARCHAR2          --工作类型
, retCode  OUT INTEGER              --返回结果
, retMsg OUT VARCHAR2               --输出信息
) 
  ---------------------------------------------------------------------------------
  --过程名:  UP_IC_ES_GEN_PRODUCE_BILL
  --功能: 通讯程序自动生成生产单
  -------------------------------------------------------------------------------
AS

  recNum INTEGER;
  strSql VARCHAR2(1000);


  planBillNo  CHAR(12);              --生产计划单
  outBillNo   CHAR(12);               --出库单号
  billNo      CHAR(12);                  --单号

  esOperator VARCHAR2(10); --ES操作员
  totalPlanProduce INTEGER; --计划单总生产数量
  totalDraw INTEGER;  --领票总数量
  totalSurplus INTEGER; --结余总数量
  totalTrash INTEGER;  --费票总数量

  orderNoInt INTEGER;                   --订单号
  curInt INTEGER; --游标循环序数
  aaaa INTEGER ;
BEGIN

  --清理临时表
  DELETE ACC_TK.T#IC_PDU_PLAN_ORDER_MAPPING;
   DELETE ACC_TK.T#IC_PDU_ORDER_FORM;
   DELETE ACC_TK.T#IC_PDU_PRODUCE_BILL_DETAIL;
   DELETE acc_tk.T#IC_PDU_RESULT_DETAIL;

  --取出库计划单号、出库单号
  SELECT plan_bill_no, out_bill_no into planbillno, outbillno
      FROM ACC_TK.ic_pdu_plan_order_mapping
      WHERE order_no = orderno;
  
  ---判断是否需生成生产单，如存在生产单，则返回
  select count(*) into recNum FROM dual
    where EXISTS(SELECT 1 from acc_tk.IC_PDU_PRODUCE_BILL where out_bill_no=outBillNo);
  if recNum<>0 then
    begin
       retCode := 4;
       retMsg := '生产单已存在';
       return;
    end;
  end if;

  --更新订单的有效票、废票、结余票、SAM卡号、处理标志、生产时间、备注
  BEGIN
    UPDATE ACC_TK.IC_PDU_ORDER_FORM
      SET fini_pronum= finiPronum, surplus_num= surplusnum, trashy_num= trashynum,
        es_samno= essamno, hdl_flag= hdlflag, achieve_time= achievetime, order_memo= ordermemo
      where order_no=orderNo;
  EXCEPTION
    WHEN OTHERS THEN
    BEGIN
        ROLLBACK;
        DBMS_OUTPUT.PUT_LINE('sqlcode : ' || SQLCODE);
        DBMS_OUTPUT.PUT_LINE('sqlerrm : ' || SQLERRM);
        retCode := 1;
        retMsg := '更新生产订单表失败,'||SQLCODE||':'||SQLERRM;
        return;
    END;
  END;
  
  --当订单未完整完成时（hdlflag='2'：被中途中止结束订单）
  BEGIN
    IF hdlflag='2' THEN
      BEGIN
        -- 更新未开始的所有订单为2 未完整完成状态
        UPDATE ACC_TK.IC_PDU_ORDER_FORM
          SET fini_pronum= 0, surplus_num= pro_num, trashy_num= 0,
            es_samno= '', hdl_flag= hdlflag, achieve_time= achievetime, order_memo= ordermemo
          where order_no IN (SELECT order_no FROM ACC_TK.IC_PDU_PLAN_ORDER_MAPPING WHERE out_bill_no = outbillno )
                AND hdl_flag = 0;
        -- 更新除正在生产外的所有订单为1 完成状态        
        UPDATE ACC_TK.IC_PDU_PLAN_ORDER_MAPPING SET finish_flag= '1', detail_total= 0
          WHERE out_bill_no = outbillno AND order_no NOT IN (
                SELECT order_no FROM ACC_TK.IC_PDU_ORDER_FORM WHERE hdl_flag=1
          ) AND finish_flag<> '1';
      END;
    END IF;
  EXCEPTION
    WHEN OTHERS THEN
      BEGIN
        ROLLBACK;
        retCode := 1;
        retMsg := '更新生产订单表失败2,'||SQLCODE||':'||SQLERRM;
        return;
      END;
  END;

  --更新订单完成标志
  BEGIN
    IF hdlflag='3' THEN
    BEGIN
      UPDATE ACC_TK.IC_PDU_PLAN_ORDER_MAPPING
        SET finish_flag= '1', detail_total= finipronum
        WHERE order_no= orderno;
    END;
    END IF;
  EXCEPTION
    WHEN OTHERS THEN
      BEGIN
        ROLLBACK;
        retCode := 2;
        retMsg := '更新订单表或对照表失败';
        return;
      END;
  END;

  --判断生产计划是否还有未完成订单
  select count(*) into recNum from dual
    where EXISTS(select 1 from acc_tk.IC_PDU_PLAN_ORDER_MAPPING where plan_bill_no=planBillNo and finish_flag <>'1');
  if recNum!=0 then
    begin
      retCode := 3;
      retMsg := '还有未完成订单';
      return;
    end;
  end if;

  --生产单号
  billNo := ACC_TK.uf_ic_es_get_bill_flow(retMsg);

   begin
    --取出ES操作员
     select operator into esOperator from ACC_TK.IC_OUT_DATE_PLAN where bill_no=planBillNo;

     ---统计数量
     select sum(draw_num),sum(trashy_num), sum(fini_pronum), sum(surplus_num)
          into totalDraw, totalTrash, totalPlanProduce, totalSurplus
        from ACC_TK.IC_PDU_ORDER_FORM
        where order_no in(select order_no from ACC_TK.IC_PDU_PLAN_ORDER_MAPPING where plan_bill_no=planBillNo);
   exception
     WHEN others THEN
     begin
       ROLLBACK;
       DBMS_OUTPUT.PUT_LINE('sqlcode : ' || SQLCODE||':'||planBillNo);
       DBMS_OUTPUT.PUT_LINE('sqlerrm : ' || SQLERRM);
       retMsg := '取出ES操作员或统计数量失败,'||SQLCODE||':'||SQLERRM;
       retCode := 5;
       return;
     end;
   end;
   
   --由于领票数量没有分拆到 生产单，所以需从原出库单查询领票数量
   SELECT out_num INTO totalDraw FROM ic_out_bill_detail WHERE bill_no = outbillno;

  ----生产生产单总表
   begin
     insert into ACC_TK.IC_PDU_PRODUCE_BILL(bill_no,out_bill_no,es_worktype_id,bill_date,
                                record_flag,remarks,es_operator,draw_total,system_balance,
                                es_useless_num,real_balance,man_useless_num,lost_num)
               values(billNo,outBillNo,esWorkTypeId,sysdate,'3','系统自动生成',esOperator,
                      totalDraw,totalSurplus,totalTrash,0,0,0);
   exception
     when OTHERS THEN
     begin
       ROLLBACK;
       DBMS_OUTPUT.PUT_LINE('sqlcode : ' || SQLCODE||':'||planBillNo);
       DBMS_OUTPUT.PUT_LINE('sqlerrm : ' || SQLERRM);
       retMsg := '生成生产单失败,'||SQLCODE||':'||SQLERRM;
       retCode := 6;
       return;
     end;
   end;

   --生成生产明细
   --缓存生产单订单映射表
   begin
     insert into ACC_TK.T#IC_PDU_PLAN_ORDER_MAPPING(plan_bill_no,order_no,finish_flag,out_bill_no,out_water_no)
                  select plan_bill_no,order_no,finish_flag,out_bill_no,out_water_no
                  from  ACC_TK.IC_PDU_PLAN_ORDER_MAPPING where plan_bill_no=planBillNo;
   exception
    WHEN others THEN
    begin
       ROLLBACK;
       retMsg := '缓存生产单订单映射表失败,'||SQLCODE||':'||SQLERRM;
       retCode := 7;
       return;
    end;
   end;

   --缓存订单表
   begin
     insert into ACC_TK.T#IC_PDU_ORDER_FORM(order_no,card_main_code,card_sub_code,card_per_ava,card_mon,
                        draw_num,fini_pronum,surplus_num,trashy_num,
                        es_samno,line_code,station_code,
                        card_ava_days,exit_line_code,exit_station_code,model)
               select order_no,card_main_code,card_sub_code,card_per_ava,CARD_MON,
                      draw_num,fini_pronum,surplus_num,trashy_num,
                      es_samno,line_code,station_code,
                      card_ava_days,exit_line_code,exit_station_code, model
              from ACC_TK.IC_PDU_ORDER_FORM
              where order_no in(select order_no from ACC_TK.T#IC_PDU_PLAN_ORDER_MAPPING);
   exception
    when OTHERS then
    begin
       ROLLBACK;
       retMsg := '缓存订单表失败,'||SQLCODE||':'||SQLERRM;
       retCode := 8;
       return;
    end;
   end;

   begin
     --缓存生产明细
     insert into ACC_TK.T#IC_PDU_PRODUCE_BILL_DETAIL(bill_no,afc_main_type,afc_sub_type,
                          card_money,vaild_date,afc_line_id,afc_station_id,es_samno,
                          draw_quantity,order_no,out_bill_no,out_water_no,
                          card_ava_days,afc_exit_line_id,afc_exit_station_id,modal)
                select billNo ,A.card_main_code,A.card_sub_code,
                       A.card_mon,A.card_per_ava,A.line_code,A.station_code,A.es_samno,
                       A.fini_pronum,B.order_no,B.out_bill_no,B.out_water_no,
                       A.card_ava_days,A.exit_line_code,A.exit_station_code,A.model
               from  ACC_TK.T#IC_PDU_ORDER_FORM A,ACC_TK.T#IC_PDU_PLAN_ORDER_MAPPING B
               where A.order_no =B.order_no;
   exception
    when OTHERS then
    begin
      ROLLBACK;
      DBMS_OUTPUT.PUT_LINE('sqlcode : ' || SQLCODE);
        DBMS_OUTPUT.PUT_LINE('sqlerrm : ' || SQLERRM);
      retMsg := '缓存生产明细失败,'||SQLCODE||':'||SQLERRM;
      retCode := 9;
      return;
    end;
   end;

   BEGIN
     --更新机器号
     update ACC_TK.T#IC_PDU_PRODUCE_BILL_DETAIL A
          set machine_no = (SELECT B.device_id from ACC_ST.OP_PRM_SAM_LIST B
                   where A.es_samno = B.sam_logical_id and B.dev_type_id='07' and B.record_flag='0');
   EXCEPTION
    WHEN OTHERS THEN
    BEGIN
      ROLLBACK;
      retMsg := '更新机器号失败,'||SQLCODE||':'||SQLERRM;
      retCode := 10;
      return;
    END;
   END;

   -----------------------------------------------合并订单号开始-----------------------------------------------------------
   DECLARE
     cursor cur_pbd is
        select bill_no,afc_main_type,afc_sub_type,card_money,vaild_date,afc_line_id,
               afc_station_id,machine_no,draw_quantity,order_no,out_bill_no,out_water_no,
               card_ava_days,afc_exit_line_id,afc_exit_station_id,modal
        from  ACC_TK.T#IC_PDU_PRODUCE_BILL_DETAIL
        order by order_no,bill_no,afc_main_type,afc_sub_type,card_money,vaild_date,afc_line_id,
                 afc_station_id,machine_no,out_bill_no,out_water_no,card_ava_days,
                 afc_exit_line_id,afc_exit_station_id,modal;
    BEGIN
      curInt := 0;
      for pbd in cur_pbd
      LOOP
          IF curInt>0 AND to_number(pbd.order_no)=orderNoInt+1 THEN
              BEGIN
                update acc_tk.T#IC_PDU_RESULT_DETAIL
                  set draw_quantity=draw_quantity+pbd.draw_quantity,end_box_id=pbd.order_no,order_no=pbd.order_no
                  where order_no=orderNoInt;
              end;
          ELSE
            begin
              insert into acc_tk.T#IC_PDU_RESULT_DETAIL(bill_no,afc_main_type,afc_sub_type,
                                       card_money,vaild_date,afc_line_id,afc_station_id,machine_no,
                                       draw_quantity,start_box_id,end_box_id,out_bill_no,out_water_no,
                                       card_ava_days,afc_exit_line_id,afc_exit_station_id,modal,order_no)
                   values(pbd.bill_no,pbd.afc_main_type,pbd.afc_sub_type,pbd.card_money,pbd.vaild_date,
                          pbd.afc_line_id,pbd.afc_station_id,pbd.machine_no,pbd.draw_quantity,
                          pbd.order_no,pbd.order_no,pbd.out_bill_no,pbd.out_water_no,pbd.card_ava_days,
                          pbd.afc_exit_line_id,pbd.afc_exit_station_id,pbd.modal,pbd.order_no);
            end;
          end if;
          --SELECT draw_quantity INTO draw_quantity_tmp FROM T#IC_PDU_RESULT_DETAIL WHERE order_no=pbd.order_no;
          curInt := curInt+1;
          orderNoInt := pbd.order_no;
          /*
        orderNoInt := "TO_NUMBER"(pbd.order_no)-1;
        select count(*) into recNum from dual
          where EXISTS(select 1 from acc_tk.T#IC_PDU_RESULT_DETAIL where order_no=orderNoInt);
        if recNum=0 then
          begin
            insert into acc_tk.T#IC_PDU_RESULT_DETAIL(bill_no,afc_main_type,afc_sub_type,
                                     card_money,vaild_date,afc_line_id,afc_station_id,machine_no,
                                     draw_quantity,start_box_id,end_box_id,out_bill_no,out_water_no,
                                     card_ava_days,afc_exit_line_id,afc_exit_station_id,modal,order_no)
                 values(pbd.bill_no,pbd.afc_main_type,pbd.afc_sub_type,pbd.card_money,pbd.vaild_date,
                        pbd.afc_line_id,pbd.afc_station_id,pbd.machine_no,pbd.draw_quantity,
                        pbd.order_no,pbd.order_no,pbd.out_bill_no,pbd.out_water_no,pbd.card_ava_days,
                        pbd.afc_exit_line_id,pbd.afc_exit_station_id,pbd.modal,pbd.order_no);
          end;
        else
          begin
            update acc_tk.T#IC_PDU_RESULT_DETAIL
              set draw_quantity=draw_quantity+pbd.draw_quantity,end_box_id=pbd.order_no,order_no=orderNoInt
              where order_no=orderNoInt;
          end;
        end if;
        */
        --循环做隐含检查 %notfound
      end loop;
    end;
    ---------------------------------------------合并订单号结束-------------------------------------------------

   BEGIN
     --转换AFC票卡类型成ic票库票卡类型
     /*
     update acc_tk.T#IC_PDU_RESULT_DETAIL A set(ic_main_type,ic_sub_type)=
          (select B.ic_main_type,B.ic_sub_type from acc_tk.IC_COD_CARD_TYPE_CONTRAST B
            where A.afc_main_type = B.CARD_MAIN_TYPE and A.afc_sub_type=B.CARD_SUB_TYPE);
            */
     update acc_tk.T#IC_PDU_RESULT_DETAIL A set(ic_main_type,ic_sub_type)=
          (SELECT DISTINCT ic_main_type,ic_sub_type FROM ic_out_bill_detail WHERE bill_no=outBillNo);
   EXCEPTION
    WHEN OTHERS THEN
    BEGIN
      ROLLBACK;
      DBMS_OUTPUT.PUT_LINE('sqlcode : ' || SQLCODE);
      DBMS_OUTPUT.PUT_LINE('sqlerrm : ' || SQLERRM);
      retMsg := '转换AFC票卡类型成ic票库票卡类型失败,'||SQLCODE||':'||SQLERRM;
      retCode := 11;
      return;
    END;
   END;

   BEGIN
     --转换AFC车站代码成ic票库车站代码
     update acc_tk.T#IC_PDU_RESULT_DETAIL A set(line_id,station_id)=
        (SELECT DISTINCT B.line_id,B.station_id from acc_tk.IC_COD_STATION_CONTRAST B
        where A.afc_line_id =B.line_code and A.afc_station_id=B.station_code);
   EXCEPTION
     WHEN OTHERS THEN
     BEGIN
      ROLLBACK;
      DBMS_OUTPUT.PUT_LINE('sqlcode : ' || SQLCODE);
      DBMS_OUTPUT.PUT_LINE('sqlerrm : ' || SQLERRM);
      retMsg := '转换AFC车站代码成ic票库车站代码失败,'||SQLCODE||':'||SQLERRM;
      retCode := 12;
      return;
    END;
   END;

   begin
    update acc_tk.T#IC_PDU_RESULT_DETAIL A set(exit_line_id,exit_station_id)=
      (SELECT DISTINCT B.line_id,B.station_id from acc_tk.IC_COD_STATION_CONTRAST B
           where A.afc_exit_line_id =B.line_code and A.afc_exit_station_id=B.station_code);
   EXCEPTION
     WHEN OTHERS THEN
     BEGIN
      ROLLBACK;
      DBMS_OUTPUT.PUT_LINE('sqlcode : ' || SQLCODE);
      DBMS_OUTPUT.PUT_LINE('sqlerrm : ' || SQLERRM);
      retMsg := '转换AFC车站代码成ic票库车站代码失败,'||SQLCODE||':'||SQLERRM;
      retCode := 13;
      return;
    END;
   END;

   begin
     --插入生产明细表(有效票)
     insert into ACC_TK.IC_PDU_PRODUCE_BILL_DETAIL(water_no,bill_no,ic_main_type,ic_sub_type,card_money,
            vaild_date,line_id,station_id,machine_no,draw_quantity,start_box_id,
            end_box_id,card_ava_days,exit_line_id,exit_station_id,model)
        select seq_ic_pdu_produce_bill_detail.nextval,bill_no,ic_main_type,ic_sub_type,card_money,vaild_date,line_id,
               station_id,machine_no,draw_quantity,start_box_id,end_box_id,
               card_ava_days,exit_line_id,exit_station_id,modal
        from acc_tk.T#IC_PDU_RESULT_DETAIL;
   exception
     when others then
     begin
       ROLLBACK;
       DBMS_OUTPUT.PUT_LINE('sqlcode : ' || SQLCODE);
       DBMS_OUTPUT.PUT_LINE('sqlerrm : ' || SQLERRM);
       retMsg := '插入生产明细表失败,'||SQLCODE||':'||SQLERRM;
       retCode := 14;
       return;
     end;
   end;

   --导入缓存数据到正式明细表---------------------------------------------------------
   if(esWorkTypeId ='00') then--初始化订单
   begin
       begin
         insert into ACC_TK.IC_ES_INITI_INFO(logical_id, card_main_type, card_sub_type, req_no, phy_id,
                                 print_id, manu_time, card_money, peri_avadate, kdc_version,
                                 hdl_time, order_no, status_flag, card_type,
                                 card_ava_days,exit_line_code,exit_station_code,model)
                      select     logical_id, card_main_type, card_sub_type, req_no, phy_id,
                                 print_id, manu_time, card_money, peri_avadate, kdc_version,
                                 hdl_time, order_no, status_flag, card_type,
                                 card_ava_days,exit_line_code,exit_station_code,model
                      from ACC_TK.IC_ES_INITI_INFO_BUF;
                      
         --插入物理卡号与逻辑卡号对照备份表
         insert into ACC_ST.op_his_phy_logic_list(physic_no,logic_no)
                      SELECT phy_id,logical_id from ACC_TK.IC_ES_INITI_INFO_BUF;
                      
         --删除物理卡号与逻辑卡号对照表已经制卡数据
         --DELETE FROM ACC_ST.op_prm_phy_logic_list WHERE physic_no in(SELECT phy_id from ACC_TK.IC_ES_INITI_INFO_BUF);
         
         --删除Buf临时数量(票务入库会删除，此处不能删除)
         --DELETE FROM ACC_TK.IC_ES_INITI_INFO_BUF;
                      
     exception
      when others then
      begin
        ROLLBACK;
        DBMS_OUTPUT.PUT_LINE('sqlcode : ' || SQLCODE);
        DBMS_OUTPUT.PUT_LINE('sqlerrm : ' || SQLERRM);
        retMsg := '插入初始化订单表失败,'||SQLCODE||':'||SQLERRM;
        retCode := 15;
        return;
      end;
     end;
   end;
   end if;

   if(esWorkTypeId ='01') then       --预赋值订单
   begin
     begin
       insert into ACC_TK.IC_ES_HUNCH_INFO(logical_id, card_main_type, card_sub_type, req_no, phy_id,
                                 print_id, manu_time, card_money, peri_avadate, kdc_version,
                                 hdl_time, order_no, status_flag, card_type,
                                 line_code, station_code, card_start_ava,
                                 card_ava_days,exit_line_code,exit_station_code,model
                                 )
                     select     logical_id, card_main_type, card_sub_type, req_no, phy_id,
                                print_id, manu_time, card_money, peri_avadate, kdc_version,
                                hdl_time, order_no, status_flag, card_type,
                                line_code, station_code, card_start_ava ,
                                card_ava_days,exit_line_code,exit_station_code,model
                     from     ACC_TK.IC_ES_HUNCH_INFO_BUF;
                     
       --插入物理卡号与逻辑卡号对照备份表
       insert into ACC_ST.op_his_phy_logic_list(physic_no,logic_no)
                    SELECT phy_id,logical_id from ACC_TK.IC_ES_HUNCH_INFO_BUF;
                      
       --删除物理卡号与逻辑卡号对照表已经制卡数据
       --DELETE FROM ACC_ST.op_prm_phy_logic_list WHERE physic_no in(SELECT phy_id from ACC_TK.IC_ES_HUNCH_INFO_BUF);
       
       --删除Buf临时数量(票务入库会删除，此处不能删除)
       --DELETE FROM ACC_TK.IC_ES_HUNCH_INFO_BUF;
       
     exception
       when others then
       begin
         ROLLBACK;
         DBMS_OUTPUT.PUT_LINE('sqlcode : ' || SQLCODE);
        DBMS_OUTPUT.PUT_LINE('sqlerrm : ' || SQLERRM);
         retMsg := '插入预赋值订单失败,'||SQLCODE||':'||SQLERRM;
         retCode := 16;
         return;
       end;
     end;
   end;
   end if;

   if(esWorkTypeId ='02') then       --重编码订单
   begin
     begin
         insert into ACC_TK.IC_ES_AGAIN_INFO(logical_id, card_main_type, card_sub_type, req_no, phy_id,
                                   print_id, manu_time, card_money, peri_avadate, kdc_version,
                                   hdl_time, order_no, status_flag, card_type,
                                   card_ava_days,exit_line_code,exit_station_code,model
                                   )
                        select     logical_id, card_main_type, card_sub_type, req_no, phy_id,
                                   print_id, manu_time, card_money, peri_avadate, kdc_version,
                                   hdl_time, order_no, status_flag, card_type,
                                   card_ava_days,exit_line_code,exit_station_code,model
                        from     ACC_TK.IC_ES_AGAIN_INFO_BUF;
                        
       --插入物理卡号与逻辑卡号对照备份表
       insert into ACC_ST.op_his_phy_logic_list(physic_no,logic_no)
                    SELECT phy_id,logical_id from ACC_TK.IC_ES_AGAIN_INFO_BUF;
                      
       --删除物理卡号与逻辑卡号对照表已经制卡数据
       --DELETE FROM ACC_ST.op_prm_phy_logic_list WHERE physic_no in(SELECT phy_id from ACC_TK.IC_ES_AGAIN_INFO_BUF); 
       
       --删除Buf临时数量(票务入库会删除，此处不能删除)
       --DELETE FROM ACC_TK.IC_ES_AGAIN_INFO_BUF;              
                        
     exception
      when others then
      begin
         ROLLBACK;
         DBMS_OUTPUT.PUT_LINE('sqlcode : ' || SQLCODE);
         DBMS_OUTPUT.PUT_LINE('sqlerrm : ' || SQLERRM);
         retMsg := '插入重编码订单失败,'||SQLCODE||':'||SQLERRM;
         retCode := 17;
         return;
      end;
     end;
   end;
   end if;
   if(esWorkTypeId ='03') then       --注销订单
   begin
     begin
       insert into ACC_TK.IC_ES_LOGOUT_INFO(logical_id, card_main_type, card_sub_type, req_no, phy_id,
                                  print_id, manu_time, card_money, peri_avadate, kdc_version,
                                  hdl_time, order_no, status_flag, card_type,
                                  card_ava_days,exit_line_code,exit_station_code,model
                                  )
                       select     logical_id, card_main_type, card_sub_type, req_no, phy_id,
                                  print_id, manu_time, card_money, peri_avadate, kdc_version,
                                  hdl_time, order_no, status_flag, card_type,
                                  card_ava_days,exit_line_code,exit_station_code,model
                       from     ACC_TK.IC_ES_LOGOUT_INFO_BUF;
                       
       --插入物理卡号与逻辑卡号对照备份表
       insert into ACC_ST.op_his_phy_logic_list(physic_no,logic_no)
                    SELECT phy_id,logical_id from ACC_TK.IC_ES_LOGOUT_INFO_BUF;
                      
       --删除物理卡号与逻辑卡号对照表已经制卡数据
       --DELETE FROM ACC_ST.op_prm_phy_logic_list WHERE physic_no in(SELECT phy_id from ACC_TK.IC_ES_LOGOUT_INFO_BUF);  
       
       --删除Buf临时数量(票务入库会删除，此处不能删除)
       --DELETE FROM ACC_TK.IC_ES_LOGOUT_INFO_BUF;               
                       
     exception
       when others then
       begin
         ROLLBACK;
         retMsg := '插入注销订单失败,'||SQLCODE||':'||SQLERRM;
         retCode := 17;
         return;
       end;
     end;
   end;
   end if;
   ------------------------导入缓存数据到正式明细表结束-------------------------
   --清理临时表
   DELETE ACC_TK.T#IC_PDU_PLAN_ORDER_MAPPING;
   DELETE ACC_TK.T#IC_PDU_ORDER_FORM;
   DELETE ACC_TK.T#IC_PDU_PRODUCE_BILL_DETAIL;
   DELETE acc_tk.T#IC_PDU_RESULT_DETAIL;

   commit;--提交

   retMsg := '成功';
   retCode := 0;

END UP_IC_ES_GEN_PRODUCE_BILL;
